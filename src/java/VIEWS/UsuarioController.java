package VIEWS;

import ENTITIES.Perfil;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.UsuarioFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private DataModel items = null;
    @EJB
    private MODELS.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
     private String nombreUsuario,contraseña,message;
    private int id_user;
    private String correo;
    private int id_muro;
    private int id_usuario_amigo;

    
    
    
    public UsuarioController() {
    }
    
    public int getId_usuario_amigo() {
        return id_usuario_amigo;
    }

    public void setId_usuario_amigo(int id_usuario_amigo) {
        System.out.println("id ami "+ id_usuario_amigo);
        this.id_usuario_amigo = id_usuario_amigo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Usuario> getIdp() {
        return idp;
    }

    public void setIdp(List<Usuario> idp) {
        this.idp = idp;
    }

    public List<Usuario> getCargaP() {
        return cargaP;
    }

    public void setCargaP(List<Usuario> cargaP) {
        this.cargaP = cargaP;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
   private List<Usuario> idp = new ArrayList();
    private List<Usuario> cargaP = new ArrayList();
    private int idProfile;


   

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

     public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    
    
    //crear usuario
    
//    
//    int id_usuario automatico
//    String username Pag
//    String password Pag
//    date fecha_creacion Auto
//    int id_perfil auto
//    String correo pag
//    int creditos auto en 0
//    string imagen portada inicial null
//    string imagen dashboard inicial null
//    string foto perfil inicial null
//            
    
    public String crearUsuario()
    {
        try{
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.setTimeZone(new SimpleTimeZone(-3, "GMT"));
                sdf.applyPattern("yyyy/mm/dd");
                Date fecha = new Date();
                
                Perfil oPe = new Perfil();
                oPe.setIdPerfil(2);
                
                
                current.setFechaCreacion(fecha);
                current.setIdPerfil(oPe);
                current.setCreditos(0);
                current.setImagen_portada_perfil("https://i.ytimg.com/vi/1WiGCe7QZGI/maxresdefault.jpg");
                current.setImagen_dashboard("https://i.ytimg.com/vi/1WiGCe7QZGI/maxresdefault.jpg");
                current.setImagen_foto_perfil("https://i.ytimg.com/vi/1WiGCe7QZGI/maxresdefault.jpg");
                
                getFacade().create(current);
                
                return "/index.xhtml";
                
        }catch(Exception e)
        {
            
            System.out.println("LA WEA NO CREA "+e);
            return "/register.xhtml";
        }
                
                
    }
    
    
        //carga username
    public String verUsername(int idp){
        System.out.println("IMRPIMIENDO EL ID DEL USUARIO "+idp);
        Usuario uo ;
        uo = ejbFacade.cargaUsername(idp).get(0);
        
    
        return uo.getUsername();
    }
    
    
    public List<Usuario> verUser(int idUser)
    {
        List<Usuario> aUs;
        aUs = ejbFacade.cargaPerfiles(idUser);
        
        return aUs;
    }
    
   
    
    
     private int getPerfil(String nomU,String PassUS)
     {
         //Usuario uss;
         idp = ejbFacade.log(nomU, PassUS);
         for(int i = 0;i<idp.size();i++)
         {
             setIdProfile((int) idp.get(i).getIdPerfil().getIdPerfil());
             setId_user((int) idp.get(i).getIdUsuario());
             setId_usuario_amigo((int)idp.get(i).getIdUsuario());
         }
         return getIdProfile();
         
     }
    
        private boolean verifica(String nombreU,String passU)
    {
        
        getPerfil(nombreU,passU);
        return !ejbFacade.log(nombreU, passU).isEmpty();
       
    }
     public String acceso()
   {
       if(verifica(getNombreUsuario(), getContraseña()))
       {
         
           HttpSession session = Util.getSession();
          
       
             session.setAttribute("id_perfil", getIdProfile());
             session.setAttribute("username", getNombreUsuario());
              session.setAttribute("id_usuario", getId_user());
              
         
          
           setCookie("CookieValue","CookieStorage",3000000);
           getCookie("CookieValue");
           

           
            return "/index";
       }
       else
       {
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login Invalido,Intentalo denuevo",
                    "Intentalo denuevo"));
                    return "login";
           
       }
   }
     
    
     
     public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "/login";
   }


  public void setCookie(String name, String value, int expiry) {

    FacesContext facesContext = FacesContext.getCurrentInstance();

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Cookie cookie = null;

    Cookie[] userCookies = request.getCookies();
    if (userCookies != null && userCookies.length > 0 ) {
        for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(name)) {
                cookie = userCookies[i];
                break;
            }
        }
    }

    if (cookie != null) {
        cookie.setValue(value);
    } else {
        cookie = new Cookie(name, value);
        cookie.setPath(request.getContextPath());
    }

    cookie.setMaxAge(expiry);

    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    response.addCookie(cookie);
  }

  public Cookie getCookie(String name) {

    FacesContext facesContext = FacesContext.getCurrentInstance();

    HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    Cookie cookie = null;

    Cookie[] userCookies = request.getCookies();
    if (userCookies != null && userCookies.length > 0 ) {
        for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(name)) {
                cookie = userCookies[i];
                return cookie;
            }
        }
    }
    return null;
  }
  
  
  
  public String verDashboard(int iduser1, int iduser2)
  {
      if(iduser1 == iduser2)
      {
          return "<li><a href='dashboard.xhtml'>Dashboard</a></li>";
      }
      else
      {
          return "";
      }
  }
///////////////////////////////////////////////// fin

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
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

    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getIdUsuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }

}
