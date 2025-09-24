package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/obtenerCategoriasPropiasAfiliado
 */
public class ObtenerCategoriasPropiasAfiliado extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CategoriasComoAfiliadoPrincipalDTO result;
  
 	public ObtenerCategoriasPropiasAfiliado (TipoIdentificacionEnum tipoIdAfiliado,TipoAfiliadoEnum tipoAfiliado,String numeroIdAfiliado){
 		super();
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.tipoAfiliado=tipoAfiliado;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CategoriasComoAfiliadoPrincipalDTO) response.readEntity(CategoriasComoAfiliadoPrincipalDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CategoriasComoAfiliadoPrincipalDTO getResult() {
		return result;
	}

 
  	public void setTipoIdAfiliado (TipoIdentificacionEnum tipoIdAfiliado){
 		this.tipoIdAfiliado=tipoIdAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAfiliado (){
 		return tipoIdAfiliado;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}