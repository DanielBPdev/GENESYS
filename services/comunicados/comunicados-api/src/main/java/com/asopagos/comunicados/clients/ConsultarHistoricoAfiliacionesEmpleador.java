package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.comunicados.dto.HistoricoAfiliacionEmpleador360DTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicoAfiliaciones/consultarHistoricoAfiliacionesEmpleador
 */
public class ConsultarHistoricoAfiliacionesEmpleador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private String numeroIdEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoAfiliacionEmpleador360DTO> result;
  
 	public ConsultarHistoricoAfiliacionesEmpleador (TipoIdentificacionEnum tipoIdEmpleador,String numeroIdEmpleador){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("numeroIdEmpleador", numeroIdEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoAfiliacionEmpleador360DTO>) response.readEntity(new GenericType<List<HistoricoAfiliacionEmpleador360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoAfiliacionEmpleador360DTO> getResult() {
		return result;
	}

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  
}