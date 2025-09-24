package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/consultarLegalizacionDesembolsoProveedor
 */
public class ConsultarLegalizacionDesembolsoProveedor extends ServiceClient {
 
  
  	private String sldId;
  	private String numeroRadicacion;
  	private String idlegalizacionDesembolosoProveedor;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<LegalizacionDesembolosoProveedorModeloDTO> result;
  
 	public ConsultarLegalizacionDesembolsoProveedor (String sldId,String numeroRadicacion,String idlegalizacionDesembolosoProveedor){
 		super();
		this.sldId=sldId;
		this.numeroRadicacion=numeroRadicacion;
		this.idlegalizacionDesembolosoProveedor=idlegalizacionDesembolosoProveedor;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("sldId", sldId)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("idlegalizacionDesembolosoProveedor", idlegalizacionDesembolosoProveedor)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<LegalizacionDesembolosoProveedorModeloDTO>) response.readEntity(new GenericType<List<LegalizacionDesembolosoProveedorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<LegalizacionDesembolosoProveedorModeloDTO> getResult() {
		return result;
	}

 
  	public void setSldId (String sldId){
 		this.sldId=sldId;
 	}
 	
 	public String getSldId (){
 		return sldId;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setIdlegalizacionDesembolosoProveedor (String idlegalizacionDesembolosoProveedor){
 		this.idlegalizacionDesembolosoProveedor=idlegalizacionDesembolosoProveedor;
 	}
 	
 	public String getIdlegalizacionDesembolosoProveedor (){
 		return idlegalizacionDesembolosoProveedor;
 	}
  
}