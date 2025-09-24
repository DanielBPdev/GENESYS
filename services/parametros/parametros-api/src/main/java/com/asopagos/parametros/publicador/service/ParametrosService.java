package com.asopagos.parametros.publicador.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;

/**
 * <b>Descripción:</b> Define los métodos de negocio relacionados
 * con la administración de los parametros de subsidio por caja
 *
 * @author Steven Quintero <squintero@heinsohn.com.co>
 */
@Path("parametrosSubsidioCaja")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ParametrosService {

    @POST
    @Path("replicarInsercionListaEspecialRevision")
    public void replicarInsercionListaEspecialRevision(@NotNull ListaEspecialRevision listaEspecialRevision);
    
    @POST
    @Path("replicarCambioEstadoListaEspecialRevision")
    public void replicarCambioEstadoListaEspecialRevision(@NotNull ListaEspecialRevision listaEspecialRevision);
}
