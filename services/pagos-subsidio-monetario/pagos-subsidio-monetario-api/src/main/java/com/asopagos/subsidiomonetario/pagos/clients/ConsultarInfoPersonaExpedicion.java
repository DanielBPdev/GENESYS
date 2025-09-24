package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionDTO;
import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaExpedicionDTO;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConsultarInfoPersonaExpedicion extends ServiceClient {

    private String identificacion;
    private String tipoIdentificacion;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private InfoPersonaExpedicionDTO result;

    public ConsultarInfoPersonaExpedicion(String identificacion, String tipoIdentificacion) {
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
        this.result = (InfoPersonaExpedicionDTO) response.readEntity(InfoPersonaExpedicionDTO.class);
    }

    public InfoPersonaExpedicionDTO getResult() {
        return result;
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
}
