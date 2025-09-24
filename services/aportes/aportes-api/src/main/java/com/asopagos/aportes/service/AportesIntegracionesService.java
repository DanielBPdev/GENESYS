package com.asopagos.aportes.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.aportes.dto.DatosCotizanteIntegracionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Interface que define los métodos de negocio relacionados
 * con las acciones de registro, consulta o relación de aportes
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Path("externalAPI/aportes")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesIntegracionesService {

    /**
     * Servicio que se encarga de consultar el último aporte, con los datos de identificación del aportante
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del aportante
     * @param numeroIdentificacion
     *        Número de identificación del aportante
     * @param numeroIdentificacionCotizante
     *        Número de identificación del cotizante
     * 
     * @return Datos del aportante
     */
    @Path("/consultarUltimoAporteAportante")
    @GET
    public DatosAportanteDTO consultarUltimoAporte(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante);

    /**
     * Servicio que se encarga de consultar los tres últimos registros de aportes, con los datos de identificación del aportante y rango de
     * periodos
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del aportante
     * @param numeroIdentificacion
     *        Número de identificación del aportante
     * @param numeroIdentificacionCotizante
     *        Número de identificación del cotizante
     * @param periodoInicio
     *        Valor Por defecto: Periodo anterior al periodo de la fecha actual
     * @param periodoFin
     *        Valor por defecto: Periodo actual
     * @return Datos del aportante
     */
    @Path("/consultarHistoricoAporteAportante")
    @GET
    public DatosAportanteDTO consultarHistoricoAporte(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante,
            @QueryParam("periodoInicio") String periodoInicio, @QueryParam("periodoFin") String periodoFin);

    /**
     * Servicio que se encarga de consultar el ultimo aporte relacionado a un cotizante, con los datos de identificación del cotizante
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del cotizante
     * @param numeroIdentificacion
     *        Número de identificación del cotizante
     * @param numeroIdentificacionAportante
     *        Número de identificación del aportante
     * @return Datos del cotizante
     */
    @Path("/consultarUltimoAporteCotizante")
    @GET
    public DatosCotizanteIntegracionDTO consultarUltimoAporteCotizantes(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante);

    /**
     * Servicio que se encarga de consultar los tres últimos registros de aportes relacionados a un cotizante, con los datos de
     * identificación del cotizante y rango de periodos
     * 
     * @param tipoIdentificacion
     *        Tipo de identificación del cotizante
     * @param numeroIdentificacion
     *        Número de identificación del cotizante
     * @param numeroIdentificacionAportante
     *        Número de identificación del aportante
     * @param periodoInicio
     *        Valor Por defecto: Periodo anterior al periodo de la fecha actual
     * @param periodoFin
     *        Valor por defecto: Periodo actual
     * @return Datos del cotizante
     */
    @Path("/consultarHistoricoAporteCotizante")
    @GET
    public DatosCotizanteIntegracionDTO consultarHistoricoAporteCotizantes(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @QueryParam("numeroIdentificacion") String numeroIdentificacion,
            @QueryParam("numeroIdentificacionAportante") String numeroIdentificacionAportante,
            @QueryParam("periodoInicio") String periodoInicio, @QueryParam("periodoFin") String periodoFin);

}
