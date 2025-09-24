package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/subsidio/obtenerCuotaMonetaria
 */
public class ObtenerCuotaMonetaria extends ServiceClient {
 
  
  	private String numeroIdPersona;
  	private TipoIdentificacionEnum tipoIdPersona;
  	private String periodo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SubsidioAfiliadoDTO result;
  
 	public ObtenerCuotaMonetaria (String numeroIdPersona,TipoIdentificacionEnum tipoIdPersona,String periodo){
 		super();
		this.numeroIdPersona=numeroIdPersona;
		this.tipoIdPersona=tipoIdPersona;
		this.periodo=periodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdPersona", numeroIdPersona)
						.queryParam("tipoIdPersona", tipoIdPersona)
						.queryParam("periodo", periodo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SubsidioAfiliadoDTO) response.readEntity(SubsidioAfiliadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SubsidioAfiliadoDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdPersona (String numeroIdPersona){
 		this.numeroIdPersona=numeroIdPersona;
 	}
 	
 	public String getNumeroIdPersona (){
 		return numeroIdPersona;
 	}
  	public void setTipoIdPersona (TipoIdentificacionEnum tipoIdPersona){
 		this.tipoIdPersona=tipoIdPersona;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdPersona (){
 		return tipoIdPersona;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  
}