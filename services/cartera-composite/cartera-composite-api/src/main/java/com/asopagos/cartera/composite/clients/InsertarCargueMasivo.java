package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.RespuestaCargueMasivoAportanteDTO;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/insertarCargueMasivo
 */
public class InsertarCargueMasivo extends ServiceClient {
    private String idArchivo;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private RespuestaCargueMasivoAportanteDTO result;

    public InsertarCargueMasivo(String idArchivo) {
        super();
        this.idArchivo = idArchivo;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idArchivo", idArchivo)
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = (RespuestaCargueMasivoAportanteDTO) response.readEntity(RespuestaCargueMasivoAportanteDTO.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public RespuestaCargueMasivoAportanteDTO getResult() {
        return result;
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public void setResult(RespuestaCargueMasivoAportanteDTO result) {
        this.result = result;
    }
}