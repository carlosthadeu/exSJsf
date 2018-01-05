/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

import br.gov.ce.idace.core.entity.ibge.Municipio;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="assentamento", schema="assentamento", uniqueConstraints = {@UniqueConstraint(name = "unq_codigo", columnNames = {"codigo"})})
public class Assentamento implements Serializable {

    private static final long serialVersionUID = -5796372744281277826L;
    
    @Id    
    @SequenceGenerator(name="assentamento_seq", allocationSize = 1, sequenceName="seq_assentamento", schema = "assentamento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="assentamento_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "codigo")
    @NotNull
    @Size(min = 3, max = 9)
    private String codigo;
    
    @Column(name = "assentamento")
    @NotNull
    @Size(min = 3, max = 100)
    private String assentamento;
    
    @Column(name = "capacidade", precision = 6, scale = 2)
    private Long capacidade;
    
    @Column(name = "area", precision = 6, scale = 2)
    private Double area;
    
    @Formula(value =  "(select count(*) from assentamento.familia f where f.assentamento_id = id )")
    private Integer quantidadeFamilias;
    
    @Column(name = "tipo_aquisicao_assentamento")
    @NotNull
    private TipoAquisicaoAssentamento tipoAquisicaoAssentamento;
    
      
    @ManyToOne
    @JoinColumn(name = "tipo_assentamento_id")
    @NotNull
    private TipoAssentamento tipoAssentamento;
    
    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @NotNull
    private Municipio municipio;

   
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

    public String getAssentamento() {
        return assentamento;
    }

    public void setAssentamento(String assentamento) {
        this.assentamento = assentamento;
    }

    public Long getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Long capacidade) {
        this.capacidade = capacidade;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    
    public TipoAquisicaoAssentamento getTipoAquisicaoAssentamento() {
        return tipoAquisicaoAssentamento;
    }

    public void setTipoAquisicaoAssentamento(TipoAquisicaoAssentamento tipoAquisicaoAssentamento) {
        this.tipoAquisicaoAssentamento = tipoAquisicaoAssentamento;
    }

    public TipoAssentamento getTipoAssentamento() {
        return tipoAssentamento;
    }

    public void setTipoAssentamento(TipoAssentamento tipoAssentamento) {
        this.tipoAssentamento = tipoAssentamento;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    
    

    public Integer getQuantidadeFamilias() {
        return quantidadeFamilias;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Assentamento other = (Assentamento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
