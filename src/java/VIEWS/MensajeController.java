package VIEWS;

import ENTITIES.Mensaje;
import ENTITIES.TipoPublicacion;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.MensajeFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

@Named("mensajeController")
@SessionScoped
public class MensajeController implements Serializable {

    private Mensaje current;
    private DataModel items = null;
    @EJB
    private MODELS.MensajeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idChat;
    private List<Mensaje> arrMen = new ArrayList();
    private List<Mensaje> arrMen2 = new ArrayList();

    public List<Mensaje> getArrMen2() {
        return arrMen2;
    }

    public void setArrMen2(List<Mensaje> arrMen2) {
        this.arrMen2 = arrMen2;
    }
    private String usernameAmigo;
    private int idAmigo;
    private String ahora;


    public MensajeController() {
    }

    public String getAhora() {
        return ahora;
    }

    public void setAhora(String ahora) {
        this.ahora = ahora;
    }

    public String getUsernameAmigo() {
        return usernameAmigo;
    }

    public void setUsernameAmigo(String usernameAmigo) {
        this.usernameAmigo = usernameAmigo;
    }

    public int getIdAmigo() {
        return idAmigo;
    }

    public void setIdAmigo(int idAmigo) {
        this.idAmigo = idAmigo;
    }

    public Mensaje getSelected() {
        if (current == null) {
            current = new Mensaje();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MensajeFacade getFacade() {
        return ejbFacade;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public List<Mensaje> getArrMen() {
        return arrMen;
    }

    public void setArrMen(List<Mensaje> arrMen) {
        this.arrMen = arrMen;
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
        current = (Mensaje) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Mensaje();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Mensaje) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Mensaje) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MensajeDeleted"));
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

    public Mensaje getMensaje(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    ///////////////////////////////////////////////////////////////////////
    
    public List<Mensaje> cargaChat(int idUser,int idAmigo)
    {
        arrMen.clear();
        arrMen2.clear();
        arrMen= ejbFacade.consultaChat(idUser, idAmigo);
        
        for(int i=arrMen.size()-1; i >= 0 ;i--)
        {
            if(i != 20)
            {
                
                arrMen2.add(arrMen.get(i));
            }else
            {
                break;
            }
        }
        arrMen.clear();
        for(int e=arrMen2.size()-1;e>=0;e--)
        {
            arrMen.add(arrMen2.get(e));
        }
        
        return arrMen;
    }
    
    public boolean ordenM(int idUser1, int idUser2)
    {
        if(idUser1 == idUser2)
        {
            return false;
        }else
        {
            return true;
        }
        
    }
    
    
    //////////////////////////////////////////////////////////////////////
    public void enviarMensaje(int idUsuario,int idUsuarioAmigo)
    {
        System.out.println("ENTRA AL METODO CREAR PUBLICACION");
        try{
            Usuario ou = new Usuario();
            ou.setIdUsuario(idUsuario);
            
            Usuario ou2 = new Usuario();
            ou2.setIdUsuario(idUsuarioAmigo);

            
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
     
            current.setFechaEnvio(dateFormat.parse(fecha));
            current.setIdEmisor(ou);
            current.setIdReceptor(ou2);
            
            System.out.println("Insertando mensaje con fecha "+fecha);

            
            if(current.getContenido()!= null)
            {
                ejbFacade.create(current);
                current= null;
            }else
            {
                System.out.println(" es null y no lo crea");
            }
        }
        catch(Exception e)
        {
         System.out.println("El error al crear la publicacion es "+ e);   
        }
    }
    
    
    public void setAmigo(String nombre,int id)
    {
        this.setUsernameAmigo(nombre);
        this.setIdAmigo(id);
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        this.setAhora("Hoy, "+timeStamp);
    }
//
    @FacesConverter(forClass = Mensaje.class)
    public static class MensajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MensajeController controller = (MensajeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mensajeController");
            return controller.getMensaje(getKey(value));
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
            if (object instanceof Mensaje) {
                Mensaje o = (Mensaje) object;
                return getStringKey(o.getIdMensaje());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Mensaje.class.getName());
            }
        }

    }

}
