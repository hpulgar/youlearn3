package VIEWS;

import ENTITIES.PublicacionPerfil;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.PublicacionPerfilFacade;

import java.util.*;
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

@Named("publicacionPerfilController")
@SessionScoped
public class PublicacionPerfilController implements Serializable {

    private PublicacionPerfil current;
    private DataModel items = null;
    @EJB
    private MODELS.PublicacionPerfilFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idPublicacion;
    private List<PublicacionPerfil> arPerfil = new ArrayList();
    //private List<PublicacionPerfil> arPerfil2 = new ArrayList();
    private int idPerfil;

    public PublicacionPerfilController() {
    }

    public List<PublicacionPerfil> getArPerfil() {
        return arPerfil;
    }

    public void setArPerfil(List<PublicacionPerfil> arPerfil) {
        this.arPerfil = arPerfil;
    }

//    public List<PublicacionPerfil> getArPerfil2() {
//        return arPerfil2;
//    }
//
//    public void setArPerfil2(List<PublicacionPerfil> arPerfil2) {
//        this.arPerfil2 = arPerfil2;
//    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    
    public PublicacionPerfil getSelected() {
        if (current == null) {
            current = new PublicacionPerfil();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PublicacionPerfilFacade getFacade() {
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
        current = (PublicacionPerfil) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PublicacionPerfil();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionPerfilCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PublicacionPerfil) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionPerfilUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PublicacionPerfil) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PublicacionPerfilDeleted"));
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
 
    
//    
//    int idpublicacion Autoincrement
//    String publicacion X pagian(Publicaciones)
//    int id_publicador X parametro
//    date publicacion genera automaticamente
//    int idUsuario X parametro (dueño d ela pagina)
//    
//    
    
    
    
    
    public void crearPublicacion(int publicador,int id_usuario)
    {
        try{
            
            Usuario ou = new Usuario();
            ou.setIdUsuario(publicador);
            
            Usuario ou2 = new Usuario();
            ou.setIdUsuario(id_usuario);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
            
            current.setIdPublicador(ou);
            current.setIdUsuario(ou2);
            current.setFechaPublicacion(dateFormat.parse(fecha));
            ejbFacade.create(current);
            this.current=null;
        }
        catch(Exception e)
        {
         System.out.println("El error al crear la publicacion es "+ e);   
        }
    }
    
    
    
    public List<PublicacionPerfil> verPublicaciones(int idUsuario)
    {
        arPerfil.clear();
        arPerfil =ejbFacade.findAll();
        List<PublicacionPerfil> arPerfil2 = new ArrayList();
        
        for(int i=0;i<arPerfil.size();i++)
        {
            if(arPerfil.get(i).getIdUsuario().getIdUsuario() == idUsuario)
            {
                
                arPerfil2.add(arPerfil.get(i));
            }
        }
        return arPerfil2;
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public PublicacionPerfil getPublicacionPerfil(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = PublicacionPerfil.class)
    public static class PublicacionPerfilControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PublicacionPerfilController controller = (PublicacionPerfilController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "publicacionPerfilController");
            return controller.getPublicacionPerfil(getKey(value));
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
            if (object instanceof PublicacionPerfil) {
                PublicacionPerfil o = (PublicacionPerfil) object;
                return getStringKey(o.getIdPublicacion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PublicacionPerfil.class.getName());
            }
        }

    }

}
