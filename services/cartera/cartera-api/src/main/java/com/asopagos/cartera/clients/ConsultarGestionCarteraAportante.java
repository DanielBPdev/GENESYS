package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.CarteraAportantePersonaDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/ConsultarGestionCarteraAportante
 */
public class ConsultarGestionCarteraAportante extends ServiceClient {

    private String numeroIdentificacion;
    private String tipoLineaCobro;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private List<CarteraAportantePersonaDTO> result;

    public ConsultarGestionCarteraAportante(String numeroIdentificacion, String tipoLineaCobro) {
        super();
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoLineaCobro = tipoLineaCobro;

    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .queryParam("tipoLineCobro", tipoLineaCobro)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    @Override
    protected void getResultData(Response response) {
        this.result = (List<CarteraAportantePersonaDTO>) response.readEntity(new GenericType<List<CarteraAportantePersonaDTO>>() {
        });
    }

    public List<CarteraAportantePersonaDTO> getResult() {
        return result;
    }

    public void setResult(List<CarteraAportantePersonaDTO> result) {
        this.result = result;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }


}