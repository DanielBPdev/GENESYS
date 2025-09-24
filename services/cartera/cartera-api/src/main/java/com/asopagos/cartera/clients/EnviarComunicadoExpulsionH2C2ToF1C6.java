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
 * /rest/cartera/enviarComunicadoExpulsionH2C2ToF1C6
 */
public class EnviarComunicadoExpulsionH2C2ToF1C6 extends ServiceClient {

    private TipoAccionCobroEnum accionCobro;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private List<AportanteAccionCobroDTO> result;

    public EnviarComunicadoExpulsionH2C2ToF1C6(TipoAccionCobroEnum accionCobro) {
        super();
        this.accionCobro = accionCobro;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("accionCobro", accionCobro)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = response.readEntity(new GenericType<List<AportanteAccionCobroDTO>>() {
        });
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<AportanteAccionCobroDTO> getResult() {
        return result;
    }


    public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
        this.accionCobro = accionCobro;
    }

    public TipoAccionCobroEnum getAccionCobro() {
        return accionCobro;
    }
}
