package com.asopagos.afiliados.clients;

import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/{idBeneficiario}/beneficiario/estado
 */
public class ActualizarEstadoBeneficiario extends ServiceClient { 
  	private Long idBeneficiario;
   	private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
   	private EstadoAfiliadoEnum estado;
  
  
 	public ActualizarEstadoBeneficiario (Long idBeneficiario,MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion,EstadoAfiliadoEnum estado){
 		super();
		this.idBeneficiario=idBeneficiario;
		this.motivoDesafiliacion=motivoDesafiliacion;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idBeneficiario", idBeneficiario)
			.queryParam("motivoDesafiliacion", motivoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.put(estado == null ? null : Entity.json(estado));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdBeneficiario (Long idBeneficiario){
 		this.idBeneficiario=idBeneficiario;
 	}
 	
 	public Long getIdBeneficiario (){
 		return idBeneficiario;
 	}
  
  	public void setMotivoDesafiliacion (MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 	
 	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion (){
 		return motivoDesafiliacion;
 	}
  
  	public void setEstado (EstadoAfiliadoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoAfiliadoEnum getEstado (){
 		return estado;
 	}
  
}