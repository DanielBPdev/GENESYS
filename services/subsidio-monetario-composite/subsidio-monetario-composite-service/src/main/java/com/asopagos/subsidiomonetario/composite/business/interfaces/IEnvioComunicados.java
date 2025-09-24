package com.asopagos.subsidiomonetario.composite.business.interfaces;

import javax.ejb.Local;

import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para el envío de los comunicados del proceso de liquidación<br/>
 * <b>Módulo:</b> Asopagos - HU-311-441<br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q</a>
 */

@Local
public interface IEnvioComunicados {

	/**
     * Método que se encarga del envío de comunicados de una liquidación segun el tipo
     * Masiva - Especifica ajustes y reconocimiento
     * 063 Notificación de dispersión de pagos al empleador 
     * 064 Notificación de dispersión de pagos al trabajador 
     * 065 Notificación de dispersión de pagos al administrador del subsidio
     * Especifica Fallecimiento
     * Por Trabajador 
     * por Beneficiario
     */
	public void enviarComunicadosLiquidacion(String numeroSolicitud, ProcesoEnum proceso);
	
}
