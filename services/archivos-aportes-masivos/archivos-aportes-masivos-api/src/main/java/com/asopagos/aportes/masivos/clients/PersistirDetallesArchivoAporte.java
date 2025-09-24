package com.asopagos.aportes.masivos.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.aportes.masivos.dto.ResultadoValidacionAporteDTO;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/PersistirDetallesArchivoAporte/{idArchivoMasivo}
 */
public class PersistirDetallesArchivoAporte extends ServiceClient { 
   	private Long idArchivoMasivo;
	private List<ResultadoValidacionAporteDTO> aportes;
   
  
 	public PersistirDetallesArchivoAporte (Long idArchivoMasivo, List<ResultadoValidacionAporteDTO> aportes){
 		super();
		this.idArchivoMasivo=idArchivoMasivo;
		this.aportes = aportes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idArchivoMasivo", idArchivoMasivo)
			.request(MediaType.APPLICATION_JSON)
			.post(aportes == null ? null : Entity.json(aportes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	


	public Long getIdArchivoMasivo() {
		return this.idArchivoMasivo;
	}

	public void setIdArchivoMasivo(Long idArchivoMasivo) {
		this.idArchivoMasivo = idArchivoMasivo;
	}

	public List<ResultadoValidacionAporteDTO> getAportes() {
		return this.aportes;
	}

	public void setAportes(List<ResultadoValidacionAporteDTO> aportes) {
		this.aportes = aportes;
	}

  
  
}