package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarBitacoraSinResultado
 */
public class ConsultarBitacoraSinResultado extends ServiceClient {
 
  
  	private Long numeroOperacion;
  	private TipoActividadBitacoraEnum actividad;
  	private List<ResultadoBitacoraCarteraEnum> resultado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BitacoraCarteraDTO> result;
  
 	public ConsultarBitacoraSinResultado (Long numeroOperacion,TipoActividadBitacoraEnum actividad,List<ResultadoBitacoraCarteraEnum> resultado){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.actividad=actividad;
		this.resultado=resultado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("actividad", actividad)
						.queryParam("resultado", resultado.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BitacoraCarteraDTO>) response.readEntity(new GenericType<List<BitacoraCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BitacoraCarteraDTO> getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setActividad (TipoActividadBitacoraEnum actividad){
 		this.actividad=actividad;
 	}
 	
 	public TipoActividadBitacoraEnum getActividad (){
 		return actividad;
 	}
  	public void setResultado (List<ResultadoBitacoraCarteraEnum> resultado){
 		this.resultado=resultado;
 	}
 	
 	public List<ResultadoBitacoraCarteraEnum> getResultado (){
 		return resultado;
 	}
  
}