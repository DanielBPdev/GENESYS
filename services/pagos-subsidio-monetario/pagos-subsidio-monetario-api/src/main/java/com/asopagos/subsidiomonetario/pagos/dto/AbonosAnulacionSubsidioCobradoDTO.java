package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información de los
 * abonos cobrados seleccionados para realizar una solicitud de anulación de subsidio cobrado <br/>
 * <b>Módulo:</b> Asopagos - HU-31-222<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class AbonosAnulacionSubsidioCobradoDTO extends CuentaAdministradorSubsidioDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2038962044001534911L;

    /**
     * Identificador asociadoa la registro en cuenta del administrador del subsidio.
     */
    @NotNull
    private Long idCuentaAdministradorSubsidio;

    /**
     * Representa la descripcion de otros detalles sobre la anulación de subsidio cobrado.
     */
    @NotNull
    private String otrosDetallesAnulacion;

    /**
     * Datos del medio de pago por el cual se realizara el cambio despues de la anulación con reemplazo.
     * Este campo solo tendra valor los valores del medio de pago EFECTIVO, cuando se vaya a realizar
     * una anulación a un registro asociado a TRANSFERENCIA.
     */
    private MedioDePagoModeloDTO medioDePagoModelo;
    
    /**
     
     */
    private MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion;

    public MotivoAnulacionSubsidioAsignadoEnum getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}

	/**
     * @return the idCuentaAdministradorSubsidio
     */
    public Long getIdCuentaAdministradorSubsidio() {
        return idCuentaAdministradorSubsidio;
    }

    /**
     * @param idCuentaAdministradorSubsidio
     *        the idCuentaAdministradorSubsidio to set
     */
    public void setIdCuentaAdministradorSubsidio(Long idCuentaAdministradorSubsidio) {
        this.idCuentaAdministradorSubsidio = idCuentaAdministradorSubsidio;
    }

    /**
     * @return the otrosDetallesAnulacion
     */
    public String getOtrosDetallesAnulacion() {
        return otrosDetallesAnulacion;
    }

    /**
     * @param otrosDetallesAnulacion
     *        the otrosDetallesAnulacion to set
     */
    public void setOtrosDetallesAnulacion(String otrosDetallesAnulacion) {
        this.otrosDetallesAnulacion = otrosDetallesAnulacion;
    }

    /**
     * @return the medioDePagoModelo
     */
    public MedioDePagoModeloDTO getMedioDePagoModelo() {
        return medioDePagoModelo;
    }

    /**
     * @param medioDePagoModelo
     *        the medioDePagoModelo to set
     */
    public void setMedioDePagoModelo(MedioDePagoModeloDTO medioDePagoModelo) {
        this.medioDePagoModelo = medioDePagoModelo;
    }

}
