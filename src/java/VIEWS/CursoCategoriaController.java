package VIEWS;

import ENTITIES.CursoCategoria;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.CursoCategoriaFacade;

import java.io.Serializable;
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

@Named("cursoCategoriaController")
@SessionScoped
public class CursoCategoriaController implements Serializable {

    private CursoCategoria current;
    private DataModel items = null;
    @EJB
    private MODELS.CursoCategoriaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int id_curso_cat;
    private List<CursoCategoria> arCursoCat = new ArrayList();

    public CursoCategoriaController() {
    }

    public int getId_curso_cat() {
        return id_curso_cat;
    }

    public void setId_curso_cat(int id_curso_cat) {
        this.id_curso_cat = id_curso_cat;
    }

    public List<CursoCategoria> getArCursoCat() {
        return arCursoCat;
    }

    public void setArCursoCat(List<CursoCategoria> arCursoCat) {
        this.arCursoCat = arCursoCat;
    }
    
    public CursoCategoria getSelected() {
        if (current == null) {
            current = new CursoCategoria();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CursoCategoriaFacade getFacade() {
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
        current = (CursoCategoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new CursoCategoria();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoCategoriaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CursoCategoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoCategoriaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CursoCategoria) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoCategoriaDeleted"));
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

    public CursoCategoria getCursoCategoria(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public List<CursoCategoria> cCats()
    {
        arCursoCat.clear();
        arCursoCat = ejbFacade.findAll();
        
       
        return arCursoCat;
    }
    ////////////////////////////////
    
    
    public List<CursoCategoria> tablaCategoriasCurso()
    {
        return ejbFacade.findAll();
    }
    
    
     public void creacionCursoCategoria()
    {
        System.out.println("Dentra o no Dentra");
        try{
            System.out.println("Antes de Crear");
            System.out.println("fecha publicacion "+current.getNomCat());
          
            ejbFacade.create(current);
            current = null;
           
            
            
        }catch(Exception e)
        {
            System.out.println("ERRRROOORR "+e);
          
        }
    }
     
     public void eliminarCategoriaCurso(int id)
        {
            current.setIdCat(id);
            ejbFacade.remove(current);
            current=null;
        
        }
     
     public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((CursoCategoria) event.getObject()).getIdCat().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public void onRowEdit(RowEditEvent event) 
        {
            
            FacesMessage msg = new FacesMessage("Car Edited", ((CursoCategoria) event.getObject()).getIdCat().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            //((Curso) event.getObject()).setPublicacion(current.getPublicacion());
            //((PublicacionPerfil) event.getObject()).setIdPublicacion(current.getIdPublicacion());
            System.out.println("Imprime publicacion q llega por evento: "+((CursoCategoria) event.getObject()).getIdCat());
            current.setIdCat(((CursoCategoria) event.getObject()).getIdCat());
            //System.out.println("Imprime publicacion q llega por evento: "+((PublicacionPerfil) event.getObject()).getIdPublicacion());
            //current = ((Curso) event.getObject());
            ejbFacade.edit(current); //REFORMULAR?????
            current = null;
        }
     
    ////////////////////////////////
    

    
    
    

    @FacesConverter(forClass = CursoCategoria.class)
    public static class CursoCategoriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursoCategoriaController controller = (CursoCategoriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursoCategoriaController");
            return controller.getCursoCategoria(getKey(value));
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
            if (object instanceof CursoCategoria) {
                CursoCategoria o = (CursoCategoria) object;
                return getStringKey(o.getIdCat());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CursoCategoria.class.getName());
            }
        }

    }

}
