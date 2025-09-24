package com.asopagos.cartera.clients;

import com.asopagos.cartera.dto.AportanteAccionCobroDTO;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarAportantesCiclo
 */
public class ObtenerAportantesParaExpulsionPorIds extends ServiceClient {


    private TipoAccionCobroEnum accionCobro;
    List<Long> idPersonasAProcesar;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<AportanteAccionCobroDTO> result;

    public ObtenerAportantesParaExpulsionPorIds(TipoAccionCobroEnum accionCobro, List<Long> idPersonasAProcesar) {
        super();
        this.accionCobro = accionCobro;
        this.idPersonasAProcesar = idPersonasAProcesar;
    }


    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
            .queryParam("accionCobro", accionCobro)
            .queryParam("idPersonasAProcesar", idPersonasAProcesar.toArray())
            .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<AportanteAccionCobroDTO>) response.readEntity(new GenericType<List<AportanteAccionCobroDTO>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public TipoAccionCobroEnum getAccionCobro() {
        return accionCobro;
    }

    public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
        this.accionCobro = accionCobro;
    }

    public List<Long> getIdPersonasAProcesar() {
        return idPersonasAProcesar;
    }

    public void setIdPersonasAProcesar(List<Long> idPersonasAProcesar) {
        this.idPersonasAProcesar = idPersonasAProcesar;
    }

    public List<AportanteAccionCobroDTO> getResult() {
        return result;
    }

    public void setResult(List<AportanteAccionCobroDTO> result) {
        this.result = result;
    }
}