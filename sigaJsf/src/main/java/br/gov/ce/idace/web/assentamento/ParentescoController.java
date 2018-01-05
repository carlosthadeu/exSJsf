/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;

import br.gov.ce.idace.entity.assentamento.Parentesco;


import br.gov.ce.idace.session.assentamento.AssentadoFacade;
import br.gov.ce.idace.session.assentamento.ParentescoFacade;

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
@Named("parentescoController")
@SessionScoped
public class ParentescoController implements Serializable {

    private static final long serialVersionUID = 9185946508423718306L;

    @EJB
    private ParentescoFacade ejbFacade;
    @EJB
    AssentadoFacade assentadoFacade;

    private Parentesco current;
    private DataModel items;

    public ParentescoFacade getEjbFacade() {
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

    public Parentesco getSelected() {
        if (current == null) {
            current = new Parentesco();
        }
        return current;
    }

    public ParentescoController() {
    }

    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListParentesco";
    }

    public String prepareView() {
        current = (Parentesco) getItems().getRowData();
        return "ViewParentesco";
    }

    public String prepareEdit() {
        current = (Parentesco) getItems().getRowData();
        return "EditParentesco";
    }

    public String update() {
        try {
            getEjbFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Parentesco atualizada com sucesso !"));
            return "ListParentesco";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização da parentesco !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new Parentesco();
        return "CreateParentesco";
    }

    public String create() {
        if (ejbFacade.haParentesco(current.getParentesco())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Já existe um tipo de parentesco com esta mesma descrição !"));
            return "CreateParentesco";
        } else {
            try {

                ejbFacade.create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A parentesco foi criada com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação da parentesco !"));
                return null;
            }
        }
    }

    public String destroy() {
/*        current = (Parentesco) getItems().getRowData();
        if (!assentadoFacade.findAssentadosByParentesco(current).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir a parentesco pois há assentados associados à ela. Exclua os assentados antes de excluir a parentesco !"));
            current = null;
            return "ListParentesco";
        } else {*/
            performDestroy();
            recreateModel();
            return "ListParentesco";
  //      }
    }

    private void performDestroy() {
        try {
            getEjbFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir a parentesco !"));
        }
    }

    public Parentesco getParentesco(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Parentesco.class)
    public static class ParentescoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParentescoController controller = (ParentescoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parentescoController");
            return controller.getParentesco(getKey(value));
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
            if (object instanceof Parentesco) {
                Parentesco o = (Parentesco) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Parentesco.class.getName());
            }
        }

    }
}
