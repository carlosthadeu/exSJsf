/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import java.io.Serializable;
import java.util.Collection;
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
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Carlos.Santos
 * 
 * <h1>Sistemas controlados pelo spring security facilitando a administração dos módulo e funcionalidades</h1>
 * 
 */
@Entity
@Table(name = "sistemas", schema="public", uniqueConstraints = @UniqueConstraint(name = "unq_nome_sistema", columnNames = {"sistema"}))
public class Sistema implements Serializable {

    private static final long serialVersionUID = 4402648416138817273L;
    
    @Id
    @SequenceGenerator(name="sistemas_seq", allocationSize = 1, sequenceName = "sistemas_id_seq", schema="public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistemas_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name="sistema")
    @Size(min = 3, max = 100)
    @NotNull
    private String sistema;
    
    @Basic(optional = false)
    @NotNull
    private long version;
    
    @OneToMany(mappedBy = "sistema", targetEntity = Modulo.class,   orphanRemoval=true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Modulo> modulos;

    public Sistema() {
    }

    
    public Sistema(Long id) {
        this.id = id;
    }

    public Sistema(Long id, String sistema, long version) {
        this.id = id;
        this.sistema = sistema;
        this.version = version;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Collection<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(Collection<Modulo> modulos) {
        this.modulos = modulos;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Sistema other = (Sistema) obj;
        if (!Objects.equals(this.sistema, other.sistema)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
