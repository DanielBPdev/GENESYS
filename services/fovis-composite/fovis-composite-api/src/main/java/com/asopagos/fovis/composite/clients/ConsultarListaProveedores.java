package com.asopagos.fovis.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Short;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.fovis.ProveedorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/consultarListaProveedores
 */
public class ConsultarListaProveedores extends ServiceClient {
 
  
  	private String primerApellido;
  	private String primerNombre;
  	private String segundoApellido;
  	private String numeroIdentificacion;
  	private Short digitoIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String segundoNombre;
  	private String razonSocial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio consulta de proveedor */
 	private List<ProveedorDTO> result;
  
 	public ConsultarListaProveedores (String primerApellido,String primerNombre,String segundoApellido,String numeroIdentificacion,Short digitoIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String segundoNombre,String razonSocial){
 		super();
		this.primerApellido=primerApellido;
		this.primerNombre=primerNombre;
		this.segundoApellido=segundoApellido;
		this.numeroIdentificacion=numeroIdentificacion;
		this.digitoIdentificacion=digitoIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.segundoNombre=segundoNombre;
		this.razonSocial=razonSocial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("primerApellido", primerApellido)
						.queryParam("primerNombre", primerNombre)
						.queryParam("segundoApellido", segundoApellido)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("digitoIdentificacion", digitoIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("segundoNombre", segundoNombre)
						.queryParam("razonSocial", razonSocial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ProveedorDTO>) response.readEntity(new GenericType<List<ProveedorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ProveedorDTO> getResult() {
		return result;
	}

 
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setPrimerNombre (String primerNombre){
 		this.primerNombre=primerNombre;
 	}
 	
 	public String getPrimerNombre (){
 		return primerNombre;
 	}
  	public void setSegundoApellido (String segundoApellido){
 		this.segundoApellido=segundoApellido;
 	}
 	
 	public String getSegundoApellido (){
 		return segundoApellido;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setDigitoIdentificacion (Short digitoIdentificacion){
 		this.digitoIdentificacion=digitoIdentificacion;
 	}
 	
 	public Short getDigitoIdentificacion (){
 		return digitoIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setSegundoNombre (String segundoNombre){
 		this.segundoNombre=segundoNombre;
 	}
 	
 	public String getSegundoNombre (){
 		return segundoNombre;
 	}
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  
}