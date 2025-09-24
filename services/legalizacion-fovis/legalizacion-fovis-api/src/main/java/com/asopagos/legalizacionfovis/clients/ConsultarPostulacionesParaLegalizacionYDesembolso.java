package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.legalizacionfovis.dto.SolicitudPostulacionLegalizacionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarPostulacionesParaLegalizacionYDesembolso
 */
public class ConsultarPostulacionesParaLegalizacionYDesembolso extends ServiceClient {
 
  
  	private String numeroRadicadoSolicitud;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPostulacionLegalizacionDTO> result;
  
 	public ConsultarPostulacionesParaLegalizacionYDesembolso (String numeroRadicadoSolicitud,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroRadicadoSolicitud=numeroRadicadoSolicitud;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicadoSolicitud", numeroRadicadoSolicitud)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudPostulacionLegalizacionDTO>) response.readEntity(new GenericType<List<SolicitudPostulacionLegalizacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudPostulacionLegalizacionDTO> getResult() {
		return result;
	}

 
  	public void setNumeroRadicadoSolicitud (String numeroRadicadoSolicitud){
 		this.numeroRadicadoSolicitud=numeroRadicadoSolicitud;
 	}
 	
 	public String getNumeroRadicadoSolicitud (){
 		return numeroRadicadoSolicitud;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}