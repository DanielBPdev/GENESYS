package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/search
 */
public class BuscarAfiliados extends ServiceClient {
 
  
  	private Long fechaNacimiento;
  	private String primerApellido;
  	private String primerNombre;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaDTO> result;
  
 	public BuscarAfiliados (Long fechaNacimiento,String primerApellido,String primerNombre,TipoAfiliadoEnum tipoAfiliado,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.fechaNacimiento=fechaNacimiento;
		this.primerApellido=primerApellido;
		this.primerNombre=primerNombre;
		this.tipoAfiliado=tipoAfiliado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaNacimiento", fechaNacimiento)
						.queryParam("primerApellido", primerApellido)
						.queryParam("primerNombre", primerNombre)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PersonaDTO>) response.readEntity(new GenericType<List<PersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PersonaDTO> getResult() {
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
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
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