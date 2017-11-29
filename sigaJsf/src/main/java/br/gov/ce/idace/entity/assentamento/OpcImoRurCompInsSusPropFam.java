/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.entity.assentamento;

/**
 *
 * @author carlos.santos
 * 
 * Opções para o item 03 - É Proprietário de imóvel rural comprovadamente insuficiente para o sustento próprio ou de sua família ? da seção 04 - Outras informações da unidade familiar do formulário Inscrição de Candidata e Candidato ao Programa Nacional de Reforma Agrária
 * do Sistema de Informações de Projetos de Reforma Agrária - SIPRA do Ministério do Desenvolvimento Agrário - Instituto Nacional de Colonização e Reforma Agrária - INCRA
 * 
 */
public enum OpcImoRurCompInsSusPropFam {
    NAO (0,"Não"),
    SIM_AREA_INF_1_MOD (1,"Sim, com área inferior a um módulo"),
    SIM_AREA_SUP_1_MOD (2,"Sim, com área igual ou superior a um módulo");
    
    private final int codigo;
    private final String opcImoRurCompInsSusPropFam;

    private OpcImoRurCompInsSusPropFam(int codigo, String opcImoRurCompInsSusPropFam) {
        this.codigo = codigo;
        this.opcImoRurCompInsSusPropFam = opcImoRurCompInsSusPropFam;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getOpcImoRurCompInsSusPropFam() {
        return opcImoRurCompInsSusPropFam;
    }
}
