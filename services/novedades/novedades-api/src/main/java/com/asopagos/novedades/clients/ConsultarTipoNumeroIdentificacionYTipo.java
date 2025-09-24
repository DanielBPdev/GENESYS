package com.asopagos.novedades.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarTipoNumeroIdentificacion
 */
public class ConsultarTipoNumeroIdentificacionYTipo extends ServiceClient {
    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;


    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private Boolean result;

    public ConsultarTipoNumeroIdentificacionYTipo(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
        super();
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (Boolean) response.readEntity(Boolean.class);
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
