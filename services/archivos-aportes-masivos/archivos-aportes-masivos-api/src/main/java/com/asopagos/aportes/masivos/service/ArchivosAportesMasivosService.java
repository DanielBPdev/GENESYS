package com.asopagos.aportes.masivos.service;

import com.asopagos.aportes.masivos.dto.*;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.Map;
import com.asopagos.dto.AnalisisDevolucionDTO;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import com.asopagos.entidades.pila.masivos.*;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.aportes.masivos.dto.DatosRadicacionMasivoAporteDTO;



//ResultadoValidacionArchivoAporteDTO;

/**
 * 
 */
@Path("archivosAportesMasivos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ArchivosAportesMasivosService {

  @GET
  @Path("obtenerSolicitantesMasivo")
  public List<SolicitanteDTO> consultarSolicitanteCorreccionMasivo(
      @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
      @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
      @NotNull @QueryParam("periodo") String periodoAporte);

  @POST
  @Path("validarEstructuraAportesMasivos")
  public List<ResultadoArchivoAporteDTO> validarEstructuraAportesMasivos(
      @NotNull List<ArchivoAportesDTO> archivosAportes,
      @Context UserDTO userDTO) throws Exception;

  @POST
  @Path("validarArchivoAportes")
  public ResultadoArchivoAporteDTO validarArchivoAportes(
      ArchivoAportesDTO archivoAportes, @Context UserDTO userDTO);

  @POST
  @Path("persistirDetallesArchivoAporte/{idArchivoMasivo}")
  public void persistirDetallesArchivoAporte(
      @NotNull @PathParam("idArchivoMasivo") Long idArchivoMasivo,
      List<ResultadoValidacionAporteDTO> aportes);

  @GET
  @Path("consultarAportesADevolver/{numeroRadicado}")
  public List<AnalisisDevolucionDTO> consultarAportesADevolver(
      @NotNull @PathParam("numeroRadicado") String numeroRadicado);


  @GET
  @Path("consultarCabeceraResultadoRecaudoMasivo/{idSolicitud}/")
  public DatosRadicacionMasivoAporteDTO consultarCabeceraResultadoRecaudoMasivo(
    @NotNull @PathParam("idSolicitud") Long idSolicitud);

  @POST
  @Path("crearSolicitudCorreccion")
  public Long crearSolicitudCorreccion(
      PersonaDTO persona, @Context UserDTO userDTO);

  @POST
  @Path("procesarArchivoAportes")
  public Map<String, String> procesarArchivoAportes(Long idArchivoMasivo, @Context UserDTO userDTO);

  @POST
  @Path("radicarSolicitudAportesMasivos")
  public Map<String, String> radicarSolicitudAportesMasivos(@NotNull SolicitudModeloDTO solicitudDTO,
      @Context UserDTO userDTO);

  @DELETE
  @Path("eliminarArchivoMasivo/{idArchivoMasivo}")
  public void eliminarArchivoMasivo(@NotNull @PathParam("idArchivoMasivo") Long idArchivoMasivo);

  /**
   * Servicio que se encarga de ejecutar el armado staging.
   * 
   * @param idTransaccion
   *                      id de la transacción.
   */
  @POST
  @Path("simularAporteMasivo")
  public Map<String, String> simularAporteMasivo(@NotNull @QueryParam("nombreArchivo") String nombreArchivo, @QueryParam("idCuentaBancaria") Long idCuentaBancaria,
      @Context UserDTO userDTO);

  /**
   * Servicio que se encarga de ejecutar el armado staging.
   * 
   * @param idTransaccion
   *                      id de la transacción.
   */
  @POST
  @Path("finalizarAporteMasivo")
  public void finalizarAporteMasivo(@NotNull @QueryParam("nombreArchivo") String nombreArchivo);

  /**
   * Servicio que consulta los hallazgos de archivo masivo
   */

  @GET
  @Path("consultarHallazgosAportes/{idArchivoMasivo}")
  public List<ResultadoHallazgosValidacionArchivoDTO> consultarHallazgosAportes(
      @NotNull @PathParam("idArchivoMasivo") Long idArchivoMasivo);

  @POST
  @Path("validarArchivoDevolucion")
  public ResultadoArchivoAporteDTO validarArchivoDevolucion(ArchivoDevolucionDTO archivoDevolucion,
      @Context UserDTO userDTO);

  @POST
  @Path("verificarEstructuraArchivoDevolucion")
  public ResultadoArchivoAporteDTO verificarEstructuraArchivoDevolucion(InformacionArchivoDTO archivo,
      ArchivoDevolucionDTO archivoDevolucio, @Context UserDTO userDTO);

  /**
   * Servicio que se encarga de guardar la información temporal de una
   * devolución.
   * 
   * @param idSolicitud
   *                        id de la soliciutd asociada.
   * @param aporteManualDTO
   *                        aporte manualDTO.
   */
  @POST
  @Path("/guardarDevolucionMasivaTemporal")
  public void guardarDevolucionTemporalMasiva(@NotNull @QueryParam("idSolicitud") Long idSolicitud,
      DevolucionDTO devolucionDTO);

  @POST
  @Path("/cargarArchivoAportesMasivos")
  public MasivoArchivo cargarArchivoAportesMasivos(MasivoArchivo datosArchivo, @Context UserDTO userDTO);

  @GET
  @Path("/consultarArchivosAportes")
  public List<ArchivoAporteMasivoDTO> consultarArchivosAportes();

  /**
   * Método que realiza la simulación de una solicitud de devolución
   * alamcenada en la tabla temporal
   * 
   * @param idSolicitudGlobal
   *                          Identificador de la solicitud global
   * @return El resultado de la simulación
   */
//   @POST
//   @Path("/simularDevolucionMasiva/{idSolicitudGlobal}")
//   public List<DevolucionDTO> simularDevolucionTemporalMasiva(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

  /**
   * Servicio encargado de consultar un aporte manual temporal.
   * 
   * @param idSolicitud
   *                    id de la solicitud global.
   * @return aporte manual dto.
   */
  @GET
  @Path("/{idSolicitud}/consultarDevolucionMasivaTemporal")
  public List<DevolucionDTO> consultarDevolucionMasivaTemporal(@PathParam("idSolicitud") List<Long> idSolicitud);

  /**
   * Servicio que se encarga de radicar una solicitud de devolucion.
   * 
   * @param solicitudDevolucion
   *                            datos de la solicitud de devolucion.
   */
  @POST
  @Path("/radicarSolicitudDevolucionMasiva")
  public Map<String, String> radicarSolicitudDevolucionMasiva(@NotNull SolicitudModeloDTO solicitudDTO,
      @Context UserDTO userDTO);

  /**
   * Servicio que se encarga de actualizar la solicitud y crear su
   * trazabilidad.
   * 
   * @param proceso
   *                     Indica el proceso de negocio / tipo de solicitud
   * @param idSolicitud
   *                     id de la solicitud global a actualizar
   * @param estado
   *                     estado nuevo de la solicitud
   * @param idComunicado
   *                     id del comunicado
   * @param userDTO
   *                     usuario tomado del contexto
   */
  @POST
  @Path("/{idSolicitud}/actualizarSolicitudTrazabilidad")
  public void actualizarSolicitudTrazabilidad(@PathParam("idSolicitud") Long idSolicitud,
      @QueryParam("proceso") @NotNull ProcesoEnum proceso,
      @QueryParam("estadoSolicitud") @NotNull EstadoSolicitudAporteEnum estado,
      @QueryParam("idComunicado") Long idComunicado, @Context UserDTO userDTO);

  @GET
  @Path("/consultarRecaudoSimulado/{idSolicitud}")
  public List<MasivoSimulado> consultarRecaudoSimulado(@PathParam("idSolicitud") Long idSolicitud);


  @GET
  @Path("/aprobarRecaudoSimulado/{idSolicitud}")
  public void aprobarRecaudoSimulado(@PathParam("idSolicitud") Long idSolicitud, @Context UserDTO userDTO);

  @POST
  @Path("/{idSolicitud}/simularAporteCorreccionMasivo")
  public CorreccionAportanteDTO simularAporteCorreccionMasivo(@PathParam("idSolicitud") Long idSolicitud,
      CorreccionAportanteDTO correccion);

    @POST
    @Path("/simularDevolucionMasiva")
    public void simularDevolucionMasiva(Solicitud solicitud);

    @POST
    @Path("/finalizarDevolucionMasiva")
    public void finalizarDevolucionMasiva(MedioDePagoModeloDTO medioDePagoDTO, @QueryParam("numeroRadicado")String numeroRadicado, @QueryParam("idTarea")Long idTarea,
        @Context UserDTO userDTO);

    @GET
    @Path("/consultarAportesSimuladoDevolucion/{numeroRadicado}")
    public List<AnalisisDevolucionDTO> consultarAportesSimuladoDevolucion(@PathParam("numeroRadicado") String numeroRadicado);


    @GET
    @Path("/{idSolicitud}/finalizarCorreccionMasiva")
    public void finalizarCorreccionMasiva(@PathParam("idSolicitud") Long idSolicitud, @Context UserDTO userDTO);

    @GET
    @Path("/reportes/generarReporteDevolucionDetalle/{numeroRadicado}")
    public Response generarReporteDevolucionDetalle(@PathParam("numeroRadicado") String numeroRadicado);

    @GET
    @Path("/reportes/generarReporteDevolucionResultado/{numeroRadicado}")
    public Response generarReporteDevolucionResultado(@PathParam("numeroRadicado") String numeroRadicado);

    @GET
    @Path("/reportes/getReporteRecaudoSimulado/{idSolicitud}")
    public Response getReporteRecaudoSimulado(@PathParam("idSolicitud") Long idSolicitud) throws IOException;

    @GET
    @Path("/reportes/getDevolucionesSimulado/{numeroRadicado}")
    public Response getDevolucionesSimulado(@PathParam("numeroRadicado") String numeroRadicado);
 
    @GET
    @Path("/{numeroRadicado}/consultarEstadoSimulado")
    public Boolean consultarEstadoSimulado(@PathParam("numeroRadicado") String numeroRadicado);

    /**
     * <b>Descripción:</b>Método que se encarga de realizar la carga automática de los archivos de cruces Aportes
     * @author A_Camelo
     */
    @GET
    @Path("/cargarAutomaticamenteArchivosCrucesAportes")
    public void cargarAutomaticamenteArchivosCrucesAportes();

}