/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.configuration;


import br.gov.ce.idace.entity.auth.Funcionalidade;
import br.gov.ce.idace.session.auth.FuncionalidadeFacade;
import javax.naming.InitialContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
//@EnableAspectJAutoProxy
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler;
    
    private FuncionalidadeFacade funcionalidadeFacade;
    private static final String FUNCIONALIDADE_EJB_LOOKUP_PATH = "java:module/FuncionalidadeFacade";



    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/faces/unsecure/**", "/faces/javax.faces.resource/**", "/faces/template/common/**", "/resources/**").permitAll()
                .antMatchers("/faces/view/modulos.xhtml", "/faces/view/auth/moduloControleUsuario.xhtml", "/faces/view/assentamento/moduloAssentamento.xhtml","/faces/view/pessoa/moduloPessoa.xhtml").authenticated()
                .and().logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/faces/unsecure/login.xhtml")
                .and().formLogin()
                .loginPage("/faces/unsecure/login.xhtml")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(exceptionMappingAuthenticationFailureHandler)
                .successForwardUrl("/faces/view/modulos.xhtml")
                .permitAll();
        
        InitialContext initialContext = new InitialContext();
        funcionalidadeFacade = (FuncionalidadeFacade) initialContext.lookup(FUNCIONALIDADE_EJB_LOOKUP_PATH);
        String roles ="";
        String virgula ="";
        for (Funcionalidade func :funcionalidadeFacade.findAll()){
            for(String role : funcionalidadeFacade.findRolesByFuncionalidade(func)){
               roles = roles.concat(virgula);
               roles = roles.concat(role.replace("ROLE_", ""));
               virgula = ", ";
            }
            http
                    .authorizeRequests()                    
                    .antMatchers(func.getUrl()).hasAnyRole(roles);
            virgula = "";        
            roles = "";
        }
         
        http.authorizeRequests().anyRequest().denyAll();
        http.exceptionHandling().accessDeniedPage("/faces/view/erro/accesdenied.xhtml");

    }

    @Bean
    public ShaPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(256);
    }

    @Bean
    public ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler() {

        return new CustomMappingAuthenticationFailureHandler();

    }

    
    

}
