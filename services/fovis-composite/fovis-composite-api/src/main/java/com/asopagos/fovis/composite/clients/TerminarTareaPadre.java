package com.asopagos.fovis.composite.clients;

import com.asopagos.services.common.ServiceClient;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/terminarTareaPadre
 */

public class TerminarTareaPadre extends ServiceClient {

    private Long idTarea;

    private  String tipoTransaccionEnum;

    private Long instanciaProceso;

    public TerminarTareaPadre(Long idTarea, String tipoTransaccionEnum, Long instanciaProceso) {
        super();
        this.idTarea = idTarea;
        this.tipoTransaccionEnum = tipoTransaccionEnum;
        this.instanciaProceso = instanciaProceso;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .resolveTemplate("idTarea", idTarea)
                .resolveTemplate("tipoTransaccionEnum", tipoTransaccionEnum)
                .resolveTemplate("instanciaProceso", instanciaProceso)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipoTransaccionEnum() {
        return tipoTransaccionEnum;
    }

    public void setTipoTransaccionEnum(String tipoTransaccionEnum) {
        this.tipoTransaccionEnum = tipoTransaccionEnum;
    }

    public Long getInstanciaProceso() {
        return instanciaProceso;
    }
    public void setInstanciaProceso(Long instanciaProceso) {
        this.instanciaProceso = instanciaProceso;
    }
}
