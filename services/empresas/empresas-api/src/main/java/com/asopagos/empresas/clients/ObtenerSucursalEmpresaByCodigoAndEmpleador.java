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
 * /rest/empresas/obtenerSucursalEmpresaByCodigoAndEmpleador
 */
public class ObtenerSucursalEmpresaByCodigoAndEmpleador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private String codigoSucursal;
  	private String numeroIdEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SucursalEmpresaModeloDTO result;
  
 	public ObtenerSucursalEmpresaByCodigoAndEmpleador (TipoIdentificacionEnum tipoIdEmpleador,String codigoSucursal,String numeroIdEmpleador){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.codigoSucursal=codigoSucursal;
		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("codigoSucursal", codigoSucursal)
						.queryParam("numeroIdEmpleador", numeroIdEmpleador)
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

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setCodigoSucursal (String codigoSucursal){
 		this.codigoSucursal=codigoSucursal;
 	}
 	
 	public String getCodigoSucursal (){
 		return codigoSucursal;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  
}