package com.asopagos.afiliaciones.empleadores.composite.enu;

import javax.xml.bind.annotation.XmlEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;

/**
 * <b>Descripción:</b> Enumeración que representa los posibles resultados de la
 * radicación de una solicitud
 * <b>Historia de Usuario:</b> HU-092 
 * 
 * @author Andrey G. López <alopez@heinsohn.com.co>
 */
@XmlEnum
public enum GestionarProductoNoConformeSubsanableEnum {
    
	/**
	 * Indica que la solicitud ha sido subsanada
	 */
	SUBSANADA (1),
	
	/**
	 * Indica que la solicitud no ha sido subsanda
	 */
	NO_SUBSANADA(2);
	
	/**
	 * Valor del resultado
	 */
	private Integer valor; 
	
	/**
	 * Constructor de la clase
	 * @param valor Valor de la enumeración
	 */
	private GestionarProductoNoConformeSubsanableEnum(Integer valor) {
		this.valor = valor;
	}
	
	/**
	 * Retorna el valor del resultado
	 * @return valor del resultado
	 */
	public Integer getValor() {
		return valor;
	}
	
	/**
	 * Cambia el valor del resultado
	 * @param valor nuevo valor
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	/**
	 * Retorna el estado de negocio en el que debe quedar la solicitud
	 * @return Estado de negocio en el que debe quedar la solictud después
	 * de subsanar
	 */
	public EstadoSolicitudAfiliacionEmpleadorEnum getEstadoSolicitud(){
		return EstadoSolicitudAfiliacionEmpleadorEnum.NO_CONFORME_GESTIONADA;
		
	}
	
	
       
}

	