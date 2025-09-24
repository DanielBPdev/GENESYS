package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliados.dto.HistoricoAfiBeneficiario360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarHistoricoAfiliadoComoBeneficiario
 */
public class ConsultarHistoricoAfiliadoComoBeneficiario extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoAfiBeneficiario360DTO> result;
  
 	public ConsultarHistoricoAfiliadoComoBeneficiario (TipoIdentificacionEnum tipoIdAfiliado,String numeroIdAfiliado){
 		super();
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoAfiBeneficiario360DTO>) response.readEntity(new GenericType<List<HistoricoAfiBeneficiario360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoAfiBeneficiario360DTO> getResult() {
		return result;
	}

 
  	public void setTipoIdAfiliado (TipoIdentificacionEnum tipoIdAfiliado){
 		this.tipoIdAfiliado=tipoIdAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAfiliado (){
 		return tipoIdAfiliado;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}