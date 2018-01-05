/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;

import br.gov.ce.idace.entity.assentamento.Assentamento;
import br.gov.ce.idace.entity.assentamento.TipoAquisicaoAssentamento;
import br.gov.ce.idace.entity.assentamento.TipoAssentamento;
import br.gov.ce.idace.session.assentamento.AssentamentoFacade;
import br.gov.ce.idace.session.assentamento.TipoAssentamentoFacade;
import br.gov.ce.idace.core.entity.ibge.Municipio;
import br.gov.ce.idace.session.ibge.MunicipioFacade;
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
@Named("assentamentoController")
@SessionScoped
public class AssentamentoController implements Serializable {

    private static final long serialVersionUID = -1918328391376620287L;

    @EJB
    private AssentamentoFacade ejbFacade;
    
    @EJB
    private TipoAssentamentoFacade tipoAssentamentoFacade;
    
    @EJB
    private MunicipioFacade MunicipioFacade;
    
    
    
    private List<TipoAssentamento> listaTipoAssentamentos;
    private List<Municipio> listaMunicipio;
    
    private Municipio paramMunicipio;
    private String paramNomeAssentamento;

    //private DataModel assentamentos = null;
    private Assentamento current;
    private DataModel items = null;

    public AssentamentoController() {
    }

    public Assentamento getSelected() {
        if (current == null) {
            current = new Assentamento();
        }
        return current;
    }

    private AssentamentoFacade getFacade() {
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

    private void recreateModel() {
        items = null;
    }
    
    public boolean getHaDados(){
        if (items == null) {
            items = new ListDataModel((List) ejbFacade.findAll());
        }
        return(items.getRowCount()>0 ) ;
    }

    public List<TipoAssentamento> getListaTipoAssentamentos() {
        listaTipoAssentamentos = tipoAssentamentoFacade.tipoAssentamentoOrderByTipo();
        return listaTipoAssentamentos;
    }

    
    
    public List<Municipio> getListaMunicipio() {
        if (listaMunicipio == null) listaMunicipio = MunicipioFacade.MunicipiosCeara();
        return listaMunicipio;
    }

    public Municipio getParamMunicipio() {
        return paramMunicipio;
    }

    public void setParamMunicipio(Municipio paramMunicipio) {
        this.paramMunicipio = paramMunicipio;
    }

    public String getParamNomeAssentamento() {
        return paramNomeAssentamento;
    }

    public void setParamNomeAssentamento(String paramNomeAssentamento) {
        this.paramNomeAssentamento = paramNomeAssentamento;
    }
        

    public String prepareList() {
        recreateModel();
        return "ListAssentamento";
    }

    public String prepareView() {
        current = (Assentamento) getItems().getRowData();
        return "ViewAssentamento";
    }

    public String prepareEdit() {
        current = (Assentamento) getItems().getRowData();
        return "EditAssentamento";
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Assentamento atualizado com sucesso !"));
            return "ListAssentamento";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do entrevistador !"));
            return null;
        }
    }
    
    

    public String prepareCreate() {
        current = new Assentamento();
        return "CreateAssentamento";
    }

    public String create() {
        try {
            getFacade().create(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O Assentamento: \"" + current.getAssentamento() + "\" foi criado com sucesso !"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do assentamento !"));
            return null;
        }

    }

    public String destroy() {
        current = (Assentamento) getItems().getRowData();
        /*if (!current.getEntrevistas().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir o assentamento pois há entrevistas associadas à ele. Exclua as entrevistas antes de excluir o assentamento !"));
            current = null;
            return "ListAssentamento";
        } else { */
            performDestroy();
            recreateModel();
            return "ListAssentamento";
      //  }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o Sistema !"));
        }
    }
    
    public String performSearch(){
        items = new ListDataModel((List) ejbFacade.FindByMunicipioNome(paramMunicipio, paramNomeAssentamento));
        paramMunicipio = null;
        paramNomeAssentamento = null;
        return "ListAssentamento";
    }
    
    public String prepareListFromFamilia() {
        recreateModel();
        return "/faces/view/assentamento/assentamento/ListAssentamento?faces-redirect=true";
    }
    
    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListAssentamento";
    }

    public Assentamento getAssentamento(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    public TipoAquisicaoAssentamento[] getTiposAquisicaoAssentamento(){
        return TipoAquisicaoAssentamento.values();
    }
    
    @FacesConverter(forClass = Assentamento.class)
    public static class AssentamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AssentamentoController controller = (AssentamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "assentamentoController");
            return controller.getAssentamento(getKey(value));
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
            if (object instanceof Assentamento) {
                Assentamento o = (Assentamento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Assentamento.class.getName());
            }
        }

    }
    
    

}
