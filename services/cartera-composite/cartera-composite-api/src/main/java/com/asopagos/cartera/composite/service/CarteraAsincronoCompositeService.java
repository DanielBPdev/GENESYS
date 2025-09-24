package com.asopagos.cartera.composite.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.asopagos.dto.cartera.AportanteRemisionComunicadoDTO;
import com.asopagos.dto.cartera.RegistroRemisionAportantesDTO;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio asincronos relacionados
 * con la cartera de la Caja de compensación.<b>Módulo:</b> Asopagos - Cartera<br/>
 *
 * @author Mario Andrés Monroy Monroy <mamonroy@heinsohn.com.co>
 */

@Path("carteraAsincronoComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface CarteraAsincronoCompositeService {
    
    /**
     * Servicio que actualiza los resultados de aportantes edictos en la acción de cobro 2E, y finaliza la tarea
     * @param registroDTO
     *        datos de los registros de los aportantes
     */
    @POST
    @Path("/finalizarResultadosEdictos")
    public void finalizarResultadosEdictosAsync(@NotNull RegistroRemisionAportantesDTO registroDTO);
    
    /**
     * Servicio que se encarga de confirmar la remisión de un aportante.
     * @param registroRemision
     *        registro de la primera remisión.
     */
    @POST
    @Path("/confirmarPrimeraRemision")
    public void confirmarPrimeraRemisionAsync(@NotNull RegistroRemisionAportantesDTO registroRemision);
    
    /**
     * Servicio que se encarga de registrar los resultados de la primera remisión.
     * @param numeroRadicacion
     *        número de radicación de la solicitud.
     * @param aportanteRemisionDTO
     *        lista de los aportantes con los resultados de la remisión.
     * @param idTarea
     *        id de la tarea asociada.
     * @param userDTO
     *        usuario autenticado.
     */
    @POST
    @Path("/{numeroRadicacion}/{idTarea}/registrarResultadosPrimeraRemision")
    public void registrarResultadosPrimeraRemisionAsync(@NotNull @PathParam("numeroRadicacion") String numeroRadicacion,
            @PathParam("idTarea") Long idTarea, @NotNull List<AportanteRemisionComunicadoDTO> aportanteRemisionDTO,
            @QueryParam("accionCobro") TipoAccionCobroEnum accionCobro, @Context UserDTO userDTO);
}
