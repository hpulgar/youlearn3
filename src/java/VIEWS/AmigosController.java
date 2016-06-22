package VIEWS;

import ENTITIES.Amigos;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.AmigosFacade;

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

@Named("amigosController")
@SessionScoped
public class AmigosController implements Serializable {

    private Amigos current;
    private DataModel items = null;
    @EJB
    private MODELS.AmigosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String mensajeBoton;
    private int idAmistad;
    private String respuesta;

    public AmigosController() {
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getIdAmistad() {
        return idAmistad;
    }

    public void setIdAmistad(int idAmistad) {
        this.idAmistad = idAmistad;
    }

    public String getMensajeBoton() {
        return mensajeBoton;
    }

    public void setMensajeBoton(String mensajeBoton) {
        this.mensajeBoton = mensajeBoton;
    }

    
    public Amigos getSelected() {
        if (current == null) {
            current = new Amigos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AmigosFacade getFacade() {
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
        current = (Amigos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Amigos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AmigosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Amigos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AmigosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Amigos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AmigosDeleted"));
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

    public Amigos getAmigos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    ////////////////////////////////////////////////////////
    private void crearSolicitud(int idUser1,int idUser2,boolean apr){
        try
        {
            //id_amistad
            //id_usuario1
            //id_usuario2
            //aprobado(usuario 2 aprueba)
            Amigos objA = new Amigos();
            Usuario ou = new Usuario();
            ou.setIdUsuario(idUser1);
            Usuario ou2 = new Usuario();
            ou2.setIdUsuario(idUser2);
            
            objA.setAprobado(apr);
            objA.setIdUsuario1(ou);
            System.out.println("asd --- "+objA.getIdUsuario1().getIdUsuario());
            objA.setIdUsuario2(ou2);
            System.out.println("asd --- "+objA.getIdUsuario2().getIdUsuario());
            
            getFacade().create(objA);
            
            System.out.println("Lo CREA");
            

        }catch(Exception e){
            System.out.println("Si tira error es este -----------------> "+e);
        }
    }
    
    private void aceptarSolicitud(int idUser1,int idUser2,Amigos objAm ){
        try
        {
            System.out.println("ID DE LA AMISTAD A EDITAR "+objAm.getIdAmistad());
            objAm.setAprobado(true);
            ejbFacade.edit(objAm);
            System.out.println("Aprobado = "+ objAm.getAprobado());
            
            crearSolicitud(idUser1,idUser2,true);
            
        }catch(Exception e){
            System.out.println("Si tira error es este -----------------> "+e);
        }
    }
    
    
    public String buscarRelacion(int idUser1,int idUser2)
    {
            
            List<Amigos> oa = ejbFacade.findAll();
            if(oa.isEmpty())
            {
                this.setRespuesta("Enviar Solicitud");
            }   
            else
            {
                for(int i=0;i<oa.size();i++)
                {
                    if(oa.get(i).getIdUsuario1().getIdUsuario() == idUser1 && oa.get(i).getIdUsuario2().getIdUsuario() == idUser2 &&
                       oa.get(i).getAprobado() == true)
                    {
                        System.out.println(oa.get(i).getIdUsuario1().getIdUsuario()+"-"+idUser1+"-"+oa.get(i).getIdUsuario2().getIdUsuario()+"-"+idUser2);
                        this.setRespuesta("Amigos");
                        break;
                    }else if(oa.get(i).getIdUsuario1().getIdUsuario() == idUser1 && oa.get(i).getIdUsuario2().getIdUsuario() == idUser2 && oa.get(i).getAprobado() == false)
                    {
                        this.setRespuesta("Solicitud Pendiente");
                    }else if(oa.get(i).getIdUsuario1().getIdUsuario() == idUser2 && oa.get(i).getIdUsuario2().getIdUsuario() == idUser1 && oa.get(i).getAprobado() == false)
                    {
                        this.setRespuesta("Aceptar Solicitud");
                        current= null;
                        current = oa.get(i);
                    }else if(oa.get(i).getIdUsuario1().getIdUsuario() != idUser2 && oa.get(i).getIdUsuario2().getIdUsuario() != idUser1)
                    {
                        System.out.println("ENTRA AL ENVIAR SOLICITUD "+oa.get(i).getIdAmistad());
                        this.setRespuesta("Enviar Solicitud");
                    }
                }

            }
        return this.getRespuesta();
    }
     
    
    public void enviarSolicitud(int idUser1,int idUser2,String meth)
    {
                if(null != meth)
                switch (meth) {
            case "Aceptar Solicitud":
                System.out.println("ACEPTAR SOLICITU3");
                System.out.println("editar");
                aceptarSolicitud(idUser1,idUser2,current);
                break;
            case "Enviar Solicitud":
                System.out.println("ENVIAR SOLICITU3");
                crearSolicitud(idUser1,idUser2,false);
                break;
            default:
                System.out.println("ESTO NO HACE NADA");
                break;
        }
    }
    
    
       
    
//    public void enviarSolicitudOLD(int idUser1,int idUser2)
//    {
//            List<Amigos> oa = ejbFacade.findAll();
//            if(!oa.isEmpty())
//            {
//                for(int i=0;i<oa.size();i++)
//                {
//                    if(oa.get(i).getIdUsuario1().getIdUsuario() == idUser1 && oa.get(i).getIdUsuario2().getIdUsuario() == idUser2)
//                    {
//                        if(oa.get(i).getAprobado() == true)
//                        {
//                            System.out.println(" no lo crea");
//                        }else
//                        {
//                            System.out.println("Solicitud Pendiente");
//                        }
//                    }else 
//                    {
//                         System.out.println("Si "+oa.get(i).getIdUsuario1().getIdUsuario()+" es igual a "+ idUser2+" y "+oa.get(i).getIdUsuario2().getIdUsuario()+" es igual a "+idUser1);
//                         System.out.println("Entonces...");
//                        if(oa.get(i).getIdUsuario1().getIdUsuario() == idUser2 && oa.get(i).getIdUsuario2().getIdUsuario() == idUser1)
//                        {
//                            if(oa.get(i).getAprobado()==false)
//                            {
//                                System.out.println("A PUNTO DE EDITAR");
//                                current= null;
//                                current = oa.get(i);
//                                System.out.println("editar");
//                                aceptarSolicitud(idUser1,idUser2,current);
//                            }else
//                            {
//                                System.out.println("SEGUN ESTO EL APROBADO ES TRUE");
//                            }
//                        }else
//                        {
//                            System.out.println("CREAR 1");
//                            crearSolicitud(idUser1,idUser2,false);
//                            break;
//                        }
//                    }
//                }
//            }else
//            {
//                System.out.println("CREAR 2");
//                crearSolicitud(idUser1,idUser2,false);
//            }
//      
//    }
//    
    
    
    
    public boolean botonAmistad(int idUser1, int idUser2)
    {
        return idUser1 != idUser2;
        
    }
    
    public boolean btoAmigosHabilitado(String meth)
    {
        return ("Solicitud Pendiente".equals(meth) ||"Amigos".equals(meth));
    }
    
    
    
    ////////////////////////////////////////////////////////////
    @FacesConverter(forClass = Amigos.class)
    public static class AmigosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AmigosController controller = (AmigosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "amigosController");
            return controller.getAmigos(getKey(value));
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
            if (object instanceof Amigos) {
                Amigos o = (Amigos) object;
                return getStringKey(o.getIdAmistad());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Amigos.class.getName());
            }
        }

    }

}
