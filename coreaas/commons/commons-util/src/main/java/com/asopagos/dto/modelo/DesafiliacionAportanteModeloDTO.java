package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.DesafiliacionAportante;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * <b>Descripcion:</b> Clase que representa de modo DTO
 * a la entidad DesafiliacionAportante <br/>
 * <b>Módulo:</b> Asopagos - HU 197<br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DesafiliacionAportanteModeloDTO implements Serializable {

    /**
     * Serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de Aportante de Desafiliacion
     */
    private Long idAportanteDesafiliacion;

    /**
     * Id de la Solicitud de Desafiliacion
     */
    private Long idSolicitudDesafiliacion;

    /**
     * Id de la persona
     */
    private Long idPersona;

    /**
     * Tipo de solicitante de la persona
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    
    /**
     * Tipo de línea de cobro
     */
    private TipoLineaCobroEnum tipoLineaCobro;

    /**
     * Constructor de la clase
     */
    public DesafiliacionAportanteModeloDTO() {
    }

    /**
     * Metodo que permite convertir de entidad a DTO
     * @param desafiliacionAportante
     *        recibe como parametro la entidad
     * @return retorna un DTO DesafiliacionAportanteModeloDTO
     */
    public DesafiliacionAportanteModeloDTO convertToDTO(DesafiliacionAportante desafiliacionAportante) {
        /* Se construye el objeto DTO */
        this.setIdAportanteDesafiliacion(
                desafiliacionAportante.getIdAportanteDesafiliacion() != null ? desafiliacionAportante.getIdAportanteDesafiliacion() : null);
        this.setIdPersona(desafiliacionAportante.getIdPersona() != null ? desafiliacionAportante.getIdPersona() : null);
        this.setTipoLineaCobro(desafiliacionAportante.getTipoLineaCobro());
        this.setIdSolicitudDesafiliacion(
                desafiliacionAportante.getIdSolicitudDesafiliacion() != null ? desafiliacionAportante.getIdSolicitudDesafiliacion() : null);
        this.setTipoSolicitante(desafiliacionAportante.getTipoSolicitante() != null ? desafiliacionAportante.getTipoSolicitante() : null);
        return this;
    }

    /**
     * Metodo que permite convertir de DTO a entidad
     * @return una entidad DesafiliacionAportante
     */
    public DesafiliacionAportante convertToEnity() {
        /* Se construye el objeto Entity */
        DesafiliacionAportante desafiliacionAportante = new DesafiliacionAportante();
        desafiliacionAportante
                .setIdAportanteDesafiliacion(this.getIdAportanteDesafiliacion() != null ? this.getIdAportanteDesafiliacion() : null);
        desafiliacionAportante.setIdPersona(this.getIdPersona() != null ? this.getIdPersona() : null);
        desafiliacionAportante
                .setIdSolicitudDesafiliacion(this.getIdSolicitudDesafiliacion() != null ? this.getIdSolicitudDesafiliacion() : null);
        desafiliacionAportante.setTipoSolicitante(this.getTipoSolicitante() != null ? this.getTipoSolicitante() : null);
        desafiliacionAportante.setTipoLineaCobro(this.getTipoLineaCobro() != null ? this.getTipoLineaCobro() : null);
        return desafiliacionAportante;
    }

    /**
     * Método que retorna el valor de idSolicitudDesafiliacion.
     * @return valor de idSolicitudDesafiliacion.
     */
    public Long getIdSolicitudDesafiliacion() {
        return idSolicitudDesafiliacion;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudDesafiliacion.
     * @param valor
     *        para modificar idSolicitudDesafiliacion.
     */
    public void setIdSolicitudDesafiliacion(Long idSolicitudDesafiliacion) {
        this.idSolicitudDesafiliacion = idSolicitudDesafiliacion;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de idAportanteDesafiliacion.
     * @return valor de idAportanteDesafiliacion.
     */
    public Long getIdAportanteDesafiliacion() {
        return idAportanteDesafiliacion;
    }

    /**
     * Método encargado de modificar el valor de idAportanteDesafiliacion.
     * @param valor
     *        para modificar idAportanteDesafiliacion.
     */
    public void setIdAportanteDesafiliacion(Long idAportanteDesafiliacion) {
        this.idAportanteDesafiliacion = idAportanteDesafiliacion;
    }

    /**
     * Método que retorna el valor de tipoLineaCobro.
     * @return valor de tipoLineaCobro.
     */
    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    /**
     * Método encargado de modificar el valor de tipoLineaCobro.
     * @param valor para modificar tipoLineaCobro.
     */
    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }
}
