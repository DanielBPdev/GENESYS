package com.asopagos.clienteanibol.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asopagos.dto.pila.azure.ArchivoPilaAzureDTO;

@Path("azure")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ClienteAzureService {

	@POST
    @Path("persistirPlanillasBloque4")
    public void persistirPlanillasBloque4(List<ArchivoPilaAzureDTO> ArchivosPilaAzureDTO);
}
