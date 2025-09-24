package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.dto.InfoAfiliadoOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerInfoAfiliado
 */
public class ObtenerInfoAfiliado extends ServiceClient {
 
  
  	private String codigoCaja;
  	private TipoIdentificacionEnum tipoID;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String identificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InfoAfiliadoOutDTO> result;
  
 	public ObtenerInfoAfiliado (String codigoCaja,TipoIdentificacionEnum tipoID,TipoAfiliadoEnum tipoAfiliado,String identificacion){
 		super();
		this.codigoCaja=codigoCaja;
		this.tipoID=tipoID;
		this.tipoAfiliado=tipoAfiliado;
		this.identificacion=identificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoCaja", codigoCaja)
						.queryParam("tipoID", tipoID)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("identificacion", identificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InfoAfiliadoOutDTO>) response.readEntity(new GenericType<List<InfoAfiliadoOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InfoAfiliadoOutDTO> getResult() {
		return result;
	}

 
  	public void setCodigoCaja (String codigoCaja){
 		this.codigoCaja=codigoCaja;
 	}
 	
 	public String getCodigoCaja (){
 		return codigoCaja;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  
}