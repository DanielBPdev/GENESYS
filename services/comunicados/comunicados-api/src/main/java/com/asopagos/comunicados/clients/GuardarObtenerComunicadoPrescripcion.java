package com.asopagos.comunicados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/guardarObtenerComunicadoPrescripcion
 */
public class GuardarObtenerComunicadoPrescripcion extends ServiceClient { 
   	private Long idCuentaAdmonSubsidio;
  	private EtiquetaPlantillaComunicadoEnum plantilla;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoDTO result;
  
 	public GuardarObtenerComunicadoPrescripcion (Long idCuentaAdmonSubsidio,EtiquetaPlantillaComunicadoEnum plantilla){
 		super();
		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
		this.plantilla=plantilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCuentaAdmonSubsidio", idCuentaAdmonSubsidio)
			.queryParam("plantilla", plantilla)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InformacionArchivoDTO) response.readEntity(InformacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InformacionArchivoDTO getResult() {
		return result;
	}

 
  	public void setIdCuentaAdmonSubsidio (Long idCuentaAdmonSubsidio){
 		this.idCuentaAdmonSubsidio=idCuentaAdmonSubsidio;
 	}
 	
 	public Long getIdCuentaAdmonSubsidio (){
 		return idCuentaAdmonSubsidio;
 	}
  	public void setPlantilla (EtiquetaPlantillaComunicadoEnum plantilla){
 		this.plantilla=plantilla;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getPlantilla (){
 		return plantilla;
 	}
  
  
}