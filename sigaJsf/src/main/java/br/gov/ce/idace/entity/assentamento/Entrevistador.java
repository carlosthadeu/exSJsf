/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import br.gov.ce.idace.core.entity.pessoa.PessoaFisica;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="entrevistador",schema = "assentamento",uniqueConstraints = @UniqueConstraint(name = "unq_cpf_entrevistador", columnNames = {"cpf"}))
    @AttributeOverrides({@AttributeOverride(name = "cpf", column = @Column(nullable = false)), @AttributeOverride(name = "nome", column = @Column(nullable = false) )})
public class Entrevistador extends PessoaFisica{

    private static final long serialVersionUID = -915584995167248195L;
    
    public Entrevistador() {
    }
    
    @OneToMany(mappedBy = "entrevistador", targetEntity = Entrevista.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Entrevista> entrevistas;

    public List<Entrevista> getEntrevistas() {
        return entrevistas;
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
        return getCpf();
    }
    
}
