package com.asopagos.novedades.fovis.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.fovis.HistoricoNovedadFovisDTO;
import com.asopagos.dto.modelo.ActoAceptacionProrrogaFovisModeloDTO;
import com.asopagos.dto.modelo.DetalleNovedadFovisModeloDTO;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import com.asopagos.entidades.ccf.fovis.SolicitudNovedadPersonaFovis;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudAnalisisNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.novedades.fovis.dto.DatosReporteNovedadProrrogaFovisDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de Proceso de Novedades FOVIS <b>Historia de Usuario:</b>
 * Proceso 3.2.5
 * 
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
@Path("novedadesFovis")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesFovisService {

    /**
     * 
     * Servicio que consulta las postulaciones que aplican para Suspensión automática por cambio de año calendario
     * 
     * <code>NovedadesFovis</code>
     * 
     * @return Lista de postulaciones
     */
    @GET
    @Path("/consultarPostulacionesNovedadSuspencionAutomatica")
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesNovedadSuspencionAutomatica();

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>solicitudNovedadFovis</code>
     * 
     * @param SolicitudNovedadFovisModeloDTO
     *        Información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarSolicitudNovedadFovis")
    public SolicitudNovedadFovisModeloDTO crearActualizarSolicitudNovedadFovis(
            @NotNull SolicitudNovedadFovisModeloDTO solicitudNovedadFovisModeloDTO);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>solicitudNovedadFovis</code>
     * 
     * @param SolicitudNovedadFovisModeloDTO
     *        Información del registro a actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/almacenarSolicitudNovedadAutomaticaMasiva")
    public void almacenarSolicitudNovedadAutomaticaMasiva(@NotNull @QueryParam("idSolicitudNovedad") Long idSolicitudNovedad,
            @NotNull DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO);

    /**
     * Servicio encargado de realizar la consulta de la postulacion vigente de las personas
     * @param listPersonas
     *        Lista de personas a consultar
     * @return
     */
    @POST
    @Path("/consultarPostulacionVigentePersonas")
    public List<PersonaPostulacionDTO> consultarPostulacionVigentePersonas(List<PersonaDTO> listPersonas);

    /**
     * Servicio encargado de registrar la informacion de las solicitudes de analisis novedad
     * @param listSolicitudes
     *        Informacion solicitudes a registrar
     * @return Lista de solicitudes registrada
     */
    @POST
    @Path("/crearActualizarListaSolicitudAnalisisNovedadFOVIS")
    public List<SolicitudAnalisisNovedadFOVISModeloDTO> crearActualizarListaSolicitudAnalisisNovedadFOVIS(
            List<SolicitudAnalisisNovedadFOVISModeloDTO> listSolicitudes);

    /**
     * Servicio encargado de consultar la informacion de una solicitud de analisis de novedad persona afecta fovis con el detalle de la
     * informacion a presentar en pantalla
     * @param idSolicitud
     *        Identificador solicitud analisis
     * @return DTO con la informacion de la solicitud de analisis
     */
    @GET
    @Path("/consultarSolicitudAnalisisNovedad")
    public SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisisNovedad(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio encargado de consultar el registro de solicitud de analisis por identificador
     * @param idSolicitud
     *        Identificador solicitud global
     * @return DTO con el registro de solicitud de analisis
     */
    @GET
    @Path("/consultarSolicitudAnalisis")
    public SolicitudAnalisisNovedadFOVISModeloDTO consultarSolicitudAnalisis(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio encargado de ejecutar la actulizacion de la solicitud de analisis
     * @param solicitudAnalisisNovedadFOVISModeloDTO
     *        Informacion de la solicitud de analisis
     * @return Solicitud actualizada
     */
    @POST
    @Path("/crearActualizarSolicitudAnalisisNovedadFOVIS")
    public SolicitudAnalisisNovedadFOVISModeloDTO crearActualizarSolicitudAnalisisNovedadFOVIS(
            @NotNull SolicitudAnalisisNovedadFOVISModeloDTO solicitudAnalisisNovedadFOVISModeloDTO);

    /**
     * Servicio encargado de actualizar el estado de la solicitud de analisis
     * @param idSolicitudGlobal
     *        Identificador solicitud analisis
     * @param estadoSolicitud
     *        Estado de la solicitud de analisis nuevo
     */
    @POST
    @Path("/actualizarEstadoSolicitudAnalisisNovedadFOVIS")
    public void actualizarEstadoSolicitudAnalisisNovedadFOVIS(@QueryParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAnalisisNovedadFovisEnum estadoSolicitud);

    /**
     * 
     * 
     * Servicio que consulta las personas que tienen inhabilidad o sancion para subsidio de vivienda
     * 
     * <code>NovedadesAutomaticasFovis</code>
     * 
     * @return Lista de personas con inhabilidad
     */
    @GET
    @Path("/consultarPersonasInhabilidadSubsidioFovisAutomatica")
    public List<InhabilidadSubsidioFovisModeloDTO> consultarPersonasInhabilidadSubsidioFovisAutomatica();

    /**
     * Servicio encargado de consultar una solicitud de novedad Fovis por el id de la solicitud global.
     * @param idSolicitudGlobal
     *        Identificador global de la solicitud de novedad Fovis.
     * @return Solicitud de novedad Fovis encontrada.
     */
    @GET
    @Path("/consultarSolicitudNovedadFovis")
    @Produces(MediaType.APPLICATION_JSON)
    public SolicitudNovedadFovisModeloDTO consultarSolicitudNovedadFovis(@QueryParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal);

    /**
     * Servicio que actualiza el estado de la solicitud de novedad Fovis.
     * @param idSolicitudGlobal
     *        id de la solicitud.
     * @param estadoSolicitud
     *        Nuevo estado para la solicitud de novedad Fovis.
     */
    @POST
    @Path("/{idSolicitudGlobal}/estadoSolicitud")
    public void actualizarEstadoSolicitudNovedadFovis(@PathParam("idSolicitudGlobal") @NotNull Long idSolicitudGlobal,
            @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudNovedadFovisEnum estadoSolicitud);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>InhabilidadSubsidioFovis</code>
     * 
     * @param inhabilidadDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarInhabilidadSubsidioFovis")
    public InhabilidadSubsidioFovisModeloDTO crearActualizarInhabilidadSubsidioFovis(InhabilidadSubsidioFovisModeloDTO inhabilidadDTO);

    /**
     * 
     * Servicio que consulta las postulaciones que aplican para rechazo automática por cambio de año calendario
     * 
     * <code>NovedadesFovis</code>
     * 
     * @return Lista de postulaciones que seran rechazadas automaticamente
     */
    @GET
    @Path("/consultarPostulacionesRechazoAutomatico")
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesRechazoAutomatico();

    /**
     * 
     * Servicio que consulta las postulaciones que aplican para cambiar el estado del hogar por
     * vencimiento de subsidios asignados
     * 
     * <code>NovedadesFovis</code>
     * 
     * @param estadoHogar
     *        Estado del hogar para validar el vencimiendo de subsidios
     * 
     * @return Lista de postulaciones que seran rechazadas automaticamente
     */
    @GET
    @Path("/consultarPostulacionesNovedadVencimiendoSubsidios")
    public List<PostulacionFOVISModeloDTO> consultarPostulacionesNovedadVencimiendoSubsidios(
            @QueryParam("estadoHogar") @NotNull EstadoHogarEnum estadoHogar);

    /**
     * Servicio que crea o actualiza el registro de novedad persona fovis
     * @param solicitudNovedadPersonaFovis
     *        Informacion solicitud novedad persona fovis
     * @return Solicitud novedad persona fovis registrada y/oactualizada
     */
    @POST
    @Path("/crearActualizarSolicitudNovedadPersonaFovis")
    public SolicitudNovedadPersonaFovis crearActualizarSolicitudNovedadPersonaFovis(SolicitudNovedadPersonaFovis solicitudNovedadPersonaFovis);

    /**
     * Servicio que crea o actualiza un registro en la tabla
     * <code>InhabilidadSubsidioFovis</code>
     * 
     * @param inhabilidadDTO
     *        La información del registro a crear/actualizar
     * @return La información del registro actualizado
     */
    @POST
    @Path("/crearActualizarListaInhabilidadSubsidioFovis")
    public void crearActualizarListaInhabilidadSubsidioFovis(List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades);

    /**
     * 
     * Servicio que consulta la inhabilidad a subsidio de vivienta de la persona conformante del hogar.
     * <code>NovedadesFovis</code>
     * 
     * @param numeroIdentificacion
     *        Numero de identificación de la persona
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona
     * 
     * @return registro de la inhabilidad para la persona
     */
    @GET
    @Path("/consultarInhabilidadSubsidioFovisPorDatosPersona")
    public InhabilidadSubsidioFovisModeloDTO consultarInhabilidadSubsidioFovisPorDatosPersona(
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

    /**
     * 
     * Servicio que consulta las inhabilidades del hogar para el subsidio de vivienta de la persona conformante del hogar.
     * <code>NovedadesFovis</code>
     * 
     * @param numeroIdentificacion
     *        Numero de identificación de la persona jefe hogar
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona jefe hogar
     * 
     * @return registro de la inhabilidad para la persona
     */
    @GET
    @Path("/consultarInhabilidadesHogarSubsidioFovisPorDatosPersona")
    public List<InhabilidadSubsidioFovisModeloDTO> consultarInhabilidadesHogarSubsidioFovisPorDatosPersona(
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion);

    /**
     * 
     * Servicio que consulta el acto de aceptación de una novedad de prórroga Fovis.
     * <code>NovedadesFovis</code>
     * 
     * @param idSolicitudNovedadFovis
     *        Identificador de la solicitud de novedad fovis
     * @return registro del acto de aceptación de prórroga
     */
    @GET
    @Path("/consultarActoAceptacionProrrogaFovisPorNovedadFovis")
    public ActoAceptacionProrrogaFovisModeloDTO consultarActoAceptacionProrrogaFovisPorNovedadFovis(
            @NotNull @QueryParam("idSolicitudNovedadFovis") Long idSolicitudNovedadFovis);

    /**
     * 
     * Servicio que registra los datos del acto de aceptación de novedad de prórroga Fovis a las distintas solicitudes de novedad
     * relacionadas en la lista.
     * 
     * @param fechaAprobacion
     *        Fecha de aprobacion del acto administrativo de aprobación de la prórroga.
     * @param numeroActoAdministrativo
     *        Número del documento del acto administrativo.
     * @param idSolicitudesNovedadFovis
     *        Lista de identificadores de la solicitud de novedad fovis a registrarles el acto.
     * @param userDTO
     *        Usuario del contexto de seguridad.
     * @return registro del acto de aceptación de prórroga
     */
    @POST
    @Path("/guardarActoAceptacionNovedadesProrrogaFovis")
    public void guardarActoAceptacionNovedadesProrrogaFovis(@NotNull @QueryParam("fechaAprobacion") Long fechaAprobacion,
            @NotNull @QueryParam("numeroActoAdministrativo") String numeroActoAdministrativo, @NotNull List<Long> idSolicitudesNovedadFovis,
            @Context UserDTO userDTO);

    /**
     * 
     * Servicio que consulta los datos a mostrar para el reporte de las solicitudes de prorroga que se les asignará el acto administrativo
     * de aprobación de prorroga.
     * 
     * @param fechaInicial
     *        fecha inicial del rango en el que aplicará el reporte.
     * @param fechaFinal
     *        fecha fina del rango en el que aplicará el reporte.
     * @return lista de solicitudes de novedad de prórroga con los datos a mostar.
     */
    @GET
    @Path("/consultarDatosReporteNovedadesProrrogaFovis")
    public List<DatosReporteNovedadProrrogaFovisDTO> consultarDatosReporteNovedadesProrrogaFovis(
            @NotNull @QueryParam("fechaInicial") Long fechaInicial, @NotNull @QueryParam("fechaFinal") Long fechaFinal);
    
    /**
     * Servicio que consulta el historico de las novedades aplicadas al hogar por el numero de radicacion de la postulacion
     * 
     * @param numeroRadicacion
     *        Numero de radicacion de la solicitud de postulacion
     * @return Lista de novedades asociadas al hogar
     */
    @GET
    @Path("/consultarHistoricoNovedadesFovisHogar")
    public List<HistoricoNovedadFovisDTO> consultarHistoricoNovedadesFovisHogar(@QueryParam("numeroRadicacion") String numeroRadicacion);


    /**
     * Consulta los numeros de instancia de las solicitudes asociadas a la postulacion
     * @param idPostulacion
     *        Identificador postulación
     * @return Lista de solicitudes asociadas
     */
    @GET
    @Path("/consultarInstanciaSolicitudAsociadaPostulacion")
    public List<Object> consultarInstanciaSolicitudAsociadaPostulacion(@QueryParam("idPostulacion") Long idPostulacion);

    /**
     * Registra y/o actualiza la información detallada de novedad fovis
     * @param detalleNovedadFovis
     *        Información detalle novedad
     * @return Información registrada
     */
    @POST
    @Path("/crearActualizarDetalleNovedad")
    public DetalleNovedadFovisModeloDTO crearActualizarDetalleNovedad(DetalleNovedadFovisModeloDTO detalleNovedadFovis);

    /**
     * Servicio para consultar las solicitudes de novedad fovis por postulacion, tipo de novedad y resultado de proceso
     * @param idPostulacion
     *        Identificador de postulación
     * @param tipoNovedad
     *        Tipo de transaccion de novedad
     * @param resultadoProceso
     *        Resultado de proceso
     * @return Lista de novedades registradas a la postulación ordenadas de la mas reciente a la mas antigua
     */
    @GET
    @Path("/solicitud/consultar/filtrada")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SolicitudNovedadFovisModeloDTO> consultarListaSolicitudNovedad(@QueryParam("idPostulacion") @NotNull Long idPostulacion,
            @NotNull @QueryParam("tipoNovedad") TipoTransaccionEnum tipoNovedad,
            @NotNull @QueryParam("resultadoProceso") ResultadoProcesoEnum resultadoProceso);

}
