/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

/**
 *
 * @author carlos.santos
 */

public enum Nacionalidade {
    BRASILEIRA (0,"Brasileira"),
    NATURALIZADA (1,"Naturalizada"),
    ESTRANGEIRA (2,"Estrangeira");
    
    private final int codigo;
    private final String nacionalidade;

    private Nacionalidade(int codigo, String nacionalidade) {
        this.codigo = codigo;
        this.nacionalidade = nacionalidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }
    
    
    
}

