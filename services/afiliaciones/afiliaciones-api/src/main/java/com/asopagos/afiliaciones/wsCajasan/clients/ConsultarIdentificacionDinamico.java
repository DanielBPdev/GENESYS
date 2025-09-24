package com.asopagos.afiliaciones.wsCajasan.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.ResponseDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarInformacionDinamicoInDTO;
import javax.ws.rs.client.Entity;
/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalWebServiceAPI/afiliacion/consultarIdentificacionDinamico
 */
public class ConsultarIdentificacionDinamico extends ServiceClient {

  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numeroIdentificacion;
	private String tipoPersona;

  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResponseDTO result;
 	public ConsultarIdentificacionDinamico (TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion,String tipoPersona){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoPersona=tipoPersona;
 	}
	@Override
    protected Response invoke(WebTarget webTarget, String path) {
        ConsultarInformacionDinamicoInDTO input = new ConsultarInformacionDinamicoInDTO(tipoIdentificacion, numeroIdentificacion, tipoPersona);

        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON));
    }

	@Override
	protected void getResultData(Response response) {
		this.result = (ResponseDTO) response.readEntity(new GenericType<ResponseDTO>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResponseDTO getResult() {
		return result;
	}

  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
	public void setTipoPersona (String tipoPersona){
 		this.tipoPersona=tipoPersona;
 	}
 	
 	public String getTipoPersona (){
 		return tipoPersona;
 	}
  
}