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
 * /rest/pagosSubsidioMonetario/consultarMedioDePagoInactivoAsignar/{idAdminSubsidio}
 */
public class ConsultarMedioDePagoInactivoAsignarAdminSubsidio extends ServiceClient { 
  	private Long idAdminSubsidio;
   	private TipoMedioDePagoEnum medioDePago;
   	private List<Long> lstIdsCuentas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<MedioDePagoModeloDTO> result;
  
 	public ConsultarMedioDePagoInactivoAsignarAdminSubsidio (Long idAdminSubsidio,TipoMedioDePagoEnum medioDePago,List<Long> lstIdsCuentas){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.medioDePago=medioDePago;
		this.lstIdsCuentas=lstIdsCuentas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
			.queryParam("medioDePago", medioDePago)
			.request(MediaType.APPLICATION_JSON)
			.put(lstIdsCuentas == null ? null : Entity.json(lstIdsCuentas));
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
  
  	public void setLstIdsCuentas (List<Long> lstIdsCuentas){
 		this.lstIdsCuentas=lstIdsCuentas;
 	}
 	
 	public List<Long> getLstIdsCuentas (){
 		return lstIdsCuentas;
 	}
  
}