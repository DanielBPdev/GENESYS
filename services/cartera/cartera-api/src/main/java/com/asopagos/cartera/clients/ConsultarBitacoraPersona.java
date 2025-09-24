package com.asopagos.cartera.clients;

import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarBitacoraPersona
 */
public class ConsultarBitacoraPersona extends ServiceClient {
    private Long perId;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private BitacoraCarteraDTO result;

    public ConsultarBitacoraPersona(Long perId) {
        super();
        this.perId = perId;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("perId", perId)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (BitacoraCarteraDTO) response.readEntity(BitacoraCarteraDTO.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public BitacoraCarteraDTO getResult() {
        return result;
    }


    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public Long getPerId() {
        return perId;
    }


}