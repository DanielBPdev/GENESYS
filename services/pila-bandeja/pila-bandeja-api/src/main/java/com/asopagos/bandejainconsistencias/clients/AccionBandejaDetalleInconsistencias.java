package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.enumeraciones.aportes.TipoInconsistenciasEnum;
import javax.ws.rs.core.GenericType;
import com.asopagos.bandejainconsistencias.dto.InconsistenciaDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/accionBandejaDetalleInconsistencias
 */
public class AccionBandejaDetalleInconsistencias extends ServiceClient { 
   	private TipoInconsistenciasEnum tipoInconsistencia;
   	private InconsistenciaDTO inconsistencias;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InconsistenciaDTO> result;
  
 	public AccionBandejaDetalleInconsistencias (TipoInconsistenciasEnum tipoInconsistencia,InconsistenciaDTO inconsistencias){
 		super();
		this.tipoInconsistencia=tipoInconsistencia;
		this.inconsistencias=inconsistencias;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoInconsistencia", tipoInconsistencia)
			.request(MediaType.APPLICATION_JSON)
			.post(inconsistencias == null ? null : Entity.json(inconsistencias));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<InconsistenciaDTO>) response.readEntity(new GenericType<List<InconsistenciaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<InconsistenciaDTO> getResult() {
		return result;
	}

 
  	public void setTipoInconsistencia (TipoInconsistenciasEnum tipoInconsistencia){
 		this.tipoInconsistencia=tipoInconsistencia;
 	}
 	
 	public TipoInconsistenciasEnum getTipoInconsistencia (){
 		return tipoInconsistencia;
 	}
  
  	public void setInconsistencias (InconsistenciaDTO inconsistencias){
 		this.inconsistencias=inconsistencias;
 	}
 	
 	public InconsistenciaDTO getInconsistencias (){
 		return inconsistencias;
 	}
  
}