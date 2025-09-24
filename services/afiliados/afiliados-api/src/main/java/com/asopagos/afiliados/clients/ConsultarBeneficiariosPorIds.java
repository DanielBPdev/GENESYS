package com.asopagos.afiliados.clients;

import java.util.List;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/consultarBeneficiariosPorIds
 */
public class ConsultarBeneficiariosPorIds extends ServiceClient {

    private List<Long> idsBeneficiarios;
    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<BeneficiarioModeloDTO> result;

    public ConsultarBeneficiariosPorIds(List<Long> idsBeneficiarios) {
        super();
        this.idsBeneficiarios = idsBeneficiarios;
        }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(idsBeneficiarios, MediaType.APPLICATION_JSON));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        this.result = (List<BeneficiarioModeloDTO>) response.readEntity(
            new javax.ws.rs.core.GenericType<List<BeneficiarioModeloDTO>>() {}
        );
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<BeneficiarioModeloDTO> getResult() {
        return result;
    }

    public void setIdsBeneficiarios(List<Long> idsBeneficiarios) {
        this.idsBeneficiarios = idsBeneficiarios;
    }

    public List<Long> getIdsBeneficiarios() {
        return idsBeneficiarios;
    }

    public void setResult(List<BeneficiarioModeloDTO> result) {
        this.result = result;
    }
    
}