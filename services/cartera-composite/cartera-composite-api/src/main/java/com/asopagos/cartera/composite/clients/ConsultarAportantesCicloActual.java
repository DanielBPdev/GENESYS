package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/consultarAportanteCicloActual
 */
public class ConsultarAportantesCicloActual extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private EstadoFiscalizacionEnum estado;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String analista;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesCicloActual (String numeroIdentificacion,EstadoFiscalizacionEnum estado,TipoIdentificacionEnum tipoIdentificacion,String analista){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.estado=estado;
		this.tipoIdentificacion=tipoIdentificacion;
		this.analista=analista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("estado", estado)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("analista", analista)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SimulacionDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setEstado (EstadoFiscalizacionEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoFiscalizacionEnum getEstado (){
 		return estado;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setAnalista (String analista){
 		this.analista=analista;
 	}
 	
 	public String getAnalista (){
 		return analista;
 	}
  
}