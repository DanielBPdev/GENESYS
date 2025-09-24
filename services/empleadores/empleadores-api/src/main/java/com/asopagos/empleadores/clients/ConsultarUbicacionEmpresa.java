package com.asopagos.empleadores.clients;

import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import java.lang.Long;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/consultarUbicacion
 */
public class ConsultarUbicacionEmpresa extends ServiceClient {
 
  
  	private Long idPersona;
  	private TipoUbicacionEnum tipoUbicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UbicacionDTO result;
  
 	public ConsultarUbicacionEmpresa (Long idPersona,TipoUbicacionEnum tipoUbicacion){
 		super();
		this.idPersona=idPersona;
		this.tipoUbicacion=tipoUbicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("tipoUbicacion", tipoUbicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UbicacionDTO) response.readEntity(UbicacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UbicacionDTO getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setTipoUbicacion (TipoUbicacionEnum tipoUbicacion){
 		this.tipoUbicacion=tipoUbicacion;
 	}
 	
 	public TipoUbicacionEnum getTipoUbicacion (){
 		return tipoUbicacion;
 	}
  
}