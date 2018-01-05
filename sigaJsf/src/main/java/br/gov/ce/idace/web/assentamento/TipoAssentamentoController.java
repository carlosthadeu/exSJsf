/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.assentamento;


import br.gov.ce.idace.entity.assentamento.TipoAssentamento;
import br.gov.ce.idace.session.assentamento.TipoAssentamentoFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
@Named("tipoAssentamentoController")
@SessionScoped
public class TipoAssentamentoController implements Serializable {

    private static final long serialVersionUID = -9013032293506089878L;
    
    @EJB
    private TipoAssentamentoFacade ejbFacade;
    private DataModel items;
    private TipoAssentamento current;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    

    public TipoAssentamentoController() {
    }
    
    public TipoAssentamento getSelected() {
        if (current == null) {
            current = new TipoAssentamento();
        }
        return current;
    }

    private TipoAssentamentoFacade getFacade() {
        return ejbFacade;
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel((List) ejbFacade.findAll());
        }
        return items;
    }
    
     public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    private void recreateModel() {
        items = null;
    }
    
    public String prepareList() {
        recreateModel();
        return "ListTipoAssentamento";
    }

    public String prepareView() {
        current = (TipoAssentamento) getItems().getRowData();
        return "ViewTipoAssentamento";
    }

    public String prepareEdit() {
        current = (TipoAssentamento) getItems().getRowData();
        return "EditTipoAssentamento";
    }

    public String update() {
        try {
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "Tipo de assentamento atualizado com sucesso !"));
            return "ListTipoAssentamento";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na atualização do tipo de assentamento !"));
            return null;
        }
    }

    public String prepareCreate() {
        current = new TipoAssentamento();
        return "CreateTipoAssentamento";
    }

    public String create() {
        try {
            getFacade().create(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "O tipo de assentamento: \"" + current.getTipoAssentamento() + "\" foi criado com sucesso !"));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Erro na criação do tipo de assentamento !"));
            return null;
        }

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

    public String destroy() {
        current = (TipoAssentamento) getItems().getRowData();
        if (!current.getAssentamentos().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Não é possível excluir o tipo de assentamento pois há assentamentos associados à ele. Exclua os assentamentos antes de excluir o tipo de assentamento !"));
            current = null;
            return "ListTipoAssentamento";
        } else {
            performDestroy();
            recreateModel();
            return "ListTipoAssentamento";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Houve um erro ao excluir o tipo de assentamento !"));
        }
    }
    
    public String resetSearch() {
        recreateModel();
        getItems();
        return "ListTipoAssentamento";
    }

    public TipoAssentamento getTipoAssentamento(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    @FacesConverter(forClass = TipoAssentamento.class)
    public static class TipoAssentamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoAssentamentoController controller = (TipoAssentamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoAssentamentoController");
            return controller.getTipoAssentamento(getKey(value));
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
            if (object instanceof TipoAssentamento) {
                TipoAssentamento o = (TipoAssentamento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoAssentamento.class.getName());
            }
        }

    }
    
}
