package com.asopagos.clienteanibol.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.asopagos.clienteanibol.dto.ConsultaSubsidioFosfecDTO;


@Path("integracionZenith")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface IntegracionZenithService {
	  @POST
	    @Path("/obtenerSolicitudesSubsidioFosfec")
	    public Response obtenerSolicitudesSubsidioFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO );	
	  
	  @POST
	    @Path("/obtenerBeneficiariosSubsidioFosfec")
	    public Response obtenerBeneficiariosSubsidioFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO );
	  
	  @GET
	    @Path("/consultarArchivoMaestroTrabajadoresActivos")
	    public Response consultarArchivoMaestroTrabajadoresActivos();
}
