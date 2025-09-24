package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.HistoricoAportes360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarHistoricoAportesAfiliado
 */
public class ConsultarHistoricoAportesAfiliado extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdAfiliado;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private Long idEmpresa;
  	private String numeroIdAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<HistoricoAportes360DTO> result;
  
 	public ConsultarHistoricoAportesAfiliado (TipoIdentificacionEnum tipoIdAfiliado,TipoAfiliadoEnum tipoAfiliado,Long idEmpresa,String numeroIdAfiliado){
 		super();
		this.tipoIdAfiliado=tipoIdAfiliado;
		this.tipoAfiliado=tipoAfiliado;
		this.idEmpresa=idEmpresa;
		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdAfiliado", tipoIdAfiliado)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("idEmpresa", idEmpresa)
						.queryParam("numeroIdAfiliado", numeroIdAfiliado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<HistoricoAportes360DTO>) response.readEntity(new GenericType<List<HistoricoAportes360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<HistoricoAportes360DTO> getResult() {
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
  	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  	public void setNumeroIdAfiliado (String numeroIdAfiliado){
 		this.numeroIdAfiliado=numeroIdAfiliado;
 	}
 	
 	public String getNumeroIdAfiliado (){
 		return numeroIdAfiliado;
 	}
  
}