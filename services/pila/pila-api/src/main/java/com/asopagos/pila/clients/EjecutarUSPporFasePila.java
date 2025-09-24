package com.asopagos.pila.clients;

import com.asopagos.enumeraciones.pila.FasePila2Enum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/ejecutarUSPporFasePila
 */
public class EjecutarUSPporFasePila extends ServiceClient {
 
  
  	private boolean reanudarTransaccion;
  	private FasePila2Enum fase;
  	private Long idIndicePlanilla;
  	private Long idTransaccion;
  	private String usuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public EjecutarUSPporFasePila (boolean reanudarTransaccion,FasePila2Enum fase,Long idIndicePlanilla,Long idTransaccion,String usuario){
 		super();
		this.reanudarTransaccion=reanudarTransaccion;
		this.fase=fase;
		this.idIndicePlanilla=idIndicePlanilla;
		this.idTransaccion=idTransaccion;
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("reanudarTransaccion", reanudarTransaccion)
						.queryParam("fase", fase)
						.queryParam("idIndicePlanilla", idIndicePlanilla)
						.queryParam("idTransaccion", idTransaccion)
						.queryParam("usuario", usuario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  	public void setReanudarTransaccion (boolean reanudarTransaccion){
 		this.reanudarTransaccion=reanudarTransaccion;
 	}
 	
 	public boolean getReanudarTransaccion (){
 		return reanudarTransaccion;
 	}
  	public void setFase (FasePila2Enum fase){
 		this.fase=fase;
 	}
 	
 	public FasePila2Enum getFase (){
 		return fase;
 	}
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  	public void setIdTransaccion (Long idTransaccion){
 		this.idTransaccion=idTransaccion;
 	}
 	
 	public Long getIdTransaccion (){
 		return idTransaccion;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  
}