package com.asopagos.validaciones.fovis.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/existeEnListasNegras
 */
public class ExisteRegistraduriaNacionalFovis extends ServiceClient {
    	private TipoIdentificacionEnum tipoIdentificacion;
        private String numeroIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ExisteRegistraduriaNacionalFovis(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
    }
 
	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}


    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
 
  
}