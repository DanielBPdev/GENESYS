package com.asopagos.pila.validadores.bloque0.ejb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.CombinacionesArchivosPilaDTO;
import com.asopagos.pila.dto.ElementoCombinatoriaArchivosDTO;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.service.IArchivosPILAEjecucion;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOIBloque0;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la validación de Bloque 0 para los
 * archivos PILA de Operador de información<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 391 <br/>
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class ValidacionOIBloque0 implements IValidacionOIBloque0, Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ValidacionOIBloque0.class);

	@Inject
	private IPersistenciaDatosValidadores persistencia;

	@Inject
	private IGestorEstadosValidacion gestorEstados;
	
	@Inject
	private IArchivosPILAEjecucion orquestador;

	/** DTO de respuesta para la validación */
	private RespuestaValidacionDTO respuesta;

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOIBloque0#validarBloqueCero(com.asopagos.entidades.pila.procesamiento.IndicePlanilla,
     *      java.util.Map, java.lang.String)
     */
    @Override
    public RespuestaValidacionDTO validarBloqueCero(IndicePlanilla indicePlanilla, Map<String, Object> contexto, String usuario) {
		String firmaMetodo = "ValidacionOIBloque0.validarBloqueCero(IndicePlanilla, Map<String, Object>, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		// se prepara el DTO de respuesta
		respuesta = respuesta == null ? new RespuestaValidacionDTO() : respuesta;

		// se lee el tamaño límite de archivos desde el contexto
		Long sizeLimit = null;
		if (contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL) == null
				|| !StringUtils.isNumeric((String) contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL))) {
			// falta del parámetro, se lanza excepción técnica
			TechnicalException e = new TechnicalException("No se cuenta con el parámetro de tamaño límite para archivos cargados");
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw e;
		}

		sizeLimit = Long.valueOf((String) contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL));

		// se valida el tamaño del archivo
		indicePlanilla.setEstadoArchivo(validarTamanioArchivo(indicePlanilla, sizeLimit));

		// sí no se da un estado fallido relacionado con el tamaño del archivo,
		// se valida la extensión
		if (indicePlanilla.getEstadoArchivo() == null
				|| !indicePlanilla.getEstadoArchivo().getReportarBandejaInconsistencias()) {
			indicePlanilla.setEstadoArchivo(validarExtension(indicePlanilla));
		}

		CombinacionesArchivosPilaDTO revisionCombinatoria = null;

		// sí no falla la validación de extensión, se valida la duplicidad y
		// combinatoria
		if (indicePlanilla.getEstadoArchivo() == null
				|| !indicePlanilla.getEstadoArchivo().getReportarBandejaInconsistencias()) {
			// se procede a validar duplicidad y combinatoria
			revisionCombinatoria = validarDuplicidadYCombinatoria(indicePlanilla);

			if (revisionCombinatoria != null) {
				// en el caso de contar con un resultado de combinación de
				// archivos válida
				// se solicita la persistencia del índice

				indicePlanilla = revisionCombinatoria.getArchivoCargado();
				indicePlanilla.setProcesar(revisionCombinatoria.getProcesar());

				// se procesan las anulaciones que den a lugar
				try {
					procesarAnulaciones(revisionCombinatoria, usuario);
				} catch (ErrorFuncionalValidacionException e) {
					// sí se presenta un error en la anulación de registros
					// anteriores,
					// se debe cambiar el estado del índice, la marca de proceso
					// y agregar el error a la respuesta
					indicePlanilla.setProcesar(false);
					indicePlanilla.setEstadoArchivo(
							EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_ANULACION_FALLIDA);

					ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
					error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
					error.setTipoArchivo(indicePlanilla.getTipoArchivo());
					error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
							MensajesValidacionEnum.ERROR_ARCHIVO_ANULACION_FALLIDA.getReadableMessage(
									TipoErrorValidacionEnum.TIPO_3.toString(), indicePlanilla.getNombreArchivo()));

					respuesta.addErrorDetalladoValidadorDTO(error);
				}
			} else {
				logger.info("Entra DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO 2");
				indicePlanilla.setEstadoArchivo(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO);

				ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
				error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
				error.setTipoArchivo(indicePlanilla.getTipoArchivo());
				error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
						MensajesValidacionEnum.ERROR_ARCHIVO_DUPLICADO.getReadableMessage(
								TipoErrorValidacionEnum.TIPO_3.toString(), indicePlanilla.getNombreArchivo()));

				respuesta.addErrorDetalladoValidadorDTO(error);
			}
		}
		
		// se actualiza el estado del índice
		//MANTIS 266507 Se descomenta como antes de mejoras mundo1
		respuesta = orquestador.actualizarIndiceYEstadoBloque(indicePlanilla, indicePlanilla.getEstadoArchivo(), respuesta, 0, null);
		persistencia.anularPlanillas(indicePlanilla);
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		return respuesta;
	}

	/**
	 * Método que prepara al nuevo índice de planilla
	 * 
	 * @param archivoPila
	 *            DTO de carga del archivo
	 * @param indicePlanilla
	 *            Nueva instancia de índice de planilla para preparar
	 * @return IndicePlanilla Instancia nueva actualizada
	 */
	private void prepararIndice(ArchivoPilaDTO archivoPila, IndicePlanilla indicePlanilla) {

		String mensaje = null;
		String tipoError = TipoErrorValidacionEnum.TIPO_3.toString();

		// en el caso de no contar con la información requerida del DTO del
		// archivo, se debe lanzar excepción funcional
		if (!validarInformacionBaseRegistro(archivoPila)) {
			mensaje = MensajesValidacionEnum.ERROR_ARCHIVO_INCOMPLETO.getReadableMessage(tipoError);
		} else {

			Long idPlanilla = null;
			TipoArchivoPilaEnum tipoArchivoCargado = FuncionesValidador.getTipoArchivo(archivoPila.getFileName());

			if (!EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE
					.equals(indicePlanilla.getEstadoArchivo())) {
				idPlanilla = (Long) FuncionesValidador.obtenerCampoNombreArchivo(tipoArchivoCargado,
						CamposNombreArchivoEnum.NUMERO_PLANILLA_OI, archivoPila.getFileName());
			} else {
				// una planilla que presentan error por extensión doble, debe
				// perder su número de planilla para no ocasionar conflictos
				idPlanilla = 0L;
				indicePlanilla.setProcesar(false);
			}

			// en le caso de no contar con el número de planilla o el tipo de
			// archivo, se debe lanzar excepción técnica
			if (tipoArchivoCargado == null || idPlanilla == null) {
                mensaje = "ValidacionOIBloque0.prepararIndice : No fue posible identificar un número de planilla o de tipo en el archivo "
                        + archivoPila.getFileName();
                
                logger.error(mensaje);
                throw new TechnicalException(mensaje);
			}

			if(mensaje == null){
	            indicePlanilla.setFechaRecibo(Calendar.getInstance().getTime());
	            indicePlanilla.setTipoArchivo(tipoArchivoCargado);
	            indicePlanilla.setIdPlanilla(idPlanilla);
	            indicePlanilla.setNombreArchivo(archivoPila.getFileName());
	            indicePlanilla.setTipoCargaArchivo(archivoPila.getModo());
	            indicePlanilla.setUsuario(archivoPila.getUsuario());
	            indicePlanilla.setRegistroActivo(true);
	            indicePlanilla.setEnLista(false);
	            indicePlanilla.setTamanoArchivo(archivoPila.getSize());

	            // se agrega el código del operador de información a partir del
	            // nombre
	            indicePlanilla.setCodigoOperadorInformacion((String) FuncionesValidador.obtenerCampoNombreArchivo(
	                    tipoArchivoCargado, CamposNombreArchivoEnum.CODIGO_OPERADOR_OI, archivoPila.getFileName()));

	            // se agregan los identificadores del documento en ECM
	            indicePlanilla.setIdDocumento(archivoPila.getIdentificadorDocumento());
	            indicePlanilla.setVersionDocumento(archivoPila.getVersionDocumento());

	            // se agrega la fecha de modificación del archivo (sí está presente
	            // en el DTO)
	            if (archivoPila.getFechaModificacion() != null) {
	                Date fechaMod = new Date();
	                fechaMod.setTime(archivoPila.getFechaModificacion());
	                indicePlanilla.setFechaFtp(fechaMod);
	            } else if (tipoArchivoCargado.isReproceso()) {
	                indicePlanilla.setEstadoArchivo(
	                        EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_SIN_FECHA_MODIFICACION);

	                mensaje = MensajesValidacionEnum.ERROR_ARCHIVO_SIN_FECHA_MODIFICACION.getReadableMessage(
	                                TipoErrorValidacionEnum.TIPO_3.toString(), indicePlanilla.getNombreArchivo());
	            }
			}
			
			if(mensaje != null){
			    logger.error(mensaje);
			    
                ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
                error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
                error.setTipoArchivo(indicePlanilla.getTipoArchivo());
                error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

                respuesta.addErrorDetalladoValidadorDTO(error);
			}
		}
	}

	/**
	 * Método para establecer que se cuenta con la información básica para
	 * realizar el registro de un nuevo índice de planilla
	 * 
	 * @param archivoPila
	 *            DTO con la información del archivo que se desea cargar
	 * @return <b>Boolean</b> Indica cumplimiento en la información básica
	 */
	private Boolean validarInformacionBaseRegistro(ArchivoPilaDTO archivoPila) {
		if (archivoPila != null) {
			// nombre del archivo
			if (archivoPila.getFileName() == null && archivoPila.getFileName().isEmpty()) {
				logger.error("No se cuenta con el nombre del archivo");
				return false;
			}

			// tipo de carga
			if (archivoPila.getModo() == null) {
				logger.error("No se cuenta con el tipo de carga del archivo");
				return false;
			}

			// usuario que realiza la carga
			if (archivoPila.getUsuario() == null || archivoPila.getUsuario().isEmpty()) {
				logger.error("No se cuenta con el usuario que realiza la carga del archivo");
				return false;
			}

			// identificador del documento en ECM
			if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(archivoPila.getModo())
					&& archivoPila.getIdentificadorDocumento() == null) {
				logger.error("No se cuenta con el ID del archivo");
				return false;
			}

			// versión del documento en ECM
			if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(archivoPila.getModo())
					&& archivoPila.getVersionDocumento() == null) {
				logger.error("No se cuenta con la versión del archivo");
				return false;
			}
		} else {
			logger.error("No se cuenta con el DTO del archivo");
			return false;
		}

		return true;
	}

	/**
	 * Método que se encarga de validar el tamaño de un archivo PILA a cargar
	 * 
     * @param indicePlanilla
     *        Entrada de índice de planilla a validar
	 * @param sizeLimit
	 *            Parámetro de límite para el tamaño del archivo
	 * @return EstadoProcesoArchivoEnum Estado de error derivado del tamaño de
	 *         archivo
	 */
	private EstadoProcesoArchivoEnum validarTamanioArchivo(IndicePlanilla indicePlanilla, Long sizeLimit) {

		EstadoProcesoArchivoEnum respuestaEstado = null;

		String mensaje = null;
		String tipoError = TipoErrorValidacionEnum.TIPO_3.toString();

		// se convierte el valor del parámetro a bytes
		Long sizeLimitBytes = sizeLimit * 1024 * 1024;

		// primero se valida el tamaño cero
		if (indicePlanilla != null) {
			if (!TipoCargaArchivoEnum.AUTOMATICA_WEB.equals(indicePlanilla.getTipoCargaArchivo())
					&& (indicePlanilla.getTamanoArchivo() == null || indicePlanilla.getTamanoArchivo() == 0)) {
				respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO;

				mensaje = MensajesValidacionEnum.ERROR_TAMANO_ARCHIVO_CERO.getReadableMessage(
						ConstantesComunesProcesamientoPILA.TAMANO_ARCHIVO, "0", tipoError, indicePlanilla.getNombreArchivo());
	            
	            logger.error(indicePlanilla.getNombreArchivo() + " :: " + respuestaEstado.getDescripcion());
			} else {
				// luego se valida tamaño máximo individual
				// la validación de tamaño máximo sólo aplica para carga manual

				if (TipoCargaArchivoEnum.MANUAL.equals(indicePlanilla.getTipoCargaArchivo())) {
					// el tamaño está definido en Bytes, así que se compara con
					// el valor parametrizado
					if (indicePlanilla.getTamanoArchivo().compareTo(sizeLimitBytes) > 0) {
						String tamanoEnMb = Long.toString(indicePlanilla.getTamanoArchivo() / 1024 / 1024);

						respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO;

						mensaje = MensajesValidacionEnum.ERROR_TAMANO_ARCHIVO_INDIVIDUAL.getReadableMessage(
								ConstantesComunesProcesamientoPILA.TAMANO_ARCHIVO, tamanoEnMb, tipoError,
								indicePlanilla.getNombreArchivo(), sizeLimit.toString());
			            
			            logger.error(indicePlanilla.getNombreArchivo() + " :: " + respuestaEstado.getDescripcion());
					}
				}
			}
		} else {
			// si no se recibe el DTO, se considera tamaño cero
			respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO;

			mensaje = MensajesValidacionEnum.ERROR_ARCHIVO_NULO
					.getReadableMessage(ConstantesComunesProcesamientoPILA.TAMANO_ARCHIVO, "0", tipoError);
			
			logger.error("Sin datos de archivo :: " + respuestaEstado.getDescripcion());
		}

		// sí se presenta un error de validación
		if (respuestaEstado != null) {
		    logger.error(mensaje);
		    
			ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
			error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
			error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

			if (indicePlanilla != null) {
				error.setTipoArchivo(FuncionesValidador.getTipoArchivo(indicePlanilla.getNombreArchivo()));
			}

			this.respuesta.addErrorDetalladoValidadorDTO(error);
		}

		return respuestaEstado;
	}

	/**
	 * Método que valida que la extensión del archivo sea txt
	 * 
     * @param indicePlanilla
     *        Entrada de índice de planilla a validar
	 * @return EstadoProcesoArchivoEnum Estado de error derivado del tamaño de
	 *         archivo
	 */
	private EstadoProcesoArchivoEnum validarExtension(IndicePlanilla indicePlanilla) {
		EstadoProcesoArchivoEnum respuestaEstado = null;

		String mensaje = null;
		String tipoError = TipoErrorValidacionEnum.TIPO_3.toString();

		if (indicePlanilla.getNombreArchivo() == null || !(indicePlanilla.getNombreArchivo().toLowerCase().endsWith(".txt"))) {
			respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA;
		} else if (indicePlanilla.getNombreArchivo().split("\\.").length > 2) {
			respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE;
		}

		// sí se presenta un error de validación
		if (respuestaEstado != null) {
			mensaje = MensajesValidacionEnum.ERROR_EXTENSION_ARCHIVO.getReadableMessage(
					ConstantesComunesProcesamientoPILA.EXTENSION_ARCHIVO, indicePlanilla.getNombreArchivo(), tipoError);

			ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
			error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
			error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

			if (indicePlanilla != null) {
				error.setTipoArchivo(FuncionesValidador.getTipoArchivo(indicePlanilla.getNombreArchivo()));
			}

			this.respuesta.addErrorDetalladoValidadorDTO(error);
            
            logger.error(indicePlanilla.getNombreArchivo() + " :: " + respuestaEstado.getDescripcion());
		}

		return respuestaEstado;
	}

	/**
	 * Método que determina si el archivo está duplicado o cumple con una
	 * combinación de archivos válida.
	 * 
	 * @param archivoPila
	 * @param entityManager
	 */
	private CombinacionesArchivosPilaDTO validarDuplicidadYCombinatoria(IndicePlanilla archivoCargado) {
		String firmaMetodo = "ValidacionOIBloque0.validarDuplicidadYCombinatoria(IndicePlanilla)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<ElementoCombinatoriaArchivosDTO> archivosPilaCargados = null;

		CombinacionesArchivosPilaDTO combArchivosPilaDTO = null;
		logger.info("validarDuplicidadYCombinatoria");
		logger.info(persistencia != null);
		logger.info(archivoCargado.getTipoArchivo().isReproceso());
		logger.info(archivoCargado.getEstadoArchivo());

		if (persistencia != null) {
			combArchivosPilaDTO = new CombinacionesArchivosPilaDTO();

			archivosPilaCargados = persistencia.consultarEntradasAnteriores(archivoCargado);
			archivosPilaCargados.addAll(persistencia.consultarEntradasAnterioresMigradas(archivoCargado));

			combArchivosPilaDTO.setArchivoCargado(archivoCargado);
			combArchivosPilaDTO.setReproceso(false);

			if (archivosPilaCargados == null || archivosPilaCargados.isEmpty()) {
				// escenario cubiertos
				// A:- I:- AR:- IR:-
				combArchivosPilaDTO.setArchivoPrevio(null);
				combArchivosPilaDTO.setProcesar(true);

				// se agrega el estado al índice
				if (archivoCargado.getTipoArchivo().isReproceso()) {
					persistencia.anularPlanillasReproceso(archivoCargado);
					archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO_REPROCESO);
				} else {
					if(persistencia.anularPlanillas(archivoCargado)){
						CombinacionesArchivosPilaDTO combArchivosPilaDTO2 = null;

						return combArchivosPilaDTO2;
					}
					archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO);
				}
			} else {

				Boolean cargar = true;

				// se valida el grupo del archivo entrante respecto a archivos
				// previos
				archivoCargado.setEstadoArchivo(construirIndicePlanilla(combArchivosPilaDTO, archivosPilaCargados));

				// sí se ha recibido un archivo de un grupo que no es válido, se
				// indica que el archivo no se debe procesar
				if (archivoCargado.getEstadoArchivo() != null) {
					cargar = false;
				}

				if (cargar) {
					// en el caso de ser válido para cargar por concepto de
					// grupo de archivo, se revisa la cambinación de archivos
					// de acuerdo a lo establecido en el Anexo 211 - Hoja 14A

					cargar = false;
					ElementoCombinatoriaArchivosDTO entrada = null;
					
					switch (archivoCargado.getTipoArchivo()) {
					case ARCHIVO_OI_A:
					case ARCHIVO_OI_AP:
						// escenarios previos cubiertos
						// A:- I:- AR:- IR:-
						// A:- I:X AR:- IR:-
						if (combArchivosPilaDTO.getArchivo_A_AP() == null
								&& (combArchivosPilaDTO.getArchivo_I_IP() == null
										|| archivoProcesado(combArchivosPilaDTO.getArchivo_I_IP()))
								&& combArchivosPilaDTO.getArchivo_AR_APR() == null
								&& combArchivosPilaDTO.getArchivo_IR_IPR() == null) {
							cargar = true;

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO);
						} else if (combArchivosPilaDTO.getArchivo_AR_APR() != null
								|| combArchivosPilaDTO.getArchivo_IR_IPR() != null) {
							String nombreArchivoPrevio = null;

							// para este error, basta con mostrar el nombre del
							// primer archivo R cargado (sea el IXR o AXR)
							if (combArchivosPilaDTO.getArchivo_AR_APR() != null
									&& !combArchivosPilaDTO.getArchivo_AR_APR().isEmpty()) {
								nombreArchivoPrevio = combArchivosPilaDTO.getArchivo_AR_APR().get(0).getIndice()
										.getNombreArchivo();
							} else if (combArchivosPilaDTO.getArchivo_IR_IPR() != null
									&& !combArchivosPilaDTO.getArchivo_IR_IPR().isEmpty()) {
								nombreArchivoPrevio = combArchivosPilaDTO.getArchivo_IR_IPR().get(0).getIndice()
										.getNombreArchivo();
							}

							// se debe anular el archivo I_IP entrante
							combArchivosPilaDTO.setAnularCargado(true);

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(
									EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO);
							archivoCargado.setRegistroActivo(true);

							ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
							error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
							error.setTipoArchivo(archivoCargado.getTipoArchivo());
							error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
									MensajesValidacionEnum.ERROR_ARCHIVO_REPROCESO_PREVIO.getReadableMessage(
											ConstantesComunesProcesamientoPILA.DUPLICIDAD_ARCHIVOS, nombreArchivoPrevio,
											TipoErrorValidacionEnum.TIPO_3.toString(),
											archivoCargado.getNombreArchivo()));

							respuesta.addErrorDetalladoValidadorDTO(error);
						}
						break;
					case ARCHIVO_OI_I:
					case ARCHIVO_OI_IP:
						// escenarios previos cubiertos
						// A:- I:- AR:- IR:-
						// A:X I:- AR:- IR:-
						if (combArchivosPilaDTO.getArchivo_I_IP() == null
								&& (combArchivosPilaDTO.getArchivo_A_AP() == null
										|| archivoProcesado(combArchivosPilaDTO.getArchivo_A_AP()))
								&& combArchivosPilaDTO.getArchivo_AR_APR() == null
								&& combArchivosPilaDTO.getArchivo_IR_IPR() == null) {
							cargar = true;

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO);
						} else if (combArchivosPilaDTO.getArchivo_AR_APR() != null
								|| combArchivosPilaDTO.getArchivo_IR_IPR() != null) {
							String nombreArchivoPrevio = null;

							// para este error, basta con mostrar el nombre del
							// primer archivo R cargado (sea el IXR o AXR)
							if (combArchivosPilaDTO.getArchivo_AR_APR() != null
									&& !combArchivosPilaDTO.getArchivo_AR_APR().isEmpty()) {
								nombreArchivoPrevio = combArchivosPilaDTO.getArchivo_AR_APR().get(0).getIndice()
										.getNombreArchivo();
							} else if (combArchivosPilaDTO.getArchivo_IR_IPR() != null
									&& !combArchivosPilaDTO.getArchivo_IR_IPR().isEmpty()) {
								nombreArchivoPrevio = combArchivosPilaDTO.getArchivo_IR_IPR().get(0).getIndice()
										.getNombreArchivo();
							}

							// se debe anular el archivo A_AP entrante
							combArchivosPilaDTO.setAnularCargado(true);

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(
									EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO);
							archivoCargado.setRegistroActivo(true);

							ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
							error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
							error.setTipoArchivo(archivoCargado.getTipoArchivo());
							error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
									MensajesValidacionEnum.ERROR_ARCHIVO_REPROCESO_PREVIO.getReadableMessage(
											ConstantesComunesProcesamientoPILA.DUPLICIDAD_ARCHIVOS, nombreArchivoPrevio,
											TipoErrorValidacionEnum.TIPO_3.toString(),
											archivoCargado.getNombreArchivo()));

							respuesta.addErrorDetalladoValidadorDTO(error);
						}
						break;
					case ARCHIVO_OI_AR:
					case ARCHIVO_OI_APR:
						combArchivosPilaDTO.setReproceso(Boolean.TRUE);
						respuesta.setEsReproceso(Boolean.TRUE);

						// sí algún listado no es anulable
						if (!combArchivosPilaDTO.listadoAnulable()) {
							// se debe anular el AR_APR entrante
							combArchivosPilaDTO.setAnularCargado(true);
							archivoCargado.setRegistroActivo(true);
						} else if (combArchivosPilaDTO.getArchivo_AR_APR() != null
								&& !combArchivosPilaDTO.getArchivo_AR_APR().isEmpty()) {
							Long fechaA = archivoCargado.getFechaFtp().getTime();
							Long fechaB = null;

							// se recorren los archivos AR encontrados
							// previamente
							for (ElementoCombinatoriaArchivosDTO archivo_AR_APR : combArchivosPilaDTO
									.getArchivo_AR_APR()) {
								fechaB = archivo_AR_APR.getIndice().getFechaFtp().getTime();

								if (fechaA.compareTo(fechaB) > 0) {
									entrada = new ElementoCombinatoriaArchivosDTO(archivo_AR_APR.getIndice(), 
											archivo_AR_APR.getEsAnulable());
									
									combArchivosPilaDTO.addIndiceListado(entrada, 1);

									// se debe anular el AR_APR previo
									cargar = true;
									combArchivosPilaDTO.setAnularAnterior(true);

									// se agrega el estado al índice
									archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO_REPROCESO_ACTUAL);
								} else {
									// se debe anular el AR_APR entrante
									combArchivosPilaDTO.setAnularCargado(true);
									// se elimina la orden de anular previos en
									// caso de haber sido marcada
									combArchivosPilaDTO.setAnularAnterior(false);

									// se agrega el estado al índice
									archivoCargado.setEstadoArchivo(
											EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR);

									archivoCargado.setRegistroActivo(true);

									String nombreArchivoPrevio = archivo_AR_APR.getIndice().getNombreArchivo();

									ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
									error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
									error.setTipoArchivo(archivoCargado.getTipoArchivo());
									error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
											MensajesValidacionEnum.ERROR_ARCHIVO_DUPLICADO_ANTERIOR.getReadableMessage(
													ConstantesComunesProcesamientoPILA.DUPLICIDAD_ARCHIVOS,
													nombreArchivoPrevio, TipoErrorValidacionEnum.TIPO_3.toString(),
													archivoCargado.getNombreArchivo()));

									respuesta.addErrorDetalladoValidadorDTO(error);
									// se cierra el ciclo debido a que el
									// archivo entrante se debe anular
									break;
								}
							}

							// en el caso de existir archivos A_AP y/o I_IP
							// previos,
							// se les debe anular
							combArchivosPilaDTO.setAnularOriginales(true);
						}
						// si el listado no es anulable, se anula el actual
						else {
							cargar = true;

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO_REPROCESO);

							// en el caso de existir archivos A_AP y/o I_IP
							// previos,
							// se les debe anular
							combArchivosPilaDTO.setAnularOriginales(true);
						}
						break;
					case ARCHIVO_OI_IR:
					case ARCHIVO_OI_IPR:
						combArchivosPilaDTO.setReproceso(Boolean.TRUE);
						respuesta.setEsReproceso(Boolean.TRUE);

						// sí algún listado no es anulable
						if (!combArchivosPilaDTO.listadoAnulable()) {
							// se debe anular el AR_APR entrante
							combArchivosPilaDTO.setAnularCargado(true);
							archivoCargado.setRegistroActivo(true);
						} else if (combArchivosPilaDTO.getArchivo_IR_IPR() != null
								&& !combArchivosPilaDTO.getArchivo_IR_IPR().isEmpty()) {
							Long fechaA = archivoCargado.getFechaFtp().getTime();
							Long fechaB = null;

							// se recorren los archivos AR encontrados
							// previamente
							for (ElementoCombinatoriaArchivosDTO archivo_IR_IPR : combArchivosPilaDTO
									.getArchivo_IR_IPR()) {
								fechaB = archivo_IR_IPR.getIndice().getFechaFtp().getTime();

								if (fechaA.compareTo(fechaB) > 0) {
									entrada = new ElementoCombinatoriaArchivosDTO(archivo_IR_IPR.getIndice(), 
											archivo_IR_IPR.getEsAnulable());
									
									combArchivosPilaDTO.addIndiceListado(entrada, 1);

									// se debe anular el IR_IPR previo
									cargar = true;
									combArchivosPilaDTO.setAnularAnterior(true);

									// se agrega el estado al índice
									archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO_REPROCESO_ACTUAL);
								} else {
									// se debe anular el IR_IPR entrante
									combArchivosPilaDTO.setAnularCargado(true);
									// se elimina la orden de anular previos en
									// casod e haber sido marcada
									combArchivosPilaDTO.setAnularAnterior(false);

									// se agrega el estado al índice
									archivoCargado.setEstadoArchivo(
											EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR);

									archivoCargado.setRegistroActivo(true);

									String nombreArchivoPrevio = archivo_IR_IPR.getIndice().getNombreArchivo();

									ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
									error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
									error.setTipoArchivo(archivoCargado.getTipoArchivo());
									error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
											MensajesValidacionEnum.ERROR_ARCHIVO_DUPLICADO_ANTERIOR.getReadableMessage(
													ConstantesComunesProcesamientoPILA.DUPLICIDAD_ARCHIVOS,
													nombreArchivoPrevio, TipoErrorValidacionEnum.TIPO_3.toString(),
													archivoCargado.getNombreArchivo()));

									respuesta.addErrorDetalladoValidadorDTO(error);
									// se cierra el ciclo debido a que el
									// archivo entrante se debe anular
									break;
								}
							}

							// en el caso de existir archivos A_AP y/o I_IP
							// previos,
							// se les debe anular
							combArchivosPilaDTO.setAnularOriginales(true);
						} else {
							cargar = true;

							// se agrega el estado al índice
							archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.CARGADO_REPROCESO);

							// en el caso de existir archivos A_AP y/o I_IP
							// previos,
							// se les debe anular
							combArchivosPilaDTO.setAnularOriginales(true);
						}
						break;
					default:
						break;
					}
				}

				// en este punto, si el índice continúa sin estado y "cargar" es
				// falso, implica que el archivo es duplicado "común"
				if (!cargar && archivoCargado.getEstadoArchivo() == null) {
					logger.info("Entra DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO 3");
					archivoCargado.setEstadoArchivo(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO);
					archivoCargado.setRegistroActivo(true);

					ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
					error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
					error.setTipoArchivo(archivoCargado.getTipoArchivo());
					error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
							MensajesValidacionEnum.ERROR_ARCHIVO_DUPLICADO.getReadableMessage(
									ConstantesComunesProcesamientoPILA.DUPLICIDAD_ARCHIVOS,
									archivoCargado.getNombreArchivo(), TipoErrorValidacionEnum.TIPO_3.toString()));

					respuesta.addErrorDetalladoValidadorDTO(error);
				}

				combArchivosPilaDTO.setProcesar(cargar);
			}

		} else {
			String mensaje = MensajesValidacionEnum.ERROR_INSTANCIA_PERSISTENCIA
					.getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString());
			
			TechnicalException e = new TechnicalException(mensaje);
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw e;
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return combArchivosPilaDTO;
	}

	/**
	 * Método que se construye la tabla de combinaciones para una carga
	 * 
	 * @param combArchivosPilaDTO
	 * @param archivosPilaCargados
	 * @return
	 */
	private EstadoProcesoArchivoEnum construirIndicePlanilla(CombinacionesArchivosPilaDTO combArchivosPilaDTO,
			List<ElementoCombinatoriaArchivosDTO> archivosPilaCargados) {

		EstadoProcesoArchivoEnum respuesta = null;

		// se mapea el cuadro de combinaciones para realizar la validación
		IndicePlanilla indicePlanilla = null;
		for (ElementoCombinatoriaArchivosDTO entrada : archivosPilaCargados) {
			indicePlanilla = entrada.getIndice();
			
			// se valida el caso que llegue un archivo de un grupo no
			// esperado
			if (!indicePlanilla.getTipoArchivo().getGrupo()
					.equals(combArchivosPilaDTO.getArchivoCargado().getTipoArchivo().getGrupo())) {
				respuesta = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO;
				combArchivosPilaDTO.getArchivoCargado().setRegistroActivo(true);

				ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
				error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
				error.setTipoArchivo(combArchivosPilaDTO.getArchivoCargado().getTipoArchivo());
				error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
						MensajesValidacionEnum.ERROR_ARCHIVO_GRUPO_NO_VALIDO.getReadableMessage(
								ConstantesComunesProcesamientoPILA.COMBINATORIA_ARCHIVOS,
								combArchivosPilaDTO.getArchivoCargado().getNombreArchivo(),
								TipoErrorValidacionEnum.TIPO_3.toString()));

				this.respuesta.addErrorDetalladoValidadorDTO(error);
			}

			/*
			 * se verifica que el archivo no tenga problemas de extensión doble,
			 * de ser así, no se le agrega para que no de problema de duplicidad
			 */
			if (indicePlanilla.getNombreArchivo().split("\\.").length <= 2) {

				switch (indicePlanilla.getTipoArchivo()) {

				case ARCHIVO_OI_A:
				case ARCHIVO_OI_AP:
					combArchivosPilaDTO.addIndiceListado(entrada, 2);
					break;

				case ARCHIVO_OI_I:
				case ARCHIVO_OI_IP:
					combArchivosPilaDTO.addIndiceListado(entrada, 3);
					break;

				case ARCHIVO_OI_AR:
				case ARCHIVO_OI_APR:
					combArchivosPilaDTO.addIndiceListado(entrada, 4);
					break;

				case ARCHIVO_OI_IR:
				case ARCHIVO_OI_IPR:
					combArchivosPilaDTO.addIndiceListado(entrada, 5);
					break;
				default:// caso que llegue el archivo de operador financiero
					respuesta = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO;
					combArchivosPilaDTO.getArchivoCargado().setRegistroActivo(true);

					ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
					error.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
					error.setTipoArchivo(indicePlanilla.getTipoArchivo());
					error = FuncionesValidador.descomponerMensajeErrorValidacion(error,
							MensajesValidacionEnum.ERROR_ARCHIVO_GRUPO_NO_VALIDO.getReadableMessage(
									ConstantesComunesProcesamientoPILA.COMBINATORIA_ARCHIVOS,
									indicePlanilla.getNombreArchivo(), TipoErrorValidacionEnum.TIPO_3.toString()));

					this.respuesta.addErrorDetalladoValidadorDTO(error);
					break;
				}
			}
		}
		return respuesta;
	}

	/**
	 * 
	 * @param archivosPila
	 * @return
	 */
	private Boolean archivoProcesado(List<ElementoCombinatoriaArchivosDTO> archivosPila) {
		if (archivosPila != null) {
			for (ElementoCombinatoriaArchivosDTO elemento : archivosPila) {
				if (!EstadoProcesoArchivoEnum.ANULADO.equals(elemento.getIndice().getEstadoArchivo())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param revisionCombinatoria
	 * @throws ErrorFuncionalValidacionException
	 */
	private void procesarAnulaciones(CombinacionesArchivosPilaDTO revisionCombinatoria, String usuarioAnulacion)
			throws ErrorFuncionalValidacionException {
		String firmaMetodo = "ValidacionOIBloque0.procesarAnulaciones(CombinacionesArchivosPilaDTO, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Date fechaAnulacion = Calendar.getInstance().getTime();

		// se solicita la anulación del índice anterior si se da el caso
		if (revisionCombinatoria.getAnularAnterior() && (revisionCombinatoria.getArchivoPrevio() != null
				&& !revisionCombinatoria.getArchivoPrevio().isEmpty())) {
			for (ElementoCombinatoriaArchivosDTO archivoPrevio : revisionCombinatoria.getArchivoPrevio()) {
//				rechazarSolicitudesCambioId(archivoPrevio.getIndice().getId(), usuarioAnulacion);

				gestorEstados.anularUltimoBloque(archivoPrevio.getIndice());
				archivoPrevio.setIndice((IndicePlanilla) persistencia.eliminarIndicePlanilla(archivoPrevio.getIndice(),
						fechaAnulacion, usuarioAnulacion));
				persistencia.cerrarInconsistencias(archivoPrevio.getIndice().getId());

				// se eliminan las variables de paso
				persistencia.eliminarVariables(archivoPrevio.getIndice().getIdPlanilla(),
						archivoPrevio.getIndice().getCodigoOperadorInformacion());
			}

		}

		// se solicita la anulación de los archivos normales anteriores
		if (revisionCombinatoria.isAnularOriginales() && (revisionCombinatoria.getArchivo_A_AP() != null
				&& !revisionCombinatoria.getArchivo_A_AP().isEmpty())) {
			for (ElementoCombinatoriaArchivosDTO archivo_A_AP : revisionCombinatoria.getArchivo_A_AP()) {
//				rechazarSolicitudesCambioId(archivo_A_AP.getIndice().getId(), usuarioAnulacion);

				gestorEstados.anularUltimoBloque(archivo_A_AP.getIndice());
				archivo_A_AP.setIndice((IndicePlanilla) persistencia.eliminarIndicePlanilla(archivo_A_AP.getIndice(),
						fechaAnulacion, usuarioAnulacion));
				persistencia.cerrarInconsistencias(archivo_A_AP.getIndice().getId());

				// se eliminan las variables de paso
				persistencia.eliminarVariables(archivo_A_AP.getIndice().getIdPlanilla(),
						archivo_A_AP.getIndice().getCodigoOperadorInformacion());
			}

		}
		if (revisionCombinatoria.isAnularOriginales() && (revisionCombinatoria.getArchivo_I_IP() != null
				&& !revisionCombinatoria.getArchivo_I_IP().isEmpty())) {
			for (ElementoCombinatoriaArchivosDTO archivo_I_IP : revisionCombinatoria.getArchivo_I_IP()) {
//				rechazarSolicitudesCambioId(archivo_I_IP.getIndice().getId(), usuarioAnulacion);

				gestorEstados.anularUltimoBloque(archivo_I_IP.getIndice());
				archivo_I_IP.setIndice((IndicePlanilla) persistencia.eliminarIndicePlanilla(archivo_I_IP.getIndice(),
						fechaAnulacion, usuarioAnulacion));
				persistencia.cerrarInconsistencias(archivo_I_IP.getIndice().getId());
			}
		}

		logger.debug("Finaliza procesarAnulaciones(CombinacionesArchivosPilaDTO)");
	}

	/**
	 * Método para la consulta y rechazo automático de las solicitudes de cambio
	 * de ID de aportante
	 */
	/*private void rechazarSolicitudesCambioId(Long idPlanilla, String usuarioAnulacion) {
		String firmaMetodo = "ValidacionOIBloque0.rechazarSolicitudesCambioId(Long, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		// se consulta sí hay solicitudes de cambio de ID para el índice de
		// planilla que se va a anular
		SolicitudCambioNumIdentAportante solicitud = persistencia.consultarSolicitudCambioId(idPlanilla);

		if (solicitud != null) {
			persistencia.rechazarSolicitudesCambioId(solicitud, usuarioAnulacion);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}*/
	
    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOIBloque0#registrarIndice(com.asopagos.dto.ArchivoPilaDTO,
     *      java.util.Map, java.lang.String, java.lang.Boolean)
     */
	@Override
    public RespuestaValidacionDTO registrarIndice(ArchivoPilaDTO archivoPila, Map<String, Object> contexto,
            String usuario, Boolean validarB0) {
        String firmaMetodo = "ValidacionOIBloque0.registrarIndice(ArchivoPilaDTO, Map<String, Object>, String, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se prepara el DTO de respuesta
        respuesta = new RespuestaValidacionDTO();

        // instancia base del nuevo índice de planilla
        IndicePlanilla indicePlanilla = new IndicePlanilla();
        respuesta.setIndicePlanilla(indicePlanilla);

        // se realiza una preparación preliminar del nuevo índice
        prepararIndice(archivoPila, indicePlanilla);
        
        indicePlanilla.setEstadoArchivo(EstadoProcesoArchivoEnum.DESCARGADO);
        indicePlanilla.setUsuarioProceso(usuario);
        indicePlanilla.setFechaProceso(new Date());
        indicePlanilla.setProcesar(Boolean.TRUE);

        // se persiste el nuevo índice de planilla
        this.persistencia.registrarEnIndicePlanillas(indicePlanilla);

        // se registra el estado del nuevo índice
        try {
            gestorEstados.registrarEstadoArchivo(indicePlanilla, indicePlanilla.getEstadoArchivo(),
                    FuncionesValidador.determinarAccion(indicePlanilla.getEstadoArchivo(), indicePlanilla.getTipoArchivo()),
                    "", 0, null);
        } catch (ErrorFuncionalValidacionException e) {
            // la operación específica realizada en este método no da lugar a un
            // estado no válido que lance la excepción
        }
        
        respuesta.setIndicePlanilla(indicePlanilla);
    
        if(validarB0 != null && validarB0){
            respuesta = validarBloqueCero(indicePlanilla, contexto, usuario);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuesta;
	}
}
