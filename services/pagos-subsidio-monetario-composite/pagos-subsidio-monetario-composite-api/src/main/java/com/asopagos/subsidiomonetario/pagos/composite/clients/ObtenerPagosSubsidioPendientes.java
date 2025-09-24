package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/obtenerPagosSubsidioPendientes
 */
public class ObtenerPagosSubsidioPendientes extends ServiceClient {
 
  
  	private String numeroRadicacion;
  	private String numeroIdAdmin;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PagoSubsidioProgramadoDTO> result;
  
 	public ObtenerPagosSubsidioPendientes (String numeroRadicacion,String numeroIdAdmin,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.numeroIdAdmin=numeroIdAdmin;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PagoSubsidioProgramadoDTO>) response.readEntity(new GenericType<List<PagoSubsidioProgramadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PagoSubsidioProgramadoDTO> getResult() {
		return result;
	}

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setNumeroIdAdmin (String numeroIdAdmin){
 		this.numeroIdAdmin=numeroIdAdmin;
 	}
 	
 	public String getNumeroIdAdmin (){
 		return numeroIdAdmin;
 	}
  	public void setTipoIdAdmin (TipoIdentificacionEnum tipoIdAdmin){
 		this.tipoIdAdmin=tipoIdAdmin;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAdmin (){
 		return tipoIdAdmin;
 	}
  
}