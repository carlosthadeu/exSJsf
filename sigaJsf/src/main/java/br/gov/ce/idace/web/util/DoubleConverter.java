/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author carlos.santos
 */
@FacesConverter("doubleConverter")
public class DoubleConverter implements Converter {
        @Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {
		if(valorTela == null || valorTela.toString().trim().equals("")){
			return 0.0d;
		} else {
			valorTela = valorTela.replaceAll(Pattern.quote("."), "");
			try{
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(2);
                                valorTela = valorTela.replace(",", ".");
                                double resultado = Double.parseDouble(valorTela);
				return resultado;
			}catch (Exception e) {
				return 0.0d;
			}
		}
	}
        @Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {
		if(valorTela == null || valorTela.toString().trim().equals("")){
			return "0,00";
		} else {
			NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
			nf.setMaximumFractionDigits(2);
			return nf.format(Double.valueOf(valorTela.toString()));
		}
	}
}