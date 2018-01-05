/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Carlos.Santos
 * 
 * <h1>Classe que referencia as funcionalidades do sistema para controle do acesso</h1>
 * 
 */
@Entity
@Table(catalog = "sige", schema = "public", uniqueConstraints = @UniqueConstraint(name = "unq_nome_funcionalidade", columnNames = {"modulo_id","funcionalidade"}))

public class Funcionalidade implements Serializable {

    private static final long serialVersionUID = 8860215199895423511L;
    
    @Id
    @SequenceGenerator(name="funcionalidade_seq", allocationSize = 1, sequenceName = "funcionalidade_id_seq", schema = "public" )
    @GeneratedValue( strategy= GenerationType.SEQUENCE, generator = "funcionalidade_seq" ) 
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 150)
    @Column(name = "funcionalidade")
    private String funcionalidade;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    private String url;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="modulo_id", foreignKey = @ForeignKey(name = "fk_modulo_funcionalidade"))
    private Modulo modulo;
    
    @ManyToMany(mappedBy="funcionalidades", fetch = FetchType.LAZY)
    private Set<SecRole> secRoles = new HashSet<SecRole>();
    
    @Basic(optional = false)
    @NotNull
    private long version;

    public Funcionalidade() {
    }

    public Funcionalidade(Long id) {
        this.id = id;
    }

    public Funcionalidade(Long id, long version, String funcionalidade, String url) {
        this.id = id;
        this.version = version;
        this.funcionalidade = funcionalidade;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    

    public String getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(String funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Set<SecRole> getSecRoles() {
        return secRoles;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final Funcionalidade other = (Funcionalidade) obj;
        if (!Objects.equals(this.funcionalidade, other.funcionalidade)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "br.gov.ce.idace.entity.auth.Funcionalidade[ id=" + id + " ]";
    }
    
}
