package com.asopagos.cartera.service;

import com.asopagos.cartera.dto.*;
import com.asopagos.dto.cartera.*;
import com.asopagos.dto.cartera.PanelCarteraDTO;
import com.asopagos.dto.modelo.*;
import com.asopagos.entidades.ccf.cartera.DocumentoCartera;
import com.asopagos.entidades.ccf.cartera.TiempoProcesoCartera;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.*;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.dto.modelo.NotificacionNiyarakyActualizacionEstadoInDTO;
import com.asopagos.dto.modelo.NotificacionNiyarakyActualizacionEstadoOutDTO;
import com.asopagos.dto.modelo.GuardarRelacionComunicadoBitacoraCarteraDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la cartera de la Caja de compensación.<b>Módulo:</b> Asopagos - Cartera<br/>
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */

@Path("cartera")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface CarteraService {

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de cartera.
     *
     * @param parametrizacionDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarParametrizacionPreventivaCartera")
    public void guardarParametrizacionPreventivaCartera(ParametrizacionPreventivaModeloDTO parametrizacionDTO);

    /**
     * Servicio que se encarga de consultar la parametrización de cartera.
     *
     * @param tipoParametrizacion tipo de parametrizacion (Preventiva, fiscalización, de cobro)
     * @return parametrización encontrada para el tipo solicitado.
     */
    @GET
    @Path("/{tipoParametrizacion}/consultarParametrizacionCartera")
    public ParametrizacionCarteraModeloDTO consultarParametrizacionCartera(
            @PathParam("tipoParametrizacion") TipoParametrizacionCarteraEnum tipoParametrizacion);

    /**
     * Servicio encargado de guardar o actualizar una solicitud preventiva.
     *
     * @param solicitudPreventivaDTO dto con la información de la solicitud preventiva a almacenar.
     * @return id de la solicitud global.
     */
    @POST
    @Path("/guardarSolicitudPreventiva")
    public Long guardarSolicitudPreventiva(@NotNull SolicitudPreventivaModeloDTO solicitudPreventivaDTO);

    @POST
    @Path("/guardarRelacionComunicadoBitacoraCartera")
    public Long guardarRelacionComunicadoBitacoraCartera(@NotNull GuardarRelacionComunicadoBitacoraCarteraDTO relacionComunicadoBitacoraCarteraDTO);

    /**
     * Servicio encargado de consultar una solicitud preventiva por el número de radicación.
     *
     * @param numeroRadicacion número de radicación de la solicitud global.
     * @return solicitud preventiva.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarSolicitudPreventiva")
    public SolicitudPreventivaModeloDTO consultarSolicitudPreventiva(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encargado de consultar el ciclo de fiscalización que se encuentre en curso.
     *
     * @return ciclo de fiscalización en curso.
     */
    @GET
    @Path("/consultarCicloFiscalizacionActual")
    public CicloCarteraModeloDTO consultarCicloFiscalizacionActual();

    /**
     * Servicio encargado de consultar los aportantes de un ciclo de fiscalización actual.
     *
     * @return lista de los aportantes del ciclo de fiscalización actual.
     */
    @GET
    @Path("/consultarAportantesCiclo")
    public List<SimulacionDTO> consultarAportantesCiclo(@QueryParam("estado") List<EstadoFiscalizacionEnum> estado,
                                                        @QueryParam("idPersona") Long idPersona, @QueryParam("analista") List<String> analista);

    /**
     * Servicio que se encarga de guardar o actualizar un nuevo ciclo de cartera.
     *
     * @param ciclo de fiscalización a guardar.
     * @return ciclo almancenado.
     */
    @POST
    @Path("/guardarCicloCartera")
    public CicloCarteraModeloDTO guardarCicloCartera(CicloCarteraModeloDTO ciclo);

    /**
     * Servicio encargado de actualizar el estado de una solicitud preventiva
     * por número de radicado
     *
     * @param numeroRadicacion, número de radicado de la solicitud
     * @param estadoSolicitud,  estado a actualizar la solicitud
     */
    @POST
    @Path("/actualizarEstadoSolicitudPreventiva/{numeroRadicacion}")
    public void actualizarEstadoSolicitudPreventiva(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
                                                    @NotNull @QueryParam("estadoSolicitud") EstadoSolicitudPreventivaEnum estadoSolicitud);

    /**
     * Servicio que se encarga de consultar los aportantes para una parametrización de cartera.
     *
     * @param filtros filtros de la consulta de aportantes.
     * @return lista que contiene los datos de aportantes para una gestión preventiva.
     */
    @POST
    @Path("/consultarAportantesParametrizacion")
    public SimulacionPaginadaDTO consultarAportantesParametrizacion(FiltrosParametrizacionDTO filtrosParametrizacion, @Context UriInfo uri,
                                                                    @Context HttpServletResponse response);

    /**
     * Servicio que se encarga de consultar los aportantes para una parametrización de cartera.
     *
     * @param filtros filtros de la consulta de aportantes.
     * @return lista que contiene los datos de aportantes para una gestión preventiva.
     */
    @POST
    @Path("/consultarAportantesParametrizacionFiscalizacion")
    public List<SimulacionDTO> consultarAportantesParametrizacionFiscalizacion(FiltrosParametrizacionDTO filtrosParametrizacion);

    /**
     * Servicio encargado de guardar o actualizar una solicitud de fiscalización.
     *
     * @param solicitudFiscalizacionDTO dto con la información de la solicitud de fiscalización a almacenar.
     * @return id de la solicitud global.
     */
    @POST
    @Path("/guardarSolicitudFiscalizacion")
    public Long guardarSolicitudFiscalizacion(@NotNull SolicitudFiscalizacionModeloDTO solicitudFiscalizacionDTO);

    /**
     * Servicio encargado de consultar una solicitud de fiscalización por el número de radicación.
     *
     * @param numeroRadicacion número de radicación de la solicitud global.
     * @return solicitud fiscalización.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarSolicitudFiscalizacion")
    public SolicitudFiscalizacionModeloDTO consultarSolicitudFiscalizacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio encargado de actualizar el estado de una solicitud de fiscalización
     * por número de radicado
     *
     * @param numeroRadicacion, número de radicado de la solicitud
     * @param estadoSolicitud,  estado a actualizar la solicitud
     */
    @POST
    @Path("/{numeroRadicacion}/actualizarEstadoSolicitudFiscalizacion")
    public void actualizarEstadoSolicitudFiscalizacion(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
                                                       @NotNull @QueryParam("estadoSolicitud") EstadoFiscalizacionEnum estadoSolicitud);

    /**
     * Servicio encargado de guardar o actualizar la parametrización de Fiscalización.
     *
     * @param parametrizacionFiscalizacionModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarParametrizacionFiscalizacion")
    public void guardarParametrizacionFiscalizacionCartera(ParametrizacionFiscalizacionModeloDTO parametrizacionFiscalizacionModeloDTO);

    /**
     * Servicio encargado de consultar una lista de los procesos historicos de fiscalización por el número de ciclo, estado, fecha de inicio
     * y fecha fin.
     *
     * @param idCicloFiscalizacion     es el numero del ciclo de tipo long
     * @param estadoCicloFiscalizacion es el estado del ciclo tipo EstadoCicloFiscalizacionEnum
     * @param fechaInicio              es la fecha inicio del ciclo de tipo long
     * @param fechaFin                 es la fecha fin del ciclo de tipo long
     * @return
     */
    @GET
    @Path("/consultarProcesosFiscalizacionHistoricos")
    public List<CicloCarteraModeloDTO> consultarProcesosFiscalizacionHistoricos(
            @QueryParam("idCicloFiscalizacion") Long idCicloFiscalizacion,
            @QueryParam("estadoCicloFiscalizacion") EstadoCicloCarteraEnum estadoCicloFiscalizacion,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin);

    /**
     * Servicio encargado de actualizar la solicitud preventiva
     *
     * @param numeroRadicacion,       numero de radicacion de la solicitud a actualizar
     * @param solicitudPreventivaDTO, dto que contiene la informacion de la solicitud a actualizar
     */
    @POST
    @Path("/actualizarSolicitudPreventiva/{numeroRadicacion}")
    public void actualizarSolicitudPreventiva(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
                                              SolicitudPreventivaModeloDTO solicitudPreventivaDTO);

    /**
     * Servicio que verifica que no hayan ciclos de fiscalizacion activos
     *
     * @return valor true de no existir ciclos de fiscalizacion activos, false sí existen
     */
    @GET
    @Path("/verificarCicloFiscalizacionNoActivo")
    public Boolean verificarCicloFiscalizacionNoActivo();

    /**
     * Servicio que verifica que no hayan ciclos de fiscalizacion activos para un aportante especifico y poderincluirlo al ciclo de fiscalización
     *
     * @return true= Si encuentra el aportante relacionado con el ciclo de fiscalización actual
     * false = Si el aportante no se encuentra relacionado para el ciclo de fiscalización actual
     */
    @POST
    @Path("/verificarCicloFiscalizacionAportante")
    public Boolean verificarCicloFiscalizacionAportante(SimulacionDTO simulacionDTO);

    /**
     * Servicio que sirve para cancelar un ciclo de fiscalizacion
     */
    @POST
    @Path("/cancelarCiclo")
    public void cancelarCiclo(CicloCarteraModeloDTO cicloFiscalizacionModeloDTO);

    /**
     * Servicio encargado de consultar un detalle de un ciclo por usuario supervisor o por usuario especifico
     *
     * @param idCiclo,      identificador del ciclo a consultar
     * @param esSupervisor, identifica si es un usuario supervisor o no
     * @param userDTO,      Usuario que se obtiene del contexto
     * @return retorna el listado de detalles de un ciclo
     */
    @GET
    @Path("/consultarDetalleCiclo/{idCiclo}")
    public List<SimulacionDTO> consultarDetalleCiclo(@PathParam("idCiclo") Long idCiclo,
                                                     @NotNull @QueryParam("esSupervisor") boolean esSupervisor, @QueryParam("gestionManual") boolean gestionManual,
                                                     @Context UserDTO userDTO);

    /**
     * Servicio encargado de obtener el ultimo usuario del back de actualización asignado.
     *
     * @param tipoSolicitante tipo de solicitante.
     * @param tipoTransaccion Tipo de transaccion
     * @return ultimo usuario asignado.
     */
    @GET
    @Path("/obtenerUltimoBackAsignado")
    public String obtenerUltimoBackAsignado(@QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                            @QueryParam("tipoTransaccion") TipoTransaccionEnum tipoTransaccion);

    /**
     * Servicio encargado consultar un detalle del ciclo de aportante por el número de radicado
     *
     * @param numeroRadicado, número de radicado por el cual se va a consultar el detalle del ciclo
     * @return retorna SimulacionDTO que contiene el detalle del ciclo aportante
     */
    @GET
    @Path("/consultarDetalleCicloAportante/{numeroRadicado}")
    public SimulacionDTO consultarDetalleCicloAportante(@NotNull @PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Servicio que se encarga de verificar si un ciclo ya tiene cerradas todas sus solicitudes de ser así se Finaliza el ciclo.
     */
    @POST
    @Path("/verificarCierreCiclos")
    public void verificarCierreCiclos(@QueryParam("tipoCiclo") TipoCicloEnum tipoCiclo,
                                      @QueryParam("idCicloAportante") Long idCicloAportante);

    /**
     * Servicio que se encarga de obtener las solicitudes
     *
     * @param idCicloAportante representa el id de ciclo de aportante
     * @return retorna el listado de SolicitudModeloDTO con la información perteneciente a una solicitud
     */
    @GET
    @Path("/{idCicloFiscalizacion}/consultarSolicitudesCiclo")
    public List<SolicitudModeloDTO> consultarSolicitudesCiclo(@NotNull @PathParam("idCicloFiscalizacion") Long idCicloFiscalizacion,
                                                              @QueryParam("estado") List<EstadoFiscalizacionEnum> estado, @QueryParam("tipoCiclo") TipoCicloEnum tipoCiclo);

    /**
     * Servicio encargado de guardar o actualizar la parametrización de convenios de pago.
     *
     * @param parametrizacionConvenioPagoDTO: Representa la parametrización del convenio de pago
     */
    @POST
    @Path("/guardarParametrizacionConveniosPago")
    public void guardarParametrizacionConveniosPago(ParametrizacionConveniosPagoModeloDTO parametrizacionConvenioPagoDTO);

    /**
     * Servicio que se encarga de consultar la parametrización de convenios de pago.
     *
     * @return ParametrizacionConveniosPagoModeloDTO: Representa la parametrización del convenio de pago
     */
    @GET
    @Path("/consultarParametrizacionConvenioPago")
    public ParametrizacionConveniosPagoModeloDTO consultarParametrizacionConveniosPago();

    /**
     * Servicio encargado de guardar o actualizar la parametrización de exclusiones.
     *
     * @param parametrizacionExclusionesModeloDTO
     */
    @POST
    @Path("/guardarParametrizacionExclusiones")
    public void guardarParametrizacionExclusiones(ParametrizacionExclusionesModeloDTO parametrizacionExclusionesModeloDTO);

    /**
     * Servicio que se encarga de consultar la parametrización de exclusiones.
     *
     * @return ParametrizacionExclusionesModeloDTO: Representa la parametrización de exclusiones
     */
    @GET
    @Path("/consultarParametrizacionExclusiones")
    public ParametrizacionExclusionesModeloDTO consultarParametrizacionExclusiones();

    /**
     * Servicio encargado de guardar o actualizar la parametrización de desafiliación.
     *
     * @param parametrizacionDesafiliacionModeloDTO
     */
    @POST
    @Path("/guardarParametrizacionDesafiliacion")
    public void guardarParametrizacionDesafiliacion(ParametrizacionDesafiliacionModeloDTO parametrizacionDesafiliacionModeloDTO);

    /**
     * Servicio que se encarga de consultar la parametrización de desafiliacion.
     *
     * @return ParametrizacionDesafiliacionModeloDTO: Representa la parametrización de desafiliación
     */
    @GET
    @Path("/{lineaCobro}/consultarParametrizacionDesafiliacion")
    public ParametrizacionDesafiliacionModeloDTO consultarParametrizacionDesafiliacion(
            @PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro);

    /**
     * Servicio que guarda el convenio despues de cumplir las validaciones
     *
     * @param convenioDTO ConvenioDTO
     */
    @POST
    @Path("/guardarConvenioPago")
    public void crearConvenioPago(ConvenioPagoModeloDTO convenioPagoDTO, @Context UserDTO userDTO);

    /**
     * Servicio encargado de actualizar o guardar una exclusion de cartera
     *
     * @param exclusionCarteraDTO, DTO que contiene la información de la exclusion
     */
    @POST
    @Path("/actualizarGuardarExclusionCartera")
    public ExclusionCarteraDTO actualizarGuardarExclusionCartera(@NotNull ExclusionCarteraDTO exclusionCarteraDTO);

    /**
     * Servicio encargado de consultar las exclusiones por aportante
     *
     * @param idPersona, id de la persona a consultar las exclusiones
     * @return retorna el listado de exclusiones cartera DTO
     */
    @GET
    @Path("/consultarExclusionPorAportante")
    public List<ExclusionCarteraDTO> consultarExclusionPorAportante(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de consultar la festion de lineas de cobro para un aportante
     *
     * @param tipoIdentificacion,   tipo de identificación del aportante
     * @param numeroIdentificacion, número de identificacion del aportante
     * @return retorna el listato de de gestiones de lineas de cobro
     */
    @GET
    @Path("/consultarGestionLineaCobro")
    public List<Long> consultarGestionLineaCobro(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                 @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                 @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de consultar la trazabilidad de exclusionCartera
     *
     * @param exclusionCarteraDTO, exclusionCarteraDTO
     * @return retorna el lista de exclusioens cartera DTO
     */
    @GET
    @Path("/buscarTrazabilidadExclusionCartera")
    public List<ExclusionCarteraDTO> buscarTrazabilidadExclusionCartera(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que consulta los convenios de pagos asociados a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de solicitante que puede ser empleador, independiente o pensionado
     * @return Retorna la lista de convenios de pago asociadas al aportante
     */
    @GET
    @Path("/consultarConveniosPago")
    public List<ConvenioPagoModeloDTO> consultarConveniosPago(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                              @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                              @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de consultar los periodos morosos a nivel de aportante
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoSolicitante
     * @return
     */
    @GET
    @Path("/consultarPeriodosMorosos")
    public List<CarteraModeloDTO> consultarPeriodosMorosos(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                           @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                           @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de anular un convenido de pago por su número de convenio
     *
     * @param numeroConvenio, número del convenio de pago a anular
     */
    @POST
    @Path("/anularConvenioPago/{numeroConvenio}")
    public void anularConvenioPago(@NotNull @PathParam("numeroConvenio") Long numeroConvenio, @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de consultar la parametrización de gestion de cobro.
     *
     * @param tipoParametrizacion tipo de parametrizacion (Preventiva, fiscalización, de cobro)
     * @return parametrización encontrada para el tipo solicitado.
     */
    @GET
    @Path("/{tipoParametrizacion}/consultarParametrizacionGestionCobro")
    public List<Object> consultarParametrizacionGestionCobro(
            @PathParam("tipoParametrizacion") TipoParametrizacionGestionCobroEnum tipoParametrizacion);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la linea de cobro.
     *
     * @param lineaCobroDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarLineaCobro")
    public void guardarLineaCobro(LineaCobroModeloDTO lineaCobroModeloDTO);

    /**
     * Servicio que consulta la linea de cobro para personas indepedientes o pensionados
     *
     * @return el modelo de linea de cobro persona
     */
    @POST
    @Path("/consultarLineaCobroPersona/{tipoLinea}")
    public LineaCobroPersonaModeloDTO consultarLineaCobroPersona(@PathParam("tipoLinea") TipoLineaCobroEnum tipoLinea);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la linea de cobro de personas (Independiente o pensionado).
     *
     * @param lineaCobroPersonaDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarLineaCobroPersona")
    public void guardarLineaCobroPersona(LineaCobroPersonaModeloDTO lineaCobroPersonaModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro A.
     *
     * @param accionCobroAModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobroA")
    public void guardarAccionCobroA(AccionCobroAModeloDTO accionCobroAModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro B.
     *
     * @param accionCobroBModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobroB")
    public void guardarAccionCobroB(AccionCobroBModeloDTO accionCobroBModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 1C.
     *
     * @param accionCobro1CModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro1C")
    public void guardarAccionCobro1C(AccionCobro1CModeloDTO accionCobro1CModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 1D.
     *
     * @param accionCobro1DModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro1D")
    public void guardarAccionCobro1D(AccionCobro1DModeloDTO accionCobro1DModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 1E.
     *
     * @param accionCobro1EModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro1E")
    public void guardarAccionCobro1E(AccionCobro1EModeloDTO accionCobro1EModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 1F.
     *
     * @param accionCobro1FModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro1F")
    public void guardarAccionCobro1F(AccionCobro1FModeloDTO accionCobro1FModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2C.
     *
     * @param accionCobro2CModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2C")
    public void guardarAccionCobro2C(AccionCobro2CModeloDTO accionCobro2CModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2D.
     *
     * @param accionCobro2DModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2D")
    public void guardarAccionCobro2D(AccionCobro2DModeloDTO accionCobro2DModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2F.
     *
     * @param accionCobro2FModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2F")
    public void guardarAccionCobro2F(AccionCobro2FModeloDTO accionCobro2GModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2G.
     *
     * @param accionCobro2GModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2G")
    public void guardarAccionCobro2G(AccionCobro2GModeloDTO accionCobro2GModeloDTO);

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2H.
     *
     * @param accionCobro2HModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2H")
    public void guardarAccionCobro2H(AccionCobro2HModeloDTO accionCobro2HModeloDTO);

    /**
     * Servicio que consulta la acción de cobro del método 2E
     *
     * @return el modelo de acción de cobro método 2E
     */
    @POST
    @Path("/consultarAccionCobro2E")
    public AccionCobro2EModeloDTO consultarAccionCobro2E();

    /**
     * Servicio encargado de guardar o actualizar la paramtrización de la accion de cobro 2E.
     *
     * @param accionCobro2EModeloDTO parametrización a guardar.
     */
    @POST
    @Path("/guardarAccionCobro2E")
    public void guardarAccionCobro2E(AccionCobro2EModeloDTO accionCobro2EModeloDTO);

    /**
     * Servicio que consulta el historico de convenios de pago para un aportante
     *
     * @param tipoIdentificacion   tipo de identificacion del aportante
     * @param numeroIdentificacion numero de identificacion del aportante
     * @param tipoSolicitante      tipo de solicitante EMPLEADOR, INDEPENDIENTE, PENSIONADO
     * @return Lista del modelo DTO de convenios de pago
     */
    @GET
    @Path("/consultarHistoricoConvenioPago")
    public List<ConvenioPagoModeloDTO> consultarHistoricoConvenioPago(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio encargado de buscar las exclusiones cartera activa
     *
     * @param tipoSolicitante,         tipo de solicitante
     * @param listExclusionCarteraDTO, lista de exclusiones carteraDTO a consultar
     * @return retorna el listado de exclusiones Cartera DTO
     */
    @POST
    @Path("/buscarListaExclusionCarteraActiva")
    public List<ExclusionCarteraDTO> buscarListaExclusionCarteraActiva(
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<Long> listIdePersonas);

    /**
     * Servicio que consulta el convenio de pago
     *
     * @param idsPersona           Lista de ids de personas
     * @param aportanteConvenioDTO
     * @return Lista de aportante relacionados a los convenios
     */
    @POST
    @Path("/consultarConveniosFiltro")
    public List<AportanteConvenioDTO> consultarConveniosFiltro(AportanteConvenioDTO aportanteConvenioDTO);

    /**
     * Servicio encargado de consultar el estado de cartera
     *
     * @param tipoIdentificacion,   tipo de identificación de la persona
     * @param numeroIdentificacion, número de identificación de la persona
     * @param tipoSolicitante,      tipo de solicitante
     * @return retorna el estado de la cartera
     */
    @GET
    @Path("/consultarEstadoCartera")
    public EstadoCarteraEnum consultarEstadoCartera(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                    @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                    @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que consulta los convenios que no tienen paga la cuota que se ha vencido.
     *
     * @return lista de los convenios de pago que no tuvieron pago en su cuota vencida.
     */
    @POST
    @Path("/consultarPagoConvenio")
    public List<ConvenioPagoModeloDTO> consultarPagoConvenio(List<Date> diasFestivos);

    /**
     * Servicio que consulta los convenios que ya pueden ser cerrados.
     *
     * @return lista de los convenios que tienen los pagos completos y pueden ser cerrados.
     */
    @POST
    @Path("/consultarConveniosCierre")
    public List<ConvenioPagoModeloDTO> consultarConveniosCierre(List<Date> diasFestivos);

    /**
     * Servicio que consulta los convenios que ya deben ser notificados de cierre.
     *
     * @return lista de los convenios que ya pueden ser notificados de su cierre satisfactorio.
     */
    @POST
    @Path("/consultarConveniosComunicado")
    public List<ConvenioPagoModeloDTO> consultarConveniosComunicado(List<Date> diasFestivos);

    /**
     * Servicio que actualiza una lista de convenios.
     *
     * @param conveniosPago a actualizar.
     */
    @POST
    @Path("/actualizarConveniosPago")
    public void actualizarConveniosPago(List<ConvenioPagoModeloDTO> conveniosPago);

    /**
     * Servicio que consultalas personas relacionadas a un tipo de solicitante para crear un convenio de pago
     *
     * @param tipoSolicitante Tipo de solicitante
     * @param idsPersonas     Lista conlos ids de personas
     * @return Lista de aportantes
     */
    @POST
    @Path("/consultarAportantesConvenio")
    public List<AportanteConvenioDTO> consultarAportantesConvenio(AportanteCarteraDTO aportante, @Context UriInfo uri,
                                                                  @Context HttpServletResponse response);

    /**
     * Servicio que consulta la informacion de la persona o empleador que se le va a
     * aplicar el cierre de convenio
     *
     * @param idPersona recibe el idPersona
     * @param tipo      recibe el tipo de solicitante
     * @return un objeto DatosComunicadoCierreConvenioDTO de la persona o empleador que se le va a
     * aplicar el cierre de convenio
     */
    @GET
    @Path("/consultarInformacionCierreConvenio")
    public DatosComunicadoCierreConvenioDTO consultarInformacionCierreConvenio(@QueryParam("idPersona") Long idPersona,
                                                                               @QueryParam("tipo") TipoSolicitanteMovimientoAporteEnum tipo);

    /**
     * Servicio encargado de consultar los periodos morosos a nivel de cotizante
     *
     * @param tipoIdentificacion   Tipo de identificacion dle aportante
     * @param numeroIdentificacion Número de Identificacion del aportante
     * @param tipoSolicitante      Tipo de solicitante del aportante
     * @param periodo,periodo      a consultar la morosidad
     * @return Retorna una lista de cartera modelo DTO con los datos de la deuda presunta y real registrada
     */
    @GET
    @Path("/consultarPeriodosMorososCotizantes")
    public List<CarteraDependienteModeloDTO> consultarPeriodosMorososCotizantes(
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @NotNull @QueryParam("periodo") Long periodo);

    /**
     * Servicio encargado de registrar en Cartera las entidades que, a día hábil hoy, entrar en mora con la CCF
     */
    @POST
    @Path("/crearRegistroCartera")
    public void crearRegistroCartera();

    /**
     * Servicio que consulta todos los registros de cartera.
     *
     * @param filtrosCartera filtros de la cartera.
     * @return lista con todas las carteras.
     */
    @POST
    @Path("/consultarCartera")
    public List<CarteraModeloDTO> consultarCartera(FiltrosCarteraDTO filtrosCartera, @Context UriInfo uri,
                                                   @Context HttpServletResponse response);

    /**
     * Servicio que se encarga de consultar una parametrización preventiva y retornarla en terminaos de parametrización preventiva y no del
     * padre.
     *
     * @return parametrización preventiva o null si no existe.
     */
    @GET
    @Path("/consultarParametrizacionPreventiva")
    public ParametrizacionPreventivaModeloDTO consultarParametrizacionPreventiva();

    /* Inicio proceso 22 */

    /**
     * Servicio que se encarga de consultar el historico del ciclo manual
     *
     * @param fechaInicio filtro de fecha de inicio.
     * @param fechaFin    filtro de fecha fin.
     * @return listado de los ciclos.
     */
    @GET
    @Path("/consultarHistoricoCicloManual")
    public List<CicloCarteraModeloDTO> consultarHistoricoCicloManual(@QueryParam("fechaInicio") Long fechaInicio,
                                                                     @QueryParam("fechaFin") Long fechaFin);

    /**
     * Servicio que se encarga de consultar el método activo para la LC1.
     *
     * @return método activo.
     */
    @GET
    @Path("/consultarMetodoActivoLC1")
    public MetodoAccionCobroEnum consultarMetodoActivoLC1();

    /**
     * Servicio que se encarga de guardar la parametrización de los criterios de gestión de cobro.
     *
     * @param parametrizacionCriterios parametrizaciones a guardar.
     */
    @POST
    @Path("/guardarCriteriosGestionCobro")
    public void guardarCriteriosGestionCobro(@QueryParam("metodo") MetodoAccionCobroEnum metodo,
                                             @NotNull List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios);

    /**
     * Servicio que se encarga de consultar la parametrización de
     *
     * @param lineaCobro linea de cobro a buscar.
     * @param automatica indica true si es automática false si es manual.
     * @param metodo     método 1 o 2 si se trata de la linea de cobro 1.
     * @return parametrización de gestión de cobro encontrada.
     */
    @POST
    @Path("/{lineaCobro}/consultarCriteriosGestionCobro")
    public ParametrizacionCriteriosGestionCobroModeloDTO consultarCriteriosGestionCobro(
            @PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro, @QueryParam("accion") TipoGestionCarteraEnum accion,
            @QueryParam("metodo") MetodoAccionCobroEnum metodo);

    /**
     * Servicio que se encarga de consultar la simulación aportantes de la gestión de cobro
     *
     * @param parametrizacion filtros para buscar los aportantes.
     * @param uri             uri con la información de los filtros.
     * @param response        con la información de los header.
     * @return lista de la simulación consultada.
     */
    @POST
    @Path("/{parametro}/consultarAportantesGestionCobro")
    public List<SimulacionDTO> consultarAportantesGestionCobro(@PathParam("parametro") Boolean parametro,
                                                               @NotNull ParametrizacionCriteriosGestionCobroModeloDTO parametrizacion, @Context UriInfo uri,
                                                               @Context HttpServletResponse response);

    /**
     * Método encargado de consultar la condición del aportante cartera
     *
     * @return retorna true si se encuentran aportantes en la línea de cobro uno
     */
    @GET
    @Path("/consultarCondicionAportanteCarteraLCUNO")
    public Boolean consultarCondicionAportantesCarteraLCUNO(
            @NotNull @QueryParam("estadoOperacion") EstadoOperacionCarteraEnum estadoOperacion,
            @NotNull @QueryParam("tipoLineaCobro") TipoLineaCobroEnum tipoLineaCobro);

    /**
     * Método que retorna el total de aportantes y total deuda por Accion y Línea
     *
     * @param listaAcciones
     * @return
     */
    @GET
    @Path("/consultarTotalDetalleAportantesPanel")
    public GestionCarteraMoraDTO consultarTotalDetalleAportantesPanel(@QueryParam("usuarioAnalista") String usuarioAnalista);
    /* Fin proceso 22 */
    /* Inicio proceso 223 */

    /**
     * Método encargado de validar el envío del comunicado electrónico.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     * @return
     */
    @GET
    @Path("/{numeroRadicacion}/validarEnvioComunicadoElectronico")
    public Boolean validarEnvioComunicadoElectronico(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                     @QueryParam("tipoAccionCobro") TipoAccionCobroEnum tipoAccionCobro, @QueryParam("idCartera") Long idCartera);

    /**
     * Método encargado de validar el envido del comunicado físico.
     *
     * @param numeroRadicacion número de radicación.
     * @return
     */
    @GET
    @Path("/{numeroRadicacion}/validarEnvioComunicadoFisico")
    public Boolean validarEnvioComunicadoFisico(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                @QueryParam("tipoAccionCobro") TipoAccionCobroEnum tipoAccionCobro, @QueryParam("idCartera") Long idCartera);

    /**
     * Método encargado de consultar el comunicado generado para el aportante.
     *
     * @param numeroRadicacion     número de radicación de la solicitud.
     * @param tipoIdentificacion   tipo de identificación.
     * @param numeroIdentificacion número de identificación.
     * @return
     */
    @GET
    @Path("/{numeroRadicacion}/consultarComunicadoGenerado")
    public String consultarComunicadoGenerado(@PathParam("numeroRadicacion") String numeroRadicacion,
                                              @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                              @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que se encarga de consultar los datos de actualización para un número de radicación.
     *
     * @param numeroRadicacion número de radicación.
     * @return actualización de datos.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarActualizacionDatos")
    public ActualizacionDatosDTO consultarActualizacionDatos(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encarga de consultar todos los aportantes asociados a una solicitud de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud de gestión de cobro.
     * @return lista de los aportantes para la remisión del comunicado.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarAportantesPrimeraRemision")
    public RegistroRemisionAportantesDTO consultarAportantesPrimeraRemision(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                                            @QueryParam("municipios") List<Long> municipios);

    /**
     * Servicio que se encarga de consultar las solicitudes de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud de gestión de cobro.
     * @return solicitud de gestión de cobro encontrada.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarSolicitudGestionCobro")
    public SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobro(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encarga de guardar la solicitud de gestion de cobro.
     *
     * @param solicitudGestionCobroDTO solicitud de gestion de cobro a guardar.
     * @param Solicitud                almacenada
     */
    @POST
    @Path("/guardarSolicitudGestionCobro")
    public SolicitudGestionCobroFisicoModeloDTO guardarSolicitudGestionCobro(
            @NotNull SolicitudGestionCobroFisicoModeloDTO solicitudGestionCobroDTO);

    /**
     * Servicio que se encarga de guardar la solicitud de gestion de cobro manual.
     *
     * @param solicitudGestionCobroDTO solicitud de gestion de cobro a guardar.
     * @param id                       de la solicitud global.
     */
    @POST
    @Path("/guardarSolicitudGestionCobroManual")
    public Long guardarSolicitudGestionCobroManual(@NotNull SolicitudGestionCobroManualModeloDTO solicitudGestionCobroDTO);

    /**
     * Servicio que se encarga de actualizar el estado de la solicitud de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     */
    @POST
    @Path("/{numeroRadicacion}/{estadoSolicitud}/actualizarEstadoSolicitudGestionCobro")
    public void actualizarEstadoSolicitudGestionCobro(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                      @PathParam("estadoSolicitud") EstadoSolicitudGestionCobroEnum estadoSolicitud);

    /**
     * Servicio que se encarga de consultar todos los aportantes asociados a una solicitud de gestión de cobro.
     *
     * @param numeroRadicacion número de radicación de la solicitud de gestión de cobro.
     * @return registro remisión del comunicado con la lista de los aportantes.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarAportantesEntregaPrimeraRemision")
    public List<AportanteRemisionComunicadoDTO> consultarAportantesEntregaPrimeraRemision(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encarga de consultar el aportante para la segudna remisión del comunicado.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     * @return aporante consultado.
     */
    @GET
    @Path("/{numeroRadicacion}/consultarAportanteSegundaRemision")
    public RegistroRemisionAportantesDTO consultarAportanteSegundaRemision(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que asigna las acciones de cobro a entidades en Cartera
     *
     * @param accionCobro Tipo de acción de cobro
     * @return La lista de aportantes que fueron asignados a la acción de cobro especificada
     */
    @GET
    @Path("/asignarAccionCobro")
    List<AportanteAccionCobroDTO> asignarAccionCobro(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro);


    @GET
    @Path("/consultaParametrizacionAnexoLiquidacion")
    boolean consultaParametrizacionAnexoLiquidacion(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro);

    /**
     * Servicio que se encarga de guardar el detalle de la solitcitud de gestion de cobro físico
     */
    @POST
    @Path("/guardarDetalleSolicitudGestionCobroFisico")
    public DetalleSolicitudGestionCobroModeloDTO guardarDetalleSolicitudGestionCobroFisico(
            DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO,
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que se encarga de guardar el detalle de la solitcitud de gestion de cobro físico
     */
    @POST
    @Path("/guardarDetallesSolicitudGestionCobroFisico")
    public void guardarDetallesSolicitudGestionCobroFisico(
            List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO,
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que se encarga de guardar el detalle de la solitcitud de gestion de cobro físico
     */
    @POST
    @Path("/guardarListaDetalleSolicitudGestionCobroFisico")
    public void guardarListaDetalleSolicitudGestionCobroFisico(
            List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO,
            @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que se encarga de guardar el detalle de la solitcitud de gestion de cobro
     */
    @POST
    @Path("/actualizarDetallesSolicitudGestionCobro")
    public void actualizarDetallesSolicitudGestionCobro(
            List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO);

    /**
     * Servicio que se encarga de guardar varios detalles de la solitcitud de gestion de cobro
     *
     * @param detalleSolicitudGestionCobroModeloDTO detalles a guardar
     */
    @POST
    @Path("/guardarDetallesSolicitudGestionCobro")
    public void guardarDetallesSolicitudGestionCobro(List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroModeloDTO,
                                                     @QueryParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * Servicio que almacena una solicitud de gestión de cobro electrónico
     *
     * @param solicitudDTO Información de la solicitud a guardar
     * @return La solicitud actualizada
     */
    @POST
    @Path("/guardarSolicitudGestionCobroElectronico")
    public SolicitudGestionCobroElectronicoModeloDTO guardarSolicitudGestionCobroElectronico(
            @NotNull SolicitudGestionCobroElectronicoModeloDTO solicitudDTO);

    /**
     * Servicio que se encarga de consultar el detalle de la solicitud de gestión de cobro
     *
     * @param tipoIdentificacion,   tipo de identificación de la persona
     * @param numeroIdentificacion, número de identificación de la persona
     * @param numeroRadicacion,     número de radicación
     * @return retorna el listado de detalles pertenecientes a la solicitud de gestión de cobro
     */
    @POST
    @Path("/consultarDetalleSolicitudGestionCobro")
    public List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleSolicitudGestionCobro(
            @NotNull @Valid List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion);

    /**
     * Servicio que se encarga de consultar el detalle de la solitud de gestión de cobro
     *
     * @param idSolicitud
     * @return
     */
    @GET
    @Path("/{idSolicitud}/consultarDetallePorSolicitud")
    public DetalleSolicitudGestionCobroModeloDTO consultarDetallePorSolicitud(@PathParam("idSolicitud") Long idSolicitud);

    /**
     * Servicio que se encarga de consultar la solicitud de gestion de cobro electronica
     *
     * @param numeroRadicado
     * @return
     */
    @GET
    @Path("/{numeroRadicado}/consultarSolicitudGestionCobroElectronico")
    public SolicitudGestionCobroElectronicoModeloDTO consultarSolicitudGestionCobroElectronico(
            @PathParam("numeroRadicado") String numeroRadicado);

    /**
     * Servicio que se encarga de actualizar estado de la solicitud de gestion de cobro electronico
     *
     * @param numeroRadicado
     * @param estadoSolicitudGestionCobro
     */
    @GET
    @Path("/{numeroRadicacion}/actualizarEstadoSolicitudGestionCobroElectronico")
    public void actualizarEstadoSolicitudGestionCobroElectronico(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
                                                                 @QueryParam("estadoSolicitudGestionCobro") EstadoSolicitudGestionCobroEnum estadoSolicitudGestionCobro);

    /**
     * Servicio que consulta el ultimo destinatario de la tabla solicitud
     *
     * @return destinatario
     */
    @GET
    @Path("/consultarUltimoDestinatarioSolicitud")
    public String consultarUltimoDestinatarioSolicitud();

    /**
     * Servicio que obtiene el último usuario "Analista de cartera de aportes" asignado a una tarea BPM
     *
     * @return El nombre de usuaio respectivo
     */
    @GET
    @Path("/obtenerUltimoAnalistaAsignado")
    public String obtenerUltimoAnalistaAsignado();

    /**
     * Servicio que almacena un documento de soporte de gestión de cobro de aportes
     *
     * @param documentoCarteraDTO Información del documento a almacenar
     * @return La información del documento actualizada
     */
    @POST
    @Path("/guardarDocumentoCartera")
    public DocumentoCarteraModeloDTO guardarDocumentoCartera(@NotNull DocumentoCarteraModeloDTO documentoCarteraDTO);

    /**
     * Servicio que almacena un documento de soporte de gestión de cobro de aportes
     *
     * @param documentoCarteraDTO Información del documento a almacenar
     * @return La información del documento actualizada, retorna una entidad manejada de DocumentoCartera
     */
    @POST
    @Path("/guardarDocumentoCarteraEnt")
    public DocumentoCartera guardarDocumentoCarteraEnt(@NotNull DocumentoCarteraModeloDTO documentoCarteraDTO);

    /**
     * Servicio que consulta el mismo usuario del back de gestión cobro de aportes que gestionó la tarea en la HU-223-170
     *
     * @param idSolicitudPrimeraRemision
     * @return
     */
    @GET
    @Path("/consultarSolicitudGestionCobroPorId")
    public SolicitudGestionCobroFisicoModeloDTO consultarSolicitudGestionCobroPorId(
            @QueryParam("idSolicitudPrimeraRemision") Long idSolicitudPrimeraRemision);

    /**
     * Servicio que se encarga de consultar el detalle de la solitud de gestión de cobro por id segunda remision
     *
     * @param idSolicitudPrimeraRemision
     * @return
     */
    @GET
    @Path("/{idSolicitudPrimeraRemision}/consultarDetallePorSolicitudPrimeraRemision")
    public DetalleSolicitudGestionCobroModeloDTO consultarDetallePorSolicitudPrimeraRemision(
            @PathParam("idSolicitudPrimeraRemision") Long idSolicitudPrimeraRemision);
    /* Fin proceso 223 */

    /**
     * Servicio que se encarga de consultar los aportanes que seran expulsados
     *
     * @param tipoSolicitante recibe como parametro tipo de solicitante
     * @return retorna lista de los aporantes candidatos a ser expulsados
     */
    @GET
    @Path("/consultarAportantesDesafiliacion")
    public List<DesafiliacionAportanteDTO> consultarAportantesDesafiliacion(
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que se encarga de consultar los aportanes asociados a la solicitud de desafiliacion
     *
     * @param numeroRadicacion recibe como parametro el numero de radicacion de la solicitud
     * @return retorna lista de los aporantes de la solicitud de desafiliacion
     */
    @GET
    @Path("/{numeroRadicacion}/consultarAportantesSolicitudDesafiliacion")
    public List<DesafiliacionAportanteDTO> consultarAportantesSolicitudDesafiliacion(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encarga de consultar el promedio de periodo impagos de los aportantes
     *
     * @param personaModeloDTOs recibe lista de los aportantes a los cuales se le va calcular el promedio
     * @return retorna el promedio de periodos impagos
     */
    @POST
    @Path("/consultarPromedioPeriodoImpago")
    public BigDecimal consultarPromedioPeriodoImpago(List<PersonaModeloDTO> personaModeloDTOs,
                                                     @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que permite consultar la solicitud de desafiliacion
     *
     * @param numeroRadicacion parametro por el cual se consulta la solicitud de desafiliacion
     * @return un objeto SolicitudDesafiliacionModeloDTO
     */
    @GET
    @Path("/{numeroRadicacion}/consultarSolicitudDesafiliacion")
    public SolicitudDesafiliacionModeloDTO consultarSolicitudDesafiliacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que permite guardar la solicitud de desafiliacion
     *
     * @param solicitudDesafiliacionModeloDTO informacion de la solicitud de desafiliacion que sera almacenada
     */
    @POST
    @Path("/guardarSolicitudDesafiliacion")
    public SolicitudDesafiliacionModeloDTO guardarSolicitudDesafiliacion(SolicitudDesafiliacionModeloDTO solicitudDesafiliacionModeloDTO,
                                                                         @Context UserDTO userDTO);

    /**
     * Servicio que permite actualizar la solicitud de desafiliacion
     *
     * @param numeroRadicacion    parametro que permite consultar la solicitud de desafiliacion
     * @param estadoDesafiliacion nuevo estado que tendra la solicitud
     */
    @POST
    @Path("/{numeroRadicacion}/actualizarEstadoSolicitudDesafiliacion")
    public void actualizarEstadoSolicitudDesafiliacion(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                       @QueryParam("estadoDesafiliacion") EstadoSolicitudDesafiliacionEnum estadoDesafiliacion);

    /**
     * Servicio que se encarga de consultar el detalle de los aportantes de una línea y acción de cobro particular.
     *
     * @param lineaCobro  linea de cobro a buscar.
     * @param accionCobro acción de cobro a buscar.
     * @return aportantes consultados.
     */
    @GET
    @Path("/{lineaCobro}/consultarDetalleAportantes")
    public List<PanelCarteraDTO> consultarDetalleAportantes(@PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro,
                                                                      @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @QueryParam("usuarioAnalista") String usuarioAnalista,
                                                                      @QueryParam("filtroNumIdentificacion") String filtroNumIdentificacion,
                                                                      @Context UriInfo uri, @Context HttpServletResponse response);


    @GET
    @Path("/{lineaCobro}/exportExcelConsultarDetalleAportantes")
    public byte[] exportExcelConsultarDetalleAportantes(@PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro,
                                                        @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @QueryParam("usuarioAnalista") String usuarioAnalista,
                                                        @Context UriInfo uri, @Context HttpServletResponse response) throws IOException;

    /**
     * Servicio que se encarga de consultar el total del detalle de los aportantes de una línea y acción de cobro particular.
     *
     * @param lineaCobro
     * @param accionCobro
     * @return
     */
    @GET
    @Path("/{lineaCobro}/consultarTotalDetalleAportantes")
    public Object[] consultarTotalDetalleAportantes(@PathParam("lineaCobro") TipoLineaCobroEnum lineaCobro,
                                                    @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @QueryParam("usuarioAnalista") String usuarioAnalista);

    /**
     * Servicio que se encarga de consultar los aportantes que se encuentran en deuda antigua.
     *
     * @param filtros filtros para consultar.
     * @return aportantes con deuda antigua.
     */
    @POST
    @Path("/consultarAportantesTraspasoDeudaAntigua")
    public AportantesTraspasoDeudaDTO consultarAportantesTraspasoDeudaAntigua(FiltrosTrasladoDeudaAntiguaPersonaDTO filtros,
                                                                              @Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio encargado de consultar la deuda
     *
     * @param tipoIdentificacion,   tipo de identificaición del aportante
     * @param numeroIdentificacion, número de identificación del aportante
     * @param tipoSolicitante,      tipo de solicitante
     * @return retorna el listado de deudas dto
     */
    @GET
    @Path("/consultarDeuda")
    public List<DeudaDTO> consultarDeuda(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                         @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                         @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                         @QueryParam("lineaCobro") TipoLineaCobroEnum lineaCobro);

    /**
     * Servicio encargado de consultar Bitácora de cartera para un aportante en una línea de cobro
     *
     * @param numeroOperacion Número de operación de cartera
     * @return Lista de registros de bitácora para el aportante
     */
    @GET
    @Path("/consultarBitacora")
    public List<BitacoraCarteraDTO> consultarBitacora(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio encargado de consultar Bitácora de cartera para un aportante en una línea de cobro
     *
     * @param numeroOperacion Número de operación de cartera
     * @return Lista de registros de bitácora para el aportante
     */
    @GET
    @Path("/consultarBitacoraSinResultado")
    public List<BitacoraCarteraDTO> consultarBitacoraSinResultado(@QueryParam("numeroOperacion") Long numeroOperacion,
                                                                  @QueryParam("actividad") TipoActividadBitacoraEnum actividad, @QueryParam("resultado") List<ResultadoBitacoraCarteraEnum> resultados);

    /**
     * Servicio encargado de guardar la bitacora de cartera
     *
     * @param bitacoraCartera, bitacoraCartera a crear
     */
    @POST
    @Path("/guardarBitacoraCartera")
    public Long guardarBitacoraCartera(@NotNull BitacoraCarteraDTO bitacoraCartera, @Context UserDTO userDTO);

    /**
     * Servicio encargado de guardar la bitacora de cartera
     *
     * @param bitacoraCartera, bitacoraCartera a crear
     */
    @POST
    @Path("/guardarListaBitacorasCarteraPorIdCartera")
    public void guardarListaBitacorasCarteraPorIdCartera(@NotNull List<BitacoraCarteraDTO> listaBitacorasCartera, @Context UserDTO userDTO);

    /**
     * Servicio que consulta los correos de los destinatarios
     *
     * @param parametrizacion      Parametrización de la gestión de cobro
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param lineaCobro           Línea de cobro
     * @return Lista de correos
     */
    @POST
    @Path("/obtenerRolesDestinatarios")
    public List<AutorizacionEnvioComunicadoDTO> obtenerRolesDestinatarios(ParametrizacionGestionCobroModeloDTO parametrizacion,
                                                                          @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                                          @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("lineaCobro") TipoLineaCobroEnum lineaCobro);

    /**
     * Servicio que se encarga de actualizar la linea de cobro a deuda antigua a los aportanres seleccionados
     * para el proceso de desafiliacion
     *
     * @param aportanteDTOs lista de aportantes seleccionados para el proceso de desafiliacion
     */
    @POST
    @Path("/actualizarLineaCobroDesafiliacion")
    public void actualizarLineaCobroDesafiliacion(List<DesafiliacionAportanteDTO> desafiliacionAportanteDTOs,
                                                  @QueryParam("usuarioTraspaso") String usuarioTraspaso);

    /**
     * Servicio que consulta la solicitud de gestión de cobro manual, ya sea por número de radicado o por identificador de cartera
     *
     * @param numeroRadicacion número de radicación que pertenece a la solicitud
     * @param numeroOperacion  Número de operación
     * @return número de radicado de la solicitud
     */
    @GET
    @Path("/consultarSolicitudGestionCobroManual")
    public SolicitudGestionCobroManualModeloDTO consultarSolicitudGestionCobroManual(
            @QueryParam("numeroRadicacion") String numeroRadicacion, @QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio que consulta la solicitud de gestión de cobro manual, ya sea por número de radicado o por identificador de cartera
     *
     * @param numeroRadicacion número de radicación que pertenece a la solicitud
     * @param numeroOperacion  Número de operación
     * @return número de radicado de la solicitud
     */
    @GET
    @Path("/consultarSolicitudGestionCobroManualEstado")
    public SolicitudGestionCobroManualModeloDTO consultarSolicitudGestionCobroManualEstado(
            @QueryParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio encargado de actualizar el estado de la solicitud de gestión de cobro Manual
     *
     * @param numeroRadicacion, número de radicación de la solicitud
     * @param estadoSolicitud,  estado actualizar de la solicitud de gestión de cobro Manual
     */
    @POST
    @Path("/actualizarEstadoSolicitudGestionCobroManual")
    public void actualizarEstadoSolicitudGestionCobroManual(@NotNull @QueryParam("numeroRadicacion") String numeroRadicacion,
                                                            @NotNull @QueryParam("estadoSolicitud") EstadoFiscalizacionEnum estadoSolicitud);

    /**
     * Servicio encargado de guardar un listado de agendas de cartera
     *
     * @param agendasCarteraDTO, agenda cartera dto a guardar
     */
    @POST
    @Path("/guardarAgendaCartera")
    public void guardarAgendaCartera(@NotNull List<AgendaCarteraModeloDTO> agendasCarteraDTO);

    /**
     * Servicio encargado de guardar el listado de actividades de cartera
     *
     * @param actividadesCarteraDTO, listado de actividades pertenecientes a cartera dto
     */
    @POST
    @Path("/guardarActividadCartera")
    public void guardarActividadCartera(@NotNull List<ActividadCarteraModeloDTO> actividadesCarteraDTO);

    /**
     * Servicio encargado de consultar los aportantes de los edictos.
     *
     * @param numeroRadicacion número de radicación de la solicitud.
     * @return resultado de la remision y sus aportantes.
     */
    @POST
    @Path("/{numeroRadicacion}/consultarAportantesEdictos")
    public RegistroRemisionAportantesDTO consultarAportantesEdictos(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio encargado de consultar la etiqueta del comunicado que se debe enviar según la acción de cobro.
     *
     * @param tipoAccion acción de cobro a consultarle la etiqueta.
     * @return etiqueta consultada.
     */
    @GET
    @Path("/{accionCobro}/consultarEtiquetaPorAccion")
    public EtiquetaPlantillaComunicadoEnum consultarEtiquetaPorAccion(@PathParam("accionCobro") TipoAccionCobroEnum accionCobro);

    /**
     * Servicio que se encarga de consultar la parametrización de gestion de cobro
     *
     * @param lineaCobro linea de cobro a buscar.
     * @return parametrización de gestión de cobro encontrada.
     */
    @POST
    @Path("/consultarCriteriosGestionCobroLinea")
    public List<ParametrizacionCriteriosGestionCobroModeloDTO> consultarCriteriosGestionCobroLinea(
            @NotNull List<TipoLineaCobroEnum> lineasCobro);

    /**
     * Servicio encargado de consultar los candidatos de expulsión
     *
     * @param lstDetalles,         listado de detalles
     * @param tipoAccionCobro,     tipo de acción de cobro
     * @param validacionExclusion, validacion de exclusiones
     * @return retorna el listado de detalles de solicitud de gestión de cobro
     */
    @POST
    @Path("/consultarCanditatosExpulsion")
    public List<DetalleSolicitudGestionCobroModeloDTO> consultarCanditatosExpulsion(
            @NotNull @QueryParam("validacionExclusion") Boolean validacionExclusion,
            @NotNull @QueryParam("tipoAccionCobro") TipoAccionCobroEnum tipoAccionCobro,
            @NotNull List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles);

    /**
     * Servicio que almacena una solicitud global
     *
     * @param solicitudDTO Información de la solicitud a almacenar
     * @return La solcitud guardada
     */
    @POST
    @Path("/guardarSolicitudGlobalCartera")
    SolicitudModeloDTO guardarSolicitudGlobalCartera(@NotNull SolicitudModeloDTO solicitudDTO);

    /**
     * Servicio que obtiene el próximo consecutivo de liquidación para el periodo actual
     *
     * @return El consecutivo de liquidación
     */
    @GET
    @Path("/obtenerConsecutivoLiquidacion")
    String obtenerConsecutivoLiquidacion();

    /**
     * Servicio encargado de guardar una notificación persona
     *
     * @param notificacionPersonalDTO, dto que contiene la información de la notificación judicial
     */
    @POST
    @Path("/crearNotificacionPersonal")
    public List<DocumentoSoporteModeloDTO> crearNotificacionPersonal(@NotNull NotificacionPersonalModeloDTO notificacionPersonalDTO);


    /**
     * Servicio para Niyaraky encargado de recibir la actualizacion de los estados
     * de las notificaciones electronicas de los comunicados certificados
     */
    @POST
    @Path("/niyaraky/notificaciones/actualizarEstado")
    public NotificacionNiyarakyActualizacionEstadoOutDTO crearActualizacionEstadoNiyaraky(@NotNull NotificacionNiyarakyActualizacionEstadoInDTO notificacionNiyarakyActualizacionEstadoInDTO);

    /**
     * Servicio que valida si es posible traspasar la cartera del aportante
     *
     * @param tipoSolicitante
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    @GET
    @Path("/validarTraspasoCartera")
    public List<String> validarTraspasoCartera(@NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                               @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                               @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                               @NotNull @QueryParam("tipoAccionCobro") TipoAccionCobroEnum tipoAccionCobro);

    /**
     * Servicio que guarda la solicitud preventiva agrupadora
     *
     * @param solicitudPreventivaAgrupadoraModeloDTO DTO con la informacion de la solicitud preventiva agrupadora
     * @return retorna un objeto tipo SolicitudPreventivaAgrupadoraModeloDTO
     */
    @POST
    @Path("/guardarSolicitudPreventivaAgrupadora")
    public SolicitudPreventivaAgrupadoraModeloDTO guardarSolicitudPreventivaAgrupadora(
            SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO);

    /**
     * Servicio que consulta que la solicitud agrupadora por numero de radicado
     *
     * @param solicitudPreventivaAgrupadoraModeloDTO DTO con la informacion de la solicitud preventiva agrupadora
     * @return retorna un objeto tipo SolicitudPreventivaAgrupadoraModeloDTO
     */
    @GET
    @Path("/{numeroRadicacion}/consultarSolicitudPreventivaAgrupadora")
    public SolicitudPreventivaAgrupadoraModeloDTO consultarSolicitudPreventivaAgrupadora(
            @PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que consulta las solicitudes agrupadoras donde sus solicitudes individuales que esten cerradas
     *
     * @param idSolicitudAgrupadora identificador de la SolicitudAgrupadora
     * @return retorna un objeto tipo SolicitudPreventivaAgrupadoraModeloDTO
     */
    @GET
    @Path("/consultarCierreSolicitudesPreventivas")
    public List<SolicitudPreventivaAgrupadoraModeloDTO> consultarCierreSolicitudesPreventivas(
            @QueryParam("idSolicitudAgrupadora") List<Long> idSolicitudAgrupadora);

    /**
     * Servicio que el detalle de las solicitudes individuales
     *
     * @return retorna un objeto tipo SolicitudPreventivaModeloDTO
     */
    @GET
    @Path("/{numeroRadicacion}/consultarDetalleSolicitudesIndividuales")
    public List<SimulacionDTO> consultarDetalleSolicitudesIndividuales(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que consulta los aportantes relacionados a una solicitud de gestion de cobro manual
     *
     * @param parametros Parametros de gestion de cobro manual
     * @param userDTO    Información del usuario, tomado del contexto
     * @return Lista del DTO Aportantes gestión manual
     */
    @POST
    @Path("/consultarAportantesGestionManual")
    public List<AportanteGestionManualDTO> consultarAportantesGestionManual(ParametrosGestionCobroManualDTO parametros,
                                                                            @Context UserDTO userDTO);

    /**
     * Servicio que consulta los aportantes relacionados a una solicitud de gestion de cobro manual
     *
     * @param parametros Parametros de gestion de cobro manual
     * @return true o false si la cartera esta vigente para el envio fisico o electronico
     */
    @GET
    @Path("/{numeroRadicacion}/validarCarteraVigenteEnvioFisicoElectronico")
    public Boolean validarCarteraVigenteEnvioFisicoElectronico(@PathParam("numeroRadicacion") String numeroRadicacion,
                                                               @QueryParam("proceso") ProcesoEnum proceso);

    /**
     * Servicio que consulta los aportantes relacionados a una solicitud de gestion de cobro manual
     *
     * @param parametros Parametros de gestion de cobro manual
     * @return true o false si la cartera esta vigente para el envio fisico o electronico
     */
    @GET
    @Path("/consultarCiclosVencidos")
    public List<CicloCarteraModeloDTO> consultarCiclosVencidos();

    /**
     * Servicio que actualiza la deuda presunta de un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param periodoEvaluacion    Periodo de evaluación. Formato YYYY-MM
     * @param tipoAportante        Tipo de aportante
     */
    @GET
    @Path("/actualizarDeudaPresuntaCartera")
    void actualizarDeudaPresuntaCartera(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                        @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodoEvaluacion") String periodoEvaluacion,
                                        @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante);

    /**
     * Servicio que actualiza la deuda presunta de un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param periodoEvaluacion    Periodo de evaluación. Formato YYYY-MM
     * @param tipoAportante        Tipo de aportante
     */
    @GET
    @Path("/actualizarDeudaPresuntaCarteraAsincrono")
    void actualizarDeudaPresuntaCarteraAsincrono(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                 @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("periodoEvaluacion") String periodoEvaluacion,
                                                 @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante);

    /**
     * Servicio que consulta las solicitudes de desafiliacion
     *
     * @return una lista de las solicitudes de desafiliacion
     */
    @GET
    @Path("/consultarHistoricosDesafiliacion")
    public List<SolicitudModeloDTO> consultarHistoricosDesafiliacion(@Context UriInfo uri, @Context HttpServletResponse response);

    /**
     * Servicio que consulta los documentos de desafiliacion
     *
     * @return una lista de los documentos de desafiliacion
     */
    @GET
    @Path("/{numeroRadicacion}/consultarDocumentosDesafiliacion")
    public List<DocumentoDesafiliacionModeloDTO> consultarDocumentosDesafiliacion(@PathParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Servicio que se encarga de actuliazar la activacion del metodo anterior de la Linea de cobro 1
     *
     * @param metodoAnterior parametro del metodo anterior por el cual se filtra en la actualizacion
     */
    @POST
    @Path("/actualizarActivacionMetodoGestionCobro")
    public void actualizarActivacionMetodoGestionCobro(@QueryParam("metodoAnterior") MetodoAccionCobroEnum metodoAnterior);

    /**
     * Servicio que consulta el estado de una solicitud de gestión de cobro
     * (físico o electrónico)
     *
     * @param idCartera       Identificador único del registro en cartera
     * @param tipoAccionCobro Tipo de acción de cobro
     * @return El estado de la solicitud
     */
    @GET
    @Path("/consultarEstadoSolicitudGestionCobro")
    String consultarEstadoSolicitudGestionCobro(@QueryParam("idCartera") Long idCartera,
                                                @QueryParam("tipoAccionCobro") TipoAccionCobroEnum tipoAccionCobro);

    /**
     * Servicio que consulta el total de deuda de un aportante registrado en cartera, agrupada por línea de cobro
     *
     * @param aportanteCarteraDTO Información del aportante
     * @return La lista de deuda, agrupada por línea de cobro
     */
    @POST
    @Path("/consultarCarteraAportante")
    List<CarteraModeloDTO> consultarCarteraAportante(@NotNull AportanteCarteraDTO aportanteCarteraDTO);

    /**
     * Servicio que consulta el detalle de cartera de un aportante, por línea de cobro
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param tipoLineaCobro       Tipo de línea de cobro
     * @return La lista de periodos y la deuda asociada al mismo
     */
    @GET
    @Path("/consultarPeriodosAportanteLineaCobro")
    List<CarteraModeloDTO> consultarPeriodosAportanteLineaCobro(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                                @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                                @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
                                                                @QueryParam("tipoLineaCobro") TipoLineaCobroEnum tipoLineaCobro);

    /**
     * Servicio que consulta la cartera de dependientes asociados a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param periodo              Periodo de deuda
     * @return La lista de cotizantes asociados a un aportante registrado en cartera
     */
    @GET
    @Path("/consultarCarteraCotizantesAportante")
    List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportante(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, @QueryParam("periodo") Long periodo,
            @QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que consulta la cartera de dependientes asociados a un aportante
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @param tipoSolicitante      Tipo de aportante
     * @param periodo              Periodo de deuda
     * @param lineaCobro           Línea de cobro consultada
     * @return La lista de cotizantes asociados a un aportante registrado en cartera
     */
    @GET
    @Path("/consultarCarteraCotizantesAportanteLC")
    List<CarteraDependienteModeloDTO> consultarCarteraCotizantesAportanteLC(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante, @QueryParam("periodo") Long periodo,
            @QueryParam("idCartera") Long idCartera, @QueryParam("lineaCobro") TipoLineaCobroEnum lineaCobro);

    /**
     * Servicio que almacena o actualiza un registro en la tabla <code>CarteraDependiente</code>
     *
     * @param carteraDependienteDTO Información del registro a almacenar
     */
    @POST
    @Path("/guardarCarteraDependiente")
    void guardarCarteraDependiente(@NotNull CarteraDependienteModeloDTO carteraDependienteDTO);

    /**
     * Servicio que almacena un registro en la tabla <code>CarteraNovedad</code>
     *
     * @param carteraNovedadDTO Información del registro a almacenar
     */
    @POST
    @Path("/guardarCarteraNovedad")
    void guardarCarteraNovedad(@NotNull CarteraNovedadModeloDTO carteraNovedadDTO);

    /**
     * Servicio que consulta un documento almacenado en la tabla <code>DocumentoSoporte</code>
     *
     * @param idECM Identificador del archivo en el ECM
     * @return La información del registro almacenado en <code>DocumentoSoporte</code>
     */
    @GET
    @Path("/consultarDocumentoSoporte")
    DocumentoSoporteModeloDTO consultarDocumentoSoporte(@QueryParam("idECM") String idECM);

    /**
     * Servicio que obtiene la lista de aportantes asociados a una solicitud de gestión de cobro físico
     *
     * @param idSolicitudFisico Identificador de la solicitud de gestión de cobro físico
     * @return La lista de aportantes
     */
    @GET
    @Path("/consultarDetalleGestionCobroSolicitud")
    List<DetalleSolicitudGestionCobroModeloDTO> consultarDetalleGestionCobroSolicitud(
            @QueryParam("idSolicitudFisico") Long idSolicitudFisico);

    /**
     * Consulta una lista de empleadores que coincida con el número de identificación
     *
     * @param numeroIdentificacion Numero de identificación de la persona
     * @return Lista de empleadores
     */
    @POST
    @Path("/consultarEmpleadorNumero/{numeroIdentificacion}")
    public List<EmpleadorModeloDTO> consultarEmpleadorNumero(@PathParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Servicio que obtiene los registros de la vista 360 Cartera -> Gestión de cartera
     *
     * @param gestionCarteraDTO Filtros de consulta por aportante
     * @return Objeto con la información de los registros de gestión de cartera
     */
    @POST
    @Path("/consultarGestionCartera360")
    GestionCarteraDTO consultarGestionCartera360(@NotNull GestionCarteraDTO gestionCarteraDTO);

    /**
     * Servicio que obtiene la bitácora de gestión de cartera de un registro por aportante
     *
     * @param carteraDTO Información del registro de cartera
     * @return La lista de registros asociados en la bitácora
     */
    @POST
    @Path("/consultarBitacoraCartera360")
    List<BitacoraCarteraDTO> consultarBitacoraCartera360(@NotNull CarteraModeloDTO carteraDTO);

    /**
     * Servicio que obtiene los registros de la vista 360 Cartera -> Gestión de fiscalización
     *
     * @param gestionCarteraDTO Filtros de consulta por aportante
     * @return Objeto con la información de los registros de gestión de fiscalización
     */
    @POST
    @Path("/consultarGestionFiscalizacion360")
    GestionCarteraDTO consultarGestionFiscalizacion360(@NotNull GestionCarteraDTO gestionCarteraDTO);

    /**
     * Servicio que obtiene los registros de la vista 360 Cartera -> Gestión preventiva
     *
     * @param gestionCarteraDTO Filtros de consulta por aportante
     * @return Objeto con la información de los registros de gestión de fiscalización
     */
    @POST
    @Path("/consultarGestionPreventiva360")
    GestionCarteraDTO consultarGestionPreventiva360(@NotNull GestionCarteraDTO gestionCarteraDTO);

    /**
     * Servicio que almacena un soporte documental en la tabla <code>DocumentoSoporte</code>
     *
     * @param documentoSoporteDTO El documento a almacenar
     * @return La información del registro almacenado
     */
    @POST
    @Path("/guardarDocumentoSoporte")
    DocumentoSoporteModeloDTO guardarDocumentoSoporte(@NotNull DocumentoSoporteModeloDTO documentoSoporteDTO);

    /**
     * Consulta la información de un empleador dado uno de sus identificadores de cartera
     *
     * @param idCartera Identificador único de cartera
     * @return La información del empleador o <code>null</code> cuando no existe un empleador para el id de cartera dado
     */
    @GET
    @Path("/consultarEmpleadorCartera")
    EmpleadorModeloDTO consultarEmpleadorCartera(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que obtiene la bitácora de gestión de preventiva de un registro por aportante
     *
     * @param preventivaDTO Información de la solicitud preventiva
     * @return La lista de registros asociados en la bitácora
     */
    @POST
    @Path("/consultarBitacoraPreventiva360")
    List<BitacoraCarteraDTO> consultarBitacoraPreventiva360(@NotNull SolicitudPreventivaModeloDTO preventivaDTO);

    /**
     * Servicio que obtiene la bitácora de gestión de fiscalización de un registro por aportante
     *
     * @param fiscalizacionDTO Información de la solicitud de fiscalización
     * @return La lista de registros asociados en la bitácora
     */
    @POST
    @Path("/consultarBitacoraFiscalizacion360")
    List<BitacoraCarteraDTO> consultarBitacoraFiscalizacion360(SolicitudFiscalizacionModeloDTO fiscalizacionDTO);

    /**
     * Servicio que se encarga de inactivar automáticamente las exclusioens de cartera no vigentes
     */
    @PUT
    @Path("/actualizarExclusionCarteraInactivacion")
    public void actualizarExclusionCarteraInactivacion();

    /**
     * Servicio que consulta la información de un rol por persona, de acuerdo a un identificador de cartera
     *
     * @param idCartera Identificador de cartera
     * @return La información del rol afiliado
     */
    @GET
    @Path("/consultarRolAfiliadoCartera")
    RolAfiliadoModeloDTO consultarRolAfiliadoCartera(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que determina si un empleador es candidato a expulsión de la CCF
     *
     * @param idCartera Identificador de cartera
     * @return <code>true</code> si el empleador es candidato a expulsión. <code>false</code> en otro caso.
     */
    @GET
    @Path("/validarEmpleadorExpulsion")
    Boolean validarEmpleadorExpulsion(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que consulta la lista de solicitudes preventivas candidatas a cierre por extemporaneidad
     *
     * @return La lista de solicitudes preventivas
     */
    @GET
    @Path("/consultarSolicitudesIndividualesCierrePreventiva")
    List<SolicitudPreventivaModeloDTO> consultarSolicitudesIndividualesCierrePreventiva();

    /**
     * Servicio que consulta la lista de solicitudes preventivas agrupadoras, candidatas a cierre por extemporaneidad
     *
     * @return La lista de solicitudes preventivas
     */
    @GET
    @Path("/consultarSolicitudesAgrupadorasCierrePreventiva")
    List<SolicitudPreventivaAgrupadoraModeloDTO> consultarSolicitudesAgrupadorasCierrePreventiva();

    /**
     * Servicio que determina si un aportante tipo persona es candidato a expulsión de la CCF
     *
     * @param idCartera Número de operación de cartera
     * @return <code>true</code> si es candidato a expulsión. <code>false</code> en otro caso.
     */
    @GET
    @Path("/validarPersonaExpulsion")
    Boolean validarPersonaExpulsion(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que consulta los datos temporales de cartera
     *
     * @param numeroOperacion Número de operación
     * @return Los datos temporales
     */
    @GET
    @Path("/consultarDatoTemporalCartera")
    DatoTemporalCarteraModeloDTO consultarDatoTemporalCartera(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio que almacena los datos temporales de cartera
     *
     * @param numeroOperacion Número de operación
     * @param jsonPayload     Datos temporales a almacenar
     */
    @POST
    @Path("/guardarDatosTemporalesCartera")
    void guardarDatosTemporalesCartera(@QueryParam("numeroOperacion") Long numeroOperacion, @NotNull String jsonPayload);

    /**
     * Servicio que obtiene los datos de un registro de cartera
     *
     * @param numeroOperacion Número de operación de cartera
     * @return Información de cartera
     */
    @GET
    @Path("/consultarInformacionCarteraPorNumeroOperacion")
    CarteraModeloDTO consultarInformacionCarteraPorNumeroOperacion(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Servicio que obtiene el número de operación de cartera, por identificador del registro en cartera
     *
     * @param idCartera Identificador del registro en cartera
     * @return El número de operación
     */
    @GET
    @Path("/consultarNumeroOperacionCartera")
    Long consultarNumeroOperacionCartera(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que consulta un comunicado de gestión de cobro generado automáticamente
     *
     * @param numeroOperacion Número de operación de cartera
     * @param accionCobro     Tipo de acción de cobro
     * @return La información del comunicado
     */
    @GET
    @Path("/consultarDocumentoCartera")
    DocumentoCarteraModeloDTO consultarDocumentoCartera(@QueryParam("numeroOperacion") Long numeroOperacion,
                                                        @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro);

    /**
     * Consulta el metodo para la parameterización de los criterios de gestión de cobro
     *
     * @return MetodoAccionCobroEnum Método en cartera
     */
    @GET
    @Path("/consultarMetodoCriterioGestionCobro")
    public MetodoAccionCobroEnum consultarMetodoCriterioGestionCobro();

    /**
     * Se eliminan los datos temporales de cartera
     *
     * @param numeroOperacion Número de operación
     */
    @POST
    @Path("/eliminarDatoTemporalCartera")
    void eliminarDatoTemporalCartera(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Consulta el id de la entidad (Persona o Empleador) a la que se le enviará el comunicado,
     * a fin de asociarlos para posterior consulta en Vista 360.
     *
     * @param tipoIdentificacion   Tipo de identificación del aportante
     * @param numeroIdentificacion Número de identificación del aportante
     * @return DTO con los id de las entidades asociadas.
     */
    @GET
    @Path("/consultarIdEntidadComunicado")
    public DatosIdEntidadComunicadoDTO consultarIdEntidadComunicado(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Consulta las agendas de cartera y las actividades de cartera que estan visibles para la gestión manual o del aportante
     */
    @GET
    @Path("/consultarAgendasActividadesVisibles")
    public GestionCicloManualDTO consultarAgendasActividadesVisibles(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Consulta las agendas de cartera y las actividades de cartera que corresponden a la solicitud de fiscaslización
     */
    @GET
    @Path("/consultarAgendasActividadesFiscalizacion")
    public ProgramacionFiscalizacionDTO consultarAgendasActividadesFiscalizacion(@QueryParam("idSolicitud") Long idSolicitud);

    /**
     * Actualiza las agendas de cartera y las actividades de cartera que estan visibles para la gestión manual o del aportante a No Visibles
     */
    @PUT
    @Path("/actualizarAgendasActividadesVisibles")
    public void actualizarAgendasActividadesVisibles(@QueryParam("numeroOperacion") Long numeroOperacion);

    /**
     * Actualiza los registros en cartera que se afectan cuando en aportes se cambia el estado de registro de los aportes de No OK a OK
     */
    @PUT
    @Path("/actualizarCarteraEstadoOK")
    public void actualizarCarteraEstadoOK();

    /**
     * Servicio que obtiene el consecutivo de liquidación existente para una cartera
     *
     * @return El consecutivo de liquidación
     */
    @GET
    @Path("/obtenerConsecutivoLiquidacionExistenteCartera")
    public String obtenerConsecutivoLiquidacionExistenteCartera(@QueryParam("idCartera") Long idCartera);

    /**
     * Servicio que obtiene el monto de la deuda presunta para un periodo de evaluación
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoAportante
     * @param periodoEvaluacion
     * @return
     */
    @GET
    @Path("/obtenerDeudaPresuntaCartera")
    public BigDecimal obtenerDeudaPresuntaCartera(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
                                                  @QueryParam("numeroIdentificacion") String numeroIdentificacion,
                                                  @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante,
                                                  @QueryParam("periodoEvaluacion") String periodoEvaluacion);

    /**
     * Servicio que guarda parametros del tiempo de proceso de cartera
     *
     * @param procesoCartera
     */
    @POST
    @Path("/guardarTiempoProcesoCartera")
    public void guardarTiempoProcesoCartera(TiempoProcesoCartera procesoCartera);

    @GET
    @Path("/exportarDetalleAportesDeudaAntigua")
    public List<AportanteGestionManualDTO> exportarDetalleAportesDeudaAntigua();

    /**
     * Servicio que se encarga de guardar o actualizar un nuevo ciclo de aportante.
     *
     * @param ciclo de aportante a guardar.
     * @return ciclo almancenado.
     */
    @POST
    @Path("/guardarCicloAportante")
    public CicloAportanteModeloDTO guardarCicloAportante(CicloAportanteModeloDTO ciclo);

    /**
     * Método que obtiene la acción siguiente a ejecutarse despues de un convenio de pago anulado o una exclusión que se inactivo
     * si cambia se encuentra que cartera debe cerrar una acción transitoria este metodo persiste la información relacionada
     *
     * @param CarteraModeloDTO
     */
    @POST
    @Path("/obtenerAccionCobroSiguiente")
    public TipoAccionCobroEnum obtenerAccionCobroSiguiente(CarteraModeloDTO carteraModeloDTO);

    /**
     * Servicio que consulta todos los registros de cartera.
     *
     * @param idPersona
     * @param tipoSolicitante
     * @return lista con todas las carteras.
     */
    @POST
    @Path("/consultarCarteraPersona")
    public List<CarteraModeloDTO> consultarCarteraPersona(@QueryParam("idPersona") Long idPersona,
                                                          @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

    /**
     * Servicio que se encarga de consultar las exclusiones de cartera que estan por inactivar
     */
    @GET
    @Path("/consultarExclusionCarteraPorInactivar")
    public List<ExclusionCarteraDTO> consultarExclusionCarteraPorInactivar();

    @GET
    @Path("/actualizarDeudaRealPorIdCartera")
    public void actualizarDeudaRealPorIdCarteraServices(@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/ConsultarGestionCarteraAportante")
    List<CarteraAportantePersonaDTO> ConsultarGestionCarteraAportante(@QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("tipoLineaCobro") String tipoLineaCobro);

    @POST
    @Path("/InsertarPersonaCartera")
    public void InsertarPersonaCartera(@NotNull PersonaModeloDTO personaModeloDTO);

    @POST
    @Path("/InsertarCarteraAportante")
    public Long InsertarCarteraAportante(@NotNull CarteraModeloDTO carteraModeloDTO);

    @POST
    @Path("/InsertarCarteraAgrupadora")
    public void InsertarCarteraAgrupadora(@QueryParam("numeroOperacion") Long numeroOperacion,
                                          @QueryParam("idCartera") Long idCartera);

    @GET
    @Path("/ConsultarEstadoAportante")
    public String ConsultarEstadoAportante(@QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/ConsultarNumeroOperacionAgrupacion")
    public Long ConsultarNumeroOperacionAgrupacion();

    @GET
    @Path("/ConsultarCarteraLineaCobro")
    public List<FirmezaDeTituloDTO> ConsultarCarteraLineaCobro();

    @GET
    @Path("/ConsultarCarteraLineaDeCobro2C")
    public List<Object[]> ConsultarCarteraLineaDeCobro2C();

    @GET
    @Path("/ConsultarNumeroTipoIdPersona")
    public FiltroIdPersonaDTO ConsultarNumeroTipoIdPersona(@NotNull @QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/consultarFirmezaTituloBitacora")
    public String consultarFirmezaTituloBitacora(@NotNull @QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/consultarCarteraBitacoraId")
    public Long consultarCarteraBitacoraId(@QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/actualizarExclusionCarteraInactivar")
    public void actualizarExclusionCarteraInactivar(@QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/enviarComunicadoH2C2ToF1C6")
    List<AportanteAccionCobroDTO> enviarComunicadoH2C2ToF1C6(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro);

    @GET
    @Path("/enviarComunicadoExpulsionH2C2ToF1C6")
    List<AportanteAccionCobroDTO> enviarComunicadoExpulsionH2C2ToF1C6(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro);

    @GET
    @Path("/obtenerAportantesParaExpulsionPorIds")
    List<AportanteAccionCobroDTO> obtenerAportantesParaExpulsionPorIds(@QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @QueryParam("idPersonasAProcesar") List<Long> idPersonasAProcesar);


    @GET
    @Path("/consultarActividadCarIdNumeroIdentificacion")
    String consultarActividadCarIdNumeroIdentificacion(@QueryParam("carId") Long carId, @QueryParam("perNumeroIdentificacion") String perNumeroIdentificacion);

    @GET
    @Path("/ConsultarDiasActaGeneracion")
    Long ConsultarDiasActaGeneracion();

    @GET
    @Path("/ConsultarFechaNotificacionBitacora")
    Long ConsultarFechaNotificacionBitacora(@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @POST
    @Path("/consultarTipoNovedadesId")
    List<CarteraNovedadModeloDTO> consultarTipoNovedadesId(@NotNull ConsultarNovedadesIdDTO consultarNovedadesIdDTO);

    @GET
    @Path("/ConsultarNumeroDeOperacion")
    Long ConsultarNumeroDeOperacion(@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/actualizarDeudaRealDeudaPresunta")
    void actualizarDeudaRealDeudaPresunta(@QueryParam("deudaReal") BigDecimal deudaReal,
                                          @QueryParam("deudaPresunta") BigDecimal deudaPresunta,
                                          @QueryParam("idCarteraDependiente") Long idCarteraDependiente,
                                          @QueryParam("idCartera") Long idCartera);

    @POST
    @Path("/guardarCotizateDependiente")
    void guardarCotizateDependiente(@NotNull List<CarteraDependienteModeloDTO> carteraDependienteModeloDTO);

    @GET
    @Path("/documentoFiscalizacionData")
    List<Object[]> documentoFiscalizacionData(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion);


    @GET
    @Path("/consultarBitacoraPersona")
    BitacoraCarteraDTO consultarBitacoraPersona(@QueryParam("perId") Long perId);


    @POST
    @Path("/DocumentosSeguimiento/Gestion")
    DocumentosSeguimientoGestionDTO createDocumentosSeguimientoGestion(DocumentosSeguimientoGestionDTO documentosSeguimientoGestionDTO);

    @GET
    @Path("/find/DocumentosSeguimiento/Gestion")
    List<DocumentosSeguimientoGestionDTO> findDocumentosSeguimientoGestion(@QueryParam("id_operacion") Long idOperacion);

    @POST
    @Path("/DocumentosSeguimiento/Novedades")
    DocumentosSeguimientoNovedadesListDTO createDocumentosSeguimientoNovedades(DocumentosSeguimientoNovedadesListDTO documentosSeguimientoNovedadesDTO);

    @GET
    @Path("/find/DocumentosSeguimiento/Novedades")
    List<DocumentosSeguimientoNovedadesDTO> findDocumentosSeguimientoNovedades(@QueryParam("id_operacion") Long idOperacion);

    @POST
    @Path("/DocumentosSeguimiento/ConveniosPago")
    DocumentosSeguimientoConveniosPagoDTO createDocumentosSeguimientoConveniosPago(DocumentosSeguimientoConveniosPagoDTO documentosSeguimientoConveniosPagoDTO);

    @GET
    @Path("/find/DocumentosSeguimiento/ConveniosPago")
    List<DocumentosSeguimientoConveniosPagoDTO> findDocumentosSeguimientoConveniosPago(@QueryParam("id_operacion") Long idOperacion);

    @GET
    @Path("/consultar/lineaCobroUno/moraPacial")
    List<CarteraModeloDTO> consultarLineaCobroMoraParcial(@Context UriInfo uri, @Context HttpServletResponse response,
                                                          @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/consultar/exclusionCarteraActivaMora")
    List<CarteraModeloMoraParcialDTO> consultarExclusionCarteraActivaMora(@Context UriInfo uri, @Context HttpServletResponse response, @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion);

        /**
     * Servicio encargado de guardar la bitacora de cartera
     *
     * @param tipoIdentificacion, bitacoraCartera a crear
     * @param numeroIdentificaion, bitacoraCartera a crear
     * @param numeroRadicacion, bitacoraCartera a crear
     */
    @GET
    @Path("/ConsultarBitacoraPersonaRadicado")
    public BitacoraCarteraDTO ConsultarBitacoraPersonaRadicado(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,@NotNull @QueryParam("numeroIdentificaion") String numeroIdentificaion, @NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);

    @GET
    @Path("/ConsultarBitacoraAActualizarPersonaRadicado")
    public List<BitacoraCarteraDTO> consultarBitacoraAActualizarPersonaRadicado(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,@NotNull @QueryParam("numeroIdentificaion") String numeroIdentificaion, @NotNull @QueryParam("numeroRadicacion") String numeroRadicacion);


    @GET
    @Path("/procesoValidacionCarteraPrescrita")
    public void procesoValidacionCarteraPrescrita();

    @GET
    @Path("/consultarBitacoraPrescritaGenerarLiquidacion")
    public Boolean consultarBitacoraPrescritaGenerarLiquidacion(@QueryParam("idPersona") Long idPersona);

    @GET
    @Path("/consultarActividadBitacoraNumeroOperacion")
    public String consultarActividadBitacoraNumeroOperacion(@QueryParam("numeroOperacion") String numeroOperacion, @QueryParam("actividad") String actividad);

    @POST
    @Path("/exportarArchivoExclusionMoraParcial")
    public Response exportarArchivoExclusionMoraParcial(
        DatosArchivoCargueExclusionMoralParcialDTO DatosArchivoCargueExclusionMoralParcial, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

    @POST
    @Path("/exportarArchivoExclusionMoraParcialExclusion")
    public Response exportarArchivoExclusionMoraParcialExclusion(
        DatosArchivoCargueExclusionMoralParcialDTO DatosArchivoCargueExclusionMoralParcial, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

}
