/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.pessoa;


import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author carlos.santos
 */
@Stateless
public class EstadoCivilFacade extends AbstractFacade<EstadoCivil> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoCivilFacade() {
        super(EstadoCivil.class);
    }
    public List<EstadoCivil> EstadoCivilOrderByNome(){
        TypedQuery<EstadoCivil> query = em.createQuery("select e from EstadoCivil e order by e.estadoCivil",EstadoCivil.class);
        return query.getResultList();
    }
    
    public boolean haEstadoCivil(String nomeEstadoCivil){
        TypedQuery<EstadoCivil> query = em.createQuery("select e from EstadoCivil e where upper(e.estadoCivil) =:nomeEstadoCivil",EstadoCivil.class);
        query.setParameter("nomeEstadoCivil", nomeEstadoCivil.toUpperCase());
        return !(query.getResultList().isEmpty());
    }
}
