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
 * Opções para o item 04 - É trabalhador rural, assalariado, posseiro, parceiro, arrendatário, foreiro ou sem terra ? da seção 04 - Outras informações da unidade familiar do formulário Inscrição de Candidata e Candidato ao Programa Nacional de Reforma Agrária
 * do Sistema de Informações de Projetos de Reforma Agrária - SIPRA do Ministério do Desenvolvimento Agrário - Instituto Nacional de Colonização e Reforma Agrária - INCRA
 * 
 */
public enum TrabRurAssPosParcArredForSemTer {
    NAO (0,"Não"),
    SIM_FORA_IMO_OBT (1,"Sim, fora do imóvel em obtenção"),
    SIM_NO_IMO_OBT (2,"Sim, no imóvel em obtenção");
    
    private final int codigo;
    private final String trabRurAssPosParcArredForSemTer;

    private TrabRurAssPosParcArredForSemTer(int codigo, String trabRurAssPosParcArredForSemTer) {
        this.codigo = codigo;
        this.trabRurAssPosParcArredForSemTer = trabRurAssPosParcArredForSemTer;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTrabRurAssPosParcArredForSemTer() {
        return trabRurAssPosParcArredForSemTer;
    }
    
}
