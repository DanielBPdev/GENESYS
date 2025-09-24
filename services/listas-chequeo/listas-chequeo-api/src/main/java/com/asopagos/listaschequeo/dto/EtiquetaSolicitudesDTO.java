package com.asopagos.listaschequeo.dto;

import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class EtiquetaSolicitudesDTO {
    /**
     * Código identificador de llave primaria del requisito
     */
    private Long idRequisito;

    /**
     * Descripcion del requisito
     */
    private String descripcionRequisito;
    /**
     * Código identificador de llave primaria de la persona
     */
    private Long idPersona;
    /**
     * tipo de identificacion de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacionPersona;

    /**
     * numero de identificacion de la persona
     */
    private String numeroIdentificacion;
    /**
     * Código identificador de llave primaria de la solicitud
     */
    private Long idSolicitud;

    /**
     * Numero de radicado
     */
    private String numeroRadicacion;
    /**
     * Nombre completo de la persona
     */
    private String nombreCompleto;

    /**
     * Formato de entrega del documento
     */
    private FormatoEntregaDocumentoEnum formatoEntregaDocumento;

    /**
     * 
     * @param idRequisito
     * @param descripcionRequisito
     * @param idPersona
     * @param tipoIdentificacionPersona
     * @param numeroIdentificacion
     * @param idSolicitud
     * @param numeroRadicacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     */

    public EtiquetaSolicitudesDTO(Long idRequisito, String descripcionRequisito, Long idPersona,
            TipoIdentificacionEnum tipoIdentificacionPersona, String numeroIdentificacion, Long idSolicitud, String numeroRadicacion,
            String razonSocial, FormatoEntregaDocumentoEnum formatoEntregaDocumento) {
        this.idRequisito = idRequisito;
        this.descripcionRequisito = descripcionRequisito;
        this.idPersona = idPersona;
        this.tipoIdentificacionPersona = tipoIdentificacionPersona;
        this.numeroIdentificacion = numeroIdentificacion;
        this.idSolicitud = idSolicitud;
        this.numeroRadicacion = numeroRadicacion;
        this.nombreCompleto = razonSocial;
        this.formatoEntregaDocumento = formatoEntregaDocumento;
    }

    public Long getIdRequisito() {
        return idRequisito;
    }

    public void setIdRequisito(Long idRequisito) {
        this.idRequisito = idRequisito;
    }

    public String getDescripcionRequisito() {
        return descripcionRequisito;
    }

    public void setDescripcionRequisito(String descripcionRequisito) {
        this.descripcionRequisito = descripcionRequisito;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public TipoIdentificacionEnum getTipoIdentificacionPersona() {
        return tipoIdentificacionPersona;
    }

    public void setTipoIdentificacionPersona(TipoIdentificacionEnum tipoIdentificacionPersona) {
        this.tipoIdentificacionPersona = tipoIdentificacionPersona;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the formatoEntregaDocumento
     */
    public FormatoEntregaDocumentoEnum getFormatoEntregaDocumento() {
        return formatoEntregaDocumento;
    }

    /**
     * @param formatoEntregaDocumento
     *        the formatoEntregaDocumento to set
     */
    public void setFormatoEntregaDocumento(FormatoEntregaDocumentoEnum formatoEntregaDocumento) {
        this.formatoEntregaDocumento = formatoEntregaDocumento;
    }

}
