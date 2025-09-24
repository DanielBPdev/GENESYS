package com.asopagos.asignaciones.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import com.asopagos.asignaciones.dto.InformacionProcesoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/asignacionSolicitud/reasignacion/consulta
 */
public class ConsultarProcesosReasignacion extends ServiceClient {
 
  
  	private ProcesoEnum proceso;
  	private String grupo;
  	private Long sede;
  	private String usuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InformacionProcesoDTO> result;
  
 	public ConsultarProcesosReasignacion (ProcesoEnum proceso,String grupo,Long sede,String usuario){
 		super();
		this.proceso=proceso;
		this.grupo=grupo;
		this.sede=sede;
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("proceso", proceso)
						.queryParam("grupo", grupo)
						.queryParam("sede", sede)
						.queryParam("usuario", usuario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InformacionProcesoDTO>) response.readEntity(new GenericType<List<InformacionProcesoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InformacionProcesoDTO> getResult() {
		return result;
	}

 
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setGrupo (String grupo){
 		this.grupo=grupo;
 	}
 	
 	public String getGrupo (){
 		return grupo;
 	}
  	public void setSede (Long sede){
 		this.sede=sede;
 	}
 	
 	public Long getSede (){
 		return sede;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  
}