package com.asopagos.reportes.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/obtenerCategoriasHeredadas
 */
public class ObtenerCategoriasHeredadas extends ServiceClient {
 
  
  	private Boolean isAfiliadoPrincipal;
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CategoriasComoBeneficiarioDTO result;
  
 	public ObtenerCategoriasHeredadas (Boolean isAfiliadoPrincipal,TipoIdentificacionEnum tipoIdAfiliado,String numeroIdAfiliado){
 		super();
		this.isAfiliadoPrincipal=isAfiliadoPrincipal;
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("isAfiliadoPrincipal", isAfiliadoPrincipal)
						.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CategoriasComoBeneficiarioDTO) response.readEntity(CategoriasComoBeneficiarioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CategoriasComoBeneficiarioDTO getResult() {
		return result;
	}

 
  	public void setIsAfiliadoPrincipal (Boolean isAfiliadoPrincipal){
 		this.isAfiliadoPrincipal=isAfiliadoPrincipal;
 	}
 	
 	public Boolean getIsAfiliadoPrincipal (){
 		return isAfiliadoPrincipal;
 	}
  	public void setTipoIdAfiliado (TipoIdentificacionEnum tipoIdAfiliado){
 		this.tipoIdAfiliado=tipoIdAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAfiliado (){
 		return tipoIdAfiliado;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}