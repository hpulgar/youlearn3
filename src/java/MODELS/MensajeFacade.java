/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Mensaje;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Zotindows
 */
@Stateless
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    
    
    public List<Mensaje> consultaChat(int idUsuario, int idAmigo)
    {
        EntityManager em3 = getEntityManager();
        Query q= em3.createNamedQuery("Mensaje.chat").setParameter("idUsuario",idUsuario).setParameter("idAmigo", idAmigo);
        
        
        return q.getResultList();
}
    
}
