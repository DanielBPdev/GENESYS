package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraModeloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/InsertarCarteraAportante
 */
public class InsertarCarteraAportante extends ServiceClient {
    private CarteraModeloDTO carteraModeloDTO;
    private Long result;

    public InsertarCarteraAportante(CarteraModeloDTO carteraModeloDTO) {
        super();
        this.carteraModeloDTO = carteraModeloDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(carteraModeloDTO == null ? null : Entity.json(carteraModeloDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = (Long) response.readEntity(Long.class);
    }


    public void setCarteraModeloDTO(CarteraModeloDTO carteraModeloDTO) {
        this.carteraModeloDTO = carteraModeloDTO;
    }

    public CarteraModeloDTO getCarteraModeloDTO() {
        return carteraModeloDTO;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}