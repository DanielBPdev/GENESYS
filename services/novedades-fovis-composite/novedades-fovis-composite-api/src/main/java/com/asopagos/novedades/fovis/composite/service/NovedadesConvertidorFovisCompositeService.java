package com.asopagos.novedades.fovis.composite.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.novedades.fovis.composite.dto.InhabilidadSubsidioFovisInDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con Novedades Regulares FOVIS en las que aplican lógica especial de negocio.
 * 
 * <b>Proceso: 3.2.5</b>
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@Path("novedadesConvertidorFovisComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesConvertidorFovisCompositeService {

    /**
     * Actualiza los datos del hogar y moviliza Ahorros Previos.
     * @param hogar
     * @param userDTO
     */
    @POST
    @Path("/actualizarHogarYMovilizacionAhorros")
    public void actualizarHogarYMovilizacionAhorros(@NotNull PostulacionFOVISModeloDTO hogar, @Context UserDTO userDTO);

    /**
     * Ejecuta el rechazo de la postulacion
     * @param postulacion
     *        Informacion de la postulacion
     * @param userDTO
     *        Usuario del contexto
     */
    @POST
    @Path("/ejecutarRechazoPostulacion")
    public void ejecutarRechazoPostulacion(PostulacionFOVISModeloDTO postulacion, @Context UserDTO userDTO);

    /**
     * Ejecuta el registro de inhabilidad de subsidio de acuerdo a los datos enviados
     * @param inDTO
     *        Información para el registro de inhabilidad
     */
    @POST
    @Path("/ejecutarRegistroInhabilidad")
    public void ejecutarRegistroInhabilidad(InhabilidadSubsidioFovisInDTO inDTO);

}
