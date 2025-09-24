package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroTerceroPagDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/cargarArchivoConsumoTerceroPagadorEfectivo/{idArchivoRetiro}/{idConvenio}
 */
public class CargarArchivoConsumoTerceroPagadorEfectivo extends ServiceClient {
 
  	private Long idConvenio;
  	private String idArchivoRetiro;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoRetiroTerceroPagDTO result;
  
 	public CargarArchivoConsumoTerceroPagadorEfectivo (Long idConvenio,String idArchivoRetiro){
 		super();
		this.idConvenio=idConvenio;
		this.idArchivoRetiro=idArchivoRetiro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idConvenio", idConvenio)
						.resolveTemplate("idArchivoRetiro", idArchivoRetiro)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResultadoValidacionArchivoRetiroTerceroPagDTO) response.readEntity(ResultadoValidacionArchivoRetiroTerceroPagDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResultadoValidacionArchivoRetiroTerceroPagDTO getResult() {
		return result;
	}

 	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  	public void setIdArchivoRetiro (String idArchivoRetiro){
 		this.idArchivoRetiro=idArchivoRetiro;
 	}
 	
 	public String getIdArchivoRetiro (){
 		return idArchivoRetiro;
 	}
  
  
}