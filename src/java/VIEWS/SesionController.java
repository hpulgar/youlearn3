package VIEWS;

import ENTITIES.Sesion;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.SesionFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

@Named("sesionController")
@SessionScoped
public class SesionController implements Serializable {

    private Sesion current;
    private DataModel items = null;
    @EJB
    private MODELS.SesionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SesionController() {
    }

    public Sesion getSelected() {
        if (current == null) {
            current = new Sesion();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SesionFacade getFacade() {
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
        current = (Sesion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Sesion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SesionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Sesion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SesionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Sesion) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SesionDeleted"));
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

    public Sesion getSesion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    /////////////////////////////////////////////MANTENEDOR
    
    public List<Sesion> tablaSesion()
    {
        return ejbFacade.findAll();
    }    
    
    public void onRowEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Inscripcion Editada",((Sesion) event.getObject()).getIdSesion().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        current.setIdSesion(((Sesion) event.getObject()).getIdSesion());
        ejbFacade.edit(current);
        current= null;
        
    }
    
    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edicion Cancelada",((Sesion) event.getObject()).getIdSesion().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearSesion()
    {
        
        try
        {
            current.setIdSesion(null);
            ejbFacade.create(current);
            current = null;
            
        }catch(Exception e)
        {
            System.out.println("Error al crear el Permiso "+e);
        }
    }
    
    public void eliminarSesion(int id)
    {
        current.setIdSesion(id);
        ejbFacade.remove(current);
        current = null;
    }
    
    public void cargaDatos(int id)
    {
        current = ejbFacade.find(id);
    }
    
    public void prepararCrear()
    {
        current = null;
    }
    
    //////////////////////////////////////////
    

    @FacesConverter(forClass = Sesion.class)
    public static class SesionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SesionController controller = (SesionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sesionController");
            return controller.getSesion(getKey(value));
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
            if (object instanceof Sesion) {
                Sesion o = (Sesion) object;
                return getStringKey(o.getIdSesion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Sesion.class.getName());
            }
        }

    }

}
