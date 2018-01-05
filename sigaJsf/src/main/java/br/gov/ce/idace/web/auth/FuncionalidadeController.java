/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.Funcionalidade;
import br.gov.ce.idace.entity.auth.Modulo;
import br.gov.ce.idace.entity.auth.Sistema;
import br.gov.ce.idace.session.auth.FuncionalidadeFacade;
import br.gov.ce.idace.session.auth.ModuloFacade;
import br.gov.ce.idace.session.auth.SistemaFacade;
import java.io.Serializable;
import java.util.HashMap;
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
 * @author Carlos.Santos
 */
@Named("funcionalidadeController")
@SessionScoped
public class FuncionalidadeController implements Serializable {

    private static final long serialVersionUID = -1435135600592343015L;
    
    @EJB
    private FuncionalidadeFacade ejbFacade;
    @EJB
    private SistemaFacade ejbSistemaFacade;
    @EJB
    private ModuloFacade ejbModuloFacade;
    
    private DataModel items = null;
    private Funcionalidade current;
    
    /* utilizadas no p:selectOneMenu do detail */
    private List<Sistema> listaSistemas;
    private List<Modulo> listaModulos;
    private Sistema paramSistema;
    private Modulo paramModulo;
    private String paramFuncionalidade;
    /* utilizadas no p:selectOneMenu */
    
    public FuncionalidadeController() {
    }

    public Modulo getParamModulo() {
        return paramModulo;
    }

    public void setParamModulo(Modulo paramModulo) {
        this.paramModulo = paramModulo;
    }

    

    public Sistema getParamSistema() {
        return paramSistema;
    }
    
    public void setParamSistema(Sistema paramSistema) {
        this.paramSistema = paramSistema;
    }

    public String getParamFuncionalidade() {
        return paramFuncionalidade;
    }

    public void setParamFuncionalidade(String paramFuncionalidade) {
        this.paramFuncionalidade = paramFuncionalidade;
    }
    
    
    
    

    public SistemaFacade getEjbSistemaFacade() {
        return ejbSistemaFacade;
    }   

    public List<Sistema> getListaSistemas() {
        listaSistemas = ejbSistemaFacade.findAll();
        return listaSistemas;
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }
     
    public Funcionalidade getSelected() {
        if (current == null) {
            current = new Funcionalidade();
        }
        return current;
    }

    private FuncionalidadeFacade getFacade() {
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
    
    public Funcionalidade getFuncionalidade(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    private void recreateModel() {
        items = null;
    }

    public String prepareList() {
        recreateModel();
        return "ListFuncionalidade";
    }

    public String prepareView() {
        current = (Funcionalidade) getItems().getRowData();
        return "ViewFuncionalidade";
    }

    public String prepareCreate() {
        current = new Funcionalidade();

        return "CreateFuncionalidade";
    }

    public String prepareEdit() {
        current = (Funcionalidade) getItems().getRowData();
        paramSistema = current.getModulo().getSistema();
        listaModulos = ejbModuloFacade.listaModulosSistema(paramSistema);
        
        return "EditFuncionalidade";
    }
    

    public String destroy() {
        current = (Funcionalidade) getItems().getRowData();
        performDestroy();
        recreateModel();
        
        return "ListFuncionalidade";
    }

    public String create() {
        try {
            getFacade().create(current);
            paramSistema = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A funcionalidade foi criada com sucesso !"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao criar a funcionalidade !"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "A funcionalidade foi atualizada com sucesso !"));
            return "ListFuncionalidade";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao atualizar a funcionalidade !"));
            return null;
        }
    }

    private void performDestroy() {
        try {
            String msgFuncionalidade = "A funcionalidade "+ current.getFuncionalidade() + " do módulo " + current.getModulo().getModulo() + " do sistema " + current.getModulo().getSistema().getSistema();
            getFacade().removeFuncionalidade(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", msgFuncionalidade + " foi excluída com sucesso !"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir a funcionalidade !"));
        }
    }
    
    public String performSearch(){
        items = new ListDataModel((List) ejbFacade.findByModuloNome(paramSistema, paramModulo,paramFuncionalidade));
        paramSistema = null;
        paramModulo = null;
        paramFuncionalidade = null;
        
        return "ListFuncionalidade";
    }
    
    
    public void onSistemaChange() {
        if(paramSistema !=null && !paramSistema.equals(""))
            listaModulos = ejbModuloFacade.listaModulosSistema(paramSistema);
        else
            listaModulos = (List<Modulo>) new HashMap<String, String>();
    }
    
    
    
    @FacesConverter(forClass = Funcionalidade.class)
    public static class FuncionalidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FuncionalidadeController controller = (FuncionalidadeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "funcionalidadeController");
            return controller.getFuncionalidade(getKey(value));
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
            if (object instanceof Funcionalidade) {
                Funcionalidade o = (Funcionalidade) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Funcionalidade.class.getName());
            }
        }
    }
    
}
