/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.core.entity.pessoa;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="escolaridade", schema="pessoa", uniqueConstraints = @UniqueConstraint(name = "unq_escolaridade", columnNames = {"escolaridade"}))
public class Escolaridade implements Serializable{

    private static final long serialVersionUID = -1672955020038694617L;
    
    @Id    
    @SequenceGenerator(name="escolaridade_seq", allocationSize = 1, sequenceName="seq_escolaridade", schema = "pessoa")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="escolaridade_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name="escolaridade")
    @Size(min = 3, max = 50)
    @NotNull
    private String escolaridade;
    
    

    public Escolaridade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Escolaridade other = (Escolaridade) obj;
        if (!Objects.equals(this.escolaridade, other.escolaridade)) {
            return false;
        }
        return true;
    }
    
    
}
