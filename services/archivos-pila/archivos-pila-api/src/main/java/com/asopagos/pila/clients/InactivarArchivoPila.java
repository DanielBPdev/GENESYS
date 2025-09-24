package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/eliminarArchivosPila
 */
public class InactivarArchivoPila extends ServiceClient { 
    	private List<ArchivoPilaDTO> listaArchivoPila;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RespuestaServicioDTO> result;
  
 	public InactivarArchivoPila (List<ArchivoPilaDTO> listaArchivoPila){
 		super();
		this.listaArchivoPila=listaArchivoPila;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaArchivoPila == null ? null : Entity.json(listaArchivoPila));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<RespuestaServicioDTO>) response.readEntity(new GenericType<List<RespuestaServicioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<RespuestaServicioDTO> getResult() {
		return result;
	}

 
  
  	public void setListaArchivoPila (List<ArchivoPilaDTO> listaArchivoPila){
 		this.listaArchivoPila=listaArchivoPila;
 	}
 	
 	public List<ArchivoPilaDTO> getListaArchivoPila (){
 		return listaArchivoPila;
 	}
  
}