package com.asopagos.entidades.pagadoras.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas
 */
public class ConsultarSolicitudesAsociacionPersonas extends ServiceClient {
 
  	private Long idEntidadPagadora;
  
  	private TipoGestionSolicitudAsociacionEnum tipoGestion;
  	private String numeroRadicado;
  	private String consecutivoGestion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAsociacionPersonaEntidadPagadoraDTO> result;
  
 	public ConsultarSolicitudesAsociacionPersonas (Long idEntidadPagadora,TipoGestionSolicitudAsociacionEnum tipoGestion,String numeroRadicado,String consecutivoGestion){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
		this.tipoGestion=tipoGestion;
		this.numeroRadicado=numeroRadicado;
		this.consecutivoGestion=consecutivoGestion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
									.queryParam("tipoGestion", tipoGestion)
						.queryParam("numeroRadicado", numeroRadicado)
						.queryParam("consecutivoGestion", consecutivoGestion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudAsociacionPersonaEntidadPagadoraDTO>) response.readEntity(new GenericType<List<SolicitudAsociacionPersonaEntidadPagadoraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> getResult() {
		return result;
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
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  	public void setConsecutivoGestion (String consecutivoGestion){
 		this.consecutivoGestion=consecutivoGestion;
 	}
 	
 	public String getConsecutivoGestion (){
 		return consecutivoGestion;
 	}
  
}