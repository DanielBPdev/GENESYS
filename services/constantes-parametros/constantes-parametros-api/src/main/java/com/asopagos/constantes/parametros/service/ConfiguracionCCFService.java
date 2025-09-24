package com.asopagos.constantes.parametros.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import com.asopagos.dto.SedeCajaCompensacionDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la caja de compensacion Familiar <b>Módulo:</b> Asopagos Transversal<br/>
 *
 * @author Luis Arturo Zarate Ayala <a href="lzarate:lzarate@heinsohn.com.co"> lzarate</a>
 */
@Path("ccf")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ConfiguracionCCFService {
    
    @GET
    @Path("sedes/{idSede}")
    public SedeCajaCompensacionDTO consultarSede(@PathParam("idSede") @NotNull Long sede);
}
