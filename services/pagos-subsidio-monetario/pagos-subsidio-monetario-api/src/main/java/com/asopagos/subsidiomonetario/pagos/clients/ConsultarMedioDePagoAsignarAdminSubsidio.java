package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/consultarMedioDePagoAsignar/{idAdminSubsidio}
 */
public class ConsultarMedioDePagoAsignarAdminSubsidio extends ServiceClient { 
  	private Long idAdminSubsidio;
   	private TipoMedioDePagoEnum medioDePago;
   	private List<Long> lstMediosDePago;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<MedioDePagoModeloDTO> result;
  
 	public ConsultarMedioDePagoAsignarAdminSubsidio (Long idAdminSubsidio,TipoMedioDePagoEnum medioDePago,List<Long> lstMediosDePago){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.medioDePago=medioDePago;
		this.lstMediosDePago=lstMediosDePago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
			.queryParam("medioDePago", medioDePago)
			.request(MediaType.APPLICATION_JSON)
			.put(lstMediosDePago == null ? null : Entity.json(lstMediosDePago));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (List<MedioDePagoModeloDTO>) response.readEntity(new GenericType<List<MedioDePagoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<MedioDePagoModeloDTO> getResult() {
		return result;
	}

 	public void setIdAdminSubsidio (Long idAdminSubsidio){
 		this.idAdminSubsidio=idAdminSubsidio;
 	}
 	
 	public Long getIdAdminSubsidio (){
 		return idAdminSubsidio;
 	}
  
  	public void setMedioDePago (TipoMedioDePagoEnum medioDePago){
 		this.medioDePago=medioDePago;
 	}
 	
 	public TipoMedioDePagoEnum getMedioDePago (){
 		return medioDePago;
 	}
  
  	public void setLstMediosDePago (List<Long> lstMediosDePago){
 		this.lstMediosDePago=lstMediosDePago;
 	}
 	
 	public List<Long> getLstMediosDePago (){
 		return lstMediosDePago;
 	}
  
}