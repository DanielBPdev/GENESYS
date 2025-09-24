package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSolicitudAnulacionSubsidioCobrado;

/**
 * <b>Descripcion:</b> Modelo de DTO que representa el detalle de los subsidios asignados asociados por abono agrupador a una
 * solicitud de anulación de subsidio cobrado </br>
 * <b>Módulo:</b> Asopagos - HU-31-222-227<br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel</a>
 */
@XmlRootElement
public class DetalleSolicitudAnulacionSubsidioCobradoModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8190745960260359784L;

    /**
     * Representa el identificador del detalle de la solicitud de anulación de
     * subsidio cobrado
     */
    @NotNull
    private Long idDetalleSolicitudAnulacionSubsidioCobrado;

    /**
     * Representa la descripcion de otros detalles sobre la anulación de subsidio
     * cobrado
     */
    @NotNull
    private String detalleAnulacion;

    /**
     * Referencia a la solicitud de anulación de subsidio cobrado
     */
    @NotNull
    private Long idSolicitudAnulacionSubsidio;

    /**
     * Referencia la cuenta administrador de subsidio asociado a la solicitud de
     * anulación
     */
    @NotNull
    private Long idCuentaAdministradorSubsidio;

    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param Entidad
     *        a convertir
     */
    public void convertToDTO(DetalleSolicitudAnulacionSubsidioCobrado detalleSolicitudAnulacionSubsidioCobrado) {
        this.setIdDetalleSolicitudAnulacionSubsidioCobrado(
                detalleSolicitudAnulacionSubsidioCobrado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
        this.setIdSolicitudAnulacionSubsidio(detalleSolicitudAnulacionSubsidioCobrado.getIdSolicitudAnulacionSubsidio());
        this.setIdCuentaAdministradorSubsidio(detalleSolicitudAnulacionSubsidioCobrado.getIdCuentaAdministradorSubsidio());
        this.setDetalleAnulacion(detalleSolicitudAnulacionSubsidioCobrado.getDetalleAnulacion());
    }

    /**
     * Metodo encargado de convertir de DTO a entidad
     * @return Entidad convertida
     */
    public DetalleSolicitudAnulacionSubsidioCobrado convertToEntity() {
        DetalleSolicitudAnulacionSubsidioCobrado detalleSolicitudAnulacionSubsidioCobrado = new DetalleSolicitudAnulacionSubsidioCobrado();
        detalleSolicitudAnulacionSubsidioCobrado
                .setIdDetalleSolicitudAnulacionSubsidioCobrado(this.getIdDetalleSolicitudAnulacionSubsidioCobrado());
        detalleSolicitudAnulacionSubsidioCobrado.setIdSolicitudAnulacionSubsidio(this.getIdSolicitudAnulacionSubsidio());
        detalleSolicitudAnulacionSubsidioCobrado.setIdCuentaAdministradorSubsidio(this.getIdCuentaAdministradorSubsidio());
        detalleSolicitudAnulacionSubsidioCobrado.setDetalleAnulacion(this.getDetalleAnulacion());
        return detalleSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the idDetalleSolicitudAnulacionSubsidioCobrado
     */
    public Long getIdDetalleSolicitudAnulacionSubsidioCobrado() {
        return idDetalleSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @param idDetalleSolicitudAnulacionSubsidioCobrado
     *        the idDetalleSolicitudAnulacionSubsidioCobrado to set
     */
    public void setIdDetalleSolicitudAnulacionSubsidioCobrado(Long idDetalleSolicitudAnulacionSubsidioCobrado) {
        this.idDetalleSolicitudAnulacionSubsidioCobrado = idDetalleSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the detalleAnulacion
     */
    public String getDetalleAnulacion() {
        return detalleAnulacion;
    }

    /**
     * @param detalleAnulacion
     *        the detalleAnulacion to set
     */
    public void setDetalleAnulacion(String detalleAnulacion) {
        this.detalleAnulacion = detalleAnulacion;
    }

    /**
     * @return the idSolicitudAnulacionSubsidio
     */
    public Long getIdSolicitudAnulacionSubsidio() {
        return idSolicitudAnulacionSubsidio;
    }

    /**
     * @param idSolicitudAnulacionSubsidio
     *        the idSolicitudAnulacionSubsidio to set
     */
    public void setIdSolicitudAnulacionSubsidio(Long idSolicitudAnulacionSubsidio) {
        this.idSolicitudAnulacionSubsidio = idSolicitudAnulacionSubsidio;
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

}