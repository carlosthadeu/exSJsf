/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;


import br.gov.ce.idace.entity.assentamento.Assentamento;
import br.gov.ce.idace.entity.assentamento.Familia;
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
public class FamiliaFacade extends AbstractFacade<Familia> {
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FamiliaFacade() {
        super(Familia.class);
    }
    
    public List<Familia> findByAssentamento(Assentamento assentamento, String nomeAssentado, boolean incluirDesistencias ){
        String hql = "select f from Familia f where (f.assentamento =:assentamento) ";
        if (!(nomeAssentado == null)) hql = hql.concat(" and (upper(f.principal.nome) like  upper(:nomeAssentado) ");
        if (!incluirDesistencias) hql = hql.concat(" and (f.dataDesistencia is not null) ");
        hql = hql.concat(" order by f.codigo");
        TypedQuery query = em.createQuery( hql, Familia.class);
        query.setParameter("assentamento", assentamento);
        if (!(nomeAssentado == null)) query.setParameter("nomeAssentado", "%"+nomeAssentado.toUpperCase()+"%");
        return query.getResultList();
    }
}
