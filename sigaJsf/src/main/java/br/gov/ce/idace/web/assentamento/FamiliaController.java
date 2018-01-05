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
import br.gov.ce.idace.entity.assentamento.Entrevista;
import br.gov.ce.idace.entity.assentamento.Entrevistador;
import br.gov.ce.idace.entity.assentamento.FamiliarAssentado;
import br.gov.ce.idace.entity.assentamento.MotivoAlteracaoCasal;
import br.gov.ce.idace.entity.assentamento.Nacionalidade;
import br.gov.ce.idace.entity.assentamento.OpcImoRurCompInsSusPropFam;
import br.gov.ce.idace.entity.assentamento.SituacaoConjugal;
import br.gov.ce.idace.entity.assentamento.TrabRurAssPosParcArredForSemTer;
import br.gov.ce.idace.entity.assentamento.UltimaComposicaoFamiliar;
import br.gov.ce.idace.session.assentamento.AssentadoFacade;
import br.gov.ce.idace.session.assentamento.CasalFacade;
import br.gov.ce.idace.session.assentamento.EntrevistaFacade;
import br.gov.ce.idace.session.assentamento.EntrevistadorFacade;
import br.gov.ce.idace.session.assentamento.FamiliaFacade;
import br.gov.ce.idace.session.assentamento.FamiliarAssentadoFacade;
import br.gov.ce.idace.session.assentamento.UltimaComposicaoFamiliarFacade;
import br.gov.ce.idace.session.ibge.MunicipioFacade;
import br.gov.ce.idace.session.pessoa.EscolaridadeFacade;
import br.gov.ce.idace.session.pessoa.EstadoCivilFacade;
import br.gov.ce.idace.web.util.Validadores;
import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author carlos.santos
 */
@Named("familiaController")
@SessionScoped
public class FamiliaController implements Serializable {

    private static final long serialVersionUID = -6127952880357807782L;

    @EJB
    private FamiliaFacade ejbFacade;

    @EJB
    private EscolaridadeFacade escolaridadeFacade;
    @EJB
    private EstadoCivilFacade estadoCivilFacade;
    @EJB
    private MunicipioFacade municipioFacade;

    @EJB
    private EntrevistadorFacade entrevistadorFacade;

    @EJB
    private AssentadoFacade assentadoFacade;

    @EJB
    private CasalFacade casalFacade;

    @EJB
    private EntrevistaFacade entrevistaFacade;
    
    @EJB
    private UltimaComposicaoFamiliarFacade ultimaComposicaoFamiliarFacade;
    
    @EJB
    private FamiliarAssentadoFacade familiarAssentadoFacade;

    private Familia current;
    private Assentamento assentamento;
    private DataModel items;
    private boolean incluirDesistencia = false;
    private Map<String, String> criteriaParams;
    private String paramAssentado;
    private List<Escolaridade> listaEscolaridades;
    private List<EstadoCivil> listaEstadoCivil;
    private List<Municipio> listaMunNasc;
    private List<Municipio> listaMunNascConj;
    private List<Entrevistador> listaEntrevistador;
    private List<Municipio> listaMunicipioCeara;
    private Assentado novoAssentado;
    private Assentado novoConjuge;
    private Assentado assentadoSelecionado;
    private Casal novoCasal;
    private Entrevista novaEntrevista;

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
        listaEscolaridades = escolaridadeFacade.EscolaridadeOrderByNome();
        return listaEscolaridades;
    }

    public EstadoCivilFacade getEstadoCivilFacade() {
        return estadoCivilFacade;
    }

    public List<EstadoCivil> getListaEstadoCivil() {
        listaEstadoCivil = estadoCivilFacade.EstadoCivilOrderByNome();
        return listaEstadoCivil;
    }

    public List<Entrevistador> getListaEntrevistador() {
        listaEntrevistador = entrevistadorFacade.findEntrevistadorOrderByNome();
        return listaEntrevistador;
    }

    public List<Municipio> getListaMunNasc() {
        return listaMunNasc;
    }

    public List<Municipio> getListaMunNascConj() {
        return listaMunNascConj;
    }

    public Entrevista getNovaEntrevista() {
        return novaEntrevista;
    }

    public void setNovaEntrevista(Entrevista novaEntrevista) {
        this.novaEntrevista = novaEntrevista;
    }

    public List<Municipio> getListaMunicipioCeara() {
        if (listaMunicipioCeara == null) {
            listaMunicipioCeara = municipioFacade.MunicipiosCeara();
        }
        return listaMunicipioCeara;
    }

    public FamiliaFacade getEjbFacade() {
        return ejbFacade;
    }

    public MunicipioFacade getMunicipioFacade() {
        return municipioFacade;
    }

    public EntrevistadorFacade getEntrevistadorFacade() {
        return entrevistadorFacade;
    }

    public AssentadoFacade getAssentadoFacade() {
        return assentadoFacade;
    }

    public CasalFacade getCasalFacade() {
        return casalFacade;
    }

    public EntrevistaFacade getEntrevistaFacade() {
        return entrevistaFacade;
    }

    public UltimaComposicaoFamiliarFacade getUltimaComposicaoFamiliarFacade() {
        return ultimaComposicaoFamiliarFacade;
    }
    
    public Assentado getAssentadoSelecionado() {
        return assentadoSelecionado;
    }

    public void setAssentadoSelecionado(Assentado assentadoSelecionado) {
        this.assentadoSelecionado = assentadoSelecionado;
    }

    public String editarAssentado(Assentado assentadoSelecionado) {
        this.assentadoSelecionado = assentadoSelecionado;
        listaMunNasc = municipioFacade.FindByUf(this.assentadoSelecionado.getUfNascimento().getCodigoIbge());
        return "EditarAssentado";
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

    public OpcImoRurCompInsSusPropFam[] getOpcImoRurCompInsSusPropFam() {
        return OpcImoRurCompInsSusPropFam.values();
    }

    public TrabRurAssPosParcArredForSemTer[] getTrabRurAssPosParcArredForSemTer() {
        return TrabRurAssPosParcArredForSemTer.values();
    }

    public SituacaoConjugal[] getSituacaoConjugal() {
        return SituacaoConjugal.values();
    }

    public void onUfNascChange() {
        if (novoAssentado.getUfNascimento() != null) {
            listaMunNasc = municipioFacade.FindByUf(novoAssentado.getUfNascimento().getCodigoIbge());
        } else {
            listaMunNasc = (List<Municipio>) new HashMap();
        }
    }

    public void onUfNascConjChange() {
        if (novoConjuge.getUfNascimento() != null) {
            listaMunNascConj = municipioFacade.FindByUf(novoConjuge.getUfNascimento().getCodigoIbge());
        } else {
            listaMunNascConj = (List<Municipio>) new HashMap();
        }
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Família atualizado com sucesso !"));
            return "ListFamilia";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização da família !"));
            return null;
        }
    }

    public String updateAssentado() {
        Validadores valida = new Validadores();
        if (!(novoAssentado.getPisPasep() == null) && (!valida.validaPIS(novoAssentado.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP do assentado Inválido !"));
            return "CreateEntrevistador";
        } else {
            try {
                assentadoFacade.edit(assentadoSelecionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Assentado atualizado com sucesso !"));
                return "ListFamilia";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do assentado !"));
                return null;
            }
        }
    }

    public String prepareCreate() {
        current = new Familia();
        current.setAssentamento(assentamento);
        novoCasal = new Casal();
        novoAssentado = new Assentado();
        novoAssentado.setEstuda(Boolean.FALSE);
        novoAssentado.setExerceFuncaoPublica(Boolean.FALSE);
        novoAssentado.setPartEstabComInd(Boolean.FALSE);
        novoAssentado.setAntecedentesCriminais(Boolean.FALSE);
        novoAssentado.setAposentadoriaInvalidez(Boolean.FALSE);
        novoAssentado.setExBenProgNacRefAgra(Boolean.FALSE);
        novoConjuge = new Assentado();
        novoConjuge.setEstuda(Boolean.FALSE);
        novoConjuge.setExerceFuncaoPublica(Boolean.FALSE);
        novoConjuge.setPartEstabComInd(Boolean.FALSE);
        novoConjuge.setAntecedentesCriminais(Boolean.FALSE);
        novoConjuge.setAposentadoriaInvalidez(Boolean.FALSE);
        novoConjuge.setExBenProgNacRefAgra(Boolean.FALSE);
        novaEntrevista = new Entrevista();
        novaEntrevista.setFazParteAcampTrabRurTerIndComSoc(Boolean.FALSE);
        novaEntrevista.setProprietarioObtencao(Boolean.FALSE);
        novaEntrevista.setOpcImoRurCompInsSusPropFam(OpcImoRurCompInsSusPropFam.NAO);
        novaEntrevista.setTrabRurAssPosParcArredForSemTer(TrabRurAssPosParcArredForSemTer.NAO);

        novaEntrevista.setMoraMunImoObt(Boolean.TRUE);

        return "CreateFamilia";
    }
    
    public String onCPFChange(){
        FamiliarAssentado testaFamiliarPrincipal = familiarAssentadoFacade.findByCpf(novoAssentado.getCpf());
        FamiliarAssentado testaFamiliarConjuge = familiarAssentadoFacade.findByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaPrincipal = ultimaComposicaoFamiliarFacade.findPrincipalByCpf(novoAssentado.getCpf());
        UltimaComposicaoFamiliar testaPrincipalConjuge = ultimaComposicaoFamiliarFacade.findPrincipalByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaConjuge = ultimaComposicaoFamiliarFacade.findConjugeByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaConjugePrincipal = ultimaComposicaoFamiliarFacade.findConjugeByCpf(novoAssentado.getCpf());
        /**
         * Testa se o cpf dado como principal já está cadastrado como familiar de assentado ativo em algum outra família em algum assentamento
        */
        if (!(testaFamiliarPrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como familiar ativo de assentado na família " + testaFamiliarPrincipal.getFamilia().getCodigo() + " no assentamento: "+
                    testaFamiliarPrincipal.getFamilia().getAssentamento().getCodigo() + " - "+ testaFamiliarPrincipal.getFamilia().getAssentamento().getAssentamento()+" !"));
        }
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como familiar de assentado ativo em algum outra família em algum assentamento
        */
        if (!(testaFamiliarConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como familiar ativo de assentado na família " + testaFamiliarConjuge.getFamilia().getCodigo() + " no assentamento: "+
                    testaFamiliarConjuge.getFamilia().getAssentamento().getCodigo() + " - "+ testaFamiliarConjuge.getFamilia().getAssentamento().getAssentamento()+" !"));
        }
        /**
         * Testa se o cpf dado como principal já está cadastrado como assentado principal em algum outra família em algum assentamento
        */
        if (!(testaPrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como assentado na família: " + testaPrincipal.getCodigoFamilia() + " no assentamento: "+
                    testaPrincipal.getCodigoAssentamento() + " - "+ testaPrincipal.getNomeAssentamento()+" !"));
        }
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como assentado principal em alguma outra família em algum assentamento
        */
        if (!(testaPrincipalConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como assentado principal na família: " + testaPrincipalConjuge.getCodigoFamilia() + " no assentamento: "+
                    testaPrincipalConjuge.getCodigoAssentamento() + " - "+ testaPrincipalConjuge.getNomeAssentamento()+" !"));
        }
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como conjuge em alguma outra família em algum assentamento
        */
        if (!(testaConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como conjuge de assentado na família: " + testaConjuge.getCodigoFamilia() + " no assentamento: "+
                    testaConjuge.getCodigoAssentamento() + " - "+ testaConjuge.getNomeAssentamento()+" !"));
        }
        /**
         * Testa se o cpf dado como principal já está cadastrado como conjuge em alguma outra família em algum assentamento
        */
        if (!(testaConjugePrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como conjuge de assentado na família: " + testaConjugePrincipal.getCodigoFamilia() + " no assentamento: "+
                    testaConjugePrincipal.getCodigoAssentamento() + " - "+ testaConjugePrincipal.getNomeAssentamento()+" !"));
        }
        Validadores valida = new Validadores();
        if (!(novoAssentado.getCpf() == null) && (!valida.validaCPF(novoAssentado.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do assentado Inválido !"));
        }
        if ((!(novoConjuge.getCpf() == null)) && (!valida.validaCPF(novoConjuge.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do conjuge Inválido !"));
        }
        return "CreateEntrevistador";
    } 

    public String create() {
        FamiliarAssentado testaFamiliarPrincipal = familiarAssentadoFacade.findByCpf(novoAssentado.getCpf());
        FamiliarAssentado testaFamiliarConjuge = familiarAssentadoFacade.findByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaPrincipal = ultimaComposicaoFamiliarFacade.findPrincipalByCpf(novoAssentado.getCpf());
        UltimaComposicaoFamiliar testaConjuge = ultimaComposicaoFamiliarFacade.findConjugeByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaPrincipalConjuge = ultimaComposicaoFamiliarFacade.findPrincipalByCpf(novoConjuge.getCpf());
        UltimaComposicaoFamiliar testaConjugePrincipal = ultimaComposicaoFamiliarFacade.findConjugeByCpf(novoAssentado.getCpf());
        Validadores valida = new Validadores();
        /**
         * Testa se o cpf dado como principal já está cadastrado como familiar de assentado ativo em algum outra família em algum assentamento
        */
        if (!(testaFamiliarPrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como familiar ativo de assentado na família " + testaFamiliarPrincipal.getFamilia().getCodigo() + " no assentamento: "+
                    testaFamiliarPrincipal.getFamilia().getAssentamento().getCodigo() + " - "+ testaFamiliarPrincipal.getFamilia().getAssentamento().getAssentamento()+" !"));
            return "CreateEntrevistador";
        } 
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como familiar de assentado ativo em algum outra família em algum assentamento
        */
        else if (!(testaFamiliarConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como familiar ativo de assentado na família " + testaFamiliarConjuge.getFamilia().getCodigo() + " no assentamento: "+
                    testaFamiliarConjuge.getFamilia().getAssentamento().getCodigo() + " - "+ testaFamiliarConjuge.getFamilia().getAssentamento().getAssentamento()+" !"));
            return "CreateEntrevistador";
        } 
        /**
         * Testa se o cpf dado como principal já está cadastrado como assentado principal em algum outra família em algum assentamento
        */
        else if (!(testaPrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como assentado principal na família: " + testaPrincipal.getCodigoFamilia() + " no assentamento: "+
                    testaPrincipal.getCodigoAssentamento() + " - "+ testaPrincipal.getNomeAssentamento()+" !"));
            return "CreateEntrevistador";
        } 
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como assentado principal em alguma outra família em algum assentamento
        */
        else if (!(testaPrincipalConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como assentado principal na família: " + testaPrincipalConjuge.getCodigoFamilia() + " no assentamento: "+
                    testaPrincipalConjuge.getCodigoAssentamento() + " - "+ testaPrincipalConjuge.getNomeAssentamento()+" !"));
            return "CreateEntrevistador";
        }
        /**
         * Testa se o cpf dado como conjuge já está cadastrado como conjuge em alguma outra família em algum assentamento
        */
        else if (!(testaConjuge == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do conjuge já está cadastrado como conjuge de assentado na família: " + testaConjuge.getCodigoFamilia() + " no assentamento: "+
                    testaConjuge.getCodigoAssentamento() + " - "+ testaPrincipal.getNomeAssentamento()+" !"));
            return "CreateEntrevistador";
        }
        /**
         * Testa se o cpf dado como principal já está cadastrado como conjuge em alguma outra família em algum assentamento
        */
        else if (!(testaConjugePrincipal == null)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "O cpf do assentado já está cadastrado como conjuge de assentado na família: " + testaConjugePrincipal.getCodigoFamilia() + " no assentamento: "+
                    testaConjugePrincipal.getCodigoAssentamento() + " - "+ testaConjugePrincipal.getNomeAssentamento()+" !"));
            return "CreateEntrevistador";
        }
        
        
        else if (!(novoAssentado.getCpf() == null) && (!valida.validaCPF(novoAssentado.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do assentado Inválido !"));
            return "CreateEntrevistador";
        } else if (!(novoAssentado.getPisPasep() == null) && (!valida.validaPIS(novoAssentado.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP do assentado Inválido !"));
            return "CreateEntrevistador";
        } else if ((!(novoConjuge.getCpf() == null)) && (!valida.validaCPF(novoConjuge.getCpf()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "CPF do conjuge Inválido !"));
            return "CreateEntrevistador";
        } else if (!(novoConjuge.getPisPasep() == null) && (!valida.validaPIS(novoConjuge.getPisPasep()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "PIS/PASEP do conjuge Inválido !"));
            return "CreateEntrevistador";
        } else {
            try {
                getFacade().create(current);
                assentadoFacade.create(novoAssentado);
                assentadoFacade.create(novoConjuge);
                novoCasal.setFamilia(current);
                novoCasal.setPrincipal(novoAssentado);
                novoCasal.setConjuge(novoConjuge);
                java.util.Date d = new Date();
                novoCasal.setDataConformacao(d);
                novoCasal.setMotivoAlteracaoCasal(MotivoAlteracaoCasal.INCLUSAO);
                casalFacade.create(novoCasal);
                novaEntrevista.setFamilia(current);
                entrevistaFacade.create(novaEntrevista);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Família  foi cadastrada com sucesso !"));
                return "ListFamilia";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação da família !"));
                return null;
            }
        }
    }

    public String destroy() {
        current = (Familia) getItems().getRowData();
        if (!(current.getUltimaConformacaoCasal() == null)) {
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

    public String performSearch() {
        items = new ListDataModel((List) ejbFacade.findByAssentamento(assentamento, paramAssentado, incluirDesistencia));
        paramAssentado = null;

        return "ListFamilia";
    }

    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListFamilia";
    }

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
