package com.asopagos.archivos.enums;

public enum ProcesoFoliumEnum {

	/**
	 * Valor para el proceso afiliación empleadores presencial
	 */
	AFILIACION_EMPRESAS_PRESENCIAL("AFL", "AFLAFE"),

	/**
	 * Valor para el proceso afiliación empleadores web
	 */
	AFILIACION_EMPRESAS_WEB("AFL", "AFLAFE"),

	/**
	 * Representa el proceso de afiliación presencial para personas
	 */
	AFILIACION_PERSONAS_PRESENCIAL("AFL", "AFLTRA"),

	/**
	 * Representa el proceso de afiliación de un dependiente vía web y qué es realizado por empleador
	 */
	AFILIACION_DEPENDIENTE_WEB("AFL", "AFLTRA"),

	/**
	 * Representa el proceso de afiliación de independientes o pensionados (ejecutado por el solicitante)
	 */
	AFILIACION_INDEPENDIENTE_WEB("AFL", "AFLTRA"),

	/**
	 * Valor para el proceso de novedades empleadores presencial
	 */
	NOVEDADES_EMPRESAS_PRESENCIAL("AFL", "AFLNVE"),

	/**
	 * Valor para el proceso novedades empleadores web
	 */
	NOVEDADES_EMPRESAS_WEB("AFL", "AFLNVE"),

	/**
	 * Valor para el proceso novedades personas presencial
	 */
	NOVEDADES_PERSONAS_PRESENCIAL("AFL", "AFLNIP"),

	/**
	 * Valor para el proceso novedades dependientes web
	 */
	NOVEDADES_DEPENDIENTE_WEB("AFL", "AFLNTPC"),

	/**
	 * Valor para el proceso novedades personas web
	 */
	NOVEDADES_PERSONAS_WEB("AFL", "AFLNIP"),

	/**
	 * Valor para el proceso de Postulaciones FOVIS Presencial
	 */
	POSTULACION_FOVIS_PRESENCIAL("FOVIS", "PFOVIS"),

	/**
	 * Valor para el proceso de Postulaciones FOVIS Web
	 */
	POSTULACION_FOVIS_WEB("FOVIS", "PFOVIS"),

	/**
	 * Proceso de analisis de novedades de personas que afectan la postulacion FOVIS
	 */
	ANALISIS_NOVEDADES_PERSONAS_FOVIS("FOVIS", "NFOVIS"),

	/**
	 * Proceso de registro de novedades regulares Fovis
	 */
	NOVEDADES_FOVIS_REGULAR("FOVIS", "NFOVIS"),

	/**
	 * Proceso de registro de novedades especiales FOVIS
	 */
	NOVEDADES_FOVIS_ESPECIAL("FOVIS", "NFOVIS"),
	

	/**
	 * Proceso para la verificacion de la postulación Fovis.
	 */
	VERIFICACION_POSTULACION_FOVIS("FOVIS", "PFOVIS"),

	/**
	 * Proceso para la gestion de cruces postulación FOVIS
	 */
	CRUCES_POSTULACION_FOVIS("FOVIS", "PFOVIS"),
	
	/**
	 * Valor para el proceso de recaudo PILA
	 */
	RECAUDO_PILA(null, null),

	/**
	 * Valor para el proceso de solicitud de pago manual de aportes
	 */
	PAGO_APORTES_MANUAL(null, null),

	/**
	 * Valor para el proceso de devolución de aportes
	 */
	DEVOLUCION_APORTES(null, null),

	/**
	 * Valor para el proceso de corrección de aportes
	 */
	CORRECCION_APORTES(null, null),

	/**
	 * Valor para el proceso de cargue y verificacion de actualizacion de informcion
	 */
	NOVEDADES_ARCHIVOS_ACTUALIZACION(null, null),

	/**
	 * Valor para el proceso de Gestión Preventiva de Cartera
	 */
	GESTION_PREVENTIVA_CARTERA(null, null),

	/**
	 * Valor para el proceso de Subsidio Monetario Masivo
	 */
	SUBSIDIO_MONETARIO_MASIVO(null, null),

	/**
	 * Valor para el proceso de Fiscalización cartera
	 */
	FISCALIZACION_CARTERA(null, null),

	/**
	 * Valor para el proceso de Subsidio Monetario Masivo
	 */
	SUBSIDIO_MONETARIO_ESPECIFICO(null, null),

	/**
	 * Valor para el proceso de Asignación FOVIS.
	 */
	ASIGNACION_FOVIS(null, null),

	/**
	 * Valor para el proceso de Legalización y Desembolso FOVIS.
	 */
	LEGALIZACION_DESEMBOLSO_FOVIS(null, null),

	/**
	 * Valor para el proceso de Subsidio Monetario Masivo
	 */
	PAGOS_SUBSIDIO_MONETARIO(null, null),

	/**
	 * Valor para el proceso de gestión de cartera física general
	 */
	GESTION_CARTERA_FISICA_GENERAL(null, null),

	/**
	 * Valor para el proceso de gestión de cartera física detallada
	 */
	GESTION_CARTERA_FISICA_DETALLADA(null, null),

	/**
	 * Valor para el proceso de gestión de cobro electrónico
	 */
	GESTION_COBRO_ELECTRONICO(null, null),

	/**
	 * Valor para el proceso de gestión de cobro manual
	 */
	GESTION_COBRO_MANUAL(null, null),

	/**
	 * Valor para el proceso de convenio de pago
	 */
	CONVENIO_PAGO(null, null),

	/**
	 * Valor para el porceso de solicitud de anulación de subsidio cobrado
	 */
	SOLICITUD_ANULACION_SUBSIDIO_COBRADO(null, null),

	/**
	 * Valor para el proceso de desafiliación de aportantes
	 */
	DESAFILIACION_APORTANTES(null, null),

	/**
	 * Valor para el proceso de liquidación de subsidio por fallecimiento
	 */
	LIQUIDACION_SUBSIDIO_FALLECIMIENTO(null, null),

	/**
	 * Proceso de gestión de cobro 2E
	 */
	GESTION_COBRO_2E(null, null),

	/**
	 * Proceso de cierre recaudo
	 */
	CIERRE_RECAUDO(null, null),

	/**
	 * proceso de gestion preventiva cartera
	 */
	GESTION_PREVENTIVA_CARTERA_ACTUALIZACION(null, null),

	/**
	 * Valor para el proceso de Recaudo Aporte Pila
	 */
	RECAUDO_APORTES_PILA(null, null),

	/**
	 * Valor para el proceso de los Reportes Normativos
	 */
	REPORTES_NORMATIVOS(null, null),

	/**
	 * Valor para el proceso de las vistas 360
	 */
	VISTAS_360(null, null);
	
	
	private String sufijoProceso;
    
    private String sufijoTema;
	
    private ProcesoFoliumEnum(String sufijoProceso, String sufijoTema) {
		this.sufijoProceso = sufijoProceso;
		this.sufijoTema = sufijoTema;
	}

	/**
	 * @return the sufijoProceso
	 */
	public String getSufijoProceso() {
		return sufijoProceso;
	}
	
	/**
	 * @return the sufijoTema
	 */
	public String getSufijoTema() {
		return sufijoTema;
	}
}
