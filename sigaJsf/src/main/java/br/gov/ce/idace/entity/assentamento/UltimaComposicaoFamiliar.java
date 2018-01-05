/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 *
 * @author carlos.santos
 */
@Entity
@Immutable
@Table(name = "vw_ultimas_conformacoes_casal", schema ="assentamento")
@NamedNativeQueries({ @NamedNativeQuery( name = "ListaUltimasComposicoes", query = "select * from assentamento.vw_ultimas_conformacoes_casal ", resultClass = UltimaComposicaoFamiliar.class)})
public class UltimaComposicaoFamiliar implements Serializable {

    private static final long serialVersionUID = 8537896141457858622L;
        
    @Id    
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="assentado_principal_id")
    private Assentado principal;
    
    @ManyToOne
    @JoinColumn(name="assentado_conjuge_id")
    private Assentado conjuge;
    
    @ManyToOne
    @JoinColumn(name="familia_id")
    private Familia familia;
    
    @Column(name="data_conformacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date  dataConformacao;
    
    @Column(name="motivo_alteracao_casal")
    private MotivoAlteracaoCasal motivoAlteracaoCasal;
    
    @Column(name="codigo_assentamento")
    private String codigoAssentamento;
    
    @Column(name="nome_assentamento")
    private String nomeAssentamento;
    
    @Column(name="cpf_principal")
    private String cpfPrincipal;
    
    @Column(name="nome_principal")
    private String nomePrincipal;
    
    @Column(name="cpf_conjuge")
    private String cpfConjuge;
    
    @Column(name="nome_conjuge")
    private String nomeConjuge;
    
    @Column(name="codigo_familia")
    private String codigoFamilia;

    public UltimaComposicaoFamiliar() {
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

    public String getCodigoAssentamento() {
        return codigoAssentamento;
    }

    public String getNomeAssentamento() {
        return nomeAssentamento;
    }

    public String getCpfPrincipal() {
        return cpfPrincipal;
    }

    public String getNomePrincipal() {
        return nomePrincipal;
    }

    public String getCpfConjuge() {
        return cpfConjuge;
    }

    public String getNomeConjuge() {
        return nomeConjuge;
    }

    public String getCodigoFamilia() {
        return codigoFamilia;
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
        final UltimaComposicaoFamiliar other = (UltimaComposicaoFamiliar) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
