/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.SecRole;
import br.gov.ce.idace.web.util.JsfUtil;
import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.auth.SecRoleFacade;
import br.gov.ce.idace.session.auth.SecUserFacade;
import br.gov.ce.idace.web.IControllerService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
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
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author Carlos.Santos
 */
@Named("secUserController")
@SessionScoped
public class SecUserController implements Serializable, IControllerService {

    private static final long serialVersionUID = -1823677040218557287L;

    @EJB
    private SecUserFacade ejbFacade;
    @EJB
    private SecRoleFacade ejbRoleFacade;

    private DataModel secUsers = null;
    private SecUser current;
    private SecRole selectedRole;

    /* utilizadas no p:selectOneMenu do detail */
    private List<SecRole> listaRoles;
    private SecRole roleParaAdicionar;

    /* utilizadas no p:selectOneMenu */
    private DataModel items = null;
    private Map<String, String> criteriaParams;
    private String paramNome;
    private String paramSobrenome;
    private String paramSetor;

    public SecUserController() {
    }

    public Map<String, String> getCriteriaParams() {
        return criteriaParams;
    }

    public void setCriteriaParams(Map<String, String> criteriaparams) {
        this.criteriaParams = criteriaparams;
    }

    public String getParamNome() {
        return paramNome;
    }

    public void setParamNome(String paramNome) {
        this.paramNome = paramNome;
    }

    public String getParamSobrenome() {
        return paramSobrenome;
    }

    public void setParamSobrenome(String paramSobrenome) {
        this.paramSobrenome = paramSobrenome;
    }

    public String getParamSetor() {
        return paramSetor;
    }

    public void setParamSetor(String paramSetor) {
        this.paramSetor = paramSetor;
    }

    public SecUser getSelected() {
        if (current == null) {
            current = new SecUser();
        }
        return current;
    }

    private SecUserFacade getFacade() {
        return ejbFacade;
    }

    public SecUser findByUserName(String username) {
        Map<String, String> parametroConsulta = new HashMap<String, String>();
        parametroConsulta.put("username", username);
        return ejbFacade.findByNamedQuerySingle("SecUser.findByUsername", parametroConsulta);
    }

    public DataModel getSecUsers() {
        if (secUsers == null) {
            secUsers = new ListDataModel(ejbFacade.findAll());
        }
        return secUsers;
    }

    public void setSelectedRole(SecRole selectedRole) {
        this.selectedRole = selectedRole;
    }

    public SecRole getSelectedRole() {
        return selectedRole;
    }

    public SecRole getRoleParaAdicionar() {
        return roleParaAdicionar;
    }

    public void setRoleParaAdicionar(SecRole roleParaAdicionar) {
        this.roleParaAdicionar = roleParaAdicionar;
    }

    public List<SecRole> getListaRoles() {
        listaRoles = ejbRoleFacade.findAll();
        return listaRoles;
    }

    
    public String prepareList() {
        recreateModel();
        return "ListSecUser";
    }

    
    public String prepareView() {
        current = (SecUser) getItems().getRowData();
        return "ViewSecUser";
    }
    
    public String prepareEdit() {
        current = (SecUser) getItems().getRowData();
        return "EditSecUser";
    }

    
    public String prepareCreate() {
        current = new SecUser();

        return "CreateSecUser";
    }

    public SecUser findByUsername(String Username) {
        return (SecUser) getFacade().findByField("username", Username);
    }

    public String create() {
        if (!(findByUserName(current.getUsername()) == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Já existe usuário cadastrado para este login !"));
            return "CreateSecUser";
        } else if (!(ejbFacade.findByEmail(current.getEmail()) == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Já existe usuário cadastrado para este email !"));
            return "CreateSecUser";
        } else {
            try {
                // cria a senha padrão como "altereasenha"
                current.setPassword("225214e52da4f114f809f5af606c62169f2d3c0a142d3d36b5ff30e4ae13aea8");
                current.setPasswordExpired(true);
                current.setEnabled(true);
                getFacade().create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O usuário: \"" + current.getUsername() + " - " + current.getPrimeiroNome() + " " + current.getUltimoNome() + "\" foi criado com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do usuário !"));
                return null;
            }
        }

    }

    

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Usuário atualizado com sucesso !"));
            return "ListSecUser";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do usuário !"));
            return null;
        }
    }

    public String updateRoles() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Usuário atualizado com sucesso !"));
            recreateModel();
            return "ListSecUser";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do usuário !"));
            return null;
        }
    }

    public String destroy() {
        current = (SecUser) getItems().getRowData();
        performDestroy();
        recreateModel();
        return "ListSecUser";
    }

    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListSecUser";
    }

    public String destroyAndView() {
        performDestroy();

        return "ViewSecUser";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir o registro");
        }
    }

    public DataModel getItems() {
        if (items == null) {
            Integer i = -1;
            Long ninguem = i.longValue();
            items = new ListDataModel((List) ejbFacade.find(ninguem));
        }
        return items;
    }

  

   

    private void recreateModel() {
        items = null;
    }

    public String performSearch() {
        recreateModel();
        criteriaParams = new HashMap<String, String>();
        if (((paramNome == null) || (paramNome.isEmpty())) && ((paramSobrenome == null) || (paramSobrenome.isEmpty()))) {
            items = new ListDataModel(ejbFacade.findAll());
        } else {
            if ((paramNome != null) || (!paramNome.isEmpty())) {
                criteriaParams.put("primeiroNome", paramNome);
            }
            if ((paramSobrenome != null) || (!paramSobrenome.isEmpty())) {
                criteriaParams.put("ultimoNome", paramSobrenome);
            }
            items = new ListDataModel(ejbFacade.findLike(criteriaParams));
        }
        paramNome = "";
        paramSobrenome = "";
        return "ListSecUser";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SecUser getSecUser(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    public String expirarSenha() {
        try {
            // muda a senha do usuário para a senha padrão altereasenha
            current.setPassword("225214e52da4f114f809f5af606c62169f2d3c0a142d3d36b5ff30e4ae13aea8");
            current.setPasswordExpired(true);
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Senha do usuário foi expirada com sucesso !"));
            recreateModel();
            return "ListSecUser";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao expirar a senha do usuário !"));
            return null;
        }
    }
    
    public String desabilitarHabilitarUsuario() {
        try {
            current = (SecUser) getItems().getRowData();
            current.setEnabled(!(current.getEnabled()));
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Status do usuário foi alterado com sucesso !"));
            recreateModel();
            return "ListSecUser";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao alterar o status do usuário !"));
            return null;
        }
    }

    @FacesConverter(forClass = SecUser.class)
    public static class SecUserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecUserController controller = (SecUserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secUserController");
            return controller.getSecUser(getKey(value));
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
            if (object instanceof SecUser) {
                SecUser o = (SecUser) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SecUser.class.getName());
            }
        }

    }

}
