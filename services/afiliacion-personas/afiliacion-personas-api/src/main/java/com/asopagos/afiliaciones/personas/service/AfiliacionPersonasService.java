package com.asopagos.afiliaciones.personas.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.ListadoSolicitudesAfiliacionDTO;
import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.dto.SolicitudDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para afiliación de
 * personas <b>Historia de Usuario:</b> HU-TRA-104
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("afiliaciones")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionPersonasService {

    /**
     * Método que permite crear una solicitud de afiliación de personas
     * 
     * @param inAfiliadoDTO,
     *        DTO que contiene la informacion del afiliado a crear la
     *        solicitud
     * @return retorna el id de la solicitud
     */
    @POST
    @Path("solicitudAfiliacionPersona")
    public Long crearSolicitudAfiliacionPersona(AfiliadoInDTO inAfiliadoDTO);

    /**
     * Método que consulta y retorna información de la solicitud de afiliación
     * de personas
     * 
     * @param idSolicitudGlobal,
     *        identificador global de la solicitud
     * @return retorna el DTO de la solicitudAfiliacionPersona
     */
    @GET
    @Path("solicitudAfiliacionPersona/{idSolicitudGlobal}")
    public SolicitudAfiliacionPersonaDTO consultarSolicitudAfiliacionPersona(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal);

    /**
     * 
     * Método que permite actualizar el estado de las solicitudes de afiliación
     * de personas
     * 
     * @param idSolicitudGlobal,
     *        identificador de la solicitud global
     * @param estado,a
     *        actualizar de la solicitud
     */
    @PUT
    @Path("solicitudAfiliacionPersona/{idSolicitudGlobal}/estado")
    public void actualizarEstadoSolicitudAfiliacionPersona(@NotNull @PathParam("idSolicitudGlobal") Long idSolicitudGlobal,
            EstadoSolicitudAfiliacionPersonaEnum estado);

    /**
     * Método que permite crear una solicitud de asociación de personas a
     * entidad pagadora
     * 
     * @param inAfiliadoDTO
     * @return retorna el id de la solicitud asociacion persona entidad pagadora
     */
    @POST
    @Path("solicitudAsociacionPersona")
    public Long crearSolicitudAsociacionPersonaEntidadPagadora(AfiliadoInDTO inAfiliadoDTO);
    
    /**
     * Metodo que permite actualizar la solicitud de afiliacion de la persona
     * 
     * @param idSolicitudGlobal,
     *        identificador global de la solicitud
     * @param solicitudDTO,
     *        solicitud
     */
    @PUT
    @Path("/solicitudAfiliacionPersona/{idSolicitudGlobal}")
    public void actualizarSolicitudAfiliacionPersona(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal, SolicitudDTO solicitudDTO);

    /**
     * Metodo que permite actualizar la solicitud de afiliacion de la persona masivamente
     *
     * @param idSolicitudGlobal,
     *        identificador global de la solicitud
     * @param solicitudDTO,
     *        solicitud
     */
    @PUT
    @Path("/solicitudAfiliacionPersonaMasivas/{idSolicitudGlobal}")
    public void actualizarSolicitudAfiliacionPersonaMasivas(@PathParam("idSolicitudGlobal") Long idSolicitudGlobal, SolicitudDTO solicitudDTO);


    /**
     * Método que consulta la informacion de la solicitud de afiliación de una persona
     * @param numeroIdentificacion,
     *        Número de identificación
     * @param tipoIdentificacion,
     *        Tipo de Identificación
     * @return datos de la solicitud de afiliación persona
     */
    @GET
    @Path("solicitudAfiliacionPersona")
    public ListadoSolicitudesAfiliacionDTO consultarSolicitudAfiliacionPersonaAfiliada(
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
            @QueryParam("estadoSolicitud") EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud,
            @QueryParam("canalRecepcion") CanalRecepcionEnum canalRecepcion,
            @QueryParam("numeroRadicacion") String numeroRadicacion);

    /**
     * Método que permite actualizar una solicitud de asociación de personas a
     * entidad pagadora
     * 
     * @param inDTO, datos de la solicitud
     */
    @POST
    @Path("/solicitudAsociacionPersona/actualiza")
    public void actualizarSolicitudAsociacionPersonaEntidadPagadora(SolicitudAsociacionPersonaEntidadPagadoraDTO inDTO);
    
    /**
     * Método que permite actualizar una solicitud de asociación de personas a
     * entidad pagadora
     * 
     * @param inDTO, datos de la solicitud
     */
    @GET
    @Path("/obtenerAfiliacionesSinInstanciaProceso")
    public List<SolicitudAfiliacionPersonaDTO> obtenerAfiliacionesSinInstanciaProceso();

    @GET
    @Path("/solicitudAfiliacionPersonaRadicacion")
    public ListadoSolicitudesAfiliacionDTO consultarSolicitudAfiliacionPersonaPorRadicacion(
        @QueryParam("canalRecepcion") CanalRecepcionEnum canalRecepcion,
        @QueryParam("numeroRadicacion") String numeroRadicacion
    );

    
    @GET
    @Path("/consultarClasificacionPensionado")
    public Object[] ConsultarClasificacionPensionado(
        @QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion,
        @QueryParam("numeroIdentificacion")String numeroIdentificacion);
        

     @GET
    @Path("/consultarDatosExistenteNoPensionado")
    public Afiliado25AniosDTO ConsultarDatosExistenteNoPensionado(
        @QueryParam("numeroIdentificacion")String numeroIdentificacion,
        @QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion);


    // TPDP 05 Crear nuevo metodo obtenerCarguePensionados25Anios que reciba por parametro un @QueryParam("idCargue") Long idCargue
	// Y retorne el ConsolaEstadoCargueProcesoDTO  (No olvidar import)
    @GET
    @Path("/obtenerCarguePensionados25Anios")
    public ConsolaEstadoCargueProcesoDTO obtenerCarguePensionados25Anios(
        @QueryParam("idCargue") Long idCargue);


    @GET
    @Path("/obtenerCargueTrasladosMasivos")
    public ConsolaEstadoCargueProcesoDTO obtenerCargueTrasladosMasivos(
        @QueryParam("idCargue") Long idCargue);
    

    
    @POST
    @Path("crearSolicitudCargueMultiple/{idCargue}")
    public Long crearSolicitudCargueMultiple(
        @PathParam("idCargue") Long idCargue);

    @POST
    @Path("crearSolicitudAfiliacionPersonaSolo")
    public void crearSolicitudAfiliacionPersonaSolo(
         @QueryParam("rolAfiliado") Long rolAfiliado,
         @QueryParam("solicitudGlobal") Long solicitudGlobal);


}
