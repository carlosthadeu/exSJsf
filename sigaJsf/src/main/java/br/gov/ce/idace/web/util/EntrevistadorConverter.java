/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.util;

import br.gov.ce.idace.entity.assentamento.Entrevistador;
import br.gov.ce.idace.session.assentamento.EntrevistadorFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author carlos.santos
 */
@FacesConverter("entrevistadorConverter")
public class EntrevistadorConverter  implements Converter, Serializable  {
    private static final long serialVersionUID = 7698336449441314774L;

    private EntrevistadorFacade entrevistadorFacade;
    private static final String ENTREVISTADOR_EJB_LOOKUP_PATH = "java:module/EntrevistadorFacade";

    public EntrevistadorFacade getEntrevistadorFacade() {
        return entrevistadorFacade;
    }

    public void setEntrevistadorFacade(EntrevistadorFacade entrevistadorFacade) {
        this.entrevistadorFacade = entrevistadorFacade;
    }

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            entrevistadorFacade = (EntrevistadorFacade) initialContext.lookup(ENTREVISTADOR_EJB_LOOKUP_PATH);
        } catch (NamingException ex) {
            Logger.getLogger(EntrevistadorConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (valorTela == null || valorTela.toString().equals("")) {
            return null;
        } else {

            try {
                Entrevistador resultado = entrevistadorFacade.findByCpf(valorTela);
                return resultado;
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            entrevistadorFacade = (EntrevistadorFacade) initialContext.lookup(ENTREVISTADOR_EJB_LOOKUP_PATH);
        } catch (NamingException ex) {
            Logger.getLogger(EntrevistadorConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (valorTela == null || valorTela.toString().trim().equals("")) {
            return "";
        } else {
            String resultado = entrevistadorFacade.FindNomeByObjeto((Entrevistador) valorTela);
            return resultado;
        }
    }

}

