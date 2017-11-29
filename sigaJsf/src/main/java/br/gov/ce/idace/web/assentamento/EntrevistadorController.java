/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;

import br.gov.ce.idace.entity.assentamento.Entrevistador;
import br.gov.ce.idace.core.entity.ibge.Estado;
import br.gov.ce.idace.core.entity.pessoa.Sexo;
import br.gov.ce.idace.session.assentamento.EntrevistadorFacade;
import br.gov.ce.idace.web.util.Validadores;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
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
import javax.inject.Named;

/**
 *
 * @author carlos.santos
 */
@Named("entrevistadorController")
@SessionScoped
public class EntrevistadorController implements Serializable {

    @EJB
    private EntrevistadorFacade ejbFacade;

    private DataModel entrevistadores = null;
    private Entrevistador current;
    private DataModel items = null;

    /* Propriedades utilizadas pela pesquisa do ListEntrevistador */
    private Map<String, String> criteriaParams;
    private String paramNome;
    private String paramCpf;

    /* Fim - Propriedades utilizadas pela pesquisa do ListEntrevistador */
    public EntrevistadorController() {
    }

    public Entrevistador getSelected() {
        if (current == null) {
            current = new Entrevistador();
        }
        return current;
    }

    private EntrevistadorFacade getFacade() {
        return ejbFacade;
    }

    public DataModel getItems() {
        if (items == null) {
            Integer i = -1;
            Long ninguem = i.longValue();
            items = new ListDataModel((List) ejbFacade.find(ninguem));
        }
        return items;
    }

    /* Propriedades utilizadas pela pesquisa do ListEntrevistador */
    public Map<String, String> getCriteriaParams() {
        return criteriaParams;
    }

    public void setCriteriaParams(Map<String, String> criteriaParams) {
        this.criteriaParams = criteriaParams;
    }

    public String getParamNome() {
        return paramNome;
    }

    public void setParamNome(String paramNome) {
        this.paramNome = paramNome;
    }

    public String getParamCpf() {
        return paramCpf;
    }

    public void setParamCpf(String paramCpf) {
        this.paramCpf = paramCpf;
    }

    public String performSearch() {
        recreateModel();
        criteriaParams = new HashMap<String, String>();
        if (((paramNome == null) || (paramNome.isEmpty())) && ((paramCpf == null) || (paramCpf.isEmpty()))) {
            items = new ListDataModel(ejbFacade.findAll());
        } else {
            if ((paramNome != null) || (!paramNome.isEmpty())) {
                criteriaParams.put("nome", paramNome);
            }
            if ((paramCpf != null) || (!paramCpf.isEmpty())) {
                criteriaParams.put("cpf", paramCpf);
            }
            items = new ListDataModel(ejbFacade.findLike(criteriaParams));
        }
        paramNome = "";
        paramCpf = "";
        return "ListEntrevistador";
    }

    /* Fim - Propriedades utilizadas pela pesquisa do ListEntrevistador */
    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListEntrevistador";
    }

    public String prepareView() {
        current = (Entrevistador) getItems().getRowData();
        return "ViewEntrevistador";
    }
    
    public String prepareEdit() {
        current = (Entrevistador) getItems().getRowData();
        return "EditEntrevistador";
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Entrevistador atualizado com sucesso !"));
            return "ListEntrevistador";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do entrevistador !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new Entrevistador();
        return "CreateEntrevistador";
    }

    public String create() {
        Validadores valida = new Validadores();
        if (!(current.getCpf() == null) && (!valida.validaCPF(current.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF Inválido !"));
            return "CreateEntrevistador";
        } else if (!(current.getPisPasep() == null) && (!valida.validaPIS(current.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP Inválido !"));
            return "CreateEntrevistador";
        } else {
            try {
                getFacade().create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O entrevistador: \"" + current.getNome() + "\" foi criado com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do entrevistador !"));
                return null;
            }
        }
    }

    public String destroy() {
        current = (Entrevistador) getItems().getRowData();
        if (!current.getEntrevistas().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir o entrevistador pois há entrevistas feitas por à ele. Exclua as entrevistas antes de excluir o entrevistador !"));
            current = null;
            return "ListEntrevistador";
        } else {
            performDestroy();
            recreateModel();
            return "ListEntrevistador";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o Sistema !"));
        }
    }
    
    public String resetSearch() {
        recreateModel();
        getItems();
        return "EntrevistadorUser";
    }

    public Entrevistador getEntrevistador(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    

    /* retorna os valores da enumeração de estados brasileiros */
    public Estado[] getEstados() {
        return Estado.values();
    }
    
    public Sexo[] getSexos() {
        return Sexo.values();
    }

   

    /* Fim para */
    @FacesConverter(forClass = Entrevistador.class)
    public static class EntrevistadorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntrevistadorController controller = (EntrevistadorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "entrevistadorController");
            return controller.getEntrevistador(getKey(value));
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
            if (object instanceof Entrevistador) {
                Entrevistador o = (Entrevistador) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Entrevistador.class.getName());
            }
        }

    }

}
