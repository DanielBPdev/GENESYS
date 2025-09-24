package com.asopagos.bandejainconsistencias.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.asopagos.bandejainconsistencias.dto.BandejaEmpleadorCeroTrabajadoresDTO;
import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaRespuestaDTO;
import com.asopagos.bandejainconsistencias.dto.CreacionAportanteDTO;
import com.asopagos.bandejainconsistencias.dto.DatosBandejaTransitoriaDTO;
import com.asopagos.bandejainconsistencias.dto.IdentificadorDocumentoDTO;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import com.asopagos.bandejainconsistencias.dto.PreparacionAprobacion399DTO;
import com.asopagos.bandejainconsistencias.dto.ResultadoAprobacionCambioIdentificacionDTO;
import com.asopagos.dto.ActualizacionEstadosPlanillaDTO;
import com.asopagos.dto.EmpAporPendientesPorAfiliarDTO;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import com.asopagos.dto.modelo.ExcepcionNovedadPilaModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.SolicitudCambioNumIdentAportante;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.RazonRechazoSolicitudCambioIdenEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * Servicio de Archivos PILA-Bandeja <br>
 * Este servicio sera el encargado de controlar la bandeja de inconsistencias de
 * la HU 392 y HU-399
 * 
 * @author <a href="mailto:anbuitrago@heinsohn.com.co">Andres Felipe Buitrago
 *         F.</a>
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez
 *         Cediel </a>
 *
 */

@Path("pilaBandeja")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface PilaBandejaService {

    /**
     * Método para la consulta general de archivos con inconsistencia
     * 
     * @param tipoIdentificacion
     *        El tipo de identificacion para la busqueda
     * @param numeroPlanilla
     *        Numero de planilla asociado a la busqueda
     * @param fechaInicio
     *        Fecha inicial de busqueda
     * @param fechaFin
     *        Fecha final de busqueda
     * @param numeroIdentificacion
     *        Numero de identificacion de busqueda
     * @param operador
     *        Tipo de operador de la busqueda
     * @param userDTO
     *        Datos del usuario
     * @return List<InconsistenciaDTO> Lista de las inconsistencias encontradas
     *         asociadas a los parametros de busqueda
     */
    @GET
    @Path("consultarArchivosInconsistentesResumen")
    public List<InconsistenciaDTO> consultarArchivosInconsistentesResumen(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroPlanilla") String numeroPlanilla,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("operador") TipoOperadorEnum operador,
            @QueryParam("digitoVerificacion") Short digitoVerificacion, @QueryParam("bloqueValidacion") String bloqueValidacion, @QueryParam("ocultarBlq5") Boolean ocultarBlq5);

    /**
     * Metodo para consultar que tipo de inconsistencias posee un item
     * 
     * @param inconsistencia
     *        Contiene datos referentes a la inconsistencia encontrada
     * 
     * 
     * @return List<TipoInconsistenciasEnum> lista que contiene los tipo de
     *         inconsistencias que posee el item seleccionado
     */

    @POST
    @Path("accionBandejaInconsistencias")
    public List<TipoInconsistenciasEnum> accionBandejaInconsistencias(InconsistenciaDTO inconsistencia);

    /**
     * Metodo para consultar el detalle del tipo de inconsistencias de una
     * planilla
     * 
     * 
     * @param inconsistencias
     *        DTO con los datos referentes a la planilla que contiene
     *        errores
     * @param tipoInconsistencia
     *        El tipo de inconsistencias que se desea consultar acerca de la
     *        planilla
     * 
     * @return List<InconsistenciaDTO> lista que contiene la descripcion de los
     *         datos de inconsistencias segun el tipo y la planilla
     */
    @POST
    @Path("accionBandejaDetalleInconsistencias")
    public List<InconsistenciaDTO> accionBandejaDetalleInconsistencias(InconsistenciaDTO inconsistencias,
            @QueryParam(value = "tipoInconsistencia") TipoInconsistenciasEnum tipoInconsistencia, 
            @Context UriInfo uri, @Context HttpServletResponse response);

    // metodos de las acciones de los botones

    /**
     * Metodo que anula las planillas que se envien
     * 
     * @param inconsistencia
     *        Contiene los datos del archivo que se desea establecer como
     *        anulado
     * @return Boolean indicando true si fue exitoso o false si no se pudo
     *         realizar la modificacion
     */
    @PUT
    @Path("anularPlanillaOI")
    public void anularPlanillaOI(InconsistenciaDTO inconsistencia, @Context UserDTO user);

    /**
     * Metodo que establece en las plantillas el estado Estructura validada
     * 
     * @param inconsistencia
     *        DTO que contiene los datos de la planilla que se va a
     *        modificar
     * 
     */
    @PUT
    @Path("validarEstructuraPlanilla")
    public void validarEstructuraPlanilla(InconsistenciaDTO inconsistencia);

    @POST
    @Path("persistirHistoricoBloque2")
    public void persistirHistoricoBloque2(Long indicePlanilla, @Context UserDTO userDTO);

    /**
     * Metodo que establece en las planillas financieras el estado anulado
     * 
     * @param inconsistencia
     *        DTO que contiene los datos de la planilla que se va a
     *        modificar
     * 
     */
    @PUT
    @Path("anularPlanillaOF")
    public void anularPlanillaOF(InconsistenciaDTO inconsistencia, @Context UserDTO user);

    /**
     * Metodo que envia una solicitud de cambio de identificacion del aportante
     * a la bandeja del supervisor
     * 
     * @param solicitudCambio
     *        contiene los campos requeridos para enviar a la bandeja
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    @POST
    @Path("enviarSolicitudCambioIdenPila")
    public void enviarSolicitudCambioIdenPila(InconsistenciaDTO inconsistencias,
            @QueryParam(value = "numeroIdentificacion") Long numeroIdentificacion, @Context UserDTO user);

    /**
     * Metodo que aprueba las solicutudes de cambio de identificacion
     * 
     * @param List<SolicitudCambioNumIdentAportante>
     *        contiene las solicitudes que se van a aprobar
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    @POST
    @Path("aprobarSolicitudCambioIdentificacion")
    public ResultadoAprobacionCambioIdentificacionDTO aprobarSolicitudCambioIden(
            @NotNull List<SolicitudCambioNumIdentAportante> listaSolicitudes, @Context UserDTO user);

    /**
     * Metodo que rechaza las solicutudes de cambio de identificacion
     * 
     * @param List<SolicitudCambioNumIdentAportante>
     *        contiene las solicitudes que se van a aprobar
     * @param user
     *        contiene los datos del usuario que esta realizando la
     *        solicitud
     */
    @POST
    @Path("rechazarSolicitudCambioIdenficacion")
    public void rechazarSolicitudCambioIden(@NotNull List<SolicitudCambioNumIdentAportante> listaSolicitudes, @Context UserDTO user,
            @QueryParam("razonRechazo") RazonRechazoSolicitudCambioIdenEnum razonRechazo, @QueryParam("comentarios") String comentarios);

    /**
     * Metodo de busqueda de solicitudes de cambio de identificacion para la
     * pantalla de la HU-411
     * 
     * @param numeroPlanilla
     *        numero de planilla a buscar
     * @param fechaInicio
     *        fecha de inicio de busqueda
     * 
     * @param fechaFin
     *        fecha fin de busqueda
     */
    @GET
    @Path("busquedaSolicitudesCambioIden")
    public List<SolicitudCambioNumIdentAportante> busquedaSolicitudCambioIden(@QueryParam("numeroPlanilla") Long numeroPlanilla,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin);

    /**
     * Servicio que prepara la creación de un aportante por bandeja 392
     * @param numeroPlanilla
     * @param inconsistencia
     * @return
     */
    @POST
    @Path("crearAportante")
    public CreacionAportanteDTO crearAportante(@QueryParam("numeroPlanilla") Long numeroPlanilla, InconsistenciaDTO inconsistencia);

    /**
     * Metodo para retornar un indice de planilla para ver el archivo
     * 
     * @param idPlanilla
     */
    @GET
    @Path("verArchivo")
    public IdentificadorDocumentoDTO veArchivo(@QueryParam("idPlanilla") Long idPlanilla,
            @QueryParam("tipoArchivo") TipoArchivoPilaEnum tipoArchivo);

    /**
     * Capacidad que permite la consulta de las planillas con alguna
     * inconsistencia en el aporte para ser gestionadas en la bandeja de aportes
     * 
     * @return <code>Long</code> El total de archivos de planillas con
     *         inconsistencias
     */
    @GET
    @Path("contarPlanillasConInconsistenciasPorGestionar")
    public Integer contarPlanillasConInconsistenciasPorGestionar();

    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser gestionadas en la bandeja de
     * aportes
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
     *        del aportante a consultar
     * @param numeroIdentificacionAportante
     *        <code>String</code> El numero de identificacion del aportante
     *        a consultar
     * @param digitoVerificacionAportante
     *        <code>Short</code> El digito de verificacion del aportante
     * @param fechaInicio
     *        <code>Long</code> la fecha de inicio del procesamiento del
     *        aporte de la planilla
     * @param fechaFin
     *        <code>Long</code> la fecha de fin del procesamiento del aporte
     *        de la planilla
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    @GET
    @Path("consultarPlanillasPorGestionarConInconsistenciasValidacion")
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorGestionarConInconsistenciasValidacion(
            @QueryParam("tipoIdentificacionAportante") TipoIdentificacionEnum tipoIdentificacionAportante,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("digitoVerificacionAportante") Short digitoVerificacionAportante, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin);

    /**
     * Capacidad que permite la consulta de los registros de aporte de las
     * planillas con alguna inconsistencia para ser aprobadas en la bandeja de
     * aportes
     * 
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> El tipo de identificacion
     *        del aportante a consultar
     * @param numeroIdentificacionAportante
     *        <code>String</code> El numero de identificacion del aportante
     *        a consultar
     * @param digitoVerificacionAportante
     *        <code>Short</code> El digito de verificacion del aportante
     * @param fechaInicio
     *        <code>Long</code> la fecha de inicio del procesamiento del
     *        aporte de la planilla
     * @param fechaFin
     *        <code>Long</code> la fecha de fin del procesamiento del aporte
     *        de la planilla
     * 
     * @return List<code>InconsistenciaRegistroAporteDTO</code> La lista de los
     *         registros con inconsistencias de archivos de planilla
     */
    @GET
    @Path("consultarPlanillasPorAprobarConInconsistenciasValidacion")
    public List<InconsistenciaRegistroAporteDTO> consultarPlanillasPorAprobarConInconsistenciasValidacion(
            @QueryParam("tipoIdentificacionAportante") TipoIdentificacionEnum tipoIdentificacionAportante,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("digitoVerificacionAportante") Short digitoVerificacionAportante, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin);

    /**
     * Capacidad que permite la aprobacion de manera manual de los registros de
     * aporte de las planillas con alguna inconsistencia seleccionados en la
     * bandeja de aportes
     * 
     * @param inconsistenciaRegistroAporteDTO
     *        <code>InconsistenciaRegistroAporteDTO</code>
     *        El registro con inconsistencia de archivos de planilla seleccionados a aprobar
     * 
     * @return <code>InconsistenciaRegistroAporteDTO</code>
     *         el DTO de registro con inconsistencia de archivos de planilla
     */
    @POST
    @Path("aprobarRegistroAporteConInconsistencia")
    public InconsistenciaRegistroAporteDTO aprobarRegistroAporteConInconsistencia(
            @NotNull InconsistenciaRegistroAporteDTO inconsistenciaRegistroAporteDTO, @Context UserDTO user);

    /**
     * 389 Metodo para la busqueda del control de resultados de parte del
     * aportante
     * 
     * @param tipoDocumento
     *        <code>TipoIdentificacionEnum</code> contiene la identificacion
     *        de la persona
     * @param numeroIdentificacion
     *        <code>String</code> Contiene el valor de la identificacion del
     *        aportante
     * @param numeroPlanilla
     *        <code>Long</code> Contiene el numero de planilla por el que se
     *        desea consultar
     * @param periodo
     *        <code>Long</code> Contiene la fecha por la que se desea
     *        consultar
     * @return List<code>RespuestaConsultaEmpleadorDTO</code> DTO que contiene
     *         como cabecera la identificacion del aportante y una lista de
     *         cotizaciones
     */
    @GET
    @Path("buscarControlResultadosAportante")
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosEmpleador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoDocumento, @QueryParam("numeroIdentificacion") String idAportante,
            @QueryParam("numeroPlanilla") Long numeroPlanilla, @QueryParam("periodoIngreso") Long periodo, @Context UserDTO userDTO);

    /**
     * 389 Metodo para la busqueda del control de resultados dde una persona
     * <code>SolicitudCambioNumIdentAportante</code>
     * 
     * @param tipoDocumento
     *        <code>TipoIdentificacionEnum</code> contiene la identificacion
     *        de la persona
     * @param numeroIdentificacion
     *        <code>String</code> Contiene el valor de la identificacion del
     *        aportante
     * @param numeroPlanilla
     *        <code>Long</code> Contiene el numero de planilla por el que se
     *        desea consultar
     * @param periodo
     *        <code>Long</code> Contiene la fecha por la que se desea
     *        consultar
     * @return List<code>RespuestaConsultaEmpleadorDTO</code> DTO que contiene
     *         como cabecera la identificacion del aportante y una lista de
     *         cotizaciones
     */
    @GET
    @Path("buscarControlResultadosPersona")
    public List<RespuestaConsultaEmpleadorDTO> buscarControlResultadosPersona(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoDocumento, @QueryParam("numeroIdentificacion") String idAportante,
            @QueryParam("numeroPlanilla") Long numeroPlanilla, @QueryParam("periodoIngreso") Long periodo, @Context UserDTO userDTO);

    /**
     * Capacidad que permite buscar los empleadores sin afiliar dentro de la caja.
     * La funcionalidad se describe a detalle en la HU-403
     * 
     * @param numeroIdentificacion
     *        <code>String</code> Contiene la identificacion de la persona
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code> Contiene el tipo de documento
     *        de la persona
     * @param digitoVerificacion
     *        <code>Short</code> Contiene el digito de verificaion de la empresa
     * @param fechaIngresoBandeja
     *        <code>Date</code> La fecha en la cual la empresa ingresa a esta bandeja
     * @param uri
     * @param response
     * @return List<EmpAporPendientesPorAfiliarDTO>
     *         <code> Listado de empleadores sin afiliar </code>
     * 
     */
    @GET
    @Path("consultarEmpPendientesPorAfiliar")
    public List<EmpAporPendientesPorAfiliarDTO> consultarEmpPendientesPorAfiliar(
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("digitoVerificacion") Short digitoVerificacion, @QueryParam("fechaIngresoBandeja") Long fechaIngresoBandeja,
            @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * HU-404 Metodo para busqueda de empleadores con cero trabajadores activos
     *
     * @param numeroIdentificacion
     * @param tipoIdentificacion
     * @param nombreEmpresa
     * @param digitoVerificacion
     * @param fechaInicioIngresoBandeja
     * @param fechaFinIngresoBandeja
     * @param uri
     * @param response
     * @return
     */
    @GET
    @Path("consultarEmpCeroTrabajadoresActivos")
    public List<BandejaEmpleadorCeroTrabajadoresDTO> consultarEmpCeroTrabajadoresActivos(
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("nombreEmpresa") String nombreEmpresa,
            @QueryParam("digitoVerificacion") Short digitoVerificacion,
            @QueryParam("fechaInicioIngresoBandeja") Long fechaInicioIngresoBandeja,
            @QueryParam("fechaFinIngresoBandeja") Long fechaFinIngresoBandeja, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * HU-405 Metodo de busqueda de los empleadores que presentaron problemas en
     * el envio de una notificacion
     * 
     * @return resultado List<code>ActualizacionDatosEmpleadorModeloDTO</code>
     */
    @GET
    @Path("consultarActualizacionDatosEmpleador")
    public List<ActualizacionDatosEmpleadorModeloDTO> consultarActualizacionDatosEmp(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoDocumento, @QueryParam("numeroIdentificacion") String idAportante,
            @QueryParam("digitoVerificacion") Short digitoVerificacion, @QueryParam("fechaIngresoBandeja") Long fechaIngresoBandeja);

    /**
     * HU-405 Metodo de actualizacion de los empleadores que presentaron
     * problemas en el envio de una notificacion
     *
     */
    @POST
    @Path("actualizarDatosEmpleador")
    public void ActualizarActualizacionDatosEmp(List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion);

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 392
     */
    @GET
    @Path("inconsistenciasPendientes")
    public Long totalInconsistenciasPendientes();

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 411
     */
    @GET
    @Path("inconsistenciasPendientesAprobacionCambioIdentificacion")
    public Long totalInconsistenciasPendientesAprobacion();

    /**
     * Metodo que obtiene la cantidad de inconsistencias pendientes para la HU
     * 411
     */
    @GET
    @Path("validarRespuestaCambioId")
    public InconsistenciaDTO validarRespuestaCambioId(@QueryParam("idError") Long idErrorValidacion);

    /**
     * HU-404 Metodo para actualizar el registro vigente de la bandeja
     * @param idEmpleadores
     *        ID de los empleadores seleccionados
     * @param user
     *        Usuario que realiza la operacion
     */
    @POST
    @Path("actualizarEmpleadoresGestionadosBandejaCero")
    public void actualizarEmpleadoresGestionadosBandejaCero(List<Long> idEmpleadores, @Context UserDTO user);

    /**
     * HU-404 Metodo para mantener una lista de empleadores en gestion enviada desde
     * pantalla
     * @param idEmpleadores
     *        ID de los empleadores seleccionados
     * @param user
     *        Usuario que realiza la operacion
     */
    @POST
    @Path("mantenerAfiliacionEmpleadoresBandejaCero")
    public void mantenerAfiliacionEmpleadoresBandejaCero(List<Long> idEmpleadores, @Context UserDTO user);

    /**
     * HU-389 Metodo para busquda por persona usando criterios
     * @param tipoDocumento
     * @param idAportante
     * @param numeroPlanilla
     * @param periodo
     * @param userDTO
     * @return
     */
    @GET
    @Path("buscarPorPersonaCriterios")
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorPersonaCriterios(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoDocumento,
            @QueryParam("numeroIdentificacion") String numIdentificacion, @QueryParam("numeroPlanilla") String numeroPlanilla,
            @QueryParam("periodo") String periodo, @Context UserDTO userDTO, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * HU-389 Metodo para busqueda por aportante usando criterios
     * @param tipoDocumento
     * @param idAportante
     * @param numeroPlanilla
     * @param periodo
     * @param userDTO
     * @return
     */
    @GET
    @Path("buscarPorAportanteCriterios")
    public List<BusquedaPorPersonaRespuestaDTO> buscarPorAportanteCriterios(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoDocumento, @QueryParam("numeroIdentificacion") String idAportante,
            @QueryParam("numeroPlanilla") String numeroPlanilla, @QueryParam("periodo") String periodo,
            @QueryParam("registroControl") Long registroControl, @Context UserDTO userDTO, @Context UriInfo uri,
            @Context HttpServletResponse response);

    /**
     * HU-389 Retorna el detalle para paginacion de cada tabla
     * @param registroControl
     * @param userDTO
     * @param uri
     * @param response
     * @return Lista con los registros detallados
     */
    @GET
    @Path("detalleAportanteCriterios")
    public List<RegistroDetalladoModeloDTO> detalleAportanteCriterios(@QueryParam("registroControl") Long registroControl,
            @Context UserDTO userDTO, @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * HU-392 Servicio para modificar los estados de una planilla PILA derivado de la
     * creación de un aportante por bandeja de inconsistencias
     * 
     * @param indicePlanilla
     *        Entity del índice de planilla a modificar
     * @author abaquero
     */
    @POST
    @Path("actualizarEstadoPlanillaAportanteNuevo")
    public IndicePlanilla actualizarEstadoPlanillaNuevoAportante(@NotNull IndicePlanilla indicePlanilla, @Context UserDTO user);


    /**
     * Servicio encargado de preparar a nivel de registro general y detallo, la aprobación de inconsistencias en bandeja 399
     * 
     * @param datosAprobacion
     *        DTO con los datos requeridos para llevar a acabo la aprobación de registros de bandeja 399
     * @return <b>PreparacionAprobacion399DTO</b>
     *         El DTO de entrada, con la marca de proceso exitoso o fallido
     */
    @POST
    @Path("aprobarDetales")
    public void aprobarDetalles(List<Long> IdsDetallados, Boolean reproceso, String Usuario);

    /**
     * Servicio encargado de preparar a nivel de registro general y detallo, la aprobación de inconsistencias en bandeja 399
     * 
     * @param datosAprobacion
     *        DTO con los datos requeridos para llevar a acabo la aprobación de registros de bandeja 399
     * @return <b>PreparacionAprobacion399DTO</b>
     *         El DTO de entrada, con la marca de proceso exitoso o fallido
     */
    @POST
    @Path("aprobarRegistrosBandeja399")
    public PreparacionAprobacion399DTO aprobarRegistrosBandeja399(@NotNull PreparacionAprobacion399DTO datosAprobacion);

    /**
     * Servicio encargado de consultar un listado de DTO para actualización de planillas PILA respecto a los estados
     * de un grupo de IDs de registro general
     * 
     * @param idsRegistroGeneral
     *        Listado de IDs de registro general a consultar
     * @param esSimulado
     *        Indica que se trata de un proceso de simulación por HU-211-410
     * @param esReproceso
     *        Indica que la actualización obedece a un reproceso
     * @return <b>List<ActualizacionEstadosPlanillaDTO></b>
     *         Listado de DTOs con los datosd para la actualización de índices de planilla PILA
     */
    @POST
    @Path("consultarDatosActualizacionPlanilla")
    public List<ActualizacionEstadosPlanillaDTO> consultarDatosActualizacionPlanilla(@NotNull List<Long> idsRegistroGeneral,
            @NotNull @QueryParam("esSimulado") Boolean esSimulado, @NotNull @QueryParam("esReproceso") Boolean esReproceso);

	/**
	 * Servicio para el recálculo del estado del archivo en registros generales por reproceso 
	 * @param idTransaccion
	 *        ID de la transacción para el procesamiento de PILA 2
	 */
    @POST
    @Path("recalcularEstadoRegistroGeneral")
	public void recalcularEstadoRegistroGeneral(@NotNull @QueryParam("idTransaccion") Long idTransaccion);

    /**
     * Servicio para la consulta de la existencia de una persona en BD
     * @param tipoId
     *        Tipo de identificación de la persona
     * @param numId
     *        Número de identificación de la persona
     * @return <b>Boolean</b> Indica que la persona existe en BD o no
     */
    @GET
    @Path("validarExistenciaPersona")
    public Boolean validarExistenciaPersona(@QueryParam("TipoIdentificacionEnum") @NotNull TipoIdentificacionEnum tipoId,
            @QueryParam("numId") @NotNull String numId);
    
	/**
	 * Servicio encargado de consultar las planillas que sufrieron un fallo en el
	 * procesamiento durante la transición de los datos temporales a core.
	 * 
	 * @param UserDTO se recibe por contexto con la información del usuario que
	 *                realiza la acción
	 * @return
	 */
    @GET
    @Path("bandejaTransitoriaGestion")
	public List<DatosBandejaTransitoriaDTO> bandejaTransitoriaGestion(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @QueryParam("numeroPlanilla") String numeroPlanilla,
            @QueryParam("fechaInicio") Long fechaInicio, 
            @QueryParam("fechaFin") Long fechaFin,
    		@Context UserDTO userDTO);
    
    /**
	 * Servicio que retorna el detalle de lo ocurrido en el fallo del procesamiento
	 * de la información transitoria de la planilla
	 * 
	 * @param idPilaEstadoTransitorio
	 * @param userDTO
	 * @return
	 */
    @GET
    @Path("detalleBandejaTransitoriaGestion")
	public DatosBandejaTransitoriaDTO detalleBandejaTransitoriaGestion(
			@NotNull @QueryParam("idPilaEstadoTransitorio") Long idPilaEstadoTransitorio, 
			@Context UserDTO userDTO);
    
    /**
     * Método que retorna los errores que ha tenido una temNovedad en el momento de la formalización 
     * 
     * @param idTempNovedad
     * @param userDTO
     * @return
     */
    @GET
    @Path("consultarExcepcionNovedadPila")
	public List<ExcepcionNovedadPilaModeloDTO> consultarExcepcionNovedadPila(
			@NotNull @QueryParam("idTempNovedad") Long idTempNovedad, 
			@Context UserDTO userDTO);
    
    /**
     * Método que actualiza el estado de la bandeja transitoria de planillas fallidas
     * 
     * @param indicePlanilla
     * @param userDTO
     * @return true si realizó una actualización
     */
    @POST
    @Path("actualizarEstadoBandejaTransitoriaGestion")
	public Boolean actualizarEstadoBandejaTransitoriaGestion(
			@NotNull @QueryParam("indicePlanilla") Long indicePlanilla, 
			@Context UserDTO userDTO);
    
    /**
     * Retorna el registro general de una planilla
     * @param indicePlanilla
     * @param userDTO
     * @return
     */
    @GET
    @Path("consultarRegistroGeneralxRegistroControl")
    public RegistroGeneral consultarRegistroGeneralxRegistroControl(
			@NotNull @QueryParam("indicePlanilla") Long indicePlanilla, 
			@Context UserDTO userDTO);
    
    /**
     * Método que actualiza el estado en proceso de los aportes de una planilla
     * 
     * @param indicePlanilla
     * @param userDTO
     * @return
     */
    @POST
    @Path("actualizarEstadoEnProcesoAportes")
	public Boolean actualizarEstadoEnProcesoAportes(
			@NotNull @QueryParam("indicePlanilla") Long indicePlanilla, 
			@Context UserDTO userDTO);
}
