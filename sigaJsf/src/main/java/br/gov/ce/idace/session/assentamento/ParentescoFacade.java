/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.Parentesco;
import br.gov.ce.idace.session.AbstractFacade;
import static java.util.Collections.list;
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
public class ParentescoFacade extends AbstractFacade<Parentesco> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public ParentescoFacade(){
        super(Parentesco.class);
    }
    
    public List<Parentesco> findAllOrderByNome(){
        TypedQuery<Parentesco> query = em.createQuery("select p from Parentesco p order by p.parentesco",Parentesco.class);
        return query.getResultList();
    }
    
    public boolean haParentesco(String nomeParentesco){
        TypedQuery<Parentesco> query = em.createQuery("select e from Parentesco e where upper(e.parentesco) =:nomeParentesco",Parentesco.class);
        query.setParameter("nomeParentesco", nomeParentesco.toUpperCase());
        return !(query.getResultList().isEmpty());
    }
}
