package com.asopagos.afiliaciones.wsCajasan.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.ResponseDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarAportesOutDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarAportesInDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.client.Entity;
import com.asopagos.enumeraciones.TipoConsultanteAportesWS;

public class ConsultarAportes extends ServiceClient{

    private Long numeroIdentificacion;
    private Integer anio;
    private TipoConsultanteAportesWS tipoConsultante;

    /** Resultado del servicio */
    private List<ConsultarAportesOutDTO> result;

    public ConsultarAportes(Long numeroIdentificacion, Integer anio, TipoConsultanteAportesWS tipoConsultante) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.anio = anio;
        this.tipoConsultante = tipoConsultante;
    }
    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        ConsultarAportesInDTO input = new ConsultarAportesInDTO( numeroIdentificacion, anio, tipoConsultante);
        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON));
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (List<ConsultarAportesOutDTO>) response.readEntity(new GenericType<List<ConsultarAportesOutDTO>>(){});
    }

    public List<ConsultarAportesOutDTO> getResult() {
        return result;
    }

    // Getters y setters
    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public TipoConsultanteAportesWS getTipoConsultante(){
        return tipoConsultante;
    }

    public void setTipoConsultante(TipoConsultanteAportesWS tipoConsultante){
        this.tipoConsultante = tipoConsultante;
    }

}
