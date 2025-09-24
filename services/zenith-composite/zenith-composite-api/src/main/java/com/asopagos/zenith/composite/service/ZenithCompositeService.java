package com.asopagos.zenith.composite.service;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.asopagos.zenith.dto.DatosPostulanteDTO;
import com.asopagos.zenith.dto.DatosSubsidioDTO;

@Path("externalAPI/zenithBusiness")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ZenithCompositeService {

	@GET
	@Path("/consultarArchivoMaestroTrabajadoresActivos")
	public Response consultarArchivoMaestroTrabajadoresActivos();
	
	@GET
	@Path("/consultarTrabajadoresActivos")
	public void consultarTrabajadoresActivos(@NotNull @QueryParam("nombreArchivo") String nombreArchivo, 
			@QueryParam("rutaUbicacion")String rutaUbicacion, 
			@QueryParam("fechaDisponible")Date fechaDisponible);
	
	@GET
	@Path("/solicitarDatosPostulante")
	public DatosPostulanteDTO solicitarDatosPostulante(@NotNull @QueryParam("tipoIdentificacionCotizante") String tipoIdentificacionCotizante,
			@NotNull @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante);

	@GET
	@Path("/solicitarDatosSubsidio")
	public List<DatosSubsidioDTO> solicitarDatosSubsidio(@NotNull @QueryParam("tipoIdentificacionCotizante") String tipoIdentificacionCotizante,
			@NotNull @QueryParam("numeroIdentificacionCotizante") String numeroIdentificacionCotizante
			);
}
