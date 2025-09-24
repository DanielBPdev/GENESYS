/**
*
*
*13 de ene. de 2017
*/
package com.asopagos.pila.util;

import java.security.MessageDigest;
import com.asopagos.pila.service.ICiframientoUtil;

/**
 * <b>Descripcion:</b> Clase para obtener el ciframiento de una cadena con hash sha<br/>
 * <b>Módulo:</b> Asopagos - HU Archivo pila<br/>
 *
 * @author  <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban Castellanos</a>
 * @version 1.0.0
 */

public class CiframientoSHA implements ICiframientoUtil{

	/**
	 * Tipo de ciframiento
	 */
    public static final String TIPO_CIFRAMIENTO = "SHA-512";
    
    /**
     * Unica instancia del ciframiento
     */
    private static CiframientoSHA instancia = new CiframientoSHA();
    
    /**
     * Unica instancia de la clase
     * @return Retorna el singleton
     */
    public static ICiframientoUtil getInstancia() {
    	return instancia;
    }

    /**
     * Transforma en hexadecimal
     * @param bytes Bytes a transformar
     * @return Bytes en hexadecimal
     */
    private static String transformHex(byte bytes[]) {
        StringBuilder strbuf = new StringBuilder(bytes.length * 2);
        int i;

        //CHECKSTYLE:OFF
        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        //CHECKSTYLE:ON

        return strbuf.toString();
    }

    /**
     * Realiza la encripcion de la cadena
     * @param cadena Cadena a encriptar
     * @return cadena encriptada
     */
    public String cifrar(String cadena) {

    	if (cadena == null || cadena.length() == 0) {
            return "";
        }
        
        MessageDigest md;
		try {
			md = MessageDigest.getInstance(TIPO_CIFRAMIENTO);
			md.update(cadena.getBytes("UTF-8")); // Change this to "UTF-16" if needed
	        byte[] digest = md.digest();

	        return transformHex(digest);
		} catch (Exception e) {
		
		}
		return "";
    }

    /**
     * Desencripta una cadena
     * @param encriptadaHex Cadena encriptada
     * @return Cadena desencriptada
     */    
    public String descifrar(String encriptadaHex) {
    	// SHA no soporta descifrar
        return "";
    }

}
