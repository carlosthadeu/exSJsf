/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.core.entity.pessoa.PessoaFisica;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="familiar_assentado",schema = "assentamento",uniqueConstraints = @UniqueConstraint(name = "unq_cpf_fam_assentado", columnNames = {"cpf"}))
public class FamiliarAssentado extends PessoaFisica{

    private static final long serialVersionUID = 2511114597550073665L;
    
    @ManyToOne
    @JoinColumn(name="familia_id")
    @NotNull
    private Familia familia;
    
    @Column(name="ativo")
    private Boolean ativo = true;
    
    @Column(name="data_desativacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date  dataDesativacao;
    
    @ManyToOne
    @JoinColumn(name="parentesco_id")
    @NotNull
    private Parentesco parentesco;
    
    @Column(name="estuda")
    private Boolean estuda;
    
    @ManyToOne
    @JoinColumn(name="escolaridade_id")
    private Escolaridade escolaridade;

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
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

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public Boolean getEstuda() {
        return estuda;
    }

    public void setEstuda(Boolean estuda) {
        this.estuda = estuda;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getId());
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
        final FamiliarAssentado other = (FamiliarAssentado) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }
}
