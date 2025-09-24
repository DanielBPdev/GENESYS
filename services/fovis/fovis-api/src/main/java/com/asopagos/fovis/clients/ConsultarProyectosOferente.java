package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarProyectosOferente
 */
public class ConsultarProyectosOferente extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private Boolean proyectosConLicencia;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Boolean proyectoRegistrado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ProyectoSolucionViviendaModeloDTO> result;
  
 	public ConsultarProyectosOferente (TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,Boolean proyectoRegistrado, Boolean proyectosConLicencia){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.proyectosConLicencia=proyectosConLicencia;
		this.tipoIdentificacion=tipoIdentificacion;
		this.proyectoRegistrado=proyectoRegistrado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("proyectoRegistrado", proyectoRegistrado)
						.queryParam("proyectosConLicencia", proyectosConLicencia)						
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ProyectoSolucionViviendaModeloDTO>) response.readEntity(new GenericType<List<ProyectoSolucionViviendaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ProyectoSolucionViviendaModeloDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setProyectosConLicencia (Boolean proyectosConLicencia){
 		this.proyectosConLicencia=proyectosConLicencia;
 	}
 	
 	public Boolean getProyectosConLicencia (){
 		return proyectosConLicencia;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setProyectoRegistrado (Boolean proyectoRegistrado){
 		this.proyectoRegistrado=proyectoRegistrado;
 	}
 	
 	public Boolean getProyectoRegistrado (){
 		return proyectoRegistrado;
 	}
  
}