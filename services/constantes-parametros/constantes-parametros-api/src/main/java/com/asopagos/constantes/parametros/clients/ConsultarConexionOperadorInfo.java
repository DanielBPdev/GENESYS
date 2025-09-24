package com.asopagos.constantes.parametros.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Short;
import java.lang.String;
import com.asopagos.dto.ConexionOperadorInformacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/consultarConexionOperadorInfo
 */
public class ConsultarConexionOperadorInfo extends ServiceClient {
 
  
  	private Short puerto;
  	private String host;
  	private String nombre;
  	private Long idOperadorInformacion;
  	private String url;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConexionOperadorInformacionDTO> result;
  
 	public ConsultarConexionOperadorInfo (Short puerto,String host,String nombre,Long idOperadorInformacion,String url){
 		super();
		this.puerto=puerto;
		this.host=host;
		this.nombre=nombre;
		this.idOperadorInformacion=idOperadorInformacion;
		this.url=url;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("puerto", puerto)
						.queryParam("host", host)
						.queryParam("nombre", nombre)
						.queryParam("idOperadorInformacion", idOperadorInformacion)
						.queryParam("url", url)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConexionOperadorInformacionDTO>) response.readEntity(new GenericType<List<ConexionOperadorInformacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConexionOperadorInformacionDTO> getResult() {
		return result;
	}

 
  	public void setPuerto (Short puerto){
 		this.puerto=puerto;
 	}
 	
 	public Short getPuerto (){
 		return puerto;
 	}
  	public void setHost (String host){
 		this.host=host;
 	}
 	
 	public String getHost (){
 		return host;
 	}
  	public void setNombre (String nombre){
 		this.nombre=nombre;
 	}
 	
 	public String getNombre (){
 		return nombre;
 	}
  	public void setIdOperadorInformacion (Long idOperadorInformacion){
 		this.idOperadorInformacion=idOperadorInformacion;
 	}
 	
 	public Long getIdOperadorInformacion (){
 		return idOperadorInformacion;
 	}
  	public void setUrl (String url){
 		this.url=url;
 	}
 	
 	public String getUrl (){
 		return url;
 	}
  
}