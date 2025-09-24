
package com.asopagos.subsidiomonetario.composite.service.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.NotAuthorizedException;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidaddescuento.clients.ActualizarArchivosDescuentoLiquidacionCancelada;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.BPMSExecutionException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.clients.ActualizarEstadoSolicitudLiquidacion;
import com.asopagos.subsidiomonetario.clients.ConsultarIdSolicitud;
import com.asopagos.subsidiomonetario.clients.GestionarProcesoEliminacion;
import com.asopagos.subsidiomonetario.clients.RechazarLiquidacionMasivaPrimerNivel;
import com.asopagos.subsidiomonetario.clients.RechazarLiquidacionMasivaSegundoNivel;
import com.asopagos.subsidiomonetario.composite.constants.SubsidioMonetarioCompositeConstants;
import com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioAsincronoCompositeService;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ObtenerTokenAcceso;
import com.asopagos.usuarios.clients.ObtenerTokenAccesoV2;

/**
 * <b>Descripción: Clase que implementa los servicios de composicion para Subsidio Monetario</b>
 *
 * 
 * @author <a href="flopez@heinsohn.com.co> Fabian López</a>
 */
@Stateless
public class SubsidioMonetarioAsincronoCompositeBusiness implements SubsidioMonetarioAsincronoCompositeService {


    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(SubsidioMonetarioAsincronoCompositeBusiness.class);

    private boolean marcaProceso = true;
    
  
    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionMasivaPrimerNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.composite.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void rechazarLiquidacionMasivaPrimerNivelAsync(String numeroSolicitud, String idTarea,
                                                          AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, String profile) {

        String firmaMetodo = "ConsultasModeloCore.rechazarLiquidacionMasivaPrimerNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        //seguimiento radicado undefined
        logger.info("String numeroSolicitud: " + numeroSolicitud + " - seguimiento undefined");
        marcaProceso = true;

            try {
                Map<String, Object> params = new HashMap<>();
                params.put(SubsidioMonetarioCompositeConstants.APROBADO_PRIMER_NIVEL, SubsidioMonetarioCompositeConstants.RECHAZO);

                ObtenerTokenAccesoV2 obtenerTokenSrv = new ObtenerTokenAccesoV2(profile);
                obtenerTokenSrv.execute();
                logger.info("Termina obtenerTokenSrv: " + obtenerTokenSrv.toString());
                logger.info("getResult obtenerTokenSrv: " + obtenerTokenSrv.getResult());

                TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                terminarTarea.setToken(obtenerTokenSrv.getResult());
                terminarTarea.execute();
                logger.info("Termina terminarTarea: " + terminarTarea.toString());
            }
            catch (Exception e) {
                //actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                //actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
                //actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
                marcaProceso = false;

                throw new BPMSExecutionException(MensajesGeneralConstants.ERROR_INSTANCIA_PROCESO_BPM_ESTADO_INCONSISTENTE, e);
            }

            logger.info("*** ___ **** MarcaProceso " + marcaProceso);

    if (marcaProceso) {
        if (aprobacionRechazoSubsidioMonetarioDTO != null) {
            if (aprobacionRechazoSubsidioMonetarioDTO.getRazonRechazo() != null
                    && aprobacionRechazoSubsidioMonetarioDTO.getObservaciones() != null) {
                //Se asocia el nuevo Proceso de Eliminación.
                GestionarProcesoEliminacion gestionEliminacion = new GestionarProcesoEliminacion(numeroSolicitud);
                gestionEliminacion.execute();
                logger.info("Termina gestionEliminacion: " + numeroSolicitud);
                try {
                    logger.info("String numeroSolicitud: " + numeroSolicitud + " - Proceso de eliminación Primera Vez - GLPI 51093");
                    rechazarLiquidacionMasivaPrimerNivel(numeroSolicitud, aprobacionRechazoSubsidioMonetarioDTO);

                    logger.info("Termina rechazarLiquidacionMasivaPrimerNivel: " + numeroSolicitud);


                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

                    logger.info("Termina idSolicitudLiquidacion: " + idSolicitudLiquidacion);

                    actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
                } catch (Exception e) {
                    logger.info("String numeroSolicitud: " + numeroSolicitud + " - Ocurrio un error en el procesamiento de la elminiaciópn, se volverá a lanzar el proceso");
                    rechazarLiquidacionMasivaPrimerNivel(numeroSolicitud, aprobacionRechazoSubsidioMonetarioDTO);

                    logger.info("Termina rechazarLiquidacionMasivaPrimerNivel_SegundoIntento: " + numeroSolicitud);


                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

                    logger.info("Termina idSolicitudLiquidacion: " + idSolicitudLiquidacion);

                    actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_PRIMER_NIVEL);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);
                }
            } else {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
        } else {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }
    }

        logger.info("Termina Metodo rechazo de liquidaciones Primer Nivel: " + numeroSolicitud);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.composite.service.SubsidioMonetarioCompositeService#rechazarLiquidacionMasivaSegundoNivel(java.lang.String,
     *      com.asopagos.subsidiomonetario.composite.dto.AprobacionRechazoSubsidioMonetarioDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    @Asynchronous
    public void rechazarLiquidacionMasivaSegundoNivelAsync(String numeroSolicitud, String idTarea,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, UserDTO userDTO, String profile) {

        String firmaMetodo = "ConsultasModeloCore.rechazarLiquidacionMasivaSegundoNivel(String, AprobacionRechazoSubsidioMonetarioDTO, UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (aprobacionRechazoSubsidioMonetarioDTO != null) {
            if (aprobacionRechazoSubsidioMonetarioDTO.getRazonRechazo() != null
                    && aprobacionRechazoSubsidioMonetarioDTO.getObservaciones() != null) {
                //Se asocia el nuevo Proceso de Eliminación.
                GestionarProcesoEliminacion gestionEliminacion = new GestionarProcesoEliminacion(numeroSolicitud);
                gestionEliminacion.execute();
                logger.info("Termina gestionEliminacion: " + numeroSolicitud);
                try {
                    logger.info("String numeroSolicitud: " + numeroSolicitud + " - Proceso de eliminación Primera Vez - GLPI 51093");

                    SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = rechazarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                            aprobacionRechazoSubsidioMonetarioDTO, Boolean.TRUE);

                    logger.info("Termina rechazarLiquidacionMasivaSegundoNivel: " + numeroSolicitud);


                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

                    logger.info("Termina idSolicitudLiquidacion: " + idSolicitudLiquidacion);

                    actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);

                    Map<String, Object> params = new HashMap<>();
                    params.put(SubsidioMonetarioCompositeConstants.APROBRADO_SEGUNDO_NIVEL, SubsidioMonetarioCompositeConstants.RECHAZO);
                    params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacion.getUsuarioEvaluacionPrimerNivel());

                    ObtenerTokenAccesoV2 obtenerTokenSrv = new ObtenerTokenAccesoV2(profile);
                    obtenerTokenSrv.execute();
                    logger.info("Termina obtenerTokenSrv: " + obtenerTokenSrv.toString());

                    TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                    terminarTarea.setToken(obtenerTokenSrv.getResult());
                    terminarTarea.execute();
                    logger.info("Termina terminarTarea: " + terminarTarea.toString());

                } catch (Exception e) {
                    logger.info("String numeroSolicitud: " + numeroSolicitud + " - Ocurrio un error en el procesamiento de la elminiaciópn, se volverá a lanzar el proceso");
                    SolicitudLiquidacionSubsidioModeloDTO solicitudLiquidacion = rechazarLiquidacionMasivaSegundoNivel(numeroSolicitud,
                            aprobacionRechazoSubsidioMonetarioDTO, Boolean.TRUE);

                    logger.info("Termina rechazarLiquidacionMasivaSegundoNivel: " + numeroSolicitud);


                    Long idSolicitudLiquidacion = consultarIdSolicitudLiquidacion(numeroSolicitud);

                    logger.info("Termina idSolicitudLiquidacion: " + idSolicitudLiquidacion);

                    actualizarArchivosDescuentoLiquidacionCancelada(numeroSolicitud);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.RECHAZADA_SEGUNDO_NIVEL);
                    actualizarEstadoSolicitudLiquidacion(idSolicitudLiquidacion, EstadoProcesoLiquidacionEnum.CERRADA);

                    Map<String, Object> params = new HashMap<>();
                    params.put(SubsidioMonetarioCompositeConstants.APROBRADO_SEGUNDO_NIVEL, SubsidioMonetarioCompositeConstants.RECHAZO);
                    params.put(SubsidioMonetarioCompositeConstants.ANALISTA_SUBSIDIO, solicitudLiquidacion.getUsuarioEvaluacionPrimerNivel());

                    ObtenerTokenAccesoV2 obtenerTokenSrv = new ObtenerTokenAccesoV2(profile);
                    obtenerTokenSrv.execute();
                    logger.info("Termina obtenerTokenSrv: " + obtenerTokenSrv.toString());

                    TerminarTarea terminarTarea = new TerminarTarea(Long.parseLong(idTarea), params);
                    terminarTarea.setToken(obtenerTokenSrv.getResult());
                    terminarTarea.execute();
                    logger.info("Termina terminarTarea: " + terminarTarea.toString());
                }
            } else {
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
            }
    }else {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_PARAMETROS_INCOMPLETOS);
        }

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
}