/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;



import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.core.entity.pessoa.PessoaFisica;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author carlos.santos
 * 
 * Informaçãoes sobre os assentados de acordo com o formulário Inscrição de Candidata e Candidato ao Programa Nacional de Reforma Agrária D
 * do Sistema de Informações de Projetos de Reforma Agrária - SIPRA do Ministério do Desenvolvimento Agrário - Instituto Nacional de Colonização e Reforma Agrária - INCRA
 *  
 */
@Entity
@Table(name="assentado",schema = "assentamento", uniqueConstraints = @UniqueConstraint(name = "unq_cpf_assentado", columnNames = {"cpf"}))
@AttributeOverrides({@AttributeOverride(name="cpf", column = @Column(nullable = false))})
public class Assentado extends PessoaFisica{
    
    @Column(name="estuda")
    private Boolean estuda;
    
    @Column(name="nacionalidade")
    private Nacionalidade nacionalidade;
    
    @Column(name="aposentadoria_invalidez")
    private Boolean aposentadoriaInvalidez;
    
    @Column(name="ativo")
    private Boolean ativo;
    
    /**
     * Item 12 da seção 02 - Identificação da unidade familiar  do formulário: Exerce função pública ?
     */
    @Column(name="exerce_funcao_publica")
    private Boolean exerceFuncaoPublica;
    
    /**
     * Item 13 da seção 02 - Identificação da unidade familiar  do formulário: É co-participante de estabelecimento comercial ou industrial ?
     */
    @Column(name="part_estab_com_ind")
    private Boolean partEstabComInd;
    
    /**
     * Item 14  da seção 02 - Identificação da unidade familiar  do formulário: Possui antecedentes criminais com sentença definitivatransitada em julgado ?
     */
    @Column(name="antecedentes_criminais")
    private Boolean antecedentesCriminais;
    
    @Column(name = "renda_mensal", precision=10, scale=2)
    private Double rendaMensal; 
    
    /**
     * Item 17 da seção 02 - Identificação da unidade familiar do formulário: É ex-beneficiário(a) do programa nacio9nal de reforma agrária ?
     */
    @Column(name="ex_ben_prog_nac_ref_agra")
    private Boolean exBenProgNacRefAgra;
    
    /**
     * Item 04 da seção 04 - Outras informações da unidade familiar do formulário: É trabalhador rural assalariado, posseiro, parceiro, arrendatário, foreiro ou sem terra  ?
     */
    @Column(name="trab_rur_ass_pos_parc_arred_for_sem_ter")
    private TrabRurAssPosParcArredForSemTer trabRurAssPosParcArredForSemTer;
    
    /**
     * Item 04 da seção 04 - Outras informações da unidade familiar do formulário: Há quanto tempo mora  ? (Anos)
     */
    @Column(name="anos_qto_tempo_mora")
    private Integer anosQtoTempoMora;
    
    /**
     * Item 04 da seção 04 - Outras informações da unidade familiar do formulário: Há quanto tempo mora  ? (Meses)
     */
    @Column(name="meses_qto_tempo_mora")
    private Integer mesesQtoTempoMora;
    
    /**
     * Item 05 da seção 04 - Nos últimos 5 anos, quanto tempo trabalha na atividade agrícola  ? (Anos)
     */
    @Column(name="anos_tmp_trab_ativ_agri")
    private Integer anosTmpTrabAtivAgri;
    
    /**
     * Item 05 da seção 04 - Nos últimos 5 anos, quanto tempo trabalha na atividade agrícola  ? (Meses)
     */
    @Column(name="meses_tmp_trb_ativ_agri")
    private Integer mesesTmpTrbAtivAgri;
    
    @ManyToOne
    @JoinColumn(name="estado_civil_id")
    private EstadoCivil estadoCivil;
    
    @ManyToOne
    @JoinColumn(name="escolaridade_id")
    private Escolaridade escolaridade;
    
    public Assentado() {
    }

    public Boolean getEstuda() {
        return estuda;
    }

    public void setEstuda(Boolean estuda) {
        this.estuda = estuda;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Boolean getAposentadoriaInvalidez() {
        return aposentadoriaInvalidez;
    }

    public void setAposentadoriaInvalidez(Boolean aposentadoriaInvalidez) {
        this.aposentadoriaInvalidez = aposentadoriaInvalidez;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getExerceFuncaoPublica() {
        return exerceFuncaoPublica;
    }

    public void setExerceFuncaoPublica(Boolean exerceFuncaoPublica) {
        this.exerceFuncaoPublica = exerceFuncaoPublica;
    }

    public Boolean getPartEstabComInd() {
        return partEstabComInd;
    }

    public void setPartEstabComInd(Boolean partEstabComInd) {
        this.partEstabComInd = partEstabComInd;
    }

    public Boolean getAntecedentesCriminais() {
        return antecedentesCriminais;
    }

    public void setAntecedentesCriminais(Boolean antecedentesCriminais) {
        this.antecedentesCriminais = antecedentesCriminais;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public Boolean getExBenProgNacRefAgra() {
        return exBenProgNacRefAgra;
    }

    public void setExBenProgNacRefAgra(Boolean exBenProgNacRefAgra) {
        this.exBenProgNacRefAgra = exBenProgNacRefAgra;
    }

    public TrabRurAssPosParcArredForSemTer getTrabRurAssPosParcArredForSemTer() {
        return trabRurAssPosParcArredForSemTer;
    }

    public void setTrabRurAssPosParcArredForSemTer(TrabRurAssPosParcArredForSemTer trabRurAssPosParcArredForSemTer) {
        this.trabRurAssPosParcArredForSemTer = trabRurAssPosParcArredForSemTer;
    }

    public Integer getAnosQtoTempoMora() {
        return anosQtoTempoMora;
    }

    public void setAnosQtoTempoMora(Integer anosQtoTempoMora) {
        this.anosQtoTempoMora = anosQtoTempoMora;
    }

    public Integer getMesesQtoTempoMora() {
        return mesesQtoTempoMora;
    }

    public void setMesesQtoTempoMora(Integer mesesQtoTempoMora) {
        this.mesesQtoTempoMora = mesesQtoTempoMora;
    }

    public Integer getAnosTmpTrabAtivAgri() {
        return anosTmpTrabAtivAgri;
    }

    public void setAnosTmpTrabAtivAgri(Integer anosTmpTrabAtivAgri) {
        this.anosTmpTrabAtivAgri = anosTmpTrabAtivAgri;
    }

    public Integer getMesesTmpTrbAtivAgri() {
        return mesesTmpTrbAtivAgri;
    }

    public void setMesesTmpTrbAtivAgri(Integer mesesTmpTrbAtivAgri) {
        this.mesesTmpTrbAtivAgri = mesesTmpTrbAtivAgri;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assentado)) {
            return false;
        }
        Assentado other = (Assentado) object;
        if ((this.getCpf() == null && other.getCpf() != null) || (this.getCpf() != null && !this.getCpf().equals(other.getCpf()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }
    
}
