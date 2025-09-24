package com.asopagos.aportes.composite.clients;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import com.asopagos.services.common.ServiceClient;

public class LiberarPlanillasBloque9Process  extends ServiceClient {
    

    private static final ILogger logger = LogManager.getLogger(LiberarPlanillasBloque9Process.class);

    public LiberarPlanillasBloque9Process (){
        super();
    }


   @Override
   protected Response invoke(WebTarget webTarget, String path) {

       logger.debug("ejecucion bloque 9 servicio rest");
       System.out.println(" ejecucion bloque 9 servicio rest "+path);
       Response response = webTarget.path(path)
       .request(MediaType.APPLICATION_JSON)
       .post(null);
       return response;
   }
   
   @Override
   protected void getResultData(Response response) {

   }


}
