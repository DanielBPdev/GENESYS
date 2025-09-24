package com.asopagos.afiliaciones.personas.web.clients;

import com.asopagos.dto.SolicitudAfiliacionPersonaDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.FiltroConsultaSolicitudesEnProcesoDTO;
/**
 * /rest/afiliacionesPersonasWeb/buscarSolicitudesAfiliacionPersonaPorEmpleador
 */
public class BuscarSolicitudesAfiliacionPersonaPorEmpleador extends ServiceClient {
 
  	private FiltroConsultaSolicitudesEnProcesoDTO filtro;
 	private List<SolicitudAfiliacionPersonaDTO> result;
  
 	public BuscarSolicitudesAfiliacionPersonaPorEmpleador(FiltroConsultaSolicitudesEnProcesoDTO filtro) {
		super();
		this.filtro = filtro;
	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(filtro));	
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}

	public FiltroConsultaSolicitudesEnProcesoDTO getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroConsultaSolicitudesEnProcesoDTO filtro) {
		this.filtro = filtro;
	}
}