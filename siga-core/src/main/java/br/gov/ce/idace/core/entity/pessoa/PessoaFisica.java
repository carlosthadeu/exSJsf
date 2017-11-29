/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.core.entity.pessoa;

import br.gov.ce.idace.core.entity.ibge.Estado;
import br.gov.ce.idace.core.entity.ibge.Municipio;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author carlos.santos
 */

@MappedSuperclass 
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PessoaFisica implements Serializable {
    
    @Id
    @TableGenerator(
            name = "pessoa_seq",
            schema = "pessoa",
            table = "pessoa_id_Generator",
            pkColumnName = "name",
            valueColumnName = "sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "pessoa_seq")
            
    private Long id;
    
    @Column(name="nome")
    @Size(min = 3, max = 200)
    @NotNull
    private String nome;
    
    @Column(name="data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    @Column(name="email", nullable = true)
    @Size(min = 1, max = 100)
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
             message="Email inválido !")
    private String email;
    
    @Column(name="nome_pai")
    @Size(min = 3, max = 200)
    private String nomePai;
    
    @Column(name="nome_mae")
    @Size(min = 3, max = 200)
    private String nomeMae;
    
    @Column(name="ctps")
    @Size(min = 3, max = 12)
    private String ctps;
    
    @Column(name="uf_ctps")
    private Estado ufCtps;
    
    @Column(name="serie_ctps")
    @Size(min = 3, max = 5)
    private String serieCtps;
    
    @Column(name="cpf")    
    @Size(min = 11, max = 11)
    //@Pattern(regexp="[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2]", message="CPF inválido !")
    private String cpf;
    
    @Column(name="pis")    
    @Size(min = 11, max = 12)
    private String pisPasep;
    
    @Column(name="rg")    
    @Size(min = 3, max = 50)
    private String rg;
    
    @Column(name="uf_rg")    
    private Estado ufRg;
    
    @Column(name="orgao_emissor_rg")    
    @Size(min = 3, max = 10)
    private String orgaoEmissorRg;
    
    @Column(name="raca_cor")
    @Size(min = 3, max = 15)
    private String racaCor;
    
    @Column(name = "sexo")
    private Sexo sexo;
    
    @ManyToOne
    @JoinColumn(name="escolaridade_id")
    private Escolaridade escolaridade;
    
    @Column(name = "uf_nascimento")
    private Estado ufNascimento;
    
    @ManyToOne
    @JoinColumn(name="municipio_nascimento_id")
    private Municipio municipioNascimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public Estado getUfCtps() {
        return ufCtps;
    }

    public void setUfCtps(Estado ufCtps) {
        this.ufCtps = ufCtps;
    }

    public String getSerieCtps() {
        return serieCtps;
    }

    public void setSerieCtps(String serieCtps) {
        this.serieCtps = serieCtps;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Estado getUfRg() {
        return ufRg;
    }

    public void setUfRg(Estado ufRg) {
        this.ufRg = ufRg;
    }

    public String getOrgaoEmissorRg() {
        return orgaoEmissorRg;
    }

    public void setOrgaoEmissorRg(String orgaoEmissorRg) {
        this.orgaoEmissorRg = orgaoEmissorRg;
    }

    public String getRacaCor() {
        return racaCor;
    }

    public void setRacaCor(String racaCor) {
        this.racaCor = racaCor;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Estado getUfNascimento() {
        return ufNascimento;
    }

    public void setUfNascimento(Estado ufNascimento) {
        this.ufNascimento = ufNascimento;
    }  

    public Municipio getMunicipioNascimento() {
        return municipioNascimento;
    }

    public void setMunicipioNascimento(Municipio municipioNascimento) {
        this.municipioNascimento = municipioNascimento;
    }
    
}
