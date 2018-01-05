/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.Funcionalidade;
import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.SecRole;
import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.auth.FuncionalidadeFacade;
import br.gov.ce.idace.session.auth.ModuloFacade;
import br.gov.ce.idace.session.auth.SecRoleFacade;
import br.gov.ce.idace.session.auth.SecUserFacade;
import br.gov.ce.idace.session.auth.SistemaFacade;
import br.gov.ce.idace.web.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Carlos.Santos
 */
@Named("secRoleController")
@SessionScoped
public class SecRoleController  implements Serializable  {

    private static final long serialVersionUID = 5711088701497112203L;
    
    @EJB
    private SecRoleFacade ejbFacade;
    @EJB
    private ModuloFacade ejbModuloFacade;
    @EJB
    private FuncionalidadeFacade ejbFuncionalidadeFacade;
    @EJB
    private SistemaFacade ejbSistemaFacade;
    @EJB
    private SecUserFacade ejbSecUserFacade;
    
    private SecRole current;
    private DataModel items = null;
    
    /* utilizadas no p:selectOneMenu do detail */
    private List<Sistema> listaSistemas;
    private List<Modulo> listaModulos;
    private List<Funcionalidade> listaFuncionalidades;
    private List<SecUser> listaUsuarios;
    private Sistema paramSistema;
    private Modulo paramModulo;
    private Funcionalidade funcionalidadeParaAdicionar;
    private Funcionalidade selectedFuncionalidade;
    private SecUser selectedSecUser;
    private SecUser usuarioParaAdicionar;
    private String nomeUsuarioParaAdicionar;
    private String sobrenomeUsuarioParaAdicionar;
    
    public SecRoleController(){
        
    }
    

        
    public Set<Funcionalidade> getFuncionalidades() {
        Set<Funcionalidade> funcionalidades = current.getFuncionalidades();
        return funcionalidades;
    }
    
    public Set<SecUser> getSecUsers() {
        Set<SecUser> secUsers = current.getSecUsers();
        return secUsers;
    }
    
    
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel((List) ejbFacade.findAll());
        }
        return items;
    }
    
    public SecRole getSelected() {
        if (current == null) {
            current = new SecRole();
        }
        return current;
    }

    public List<Sistema> getListaSistemas() {
        listaSistemas = ejbSistemaFacade.findAll();
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public List<Funcionalidade> getListaFuncionalidades() {
        return listaFuncionalidades;
    }

    public void setListaFuncionalidades(List<Funcionalidade> listaFuncionalidades) {
        this.listaFuncionalidades = listaFuncionalidades;
    }
    
    /* Lista de usuários utilizada na seleção do usuário a ser adicionado na sec_user_sec_role */

    public List<SecUser> getListaUsuarios() {
        if (listaUsuarios == null) listaUsuarios = new ArrayList<SecUser>();
        if (listaUsuarios.isEmpty()) for(SecUser secUser : ejbSecUserFacade.findAllOrderByUsername()) listaUsuarios.add(secUser);
        return listaUsuarios;
    }

    
    
    /* Lista dos usuários da role, utilizada no datagrid */

    public Set<SecUser> getListaUsuariosRole() {
        Set<SecUser> usuarios = current.getSecUsers();
        return usuarios;
    }

    
    
    

    public Sistema getParamSistema() {
        return paramSistema;
    }

    public void setParamSistema(Sistema paramSistema) {
        this.paramSistema = paramSistema;
    }

    public Modulo getParamModulo() {
        return paramModulo;
    }

    public void setParamModulo(Modulo paramModulo) {
        this.paramModulo = paramModulo;
    }

    public SecUser getUsuarioParaAdicionar() {
        return usuarioParaAdicionar;
    }

    public void setUsuarioParaAdicionar(SecUser usuarioParaAdicionar) {
        this.usuarioParaAdicionar = usuarioParaAdicionar;
    }

    
    
    
    
    public Funcionalidade getFuncionalidadeParaAdicionar() {
        return funcionalidadeParaAdicionar;
    }

    public void setFuncionalidadeParaAdicionar(Funcionalidade funcionalidadeParaAdicionar) {
        this.funcionalidadeParaAdicionar = funcionalidadeParaAdicionar;
    }

    public Funcionalidade getSelectedFuncionalidade() {
        return selectedFuncionalidade;
    }

    public void setSelectedFuncionalidade(Funcionalidade selectedFuncionalidade) {
        this.selectedFuncionalidade = selectedFuncionalidade;
    }

    public SecUser getSelectedSecUser() {
        return selectedSecUser;
    }

    public void setSelectedSecUser(SecUser selectedSecUser) {
        this.selectedSecUser = selectedSecUser;
    }

    public String getNomeUsuarioParaAdicionar() {
        return nomeUsuarioParaAdicionar;
    }

    public void setNomeUsuarioParaAdicionar(String nomeUsuarioParaAdicionar) {
        this.nomeUsuarioParaAdicionar = nomeUsuarioParaAdicionar;
    }

    public String getSobrenomeUsuarioParaAdicionar() {
        return sobrenomeUsuarioParaAdicionar;
    }

    public void setSobrenomeUsuarioParaAdicionar(String sobrenomeUsuarioParaAdicionar) {
        this.sobrenomeUsuarioParaAdicionar = sobrenomeUsuarioParaAdicionar;
    }
    
    private void recreateModel() {
        items = null;
    }
    
    private SecRoleFacade getFacade() {
        return ejbFacade;
    }
    
    public String prepareList() {
        recreateModel();
        return "ListSecRole";
    }
    
    public String prepareView() {
        current = (SecRole) getItems().getRowData();
        return "ViewSecRole";
    }

    public String prepareCreate() {
        current = new SecRole();
        return "CreateSecRole";
    }

    public String prepareEdit() {
        current = (SecRole) getItems().getRowData();
        return "EditSecRole";
    }
    

    public String destroy() {
        if (!current.getFuncionalidades().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é póssível excluir a Role "+current.getAuthority() +" pois existem funcionalidades vinculadas à ela. Desvincule todas as funcionalidades e tente novamente !"));
        } else if (!current.getSecUsers().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é póssível excluir a Role "+current.getAuthority() +" pois existem usuários vinculados à ela. Desvincule todos os usuários e tente novamente !"));
        } else {
            current = (SecRole) getItems().getRowData();
            String nomeRole = current.getAuthority();
            performDestroy();
            recreateModel();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A role: \"" + nomeRole + "\" foi excluída com sucesso !"));
        }
        return "ListSecRole";
    }
    
    public String removeFuncionalidade() {
        try {
            ejbFacade.removeFuncionalidade(selectedFuncionalidade, current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A funcionalidade: \"" + selectedFuncionalidade.getFuncionalidade() + "\" foi removida da role: " + current.getAuthority() + " com sucesso !"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro ao excluir a funcionalidade da role !"));

        }
        return "ViewSecRole";
    }
    
    public String adicionaFuncionalidade(){
        if (current.getFuncionalidades().contains(funcionalidadeParaAdicionar)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "A funcionalidade: \"" + funcionalidadeParaAdicionar.getFuncionalidade() + "\" já pertence a role: " + current.getAuthority() +  " !"));
        }
        else {
            Long atual = current.getId();
            ejbFacade.adicionaFuncionalidade(funcionalidadeParaAdicionar, current);
            current = ejbFacade.find(atual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A funcionalidade: \""+ funcionalidadeParaAdicionar.getFuncionalidade() + "\" foi adicionada a role: " + current.getAuthority() +  " com sucesso !"));
            funcionalidadeParaAdicionar = null;
        }
        return "ViewSecRole";
    }
    
    public String adicionaUsuario(){
        if (current.getSecUsers().contains(usuarioParaAdicionar)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O Usuário: \"" + usuarioParaAdicionar.getUsername() + usuarioParaAdicionar.getPrimeiroNome() + " " + usuarioParaAdicionar.getUltimoNome() + " - " +  "\" já tem direito à role: " + current.getAuthority() +  " !"));
        }
        else {
            Long atual = current.getId();
            ejbFacade.adicionaUsuario(usuarioParaAdicionar, current);
            current = ejbFacade.find(atual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Foi dado ao usuário: \""+ usuarioParaAdicionar.getUsername() + "\" os direitos à role: " + current.getAuthority() +  " com sucesso !"));
            funcionalidadeParaAdicionar = null;
        }
        return "ViewSecRole";
    }
    
    public String removeUsuario(){
        try{
            ejbFacade.removeUsuario(selectedSecUser, current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Foi retirado do usuário: \""+ selectedSecUser.getUsername() + "\" o direito à role: " + current.getAuthority() +  " com sucesso !"));
            
        
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro ao retirar do usuário o direito à role !"));
            
        }
        return "ViewSecRole";
        }

    public String create() {
        try {
            getFacade().create(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A role "+ current.getAuthority() +" foi criada com sucesso !"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao criar a role !"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A role foi atualizada com sucesso !"));
            return "ListSecRole";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao atualizar a role !"));
            return null;
        }
    }

    private void performDestroy() {
        try {
            
            getFacade().remove(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ","A role foi excluída com sucesso !"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir a role !"));
        }
    }
    
    
    public List<SecRole> findAll(){
        return ejbFacade.findAll();
    }

   /*
    Modifica a lista de módulos de acordo com o sistema selecionado
    */
    
    public void onSistemaChange() {
        if(paramSistema !=null && !paramSistema.equals(""))
            listaModulos = ejbModuloFacade.listaModulosSistema(paramSistema);
        else
            listaModulos = (List<Modulo>) new HashMap<String, String>();
    }
    
    /*
    Modifica a lista de funcionalidades de acordo com o sistema selecionado
    */
    public void onModuloChange() {
        if(paramSistema !=null && !paramSistema.equals(""))
            listaFuncionalidades = ejbFuncionalidadeFacade.findBySistemaModulo(paramSistema,paramModulo);
        else
            listaFuncionalidades = (List<Funcionalidade>) new HashMap<String, String>();
    }
    
    public String onUsuarioChange() {
        nomeUsuarioParaAdicionar = usuarioParaAdicionar.getPrimeiroNome();
        sobrenomeUsuarioParaAdicionar = usuarioParaAdicionar.getUltimoNome();
        return "ViewSecRole";
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SecRole getSecRole(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = SecRole.class)
    public static class SecRoleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecRoleController controller = (SecRoleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secRoleController");
            return controller.getSecRole(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SecRole) {
                SecRole o = (SecRole) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecRole.class.getName());
            }
        }

    }
    
}
