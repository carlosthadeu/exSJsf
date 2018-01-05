/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.Familia;
import br.gov.ce.idace.entity.assentamento.FamiliarAssentado;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author carlos.santos
 */
@Stateless
public class FamiliarAssentadoFacade extends AbstractFacade<FamiliarAssentado> {
    
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public FamiliarAssentadoFacade(){
        super(FamiliarAssentado.class);
    }
    
    public List<FamiliarAssentado> findByFamilia(Familia familia, Boolean ativos){
        String hql = "select fa from FamiliarAssentado fa where fa.familia =:familia ";
        if(ativos) hql = hql.concat(" and fa.ativo = true ");
        hql = hql.concat(" order by fa.nome");
        TypedQuery<FamiliarAssentado> query = em.createQuery(hql,FamiliarAssentado.class);
        query.setParameter("familia", familia);
        return query.getResultList();
    }
    public FamiliarAssentado findByCpf(String cpf){
        TypedQuery<FamiliarAssentado> query = em.createQuery("select fa from FamiliarAssentado fa where fa.cpf =:cpf  and fa.ativo = true",FamiliarAssentado.class);
        query.setParameter("cpf", cpf);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
