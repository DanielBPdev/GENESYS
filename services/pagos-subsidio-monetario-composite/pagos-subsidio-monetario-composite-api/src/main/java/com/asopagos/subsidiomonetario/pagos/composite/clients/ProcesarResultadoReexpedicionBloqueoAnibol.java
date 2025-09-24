package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/procesarResultadoReexpedicionBloqueoAnibol
 */
public class ProcesarResultadoReexpedicionBloqueoAnibol extends ServiceClient { 
    	private List<ResultadoReexpedicionBloqueoInDTO> listaConsulta;
  
  
 	public ProcesarResultadoReexpedicionBloqueoAnibol (List<ResultadoReexpedicionBloqueoInDTO> listaConsulta){
 		super();
		this.listaConsulta=listaConsulta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaConsulta == null ? null : Entity.json(listaConsulta));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaConsulta (List<ResultadoReexpedicionBloqueoInDTO> listaConsulta){
 		this.listaConsulta=listaConsulta;
 	}
 	
 	public List<ResultadoReexpedicionBloqueoInDTO> getListaConsulta (){
 		return listaConsulta;
 	}
  
}