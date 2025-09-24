package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionValidacionesDTO;


import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class ConsultarInfoPersonaExpedicionValidaciones extends ServiceClient {

    private String identificacion;
    private String tipoIdentificacion;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<InfoPersonaExpedicionValidacionesDTO> result;

    public ConsultarInfoPersonaExpedicionValidaciones(String identificacion, String tipoIdentificacion) {
        this.identificacion = identificacion;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("identificacion", identificacion)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (List<InfoPersonaExpedicionValidacionesDTO>) response.readEntity(new GenericType<List<InfoPersonaExpedicionValidacionesDTO>>(){});
        //this.result = (List<RegistroPersonaInconsistenteDTO>) response.readEntity(new GenericType<List<RegistroPersonaInconsistenteDTO>>(){});
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<InfoPersonaExpedicionValidacionesDTO> getResult() {
        return result;
    }
}
