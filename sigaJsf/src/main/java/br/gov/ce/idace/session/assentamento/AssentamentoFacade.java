/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.Assentamento;
import br.gov.ce.idace.session.AbstractFacade;
import br.gov.ce.idace.core.entity.ibge.Municipio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author thadeu
 */
@Stateless
public class AssentamentoFacade extends AbstractFacade<Assentamento> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssentamentoFacade() {
        super(Assentamento.class);
    }
    
    public List<Assentamento> FindByMunicipioNome(Municipio municipio, String nome){
        String hql = "select a from Assentamento a";
        String whereAnd = " where ";
        
        if (!(municipio == null)) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" a.municipio = :municipio ");
            whereAnd = " and ";
        }
        
        
        if (!(nome==null) &&(!nome.equals(""))) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" upper(a.assentamento) like  upper(:nome)");
        }
        hql = hql.concat(" order by a.municipio.nome, a.assentamento");
        TypedQuery<Assentamento> query =  em.createQuery(hql, Assentamento.class);
        if (!(municipio == null)) {
            query.setParameter("municipio", municipio);
        }
        
        
        if (!(nome==null) &&(!nome.equals(""))) {
            query.setParameter("nome", "%"+ nome.toUpperCase()+"%");
        }
        return query.getResultList();
    }
    
}
