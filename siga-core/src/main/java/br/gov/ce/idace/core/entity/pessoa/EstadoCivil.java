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
@Table(name="estado_civil", schema = "pessoa", uniqueConstraints = {@UniqueConstraint(name = "unq_estado_civil", columnNames = {"estado_civil"})})
public class EstadoCivil implements Serializable {

    private static final long serialVersionUID = 2912290215614012393L;
    
    @Id
    @SequenceGenerator(name ="estado_civil_seq",schema = "pessoa",allocationSize = 1,initialValue = 1,sequenceName = "seq_estado_civil")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_civil_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name="estado_civil")
    @Size(min = 3, max = 50)
    @NotNull
    private String estadoCivil;

    public EstadoCivil() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final EstadoCivil other = (EstadoCivil) obj;
        if (!Objects.equals(this.estadoCivil, other.estadoCivil)) {
            return false;
        }
        return true;
    }
    
    
}
