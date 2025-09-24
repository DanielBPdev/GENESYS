package com.asopagos.clienteanibol.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum BeneficiarioMPCEnum {


	SI(1),
	SI_DECRETO_1508(2),
	NO(3),
	PROCESO_VERIFICACIÓN(4);
	
	/** Codigo de estado del beneficiarios frente al mecanismo de proteccion al cesante*/
	private final Integer codigo;
	
	BeneficiarioMPCEnum(Integer codigo){
		this.codigo = codigo;
	}
	
	public Integer getCodigo() { return codigo; }



}
