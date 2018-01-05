/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.auth;

import br.gov.ce.idace.entity.auth.Setores;
import br.gov.ce.idace.session.SetoresFacade;
import br.gov.ce.idace.web.util.JsfUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author marcos.silva
 */
@Named("setoresController")
@SessionScoped
public class SetoresController implements Serializable {

    private static final long serialVersionUID = 329447563484909900L;

    private Setores current;
    private DataModel items = null;
    @EJB
    private SetoresFacade ejbFacade;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    private Map<String, String> criteriaparams;
    private DataModel setores = null;
    private String paramNome;
    private String paramId;
    
    
   public SetoresController() {
    }

    public Map<String, String> getCriteriaparams() {
        return criteriaparams;
    }

    public void setCriteriaparams(Map<String, String> criteriaparams) {
        this.criteriaparams = criteriaparams;
    }

    public String getParamNome() {
        return paramNome;
    }

    public void setParamNome(String paramNome) {
        this.paramNome = paramNome;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public Setores getSelected() {
        if (current == null) {
            current = new Setores();
        }
        return current;
    }

    private SetoresFacade getFacade() {
        return ejbFacade;
    }

    public DataModel getSetores() {
        if (setores == null) {
            setores = new ListDataModel(ejbFacade.findAll());
        }
        return setores;
    }

    public String prepareList() {
        recreateModel();
        return "ListSetores";
    }

    public String prepareView() {
        current = (Setores) getItems().getRowData();
        return "ViewSetores";
    }

    public String prepareCreate() {
        current = new Setores();
        return "CreateSetores";
    }

    public Setores findByNome(String nome) {
        return (Setores) getFacade().findByField("nome", nome);
    }

    public String create() {
        try {
            getFacade().create(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "DRegistro criado com sucesso!"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Erro ao criar registro!"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Setores) getItems().getRowData();
        return "EditSetores";
    }

    public String destroyList() {
        Long key;
        Iterator it = checked.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Boolean> item = (Map.Entry<Long, Boolean>) it.next();
            if (item.getValue()) {
                key = item.getKey();
                current = ejbFacade.find(key);
                performDestroy();
                it.remove();
            }
        }
        recreateModel();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Registro excluído com sucesso!"));
        return "ListSetores";
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Dados alterados com sucesso!"));
            return "ListSetores";
        } catch (Exception e) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Erro ao alterar dados!"));
            return null;
        }
    }

    public String destroy() {
        current = (Setores) getItems().getRowData();
        performDestroy();
        recreateModel();
        return "ListSetores";
    }

    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListSetores";
    }
    
    public String performSearch() {
        recreateModel();
        criteriaparams = new HashMap<String, String>();
        if (paramId == null & paramNome == null) {
            items = new ListDataModel(ejbFacade.findAll());
        } 
        else {
            if (paramId != null){
                criteriaparams.put("id", paramId); 
            }
            if (paramNome != null){
                criteriaparams.put("nome", paramNome); 
            }
        items = new ListDataModel(ejbFacade.findLike(criteriaparams));
        }
        
        return "ListSetores";
    }

    public String destroyAndView() {
        performDestroy();

        return "ViewSetores";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Erro de persistência!"));
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(ejbFacade.findAll());
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Setores getSetores(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Setores.class)
    public static class SetoresControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SetoresController controller = (SetoresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "setoresController");
            return controller.getSetores(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
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
            if (object instanceof Setores) {
                Setores o = (Setores) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Setores.class.getName());
            }
        }

    }

}
