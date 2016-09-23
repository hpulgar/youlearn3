package VIEWS;

import ENTITIES.Curso;
import ENTITIES.CursoSubCat;
import ENTITIES.Perfil;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.CursoFacade;
import java.io.IOException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
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
import org.primefaces.event.SelectEvent;

@Named("cursoController")
@SessionScoped
public class CursoController implements Serializable {

    private Curso current;
    private DataModel items = null;
    @EJB
    private MODELS.CursoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idCurso;
    private int idCursoSeleccionado;
    private int idSubcatSeleccionado;
   private String textoBusqueda;
   private String nombreCurso;
   
    private List<Curso> arCurso = new ArrayList();

    public List<Curso> getArCurso() {
        return arCurso;
    }

    public void setArCurso(List<Curso> arCurso) {
        this.arCurso = arCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    

    

    public CursoController() {
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
        
    }

    public int getIdCursoSeleccionado() {
        return idCursoSeleccionado;
    }

    public void setIdCursoSeleccionado(int idCursoSeleccionado) {
        this.idCursoSeleccionado = idCursoSeleccionado;
    }

    public int getIdSubcatSeleccionado() {
        return idSubcatSeleccionado;
    }

    public void setIdSubcatSeleccionado(int idSubcatSeleccionado) {
        this.idSubcatSeleccionado = idSubcatSeleccionado;
    }
    
    
    
    public String submit()
{
   
    // blank out the value of the name property
    textoBusqueda = null;
    idCursoSeleccionado = 0;
    // send the user back to the same page.
    return null;
}

   

    
    
    public Curso getSelected() {
        if (current == null) {
            current = new Curso();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CursoFacade getFacade() {
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
        current = (Curso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Curso();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Curso) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Curso) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoDeleted"));
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

    public Curso getCurso(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    ////////////////////////////////CURSO///////////////////////////////////////////////////
        
        // INT IDCURSO
        //INT ID SUBCAT
        //ID_USUARIO
        //NOMBRE_CURSO
        //PERSONAS_INSCRITAS
        //DESCRIPCION_CURSO
        //AUTORIZADO 
        //CONTENIDOS
        //SEGUIDORES
        //INTRODUCCION
        //IMAGEN
        
        
        
        public List<Curso> listaCursos(int idSubcat,String nombreCurso)
        {
            System.out.println("ENTRA AL METODO");
            
                if(idSubcat != 0 && nombreCurso.isEmpty())
                {
                     System.out.println("ENTRO AL IF Q DEVUELVE SEGUN EL ID"+idSubcat);
                    
                    arCurso.clear();
                    arCurso = ejbFacade.cursosSubcategorias(idSubcat);
                }else if(!nombreCurso.isEmpty()  && idSubcat == 0)
                {
                    System.out.println("ENTRO AL IF Q DEVUELVE SEGUN EL NOMBRE"+nombreCurso);
                    arCurso.clear();
                    arCurso= ejbFacade.cursosNombres(nombreCurso);
                }else
                {
                   System.out.println("ENTRO AL IF Q DEVUELVE TODO COTITO");
                    this.arCurso.clear();
                    arCurso = ejbFacade.findAll();
            
                    
                }
            
            return arCurso;
        }
        
        public List<Curso> verCurso(int idCurso)
        {
            arCurso.clear();
            arCurso = ejbFacade.verC(idCurso);
            return arCurso;
        }
        
        public String nomcurso(int idCurso)
        {
            
            return ejbFacade.nombreC(idCurso);
        }
        
        public String cursoCont()
        {
            return "/curso.xhtml";
        }
        
        public String irTablero(int idCurso)
        {
            this.setIdCurso(idCurso);
            return "/tablero.xhtml";
        }
        
          public String verCurs(int idCurso)
        {
            this.setIdCurso(idCurso);
            return "/detalles_curso.xhtml";
        }
        
        public String CrearCurso(int id_scat_curso,int id_usuario)
        {
            try{
                
        // 1 INT IDCURSO automatico x
        // 2 INT ID SUBCAT por parametro X
        // 3 ID_USUARIO por parametro  X
        // 4 NOMBRE_CURSO selecterd X
        // 5 PERSONAS_INSCRITAS defecto 0 X
        // 6 DESCRIPCION_CURSO selected X
        // 7 AUTORIZADO  por defecto 0 X
        // 8 CONTENIDOS selected X
        // 9 SEGUIDORES por defecto 0 X
        // 10 INTRODUCCION selected X
        // 11 IMAGEN selected
                
        
        ///////AGREGAR FECHA
                
                CursoSubCat csc = new CursoSubCat();
                csc.setIdSubcat(id_scat_curso);
        
                Usuario ou = new Usuario();
                ou.setIdUsuario(id_usuario);
                
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
//                Date date = new Date();
//                String fecha = dateFormat.format(date);
//                
                
                
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.setTimeZone(new SimpleTimeZone(-3, "GMT"));
                sdf.applyPattern("yyyy/mm/dd");
                Date fecha = new Date();

             
                current.setFecha(fecha);
                
               
                current.setIdUsuario(ou);
                current.setIdCat(csc);
                current.setAutorizado(false);
                current.setSeguidores(0);
                current.setPersonasInscritas(0);
                getFacade().create(current);
                current = null;

                return "/cursos_listado.xhtml";//momentaneo

                }catch(Exception e)
                {
                    System.out.println("EL ERRORR"+ e);
                    return "/curso_crear.xhtml";
                }
        }
        
         public List<String> autoCompletado(String query) {
            arCurso = ejbFacade.findAll();
             List<String> results = new ArrayList();
            for(int i = 0; i < arCurso.size(); i++) {
            
            if((arCurso.get(i).getNomCurso().regionMatches(0, query, 0, 1)) || (arCurso.get(i).getNomCurso().regionMatches(0, query.toUpperCase(), 0, 1)) ){
            results.add(arCurso.get(i).getNomCurso());
            idCursoSeleccionado =arCurso.get(i).getIdCurso();
            }
            
          //  cur.setIdCurso(arCurso.get(i).getIdCurso());
            
        }
         System.out.println("Nombre Curso"+results.toString());
          System.out.println("ID Curso "+idCursoSeleccionado);
        return results;
    }
         
         
         
         public void clickCursoCompletado() throws IOException {
    // ...
     this.setIdCurso(idCursoSeleccionado);
     if(this.getIdCursoSeleccionado()!=0){
     System.out.println(this.getIdCursoSeleccionado());
    FacesContext.getCurrentInstance().getExternalContext().redirect("detalles_curso.xhtml");
    FacesContext.getCurrentInstance().responseComplete();

    }
     
 
}
         
        
        
    @FacesConverter(forClass = Curso.class)
    public static class CursoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursoController controller = (CursoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursoController");
            return controller.getCurso(getKey(value));
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
            if (object instanceof Curso) {
                Curso o = (Curso) object;
                return getStringKey(o.getIdCurso());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Curso.class.getName());
            }
        }

    }

}
