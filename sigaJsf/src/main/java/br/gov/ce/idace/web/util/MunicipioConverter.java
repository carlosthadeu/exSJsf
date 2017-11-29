/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.util;


import br.gov.ce.idace.session.ibge.MunicipioFacade;
import br.gov.ce.idace.core.entity.ibge.Municipio;
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
@FacesConverter("municipioConverter")
public class MunicipioConverter implements Converter, Serializable {

    private MunicipioFacade municipioFacade;
    private static final String MUNICIPIO_EJB_LOOKUP_PATH = "java:module/MunicipioFacade";

    public MunicipioFacade getMunicipioFacade() {
        return municipioFacade;
    }

    public void setMunicipioFacade(MunicipioFacade municipioFacade) {
        this.municipioFacade = municipioFacade;
    }

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {
        InitialContext initialContext;
        try {
            initialContext = new InitialContext();
            municipioFacade = (MunicipioFacade) initialContext.lookup(MUNICIPIO_EJB_LOOKUP_PATH);
        } catch (NamingException ex) {
            Logger.getLogger(MunicipioConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (valorTela == null || valorTela.toString().equals("")) {
            return null;
        } else {

            try {
                Municipio resultado = municipioFacade.FindByNome(valorTela);
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
            municipioFacade = (MunicipioFacade) initialContext.lookup(MUNICIPIO_EJB_LOOKUP_PATH);
        } catch (NamingException ex) {
            Logger.getLogger(MunicipioConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (valorTela == null || valorTela.toString().trim().equals("")) {
            return "";
        } else {
            String resultado = municipioFacade.FindNomeByObjeto((Municipio) valorTela);
            return resultado;
        }
    }

}
