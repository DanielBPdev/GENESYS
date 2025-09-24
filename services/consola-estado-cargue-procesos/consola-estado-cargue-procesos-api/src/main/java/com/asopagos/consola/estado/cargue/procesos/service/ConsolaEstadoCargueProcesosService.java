package com.asopagos.consola.estado.cargue.procesos.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ConsolaEstadoProcesoDTO;
import com.asopagos.dto.RegistroErroresArchivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;
import com.asopagos.dto.DatosArchivoCargueProcesosReporteDTO;
import com.asopagos.dto.DatosArchivoProcesosProcesosReporteDTO;
import java.io.IOException;
import javax.ws.rs.core.Response;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST la consola de estados
 * cargue de procesos <b>Historia de Usuario:</b> TRA-368
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@Path("consolaEstadoCargueProcesos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ConsolaEstadoCargueProcesosService {

    /**
     * Servicio que se encarga de registrar los procesos de cargue masivos realiazados
     * 
     * @param consolaEstadoCargueProcesoDTO
     */
    @POST
    @Path("/registrarCargueConsola")
    public void registrarCargueConsolaEstado(@Valid ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO);

    @POST
    @Path("/registrarCargueProcesoMasivo")
    public Long registrarCargueProcesoMasivo(@Valid ConsolaEstadoProcesoDTO consolaEstadoProcesoDTO);
    /**
     * Consulta de forma pagina la consola de procesos masivos a partir de los filtros enviados
     * @param tipoProceso
     *        Tipo de proceso masivo
     * @param estado
     *        Estado del proceso masivo
     * @param fechaInicio
     *        Fecha en que inicio el proceso
     * @param fechaFin
     *        Fecha en que finalizo el proceso
     * @param usuario
     *        Usuario que realizo el proceso
     * @param uriInfo
     *        Información para la paginación de la consulta
     * @param response
     *        Información del formato de respuesta
     * @return Lista de registros de la consola o vacio si no se encuentra información asociada a los filtros
     */
    @GET
    @Path("/consultarCargueConsola")
    public List<ConsolaEstadoCargueProcesoDTO> consultarCargueConsolaEstado(
            @NotNull @QueryParam("tipoProceso") TipoProcesoMasivoEnum tipoProceso, @QueryParam("estado") EstadoCargueMasivoEnum estado,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @Context UriInfo uriInfo, @Context HttpServletResponse response);
         
 @GET
 @Path("/consultarUltimoCargueConsolaEstado")
 public ConsolaEstadoCargueProcesoDTO consultarUltimoCargueConsolaEstado(@NotNull @QueryParam("tipoProceso") TipoProcesoMasivoEnum tipoProceso);
     /**
     * Consulta de forma pagina la consola de procesos masivos a partir de los filtros enviados
     * @param tipoProceso
     *        Tipo de proceso masivo
     * @param estado
     *        Estado del proceso masivo
     * @param fechaInicio
     *        Fecha en que inicio el proceso
     * @param fechaFin
     *        Fecha en que finalizo el proceso
     * @param usuario
     *        Usuario que realizo el proceso
     * @param uriInfo
     *        Información para la paginación de la consulta
     * @param response
     *        Información del formato de respuesta
     * @return Lista de registros de la consola o vacio si no se encuentra información asociada a los filtros
     */
    @GET
    @Path("/consultarProcesoConsolaEstado")
    public List<ConsolaEstadoProcesoDTO> consultarProcesoConsolaEstado(
            @NotNull @QueryParam("tipoProceso") TipoProcesosMasivosEnum proceso, @QueryParam("estado") EstadoCargueMasivoEnum estado,
            @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("error") String error, 
            @Context UriInfo uriInfo, @Context HttpServletResponse response);

    /**
     * Consulta de forma pagina la consola de procesos masivos a partir de los filtros enviados
     * @param datosArchivoCargueProcesosReporte
     *        Tipo de proceso masivo
     * @return Lista de registros de la consola o vacio si no se encuentra información asociada a los filtros
     */
    @POST
    @Path("/exportarArchivoCargueConsolaEstado")
    public Response exportarArchivoCargueConsolaEstado(
        DatosArchivoCargueProcesosReporteDTO datosArchivoCargueProcesosReporte, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

            /**
     * Consulta de forma pagina la consola de procesos masivos a partir de los filtros enviados
     * @param datosArchivoProcesosProcesosReporte
     *        Tipo de proceso masivo
     * @return Lista de registros de la consola o vacio si no se encuentra información asociada a los filtros
     */
    @POST
    @Path("/exportarArchivProcesosConsolaEstado")
    public Response exportarArchivProcesosConsolaEstado(
        DatosArchivoProcesosProcesosReporteDTO datosArchivoProcesosProcesosReporte, 
        @Context UriInfo uriInfo, 
        @Context HttpServletResponse response) throws IOException;

    /**
     * Devuelve la informacion del archivo excel de errores
     * @param registroErroresArchivoDTO
     *        Tipo de proceso masivo
     * @return Lista de registros de la consola o vacio si no se encuentra información asociada a los filtros
     */
    @POST
    @Path("/exportarRegistroErroresArchivo")
    public Response exportarRegistroErroresArchivo(RegistroErroresArchivoDTO registroErroresArchivoDTO,
                                                   @Context UriInfo uriInfo, @Context HttpServletResponse response) throws IOException;


    /**
     * Servicio que se encarga actualizar los estados de carga de procesos masivos existentes
     * 
     * @param idCargue
     * @param consolaEstadoCargueProcesoDTO
     */
    @PUT
    @Path("/actualizarCargueConsola/{idCargue}")
    public void actualizarCargueConsolaEstado(@NotNull @PathParam("idCargue") Long idCargue,
            @Valid ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO);


            
    /**
     * Servicio que se encarga actualizar los estados de carga de procesos masivos existentes
     * 
     * @param idCargue
     * @param ConsolaEstadoProcesoDTO
     */
    @PUT
    @Path("/actualizarProcesoConsolaEstado/{IdConsolaEstadoProcesoMasivo}")
    public void actualizarProcesoConsolaEstado(@NotNull @PathParam("IdConsolaEstadoProcesoMasivo") Long IdConsolaEstadoProcesoMasivo,
            @Valid ConsolaEstadoProcesoDTO ConsolaEstadoProcesoDTO);

    /**
     * Consulta el log de error de un registro de la consola
     * @param idConsola
     *        Identificador del registro de consola que contiene el log
     * @param uriInfo
     *        Información de la paginación de la consulta
     * @param response
     *        Información del formato de respuesta
     * @return Lista de hallazgos del archivo cargado
     */
    @GET
    @Path("/consultarLogErrorArchivo")
    public List<ResultadoHallazgosValidacionArchivoDTO> consultarLogErrorArchivo(@QueryParam("idConsola") Long idConsola,
            @Context UriInfo uriInfo, @Context HttpServletResponse response);

    @GET
    @Path("/existeArchivoPorNombre")
    public Boolean existeArchivoPorNombre(@QueryParam("tipoProceso") TipoProcesoMasivoEnum tipoProceso,
            @QueryParam("nombreArchivo") String nombreArchivo);
}
