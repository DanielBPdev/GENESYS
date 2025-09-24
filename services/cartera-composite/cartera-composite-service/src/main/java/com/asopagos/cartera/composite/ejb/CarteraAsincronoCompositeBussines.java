package com.asopagos.cartera.composite.ejb;

import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.afiliados.clients.ConsultarRolesAfiliado;
import com.asopagos.afiliados.dto.RolAfiliadoEmpleadorDTO;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.cartera.clients.*;
import com.asopagos.cartera.composite.clients.GuardarResultadosEdictos;
import com.asopagos.cartera.composite.service.CarteraAsincronoCompositeService;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.cartera.dto.FiltroDetalleSolicitudGestionCobroDTO;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.empleadores.clients.GuardarDatosEmpleador;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.*;
import com.asopagos.enumeraciones.core.ExpulsionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ConsultarSolicitudPorRadicado;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.RetomarTarea;
import com.asopagos.tareashumanas.clients.SuspenderTarea;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
public class CarteraAsincronoCompositeBussines implements CarteraAsincronoCompositeService {

    /**
     * Referencia al logger.
     */
    private final ILogger logger = LogManager.getLogger(CarteraAsincronoCompositeBussines.class);

    /**
     * Constante con el nombre del parámetro entregar
     */
    private static final String ENTREGA = "entrega";

    @Override
    @Asynchronous
    public void finalizarResultadosEdictosAsync(RegistroRemisionAportantesDTO registroDTO) {
        logger.debug("Inicio de método finalizarResultadosEdictosAsync");
        List<DetalleSolicitudGestionCobroModeloDTO> listaDetallesDTO = new ArrayList<>();

        SolicitudGestionCobroFisicoModeloDTO solicitud = consultarSolicitudGestionCobro(registroDTO.getNumeroRadicacion());

        Long idTarea = obtenerTareaActiva(solicitud.getIdInstanciaProceso(), registroDTO.getToken());
        //Se suspende temporalmente la tarea a fin de que no sea retomada por el usuario desde la bandeja de tareas
        SuspenderTarea suspenderTareaService = new SuspenderTarea(idTarea, new HashMap<>());
        suspenderTareaService.setToken(registroDTO.getToken());
        suspenderTareaService.execute();

        GuardarResultadosEdictos guardarEdictos = new GuardarResultadosEdictos(registroDTO);
        guardarEdictos.execute();
        SolicitudGestionCobroFisicoModeloDTO solicitudDTO = guardarEdictos.getResult();

        List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleDTO = consultarDetalleGestionCobroSolicitud(
                solicitudDTO.getIdSolicitudGestionCobroFisico());

        // Actualización del estado de la solicitud de gestión de cobro
        actualizarEstadoSolicitudGestionCobro(solicitudDTO.getNumeroRadicacion(),
                EstadoSolicitudGestionCobroEnum.ACTUALIZACION_EXITOSA);
        actualizarEstadoSolicitudGestionCobro(solicitudDTO.getNumeroRadicacion(),
                EstadoSolicitudGestionCobroEnum.CERRADA);

        List<BitacoraCarteraDTO> bitacoras = new ArrayList<>();
        // Actualización del estado del detalle de gestión de cobro
        for (DetalleSolicitudGestionCobroModeloDTO detalleDTO : listaDetalleDTO) {

            detalleDTO.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
            listaDetallesDTO.add(detalleDTO);

            ResultadoBitacoraCarteraEnum resultado = ResultadoBitacoraCarteraEnum.PUBLICADO;

            if (!detalleDTO.getEnviarPrimeraRemision()) {
                resultado = ResultadoBitacoraCarteraEnum.NO_PUBLICADO;
            }

            // Almacena la bitácora
            List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<DocumentoSoporteModeloDTO>();

            if (solicitudDTO.getDocumentoSoporte() != null) {
                listaDocumentos.add(solicitudDTO.getDocumentoSoporte());
            }

            bitacoras.add(crearBitacoraCarteraDTO(null, null, TipoActividadBitacoraEnum.E2, MedioCarteraEnum.DOCUMENTO_FISICO, resultado,
                    null, null, null, listaDocumentos, null, detalleDTO.getIdCartera()));
        }

        //Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
        RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
        retomarTareaService.setToken(registroDTO.getToken());
        retomarTareaService.execute();

        guardarListaDetalleSolicitudGestionCobro(listaDetallesDTO, solicitudDTO.getIdSolicitud());
        guardarListaBitacorasCarteraPorIdCartera(bitacoras);
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
        terminarTarea.setToken(registroDTO.getToken());
        terminarTarea.execute();

        logger.debug("Fin de método finalizarResultadosEdictosAsync");
    }

    /**
     * (non-Javadoc)
     */
    @Override
    @Asynchronous
    public void confirmarPrimeraRemisionAsync(RegistroRemisionAportantesDTO registroRemision) {
        logger.debug("Inicio de método confirmarPrimeraRemision(RegistroRemisionAportantesDTO)");

        /* se actualizan los campos de la remisión a la solicitud. */
        SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobro = consultarSolicitudGestionCobro(registroRemision.getNumeroRadicacion());
        solicitudGestionCobro.setObservacionRemision(registroRemision.getObservaciones());

        Long idTarea = obtenerTareaActiva(solicitudGestionCobro.getIdInstanciaProceso(), registroRemision.getToken());
        //Se suspende temporalmente la tarea a fin de que no sea retomada por el usuario desde la bandeja de tareas
        SuspenderTarea suspenderTareaService = new SuspenderTarea(idTarea, new HashMap<>());
        suspenderTareaService.setToken(registroRemision.getToken());
        suspenderTareaService.execute();

        if (registroRemision.getIdDocumento() != null) {
            InformacionArchivoDTO archivo = null;
            DocumentoSoporteModeloDTO documento = null;
            archivo = obtenerArchivo(registroRemision.getIdDocumento());
            if (solicitudGestionCobro.getDocumentoSoporte() == null) {
                documento = new DocumentoSoporteModeloDTO();
                documento.setFechaHoraCargue(new Date().getTime());
                documento.setIdentificacionDocumento(archivo.getIdentificadorDocumento() + "_" + archivo.getVersionDocumento());
                documento.setNombre("Documento soporte remisión de los comunicados");
                documento.setVersionDocumento(archivo.getVersionDocumento());
                documento.setTipoDocumento(TipoDocumentoAdjuntoEnum.OTRO);
                documento = guardarDocumentoSoporte(documento);
                solicitudGestionCobro.setDocumentoSoporte(documento);
            } else if (!solicitudGestionCobro.getDocumentoSoporte().getIdentificacionDocumento().equals(registroRemision.getIdDocumento())) {
                documento = solicitudGestionCobro.getDocumentoSoporte();
                documento.setFechaHoraCargue(new Date().getTime());
                documento.setIdentificacionDocumento(archivo.getIdentificadorDocumento() + "_" + archivo.getVersionDocumento());
                documento.setVersionDocumento(archivo.getVersionDocumento());
                documento = guardarDocumentoSoporte(documento);
                solicitudGestionCobro.setDocumentoSoporte(documento);
            }
        }

        if (registroRemision.getFechaRemision() != null) {
            if (registroRemision.getHoraRemision() != null) {
                solicitudGestionCobro.setFechaRemision(CalendarUtils
                        .concatenarFechaHora(registroRemision.getFechaRemision(), registroRemision.getHoraRemision()).getTime());
            } else {
                solicitudGestionCobro.setFechaRemision(registroRemision.getFechaRemision());
            }
        }

        /* se ajustan los filtros para consultar los detalles de todos los aportantes. */
        List<FiltroDetalleSolicitudGestionCobroDTO> lstFiltroDetalleSolicitud = new ArrayList<>();
        for (AportanteRemisionComunicadoDTO aportanteRemision : registroRemision.getAportantes()) {
            FiltroDetalleSolicitudGestionCobroDTO filtroDetalleSolicitud = new FiltroDetalleSolicitudGestionCobroDTO();
            filtroDetalleSolicitud.setTipoIdentificacion(aportanteRemision.getTipoIdentificacion());
            filtroDetalleSolicitud.setNumeroIdentificacion(aportanteRemision.getNumeroIdentificacion());
            filtroDetalleSolicitud.setNumeroRadicacion(registroRemision.getNumeroRadicacion());
            filtroDetalleSolicitud.setObservacionPrimeraRemision(aportanteRemision.getObservacion());
            filtroDetalleSolicitud.setEnviarPrimeraRemision(aportanteRemision.getEnviar());

            lstFiltroDetalleSolicitud.add(filtroDetalleSolicitud);
        }

        List<DetalleSolicitudGestionCobroModeloDTO> lstDetallesSolicitudes = consultarDetalleSolicitudGestionCobro(
                lstFiltroDetalleSolicitud);

        TipoActividadBitacoraEnum actividad = TipoActividadBitacoraEnum.valueOf(registroRemision.getAccionCobro().toString());

        /* se define si es un guardado o si se va a confirmar la solicitud */
        if (!registroRemision.getGuardar()) {
            Map<String, Object> params = new HashMap<>();
            guardarSolicitudGestionCobro(solicitudGestionCobro);
            Boolean entregar = actualizarEstadosPrimeraRemision(lstDetallesSolicitudes, registroRemision.getNumeroRadicacion(),
                    solicitudGestionCobro, actividad, registroRemision.getUserDTO().getNombreUsuario());
            params.put(ENTREGA, entregar);

            //Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
            RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
            retomarTareaService.setToken(registroRemision.getToken());
            retomarTareaService.execute();

            TerminarTarea terminarTarea = new TerminarTarea(idTarea, params == null ? new HashMap<>() : params);
            terminarTarea.setToken(registroRemision.getToken());
            terminarTarea.execute();
        } else {
            guardarSolicitudGestionCobro(solicitudGestionCobro);
            /* unicamente se guarda pero no se actualizan estados */
            actualizarDetallesSolicitudGestionCobro(lstDetallesSolicitudes);

            //Se retoma la tarea al estado previo a la suspensión.
            RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
            retomarTareaService.setToken(registroRemision.getToken());
            retomarTareaService.execute();
        }
    }

    /**
     * (non-Javadoc)
     */
    @Override
    @Asynchronous
    public void registrarResultadosPrimeraRemisionAsync(String numeroRadicacion, Long idTarea,
                                                        List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO, TipoAccionCobroEnum accionCobro, UserDTO userDTO) {

        //Se suspende temporalmente la tarea a fin de que no sea retomada por el usuario desde la bandeja de tareas
        SuspenderTarea suspenderTareaService = new SuspenderTarea(idTarea, new HashMap<>());
        suspenderTareaService.setToken(userDTO.getToken());
        suspenderTareaService.execute();
        try {
            logger.debug("Inicio de método registrarResultadosPrimeraRemision(String, List<AportanteRemisionComunicadoDTO>,UserDTO)");
            SolicitudModeloDTO solicitud = consultarSolicitudPorRadicado(numeroRadicacion);
            Boolean finalizar = Boolean.TRUE;
            List<FiltroDetalleSolicitudGestionCobroDTO> lstFiltroDetalleSolicitud = new ArrayList<>();
            for (AportanteRemisionComunicadoDTO aportanteRemisionComunicadoDTO : aportanteRemisionDTO) {
                FiltroDetalleSolicitudGestionCobroDTO filtroDetalleSolicitud = new FiltroDetalleSolicitudGestionCobroDTO();
                filtroDetalleSolicitud.setTipoIdentificacion(aportanteRemisionComunicadoDTO.getTipoIdentificacion());
                filtroDetalleSolicitud.setNumeroIdentificacion(aportanteRemisionComunicadoDTO.getNumeroIdentificacion());
                filtroDetalleSolicitud.setNumeroRadicacion(numeroRadicacion);
                filtroDetalleSolicitud.setEnviarPrimeraRemision(Boolean.TRUE);//Se envía true en registrar remisión por que este valor cambia al confimar en la HU-170, este punto corresponde a la HU-172
                //filtroDetalleSolicitud.setObservacionPrimeraRemision(aportanteRemisionDTO.getObservacionPrimeraEntrega);

                lstFiltroDetalleSolicitud.add(filtroDetalleSolicitud);
                if (ResultadoEntregaEnum.PENDIENTE.equals(aportanteRemisionComunicadoDTO.getResultadoPrimeraEntrega())
                        || (!EstadoTareaGestionCobroEnum.CERRADA.equals(aportanteRemisionComunicadoDTO.getEstadoTarea())
                        && !EstadoTareaGestionCobroEnum.PENDIENTE_ACTUALIZACION_DATOS
                        .equals(aportanteRemisionComunicadoDTO.getEstadoTarea()))) {
                    /* si falta una acción por ejecutarse o algún resultado esta pendiente no se puede finalizar solo se guarda */
                    finalizar = Boolean.FALSE;
                }
            }
            /* si no se puede finalizar se guarda */
            List<DetalleSolicitudGestionCobroModeloDTO> detalles = consultarDetalleSolicitudGestionCobro(lstFiltroDetalleSolicitud);
            /* Lista de detalles exitosos y no exitosos */
            List<DetalleSolicitudGestionCobroModeloDTO> detallesExitosoNoExito = new ArrayList<>();
            /* se actualizan los campos de cada detalle a la solicitud */
            for (DetalleSolicitudGestionCobroModeloDTO detalle : detalles) {
                for (AportanteRemisionComunicadoDTO aportanteRemision : aportanteRemisionDTO) {
                    if (detalle.getTipoIdentificacion().equals(aportanteRemision.getTipoIdentificacion())
                            && detalle.getNumeroIdentificacion().equals(aportanteRemision.getNumeroIdentificacion())) {
                        detalle.setObservacionPrimeraEntrega(aportanteRemision.getObservacionPrimeraEntrega());
                        detalle.setResultadoPrimeraEntrega(aportanteRemision.getResultadoPrimeraEntrega());
                        detalle.setFechaPrimeraEntrega(aportanteRemision.getFechaPrimeraEntrega());
                        DocumentoSoporteModeloDTO documentoSoporte = construirDocumentoSoporte(detalle.getDocumentoPrimeraRemision(),
                                aportanteRemision.getIdDocumentoPrimeraEntrega(), "DOCUMENTO_REGISTRO_PRIMERA_REMISION_COMUNICADO",
                                "Se realiza registro de primera remisión del comunicado");
                        detalle.setDocumentoPrimeraRemision(documentoSoporte);

                        if (ResultadoEntregaEnum.ENTREGA_EXITOSA.equals(aportanteRemision.getResultadoPrimeraEntrega())
                                || ResultadoEntregaEnum.ENTREGA_NO_EXITOSA.equals(aportanteRemision.getResultadoPrimeraEntrega())) {
                            detallesExitosoNoExito.add(detalle);
                        }

                        // Registro de bitácora
                        if (finalizar) {
                            ResultadoBitacoraCarteraEnum resultado = ResultadoBitacoraCarteraEnum.EXITOSO;

                            if (!ResultadoEntregaEnum.ENTREGA_EXITOSA.equals(aportanteRemision.getResultadoPrimeraEntrega())) {
                                resultado = ResultadoBitacoraCarteraEnum.NO_EXITOSO;
                            }

                            List<DocumentoSoporteModeloDTO> documentosBitacora = new ArrayList<>();
                            documentosBitacora.add(documentoSoporte);
                            PersonaModeloDTO personaDTO = consultarDatosPersona(aportanteRemision.getTipoIdentificacion(),
                                    aportanteRemision.getNumeroIdentificacion());
                            CarteraModeloDTO carteraDTO = obtenerInformacionCartera(aportanteRemision.getTipoIdentificacion(),
                                    aportanteRemision.getNumeroIdentificacion(), aportanteRemision.getTipoAportante(),
                                    accionCobro.getLineaCobro(), aportanteRemision.getPeriodo());

                            // Ajuste por mantis 250938
                            if (null != carteraDTO) {
                                Long numeroOperacion = consultarNumeroOperacionCartera(carteraDTO.getIdCartera());
                                almacenarBitacoraCartera(null, null, TipoActividadBitacoraEnum.valueOf(accionCobro.name()),
                                        MedioCarteraEnum.DOCUMENTO_FISICO, resultado, null, personaDTO.getIdPersona(),
                                        aportanteRemision.getTipoAportante(), documentosBitacora, numeroOperacion.toString(), userDTO.getNombreUsuario());
                            }

                        }
                    }
                }
            }
            /* se guarda los detalles */
            guardarDetallesSolicitudGestionCobro(detalles, solicitud.getIdSolicitud());
            if (finalizar) {
                finalizarResultadosPrimeraRemision(numeroRadicacion, idTarea, detallesExitosoNoExito, userDTO);
            } else {
                //Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
                RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
                retomarTareaService.setToken(userDTO.getToken());
                retomarTareaService.execute();
            }
            logger.debug("Fin de método registrarResultadosPrimeraRemision(String, List<AportanteRemisionComunicadoDTO>,UserDTO)");
        } catch (AsopagosException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error("Ocurrió un error registrarResultadosPrimeraRemision(String, List<AportanteRemisionComunicadoDTO>,UserDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * Método encargado de invocar el servicio que actualiza el estado de una solicitud de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud de gestión de cobro.
     * @param estadoSolicitud  estado de solicitud a actualizar.
     */
    private void actualizarEstadoSolicitudGestionCobro(String numeroRadicacion, EstadoSolicitudGestionCobroEnum estadoSolicitud) {
        logger.debug("Inicio de método actualizarEstadoSolicitudGestionCobro");
        ActualizarEstadoSolicitudGestionCobro actualizarEstadoService = new ActualizarEstadoSolicitudGestionCobro(numeroRadicacion,
                estadoSolicitud);
        actualizarEstadoService.execute();
        logger.debug("Fin de método actualizarEstadoSolicitudGestionCobro");
    }

    /**
     * Método que invoca el servicio que obtiene la lista de aportantes asociados a una solicitud de gestión de cobro físico
     *
     * @param idSolicitudFisico Identificador de la solicitud de gestión de cobro físico
     * @return La lista de aportantes
     */
    private List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleGestionCobroSolicitud(Long idSolicitudFisico) {
        logger.debug("Inicio de método consultarDetalleGestionCobroSolicitud");
        ConsultarDetalleGestionCobroSolicitud service = new ConsultarDetalleGestionCobroSolicitud(idSolicitudFisico);
        service.execute();
        logger.debug("Fin de método consultarDetalleGestionCobroSolicitud");
        return service.getResult();
    }

    /**
     * Método que prepara el registro de bitácora a ser almacenado
     *
     * @param idBitacoraCartera Identificador del registro en bitácora
     * @param fecha             Fecha de registro
     * @param actividad         Actividad
     * @param medio             Medio o canal
     * @param resultado         Resultado
     * @param usuario           Usuario que registró
     * @param idPersona         Identificador de la persona aportante
     * @param tipoSolicitante   Tipo de aportante
     * @param documentosSoporte Lista de documentos a agregar en la traza
     * @param numeroOperacion   Número de operación
     * @param idCartera         Número identificador de la cartera
     */
    private BitacoraCarteraDTO crearBitacoraCarteraDTO(Long idBitacoraCartera, Long fecha,
                                                       TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado,
                                                       String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                       List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, Long idCartera) {
        BitacoraCarteraDTO bitacora = new BitacoraCarteraDTO(idBitacoraCartera, fecha, actividad, medio, resultado,
                usuario, idPersona, tipoSolicitante, documentosSoporte, numeroOperacion, idCartera);
        return bitacora;
    }

    /**
     * Metodo que invoca servicio de guardar el DetalleSolicitudGestionCobro
     *
     * @param detallesSolicitudGestionCobroModeloDTO DTO con la informacion relacionado al detalle
     */
    private void guardarListaDetalleSolicitudGestionCobro(
            List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO, Long idSolicitudGlobal) {
        String fimaMetodo = "guardarListaDetalleSolicitudGestionCobro(Lizt<DetalleSolicitudGestionCobroModeloDTO>)";
        logger.debug("Inicia " + fimaMetodo);
        GuardarDetallesSolicitudGestionCobroFisico guardar = new GuardarDetallesSolicitudGestionCobroFisico(idSolicitudGlobal,
                detallesSolicitudGestionCobroModeloDTO);
        guardar.execute();
        logger.debug("Finaliza " + fimaMetodo);
    }

    /**
     * Método que invoca el servicio que crea varias bitácora
     *
     * @param listaBitacorasCartera
     */
    private void guardarListaBitacorasCarteraPorIdCartera(List<BitacoraCarteraDTO> listaBitacorasCartera) {
        logger.debug("Inicio de método guardarListaBitacorasCarteraPorIdCartera");
        GuardarListaBitacorasCarteraPorIdCartera service = new GuardarListaBitacorasCarteraPorIdCartera(listaBitacorasCartera);
        service.execute();
        logger.debug("Fin de método guardarListaBitacorasCarteraPorIdCartera");
    }

    /**
     * Método encargado de llamar el cliente del servicio que se encarga de obtener la tarea activa
     *
     * @param idInstanciaProceso, id de instancia proceso
     * @return retornal el id de la tarea
     */
    private Long obtenerTareaActiva(String idInstanciaProceso, String token) {
        logger.debug("Inicia obtenerTareaActiva( String )");
        Map<String, Object> mapResult;
        ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
        obtenerTareaActivaService.setToken(token);
        obtenerTareaActivaService.execute();
        mapResult = obtenerTareaActivaService.getResult();
        String idTarea = ((Integer) mapResult.get("idTarea")).toString();
        logger.debug("Finaliza obtenerTareaActiva( String )");
        return new Long(idTarea);
    }

    /**
     * Método que se encarga de consultar la solicitud de gestión de cobro por el número de radicación.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     * @return solicitud de gestión de cobro encontrada.
     */
    private SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobro(String numeroRadicacion) {
        logger.debug("Inicio de método consultarSolicitudGestionCobro(String numeroRadicacion)");
        ConsultarSolicitudGestionCobro consultarSolicitudService = new ConsultarSolicitudGestionCobro(numeroRadicacion);
        consultarSolicitudService.execute();
        logger.debug("Fin de método consultarSolicitudGestionCobro(String numeroRadicacion)");
        return consultarSolicitudService.getResult();
    }

    /**
     * Método que consulta un archivo de acuerdo con el id del ECM
     *
     * @param archivoId Identificador del ECM
     * @return La información del archivo consultado
     */
    private InformacionArchivoDTO obtenerArchivo(String archivoId) {
        logger.debug("Inicia método obtenerArchivo");
        ObtenerArchivo consultarArchivo = new ObtenerArchivo(archivoId);
        consultarArchivo.execute();
        InformacionArchivoDTO archivoMultiple = consultarArchivo.getResult();
        logger.debug("Finaliza método obtenerArchivo");
        return archivoMultiple;
    }

    /**
     * Guarda el documento soporte
     *
     * @param documento
     * @return
     */
    private DocumentoSoporteModeloDTO guardarDocumentoSoporte(DocumentoSoporteModeloDTO documento) {
        logger.debug("Inicia método consultarDocumentoSoporte");
        GuardarDocumentoSoporte service = new GuardarDocumentoSoporte(documento);
        service.execute();
        logger.debug("Finaliza método consultarDocumentoSoporte");
        return service.getResult();
    }

    /**
     * Metodo que invoca al servicio consultarDetalleSolicitudGestionCobro
     *
     * @param filtroDetalleSolicitudGestion recibe de parametro el dto
     * @return una lista de DetalleSolicitudGestionCobroModeloDTO
     */
    private List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleSolicitudGestionCobro(
            List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion) {
        String fimaMetodo = "consultarDetalleSolicitudGestionCobro(List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion)";
        logger.debug("Inicia " + fimaMetodo);
        ConsultarDetalleSolicitudGestionCobro consultarDetalleSolicitudGestionCobro = new ConsultarDetalleSolicitudGestionCobro(
                filtroDetalleSolicitudGestion);
        consultarDetalleSolicitudGestionCobro.execute();
        logger.debug("Finaliza " + fimaMetodo);
        return consultarDetalleSolicitudGestionCobro.getResult();
    }

    /**
     * Método encargado de invocar el servicio que guarda o actualiza una solicitud de gestión de cobro.
     *
     * @param solicitudGestionDTO gestión de cobro a guardar.
     * @return Solicitud guardada o actualizada.
     */
    private SolicitudGestionCobroFisicoModeloDTO guardarSolicitudGestionCobro(SolicitudGestionCobroFisicoModeloDTO solicitudGestionDTO) {
        logger.debug("Inicio de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        GuardarSolicitudGestionCobro guardarSolicitudService = new GuardarSolicitudGestionCobro(solicitudGestionDTO);
        guardarSolicitudService.execute();
        logger.debug("Fin de método guardarSolicitudGestionCobro(SolicitudGestionCobroModeloDTO solicitudGestionDTO)");
        return guardarSolicitudService.getResult();
    }

    /**
     * Método encargado de actualizar los estados de la primera remisión para lo solicitud y los detalles
     *
     * @param detalles              lista de los detalles a actualizar el estado.
     * @param numeroRadicacion      número de la solicitud para actualizarle el estado.
     * @param solicitudGestionCobro si se debe entregar al menos un comunicado, false si no se debe entregar ninguno.
     */
    private Boolean actualizarEstadosPrimeraRemision(List<DetalleSolicitudGestionCobroModeloDTO> detalles, String numeroRadicacion,
                                                     SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobro, TipoActividadBitacoraEnum actividad, String nombreUsuario) {
        logger.info("Inicio de método actualizarEstadosPrimeraRemision");
        Boolean entrega = Boolean.FALSE;
        List<DetalleSolicitudGestionCobroModeloDTO> aportantesExpulsion = new ArrayList<>();
        List<DetalleSolicitudGestionCobroModeloDTO> detallesCerrar = new ArrayList<>();

        for (DetalleSolicitudGestionCobroModeloDTO detalle : detalles) {
            List<DocumentoSoporteModeloDTO> listaDocumentos = new ArrayList<>();
            List<ResultadoBitacoraCarteraEnum> resultados = new ArrayList<>();
            logger.info("actividad" + actividad);
            if (actividad.equals(TipoActividadBitacoraEnum.A2)) {
                resultados.add(ResultadoBitacoraCarteraEnum.ENVIADO);
                resultados.add(ResultadoBitacoraCarteraEnum.EN_PROCESO);
            } else if (actividad.equals(TipoActividadBitacoraEnum.D2)) {
                resultados.add(ResultadoBitacoraCarteraEnum.ENVIADO);
                resultados.add(ResultadoBitacoraCarteraEnum.EXITOSO);
                resultados.add(ResultadoBitacoraCarteraEnum.NO_ENVIADO);
                resultados.add(ResultadoBitacoraCarteraEnum.NO_EXITOSO);
                actividad = TipoActividadBitacoraEnum.GENERAR_LIQUIDACION;
            } else {
                resultados.add(ResultadoBitacoraCarteraEnum.EN_PROCESO);
            }
            if (actividad.equals(TipoActividadBitacoraEnum.LC3A) || actividad.equals(TipoActividadBitacoraEnum.LC2A))
                actividad = TipoActividadBitacoraEnum.NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE;

            logger.info("actividad" + actividad);
            try {
                logger.info("resultados" + new ObjectMapper().writeValueAsString(resultados));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            logger.info("getNumeroOperacion" + detalle.getNumeroOperacion());
            List<BitacoraCarteraDTO> bitacoras = consultarBitacoraSinResultado(detalle.getNumeroOperacion(), actividad, resultados);
            logger.info("actividad2 " + actividad);
            if (detalle.getEnviarPrimeraRemision()) {
                logger.info("Entra en el if enviar");
                /* si al menos alguno se debe enviar se activa la bandera para el estado de la solicitud */
                entrega = true;
                detalle.setEstadoSolicitud(EstadoTareaGestionCobroEnum.COMUNICADO_REMITIDO);
                /* Se agregan los aportantes que cumplan con el envio de primera remision */
                aportantesExpulsion.add(detalle);
                logger.info("antes del for bitacora");
                for (BitacoraCarteraDTO detalleBitacora : bitacoras) {
                    logger.info("Entra en el for bitacora");
                    Long idBitacora = 0L;
                    if (detalleBitacora != null) {
                        logger.info("Entra en el if detalleBitacora");
                        idBitacora = detalleBitacora.getIdBitacoraCartera();
                        listaDocumentos.addAll(detalleBitacora.getDocumentosSoporte());
                    }
                    // TODO: INSERTAR EN MODO MASIVO
                    almacenarBitacoraCartera(idBitacora, null,
                            TipoActividadBitacoraEnum.valueOf(solicitudGestionCobro.getTipoAccionCobro().name()),
                            MedioCarteraEnum.DOCUMENTO_FISICO, ResultadoBitacoraCarteraEnum.ENVIADO, null, detalle.getIdPersona(),
                            detalle.getTipoSolicitante(), listaDocumentos, detalle.getNumeroOperacion().toString(), nombreUsuario);
                }
            } else {
                logger.info("Entra en el else enviar");
                detalle.setEstadoSolicitud(EstadoTareaGestionCobroEnum.PRIMERA_REMISION_COMUNICADO_CANCELADA);

                detallesCerrar.add(detalle);
                for (BitacoraCarteraDTO detalleBitacora : bitacoras) {
                    Long idBitacora = 0L;
                    if (detalleBitacora != null) {
                        idBitacora = detalleBitacora.getIdBitacoraCartera();
                        listaDocumentos.addAll(detalleBitacora.getDocumentosSoporte());
                    }
                    // TODO: INSERTAR EN MODO MASIVO
                    almacenarBitacoraCartera(idBitacora, null,
                            TipoActividadBitacoraEnum.valueOf(solicitudGestionCobro.getTipoAccionCobro().name()),
                            MedioCarteraEnum.DOCUMENTO_FISICO, ResultadoBitacoraCarteraEnum.NO_ENVIADO, null, detalle.getIdPersona(),
                            detalle.getTipoSolicitante(), listaDocumentos, detalle.getNumeroOperacion().toString(), nombreUsuario);
                }
            }
        }
        /* Se actualizan los detalles con el primer estado */
        guardarDetallesSolicitudGestionCobro(detalles, solicitudGestionCobro.getIdSolicitud());

        /* se cambia el estado de los detalles cancelados a cerrados */
        for (DetalleSolicitudGestionCobroModeloDTO detalle : detallesCerrar) {
            detalle.setEstadoSolicitud(EstadoTareaGestionCobroEnum.CERRADA);
        }
        /* se actualizar los detalles que se cerraran */
        guardarDetallesSolicitudGestionCobro(detallesCerrar, solicitudGestionCobro.getIdSolicitud());

        if (entrega) {
            logger.info("Entra en el if entrega");
            /* si se debe entregar al menos uno */
            actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.COMUNICADOS_REMITIDOS_PRIMERA_VEZ);
            actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.PENDIENTE_REGISTRAR_ENTREGA);

            /* contiene las validaciones de la seccion 6.1 para las acciones de cobro 1F y 2H */
            validarSeccion61AccionesCobroDeCierre(numeroRadicacion, aportantesExpulsion, solicitudGestionCobro);
        } else {
            /* si no hay ninguna entrega se debe cancelar y se finaliza el proceso */
            actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.PRIMERA_REMISION_CANCELADA);
            actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.CERRADA);
        }
        logger.debug("Fin de método actualizarEstadosPrimeraRemision(List<DetalleSolicitudGestionCobroModeloDTO> lstDetallesSolicitudes)");
        return entrega;
    }

    /**
     * Servicio encargado de guardar varios detalles de una solicitud de gestión de cobro
     *
     * @param detallesSolicitudesGestion, listado de detalles de solicitud de gestión de cobro a guardar
     */
    private void guardarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudesGestion,
                                                      Long idSolicitudGlobal) {
        logger.debug("Inicia guardarVariosDetalleSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO>)");
        GuardarDetallesSolicitudGestionCobro guardadoDetalleSolicitud = new GuardarDetallesSolicitudGestionCobro(idSolicitudGlobal,
                detallesSolicitudesGestion);
        guardadoDetalleSolicitud.execute();
        logger.debug("Inicia guardarVariosDetalleSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO>)");
    }

    /**
     * Servicio encargado de guardar varios detalles de una solicitud de gestión de cobro
     *
     * @param detallesSolicitudesGestion, listado de detalles de solicitud de gestión de cobro a guardar
     */
    private void actualizarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudesGestion) {
        logger.debug("Inicia actualizarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO>)");
        ActualizarDetallesSolicitudGestionCobro actualizadorDetalleSolicitud = new ActualizarDetallesSolicitudGestionCobro(detallesSolicitudesGestion);
        actualizadorDetalleSolicitud.execute();
        logger.debug("Inicia actualizarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO>)");
    }


    /**
     * Servicio que consulta la bitacora sin resultado dado un número de operación, es para el caso que es físico el metodo de envió en la
     * acción de cobro y no se ha realizado su respectiva gestión
     *
     * @param numeroOperacion Numero de operacion relacionado a la cartera y bitacora
     * @return Lista de bitacoras
     */
    private List<BitacoraCarteraDTO> consultarBitacoraSinResultado(Long numeroOperacion, TipoActividadBitacoraEnum actividad, List<ResultadoBitacoraCarteraEnum> resultados) {
        logger.debug("Inicio de método consultarSolicitudPorRadicado");
        ConsultarBitacoraSinResultado bitacora = new ConsultarBitacoraSinResultado(numeroOperacion, actividad, resultados);
        bitacora.execute();
        logger.debug("Fin de método consultarSolicitudPorRadicado");
        return bitacora.getResult();
    }

    /**
     * Método que prepara el registro de bitácora a ser almacenado
     *
     * @param idBitacoraCartera Identificador del registro en bitácora
     * @param fecha             Fecha de registro
     * @param actividad         Actividad
     * @param medio             Medio o canal
     * @param resultado         Resultado
     * @param usuario           Usuario que registró
     * @param idPersona         Identificador de la persona aportante
     * @param tipoSolicitante   Tipo de aportante
     * @param documentosSoporte Lista de documentos a agregar en la traza
     * @param numeroOperacion   Número de operación
     */
    private void almacenarBitacoraCartera(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio,
                                          ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                          List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, String nombreUsuario) {
        try {
            logger.debug("Inicio de método almacenarBitacoraCartera");

            BitacoraCarteraDTO bitacora = crearBitacoraCarteraDTO(idBitacoraCartera, fecha, actividad, medio, resultado,
                    usuario, idPersona, tipoSolicitante, documentosSoporte, numeroOperacion, null);
            bitacora.setUsuario(nombreUsuario);
            guardarBitacoraCartera(bitacora);
            logger.debug("Fin de método almacenarBitacoraCartera");
        } catch (Exception e) {
            logger.error("Ocurrió un error en almacenarBitacoraCartera", e);
        }
    }

    /**
     * Metodo que contiene las validaciones de la seccion 6.1 para las acciones de cobro 1F y 2H
     *
     * @param numeroRadicacion    representa a la solicitud asociada
     * @param aportantesExpulsion lista de aportantes para validar si son candidatos a expulasar o no
     */
    private void validarSeccion61AccionesCobroDeCierre(String numeroRadicacion,
                                                       List<DetalleSolicitudGestionCobroModeloDTO> aportantesExpulsion, SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobro) {
        /* Inicio de validaciones de punto 6.1 de HU 1F */
        if (solicitudGestionCobro != null) {
            if (TipoAccionCobroEnum.F1.equals(solicitudGestionCobro.getTipoAccionCobro())
                    || TipoAccionCobroEnum.H2.equals(solicitudGestionCobro.getTipoAccionCobro())
                    || TipoAccionCobroEnum.LC4C.equals(solicitudGestionCobro.getTipoAccionCobro())
                    || TipoAccionCobroEnum.LC5C.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                /* Se valida que la accion de cobro F1 o H2 */
                TipoParametrizacionGestionCobroEnum tipoParametrizacionGestionCobroEnum = null;
                TipoLineaCobroEnum lineaCobro = null;
                switch (solicitudGestionCobro.getTipoAccionCobro()) {
                    case F1:
                        /* Se consulta la paremetrizacion para accion de cobro 1F */
                        tipoParametrizacionGestionCobroEnum = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F;
                        break;
                    case H2:
                        /* Se consulta la paremetrizacion para accion de cobro 2H */
                        tipoParametrizacionGestionCobroEnum = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2H;
                        break;
                    case LC4C:
                        /* Se consulta la paremetrizacion para accion de cobro LC4C */
                        lineaCobro = TipoLineaCobroEnum.LC4;
                        break;
                    case LC5C:
                        /* Se consulta la paremetrizacion para accion de cobro LC5C */
                        lineaCobro = TipoLineaCobroEnum.LC5;
                        break;
                    default:
                        break;
                }

                if (TipoAccionCobroEnum.F1.equals(solicitudGestionCobro.getTipoAccionCobro())
                        || TipoAccionCobroEnum.H2.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                    /* Se obtiene la parametrizacion ya sea 1F o 2H */
                    List<Object> parametrizacion = consultarParametrizacionGestionCobro(tipoParametrizacionGestionCobroEnum);
                    if (parametrizacion != null && !parametrizacion.isEmpty()) {
                        Object object = parametrizacion.get(0);
                        Map<String, Object> accion = (Map<String, Object>) object;
                        if (AccionCarteraEnum.RESULTADO_ENVIO_COMUNICADO.equals(
                                AccionCarteraEnum.valueOf((String) accion.get("siguienteAccion"))) && !aportantesExpulsion.isEmpty()) {

                            /* Se invoca metodo comun */
                            validarCandidatoAExpulsar(aportantesExpulsion, solicitudGestionCobro.getTipoAccionCobro());
                        }
                    }
                } else if (TipoAccionCobroEnum.LC4C.equals(solicitudGestionCobro.getTipoAccionCobro())
                        || TipoAccionCobroEnum.LC5C.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                    /* Se obtiene la parametrizacion ya sea LC4C o LC5C */
                    ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO = consultarParametrizacionDesafiliacion(
                            lineaCobro);
                    /* Se valida que la parametrizacion no sea null */
                    if (parametrizacionDesafiliacionModeloDTO != null && AccionCarteraEnum.RESULTADO_ENVIO_COMUNICADO
                            .equals(parametrizacionDesafiliacionModeloDTO.getSiguienteAccion()) && !aportantesExpulsion.isEmpty()) {
                        /* Se invoca metodo comun */
                        validarCandidatoAExpulsar(aportantesExpulsion, solicitudGestionCobro.getTipoAccionCobro());
                    }
                }
            }
        }
    }

    /**
     * Método encargado de llamar el cliente del servicio que guarda la bitacora de cartera
     *
     * @param bitacoraCartera, bitacora de cartera a guardar
     */
    private void guardarBitacoraCartera(BitacoraCarteraDTO bitacoraCartera) {
        logger.debug("Inicio de método guardarBitacoraCartera(BitacoraCarteraDTO)");
        GuardarBitacoraCartera bitacora = new GuardarBitacoraCartera(bitacoraCartera);
        bitacora.execute();
        logger.debug("Finaliza método guardarBitacoraCartera(BitacoraCarteraDTO)");
    }

    /**
     * Método que invoca el servicio de consulta de parametrización de acciones de cobro
     *
     * @param tipoParametrizacion Tipo de parametrización
     * @return Lista de registros de parametrización de la acción de cobro
     */
    private List<Object> consultarParametrizacionGestionCobro(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        logger.debug("Inicio de método consultarParametrizacionGestionCobro");
        ConsultarParametrizacionGestionCobro service = new ConsultarParametrizacionGestionCobro(tipoParametrizacion);
        service.execute();
        logger.debug("Fin de método consultarParametrizacionGestionCobro");
        return service.getResult();
    }

    /**
     * Metodo que sirve para validar que aportantes van a la desafiliacion de la HU 197 o quedan con marca de suspendidos
     *
     * @param detallesCanditadosExpulsion los aportantes que se van a identificar si van a proceso de desafiliacione
     */
    private void validarCandidatoAExpulsar(List<DetalleSolicitudGestionCobroModeloDTO> detallesCanditadosExpulsion,
                                           TipoAccionCobroEnum tipoAccionCobro) {

        /* Se llama la consulta */
        Boolean validarNoAprobados = false;

        List<DetalleSolicitudGestionCobroModeloDTO> aportantesValidacionesAprobadas = consultarCanditatosExpulsion(validarNoAprobados,
                tipoAccionCobro, detallesCanditadosExpulsion);

        /* Aportantes que no superaron las validaciones del c,d,e del punto 6 de HU 1F,2H */
        List<DetalleSolicitudGestionCobroModeloDTO> aportantesValidacionesNoAprobadas = new ArrayList<>();
        /* Se realiza una copia de la lista original de aportantes hacia la lista de los que no superaron las validaciones */
        aportantesValidacionesNoAprobadas.addAll(detallesCanditadosExpulsion);

        List<RolAfiliadoEmpleadorDTO> rolAfiliadoEmpleadorDTOs;

        /* Si hay aportantes aprobados */
        if (aportantesValidacionesAprobadas != null && !aportantesValidacionesAprobadas.isEmpty()) {

            for (DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroAprobados : aportantesValidacionesAprobadas) {

                /* Se recorre para que queden solo los que no superaron las validaciones */
                for (DetalleSolicitudGestionCobroModeloDTO canditatosASuspender : detallesCanditadosExpulsion) {
                    if (detalleSolicitudGestionCobroAprobados.getNumeroIdentificacion()
                            .equals(canditatosASuspender.getNumeroIdentificacion())
                            && aportantesValidacionesNoAprobadas.contains(canditatosASuspender)) {
                        aportantesValidacionesNoAprobadas.remove(canditatosASuspender);
                    }
                    validarNoAprobados = true;
                }

                if (TipoAccionCobroEnum.F1.equals(tipoAccionCobro) || TipoAccionCobroEnum.H2.equals(tipoAccionCobro)) {
                    /* Se consulta el empleador para asignar la marca */
                    EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(
                            detalleSolicitudGestionCobroAprobados.getTipoIdentificacion(),
                            detalleSolicitudGestionCobroAprobados.getNumeroIdentificacion());
                    if (empleadorModeloDTO != null) {
                        empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                        /* Se actualiza el empleador */
                        guardarDatosEmpleador(empleadorModeloDTO);
                    }

                } else if (TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro) || TipoAccionCobroEnum.LC5C.equals(tipoAccionCobro)) {
                    /* Se consultan los roles */
                    rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(detalleSolicitudGestionCobroAprobados.getTipoIdentificacion(),
                            detalleSolicitudGestionCobroAprobados.getNumeroIdentificacion(),
                            TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro) ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE
                                    : TipoAfiliadoEnum.PENSIONADO);

                    /* Se valida que hay datos */
                    if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                        /* Se consulta el afiliado */
                        RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                                rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                        if (rolAfiliadoDTO != null) {
                            rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.CANDIDATA_A_EXPULSAR);
                            /* Se actualiza el afiliado */
                            actualizarRolAfiliado(rolAfiliadoDTO);
                        }
                    }
                }
            }

            if (aportantesValidacionesNoAprobadas != null && !aportantesValidacionesNoAprobadas.isEmpty()) {

                aportantesValidacionesNoAprobadas = consultarCanditatosExpulsion(validarNoAprobados, tipoAccionCobro,
                        aportantesValidacionesNoAprobadas);

                if (aportantesValidacionesNoAprobadas != null && !aportantesValidacionesNoAprobadas.isEmpty()) {
                    for (DetalleSolicitudGestionCobroModeloDTO noAprobadas : aportantesValidacionesNoAprobadas) {

                        if (TipoAccionCobroEnum.F1.equals(tipoAccionCobro) || TipoAccionCobroEnum.H2.equals(tipoAccionCobro)) {
                            /* Se consulta el empleador para asignar la marca */
                            EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(noAprobadas.getTipoIdentificacion(),
                                    noAprobadas.getNumeroIdentificacion());
                            if (empleadorModeloDTO != null) {
                                empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.SUSPENDIDA);
                                /* Se actualiza el empleador */
                                guardarDatosEmpleador(empleadorModeloDTO);
                            }
                        } else if (TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro) || TipoAccionCobroEnum.LC5C.equals(tipoAccionCobro)) {
                            /* Se consultan los roles */
                            rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(noAprobadas.getTipoIdentificacion(),
                                    noAprobadas.getNumeroIdentificacion(), TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro)
                                            ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE : TipoAfiliadoEnum.PENSIONADO);

                            /* Se valida que hay datos */
                            if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                                /* Se consulta el afiliado */
                                RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                                        rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                                if (rolAfiliadoDTO != null) {
                                    rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.SUSPENDIDA);
                                    /* Se actualiza el afiliado */
                                    actualizarRolAfiliado(rolAfiliadoDTO);
                                }
                            }
                        }
                    }
                }
            }

        } else {
            /*
             * Como llego vacia quiere decir que todos los aportantes que llegan al servicio
             * no superaron las validaciones
             */
            validarNoAprobados = true;
            aportantesValidacionesNoAprobadas = consultarCanditatosExpulsion(validarNoAprobados, tipoAccionCobro,
                    aportantesValidacionesNoAprobadas);

            if (aportantesValidacionesNoAprobadas != null && !aportantesValidacionesNoAprobadas.isEmpty()) {

                for (DetalleSolicitudGestionCobroModeloDTO noAprobadas : aportantesValidacionesNoAprobadas) {

                    if (TipoAccionCobroEnum.F1.equals(tipoAccionCobro) || TipoAccionCobroEnum.H2.equals(tipoAccionCobro)) {
                        /* Se consulta el empleador para asignar la marca */
                        EmpleadorModeloDTO empleadorModeloDTO = consultarEmpleadorTipoNumero(noAprobadas.getTipoIdentificacion(),
                                noAprobadas.getNumeroIdentificacion());
                        if (empleadorModeloDTO != null) {
                            empleadorModeloDTO.setMarcaExpulsion(ExpulsionEnum.SUSPENDIDA);
                            /* Se actualiza el empleador */
                            guardarDatosEmpleador(empleadorModeloDTO);
                        }
                    } else if (TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro) || TipoAccionCobroEnum.LC5C.equals(tipoAccionCobro)) {
                        /* Se consultan los roles */
                        rolAfiliadoEmpleadorDTOs = consultarRolesAfiliado(noAprobadas.getTipoIdentificacion(),
                                noAprobadas.getNumeroIdentificacion(), TipoAccionCobroEnum.LC4C.equals(tipoAccionCobro)
                                        ? TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE : TipoAfiliadoEnum.PENSIONADO);

                        /* Se valida que hay datos */
                        if (rolAfiliadoEmpleadorDTOs != null && !rolAfiliadoEmpleadorDTOs.isEmpty()) {
                            /* Se consulta el afiliado */
                            RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado(
                                    rolAfiliadoEmpleadorDTOs.get(0).getRolAfiliado().getIdRolAfiliado());
                            if (rolAfiliadoDTO != null) {
                                rolAfiliadoDTO.setMarcaExpulsion(ExpulsionEnum.SUSPENDIDA);
                                /* Se actualiza el afiliado */
                                actualizarRolAfiliado(rolAfiliadoDTO);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo que invoca al servicio que consulta la parametrizacion de desafiliacion
     *
     * @param lineaCobro parametro por el cual se consulta la parametrizacion de desafiliacion
     * @return un DTO de la parametrizacion de desafiliacion
     */
    private ParametrizacionDesafiliacionModeloDTO consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro) {
        String firmaMetodo = "consultarParametrizacionDesafiliacion(TipoLineaCobroEnum lineaCobro)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ConsultarParametrizacionDesafiliacion consultarParametrizacionDesafiliacion = new ConsultarParametrizacionDesafiliacion(lineaCobro);
        consultarParametrizacionDesafiliacion.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
        return consultarParametrizacionDesafiliacion.getResult();
    }

    /**
     * Método encargado de llamar el cliente del servicio que consulta candidatos de expulsión
     *
     * @param validacionExclusion, bandera que se encarga de definir si se realiza consulta de exclusiones
     * @param tipoAccionCobro,     tipo de accion de cobro
     * @param lstDetalles,         lista de detalles de solicitud de gestión de cobro
     * @return retorna la lista de solicitudes de gestión
     */
    private List<DetalleSolicitudGestionCobroModeloDTO> consultarCanditatosExpulsion(Boolean validacionExclusion,
                                                                                     TipoAccionCobroEnum tipoAccionCobro, List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles) {
        logger.debug("Inicia consultarCanditatosExpulsion(Boolean,List<DetalleSolicitudGestionCobroModeloDTO>)");
        ConsultarCanditatosExpulsion exclusionCartera = new ConsultarCanditatosExpulsion(tipoAccionCobro, validacionExclusion, lstDetalles);
        exclusionCartera.execute();
        logger.debug("Finaliza consultarCanditatosExpulsion(Boolean,List<DetalleSolicitudGestionCobroModeloDTO>)");
        return exclusionCartera.getResult();
    }

    /**
     * Metodo que sirve para consultar la informacion del empleador por tipo y numero identificacion
     *
     * @param tipoIdentificacion   recibe como parametro el tipo de identificacion del empleador
     * @param numeroIdentificacion recibe como parametro el numero de identificacion del empleador
     * @return
     */
    private EmpleadorModeloDTO consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        String firmaMetodo = "consultarEmpleadorTipoNumero(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)";
        logger.debug("Inicia " + firmaMetodo);
        ConsultarEmpleadorTipoNumero consultarEmpleadorTipoNumero = new ConsultarEmpleadorTipoNumero(numeroIdentificacion,
                tipoIdentificacion);
        consultarEmpleadorTipoNumero.execute();
        logger.debug("Finaliza " + firmaMetodo);
        return consultarEmpleadorTipoNumero.getResult();
    }

    /**
     * Metodo que invoca al servicio de guardar datos del empleador
     *
     * @param empleadorModeloDTO recibe la informacion a guardar del empleador
     */
    private void guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO) {
        String firmaMetodo = "guardarDatosEmpleador(EmpleadorModeloDTO empleadorModeloDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        GuardarDatosEmpleador guardarDatosEmpleador = new GuardarDatosEmpleador(empleadorModeloDTO);
        logger.debug("Finaliza metodo" + firmaMetodo);
        guardarDatosEmpleador.execute();
    }

    /**
     * Metodo que sirve para consultar la informacion del afiliado
     *
     * @param tipoIdentificacion   recibe como parametro el tipo de identificacion del afiliado
     * @param numeroIdentificacion recibe como parametro el numero de identificacion del afiliado
     * @param tipoAfiliado         recibe como parametro el tipo de afilado
     * @return
     */
    private List<RolAfiliadoEmpleadorDTO> consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                                 TipoAfiliadoEnum tipoAfiliado) {
        String firmaMetodo = "consultarRolesAfiliado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoAfiliadoEnum tipoAfiliado)";
        logger.debug("Inicia " + firmaMetodo);
        ConsultarRolesAfiliado consultarRolesAfiliado = new ConsultarRolesAfiliado(tipoAfiliado, numeroIdentificacion, tipoIdentificacion);
        consultarRolesAfiliado.execute();
        logger.debug("Finaliza " + firmaMetodo);
        return consultarRolesAfiliado.getResult();
    }

    /**
     * Metodo que invoca al servicio ConsultarRolAfiliado
     *
     * @param idRolAfiliado insumo por el cual va hacer consultado el afiliado
     * @return un objeto con la informacion del afiliado
     */
    private RolAfiliadoModeloDTO consultarRolAfiliado(Long idRolAfiliado) {
        logger.debug("Inicio de método ConsultarRolAfiliado (Long idRolAfiliado)");
        ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(idRolAfiliado);
        consultarRolAfiliado.execute();
        logger.debug("Finaliza de método ConsultarRolAfiliado (Long idRolAfiliado)");
        return consultarRolAfiliado.getResult();
    }

    /**
     * Metodo que invoca el servicio de actualizarRolAfiliado
     *
     * @param rolAfiliadoModeloDTO recibe la informacion del afiliado
     */
    private void actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO) {
        String firmaMetodo = "actualizarRolAfiliado(RolAfiliadoModeloDTO rolAfiliadoModeloDTO)";
        logger.debug("Inicia metodo" + firmaMetodo);
        ActualizarRolAfiliado actualizarRolAfiliado = new ActualizarRolAfiliado(rolAfiliadoModeloDTO);
        actualizarRolAfiliado.execute();
        logger.debug("Finaliza metodo" + firmaMetodo);
    }

    /**
     * @param numeroRadicacion
     * @return
     */
    private SolicitudModeloDTO consultarSolicitudPorRadicado(String numeroRadicacion) {
        logger.debug("Inicio de método consultarSolicitudPorRadicado");
        ConsultarSolicitudPorRadicado solicitud = new ConsultarSolicitudPorRadicado(numeroRadicacion);
        solicitud.execute();
        logger.debug("Fin de método consultarSolicitudPorRadicado");
        return solicitud.getResult();
    }

    /**
     * Método encargado de construir un archivo de documento de soporte
     *
     * @param documento,          documento ya existente
     * @param identificadorECM,   identificador del ECM perteneciente al archivo nuevo
     * @param nombreArchivo,      Nombre del archivo
     * @param descripcionArchivo, Descripción del archivo
     * @return retorna el archivo de documento soporte dto ya construido
     */
    private DocumentoSoporteModeloDTO construirDocumentoSoporte(DocumentoSoporteModeloDTO documento, String identificadorECM,
                                                                String nombreArchivo, String descripcionArchivo) {
        String separador = "_";
        DocumentoSoporteModeloDTO documentoSoporte = null;
        if (identificadorECM != null && !identificadorECM.equals("") && documento == null) {
            documentoSoporte = new DocumentoSoporteModeloDTO();
            documentoSoporte.setNombre(nombreArchivo);
            documentoSoporte.setDescripcionComentarios(descripcionArchivo);
            documentoSoporte.setFechaHoraCargue((new Date()).getTime());
            String[] split = identificadorECM.split(separador);
            documentoSoporte.setIdentificacionDocumento(split[0]);
            documentoSoporte.setVersionDocumento(split[1]);
        } else if (documento != null && (identificadorECM != null && !identificadorECM.equals(""))) {
            documentoSoporte = documento;
            String[] split = identificadorECM.split(separador);
            documentoSoporte.setIdentificacionDocumento(split[0]);
            documentoSoporte.setVersionDocumento(split[1]);
        }
        return documentoSoporte;
    }

    /**
     * Servicio encargado de consultar a una persona por tipo y número de identificación.
     *
     * @param tipoIdentificacion   tipo de identificación.
     * @param numeroIdentificacion número de identificación.
     * @return persona encontrada.
     */
    private PersonaModeloDTO consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        logger.debug("Inicio de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        ConsultarDatosPersona consultarDatosService = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarDatosService.execute();
        PersonaModeloDTO personaModeloDTO = consultarDatosService.getResult();
        logger.debug("Fin de método consultarDatosPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion)");
        return personaModeloDTO;
    }

    /**
     * Método que consulta el registro de cartera asociado a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación
     * @param numeroIdentificacion Número de identificación
     * @param tipoSolicitante      Tipo de aportante
     * @param tipoLineaCobro       Línea de cobro
     * @param periodoMillis        Periodo de deuda a consulta
     * @return La información del registro en cartera
     */
    private CarteraModeloDTO obtenerInformacionCartera(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
                                                       TipoSolicitanteMovimientoAporteEnum tipoSolicitante, TipoLineaCobroEnum tipoLineaCobro, Long periodoMillis) {
        try {
            logger.debug("Inicio de método obtenerInformacionCartera");
            List<CarteraModeloDTO> listaCartera = consultarPeriodosAportanteLineaCobro(tipoIdentificacion, numeroIdentificacion,
                    tipoSolicitante, tipoLineaCobro);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

            if (periodoMillis != null) {
                String periodo = dateFormat.format(new Date(periodoMillis));

                for (CarteraModeloDTO registro : listaCartera) {
                    if (periodo.equals(dateFormat.format(new Date(registro.getPeriodoDeuda())))) {
                        logger.debug("Fin de método obtenerInformacionCartera");
                        return registro;
                    }
                }
            }

            if (!listaCartera.isEmpty()) {
                return listaCartera.get(0);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error en obtenerInformacionCartera", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        return null;
    }

    /**
     * Método que obtiene el número de operación de cartera a partir de un identificador del periodo en mora
     *
     * @param idCartera Identificador de cartera / periodo en mora
     * @return El número de operación
     */
    private Long consultarNumeroOperacionCartera(Long idCartera) {
        logger.debug("Inicia método consultarNumeroOperacionCartera");
        ConsultarNumeroOperacionCartera service = new ConsultarNumeroOperacionCartera(idCartera);
        service.execute();
        logger.debug("Finaliza método consultarNumeroOperacionCartera");
        return service.getResult();
    }

    /**
     * Método encargado de finalizar los resultados de la primera remisión
     *
     * @param numeroRadicacion,       número de radicación de la solicitud
     * @param idTarea,                identificador de la tarea
     * @param detallesExitosoNoExito, listado de detalles exitosos y no exitosos
     */
    private void finalizarResultadosPrimeraRemision(String numeroRadicacion, Long idTarea,
                                                    List<DetalleSolicitudGestionCobroModeloDTO> detallesExitosoNoExito, UserDTO userDTO) {
        logger.debug("Inicio de método finalizarResultadosPrimeraRemision");
        /* si se puede finalizar es porque no hay ninguna pendiente y tiene un estado CERRADA O PENDIENTE DE ACTUALIZACIÓN */
        actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.RESULTADOS_DE_PRIMERA_REMISION_REGISTRADOS);
        actualizarEstadoSolicitudGestionCobro(numeroRadicacion, EstadoSolicitudGestionCobroEnum.CERRADA);
        SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobro = consultarSolicitudGestionCobro(numeroRadicacion);
        if (solicitudGestionCobro != null && solicitudGestionCobro.getTipoAccionCobro() != null
                && (TipoAccionCobroEnum.F1.equals(solicitudGestionCobro.getTipoAccionCobro())
                || TipoAccionCobroEnum.H2.equals(solicitudGestionCobro.getTipoAccionCobro())
                || TipoAccionCobroEnum.LC4C.equals(solicitudGestionCobro.getTipoAccionCobro())
                || TipoAccionCobroEnum.LC5C.equals(solicitudGestionCobro.getTipoAccionCobro()))) {
            boolean accionLineaCobro = false;
            TipoLineaCobroEnum lineaCobro = null;
            TipoParametrizacionGestionCobroEnum tipoParametrizacion = null;
            if (TipoAccionCobroEnum.F1.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_1F;
                accionLineaCobro = true;
            }
            if (TipoAccionCobroEnum.H2.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                tipoParametrizacion = TipoParametrizacionGestionCobroEnum.ACCION_COBRO_2H;
                accionLineaCobro = true;
            }
            if (TipoAccionCobroEnum.LC4C.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                lineaCobro = TipoLineaCobroEnum.LC4;
                accionLineaCobro = false;
            }
            if (TipoAccionCobroEnum.LC5C.equals(solicitudGestionCobro.getTipoAccionCobro())) {
                lineaCobro = TipoLineaCobroEnum.LC5;
                accionLineaCobro = false;
            }
            if (accionLineaCobro) {
                List<Object> accionesCobro = consultarParametrizacionGestionCobro(tipoParametrizacion);
                AccionCarteraEnum siguienteAccion = null;
                if (!accionesCobro.isEmpty()) {
                    Map<Object, Object> mapAccionCobro = (Map<Object, Object>) accionesCobro.get(0);
                    siguienteAccion = AccionCarteraEnum.valueOf((String) mapAccionCobro.get("siguienteAccion"));
                }
                if (AccionCarteraEnum.REGISTRO_RECEPCION_COMUNICADO.equals(siguienteAccion) && !detallesExitosoNoExito.isEmpty()) {
                    validarCandidatoAExpulsar(detallesExitosoNoExito, solicitudGestionCobro.getTipoAccionCobro());
                }
            } else {
                /* Se obtiene la parametrizacion ya sea LC4C o LC5C */
                ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO = consultarParametrizacionDesafiliacion(
                        lineaCobro);
                /* Se valida que la parametrizacion no sea null */
                if (parametrizacionDesafiliacionModeloDTO != null
                        && (AccionCarteraEnum.RESULTADO_ENVIO_COMUNICADO.equals(parametrizacionDesafiliacionModeloDTO.getSiguienteAccion())
                        || AccionCarteraEnum.REGISTRO_RECEPCION_COMUNICADO
                        .equals(parametrizacionDesafiliacionModeloDTO.getSiguienteAccion()))) {
                    /* Se invoca metodo comun */
                    validarCandidatoAExpulsar(detallesExitosoNoExito, solicitudGestionCobro.getTipoAccionCobro());
                }
            }
        }

        //Se retoma la tarea al estado previo a la suspensión, para luego cerrarse
        RetomarTarea retomarTareaService = new RetomarTarea(idTarea, new HashMap<>());
        retomarTareaService.setToken(userDTO.getToken());
        retomarTareaService.execute();

        TerminarTarea terminarTarea = new TerminarTarea(idTarea, new HashMap<>());
        terminarTarea.setToken(userDTO.getToken());
        terminarTarea.execute();

        logger.debug("Fin de método finalizarResultadosPrimeraRemision");
    }

    /**
     * Método que invoca el servicio de consulta de registros en cartera vigente, por aportante y línea de cobro
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoAportante        Tipo de aportante
     * @param tipoLineaCobro       Línea de cobro
     * @return La lista de registros en cartera, para el aportante
     */
    private List<CarteraModeloDTO> consultarPeriodosAportanteLineaCobro(TipoIdentificacionEnum tipoIdentificacion,
                                                                        String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante, TipoLineaCobroEnum tipoLineaCobro) {
        logger.debug("Inicia método consultarEmpleadorNumero");
        ConsultarPeriodosAportanteLineaCobro service = new ConsultarPeriodosAportanteLineaCobro(tipoAportante, tipoLineaCobro,
                numeroIdentificacion, tipoIdentificacion);
        service.execute();
        logger.debug("Finaliza método consultarEmpleadorNumero");
        return service.getResult();
    }
}
