package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ConvenioTercerPagadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/convenioTerceroPagadorPorIdEmpresa/{idEmpresa}
 */
public class ConsultarConvenioTerceroPagadorPorIdEmpresa extends ServiceClient {
 
  	private Long idEmpresa;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConvenioTercerPagadorDTO result;
  
 	public ConsultarConvenioTerceroPagadorPorIdEmpresa (Long idEmpresa){
 		super();
		this.idEmpresa=idEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpresa", idEmpresa)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConvenioTercerPagadorDTO) response.readEntity(ConvenioTercerPagadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConvenioTercerPagadorDTO getResult() {
		return result;
	}

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
}