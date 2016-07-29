/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;


import ENTITIES.MasterRespuestas;
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
public class MasterRespuestasFacade extends AbstractFacade<MasterRespuestas> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterRespuestasFacade() {
        super(MasterRespuestas.class);
    }
    
    public List<MasterRespuestas> verRespuestas(int idComentario)
    {
         EntityManager m2 =  getEntityManager();
        Query q = m2.createNamedQuery("MasterRespuestas.buscarIdComentario").setParameter("idComentario", idComentario);
        
        return q.getResultList();
    }
}
