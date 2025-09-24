package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;

/**
 * <b>Descripcion:</b> DTO que representa la entidad del DocumentoSoporteConvenio <br/>
 * <b>Módulo:</b> Asopagos - HU-31-210 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */
@XmlRootElement
public class DocumentoSoporteConvenioModeloDTO implements Serializable {

    private static final long serialVersionUID = -4787654038532147622L;

    /**
     * Id del convenio.
     */
    private Long idConvenioTerceroPagador;

    /**
     * Nombre que identifica el documento de soporte
     */
    private String nombre;

    /**
     * Descripción y/o comentarios asociado al documento.
     */
    private String descripcionComentario;

    /**
     * Identificación del documento cargado en Alfresco.
     */
    private String identificacionDocumento;

    /**
     * Versión del documento cargado en Alfresco.
     */
    private String versionDocumento;

    /**
     * Fecha y hora en la cual se cargo el documento de soporte
     */
    private Long fechaHoraCargue;

    /**
     * Constructor vacio de la clase.
     */
    public DocumentoSoporteConvenioModeloDTO() {

    }

    /**
     * Constructor que inicializa la clase por medio de los datos de la entidad
     * 
     * @param documentoSoporte
     *        entidad que representa el documento de soporte del convenio
     */
    public DocumentoSoporteConvenioModeloDTO(DocumentoSoporte documentoSoporte) {

        this.setNombre(documentoSoporte.getNombre());
        this.setDescripcionComentario(documentoSoporte.getDescripcionComentarios());
        this.setIdentificacionDocumento(documentoSoporte.getIdentificacionDocumento());
        this.setVersionDocumento(documentoSoporte.getVersionDocumento());
        this.setFechaHoraCargue(documentoSoporte.getFechaHoraCargue().getTime());
    }

    /**
     * Metodo que convierte el DTO en Entidad.
     * @return entidad de documento de soporte de convenio.
     */
    public DocumentoSoporte convertToEntity() {

        DocumentoSoporte documentoSoporte = new DocumentoSoporte();

        documentoSoporte.setNombre(this.getNombre());
        documentoSoporte.setDescripcionComentarios(this.getDescripcionComentario());
        documentoSoporte.setIdentificacionDocumento(this.getIdentificacionDocumento());
        documentoSoporte.setVersionDocumento(this.getVersionDocumento());
        documentoSoporte.setFechaHoraCargue(new Date(this.getFechaHoraCargue()));

        return documentoSoporte;
    }

    /**
     * @return the idConvenioTerceroPagador
     */
    public Long getIdConvenioTerceroPagador() {
        return idConvenioTerceroPagador;
    }

    /**
     * @param idConvenioTerceroPagador
     *        the idConvenioTerceroPagador to set
     */
    public void setIdConvenioTerceroPagador(Long idConvenioTerceroPagador) {
        this.idConvenioTerceroPagador = idConvenioTerceroPagador;
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
     * @return the descripcionComentario
     */
    public String getDescripcionComentario() {
        return descripcionComentario;
    }

    /**
     * @param descripcionComentario
     *        the descripcionComentario to set
     */
    public void setDescripcionComentario(String descripcionComentario) {
        this.descripcionComentario = descripcionComentario;
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

}
