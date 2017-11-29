/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Carlos.Santos
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> resultados = getEntityManager().createQuery(cq).getResultList();
        return resultados;
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findLike(Map<String, String> criteriaParams) {
        javax.persistence.criteria.CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = builder.createQuery();
        Root<T> from = cq.from(entityClass);
        Predicate predicate = builder.and();
        for (String field : criteriaParams.keySet()) {
            predicate = builder.and(predicate, builder.like(builder.upper(from.<String>get(field)), "%" + criteriaParams.get(field).toUpperCase() + "%"));
        }
        TypedQuery<T> typedQuery = getEntityManager().createQuery(cq.select(from)
                .where(predicate));
        List<T> results = typedQuery.getResultList();
        return results;
    }

    public List<T> findExaticly(Map<String, String> criteriaParams) {
        javax.persistence.criteria.CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = builder.createQuery();
        Root<T> from = cq.from(entityClass);
        Predicate predicate = builder.and();
        for (String field : criteriaParams.keySet()) {
            predicate = builder.and(predicate, builder.equal(from.<String>get(field), criteriaParams.get(field)));
        }
        TypedQuery<T> typedQuery = getEntityManager().createQuery(cq.select(from)
                .where(predicate));
        List<T> results = typedQuery.getResultList();
        return results;
    }

    public List<T> findByField(String field, String value) {
        javax.persistence.criteria.CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = builder.createQuery();
        Root<T> from = cq.from(entityClass);
        cq.select(cq.from(entityClass));
        cq.where(builder.equal(from.get(field), value));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public T findByNamedQuerySingle(String namedQuery, Map<String, String> parameters) {
        try {
            TypedQuery<T> query;

            query = getEntityManager().createNamedQuery(namedQuery, entityClass);
            for (String field : parameters.keySet()) {
                query.setParameter(field, parameters.get(field));
            }
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
