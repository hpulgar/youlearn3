package VIEWS;

import ENTITIES.ForoPosteos;
import ENTITIES.ForoSubcategoria;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.ForoPosteosFacade;

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

@Named("foroPosteosController")
@SessionScoped
public class ForoPosteosController implements Serializable {

    private ForoPosteos current;
    private DataModel items = null;
    @EJB
    private MODELS.ForoPosteosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<ForoPosteos> carga = new ArrayList();
    private List<ForoPosteos> cargaNoticias = new ArrayList();
    private int idPosteo;
    private List<ForoPosteos> arForo = new ArrayList();
    private List<ForoPosteos> carga2 = new ArrayList();
    private String tit;

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public List<ForoPosteos> getArForo() {
        return arForo;
    }

    public void setArForo(List<ForoPosteos> arForo) {
        this.arForo = arForo;
    }

    public int getIdPosteo() {
        return idPosteo;
    }

    public void setIdPosteo(int idPosteo) {
        this.idPosteo = idPosteo;
    }

    public ForoPosteosController() {
    }

    public ForoPosteos getSelected() {
        if (current == null) {
            current = new ForoPosteos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ForoPosteosFacade getFacade() {
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
        current = (ForoPosteos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ForoPosteos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ForoPosteosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ForoPosteos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ForoPosteosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ForoPosteos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ForoPosteosDeleted"));
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
    ///////////////////////////////////////////////////////////////////////////////
    public List<ForoPosteos> cargaUno(int id)
    {
        carga.clear();
        carga = ejbFacade.verP(id);
        return carga;
    }
     
     
     public String nombrePost(int id)
     {
         return ejbFacade.verP(idPosteo).get(0).getTitulo();
     }
     
     
     
     public void verUno(int id)
     {
          ForoPosteos fp = ejbFacade.verP(id).get(0);
          current = fp;
     }
        
     public List<ForoPosteos> cargaTodos()
         {
             carga.clear();
             carga=ejbFacade.findAll();
             carga2.clear();
             for(int i=0;i<carga.size();i++)
             {
                 if(carga.get(i).getAutorizado() == true)
                 {
                     carga2.add(carga.get(i));
                 }
                 
             }
             
             
             return carga2;
        
         }
         
    public String verPost(int id)//metodo para cambiar de pagina USAR CON ¡¡¡¡COMMANDLINK!!!!
    {
             setIdPosteo(id);
             return "/blog-single.xhtml";
    }

         
         
    public String crearForo(int id_subcategoria,int id_usuario)
    {
        //  id Post - autoincrement -OK
        //  id_subcategoria - parametrizado(tambien puede ser rescatado)-OK
        //  id usuario - parametrizado -OK
        //  titulo - se rescata
        //  contenido se rescata
        //  fecha - automatica -OK
        //  autorizado - predefinido en 0 -OK
        //  imagen_foro_posteo - se rescata
        
        try{
        ForoSubcategoria fsc = new ForoSubcategoria();
        fsc.setIdSubcategoria(id_subcategoria);
        Usuario ou = new Usuario();
        ou.setIdUsuario(id_usuario);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        Date date = new Date();
        String fecha = dateFormat.format(date);
        
        System.out.println("el id subcat "+id_subcategoria);
        
        current.setFecha(dateFormat.parse(fecha));
        current.setIdUser(ou);
        current.setIdSubcategoria(fsc);
        current.setAutorizado(false);
        //current.getIdPost();
        
        getFacade().create(current);
        getFacade().find(ou).getIdPost();
        current = null;
        
        return "/foro.xhtml";
        
        }catch(Exception e)
        {
            System.out.println("EL ERRORR"+ e);
            return "/foro_crear.xhtml";
        }
            
    }
    
       public List<ForoPosteos> verPublicaciones(int idPost)
    {
      
        
        arForo.clear();
        arForo =ejbFacade.findAll();
        List<ForoPosteos> arForo2 = new ArrayList();
        
        
        for(int i= arForo.size()-1 ; i >= 0 ;i--)
        {
            if(arForo.get(i).getIdPost() == idPost)
            {
//                if(arPerfil.get(i).getIdTipoPublicacion().getIdTipoPublicacion() == 2)
//                {
//                   this.paginaRedirect = "detalles_curso.xhtml";
//                   this.metodoRedirect = "cursoController.setIdCurso("+arPerfil.get(i).getLinkPublicacion()+")";
//                   
//                }else if(arPerfil.get(i).getIdTipoPublicacion().getIdTipoPublicacion() == 3)
//                {
//                    this.paginaRedirect = "blog-single.xhtml";
//                    CursoController cc = new CursoController();
//                    cc.setIdCurso(Integer.parseInt(arPerfil.get(i).getLinkPublicacion()));
              arForo2.add(arForo.get(i));
            }
        }
        return arForo2;
        
    }
   public List<ForoPosteos> listaForos(String tituloForo,int idSubcat)
    {
        System.out.println("ENTRA AL METODO");

            if(idSubcat != 0 && tituloForo.isEmpty())
            {
                 System.out.println("ENTRO AL IF Q DEVUELVE SEGUN EL ID"+idSubcat);

                arForo.clear();
                arForo = ejbFacade.foroSubcategoria(idSubcat);
            }else if(!tituloForo.isEmpty()  && idSubcat == 0)
            {
                System.out.println("ENTRO AL IF Q DEVUELVE SEGUN EL NOMBRE"+tituloForo);
                arForo.clear();
                arForo= ejbFacade.nombreForo(tituloForo);
            }
            else if(!tituloForo.isEmpty() && idSubcat != 0)
            {
                System.out.println("Entro al IF que devuelve segui nombre y idsubcat");
                arForo.clear();
                arForo=ejbFacade.nombreYsubcategoria(tituloForo, idSubcat);
            }else
            {
               System.out.println("ENTRO AL IF Q DEVUELVE TODO COTITO");
                this.arForo.clear();
                arForo = ejbFacade.findAll();


            }

        return arForo;
    }
       
       
       
       
       
       
       
       
       
       
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ForoPosteos getForoPosteos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ForoPosteos.class)
    public static class ForoPosteosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ForoPosteosController controller = (ForoPosteosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "foroPosteosController");
            return controller.getForoPosteos(getKey(value));
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
            if (object instanceof ForoPosteos) {
                ForoPosteos o = (ForoPosteos) object;
                return getStringKey(o.getIdPost());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ForoPosteos.class.getName());
            }
        }

    }

}
