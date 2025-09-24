package com.asopagos.afiliaciones.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class ConsultarPersonaPensionado25Anios extends ServiceClient {

    private TipoIdentificacionEnum tipoDocumento;
    // private String idEmpleador;
    private String numeroDocumento;

  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Afiliado25AniosExistenteDTO result;
  
 	public ConsultarPersonaPensionado25Anios(TipoIdentificacionEnum tipoDocumento, String numeroDocumento){
 		super();
		this.tipoDocumento=tipoDocumento;
		// this.idEmpleador=idEmpleador;
		this.numeroDocumento=numeroDocumento;
		System.out.println("Aquiiiii:"+" "+tipoDocumento+" "+numeroDocumento+" ");


 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("tipoDocumento", tipoDocumento)
			.resolveTemplate("numeroDocumento", numeroDocumento)
			.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Afiliado25AniosExistenteDTO) response.readEntity(Afiliado25AniosExistenteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Afiliado25AniosExistenteDTO getResult() {
		return result;
	}

 	public void setTipoDocumento(TipoIdentificacionEnum tipoDocumento){
 		this.tipoDocumento=tipoDocumento;
 	}
 	
 	public TipoIdentificacionEnum getTipoDocumento (){
 		return tipoDocumento;
 	}
  
  	public void setNumeroDocumento (String numeroDocumento){
 		this.numeroDocumento=numeroDocumento;
 	}
 	
 	public String getNumeroDocumento (){
 		return numeroDocumento;
 	}
    
}