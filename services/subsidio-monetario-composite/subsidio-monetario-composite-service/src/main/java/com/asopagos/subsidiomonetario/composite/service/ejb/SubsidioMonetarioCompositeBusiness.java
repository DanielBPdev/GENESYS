package com.asopagos.subsidiomonetario.composite.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.clienteanibol.clients.AbonarSaldoTarjetas;
import com.asopagos.clienteanibol.clients.ConsultarEstadoProcesamiento;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoValidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.entidaddescuento.clients.ActualizarArchivosDescuentoLiquidacionCancelada;
import com.asopagos.entidaddescuento.clients.CrearRegistroArchivoSalidaDescuento;
import com.asopagos.entidaddescuento.clients.GenerarResultadosArchivoDescuento;
import com.asopagos.entidaddescuento.clients.ObtenerEntidadesDescuentoRadicacion;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.RazonRechazoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionFallidaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoSolicitudAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoOperacionAnibolEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.ConsultarPersona;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.clients.ActualizarDesembolsoSubsidioLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ActualizarEstadoDerechoLiquidacion;
import com.asopagos.subsidiomonetario.clients.ActualizarEstadoSolicitudLiquidacion;
import com.asopagos.subsidiomonetario.clients.ActualizarFechaDispersion;
import com.asopagos.subsidiomonetario.clients.ActualizarInstanciaSolicitudGlobal;
import com.asopagos.subsidiomonetario.clients.AprobarLiquidacionMasivaPrimerNivel;
import com.asopagos.subsidiomonetario.clients.AprobarLiquidacionMasivaSegundoNivel;
import com.asopagos.subsidiomonetario.clients.CancelarMasivaActualizarObservacionesProceso;
import com.asopagos.subsidiomonetario.clients.CancelarProcesoLiquidacion;
import com.asopagos.subsidiomonetario.clients.ConfirmarAfiliadoLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConfirmarBeneficiarioLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConfirmarLiquidacionFallecimientoAporteMinimo;
import com.asopagos.subsidiomonetario.clients.ConsolidarSubsidiosFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConsultarArchivosLiquidacion;
import com.asopagos.subsidiomonetario.clients.ConsultarArchivosLiquidacionPorId;
import com.asopagos.subsidiomonetario.clients.ConsultarCondicionPersonaRadicacion;
import com.asopagos.subsidiomonetario.clients.ConsultarCondicionesPersonas;
import com.asopagos.subsidiomonetario.clients.ConsultarIdSolicitud;
import com.asopagos.subsidiomonetario.clients.ConsultarIdentificadorConjuntoValidacion;
import com.asopagos.subsidiomonetario.clients.ConsultarIdentificadorPersonaCore;
import com.asopagos.subsidiomonetario.clients.ConsultarPeriodoRegularLiquidacionPorRadicado;
import com.asopagos.subsidiomonetario.clients.ConsultarResultadoLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConsultarResultadosLiquidacionGestionAporteMinimo;
import com.asopagos.subsidiomonetario.clients.ConsultarSolicitudLiquidacion;
import com.asopagos.subsidiomonetario.clients.ConsultarValidacionAporteMinimoFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConsultarValidacionFallidaPersonaFallecimiento;
import com.asopagos.subsidiomonetario.clients.ConsultarValidacionesTipoProceso;
import com.asopagos.subsidiomonetario.clients.EjecutarLiquidacionMasiva;
import com.asopagos.subsidiomonetario.clients.EjecutarSPGestionarColaEjecucionLiquidacion;
import com.asopagos.subsidiomonetario.clients.EjecutarSPLiquidacionEspecifica;
import com.asopagos.subsidiomonetario.clients.EjecutarSPLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.EjecutarSPLiquidacionFallecimientoGestionPersona;
import com.asopagos.subsidiomonetario.clients.EjecutarSPLiquidacionReconocimiento;
import com.asopagos.subsidiomonetario.clients.EliminarLiquidacionSP;
import com.asopagos.subsidiomonetario.clients.EliminarMarcaAprobacionSegNivel;
import com.asopagos.subsidiomonetario.clients.EnviarResultadoLiquidacionAPagos;
import com.asopagos.subsidiomonetario.clients.EnviarResultadoLiquidacionAPagosFallecimiento;
import com.asopagos.subsidiomonetario.clients.GenerarArchivoResultadoLiquidacion;
import com.asopagos.subsidiomonetario.clients.GenerarArchivoResultadoPersonasSinDerecho;
import com.asopagos.subsidiomonetario.clients.GenerarNuevoPeriodoL;
import com.asopagos.subsidiomonetario.clients.GestionarArchivosLiquidacion;
import com.asopagos.subsidiomonetario.clients.GuardarCondicionesEspecialesReconocimiento;
import com.asopagos.subsidiomonetario.clients.GuardarLiquidacionEspecifica;
import com.asopagos.subsidiomonetario.clients.GuardarPeriodosLiquidacion;
import com.asopagos.subsidiomonetario.clients.GuardarPersonasLiquidacionEspecifica;
import com.asopagos.subsidiomonetario.clients.InicializarPantallaSolicitudLiquidacion;
import com.asopagos.subsidiomonetario.clients.IniciarLiquidacionMasiva;
import com.asopagos.subsidiomonetario.clients.IniciarPorcentajeAvanceProcesoLiquidacion;
import com.asopagos.subsidiomonetario.clients.ObtenerTrazabilidadSubsidioEspecifico;
import com.asopagos.subsidiomonetario.clients.PersistirLiquidacionMasiva;
import com.asopagos.subsidiomonetario.clients.RechazarLiquidacionMasivaPrimerNivel;
import com.asopagos.subsidiomonetario.clients.RechazarLiquidacionMasivaSegundoNivel;
import com.asopagos.subsidiomonetario.clients.RegistrarAplicacionValidacionSubsidio;
import com.asopagos.subsidiomonetario.clients.RegistrarAplicacionValidacionSubsidioPersona;
import com.asopagos.subsidiomonetario.clients.SeleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.SeleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento;
import com.asopagos.subsidiomonetario.clients.ValidarMarcaAprobacionSegNivel;
import com.asopagos.subsidiomonetario.clients.VerificarEstructuraArchivoBloquedoCM;
import com.asopagos.subsidiomonetario.clients.VerificarExistenciaPeriodo;
import com.asopagos.subsidiomonetario.clients.VerificarLiquidacionEnProceso;
import com.asopagos.subsidiomonetario.clients.VerificarLiquidacionFallecimientoEnProceso;
import com.asopagos.subsidiomonetario.clients.VerificarPersonasSinCondiciones;
import com.asopagos.subsidiomonetario.clients.VerificarPersonasSinCondicionesAprobarResultados;
import com.asopagos.subsidiomonetario.clients.ConsultarLiquidacionMasivaEnProceso;
import com.asopagos.subsidiomonetario.composite.business.interfaces.IEnvioComunicados;
import com.asopagos.subsidiomonetario.composite.constants.ActividadRealizadaSubsidioEspecificoEnum;
import com.asopagos.subsidiomonetario.composite.constants.ResultadoTarjetaAnibolEnum;
import com.asopagos.subsidiomonetario.composite.constants.SubsidioMonetarioCompositeConstants;
import com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioAsincronoCompositeService;
import com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.pagos.clients.ActualizarEstadoSolicitudAnibol;
import com.asopagos.subsidiomonetario.pagos.clients.ActualizarRegistroSolicitudAnibol;
import com.asopagos.subsidiomonetario.pagos.clients.ConsultarCuentaAdminSubsidioPorSolicitud;
import com.asopagos.subsidiomonetario.pagos.clients.ConsultarCuentasAdministradorMedioTarjeta;
import com.asopagos.subsidiomonetario.pagos.clients.ConsultarRegistroSolicitudAnibol;
import com.asopagos.subsidiomonetario.pagos.clients.DispersarMasivoPagosEstadoAplicado;
import com.asopagos.subsidiomonetario.pagos.clients.DispersarPagosEstadoAplicado;
import com.asopagos.subsidiomonetario.pagos.clients.DispersarPagosEstadoEnviado;
import com.asopagos.subsidiomonetario.pagos.clients.GestionarTransaccionesFallidas;
import com.asopagos.subsidiomonetario.pagos.clients.RegistrarSolicitudAnibol;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroSolicitudAnibolModeloDTO;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActivaInstancia;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.clients.ObtenerTokenAcceso;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import javax.ejb.Asynchronous;
import com.asopagos.subsidiomonetario.composite.clients.GenerarRegistroArchivoLiquidacion;
import com.asopagos.subsidiomonetario.composite.clients.GenerarArchivoLiquidacion;
import com.asopagos.subsidiomonetario.clients.GenerarArchivoResultadoPersonasSinDerechoAsync;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.novedades.composite.clients.InsercionMonitoreoLogs;

/**
 * <b>Descripción: Clase que implementa los servicios de composicion para Subsidio Monetario</b>
 * <b>Historia de Usuario: HU-311-</b>
 *
 * 
 * @author <a href="mailto:rarboleda@heinsohn.com.co> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co> Roy López Cardona</a>
 */
@Stateless
public class SubsidioMonetarioCompositeBusiness implements SubsidioMonetarioCompositeService {

    private final String PROFILE = "Profile";

    /**e
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SubsidioMonetarioCompositeBusiness.class);
    
    /** inject del EJB para consultas en modelo Subsidio */
    @Inject
    private IEnvioComunicados envioComunicados;
    
    /**
     * Tipo de autenticación (de acuerdo al header de la petición)(Controla la cantidad de caracteres previos al token)
     */
    private static final String TIPO_AUTORIZACION = "Bearer ";
    
    @Inject
    private SubsidioMonetarioAsincronoCompositeService subsidioMonetarioAsincronoCompositeService;
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#aprobarLiquidacionMasivaPrimerNivel(java.lang.String,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void aprobarLiquidacionMasivaPrimerNivelComposite(String numeroSolicitud, String idTarea, String usernameSupervisor,
            UserDTO userDTO, HttpHeaders headers) {

        String firmaMetodo = "ConsultasModeloCore.aprobarLiquidacionMasivaPrimerNivel(String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

        Map<String, Object> params = new HashMap<>();
        params.put(SubsidioMonetarioCompositeConstants.APROBADO_PRIMER_NIVEL, SubsidioMonetarioCompositeConstants.APROBACION);
        params.put(SubsidioMonetarioCompositeConstants.SUPERVISOR_SUBSIDIO, usernameSupervisor);
        try {
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();

        } catch (NotAuthorizedException ne) {
            ObtenerTokenAcceso obtenerTokenSrv = new ObtenerTokenAcceso(headers.getHeaderString(PROFILE));
            obtenerTokenSrv.execute();
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.setToken(obtenerTokenSrv.getResult());
            terminarTarea.execute();
        } catch (Exception e){
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }

        aprobarLiquidacionMasivaPrimerNivel(numeroSolicitud);
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.APROBADA_PRIMER_NIVEL);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#aprobarLiquidacionMasivaSegundoNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.composite.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> aprobarLiquidacionMasivaSegundoNivelComposite(String numeroSolicitud, String idTarea,
        AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, HttpHeaders headers) {
    	
    	String firmaMetodo = "ConsultasModeloCore.aprobarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        String MensajeNoAprobado;
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
    	 
    	HashMap<String, String> resultado = new HashMap<>();
    	try {
    		Boolean enAprobacionProceso = obtenerMarcaProcesoAprobarSegNivel(numeroSolicitud);
    		logger.debug("enAprobacionProceso: " + enAprobacionProceso);
	    	if (enAprobacionProceso){
	    		resultado.put("resultado", "espera");
	    		return resultado;
	    	}
	    	
            Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = null;
            if (aprobacionRechazoSubsidioMonetarioDTO != null) {
            	
                solicitudLiquidacion = aprobarLiquidacionMasivaSegundoNivel(numeroSolicitud, aprobacionRechazoSubsidioMonetarioDTO);
                logger.info("ZZZ aprobarLiquidacionMasivaSegundoNivel: " + new Date());
            }
            else {
            	
                solicitudLiquidacion = aprobarLiquidacionMasivaSegundoNivel(numeroSolicitud, new AprobacionRechazoSubsidioMonetarioDTO());
                logger.info("ZZZ aprobarLiquidacionMasivaSegundoNivel: " + new Date());
            }
            if (solicitudLiquidacion != null){
            	
                Map<String, Object> params = new HashMap<>();                
                params.put(SubsidioMonetarioCompositeConstants.APROBRADO_SEGUNDO_NIVEL, SubsidioMonetarioCompositeConstants.APROBACION);
                params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacion.getUsuarioEvaluacionPrimerNivel());
                try {
                    TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                    terminarTarea.execute();
                    logger.info("ZZZ terminarTarea: " + new Date());
                } catch (NotAuthorizedException ne) {
                    ObtenerTokenAcceso obtenerTokenSrv = new ObtenerTokenAcceso(headers.getHeaderString(PROFILE));
                    obtenerTokenSrv.execute();
                    TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                    terminarTarea.setToken(obtenerTokenSrv.getResult());
                    terminarTarea.execute();
                } catch (RecursoNoAutorizadoException re) {
                    ObtenerTokenAcceso obtenerTokenSrv = new ObtenerTokenAcceso(headers.getHeaderString(PROFILE));
                    obtenerTokenSrv.execute();
                    TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                    terminarTarea.setToken(obtenerTokenSrv.getResult());
                    terminarTarea.execute();
                }  catch (Exception e){
                    throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
                }
                
                //Se invoca el servicio que realiza la migración de los datos de liquidación a pagos mediante un SP
                enviarConsolidadoAPagos(userDTO.getNombreUsuario(), numeroSolicitud);
                logger.info("ZZZ enviarConsolidadoAPagos: " + new Date());
                
                actualizarEstadoDerechoLiquidacion(numeroSolicitud);
                logger.info("ZZZ actualizarEstadoDerechoLiquidacion: " + new Date());
                   
                generarArchivosSalidaEntidadDescuentoComposite(numeroSolicitud);
                logger.info("ZZZ generarArchivosSalidaEntidadDescuentoComposite: " + new Date());

                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.APROBADA_SEGUNDO_NIVEL);
                resultado.put("resultado", "aprobado");
                logger.info("ZZZ actualizarEstadoSolicitudLiquidacion: " + new Date());
            } else {
            	HashMap<String, String> resultadoEnProceso;
                MensajeNoAprobado = "Beneficiario ya liquidado para este trabajador por el período";
                AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazo = new AprobacionRechazoSubsidioMonetarioDTO();
                aprobacionRechazo.setObservaciones(MensajeNoAprobado);
                aprobacionRechazo.setRazonRechazo(RazonRechazoEnum.BENEFICIARIO_LIQUIDADO_TRABAJADOR_PERIODO);
                resultadoEnProceso = (HashMap<String, String>) rechazarLiquidacionMasivaSegundoNivelComposite(numeroSolicitud, idTarea, aprobacionRechazo, userDTO, headers);
                if(resultadoEnProceso != null){
                	resultado.put("mensaje", resultadoEnProceso.get("mensaje"));
                	return resultado;
                }
                resultado.put("resultado", "noAprobado");                
            }    
        }catch (BPMSExecutionException ebpm){
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,ebpm);
        }catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }finally{
        
        	eliminarMarcaAprobacionSegNivel();
        }

        eliminarMarcaAprobacionSegNivel();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionMasivaPrimerNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.composite.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void rechazarLiquidacionMasivaPrimerNivelComposite(String numeroSolicitud, String idTarea,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, HttpHeaders headers) {

    	String firmaMetodo = "SubsidioMonetarioCompositeBusiness.rechazarLiquidacionMasivaPrimerNivelComposite(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.debug("String numeroSolicitud: " + numeroSolicitud + " - seguimiento undefined - " + userDTO);
        
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        //Se obtine solo la parte asociada al token (del header proviene un string de tipo [tipoAtorizacion][token])
        userDTO.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));
        
        this.subsidioMonetarioAsincronoCompositeService.
        		rechazarLiquidacionMasivaPrimerNivelAsync(numeroSolicitud, idTarea, aprobacionRechazoSubsidioMonetarioDTO, userDTO, headers.getHeaderString(PROFILE));
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionMasivaSegundoNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.composite.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String,String> rechazarLiquidacionMasivaSegundoNivelComposite(String numeroSolicitud, String idTarea,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, HttpHeaders headers) {

    	 String firmaMetodo = "ConsultasModeloCore.rechazarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
         logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
         HashMap<String, String> resultado = new HashMap<>();
         
         if (aprobacionRechazoSubsidioMonetarioDTO != null) {
             if (aprobacionRechazoSubsidioMonetarioDTO.getRazonRechazo() != null
                     && aprobacionRechazoSubsidioMonetarioDTO.getObservaciones() != null) {
             	
                 SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = rechazarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                         aprobacionRechazoSubsidioMonetarioDTO, Boolean.FALSE);

                 if (solicitudLiquidacion.getLiquidacionORechazOStagingEnProceso() != null
                         && solicitudLiquidacion.getLiquidacionORechazOStagingEnProceso()) {
                     resultado.put("mensaje",
                             "Proceso en ejecución, no es posible eliminar la liquidación por favor intentar en un momento");
                     return resultado;
                 }

                 Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

                 Map<String, Object> params = new HashMap<>();
                 params.put(SubsidioMonetarioCompositeConstants.APROBRADO_SEGUNDO_NIVEL, SubsidioMonetarioCompositeConstants.RECHAZO);
                 params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacion.getUsuarioEvaluacionPrimerNivel());
                 
                 try {
                     TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                     terminarTarea.execute();
                 } catch (NotAuthorizedException ne) {
                     ObtenerTokenAcceso obtenerTokenSrv = new ObtenerTokenAcceso(headers.getHeaderString(PROFILE));
                     obtenerTokenSrv.execute();
                     TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                     terminarTarea.setToken(obtenerTokenSrv.getResult());
                     terminarTarea.execute();
                 } catch (Exception e) {
                     throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
                }

                 actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                 actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL);
             }
             else {
                 throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
             }
         }
         else {
             throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
         }
         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         return null;
       
    }
   
    /**
     *  (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionMasivaSegundoNivelCompositeAsyn(java.lang.String, java.lang.String, com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO, javax.ws.rs.core.HttpHeaders)
     */
    @Override
    public void rechazarLiquidacionMasivaSegundoNivelCompositeAsyn(String numeroSolicitud, String idTarea,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, HttpHeaders headers) {

        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.rechazarLiquidacionMasivaSegundoNivelComposite(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        //Se obtine solo la parte asociada al token (del header proviene un string de tipo [tipoAtorizacion][token])
        userDTO.setToken(token.substring(TIPO_AUTORIZACION.length(), token.length()));
        
        this.subsidioMonetarioAsincronoCompositeService.
        		rechazarLiquidacionMasivaSegundoNivelAsync(numeroSolicitud, idTarea, aprobacionRechazoSubsidioMonetarioDTO, userDTO, headers.getHeaderString(PROFILE));
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

       
    }

    /**
     * Método que permite consultar el id de solicitud de liquidacion correspondiente al radicado
     * @param numeroRadicado
     *        valor del radicado
     * @return identificador de la solicitud
     */
    private Long consultarIdSolicitudLiquidacion(String numeroRadicado) {
        ConsultarIdSolicitud consultarSolicitud = new ConsultarIdSolicitud(numeroRadicado);
        consultarSolicitud.execute();
        return consultarSolicitud.getResult();
    }

    /**
     * Método que permite registrar la información de aprobación de primer nivel para un subsidio monetario masivo
     * @param numeroSolicitud
     *        número de la solicitud
     * @return
     */
    private Long aprobarLiquidacionMasivaPrimerNivel(String numeroSolicitud) {
        AprobarLiquidacionMasivaPrimerNivel aprobacion = new AprobarLiquidacionMasivaPrimerNivel(numeroSolicitud);
        aprobacion.execute();
        return aprobacion.getResult();
    }

    /**
     * Método que permite registrar la información de aprobación de segundo nivel para un subsidio monetario masivo
     * @param numeroSolicitud
     *        número de la solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        información de la aprobación
     * @return identificador de la solicitud
     */
    private SolicitudLiquidacionSubsidioModeloDTO aprobarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO) {
        AprobarLiquidacionMasivaSegundoNivel aprobacion = new AprobarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                aprobacionRechazoSubsidioMonetarioDTO);
        aprobacion.execute();
        return aprobacion.getResult();
    }

    /**
     * Método que permite registrar la información de rechazo en primer nivel para un subsidio monetario masivo
     * @param numeroSolicitud
     *        número de soliciutd
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        información del rechazo
     * @return identificador de la solicitud
     */
    private Long rechazarLiquidacionMasivaPrimerNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO) {
        RechazarLiquidacionMasivaPrimerNivel rechazo = new RechazarLiquidacionMasivaPrimerNivel(numeroSolicitud,
                aprobacionRechazoSubsidioMonetarioDTO);
        rechazo.execute();
        return rechazo.getResult();
    }

    /**
     * Método que permite registrar la información de rechazo en segundo nivel para un subsidio monetario masivo
     * @param numeroSolicitud
     *        número de solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        información de rechazo
     * @return identificador de la solicitud
     */
    private SolicitudLiquidacionSubsidioModeloDTO rechazarLiquidacionMasivaSegundoNivel(String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, Boolean isAsync) {
        RechazarLiquidacionMasivaSegundoNivel rechazo = new RechazarLiquidacionMasivaSegundoNivel(numeroSolicitud, isAsync,
                aprobacionRechazoSubsidioMonetarioDTO);
        rechazo.execute();
        return rechazo.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#guardarLiquidacionMasiva(com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO,
     *      java.lang.Long)
     */
    @Override
    public RespuestaGenericaDTO guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PersistirLiquidacionMasiva persistirLiquidacionMasiva = new PersistirLiquidacionMasiva(periodo, liquidacion);
        persistirLiquidacionMasiva.execute();

        RespuestaGenericaDTO respuesta = persistirLiquidacionMasiva.getResult();
        RespuestaGenericaDTO respuestaFinal = new RespuestaGenericaDTO();

        if (!respuesta.getOperacionExitosa()) {
            respuestaFinal.setOperacionExitosa(respuesta.getOperacionExitosa());
            respuestaFinal.setCausaError(respuesta.getCausaError());
            return respuestaFinal;
        }
        else {
            respuestaFinal.setOperacionExitosa(respuesta.getOperacionExitosa());
        }

        // Servicio composite para generar el numero de radicado
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(respuesta.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
        radicarSolicitudService.execute();
        
        //Obtener token para llamado a servicio del BPM
        GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
        accesoCore.execute();
        TokenDTO token = accesoCore.getResult();
        token.getToken();

        // Iniciar proceso de liquidacion BPM
        Map<String, Object> params = new HashMap<>();

        String tiempoProcesoSolicitud = (String) CacheManager
                .getParametro(ParametrosSistemaConstants.BPM_LMSM_TIEMPO_PROCESO_SOLICITUD);

        params.put("analistaSubsidio", userDTO.getNombreUsuario());
        params.put("idSolicitud", respuesta.getIdSolicitud());
        params.put("numeroRadicado", radicarSolicitudService.getResult());
        params.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
        IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO, params);
        iniciarProceso.setToken(token.getToken());
        iniciarProceso.execute();

        //Actualizar la solicitud global con el numero de intancia del BPM
        ActualizarInstanciaSolicitudGlobal actualizarSolicitudGlobal = new ActualizarInstanciaSolicitudGlobal(respuesta.getIdSolicitud(),
                iniciarProceso.getResult());
        actualizarSolicitudGlobal.execute();
        
        String numeroRadicacion = radicarSolicitudService.getResult();
        
        // se crea el registro que cuenta el porcentaje de avance del procesp
        iniciarPorcentajeAvanceProcesoLiquidacion(numeroRadicacion);
        
        respuestaFinal.setNumeroRadicado(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaFinal;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#ejecutarLiquidacionMasiva(com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO,
     *      java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Boolean ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion, Long periodo, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.guardarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO, Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // Ejecutar orquestador de Stagin con la fecha actual
        /*EjecutarOrquestadorStagin ejecutarOrquestadorStagin = new EjecutarOrquestadorStagin(new Date().getTime());
        ejecutarOrquestadorStagin.execute();
        */
        
        GenerarNuevoPeriodoL generarNuevoPeriodoL = new  GenerarNuevoPeriodoL(periodo);
        generarNuevoPeriodoL.execute();        

        // Verificar si hay una ejecucion en proceso
        EjecutarLiquidacionMasiva ejecutarLiquidacionMasiva = new EjecutarLiquidacionMasiva(periodo, liquidacion);
        ejecutarLiquidacionMasiva.execute();

        if (ejecutarLiquidacionMasiva.getResult().getEjecucionEnProceso()) {
            return Boolean.FALSE;
        }

        RespuestaGenericaDTO respuestaGuardar = this.guardarLiquidacionMasiva(liquidacion, periodo, userDTO);

        // Iniciar la liquidacion masiva llamando StoredProcedure
        IniciarLiquidacionMasiva iniciarLiquidacionMasiva = new IniciarLiquidacionMasiva(periodo, respuestaGuardar.getNumeroRadicado());
        iniciarLiquidacionMasiva.execute();

        
//        Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(respuestaGuardar.getNumeroRadicado());
//        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_APROBACION);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaGuardar.getOperacionExitosa();
    }

    /**
     * Método que permite actualizar el estado de una solicitud de liquidación por su identificador
     * @param idSolicitudLiquidacion
     *        identificador de la solicitud
     * @param estado
     *        nuevo estado para la solicitud
     */
    private void actualizarEstadoSolicitudLiquidacion(Long idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum estado) {
        ActualizarEstadoSolicitudLiquidacion solicitudLiquidacion = new ActualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion,
                estado);
        solicitudLiquidacion.execute();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#terminarTareaLiquidacion(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String,String> terminarTareaLiquidacionCancelada(String numeroSolicitud, String idTarea, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.terminarTareaLiquidacionCancelada(String, String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        Boolean procesoEnEjecucion = Boolean.FALSE;
        HashMap<String, String> resultado = new HashMap<>();
        
        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = consultarSolicitudLiquidacion(numeroSolicitud);
        
        /*Si es una liquidación ESPECIFICA, se eliminan los datos de la liquidación.*/
        if (!TipoProcesoLiquidacionEnum.MASIVA.equals(solicitudLiquidacion.getTipoLiquidacion())) {
            EliminarLiquidacionSP eliminarLiquidacionSP = new EliminarLiquidacionSP(numeroSolicitud);
            eliminarLiquidacionSP.execute();
            procesoEnEjecucion =eliminarLiquidacionSP.getResult();
            if(procesoEnEjecucion != null && procesoEnEjecucion){
            	resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación por favor intentar en un momento");
            	return resultado;
            }
        }
        
        Long idSolicitudLiquidacion = solicitudLiquidacion.getIdProcesoLiquidacionSubsidio();

        try {
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), new HashMap<>());
            terminarTarea.execute();
        } catch (Exception e) {
            throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
        }

        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CANCELADA);
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);       
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#terminarTareaLiquidacionDispersada(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void terminarTareaLiquidacionDispersada(String numeroSolicitud, String idTarea, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.terminarTareaLiquidacionDispersada(String, String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);
        try{

             //Se comenta este bloque para las pruebas en el ambiente no cierre la tarea y no repetir la liquidacion
            try{
                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), new HashMap<>());
                terminarTarea.execute();
            }
            catch (Exception e) {
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }

            dispersarPagosEstadoEnviado(numeroSolicitud);

            List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = consultarCuentasAdministradorMedioTarjeta(numeroSolicitud);

            List<Long> pagosTarjetaExitosos = new ArrayList<>();

            String idProceso = dispersarPagosMedioTarjetaAnibol(cuentasMedioTarjeta);

            if(cuentasMedioTarjeta != null && !cuentasMedioTarjeta.isEmpty()){
                crearRegistroSolicitudAnibol(idProceso, idSolicitudLiquidacion, numeroSolicitud);
            }

            dispersarPagosEstadoAplicado(numeroSolicitud, pagosTarjetaExitosos);

            actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.DISPERSADA);
            actualizarFechaDispersion(idSolicitudLiquidacion);
            actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);

            // Envío de comunicados
            // 063 Notificación de dispersión de pagos al empleador
            // 064 Notificación de dispersión de pagos al trabajador
            // 065 Notificación de dispersión de pagos al administrador del subsidio

            enviarComunicadosLiquidacion(numeroSolicitud);


        }catch (Exception e) {
            InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( 
                                "idSolicitudLiquidacion : "+ idSolicitudLiquidacion
            					,"Se rompe en terminarTareaLiquidacionDispersada subsidioMonetarioComposite " + e);
			insercionMonitoreoLogs.execute();
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }
       
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    @Override
    public void dispersarPagos() {
        String firmaMetodo = "dispersarPagos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        ConsultarRegistroSolicitudAnibol registrosAnibol = new ConsultarRegistroSolicitudAnibol();
        registrosAnibol.execute();
        List<RegistroSolicitudAnibol> registros = registrosAnibol.getResult();
        List<Long> pagosTarjetaExitosos = new ArrayList<>();
        
        for (RegistroSolicitudAnibol registroAnibol : registros) {

            List<ResultadoProcesamientoDTO> resultadosAnibol = new ArrayList<>();
            
            try {
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                if(conexionAnibol.equals("TRUE")){
                ConsultarEstadoProcesamiento consultarEstadoProcesamiento = new ConsultarEstadoProcesamiento(registroAnibol.getIdProceso());
                consultarEstadoProcesamiento.execute();
                resultadosAnibol = consultarEstadoProcesamiento.getResult();
                }
                if(resultadosAnibol != null)
                {
                    logger.info("La cantidad de registros que trajo es: "+resultadosAnibol.size());
                }
                else{
                    logger.info("La respuesta fue nula");
                }
                
            }catch (TechnicalException e) {
                logger.warn("No se pudo realizar la conexión con Anibol", e);
                return;
            }

            Long idSolicitudLiquidacionSubsidio = registroAnibol.getSolicitudLiquidacionSubsidio();
            ConsultarCuentaAdminSubsidioPorSolicitud consultarCuentaAdmin = new ConsultarCuentaAdminSubsidioPorSolicitud(idSolicitudLiquidacionSubsidio);
            consultarCuentaAdmin.execute();
            List<CuentaAdministradorSubsidio> cuentas = consultarCuentaAdmin.getResult();
            Map<String, CuentaAdministradorSubsidio> tarjetasIdCuentaAdministradorSubsidio = new HashMap<>();
            
            for (CuentaAdministradorSubsidio cuentaAdministradorSubsidio : cuentas) {
                tarjetasIdCuentaAdministradorSubsidio.put(
                        cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio(),
                        cuentaAdministradorSubsidio);
            }
            
            List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
            
            for (ResultadoProcesamientoDTO resultadoAnibol : resultadosAnibol) {
                
                if(ResultadoTarjetaAnibolEnum.RTA_REDEBAN.name().equals(resultadoAnibol.getEstado())) {
                    if(resultadoAnibol.isExitoso()){
                        pagosTarjetaExitosos.add(
                            tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
                                .getIdCuentaAdministradorSubsidio()
                            ); 
                    }else {
                        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
                                    tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
                                );
                        cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
                    }
                }
                if(ResultadoTarjetaAnibolEnum.ERORRSOLICITUD.name().equals(resultadoAnibol.getEstado())) {
                    if(!resultadoAnibol.isExitoso()){
                        logger.info("resultadoAnibol.getNumeroTarjeta()" + resultadoAnibol.getNumeroTarjeta());
                        CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO = new CuentaAdministradorSubsidioDTO(
                                tarjetasIdCuentaAdministradorSubsidio.get(resultadoAnibol.getNumeroTarjeta())
                            );
                        cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
                    }
                }
            }
            
            String numeroSolicitud = registroAnibol.getNumeroRadicacion();
            if(!pagosTarjetaExitosos.isEmpty()) {
                
            	logger.info("datos para dispersarPagosEstadoAplicado");
            	logger.info("String numeroSolicitud : " + numeroSolicitud);
            	logger.info("List <Long> pagosTarjetaExitosos : " + pagosTarjetaExitosos);
            	
            	dispersarMasivoPagosEstadoAplicado(numeroSolicitud, pagosTarjetaExitosos);
            	//dispersarPagosEstadoAplicado(numeroSolicitud, pagosTarjetaExitosos);
                actualizarEstadoDerechoLiquidacion(numeroSolicitud);
                
                Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.DISPERSADA);
                actualizarFechaDispersion(idSolicitudLiquidacion);
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
                
                // Envío de comunicados
                // 063 Notificación de dispersión de pagos al empleador 
                // 064 Notificación de dispersión de pagos al trabajador 
                // 065 Notificación de dispersión de pagos al administrador del subsidio
                enviarComunicadosLiquidacion(numeroSolicitud);
                
                actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            }

            for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
                registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
                actualizarEstadoRegistroSolicitudAnibol(registroAnibol.getIdRegistroSolicitudAnibol());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    private void actualizarEstadoRegistroSolicitudAnibol(Long idSolicitudAnibol){
        ActualizarEstadoSolicitudAnibol actualizarEstadoSolicitudAnibol = new ActualizarEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.PROCESADA, idSolicitudAnibol);
        actualizarEstadoSolicitudAnibol.execute();
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#cancelarLiquidacionMasiva(com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO)
     */
    @Override
    public Map<String,String> cancelarLiquidacionMasivaComposite(IniciarSolicitudLiquidacionMasivaDTO params, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.cancelarLiquidacionMasiva(IniciarSolicitudLiquidacionMasivaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        HashMap<String, String> resultado = new HashMap<>();      
        
        SolicitudLiquidacionSubsidioModeloDTO sol = consultarSolicitudLiquidacion(params.getNumeroRadicado());
        
        //Obtener token para llamado a servicio del BPM
        GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
        accesoCore.execute();
        TokenDTO token = accesoCore.getResult();
        token.getToken();

        // Abortar el proceso en BPM
        if (sol != null && sol.getIdInstanciaProceso() != null) {
        	
        	if (cancelarMasivaActualizarObservacionesProceso(params.getNumeroRadicado(), params.getObservacionesProceso()) != null){
        		 resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación en este momento, por favor intentar en un momento");
                 return resultado;
        	}
        	
            AbortarProceso aborProceso = new AbortarProceso(ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO,
                    new Long(sol.getIdInstanciaProceso()));
            aborProceso.setToken(token.getToken());
            aborProceso.execute();
            
            cancelarProcesoLiquidacion(params.getNumeroRadicado());
            
            
            // Cambiar el estado de la solicitud de liquidacion
            actualizarEstadoSolicitudLiquidacion(sol.getIdProcesoLiquidacionSubsidio(), EstadoProcesoLiquidacionEnum.CANCELADA);
            
            // Cambiar el estado de la solicitud de liquidacion
            actualizarEstadoSolicitudLiquidacion(sol.getIdProcesoLiquidacionSubsidio(), EstadoProcesoLiquidacionEnum.CERRADA);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long generarRegistroArchivoLiquidacion(String numeroRadicacion) {
        SolicitudLiquidacionSubsidioModeloDTO sol = consultarSolicitudLiquidacion(numeroRadicacion);
        // Se busca si existe ya el archivo de liquidacion subsidio
        ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultarArchivosLiquidacion(numeroRadicacion);
        if (archivosLiquidacionDTO != null) {
            return archivosLiquidacionDTO.getIdArchivoLiquidacionSubsidio();
        }
        ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionSubsidio = new ArchivoLiquidacionSubsidioModeloDTO();
        archivoLiquidacionSubsidio.setIdSolicitudLiquidacionSubsidio(sol.getIdProcesoLiquidacionSubsidio());
        return gestionarArchivosLiquidacion(archivoLiquidacionSubsidio);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#generarArchivoLiquidacion(java.lang.String)
     */
    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void generarArchivoLiquidacion(Long archivoLiquidacionSubsidio, String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.generarArchivoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String comilla = "\"";
        final String enProceso = "EN_PROCESO";
        //En caso de que se tenga registrada la información y ya se haya generado el archivo de liquidacion se retorna su id ECM

        ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultarArchivosLiquidacionPorId(archivoLiquidacionSubsidio);

        if (archivosLiquidacionDTO == null ||
                enProceso.equals(archivosLiquidacionDTO.getIdentificadorECMLiquidacion())) {
            return;
        }


        archivosLiquidacionDTO.setIdentificadorECMLiquidacion(enProceso);
        // Se usa el servicio de forma que se actualize antes de finalizar el metodo
        GestionarArchivosLiquidacion servicio = new GestionarArchivosLiquidacion(archivosLiquidacionDTO);
        servicio.execute();

        InformacionArchivoDTO archivoLiquidacion = generarArchivoResultadoLiquidacion(numeroRadicacion, null, null);

        try {
            archivoLiquidacion = almacenarArchivoLiquidacion(archivoLiquidacion);

            archivosLiquidacionDTO.setIdentificadorECMLiquidacion(archivoLiquidacion.getIdentificadorDocumento());
            logger.info("2g antes de gestionarArchivosLiquidacion");

            gestionarArchivosLiquidacion(archivosLiquidacionDTO);
        } catch (Exception e) {
            logger.info("Sucedio un error al almacenar el archivo de liquidacion ");
            e.printStackTrace();
            archivosLiquidacionDTO = consultarArchivosLiquidacionPorId(archivoLiquidacionSubsidio);
            archivosLiquidacionDTO.setIdentificadorECMLiquidacion(null);
            GestionarArchivosLiquidacion servicio2 = new GestionarArchivosLiquidacion(archivosLiquidacionDTO);
            servicio2.execute();
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

    }

    /**
     * Método que permite obtener el archivo correspondiente al resultado de la liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param tipoIdentificacion
     * @param numeroIdentificacion       
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO generarArchivoResultadoLiquidacion(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        GenerarArchivoResultadoLiquidacion archivoLiquidacion = new GenerarArchivoResultadoLiquidacion(numeroRadicacion, numeroIdentificacion, tipoIdentificacion);
        archivoLiquidacion.execute();
        return archivoLiquidacion.getResult();
    }

    /**
     * Método que se encarga de almacenar el archivo de liquidacion en el ECM
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivoLiquidacion(InformacionArchivoDTO informacionArchivo) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#generarArchivoPersonasSinDerecho(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String generarArchivoPersonasSinDerecho(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.generarArchivoPersonasSinDerecho(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String comilla = "\"";
        Boolean indicadorRegistro = Boolean.FALSE;
        try {
            //En caso de que se tenga registrada la información y ya se haya generado el archivo de personas sin derecho se retorna su id ECM
            ArchivoLiquidacionSubsidioModeloDTO archivosLiquidacionDTO = consultarArchivosLiquidacion(numeroRadicacion);
            if (archivosLiquidacionDTO != null) {
                indicadorRegistro = Boolean.TRUE;
                if (archivosLiquidacionDTO.getIdentificadorECMPersonasSinDerecho() != null) {
                    return comilla + archivosLiquidacionDTO.getIdentificadorECMPersonasSinDerecho() + comilla;
                }
            }
            long timeInicioFileGenerator = System.currentTimeMillis();
            InformacionArchivoDTO archivoSinDerecho = generarArchivoResultadoPersonasSinDerecho(numeroRadicacion, null, null);
            Long timeFinal = System.currentTimeMillis()-timeInicioFileGenerator;
            logger.info("Fin fileGenerator.generate tiempo ejecucion (ms) : "+ timeFinal);
            
            // Se vuelve a verificar si otro proceso ya generó el archivo
            archivosLiquidacionDTO = consultarArchivosLiquidacion(numeroRadicacion);
            if (archivosLiquidacionDTO != null) {
                indicadorRegistro = Boolean.TRUE;
                if (archivosLiquidacionDTO.getIdentificadorECMPersonasSinDerecho() != null) {
                    return comilla + archivosLiquidacionDTO.getIdentificadorECMPersonasSinDerecho() + comilla;
                }
            }
            
            if (archivoSinDerecho != null && archivoSinDerecho.getFileName() != null) {
            	long timeInicioAlmacenar = System.currentTimeMillis();
                logger.info("Inicio almacenarArchivoLiquidacion Numero Radicado: " +numeroRadicacion);
                archivoSinDerecho = almacenarArchivoLiquidacion(archivoSinDerecho);
                
                Long tiempoFinalAlmacenar = System.currentTimeMillis()-timeInicioAlmacenar;
                logger.info("Tiempo Fin almacenarArchivoLiquidacion tiempo ejecucion (ms) : "+ tiempoFinalAlmacenar);
                
                //En caso de que el registro de identificadores no exista
                if (!indicadorRegistro) {
                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);

                    archivosLiquidacionDTO = new ArchivoLiquidacionSubsidioModeloDTO();
                    archivosLiquidacionDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
                }
                archivosLiquidacionDTO.setIdentificadorECMPersonasSinDerecho(archivoSinDerecho.getIdentificadorDocumento());
                gestionarArchivosLiquidacion(archivosLiquidacionDTO);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return comilla + archivoSinDerecho.getIdentificadorDocumento() + comilla;
            }else {
            	logger.info("No genero archivo sin derecho NumeroRadicado: "+numeroRadicacion);
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método que permite obtener el archivo correspondiente a las personas sin
     * derecho en una liquidación
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información del archivo
     */
    private InformacionArchivoDTO generarArchivoResultadoPersonasSinDerecho(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        GenerarArchivoResultadoPersonasSinDerecho archivoSinDerecho = new GenerarArchivoResultadoPersonasSinDerecho(
                numeroRadicacion, numeroIdentificacion, tipoIdentificacion);
        archivoSinDerecho.execute();
        return archivoSinDerecho.getResult();
    }

    /**
     * Método que permite consultar la información referente a los identificadores de los archvivos generados para una liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de los archivos
     */
    private ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(String numeroRadicacion) {
        ConsultarArchivosLiquidacion archivosLiquidacion = new ConsultarArchivosLiquidacion(numeroRadicacion);
        archivosLiquidacion.execute();
        return archivosLiquidacion.getResult();
    }


    private ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionPorId(Long idArchivoLiquidacion) {
        ConsultarArchivosLiquidacionPorId archivosLiquidacion = new ConsultarArchivosLiquidacionPorId(idArchivoLiquidacion);
        archivosLiquidacion.execute();
        return archivosLiquidacion.getResult();
    }

    /**
     * Método que permite realizar la gestión para el registro de identificadores de los archivos para una liquidación
     * @param archivoLiquidacionDTO
     *        DTO con la información de los archivos a gestionar
     * @return identificador del registro de los archivos
     */
    private Long gestionarArchivosLiquidacion(ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO) {
        GestionarArchivosLiquidacion gestionarArchivos = new GestionarArchivosLiquidacion(archivoLiquidacionDTO);
        gestionarArchivos.execute();
        return gestionarArchivos.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#consultarTrazabilidadSubsidioEspecifico(java.lang.String)
     */
    @Override
    public List<TrazabilidadSubsidioEspecificoDTO> consultarTrazabilidadSubsidioEspecifico(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.consultarTrazabilidadSubsidioEspecifico(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TrazabilidadSubsidioEspecificoDTO> trazabilidadSubsidio = new ArrayList<>();
       // try {
            Long identificadorLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);
            trazabilidadSubsidio = obtenerTrazabilidadSubsidioEspecifico(identificadorLiquidacion);

            Boolean indicadorRechazo = Boolean.FALSE;
            for (TrazabilidadSubsidioEspecificoDTO trazabilidadSubsidioEspecificoDTO : trazabilidadSubsidio) {
                String actividadRealizada = null;
                if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud().equals(EstadoProcesoLiquidacionEnum.EN_PROCESO)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.INICIAR_VALIDACIONES.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.APROBADA_PRIMER_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_LIQUIDACION_ANALISTA.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.RECHAZAR_LIQUIDACION_ANALISTA.getDescripcion();
                    indicadorRechazo = Boolean.TRUE;
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud().equals(EstadoProcesoLiquidacionEnum.EN_APROBACION)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.EN_APROBACION_SUPERVISOR.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.APROBADA_SEGUNDO_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_LIQUIDACION_SUPERVISOR.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.RECHAZAR_LIQUIDACION_SUPERVISOR.getDescripcion();
                    indicadorRechazo = Boolean.TRUE;
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.DISPERSADA)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.DISPERSAR_A_MEDIOS_DE_PAGO.getDescripcion();                    
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.CERRADA)) {
                    if (indicadorRechazo) {
                        actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.CERRAR_SOLICITUD_RECHAZADA.getDescripcion();
                    }
                    else {
                        actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_DISPERSION.getDescripcion();
                    }
                }

                trazabilidadSubsidioEspecificoDTO.setActividadRealizada(actividadRealizada);
            }
/*
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
*/
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return trazabilidadSubsidio;
    }

    /**
     * Método que permite obtener la trazabilidad asociada a una liquidación de subsidio específico
     * @param identificadorLiquidacion
     *        valor del identificador de la liquidación
     * @return información de trazabilidada
     * @author rlopez
     */
    private List<TrazabilidadSubsidioEspecificoDTO> obtenerTrazabilidadSubsidioEspecifico(Long identificadorLiquidacion) {
        ObtenerTrazabilidadSubsidioEspecifico trazabilidad = new ObtenerTrazabilidadSubsidioEspecifico(identificadorLiquidacion);
        trazabilidad.execute();
        return trazabilidad.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#ejecutarLiquidacionEspecificaComposite(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionEspecificaComposite(LiquidacionEspecificaDTO liquidacionEspecifica, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.ejecutarLiquidacionEspecificaComposite(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO respuestaFinal = new RespuestaGenericaDTO();

        //Verificar que los periodos seleccionados se encuentren creados
        VerificarExistenciaPeriodo verificarExistenciaPeriodo = new VerificarExistenciaPeriodo(liquidacionEspecifica.getListaPeriodos());
        verificarExistenciaPeriodo.execute();
        Boolean periodosExisten = verificarExistenciaPeriodo.getResult();
        if (!periodosExisten) {
            respuestaFinal.setCausaError("UNO_DE_LOS_PERIODOS_NO_EXISTE");
            return respuestaFinal;
        }

        //Verificar si hay una liquidacion especifica
        //Se comenta por CC HU-317-226 y HU-317-245 liquidaciones en paralelo
        ConsultarLiquidacionMasivaEnProceso consultarLiquidacionMasivaEnProceso = new ConsultarLiquidacionMasivaEnProceso();
        consultarLiquidacionMasivaEnProceso.execute();
        SolicitudLiquidacionSubsidioModeloDTO liquidacionMasivaEnProceso = consultarLiquidacionMasivaEnProceso.getResult();
        if (liquidacionMasivaEnProceso != null) {
            respuestaFinal.setLiquidacionMasivaEnProceso(Boolean.TRUE);
            return respuestaFinal;
        }
        else {
            GuardarLiquidacionEspecifica guardarLiquidacionEspecifica = new GuardarLiquidacionEspecifica(liquidacionEspecifica);
            guardarLiquidacionEspecifica.execute();
            RespuestaGenericaDTO respuesta = guardarLiquidacionEspecifica.getResult();

            // Generar el numero de radicado
            RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(respuesta.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
            radicarSolicitudService.execute();
            
            logger.debug("radicarSolicitudService.getResult(): " + radicarSolicitudService.getResult() + " - seguimiento undefined");
            
            //Obtener token para llamado a servicio del BPM
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            
            // Iniciar proceso de liquidacion BPM
            Map<String, Object> params = new HashMap<>();

            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_LPESM_TIEMPO_PROCESO_SOLICITUD);
            
            params.put("analistaSubsidio", userDTO.getNombreUsuario());
            params.put("idSolicitud", respuesta.getIdSolicitud());
            params.put("numeroRadicado", radicarSolicitudService.getResult());
            params.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            IniciarProceso iniciarProceso;
            try {
                iniciarProceso = new IniciarProceso(ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO, params);
                iniciarProceso.setToken(token.getToken());
                iniciarProceso.execute();
            } catch (Exception e) {
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }

            //Actualizar la solicitud global con el numero de intancia del BPM
            ActualizarInstanciaSolicitudGlobal actualizarSolicitudGlobal = new ActualizarInstanciaSolicitudGlobal(
                    respuesta.getIdSolicitud(), iniciarProceso.getResult());
            
            actualizarSolicitudGlobal.execute();
            
            respuestaFinal.setNumeroRadicado(radicarSolicitudService.getResult());

            // Guardar los periodos asociados
            GuardarPeriodosLiquidacion guardarPeriodosLiquidacion = new GuardarPeriodosLiquidacion(respuesta.getIdSolicitudLiquidacion(),
                    liquidacionEspecifica.getTipoAjuste(), liquidacionEspecifica.getListaPeriodos());
            guardarPeriodosLiquidacion.execute();

            // Persistir las personas/empresas etc. (PersonaLiquidacionEspecifica)
            if(liquidacionEspecifica.getNivelLiquidacion() != null){
                GuardarPersonasLiquidacionEspecifica guardarPersonasLiquidacionEspecifica = new GuardarPersonasLiquidacionEspecifica(
                        respuesta.getIdSolicitudLiquidacion(), liquidacionEspecifica);
                guardarPersonasLiquidacionEspecifica.execute();
            }
            
            if (!liquidacionEspecifica.getTipoLiquidacion().equals(TipoProcesoLiquidacionEnum.RECONOCIMIENTO_DE_SUBSIDIOS)) {
                //Una vez realizadas las persistencias necesarias se invoca el SP de liquidacion especifica
                EjecutarSPLiquidacionEspecifica ejecutarSPLiquidacionEspecifica = new EjecutarSPLiquidacionEspecifica(
                        liquidacionEspecifica.getTipoAjuste(), radicarSolicitudService.getResult());
                ejecutarSPLiquidacionEspecifica.execute();                
            }
            else {
                // Persistir las condiciones especiales en subsidio por reconocimiento
                GuardarCondicionesEspecialesReconocimiento guardarCondicionesEspecialesReconocimiento = new GuardarCondicionesEspecialesReconocimiento(
                        respuesta.getIdSolicitudLiquidacion(), liquidacionEspecifica.getCondicionesEspecialesReconocimiento());
                guardarCondicionesEspecialesReconocimiento.execute();
                
                // Ejecutar SP de liquidacion por reconocimiento
                EjecutarSPLiquidacionReconocimiento ejecutarSPLiquidacionReconocimiento = new EjecutarSPLiquidacionReconocimiento(
                        radicarSolicitudService.getResult());
                ejecutarSPLiquidacionReconocimiento.execute();
            }
       }
       
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaFinal;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#ejecutarLiquidacionEspecificaComposite(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<String> gestionarColaEjecucionLiquidacion() {
        String firmaServicio = "SubsidioMonetarioCompositeBusiness.gestionarColaEjecucionLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);       
        
        EjecutarSPGestionarColaEjecucionLiquidacion ejecutarSPGestionarColaEjecucionLiquidacion = new EjecutarSPGestionarColaEjecucionLiquidacion();        
        ejecutarSPGestionarColaEjecucionLiquidacion.execute();
        List<String> liquidaciones = ejecutarSPGestionarColaEjecucionLiquidacion.getResult();
        
        ArrayList<String> liquidacionesEjecutadas = new ArrayList<>();
        
        if (liquidaciones == null){
            logger.info("liquidaciones == null");
        }        
        
        if (liquidaciones != null && !liquidaciones.isEmpty()){
            logger.debug("LIQUIDACIONEEEEESS :" + liquidaciones.size());
            for (String liquidacion : liquidaciones) {
                logger.debug("LIQUIDACION A EJECUTAR : " + liquidacion);
                logger.info("LIQUIDACION A EJECUTAR : " + liquidacion);       
                
                EjecutarSPLiquidacionReconocimiento ejecutarSPLiquidacionReconocimiento = new EjecutarSPLiquidacionReconocimiento(liquidacion);
                ejecutarSPLiquidacionReconocimiento.execute();
          
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        }
        return liquidacionesEjecutadas;
    }
    
    
    private String ejecutarSpLiquidacionReconocimiento(String radicado){
        EjecutarSPLiquidacionReconocimiento ejecutarSPLiquidacionReconocimiento = new EjecutarSPLiquidacionReconocimiento(radicado);
        ejecutarSPLiquidacionReconocimiento.execute();
        return radicado;
    }
    
    

    /**
     * Método que permite enivar la información de subsidios a pagos
     * @param usuario
     *        nombre del usuario
     * @param numeroRadicado
     *        valor del número de radicado
     */
    private void enviarConsolidadoAPagos(String usuario, String numeroRadicado) {
        EnviarResultadoLiquidacionAPagos envioPagos = new EnviarResultadoLiquidacionAPagos(numeroRadicado, usuario);
        envioPagos.execute();
    }

    /**
     * método que permite realizar la actualización de la fecha de dispersión de una solicitud de liquidación a partir de su identificador
     * @param idSolicitudLiquidacion
     *        valor del identificador de la solicitud de liquidación
     * @author rlopez
     */
    private void actualizarFechaDispersion(Long idSolicitudLiquidacion) {
        ActualizarFechaDispersion actualizarDispersion = new ActualizarFechaDispersion(idSolicitudLiquidacion);
        actualizarDispersion.execute();
    }

    /**
     * Método que permite generar los archivos de descuento con los resultados de la pignoración en un proceso de liquidación
     * @param numeroRadicacion
     *        valor del número deradicación
     * @author rlopez
     */
    private void generarArchivosSalidaEntidadDescuentoComposite(String numeroRadicacion) {
        String firmaServicio = "SubsidioMonetarioCompositeBusiness.generarArchivosSalidaEntidadDescuentoComposite(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            List<Long> idEntidadesDescuento = obtenerEntidadesDescuento(numeroRadicacion);
            //Si existen Entidades hubo descuentos aplicados.
            if (idEntidadesDescuento != null && !idEntidadesDescuento.isEmpty()) {
            	 // Se consulta el Id de la solicitud de liquidación
                 Long idSolicitudLiquidacionSubsidio = this.consultarIdSolicitudLiquidacion(numeroRadicacion);
            	 for (Long idEntidadDescuento : idEntidadesDescuento) {
     	        	ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO = new ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO();
     	        	trazabilidadDTO.setNumeroRadicacion(numeroRadicacion);
     	        	trazabilidadDTO.setIdEntidadDescuento(idEntidadDescuento);
     	            InformacionArchivoDTO informacionArchivoDTO = generarArchivoDescuentoPignorado(trazabilidadDTO);
     	            informacionArchivoDTO = almacenarArchivoXLSXDescuentos(informacionArchivoDTO);
     	            
     	            //Crea el registro de archivos de salida generado.
     	            ArchivoSalidaDescuentoSubsidioModeloDTO archivoSalidaDTO = new ArchivoSalidaDescuentoSubsidioModeloDTO();
     	            archivoSalidaDTO.setCodigoIdentificacionECMSalida(informacionArchivoDTO.getIdentificadorDocumento());
     	            archivoSalidaDTO.setNombreOUT(informacionArchivoDTO.getDocName());
     	            archivoSalidaDTO.setFechaGeneracion(new Date());
     	            archivoSalidaDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacionSubsidio);
     	            archivoSalidaDTO.setIdEntidadDescuento(idEntidadDescuento);
     	           
     	            this.crearRegistroArchivoSalidaDescuento(archivoSalidaDTO);
                 }
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    /**
     * Método que se encarga de obtener los identificadores de trazabilidad para una solicitud de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return lista de identificadores
     */
    private List<Long> obtenerEntidadesDescuento(String numeroRadicacion) {
        ObtenerEntidadesDescuentoRadicacion identificadores = new ObtenerEntidadesDescuentoRadicacion(numeroRadicacion);
        identificadores.execute();
        return identificadores.getResult();
    }

    /**
     * método que se encarga de generar el archivo de salida para la entidad de descuento relacionada
     * @param idTrazabilidad
     *        valor del identificador de trazabilidad
     * @return DTO con la información del archivo XLSX
     */
    private InformacionArchivoDTO generarArchivoDescuentoPignorado(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO trazabilidadDTO) {
        GenerarResultadosArchivoDescuento resultadoArchivo = new GenerarResultadosArchivoDescuento(trazabilidadDTO);
        resultadoArchivo.execute();
        return resultadoArchivo.getResult();
    }

    /**
     * Método que se encarga de almacenar el archivo XLSX en el ECM
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivoXLSXDescuentos(InformacionArchivoDTO informacionArchivo) {
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();
    }

    /**
     * Método que se encarga de gestionar la información de trazabilidad para un archivo de descuento
     * @param informacionTrazabilidad
     *        DTO con la información de la trazabilidad
     */
    private void crearRegistroArchivoSalidaDescuento(ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo) {
        CrearRegistroArchivoSalidaDescuento crearArchivoSalida = new CrearRegistroArchivoSalidaDescuento(infoArchivo);
        crearArchivoSalida.execute();
    }

    /**
     * Método que permite actualizar la información relacionada a los archivos de descuento asociados a una solicitud de liquidación
     * cancelada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    private void actualizarArchivosDescuentoLiquidacionCancelada(String numeroRadicacion) {
        ActualizarArchivosDescuentoLiquidacionCancelada actualizar = new ActualizarArchivosDescuentoLiquidacionCancelada(numeroRadicacion);
        actualizar.execute();
    }

    /**
     * Método que permite realizar la actualización de los subsidios asignados en un proceso de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    private void actualizarEstadoDerechoLiquidacion(String numeroRadicacion) {
        ActualizarEstadoDerechoLiquidacion actualizarDerecho = new ActualizarEstadoDerechoLiquidacion(numeroRadicacion);
        actualizarDerecho.execute();
    }

    /**
     * Método que permite actualizar el estado de las Cuentas de Administrador de subsidio a enviado
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    private void dispersarPagosEstadoEnviado(String numeroRadicacion) {
        List<TipoMedioDePagoEnum> mediosDePago = new ArrayList<TipoMedioDePagoEnum>();
        mediosDePago.add(TipoMedioDePagoEnum.EFECTIVO);
        mediosDePago.add(TipoMedioDePagoEnum.TARJETA);
        mediosDePago.add(TipoMedioDePagoEnum.TRANSFERENCIA);

        DispersarPagosEstadoEnviado dispersar = new DispersarPagosEstadoEnviado(numeroRadicacion, EstadoTransaccionSubsidioEnum.ENVIADO,
                mediosDePago);
        dispersar.execute();
    }

    /**
     * Método que permite actualizar el estado de las Cuentas de Administrador de subsidio a enviado para los medio de pago efectivo y
     * tarjeta. Este último solo en el caso de los abonos exitosos
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param abonosExitosos
     *        lista de identificadores de las cuentas de administrador con abonos a tarjeta exitosos
     * @author rlopez
     */
    private void dispersarPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos) {
        DispersarPagosEstadoAplicado dispersar = new DispersarPagosEstadoAplicado(numeroRadicacion, abonosExitosos);
        dispersar.execute();
    }
    
    private void dispersarMasivoPagosEstadoAplicado(String numeroRadicacion, List<Long> abonosExitosos) {
    	DispersarMasivoPagosEstadoAplicado dispersar = new DispersarMasivoPagosEstadoAplicado(numeroRadicacion, abonosExitosos);
        dispersar.execute();
    }

    /**
     * Método que permite obtener las cuentas de administrador de subsidio en una liquidación para el medio de pago tarjeta
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return información de las cuentas
     * @author rlopez
     */
    private List<CuentaAdministradorSubsidioDTO> consultarCuentasAdministradorMedioTarjeta(String numeroRadicacion) {
        ConsultarCuentasAdministradorMedioTarjeta consultar = new ConsultarCuentasAdministradorMedioTarjeta(numeroRadicacion);
        consultar.execute();
        return consultar.getResult();
    }

    /**
     * Método que se encarga de gestionar (consultar estado tarjeta, abonar y registrar transacciones fallidas) la dispersión de pagos al
     * medio tarjeta
     * 
     * @author rlopez
     * 
     * @param cuentasMedioTarjeta
     *        información de las cuentas asociadas al medio de pago tarjeta
     * @return lista de identificadores de las cuentas con dispersión exitosa
     */
    private String dispersarPagosMedioTarjetaAnibol(List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta) {
        
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaActiva = new ArrayList<>();
        List<SaldoTarjetaDTO> saldosTarjetaDTO = new ArrayList<>();
        SaldoTarjetaDTO saldoTarjetaDTO; 
        List<CuentaAdministradorSubsidioDTO> cuentasTarjetaInactivaError = new ArrayList<>();
        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasMedioTarjeta) {
            if(EstadoTarjetaMultiserviciosEnum.ACTIVA.equals(cuentaAdministradorSubsidioDTO.getEstadoTarjeta())) {
                cuentasTarjetaActiva.add(cuentaAdministradorSubsidioDTO);
                saldoTarjetaDTO = new SaldoTarjetaDTO();
                String tipoIdentificacionAnibol = 
                        com.asopagos.clienteanibol.enums.TipoIdentificacionEnum
                        .valueOf(cuentaAdministradorSubsidioDTO.getTipoIdAdminSubsidio().name())
                        .getTipoIdentificacion();
                saldoTarjetaDTO.setTipoIdentificacion(tipoIdentificacionAnibol);
                saldoTarjetaDTO.setNumeroIdentificacion(cuentaAdministradorSubsidioDTO.getNumeroIdAdminSubsidio());
                saldoTarjetaDTO.setNumeroTarjeta(cuentaAdministradorSubsidioDTO.getNumeroTarjetaAdminSubsidio());
                saldoTarjetaDTO.setSaldo(String.format("%.2f",cuentaAdministradorSubsidioDTO.getValorRealTransaccion()));
                logger.debug("Tarjeta para abonar: " + saldoTarjetaDTO.toString());
                saldosTarjetaDTO.add(saldoTarjetaDTO);
            }else {
                cuentasTarjetaInactivaError.add(cuentaAdministradorSubsidioDTO);
            }
        }
        
        String idProceso = null;
        if(!saldosTarjetaDTO.isEmpty()) {
            try {
                String conexionAnibol = (String) CacheManager.getConstante(ConstantesSistemaConstants.CONEXION_ANIBOL);
                ResultadoAnibolDTO resultadoAnibolDTO = null;
                if(conexionAnibol.equals("TRUE")){
                AbonarSaldoTarjetas abonarSaldoTarjetas = new AbonarSaldoTarjetas(saldosTarjetaDTO);
                abonarSaldoTarjetas.execute();
                resultadoAnibolDTO = abonarSaldoTarjetas.getResult();
                }
                if(resultadoAnibolDTO != null && resultadoAnibolDTO.isExitoso()) {
                    idProceso = resultadoAnibolDTO.getIdProceso();    
                }else{
                    cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
                }
            }catch (TechnicalException e) {
                logger.error("No se pudo realizar la conexión con Anibol", e);
                cuentasTarjetaInactivaError.addAll(cuentasTarjetaActiva);
            }
        }
        
        for (CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO : cuentasTarjetaInactivaError) {
            registrarTransaccionFallida(cuentaAdministradorSubsidioDTO);
        }
        
        return idProceso;
    }

    /**
     * Método que se encarga de invocar el cliente de anibol para realizar las novedades monetaria por abono de tarjeta
     * @param novedadesMonetariasDTO
     *        lista de DTO´s con la información de las tarjetas
     * @return información de la novedad monetaria
     * @author rlopez
     */
//    private List<DatosNovedadMonetaria> novedadMonetaria(List<NovedadMonetariaDTO> novedadesMonetariasDTO) {
//        NovedadMonetaria novedadMonetaria = new NovedadMonetaria(novedadesMonetariasDTO);
//        novedadMonetaria.execute();
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(novedadMonetaria.getResult(), new TypeToken<List<DatosNovedadMonetaria>>() {
//        }.getType());
//    }

    /**
     * Método que permite realizar el registro de una transacción fallida por concepto de abono al medio tarjeta
     * @param cuentaAdministrador
     *        información de la cuenta de administrador para pago al medio tarjeta
     * @author rlopez
     */
    private void registrarTransaccionFallida(CuentaAdministradorSubsidioDTO cuentaAdministrador) {
        TransaccionFallidaDTO transaccionFallida = new TransaccionFallidaDTO();

        transaccionFallida.setFechaHoraRegistro(new Date());
        transaccionFallida.setCanal(CanalRecepcionEnum.WEB);
        transaccionFallida.setEstadoResolucion(EstadoTransaccionFallidaEnum.PENDIENTE);
        transaccionFallida.setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum.ABONO_NO_EXITOSO);
        transaccionFallida.setIdCuentaAdmonSubsidio(cuentaAdministrador.getIdCuentaAdministradorSubsidio());

        GestionarTransaccionesFallidas gestionar = new GestionarTransaccionesFallidas(transaccionFallida);
        gestionar.execute();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#confirmarBeneficiarioAfiliadoLiquidacionFallecimiento(java.lang.Long,
     *      java.lang.String)
     */
    @Override
    public ResultadoLiquidacionFallecimientoDTO confirmarBeneficiarioAfiliadoLiquidacionFallecimiento(Long condicionBeneficiarioAfiliado,
            String numeroRadicacion) {
        String firmaServicio = "SubsidioMonetarioCompositeBusiness.confirmarBeneficiarioAfiliadoLiquidacionFallecimiento(Long,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        confirmarBeneficiarioLiquidacionFallecimiento(numeroRadicacion, condicionBeneficiarioAfiliado);
        ResultadoLiquidacionFallecimientoDTO resultadoLiquidacionDTO = consultarResultadoLiquidacionFallecimiento(numeroRadicacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoLiquidacionDTO;
    }

    /**
     * Método que permite obtener la información de una solicitud de liquidación a partir de su numero de radicado
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return DTO con la información de la solicitud de liquidación
     */
    private SolicitudLiquidacionSubsidioModeloDTO consultarSolicitudLiquidacion(String numeroRadicacion) {
        ConsultarSolicitudLiquidacion consultarSolicitud = new ConsultarSolicitudLiquidacion(numeroRadicacion);
        consultarSolicitud.execute();
        return consultarSolicitud.getResult();
    }

    /**
     * Método que permite confirmar un beneficiario dentro de una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionBeneficiario
     *        identificador de la condición de beneficiario
     */
    private void confirmarBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionBeneficiario) {
        ConfirmarBeneficiarioLiquidacionFallecimiento confirmacion = new ConfirmarBeneficiarioLiquidacionFallecimiento(numeroRadicacion,
                idCondicionBeneficiario);
        confirmacion.execute();
    }

    /**
     * Método que permite confirmar los beneficiarios asociados a un trabajador en una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionAfiliado
     *        identificador de la condición de afiliado
     */
    private void confirmarAfiliadoLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionAfiliado) {
        ConfirmarAfiliadoLiquidacionFallecimiento confirmacion = new ConfirmarAfiliadoLiquidacionFallecimiento(numeroRadicacion,
                idCondicionAfiliado);
        confirmacion.execute();
    }

    /**
     * Método que permite obtener el resultado de una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de la liquidación
     */
    private ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimiento(String numeroRadicacion) {
        ConsultarResultadoLiquidacionFallecimiento resultadoLiquidacion = new ConsultarResultadoLiquidacionFallecimiento(numeroRadicacion);
        resultadoLiquidacion.execute();
        return resultadoLiquidacion.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#ejecutarLiquidacionEspecificaFallecimiento(com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public RespuestaGenericaDTO ejecutarLiquidacionEspecificaFallecimiento(LiquidacionEspecificaDTO liquidacionEspecifica,
            UserDTO userDTO) {

        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.ejecutarLiquidacionEspecificaFallecimientoTrabajador(LiquidacionEspecificaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaGenericaDTO respuestaFinal = new RespuestaGenericaDTO();

        //Verificar si hay una liquidacion especifica
        VerificarLiquidacionFallecimientoEnProceso verificarLiquidacionEnProceso = new VerificarLiquidacionFallecimientoEnProceso();
        verificarLiquidacionEnProceso.execute();
        Boolean hayLiquidacionEnProceso = verificarLiquidacionEnProceso.getResult();
        respuestaFinal.setEjecucionEnProceso(hayLiquidacionEnProceso);
        if (hayLiquidacionEnProceso) {
            return respuestaFinal;
        }
        // Continuar con la liquidacion especifica 
        else {
            // Guardar la liquidacion especifica
            GuardarLiquidacionEspecifica guardarLiquidacionEspecifica = new GuardarLiquidacionEspecifica(liquidacionEspecifica);
            guardarLiquidacionEspecifica.execute();
            RespuestaGenericaDTO respuesta = guardarLiquidacionEspecifica.getResult();

            // Generar el numero de radicado
            RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(respuesta.getIdSolicitud(), userDTO.getSedeCajaCompensacion());
            radicarSolicitudService.execute();
            respuestaFinal.setNumeroRadicado(radicarSolicitudService.getResult());

            //Obtener token para llamado a servicio del BPM
            GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
            accesoCore.execute();
            TokenDTO token = accesoCore.getResult();
            token.getToken();
            
            // Iniciar proceso de liquidacion BPM
            Map<String, Object> params = new HashMap<>();
            
            params.put("analistaSubsidio", userDTO.getNombreUsuario());
            params.put("idSolicitud", respuesta.getIdSolicitud());
            params.put("numeroRadicado", radicarSolicitudService.getResult());
            IniciarProceso iniciarProceso = new IniciarProceso(ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO, params);
            iniciarProceso.setToken(token.getToken());
            iniciarProceso.execute();
            
            //Actualizar la solicitud global con el numero de intancia del BPM
            ActualizarInstanciaSolicitudGlobal actualizarSolicitudGlobal = new ActualizarInstanciaSolicitudGlobal(
            respuesta.getIdSolicitud(), iniciarProceso.getResult());
            
            actualizarSolicitudGlobal.execute();
            
            // Guardar los periodos asociados
            List<ValorPeriodoDTO> periodo = new ArrayList<>();
            periodo.add(new ValorPeriodoDTO(new Date().getTime()));
            
            GuardarPeriodosLiquidacion guardarPeriodosLiquidacion = new GuardarPeriodosLiquidacion(respuesta.getIdSolicitudLiquidacion(),
                    liquidacionEspecifica.getTipoAjuste(), periodo);
            guardarPeriodosLiquidacion.execute();            
            
            // Persistir el trabajador
            GuardarPersonasLiquidacionEspecifica guardarPersonasLiquidacionEspecifica = new GuardarPersonasLiquidacionEspecifica(
                    respuesta.getIdSolicitudLiquidacion(), liquidacionEspecifica);
            guardarPersonasLiquidacionEspecifica.execute();

            Boolean beneficiarioFallecido = liquidacionEspecifica.getTipoAjuste()
                    .equals(TipoLiquidacionEspecificaEnum.DEFUNCION_TRABAJADOR_DEPENDIENTE) ? Boolean.FALSE : Boolean.TRUE;

            // Ejecutar SP de liquidacion por fallecimiento de trabajador
            EjecutarSPLiquidacionFallecimiento ejecutarSPLiquidacionFallecimiento = new EjecutarSPLiquidacionFallecimiento(new Date().getTime(),
                    radicarSolicitudService.getResult(), beneficiarioFallecido);
            ejecutarSPLiquidacionFallecimiento.execute();

            //Ejecucion automatica de los archivos sin y con derecho de manera asincrona
            // Persistencia temprana de archivo liquidacion subsidio
            try {

                GenerarRegistroArchivoLiquidacion servicio1 = new GenerarRegistroArchivoLiquidacion(radicarSolicitudService.getResult());
                servicio1.execute();
                Long idArchivoLiquidacion = servicio1.getResult();
                //Archivo sin derecho
                GenerarArchivoResultadoPersonasSinDerechoAsync servicio2 = new GenerarArchivoResultadoPersonasSinDerechoAsync(idArchivoLiquidacion, radicarSolicitudService.getResult());
                servicio2.execute();
                //Archivo con derecho
                GenerarArchivoLiquidacion servicio3 = new GenerarArchivoLiquidacion(idArchivoLiquidacion, radicarSolicitudService.getResult());
                servicio3.execute();
            } catch (Exception e) {
                logger.error("Fallo en la ejecucion de los procesos de generacion arhcivos de liquidacion", e);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return respuestaFinal;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#escarlarLiquidacionFallecimiento(java.lang.String,
     *      java.lang.String, java.lang.Boolean, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void escalarLiquidacionFallecimiento(String numeroRadicacion, String idTarea, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso, EstadoAporteSubsidioEnum estadoAporte, String usernameSupervisor, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.escarlarLiquidacionFallecimiento(String,String,Boolean,ModoDesembolsoEnum,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        String timerApoSubFallecimiento  = (String) CacheManager.getParametro(ParametrosSistemaConstants.TIMER_APO_SUB_FALLECIMIENTO);
        
        try {
            //TODO realizar las acciones de validación de gestión y cambio de condiciones
            Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);
            actualizarDesembolsoSubsidioLiquidacionFallecimiento(numeroRadicacion, consideracionAportes, tipoDesembolso);

            //Evaluación del Caso 2: Consideración aporte -> true, Estado aporte -> OK
            if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.OK)) {
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.ESCALADO);
                aprobarLiquidacionMasivaPrimerNivel(numeroRadicacion);

                Map<String, Object> params = new HashMap<>();
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                        SubsidioMonetarioCompositeConstants.RECHAZO);
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD,
                        SubsidioMonetarioCompositeConstants.APROBACION);
                params.put(SubsidioMonetarioCompositeConstants.SUPERVISOR_SUBSIDIO, usernameSupervisor);
                params.put(SubsidioMonetarioCompositeConstants.TIMER_APO_SUB_FALLECIMIENTO, timerApoSubFallecimiento);
                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                terminarTarea.execute();
            }
            //Evaluacion del Caso 3: Consideración aporte -> true, Estado aporte -> NO_OK 
            if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.NO_OK)) {
                /*actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
                //TODO cambiar el estado de derecho a derecho rechazado
                //TODO ejecutar acciones para realizar el envío de comunicado
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
                actualizarArchivosDescuentoLiquidacionCancelada(numeroRadicacion);
                rechazarLiquidacionMasivaPrimerNivel(numeroRadicacion, new AprobacionRechazoSubsidioMonetarioDTO());

                Map<String, Object> params = new HashMap<>();
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                        SubsidioMonetarioCompositeConstants.RECHAZO);
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD,
                        SubsidioMonetarioCompositeConstants.RECHAZO);
                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                terminarTarea.execute();*/
                
                /*
                 * Se envian los comunicados, causal 3
                 * 137 Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio
                 * 138 Notificación rechazo liquidación específica por fallecimiento - Trabajador
                 */
                //enviarComunicadosLiquidacionFallecimiento(numeroRadicacion, Long.valueOf(3));
            }
            //Evaluacion del Caso 4: Consideración aporte -> true, Estado aporte -> SIN_INFORMACION
            if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.SIN_INFORMACION)) {
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);
            }
            //Evaluacion del Caso 5: Consideración aporte -> false
            if (consideracionAportes.equals(Boolean.FALSE)) {
            	
            	ConsultarValidacionAporteMinimoFallecimiento consultaAporteMinimo = new ConsultarValidacionAporteMinimoFallecimiento(numeroRadicacion);
            	consultaAporteMinimo.execute();
            	Boolean aporteMinimo = consultaAporteMinimo.getResult();
            	
            	//Si el trabajador no cumple con aporte Minimo, y no se consideran Aportes
            	//Se ejecuta la gestión de la validación, y ejecuta la liquidación.
            	if (aporteMinimo) {
            		ConsultarCondicionPersonaRadicacion condicionPersona = new ConsultarCondicionPersonaRadicacion(numeroRadicacion);
            		condicionPersona.execute();
            		Long idCondicionPersona = condicionPersona.getResult();
            		this.guardarGestionTrabajadorLiquidacionFallecimiento(numeroRadicacion, ConjuntoValidacionSubsidioEnum.TRABAJADOR_NO_APORTE_MINIMO, idCondicionPersona);
            		this.ejecutarAccionesGestionLiquidacionFallecimiento(numeroRadicacion, idCondicionPersona);
            		//Se confirma la liquidación.
            		ConfirmarLiquidacionFallecimientoAporteMinimo confirmar = new ConfirmarLiquidacionFallecimientoAporteMinimo(numeroRadicacion);
            		confirmar.execute();
            	}
            	
                actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.ESCALADO);
                aprobarLiquidacionMasivaPrimerNivel(numeroRadicacion);

                Map<String, Object> params = new HashMap<>();
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                        SubsidioMonetarioCompositeConstants.RECHAZO);
                params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD,
                        SubsidioMonetarioCompositeConstants.APROBACION);
                params.put(SubsidioMonetarioCompositeConstants.SUPERVISOR_SUBSIDIO, usernameSupervisor);
                params.put(SubsidioMonetarioCompositeConstants.TIMER_APO_SUB_FALLECIMIENTO, timerApoSubFallecimiento);
                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                terminarTarea.execute();
            }

        } catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionFallecimiento(java.lang.String,
     *      java.lang.String, com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO,
     *      com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> rechazarLiquidacionFallecimiento(String numeroRadicacion, String idTarea,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.rechazarLiquidacionFallecimiento(String,String,AprobacionRechazoSubsidioMonetarioDTO,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        HashMap<String, String> resultado = new HashMap<>();      

        try {
            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = rechazarLiquidacionMasivaSegundoNivel(numeroRadicacion,
                    aprobacionRechazoSubsidioMonetarioDTO, Boolean.FALSE);
            
            if(solicitudLiquidacion.getLiquidacionORechazOStagingEnProceso() != null && solicitudLiquidacion.getLiquidacionORechazOStagingEnProceso()){
            	resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación por favor intentar en un momento");
            	return resultado;
            }
            Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_SEGUNDO_NIVEL,
                    SubsidioMonetarioCompositeConstants.RECHAZO);
            params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacion.getUsuarioEvaluacionPrimerNivel());
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();

            actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL);

        } catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        return null;
    }
    
    /**
     * Método que permite actualizar los datos de la solicitud de liquidación con la información parametrizada por el analista
     * @param consideracionAportes
     *        indicador de consideración de aportes
     * @param tipoDesembolso
     *        tipo de desembolso
     */
    private void actualizarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso) {
        ActualizarDesembolsoSubsidioLiquidacionFallecimiento actualizar = new ActualizarDesembolsoSubsidioLiquidacionFallecimiento(
                numeroRadicacion, consideracionAportes, tipoDesembolso);
        actualizar.execute();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#finalizarLiquidacionFallecimiento(java.lang.String,
     *      java.lang.String, java.lang.Boolean, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum,
     *      com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoLiquidacionFallecimientoDTO finalizarLiquidacionFallecimiento(String numeroRadicacion, String idTarea, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso, EstadoAporteSubsidioEnum estadoAporte, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.finalizarLiquidacionFallecimiento(String,String,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String timerApoSubFallecimiento  = (String) CacheManager.getParametro(ParametrosSistemaConstants.TIMER_APO_SUB_FALLECIMIENTO);
        ResultadoLiquidacionFallecimientoDTO resultado = new ResultadoLiquidacionFallecimientoDTO();
        resultado.setResultadoProceso(Boolean.TRUE);
        //TODO realizar las acciones de validación de gestión y cambio de condiciones
        Long idSolicitud = consultarIdSolicitudLiquidacion(numeroRadicacion);
        actualizarDesembolsoSubsidioLiquidacionFallecimiento(numeroRadicacion, consideracionAportes, tipoDesembolso); // -> PERSISTE EN SOLICITUD LIQUIDACION SUBSIDIO

        //Evaluación del Caso 2: Consideración aporte -> true, Estado aporte -> OK
        if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.OK)) { // -> consideracion viene en false
            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.APROBADA_PRIMER_NIVEL);
            //consolidación del subsidio
//            consolidarFallecimientoSubsidios(numeroRadicacion, tipoDesembolso);
            logger.info("Enviando consolidado de subsidios por fallecimiento al pago con " + tipoDesembolso.name());
            enviarConsolidadoAPagosFallecimiento(userDTO.getNombreUsuario(), numeroRadicacion, tipoDesembolso);
            actualizarEstadoDerechoLiquidacion(numeroRadicacion);
            aprobarLiquidacionMasivaPrimerNivel(numeroRadicacion);

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                    SubsidioMonetarioCompositeConstants.APROBACION);
            params.put(SubsidioMonetarioCompositeConstants.ESPERA_APORTE, consideracionAportes);
            params.put(SubsidioMonetarioCompositeConstants.TIMER_APO_SUB_FALLECIMIENTO, timerApoSubFallecimiento);
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
            
            generarArchivosSalidaEntidadDescuentoComposite(numeroRadicacion);
        }
        //Evaluacion del Caso 3: Consideración aporte -> true, Estado aporte -> NO_OK 
        if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.NO_OK)) {
            /*actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
            //TODO cambiar el estado de derecho a derecho rechazado
            //TODO ejecutar acciones para realizar el envío de comunicado
            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.CERRADA);
            actualizarArchivosDescuentoLiquidacionCancelada(numeroRadicacion);
            rechazarLiquidacionMasivaPrimerNivel(numeroRadicacion, new AprobacionRechazoSubsidioMonetarioDTO());

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                    SubsidioMonetarioCompositeConstants.RECHAZO);
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD, SubsidioMonetarioCompositeConstants.RECHAZO);
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
            
            /*
             * Se envian los comunicados, causal 3
             * 137 Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio
             * 138 Notificación rechazo liquidación específica por fallecimiento - Trabajador
             *
            enviarComunicadosLiquidacionFallecimiento(numeroRadicacion, Long.valueOf(3));*/
        }
        //Evaluacion del Caso 4: Consideración aporte -> true, Estado aporte -> SIN_INFORMACION
        if (consideracionAportes.equals(Boolean.TRUE) && estadoAporte.equals(EstadoAporteSubsidioEnum.SIN_INFORMACION)) {
            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES);
        }
        
        //Evaluacion del Caso 5: Consideración aporte -> false
        if (consideracionAportes.equals(Boolean.FALSE)) {
        	
        	ConsultarValidacionAporteMinimoFallecimiento consultaAporteMinimo = new ConsultarValidacionAporteMinimoFallecimiento(numeroRadicacion);
        	consultaAporteMinimo.execute();
        	Boolean aporteMinimo = consultaAporteMinimo.getResult();
        	
        	//Si el trabajador no cumple con aporte Minimo, y no se consideran Aportes
        	//Se ejecuta la gestión de la validación, y ejecuta la liquidación.
        	if (aporteMinimo) {
        		ConsultarCondicionPersonaRadicacion condicionPersona = new ConsultarCondicionPersonaRadicacion(numeroRadicacion);
        		condicionPersona.execute(); //
        		Long idCondicionPersona = condicionPersona.getResult(); // -> 406133, 406133, 406133, 406133
        		this.guardarGestionTrabajadorLiquidacionFallecimiento(numeroRadicacion, ConjuntoValidacionSubsidioEnum.TRABAJADOR_NO_APORTE_MINIMO, idCondicionPersona);
        		this.ejecutarAccionesGestionLiquidacionFallecimiento(numeroRadicacion, idCondicionPersona);
        		
        		//Consulta el resultado de la gestion.
        		ConsultarResultadosLiquidacionGestionAporteMinimo consultarResultadosGestion = new ConsultarResultadosLiquidacionGestionAporteMinimo(numeroRadicacion);
        		consultarResultadosGestion.execute();
        		resultado = consultarResultadosGestion.getResult();
        		
        		if(!resultado.getResultadoProceso()) {
        			return resultado;
        		} else {
        			//Se confirma la liquidación.
            		ConfirmarLiquidacionFallecimientoAporteMinimo confirmar = new ConfirmarLiquidacionFallecimientoAporteMinimo(numeroRadicacion);
            		confirmar.execute();
        		}
        	}
            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.APROBADA_PRIMER_NIVEL);
            //consolidación del subsidio
//            consolidarFallecimientoSubsidios(numeroRadicacion,tipoDesembolso);
            enviarConsolidadoAPagosFallecimiento(userDTO.getNombreUsuario(), numeroRadicacion, tipoDesembolso);
            actualizarEstadoDerechoLiquidacion(numeroRadicacion);
            aprobarLiquidacionMasivaPrimerNivel(numeroRadicacion);

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                    SubsidioMonetarioCompositeConstants.APROBACION);
            params.put(SubsidioMonetarioCompositeConstants.ESPERA_APORTE, consideracionAportes);
            params.put(SubsidioMonetarioCompositeConstants.TIMER_APO_SUB_FALLECIMIENTO, timerApoSubFallecimiento);
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
            
            generarArchivosSalidaEntidadDescuentoComposite(numeroRadicacion);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#cerrarLiquidacionFallecimiento(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String,String> cerrarLiquidacionFallecimiento(String numeroRadicacion, String idTarea, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.cerrarLiquidacionFallecimiento(String,String,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        HashMap<String, String> resultado = new HashMap<>();      

        try {
        	if(rechazarLiquidacionMasivaPrimerNivel(numeroRadicacion, new AprobacionRechazoSubsidioMonetarioDTO()) == -1L){
        		resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación por favor intentar en un momento");
            	return resultado;
        	}
        	
            Long idSolicitud = consultarIdSolicitudLiquidacion(numeroRadicacion);

            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
            //TODO cambiar el estado del derecho a derecho rechazado
            //TODO enviar comunicado
            actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.CERRADA);

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL,
                    SubsidioMonetarioCompositeConstants.RECHAZO);
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD, SubsidioMonetarioCompositeConstants.RECHAZO);
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
        } catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        
        return null;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#aprobarLiquidacionFallecimientoSupervisor(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void aprobarLiquidacionFallecimientoSupervisor(String numeroRadicacion, String idTarea,Boolean consideracionAportes, ModoDesembolsoEnum tipoDesembolso, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.aprobarLiquidacionFallecimientoSupervisor(String,String,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        try {
            SolicitudLiquidacionSubsidioModeloDTO solicituLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);

            actualizarEstadoSolicitudLiquidacion(solicituLiquidacionDTO.getIdProcesoLiquidacionSubsidio(),
                    EstadoProcesoLiquidacionEnum.APROBADA_SEGUNDO_NIVEL);
            //consolidación del subsidio
            //consolidarFallecimientoSubsidios(numeroRadicacion,tipoDesembolso);
            enviarConsolidadoAPagosFallecimiento(userDTO.getNombreUsuario(), numeroRadicacion, solicituLiquidacionDTO.getModoDesembolso());
            actualizarEstadoDerechoLiquidacion(numeroRadicacion);
            SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = aprobarLiquidacionMasivaSegundoNivel(numeroRadicacion,
                    new AprobacionRechazoSubsidioMonetarioDTO());

            Map<String, Object> params = new HashMap<>();
            params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_SEGUNDO_NIVEL,
                    SubsidioMonetarioCompositeConstants.APROBACION);
            params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacionDTO.getUsuarioEvaluacionPrimerNivel());
            params.put(SubsidioMonetarioCompositeConstants.ESPERA_APORTE, consideracionAportes);
            TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
            terminarTarea.execute();
            
            generarArchivosSalidaEntidadDescuentoComposite(numeroRadicacion);
        } catch (Exception e) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#consultarTrazabilidadSubsidioEspecificoFallecimiento(java.lang.String)
     */
    @Override
    public List<TrazabilidadSubsidioEspecificoDTO> consultarTrazabilidadSubsidioEspecificoFallecimiento(String numeroRadicacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.consultarTrazabilidadSubsidioEspecificoFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<TrazabilidadSubsidioEspecificoDTO> trazabilidadSubsidio = new ArrayList<>();
        try {
            Long identificadorLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);
            trazabilidadSubsidio = obtenerTrazabilidadSubsidioEspecifico(identificadorLiquidacion);

            Boolean indicadorRechazo = Boolean.FALSE;
            for (TrazabilidadSubsidioEspecificoDTO trazabilidadSubsidioEspecificoDTO : trazabilidadSubsidio) {
                String actividadRealizada = null;
                if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud().equals(EstadoProcesoLiquidacionEnum.RADICADO)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.INICIAR_VALIDACIONES.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.APROBADA_PRIMER_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_LIQUIDACION_ANALISTA.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.RECHAZAR_LIQUIDACION_ANALISTA.getDescripcion();
                    indicadorRechazo = Boolean.TRUE;
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud().equals(EstadoProcesoLiquidacionEnum.ESCALADO)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.ESCALAR_LIQUIDACION.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.APROBADA_SEGUNDO_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_LIQUIDACION_SUPERVISOR.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.RECHAZAR_LIQUIDACION_SUPERVISOR.getDescripcion();
                    indicadorRechazo = Boolean.TRUE;
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud()
                        .equals(EstadoProcesoLiquidacionEnum.PENDIENTE_PAGO_APORTES)) {
                    actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.ESPERAR_APORTE_OK.getDescripcion();
                }
                else if (trazabilidadSubsidioEspecificoDTO.getEstadoSolicitud().equals(EstadoProcesoLiquidacionEnum.CERRADA)) {
                    if (indicadorRechazo) {
                        actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.CERRAR_SOLICITUD_RECHAZADA.getDescripcion();
                    }
                    else {
                        actividadRealizada = ActividadRealizadaSubsidioEspecificoEnum.APROBAR_DISPERSION.getDescripcion();
                    }
                }

                trazabilidadSubsidioEspecificoDTO.setActividadRealizada(actividadRealizada);
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return trazabilidadSubsidio;
    }
    
      
    /**
     * Método que permite enivar la información de subsidios a pagos
     * @param usuario
     *        nombre del usuario
     * @param numeroRadicado
     *        valor del número de radicado
     * @param modoDesembolso
     *        Modo en que se realiza el desembolso
     */
    private void enviarConsolidadoAPagosFallecimiento(String usuario, String numeroRadicado, ModoDesembolsoEnum modoDesembolso) {
        logger.info("Enviando consolidado de subsidios a pagos para el radicado: " + numeroRadicado);
        EnviarResultadoLiquidacionAPagosFallecimiento envioPagos = new EnviarResultadoLiquidacionAPagosFallecimiento(numeroRadicado,
                usuario, modoDesembolso);
        envioPagos.execute();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#consultarValidacionesTrabajadorGestionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public List<ResultadoValidacionFallecimientoDTO> consultarValidacionesTrabajadorGestionFallecimiento(String numeroRadicacion,
            Long condicionPersona) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.consultarValidacionesTrabajadorGestionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConjuntoValidacionSubsidioEnum validacionFallida = consultarValidacionFallidaPersonaFallecimiento(numeroRadicacion,
                condicionPersona);
        List<ConjuntoValidacionSubsidioEnum> conjuntoValidaciones = consultarValidacionesTipoProceso(
                TipoValidacionLiquidacionEspecificaEnum.BENEFICIARIO_TRABAJADOR);

        List<ResultadoValidacionFallecimientoDTO> resultadosValidacion = new ArrayList<>();

        Function<ConjuntoValidacionSubsidioEnum, ResultadoValidacionFallecimientoDTO> mapper = item -> {
            ResultadoValidacionFallecimientoDTO resultado = new ResultadoValidacionFallecimientoDTO();
            resultado.setValidacion(item);
            if (validacionFallida != null && item.equals(validacionFallida)) {
                resultado.setResultado(Boolean.FALSE);
            }
            else {
                resultado.setResultado(Boolean.TRUE);
            }
            return resultado;
        };

        if (validacionFallida == null) {
            //Se evalua el caso en el que el trabajador cumple todas las validaciones
            resultadosValidacion = conjuntoValidaciones.stream().map(mapper).collect(Collectors.toList());
        }
        else {
            //Se evalua el caso en el que el trabajador no cumple con una validación
            resultadosValidacion = conjuntoValidaciones.stream().limit(conjuntoValidaciones.indexOf(validacionFallida) + 1).map(mapper)
                    .collect(Collectors.toList());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadosValidacion;
    }

    /**
     * Método que permite consultar la validación fallida por la persona en la liquidación de fallecimiento, en caso de que exista
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de la condición persona
     * @return Validación fallida
     */
    private ConjuntoValidacionSubsidioEnum consultarValidacionFallidaPersonaFallecimiento(String numeroRadicacion, Long condicionPersona) {
        ConsultarValidacionFallidaPersonaFallecimiento consultar = new ConsultarValidacionFallidaPersonaFallecimiento(numeroRadicacion,
                condicionPersona);
        consultar.execute();
        return consultar.getResult();
    }

    /**
     * Método que permite consultar las validaciones a partir del tipo de proceso parametrizado
     * @param tipoProceso
     *        Tipo de proceso
     * @return Lista de validaciones
     */
    private List<ConjuntoValidacionSubsidioEnum> consultarValidacionesTipoProceso(TipoValidacionLiquidacionEspecificaEnum tipoProceso) {
        ConsultarValidacionesTipoProceso consultar = new ConsultarValidacionesTipoProceso(tipoProceso);
        consultar.execute();
        return consultar.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#consultarValidacionesBeneficiarioGestionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public List<ResultadoValidacionFallecimientoDTO> consultarValidacionesBeneficiarioGestionFallecimiento(String numeroRadicacion,
            Long condicionPersona) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.consultarValidacionesBeneficiarioGestionFallecimiento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ConjuntoValidacionSubsidioEnum validacionFallida = consultarValidacionFallidaPersonaFallecimiento(numeroRadicacion,
                condicionPersona);
        List<ConjuntoValidacionSubsidioEnum> conjuntoValidaciones = consultarValidacionesTipoProceso(
                TipoValidacionLiquidacionEspecificaEnum.FALLECIMIENTO_BENEFICIARIO);

        List<ResultadoValidacionFallecimientoDTO> resultadosValidacion = new ArrayList<>();

        Function<ConjuntoValidacionSubsidioEnum, ResultadoValidacionFallecimientoDTO> mapper = item -> {
            ResultadoValidacionFallecimientoDTO resultado = new ResultadoValidacionFallecimientoDTO();
            resultado.setValidacion(item);
            if (validacionFallida != null && item.equals(validacionFallida)) {
                resultado.setResultado(Boolean.FALSE);
            }
            else {
                resultado.setResultado(Boolean.TRUE);
            }
            return resultado;
        };

        if (validacionFallida == null) {
            //Se evalua el caso en el que el trabajador cumple todas las validaciones
            resultadosValidacion = conjuntoValidaciones.stream().map(mapper).collect(Collectors.toList());
        }
        else {
            //Se evalua el caso en el que el trabajador no cumple con una validación
            resultadosValidacion = conjuntoValidaciones.stream().limit(conjuntoValidaciones.indexOf(validacionFallida) + 1).map(mapper)
                    .collect(Collectors.toList());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultadosValidacion;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#guardarGestionTrabajadorLiquidacionFallecimiento(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum)
     */
    @Override
    public void guardarGestionTrabajadorLiquidacionFallecimiento(String numeroRadicacion, ConjuntoValidacionSubsidioEnum validacion,
            Long idCondicionPersona) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.ConjuntoValidacionSubsidioEnum(ConjuntoValidacionSubsidioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);
        //Se obtiene el identificador de la solicitud de liquidación
        Long idSolicitudLiquidacion = solicitudLiquidacionDTO.getIdProcesoLiquidacionSubsidio();
        //Se obtiene el identificador del conjunto validación gestionado
        Long idConjuntoValidacion = consultarIdentificadorConjuntoValidacion(validacion);
        //Se crea el registro en AplicacionValidacionSubsidio
        AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO = new AplicacionValidacionSubsidioModeloDTO();
        aplicacionValidacionDTO.setIdConjuntoValidacionSubsidio(idConjuntoValidacion);
        aplicacionValidacionDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
        aplicacionValidacionDTO.setIsValidable(Boolean.FALSE);

        Long idAplicacionValidacionSubsidio = registrarAplicacionValidacionSubsidio(aplicacionValidacionDTO);

        //Se consulta el identificador de la persona en core a partir del idCondicionPersona y el identificador de la persona liquidación específica
        Long idPersonaCore = consultarIdentificadorPersonaCore(numeroRadicacion, idCondicionPersona);
        Long idPersonaLiquidacionEspecifica = seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(numeroRadicacion,
                idPersonaCore);

        //Se crea el registro en AplicacionValidacionSubsidioPersona
        AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO = new AplicacionValidacionSubsidioPersonaModeloDTO();
        aplicacionValidacionPersonaDTO.setIdAplicacionValidacionSubsidio(idAplicacionValidacionSubsidio);
        aplicacionValidacionPersonaDTO.setIdPersonaLiquidacionEspecifica(idPersonaLiquidacionEspecifica);

        registrarAplicacionValidacionSubsidioPersona(aplicacionValidacionPersonaDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#guardarGestionBeneficiarioLiquidacionFallecimiento(com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum)
     */
    @Override
    public void guardarGestionBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, ConjuntoValidacionSubsidioEnum validacion,
            Long idCondicionPersona) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.guardarGestionBeneficiarioLiquidacionFallecimiento(ConjuntoValidacionSubsidioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);
        //Se obtiene el identificador de la solicitud de liquidación
        Long idSolicitudLiquidacion = solicitudLiquidacionDTO.getIdProcesoLiquidacionSubsidio();
        //Se obtiene el identificador del conjunto validación gestionado
        Long idConjuntoValidacion = consultarIdentificadorConjuntoValidacion(validacion);
        //Se crea el registro en AplicacionValidacionSubsidio
        AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO = new AplicacionValidacionSubsidioModeloDTO();
        aplicacionValidacionDTO.setIdConjuntoValidacionSubsidio(idConjuntoValidacion);
        aplicacionValidacionDTO.setIdSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
        aplicacionValidacionDTO.setIsValidable(Boolean.FALSE);

        Long idAplicacionValidacionSubsidio = registrarAplicacionValidacionSubsidio(aplicacionValidacionDTO);

        //Se consulta el identificador de la persona en core a partir del idCondicionPersona y el identificador de la persona liquidación específica
        Long idPersonaCore = consultarIdentificadorPersonaCore(numeroRadicacion, idCondicionPersona);
        Long idPersonaLiquidacionEspecifica = seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(numeroRadicacion,
                idPersonaCore);

        //Se crea el registro en AplicacionValidacionSubsidioPersona
        AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO = new AplicacionValidacionSubsidioPersonaModeloDTO();
        aplicacionValidacionPersonaDTO.setIdAplicacionValidacionSubsidio(idAplicacionValidacionSubsidio);
        aplicacionValidacionPersonaDTO.setIdPersonaLiquidacionEspecifica(idPersonaLiquidacionEspecifica);

        registrarAplicacionValidacionSubsidioPersona(aplicacionValidacionPersonaDTO);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#ejecutarAccionesGestionLiquidacionFallecimiento(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public void ejecutarAccionesGestionLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionPersona) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.ejecutarAccionesGestionLiquidacionFallecimiento(String,Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacionDTO = consultarSolicitudLiquidacion(numeroRadicacion);

        List<Long> identificadoresCondiciones = new ArrayList<>();
        identificadoresCondiciones.add(idCondicionPersona);
        Map<String, CondicionPersonaLiquidacionDTO> condicionesPersonas = consultarCondicionesPersonas(numeroRadicacion,
                identificadoresCondiciones);
        condicionesPersonas = convertirMapa(condicionesPersonas);

        if (condicionesPersonas.containsKey(idCondicionPersona.toString())) {
            CondicionPersonaLiquidacionDTO condicionPersonaDTO = condicionesPersonas.get(idCondicionPersona.toString());

            Date periodoRegular = consultarPeriodoRegularRadicacion(numeroRadicacion);

            //Se invoca el servicio que se encarga de ejecutar el Orquestador de fallecimiento posterior a la gestión realizada
            Boolean beneficiarioFallecido = solicitudLiquidacionDTO.getTipoLiquidacionEspecifica()
                    .equals(TipoLiquidacionEspecificaEnum.DEFUNCION_BENEFICIARIO) ? Boolean.TRUE : Boolean.FALSE;
            ejecutarSPLiquidacionFallecimientoGestionPersona(numeroRadicacion, periodoRegular.getTime(), beneficiarioFallecido,
                    condicionPersonaDTO.getTipoIdentificacion(), condicionPersonaDTO.getNumeroIdentificacion());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método que se encarga de realizar la conversión del mapa obtenido del microservicio ya que el cliente convierte el valor en un
     * LinkedHashMap con los atributos del objeto
     * @param condiciones
     *        mapa de condiciones de personas
     * @return mapa con formato requerido
     */
    @SuppressWarnings("unchecked")
    private Map<String, CondicionPersonaLiquidacionDTO> convertirMapa(Map<String, CondicionPersonaLiquidacionDTO> condiciones) {
        Map<String, CondicionPersonaLiquidacionDTO> mapa = new HashMap<>();
        for (Map.Entry<String, CondicionPersonaLiquidacionDTO> entry : condiciones.entrySet()) {
            CondicionPersonaLiquidacionDTO condicion = new CondicionPersonaLiquidacionDTO();
            Object entrada = entry.getValue();
            HashMap<Object, Object> valoresEntrada = (HashMap<Object, Object>) entrada;

            condicion.setIdCondicionPersona(Long.valueOf(valoresEntrada.get("idCondicionPersona").toString()));
            condicion.setTipoIdentificacion(TipoIdentificacionEnum.valueOf(valoresEntrada.get("tipoIdentificacion").toString()));
            condicion.setNumeroIdentificacion((String) valoresEntrada.get("numeroIdentificacion"));
            condicion.setRazonSocial((String) valoresEntrada.get("razonSocial"));

            mapa.put(condicion.getIdCondicionPersona().toString(), condicion);
        }
        return mapa;
    }
    
    /**
     * Método que permite consultar la información de las personas dentro de un proceso de liquidación
     * 
     * @param numeroRadicacion
     *        <code>String</code>
     *        Valor del número de radicación
     * 
     * @param identificadoresCondiciones
     *        <code>List<Long></code>
     *        Lista de identificadores de condición
     * 
     * @return Información de la condición de las personas
     */
    private Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(String numeroRadicacion,
            List<Long> identificadoresCondiciones) {
        ConsultarCondicionesPersonas consultar = new ConsultarCondicionesPersonas(numeroRadicacion, identificadoresCondiciones);
        consultar.execute();
        return consultar.getResult();
    }
    
    /**
     * Método que permite consultar el identificador relacionado al conjunto validación parametrizado
     * @param validacion
     *        Valor de la validación
     * @return Identificador del conjunto validación
     */
    private Long consultarIdentificadorConjuntoValidacion(ConjuntoValidacionSubsidioEnum validacion) {
        ConsultarIdentificadorConjuntoValidacion consultar = new ConsultarIdentificadorConjuntoValidacion(validacion);
        consultar.execute();
        return consultar.getResult();
    }
    
    /**
     * Método que se encarga de realizar el registro de la AplicacionValidacionSubsidio
     * @param aplicacionValidacionDTO
     *        DTO con la información de la validación
     * @return Identificador del registro
     */
    private Long registrarAplicacionValidacionSubsidio(AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO) {
        RegistrarAplicacionValidacionSubsidio registrar = new RegistrarAplicacionValidacionSubsidio(aplicacionValidacionDTO);
        registrar.execute();
        return registrar.getResult();
    }
    
    /**
     * Método que se encarga de consultar el identificador de la persona en core a partir del identificador de condición
     * @param idCondicionPersona
     *        Valor del identificador de condición
     * @return Identificador de la persona en core
     */
    private Long consultarIdentificadorPersonaCore(String numeroRadicacion, Long idCondicionPersona) {
        ConsultarIdentificadorPersonaCore consultar = new ConsultarIdentificadorPersonaCore(numeroRadicacion, idCondicionPersona);
        consultar.execute();
        return consultar.getResult();
    }
    
    /**
     * Método que permite obtener el identificador de la personaLiquidaciónEspecifica para la creación de la validación
     * @param numeroRadicacion
     *        Valor del número de radicado
     * @param idPersona
     *        Identificador de la persona
     * @return Identificador de la PersonaLiquidacionEspecifica
     */
    private Long seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        SeleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento seleccionar = new SeleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(
                idPersona, numeroRadicacion);
        seleccionar.execute();
        return seleccionar.getResult();
    }
    
    /**
     * Método que se encarga de realizar el registro de la AplicacionValidacionSubsidioPersona
     * @param aplicacionValidacionDTO
     *        DTO con la información de la validación
     * @return Identificador del registro
     */
    private Long registrarAplicacionValidacionSubsidioPersona(AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionDTO) {
        RegistrarAplicacionValidacionSubsidioPersona registrar = new RegistrarAplicacionValidacionSubsidioPersona(aplicacionValidacionDTO);
        registrar.execute();
        return registrar.getResult();
    }
    
    /**
     * Método que se encarga de consultar la información de la persona a partir de su identificador
     * @param idPersona
     *        Identificador de la persona
     * @return DTO con la información de la persona
     */
    private PersonaModeloDTO consultarPersona(Long idPersona) {
        ConsultarPersona consultar = new ConsultarPersona(idPersona);
        consultar.execute();
        return consultar.getResult();
    }
    
    /**
     * Método que se encarga de obtener el periodo regular asociado a una liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return Fecha que representa el periodo regular
     */
    private Date consultarPeriodoRegularRadicacion(String numeroRadicacion) {
        ConsultarPeriodoRegularLiquidacionPorRadicado consultar = new ConsultarPeriodoRegularLiquidacionPorRadicado(numeroRadicacion);
        consultar.execute();
        return consultar.getResult();
    }
    
    /**
     * Método que se encarga de ejecutar el SP de liquidación por fallecimiento para tras la gestión de una persona (trabajador o
     * beneficiario)
     * @param numeroRadicado
     *        Valor del número de radicado
     * @param periodo
     *        Periodo en el que se realiza la liquidación
     * @param beneficiarioFallecido
     *        Indicador de fallecimiento para beneficiario
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona gestionada
     * @param numeroIdentificacion
     *        Número de identificación de la persona gestionada
     */
    private void ejecutarSPLiquidacionFallecimientoGestionPersona(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        EjecutarSPLiquidacionFallecimientoGestionPersona ejecutar = new EjecutarSPLiquidacionFallecimientoGestionPersona(periodo,
                numeroRadicado, numeroIdentificacion, tipoIdentificacion, beneficiarioFallecido);
        ejecutar.execute();
    }
    
    /**
     * Método que permite obtener el identificador de la personaLiquidaciónEspecifica para la creación de la validación
     * @param numeroRadicacion
     *        Valor del número de radicado
     * @param idPersona
     *        Identificador de la persona
     * @return Identificador de la PersonaLiquidacionEspecifica
     */
    private Long seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idPersona) {
        SeleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento seleccionar = new SeleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(
                idPersona, numeroRadicacion);
        seleccionar.execute();
        return seleccionar.getResult();
    }

    /**
     * Metodo encargado de llamar el servicio que permite realizar un registro
     * de una solicitud que se realiza desde un servicio expuesto a ANIBOL.
     * @param idProceso 
     * @param idSolicitudLiquidacion 
     * 
     * @param registroSolicitudAnibolModeloDTO
     *        <code>RegistroSolicitudAnibolModeloDTO</code>
     *        DTO que contiene la información para realizar el registro de solicitud.
     * @return <code>Long</code>
     *          Identificador del registro de solicitud de ANIBOL.
     */
    private Long crearRegistroSolicitudAnibol(String idProceso, Long idSolicitudLiquidacion, String numeroRadicado){
        RegistroSolicitudAnibolModeloDTO registroSolicitudAnibol = new RegistroSolicitudAnibolModeloDTO();
        registroSolicitudAnibol.setFechaHoraRegistro(new Date());
        registroSolicitudAnibol.setTipoOperacionAnibol(TipoOperacionAnibolEnum.DISPERSION);
        registroSolicitudAnibol.setIdProceso(idProceso);
        registroSolicitudAnibol.setSolicitudLiquidacionSubsidio(idSolicitudLiquidacion);
        registroSolicitudAnibol.setNumeroRadicacion(numeroRadicado);
        registroSolicitudAnibol.setEstadoSolicitudAnibol(EstadoSolicitudAnibolEnum.EN_ESPERA);
        
        RegistrarSolicitudAnibol registroSolicitud = new RegistrarSolicitudAnibol(registroSolicitudAnibol);
        registroSolicitud.execute();
        return registroSolicitud.getResult();
    }
    
    /**
     * Método encargado de actualizar el registro de solicitud de ANIBOL llamando el servicio correspondiente de PagosSubsidioMonetario
     * 
     * @param idRegistroSolicitudAnibol
     *        <code>Long</code>
     *        Identificador del registro de solicitud de ANIBOL registrado.
     * @param parametrosOUT
     *        <code>String</code>
     *        Variable que contiene los parametros de salida por parte de ANIBOl en formato Json
     */
    private void actualizarSolicitudRegistroAnibol(Long idRegistroSolicitudAnibol, String parametrosOUT){
        ActualizarRegistroSolicitudAnibol actualizacionSolicitudAnibol = new ActualizarRegistroSolicitudAnibol(idRegistroSolicitudAnibol, parametrosOUT);
        actualizacionSolicitudAnibol.execute();
    }
    
    
    /**
     * Método que se encarga del envío de comunicados de una liquidación segun el tipo
     * Masiva - Especifica ajustes y reconocimiento
     * 063 Notificación de dispersión de pagos al empleador 
     * 064 Notificación de dispersión de pagos al trabajador 
     * 065 Notificación de dispersión de pagos al administrador del subsidio
     */
    private void enviarComunicadosLiquidacion(String numeroSolicitud) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.enviarComunicadosLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            SolicitudLiquidacionSubsidioModeloDTO sol = consultarSolicitudLiquidacion(numeroSolicitud);
            ProcesoEnum proceso = null;
            switch (sol.getTipoLiquidacion()) {
            case MASIVA: proceso = ProcesoEnum.SUBSIDIO_MONETARIO_MASIVO; 
                break;
            case RECONOCIMIENTO_DE_SUBSIDIOS:
            case AJUSTES_DE_CUOTA: proceso = ProcesoEnum.SUBSIDIO_MONETARIO_ESPECIFICO;
                break;
            case SUBSUDIO_DE_DEFUNCION: proceso = ProcesoEnum.LIQUIDACION_SUBSIDIO_FALLECIMIENTO;
                break;
            }
            envioComunicados.enviarComunicadosLiquidacion(numeroSolicitud, proceso);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método que invoca a quien crea el registro del avance del porcentaje
     *  
     * @param numeroRadicacion
     * @author jocampo
     */
    private void iniciarPorcentajeAvanceProcesoLiquidacion(String numeroRadicacion) {
        String firmaMetodo = "SubsidioBusiness.iniciarPorcentajeAvanceProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        IniciarPorcentajeAvanceProcesoLiquidacion iniciarPorcentajeAvance = new IniciarPorcentajeAvanceProcesoLiquidacion(numeroRadicacion);
        iniciarPorcentajeAvance.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    /**
     * Método que invoca el cliente de actualizar las observaciones del proceso
     * 
     * @param idProcesoLiquidacionSubsidio
     * @param observacionesProceso
     */
    private Map<String,String> cancelarMasivaActualizarObservacionesProceso(String numeroRadicacion, String observacionesProceso) {
        String firmaMetodo = "SubsidioBusiness.actualizarObservacionesProceso(String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        CancelarMasivaActualizarObservacionesProceso act = new CancelarMasivaActualizarObservacionesProceso(numeroRadicacion, observacionesProceso);
        act.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return act.getResult();
    }
    
    /**
     * Método que se encarga de cancelar el SP de la liquidación masiva
     * 
     * @param numeroRadicado
     */
    private void cancelarProcesoLiquidacion(String numeroRadicado) {
        String firmaMetodo = "SubsidioBusiness.cancelarProcesoLiquidacion(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        CancelarProcesoLiquidacion cancelar = new CancelarProcesoLiquidacion(numeroRadicado);
        cancelar.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }   
 
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#generarArchivoLiquidacionEmpleador(java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response generarArchivoLiquidacionEmpleador(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.generarArchivoLiquidacionEmpleador(String, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            InformacionArchivoDTO archivoLiquidacion = generarArchivoResultadoLiquidacion(numeroRadicacion, tipoIdentificacion, numeroIdentificacion);
            if (archivoLiquidacion != null) {
                Response.ResponseBuilder response;
                response = Response.ok((archivoLiquidacion.getDataFile()));
                response.header("Content-Type", archivoLiquidacion.getFileType());
                response.header("Content-Disposition", "attachment; filename=" + archivoLiquidacion.getFileName());
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return response.build();
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#generarArchivoPersonasSinDerechoEmpleador(java.lang.String, com.asopagos.enumeraciones.personas.TipoIdentificacionEnum, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response generarArchivoPersonasSinDerechoEmpleador(String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.generarArchivoPersonasSinDerechoEmpleador(String, TipoIdentificacionEnum, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        final String comilla = "\"";
        Boolean indicadorRegistro = Boolean.FALSE;
        try {
            InformacionArchivoDTO archivoSinDerecho = generarArchivoResultadoPersonasSinDerecho(numeroRadicacion,
                    tipoIdentificacion, numeroIdentificacion);
            if (archivoSinDerecho != null) {
                Response.ResponseBuilder response;
                response = Response.ok((archivoSinDerecho.getDataFile()));
                response.header("Content-Type", archivoSinDerecho.getFileType());
                response.header("Content-Disposition", "attachment; filename=" + archivoSinDerecho.getFileName());
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return response.build();
            }
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw e;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#terminarTareaLiquidacionFallecimiento(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void terminarTareaLiquidacionFallecimiento(String numeroRadicacion, String idTarea, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.terminarTareaLiquidacionDispersada(String, String, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroRadicacion);

        TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), new HashMap<>());
        terminarTarea.execute();

        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.DISPERSADA);
        actualizarFechaDispersion(idSolicitudLiquidacion);
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);

        // Envío de comunicados
        // 063 Notificación de dispersión de pagos al empleador 
        // 064 Notificación de dispersión de pagos al trabajador 
        // 065 Notificación de dispersión de pagos al administrador del subsidio
        //enviarComunicadosLiquidacion(numeroRadicacion);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }


    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#inicializarPantallaSolicitudLiquidacionComposite(com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionComposite(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.inicializarPantallaSolicitudLiquidacion()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = inicializarPantallaSolicitudLiquidacion();
        if(result.getIdInstanciaProceso() != null) {
            TareaDTO tarea = obtenerTareaActivaInstancia(Long.valueOf(result.getIdInstanciaProceso()));
            if(tarea != null){
                result.setIdTarea(tarea.getId());
            }
        }
        //SEG
        System.out.println("result final: " + result.getNumeroRadicado());
        System.out.println("result final: " + result.getTipoProcesoLiquidacion());
        
        
      //ENDSEG
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#inicializarPantallaSolicitudLiquidacionMasivaComposite(com.asopagos.rest.security.dto.UserDTO)
     */
    /*@Override
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerradaComposite(UserDTO userDTO) {
        String firmaMetodo = "SubsidioBusiness.inicializarPantallaSolicitudLiquidacionCerrada()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        IniciarSolicitudLiquidacionMasivaDTO result = inicializarPantallaSolicitudLiquidacionCerrada();
        if(result.getIdInstanciaProceso() != null) {
            TareaDTO tarea = obtenerTareaActivaInstancia(Long.valueOf(result.getIdInstanciaProceso()));
            if(tarea != null){
                result.setIdTarea(tarea.getId());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }*/
    
    /**
     * Metodo que invoca el micro servicio de inicializar las pantallas
     * @return
     */
    private IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacion() {
        InicializarPantallaSolicitudLiquidacion iniSol = new InicializarPantallaSolicitudLiquidacion();
        iniSol.execute();
        return iniSol.getResult();
    }
    
    /**
     * Metodo que invoca el micro servicio de inicializar las pantallas de liquidacion masiva
     * @return
     */
    /*private IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerrada() {
        InicializarPantallaSolicitudLiquidacionCerrada iniSol = new InicializarPantallaSolicitudLiquidacionCerrada();
        iniSol.execute();
        return iniSol.getResult();
    }*/
    
    /**
     * Metodo que invoca el micro servicio de tareas humanas por usuairo
     * @param idInstanciaProceso
     * @return
     */
    private TareaDTO obtenerTareaActivaInstancia(Long idInstanciaProceso) {
        ObtenerTareaActivaInstancia tarea = new ObtenerTareaActivaInstancia(idInstanciaProceso);
        tarea.execute();
        return tarea.getResult();
    }
     
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#finalizarCancelarLiquidacionFallecimientoCasos1y3(java.lang.String,
     *      java.lang.String, java.lang.Boolean, com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum,
     *      com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String,String> finalizarCancelarLiquidacionFallecimientoCasos1y3(String numeroRadicacion, String idTarea, Boolean consideracionAportes,
            ModoDesembolsoEnum tipoDesembolso, EstadoAporteSubsidioEnum estadoAporte, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.finalizarCancelarLiquidacionFallecimientoCasos1y3(String, String, Boolean, ModoDesembolsoEnum, EstadoAporteSubsidioEnum ,Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        HashMap<String, String> resultado = new HashMap<>();      
        
        if(rechazarLiquidacionMasivaPrimerNivel(numeroRadicacion, new AprobacionRechazoSubsidioMonetarioDTO()) == -1L){
            resultado.put("mensaje", "Proceso en ejecución, no es posible eliminar la liquidación en este momento, por favor intentar en un momento");
            return resultado;
        }
        

        Long idSolicitud = consultarIdSolicitudLiquidacion(numeroRadicacion);
        actualizarDesembolsoSubsidioLiquidacionFallecimiento(numeroRadicacion, consideracionAportes, tipoDesembolso);

        actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
        //TODO cambiar el estado de derecho a derecho rechazado
        actualizarEstadoSolicitudLiquidacion(idSolicitud, EstadoProcesoLiquidacionEnum.CERRADA);
        actualizarArchivosDescuentoLiquidacionCancelada(numeroRadicacion);

        Map<String, Object> params = new HashMap<>();
        params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_APROBADA_PRIMER_NIVEL, SubsidioMonetarioCompositeConstants.RECHAZO);
        params.put(SubsidioMonetarioCompositeConstants.FALLECIMIENTO_ESCALAR_SOLICITUD, SubsidioMonetarioCompositeConstants.RECHAZO);
        TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
        terminarTarea.execute();

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }
    
    /**
     * Metodo encargado de consolidar los subsidios de fallecimiento
     * @param numeroRadicado
     *        <code>String</code>
     *        Número de radicado
     */
    private void consolidarFallecimientoSubsidios(String numeroRadicado, ModoDesembolsoEnum modoDesembolso){
        ConsolidarSubsidiosFallecimiento consolidacion = new ConsolidarSubsidiosFallecimiento(numeroRadicado, modoDesembolso);
        consolidacion.execute();
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.fovis.composite.service.FovisCompositeService#validarArchivoCruce(com.asopagos.dto.CargueArchivoCruceFovisDTO,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoValidacionArchivoBloqueoCMDTO validarArchivoBloqueoCM(CargueArchivoBloqueoCMDTO cargue, UserDTO userDTO) {
        logger.info("Inicia servicio validarArchivoBloqueoCM(validarArchivoBloqueoCM)");
        //try {
            // Se obtiene la informacion del archivo cargado
            
            
            // ********************** MOCK PARA PROBAR ARCHIVO DE BLOQUEO *******************************************
            InformacionArchivoDTO archivo = obtenerArchivo(cargue.getCodigoIdentificacionECM());
            // File file = new File("D:\\archivosPruebaBloqueo\\CCCF010_BLOQUEO_CUOTA_MONETARIA_20190513_A001.txt");
             
           /*  File file = new File("E:\\archivosPruebaBloqueo\\CCF016_BLOQUEO_CUOTA_MONETARIA_20200730_A003.txt");  
             byte[] bArray = new byte[(int) file.length()];
             FileInputStream fis = null;
             try{
                 fis = new FileInputStream(file);
                 fis.read(bArray);
                 fis.close();        
                 
             }catch(IOException ioExp){
                 ioExp.printStackTrace();
             }
             
            InformacionArchivoDTO archivo = new InformacionArchivoDTO();   
           archivo.setDataFile(bArray);
            archivo.setFileName("CCF016_BLOQUEO_CUOTA_MONETARIA_20200730_A003.txt.txt");*/
           
            // ******************************* FIN MOCK *************************************************************
            
            
            // Se verifica la estructura y se obtiene las lineas para procesarlas
            cargue.setArchivo(archivo);
            VerificarEstructuraArchivoBloquedoCM verificarArchivo = new VerificarEstructuraArchivoBloquedoCM(cargue);   
            verificarArchivo.execute();  
            ResultadoValidacionArchivoBloqueoCMDTO resultDTO = verificarArchivo.getResult();

            return resultDTO;
          /*
             
            if (resultDTO.getResultadoCruceFOVISDTO().getExcepcion() == null || !resultDTO.getResultadoCruceFOVISDTO().getExcepcion()) {
                // Se registra el estado en la consola
                ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
                String codigoCaja;
                try {
                    codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
                } catch (Exception e) {
                    codigoCaja = null;
                }
                consolaEstadoCargue.setCcf(codigoCaja);
                EstadoCargueMasivoEnum estadoProcesoMasivo = EstadoCargueMasivoEnum.EN_PROCESO;
                if (resultDTO.getEstadoCargue().equals(EstadoCargaMultipleEnum.CANCELADO)) {
                    estadoProcesoMasivo = EstadoCargueMasivoEnum.FINALIZADO;
                }
                consolaEstadoCargue.setEstado(estadoProcesoMasivo);
                consolaEstadoCargue.setFileLoaded_id(resultDTO.getFileDefinitionId());
                consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
                consolaEstadoCargue.setIdentificacionECM(cargue.getCodigoIdentificacionECM());
                consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_CRUCE_FOVIS);
                consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
                consolaEstadoCargue.setCargue_id(resultDTO.getIdCargue());
                consolaEstadoCargue.setNumRegistroConErrores(resultDTO.getRegistrosConErrores());
                consolaEstadoCargue.setNumRegistroObjetivo(resultDTO.getTotalRegistro());
                consolaEstadoCargue.setNumRegistroProcesado(resultDTO.getTotalRegistro());
                consolaEstadoCargue.setNumRegistroValidados(resultDTO.getRegistrosValidos());
                registrarConsolaEstado(consolaEstadoCargue);

                // Registrar estado procesado
                resultDTO.setEstadoCargue(EstadoCargaMultipleEnum.CERRADO);

                // Se actualiza el estado en la consola
                ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
                conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
                conCargueMasivo.setFechaFin(Calendar.getInstance().getTimeInMillis());
                conCargueMasivo.setGradoAvance(EstadoCargueArchivoActualizacionEnum.PROCESADO.getGradoAvance());
                conCargueMasivo.setCargue_id(resultDTO.getIdCargue());
                conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_CRUCE_FOVIS);
                actualizarCargueConsolaEstado(resultDTO.getIdCargue(), conCargueMasivo);
            }
            logger.info("Finaliza servicio validarArchivoCruce(CargueArchivoCruceFovisDTO)");
            return resultDTO;
                
        } catch (Exception e) {
            logger.error("Error - Finaliza servicio validarArchivoCruce(CargueArchivoCruceFovisDTO)");
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        */
    }
    
    
    /**
     * Consulta un archivo deacuerdo con el id del ECM
     * 
     * @param archivoId
     * @return
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia obtenerArchivo(String)");
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }

    @Override
    public Boolean verificarPersonasSinCondicionesAprobarResultadoComposite(String numeroRadicado) {
        
        VerificarPersonasSinCondicionesDTO listas = new VerificarPersonasSinCondicionesDTO();
        RespuestaVerificarPersonasSinCondicionesDTO respuestaVerificacion = new RespuestaVerificarPersonasSinCondicionesDTO();
        
        VerificarPersonasSinCondicionesAprobarResultados verificacionCore = new VerificarPersonasSinCondicionesAprobarResultados(numeroRadicado);
        verificacionCore.execute();
        
        listas = verificacionCore.getResult();
        
        VerificarPersonasSinCondiciones verificacionSubsidio = new VerificarPersonasSinCondiciones(listas);
        verificacionSubsidio.execute();
        
        respuestaVerificacion = verificacionSubsidio.getResult();

        return respuestaVerificacion.getSinCondiciones();
    }
    
    /**
     * Consulta un archivo deacuerdo con el id del ECM
     * 
     * @param archivoId
     * @return
     */
    private boolean obtenerMarcaProcesoAprobarSegNivel(String numeroRadicado) {
        String firmaMetodo = "obtenerMarcaProcesoAprobarSegNivel.finalizarCancelarLiquidacionFallecimientoCasos1y3(String, String, Boolean, ModoDesembolsoEnum, EstadoAporteSubsidioEnum ,Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        ValidarMarcaAprobacionSegNivel validacion = new ValidarMarcaAprobacionSegNivel(numeroRadicado);        
        validacion.execute(); 
        Boolean resultado = (Boolean) validacion.getResult();
        logger.debug("resultado composite:" + resultado);
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return resultado;
    }
    
    private void eliminarMarcaAprobacionSegNivel() {
        String firmaMetodo = "obtenerMarcaProcesoAprobarSegNivel.finalizarCancelarLiquidacionFallecimientoCasos1y3(String, String, Boolean, ModoDesembolsoEnum, EstadoAporteSubsidioEnum ,Long, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        EliminarMarcaAprobacionSegNivel validacion = new EliminarMarcaAprobacionSegNivel();        
        validacion.execute();        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);        
    }
    
    //Metodo para dispersa liquidacion en Anibol
        /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#terminarTareaLiquidacionDispersada(java.lang.String,
     *      java.lang.String, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void terminarLiquidacionDispersadaAnibol(String numeroSolicitud, String idTarea, UserDTO userDTO) {
        String firmaMetodo = "SubsidioMonetarioCompositeBusiness.terminarLiquidacionDispersadaAnibol(String, String, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);
      try{
             //Se comenta este bloque para las pruebas en el ambiente no cierre la tarea y no repetir la liquidacion
       /*     try{
                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), new HashMap<>());
                terminarTarea.execute();
            }
            catch (Exception e) {
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }
*/
        dispersarPagosEstadoEnviado(numeroSolicitud);
        
        List<CuentaAdministradorSubsidioDTO> cuentasMedioTarjeta = consultarCuentasAdministradorMedioTarjeta(numeroSolicitud);

        List<Long> pagosTarjetaExitosos = new ArrayList<>();

        String idProceso = dispersarPagosMedioTarjetaAnibol(cuentasMedioTarjeta);

        if(cuentasMedioTarjeta != null && !cuentasMedioTarjeta.isEmpty()){
            crearRegistroSolicitudAnibol(idProceso, idSolicitudLiquidacion, numeroSolicitud);
        }

     //   dispersarPagosEstadoAplicado(numeroSolicitud, pagosTarjetaExitosos);

 /*       actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.DISPERSADA);
        actualizarFechaDispersion(idSolicitudLiquidacion);
        actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
*/
        // Envío de comunicados
        // 063 Notificación de dispersión de pagos al empleador 
        // 064 Notificación de dispersión de pagos al trabajador 
        // 065 Notificación de dispersión de pagos al administrador del subsidio

        //enviarComunicadosLiquidacion(numeroSolicitud);
        }catch (Exception e) {
            InsercionMonitoreoLogs insercionMonitoreoLogs = new InsercionMonitoreoLogs ( 
                                "idSolicitudLiquidacion : "+ idSolicitudLiquidacion
            					,"Se rompe en terminarTareaLiquidacionDispersada subsidioMonetarioComposite " + e);
			insercionMonitoreoLogs.execute();
                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE,e);
            }
       
        logger.info(ConstantesComunes.FIN_LOGGER + "*****"+ firmaMetodo);
    }
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void generarArchivosSalidaEntidadDescuentoCompositeExp(String numeroRadicacion) {
            generarArchivosSalidaEntidadDescuentoComposite(numeroRadicacion);
    }
}
