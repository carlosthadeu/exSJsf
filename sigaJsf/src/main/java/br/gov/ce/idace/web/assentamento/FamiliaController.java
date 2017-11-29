/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;

import br.gov.ce.idace.core.entity.pessoa.Escolaridade;
import br.gov.ce.idace.entity.assentamento.Assentado;
import br.gov.ce.idace.entity.assentamento.Assentamento;
import br.gov.ce.idace.entity.assentamento.Casal;
import br.gov.ce.idace.entity.assentamento.Familia;
import br.gov.ce.idace.core.entity.ibge.Estado;
import br.gov.ce.idace.core.entity.ibge.Municipio;
import br.gov.ce.idace.core.entity.pessoa.EstadoCivil;
import br.gov.ce.idace.core.entity.pessoa.Sexo;
import br.gov.ce.idace.entity.assentamento.Nacionalidade;
import br.gov.ce.idace.session.assentamento.FamiliaFacade;
import br.gov.ce.idace.session.ibge.MunicipioFacade;
import br.gov.ce.idace.session.pessoa.EscolaridadeFacade;
import br.gov.ce.idace.session.pessoa.EstadoCivilFacade;
import br.gov.ce.idace.web.util.Validadores;
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
import javax.inject.Named;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author carlos.santos
 */
@Named("familiaController")
@SessionScoped
public class FamiliaController implements Serializable {
    @EJB
    private FamiliaFacade ejbFacade;
    
    @EJB
    private EscolaridadeFacade escolaridadeFacade;
    @EJB
    private EstadoCivilFacade estadoCivilFacade;
    @EJB
    private MunicipioFacade municipioFacade;
    
    private Familia current;
    private Assentamento assentamento;
    private DataModel items;
    private boolean incluirDesistencia = false;
    private boolean skip;
    
    private Map<String, String> criteriaParams;
    private String paramAssentado;
    private List<Escolaridade> listaEscolaridades;
    private List<EstadoCivil> listaEstadoCivil;
    private List<Municipio> listaMunNasc;
    private Assentado novoAssentado;
    private Assentado novoConjuge;
    private Casal novoCasal;
    
    public FamiliaController() {
    }

    public Familia getSelected() {
        if (current == null) {
            current = new Familia();
        }
        return current;
    }

    private FamiliaFacade getFacade() {
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

    public Assentamento getAssentamento() {
        return assentamento;
    }

    public void setAssentamento(Assentamento assentamento) {
        this.assentamento = assentamento;
    }
    
    private void recreateModel() {
        items = null;
    }

    public boolean isIncluirDesistencia() {
        return incluirDesistencia;
    }

    public void setIncluirDesistencia(boolean incluirDesistencia) {
        this.incluirDesistencia = incluirDesistencia;
    }

    public Map<String, String> getCriteriaParams() {
        return criteriaParams;
    }

    public void setCriteriaParams(Map<String, String> criteriaParams) {
        this.criteriaParams = criteriaParams;
    }

    public String getParamAssentado() {
        return paramAssentado;
    }

    public void setParamAssentado(String paramAssentado) {
        this.paramAssentado = paramAssentado;
    }

    public Assentado getNovoAssentado() {
        return novoAssentado;
    }

    public void setNovoAssentado(Assentado novoAssentado) {
        this.novoAssentado = novoAssentado;
    }

    public Assentado getNovoConjuge() {
        return novoConjuge;
    }

    public void setNovoConjuge(Assentado novoConjuge) {
        this.novoConjuge = novoConjuge;
    }

    public Casal getNovoCasal() {
        return novoCasal;
    }

    public void setNovoCasal(Casal novoCasal) {
        this.novoCasal = novoCasal;
    }

    public EscolaridadeFacade getEscolaridadeFacade() {
        return escolaridadeFacade;
    }

    public List<Escolaridade> getListaEscolaridades() {
        if (listaEscolaridades == null) listaEscolaridades = escolaridadeFacade.EscolaridadeOrderByNome();
        return listaEscolaridades;
    }

    public EstadoCivilFacade getEstadoCivilFacade() {
        return estadoCivilFacade;
    }

    public List<EstadoCivil> getListaEstadoCivil() {
        if (listaEstadoCivil == null) listaEstadoCivil = estadoCivilFacade.EstadoCivilOrderByNome();
        return listaEstadoCivil;
    }

    public List<Municipio> getListaMunNasc() {
        return listaMunNasc;
    }
    
    
    /* retorna os valores da enumeração de estados brasileiros */
    public Estado[] getEstados() {
        return Estado.values();
    }
    
    public Sexo[] getSexos() {
        return Sexo.values();
    }
    
    public Nacionalidade[] getNacionalidades() {
        return Nacionalidade.values();
    }
    
    public void onUfNascChange() {
        if(novoAssentado.getUfNascimento() !=null )
            listaMunNasc = municipioFacade.FindByUf(novoAssentado.getUfNascimento().getCodigoIbge());
        else
            listaMunNasc = (List<Municipio>) new HashMap<String, String>();
    }
    
    public String prepareList() {
        
        recreateModel();
        return "ListFamilia";
    }
    
    public String prepareListFromAssentamento(Assentamento assentamento) {
        this.assentamento = assentamento;
        recreateModel();
        return "/faces/view/assentamento/familia/ListFamilia?faces-redirect=true";
    }
    

    public String prepareView() {
        current = (Familia) getItems().getRowData();
        return "ViewFamilia";
    }

    public String prepareEdit() {
        current = (Familia) getItems().getRowData();
        return "EditFamilia";
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Tipo de assentamento atualizado com sucesso !"));
            return "ListFamilia";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do entrevistador !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new Familia();
        current.setAssentamento(assentamento);
        novoAssentado = new Assentado();
        novoConjuge = new Assentado();
        novoCasal = new Casal();
        return "CreateFamilia";
    }

    public String create() {
        Validadores valida = new Validadores();
        if (!(novoAssentado.getCpf() == null) && (!valida.validaCPF(novoAssentado.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do assentado Inválido !"));
            return "CreateEntrevistador";
        } else if (!(novoAssentado.getPisPasep() == null) && (!valida.validaPIS(novoAssentado.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP do assentado Inválido !"));
            return "CreateEntrevistador";
        }else if ((!(novoConjuge.getCpf()==null))&& (!valida.validaCPF(novoConjuge.getCpf()))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do conjuge Inválido !"));
            return "CreateEntrevistador";
        } else if (!(novoConjuge.getPisPasep() == null) && (!valida.validaPIS(novoConjuge.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP do conjuge Inválido !"));
            return "CreateEntrevistador";
        }else {
            try {
                getFacade().create(current);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Família  foi cadastrada com sucesso !"));
                return prepareCreate();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do entrevistador !"));
                return null;
            }
        }
    }
    
    public String destroy() {
        current = (Familia) getItems().getRowData();
        if (!current.getCasais().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir a família pois há casais associadas à ela. Exclua os casais antes de excluir a família !"));
            current = null;
            return "ListFamilia";
        } else {
            performDestroy();
            recreateModel();
            return "ListFamilia";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir a família !"));
        }
    }
    
    public String performSearch(){
        items = new ListDataModel((List) ejbFacade.findByAssentamento(assentamento, paramAssentado, incluirDesistencia));
        paramAssentado = null;
        
        return "ListFuncionalidade";
    }
    
    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListFamilia";
    }
    
    /*
    Controle do fluxo do wizard
    */
    
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    
    /*
    Fim do controle do wizard
    */

    public Familia getFamilia(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    @FacesConverter(forClass = Familia.class)
    public static class FamiliaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FamiliaController controller = (FamiliaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "familiaController");
            return controller.getFamilia(getKey(value));
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
            if (object instanceof Familia) {
                Familia o = (Familia) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Familia.class.getName());
            }
        }

    }
    
}
