/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author carlos.santos
 */
public interface IControllerService {
    
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public String prepareList(); 
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String prepareView();
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String prepareCreate();
            
}
