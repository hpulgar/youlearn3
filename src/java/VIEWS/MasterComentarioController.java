package VIEWS;

import ENTITIES.MasterComentario;
import ENTITIES.MasterPft;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.MasterComentarioFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

@Named("masterComentarioController")
@SessionScoped
public class MasterComentarioController implements Serializable {

    private MasterComentario current;
    private List<MasterComentario> comentariosForo;     
    private List<MasterComentario> comentariosForo2;  
    private DataModel items = null;
    @EJB
    private MODELS.MasterComentarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public MasterComentarioController() {
    }
/////////////////////////////////
    public List<MasterComentario> comentariosForo() {
        MasterComentario foro = new MasterComentario();        
        this.comentariosForo = ejbFacade.verC();        
        for(int i=0;i>=comentariosForo.size();i++)
        {
        
            if((comentariosForo.get(i).getIdPft().getIdPft()==2) &&(comentariosForo.get(i).getIdPublicacion()==4))
                {
                    foro = comentariosForo.get(i);
                    this.comentariosForo2.add(foro);
                } 
        }
        return comentariosForo2;
        
    }
    
   ///////////////////// 
    
    public void setComentariosForo(List<MasterComentario> comentariosForo) {
        this.comentariosForo = comentariosForo;
    }

    public List<MasterComentario> getComentariosForo() {
        return comentariosForo;
    }
    
    public MasterComentario getSelected() {
        if (current == null) {
            current = new MasterComentario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MasterComentarioFacade getFacade() {
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
        current = (MasterComentario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new MasterComentario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MasterComentarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (MasterComentario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MasterComentarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (MasterComentario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MasterComentarioDeleted"));
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
    
    //CODIGO RECONCHESUMARE
    
    public String CrearComentario(int idComentador,int idPublicacion,int pft){
    try
    {
        
        //id comentario no va
        //id_pft viene por parametro --
        //comentario se define en xhtml
        //fecha comentario se moldea --
        //id usuario viene por parametro --
        //id publicacion viene por parametro --
        
        MasterComentario mc = new MasterComentario();
        mc.setIdPublicacion(idPublicacion);
        Usuario ou = new Usuario();
        ou.setIdUsuario(idComentador);
        MasterPft mpft =  new MasterPft();
        mpft.setIdPft(pft);
                
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        Date date = new Date();
        String fecha = dateFormat.format(date);
        
        
        current.setIdPft(mpft);
        current.setFechaComentario(dateFormat.parse(fecha));
        current.setIdUsuario(ou);
        current.setIdPublicacion(idPublicacion);
        
        getFacade().create(current);
        current = null;
        
        return "/blog-single.xhtml";
        
    }catch(Exception e){
        
        System.out.println("Si la wea tira error es este --> "+e);
        return "/blog-single.xhtml";}
    
    
    
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    //fin

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public MasterComentario getMasterComentario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = MasterComentario.class)
    public static class MasterComentarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MasterComentarioController controller = (MasterComentarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "masterComentarioController");
            return controller.getMasterComentario(getKey(value));
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
            if (object instanceof MasterComentario) {
                MasterComentario o = (MasterComentario) object;
                return getStringKey(o.getIdComentario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MasterComentario.class.getName());
            }
        }

    }

}
