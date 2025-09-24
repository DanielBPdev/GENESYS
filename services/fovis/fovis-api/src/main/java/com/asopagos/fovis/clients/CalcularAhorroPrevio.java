package com.asopagos.fovis.clients;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/calcularAhorroPrevio
 */
public class CalcularAhorroPrevio extends ServiceClient {
    private Long idPostulacion;
    /** Atributo que almacena los datos resultado del llamado al servicio */
    private BigDecimal result;
    public CalcularAhorroPrevio(Long idPostulacion){
        super();
        this.idPostulacion=idPostulacion;
    }
    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
                .queryParam("idPostulacion", idPostulacion)
                .request(MediaType.APPLICATION_JSON).get();
    }
    @Override
    protected void getResultData(Response response) {
        this.result = (BigDecimal) response.readEntity(BigDecimal.class);
    }
    /**
     * Retorna el resultado del llamado al servicio
     */
    public BigDecimal getResult() {
        return result;
    }
    public void setIdPostulacion (Long idPostulacion){
        this.idPostulacion=idPostulacion;
    }
    public Long getIdPostulacion (){
        return idPostulacion;
    }
}