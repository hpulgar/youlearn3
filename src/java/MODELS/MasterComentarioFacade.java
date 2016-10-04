/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.MasterComentario;
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
public class MasterComentarioFacade extends AbstractFacade<MasterComentario> {
    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterComentarioFacade() {
        super(MasterComentario.class);
    }
    
    public List<MasterComentario> verC()//Meotod que retorna posteo
    {
         EntityManager m2 =  getEntityManager();
        Query q= m2.createNamedQuery("MasterComentario.findAll");
        
        return q.getResultList();
    }
    
    public List<MasterComentario> verComentario(int idPublicacion,int idPtf)
    {
         EntityManager m2 =  getEntityManager();
        Query q = m2.createNamedQuery("MasterComentario.buscaComentario").setParameter("idPublicacion", idPublicacion).setParameter("idPtf", idPtf);
        
        return q.getResultList();
    }
    
     public List<MasterComentario> verComentarioPorID(int idPtf)
    {
         EntityManager m2 =  getEntityManager();
        Query q = m2.createNamedQuery("MasterComentario.buscaComentarioPorID").setParameter("idPtf", idPtf);
        
        return q.getResultList();
    }
    
    
}
