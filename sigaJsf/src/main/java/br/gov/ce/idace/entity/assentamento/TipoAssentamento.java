/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "tipo_assentamento", schema = "assentamento", uniqueConstraints = {@UniqueConstraint(name = "unq_tipo_assentamento", columnNames = {"tipo_assentamento"})})
public class TipoAssentamento implements Serializable{
    
    @Id    
    @SequenceGenerator(name="tipo_assentamento_seq", allocationSize = 1, sequenceName="seq_tipo_assentamento", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="tipo_assentamento_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "tipo_assentamento")
    @NotNull
    @Size(min = 3, max = 100)
    private String tipoAssentamento;
    
    @OneToMany(mappedBy = "tipoAssentamento", targetEntity = Assentamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Assentamento> assentamentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoAssentamento() {
        return tipoAssentamento;
    }

    public void setTipoAssentamento(String tipoAssentamento) {
        this.tipoAssentamento = tipoAssentamento;
    }

    public List<Assentamento> getAssentamentos() {
        return assentamentos;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final TipoAssentamento other = (TipoAssentamento) obj;
        if (!Objects.equals(this.tipoAssentamento, other.tipoAssentamento)) {
            return false;
        }
        return true;
    }
    
    
}
