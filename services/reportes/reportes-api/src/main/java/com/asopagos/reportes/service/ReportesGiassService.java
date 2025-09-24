package com.asopagos.reportes.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.reportes.ReporteGiassEnum;

@Path("reportesGiass")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ReportesGiassService {
	
	    @GET
	    @Path("/generarReporteTabla/{tablaGiass}")
	    public Response generarReporteTabla(@NotNull @PathParam("tablaGiass") ReporteGiassEnum tablaGiass);

}
