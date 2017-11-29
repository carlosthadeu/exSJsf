/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.core.entity.pessoa;

/**
 *
 * @author carlos.santos
 */
public enum Sexo {
    M (0,"Masculino"),
    F (1,"Femino");
    
    private final int codigo;
    private final String sexo;

    private Sexo(int codigo, String sexo) {
        this.codigo = codigo;
        this.sexo = sexo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getSexo() {
        return sexo;
    }
    
    
    
}
