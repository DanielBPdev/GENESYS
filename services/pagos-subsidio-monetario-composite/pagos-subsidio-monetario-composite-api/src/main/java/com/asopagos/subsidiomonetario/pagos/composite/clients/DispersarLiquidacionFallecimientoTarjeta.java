package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/dispersarLiquidacionFallecimientoTarjeta/{numeroIdentificacion}/{tipoIdentificacion}/{numeroRadicado}
 */
public class DispersarLiquidacionFallecimientoTarjeta extends ServiceClient { 
  	private String numeroIdentificacion;
  	private String numeroRadicado;
  	private TipoIdentificacionEnum tipoIdentificacion;
    	private List<Long> lstIdsCondicionesBeneficiarios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public DispersarLiquidacionFallecimientoTarjeta (String numeroIdentificacion,String numeroRadicado,TipoIdentificacionEnum tipoIdentificacion,List<Long> lstIdsCondicionesBeneficiarios){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.numeroRadicado=numeroRadicado;
		this.tipoIdentificacion=tipoIdentificacion;
		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroIdentificacion", numeroIdentificacion)
			.resolveTemplate("numeroRadicado", numeroRadicado)
			.resolveTemplate("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(lstIdsCondicionesBeneficiarios == null ? null : Entity.json(lstIdsCondicionesBeneficiarios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
  
  	public void setLstIdsCondicionesBeneficiarios (List<Long> lstIdsCondicionesBeneficiarios){
 		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 	
 	public List<Long> getLstIdsCondicionesBeneficiarios (){
 		return lstIdsCondicionesBeneficiarios;
 	}
  
}