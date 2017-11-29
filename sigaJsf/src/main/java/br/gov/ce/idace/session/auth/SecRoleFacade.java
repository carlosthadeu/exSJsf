/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.auth;

import br.gov.ce.idace.entity.auth.Funcionalidade;
import br.gov.ce.idace.entity.auth.SecRole;
import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos.Santos
 */
@Stateless
public class SecRoleFacade extends AbstractFacade<SecRole> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecRoleFacade() {
        super(SecRole.class);
    }
    
    public void adicionaFuncionalidade(Funcionalidade funcionalidade, SecRole secRole){
        secRole.getFuncionalidades().add(funcionalidade);
        em.merge(secRole);
        em.flush();
        em.clear();
        
    }
    
    public void removeFuncionalidade(Funcionalidade funcionalidade, SecRole secRole){
   //     funcionalidade.setSecRoles(null);
        secRole.getFuncionalidades().remove(funcionalidade);
        em.merge(secRole);
        em.flush();
        em.clear();
    }
    
    public void adicionaUsuario(SecUser secUser, SecRole secRole){
        secRole.getSecUsers().add(secUser);
        em.merge(secRole);
        em.flush();
        em.clear();        
    }
    
    public void removeUsuario(SecUser secUser, SecRole secRole){
   //     funcionalidade.setSecRoles(null);
        secRole.getSecUsers().remove(secUser);
        em.merge(secRole);
        em.flush();
        em.clear();
    }
    
    public List<String> rolesPorUsuario (Long userId){
        String hql = "select role.authority from SecRole as role"+
                " inner join role.secUsers as user where user.id = :userId";
        Query query = em.createQuery(hql);
        query.setParameter("userId", userId);
        List<String> resultado = query.getResultList();
        return resultado;
    }
    
}
