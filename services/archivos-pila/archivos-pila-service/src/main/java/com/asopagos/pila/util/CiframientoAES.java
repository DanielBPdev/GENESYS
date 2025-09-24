/**
*
*
*13 de ene. de 2017
*/
package com.asopagos.pila.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.asopagos.pila.service.ICiframientoUtil;

/**
 * <b>Descripcion:</b> Clase para obtener el ciframiento y desciframiento de una cadena con AES<br/>
 * <b>Módulo:</b> Asopagos - HU Archivo pila<br/>
 *
 * @author  <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban Castellanos</a>
 * @version 1.0.0
 */

public class CiframientoAES implements ICiframientoUtil{

	/**
	 * Indica que el ciframiento es de 256 bits
	 */
	private static final int LONGITUD_CIFRAMIENTO = 256;

	/**
	 * Llave de ciframiento
	 */
	public static final String LLAVE = "7430eef0c266d1c5";
	
	/**
	 * Tipo de ciframiento
	 */
    public static final String TIPO_CIFRAMIENTO = "AES";

    /**
     * Unica instancia del ciframiento
     */
    private static CiframientoAES instancia = new CiframientoAES();
    
    /**
     * Constructor por defecto
     */
    private CiframientoAES() {
        
    }
    
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
     * Transformar los bytes y los encripta
     * @param hexa Cadena con hexadecimales
     * @return Palabra transformada
     */
    private static byte[] transformByte(char[] hexa) {
        int length = hexa.length / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            //CHECKSTYLE:OFF
            int alto = Character.digit(hexa[i * 2], 16);
            int bajo = Character.digit(hexa[i * 2 + 1], 16);
            int valor = (alto << 4) | bajo;
            if (valor > 127) {
                valor -= 256;
            }
            //CHECKSTYLE:ON
            bytes[i] = (byte) valor;
        }
        return bytes;
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
        
        String res = "";
        try {
            byte[] encriptada ;
            SecretKey llave = new SecretKeySpec(LLAVE.getBytes(), TIPO_CIFRAMIENTO);
            Cipher cipher = Cipher.getInstance(TIPO_CIFRAMIENTO);
            cipher.init(Cipher.ENCRYPT_MODE, llave);

            encriptada = cipher.doFinal(cadena.getBytes());
            res = transformHex(encriptada);
            KeyGenerator kgen = KeyGenerator.getInstance(TIPO_CIFRAMIENTO);
            kgen.init(LONGITUD_CIFRAMIENTO); // 256 bits puede no estar disponible
        } catch (Exception e) {
        }

        return res;
    }

    /**
     * Desencripta una cadena
     * @param encriptadaHex Cadena encriptada
     * @return Cadena desencriptada
     */    
    public String descifrar(String encriptadaHex) {

        if (encriptadaHex == null || encriptadaHex.length() == 0) {
            return "";
        }
        String originalString = "";
        byte[] encriptada = transformByte(encriptadaHex.toCharArray());
        try {

            SecretKey llave = new SecretKeySpec(LLAVE.getBytes(), TIPO_CIFRAMIENTO);
            byte[] raw = llave.getEncoded();

            Cipher cipher = Cipher.getInstance(TIPO_CIFRAMIENTO);

            SecretKeySpec keySpec = new SecretKeySpec(raw, TIPO_CIFRAMIENTO);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] original =
                    cipher.doFinal(encriptada);
            originalString = new String(original);

        } catch (Exception e) {
            
        }
        return originalString;
    }

}
