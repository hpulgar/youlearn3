/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.Curso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import javax.persistence.Query;

/**
 *
 * @author Fenix III
 */
@Stateless
public class CursoFacade extends AbstractFacade<Curso> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }
    
    public List<Curso> verC(int idCurso)
    {
        EntityManager m2 = getEntityManager();
        Query q=m2.createNamedQuery("Curso.buscarIdCurso").setParameter("idCurso", idCurso);
        
        return q.getResultList();
    }
    
}