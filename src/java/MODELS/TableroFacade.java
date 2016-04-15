/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Tablero;
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
public class TableroFacade extends AbstractFacade<Tablero> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TableroFacade() {
        super(Tablero.class);
    }
    
    public List<Tablero> verT(int idTab)
    {
        EntityManager t2= getEntityManager();
        Query q = t2.createNamedQuery("Tablero.verTablero").setParameter("idTablero", idTab);   
        
       return q.getResultList(); 
    }
    
}
