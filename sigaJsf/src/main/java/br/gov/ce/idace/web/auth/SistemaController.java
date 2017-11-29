/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.auth.ModuloFacade;
import br.gov.ce.idace.session.auth.SistemaFacade;
import br.gov.ce.idace.web.util.JsfUtil;

import java.io.Serializable;
import java.util.HashMap;
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
import javax.inject.Named;

/**
 *
 * @author Carlos.Santos
 */
@Named("sistemaController")
@SessionScoped
public class SistemaController implements Serializable {

    @EJB
    private SistemaFacade ejbFacade;
    @EJB
    private ModuloFacade moduloFacade;
    private DataModel items = null;
    private DataModel modulos = null;
    private Sistema current;
    private String nomeModuloAdicionar;
    private Modulo moduloAdicionar;
    private Modulo moduloSelecionado;
    


    public SistemaController() {
    }

    public Sistema getSelected() {
        if (current == null) {
            current = new Sistema();
        }
        return current;
    }

    private SistemaFacade getFacade() {
        return ejbFacade;
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(ejbFacade.findAll());
        }
        return items;
    }

    public DataModel getModulos() {
        if (modulos == null) {
            modulos = new ListDataModel(moduloFacade.listaModulosSistema(current));
        }
        return modulos;
    }
    
    

    public Sistema getSistema(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    public String getNomeModuloAdicionar() {
        return nomeModuloAdicionar;
    }

    public void setNomeModuloAdicionar(String nomeModuloAdicionar) {
        this.nomeModuloAdicionar = nomeModuloAdicionar;
    }

    public ModuloFacade getModuloFacade() {
        return moduloFacade;
    }

    public void setModuloFacade(ModuloFacade moduloFacade) {
        this.moduloFacade = moduloFacade;
    }

   

    public Modulo getModuloSelecionado() {
        return moduloSelecionado;
    }

    public void setModuloSelecionado(Modulo moduloSelecionado) {
        this.moduloSelecionado = moduloSelecionado;
    }
    
    

    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListSistema";
    }

    public String prepareView() {
        current = (Sistema) getItems().getRowData();
        return "ViewSistema";
    }

    public String prepareCreate() {
        current = new Sistema();

        return "CreateSistema";
    }

    public String prepareEdit() {
        current = (Sistema) getItems().getRowData();
        return "EditSistema";
    }
    

    public String destroy() {
        current = (Sistema) getItems().getRowData();
        if (!current.getModulos().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir o sistema pois há módulos atrelados à ele. Exclua os módulos antes de excluir o sistema !"));
            current = null;
            return "ListSistema";
        }
        else{
            
            performDestroy();
            recreateModel();
            return "ListSistema";
        }
    }

    public String create() {
        try {
            getFacade().create(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O Sistema foi criado com sucesso !"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao criar o sistema !"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O Sistema foi atualizado com sucesso !"));
            return "ListSistema";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao atualizar o Sistema !"));
            return null;
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o Sistema !"));
        }
    }

    public String adicionaModulo() {
        Map<String, String> parametroConsulta = new HashMap<>();
        parametroConsulta.put("sistema", current.getSistema());
        parametroConsulta.put("modulo", nomeModuloAdicionar);
        Modulo verificaModulo = moduloFacade.findByNamedQuerySingle("Modulo.findBySistemaNomeModulo", parametroConsulta);
        if (verificaModulo != null) {
            JsfUtil.addErrorMessage("Já existe um módulo com o nome " + verificaModulo.getModulo() + "No sistema " + current.getSistema());
        } else {
            moduloAdicionar = new Modulo();
            moduloAdicionar.setModulo(nomeModuloAdicionar);
            moduloAdicionar.setSistema(current);
            
            try {
                getModuloFacade().create(moduloAdicionar);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O módulo foi adicionado com sucesso ao sistema !"));
                moduloAdicionar = null;
                nomeModuloAdicionar = null;
                modulos = null;
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao criar o módulo no sistema !"));
                return null;
            }

        }

        return "ViewSistema";
    }
    
    public String removeModulo() {
         
        try {
            Long idSistema = current.getId();
            getFacade().removeModulo(moduloSelecionado,current);
            /* Atualizando o sistema (parent) com as últimas alterações no BD */
            current = null;
            current = getFacade().find(idSistema);
            
            moduloSelecionado = null;
            modulos = new ListDataModel(moduloFacade.listaModulosSistema(current));
            return "ViewSistema";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o Sistema !"));
            return null;
        }
        
        
    }

    @FacesConverter(forClass = Sistema.class)
    public static class SistemaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SistemaController controller = (SistemaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sistemaController");
            return controller.getSistema(getKey(value));
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
            if (object instanceof Sistema) {
                Sistema o = (Sistema) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Sistema.class.getName());
            }
        }
    }

}
