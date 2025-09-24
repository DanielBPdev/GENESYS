package com.asopagos.novedades.composite.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/radicarSolicitudNovedadArchivoActualizacion
 */
public class GestionarNovedad extends ServiceClient { 
    
    private Map<String,Object> datos;
    // private TipoArchivoRespuestaEnum tipoArchivo;
    // private TipoTransaccionEnum tipoTransaccion;
    // private InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO;
    // private CanalRecepcionEnum canal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GestionarNovedad (Map<String,Object> datos){
 		super();
		this.datos=datos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datos == null ? null : Entity.json(datos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

    public Map<String,Object> getDatos() {
        return this.datos;
    }

    public void setDatos(Map<String,Object> datos) {
        this.datos = datos;
    }
  	
}