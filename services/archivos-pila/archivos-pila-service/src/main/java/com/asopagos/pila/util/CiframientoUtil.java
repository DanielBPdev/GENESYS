package com.asopagos.pila.util;

import com.asopagos.enumeraciones.pila.EnumTipoCiframiento;
import com.asopagos.pila.service.ICiframientoUtil;

/**
 * <b>Descripcion:</b> Clase utilitaria para hacer el ciframiento y descriframietno de acuerdo al tipo de ciframiento<br/>
 * <b>Módulo:</b> Asopagos - HU Archivo pila<br/>
 *
 * @author  <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban Castellanos</a>
 * @version 1.0.0
 */

public class CiframientoUtil {


	/**
	 * Realiza el ciframiento de la cadena indicada
	 * @param cadenaACifrar Cadena a cifrar
	 * @param tipoCiframiento Tipo de ciframiento a utilizar
	 * @return Cadena cifrada de acuerdo con el tipo de ciframiento
	 */
	public static String cifrar( String cadenaACifrar, EnumTipoCiframiento tipoCiframiento ) {
		ICiframientoUtil estrategiaCiframiento  = construirCiframiento(tipoCiframiento);
		return estrategiaCiframiento.cifrar(cadenaACifrar);
	}
	
	/**
	 * Descifra la cadena enviada
	 * @param cadenaADescifrar Cadena a descifrar
	 * @param tipoCiframiento Tipo de ciframiento utilizado
	 * @return Cadena descifrada
	 */
	public static String descifrar( String cadenaADescifrar, EnumTipoCiframiento tipoCiframiento ) {
		ICiframientoUtil estrategiaCiframiento  = construirCiframiento(tipoCiframiento);
		return estrategiaCiframiento.descifrar(cadenaADescifrar);
	}

	/**
	 * Construye el tipo de ciframiento
	 * @param tipoCiframiento Tipo de ciframiento a construir
	 * @return Estrategia de ciframiento
	 */
	private static ICiframientoUtil construirCiframiento(EnumTipoCiframiento tipoCiframiento) {
		ICiframientoUtil estrategiaCiframiento = null;
		switch( tipoCiframiento) {
			case AES:
				// Cifra con AES
				estrategiaCiframiento = CiframientoAES.getInstancia();
				break;
			case SHA512:
				// Cifra con AES
				estrategiaCiframiento = CiframientoSHA.getInstancia();
				break;
			default:
				
				break;			
		}
		return estrategiaCiframiento;
	}
}

