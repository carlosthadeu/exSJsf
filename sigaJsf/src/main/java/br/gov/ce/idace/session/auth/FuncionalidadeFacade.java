/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.auth;

import br.gov.ce.idace.entity.auth.Funcionalidade;
import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Carlos.Santos
 */
@Stateless
public class FuncionalidadeFacade extends AbstractFacade<Funcionalidade> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionalidadeFacade() {
        super(Funcionalidade.class);
    }

    public List<Funcionalidade> findByModuloNome(Sistema sistema, Modulo modulo, String nome) {
        String hql = "select func from Funcionalidade as func"+
                " inner join func.modulo as mod" +
                " inner join mod.sistema as sis ";
        String whereAnd = " where ";
        
        if (!(sistema == null)) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" mod.sistema = :sistema ");
            whereAnd = " and ";
        }
        
        if (!(modulo == null)) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" func.modulo = :modulo ");
            whereAnd = " and ";
        }
        if (!(nome==null) &&(!nome.equals(""))) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" upper(func.funcionalidade) like  upper(:nome)");
        }
        hql = hql.concat(" order by mod.sistema.sistema asc, func.modulo.modulo asc");
        TypedQuery<Funcionalidade> query =  em.createQuery(hql, Funcionalidade.class);
        if (!(sistema == null)) {
            query.setParameter("sistema", sistema);
        }
        
        if (!(modulo == null)) {
            query.setParameter("modulo", modulo);
        }
        if (!(nome==null) &&(!nome.equals(""))) {
            query.setParameter("nome", "%"+ nome.toUpperCase()+"%");
        }
        return query.getResultList();
    }
    
    public List<Funcionalidade> findBySistemaModulo(Sistema sistema, Modulo modulo) {
        String hql = "select func from Funcionalidade as func"+
                " inner join func.modulo as mod" +
                " inner join mod.sistema as sis ";
        String whereAnd = " where ";
        
        if (!(sistema == null)) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" mod.sistema = :sistema ");
            whereAnd = " and ";
        }
        
        if (!(modulo == null)) {
            hql = hql.concat(whereAnd);
            hql = hql.concat(" func.modulo = :modulo ");
            whereAnd = " and ";
        }
        
        hql = hql.concat(" order by mod.sistema.sistema asc, func.modulo.modulo asc");
        TypedQuery<Funcionalidade> query =  em.createQuery(hql, Funcionalidade.class);
        if (!(sistema == null)) {
            query.setParameter("sistema", sistema);
        }
        
        if (!(modulo == null)) {
            query.setParameter("modulo", modulo);
        }
        
        List<Funcionalidade> resultado = query.getResultList();
        
        return resultado;
    }
    
    public void removeFuncionalidade(Funcionalidade funcionalidade){
        Modulo modulo = funcionalidade.getModulo();
        funcionalidade.setModulo(null);
        modulo.getFuncionalidades().remove(funcionalidade);
        em.merge(modulo);
        em.flush();
        em.clear();
    }
    
    public List<String> findRolesBySistemaModuloFuncionalidade(String sistema, String modulo, String funcionalidade){
        String hql = "Select roles.authority from Funcionalidade func inner join func.secRoles roles where func.modulo.sistema.sistema =:sistema and func.modulo.modulo =:modulo and func.funcionalidade=:funcionalidade";
        Query query =  em.createQuery(hql);
        query.setParameter("sistema", sistema);
        query.setParameter("modulo", modulo);
        query.setParameter("funcionalidade", funcionalidade);
        return query.getResultList();
    }
    
    public List<String> findRolesByFuncionalidade(Funcionalidade funcionalidade){
        String hql = "Select roles.authority from Funcionalidade func inner join func.secRoles roles where func.id =:id";
        Query query =  em.createQuery(hql);
        query.setParameter("id", funcionalidade.getId());
        
        return query.getResultList();
    }
    
    

}
