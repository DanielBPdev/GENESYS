package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/cargar/manual/ArchivoConsumoTarjetaAnibol/{idArchivoConsumo}
 */
public class CargarManualArchivoConsumoTarjetaAnibol extends ServiceClient {
 
  	private String idArchivoConsumo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoRetiroDTO result;
  
 	public CargarManualArchivoConsumoTarjetaAnibol (String idArchivoConsumo){
 		super();
		this.idArchivoConsumo=idArchivoConsumo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idArchivoConsumo", idArchivoConsumo)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoValidacionArchivoRetiroDTO) response.readEntity(ResultadoValidacionArchivoRetiroDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoValidacionArchivoRetiroDTO getResult() {
		return result;
	}

 	public void setIdArchivoConsumo (String idArchivoConsumo){
 		this.idArchivoConsumo=idArchivoConsumo;
 	}
 	
 	public String getIdArchivoConsumo (){
 		return idArchivoConsumo;
 	}
  
  
}