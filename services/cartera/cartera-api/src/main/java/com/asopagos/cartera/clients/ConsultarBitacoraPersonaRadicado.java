package com.asopagos.cartera.clients;

import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarBitacoraPersona
 */
public class ConsultarBitacoraPersonaRadicado extends ServiceClient {
    private TipoIdentificacionEnum tipoIdentificacion;
    private String numeroIdentificaion;
    private String numeroRadicacion;

    /**
     * Atributo que almacena los datos resultado del llamado al servicio
     */
    private BitacoraCarteraDTO result;

    public ConsultarBitacoraPersonaRadicado(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificaion, String numeroRadicacion) {
        super();
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificaion = numeroIdentificaion;
        this.numeroRadicacion = numeroRadicacion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipoIdentificacion", tipoIdentificacion.name())
                .queryParam("numeroIdentificaion", numeroIdentificaion)
                .queryParam("numeroRadicacion", numeroRadicacion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (BitacoraCarteraDTO) response.readEntity(BitacoraCarteraDTO.class);
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public BitacoraCarteraDTO getResult() {
        return result;
    }


    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    public void setNumeroIdentificaion(String numeroIdentificaion) {
        this.numeroIdentificaion = numeroIdentificaion;
    }

    public String getNumeroIdentificaion() {
        return numeroIdentificaion;
    }
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }


}