package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/validarArchivoConsumoTerceroPagadorEfectivo/{idConvenio}/{idArchivoRetiro}/{IdArchivoRetiroTerceroPagadorEfectivo}
 */
public class ValidarArchivoConsumoTerceroPagadorEfectivo extends ServiceClient {
 
  	private Long idConvenio;
  	private String idArchivoRetiro;
  	private Long IdArchivoRetiroTerceroPagadorEfectivo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ValidarArchivoConsumoTerceroPagadorEfectivo (Long idConvenio,String idArchivoRetiro,Long IdArchivoRetiroTerceroPagadorEfectivo){
 		super();
		this.idConvenio=idConvenio;
		this.idArchivoRetiro=idArchivoRetiro;
		this.IdArchivoRetiroTerceroPagadorEfectivo=IdArchivoRetiroTerceroPagadorEfectivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idConvenio", idConvenio)
						.resolveTemplate("idArchivoRetiro", idArchivoRetiro)
						.resolveTemplate("IdArchivoRetiroTerceroPagadorEfectivo", IdArchivoRetiroTerceroPagadorEfectivo)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
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
  	public void setIdArchivoRetiroTerceroPagadorEfectivo (Long IdArchivoRetiroTerceroPagadorEfectivo){
 		this.IdArchivoRetiroTerceroPagadorEfectivo=IdArchivoRetiroTerceroPagadorEfectivo;
 	}
 	
 	public Long getIdArchivoRetiroTerceroPagadorEfectivo (){
 		return IdArchivoRetiroTerceroPagadorEfectivo;
 	}
  
  
}