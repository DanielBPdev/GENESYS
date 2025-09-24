package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarMedioDePago
 */
public class ConsultarMedioDePago extends ServiceClient {
 
  
  	private Long idGrupoFamiliar;
  	private TipoMedioDePagoEnum tipoMedioDePagoEnum;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private MedioDePagoModeloDTO result;
  
 	public ConsultarMedioDePago (Long idGrupoFamiliar,TipoMedioDePagoEnum tipoMedioDePagoEnum,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.idGrupoFamiliar=idGrupoFamiliar;
		this.tipoMedioDePagoEnum=tipoMedioDePagoEnum;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idGrupoFamiliar", idGrupoFamiliar)
						.queryParam("tipoMedioDePagoEnum", tipoMedioDePagoEnum)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (MedioDePagoModeloDTO) response.readEntity(MedioDePagoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public MedioDePagoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdGrupoFamiliar (Long idGrupoFamiliar){
 		this.idGrupoFamiliar=idGrupoFamiliar;
 	}
 	
 	public Long getIdGrupoFamiliar (){
 		return idGrupoFamiliar;
 	}
  	public void setTipoMedioDePagoEnum (TipoMedioDePagoEnum tipoMedioDePagoEnum){
 		this.tipoMedioDePagoEnum=tipoMedioDePagoEnum;
 	}
 	
 	public TipoMedioDePagoEnum getTipoMedioDePagoEnum (){
 		return tipoMedioDePagoEnum;
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
  
}