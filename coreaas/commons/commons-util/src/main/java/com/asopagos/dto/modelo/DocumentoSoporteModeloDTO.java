package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.enumeraciones.aportes.TipoDocumentoAdjuntoEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * 
 * Contiene la información del Documento soporte - Proceso FOVIS 3.2.4
 * 
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class DocumentoSoporteModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -1720843832505134491L;

    private Long idDocumentoSoporte;

    /**
     * Nombre del documento de soporte.
     */
    private String nombre;

    /**
     * Descripción y/o comentarios del documento de soporte.
     */
    private String descripcionComentarios;

    /**
     * Identificador del documento de soporte que se carga en Alfresco.
     */
    private String identificacionDocumento;

    /**
     * Versión del documento de soporte que se carga en Alfresco.
     */
    private String versionDocumento;

    /**
     * Indica la fecha y hora de cargue del documento
     */
    private Long fechaHoraCargue;

    /**
     * Tipo documento adjunto
     */
    private TipoDocumentoAdjuntoEnum tipoDocumento;

    /**
     * Identificador principal de la bitacora cartera relacionada al documento soporte.
     */
    private Long idBitacoraCartera;
    
    /**
     * Asociación con el tipo de documento en el proceso FOVIS
     */
    private Long idTipoDocumentoSoporteFovis;
    
    /**
     * Constructor vacio
     */
    public DocumentoSoporteModeloDTO() {
    }

    /**
     * Constructor que recibe el entity
     */
    public DocumentoSoporteModeloDTO(DocumentoSoporte documentoSoporte) {
        convertToDTO(documentoSoporte);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return ResultadosAsignacionFOVIS
     */
    public DocumentoSoporte convertToEntity() {
        DocumentoSoporte documentoSoporte = new DocumentoSoporte();

        documentoSoporte.setDescripcionComentarios(this.getDescripcionComentarios() != null ? this.getDescripcionComentarios() : "");
        documentoSoporte.setFechaHoraCargue(this.getFechaHoraCargue() != null ? new Date(this.getFechaHoraCargue()) : new Date());
        documentoSoporte.setIdDocumentoSoporte(this.getIdDocumentoSoporte());
        documentoSoporte.setIdentificacionDocumento(this.getIdentificacionDocumento());
        documentoSoporte.setNombre(this.getNombre() != null ? this.getNombre() : "");
        documentoSoporte.setVersionDocumento(this.getVersionDocumento());
        documentoSoporte.setTipoDocumento(this.getTipoDocumento());
        documentoSoporte.setIdBitacoraCartera(this.getIdBitacoraCartera());
        documentoSoporte.setTipoDocumentoSoporteFovis(this.getIdTipoDocumentoSoporteFovis());
        return documentoSoporte;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param ResultadosAsignacionFOVIS
     */
    public void convertToDTO(DocumentoSoporte documentoSoporte) {
        this.setDescripcionComentarios(documentoSoporte.getDescripcionComentarios());
        this.setFechaHoraCargue(documentoSoporte.getFechaHoraCargue() != null ? documentoSoporte.getFechaHoraCargue().getTime() : null);
        this.setIdDocumentoSoporte(documentoSoporte.getIdDocumentoSoporte());
        this.setIdentificacionDocumento(documentoSoporte.getIdentificacionDocumento());
        this.setNombre(documentoSoporte.getNombre());
        this.setVersionDocumento(documentoSoporte.getVersionDocumento());
        this.setTipoDocumento(documentoSoporte.getTipoDocumento());
        this.setIdBitacoraCartera(documentoSoporte.getIdBitacoraCartera());
        this.setIdTipoDocumentoSoporteFovis(documentoSoporte.getTipoDocumentoSoporteFovis());
    }

    /**
     * @return the idDocumentoSoporte
     */
    public Long getIdDocumentoSoporte() {
        return idDocumentoSoporte;
    }

    /**
     * @param idDocumentoSoporte
     *        the idDocumentoSoporte to set
     */
    public void setIdDocumentoSoporte(Long idDocumentoSoporte) {
        this.idDocumentoSoporte = idDocumentoSoporte;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcionComentarios
     */
    public String getDescripcionComentarios() {
        return descripcionComentarios;
    }

    /**
     * @param descripcionComentarios
     *        the descripcionComentarios to set
     */
    public void setDescripcionComentarios(String descripcionComentarios) {
        this.descripcionComentarios = descripcionComentarios;
    }

    /**
     * @return the identificacionDocumento
     */
    public String getIdentificacionDocumento() {
        return identificacionDocumento;
    }

    /**
     * @param identificacionDocumento
     *        the identificacionDocumento to set
     */
    public void setIdentificacionDocumento(String identificacionDocumento) {
        this.identificacionDocumento = identificacionDocumento;
    }

    /**
     * @return the versionDocumento
     */
    public String getVersionDocumento() {
        return versionDocumento;
    }

    /**
     * @param versionDocumento
     *        the versionDocumento to set
     */
    public void setVersionDocumento(String versionDocumento) {
        this.versionDocumento = versionDocumento;
    }

    /**
     * @return the fechaHoraCargue
     */
    public Long getFechaHoraCargue() {
        return fechaHoraCargue;
    }

    /**
     * @param fechaHoraCargue
     *        the fechaHoraCargue to set
     */
    public void setFechaHoraCargue(Long fechaHoraCargue) {
        this.fechaHoraCargue = fechaHoraCargue;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumentoAdjuntoEnum getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *        the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumentoAdjuntoEnum tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    /**
     * @return the idBitacoraCartera
     */
    public Long getIdBitacoraCartera() {
        return idBitacoraCartera;
    }

    /**
     * @param idBitacoraCartera the idBitacoraCartera to set
     */
    public void setIdBitacoraCartera(Long idBitacoraCartera) {
        this.idBitacoraCartera = idBitacoraCartera;
    }

    /**
     * @return the idTipoDocumentoSoporteFovis
     */
    public Long getIdTipoDocumentoSoporteFovis() {
        return idTipoDocumentoSoporteFovis;
    }

    /**
     * @param idTipoDocumentoSoporteFovis the idTipoDocumentoSoporteFovis to set
     */
    public void setIdTipoDocumentoSoporteFovis(Long idTipoDocumentoSoporteFovis) {
        this.idTipoDocumentoSoporteFovis = idTipoDocumentoSoporteFovis;
    }

    /** (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idDocumentoSoporte == null) ? 0 : idDocumentoSoporte.hashCode());
        return result;
    }

    /** (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DocumentoSoporteModeloDTO other = (DocumentoSoporteModeloDTO) obj;
        if (idDocumentoSoporte == null) {
            if (other.idDocumentoSoporte != null)
                return false;
        }
        else if (!idDocumentoSoporte.equals(other.idDocumentoSoporte))
            return false;
        return true;
    }

}
