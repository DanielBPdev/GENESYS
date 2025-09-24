package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.BusquedaPorPersonaRespuestaDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/buscarPorAportanteCriterios
 */
public class BuscarPorAportanteCriterios extends ServiceClient {
 
  
  	private String numeroPlanilla;
  	private String periodo;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long registroControl;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BusquedaPorPersonaRespuestaDTO> result;
  
 	public BuscarPorAportanteCriterios (String numeroPlanilla,String periodo,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long registroControl){
 		super();
		this.numeroPlanilla=numeroPlanilla;
		this.periodo=periodo;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.registroControl=registroControl;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroPlanilla", numeroPlanilla)
						.queryParam("periodo", periodo)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("registroControl", registroControl)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BusquedaPorPersonaRespuestaDTO>) response.readEntity(new GenericType<List<BusquedaPorPersonaRespuestaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BusquedaPorPersonaRespuestaDTO> getResult() {
		return result;
	}

 
  	public void setNumeroPlanilla (String numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public String getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
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
  	public void setRegistroControl (Long registroControl){
 		this.registroControl=registroControl;
 	}
 	
 	public Long getRegistroControl (){
 		return registroControl;
 	}
  
}