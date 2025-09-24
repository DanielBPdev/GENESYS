package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.comunicados.dto.HistoricoEstadoEmpleador360DTO;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicoAfiliaciones/consultarHistoricoEstadosEmpleador
 */
public class ConsultarHistoricoEstadosEmpleador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private String numeroIdEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoEstadoEmpleador360DTO> result;
  
 	public ConsultarHistoricoEstadosEmpleador (TipoIdentificacionEnum tipoIdEmpleador,TipoSolicitanteMovimientoAporteEnum tipoAportante,String numeroIdEmpleador){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.tipoAportante=tipoAportante;
		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("tipoAportante", tipoAportante)
						.queryParam("numeroIdEmpleador", numeroIdEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoEstadoEmpleador360DTO>) response.readEntity(new GenericType<List<HistoricoEstadoEmpleador360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoEstadoEmpleador360DTO> getResult() {
		return result;
	}

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setTipoAportante (TipoSolicitanteMovimientoAporteEnum tipoAportante){
 		this.tipoAportante=tipoAportante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoAportante (){
 		return tipoAportante;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  
}