package com.asopagos.aportes.masivos.service.business.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.masivos.dto.ArchivoDevolucionDTO;
import com.asopagos.aportes.masivos.dto.DatosRadicacionMasivaDTO;
import com.asopagos.aportes.masivos.dto.ResultadoArchivoAporteDTO;
import com.asopagos.aportes.masivos.dto.ResultadoCotizanteAporteMasivoDTO;
import com.asopagos.aportes.masivos.dto.ResultadoValidacionAporteDTO;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloPila;
import com.asopagos.aportes.masivos.service.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.entidades.pila.masivos.*;
import com.asopagos.dto.*;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.*;
import com.asopagos.enumeraciones.pila.*;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import java.lang.Boolean;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.EstadoGestionAporteEnum;
import com.asopagos.aportes.masivos.dto.ReporteDevolucionDetallado;
import com.asopagos.aportes.masivos.dto.ReporteDevolucionesSimulado;
import com.asopagos.aportes.masivos.dto.ReporteRecaudoSimulado;





@Stateless
public class ConsultasModeloPila implements IConsultasModeloPila, Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ConsultasModeloPila.class);

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "aportes_masivos_pila_PU")
	private EntityManager entityManagerPila;


    @PersistenceContext(unitName = "aportes_masivos_pila_masivos_PU")
	private EntityManager entityManagerMasivos;

    @Override
    public String prueba() {
        logger.info("Inicia prueba()");
        String res = "";
        List<Object[]> prueba = new ArrayList<>();
        try {
            prueba = entityManagerPila
                    .createNamedQuery(NamedQueriesConstants.MASIVO_PRUEBA_PILA)
                    .getResultList();

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en consultarPersonaSolicitanteAporteGeneral: ", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug("Finaliza prueba()");
        if (prueba != null && prueba.size() > 0) {
            res = prueba.get(0)[0].toString();
        }
        
        return res;
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.masivos.business.interfaces.IConsultasModeloPILA#simularAporteMasivo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void simularAporteMasivo(String nombreArchivo) {
		logger.info("Inicia simularAporteMasivo(nombreArchivo)"+nombreArchivo);
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_VALIDACION_INICIAL)
					.setParameter("nombreArchivo", nombreArchivo);
			procedimiento.execute();
			MasivoArchivo archivo = consultarArchivoMasivoPorECM(nombreArchivo);
			
			archivo.setEstado("SIMULADO");
			archivo.setFechaActualizacion(new Date());
			entityManagerMasivos.merge(archivo);
			logger.info("Finaliza simularAporteMasivo(nombreArchivo)");
			// Actualizar estado del archivo masivo a cargado

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.masivos.business.interfaces.IConsultasModeloPILA#finalizarAporteMasivo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> finalizarAporteMasivo(String nombreArchivo) {
		logger.info("Inicia finalizarAporteMasivo(nombreArchivo)"+nombreArchivo);
		List<Object> result = null;
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_FINALIZAR_APORTE_MASIVO)
					.setParameter("nombreArchivo", nombreArchivo);
			logger.info("Finaliza finalizarAporteMasivo(nombreArchivo)");
			procedimiento.execute();
			result = (List<Object>) procedimiento.getResultList();
			return result;
		} catch (Exception e) {
			logger.debug("Finaliza finalizarAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.masivos.business.interfaces.IConsultasModeloPILA#finalizarAporteMasivo(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void crearPersonaAporteMasivo() {
		logger.info("Inicia crearPersonaAporteMasivo()");
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_CREAR_PERSONA);
			
			procedimiento.execute();
			logger.info("Finaliza crearPersonaAporteMasivo()");
		} catch (Exception e) {
			logger.debug("Finaliza finalizarAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}


	@Override
	public MasivoArchivo guardarArchivoMasivo(MasivoArchivo datosArchivo, UserDTO userDTO) {
		MasivoArchivo masivoArchivo = new MasivoArchivo();
		masivoArchivo.setTipoArchivo("APORTE");
		masivoArchivo.setNombreArchivo(datosArchivo.getNombreArchivo());
		masivoArchivo.setNombreOriginalArchivo(datosArchivo.getNombreOriginalArchivo());
		masivoArchivo.setFechaProcesamiento(new Date());
		masivoArchivo.setFechaActualizacion(new Date());
		masivoArchivo.setEstado("CARGADO");
		masivoArchivo.setUsuario(userDTO.getNombreUsuario());
		entityManagerMasivos.persist(masivoArchivo);

		return masivoArchivo;
	}
	@Override
	public void actualizarSolicitudMasiva(MasivoArchivo datosArchivo){
		MasivoArchivo masivoArchivo = datosArchivo;
		entityManagerMasivos.merge(masivoArchivo);

	}

	@Override
	public void guardarTemporalMasivo(ResultadoArchivoAporteDTO resultadoArchivo) {
		try {
			ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(resultadoArchivo);
			MasivoTemporal masivoTemporal = new MasivoTemporal();
			masivoTemporal.setIdMasivoArchivo(resultadoArchivo.getIdArchivoMasivo());
			masivoTemporal.setPayload(jsonPayload);
			entityManagerMasivos.persist(masivoTemporal);
		} catch (Exception e) {
			logger.error("Ocurrio un error radicando una solicitud", e);
		}
	}

	@Override
	public Long consultarArchivoEnProcesoAportes() {
		List<Object> objIdArchivo = entityManagerMasivos
			.createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_EN_PROCESAMIENTO)
			.setParameter("tipoArchivo", "APORTE")
			.getResultList();
		if (objIdArchivo == null) {
			return null;
		}
		if (objIdArchivo.size() == 0) {
			return null;
		}
		return new Long(objIdArchivo.get(0).toString());
	}

	@Override
	public List<MasivoArchivo> consultarArchivoAporte(){
		List<MasivoArchivo> archivoAportes = (List<MasivoArchivo>) entityManagerMasivos
			.createNamedQuery(
				NamedQueriesConstants.CONSULTAR_MASIVO_ARCHIVO_APORTE)
			.setParameter("tipoArchivo", "APORTE").getResultList();
        return archivoAportes;
	}

	

	@Override
	public List<MasivoSimulado> consultarRecaudoSimulado(Long idSolicitud){
	List<MasivoSimulado> masivoSimulado = new ArrayList<MasivoSimulado>();
	masivoSimulado = entityManagerMasivos
                                .createNamedQuery(
                                        NamedQueriesConstants.CONSULTAR_MASIVO_SIMULADO, MasivoSimulado.class)
                                .setParameter("idSolicitud",idSolicitud ).getResultList();
	for(MasivoSimulado aporteSimulado: masivoSimulado){
		MasivoArchivo masivoArchivo = consultarArchivoMasivoPorECM(aporteSimulado.getNombreArchivo());
		//aporteSimulado.setNombreArchivo(masivoArchivo.getNombreOriginalArchivo());
	}
	
	return masivoSimulado;
	}
    
	@Override
	public void procesarArchivoAportes(ResultadoArchivoAporteDTO resultadoArchivo,Long idSolicitud, String numeroRadicado, UserDTO userDTO) {
		logger.info("Inicio metodo procesarArchivoAportes");
		List<ResultadoValidacionAporteDTO> resultadoAportes = resultadoArchivo.getResultadoAportes();
		MasivoArchivo masivoArchivo = resultadoAportes.get(0).toMasivoArchivo(userDTO.getNombreUsuario());
		masivoArchivo.setSolicitud(idSolicitud);
		masivoArchivo.setNumeroRadicacion(numeroRadicado);
		masivoArchivo.setTipoArchivo("APORTE");
			
		entityManagerMasivos.merge(masivoArchivo);
		Long idMasivoArchivo = masivoArchivo.getId();
		logger.info("Fin metodo procesarArchivoAportes");

	}
	@Override
	public MasivoGeneral consultarMasivoGeneral(Long solicitud, String numeroIdentificacion){
		MasivoGeneral masivoGeneral = null;
		masivoGeneral = entityManagerMasivos
                                .createNamedQuery(
                                        NamedQueriesConstants.CONSULTAR_MASIVO_GENERAL, MasivoGeneral.class)
                                .setParameter("idSolicitud",solicitud )
								.setParameter("numeroIdentificacion",numeroIdentificacion ).setMaxResults(1).getSingleResult();
        return masivoGeneral;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistirDetallesArchivoAporte(Long idMasivoArchivo, ResultadoValidacionAporteDTO aporte) {

		logger.info("Inicio metodo persistirDetallesArchivoAporte");
			
		DatosRadicacionMasivaDTO resultadoDatosRadicacion = aporte.getResultadoDatosRadicacion();
		MasivoGeneral masivoGeneral = resultadoDatosRadicacion.toMasivosGeneral(idMasivoArchivo);
		entityManagerMasivos.persist(masivoGeneral);
		Long idMasivoGeneral = masivoGeneral.getId();
		for (ResultadoCotizanteAporteMasivoDTO cotizante : aporte.getResultadoCotizantesMasivos()) {
			MasivoDetallado masivoDetallado = cotizante.toMasivoDetallado(idMasivoGeneral);
			logger.info(masivoDetallado.getTipoCotizante());
			entityManagerMasivos.persist(masivoDetallado);
		}
		
		logger.info("Fin metodo persistirDetallesArchivoAporte");
		
	}
   /**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloPILA#consultarIndicesPlanillaPorIds(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IndicePlanillaModeloDTO> consultarIndicesPlanillaPorIds(List<Long> idsPlanilla) {
		String firmaMetodo = "ConsultasModeloPila.crearTemAporteProcesado(Map<Long, ConsultaPresenciaNovedadesDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		List<IndicePlanillaModeloDTO> result = null;

		try {
			result = entityManagerPila.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_INDICE_PLANILLA_LISTA_ID,
					IndicePlanillaModeloDTO.class).setParameter("idsPlanilla", idsPlanilla).getResultList();

		} catch (NoResultException e) {
			result = Collections.emptyList();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	@Override
	public void procesarArchivoDevolucion(ArchivoDevolucionDTO resultadoArchivo,Long solicitud,String numeroRadicado, UserDTO userDTO) {
		logger.info("Inicio metodo procesarArchivoDevolucion");

		MasivoArchivo masivoArchivo = new MasivoArchivo();
		masivoArchivo.setNombreArchivo(resultadoArchivo.getCargue().getCodigoIdentificacionECM());
		masivoArchivo.setNombreOriginalArchivo(resultadoArchivo.getCargue().getNombreArchivo());
		masivoArchivo.setFechaProcesamiento(new Date());
		masivoArchivo.setFechaActualizacion(new Date());
		masivoArchivo.setUsuario(userDTO.getNombreUsuario());
		masivoArchivo.setNumeroRadicacion(numeroRadicado);
		masivoArchivo.setSolicitud(solicitud);
		masivoArchivo.setEstado("CARGADO");
		masivoArchivo.setTipoArchivo("DEVOLUCION");
		masivoArchivo.setValSubsidio(resultadoArchivo.getIncluirSubsidio());
		masivoArchivo.setMotivoPeticion(resultadoArchivo.getDevolucion().getMotivoPeticion());
		masivoArchivo.setOtroMotivoPeticion(resultadoArchivo.getDevolucion().getOtroMotivo() != null ? resultadoArchivo.getDevolucion().getOtroMotivo() : null);
		masivoArchivo.setDestinatario(resultadoArchivo.getDevolucion().getDestinatarioDevolucion());
		masivoArchivo.setOtroDestinatario(resultadoArchivo.getDevolucion().getOtroDestinatario() != null ? resultadoArchivo.getDevolucion().getOtroDestinatario() : null);
		masivoArchivo.setOtraCaja(resultadoArchivo.getDevolucion().getOtraCaja() != null ? resultadoArchivo.getDevolucion().getOtraCaja() : null);

		logger.info(masivoArchivo);

		entityManagerMasivos.persist(masivoArchivo);
		Long idMasivoArchivo = masivoArchivo.getId();

		for(DatosRadicacionMasivaDTO datosRadicacionAportante: resultadoArchivo.getDatosRadicacionMasiva()){
			MasivoGeneralDevolucion masivoGeneralDevolucion = new MasivoGeneralDevolucion();
			masivoGeneralDevolucion.setIdMasivoArchivo(idMasivoArchivo);
			masivoGeneralDevolucion.setTipoIdentificacionAportante(datosRadicacionAportante.getTipoIdentificacion());
			masivoGeneralDevolucion.setNumeroIdentificacionAportante(datosRadicacionAportante.getNumeroIdentificacion());
			masivoGeneralDevolucion.setRazonSocial(datosRadicacionAportante.getRazonSocialAportante());
			masivoGeneralDevolucion.setPeriodoPago(datosRadicacionAportante.getPeriodoPago());
			masivoGeneralDevolucion.setTipoAportante(datosRadicacionAportante.getTipoAportante());
			entityManagerMasivos.persist(masivoGeneralDevolucion);
		}

		logger.info("Fin metodo procesarArchivoAportes");

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void eliminarArchivoMasivo(Long idArchivoMasivo) {
		String firmaMetodo = "eliminarArchivoMasivo";


		try {
			entityManagerMasivos.createNamedQuery(NamedQueriesConstants.BORRAR_MASIVO_SIMULADO_POR_MASIVO_ARCHIVO)
					.setParameter("idMasivoArchivo", idArchivoMasivo).executeUpdate();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
		}

		try {
			entityManagerMasivos.createNamedQuery(NamedQueriesConstants.BORRAR_MASIVO_DETALLADO_POR_MASIVO_ARCHIVO)
					.setParameter("idMasivoArchivo", idArchivoMasivo).executeUpdate();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
		}

		try {
			entityManagerMasivos.createNamedQuery(NamedQueriesConstants.BORRAR_MASIVO_GENERAL_POR_MASIVO_ARCHIVO)
					.setParameter("idMasivoArchivo", idArchivoMasivo).executeUpdate();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
		}  
		
		try {
			entityManagerMasivos.createNamedQuery(NamedQueriesConstants.BORRAR_MASIVO_ARCHIVO_POR_MASIVO_ARCHIVO)
					.setParameter("idMasivoArchivo", idArchivoMasivo).executeUpdate();
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);

			// En caso de que no se elimine masivo archivo la excepcion se esparce hasta la respuesta de peticion

		}  
	}

	@Override
	public void actualizarArchivoMasivo(Long idArchivomasivo, String estado) {
		MasivoArchivo masivoArchivo = entityManagerMasivos.find(MasivoArchivo.class, idArchivomasivo);
		masivoArchivo.setEstado(estado);
		entityManagerMasivos.merge(masivoArchivo);
	}

	//Sobrecarga de metodo  actualizarArchivoMasivo
	@Override
	public void actualizarArchivoMasivo(Long idArchivoMasivo, MasivoArchivo archivoMasivo) {
		archivoMasivo.setId(idArchivoMasivo);
		entityManagerMasivos.merge(archivoMasivo);
	}

	@Override
	public MasivoArchivo consultarArchivoMasivo(Long idArchivoMasivo){
		MasivoArchivo masivoArchivo = entityManagerMasivos.find(MasivoArchivo.class, idArchivoMasivo);
		return masivoArchivo;
	}

	@Override
	public MasivoArchivo consultarArchivoMasivoPorECM(String ecmIdentificador) {
		List<MasivoArchivo> resultado = entityManagerMasivos.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MASIVO_ARCHIVO_POR_ECM, MasivoArchivo.class
		).setParameter("ecmIdentificador",ecmIdentificador + "%").getResultList();
		if (resultado != null && !resultado.isEmpty()) {
			return resultado.get(0);
		}
		return null;
	}

	@Override
	public MasivoArchivo consultarArchivoMasivoPorRadicado(String numeroRadicado) {
		List<MasivoArchivo> resultado = entityManagerMasivos.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MASIVO_ARCHIVO_POR_RADICADO, MasivoArchivo.class
		)
			.setParameter("numeroRadicado",numeroRadicado)
			.getResultList();
		if (resultado != null && !resultado.isEmpty()) {
			return resultado.get(0);
		}
		return null;
	}


	@Override
	public CorreccionAportanteDTO popularCorreccionAportante(CorreccionAportanteDTO correccionAportanteDTO) {

		List<Object> claseAportante = entityManagerPila.createNamedQuery(
			NamedQueriesConstants.TIPO_CLASE_APORTANTE).setParameter("apgId",correccionAportanteDTO.getIdAporte()).getResultList();
		if(!claseAportante.isEmpty() && (claseAportante.size() == 1)){
			ClaseAportanteEnum clase = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante.get(0).toString());
			correccionAportanteDTO.setClaseAportante(clase);
		}
		return correccionAportanteDTO;
		
	}

	@Override
	public List<AnalisisDevolucionDTO> consultarAportesADevolver(String numeroRadicado) {
		List<Object[]> resultado = new ArrayList<>();
		List<AnalisisDevolucionDTO> analisisDevolucionDTO = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");	
		try {
			resultado = entityManagerMasivos.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_ARCHIVO_APORTES_DEVOLUCION_POR_RADICADO)
			.setParameter("numeroRadicado",numeroRadicado).getResultList();

			for (Object[] resultadDevolucion : resultado) {
				AnalisisDevolucionDTO analisis = new AnalisisDevolucionDTO();
				analisis.setNumOperacion(resultadDevolucion[18] != null ? resultadDevolucion[18].toString(): null);
				analisis.setFechaPago(resultadDevolucion[1] != null ? dateFormat1.parse(resultadDevolucion[1].toString()).getTime(): null);
				analisis.setMetodo(resultadDevolucion[20] != null ? ModalidadRecaudoAporteEnum.valueOf(resultadDevolucion[20].toString()): null);
				analisis.setConDetalle(resultadDevolucion[3] != null &&  resultadDevolucion[3].toString().equals("1") ? Boolean.TRUE: Boolean.FALSE);
				analisis.setNumPlanilla(resultadDevolucion[4] != null ? resultadDevolucion[4].toString(): null);
				analisis.setEstadoArchivo(resultadDevolucion[5] != null ? EstadoProcesoArchivoEnum.valueOf(resultadDevolucion[5].toString()): null);
				analisis.setTipoArchivo(resultadDevolucion[6] != null ? TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(resultadDevolucion[6].toString()): null);
				analisis.setTipoPlanilla(resultadDevolucion[7] != null ? TipoPlanillaEnum.obtenerTipoPlanilla(resultadDevolucion[7].toString()): null);
				analisis.setPeriodo(resultadDevolucion[8] != null ? dateFormat.parse(resultadDevolucion[8].toString()).getTime(): null);
				analisis.setTieneModificaciones(resultadDevolucion[9] != null &&  resultadDevolucion[9].toString().equals("1")? Boolean.TRUE: Boolean.FALSE);
				analisis.setMonto(resultadDevolucion[10] != null ? new BigDecimal(resultadDevolucion[10].toString()): null);
				analisis.setInteres(resultadDevolucion[11] != null ? new BigDecimal(resultadDevolucion[11].toString()): null);
				analisis.setTotal(resultadDevolucion[12] != null ? new BigDecimal(resultadDevolucion[12].toString()): null);	

				analisisDevolucionDTO.add(analisis);
				
			}

		} catch (Exception e) {
			logger.warn("error "+e);
			return null;
		}
		return analisisDevolucionDTO;
	}
	@Override
	public List<AnalisisDevolucionDTO> consultarAportesSimuladosDevolucion(String numeroRadicado) {
		List<Object[]> resultado = new ArrayList<>();
		List<AnalisisDevolucionDTO> analisisDevolucionDTO = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("pila.consultarAportesSimuladoDevolucion: " + numeroRadicado);
		try {
			resultado = entityManagerMasivos.createNamedQuery(NamedQueriesConstants.CONSULTAR_MASIVO_APORTES_SIMULADOS_DEVOLUCION_POR_RADICADO)
			.setParameter("numeroRadicado",numeroRadicado).getResultList();

			for (Object[] resultadDevolucion : resultado) {
				AnalisisDevolucionDTO analisis = new AnalisisDevolucionDTO();
				analisis.setNumOperacion(resultadDevolucion[3] != null ? resultadDevolucion[3].toString(): null);
				analisis.setFechaPago(resultadDevolucion[4] != null ? dateFormat1.parse(resultadDevolucion[4].toString()).getTime(): null);
				analisis.setFecha(resultadDevolucion[4] != null ? dateFormat1.parse(resultadDevolucion[4].toString()).getTime(): null);
				analisis.setMetodo(resultadDevolucion[23] != null ? ModalidadRecaudoAporteEnum.valueOf(resultadDevolucion[23].toString()): null);
				analisis.setConDetalle(resultadDevolucion[6] != null &&  resultadDevolucion[6].toString().equals("1") ? Boolean.TRUE: Boolean.FALSE);
				analisis.setNumPlanilla(resultadDevolucion[7] != null ? resultadDevolucion[7].toString(): null);
				analisis.setEstadoArchivo(resultadDevolucion[8] != null ? EstadoProcesoArchivoEnum.valueOf(resultadDevolucion[8].toString()): null);
				analisis.setTipoArchivo(resultadDevolucion[9] != null ? TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(resultadDevolucion[9].toString()): null);
				analisis.setTipoPlanilla(resultadDevolucion[10] != null ? TipoPlanillaEnum.obtenerTipoPlanilla(resultadDevolucion[10].toString()): null);
				analisis.setPeriodo(resultadDevolucion[11] != null ? dateFormat.parse(resultadDevolucion[11].toString()).getTime(): null);
				analisis.setGestionado(Boolean.TRUE);
				analisis.setResultado(EstadoGestionAporteEnum.APROBADO);
				analisis.setMonto(resultadDevolucion[13] != null ? new BigDecimal(resultadDevolucion[13].toString()): null);
				analisis.setInteres(resultadDevolucion[14] != null ? new BigDecimal(resultadDevolucion[14].toString()): null);
				analisis.setTotal(resultadDevolucion[15] != null ? new BigDecimal(resultadDevolucion[15].toString()): null);	

				analisisDevolucionDTO.add(analisis);
				
			}

		} catch (Exception e) {
			logger.warn("error "+e);
			return null;
		}
		return analisisDevolucionDTO;
	}

	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void consultarDevolucionMasivoGeneral(String idSolicitud) {
		try {
			StoredProcedureQuery procedimientoGeneral = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_CONSULTARAPORTESDEVOLUCIONMASIVOS)
					.setParameter("solicitud", idSolicitud);

			procedimientoGeneral.execute();

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void consultarDevolucionMasivoDetallado(String idSolicitud) {
		try {
			StoredProcedureQuery procedimientoDetallado = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_CONSULTARAPORTESDEVOLUCIONMASIVOSDETALLE)
					.setParameter("numeroRadicado", idSolicitud);

			procedimientoDetallado.execute();
			// Actualizar estado del archivo masivo a cargado

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void simularDevolucionMasivoGeneral(String numeroRadicado) {
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_SIMULARAPORTESDEVOLUCIONMASIVOS)
					.setParameter("numeroRadicado", numeroRadicado);

			procedimiento.execute();
			// Actualizar estado del archivo masivo a cargado

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void crearSolicitudCascadaDevolucion(String numeroRadicado) {
		try {
			StoredProcedureQuery procedimiento = entityManagerPila
					.createNamedStoredProcedureQuery(
							NamedQueriesConstants.ASP_SIMULARAPORTESDEVOLUCIONMASIVOSSOLICITUD)
					.setParameter("numeroRadicado", numeroRadicado);

			procedimiento.execute();
			// Actualizar estado del archivo masivo a cargado

		} catch (Exception e) {
			logger.debug("Finaliza simularAporteMasivo");
			logger.error("Hubo un error en la consulta:", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public List<ReporteDevolucionDetallado> getReporteDevolucionesDetalle(String numeroRadicado) {

		List<ReporteDevolucionDetallado> resultado = entityManagerPila.createNamedQuery(
			NamedQueriesConstants.REPORTE_DEVOLUCION_DETALLE_1, ReporteDevolucionDetallado.class)
			.setParameter("numeroRadicado",numeroRadicado).getResultList();
		return resultado;
	}

	@Override
	public Object[] consultarMontosValoresRecaudoAportesMasivos(String idArchivo, String numeroRadicado) {

		logger.info("consultarMontosValoresRecaudoAportesMasivos: " + idArchivo + " " + numeroRadicado);
		List<Object[]> resultado = entityManagerMasivos.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MONTOS_RECAUDO_SIMULADO)
			.setParameter("numeroRadicado",numeroRadicado)
			.setParameter("nombreArchivo",idArchivo)
			.getResultList();
		if (resultado != null && !resultado.isEmpty()) {
			return resultado.get(0);
		}
		return null;

	}
	@Override
	public List<ReporteRecaudoSimulado> consultarRecaudoSimuladoReporte(Long idSolicitud) {
		logger.info("consultarRecaudoSimuladoReporte: " + idSolicitud);
		return entityManagerMasivos.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MASIVO_SIMULADO_REPORTE, ReporteRecaudoSimulado.class)
			.setParameter("idSolicitud",idSolicitud)
			.getResultList();

	}

	@Override
	public List<ReporteDevolucionesSimulado> consultarDevolucionesSimuladoReporte(String numeroRadicacion) {
		logger.info("consultarDevolucionesSimuladoReporte: " + numeroRadicacion);
		return entityManagerMasivos.createNamedQuery(
			NamedQueriesConstants.CONSULTAR_MASIVO_SIMULADO_DEVOLUCIONES, ReporteDevolucionesSimulado.class)
			.setParameter("numeroRadicado",numeroRadicacion)
			.getResultList();
	}
    
}
