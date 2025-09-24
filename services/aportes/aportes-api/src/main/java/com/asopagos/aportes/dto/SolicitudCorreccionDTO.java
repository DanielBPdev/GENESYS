package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.entidades.ccf.aportes.SolicitudAporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * 
 * DTO que contiene la informacion de una solicitud de correcion
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudCorreccionDTO implements Serializable {

	/**
	 * Tipo de solicitante de la devolución.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	/**
	 * Tipo de identificación del solicitante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	/**
	 * Número de identificación del solicitante.
	 */
	private String numeroIdentificacion;
	/**
	 * Digito de verificación en caso de que sea NIT.
	 */
	private Short digitoVerificacion;
	/**
	 * Nombre del solicitante de la devolución.
	 */
	private String nombreSolicitante;
	/**
	 * Comentario de la solicitud
	 */
	private String comentario;
	/**
	 * Estado de la solicitud.
	 */
	private EstadoSolicitudAporteEnum estado;
	/**
	 * Fecha de recepción de la solicitud de devolución del aporte.
	 */
	private Long fechaRecepcion;
	/**
	 * Identificador del aporte
	 */
	private Long idAporte;
	/**
	 * Lista de documentos a adjuntar que acompañen la solicitud de devolución
	 * de aportes.
	 */
	private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;


    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de dv.
     * @return valor de dv.
     */
    public Short getDv() {
        return digitoVerificacion;
    }

    /**
     * Método encargado de modificar el valor de dv.
     * @param valor para modificar dv.
     */
    public void setDv(Short dv) {
        this.digitoVerificacion = dv;
    }

    /**
     * Método que retorna el valor de nombreSolicitante.
     * @return valor de nombreSolicitante.
     */
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    /**
     * Método encargado de modificar el valor de nombreSolicitante.
     * @param valor para modificar nombreSolicitante.
     */
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    /**
     * Método que retorna el valor de comentario.
     * @return valor de comentario.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Método encargado de modificar el valor de comentario.
     * @param valor para modificar comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Método que retorna el valor de estado.
     * @return valor de estado.
     */
    public EstadoSolicitudAporteEnum getEstado() {
        return estado;
    }

    /**
     * Método encargado de modificar el valor de estado.
     * @param valor para modificar estado.
     */
    public void setEstado(EstadoSolicitudAporteEnum estado) {
        this.estado = estado;
    }

    /**
     * Método que retorna el valor de fechaRecepcion.
     * @return valor de fechaRecepcion.
     */
    public Long getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Método encargado de modificar el valor de fechaRecepcion.
     * @param valor para modificar fechaRecepcion.
     */
    public void setFechaRecepcion(Long fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * Método que retorna el valor de idAporte.
     * @return valor de idAporte.
     */
    public Long getIdAporte() {
        return idAporte;
    }

    /**
     * Método encargado de modificar el valor de idAporte.
     * @param valor para modificar idAporte.
     */
    public void setIdAporte(Long idAporte) {
        this.idAporte = idAporte;
    }

    /**
     * Método que retorna el valor de documentos.
     * @return valor de documentos.
     */
    public List<DocumentoAdministracionEstadoSolicitudDTO> getDocumentos() {
        return documentos;
    }

    /**
     * Método encargado de modificar el valor de documentos.
     * @param valor para modificar documentos.
     */
    public void setDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos) {
        this.documentos = documentos;
    }


    @Override
    public String toString() {
        return "{" +
            " tipoSolicitante='" + getTipoSolicitante() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", digitoVerificacion='" + getDv() + "'" +
            ", nombreSolicitante='" + getNombreSolicitante() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaRecepcion='" + getFechaRecepcion() + "'" +
            ", idAporte='" + getIdAporte() + "'" +
            ", documentos='" + getDocumentos() + "'" +
            "}";
    }
	
}