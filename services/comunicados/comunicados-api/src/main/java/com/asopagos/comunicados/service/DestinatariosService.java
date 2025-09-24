package com.asopagos.comunicados.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.asopagos.comunicados.dto.RolComunicadoDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * <b>Descripcion:</b> Interfaz de servicios Web REST para adminsitración de
 * comunicados<br/>
 * <b>Historia de Usuario:</b>  <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
@Path("destinatarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface DestinatariosService {

	/**
	 * Servicio encargado de consultar los comunicados que se deben de enviar en un proceso 
	 * @param proceso, proceso a consultar los comunicados 
	 * @return listado de comunicados perteneciente al proceso
	 */
	@GET
	@Path("/buscarComunicadosPorProceso/{proceso}")
	public List<RolComunicadoDTO> buscarComunicadosPorProceso(@NotNull @PathParam("proceso") ProcesoEnum proceso);
}
