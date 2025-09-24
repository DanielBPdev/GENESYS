package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Modelo de DTO que representa el detalle de los subsidios asignados asociados por abono agrupador a una
 * solicitud de anulación de subsidio cobrado </br>
 * <b>Módulo:</b> Asopagos - HU-31-222-227<br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel</a>
 */
@XmlRootElement
public class DetalleSolicitudAnulacionSubsidioCobradoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7852404012549312155L;

    /**
     * Representa el identificador de la solicitud de anulación de subsidio cobrado
     */
    private Long idSolicitudAnulacionSubsidioCobrado;

    /**
     * Indica la lista de transacciones de abono cobradas de cuentas de administradores de subsidios con su respectivo detalle asociadas a
     * la solicitud de anulacion de subsidio cobrado.
     */
    private List<AbonosSolicitudAnulacionSubsidioCobradoDTO> abonosSolicitudAnulacionSubsidioCobradoDTO;

    /**
     * @return the idSolicitudAnulacionSubsidioCobrado
     */
    public Long getIdSolicitudAnulacionSubsidioCobrado() {
        return idSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @param idSolicitudAnulacionSubsidioCobrado
     *        the idSolicitudAnulacionSubsidioCobrado to set
     */
    public void setIdSolicitudAnulacionSubsidioCobrado(Long idSolicitudAnulacionSubsidioCobrado) {
        this.idSolicitudAnulacionSubsidioCobrado = idSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the abonosSolicitudAnulacionSubsidioCobradoDTO
     */
    public List<AbonosSolicitudAnulacionSubsidioCobradoDTO> getAbonosSolicitudAnulacionSubsidioCobradoDTO() {
        return abonosSolicitudAnulacionSubsidioCobradoDTO;
    }

    /**
     * @param abonosSolicitudAnulacionSubsidioCobradoDTO
     *        the abonosSolicitudAnulacionSubsidioCobradoDTO to set
     */
    public void setAbonosSolicitudAnulacionSubsidioCobradoDTO(
            List<AbonosSolicitudAnulacionSubsidioCobradoDTO> abonosSolicitudAnulacionSubsidioCobradoDTO) {
        this.abonosSolicitudAnulacionSubsidioCobradoDTO = abonosSolicitudAnulacionSubsidioCobradoDTO;
    }

}