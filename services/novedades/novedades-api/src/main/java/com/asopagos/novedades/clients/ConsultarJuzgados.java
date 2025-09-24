package com.asopagos.novedades.clients;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.modelo.OficinaJuzgadoDTO;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarJuzgados
 */
public class ConsultarJuzgados extends ServiceClient {
    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<OficinaJuzgadoDTO> result;

    public ConsultarJuzgados(List<OficinaJuzgadoDTO> result) {
        super();
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (List<OficinaJuzgadoDTO>) response.readEntity(new GenericType<List<OficinaJuzgadoDTO>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<OficinaJuzgadoDTO> getResult() {
        return result;
    }

}