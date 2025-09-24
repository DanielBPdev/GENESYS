package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/consultarDetallesProgramadosPorSolicitud
 */
public class ConsultarDetallesProgramadosPorSolicitud extends ServiceClient { 
   	private String numeroRadicado;
   	private List<Long> lstIdsCondicionesBeneficiarios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ConsultarDetallesProgramadosPorSolicitud (String numeroRadicado,List<Long> lstIdsCondicionesBeneficiarios){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(lstIdsCondicionesBeneficiarios == null ? null : Entity.json(lstIdsCondicionesBeneficiarios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DetalleSubsidioAsignadoDTO>) response.readEntity(new GenericType<List<DetalleSubsidioAsignadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DetalleSubsidioAsignadoDTO> getResult() {
		return result;
	}

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  	public void setLstIdsCondicionesBeneficiarios (List<Long> lstIdsCondicionesBeneficiarios){
 		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 	
 	public List<Long> getLstIdsCondicionesBeneficiarios (){
 		return lstIdsCondicionesBeneficiarios;
 	}
  
}