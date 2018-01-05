/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.UltimaComposicaoFamiliar;
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
public class UltimaComposicaoFamiliarFacade extends AbstractFacade<UltimaComposicaoFamiliar> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UltimaComposicaoFamiliarFacade() {
        super(UltimaComposicaoFamiliar.class);
    }

    public List<UltimaComposicaoFamiliar> findAll() {
        List<UltimaComposicaoFamiliar> composicoes = em.createNamedQuery("ListaUltimasComposicoes").getResultList();
        return composicoes;
    }

    public UltimaComposicaoFamiliar findPrincipalByCpf(String cpf) {
        TypedQuery<UltimaComposicaoFamiliar> query = em.createQuery("select cf from UltimaComposicaoFamiliar cf where cf.cpfPrincipal = :cpf", UltimaComposicaoFamiliar.class);
        query.setParameter("cpf", cpf);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {

            return null;
        }
    }
    
    public UltimaComposicaoFamiliar findConjugeByCpf(String cpf) {
        TypedQuery<UltimaComposicaoFamiliar> query = em.createQuery("select cf from UltimaComposicaoFamiliar cf where cf.cpfConjuge = :cpf", UltimaComposicaoFamiliar.class);
        query.setParameter("cpf", cpf);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {

            return null;
        }
    }
}
