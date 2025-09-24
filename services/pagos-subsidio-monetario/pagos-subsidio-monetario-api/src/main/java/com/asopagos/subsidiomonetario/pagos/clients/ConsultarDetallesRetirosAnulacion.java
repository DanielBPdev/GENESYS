package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarDetallesRetirosAnulacion
 */
public class ConsultarDetallesRetirosAnulacion extends ServiceClient {
 
  
  	private TipoTransaccionSubsidioMonetarioEnum tipoTransaccion;
  	private Long idCuentaAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSubsidioAsignadoDTO> result;
  
 	public ConsultarDetallesRetirosAnulacion (TipoTransaccionSubsidioMonetarioEnum tipoTransaccion,Long idCuentaAdmin){
 		super();
		this.tipoTransaccion=tipoTransaccion;
		this.idCuentaAdmin=idCuentaAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoTransaccion", tipoTransaccion)
						.queryParam("idCuentaAdmin", idCuentaAdmin)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DetalleSubsidioAsignadoDTO>) response.readEntity(new GenericType<List<DetalleSubsidioAsignadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleSubsidioAsignadoDTO> getResult() {
		return result;
	}

 
  	public void setTipoTransaccion (TipoTransaccionSubsidioMonetarioEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionSubsidioMonetarioEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  	public void setIdCuentaAdmin (Long idCuentaAdmin){
 		this.idCuentaAdmin=idCuentaAdmin;
 	}
 	
 	public Long getIdCuentaAdmin (){
 		return idCuentaAdmin;
 	}
  
}