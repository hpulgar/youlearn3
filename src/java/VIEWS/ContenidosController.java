package VIEWS;

import ENTITIES.Contenidos;
import ENTITIES.Curso;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.ContenidosFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import java.util.*;
import javax.faces.application.FacesMessage;
import org.primefaces.event.RowEditEvent;

@Named("contenidosController")
@SessionScoped
public class ContenidosController implements Serializable {

    private Contenidos current;
    private DataModel items = null;
    @EJB
    private MODELS.ContenidosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idUnidad;
    private int idContenido;
    private List<Contenidos> arContenidos = new ArrayList<Contenidos>();
    private List<Contenidos> arContenidos2 = new ArrayList<Contenidos>();
    
    public ContenidosController() {
    }

    public Contenidos getSelected() {
        if (current == null) {
            current = new Contenidos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ContenidosFacade getFacade() {
        return ejbFacade;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public List<Contenidos> getArContenidos() {
        return arContenidos;
    }

    public void setArContenidos(List<Contenidos> arContenidos) {
        this.arContenidos = arContenidos;
    }

    public List<Contenidos> getArContenidos2() {
        return arContenidos2;
    }

    public void setArContenidos2(List<Contenidos> arContenidos2) {
        this.arContenidos2 = arContenidos2;
    }
    
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Contenidos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Contenidos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenidosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Contenidos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenidosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Contenidos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenidosDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Contenidos getContenidos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    /////////////////////////////////////////////////////////////////////
//    contenidos
//            int id_contenido X (auto increment)
//            int unidad   POR USUARIO
//            string nombre_unidad POR USUARIO
//            date fecha  AUTOMATICO
//            string contenido POR USUARIO
//            int id_curso    POR PARAMETRO
                
    
    
    
    
    public String crearContenido(int idCurso)
    {
        try
        {
            Curso obCurso = new Curso();
            obCurso.setIdCurso(idCurso);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
            
            current.setFecha(dateFormat.parse(fecha));
            current.setIdCurso(obCurso);
            
            getFacade().create(current);
            current = null;
            
            return "/curso.xhtml";
            
        }catch(Exception e)
        {
            System.out.println("EL ERROR"+ e );
            return "/contenido_crear.xhtml";            
        }
        
    }
    
    
    public List<Contenidos> verTodosCont(int idCurso)
    {
        
        this.arContenidos.clear();
        this.arContenidos2.clear();
        this.arContenidos= ejbFacade.findAll();
        
        for(int i=0;i<arContenidos.size();i++)
        {
            if(arContenidos.get(i).getIdCurso().getIdCurso() == idCurso)
            {
                arContenidos2.add(arContenidos.get(i));
            }
            
        }
              return arContenidos2;  
        
    }
    
    public List<Contenidos> verUnCont(int idCont)
    {
        arContenidos.clear();
        arContenidos = ejbFacade.verUnContenido(idCont);
       
        
        return arContenidos;
    }
    
    public void cargarCont(int idContenido)
    {
        //Contenidos obC = new Contenidos();
        current = ejbFacade.contenido(idContenido);
        
    }
    
    
    public String verContenidoI(int idContenido)// I de Individual
    {
        setIdContenido(idContenido);
        return "/contenidos.xhtml";
    }
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////
    
    //MANTENEDOR

    public List<Contenidos> tablaContenidos()
    {
        return ejbFacade.findAll();
    }
    
    
    public void onRowEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Contenido Editado",((Contenidos) event.getObject()).getIdContenido().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        current.setIdContenido(((Contenidos) event.getObject()).getIdContenido());
        ejbFacade.edit(current);
        current= null;
        
        
    }
    
    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edicion Cancelada",((Contenidos) event.getObject()).getIdContenido().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    
    public void crearCont()
    {
        try
        {
            current.setIdContenido(null);
            ejbFacade.crear(current);
            current = null;
        }catch(Exception e)
        {
            System.out.println("Error al crear contenido "+e);
        }
    }
    
    
    public void eliminarContenido(int id)
    {
        current.setIdContenido(id);
        ejbFacade.remove(current);
        current= null;
    }
    
    public String verContenido(int id)
    {
        return "conte"+id;
    }
    
    public void cargaDatos(int id)
    {
        
        current = ejbFacade.find(id);
        
    }
    
    public void prepararCrear()
    {
        current = null;
    }
    
    
    //////////////////////////////////////////////////
    
    
    
    
    

    @FacesConverter(forClass = Contenidos.class)
    public static class ContenidosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContenidosController controller = (ContenidosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contenidosController");
            return controller.getContenidos(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Contenidos) {
                Contenidos o = (Contenidos) object;
                return getStringKey(o.getIdContenido());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Contenidos.class.getName());
            }
        }

    }

}
