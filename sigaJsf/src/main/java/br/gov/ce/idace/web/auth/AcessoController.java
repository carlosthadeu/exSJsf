/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.session.auth.FuncionalidadeFacade;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 *
 * @author carlos.santos
 */

@Configuration
public class AcessoController {
    @EJB
    private FuncionalidadeFacade ejbFacade;

    public AcessoController() {
        super();
        throw new NullPointerException();
    }
    
    
    
    @Bean
    public String rolesByFuncionalidade() {
        String resultado = "";
        String virgula = "";
        SecurityExpressionRoot securityExpressionRoot;
        securityExpressionRoot = new SecurityExpressionRoot(SecurityContextHolder.getContext().getAuthentication()) {};
        
            
            for (String role : ejbFacade.findRolesBySistemaModuloFuncionalidade("SIGA JSF", "Segurança", "Cadastro de usuarios")) {
                resultado = resultado.concat(virgula + "'" + role + "'");
                virgula = ", ";
            }
        
        return resultado;
    }
    
}
