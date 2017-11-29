/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.auth;

import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author thadeu
 */
@Stateless
public class ModuloFacade extends AbstractFacade<Modulo> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModuloFacade() {
        super(Modulo.class);
    }
    
    public List<Modulo> listaModulosSistema (Sistema sistema){
        try {
            List<Modulo> modulos = null;
            TypedQuery<Modulo> query;
            query = getEntityManager().createNamedQuery("Modulo.findModulosBySistema", Modulo.class).setParameter("sistema", sistema);
            modulos = query.getResultList();
            return modulos; 
        } catch (NoResultException e) {
            return null;
        }
                
                
    }
    
}
