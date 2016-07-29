/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.PublicacionPerfil;
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
public class PublicacionPerfilFacade extends AbstractFacade<PublicacionPerfil> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicacionPerfilFacade() {
        super(PublicacionPerfil.class);
    }
    
    public List<PublicacionPerfil> PublicacionesPerfil(int idUsuario)
    {
        EntityManager em3 = getEntityManager();
        Query q= em3.createNamedQuery("PublicacionPerfil.aZote").setParameter("idUsuario",idUsuario);
        
        return q.getResultList();
    }
    
}
