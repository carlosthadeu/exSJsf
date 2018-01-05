/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name = "familia", schema="assentamento", uniqueConstraints = @UniqueConstraint(name = "unq_codigo", columnNames = {"codigo"}))
public class Familia implements Serializable {

    private static final long serialVersionUID = 7571763419342308758L;
    
    @Id    
    @SequenceGenerator(name="familia_seq", allocationSize = 1, sequenceName="seq_familia", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="familia_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "codigo")
    @Size(min = 3, max = 14)
    private String codigo;
    
    @Column(name="data_desistencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDesistencia;
    
    @ManyToOne
    @JoinColumn(name="assentamento_id")
    private Assentamento assentamento;
    
    @OneToOne(mappedBy = "familia", fetch = FetchType.LAZY)
    private UltimaComposicaoFamiliar ultimaConformacaoCasal;
 

    public Familia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDataDesistencia() {
        return dataDesistencia;
    }

    public void setDataDesistencia(Date dataDesistencia) {
        this.dataDesistencia = dataDesistencia;
    }

    public Assentamento getAssentamento() {
        return assentamento;
    }

    public void setAssentamento(Assentamento assentamento) {
        this.assentamento = assentamento;
    }

    

    public UltimaComposicaoFamiliar getUltimaConformacaoCasal() {
        return ultimaConformacaoCasal;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Familia other = (Familia) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigo ;
    }
}
