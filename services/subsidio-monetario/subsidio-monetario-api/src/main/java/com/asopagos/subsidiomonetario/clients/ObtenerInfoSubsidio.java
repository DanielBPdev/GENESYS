package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/subsidio/obtenerInfoSubsidio
 */
public class ObtenerInfoSubsidio extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdPersona;
  	private String numeroIdBeneficiario;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SubsidioAfiliadoDTO result;
  
 	public ObtenerInfoSubsidio (TipoIdentificacionEnum tipoIdPersona,String numeroIdBeneficiario,String numeroIdAfiliado){
 		super();
		this.tipoIdPersona=tipoIdPersona;
		this.numeroIdBeneficiario=numeroIdBeneficiario;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdPersona", tipoIdPersona)
						.queryParam("numeroIdBeneficiario", numeroIdBeneficiario)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SubsidioAfiliadoDTO) response.readEntity(SubsidioAfiliadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SubsidioAfiliadoDTO getResult() {
		return result;
	}

 
  	public void setTipoIdPersona (TipoIdentificacionEnum tipoIdPersona){
 		this.tipoIdPersona=tipoIdPersona;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdPersona (){
 		return tipoIdPersona;
 	}
  	public void setNumeroIdBeneficiario (String numeroIdBeneficiario){
 		this.numeroIdBeneficiario=numeroIdBeneficiario;
 	}
 	
 	public String getNumeroIdBeneficiario (){
 		return numeroIdBeneficiario;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}