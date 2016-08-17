/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Fenix III
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
        public List<Usuario> log(String name,String pass)
    {
        EntityManager em2 = getEntityManager();
        Query q = em2.createNamedQuery("Usuario.logIn")
                .setParameter("username", name)
                .setParameter("password", pass);
    
        return q.getResultList();
    
    }
       
    public List <Usuario> cargaPerfiles(int id)
     {
         EntityManager em2 = getEntityManager();
         Query q = em2.createNamedQuery("Usuario.findByIdUsuario").setParameter("idUsuario", id);
         return q.getResultList();
         
     }   
       
    public List<Usuario> cargaUsername(int idp)
    {
        EntityManager em3 = getEntityManager();
        Query q= em3.createNamedQuery("Usuario.findByIdUsuario").setParameter("idUsuario",idp);
        return q.getResultList();
                
    }
    
    public String username(int idUsuario)
    {
        EntityManager em3 = getEntityManager();
        Query q= em3.createNamedQuery("Usuario.username").setParameter("idUsuario",idUsuario);
        
        return q.toString();
        
    }
    
}
