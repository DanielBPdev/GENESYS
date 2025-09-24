package com.asopagos.comunicados.ejb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.afiliados.clients.ConsultarTiposAfiliacionAfiliado;
import com.asopagos.aportes.clients.ConsultarCotizantesVista360Persona;
import com.asopagos.aportes.clients.ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.comunicados.business.interfaces.IConsultasModeloReporte;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.dto.CertificadoDTO;
import com.asopagos.comunicados.service.CertificadosService;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.entidades.ccf.personas.Certificado;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.BuscarPersonas;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 *   con la gestión certificados<br/>
 * <b>Módulo:</b> Asopagos - HU Vistas 360<br/>
 * @author jvelandia
 *
 */
@Stateless
public class CertificadosBusiness implements CertificadosService {

	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "comunicados_PU")
	private EntityManager entityManager;

    /**
     * Inject del EJB para consultas en modelo reportes entityManagerReporte
     */
    @Inject
    private IConsultasModeloReporte consultasReporte;
	
	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(CertificadosBusiness.class);

	@Override
	public CertificadoDTO generarCertificado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Long idComunicado,
			TipoCertificadoEnum tipoCertificado, String dirigidoA, Boolean empleador, TipoAfiliadoEnum tipoAfiliado,
			Long idEmpleador, Short anio, EtiquetaPlantillaComunicadoEnum etiqueta, Long idCertificado, Boolean validaEstadoCartera, String tipoSolicitud) {
		String firma = "generarCertificado(Long, TipoCertificadoEnum, String, Boolean, TipoAfiliadoEnum, Long, Short)";
		CertificadoDTO datosCertificado = null;
		logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
		try {
			Long idPersona = getIdPersona(tipoIdentificacion, numeroIdentificacion);
			logger.error("Encontró a la persona"+idPersona);
			if(idPersona>0L){
				if(etiqueta==null)
				validarGeneracionCertificado(tipoCertificado, idPersona, numeroIdentificacion, tipoIdentificacion,
						tipoAfiliado, idEmpleador, empleador, anio, validaEstadoCartera, tipoSolicitud);
				
				if (tipoAfiliado == null && !empleador && TipoCertificadoEnum.CERTIFICADO_AFILIACION.equals(tipoCertificado))
					tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
				datosCertificado = new CertificadoDTO(idPersona, tipoCertificado, empleador, dirigidoA, anio,
						tipoAfiliado, idEmpleador);
				if(etiqueta!=null)
					datosCertificado.setIdCertificado(idCertificado);
	
				datosCertificado = registrarCertificado(datosCertificado, etiqueta, idComunicado);
			}
			logger.debug(ConstantesComunes.FIN_LOGGER + firma);
			return datosCertificado;
		} catch (FunctionalConstraintException e) {
			throw new FunctionalConstraintException(e);
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firma, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	private Long getIdPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
		BuscarPersonas buscarPersonas = new BuscarPersonas(null, null, null,null, null, null, numeroIdentificacion,
				tipoIdentificacion, null);
		buscarPersonas.execute();
		if(buscarPersonas.getResult()==null){
			return 0L;
		}
		return buscarPersonas.getResult().get(0).getIdPersona();
	}

	private void validarGeneracionCertificado(TipoCertificadoEnum tipoCertificado, Long idPersona,
			String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion, TipoAfiliadoEnum tipoAfiliado,
			Long idEmpleador, Boolean empleador,Short anio, Boolean validaEstadoCartera, String tipoSolicitud) {
		boolean valido = false;
		switch (tipoCertificado) {
		case CERTIFICADO_AFILIACION:
			valido = poseeAfiliacionAprobadaCerrada(idPersona, tipoAfiliado, idEmpleador,false, false, empleador, tipoSolicitud);
			break;
		case CERTIFICADO_HISTORICO_AFILIACIONES:
			valido = estuvoAfiliadoEnCaja(idPersona, empleador);
			break;
		case CERTIFICADO_APORTES_POR_ANIO:
			valido = aporteRecibidoVigente(tipoIdentificacion, numeroIdentificacion, empleador, anio, idPersona);
			break;
		case CERTIFICADO_PAZ_Y_SALVO:
			valido = poseeAfiliacionActiva(idPersona, tipoAfiliado, idEmpleador,true, validaEstadoCartera, empleador, tipoSolicitud);
			break;
		default:
			break;
		}
		if (!valido) {
			throw new FunctionalConstraintException(
					"No se puede generar el certificado ya que no ha cumplido con las validaciones necesarias",
					new Object[0]);
		}
	}

	/**
	 * Método que verifica si una persona posee una afiliacion activa en la caja
	 * 
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de afiliado para cuando es persona, null cuando es
	 *            empleador
	 * @param idEmpleador
	 *            Identificador del empleador para cuando el tipo de afiliado es
	 *            dependiente.
	 * @return Valor indicando si está o no activo
	 */
	@SuppressWarnings("unchecked")
	private boolean poseeAfiliacionActiva(Long idPersona, TipoAfiliadoEnum tipoAfiliado, Long idEmpleador, boolean pazYsalvo, Boolean validaEstadoCartera, boolean empleador, String tipoSolicitud) {
		boolean afiliacionActiva = false;
		try {
			String tipoAfi=null;
			if(tipoAfiliado == null && !empleador && !pazYsalvo)
				tipoAfi="PERSONA_DEPENDIENTE";
			else
				tipoAfi = tipoAfiliado != null ? tipoAfiliado.name() : null;
				logger.error("TipoAfi"+tipoAfi);
			List<String> registros = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACION_ACTIVA_CAJA)
					.setParameter("idPersona", idPersona)
					.setParameter("tipoAfiliado", tipoAfi)
					.setParameter("idEmpleador", idEmpleador).getResultList();
			logger.error("Registros:"+registros);
			if (registros != null && !registros.isEmpty()) {
				for (String estado : registros) {
				    logger.error("Estado:"+estado);
					if (!pazYsalvo) {
						if (estado.equals("ACTIVO")) {
							afiliacionActiva = true;
							break;
						}
					} else {
						if (estado.equals("INACTIVO") || estado.equals("NO_FORMALIZADO_RETIRADO_CON_APORTES")) {
							afiliacionActiva = true;
							logger.error("Ingreso a if inactivo");
							if(validaEstadoCartera){
								afiliacionActiva=estadocartera(idPersona, tipoSolicitud);
								logger.error("estado de cartera"+afiliacionActiva);
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR, e);
		}
		return afiliacionActiva;
	}

	/**
	 * Método que verifica si una persona posee una afiliacion activa en la caja
	 *
	 * @param idPersona
	 *            Identificador de la persona
	 * @param tipoAfiliado
	 *            Tipo de afiliado para cuando es persona, null cuando es
	 *            empleador
	 * @param idEmpleador
	 *            Identificador del empleador para cuando el tipo de afiliado es
	 *            dependiente.
	 * @return Valor indicando si la afiliacion esta aprobada y cerrada
	 */
	@SuppressWarnings("unchecked")
	private boolean poseeAfiliacionAprobadaCerrada(Long idPersona, TipoAfiliadoEnum tipoAfiliado, Long idEmpleador, boolean pazYsalvo, Boolean validaEstadoCartera, boolean empleador, String tipoSolicitud) {
		boolean afiliacionAprobadaCerrada = false;
		try {
			String tipoAfi=null;
			if(tipoAfiliado == null && !empleador && !pazYsalvo)
				tipoAfi="PERSONA_DEPENDIENTE";
			else
				tipoAfi = tipoAfiliado != null ? tipoAfiliado.name() : null;
			logger.error("TipoAfi"+tipoAfi);
			List<String> registros = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACION_CERRADA_CAJA)
					.setParameter("idPersona", idPersona)
					.setParameter("tipoAfiliado", tipoAfi)
					.setParameter("idEmpleador", idEmpleador).getResultList();
			logger.error("Registros:"+registros);
			if (registros != null && !registros.isEmpty()) {
				for (String estado : registros) {
					logger.error("Estado:"+estado);
					if (!pazYsalvo) {
						if (estado.equals("ACTIVO")) {
							afiliacionAprobadaCerrada = true;
							break;
						}
					} else {
						if (estado.equals("INACTIVO") || estado.equals("NO_FORMALIZADO_RETIRADO_CON_APORTES")) {
							afiliacionAprobadaCerrada = true;
							logger.error("Ingreso a if inactivo");
							if(validaEstadoCartera){
								afiliacionAprobadaCerrada=estadocartera(idPersona, tipoSolicitud);
								logger.error("estado de cartera"+afiliacionAprobadaCerrada);
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR, e);
		}
		return afiliacionAprobadaCerrada;
	}
	
	@SuppressWarnings("unchecked")
	private boolean estadocartera(Long idPersona, String tipoSolicitud){
		List<String> estadoCartera = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_CARTERA)
				.setParameter("idPersona", idPersona)
				.setParameter("tipoSolicitud", tipoSolicitud).getResultList();
		logger.error("estadoCArtera"+estadoCartera);
		for (String estado : estadoCartera) {
			if(estado.equals("AL_DIA")){
			    logger.error("estadoCArtera"+estadoCartera);
				return true;
			}
		}
		logger.error("estado cartera invalido");
		return false;
	}


	/**
	 * Método que registra o actualiza un certificado en base de datos.
	 * 
	 * @param certificado
	 *            DTO con los datos del certificado.
	 * @return DTO con los datos registrados del certificado.
	 */
	private CertificadoDTO registrarCertificado(CertificadoDTO certificado, EtiquetaPlantillaComunicadoEnum etiqueta, Long idComunicado) {
		Certificado certificadoEntity=null;
		CertificadoDTO certificadoDto = null;
		if(etiqueta!=null&&certificado.getIdCertificado()!=null){
			Comunicado com;
			//consulta del comunicado para retornar datos faltantes
			if(idComunicado!=null)
				com = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_ETIQUETA_IDSOLICITUD, Comunicado.class)
					.setParameter("etiqueta", etiqueta)
					.setParameter("idComunicado", idComunicado).getSingleResult();
			else
				com = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_COMUNICADO_POR_ETIQUETA_IDCERTIFICADO, Comunicado.class)
					.setParameter("etiqueta", etiqueta)
					.setParameter("idSolicitud", certificado.getIdCertificado()).setMaxResults(1).getSingleResult();
			certificado.setIdComunicado(com.getIdComunicado());
			certificadoEntity = entityManager.merge(certificado.convertToEntity());
			certificadoDto = new CertificadoDTO(certificadoEntity);
			certificadoDto.setEtiqueta(etiqueta);
			certificadoDto.setIdentificadorArchivoCertificado(com.getIdentificadorArchivoComunicado());
			certificadoDto.setCorreoDestinatario(com.getDestinatario());
			return certificadoDto;
		}else
			certificadoEntity = entityManager.merge(certificado.convertToEntity());
		return new CertificadoDTO(certificadoEntity);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.comunicados.service.CertificadosService#consultarCertificados(com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 *      java.lang.String, java.lang.Boolean,
	 *      com.asopagos.enumeraciones.core.TipoCertificadoEnum)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CertificadoDTO> consultarCertificados(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, Boolean empleador, TipoCertificadoEnum tipoCertificado, Long fechaInicio, Long fechaFinal) {
		String firmaMetodo = "consultarCertificados(Long, Boolean, TipoCertificadoEnum)";
		List<CertificadoDTO> responseCer = new ArrayList<>();
		CertificadoDTO objTmp = new CertificadoDTO();
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-M-dd HH:m:ss");
			List<String> tiposDeCertificados = new ArrayList<>();
			if (tipoCertificado != null) {
				tiposDeCertificados.add(tipoCertificado.name());
			} else { // si el tipo llega null quere decir que se consultan todos
				for (TipoCertificadoEnum tipoCertificadoEnum : TipoCertificadoEnum.values()) {
					tiposDeCertificados.add(tipoCertificadoEnum.name());
				}
			}
			Long idPersona = getIdPersona(tipoIdentificacion, numeroIdentificacion);
			List<Object[]> certificados = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_CERTIFICADO_EMPLEADOR_PERSONA)
					.setParameter("idPersona", idPersona).setParameter("empleador", empleador)
					.setParameter("tipoCertificado", tiposDeCertificados).getResultList();
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			for (Object[] objects : certificados) {
				Date d = f.parse(objects[2].toString());
				objTmp = new CertificadoDTO();
				objTmp.setIdCertificado(Long.valueOf(objects[0].toString()));
				objTmp.setTipoCertificado(TipoCertificadoEnum.valueOf(objects[1].toString()));
				objTmp.setIdentificadorArchivoCertificado(objects[4]!=null?objects[4].toString():"");
				objTmp.setCorreoDestinatario(objects[3]!=null?objects[3].toString():"");
				objTmp.setFechaGeneracion(d.getTime());
				objTmp.setEtiqueta(objects[5]!=null?EtiquetaPlantillaComunicadoEnum.valueOf(objects[5].toString()):null);
				objTmp.setIdComunicado(objects[6]!=null?Long.valueOf(objects[6].toString()):null);
				objTmp.setDirigidoA(objects[7].toString());
				responseCer.add(objTmp);
			}
			return filtrarCriterioBusqueda(responseCer, fechaInicio, fechaFinal);
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	@Override
	public void registrarEnvioCertificado(Long idCertificado, Long idComunicado) {
		String firmaMetodo = "registrarEnvioCertificado(Long, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		try {
			CertificadoDTO certificadoDTO = consultarCertificadoPorId(idCertificado);
			certificadoDTO.setIdComunicado(idComunicado);
			registrarCertificado(certificadoDTO,null, null);
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return;
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}

	/**
	 * Método que consulta el certificado por el id
	 * 
	 * @param idCertificado
	 *            Identificador del certificado
	 * @return el dto del certificado
	 */
	private CertificadoDTO consultarCertificadoPorId(Long idCertificado) {
		String firmaMetodo = "consultarCertificadoPorId(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		try {
			Certificado certificado = entityManager.find(Certificado.class, idCertificado);
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return new CertificadoDTO(certificado);
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	}
    
    private List<CertificadoDTO> filtrarCriterioBusqueda(List<CertificadoDTO> certificado, Long fechaInicio, Long fechaFinal){
    	logger.debug("Fecha Inicio "+ fechaInicio+ " Fecha Final "+ fechaFinal);
		List<CertificadoDTO> arrayRequestTmp = new ArrayList<>();
		if(fechaInicio!=null && fechaFinal!=null){
			arrayRequestTmp = certificado.stream().
					filter(cer->cer.getFechaGeneracion()>=fechaInicio && cer.getFechaGeneracion()<=fechaFinal
					).collect(Collectors.toList());
		}else if(fechaInicio==null && fechaFinal!=null){
			arrayRequestTmp = certificado.stream().
					filter(cer->cer.getFechaGeneracion()<=fechaFinal
					).collect(Collectors.toList());
		}else if(fechaInicio!=null && fechaFinal==null){
			arrayRequestTmp = certificado.stream().
					filter(cer->cer.getFechaGeneracion()>=fechaInicio
					).collect(Collectors.toList());
		}else {
			arrayRequestTmp = certificado;
		}
		
		return arrayRequestTmp;
	}

	@Override
	public void actualizarCertificado(EtiquetaPlantillaComunicadoEnum etiqueta, Long idCertificado) {
		// TODO Auto-generated method stub
		CertificadoDTO certificadoDTO = consultarCertificadoPorId(idCertificado);
		if(certificadoDTO.getIdComunicado()==null){
			PersonaModeloDTO per = null;
			ConsultarPersona buscarPersonas = new ConsultarPersona(certificadoDTO.getIdPersona());
			buscarPersonas.execute();
			per = buscarPersonas.getResult();
			if(per!=null)
				certificadoDTO = generarCertificado(per.getTipoIdentificacion(), per.getNumeroIdentificacion(), null, certificadoDTO.getTipoCertificado(), certificadoDTO.getDirigidoA(), certificadoDTO.getGeneradoComoEmpleador(), certificadoDTO.getTipoAfiliado(), certificadoDTO.getIdEmpleador(), certificadoDTO.getAnio(), etiqueta, certificadoDTO.getIdCertificado(), false, null);
		}
	}
	
	public boolean estuvoAfiliadoEnCaja(Long idPersona, Boolean empleador) {
		String firmaMetodo = "haTenidoAfiliacion(Long, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		boolean haTenidoAfiliacion = false;
		try {
			List<Object[]> afiliaciones = null;
			
			if(empleador){
				afiliaciones = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_EMPRESA_CORE)
						.setParameter("idPersona", idPersona.toString())
						.getResultList();
			}
			else{
				afiliaciones = (List<Object[]>) entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_ESTADOS_PERSONA_CORE)
						.setParameter("idPersona", idPersona.toString())
						.getResultList();
			}

			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			for (Object[] objects : afiliaciones) {
				if (objects[0].equals("ACTIVO") || objects[0].equals("NO_FORMALIZADO_RETIRADO_CON_APORTES") || objects[0].equals("INACTIVO")) {
					haTenidoAfiliacion = true;
					break;
				}
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			return false;
		}
		return haTenidoAfiliacion;
	}
	
	/**
	 * Valida si se cumplen los requisitos para la generación del certificado de aportes por año
	 * de acuerdo a la entidad (Empresa o Persona)
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param empleador
	 * @param anio
	 * @param idPersona
	 * @return
	 */
	public boolean aporteRecibidoVigente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            Boolean empleador, Short anio, Long idPersona){
        boolean estadoVigente=false;
        ConsultarRecaudoDTO recaudo = new ConsultarRecaudoDTO();
        List<TipoAfiliadoEnum> afiliaciones = new ArrayList<>();
        List<Long> cotizantesRslt = new ArrayList<>();
        List<Object[]> certificados;
        String query;
        if(empleador){
            query=NamedQueriesConstants.CONSULTA_CERTIFICADO_APORTANTE_EMPLEADOR;
            certificados = entityManager
                    .createNamedQuery(query)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .setParameter("anio", anio).getResultList();
            for (Object[] objects : certificados) {
                if (objects[0].equals("VIGENTE")) {
                    estadoVigente = true;
                    break;
                }
            }
        }else{
            ConsultarTiposAfiliacionAfiliado afiliacionesSrv = new ConsultarTiposAfiliacionAfiliado(numeroIdentificacion, tipoIdentificacion);
            afiliacionesSrv.execute();
            afiliaciones = afiliacionesSrv.getResult();
            
            ConsultarCotizantesVista360Persona cotizantesSrv = new ConsultarCotizantesVista360Persona(idPersona);
            cotizantesSrv.execute();
            cotizantesRslt = cotizantesSrv.getResult();
            
            Calendar  fechaInicio = new GregorianCalendar(anio,0,1);
            Calendar fechaFin = new GregorianCalendar(anio,11,31); 
            recaudo.setFechaInicio(fechaInicio.getTimeInMillis());
            recaudo.setFechaFin(fechaFin.getTimeInMillis());
            recaudo.setTipoIdentificacion(tipoIdentificacion);
            recaudo.setNumeroIdentificacion(numeroIdentificacion);
            recaudo.setListaIdsAporteGeneral(cotizantesRslt);
            
            estadoVigente = vigente(recaudo, afiliaciones);
            
        }
        
        return estadoVigente;
    }

    /**
     * Valida si la persona tiene al menos un aporte vigente
     * @param recaudo
     * @param afiliaciones
     */
    private boolean vigente(ConsultarRecaudoDTO recaudo, List<TipoAfiliadoEnum> afiliaciones) {
        for (TipoAfiliadoEnum tipoAfiliado : afiliaciones) {
            switch (tipoAfiliado) {
                case TRABAJADOR_DEPENDIENTE:
                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
                    break;
                case TRABAJADOR_INDEPENDIENTE:
                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE);
                    break;
                case PENSIONADO:
                    recaudo.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
                    break;
            }
            ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal aportesSrv = new ConsultarRecaudoDevolucionCorreccionVista360PersonaPrincipal(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL, recaudo);
            aportesSrv.execute();
            List<CuentaAporteDTO> aportesRslt = aportesSrv.getResult();
            
            for (CuentaAporteDTO cuentaAporteDTO : aportesRslt) {
                if (EstadoAporteEnum.VIGENTE.equals(cuentaAporteDTO.getEstadoAporte())){
                    return true;
                }
            }
        }
        return false;
    }
	
	
}
