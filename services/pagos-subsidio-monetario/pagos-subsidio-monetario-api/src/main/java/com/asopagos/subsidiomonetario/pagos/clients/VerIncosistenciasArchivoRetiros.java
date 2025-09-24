package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.IncosistenciaConciliacionConvenioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/verIncosistenciasArchivoRetiros/{idArchivoRetiroTerceroPagador}
 */
public class VerIncosistenciasArchivoRetiros extends ServiceClient {
 
  	private Long idArchivoRetiroTerceroPagador;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<IncosistenciaConciliacionConvenioDTO> result;
  
 	public VerIncosistenciasArchivoRetiros (Long idArchivoRetiroTerceroPagador){
 		super();
		this.idArchivoRetiroTerceroPagador=idArchivoRetiroTerceroPagador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idArchivoRetiroTerceroPagador", idArchivoRetiroTerceroPagador)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<IncosistenciaConciliacionConvenioDTO>) response.readEntity(new GenericType<List<IncosistenciaConciliacionConvenioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<IncosistenciaConciliacionConvenioDTO> getResult() {
		return result;
	}

 	public void setIdArchivoRetiroTerceroPagador (Long idArchivoRetiroTerceroPagador){
 		this.idArchivoRetiroTerceroPagador=idArchivoRetiroTerceroPagador;
 	}
 	
 	public Long getIdArchivoRetiroTerceroPagador (){
 		return idArchivoRetiroTerceroPagador;
 	}
  
  
}