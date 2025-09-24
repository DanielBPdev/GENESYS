package com.asopagos.listaschequeo.clients;

import com.asopagos.listaschequeo.dto.RequisitosClasificacionesDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/requisitos/clasificaciones
 */
public class ConsultarRequisitosClasificaciones extends ServiceClient {
 
  
  	private Integer idCajaCompensacion;
  	private TipoTransaccionEnum tipoTransaccion;
  	private String tipoSolicitante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RequisitosClasificacionesDTO result;
  
 	public ConsultarRequisitosClasificaciones (Integer idCajaCompensacion,TipoTransaccionEnum tipoTransaccion,String tipoSolicitante){
 		super();
		this.idCajaCompensacion=idCajaCompensacion;
		this.tipoTransaccion=tipoTransaccion;
		this.tipoSolicitante=tipoSolicitante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCajaCompensacion", idCajaCompensacion)
						.queryParam("tipoTransaccion", tipoTransaccion)
						.queryParam("tipoSolicitante", tipoSolicitante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RequisitosClasificacionesDTO) response.readEntity(RequisitosClasificacionesDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RequisitosClasificacionesDTO getResult() {
		return result;
	}

 
  	public void setIdCajaCompensacion (Integer idCajaCompensacion){
 		this.idCajaCompensacion=idCajaCompensacion;
 	}
 	
 	public Integer getIdCajaCompensacion (){
 		return idCajaCompensacion;
 	}
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  	public void setTipoSolicitante (String tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public String getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
}