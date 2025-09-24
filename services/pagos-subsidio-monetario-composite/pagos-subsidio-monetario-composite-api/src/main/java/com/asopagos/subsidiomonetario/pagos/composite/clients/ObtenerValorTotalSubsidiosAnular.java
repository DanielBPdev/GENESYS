package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.math.BigDecimal;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/obtenerValorTotalSubsidiosAnular
 */
public class ObtenerValorTotalSubsidiosAnular extends ServiceClient {
 
  
  	private String tipo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public ObtenerValorTotalSubsidiosAnular (String tipo){
 		super();
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipo", tipo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BigDecimal) response.readEntity(BigDecimal.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BigDecimal getResult() {
		return result;
	}

 
  	public void setTipo (String tipo){
 		this.tipo=tipo;
 	}
 	
 	public String getTipo (){
 		return tipo;
 	}
  
}