/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.auth;


import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos.Santos
 */
@Stateless
public class SistemaFacade extends AbstractFacade<Sistema> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaFacade() {
        super(Sistema.class);
    }
    
    public void removeModulo(Modulo modulo, Sistema sistema){
        modulo.setSistema(null);
        sistema.getModulos().remove(modulo);
        em.merge(sistema);
        em.flush();
        em.clear();
    }
    
}
