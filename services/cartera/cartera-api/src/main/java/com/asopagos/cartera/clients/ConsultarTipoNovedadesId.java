package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.CarteraNovedadModeloDTO;
import com.asopagos.dto.modelo.ConsultarNovedadesIdDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarTipoNovedadesId
 */
public class ConsultarTipoNovedadesId extends ServiceClient {
    private ConsultarNovedadesIdDTO consultarNovedadesIdDTO;

    public ConsultarTipoNovedadesId(ConsultarNovedadesIdDTO consultarNovedadesIdDTO) {
        super();
        this.consultarNovedadesIdDTO = consultarNovedadesIdDTO;
    }

    private List<CarteraNovedadModeloDTO> result;

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(consultarNovedadesIdDTO == null ? null : Entity.json(consultarNovedadesIdDTO));
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<CarteraNovedadModeloDTO>) response.readEntity(new GenericType<List<CarteraNovedadModeloDTO>>() {
        });
    }

    public ConsultarNovedadesIdDTO getConsultarNovedadesIdDTO() {
        return consultarNovedadesIdDTO;
    }

    public void setConsultarNovedadesIdDTO(ConsultarNovedadesIdDTO consultarNovedadesIdDTO) {
        this.consultarNovedadesIdDTO = consultarNovedadesIdDTO;
    }

    public List<CarteraNovedadModeloDTO> getResult() {
        return result;
    }

    public void setResult(List<CarteraNovedadModeloDTO> result) {
        this.result = result;
    }
}
