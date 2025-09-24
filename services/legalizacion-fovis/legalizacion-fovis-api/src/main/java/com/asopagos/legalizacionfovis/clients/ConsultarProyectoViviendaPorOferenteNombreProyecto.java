package com.asopagos.legalizacionfovis.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarProyectoViviendaPorOferenteNombreProyecto
 */
public class ConsultarProyectoViviendaPorOferenteNombreProyecto extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private String nombreProyecto;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ProyectoSolucionViviendaModeloDTO result;
  
 	public ConsultarProyectoViviendaPorOferenteNombreProyecto (String numeroIdentificacion,String nombreProyecto,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.nombreProyecto=nombreProyecto;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("nombreProyecto", nombreProyecto)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ProyectoSolucionViviendaModeloDTO) response.readEntity(ProyectoSolucionViviendaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ProyectoSolucionViviendaModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setNombreProyecto (String nombreProyecto){
 		this.nombreProyecto=nombreProyecto;
 	}
 	
 	public String getNombreProyecto (){
 		return nombreProyecto;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}