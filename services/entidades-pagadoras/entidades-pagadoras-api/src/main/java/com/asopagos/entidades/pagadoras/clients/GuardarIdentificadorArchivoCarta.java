package com.asopagos.entidades.pagadoras.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas/guardarCarta
 */
public class GuardarIdentificadorArchivoCarta extends ServiceClient { 
  	private Long idEntidadPagadora;
   	private TipoGestionSolicitudAsociacionEnum tipoGestion;
  	private String identificadorArchivoCarta;
   	private List<String> numerosRadicado;
  
  
 	public GuardarIdentificadorArchivoCarta (Long idEntidadPagadora,TipoGestionSolicitudAsociacionEnum tipoGestion,String identificadorArchivoCarta,List<String> numerosRadicado){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
		this.tipoGestion=tipoGestion;
		this.identificadorArchivoCarta=identificadorArchivoCarta;
		this.numerosRadicado=numerosRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
			.queryParam("tipoGestion", tipoGestion)
			.queryParam("identificadorArchivoCarta", identificadorArchivoCarta)
			.request(MediaType.APPLICATION_JSON)
			.post(numerosRadicado == null ? null : Entity.json(numerosRadicado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEntidadPagadora (Long idEntidadPagadora){
 		this.idEntidadPagadora=idEntidadPagadora;
 	}
 	
 	public Long getIdEntidadPagadora (){
 		return idEntidadPagadora;
 	}
  
  	public void setTipoGestion (TipoGestionSolicitudAsociacionEnum tipoGestion){
 		this.tipoGestion=tipoGestion;
 	}
 	
 	public TipoGestionSolicitudAsociacionEnum getTipoGestion (){
 		return tipoGestion;
 	}
  	public void setIdentificadorArchivoCarta (String identificadorArchivoCarta){
 		this.identificadorArchivoCarta=identificadorArchivoCarta;
 	}
 	
 	public String getIdentificadorArchivoCarta (){
 		return identificadorArchivoCarta;
 	}
  
  	public void setNumerosRadicado (List<String> numerosRadicado){
 		this.numerosRadicado=numerosRadicado;
 	}
 	
 	public List<String> getNumerosRadicado (){
 		return numerosRadicado;
 	}
  
}