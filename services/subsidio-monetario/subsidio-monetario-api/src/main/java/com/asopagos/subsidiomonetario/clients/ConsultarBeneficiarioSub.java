package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarBeneficiarioSub
 */
public class ConsultarBeneficiarioSub extends ServiceClient {
 
  
  	private Long fechaNacimiento;
  	private String primerApellido;
  	private String primerNombre;
  	private String segundoApellido;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String segundoNombre;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioModeloDTO> result;
  
 	public ConsultarBeneficiarioSub (Long fechaNacimiento,String primerApellido,String primerNombre,String segundoApellido,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String segundoNombre){
 		super();
		this.fechaNacimiento=fechaNacimiento;
		this.primerApellido=primerApellido;
		this.primerNombre=primerNombre;
		this.segundoApellido=segundoApellido;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.segundoNombre=segundoNombre;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaNacimiento", fechaNacimiento)
						.queryParam("primerApellido", primerApellido)
						.queryParam("primerNombre", primerNombre)
						.queryParam("segundoApellido", segundoApellido)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("segundoNombre", segundoNombre)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficiarioModeloDTO>) response.readEntity(new GenericType<List<BeneficiarioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficiarioModeloDTO> getResult() {
		return result;
	}

 
  	public void setFechaNacimiento (Long fechaNacimiento){
 		this.fechaNacimiento=fechaNacimiento;
 	}
 	
 	public Long getFechaNacimiento (){
 		return fechaNacimiento;
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
  
}