package com.asopagos.afiliaciones.personas.composite.clients;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class CargueArchivoPensionados25Anios extends ServiceClient{
    private String tipoArchivo;
    // private String idEmpleador;
	private CargueArchivoActualizacionDTO cargue;

	private ResultadoArchivo25AniosDTO result;

 	public CargueArchivoPensionados25Anios (String tipoArchivo, String idEmpleador){
 		super();
		 this.tipoArchivo=tipoArchivo;
		// this.idEmpleador=idEmpleador;
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoArchivo25AniosDTO) response.readEntity(ResultadoArchivo25AniosDTO.class);
	}

	public void setTipoArchivo (String tipoArchivo){
		this.tipoArchivo=tipoArchivo;
	}
	
	public String getTipoArchivo (){
		return tipoArchivo;
	}
	// public void setIdEmpleador(String idEmpleador){
	// 	this.idEmpleador=idEmpleador;
	// }
	
	// public String getIdEmpleador (){
	// 	return idEmpleador;
	// }
	

  
  	public void setCargue (CargueArchivoActualizacionDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueArchivoActualizacionDTO getCargue (){
 		return cargue;
 	}
}