package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraModeloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultar/lineaCobroUno/moraPacial
 */
public class ConsultarLineaCobroMoraParcial extends ServiceClient {

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private List<CarteraModeloDTO> result;

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (List<CarteraModeloDTO>) response.readEntity(new GenericType<List<CarteraModeloDTO>>() {
        });
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<CarteraModeloDTO> getResult() {
        return result;
    }
}
