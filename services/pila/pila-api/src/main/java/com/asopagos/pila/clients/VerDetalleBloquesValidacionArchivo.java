package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.pila.dto.BloquesValidacionArchivoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/verDetalleBloquesValidacionArchivo
 */
public class VerDetalleBloquesValidacionArchivo extends ServiceClient {
 
  
  	private TipoOperadorEnum tipoOperador;
  	private Long idPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BloquesValidacionArchivoDTO> result;
  
 	public VerDetalleBloquesValidacionArchivo (TipoOperadorEnum tipoOperador,Long idPlanilla){
 		super();
		this.tipoOperador=tipoOperador;
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoOperador", tipoOperador)
						.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BloquesValidacionArchivoDTO>) response.readEntity(new GenericType<List<BloquesValidacionArchivoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BloquesValidacionArchivoDTO> getResult() {
		return result;
	}

 
  	public void setTipoOperador (TipoOperadorEnum tipoOperador){
 		this.tipoOperador=tipoOperador;
 	}
 	
 	public TipoOperadorEnum getTipoOperador (){
 		return tipoOperador;
 	}
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
}