package com.asopagos.bandejainconsistencias.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.bandejainconsistencias.dto.IdentificadorDocumentoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/verArchivo
 */
public class VeArchivo extends ServiceClient {
 
  
  	private TipoArchivoPilaEnum tipoArchivo;
  	private Long idPlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private IdentificadorDocumentoDTO result;
  
 	public VeArchivo (TipoArchivoPilaEnum tipoArchivo,Long idPlanilla){
 		super();
		this.tipoArchivo=tipoArchivo;
		this.idPlanilla=idPlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoArchivo", tipoArchivo)
						.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IdentificadorDocumentoDTO) response.readEntity(IdentificadorDocumentoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public IdentificadorDocumentoDTO getResult() {
		return result;
	}

 
  	public void setTipoArchivo (TipoArchivoPilaEnum tipoArchivo){
 		this.tipoArchivo=tipoArchivo;
 	}
 	
 	public TipoArchivoPilaEnum getTipoArchivo (){
 		return tipoArchivo;
 	}
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
}