package com.asopagos.cartera.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.asopagos.cartera.constants.NamedQueriesConstants;
import com.asopagos.cartera.dto.DetalleIntegracionCarteraDTO;
import com.asopagos.cartera.dto.IntegracionCarteraDTO;
import com.asopagos.cartera.service.IntegracionService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoConvenioPagoEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import com.asopagos.entidades.ccf.core.AuditoriaIntegracionServicios;
import com.asopagos.util.AuditoriaIntegracionInterceptor;
import javax.persistence.PersistenceContext;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> EJB que representa los métodos de negocio relacionados a
 * los servicios de integración de cartera <br/>
 * <b>Historia de Usuario: </b> Portafolio de servicios - Integración cartera
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@Stateless
@SuppressWarnings("unchecked")
public class IntegracionBusiness implements IntegracionService {

	/**
	 * Referencia a la unidad de persistencia del servicio
	 */
	@PersistenceContext(unitName = "cartera_PU")
	private EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private final ILogger logger = LogManager.getLogger(IntegracionBusiness.class);

	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerEstadoCarteraOld(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public IntegracionCarteraDTO obtenerEstadoCarteraOld(TipoSolicitanteMovimientoAporteEnum tipoAportante, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		try {
			logger.debug("Inicia el servicio obtenerEstadoCarteraOld");
			IntegracionCarteraDTO resultado = new IntegracionCarteraDTO();
			List<Object[]> lista =
					entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARTERA_INTEGRACION).setParameter("tipoAportante", tipoAportante.name()).setParameter("tipoIdentificacion", tipoIdentificacion.name()).setParameter("numeroIdentificacion", numeroIdentificacion).getResultList();

			if (!lista.isEmpty()) {
				Object[] registro = lista.get(0);
				resultado.setTipoAportante(tipoAportante);
				resultado.setTipoIdentificacion(tipoIdentificacion);
				resultado.setNumeroIdentificacion(numeroIdentificacion);
				resultado.setRazonSocial(registro[0].toString());
				resultado.setDeudaPresunta(new BigDecimal(registro[1].toString()));
				resultado.setEstadoCartera(EstadoCarteraEnum.valueOf(registro[2].toString()));
				resultado.setPeriodoEnMora(registro[3].toString());
				resultado.setMesesEnMora(Integer.valueOf(registro[4].toString()));
			}

			logger.debug("Finaliza el servicio obtenerEstadoCarteraOld");
			return resultado;
		} catch (Exception e) {
			logger.error("Error en el servicio obtenerEstadoCarteraOld", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerEstadoCarteraDetalleOld(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response obtenerEstadoCarteraDetalleOld(TipoSolicitanteMovimientoAporteEnum tipoAportante, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, HttpServletRequest requestContext, UserDTO userDTO) {
			logger.debug("Inicia el servicio obtenerEstadoCarteraDetalleOld");
			String firmaServicio = "IntegracionBussiones.obtenerEstadoCarteraDetalleOld(TipoIdentificacionEnum, String)";
			Instant start = Instant.now();
			HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
			parametrosMetodo.put("tipoAportante", tipoAportante.name());
			parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
			parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
			String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
			List<IntegracionCarteraDTO> listaDTO = null;
			AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
			try{
			listaDTO = new ArrayList<IntegracionCarteraDTO>();
			List<Object[]> lista =
					entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARTERA_DETALLE_INTEGRACION)
								.setParameter("tipoAportante", tipoAportante.name())
								.setParameter("tipoIdentificacion", tipoIdentificacion.name())
								.setParameter("numeroIdentificacion", numeroIdentificacion)
								.getResultList();

			for (Object[] registro : lista) {
				IntegracionCarteraDTO registroDTO = new IntegracionCarteraDTO();
				registroDTO.setTipoAportante(tipoAportante);
				registroDTO.setTipoIdentificacion(tipoIdentificacion);
				registroDTO.setNumeroIdentificacion(numeroIdentificacion);
				registroDTO.setRazonSocial(registro[0].toString());
				registroDTO.setDeudaPresunta(new BigDecimal(registro[1].toString()));
				registroDTO.setEstadoCartera(EstadoCarteraEnum.valueOf(registro[2].toString()));
				registroDTO.setPeriodoEnMora(registro[3].toString());
				registroDTO.setLineaDeCobro(TipoLineaCobroEnum.valueOf(registro[4].toString()));
				registroDTO.setMetodoDeCobro(registro[5] != null ? MetodoAccionCobroEnum.valueOf(registro[5].toString()) : null);
				registroDTO.setTipoAccionDeCobro(registro[6] != null ? TipoAccionCobroEnum.valueOf(registro[6].toString()) : null);
				registroDTO.setTipoDeDeuda(TipoDeudaEnum.valueOf(registro[7].toString()));
				listaDTO.add(registroDTO);
			}

			logger.debug("Finaliza el servicio obtenerEstadoCarteraDetalleOld");
			//return listaDTO;

		     }catch (Exception e) {
				logger.error("Por aqui catera",e);
				return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,listaDTO,entityManager,auditoriaIntegracionServicios);
			 }
			   return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,listaDTO,entityManager,auditoriaIntegracionServicios);
		}

	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerEstadoCarteraConvenioPagoOld(com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response obtenerEstadoCarteraConvenioPagoOld(TipoSolicitanteMovimientoAporteEnum tipoAportante, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, HttpServletRequest requestContext, UserDTO userDTO) {
	    String firmaServicio = "IntegracionBussiones.obtenerEstadoCarteraConvenioPagoOld(TipoIdentificacionEnum, String)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("tipoAportante", tipoAportante.name());
        parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
        parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
		IntegracionCarteraDTO resultado = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try {
			logger.debug("Inicia el servicio obtenerEstadoCarteraConvenioPagoOld");
			resultado = new IntegracionCarteraDTO();
			List<Object[]> lista =
					entityManager
							.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARTERA_CONVENIO_PAGO_INTEGRACION)
								.setParameter("tipoAportante", tipoAportante.name())
								.setParameter("tipoIdentificacion", tipoIdentificacion.name())
								.setParameter("numeroIdentificacion", numeroIdentificacion)
								.getResultList();

			if (!lista.isEmpty()) {
				Object[] registro = lista.get(0);
				resultado.setTipoAportante(tipoAportante);
				resultado.setTipoIdentificacion(tipoIdentificacion);
				resultado.setNumeroIdentificacion(numeroIdentificacion);
				resultado.setRazonSocial(registro[0].toString());
				resultado.setDeudaPresunta(registro[1] != null ? new BigDecimal(registro[1].toString()) : null);
				resultado.setEstadoCartera(registro[2] != null ? EstadoCarteraEnum.valueOf(registro[2].toString()) : null);
				resultado.setPeriodoEnMora(registro[3] != null ? registro[3].toString() : null);
				resultado.setNumeroConvenioPago(registro[4] != null ? Long.parseLong(registro[4].toString()) : null);
				resultado.setEstadoConvenioPago(registro[5] != null ? EstadoConvenioPagoEnum.valueOf(registro[5].toString()) : null);
				resultado.setFechaLimitePago(registro[6] != null ? registro[6].toString() : null);
				resultado.setFechaRegistroConvenioPago(registro[7] != null ? registro[7].toString() : null);
			}

			logger.debug("Finaliza el servicio obtenerEstadoCarteraConvenioPagoOld");
			//return resultado;
		} catch (Exception e) {
		   logger.error("Por aqui catera",e);
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,resultado,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,resultado,entityManager,auditoriaIntegracionServicios);
	}

	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerConvenioPagoOld(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response obtenerConvenioPagoOld(Long numeroConvenio, HttpServletRequest requestContext,UserDTO userDTO) {
		String firmaServicio = "IntegracionBussiones.obtenerConvenioPagoOld(Long)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("numeroConvenio", String.valueOf(numeroConvenio));
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
		IntegracionCarteraDTO resultado = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try {
			logger.debug("Inicia el servicio obtenerConvenioPagoOld");
		     resultado = new IntegracionCarteraDTO();
			List<Object[]> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_PAGO_INTEGRACION).setParameter("idConvenio", numeroConvenio).getResultList();

			if (!lista.isEmpty()) {
				Object[] registro = lista.get(0);
				resultado.setNumeroConvenioPago(numeroConvenio);
				resultado.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf(registro[0].toString()));
				resultado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[1].toString()));
				resultado.setNumeroIdentificacion(registro[2].toString());
				resultado.setRazonSocial(registro[3].toString());
				resultado.setDeudaPresunta(registro[4] != null ? new BigDecimal(registro[4].toString()) : null);
				resultado.setEstadoCartera(registro[5] != null ? EstadoCarteraEnum.valueOf(registro[5].toString()) : null);
				resultado.setPeriodoEnMora(registro[6] != null ? registro[6].toString() : null);
				resultado.setEstadoConvenioPago(EstadoConvenioPagoEnum.valueOf(registro[7].toString()));
				resultado.setFechaLimitePago(registro[8].toString());
				resultado.setFechaRegistroConvenioPago(registro[9].toString());
			}

			logger.debug("Finaliza el servicio obtenerConvenioPagoOld");
			//return resultado;
		} catch (Exception e) {
		   logger.error("Por aqui catera",e);
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,resultado,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,resultado,entityManager,auditoriaIntegracionServicios);
	}


	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerEstadoCartera(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IntegracionCarteraDTO> obtenerEstadoCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacionAportante, String numeroIdentificacionDependiente, String periodoConsulta) {		
		try {		
			logger.debug("Inicia el servicio obtenerEstadoCartera");
			List<IntegracionCarteraDTO> listaAportantes = new ArrayList<IntegracionCarteraDTO>();
			List<IntegracionCarteraDTO> listaResultados = new ArrayList<IntegracionCarteraDTO>();
			
			if(tipoIdentificacion == null){
				throw new ParametroInvalidoExcepcion("El tipo de identificación es requerido");
			}
			
			if(numeroIdentificacionAportante == null && numeroIdentificacionDependiente == null){
				throw new ParametroInvalidoExcepcion("El número de identificación (aportante o cotizante) es requerido");
			}
			
			if(periodoConsulta == null){
				periodoConsulta = new SimpleDateFormat("yyyy-MM").format(new Date());
			}
			
			if(numeroIdentificacionAportante != null){ // Es aportante. Se agregan los datos a la lista de aportantes a ser consultados en cartera
				numeroIdentificacionDependiente = null;
				IntegracionCarteraDTO entrada = new IntegracionCarteraDTO();
				entrada.setTipoIdentificacion(tipoIdentificacion);
				entrada.setNumeroIdentificacion(numeroIdentificacionAportante);
				listaAportantes.add(entrada);
			} else{ // Es dependiente. Se realiza la búsqueda de aportantes asociados al dependiente				 
				List<Object[]> lista = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_COTIZANTE_APORTANTE)
						.setParameter("tipoIdentificacion", tipoIdentificacion.name())
						.setParameter("numeroIdentificacionDependiente", numeroIdentificacionDependiente)
						.getResultList();
				
				for(Object[] registro : lista){
					IntegracionCarteraDTO entrada = new IntegracionCarteraDTO();
					entrada.setTipoIdentificacion(registro[0]!=null ? TipoIdentificacionEnum.valueOf(registro[0].toString()) : null);
					entrada.setNumeroIdentificacion(registro[1]!=null ? registro[1].toString() : null);
					listaAportantes.add(entrada);
				}
			}
			
			// Se realiza la búsqueda del estado de cartera para los aportantes de la lista
			for(IntegracionCarteraDTO aportante : listaAportantes){
				List<Object[]> lista = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_ESTADO_CARTERA_INTEGRACION)
						.setParameter("tipoIdentificacion", aportante.getTipoIdentificacion().name())
						.setParameter("numeroIdentificacionAportante", aportante.getNumeroIdentificacion())
						.setParameter("periodoConsulta", periodoConsulta)
						.getResultList();
				IntegracionCarteraDTO resultado = new IntegracionCarteraDTO();

				if (!lista.isEmpty()) {
					Object[] datosGenerales = lista.get(0);
					resultado.setTipoAportante(datosGenerales[0]!=null ? TipoSolicitanteMovimientoAporteEnum.valueOf(datosGenerales[0].toString()) : null);
					resultado.setTipoIdentificacion(datosGenerales[1]!=null ? TipoIdentificacionEnum.valueOf(datosGenerales[1].toString()) : null);
					resultado.setNumeroIdentificacion(datosGenerales[2]!=null ? datosGenerales[2].toString() : null);
					resultado.setRazonSocial(datosGenerales[3]!=null ? datosGenerales[3].toString() : null);				
					resultado.setFechaInicioCartera(datosGenerales[4]!=null ? datosGenerales[4].toString() : null);
					resultado.setMesesEnMora(lista.size());
					List<DetalleIntegracionCarteraDTO> listaDetalle = new ArrayList<DetalleIntegracionCarteraDTO>();
					
					// Se agrega el detalle (arreglo) de periodos en mora 
					for(Object[] registro : lista){
						DetalleIntegracionCarteraDTO detalle = new DetalleIntegracionCarteraDTO();
						detalle.setDeudaPresunta(registro[5]!=null ? new BigDecimal(registro[5].toString()) : null);
						detalle.setEstadoCartera(registro[6]!=null ? EstadoCarteraEnum.valueOf(registro[6].toString()) : null);
						detalle.setPeriodoEnMora(registro[7]!=null ? registro[7].toString() : null);
						listaDetalle.add(detalle);
					}
					
					resultado.setPeriodosMora(listaDetalle);
					listaResultados.add(resultado);
				}				
			}

			logger.debug("Finaliza el servicio obtenerEstadoCartera");
			return listaResultados;
		} catch(ParametroInvalidoExcepcion e) {
			logger.error(e.getMessage());
			throw new ParametroInvalidoExcepcion(e.getMessage());
		} catch(Exception e){
			logger.error("Error en el servicio obtenerEstadoCartera", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.asopagos.cartera.service.IntegracionService#obtenerEstadoCarteraConvenioPago(java.lang.Long, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response obtenerEstadoCarteraConvenioPago(Long numeroConvenioPago, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, HttpServletRequest requestContext,UserDTO userDTO) {
		String firmaServicio = "IntegracionBussiones.obtenerEstadoCarteraConvenioPago(Long)";
        Instant start = Instant.now();
        HashMap<String, String> parametrosMetodo = new HashMap<String,String>();
        parametrosMetodo.put("numeroConvenioPago", String.valueOf(numeroConvenioPago));
		parametrosMetodo.put("tipoIdentificacion", tipoIdentificacion.name());
		parametrosMetodo.put("numeroIdentificacion", numeroIdentificacion);
        String parametrosIn = AuditoriaIntegracionInterceptor.convertParametrosToJsonString(parametrosMetodo);
		List<IntegracionCarteraDTO> listaResultados = null;
        AuditoriaIntegracionServicios auditoriaIntegracionServicios = AuditoriaIntegracionServicios.integracionServicios(requestContext.getRemoteAddr(),firmaServicio,parametrosIn,userDTO.getEmail());
		try {
			logger.debug("Inicia el servicio obtenerEstadoCarteraConvenioPago");
		    List<Long> listaConvenios = new ArrayList<Long>();
		    listaResultados = new ArrayList<IntegracionCarteraDTO>();
			
			if(numeroConvenioPago == null && (tipoIdentificacion == null || numeroIdentificacion == null)){
				throw new ParametroInvalidoExcepcion("El número del convenio o tipo/número de identificación del aportante es requerido");
			}
			
			if(numeroConvenioPago != null){
				listaConvenios.add(numeroConvenioPago);
			} else{
				List<Object> lista = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_CONVENIO_APORTANTE)
						.setParameter("tipoIdentificacion", tipoIdentificacion.name())
						.setParameter("numeroIdentificacion", numeroIdentificacion)
						.getResultList();
				
				for(Object registro : lista){
					listaConvenios.add(Long.parseLong(registro.toString()));
				}
			}
			
			for(Long idConvenio : listaConvenios){
				IntegracionCarteraDTO resultado = new IntegracionCarteraDTO();
				List<Object[]> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONVENIO_PAGO_INTEGRACION)
						.setParameter("idConvenio", idConvenio)
						.getResultList();
	
				if (!lista.isEmpty()) {
					Object[] registro = lista.get(0);
					resultado.setNumeroConvenioPago(idConvenio);
					resultado.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf(registro[0].toString()));
					resultado.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(registro[1].toString()));
					resultado.setNumeroIdentificacion(registro[2].toString());
					resultado.setRazonSocial(registro[3].toString());
					resultado.setDeudaPresunta(registro[4] != null ? new BigDecimal(registro[4].toString()) : null);
					resultado.setEstadoCartera(registro[5] != null ? EstadoCarteraEnum.valueOf(registro[5].toString()) : null);
					resultado.setPeriodoEnMora(registro[6] != null ? registro[6].toString() : null);
					resultado.setEstadoConvenioPago(EstadoConvenioPagoEnum.valueOf(registro[7].toString()));
					resultado.setFechaLimitePago(registro[8].toString());
					resultado.setFechaRegistroConvenioPago(registro[9].toString());
				}
				
				listaResultados.add(resultado);
			}

			logger.debug("Finaliza el servicio obtenerEstadoCarteraConvenioPago");
			//return listaResultados;
		} catch (Exception e) {
		   logger.error("Por aqui catera",e);
           return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,e,listaResultados,entityManager,auditoriaIntegracionServicios);
        }
          return AuditoriaIntegracionInterceptor.integracionServiciosErroresyExitosos( start,null,listaResultados,entityManager,auditoriaIntegracionServicios);
	}
}

