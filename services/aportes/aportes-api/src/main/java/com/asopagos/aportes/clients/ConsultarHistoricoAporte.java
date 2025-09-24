package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.DatosAportanteDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/aportes/consultarHistoricoAporteAportante
 */
public class ConsultarHistoricoAporte extends ServiceClient {
 
  
  	private String periodoInicio;
  	private String periodoFin;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numeroIdentificacionCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosAportanteDTO result;
  
 	public ConsultarHistoricoAporte (String periodoInicio,String periodoFin,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacionCotizante){
 		super();
		this.periodoInicio=periodoInicio;
		this.periodoFin=periodoFin;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodoInicio", periodoInicio)
						.queryParam("periodoFin", periodoFin)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacionCotizante", numeroIdentificacionCotizante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatosAportanteDTO) response.readEntity(DatosAportanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosAportanteDTO getResult() {
		return result;
	}

 
  	public void setPeriodoInicio (String periodoInicio){
 		this.periodoInicio=periodoInicio;
 	}
 	
 	public String getPeriodoInicio (){
 		return periodoInicio;
 	}
  	public void setPeriodoFin (String periodoFin){
 		this.periodoFin=periodoFin;
 	}
 	
 	public String getPeriodoFin (){
 		return periodoFin;
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
  	public void setNumeroIdentificacionCotizante (String numeroIdentificacionCotizante){
 		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
 	}
 	
 	public String getNumeroIdentificacionCotizante (){
 		return numeroIdentificacionCotizante;
 	}
  
}