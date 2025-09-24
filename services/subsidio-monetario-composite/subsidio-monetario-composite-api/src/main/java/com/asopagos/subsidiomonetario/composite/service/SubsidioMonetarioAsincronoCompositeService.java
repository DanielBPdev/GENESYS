package com.asopagos.subsidiomonetario.composite.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * a la definicion de servicios asíncronos en Subsidio Monetario.
 * <b>Módulo:</b> Asopagos - 311 - 438<br/>
 *
 * @author <a href="mailto:flopez@heinsohn.com.co> Fabian López</a>
 */

@Path("subsidioMonetarioAsincronoComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SubsidioMonetarioAsincronoCompositeService {

    /**
     * Método que se encarga de registrar el rechazo de un proceso de liquidación masiva, anexando la información relacionada
     * @author flopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la información relacionada en el rechazo
     * @param userDTO
     */
    @POST
    @Path("/rechazarLiquidacionMasivaPrimerNivelAsync/{numeroSolicitud}/{idTarea}")
    public void rechazarLiquidacionMasivaPrimerNivelAsync(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @QueryParam("profile") String profile);

    /**
     * Método que se encarga de registar el rechazo de un proceso de liquidación masiva, anexando la información relacionada
     * @author flopez
     * @param numeroSolicitud
     *        número que identifica el proceso de liquidación
     * @param aprobacionRechazoSubsidioMonetarioDTO
     *        DTO con la infromación relacionada en el rechazo
     * @param userDTO
     */
    @POST
    @Path("/rechazarLiquidacionMasivaSegundoNivelAsync/{numeroSolicitud}/{idTarea}")
    public void rechazarLiquidacionMasivaSegundoNivelAsync(@PathParam("numeroSolicitud") String numeroSolicitud,
            @PathParam("idTarea") String idTarea, AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO,
            @Context UserDTO userDTO, @QueryParam("profile") String profile);

}
