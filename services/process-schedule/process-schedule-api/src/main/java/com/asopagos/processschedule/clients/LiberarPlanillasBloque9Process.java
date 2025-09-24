package com.asopagos.processschedule.clients;

import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;
import com.asopagos.services.common.ServiceClient;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LiberarPlanillasBloque9Process extends ServiceClient {


    private ProcesoAutomaticoEnum proceso;
  



    @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(proceso == null ? null : Entity.json(proceso));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {

	}

    public void setProceso (ProcesoAutomaticoEnum proceso){
        this.proceso=proceso;
    }
    
    public ProcesoAutomaticoEnum getProceso (){
        return proceso;
    }

    
}
