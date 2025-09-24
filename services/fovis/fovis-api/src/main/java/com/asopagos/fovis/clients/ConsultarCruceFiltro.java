package com.asopagos.fovis.clients;

import com.asopagos.dto.CruceDetalleDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.TipoInformacionCruceEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCruceFiltro
 */
public class ConsultarCruceFiltro extends ServiceClient {
 
  
  	private TipoInformacionCruceEnum tipo;
  	private String identificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDetalleDTO> result;
  
 	public ConsultarCruceFiltro (TipoInformacionCruceEnum tipo,String identificacion){
 		super();
		this.tipo=tipo;
		this.identificacion=identificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipo", tipo)
						.queryParam("identificacion", identificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CruceDetalleDTO>) response.readEntity(new GenericType<List<CruceDetalleDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CruceDetalleDTO> getResult() {
		return result;
	}

 
  	public void setTipo (TipoInformacionCruceEnum tipo){
 		this.tipo=tipo;
 	}
 	
 	public TipoInformacionCruceEnum getTipo (){
 		return tipo;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  
}