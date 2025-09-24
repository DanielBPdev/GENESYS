package com.asopagos.personas.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.personas.dto.DetalleBeneficiarioGrupoFamiliarDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/obtenerInfoDetalladaBeneficiarioGrupo
 */
public class ObtenerInfoDetalladaBeneficiarioGrupo extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long idGrupo;
  	private ClasificacionEnum parentezco;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleBeneficiarioGrupoFamiliarDTO result;
  
 	public ObtenerInfoDetalladaBeneficiarioGrupo (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long idGrupo,ClasificacionEnum parentezco){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.idGrupo=idGrupo;
		this.parentezco=parentezco;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("idGrupo", idGrupo)
						.queryParam("parentezco", parentezco)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleBeneficiarioGrupoFamiliarDTO) response.readEntity(DetalleBeneficiarioGrupoFamiliarDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleBeneficiarioGrupoFamiliarDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setIdGrupo (Long idGrupo){
 		this.idGrupo=idGrupo;
 	}
 	
 	public Long getIdGrupo (){
 		return idGrupo;
 	}
  	public void setParentezco (ClasificacionEnum parentezco){
 		this.parentezco=parentezco;
 	}
 	
 	public ClasificacionEnum getParentezco (){
 		return parentezco;
 	}
  
}