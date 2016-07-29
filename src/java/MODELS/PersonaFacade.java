/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Persona;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Felipe
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
    
    public Persona unaPersona(int idUsuario)
    {
        Persona obP = new Persona();
        
        EntityManager m2=getEntityManager();
        Query q = m2.createNamedQuery("Persona.buscarUsuario").setParameter("idUsuario", idUsuario);
        
        return obP = (Persona) q.getSingleResult();
    }
    
    
    
}
