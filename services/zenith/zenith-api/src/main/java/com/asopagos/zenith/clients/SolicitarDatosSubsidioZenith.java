package com.asopagos.zenith.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.zenith.dto.DatosSubsidioDTO;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/zenith/solicitarDatosSubsidioZenith
 */
public class SolicitarDatosSubsidioZenith extends ServiceClient {
 
  
  	private String numeroIdCotizante;
  	private TipoIdentificacionEnum tipoIdCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosSubsidioDTO> result;
  
 	public SolicitarDatosSubsidioZenith (String numeroIdCotizante,TipoIdentificacionEnum tipoIdCotizante){
 		super();
		this.numeroIdCotizante=numeroIdCotizante;
		this.tipoIdCotizante=tipoIdCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdCotizante", numeroIdCotizante)
						.queryParam("tipoIdCotizante", tipoIdCotizante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosSubsidioDTO>) response.readEntity(new GenericType<List<DatosSubsidioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosSubsidioDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdCotizante (String numeroIdCotizante){
 		this.numeroIdCotizante=numeroIdCotizante;
 	}
 	
 	public String getNumeroIdCotizante (){
 		return numeroIdCotizante;
 	}
  	public void setTipoIdCotizante (TipoIdentificacionEnum tipoIdCotizante){
 		this.tipoIdCotizante=tipoIdCotizante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdCotizante (){
 		return tipoIdCotizante;
 	}
  
}