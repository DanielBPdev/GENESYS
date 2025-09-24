package com.asopagos.afiliados.clients;

import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarValorIngresoMensual
 */
public class ConsultarValorIngresoMensual extends ServiceClient {
 
  
  	private String numeroIdentificacionJefe;
  	private TipoIdentificacionEnum tipoIdentificacionIntegrante;
  	private TipoIdentificacionEnum tipoIdentificacionJefe;
  	private String numeroIdentificacionIntegrante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public ConsultarValorIngresoMensual (String numeroIdentificacionJefe,TipoIdentificacionEnum tipoIdentificacionIntegrante,TipoIdentificacionEnum tipoIdentificacionJefe,String numeroIdentificacionIntegrante){
 		super();
		this.numeroIdentificacionJefe=numeroIdentificacionJefe;
		this.tipoIdentificacionIntegrante=tipoIdentificacionIntegrante;
		this.tipoIdentificacionJefe=tipoIdentificacionJefe;
		this.numeroIdentificacionIntegrante=numeroIdentificacionIntegrante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacionJefe", numeroIdentificacionJefe)
						.queryParam("tipoIdentificacionIntegrante", tipoIdentificacionIntegrante)
						.queryParam("tipoIdentificacionJefe", tipoIdentificacionJefe)
						.queryParam("numeroIdentificacionIntegrante", numeroIdentificacionIntegrante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BigDecimal) response.readEntity(BigDecimal.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BigDecimal getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacionJefe (String numeroIdentificacionJefe){
 		this.numeroIdentificacionJefe=numeroIdentificacionJefe;
 	}
 	
 	public String getNumeroIdentificacionJefe (){
 		return numeroIdentificacionJefe;
 	}
  	public void setTipoIdentificacionIntegrante (TipoIdentificacionEnum tipoIdentificacionIntegrante){
 		this.tipoIdentificacionIntegrante=tipoIdentificacionIntegrante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionIntegrante (){
 		return tipoIdentificacionIntegrante;
 	}
  	public void setTipoIdentificacionJefe (TipoIdentificacionEnum tipoIdentificacionJefe){
 		this.tipoIdentificacionJefe=tipoIdentificacionJefe;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionJefe (){
 		return tipoIdentificacionJefe;
 	}
  	public void setNumeroIdentificacionIntegrante (String numeroIdentificacionIntegrante){
 		this.numeroIdentificacionIntegrante=numeroIdentificacionIntegrante;
 	}
 	
 	public String getNumeroIdentificacionIntegrante (){
 		return numeroIdentificacionIntegrante;
 	}
  
}