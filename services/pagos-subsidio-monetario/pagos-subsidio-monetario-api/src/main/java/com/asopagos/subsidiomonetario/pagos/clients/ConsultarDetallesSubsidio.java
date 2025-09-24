package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/consultarDetallesSubsidio
 */
public class ConsultarDetallesSubsidio extends ServiceClient { 
    	private DetalleTransaccionAsignadoConsultadoDTO detalleConsultado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ConsultarDetallesSubsidio (DetalleTransaccionAsignadoConsultadoDTO detalleConsultado){
 		super();
		this.detalleConsultado=detalleConsultado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(detalleConsultado == null ? null : Entity.json(detalleConsultado));
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

 
  
  	public void setDetalleConsultado (DetalleTransaccionAsignadoConsultadoDTO detalleConsultado){
 		this.detalleConsultado=detalleConsultado;
 	}
 	
 	public DetalleTransaccionAsignadoConsultadoDTO getDetalleConsultado (){
 		return detalleConsultado;
 	}
  
}