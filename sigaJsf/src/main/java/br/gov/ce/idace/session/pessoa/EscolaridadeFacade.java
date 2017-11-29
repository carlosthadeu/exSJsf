/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.pessoa;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
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
public class EscolaridadeFacade extends AbstractFacade<Escolaridade> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EscolaridadeFacade() {
        super(Escolaridade.class);
    }
    
    public List<Escolaridade> EscolaridadeOrderByNome(){
        TypedQuery<Escolaridade> query = em.createQuery("select e from Escolaridade e order by e.escolaridade",Escolaridade.class);
        return query.getResultList();
    }
    
    public boolean haEscolaridade(String nomeEscolaridade){
        TypedQuery<Escolaridade> query = em.createQuery("select e from Escolaridade e where upper(e.escolaridade) =:nomeEscolaridade",Escolaridade.class);
        query.setParameter("nomeEscolaridade", nomeEscolaridade.toUpperCase());
        return !(query.getResultList().isEmpty());
    }
    
}
