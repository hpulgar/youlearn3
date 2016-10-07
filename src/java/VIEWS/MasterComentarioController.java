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

@Named("masterComentarioController")
@SessionScoped
public class MasterComentarioController implements Serializable {

    private MasterComentario current;
    private List<MasterComentario> comentariosForo = new ArrayList();     
    private List<MasterComentario> comentarios;
    private DataModel items = null;
    @EJB
    private MODELS.MasterComentarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idCom;
    private int cuenta;
    private int bigId;
    private boolean mostrar = false;
    private String comentario2;

    public List<MasterComentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<MasterComentario> comentarios) {
        this.comentarios = comentarios;
    }

    
    
    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    
    public int getBigId() {
        return bigId;
    }

    public void setBigId(int bigId) {
        this.bigId = bigId;
    }    

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }
    
    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public String getComentario2() {
        return comentario2;
    }

    public void setComentario2(String comentario2) {
        this.comentario2 = comentario2;
    }

    
    
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
    
    //CO    
    public String nombreForm(int numero)
    {
       System.out.println("form"+numero);
        return "form"+numero;
        
    }
    
    public String nombreForm2(int numero)
    {
        
        return "Segundo"+numero;
        
    }
    
    public String nombreForm3(int numero)
    {
       
        return "grid"+numero;
    }
        
    public String nombreForm4(int numero)
    {
       
        return "acc"+numero;
    }
    
    public String nombreForm5(int numero)
    {
       
        return "Fifth"+numero;
    }
    public String nombreForm6(int numero)
    {
       
        return "SextoF"+numero;
    }
    
    public String nombreInput(int numero)
    {
        //System.out.println("inpCom"+numero);
        return "inpCom"+numero;
        
    }
    public String nombreInput2(int numero)
    {
        //System.out.println("inpCom"+numero);
        return "inpRes"+numero;
        
    }
    public String nombreBoton(int numero)
    {
        
        return "btoUno"+numero;
        
    }
    public String nombreBoton2(int numero)
    {
       
        return "btoDos"+numero;
        
    }
    
    
    
    
    public boolean mostrarComentario(boolean asd)
    {
        this.setMostrar(asd);
        return this.isMostrar();
    }
    
    public void CrearComentario(int idComentador,int idPublicacion,int pft){
        System.out.println("----------->Entra<-----------------");
        
        try
        {

            //id comentario no va
            //id_pft viene por parametro --
            //comentario se define en xhtml
            //fecha comentario se moldea --
            //id usuario viene por parametro --
            //id publicacion viene por parametro --

            //MasterComentario mc = new MasterComentario();
            //mc.setIdPublicacion(idPublicacion);
            //mc.setComentario(comentario);
            
            
            
            Usuario ou = new Usuario();
            ou.setIdUsuario(idComentador);
            MasterPft mpft =  new MasterPft();
            mpft.setIdPft(pft);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);

 
            System.out.println("en el id pft"+ pft);
            System.out.println("en id us"+ idComentador);
            System.out.println("en id pub"+ idPublicacion);
            
            current.setIdPft(mpft);
            //current.setComentario(comentario);
            current.setFechaComentario(dateFormat.parse(fecha));
            current.setIdUsuario(ou);
            current.setIdPublicacion(idPublicacion);
            
           
            

            if(current.getComentario() != null)
            {
                getFacade().create(current);
                this.current = null;
                System.out.println(" Crea el Comentario");
            }
            else 
            {
                System.out.println(" no Crea el comentario");
            }

           // return "/blog-single.xhtml";

        }catch(Exception e){

            System.out.println("Si tira error es este --> "+e);
            //return "/blog-single.xhtml";}
        }    
    }
    public void CrearComentario2(int idComentador,int idPublicacion,int pft,String coment){
        
        try
        {

            //id comentario no va
            //id_pft viene por parametro --
            //comentario se define en xhtml
            //fecha comentario se moldea --
            //id usuario viene por parametro --
            //id publicacion viene por parametro --

            MasterComentario mc = new MasterComentario();
            //mc.setIdPublicacion(idPublicacion);
            //mc.setComentario(comentario);
            Usuario ou = new Usuario();
            ou.setIdUsuario(idComentador);
            MasterPft mpft =  new MasterPft();
            mpft.setIdPft(pft);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);

            
            System.out.println(" comentario   "+coment);
            mc.setIdPft(mpft);//1
            mc.setFechaComentario(dateFormat.parse(fecha));//2
            mc.setIdUsuario(ou);//3
            mc.setIdPublicacion(idPublicacion);//4
            mc.setComentario(coment);//5

            
            System.out.println(" comentario   "+current.getComentario());
            System.out.println(" ptf   "+mc.getIdPft().getIdPft());
            System.out.println(" idPublicacion   "+mc.getIdPublicacion());
            System.out.println(" idComentador   "+mc.getIdUsuario().getIdUsuario());
            System.out.println(" fecha   "+mc.getFechaComentario());
            
            
            if(mc.getComentario() != null)
            {   
                System.out.println(" LO CREA");
                getFacade().create(mc);
                mc = null;
            }else
            {
                System.out.println("NO lo CREA");
            }
            
           //return "/peril.xhtml";
        }catch(Exception e){

            System.out.println("error en el comentario --> "+e);
            //return "/perfil.xhtml";
        }    
    }
    
    
    
    
    public String insertForo(int idComentador,int idPublicacion,int pft)
    {        
        this.CrearComentario(idComentador, idPublicacion, pft);
        return "/blog-single.xhtml";
    }
    
//    public List<MasterComentario> cargaComentarios(int idPublicacion,int idPtf)
//    {        
//        List<MasterComentario> ob = new ArrayList() ;   
//        comentariosForo.clear();
//        ob.clear();
//        
//        comentariosForo = ejbFacade.findAll();                
//        for(int i=0;i<comentariosForo.size();i++)
//        {
//            if((comentariosForo.get(i).getIdPft().getIdPft()==idPtf) &&(comentariosForo.get(i).getIdPublicacion()==idPublicacion))
//                {
//                   //System.out.println(" comentario>>>> "+comentariosForo.get(i));
//                   this.setBigId(comentariosForo.get(i).getIdComentario());
//                   ob.add(comentariosForo.get(i));
//                } 
//        }
//        
//        
//        this.setCuenta(ob.size());
//        
//        return ob;
//    }
    
    
    public List<MasterComentario> cargaComentarios(int idPublicacion,int idPtf)
    {        

        comentarios = ejbFacade.verComentario(idPublicacion, idPtf);

        
        this.setCuenta(comentarios.size());
        
        return comentarios;
    }
    
    public String mensajeComentarios(int idPublicacion,int idPtf)
    {
        comentarios = ejbFacade.verComentario(idPublicacion, idPtf);
       
        
        return "Mostrar "+comentarios.size()+" Comentarios Ocultos";
    }
    
    public boolean mostrarAcc(int idPublicacion,int idPtf)
    {
        comentarios = ejbFacade.verComentario(idPublicacion, idPtf);
        if(comentarios.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public int cuentaComentarios()
    {   
        return this.getCuenta();   
    }
    
    public int cuentaMensajes(int idpost)
    {   
        int c=0;
        comentariosForo.clear();
        comentariosForo = ejbFacade.findAll();
        
        for(int i=0;i<comentariosForo.size();i++)
        {
            if(comentariosForo.get(i).getIdPublicacion() == idpost && comentariosForo.get(i).getIdPft().getIdPft() == 2)
            {
                c = c+1;
            }
        }
        return c;  
    }
     
    public int mayorIdComentarios()
    {   
       // System.out.println("QUIERO IMPRIMIR EL MAYOR ID: "+this.getBigId());
        return this.getBigId();   
    }
    
    
    public String ShowResponse(int i)
    {
        String a = "response"+i;
        //System.out.println("IMPRIMIENDO Response: "+a);
        
        return a;
    }
    
    public String ShowResponder(int i)
    {
            String b = "responder"+i;
           // System.out.println("IMPRIMIENDO RESPONDER: "+b);
            
        return b;
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


/* 
               function responder(var largo)
               {
                   for(var i=0;i<5;i++)
                  {
                        var a ="responder"+i;                          
                  }
                   return a;
               }
               
               function responder(var largo)
               {
                   for(var i=0;i<5;i++)
                  {
                        var b ="response"+i;
                          
                  }
                   return b;
               }*/

//$("#responder").click(function() {
          //           $("#responder").hide();
            //         $("#response").show();
              //    });