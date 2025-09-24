/**
 * 
 */
package com.asopagos.enumeraciones;

/**
 * @author squintero
 *
 */
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
	
	public static BeneficiarioMPCEnum obtenerValorEnumeracionPorCodigo(Integer codigo){
		BeneficiarioMPCEnum resultBeneficiarioMPCEnum = null;
    	if(codigo != null)
	        for (BeneficiarioMPCEnum beneficiarioMPCEnum : BeneficiarioMPCEnum.values()) {
	            if (beneficiarioMPCEnum.getCodigo().equals(codigo) ) {
	            	resultBeneficiarioMPCEnum = beneficiarioMPCEnum;
	            	break;
	            }
	        }
        return resultBeneficiarioMPCEnum;
    }
	
	public Integer getCodigo() { return codigo; }
}
