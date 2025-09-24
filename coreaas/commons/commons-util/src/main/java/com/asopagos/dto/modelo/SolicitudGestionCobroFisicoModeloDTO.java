package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroFisico;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;

/**
 * DTO que contiene los campos Solicitud Gestión Cobro Físico.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudGestionCobroFisicoModeloDTO extends SolicitudModeloDTO implements Serializable {

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudGestionCobroFisico;

    /**
     * Tipo de acción de cobro
     */
    private TipoAccionCobroEnum tipoAccionCobro;

    /**
     * Estado de la solicitud
     */
    private EstadoSolicitudGestionCobroEnum estado;

    /**
     * Fecha de remisión
     */
    private Long fechaRemision;

    /**
     * Identificador del documento asociado a la solicitud. Referencia a la
     * tabla <code>DocumentoCartera</code>
     */
    private DocumentoSoporteModeloDTO documentoSoporte;

    /**
     * Comentarios añadidos a la remisión
     */
    private String observacionRemision;

    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public SolicitudGestionCobroFisico convertToEntity() {
        SolicitudGestionCobroFisico solicitudGestionCobroFisico = new SolicitudGestionCobroFisico();
        solicitudGestionCobroFisico.setIdSolicitudGestionCobroFisico(this.getIdSolicitudGestionCobroFisico());
        solicitudGestionCobroFisico.setEstado(this.getEstado());
        solicitudGestionCobroFisico.setFechaRemision(this.getFechaRemision() != null ? new Date(this.getFechaRemision()) : null);
        
        if (this.getDocumentoSoporte() != null) {
        DocumentoSoporte documento = this.documentoSoporte.convertToEntity();
        solicitudGestionCobroFisico.setDocumento(documento != null ? documento : null);
        }
        solicitudGestionCobroFisico.setObservacionRemision(this.getObservacionRemision());
        solicitudGestionCobroFisico.setTipoAccionCobro(this.getTipoAccionCobro());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudGestionCobroFisico.setSolicitudGlobal(solicitudGlobal);
        return solicitudGestionCobroFisico;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud
     *        entidad a convertir.
     */
    public void convertToDTO(SolicitudGestionCobroFisico solicitudGestionCobroFisico) {
        if (solicitudGestionCobroFisico.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudGestionCobroFisico.getSolicitudGlobal());
        }
        this.setIdSolicitudGestionCobroFisico(solicitudGestionCobroFisico.getIdSolicitudGestionCobroFisico());
        this.setEstado(solicitudGestionCobroFisico.getEstado());
        this.setFechaRemision(
                solicitudGestionCobroFisico.getFechaRemision() != null ? solicitudGestionCobroFisico.getFechaRemision().getTime() : null);
        
        if (solicitudGestionCobroFisico.getDocumento() != null) {
            DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
            documentoSoporteModeloDTO.convertToDTO(solicitudGestionCobroFisico.getDocumento());
            this.setDocumentoSoporte(documentoSoporteModeloDTO);
        }
        
        this.setObservacionRemision(solicitudGestionCobroFisico.getObservacionRemision());
        this.setTipoAccionCobro(solicitudGestionCobroFisico.getTipoAccionCobro());
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * @param solicitudGestionCobro
     *        previamente consultado.
     * @return solicitudNovedad solicitud modificada con los datos del DTO.
     */
    public SolicitudGestionCobroFisico copyDTOToEntiy(SolicitudGestionCobroFisico solicitudGestionCobro) {
        if (this.getIdSolicitudGestionCobroFisico() != null) {
            solicitudGestionCobro.setIdSolicitudGestionCobroFisico(this.getIdSolicitudGestionCobroFisico());
        }
        if (this.getEstado() != null) {
            solicitudGestionCobro.setEstado(solicitudGestionCobro.getEstado());
        }
        if (this.getFechaRemision() != null) {
            solicitudGestionCobro.setFechaRemision(new Date(this.getFechaRemision()));
        }
        if (this.getDocumentoSoporte() != null) {
            
            DocumentoSoporte documento = this.documentoSoporte.convertToEntity();
            solicitudGestionCobro.setDocumento(documento);
        }
        if (this.getObservacionRemision() != null) {
            solicitudGestionCobro.setObservacionRemision(this.getObservacionRemision());
        }
        if (this.getTipoAccionCobro() != null) {
            solicitudGestionCobro.setTipoAccionCobro(this.getTipoAccionCobro());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudGestionCobro.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudGestionCobro.setSolicitudGlobal(solicitudGlobal);
        }
        return solicitudGestionCobro;
    }

    /**
     * Método que retorna el valor de idSolicitudGestionCobroFisico.
     * @return valor de idSolicitudGestionCobroFisico.
     */
    public Long getIdSolicitudGestionCobroFisico() {
        return idSolicitudGestionCobroFisico;
    }

    /**
     * Método que retorna el valor de tipoAccionCobro.
     * @return valor de tipoAccionCobro.
     */
    public TipoAccionCobroEnum getTipoAccionCobro() {
        return tipoAccionCobro;
    }

    /**
     * Método que retorna el valor de estado.
     * @return valor de estado.
     */
    public EstadoSolicitudGestionCobroEnum getEstado() {
        return estado;
    }

    /**
     * Método que retorna el valor de fechaRemision.
     * @return valor de fechaRemision.
     */
    public Long getFechaRemision() {
        return fechaRemision;
    }

    /**
     * Método que retorna el valor de documentoSoporte.
     * @return valor de documentoSoporte.
     */
    public DocumentoSoporteModeloDTO getDocumentoSoporte() {
        return documentoSoporte;
    }

    /**
     * Método que retorna el valor de observacionRemision.
     * @return valor de observacionRemision.
     */
    public String getObservacionRemision() {
        return observacionRemision;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudGestionCobroFisico.
     * @param valor
     *        para modificar idSolicitudGestionCobroFisico.
     */
    public void setIdSolicitudGestionCobroFisico(Long idSolicitudGestionCobroFisico) {
        this.idSolicitudGestionCobroFisico = idSolicitudGestionCobroFisico;
    }

    /**
     * Método encargado de modificar el valor de tipoAccionCobro.
     * @param valor
     *        para modificar tipoAccionCobro.
     */
    public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
        this.tipoAccionCobro = tipoAccionCobro;
    }

    /**
     * Método encargado de modificar el valor de estado.
     * @param valor
     *        para modificar estado.
     */
    public void setEstado(EstadoSolicitudGestionCobroEnum estado) {
        this.estado = estado;
    }

    /**
     * Método encargado de modificar el valor de fechaRemision.
     * @param valor
     *        para modificar fechaRemision.
     */
    public void setFechaRemision(Long fechaRemision) {
        this.fechaRemision = fechaRemision;
    }

    /**
     * Método encargado de modificar el valor de documentoSoporte.
     * @param valor
     *        para modificar documentoSoporte.
     */
    public void setDocumentoSoporte(DocumentoSoporteModeloDTO documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    /**
     * Método encargado de modificar el valor de observacionRemision.
     * @param valor
     *        para modificar observacionRemision.
     */
    public void setObservacionRemision(String observacionRemision) {
        this.observacionRemision = observacionRemision;
    }

}
