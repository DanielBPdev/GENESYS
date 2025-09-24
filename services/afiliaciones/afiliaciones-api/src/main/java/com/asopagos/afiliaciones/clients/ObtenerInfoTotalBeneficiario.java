package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliaciones.dto.InfoTotalBeneficiarioOutDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerInfoTotalBeneficiario
 */
public class ObtenerInfoTotalBeneficiario extends ServiceClient {
 
  
  	private String identificacionAfiliado;
  	private TipoIdentificacionEnum tipoID;
  	private String identificacionBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InfoTotalBeneficiarioOutDTO> result;
  
 	public ObtenerInfoTotalBeneficiario (String identificacionAfiliado,TipoIdentificacionEnum tipoID,String identificacionBeneficiario){
 		super();
		this.identificacionAfiliado=identificacionAfiliado;
		this.tipoID=tipoID;
		this.identificacionBeneficiario=identificacionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificacionAfiliado", identificacionAfiliado)
						.queryParam("tipoID", tipoID)
						.queryParam("identificacionBeneficiario", identificacionBeneficiario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InfoTotalBeneficiarioOutDTO>) response.readEntity(new GenericType<List<InfoTotalBeneficiarioOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InfoTotalBeneficiarioOutDTO> getResult() {
		return result;
	}

 
  	public void setIdentificacionAfiliado (String identificacionAfiliado){
 		this.identificacionAfiliado=identificacionAfiliado;
 	}
 	
 	public String getIdentificacionAfiliado (){
 		return identificacionAfiliado;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setIdentificacionBeneficiario (String identificacionBeneficiario){
 		this.identificacionBeneficiario=identificacionBeneficiario;
 	}
 	
 	public String getIdentificacionBeneficiario (){
 		return identificacionBeneficiario;
 	}
  
}