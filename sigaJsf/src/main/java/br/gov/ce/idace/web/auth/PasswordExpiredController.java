/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.auth.SecUserFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Carlos.Santos
 */
@Named("passwordExpiredController")
@SessionScoped
public class PasswordExpiredController implements Serializable {

    private static final long serialVersionUID = 3644298865072185746L;
    
    @EJB
    private SecUserFacade ejbUserFacade;
    private SecUser usuarioTroca;
    private String senhaAntiga;
    private String novaSenha;
    private String login;

    public SecUser getUsuarioTroca() {
        return usuarioTroca;
    }

    public void setUsuarioTroca(SecUser usuarioTroca) {
        this.usuarioTroca = usuarioTroca;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao logar no sistema: ", "Sua senha está expirada, favor informar nova senha !"));
    }
    
    
    private Boolean verificaUsuario(){
        Map<String, String> parametroConsulta =new HashMap<String, String>();
        parametroConsulta.put("username", login);
        usuarioTroca = ejbUserFacade.findByNamedQuerySingle("SecUser.findByUsername", parametroConsulta);
        return usuarioTroca != null;
        
    }
    
    
    public String trocasenha(){
        if (!verificaUsuario()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Login do usuário não existe !"));
            return "ChangePasswordExpired";
        }else if (!verificarSenhaEncriptada(senhaAntiga,usuarioTroca.getPassword())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Senha antiga não confere !"));
            return "ChangePasswordExpired";
        }else if (senhaAntiga.equals(novaSenha)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "A nova senha não pode ser igual a senha antiga !"));
            return "ChangePasswordExpired";
        } else {
           String novaSenhaEncriptada = DigestUtils.sha256Hex(novaSenha); 
           usuarioTroca.setPassword(novaSenhaEncriptada);
           usuarioTroca.setPasswordExpired(false);
           try {
               ejbUserFacade.edit(usuarioTroca);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Senha alterada com sucesso !"));
               return "/faces/unsecure/login.xhtml";
               
           } catch (Exception e) {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Erro de banco de dados ao alterar a senha !"));
               return "ChangePassword";
           }
        }
    }
        
    public Boolean verificarSenhaEncriptada(String senhaAVerificar, String senhaEncriptada){
        String senhaAVEncriptada = DigestUtils.sha256Hex(senhaAVerificar);
        return senhaEncriptada.equals(senhaAVEncriptada);
    }    
    
}
