package com.asopagos.validaciones.fovis.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import java.util.Map;
import java.lang.String;
import com.asopagos.dto.ParametrizacionNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/validacionesAPI/novedades/habilitar
 */
public class ValidadorNovedadesHabilitadasFovis extends ServiceClient {
   	private List<String> objetoValidacion;
  	private ProcesoEnum proceso;
  	private TipoNovedadEnum tipoNovedad;
   	private Map<String,String> datosValidacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionNovedadDTO> result;
  
 	public ValidadorNovedadesHabilitadasFovis(List<String> objetoValidacion, ProcesoEnum proceso, TipoNovedadEnum tipoNovedad, Map<String,String> datosValidacion){
 		super();
		this.objetoValidacion=objetoValidacion;
		this.proceso=proceso;
		this.tipoNovedad=tipoNovedad;
		this.datosValidacion=datosValidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("objetoValidacion", objetoValidacion)
			.queryParam("proceso", proceso)
			.queryParam("tipoNovedad", tipoNovedad)
			.request(MediaType.APPLICATION_JSON)
			.post(datosValidacion == null ? null : Entity.json(datosValidacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ParametrizacionNovedadDTO>) response.readEntity(new GenericType<List<ParametrizacionNovedadDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ParametrizacionNovedadDTO> getResult() {
		return result;
	}

 
  	public void setObjetoValidacion (List<String> objetoValidacion){
 		this.objetoValidacion=objetoValidacion;
 	}
 	
 	public List<String> getObjetoValidacion (){
 		return objetoValidacion;
 	}
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setTipoNovedad (TipoNovedadEnum tipoNovedad){
 		this.tipoNovedad=tipoNovedad;
 	}
 	
 	public TipoNovedadEnum getTipoNovedad (){
 		return tipoNovedad;
 	}
  
  	public void setDatosValidacion (Map<String,String> datosValidacion){
 		this.datosValidacion=datosValidacion;
 	}
 	
 	public Map<String,String> getDatosValidacion (){
 		return datosValidacion;
 	}
  
}