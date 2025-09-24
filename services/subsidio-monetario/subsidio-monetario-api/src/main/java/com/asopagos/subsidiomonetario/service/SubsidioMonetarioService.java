package com.asopagos.subsidiomonetario.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.EmpleadorDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.RolAfiliadoDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.AplicacionValidacionSubsidioPersonaModeloDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionEntidadDescuentoLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoHistoricoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.TrazabilidadSubsidioEspecificoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoValidacionLiquidacionEspecificaEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.CargueBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.CondicionesEspecialesLiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaDescuentosSubsidioTrabajadorGrupoDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaValidacionesLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.CuotaMonetariaIVRDTO;
import com.asopagos.subsidiomonetario.dto.DatosComunicadoDTO;
import com.asopagos.subsidiomonetario.dto.DetalleCantidadEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.DetalleLiquidacionSubsidioEspecificoFallecimientoDTO;
import com.asopagos.subsidiomonetario.dto.DetalleResultadoPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.EspecieLiquidacionManualDTO;
import com.asopagos.subsidiomonetario.dto.ExportarInconsistenciasDTO;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.LiquidacionEspecificaDTO;
import com.asopagos.subsidiomonetario.dto.ParametrizacionCondicionesSubsidioCajaDTO;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoHistoricoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.TemporalAsignacionDerechoDTO;
import com.asopagos.subsidiomonetario.dto.ValorPeriodoDTO;
import com.asopagos.subsidiomonetario.dto.VerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.modelo.dto.CuentaCCFModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.ParametrizacionLiquidacionSubsidioModeloDTO;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;

/**
 * <b>Descripcion:</b> Clase que define los servicios para la manipulación de la liquidación masiva<br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 y 436<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Path("subsidioMonetario")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SubsidioMonetarioService {

    /**
     * Metodo que ejecuta una liquidacion masiva
     * @param liquidacion
     * @author rarboleda
     * @return Boolean Estado de exito o fracaso en la operacion
     */
    @POST
    @Path("ejecutarLiquidacionMasiva")
    public RespuestaGenericaDTO ejecutarLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion,
            @QueryParam("periodo") Long periodo);

    /**
     * Metodo que guarda una liquidacion masiva para su posterior ejecución
     * @param liquidacion
     * @author rarboleda
     * @return Boolean Estado de exito o fracaso en la operacion
     */
    @POST
    @Path("persistirLiquidacionMasiva") 
    public RespuestaGenericaDTO persistirLiquidacionMasiva(SolicitudLiquidacionSubsidioModeloDTO liquidacion,
            @QueryParam("periodo") Long periodo, @Context UserDTO userDTO);

    /**
     * Metodo para cancelar una liquidacion masiva en proceso
     * @author rarboleda
     * @return Boolean Estado de exito o fracaso en la operacion
     */
    @POST
    @Path("cancelarLiquidacionMasiva")
    public Boolean cancelarLiquidacionMasiva();

    /**
     * Método que permite consultar los resultados de una liquidación masiva
     * 
     * @param codigoProceso
     *        código del proceso de liquidación masiva
     * @author rlopez
     * @return información de la liquidación masiva
     */
    @GET
    @Path("/consultarResultadoLiquidacionMasiva/{numeroSolicitud}")
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionMasiva(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas subsidios liquidados desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de los trabajadores con una cantidad de subsidios liquidados por encima del umbral
     */
    @GET
    @Path("/trabajadores/subsidiosLiquidados/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosLiquidadosPorTrabajadores(@PathParam("numeroSolicitud") String codigoProceso);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas monto de subsidios liquidados desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @return información detallada de los trabajadores con un monto de subsidios liquidados por encima del umbral
     */
    @GET
    @Path("/trabajadores/montoSubsidiosLiquidados/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO montoSubsidiosLiquidadosPorTrabajadores(
            @PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas subsidios con invalidez
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de los trabajadores con una cantidad de subsidios de invalidez por encima del umbral
     */
    @GET
    @Path("/trabajadores/subsidiosInvalidez/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosInvalidezPorTrabajadores(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consutlar el detalle de las empresas con mas periodos retroactivos en el mes desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @return información detallada de las empresas con mas periodos retroactivos por encima del umbral
     */
    @GET
    @Path("/empresas/periodosRetroactivosMes/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO periodosRetroactivosMes(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consultar el detalle de las empresas acogidas al decreto 1429 que se encuentran en el año 1 y 2 del beneficio con
     * subsidio mayor a 0
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de las empresas con las condiciones establecidas
     */
    @GET
    @Path("/empresas/beneficio1429Anio1y2/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO empresasBeneficio1429(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consultar el detalle de personas con monto descontado
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de las personas con montos descontados
     */
    @GET
    @Path("/trabajadores/descuentos/{numeroSolicitud}")
    public DetalleCantidadEmpresaTrabajadorDTO trabajadoresDescuentos(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Método que permite consultar el histórico de una liquidación masiva, dados los criterios de busqueda
     * @param periodoRegular
     *        periodo asociado al proceso de liquidación
     * @param fechaInicial
     *        límite inferior para el rango de fechas de los procesos de liquidación
     * @param fechaFin
     *        límite superior para el rango de fechas de los procesos de liquidación
     * @param numeroOperacion
     *        número de operación correspondiente al proceso de liquidación
     * @author rlopez
     * @return DTO con la información obtenida
     */
    @GET
    @Path("/consultarHistoricoLiquidacionMasiva")
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionMasiva(
            @QueryParam("periodoRegular") Long periodoRegular, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("numeroOperacion") String numeroOperacion, @Context UriInfo uri,
            @Context HttpServletResponse response);

    /**
     * Método que permite realizar la consulta de liquidaciones históricas por los datos de liquidación
     * 
     * @param tipoLiquidacion
     *        tipo de liquidación especifica sobre la que se desea consultar el histórico (reconocimiento o ajuste de cuota)
     * @param numeroOperacion
     *        número de operación relacionado con el proceso de liquidación
     * @param fechaInicio
     *        límite inferior para el rango de fechas de los procesos de liquidación
     * @param fechaFin
     *        límite superior para el rango de fechas de los procesos de liquidación
     * @param uri
     *        parámetros de la consulta sobre el recurso
     * @param response
     *        respuesta
     * @author rlopez
     * @return DTO´s con la información de las liquidaciones históricas
     */
    @GET
    @Path("/consultarHistoricoLiquidacionEspecifica")
    public List<ResultadoHistoricoLiquidacionMasivaDTO> consultarHistoricoLiquidacionEspecifica(
            @QueryParam("tipoLiquidacion") TipoProcesoLiquidacionEnum tipoLiquidacion,
            @QueryParam("tipoLiquidacionEspecifica") TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica,
            @QueryParam("periodoRegular") Long periodoRegular, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("numeroOperacion") String numeroOperacion, @Context UriInfo uri,
            @Context HttpServletResponse response);

    /**
     * Método que permite realizar la consulta de los resultados para una liquidación de tipo especifica
     * @param numeroSolicitud
     *        número de la solicitud
     * @author rlopez
     * @return DTO con la información de la liquidación
     */
    @GET
    @Path("/consultarResultadoLiquidacionEspecifica/{numeroSolicitud}")
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionEspecifica(@PathParam("numeroSolicitud") String numeroSolicitud);

    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de subsidio
     * @return DTO con informacion acerca del proceso actual
     * @author rarboleda
     */
    @GET
    @Path("inicializarPantallaSolicitudLiquidacion")
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacion(@Context UserDTO userDTO);
    
    /**
     * Metodo para inicializar la pantalla de solicitud de liquidacion de subsidio masiva cerradas
     * @param userDTO
     * @return
     */
    /*@GET
    @Path("inicializarPantallaSolicitudLiquidacionCerrada")
    public IniciarSolicitudLiquidacionMasivaDTO inicializarPantallaSolicitudLiquidacionCerrada(@Context UserDTO userDTO);*/
    
    /**
     * Método que permite consultar una solicitud de liquidación dado el número de radicado
     * @param numeroRadicado
     *        valor del número de radicado
     * @author rlopez
     * @return identificador de la solicitud de liquidacion
     */
    @GET
    @Path("/consultarIdSolicitud/{numeroRadicado}")
    public Long consultarIdSolicitud(@PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Método que se encarga de registrar la información para una aprobación de susbsidio monetario en primer nivel
     * @param numeroSolicitud
     *        número de la solicitud de liquidación
     * @param userDTO
     *        usuario que realiza la aprobación
     * @return identificador de la solicitud
     */
    @POST
    @Path("/registroInformacion/aprobacionPrimerNivel/{numeroSolicitud}")
    public Long aprobarLiquidacionMasivaPrimerNivel(@PathParam("numeroSolicitud") String numeroSolicitud, @Context UserDTO userDTO);

    /**
     * Método que se encarga de registrar la información para un rechazo de subsidio monetario en primer nivel
     * @param numeroSolicitud
     *        número de solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información a registrar
     * @param userDTO
     *        usuario que realiza el rechazo
     * @return identificador de la solicitud
     */
    @POST
    @Path("/registroInformacion/rechazoPrimerNivel/{numeroSolicitud}")
    public Long rechazarLiquidacionMasivaPrimerNivel(@PathParam("numeroSolicitud") String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, @Context UserDTO userDTO);

    /**
     * Método que se encarga de registrar la información para una aprobación de susbsidio monetario en segundo nivel
     * @param numeroSolicitud
     *        número de solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información a registrar
     * @param userDTO
     *        usuario que realiza la aprobación
     * @return DTO con la información de la solicitud
     */
    @POST
    @Path("/registroInformacion/aprobacionSegundoNivel/{numeroSolicitud}")
    public SolicitudLiquidacionSubsidioModeloDTO aprobarLiquidacionMasivaSegundoNivel(@PathParam("numeroSolicitud") String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, @Context UserDTO userDTO);

    /**
     * Método que se encarga de registar la información para un rechazo de subsidio monetario en segundo nivel
     * @param numeroSolicitud
     *        número de la solicitud
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información a registrar
     * @param userDTO
     *        usuario que realiza el rechazo
     * @return DTO con la información de la solicitud de liquidación
     */
    @POST
    @Path("/registroInformacion/rechazoSegundoNivel/{numeroSolicitud}")
    public SolicitudLiquidacionSubsidioModeloDTO rechazarLiquidacionMasivaSegundoNivel(@PathParam("numeroSolicitud") String numeroSolicitud,
            AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO, @QueryParam("isAsync")Boolean isAsync, @Context UserDTO userDTO);

    /**
     * 
     */
    @POST
    @Path("actualizarInstanciaSolicitudGlobal")
    public void actualizarInstanciaSolicitudGlobal(@QueryParam("idInstancia") Long idInstancia,
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * <b>Descripcion</b>Metodo que se encarga de actualizar el estado de la
     * socilictud de liquidacion
     *
     * @param idSolicitudLiquidacion,
     *        identificador que permite buscar la solicitud
     * @param estado
     *        Referencia al estado al cual se va actualizar la solicitud de
     *        liquidacion
     * @author rlopez
     */
    @PUT
    @Path("/solicitudLiquidacion/estado/{idSolicitudLiquidacion}")
    public void actualizarEstadoSolicitudLiquidacion(@PathParam("idSolicitudLiquidacion") Long idSolicitudLiquidacion,
            EstadoProcesoLiquidacionEnum estado);

    /**
     * Método que permite obtener la información de una solicitud de liquidación dado su número de radicado
     * @param numeroRadicado
     *        valor del número de radicado
     * @return DTO con la información de la solicitud de liquidación
     */
    @GET
    @Path("/solicitudLiquidacion/consultar/{numeroRadicado}")
    public SolicitudLiquidacionSubsidioModeloDTO consultarSolicitudLiquidacion(@NotNull @PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Método que permite obtener la información de una solicitud de liquidación dado su número de radicado
     * @param numeroRadicado
     *        valor del número de radicado
     * @return Estado de la solicitud de liquidación
     */
    @GET
    @Path("/solicitudLiquidacion/consultarEstado/{numeroRadicado}")
    public EstadoProcesoLiquidacionEnum consultarEstadoSolicitudLiquidacion(@NotNull @PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Método que se encarga de actualizar el estado de una solicitud de liquidación a "EN PROCESO", cuando el supervisor selecciona la
     * tarea de su bandeja
     */
    @PUT
    @Path("/solicitudLiquidacion/actualizarEstado/{numeroRadicado}")
    public void actualizarEstadoSolicitudLiquidacionEnProceso(@PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Metodo que se ejecuta al seleccionar una persona luego de haberla buscado (HU-317-144)
     * @param persona
     * @return Respuesta generica con la informacion relacionada
     * @author rarboleda
     */
    @GET
    @Path("/seleccionarPersona/consultarEstado/{personaId}")
    public RespuestaGenericaDTO seleccionarPersona(@NotNull @PathParam("personaId") Long personaId);

    /**
     * Metodo que se ejecuta al seleccionar una empresa luego de haberla buscado (HU-317-144)
     * @param empleador
     * @return Respuesta generica con la informacion relacionada
     * @author rarboleda
     */
    @GET
    @Path("/seleccionarEmpleador/consultarEstado/{empleadorId}")
    public RespuestaGenericaDTO seleccionarEmpleador(@NotNull @PathParam("empleadorId") Long empleadorId);

    /**
     * Metodo para consultar los beneficiarios que posee un empleado dependiente
     * @param idPersona
     *        identificador unico de la persona
     * @return Lista con los beneficarios
     * @author rarboleda
     */
    @GET
    @Path("/buscarBeneficiariosAfiliado/{idPersona}")
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliado(@NotNull @PathParam("idPersona") Long idPersona);

    /**
     * @param numeroRadicado
     *        Numero del radicado para la solicitud actual
     * @param periodo
     *        Periodo de la solicitud actual
     */
    @POST
    @Path("/iniciarLiquidacionMasiva")
    public void iniciarLiquidacionMasiva(@QueryParam("numeroRadicado") String numeroRadicado, @QueryParam("periodo") Long periodo);

    /**
     * Metodo para actualizar un periodo en la entidad periodo
     */
    @POST
    @Path("/generarNuevoPeriodo")
    public void generarNuevoPeriodo();
    
    /**
     * Metodo para actualizar un periodo en la entidad periodo
     */
    @POST
    @Path("/generarNuevoPeriodoL")
    public void generarNuevoPeriodoL(@QueryParam("periodoL") Long periodoL);

    /**
     * Ejecutar orquestador stagin
     */
    @POST
    @Path("/ejecutarOrquestadorStagin")
    public void ejecutarOrquestadorStagin(@QueryParam("fechaActual") Long fechaActual);

    /**
     * Metodo para ejecutar el Orquestador de Staging entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     */
    @POST
    @Path("/ejecutarOrquestadorStaginIntervaloFechas")
    public void ejecutarOrquestadorStaginIntervaloFechas(@NotNull @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin);

    /**
     * Metodo para eliminar una liquidacion a traves del llamado SP.
     * @param numeroRadicado
     */
    @POST
    @Path("/eliminarLiquidacionSP")
    public Boolean eliminarLiquidacionSP(@NotNull @QueryParam("numeroRadicado") String numeroRadicado);

    /**
     * Metodo para actualizar el estado de una liquidacion a partir del número de radicado
     * @param numRadicado
     */
    @POST
    @Path("/actualizarEstadoSolicitudLiquidacionXNumRadicado")
    public void actualizarEstadoSolicitudLiquidacionXNumRadicado(@NotNull @QueryParam("numeroRadicado") String numeroRadicado);

    @GET
    @Path("/temporalDerechoBeneficiarios/{numeroRadicacion}")
    public List<TemporalAsignacionDerechoDTO> temporalDerechoBeneficiarios(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Método que permite generar el archivo de liquidación dado el número de radicado asociado a la solicitud
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param tipoIdentificacion 
     *          el tipo de identificación del empleador
     * @param numeroIdentificacion
     *          el número de identificación del empleador
     * @return DTO con la información del archivo
     * @author rlopez
     * @author jocampo modificación
     */
    @GET
    @Path("/generarArchivoResultadoLiquidacion/{numeroRadicacion}")
    public InformacionArchivoDTO generarArchivoResultadoLiquidacion(
            @NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Método que permite generar el archivo de personas sin derecho en una
     * liquidación
     * 
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param tipoIdentificacion 
     *          el tipo de identificación del empleador
     * @param numeroIdentificacion
     *          el número de identificación del empleador
     * @return DTO con la información del archivo
     * @author rlopez
     * @author jocampo modificación
     */
    @GET
    @Path("/generarArchivoResultadoPersonasSinDerecho/{numeroRadicacion}")
    public InformacionArchivoDTO generarArchivoResultadoPersonasSinDerecho(
            @PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Metodo encargado de guardar una liquidacion especifica
     * @param liquidacionEspecifica
     * @return Respuesta generica
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarLiquidacionEspecifica")
    public RespuestaGenericaDTO ejecutarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica);

    /**
     * Consulta el valor de la cuota para un solo periodo
     * @param periodo
     * @return Valor de la cuota
     * @author rarboleda
     */
    @GET
    @Path("/consultarValorCuotaPeriodo")
    public BigDecimal consultarValorCuotaPeriodo(@NotNull @QueryParam("periodo") Long periodo);

    /**
     * Método que permite obtener la información referente a los identificadores de los archivos de una liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de los identificadores
     * @author rlopez
     */
    @GET
    @Path("/archivosLiquidacion/consultar/{numeroRadicacion}")
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);
    @GET
    @Path("/archivosLiquidacion/consultarPorId/{idArchivoLiquidacion}")
    public ArchivoLiquidacionSubsidioModeloDTO consultarArchivosLiquidacionPorId(@PathParam("idArchivoLiquidacion") Long idArchivoLiquidacion);

    /**
     * Método que se encarga de gestionar los archivos relacionados con la liquidación
     * @param archivoLiquidacionDTO
     *        DTO con la información de los archivos
     * @return identificador del registro asociado a los archivos
     * @author rlopez
     */
    @POST
    @Path("/archivosLiquidacion/gestionar")
    public Long gestionarArchivosLiquidacion(@NotNull @Valid ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO);

    /**
     * Consulta el valor de la cuota para una lista de periodos
     * @param periodos
     * @return Lista con los valores de periodo
     * @author rarboleda
     */
    @POST
    @Path("/consultarValorCuotaPeriodos")
    public List<ValorPeriodoDTO> consultarValorCuotaPeriodos(List<Long> periodos);

    /**
     * Método que permite realizar la consulta del periodo regular asociado a una liquidación
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return periodo regular
     * @author rlopez
     */
    @GET
    @Path("/liquidacion/consultarPeriodoRegular/{numeroRadicacion}")
    public Date consultarPeriodoRegularLiquidacionPorRadicado(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Metodo para verificar si hay una liquidacion en proceso, de cualquier tipo
     * @return
     * @author rarboleda
     */
    @GET
    @Path("/verificarLiquidacionEnProceso")
    public Boolean verificarLiquidacionEnProceso();

    /**
     * Metodo que persiste una liquidacion especifica
     * @param liquidacionEspecifica
     * @return RespuestaGenericaDTO DTO con la respuesta asociada al proceso
     * @author rarboleda
     */
    @POST
    @Path("/guardarLiquidacionEspecifica")
    public RespuestaGenericaDTO guardarLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica, @Context UserDTO userDTO);

    /**
     * Consulta el valor de la cuota para una lista de periodos
     * @param periodos
     * @return Lista con los valores de periodo
     * @author rarboleda
     */
    @POST
    @Path("/consultarValorCuotaAnualYAgrariaPeriodos")
    public List<ValorPeriodoDTO> consultarValorCuotaAnualYAgrariaPeriodos(List<Long> periodos);

    /**
     * Método que se encarga de enviar la información de una liquidación a pagos
     * @param nombreUsuario
     *        usuario que ejecuta la aprobación en segundo nivel
     * @param numeroRadicado
     *        valor del número de radicado
     * @author rlopez
     */
    @GET
    @Path("/enviarResultadoLiquidacionAPagos")
    public void enviarResultadoLiquidacionAPagos(@QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("numeroRadicado") String numeroRadicado);

    /**
     * Metodo para verificar la existencia de un periodo en BD
     * @param periodo
     * @return
     */
    @POST
    @Path("/verificarExistenciaPeriodo")
    public Boolean verificarExistenciaPeriodo(List<ValorPeriodoDTO> periodos);

    /**
     * Metodo para guardar los periodos de liquidacion asociados a la liquidacion especifica
     * @param periodos
     * @param idSolicitudLiquidacion
     * @return
     * @author rarboleda
     */
    @POST
    @Path("guardarPeriodosLiquidacion")
    public Boolean guardarPeriodosLiquidacion(List<ValorPeriodoDTO> periodos,
            @NotNull @QueryParam("idSolicitudLiquidacion") Long idSolicitudLiquidacion,
            @QueryParam("tipoAjuste") TipoLiquidacionEspecificaEnum tipoAjuste);

    /**
     * Metodo que permite obtener los factores de discapacidad para los periodos dados
     * @param periodos
     *        para los cuales se desea obtener el factor de discapacidad
     * @return Lista con los factores de discapacidad asociados al periodo
     */
    @POST
    @Path("/consultarFactorCuotaDiscapacidadPeriodos")
    public List<ValorPeriodoDTO> consultarFactorCuotaDiscapacidadPeriodos(List<Long> periodos);

    /**
     * Metodo que permite obtener el valor de la cuota agraria para los periodos dados
     * @param periodos
     *        para los cuales se desea obtener el valor de la cuota agraria
     * @return Lista con los valores de las cuotas agrarias para el periodo
     * @author rarboleda
     */
    @POST
    @Path("/consultarValorCuotaAgrariaPeriodos")
    public List<ValorPeriodoDTO> consultarValorCuotaAgrariaPeriodos(List<Long> periodos);

    /**
     * Metodo para persistir las personas/empresas asociadas a una liquidacion especifica
     * (HU's 143-144-146-148)
     * @param liquidacionEspecifica
     *        Informacion de la liquidacion especifica que viene de pantallas
     * @return Boolean indicando si el proceso fue satisfactorio
     * @author rarboleda
     */
    @POST
    @Path("/guardarPersonasLiquidacionEspecifica")
    public Boolean guardarPersonasLiquidacionEspecifica(LiquidacionEspecificaDTO liquidacionEspecifica,
            @QueryParam("idSolicitudLiquidacion") Long idSolicitudLiquidacion);

    /**
     * Metodo que permite ejecutar una liquidacion especifica
     * HU's 143-144-146-148
     * @param tipoLiquidacionEspecifica
     *        Tipo de liquidacion especifica
     * @param numeroRadicado
     *        Número de radicado asociado
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarSPLiquidacionEspecifica")
    public void ejecutarSPLiquidacionEspecifica(
            @NotNull @QueryParam("tipoLiquidacion") TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica,
            @NotNull @QueryParam("numeroRadicado") String numeroRadicado);

    /**
     * Método que permite actualizar la solicitud de liquidación, registrando la fecha de dispersión
     * @param idSolicitudLiquidacion
     *        valor del identificador de la solicitud de liquidación
     * @author rlopez
     */
    @PUT
    @Path("/actualizarFechaDispersion")
    public void actualizarFechaDispersion(@QueryParam("idSolicitudLiquidacion") Long idSolicitudLiquidacion);

    /**
     * Método que permite actualizar el estado de derecho para los subsidios asignados en un proceso de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    @PUT
    @Path("/actualizarEstadoDerechoLiquidacion/{numeroRadicacion}")
    public void actualizarEstadoDerechoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Metodo encargado de almacenar las condiciones especiales asociadas a una liquidacion especifica por
     * reconocimiento
     * @param condiciones
     *        Condiciones especiales
     * @param idSolicitudLiquidacion
     *        Identificador de la solicitud de liquidacion
     */
    @POST
    @Path("/guardarCondicionesEspecialesReconocimiento")
    public void guardarCondicionesEspecialesReconocimiento(CondicionesEspecialesLiquidacionEspecificaDTO condiciones,
            @NotNull @QueryParam("idSolicitudLiquidacion") Long idSolicitudLiquidacion);

    /**
     * Metodo para llamar al Stored Procedure de subsidio por reconocimiento
     * HU's 317-226, 317-245
     * @param numeroRadicado
     *        Número de radicado asociado
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarSPLiquidacionReconocimiento")
    public void ejecutarSPLiquidacionReconocimiento(@NotNull @QueryParam("numeroRadicado") String numeroRadicado);
    
    /**
     * Metodo para llamar al Stored Procedure de subsidio por reconocimiento
     * HU's 317-226, 317-245
     * @param numeroRadicado
     *        Número de radicado asociado
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarSPLiquidacionReconocimientoSincrono")
    public void ejecutarSPLiquidacionReconocimientoSincrono(@NotNull @QueryParam("numeroRadicado") String numeroRadicado);
    
    /**
     * Metodo para llamar al Stored Procedure de subsidio por reconocimiento
     * HU's 317-226, 317-245
     * @param numeroRadicado
     *        Número de radicado asociado
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarSPGestionarColaEjecucionLiquidacion")
    public List<String> ejecutarSPGestionarColaEjecucionLiquidacion();

    /**
     * Método que permite consultar el resultado de una liquidación específica por fallecimiento
     * 
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return DTO con la información de la liquidación por fallecimiento
     * 
     * @author rlopez
     */
    @GET
    @Path("/consultarResultadoLiquidacionFallecimiento/{numeroSolicitud}")
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimiento(
            @PathParam("numeroSolicitud") String numeroRadicacion);
    
    /**
     * Método que permite consultar el resultado de una liquidación específica por fallecimiento
     * 
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return DTO con la información de la liquidación por fallecimiento
     * 
     * @author rlopez
     */
    @GET
    @Path("/consultarResultadoLiquidacionFallecimientoConfirmados/{numeroSolicitud}")
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimientoConfirmados(
            @PathParam("numeroSolicitud") String numeroRadicacion);

    /**
     * Método que se encarga de realizar las acciones correspondientes a la confirmación de un beneficiario dentro del proceso de
     * liquidación por fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionBeneficiario
     *        identificador de condición de beneficiario
     * @author rlopez
     */
    @PUT
    @Path("/liquidacionFallecimiento/resultados/confirmarBeneficiario/{numeroRadicacion}")
    public void confirmarBeneficiarioLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("idCondicionBeneficiario") Long idCondicionBeneficiario);

    /**
     * Método que se encarga de realizar las acciones correspondientes a la confirmación de un afiliado dentro del proceso de liquidación
     * por fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionAfiliado
     *        identificador de condición de afiliado
     * @author rlopez
     */
    @PUT
    @Path("/liquidacionFallecimiento/resultados/confirmarAfiliado/{numeroRadicacion}")
    public void confirmarAfiliadoLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("idCondicionAfiliado") Long idCondicionAfiliado);

    /**
     * Método que permite obtener el detalle del beneficiario en una liquidación de fallecimeinto
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionBeneficiario
     *        identificador de la condición del beneficiario
     * @return DTO con la información del beneficiario
     * @author rlopez
     */
    @GET
    @Path("/liquidacionFallecimiento/consultar/detalleBeneficiario/{numeroRadicacion}")
    public DetalleLiquidacionBeneficiarioFallecimientoDTO consultarDetalleBeneficiarioLiquidacionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @QueryParam("idCondicionBeneficiario") Long idCondicionBeneficiario);

    /**
     * Metodo que consulta la proyeccion de cuotas de fallecimiento segun el numero de radicado
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/consultar/proyeccionCuotasFallecimiento/medioDePago/{numeroRadicacion}/{identificadorCondicion}")
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("identificadorCondicion") Long identificadorCondicion);
    
    /**
     * Metodo que realiza las validaciones asociadas a la HU-317-503 para el subsidio de fallecimiento trabajador
     */
    @POST
    @Path("seleccionarPersonaSubsidioFallecimientoTrabajador")
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajador(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoLiquidacion") TipoLiquidacionEspecificaEnum tipoLiquidacion);

    /**
     * Metodo para llamar al Stored Procedure de subsidio por fallecimiento
     * HU's 317-503, 317-506
     * @param numeroRadicado
     *        Número de radicado asociado
     * @param periodo
     *        Periodo asociado
     * @param beneficiarioFallecido
     *        false para trabajador, true para beneficiario
     * @author rarboleda
     */
    @POST
    @Path("/ejecutarSPLiquidacionFallecimiento")
    public void ejecutarSPLiquidacionFallecimiento(@NotNull @QueryParam("numeroRadicado") String numeroRadicado,
            @NotNull @QueryParam("periodo") Long periodo, @NotNull @QueryParam("beneficiarioFallecido") Boolean beneficiarioFallecido);
    
    /**
     * Método que permite obtener la información de una persona dentro de un proceso de liquidación a partir de su identificador
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param identificadoresCondiciones
     *        lista de identificadores de condiciones
     * @return Información de las condiciones de las personas
     * @author rlopez
     */
    @GET
    @Path("/consultar/condicionesPersonas/{numeroRadicacion}")
    public Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("indentificadoresCondiciones") List<Long> identificadoresCondiciones);

    /**
     * Método que permite obtener la información de las condiciones de las entidades de descuento en un proceso de liquidación a partir de
     * su identificador
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param identificadoresCondiciones
     *        lista de identificadores de condiciones
     * @return Información de las condiciones de las personas
     * @author rlopez
     */
    @GET
    @Path("/consultar/condicionesEntidadesDescuento/{numeroRadicacion}")
    public Map<Long, CondicionEntidadDescuentoLiquidacionDTO> consultarCondicionesEntidadesDescuento(
            @PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("indentificadoresCondiciones") List<Long> identificadoresCondiciones);

    /**
     * Método que permite obtener la información de desembolso para una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de desembolso
     * @author rlopez
     */
    @GET
    @Path("/liquidacionFallecimiento/consultar/desembolsoSubsidio/{numeroRadicacion}")
    public ResultadoLiquidacionFallecimientoDTO consultarDesembolsoSubsidioLiquidacionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Método que se encarga de actualizar la solicitud de liquidación con base en los parámetros definidos para el desembolso de subsidio
     * en una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param consideracionAportes
     *        indicador de consideración de aportes
     * @param tipoDesembolso
     *        modo en que se realizará el desembolso
     * @author rlopez
     */
    @PUT
    @Path("/liquidacionFallecimiento/actualizar/desembolsoSubsidio/{numeroRadicacion}")
    public void actualizarDesembolsoSubsidioLiquidacionFallecimiento(@PathParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("consideracionAportes") Boolean consideracionAportes,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum tipoDesembolso);

    /**
     * Método que permite obtener la información histórica de las liquidaciones específicas de fallecimiento
     * @param periodoRegular
     *        Periodo regular asociado a la liquidación de fallecimiento
     * @param fechaInicio
     *        Filtro de fecha inicial para la consulta de históricos
     * @param fechaFin
     *        Filtro de fecha final para la consulta de históricos
     * @param tipoIdentificacion
     *        Tipo de identificación del fallecido
     * @param numeroIdentificacion
     *        Número de identificación del fallecido
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param uri
     * @param response
     * @return Lista de DTO´s con la información histórica
     * @author rlopez
     */
    @GET
    @Path("/consultarHistoricoLiquidacionFallecimiento")
    public List<ResultadoHistoricoLiquidacionFallecimientoDTO> consultarHistoricoLiquidacionFallecimiento(
            @NotNull @QueryParam("periodoRegular") Long periodoRegular, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("tipoIdentificaccion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("numeroOperacion") String numeroRadicacion,
            @Context UriInfo uri, @Context HttpServletResponse response);
    
    /**
     * Método para consultar las parametrizaciones de una liquidacion especifica, incluidas ajuste, recocimiento y defuncion 
     * @param numeroRadicado
     *        Número de radicado asociado  
     * @return DTO con la parametrizacion asociada   
     */
    @GET
    @Path("/consultarParametrizacionLiqEspecifica")
    public LiquidacionEspecificaDTO consultarParametrizacionLiqEspecifica(@QueryParam("numeroRadicado") String numeroRadicado);
    
    /**
     * Metodo para verificar si hay una liquidacion en proceso
     * @return DTO con informacion acerca del proceso actual
     * @author rarboleda
     */
    @GET
    @Path("verificarLiquidacionEnProcesoInfoCompleta")
    public IniciarSolicitudLiquidacionMasivaDTO verificarLiquidacionEnProcesoInfoCompleta(@Context UserDTO userDTO);
    
    /**
     * Método que se encarga de enviar la información de una liquidación de fallecimiento a pagos
     * @param nombreUsuario
     *        Usuario que realiza las acciones sobre la liquidación de fallecimiento
     * @param numeroRadicado
     *        Valor del número de radicado
     * @param modoDesembolso
     *        Modo en el que realiza el desembolso de subsidio
     * @author rlopez
     */
    @GET
    @Path("/enviarResultadoLiquidacionAPagosFallecimiento")
    public void enviarResultadoLiquidacionAPagosFallecimiento(@QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("numeroRadicado") String numeroRadicado, @QueryParam("tipoDesembolso") ModoDesembolsoEnum modoDesembolso);
    
    /**
     * Método que permite obtener la información de la validación fallida para una persona (trabajador o benefiario) en el proceso de
     * fallecimiento
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de condición persona
     * @return Validación fallida
     * @author rlopez
     */
    @GET
    @Path("/consultar/validacionFallida/personaFallecimiento/{numeroRadicacion}/{condicionPersona}")
    public ConjuntoValidacionSubsidioEnum consultarValidacionFallidaPersonaFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("condicionPersona") Long condicionPersona);

    /**
     * Método que permite obtener el identificador de la persona en el modelo core a partir de su identificador de condición en un proceso
     * de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de condición persona
     * @return Identificador de la persona en core
     * @author rlopez
     */
    @GET
    @Path("/consultar/identificadorPersona/core/{numeroRadicacion}/{condicionPersona}")
    public Long consultarIdentificadorPersonaCore(@PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("condicionPersona") Long condicionPersona);

    /**
     * Método que permite obtener el identificador de PersonaLiquidacionEspecifica para el trabajador relacionado en una liquidación de
     * fallecimiento
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param idPersona
     *        Identificador de la persona
     * @return Identificador de PersonaLiquidacionEspecifica
     * @author rlopez
     */
    @GET
    @Path("/seleccionar/personaLiquidacionEspecifica/trabajador/liquidacionFallecimiento/{numeroRadicacion}/{idPersona}")
    public Long seleccionarPersonaLiquidacionEspecificaTrabajadorLiquidacionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("idPersona") Long idPersona);

    /**
     * Método que permite obtener el identificador de PersonaLiquidacionEspecifica para el beneficiario relacionado en una liquidación de
     * fallecimiento
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param idPersona
     *        Identificador de la persona
     * @return Identificador de PersonaLiquidacionEspecifica
     * @author rlopez
     */
    @GET
    @Path("/seleccionar/personaLiquidacionEspecifica/beneficiario/liquidacionFallecimiento/{numeroRadicacion}/{idPersona}")
    public Long seleccionarPersonaLiquidacionEspecificaBeneficiarioLiquidacionFallecimiento(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("idPersona") Long idPersona);

    /**
     * Método que se encarga de consultar las validaciones existentes para el tipo de proceso parametrizado
     * @param tipoProceso
     *        Tipo de proceso
     * @return Lista de validaciones para el tipo de proceso
     * @author rlopez
     */
    @GET
    @Path("/consultar/validacionesTipoProceso")
    public List<ConjuntoValidacionSubsidioEnum> consultarValidacionesTipoProceso(
            @QueryParam("tipoProceso") TipoValidacionLiquidacionEspecificaEnum tipoProceso);

    /**
     * Método que se encarga de consultar el identificador correspondiente al conjunto validación parametrizado
     * @param validacion
     *        Valor de la validación
     * @return Identificador del conjunto validación
     * @author rlopez
     */
    @GET
    @Path("/consultar/identificador/conjuntoValidacion")
    public Long consultarIdentificadorConjuntoValidacion(@QueryParam("validacion") ConjuntoValidacionSubsidioEnum validacion);

    /**
     * Método que se encarga de registrar una aplicación validación subsidio
     * @param aplicacionValidacionDTO
     *        DTO con la información de la AplicacionValidacionSubsidio
     * @return Identificador de la AplicacionValidacionSubsidio registrada
     * @author rlopez
     */
    @POST
    @Path("/registrar/aplicacionValidacionSubsidio")
    public Long registrarAplicacionValidacionSubsidio(@NotNull AplicacionValidacionSubsidioModeloDTO aplicacionValidacionDTO);

    /**
     * Método que se encarga de registrar una aplicación validación subsidio persona
     * @param aplicacionValidacionPersonaDTO
     *        DTO con la información de la aplicación validación persona
     * @return Identificador de la AplicacionValidacionPersonaDTO
     * @author rlopez
     */
    @POST
    @Path("/registrar/aplicacionValidacionSubsidioPersona")
    public Long registrarAplicacionValidacionSubsidioPersona(
            @NotNull AplicacionValidacionSubsidioPersonaModeloDTO aplicacionValidacionPersonaDTO);


    /*
    @POST
    @Path("/iniciarProcesoGeneracionArchivoSinDerecho/{numeroRadicacion}")
    public Map<String, String> iniciarProcesoGeneracionArchivoSinDerecho(
        @PathParam("numeroRadicacion") String numeroRadicacion);
         */
        
    @POST
    @Path("/generarArchivoResultadoPersonasSinDerechoAsync/{idArchivoLiquidacion}/{numeroRadicacion}")
    public void generarArchivoResultadoPersonasSinDerechoAsync(
        @PathParam("idArchivoLiquidacion") Long idArchivoLiquidacion,
        @PathParam("numeroRadicacion") String numeroRadicacion);
        
    
    /**
     * Método que permite realizar la ejecución del SP de fallecimiento tras la gestión sobre una persona en los resultados de la
     * liquidación
     * @param numeroRadicado
     *        Valor del número de radicación
     * @param periodo
     *        Periodo relacionado a la liquidación
     * @param beneficiarioFallecido
     *        Indicador de fallecimiento de beneficiario
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona gestionada
     * @param numeroIdentificacion
     *        Número de identificación de la persona gestionada
     * @author rlopez
     */
    @POST
    @Path("/ejecutarSPLiquidacionFallecimientoGestionPersona")
    public void ejecutarSPLiquidacionFallecimientoGestionPersona(@QueryParam("numeroRadicacion") String numeroRadicado,
            @QueryParam("periodo") Long periodo, @QueryParam("beneficiarioFallecido") Boolean beneficiarioFallecido,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Método que permite obtener la información de parametrización para una liquidación masiva
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return DTO con la información de parametrización
     * @author rlopez
     */
    @GET
    @Path("/consultarParametrizacion/liquidacionMasiva/{numeroRadicacion}")
    public IniciarSolicitudLiquidacionMasivaDTO consultarParametrizacionLiquidacionMasiva(
            @PathParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Método que permite obtener la información relacionada con los destinatarios
     * del comunicado de una liquidación masiva
     * 
     * @param numeroRadicacion
     *            Valor del número de radicación
     * @return lista DatosComunicadoDTO con la información de parametrización
     * @author jocampo
     */
    @GET
    @Path("/consultarInformacionComunicados/liquidacionMasiva/{numeroRadicacion}")
    public List<DatosComunicadoDTO> consultarInformacionComunicadosLiquidacionMasiva(
            @PathParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Método que permite obtener el porcentaje de avance sobre un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return Número que representa el porcentaje de avance sobre el proceso de liquidación
     * @author rlopez
     */
    @GET
    @Path("/consultarPorcentajeAvanzadoProcesoLiquidacion/{numeroRadicacion}")
    public Integer consultarPorcentajeAvanceProcesoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Método que permite registrar la cancelación para un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @author rlopez
     */
    @PUT
    @Path("/cancelarProcesoLiquidacion/{numeroRadicacion}")
    public void cancelarProcesoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * <b>Descripción:</b>Método encargado de llamar el SP para almacenar los detalles de subsidios programados 
     * HU-317-508
     */
    @PUT
    @Path("/agregar/detallesProgramadosToDetallesSubsidiosAsignados")
    public void agregarDetallesProgramadosToDetallesSubsidiosAsignados();
    
    /**
     * Método que permite inciar el registro con el porcentaje de avance sobre un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return Número que representa el porcentaje de avance sobre el proceso de liquidación
     * @author jocampo
     */
    @PUT
    @Path("/iniciarPorcentajeAvanzadoProcesoLiquidacion/{numeroRadicacion}")
    public void iniciarPorcentajeAvanceProcesoLiquidacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Metodo para verificar si hay una liquidacion de Fallecimiento en proceso
     * @return
     * @author jocampo
     */
    @GET
    @Path("/verificarLiquidacionFallecimientoEnProceso")
    public Boolean verificarLiquidacionFallecimientoEnProceso();
    
    /**
     * Metodo para consultar los beneficiarios que posee un empleado dependiente asociados a las condiciones seleccionadas en la liquidación
     * @param idSolicitud
     *        identificador unico de la solicitud global
     * @param idPersona
     *        identificador unico de la persona
     * @return Lista con los beneficarios asociados a las condiciones de la liquidación
     * @author mosorio
     */
    @GET
    @Path("/buscarBeneficiariosAfiliado/condicionesLiquidacionSubsidio/{idSolicitud}/{idPersona}")
    public List<BeneficiariosAfiliadoDTO> consultarBeneficiariosAfiliadoCondicionesLiquidacion(@NotNull @PathParam("idSolicitud") Long idSolicitud,
            @NotNull @PathParam("idPersona") Long idPersona);

    /**
     * Metodo encargado de eliminar los registros almacenados de las tablas AplicacionValidacionSubsidioPersona y
     * AplicacionValidacionSubsidio,
     * al momento de cancelar el proceso realizado en las Hus 513,514,515 y 516, puesto que no se guardo el proceso completo.
     * @param numeroRadicacion
     *        valor unico del numéro del radicado.
     * @param idCondicionPersona
     *        id de la condición de la persona para eliminar las validaciones relacionadas
     */
    @DELETE
    @Path("/eliminarProceso/gestionarTrabajadorBeneficiario")
    public void eliminarProcesoGestionarTrabajadorBeneficiario(@QueryParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("condicionPersona") Long idCondicionPersona,
            @QueryParam("tipoLiquidacion") TipoLiquidacionEspecificaEnum tipoLiquidacion,
            @QueryParam("cumple") Boolean cumple, @QueryParam("esTrabajadorFallecido") Boolean esTrabajadorFallecido);
    
    /**
     * Método que actualiza la observacion del proceso
     * 
     * @param idSolicitudLiquidacion
     * @param observacion
     * @param userDTO
     */
    @POST
    @Path("/cancelarMasivaActualizarObservacionesProceso")
    public Map<String,String> cancelarMasivaActualizarObservacionesProceso(
            @QueryParam("numeroSolicitud") String numeroSolicitud, 
            String observacion, 
            @Context UserDTO userDTO); 

    /**
     * Método que se encarga de buscar liquidaciones por empleador
     *
     * @param tipoIdentificacion
     *            Valor del tipo de identificación del empleador
     * @param numeroIdentificacion
     *            Valor del número de identificación del empleador
     * @param periodo
     *            periodo liquidado
     * @param fechaInicio
     *            Rango inicial de la fecha de la liquidación
     * @param fechaFin
     *            Rango inicial de la fecha de la liquidación
     * @param numeroRadicacion
     *            numero de radicación de la liquidación
     * @return listado de liquidaciones que cumplen con los criterios de
     *         búsqueda
     * @author jocampo
     * 
     */
    @GET
    @Path("/consultarLiquidacionesPorEmpleador")
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorEmpleador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodo") Long periodo,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("numeroRadicacion") String numeroRadicacion, @Context UriInfo uri,
            @Context HttpServletResponse response);
    
    /**
     * Método que se encarga de buscar liquidaciones por trabajador
     *
     * @param tipoIdentificacion
     *            Valor del tipo de identificación del trabajador
     * @param numeroIdentificacion
     *            Valor del número de identificación del trabajador
     * @param periodo
     *            periodo liquidado
     * @param fechaInicio
     *            Rango inicial de la fecha de la liquidación
     * @param fechaFin
     *            Rango inicial de la fecha de la liquidación
     * @param numeroRadicacion
     *            numero de radicación de la liquidación
     * @param tipoLiquidacion
     *            tipo de liquidación 
     * @return listado de liquidaciones que cumplen con los criterios de
     *         búsqueda
     * @author jocampo
     * 
     */
    @POST
    @Path("/consultarLiquidacionesPorTrabajador")
    public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorTrabajador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, List<Long> periodo,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("tipoLiquidacion") TipoProcesoLiquidacionEnum tipoLiquidacion,
            @Context UriInfo uri, @Context HttpServletResponse response);
    /**
     * @param tipoIdentificacion
     *            Valor del tipo de identificación del trabajador
     * @param numeroIdentificacion
     *            Valor del número de identificación del trabajador
     * @param numeroRadicacion
     *            numero de radicación de la liquidación
     * @return listado de las validaciones de la liquidación que cumplen con los criterios de búsqueda
     * @author jocampo
     */
    @GET
    @Path("/consultarValidacionesLiquidacionesPorTrabajador")
    public ConsultaValidacionesLiquidacionSubsidioMonetarioDTO consultarValidacionesLiquidacionesPorTrabajador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @QueryParam("numeroRadicacion") String numeroRadicacion,
            @QueryParam("tipoIdentificacionEmpl") TipoIdentificacionEnum tipoIdentificacionEmpl, 
            @QueryParam("numeroIdentificacionEmpl") String numeroIdentificacionEmpl);

    /**
     * Método que consulta  la información de la vista 360 de personas, relacionada con el grupo familiar
     *  
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroRadicacion
     * @param periodo
     * @return
     */
    @GET
    @Path("/consultarGrupoFamiliarLiquidacionesPorTrabajador")
    public List<DetalleResultadoPorAdministradorDTO> consultarGrupoFamiliarLiquidacionesPorTrabajador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @QueryParam("tipoIdentificacionEmpl") TipoIdentificacionEnum tipoIdentificacionEmp, 
            @QueryParam("numeroIdentificacionEmpl") String numeroIdentificacionEmp,
            @QueryParam("numeroRadicacion") String numeroRadicacion, 
            @QueryParam("periodo") Long periodo);

    /**
     * Método que se encarga de buscar liquidaciones por empleador para generar el reporte a descargar
     *
     * @param tipoIdentificacion
     *            Valor del tipo de identificación del empleador
     * @param numeroIdentificacion
     *            Valor del número de identificación del empleador
     * @param periodo
     *            periodo liquidado
     * @param fechaInicio
     *            Rango inicial de la fecha de la liquidación
     * @param fechaFin
     *            Rango inicial de la fecha de la liquidación
     * @param numeroRadicacion
     *            numero de radicación de la liquidación
     * @return listado de liquidaciones que cumplen con los criterios de
     *         búsqueda
     * @author jocampo
     * 
     */
    @GET
    @Path("/exportarLiquidacionesPorEmpleador")
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleador(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodo") Long periodo,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("numeroRadicacion") String numeroRadicacion, @Context UriInfo uri,
            @Context HttpServletResponse response);
    
    /**
     * Se encarga de consultar la información para la vista 360 de detalle de la
     * liquidación especifica de fallecimiento
     * 
     * @param numeroRadicado
     * @return información para la vista 360 de detalle de la liquidación
     *         especifica de fallecimiento
     * @author jocampo
     */
    @GET
    @Path("/consultarInfoLiquidacionFallecimientoVista360")
    public DetalleLiquidacionSubsidioEspecificoFallecimientoDTO consultarInfoLiquidacionFallecimientoVista360(
            @QueryParam("numeroRadicacion") String numeroRadicado);
    
    /**
     * Metodo encargado de saber si el beneficiario esta en la categoria PADRES
     * @param idCondicionBeneficiario
     *        <code>Long</code>
     *        Identificador de la condición del beneficiario
     * @return TRUE esta en la categoria PADRES, FALSE de lo contrario.
     */
    @GET
    @Path("/consultarBeneficiarioPadre")
    public Boolean consultarBeneficiarioPadre(@NotNull @QueryParam("idCondicionBeneficiario") Long idCondicionBeneficiario,
            @NotNull @QueryParam("numeroRadicacion") String numeroRadicado);

    /**
     * Método que permite obtener la información relacionada con los destinatarios
     * del comunicado 137 y 138
     * 
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return lista DatosComunicadoDTO con la información de parametrización
     * @author mosorio
     */
    @GET
    @Path("/consultarInformacionComunicados/fallecimiento137_138/{numeroRadicacion}/{causal}")
    public List<DatosComunicadoDTO> consultarInformacionComunicadosFallecimiento137138(
            @PathParam("numeroRadicacion") String numeroRadicacion, @PathParam("causal") Long causal);
    
    /**
     * Método que se encarga de llamar el SP que consolida los subsidios por fallecimiento
     * @param numeroRadicado
     *        Valor del número de radicado
     * @param modoDesembolso
     *        Modo en el que realiza el desembolso de subsidio
     * @author mosorio
     */
    @GET
    @Path("/consolidarSubsidiosFallecimiento")
    public void consolidarSubsidiosFallecimiento(@QueryParam("numeroRadicado") String numeroRadicado,
            @QueryParam("tipoDesembolso") ModoDesembolsoEnum modoDesembolso);
    
    /**
     * Se encarga de consultar los destinatarios y la información adicional del comunicado 54,55,57 y 58
     * al momento de dispersar una liquidación por fallecimiento
     * 
     * @param numeroRadicacion
     * @param adminSubsidio
     * @param modoDesembolso
     * @return
     */
    @GET
    @Path("/obtenerDatosComunicadosDispersionFallecimiento")
    public List<DatosComunicadoDTO> obtenerDatosComunicadosDispersionFallecimiento(@QueryParam("numeroRadicado") String numeroRadicacion,
            @QueryParam("modoDesembolso") ModoDesembolsoEnum modoDesembolso);

    /**
     * Consulta si exiten condiciones y parametros para los periodos indicados 
     * @param periodos
     * @return Lista con los valores de periodo
     * @author jocampo
     */
    @POST
    @Path("/consultarParametrosPeriodos")
    public List<ValorPeriodoDTO> consultarParametrosPeriodos(List<Long> periodos);
    
    /**
     * Servicio que permite obtener la información de trazabilidad asociada a una liquidación específica
     * @param identificadorLiquidacion
     *        valor del idnetificador de la liquidación
     * @return información de la trazabilidada
     * @author dsuesca
     */
    @GET
    @Path("/obtenerTrazabilidadSubsidioEspecifico")
    public List<TrazabilidadSubsidioEspecificoDTO> obtenerTrazabilidadSubsidioEspecifico(
            @QueryParam("identificadorLiquidacion") Long identificadorLiquidacion);
    
    /**
     * Servicio que permite obtener la información de la cuenta de confa     
     * @return información de la cuenta
     * @author dsuesca
     */
    @GET
    @Path("/obtenerCuentaCCF")
    public CuentaCCFModeloDTO obtenerCuentaCCF();
    
    /**
     * Método que se encarga de registrar una aplicación validación subsidio
     * @param aplicacionValidacionDTO
     *        DTO con la información de la AplicacionValidacionSubsidio
     * @return Identificador de la AplicacionValidacionSubsidio registrada
     * @author rlopez
     */
    @POST
    @Path("/registrar/cuentaCCF")
    public Long registrarCuentaCCF(@NotNull CuentaCCFModeloDTO cuentaDTO);
    
    /**
     * Método que se encarga de validar estructura de archivo de bloqueo de subsidio monetario
     * @param aplicacionValidacionDTO
     *        DTO con la información de la AplicacionValidacionSubsidio
     * @return Identificador de la AplicacionValidacionSubsidio registrada
     * @author rlopez
     */
    @POST
    @Path("/verificarEstructuraArchivoBloquedoCM") 
    public ResultadoValidacionArchivoBloqueoCMDTO verificarEstructuraArchivoBloquedoCM(CargueArchivoBloqueoCMDTO cargue,@Context UserDTO userDTO);
    
    /**
     * Método que permite obtener la información relacionada con los destinatarios
     * del comunicado 137 y 138
     * 
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return lista DatosComunicadoDTO con la información de parametrización
     * @author mosorio
     */
    @POST
    @Path("/radicar/bloqueoCM/{idCargueBloqueoCuotaMonetaria}")
    public int radicarBloqueoCM(
            @PathParam("idCargueBloqueoCuotaMonetaria") Long idCargueBloqueoCuotaMonetaria);
    
    /**
     * Método que permite obtener la información relacionada con los destinatarios
     * del comunicado 137 y 138
     * 
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return lista DatosComunicadoDTO con la información de parametrización
     * @author mosorio
     */
    @POST
    @Path("/cancelar/bloqueoCM/{idCargueBloqueoCuotaMonetaria}")
    public int cancelarBloqueoCM(
            @PathParam("idCargueBloqueoCuotaMonetaria") Long idCargueBloqueoCuotaMonetaria); 
    
    /**
     * <b>Descripción:</b>Servicio que consulta el beneficiario o beneficiarios que coincidan con los filtros de busqueda.</br>
     * 
     * @param tipoIdentificacion
     *        tipo de identificación del beneficiario
     * @param numeroIdentificacion
     *        número de identificación del beneficiario
     * @param primerNombre
     *        primer nombre del beneficiario
     * @param segundoNombre
     *        segundo nombre del beneficiario
     * @param primerApellido
     *        primer apellido del beneficiario
     * @param segundoApellido
     *        segundo apellido del beneficiario
     * @return beneficiario o beneficiarios que coincidan con los filtros.
     * @author mosorio
     */
    @GET
    @Path("/consultarBeneficiarioSub")
    public List<BeneficiarioModeloDTO> consultarBeneficiarioSub(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("primerNombre") String primerNombre,
            @QueryParam("segundoNombre") String segundoNombre, @QueryParam("primerApellido") String primerApellido,
            @QueryParam("segundoApellido") String segundoApellido,@QueryParam("fechaNacimiento") Long fechaNacimiento); 

    /**
     * Método que se encarga de radicar beneficiarios bloqueados
     * @param CargueBloqueoCMDTO
     *        DTO con la información de beneficiarios bloqueados
     * @return Identificador de la AplicacionValidacionSubsidio registrada
     * @author rlopez
     */
    @POST
    @Path("/radicarCargueManualBloqueoCM") 
    public Long radicarCargueManualBloqueoCM(CargueBloqueoCMDTO cargue, @Context UserDTO userDTO);
    
    
    /**
     * Servicio que consulta los beneficiarios bloqueados    
     * @return lista de beneficiarios bloqueados
     * @author dsuesca
     */
    @POST
    @Path("/consultarBeneficiariosBloqueados") 
    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueados(ConsultaBeneficiarioBloqueadosDTO consulta);
    
    /**
     * Servicio que consulta los beneficiarios bloqueados    
     * @return lista de beneficiarios bloqueados
     * @author dsuesca
     */
    @POST
    @Path("/desbloquearBeneficiariosCM") 
    public int desbloquearBeneficiariosCM(List<BloqueoBeneficiarioCuotaMonetariaDTO> beneficiariosBloqueados, @Context UserDTO userDTO);
    
    /**
     * Genera la información histórica de las liquidaciones específicas de fallecimiento a fin de ser exportada en un archivo .xlsx
     * @param periodoRegular
     *        Periodo regular asociado a la liquidación de fallecimiento
     * @param fechaInicio
     *        Filtro de fecha inicial para la consulta de históricos
     * @param fechaFin
     *        Filtro de fecha final para la consulta de históricos
     * @param tipoIdentificacion
     *        Tipo de identificación del fallecido
     * @param numeroIdentificacion
     *        Número de identificación del fallecido
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param uri
     * @param response
     * @return Lista de DTO´s con la información histórica
     */
    @POST
    @Path("exportarHistoricoLiquidacionFallecimiento")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response exportarHistoricoLiquidacionFallecimiento(
            @NotNull @QueryParam("periodoRegular") Long periodoRegular, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("tipoIdentificaccion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("numeroOperacion") String numeroRadicacion,
            @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio que consulta si el proceso de staging de subsidios se está ejecutando
     * @return 
     * @author dsuesca
     */
    @GET
    @Path("/validarEnProcesoStaging")
    public Boolean validarEnProcesoStaging();
    
    /**
     * Servicio que verifica si las personas tienen condiciones en staging para un periodo determinado
     * @param periodo
     * @param idPersonas
     * @param idEmpleadores
     * @return
     * @author amarin
     */
    @POST
    @Path("/verificarPersonasSinCondiciones")
    public RespuestaVerificarPersonasSinCondicionesDTO verificarPersonasSinCondiciones(VerificarPersonasSinCondicionesDTO verificacion);
    
    @POST
    @Path("/verificarPersonasSinCondiciones/aprobarResultados")
    public VerificarPersonasSinCondicionesDTO verificarPersonasSinCondicionesAprobarResultados(String numeroRadicado);

    /**
     * Servicio que consulta si el proceso de staging de subsidios se está ejecutando
     * @return 
     * @author dsuesca
     */
    @GET
    @Path("/consultarEmpleadorPorPersonaTrabajador")
    public List<RolAfiliadoDTO> consultarEmpleadorPorPersonaTrabajador(@QueryParam("idPersona")Long idPersona);

    /**
     * Servicio que consulta si el proceso de staging de subsidios se está ejecutando
     * @return 
     * @author dsuesca
     */
    @GET
    @Path("/validarMarcaAprobacionSegNivel")
    public Boolean validarMarcaAprobacionSegNivel(@QueryParam("numeroRadicado")String numeroRadicado);

    /**
     * Servicio que consulta si el proceso de staging de subsidios se está ejecutando
     * @return 
     * @author dsuesca
     */
    @PUT
    @Path("/eliminarMarcaAprobacionSegNivel")
    public void eliminarMarcaAprobacionSegNivel();
    
    
    @POST
    @Path("exportarRegistrosInconsistentesBloqueo")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response exportarRegistrosInconsistentesBloqueo(ExportarInconsistenciasDTO datosExportar);
    
    @POST
    @Path("exportarBeneficiariosBloqueados")
    public Response exportarBeneficiariosBloqueados();
    
    @GET
    @Path("/consultarExistenciaBeneficiariosBloqueados")
    public Boolean consultarExistenciaBeneficiariosBloqueados();
    
    @GET
    @Path("/seleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios")
    public PersonaFallecidaTrabajadorDTO seleccionarPersonaSubsidioFallecimientoTrabajadorBeneficiarios(
    		@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoLiquidacion") TipoLiquidacionEspecificaEnum tipoLiquidacion,
            @NotNull @QueryParam("numeroLiquidacion") String numeroLiquidacion);
    
    /**
     * Consulta si el Trabajador no cumple validación de Trabajador con Aporte mínimo
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/consultarValidacionAporteMinimoFallecimiento")
    public Boolean consultarValidacionAporteMinimoFallecimiento(
    		@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Consulta la condicion Persona del trabajador por Número de radicación.
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/consultarCondicionPersonaRadicacion")
    public Long consultarCondicionPersonaRadicacion(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);
    
    
    /**
     * Confirma la liquidación por fallecimiento.
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/confirmarLiquidacionFallecimientoAporteMinimo")
    public void confirmarLiquidacionFallecimientoAporteMinimo(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Consulta los resultados de la liquidacion para la gestion del aporte mínimo.
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/consultarResultadosLiquidacionGestionAporteMinimo")
    public ResultadoLiquidacionFallecimientoDTO consultarResultadosLiquidacionGestionAporteMinimo(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);
    
    /**
     * Consulta si existen liquidaciones o rechazos en proceso
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/validarProcesoEnEjecucion")
    public Boolean validarProcesoEnEjecucion(@NotNull @QueryParam("numeroRadicado")String numeroRadicado);
    
    /**
     * Persiste proceso en Eliminación
     * @param numeroRadicacion
     * @return
     */
    @POST
    @Path("/gestionarProcesoEliminacion")
    public void gestionarProcesoEliminacion(@NotNull @QueryParam("numeroRadicado") String numeroRadicado);
    
    /**
     * Consulta los descuentos asociados a un trabajador en una liquidación.
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroRadicacion
     * @return
     */
    @GET
    @Path("/consultarDescuentosSubsidioTrabajador")
    public List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> consultarDescuentosSubsidioTrabajador(
    		@QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion")String numeroIdentificacion, 
    		@QueryParam("numeroRadicacion")String numeroRadicacion);
                           /**
     * Consulta SERVICIO WEB SUBSIDIO EN ESPECIE LIQUIDACIÓN MANUAL - CONFA GLPI 57870
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param Periodo
     * @return
     */
    @GET
    @Path("/consultarSubsidioEspecieLiquidacionManual")
    public List<EspecieLiquidacionManualDTO> consultarSubsidioEspecieLiquidacionManual(
    		@QueryParam("tipoIdentificacionAfiliado")TipoIdentificacionEnum tipoIdentificacionAfiliado, @QueryParam("numeroIdentificacionAfiliado")String numeroIdentificacionAfiliado, 
    		@QueryParam("Periodo")String Periodo);
     /**
     * Consulta SERVICIO WEB SUBSIDIO EN ESPECIE LIQUIDACIÓN MANUAL - CONFA GLPI 57870
     * @param tipoIdentificacion
     * @param numeroIdentificacion

     * @return
     */
    @GET
    @Path("/consultarCuotaMonetariaCanalIVR")
    public List<CuotaMonetariaIVRDTO> consultarCuotaMonetariaCanalIVR(
    		@QueryParam("tipoIdentificacionAfiliado")TipoIdentificacionEnum tipoIdentificacionAfiliado, @QueryParam("numeroIdentificacionAfiliado")String numeroIdentificacionAfiliado);

    /**
     * Ejecutar orquestador stagin Fallecimiento falta implementacion
     */
    @POST
    @Path("/ejecutarOrquestadorStaginFallecimiento")
    public void ejecutarOrquestadorStaginFallecimiento(@QueryParam("fechaActual") Long fechaActual, @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/consultarLiquidacionMasivaEnProceso")
    public SolicitudLiquidacionSubsidioModeloDTO consultarLiquidacionMasivaEnProceso();


    /**
     * @param numeroRadicacion
     *        Valor del número de radicación
     */
    @GET
    @Path("/ConsultarCantidadResgistrosSinDerecho/{numeroRadicacion}/")
    public Long ConsultarCantidadResgistrosSinDerecho(@PathParam("numeroRadicacion") String numeroRadicacion);

}