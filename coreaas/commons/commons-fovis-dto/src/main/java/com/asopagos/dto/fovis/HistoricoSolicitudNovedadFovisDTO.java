/**
 * 
 */
package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;

/**
 * Clase DTO que contiene los datos de una solicitud de novedad FOVIS.
 * 
 * @author Edward Castano<ecastasno@heinsohn.com.co>
 *
 */
@XmlRootElement
public class HistoricoSolicitudNovedadFovisDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5366556390922733761L;

	/**
	 * Id de la solicitud asociada a la novedad.
	 */
	private Long idSolicitud;

	/**
	 * Id de la solicitud de novedad
	 */
	private Long idSolicitudNovedadFovis;

	/**
	 * Id instancia del proceso.
	 */
	private Long idInstancia;

	/**
	 * Nombre de la novedad
	 */
	private ParametrizacionNovedadModeloDTO parametrizacionNovedad;

	/**
	 * Estado de la solicitud de novedad.
	 */
	private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;

	/**
	 * Número de radicación despues de radicar la solicitud
	 */
	private String numeroRadicacion;

	/**
	 * Fecha de radicación de la solicitud
	 */
	private Long fechaRadicacion;

	/**
	 * Informacion postulación sujeto a cambios por novedad
	 */
	private SolicitudPostulacionFOVISDTO datosPostulacion;

	/**
	 * Identificador postulacion
	 */
	private Long idPostulacion;

	/**
	 * Identificador comunicado solicitud
	 */
	private String idComunicadoSolicitud;
	
	/**
     * Tipo de solicitud
     */
    private TipoSolicitudEnum tipoSolicitud;

	/**
	 * Constructor por defecto
	 */
	public HistoricoSolicitudNovedadFovisDTO() {
		super();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param idSolicitudPostulacion
	 * @param idSolicitud
	 * @param numeroRadicacion
	 * @param postulacion
	 */
	public HistoricoSolicitudNovedadFovisDTO(String idSolicitud, String numeroRadicacion, Date fechaRadicacion,
			String idSolicitudNovedad, EstadoSolicitudNovedadFovisEnum estadoSolicitud, String idComunicadoSolicitud) {
		super();
		this.idSolicitudNovedadFovis = idSolicitudNovedad != null ? Long.parseLong(idSolicitudNovedad) : null;
		this.idSolicitud = idSolicitud != null ? Long.parseLong(idSolicitud) : null;
		this.numeroRadicacion = numeroRadicacion;
		this.estadoSolicitudNovedad = estadoSolicitud;
		this.fechaRadicacion = fechaRadicacion != null ? fechaRadicacion.getTime() : null;
		this.idComunicadoSolicitud = idComunicadoSolicitud;
		this.tipoSolicitud = TipoSolicitudEnum.NOVEDAD_FOVIS;
	}

	/**
	 * Método que retorna el valor de idSolicitud.
	 * 
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * 
	 * @param valor
	 *            para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de novedadDTO.
	 * 
	 * @return valor de novedadDTO.
	 */
	public ParametrizacionNovedadModeloDTO getParametrizacionNovedad() {
		return parametrizacionNovedad;
	}

	/**
	 * Método encargado de modificar el valor de novedadDTO.
	 * 
	 * @param valor
	 *            para modificar novedadDTO.
	 */
	public void setParametrizacionNovedad(ParametrizacionNovedadModeloDTO parametrizacionNovedad) {
		this.parametrizacionNovedad = parametrizacionNovedad;
	}

	/**
	 * @return the idSolicitudNovedadFovis
	 */
	public Long getIdSolicitudNovedadFovis() {
		return idSolicitudNovedadFovis;
	}

	/**
	 * @param idSolicitudNovedadFovis
	 *            the idSolicitudNovedadFovis to set
	 */
	public void setIdSolicitudNovedadFovis(Long idSolicitudNovedadFovis) {
		this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
	}

	/**
	 * @return the idInstancia
	 */
	public Long getIdInstancia() {
		return idInstancia;
	}

	/**
	 * @param idInstancia
	 *            the idInstancia to set
	 */
	public void setIdInstancia(Long idInstancia) {
		this.idInstancia = idInstancia;
	}

	/**
	 * @return the estadoSolicitudNovedad
	 */
	public EstadoSolicitudNovedadFovisEnum getEstadoSolicitudNovedad() {
		return estadoSolicitudNovedad;
	}

	/**
	 * @param estadoSolicitudNovedad
	 *            the estadoSolicitudNovedad to set
	 */
	public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad) {
		this.estadoSolicitudNovedad = estadoSolicitudNovedad;
	}

	/**
	 * @return the numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * @param numeroRadicacion
	 *            the numeroRadicacion to set
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Long getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion
	 *            the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Long fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the datosPostulacion
	 */
	public SolicitudPostulacionFOVISDTO getDatosPostulacion() {
		return datosPostulacion;
	}

	/**
	 * @param datosPostulacion
	 *            the datosPostulacion to set
	 */
	public void setDatosPostulacion(SolicitudPostulacionFOVISDTO datosPostulacion) {
		this.datosPostulacion = datosPostulacion;
	}

	/**
	 * @return the idPostulacion
	 */
	public Long getIdPostulacion() {
		return idPostulacion;
	}

	/**
	 * @param idPostulacion
	 *            the idPostulacion to set
	 */
	public void setIdPostulacion(Long idPostulacion) {
		this.idPostulacion = idPostulacion;
	}

	/**
	 * @return the idComunicadoSolicitud
	 */
	public String getIdComunicadoSolicitud() {
		return idComunicadoSolicitud;
	}

	/**
	 * @param idComunicadoSolicitud
	 *            the idComunicadoSolicitud to set
	 */
	public void setIdComunicadoSolicitud(String idComunicadoSolicitud) {
		this.idComunicadoSolicitud = idComunicadoSolicitud;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public TipoSolicitudEnum getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

}
