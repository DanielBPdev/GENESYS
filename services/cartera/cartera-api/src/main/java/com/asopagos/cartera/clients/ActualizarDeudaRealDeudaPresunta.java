package com.asopagos.cartera.clients;

import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/actualizarDeudaRealDeudaPresunta
 */
public class ActualizarDeudaRealDeudaPresunta extends ServiceClient {
    private BigDecimal deudaReal;
    private BigDecimal deudaPresunta;
    private Long idCarteraDependiente;
    private Long idCartera;

    public ActualizarDeudaRealDeudaPresunta(BigDecimal deudaReal, BigDecimal deudaPresunta, Long idCarteraDependiente, Long idCartera) {
        super();
        this.deudaReal = deudaReal;
        this.deudaPresunta = deudaPresunta;
        this.idCarteraDependiente = idCarteraDependiente;
        this.idCartera = idCartera;

    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("deudaReal", deudaReal)
                .queryParam("deudaPresunta", deudaPresunta)
                .queryParam("idCarteraDependiente", idCarteraDependiente)
                .queryParam("idCartera", idCartera)
                .request(MediaType.APPLICATION_JSON)
                .get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {

    }

    public BigDecimal getDeudaReal() {
        return deudaReal;
    }

    public void setDeudaReal(BigDecimal deudaReal) {
        this.deudaReal = deudaReal;
    }

    public BigDecimal getDeudaPresunta() {
        return deudaPresunta;
    }

    public void setDeudaPresunta(BigDecimal deudaPresunta) {
        this.deudaPresunta = deudaPresunta;
    }

    public Long getIdCarteraDependiente() {
        return idCarteraDependiente;
    }

    public void setIdCarteraDependiente(Long idCarteraDependiente) {
        this.idCarteraDependiente = idCarteraDependiente;
    }


    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

}
