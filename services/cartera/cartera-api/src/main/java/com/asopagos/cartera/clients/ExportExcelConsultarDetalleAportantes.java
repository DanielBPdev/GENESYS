package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{lineaCobro}/exportExcelConsultarDetalleAportantes
 */
public class ExportExcelConsultarDetalleAportantes extends ServiceClient {
 
  	private TipoLineaCobroEnum lineaCobro;
  
  	private TipoAccionCobroEnum accionCobro;
  	private String usuarioAnalista;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private byte[] result;
  
 	public ExportExcelConsultarDetalleAportantes (TipoLineaCobroEnum lineaCobro,TipoAccionCobroEnum accionCobro,String usuarioAnalista){
 		super();
		this.lineaCobro=lineaCobro;
		this.accionCobro=accionCobro;
		this.usuarioAnalista=usuarioAnalista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("lineaCobro", lineaCobro)
									.queryParam("accionCobro", accionCobro)
						.queryParam("usuarioAnalista", usuarioAnalista)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (byte[]) response.readEntity(byte[].class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public byte[] getResult() {
		return result;
	}

 	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
 	}
  
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  	public void setUsuarioAnalista (String usuarioAnalista){
 		this.usuarioAnalista=usuarioAnalista;
 	}
 	
 	public String getUsuarioAnalista (){
 		return usuarioAnalista;
 	}
  
}