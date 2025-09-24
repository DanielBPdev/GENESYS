package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConsultarActividadCarIdNumeroIdentificacion extends ServiceClient {

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private Long carId;
    private String numeroIdentificacion;
    private String result;

    public ConsultarActividadCarIdNumeroIdentificacion(Long carId, String numeroIdentificacion) {
        this.carId = carId;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("carId", carId)
                .queryParam("perNumeroIdentificacion", numeroIdentificacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
