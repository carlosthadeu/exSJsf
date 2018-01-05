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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    public Boolean findAssentadosByEscolaridade(Escolaridade escolaridade){
        Query query = em.createQuery("select count(*) from Assentado a where a.escolaridade = :escolaridade");
        query.setParameter("escolaridade", escolaridade);
        Integer qtd = (Integer) query.getSingleResult();
        return qtd >0;
    }
    
    public Boolean findAssentadosByEstadoCivil(EstadoCivil estadoCivil){
        Query query = em.createQuery("select count(*) from Assentado a where a.estadoCivil = :estadoCivil");
        query.setParameter("estadoCivil", estadoCivil);
        Integer qtd = (Integer) query.getSingleResult();
        return qtd >0;
    }
    
}
