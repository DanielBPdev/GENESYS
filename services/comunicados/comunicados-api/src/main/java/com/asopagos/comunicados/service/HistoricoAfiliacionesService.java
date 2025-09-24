package com.asopagos.comunicados.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.asopagos.comunicados.dto.HistoricoAfiliacionEmpleador360DTO;
import com.asopagos.comunicados.dto.HistoricoEstadoEmpleador360DTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que define los servicios para la consulta de historicos para afiliaciones<br/>
 * <b>Historia de Usuario:</b> VISTAS 360 <br/>
 *
 * @author Steven Quintero González <a href="mailto:squintero@heinsohn.com.co"> squintero@heinsohn.com.co</a>
 */
@Path("historicoAfiliaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HistoricoAfiliacionesService {

    /**
     * Servicio encargado de consultar el historico de afliaciones realizadas por un empleador en específico
     * 
     * @param tipoIdEmpleador
     *          tipo de identificación del empleador.
     *          
     * @param numeroIdEmpleador
     *          número de identificación del empleador.
     *          
     * @return List<HistoricoAfiliacionEmpleador360DTO> con los registros encontrados.
     * 
     * @author squintero
     */
    @GET
    @Path("/consultarHistoricoAfiliacionesEmpleador")
    public List<HistoricoAfiliacionEmpleador360DTO> consultarHistoricoAfiliacionesEmpleador(
                @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
                @QueryParam("numeroIdEmpleador") String numeroIdEmpleador); 
    
    /**
     * Servicio encargado de consultar el historico de estados para un empleador
     * 
     * @param tipoIdEmpleador
     *          tipo de identificación del empleador.
     *          
     * @param numeroIdEmpleador
     *          numero de identificación del empleador.
     *          
     * @param tipoAportante
     *          tipo del aportante.
     *          
     * @return List<HistoricoEstadoEmpleador360DTO> con los registros encontrados.
     * 
     * @author squintero
     */
    @GET
    @Path("/consultarHistoricoEstadosEmpleador")
    public List<HistoricoEstadoEmpleador360DTO> consultarHistoricoEstadosEmpleador(
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador,
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador,
            @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante);
    
}
