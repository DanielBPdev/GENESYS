package com.asopagos.fovis.clients;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.core.GenericType;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConsultarSolicitudFovis extends ServiceClient {

    private Long instanciaProceso;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private Solicitud result;

    public ConsultarSolicitudFovis(Long instanciaProceso){
        super();
        this.instanciaProceso=instanciaProceso;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("instanciaProceso", instanciaProceso)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (Solicitud) response.readEntity(Solicitud.class);
    }

    public Solicitud getResult() {
        return result;
    }

    public Long getInstanciaProceso() {
        return instanciaProceso;
    }

    public void setInstanciaProceso(Long instanciaProceso) {
        this.instanciaProceso = instanciaProceso;
    }
}
