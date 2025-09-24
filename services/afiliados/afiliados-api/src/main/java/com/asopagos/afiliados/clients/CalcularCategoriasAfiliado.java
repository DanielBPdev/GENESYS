package com.asopagos.afiliados.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import java.util.Map;
import com.asopagos.dto.CategoriaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/calcularCategoriasAfiliado
 */
public class CalcularCategoriasAfiliado extends ServiceClient { 
    	private CategoriaDTO categoriaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<TipoAfiliadoEnum,CategoriaPersonaEnum> result;
  
 	public CalcularCategoriasAfiliado (CategoriaDTO categoriaDTO){
 		super();
		this.categoriaDTO=categoriaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(categoriaDTO == null ? null : Entity.json(categoriaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<TipoAfiliadoEnum,CategoriaPersonaEnum>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<TipoAfiliadoEnum,CategoriaPersonaEnum> getResult() {
		return result;
	}

 
  
  	public void setCategoriaDTO (CategoriaDTO categoriaDTO){
 		this.categoriaDTO=categoriaDTO;
 	}
 	
 	public CategoriaDTO getCategoriaDTO (){
 		return categoriaDTO;
 	}
  
}