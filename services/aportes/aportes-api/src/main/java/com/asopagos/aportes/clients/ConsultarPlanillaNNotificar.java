package com.asopagos.aportes.clients;


import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConsultarPlanillaNNotificar extends ServiceClient {
private Long idPlanilla;
private Boolean result; 

public ConsultarPlanillaNNotificar(Long idPlanilla){
    super();
    this.idPlanilla=idPlanilla;
}
    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
						.queryParam("idPlanilla", idPlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
    }

    @Override
    protected void getResultData(Response response) {
      this.result = (Boolean) response.readEntity(Boolean.class);
    }

    public Long getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Long idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
    
    
}