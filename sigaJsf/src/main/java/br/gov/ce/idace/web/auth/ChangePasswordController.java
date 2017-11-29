/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;


import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.auth.SecUserFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Carlos.Santos
 */
@Named("changePasswordController")
@SessionScoped
public class ChangePasswordController implements Serializable {
    
    @EJB
    private SecUserFacade ejbUserFacade;
    
    @PersistenceContext(unitName = "sigaPU")
    private EntityManager em;
    
    private SecUser usuarioTroca;
    private String senhaAntiga;
    private String novaSenha;
    
    public ChangePasswordController(){
        
        
    }
    
    @PostConstruct
    public void init() {
        String nomeUsuarioLogado = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        TypedQuery<SecUser> query = em.createNamedQuery("SecUser.findByUsername", SecUser.class).setParameter("username", nomeUsuarioLogado);
        usuarioTroca = query.getSingleResult();
    }

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
    
    public String getNovaSenha(){
        return novaSenha;
    }
    
    public void setNovaSenha(String novaSenha){
        this.novaSenha = novaSenha;
    }
    
    public String trocasenha(){
        String senhaUsuario = usuarioTroca.getPassword();
        if (!verificarSenhaEncriptada(senhaAntiga,senhaUsuario)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Senha antiga não confere !"));
            return "ChangePassword";
        }else if (senhaAntiga.equals(novaSenha)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "A nova senha não pode ser igual a senha antiga !"));
            return "ChangePassword";
        } else {
           String novaSenhaEncriptada = DigestUtils.sha256Hex(novaSenha); 
           usuarioTroca.setPassword(novaSenhaEncriptada);
           try {
               ejbUserFacade.edit(usuarioTroca);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Senha alterada com sucesso !"));
               return "/faces/view/modulos.xhtml";
               
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
