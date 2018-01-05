/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.auth;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 *
 * @author Carlos.Santos
 */
@Entity
@Table(name = "sec_user", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "unq_username", columnNames = {"username"}), @UniqueConstraint(name = "unq_email", columnNames = {"email"})})
@NamedQuery(name="SecUser.findByUsername", query="SELECT s FROM SecUser s where s.username = :username")

public class SecUser implements Serializable {

    private static final long serialVersionUID = 8127476623202564090L;
    
    @Id    
    @SequenceGenerator(name="user_seq", allocationSize = 1, sequenceName="seq_sec_user", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="user_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    private long version;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_expired")
    private boolean accountExpired;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_locked")
    private boolean accountLocked;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
             message="Email inválido !")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    private boolean enabled;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "password_expired")
    private boolean passwordExpired;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String username;
    
    @Column(name = "primeiro_nome")
    @NotNull
    private String primeiroNome;
    @Size(max = 255)
    @Column(name = "ultimo_nome")
    @NotNull
    private String ultimoNome;
    

    public SecUser() {
    }

    public SecUser(Long id) {
        this.id = id;
    }

    public SecUser(Long id, long version, boolean accountExpired, boolean accountLocked, String email, boolean enabled, String password, boolean passwordExpired, String username) {
        this.id = id;
        this.version = version;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.passwordExpired = passwordExpired;
        this.username = username;
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

    public boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }


    
/*    public Set<SecRole> getRoles() {
      return secRoles;
   }    

  */  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUser)) {
            return false;
        }
        SecUser other = (SecUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.gov.ce.idace.entity.auth.SecUser[ id=" + id + " ]";
    }
    
}
