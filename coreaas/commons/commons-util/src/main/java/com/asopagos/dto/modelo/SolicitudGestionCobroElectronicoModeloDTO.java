package com.asopagos.dto.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroElectronico;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;

/**
 * <b>Descripción: </b> Clase que representa los datos de una solicitud de
 * gestión de cobro electrónico <br/>
 * <b>Historia de Usuario: </b>
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class SolicitudGestionCobroElectronicoModeloDTO extends SolicitudModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2117486390536445375L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idSolicitudGestionCobroElectronico;

	/**
	 * Tipo de acción de cobro
	 */
	private TipoAccionCobroEnum tipoAccionCobro;

	/**
	 * Estado de la solicitud
	 */
	private EstadoSolicitudGestionCobroEnum estado;

	/**
	 * Identificador del registro de Cartera
	 */
	private Long idCartera;

	/**
	 * Método encargado de convertir el DTO a entidad
	 * 
	 * @return entidad La entidad equivalente
	 */
	public SolicitudGestionCobroElectronico convertToEntity() {
		SolicitudGestionCobroElectronico solicitudGestionCobroElectronico = new SolicitudGestionCobroElectronico();
		solicitudGestionCobroElectronico.setId(this.getIdSolicitudGestionCobroElectronico());
		solicitudGestionCobroElectronico.setEstado(this.getEstado());
		solicitudGestionCobroElectronico.setTipoAccionCobro(this.getTipoAccionCobro());
		solicitudGestionCobroElectronico.setIdCartera(this.getIdCartera());
		Solicitud solicitudGlobal = super.convertToSolicitudEntity();
		solicitudGestionCobroElectronico.setSolicitudGlobal(solicitudGlobal);
		return solicitudGestionCobroElectronico;
	}

	/**
	 * Método encargado de convertir una entidad
	 * <code>SolicitudGestionCobroElectronico</code> al DTO
	 * 
	 * @param solicitudGestionCobroElectronico
	 *            La entidad a convertir
	 */
	public void convertToDTO(SolicitudGestionCobroElectronico solicitudGestionCobroElectronico) {
		if (solicitudGestionCobroElectronico.getSolicitudGlobal() != null) {
			super.convertToDTO(solicitudGestionCobroElectronico.getSolicitudGlobal());
		}

		this.setIdSolicitudGestionCobroElectronico(solicitudGestionCobroElectronico.getId());
		this.setEstado(solicitudGestionCobroElectronico.getEstado());
		this.setTipoAccionCobro(solicitudGestionCobroElectronico.getTipoAccionCobro());
		this.setIdCartera(solicitudGestionCobroElectronico.getIdCartera());
	}

	/**
	 * Obtiene el valor de idSolicitudGestionCobroElectronico
	 * 
	 * @return El valor de idSolicitudGestionCobroElectronico
	 */
	public Long getIdSolicitudGestionCobroElectronico() {
		return idSolicitudGestionCobroElectronico;
	}

	/**
	 * Establece el valor de idSolicitudGestionCobroElectronico
	 * 
	 * @param idSolicitudGestionCobroElectronico
	 *            El valor de idSolicitudGestionCobroElectronico por asignar
	 */
	public void setIdSolicitudGestionCobroElectronico(Long idSolicitudGestionCobroElectronico) {
		this.idSolicitudGestionCobroElectronico = idSolicitudGestionCobroElectronico;
	}

	/**
	 * Obtiene el valor de tipoAccionCobro
	 * 
	 * @return El valor de tipoAccionCobro
	 */
	public TipoAccionCobroEnum getTipoAccionCobro() {
		return tipoAccionCobro;
	}

	/**
	 * Establece el valor de tipoAccionCobro
	 * 
	 * @param tipoAccionCobro
	 *            El valor de tipoAccionCobro por asignar
	 */
	public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
		this.tipoAccionCobro = tipoAccionCobro;
	}

	/**
	 * Obtiene el valor de estado
	 * 
	 * @return El valor de estado
	 */
	public EstadoSolicitudGestionCobroEnum getEstado() {
		return estado;
	}

	/**
	 * Establece el valor de estado
	 * 
	 * @param estado
	 *            El valor de estado por asignar
	 */
	public void setEstado(EstadoSolicitudGestionCobroEnum estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene el valor de idCartera
	 * 
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * Establece el valor de idCartera
	 * 
	 * @param idCartera
	 *            El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}
}
