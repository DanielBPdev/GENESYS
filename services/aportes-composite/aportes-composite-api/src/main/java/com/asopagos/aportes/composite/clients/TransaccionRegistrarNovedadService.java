package com.asopagos.aportes.composite.clients;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/transaccionRegistrarNovedadService
 */
public class TransaccionRegistrarNovedadService extends ServiceClient { 
   	private CanalRecepcionEnum canal;
  	private Boolean esTrabajadorReintegrable;
  	private Boolean esEmpleadorReintegrable;
  	private String numeroIdAportante;
  	private TipoIdentificacionEnum tipoIdAportante;
   	private NovedadPilaDTO novedadPilaDTO;
  
  
 	public TransaccionRegistrarNovedadService (CanalRecepcionEnum canal,Boolean esTrabajadorReintegrable,Boolean esEmpleadorReintegrable,String numeroIdAportante,TipoIdentificacionEnum tipoIdAportante,NovedadPilaDTO novedadPilaDTO){
 		super();
		this.canal=canal;
		this.esTrabajadorReintegrable=esTrabajadorReintegrable;
		this.esEmpleadorReintegrable=esEmpleadorReintegrable;
		this.numeroIdAportante=numeroIdAportante;
		this.tipoIdAportante=tipoIdAportante;
		this.novedadPilaDTO=novedadPilaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("canal", canal)
			.queryParam("esTrabajadorReintegrable", esTrabajadorReintegrable)
			.queryParam("esEmpleadorReintegrable", esEmpleadorReintegrable)
			.queryParam("numeroIdAportante", numeroIdAportante)
			.queryParam("tipoIdAportante", tipoIdAportante)
			.request(MediaType.APPLICATION_JSON)
			.post(novedadPilaDTO == null ? null : Entity.json(novedadPilaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setCanal (CanalRecepcionEnum canal){
 		this.canal=canal;
 	}
 	
 	public CanalRecepcionEnum getCanal (){
 		return canal;
 	}
  	public void setEsTrabajadorReintegrable (Boolean esTrabajadorReintegrable){
 		this.esTrabajadorReintegrable=esTrabajadorReintegrable;
 	}
 	
 	public Boolean getEsTrabajadorReintegrable (){
 		return esTrabajadorReintegrable;
 	}
  	public void setEsEmpleadorReintegrable (Boolean esEmpleadorReintegrable){
 		this.esEmpleadorReintegrable=esEmpleadorReintegrable;
 	}
 	
 	public Boolean getEsEmpleadorReintegrable (){
 		return esEmpleadorReintegrable;
 	}
  	public void setNumeroIdAportante (String numeroIdAportante){
 		this.numeroIdAportante=numeroIdAportante;
 	}
 	
 	public String getNumeroIdAportante (){
 		return numeroIdAportante;
 	}
  	public void setTipoIdAportante (TipoIdentificacionEnum tipoIdAportante){
 		this.tipoIdAportante=tipoIdAportante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAportante (){
 		return tipoIdAportante;
 	}
  
  	public void setNovedadPilaDTO (NovedadPilaDTO novedadPilaDTO){
 		this.novedadPilaDTO=novedadPilaDTO;
 	}
 	
 	public NovedadPilaDTO getNovedadPilaDTO (){
 		return novedadPilaDTO;
 	}
  
}