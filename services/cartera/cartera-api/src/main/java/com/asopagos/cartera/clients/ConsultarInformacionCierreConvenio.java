package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.cartera.dto.DatosComunicadoCierreConvenioDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarInformacionCierreConvenio
 */
public class ConsultarInformacionCierreConvenio extends ServiceClient {
 
  
  	private Long idPersona;
  	private TipoSolicitanteMovimientoAporteEnum tipo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosComunicadoCierreConvenioDTO result;
  
 	public ConsultarInformacionCierreConvenio (Long idPersona,TipoSolicitanteMovimientoAporteEnum tipo){
 		super();
		this.idPersona=idPersona;
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("tipo", tipo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatosComunicadoCierreConvenioDTO) response.readEntity(DatosComunicadoCierreConvenioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosComunicadoCierreConvenioDTO getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setTipo (TipoSolicitanteMovimientoAporteEnum tipo){
 		this.tipo=tipo;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipo (){
 		return tipo;
 	}
  
}