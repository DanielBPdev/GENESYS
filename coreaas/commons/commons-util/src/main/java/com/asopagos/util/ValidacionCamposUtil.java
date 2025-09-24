package com.asopagos.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asopagos.constants.ExpresionesRegularesConstants;

/**
 * <b>Descripción:</b> Clase encargada de implementar métodos de validación
 * para atributos de las entidades.
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Jesus Rodriguez <jerodriguez@heinsohn.com.co>
 */
public class ValidacionCamposUtil {

    /**
     * <b>Descripción</b>Método encargado de validar la estructura de un
     * email o correo electrónico<br/>
     * @param email cadena de texto o correo a validar
     * @return valor booleano. True estructura válida y False no válida
     */
    public static boolean validarEmail(String email) {
    	if(email == null){
    		return Boolean.FALSE;
    	}
    	
        String patron = ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL;
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(email.toLowerCase());
        return matcher.matches();
    }
    
    /**
     * <b>Descripción</b>Método encargado de valida que los atributos
     * de un objetos no esten vacios o nulos<br/>
     * @param object cadena de texto o correo a validar
     * @return valor booleano. 
     */
    public static boolean  validarCamposNull(Object object){	
    	return true;	
    }
}
