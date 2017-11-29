/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;

/**
 *
 * @author carlos.santos
 * 
 * Informaçãoes sobre os assentados de acordo com o formulário Inscrição de Candidata e Candidato ao Programa Nacional de Reforma Agrária D
 * do Sistema de Informações de Projetos de Reforma Agrária - SIPRA do Ministério do Desenvolvimento Agrário - Instituto Nacional de Colonização e Reforma Agrária - INCRA
 */
@Entity
@Table(name="entrevista", schema = "assentamento")
public class Entrevista implements Serializable{
    
    @Id    
    @SequenceGenerator(name="entrevista_seq", allocationSize = 1, sequenceName="seq_entrevista", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="entrevista_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="entrevistador_id")
    private Entrevistador entrevistador;
    
    @ManyToOne
    @JoinColumn(name="familia_id")
    private Familia familia;
    
    @Column(name="ano_referencia")
    @NotNull
    private Integer anoReferencia;
    
    @Column(name = "data_entrevista")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEntrevista;
    
    /**
     * Item 01 da seção 04 - Faz parte de acampamento de trabalhadores rurais, terra indígena ou outra comunidade social ?
     */
    @Column(name="faz_parte_acamp_trab_rur_ter_ind_com_soc")
    private Boolean fazParteAcampTrabRurTerIndComSoc;
    
    /**
     * Item 01 da seção 04 - Faz parte de acampamento de trabalhadores rurais, terra indígena ou outra comunidade social ?
     */
    @Column(name="codigo_comunidade_social")
    @Size(max = 9)
    private String codigoComunidadeSocial;
    
    
    /**
     * Item 02 da seção 04 - É proprietário do imóvel em obtenção ?
     */
    @Column(name="proprietario_obtencao")
    private Boolean proprietarioObtencao;
    
    /**
     * Item 03 da seção 04 - É Proprietário de imóvel rural comprovadamente insuficiente para o sustento próprio ou de sua família ?
     */
    @Column(name="opc_imo_rur_comp_ins_sus_prop_fam")
    private OpcImoRurCompInsSusPropFam opcImoRurCompInsSusPropFam;
    
    /**
     * Item 05 da seção 04 - Mora no município do imóvel em obtenção ?
     */
    @Column(name="mora_mun_imo_obt")
    private Boolean moraMunImoObt;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entrevistador getEntrevistador() {
        return entrevistador;
    }

    public void setEntrevistador(Entrevistador entrevistador) {
        this.entrevistador = entrevistador;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Date getDataEntrevista() {
        return dataEntrevista;
    }

    public void setDataEntrevista(Date dataEntrevista) {
        this.dataEntrevista = dataEntrevista;
    }

    public Boolean getFazParteAcampTrabRurTerIndComSoc() {
        return fazParteAcampTrabRurTerIndComSoc;
    }

    public void setFazParteAcampTrabRurTerIndComSoc(Boolean fazParteAcampTrabRurTerIndComSoc) {
        this.fazParteAcampTrabRurTerIndComSoc = fazParteAcampTrabRurTerIndComSoc;
    }

    public String getCodigoComunidadeSocial() {
        return codigoComunidadeSocial;
    }

    public void setCodigoComunidadeSocial(String codigoComunidadeSocial) {
        this.codigoComunidadeSocial = codigoComunidadeSocial;
    }

    public Boolean getProprietarioObtencao() {
        return proprietarioObtencao;
    }

    public void setProprietarioObtencao(Boolean proprietarioObtencao) {
        this.proprietarioObtencao = proprietarioObtencao;
    }

    public OpcImoRurCompInsSusPropFam getOpcImoRurCompInsSusPropFam() {
        return opcImoRurCompInsSusPropFam;
    }

    public void setOpcImoRurCompInsSusPropFam(OpcImoRurCompInsSusPropFam opcImoRurCompInsSusPropFam) {
        this.opcImoRurCompInsSusPropFam = opcImoRurCompInsSusPropFam;
    }

    public Boolean getMoraMunImoObt() {
        return moraMunImoObt;
    }

    public void setMoraMunImoObt(Boolean moraMunImoObt) {
        this.moraMunImoObt = moraMunImoObt;
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
        if (!(object instanceof Entrevista)) {
            return false;
        }
        Entrevista other = (Entrevista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
