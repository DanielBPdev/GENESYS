package com.asopagos.novedades.clients;
import com.asopagos.services.common.ServiceClient;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarRetroactividadNovedad
 */

public class ConsultarRetroactividadNovedad extends ServiceClient{
    private Long idRegistroDetallado;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private Boolean result;

 	public ConsultarRetroactividadNovedad (Long idRegistroDetallado){
 		super();
		this.idRegistroDetallado=idRegistroDetallado;
 	}

 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.resolveTemplate("idRegistroDetallado", idRegistroDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}


	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}

    public Long getIdRegistroDetallado() {
        return this.idRegistroDetallado;
    }

    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    
}
