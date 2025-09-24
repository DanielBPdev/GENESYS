package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/obtenerSucursalEmpresa
 */
public class ObtenerSucursalEmpresa extends ServiceClient {
 
  
  	private String codigoSucursal;
  	private String numeroIdAportante;
  	private TipoIdentificacionEnum tipoIdAportante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SucursalEmpresaModeloDTO result;
  
 	public ObtenerSucursalEmpresa (String codigoSucursal,String numeroIdAportante,TipoIdentificacionEnum tipoIdAportante){
 		super();
		this.codigoSucursal=codigoSucursal;
		this.numeroIdAportante=numeroIdAportante;
		this.tipoIdAportante=tipoIdAportante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoSucursal", codigoSucursal)
						.queryParam("numeroIdAportante", numeroIdAportante)
						.queryParam("tipoIdAportante", tipoIdAportante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SucursalEmpresaModeloDTO) response.readEntity(SucursalEmpresaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SucursalEmpresaModeloDTO getResult() {
		return result;
	}

 
  	public void setCodigoSucursal (String codigoSucursal){
 		this.codigoSucursal=codigoSucursal;
 	}
 	
 	public String getCodigoSucursal (){
 		return codigoSucursal;
 	}
  	public void setNumeroIdAportante (String numeroIdAportante){
 		this.numeroIdAportante=numeroIdAportante;
 	}
 	
 	public String getNumeroIdAportante (){
 		return numeroIdAportante;
 	}
  	public void setTipoIdAportante (TipoIdentificacionEnum tipoIdAportante){
 		this.tipoIdAportante=tipoIdAportante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAportante (){
 		return tipoIdAportante;
 	}
  
}