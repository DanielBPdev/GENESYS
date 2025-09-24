package com.asopagos.novedades.clients;

import com.asopagos.services.common.ServiceClient;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import java.util.List;
import javax.ws.rs.core.GenericType;

public class ObtenerEmpleadoresProcesar extends ServiceClient{

    private List<Object> empleadores;

    private List<Object[]> result;

    public ObtenerEmpleadoresProcesar (){
        super();
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
    Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
	protected void getResultData(Response response) {
		this.result = (List<Object[]>) response.readEntity(new GenericType<List<Object[]>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Object[]> getResult() {
		return result;
	}
    
}
