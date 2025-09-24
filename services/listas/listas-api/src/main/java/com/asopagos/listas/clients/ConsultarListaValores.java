package com.asopagos.listas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ElementoListaDTO;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/listasValores/{idListaValores}
 */
public class ConsultarListaValores extends ServiceClient {
 
  	private Integer idListaValores;
  
  	private String nombreFiltro;
  	private String valorFiltro;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ElementoListaDTO> result;
  
 	public ConsultarListaValores (Integer idListaValores,String nombreFiltro,String valorFiltro){
 		super();
		this.idListaValores=idListaValores;
		this.nombreFiltro=nombreFiltro;
		this.valorFiltro=valorFiltro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idListaValores", idListaValores)
									.queryParam("nombreFiltro", nombreFiltro)
						.queryParam("valorFiltro", valorFiltro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ElementoListaDTO>) response.readEntity(new GenericType<List<ElementoListaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ElementoListaDTO> getResult() {
		return result;
	}

 	public void setIdListaValores (Integer idListaValores){
 		this.idListaValores=idListaValores;
 	}
 	
 	public Integer getIdListaValores (){
 		return idListaValores;
 	}
  
  	public void setNombreFiltro (String nombreFiltro){
 		this.nombreFiltro=nombreFiltro;
 	}
 	
 	public String getNombreFiltro (){
 		return nombreFiltro;
 	}
  	public void setValorFiltro (String valorFiltro){
 		this.valorFiltro=valorFiltro;
 	}
 	
 	public String getValorFiltro (){
 		return valorFiltro;
 	}
  
}