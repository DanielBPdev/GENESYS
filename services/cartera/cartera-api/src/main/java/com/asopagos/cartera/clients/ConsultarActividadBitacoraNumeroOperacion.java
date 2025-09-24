package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarActividadBitacoraNumeroOperacion
 */
public class ConsultarActividadBitacoraNumeroOperacion extends ServiceClient {


    private String numeroOperacion;
    private String actividad;


    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private String result;

    public ConsultarActividadBitacoraNumeroOperacion(String numeroOperacion, String actividad) {
        super();
        this.numeroOperacion = numeroOperacion;
        this.actividad = actividad;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
                .queryParam("numeroOperacion", numeroOperacion)
                .queryParam("actividad", actividad)
                .request(MediaType.APPLICATION_JSON).get();
    }


    @Override
    protected void getResultData(Response response) {
        this.result = response.readEntity(new GenericType<String>() {
        });
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public String getResult() {
        return result;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}