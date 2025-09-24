package com.asopagos.subsidiomonetario.pagos.dto;

import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO para la comunicación con pantallas para la anulación de los
 * detalles de los abonos<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 207,208,220 y 221<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class CuentaAdministradorSubsidioYDetallesDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Lista de los detalles.
     */
    private List<DetalleSubsidioAsignado> listaDetalleSubsidioAsignado;
    /**
     * Lista de las cuentas subsidio.
     */
    private List<CuentaAdministradorSubsidioDTO> listaCuentaAdministradorSubsidio;

    public CuentaAdministradorSubsidioYDetallesDTO() {
    }

    public CuentaAdministradorSubsidioYDetallesDTO(List<DetalleSubsidioAsignado> listaDetalleSubsidioAsignado,
                                                   List<CuentaAdministradorSubsidioDTO> listaCuentaAdministradorSubsidio) {
        this.listaDetalleSubsidioAsignado = listaDetalleSubsidioAsignado;
        this.listaCuentaAdministradorSubsidio = listaCuentaAdministradorSubsidio;
    }

    public List<DetalleSubsidioAsignado> getListaDetalleSubsidioAsignado() {
        return listaDetalleSubsidioAsignado;
    }

    public void setListaDetalleSubsidioAsignado(List<DetalleSubsidioAsignado> listaDetalleSubsidioAsignado) {
        this.listaDetalleSubsidioAsignado = listaDetalleSubsidioAsignado;
    }

    public List<CuentaAdministradorSubsidioDTO> getListaCuentaAdministradorSubsidio() {
        return listaCuentaAdministradorSubsidio;
    }

    public void setListaCuentaAdministradorSubsidio(List<CuentaAdministradorSubsidioDTO> listaCuentaAdministradorSubsidio) {
        this.listaCuentaAdministradorSubsidio = listaCuentaAdministradorSubsidio;
    }
}
