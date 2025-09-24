package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioYDetallesDTO;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/rollbackRetiro
 */
public class RollbackRetiro extends ServiceClient {

    private String idPuntoCobro;
    private String idTransaccionTercerPagador;
    private String usuario;
    private CuentaAdministradorSubsidioYDetallesDTO listaCuentaAdminSubsidio;

    private String retiro;


    public RollbackRetiro(String idPuntoCobro, String idTransaccionTercerPagador, String usuario, CuentaAdministradorSubsidioYDetallesDTO listaCuentaAdminSubsidio, String retiro) {
        super();
        this.idPuntoCobro = idPuntoCobro;
        this.idTransaccionTercerPagador = idTransaccionTercerPagador;
        this.usuario = usuario;
        this.listaCuentaAdminSubsidio = listaCuentaAdminSubsidio;
        this.retiro=retiro;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("idPuntoCobro", idPuntoCobro)
                .queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
                .queryParam("usuario", usuario)
                .queryParam("retiro",retiro )
                .request(MediaType.APPLICATION_JSON)
                .post(listaCuentaAdminSubsidio == null ? null : Entity.json(listaCuentaAdminSubsidio));
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdPuntoCobro() {
        return idPuntoCobro;
    }

    public void setIdPuntoCobro(String idPuntoCobro) {
        this.idPuntoCobro = idPuntoCobro;
    }

    public String getIdTransaccionTercerPagador() {
        return idTransaccionTercerPagador;
    }

    public void setIdTransaccionTercerPagador(String idTransaccionTercerPagador) {
        this.idTransaccionTercerPagador = idTransaccionTercerPagador;
    }

    public CuentaAdministradorSubsidioYDetallesDTO getListaCuentaAdminSubsidio() {
        return listaCuentaAdminSubsidio;
    }

    public void setListaCuentaAdminSubsidio(CuentaAdministradorSubsidioYDetallesDTO listaCuentaAdminSubsidio) {
        this.listaCuentaAdminSubsidio = listaCuentaAdminSubsidio;
    }

    public String getRetiro() {
        return retiro;
    }

    public void setRetiro(String retiro) {
        this.retiro = retiro;
    }
}