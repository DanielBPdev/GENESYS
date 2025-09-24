package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.EstadoTarjetaEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idAfiliado}/tarjetas
 */
public class ConsultarTarjetasAfiliado extends ServiceClient {
 
  	private Long idAfiliado;
  
  	private EstadoTarjetaEnum estado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public ConsultarTarjetasAfiliado (Long idAfiliado,EstadoTarjetaEnum estado){
 		super();
		this.idAfiliado=idAfiliado;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAfiliado", idAfiliado)
									.queryParam("estado", estado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<String> getResult() {
		return result;
	}

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  	public void setEstado (EstadoTarjetaEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoTarjetaEnum getEstado (){
 		return estado;
 	}
  
}