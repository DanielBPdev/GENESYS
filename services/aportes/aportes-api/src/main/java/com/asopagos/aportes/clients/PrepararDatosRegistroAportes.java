package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.DatosPersistenciaAportesDTO;
import java.lang.Long;
import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/prepararDatosRegistroAportes
 */
public class PrepararDatosRegistroAportes extends ServiceClient { 
   	private Integer tamanoPaginador;
  	private Long idRegistroGeneral;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosPersistenciaAportesDTO result;
  
 	public PrepararDatosRegistroAportes (Integer tamanoPaginador,Long idRegistroGeneral){
 		super();
		this.tamanoPaginador=tamanoPaginador;
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tamanoPaginador", tamanoPaginador)
			.queryParam("idRegistroGeneral", idRegistroGeneral)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DatosPersistenciaAportesDTO) response.readEntity(DatosPersistenciaAportesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatosPersistenciaAportesDTO getResult() {
		return result;
	}

 
  	public void setTamanoPaginador (Integer tamanoPaginador){
 		this.tamanoPaginador=tamanoPaginador;
 	}
 	
 	public Integer getTamanoPaginador (){
 		return tamanoPaginador;
 	}
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
  
}