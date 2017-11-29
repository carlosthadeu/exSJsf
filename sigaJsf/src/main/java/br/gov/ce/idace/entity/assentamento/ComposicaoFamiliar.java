/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name = "composicao_familiar", schema = "assentamento", uniqueConstraints = @UniqueConstraint(name = "unq_codigo", columnNames = {"familia_id","familiar_assentado_id"}) )
public class ComposicaoFamiliar implements Serializable {
    
    @Id    
    @SequenceGenerator(name="composicao_familiar_seq", allocationSize = 1, sequenceName="seq_composicao_familiar", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="composicao_familiar_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="familia_id")
    @NotNull
    private Familia familia;
    
    @ManyToOne
    @JoinColumn(name="familiar_assentado_id")
    @NotNull
    private FamiliarAssentado familiarAssentado;
    
    @Column(name="ativo")
    private Boolean ativo = true;
    
    @Column(name="data_desativacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date  dataDesativacao;

    public ComposicaoFamiliar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public FamiliarAssentado getFamiliarAssentado() {
        return familiarAssentado;
    }

    public void setFamiliarAssentado(FamiliarAssentado familiarAssentado) {
        this.familiarAssentado = familiarAssentado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataDesativacao() {
        return dataDesativacao;
    }

    public void setDataDesativacao(Date dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final ComposicaoFamiliar other = (ComposicaoFamiliar) obj;
        if (!Objects.equals(this.familia, other.familia)) {
            return false;
        }
        if (!Objects.equals(this.familiarAssentado, other.familiarAssentado)) {
            return false;
        }
        return true;
    }
    
    
}
