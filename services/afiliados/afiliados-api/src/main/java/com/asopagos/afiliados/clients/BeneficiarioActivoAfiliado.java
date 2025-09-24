package com.asopagos.afiliados.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/beneficiarioActivoAfiliado
 */
public class BeneficiarioActivoAfiliado extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
  	private TipoIdentificacionEnum tipoIdentificacionBeneficiario;
  	private String numeroIdentificacionBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public BeneficiarioActivoAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado,TipoIdentificacionEnum tipoIdentificacionBeneficiario,String numeroIdentificacionBeneficiario){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
		this.tipoIdentificacionBeneficiario=tipoIdentificacionBeneficiario;
		this.numeroIdentificacionBeneficiario=numeroIdentificacionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
						.queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.queryParam("tipoIdentificacionBeneficiario", tipoIdentificacionBeneficiario)
						.queryParam("numeroIdentificacionBeneficiario", numeroIdentificacionBeneficiario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setTipoIdentificacionAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado){
 		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionAfiliado (){
 		return tipoIdentificacionAfiliado;
 	}
  	public void setNumeroIdentificacionAfiliado (String numeroIdentificacionAfiliado){
 		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 	
 	public String getNumeroIdentificacionAfiliado (){
 		return numeroIdentificacionAfiliado;
 	}
  	public void setTipoIdentificacionBeneficiario (TipoIdentificacionEnum tipoIdentificacionBeneficiario){
 		this.tipoIdentificacionBeneficiario=tipoIdentificacionBeneficiario;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionBeneficiario (){
 		return tipoIdentificacionBeneficiario;
 	}
  	public void setNumeroIdentificacionBeneficiario (String numeroIdentificacionBeneficiario){
 		this.numeroIdentificacionBeneficiario=numeroIdentificacionBeneficiario;
 	}
 	
 	public String getNumeroIdentificacionBeneficiario (){
 		return numeroIdentificacionBeneficiario;
 	}
  
}