/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import br.gov.ce.idace.core.entity.pessoa.PessoaFisica;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="familiar_assentado",schema = "assentamento",uniqueConstraints = @UniqueConstraint(name = "unq_cpf_fam_assentado", columnNames = {"cpf"}))
public class FamiliarAssentado extends PessoaFisica{
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getId());
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
        final FamiliarAssentado other = (FamiliarAssentado) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }
}
