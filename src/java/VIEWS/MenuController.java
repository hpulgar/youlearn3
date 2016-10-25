package VIEWS;

import ENTITIES.Menu;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.MenuFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

@Named("menuController")
@SessionScoped
public class MenuController implements Serializable {

    private Menu current;
    private DataModel items = null;
    @EJB
    private MODELS.MenuFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String nombreMenu;
    private Menu objMenu;

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public MenuController() {
    }

    public Menu getObjMenu() {
        return objMenu;
    }

    public void setObjMenu(Menu objMenu) {
        this.objMenu = objMenu;
    }

    public Menu getSelected() {
        if (current == null) {
            current = new Menu();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MenuFacade getFacade() {
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
        current = (Menu) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Menu();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MenuCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Menu) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MenuUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Menu) getItems().getRowData();
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

     public boolean logeado()
     {
         boolean value= true;
         HttpSession sess = Util.getSession();
        if (sess.getAttribute("username")== null)
        {
           value=false;
        }
        return value;
     }
     
    
     public boolean noLogeado()
     {
         HttpSession sess = Util.getSession();
        return sess.getAttribute("username") == null;
     }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MenuDeleted"));
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

    public Menu getMenu(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    ////////////////////MANTENEDOR
    
    public List<Menu> cargarMenu()
    {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) 
    {
        FacesMessage msg = new FacesMessage("Car Edited", ((Menu) event.getObject()).getIdMenu().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        try{
        //((Curso) event.getObject()).setPublicacion(current.getPublicacion());
        //((PublicacionPerfil) event.getObject()).setIdPublicacion(current.getIdPublicacion());
        
        //System.out.println("Imprime publicacion q llega por evento: "+((PublicacionPerfil) event.getObject()).getIdPublicacion());
        current.setIdMenu(((Menu) event.getObject()).getIdMenu()); 
        System.out.println("Imprime publicacion q llega por evento: "+current.getIdMenu());
        ejbFacade.edit(current); //REFORMULAR?????
        current = null;
        }
        catch(Exception e)
        {
             System.out.println(e+" Imprime publicacion q llega por evento: "+((Menu) event.getObject()).getIdMenu());
        }
    }
    
    
     public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Menu) event.getObject()).getIdMenu().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
     
     
     public void eliminarMenu()
        {
            try
            {
                System.out.println("imrpime q "+objMenu.getNombre());
                System.out.println("imrpime q "+objMenu.getIdMenu());
                System.out.println("imrpime q "+objMenu.getUrl());
                System.out.println("imrpime q "+objMenu.getIdPerfil());

                
                getFacade().remove(objMenu);
            }
            catch(Exception e)
            {
                System.out.println("Error en Eliminacion "+e);
            }
        }
     
     public void crearMenu()
     {
         try{
            System.out.println("Antes de Crear");
            System.out.println("DATO MENU "+current.getNombre());
          
            ejbFacade.create(current);
            current = null;
           
            //return "/MantenedorGeneral.xhtml";
            
        }catch(Exception e)
        {
            System.out.println("ERRRROOORR "+e);
           // return "/publicacionDialog.xhtml";
        }
     }
    ////////////////////////////////////////////

    @FacesConverter(forClass = Menu.class)
    public static class MenuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "menuController");
            return controller.getMenu(getKey(value));
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
            if (object instanceof Menu) {
                Menu o = (Menu) object;
                return getStringKey(o.getIdMenu());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Menu.class.getName());
            }
        }

    }

}
