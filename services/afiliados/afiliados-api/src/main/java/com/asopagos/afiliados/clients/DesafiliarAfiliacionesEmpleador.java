package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/desafiliarAfiliacionesEmpleador
 */
public class DesafiliarAfiliacionesEmpleador extends ServiceClient { 
   	private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
   	private List<RolAfiliadoModeloDTO> roles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioDTO> result;
  
 	public DesafiliarAfiliacionesEmpleador (MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion,List<RolAfiliadoModeloDTO> roles){
 		super();
		this.motivoDesafiliacion=motivoDesafiliacion;
		this.roles=roles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("motivoDesafiliacion", motivoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(roles == null ? null : Entity.json(roles));
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

 
  	public void setMotivoDesafiliacion (MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 	
 	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion (){
 		return motivoDesafiliacion;
 	}
  
  	public void setRoles (List<RolAfiliadoModeloDTO> roles){
 		this.roles=roles;
 	}
 	
 	public List<RolAfiliadoModeloDTO> getRoles (){
 		return roles;
 	}
  
}