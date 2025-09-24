package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/consultarEmpleadorEstadoCaja
 */
public class ConsultarEmpleadorEstadoCaja extends ServiceClient {
 
  
  	private String textoFiltro;
  	private EstadoEmpleadorEnum estadoCaja;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String razonSocial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpleadorModeloDTO> result;
  
 	public ConsultarEmpleadorEstadoCaja (String textoFiltro,EstadoEmpleadorEnum estadoCaja,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String razonSocial){
 		super();
		this.textoFiltro=textoFiltro;
		this.estadoCaja=estadoCaja;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.razonSocial=razonSocial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("textoFiltro", textoFiltro)
						.queryParam("estadoCaja", estadoCaja)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("razonSocial", razonSocial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<EmpleadorModeloDTO>) response.readEntity(new GenericType<List<EmpleadorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EmpleadorModeloDTO> getResult() {
		return result;
	}

 
  	public void setTextoFiltro (String textoFiltro){
 		this.textoFiltro=textoFiltro;
 	}
 	
 	public String getTextoFiltro (){
 		return textoFiltro;
 	}
  	public void setEstadoCaja (EstadoEmpleadorEnum estadoCaja){
 		this.estadoCaja=estadoCaja;
 	}
 	
 	public EstadoEmpleadorEnum getEstadoCaja (){
 		return estadoCaja;
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
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  
}