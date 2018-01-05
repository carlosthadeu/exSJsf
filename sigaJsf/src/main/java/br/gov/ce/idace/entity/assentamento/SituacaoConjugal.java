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
public enum SituacaoConjugal {
    CONJUGE (1,"Conjuge"),
    COMPANHEIRO (2,"Companheiro");
    
    private final int codigo;
    private final String situacaoConjugal;

    private SituacaoConjugal(int codigo, String situacaoConjugal) {
        this.codigo = codigo;
        this.situacaoConjugal = situacaoConjugal;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getSituacaoConjugal() {
        return situacaoConjugal;
    }
    
    
    
}

    

