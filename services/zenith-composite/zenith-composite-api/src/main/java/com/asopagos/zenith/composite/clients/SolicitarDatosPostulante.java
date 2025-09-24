package com.asopagos.zenith.composite.clients;

import java.lang.String;
import com.asopagos.zenith.dto.DatosPostulanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/zenithBusiness/solicitarDatosPostulante
 */
public class SolicitarDatosPostulante extends ServiceClient {
 
  
  	private String tipoIdentificacionCotizante;
  	private String numeroIdentificacionCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosPostulanteDTO result;
  
 	public SolicitarDatosPostulante (String tipoIdentificacionCotizante,String numeroIdentificacionCotizante){
 		super();
		this.tipoIdentificacionCotizante=tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionCotizante", tipoIdentificacionCotizante)
						.queryParam("numeroIdentificacionCotizante", numeroIdentificacionCotizante)
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

 
  	public void setTipoIdentificacionCotizante (String tipoIdentificacionCotizante){
 		this.tipoIdentificacionCotizante=tipoIdentificacionCotizante;
 	}
 	
 	public String getTipoIdentificacionCotizante (){
 		return tipoIdentificacionCotizante;
 	}
  	public void setNumeroIdentificacionCotizante (String numeroIdentificacionCotizante){
 		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
 	}
 	
 	public String getNumeroIdentificacionCotizante (){
 		return numeroIdentificacionCotizante;
 	}
  
}