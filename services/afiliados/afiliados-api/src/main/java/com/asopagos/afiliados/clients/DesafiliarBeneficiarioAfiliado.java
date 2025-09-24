package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/desafiliarBeneficiarioAfiliado/{idAfiliado}
 */
public class DesafiliarBeneficiarioAfiliado extends ServiceClient { 
  	private Long idAfiliado;
   	private Long fechaRetiroAfiliado;
  	private Long idRolAfiliado;
  	private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioDTO> result;
  
 	public DesafiliarBeneficiarioAfiliado (Long idAfiliado,Long fechaRetiroAfiliado,Long idRolAfiliado,MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		super();
		this.idAfiliado=idAfiliado;
		this.fechaRetiroAfiliado=fechaRetiroAfiliado;
		this.idRolAfiliado=idRolAfiliado;
		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.queryParam("fechaRetiroAfiliado", fechaRetiroAfiliado)
			.queryParam("idRolAfiliado", idRolAfiliado)
			.queryParam("motivoDesafiliacion", motivoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<BeneficiarioDTO>) response.readEntity(new GenericType<List<BeneficiarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<BeneficiarioDTO> getResult() {
		return result;
	}

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  	public void setFechaRetiroAfiliado (Long fechaRetiroAfiliado){
 		this.fechaRetiroAfiliado=fechaRetiroAfiliado;
 	}
 	
 	public Long getFechaRetiroAfiliado (){
 		return fechaRetiroAfiliado;
 	}
  	public void setIdRolAfiliado (Long idRolAfiliado){
 		this.idRolAfiliado=idRolAfiliado;
 	}
 	
 	public Long getIdRolAfiliado (){
 		return idRolAfiliado;
 	}
  	public void setMotivoDesafiliacion (MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 	
 	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion (){
 		return motivoDesafiliacion;
 	}
  
  
}