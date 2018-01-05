/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.pessoa;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.session.assentamento.AssentadoFacade;
import br.gov.ce.idace.session.pessoa.EscolaridadeFacade;
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
@Named("escolaridadeController")
@SessionScoped
public class EscolaridadeController implements Serializable {

    private static final long serialVersionUID = 9185946508423718306L;

    @EJB
    private EscolaridadeFacade ejbFacade;
    @EJB
    AssentadoFacade assentadoFacade;

    private Escolaridade current;
    private DataModel items;

    public EscolaridadeFacade getEjbFacade() {
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

    public Escolaridade getSelected() {
        if (current == null) {
            current = new Escolaridade();
        }
        return current;
    }

    public EscolaridadeController() {
    }

    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListEscolaridade";
    }

    public String prepareView() {
        current = (Escolaridade) getItems().getRowData();
        return "ViewEscolaridade";
    }

    public String prepareEdit() {
        current = (Escolaridade) getItems().getRowData();
        return "EditEscolaridade";
    }

    public String update() {
        try {
            getEjbFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Escolaridade atualizada com sucesso !"));
            return "ListEscolaridade";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização da escolaridade !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new Escolaridade();
        return "CreateEscolaridade";
    }

    public String create() {
        if (ejbFacade.haEscolaridade(current.getEscolaridade())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Já existe um tipo de escolaridade com esta mesma descrição !"));
            return "CreateEscolaridade";
        } else {
            try {

                ejbFacade.create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A escolaridade foi criada com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação da escolaridade !"));
                return null;
            }
        }
    }

    public String destroy() {
        current = (Escolaridade) getItems().getRowData();
        if (!assentadoFacade.findAssentadosByEscolaridade(current)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir a escolaridade pois há assentados associados à ela. Exclua os assentados antes de excluir a escolaridade !"));
            current = null;
            return "ListEscolaridade";
        } else {
            performDestroy();
            recreateModel();
            return "ListEscolaridade";
        }
    }

    private void performDestroy() {
        try {
            getEjbFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir a escolaridade !"));
        }
    }

    public Escolaridade getEscolaridade(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Escolaridade.class)
    public static class EscolaridadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EscolaridadeController controller = (EscolaridadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "escolaridadeController");
            return controller.getEscolaridade(getKey(value));
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
            if (object instanceof Escolaridade) {
                Escolaridade o = (Escolaridade) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Escolaridade.class.getName());
            }
        }

    }
}
