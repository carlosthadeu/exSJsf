/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.entity.assentamento.Assentado;
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
public class AssentadoFacade extends AbstractFacade<Assentado> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssentadoFacade() {
        super(Assentado.class);
    }
    
    public List<Assentado> findAssentadosByEscolaridade(Escolaridade escolaridade){
        TypedQuery query = em.createQuery("select a from Assentado a where a.escolaridade = :escolaridade", Assentado.class);
        query.setParameter("escolaridade", escolaridade);
        return query.getResultList();
    }
    
    public List<Assentado> findAssentadosByEstadoCivil(EstadoCivil estadoCivil){
        TypedQuery query = em.createQuery("select a from Assentado a where a.estadoCivil = :estadoCivil", Assentado.class);
        query.setParameter("estadoCivil", estadoCivil);
        return query.getResultList();
    }
}
