package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/validarArchivoRetiro/{idArchivoRetiro}
 */
public class ValidarArchivoRetiro extends ServiceClient {
 
  	private String idArchivoRetiro;
  
  	private String nombreTerceroPagador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoRetiroDTO result;
  
 	public ValidarArchivoRetiro (String idArchivoRetiro,String nombreTerceroPagador){
 		super();
		this.idArchivoRetiro=idArchivoRetiro;
		this.nombreTerceroPagador=nombreTerceroPagador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idArchivoRetiro", idArchivoRetiro)
									.queryParam("nombreTerceroPagador", nombreTerceroPagador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoValidacionArchivoRetiroDTO) response.readEntity(ResultadoValidacionArchivoRetiroDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoValidacionArchivoRetiroDTO getResult() {
		return result;
	}

 	public void setIdArchivoRetiro (String idArchivoRetiro){
 		this.idArchivoRetiro=idArchivoRetiro;
 	}
 	
 	public String getIdArchivoRetiro (){
 		return idArchivoRetiro;
 	}
  
  	public void setNombreTerceroPagador (String nombreTerceroPagador){
 		this.nombreTerceroPagador=nombreTerceroPagador;
 	}
 	
 	public String getNombreTerceroPagador (){
 		return nombreTerceroPagador;
 	}
  
}