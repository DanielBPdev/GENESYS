package com.asopagos.empleadores.clients;

import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.entidades.ccf.personas.Persona;

public class ConsultarPersonaEmpleador extends ServiceClient{


    private Long idEmpleador;

    private Persona result;

    public ConsultarPersonaEmpleador(Long idEmpleador){
        super();
        this.idEmpleador = idEmpleador;
    }

    @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
								.resolveTemplate("idEmpleador", idEmpleador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result =  response.readEntity(Persona.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Persona getResult() {
		return result;
	}

 
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}


}

