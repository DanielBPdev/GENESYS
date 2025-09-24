package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/documentoFiscalizacionData
 */
public class DocumentoFiscalizacionData extends ServiceClient {

    private List<Object[]> response;
    private TipoIdentificacionEnum identificacion;
    private String numeroIdentificacion;

    public DocumentoFiscalizacionData(TipoIdentificacionEnum identificacion, String numeroIdentificacion) {

        this.identificacion = identificacion;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public DocumentoFiscalizacionData() {
        super();
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipoIdentificacion", identificacion)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.response = (List<Object[]>) response.readEntity(new GenericType<List<Object[]>>() {
        });
    }

    public List<Object[]> getResult() {
        return response;
    }
}
