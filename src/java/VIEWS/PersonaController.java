package VIEWS;

import ENTITIES.Ciudad;
import ENTITIES.Persona;
import ENTITIES.Usuario;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.PersonaFacade;

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

@Named("personaController")
@SessionScoped
public class PersonaController implements Serializable {

    private Persona current;
    private DataModel items = null;
    @EJB
    private MODELS.PersonaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idPersona;
    private List<Persona> arPersona = new ArrayList();
    private List<Persona> arPersona2 = new ArrayList();
    private String modificar;

    public PersonaController() {
    }

    public String getModificar() {
        return modificar;
    }

    public void setModificar(String modificar) {
        this.modificar = modificar;
    }

    public List<Persona> getArPersona() {
        return arPersona;
    }

    public void setArPersona(List<Persona> arPersona) {
        this.arPersona = arPersona;
    }

    public List<Persona> getArPersona2() {
        return arPersona2;
    }

    public void setArPersona2(List<Persona> arPersona2) {
        this.arPersona2 = arPersona2;
    }

    
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    
    public Persona getSelected() {
        if (current == null) {
            current = new Persona();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PersonaFacade getFacade() {
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
        current = (Persona) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Persona();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Persona) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Persona) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonaDeleted"));
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
    
    
    
    /*
    
    
    int id_persona increment
    String nombre  pag (quitar ____)
    string apellido pag
    date fecha_nac pag (mayus)
    string ocupacion pag
    int id_ciudad XPARAMEtro
    boolean genero pag (arreglarlo)
    int id_usuario XPARAMETRo
    String descripcion
    
    
    */
    public String inforP(int idUsuario,String modificar)
    {
        
            if(!this.buscarPersona(idUsuario).isEmpty())
            {
                editP(idUsuario);
                this.setModificar("Bio.xhtml");
            }else
            {
                editP(idUsuario);
               this.setModificar("editarInfo.xhtml");
            }
       
        return this.getModificar();
        
    }
    
    
    
    public void updateP(int idUsuario,int idCity)
    {
        
        if(!this.buscarPersona(idUsuario).isEmpty())
        {
            this.setModificar("");
             editarPersona(idUsuario,idCity);
             
            
        }else
        {
            this.setModificar("");
            crearPersona(idUsuario,idCity);
           
        }
        
    }
    
     public List<Persona> verPersona()
    {
        List<Persona> arPersonas =ejbFacade.findAll();
       
        return arPersonas;
        
    }
     
     public void infop(int idUsuario)
     {
         current = ejbFacade.unaPersona(idUsuario);
     }
     
    
    
    public List<Persona> buscarPersona(int idUsuario)
    {
        arPersona.clear();
        arPersona2.clear();
        arPersona = ejbFacade.findAll();
        for(int i=0;i<arPersona.size();i++)
        {
            if(arPersona.get(i).getIdUsuario().getIdUsuario() == idUsuario)
            {
                arPersona2.add(arPersona.get(i));
            }
        }
        return arPersona2;
        
    }
    
    
    private void editP(int id_usuario)
    {
        if(!buscarPersona(id_usuario).isEmpty())
            {
                current = buscarPersona(id_usuario).get(0);
            }
            
    }
    
    
    public String crearPersona(int id_usuario, int id_ciudad)
    {
        System.out.println("ENTRA AL crear");
        try{
            
                        
            Ciudad oCi = new Ciudad();
            oCi.setIdCiudad(id_ciudad);
            
            Usuario oUs = new Usuario();
            oUs.setIdUsuario(id_usuario);
            
            current.setIdCiudad(oCi);
            current.setIdUsuario(oUs);
            
            getFacade().create(current);
            
            return "/perfil.xhtml";
        }catch(Exception e)
        {
            System.out.println("Error "+e);
            return "/perfil.xhtml";
        }
    }
    
    
    
    public String editarPersona(int id_usuario, int id_ciudad)
    {
        System.out.println("ENTRA AL EDITAR");
        try{
            
                        
            Ciudad oCi = new Ciudad();
            oCi.setIdCiudad(id_ciudad);
            
            Usuario oUs = new Usuario();
            oUs.setIdUsuario(id_usuario);
            
            current.setIdCiudad(oCi);
            current.setIdUsuario(oUs);
            
            getFacade().edit(current);
            
            return "/perfil.xhtml";
        }catch(Exception e)
        {
            System.out.println("Error "+e);
            return "/perfil.xhtml";
        }
    }
    
    
    

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Persona getPersona(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Persona.class)
    public static class PersonaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonaController controller = (PersonaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personaController");
            return controller.getPersona(getKey(value));
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
            if (object instanceof Persona) {
                Persona o = (Persona) object;
                return getStringKey(o.getIdPersona());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Persona.class.getName());
            }
        }

    }

}
