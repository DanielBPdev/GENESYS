package com.asopagos.clienteanibol.enums;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeracion que representa los posibles estados de verificacion
 * de una solicitud de subsidio FOSFEC en integracion con servicios 
 * expuestos por ZENITH
 * 
 * @author jbuitrago
 *
 */
@XmlEnum
public enum VerificacionRequisitosEnum {

	APROBADO(1),
	DENEGADO(2),
	DESISTIDO(3),
	CANCELADO(4),
	PROCESO_VERIFICACIÓN(5);
	
	/** Codigo de identificacion del estado de verficacion de requisitos */
	private final Integer codigo;
	
	VerificacionRequisitosEnum(Integer codigo){
		this.codigo = codigo;
	}
	
	public Integer getCodigo() { return codigo; }

}
