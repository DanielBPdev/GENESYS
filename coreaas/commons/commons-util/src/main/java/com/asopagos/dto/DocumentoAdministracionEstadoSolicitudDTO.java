/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.DocumentoAdministracionEstadoSolicitud;
import com.asopagos.enumeraciones.aportes.ActividadEnum;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;

/**
 * DTO que contiene los datos que mapean a un documento adjunto que no es lista de chequeo.
 * @author jzambrano
 */
@XmlRootElement
public class DocumentoAdministracionEstadoSolicitudDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria de la <code>DocumentoAdministracionEstadoSolicitud</code>
     */
    private Long idDocumentoAdminEstado;

    /**
     * Id que identifica la solicitud asociada al documento de soporte
     */
    private Long idSolicitudGlobal;

    /**
     * Id que identifica el documento de soporte de gestión en el ECM
     */
    private String identificadorDocumentoSoporteCambioEstado;

    /**
     * nombre que hace alusión al documento cargado
     */
    private String nombreDocumento;

    /**
     * Tipo de documento adjunto.
     */
    private TipoDocumentoAdjuntoEnum tipoDocumentoAdjunto;

    /**
     * Actividad realizada.
     */
    private ActividadEnum actividad;

    /**
     * Convertidor del DTO a la entidad.
     * @param dto
     *        a convertir.
     * @return entidad convertida.
     */
    public DocumentoAdministracionEstadoSolicitud convertToEntity(DocumentoAdministracionEstadoSolicitudDTO dto) {
        DocumentoAdministracionEstadoSolicitud entidad = new DocumentoAdministracionEstadoSolicitud();
        entidad.setIdentificadorDocumentoSoporteCambioEstado(dto.getIdentificadorDocumentoSoporteCambioEstado());
        entidad.setNombreDocumento(dto.getNombreDocumento());
        entidad.setIdSolicitudGlobal(dto.getIdSolicitudGlobal());
        entidad.setTipoDocumentoAdjunto(dto.getTipoDocumentoAdjunto());
        entidad.setActividad(dto.getActividad());
        return entidad;
    }

    /**
     * Método que convierta la entidad a un DTO.
     * @param entidad
     *        a convertir.
     */
    public void convertToDTO(DocumentoAdministracionEstadoSolicitud entidad) {
        this.setIdDocumentoAdminEstado(entidad.getIdDocumentoAdminEstado());
        this.setIdentificadorDocumentoSoporteCambioEstado(entidad.getIdentificadorDocumentoSoporteCambioEstado());
        this.setIdSolicitudGlobal(entidad.getIdSolicitudGlobal());
        this.setNombreDocumento(entidad.getNombreDocumento());
        this.setTipoDocumentoAdjunto(entidad.getTipoDocumentoAdjunto());
        this.setActividad(entidad.getActividad());
    }

    /**
     * Método que retorna el valor de idDocumentoAdminEstado.
     * @return valor de idDocumentoAdminEstado.
     */
    public Long getIdDocumentoAdminEstado() {
        return idDocumentoAdminEstado;
    }

    /**
     * Método encargado de modificar el valor de idDocumentoAdminEstado.
     * @param valor
     *        para modificar idDocumentoAdminEstado.
     */
    public void setIdDocumentoAdminEstado(Long idDocumentoAdminEstado) {
        this.idDocumentoAdminEstado = idDocumentoAdminEstado;
    }

    /**
     * Método que retorna el valor de idSolicitudGlobal.
     * @return valor de idSolicitudGlobal.
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudGlobal.
     * @param valor
     *        para modificar idSolicitudGlobal.
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * Método que retorna el valor de identificadorDocumentoSoporteCambioEstado.
     * @return valor de identificadorDocumentoSoporteCambioEstado.
     */
    public String getIdentificadorDocumentoSoporteCambioEstado() {
        return identificadorDocumentoSoporteCambioEstado;
    }

    /**
     * Método encargado de modificar el valor de identificadorDocumentoSoporteCambioEstado.
     * @param valor
     *        para modificar identificadorDocumentoSoporteCambioEstado.
     */
    public void setIdentificadorDocumentoSoporteCambioEstado(String identificadorDocumentoSoporteCambioEstado) {
        this.identificadorDocumentoSoporteCambioEstado = identificadorDocumentoSoporteCambioEstado;
    }

    /**
     * Método que retorna el valor de nombreDocumento.
     * @return valor de nombreDocumento.
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * Método encargado de modificar el valor de nombreDocumento.
     * @param valor
     *        para modificar nombreDocumento.
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * Método que retorna el valor de tipoDocumentoAdjunto.
     * @return valor de tipoDocumentoAdjunto.
     */
    public TipoDocumentoAdjuntoEnum getTipoDocumentoAdjunto() {
        return tipoDocumentoAdjunto;
    }

    /**
     * Método encargado de modificar el valor de tipoDocumentoAdjunto.
     * @param valor
     *        para modificar tipoDocumentoAdjunto.
     */
    public void setTipoDocumentoAdjunto(TipoDocumentoAdjuntoEnum tipoDocumentoAdjunto) {
        this.tipoDocumentoAdjunto = tipoDocumentoAdjunto;
    }

    /**
     * Método que retorna el valor de actividad.
     * @return valor de actividad.
     */
    public ActividadEnum getActividad() {
        return actividad;
    }

    /**
     * Método encargado de modificar el valor de actividad.
     * @param valor
     *        para modificar actividad.
     */
    public void setActividad(ActividadEnum actividad) {
        this.actividad = actividad;
    }
}
