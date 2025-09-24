package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliados.dto.EmpleadorRelacionadoAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/obtenerEmpleadoresRelacionadosAfiliado
 */
public class ObtenerEmpleadoresRelacionadosAfiliado extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private String numeroIdEmpleador;
  	private String razonSocial;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EmpleadorRelacionadoAfiliadoDTO> result;
  
 	public ObtenerEmpleadoresRelacionadosAfiliado (TipoIdentificacionEnum tipoIdEmpleador,TipoIdentificacionEnum tipoIdAfiliado,String numeroIdEmpleador,String razonSocial,String numeroIdAfiliado){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.numeroIdEmpleador=numeroIdEmpleador;
		this.razonSocial=razonSocial;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("numeroIdEmpleador", numeroIdEmpleador)
						.queryParam("razonSocial", razonSocial)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<EmpleadorRelacionadoAfiliadoDTO>) response.readEntity(new GenericType<List<EmpleadorRelacionadoAfiliadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EmpleadorRelacionadoAfiliadoDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setTipoIdAfiliado (TipoIdentificacionEnum tipoIdAfiliado){
 		this.tipoIdAfiliado=tipoIdAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAfiliado (){
 		return tipoIdAfiliado;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  	public void setRazonSocial (String razonSocial){
 		this.razonSocial=razonSocial;
 	}
 	
 	public String getRazonSocial (){
 		return razonSocial;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}