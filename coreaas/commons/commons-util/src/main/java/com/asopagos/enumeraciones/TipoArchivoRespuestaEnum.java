package com.asopagos.enumeraciones;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los tipos de persona
 * definidos en el Decreto 2388 de 2016 <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */

public enum TipoArchivoRespuestaEnum {
	EMPLEADOR("Empleador"), 
	BENEFICIARIO("Beneficiario"), 
	AFILIADO_PRINCIPAL("Afiliado principal"),
	TrasladoMasivosEmpresasCCF("traslado"),
	TrasladoMasivosEmpresasCCFCargo("traslado cargo"),
	Pensionados25Anios("Pensionados 25 anios"),
	CAMBIO_MEDIO_DE_PAGO_MASIVO_TRANSFERENCIA("Cambio de medio de pago masivo a transferencia"),
	CERTIFICADOS_AFILIACION_MASIVOS("Certificados masivos de afiliacion.");

	/**
	 * Descripción del tipo
	 */
	private String descripcion;

	/**
	 * Constructor de la enumeración
	 */
	private TipoArchivoRespuestaEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
}
