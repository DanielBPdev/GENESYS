package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCiclosAsignacion
 */
public class ConsultarCiclosAsignacion extends ServiceClient {


    private EstadoCicloAsignacionEnum estadoCicloAsignacion;

    private String fecha;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<CicloAsignacionModeloDTO> result;

    public ConsultarCiclosAsignacion(EstadoCicloAsignacionEnum estadoCicloAsignacion, String fecha){
        super();
        this.estadoCicloAsignacion=estadoCicloAsignacion;
        this.fecha=fecha;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("estadoCicloAsignacion", estadoCicloAsignacion)
                .queryParam("fecha", fecha)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<CicloAsignacionModeloDTO>) response.readEntity(new GenericType<List<CicloAsignacionModeloDTO>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<CicloAsignacionModeloDTO> getResult() {
        return result;
    }


    public void setEstadoCicloAsignacion (EstadoCicloAsignacionEnum estadoCicloAsignacion){
        this.estadoCicloAsignacion=estadoCicloAsignacion;
    }

    public EstadoCicloAsignacionEnum getEstadoCicloAsignacion (){
        return estadoCicloAsignacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}