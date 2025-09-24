package com.asopagos.afiliaciones.empleadores.composite.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.RadicarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarInformacionSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarResultadosProductoNoConformeDTO;
import com.asopagos.dto.AnalizarSolicitudAfiliacionDTO;
import com.asopagos.dto.modelo.DetalleAportesFuturosDTO;
import com.asopagos.dto.modelo.EncabezadoAportesFuturosDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.validation.annotation.ValidacionCreacion;
import com.asopagos.dto.ActivacionEmpleadorDTO;


/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados con la gestión de empleadores <b>Módulo:</b> Asopagos -
 * transversal<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Jerson Zambrano <a href="jzambrano:jzambrano@heinsohn.com.co"> jzambrano</a>
 *
 * @author <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon</a>
 */
@Path("solicitudAfiliacionEmpleador")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionEmpleadoresCompositeService {

	/**
     * Método encargado de iniciar una nueva instancia del proceso en el BPM
     * @param inDTO
     * @param userDTO
     * @return 
     */
    @POST
    @Path("afiliacionEmpleadoresPresencial/iniciar")
    public Long iniciarProcesoAfliliacionEmpleadoresPresencial(ProcesoAfiliacionEmpleadoresPresencialDTO inDTO, @Context UserDTO userDTO);

    @POST
    @Path("afiliacionEmpleadoresPresencial/iniciarSat")
    public Long iniciarProcesoAfliliacionEmpleadoresPresencialSat(ProcesoAfiliacionEmpleadoresPresencialDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que procesa el resultado de la verificación de información de la 
     * solicitud de afiliación en el back
     * <b>Historia de Usuario:</b> 111-091 Verificar información solicitud
     * 
     * @param inDTO 
     * @param userDTO 
     */
    @POST
    @Path("verificarInformacionSolicitud")    
    public void verificarInformacionSolicitud(VerificarInformacionSolicitudDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que procesa el análisis de una solicitud de afiliación escalada
     * <b>Historia de Usuario:</b> 111-070 Analizar solicitud afiliación
     * 
     * @param inDTO 
     * @param userDTO 
     */
    @POST
    @Path("analizarSolicitudAfiliacion")    
    public void analizarSolicitudAfiliacion(
            AnalizarSolicitudAfiliacionDTO inDTO, @Context UserDTO userDTO); 
    /**
    * Método que getiona el producto no conforme subsanable
    * <b>Historia de Usuario:</b> 111-092 Gestionar producto no conforme 
    * subsanable solicitud afiliación empleador (front)
    * 
     * @param userDTO
    * @param inDTO 
    */
   @POST
   @Path("gestionarProductoNoConformeSubsanable")    
   public void gestionarProductoNoConformeSubsanable(
           GestionarProductoNoConformeSubsanableDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que procesa la radicación de la solicitud de afiliación.
     * <b>Historia de Usuario:</b> 111-066 Radicar solicitud afiliación
     * 
     * @param inDTO
     * @param userDTO
     * @return 
     * @throws IOException Lanzada en caso de error convirtiendo los datos temporales de la afiliación
     */
    @POST
    @Path("radicarSolicitudAfiliacion")    
    public Map<String, Object> radicarSolicitudAfiliacionYActivarEmpleador(
            @NotNull @Valid @ValidacionCreacion RadicarSolicitudAfiliacionDTO inDTO, @Context UserDTO userDTO) throws IOException;
    
    /**
     * Método que procesa la asignación de la solicitud de afiliación.
     * <b>Historia de Usuario:</b> 111-0332 Asignar solicitud afiliación
     * 
     * @param inDTO
     * @param userDTO
     */
    @POST
    @Path("asignarSolicitudAfiliacion")    
    public void asignarSolicitudAfiliacionEmpleador(
            @NotNull @Valid AsignarSolicitudAfiliacionDTO inDTO, @Context UserDTO userDTO);    

    /**
     * Método que permite terminar la tarea para el usuario recibido
     * <b>Historia de Usuario:</b> 111-093 Verificar los resultados del producto 
     * no conforme del back
     * 
     * @param idTarea
     * @param userDTO
     * @param inDTO 
     */
    @POST
    @Path("verificarResultadosProductoNoConforme/{idTarea}/terminar")    
    public void verificarResultadosProductoNoConforme(@PathParam("idTarea") Long idTarea,
            VerificarResultadosProductoNoConformeDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Metodo encargado de registrar una persona en lista de especial revision
     * 
     * @param listaEspecialRevisionDTO,
     *            dto que contiene los datos a registrar
     * @return retorna el id del registro creado
     */
    @POST
    @Path("/registarEmpleadorListaEspecial")
    public Integer registrarEmpleadorEnListaEspecialRevision(ListaEspecialRevisionDTO listaEspecialRevisionDTO,
            @Context UserDTO userDTO);

    /**
     * Servicio encargado de consultar la ListaEspecialRevision de manera
     * dinamica
     * 
     * @param tipoIdentificacion,
     *            tipo de identificacion
     * @param numeroIdentificacion,
     *            número de identificacion
     * @param digitoVerificacion,
     *            digito de verificacion
     * @param fechaInicio,
     *            fecha Inicio
     * @param fechaFin,
     *            fecha fina
     * @param nombreEmpleador,nombre
     *            el empleador (razoSocial)
     * @return retorna el DTO ListaEspecialRevisionDTO
     */
    @GET
    @Path("/consultarEmpleadorListaEspecialRevision")
    public List<ListaEspecialRevisionDTO> consultarEmpleadorListaEspecialRevision(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("digitoVerificacion") Byte digitoVerificacion, @QueryParam("fechaInicioInclusion") Long fechaInicio,
            @QueryParam("fechaFinInclusion") Long fechaFin, @QueryParam("nombreEmpleador") String nombreEmpleador);

    /**
     * Servicio encargado de cambiar el estado del registro de la lista
     * 
     * @param tipoIdentificacion,
     *            tipo de identificacion
     * @param numeroIdentificacion,
     *            numero de identificacion
     * @param digitoVerificacion,
     *            digito de verificacion
     * @param actualizacionEstadoListaEspecialDTO
     * @param userDTO
     */
    @PUT
    @Path("/cambiarEstadoEmpleadorRegistroListaEspecial")
    public void cambiarEstadoEmpleadorRegistroLista(ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO, @Context UserDTO userDTO);

    /**
     * Método que procesa la radicación de la solicitud de afiliación y de generar y guardar el dato temporal del comunicado
     * <b>Historia de Usuario:</b> 111-066 Radicar solicitud afiliación
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param inDTO
     * @param userDTO
     * @return Mapa con el jsonPayload del dato temporal del comunicado
     * @throws IOException Lanzada en caso de error convirtiendo los datos temporales de la afiliación
     */
    @POST
    @Path("radicarSolicitudAfiliacionComunicado")    
    public Map<String, Object> radicarSolicitudAfiliacionComunicado (@NotNull @Valid @ValidacionCreacion RadicarSolicitudAfiliacionDTO inDTO, @Context UserDTO userDTO) throws IOException;
    /**
     * Método que procesa el análisis de una solicitud de afiliación escalada y de generar y guardar el dato temporal del comunicado
     * <b>Historia de Usuario:</b> 111-070 Analizar solicitud afiliación
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param inDTO 
     * @param userDTO
     * @return Mapa con el jsonPayload del dato temporal del comunicado
     */
        
    @POST
    @Path("analizarSolicitudAfiliacionComunicado")    
    public Map<String, Object> analizarSolicitudAfiliacionComunicado(@NotNull AnalizarSolicitudAfiliacionDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que procesa el resultado de la verificación de información de la 
     * solicitud de afiliación en el back y de generar y guardar el dato temporal del comunicado
     * <b>Historia de Usuario:</b> 111-091 Verificar información solicitud
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param inDTO 
     * @param userDTO 
     * @return Mapa con el jsonPayload del dato temporal del comunicado
     */
    @POST
    @Path("verificarInformacionSolicitudComunicado")    
    public Map<String, Object> verificarInformacionSolicitudComunicado(@NotNull VerificarInformacionSolicitudDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que permite terminar la tarea para el usuario recibido
     * <b>Historia de Usuario:</b> 111-093 Verificar los resultados del producto 
     * no conforme del back y de generar y guardar el dato temporal del comunicado
     * 
     * @author <a href="mailto:fhoyos@heinsohn.com.co"> Francisco Alejandro Hoyos Rojas</a>
     * @param idTarea
     * @param userDTO
     * @param inDTO 
     * @return Mapa con el jsonPayload del dato temporal del comunicado
     */
    @POST
    @Path("verificarResultadosProductoNoConformeComunicado/{idTarea}/terminar")    
    public Map<String, Object> verificarResultadosProductoNoConformeComunicado(@NotNull @PathParam("idTarea") Long idTarea,
            @NotNull VerificarResultadosProductoNoConformeDTO inDTO, @Context UserDTO userDTO);
    
    @GET
    @Path("consultarEncabezadoAportesFuturos")    
    public List<EncabezadoAportesFuturosDTO> consultarEncabezadoAportesFuturos( 
            @QueryParam("fechaInicio") Long fechaInicio, 
            @QueryParam("fechaFin") Long fechaFin, 
            @QueryParam("antiguedadRecaudo") Long antiguedadRecaudo, 
            @QueryParam("tipoEntidad") String tipoEntidad
    ) throws IOException;
    
    
    @GET
    @Path("consultarDetalleAportesFuturos")    
    public List<DetalleAportesFuturosDTO> consultarDetalleAportesFuturos(
            @QueryParam("idEncabezado") String idEncabezado 
    ) throws IOException;

    @POST
    @Path("/creacionUsuriosEmpresasMasivos")
    public List<String> creacionUsuriosEmpresasMasivos();


    @POST
    @Path("/crearSolicitudAfiliacionEmpleadorAportes")
    void crearSolicitudAfiliacionEmpleadorAportes(
        ActivacionEmpleadorDTO datosReintegro);
    
}