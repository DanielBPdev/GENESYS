package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.InformacionArchivoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/obtenerArchivoConsumoTarjetaANIBOLFTP
 */
public class ObtenerArchivoConsumoTarjetaANIBOLFTP extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InformacionArchivoDTO> result;
  
 	public ObtenerArchivoConsumoTarjetaANIBOLFTP (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InformacionArchivoDTO>) response.readEntity(new GenericType<List<InformacionArchivoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InformacionArchivoDTO> getResult() {
		return result;
	}

 
  
}