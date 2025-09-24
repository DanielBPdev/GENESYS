package com.asopagos.zenith.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.asopagos.dto.BeneficiariosSubsidioFosfecDTO;
import com.asopagos.dto.ConfirmacionProcesoArchivoZenithDTO;
import com.asopagos.dto.ConsultaSubsidioFosfecDTO;
import com.asopagos.dto.SolicitudSubsidioFosfecDTO;

@Path("externalAPI/clienteZenith")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ClienteZenithService {

	@POST
	@Path("/obtenerSolicitudesSubsidioFosfec")
	public List<SolicitudSubsidioFosfecDTO> obtenerSolicitudesSubsidioFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO);

	@POST
	@Path("/obtenerBeneficiariosSubsidioFosfec")
	public List<BeneficiariosSubsidioFosfecDTO> obtenerBeneficiariosSubsidioFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO);
	
	@POST
	@Path("/confirmarProcesamientoArchivo")
	public String confirmarProcesamientoArchivo(@NotNull ConfirmacionProcesoArchivoZenithDTO confirmacionProcesoArchivoZenithDTO);
}
