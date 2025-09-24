package com.asopagos.novedades.fovis.composite.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudVerificacionFovisEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudPostulacion;
import com.asopagos.fovis.clients.ActualizarEstadoSolicitudVerificacionFovis;
import com.asopagos.fovis.clients.ConsultarAhorroPrevio;
import com.asopagos.fovis.clients.ConsultarIntegranteHogarPorIdentificacion;
import com.asopagos.fovis.clients.ConsultarIntegrantesHogarPostulacion;
import com.asopagos.fovis.clients.CrearActualizarAhorroPrevio;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona;
import com.asopagos.novedades.fovis.clients.ConsultarInstanciaSolicitudAsociadaPostulacion;
import com.asopagos.novedades.fovis.clients.CrearActualizarListaInhabilidadSubsidioFovis;
import com.asopagos.novedades.fovis.composite.dto.InhabilidadSubsidioFovisInDTO;
import com.asopagos.novedades.fovis.composite.service.NovedadesConvertidorFovisCompositeService;
import com.asopagos.personas.clients.ConsultarJefeHogar;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.tareashumanas.clients.AbortarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con Novedades Regulares FOVIS en las que aplican lógica especial de negocio.
 * 
 * proceso 3.2.5
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Stateless
public class NovedadesConvertidorFovisCompositeBusiness implements NovedadesConvertidorFovisCompositeService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesConvertidorFovisCompositeBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.novedades.fovis.composite.service.NovedadesConvertidorFovisCompositeService#actualizarHogarYMovilizacionAhorros(com.
     * asopagos.dto.modelo.PostulacionFOVISModeloDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void actualizarHogarYMovilizacionAhorros(PostulacionFOVISModeloDTO hogar, UserDTO userDTO) {
        String path = "actualizarHogarYMovilizacionAhorros(PostulacionFOVISModeloDTO, UserDTO): void";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        List<AhorroPrevioModeloDTO> ahorrosPreviosModificar = new ArrayList<>();

        /* Se actualiza el hogar */
        this.crearActualizarPostulacionFOVIS(hogar);

        /* Se actualizan los ahorros previos que hayan sido objeto de movilización. */
        /* Si el ahorro Evaluación Crediticia fue movilizado */
        if (hogar.getAhorroEvaluacionCrediticiaMobilizado() != null 
                && hogar.getAhorroEvaluacionCrediticiaMobilizado()) {
            AhorroPrevioModeloDTO ahorroPrevioModeloDTO = this.consultarAhorroPrevio(hogar.getIdPostulacion(),
                    TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA);
            ahorroPrevioModeloDTO.setAhorroMovilizado(hogar.getAhorroEvaluacionCrediticiaMobilizado());
            ahorrosPreviosModificar.add(ahorroPrevioModeloDTO);
        }
        if (hogar.getAhorroProgramadoMobilizado() != null 
                && hogar.getAhorroProgramadoMobilizado()) {
            AhorroPrevioModeloDTO ahorroPrevioModeloDTO = this.consultarAhorroPrevio(hogar.getIdPostulacion(),
                    TipoAhorroPrevioEnum.AHORRO_PROGRAMADO);
            ahorroPrevioModeloDTO.setAhorroMovilizado(hogar.getAhorroProgramadoMobilizado());
            ahorrosPreviosModificar.add(ahorroPrevioModeloDTO);
        }
        if (hogar.getCesantiasMovilizado() != null 
                && hogar.getCesantiasMovilizado()) {
            AhorroPrevioModeloDTO ahorroPrevioModeloDTO = this.consultarAhorroPrevio(hogar.getIdPostulacion(),
                    TipoAhorroPrevioEnum.CESANTIAS_INMOVILIZADAS);
            ahorroPrevioModeloDTO.setAhorroMovilizado(hogar.getCesantiasMovilizado());
            ahorrosPreviosModificar.add(ahorroPrevioModeloDTO);
        }
        /* Se actualizan los ahorros previos */
        this.actualizarAhorrosPrevios(hogar.getIdPostulacion(), ahorrosPreviosModificar);

        logger.debug(ConstantesComunes.FIN_LOGGER + path);
    }

    /**
     * Métodos Privados
     */
    /**
     * Método que invoca el servicio de actualización de una postulación FOVIS
     * 
     * @param postulacionFOVISDTO
     *        La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.debug("Inicia el método crearActualizarPostulacionFOVIS");
        CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISDTO);
        service.execute();
        logger.debug("Finaliza el método crearActualizarPostulacionFOVIS");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de consuta del ahorro previo.
     * 
     * @param idPostulación
     * @param tipoAhorro
     * 
     * @return Datos del registro actualizado
     */
    private AhorroPrevioModeloDTO consultarAhorroPrevio(Long idPostulacion, TipoAhorroPrevioEnum tipoAhorro) {
        String path = "consultarAhorroPrevio(Long, TipoAhorroPrevioEnum): AhorroPrevioModeloDTO";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        ConsultarAhorroPrevio service = new ConsultarAhorroPrevio(tipoAhorro, idPostulacion);
        service.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización de ahorros previos
     * 
     * @param idPostulación
     * @param ahorrosPrevios
     * 
     * @return Datos del registro actualizado
     */
    private List<AhorroPrevioModeloDTO> actualizarAhorrosPrevios(Long idPostulacion, List<AhorroPrevioModeloDTO> ahorrosPrevios) {
        String path = "actualizarAhorrosPrevios(Long, List<AhorroPrevioModeloDTO>): AhorroPrevioModeloDTO";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        CrearActualizarAhorroPrevio service = new CrearActualizarAhorroPrevio(idPostulacion, ahorrosPrevios);
        service.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return service.getResult();
    }

    @Override
    public void ejecutarRechazoPostulacion(PostulacionFOVISModeloDTO postulacion, UserDTO userDTO) {
        String path = "ejecutarRechazoPostulacion(PostulacionFOVISModeloDTO, UserDTO): void";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        // Se consultas las instancias de la postulacion
        ConsultarInstanciaSolicitudAsociadaPostulacion instanciaSolicitudService = new ConsultarInstanciaSolicitudAsociadaPostulacion(
                postulacion.getIdPostulacion());
        instanciaSolicitudService.execute();
        List<Object> instancias = instanciaSolicitudService.getResult();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : instancias) {
            // Se verifica si existe solicitud de postulación
            abortarProcesoPostulacion(objectMapper, object);
            // Se verifica si existe solicitud de verificacion
            abortarProcesoVerificacion(objectMapper, object);
        }
        // Se actualiza la postulacion con el estado enviado
        crearActualizarPostulacionFOVIS(postulacion);

        logger.debug(ConstantesComunes.FIN_LOGGER + path);
    }

    private void abortarProcesoPostulacion(ObjectMapper objectMapper, Object object){
        try {
            SolicitudPostulacionModeloDTO spo = objectMapper.convertValue(object, SolicitudPostulacionModeloDTO.class);
            if (spo == null || spo.getIdSolicitudPostulacion() == null) {
                return;
            }
            if (!spo.getEstadoSolicitud().equals(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA)
                    && spo.getResultadoProceso() == null) {
                // Se cambia el estado a cancelada
                actualizarEstadoSolicitudPostulacion(spo.getIdSolicitud(), EstadoSolicitudPostulacionEnum.CANCELADA);
                // Se cambia el estado a cerrada
                actualizarEstadoSolicitudPostulacion(spo.getIdSolicitud(), EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
            }
            else if (!spo.getEstadoSolicitud().equals(EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA)) {
                // Se cambia el estado a cerrada
                actualizarEstadoSolicitudPostulacion(spo.getIdSolicitud(), EstadoSolicitudPostulacionEnum.POSTULACION_CERRADA);
            }
            if (spo.getIdInstanciaProceso() != null) {
                Long idInstancia = new Long(spo.getIdInstanciaProceso());
                Long idTarea = consultarTareaActiva(idInstancia);
                if (idTarea != null) {
                    abortarProceso(spo.getTipoTransaccion().getProceso(), idInstancia);
                }
            }
            
        } catch (IllegalArgumentException e) {
            logger.error(e);
        }
    }
    
    private void abortarProcesoVerificacion(ObjectMapper objectMapper, Object object){
        try {
            SolicitudVerificacionFovisModeloDTO svf = objectMapper.convertValue(object, SolicitudVerificacionFovisModeloDTO.class);
            if (svf == null || svf.getIdSolicitudVerificacionFovis() == null) {
                return;
            }
            if (!svf.getEstadoSolicitud().equals(EstadoSolicitudVerificacionFovisEnum.CERRADA)
                    && svf.getResultadoProceso() == null) {
                // Se cambia el estado a cancelada
                actualizarEstadoSolicitudVerificacion(svf.getIdSolicitud(), EstadoSolicitudVerificacionFovisEnum.CANCELADA);
                // Se cambia el estado a cerrada
                actualizarEstadoSolicitudVerificacion(svf.getIdSolicitud(), EstadoSolicitudVerificacionFovisEnum.CERRADA);
            }
            else if (!svf.getEstadoSolicitud().equals(EstadoSolicitudVerificacionFovisEnum.CERRADA)) {
                // Se cambia el estado a cerrada
                actualizarEstadoSolicitudVerificacion(svf.getIdSolicitud(), EstadoSolicitudVerificacionFovisEnum.CERRADA);
            }
            if (svf.getIdInstanciaProceso() != null) {
                Long idInstancia = new Long(svf.getIdInstanciaProceso());
                Long idTarea = consultarTareaActiva(idInstancia);
                if (idTarea != null) {
                    abortarProceso(svf.getTipoTransaccion().getProceso(), idInstancia);
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
        }
    }

    /**
     * Consulta si la instancia de proceso enviada tiene tarea activa
     * @param idInstanciaProceso
     *        Identificador instancia proceso
     * @return Identificador tarea activa
     */
    private Long consultarTareaActiva(Long idInstanciaProceso) {
        Long idTarea = null;
        Map<String, Object> mapResult = new HashMap<String, Object>();
        ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(idInstanciaProceso);
        obtenerTareaActivaService.execute();
        mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
        if (mapResult != null && !mapResult.isEmpty() && mapResult.get("idTarea") != null) {
            idTarea = ((Integer) mapResult.get("idTarea")).longValue();
        }
        return idTarea;
    }

    /**
     * Aborta el proceso bpm indicado por la instancia
     * @param proceso
     *        Proceso BPM
     * @param idInstanciaProceso
     *        Identificador instancia del proceso
     */
    private void abortarProceso(ProcesoEnum proceso, Long idInstanciaProceso) {
        AbortarProceso aborProceso = new AbortarProceso(proceso, idInstanciaProceso);
        aborProceso.execute();
    }

    /**
     * Realiza el llamado al servicio que actualiza el estado de la solicitud de postulación
     * @param idSolicitudGlobal
     *        Identificador solicitud
     * @param estadoSolicitud
     *        Estado nuevo
     */
    private void actualizarEstadoSolicitudPostulacion(Long idSolicitudGlobal, EstadoSolicitudPostulacionEnum estadoSolicitud) {
        ActualizarEstadoSolicitudPostulacion actualizarEstadoSolicitudPostulacion = new ActualizarEstadoSolicitudPostulacion(
                idSolicitudGlobal, estadoSolicitud);
        actualizarEstadoSolicitudPostulacion.execute();
    }

    /**
     * Realiza el llamado al servicio que actualiza el estado de la solicitud de verificación de postulación
     * @param idSolicitudGlobal
     *        Identificado solicitud
     * @param estadoSolicitud
     *        Estado nuevo
     */
    private void actualizarEstadoSolicitudVerificacion(Long idSolicitudGlobal, EstadoSolicitudVerificacionFovisEnum estadoSolicitud) {
        ActualizarEstadoSolicitudVerificacionFovis actualizarEstadoSolicitudVerificacionFovis = new ActualizarEstadoSolicitudVerificacionFovis(
                idSolicitudGlobal, estadoSolicitud);
        actualizarEstadoSolicitudVerificacionFovis.execute();
    }

    @Override
    public void ejecutarRegistroInhabilidad(InhabilidadSubsidioFovisInDTO inDTO) {
        String path = "ejecutarRestitucionSubsidio(PostulacionFOVISModeloDTO, UserDTO): void";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        // Si no se envian datos se evade la ejecución de servicio
        if (inDTO == null || inDTO.getTipoIdJefeHogar() == null || inDTO.getNumeroIdJefeHogar() == null) {
            return;
        }
        // Lista de inhabilidades a registrar
        List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades = new ArrayList<InhabilidadSubsidioFovisModeloDTO>();
        // Lista de integrantes del hogar para el procesamiento de la inhabilidad
        List<IntegranteHogarModeloDTO> listaIntegrantesHogar = new ArrayList<IntegranteHogarModeloDTO>();
        // Fecha inicio inhabilidad
        Calendar fechaInicio = Calendar.getInstance();

        // Se verifica si se envio la información de la postulacion
        // Aplica inhabilidad - Hogar
        if (inDTO.getIdPostulacion() != null && (inDTO.getTipoIdIntegrante() == null || inDTO.getNumeroIdIntegrante() == null)) {
            //Se consultan los integrantes del hogar asociados a la postulacion
            ConsultarIntegrantesHogarPostulacion consultarIntegrantesHogarPostulacion = new ConsultarIntegrantesHogarPostulacion(
                    inDTO.getIdPostulacion());
            consultarIntegrantesHogarPostulacion.execute();
            listaIntegrantesHogar = consultarIntegrantesHogarPostulacion.getResult();
            if (listaIntegrantesHogar != null && !listaIntegrantesHogar.isEmpty()) {
                //Se crea el registro de inhabilidad del jefe de hogar
                InhabilidadSubsidioFovisModeloDTO inhabilidadDTO = new InhabilidadSubsidioFovisModeloDTO();
                JefeHogarModeloDTO jefeHogar = new JefeHogarModeloDTO();
                jefeHogar.setIdJefeHogar(listaIntegrantesHogar.get(0).getIdJefeHogar());
                inhabilidadDTO.setInhabilitadoParaSubsidio(Boolean.TRUE);
                inhabilidadDTO.setJefeHogar(jefeHogar);
                inhabilidadDTO.setFechaInicioInhabilidad(fechaInicio.getTimeInMillis());
                listaInhabilidades.add(inhabilidadDTO);
            }
        }
        // Se verifica si se envio la información de integrante hogar
        // Aplica inhabilidad - Miembro Hogar
        else if (inDTO.getTipoIdIntegrante() != null && inDTO.getNumeroIdIntegrante() != null) {
            //Se consulta el integrante del hogar
            ConsultarIntegranteHogarPorIdentificacion consultarIntegrantesHogar = new ConsultarIntegranteHogarPorIdentificacion(
                    inDTO.getIdPostulacion(), inDTO.getNumeroIdIntegrante(), inDTO.getTipoIdIntegrante());
            consultarIntegrantesHogar.execute();
            listaIntegrantesHogar.add(consultarIntegrantesHogar.getResult());
        }
        // Aplica inhabilidad - Jefe Hogar
        else {
            // Se consulta el jefe hogar
            ConsultarJefeHogar consultarJefeHogar = new ConsultarJefeHogar(inDTO.getNumeroIdJefeHogar(), inDTO.getTipoIdJefeHogar());
            consultarJefeHogar.execute();
            JefeHogarModeloDTO jefeHogar = consultarJefeHogar.getResult();
            InhabilidadSubsidioFovisModeloDTO inhabilidadDTO = new InhabilidadSubsidioFovisModeloDTO();
            inhabilidadDTO.setInhabilitadoParaSubsidio(Boolean.TRUE);
            inhabilidadDTO.setJefeHogar(jefeHogar);
            inhabilidadDTO.setFechaInicioInhabilidad(fechaInicio.getTimeInMillis());
            listaInhabilidades.add(inhabilidadDTO);
        }

        //Se crean las inhabilidades para los integrantes de hogar encontrados
        if (listaIntegrantesHogar != null && !listaIntegrantesHogar.isEmpty()) {
            for (IntegranteHogarModeloDTO integranteHogar : listaIntegrantesHogar) {
                InhabilidadSubsidioFovisModeloDTO inhabilidadDTO = new InhabilidadSubsidioFovisModeloDTO();
                inhabilidadDTO.setInhabilitadoParaSubsidio(Boolean.TRUE);
                inhabilidadDTO.setIntegranteHogar(integranteHogar);
                inhabilidadDTO.setFechaInicioInhabilidad(fechaInicio.getTimeInMillis());
                listaInhabilidades.add(inhabilidadDTO);
            }
        }

        // Se consultan las inhabilidades existentes al hogar
        ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona inhabilidadesHogarService = new ConsultarInhabilidadesHogarSubsidioFovisPorDatosPersona(
                inDTO.getNumeroIdJefeHogar(), inDTO.getTipoIdJefeHogar());
        inhabilidadesHogarService.execute();
        List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidadesExistentes = inhabilidadesHogarService.getResult();

        // Se verifica si ya se registro la inhabilidad para actualizarla
        for (InhabilidadSubsidioFovisModeloDTO inhabilidad : listaInhabilidades) {
            validarInhabilidadExistente(listaInhabilidadesExistentes, inhabilidad);
        }
        CrearActualizarListaInhabilidadSubsidioFovis service = new CrearActualizarListaInhabilidadSubsidioFovis(listaInhabilidades);
        service.execute();
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
    }

    /**
     * Verifica si las inhabilidades se deben crear
     * @param listaInhabilidadesExistentes
     *        Lista de inahbilidades previamente registradas
     * @param inhabilidad
     *        Información de inhabilidad a verificar
     */
    private void validarInhabilidadExistente(List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidadesExistentes,
            InhabilidadSubsidioFovisModeloDTO inhabilidad) {
        // Se obtiene la información de la inhabilidad nueva
        Long idJefeInhabilidadNueva = getIdJefeHogar(inhabilidad);
        Long idIntegranteInhabiNuev = getIdIntegranteHogar(inhabilidad);

        for (InhabilidadSubsidioFovisModeloDTO inhabilidadSubsidio : listaInhabilidadesExistentes) {
            Long idJefeInhabilidadExist = getIdJefeHogar(inhabilidadSubsidio);
            Long idIntegranteInhabiExis = getIdIntegranteHogar(inhabilidadSubsidio);
            if ((idJefeInhabilidadNueva != null && idJefeInhabilidadExist != null && idJefeInhabilidadNueva.equals(idJefeInhabilidadExist))
                    || (idIntegranteInhabiNuev != null && idIntegranteInhabiExis != null
                            && idIntegranteInhabiNuev.equals(idIntegranteInhabiExis))) {
                inhabilidad.setIdInhabilidadSubsidioFovis(inhabilidadSubsidio.getIdInhabilidadSubsidioFovis());
                inhabilidad.setFechaInicioInhabilidad(inhabilidadSubsidio.getFechaInicioInhabilidad());
                break;
            }
        }
    }

    /**
     * Obtiene el identificador del jefe de hogar apartir de la información de inhabilidad
     * @param inhabilidad
     *        Información inhabilidad
     * @return Identificador jefe hogar
     */
    private Long getIdJefeHogar(InhabilidadSubsidioFovisModeloDTO inhabilidad) {
        Long result = null;
        if (inhabilidad.getJefeHogar() != null && inhabilidad.getJefeHogar().getIdJefeHogar() != null) {
            result = inhabilidad.getJefeHogar().getIdJefeHogar();
        }
        return result;
    }

    /**
     * Obtiene el identificador del integrante de hogar apartir de la información de inhabilidad
     * @param inhabilidad
     *        Información inhabilidad
     * @return Identificador integrante hogar
     */
    private Long getIdIntegranteHogar(InhabilidadSubsidioFovisModeloDTO inhabilidad) {
        Long result = null;
        if (inhabilidad.getIntegranteHogar() != null && inhabilidad.getIntegranteHogar().getIdIntegranteHogar() != null) {
            result = inhabilidad.getIntegranteHogar().getIdIntegranteHogar();
        }
        return result;
    }

}
