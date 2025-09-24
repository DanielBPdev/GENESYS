package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import java.util.List;
import com.asopagos.entidades.seguridad.Pregunta;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/autenticacion/preguntas/{estado}
 */
public class ConsultarPreguntasPorEstado extends ServiceClient {
 
  	private EstadoActivoInactivoEnum estado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Pregunta> result;
  
 	public ConsultarPreguntasPorEstado (EstadoActivoInactivoEnum estado){
 		super();
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("estado", estado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Pregunta>) response.readEntity(new GenericType<List<Pregunta>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Pregunta> getResult() {
		return result;
	}

 	public void setEstado (EstadoActivoInactivoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoActivoInactivoEnum getEstado (){
 		return estado;
 	}
  
  
}