package com.asopagos.aportes.composite.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.aportes.composite.dto.ResultadoCierreDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que contiene la lógica de negocio para el proceso
 * 2.1.5 Cierre de recaudo <br/>
 * <b>Módulo:</b> Asopagos - 2.1.5 <br/>
 *
 * @author <a href="mailto:atoro@heinsohn.com.co"> Angélica Toro Murillo</a>
 */
@Path("cierre")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesCierreCompositeService {
    

    @POST
    @Path("/generarCierre")
    public String generarCierre(@QueryParam("tipoCierre")TipoCierreEnum tipoCierre,@QueryParam("fechaInicio")Long fechaInicio,@QueryParam("fechaFin") Long fechaFin, @Context UserDTO userDTO);
    
    /**
     * Servicio que se encarga de emitir el resultado del analista de aportes.
     * @param resultado dto con la información del resultado.
     * @param userDTO usuario autenticado.
     */
    @POST
    @Path("/emitirResultadoAnalista")
    public void emitirResultadoAnalista(ResultadoCierreDTO resultadoCierre, @Context UserDTO userDTO);
    
    /**
     * Servicio que se encarga de emitir el resultado de un supervisor.
     * @param resultado dto con la información del resultado.
     * @param userDTO usuario autenticado.
     */
    @POST
    @Path("/emitirResultadoSupervisor")
    public void emitirResultadoSupervisor(ResultadoCierreDTO resultadoCierre, @Context UserDTO userDTO);
    
    /**
     * Servicio que se encarga de emitir el resultado de un analista contable.
     * @param resultado dto con la información del resultado.
     * @param userDTO usuario autenticado.
     */
    @POST
    @Path("/emitirResultadoAnalistaContable")
    public void emitirResultadoAnalistaContable(ResultadoCierreDTO resultadoCierre, @Context UserDTO userDTO);

    
    /**
     * Servicio que se encarga de consultar todos los registros de aporte que se les puede aplicar un cierre de recaudo de aportes
     * y se caulcula el resumen de ellos
     * @param fechaInicio
     *        fecha inicio para consultar los aportes
     * @param fechaFin
     *        fecha fin para consultar los aportes
     * @return List<ResumenCierreRecaudoDTO> Lista con todos los registros de aportes
     */
    @POST
    @Path("/consultarResumenCierreRecaudo")
    public List<ResumenCierreRecaudoDTO> consultarResumenCierreRecaudo(@QueryParam("fechaInicio") @NotNull Long fechaInicio,
            @QueryParam("fechaFin") @NotNull Long fechaFin);
}
