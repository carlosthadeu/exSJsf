/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.pessoa;

import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.session.assentamento.AssentadoFacade;
import br.gov.ce.idace.session.pessoa.EstadoCivilFacade;
import java.io.Serializable;
import java.util.List;
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
 * @author carlos.santos
 */
@Named("estadoCivilController")
@SessionScoped
public class EstadoCivilController implements Serializable {

    @EJB
    private EstadoCivilFacade ejbFacade;
    @EJB
    AssentadoFacade assentadoFacade;

    private EstadoCivil current;
    private DataModel items;

    public EstadoCivilFacade getEjbFacade() {
        return ejbFacade;
    }

    public AssentadoFacade getAssentadoFacade() {
        return assentadoFacade;
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel((List) ejbFacade.findAll());
        }
        return items;
    }

    public EstadoCivil getSelected() {
        if (current == null) {
            current = new EstadoCivil();
        }
        return current;
    }

    public EstadoCivilController() {
    }

    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListEstadoCivil";
    }

    public String prepareView() {
        current = (EstadoCivil) getItems().getRowData();
        return "ViewEstadoCivil";
    }

    public String prepareEdit() {
        current = (EstadoCivil) getItems().getRowData();
        return "EditEstadoCivil";
    }

    public String update() {
        try {
            getEjbFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Estado civil atualizado com sucesso !"));
            return "ListEstadoCivil";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do estado civil !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new EstadoCivil();
        return "CreateEstadoCivil";
    }

    public String create() {
        if (ejbFacade.haEstadoCivil(current.getEstadoCivil())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Já existe um tipo de estado civil com esta mesma descrição !"));
            return "CreateEstadoCivil";
        } else {
            try {

                ejbFacade.create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O estado civil foi criada com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do estado civil !"));
                return null;
            }
        }
    }

    public String destroy() {
        current = (EstadoCivil) getItems().getRowData();
        if (!assentadoFacade.findAssentadosByEstadoCivil(current).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir o estado civil pois há assentados associados à ele. Exclua os assentados antes de excluir o estado civil !"));
            current = null;
            return "ListEstadoCivil";
        } else {
            performDestroy();
            recreateModel();
            return "ListEstadoCivil";
        }
    }

    private void performDestroy() {
        try {
            getEjbFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o estado civil !"));
        }
    }

    public EstadoCivil getEstadoCivil(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = EstadoCivil.class)
    public static class EstadoCivilControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstadoCivilController controller = (EstadoCivilController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estadoCivilController");
            return controller.getEstadoCivil(getKey(value));
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
            if (object instanceof EstadoCivil) {
                EstadoCivil o = (EstadoCivil) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EstadoCivil.class.getName());
            }
        }

    }
}
