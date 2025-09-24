package com.asopagos.novedades.fovis.ejb;

import static com.asopagos.util.Interpolator.interpolate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ConsultarEstadoDTO;
import com.asopagos.dto.EstadoDTO;
import com.asopagos.dto.ParametroConsultaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.fovis.HistoricoNovedadFovisDTO;
import com.asopagos.dto.modelo.ActoAceptacionProrrogaFovisModeloDTO;
import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import com.asopagos.entidades.ccf.fovis.ActoAceptacionProrrogaFovis;
import com.asopagos.entidades.ccf.fovis.DetalleNovedadFovis;
import com.asopagos.entidades.ccf.fovis.InhabilidadSubsidioFovis;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudAnalisisNovedadFovis;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadFovis;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadPersonaFovis;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.fovis.SolicitudVerificacionFovis;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.constants.NamedQueriesConstants;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.novedades.fovis.dto.DatosReporteNovedadProrrogaFovisDTO;
import com.asopagos.novedades.fovis.service.NovedadesFovisService;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.util.EstadosUtils;
import com.asopagos.util.Interpolator;
import com.asopagos.util.PersonasFOVISUtils;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de Proceso de Novedades FOVIS <b>Historia de Usuario:</b>
 * Proceso 3.2.5
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
@Stateless
public class NovedadesFovisBusiness implements NovedadesFovisService {

    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName = "novedades_fovis_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(NovedadesFovisBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedadesfovis.service.NovedadesFovisService#consultarPostulacionesNovedadSuspencionAutomatica()
     */
    @Override
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesNovedadSuspencionAutomatica() {
        logger.debug("Inicia el servicio consultarPostulacionesNovedadSancionAutomatica");

        //Se identifican las postulaciones que cumplen con la condicion para generar la novedad automatica de suspencion por cambio de año
        List<PostulacionFOVIS> listaPostulacionesSuspencion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_NOVEDAD_SUSPENCION_CAMBIO_ANO, PostulacionFOVIS.class)
                .getResultList();

        List<PostulacionFOVISModeloDTO> listaPostulaciones = new ArrayList<PostulacionFOVISModeloDTO>();
        if (!listaPostulacionesSuspencion.isEmpty()) {

            for (PostulacionFOVIS postulacion : listaPostulacionesSuspencion) {
                PostulacionFOVISModeloDTO postulacionDTO = new PostulacionFOVISModeloDTO(postulacion);
                //Cambia estado de los hogares con estado de "Postulado", "Hábil" a "Suspendida por cambio de año".
                if (EstadoHogarEnum.POSTULADO.equals(postulacion.getEstadoHogar())
                        || EstadoHogarEnum.HABIL.equals(postulacion.getEstadoHogar())) {
                    postulacionDTO.setEstadoHogar(EstadoHogarEnum.SUSPENDIDO_POR_CAMBIO_DE_ANIO);
                }
                //Cambia el estado de los hogares con estado "Habil segundo año" a "Hogar rechazado"
                if (EstadoHogarEnum.HABIL_SEGUNDO_ANIO.equals(postulacion.getEstadoHogar())) {
                    postulacionDTO.setEstadoHogar(EstadoHogarEnum.RECHAZADO);
                }

                listaPostulaciones.add(postulacionDTO);
            }
        }
        logger.debug("Finaliza el servicio consultarPostulacionesNovedadSuspencionAutomatica");
        return listaPostulaciones;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#crearActualizarSolicitudNovedadFovis(com.asopagos.dto.modelo.
     * SolicitudNovedadFovisModeloDTO)
     */
    @Override
    public SolicitudNovedadFovisModeloDTO crearActualizarSolicitudNovedadFovis(
        SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO) {
        logger.debug("Inicia el servicio crearActualizarSolicitudNovedadFovis");
        Solicitud solicitud = solicitudNovedadFovisModeloDTO.convertToSolicitudEntity();
        solicitud = entityManager.merge(solicitud);
        SolicitudNovedadFovis solicitudNovedadFovis = solicitudNovedadFovisModeloDTO.convertToEntity();
        solicitudNovedadFovis.setSolicitudGlobal(solicitud);
        solicitudNovedadFovis = entityManager.merge(solicitudNovedadFovis);
        solicitudNovedadFovisModeloDTO.convertToDTO(solicitudNovedadFovis);
        logger.debug("Finaliza el servicio crearActualizarSolicitudNovedadFovis");
        return solicitudNovedadFovisModeloDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#almacenarSolicitudNovedadAutomaticaMasiva(java.lang.Long,
     * com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO)
     */
    @Override
    public void almacenarSolicitudNovedadAutomaticaMasiva(Long idSolicitudNovedad,
            DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO) {
        logger.debug(
                "Ingresa al método aalmacenarSolicitudNovedadAutomaticaMasiva(Long idSolicitudNovedad, List<PostulacionFOVISModeloDTO> listaPostulaciones)");

        /*
         * Se persiste en Batch las SolicitudNovedadPersonaFovis
         * Configuración actual: hibernate.jdbc.batch_size value = 500
         */
        if (datosNovedadAutomaticaFovisDTO.getListaPostulaciones() != null
                && !datosNovedadAutomaticaFovisDTO.getListaPostulaciones().isEmpty()) {
            for (PostulacionFOVISModeloDTO postulacion : datosNovedadAutomaticaFovisDTO.getListaPostulaciones()) {
                SolicitudNovedadPersonaFovis solicitudNovedadAutomaticaFovis = new SolicitudNovedadPersonaFovis();
                solicitudNovedadAutomaticaFovis.setIdPostulacionFovis(postulacion.getIdPostulacion());
                solicitudNovedadAutomaticaFovis.setIdSolicitudNovedadFovis(idSolicitudNovedad);
                crearActualizarSolicitudNovedadPersonaFovis(solicitudNovedadAutomaticaFovis);
            }
        }

        if (datosNovedadAutomaticaFovisDTO.getListaInhabilidades() != null
                && !datosNovedadAutomaticaFovisDTO.getListaInhabilidades().isEmpty()) {
            for (InhabilidadSubsidioFovisModeloDTO inhabilidad : datosNovedadAutomaticaFovisDTO.getListaInhabilidades()) {
                SolicitudNovedadPersonaFovis solicitudNovedadAutomaticaFovis = new SolicitudNovedadPersonaFovis();
                solicitudNovedadAutomaticaFovis.setIdPersona(inhabilidad.getIdPersona());
                solicitudNovedadAutomaticaFovis.setIdSolicitudNovedadFovis(idSolicitudNovedad);
                crearActualizarSolicitudNovedadPersonaFovis(solicitudNovedadAutomaticaFovis);
            }
        }

        logger.debug(
                "Finaliza el método almacenarSolicitudNovedadAutomaticaMasiva(Long idSolicitudNovedad, List<PostulacionFOVISModeloDTO> listaPostulaciones)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedadesfovis.service.NovedadesFovisService#consultarPostulacionVigentePersonas(java.util.List)
     */
    @Override
    public List<PersonaPostulacionDTO> consultarPostulacionVigentePersonas(List<PersonaDTO> listPersonas) {
        String firma = "consultarPostulacionVigentePersonas(List<PersonaDTO>):List<PersonaPostulacionDTO>";
        logger.info(ConstantesComunes.INICIO_LOGGER + firma);
        List<ParametroConsultaDTO> paramsConsulta = new ArrayList<>();
        // Se crea el parametro de consulta
        ParametroConsultaDTO parametroConsultaDTO;
        for (PersonaDTO personaDTO : listPersonas) {
            parametroConsultaDTO = new ParametroConsultaDTO();
            parametroConsultaDTO.setEntityManager(entityManager);
            parametroConsultaDTO.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
            parametroConsultaDTO.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
            paramsConsulta.add(parametroConsultaDTO);
        }
        logger.info(ConstantesComunes.FIN_LOGGER + firma);
        return PersonasFOVISUtils.consultarPostulacionVigente(paramsConsulta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedadesfovis.service.NovedadesFovisService#crearSolicitudAnalisisNovedadFOVIS(java.util.List)
     */
    @Override
    public List<SolicitudAnalisisNovedadFOVISModeloDTO> crearActualizarListaSolicitudAnalisisNovedadFOVIS(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes) {
        String firma = "crearSolicitudAnalisisNovedadFOVIS(List<SolicitudAnalisisNovedadFOVISModeloDTO>):List<SolicitudAnalisisNovedadFOVISModeloDTO>";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        // Se realiza la iteracion y agregacion de los registros
        for (SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO : listSolicitudes) {
            crearActualizarSolicitudAnalisisNovedadFOVIS(solicitudAnalisisNovedadFOVISModeloDTO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return listSolicitudes;
    }

    @Override
    public SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisisNovedad(Long idSolicitud) {
        String firma = "consultarSolicitudAnalisisNovedad(Long):SolicitudAnalisisNovedadFOVISModeloDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
            // Se consulta la solicitud de analisis a partir del identificador de la solicitud
            SolicitudAnalisisNovedadFOVISModeloDTO solicitud = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ANALISIS_NOVEDAD_AFECTA_FOVIS,
                            SolicitudAnalisisNovedadFOVISModeloDTO.class)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            // Se obtiene el estado de la persona respecto a la CCF
            String numeroDocumento = solicitud.getPersonaNovedad().getNumeroIdentificacion();
            TipoIdentificacionEnum tipoDocumento = solicitud.getPersonaNovedad().getTipoIdentificacion();
            List<ConsultarEstadoDTO> listParam = new ArrayList<ConsultarEstadoDTO>();
            ConsultarEstadoDTO parametros = new ConsultarEstadoDTO();
            parametros.setEntityManager(entityManager);
            parametros.setNumeroIdentificacion(numeroDocumento);
            parametros.setTipoIdentificacion(tipoDocumento);
            parametros.setTipoRol(ConstantesComunes.PERSONAS);
            listParam.add(parametros);
            List<EstadoDTO> listEstados = EstadosUtils.consultarEstadoCaja(listParam);
            EstadoAfiliadoEnum estadoAfiliadoEnum = EstadoAfiliadoEnum.NO_FORMALIZADO_CON_INFORMACION;
            if (listEstados != null && !listEstados.isEmpty()) {
                EstadoDTO estadoDTO = listEstados.iterator().next();
                estadoAfiliadoEnum = estadoDTO.getEstado();
            }
            solicitud.getPersonaNovedad().setEstadoAfiliadoCaja(estadoAfiliadoEnum);
            logger.debug(ConstantesComunes.FIN_LOGGER + firma);
            return solicitud;
        } catch (NoResultException | NonUniqueResultException nre) {
            logger.error(ConstantesComunes.FIN_LOGGER + firma);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarPersonasInhabilidadSubsidioFovisAutomatica()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<InhabilidadSubsidioFovisModeloDTO> consultarPersonasInhabilidadSubsidioFovisAutomatica() {
        try {
            logger.debug("Inicia el servicio consultarPersonasInhabilidadSubsidioFovisAutomatica");
            //Se identifican las postulaciones que cumplen con la condicion para generar la novedad y levantar la inhabilidad o sancion
            List<Object[]> listObject = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INHABILIDAD_PERSONA_BY_TIPO_NRO)
                    .setParameter("numeroIdentificacion", null).setParameter("tipoIdentificacion", null).getResultList();
            List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades = new ArrayList<>();
            for (Object[] response : listObject) {
                InhabilidadSubsidioFovis inhabilidadSubsidioFovis = (InhabilidadSubsidioFovis) response[0];
                Persona persona = (Persona) response[1];
                // Se agrega la inhabilidad
                listaInhabilidades.add(new InhabilidadSubsidioFovisModeloDTO(inhabilidadSubsidioFovis, persona.getIdPersona()));
            }
            logger.debug("Finaliza el servicio consultarPersonasInhabilidadSubsidioFovisAutomatica");
            return listaInhabilidades;
        } catch (NoResultException e) {
            return null;
        }
    }

    /*
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#actualizarEstadoSolicitudNovedadFovis(
     * java.lang.Long, com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum)
     */
    @Override
    public void actualizarEstadoSolicitudNovedadFovis(Long idSolicitudGlobal, EstadoSolicitudNovedadFovisEnum estadoSolicitud) {
        logger.debug("Se inicia el servicio de actualizarEstadoSolicitudNovedadFovis(" + "Long, EstadoSolicitudNovedadFovisEnum)");
        SolicitudNovedadFovisModeloDTO solicitudNovedadFovisDTO = consultarSolicitudNovedadFovis(idSolicitudGlobal);
        SolicitudNovedadFovis solicitudNovedadFovis = solicitudNovedadFovisDTO.convertToEntity();

        // se verifica si el nuevo estado es CERRADA para actualizar el
        // resultado del proceso
        if (EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_CERRADA.equals(estadoSolicitud)) {
            ResultadoProcesoEnum estadoResultadoProceso = ResultadoProcesoEnum.APROBADA;
            if (EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RECHAZADA.equals(solicitudNovedadFovis.getEstadoSolicitud())
                    || EstadoSolicitudNovedadFovisEnum.POSTULACION_RECHAZADA.equals(solicitudNovedadFovis.getEstadoSolicitud())) {
                estadoResultadoProceso = ResultadoProcesoEnum.RECHAZADA;
            }
            else if (EstadoSolicitudNovedadFovisEnum.DESISTIDA.equals(solicitudNovedadFovis.getEstadoSolicitud())
                    || EstadoSolicitudNovedadFovisEnum.CANCELADA.equals(solicitudNovedadFovis.getEstadoSolicitud())) {
                estadoResultadoProceso = ResultadoProcesoEnum.valueOf(solicitudNovedadFovis.getEstadoSolicitud().name());
            }
            if(!EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_CERRADA.equals(solicitudNovedadFovis.getEstadoSolicitud())){
                solicitudNovedadFovis.getSolicitudGlobal().setResultadoProceso(estadoResultadoProceso);
            }
        }

        solicitudNovedadFovis.setEstadoSolicitud(estadoSolicitud);
        entityManager.merge(solicitudNovedadFovis);
        logger.debug("Finaliza actualizarEstadoSolicitudNovedadFovis(Long, EstadoSolicitudNovedadFovisEnum)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarSolicitudNovedadFovis(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SolicitudNovedadFovisModeloDTO consultarSolicitudNovedadFovis(Long idSolicitudGlobal) {
        logger.debug("Se inicia el servicio de consultarSolicitudNovedadFovis(Long)");
        try {
            SolicitudNovedadFovis sol = (SolicitudNovedadFovis) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_NOVEDAD_FOVIS_POR_ID_SOLICITUD_GLOBAL)
                    .setParameter("idSolicitudGlobal", idSolicitudGlobal).getSingleResult();
            SolicitudNovedadFovisModeloDTO solicitudDTO = new SolicitudNovedadFovisModeloDTO();
            solicitudDTO.convertToDTO(sol);
            logger.debug("Finaliza consultarSolicitudNovedadFovis(Long)");
            return solicitudDTO;
        } catch (NoResultException nre) {
            logger.debug("Finaliza consultarSolicitudNovedadFovis(Long)"
                    + interpolate("No se encontraron resultados con el id de solicitud {0} ingresada.", idSolicitudGlobal));
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#crearActualizarInhabilidadSubsidioFovis(com.asopagos.dto.modelo.
     * InhabilidadSubsidioFovisModeloDTO)
     */
    @Override
    public InhabilidadSubsidioFovisModeloDTO crearActualizarInhabilidadSubsidioFovis(InhabilidadSubsidioFovisModeloDTO inhabilidadDTO) {
        logger.debug("Inicia el servicio crearActualizarInhabilidadSubsidioFovis");
        if (inhabilidadDTO == null || inhabilidadDTO.getInhabilitadoParaSubsidio() == null) {
            return null;
        }
        InhabilidadSubsidioFovis inhabilidadSubsidioFovis = inhabilidadDTO.convertToEntity();
        inhabilidadSubsidioFovis = entityManager.merge(inhabilidadSubsidioFovis);
        inhabilidadDTO.setIdInhabilidadSubsidioFovis(inhabilidadSubsidioFovis.getIdInhabilidadSubsidioFovis());
        logger.debug("Finaliza el servicio crearActualizarInhabilidadSubsidioFovis");
        return inhabilidadDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarPostulacionesRechazoAutomatico()
     */
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesRechazoAutomatico() {
        logger.debug("Inicia el servicio consultarPostulacionesRechazoAutomatico");

        //Se consultan los hogares que  durante el año no presentan una solicitud para habilitar el hogar en el segundo año
        List<PostulacionFOVIS> listaPostulacionesRechazo = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_NOVEDAD_RECHAZO_CAMBIO_ANO, PostulacionFOVIS.class)
                .getResultList();

        logger.info("listaPostulacionesRechazo " +listaPostulacionesRechazo.size());

        List<PostulacionFOVISModeloDTO> listaPostulaciones = new ArrayList<PostulacionFOVISModeloDTO>();
        if (!listaPostulacionesRechazo.isEmpty()) {

            for (PostulacionFOVIS postulacion : listaPostulacionesRechazo) {
                //Si el hogar durante el año no presenta una solicitud para habilitar el hogar en el segundo año, 
                // el 31 de diciembre del año en curso se debe cambiar el estado del hogar a "Rechazado por suspensión sin renovación"                   
                PostulacionFOVISModeloDTO postulacionDTO = new PostulacionFOVISModeloDTO(postulacion);
                postulacionDTO.setEstadoHogar(EstadoHogarEnum.RECHAZADO_POR_SUSPENSION_SIN_RENOVACION);
                listaPostulaciones.add(postulacionDTO);
            }
        }
        logger.debug("Finaliza el servicio consultarPostulacionesRechazoAutomatico");
        return listaPostulaciones;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarPostulacionesNovedadVencimiendoSubsidios(com.asopagos.
     * enumeraciones.fovis.EstadoHogarEnum)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesNovedadVencimiendoSubsidios(EstadoHogarEnum estadoHogar) {
        //Se identifican las postulaciones que cumplen con la condicion para generar la novedad automatica de suspencion por cambio de año
        return entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_NOVEDAD_VENCIMIENTO_SUBSIDIOS)
                .setParameter("estadoHogar", estadoHogar).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarSolicitudAnalisis(java.lang.Long)
     */
    @Override
    public SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisis(Long idSolicitud) {
        String firma = "consultarSolicitudAnalisis(Long):SolicitudAnalisisNovedadFOVISModeloDTO";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        // Se consulta la info basica de la solicitud
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedad = consultarSolicitudAnalisisByIdSol(idSolicitud);
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        if (solicitudAnalisisNovedad != null) {
            return new SolicitudAnalisisNovedadFOVISModeloDTO(solicitudAnalisisNovedad);
        }
        return null;
    }

    /**
     * Consulta la informacion de la solicitud de analisis por id solicitud retornando la entidad
     * @param idSolicitud
     *        Identificador de solicitud global
     * @return Entidad de solicitud analisis novedad fovis
     */
    private SolicitudAnalisisNovedadFovis consultarSolicitudAnalisisByIdSol(Long idSolicitud) {
        try {
            SolicitudAnalisisNovedadFovis solicitudAnalisisNovedad = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_ANALISIS_ID_SOLICITUD, SolicitudAnalisisNovedadFovis.class)
                    .setParameter("idSolicitud", idSolicitud).getSingleResult();
            return solicitudAnalisisNovedad;
        } catch (NoResultException | NonUniqueResultException ne) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#crearActualizarSolicitudAnalisisNovedadFOVIS(com.asopagos.dto.modelo.
     * SolicitudAnalisisNovedadFOVISModeloDTO)
     */
    @Override
    public SolicitudAnalisisNovedadFOVISModeloDTO crearActualizarSolicitudAnalisisNovedadFOVIS(
            SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO) {
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = solicitudAnalisisNovedadFOVISModeloDTO.convertToEntity();
        if (solicitudAnalisisNovedadFovis.getIdSolicitudAnalisisNovedadFovis() == null) {
            entityManager.persist(solicitudAnalisisNovedadFovis);
        }
        else {
            entityManager.merge(solicitudAnalisisNovedadFovis);
        }
        solicitudAnalisisNovedadFOVISModeloDTO.convertToDTO(solicitudAnalisisNovedadFovis);
        return solicitudAnalisisNovedadFOVISModeloDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#actualizarEstadoSolicitudAnalisisNovedadFOVIS(java.lang.Long,
     * com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum)
     */
    @Override
    public void actualizarEstadoSolicitudAnalisisNovedadFOVIS(Long idSolicitudGlobal,
            EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud) {
        String firma = "actualizarEstadoSolicitudAnalisisNovedadFOVIS(Long, EstadoSolicitudAnalisisNovedadFovisEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        SolicitudAnalisisNovedadFovis solicitudAnalisisNovedadFovis = consultarSolicitudAnalisisByIdSol(idSolicitudGlobal);
        /*
         * se verifica si el nuevo estado es CERRADA para actualizar el
         * resultado del proceso
         */
        if (EstadoSolicitudAnalisisNovedadFovisEnum.CERRADA.equals(estadoSolicitud)) {
            ResultadoProcesoEnum estadoResultadoProceso = ResultadoProcesoEnum.APROBADA;
            if (EstadoSolicitudAnalisisNovedadFovisEnum.DESISTIDA.equals(solicitudAnalisisNovedadFovis.getEstadoSolicitud())
                    || EstadoSolicitudAnalisisNovedadFovisEnum.CANCELADA.equals(solicitudAnalisisNovedadFovis.getEstadoSolicitud())) {
                estadoResultadoProceso = ResultadoProcesoEnum.valueOf(solicitudAnalisisNovedadFovis.getEstadoSolicitud().name());
            }
            solicitudAnalisisNovedadFovis.getSolicitudGlobal().setResultadoProceso(estadoResultadoProceso);
        }
        solicitudAnalisisNovedadFovis.setEstadoSolicitud(estadoSolicitud);
        entityManager.merge(solicitudAnalisisNovedadFovis);
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    }

    @Override
    public SolicitudNovedadPersonaFovis crearActualizarSolicitudNovedadPersonaFovis(
            SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis) {
        String path = "crearSolicitudNovedadPersonaFovis(SolicitudNovedadPersonaFovis):SolicitudNovedadPersonaFovis";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        if (solicitudNovedadPersonaFovis.getIdSolicitudNovedadPersonaFovis() == null) {
            entityManager.persist(solicitudNovedadPersonaFovis);
        }
        else {
            entityManager.merge(solicitudNovedadPersonaFovis);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return solicitudNovedadPersonaFovis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#crearActualizarListaInhabilidadSubsidioFovis(java.util.List)
     */
    @Override
    public void crearActualizarListaInhabilidadSubsidioFovis(List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades) {
        logger.debug("Inicia el servicio crearActualizarListaInhabilidadSubsidioFovis");
        if (listaInhabilidades == null || listaInhabilidades.isEmpty()) {
            return;
        }
        for (InhabilidadSubsidioFovisModeloDTO inhabilidadDTO : listaInhabilidades) {
            inhabilidadDTO = crearActualizarInhabilidadSubsidioFovis(inhabilidadDTO);
        }
        logger.debug("Finaliza el servicio crearActualizarListaInhabilidadSubsidioFovis");

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public InhabilidadSubsidioFovisModeloDTO consultarInhabilidadSubsidioFovisPorDatosPersona(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        String path = "consultarInhabilidadSubsidioFovisPorDatosPersona(String,TipoIdentificacionEnum):InhabilidadSubsidioFovisModeloDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            Object[] inhablididadObject = (Object[]) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_INHABILIDAD_PERSONA_BY_TIPO_NRO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .getSingleResult();
            InhabilidadSubsidioFovisModeloDTO inhabilidadSubsidioFovisModeloDTO = null;
            if (inhablididadObject != null) {
                inhabilidadSubsidioFovisModeloDTO = new InhabilidadSubsidioFovisModeloDTO((InhabilidadSubsidioFovis) inhablididadObject[0],
                        null);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return inhabilidadSubsidioFovisModeloDTO;
        } catch (NoResultException | NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InhabilidadSubsidioFovisModeloDTO> consultarInhabilidadesHogarSubsidioFovisPorDatosPersona(String numeroIdentificacion,
            TipoIdentificacionEnum tipoIdentificacion) {
        String path = "consultarInhabilidadesHogarSubsidioFovisPorDatosPersona(String,TipoIdentificacionEnum):InhabilidadSubsidioFovisModeloDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            List<InhabilidadSubsidioFovis> inhabilidadesHogar = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_INHABILIDAD_HOGAR_BY_TIPO_NRO_JEFE,
                            InhabilidadSubsidioFovis.class)
                    .setParameter("numeroIdentificacion", numeroIdentificacion).setParameter("tipoIdentificacion", tipoIdentificacion.name())
                    .getResultList();
            List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades = new ArrayList<InhabilidadSubsidioFovisModeloDTO>();
            if (inhabilidadesHogar != null && !inhabilidadesHogar.isEmpty()) {
                for (InhabilidadSubsidioFovis inhabilidadSubsidioFovis : inhabilidadesHogar) {
                    InhabilidadSubsidioFovisModeloDTO inhabilidadSubsidioFovisModeloDTO = new InhabilidadSubsidioFovisModeloDTO();
                    inhabilidadSubsidioFovisModeloDTO.convertToDTO(inhabilidadSubsidioFovis);
                    listaInhabilidades.add(inhabilidadSubsidioFovisModeloDTO);
                }
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return listaInhabilidades;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#consultarActoAceptacionProrrogaFovisPorNovedadFovis(java.lang.Long)
     */
    @Override
    public ActoAceptacionProrrogaFovisModeloDTO consultarActoAceptacionProrrogaFovisPorNovedadFovis(Long idSolicitudNovedadFovis) {
        String path = "consultarActoAceptacionProrrogaFovisPorNovedadFovis(Long):ActoAceptacionProrrogaFovis";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            ActoAceptacionProrrogaFovis actoAceptacionProrrogaFovis = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTO_ACEPTACION_PRORROGA_POR_SOLICITUD_NOVEDAD_FOVIS,
                            ActoAceptacionProrrogaFovis.class)
                    .setParameter("idSolicitudNovedadFovis", idSolicitudNovedadFovis).getSingleResult();
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return new ActoAceptacionProrrogaFovisModeloDTO(actoAceptacionProrrogaFovis);
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.NovedadesFovisCompositeService#
     *      generarReporteSolicitudesNovedadFovisProrroga(java.lang.Long, java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void guardarActoAceptacionNovedadesProrrogaFovis(Long fechaAprobacion, String numeroActoAdministrativo,
            List<Long> idSolicitudesNovedadFovis, UserDTO userDTO) {
        String firma = "guardarActoAceptacionNovedadesProrrogaFovis(Long,String,List<Long>,UserDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        for (Long idSolicitudNovedadFovis : idSolicitudesNovedadFovis) {
            ActoAceptacionProrrogaFovis aceptacionProrrogaFovis = new ActoAceptacionProrrogaFovis();
            aceptacionProrrogaFovis.setNumeroActoAdministrativo(numeroActoAdministrativo);
            aceptacionProrrogaFovis.setFechaAprobacionConsejo(new Date(fechaAprobacion));
            aceptacionProrrogaFovis.setIdSolicitudNovedadFovis(idSolicitudNovedadFovis);
            entityManager.persist(aceptacionProrrogaFovis);
        }
        actualizarEstadoPostulacionProrroga(idSolicitudesNovedadFovis);
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
    }

    /**
     * Realiza la actualización del estado de hogar de la postulación fovis asociado a la solicitud novedad de prórroga
     * @param listSolicitudNovedadFovis
     *        Lista de solicitudes novedades fovis que le aceptaron la prórroga
     */
    @SuppressWarnings("unchecked")
    private void actualizarEstadoPostulacionProrroga(List<Long> listSolicitudNovedadFovis) {
        // Consultar las postulaciones asociadas a las solicitudes de novedad seleccionadas en pantalla
        List<Object[]> listPostulaciones = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACION_PRORROBA_BY_SOLICITUD_NOVEDAD)
                .setParameter("resultadoProceso", ResultadoProcesoEnum.APROBADA.name())
                .setParameter("tipoTransaccion", TipoTransaccionEnum.PRORROGA_FOVIS.name())
                .setParameter("listaSolicitudNovedad", listSolicitudNovedadFovis).getResultList();
        
        PostulacionFOVIS postulacion;
        // Representa la cantidad de prorrogas registradas a la postulación
        // El máximo de prórrogas según negocio es 2.
        Integer cantidadProrroga;
        for (Object[] result : listPostulaciones) {
            postulacion = (PostulacionFOVIS) result[0];
            cantidadProrroga = (Integer) result[1];
            if (cantidadProrroga > 1) {
                postulacion.setEstadoHogar(EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA);
            } else {
                postulacion.setEstadoHogar(EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA);
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.service.NovedadesFovisService#
     *      consultarActoAceptacionProrrogaFovisPorNovedadFovis(java.lang.Long,java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DatosReporteNovedadProrrogaFovisDTO> consultarDatosReporteNovedadesProrrogaFovis(Long fechaInicial, Long fechaFinal) {
        logger.debug(Interpolator.interpolate(
                "Inicio consultarDatosReporteNovedadesProrrogaFovis(Long,Long):List<DatosReporteNovedadProrrogaFovisDTO> \\t fechaInicial: {0}\\t fechaFinal: {1}",
                fechaInicial, fechaFinal));
        List<DatosReporteNovedadProrrogaFovisDTO> solicitudesReporte = null;
        List<Object[]> datosSolicitudesReporte = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITIDES_NOVEDAD_FOVIS_PRORROGA_REPORTE)
                .setParameter("resultadoProceso", ResultadoProcesoEnum.APROBADA.name())
                .setParameter("tipoTransaccion", TipoTransaccionEnum.PRORROGA_FOVIS.name())
                .setParameter("fechaInicial", new Date(fechaInicial)).setParameter("fechaFinal", new Date(fechaFinal))
                .getResultList();
        if (datosSolicitudesReporte != null && !datosSolicitudesReporte.isEmpty()) {
            solicitudesReporte = new ArrayList<>();
            for (Object[] registro : datosSolicitudesReporte) {
                solicitudesReporte.add(new DatosReporteNovedadProrrogaFovisDTO(((BigInteger) registro[0]).longValue(),
                        EstadoSolicitudNovedadFovisEnum.valueOf((String) registro[1]), (String) registro[2], (Date) registro[3],
                        ResultadoProcesoEnum.valueOf((String) registro[4]), ((BigInteger) registro[5]).longValue(),
                        EstadoHogarEnum.valueOf((String) registro[6]), TipoIdentificacionEnum.valueOf((String) registro[7]),
                        (String) registro[8], (String) registro[9]));
            }
        }
        return solicitudesReporte;
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<HistoricoNovedadFovisDTO> consultarHistoricoNovedadesFovisHogar(String numeroRadicacion) {
        logger.debug(Interpolator.interpolate("Inicio consultarHistoricoNovedadesFovisHogar(String):List<HistoricoNovedadFovisDTO> \\t numeroRadicacion: {0}", numeroRadicacion));
        List<HistoricoNovedadFovisDTO> resultHistorico = new ArrayList<>();
        List<Object[]> listResult = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_NOVEDADES_HOGAR)
                .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

        HistoricoNovedadFovisDTO historico;
        for (Object[] registro : listResult) {
            historico = new HistoricoNovedadFovisDTO();
            // Info Tabla
            historico.setTipoTransaccion(registro[0] != null ? TipoTransaccionEnum.valueOf(registro[0].toString()) : null);
            historico.setNumeroRadicacion(registro[1].toString());
            historico.setFechaRadicacion(registro[2] != null ? (Date) registro[2] : null);
            historico.setEstadoSolicitud(
                    registro[3] != null ? EstadoSolicitudNovedadFovisEnum.valueOf(registro[3].toString()) : null);
            historico.setClasificacion(registro[4] != null ? ClasificacionEnum.valueOf(registro[4].toString()) : null);
            historico.setTipoIdentificacion(registro[5] != null ? TipoIdentificacionEnum.valueOf(registro[5].toString()) : null);
            historico.setNumeroIdentificacion(registro[6].toString());
            historico.setNombreCompleto(registro[7].toString());
            // Info común formulario
            historico.setIdSolicitud(((BigInteger) registro[8]).longValue());
            historico.setResultadoProceso(registro[9] != null ? ResultadoProcesoEnum.valueOf(registro[9].toString()) : null);
            resultHistorico.add(historico);
        }
        return resultHistorico;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> consultarInstanciaSolicitudAsociadaPostulacion(Long idPostulacion) {
        logger.debug("Inicia servicio consultarInfoSolicitudAsociadaPostulacion(Long)");
        List<Object> list  = new ArrayList<>();
        List<Object[]> solicitudesAsociadasPostulacion = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUDES_ASOCIADAS_POSTULACION)
                .setParameter("idPostulacion", idPostulacion).getResultList();
        if (solicitudesAsociadasPostulacion != null && !solicitudesAsociadasPostulacion.isEmpty()) {
            for (Object[] objects : solicitudesAsociadasPostulacion) {
                SolicitudPostulacion spo = (SolicitudPostulacion) objects[0];
                SolicitudVerificacionFovis svf = (SolicitudVerificacionFovis) objects[1];
                
                list.add(spo != null ? new SolicitudPostulacionModeloDTO(spo) : new SolicitudPostulacionModeloDTO());
                list.add(svf != null ? new SolicitudVerificacionFovisModeloDTO(svf) : new SolicitudVerificacionFovisModeloDTO());
            }
        }
        logger.debug("Finaliza servicio consultarInfoSolicitudAsociadaPostulacion(Long)");
        return list;
    }

    @Override
    public DetalleNovedadFovisModeloDTO crearActualizarDetalleNovedad(DetalleNovedadFovisModeloDTO detalleNovedadFovis) {
        String path = "crearActualizarDetalleNovedad(DetalleNovedadFovisModeloDTO) : DetalleNovedadFovisModeloDTO";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        DetalleNovedadFovis detalleNovedad = detalleNovedadFovis.converToEntity();
        if (detalleNovedad.getIdDetalleNovedadFovis() == null) {
            entityManager.persist(detalleNovedad);
        }
        else {
            entityManager.merge(detalleNovedad);
        }
        detalleNovedadFovis.convertToDTO(detalleNovedad);
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return detalleNovedadFovis;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SolicitudNovedadFovisModeloDTO> consultarListaSolicitudNovedad(Long idPostulacion, TipoTransaccionEnum tipoNovedad,
            ResultadoProcesoEnum resultadoProceso) {
        logger.debug(Interpolator.interpolate("Inicio consultarListaSolicitudNovedad(Long, TipoTransaccionEnum, ResultadoProcesoEnum)\\\\t idPostulacion: {0}\\\\t tipoNovedad {1}\\\\t resultadoProceso:{2}", idPostulacion, tipoNovedad, resultadoProceso));
        List<SolicitudNovedadFovisModeloDTO> listaSolicitudes = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_NOVEDAD_FOVIS_POR_ID_POSTULACION_TRANSACCION_RESULTADO, SolicitudNovedadFovisModeloDTO.class)
                .setParameter("idPostulacion", idPostulacion)
                .setParameter("tipoNovedad", tipoNovedad)
                .setParameter("resultadoProceso", resultadoProceso).getResultList();
        return listaSolicitudes;
    }
}
