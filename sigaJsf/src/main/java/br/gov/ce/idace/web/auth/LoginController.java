/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Carlos.Santos
 */
@Named("loginController")
@SessionScoped

public class LoginController implements Serializable {

    private static final long serialVersionUID = 3920307229171899090L;

    public LoginController() {
    }
    
    
    
    public void enviarMenssagemErro( String detalhe){
        if (!detalhe.equals("User credentials have expired")){
            if (detalhe.equals("Bad credentials")) detalhe = "O nome do usuário ou senha estão incorretos !";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao logar no sistema ! :", detalhe));
        }        
        
    }
    
}
