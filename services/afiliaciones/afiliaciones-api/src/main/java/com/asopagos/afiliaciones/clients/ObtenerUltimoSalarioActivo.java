package com.asopagos.afiliaciones.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.dto.UltimoSalarioAfiliadoOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerUltimoSalarioActivo
 */
public class ObtenerUltimoSalarioActivo extends ServiceClient {
 
  
  	private String identificacionAfiliado;
  	private String periodo;
  	private TipoIdentificacionEnum tipoID;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UltimoSalarioAfiliadoOutDTO result;
  
 	public ObtenerUltimoSalarioActivo (String identificacionAfiliado,String periodo,TipoIdentificacionEnum tipoID){
 		super();
		this.identificacionAfiliado=identificacionAfiliado;
		this.periodo=periodo;
		this.tipoID=tipoID;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificacionAfiliado", identificacionAfiliado)
						.queryParam("periodo", periodo)
						.queryParam("tipoID", tipoID)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UltimoSalarioAfiliadoOutDTO) response.readEntity(UltimoSalarioAfiliadoOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UltimoSalarioAfiliadoOutDTO getResult() {
		return result;
	}

 
  	public void setIdentificacionAfiliado (String identificacionAfiliado){
 		this.identificacionAfiliado=identificacionAfiliado;
 	}
 	
 	public String getIdentificacionAfiliado (){
 		return identificacionAfiliado;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  
}