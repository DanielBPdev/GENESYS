package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.aportes.dto.HistoricoNovedadesDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/{tipoIdentificacion}/{numeroIdentificacion}/consultarNovedades
 */
public class ConsultarNovedades extends ServiceClient {
 
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	private Long periodopago;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoNovedadesDTO> result;
  
 	public ConsultarNovedades (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long periodopago){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.periodopago=periodopago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroIdentificacion", numeroIdentificacion)
						.resolveTemplate("tipoIdentificacion", tipoIdentificacion)
									.queryParam("periodopago", periodopago)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoNovedadesDTO>) response.readEntity(new GenericType<List<HistoricoNovedadesDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoNovedadesDTO> getResult() {
		return result;
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
  
  	public void setPeriodopago (Long periodopago){
 		this.periodopago=periodopago;
 	}
 	
 	public Long getPeriodopago (){
 		return periodopago;
 	}
  
}