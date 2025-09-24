package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCotizateDependiente
 */
public class GuardarCotizateDependiente extends ServiceClient {
    private List<CarteraDependienteModeloDTO> carteraDependienteDTO;

    public GuardarCotizateDependiente(List<CarteraDependienteModeloDTO> carteraDependienteDTO) {
        super();
        this.carteraDependienteDTO = carteraDependienteDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(carteraDependienteDTO == null ? null : Entity.json(carteraDependienteDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }


    public List<CarteraDependienteModeloDTO> getCarteraDependienteDTO() {
        return carteraDependienteDTO;
    }

    public void setCarteraDependienteDTO(List<CarteraDependienteModeloDTO> carteraDependienteDTO) {
        this.carteraDependienteDTO = carteraDependienteDTO;
    }
}
