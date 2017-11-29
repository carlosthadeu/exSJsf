/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Carlos.Santos
 */
@Entity
@Table(name = "sec_role", schema = "public", uniqueConstraints = @UniqueConstraint(name = "unq_authority", columnNames = {"authority"}))
public class SecRole implements Serializable {

    @Id    
    @SequenceGenerator(name="role_seq", allocationSize = 1, sequenceName="seq_sec_role", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="role_seq")
    @Basic(optional = false)
    @Column(name = "id")    
    private Long id;
   
    
    @Basic(optional = false)
    @NotNull
    private long version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String authority;
    
    
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sec_user_sec_role", schema = "public",
      joinColumns = {@JoinColumn(name = "sec_role_id")},
      inverseJoinColumns = {@JoinColumn(name = "sec_user_id")})
    private Set<SecUser> secUsers = new HashSet<SecUser>();
    
    
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sec_role_funcionalidade", schema = "public",
      joinColumns = {@JoinColumn(name = "sec_role_id")},
      inverseJoinColumns = {@JoinColumn(name = "funcionalidade_id")})
    private Set<Funcionalidade> funcionalidades  =new HashSet<Funcionalidade>();

    

    public SecRole() {
    }

    public SecRole(Long id) {
        this.id = id;
    }

    public SecRole(Long id, long version, String authority) {
        this.id = id;
        this.version = version;
        this.authority = authority;
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
       

    public Set<Funcionalidade> getFuncionalidades() {
        return funcionalidades;
    }

    public Set<SecUser> getSecUsers() {
        return secUsers;
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
        if (!(object instanceof SecRole)) {
            return false;
        }
        SecRole other = (SecRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.idace.entity.auth.SecRole[ id=" + id + " ]";
    }

    
    

    
    
}
