package com.asopagos.processschedule.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para adminsitración del
 * Process Schedule
 * 
 * @author Andres Valbuena <anvalbuena@heinsohn.com.co>
 */
@Path("processSchedule")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ProcessScheduleService {

    /**
     * Servicio para consultar las programaciones asociadas a la lista de procesos que llegan por parametro.
     * @param procesos
     *        Lista de procesos a los que se debe consultar la programación.
     * @return Lista de las parametrizaciones de ejecucion
     * @see com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO
     */
    @POST
    @Path("/consultarProgramacion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParametrizacionEjecucionProgramadaModeloDTO> consultarProgramacion(@NotNull List<ProcesoAutomaticoEnum> procesos);

    /**
     * Servicio que crea o actuliza las programaciones que llegan por parametro
     * @param programaciones
     */
    @POST
    @Path("/registrarActualizarProgramacionPostman")
    public void registrarActualizarProgramacionPostman(@NotNull List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones, @NotNull @QueryParam("user") String user, @NotNull @QueryParam("pass") String pass, @Context UserDTO userDTO);

     /**
     * Servicio que crea o actuliza las programaciones que llegan por parametro
     * @param programaciones
     */
    @POST
    @Path("/registrarActualizarProgramacionAutomatico")
    public void registrarActualizarProgramacion(@NotNull List<ParametrizacionEjecucionProgramadaModeloDTO> programaciones);

    /**
     * Cancela todas las programaciones que tengan asociado el proceso dado por parametro.
     * @param proceso
     *        Proceso al que se le deben cancelar todas las programaciones.
     */
    @POST
    @Path("/cancelarProgramacionPorProceso")
    public void cancelarProgramacionPorProceso(@NotNull ProcesoAutomaticoEnum proceso);


    /**
     * Servicio para liberar planillas que se bloquean en bloque 9 
     * @param procesos
     *        Lista de procesos a los que se debe consultar la programación.
     * @return Lista de las parametrizaciones de ejecucion
     * @see com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO
     */
    @POST
    @Path("/liberarPlanillasBloque9")
    @Produces()
    public void liberarPlanillasBloque9();

}
