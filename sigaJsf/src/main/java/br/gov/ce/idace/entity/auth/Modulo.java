/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import br.gov.ce.idace.web.util.SampleEntity;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Carlos.Santos
 * <h1>Agrupamento de funcionalidades por subsistemas</h1>
 */
@Entity
@Table(catalog = "sige", schema = "public", uniqueConstraints = @UniqueConstraint(name = "unq_sistema_nome_modulo", columnNames = {"sistema_id", "modulo"}))

@NamedQueries({
    @NamedQuery(name="Modulo.findBySistemaNomeModulo", query="SELECT m FROM Modulo m where m.sistema.sistema = :sistema and m.modulo =:modulo"),
    @NamedQuery(name="Modulo.findModulosBySistema", query="SELECT m FROM Modulo m where m.sistema = :sistema")
})
public class Modulo implements Serializable, SampleEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="modulo_seq", allocationSize = 1, sequenceName = "modulo_id_seq", schema = "public")
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "modulo_seq")
    @Basic(optional = false)
    @NotNull
    private Long id;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "modulo")
    private String modulo;
    
    @ManyToOne(optional = false)
    @JoinColumn(name="sistema_id", foreignKey = @ForeignKey(name = "fk_sistema_modulo") )
    private Sistema sistema;
    
   
    @OneToMany(mappedBy = "modulo", targetEntity = Funcionalidade.class, orphanRemoval=true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Funcionalidade> funcionalidades;
    
    @Basic(optional = false)
    @NotNull
    private long version;

    public Modulo() {
    }

    public Modulo(Long id) {
        this.id = id;
    }

    public Modulo(Long id, long version, String modulo) {
        this.id = id;
        this.version = version;
        this.modulo = modulo;
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }
    
    
    public Collection<Funcionalidade> getFuncionalidades() {
        return funcionalidades;
    }

    public void setFuncionalidades(Collection<Funcionalidade> funcionalidades) {
        this.funcionalidades = funcionalidades;
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
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.idace.entity.auth.Modulo[ id=" + id + " ]";
    }
    
}
