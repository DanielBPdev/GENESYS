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
 * /rest/empresas/obtenerSucursalEmpresaByNombreAndEmpleador
 */
public class ObtenerSucursalEmpresaByNombreAndEmpleador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private String nombreSucursal;
  	private String numeroIdEmpleador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SucursalEmpresaModeloDTO result;
  
 	public ObtenerSucursalEmpresaByNombreAndEmpleador (TipoIdentificacionEnum tipoIdEmpleador,String nombreSucursal,String numeroIdEmpleador){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.nombreSucursal=nombreSucursal;
		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("nombreSucursal", nombreSucursal)
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
  	public void setNombreSucursal (String nombreSucursal){
 		this.nombreSucursal=nombreSucursal;
 	}
 	
 	public String getNombreSucursal (){
 		return nombreSucursal;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  
}