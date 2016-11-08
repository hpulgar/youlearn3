package VIEWS;

import ENTITIES.Curso;
import ENTITIES.InscripcionCurso;
import ENTITIES.TipoAlumno;
import ENTITIES.Usuario;
import ENTITIES.PublicacionPerfil;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.InscripcionCursoFacade;
import VIEWS.PublicacionPerfilController;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

@Named("inscripcionCursoController")
@SessionScoped
public class InscripcionCursoController implements Serializable {

    private InscripcionCurso current;
    private DataModel items = null;
    @EJB
    private MODELS.InscripcionCursoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String estadoS;

    public InscripcionCursoController() {
    }

    public String getEstadoS() {
        return estadoS;
    }

    public void setEstadoS(String estadoS) {
        this.estadoS = estadoS;
    }

    public InscripcionCurso getSelected() {
        if (current == null) {
            current = new InscripcionCurso();
            selectedItemIndex = -1;
        }
        return current;
    }

    private InscripcionCursoFacade getFacade() {
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
        current = (InscripcionCurso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new InscripcionCurso();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionCursoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (InscripcionCurso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionCursoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (InscripcionCurso) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionCursoDeleted"));
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

    public InscripcionCurso getInscripcionCurso(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    /*//////////////////////////////////////////////////////////////
    cuando un usuario sigue un curso lo esta siguiendo, y cuando un usuario que ya sigue un curso le pone subscribir al curso, vamos a editar el campo tipo usuario de seguidor a suscriptor 
    y asi no tenemos conflicto al momento de contar los seguidores
    bykk
    //////////////////////////////////////////////////////////////////*/
    
    public String inscribirAlCurso(int idUser,int idCurso,int idTipoAlumno)
    {
        //int id_insc; AutoIncrement
        //int id_usuario; Parametro
        //int id_curso; Parametro 
        //Date fecha_insc; se Define ACA
        //String descrp; Se DefineACA(que se sho)
        //int tipo_alumno; Parametro(Dependiendo del boton q Aprete)
        
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
            
            Usuario ou = new Usuario();
            ou.setIdUsuario(idUser);
            
            Curso oc = new Curso();
            oc.setIdCurso(idCurso);
            
            TipoAlumno ota = new TipoAlumno();
            ota.setIdTipo(idTipoAlumno);
            
            InscripcionCurso oic = new InscripcionCurso();
            
            oic.setIdUsuario(ou);
            oic.setIdCurso(oc);
            oic.setFechaInsc(dateFormat.parse(fecha));
            oic.setDescripcion("y q se sho");
            oic.setTipoAlumno(ota);
//            System.out.println("id u "+oic.getIdUsuario().getIdUsuario());
//            System.out.println("id c "+oic.getIdCurso().getIdCurso());
//            System.out.println("fecfha " +oic.getFechaInsc());
//            System.out.println("desc "+oic.getDescripcion());
//            System.out.println("tipo a "+oic.getTipoAlumno().getIdTipo());
            ejbFacade.create(oic);
            System.out.println("ESTA COSI3 CONFIMA LA CREACION");
            
            return "/curso.xhtml";
            
        }catch(Exception e)
        {
            System.out.println("EL ERROR AL CREAR LA INSCRIPCION ES EL SIGUIENTE: "+e+" Si, no se entiende ninguna wea =P");
            return "/detalles_curso.xthml";
        }
        
     //return "byyk";   
    }
    
    
    
    public int seguidores(int idCurso)
    {
        int seguidores = 0;
        List<InscripcionCurso> ars = ejbFacade.findAll();
        
        for(int i=0;i<ars.size();i++)
        {
            if(ars.get(i).getIdCurso().getIdCurso()== idCurso)
            {
                if(ars.get(i).getTipoAlumno().getIdTipo() == 1)
                {
                    seguidores++;
                }
            }
        }
        
        return seguidores;
        
    }
    
    public int suscriptores(int idCurso)
    {
        int suscriptores = 0;
        List<InscripcionCurso> ars = ejbFacade.findAll();
        
        for(int i=0;i<ars.size();i++)
        {
            if(ars.get(i).getIdCurso().getIdCurso()== idCurso)
            {
                if(ars.get(i).getTipoAlumno().getIdTipo() == 2)
                {
                    suscriptores++;
                }
            }
        }
        
        return suscriptores;
        
    }
    
    
     public String cargarCurso(int idc,int idus)
        {
                        
            String pagina ="";
            
            //Obtengo arraylist con datos del curso
            
            List<InscripcionCurso> ars = new ArrayList();
            ars.clear();
            ars= ejbFacade.findAll();
            
  
            if(!ars.isEmpty())
            {
                for(int i=0;i<ars.size();i++)
                {
                      System.out.println("Entro al FOR con valores de idc = "+idc+" y id usuario "+idus);
                    //Si cumple las condiciones,entonces...
                    if((ars.get(i).getIdCurso().getIdCurso()==idc && ars.get(i).getIdUsuario().getIdUsuario()== idus))
                    {
                        System.out.println("Entro al IF");
                        //Al cumplir las condiciones(usuario inscrito),nos llevara directamente al curso.
                        //setIdCurso(idc);
                        pagina= "/curso.xhtml";
                        break;

                    } 
                    else
                   {
                         System.out.println("Entro al ELSE");
                                   // Si no, mostrara el detalle del curso
                        //setIdCurso(idc);
                        pagina= "/detalles_curso.xhtml";
                    }
                }
            }else
            {
                pagina="/detalles_curso.xhtml";
              
            }
            System.out.println("Salgo del for con el valor de pagina = "+pagina);
            return pagina;
        }
     
     public String nboton(int idCurso,int idUs)
     { 
    
         String estado="";
         
         if(suscripcionCurso(idCurso,idUs)==true)
         {
             estado="Suscribir";
             
         }
         else
         {
             estado="Suscrito";
         }
             
        return estado;
        
     }
     
    public boolean suscripcionCurso(int idCurso,int idUs)
    {
        boolean suscrito=false;
        List<InscripcionCurso> ars = new ArrayList();
        ars.clear();
        ars= ejbFacade.findAll();
          for(int i=0;i<ars.size();i++)
            {
                //Si cumple las condiciones,entonces...
                if((ars.get(i).getIdUsuario().getIdUsuario() == idUs && ars.get(i).getIdCurso().getIdCurso()== idCurso && ars.get(i).getTipoAlumno().getIdTipo()==1))
                {
                    this.setEstadoS("Suscribir");
                    current = ars.get(i);
                    suscrito=true;  
                } 
                else
               {       
                   this.setEstadoS("Suscrito");
                   suscrito=false;
                }
            }
        
        return suscrito;
    }    
    
    
    public void crearSuscripcion()
    {
        try{
            if(current != null)
            {
                System.out.println("Entra al try para comenzar la suscripcion del curso");
                TipoAlumno ta = new TipoAlumno();
                ta.setIdTipo(2);
                current.setTipoAlumno(ta);
                ejbFacade.edit(current);
                
                
//                PublicacionPerfilController a = new PublicacionPerfilController();
//                a.crearPublicacionSuscribirCurso(1,"asd");
                
            }
        }catch(Exception e)
        {
            System.out.println("Error al crear suscripcion = "+e);
        }
    }
    
    
    
    ///////////////////////////////INICIO MANTENEDOR
    
    
    public List<InscripcionCurso> tablaInscripcion()
    {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Inscripcion Editada",((InscripcionCurso) event.getObject()).getIdInsc().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        current.setIdInsc((((InscripcionCurso) event.getObject()).getIdInsc()));
        ejbFacade.edit(current);
        current= null;
        
    }
    
    public void onborrar(RowEditEvent event)
    {
        
        current = ((InscripcionCurso) event.getObject());
        ejbFacade.remove(current);
        current= null;
        
    }
    
    
    public void onRowCancel(RowEditEvent event)
    {
        FacesMessage msg = new FacesMessage("Edicion Cancelada",((InscripcionCurso) event.getObject()).getIdInsc().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearInscripciones()
    {
        try{
        ejbFacade.create(current);
        current = null;
        }catch(Exception e){
        System.out.println("Error al crear la inscripcion "+e);
        }
    }
    
    public void eliminarInscripcion(int id)
    {
       
       current.setIdInsc(id);
       System.out.println("id a eliminar "+current.getIdInsc());
       ejbFacade.remove(current);
       current = null;
      
    }
    
    
    public String verDescripcion(int id)
    {
        return "descInsc"+id;
    }
       
    
    public void cargaDatos(int id)
    {
        
        current = ejbFacade.find(id);
        
    }
    
    
    public void prepararCrear()
    {
        current = null;
    }
    //////////////////////////////////////////////
    @FacesConverter(forClass = InscripcionCurso.class)
    public static class InscripcionCursoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InscripcionCursoController controller = (InscripcionCursoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inscripcionCursoController");
            return controller.getInscripcionCurso(getKey(value));
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
            if (object instanceof InscripcionCurso) {
                InscripcionCurso o = (InscripcionCurso) object;
                return getStringKey(o.getIdInsc());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + InscripcionCurso.class.getName());
            }
        }

    }

}
