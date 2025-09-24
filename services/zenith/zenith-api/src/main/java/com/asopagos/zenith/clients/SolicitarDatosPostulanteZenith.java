package com.asopagos.zenith.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.zenith.dto.DatosPostulanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/zenith/solicitarDatosPostulanteZenith
 */
public class SolicitarDatosPostulanteZenith extends ServiceClient {
 
  
  	private String numeroIdCotizante;
  	private TipoIdentificacionEnum tipoIdCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosPostulanteDTO result;
  
 	public SolicitarDatosPostulanteZenith (String numeroIdCotizante,TipoIdentificacionEnum tipoIdCotizante){
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
		this.result = (DatosPostulanteDTO) response.readEntity(DatosPostulanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosPostulanteDTO getResult() {
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