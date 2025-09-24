package com.asopagos.subsidiomonetario.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionEntidadDescuentoLiquidacionDTO;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultar/condicionesEntidadesDescuento/{numeroRadicacion}
 */
public class ConsultarCondicionesEntidadesDescuento extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private List<Long> indentificadoresCondiciones;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<Long,CondicionEntidadDescuentoLiquidacionDTO> result;
  
 	public ConsultarCondicionesEntidadesDescuento (String numeroRadicacion,List<Long> indentificadoresCondiciones){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.indentificadoresCondiciones=indentificadoresCondiciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("indentificadoresCondiciones", indentificadoresCondiciones.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<Long,CondicionEntidadDescuentoLiquidacionDTO>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<Long,CondicionEntidadDescuentoLiquidacionDTO> getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setIndentificadoresCondiciones (List<Long> indentificadoresCondiciones){
 		this.indentificadoresCondiciones=indentificadoresCondiciones;
 	}
 	
 	public List<Long> getIndentificadoresCondiciones (){
 		return indentificadoresCondiciones;
 	}
  
}