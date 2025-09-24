package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.Long;
import java.lang.String;

public class ConsultarIdMedioDePagoTarjeta extends ServiceClient {

    private String numeroIdentificacion,tipoIdentificacion;

    private Long result;

    public ConsultarIdMedioDePagoTarjeta(String tipoIdentificacion,String numeroIdentificacion){
        super();
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion =tipoIdentificacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .queryParam("numeroIdentificacion",numeroIdentificacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }
    
    @Override
    protected void getResultData(Response response) {
        this.result = (Long) response.readEntity(Long.class);
    }

    public Long getResult(){
        return result;
    }

}