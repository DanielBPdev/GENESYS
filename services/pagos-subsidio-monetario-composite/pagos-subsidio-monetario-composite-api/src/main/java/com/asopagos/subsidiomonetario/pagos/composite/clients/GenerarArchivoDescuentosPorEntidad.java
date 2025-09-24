package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/generarArchivoDescuentosPorEntidad
 */
public class GenerarArchivoDescuentosPorEntidad extends ServiceClient {
 
  
  	private String numeroRadicacion;
  	private Long idEntidadDescuento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GenerarArchivoDescuentosPorEntidad (String numeroRadicacion,Long idEntidadDescuento){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idEntidadDescuento=idEntidadDescuento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("idEntidadDescuento", idEntidadDescuento)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setIdEntidadDescuento (Long idEntidadDescuento){
 		this.idEntidadDescuento=idEntidadDescuento;
 	}
 	
 	public Long getIdEntidadDescuento (){
 		return idEntidadDescuento;
 	}
  
}