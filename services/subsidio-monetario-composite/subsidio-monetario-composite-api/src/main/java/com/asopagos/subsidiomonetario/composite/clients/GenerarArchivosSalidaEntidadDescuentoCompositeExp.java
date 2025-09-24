package com.asopagos.subsidiomonetario.composite.clients;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.services.common.ServiceClient;
/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetarioComposite/generarArchivoPersonasSinDerechoEmpleador/{numeroRadicacion}
 */
public class GenerarArchivosSalidaEntidadDescuentoCompositeExp extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public GenerarArchivosSalidaEntidadDescuentoCompositeExp (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Response getResult() {
		return result;
	}
 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
}