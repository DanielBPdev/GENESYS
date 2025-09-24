package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioYDetallesDTO;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarCuentaAdministradorSubsidio
 */
public class ConsultarCuentaAdminSubsidio extends ServiceClient{

    private String tipoIdentificacion, numeroIdentificacion;

    private TipoMedioDePagoEnum medioDePago;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private CuentaAdministradorSubsidioYDetallesDTO result;

    public ConsultarCuentaAdminSubsidio(String tipoIdentificacion, String numeroIdentificacion, TipoMedioDePagoEnum medioDePago) {
        super();
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.medioDePago = medioDePago;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("tipoIdentificacion", tipoIdentificacion)
                .queryParam("numeroIdentificacion", numeroIdentificacion)
                .queryParam("medioDePago", medioDePago)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        result = (CuentaAdministradorSubsidioYDetallesDTO) response.readEntity(new GenericType<CuentaAdministradorSubsidioYDetallesDTO>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public CuentaAdministradorSubsidioYDetallesDTO getResult() {
        return result;
    }


    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }
}