package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase DTO que representa la información que representa los
 * abonos cobrados asociados a una solicitud de anulación de subsidio cobrado <br/>
 * <b>Módulo:</b> Asopagos - HU-31-222<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 */
@XmlRootElement
public class SolicitudAnulacionSubsidioCobradoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1499803383580614745L;

    /**
     * Identificador de solicitud global generada
     */
    private Long idSolicitud;

    /**
     * Identificador de solicitud de anulacion de subsidio cobrado
     */
    private Long idSolicitudAnulacionSubsidioCobrado;

    /**
     * Indica el numero de radicado de la solicitud de anulacion de subsidio cobrado
     */
    private String numeroRadicado;

    /**
     * Resultado de una operacion
     */
    private Boolean registroExitoso;

    /**
     * Causa error en caso de existir un problema en el registro de la solicitud
     */
    private String causaError;

    /**
     * Representa la información que representa los abonos cobrados asociados a una solicitud de anulación de subsidio cobrado
     */
    @NotNull
    private List<AbonosAnulacionSubsidioCobradoDTO> abonosAnulacionSubsidioCobradoDTO;

    /**
     * Indica las observaciones para la aprobacion o rechazo de la solicitud de anulación de subsidio monetario cobrado
     */
    private String observaciones;

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

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
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado
     *        the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the registroExitoso
     */
    public Boolean getRegistroExitoso() {
        return registroExitoso;
    }

    /**
     * @param registroExitoso
     *        the registroExitoso to set
     */
    public void setRegistroExitoso(Boolean registroExitoso) {
        this.registroExitoso = registroExitoso;
    }

    /**
     * @return the causaError
     */
    public String getCausaError() {
        return causaError;
    }

    /**
     * @param causaError
     *        the causaError to set
     */
    public void setCausaError(String causaError) {
        this.causaError = causaError;
    }

    /**
     * @return the abonosAnulacionSubsidioCobradoDTO
     */
    public List<AbonosAnulacionSubsidioCobradoDTO> getAbonosAnulacionSubsidioCobradoDTO() {
        return abonosAnulacionSubsidioCobradoDTO;
    }

    /**
     * @param abonosAnulacionSubsidioCobradoDTO
     *        the abonosAnulacionSubsidioCobradoDTO to set
     */
    public void setAbonosAnulacionSubsidioCobradoDTO(List<AbonosAnulacionSubsidioCobradoDTO> abonosAnulacionSubsidioCobradoDTO) {
        this.abonosAnulacionSubsidioCobradoDTO = abonosAnulacionSubsidioCobradoDTO;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}