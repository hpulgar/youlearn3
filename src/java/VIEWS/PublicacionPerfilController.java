package VIEWS;

import ENTITIES.Amigos;
import ENTITIES.Curso;
import ENTITIES.ForoPosteos;
import ENTITIES.PublicacionPerfil;
import ENTITIES.TipoPublicacion;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

@Named("publicacionPerfilController")
@SessionScoped
public class PublicacionPerfilController extends AmigosController implements Serializable  {

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
    private String paginaRedirect;
    private String metodoRedirect;
    private String link;
    private int largoArray = 9;
    

    public PublicacionPerfilController() {
    }

    
    
    public int getLargoArray() {
        return largoArray;
    }
    
    public void presetLargo(int largo)
    {
        this.largoArray = largo;
    }
    public void setLargoArray(int largoArray) {
        
        this.largoArray = this.largoArray + largoArray;
        System.out.println("this.largoArray");
    }

    

    public String getPaginaRedirect() {
        return paginaRedirect;
    }

    public void setPaginaRedirect(String paginaRedirect) {
        this.paginaRedirect = paginaRedirect;
    }

    public String getMetodoRedirect() {
        return metodoRedirect;
    }

    public void setMetodoRedirect(String metodoRedirect) {
        this.metodoRedirect = metodoRedirect;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
    
    public void creacion()
    {
        System.out.println("Dentra o no Dentra");
        try{
//            System.out.println("Antes de Crear");
//            System.out.println("fecha publicacion "+current.getFechaPublicacion());
//            System.out.println("Publicacion "+current.getPublicacion());
//            System.out.println("Publicacion "+current.getPublicacion());
            ejbFacade.create(current);
            current = null;
            
            //return "/MantenedorGeneral.xhtml";
            
        }catch(Exception e)
        {
            System.out.println("ERRRROOORR "+e);
           // return "/publicacionDialog.xhtml";
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
//    String publicacion X pagian(Publicaciones)(<CommandLink Usuario/> +"Se ha Suscrito a "+"<:p CommandLink value="nombreCurso" action="metodoDentrodeLinkRedirige al Curso/>"
//    int id_publicador X parametro
//    date publicacion genera automaticamente
//    int idUsuario X parametro (dueño d ela pagina)
//    
//    
    
    public int agrandarLista()
    {
        int largo = this.largoArray  +10 ;
        
        return largo; 
    }
    
    public void crearPublicacion(int publicador,int id_usuario)
    {
        System.out.println("ENTRA AL METODO CREAR PUBLICACION");
        try{
            
            if(publicador == id_usuario)
            {
                Usuario ou = new Usuario();
                ou.setIdUsuario(publicador);

                Usuario ou2 = new Usuario();
                ou2.setIdUsuario(id_usuario);

                TipoPublicacion tp = new TipoPublicacion();
                tp.setIdTipoPublicacion(1);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
                Date date = new Date();
                String fecha = dateFormat.format(date);

                 System.out.println("Usuario2 Parametro --> "+current.getPublicacion());

                current.setIdTipoPublicacion(tp);
                current.setIdPublicador(ou);
                current.setIdUsuario(ou2);     
                current.setFechaPublicacion(dateFormat.parse(fecha));

                    if(current.getPublicacion() != null)
                    {
                        ejbFacade.create(current);
                        current= null;
                    }else
                    {
                        System.out.println(" es null y no lo crea");
                    }   
            }else
            {
                //UsuarioController uc = new UsuarioController();
                
                Usuario ou = new Usuario();
                ou.setIdUsuario(publicador);
                //ou.setUsername(uc.cargaUsername(id_usuario));
                
                
                
                
                Usuario ou2 = new Usuario();
                ou2.setIdUsuario(id_usuario);
                //ou2.setUsername(uc.cargaUsername(id_usuario));

                TipoPublicacion tp = new TipoPublicacion();
                tp.setIdTipoPublicacion(4);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
                Date date = new Date();
                String fecha = dateFormat.format(date);

                 System.out.println("Usuario2 Parametro --> "+current.getPublicacion());

                current.setIdTipoPublicacion(tp);
                current.setIdPublicador(ou);
                current.setIdUsuario(ou2);     
                current.setFechaPublicacion(dateFormat.parse(fecha));
                current.setLinkPublicacion(ou.getUsername()+" <a>Ha publicado en el Muro de </a>"+ou2.getUsername());
          //puta la wea current.setLinkPublicacion(ou.getUsername()+" <a 'style="color:blue"'>Ha publicado en el Muro de </a>"+ou2.getUsername());
                    if(current.getPublicacion() != null)
                    {
                        ejbFacade.create(current);
                        current= null;
                    }else
                    {
                        System.out.println(" es null y no lo crea");
                    }
            }
            
            
        }
        catch(Exception e)
        {
         System.out.println("El error al crear la publicacion es "+ e);   
        }
    }
    
    
   
    
      public void crearPublicacionPerfil(int id_usuario, String bton,int idPosteo, int idCurso)
    {
        System.out.println("ENTRA AL METODO CREAR PUBLICACION");
        try{
            PublicacionPerfil pp = new PublicacionPerfil();
            
            
            Usuario ou2 = new Usuario();
            ou2.setIdUsuario(id_usuario);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
            
            TipoPublicacion tp = new TipoPublicacion();
            tp.setIdTipoPublicacion(2);
            
            Curso cur = new Curso();
            cur.setIdCurso(idCurso);
            
            ForoPosteos fp = new ForoPosteos();
            fp.setIdPost(idPosteo);
            
           // pp.setPublicacion("Ha "+bton+" al curso "+);
           // pp.setLinkPublicacion("#{inscripcionCursoController.cargarCurso("+idCurso+","+id_usuario+")}");               
          
            System.out.println("Antes de Crear");
            if(idPosteo != 0)
            {
                    pp.setIdTipoPublicacion(tp);
                    pp.setIdPublicador(ou2);
                    pp.setIdUsuario(ou2);
                    pp.setFechaPublicacion(dateFormat.parse(fecha));
                    pp.setPublicacion("Ha creado el blog ");// cuando vamos a generar una publicacion q muestre el foro?
                   // pp.setIdCurso(cur);//creo q esta de mas(es mejor dejarlo null)
                    pp.setIdForo(fp);
                    System.out.println("Id usuario "+pp.getIdUsuario().getIdUsuario());
                    System.out.println("Id publicador "+pp.getIdPublicador().getIdUsuario());
                    System.out.println("Fecha "+pp.getFechaPublicacion());
                    System.out.println("Publicacion "+pp.getPublicacion());
                        
                
            }
            
               
            if(idCurso != 0)
            {     
                  pp.setIdTipoPublicacion(tp);
                  pp.setIdPublicador(ou2);
                  pp.setIdUsuario(ou2);
                  pp.setFechaPublicacion(dateFormat.parse(fecha));
                  pp.setIdCurso(cur);
                  //pp.setIdForo(fp);// lo mismo q arriba
                  pp.setPublicacion("Se esta "+bton+" al curso ");
                  System.out.println("Id usuario "+pp.getIdUsuario().getIdUsuario());
                  System.out.println("Id publicador "+pp.getIdPublicador().getIdUsuario());
                  System.out.println("Fecha "+pp.getFechaPublicacion());
                  System.out.println("Publicacion "+pp.getPublicacion());
                       
            }
            
             getFacade().create(pp);
            
      
            System.out.print("Despues de crear");
            
        }
        catch(NullPointerException e)
        {
         System.out.println("El error al crear la publicacion es "+ e);   
        }catch(Exception e)
        {
            System.out.println("Excepcion "+e);
        }
    }
    
    
    
    public List<PublicacionPerfil> verPublicaciones(int idUsuario)
    {
      
        
        List<PublicacionPerfil> arPerfil2 = ejbFacade.PublicacionesPerfil(idUsuario);
     
        return arPerfil2;
        
    }
    
    public List<PublicacionPerfil> verPublicacionesDashboard(int idUsuario)
    {
        arPerfil.clear();
        arPerfil =ejbFacade.findAll();
        List<PublicacionPerfil> arPerfil2 = new ArrayList();
        List<Usuario> arAmigos = listAmigos(idUsuario);
        
        for(int i= arPerfil.size()-1 ; i >= 0 ;i--)
        {
            for(int o=0;o<arAmigos.size();o++)
            {
                //System.out.println("ID "+arAmigos.get(o).getIdUsuario());
                if(Objects.equals(arPerfil.get(i).getIdUsuario().getIdUsuario(), arAmigos.get(o).getIdUsuario()))
                {
                   arPerfil2.add(arPerfil.get(i));
                }
                

            }
        }
        return arPerfil2;
    }
    ////////PARA EDITAR MANTENEDOR (TEST)
    
    
    
    
    
    
    
    public List<PublicacionPerfil> todasPublicaciones()
    {
        return ejbFacade.findAll();
    }
    
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((PublicacionPerfil) event.getObject()).getIdPublicacion().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        ((PublicacionPerfil) event.getObject()).setPublicacion(current.getPublicacion());
        //((PublicacionPerfil) event.getObject()).setIdPublicacion(current.getIdPublicacion());
        System.out.println("Imprime publicacion q llega por evento: "+((PublicacionPerfil) event.getObject()).getPublicacion());
        //System.out.println("Imprime publicacion q llega por evento: "+((PublicacionPerfil) event.getObject()).getIdPublicacion());
        //current = ((PublicacionPerfil) event.getObject());
        ejbFacade.edit(((PublicacionPerfil) event.getObject()));
      
        
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((PublicacionPerfil) event.getObject()).getIdPublicacion().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
   
    public void eliminarPublicacion(int id)
    {
        current.setIdPublicacion(id);
        ejbFacade.remove(current);
        
    }
    
    //////////////////////FIN EDITAR MANTENEDOR
      
    
    

    @Override
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    @Override
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
