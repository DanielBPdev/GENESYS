package com.asopagos.aportes.masivos.service.ejb;


import java.util.concurrent.ExecutionException;
import java.io.IOException;
import com.asopagos.tareashumanas.clients.SuspenderTarea;
import java.math.BigDecimal;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloCore;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloPila;
import javax.ejb.Stateless;
import com.asopagos.aportes.masivos.service.ArchivosAportesMasivosService;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.rest.exception.TechnicalException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.asopagos.aportes.masivos.clients.ValidarArchivoAportes;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.aportes.clients.ConsultarAporteDetallado;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.GuardarMedioDePago;
import java.util.LinkedList;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import java.util.concurrent.Callable;
import com.asopagos.dto.aportes.CotizanteDTO;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import com.asopagos.cache.CacheManager;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.DevolucionAporteDetalleModeloDTO;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.FileLoaderInterface;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import co.com.heinsohn.lion.fileCommon.enums.FileFormat;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.aportes.masivos.dto.*;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.aportes.clients.CrearAporteDetallado;
import com.asopagos.aportes.clients.BorrarTemporalesPILA;
import com.asopagos.aportes.clients.ConsultarAporteGeneral;
import com.asopagos.aportes.clients.ActualizacionAportesRecalculados;
import com.asopagos.aportes.clients.CrearActualizarAporteGeneral;
import com.asopagos.aportes.clients.ConsultarAporteTemporal;
import com.asopagos.aportes.clients.ConsultarSolicitudAporte;
import com.asopagos.aportes.clients.ConsultarRegistroGeneral;
import com.asopagos.aportes.clients.ActualizarSolicitudAporte;
import com.asopagos.aportes.clients.CrearTransaccion;
import com.asopagos.aportes.clients.ConsultarRegistroGeneralId;
import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.aportes.clients.CrearRegistroGeneral;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.aportes.clients.CrearActualizarDevolucionAporte;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.enumeraciones.aportes.PilaEstadoTransitorioEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.aportes.clients.ActualizarMovimientoAporte;
import com.asopagos.solicitudes.clients.ConsultarSolicitudGlobal;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.Asynchronous;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.text.SimpleDateFormat;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;
import org.apache.commons.lang3.StringUtils;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.consola.estado.cargue.procesos.clients.RegistrarCargueConsolaEstado;
import com.asopagos.aportes.clients.ConsultarRecaudo;
import com.asopagos.aportes.clients.ConsultarRegistroDetallado;
import com.asopagos.aportes.clients.EjecutarArmadoStaging;
import com.asopagos.aportes.clients.EjecutarBorradoStaging;
import com.asopagos.aportes.clients.SimularFasePila2;
import com.asopagos.aportes.clients.ConsultarRegistroDetalladoPorId;
import com.asopagos.aportes.clients.ConsultarSolicitudDevolucionAporte;
import com.asopagos.aportes.clients.CrearActualizarDevolucionAporteDetalle;
import com.asopagos.aportes.clients.CrearRegistroDetallado;
import com.asopagos.aportes.clients.RegistrarRelacionarAportesNovedades;
import com.asopagos.aportes.clients.RegistrarRelacionarAportes;
import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.aportes.clients.CrearActualizarSolicitudDevolucionAporte;
import com.asopagos.aportes.clients.CrearActualizarSolicitudGlobal;
import com.asopagos.aportes.clients.RegistrarRelacionarNovedades;
import com.asopagos.aportes.clients.CrearActualizarSolicitudCorreccionAporte;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import com.asopagos.aportes.composite.clients.ActualizarSolicitudTrazabilidad;
import com.asopagos.aportes.composite.clients.ProcesarNovedadIngresoAporte;
import com.asopagos.aportes.composite.clients.ProcesarActivacionEmpleador;
import com.asopagos.aportes.composite.clients.RegistrarRelacionarListadoAportes;
import com.asopagos.aportes.composite.dto.RadicacionAporteManualDTO;
import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadAportes;
import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliado;
import com.asopagos.aportes.clients.ConsultarNovedad;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosAnalisisDevolucionDTO;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
import com.asopagos.aportes.masivos.util.ArchivosAportesMasivosUtils;
import com.asopagos.aportes.masivos.util.FuncionesUtilitarias;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloStaging;
import com.asopagos.consola.estado.cargue.procesos.clients.ActualizarCargueConsolaEstado;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.EmpresaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.aportes.EvaluacionDTO;
import com.asopagos.dto.aportes.HistoricoDTO;
import com.asopagos.dto.aportes.NovedadAportesDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.NovedadPilaDTO;

import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.aportes.EstadoGestionAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.novedades.EstadoCargueArchivoActualizacionEnum;
import com.asopagos.enumeraciones.novedades.MarcaNovedadEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.MarcaRegistroAporteArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.novedades.clients.CrearCargueArchivoActualizacion;
import com.asopagos.rest.exception.AsopagosException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.concurrent.Future;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.stream.Collectors;

import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.pila.masivos.*;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.aportes.composite.clients.SimularAporteCorreccion;
import com.asopagos.aportes.composite.clients.SimularCorreccionTemporal;
import com.asopagos.aportes.masivos.clients.PersistirDetallesArchivoAporte;
import com.asopagos.consola.estado.cargue.procesos.clients.ConsultarLogErrorArchivo;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.tareashumanas.clients.RetomarTarea;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.aportes.composite.dto.InformacionSolicitudDTO;
import com.asopagos.aportes.composite.clients.FinalizarCorreccionAsyncMasiva;
import com.asopagos.aportes.composite.clients.ConsultarCorreccionTemporal;
import com.asopagos.aportes.composite.clients.GuardarCorreccionTemporal;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import com.asopagos.aportes.masivos.service.constants.ConstantesReportes;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCartera;
import com.asopagos.archivos.util.ComprimidoUtil;
import com.asopagos.archivos.util.ExcelUtil;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.util.Collections;

import co.com.heinsohn.lion.common.enums.Protocolo;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;

import java.time.LocalDate;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.aportes.masivos.service.constants.CamposArchivoConstants;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.enumeraciones.aportes.EstadoCargaArchivoCrucesAportesEnum;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.filegenerator.dto.FileGeneratorOutDTO;
import co.com.heinsohn.lion.filegenerator.ejb.FileGenerator;
import co.com.heinsohn.lion.filegenerator.enums.FileGeneratedState;
import co.com.heinsohn.lion.fileprocessing.fileloader.enums.FileLoadedState;
import java.util.Locale;
import com.asopagos.consola.estado.cargue.procesos.clients.ConsultarUltimoCargueConsolaEstado;
import com.asopagos.aportes.masivos.service.load.source.ArchivoCruceAportesFilterDTO;
/**
 * 
 */
@Stateless
public class ArchivosAportesMasivosBusiness implements ArchivosAportesMasivosService {
  	
    @Resource//(lookup="java:jboss/ee/concurrency/executor/archivoAportesmasivos")
    private ManagedExecutorService managedExecutorService;
	
    @Inject
	private IConsultasModeloCore consultasCore;

    @Inject
	private IConsultasModeloPila consultasPila;

	@Inject
	private FileLoaderInterface fileLoader;

       /**Interfaz de generación de archivos mediante Lion Framework*/
    @Inject
    private FileGenerator fileGenerator;

    /**
	 * Inject del EJB para consultas en modelo Staging entityManagerStPila
	 */
	@Inject
	private IConsultasModeloStaging consultasStaging;

	private Long fileDefinitionIdArchivoAportesMasivosRecaudo = new Long(CacheManager
		.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_RECAUDO.toString())
		.toString());
        
        



	
	/**
	 * Logger
	 */

    private static final ILogger logger = LogManager.getLogger(ArchivosAportesMasivosBusiness.class);


    @Override
    public List<SolicitanteDTO> consultarSolicitanteCorreccionMasivo(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion,String periodoAporte){

        try {
			// validacion nombre
			List<SolicitanteDTO> solicitantes = consultasCore
					.consultarPersonaSolicitanteAporteGeneral(tipoIdentificacion, numeroIdentificacion,periodoAporte);
			List<SolicitanteDTO> solicitantesEmpresa = consultasCore
					.consultarEmpresaSolicitanteAporteGeneral(tipoIdentificacion, numeroIdentificacion,periodoAporte);
			solicitantes.addAll(solicitantesEmpresa);
			return solicitantes;
		} catch (NoResultException e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccionMasivo(TipoIdentificacionEnum, String):No se encuentran registros con los parametros ingresados");
			return null;
		} catch (Exception e) {
			logger.debug(
					"Finaliza método consultarSolicitanteCorreccionMasivo(TipoIdentificacionEnum, String):Ocurrio un error técnico inesperado");
			logger.error(
					"Finaliza método consultarSolicitanteCorreccionMasivo(TipoIdentificacionEnum, String):Ocurrio un error técnico inesperado",
					e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
    }

    @Override
    public List<MasivoSimulado> consultarRecaudoSimulado(Long idSolicitud){
        return consultasPila.consultarRecaudoSimulado(idSolicitud);
    }

   @Override
    // @Asynchronous
    public void aprobarRecaudoSimulado(Long idSolicitud, UserDTO userDTO){
        //Invocar simulacion de resultados
        List<Long> ids = new ArrayList();
        Solicitud solicitud = consultasCore.consultarSolicitudGlobal(idSolicitud);
        MasivoArchivo archivo = consultasPila.consultarArchivoMasivoPorRadicado(solicitud.getNumeroRadicacion());
        List<Object> stringIds = consultasPila.finalizarAporteMasivo(archivo.getNombreArchivo());
        ObtenerTareaActiva servicio = new ObtenerTareaActiva(Long.valueOf(solicitud.getIdInstanciaProceso()));
        //servicio.setToken(userDTO.getToken());
        servicio.execute();
        Map<String, Object> infoTarea = servicio.getResult();

        if (infoTarea == null || !infoTarea.containsKey("idTarea")) {
            throw new RuntimeException("Tarea no encontrada");
        }
        Long idTarea =  Long.valueOf(infoTarea.get("idTarea").toString());

        // SuspenderTarea suspenderTarea = new SuspenderTarea(idTarea, new HashMap<String, Object>());
        // suspenderTarea.execute();
        for (Object stringId : stringIds) {
            logger.info(stringId);
            ids.add(Long.valueOf(stringId.toString()));
        }
        
        finalizarAporteMasivoAsync(ids,idSolicitud,userDTO);
        
        

        //Finalizar revision contable
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
        terminarTarea.execute();


        // Finalizar envio de comunicados
        ObtenerTareaActiva servicio2 = new ObtenerTareaActiva(Long.valueOf(solicitud.getIdInstanciaProceso()));
        servicio2.execute();
        Map<String, Object> infoTarea2 = servicio2.getResult();

        if (infoTarea2 == null || !infoTarea2.containsKey("idTarea")) {
            throw new RuntimeException("Tarea no encontrada");
        }
        Long idTarea2 =  Long.valueOf(infoTarea2.get("idTarea").toString());

        TerminarTarea terminarTarea2 = new TerminarTarea(idTarea2, new HashMap<>());
        terminarTarea2.execute();
    }

    @Asynchronous
    public void finalizarAporteMasivoAsync(List<Long> idSolicitudes, Long idOrginal,UserDTO userDTO ){
        for(Long idSolicitud :idSolicitudes){
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        try{
        registroGeneralDTO = consultarRegistroGeneral(idSolicitud);
        //registrarRelacionarAportesNovedades(registroGeneralDTO.getTransaccion());
        //registroGeneralDTO = consultarRegistroGeneral(idSolicitud);
        MasivoGeneral mag = consultasPila.consultarMasivoGeneral(idOrginal, registroGeneralDTO.getNumeroIdentificacionAportante());
        MasivoArchivo maa = consultasPila.consultarArchivoMasivo(mag.getIdMasivoArchivo());
        registrarAporteConDetalle(null,
                                null, idSolicitud,
                                mag.getTipoAportante(), Boolean.FALSE,
                                registroGeneralDTO.getId(),
                                null,
                                maa.getIdCuentaBancaria() != null ? maa.getIdCuentaBancaria().intValue() : null);

        SolicitudAporteModeloDTO solicitudAporteDTO = consultarSolicitudAporte(idSolicitud);
                        solicitudAporteDTO.setIdRegistroGeneral(registroGeneralDTO.getId());
        actualizarSolicitudAporte(solicitudAporteDTO);
        cambiarEstadoAporte(idSolicitud, userDTO);
        } catch (Exception e) {
            logger.info("Ocurrio un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        } finally {
        if (registroGeneralDTO != null) {
                borrarTemporalesPILA(registroGeneralDTO.getId());

                if (registroGeneralDTO.getTransaccion() != null) {
                    ejecutarBorradoStaging(registroGeneralDTO.getTransaccion());
                }
            }


            //try { // Actualiza la cartera del aportante -> HU-169
            //MasivoGeneral mag = consultasPila.consultarMasivoGeneral(idOrginal, registroGeneralDTO.getNumeroIdentificacionAportante());
            //  
            //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            //        String periodoPago = dateFormat.format(new Date(registroGeneralDTO.getPeriodoAporte()));
            //        actualizarDeudaPresuntaCartera(registroGeneralDTO.getTipoIdentificacionAportante(),
            //                registroGeneralDTO.getNumeroIdentificacionAportante(), periodoPago,
            //                mag.getTipoAportante());
            //    
            //} catch (Exception e) {
            //    logger.info("Ocurrió un error actualizando la deuda presunta", e);
            //}
        }
        }
    }

    private void actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO) {
        logger.info("Inicio de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
        ActualizarSolicitudAporte actualizarSolicitudAporteService = new ActualizarSolicitudAporte(
                solicitudAporteModeloDTO);
        actualizarSolicitudAporteService.execute();
        logger.info("Fin de método actualizarSolicitudAporte(SolicitudAporteModeloDTO solicitudAporteModeloDTO)");
    }
    /**
     * Método que invoca el servicio que consulta una solicitud de aporte.
     * 
     * @param idSolicitudGlobal
     *                          id de la solicitud global.
     * @return solicitud de aporte consultada.
     */
    private SolicitudAporteModeloDTO consultarSolicitudAporte(Long idSolicitudGlobal) {
        logger.info("Inicio de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudAporte consultarSolicitudAporte = new ConsultarSolicitudAporte(idSolicitudGlobal);
        consultarSolicitudAporte.execute();
        SolicitudAporteModeloDTO solicitudAporteModeloDTO = consultarSolicitudAporte.getResult();
        logger.info("Fin de método consultarSolicitudAporte(Long idSolicitudGlobal)");
        return solicitudAporteModeloDTO;
    }

    /**
     * Método para cambiar el estado del aporte y terminar la tarea.
     */
    private void cambiarEstadoAporte(Long idSolicitud,  UserDTO userDTO) {
        
        actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL,
                EstadoSolicitudAporteEnum.NOTIFICADA, null, userDTO);
        actualizarSolicitudTrazabilidad(idSolicitud, ProcesoEnum.PAGO_APORTES_MANUAL, EstadoSolicitudAporteEnum.CERRADA,
                null, userDTO);

        
    }

    /**
     * Método para registrar un aporte con detalle.
     */
    private void registrarAporteConDetalle(OrigenAporteEnum origenAporte, Integer cajaAporte, Long idSolicitud,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Boolean pagadorTercero, Long idRegistroGeneral,
            Long fechaRecaudo, Integer cuentaBancariaRecaudo) {
        logger.info("Inicio de método registrarAporteConDetalle");
        /*
         * se consultan las tablas temporales y se guarda la información del aporte
         * general y detallado
         */
        List<AporteDTO> aportes = consultarInformacionTemporal(idRegistroGeneral);
        List<TemNovedad> novedades = consultarInformacionTemporalNovedad(idRegistroGeneral);

        logger.info("**__**Manueles idRegistroGeneral a procesar:" + idRegistroGeneral);
        logger.info("**__**Manueles tem novedades a procesar:" + novedades.size());

        int ciclosAportes = 1;
        String cedulaNovedadTemp = "";
        for (AporteDTO aporteDTO : aportes) {
            List<NovedadPilaDTO> novedadesPila = new ArrayList<>();
            // Corregir consulta con el orden y verificarlo en este servicio
            Long idRegistroTemporal = 0L;
            String cedulaAporte = "";

            cedulaAporte = aporteDTO.getPersonaCotizante().getNumeroIdentificacion();
            logger.info("**__**cedulaAporte1111111111:" + cedulaAporte + "L ciclosAportes:" + ciclosAportes);
            if (!cedulaNovedadTemp.equals(cedulaAporte)) {
                cedulaNovedadTemp = cedulaAporte;
                String cedulaNovedad = "";
                for (TemNovedad temNovedad : novedades) {
                    logger.info("**__**¨[[[]]]. : getAccionNovedad: " + temNovedad.getAccionNovedad());
                    logger.info("**__**¨  [[[]]]  : getRegistroDetallado" + temNovedad.getRegistroDetallado());
                    logger.info("**__**¨ [[[]]] getTipoTransaccion: " + temNovedad.getTipoTransaccion());
                    logger.info("**__**¨ [[[]]] getFechaInicioNovedad: " + temNovedad.getFechaInicioNovedad());
                    logger.info("**__**temNovjnnnnvv:" + temNovedad.getNumeroIdCotizante() + "L");
                    logger.info(
                            "**__**temNovedajnnn:" + aporteDTO.getPersonaCotizante().getNumeroIdentificacion() + "L");

                    cedulaNovedad = temNovedad.getNumeroIdCotizante();
                    logger.info("**__**cedulaAporte:" + cedulaAporte + "L");
                    logger.info("**__**cedulaNovedad:" + cedulaNovedad + "L");
                    if (cedulaAporte.equals(cedulaNovedad)) {
                        logger.info(
                                "**__**¨-----------------------------------------------------------------------------------------------");
                        logger.info("**__**temNovedad.getRegistroDetallado()" + temNovedad.getRegistroDetallado());
                        logger.info("**__**temNovedad.getTipoTransaccion()" + temNovedad.getTipoTransaccion());
                        logger.info("**__**temNovedad.getFechaInicioNovedad()" + temNovedad.getFechaInicioNovedad());
                        logger.info("**__**temNovedad.getNumeroIdCotizante()" + temNovedad.getNumeroIdCotizante());
                        logger.info("**__**temNovedad.getNumeroIdentificacion()"
                                + aporteDTO.getPersonaCotizante().getNumeroIdentificacion());
                        logger.info(
                                "**__**¨******************************************************************************");
                        NovedadPilaDTO novedadPilaDTO = new NovedadPilaDTO(temNovedad.getMarcaNovedadSimulado(),
                                temNovedad.getMarcaNovedadManual(), temNovedad.getTipoTransaccion(),
                                temNovedad.getEsIngreso(),
                                temNovedad.getEsRetiro(),
                                temNovedad.getTipoIdCotizante() != null
                                        ? TipoIdentificacionEnum.valueOf(temNovedad.getTipoIdCotizante())
                                        : null,
                                temNovedad.getNumeroIdCotizante(), temNovedad.getFechaInicioNovedad(),
                                temNovedad.getFechaFinNovedad(),
                                temNovedad.getAccionNovedad(), temNovedad.getMensajeNovedad(),
                                temNovedad.getTipoCotizante() != null
                                        ? TipoAfiliadoEnum.valueOf(temNovedad.getTipoCotizante())
                                        : aporteDTO.getAporteDetallado().getTipoCotizante(),
                                temNovedad.getValor());
                        novedadPilaDTO.setIdRegistroDetallado(temNovedad.getRegistroDetallado());
                        novedadPilaDTO.setIdRegistroGeneral(idRegistroGeneral);
                        novedadesPila.add(novedadPilaDTO);
                        idRegistroTemporal = temNovedad.getRegistroDetallado();

                    } else {
                        logger.info("**__**sddddddd");
                        // break;
                    }
                }
            }
            cedulaAporte = "";
            aporteDTO.setNovedades(novedadesPila);
            aporteDTO.getAporteGeneral().setTipoSolicitante(tipoSolicitante);
            aporteDTO.getAporteGeneral().setPagadorPorTerceros(pagadorTercero);
            aporteDTO.getAporteGeneral().setOrigenAporte(origenAporte);
            aporteDTO.getAporteGeneral().setIdCajaCompensacion(cajaAporte);
            aporteDTO.setEsManual(Boolean.TRUE);
            if (fechaRecaudo != null) {
                aporteDTO.getAporteGeneral().setFechaRecaudo(fechaRecaudo);
            }
            if (cuentaBancariaRecaudo != null) {
                aporteDTO.getAporteGeneral().setCuentaBancariaRecaudo(cuentaBancariaRecaudo);
            }
            ciclosAportes++;
        }

        /* Se actualizan los aportes por HU-480 que apliquen */
        List<Long> ids = new ArrayList<>();
        ids.add(idRegistroGeneral);
        ActualizacionAportesRecalculados actualizacionAportes = new ActualizacionAportesRecalculados(Boolean.TRUE, ids);
        actualizacionAportes.execute();
        // entra aqui aportes manuales ruta donde hace persist(aportedetallado)
        /* se registra la información del aporte */
        registrarRelacionarListadoAportes(aportes);
        logger.info("Fin de método registrarAporteConDetalle");
    }


    public void registrarRelacionarListadoAportes(List<AporteDTO> aportes){
        RegistrarRelacionarListadoAportes registrarRelacionarListadoAportes = new RegistrarRelacionarListadoAportes(
                aportes);
        registrarRelacionarListadoAportes.execute();
    }

    /**
     * Método encargado de invocar el servicio que consulta la información temporal.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     * @return lista de los aportes detallados
     */
    private List<AporteDTO> consultarInformacionTemporal(Long idTransaccion) {
        logger.info("Inicio de método consultarInformacionTemporal(Long idTransaccion)");
        ConsultarAporteTemporal consultarAporteService = new ConsultarAporteTemporal(idTransaccion);
        consultarAporteService.execute();
        List<AporteDTO> aportesDTO = consultarAporteService.getResult();
        logger.info("Fin de método consultarInformacionTemporal(Long idTransaccion)");
        return aportesDTO;
    }


    private void registrarRelacionarAportesNovedades(Long idTransaccion) {
        logger.info("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
        RegistrarRelacionarAportesNovedades registrarRelacionarService = new RegistrarRelacionarAportesNovedades(
                idTransaccion,
                Boolean.TRUE, Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.info("Fin de método registrarRelacionarAportesNovedades(Long idTransaccion)");
    }
    public void borrarTemporalesPILA(Long idRegistroGeneral) {
        logger.info("Inicio de método borrarTemporalesPILA(Long idRegistroGeneral)");
        BorrarTemporalesPILA borrarTemporal = new BorrarTemporalesPILA(idRegistroGeneral);
        borrarTemporal.execute();
        logger.info("Fin de método borrarTemporalesPILA(Long idRegistroGeneral)");
    }
    private void actualizarDeudaPresuntaCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String periodoEvaluacion, TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        String firmaMetodo = "AportesManuales.CompositeBusiness.actualizarDeudaPresuntaCartera(TipoIdentificacionEnum, String, String, "
                + "TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo + " :: tipoIdentificacion = "
                + tipoIdentificacion.getValorEnPILA()
                + ", numeroIdentificacion = " + numeroIdentificacion + ", periodoEvaluacion = " + periodoEvaluacion
                + ", "
                + "tipoAportante = " + tipoAportante.name());

        ActualizarDeudaPresuntaCartera service = new ActualizarDeudaPresuntaCartera(tipoAportante, numeroIdentificacion,
                periodoEvaluacion, tipoIdentificacion);
        service.execute();

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @Override
    public DatosRadicacionMasivoAporteDTO consultarCabeceraResultadoRecaudoMasivo(Long idSolicitud) {
        DatosRadicacionMasivoAporteDTO res = new DatosRadicacionMasivoAporteDTO();
        Solicitud solicitud = consultasCore.consultarSolicitudGlobal(idSolicitud);
        MasivoArchivo archivo = consultasPila.consultarArchivoMasivoPorRadicado(solicitud.getNumeroRadicacion());



        BigDecimal montoTotalGestionable = new BigDecimal("0");
        BigDecimal montoTotalDiligenciado = new BigDecimal("0");
        try {
            Object[] montos = consultasPila.consultarMontosValoresRecaudoAportesMasivos(
                archivo.getNombreArchivo(),
                solicitud.getNumeroRadicacion());
            

                montoTotalGestionable = new BigDecimal(montos[0].toString());
                montoTotalDiligenciado = new BigDecimal(montos[1].toString());
        } catch (Exception e) {
            logger.error("Error consulta montos", e);
        }

        res.setUsuarioRadicacion(archivo.getUsuario());
        res.setFechaRadicado(solicitud.getFechaRadicacion());
        res.setNumeroRadicacion(solicitud.getNumeroRadicacion());
        res.setMontoTotalDiligenciado(montoTotalDiligenciado);
        res.setMontoTotalGestionable(montoTotalGestionable);

        return res;
    }

    @Override
    public MasivoArchivo cargarArchivoAportesMasivos(MasivoArchivo datosArchivo, UserDTO userDTO){
        return consultasPila.guardarArchivoMasivo(datosArchivo, userDTO);
    }

    @Override
    public List<ArchivoAporteMasivoDTO> consultarArchivosAportes(){
        List<MasivoArchivo> masivoArchivos = consultasPila.consultarArchivoAporte();
        return consultasCore.popularDatosMasivoArchivoAportes(masivoArchivos);

    }

    @Override
    public List<AnalisisDevolucionDTO> consultarAportesADevolver(String numeroRadicado){
        List<AnalisisDevolucionDTO> aportes = consultasPila.consultarAportesADevolver(numeroRadicado);
        if(aportes.isEmpty() || aportes == null){
            consultasPila.consultarDevolucionMasivoGeneral(numeroRadicado);
            consultasPila.consultarDevolucionMasivoDetallado(numeroRadicado);
            aportes = consultasPila.consultarAportesADevolver(numeroRadicado);
        }
        return aportes;
    }
    @Override
    public List<AnalisisDevolucionDTO> consultarAportesSimuladoDevolucion(String numeroRadicado){
        logger.info("consultarAportesSimuladoDevolucion: " + numeroRadicado);
        List<AnalisisDevolucionDTO> aportes = consultasPila.consultarAportesSimuladosDevolucion(numeroRadicado);

        return aportes;
    }

    
@Override
	public  List<ResultadoArchivoAporteDTO> validarEstructuraAportesMasivos(List<ArchivoAportesDTO> archivosAportes, UserDTO userDTO) throws Exception {
		// Se validan de forma paralela cada uno de los archivosDeAportesRecibidos
		// Se genera un numero de radicacion
		// Inicia el proceso de persistencia
		// Integer cantidadArchivos = Integer.valueOf(archivosAportes.size());
        List<ResultadoArchivoAporteDTO> listResultadoProcesamiento = new ArrayList<>();

		// List<Callable<ResultadoArchivoAporteDTO>> tareasParalelas = new LinkedList();
		for (ArchivoAportesDTO archivo : archivosAportes) {
			// Callable<ResultadoArchivoAporteDTO> parallelTask = () -> {

				ValidarArchivoAportes validarSrv = new ValidarArchivoAportes(archivo);
				validarSrv.execute();
				listResultadoProcesamiento.add(validarSrv.getResult());
			// };
			// tareasParalelas.add(parallelTask);
		}




        // try {
        //     List<Future<ResultadoArchivoAporteDTO>> listInfoArchivoFuture = managedExecutorService.invokeAll(tareasParalelas);
        //     for (Future<ResultadoArchivoAporteDTO> future : listInfoArchivoFuture) {
        //         listResultadoProcesamiento.add(future.get());
        //     }
        // } catch (InterruptedException | ExecutionException e) {
        //     logger.error("Error tareas asincrona afiliacionesPersonasWeb", e);
        //     throw new TechnicalException(e);
        // }

		return listResultadoProcesamiento;
	}

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistirDetallesArchivoAporte(Long idArchivoMasivo, List<ResultadoValidacionAporteDTO> aportes) {
        for (ResultadoValidacionAporteDTO aporte: aportes) {
            consultasPila.persistirDetallesArchivoAporte(idArchivoMasivo, aporte);
        }
        

    }


    @Override
    public ResultadoArchivoAporteDTO validarArchivoAportes(ArchivoAportesDTO archivoAportes, UserDTO userDTO) {
		// Se obtiene informacion sobre el archivo
        ArchivosAportesMasivosUtils archivosAportesMasivosUtils = new ArchivosAportesMasivosUtils(consultasCore);
		InformacionArchivoDTO infoArchivo = obtenerArchivo(archivoAportes.getIdArchivoMasivo());
        ResultadoArchivoAporteDTO resultadoValidacion = new ResultadoArchivoAporteDTO();
        resultadoValidacion.setEcmIdentificador(archivoAportes.getCodigoECM());
        resultadoValidacion.setIdArchivoMasivo(archivoAportes.getIdArchivoMasivo());
        

		List<ResultadoAporteMasivoDTO> lista = new ArrayList<ResultadoAporteMasivoDTO>();
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		List<ResultadoHallazgosValidacionArchivoDTO> resHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lista);
		context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, 0L);
		context.put(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE, archivoAportes.getCodigoECM());
		//context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, 0);
		context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, 0);
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
        FileFormat fileFormat = darFileFormat(infoArchivo);
        try {
            outDTO = fileLoader.validateAndLoad(
                context,
                fileFormat,
                infoArchivo.getDataFile(),
                fileDefinitionIdArchivoAportesMasivosRecaudo);
            
           

            resHallazgos.addAll( (List<ResultadoHallazgosValidacionArchivoDTO>)
            outDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
            

            resHallazgos.addAll(archivosAportesMasivosUtils.consultarListaHallazgos(fileDefinitionIdArchivoAportesMasivosRecaudo, outDTO));


           
        } catch (Exception e) {
            logger.error("Error en la lectura :C" + e.getMessage());
            e.printStackTrace();
        }
        // Si encuentra hallazgos no realizar persistencia en en esquema masivos
        resultadoValidacion.setResultadoHallazgosAportesMasivos(listaHallazgos);
        // Persisir estado en consola estado cargue
         String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        // Se obtiene la informacion del archivo cargado
        // Se registra el estado inicial del cargue
        CargueArchivoActualizacionDTO cargue = new CargueArchivoActualizacionDTO();
        cargue.setNombreArchivo(infoArchivo.getFileName());
        cargue.setCodigoIdentificacionECM(archivoAportes.getCodigoECM());
        cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
        Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);
        //TODO: actualizar masivoarchivo con id cargue
        MasivoArchivo masivoArchivo = consultasPila.consultarArchivoMasivo(archivoAportes.getIdArchivoMasivo());
        masivoArchivo.setIdCargue(idCargue);
        consultasPila.actualizarArchivoMasivo(archivoAportes.getIdArchivoMasivo(), masivoArchivo);
        cargue.setIdCargueArchivoActualizacion(idCargue);

        // Se registra el estado en la consola
        List<ResultadoAporteMasivoDTO> resAportes = 
            (List<ResultadoAporteMasivoDTO>) outDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);

        Long cantidadRegistos = (long) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
        // if (!resAportes.isEmpty()) {
        //     cantidadRegistos += (long) resAportes.size();
        // }

        ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
        consolaEstadoCargue.setCargue_id(idCargue);
        consolaEstadoCargue.setCcf(codigoCaja);
        consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
        consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
        consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
        consolaEstadoCargue.setIdentificacionECM(archivoAportes.getCodigoECM());
        consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
        consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_APORTES_MASIVOS);
        consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
        consolaEstadoCargue.setLstErroresArhivo(resHallazgos);
        consolaEstadoCargue.setNumRegistroObjetivo(cantidadRegistos);
        consolaEstadoCargue.setNumRegistroProcesado(cantidadRegistos);
        consolaEstadoCargue.setNumRegistroConErrores((long) resHallazgos.size());
        registrarConsolaEstado(consolaEstadoCargue);
        
        //RegistrarCargueConsolaEstado 
        
        
        
        
        if (resHallazgos.isEmpty()) {

            List<ResultadoValidacionAporteDTO> aportes = archivosAportesMasivosUtils.agruparAportes(resAportes, idCargue);
            // Se invoca el servicio asincrono de persistir masivo general y masivo detallado
            consultasPila.actualizarArchivoMasivo(archivoAportes.getIdArchivoMasivo(), "VALIDADO");
            PersistirDetallesArchivoAporte persistirDetallesArchivoAporte = new PersistirDetallesArchivoAporte(archivoAportes.getIdArchivoMasivo(), aportes);
            persistirDetallesArchivoAporte.execute();
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            logger.info("id cargue "+idCargue);
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_APORTES_MASIVOS);
            conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
            actualizarConsolaEstado(idCargue, conCargueMasivo);



            // Se persiste la informacion de Masivo General y Detallado
            
        } else {
            // Si hay inconsistencias se actualiza el archivo a cargado con errores
            consultasPila.actualizarArchivoMasivo(archivoAportes.getIdArchivoMasivo(), "VALIDADO CON ERRORES");
            ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
            logger.info("id cargue "+idCargue);
            conCargueMasivo.setCargue_id(idCargue);
            conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
            conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_APORTES_MASIVOS);
            actualizarConsolaEstado(idCargue, conCargueMasivo);

        }
        resultadoValidacion.setResultadoHallazgosAportesMasivos(resHallazgos);
        // Persist en tablas de Masivo Archivo, y datos temporales
        

        return resultadoValidacion;
    }

    @Override
    public Long crearSolicitudCorreccion(PersonaDTO persona, UserDTO userDto) {

        SolicitudModeloDTO solicitudModeloDTO = new SolicitudModeloDTO();
        solicitudModeloDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudModeloDTO.setFechaRadicacion(new Date().getTime());
        solicitudModeloDTO.setFechaCreacion(new Date().getTime());
        solicitudModeloDTO.setTipoTransaccion(TipoTransaccionEnum.CORRECCION_APORTES);
        solicitudModeloDTO.setDestinatario(userDto.getNombreUsuario());
        solicitudModeloDTO.setUsuarioRadicacion(userDto.getNombreUsuario());
        solicitudModeloDTO.setSedeDestinatario("1");

        Long idSolicitudGlobal = crearActualizarSolicitud(solicitudModeloDTO);

        PersonaDTO resPersona;
        resPersona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
                persona.getTipoIdentificacion(),
                persona.getNumeroIdentificacion());

        // Generar solicitud Correccion y correccion
        SolicitudCorreccionAporteModeloDTO correccionDTO = new SolicitudCorreccionAporteModeloDTO();
        Solicitud solicitud = consultasCore.consultarSolicitudGlobal(idSolicitudGlobal);
        String numeroRadicado = generarNumeroRadicado(idSolicitudGlobal, userDto.getSedeCajaCompensacion());
        solicitud.setNumeroRadicacion(numeroRadicado);
        correccionDTO.convertToDTO(solicitud);
        correccionDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        correccionDTO.setFechaRadicacion(new Date().getTime());
        correccionDTO.setFechaCreacion(new Date().getTime());
        correccionDTO.setTipoTransaccion(TipoTransaccionEnum.CORRECCION_APORTES);
        correccionDTO.setDestinatario(userDto.getNombreUsuario());
        correccionDTO.setUsuarioRadicacion(userDto.getNombreUsuario());
        correccionDTO.setSedeDestinatario("1");
        correccionDTO.setEstadoSolicitud(EstadoSolicitudAporteEnum.RADICADA);
        // TODO : VALIDAR EL TIPO DE SOLICITANTE
        correccionDTO.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
        correccionDTO.setObservacionSupervisor("Ok");
        correccionDTO.setResultadoSupervisor(ResultadoProcesoEnum.APROBADA);
        correccionDTO.setIdPersona(resPersona.getIdPersona());

        CrearActualizarSolicitudCorreccionAporte crearActualizarSolicitudCorreccionAporte =
            new CrearActualizarSolicitudCorreccionAporte(correccionDTO);

        crearActualizarSolicitudCorreccionAporte.execute();

        return idSolicitudGlobal;
    }

    
    @Override
    public Map<String, String> procesarArchivoAportes(Long idArchivoMasivo, @Context UserDTO userDTO) {


        consultasPila.actualizarArchivoMasivo(idArchivoMasivo, "SIMULADO");

        SolicitudModeloDTO solicitudModeloDTO = new SolicitudModeloDTO();
        solicitudModeloDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudModeloDTO.setFechaRadicacion(new Date().getTime());
        solicitudModeloDTO.setFechaCreacion(new Date().getTime());
        solicitudModeloDTO.setTipoTransaccion(TipoTransaccionEnum.APORTES_MANUALES_MASIVA);
        solicitudModeloDTO.setDestinatario(userDTO.getNombreUsuario());
        solicitudModeloDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudModeloDTO.setSedeDestinatario("1");

        logger.info("procesarArchivoAportes: " );
        return radicarSolicitudAportesMasivos(solicitudModeloDTO, userDTO);
        
    }

    @Override
    public List<ResultadoHallazgosValidacionArchivoDTO> consultarHallazgosAportes(Long idArchivoMasivo){
        MasivoArchivo masivoArchivo = consultasPila.consultarArchivoMasivo(idArchivoMasivo);
        if (masivoArchivo == null) {
            throw new ParametroInvalidoExcepcion("No se encuentra el archivo con el id: " + idArchivoMasivo);
        }
        Long idCargue = masivoArchivo.getIdCargue();
        if (idCargue == null) {
            return new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        }

        Long idConsola = consultasCore.consultarIdConsolaAporte(idCargue);
        if (idConsola == null) {
            return new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        }

        ConsultarLogErrorArchivo consultarLogErrorArchivo = new ConsultarLogErrorArchivo(idConsola);
        consultarLogErrorArchivo.execute();
        return consultarLogErrorArchivo.getResult();
    }

    
     @Override
     public Map<String, String> radicarSolicitudAportesMasivos(SolicitudModeloDTO solicitudDTO,
     UserDTO userDTO){
            try {
                logger.info("Inicio de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");
                
                
                Long idSolicitudGlobal = crearActualizarSolicitud(solicitudDTO);
                
    
    
                /* se realiza el llamado para que radique la solicitud */
                generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());
    
    
                /* se asigna la solicitud al analista */
                
                asignarSolicitudAnalista(idSolicitudGlobal, ProcesoEnum.PAGO_APORTES_MANUAL, userDTO);
                ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
                consultarSolicitudGlobal.execute();
                SolicitudModeloDTO solicitudMasivaDTO = consultarSolicitudGlobal.getResult();
                logger.info("numeroRadicacion"+solicitudMasivaDTO.getNumeroRadicacion());
                logger.info("idSolicitud"+solicitudMasivaDTO.getIdSolicitud().toString().toString());
                
                logger.info("Fin de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");
                Map<String, String> mapa = new HashMap<>();
                mapa.put("numeroRadicacion", solicitudMasivaDTO.getNumeroRadicacion());
                mapa.put("idSolicitud", solicitudMasivaDTO.getIdSolicitud().toString().toString());
                
                
                return mapa;
            } catch (AsopagosException ae) {
                throw ae;
            } catch (Exception e) {
                logger.error("Ocurrió un error radicando una solicitud de devolución", e);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
            }
     }



    @Override
    public void eliminarArchivoMasivo(Long idArchivoMasivo) {
        consultasPila.eliminarArchivoMasivo(idArchivoMasivo);
    }

     @Override
     public Map<String, String> simularAporteMasivo(String nombreArchivo, Long idCuentaBancaria, UserDTO userDTO){
		logger.debug("Inicio de método simularAporteMasivo(Long nombreArchivo)");
        Long idArchivoMasivo = Long.valueOf(nombreArchivo);
        
        MasivoArchivo masivoArchivo = consultasPila.consultarArchivoMasivo(idArchivoMasivo);

		consultasPila.simularAporteMasivo(masivoArchivo.getNombreArchivo());
        Map<String, String> solicitud = new HashMap<String, String>();

        solicitud = procesarArchivoAportes(masivoArchivo.getId(), userDTO);
        masivoArchivo = consultasPila.consultarArchivoMasivo(idArchivoMasivo);
        masivoArchivo.setNumeroRadicacion(solicitud.get("numeroRadicacion"));
        masivoArchivo.setSolicitud(Long.parseLong(solicitud.get("idSolicitud")));
        masivoArchivo.setIdCuentaBancaria(idCuentaBancaria);

        consultasPila.crearPersonaAporteMasivo();

        consultasPila.actualizarSolicitudMasiva(masivoArchivo);
		logger.debug("Fin de método simularAporteMasivo(Long nombreArchivo)");
        return solicitud;
     }

     @Override
     public void finalizarAporteMasivo(String nombreArchivo){
		logger.debug("Inicio de método finalizarAporteMasivo(Long nombreArchivo)");
		consultasPila.finalizarAporteMasivo(nombreArchivo);
		logger.debug("Fin de método finalizarAporteMasivo(Long nombreArchivo)");
     }

    @Override
    public void finalizarCorreccionMasiva(Long idSolicitud, UserDTO userDTO){
        logger.debug("Inicio de método finalizarCorreccionMasiva(Long nombreArchivo)");
        InformacionSolicitudDTO infoCor = new InformacionSolicitudDTO();
        infoCor.setIdSolicitud(idSolicitud);
        infoCor.setUserDTO(userDTO);
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        //registroGeneralDTO = consultarRegistroGeneral(idSolicitud);
        // logger.info("id transaccion "+ registroGeneralDTO.getTransaccion());
        // RegistrarRelacionarAportes registrarRelacionarAportes = new RegistrarRelacionarAportes(registroGeneralDTO.getTransaccion(), Boolean.TRUE, Boolean.FALSE);
        // registrarRelacionarAportes.execute();
        

        FinalizarCorreccionAsyncMasiva servicio = new FinalizarCorreccionAsyncMasiva(infoCor);
        servicio.execute();

        logger.debug("Fin de método finalizarCorreccionMasiva(Long nombreArchivo)");
    }

	/**
	 * Método encargado de dar FileFormat de un archivo
	 * 
	 * @param informacionArchivoDTO,
	 *            archivo al cual se le dara el fileFormat
	 * @return retorna el fileFormat encontrado
	 */
	private FileFormat darFileFormat(InformacionArchivoDTO informacionArchivoDTO) {
		FileFormat fileFormat;
		if (informacionArchivoDTO.getFileName().toUpperCase()
				.endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)) {
			fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
			return fileFormat;
		}
		if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLSX)) {
			fileFormat = FileFormat.EXCEL_XLSX;
			return fileFormat;
		}
		if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.EXT_XLS)) {
			fileFormat = FileFormat.EXCEL_XLS;
			return fileFormat;
		} else {
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
		}

	}

	private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia obtenerArchivo(String)");
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        String[] parts = archivoId.split("_");
        archivoId = parts[0];
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }


    private InformacionArchivoDTO obtenerArchivo(Long idArchivoMasivo) {
        logger.debug("Inicia obtenerArchivo(Long)");
        MasivoArchivo masivoArchivo =  consultasPila.consultarArchivoMasivo(idArchivoMasivo);
        InformacionArchivoDTO archivoMultiple = new InformacionArchivoDTO();
        String[] parts = masivoArchivo.getNombreArchivo().split("_");
        String archivoId = parts[0];
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        archivoMultiple = (InformacionArchivoDTO) consultarArchivo.getResult();
        logger.debug("Finaliza obtenerArchivo(String)");
        return archivoMultiple;
    }

    /**
     * Realiza el registro del cargue de archivo de actualizacion de informacion
     *
     * @param cargueArchivoActualizacionDTO Informacion cargue archivo
     * actualizacion
     * @return Identificador del cargue
     */
    private Long crearActualizarCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        CrearCargueArchivoActualizacion crearCargueArchivoActualizacion = new CrearCargueArchivoActualizacion(
                cargueArchivoActualizacionDTO);
        crearCargueArchivoActualizacion.execute();
        return crearCargueArchivoActualizacion.getResult();
    }


    /**
     * Método encargado de llamar el cliente que se encarga de registrar en
     * consola de estado de cargue múltiple
     *
     * @param consolaEstadoCargueProcesoDTO
     */
    private void registrarConsolaEstado(ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        RegistrarCargueConsolaEstado registroConsola = new RegistrarCargueConsolaEstado(consolaEstadoCargueProcesoDTO);
        registroConsola.execute();
    }


    private void actualizarConsolaEstado(Long idCargue, ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO) {
        ActualizarCargueConsolaEstado actualizarConsola = new ActualizarCargueConsolaEstado(idCargue, consolaEstadoCargueProcesoDTO);
        actualizarConsola.execute();
    }

	/*
     * (non-Javadoc)
     *
     * @see com.asopagos.novedades.composite.service.NovedadesCompositeService#validarArchivoReintegroTrabajadores(com.asopagos.enumeraciones.
     * TipoArchivoRespuestaEnum, com.asopagos.dto.CargueMultipleDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public ResultadoArchivoAporteDTO validarArchivoDevolucion(ArchivoDevolucionDTO archivoDevolucion,
    UserDTO userDTO) {

        //DevolucionAporteModeloDTO
        logger.info("Inicia validarArchivoDevolucion");
        // Se obtiene la informacion del archivo cargado
        InformacionArchivoDTO archivo = obtenerArchivo(archivoDevolucion.getCargue().getCodigoIdentificacionECM());

        // Se verifica la estructura y se obtiene las lineas para procesarlas
        ResultadoArchivoAporteDTO resultDTO = verificarEstructuraArchivoDevolucion(archivo,archivoDevolucion,userDTO);



        //Si hay registros validos procesamos esos registros validos.
         //if (CollectionUtils.isNotEmpty(resultDTO.getListActualizacionInfoNovedad())) {
            //logger.info("Se llama el metodo validarArchivoReintegroTrabajadores ln 1594");

             //List<SolicitudNovedadDTO> list = procesarNovedadReintegroTrabajadores(resultDTO.getListActualizacionInfoNovedad(), idCargue, userDTO);


        //}
        return resultDTO;

    }
	
    
    
	@Override
    public ResultadoArchivoAporteDTO verificarEstructuraArchivoDevolucion(InformacionArchivoDTO archivo, ArchivoDevolucionDTO archivoDevolucion, UserDTO userDTO) {
        Long fileDefinitionId = null;
        ArchivosAportesMasivosUtils archivosAportesMasivosUtils = new ArchivosAportesMasivosUtils(consultasCore);

        // Se verifica el tipo de archivo cargado
        fileDefinitionId = darFileDefinitionId(
                    ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_DEVOLUCION.toString());
        
        ResultadoArchivoAporteDTO resultadoValidacion = new ResultadoArchivoAporteDTO();
        resultadoValidacion.setEcmIdentificador(archivoDevolucion.getCargue().getCodigoIdentificacionECM());
        resultadoValidacion.setIdArchivoMasivo(archivoDevolucion.getCargue().getIdCargueArchivoActualizacion());

        
        // Se obtiene el formato de archivo
        FileFormat fileFormat = darFileFormat(archivo);
        // Se llena el contexto de lectura de archivo
        List<DatosRadicacionMasivaDTO> listInfoActualizar = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        List<ResultadoHallazgosValidacionArchivoDTO> resHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistroError = new ArrayList<Long>();

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listInfoActualizar);
        context.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, 0L);
        context.put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);

        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
		List<AnalisisDevolucionDTO> listAnalisisDevolucionDTO = new ArrayList<>();
        SolicitudModeloDTO solicitudModeloDTO = new SolicitudModeloDTO();

        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, archivo.getDataFile(), fileDefinitionId);

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);

            resultadoValidacion.setResultadoHallazgosAportesMasivos(listaHallazgos);


            resHallazgos.addAll( (List<ResultadoHallazgosValidacionArchivoDTO>)
            outDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));

            resHallazgos.addAll(archivosAportesMasivosUtils.consultarListaHallazgos(fileDefinitionId, outDTO));

        


            listInfoActualizar = (List<DatosRadicacionMasivaDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);
            
            logger.info("listInfoActualizar verificarEstructuraArchivoDevolucion: "+ listInfoActualizar.size());

        
            logger.info("listaHallazgos verificarEstructuraArchivoDevolucion: "+ listaHallazgos.size());
            String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
            CargueArchivoActualizacionDTO cargue = new CargueArchivoActualizacionDTO();
            cargue.setNombreArchivo(archivo.getFileName());
            cargue.setCodigoIdentificacionECM(archivoDevolucion.getCargue().getCodigoIdentificacionECM());
            cargue.setEstado(EstadoCargueArchivoActualizacionEnum.EN_PROCESAMIENTO);
            Long idCargue = crearActualizarCargueArchivoActualizacion(cargue);

            List<ResultadoAporteMasivoDTO> resAportes = 
            (List<ResultadoAporteMasivoDTO>) outDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS);

            Long cantidadRegistos = (long) outDTO.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO);
            if (!resAportes.isEmpty()) {
                cantidadRegistos += (long) resAportes.size();
            }

            ConsolaEstadoCargueProcesoDTO consolaEstadoCargue = new ConsolaEstadoCargueProcesoDTO();
            consolaEstadoCargue.setCargue_id(idCargue);
            consolaEstadoCargue.setCcf(codigoCaja);
            consolaEstadoCargue.setEstado(EstadoCargueMasivoEnum.EN_PROCESO);
            consolaEstadoCargue.setFechaInicio(Calendar.getInstance().getTimeInMillis());
            consolaEstadoCargue.setGradoAvance(EstadoCargaMultipleEnum.CARGADO.getGradoAvance());
            consolaEstadoCargue.setIdentificacionECM(archivoDevolucion.getCargue().getCodigoIdentificacionECM());
            consolaEstadoCargue.setNombreArchivo(cargue.getNombreArchivo());
            consolaEstadoCargue.setProceso(TipoProcesoMasivoEnum.CARGUE_DEVOLUCION_MASIVOS);
            consolaEstadoCargue.setUsuario(userDTO.getNombreUsuario());
            consolaEstadoCargue.setLstErroresArhivo(resHallazgos);
            consolaEstadoCargue.setNumRegistroObjetivo(cantidadRegistos);
            consolaEstadoCargue.setNumRegistroProcesado(cantidadRegistos);
            consolaEstadoCargue.setNumRegistroConErrores((long) resHallazgos.size());
            registrarConsolaEstado(consolaEstadoCargue);

            if(listInfoActualizar.size() > 0 && listaHallazgos.size() == 0){
                solicitudModeloDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
                solicitudModeloDTO.setFechaRadicacion(new Date().getTime());
                solicitudModeloDTO.setFechaCreacion(new Date().getTime());
                solicitudModeloDTO.setTipoTransaccion(TipoTransaccionEnum.DEVOLUCION_APORTES_MASIVA);
                solicitudModeloDTO.setDestinatario(userDTO.getNombreUsuario());
                solicitudModeloDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
                solicitudModeloDTO.setSedeDestinatario("1");


                archivoDevolucion.setDatosRadicacionMasiva(listInfoActualizar);


                Map<String, String> mapa = new HashMap<>();
                //primer String = radicado
                //Segundo String = idsolicitud
                mapa = radicarSolicitudDevolucionMasiva(solicitudModeloDTO,userDTO);

                consultasPila.procesarArchivoDevolucion(archivoDevolucion,Long.valueOf(mapa.get("idSolicitud")),mapa.get("numeroRadicacion"), userDTO);

                resultadoValidacion.setNumeroRadicado(mapa.get("numeroRadicacion"));
                ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
                conCargueMasivo.setCargue_id(idCargue);
                conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
                conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DEVOLUCION_MASIVOS);
                actualizarConsolaEstado(idCargue, conCargueMasivo);

               
            }else if (listaHallazgos.size() > 0){
                ConsolaEstadoCargueProcesoDTO conCargueMasivo = new ConsolaEstadoCargueProcesoDTO();
                conCargueMasivo.setCargue_id(idCargue);
                conCargueMasivo.setEstado(EstadoCargueMasivoEnum.FIN_ERROR);
                conCargueMasivo.setProceso(TipoProcesoMasivoEnum.CARGUE_DEVOLUCION_MASIVOS);
                actualizarConsolaEstado(idCargue, conCargueMasivo);
            }

            

            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }


            resultadoValidacion.setResultadoHallazgosAportesMasivos(resHallazgos);
            // Persist en tablas de Masivo Archivo, y datos temporales
        

            return resultadoValidacion;
        } catch (FileProcessingException e) {
            logger.info("Error en verificarEstructuraArchivoDevolucion: "+ e.getMessage());
            e.printStackTrace();
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
	}
    

		private Long darFileDefinitionId(String parametroConstante) {
		return new Long(CacheManager.getConstante(parametroConstante).toString());
	}

	/**
     * Método que se encarga de transformar un objeto
     * <code>SolicitudDevolucionDTO</code> a un
     * <code>SolicitudDevolucionAporteModeloDTO</code>
     * 
     * @param solicitudDevolucionDTO
     *                               El objeto a transformar
     * @param userDTO
     *                               Información del usuario, tomada del contexto
     * @return El objeto <code>SolicitudDevolucionAporteModeloDTO</code> equivalente
     */
    /** 
    private SolicitudDevolucionAporteModeloDTO transformarSolicitudDevolucionAporteMasivo(
        SolicitudModeloDTO solicitudDevolucionDTO,
            UserDTO userDTO) {
        logger.info(
                "Inicio de método transformarSolicitudDevolucionAporte(SolicitudDevolucionDTO solicitudDevolucionDTO, UserDTO userDTO)");
        SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = new SolicitudDevolucionAporteModeloDTO();

        // Datos de la solicitud global
        solicitudDevolucionAporteDTO.setCanalRecepcion(CanalRecepcionEnum.PRESENCIAL);
        solicitudDevolucionAporteDTO.setFechaCreacion(new Date().getTime());
        solicitudDevolucionAporteDTO.setFechaRadicacion(new Date().getTime());
        solicitudDevolucionAporteDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitudDevolucionAporteDTO.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitudDevolucionAporteDTO.setObservacion(solicitudDevolucionDTO.getComentarios());
        if(solicitudDevolucionDTO.getNumeroIdentificacion()==null){
            //Solicitud principal masiva sin aportante en los datos
            solicitudDevolucionAporteDTO.setTipoTransaccion(TipoTransaccionEnum.DEVOLUCION_APORTES_MASIVA);
        }else{
            // solicitudes secundarias con los datos
            solicitudDevolucionAporteDTO.setTipoTransaccion(TipoTransaccionEnum.DEVOLUCION_APORTES);  
            // Datos de la solicitud de devolución
            solicitudDevolucionAporteDTO.setTipoSolicitante(solicitudDevolucionDTO.getTipoSolicitante());
            PersonaModeloDTO personaDTO = consultarPersona(solicitudDevolucionDTO.getNumeroIdentificacion(),
                solicitudDevolucionDTO.getTipoIdentificacion());
            solicitudDevolucionAporteDTO.setIdPersona(personaDTO.getIdPersona());
        }

        

        logger.info(
                "Fin de método transformarSolicitudDevolucionAporte(SolicitudDevolucionDTO solicitudDevolucionDTO, UserDTO userDTO)");
        return solicitudDevolucionAporteDTO;
    }
    */
    
   /**
     * Método que consulta los datos de una persona, por tipo y número de documento
     * 
     * @param numeroIdentificacion
     *                             Número de identificación
     * @param tipoIdentificacion
     *                             Tipo de identificación
     * @return Objeto <code>PersonaModeloDTO</code> con los datos de persona
     */
    private PersonaModeloDTO consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        logger.info(
                "Inicio de método consultarPersona(String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion)");
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(numeroIdentificacion,
                tipoIdentificacion);
        consultarDatosPersona.execute();
        PersonaModeloDTO personaDTO = consultarDatosPersona.getResult();
        logger.info(
                "Fin de método consultarPersona(String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion)");
        return personaDTO;
    }
    
	/*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * radicarSolicitudDevolucion(com.asopagos.aportes.composite.dto.
     * SolicitudDevolucionDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public Map<String, String> radicarSolicitudDevolucionMasiva(SolicitudModeloDTO solicitudDTO,
            UserDTO userDTO) {
        try {
            logger.info("Inicio de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");
            
            
            Long idSolicitudGlobal = crearActualizarSolicitud(solicitudDTO);
            


            /* se realiza el llamado para que radique la solicitud */
            generarNumeroRadicado(idSolicitudGlobal, userDTO.getSedeCajaCompensacion());


            /* se asigna la solicitud al analista */
            
            asignarSolicitudAnalista(idSolicitudGlobal, ProcesoEnum.DEVOLUCION_APORTES, userDTO);
            ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
            consultarSolicitudGlobal.execute();
            SolicitudModeloDTO solicitudMasivaDTO = consultarSolicitudGlobal.getResult();
            logger.info("numeroRadicacion"+solicitudMasivaDTO.getNumeroRadicacion());
            logger.info("idSolicitud"+solicitudMasivaDTO.getIdSolicitud().toString().toString());
            
            logger.info("Fin de método radicarSolicitudDevolucion(SolicitudDevolucionDTO solicitudDevolucion)");
            Map<String, String> mapa = new HashMap<>();
            mapa.put("numeroRadicacion", solicitudMasivaDTO.getNumeroRadicacion());
            mapa.put("idSolicitud", solicitudMasivaDTO.getIdSolicitud().toString().toString());
            
            
            return mapa;
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando una solicitud de devolución", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }


        /**
     * Método que hace la peticion REST al servicio de generar nuemro de
     * radicado
     * 
     * @param idSolicitud
     *                             <code>Long</code> El identificador de la
     *                             solicitud
     * @param sedeCajaCompensacion
     *                             <code>String</code> El usuario del sistema
     */
    private String generarNumeroRadicado(Long idSolicitud, String sedeCajaCompensacion) {
        logger.info("Inicia generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud radicarSolicitudService = new RadicarSolicitud(idSolicitud, sedeCajaCompensacion);
        radicarSolicitudService.execute();
        logger.info("Finaliza generarNumeroRadicado( idSolicitud, sedeCajaCompensacion)");
        return radicarSolicitudService.getResult();
    }


    /**
     * Método encargado de asignar la solicitud a al analista de aportes y guardar
     * la información en la solicitud.
     * 
     * @param solicitudDTO
     *                      solicitud a asignar analista.
     * @param numeroProceso
     *                      número del proceso a atender.
     * @param userDTO
     *                      usuario autenticado.
     * @return id de instancia.
     */
    private Long asignarSolicitudAnalista(Long idSolicitudGlobal, ProcesoEnum proceso, UserDTO userDTO) {
        logger.info(
                "Inicio de método asignarSolicitudAnalista(SolicitudAporteModeloDTO solicitudAporteModeloDTO,UserDTO userDTO)");
        /* se inicia el proceso asignandose la tarea al analista de aportes */
        String destinatario = null;
        if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(proceso) || ProcesoEnum.DEVOLUCION_APORTES.equals(proceso)) {
            destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                    proceso);
        } else {
            /*
             * si se trata de una solicitud de corrección se asinga a si mismo(el analista)
             */
            destinatario = userDTO.getNombreUsuario();
        }
        UsuarioDTO usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);

        /* Consulta de la solicitud global */
        ConsultarSolicitudGlobal consultarSolicitudGlobal = new ConsultarSolicitudGlobal(idSolicitudGlobal);
        consultarSolicitudGlobal.execute();
        SolicitudModeloDTO solicitudDTO = consultarSolicitudGlobal.getResult();

        /* se inicia el proceso de aportes manual asociando la tarea 482 al analista */
        Map<String, Object> parametrosProceso = new HashMap<>();
        parametrosProceso.put("usuarioAnalista", usuarioDTO.getNombreUsuario());
        parametrosProceso.put("idSolicitud", solicitudDTO.getIdSolicitud());
        parametrosProceso.put("numeroRadicado", solicitudDTO.getNumeroRadicacion());
        Long idInstanciaProceso = iniciarProceso(proceso, parametrosProceso);
        logger.info("idInstanciaProceso"+idInstanciaProceso);
        /* se actualizan los datos del analista y del proceso a la solciitud. */
        solicitudDTO.setDestinatario(usuarioDTO.getNombreUsuario());
        solicitudDTO.setSedeDestinatario(usuarioDTO.getCodigoSede());
        solicitudDTO.setIdInstanciaProceso(idInstanciaProceso.toString());
        crearActualizarSolicitud(solicitudDTO);
        logger.info(
                "Fin de método asignarSolicitudAnalista(SolicitudAporteModeloDTO solicitudAporteModeloDTO,UserDTO userDTO)");
        return idInstanciaProceso;
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     * 
     * @param nombreUsuarioCaja
     *                          <code>String</code> El nombre de usuario del
     *                          funcionario de la
     *                          caja que realiza la consulta
     * 
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     *         usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.info("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja  )");
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        logger.info("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return obtenerDatosUsuariosCajaCompensacionService.getResult();
    }

    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     * 
     * @param sedeCaja
     *                    <code>Long</code> el identificador del afiliado
     * @param procesoEnum
     *                    <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     *         caja
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.info("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.info("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        return ejecutarAsignacion.getResult();
    }


        /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * actualizarSolicitudTrazabilidad(java.lang.Long,
     * com.asopagos.enumeraciones.core.ProcesoEnum,
     * com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum, java.lang.Long,
     * com.asopagos.rest.security.dto.UserDTO)
     */
    
    public void actualizarSolicitudTrazabilidad(Long idSolicitudGlobal, ProcesoEnum proceso,
            EstadoSolicitudAporteEnum estado,
            Long idComunicado, @Context UserDTO userDTO) {
                logger.info("Inicia actualizarSolicitudTrazabilidad( String  )");
                ActualizarSolicitudTrazabilidad actualizarSolicitudTrazabilidad = new ActualizarSolicitudTrazabilidad(idSolicitudGlobal, proceso,
                estado, idComunicado);
                actualizarSolicitudTrazabilidad.execute();
                logger.info("Finaliza actualizarSolicitudTrazabilidad( String )");
                
    }

    /**
     * Método que invoca el servicio que crea o actualiza una solicitud global
     * 
     * @param solicitudDTO
     *                     Los datos de la solicitud
     * @return El identificador de la solicitud creada/actualizada
     */
    private Long crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO) {
        logger.info("Inicio de método crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO)");
        CrearActualizarSolicitudGlobal crearActualizarSolicitudGlobal = new CrearActualizarSolicitudGlobal(
                solicitudDTO);
        crearActualizarSolicitudGlobal.execute();
        Long id = crearActualizarSolicitudGlobal.getResult();
        logger.info("Fin de método crearActualizarSolicitud(SolicitudModeloDTO solicitudDTO)");
        return id;
    }

    /**
     * Método encargado de invocar el servicio Iniciar proceso.
     * 
     * @param procesoEnum
     *                          proceso que se desea iniciar.
     * @param parametrosProceso
     *                          parametros del proceso.
     * @return id de la instancia del proceso.
     */
    private Long iniciarProceso(ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso) {
        logger.info("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");

        if (ProcesoEnum.PAGO_APORTES_MANUAL.equals(procesoEnum)) {

            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_AM_TIEMPO_PROCESO_SOLICITUD);
            String tiempoPendienteInformacion = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_AM_TIEMPO_PENDIENTE_INFORMACION);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoPendienteInformacion", tiempoPendienteInformacion);

        } else if (ProcesoEnum.DEVOLUCION_APORTES.equals(procesoEnum)) {

            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_DA_TIEMPO_PROCESO_SOLICITUD);
            String tiempoPendienteInformacion = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_DA_TIEMPO_PENDIENTE_INFORMACION);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);
            parametrosProceso.put("tiempoPendienteInformacion", tiempoPendienteInformacion);
        } else if (ProcesoEnum.CORRECCION_APORTES.equals(procesoEnum)) {
            String tiempoProcesoSolicitud = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.BPM_CA_TIEMPO_PROCESO_SOLICITUD);

            parametrosProceso.put("tiempoProcesoSolicitud", tiempoProcesoSolicitud);

        }

        IniciarProceso iniciarProcesoNovedadService = new IniciarProceso(procesoEnum, parametrosProceso);
        iniciarProcesoNovedadService.execute();
        Long idInstanciaProcesoNovedad = iniciarProcesoNovedadService.getResult();
        logger.info("Inicio de método iniciarProceso(ProcesoEnum procesoEnum, Map<String,Object> parametrosProceso)");
        return idInstanciaProcesoNovedad;

    }

     /**
     * Método que invoca el servicio que consulta una solicitud de devolución de
     * aportes
     * 
     * @param idSolicitudGlobal
     *                          Identificador de la solicitud global
     * @return Objeto <code>SolicitudDevolucionAporteModeloDTO</code> con la
     *         información de la solicitud consultada
     */
    private SolicitudDevolucionAporteModeloDTO consultarSolicitudDevolucionAporte(Long idSolicitudGlobal) {
        logger.info("Inicio de método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
        ConsultarSolicitudDevolucionAporte consultarSolicitudDevolucionAporte = new ConsultarSolicitudDevolucionAporte(
                idSolicitudGlobal);
        consultarSolicitudDevolucionAporte.execute();
        SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte
                .getResult();
        logger.info("Fin de método consultarSolicitudDevolucionAporte(Long idSolicitudGlobal)");
        return solicitudDevolucionAporteDTO;
    }

        /**
     * Método que invoca el servicio de almacenamiento/actualización de una
     * solicitud de devolución de aportes
     * 
     * @param solicitudDevolucionAporteDTO
     *                                     La solicitud de devolución a almacenar
     * @return El identificador de la solicitud global creada/actualizada
     */
        private Long crearActualizarSolicitudDevolucionAporte(
            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO) {
        logger.info("Inicio de método crearActualizarSolicitudDevolucionAporte");
        CrearActualizarSolicitudDevolucionAporte crearActualizarSolicitudDevolucionAporte = new CrearActualizarSolicitudDevolucionAporte(
                solicitudDevolucionAporteDTO);
        crearActualizarSolicitudDevolucionAporte.execute();
        Long id = crearActualizarSolicitudDevolucionAporte.getResult();
        logger.info("Fin de método crearActualizarSolicitudDevolucionAporte");
        return id;
    }

	/*
     * (non-Javadoc)
     * 
     * @see com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * simularDevolucionTemporal(java.lang.Long)
     */
    // @Override
    // public List<DevolucionDTO> simularDevolucionTemporalMasiva(Long idSolicitudGlobal) {
    //     try {
    //         logger.info("Inicio de método simularDevolucionTemporal(Long idSolicitudGlobal)");

    //         // Consulta datos temporales para simulación
    //         List<Long> solicitudes = consultasCore.consultarsolicitudesGlobales(idSolicitudGlobal);
    //         List<DevolucionDTO> devolucionesDTO = consultarDevolucionMasivaTemporal(solicitudes);
    //         int count = 0;
	// 		for(DevolucionDTO devolucionDTO:devolucionesDTO){

    //         // Crea transacción para la simulación
    //         Long idTransaccion = crearTransaccion();

    //         // Crea lista de ids de RegistroGeneral
    //         List<Long> listaIdRegistroGeneral = new ArrayList<>();

    //         // Recorre lista de aportes incluidos en la solicitud de devolución
    //         List<AnalisisDevolucionDTO> listaAnalisisDevolucionDTO = devolucionDTO.getAnalisis();

    //         for (AnalisisDevolucionDTO analisisDevolucionDTO : listaAnalisisDevolucionDTO) {
    //             RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();

    //             // Consulta RegistroGeneral asociado al aporte. Si ya existe un RegistroGeneral
    //             // en simulación, se modifica ése
    //             if (analisisDevolucionDTO.getIdRegistroGeneralNuevo() != null) {
    //                 registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
    //             } else {
    //                 registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneral());
    //                 List<RegistroDetalladoModeloDTO> registrosDetallados = consultarRegistroDetallado(
    //                         analisisDevolucionDTO.getIdRegistroGeneral());

    //                 registroGeneralDTO = crearRegistroGeneralDevCorr(registroGeneralDTO, registrosDetallados,
    //                         analisisDevolucionDTO.getTipoSolicitante());
    //                 // registroGeneralDTO =
    //                 // consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneral());
    //                 // registroGeneralDTO.setId(null);
    //             }

    //             // Asigna valores al nuevo RegistroGeneral que se va a crear
    //             registroGeneralDTO.setRegistroControlManual(solicitudes.get(count));
    //             registroGeneralDTO.setTransaccion(idTransaccion);
    //             registroGeneralDTO.setEsSimulado(Boolean.TRUE);
    //             registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
    //             registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
    //             BigDecimal nuevoAporte = new BigDecimal(0);
    //             if (registroGeneralDTO.getValTotalApoObligatorio() != null) {
    //                 nuevoAporte = registroGeneralDTO.getValTotalApoObligatorio()
    //                         .subtract(analisisDevolucionDTO.getMonto());
    //             } else {
    //                 nuevoAporte = analisisDevolucionDTO.getMonto()
    //                         .subtract(analisisDevolucionDTO.getMontoRegistro());
    //             }

    //             BigDecimal interes = registroGeneralDTO.getValorIntMora() != null ? registroGeneralDTO.getValorIntMora()
    //                     : BigDecimal.ZERO;
    //             BigDecimal nuevoMora = interes.subtract(analisisDevolucionDTO.getInteres());
    //             registroGeneralDTO.setValTotalApoObligatorio(nuevoAporte);
    //             registroGeneralDTO.setValorIntMora(nuevoMora);

    //             // Guarda de la temporal a RegistroGeneral en staging
    //             Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);
    //             listaIdRegistroGeneral.add(idRegistroGeneral);
    //             analisisDevolucionDTO.setIdRegistroGeneralNuevo(idRegistroGeneral);

    //             if (analisisDevolucionDTO.getCotizanteDTO() != null) {
    //                 // Recorre lista de cotizantes del aporte, incluidos en la solicitud de
    //                 // devolución
    //                 for (CotizanteDTO cotizanteDTO : analisisDevolucionDTO.getCotizanteDTO()) {
    //                     RegistroDetalladoModeloDTO registroDetalladoDTO = cotizanteDTO.convertToDTODevolucion();
    //                     registroDetalladoDTO.setRegistroGeneral(idRegistroGeneral);

    //                     RegistroDetalladoModeloDTO registroDetalladoBD = consultarRegistroDetalladoPorId(
    //                             cotizanteDTO.getIdRegistro());
    //                     registroDetalladoDTO.setTipoCotizante(registroDetalladoBD.getTipoCotizante());
    //                     registroDetalladoDTO.setOutTipoAfiliado(registroDetalladoBD.getOutTipoAfiliado());
    //                     registroDetalladoDTO
    //                             .setTipoIdentificacionCotizante(registroDetalladoBD.getTipoIdentificacionCotizante());
    //                     registroDetalladoDTO.setNumeroIdentificacionCotizante(
    //                             registroDetalladoBD.getNumeroIdentificacionCotizante());
    //                     registroDetalladoDTO.setCodDepartamento(registroDetalladoBD.getCodDepartamento());
    //                     registroDetalladoDTO.setCodMunicipio(registroDetalladoBD.getCodMunicipio());
    //                     registroDetalladoDTO.setPrimerApellido(registroDetalladoBD.getPrimerApellido());
    //                     registroDetalladoDTO.setPrimerNombre(registroDetalladoBD.getPrimerNombre());
    //                     registroDetalladoDTO.setSegundoApellido(registroDetalladoBD.getSegundoApellido());
    //                     registroDetalladoDTO.setSegundoNombre(registroDetalladoBD.getSegundoNombre());

    //                     // Si no existe simulación previa, se crea el registro en RegistroDetallado;
    //                     // sino, se actualiza
    //                     registroDetalladoDTO.setId(cotizanteDTO.getIdRegistroDetalladoNuevo());
    //                     Long idRegistroDetallado = guardarRegistroDetallado(registroDetalladoDTO);
    //                     cotizanteDTO.setIdRegistroDetalladoNuevo(idRegistroDetallado);
    //                 }

    //             }
    //         }

    //         // Aplica ejecución de armado de tablas en staging
    //         ejecutarArmadoStaging(idTransaccion);

    //         // Invoca simulación
    //         simularFasePila2(idTransaccion);

    //         // Invoca borrado staging
    //         ejecutarBorradoStaging(idTransaccion);

    //         // Consulta resultados posteriores a la simulación
    //         for (Long idRegistroGeneral : listaIdRegistroGeneral) {
    //             List<RegistroDetalladoModeloDTO> listaRegistroDetalladoDTO = consultarRegistroDetallado(
    //                     idRegistroGeneral);

    //             for (AnalisisDevolucionDTO analisisDevolucionDTO : listaAnalisisDevolucionDTO) {
    //                 if (analisisDevolucionDTO.getIdRegistroGeneralNuevo() == idRegistroGeneral
    //                         && analisisDevolucionDTO.getCotizanteDTO() != null) {
    //                     analisisDevolucionDTO.setCotizanteDTO(
    //                             asignarEvaluacionCotizante(analisisDevolucionDTO.getCotizanteDTO(),
    //                                     listaRegistroDetalladoDTO, false));
    //                     break;
    //                 }
    //             }
    //         }
    //         // Almacena datos de simulación en tabla temporal
    //         guardarDevolucionTemporalMasiva(solicitudes.get(count), devolucionDTO);
	// 	}


    //         logger.info("Fin de método simularDevolucionTemporal(Long idSolicitudGlobal)");
    //         return devolucionesDTO;
    //     } catch (Exception e) {
    //         logger.error("Ocurrió un errror en el método simularDevolucionTemporal(Long idSolicitudGlobal)", e);
    //         throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
    //     }
    // }
    
    
    /**
     * Método que se encarga de asignar los estados de evaluación de cotizante.
     * 
     * @param cotizantes
     *                              a asignarle los estados de evaluación.
     * @param registroDetalladoList
     *                              registros detallado con la evaluacion.
     * @return lista de cotizante con los estados de evaluacion asignados.
     */
    private List<CotizanteDTO> asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes,
            List<RegistroDetalladoModeloDTO> registroDetalladoList, boolean aporteManual) {
        logger.info(
                "Inicio de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        for (CotizanteDTO cotizante : cotizantes) {
            for (RegistroDetalladoModeloDTO registroDetalladoModeloDTO : registroDetalladoList) {
                if ((aporteManual && cotizante.getIdCotizante() != null
                        && cotizante.getIdCotizante().equals(registroDetalladoModeloDTO.getId()))
                        || (!aporteManual && cotizante.getIdRegistroDetalladoNuevo() != null
                                && cotizante.getIdRegistroDetalladoNuevo()
                                        .equals(registroDetalladoModeloDTO.getId()))) {
                    EvaluacionDTO evaluacion = new EvaluacionDTO();
                    if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setDepV0(registroDetalladoModeloDTO.getOutEstadoValidacionV0());
                        evaluacion.setDepV1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV2(registroDetalladoModeloDTO.getOutEstadoValidacionV2());
                        evaluacion.setDepV3(registroDetalladoModeloDTO.getOutEstadoValidacionV3());
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setIdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.PENSIONADO.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setPdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    }
                    evaluacion.setPersona(cotizante.getTipoAfiliado());
                    evaluacion.setValorAporte(registroDetalladoModeloDTO.getAporteObligatorio());
                    if (aporteManual) {
                        cotizante.setEvaluacion(evaluacion);
                    } else {
                        cotizante.setEvaluacionSimulada(evaluacion);
                    }

                    cotizante.setEvaluacionSimulacion(calcularEstadoSimulacion(registroDetalladoModeloDTO));
                }
            }
        }

        logger.info(
                "Fin de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        return cotizantes;
    }
    
    /**
     * Método encargado de calcular el resultado del estado de la simulación, para
     * saber si fue o no superada.
     * 
     * @param evaluación
     *                   de la simulación.
     * @return resultado de la simulacion
     */
    private EstadoValidacionRegistroAporteEnum calcularEstadoSimulacion(RegistroDetalladoModeloDTO registroDetallado) {
        logger.info("Inicio de método calcularEstadoSimulacion(EvaluacionDTO evaluacion)");

        EstadoValidacionRegistroAporteEnum noOk = EstadoValidacionRegistroAporteEnum.NO_OK;
        if (MarcaRegistroAporteArchivoEnum.NO_VALIDADO_BD
                .equals(registroDetallado.getOutMarcaValidacionRegistroAporte())) {
            return EstadoValidacionRegistroAporteEnum.NO_VALIDADO_BD;
        }
        if (EstadoRegistroAportesArchivoEnum.NO_OK.equals(registroDetallado.getOutEstadoRegistroAporte())
                || registroDetallado.getOutEstadoRegistroAporte() == null
                || registroDetallado.getOutMarcaValidacionRegistroAporte() == null) {
            return noOk;
        }
        if (EstadoValidacionRegistroAporteEnum.NO_CUMPLE.equals(registroDetallado.getOutEstadoValidacionV0())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV2())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV3())) {
            return noOk;
        } else {
            return EstadoValidacionRegistroAporteEnum.OK;
        }
    }

    
    /**
     * Método encargado de invocar el servicio que consulta los registros detallados
     * por id de registro general.
     * 
     * @param idRegistroGeneral
     *                          id del registro general.
     */
    private List<RegistroDetalladoModeloDTO> consultarRegistroDetallado(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idRegistroGeneral)");
        ConsultarRegistroDetallado consultarRegistroDetalladoService = new ConsultarRegistroDetallado(
                idRegistroGeneral);
        consultarRegistroDetalladoService.execute();
        List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetalladoService.getResult();
        logger.info("Fin de método consultarRegistroDetallado(Long idRegistroGeneral)");
        return registroDetalladoList;
    }

    /**
     * Método encargado de crear un DTO de registro general de PILA a partir de otro
     * existente
     * 
     * @param registroGeneralBase
     * @param tipoSolicitante
     * @return
     */
    private RegistroGeneralModeloDTO crearRegistroGeneralDevCorr(RegistroGeneralModeloDTO registroGeneralBase,
            List<RegistroDetalladoModeloDTO> registrosDetallados, TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        String firmaMetodo = "AportesManualesComposite.crearRegistroGeneralDevolucion(RegistroGeneralModeloDTO, "
                + "List<RegistroDetalladoModeloDTO>, TipoSolicitanteMovimientoAporteEnum)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RegistroGeneralModeloDTO result = registroGeneralBase;
        result.setId(null);

        if (!TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(tipoSolicitante)) {
            BigDecimal interes = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            Integer cantidad = 0;

            for (RegistroDetalladoModeloDTO regDet : registrosDetallados) {
                Boolean usarRegistro = Boolean.FALSE;
                if (regDet.getOutRegistroActual() && ((TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
                        .equals(tipoSolicitante)
                        && TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(regDet.getOutTipoAfiliado()))
                        || (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(tipoSolicitante)
                                && TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(regDet.getOutTipoAfiliado())))) {
                    usarRegistro = Boolean.TRUE;
                }

                if (usarRegistro) {
                    interes = interes.add(regDet.getOutValorMoraCotizante());
                    total = total.add(regDet.getAporteObligatorio());
                    cantidad++;
                }
            }

            result.setValTotalApoObligatorio(total);
            result.setValorIntMora(interes);
            result.setCantidadReg2(cantidad);
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de invocar el servicio que crea o actualiza un registro
     * general.
     * 
     * @param registroGeneralDTO
     *                           registro general dto.
     * @return id del registro guardado o actualizado.
     */
    private Long guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO) {
        logger.info("Inicia guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
        CrearRegistroGeneral crearRegGeneralService = new CrearRegistroGeneral(registroGeneralDTO);
        crearRegGeneralService.execute();
        logger.info("Fin guardarRegistroGeneral(RegistroGeneralModeloDTO registroGeneralDTO)");
        return crearRegGeneralService.getResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * consultarAporteManualTemporal(java.lang.Long)
     */
    @Override
    public List<DevolucionDTO> consultarDevolucionMasivaTemporal(List<Long> idSolicitudes) {
        try {
            logger.info("Inicio de método consultarDevolucionMasivaTemporal(Long idSolicitud)");
            List<DevolucionDTO> devolucionesDTO = null;
            for(Long idSolicitud: idSolicitudes){
            String jsonPayload = consultarDatosTemporales(idSolicitud);
            ObjectMapper mapper = new ObjectMapper();
            DevolucionDTO devolucionDTO = mapper.readValue(jsonPayload, DevolucionDTO.class);
            devolucionesDTO.add(devolucionDTO);
            }
            logger.info("Fin de método consultarDevolucionTemporal(Long idSolicitud)");
            return devolucionesDTO;
        } catch (Exception e) {
            logger.error("Ocurrio un error en consultarDevolucionTemporal(Long idSolicitud)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	   private void ejecutarBorradoStaging(Long idTransaccion) {
        logger.info("Inicio de método ejecutarBorradoStaging(Long idTransaccion)");
        EjecutarBorradoStaging ejecutarBorradoStagingService = new EjecutarBorradoStaging(idTransaccion);
        ejecutarBorradoStagingService.execute();
        logger.info("Fin de método ejecutarBorradoStaging(Long idTransaccion)");
    }

	 private void simularFasePila2(Long idTransaccion) {
        logger.info("Inicio de método simularFasePila2(Long idTransaccion)");
        SimularFasePila2 simularService = new SimularFasePila2(idTransaccion);
        simularService.execute();
        logger.info("Fin de método simularFasePila2(Long idTransaccion)");
    }

	private void ejecutarArmadoStaging(Long idTransaccion) {
        logger.info("Inicio de método ejecutarArmadoStaging(Long idTransaccion)");
        EjecutarArmadoStaging ejecutarArmadoStagingService = new EjecutarArmadoStaging(idTransaccion);
        ejecutarArmadoStagingService.execute();
        logger.info("Fin de método ejecutarArmadoStaging(Long idTransaccion)");
    }

        /**
     * Método que invoca el servicio que consulta los datos temporales.
     * 
     * @param idSolicitud
     *                    id de la solicitud global.
     * @return jsonPayload con los datos temporales.
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        logger.info("Inicio de método consultarDatosTemporales(Long idSolicitud)");
        ConsultarDatosTemporales consultarDatosNovedad = new ConsultarDatosTemporales(idSolicitud);
        consultarDatosNovedad.execute();
        logger.info("Fin de método consultarDatosTemporales(Long idSolicitud)");
        return consultarDatosNovedad.getResult();
    }

    
    /**
     * Método que consulta un registro en <code>RegistroDetallado</code> por
     * identificador
     * 
     * @param idRegistroDetallado
     *                            Identificador del registro
     * @return Objeto <code>RegistroDetalladoModeloDTO</code> con la información del
     *         aporte
     */
    private RegistroDetalladoModeloDTO consultarRegistroDetalladoPorId(Long idRegistroDetallado) {
        logger.info("Inicio de método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
        ConsultarRegistroDetalladoPorId consultarRegistroDetallado = new ConsultarRegistroDetalladoPorId(
                idRegistroDetallado);
        consultarRegistroDetallado.execute();
        logger.info("Fin de método consultarRegistroDetalladoPorId(Long idRegistroDetallado)");
        return consultarRegistroDetallado.getResult();
    }

    /**
     * Método encargado de invocar el servicio que crea o actualiza un registro
     * detallado.
     * 
     * @param registroDetalladoDTO
     *                             registro detallado dto.
     * @return id del resgistro detallado.
     */
    private Long guardarRegistroDetallado(RegistroDetalladoModeloDTO registroDetalladoDTO) {
        logger.info("Inicia guardarRegistroDetallado(RegistroGeneralModeloDTO registroGeneralDTO)");
        CrearRegistroDetallado crearRegDetalladoService = new CrearRegistroDetallado(registroDetalladoDTO);
        crearRegDetalladoService.execute();
        logger.info("Fin guardarRegistroDetallado(RegistroGeneralModeloDTO registroGeneralDTO)");
        return crearRegDetalladoService.getResult();
    }

	/*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.aportes.composite.service.AportesManualesCompositeService#
     * guardarDevolucionTemporal(java.lang.Long,
     * com.asopagos.aportes.composite.dto.AporteManualDTO)
     */
    @Override
    public void guardarDevolucionTemporalMasiva(Long idSolicitud, DevolucionDTO devolucionDTO) {
        try {
            logger.info(
                    "Inicio de método guardarDevolucionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
            BigDecimal totalIntereses;
            BigDecimal totalMonto;
            if (devolucionDTO != null && devolucionDTO.getAnalisis() != null)
                for (AnalisisDevolucionDTO analisisDTO : devolucionDTO.getAnalisis()) {
                    totalIntereses = new BigDecimal(0);
                    totalMonto = new BigDecimal(0);
                    if (analisisDTO.getCotizanteDTO() != null) {
                        for (CotizanteDTO cotizanteDTO : analisisDTO.getCotizanteDTO()) {
                            if (cotizanteDTO.getAportes() != null) {
                                if (cotizanteDTO.getAportes().getMoraAporte() != null
                                        && cotizanteDTO.getAportes().getMoraAporteNuevo() != null) {
                                    totalIntereses = totalIntereses.add(cotizanteDTO.getAportes().getMoraAporte()
                                            .subtract(cotizanteDTO.getAportes().getMoraAporteNuevo()));
                                }
                                if (cotizanteDTO.getAportes().getAporteObligatorio() != null
                                        && cotizanteDTO.getAportes().getAporteObligatorioNuevo() != null) {
                                    totalMonto = totalMonto.add(cotizanteDTO.getAportes().getAporteObligatorio()
                                            .subtract(cotizanteDTO.getAportes().getAporteObligatorioNuevo()));
                                }
                            }
                        }
                    }
                    analisisDTO.setInteresRegistro(totalIntereses);
                    analisisDTO.setMontoRegistro(totalMonto);
                }
			
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload;
            jsonPayload = mapper.writeValueAsString(devolucionDTO);
            guardarDatosTemporales(idSolicitud, jsonPayload);
            logger.info("Fin de método guardarDevolucionTemporal(Long idSolicitud, AporteManualDTO aporteManualDTO)");
        } catch (Exception e) {
            logger.error("Ocurrio un error radicando una solicitud", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	 private void guardarDatosTemporales(Long idsolicitud, String jsonPayload) {
        logger.info("Inicio de método guardarDatosTemporales(Long idsolicitud, String jsonPayload)");
        GuardarDatosTemporales datosTemporalService = new GuardarDatosTemporales(idsolicitud, jsonPayload);
        datosTemporalService.execute();
        logger.info("Fin de método guardarDatosTemporales(Long idsolicitud, String jsonPayload)");
    }


    /**
     * Método que se encarga de realizar el registro de la devolución en base de
     * datos
     * 
     * @param idSolicitudGlobal
     *                          Identificador de la solicitud global relacionada
     * @param userDTO
     *                          Información del usuario, tomada del contexto
     */
    private void registrarDevolucion(Long idSolicitudGlobal, UserDTO userDTO) {
        try {
            logger.info("Inicio de método registrarDevolucion");

            // Consulta la devolución almacenada en la tabla temporal
            List<Long> solicitudes = consultasCore.consultarsolicitudesGlobales(idSolicitudGlobal);
            List<DevolucionDTO> devolucionesDTO = consultarDevolucionMasivaTemporal(solicitudes);
            int count = 0;
            for(DevolucionDTO devolucionDTO:devolucionesDTO){

            // Crea el registro en DevolucionAporte
            DevolucionAporteModeloDTO devolucionAporteDTO = transformarDevolucionAporte(devolucionDTO);
            Long idDevolucionAporte = crearActualizarDevolucionAporte(devolucionAporteDTO);

            // Actualiza la SolicitudDevolucionAporte
            SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteDTO = consultarSolicitudDevolucionAporte(
                solicitudes.get(count));
            solicitudDevolucionAporteDTO.setIdDevolucionAporte(idDevolucionAporte);
            crearActualizarSolicitudDevolucionAporte(solicitudDevolucionAporteDTO);

            // Recorre cada aporte general agregado a la solicitud de devolución
            for (AnalisisDevolucionDTO analisisDevolucionDTO : devolucionDTO.getAnalisis()) {
                // Consulta el AporteGeneral
                AporteGeneralModeloDTO aporteGeneralDTO = consultarAporteGeneral(analisisDevolucionDTO.getIdAporte());
                BigDecimal nuevoAporteGeneral = aporteGeneralDTO.getValorTotalAporteObligatorio();
                BigDecimal nuevoInteresGeneral = aporteGeneralDTO.getValorInteresesMora();

                // Guarda el RegistroGeneral
                RegistroGeneralModeloDTO registroGeneralDTO = null;
                if (!analisisDevolucionDTO.getConDetalle()) {
                    registroGeneralDTO = consultarRegistroGeneralId(aporteGeneralDTO.getIdRegistroGeneral());

                    // se actualiza el registro general anterior para quitar al marca de registro
                    // actual
                    registroGeneralDTO.setOutRegistroActual(Boolean.FALSE);
                    guardarRegistroGeneral(registroGeneralDTO);

                    // se prepara el nuevo registro general
                    // referencia al registro inicial
                    registroGeneralDTO.setOutRegInicial(registroGeneralDTO.getOutRegInicial() != null
                            ? registroGeneralDTO.getOutRegInicial()
                            : registroGeneralDTO.getId());
                    registroGeneralDTO.setOutRegistroActual(Boolean.TRUE);

                    registroGeneralDTO.setId(null);
                    registroGeneralDTO.setRegistroControlManual(solicitudes.get(count));
                    registroGeneralDTO.setEsSimulado(Boolean.TRUE);
                    registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
                    registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
                    nuevoAporteGeneral = aporteGeneralDTO.getValorTotalAporteObligatorio()
                            .subtract(new BigDecimal(analisisDevolucionDTO.getHistorico().getAporteObligatorio()));
                    nuevoInteresGeneral = aporteGeneralDTO.getValorInteresesMora().subtract(new BigDecimal(
                            analisisDevolucionDTO.getHistorico().getMora() != null
                                    ? analisisDevolucionDTO.getHistorico().getMora()
                                    : "0"));
                    aporteGeneralDTO.setValorTotalAporteObligatorio(nuevoAporteGeneral);
                    aporteGeneralDTO.setValorInteresesMora(nuevoInteresGeneral);
                    registroGeneralDTO.setValTotalApoObligatorio(nuevoAporteGeneral);
                    registroGeneralDTO.setValorIntMora(nuevoInteresGeneral);
                } else {
                    registroGeneralDTO = consultarRegistroGeneralId(analisisDevolucionDTO.getIdRegistroGeneralNuevo());
                    registroGeneralDTO.setRegistroControlManual(solicitudes.get(count));
                    registroGeneralDTO.setEsSimulado(Boolean.TRUE);
                    registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
                    registroGeneralDTO
                            .setOutEstadoArchivo(EstadoProcesoArchivoEnum.REGISTRADO_O_RELACIONADO_LOS_APORTES);
                }

                Long idRegistroGeneral = guardarRegistroGeneral(registroGeneralDTO);

                Long idRegistroGeneralOriginal = aporteGeneralDTO.getIdRegistroGeneral();
                // Guarda el AporteGeneral
                aporteGeneralDTO.setIdRegistroGeneral(idRegistroGeneral);
                Long idAporteGeneral = crearActualizarAporteGeneral(aporteGeneralDTO);

                if (analisisDevolucionDTO.getConDetalle() && analisisDevolucionDTO.getCotizanteDTO() != null) {
                    for (CotizanteDTO cotizanteDTO : analisisDevolucionDTO.getCotizanteDTO()) {
                        // Consulta el AporteDetallado
                        AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado(
                                cotizanteDTO.getIdCotizante());
                        BigDecimal aporteDevolucion = cotizanteDTO.getAportes().getAporteObligatorioNuevo() != null
                                ? cotizanteDTO.getAportes().getAporteObligatorioNuevo()
                                : BigDecimal.ZERO;
                        BigDecimal moraDevolucion = cotizanteDTO.getAportes().getMoraAporteNuevo() != null
                                ? cotizanteDTO.getAportes().getMoraAporteNuevo()
                                : BigDecimal.ZERO;
                        BigDecimal valorDevolucion = aporteDetalladoDTO.getAporteObligatorio()
                                .subtract(aporteDevolucion);
                        BigDecimal valorDevolucionInteres = aporteDetalladoDTO.getValorMora().subtract(moraDevolucion);
                        BigDecimal nuevoAporteDetallado = aporteDetalladoDTO.getAporteObligatorio()
                                .subtract(valorDevolucion);
                        BigDecimal nuevoInteresDetallado = aporteDetalladoDTO.getValorMora()
                                .subtract(valorDevolucionInteres);
                        aporteDetalladoDTO.setAporteObligatorio(nuevoAporteDetallado);
                        aporteDetalladoDTO.setValorMora(nuevoInteresDetallado);
                        aporteDetalladoDTO.setDiasCotizados(cotizanteDTO.getAportes().getDiasCotizadoNuevo() != null
                                ? cotizanteDTO.getAportes().getDiasCotizadoNuevo().shortValue()
                                : null);
                        aporteDetalladoDTO
                                .setHorasLaboradas(cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo() != null
                                        ? cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo().shortValue()
                                        : null);
                        aporteDetalladoDTO.setSalarioBasico(cotizanteDTO.getAportes().getSalarioBasicoNuevo());
                        aporteDetalladoDTO.setValorIBC(cotizanteDTO.getAportes().getIbcNuevo());
                        aporteDetalladoDTO.setTarifa(cotizanteDTO.getAportes().getTarifaNuevo());
                        nuevoAporteGeneral = nuevoAporteGeneral.subtract(valorDevolucion);
                        nuevoInteresGeneral = nuevoInteresGeneral.subtract(valorDevolucionInteres);

                        // Guarda el RegistroDetallado
                        RegistroDetalladoModeloDTO registroDetalladoDTO = consultarRegistroDetalladoPorId(
                                aporteDetalladoDTO.getIdRegistroDetallado());

                        // se actualiza el registro detallado anterior para quitar al marca de registro
                        // actual
                        registroDetalladoDTO.setOutRegistroActual(Boolean.FALSE);
                        guardarRegistroDetallado(registroDetalladoDTO);

                        // se prepara el nuevo registro detallado
                        // referencia al registro inicial
                        registroDetalladoDTO.setOutRegInicial(registroDetalladoDTO.getOutRegInicial() != null
                                ? registroDetalladoDTO.getOutRegInicial()
                                : registroDetalladoDTO.getId());

                        registroDetalladoDTO.setId(null);
                        registroDetalladoDTO.setOutRegistroActual(Boolean.TRUE);

                        registroDetalladoDTO.setAporteObligatorio(nuevoAporteDetallado);
                        registroDetalladoDTO.setOutValorMoraCotizante(nuevoInteresDetallado);
                        registroDetalladoDTO.setDiasCotizados(cotizanteDTO.getAportes().getDiasCotizadoNuevo() != null
                                ? cotizanteDTO.getAportes().getDiasCotizadoNuevo().shortValue()
                                : null);
                        registroDetalladoDTO
                                .setHorasLaboradas(cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo() != null
                                        ? cotizanteDTO.getAportes().getNumeroHorasLaboralNuevo().shortValue()
                                        : null);
                        registroDetalladoDTO.setSalarioBasico(cotizanteDTO.getAportes().getSalarioBasicoNuevo());
                        registroDetalladoDTO.setValorIBC(cotizanteDTO.getAportes().getIbcNuevo());
                        registroDetalladoDTO.setTarifa(cotizanteDTO.getAportes().getTarifaNuevo());

                        Long idRegistroDetallado = cotizanteDTO.getIdRegistroDetalladoNuevo();
                        // guardarRegistroDetallado(registroDetalladoDTO);
                        // logger.info("registrardev:"+idRegistroDetallado);
                        // Crea transacción
                        Long idTransaccion = crearTransaccion();
                        registroGeneralDTO.setTransaccion(idTransaccion);
                        guardarRegistroGeneral(registroGeneralDTO);

                        if (cotizanteDTO.getTipoCotizante() == null
                                && registroDetalladoDTO.getTipoCotizante() != null) {
                            String codigoTipoCotizante = registroDetalladoDTO.getTipoCotizante().toString();
                            cotizanteDTO.setTipoCotizante(
                                    TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(codigoTipoCotizante)));
                        }
                        // se radican novedades
                        radicarNovedades(aporteGeneralDTO.getIdRegistroGeneral(), registroGeneralDTO.getTransaccion(),
                                cotizanteDTO,
                                devolucionDTO.getSolicitud().getTipoIdentificacion(),
                                devolucionDTO.getSolicitud().getNumeroIdentificacion());

                        // Actualiza el AporteDetallado
                        aporteDetalladoDTO.setIdRegistroDetallado(idRegistroDetallado);
                        crearActualizarAporteDetallado(aporteDetalladoDTO);

                        // Crea el MovimientoAporte
                        MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                                TipoAjusteMovimientoAporteEnum.DEVOLUCION,
                                TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES,
                                EstadoAporteEnum.CORREGIDO, valorDevolucion, valorDevolucionInteres, new Date(),
                                new Date(),
                                aporteDetalladoDTO.getId(), idAporteGeneral);
                        Long idMovimientoAporte = crearActualizarMovimientoAporte(movimientoAporteDTO);

                        // Crea el DevolucionDetalleAporte
                        DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = transformarDevolucionAporteDetalle(
                                null, cotizanteDTO,
                                idMovimientoAporte, idDevolucionAporte, userDTO);
                        crearActualizarDevolucionAporteDetalle(devolucionAporteDetalleDTO);
                    }
                    aporteGeneralDTO.setValorTotalAporteObligatorio(nuevoAporteGeneral);
                    aporteGeneralDTO.setValorInteresesMora(nuevoInteresGeneral);
                    aporteGeneralDTO.setIdRegistroGeneral(idRegistroGeneralOriginal);
                    crearActualizarAporteGeneral(aporteGeneralDTO);
                    registroGeneralDTO.setValTotalApoObligatorio(nuevoAporteGeneral);
                    registroGeneralDTO.setValorIntMora(nuevoInteresGeneral);
                    registroGeneralDTO.setOutEstadoArchivo(EstadoProcesoArchivoEnum.RECAUDO_CONCILIADO);
                    guardarRegistroGeneral(registroGeneralDTO);
                } else {
                    // Crea el MovimientoAporte
                    MovimientoAporteModeloDTO movimientoAporteDTO = new MovimientoAporteModeloDTO(
                            TipoAjusteMovimientoAporteEnum.DEVOLUCION,
                            TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES, EstadoAporteEnum.CORREGIDO,
                            new BigDecimal(analisisDevolucionDTO.getHistorico().getAporteObligatorio()),
                            new BigDecimal(analisisDevolucionDTO.getHistorico().getMora() != null
                                    ? analisisDevolucionDTO.getHistorico().getMora()
                                    : "0"),
                            new Date(), new Date(), null, idAporteGeneral);
                    Long idMovimientoAporte = crearActualizarMovimientoAporte(movimientoAporteDTO);

                    // Crea el DevolucionDetalleAporte
                    DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = transformarDevolucionAporteDetalle(
                            analisisDevolucionDTO,
                            null, idMovimientoAporte, idDevolucionAporte, userDTO);
                    crearActualizarDevolucionAporteDetalle(devolucionAporteDetalleDTO);
                }
            }
            count ++;
            }

            logger.info("Fin de método registrarDevolucion");
        } catch (Exception e) {
            logger.error("Ocurrió un error en el método registrarDevolucion", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
    /**
     * Método que crea o actualiza un registro en la tabla
     * <code>DvolucionAporteDetalle</code>
     * 
     * @param devolucionAporteDetalleDTO
     *                                   Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarDevolucionAporteDetalle(DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO) {
        logger.info("Inicia método crearACtualizaDevolucionAporteDetalle");
        CrearActualizarDevolucionAporteDetalle crearActualizarDevolucionAporteDetalle = new CrearActualizarDevolucionAporteDetalle(
                devolucionAporteDetalleDTO);
        crearActualizarDevolucionAporteDetalle.execute();
        Long id = crearActualizarDevolucionAporteDetalle.getResult();
        logger.info("Inicia método crearACtualizaDevolucionAporteDetalle");
        return id;
    }
    
     /**
     * Método que transforma un <code>CotizanteDTO</code> en un
     * <code>DevolucionAporteDetalleModeloDTO</code>
     * 
     * @param cotizanteDTO
     *                           El <code>CotizanteDTO</code> a transformar
     * @param idMovimientoAporte
     *                           Identificador del movimiento monetario asociado
     * @param idDevolucionAporte
     *                           Identificador de la devolución asociada
     * @param userDTO
     *                           Información del usuario, tomada del contexto
     * @return El <code>DevolucionAporteDetalleModeloDTO</code> resultante
     */
    private DevolucionAporteDetalleModeloDTO transformarDevolucionAporteDetalle(AnalisisDevolucionDTO analisis,
            CotizanteDTO cotizanteDTO,
            Long idMovimientoAporte, Long idDevolucionAporte, UserDTO userDTO) {
        DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO = new DevolucionAporteDetalleModeloDTO();
        if (analisis != null) {
            devolucionAporteDetalleDTO.setComentarioHistorico(analisis.getHistorico().getComentarios());
        } else if (cotizanteDTO != null) {
            devolucionAporteDetalleDTO.setComentarioAportes(cotizanteDTO.getAportes().getComentario());
            devolucionAporteDetalleDTO.setComentarioHistorico(cotizanteDTO.getHistorico().getComentarios());
            devolucionAporteDetalleDTO.setComentarioNovedades(cotizanteDTO.getComentarioNovedad());
            devolucionAporteDetalleDTO
                    .setIncluyeAporteObligatorio(cotizanteDTO.getAportes().getAplicarAporteObligatorio());
            devolucionAporteDetalleDTO.setIncluyeMoraCotizante(cotizanteDTO.getAportes().getAplicarMoraCotizante());
        }
        devolucionAporteDetalleDTO.setFechaGestion(new Date().getTime());
        devolucionAporteDetalleDTO.setIdMovimientoAporte(idMovimientoAporte);
        devolucionAporteDetalleDTO.setIdDevolucionAporte(idDevolucionAporte);
        devolucionAporteDetalleDTO.setUsuario(userDTO.getNombreUsuario());
        return devolucionAporteDetalleDTO;
    }

    
    /**
     * Método que invoca el servicio de creación/actualización de un conjunto de
     * registros en <code>MovimientoAporte</code>
     * 
     * @param listaMovimientoAporteDTO
     *                                 Lista de registros a crear/actualizar
     */
    private Long crearActualizarMovimientoAporte(MovimientoAporteModeloDTO movimientoAporteDTO) {
        ActualizarMovimientoAporte actualizarMovimientoAporte = new ActualizarMovimientoAporte(movimientoAporteDTO);
        actualizarMovimientoAporte.execute();
        return actualizarMovimientoAporte.getResult();
    }
  

    /**
     * Método que invoca el servicio de creación o actualización de un registro en
     * <code>AporteDetallado</code>
     * 
     * @param aporteDetalladoDTO
     *                           Datos del registro a modificar
     * @return El identificador del registro actualizado
     */
    private Long crearActualizarAporteDetallado(AporteDetalladoModeloDTO aporteDetalladoDTO) {
        logger.info("Inicia método crearActualizarDevolucionAporte");
        CrearAporteDetallado crearAporteDetallado = new CrearAporteDetallado(aporteDetalladoDTO);
        crearAporteDetallado.execute();
        Long id = crearAporteDetallado.getResult();
        logger.info("Inicia método crearActualizarDevolucionAporte");
        return id;
    }

     /**
     * Método encargado de radicar las novedades de un cotizante.
     * 
     * @param idRegistroGeneral
     *                                      id del registro general
     * @param idTransaccion
     *                                      id de la transacción.
     * @param cotizante
     *                                      cotizante a revisarle las novedades.
     * @param tipoIdentificacionAportante
     *                                      tipo de identificación del aportante.
     * @param numeroIdentificacionAportante
     *                                      número de identificación del aportante.
     */
    private void radicarNovedades(Long idRegistroGeneral, Long idTransaccion, CotizanteDTO cotizante,
            TipoIdentificacionEnum tipoIdentificacionAportante, String numeroIdentificacionAportante) {
        logger.info("Inicio de método radicarNovedades(List<NovedadCotizanteDTO>)");
        try {
            registrarRelacionarNovedades(idTransaccion);
            List<TemNovedad> novedades = consultarInformacionTemporalNovedad(idRegistroGeneral);
            NovedadAportesDTO novedadAportes = new NovedadAportesDTO();

            RegistroGeneralModeloDTO registroGeneral = consultarRegistroGeneralId(idRegistroGeneral);
            Boolean reintegroEmpleadorProcesado = Boolean.FALSE;

            for (TemNovedad temNovedad : novedades) {
                String accionNovedad = temNovedad.getAccionNovedad();
                if (temNovedad.getEsIngreso()) {
                    if (MarcaNovedadEnum.valueOf(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca().toString())
                            .equals(MarcaNovedadEnum.APLICADA)) {

                        TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.valueOf(temNovedad.getTipoCotizante());
                        TipoAfiliadoEnum tipoAfiliado = tipoCotizante != null ? tipoCotizante.getTipoAfiliado()
                                : TipoAfiliadoEnum.PENSIONADO;
                        TipoIdentificacionEnum tipoIdAportante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdAportante());
                        TipoIdentificacionEnum tipoIdCotizante = TipoIdentificacionEnum
                                .valueOf(temNovedad.getTipoIdCotizante());

                        ProcesoNovedadIngresoDTO datosProcesoIng = new ProcesoNovedadIngresoDTO(tipoAfiliado,
                                tipoIdAportante,
                                temNovedad.getNumeroIdAportante(), tipoIdCotizante, temNovedad.getNumeroIdCotizante(),
                                Boolean.TRUE,
                                temNovedad.getRegistroDetallado(), idRegistroGeneral,
                                registroGeneral.getOutEsEmpleadorReintegrable(),
                                CanalRecepcionEnum.CORRECCION_APORTE, temNovedad.getFechaInicioNovedad().getTime());

                        // se procesa el reintegro del afiliado
                        ProcesarNovedadIngresoAporte procesarNovedadIngresoAporte = new ProcesarNovedadIngresoAporte(datosProcesoIng);
                        Boolean afiliadoReintegrado = procesarNovedadIngresoAporte.getResult();

                        // se procesa el reintegro del empleador (sí aplica)
                        if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(tipoAfiliado)
                                && registroGeneral.getOutEsEmpleadorReintegrable()
                                && !reintegroEmpleadorProcesado && afiliadoReintegrado) {
                            reintegroEmpleadorProcesado = procesarReintegroEmpleador(tipoIdAportante,
                                    temNovedad.getNumeroIdAportante(),
                                    Boolean.TRUE, idRegistroGeneral, CanalRecepcionEnum.CORRECCION_APORTE,
                                    FuncionesUtilitarias
                                            .obtenerFechaMillis(registroGeneral.getPeriodoAporte() + "-01"));
                        }
                        continue;
                    } else {
                        novedadAportes.setTipoNovedad(TipoTransaccionEnum.NOVEDAD_REINTEGRO);
                        novedadAportes.setAplicar(MarcaNovedadEnum.NO_APLICADA);
                        novedadAportes.setCanalRecepcion(CanalRecepcionEnum.APORTE_MANUAL);
                    }

                } else {

                    novedadAportes.setAplicar(MarcaAccionNovedadEnum.valueOf(accionNovedad).getMarca());
                    novedadAportes.setCanalRecepcion(CanalRecepcionEnum.APORTE_MANUAL);
                    novedadAportes.setTipoNovedad(temNovedad.getTipoTransaccion());
                }
                // se consultan las clasificaciones del afiliado (si lo está)
                List<ClasificacionEnum> clasificacionesAfiliado = consultarClasificacionesAfiliado(
                        cotizante.getTipoIdentificacion(),
                        cotizante.getNumeroIdentificacion());
                for (ClasificacionEnum clasificacion : clasificacionesAfiliado) {
                    if (clasificacion != null) {
                        // && clasificacion.equals(cotizante.getTipoCotizante() != null
                        // ? cotizante.getTipoCotizante().getTipoAfiliado() :
                        // cotizante.getTipoAfiliado())) {
                        novedadAportes.setClasificacionAfiliado(clasificacion);
                        break;
                    }
                }
                novedadAportes.setFechaFin(
                        temNovedad.getFechaFinNovedad() != null ? temNovedad.getFechaFinNovedad().getTime() : null);
                novedadAportes
                        .setFechaInicio(temNovedad.getFechaInicioNovedad() != null
                                ? temNovedad.getFechaInicioNovedad().getTime()
                                : null);
                novedadAportes.setIdRegistroDetallado(cotizante.getIdCotizante());
                novedadAportes.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
                novedadAportes.setNumeroIdentificacionAportante(numeroIdentificacionAportante);
                novedadAportes.setTipoIdentificacion(cotizante.getTipoIdentificacion());
                novedadAportes.setTipoIdentificacionAportante(tipoIdentificacionAportante);
                novedadAportes.setComentarios(temNovedad.getMensajeNovedad());
                radicarSolicitudNovedadAportes(novedadAportes);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error radicando las novedades de devolucion", e);
        }

        logger.info("Fin de método radicarNovedades(List<NovedadCotizanteDTO>)");
    }
    
    private Boolean procesarReintegroEmpleador(TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            Boolean tieneCotizanteReintegrable, Long idRegistroGeneral, CanalRecepcionEnum canal, Long periodo) {
        String firmaMetodo = "AportesManualesCompositeBusiness.procesarReintegroEmpleador(TipoIdentificacionEnum, String, Boolean, "
                + "Long, CanalRecepcionEnum, Long)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tieneCotizanteReintegrable) {
            EmpleadorModeloDTO empleador = consultarEmpleadorTipoNumero(tipoIdAportante, numeroIdAportante);

            // se prepara el DTO con los datos del reintegro del empleador
            ActivacionEmpleadorDTO datosReintegro = new ActivacionEmpleadorDTO();
            datosReintegro.setIdAportante(empleador.getIdEmpresa());
            datosReintegro.setIdRegistroGeneral(idRegistroGeneral);
            datosReintegro.setCanalReintegro(canal);
            datosReintegro.setFechaReintegro(periodo);
            datosReintegro.setTipoIdEmpleador(tipoIdAportante);
            datosReintegro.setNumIdEmpleador(numeroIdAportante);
            
            ProcesarActivacionEmpleador procesarActivacionEmpleador = new ProcesarActivacionEmpleador(datosReintegro);
            procesarActivacionEmpleador.execute();
        }

        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.TRUE;
    }
    
    /**
     * Método que se encarga de invocar el servicio de consultar empleadores por
     * tipo y número de identificación.
     * 
     * @param tipoIdentificacion
     *                             tipo de identificación.
     * @param numeroIdentificacion
     *                             número de identificación.
     * @return empleador consultado.
     */
    private EmpleadorModeloDTO consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        logger.info(
                "Inicio de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarEmpleadorTipoNumero consultarEmpleadorService = new ConsultarEmpleadorTipoNumero(numeroIdentificacion,
                tipoIdentificacion);
        consultarEmpleadorService.execute();
        logger.info(
                "Fin de método consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return consultarEmpleadorService.getResult();
    }

    private RegistroGeneralModeloDTO consultarRegistroGeneral(Long idSolicitud) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        RegistroGeneralModeloDTO registroGeneralDTO = null;
        ConsultarRegistroGeneral consultarRegistroGeneralService = new ConsultarRegistroGeneral(
                idSolicitud);
        consultarRegistroGeneralService.execute();
        registroGeneralDTO = consultarRegistroGeneralService.getResult();
        logger.info("Fin de método consultarRegistroDetallado(Long idSolicitud, Boolean limitar)");
        return registroGeneralDTO;
    }
    
     /**
     * Método que se encarga de radicar una solicitud de novedad que de aportes.
     * 
     * @param novedadAportesDTO
     *                          novedad a tramitar.
     */
    private void radicarSolicitudNovedadAportes(NovedadAportesDTO novedadAportesDTO) {
        logger.info("inicio radicarSolicitudNovedadAportes aportes manuales");
        RadicarSolicitudNovedadAportes radicarService = new RadicarSolicitudNovedadAportes(novedadAportesDTO);
        radicarService.execute();
        logger.info("FIN radicarSolicitudNovedadAportes aportes manuales");
    }
    
    /**
     * Método encargado de hacer el llamado al microservicio que consulta las
     * clasificaciones de un afiliado
     * 
     * @param tipoId
     *                 el tipo de identificacion del afiliado
     * @param numeroId
     *                 el numero de identificacion del afiliado
     * 
     * @return List<ClasificacionEnum> con las clasificaciones encontradas
     */
    private List<ClasificacionEnum> consultarClasificacionesAfiliado(TipoIdentificacionEnum tipoId, String numeroId) {
        try {
            ConsultarClasificacionesAfiliado consultarClasificacionAfiliado = new ConsultarClasificacionesAfiliado(
                    numeroId, tipoId);
            consultarClasificacionAfiliado.execute();
            return consultarClasificacionAfiliado.getResult();
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR, e);
        }
    }
    
    /**
     * Método encargado de invocar el servicio que consulta la información temporal.
     * 
     * @param idTransaccion
     *                      id de la transaccion.
     * @return lista de los aportes detallados
     */
    private List<TemNovedad> consultarInformacionTemporalNovedad(Long idTransaccion) {
        logger.info("Inicio de método consultarInformacionTemporalNovedad(Long idTransaccion)");
        ConsultarNovedad consultarNovedadTemporal = new ConsultarNovedad(idTransaccion);
        consultarNovedadTemporal.execute();
        List<TemNovedad> novedades = consultarNovedadTemporal.getResult();
        for (TemNovedad temNovedad : novedades) {
            logger.info("**__** ¡¡¡¡¡ : getAccionNovedad: " + temNovedad.getAccionNovedad());
            logger.info("**__**¨ ¡¡¡¡¡  : getRegistroDetallado" + temNovedad.getRegistroDetallado());
            logger.info("**__**¨ ¡¡¡¡¡getTipoTransaccion: " + temNovedad.getTipoTransaccion());
            logger.info("**__**¨ ¡¡¡¡¡getFechaInicioNovedad: " + temNovedad.getFechaInicioNovedad());
        }
        logger.info("Fin de método consultarInformacionTemporalNovedad(Long idTransaccion)");
        return novedades;
    }
    
    /**
     * Método que invoca el servicio que invoca los procedimientos almacenados de
     * relacionar o registrar novedades.
     * 
     * @param idTransaccion
     *                      id de la transacción.
     */
    private void registrarRelacionarNovedades(Long idTransaccion) {
        logger.info("Inicio de método registrarRelacionarAportesNovedades(Long idTransaccion)");
        RegistrarRelacionarNovedades registrarRelacionarService = new RegistrarRelacionarNovedades(idTransaccion,
                Boolean.TRUE,
                Boolean.FALSE);
        registrarRelacionarService.execute();
        logger.info("Fin de método registrarRelacionarAportesNovedades(Long idTransaccion)");
    }


     /**
     * Método encargado de invocar el servicio que crea una transacción.
     * 
     * @return id de la transacción.
     */
    private Long crearTransaccion() {
        logger.info("Inicio de método crearTransaccion()");
        CrearTransaccion crearTransaccionService = new CrearTransaccion();
        crearTransaccionService.execute();
        logger.info("Fin de método crearTransaccion()");
        return crearTransaccionService.getResult();
    }
    
    /**
     * Método que consulta un registro en <code>AporteDetallado</code> por
     * identificador
     * 
     * @param idAporteDetallado
     *                          Identificador del registro
     * @return Objeto <code>AporteDetalladoModeloDTO</code> con la información del
     *         aporte
     */
    private AporteDetalladoModeloDTO consultarAporteDetallado(Long idAporteDetallado) {
        logger.info("Inicio de método consultarAporteDetallado(Long idAporteDetallado)");
        ConsultarAporteDetallado consultarAporteDetallado = new ConsultarAporteDetallado(idAporteDetallado);
        consultarAporteDetallado.execute();
        AporteDetalladoModeloDTO aporteDetalladoDTO = consultarAporteDetallado.getResult();
        logger.info("Fin de método consultarAporteDetallado(Long idAporteDetallado)");
        return aporteDetalladoDTO;
    }

    /**
     * Método que crea o actualiza un registro en la tabla
     * <code>AporteGeneral</code>
     * 
     * @param aporteGeneralDTO
     *                         Información del registro a actualizar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarAporteGeneral(AporteGeneralModeloDTO aporteGeneralDTO) {
        logger.info("Inicia método crearActualizarAporteGeneral");
        CrearActualizarAporteGeneral crearActualizarAporteGeneral = new CrearActualizarAporteGeneral(aporteGeneralDTO);
        crearActualizarAporteGeneral.execute();
        Long id = crearActualizarAporteGeneral.getResult();
        logger.info("Inicia método crearActualizarAporteGeneral");
        return id;
    }

    /**
     * Método encargado de invocar el servicio que consulta el registro general por
     * id de registro general.
     * 
     * @param idRegistroGeneral
     *                          id del registro general.
     */
    private RegistroGeneralModeloDTO consultarRegistroGeneralId(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        ConsultarRegistroGeneralId consultarRegistroGeneralId = new ConsultarRegistroGeneralId(idRegistroGeneral);
        consultarRegistroGeneralId.execute();
        RegistroGeneralModeloDTO registroGeneralDTO = consultarRegistroGeneralId.getResult();
        logger.info("Fin de método consultarRegistroGeneralId(Long idRegistroGeneral)");
        return registroGeneralDTO;
    }

     /**
     * Método que consulta un registro en <code>AporteGeneral</code>, por
     * identificador
     * 
     * @param idAporteGeneral
     *                        Identificador del registro
     * @return Objeto <code>AporteGeneralModeloDTO</code> con la información del
     *         registro
     */
    private AporteGeneralModeloDTO consultarAporteGeneral(Long idAporteGeneral) {
        logger.info("Inicio de método consultarAporteGeneral(Long idAporteGeneral)");
        ConsultarAporteGeneral consultarAporteGeneral = new ConsultarAporteGeneral(idAporteGeneral);
        consultarAporteGeneral.execute();
        logger.info("Fin de método consultarAporteGeneral(Long idAporteGeneral)");
        return consultarAporteGeneral.getResult();
    }


    /**
     * Método que invoca el servicio de actualización de un registro en
     * <code>DevolucionAporte</code>
     * 
     * @param devolucionAporteDTO
     *                            Información de la devolución a almacenar
     * @return El identificador del registro modificado
     */
    private Long crearActualizarDevolucionAporte(DevolucionAporteModeloDTO devolucionAporteDTO) {
        logger.info("Inicia método crearActualizarDevolucionAporte");
        CrearActualizarDevolucionAporte crearActualizarDevolucionAporte = new CrearActualizarDevolucionAporte(
                devolucionAporteDTO);
        crearActualizarDevolucionAporte.execute();
        logger.info("Fin método crearActualizarDevolucionAporte");
        return crearActualizarDevolucionAporte.getResult();
    }

    /**
     * Método que transforma un <code>DevolucionDTO</code> en un
     * <code>DevolucionAporteModeloDTO</code>
     * 
     * @param devolucionDTO
     *                      El <code>DevolucionDTO</code> a transformar
     * @return Objeto <code>DevolucionAporteModeloDTO</code> resultante
     */
    private DevolucionAporteModeloDTO transformarDevolucionAporte(DevolucionDTO devolucionDTO) {
        logger.info("Inicia método transformarDevolucionAporte");
        DevolucionAporteModeloDTO devolucionAporteDTO = new DevolucionAporteModeloDTO();
        devolucionAporteDTO.setCajaCompensacion(devolucionDTO.getSolicitud().getCaja());
        devolucionAporteDTO.setDescuentoGestionFinanciera(devolucionDTO.getDescuentoGestionFinanciera());
        devolucionAporteDTO.setDescuentoGestionPagoOI(devolucionDTO.getDescuentoGestionPagoOI());
        devolucionAporteDTO.setDescuentoOtro(devolucionDTO.getDescuentoOtro());
        devolucionAporteDTO.setDestinatarioDevolucion(devolucionDTO.getSolicitud().getDestinatario());
        devolucionAporteDTO.setFechaRecepcion(devolucionDTO.getSolicitud().getFechaRecepcion());

        if (devolucionDTO.getSolicitud().getPago() != null) {
            // Crea la forma de pago
            MedioDePagoModeloDTO medioDePagoDTO = devolucionDTO.getSolicitud().getPago()
                    .convertToMedioDePagoModeloDTO();
            medioDePagoDTO = guardarMedioDePago(medioDePagoDTO);
            devolucionAporteDTO.setFormaPago(medioDePagoDTO.getIdMedioDePago());
            devolucionAporteDTO.setSedeCCF(medioDePagoDTO.getSede());
        }

        if (devolucionDTO.getSolicitud().getMontoReclamado() != null) {
            devolucionAporteDTO.setMontoAportes(new BigDecimal(devolucionDTO.getSolicitud().getMontoReclamado()));
        }

        if (devolucionDTO.getSolicitud().getMontoIntereses() != null) {
            devolucionAporteDTO.setMontoIntereses(new BigDecimal(devolucionDTO.getSolicitud().getMontoIntereses()));
        }

        devolucionAporteDTO.setMotivoPeticion(devolucionDTO.getSolicitud().getMotivoPeticion());
        devolucionAporteDTO.setOtroDestinatario(devolucionDTO.getSolicitud().getOtro());
        devolucionAporteDTO
                .setPeriodoReclamado(StringUtils.join(devolucionDTO.getSolicitud().getPeriodosReclamados(), "|"));
        devolucionAporteDTO.setOtraCaja(devolucionDTO.getSolicitud().getOtraCaja());
        devolucionAporteDTO.setOtroMotivo(devolucionDTO.getSolicitud().getOtroMotivo());
        logger.info("Finaliza método transformarDevolucionAporte");
        return devolucionAporteDTO;
    }

    /**
     * Método que invoca el servicio de almacenamiento de medios de pago
     * 
     * @param medioDePagoModeloDTO
     *                             Información del medio de pago a almacenar
     * @return La información del medio de pago, actualizada
     */
    private MedioDePagoModeloDTO guardarMedioDePago(MedioDePagoModeloDTO medioDePagoModeloDTO) {
        logger.info("Inicio de método guardarMedioDePago");
        GuardarMedioDePago service = new GuardarMedioDePago(medioDePagoModeloDTO);
        service.execute();
        logger.info("Finaliza método guardarMedioDePago");
        return service.getResult();
    }

     /**
	 * (non-Javadoc)
	 *  
	 * @see com.asopagos.aportes.service.AportesService#consultarRecaudo(java.lang.Long)
	 */
    
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AnalisisDevolucionDTO> consultarRecaudosMasivos(ConsultarRecaudoDTO consultaRecaudo,
			TipoMovimientoRecaudoAporteEnum tipo, Boolean hayParametros, Boolean vista360) {
        
		logger.info("getIdAporteGeneral"+consultaRecaudo.getIdAporteGeneral());
		logger.info("getTipoIdentificacion"+consultaRecaudo.getTipoIdentificacion());
		logger.info("tipo"+tipo);
		logger.debug("Inicia método consultarRecaudo(Long, ConsultarRecaudoDTO)");
		if (consultaRecaudo.getNumeroIdentificacion() == null && consultaRecaudo.getTipoIdentificacion() == null
				&& consultaRecaudo.getTipoSolicitante() == null) {
			logger.debug("Finaliza método consultarRecaudo(Long, ConsultarRecaudoDTO):Parámetros Incompletos");
			throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
		} else {
			PersonaDTO persona = consultasCore.consultarPersonaTipoNumeroIdentificacion(
					consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion());
					logger.info("getNumeroIdentificacion"+persona.getNumeroIdentificacion());

			if (persona != null) {
				if (persona.getRazonSocial() == null || persona.getRazonSocial().isEmpty()) {
					StringBuilder nombreAportante = new StringBuilder();
					nombreAportante.append(persona.getPrimerNombre() + " ");
					nombreAportante.append(persona.getSegundoNombre() != null ? persona.getSegundoNombre() + " " : "");
					nombreAportante.append(persona.getPrimerApellido() + " ");
					nombreAportante.append(persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
					consultaRecaudo.setNombreCompleto(nombreAportante.toString());

				} else {
					consultaRecaudo.setNombreCompleto(persona.getRazonSocial());
				}
			}

			CriteriaBuilder cb = consultasCore.obtenerCriteriaBuilder();
			CriteriaQuery<AporteGeneral> c = cb.createQuery(AporteGeneral.class);
			Root<AporteGeneral> aporteGeneral = c.from(AporteGeneral.class);
			c.select(aporteGeneral);
			List<AporteGeneral> aportesGenerales = null;
			EmpresaDTO empresa = null;
			List<Predicate> predicates = new ArrayList<Predicate>();
			List<Long> idsAportesCorDev = new ArrayList<>();
			List<AnalisisDevolucionDTO> analisisDevolucion = new ArrayList<>();

			if (persona.getIdPersona() != null) {
				empresa = consultasCore.consultarEmpresa(persona.getIdPersona());
			}
					predicates.add(cb.or(cb.equal(aporteGeneral.get("idEmpresa"), empresa.getIdEmpresa())));
				

				if (consultaRecaudo.getTipoSolicitante() != null && !TipoSolicitanteMovimientoAporteEnum.EMPLEADOR
						.equals(consultaRecaudo.getTipoSolicitante())) {;
					predicates
							.add(cb.equal(aporteGeneral.get("tipoSolicitante"), consultaRecaudo.getTipoSolicitante()));
				}
				if (consultaRecaudo.getIdRegistroGeneral() != null) {
					predicates.add(
							cb.equal(aporteGeneral.get("idRegistroGeneral"), consultaRecaudo.getIdRegistroGeneral()));
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				if (vista360 == null || (vista360 != null && !vista360)) {
					if (consultaRecaudo.getMetodoRecaudo() != null && consultaRecaudo.getPeriodoRecaudo() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
						predicates.add(cb.equal(aporteGeneral.get("modalidadRecaudoAporte"),
								consultaRecaudo.getMetodoRecaudo()));
					} else {
						if (consultaRecaudo.getPeriodoRecaudo() != null) {
							String fechaPagoString = null;
							fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
							predicates.add(cb.equal(aporteGeneral.get("periodoAporte"), fechaPagoString));
						}
					}
				}

				if (hayParametros != null && hayParametros) {
					if (consultaRecaudo.getListMetodoRecaudo() != null
							&& !consultaRecaudo.getListMetodoRecaudo().isEmpty()) {
						predicates.add(
								aporteGeneral.get("modalidadRecaudoAporte").in(consultaRecaudo.getListMetodoRecaudo()));
					}

					//
					if (TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.equals(tipo)
							&& consultaRecaudo.getPeriodoRecaudo() != null && consultaRecaudo.getPeriodoFin() != null) {
						String periodoInicialFormat = null;
						String periodoFinalFormat = null;
						periodoInicialFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						periodoFinalFormat = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
						predicates.add(cb.between(aporteGeneral.get("periodoAporte"), periodoInicialFormat,
								periodoFinalFormat));
					} else if (consultaRecaudo.getPeriodoRecaudo() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoRecaudo()));
						predicates.add(cb.greaterThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
					} else if (consultaRecaudo.getPeriodoFin() != null) {
						String fechaPagoString = null;
						fechaPagoString = dateFormat.format(new Date(consultaRecaudo.getPeriodoFin()));
						predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("periodoAporte"), fechaPagoString));
					}

                    if(consultaRecaudo.getPeriodoRecaudo() == null){
                        EstadoRegistroAporteEnum estadoRegistroAporteAportante = EstadoRegistroAporteEnum.RELACIONADO;
                        predicates.add(cb.equal(aporteGeneral.get("estadoRegistroAporteAportante"), estadoRegistroAporteAportante));
                    }

					if (consultaRecaudo.getFechaInicio() != null && consultaRecaudo.getFechaFin() != null) {
						Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
						Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
						predicates.add(
								cb.between(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate, fechaFinDate));
					} else if (consultaRecaudo.getFechaInicio() != null) {
						Date fechaInicioDate = new Date(consultaRecaudo.getFechaInicio());
						predicates
								.add(cb.greaterThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaInicioDate));
					} else if (consultaRecaudo.getFechaFin() != null) {
						Date fechaFinDate = new Date(consultaRecaudo.getFechaFin());
						predicates.add(cb.lessThanOrEqualTo(aporteGeneral.get("fechaProcesamiento"), fechaFinDate));
					}
				}

				if (consultaRecaudo.getIdAporteGeneral() != null) {
					predicates.add(cb.equal(aporteGeneral.get("id"), consultaRecaudo.getIdAporteGeneral()));
				}

			


		

			c.select(aporteGeneral).where(predicates.toArray(new Predicate[] {}));
			aportesGenerales = consultasCore.obtenerListaAportes(c);

			//logger.info("aportesGenerales_1... " + aportesGenerales.toString());

			// se aplica el criterio de número de planilla (sí aplica)
			aportesGenerales = aplicarCriterioNumeroPlanilla(aportesGenerales, consultaRecaudo.getNumeroPlanilla());

			if (aportesGenerales != null && !aportesGenerales.isEmpty() && hayParametros != null && !hayParametros
					&& vista360 != null && vista360) {
				aportesGenerales.sort(Comparator.comparing(AporteGeneral::getFechaProcesamiento)
						.thenComparing(AporteGeneral::getPeriodoAporte).reversed());
				if (aportesGenerales.size() > 24) {
					aportesGenerales = aportesGenerales.subList(0, 24);
				}
			}

			if (aportesGenerales.size() > 0) {

			List<Long> idsAporte = new ArrayList<>();
			for (AporteGeneral aporte : aportesGenerales) {
				idsAporte.add(aporte.getId());
			}
			List<AporteGeneralModeloDTO> aportesGeneralesDTO = new ArrayList<>();
			if (!idsAporte.isEmpty()) {
				aportesGeneralesDTO = consultasCore.consultarAporteYMovimiento(idsAporte);
				
				// luego de obtener los aportes, buscar las personas
				// tramitadoras
				agregarTramitadores(consultaRecaudo, aportesGeneralesDTO);
			}
			analisisDevolucion = obtenerAnalisisDevolucionDTO(aportesGeneralesDTO, consultaRecaudo);
			
			
			//logger.info("NumAportes: " + aportesGenerales.size());
			//logger.info("getIdRegistroGeneral: " + aportesGenerales.get(0).getIdRegistroGeneral());
			
			
			CuentaAporteDTO infoAporte = consultasStaging.consultarDatosPlanillaAporte(aportesGenerales.get(0).getIdRegistroGeneral());
			
			//logger.info("getRegistroControl: " + infoAporte.getRegistroControl());
            //logger.info("EstadoProcesoArchivoEnum: " + infoAporte.getEstadoArchivo());
			
			if(aportesGeneralesDTO == null) {
			    //logger.info("caso 1: ");
				analisisDevolucion.get(0).setEnProcesamiento(true);
				List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(infoAporte.getRegistroControl());
				if (pet.isEmpty()) {
					analisisDevolucion.get(0).setEnBandejaTransitoria(false);
				} else {
					analisisDevolucion.get(0).setEnBandejaTransitoria(true);
				}
			} else {
			    //logger.info("caso2: ");
				List<PilaEstadoTransitorio> pet = consultasCore.consultarEstadoProcesamientoPlanilla(infoAporte.getRegistroControl());
				// si está vacío puede ser por:
				// caso 1: planillas cargadas antes del ajuste de pila fase 3
				// caso 2: aún no se copia el aportes en AporteGeneral, como tal en la vista 360 no lo mostraría (no se tiene en cuenta) 
				if (pet.isEmpty()) {
				    //logger.info("caso3: ");
					analisisDevolucion.get(0).setEnBandejaTransitoria(false);
					if (analisisDevolucion.get(0) != null && 
							(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO.equals(infoAporte.getEstadoArchivo()))){
						analisisDevolucion.get(0).setEnProcesamiento(false);
					} else {
						analisisDevolucion.get(0).setEnProcesamiento(true);
					}
				} else {
				    //logger.info("caso4: ");
					analisisDevolucion.get(0).setEnProcesamiento(true);
					analisisDevolucion.get(0).setEnBandejaTransitoria(true);
					
					for (PilaEstadoTransitorio p : pet) {
						if (PilaEstadoTransitorioEnum.EXITOSO.equals(p.getEstado())
								&& PilaAccionTransitorioEnum.NOTIFICAR_PLANILLA.equals(p.getAccion())) {
							analisisDevolucion.get(0).setEnProcesamiento(false);
							analisisDevolucion.get(0).setEnBandejaTransitoria(false);
						}
					}
				}
			}
		}

			logger.debug("Finaliza método consultarRecaudo(Long, ConsultarRecaudoDTO)");
			return analisisDevolucion;
		}

	}

/**
	 * Método encargado de la consulta de datos de tramitadores de aportes
	 * 
	 * @param consultaRecaudo
	 * @param aportesGenerales
	 */
	private void agregarTramitadores(ConsultarRecaudoDTO consultaRecaudo,
			List<AporteGeneralModeloDTO> aportesGenerales) {
		String firmaMetodo = "agregarTramitadores(ConsultarRecaudoDTO, List<AporteGeneral>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		ConsultarRecaudoDTO consultaRecaudoTemp = consultaRecaudo;

		// se listan los ID de empresa tramitadora de los aportes
		List<Long> idsEmpresaTramitadora = new ArrayList<>();
		for (AporteGeneralModeloDTO aporte : aportesGenerales) {
			if (aporte.getEmpresaTramitadoraAporte() != null
					&& !idsEmpresaTramitadora.contains(aporte.getEmpresaTramitadoraAporte())) {
				idsEmpresaTramitadora.add(aporte.getEmpresaTramitadoraAporte());
			}
		}

		// se consultan las empresas
		Map<Long, EmpresaModeloDTO> mapaEmpresasTramitadoras = new HashMap<>();
		if (idsEmpresaTramitadora != null && !idsEmpresaTramitadora.isEmpty()) {
			List<EmpresaModeloDTO> empresasTramitadoras = consultasCore.consultarEmpresasPorIds(idsEmpresaTramitadora);

			// se organiza el mapa de empresas por aporte
			for (AporteGeneralModeloDTO aporte : aportesGenerales) {
				if (aporte.getEmpresaTramitadoraAporte() != null) {
					for (EmpresaModeloDTO tramitador : empresasTramitadoras) {
						if (tramitador.getIdEmpresa().equals(aporte.getEmpresaTramitadoraAporte())) {
							mapaEmpresasTramitadoras.put(aporte.getId(), tramitador);
							break;
						}
					}
				}
			}
		}

		consultaRecaudoTemp.setEmpresasTramitadoras(mapaEmpresasTramitadoras);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}        
        
        /**
	 * Método privado que retorna una lista de AnalisisDevolucionDTO
	 * 
	 * @param aportesGenerales
	 * @param consultaRecaudo
	 * @return
	 */
	private List<AnalisisDevolucionDTO> obtenerAnalisisDevolucionDTO(List<AporteGeneralModeloDTO> aportesGenerales,
			ConsultarRecaudoDTO consultaRecaudo) {
		String firmaMetodo = "AportesBusiness.obtenerAnalisisDevolucionDTO(List<AporteGeneralModeloDTO>, ConsultarRecaudoDTO)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		List<AnalisisDevolucionDTO> analisisDevolucion = new ArrayList<>();

		DatosAnalisisDevolucionDTO datosBD = new DatosAnalisisDevolucionDTO();

		// listado de IDs de registros generales, aportes y DTOs para la
		// consulta de subsidio
		List<Long> idsRegistrosGeneral = new ArrayList<>();
		List<Long> idsAporteDevolucion = new ArrayList<>();
		List<Long> idsEmpresas = new ArrayList<>();
		List<Long> idsPersonas = new ArrayList<>();
		List<DatosConsultaSubsidioPagadoDTO> datosConsultaSubsidios = new ArrayList<>();

		// se recopilan los ID de registros generales y de aportes
		for (AporteGeneralModeloDTO aporte : aportesGenerales) {
			if (!idsRegistrosGeneral.contains(aporte.getIdRegistroGeneral())) {
				idsRegistrosGeneral.add(aporte.getIdRegistroGeneral());
			}

			if (!idsAporteDevolucion.contains(aporte.getId())) {
				idsAporteDevolucion.add(aporte.getId());
			}

			if (aporte.getIdEmpresa() != null && !idsEmpresas.contains(aporte.getIdEmpresa())) {
				idsEmpresas.add(aporte.getIdEmpresa());
			}

			if (aporte.getIdPersona() != null && !idsPersonas.contains(aporte.getIdPersona())) {
				idsPersonas.add(aporte.getIdPersona());
			}

			datosConsultaSubsidios.add(new DatosConsultaSubsidioPagadoDTO(consultaRecaudo.getTipoIdentificacion(),
					consultaRecaudo.getNumeroIdentificacion(), aporte.getPeriodoAporte()));
		}
		datosBD.setIdsRegistrosGeneral(idsRegistrosGeneral);
		datosBD.setIdsAporteDevolucion(idsAporteDevolucion);
		datosBD.setIdsEmpresas(idsEmpresas);
		datosBD.setIdsPersonas(idsPersonas);
		datosBD.setDatosCotizantes(datosConsultaSubsidios);

		datosBD = consultaPreviaAnalisisDevolucion(datosBD);

		Long periodo = null;
		String nombreCompletoAportante = null;
		TipoIdentificacionEnum tipoIdentificacionAportante = null;
		String numeroIdentificacionAportante = null;
		TipoSolicitanteMovimientoAporteEnum tipoSolicitante = null;

		for (AporteGeneralModeloDTO aportes : aportesGenerales) {
			nombreCompletoAportante = consultaRecaudo.getNombreCompleto();
			tipoIdentificacionAportante = consultaRecaudo.getTipoIdentificacion();
			numeroIdentificacionAportante = consultaRecaudo.getNumeroIdentificacion();
			tipoSolicitante = consultaRecaudo.getTipoSolicitante();

			try {
				periodo = dateFormat.parse(aportes.getPeriodoAporte()).getTime();
			} catch (ParseException e) {
				logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
				throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
			}

			AnalisisDevolucionDTO analisisDevolucionDTO = new AnalisisDevolucionDTO();
			analisisDevolucionDTO.setEstadoAporte(aportes.getEstadoAporteAportante());
			analisisDevolucionDTO.setResultado(EstadoGestionAporteEnum.PENDIENTE);
			RegistroGeneralModeloDTO registroGeneral = datosBD.getRegistrosGenerales()
					.get(aportes.getIdRegistroGeneral());

			DatosConsultaSubsidioPagadoDTO datoCotizante = ubicarPagoSubsidioCotizante(
					consultaRecaudo.getTipoIdentificacion(), consultaRecaudo.getNumeroIdentificacion(),
					aportes.getPeriodoAporte(), datosBD.getDatosCotizantes());

			if (consultaRecaudo.getNumeroPlanilla() != null && !consultaRecaudo.getNumeroPlanilla().isEmpty()
					&& registroGeneral != null) {
				if (consultaRecaudo.getNumeroPlanilla().equals(registroGeneral.getNumPlanilla())) {
					if (aportes.getFechaProcesamiento() != null) {
						analisisDevolucionDTO.setFecha(aportes.getFechaProcesamiento());
					}
					if (aportes.getFechaRecaudo() != null) {
                        analisisDevolucionDTO.setFechaPago(aportes.getFechaRecaudo());
                    }
					if (aportes.getCuentaBancariaRecaudoTexto() != null) {
						analisisDevolucionDTO.setCuentaBancariaRecaudo(aportes.getCuentaBancariaRecaudo());
						analisisDevolucionDTO.setCuentaBancariaRecaudoTexto(aportes.getCuentaBancariaRecaudoTexto());
					}
					analisisDevolucionDTO.setIdAporte(aportes.getId());
					analisisDevolucionDTO.setMetodo(aportes.getModalidadRecaudoAporte());
					analisisDevolucionDTO.setConDetalle(aportes.getAporteConDetalle());
					analisisDevolucionDTO.setPeriodo(periodo);
					analisisDevolucionDTO.setMonto(aportes.getValorTotalAporteObligatorio());
					analisisDevolucionDTO.setIdRegistroGeneral(registroGeneral.getId());
					analisisDevolucionDTO.setInteres(aportes.getValorInteresesMora());
					analisisDevolucionDTO.setNumPlanilla(registroGeneral.getNumPlanilla());
					analisisDevolucionDTO.setEstadoArchivo(registroGeneral.getOutEstadoArchivo());
					if (registroGeneral.getTipoPlanilla() != null) {
						analisisDevolucionDTO.setTipoPlanilla(
								TipoPlanillaEnum.obtenerTipoPlanilla(registroGeneral.getTipoPlanilla()));
					}
					if (aportes.getId() != null) {
						analisisDevolucionDTO.setNumOperacion(aportes.getId().toString());
					}

					if (registroGeneral.getRegistroControl() != null
							&& datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl()) != null) {
						analisisDevolucionDTO.setIdEcmArchivo(
								datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[0]);
						analisisDevolucionDTO.setTipoArchivo(TipoArchivoPilaEnum.valueOf(
								datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[1]));
					}
					BigDecimal monto = (aportes.getValorTotalAporteObligatorio() != null
							? aportes.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
					BigDecimal interes = (aportes.getValorInteresesMora() != null ? aportes.getValorInteresesMora()
							: BigDecimal.ZERO);
					analisisDevolucionDTO.setTotal(monto.add(interes));
					analisisDevolucionDTO.setIdPersona(aportes.getIdPersona());
					analisisDevolucionDTO.setIdEmpresa(aportes.getIdEmpresa());
					analisisDevolucionDTO.setTieneModificaciones(aportes.getTieneModificaciones());

					// Histórico evaluación de aportes
					SolicitudAporteModeloDTO solicitudAporteDTO = datosBD.getSolicitudesDevolucion()
							.get(registroGeneral.getOutRegInicial() != null ? registroGeneral.getOutRegInicial()
									: registroGeneral.getId());

					HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporteDetalle(
							aportes.getEstadoAporteAportante(), analisisDevolucionDTO.getMetodo(),
							analisisDevolucionDTO.getEstadoArchivo(), analisisDevolucionDTO.getTieneModificaciones(),
							solicitudAporteDTO != null ? solicitudAporteDTO.getEstadoSolicitud() : null,
							datoCotizante.getTipoIdentificacion(), datoCotizante.getNumeroIdentificacion(),
							datoCotizante.getPeriodo(), datoCotizante.getTieneSubsidio());

					analisisDevolucionDTO.setHistorico(historicoDTO);
					/* Se agrega pagos de terceros para vista 360 */
					if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportes.getTipoSolicitante())) {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

						EmpresaModeloDTO empresa = datosBD.getEmpresasAportantes().get(aportes.getIdEmpresa());
						if (empresa != null) {
							analisisDevolucionDTO.setTipoIdentificacionTramitador(empresa.getTipoIdentificacion());
							analisisDevolucionDTO.setNumeroIdentificacionTramitador(empresa.getNumeroIdentificacion());

							if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isEmpty()) {
								StringBuilder nombreAportante = new StringBuilder();
								nombreAportante.append(empresa.getPrimerNombre() + " ");
								nombreAportante.append(
										empresa.getSegundoNombre() != null ? empresa.getSegundoNombre() + " " : "");
								nombreAportante.append(empresa.getPrimerApellido() + " ");
								nombreAportante.append(
										empresa.getSegundoApellido() != null ? empresa.getSegundoApellido() : "");
								analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

							} else {
								analisisDevolucionDTO.setNombreCompletoTramitador(empresa.getRazonSocial());
							}
						}
					} else if (aportes.getEmpresaTramitadoraAporte() != null) {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

						EmpresaModeloDTO tramitador = consultaRecaudo.getEmpresasTramitadoras().get(aportes.getId());

						if (tramitador != null) {
							analisisDevolucionDTO.setTipoIdentificacionTramitador(tramitador.getTipoIdentificacion());
							analisisDevolucionDTO
									.setNumeroIdentificacionTramitador(tramitador.getNumeroIdentificacion());

							if (tramitador.getRazonSocial() == null || tramitador.getRazonSocial().isEmpty()) {
								StringBuilder nombreAportante = new StringBuilder();
								nombreAportante.append(tramitador.getPrimerNombre() + " ");
								nombreAportante.append(tramitador.getSegundoNombre() != null
										? tramitador.getSegundoNombre() + " " : "");
								nombreAportante.append(tramitador.getPrimerApellido() + " ");
								nombreAportante.append(
										tramitador.getSegundoApellido() != null ? tramitador.getSegundoApellido() : "");
								analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

							} else {
								analisisDevolucionDTO.setNombreCompletoTramitador(tramitador.getRazonSocial());
							}
						}

						PersonaModeloDTO personaAportante = datosBD.getPersonasAportantes().get(aportes.getIdPersona());
						if (personaAportante != null) {
							tipoIdentificacionAportante = personaAportante.getTipoIdentificacion();
							numeroIdentificacionAportante = personaAportante.getNumeroIdentificacion();
							nombreCompletoAportante = personaAportante.getRazonSocial();
						}

						tipoSolicitante = aportes.getTipoSolicitante();
					} else {
						analisisDevolucionDTO.setPagadorPorTerceros(Boolean.FALSE);
					}

					analisisDevolucionDTO.setNombreCompleto(nombreCompletoAportante);
					analisisDevolucionDTO.setTipoIdentificacion(tipoIdentificacionAportante);
					analisisDevolucionDTO.setNumeroIdentificacion(numeroIdentificacionAportante);

					analisisDevolucionDTO.setTipoSolicitante(tipoSolicitante);
					analisisDevolucionDTO.setCodigoEntidadFinanciera(
							aportes.getCodigoEntidadFinanciera() != null ? aportes.getCodigoEntidadFinanciera() : null);
					analisisDevolucion.add(analisisDevolucionDTO);
				}
			} else if (registroGeneral != null) {
				if (aportes.getFechaProcesamiento() != null) {
					analisisDevolucionDTO.setIdAporte(aportes.getId());
					analisisDevolucionDTO.setFecha(aportes.getFechaProcesamiento());
				}
				if (aportes.getFechaRecaudo() != null) {
                    analisisDevolucionDTO.setFechaPago(aportes.getFechaRecaudo());
                }
				analisisDevolucionDTO.setIdAporte(aportes.getId());
				analisisDevolucionDTO.setMetodo(aportes.getModalidadRecaudoAporte());
				analisisDevolucionDTO.setConDetalle(aportes.getAporteConDetalle());
				analisisDevolucionDTO.setPeriodo(periodo);
				analisisDevolucionDTO.setMonto(aportes.getValorTotalAporteObligatorio());
				analisisDevolucionDTO.setInteres(aportes.getValorInteresesMora());
				if (aportes.getCuentaBancariaRecaudo() != null) {
                analisisDevolucionDTO.setCuentaBancariaRecaudo(aportes.getCuentaBancariaRecaudo());
                analisisDevolucionDTO.setCuentaBancariaRecaudoTexto(aportes.getCuentaBancariaRecaudoTexto());																				 
				}

				analisisDevolucionDTO.setIdRegistroGeneral(registroGeneral.getId());
				analisisDevolucionDTO.setNumPlanilla(registroGeneral.getNumPlanilla());
				analisisDevolucionDTO.setEstadoArchivo(registroGeneral.getOutEstadoArchivo());
				if (registroGeneral.getTipoPlanilla() != null) {
					analisisDevolucionDTO
							.setTipoPlanilla(TipoPlanillaEnum.obtenerTipoPlanilla(registroGeneral.getTipoPlanilla()));
				}

				if (aportes.getId() != null) {
					analisisDevolucionDTO.setNumOperacion(aportes.getId().toString());
				}

				if (registroGeneral.getRegistroControl() != null
						&& datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl()) != null) {
					analisisDevolucionDTO.setIdEcmArchivo(
							datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[0]);
					analisisDevolucionDTO.setTipoArchivo(TipoArchivoPilaEnum
							.valueOf(datosBD.getIdsDocumentosPlanilla().get(registroGeneral.getRegistroControl())[1]));
				}

				BigDecimal monto = (aportes.getValorTotalAporteObligatorio() != null
						? aportes.getValorTotalAporteObligatorio() : BigDecimal.ZERO);
				BigDecimal interes = (aportes.getValorInteresesMora() != null ? aportes.getValorInteresesMora()
						: BigDecimal.ZERO);
				analisisDevolucionDTO.setTotal(monto.add(interes));
				analisisDevolucionDTO.setIdPersona(aportes.getIdPersona());
				analisisDevolucionDTO.setIdEmpresa(aportes.getIdEmpresa());
				analisisDevolucionDTO.setTieneModificaciones(aportes.getTieneModificaciones());

				// Histórico evaluación de aportes
				SolicitudAporteModeloDTO solicitudAporteDTO = datosBD.getSolicitudesDevolucion()
						.get(registroGeneral.getOutRegInicial() != null ? registroGeneral.getOutRegInicial()
								: registroGeneral.getId());

				HistoricoDTO historicoDTO = consultarHistoricoEvaluacionAporteDetalle(
						aportes.getEstadoAporteAportante(), analisisDevolucionDTO.getMetodo(),
						analisisDevolucionDTO.getEstadoArchivo(), analisisDevolucionDTO.getTieneModificaciones(),
						solicitudAporteDTO != null ? solicitudAporteDTO.getEstadoSolicitud() : null,
						datoCotizante.getTipoIdentificacion(), datoCotizante.getNumeroIdentificacion(),
						datoCotizante.getPeriodo(), datoCotizante.getTieneSubsidio());
				analisisDevolucionDTO.setHistorico(historicoDTO);

				/* Se agrega pagos de terceros para vista 360 */
				if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aportes.getTipoSolicitante())) {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

					EmpresaModeloDTO empresa = datosBD.getEmpresasAportantes().get(aportes.getIdEmpresa());
					if (empresa != null) {
						analisisDevolucionDTO.setTipoIdentificacionTramitador(empresa.getTipoIdentificacion());
						analisisDevolucionDTO.setNumeroIdentificacionTramitador(empresa.getNumeroIdentificacion());

						if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isEmpty()) {
							StringBuilder nombreAportante = new StringBuilder();
							nombreAportante.append(empresa.getPrimerNombre() + " ");
							nombreAportante
									.append(empresa.getSegundoNombre() != null ? empresa.getSegundoNombre() + " " : "");
							nombreAportante.append(empresa.getPrimerApellido() + " ");
							nombreAportante
									.append(empresa.getSegundoApellido() != null ? empresa.getSegundoApellido() : "");
							analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

						} else {
							analisisDevolucionDTO.setNombreCompletoTramitador(empresa.getRazonSocial());
						}
					}
				} else if (aportes.getEmpresaTramitadoraAporte() != null) {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.TRUE);

					EmpresaModeloDTO tramitador = consultaRecaudo.getEmpresasTramitadoras().get(aportes.getId());

					if (tramitador != null) {
						analisisDevolucionDTO.setTipoIdentificacionTramitador(tramitador.getTipoIdentificacion());
						analisisDevolucionDTO.setNumeroIdentificacionTramitador(tramitador.getNumeroIdentificacion());

						if (tramitador.getRazonSocial() == null || tramitador.getRazonSocial().isEmpty()) {
							StringBuilder nombreAportante = new StringBuilder();
							nombreAportante.append(tramitador.getPrimerNombre() + " ");
							nombreAportante.append(
									tramitador.getSegundoNombre() != null ? tramitador.getSegundoNombre() + " " : "");
							nombreAportante.append(tramitador.getPrimerApellido() + " ");
							nombreAportante.append(
									tramitador.getSegundoApellido() != null ? tramitador.getSegundoApellido() : "");
							analisisDevolucionDTO.setNombreCompletoTramitador(nombreAportante.toString());

						} else {
							analisisDevolucionDTO.setNombreCompletoTramitador(tramitador.getRazonSocial());
						}
					}

					PersonaModeloDTO personaAportante = datosBD.getPersonasAportantes().get(aportes.getIdPersona());
					if (personaAportante != null) {
						tipoIdentificacionAportante = personaAportante.getTipoIdentificacion();
						numeroIdentificacionAportante = personaAportante.getNumeroIdentificacion();
						nombreCompletoAportante = personaAportante.getRazonSocial();
					}

					tipoSolicitante = aportes.getTipoSolicitante();
				} else {
					analisisDevolucionDTO.setPagadorPorTerceros(Boolean.FALSE);
				}

				analisisDevolucionDTO.setNombreCompleto(nombreCompletoAportante);
				analisisDevolucionDTO.setTipoIdentificacion(tipoIdentificacionAportante);
				analisisDevolucionDTO.setNumeroIdentificacion(numeroIdentificacionAportante);

				analisisDevolucionDTO.setTipoSolicitante(tipoSolicitante);
				analisisDevolucionDTO.setCodigoEntidadFinanciera(
						aportes.getCodigoEntidadFinanciera() != null ? aportes.getCodigoEntidadFinanciera() : null);
				analisisDevolucion.add(analisisDevolucionDTO);
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return analisisDevolucion;

	}
        
        /**
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param periodoAporte
	 * @param datosCotizante
	 * @return
	 */
	private DatosConsultaSubsidioPagadoDTO ubicarPagoSubsidioCotizante(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion, String periodoAporte, List<DatosConsultaSubsidioPagadoDTO> datosCotizante) {
		String firmaMetodo = "AportesBusiness.consultaPreviaAnalisisDevolucion(TipoIdentificacionEnum, String, String, "
				+ "List<DatosConsultaSubsidioPagadoDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosConsultaSubsidioPagadoDTO result = null;

		for (DatosConsultaSubsidioPagadoDTO cotizante : datosCotizante) {
			if (cotizante.getTipoIdentificacion().equals(tipoIdentificacion)
					&& cotizante.getNumeroIdentificacion().equalsIgnoreCase(numeroIdentificacion)
					&& cotizante.getPeriodo().equals(periodoAporte)) {
				result = cotizante;
				break;
			}
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

        
        /**
	 * @param aportesGenerales
	 * @param numeroPlanilla
	 * @return
	 */
	private List<AporteGeneral> aplicarCriterioNumeroPlanilla(List<AporteGeneral> aportesGenerales,
			String numeroPlanilla) {
		String firmaMetodo = "AportesBusiness.aplicarCriterioNumeroPlanilla(List<AporteGeneral>, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		List<AporteGeneral> result = new ArrayList<>();

		if (numeroPlanilla != null && !numeroPlanilla.isEmpty()) {
			Map<Long, AporteGeneral> mapaAportes = new HashMap<>();
			List<Long> idsRegistroGeneral = new ArrayList<>();

			// en primer lugar, los aportes manuales se descartan del resultado
			// y se listan los ID de registro general en los aportes
			for (AporteGeneral aporteGeneral : aportesGenerales) {
				if (!ModalidadRecaudoAporteEnum.MANUAL.equals(aporteGeneral.getModalidadRecaudoAporte())) {
					mapaAportes.put(aporteGeneral.getIdRegistroGeneral(), aporteGeneral);
					idsRegistroGeneral.add(aporteGeneral.getIdRegistroGeneral());
				}
			}

			if (!idsRegistroGeneral.isEmpty()) {
				// se consultan los registros generales
				List<RegistroGeneralModeloDTO> registrosGenerales = new ArrayList<>(
						consultasStaging.consultarRegistrosGeneralesPorListaId(idsRegistroGeneral).values());

				for (RegistroGeneralModeloDTO registrogeneral : registrosGenerales) {
					// se compara el # de planilla para agregar al resultado
					if (numeroPlanilla.equals(registrogeneral.getNumPlanilla())) {
						result.add(mapaAportes.get(registrogeneral.getId()));
					}
				}
			} else {
				result = aportesGenerales;
			}
		} else {
			result = aportesGenerales;
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
        
        /**
	 * @param idsRegistrosGeneral
	 * @return
	 */
	private DatosAnalisisDevolucionDTO consultaPreviaAnalisisDevolucion(DatosAnalisisDevolucionDTO datosBase) {
		String firmaMetodo = "AportesBusiness.consultaPreviaAnalisisDevolucion()";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosAnalisisDevolucionDTO result = datosBase;

		// se consultan los registros generales y las solicitudes de devolución
		// asociadas
		if (datosBase.getIdsRegistrosGeneral() != null && !datosBase.getIdsRegistrosGeneral().isEmpty()) {
			result.setRegistrosGenerales(
					consultasStaging.consultarRegistrosGeneralesPorListaId(datosBase.getIdsRegistrosGeneral()));
			/*
			 * en el caso de que los aportes presenten registros generales
			 * modificados por motivo de movimiento en aporte sin detalle, los
			 * ids se agregan al listado de registros para la consulta de
			 * solicitudes
			 */
			for (RegistroGeneralModeloDTO registro : result.getRegistrosGenerales().values()) {
				if (registro.getOutRegInicial() != null
						&& !datosBase.getIdsRegistrosGeneral().contains(registro.getOutRegInicial())) {
					datosBase.getIdsRegistrosGeneral().add(registro.getOutRegInicial());
				}
			}

			result.setSolicitudesDevolucion(
					consultasCore.consultarSolicitudesDevolucionListaIds(datosBase.getIdsRegistrosGeneral()));
		}

		/*
		 * con los registros generales, se consultan los Índices de planilla que
		 * aplique para obtener los identificadores de documento de los archivos
		 * de PILA
		 */
		if (!result.getIdsRegistrosGeneral().isEmpty()) {
			List<Long> idsPlanilla = new ArrayList<>();
			for (RegistroGeneralModeloDTO registroGeneral : result.getRegistrosGenerales().values()) {
				if (registroGeneral.getRegistroControl() != null
						&& !idsPlanilla.contains(registroGeneral.getRegistroControl())) {
					idsPlanilla.add(registroGeneral.getRegistroControl());
				}
			}

			if (idsPlanilla != null && !idsPlanilla.isEmpty()) {
				List<IndicePlanillaModeloDTO> indices = consultasPila.consultarIndicesPlanillaPorIds(idsPlanilla);
				for (IndicePlanillaModeloDTO indice : indices) {
					String[] documento = { indice.getIdDocumento(), indice.getTipoArchivo().name() };
					result.getIdsDocumentosPlanilla().put(indice.getId(), documento);
				}
			}
		}

		if (datosBase.getDatosCotizantes() != null && !datosBase.getDatosCotizantes().isEmpty()) {
			result.setDatosCotizantes(consultasCore.consultarPagoSubsidioCotizantes(datosBase.getDatosCotizantes()));
		}

		Map<Long, EmpresaModeloDTO> mapaEmpresas = new HashMap<>();
		if (datosBase.getIdsEmpresas() != null && !datosBase.getIdsEmpresas().isEmpty()) {
			List<EmpresaModeloDTO> empresas = consultasCore.consultarEmpresasPorIds(datosBase.getIdsEmpresas());
			for (EmpresaModeloDTO empresa : empresas) {
				if (!mapaEmpresas.containsKey(empresa.getIdEmpresa())) {
					mapaEmpresas.put(empresa.getIdEmpresa(), empresa);
				}
			}
		}
		result.setEmpresasAportantes(mapaEmpresas);

		if (datosBase.getIdsPersonas() != null && !datosBase.getIdsPersonas().isEmpty()) {
			datosBase.setPersonasAportantes(
					consultasCore.consultarPersonasPorListadoIds(datosBase.getIdsPersonas(), Boolean.FALSE));
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}
        
        /**
	 * Método que consulta la evaluación de condiciones por las cuales un aporte
	 * puede ser tenido o no en cuenta en una solicitud de devolución o
	 * corrección, de acuerdo a su registro histórico
	 * 
	 * @param estadoAporte
	 *            Estado del aporte
	 * @param modalidadRecaudo
	 *            Modalidad de recaudo (PILA manual, PILA automático o Aporte
	 *            Manual)
	 * @param estadoProcesoArchivo
	 *            Estado de la planilla con la que se realizó el aporte, si éste
	 *            se hizo por PILA
	 * @param tieneModificaciones
	 *            Indica si el archivo ha sido modificado. Aplica sólo para PILA
	 * @param estadoSolicitudAporte
	 *            Estado de la solicitud de aportes
	 * @param tipoIdentificacionCotizante
	 *            Tipo de identificación del cotizante
	 * @param numeroIdentificacionCotizante
	 *            Número de identificación del cotizante
	 * @param periodoAporte
	 *            Periodo del aporte a validar
	 * @param subsidioPagado
	 *            Indicador de pago de subsidio para el cotizante
	 * @return Información de la evaluación de condiciones de acuerdo al
	 *         registro histórico del aporte
	 */
	private HistoricoDTO consultarHistoricoEvaluacionAporteDetalle(EstadoAporteEnum estadoAporte,
			ModalidadRecaudoAporteEnum modalidadRecaudo, EstadoProcesoArchivoEnum estadoProcesoArchivo,
			Boolean tieneModificaciones, EstadoSolicitudAporteEnum estadoSolicitudAporte,
			TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String periodoAporte, Boolean subsidioPagado) {

		String firmaMetodo = "AportesBusiness.consultarHistoricoEvaluacionAporteDetalle(EstadoAporteEnum, ModalidadRecaudoAporteEnum, "
				+ "EstadoProcesoArchivoEnum, Boolean, EstadoSolicitudAporteEnum, TipoIdentificacionEnum, String, String, Boolean)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		HistoricoDTO historicoDTO = new HistoricoDTO();

		try {

			// ¿Archivo original ha sido reemplazado?
			historicoDTO.setArchivoRemplazado(Boolean.FALSE);

			if (modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA_MANUAL)
					|| modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA)) {
				historicoDTO.setArchivoRemplazado(tieneModificaciones);
			}

			// ¿Archivo/Recaudo manual ha finalizado?
			if (modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA_MANUAL)
					|| modalidadRecaudo.equals(ModalidadRecaudoAporteEnum.PILA)) {
				historicoDTO.setArchivoFinalizado(Boolean.FALSE);

				if (estadoProcesoArchivo != null) {
					if (estadoProcesoArchivo.equals(EstadoProcesoArchivoEnum.PROCESADO_NOVEDADES)
							|| estadoProcesoArchivo.equals(EstadoProcesoArchivoEnum.RECAUDO_NOTIFICADO)) {
						historicoDTO.setArchivoFinalizado(Boolean.TRUE);
					}
				}
			} else {
				historicoDTO.setArchivoFinalizado(Boolean.FALSE);

				if (estadoSolicitudAporte != null && estadoSolicitudAporte.equals(EstadoSolicitudAporteEnum.CERRADA)) {
					historicoDTO.setArchivoFinalizado(Boolean.TRUE);
				}
			}

			// ¿Aporte está vigente?
			historicoDTO.setAporteVigente(EstadoAporteEnum.VIGENTE.equals(estadoAporte));

			// ¿Se ha pagado subsidio monetario para el periodo?
			historicoDTO.setSeHaPagadoEnPeriodo(subsidioPagado != null ? subsidioPagado
					: consultasCore.cotizanteConSubsidioPeriodo(tipoIdentificacionCotizante,
							numeroIdentificacionCotizante, periodoAporte));
		} catch (Exception e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return historicoDTO;
	}

    public CorreccionAportanteDTO simularAporteCorreccionMasivo(Long idSolicitud, CorreccionAportanteDTO correccion) {
       
        CorreccionAportanteDTO correccionAporte = consultasCore.popularCorreccionAportante(correccion);
        correccionAporte = consultasPila.popularCorreccionAportante(correccionAporte);
        logger.info("Res correccion: " + ToStringBuilder.reflectionToString(correccionAporte.getCotizantesNuevos()));
        SimularAporteCorreccion simularAporteCorreccion = new SimularAporteCorreccion(idSolicitud, correccionAporte);
        simularAporteCorreccion.execute();
        CorreccionAportanteDTO resultadoSimulacion = simularAporteCorreccion.getResult();
        // Se actualiza los datos temporales de la correccion con el resultado del analisis
        logger.info("resultadoSimulacion: " + resultadoSimulacion.toString());
        ConsultarCorreccionTemporal servicio = new ConsultarCorreccionTemporal(idSolicitud);
        servicio.execute();
        CorreccionDTO datoTemporal = servicio.getResult();
        logger.info("datoTemporal: " + datoTemporal.toString());

        ArchivosAportesMasivosUtils utils = new ArchivosAportesMasivosUtils();
        GuardarCorreccionTemporal servicio2 = new GuardarCorreccionTemporal(
            idSolicitud,
            utils.guardarResultadoValidacionEnCorreccion(resultadoSimulacion, datoTemporal));
        
        servicio2.execute();
        

        SimularCorreccionTemporal simularCorreccionTemporal = new SimularCorreccionTemporal(idSolicitud);
        simularCorreccionTemporal.execute();
       
        return resultadoSimulacion;
        
    }

    public void actualizarSolicitudDevolucionMasiva(Solicitud solicitud){
        consultasCore.actualizarSolicitudDevolucionMasiva(solicitud);
    }
   
     @Override
     public void simularDevolucionMasiva(Solicitud solicitud){
        consultasPila.simularDevolucionMasivoGeneral(solicitud.getNumeroRadicacion());
        consultasPila.crearSolicitudCascadaDevolucion(solicitud.getNumeroRadicacion());
        actualizarSolicitudDevolucionMasiva(solicitud);
        
     }

     @Override
     public void finalizarDevolucionMasiva(MedioDePagoModeloDTO medioDePagoDTO,String numeroRadicado, Long idTarea, UserDTO userDTO){
        logger.info("Iniciar finalizarDevolucionMasiva "+medioDePagoDTO.toString());
        if(medioDePagoDTO.getTipoMedioDePago().equals(TipoMedioDePagoEnum.EFECTIVO)){
            medioDePagoDTO.setEfectivo(Boolean.TRUE);
        }
        MedioDePagoModeloDTO medioDePagoPersist = guardarMedioDePago(medioDePagoDTO);

        logger.info("numeroRadicacion "+numeroRadicado);
        logger.info("getIdMedioDePago "+medioDePagoPersist.getIdMedioDePago());

        consultasCore.simularDevolucionMasivoCore(numeroRadicado,medioDePagoPersist.getIdMedioDePago());
        Map<String, Object> params = new HashMap<>();
        params.put("resultadoAnalisis", 1);
        params.put("usuarioSupervisor", userDTO.getNombreUsuario());

        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
        terminarTarea.execute();
        
 
         Map<String, Object> params2 = new HashMap<>();


         Solicitud solicitud = consultasCore.consultarSolicitudGlobalPorRadicado(numeroRadicado);

         ObtenerTareaActiva servicio = new ObtenerTareaActiva(Long.valueOf(solicitud.getIdInstanciaProceso()));
         //servicio.setToken(userDTO.getToken());
         servicio.execute();
         Map<String, Object> infoTarea = servicio.getResult();
 
 
 
         if (infoTarea == null || !infoTarea.containsKey("idTarea")) {
             throw new RuntimeException("Tarea no encontrada");
         }
         Long idTarea2 =  Long.valueOf(infoTarea.get("idTarea").toString());
 
         //Finalizar revision contable
         TerminarTarea terminarTarea2 = new TerminarTarea(idTarea2, new HashMap<>());
         terminarTarea2.execute();

     }
     
     @Override
public Response generarReporteDevolucionDetalle(String numeroRadicado) {
    String comprimidoString = ConstantesReportes.NOMBRE_REPORTE_DEVOLUCION_DETALLE + ".zip";
    String cabeceras[] = ConstantesReportes.CABECERAS_REPORTE_DEVOLUCION_DETALLE;
    List<ReporteDevolucionDetallado> dataReporte = consultasPila.getReporteDevolucionesDetalle(numeroRadicado);

    List<String[]> dataExcel = new ArrayList<>();
    int fila = 1;
    for (ReporteDevolucionDetallado registro : dataReporte) {
        String[] data = registro.toFromatString();
        data[0] = String.valueOf(fila);
        formatColumn(data, 11); // Monto
        formatColumn(data, 12); // Intereses
        formatColumn(data, 13); // Total
        formatColumn(data, 21); // IBC
        formatColumn(data, 32); // Salario
        formatColumn(data, 33); // Valor aporte
        dataExcel.add(data);
        fila++;
    }
    byte[] infoExcel = ExcelUtil.generarArchivoExcel("reporte_devolucion_detalle.xlsx", dataExcel, Arrays.asList(cabeceras));
    HashMap<String, byte[]> archivos = new HashMap<>();
    archivos.put("reporte_devolucion_detalle.xlsx", infoExcel);
    try {
        return ComprimidoUtil.comprimirZipResponse(2, archivos, comprimidoString);
    } catch (IOException e) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }
}
private void formatColumn(String[] data, int index) {
    if (data.length > index) {
        String value = data[index];
        if (value.contains(".")) {
            value = value.replaceAll("\\.0{1,}", "");
            if (value.endsWith(".")) {
                value = value.substring(0, value.length() - 1);
            }
        }
            data[index] = value;
    }
}
    @Override
    public Response generarReporteDevolucionResultado(String numeroRadicado) {
        //List<DevolucionMasiva> devoluciones = consultasCore.getDevolucionesMasivas(numeroRadicado);
        String comprimidoString = ConstantesReportes.NOMBRE_REPORTE_DEVOLUCION_FINALIZADA + ".zip";

        HashMap<String, byte[]> archivos = new HashMap<>();
        try {
            return ComprimidoUtil.comprimirZipResponse(2, archivos, comprimidoString);
        } catch (IOException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public Response getReporteRecaudoSimulado(Long idSolicitud) {
        String comprimidoString = ConstantesReportes.NOMBRE_REPORTE_RECAUDO_MASIVO_SIMULADO + ".xlsx";
        String cabeceras[] = ConstantesReportes.CABECERAS_APORTE_MASIVO_SIMULADO;
        List<ReporteRecaudoSimulado> dataReporte = consultasPila.consultarRecaudoSimuladoReporte(idSolicitud);
        List<String[]> dataBody = dataReporte.stream().map(ReporteRecaudoSimulado::toFormatString).collect(Collectors.toList());
        List<String> header = Arrays.asList(cabeceras);

        byte[] byteReporteExcel = ExcelUtil.generarArchivoExcel("Resultado", dataBody, header);

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
		inputStream = new BufferedInputStream(new ByteArrayInputStream(byteReporteExcel));
		res = Response.ok(inputStream);
        res.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
        
    }

    @Override
    public Boolean consultarEstadoSimulado(String numeroRadicado) {
        MasivoArchivo archivo = consultasPila.consultarArchivoMasivoPorRadicado(numeroRadicado);
        return archivo.getEstado().equals("SIMULADO") ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Response getDevolucionesSimulado(String numeroRadicacion) {
        String comprimidoString = ConstantesReportes.NOMBRE_REPORTE_DEVOLUCION_MASIVA_SIMULADO + ".xlsx";
        String cabeceras[] = ConstantesReportes.CABECERAS_APORTE_MASIVO_SIMULADO;
        List<ReporteDevolucionesSimulado> dataReporte = consultasPila.consultarDevolucionesSimuladoReporte(numeroRadicacion);
        List<String[]> dataBody = dataReporte.stream().map(ReporteDevolucionesSimulado::toFormatString).collect(Collectors.toList());
        List<String> header = Arrays.asList(cabeceras);

        for (String[] data : dataBody) {
            formatColumn(data, 11); // Aporte Obligatorio
            formatColumn(data, 12); // Valor Intereses
            formatColumn(data, 13); // Total Aporte
        }
        byte[] byteReporteExcel = ExcelUtil.generarArchivoExcel("Resultado", dataBody, header);

        Response.ResponseBuilder res = null;
        BufferedInputStream inputStream;
        inputStream = new BufferedInputStream(new ByteArrayInputStream(byteReporteExcel));
        res = Response.ok(inputStream);
        res.header("Content-Type", MediaType.APPLICATION_OCTET_STREAM_TYPE);
        res.header("Content-Disposition", "attachment; filename=exportar.xlsx");
        return res.build();
    }
    
    //desarollo GLPI 85501
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.composite.service.EntidadDescuentoCompositeService#cargarAutomaticamenteArchivosCrucesAportes()
     */
    @Override 
    public void cargarAutomaticamenteArchivosCrucesAportes() {
        String firmaServicio = "EntidadDescuentoCompositeBusiness.cargarAutomaticamenteArchivosCrucesAportes(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivoCrucesAportesDTO> archivosCruces = obtenerArchivosCrucesAportes();
        if (archivosCruces != null) {
            validarYRegistrarArchivos(archivosCruces);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    /**
     * Método que se encarga de obtener los nombres de los archivos de descuento almacenados en el ECM
     * @param archivosDTO DTO´s con la información de los archivos
     * @return lista de nombres de los archivos de descuento
     */
    private void validarYRegistrarArchivos(List<ArchivoCrucesAportesDTO> archivosDTO){

        String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
        LocalDate fechaHoy = LocalDate.now();
        Long cargue_id=1L;
       try {
            ConsultarUltimoCargueConsolaEstado consultaConsola = new ConsultarUltimoCargueConsolaEstado(
                TipoProcesoMasivoEnum.CARGUE_ARCHIVOS_CRUCES_APORTES,null,null,null);
            consultaConsola.execute();
             ConsolaEstadoCargueProcesoDTO dto = consultaConsola.getResult();
            if (dto !=null){
                cargue_id = dto.getCargue_id() + 1L;
            }
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        for (ArchivoCrucesAportesDTO archivoCrucesAportesDTO : archivosDTO) {
            if(archivoCrucesAportesDTO.getFileName().matches(ExpresionesRegularesConstants.REGEX_NOMBRE_ARCHIVO_CRUCES_APORTES)){
                   if(codigoCaja.equals(archivoCrucesAportesDTO.getFileName().substring(9,14))){ 
                    try {
                       int anho = Integer.parseInt(archivoCrucesAportesDTO.getFileName().substring(15, 19));
                        int mes = Integer.parseInt(archivoCrucesAportesDTO.getFileName().substring(20, 22));
                        int dia = Integer.parseInt(archivoCrucesAportesDTO.getFileName().substring(23, 25));
                        LocalDate.of(anho, mes, dia);
                            LocalDate fechaArchivo = null;
                            fechaArchivo = LocalDate.of(anho, mes, dia);
                        if (fechaHoy.compareTo(fechaArchivo) == 0) {
                            List<String> codigosArchivosRegistrados = new ArrayList<>();
                           // for (ArchivoCrucesAportesDTO archivoCrucesAportesDTO : archivosDTO) {
                                     logger.info ("*__* archivoCrucesAportesDTO " + archivoCrucesAportesDTO.toString() + archivoCrucesAportesDTO.getFileName());
 
                                    //Se almacena el archivo en el ECM y se registra la trazabilidad del archivo
                                    InformacionArchivoDTO informacionArchivo = new InformacionArchivoDTO();
                                    try {
                                        informacionArchivo = almacenarArchivo(archivoCrucesAportesDTO);
                                        informacionArchivo = obtenerArchivo(informacionArchivo.getIdentificadorDocumento());
                                        informacionArchivo.setFileName(archivoCrucesAportesDTO.getFileName());
                                    } catch (Exception e) {
                                        logger.debug(ConstantesComunes.FIN_LOGGER + e);
                                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                                    }
                                    //valida con Lion y registra lineas
                                    ResultadoValidacionArchivoDTO resultadoValidacionDTO = null;
                                    try {
                                        resultadoValidacionDTO = validarEstructuraArchivoCrucesAportes(informacionArchivo);
                                    } catch (Exception e) {
                                        logger.debug(ConstantesComunes.FIN_LOGGER + e);
                                        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
                                    }
                                        Long lineasConError = 0L;
                                        Long lineaError = -1L;
                                        for(ResultadoHallazgosValidacionArchivoDTO hall:resultadoValidacionDTO.getResultadoHallazgosValidacionArchivoDTO()){
                                            if(lineaError != hall.getNumeroLinea()){
                                                lineasConError ++;
                                            }
                                            lineaError = hall.getNumeroLinea();
                                        }
                                    ConsolaEstadoCargueProcesoDTO consolaCarga = new ConsolaEstadoCargueProcesoDTO();
                                    consolaCarga.setCcf(codigoCaja);
                                    consolaCarga.setCargue_id(cargue_id);
                                    consolaCarga.setEstado(lineasConError > 0 ? EstadoCargueMasivoEnum.FIN_ERROR:EstadoCargueMasivoEnum.FINALIZADO);
                                    consolaCarga.setFileLoaded_id(resultadoValidacionDTO.getFileDefinitionId());
                                    consolaCarga.setGradoAvance(new BigDecimal(100));
                                    consolaCarga.setIdentificacionECM(informacionArchivo.getIdentificadorDocumento());
                                    consolaCarga.setNumRegistroConErrores(lineasConError);
                                    consolaCarga.setLstErroresArhivo(resultadoValidacionDTO.getResultadoHallazgosValidacionArchivoDTO());
                                    consolaCarga.setNumRegistroObjetivo(resultadoValidacionDTO.getTotalRegistro());
                                    consolaCarga.setNumRegistroProcesado(resultadoValidacionDTO.getTotalRegistro());
                                    consolaCarga.setNumRegistroValidados(resultadoValidacionDTO.getRegistrosValidos());
                                    consolaCarga.setProceso(TipoProcesoMasivoEnum.CARGUE_ARCHIVOS_CRUCES_APORTES);
                                    consolaCarga.setUsuario(null);
                                    consolaCarga.setNombreArchivo(informacionArchivo.getFileName());
                                    registrarConsolaEstado(consolaCarga);  
                                    cargue_id = cargue_id + 1L;

                           // }
                        }
                    } catch (Exception e) {
                        logger.debug(ConstantesComunes.FIN_LOGGER
                                + " Se intentó subir un archivo con fecha no calida en el nombre "
                                + archivoCrucesAportesDTO.getFileName());
                    }
                }
            }
        }

    }
    /**
     * Método que se encarga de almacenar el archivo en el Enterprise Content Management
     * @param archivoCrucesAportesDTO DTO con la información del archivo
     * @return DTO con la información del archivo almacenado
     */
    private InformacionArchivoDTO almacenarArchivo(ArchivoCrucesAportesDTO archivoCrucesAportesDTO){
        InformacionArchivoDTO informacionArchivo = archivoCrucesAportesDTO.convertToParent();
        AlmacenarArchivo almacenarArchivo = new AlmacenarArchivo(informacionArchivo);
        almacenarArchivo.execute();
        return almacenarArchivo.getResult();

    }

    public ResultadoValidacionArchivoDTO validarEstructuraArchivoCrucesAportes(InformacionArchivoDTO informacionArchivoDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.validarEstructuraArchivoDescuentos(InformacionArchivoDTO,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoValidacionArchivoDTO resultadoDTO = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_CRUCES_APORTES).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }
        FileFormat fileFormat;
        if (informacionArchivoDTO.getFileName().toUpperCase().endsWith(ArchivoMultipleCampoConstants.DELIMITED_TEXT_PLAIN)) {
            fileFormat = FileFormat.DELIMITED_TEXT_PLAIN;
        }
        else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        Map<String, Object> context = new HashMap<String, Object>();
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        List<Long> totalRegistro = new ArrayList<Long>();
        List<Long> totalRegistroError = new ArrayList<Long>();
        List<Long> totalRegistroValidos = new ArrayList<Long>();
        List<AportanteCruceCierreDTO> listaAportantes = new ArrayList<AportanteCruceCierreDTO>();
        context.put(CamposArchivoConstants.LISTA_HALLAZGOS, listaHallazgos);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO, totalRegistro);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES, totalRegistroError);
        context.put(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO, totalRegistroValidos);
        context.put(CamposArchivoConstants.LISTA_APORTANTES_CRUCE_APORTES, listaAportantes);
        FileLoaderOutDTO outDTO = new FileLoaderOutDTO();
           System.out.println("**__** Inicia fileLoader.validateAndLoad: "+fileFormat+" fileDefinitionId: "+fileDefinitionId);
        try {
            outDTO = fileLoader.validateAndLoad(context, fileFormat, informacionArchivoDTO.getDataFile(), fileDefinitionId,
                    informacionArchivoDTO.getFileName());

            listaHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) outDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            listaAportantes = (List<AportanteCruceCierreDTO>) outDTO.getContext()
                    .get(CamposArchivoConstants.LISTA_APORTANTES_CRUCE_APORTES);
            resultadoDTO = new ResultadoValidacionArchivoDTO();
            resultadoDTO.setNombreArchivo(informacionArchivoDTO.getFileName());
            
            listaHallazgos.addAll(consultarListaHallazgos(fileDefinitionId, outDTO));

            if (FileLoadedState.SUCCESFUL.equals(outDTO.getState())) {
                resultadoDTO.setEstadoCargueArchivoCrucesAportes(EstadoCargaArchivoCrucesAportesEnum.CARGADO);
            }
            else {
                resultadoDTO.setEstadoCargueArchivoCrucesAportes(EstadoCargaArchivoCrucesAportesEnum.ANULADO);
            }
            
            List<Long> numLinea = new ArrayList<Long>();
            for (ResultadoHallazgosValidacionArchivoDTO hallazgo : listaHallazgos) {
                if (!numLinea.contains(hallazgo.getNumeroLinea())) {
                    numLinea.add(hallazgo.getNumeroLinea());
                }
            }
            
            totalRegistro = (List<Long>) outDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO);
            totalRegistroError = (List<Long>) outDTO.getContext()
                    .get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES);
            totalRegistroValidos = (List<Long>) outDTO.getContext()
                    .get(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO);
            
            Long cantidadLinea = 0L;
            if (totalRegistro.isEmpty()) {
                cantidadLinea = (long) numLinea.size();
            }
            Long sumTotalRegistro = (long) totalRegistro.size() + cantidadLinea;
            Long registrosError = (long) totalRegistroError.size();
            
            if (registrosError == sumTotalRegistro) {
                resultadoDTO.setEstadoCargueArchivoCrucesAportes(EstadoCargaArchivoCrucesAportesEnum.ANULADO);
            }
            if(!listaAportantes.isEmpty()){
                generarResultadosArchivoCruce(listaAportantes);
            }
            resultadoDTO.setResultadoHallazgosValidacionArchivoDTO(listaHallazgos);
            resultadoDTO.setTotalRegistro(sumTotalRegistro);
            resultadoDTO.setRegistrosConErrores(((long) totalRegistroError.size())+cantidadLinea);
            resultadoDTO.setRegistrosValidos(sumTotalRegistro - resultadoDTO.getRegistrosConErrores());
            resultadoDTO.setFechaCargue(new Date().getTime());
            resultadoDTO.setFileDefinitionId(fileDefinitionId);
            
        } catch (FileProcessingException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultadoDTO;
    }
    
    /**
     * Método encargado de consultar la lista hallazgos
     * 
     * @param fileDefinitionId
     *        Identificador del archivo
     * @param outDTO
     *        Objeto con el procesamiento del archivo
     * @return Lista de hallazgos del procesamiento del archivo
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId, FileLoaderOutDTO outDTO) {
        // Lista de errores
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        // Campos asociados al archivo
        List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
        // Se verifica si se registraron errores en la tabla FileLoadedLog
        if (outDTO.getFileLoadedId() != null && outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty()) {
            // Se recorren los errores y se crean los respectivos hallazgos
            for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
                listaHallazgos.add(obtenerHallazgo(campos, detalleError.getMessage(), detalleError.getLineNumber()));
            }
        }
        return listaHallazgos; 
    }
    
    /**
     * Metodo encargado de consultar los campos del archivo
     * 
     * @param fileLoadedId
     *            identificador del fileLoadedId
     * @return lista de definiciones de campos
     */
    private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
        return consultasCore.consultarCamposDelArchivo(fileLoadedId);
	}

    /**
     * Obtiene el hallazgo a partir de la informacion del mensaje y los campos
     * @param campos
     *        Lista de campos del archivo
     * @param mensaje
     *        Mensaje de error obtenido
     * @param lineNumber
     *        Linea donde se encontro el error
     * @return Hallazgo creado en respectivo formato
     */
    private ResultadoHallazgosValidacionArchivoDTO obtenerHallazgo(List<DefinicionCamposCargaDTO> campos, String mensaje, Long lineNumber) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
        // Indica si el mensaje contiene el nombre del campo
        Boolean encontroCampo = Boolean.FALSE;
        // Se separa el mensaje por caracter ;
        String[] arregloMensaje = mensaje.split(";");
        // Se verifica si el mensaje contiene algún campo
        for (DefinicionCamposCargaDTO campo : campos) {
            for (int i = 0; i < arregloMensaje.length; i++) {
                if (arregloMensaje[i].contains(campo.getName())) {
                    mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
                    hallazgo = crearHallazgo(lineNumber, campo.getLabel(), mensaje);
                    encontroCampo = Boolean.TRUE;
                    break;
                }
            }
        }
        // Si no se encontro campo se crea el hallazgo sin campo
        if (!encontroCampo) {
            hallazgo = crearHallazgo(lineNumber, "", mensaje);
        }
        return hallazgo;
    }
    
    /**
     * Metodo encargado retornar un DTO que se construye con los datos que
     * llegan por parametro
     * 
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return retorna el resultado hallazgo validacion
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(errorMessage);
        return hallazgo;
    }
    public List<ArchivoCrucesAportesDTO> obtenerArchivosCrucesAportes() {
        String firmaServicio = "EntidadDescuentoBusiness.obtenerArchivos()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<ArchivoCrucesAportesDTO> archivos = new ArrayList<>();
        try {
            archivos = obtenerArchivosFTP();
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
         
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return archivos;
    }

        /**
     * Método que permite obtener la lista de archivos entregados por la entidad de CRUCES_APORTES
     * @param archivos
     *        arreglo para almacenar la información de los archivos
     * @return lista de DTO´s con la información de los archivos
     */
    private List<ArchivoCrucesAportesDTO> obtenerArchivosFTP() {
        String firmaServicio = "APORTES MASIVOS.obtenerArchivosFTP(List<ArchivoCrucesAportesDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ConexionServidorFTPUtil<ArchivoCrucesAportesDTO> conexionFTP = new ConexionServidorFTPUtil<ArchivoCrucesAportesDTO>("CARGUE_ARCHIVOS_CRUCES_APORTES", ArchivoCrucesAportesDTO.class);
        conexionFTP.setNombreHost((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_HOST));
        conexionFTP.setPuerto((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_PUERTO));
        conexionFTP.setUrlArchivos((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS));
        conexionFTP.setProtocolo(
                Protocolo.valueOf((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_PROTOCOLO)));
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_USUARIO)));
        conexionFTP.setContrasena(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_CONTRASENA)));

        conexionFTP.conectarYRecorrer();

        if (!conexionFTP.getArchivosDescargados().isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return conexionFTP.getArchivosDescargados();
        }
        else {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return null;
        }
    }
    
    public void generarResultadosArchivoCruce(List<AportanteCruceCierreDTO> aportanteCruceCierreDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.generarResultadosArchivosDescuento(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ArchivoCruceAportesFilterDTO archivoCruceAportesFilter = new ArchivoCruceAportesFilterDTO();
        archivoCruceAportesFilter.setAportantes(aportanteCruceCierreDTO);
        InformacionArchivoDTO archivoXLSX = null;
        Long fileDefinitionId;
        try {
            fileDefinitionId = new Long(
                    CacheManager.getConstante(ConstantesSistemaConstants.FILE_DEFINITION_ID_RESULTADOS_CRUCE_APORTES).toString());
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_CONFIGURACION_CARGA_MULTIPLE, e);
        }

        FileFormat[] formats = { FileFormat.DELIMITED_TEXT_PLAIN };
        try {
            

            String codigoCaja =  CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();
           

            FileGeneratorOutDTO outDTO = fileGenerator.generate(fileDefinitionId, archivoCruceAportesFilter, formats);
            logger.info(outDTO.getState());
            if (outDTO.getState().equals(FileGeneratedState.SUCCESFUL)) {
                Date fechaEnvio = new Date();
                //outDTO.setDelimitedTxtFilename(generarNombreArchivo(codigoCaja, ".txt", fechaEnvio));
                outDTO.setDelimitedTxtFilename(generarNombreArchivo(codigoCaja, ".csv", fechaEnvio));

                enviarArchivoServidorFTP(outDTO);

            }
        } catch (AsopagosException e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            throw e;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    private void enviarArchivoServidorFTP(FileGeneratorOutDTO outDTO) {
        String firmaServicio = "EntidadDescuentoBusiness.enviarArchivoServidorFTP(FileGeneratorOutDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ConexionServidorFTPUtil<ArchivoCrucesAportesDTO> conexionFTP = new ConexionServidorFTPUtil<ArchivoCrucesAportesDTO>("CARGUE_ARCHIVOS_CRUCES_APORTES", ArchivoCrucesAportesDTO.class);
        conexionFTP.setNombreHost((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_HOST));
        conexionFTP.setPuerto((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_PUERTO));
        String urlArchivos = (String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS_SALIDA);
        conexionFTP.setUrlArchivos(urlArchivos);        
        conexionFTP.setProtocolo(
                Protocolo.valueOf((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_PROTOCOLO)));
        conexionFTP.setNombreUsuario(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_USUARIO)));
        conexionFTP.setContrasena(DesEncrypter.getInstance()
                .decrypt((String) CacheManager.getParametro(ParametrosSistemaConstants.FTP_ARCHIVOS_CRUCES_APORTES_CONTRASENA)));

        conexionFTP.subirArchivoFTP(outDTO.getDelimitedTxtFilename(), outDTO.getDelimitedTxt());

        logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }

    private String generarNombreArchivo(String codigoCaja, String extension, Date fechaEnvio){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return "CRUCE_" +  codigoCaja + "_" + format.format(fechaEnvio) + "_" + formatHora.format(fechaEnvio) + extension;
    }

}
