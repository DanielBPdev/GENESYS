package com.asopagos.dto.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.aportes.Correccion;

/**
 * <b>Descripción: </b> DTO que representa los datos de una corrección de
 * aportes <br/>
 * <b>Historia de Usuario: </b> HU-105
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class CorreccionModeloDTO  implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6187929981479408878L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idCorreccion;

	/**
	 * Identificador del aporte detallado que se corrigió. Referencia a la tabla
	 * <code>AporteDetallado</code>
	 */
	private Long idAporteDetalladoCorregido;

	/**
	 * Identificador del aporte nuevo, el que se crea por corrección de un
	 * cotizante. Referencia a la tabla <code>AporteGeneral</code>
	 */
	private Long idAporteGeneralNuevo;

	/**
	 * Identificador de la solicitud de corrección. Referencia a la tabla
	 * <code>SolicitudCorreccionAporte</code>
	 */
	private Long idSolicitudCorreccionAporte;

	/**
	 * Método que convierte una entidad <code>Correccion</code> en DTO
	 * 
	 * @param correccion
	 *            La entidad a convertir
	 */
	public void convertToDTO(Correccion correccion) {
		this.idCorreccion = correccion.getIdCorreccion();
		this.idAporteGeneralNuevo = correccion.getIdAporteGeneralNuevo();
		this.idSolicitudCorreccionAporte = correccion.getIdSolicitudCorreccionAporte();
		this.idAporteDetalladoCorregido = correccion.getIdAporteDetalladoCorregido();
	}

	/**
	 * Método que convierte el DTO en una entidad <code>Correccion</code>
	 * 
	 * @return Una entidad <code>Correccion</code> con los datos equivalentes
	 */
	public Correccion convertToEntity() {
		Correccion correccion = new Correccion();
		correccion.setIdCorreccion(this.idCorreccion);
		correccion.setIdAporteGeneralNuevo(this.idAporteGeneralNuevo);
		correccion.setIdSolicitudCorreccionAporte(this.idSolicitudCorreccionAporte);
		correccion.setIdAporteDetalladoCorregido(this.idAporteDetalladoCorregido);
		return correccion;
	}

	/**
	 * Obtiene el valor de idCorreccion
	 * 
	 * @return El valor de idCorreccion
	 */
	public Long getIdCorreccion() {
		return idCorreccion;
	}

	/**
	 * Establece el valor de idCorreccion
	 * 
	 * @param idCorreccion
	 *            El valor de idCorreccion por asignar
	 */
	public void setIdCorreccion(Long idCorreccion) {
		this.idCorreccion = idCorreccion;
	}

	/**
	 * Obtiene el valor de idAporteDetalladoCorregido
	 * 
	 * @return El valor de idAporteDetalladoCorregido
	 */
	public Long getIdAporteDetalladoCorregido() {
		return idAporteDetalladoCorregido;
	}

	/**
	 * Establece el valor de idAporteDetalladoCorregido
	 * 
	 * @param idAporteDetalladoCorregido
	 *            El valor de idAporteDetalladoCorregido por asignar
	 */
	public void setIdAporteDetalladoCorregido(Long idAporteDetalladoCorregido) {
		this.idAporteDetalladoCorregido = idAporteDetalladoCorregido;
	}

	/**
	 * Obtiene el valor de idAporteGeneralNuevo
	 * 
	 * @return El valor de idAporteGeneralNuevo
	 */
	public Long getIdAporteGeneralNuevo() {
		return idAporteGeneralNuevo;
	}

	/**
	 * Establece el valor de idAporteGeneralNuevo
	 * 
	 * @param idAporteGeneralNuevo
	 *            El valor de idAporteGeneralNuevo por asignar
	 */
	public void setIdAporteGeneralNuevo(Long idAporteGeneralNuevo) {
		this.idAporteGeneralNuevo = idAporteGeneralNuevo;
	}

	/**
	 * Obtiene el valor de idSolicitudCorreccionAporte
	 * 
	 * @return El valor de idSolicitudCorreccionAporte
	 */
	public Long getIdSolicitudCorreccionAporte() {
		return idSolicitudCorreccionAporte;
	}

	/**
	 * Establece el valor de idSolicitudCorreccionAporte
	 * 
	 * @param idSolicitudCorreccionAporte
	 *            El valor de idSolicitudCorreccionAporte por asignar
	 */
	public void setIdSolicitudCorreccionAporte(Long idSolicitudCorreccionAporte) {
		this.idSolicitudCorreccionAporte = idSolicitudCorreccionAporte;
	}
}