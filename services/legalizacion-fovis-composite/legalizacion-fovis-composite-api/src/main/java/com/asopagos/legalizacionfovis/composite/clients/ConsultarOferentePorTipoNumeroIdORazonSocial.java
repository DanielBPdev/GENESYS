package com.asopagos.legalizacionfovis.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovisComposite/consultarOferentePorTipoNumeroIdORazonSocial
 */
public class ConsultarOferentePorTipoNumeroIdORazonSocial extends ServiceClient {
 
  
  	private String razonSocialNombre;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<OferenteModeloDTO> result;
  
 	public ConsultarOferentePorTipoNumeroIdORazonSocial (String razonSocialNombre,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.razonSocialNombre=razonSocialNombre;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("razonSocialNombre", razonSocialNombre)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<OferenteModeloDTO>) response.readEntity(new GenericType<List<OferenteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<OferenteModeloDTO> getResult() {
		return result;
	}

 
  	public void setRazonSocialNombre (String razonSocialNombre){
 		this.razonSocialNombre=razonSocialNombre;
 	}
 	
 	public String getRazonSocialNombre (){
 		return razonSocialNombre;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}