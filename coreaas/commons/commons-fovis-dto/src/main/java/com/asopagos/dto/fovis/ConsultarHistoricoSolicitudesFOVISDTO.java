package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion para consultar las
 * solicitudes FOVIS<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-053<br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
public class ConsultarHistoricoSolicitudesFOVISDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6516256299881192950L;

	/**
	 * Identificador de la solicitud a consultar
	 */
	private String numeroSolicitud;

	/**
	 * Identificador de la postulacion
	 */
	private TipoSolicitudEnum tipoSolicitud;

	/**
	 * Estado de la solicitud de novedad.
	 */
	private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;

	/**
	 * Estado de la solicitud legalizacion.
	 */
	private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion;

	/**
	 * Estado de la solicitud postulación.
	 */
	private EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion;

	/**
	 * Fecha de radicacion
	 */
	private Long fechaExactaRadicacion;

	/**
	 * Fecha inicio
	 */
	private Long fechaInicio;

	/**
	 * Fecha inicio
	 */
	private Long fechaFin;

	/**
	 * Lista de solicitudes de postulacion
	 */
	private List<SolicitudPostulacionFOVISDTO> listaSolicitudesPostulacion;

	/**
	 * Lista de solicitudes de postulacion
	 */
	private List<HistoricoSolicitudNovedadFovisDTO> listaSolicitudesNovedad;

	/**
	 * Lista de solicitudes de postulacion
	 */
	private List<SolicitudLegalizacionDesembolsoDTO> listaSolicitudesLegalizacion;

	/**
	 * @return the tipoSolicitud
	 */
	public TipoSolicitudEnum getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud
	 *            the tipoSolicitud to set
	 */
	public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
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
	 * @return the estadoSolicitudlegalizacion
	 */
	public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitudlegalizacion() {
		return estadoSolicitudlegalizacion;
	}

	/**
	 * @param estadoSolicitudlegalizacion
	 *            the estadoSolicitudlegalizacion to set
	 */
	public void setEstadoSolicitudlegalizacion(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion) {
		this.estadoSolicitudlegalizacion = estadoSolicitudlegalizacion;
	}

	/**
	 * @return the estadoSolicitudPostulacion
	 */
	public EstadoSolicitudPostulacionEnum getEstadoSolicitudPostulacion() {
		return estadoSolicitudPostulacion;
	}

	/**
	 * @param estadoSolicitudPostulacion
	 *            the estadoSolicitudPostulacion to set
	 */
	public void setEstadoSolicitudPostulacion(EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion) {
		this.estadoSolicitudPostulacion = estadoSolicitudPostulacion;
	}

	/**
	 * @return the fechaExactaRadicacion
	 */
	public Long getFechaExactaRadicacion() {
		return fechaExactaRadicacion;
	}

	/**
	 * @param fechaExactaRadicacion
	 *            the fechaExactaRadicacion to set
	 */
	public void setFechaExactaRadicacion(Long fechaExactaRadicacion) {
		this.fechaExactaRadicacion = fechaExactaRadicacion;
	}

	/**
	 * @return the fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the numeroSolicitud
	 */
	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	/**
	 * @param numeroSolicitud
	 *            the numeroSolicitud to set
	 */
	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	/**
	 * @return the listaSolicitudesPostulacion
	 */
	public List<SolicitudPostulacionFOVISDTO> getListaSolicitudesPostulacion() {
		return listaSolicitudesPostulacion;
	}

	/**
	 * @param listaSolicitudesPostulacion the listaSolicitudesPostulacion to set
	 */
	public void setListaSolicitudesPostulacion(List<SolicitudPostulacionFOVISDTO> listaSolicitudesPostulacion) {
		this.listaSolicitudesPostulacion = listaSolicitudesPostulacion;
	}

	/**
	 * @return the listaSolicitudesNovedad
	 */
	public List<HistoricoSolicitudNovedadFovisDTO> getListaSolicitudesNovedad() {
		return listaSolicitudesNovedad;
	}

	/**
	 * @param listaSolicitudesNovedad the listaSolicitudesNovedad to set
	 */
	public void setListaSolicitudesNovedad(List<HistoricoSolicitudNovedadFovisDTO> listaSolicitudesNovedad) {
		this.listaSolicitudesNovedad = listaSolicitudesNovedad;
	}

	/**
	 * @return the listaSolicitudesLegalizacion
	 */
	public List<SolicitudLegalizacionDesembolsoDTO> getListaSolicitudesLegalizacion() {
		return listaSolicitudesLegalizacion;
	}

	/**
	 * @param listaSolicitudesLegalizacion the listaSolicitudesLegalizacion to set
	 */
	public void setListaSolicitudesLegalizacion(List<SolicitudLegalizacionDesembolsoDTO> listaSolicitudesLegalizacion) {
		this.listaSolicitudesLegalizacion = listaSolicitudesLegalizacion;
	}

}
