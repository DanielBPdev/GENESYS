package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.GruposMedioTarjetaDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarGruposTrabajadorMedioTarjeta
 */
public class ConsultarGruposTrabajadorMedioTarjeta extends ServiceClient {
 
  
  	private String numeroTarjeta;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String identificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GruposMedioTarjetaDTO> result;
  
 	public ConsultarGruposTrabajadorMedioTarjeta (String numeroTarjeta,TipoIdentificacionEnum tipoIdentificacion,String identificacion){
 		super();
		this.numeroTarjeta=numeroTarjeta;
		this.tipoIdentificacion=tipoIdentificacion;
		this.identificacion=identificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroTarjeta", numeroTarjeta)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("identificacion", identificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<GruposMedioTarjetaDTO>) response.readEntity(new GenericType<List<GruposMedioTarjetaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<GruposMedioTarjetaDTO> getResult() {
		return result;
	}

 
  	public void setNumeroTarjeta (String numeroTarjeta){
 		this.numeroTarjeta=numeroTarjeta;
 	}
 	
 	public String getNumeroTarjeta (){
 		return numeroTarjeta;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  
}