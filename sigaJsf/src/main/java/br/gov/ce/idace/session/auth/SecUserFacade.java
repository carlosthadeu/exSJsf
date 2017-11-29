/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.auth;

import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author thadeu
 */
@Stateless
public class SecUserFacade extends AbstractFacade<SecUser> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecUserFacade() {
        super(SecUser.class);
    }
    
    public List<SecUser> findAllOrderByUsername(){
        String hql = "Select user from SecUser user order by user.username";
        TypedQuery<SecUser> query =  em.createQuery(hql, SecUser.class);
        return query.getResultList();
    }
    
    public SecUser findByEmail(String email){
        try{
            String hql = "Select user from SecUser user where user.email =:email";
            TypedQuery<SecUser> query =  em.createQuery(hql, SecUser.class);
            query.setParameter("email", email);
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            return null;
        }
        
        
        
    }
    
}
