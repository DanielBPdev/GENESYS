package com.asopagos.aportes.composite.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.Context;
import com.asopagos.rest.security.dto.UserDTO;


/**
 * <b>Descripcion:</b> Clase que contiene la lógica de negocio para el proceso
 * 2.1.2 Aportes manuales <br/>
 * <b>Módulo:</b> Asopagos - 2.1.2 <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> Claudia Milena Marín Hincapié</a>
 */
@Path("externalAPI/aporte")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesIntegracionCompositeService {

    /**
     * Servicio que se encarga de consultar el último aporte, con los datos de identificación del aportante
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del aportante
     * @param numeroIdentificacion
     *        Número de identificación del aportante
     * @return Datos del aportante
     */
    @Path("/obtenerUltimoAporteAportante")
    @GET
    public Response obtenerUltimoAporte(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );

    /**
     * Servicio que se encarga de consultar los tres últimos registros de aportes, con los datos de identificación del aportante y rango de
     * periodos
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del aportante
     * @param numeroIdentificacion
     *        Número de identificación del aportante
     * @param periodoInicio
     *        Valor Por defecto: Periodo anterior al periodo de la fecha actual
     * @param periodoFin
     *        Valor por defecto: Periodo actual
     * @return Datos del aportante
     */
    @Path("/obtenerHistoricoAporteAportante")
    @GET
    public Response obtenerHistoricoAporte(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante,
            @QueryParam("periodoInicio") String periodoInicio, @QueryParam("periodoFin") String periodoFin,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );

    /**
     * Servicio que se encarga de consultar el ultimo aporte relacionado a un cotizante, con los datos de identificación del cotizante
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del cotizante
     * @param numeroIdentificacion
     *        Número de identificación del cotizante
     * @return Datos del cotizante
     */
    @Path("/obtenerUltimoAporteCotizante")
    @GET
    public Response obtenerUltimoAporteCotizantes(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);

    /**
     * Servicio que se encarga de consultar los tres últimos registros de aportes relacionados a un cotizante, con los datos de
     * identificación del cotizante y rango de periodos
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del cotizante
     * @param numeroIdentificacion
     *        Número de identificación del cotizante
     * @param periodoInicio
     *        Valor Por defecto: Periodo anterior al periodo de la fecha actual
     * @param periodoFin
     *        Valor por defecto: Periodo actual
     * @return Datos del cotizante
     */
    @Path("/obtenerHistoricoAporteCotizante")
    @GET
    public Response obtenerHistoricoAporteCotizantes(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("periodoInicio") String periodoInicio, @QueryParam("periodoFin") String periodoFin,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );

}
