package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.TipoCicloEnum;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{idCicloFiscalizacion}/consultarSolicitudesCiclo
 */
public class ConsultarSolicitudesCiclo extends ServiceClient {
 
  	private Long idCicloFiscalizacion;
  
  	private List<EstadoFiscalizacionEnum> estado;
  	private TipoCicloEnum tipoCiclo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudModeloDTO> result;
  
 	public ConsultarSolicitudesCiclo (Long idCicloFiscalizacion,List<EstadoFiscalizacionEnum> estado,TipoCicloEnum tipoCiclo){
 		super();
		this.idCicloFiscalizacion=idCicloFiscalizacion;
		this.estado=estado;
		this.tipoCiclo=tipoCiclo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idCicloFiscalizacion", idCicloFiscalizacion)
									.queryParam("estado", estado.toArray())
						.queryParam("tipoCiclo", tipoCiclo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudModeloDTO>) response.readEntity(new GenericType<List<SolicitudModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudModeloDTO> getResult() {
		return result;
	}

 	public void setIdCicloFiscalizacion (Long idCicloFiscalizacion){
 		this.idCicloFiscalizacion=idCicloFiscalizacion;
 	}
 	
 	public Long getIdCicloFiscalizacion (){
 		return idCicloFiscalizacion;
 	}
  
  	public void setEstado (List<EstadoFiscalizacionEnum> estado){
 		this.estado=estado;
 	}
 	
 	public List<EstadoFiscalizacionEnum> getEstado (){
 		return estado;
 	}
  	public void setTipoCiclo (TipoCicloEnum tipoCiclo){
 		this.tipoCiclo=tipoCiclo;
 	}
 	
 	public TipoCicloEnum getTipoCiclo (){
 		return tipoCiclo;
 	}
  
}