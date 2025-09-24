package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.dto.modelo.ParametrizacionGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/obtenerRolesDestinatariosPrescripcion
 */
public class ObtenerRolesDestinatariosPrescripcion extends ServiceClient { 
   	private Long idCuentaAdmonSubsidio;

  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AutorizacionEnvioComunicadoDTO> result;
  
 	public ObtenerRolesDestinatariosPrescripcion (Long idCuentaAdmonSubsidio){
 		super();
		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio)
			.request(MediaType.APPLICATION_JSON)
			.post(idCuentaAdmonSubsidio == null ? null : Entity.json(idCuentaAdmonSubsidio));
		return response;
	}
        
        
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AutorizacionEnvioComunicadoDTO>) response.readEntity(new GenericType<List<AutorizacionEnvioComunicadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AutorizacionEnvioComunicadoDTO> getResult() {
		return result;
	}

  	public void setIdCuentaAdmonSubsidio (Long  idCuentaAdmonSubsidio){
 		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
 	}
 	
 	public Long getIdCuentaAdmonSubsidio (){
 		return idCuentaAdmonSubsidio;
 	}
  
}