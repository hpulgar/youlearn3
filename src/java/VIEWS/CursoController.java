package VIEWS;

import ENTITIES.Curso;
import ENTITIES.CursoSubCat;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.CursoFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

@Named("cursoController")
@SessionScoped
public class CursoController implements Serializable {

    private Curso current;
    private DataModel items = null;
    @EJB
    private MODELS.CursoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idCurso;
    private List<Curso> arCurso = new ArrayList();

    public List<Curso> getArCurso() {
        return arCurso;
    }

    public void setArCurso(List<Curso> arCurso) {
        this.arCurso = arCurso;
    }
    

    

    public CursoController() {
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

   

    
    
    public Curso getSelected() {
        if (current == null) {
            current = new Curso();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CursoFacade getFacade() {
        return ejbFacade;
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
        current = (Curso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Curso();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Curso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Curso) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoDeleted"));
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

    public Curso getCurso(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    ////////////////////////////////CURSO///////////////////////////////////////////////////
        
        // INT IDCURSO
        //INT ID SUBCAT
        //ID_USUARIO
        //NOMBRE_CURSO
        //PERSONAS_INSCRITAS
        //DESCRIPCION_CURSO
        //AUTORIZADO 
        //CONTENIDOS
        //SEGUIDORES
        //INTRODUCCION
        //IMAGEN
        
        
        
        public List<Curso> listaCursos()
        {
            this.arCurso.clear();
            arCurso = ejbFacade.findAll();
            
            return arCurso;
        }
        
        public Curso verCurso(int idCurso)
        {
            Curso objC;
            objC = ejbFacade.find(idCurso);
            
            
            return objC;
        }
        
        
        public String CrearCurso(int id_scat_curso,int id_usuario)
        {
            try{
                
        // 1 INT IDCURSO automatico
        // 2 INT ID SUBCAT por parametro X
        // 3 ID_USUARIO por parametro  X
        // 4 NOMBRE_CURSO selecterd 
        // 5 PERSONAS_INSCRITAS defecto 0 X
        // 6 DESCRIPCION_CURSO selected 
        // 7 AUTORIZADO  por defecto 0 X
        // 8 CONTENIDOS selected 
        // 9 SEGUIDORES por defecto 0 X
        // 10 INTRODUCCION selected 
        // 11 IMAGEN selected
                
                
                CursoSubCat csc = new CursoSubCat();
                csc.setIdSubcat(id_scat_curso);
        
                Usuario ou = new Usuario();
                ou.setIdUsuario(id_usuario);
                
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
//                Date date = new Date();
//                String fecha = dateFormat.format(date);

             

               // current.setfechaCurso(dateFormat.parse(fecha));
                current.setIdUsuario(ou);
                current.setIdCat(csc);
                current.setAutorizado(false);
                current.setSeguidores(0);
                current.setPersonasInscritas(0);
                getFacade().create(current);
                current = null;

                return "/Por Definir";

                }catch(Exception e)
                {
                    System.out.println("EL ERRORR"+ e);
                    return "/Por Definir";
                }
        }
        
        
    @FacesConverter(forClass = Curso.class)
    public static class CursoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursoController controller = (CursoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursoController");
            return controller.getCurso(getKey(value));
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
            if (object instanceof Curso) {
                Curso o = (Curso) object;
                return getStringKey(o.getIdCurso());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Curso.class.getName());
            }
        }

    }

}
