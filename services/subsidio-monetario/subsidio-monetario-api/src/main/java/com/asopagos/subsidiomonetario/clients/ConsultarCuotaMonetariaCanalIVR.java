package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.dto.CuotaMonetariaIVRDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarCuotaMonetariaCanalIVR
 */
public class ConsultarCuotaMonetariaCanalIVR extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuotaMonetariaIVRDTO> result;
  
 	public ConsultarCuotaMonetariaCanalIVR (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
						.queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuotaMonetariaIVRDTO>) response.readEntity(new GenericType<List<CuotaMonetariaIVRDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuotaMonetariaIVRDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdentificacionAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado){
 		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionAfiliado (){
 		return tipoIdentificacionAfiliado;
 	}
  	public void setNumeroIdentificacionAfiliado (String numeroIdentificacionAfiliado){
 		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 	
 	public String getNumeroIdentificacionAfiliado (){
 		return numeroIdentificacionAfiliado;
 	}
  
}