package com.asopagos.novedades.fovis.composite.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import com.asopagos.afiliaciones.clients.RadicarSolicitud;
import com.asopagos.afiliados.clients.ConsultarAfiliadoPorId;
import com.asopagos.afiliados.clients.ConsultarBeneficiarioTipoNroIdentificacion;
import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliado;
import com.asopagos.asignaciones.clients.EjecutarAsignacion;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.empresas.clients.CrearActualizarOferente;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadPersonaFovis;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.afiliaciones.OrigenEscalamientoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoAnalistaEstalamientoFOVISEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.PuntoResolucionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ActualizarEstadoHogar;
import com.asopagos.fovis.clients.ActualizarJsonPostulacion;
import com.asopagos.fovis.clients.CrearActualizarAhorroPrevio;
import com.asopagos.fovis.clients.CrearActualizarListaPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarPostulacionFOVIS;
import com.asopagos.fovis.clients.CrearActualizarProyectoSolucionVivienda;
import com.asopagos.fovis.clients.CrearActualizarRecursoComplementario;
import com.asopagos.fovis.clients.CrearListaCondicionEspecialPersona;
import com.asopagos.fovis.clients.ExistenEscalamientosSinResultado;
import com.asopagos.fovis.clients.InactivarIntegrantesHogarNoRelacionados;
import com.asopagos.listaschequeo.clients.GuardarListaChequeo;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.archivos.composite.clients.EnviarNotificacionComunicado;
import com.asopagos.notificaciones.dto.NotificacionParametrizadaDTO;
import com.asopagos.novedades.clients.ConsultarNovedadPorNombre;
import com.asopagos.novedades.clients.CrearIntentoNovedad;
import com.asopagos.novedades.dto.IntentoNovedadDTO;
import com.asopagos.novedades.fovis.clients.ActualizarEstadoSolicitudAnalisisNovedadFOVIS;
import com.asopagos.novedades.fovis.clients.ActualizarEstadoSolicitudNovedadFovis;
import com.asopagos.novedades.fovis.clients.AlmacenarSolicitudNovedadAutomaticaMasiva;
import com.asopagos.novedades.fovis.clients.ConsultarSolicitudAnalisis;
import com.asopagos.novedades.fovis.clients.ConsultarSolicitudAnalisisNovedad;
import com.asopagos.novedades.fovis.clients.ConsultarSolicitudNovedadFovis;
import com.asopagos.novedades.fovis.clients.CrearActualizarListaInhabilidadSubsidioFovis;
import com.asopagos.novedades.fovis.clients.CrearActualizarSolicitudAnalisisNovedadFOVIS;
import com.asopagos.novedades.fovis.clients.CrearActualizarSolicitudNovedadFovis;
import com.asopagos.novedades.fovis.clients.CrearActualizarSolicitudNovedadPersonaFovis;
import com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.dto.AsignarSolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import com.asopagos.novedades.fovis.composite.factories.ValidacionNovedadAutomaticaFactory;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisAbstractFactory;
import com.asopagos.novedades.fovis.composite.service.NovedadFovisCore;
import com.asopagos.novedades.fovis.composite.service.NovedadesFovisCompositeService;
import com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.novedadesfovis.composite.dto.VerificacionNovedadFovisDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.personas.clients.CrearActualizarIntegranteHogar;
import com.asopagos.personas.clients.CrearActualizarJefeHogar;
import com.asopagos.personas.clients.CrearActualizarUbicacion;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
import com.asopagos.solicitudes.clients.RegistrarEscalamientoSolicitud;
import com.asopagos.tareashumanas.clients.IniciarProceso;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.ObtenerDatosUsuarioCajaCompensacion;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.PersonasFOVISUtils;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.validaciones.fovis.clients.ValidarReglasNegocioFovis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con Novedades FOVIS.
 * proceso 3.2.5
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
@Stateless
public class NovedadesFovisCompositeBusiness implements NovedadesFovisCompositeService {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(NovedadesFovisCompositeBusiness.class);

    // Constantes datos validación
    /**
     * Indica el nombre del parametro tipoTransaccion
     */
    private static final String KEY_TIPO_TRANSACCION = "tipoTransaccion";
    /**
     * Indica el nombre del parametro tipoIdentificacion - informacion solicitante
     */
    private static final String KEY_TIPO_IDENTIFICACION = "tipoIdentificacion";
    /**
     * Indica el nombre del parametro numeroIdentificacion - informacion solicitante
     */
    private static final String KEY_NRO_IDENTIFICACION = "numeroIdentificacion";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_TIPO_IDENTIFICA_BENEFI = "tipoIdentificacionBeneficiario";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_NRO_IDENTIFICA_BENEFI = "numeroIdentificacionBeneficiario";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion jefe hogar
     */
    private static final String KEY_TIPO_IDENTIFICA_AFILIADO = "tipoIdentificacionAfiliado";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion jefe hogar
     */
    private static final String KEY_NRO_IDENTIFICA_AFILIADO = "numeroIdentificacionAfiliado";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_PRIMER_NOMBRE = "primerNombre";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_SEGUNDO_NOMBRE = "segundoNombre";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_PRIMER_APELLIDO = "primerApellido";
    /**
     * Indica el nombre del parametro tipoIdentificacionBeneficiario - informacion solicitante
     */
    private static final String KEY_SEGUNDO_APELLIDO = "segundoApellido";
    /**
     * Indica el nombre del parametro idPostulacion - informacion postulacion solicitante
     */
    private static final String KEY_ID_POSTULACION = "idPostulacion";
    /**
     * Indica el nombre del parametro objetoValidacion - informacion clasificación solicitante
     */
    private static final String KEY_OBJETO_VALIDACION = "objetoValidacion";

    // Constantes Códigos resolución BPM
    /**
     * Indica el código que espera el BPM para rechazar la solicitud. <br>
     * Aplica en:
     * - Radicación inicial <br>
     * - Primera verificación del back<br>
     * - Verificación PNC en back
     */
    private static final int CODIGO_RECHAZO = 2;
    /**
     * Indica el código que espera el BPM para escalar la solicitud. <br>
     * Aplica en: <br>
     * - Radicación inicial <br>
     * - Primera verificación del back<br>
     * - Verificación PNC en back
     */
    private static final int CODIGO_ESCALAMIENTO = 3;
    /**
     * Indica el código que espera el BPM para iniciar la gestión de PNC
     */
    private static final int CODIGO_GESTION_PNC = 4;
    /**
     * Indica el código que espera el BPM para aprobar o rechazar la solicitud. <br>
     * Aplica en: <br>
     * - Primera verificación del back <br>
     * - Verificación PNC en back
     */
    private static final int CODIGO_SOLICITUD_APROBADA = 1;
    /**
     * Indica el código que espera el BPM como resultado de escalamiento Procedente
     */
    private static final int CODIGO_ESCALMIENTO_PROCEDENTE = 1;
    /**
     * Indica el código que espera el BPM como resultado de escalamiento NO Procedente
     */
    private static final int CODIGO_ESCALMIENTO_NO_PROCEDENTE = 2;
    /**
     * Indica el código que espera el BPM cuando el escalamiento es desde front
     */
    private static final String CODIGO_ESCALMIENTO_FRONT = "1";
    /**
     * Indica el código que espera el BPM cuando el escalamiento no es desde front
     */
    private static final String CODIGO_ESCALMIENTO_NO_FRONT = "0";

    // Constantes BPM
    /**
     * Constante para el parámetro BPM id de solicitud.
     */
    private static final String ID_SOLICITUD = "idSolicitud";
    /**
     * Constante para el parámetro BPM número de radicación de solicitud
     */
    private static final String NUMERO_RADICACION = "numeroRadicado";
    /**
     * Constante para el parámetro BPM usuario analista técnico - Escalamiento solicitud
     */
    private static final String USUARIO_ANALISTA_TECNICO = "usuarioAnalistaTecnico";
    /**
     * Constante para el parámetro BPM usuario analista jurídico - Escalamiento solicitud
     */
    private static final String USUARIO_ANALISTA_JURIDICO = "usuarioAnalistaJuridico";
    /**
     * Constante para el parámetro BPM usuario analista miembro hogar - Escalamiento solicitud
     */
    private static final String USUARIO_ANALISTA_HOGAR = "usuarioAnalistaMiembroHogar";
    /**
     * Constante para el parámetro BPM usuario back
     */
    private static final String USUARIO_BACK = "usuarioBack";
    /**
     * Constante para el parámetro BPM usuario front
     */
    private static final String USUARIO_FRONT = "usuarioFront";
    /**
     * Constante para el parámetro BPM resultado radicacion - enviado desde front
     */
    private static final String RESULTADO_RADICACION = "resultadoRadica";
    /**
     * Constante para el parámetro BPM escalamiento front <br>
     * Indica si se realiza escalamiento desde front con 1 con 0 en caso contrario
     */
    private static final String ESCALAMIENTO_FRONT = "escalamientoFront";
    /**
     * Constante para el parámetro BPM resultado back - resultado primera verificación back
     */
    private static final String RESULTADO_BACK = "resultadoBack";
    /**
     * Constante para el parámetro BPM resultado PNC - resultado segunda verificación back con PNC
     */
    private static final String RESULTADO_PNC = "resultadoPNC";
    /**
     * Constante para el parámetro BPM resultado escalamiento back - resultado del escalamiento desde el back
     */
    private static final String RESULTADO_ESCALAMIENTO_BACK = "resultadoEscalamientoBack";
    /**
     * Constante para el parámetro BPM resultado escalamiento front - resultado del escalamiento desde el front
     */
    private static final String RESULTADO_ESCALAMIENTO_FRONT = "resultadoEscalamientoFront";
    /**
     * Constante que define señal para documentos físicos
     */
    private static final String DOCUMENTOS_FISICOS = "documentosFisicos";

    /**
     * Indica el bloque de validación 325-023-1
     */
    private static final String BLOQUE_VALIDACION_325_23 = "325-023-1";

    @Resource
    private ManagedExecutorService managedExecutorService;


    @Override
    public SolicitudNovedadFovisDTO radicarSolicitudNovedadAutomaticaFovis(SolicitudNovedadFovisDTO solNovedadDTO, UserDTO userDTO) {
        String path = "radicarSolicitudNovedadAutomaticaFovis(SolicitudNovedadFovisDTO, UserDTO): SolicitudNovedadFovisDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            TipoTransaccionEnum tipoTransaccionNovedad = solNovedadDTO.getTipoTransaccion();
            // Se invoca el componente para la validación de la novedad automatica          
            ValidacionAutomaticaFovisCore validacionAutomaticaFovisCore = ValidacionNovedadAutomaticaFactory.getInstance()
                    .obtenerServicioNovedad(tipoTransaccionNovedad, solNovedadDTO.getParametro());
            DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO = new DatosNovedadAutomaticaFovisDTO();
            //Se verifica si se envio el parametro solo aplica para novedades automaticas 
            // de vencimiento de subsidios asignados
            if (solNovedadDTO.getParametro() != null) {
                datosNovedadAutomaticaFovisDTO = validacionAutomaticaFovisCore.validar(solNovedadDTO.getParametro());
            }
            else {
                datosNovedadAutomaticaFovisDTO = validacionAutomaticaFovisCore.validar();
            }

            // Se verifica si se crearon datos para la novedad automatica para su registro
            if ((datosNovedadAutomaticaFovisDTO != null && datosNovedadAutomaticaFovisDTO.getListaInhabilidades() != null
                    && !datosNovedadAutomaticaFovisDTO.getListaInhabilidades().isEmpty())
                    || (datosNovedadAutomaticaFovisDTO != null && datosNovedadAutomaticaFovisDTO.getListaPostulaciones() != null
                            && !datosNovedadAutomaticaFovisDTO.getListaPostulaciones().isEmpty())) {
                // Se consulta la informacion de la novedad
                ParametrizacionNovedadModeloDTO parametrizacionNovedad = consultarNovedad(tipoTransaccionNovedad);
                solNovedadDTO.setParametrizacionNovedad(parametrizacionNovedad);
                // Se realiza la registro de la solicitud de novedad
                solNovedadDTO = crearSolicitudNovedad(solNovedadDTO, userDTO);
                // Se asigna el numero de radicacion
                solNovedadDTO.setNumeroRadicacion(this.radicarSolicitud(solNovedadDTO.getIdSolicitud(), userDTO.getSedeCajaCompensacion()));
                // Almacena las postulaciones asociadas a la solicitud de novedad fovis
                almacenarSolicitudNovedadAutomaticaMasiva(solNovedadDTO.getIdSolicitudNovedadFovis(), datosNovedadAutomaticaFovisDTO);
                // Se procesa la informacion de la novedad 
                solNovedadDTO.setDatosNovedadAutomaticaFovisDTO(datosNovedadAutomaticaFovisDTO);
                resolverNovedadAutomatica(solNovedadDTO, userDTO);
                // Se envia el comunicado
                enviarComunicadoMasivo(solNovedadDTO, userDTO);
                // Se actualiza el estado de la solicitud novedad fovis a CERRADA
                actualizarEstadoSolicitudNovedadFovis(solNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_CERRADA);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return solNovedadDTO;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Crea la solicitud de novedad fovis y le asocia la postulación cuando no es automatica
     * @param solNovedadDTO
     *        Solicitud novedad fovis a registrar
     * @param userDTO
     *        Información usuario autenticado
     * @return Información solicitud novedad fovis registrada
     */
    private SolicitudNovedadFovisDTO crearSolicitudNovedad(SolicitudNovedadFovisDTO solNovedadDTO, UserDTO userDTO) {
        logger.debug("Inicia servicio crearSolicitudNovedad(SolicitudNovedadFovisDTO):SolicitudNovedadFovisDTO");

        SolicitudNovedadFovisModeloDTO solicitud = new SolicitudNovedadFovisModeloDTO();
        solicitud.setCanalRecepcion(solNovedadDTO.getCanalRecepcion());
        solicitud.setClasificacion(solNovedadDTO.getClasificacion());
        solicitud.setUsuarioRadicacion(userDTO.getNombreUsuario());
        solicitud.setCiudadSedeRadicacion(userDTO.getCiudadSedeCajaCompensacion());
        solicitud.setTipoTransaccion(solNovedadDTO.getTipoTransaccion());
        solicitud.setMetodoEnvio(solNovedadDTO.getMetodoEnvio());
        solicitud.setObservaciones(solNovedadDTO.getObservaciones());
        solicitud.setFechaCreacion((new Date()).getTime());
        solicitud.setEstadoSolicitud(EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_PRERADICADA);
        solicitud.setIdParametrizacionNovedad(solNovedadDTO.getParametrizacionNovedad().getIdNovedad());
        //Se crea la solicitud de novedad FOVIS
        solicitud = crearActualizarSolicitudNovedadFovis(solicitud);

        // Se registra la informacion de la persona y postulacion asociados a la solicitud si no es novedad automatica
        if (!solNovedadDTO.getParametrizacionNovedad().getPuntoResolucion().equals(PuntoResolucionEnum.SISTEMA_AUTOMATICO)) {
            SolicitudNovedadPersonaFovis novedadPersonaFovis = new SolicitudNovedadPersonaFovis();
            novedadPersonaFovis.setIdPostulacionFovis(solNovedadDTO.getIdPostulacion());
            TipoIdentificacionEnum tipoIdentifica = null;
            String nroIdentifica = "";
            if (PersonasFOVISUtils.listarClasificacionIntegrante().contains(solNovedadDTO.getClasificacion())
                    && !TipoTransaccionEnum.AGREGAR_MIEMBRO_HOGAR.equals(solNovedadDTO.getTipoTransaccion())) {
                tipoIdentifica = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdIntegrante();
                nroIdentifica = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdIntegrante();
            }
            else {
                tipoIdentifica = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdJefeHogar();
                nroIdentifica = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdJefeHogar();
            }
            // Se consulta la persona objeto de la novedad para asociarla
            PersonaModeloDTO persona = consultarPersona(nroIdentifica, tipoIdentifica);
            novedadPersonaFovis.setIdPersona(persona.getIdPersona());
            novedadPersonaFovis.setIdSolicitudNovedadFovis(solicitud.getIdSolicitudNovedadFovis());
            crearActualizarSolicitudNovedadPersonaFovis(novedadPersonaFovis);
        }
        // Se setean los id de la solicitud a la respuesta 
        solNovedadDTO.setIdSolicitudNovedadFovis(solicitud.getIdSolicitudNovedadFovis());
        solNovedadDTO.setIdSolicitud(solicitud.getIdSolicitud());
        solNovedadDTO.setEstadoSolicitudNovedad(solicitud.getEstadoSolicitud());
        solNovedadDTO.setTipoProceso(solNovedadDTO.getTipoTransaccion().getProceso());
        solNovedadDTO.setUsuarioRadicacion(userDTO.getNombreUsuario());
        logger.debug("Finaliza servicio crearSolicitudNovedad(SolicitudNovedadFovisDTO):SolicitudNovedadFovisDTO");
        return solNovedadDTO;
    }

    /**
     * Método que realiza el proceso en caso de que la solicitud de novedad
     * tenga como punto de resolución el back.
     * 
     * @param solicitudNovedadDTO
     *        datos de la novedad.
     * @param solicitudNovedad
     *        solicitud a modificar.
     */
    private void resolverNovedadAutomatica(SolicitudNovedadFovisDTO solNovedadDTO, UserDTO userDTO) {
        logger.debug("Inicio de método resolverNovedadFront(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");

        DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO = solNovedadDTO.getDatosNovedadAutomaticaFovisDTO();

        // Se actualizan las postulaciones, no se ejecuta convertidor ya las postulaciones que cumplen con 
        // la condicion para generar la novedad automatica de suspencion por cambio de año, solo se actualizan los estados del hogar       
        if (datosNovedadAutomaticaFovisDTO != null && datosNovedadAutomaticaFovisDTO.getListaPostulaciones() != null
                && !datosNovedadAutomaticaFovisDTO.getListaPostulaciones().isEmpty()) {
            crearActualizarListaPostulacionFOVIS(datosNovedadAutomaticaFovisDTO.getListaPostulaciones());
        }

        // Se actualizan las inhabilidades de subsidio, no se ejecuta convertidor ya las inhabilidades que cumplen con 
        // la condicion para generar la novedad automatica levantar inhabilidad sancion solo se actualiza 
        // le fecha de inhabilidad y el indicador de inhabilidad
        if (datosNovedadAutomaticaFovisDTO != null && datosNovedadAutomaticaFovisDTO.getListaInhabilidades() != null
                && !datosNovedadAutomaticaFovisDTO.getListaInhabilidades().isEmpty()) {
            crearActualizarListaInhabilidadSubsidioFovis(datosNovedadAutomaticaFovisDTO.getListaInhabilidades());
        }
        //El sistema cambia el estado de la solicitud a Novedad FOVIS automática Aprobada
        actualizarEstadoSolicitudNovedadFovis(solNovedadDTO.getIdSolicitud(), EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_APROBADA);
        logger.debug("Fin de método resolverNovedadFront(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedad solicitudNovedad)");
    }

    /**
     * Realiza la invocacion de los convertidores parametrizados para cada novedad
     * @param solNovedadDTO
     *        Informacion solicitud novedad
     * @throws ClassNotFoundException
     *         Lanzada si no se encuentra la clase
     * @throws InstantiationException
     *         Lanzada si no es posible instanciar la clase
     * @throws IllegalAccessException
     *         Lanzada en caso que no terner acceso a la clase
     */
    private void ejecutarNovedad(SolicitudNovedadFovisDTO solNovedadDTO)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        /* se invoca el servicio para dicha novedad */
        NovedadFovisCore servicioNovedad = NovedadFovisAbstractFactory.getInstance()
                .obtenerServicioNovedad(solNovedadDTO.getParametrizacionNovedad());
        // Se instancia el servicio de la novedad 
        ServiceClient servicio = servicioNovedad.transformarServicio(solNovedadDTO);
        servicio.execute();
    }

    /**
     * Método que invoca el servicio de actualización de una postulación FOVIS
     * 
     * @param postulacionFOVISDTO
     *        La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private PostulacionFOVISModeloDTO crearActualizarPostulacionFOVIS(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.info("Inicia el método crearActualizarPostulacionFOVIS");
        CrearActualizarPostulacionFOVIS service = new CrearActualizarPostulacionFOVIS(postulacionFOVISDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarPostulacionFOVIS");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización o creacion de un registro de inhabilidad subsidio FOVIS
     * 
     * @param inhabilidadSubsidioDTO
     *        La información del registro crear o actualizar
     * @return Datos del registro actualizado
     */
    private void crearActualizarListaInhabilidadSubsidioFovis(List<InhabilidadSubsidioFovisModeloDTO> inhabilidadSubsidioDTO) {
        logger.info("Inicia el método crearActualizarListaInhabilidadSubsidioFovis");
        CrearActualizarListaInhabilidadSubsidioFovis service = new CrearActualizarListaInhabilidadSubsidioFovis(inhabilidadSubsidioDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarListaInhabilidadSubsidioFovis");
    }

    /**
     * Obtiene el comunicado de acuerdo al tipo transaccion de la novedad
     * automatica registrada y se realiza el envio del comunicado de acuerdo a
     * la parametrizacion de notificacion
     * 
     * @param solicitudNovedadDTO
     *        Informacion solicitud novedad FOVIS
     * @param userDTO
     *        Informacion usuario
     */
    private void enviarComunicadoMasivo(SolicitudNovedadFovisDTO solicitudNovedadDTO, UserDTO userDTO) {
        // Novedad en registro
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();
        EtiquetaPlantillaComunicadoEnum comunicado = null;

        switch (novedad) {
            case SUSPENSION_CAMBIO_ANIO_CALENDARIO_AUTOMATICA:
            case VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA:
            case LEVANTAR_INHABILIDAD_SANCION_AUTOMATICA:
            case RECHAZO_SOLICITUDES_SUSPENDIDAS_CAMBIO_ANIO_AUTOMATICA:
                // Comunicado 97.
                comunicado = EtiquetaPlantillaComunicadoEnum.NTF_SOL_NVD_FOVIS;
                break;
            default:
                break;
        }

        if (comunicado != null) {
            NotificacionParametrizadaDTO notificacion = new NotificacionParametrizadaDTO();
            notificacion.setEtiquetaPlantillaComunicado(comunicado);
            notificacion.setProcesoEvento(solicitudNovedadDTO.getParametrizacionNovedad().getNovedad().getProceso().name());
            notificacion.setIdSolicitud(solicitudNovedadDTO.getIdSolicitud());
            notificacion.setTipoTx(solicitudNovedadDTO.getParametrizacionNovedad().getNovedad());

            enviarComunicadoConstruido(notificacion);
        }
    }

    /**
     * Invoca el servicio de inserción y/o actualización en la tabla
     * <code>SolicitudNovedadFovis</code>
     * 
     * @param solicitudNovedadFovisModeloDTO
     *        Información de la novedad fovis a registrar o actualizar
     * @return Registro actualizado
     */
    private SolicitudNovedadFovisModeloDTO crearActualizarSolicitudNovedadFovis(
            SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO) {
        logger.info("Inicia el método crearActualizarSolicitudNovedadFovis");
        CrearActualizarSolicitudNovedadFovis service = new CrearActualizarSolicitudNovedadFovis(solicitudNovedadFovisModeloDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarSolicitudNovedadFovis");
        return service.getResult();
    }

    /**
     * Invoca el servicio de inserción y/o actualización masivo en la tabla
     * <code>SolicitudNovedadPersonaFovis</code>
     * 
     * @param idSolicitudNovedad
     *        Identificador de la solicitud de novedad FOVIS
     * @param listaPostulaciones
     *        Lista de postulaciones procesadas por la novedad FOVIS
     */
    private void almacenarSolicitudNovedadAutomaticaMasiva(Long idSolicitudNovedad,
            DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO) {
        logger.info("Inicia el método crearActualizarSolicitudNovedadFovis");
        AlmacenarSolicitudNovedadAutomaticaMasiva service = new AlmacenarSolicitudNovedadAutomaticaMasiva(idSolicitudNovedad,
                datosNovedadAutomaticaFovisDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarSolicitudNovedadFovis");
    }

    /**
     * Consulta la parametrización de novedad
     * 
     * @param tipoTransaccion
     *        Tipo transacción novedad
     * @return Parametrización novedad
     */
    private ParametrizacionNovedadModeloDTO consultarNovedad(TipoTransaccionEnum tipoTransaccion) {
        ParametrizacionNovedadModeloDTO novedad = new ParametrizacionNovedadModeloDTO();
        ConsultarNovedadPorNombre consultarNovedadService = new ConsultarNovedadPorNombre(tipoTransaccion);
        consultarNovedadService.execute();
        novedad = consultarNovedadService.getResult();
        if (novedad == null) {
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        }
        return novedad;
    }

    /**
     * Envia el comunicado construido evitando abortar el proceso por excepción
     * 
     * @param notificacion
     *        Informacion notificación a enviar
     */
    private void enviarComunicadoConstruido(NotificacionParametrizadaDTO notificacion) {
        try {
            enviarCorreoParametrizado(notificacion);
        } catch (Exception e) {
            // El envío del correo del comunicado no debe abortar el proceso 
            logger.warn("No fue posible enviar el correo con el comunicado, el  proceso continuará normalmente");
        }
    }

    /**
     * Invoca el servicio de envio de correo parametrizado
     * @param notificacion
     *        Informacion notificación a enviar
     */
    private void enviarCorreoParametrizado(NotificacionParametrizadaDTO notificacion) {
        EnviarNotificacionComunicado enviarComunicado = new EnviarNotificacionComunicado(notificacion);
        enviarComunicado.execute();
    }

    @Override
    public SolicitudNovedadFovisDTO registrarIntentoSolicitudNovedadFovis(SolicitudNovedadFovisDTO solicitudNovedadFovisDTO,
            UserDTO userDTO) {
        String path = "registrarIntentoSolicitudNovedadFovis(SolicitudNovedadFovisDTO, UserDTO):Long";
        logger.debug(ConstantesComunes.INICIO_LOGGER + path);
        ParametrizacionNovedadModeloDTO parametrizacionNovedad = consultarNovedad(solicitudNovedadFovisDTO.getTipoTransaccion());
        solicitudNovedadFovisDTO.setParametrizacionNovedad(parametrizacionNovedad);
        // Se realiza la registro de la solicitud de novedad
        solicitudNovedadFovisDTO = crearSolicitudNovedad(solicitudNovedadFovisDTO, userDTO);
        // Crea el intento de postulación
        crearIntentoNovedad(solicitudNovedadFovisDTO, userDTO);
        // Se actualiza el estado de la solicitud a RECHAZADA
        actualizarEstadoSolicitudNovedadFovis(solicitudNovedadFovisDTO.getIdSolicitud(),
                EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RECHAZADA);
        // Se inicia el proceso BPM
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put(RESULTADO_RADICACION, "2");
        solicitudNovedadFovisDTO.setIdInstancia(iniciarProcesoNovedadFovis(solicitudNovedadFovisDTO.getIdSolicitud(),
                solicitudNovedadFovisDTO.getTipoTransaccion().getProceso(), parametros, userDTO));
        // Se actualiza el estado de la solicitud a CERRADA
        actualizarEstadoSolicitudNovedadFovis(solicitudNovedadFovisDTO.getIdSolicitud(),
                EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_CERRADA);
        logger.debug(ConstantesComunes.FIN_LOGGER + path);
        return solicitudNovedadFovisDTO;
    }

    @Override
    public void verificarSolicitudNovedadFovis(VerificacionNovedadFovisDTO verificacionNovedadFovis, UserDTO userDTO) {
        String path = "verificarSolicitudNovedadFovis(VerificacionNovedadFovisDTO, UserDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // Mapa parámetros proceso BPM 
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_BACK, verificacionNovedadFovis.getResultado());
            // Se realiza el procesamiento de la verificacion BACK
            procesarVerificacionSolicitudNovedadFovis(verificacionNovedadFovis, params, false, userDTO);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void verificarPNCSolicitudNovedadFovis(VerificacionNovedadFovisDTO verificacionNovedadFovis, UserDTO userDTO) {
        String path = "verificarPNCSolicitudNovedadFovis(VerificacionNovedadFovisDTO, UserDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // Mapa parámetros proceso BPM 
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_PNC, verificacionNovedadFovis.getResultado());
            // Se realiza el procesamiento de la verificacion BACK
            procesarVerificacionSolicitudNovedadFovis(verificacionNovedadFovis, params, true, userDTO);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Procesa el resultado de verificación de la solicitud novedad fovis
     * @param verificacionNovedadFovis
     *        Información de la verificación enviada desde pantalla
     * @param params
     *        Parámetros de proceso BPM a notificar
     * @param verificaPNC
     *        Indica si se esta verificando un PNC
     * @param userDTO
     *        Información usuario auenticado
     * @throws Exception
     *         Lanzada si ocurre un error actualizando la información
     */
    private void procesarVerificacionSolicitudNovedadFovis(VerificacionNovedadFovisDTO verificacionNovedadFovis, Map<String, Object> params,
            Boolean verificaPNC, UserDTO userDTO) throws Exception {
        // Consultar los datos de solicitud novedad fovis del temporal
        logger.info("verificacionNovedadFovis.getIdSolicitud() " +verificacionNovedadFovis.getIdSolicitud());
        SolicitudNovedadFovisDTO solicitudTemporal = consultarNovedadFovisTemporal(verificacionNovedadFovis.getIdSolicitud());
        logger.info("solicitudTemporal " +solicitudTemporal.toString());

        EstadoSolicitudNovedadFovisEnum estadoNuevo = null;
        // Se verifica que proceso realizar
        switch (verificacionNovedadFovis.getResultado()) {
            // Aprobar solicitud
            case CODIGO_SOLICITUD_APROBADA:
                // Si la novedad es especial se actualiza la información
                if (solicitudTemporal.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
                    if (TipoTransaccionEnum.HABILITACION_POSTULACION_SUSPENDIDA_CAMBIO_ANIO.equals(solicitudTemporal.getTipoTransaccion())) {   
                        solicitudTemporal.getDatosPostulacion().getPostulacion().setEstadoHogar(EstadoHogarEnum.HABIL_SEGUNDO_ANIO);
                    }
                    logger.info("solicitudPostulacionDTO.getListaChequeo() postulacion " +solicitudTemporal.getDatosPostulacion().getListaChequeo().toString());
                    for (ItemChequeoDTO itemChequeoDTO : solicitudTemporal.getDatosPostulacion().getListaChequeo().getListaChequeo()) {
                        logger.info("itemChequeoDTO postulacion 1.0" +itemChequeoDTO.toString());
                        logger.info("itemChequeoDTO postulacion 1.0 " +itemChequeoDTO.getIdentificadorDocumento());
                    }

                    logger.info("solicitudPostulacionDTO.getListaChequeo() " +solicitudTemporal.getDatosPostulacion().getPostulacion().getListaChequeoJefeHogar().getListaChequeo().toString());
                    for (ItemChequeoDTO itemChequeoDTO : solicitudTemporal.getDatosPostulacion().getPostulacion().getListaChequeoJefeHogar().getListaChequeo()) {
                        logger.info("itemChequeoDTO 1.0 " +itemChequeoDTO.toString());
                        logger.info("itemChequeoDTO 1.0 " +itemChequeoDTO.getIdentificadorDocumento());
                    }
                    solicitudTemporal
                            .setDatosPostulacion(this.actualizarDatosPostulacion(solicitudTemporal.getDatosPostulacion(), userDTO));
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_APROBADA;
                    // Se actualiza la postulación fovis
                    actualizarJsonPostulacion(solicitudTemporal.getDatosPostulacion().getPostulacion().getIdPostulacion(),
                            solicitudTemporal.getDatosPostulacion());
                }
                else {
                    resolverNovedad(solicitudTemporal, userDTO);
                }
                break;
            // Rechazar solicitud
            case CODIGO_RECHAZO:
                if (!verificaPNC && solicitudTemporal.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.POSTULACION_RECHAZADA;
                    // Cambia el estado hogar a RECHAZADO
                    actualizarEstadoHogar(solicitudTemporal.getIdPostulacion(), EstadoHogarEnum.RECHAZADO);
                }
                else {
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RECHAZADA;
                }
                break;
            // Escalar solicitud
            case CODIGO_ESCALAMIENTO:
                estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_ESCALADA_BACK;
                params.put(ESCALAMIENTO_FRONT, CODIGO_ESCALMIENTO_NO_FRONT);
                // Se realiza el registro del escalamiento a los analistas indicados 
                Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = registrarEscalamientoAnalistas(params,
                        verificacionNovedadFovis.getEscalamientoMiembrosHogar(),
                        verificacionNovedadFovis.getEscalamientoTecnicoConstruccion(), verificacionNovedadFovis.getEscalamientoJuridico(),
                        verificacionNovedadFovis.getIdSolicitud(), OrigenEscalamientoEnum.BACK, userDTO);
                solicitudTemporal.setEscalamientoJuridicoBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
                solicitudTemporal.setEscalamientoMiembrosHogarBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
                solicitudTemporal.setEscalamientoTecnicoConstruccionBack(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));
                break;
            // Gestionar PNC
            case CODIGO_GESTION_PNC:
                estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_NO_CONFORME_SUBSANABLE;
                break;
        }
        // Se actualiza el estado de la solicitud
        if (estadoNuevo != null) {
            actualizarEstadoSolicitudNovedadFovis(verificacionNovedadFovis.getIdSolicitud(), estadoNuevo);
            solicitudTemporal.setEstadoSolicitudNovedad(estadoNuevo);
        }
        // Se alamcena los datos temporales
        guardarDatosTemporal(solicitudTemporal);
        // Se finaliza la tarea actual
        terminarTarea(verificacionNovedadFovis.getIdTarea(), params, solicitudTemporal.getIdInstancia());
    }

    /**
     * Inicia el proceso BPM para las novedades fovis y asocia el id instancia a la solicitud
     * @param idSolicitud
     *        Identificador solicitud inicia proceso
     * @param procesoEnum
     *        Proceso BPM a iniciar
     * @param parametrosProceso
     *        Parametros del proceso a iniciar
     * @param usuario
     *        Usuario autenticado
     * @return Codigo instancia proceso iniciado
     */
    private Long iniciarProcesoNovedadFovis(Long idSolicitud, ProcesoEnum procesoEnum, Map<String, Object> parametrosProceso,
            UserDTO usuario) {
        String path = "iniciarProcesoNovedadFovis(Long, ProcesoEnum, Map<String, Object>, UserDTO):Long";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            // Se consulta la solicitud novedad fovis
            SolicitudNovedadFovisModeloDTO solicitudNovedadFovis = consultarSolicitudNovedadFovis(idSolicitud);
            // Se adiciona la informacion de la solicitud y el usuario
            parametrosProceso.put(ID_SOLICITUD, idSolicitud);
            parametrosProceso.put(USUARIO_FRONT, usuario.getNombreUsuario());
            // Se inicia el proceso
            IniciarProceso iniciarProcesoNovedadFovisService = new IniciarProceso(procesoEnum, parametrosProceso);
            iniciarProcesoNovedadFovisService.execute();
            Long idInstancia = iniciarProcesoNovedadFovisService.getResult();
            // Se actualiza la solicitud de novedad fovis
            solicitudNovedadFovis.setIdInstanciaProceso(idInstancia.toString());
            crearActualizarSolicitudNovedadFovis(solicitudNovedadFovis);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return idInstancia;
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + path);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Invoca la consulta de solicitud de novedad fovis
     * @param idSolicitud
     *        Identificador global solicitud novedad
     * @return Informacion solicitud novedad registrada
     */
    private SolicitudNovedadFovisModeloDTO consultarSolicitudNovedadFovis(Long idSolicitud) {
        ConsultarSolicitudNovedadFovis consultarSolicitudNovedadFovisService = new ConsultarSolicitudNovedadFovis(idSolicitud);
        consultarSolicitudNovedadFovisService.execute();
        return consultarSolicitudNovedadFovisService.getResult();
    }

    /**
     * 325-078 actualiza los datos de la postulación modificados en la novedad.
     * 
     * @param solicitudPostulacionDTO
     * @param estadoSolicitud
     * @return
     */
    private SolicitudPostulacionFOVISDTO actualizarDatosPostulacion(SolicitudPostulacionFOVISDTO solicitudPostulacionDTO, UserDTO userDTO) {

        /* Se actualiza el Jefe de Hogar */
        JefeHogarModeloDTO jefeHogarModeloDTO = solicitudPostulacionDTO.getPostulacion().getJefeHogar();
        jefeHogarModeloDTO = crearActualizarJefeHogar(jefeHogarModeloDTO);

        /* Se consultan los datos del afiliado asociado al jefe de hogar. */
        AfiliadoModeloDTO afiliadoJefeHogar = buscarAfiliado(jefeHogarModeloDTO.getIdAfiliado());

        /* Se actualiza la ubicacion del Jefe de hogar */
        UbicacionModeloDTO ubicacionJefeHogarDTO = jefeHogarModeloDTO.getUbicacionModeloDTO();
        if (afiliadoJefeHogar.getUbicacionModeloDTO() != null) {
            ubicacionJefeHogarDTO.setIdUbicacion(afiliadoJefeHogar.getUbicacionModeloDTO().getIdUbicacion());
        }
        ubicacionJefeHogarDTO = crearActualizarUbicacion(ubicacionJefeHogarDTO);
        solicitudPostulacionDTO.getPostulacion().getJefeHogar().setUbicacionModeloDTO(ubicacionJefeHogarDTO);        

        // Almacena en Oferente 
        OferenteModeloDTO oferente = null;
        if (solicitudPostulacionDTO.getOferente() != null && solicitudPostulacionDTO.getOferente().getOferente() != null) {
            oferente = crearActualizarOferente(solicitudPostulacionDTO.getOferente().getOferente());
        }
        // Almacena en ProyectoSolucionVivienda 
        ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO = solicitudPostulacionDTO.getPostulacion()
                .getProyectoSolucionVivienda();
        if (proyectoSolucionViviendaDTO != null && proyectoSolucionViviendaDTO.getNombreProyecto() != null) {
            proyectoSolucionViviendaDTO.setOferente(oferente);
            proyectoSolucionViviendaDTO = crearActualizarProyectoSolucionVivienda(
                    solicitudPostulacionDTO.getPostulacion().getProyectoSolucionVivienda());
            solicitudPostulacionDTO.getPostulacion().setProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        }

        PostulacionFOVISModeloDTO postulacionFOVISModeloDTO = solicitudPostulacionDTO.getPostulacion();
        /* Se crean condiciones especiales Jefe de Hogar */
        if (solicitudPostulacionDTO.getCondicionesEspeciales() != null) {
            this.crearListaCondicionEspecialPersona(jefeHogarModeloDTO.getTipoIdentificacion(),
                    jefeHogarModeloDTO.getNumeroIdentificacion(), solicitudPostulacionDTO.getCondicionesEspeciales(), postulacionFOVISModeloDTO.getIdPostulacion());
        }
        
        
        //Almacena los datos de la ubicacion de la vivienda
        if (proyectoSolucionViviendaDTO != null
                && solicitudPostulacionDTO.getPostulacion().getUbicacionViviendaMismaProyecto() != null
                && solicitudPostulacionDTO.getPostulacion().getUbicacionViviendaMismaProyecto()) {
            postulacionFOVISModeloDTO.setUbicacionViviendaMismaProyecto(Boolean.TRUE);
            postulacionFOVISModeloDTO.setUbicacionVivienda(proyectoSolucionViviendaDTO.getUbicacionProyecto());
            solicitudPostulacionDTO.getPostulacion().setUbicacionVivienda(proyectoSolucionViviendaDTO.getUbicacionProyecto());
        }
        else if (solicitudPostulacionDTO.getPostulacion().getUbicacionVivienda() != null) {
            /* Se crea la Ubicación de la Vivienda */
            CrearActualizarUbicacion ubicacion = new CrearActualizarUbicacion(
                    solicitudPostulacionDTO.getPostulacion().getUbicacionVivienda());
            ubicacion.execute();
            UbicacionModeloDTO ubicacionResult = ubicacion.getResult();
            postulacionFOVISModeloDTO.setUbicacionViviendaMismaProyecto(Boolean.FALSE);
            postulacionFOVISModeloDTO.setUbicacionVivienda(ubicacionResult);
            solicitudPostulacionDTO.getPostulacion().setUbicacionVivienda(ubicacionResult);
        }
        //        postulacionFOVISModeloDTO.setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());
        postulacionFOVISModeloDTO.setProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        /* Se almacenan los datos de la postulación. */
        postulacionFOVISModeloDTO = crearActualizarPostulacionFOVIS(postulacionFOVISModeloDTO);

        solicitudPostulacionDTO.setPostulacion(postulacionFOVISModeloDTO);
        logger.info("solicitudPostulacionDTO.getListaChequeo() " +solicitudPostulacionDTO.getListaChequeo().toString());
        for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getListaChequeo().getListaChequeo()) {
            logger.info("itemChequeoDTO postulacion" +itemChequeoDTO.toString());
            logger.info("itemChequeoDTO postulacion" +itemChequeoDTO.getIdentificadorDocumento());
        }
        ListaChequeoDTO listaChequeoPostulacion = solicitudPostulacionDTO.getListaChequeo();
        listaChequeoPostulacion.setNumeroIdentificacion(jefeHogarModeloDTO.getNumeroIdentificacion());
        listaChequeoPostulacion.setTipoIdentificacion(jefeHogarModeloDTO.getTipoIdentificacion());
        listaChequeoPostulacion.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
        listaChequeoPostulacion.setListaFOVIS(Boolean.TRUE);
        for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getListaChequeo().getListaChequeo()) {
            logger.info("itemChequeoDTO postulacion 2.0 " +itemChequeoDTO.toString());
            logger.info("itemChequeoDTO postulacion 2.0 " +itemChequeoDTO.getIdentificadorDocumento());
        }
        // Almacena lista de chequeo documental de postulación.
        guardarListaChequeo(listaChequeoPostulacion);
        logger.info("solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar() " +solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar());
        for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar().getListaChequeo()) {
            logger.info("itemChequeoDTO " +itemChequeoDTO.toString());
            logger.info("itemChequeoDTO " +itemChequeoDTO.getIdentificadorDocumento());
        }
        ListaChequeoDTO listaChequeoJefeHogar = solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar();
        listaChequeoJefeHogar.setNumeroIdentificacion(jefeHogarModeloDTO.getNumeroIdentificacion());
        listaChequeoJefeHogar.setTipoIdentificacion(jefeHogarModeloDTO.getTipoIdentificacion());
        listaChequeoJefeHogar.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
        listaChequeoJefeHogar.setListaFOVIS(Boolean.TRUE);
        for (ItemChequeoDTO itemChequeoDTO : solicitudPostulacionDTO.getPostulacion().getListaChequeoJefeHogar().getListaChequeo()) {
            logger.info("itemChequeoDTO 2.0 " +itemChequeoDTO.toString());
            logger.info("itemChequeoDTO 2.0 " +itemChequeoDTO.getIdentificadorDocumento());
        }
        // Almacena lista de chequeo documental de JefeHogar.
        guardarListaChequeo(listaChequeoJefeHogar);

        List<Long> integrantesActivos = new ArrayList<>();
        if (solicitudPostulacionDTO.getIntegrantesHogar() != null && !solicitudPostulacionDTO.getIntegrantesHogar().isEmpty()) {
            for (IntegranteHogarModeloDTO integranteHogarModeloDTO : solicitudPostulacionDTO.getIntegrantesHogar()) {
                integranteHogarModeloDTO.setIdJefeHogar(jefeHogarModeloDTO.getIdJefeHogar());
                integranteHogarModeloDTO.setIdPostulacion(postulacionFOVISModeloDTO.getIdPostulacion());
                if (integranteHogarModeloDTO.getEstadoHogar() == null) {
                    integranteHogarModeloDTO.setEstadoHogar(EstadoFOVISHogarEnum.ACTIVO);
                }
                integranteHogarModeloDTO = crearActualizarIntegranteHogar(integranteHogarModeloDTO);
                integrantesActivos.add(integranteHogarModeloDTO.getIdIntegranteHogar());
                
                /* Se crean condiciones especiales de cada Integrante del Hogar */
                if (integranteHogarModeloDTO.getCondicionesEspeciales() != null) {
                    this.crearListaCondicionEspecialPersona(integranteHogarModeloDTO.getTipoIdentificacion(),
                            integranteHogarModeloDTO.getNumeroIdentificacion(), integranteHogarModeloDTO.getCondicionesEspeciales(),postulacionFOVISModeloDTO.getIdPostulacion());
                }
                
                ListaChequeoDTO listaChequeoIntegranteHogar = integranteHogarModeloDTO.getListaChequeo();
                listaChequeoIntegranteHogar.setNumeroIdentificacion(integranteHogarModeloDTO.getNumeroIdentificacion());
                listaChequeoIntegranteHogar.setTipoIdentificacion(integranteHogarModeloDTO.getTipoIdentificacion());
                listaChequeoIntegranteHogar.setIdSolicitudGlobal(solicitudPostulacionDTO.getIdSolicitud());
                listaChequeoIntegranteHogar.setListaFOVIS(Boolean.TRUE);
                // Almacena lista de chequeo documental de cada integrante de Hogar
                guardarListaChequeo(listaChequeoIntegranteHogar);
            }
        }
        InactivarIntegrantesHogarNoRelacionados inactivarIntegrantes = new InactivarIntegrantesHogarNoRelacionados(
                postulacionFOVISModeloDTO.getIdPostulacion(), jefeHogarModeloDTO.getIdJefeHogar(), integrantesActivos);
        inactivarIntegrantes.execute();

        // Almacena en AhorroPrevio 3.1.2.5
        crearActualizarAhorroPrevio(solicitudPostulacionDTO.getPostulacion());

        // Almacena en RecursoComplementario 3.1.2.6
        crearActualizarRecursoComplementario(solicitudPostulacionDTO.getPostulacion());

        return solicitudPostulacionDTO;
    }

    /**
     * Método que invoca el servicio de consulta de un afiliado
     * 
     * @param idAfiliado
     *        Id del afiliado
     * @return Objeto <code>AfiliadoModeloDTO</code> con la información del afiliado
     */
    private AfiliadoModeloDTO buscarAfiliado(Long idAfiliado) {
        ConsultarAfiliadoPorId service = new ConsultarAfiliadoPorId(idAfiliado);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>JefeHogar</code>
     * 
     * @param jefeHogarDTO
     *        Información del jefe de hogar a actualizar
     * @return El registro actualizado
     */
    private JefeHogarModeloDTO crearActualizarJefeHogar(JefeHogarModeloDTO jefeHogarDTO) {
        CrearActualizarJefeHogar service = new CrearActualizarJefeHogar(jefeHogarDTO);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de creación o actualización de un registro
     * en la tabla <code>Ubicacion</code>
     * 
     * @param ubicacionDTO
     *        La información del registro a actualizar
     * @return El registro actualizado
     */
    private UbicacionModeloDTO crearActualizarUbicacion(UbicacionModeloDTO ubicacionDTO) {
        CrearActualizarUbicacion service = new CrearActualizarUbicacion(ubicacionDTO);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de creación de un conjunto de condiciones
     * especiales por persona
     * 
     * @param idPersona
     *        Identificador único de la persona
     * @param listaCondicionesEspeciales
     *        Lista de identificadores de las condiciones especiales
     * @param idPostulacion
     *        Identificador de la postulacion
     */
    private void crearListaCondicionEspecialPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            List<NombreCondicionEspecialEnum> listaCondicionesEspeciales, Long idPostulacion) {
        if (listaCondicionesEspeciales != null && !listaCondicionesEspeciales.isEmpty()) {
            CrearListaCondicionEspecialPersona service = new CrearListaCondicionEspecialPersona(numeroIdentificacion, tipoIdentificacion,
                    listaCondicionesEspeciales, idPostulacion);
            service.execute();
        }
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>IntegranteHogar</code>
     * 
     * @param integranteHogarDTO
     *        Información del integrante hogar a actualizar
     * @return El registro actualizado
     */
    private IntegranteHogarModeloDTO crearActualizarIntegranteHogar(IntegranteHogarModeloDTO integranteHogarDTO) {
        CrearActualizarIntegranteHogar service = new CrearActualizarIntegranteHogar(integranteHogarDTO);
        service.execute();
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de almacenamiento de una lista de chequeo
     * de requisitos documentales
     * 
     * @param listaChequeo
     *        La información de la lista de chequeo a almacenar
     */
    private void guardarListaChequeo(ListaChequeoDTO listaChequeo) {
        for(ItemChequeoDTO itemChequeoDTO : listaChequeo.getListaChequeo()){
            if(itemChequeoDTO.getIdentificadorDocumento() == null){
                itemChequeoDTO.setIdentificadorDocumento(itemChequeoDTO.getIdentificadorDocumentoPrevio());
                logger.info("itemChequeoDTO 3.0 " +itemChequeoDTO.getIdentificadorDocumento());
            }
        }
        GuardarListaChequeo service = new GuardarListaChequeo(listaChequeo);
        service.execute();
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>RecursoComplementario</code>
     * 
     * @param recursoComplementarioDTO
     *        Información del recurso complementario a actualizar
     * @return El registro actualizado
     */
    private void crearActualizarRecursoComplementario(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.info("Inicia el método crearActualizarRecursoComplementario");
        List<RecursoComplementarioModeloDTO> recursosComplementariosDTO = new ArrayList<>();

        if (postulacionFOVISDTO.getAhorroOtrasModalidades() != null && postulacionFOVISDTO.getAhorroOtrasModalidades().getValor() != null) {
            postulacionFOVISDTO.getAhorroOtrasModalidades().setNombre(TipoRecursoComplementarioEnum.AHORRO_OTRAS_MODALIDADES);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAhorroOtrasModalidades());
        }
        if (postulacionFOVISDTO.getAportesEnteTerritorial() != null && postulacionFOVISDTO.getAportesEnteTerritorial().getValor() != null) {
            postulacionFOVISDTO.getAportesEnteTerritorial().setNombre(TipoRecursoComplementarioEnum.APORTES_ENTE_TERRITORIAL);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAportesEnteTerritorial());
        }
        if (postulacionFOVISDTO.getAportesSolidarios() != null && postulacionFOVISDTO.getAportesSolidarios().getValor() != null) {
            postulacionFOVISDTO.getAportesSolidarios().setNombre(TipoRecursoComplementarioEnum.APORTES_SOLIDARIOS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getAportesSolidarios());

        }
        if (postulacionFOVISDTO.getCesantiasNoInmovilizadas() != null
                && postulacionFOVISDTO.getCesantiasNoInmovilizadas().getValor() != null) {
            postulacionFOVISDTO.getCesantiasNoInmovilizadas().setNombre(TipoRecursoComplementarioEnum.CESANTIAS_NO_INMOVILIZADAS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getCesantiasNoInmovilizadas());
        }
        if (postulacionFOVISDTO.getCreditoAprobado() != null && postulacionFOVISDTO.getCreditoAprobado().getValor() != null) {
            postulacionFOVISDTO.getCreditoAprobado().setNombre(TipoRecursoComplementarioEnum.CREDITO_APROBADO);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getCreditoAprobado());
        }
        if (postulacionFOVISDTO.getDonacionOtrasEntidades() != null && postulacionFOVISDTO.getDonacionOtrasEntidades().getValor() != null) {
            postulacionFOVISDTO.getDonacionOtrasEntidades().setNombre(TipoRecursoComplementarioEnum.DONACION_OTRAS_ENTIDADES);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getDonacionOtrasEntidades());
        }
        if (postulacionFOVISDTO.getEvaluacionCrediticia() != null && postulacionFOVISDTO.getEvaluacionCrediticia().getValor() != null) {
            postulacionFOVISDTO.getEvaluacionCrediticia().setNombre(TipoRecursoComplementarioEnum.EVALUACION_CREDITICIA);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getEvaluacionCrediticia());
        }
        if (postulacionFOVISDTO.getOtrosRecursos() != null && postulacionFOVISDTO.getOtrosRecursos().getValor() != null) {
            postulacionFOVISDTO.getOtrosRecursos().setNombre(TipoRecursoComplementarioEnum.OTROS_RECURSOS);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getOtrosRecursos());
        }
        if (postulacionFOVISDTO.getValorAvanceObra() != null && postulacionFOVISDTO.getValorAvanceObra().getValor() != null) {
            postulacionFOVISDTO.getValorAvanceObra().setNombre(TipoRecursoComplementarioEnum.VALOR_AVANCE_OBRA);
            recursosComplementariosDTO.add(postulacionFOVISDTO.getValorAvanceObra());
        }
        if (!recursosComplementariosDTO.isEmpty()) {
            CrearActualizarRecursoComplementario service = new CrearActualizarRecursoComplementario(postulacionFOVISDTO.getIdPostulacion(),
                    recursosComplementariosDTO);
            service.execute();
        }
        logger.info("Finaliza el método crearActualizarRecursoComplementario");
    }

    /**
     * Método que invoca el servicio de actualización en la tabla
     * <code>AhorroPrevio</code>
     * 
     * @param ahorroPrevioDTO
     *        Información del ahorro previo a actualizar
     * @return El registro actualizado
     */
    private void crearActualizarAhorroPrevio(PostulacionFOVISModeloDTO postulacionFOVISDTO) {
        logger.info("Inicia el método crearActualizarAhorroPrevio");
        List<AhorroPrevioModeloDTO> ahorrosPreviosDTO = new ArrayList<>();

        if (postulacionFOVISDTO.getAhorroProgramado() != null && postulacionFOVISDTO.getAhorroProgramado().getValor() != null) {
            postulacionFOVISDTO.getAhorroProgramado().setNombreAhorro(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAhorroProgramado());
        }
        if (postulacionFOVISDTO.getAhorroProgramadoContractual() != null
                && postulacionFOVISDTO.getAhorroProgramadoContractual().getValor() != null) {
            postulacionFOVISDTO.getAhorroProgramadoContractual()
                    .setNombreAhorro(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAhorroProgramadoContractual());
        }
        if (postulacionFOVISDTO.getAportesPeriodicos() != null && postulacionFOVISDTO.getAportesPeriodicos().getValor() != null) {
            postulacionFOVISDTO.getAportesPeriodicos().setNombreAhorro(TipoAhorroPrevioEnum.APORTES_PERIODICOS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getAportesPeriodicos());
        }
        if (postulacionFOVISDTO.getCesantiasInmovilizadas() != null && postulacionFOVISDTO.getCesantiasInmovilizadas().getValor() != null) {
            postulacionFOVISDTO.getCesantiasInmovilizadas().setNombreAhorro(TipoAhorroPrevioEnum.CESANTIAS_INMOVILIZADAS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCesantiasInmovilizadas());
        }
        if (postulacionFOVISDTO.getCuotaInicial() != null && postulacionFOVISDTO.getCuotaInicial().getValor() != null) {
            postulacionFOVISDTO.getCuotaInicial().setNombreAhorro(TipoAhorroPrevioEnum.CUOTA_INICIAL);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCuotaInicial());
        }
        if (postulacionFOVISDTO.getCuotasPagadas() != null && postulacionFOVISDTO.getCuotasPagadas().getValor() != null) {
            postulacionFOVISDTO.getCuotasPagadas().setNombreAhorro(TipoAhorroPrevioEnum.CUOTAS_PAGADAS);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getCuotasPagadas());
        }
        if (postulacionFOVISDTO.getValorLoteTerreno() != null && postulacionFOVISDTO.getValorLoteTerreno().getValor() != null) {
            postulacionFOVISDTO.getValorLoteTerreno().setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_O_TERRENO_PROPIO);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteTerreno());
        }
        if (postulacionFOVISDTO.getValorLoteOPV() != null && postulacionFOVISDTO.getValorLoteOPV().getValor() != null) {
            postulacionFOVISDTO.getValorLoteOPV().setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_OPV);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteOPV());
        }
        if (postulacionFOVISDTO.getValorLoteSubsidioMunicipal() != null
                && postulacionFOVISDTO.getValorLoteSubsidioMunicipal().getValor() != null) {
            postulacionFOVISDTO.getValorLoteSubsidioMunicipal()
                    .setNombreAhorro(TipoAhorroPrevioEnum.VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL);
            ahorrosPreviosDTO.add(postulacionFOVISDTO.getValorLoteSubsidioMunicipal());
        }
        if (!ahorrosPreviosDTO.isEmpty()) {
            CrearActualizarAhorroPrevio service = new CrearActualizarAhorroPrevio(postulacionFOVISDTO.getIdPostulacion(),
                    ahorrosPreviosDTO);
            service.execute();
        }
        logger.info("Finaliza el método crearActualizarAhorroPrevio");
    }

    /**
     * Método que invoca el servicio de actualización de un oferente
     * 
     * @param oferenteDTO
     *        Información del oferente
     * @return El registro actualizado
     */
    private OferenteModeloDTO crearActualizarOferente(OferenteModeloDTO oferenteDTO) {
        logger.info("Inicia el método crearActualizarOferente");
        CrearActualizarOferente service = new CrearActualizarOferente(oferenteDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarOferente");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de actualización de un proyecto de vivienda
     * 
     * @param proyectoSolucionViviendaDTO
     *        La información del registro a actualizar
     * @return Datos del registro actualizado
     */
    private ProyectoSolucionViviendaModeloDTO crearActualizarProyectoSolucionVivienda(
            ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaDTO) {
        logger.info("Inicia el método crearActualizarProyectoSolucionVivienda");
        CrearActualizarProyectoSolucionVivienda service = new CrearActualizarProyectoSolucionVivienda(proyectoSolucionViviendaDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarProyectoSolucionVivienda");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio de radicación de una solicitud
     * 
     * @param idSolicitudGlobal
     *        Identificador único de la solicitud global
     * @param sedeCajaCompensacion
     *        Sede de la CCF del usuario autenticado
     */
    private String radicarSolicitud(Long idSolicitudGlobal, String sedeCajaCompensacion) {
        logger.debug("Inicia radicarSolicitud(idSolicitud, sedeCajaCompensacion)");
        RadicarSolicitud service = new RadicarSolicitud(idSolicitudGlobal, sedeCajaCompensacion);
        service.execute();
        logger.debug("Finaliza radicarSolicitud(idSolicitud, sedeCajaCompensacion)");
        return service.getResult();
    }

    /**
     * Método que crea un intento de novedad
     * 
     * @param solNovedadDTO
     *        Solicitud para la creación del intento.
     */
    private IntentoNovedadDTO crearIntentoNovedad(SolicitudNovedadFovisDTO solicitudNovedadFovisDTO, UserDTO userDTO) {
        logger.debug("Inicio de método crearIntentoNovedad(IntentoNovedad intentoNovedad)");

        IntentoNovedadDTO intentoNovedadDTO = new IntentoNovedadDTO();
        intentoNovedadDTO.setCausaIntentoFallido(solicitudNovedadFovisDTO.getCausaIntentoFallido());
        intentoNovedadDTO.setFechaCreacion(new Date());
        intentoNovedadDTO.setIdSolicitud(solicitudNovedadFovisDTO.getIdSolicitud());
        intentoNovedadDTO.setTipoTransaccion(solicitudNovedadFovisDTO.getTipoTransaccion());

        if (solicitudNovedadFovisDTO.getDatosPostulacion().getListaChequeo() != null
                && solicitudNovedadFovisDTO.getDatosPostulacion().getListaChequeo().getListaChequeo() != null) {
            List<Long> requisitos = new ArrayList<>();
            for (ItemChequeoDTO itemChequeoDTO : solicitudNovedadFovisDTO.getDatosPostulacion().getListaChequeo().getListaChequeo()) {
                if ((itemChequeoDTO.getCumpleRequisito() != null && !itemChequeoDTO.getCumpleRequisito())
                        || (itemChequeoDTO.getCumpleRequisitoBack() != null && !itemChequeoDTO.getCumpleRequisitoBack())) {
                    requisitos.add(itemChequeoDTO.getIdRequisito());
                }
            }
            intentoNovedadDTO.setRequisitos(requisitos);
        }
        CrearIntentoNovedad crearIntentoNovedad = new CrearIntentoNovedad(intentoNovedadDTO);
        crearIntentoNovedad.execute();
        intentoNovedadDTO.setIdIntentoNovedad(crearIntentoNovedad.getResult());
        logger.debug("Fin de método crearIntentoNovedad(IntentoNovedad intentoNovedad)");
        return intentoNovedadDTO;
    }

    /**
     * Invoca el servicio que realiza la actualización del estado de la solicitud de novedad fovis
     * @param idSolicitud
     *        Identificador global solicitud novedad
     * @param estadoSolicitud
     *        Nuevo estado de solicitud
     */
    private void actualizarEstadoSolicitudNovedadFovis(Long idSolicitud, EstadoSolicitudNovedadFovisEnum estadoSolicitud) {
        ActualizarEstadoSolicitudNovedadFovis actualizarEstadoSolNovedadFovis = new ActualizarEstadoSolicitudNovedadFovis(idSolicitud,
                estadoSolicitud);
        actualizarEstadoSolNovedadFovis.execute();
    }

    @Override
    public void registrarResultadoAnalisis(Long idSolicitud, Long idTarea, String observaciones) {
        // Se consulta la solicitud con identificador
        ConsultarSolicitudAnalisis consultarSolicitudAnalisis = new ConsultarSolicitudAnalisis(idSolicitud);
        consultarSolicitudAnalisis.execute();
        SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisis = consultarSolicitudAnalisis.getResult();
        // Se actualiza la solicitud con las observaciones
        solicitudAnalisis.setObservaciones(observaciones);
        CrearActualizarSolicitudAnalisisNovedadFOVIS crearActualizarSolicitudAnalisisNovedadFOVIS = new CrearActualizarSolicitudAnalisisNovedadFOVIS(
                solicitudAnalisis);
        crearActualizarSolicitudAnalisisNovedadFOVIS.execute();
        // Se cierra la solicitud
        ActualizarEstadoSolicitudAnalisisNovedadFOVIS actualizarEstadoSolicitudAnalisis = new ActualizarEstadoSolicitudAnalisisNovedadFOVIS(
                EstadoSolicitudAnalisisNovedadFovisEnum.CERRADA, idSolicitud);
        actualizarEstadoSolicitudAnalisis.execute();
        // Termina la tarea
        terminarTarea(idTarea, null, null);
    }

    /**
     * Invoca el servicio para terminar la tarea BPM
     * @param idTarea
     *        Identificador tarea
     * @param params
     *        Parametros para cerrar la tarea
     * @param idInstanciaProceso
     *        Identificador de la instancia del proceso, util cuando no se conoce el id de tarea
     */
    private void terminarTarea(Long idTarea, Map<String, Object> params, Long idInstanciaProceso) {
        if (params == null) {
            params = new HashMap<>();
        }
        Long idTareaActiva = idTarea;
        if (idTarea == null) {
            idTareaActiva = consultarTareaActiva(idInstanciaProceso);
        }
        TerminarTarea terminarTarea = new TerminarTarea(idTareaActiva, params);
        terminarTarea.execute();
    }

    /**
     * Método que hace la peticion REST al servicio de obtener tarea activa en el BPM
     * 
     * @param idInstanciaProceso
     *        <code>String</code> El identificador de la Instancia Proceso
     *        Afiliacion de la Persona
     * 
     * @return <code>Long</code> El identificador de la tarea Activa
     */
    private Long consultarTareaActiva(Long idInstanciaProceso) {
        ObtenerTareaActiva obtenerTareaActivaService = new ObtenerTareaActiva(idInstanciaProceso);
        obtenerTareaActivaService.execute();
        Map<String, Object> mapResult = (Map<String, Object>) obtenerTareaActivaService.getResult();
        return ((Integer) mapResult.get("idTarea")).longValue();
    }

    /**
     * Realiza la verificacion de los escalamientos generados, registrandolos y agregando el parametro de nombre de usuario analista para el
     * proceso BPM
     * @param parametros
     *        Mapa con los parametros para el proceso BPM
     * @param escalamientoHogar
     *        Información del escalamiento al analista Miembros Hogar
     * @param escalamientoTecnico
     *        Información del escalamiento al analista Tecnico
     * @param escalamientoJuridico
     *        Información del escalimiento al analista Juridico
     * @param idSolicitud
     *        Identificador solicitud ejecuta escalamiento
     * @param userDTO
     *        Información usuario autenticado
     * @return Mapa con el tipo de analista y el escalamiento registrado
     */
    private Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> registrarEscalamientoAnalistas(Map<String, Object> parametros,
            EscalamientoSolicitudDTO escalamientoHogar, EscalamientoSolicitudDTO escalamientoTecnico,
            EscalamientoSolicitudDTO escalamientoJuridico, Long idSolicitud, OrigenEscalamientoEnum origen, UserDTO userDTO) {
        logger.debug("Inicia método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
        Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = new HashMap<>();
        if (escalamientoHogar != null && escalamientoHogar.getDestinatario() != null) {
            escalamientoHogar.setOrigen(origen);
            escalamientoHogar = escalarSolicitud(idSolicitud, escalamientoHogar, userDTO);
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR, escalamientoHogar);
            parametros.put(USUARIO_ANALISTA_HOGAR, escalamientoHogar.getDestinatario());
        }
        if (escalamientoJuridico != null && escalamientoJuridico.getDestinatario() != null) {
            escalamientoJuridico.setOrigen(origen);
            escalamientoJuridico = escalarSolicitud(idSolicitud, escalamientoJuridico, userDTO);
            parametros.put(USUARIO_ANALISTA_JURIDICO, escalamientoJuridico.getDestinatario());
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO, escalamientoJuridico);
        }
        if (escalamientoTecnico != null && escalamientoTecnico.getDestinatario() != null) {
            escalamientoTecnico.setOrigen(origen);
            escalamientoTecnico = escalarSolicitud(idSolicitud, escalamientoTecnico, userDTO);
            parametros.put(USUARIO_ANALISTA_TECNICO, escalamientoTecnico.getDestinatario());
            escalamientos.put(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO, escalamientoTecnico);
        }
        logger.debug("Finaliza método registrarEscalamientoAnalistas(Map, Object, UserDTO)");
        return escalamientos;
    }

    /**
     * Invoca el servicio que realiza el registro del escalamiento al analista
     * @param idSolicitud
     *        Identificador solicitud que es escalda
     * @param escalamientoDTO
     *        Información del escalmiento a registrar
     * @param usuario
     *        Información del usuario logueado
     * @return Informacion registrada del escalmiento
     */
    private EscalamientoSolicitudDTO escalarSolicitud(Long idSolicitud, EscalamientoSolicitudDTO escalamientoDTO, UserDTO usuario) {
        // Se agregan los datos de creacion del escalamiento
        escalamientoDTO.setFechaCreacion(new Date());
        escalamientoDTO.setUsuarioCreacion(usuario != null ? usuario.getNombreUsuario() : null);
        RegistrarEscalamientoSolicitud escalarSolicitud = new RegistrarEscalamientoSolicitud(idSolicitud, escalamientoDTO);
        escalarSolicitud.execute();
        return escalarSolicitud.getResult();
    }

    /**
     * Invoca el servicio que guarda los datos temporales asociados a una solicitud
     * @param solicitudNovedadFovisDTO
     *        Informacion solicitud novedad fovis
     * @throws JsonProcessingException
     *         Lanzada si ocurre un error durante la conversión
     */
    private void guardarDatosTemporal(SolicitudNovedadFovisDTO solicitudNovedadFovisDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        GuardarDatosTemporales guardarDatosTemporalesService = new GuardarDatosTemporales(solicitudNovedadFovisDTO.getIdSolicitud(),
                mapper.writeValueAsString(solicitudNovedadFovisDTO));
        guardarDatosTemporalesService.execute();
    }

    @Override
    public SolicitudNovedadFovisDTO consultarNovedadFovisTemporal(Long idSolicitudGlobal) {
        String path = "consultarNovedadFovisTemporal(Long):SolicitudNovedadFovisDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            String jsonPayload = consultarDatosTemporales(idSolicitudGlobal);
            ObjectMapper mapper = new ObjectMapper();
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return mapper.readValue(jsonPayload, SolicitudNovedadFovisDTO.class);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Invoca el servicio de consulta de datos temporales por identificador solicitud
     * @param idSolicitud
     *        Identificador global solicitud
     * @return Payload de los datos temporales
     */
    private String consultarDatosTemporales(Long idSolicitud) {
        ConsultarDatosTemporales consultarDatosTemporalesService = new ConsultarDatosTemporales(idSolicitud);
        consultarDatosTemporalesService.execute();
        return consultarDatosTemporalesService.getResult();
    }

    @Override
    public SolicitudNovedadFovisDTO radicarSolicitudNovedad(SolicitudNovedadFovisDTO infoSolicitud, UserDTO userDTO) {
        String path = "radicarSolicitudNovedad(SolicitudNovedadFovisDTO, UserDTO): SolicitudNovedadFovisDTO";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            TipoTransaccionEnum tipoTransaccionNovedad = infoSolicitud.getTipoTransaccion();
            // Se consulta la informacion de la novedad
            ParametrizacionNovedadModeloDTO parametrizacionNovedad = consultarNovedad(tipoTransaccionNovedad);
            infoSolicitud.setParametrizacionNovedad(parametrizacionNovedad);
            // Se ejecuta la validacion de reglas de negocio siempre que no se haya indicado continuar el proceso
            if (infoSolicitud.getContinuaProceso() == null || !infoSolicitud.getContinuaProceso()) {
                infoSolicitud.setResultadoValidacion(validarNovedad(infoSolicitud));
            }
            // Si las validaciones son exitosas o se continuo con el proceso se realiza la radicacion de la solicitud
            if (infoSolicitud.getResultadoValidacion().equals(ResultadoRadicacionSolicitudEnum.EXITOSA)
                    || (infoSolicitud.getContinuaProceso() != null && infoSolicitud.getContinuaProceso())) {
                // Se registra la soliciutd como preradicada, cuando se acepte el resultado se cambia el estado radicada
                if (infoSolicitud.getIdSolicitud() == null) {
                    // Se realiza la registro de la solicitud de novedad
                    infoSolicitud = crearSolicitudNovedad(infoSolicitud, userDTO);
                    // Se asigna el numero de radicacion
                    infoSolicitud
                            .setNumeroRadicacion(this.radicarSolicitud(infoSolicitud.getIdSolicitud(), userDTO.getSedeCajaCompensacion()));
                }
                else {
                    // Se actualiza el estado de la solicitud a RADICADA
                    actualizarEstadoSolicitudNovedadFovis(infoSolicitud.getIdSolicitud(),
                            EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RADICADA);
                }
                // Si el punto de resolución es BACK y no se indico continuar la radicación se devuelve a pantalla el resultado de validacion exitoso
                // Se solicita el resultado de radicacion para el inicio del proceso BPM
                if (infoSolicitud.getParametrizacionNovedad().getPuntoResolucion().equals(PuntoResolucionEnum.BACK)
                        && (infoSolicitud.getContinuaProceso() == null || !infoSolicitud.getContinuaProceso())) {
                    return infoSolicitud;
                }
                // Si la novedad es especial se ejecuta la actualizacion de la postulación y el registro de la lista de chequeo
                if (tipoTransaccionNovedad.getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
                    // Se comenta para no actualizar los datos de la postulación hasta que se apruebe la solicitud
                    // Se actualiza la postulación al registro
                    // infoSolicitud.setDatosPostulacion(this.actualizarDatosPostulacion(infoSolicitud.getDatosPostulacion(), userDTO));
                    // Se almacena la lista de chequeo
                    ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
                    listaChequeo.setIdSolicitudGlobal(infoSolicitud.getIdSolicitud());
                    listaChequeo.setNumeroIdentificacion(infoSolicitud.getDatosNovedadRegularFovisDTO().getNumeroIdJefeHogar());
                    listaChequeo.setTipoIdentificacion(infoSolicitud.getDatosNovedadRegularFovisDTO().getTipoIdJefeHogar());
                    listaChequeo.setListaChequeo(infoSolicitud.getListaChequeo());
                    if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
                        guardarListaChequeo(listaChequeo);
                    }
                    
                }
                else {
                    // Se identifica el solicitante para asociarle la lista de chequeo
                    TipoIdentificacionEnum tipoIdentificacionSolicitante = null;
                    String numeroIdentificacionSolicitante = null;
                    if (PersonasFOVISUtils.listarClasificacionIntegrante().contains(infoSolicitud.getClasificacion())) {
                        tipoIdentificacionSolicitante = infoSolicitud.getDatosNovedadRegularFovisDTO().getTipoIdIntegrante();
                        numeroIdentificacionSolicitante = infoSolicitud.getDatosNovedadRegularFovisDTO().getNumeroIdIntegrante();
                    }
                    // Si el solicitante es el Hogar o el Jefe Hogar
                    else {
                        tipoIdentificacionSolicitante = infoSolicitud.getDatosNovedadRegularFovisDTO().getTipoIdJefeHogar();
                        numeroIdentificacionSolicitante = infoSolicitud.getDatosNovedadRegularFovisDTO().getNumeroIdJefeHogar();
                    }
                    // Se almacena la lista de chequeo
                    ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
                    listaChequeo.setIdSolicitudGlobal(infoSolicitud.getIdSolicitud());
                    listaChequeo.setNumeroIdentificacion(numeroIdentificacionSolicitante);
                    listaChequeo.setTipoIdentificacion(tipoIdentificacionSolicitante);
                    listaChequeo.setListaChequeo(infoSolicitud.getListaChequeo());
                    if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
                        guardarListaChequeo(listaChequeo);
                    }
                }
                // Si la novedad tiene punto resolución en FRONT se resuelve comunicado en caso contrario se pregunta el resultado de radicacion
                if (infoSolicitud.getParametrizacionNovedad().getPuntoResolucion().equals(PuntoResolucionEnum.FRONT)) {
                    resolverNovedad(infoSolicitud, userDTO);
                    // Se envia el codigo de rechazo 2, este permite la asignacion de la tarea de generar comunicado para el envio del correo 
                    // con la notificación y cerrar la solicitud al finalizar el envio
                    infoSolicitud.setResultadoRadicacion(CODIGO_RECHAZO);
                }
                // Se inicia el proceso BPM
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put(RESULTADO_RADICACION, infoSolicitud.getResultadoRadicacion().toString());
                parametros.put(NUMERO_RADICACION, infoSolicitud.getNumeroRadicacion());
                if (infoSolicitud.getResultadoRadicacion().equals(CODIGO_ESCALAMIENTO)) {
                    parametros.put(ESCALAMIENTO_FRONT, CODIGO_ESCALMIENTO_FRONT);
                    // Se realiza el registro del escalamiento a los analistas indicados 
                    Map<TipoAnalistaEstalamientoFOVISEnum, EscalamientoSolicitudDTO> escalamientos = registrarEscalamientoAnalistas(
                            parametros, infoSolicitud.getEscalamientoMiembrosHogar(), infoSolicitud.getEscalamientoTecnicoConstruccion(),
                            infoSolicitud.getEscalamientoJuridico(), infoSolicitud.getIdSolicitud(), OrigenEscalamientoEnum.FRONT, userDTO);
                    // Actualiza los registros de escalmiento
                    infoSolicitud.setEscalamientoJuridico(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO));
                    infoSolicitud.setEscalamientoMiembrosHogar(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR));
                    infoSolicitud.setEscalamientoTecnicoConstruccion(escalamientos.get(TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO));
                    //Actualiza el estado de la solicitud
                    actualizarEstadoSolicitudNovedadFovis(infoSolicitud.getIdSolicitud(),
                            EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_ESCALADA);
                }
                // Se instancia el proceso BPM
                infoSolicitud.setIdInstancia(iniciarProcesoNovedadFovis(infoSolicitud.getIdSolicitud(), tipoTransaccionNovedad.getProceso(),
                        parametros, userDTO));
                // Se guarda los datos de la solicitud en el registro temporal
                guardarDatosTemporal(infoSolicitud);
            }
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
            return infoSolicitud;
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Ejecuta las validaciones de negocio de cada novedad
     * @param solNovedadDTO
     *        Informacion solicitud novedad
     * @return Resultado de las validaciones
     */
    private ResultadoRadicacionSolicitudEnum validarNovedad(SolicitudNovedadFovisDTO solNovedadDTO) {
        List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();
        Map<String, String> datosValidacion = null;
        if (solNovedadDTO.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
            // Se realiza la validacion del bloque 321-023 para todos los miembros del hogar
            validaciones = validarNovedadEspecial(solNovedadDTO);
        }
        else {
            datosValidacion = obtenerDatosValidacionRegular(solNovedadDTO);
            ValidarReglasNegocioFovis validarReglasService = new ValidarReglasNegocioFovis(solNovedadDTO.getTipoTransaccion().name(),
                    solNovedadDTO.getTipoTransaccion().getProceso(), solNovedadDTO.getClasificacion().name(), datosValidacion);
            validarReglasService.execute();
            validaciones = (List<ValidacionDTO>) validarReglasService.getResult();
        }
        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        if (!validaciones.isEmpty()) {
            List<ValidacionDTO> listValidacionTipoUno = new ArrayList<>();
            // Se verifica el resultado de las validaciones
            resultado = verificarResultadoValidacion(listValidacionTipoUno, validaciones, solNovedadDTO);
            // Se agrega la lista de validaciones fallidas
            if (!listValidacionTipoUno.isEmpty()) {
                solNovedadDTO.setListResultadoValidacion(listValidacionTipoUno);
            }
        }
        return resultado;
    }

    /**
     * Obtiene los datos para la validacion de la novedad
     * 
     * @param solNovedadDTO
     *        Informacion solicitud novedad
     * @return Mapa con los parametros de validacion
     */
    private Map<String, String> obtenerDatosValidacionRegular(SolicitudNovedadFovisDTO solNovedadDTO) {
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put(KEY_TIPO_TRANSACCION, solNovedadDTO.getTipoTransaccion().name());
        datosValidacion.put(KEY_OBJETO_VALIDACION, solNovedadDTO.getClasificacion().name());
        datosValidacion.put(KEY_ID_POSTULACION, solNovedadDTO.getIdPostulacion().toString());
        TipoIdentificacionEnum tipoIdentificacionSolicitante = null;
        String numeroIdentificacionSolicitante = null;
        TipoIdentificacionEnum tipoIdentificacionJefe = null;
        String numeroIdentificacionJefe = null;

        // Si el solicitante es Miembro Hogar, se obtiene la información del integrante
        if (PersonasFOVISUtils.listarClasificacionIntegrante().contains(solNovedadDTO.getClasificacion())) {
            // Si se enviaron datos nuevos se relacionan estos para el proceso de validacion
            if (solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdIntegranteNuevo() != null
                    && solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdIntegranteNuevo() != null) {
                tipoIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdIntegranteNuevo();
                numeroIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdIntegranteNuevo();
            }
            else {
                tipoIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdIntegrante();
                numeroIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdIntegrante();
            }
            tipoIdentificacionJefe = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdJefeHogar();
            numeroIdentificacionJefe = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdJefeHogar();
            datosValidacion.put(KEY_NRO_IDENTIFICA_AFILIADO, numeroIdentificacionJefe);
            datosValidacion.put(KEY_TIPO_IDENTIFICA_AFILIADO, tipoIdentificacionJefe.name());
        }
        // Si el solicitante es el Hogar o el Jefe Hogar
        else {
            tipoIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getTipoIdJefeHogar();
            numeroIdentificacionSolicitante = solNovedadDTO.getDatosNovedadRegularFovisDTO().getNumeroIdJefeHogar();
        }
        datosValidacion.put(KEY_NRO_IDENTIFICACION, numeroIdentificacionSolicitante);
        datosValidacion.put(KEY_TIPO_IDENTIFICACION, tipoIdentificacionSolicitante.name());
        datosValidacion.put(KEY_NRO_IDENTIFICA_BENEFI, numeroIdentificacionSolicitante);
        datosValidacion.put(KEY_TIPO_IDENTIFICA_BENEFI, tipoIdentificacionSolicitante.name());
        datosValidacion.put(KEY_PRIMER_NOMBRE, solNovedadDTO.getDatosNovedadRegularFovisDTO().getPrimerNombreIntegrante());
        datosValidacion.put(KEY_SEGUNDO_NOMBRE, solNovedadDTO.getDatosNovedadRegularFovisDTO().getSegundoNombreIntegrante());
        datosValidacion.put(KEY_PRIMER_APELLIDO, solNovedadDTO.getDatosNovedadRegularFovisDTO().getPrimerApellidoIntegrante());
        datosValidacion.put(KEY_SEGUNDO_APELLIDO, solNovedadDTO.getDatosNovedadRegularFovisDTO().getSegundoApellidoIntegrante());
        return datosValidacion;
    }

    /**
     * Se realiza la ejecucion del bloque de validaciones 321-23 para los miembros del hogar objeto de la novedad especial
     * @param solNovedadDTO
     *        Informacion solicitud novedad
     * @return Lista de validaciones ejecutadas
     */
    private List<ValidacionDTO> validarNovedadEspecial(SolicitudNovedadFovisDTO solNovedadDTO) {
        // Lista de resultado de validaciones
        List<ValidacionDTO> validaciones = new ArrayList<ValidacionDTO>();
        // Se obtiene la informacion del jefe de hogar
        JefeHogarModeloDTO jefeHogar = solNovedadDTO.getDatosPostulacion().getPostulacion().getJefeHogar();
        // Se obtiene la lista de miembros Hogar
        List<IntegranteHogarModeloDTO> miembrosHogar = solNovedadDTO.getDatosPostulacion().getIntegrantesHogar();
        // Se realiza la validacion del proceso 321-023 para cada unos de los integrantes Hogar
        // Se inicalizan los parametros de validacion
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put(KEY_TIPO_TRANSACCION, solNovedadDTO.getTipoTransaccion().name());
        datosValidacion.put(KEY_OBJETO_VALIDACION, ClasificacionEnum.JEFE_HOGAR.name());
        datosValidacion.put(KEY_ID_POSTULACION, solNovedadDTO.getIdPostulacion().toString());
        datosValidacion.put(KEY_NRO_IDENTIFICACION, jefeHogar.getNumeroIdentificacion());
        datosValidacion.put(KEY_TIPO_IDENTIFICACION, jefeHogar.getTipoIdentificacion().name());
        datosValidacion.put(KEY_NRO_IDENTIFICA_BENEFI, jefeHogar.getNumeroIdentificacion());
        datosValidacion.put(KEY_TIPO_IDENTIFICA_BENEFI, jefeHogar.getTipoIdentificacion().name());
        datosValidacion.put(KEY_NRO_IDENTIFICA_AFILIADO, jefeHogar.getNumeroIdentificacion());
        datosValidacion.put(KEY_TIPO_IDENTIFICA_AFILIADO, jefeHogar.getTipoIdentificacion().name());
        datosValidacion.put(KEY_PRIMER_NOMBRE, jefeHogar.getPrimerNombre());
        datosValidacion.put(KEY_SEGUNDO_NOMBRE, jefeHogar.getSegundoNombre());
        datosValidacion.put(KEY_PRIMER_APELLIDO, jefeHogar.getPrimerApellido());
        datosValidacion.put(KEY_SEGUNDO_APELLIDO, jefeHogar.getSegundoApellido());
        // Se inicia el proceso JEFE HOGAR
        validaciones.addAll(ejecutarValidacionReglaNegocio(datosValidacion, ClasificacionEnum.JEFE_HOGAR));
        // Se inicalizan los parametros de validacion para los miembros
        datosValidacion = new HashMap<>();
        datosValidacion.put(KEY_TIPO_TRANSACCION, solNovedadDTO.getTipoTransaccion().name());
        datosValidacion.put(KEY_ID_POSTULACION, solNovedadDTO.getIdPostulacion().toString());
        datosValidacion.put(KEY_NRO_IDENTIFICA_AFILIADO, jefeHogar.getNumeroIdentificacion());
        datosValidacion.put(KEY_TIPO_IDENTIFICA_AFILIADO, jefeHogar.getTipoIdentificacion().name());
        for (IntegranteHogarModeloDTO integranteHogarModeloDTO : miembrosHogar) {
            datosValidacion.put(KEY_OBJETO_VALIDACION, integranteHogarModeloDTO.getTipoIntegranteHogar().name());
            datosValidacion.put(KEY_NRO_IDENTIFICACION, integranteHogarModeloDTO.getNumeroIdentificacion());
            datosValidacion.put(KEY_TIPO_IDENTIFICACION, integranteHogarModeloDTO.getTipoIdentificacion().name());
            datosValidacion.put(KEY_NRO_IDENTIFICA_BENEFI, integranteHogarModeloDTO.getNumeroIdentificacion());
            datosValidacion.put(KEY_TIPO_IDENTIFICA_BENEFI, integranteHogarModeloDTO.getTipoIdentificacion().name());
            datosValidacion.put(KEY_PRIMER_NOMBRE, integranteHogarModeloDTO.getPrimerNombre());
            datosValidacion.put(KEY_SEGUNDO_NOMBRE, integranteHogarModeloDTO.getSegundoNombre());
            datosValidacion.put(KEY_PRIMER_APELLIDO, integranteHogarModeloDTO.getPrimerApellido());
            datosValidacion.put(KEY_SEGUNDO_APELLIDO, integranteHogarModeloDTO.getSegundoApellido());
            validaciones.addAll(ejecutarValidacionReglaNegocio(datosValidacion, integranteHogarModeloDTO.getTipoIntegranteHogar()));
        }
        return validaciones;
    }

    /**
     * Invoca el servicio de validaciones de reglas de negocio para el bloque 321-023
     * @param datosValidacion
     *        Mapa con los datos de validacion
     * @param clasificacionEnum
     *        Clasificacion de la persona objeto de validacion
     * @return Lista de validaciones ejecutadas
     */
    private List<ValidacionDTO> ejecutarValidacionReglaNegocio(Map<String, String> datosValidacion, ClasificacionEnum clasificacionEnum) {
        ValidarReglasNegocioFovis validarReglasService = new ValidarReglasNegocioFovis(BLOQUE_VALIDACION_325_23,
                ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL, clasificacionEnum.name(), datosValidacion);
        validarReglasService.execute();
        return validarReglasService.getResult();
    }

    /**
     * Realiza el proceso de verificacion del resultado de las validaciones ejecutadas, para indicar el comportamiento en pantalla
     * @param listaValidacionesFallidas
     *        Contiene la lista de validaciones fallidas por tipo de excepcion
     * @param validacionesEjecutadas
     *        Lista de validaciones ejecutadas
     * @param solNovedadD
     *        TO
     *        Informacion de la solicitud de novedad
     * @return Resultado de radicacion de la solicitud a partir del resultado de las validaciones
     */
    private ResultadoRadicacionSolicitudEnum verificarResultadoValidacion(List<ValidacionDTO> listaValidacionesFallidas,
            List<ValidacionDTO> validacionesEjecutadas, SolicitudNovedadFovisDTO solNovedadDTO) {
        // Resultado verificacion validaciones
        ResultadoRadicacionSolicitudEnum resultado = ResultadoRadicacionSolicitudEnum.EXITOSA;
        List<ValidacionDTO> listaValidacionesT2 = new ArrayList<>();
        List<ValidacionDTO> listaValidacionesT1 = new ArrayList<>();
        boolean excepcionUno = false;
        boolean excepcionDos = false;

        for (ValidacionDTO validacionDTO : validacionesEjecutadas) {
            // Si la validacion fue NO_APROBADA se verifica el tipo de excepcion 
            // Se crea una lista de validacion para el comportamiento en pantalla
            if (validacionDTO.getResultado().equals(ResultadoValidacionEnum.NO_APROBADA)) {
                resultado = ResultadoRadicacionSolicitudEnum.FALLIDA;
                if (TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacionDTO.getTipoExcepcion())) {
                    excepcionDos = true;
                    listaValidacionesT2.add(validacionDTO);
                }
                else {
                    listaValidacionesT1.add(validacionDTO);
                    excepcionUno = true;
                }
            }
        }
        // Se identifica el error ocurrido y se agrega la lista de validaciones
        if (excepcionDos) {
            solNovedadDTO.setExcepcionTipoDos(true);
            listaValidacionesFallidas.addAll(listaValidacionesT2);
        }
        else if (excepcionUno) {
            solNovedadDTO.setExcepcionTipoDos(false);
            listaValidacionesFallidas.addAll(listaValidacionesT1);
        }
        return resultado;
    }

    /**
     * Invoca el servicio de inserción y/o actualización en la tabla
     * <code>SolicitudNovedadPersonaFovis</code>
     * 
     * @param SolicitudNovedadPersonaFovis
     *        Información de la persona en novedad fovis a registrar o actualizar
     * @return Registro actualizado
     */
    private SolicitudNovedadPersonaFovis crearActualizarSolicitudNovedadPersonaFovis(
            SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis) {
        CrearActualizarSolicitudNovedadPersonaFovis service = new CrearActualizarSolicitudNovedadPersonaFovis(solicitudNovedadPersonaFovis);
        service.execute();
        return service.getResult();
    }

    /**
     * Invoca el servicio de consulta de persona
     * @param numeroIdentificacion
     *        Numero identificación persona a consultar
     * @param tipoIdentificacion
     *        Tipo identificación persona a consultar
     * @return Información persona existe con los parametros enviados
     */
    private PersonaModeloDTO consultarPersona(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        ConsultarDatosPersona consultarPersona = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
        consultarPersona.execute();
        PersonaModeloDTO personas = consultarPersona.getResult();
        return personas;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.NovedadesFovisCompositeService#analizarSolicitudNovedadFovis(
     *      com.asopagos.novedades.fovis.composite.dto.AnalisisSolicitudNovedadFovisDTO, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void analizarSolicitudNovedadFovis(AnalisisSolicitudNovedadFovisDTO analisisSolicitud, UserDTO userDTO) {
        String path = "analizarSolicitudNovedadFovis(AnalisisSolicitudNovedadFovisDTO, UserDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            SolicitudNovedadFovisDTO solicitudTemporal = consultarNovedadFovisTemporal(analisisSolicitud.getIdSolicitud());

            RegistrarEscalamientoSolicitud registrarEscalamientoSolicitud = new RegistrarEscalamientoSolicitud(
                    analisisSolicitud.getIdSolicitud(), analisisSolicitud.getEscalamientoSolicitud());
            registrarEscalamientoSolicitud.execute();
            EscalamientoSolicitudDTO escalamiento = registrarEscalamientoSolicitud.getResult();
            if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_HOGAR.equals(escalamiento.getTipoAnalistaFOVIS())) {
                if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                    solicitudTemporal.setEscalamientoMiembrosHogar(escalamiento);
                } else {
                    solicitudTemporal.setEscalamientoMiembrosHogarBack(escalamiento);
                }
            }
            else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_JURIDICO.equals(escalamiento.getTipoAnalistaFOVIS())) {
                if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                    solicitudTemporal.setEscalamientoJuridico(escalamiento);                    
                } else {
                    solicitudTemporal.setEscalamientoJuridicoBack(escalamiento);
                }
            }
            else if (TipoAnalistaEstalamientoFOVISEnum.ANALISTA_TECNICO.equals(escalamiento.getTipoAnalistaFOVIS())) {
                if (OrigenEscalamientoEnum.FRONT.equals(escalamiento.getOrigen())) {
                    solicitudTemporal.setEscalamientoTecnicoConstruccion(escalamiento);
                } else {
                    solicitudTemporal.setEscalamientoTecnicoConstruccionBack(escalamiento);
                }
            }

            if (!existenEscalamientosSinResultado(analisisSolicitud.getIdSolicitud())) {

                actualizarEstadoSolicitudNovedadFovis(analisisSolicitud.getIdSolicitud(),
                        EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_EN_ANALISIS_ESPECIALIZADO);
                actualizarEstadoSolicitudNovedadFovis(analisisSolicitud.getIdSolicitud(),
                        EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_GESTIONADA_POR_ESPECIALISTA);
                solicitudTemporal.setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_GESTIONADA_POR_ESPECIALISTA);
            }
            guardarDatosTemporal(solicitudTemporal);
            terminarTarea(analisisSolicitud.getIdTarea(), null, solicitudTemporal.getIdInstancia());
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que usa un servicio para verificar si existen escalamientos sin resultado para una solicitud de novedad Fovis.
     * @param idSolicitud
     *        Identificador de la solicitud global.
     * @return Resultado de la existencia o no de escalamientos sin resultado.
     */
    private boolean existenEscalamientosSinResultado(Long idSolicitud) {
        ExistenEscalamientosSinResultado servicio = new ExistenEscalamientosSinResultado(idSolicitud);
        servicio.execute();
        return servicio.getResult();
    }

    @Override
    public void finalizarAnalisisNovedadFovisFront(AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento, UserDTO userDTO) {
        String path = "finalizarAnalisisNovedadFovisFront(AnalisisSolicitudNovedadFovisDTO, UserDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_ESCALAMIENTO_FRONT, resultadoEscalamiento.getResultado());
            // Se realiza el procesamiento del resultado
            registrarResultadoConceptoEscalamientoNovedadFovis(resultadoEscalamiento, params, true, userDTO);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    @Override
    public void finalizarAnalisisNovedadFovisBack(AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento, UserDTO userDTO) {
        String path = "finalizarAnalisisNovedadFovisBack(AnalisisSolicitudNovedadFovisDTO, UserDTO)";
        try {
            logger.debug(ConstantesComunes.INICIO_LOGGER + path);
            Map<String, Object> params = new HashMap<>();
            params.put(RESULTADO_ESCALAMIENTO_BACK, resultadoEscalamiento.getResultado());
            // Se realiza el procesamiento del resultado
            registrarResultadoConceptoEscalamientoNovedadFovis(resultadoEscalamiento, params, false, userDTO);
            logger.debug(ConstantesComunes.FIN_LOGGER + path);
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + path, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Procesa y actualiza los datos de la solicitud cuando se realiza la verificacion del resultado del escalamiento
     * @param resultadoEscalamiento
     *        Información resultado escalamiento
     * @param params
     *        Parámetros proceso BPM
     * @param escalamientoFront
     *        Indica si el escalamiento se realizo desde el front
     * @param userDTO
     *        Información usuario autenticado
     * @throws Exception
     *         Lanzada si ocurre un error actualizando la información
     */
    private void registrarResultadoConceptoEscalamientoNovedadFovis(AnalisisSolicitudNovedadFovisDTO resultadoEscalamiento,
            Map<String, Object> params, Boolean escalamientoFront, UserDTO userDTO) throws Exception {
        // Consultar los datos de solicitud novedad fovis del temporal
        SolicitudNovedadFovisDTO solicitudTemporal = consultarNovedadFovisTemporal(resultadoEscalamiento.getIdSolicitud());
        EstadoSolicitudNovedadFovisEnum estadoNuevo = null;
        // Se realiza el procesamiento del resultado
        switch (resultadoEscalamiento.getResultado()) {
            case CODIGO_ESCALMIENTO_PROCEDENTE:
                if (escalamientoFront) {
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_PENDIENTE_ENVIO_AL_BACK;
                    actualizarEstadoSolicitudNovedadFovis(resultadoEscalamiento.getIdSolicitud(),
                            EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_PENDIENTE_ENVIO_AL_BACK);
                }
                else {
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_APROBADA;
                    // Si la novedad es especial se actualiza la información
                    if (solicitudTemporal.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
                        solicitudTemporal
                                .setDatosPostulacion(this.actualizarDatosPostulacion(solicitudTemporal.getDatosPostulacion(), userDTO));
                        actualizarEstadoSolicitudNovedadFovis(resultadoEscalamiento.getIdSolicitud(),
                                EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_APROBADA);
                    }
                    else {
                        resolverNovedad(solicitudTemporal, userDTO);
                    }
                }
                break;
            case CODIGO_ESCALMIENTO_NO_PROCEDENTE:
                estadoNuevo = EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RECHAZADA;
                actualizarEstadoSolicitudNovedadFovis(resultadoEscalamiento.getIdSolicitud(),
                        EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_RECHAZADA);
                if (solicitudTemporal.getTipoTransaccion().getProceso().equals(ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL)) {
                    actualizarEstadoSolicitudNovedadFovis(resultadoEscalamiento.getIdSolicitud(),
                            EstadoSolicitudNovedadFovisEnum.POSTULACION_RECHAZADA);
                    estadoNuevo = EstadoSolicitudNovedadFovisEnum.POSTULACION_RECHAZADA;
                    // Cambia el estado hogar a RECHAZADO 
                    actualizarEstadoHogar(solicitudTemporal.getIdPostulacion(), EstadoHogarEnum.RECHAZADO);
                }
                break;
        }
        solicitudTemporal.setEstadoSolicitudNovedad(estadoNuevo);
        // Se alamcena los datos temporales
        guardarDatosTemporal(solicitudTemporal);
        // Se finaliza la tarea actual
        terminarTarea(resultadoEscalamiento.getIdTarea(), params, solicitudTemporal.getIdInstancia());
    }

    @Override
    public SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisisNovedadAfectaFOVIS(Long idSolicitud) {
        String firma = "consultarSolicitudAnalisisNovedadAfectaFOVIS(Long):SolicitudAnalisisNovedadFOVISModeloDTO";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firma);
        ConsultarSolicitudAnalisisNovedad consultarSolicitudAnalisisNovedadService = new ConsultarSolicitudAnalisisNovedad(idSolicitud);
        consultarSolicitudAnalisisNovedadService.execute();
        SolicitudAnalisisNovedadFOVISModeloDTO solicitud = consultarSolicitudAnalisisNovedadService.getResult();
        if (solicitud == null) {
            return solicitud;
        }
        // Se verifica la clasificacion de la persona
        String numeroDocumento = solicitud.getPersonaNovedad().getNumeroIdentificacion();
        TipoIdentificacionEnum tipoDocumento = solicitud.getPersonaNovedad().getTipoIdentificacion();
        ConsultarClasificacionesAfiliado consultarClasificacion = new ConsultarClasificacionesAfiliado(numeroDocumento, tipoDocumento);
        consultarClasificacion.execute();
        List<ClasificacionEnum> listClasificacion = consultarClasificacion.getResult();
        if (listClasificacion != null && !listClasificacion.isEmpty()) {
            ClasificacionEnum clasificacion = listClasificacion.iterator().next();
            solicitud.getPersonaNovedad().setClasificacion(clasificacion);
            solicitud.setTipoSolicitante(clasificacion.getSujetoTramite().getDescripcion());
        }
        // Si no tiene clasificacion como afiliado, se verifica el tipo de beneficiario
        else {
            ConsultarBeneficiarioTipoNroIdentificacion consultarBeneficiario = new ConsultarBeneficiarioTipoNroIdentificacion(
                    numeroDocumento, tipoDocumento);
            consultarBeneficiario.execute();
            List<BeneficiarioModeloDTO> listBeneficiario = consultarBeneficiario.getResult();
            if (listBeneficiario != null && !listBeneficiario.isEmpty()) {
                ClasificacionEnum clasificacion = listBeneficiario.iterator().next().getTipoBeneficiario();
                solicitud.getPersonaNovedad().setClasificacion(clasificacion);
                solicitud.setTipoSolicitante(clasificacion.getSujetoTramite().getDescripcion());
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firma);
        return solicitud;
    }

    /**
     * Método que invoca el servicio de actualización de una lista de postulaciones FOVIS
     * 
     * @param lista
     *        de postulaciones fovis con la información del registro a actualizar
     */
    private void crearActualizarListaPostulacionFOVIS(List<PostulacionFOVISModeloDTO> listaPostulacionFOVISDTO) {
        logger.info("Inicia el método crearActualizarListaPostulacionFOVIS");
        CrearActualizarListaPostulacionFOVIS service = new CrearActualizarListaPostulacionFOVIS(listaPostulacionFOVISDTO);
        service.execute();
        logger.info("Finaliza el método crearActualizarListaPostulacionFOVIS");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.NovedadesFovisCompositeService#
     *      gestionarPNCNovedadFovis(java.lang.Long, com.asopagos.rest.security.dto.UserDTO)
     */
    @Override
    public void gestionarPNCNovedadFovis(Long idSolicitud, UserDTO userDTO) {
        logger.debug("Inicia servicio gestionarPNCNovedadFovis(long, UserDTO)");
        try {
            SolicitudNovedadFovisDTO solicitudTemporal = consultarNovedadFovisTemporal(idSolicitud);
            actualizarEstadoSolicitudNovedadFovis(idSolicitud, EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_NO_CONFORME_EN_GESTION);
            actualizarEstadoSolicitudNovedadFovis(idSolicitud, EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_NO_CONFORME_GESTIONADA);
            solicitudTemporal.setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_NO_CONFORME_GESTIONADA);
            guardarDatosTemporal(solicitudTemporal);
            terminarTarea(null, null, solicitudTemporal.getIdInstancia());
            logger.debug("Finaliza servicio gestionarPNCNovedadFovis(Long, UserDTO)");
        } catch (Exception e) {
            logger.error("Error inesperado en gestionarPNCNovedadFovis(Long, UserDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    public void asignarSolicitudNovedadFovis(AsignarSolicitudNovedadFovisDTO entrada, UserDTO userDTO) {
        logger.debug("Se inicia el servicio de asignarSolicitudNovedadFovis(AsignarSolicitudNovedadFovisDTO)");
        try {
            String destinatario = null;
            String sedeDestinatario = null;
            String observacion = null;
            UsuarioDTO usuarioDTO = new UsuarioCCF();
            Map<String, Object> params = new HashMap<>();

            if (MetodoAsignacionBackEnum.AUTOMATICO.equals(entrada.getMetodoAsignacion())) {
                destinatario = asignarAutomaticamenteUsuarioCajaCompensacion(new Long(userDTO.getSedeCajaCompensacion()),
                        entrada.getTipoTransaccion().getProceso());
                usuarioDTO = consultarUsuarioCajaCompensacion(destinatario);
                sedeDestinatario = usuarioDTO.getCodigoSede();
                observacion = null;
            }
            else if (MetodoAsignacionBackEnum.MANUAL.equals(entrada.getMetodoAsignacion())) {
                // se busca el usuario a quien se le asigna la tarea, por su nombe
                // de usuario
                usuarioDTO = consultarUsuarioCajaCompensacion(entrada.getUsuario());
                sedeDestinatario = usuarioDTO.getCodigoSede();
                destinatario = usuarioDTO.getNombreUsuario();
                observacion = entrada.getObservacion();
            }
            /* se actualiza la solicitud de legalización */
            SolicitudNovedadFovisModeloDTO solicitudNovedadFovis = consultarSolicitudNovedadFovis(entrada.getIdSolicitud());
            solicitudNovedadFovis.setDestinatario(destinatario);
            solicitudNovedadFovis.setSedeDestinatario(sedeDestinatario == null ? null : String.valueOf(sedeDestinatario));
            solicitudNovedadFovis.setObservacion(observacion);

            SolicitudNovedadFovisDTO solicitudTemporal = consultarNovedadFovisTemporal(entrada.getIdSolicitud());

            /* se cambia el estado de la soliciutd de acuerdo a los documentos. */
            if (entrada.getDocumentosFisicos()) {
                solicitudNovedadFovis.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
                solicitudNovedadFovis.setEstadoSolicitud(EstadoSolicitudNovedadFovisEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS);
                solicitudTemporal.setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum.PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS);
            }
            else {
                solicitudNovedadFovis.setEstadoDocumentacion(EstadoDocumentacionEnum.ENVIADA_AL_BACK);
                solicitudNovedadFovis.setEstadoSolicitud(EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_ASIGNADA_AL_BACK);
                solicitudTemporal.setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_ASIGNADA_AL_BACK);
            }
            this.crearActualizarSolicitudNovedadFovis(solicitudNovedadFovis);

            guardarDatosTemporal(solicitudTemporal);
            params.put(USUARIO_BACK, destinatario);
            params.put(DOCUMENTOS_FISICOS, entrada.getDocumentosFisicos());

            terminarTarea(entrada.getIdTarea(), params,
                    solicitudNovedadFovis.getIdInstanciaProceso() != null ? new Long(solicitudNovedadFovis.getIdInstanciaProceso()) : null);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio asignarSolicitudNovedadFovis(AsignarSolicitudNovedadFovisDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Método que hace la peticion REST al servicio de ejecutar asignacion
     * 
     * @param sedeCaja
     *        <code>Long</code> el identificador del afiliado
     * @param procesoEnum
     *        <code>ProcesoEnum</code> el identificador del afiliado
     * @return nombreUsuarioCaja <code>String</code> El nombre del usuario de la
     *         caja
     */
    private String asignarAutomaticamenteUsuarioCajaCompensacion(Long sedeCaja, ProcesoEnum procesoEnum) {
        logger.debug("Inicia asignarAutomaticamenteUsuarioCajaCompensacion( String  )");
        EjecutarAsignacion ejecutarAsignacion = new EjecutarAsignacion(procesoEnum, sedeCaja);
        ejecutarAsignacion.execute();
        logger.debug("Finaliza asignarAutomaticamenteUsuarioCajaCompensacion( String )");
        String nombreUsuarioCaja = (String) ejecutarAsignacion.getResult();
        return nombreUsuarioCaja;
    }

    /**
     * Método que hace la peticion REST al servicio de consultar un usuario de
     * caja de compensacion
     * 
     * @param nombreUsuarioCaja
     *        <code>String</code> El nombre de usuario del funcionario de la
     *        caja que realiza la consulta
     * 
     * @return <code>UsuarioDTO</code> DTO para el servicio de autenticación
     *         usuario
     */
    private UsuarioDTO consultarUsuarioCajaCompensacion(String nombreUsuarioCaja) {
        logger.debug("Inicia consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        ObtenerDatosUsuarioCajaCompensacion obtenerDatosUsuariosCajaCompensacionService = new ObtenerDatosUsuarioCajaCompensacion(
                nombreUsuarioCaja, null, null, false);
        obtenerDatosUsuariosCajaCompensacionService.execute();
        usuarioDTO = (UsuarioDTO) obtenerDatosUsuariosCajaCompensacionService.getResult();
        logger.debug("Finaliza consultarUsuarioCajaCompensacion( nombreUsuarioCaja )");
        return usuarioDTO;
    }

    /**
     * Invoca al servicio que ejecuta la actualizacion del estado del hogar
     * @param idPostulacion
     *        Identificador postulación
     * @param estadoHogar
     *        Estado Hogar Nuevo
     */
    private void actualizarEstadoHogar(Long idPostulacion, EstadoHogarEnum estadoHogar) {
        ActualizarEstadoHogar actualizarEstadoHogarService = new ActualizarEstadoHogar(idPostulacion, estadoHogar);
        actualizarEstadoHogarService.execute();
    }

    /**
     * Realiza el proceso de resolver (Registrar informacion novedad) utilizando el convertidor parametrizado para la novedad
     * ademas actualiza el estado de la novedad
     * @param solicitudNovedadFovisDTO
     *        Informacion solicitud novedad fovis
     * @param userDTO
     *        Usuario autenticado en el sistema
     * @throws Exception
     *         Lanzada si ocurre un error
     */
    private void resolverNovedad(SolicitudNovedadFovisDTO solicitudNovedadFovisDTO, UserDTO userDTO) throws Exception {
        // Se ejecuta el convertidor parametrizado para la novedad
        ejecutarNovedad(solicitudNovedadFovisDTO);
        // Se actualiza la solicitud novedad 
        actualizarEstadoSolicitudNovedadFovis(solicitudNovedadFovisDTO.getIdSolicitud(),
                EstadoSolicitudNovedadFovisEnum.NOV_FOVIS_APROBADA);
    }

    @Asynchronous
    public void ejecutarNovedadVencimiento(UserDTO userDTO){
        logger.debug("Inicia servicio ejecutarNovedadVencimiento(UserDTO)");
        try {
            List<SolicitudNovedadFovisDTO> listEjecucion = new ArrayList<>();
            // Se indica la novedad vencimiento de subsidios asignados con primera prorroga
            SolicitudNovedadFovisDTO solNovedadVenciPriProrroga = new SolicitudNovedadFovisDTO();
            solNovedadVenciPriProrroga.setTipoTransaccion(TipoTransaccionEnum.VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA);
            solNovedadVenciPriProrroga.setParametro(ParametroFOVISEnum.PLAZO_VENCIMIENTO_PRIMERA_PRORROGA);
            listEjecucion.add(solNovedadVenciPriProrroga);
            // Se indica la novedad vencimiento de subsidios asignados con segunda prorroga
            SolicitudNovedadFovisDTO solNovedadVenciSegProrroga = new SolicitudNovedadFovisDTO();
            solNovedadVenciSegProrroga.setTipoTransaccion(TipoTransaccionEnum.VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA);
            solNovedadVenciSegProrroga.setParametro(ParametroFOVISEnum.PLAZO_VENCIMIENTO_SEGUNDA_PRORROGA);
            listEjecucion.add(solNovedadVenciSegProrroga);
            // Se indica la novedad vencimiento de subsidios asignados sin prorroga
            SolicitudNovedadFovisDTO solNovedadVenciSinProrroga = new SolicitudNovedadFovisDTO();
            solNovedadVenciSinProrroga.setTipoTransaccion(TipoTransaccionEnum.VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA);
            solNovedadVenciSinProrroga.setParametro(ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA);
            listEjecucion.add(solNovedadVenciSinProrroga);
            
            // Creacion tareas paralelas ejecucion validaciones postulacion
            List<Callable<SolicitudNovedadFovisDTO>> tareasParalelas = new LinkedList<>();
            for (SolicitudNovedadFovisDTO solicitudNovedadDTO : listEjecucion) {
                // Se crea la tarea paralela para la ejecucion de validaciones
                Callable<SolicitudNovedadFovisDTO> parallelTask = () -> {
                    return radicarSolicitudNovedadAutomaticaFovis(solicitudNovedadDTO, userDTO);
                };
                tareasParalelas.add(parallelTask);
            }
            managedExecutorService.invokeAll(tareasParalelas);
            logger.debug("Finaliza servicio ejecutarNovedadVencimiento(UserDTO)");
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado en el servicio ejecutarNovedadVencimiento(UserDTO)", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

    /**
     * Llama al servicio que realiza la actualización de la información de json postulacion
     * @param idPostulacion
     *        Identificador postulacion
     * @param solicitudPostulacion
     *        Información de la solicitud de postulacion
     */
    private void actualizarJsonPostulacion(Long idPostulacion, SolicitudPostulacionFOVISDTO solicitudPostulacion) {
        ActualizarJsonPostulacion actualizarJsonPostulacion = new ActualizarJsonPostulacion(idPostulacion, solicitudPostulacion);
        actualizarJsonPostulacion.execute();
    }
}
