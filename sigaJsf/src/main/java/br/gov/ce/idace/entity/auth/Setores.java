/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author marcos.albano
 */
@Entity
@Table(name = "setores", schema = "sige")
@NamedQueries({
    @NamedQuery(name = "Setores.findAll", query = "SELECT s FROM Setores s")
    , @NamedQuery(name = "Setores.findById", query = "SELECT s FROM Setores s WHERE s.id = :id")
    , @NamedQuery(name = "Setores.findByNome", query = "SELECT s FROM Setores s WHERE s.nome = :nome")

})
public class Setores implements Serializable {

    private static final long serialVersionUID = -5060329574006857004L;
    
    @Id
    @SequenceGenerator(name = "setores_seq", allocationSize = 1, sequenceName = "setores_id_seq", schema = "sige")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setores_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 20, nullable = false, unique = true)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Setores() {
    }

    public Setores(Long id) {
        this.id = id;
    }

    public Setores(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setores)) {
            return false;
        }
        Setores other = (Setores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.idace.entity.auth.Setores[ id=" + id + " ]";
    }
}
