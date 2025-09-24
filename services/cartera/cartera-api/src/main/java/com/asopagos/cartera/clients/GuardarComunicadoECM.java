package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.notificaciones.dto.ParametrosComunicadoDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/guardarComunicadoECM
 */
public class GuardarComunicadoECM extends ServiceClient { 
   	private TipoTransaccionEnum tipoTransaccion;
  	private EtiquetaPlantillaComunicadoEnum plantilla;
   	private ParametrosComunicadoDTO parametroComunicadoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GuardarComunicadoECM(TipoTransaccionEnum tipoTransaccion, EtiquetaPlantillaComunicadoEnum plantilla, ParametrosComunicadoDTO parametroComunicadoDTO){
 		super();
		this.tipoTransaccion=tipoTransaccion;
		this.plantilla=plantilla;
		this.parametroComunicadoDTO=parametroComunicadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoTransaccion", tipoTransaccion)
			.queryParam("plantilla", plantilla)
			.request(MediaType.APPLICATION_JSON)
			.post(parametroComunicadoDTO == null ? null : Entity.json(parametroComunicadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  	public void setPlantilla (EtiquetaPlantillaComunicadoEnum plantilla){
 		this.plantilla=plantilla;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getPlantilla (){
 		return plantilla;
 	}
  
  	public void setParametroComunicadoDTO (ParametrosComunicadoDTO parametroComunicadoDTO){
 		this.parametroComunicadoDTO=parametroComunicadoDTO;
 	}
 	
 	public ParametrosComunicadoDTO getParametroComunicadoDTO (){
 		return parametroComunicadoDTO;
 	}
  
}