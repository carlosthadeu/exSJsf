/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.configuration;

/**
 *
 * @author Carlos.Santos
 */
import org.springframework.security.web.context.*;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
public SecurityWebApplicationInitializer() {
    super(WebSecurityConfig.class);
 }
}