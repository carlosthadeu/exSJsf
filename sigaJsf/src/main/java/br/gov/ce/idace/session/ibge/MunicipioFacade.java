/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.session.ibge;

import br.gov.ce.idace.core.entity.ibge.Estado;
import br.gov.ce.idace.session.AbstractFacade;
import br.gov.ce.idace.core.entity.ibge.Municipio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author carlos.santos
 */
@Stateless
public class MunicipioFacade extends AbstractFacade<Municipio> {

    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;

    public MunicipioFacade() {
        super(Municipio.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Municipio> MunicipioOrderByNome() {
        TypedQuery<Municipio> query = em.createQuery("select m from Municipio m order by m.nome", Municipio.class);
        return query.getResultList();

    }

    public Municipio FindByNome(String nomeMunicipio) {
        TypedQuery<Municipio> query = em.createQuery("select m from Municipio m where m.nome =:nome", Municipio.class);
        query.setParameter("nome", nomeMunicipio);
        return query.getSingleResult();
    }
    
    public List<Municipio> FindByUf(String codUf) {
        TypedQuery<Municipio> query = em.createQuery("select m from Municipio m where m.idUf =:idUf", Municipio.class);
        query.setParameter("idUf", codUf);
        return query.getResultList();
    }

    public String FindNomeByObjeto(Municipio municipio) {
        Query query = em.createQuery("select m.nome from Municipio m where m =:municipio");
        query.setParameter("municipio", municipio);
        return (String) query.getSingleResult();
    }

    public List<Municipio> MunicipiosCeara() {
        TypedQuery<Municipio> query = em.createNamedQuery("municipiosCeara", Municipio.class);
        return query.getResultList();
    }

}
