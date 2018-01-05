/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

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
@Table(name="parentesco", schema="assentamento", uniqueConstraints = @UniqueConstraint(name = "unq_parentesco", columnNames = {"parentesco"}))
public class Parentesco implements Serializable {

    private static final long serialVersionUID = -8418044743791001263L;
    
    @Id    
    @SequenceGenerator(name="parentesco_seq", allocationSize = 1, sequenceName="seq_parentesco", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="parentesco_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name="parentesco")
    @Size(min = 3, max = 50)
    @NotNull
    private String parentesco;
    
    

    public Parentesco() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
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
        final Parentesco other = (Parentesco) obj;
        if (!Objects.equals(this.parentesco, other.parentesco)) {
            return false;
        }
        return true;
    }
    
    
}

