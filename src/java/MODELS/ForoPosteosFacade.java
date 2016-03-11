/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.ForoPosteos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Felipe
 */
@Stateless
public class ForoPosteosFacade extends AbstractFacade<ForoPosteos> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ForoPosteosFacade() {
        super(ForoPosteos.class);
    }
    
     public List<ForoPosteos> verP(int idPost)//Meotod que retorna posteo
    {
         EntityManager m2 =  getEntityManager();
        Query q= m2.createNamedQuery("ForoPosteos.verPost").setParameter("idPost", idPost);
        return q.getResultList();
}
    
}
