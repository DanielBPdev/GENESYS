package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicacion}/validarEnvioComunicadoElectronico
 */
public class ValidarEnvioComunicadoElectronico extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private TipoAccionCobroEnum tipoAccionCobro;
  	private Long idCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarEnvioComunicadoElectronico (String numeroRadicacion,TipoAccionCobroEnum tipoAccionCobro,Long idCartera){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.tipoAccionCobro=tipoAccionCobro;
		this.idCartera=idCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("tipoAccionCobro", tipoAccionCobro)
						.queryParam("idCartera", idCartera)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setTipoAccionCobro (TipoAccionCobroEnum tipoAccionCobro){
 		this.tipoAccionCobro=tipoAccionCobro;
 	}
 	
 	public TipoAccionCobroEnum getTipoAccionCobro (){
 		return tipoAccionCobro;
 	}
  	public void setIdCartera (Long idCartera){
 		this.idCartera=idCartera;
 	}
 	
 	public Long getIdCartera (){
 		return idCartera;
 	}
  
}