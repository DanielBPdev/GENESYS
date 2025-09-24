package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.dto.RegistroPersonaInconsistenteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/buscarPersonasPendientesActulizar
 */
public class BuscarPersonasPendientesActulizar extends ServiceClient {
 
  
  	private Long numeroIdentificacion;
  	private Long fechaIngresoBandeja;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroPersonaInconsistenteDTO> result;
  
 	public BuscarPersonasPendientesActulizar (Long numeroIdentificacion,Long fechaIngresoBandeja,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.fechaIngresoBandeja=fechaIngresoBandeja;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("fechaIngresoBandeja", fechaIngresoBandeja)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RegistroPersonaInconsistenteDTO>) response.readEntity(new GenericType<List<RegistroPersonaInconsistenteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroPersonaInconsistenteDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (Long numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public Long getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setFechaIngresoBandeja (Long fechaIngresoBandeja){
 		this.fechaIngresoBandeja=fechaIngresoBandeja;
 	}
 	
 	public Long getFechaIngresoBandeja (){
 		return fechaIngresoBandeja;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}