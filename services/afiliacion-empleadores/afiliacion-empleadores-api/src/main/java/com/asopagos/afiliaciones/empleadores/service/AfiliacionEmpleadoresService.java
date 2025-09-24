package com.asopagos.afiliaciones.empleadores.service;

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
import com.asopagos.afiliaciones.empleadores.dto.RespuestaConsultaSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.dto.SolicitudAfiliacionEmpleadorDTO;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.SolicitudDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de empleadores <b>Módulo:</b> Asopagos - transversal<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author Jerson Zambrano
 *         <a href="jzambrano:jzambrano@heinsohn.com.co"> jzambrano</a>
 *
 * @author <a href="mailto:jopinzon@heinsohn.com.co"> jopinzon</a>
 */
@Path("solicitudAfiliacionEmpleador")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionEmpleadoresService {

    /**
     * <b>Descripcion</b> Método que se encarga de Crear una solictid de
     * afiliacion empleadores
     * <code>solAfiliacionEmpleador es la solicitud de afiliacion empleador</code>
     *
     * @param solAfiliacionEmpleador
     *        Solicitud de creacion empleador a crear en la base de datos
     * @return identificador de la solicitud afiliacion empleador
     */
    @POST
    @Path("")
    public Long crearSolicitudAfiliacionEmpleador(SolicitudAfiliacionEmpleador solAfiliacionEmpleador);

    @POST
    @Path("/crearSolicitudAfiliacionEmpleadorCeroTrabajadores")
    public Boolean crearSolicitudAfiliacionEmpleadorCeroTrabajadores(SolicitudAfiliacionEmpleador solAfiliacionEmpleador);

    /**
     * <b>Descripcion</b>Metodo que se encarga de consultar una solictid de
     * afiliacion empleadores
     * <code>idSolicitudAfiliacionEmpleador, es con el cual vamos a buscar el solicitud en la base de datos
     * </code>
     *
     * @param idSolicitudAfiliacionEmpleador,
     *        id del solicitud de afiliacion empleador a consultar
     *
     * @return retorna una lista de los id de los socios guardados
     *
     */
    @GET
    @Path("/{idSolicitudAfiliacionEmpleador}")
    public SolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador(
            @PathParam("idSolicitudAfiliacionEmpleador") Long idSolicitudAfiliacionEmpleador);

    /**
     * <b>Descripcion</b>Metodo que se encarga de actualizar la solicitud de
     * afiliacion del empleador
     * <code>idSolicitudAfiliacionEmpleador, es con el cual se busca la solicitud empleador en la base de datos </code>
     *
     * @param idSolicitudAfiliacionEmpleador,
     *        id del solicitud de afiliacion empleador a consultar
     * @param solAfiliacionEmpleador,
     *        solicitud de afiliacion empleador
     */
    @PUT
    @Path("/{idSolicitudAfiliacionEmpleador}")
    public void actualizarSolicitudAfiliacionEmpleador(@PathParam("idSolicitudAfiliacionEmpleador") Long idSolicitudAfiliacionEmpleador,
            SolicitudAfiliacionEmpleador solAfiliacionEmpleador);

    /**
     * <b>Descripcion</b>Metodo que se encarga de actualizar el estado de la
     * socilictud del empleador
     *
     * @param idSolicitudAfiliacionEmpleador,
     *        identificador que permite buscar la solicitud
     * @param estado
     *        Referencia al estado al cual se va actualizar la solicitud de
     *        afiliacion del empleador
     */
    @PUT
    @Path("/{idSolicitudAfiliacionEmpleador}/estado")
    public void actualizarEstadoSolicitudAfiliacion(@PathParam("idSolicitudAfiliacionEmpleador") Long idSolicitudAfiliacionEmpleador,
            EstadoSolicitudAfiliacionEmpleadorEnum estado);

    /**
     * Consulta las Solicitudes pendientes de aprobación por consejo
     *
     * @param fechaInicio,
     *        fecha inicial de radicacion de solicitudes de afiliacion empleador
     * @param fechaFin,
     *        fecha final de radicacion de solicitudes de afiliacion empleador
     * @return lista de solicitudes de afiliación de empleador pendientes de
     *         aprobación de consejo
     */
    @GET
    @Path("/pendientesAprobacionConsejo")
    public List<SolicitudAfiliacionEmpleador> consultarPendientesAprobacionConsejo();
    
    /**
     * Consultar  empresa Descentralizada por numero de 
     * NIT con serial
     * 
     */
    @GET
    @Path("/consultarRegistroDescentralizada")
    public List<PreRegistroEmpresaDesCentralizada> ConsultarRegistrodes(@QueryParam("numeroIdentificacion") String numeroIdentificacion);

    /**
     * Actualiza las solicitudes con la fecha de aprobación por consejo y el
     * numero administrativo
     *
     * @param solicitudes,
     *        listado de identificadores de solicitud
     * @param numeroActoAdministrativo,
     *        numero del acto administrativo
     * @param fechaAprobacionConsejo,
     *        fecha de aprobacion del consejo
     */
    @PUT
    @Path("/registrarAprobacionConsejo")
    public void registrarAprobacionConsejo(@NotNull List<Long> solicitudes,
            @NotNull @QueryParam("numeroActoAdministrativo") String numeroActoAdministrativo,
            @NotNull @QueryParam("fechaAprobacionConsejo") Long fechaAprobacionConsejo);

    /**
     * Fitra solicitudes de afiliación por tipoIdentificación, número de
     * identificación y número de radicado
     * 
     * @param tipoIdentificacion,
     *        tipo de identificacion
     * @param numeroIdentificacion,
     *        numero de identificacion
     * @param numeroRadicado,
     *        numero de radicado
     * @param digitoVerificacion,
     *        digito de verificacion
     * @return un listado de respuestas a la consulta de la solcitud
     */
    @GET
    @Path("/consultarSolicitudAfiliacionEmpleador")
    public List<RespuestaConsultaSolicitudDTO> consultarSolicitud(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("numeroRadicado") String numeroRadicado,
            @QueryParam("digitoVerificacion") Short digitoVerificacion);
    
    /**
     * Método encargado de consultar la solicitud afiliación empleado
     * 
     * @param numeroRadicado
     *        numero de radicado
     * 
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param numeroIdentificacion
     *        numero de identificación
     * 
     * @return objeto con la solicitud afiliacion empleado
     */
    @GET
    public SolicitudAfiliacionEmpleadorDTO consultarSolicitudAfiliEmpleador(@NotNull @QueryParam("numeroRadicado") String numeroRadicado,
            @NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("canalRecepcion") CanalRecepcionEnum canalRecepcion);

    /**
     * <b>Descripcion</b>Metodo que se encarga de consultar una solicitud de
     * afiliacion empleadores por el número de radicado
     *
     * @param numeroRadicado,
     *        número de radicado de la solicitud de afiliacion empleador a consultar
     *
     * @return retorna una solicitud de empleador o null si no se encuentra alguna 
     *      que cumpla con el criterio especificado
     */
    @GET
    @Path("/numeroRadicado/{numeroRadicado}")
    public SolicitudAfiliacionEmpleadorDTO consultarSolicitudAfiliacionEmpleadorPorRadicado(
            @PathParam("numeroRadicado") String numeroRadicado);
    
    /**
     * <b>Descripcion</b>Metodo que se encarga de consultar una solicitud de
     * afiliacion empleadores por id del empleador
     * @param idEmpleador
     * @return
     */
	@GET
	@Path("/consultarSolicitudesEnProceso")
	public List<SolicitudAfiliacionEmpleador> consultarSolicitudesEnProceso(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
			@QueryParam("numeroIdentificacion") String numeroIdentificacion,
			@QueryParam("idEmpleador") Long idEmpleador);

     /**
     * <b>Descripcion</b>Metodo que se encarga de consultar las cajas de compensación
     * @return
     */
    @GET
    @Path("/consultarIdDepartamento")
    public Integer consultarIdDepartamento(@QueryParam("dpto") String departamento);

     /**
     *
     * @param idempleador,
     *        id del empleador de afiliacion empleador a consultar
     */
    @GET
    @Path("/actualizarFechaDesafiliacionEmpleador")
    public void actualizarFechaDesafiliacionEmpleador(@QueryParam("idEmpleador")Long idEmpleador);

    @GET
    @Path("/consultarSolicitudAfiliacionEmpleadorAnteriores")
    public List<SolicitudAfiliacionEmpleador> consultarSolicitudAfiliacionEmpleadorAnteriores(
        @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
        @QueryParam("numeroIdentificacion") String numeroIdentificacion);

    @GET
    @Path("/consultarSolicitudAfiliacionEmpleadorPorInstancia")
    public SolicitudDTO consultarSolicitudAfiliacionEmpleadorPorInstancia(@QueryParam("instanciaProceso") String instanciaProceso);

}
