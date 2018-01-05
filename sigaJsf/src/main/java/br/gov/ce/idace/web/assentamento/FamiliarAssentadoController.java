/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.core.entity.pessoa.Sexo;
import br.gov.ce.idace.entity.assentamento.FamiliarAssentado;
import br.gov.ce.idace.entity.assentamento.Familia;
import br.gov.ce.idace.entity.assentamento.Parentesco;
import br.gov.ce.idace.session.assentamento.FamiliarAssentadoFacade;
import br.gov.ce.idace.session.assentamento.FamiliaFacade;
import br.gov.ce.idace.session.assentamento.ParentescoFacade;
import br.gov.ce.idace.session.pessoa.EscolaridadeFacade;
import br.gov.ce.idace.web.util.Validadores;
import java.io.Serializable;
import java.util.Date;
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
@Named("familiarAssentadoController")
@SessionScoped
public class FamiliarAssentadoController implements Serializable {

    private static final long serialVersionUID = 127444318813226053L;

    @EJB
    private FamiliarAssentadoFacade ejbFacade;

    @EJB
    private FamiliaFacade familiaFacade;

    private FamiliarAssentado current;

    private Familia familia;

    private DataModel items;

    private List<Parentesco> listaParentesco;

    @EJB
    private ParentescoFacade parentescoFacade;

    @EJB
    private EscolaridadeFacade escolaridadeFacade;

    private List<Escolaridade> listaEscolaridades;

    private Boolean somenteAtivos;

    public FamiliarAssentadoController() {
    }

    public FamiliarAssentado getSelected() {
        if (current == null) {
            current = new FamiliarAssentado();
        }
        return current;
    }

    private FamiliarAssentadoFacade getFacade() {
        return ejbFacade;
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel((List) ejbFacade.findByFamilia(familia, somenteAtivos));
        }
        return items;
    }

    public FamiliaFacade getFamiliaFacade() {
        return familiaFacade;
    }

    public void setFamiliaFacade(FamiliaFacade familiaFacade) {
        this.familiaFacade = familiaFacade;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public FamiliarAssentado getFamiliarAssentado(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    public ParentescoFacade getParentescoFacade() {
        return parentescoFacade;
    }

    public List<Parentesco> getListaParentesco() {
        listaParentesco = parentescoFacade.findAllOrderByNome();
        return listaParentesco;
    }

    public List<Escolaridade> getListaEscolaridades() {
        listaEscolaridades = escolaridadeFacade.EscolaridadeOrderByNome();
        return listaEscolaridades;
    }

    public Boolean getSomenteAtivos() {
        return somenteAtivos;
    }

    public void setSomenteAtivos(Boolean somenteAtivos) {
        this.somenteAtivos = somenteAtivos;
    }

    public void onAtivoChange() {
        items = new ListDataModel((List) ejbFacade.findByFamilia(familia, somenteAtivos));
    }

    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListFamiliarAssentado";
    }

    public String prepareListFromFamilia(Familia familia) {
        this.familia = familia;
        this.somenteAtivos = true;
        recreateModel();
        return "/faces/view/assentamento/familiarAssentado/ListFamiliarAssentado?faces-redirect=true";
    }

    public String prepareView() {
        current = (FamiliarAssentado) getItems().getRowData();
        return "ViewFamiliarAssentado";
    }

    public String prepareEdit() {
        current = (FamiliarAssentado) getItems().getRowData();
        return "EditFamiliarAssentado";
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Familiar atualizado com sucesso !"));
            return "ListFamiliarAssentado";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do familiar !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new FamiliarAssentado();
        current.setFamilia(familia);
        current.setAtivo(Boolean.TRUE);
        return "CreateFamiliarAssentado";
    }

    public String alterarAtivo() {
        try {
            current = (FamiliarAssentado) getItems().getRowData();
            current.setAtivo(!current.getAtivo());
            if (!current.getAtivo()) {
                java.util.Date dataAtual = new Date();
                current.setDataDesativacao(dataAtual);
            } else {
                current.setDataDesativacao(null);
            }
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Familiar atualizado com sucesso !"));
            return "ListFamiliarAssentado";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do familiar !"));
            return null;
        }

    }

    public String create() {
        Validadores valida = new Validadores();
        if (!(current.getCpf() == null) && (!valida.validaCPF(current.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do familiar Inválido !"));
            return "CreateEntrevistador";
        } else {
            try {
                getFacade().create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Famíliar  foi cadastrado com sucesso !"));
                recreateModel();
                return "ListFamiliarAssentado";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do familiar !"));
                return null;
            }
        }
    }

    public String destroy() {
        current = (FamiliarAssentado) getItems().getRowData();
        performDestroy();
        recreateModel();
        return "ListFamilia";

    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o familiar !"));
        }
    }

    public Sexo[] getSexos() {
        return Sexo.values();
    }

    @FacesConverter(forClass = FamiliarAssentado.class)
    public static class FamiliarAssentadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FamiliarAssentadoController controller = (FamiliarAssentadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "familiaController");
            return controller.getFamiliarAssentado(getKey(value));
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
            if (object instanceof FamiliarAssentado) {
                FamiliarAssentado o = (FamiliarAssentado) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FamiliarAssentado.class.getName());
            }
        }

    }

}
