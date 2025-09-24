package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/ConsultarUltimaClasificacionCategoria
 */
public class ConsultarUltimaClasificacionCategoria extends ServiceClient {
 
  
  	private String numeroDocumento;
  	private TipoIdentificacionEnum tipoDocumento;

	private String result;
  
 	public ConsultarUltimaClasificacionCategoria (String numeroDocumento,TipoIdentificacionEnum tipoDocumento){
 		super();
		this.numeroDocumento=numeroDocumento;
		this.tipoDocumento=tipoDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("numeroDocumento", numeroDocumento)
						.queryParam("tipoDocumento", tipoDocumento.name())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}


    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoIdentificacionEnum getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoIdentificacionEnum tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


}