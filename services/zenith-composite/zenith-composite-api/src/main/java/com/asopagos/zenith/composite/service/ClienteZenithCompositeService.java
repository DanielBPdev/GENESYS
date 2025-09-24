package com.asopagos.zenith.composite.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.asopagos.dto.BeneficiariosSubsidioFosfecDTO;
import com.asopagos.dto.ConsultaSubsidioFosfecDTO;
import com.asopagos.dto.SolicitudSubsidioFosfecDTO;


@Path("externalAPI/ClienteZenithBusiness")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ClienteZenithCompositeService {

	@POST
	@Path("/obtenerSolicitudesFosfec")
	public List<SolicitudSubsidioFosfecDTO> obtenerSolicitudesFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO);

	@POST
	@Path("/obtenerBeneficiariosFosfec")
	public List<BeneficiariosSubsidioFosfecDTO> obtenerBeneficiariosFosfec(@NotNull ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO);
}
