package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.notificaciones.dto.AutorizacionEnvioComunicadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.dto.modelo.ParametrizacionGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/obtenerRolesDestinatarios
 */
public class ObtenerRolesDestinatarios extends ServiceClient { 
   	private TipoLineaCobroEnum lineaCobro;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   	private ParametrizacionGestionCobroModeloDTO parametrizacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AutorizacionEnvioComunicadoDTO> result;
  
 	public ObtenerRolesDestinatarios (TipoLineaCobroEnum lineaCobro,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,ParametrizacionGestionCobroModeloDTO parametrizacion){
 		super();
		this.lineaCobro=lineaCobro;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.parametrizacion=parametrizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("lineaCobro", lineaCobro)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacion == null ? null : Entity.json(parametrizacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AutorizacionEnvioComunicadoDTO>) response.readEntity(new GenericType<List<AutorizacionEnvioComunicadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AutorizacionEnvioComunicadoDTO> getResult() {
		return result;
	}

 
  	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
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
  
  	public void setParametrizacion (ParametrizacionGestionCobroModeloDTO parametrizacion){
 		this.parametrizacion=parametrizacion;
 	}
 	
 	public ParametrizacionGestionCobroModeloDTO getParametrizacion (){
 		return parametrizacion;
 	}
  
}