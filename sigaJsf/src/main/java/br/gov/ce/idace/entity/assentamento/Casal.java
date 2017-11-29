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
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="casal", schema="assentamento")
public class Casal implements Serializable {
    
    @Id    
    @SequenceGenerator(name="casal_seq", allocationSize = 1, sequenceName="seq_casal", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="casal_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="assentado_principal_id")
    @NotNull
    private Assentado principal;
    
    @ManyToOne
    @JoinColumn(name="assentado_conjuge_id")
    private Assentado conjuge;
    
    @ManyToOne
    @JoinColumn(name="familia_id")
    @NotNull
    private Familia familia;
    
    @Column(name="data_conformacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date  dataConformacao;
    
    @Column(name="motivo_alteracao_casal")
    @NotNull
    private MotivoAlteracaoCasal motivoAlteracaoCasal;

    public Casal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assentado getPrincipal() {
        return principal;
    }

    public void setPrincipal(Assentado principal) {
        this.principal = principal;
    }

    public Assentado getConjuge() {
        return conjuge;
    }

    public void setConjuge(Assentado conjuge) {
        this.conjuge = conjuge;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Date getDataConformacao() {
        return dataConformacao;
    }

    public void setDataConformacao(Date dataConformacao) {
        this.dataConformacao = dataConformacao;
    }

    public MotivoAlteracaoCasal getMotivoAlteracaoCasal() {
        return motivoAlteracaoCasal;
    }

    public void setMotivoAlteracaoCasal(MotivoAlteracaoCasal motivoAlteracaoCasal) {
        this.motivoAlteracaoCasal = motivoAlteracaoCasal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Casal other = (Casal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
