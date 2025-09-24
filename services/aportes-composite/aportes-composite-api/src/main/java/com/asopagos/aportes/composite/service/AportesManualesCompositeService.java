package com.asopagos.aportes.composite.service;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import com.asopagos.aportes.composite.dto.AporteManualDTO;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import com.asopagos.aportes.composite.dto.DatosCotizanteDTO;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import com.asopagos.aportes.composite.dto.EvaluacionAnalistaDTO;
import com.asopagos.aportes.composite.dto.EvaluacionSupervisorDTO;
import com.asopagos.aportes.composite.dto.GestionAnalistaDTO;
import com.asopagos.aportes.composite.dto.GestionInformacionFaltanteDTO;
import com.asopagos.aportes.composite.dto.InformacionSolicitudDTO;
import com.asopagos.aportes.composite.dto.RadicacionAporteManualDTO;
import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import com.asopagos.aportes.dto.ConsultarRecaudoDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.dto.SolicitudCorreccionDTO;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la lógica de negocio para el proceso
 * 2.1.2 Aportes manuales <br/>
 * <b>Módulo:</b> Asopagos - 2.1.2 <br/>
 *
 * @author <a href="mailto:atoro@heinsohn.com.co"> Angélica Toro Murillo</a>
 */
@Path("aporteManual")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesManualesCompositeService {

    /**
     * Servicio que se encarga de radicar una solicitud de aporte manual.
     * 
     * @param radicacionAporteManualDTO
     *        datos de la radicacion y algunos datos basicos.
     * @param userDTO
     *        usuario que esta realizando la radicación, debe corresponder a
     *        un auxiliar de aportes.
     * @return número de radicación de la solicitud.
     */
    @POST
    @Path("/radicarSolicitudAporte")
    public String radicarSolicitudAporte(@NotNull RadicacionAporteManualDTO radicacionAporteManualDTO, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de guardar la información temporal de un aporte
     * manual.
     * 
     * @param idSolicitud
     *        id de la soliciutd asociada.
     * @param aporteManualDTO
     *        aporte manualDTO.
     */
    @POST
    @Path("/guardarAporteManualTemporal")
    public void guardarAporteManualTemporal(@NotNull @QueryParam("idSolicitud") Long idSolicitud, AporteManualDTO aporteManualDTO);

    /**
     * Servicio encargado de consultar un aporte manual temporal.
     * 
     * @param idSolicitud
     *        id de la solicitud global.
     * @return aporte manual dto.
     */
    @GET
    @Path("/{idSolicitud}/consultarAporteManualTemporal")
    public AporteManualDTO consultarAporteManualTemporal(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de guardar la información temporal de una
     * devolución.
     * 
     * @param idSolicitud
     *        id de la soliciutd asociada.
     * @param aporteManualDTO
     *        aporte manualDTO.
     */
    @POST
    @Path("/guardarDevolucionTemporal")
    public void guardarDevolucionTemporal(@NotNull @QueryParam("idSolicitud") Long idSolicitud, DevolucionDTO devolucionDTO);

    /**
     * Servicio encargado de consultar un aporte manual temporal.
     * 
     * @param idSolicitud
     *        id de la solicitud global.
     * @return aporte manual dto.
     */
    @GET
    @Path("/{idSolicitud}/consultarDevolucionTemporal")
    public DevolucionDTO consultarDevolucionTemporal(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de guardar la información temporal de una
     * corrección.
     * 
     * @param idSolicitud
     *        id de la soliciutd asociada.
     * @param aporteManualDTO
     *        aporte manualDTO.
     */
    @POST
    @Path("/guardarCorreccionTemporal")
    public void guardarCorreccionTemporal(@NotNull @QueryParam("idSolicitud") Long idSolicitud, CorreccionDTO correccionDTO);

    /**
     * Servicio encargado de consultar un aporte manual temporal.
     * 
     * @param idSolicitud
     *        id de la solicitud global.
     * @return aporte manual dto.
     */
    @GET
    @Path("/{idSolicitud}/consultarCorreccionTemporal")
    public CorreccionDTO consultarCorreccionTemporal(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de simular (llamado a HU395, HU396 y HU480)
     * 
     * @param aporteManualDTO
     *        dto con la información a simular.
     * @return AporteManualDTO con los resultados.
     */
    @POST
    @Path("/{idSolicitud}/simularAporteManual")
    public AporteManualDTO simularAporteManual(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de finalizar la información faltante.
     * 
     * @param idSolicitud
     *        datos de la información faltante.
     * @param informacionFaltante
     *        información faltante a registrar.
     */
    @POST
    @Path("/{idSolicitud}/finalizarInformacionfaltante")
    public void finalizarInformacionfaltante(@PathParam("idSolicitud") Long idSolicitud, GestionInformacionFaltanteDTO informacionFaltante,
            @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de actualizar la solicitud y crear su
     * trazabilidad.
     * 
     * @param proceso
     *        Indica el proceso de negocio / tipo de solicitud
     * @param idSolicitud
     *        id de la solicitud global a actualizar
     * @param estado
     *        estado nuevo de la solicitud
     * @param idComunicado
     *        id del comunicado
     * @param userDTO
     *        usuario tomado del contexto
     */
    @POST
    @Path("/{idSolicitud}/actualizarSolicitudTrazabilidad")
    public void actualizarSolicitudTrazabilidad(@PathParam("idSolicitud") Long idSolicitud,
            @QueryParam("proceso") @NotNull ProcesoEnum proceso, @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAporteEnum estado,
            @QueryParam("idComunicado") Long idComunicado, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de cargar el archivo y validar su estructura.
     * 
     * @param identificadorDocumento
     *        en el ECM.
     * @return retorna el resultadoArchivoAporteDTO
     */
    @POST
    @Path("/verificarArchivoPagoManualAportes")
    public ResultadoArchivoAporteDTO verificarArchivoPagoManualAportes(
            @NotNull @QueryParam("identificadorDocumento") String identificadorDocumento);

    /**
     * Servicio que se encarga de cargar el archivo y validar su estructura (Pensionados).
     * 
     * @param identificadorDocumento
     *        en el ECM.
     * @return retorna el resultadoArchivoAporteDTO
     */
    @POST
    @Path("/verificarArchivoPagoManualAportesPensionados")
    public ResultadoArchivoAporteDTO verificarArchivoPagoManualAportesPensionados(
            @NotNull @QueryParam("identificadorDocumento") String identificadorDocumento);

    /**
     * Servicio que se encarga de registrar, es decir convertir los datos
     * temporales en oficiales.
     * 
     * @param idSoliciutd
     *        id de la solicitud.
     */
    @POST
    @Path("/{idSolicitud}/registrarAporte")
    public Boolean registrarAporte(@PathParam("idSolicitud") Long idSoliciutd, @QueryParam("idTarea") Long idTarea,
            @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de radicar una solicitud de devolucion.
     * 
     * @param solicitudDevolucion
     *        datos de la solicitud de devolucion.
     */
    @POST
    @Path("/radicarSolicitudDevolucion")
    public Map<String, String> radicarSolicitudDevolucion(@NotNull SolicitudDevolucionDTO solicitudDevolucion, @Context UserDTO userDTO);

    /**
     * Servicio encargado de finalizar, de parte del analista, la evaluacion de
     * una solicitud de devolución
     * 
     * @param evaluacionAnalista
     *        Información de la solicitud a finalizar por parte del analista
     *        de aportes
     * @param userDTO
     *        Usuario que se obtiene del contexto
     */
    @POST
    @Path("/finalizarEvaluacionAnalista")
    public void finalizarEvaluacionAnalista(@NotNull EvaluacionAnalistaDTO evaluacionAnalista, @Context UserDTO userDTO);

    /**
     * Servicio encargado de finalizar, de parte del supervisor, la evaluacion
     * de una solicitud de correcion.
     * 
     * @param idSolicitud,
     *        identificador de la solicitud de corrección a finalizar.
     * @param evaluacionSolicitud,
     *        resultado de la evaluación hecha por el supervisor. Aquí
     *        rechaza o acepta.
     * @param userDTO,
     *        usuario que se obtiene del contexto.
     */
    @POST
    @Path("/finalizarEvaluacionSupervisor")
    public void finalizarEvaluacionSupervisor(EvaluacionSupervisorDTO evaluacionSupervisorDTO, @Context UserDTO userDTO);

    /**
     * Servicio encargado de finalizar la gestión hecha por el analista
     * contable.
     * 
     * @param idSolicitud,
     *        identificador de la solicitud a la que se le finalizará su
     *        gestión de parte del analista.
     * @param idTarea,
     *        identificador de la tarea a finalizar.
     * @param userDTO,
     *        usuario que se obtiene del contexto.
     */
    @POST
    @Path("/{idSolicitud}/finalizarGestionAnalistaContable")
    public void finalizarGestionAnalistaContable(@PathParam("idSolicitud") Long idSolicitud, @NotNull @QueryParam("idTarea") Long idTarea,
            @Context UserDTO userDTO);

    /**
     * Servicio encargado de finalizar una gestion de analista para aportes y
     * asignarla al analista contabla en caso que sea aprobada la solicitud.
     * 
     * @param gestionAnalista
     *        dto con la información para gestionar el analista.
     * @param userDTO,
     *        usuario que se obtiene del contexto.
     */
    @POST
    @Path("/finalizarGestionAnalistaAporte")
    public void finalizarGestionAnalistaAporte(GestionAnalistaDTO gestionAnalista, @Context UserDTO userDTO, @Context HttpHeaders headers);

    /**
     * Servicio encargado de consultar la informacion faltante por id de
     * solicitud y los documentos.
     * 
     * @param idSolicitud
     *        id de la solicitud a consultar su información faltante.
     * 
     */
    @GET
    @Path("/{idSolicitud}/consultarInformacionFaltante")
    public GestionInformacionFaltanteDTO consultarInformacionFaltante(@PathParam("idSolicitud") Long idSolicitud,
            @QueryParam("proceso") ProcesoEnum proceso);

    /**
     * Servicio encargado de realizar la radicacion de una solicitud de
     * correcion
     * 
     * @param solicitudCorrecion,
     *        solicitud a realizar la radicacion
     */
    @POST
    @Path("/radicarSolicitudCorreccion")
    public Map<String, String> radicarSolicitudCorreccion(@NotNull SolicitudCorreccionDTO solicitudCorreccionDTO, @Context UserDTO userDTO);

    /**
     * Servicio para consultar los datos de un cotizante.
     * 
     * @param DatosCotizanteIntegracionDTO
     *        datos para buscar el cotizante.
     * @param tipoSolicitante
     *        tipo de solicitante.
     * @return DatosCotizanteDTO datos retornados con la informacion
     */
    @POST
    @Path("/consultarDatosCotizante")
    public DatosCotizanteDTO consultarDatosCotizante(DatosCotizanteDTO datosCotizante,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSoliciante);

    /**
     * Servicio que consulta la información necesaria para la gestión de un
     * aporte a nivel de cotizante (proceso de devolución y corrección)
     * 
     * @param modalidadRecaudo
     *        Modalidad de recaudo (Pila manual, PILA automático o Aporte
     *        Manual)
     * @param idPlanilla
     *        Número de planilla, si el aporte se hizo por PILA
     * @param cotizanteDTO
     *        Información del cotizante
     * @return Información del cotizante con la información actualizada para
     *         realizar la gestión del aporte
     */
    @POST
    @Path("/gestionarCotizante/{modalidadRecaudo}")
    public CotizanteDTO gestionarCotizante(@PathParam("modalidadRecaudo") ModalidadRecaudoAporteEnum modalidadRecaudo,
            @QueryParam("idPlanilla") Long idPlanilla, @NotNull CotizanteDTO cotizanteDTO);

     /**
     * Servicio que consulta la información necesaria para la gestión de un
     * aporte a nivel de cotizante (proceso de devolución y corrección)
     * 
     * @param modalidadRecaudo
     *        Modalidad de recaudo (Pila manual, PILA automático o Aporte
     *        Manual)
     * @param idPlanilla
     *        Número de planilla, si el aporte se hizo por PILA
     * @param cotizanteDTO
     *        Información del cotizante
     * @return Información del cotizante con la información actualizada para
     *         realizar la gestión del aporte
     */
    @POST
    @Path("/gestionarCotizantes/{modalidadRecaudo}")
    public List<CotizanteDTO> gestionarCotizantes(@PathParam("modalidadRecaudo") ModalidadRecaudoAporteEnum modalidadRecaudo,
            @QueryParam("idPlanilla") Long idPlanilla, @NotNull List<CotizanteDTO> cotizanteDTO);
            
    /**
     * Servicio que consulta la información necesaria para la gestión de un
     * aporte a nivel de cotizante (Vista 360)
     * 
     * @param modalidadRecaudo
     *        Modalidad de recaudo (Pila manual, PILA automático o Aporte
     *        Manual)
     * @param idPlanilla
     *        Número de planilla, si el aporte se hizo por PILA
     * @param cotizanteDTO
     *        Información del cotizante
     * @return Información del cotizante con la información actualizada para
     *         realizar la gestión del aporte
     */
    @POST
    @Path("/gestionarCotizanteV360/{modalidadRecaudo}")
    public CotizanteDTO gestionarCotizanteV360(@PathParam("modalidadRecaudo") ModalidadRecaudoAporteEnum modalidadRecaudo,
            @QueryParam("idPlanilla") Long idPlanilla, @NotNull CotizanteDTO cotizanteDTO);

    /**
     * Método que realiza la simulación de una solicitud de devolución
     * alamcenada en la tabla temporal
     * 
     * @param idSolicitudGlobal
     *        Identificador de la solicitud global
     * @return El resultado de la simulación
     */
    @POST
    @Path("/simularDevolucion/{idSolicitudGlobal}")
    public DevolucionDTO simularDevolucionTemporal(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que consulta la información necesaria para gestionar un aporte
     * sin detalle
     * 
     * @param analisisDevolucionDTO
     *        Información del aporte a gestionar
     * @return Objeto <code>AnalisisDevolucionDTO</code> actualizado con la
     *         evaluación histórica del aporte
     */
    @POST
    @Path("/gestionarAporteSinDetalle")
    public AnalisisDevolucionDTO gestionarAporteSinDetalle(@NotNull AnalisisDevolucionDTO analisisDevolucionDTO);

    /**
     * Servicio encargado de realizar la simulacion de corrección de segundo
     * nivel.
     * 
     * @param idSolicitud
     *        id de la solicitud global de corrección.
     */
    @POST
    @Path("/{idSolicitud}/simularCorreccionTemporal")
    public List<CotizanteDTO> simularCorreccionTemporal(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de simular una corrección de aportes
     * 
     * @param idSolicitud
     *        Identificador de la solicitud global asociada
     * @return CorreccionDTO Objeto con el resultado de la simulación
     */
    @POST
    @Path("/{idSolicitud}/simularAporteCorreccion")
    public CorreccionAportanteDTO simularAporteCorreccion(@PathParam("idSolicitud") Long idSolicitud, CorreccionAportanteDTO correccion);

    /**
     * Servicio encargado de finalizar la corrección.
     * 
     * @param idSolicitud
     *        id de la solicitud global.
     */
    @POST
    @Path("/{idSolicitud}/finalizarCorreccion")
    public void finalizarCorreccion(@PathParam("idSolicitud") Long idSolicitud, @QueryParam("idTarea") Long idTarea,
    		@QueryParam("instaciaProceso") Long instaciaProceso, @Context UserDTO userDTO, @Context HttpHeaders header);

    @POST
    @Path("/finalizarCorreccionAsyncMasiva")
    public void finalizarCorreccionAsyncMasiva(InformacionSolicitudDTO infoSolicitud);

    /**
     * Servicio encargado de finalizar la corrección.
     * 
     * @param infoSolicitud
     *        Información de la solicitud.
     */
    @POST
    @Path("/finalizarCorreccionAsync")
    public void finalizarCorreccionAsync(InformacionSolicitudDTO infoSolicitud);
    
    /**
     * Servicio encargado de actualizar la trazabilidad en su comunicado
     * 
     * @param id
     *        de la trazabilidad a actualizar, id de la solicitud a
     *        actualizar
     */
    @POST
    @Path("/{idSolicitud}/actualizarComunicadoTrazabilidad")
    public void actualizarComunicadoTrazabilidad(@PathParam("idSolicitud") Long idSolicitud,
            @QueryParam("actividad") ActividadEnum actividad);

    /**
     * Servicio que se encarga de consultar los cotizantes que estuvieron
     * activos en el periodo cotizado.
     * 
     * @param tipoIdentificacion
     *        tipo de identificacion del empleador.
     * @param numeroIdentificacion
     *        número de identificacion del empleador.
     * @param periodoAporte
     *        perido del aporte de pago.
     * @return lista de los cotizantes.
     */
    @GET
    @Path("/consultarCotizantes")
    public List<CotizanteDTO> consultarCotizantes(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodoAporte") Long periodoAporte);

    /**
     * Servicio que se encarga de consultar el estado de un cotizante en un periodo dado.
     * @param tipoIdentificacion
     *        tipo de identificación del cotizante.
     * @param numeroIdentificacion
     *        número de identificación.
     * @param tipoIdentificacionAportante
     *        tipo de identificación del aportante.
     * @param numeroIdentifacionAportante
     *        número de identificación del aportante.
     * @param periodoAporte
     *        periodo del aporte.
     * @return estado del afiliado.
     */
    @GET
    @Path("/consultarEstadoAfiliadoPorPeriodo")
    public EstadoAfiliadoEnum consultarEstadoCotizantePorPeriodo(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacionAportante") TipoIdentificacionEnum tipoIdentificacionAportante,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("periodoAporte") Long periodoAporte,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que se encarga de consultar el estado de un aportante en un periodo dado.
     * @param tipoIdentificacion
     *        tipo de identificación del aportante.
     * @param numeroIdentificacion
     *        número de identificación.
     * @param periodoAporte
     *        periodo del aporte.
     * @return estado del empleador.
     */
    @GET
    @Path("/consultarEstadoAportantePorPeriodo")
    public EstadoEmpleadorEnum consultarEstadoAportantePorPeriodo(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodoAporte") Long periodoAporte,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de verificar los aportantes que no cuenten con el día hábil de vencimiento de aportes para su actualización
     */
    @POST
    @Path("/verificarDiaVencimientoAportantes")
    public void verificarDiaVencimientoAportantes();

    /**
     * Servicio que valida si el total de los aportes coincide con la suma de aportes de los cotizantes
     * 
     * @param idSolicitud
     *        id de la Solicitud global
     * @return Retorna false en el caso que el total de aportes no coincida con los aportes de los cotizantes
     */
    @POST
    @Path("/{idSolicitud}/validarTotalAportes")
    public Boolean validarTotalAportes(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que consulta los solicitantes relacionados a un aporte
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del aportante
     * @param numeroIdentificacion
     *        Número de identificación del aportante
     * @param primerNombre
     *        Primer nombre del aportante
     * @param primerApellido
     *        Primer apellido del aportante
     * @param segundoNombre
     *        Segundo nombre del aportante
     * @param segundoApellido
     *        Segundo apellido del aportante
     * @param razonSocial
     *        Razón social del aportante
     * @param tipoAportante
     *        Tipo de aportante
     * @return Lista de los solicitantes asociados al aporte general por los parametros de búsqueda ingresados
     */
    @GET
    @Path("/obtenerSolicitantesCuentaAporte")
    public List<SolicitanteDTO> consultarSolicitanteCuentaAporte(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("primerApellido") String primerApellido, @QueryParam("segundoNombre") String segundoNombre,
            @QueryParam("segundoApellido") String segundoApellido, @QueryParam("razonSocial") String razonSocial,
            @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
            @QueryParam("numeroOperacion") Long numeroOperacionAportante);

    /**
     * Servicio que valida si el total de los aportes coincide con la suma de aportes de los cotizantes
     * 
     * @param idSolicitud
     *        id de la Solicitud global
     * @return Retorna false en el caso que el total de aportes no coincida con los aportes de los cotizantes
     */
    @POST
    @Path("/validarTotalAportesCorreccion")
    public Boolean validarTotalAportesCorreccion(CorreccionAportanteDTO correccion);

    /**
     * Servicio para consultar los datos de varios cotizantes.
     * 
     * @param DatosCotizanteIntegracionDTO
     *        datos para buscar el cotizante.
     * @param tipoSolicitante
     *        tipo de solicitante.
     * @return DatosCotizanteDTO datos retornados con la informacion
     */
    @POST
    @Path("/consultarDatosCotizanteCargue")
    public List<DatosCotizanteDTO> consultarDatosCotizanteCargue(List<DatosCotizanteDTO> datosCotizante,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSoliciante);

    /**
     * Servicio encargado de verificar que los datos ingresados en la correción para la sucursal del empleador o aportante
     * cumpla con lo registrado en core
     * @param idRegistroGeneral
     *        Identificador del registro general
     * @return Si cumple o no cumple las condiciones de la sucursal
     */
    @POST
    @Path("/verificarCumplimientoSucursal")
    public Boolean verificarCumplimientoSucursal(@NotNull @QueryParam("idRegistroGeneral") Long idRegistroGeneral);

    /**
     * Servicio que consulta las cuentas de un aporte por el id del aporte
     * general mostrando los recaudos y ajustes ya sea de corrección o
     * devolución
     * 
     * @param idAporteGeneral
     * @return
     */
    @POST
    @Path("/consultarCuentaAporteVista")
    public List<CuentaAporteDTO> consultarCuentaAporteVista(@QueryParam("idPersonaCotizante") Long idPersonaCotizante,
            List<AnalisisDevolucionDTO> analisisDevolucionDTO,
            @QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
            @QueryParam("idSolicitudDevolucion") Long idSolicitudDevolucion);

    /**
     * Servicio que consulta la trazabilidad de aportes
     * 
     * @param idSolicitud
     * @return
     */
    @GET
    @Path("/{idSolicitud}/consultarTrazabilidadAportes")
    public List<RegistroEstadoAporteModeloDTO> consultarTrazabilidadAportes(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio encargado de consultar las solicitudes de devoluciones de aportes asociadas a los identificadores de aportes generales
     * 
     * @param idsAporteGeneral
     *        Identificadores de aportes generales
     * @return Lista de solicitudes de devoluocines de aportes
     */
    @POST
    @Path("/consultarRecaudoCorreccion")
    public List<AnalisisDevolucionDTO> consultarRecaudoCorreccion(ConsultarRecaudoDTO consultaRecaudo,
            @QueryParam("tipoMovimientoRecaudoAporte") TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudoAporte,
            @QueryParam("hayParametros") Boolean hayParametros, @QueryParam("vista360") Boolean vista360);
    
    /**
     * Relaciona el comunicado con RegistroEstadoAporte
     * (Para los casos en que la generación del comunicado y la creación de RegistroEstadoAporte ocurren en la misma transacción )
     * 
     * @param id
     *        de la trazabilidad a actualizar, id de la solicitud a
     *        actualizar
     */
    @POST
    @Path("/{idSolicitud}/actualizarComunicadoTrazabilidadAporte")
    public void actualizarComunicadoTrazabilidadAporte(@PathParam("idSolicitud") Long idSolicitud,
            @QueryParam("actividad") ActividadEnum actividad, @QueryParam("idComunicado") Long idComunicado);
    
    /**
     * Servicio que se encarga de consultar la cantidad de cotizantes que estuvieron
     * activos en el periodo cotizado.
     * 
     * @param tipoIdentificacion
     *        tipo de identificacion del empleador.
     * @param numeroIdentificacion
     *        número de identificacion del empleador.
     * @param periodoAporte
     *        perido del aporte de pago.
     * @return lista de los cotizantes.
     */
    @GET
    @Path("/consultarNumeroCotizantes")
    public Long consultarNumeroCotizantes(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodoAporte") Long periodoAporte);
    
    /**
     * Servicio que se encarga de registrar un aporte, validando que no se haya realizado la misma petición mas de una vez desde la pantalla
     * 
     * @param idSoliciutd
     *        id de la solicitud.
     */
    @POST
    @Path("/{idSolicitud}/validarRegistrarAporteCorrecion")
    public Map<String, Object> validarRegistrarAporteCorrecion(@PathParam("idSolicitud") Long idSoliciutd, @QueryParam("idTarea") Long idTarea,
            @QueryParam("instaciaProceso") Long instaciaProceso, @Context UserDTO userDTO);


    @POST
    @Path("/registrarAporteConDetalleAsync")
    public void registrarAporteConDetalleAsync(
        @QueryParam("origenAporte") OrigenAporteEnum origenAporte,
        @QueryParam("cajaAporte") Integer cajaAporte,
        @QueryParam("idSolicitud") Long idSolicitud,
        @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
        @QueryParam("pagadorTercero") Boolean pagadorTercero,
        @QueryParam("idRegistroGeneral") Long idRegistroGeneral,
        @QueryParam("fechaRecaudo") Long fechaRecaudo,
        @QueryParam("cuentaBancariaRecaudo") Integer cuentaBancariaRecaudo);


}
