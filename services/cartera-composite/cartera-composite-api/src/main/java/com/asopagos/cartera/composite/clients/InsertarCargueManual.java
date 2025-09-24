package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.CargueManualCotizanteAportante;
import com.asopagos.dto.cartera.RespuestaCargueMasivoAportanteDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/insertarCargueManual
 */
public class InsertarCargueManual extends ServiceClient {
    List<CargueManualCotizanteAportante> cargueManualCotizanteAportantes;

    public InsertarCargueManual(List<CargueManualCotizanteAportante> cargueManualCotizanteAportantes) {
        super();
        this.cargueManualCotizanteAportantes = cargueManualCotizanteAportantes;
    }

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private RespuestaCargueMasivoAportanteDTO result;

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(cargueManualCotizanteAportantes == null ? null : Entity.json(cargueManualCotizanteAportantes));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = (RespuestaCargueMasivoAportanteDTO) response.readEntity(RespuestaCargueMasivoAportanteDTO.class);
    }
}
