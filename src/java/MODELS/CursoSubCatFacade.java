/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.CursoSubCat;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Fenix III
 */
@Stateless
public class CursoSubCatFacade extends AbstractFacade<CursoSubCat> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoSubCatFacade() {
        super(CursoSubCat.class);
    }
    
    
    
    public List<CursoSubCat> Subcategorias(int idCategoria)
    {
        EntityManager m2 = getEntityManager();
        Query q=m2.createNamedQuery("CursoSubCat.findByidCat").setParameter("idCat",idCategoria);
        
        
        return q.getResultList();
    }
    
    
}
