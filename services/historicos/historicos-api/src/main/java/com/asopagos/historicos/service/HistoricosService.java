package com.asopagos.historicos.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoRolContactoEnum;
import com.asopagos.historicos.dto.*;


/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con los históricos en el modelo de auditoría de la Caja de compensación.<b>Módulo:</b> Asopagos - Historicos<br/>
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */

@Path("historicos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface HistoricosService {

    /**
     * Servicio que obtiene los id de los roles activos (trabajadores) para un empleador en un periodo.
     * @param startDate
     *        fecha inicio del periodo.
     * @param endDate
     *        fecha fin del periodo.
     * @param idEmpleador
     *        id del empleador.
     * @return lista con los id de los roles.
     */
    @GET
    @Path("/obtenerTrabajadoresActivosPeriodo")
    public List<Long> obtenerTrabajadoresActivosPeriodo(@QueryParam("startDate") Long startDate, @QueryParam("endDate") Long endDate,
            @QueryParam("idEmpleador") Long idEmpleador);

    /**
     * Servicio que obtiene los id de los roles activos (trabajadores) para un empleador en un periodo.
     * @param startDate
     *        fecha inicio del periodo.
     * @param endDate
     *        fecha fin del periodo.
     * @param idEmpleador
     *        id del empleador.
     * @return estado del afiliado.
     */
    @GET
    @Path("/obtenerEstadoTrabajadorPeriodo")
    public EstadoAfiliadoEnum obtenerEstadoTrabajador(@QueryParam("startDate") Long startDate, @QueryParam("endDate") Long endDate,
            @QueryParam("idEmpleador") Long idEmpleador, @QueryParam("idAfiliado") Long idAfiliado,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);

    /**
     * Servicio que obtiene el estado de un empleador en un periodo dado.
     * @param startDate
     *        fecha de inicio del periodo.
     * @param endDate
     *        fecha de fin del periodo.
     * @param idEmpleador
     *        id del empleador.
     * @return estado del empleador.
     */
    @GET
    @Path("/obtenerEstadoEmpleadorPeriodo")
    public EstadoEmpleadorEnum obtenerEstadoEmpleadorPeriodo(@QueryParam("startDate") Long startDate, @QueryParam("endDate") Long endDate,
            @QueryParam("idEmpleador") Long idEmpleador);

    /**
     * Servicio que obtiene el estado de un aportante en un periodo dado.
     * @param startDate
     *        fecha de inicio del periodo.
     * @param endDate
     *        fecha de fin del periodo.
     * @param idAportante
     *        id del empleador.
     * @return estado del empleador.
     */
    @GET
    @Path("/obtenerEstadoAportantePeriodo")
    public EstadoEmpleadorEnum obtenerEstadoAportantePeriodo(@QueryParam("startDate") Long startDate, @QueryParam("endDate") Long endDate,
            @QueryParam("idAportante") Long idAportante, @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante);

   
    /**
     * Servicio que pertemite obtener la ubicación del una persona y si es un empleador se verifica respecto al tipo de ubicación
     * @param idPersona,
     *        id de la persona
     * @param tipoSolicitante,
     *        tipo de solicitante
     * @param tipoUbicacion,
     *        tipo de ubicación
     * @param fechaRevision,
     *        fecha de revisión
     * @return retorna la ubicacionDTO de la persona o del empleador
     */
    @GET
    @Path("/buscarDireccion/{idPersona}")
    public UbicacionDTO buscarDireccion(@NotNull @PathParam("idPersona") Long idPersona,
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            @QueryParam("tipoUbicacion") TipoUbicacionEnum tipoUbicacion, @NotNull @QueryParam("fechaRevision") Long fechaRevision);

    /**
     * Servicio encargado de buscar la dirrección por tipo de ubicación
     * @param idPersona,
     *        id de la persona
     * @param tipoSolicitante,
     *        tipo de solicitante
     * @param tiposUbicacion,
     *        tipos de ubicación de la empresa
     * @param fechaRevision,
     *        fecha de revisión
     * @return retorna la lista de ubicación
     */
    @GET
    @Path("/buscarDireccionPorTipoUbicacion/{idPersona}")
    public List<UbicacionDTO> buscarDireccionPorTipoUbicacion(@NotNull @PathParam("idPersona") Long idPersona,
            @NotNull @QueryParam("tipoSolicitante") TipoSolicitanteMovimientoAporteEnum tipoSolicitante,
            @QueryParam("tiposUbicacion") List<TipoUbicacionEnum> tiposUbicacion, @NotNull @QueryParam("fechaRevision") Long fechaRevision);

    /**
     * Servicio encargado de buscar el historial de ubicaciones de rol contacto empleador
     * @param idPersona,
     *        id de la persona
     * @param tipoRolContactoEmpleador,
     *        tipo de rol contacto a consultar
     * @param fechaRevision,
     *        fecha de revisión del historial
     * @return retorna las ubicaciones dto del rol contacto empleador
     */
    @GET
    @Path("/buscarHistorialUbicacionRolContactoEmpledor/{idPersona}")
    public List<UbicacionDTO> buscarHistorialUbicacionRolContactoEmpledor(@NotNull @PathParam("idPersona") Long idPersona,
            @NotNull @QueryParam("tipoRolContactoEmpleador") TipoRolContactoEnum tipoRolContactoEmpleador,
            @NotNull @QueryParam("fechaRevision") Long fechaRevision);

  
    /**
     * Servicio encargado de consultar el histórico de datos de contacto de una persona para obtener los últimos
     * registros de: - número tel fijo
     *               - número celular
     *               - correo electrónico 
     * 
     * @param tipoIdAfiliado
     *          tipo de identificación de la persona.
     *          
     * @param numeroIdAfiliado
     *          número de identificación de la persona.
     *          
     * @return ContactoPersona360DTO con los datos obtenidos.
     */
    @GET
    @Path("/obtenerHistoricoContactoPersona")
    public ContactoPersona360DTO obtenerHistoricoContactoPersona(
            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado);
    
    /**
     * <b>Descripción:</b>Servicio encargado de buscar los grupos familiares donde la persona dada
     * esté o haya estado relacionada como administrador del susbsidio.
     * 
     * @param numeroIdentificacion
     *          número de identificacion del administrador del subsidio.
     *          
     * @param tipoIdentificacion
     *          tipo de identificación del administrador del subsidio.
     *          
     * @return listado con la información de los grupos familiares relacionados al administrador del
     * subsidio.
     * 
     * @author squintero
     */
    @GET
    @Path("/consultarResumenGruposFamiliares")
    public List<PersonaComoAdminSubsidioDTO> consultarResumenGruposFamiliares(
            @NotNull @QueryParam("numeroIdentificacion")String numeroIdentificacion, 
            @NotNull @QueryParam("tipoIdentificacion")TipoIdentificacionEnum tipoIdentificacion); 
    
    
    /**
     * <b>Descripción:</b>Servicio encargado de buscar los grupos familiares y beneficicarios para los
     * cuales la persona es afiliado principal
     * 
     * @param numeroIdentificacion
     *          número de identificación de la persona afiliada principal.
     *          
     * @param tipoIdentificacion
     *          tipo de identificación de la persona afiliada principal.
     *          
     * @return listado con la información de los grupos familiares relacionados al afiliado principal
     */
    @GET
    @Path("/obtenerGruposFamiliaresAfiPrincipal")
    public List<PersonaComoAfiPpalGrupoFamiliarDTO> obtenerGruposFamiliaresAfiPrincipal(
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoIdentificacion") String tipoIdentificacion);
    
    /**
     * <b>Descripción:</b>Servicio encargado de buscar los grupos familiares y beneficicarios para los
     * cuales la persona es afiliado principal incluyendo el ID de beneficiario
     * 
     * @param numeroIdentificacion
     *          número de identificación de la persona afiliada principal.
     *          
     * @param tipoIdentificacion
     *          tipo de identificación de la persona afiliada principal.
     *          
     * @return listado con la información de los grupos familiares relacionados al afiliado principal
     */
    @GET
    @Path("/obtenerGruposFamiliaresAfiPrincipalID")
    public List<PersonaComoAfiPpalGrupoFamiliarIDDTO> obtenerGruposFamiliaresAfiPrincipalID(
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @NotNull @QueryParam("tipoIdentificacion") String tipoIdentificacion);

    @POST
    @Path("/actualizarEstadisticasGenesys")
    public void actualizarEstadisticasGenesys();

    @GET
    @Path("/obtenerEstadisticasGenesys")
    public HistoricoEstadisticasGenesysDTO obtenerEstadisticasGenesys();
}