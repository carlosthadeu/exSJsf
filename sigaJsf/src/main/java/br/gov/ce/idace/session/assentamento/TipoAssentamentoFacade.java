/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.assentamento;

import br.gov.ce.idace.entity.assentamento.TipoAssentamento;
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
public class TipoAssentamentoFacade  extends AbstractFacade<TipoAssentamento> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoAssentamentoFacade() {
        super(TipoAssentamento.class);
    }
    
    public List<TipoAssentamento> tipoAssentamentoOrderByTipo(){
        TypedQuery <TipoAssentamento> query = em.createQuery("select t from TipoAssentamento t order by t.tipoAssentamento", TipoAssentamento.class);
        return query.getResultList();
        
    }
    
}
