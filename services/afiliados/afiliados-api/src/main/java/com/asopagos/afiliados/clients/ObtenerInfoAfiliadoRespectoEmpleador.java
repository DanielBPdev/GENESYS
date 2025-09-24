package com.asopagos.afiliados.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliados.dto.InfoAfiliadoRespectoEmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/obtenerInfoAfiliadoRespectoEmpleador
 */
public class ObtenerInfoAfiliadoRespectoEmpleador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdEmpleador;
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private String numeroIdEmpleador;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoAfiliadoRespectoEmpleadorDTO result;
  
 	public ObtenerInfoAfiliadoRespectoEmpleador (TipoIdentificacionEnum tipoIdEmpleador,TipoIdentificacionEnum tipoIdAfiliado,String numeroIdEmpleador,String numeroIdAfiliado){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.numeroIdEmpleador=numeroIdEmpleador;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdEmpleador", tipoIdEmpleador)
						.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("numeroIdEmpleador", numeroIdEmpleador)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoAfiliadoRespectoEmpleadorDTO) response.readEntity(InfoAfiliadoRespectoEmpleadorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoAfiliadoRespectoEmpleadorDTO getResult() {
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
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}