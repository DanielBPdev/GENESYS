package com.asopagos.aportes.composite.service.business.ejb;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.asopagos.log.LogManager;
import com.asopagos.log.ILogger;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;

import com.asopagos.aportes.composite.service.business.interfaces.IConsultasModeloCoreComposite;

import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.EstadosUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;

//


import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.util.EstadosUtils;
import com.asopagos.aportes.composite.service.constants.NamedQueriesConstants;
import com.asopagos.entidades.ccf.personas.RolAfiliado;


/**lllllllllllllllllllllllllllllllllllllllllllll */
import com.asopagos.afiliaciones.clients.ActualizarEstadoDocumentacionAfiliacion;
import com.asopagos.afiliaciones.clients.BuscarSolicitud;
import com.asopagos.afiliaciones.clients.BuscarSolicitudPorId;
import com.asopagos.afiliaciones.clients.ConsultarProductosNoConforme;
import com.asopagos.afiliaciones.clients.GuardarInstanciaProceso;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliaciones.clients.RegistrarIntentoAfliliacion;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
//import com.asopagos.afiliaciones.personas.clients.ActualizarEstadoSolicitudAfiliacionPersona;
//import com.asopagos.afiliaciones.personas.clients.ActualizarSolicitudAfiliacionPersona;
//import com.asopagos.afiliaciones.personas.clients.ConsultarSolicitudAfiliacionPersona;
//import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAfiliacionPersona;
//import com.asopagos.afiliaciones.personas.clients.CrearSolicitudAsociacionPersonaEntidadPagadora;
//import com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO;
//import com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService;
import com.asopagos.afiliados.clients.ActualizarAfiliado;
import com.asopagos.afiliados.clients.ActualizarEstadoBeneficiario;
import com.asopagos.afiliados.clients.ActualizarEstadoRolAfiliado;
import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.AsociarBeneficiarioAGrupoFamiliar;
import com.asopagos.afiliados.clients.BuscarAfiliados;
import com.asopagos.afiliados.clients.CalcularCategoriasAfiliado;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarBeneficiarios;
import com.asopagos.afiliados.clients.ConsultarGrupoFamiliar;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.afiliados.clients.CrearAfiliado;
import com.asopagos.afiliados.clients.GuardarDatosIdentificacionYUbicacion;
import com.asopagos.afiliados.clients.GuardarInformacionLaboral;
import com.asopagos.afiliados.clients.RegistrarBeneficiario;
import com.asopagos.afiliados.clients.RegistrarInformacionBeneficiarioConyugue;
import com.asopagos.afiliados.clients.RegistrarInformacionBeneficiarioHijoPadre;
import com.asopagos.afiliados.clients.RegistrarPersonasBeneficiariosAbreviada;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliarBeneficiarioDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.dto.BeneficiarioHijoPadreDTO;
import com.asopagos.dto.CategoriaDTO;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.dto.GrupoFamiliarDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ProductoNoConformeDTO;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.dto.ResultadoGeneralProductoNoConformeBeneficiarioDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.VerificarProductoNoConformeDTO;
import com.asopagos.dto.VerificarRequisitosDocumentalesDTO;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.empleadores.clients.ConsultarDatosTemporalesEmpleador;
import com.asopagos.empleadores.clients.ConsultarEmpleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
//import com.asopagos.entidades.pagadoras.clients.ActualizarGestionSolicitudesAsociacion;
//import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudPersonaEntidadPagadoraEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGestionProductoNoConformeSubsanableEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
//import com.asopagos.listaschequeo.clients.GuardarListaChequeo;

import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarRetiroTrabajadores;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAutomaticaSinValidaciones;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.exception.ValidacionFallidaException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.validaciones.clients.ValidarPersonas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.personas.Persona;
//import com.asopagos.rutine.afiliacionpersonasrutines.actualizarsolicitudafiliacionpersona.ActualizarSolicitudAfiliacionPersonaRutine;
//import com.asopagos.rutine.afiliacionpersonasrutines.actualizarsolicitudafiliacionpersona.ActualizarSolicitudAfiliacionPersonaRutine;
/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Stateless
public class ConsultasModeloCoreComposite implements IConsultasModeloCoreComposite, Serializable {

	/**
	 * Constantes para nombramiento de parámetros de consulta
	 */

	private static final long serialVersionUID = 1L;
    private static final String NUMERO_RADICADO_SOLICITUD="numeroRadicado";
	/**
	 * Referencia al logger
	 */

	/**
	 * Entity Manager
	 */
	@PersistenceContext(unitName = "core_PU_APORTE")
	private EntityManager entityManager;
        
        private static final ILogger logger = LogManager.getLogger(ConsultasModeloCoreComposite.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#crearSolicitudAporte(com.asopagos.dto.modelo.SolicitudAporteModeloDTO)
	 */

	//@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Long> registrarAportesDetallados(List<JuegoAporteMovimientoDTO> aportesDetallados) {
		String firmaMetodo = "ConsultasModeloCore.registrarAportesDetallados(List<AporteDetalladoModeloDTO>)";
		System.out.println("**__**aportesDetallados>>:llll " + aportesDetallados.size());
		List<Long> result = new ArrayList<>();
		
		AporteDetallado aporteDetallado = null;
		MovimientoAporte movimientoAporte = null;
		for (JuegoAporteMovimientoDTO juegoAporteMovimiento : aportesDetallados) {
			aporteDetallado = juegoAporteMovimiento.getAporteDetallado().convertToEntity();
			aporteDetallado.setFechaCreacion(new Date());
			aporteDetallado.setMarcaCalculoCategoria(true);
			movimientoAporte = juegoAporteMovimiento.getMovimientoAporte().convertToEntity();
			
			entityManager.persist(aporteDetallado);
			
			movimientoAporte.setIdAporteGeneral(aporteDetallado.getIdAporteGeneral());
			movimientoAporte.setIdAporteDetallado(aporteDetallado.getId());
			movimientoAporte.setFechaCreacion(new Date());
			entityManager.persist(movimientoAporte);
			
			result.add(aporteDetallado.getIdRegistroDetallado());
			System.out.println("**__**aporteDetallado.getIdRegistroDetallado()"+aporteDetallado.getIdRegistroDetallado());
			//System.out.println(aporteDetallado.toString());
		}

		return result;
	}
	//new update rol afiliado
		//@Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateRolAfiliadoAportes(RolAfiliado rolAfiliado,Boolean update) {
		String firmaMetodo = "ConsultasModeloCore.registrarAportesDetallados(List<AporteDetalladoModeloDTO>)";
		System.out.println("**__**updateRolAfiliado>>c: " + update);
			RolAfiliado rolAfiliado2 = new RolAfiliado();
			rolAfiliado2=rolAfiliado;
		//RolAfiliado rolAfiliado = rolAfiliadoDTO.convertToEntity();
				System.out.println("**__** idRolAfiliado BBBB:  "+rolAfiliado2.getIdRolAfiliado());
		System.out.println("**__**rolAfiliado2.getSucursalEmpleador(): " + rolAfiliado2.getSucursalEmpleador());
		System.out.println("**__**rolAfiliado2.getEmpleador(): " + rolAfiliado2.getEmpleador());
		System.out.println("**__**rolAfiliado2.getAfiliado(): " + rolAfiliado2.getAfiliado());
		System.out.println("**__**rolAfiliado2.getTipoAfiliado(): " + rolAfiliado2.getTipoAfiliado());
		System.out.println("**__**rolAfiliado2.getEstadoAfiliado(): " + rolAfiliado2.getEstadoAfiliado());
		System.out.println("**__**rolAfiliado2.getCanalReingreso(): " + rolAfiliado2.getCanalReingreso());
		System.out.println("**__**rolAfiliado2.getReferenciaAporteReingreso(): " + rolAfiliado2.getReferenciaAporteReingreso());
		System.out.println("**__**rolAfiliado2.getReferenciaSolicitudReingreso(): " + rolAfiliado2.getReferenciaSolicitudReingreso());		
		System.out.println("**__**rolAfiliado2.getEstadoAfiliado(): " + rolAfiliado2.getEstadoAfiliado());
		System.out.println("**__**rolAfiliado2.getClaseTrabajador(): " + rolAfiliado2.getClaseTrabajador());
		System.out.println("**__**rolAfiliado2.getFechaIngreso(): " + rolAfiliado2.getFechaIngreso());
		System.out.println("**__**rolAfiliado2.getFechaFinContrato(): " + rolAfiliado2.getFechaFinContrato());
		System.out.println("**__**rolAfiliado2.getTipoSalario(): " + rolAfiliado2.getTipoSalario());
		System.out.println("**__**rolAfiliado2.getValorSalarioMesadaIngresos());: " + rolAfiliado2.getValorSalarioMesadaIngresos());	
		System.out.println("**__**rolAfiliado2.getHorasLaboradasMes(): " + rolAfiliado2.getHorasLaboradasMes());
		System.out.println("**__**rolAfiliado2.getCargo(): " + rolAfiliado2.getCargo());
		System.out.println("**__**rolAfiliado2.getTipoContrato(): " + rolAfiliado2.getTipoContrato());
		System.out.println("**__**rolAfiliado2.getIdMunicipioDesempenioLabores(): " + rolAfiliado2.getIdMunicipioDesempenioLabores());
		
			if(update){
		System.out.println("**__**procede a realizar merge en ConsultasModeloCoreComposite : ");
			entityManager.merge(rolAfiliado2);
			}else{
				entityManager.persist(rolAfiliado2);
			}
	}
public void AportesCompositeactualizarEstadoEmpleadorPorAportes(ActivacionEmpleadorDTO datosReintegro) {
       System.out.println("**__**inicia AportesCompositeactualizarEstadoEmpleadorPorAportes aportes composite: " );

        Empleador empleador = AportesCompositeconsultarEmpleadorPorEmpresa(datosReintegro.getIdAportante(), entityManager);
        if (empleador != null) {
            if (EstadoEmpleadorEnum.INACTIVO.equals(empleador.getEstadoEmpleador())
                    || EstadoEmpleadorEnum.NO_FORMALIZADO_RETIRADO_CON_APORTES.equals(empleador.getEstadoEmpleador())) {
				empleador.setFechaRetiro(null);
				empleador.setMotivoDesafiliacion(null);
                empleador.setFechaCambioEstadoAfiliacion(new Date(datosReintegro.getFechaReintegro()));
                empleador.setEstadoEmpleador(EstadoEmpleadorEnum.ACTIVO);
                empleador.setCanalReingreso(datosReintegro.getCanalReintegro());
                empleador.setReferenciaAporteReingreso(datosReintegro.getIdRegistroGeneral());
                empleador.setFechaGestionDesafiliacion(null);

                entityManager.merge(empleador);
            }
        }
        else {
            String mensaje = " :: no se actualizó el estado. Empleador no encontrado";
              System.out.println("**__**error  AportesCompositeactualizarEstadoEmpleadorPorAportes "+mensaje );
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO + mensaje);
        }
        System.out.println("finaliza AportesCompositeactualizarEstadoEmpleadorPorAportes");
    }
	private Empleador AportesCompositeconsultarEmpleadorPorEmpresa(Long idEmpresa, EntityManager entityManager) {
		System.out.println("Inicia consultarEmpleadorPorEmpresa(Long)");
		try {
			Empleador empleador = (Empleador) entityManager
					.createNamedQuery(NamedQueriesConstants.APORTES_COMPOSITE_CONSULTAR_EMPLEADOR_POR_ID_EMPRESA)
					.setParameter("idEmpresa", idEmpresa).getSingleResult();

			List<ConsultarEstadoDTO> listConsulta = new ArrayList<>();
			ConsultarEstadoDTO paramsConsulta = new ConsultarEstadoDTO();
			paramsConsulta.setEntityManager(entityManager);
			paramsConsulta.setNumeroIdentificacion(empleador.getEmpresa().getPersona().getNumeroIdentificacion());
			paramsConsulta.setTipoIdentificacion(empleador.getEmpresa().getPersona().getTipoIdentificacion());
			paramsConsulta.setTipoPersona(ConstantesComunes.EMPLEADORES);
			listConsulta.add(paramsConsulta);

			List<EstadoDTO> listEstado = EstadosUtils.consultarEstadoCaja(listConsulta);
			empleador.setEstadoEmpleador(EstadoEmpleadorEnum.valueOf(listEstado.get(0).getEstado().toString()));

			System.out.println("Finaliza consultarEmpleadorPorEmpresa(Long)xxx");
			return empleador;
		} catch (NoResultException nre) {
			System.out.println("Finaliza consultarEmpleadorPorEmpresa(Long):xx No existe el empleador a actualizar");
			return null;
		}
	}
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistroDetalladoAnterior(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BigDecimal> consultarSalarioeIbcNuevo(Long idAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCoreComposite.consultarSalarioeIbcNuevo(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                BigDecimal result1=null;
                BigDecimal result2=null;
                List<BigDecimal> result  = new ArrayList();

		try {
			Object[] resultQuery = (Object[]) entityManager
					.createNamedQuery(NamedQueriesConstants.APORTES_COMPOSITE_CONSULTAR_SALARIO_E_IBC)
					.setParameter("idAporteDetallado", idAporteDetallado).getSingleResult();
                        if (resultQuery != null) {
							logger.info("Resultado salario :"+(BigDecimal) resultQuery[0]);
							logger.info("Resultado IBC :"+(BigDecimal) resultQuery[1]);
                                result1 =(BigDecimal) resultQuery[0];
                                result2 = (BigDecimal) resultQuery[1];
				                result.add(result1);
                                result.add(result2);
				
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistroDetalladoAnterior(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigDecimal consultarAporteObligatorio(Long idAporteDetallado) {
		String firmaMetodo = "ConsultasModeloCoreComposite.consultarSalarioeIbcNuevo(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                BigDecimal result  = BigDecimal.ZERO;

		try {
			Object resultQuery = (Object) entityManager
					.createNamedQuery(NamedQueriesConstants.APORTES_COMPOSITE_CONSULTAR_APORTE_OBLIGATORIO)
					.setParameter("idAporteDetallado", idAporteDetallado).getSingleResult();
                        if (resultQuery != null) {
                                result =(BigDecimal) resultQuery;
				
			}
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.business.interfaces.IConsultasModeloCore#consultarRegistroDetalladoAnterior(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal consultarAporteObligatorioAnt(Long idRegistroDetallado) {
        String firmaMetodo = "ConsultasModeloCoreComposite.consultarSalarioeIbcNuevo(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
                BigDecimal result  = BigDecimal.ZERO;

        try {
            Object resultQuery = (Object) entityManager
                    .createNamedQuery(NamedQueriesConstants.APORTES_COMPOSITE_CONSULTAR_APORTE_OBLIGATORIO_ANT)
                    .setParameter("idRegistoDetallado", idRegistroDetallado).getSingleResult();
                        if (resultQuery != null) {
                                result =(BigDecimal) resultQuery;
                
            }
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean validarReintegroDiferenteEmpleador(
		String numeroIdentificacionCotizante,
		TipoIdentificacionEnum tipoIdentificacionCotizante,
		String numeroIdentificacionAportante,
		TipoIdentificacionEnum tipoIdentificacionAportante
	) {
		String firmaMetodo = "ConsultasModeloCoreComposite.validarReintegroDiferenteEmpleador(Long)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
		try {
			Object idRolAfiliado = (Object) entityManager.createNamedQuery(
				NamedQueriesConstants.APORTES_COMPOSITE_CONSULTAR_REINTEGRO_DIFERENTE_EMPLEADOR
				).setParameter("numCotizante", numeroIdentificacionCotizante)
				.setParameter("tipoCotizante", tipoIdentificacionCotizante.name())
				.setParameter("numAportante", numeroIdentificacionAportante)
				.setParameter("tipoAportante", tipoIdentificacionAportante.name())
				.getSingleResult();
			if (idRolAfiliado != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
		
	}
//	/**apartirde aca comienza ajuste  ññññññññ*/
//
//// //@Override
////  public Map<String, Object> digitarDatosIdentificacionPersona(AfiliadoInDTO inDTO, UserDTO userDTO) {
////      System.out.println("Inicia AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona( AfiliadoInDTO, UserDTO )");
////      /*
////       * Este servicio de composición debe crear el afiliado, crear la
////       * solicitud de afiliación de persona y finalmente iniciar el proceso en
////       * el BPM y retornar las variables de contexto definidas en el BPM
////       */
////      Long idSolicitudGlobal = null;
////      Long idAfiliado = null;
////      Long idInstanciaProcesoAfiliacionPersona = null;
////
////      AfiliadoInDTO afiliadoDTO = null;
////
////      Map<String, Object> parametrosProceso = null;
////
////      if (inDTO != null && (inDTO.getPersona() != null) && inDTO.getPersona().getTipoIdentificacion() != null
////              && inDTO.getPersona().getNumeroIdentificacion() != null && (inDTO.getTipoAfiliado() != null)
////              && (inDTO.getCanalRecepcion() != null)) {
////
////          // se Crea el afiliado
////          System.out.println("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Crear el afiliado");
////          afiliadoDTO = crearAfiliado(inDTO);
////          idAfiliado = afiliadoDTO.getIdAfiliado();
////          if (idAfiliado != null && idAfiliado > 0) {
////              // Se crea la solicitud de afiliacion de la persona
////              System.out.println(
////                      "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Se crea la solicitud de afiliacion de la persona");
////              inDTO.setIdAfiliado(idAfiliado);
////              inDTO.setIdRolAfiliado(afiliadoDTO.getIdRolAfiliado());
////              idSolicitudGlobal = crearSolicitudAfiliacionPersona(inDTO);
////
////              if (idSolicitudGlobal != null && idSolicitudGlobal > 0) {
////                  System.out.println(
////                          "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Valido que el usuario que envian en la peticion sea el registrado para la caja de compensacion");
////
////                  System.out.println("clave " + userDTO.getNombreUsuario());
////
////                  System.out.println(
////                          "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: Inicio proceso de afiliacion de persona en el BPM");
////
////                  // genera el numero de radicado correspondiente y lo
////                  // actualiza en relacion en la solicitud
////                  String numeroRadicado = generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());
////
////                  if (inDTO.getIdBeneficiario() != null && !inDTO.getIdBeneficiario().isEmpty()) {
////                      // Si tiene esta como algun beneficiario se inactiva
////
////                      //actualizarEstadoBeneficiario(inDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO,
////                      //MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
////                      List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<BeneficiarioModeloDTO>();
////                      for (Long idBeneficiarioInactivar : inDTO.getIdBeneficiario()) {
////                          //actualizarEstadoBeneficiario(idBeneficiarioInactivar, EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
////                          ConsultarBeneficiario beneficiarioSrv = new ConsultarBeneficiario(idBeneficiarioInactivar);
////                          beneficiarioSrv.execute();
////
////                          listaBeneficiarios.add(beneficiarioSrv.getResult());
////                      }
////
////                      DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
////                      datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
////                      datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
////                      datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(numeroRadicado);
////                      datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
////                      RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
////                      novedadCascada.execute();
////                      
////                  }
////
////                  try {
////                      // Inicio proceso de afiliacion de persona en el BPM
////                      idInstanciaProcesoAfiliacionPersona = iniciarProcesoAfiliacionPersona(idSolicitudGlobal,numeroRadicado, userDTO);
////                  } catch (Exception e) {
////                      // TODO Auto-generated catch block
////                      throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
////                  }
////
////                  if (idInstanciaProcesoAfiliacionPersona != null && idInstanciaProcesoAfiliacionPersona > 0) {
////
////                      actualizarIdInstanciaProcesoSolicitudDePersona(idSolicitudGlobal, idInstanciaProcesoAfiliacionPersona);
////
////                      parametrosProceso = new HashMap<>();
////                      parametrosProceso.put("idSolicitudGlobal", idSolicitudGlobal);
////                      parametrosProceso.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
////                  }
////                  else {
////                      System.out.println(
////                              "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro incial el proceso de afiliacion persona en el BPM");
////                      throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE);
////                  }
////
////              }
////              else {
////                  System.out.println(
////                          "AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro radicar la solicitud");
////                  throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
////              }
////          }
////          else {
////              System.out.println("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No se logro crear el afiliado");
////              throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
////          }
////
////      }
////      else {
////          System.out.println("AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona :: No existe la Persona");
////          throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
////      }
////      System.out.println("Finaliza AfiliacionPersonasCompositeBusiness.digitarDatosIdentificacionPersona( AfiliadoInDTO, UserDTO )");
////      return parametrosProceso;
////  }
//
//    /**
//     * (non-Javadoc)
//     * 
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#verificarRequisitosDocumentalesPersona(com.asopagos.dto.VerificarRequisitosDocumentalesDTO,
//     *      com.asopagos.rest.security.dto.UserDTO)
//     */
//   // //@Override
//    public void verificarRequisitosDocumentalesPersona(VerificarRequisitosDocumentalesDTO inDTO, UserDTO userDTO) {
//        System.out.println(
//                "Inicia AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona( VerificarRequisitosDocumentalesDTO,UserDTO )");
//        List<Long> lstResult = null;
//        List<Long> lstIdsRequisitos = null;
//        List<PersonaDTO> lstPersonaDTO = null;
//
//        Long idTarea = null;
//        String idInstanciaProceso = null;
//        Boolean esDocumentoFisico = null;
//
//        PersonaDTO personaDTO = null;
//        SolicitudDTO solicitudDTO = null;
//        IntentoAfiliacionInDTO intentoAfiliacion = null;
//        SolicitudAfiliacionPersonaDTO soliAfiPersonaDTO = null;
//
//        if (inDTO != null && (inDTO.getIdSolicitudGlobal() != null) && !inDTO.getListaChequeo().getListaChequeo().isEmpty()
//                && (inDTO.getListaChequeo().getNumeroIdentificacion() != null) && (inDTO.getDocumentosFisicos() != null)
//                || (inDTO.getTipoClasificacion() != null) || (inDTO.getCumpleRequisitosDocumentales() != null)) {
//
//            System.out.println(
//                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Se guarda la información de la lista de chequeo");
//            // Se guarda la información de la lista de chequeo
//            lstResult = crearListaChequeoAfiliacionPersona(inDTO.getListaChequeo());
//            if (lstResult != null && !lstResult.isEmpty()) {
//
//                soliAfiPersonaDTO = consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
//                if (soliAfiPersonaDTO != null && soliAfiPersonaDTO.getIdInstanciaProceso() != null) {
//                    idInstanciaProceso = soliAfiPersonaDTO.getIdInstanciaProceso();
//                    idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
//                    if (idTarea != null) {
//                        if (inDTO.getCumpleRequisitosDocumentales()) {
//                            System.out.println(
//                                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Si todos los requisitos son satisfechos, actualizar el estado del afiliado como preafiliado");
//                            // Si todos los requisitos son satisfechos,
//                            // actualizar el estado del afiliado como
//                            // preafiliado
//                            lstPersonaDTO = buscarAfiliado(inDTO.getListaChequeo().getNumeroIdentificacion());
//                            if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
//                                personaDTO = lstPersonaDTO.iterator().next();
//                            }
//                            else {
//                                System.out.println(
//                                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No existe el afiliado");
//                                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                            }
//                        }
//                        else {
//                            lstIdsRequisitos = new ArrayList<Long>();
//                            for (ItemChequeoDTO requisito : inDTO.getListaChequeo().getListaChequeo()) {
//                                lstIdsRequisitos.add(requisito.getIdRequisito());
//                            }
//                            System.out.println(
//                                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: En caso de requisitos no satisfechos, registrar el intento de afiliación.");
//                            // en caso de requisitos no satisfechos, registrar
//                            // el intento de afiliación.
//                            intentoAfiliacion = new IntentoAfiliacionInDTO();
//                            intentoAfiliacion
//                                    .setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_REQUISITOS_DOCUMENTALES);
//                            intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
//                            intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
//                            // TODO se debe setear la fecha del proceso
//                            intentoAfiliacion.setFechaInicioProceso(new Date());
//                            intentoAfiliacion.setIdsRequsitos(lstIdsRequisitos);
//                            registrarIntentoAfiliacion(intentoAfiliacion);
//                        }
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: Se termina la tarea en el BPM");
//                        // Finalmente terminar la tarea en el BPM
//                        switch (inDTO.getDocumentosFisicos()) {
//                            case FISICO:
//                                esDocumentoFisico = Boolean.TRUE;
//                                actualizarEstadoDocumentacionAfiliacion(soliAfiPersonaDTO.getIdSolicitudGlobal(),
//                                        EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
//                                break;
//                            case ELECTRONICO:
//                                esDocumentoFisico = Boolean.FALSE;
//                                actualizarEstadoDocumentacionAfiliacion(soliAfiPersonaDTO.getIdSolicitudGlobal(),
//                                        EstadoDocumentacionEnum.ENVIADA_AL_BACK);
//                                break;
//
//                        }
//
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener el idTarea");
//                        // Se registra la información de la solicitud con los
//                        // campos indicados
//                        solicitudDTO = new SolicitudDTO();
//                        solicitudDTO.setIdSolicitud(soliAfiPersonaDTO.getIdSolicitudGlobal());
//                        solicitudDTO.setMetodoEnvio((inDTO.getDocumentosFisicos() != null) ? inDTO.getDocumentosFisicos() : null);
//                        solicitudDTO.setClasificacion(inDTO.getTipoClasificacion());
//                        actualizarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal(), solicitudDTO);
//
//                        terminarTareaAfiliacionPersonas(idTarea, inDTO.getCumpleRequisitosDocumentales(), esDocumentoFisico);
//
//                    }
//                    else {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener el idTarea");
//                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                    }
//                }
//                else {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro obtener la Solicitud de Afiliacion de la Persona");
//                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                }
//            }
//            else {
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No se logro realiza la creacion de las listas de chequeo");
//                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//            }
//        }
//        else {
//            System.out.println(
//                    "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: No llegan los parametros para la verificacion de los requisitos documentales");
//            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//        }
//        System.out.println(
//                "Finaliza AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona( VerificarRequisitosDocumentalesDTO,UserDTO )");
//    }
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//public void radicarSolicitudAbreviadaAfiliacionPersonaAfiliados(RadicarSolicitudAbreviadaDTO inDTO) {
//  ResultadoRegistroContactoEnum resultadoServ = null;
//   System.out.println("**__**Inicicia radicarSolicitudAbreviadaAfiliacionPersona");
//   UserDTO userDTO =new UserDTO();
//    resultadoServ=radicarSolicitudAbreviadaAfiliacionPersona(inDTO,userDTO);
//    if(resultadoServ != null){
//         System.out.println("**__**Exito resultadoServ viene diferente a null radicarSolicitudAbreviadaAfiliacionPersona");
//    }else{
//      System.out.println("**__**fallo resultadoServ viene igual a null radicarSolicitudAbreviadaAfiliacionPersona");  
//    }
//}
//    /**
//     * (non-Javadoc)
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#radicarSolicitudAbreviadaAfiliacionPersona(com.asopagos.afiliaciones.personas.composite.dto.RadicarSolicitudAbreviadaDTO,
//     *      com.asopagos.rest.security.dto.UserDTO)
//     */
//   // //@Override
//    public ResultadoRegistroContactoEnum radicarSolicitudAbreviadaAfiliacionPersona(RadicarSolicitudAbreviadaDTO inDTO, UserDTO userDTO) {
//        System.out.println(
//                "Inicia AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona( RadicarSolicitudAbreviadaDTO, UserDTO ) inDTO.getCanal() "+ inDTO.getCanal());
//
//        boolean incluyenBeneficiarios = false;
//
//        Long idSolicitudGlobal = null;
//        Long idRolAfiliado = null;
//        Long idTarea = null;
//        boolean docFisicos = false;
//        boolean registroIntentoAfiliacion = false;
//        Long idEmpleadorAfi = null;
//        String idInstanciaProceso = null;
//
//        TipoRadicacionEnum tipoRadicacionSolcitud = null;
//
//        SolicitudDTO solicitudDTO = null;
//        AfiliadoInDTO afiliadoInDTO = null;
//        IntentoAfiliacionInDTO intentoAfiliacion = null;
//        SolicitudAfiliacionPersonaDTO solAfiliacionPersonaDTO = null;
//        // Representa el salario registrado en pantalla, especial caso Reintegro
//        BigDecimal salario = null;
//        
//        List<BeneficiarioDTO> lstBeneficiariosDTO = inDTO.getBeneficiarios();
//        
//        if ((inDTO.getIdSolicitudGlobal() == null) || (inDTO.getTipoSolicitante() == null) || (inDTO.getTipoRadicacion() == null)) {
//            System.out.println(
//                    "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No viene parametros validos para radicar la solicitud de afilacion de persona");
//            // No viene parametros validos para radicar la solicitud de
//            // afilacion de persona
//            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//        }
//        else {
//            idSolicitudGlobal = inDTO.getIdSolicitudGlobal();
//            tipoRadicacionSolcitud = inDTO.getTipoRadicacion();
//
//            if (inDTO.getRegistrarIntentoAfiliacion()) {
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.verificarRequisitosDocumentalesPersona :: se registra el intento de afiliación, si se solicita desde pantalla.");
//                // se registra el intento de afiliación, si se solicita desde
//                // pantalla
//                registroIntentoAfiliacion = true;
//                intentoAfiliacion = new IntentoAfiliacionInDTO();
//                intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.DECLINACION_VOLUNTARIA_USUARIO);
//                intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
//                intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
//                intentoAfiliacion.setFechaInicioProceso(new Date());
//                registrarIntentoAfiliacion(intentoAfiliacion);
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a Registrar intento de afiliación");
//                // Se actualiza el estado de la solicitud a Registrar intento de
//                // afiliación
//                actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION);
//
//                if (lstBeneficiariosDTO != null && !lstBeneficiariosDTO.isEmpty()) {
//                    incluyenBeneficiarios = true;
//                }
//                // Se realiza la terminacion de tarea en el BPM para el proceso
//                // de
//                // Afiliación personas presencial
//                solAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
//                idInstanciaProceso = solAfiliacionPersonaDTO.getIdInstanciaProceso();
//                idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
//
//                if (solAfiliacionPersonaDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
//                    docFisicos = true;
//                }
//                if (inDTO.getMetodoEnvio() != null && inDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
//                    docFisicos = true;
//                }
//                if (idTarea != null) {
//                    String numeroRadicado = solAfiliacionPersonaDTO.getNumeroRadicacion();
//                    terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
//                            registroIntentoAfiliacion, docFisicos, numeroRadicado);
//                }
//                else {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No se logro obtener el idTarea");
//
//                }
//            }
//            else {
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Valida que todos los beneficiarios tengan el estado AFIALIABLE para poder radicar");
//                // Validar que todos los beneficiarios tengan el estado
//                // AFIALIABLE
//                // para poder radicar
//                if (lstBeneficiariosDTO != null && !lstBeneficiariosDTO.isEmpty()) {
//                    for (BeneficiarioDTO beneficiarioDTO : lstBeneficiariosDTO) {
//                        if (beneficiarioDTO.getResultadoValidacion() == null) {
//                            System.out.println(
//                                    "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No es posible radicar debido a que al menos un afiliado es NO_AFILIABLE");
//                            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//                        }
//                    }
//                    incluyenBeneficiarios = true;
//                }
//
//                solAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
//                //Consultar temporal para guardarlo
//                ConsultarDatosTemporalesEmpleador consultTemporal = new ConsultarDatosTemporalesEmpleador(idSolicitudGlobal);
//                consultTemporal.execute();
//                String dataTemporal = consultTemporal.getResult();
//                
//                if (dataTemporal != null && !dataTemporal.isEmpty()) {
//                    GuardarTemporalAfiliacionPersonaAportes guardarTemporal = new GuardarTemporalAfiliacionPersonaAportes();
//
//                    ObjectMapper mapper = new ObjectMapper();
//                    try {
//                        guardarTemporal = mapper.readValue(dataTemporal, GuardarTemporalAfiliacionPersonaAportes.class);
//                    } catch (Exception e) {
//                        System.out.println(" ERROR"+
//                                "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Ocurrio un error consultar los datos temporales"+
//                                e);
//                        throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO+e);
//                    }
//
//                    if (guardarTemporal.getInfoLaboral() != null) {
//                        guardarTemporal.getInfoLaboral().setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
//                        GuardarInformacionLaboral guardarInfoLaboral = new GuardarInformacionLaboral(guardarTemporal.getInfoLaboral());
//                        guardarInfoLaboral.execute();
//                        // Se ajusta que siempre sea el mismo salario CASO Reintegro
//                        if (guardarTemporal.getInfoLaboral().getValorSalario() != null) {
//                            salario = guardarTemporal.getInfoLaboral().getValorSalario();
//                        }
//                    }
//
//                    if (guardarTemporal.getModeloInformacion() != null) {
//                        GuardarDatosIdentificacionYUbicacion guardarInforUbicacion = new GuardarDatosIdentificacionYUbicacion(
//                                guardarTemporal.getModeloInformacion());
//                        guardarInforUbicacion.execute();
//                        if(guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos() != null){
//                            salario = guardarTemporal.getModeloInformacion().getValorMesadaSalarioIngresos();
//                        }
//                    }
//
//                    if (guardarTemporal.getBeneficiarios() != null && !guardarTemporal.getBeneficiarios().isEmpty()
//                            && inDTO.getTipoRadicacion().equals(TipoRadicacionEnum.ABREVIADA)) {
//                        lstBeneficiariosDTO = new ArrayList<BeneficiarioDTO>();
//                        for (BeneficiarioDTO beneficiarioDTO : guardarTemporal.getBeneficiarios()) {
//                            if (beneficiarioDTO.getResultadoValidacion().equals(ResultadoGeneralValidacionEnum.AFILIABLE)) {
//                                lstBeneficiariosDTO.add(beneficiarioDTO);
//                            }
//                        }
//                        if (!lstBeneficiariosDTO.isEmpty() && lstBeneficiariosDTO.size() > 0) {
//                            RegistrarPersonasBeneficiariosAbreviada registrarPersonasSrv = new RegistrarPersonasBeneficiariosAbreviada(
//                                    solAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado(), lstBeneficiariosDTO);
//                            registrarPersonasSrv.execute();
//                        }
//                    }
//                }
//                
//                afiliadoInDTO = solAfiliacionPersonaDTO.getAfiliadoInDTO();
//                idEmpleadorAfi = afiliadoInDTO.getIdEmpleador();
//                Empleador empleador = null;
//                if (afiliadoInDTO.getTipoAfiliado().equals(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE)) {
//                    empleador = buscarEmpleador(afiliadoInDTO.getIdEmpleador());
//                }
//
//                ResultadoRegistroContactoEnum resultadoServ = null;
//
//                // Se realizan las validaciones pertinentes a solicitudes ya
//                // existentes en proceso
//
//                Map<String, String> datosValidacion = new HashMap<String, String>();
//                datosValidacion.put("tipoIdentificacion", afiliadoInDTO.getPersona().getTipoIdentificacion().toString());
//                datosValidacion.put("numeroIdentificacion", afiliadoInDTO.getPersona().getNumeroIdentificacion());
//
//                if (empleador != null) {
//                    
//                    datosValidacion.put("tipoIdentificacionEmpleador",
//                            empleador.getEmpresa().getPersona().getTipoIdentificacion().toString());
//                    datosValidacion.put("numeroIdentificacionEmpleador", empleador.getEmpresa().getPersona().getNumeroIdentificacion());
//
//                }
//                datosValidacion.put("tipoAfiliado", afiliadoInDTO.getTipoAfiliado().getName());
//                // Mantis 0252654 - Se envia el valor de la solicitud global de afiliación
//                datosValidacion.put("idSolicitud", idSolicitudGlobal.toString());
//                ValidarPersonas validarPersona = new ValidarPersonas("121-107-1", ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
//                        afiliadoInDTO.getTipoAfiliado().toString(), datosValidacion);
//                validarPersona.execute();
//                List<ValidacionDTO> list = validarPersona.getResult();
//                ValidacionDTO validacionExistenciaSolicitud = getValidacion(ValidacionCoreEnum.VALIDACION_SOLICITUD_PERSONA, list);
//
//                if (validacionExistenciaSolicitud.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
//                    resultadoServ = ResultadoRegistroContactoEnum.AFILIACION_EN_PROCESO;
//
//                    /*
//                     * se procede a cambiar el estado de la solciitud a cerrada
//                     * y a terminar la instancia del proceso en el BPM
//                     */
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
//
//                    /**
//                     * Se registra intento de afiliación para la solicitud que estaba en proceso y
//                     * llevar la traza de la inconsistencía
//                     */
//                    intentoAfiliacion = new IntentoAfiliacionInDTO();
//                    intentoAfiliacion.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.SOLICITUD_SIMULTANEA);
//                    intentoAfiliacion.setIdSolicitud(inDTO.getIdSolicitudGlobal());
//                    intentoAfiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
//                    intentoAfiliacion.setFechaInicioProceso(new Date());
//                    registrarIntentoAfiliacion(intentoAfiliacion);
//
//                    AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL,
//                            new Long(solAfiliacionPersonaDTO.getIdInstanciaProceso()));
//                    aborProceso.execute();
//
//                    return resultadoServ;
//                }
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la solicitud a radicada");
//                // Se actualiza el estado de la solicitud a radicada
//                actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.RADICADA);
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se registra la información de la solicitud con los campos indicados");
//                // Se registra la información de la solicitud con los campos
//                // indicados
//                solicitudDTO = new SolicitudDTO();
//                solicitudDTO.setFechaRadicacion(new Date());
//                solicitudDTO.setCanalRecepcion(inDTO.getCanal());
//                solicitudDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
//                solicitudDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
//                solicitudDTO.setTipoRadicacion(tipoRadicacionSolcitud);
//                solicitudDTO.setMetodoEnvio((inDTO.getMetodoEnvio() != null) ? inDTO.getMetodoEnvio() : null);
//                actualizarSolicitudAfiliacionPersona(idSolicitudGlobal, solicitudDTO);
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza la fecha de afiliación del afiliadoa la caja de compensación");
//                // Se actualiza el rol Afiliado
//                // Se actualiza la fecha de afiliación del afiliadoa la caja de compensación
//                // Se actualiza el estado de afiliación a ACTIVO
//                idRolAfiliado = inDTO.getIdRolAfiliado();
//                RolAfiliadoModeloDTO rolAfiliadoModeloDTO =  new RolAfiliadoModeloDTO();
//                rolAfiliadoModeloDTO.setIdRolAfiliado(inDTO.getIdRolAfiliado());
//                rolAfiliadoModeloDTO.setFechaAfiliacion(new Date().getTime());
//                rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.ACTIVO);
//                actualizarRolAfiliado(rolAfiliadoModeloDTO);
//
//                if (solAfiliacionPersonaDTO.getMetodoEnvio().equals(FormatoEntregaDocumentoEnum.FISICO)) {
//                    docFisicos = true;
//                }
//                if (inDTO.getMetodoEnvio() != null) {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el estado de la documentación dependiendo de lo indicado al servicio");
//                    // actualizo el estado de la documentación dependiendo de lo
//                    // indicado al servicio
//                    switch (inDTO.getMetodoEnvio()) {
//                        case ELECTRONICO:
//                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.ENVIADA_AL_BACK);
//                            break;
//                        case FISICO:
//                            actualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal, EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
//                            docFisicos = true;
//                            break;
//                    }
//                }
//
//                /*
//                 * TODO esta parte esta por realizarse posteriormente ya que aun
//                 * no se encuentra definida: "Almacena y registra la información
//                 * de la solicitud. Nota: En caso de que el afiliado principal
//                 * y/o beneficiarios, sea reintegrado (estuvo afiliado
//                 * previamente en la Caja de Compensación), el sistema debe
//                 * guardar la información histórica (no sobreescribirla con los
//                 * datos nuevos)."
//                 * 
//                 */
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza el valor del campo salario/mesada del afiliado principal");
//                // Actualizo el valor del campo salario/mesada del afiliado
//                // principal
//                if (salario != null) {
//                    afiliadoInDTO.setValorSalarioMesada(salario);
//                }
//                System.out.println(
//                        "--AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se actualiza al trabajador en estado “Activo”");
//                // Se actualiza al trabajador
//                actualizarAfiliado(afiliadoInDTO);
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se calcula la categoría del afiliado deacuerdo a las condiciones establecidas en HU-121-341 Definir estructura persona/aportante");
//                // Se calcula la categoría del afiliado deacuerdo a las
//                // condiciones
//                // establecidas en HU-121-341 Definir estructura
//                // persona/aportante
//               calcularCategoriaAfiliado(afiliadoInDTO);
//
//                System.out.println(
//                        "xxxAfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: Se realiza la terminacion de tarea en el BPM para el proceso de Afiliación personas presencial");
//                // Se realiza la terminacion de tarea en el BPM para el proceso
//                // de
//                // Afiliación personas presencial
//                idInstanciaProceso = solAfiliacionPersonaDTO.getIdInstanciaProceso();
//                idTarea = consultarTareaAfiliacionPersonas(idInstanciaProceso);
//                if (idTarea != null) {
//                    String numeroRadicado = solAfiliacionPersonaDTO.getNumeroRadicacion();
//                    terminarTareaRadicacionAbreviadaAfiliacionPersonas(idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios,
//                            registroIntentoAfiliacion, docFisicos, numeroRadicado);
//                            /**ajuste para pila  14/01/2022*/
//                if(inDTO.getCanal() == CanalRecepcionEnum.PILA){
//                System.out.println("**__** INICIA VerificarSolicitud PARA PILA  ");
//                System.out.println("**__** idEmpleadorAfi VerificarSolicitud PARA PILA  "+idEmpleadorAfi);
//                System.out.println("**__** idTarea VerificarSolicitud PARA PILA  "+idTarea);
//                System.out.println("**__** numeroRadicado VerificarSolicitud PARA PILA  "+numeroRadicado);
//                System.out.println("**__** afiliadoInDTO.getPersona().getNumeroIdentificacion() VerificarSolicitud PARA PILA  "+afiliadoInDTO.getPersona().getNumeroIdentificacion());
//            VerificarSolicitudAfiliacionPersonaDTO verficarSolicitudDTO = new VerificarSolicitudAfiliacionPersonaDTO();
//                verficarSolicitudDTO.setIdEmpleador(idEmpleadorAfi);
//                verficarSolicitudDTO.setIdTarea(idTarea);
//                verficarSolicitudDTO.setNumeroIdentificacionAfiliado(afiliadoInDTO.getPersona().getNumeroIdentificacion());
//                verficarSolicitudDTO.setNumeroRadicado(numeroRadicado);
//                verficarSolicitudDTO.setResultadoGeneralAfiliado(ResultadoGeneralProductoNoConformeEnum.APROBADA);
//                verificarInformacionSolicitud(verficarSolicitudDTO,userDTO);
//                                    
//      //  VerificarSolicitud verficarSolicitud = new VerificarSolicitud(verficarSolicitud);
//      //                  verficarSolicitud.execute();
//                            }
//
//                //VerificarSolicitudDTO resul = verficarSolicitud.getResult();
//                            /**Fin ajuste para pila */
//                }
//                else {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona :: No se logro obtener el idTarea");
//                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                }
//            }
//        }
//        System.out.println(
//                "Finaliza AfiliacionPersonasCompositeBusiness.radicarSolicitudAbreviadaAfiliacionPersona( RadicarSolicitudAbreviadaDTO, UserDTO )");
//        return null;
//    }
//
//    private List<ItemChequeoDTO> ingresarSolcitudItemChequeo(Long idSolicitudGlobal, List<ItemChequeoDTO> items, Long fechaRecepcionDocumentos) {
//        if (items != null && !items.isEmpty() && idSolicitudGlobal != null) {
//            for (ItemChequeoDTO itemChequeoDTO : items) {
//                itemChequeoDTO.setIdSolicitudGlobal(idSolicitudGlobal);
//                itemChequeoDTO.setFechaRecepcionDocumentos(fechaRecepcionDocumentos);
//            }
//        }
//        return items;
//    }
//
//    /**
//     * (non-Javadoc)
//     * 
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#afiliarBeneficiario(com.asopagos.afiliaciones.personas.composite.dto.AfiliarBeneficiarioDTO,
//     *      com.asopagos.rest.security.dto.UserDTO)
//     */
//    //@Override
//    public Long afiliarBeneficiario(AfiliarBeneficiarioDTO inDTO, UserDTO userDTO) {
//        System.out.println("Inicia AfiliacionPersonasCompositeBusiness.afiliarBeneficiario(AfiliarBeneficiarioDTO, UserDTO)");
//
//        System.out.println("Los datos del beneficiario a procesar son: " + inDTO.toString());
//        
//        
//        boolean success = false;
//        Long idRolAfiliado = null;
//        Long idAfiliado = null;
//        Long idBeneficiario = null;
//        Long idSolcitudGlobal = null;
//        Long idGrupoFamiliar = null;
//
//        String numIdentAfiliado = null;
//        String numeroRadicado = null;
//
//        PersonaDTO personaAfiliado = null;
//        PersonaDTO personaBeneficiario = null;
//        SolicitudDTO solicitudGlobalDTO = null;
//        SolicitudAfiliacionPersonaDTO solicitudAfiliacion = null;
//        IdentificacionUbicacionPersonaDTO conyugeBeneficiario = null;
//        BeneficiarioHijoPadreDTO hijoPadreBeneficiario = null;
//        DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO = null;
//        GrupoFamiliarDTO miembrogrupoFamiliarAfiliado = null;
//
//        List<PersonaDTO> lstPersonaDTO = null;
//        List<GrupoFamiliarDTO> lstGrupoFamiliarDTO = null;
//
//        if ((inDTO.getBeneficiarioConyuge() == null) && (inDTO.getBeneficiarioHijoPadre() == null)) {
//            System.out.println(
//                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para tramitar la solicitud de registro de beneficiario");
//            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//        }
//        else {
//            // si es true el beneficiario es nuevo
//            boolean auxBeneficiarioNuevo = false;
//            if (inDTO.getBeneficiarioConyuge() == null) {
//                auxBeneficiarioNuevo = true;
//                hijoPadreBeneficiario = inDTO.getBeneficiarioHijoPadre();
//                hijoPadreBeneficiario.setFechaAfiliacion(new Date());
//                hijoPadreBeneficiario.setListaChequeo(
//                        ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), hijoPadreBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
//                personaBeneficiario = hijoPadreBeneficiario.getPersona();
//            }
//            else if (inDTO.getBeneficiarioHijoPadre() == null) {
//                auxBeneficiarioNuevo = true;
//                conyugeBeneficiario = inDTO.getBeneficiarioConyuge();
//                conyugeBeneficiario.setFechaAfiliacion(new Date());
//                conyugeBeneficiario
//                        .setListaChequeo(ingresarSolcitudItemChequeo(inDTO.getIdSolicitudGlobal(), conyugeBeneficiario.getListaChequeo(), inDTO.getFechaRecepcionDocumento()));
//                personaBeneficiario = conyugeBeneficiario.getPersona();
//            }
//            idAfiliado = personaBeneficiario.getIdAfiliado();
//            idSolcitudGlobal = inDTO.getIdSolicitudGlobal();
//            numIdentAfiliado = inDTO.getNumeroIdentificacionAfiliado();
//
//            solicitudAfiliacion = consultarSolicitudAfiliacionPersona(idSolcitudGlobal);
//            if (solicitudAfiliacion == null) {
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se encuentra la solicitud de afiliacion relacionada ");
//                throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//            }
//            //Se obtiene el id del Rol Afiliado
//            idRolAfiliado = solicitudAfiliacion.getAfiliadoInDTO().getIdRolAfiliado();
//            numeroRadicado = solicitudAfiliacion.getNumeroRadicacion();
//
//            if (TipoBeneficiarioEnum.CONYUGE.equals(inDTO.getTipoBeneficiario())) {
//
//                // Especificacion 3.1.3 Completar información del beneficiario
//                // tipo cónyuge
//                lstPersonaDTO = buscarAfiliado(numIdentAfiliado);
//                if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
//                    personaAfiliado = lstPersonaDTO.iterator().next();
//                    // En caso de que se active un cónyuge, el sistema debe
//                    // cambiar automáticamente el valor del campo “Estado Civil”
//                    // a “Casado/Unión Libre”.
//                    /*CCREPNORMATIVOS*/
//                    //conyugeBeneficiario.getPersona().setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
//                }
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion ");
//                // Se realiza la evaluacion del resultado general de validacion
//                if (inDTO.getResultadoGeneralValidacion() != null) {
//                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
//                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
//                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
//                        conyugeBeneficiario.setFechaAfiliacion(new Date());
//                    }
//                    else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
//                        // Si el Resultado validación del beneficiario es No
//                        // afiliable, se debe inactivar
//                        conyugeBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
//                        conyugeBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
//                        if(auxBeneficiarioNuevo){
//                            conyugeBeneficiario.setFechaAfiliacion(null);
//                        }
//                    }
//                    else {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
//                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//
//                    /*
//                     * Calcular categoría del beneficiario y habilitar la
//                     * utilización de servicios de la caja, como se especifica
//                     * en la “HU-TRN-341Definir estructura de personas –
//                     * aportantes”.
//                     */
//
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Almacena los datos del beneficiario asociado al afiliado principal de la solicitud");
//                    // Se Almacena los datos del beneficiario asociado al
//                    // afiliado principal de la solicitud, con la fecha y hora
//                    // del sistema como “Fecha y Hora de afiliación”
//                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
//                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
//                    datosBasicosIdentificacionDTO.setPersona(conyugeBeneficiario.getPersona());
//                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
//
//                    // TODO en el modelo de datos actual no existe el concepto
//                    // categoria en entidades
//                    // conyugeBeneficiario.setCategoriaBeneficiario(categoriaBeneficiario);
//                    conyugeBeneficiario.setFechaAfiliacion(new Date());
//
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Actualiza con el valor “Presencial” en el dato de “Canal de recepción” de la solicitud");
//                    // Se Registra el valor “Presencial” en el dato de “Canal de
//                    // recepción” de la solicitud.
//                    solicitudGlobalDTO = new SolicitudDTO();
//                    solicitudGlobalDTO.setIdSolicitud(idSolcitudGlobal);
//                    solicitudGlobalDTO.setNumeroRadicacion(numeroRadicado);
//                    //solicitudGlobalDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
//                    actualizarSolicitudAfiliacionPersona(idSolcitudGlobal, solicitudGlobalDTO);
//                    conyugeBeneficiario.setIdRolAfiliado(idRolAfiliado);
//                    idBeneficiario = registrarInformacionBeneficiarioConyuge(idAfiliado, conyugeBeneficiario);
//                    if (idBeneficiario == null) {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario conyuge");
//                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                    }
//
//                    if (conyugeBeneficiario.getIdGrupoFamiliar() != null) {
//                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioConyuge().getIdGrupoFamiliar(),
//                                datosBasicosIdentificacionDTO);
//                    }
//
//                    lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
//                    if (lstGrupoFamiliarDTO != null && !lstGrupoFamiliarDTO.isEmpty()) {
//                        miembrogrupoFamiliarAfiliado = lstGrupoFamiliarDTO.iterator().next();
//                        idGrupoFamiliar = miembrogrupoFamiliarAfiliado.getIdGrupoFamiliar();
//                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, datosBasicosIdentificacionDTO);
//                        if (!success) {
//                            System.out.println(
//                                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro asociar el beneficiario conyuge al Grupo Familiar");
//                            throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                        }
//                    }
//                    else {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se encontro el Grupo Familiar del afiliado");
//                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//                else {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario Conyuge");
//                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//
//            }
//            else if ((TipoBeneficiarioEnum.PADRES.equals(inDTO.getTipoBeneficiario())
//                    || TipoBeneficiarioEnum.HIJO.equals(inDTO.getTipoBeneficiario()))) {
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realizan las validaciones del beneficiario hijo o padre “HU-121-339 Validar condiciones de persona en BD Core ");
//
//                System.out.println(
//                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se realiza la evaluacion del resultado general de validacion del hijo o padre ");
//                // Se realiza la evaluacion del resultado general de validacion
//                // del hijo o padre
//                if (inDTO.getResultadoGeneralValidacion() != null) {
//                    System.out.println("el resultado general de las validaciones, contenido en inDTO.getResultadoGeneralValidacion() fue: ");
//                    
//                    if (ResultadoGeneralValidacionEnum.AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es afiliable, ponerlo en estado activo ");
//                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.ACTIVO);
//                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.ACTIVO);
//                        hijoPadreBeneficiario.setFechaAfiliacion(new Date());
//                    }
//                    else if (ResultadoGeneralValidacionEnum.NO_AFILIABLE.equals(inDTO.getResultadoGeneralValidacion())) {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Si el Resultado validación del beneficiario es No afiliable, ponerlo en estado inactivo ");
//                        // Si el Resultado validación del beneficiario es No
//                        // afiliable, se debe inactivar
//                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoCaja(EstadoAfiliadoEnum.INACTIVO);
//                        hijoPadreBeneficiario.getPersona().setEstadoAfiliadoRol(EstadoAfiliadoEnum.INACTIVO);
//                        if(auxBeneficiarioNuevo){
//                            hijoPadreBeneficiario.setFechaAfiliacion(null);
//                        }
//                    }
//                    else {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No viene datos completos para verificar las condiciones del resultado de validacion del beneficiario hijo o padre");
//                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: Se Persisten los datos del beneficiario asociado al afiliado principal de la solicitud");
//                    // Se Almacena los datos del beneficiario asociado al
//                    // afiliado principal de la solicitud, con la fecha y hora
//                    // del sistema como “Fecha y Hora de afiliación”
//                    datosBasicosIdentificacionDTO = new DatosBasicosIdentificacionDTO();
//                    datosBasicosIdentificacionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
//                    datosBasicosIdentificacionDTO.setPersona(hijoPadreBeneficiario.getPersona());
//                    datosBasicosIdentificacionDTO.setTipoAfiliado(inDTO.getTipoAfiliado());
//
//                    // Se Almacenan los datos del beneficiario asociado al
//                    // afiliado principal de la solicitud
//                    // TODO en el modelo de datos actual no existe el concepto
//                    // categoria en entidades
//                    // hijoPadreBeneficiario.setCategoriaBeneficiario(categoriaBeneficiario);
//                    hijoPadreBeneficiario.setFechaAfiliacion(new Date());
//
//                    if (hijoPadreBeneficiario.getIdGrupoFamiliar() != null) {
//                        hijoPadreBeneficiario.getPersona().setGradoAcademico(inDTO.getBeneficiarioHijoPadre().getIdGradoAcademico());
//                    }
//                    hijoPadreBeneficiario.setIdRolAfiliado(idRolAfiliado);
//                    
//                    idBeneficiario = registrarInformacionBeneficiarioHijoOPadre(idAfiliado, hijoPadreBeneficiario);
//                    if (idBeneficiario == null) {
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro registrar el beneficiario hijo o padre");
//                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//                    }
//                    if (inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar() != null) {
//                        success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, inDTO.getBeneficiarioHijoPadre().getIdGrupoFamiliar(),
//                                datosBasicosIdentificacionDTO);
//                    }
//                    else {
//                        lstGrupoFamiliarDTO = consultarGrupoFamiliar(idAfiliado);
//                        if (lstGrupoFamiliarDTO != null && !lstGrupoFamiliarDTO.isEmpty()) {
//                            miembrogrupoFamiliarAfiliado = lstGrupoFamiliarDTO.iterator().next();
//                            idGrupoFamiliar = miembrogrupoFamiliarAfiliado.getIdGrupoFamiliar();
//                            success = asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, datosBasicosIdentificacionDTO);
//                            if (!success) {
//                                System.out.println(
//                                        "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro asociar el beneficiario hijo o padre al grupo familiar del afiliado");
//                                throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                            }
//                        }
//                        else {
//                            System.out.println(
//                                    "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No se logro encontrar el grupo familiar del afiliado");
//                            throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                        }
//                    }
//                }
//                else {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.afiliarBeneficiario :: No vienen datos completos para verificar las validaciones de las Condiciones del Beneficiario hijo o padre");
//                    throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                }
//
//            }
////            if(inDTO.getIdBeneficiario()!=null){
////               actualizarEstadoBeneficiario(inDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
////            }
//            if (inDTO.getIdsBeneficiariosInactivar() != null) {
//                List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<BeneficiarioModeloDTO>();
//                for (Long idBeneficiarioInactivar : inDTO.getIdsBeneficiariosInactivar()) {
//                    //actualizarEstadoBeneficiario(idBeneficiarioInactivar, EstadoAfiliadoEnum.INACTIVO, MotivoDesafiliacionBeneficiarioEnum.CAMBIO_TIPO_AFILIADO);
//                    ConsultarBeneficiario beneficiarioSrv = new ConsultarBeneficiario(idBeneficiarioInactivar);
//                    beneficiarioSrv.execute();
//
//                    listaBeneficiarios.add(beneficiarioSrv.getResult());
//                }
//
//                DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
//                datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
//                datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
//                datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(numeroRadicado);
//                datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION);
//                RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
//                novedadCascada.execute();
//            }
//                
//        }
//        System.out.println("Finaliza AfiliacionPersonasCompositeBusiness.afiliarBeneficiario(AfiliarBeneficiarioDTO, UserDTO)");
//        return idBeneficiario;
//    }
//
//     
//    //@Override
//    public void verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO inDTO, UserDTO userDTO) {
//        System.out.println(
//                "Inicia AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
//        // HU 121-122 3.1.4. Cerrar solicitud de afiliación de persona
//        // (verificación sin producto no conforme no resuelto)
//        String numeroRadicado = null;
//        String numeroIdentAfiliado = null;
//
//        Long idSolicitudGlobal = null;
//        Long idAfiliado = null;
//        Long idRolAfiliado = null;
//        List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> lstResultadoGeneralBeneficiarios;
//
//        List<PersonaDTO> lstPersonaDTO = null;
//        List<SolicitudDTO> lstSolicitudDTO = null;
//        List<BeneficiarioDTO> lstBeneficiarioDTO = null;
//
//        AfiliadoInDTO afiliadoInDTO = null;
//        PersonaDTO personaDTO = null;
//        SolicitudDTO solicitudDTO = null;
//
//        Integer resultado = null;
//
//        if ((inDTO.getNumeroRadicado() == null) || (inDTO.getNumeroIdentificacionAfiliado() == null)) {
//            System.out.println(
//                    "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No viene datos completos para tramitar la solicitud de registro de beneficiario");
//            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
//        }
//        else {
//            numeroRadicado = inDTO.getNumeroRadicado();
//            lstSolicitudDTO = buscarSolicitud(numeroRadicado);
//            numeroIdentAfiliado = inDTO.getNumeroIdentificacionAfiliado();
//
//            if (lstSolicitudDTO != null && !lstSolicitudDTO.isEmpty()) {
//                solicitudDTO = lstSolicitudDTO.iterator().next();
//                idSolicitudGlobal = solicitudDTO.getIdSolicitud();
//
//
//                SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = consultarSolicitudAfiliacionPersona(idSolicitudGlobal);
//
//                idAfiliado = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado();
//                idRolAfiliado = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado();
//
//                if (inDTO.getResultadoGeneralAfiliado() == null && inDTO.getResultadoGeneralBeneficiarios() == null) {
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se cambia el estado de la solicitud a “Aprobada” si no vienen resultados a nivel de validaciones y posteriormente a CERRADA");
//                    // Se cambia el estado de la solicitud a “Aprobada” si no
//                    // vienen resultados a nivel de validaciones
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
//                   // procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
//                }
//                else if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(inDTO.getResultadoGeneralAfiliado()) || ResultadoGeneralProductoNoConformeEnum.RECHAZADA.equals(inDTO.getResultadoGeneralAfiliado())) {
//                    resultado = 3;
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se cambia el estado de la solicitud a “Rechazada” si el resultado de las validaciones fueron NO_SUBSANABLES y posteriormente a CERRADA");
//                    // Se cambia el estado de la solicitud a “Rechazada” si el
//                    // resultado de las validaciones fueron SUBSANABLES
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
//
//                    actualizarEstadoRolAfiliado(idRolAfiliado, EstadoAfiliadoEnum.INACTIVO);
//
//                    System.out.println("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se inactiva del afiliado principal");
//                    // Se inactiva del afiliado principal
//                    lstPersonaDTO = buscarAfiliado(numeroIdentAfiliado);
//                    if (lstPersonaDTO != null && !lstPersonaDTO.isEmpty()) {
//                        personaDTO = lstPersonaDTO.iterator().next();
//                        afiliadoInDTO = solicitudAfiliacionPersonaDTO.getAfiliadoInDTO();
//                        afiliadoInDTO.setPersona(personaDTO);
//                        actualizarAfiliado(afiliadoInDTO);
// 
//                        // TODO
//                        /*
//                         * -Desafiliar al afiliado principal de la solicitud (en
//                         * esta instancia de afiliación). -Cambiar de estado la
//                         * solicitud a “Cerrada” <<Es en otro accion desde el
//                         * frontend?>> ejecuta el comunicado “003” “Notificación de
//                         * radicación de solicitud de afiliación de persona” -
//                         * HU-TRA-331 Enviar comunicado).
//                         */
//                        ConsultarRolAfiliado rolAfiliadoService = new ConsultarRolAfiliado(idRolAfiliado);
//                        rolAfiliadoService.execute();
//                        RolAfiliadoModeloDTO rolAfiliadoModeloDTO = rolAfiliadoService.getResult();
//                        rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
//                        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
//                        afiliadoModeloDTO.setIdAfiliado(idAfiliado);
//                        rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
//                        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
//                        rolAfiliadoModeloDTO.setIdRolAfiliado(idRolAfiliado);
//                        rolAfiliadoModeloDTO.setIdEmpleador(inDTO.getIdEmpleador());
//                        rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getTipoAfiliado());
//                        rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
//                        ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
//                        
//                        if (inDTO.getResultadoGeneralBeneficiarios() != null) {
//                            lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
//                            for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
//                                Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
//                                System.out.println(
//                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
//                                // Se efectúa la inactivación del beneficiario
//                                // incluido en la solicitud que haya sido
//                                // activado durante el proceso
//                                if (idBeneficiario!=null){
//                                    actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
//                           
//                                }
//                            }
//                        }
//                        System.out.println(
//                                "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación de todos los beneficiarios incluidos en la solicitud  que hayan sido activados durante el proceso");
//                    }
//                    else {
//                        System.out.println("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No se encontro el afiliado");
//                        throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//                    }
//                }
//                else if (ResultadoGeneralProductoNoConformeEnum.SUBSANABLE.equals(inDTO.getResultadoGeneralAfiliado())) {
//                    resultado = 1;
//                    System.out.println(
//                            "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se Cambia el estado de la solicitud a “No conforme en gestión” si el resultado general del afiliado es SUBSANABLE independiente de sus beneficiarios..");
//                    // Se Cambia el estado de la solicitud a “No conforme en
//                    // gestión” si el resultado general del afiliado es
//                    // SUBSANABLE independiente de sus beneficiarios.
//                    actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
//
//                    lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
//                    if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {
//
//                        // mapResultadoGeneralBeneficiarios.forEach((k,v) ->
//                        // ...);
//                        for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
//                            Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
//                            ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
//                                    .getResultadoGeneralBeneficiario();
//                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
//                                System.out.println(
//                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
//                                // Se efectúa la inactivación del beneficiario
//                                // incluido en la solicitud que haya sido
//                                // activado durante el proceso
//                                
//                                // Si el valor del campo es “No subsanable”, se
//                                // efectúa la inactivación del
//                                // beneficiario (si está activo)
//                                // (se debe registrar automáticamente novedad de
//                                // inactivación de personas con
//                                // motivo/causal: “anulación de afiliación”
//                                if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE
//                                        .equals(resultadoBeneficiarioDTO
//                                                .getResultadoGeneralBeneficiario())) {
//                                    registrarNovedadInactivacion(
//                                            resultadoBeneficiarioDTO,
//                                            EstadoAfiliadoEnum.INACTIVO,
//                                            MotivoDesafiliacionBeneficiarioEnum.AFILIACION_ANULADA);
//                                } else {
//                                    actualizarEstadoBeneficiario(
//                                            idBeneficiario,
//                                            EstadoAfiliadoEnum.INACTIVO, null);
//                                }
//
//                            }
//                        }
//                    }
//                }
//                else if (ResultadoGeneralProductoNoConformeEnum.APROBADA.equals(inDTO.getResultadoGeneralAfiliado())) {
//
//                    resultado = 2;
//                    lstResultadoGeneralBeneficiarios = inDTO.getResultadoGeneralBeneficiarios();
//                    if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {
//                    
//                        Integer todosNoSubsanables = new Integer(0);
//                    
//
//                        for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
//                            Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
//                            ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
//                                    .getResultadoGeneralBeneficiario();
//                            
//                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE
//                                    .equals(resultadoBeneficiarioDTO
//                                            .getResultadoGeneralBeneficiario())) {
//                                todosNoSubsanables++;
//
//                            }
//                            
//                            if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
//                                System.out.println(
//                                        "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
//                                // Se efectúa la inactivación del beneficiario
//                                // incluido en la solicitud que haya sido
//                                // activado durante el proceso
//                                actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
//                            }
//                            if (!ResultadoGeneralProductoNoConformeEnum.APROBADA.equals(resultadoBeneficiario)) {
//                                resultado = 1;
//                            }
//                        }          
//                        if(todosNoSubsanables.equals(lstResultadoGeneralBeneficiarios.size())){
//                            resultado= 2;
//                        }
//
//                    }
//                    
//
//                    
//                    if (resultado == 2) {
//                        // Se Cambia el estado de la solicitud a “aprobada y luego a cerrada” si el resultado general del afiliado es
//                        // Aprobado independiente de sus beneficiarios.
//                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
//                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
//                        actualizarEstadoRolAfiliado(idRolAfiliado, EstadoAfiliadoEnum.ACTIVO);
//                       // procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
//                    }
//                    else {
//                        // Se Cambia el estado de la solicitud a “No conforme en
//                        // gestión” si el resultado general del afiliado es
//                        // SUBSANABLE independiente de sus beneficiarios.
//                        actualizarEstadoSolicitudPersona(idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_EN_GESTION);
//                    }
//                }
//            }
//            else {
//                System.out.println("AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: No se encontro la solcitud.");
//                throw new ValidacionFallidaException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//            }
//        }
//
//        // resultadoVerificacion:Integer(1-subsanable,2-aprobada,3-rechazada)
//        Map<String, Object> params = new HashMap<>();
//        params.put("resultadoVerificacion", resultado);
//        try {
//            TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
//            service2.execute();
//        } catch (Exception e) {
//            System.out.println(" ERROR"+
//                    "Finaliza AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
//            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
//        }
//        System.out.println(
//                "Finaliza AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO, UserDTO)");
//    }
//
//    /**
//     * (non-Javadoc)
//     * 
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#validarBeneficiario(com.asopagos.rest.security.dto.UserDTO)
//     */
//    //@Override
//    @Deprecated
//    public Object validarBeneficiario(UserDTO userDTO) {
//        /*
//         * Se debe implementar la logica descrita desde la página 24 para el
//         * botón validar. Sin embargo aun no es claro que acciones se van a
//         * hacer desde la pantall y que acciones debe implementar el servicio
//         * compuesto
//         * 
//         * <<Se realizo la verificacion de esta capacidad con Sergio y Jerson y
//         * la logica a implementar ya se esta realizando en el consumo de otros
//         * servicios pro tanto no se hace necesario la creacion de esta
//         * capacidad [20161016:11:31::hhernandez]>>
//         */
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     * (non-Javadoc)
//     * 
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#afiliarAfiliadoPrincipal(com.asopagos.rest.security.dto.UserDTO)
//     */
//    //@Override
//    @Deprecated
//    public Object afiliarAfiliadoPrincipal(UserDTO userDTO) {
//        // TODO: eliminar
//        return null;
//    }
//
//    /**
//     * (non-Javadoc)
//     * 
//     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#gestionarProductoNoConformeSubsanable(com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO,
//     *      com.asopagos.rest.security.dto.UserDTO)
//     */
//// //@Override
//// public void gestionarProductoNoConformeSubsanable(GestionarProductoNoConformeSubsanableDTO inDTO, UserDTO userDTO) {
////     // Se actualiza el estado de la solicitud
////     SolicitudAfiliacionPersonaDTO solicitud = consultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
////     if (solicitud != null) {
////         actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.NO_CONFORME_GESTIONADA);
////         Map<String, Object> params = new HashMap<String, Object>();
////         // el bpm no espera parametros, por ende se envía nulo
////         Long idTarea = consultarTareaAfiliacionPersonas(solicitud.getIdInstanciaProceso());
////         try {
////             TerminarTarea service2 = new TerminarTarea(idTarea, params);
////             service2.execute();
////         } catch (Exception e) {
////             throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
////         }
////     }
//// }
//
////    /**
////     * (non-Javadoc)
////     * 
////     * @see com.asopagos.afiliaciones.personas.composite.service.AfiliacionPersonasCompositeService#verificarResultadosProductoNoConforme(com.asopagos.afiliaciones.personas.composite.dto.VerificarProductoNoConformeDTO,
////     *      com.asopagos.rest.security.dto.UserDTO)
////     */
////    //@Override
////    public void verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO inDTO, UserDTO userDTO) {
////
////        Integer resultadoBack = null;
////        boolean notificacionFallida = false;
////
////        ConsultarSolicitudAfiliacionPersona consultaSolAfi = new ConsultarSolicitudAfiliacionPersona(inDTO.getIdSolicitudGlobal());
////        consultaSolAfi.execute();
////        SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO = consultaSolAfi.getResult();
////
////        switch (inDTO.getResultadoAfiliado()) {
////            case NO_SUBSANABLE: {
////                // CASO A: Afiliado principal “No subsanado” y
////                // Beneficiarios “Subsanado” o “No subsanado”
////                gestionarSolicitudRechazada(inDTO, solicitudAfiliacionPersonaDTO);
////                resultadoBack = 1;
////                break;
////            }
////            case SUBSANABLE: {
////                //En caso de que el PNC sea subsanable pero el empleador esté inactivo se rechaza la solicitud
////                //Y se inactiva a afiliado y beneficiarios
////                if(Boolean.TRUE.equals(inDTO.getEmpleadorInactivo())) {
////                    gestionarSolicitudRechazada(inDTO, solicitudAfiliacionPersonaDTO);
////                    resultadoBack = 2;
////                    break;
////                }
////                
////                // CASO B: Afiliado principal “Subsanable” – Beneficiarios
////                // “Subsanable” o “No subsanable”
////
////                // Cambiar el estado de la solicitud a “No conforme en gestión”.
////                actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.APROBADA);
////
////                // TODO esta logica no se hace necesaria debido a que en los
////                // parametros de la invocacion viene ya el resultado de cada
////                // beneficiario
////                // notificacionFallida = guardarAfiliacionSubsanable(inDTO);
////
////                List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> lstResultadoGeneralBeneficiarios = inDTO
////                        .getResultadoBeneficiarios();
////                if (lstResultadoGeneralBeneficiarios != null && !lstResultadoGeneralBeneficiarios.isEmpty()) {
////                    for (ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO : lstResultadoGeneralBeneficiarios) {
////                        Long idBeneficiario = resultadoBeneficiarioDTO.getIdBeneficiario();
////                        ResultadoGeneralProductoNoConformeEnum resultadoBeneficiario = resultadoBeneficiarioDTO
////                                .getResultadoGeneralBeneficiario();
////                        if (ResultadoGeneralProductoNoConformeEnum.NO_SUBSANABLE.equals(resultadoBeneficiario)) {
////                            System.out.println(
////                                    "AfiliacionPersonasCompositeBusiness.verificarInformacionSolicitud :: Se efectúa la inactivación del beneficiario incluido en la solicitud que haya sido activado durante el proceso.");
////                            // Se efectúa la inactivación del beneficiario incluido
////                            // en la solicitud que haya sido activado durante el
////                            // proceso
////                            actualizarEstadoBeneficiario(idBeneficiario, EstadoAfiliadoEnum.INACTIVO, null);
////                            notificacionFallida = true;
////
////                        }
////                    }
////                }
////
////                if (notificacionFallida) {
////                    // enviarNotificacionRadicacionSolicitudAfiliacion();
////                }
////
////                // Cambiar de estado la solicitud a “Cerrada”.
////                actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
////                actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(), EstadoAfiliadoEnum.ACTIVO);
////                procesarAsociacionEntidadPagadora(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO(), userDTO);
////                resultadoBack = 2;
////                break;
////            }
////            default: {
////                throw new IllegalArgumentException("Dato invalido");
////            }
////        }
////
////        // estadoSolicitud:Integer(1-Rechazada,2-Cerrado)
////        Map<String, Object> params = new HashMap<>();
////        params.put("estadoSolicitud", resultadoBack);
////        try {
////            TerminarTarea service2 = new TerminarTarea(inDTO.getIdTarea(), params);
////            service2.execute();
////        } catch (Exception e) {
////            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
////        }
////    }
////
//    /**
//     * Procesa la inactivación de afiliado y beneficiarios.
//     * Rechaza la solicitud de afiliación 
//     * @param inDTO
//     * @param solicitudAfiliacionPersonaDTO
//     */
//    private void gestionarSolicitudRechazada(VerificarProductoNoConformeDTO inDTO,
//            SolicitudAfiliacionPersonaDTO solicitudAfiliacionPersonaDTO) {
//        guardarAfiliacionNoSubsanable(inDTO);
//
//        // (se debe registrar automáticamente novedad de inactivación de
//        // personas con motivo/causal: “anulación de afiliación” – Ver HU
//        // novedades personas – inactivación de beneficiarios).
//        // registrarNovedadInactivacionPersonas();
//
//        actualizarEstadoRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado(),
//                EstadoAfiliadoEnum.INACTIVO);
//
//        RolAfiliadoModeloDTO rolAfiliadoModeloDTO = new RolAfiliadoModeloDTO();
//        rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
//        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
//        afiliadoModeloDTO.setIdAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdAfiliado());
//        rolAfiliadoModeloDTO.setAfiliado(afiliadoModeloDTO);
//        rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
//        rolAfiliadoModeloDTO.setIdRolAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getIdRolAfiliado());
//        rolAfiliadoModeloDTO.setTipoAfiliado(solicitudAfiliacionPersonaDTO.getAfiliadoInDTO().getTipoAfiliado());
//        rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.AFILIACION_ANULADA);
//        ejecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de crear un afiliado a la
//     * caja de compenscion familiar indicado en <code>AfiliadoInDTO</code>
//     * 
//     * @param afiliadoInDTO
//     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
//     *        de un Afiliado
//     * 
//     * @return <code>AfiliadoInDTO</code> el DTO con los datos del afiliado
//     */
//    private AfiliadoInDTO crearAfiliado(AfiliadoInDTO afiliadoInDTO) {
//        System.out.println("Inicia crearAfiliado( AfiliadoInDTO )");
//        AfiliadoInDTO afiliadoDTO = new AfiliadoInDTO();
//        CrearAfiliado crearAfiliadoService;
//        crearAfiliadoService = new CrearAfiliado(afiliadoInDTO);
//        crearAfiliadoService.execute();
//        afiliadoDTO = (AfiliadoInDTO) crearAfiliadoService.getResult();
//        System.out.println("Finaliza crearAfiliado( AfiliadoInDTO )");
//        return afiliadoDTO;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de crear una Solicitud de
//     * Afiliacion de Persona indicado en <code>AfiliadoInDTO</code>
//     * 
//     * @param afiliadoInDTO
//     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
//     *        de un Afiliado
//     * 
//     * @return <code>Long</code> El identificador de la solicitud de afiliacion
//     *         de persona
//     */
//   // private Long crearSolicitudAfiliacionPersona(AfiliadoInDTO afiliadoInDTO) {
//   //     System.out.println("Inicia crearSolicitudAfiliacionPersona( AfiliadoInDTO )");
//   //     Long idSolicitud = new Long(0);
//   //     CrearSolicitudAfiliacionPersona crearSolicitudAfiliacionPersonaService;
//   //     crearSolicitudAfiliacionPersonaService = new CrearSolicitudAfiliacionPersona(afiliadoInDTO);
//   //     crearSolicitudAfiliacionPersonaService.execute();
//   //     idSolicitud = (Long) crearSolicitudAfiliacionPersonaService.getResult();
//   //     System.out.println("Finaliza crearSolicitudAfiliacionPersona( AfiliadoInDTO )");
//   //     return idSolicitud;
//   // }
//
//    /**
//     * Método que hace la peticion REST al servicio de iniciar el Proceso de
//     * Afiliacion de Persona en el BPM
//     * 
//     * @param idSolicitudGlobal
//     *        El identificador de la solicitud global de afiliacion de
//     *        persona
//     * @param numeroRadicado
//     *        Número de radicación de la solicitud
//     * @param usuarioDTO
//     *        DTO para el servicio de autenticación usuario
//     * 
//     * @return <code>Long</code> El identificador de la Instancia Proceso
//     *         Afiliacion de la Persona
//     */
//    private Long iniciarProcesoAfiliacionPersona(Long idSolicitudGlobal,String numeroRadicado, UserDTO userInDTO) {
//        System.out.println("Inicia iniciarProcesoAfiliacionPersona( idSolicitudGlobal )");
//        Long idInstanciaProcesoAfiliacionPersona = new Long(0);
//        // String tokenAccesoCore = generarTokenAccesoCore();
//        Map<String, Object> parametrosProceso = new HashMap<String, Object>();
//        
//        String tiempoProcesoSolicitud = (String) CacheManager
//                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_PROCESO_SOLICITUD);
//        String tiempoAsignacionBack = (String) CacheManager
//                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_ASIGNACION_BACK);
//        String tiempoSolicitudPendienteDocumentos = (String) CacheManager
//                .getParametro(ParametrosSistemaConstants.BPM_APP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS);
//        
//        parametrosProceso.put("idSolicitud", idSolicitudGlobal);
//        parametrosProceso.put("usuarioFront", userInDTO.getNombreUsuario());
//        parametrosProceso.put("tiempoAsignacionBack", tiempoAsignacionBack);
//        parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
//        parametrosProceso.put("tiempoSolicitudPendienteDocumentos", tiempoSolicitudPendienteDocumentos);
//        parametrosProceso.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
//        IniciarProceso iniciarProcesoPersonaService = new IniciarProceso(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL, parametrosProceso);
//        // iniciarProcesoPersonaService.setToken(tokenAccesoCore);
//        iniciarProcesoPersonaService.execute();
//        idInstanciaProcesoAfiliacionPersona = (Long) iniciarProcesoPersonaService.getResult();
//        System.out.println("Finaliza iniciarProcesoAfiliacionPersona( idSolicitudGlobal )");
//        return idInstanciaProcesoAfiliacionPersona;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar el
//     * Identificador Instancia del Proceso de Solicitud de afiliacion De Persona
//     * *
//     * 
//     * @param idSolicitudGlobal
//     *        <code>Long</code> El identificador de la solicitud global de
//     *        afiliacion de persona
//     * 
//     * @param idInstanciaProceso
//     *        <code>Long</code> El identificador de la Instancia Proceso
//     *        Afiliacion de la Persona
//     */
//    private void actualizarIdInstanciaProcesoSolicitudDePersona(Long idSolicitudGlobal, Long idInstanciaProceso) {
//        System.out.println("Inicia actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
//        GuardarInstanciaProceso guardarInstanciaProcesoService = new GuardarInstanciaProceso(idSolicitudGlobal,
//                String.valueOf(idInstanciaProceso));
//        guardarInstanciaProcesoService.execute();
//        System.out.println("Finaliza actualizarIdInstanciaProcesoSolicitudDePersona( idSolicitudGlobal )");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar el estado de
//     * una Solicitud de Afiliacion de Persona indicado en
//     * <code>EstadoSolicitudAfiliacionPersonaEnum</code>
//     * 
//     * @param idSolicitudGlobal
//     *        <code>Long</code> El identificador de la solicitud global de
//     *        afiliacion de persona
//     * @param estadoSolcitudAfiliacionPersona
//     *        <code>EstadoSolicitudAfiliacionPersonaEnum</code> Enumeración
//     *        que representa los estados de la solicitud de afiliación de
//     *        una persona
//     */
//    private void actualizarEstadoSolicitudPersona(Long idSolicitudGlobal,
//            EstadoSolicitudAfiliacionPersonaEnum estadoSolcitudAfiliacionPersona) {
//        System.out.println("Inicia actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
//        actualizarEstadoSolicitudAfiliacionPersona(
//                idSolicitudGlobal, estadoSolcitudAfiliacionPersona);
//        System.out.println("Finaliza actualizarEstadoSolicitudPersona( idSolicitudGlobal, EstadoSolicitudAfiliacionPersonaEnum )");
//    }
//	//@Override
//	public void actualizarEstadoSolicitudAfiliacionPersona(Long idSolicitudGlobal,
//			EstadoSolicitudAfiliacionPersonaEnum estado) {
//		System.out.println("Inicia actualizarEstadoSolicitudAfiliacionPersona(Long)");
//		try {
//			if (idSolicitudGlobal == null || estado == null) {
//				throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCOMPLETO);
//			} else {
//				Query q = entityManager.createQuery("SELECT solPer FROM SolicitudAfiliacionPersona solPer JOIN FETCH solPer.rolAfiliado JOIN FETCH solPer.solicitudGlobal where	solPer.solicitudGlobal.idSolicitud= :idSolicitudGlobal");
//				q.setParameter("idSolicitudGlobal", idSolicitudGlobal);
//				SolicitudAfiliacionPersona solicitudResult = (SolicitudAfiliacionPersona) q.getSingleResult();
//				/*
//				 * se verifica si el nuevo estado es CERRADA para actualizar el
//				 * resultado del proceso Mantis 0216650
//				 */
//				if (EstadoSolicitudAfiliacionPersonaEnum.CERRADA.equals(estado)
//						&& (EstadoSolicitudAfiliacionPersonaEnum.APROBADA.equals(solicitudResult.getEstadoSolicitud())
//								|| EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA
//										.equals(solicitudResult.getEstadoSolicitud())
//								|| EstadoSolicitudAfiliacionPersonaEnum.CANCELADA
//										.equals(solicitudResult.getEstadoSolicitud())
//								|| EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA
//										.equals(solicitudResult.getEstadoSolicitud()))) {
//					solicitudResult.getSolicitudGlobal().setResultadoProceso(
//							ResultadoProcesoEnum.valueOf(solicitudResult.getEstadoSolicitud().name()));
//				}
//				// Si el estado es DESISTIDA o REGISTRO_INTENTO_AFILIACION poner resultado del proceso en RECHAZADA
//				if (EstadoSolicitudAfiliacionPersonaEnum.DESISTIDA.equals(estado)
//						|| EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION.equals(estado)) {
//					solicitudResult.getSolicitudGlobal().setResultadoProceso(ResultadoProcesoEnum.RECHAZADA);
//				}
//
//				// Si se registra un intento de afiliacion persona, inactivar el rolAfiliado
//				if(EstadoSolicitudAfiliacionPersonaEnum.REGISTRO_INTENTO_AFILIACION.equals(estado)){
//					RolAfiliado rolAfiliado = entityManager.find(RolAfiliado.class, solicitudResult.getRolAfiliado().getIdRolAfiliado());
//					entityManager.merge(rolAfiliado);
//					rolAfiliado.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
//				}
//
//				solicitudResult.setEstadoSolicitud(estado);
//				entityManager.merge(solicitudResult);
//			System.out.println(
//						"Finaliza actualizarEstadoSolicitudAfiliacionPersona(Long,EstadoSolicitudAfiliacionPersonaEnum)");
//			}
//		} catch (Exception e) {
//			System.out.println("No es posible actualizar el estado de la solicitud safiliacion persona"+e);
//			System.out.println("Finaliza actualizarEstadoSolicitudAfiliacionPersona(Long)");
//		}
//	}
//
//    /**
//     * Método que hace la peticion REST al servicio de crear una Lista de
//     * Chequeo (requisitos) para una benficiario o afiliado indicado en
//     * <code>ListaChequeoDTO</code>
//     * 
//     * @param listaChequeo
//     *        <code>ListaChequeoDTO</code> DTO que transporta los datos la
//     *        lista de chequeo (requisitos documentales)
//     * 
//     * @return List<code>Long</code> una lista con los identificadores de cada
//     *         item de chequeo (requisito) creado
//     */
//    private List<Long> crearListaChequeoAfiliacionPersona(ListaChequeoDTO listaChequeo) {
//        System.out.println("Inicia crearListaChequeoAfiliacionPersona( ListaChequeoDTO )");
//        List<Long> lstResult = new ArrayList<Long>();
//       // GuardarListaChequeo crearListaChequeoService = new GuardarListaChequeo(listaChequeo);
//       // crearListaChequeoService.execute();
//       // lstResult = crearListaChequeoService.getResult();
//       // System.out.println("Finaliza crearListaChequeoAfiliacionPersona( ListaChequeoDTO )");
//       // return lstResult;
//   return null;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de buscar un afiliado
//     * 
//     * @param numeroIdentificacion
//     *        <code>String</code> Numero de identiticacion de un Afiliado
//     * 
//     * @return List <code>PersonaDTO</code> Lista de DTO's que transporta los
//     *         datos básicos de una persona
//     */
//    private List<PersonaDTO> buscarAfiliado(String numeroIdentificacion) {
//        System.out.println("Inicia buscarAfiliado( numeroIdentificacion )");
//        List<PersonaDTO> lstPersonaDTO = new ArrayList<PersonaDTO>();
//        BuscarAfiliados buscarAfiliadosService = new BuscarAfiliados(null, null, null, null, numeroIdentificacion, null);
//        buscarAfiliadosService.execute();
//        lstPersonaDTO = buscarAfiliadosService.getResult();
//        System.out.println("Finaliza buscarAfiliado( numeroIdentificacion )");
//        return lstPersonaDTO;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio que permite buscar una
//     * Solicitud
//     * 
//     * @param numeroRadicado
//     *        <code>String</code> El numero de radicado de la solicitud
//     * 
//     * @return List <code>SolicitudDTO</code> Lista de DTO's que transporta los
//     *         datos de una solcitud
//     */
//    private List<SolicitudDTO> buscarSolicitud(String numeroRadicado) {
//        System.out.println("Inicia buscarSolicitud( numeroRadicado )");
//        List<SolicitudDTO> lstSolicitudDTO = new ArrayList<SolicitudDTO>();
//        BuscarSolicitud buscarSolicitudService = new BuscarSolicitud(numeroRadicado);
//        buscarSolicitudService.execute();
//        lstSolicitudDTO = buscarSolicitudService.getResult();
//        System.out.println("Finaliza buscarSolicitud( numeroRadicado )");
//        return lstSolicitudDTO;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de consultar una Solicitud
//     * de Afiliacion de Persona
//     * 
//     * @param idSolicitudGlobal
//     *        <code>Long</code> El identificador de la solicitud global de
//     *        afiliacion de persona
//     * 
//     * @return <code>SolicitudAfiliacionPersonaDTO</code> DTOs que transporta
//     *         los datos de una solcitud de Afiliacion de Persona
//     */
//    private SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersona(Long idSolicitudGlobal) {
//        System.out.println("Inicia consultarSolicitudAfiliacionPersona( idSolicitudGlobal )");
//        SolicitudAfiliacionPersonaDTO solicitudAfiliDTO = new SolicitudAfiliacionPersonaDTO();
//       // ConsultarSolicitudAfiliacionPersona consultarSolAfilPersonaService = new ConsultarSolicitudAfiliacionPersona(idSolicitudGlobal);
//       // consultarSolAfilPersonaService.execute();
//        solicitudAfiliDTO = (SolicitudAfiliacionPersonaDTO) consultarSolicitudAfiliacionPersonas(idSolicitudGlobal);
//        System.out.println("Finaliza consultarSolicitudAfiliacionPersona( idSolicitudGlobal )");
//        return solicitudAfiliDTO;
//    }
//	//@Override
//	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//	public SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersonas(Long idSolicitudGlobal) {
//		System.out.println("Inicia consultarSolicitudAfiliacionPersona(Long)");
//		Query qSolicitudAfiliacion = entityManager.createQuery("SELECT solPer FROM SolicitudAfiliacionPersona solPer JOIN FETCH	solPer.rolAfiliado JOIN FETCH solPer.solicitudGlobal where	solPer.solicitudGlobal.idSolicitud= :idSolicitudGlobal");
//		qSolicitudAfiliacion.setParameter("idSolicitudGlobal", idSolicitudGlobal);
//		try {
//			SolicitudAfiliacionPersona solicitudAfiResult = (SolicitudAfiliacionPersona) qSolicitudAfiliacion
//					.getSingleResult();
//			SolicitudAfiliacionPersonaDTO solAfiPersonaDTO = new SolicitudAfiliacionPersonaDTO();
//			Query qSolicitud = entityManager.createQuery("SELECT sol	FROM Solicitud sol	where sol.idSolicitud= :idSolicitud");
//			qSolicitud.setParameter("idSolicitud", idSolicitudGlobal);
//			Solicitud solicitudResult = (Solicitud) qSolicitud.getSingleResult();
//			// Asignacion datos SolicitudAfiliacionPersonaDTO
//
//			solAfiPersonaDTO.setIdSolicitudGlobal(solicitudAfiResult.getSolicitudGlobal().getIdSolicitud());
//			solAfiPersonaDTO.setComentarioSolicitud(solicitudAfiResult.getSolicitudGlobal().getObservacion());
//			solAfiPersonaDTO.setIdSolicitudAfiliacionPersona(solicitudAfiResult.getIdSolicitudAfiliacionPersona());
//			EstadoSolicitudAfiliacionPersonaEnum estadoSolicitudEnum = solicitudAfiResult.getEstadoSolicitud();
//			solAfiPersonaDTO.setEstadoSolicitud(estadoSolicitudEnum);
//			// Consultar rol Afiliado por Id
//			Query qRolAfiliacion = entityManager.createQuery("SELECT rolA FROM RolAfiliado rolA join fetch rolA.afiliado left join fetch	rolA.empleador WHERE rolA.idRolAfiliado= :idRolAfiliado");
//			qRolAfiliacion.setParameter("idRolAfiliado", solicitudAfiResult.getRolAfiliado().getIdRolAfiliado());
//			RolAfiliado rolAfiliadoResult = (RolAfiliado) qRolAfiliacion.getSingleResult();
//			AfiliadoInDTO afiDTO = new AfiliadoInDTO();
//			if (rolAfiliadoResult != null) {
//				// Asignacion datos afiliacionDTO
//				// Consultar Afiliado
//				Query qAfiliado = entityManager.createQuery("SELECT afi	FROM Afiliado afi join fetch afi.persona where afi.idAfiliado= :idAfiliado");
//				qAfiliado.setParameter("idAfiliado", rolAfiliadoResult.getAfiliado().getIdAfiliado());
//				Afiliado afiliadoResult = (Afiliado) qAfiliado.getSingleResult();
//				PersonaDTO personaDTO = null;
//				if (afiliadoResult != null) {
//					// Consultar la persona
//					Query qPersona = entityManager.createNamedQuery("SELECT per	FROM Persona per LEFT JOIN FETCH per.ubicacionPrincipal ubi	where per.idPersona= :idPersona");
//					qPersona.setParameter("idPersona", afiliadoResult.getPersona().getIdPersona());
//					Persona personaResult = (Persona) qPersona.getSingleResult();
//
//					// Consultar la persona detalle
//
//					PersonaDetalle personaDetalleResult = null;
//					Query qPersonaDetalle = entityManager
//							.createNamedQuery("SELECT pdt	FROM PersonaDetalle pdt	WHERE pdt.idPersona= :idPersona");
//					qPersonaDetalle.setParameter("idPersona", personaResult.getIdPersona());
//					try {
//						personaDetalleResult = (PersonaDetalle) qPersonaDetalle.getSingleResult();
//
//					} catch (NoResultException nre) {
//						personaDetalleResult = null;
//					}
//
//					personaDTO = PersonaDTO.convertPersonaToDTO(personaResult, personaDetalleResult);
//					if (afiliadoResult.getIdAfiliado() != null) {
//						personaDTO.setIdAfiliado(afiliadoResult.getIdAfiliado());
//						afiDTO.setIdAfiliado(afiliadoResult.getIdAfiliado());
//					}
//					afiDTO.setPersona(personaDTO);
//				}
//				afiDTO.setTipoAfiliado(rolAfiliadoResult.getTipoAfiliado());
//				afiDTO.setIdRolAfiliado(rolAfiliadoResult.getIdRolAfiliado());
//				afiDTO.setCanalRecepcion(solicitudResult.getCanalRecepcion());
//				/* Se valida si el empleador existe o no */
//				if (rolAfiliadoResult.getEmpleador() != null) {
//					afiDTO.setIdEmpleador(rolAfiliadoResult.getEmpleador().getIdEmpleador());
//				}
//				afiDTO.setValorSalarioMesada(rolAfiliadoResult.getValorSalarioMesadaIngresos());
//			}
//			solAfiPersonaDTO.setAfiliadoInDTO(afiDTO);
//			solAfiPersonaDTO.setIdTipoSolicitante(solicitudAfiResult.getIdSolicitudAfiliacionPersona());
//			solAfiPersonaDTO.setTipoTransaccion(solicitudResult.getTipoTransaccion());
//			if (solicitudResult.getNumeroRadicacion() != null && !solicitudResult.getNumeroRadicacion().equals("")) {
//				solAfiPersonaDTO.setNumeroRadicacion(solicitudResult.getNumeroRadicacion());
//			}
//			if (solicitudResult.getIdInstanciaProceso() != null
//					&& !solicitudResult.getIdInstanciaProceso().equals("")) {
//				solAfiPersonaDTO.setIdInstanciaProceso(solicitudResult.getIdInstanciaProceso());
//			}
//			solAfiPersonaDTO.setClasificacion(solicitudResult.getClasificacion());
//			solAfiPersonaDTO.setMetodoEnvio(solicitudResult.getMetodoEnvio());
//			solAfiPersonaDTO.setUsuarioRadicacion(solicitudResult.getUsuarioRadicacion());
//			solAfiPersonaDTO.setFechaRadicacion(solicitudResult.getFechaRadicacion());
//			solAfiPersonaDTO.setTipoRadicacion(solicitudResult.getTipoRadicacion());
//			System.out.println("Finaliza consultarSolicitudAfiliacionPersona(Long)");
//
//			return solAfiPersonaDTO;
//		} catch (NoResultException e) {
//			System.out.println("Finaliza consultarSolicitudAfiliacionPersona");
//			return null;
//		} catch (Exception e) {
//			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
//		}
//	}
//    /**
//     * Método que hace la peticion REST al servicio de actualizar un Afiliado
//     * indicado en <code>AfiliadoInDTO</code>
//     * 
//     * @param afiliadoInDTO
//     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
//     *        de un Afiliado
//     */
//    private void actualizarAfiliado(AfiliadoInDTO afiliadoInDTO) {
//        System.out.println("Inicia actualizarAfiliado( PersonaDTO )");
//        ActualizarAfiliado actualizarAfiliadoService = new ActualizarAfiliado(afiliadoInDTO.getPersona().getIdAfiliado(), afiliadoInDTO);
//        actualizarAfiliadoService.execute();
//        System.out.println("Finaliza actualizarAfiliado( PersonaDTO )");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de obtener tarea activa para
//     * posteriomente finalizar el proceso de Afiliación personas presencial
//     * 
//     * @param idInstanciaProceso
//     *        <code>String</code> El identificador de la Instancia Proceso
//     *        Afiliacion de la Persona
//     * 
//     * @return <code>Long</code> El identificador de la tarea Activa
//     */
//    private Long consultarTareaAfiliacionPersonas(String idInstanciaProceso) {
//        System.out.println("Inicia consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
//        String idTarea = null;
//        try {
//            Map<String, Object> mapResult = new HashMap<String, Object>();
//            ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
//            obtenerTareaActivaService.execute();
//            mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
//            System.out.println("Finaliza consultarTareaAfiliacionPersonas( idSolicitudGlobal )");
//            idTarea = ((Integer) mapResult.get("idTarea")).toString();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
//        }
//        return new Long(idTarea);
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de registrar intento de
//     * afiliación indicado en <code>IntentoAfiliacionInDTO</code>
//     * 
//     * @param intentoAfiliacionInDTO
//     *        <code>IntentoAfiliacionInDTO</code> DTO para el registro de
//     *        intentos de afiliación
//     */
//    private void registrarIntentoAfiliacion(IntentoAfiliacionInDTO intentoAfiliacionInDTO) {
//        System.out.println("Inicia registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
//        RegistrarIntentoAfliliacion registrarIntentoAfliliacion = new RegistrarIntentoAfliliacion(intentoAfiliacionInDTO);
//        registrarIntentoAfliliacion.execute();
//        System.out.println("Finaliza registrarIntentoAfiliacion(IntentoAfiliacionInDTO)");
//    }
//
//    /**
//     * /** Método que hace la peticion REST al servicio de terminar tarea para
//     * finalizar el proceso de Afiliación personas presencial
//     * 
//     * @param idTarea
//     *        <code>Long</code> El identificador de la tarea Activa del
//     *        proceso de Afiliación
//     * @param cumpleRequisitosDocumentales
//     *        <code>Boolean</code> Si cumple o no los Requisitos
//     *        Documentales
//     * @param documentosFisicos
//     *        <code>Boolean</code> Si cumple o no los documentos Fisicos
//     */
//    private void terminarTareaAfiliacionPersonas(Long idTarea, Boolean cumpleRequisitosDocumentales, Boolean documentosFisicos) {
//        System.out.println("Inicia terminarTareaAfiliacionPersonas( idTarea, cumpleRequisitosDocumentales, documentosFisicos)");
//        Map<String, Object> params = new HashMap<>();
//        params.put("cumpleRequisitosDocumentales", cumpleRequisitosDocumentales);
//        params.put("documentosFisicos", documentosFisicos);
//        try {
//            TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
//            terminarTareaService.execute();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
//        }
//        System.out.println("Finaliza terminarTareaAfiliacionPersonas( idTarea, cumpleRequisitosDocumentales, documentosFisicos)");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de terminar tarea para
//     * finalizar el proceso de Radicacion Abreviada Afiliación personas
//     * presencial
//     * 
//     * @param idTarea
//     *        <code>Long</code> El identificador de la tarea Activa del
//     *        proceso de Afiliación
//     * @param tipoRadicacionSolcitud
//     *        <code>TipoRadicacionEnum</code> Enumeración que representa los
//     *        estados de documentación del afiliación
//     * @param incluyenBeneficiarios
//     *        <code>boolean</code> Si se incluyen o no beneficiarios
//     */
//    private void terminarTareaRadicacionAbreviadaAfiliacionPersonas(Long idTarea, TipoRadicacionEnum tipoRadicacionSolcitud,
//            boolean incluyenBeneficiarios, boolean intentoAfiliacion, boolean metodoEnvio, String numeroRadicado) {
//        System.out.println("Inicia terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
//        Map<String, Object> params = new HashMap<>();
//        int tipoRadicacion = 0;
//        if (TipoRadicacionEnum.COMPLETA.equals(tipoRadicacionSolcitud)) {
//            tipoRadicacion = 1;
//        }
//        else if (TipoRadicacionEnum.ABREVIADA.equals(tipoRadicacionSolcitud)) {
//            tipoRadicacion = 2;
//        }
//        params.put("radicacionCompleta", tipoRadicacion);
//        params.put("incluyeBeneficiarios", incluyenBeneficiarios);
//        params.put("registroIntento", intentoAfiliacion);
//        params.put("documentosFisicos", metodoEnvio);
//        params.put(NUMERO_RADICADO_SOLICITUD, numeroRadicado);
//        try {
//            TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
//            terminarTareaService.execute();
//        } catch (Exception e) {
//            System.out.println(" ERROR"+
//                    "Finaliza terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
//            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
//        }
//        System.out.println(
//                "Finaliza terminarTareaRadicacionAbreviadaAfiliacionPersonas( idTarea, tipoRadicacionSolcitud, incluyenBeneficiarios)");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de generar nuemro de
//     * radicado
//     * 
//     * @param idSolicitud
//     *        <code>Long</code> El identificador de la solicitud
//     * @param sedeCajaCompensacion
//     *        <code>String</code> El usuario del sistema
//     */
//    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
//        System.out.println("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
//        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
//        radicarSolicitudService.execute();
//        System.out.println("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
//        return radicarSolicitudService.getResult();
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar una Solicitud
//     * de Afiliacion de una Persona
//     * 
//     * @param idSolicitudGlobal
//     *        <code>Long</code> El identificador de la solicitud global de
//     *        afiliacion de persona
//     * @param solicitudDTO
//     *        <code>SolicitudDTO</code> DTO's que transporta los datos de
//     *        una solcitud
//     */
//    private void actualizarSolicitudAfiliacionPersona(Long idSolicitudGlobal, SolicitudDTO solicitudDTO) {
//        System.out.println("Inicia actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO)");
//       	   
//		actualizarSolicitudAfiliacionPersonaRutApo(idSolicitudGlobal, solicitudDTO, entityManager);
//
//	  // ActualizarSolicitudAfiliacionPersona actualizarSolicitudAfiliacionPersonaService = new ActualizarSolicitudAfiliacionPersona(
//      //         idSolicitudGlobal, solicitudDTO);
//      // actualizarSolicitudAfiliacionPersonaService.execute();
//        System.out.println("Finaliza actualizarSolicitudAfiliacionPersona( idSolicitudGlobal, SolicitudDTO)");
//    }
//public void actualizarSolicitudAfiliacionPersonaRutApo(Long idSolicitudGlobal, SolicitudDTO solicitudDTO, EntityManager entityManager) {
//        try {
//           System.out.println("Inicia rutina  actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
//            Solicitud solicitud = entityManager.find(Solicitud.class, idSolicitudGlobal);
//            if (solicitud != null) {
//                if (solicitudDTO.getCanalRecepcion() != null && !solicitudDTO.getCanalRecepcion().equals("")) {
//                    solicitud.setCanalRecepcion(solicitudDTO.getCanalRecepcion());
//                }
//                if (solicitudDTO.getIdInstanciaProceso() != null && !solicitudDTO.getIdInstanciaProceso().isEmpty()) {
//                    solicitud.setIdInstanciaProceso(solicitudDTO.getIdInstanciaProceso());
//                }
//                if (solicitudDTO.getEstadoDocumentacion() != null) {
//                    solicitud.setEstadoDocumentacion(solicitudDTO.getEstadoDocumentacion());
//                }
//                if (solicitudDTO.getMetodoEnvio() != null) {
//                    solicitud.setMetodoEnvio(solicitudDTO.getMetodoEnvio());
//                }
//                if (solicitudDTO.getIdCajaCorrespondencia() != null) {
//                    solicitud.setIdCajaCorrespondencia(solicitudDTO.getIdCajaCorrespondencia());
//                }
//                if (solicitudDTO.getTipoTransaccion() != null) {
//                    solicitud.setTipoTransaccion(solicitudDTO.getTipoTransaccion());
//                }
//                if (solicitudDTO.getClasificacion() != null) {
//                    solicitud.setClasificacion(solicitudDTO.getClasificacion());
//                }
//                if (solicitudDTO.getTipoRadicacion() != null) {
//                    solicitud.setTipoRadicacion(solicitudDTO.getTipoRadicacion());
//                }
//                if (solicitudDTO.getNumeroRadicacion() != null && !solicitudDTO.getNumeroRadicacion().isEmpty()) {
//                    solicitud.setNumeroRadicacion(solicitudDTO.getNumeroRadicacion());
//                }
//                if (solicitudDTO.getUsuarioRadicacion() != null && !solicitudDTO.getUsuarioRadicacion().isEmpty()) {
//                    solicitud.setUsuarioRadicacion(solicitudDTO.getUsuarioRadicacion());
//                }
//                if (solicitudDTO.getFechaRadicacion() != null) {
//                    solicitud.setFechaRadicacion(solicitudDTO.getFechaRadicacion());
//                }
//                if (solicitudDTO.getCiudadSedeRadicacion() != null
//                        && !solicitudDTO.getCiudadSedeRadicacion().isEmpty()) {
//                    solicitud.setCiudadSedeRadicacion(solicitudDTO.getCiudadSedeRadicacion());
//                }
//                if (solicitudDTO.getDestinatario() != null && !solicitudDTO.getDestinatario().isEmpty()) {
//                    solicitud.setDestinatario(solicitudDTO.getDestinatario());
//                }
//                if (solicitudDTO.getSedeDestinatario() != null && !solicitudDTO.getSedeDestinatario().isEmpty()) {
//                    solicitud.setSedeDestinatario(solicitudDTO.getSedeDestinatario());
//                }
//                if (solicitudDTO.getFechaCreacion() != null && !solicitudDTO.getFechaCreacion().equals("")) {
//                    solicitud.setFechaCreacion(solicitudDTO.getFechaCreacion());
//                }
//                if (solicitudDTO.getObservacion() != null && !solicitudDTO.getObservacion().isEmpty()) {
//                    if (solicitud.getObservacion() != null && solicitud.getObservacion().isEmpty()) {
//                        solicitud.setObservacion(solicitud.getObservacion().concat(solicitudDTO.getObservacion()));
//                    } else {
//                        solicitud.setObservacion(solicitudDTO.getObservacion());
//                    }
//
//                }
//                if (solicitudDTO.getCargaAfiliacionMultiple() != null
//                        && !solicitudDTO.getCargaAfiliacionMultiple().equals("")) {
//                    solicitud.setCargaAfiliacionMultiple(solicitudDTO.getCargaAfiliacionMultiple());
//                }
//
//                if (solicitudDTO.getAnulada() != null) {
//                    solicitud.setAnulada(solicitudDTO.getAnulada());
//                }
//                entityManager.merge(solicitud);
//            }
//            System.out.println("Finaliza rutina actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
//        } catch (Exception e) {
//            System.out.println("No es posible actualizar la solicitud de afiliacion persona"+ e);
//            System.out.println("Finaliza actualizarSolicitudAfiliacionPersona(Long,SolicitudDTO)");
//            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,e);
//        }
//    }
//    /**
//     * Método que hace la peticion REST al servicio de actualizar el estado de
//     * la documentacion Afiliacion
//     * 
//     * @param idSolicitudGlobal
//     *        <code>Long</code> El identificador de la solicitud global de
//     *        afiliacion de persona
//     * @param estado
//     *        <code>EstadoDocumentacionEnum</code> Enumeración que
//     *        representa los estados de documentación del afiliación
//     */
//    private void actualizarEstadoDocumentacionAfiliacion(Long idSolicitudGlobal, EstadoDocumentacionEnum estadoDocumentacion) {
//        System.out.println("Inicia actualizarEstadoDocumentacionAfiliacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
//        ActualizarEstadoDocumentacionAfiliacion actualizarEstDocAfilService = new ActualizarEstadoDocumentacionAfiliacion(idSolicitudGlobal,
//                estadoDocumentacion);
//        actualizarEstDocAfilService.execute();
//        System.out.println("Finaliza actualizarEstadoDocumentacionAfiliacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar la Gestion de
//     * Solicitud de Asociacion de la afiliado con la entidad pagadora
//     * 
//     * @param idEntidadPagadora
//     *        <code>Long</code> El identificador de la entidad pagadora
//     *        asociada
//     * @param solicitudes
//     *        List <code>SolicitudAsociacionPersonaEntidadPagadoraDTO</code>
//     *        Lista de DTO's que trasporta las Solicitud de Asociacion de la
//     *        afiliado con la entidad pagadora
//     * 
//     * @return <code>Long</code> El identificador de la Solicutd de Gestion del
//     *         afiliado con la entidad pagadora
//     */
//// private String actualizarGestionSolicitudesAsociacion(Long idEntidadPagadora,
////         List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes) {
////     System.out.println("Inicia actualizarGestionSolicitudesAsociacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
////     String soaConsecutivo = "";
////     ActualizarGestionSolicitudesAsociacion actGestionSolAsociacionService = new ActualizarGestionSolicitudesAsociacion(
////             idEntidadPagadora, solicitudes);
////     actGestionSolAsociacionService.execute();
////     soaConsecutivo = (String) actGestionSolAsociacionService.getResult();
////     System.out.println("Finaliza actualizarGestionSolicitudesAsociacion( idSolicitudGlobal, EstadoDocumentacionEnum )");
////     return soaConsecutivo;
////
//// }
////
//    /**
//     * Método que hace la peticion REST al servicio de calcular la Categoria del
//     * Afiliado indicado en <code>DatosBasicosIdentificacionDTO</code>
//     * 
//     * @param inDTO
//     *        <code>DatosBasicosIdentificacionDTO</code> DTO que trasnporta
//     *        los datos básicos de identificación de una persona
//     * 
//     * @return <code>CategoriaPersonaEnum</code> Enumeración que representa la
//     *         categoria de una persona
//     */
//    private void calcularCategoriaAfiliado(AfiliadoInDTO inDTO) {
//        System.out.println("Inicia calcularCategoriaAfiliado ( DatosBasicosIdentificacionDTO )");
//        
//        CategoriaDTO categoriaDTO = new CategoriaDTO();
//        categoriaDTO.setTipoIdentificacion(inDTO.getPersona().getTipoIdentificacion());
//        categoriaDTO.setNumeroIdentificacion(inDTO.getPersona().getNumeroIdentificacion());
//        categoriaDTO.setMotivoCambioCategoria(MotivoCambioCategoriaEnum.NUEVA_AFILIACION);
//        CalcularCategoriasAfiliado calcularCategoria = new CalcularCategoriasAfiliado(categoriaDTO);
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de crear asociacion del
//     * Afiliado con la Entidad Pagadora indicado en <code>AfiliadoInDTO</code>
//     * 
//     * @param inAfiliadoDTO
//     *        <code>AfiliadoInDTO</code> DTO que transporta los de ingreso
//     *        de un Afiliado
//     * 
//     * @return <code>Long</code> El identificador de la Solicitud de Asociacion
//     *         del Afiliado con la Entidad Pagadora
//     */
// // private Long crearAsociacionAfiliadoEntidadPagadora(AfiliadoInDTO inAfiliadoDTO) {
// //     System.out.println("Inicia crearAsociacionAfiliadoEntidadPagadora( inAfiliadoDTO )");
// //     Long idSolicutdAsociacionAfiliadoEntidadPagadora = new Long(0);
// //     CrearSolicitudAsociacionPersonaEntidadPagadora asociacionAfiliadoEntidadPagadoraService = new CrearSolicitudAsociacionPersonaEntidadPagadora(
// //             inAfiliadoDTO);
// //     asociacionAfiliadoEntidadPagadoraService.execute();
// //     idSolicutdAsociacionAfiliadoEntidadPagadora = (Long) asociacionAfiliadoEntidadPagadoraService.getResult();
// //     System.out.println("Finaliza crearAsociacionAfiliadoEntidadPagadora( inAfiliadoDTO )");
// //     return idSolicutdAsociacionAfiliadoEntidadPagadora;
// // }
//
//    /**
//     * Método que hace la peticion REST al servicio de obtener la lista de
//     * beneficiarios asociados a un afiliado
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * 
//     * @return List<BeneficiarioDTO> La lista de DTO's que transporta los datos
//     *         básicos de beneficiarios.
//     */
//    private List<BeneficiarioDTO> consultarBeneficiariosDeAfiliado(Long idAfiliado) {
//        System.out.println("Inicia consultarBeneficiariosDeAfiliado( idAfiliado )");
//        List<BeneficiarioDTO> lstBeneficiariosDTO;
//        ConsultarBeneficiarios consultarBeneficiariosService = new ConsultarBeneficiarios(idAfiliado, false);
//        consultarBeneficiariosService.execute();
//        lstBeneficiariosDTO = consultarBeneficiariosService.getResult();
//        if (lstBeneficiariosDTO == null) {
//            lstBeneficiariosDTO = new ArrayList<>();
//        }
//        System.out.println("Finaliza consultarBeneficiariosDeAfiliado( idAfiliado )");
//        return lstBeneficiariosDTO;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de registrar la informacion
//     * de un benenficiario tip conyuge asociado a un afiliado
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * @param inDTO
//     *        <code>IdentificacionUbicacionPersonaDTO</code> DTO que
//     *        transporta los datos de identificacion ubicacion persona
//     * 
//     * @return <code>Long</code> El identificador del beneficiario
//     */
//    private Long registrarInformacionBeneficiarioConyuge(Long idAfiliado, IdentificacionUbicacionPersonaDTO inDTO) {
//        System.out.println("Inicia registrarInformacionBeneficiarioConyuge(Long, IdentificacionUbicacionPersonaDTO )");
//        Long idBeneficiario = new Long(0);
//        RegistrarInformacionBeneficiarioConyugue registrarInfoBeneConyugeService = new RegistrarInformacionBeneficiarioConyugue(idAfiliado,
//                inDTO);
//        registrarInfoBeneConyugeService.execute();
//        idBeneficiario = (Long) registrarInfoBeneConyugeService.getResult();
//        System.out.println("Finaliza registrarInformacionBeneficiarioConyuge(Long, IdentificacionUbicacionPersonaDTO )");
//        return idBeneficiario;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de registrar la informacion
//     * de un benenficiario tip hijo o Padre asociado a un afiliado
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * @param inDTO
//     *        <code>BeneficiarioHijoPadreDTO</code> DTO que transporta los
//     *        datos básicos de un beneficiario hijo o padre
//     * 
//     * @return <code>Long</code> El identificador del beneficiario
//     */
//    private Long registrarInformacionBeneficiarioHijoOPadre(Long idAfiliado, BeneficiarioHijoPadreDTO inDTO) {
//        System.out.println("Inicia registrarInformacionBeneficiarioHijoOPadre(Long, BeneficiarioHijoPadreDTO )");
//        
//        Long idBeneficiario = new Long(0);
//        RegistrarInformacionBeneficiarioHijoPadre registrarInforBeneHijoPadreService = new RegistrarInformacionBeneficiarioHijoPadre(
//                idAfiliado, inDTO);
//        registrarInforBeneHijoPadreService.execute();
//        idBeneficiario = (Long) registrarInforBeneHijoPadreService.getResult();
//        System.out.println("Finaliza registrarInformacionBeneficiarioHijoOPadre(Long, BeneficiarioHijoPadreDTO )");
//        return idBeneficiario;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de consultar un usuario de
//     * caja de compensacion
//     * 
//     * @param nombreUsuarioCaja
//     *        <code>String</code> El nombre de usuario del funcionario de la
//     *        caja que realiza la consulta
//     * 
//     * @return <code>UsuarioCCF</code> DTO para el servicio de autenticación
//     *         usuario
//     */
//    private UsuarioCCF consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
//        System.out.println("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
//        UsuarioCCF usuarioCCF = new UsuarioCCF();
//        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
//                nombreUsuarioCaja, null, null, false);
//        obtenerDatosUsuariosCajaCompensacionService.execute();
//        usuarioCCF = (UsuarioCCF) obtenerDatosUsuariosCajaCompensacionService.getResult();
//        System.out.println("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
//        return usuarioCCF;
//    }
//
//    /**
//     * @param nombreUsuarioCaja
//     * @return
//     */
//    /**
//     * Método que hace la peticion REST al servicio de ejecutar asignacion
//     * 
//     * @param sedeCaja
//     *        <code>Long</code> el identificador del afiliado
//     * @param procesoEnum
//     *        <code>ProcesoEnum</code> el identificador del afiliado
//     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
//     *         caja
//     */
//    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
//        System.out.println("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
//        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
//        String nombreUsuarioCaja = "";
//        ejecutarAsignacion.execute();
//        System.out.println("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
//        nombreUsuarioCaja = (String) ejecutarAsignacion.getResult();
//        return nombreUsuarioCaja;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de obtener el grupo familiar
//     * asociados a un afiliado
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> el identificador del afiliado
//     * 
//     * @return List <code>GrupoFamiliarDTO</code> DTO que transporta los datos
//     *         de un grupo familiar
//     */
//    private List<GrupoFamiliarDTO> consultarGrupoFamiliar(Long idAfiliado) {
//        System.out.println("Inicia consultarGrupoFamiliar( idAfiliado )");
//        List<GrupoFamiliarDTO> lstGrupoFamiliarDTO = null;
//        ConsultarGrupoFamiliar consultarGrupoFamiliarService = new ConsultarGrupoFamiliar(idAfiliado);
//        consultarGrupoFamiliarService.execute();
//        lstGrupoFamiliarDTO = consultarGrupoFamiliarService.getResult();
//        System.out.println("Finaliza consultarGrupoFamiliar( idAfiliado )");
//        return lstGrupoFamiliarDTO;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de asociar un beneficiario
//     * al grupo familiar de un afiliado a la caja
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * @param idGrupoFamiliar
//     *        <code>Long</code> El Identificador del grupo familiar
//     * @param inDTO
//     *        <code>DatosBasicosIdentificacionDTO</code> DTO para los datos
//     *        básicos de identificación de una persona
//     * 
//     */
//    private boolean asociarBeneficiarioAGrupoFamiliar(Long idAfiliado, Long idGrupoFamiliar, DatosBasicosIdentificacionDTO inDTO) {
//        System.out.println("Inicia asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, DatosBasicosIdentificacionDTO )");
//        boolean success = true;
//        try {
//            AsociarBeneficiarioAGrupoFamiliar consultarGrupoFamiliarService = new AsociarBeneficiarioAGrupoFamiliar(idGrupoFamiliar,
//                    idAfiliado, inDTO);
//            consultarGrupoFamiliarService.execute();
//        } catch (Exception e) {
//            success = false;
//        }
//        System.out.println("Finaliza asociarBeneficiarioAGrupoFamiliar(idAfiliado, idGrupoFamiliar, DatosBasicosIdentificacionDTO )");
//        return success;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar un
//     * beneficiario
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * @param beneficiarioDTO
//     *        <code>BeneficiarioDTO</code> DTO que transporta los datos
//     *        básicos de un beneficiario
//     * 
//     * @return <code>Long</code> El identificador del Beneficiario
//     */
//    private Long actualizarBeneficiario(Long idAfiliado, BeneficiarioDTO beneficiarioDTO) {
//        System.out.println("Inicia actualizarBeneficiario( idAfiliado ,BeneficiarioDTO )");
//        Long idBeneficiario = new Long(0);
//        RegistrarBeneficiario registrarBeneficiarioService = new RegistrarBeneficiario(idAfiliado, beneficiarioDTO);
//        registrarBeneficiarioService.execute();
//        idBeneficiario = (Long) registrarBeneficiarioService.getResult();
//        System.out.println("Finaliza actualizarBeneficiario( idAfiliado ,BeneficiarioDTO ) ");
//        return idBeneficiario;
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar el estado de
//     * un rol afiliado
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del rol afiliado
//     * @param estadoAfiliadoEnum
//     * @param beneficiarioDTO
//     *        <code>EstadoAfiliadoEnum</code> enumeracion que indica el
//     *        nuevo estado del rol afiliado
//     */
//    private void actualizarEstadoRolAfiliado(Long idRolAfiliado, EstadoAfiliadoEnum estadoAfiliadoEnum) {
//        System.out.println("Inicia actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
//        ActualizarEstadoRolAfiliado actualizarEstadoRolAfiliado = new ActualizarEstadoRolAfiliado(idRolAfiliado, estadoAfiliadoEnum);
//        actualizarEstadoRolAfiliado.execute();
//        System.out.println("Finaliza actualizarEstadoRolAfiliado(Long, EstadoAfiliadoEnum)");
//    }
//
//    /**
//     * Método que hace la peticion REST al servicio de actualizar el estado de
//     * un beneficiario
//     * 
//     * @param idAfiliado
//     *        <code>Long</code> El identificador del afiliado
//     * @param estado
//     *        <code>EstadoAfiliadoEnum</code> enumeracion que indica el
//     *        nuevo estado del beneficiario
//     */
//    private void actualizarEstadoBeneficiario(Long idBeneficario, EstadoAfiliadoEnum estado,
//            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
//        System.out.println("Inicia actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
//        ActualizarEstadoBeneficiario actualizarEstadoBeneficario = new ActualizarEstadoBeneficiario(idBeneficario, motivoDesafiliacion,
//                estado);
//        actualizarEstadoBeneficario.execute();
//        System.out.println("Finaliza actualizarEstadoBeneficario(Long, EstadoAfiliadoEnum)");
//    }
//
//    /**
//     * Método que consulta los productos no conformes
//     * 
//     * @param idSolicitudAfiliacion
//     * @param resuelto
//     * @return
//     */
//    private List<ProductoNoConformeDTO> consultarProductosNoConforme(Long idSolicitudAfiliacion, Boolean resuelto) {
//        System.out.println("Inicia consultarProductosNoConforme(Long, Boolean)");
//        ConsultarProductosNoConforme consultarProductosNoConforme = new ConsultarProductosNoConforme(idSolicitudAfiliacion, resuelto);
//        consultarProductosNoConforme.execute();
//        List<ProductoNoConformeDTO> productos = consultarProductosNoConforme.getResult();
//        System.out.println("Finaliza consultarProductosNoConforme(Long, Boolean)");
//        return productos;
//    }
//
//    /**
//     * Método con la lógica para afiliar un afilido subsanble con sus
//     * beneficiarios subsanados para el <strong>CASO B: Afiliado principal
//     * “Subsanable” – Beneficiarios “Subsanable” o “No subsanable”</strong><br>
//     * usado por los métodos:
//     * <ul>
//     * <li>
//     * {@link #afiliarAfiliadoPrincipal(VerificarProductoNoConformeDTO, UserDTO)}
//     * (HU-122 primera revisión back)</li>
//     * <li>
//     * {@link #verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO, UserDTO)}
//     * (HU-116 segunga revisión back)</li>
//     * </ul>
//     * 
//     * @param inDTO
//     * @return true si hubo algún beneficiario no subsanado, de lo contrario
//     *         false
//     */
//    private Boolean guardarAfiliacionSubsanable(VerificarProductoNoConformeDTO inDTO) {
//        // CASO B: Afiliado principal “Subsanable” – Beneficiarios “Subsanable”
//        // o “No subsanable”
//        // TODO verificar de que depende este valor
//        Boolean resultado = false;
//        // Para los beneficiarios asociados a la solicitud radicada que presenta
//        // producto no conforme no subsanable, se les debe cambiar el valor del
//        // campo “Estado con respecto al afiliado principal” a “Inactivo”
//        List<ProductoNoConformeDTO> productosNoConformes = consultarProductosNoConforme(inDTO.getIdSolicitudGlobal(), resultado);
//        Map<Long, ProductoNoConformeDTO> beneficiarioProductoNoConforme = new HashMap<Long, ProductoNoConformeDTO>();
//        for (ProductoNoConformeDTO pNoConforme : productosNoConformes) {
//            beneficiarioProductoNoConforme.put(pNoConforme.getIdBeneficiario(), pNoConforme);
//        }
//
//        boolean result = false;
//        ProductoNoConformeDTO productoNoConformeDTO;
//        ResultadoGestionProductoNoConformeSubsanableEnum resultadoRevision;
//
//        List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(inDTO.getIdAfiliado());
//        for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
//            if (beneficiarioProductoNoConforme.containsKey(beneficiarioDTO.getIdBeneficiario())) {
//                productoNoConformeDTO = beneficiarioProductoNoConforme.get(beneficiarioDTO.getIdBeneficiario());
//                if (ResultadoGestionProductoNoConformeSubsanableEnum.NO_SUBSANABLE
//                        .equals(productoNoConformeDTO.getResultadoRevisionBack2())) {
//                    actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, null);
//                    result = true;
//                }
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * Método con la lógica para continua el proceso de un afilido no subsanble
//     * con sus beneficiarios para el <strong>CASO A: Afiliado principal “No
//     * subsanable” – Beneficiarios “Subsanable” o “No subsanable”</strong><br>
//     * usado por los métodos:
//     * <ul>
//     * <li>
//     * {@link #afiliarAfiliadoPrincipal(VerificarProductoNoConformeDTO, UserDTO)}
//     * (HU-122 primera revisión back)</li>
//     * <li>
//     * {@link #verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO, UserDTO)}
//     * (HU-116 segunga revisión back)</li>
//     * </ul>
//     * 
//     * @param inDTO
//     */
//    private void guardarAfiliacionNoSubsanable(VerificarProductoNoConformeDTO inDTO) {
//        // CASO A: Afiliado principal “No subsanable” – Beneficiarios
//        // “Subsanable” o “No subsanable”
//
//        // Cambiar el estado de la solicitud a “Rechazada”.
//        actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.RECHAZADA);
//
//        // Desafiliar al afiliado principal de la solicitud (en esta
//        // instancia de afiliación).
//        actualizarEstadoRolAfiliado(inDTO.getIdRolAfiliado(), EstadoAfiliadoEnum.INACTIVO);
//
//        // Para los beneficiarios asociados a la solicitud radicada se debe
//        // cambiar el valor del campo “Estado con respecto al afiliado
//        // principal” a “Inactivo”.
//        List<BeneficiarioDTO> beneficiarios = consultarBeneficiariosDeAfiliado(inDTO.getIdAfiliado());
//        for (BeneficiarioDTO beneficiarioDTO : beneficiarios) {
//            
//            //Solo se deben inactivar los benefiarios asociado a ese rolAfiliado
//            if(inDTO.getIdRolAfiliado()!= null && beneficiarioDTO.getIdRolAfiliado() != null
//                    && inDTO.getIdRolAfiliado().equals(beneficiarioDTO.getIdRolAfiliado())) {
//
//                actualizarEstadoBeneficiario(beneficiarioDTO.getIdBeneficiario(), EstadoAfiliadoEnum.INACTIVO, null);
//            }
//            
//        }
//
//        actualizarEstadoSolicitudPersona(inDTO.getIdSolicitudGlobal(), EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
//
//        // Enviar notificación de rechazo de solicitud al front (en caso de que
//        // la caja decida enviar el comunicado, ejecutar el comunicado “003”
//        // “Notificación de radicación de solicitud de afiliación de personas” -
//        // HU-TRA-331 Editar comunicados).
//        // enviarNotificacionRadicacionSolicitudAfiliacion();
//
//    }
//
//    /**
//     * Realiza la petición REST al servicio que actualiza el rol afiliado
//     * @param rolAfiliadoModeloDTO
//     *        Información del rolAfiliado a actualizar
//     */
//    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
//        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
//        actualizarRolAfiliado.execute();
//    }
//
//    /**
//     * Este metodo permite la generacion de un nuevo token para invocacion entre
//     * servicios externos
//     * 
//     * @return token <code>String</code> token generado
//     */
//    private String generarTokenAccesoCore() {
//        GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
//        accesoCore.execute();
//        TokenDTO token = accesoCore.getResult();
//        return token.getToken();
//    }
//
//    /**
//     * Este método permite consultar una solicitud global por id
//     */
//    private Solicitud buscarSolicitudPorId(Long idSolicitudAsociacionAfiliadoEntidadPagadora) {
//        System.out.println("Inicia buscarSolicitudPorId(Long)");
//        Solicitud solGlobal = new Solicitud();
//        BuscarSolicitudPorId buscarSolicitudPorId = new BuscarSolicitudPorId(idSolicitudAsociacionAfiliadoEntidadPagadora);
//        buscarSolicitudPorId.execute();
//        solGlobal = (Solicitud) buscarSolicitudPorId.getResult();
//        System.out.println("Finaliza buscarSolicitudPorId(Long)");
//        return solGlobal;
//    }
//
//    /**
//     * Este método permite consultar un empleador por el id del empleador
//     */
//    private Empleador buscarEmpleador(Long idEmpleador) {
//        System.out.println("Inicia buscarEmpleador(Long idEmpleador)");
//        Empleador empleador = new Empleador();
//        ConsultarEmpleador consultarEmple = new ConsultarEmpleador(idEmpleador);
//        consultarEmple.execute();
//        empleador = (Empleador) consultarEmple.getResult();
//        System.out.println("Finaliza buscarEmpleador(Long idEmpleador)");
//        return empleador;
//    }
//
//    /**
//     * Metodo que se encarga de filtrar las validaciones
//     * @param validacion
//     * @param lista
//     * @return
//     */
//    private ValidacionDTO getValidacion(ValidacionCoreEnum validacion, List<ValidacionDTO> lista) {
//        for (ValidacionDTO validacionAfiliacionDTO : lista) {
//            if (validacionAfiliacionDTO.getValidacion().equals(validacion)) {
//                return validacionAfiliacionDTO;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Método encargado de llamar el cliente que genera el retiro de trabajadores y sus beneficiarios mediante novedades
//     * @param rolAfiliadoModeloDTO,
//     *        rol Afiliado Modelo DTO
//     */
//    private void ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
//        System.out.println("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
//        EjecutarRetiroTrabajadores ejecucionRetiro = new EjecutarRetiroTrabajadores(rolAfiliadoModeloDTO);
//        ejecucionRetiro.execute();
//        System.out.println("Inicio de método ejecutarRetiroTrabajadores(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)");
//    }
//    
//    /**
//     * Ejecuta el proceso de creacion de la solicitud de asociación de persona en entidad pagadora
//     * @param afiliadoInDTO
//     *        Informacion Afiliado asociar
//     * @param userDTO
//     *        Informacion usuario registro
//     */
//  // private void procesarAsociacionEntidadPagadora(AfiliadoInDTO afiliadoInDTO, UserDTO userDTO) {
//  //     Long idSolicitudAsociacionAfiliadoEntidadPagadora = null;
//  //     SolicitudAsociacionPersonaEntidadPagadoraDTO solAsoPerEntPagadoraDTO = null;
//  //     List<SolicitudAsociacionPersonaEntidadPagadoraDTO> lstSolAsoPerEntPagadoraDTO = null;
//
//  //     ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(afiliadoInDTO.getIdRolAfiliado());
//  //     consultarRolAfiliado.execute();
//  //     RolAfiliadoModeloDTO rolAfiliadoModeloDTO = consultarRolAfiliado.getResult();
//
//  //     if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(afiliadoInDTO.getTipoAfiliado())
//  //             || TipoAfiliadoEnum.PENSIONADO.equals(afiliadoInDTO.getTipoAfiliado())) {
//  //         /*
//  //          * si se encuentra que existe información en el campo
//  //          * “Entidad pagadora de aportes de pensionado” es
//  //          * necesario que el sistema realice la asociación con la
//  //          * entidad pagadora correspondiente (Ver HU-121-109
//  //          * Gestionar personas en entidad pagadora)
//  //          */
//  //         if (rolAfiliadoModeloDTO != null && rolAfiliadoModeloDTO.getPagadorAportes() != null
//  //                 && rolAfiliadoModeloDTO.getPagadorAportes().getIdEntidadPagadora() != null) {
//  //             idSolicitudAsociacionAfiliadoEntidadPagadora = crearAsociacionAfiliadoEntidadPagadora(afiliadoInDTO);
//  //             if (idSolicitudAsociacionAfiliadoEntidadPagadora != null) {
//  //                 System.out.println(
//  //                         "AfiliacionPersonasCompositeBusiness.procesarAsociacionEntidadPagadora :: genera el numero de radicado correspondiente y lo actualiza en relacion en la solicitud de asociacion del afiliado y la entidad pagadora");
//  //                 // Ggenera el numero de radicado correspondiente y lo actualiza en
//  //                 // relacion en la solicitud de asociacion del afiliado y la entidad pagadora
//  //                 generarNumeroRadicado(idSolicitudAsociacionAfiliadoEntidadPagadora, userDTO.getSedeCajaCompensacion());
//
//  //                 Solicitud solGlobal = buscarSolicitudPorId(idSolicitudAsociacionAfiliadoEntidadPagadora);
//
//  //                 solAsoPerEntPagadoraDTO = new SolicitudAsociacionPersonaEntidadPagadoraDTO();
//  //                 solAsoPerEntPagadoraDTO.setEstadoSolicitud(EstadoSolicitudPersonaEntidadPagadoraEnum.PENDIENTE_SOLICITAR_ALTA);
//  //                 solAsoPerEntPagadoraDTO.setNumeroRadicado(solGlobal.getNumeroRadicacion());
//  //                 solAsoPerEntPagadoraDTO.setIdSolicitudGlobal(solGlobal.getIdSolicitud());
//  //                 solAsoPerEntPagadoraDTO.setTipoGestion(TipoGestionSolicitudAsociacionEnum.PENDIENTE_SOLICITAR_ALTA);
//
//  //                 lstSolAsoPerEntPagadoraDTO = new ArrayList<>();
//  //                 lstSolAsoPerEntPagadoraDTO.add(solAsoPerEntPagadoraDTO);
//  //                 actualizarGestionSolicitudesAsociacion(rolAfiliadoModeloDTO.getPagadorAportes().getIdEntidadPagadora(),
//  //                         lstSolAsoPerEntPagadoraDTO);
//  //             }
//  //         }
//  //         /*
//  //          * Se reconoce aportes y novedades del afiliado, se verifica
//  //          * si se han registrado aportes para el periodo de
//  //          * afiliación y se habilita la prestación de servicios, si
//  //          * aplica. Ver HU-xxx-119 Reconocer aportes y novedades de
//  //          * afiliado
//  //          * 
//  //          * <<TODO 121-119 SE DESARROLLARA SPRINT 5>>
//  //          */
//  //     }
//  //     else if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(afiliadoInDTO.getTipoAfiliado())) {
//  //         /*
//  //          * o Se reconoce aportes y novedades del afiliado y se
//  //          * activan los servicios, si aplica HU-xxx-119 Reconocer
//  //          * aportes y novedades de afiliado
//  //          * 
//  //          * <<TODO 121-119 SE DESARROLLARA SPRINT 5>>
//  //          */
//  //     }
//   // }
//    
//
//    /**
//     * Método que hace la peticion REST al servicio procesarInactivacionBeneficiario de NovedadesCompositeBusiness
//     * @param resultadoBeneficiarioDTO
//     * @param estado
//     * @param motivoDesafiliacion
//     */
//    private void registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO resultadoBeneficiarioDTO , EstadoAfiliadoEnum estado,
//            MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
//        System.out.println("Inicia registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO, EstadoAfiliadoEnum,MotivoDesafiliacionBeneficiarioEnum)");
//       ;
//        
//       ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(resultadoBeneficiarioDTO.getIdBeneficiario());
//       consultarBeneficiario.execute();
//       
//        BeneficiarioModeloDTO beneficiarioDto = consultarBeneficiario.getResult();
//
//        SolicitudNovedadDTO inactivacionNovedadDTO = new SolicitudNovedadDTO();
//
//        // Datos del beneficiario a inactivar
//        DatosPersonaNovedadDTO personaDTO = new DatosPersonaNovedadDTO();
//        
//        personaDTO.setMotivoDesafiliacionBeneficiario(motivoDesafiliacion);
//        llenarDatosBeneficiarioNovedad(beneficiarioDto, personaDTO);
//
//        inactivacionNovedadDTO.setDatosPersona(personaDTO);
//        inactivacionNovedadDTO.setClasificacion(beneficiarioDto.getTipoBeneficiario());
//        
//        TipoTransaccionEnum tipoTransaccionBeneficiario= 
//                obtenerTipoTransaccionBeneficiario(beneficiarioDto.getTipoBeneficiario());
//        inactivacionNovedadDTO.setTipoTransaccion(tipoTransaccionBeneficiario);
//        inactivacionNovedadDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL_INT);
//        RadicarSolicitudNovedadAutomaticaSinValidaciones registrarNovedadAutomatica = new RadicarSolicitudNovedadAutomaticaSinValidaciones(inactivacionNovedadDTO);
//        registrarNovedadAutomatica.execute();
//        
//        System.out.println("Finaliza registrarNovedadInactivacion(ResultadoGeneralProductoNoConformeBeneficiarioDTO, EstadoAfiliadoEnum,MotivoDesafiliacionBeneficiarioEnum)");
//    }
//    
//
//    /**
//     * Transforma la información básica del beneficiario para el registro de la novedad por supervivencia
//     * @param beneficiarioModeloDTO
//     *        Información beneficiario
//     * @param persona
//     *        Modelo para el registro de novedad
//     */
//    private void llenarDatosBeneficiarioNovedad(BeneficiarioModeloDTO beneficiarioModeloDTO, DatosPersonaNovedadDTO persona) {
//        // Info Beneficiario
//        persona.setIdBeneficiario(beneficiarioModeloDTO.getIdBeneficiario());
//        persona.setTipoIdentificacionBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion());
//        persona.setNumeroIdentificacionBeneficiario(beneficiarioModeloDTO.getNumeroIdentificacion());
//        persona.setTipoIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getTipoIdentificacion());
//        persona.setNumeroIdentificacionBeneficiarioAnterior(beneficiarioModeloDTO.getNumeroIdentificacion());
//        persona.setPrimerApellidoBeneficiario(beneficiarioModeloDTO.getPrimerApellido());
//        persona.setSegundoApellidoBeneficiario(beneficiarioModeloDTO.getSegundoApellido());
//        persona.setPrimerNombreBeneficiario(beneficiarioModeloDTO.getPrimerNombre());
//        persona.setSegundoNombreBeneficiario(beneficiarioModeloDTO.getSegundoNombre());
//        // Info Afiliado
//        persona.setTipoIdentificacion(beneficiarioModeloDTO.getAfiliado().getTipoIdentificacion());
//        persona.setNumeroIdentificacion(beneficiarioModeloDTO.getAfiliado().getNumeroIdentificacion());
//        persona.setTipoIdentificacionTrabajador(beneficiarioModeloDTO.getAfiliado().getTipoIdentificacion());
//        persona.setNumeroIdentificacionTrabajador(beneficiarioModeloDTO.getAfiliado().getNumeroIdentificacion());
//    }
//    
//    
//    /**
//     * Entrega el tipo de transaccion para inactivacion del beneficario
//     * @param clasificacion
//     * @return
//     */
//    private TipoTransaccionEnum obtenerTipoTransaccionBeneficiario(ClasificacionEnum clasificacion) {
//        TipoTransaccionEnum tipoTransaccionResult = null;
//        switch (clasificacion) {
//        case CONYUGE:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL;
//            break;
//        case PADRE:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL;
//            break;
//        case MADRE:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL;
//            break;
//        case HIJO_BIOLOGICO:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL;
//            break;
//        case HIJASTRO:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL;
//            break;
//        case HERMANO_HUERFANO_DE_PADRES:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL;
//            break;
//        case HIJO_ADOPTIVO:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL;
//            break;
//        case BENEFICIARIO_EN_CUSTODIA:
//            tipoTransaccionResult = TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL;
//            break;
//        default:
//            break;
//        }
//        return tipoTransaccionResult;
//    }
//
//    //@Override
//    public void terminarTareaAfiliacionPersonasIntento(Long idTarea, IntentoAfiliacionInDTO intentoAfiliacionInDTO, UserDTO userDTO) {
//        System.out.println("Inicia terminarTareaAfiliacionPersonasIntento( idTarea, userDTO)");              
//        
//        intentoAfiliacionInDTO.setFechaInicioProceso(new Date());
//        registrarIntentoAfiliacion(intentoAfiliacionInDTO);
//        
//        Map<String, Object> params = new HashMap<>();
//        params.put("registroIntento", true);
//        TerminarTarea terminarTareaService = new TerminarTarea(idTarea, params);
//        terminarTareaService.execute();
//        System.out.println("Finaliza terminarTareaAfiliacionPersonas( idTarea, userDTO)");
//        
//    }
//	/**fin ajuste */
}
