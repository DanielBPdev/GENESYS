package com.asopagos.pila.util;

import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.service.ArchivosPILAService;

/**
 * Clase utilitaria para determinar acciones comunes en las validaciones de
 * acciones de corrección
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class AccionCorreccionUtil {

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ArchivosPILAService.class);

	public static Boolean archivoEstadoProcesado(EstadoProcesoArchivoEnum estadoArchivoPila) {
		logger.debug("Inicia archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
		if (!EstadoProcesoArchivoEnum.ANULADO.equals(estadoArchivoPila) && estadoArchivoPila
				.ordinal() > EstadoProcesoArchivoEnum.PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES.ordinal()) {
			logger.debug("Finaliza archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
			return true;
		}
		logger.debug("Finaliza archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
		return false;
	}
	
	/**
	 * Valida el estado recibido para determinar si es posbile que pase a estado
	 * anulado
	 * 
	 * @param estadoArchivoPila
	 */
	public static Boolean archivoEstadoCandidatoParaAnular(EstadoProcesoArchivoEnum estadoArchivoPila) {
		logger.debug("Inicia archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
		if (estadoArchivoPila.ordinal() < EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES.ordinal()) {
			logger.debug("Finaliza archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
			return true;
		}
		logger.debug("Finaliza archivoEstadoArchivoCandidatoParaAnular(EstadoArchivoPilaEnum)");
		return false;
	}

}
