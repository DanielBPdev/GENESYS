/**
*
*
*13 de ene. de 2017
*/
package com.asopagos.pila.service;

/**
 * <b>Descripcion:</b> Esta interface implementa los metodos para el ciframiento y desciframiento de cadenas
 *    dependiente del tipo ciframiento<br/>
 * <b>Módulo:</b> Asopagos - HU Archivos pila<br/>
 *
 * @author  <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban Castellanos</a>
 * @version 1.0.0
 */

public interface ICiframientoUtil {
	public String cifrar(String cadena);
	public String descifrar(String cadenaCifrada);
}
