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
public enum MotivoAlteracaoCasal {
    
    INCLUSAO (0,"Inclusão"),
    NAOCONVIVENCIA (1,"Declaração de não convivência"),
    UNIAOESTAVEL (2,"Declaração de união estável"),
    APOSENTADORIA(3,"Aposentadoria"),
    FALECIMENTO(4,"Falecimento");
    
    private final int codigo;
    private final String motivoAlteracao;

    private MotivoAlteracaoCasal(int codigo, String motivoAlteracao) {
        this.codigo = codigo;
        this.motivoAlteracao = motivoAlteracao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMotivoAlteracao() {
        return motivoAlteracao;
    }
    
    
    
}
