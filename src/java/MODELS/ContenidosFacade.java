/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Contenidos;
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
public class ContenidosFacade extends AbstractFacade<Contenidos> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContenidosFacade() {
        super(Contenidos.class);
    }
    
    
    public List<Contenidos> verUnContenido(int idContenido)
    {
        EntityManager m2=getEntityManager();
        Query q = m2.createNamedQuery("Contenidos.verContenido").setParameter("idContenido", idContenido);
        return q.getResultList();
    }
    
    
    
}
