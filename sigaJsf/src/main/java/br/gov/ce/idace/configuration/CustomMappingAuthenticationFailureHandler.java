/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

/**
 *
 * @author Carlos.Santos
 */
public class CustomMappingAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    public CustomMappingAuthenticationFailureHandler() {
        Map<String, String> mappings = new HashMap<String, String>();
        mappings.put("org.springframework.security.authentication.CredentialsExpiredException", "/faces/unsecure/ChangePasswordExpired.xhtml");
        mappings.put("org.springframework.security.authentication.LockedException", "/faces/unsecure/usuarioBloqueado.xhtml");
        mappings.put("org.springframework.security.authentication.BadCredentialsException", "/faces/unsecure/login.xhtml");
        mappings.put("org.springframework.security.core.userdetails.UsernameNotFoundException", "/faces/unsecure/login.xhtml");

        this.setExceptionMappings(mappings);
    }
    
    
    @Override
     public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
                                    javax.servlet.http.HttpServletResponse response,
                                    AuthenticationException exception)
                             throws IOException,
                                    javax.servlet.ServletException{
         saveException(request, exception); 
         super.onAuthenticationFailure(request, response, exception);
         
     }
    
}
