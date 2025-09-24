package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/solicitudAnulacionSubsidioCobrado/rechazar/{numeroSolicitudAnulacion}/{idTarea}
 */
public class RechazarSolicitudAnulacionSubsidioCobrado extends ServiceClient { 
  	private String idTarea;
  	private String numeroSolicitudAnulacion;
    	private SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAnulacionSubsidioCobradoDTO result;
  
 	public RechazarSolicitudAnulacionSubsidioCobrado (String idTarea,String numeroSolicitudAnulacion,SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO){
 		super();
		this.idTarea=idTarea;
		this.numeroSolicitudAnulacion=numeroSolicitudAnulacion;
		this.solicitudAnulacionSubsidioCobradoDTO=solicitudAnulacionSubsidioCobradoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.resolveTemplate("numeroSolicitudAnulacion", numeroSolicitudAnulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAnulacionSubsidioCobradoDTO == null ? null : Entity.json(solicitudAnulacionSubsidioCobradoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudAnulacionSubsidioCobradoDTO) response.readEntity(SolicitudAnulacionSubsidioCobradoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudAnulacionSubsidioCobradoDTO getResult() {
		return result;
	}

 	public void setIdTarea (String idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public String getIdTarea (){
 		return idTarea;
 	}
  	public void setNumeroSolicitudAnulacion (String numeroSolicitudAnulacion){
 		this.numeroSolicitudAnulacion=numeroSolicitudAnulacion;
 	}
 	
 	public String getNumeroSolicitudAnulacion (){
 		return numeroSolicitudAnulacion;
 	}
  
  
  	public void setSolicitudAnulacionSubsidioCobradoDTO (SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobradoDTO){
 		this.solicitudAnulacionSubsidioCobradoDTO=solicitudAnulacionSubsidioCobradoDTO;
 	}
 	
 	public SolicitudAnulacionSubsidioCobradoDTO getSolicitudAnulacionSubsidioCobradoDTO (){
 		return solicitudAnulacionSubsidioCobradoDTO;
 	}
  
}