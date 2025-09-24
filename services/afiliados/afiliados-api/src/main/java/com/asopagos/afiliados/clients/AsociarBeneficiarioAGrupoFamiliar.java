package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/{idAfiliado}/gruposFamiliares/{idGrupoFamiliar}/asociarBeneficiario
 */
public class AsociarBeneficiarioAGrupoFamiliar extends ServiceClient { 
  	private Long idGrupoFamiliar;
  	private Long idAfiliado;
    	private DatosBasicosIdentificacionDTO inDTO;
  
  
 	public AsociarBeneficiarioAGrupoFamiliar (Long idGrupoFamiliar,Long idAfiliado,DatosBasicosIdentificacionDTO inDTO){
 		super();
		this.idGrupoFamiliar=idGrupoFamiliar;
		this.idAfiliado=idAfiliado;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idGrupoFamiliar", idGrupoFamiliar)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdGrupoFamiliar (Long idGrupoFamiliar){
 		this.idGrupoFamiliar=idGrupoFamiliar;
 	}
 	
 	public Long getIdGrupoFamiliar (){
 		return idGrupoFamiliar;
 	}
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  	public void setInDTO (DatosBasicosIdentificacionDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public DatosBasicosIdentificacionDTO getInDTO (){
 		return inDTO;
 	}
  
}