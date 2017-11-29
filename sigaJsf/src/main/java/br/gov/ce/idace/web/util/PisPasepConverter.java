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
@FacesConverter("pisPasepConverter")
public class PisPasepConverter implements Converter {
     public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
       
          String pisPasep = value;
          if (value!= null && !value.equals(""))
               pisPasep = value.replaceAll("\\.", "").replaceAll("\\-", "");
 
          return pisPasep;
     }
 
     public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
         
          String pisPasep= (String) value;
          if (pisPasep != null && pisPasep.length() == 11)
               pisPasep = pisPasep.substring(0, 3) + "." + pisPasep.substring(3, 8) + "." + pisPasep.substring(8, 10) + "-" + pisPasep.substring(10, 11);
 
          return pisPasep;
     }
}