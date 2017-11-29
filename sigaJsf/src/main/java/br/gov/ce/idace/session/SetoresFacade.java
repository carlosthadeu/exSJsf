/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session;

import br.gov.ce.idace.entity.auth.Setores;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcos.silva
 */
@Stateless
public class SetoresFacade extends AbstractFacade<Setores> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em; 
    }

    public SetoresFacade() {
        super(Setores.class);
    }

    
}
