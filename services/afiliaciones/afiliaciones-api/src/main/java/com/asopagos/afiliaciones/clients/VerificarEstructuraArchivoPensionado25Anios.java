package com.asopagos.afiliaciones.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class VerificarEstructuraArchivoPensionado25Anios extends ServiceClient {

    private TipoArchivoRespuestaEnum tipoArchivo;
    // private String idEmpleador;
    private InformacionArchivoDTO archivo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoArchivo25AniosDTO result;
  
 	public VerificarEstructuraArchivoPensionado25Anios(TipoArchivoRespuestaEnum tipoArchivo,InformacionArchivoDTO archivo){
 		super();
		this.tipoArchivo=tipoArchivo;
		// this.idEmpleador=idEmpleador;
		this.archivo=archivo;
		System.out.println("Aquiiiii:"+" "+tipoArchivo+" "+archivo+" ");


 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		System.out.println("Aca"+" "+tipoArchivo+" "+archivo+" "+path);
		Response response = webTarget.path(path)
			.resolveTemplate("tipoArchivo", tipoArchivo)
			.request(MediaType.APPLICATION_JSON)
			.post(archivo == null ? null : Entity.json(archivo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoArchivo25AniosDTO) response.readEntity(ResultadoArchivo25AniosDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoArchivo25AniosDTO getResult() {
		return result;
	}

 	public void setTipoArchivo(TipoArchivoRespuestaEnum tipoArchivo){
 		this.tipoArchivo=tipoArchivo;
 	}
 	
 	public TipoArchivoRespuestaEnum getTipoArchivo (){
 		return tipoArchivo;
 	}
 	// public void setIdEmpleador(String idEmpleador){
 	// 	this.idEmpleador=idEmpleador;
 	// }
 	
 	// public String getIdEmpleador (){
 	// 	return idEmpleador;
 	// }
  
  
  	public void setArchivo (InformacionArchivoDTO archivo){
 		this.archivo=archivo;
 	}
 	
 	public InformacionArchivoDTO getArchivo (){
 		return archivo;
 	}
    
}