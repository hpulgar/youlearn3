/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWS;

/**
 *
 * @author hpulgar.externo
 */

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
@Named("util")
@SessionScoped
public class Util implements Serializable {
    
     private boolean verCrear = false;
     private boolean tabla;
     
     public boolean getVerCrear() {
        return verCrear;
    }

    public void setVerCrear(boolean verCrear) {
        System.out.println("crear ? "+verCrear);
        this.verCrear = verCrear;
    }

    public boolean isTabla() {
        return tabla;
    }

    public void setTabla(boolean tabla) {
        this.tabla = tabla;
    }
    
    
 
      public static HttpSession getSession() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
       
      public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
 
      public static String getUserName()
      {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("username").toString();
      }
       
      public static String getUserId()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("id_usuario");
        else
            return null;
      }
      
      public static String getIdPerfil()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("id_perfil");
        else
            return null;
      }
      
      
      
      
      
      
      
      
      
}
