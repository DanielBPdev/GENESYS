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

public class RegistrarProcesoBandeja extends ServiceClient{
    
    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private EstadoBandejaTransaccionEnum estado;

    private ProcesoBandejaTransaccionEnum proceso;

    private Long result, idMedioDePagoOrigen, idMedioDePagoDestino;


    public RegistrarProcesoBandeja(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, 
        EstadoBandejaTransaccionEnum estado, ProcesoBandejaTransaccionEnum proceso) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.estado = estado;
        this.proceso = proceso;
    }
    

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .queryParam("estado", estado)
                .queryParam("proceso", proceso)
                .queryParam("idMedioDePagoOrigen", idMedioDePagoOrigen)
                .queryParam("idMedioDePagoDestino",idMedioDePagoDestino)
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

    public EstadoBandejaTransaccionEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoBandejaTransaccionEnum estado) {
        this.estado = estado;
    }

    public ProcesoBandejaTransaccionEnum getProceso() {
        return this.proceso;
    }

    public void setProceso(ProcesoBandejaTransaccionEnum proceso) {
        this.proceso = proceso;
    }
    public void setResult(Long result) {
        this.result = result;
    }

    public Long getIdMedioDePagoOrigen() {
        return this.idMedioDePagoOrigen;
    }

    public void setIdMedioDePagoOrigen(Long idMedioDePagoOrigen) {
        this.idMedioDePagoOrigen = idMedioDePagoOrigen;
    }

    public Long getIdMedioDePagoDestino() {
        return this.idMedioDePagoDestino;
    }

    public void setIdMedioDePagoDestino(Long idMedioDePagoDestino) {
        this.idMedioDePagoDestino = idMedioDePagoDestino;
    }

}