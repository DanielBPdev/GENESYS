package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import com.asopagos.subsidiomonetario.dto.DatosComunicadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/obtenerDatosComunicadosDispersionFallecimiento
 */
public class ObtenerDatosComunicadosDispersionFallecimiento extends ServiceClient {
 
  
  	private ModoDesembolsoEnum modoDesembolso;
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosComunicadoDTO> result;
  
 	public ObtenerDatosComunicadosDispersionFallecimiento (ModoDesembolsoEnum modoDesembolso,String numeroRadicado){
 		super();
		this.modoDesembolso=modoDesembolso;
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("modoDesembolso", modoDesembolso)
						.queryParam("numeroRadicado", numeroRadicado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosComunicadoDTO>) response.readEntity(new GenericType<List<DatosComunicadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosComunicadoDTO> getResult() {
		return result;
	}

 
  	public void setModoDesembolso (ModoDesembolsoEnum modoDesembolso){
 		this.modoDesembolso=modoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getModoDesembolso (){
 		return modoDesembolso;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}