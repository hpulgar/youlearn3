/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELS;

import ENTITIES.PreguntasData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Felipe
 */
@Stateless
public class PreguntasDataFacade extends AbstractFacade<PreguntasData> {

    @PersistenceContext(unitName = "YoulearnPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntasDataFacade() {
        super(PreguntasData.class);
    }
    
}
