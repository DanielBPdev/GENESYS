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

public class ConsultarUltimaSolicitud extends ServiceClient{
    
    private TipoIdentificacionEnum tipo;

    private String numero;

    private Long result;


    public ConsultarUltimaSolicitud(TipoIdentificacionEnum tipo, String numero) {
        super();
        this.tipo = tipo;
        this.numero = numero;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipo", tipo)
                .queryParam("numero", numero)
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (Long) response.readEntity(Long.class);
    }

    public Long getResult(){
        return result;
    }

    public TipoIdentificacionEnum getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoIdentificacionEnum tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}