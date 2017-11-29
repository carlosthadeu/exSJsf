/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.core.entity.ibge;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author carlos.santos
 */
@Entity
@Table(name="municipios", schema="ibge")
@NamedQueries({@NamedQuery(name="municipiosCeara", query="SELECT m FROM Municipio m where m.idUf = '23' order by geocodigo")})
public class Municipio implements Serializable {
    @Id
    @Column(name="geom_id")
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "geocodigo")
    private String geocodigo;
    
    @Column(name="uf")
    private String uf;
    
    @Column(name="id_uf")
    private String idUf;
    
    @Column(name="regiao")
    private String regiao;
    
    @Column(name="mesoregiao")
    private String mesoregiao;
    
    @Column(name="mesoregia")
    private String microregiao;
    
    @Column(name="latitude")
    private Double latitude;
    
    @Column(name="longitude")
    private Double longitude;

    public Municipio() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeocodigo() {
        return geocodigo;
    }

    public void setGeocodigo(String geocodigo) {
        this.geocodigo = geocodigo;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIdUf() {
        return idUf;
    }

    public void setIdUf(String idUf) {
        this.idUf = idUf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getMesoregiao() {
        return mesoregiao;
    }

    public void setMesoregiao(String mesoregiao) {
        this.mesoregiao = mesoregiao;
    }

    public String getMicroregiao() {
        return microregiao;
    }

    public void setMicroregiao(String microregiao) {
        this.microregiao = microregiao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.geocodigo);
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
        final Municipio other = (Municipio) obj;
        if (!Objects.equals(this.geocodigo, other.geocodigo)) {
            return false;
        }
        return true;
    }
    
}
