package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarNovedadesPostulacionRangoFecha
 */
public class ConsultarNovedadesPostulacionesRango extends ServiceClient {


    private Long cicloAsignacion;

    private String fechaInicio;

    private String fechaFnal;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<PostulacionFOVISModeloDTO> result;

    public ConsultarNovedadesPostulacionesRango(Long cicloAsignacion, String fechaInicio, String fechaFnal){
        super();
        this.cicloAsignacion=cicloAsignacion;
        this.fechaInicio=fechaInicio;
        this.fechaFnal=fechaFnal;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idCicloAsignacion", cicloAsignacion)
                .queryParam("fechaInico", fechaInicio)
                .queryParam("fechaFin", fechaFnal)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<PostulacionFOVISModeloDTO>) response.readEntity(new GenericType<List<PostulacionFOVISModeloDTO>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<PostulacionFOVISModeloDTO> getResult() {
        return result;
    }


    public Long getCicloAsignacion() {
        return cicloAsignacion;
    }

    public void setCicloAsignacion(Long cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFnal() {
        return fechaFnal;
    }

    public void setFechaFnal(String fechaFnal) {
        this.fechaFnal = fechaFnal;
    }
}