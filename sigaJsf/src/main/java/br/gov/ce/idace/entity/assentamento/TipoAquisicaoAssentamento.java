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
public enum TipoAquisicaoAssentamento {
    
    DOACAO(1,"Doação"),
    COMPRA(2,"Compra"),
    DESAPROPRIACAO(3,"Desapropriação");
    
    private final int codigo;
    private final String tipoAquisicaoAssentamento;

    private TipoAquisicaoAssentamento(int codigo, String tipoAquisicaoAssentamento) {
        this.codigo = codigo;
        this.tipoAquisicaoAssentamento = tipoAquisicaoAssentamento;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTipoAquisicaoAssentamento() {
        return tipoAquisicaoAssentamento;
    }
    
    
        
    
}
