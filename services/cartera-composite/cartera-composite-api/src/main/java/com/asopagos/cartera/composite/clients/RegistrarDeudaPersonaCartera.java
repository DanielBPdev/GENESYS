package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.GestionDeudaDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/registrarDeudaPersonaCartera
 */
public class RegistrarDeudaPersonaCartera extends ServiceClient {
    private String periodo;
    private List<GestionDeudaDTO> listaCotizantesDTO;

    public RegistrarDeudaPersonaCartera(String periodo, List<GestionDeudaDTO> listaCotizantesDTO) {
        super();
        this.periodo = periodo;
        this.listaCotizantesDTO = listaCotizantesDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("periodo", periodo)
                .request(MediaType.APPLICATION_JSON)
                .post(listaCotizantesDTO == null ? null : Entity.json(listaCotizantesDTO));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }


    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setListaCotizantesDTO(List<GestionDeudaDTO> listaCotizantesDTO) {
        this.listaCotizantesDTO = listaCotizantesDTO;
    }

    public List<GestionDeudaDTO> getListaCotizantesDTO() {
        return listaCotizantesDTO;
    }

}