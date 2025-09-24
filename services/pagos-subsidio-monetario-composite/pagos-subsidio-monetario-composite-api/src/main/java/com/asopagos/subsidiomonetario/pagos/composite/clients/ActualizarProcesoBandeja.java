package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ProcesoBandejaTransaccionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoBandejaTransaccionEnum;
import java.lang.Long;

public class ActualizarProcesoBandeja extends ServiceClient{

    private Long idBandeja, idSolicitud;

    private EstadoBandejaTransaccionEnum estado;


    public ActualizarProcesoBandeja(Long idBandeja, EstadoBandejaTransaccionEnum estado,Long idSolicitud) {
        this.idBandeja = idBandeja;
        this.estado = estado;
        this.idSolicitud = idSolicitud;
    }
    

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idBandeja", idBandeja)
                .queryParam("estado", estado)
                .queryParam("idSolicitud", idSolicitud)
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }


    public Long getIdBandeja() {
        return this.idBandeja;
    }

    public void setIdBandeja(Long idBandeja) {
        this.idBandeja = idBandeja;
    }

    public Long getIdSolicitud() {
        return this.idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public EstadoBandejaTransaccionEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoBandejaTransaccionEnum estado) {
        this.estado = estado;
    }
    
}