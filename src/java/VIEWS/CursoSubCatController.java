package VIEWS;

import ENTITIES.CursoSubCat;
import VIEWS.util.JsfUtil;
import VIEWS.util.PaginationHelper;
import MODELS.CursoSubCatFacade;
import java.io.IOException;

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
import org.primefaces.event.SelectEvent;

@Named("cursoSubCatController")
@SessionScoped
public class CursoSubCatController implements Serializable {

    private CursoSubCat current;
    private DataModel items = null;
    @EJB
    private MODELS.CursoSubCatFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int id_cSub_cat;
    private String textoBusqueda;
    private List<CursoSubCat> arCursoSubCat = new ArrayList();
    private List<CursoSubCat> arCursoSubCat2 = new ArrayList();
    public CursoSubCatController() {
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public int getId_cSub_cat() {
        return id_cSub_cat;
    }

    public void setId_cSub_cat(int id_cSub_cat) {
        this.id_cSub_cat = id_cSub_cat;
    }

    public List<CursoSubCat> getArCursoSubCat() {
        return arCursoSubCat;
    }

    public void setArCursoSubCat(List<CursoSubCat> arCursoSubCat) {
        this.arCursoSubCat = arCursoSubCat;
    }

    public CursoSubCat getSelected() {
        if (current == null) {
            current = new CursoSubCat();
            selectedItemIndex = -1;
        }
        return current;
    }

    public List<CursoSubCat> getArCursoSubCat2() {
        return arCursoSubCat2;
    }

    public void setArCursoSubCat2(List<CursoSubCat> arCursoSubCat2) {
        this.arCursoSubCat2 = arCursoSubCat2;
    }

    private CursoSubCatFacade getFacade() {
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
        current = (CursoSubCat) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new CursoSubCat();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoSubCatCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CursoSubCat) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoSubCatUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CursoSubCat) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CursoSubCatDeleted"));
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

    public CursoSubCat getCursoSubCat(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    
    
    public List<CursoSubCat> listaSubCategorias(int id_categoria)
    {
        arCursoSubCat.clear();
        arCursoSubCat = ejbFacade.Subcategorias(id_categoria);
        
        
         return arCursoSubCat;
    }
    
        public List<CursoSubCat> listaSubCategoriasSinID()
    {
        arCursoSubCat.clear();
        arCursoSubCat = ejbFacade.findAll();
        
      
        
         return arCursoSubCat;
    }
        
        public List<String> autoCompletado(String query) {
            arCursoSubCat = ejbFacade.findAll();
             List<String> results = new ArrayList<String>();
        for(int i = 0; i < arCursoSubCat.size(); i++) {
            
            if(arCursoSubCat.get(i).getNomSubcat().regionMatches(0, query, 0, 3))
            results.add(arCursoSubCat.get(i).getNomSubcat());
        }
         
        return results;
    }
    
       
               public void clickCursoSelect(String idcategoria) throws IOException {
                System.out.println("******************************************");
                   System.out.println("valor categoria->"+idcategoria);
                    System.out.println("******************************************");
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("cursos_listado.xhtml");
    FacesContext.getCurrentInstance().responseComplete();

    
     
 
}
               
               public void handleSelect(SelectEvent event) {  
 String value = (String) event.getObject();
 System.out.println("selected "+value);

}
    
    
    @FacesConverter(forClass = CursoSubCat.class)
    public static class CursoSubCatControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursoSubCatController controller = (CursoSubCatController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursoSubCatController");
            return controller.getCursoSubCat(getKey(value));
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
            if (object instanceof CursoSubCat) {
                CursoSubCat o = (CursoSubCat) object;
                return getStringKey(o.getIdSubcat());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CursoSubCat.class.getName());
            }
        }

    }

}
