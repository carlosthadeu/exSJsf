/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.Entrevistador;
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
 * @author thadeu
 */
@Stateless
public class EntrevistadorFacade extends AbstractFacade<Entrevistador> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntrevistadorFacade() {
        super(Entrevistador.class);
    }
    
    public List<Entrevistador> findEntrevistadorOrderByNome(){
        TypedQuery<Entrevistador> query = em.createQuery("select e from Entrevistador e order by e.nome",Entrevistador.class);
        return query.getResultList();
    }
    
    public Entrevistador findByCpf(String cpf){
        TypedQuery<Entrevistador> query = em.createQuery("select e from Entrevistador e where e.cpf = :cpf",Entrevistador.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult();
    }
    public String FindNomeByObjeto(Entrevistador entrevistador) {
        Query query = em.createQuery("select e.nome from Entrevistador e where e =:entrevistador");
        query.setParameter("entrevistador", entrevistador);
        return (String) query.getSingleResult();
    }
}
