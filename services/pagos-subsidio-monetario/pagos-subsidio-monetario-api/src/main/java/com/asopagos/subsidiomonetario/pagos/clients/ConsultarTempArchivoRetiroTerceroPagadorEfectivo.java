package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.TempArchivoRetiroTerceroPagadorEfectivoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarTempArchivoRetiroTerceroPagadorEfectivo
 */
public class ConsultarTempArchivoRetiroTerceroPagadorEfectivo extends ServiceClient {
 
  
  	private Long idArchivoRetiroTerceroPagadorEfectivo;
  	private Long idConvenio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TempArchivoRetiroTerceroPagadorEfectivoDTO> result;
  
 	public ConsultarTempArchivoRetiroTerceroPagadorEfectivo (Long idArchivoRetiroTerceroPagadorEfectivo,Long idConvenio){
 		super();
		this.idArchivoRetiroTerceroPagadorEfectivo=idArchivoRetiroTerceroPagadorEfectivo;
		this.idConvenio=idConvenio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idArchivoRetiroTerceroPagadorEfectivo", idArchivoRetiroTerceroPagadorEfectivo)
						.queryParam("idConvenio", idConvenio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TempArchivoRetiroTerceroPagadorEfectivoDTO>) response.readEntity(new GenericType<List<TempArchivoRetiroTerceroPagadorEfectivoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TempArchivoRetiroTerceroPagadorEfectivoDTO> getResult() {
		return result;
	}

 
  	public void setIdArchivoRetiroTerceroPagadorEfectivo (Long idArchivoRetiroTerceroPagadorEfectivo){
 		this.idArchivoRetiroTerceroPagadorEfectivo=idArchivoRetiroTerceroPagadorEfectivo;
 	}
 	
 	public Long getIdArchivoRetiroTerceroPagadorEfectivo (){
 		return idArchivoRetiroTerceroPagadorEfectivo;
 	}
  	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  
}