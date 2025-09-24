package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliaciones.dto.InfoTotalEmpleadorOutDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerInfoTotalEmpleador
 */
public class ObtenerInfoTotalEmpleador extends ServiceClient {
 
  
  	private String codigoSucursal;
  	private String identificacionAfiliado;
  	private TipoIdentificacionEnum tipoID;
  	private String identificacionEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InfoTotalEmpleadorOutDTO> result;
  
 	public ObtenerInfoTotalEmpleador (String codigoSucursal,String identificacionAfiliado,TipoIdentificacionEnum tipoID,String identificacionEmpleador){
 		super();
		this.codigoSucursal=codigoSucursal;
		this.identificacionAfiliado=identificacionAfiliado;
		this.tipoID=tipoID;
		this.identificacionEmpleador=identificacionEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoSucursal", codigoSucursal)
						.queryParam("identificacionAfiliado", identificacionAfiliado)
						.queryParam("tipoID", tipoID)
						.queryParam("identificacionEmpleador", identificacionEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InfoTotalEmpleadorOutDTO>) response.readEntity(new GenericType<List<InfoTotalEmpleadorOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InfoTotalEmpleadorOutDTO> getResult() {
		return result;
	}

 
  	public void setCodigoSucursal (String codigoSucursal){
 		this.codigoSucursal=codigoSucursal;
 	}
 	
 	public String getCodigoSucursal (){
 		return codigoSucursal;
 	}
  	public void setIdentificacionAfiliado (String identificacionAfiliado){
 		this.identificacionAfiliado=identificacionAfiliado;
 	}
 	
 	public String getIdentificacionAfiliado (){
 		return identificacionAfiliado;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setIdentificacionEmpleador (String identificacionEmpleador){
 		this.identificacionEmpleador=identificacionEmpleador;
 	}
 	
 	public String getIdentificacionEmpleador (){
 		return identificacionEmpleador;
 	}
  
}