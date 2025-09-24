package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.dto.ArchivoPilaDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/filtrarExistentes
 */
public class CargarArchivosParalelo extends ServiceClient { 
   	private Boolean ejecutarB0;
  	private Long idProceso;
  	private TipoCargaArchivoEnum tipoCarga;
   	private List<ArchivoPilaDTO> archivosDescargados;
  
  
 	public CargarArchivosParalelo (Boolean ejecutarB0,Long idProceso,TipoCargaArchivoEnum tipoCarga,List<ArchivoPilaDTO> archivosDescargados){
 		super();
		this.ejecutarB0=ejecutarB0;
		this.idProceso=idProceso;
		this.tipoCarga=tipoCarga;
		this.archivosDescargados=archivosDescargados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("ejecutarB0", ejecutarB0)
			.queryParam("idProceso", idProceso)
			.queryParam("tipoCarga", tipoCarga)
			.request(MediaType.APPLICATION_JSON)
			.post(archivosDescargados == null ? null : Entity.json(archivosDescargados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setEjecutarB0 (Boolean ejecutarB0){
 		this.ejecutarB0=ejecutarB0;
 	}
 	
 	public Boolean getEjecutarB0 (){
 		return ejecutarB0;
 	}
  	public void setIdProceso (Long idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public Long getIdProceso (){
 		return idProceso;
 	}
  	public void setTipoCarga (TipoCargaArchivoEnum tipoCarga){
 		this.tipoCarga=tipoCarga;
 	}
 	
 	public TipoCargaArchivoEnum getTipoCarga (){
 		return tipoCarga;
 	}
  
  	public void setArchivosDescargados (List<ArchivoPilaDTO> archivosDescargados){
 		this.archivosDescargados=archivosDescargados;
 	}
 	
 	public List<ArchivoPilaDTO> getArchivosDescargados (){
 		return archivosDescargados;
 	}
  
}