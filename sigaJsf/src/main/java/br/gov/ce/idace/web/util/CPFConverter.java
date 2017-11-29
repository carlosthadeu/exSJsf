/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.web.util;

/**
 *
 * @author carlos.santos
 */

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
* <b>Converter para CPF.</b>
*
* @author Dilnei Cunha
*/
@FacesConverter("cpfConverter")
public class CPFConverter implements Converter {
     @Override
     public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         
          String cpf = value;
          if (value!= null && !value.equals(""))
               cpf = value.replaceAll("\\.", "").replaceAll("\\-", "");
 
          return cpf;
     }
 
     @Override
     public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
         
          String cpf= (String) value;
          if (cpf != null && cpf.length() == 11)
               cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
 
          return cpf;
     }
}