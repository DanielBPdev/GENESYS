package com.asopagos.solicitud.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> Entidad que representa los documentos de soporte para una
 * solicitud que fue desistida o cerrada <b>Historia de Usuario: </b>Transversal 114
 * 
 * @author Jerson Zambrano <jzambrano@heinsohn.com.co>
 */

@XmlRootElement
public class CambiarEstadoSolicitudFinGestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSolicitudGlobal;

	private String numeroRadicado;

	private String estado;

	private TipoTransaccionEnum tipoTx;
	
	private String observacion;
	
	private Long idInstanciaProceso;

	private List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds;

	
	
	public CambiarEstadoSolicitudFinGestionDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado
	 *            the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the tipoTx
	 */
	public TipoTransaccionEnum getTipoTx() {
		return tipoTx;
	}

	/**
	 * @param tipoTx
	 *            the tipoTx to set
	 */
	public void setTipoTx(TipoTransaccionEnum tipoTx) {
		this.tipoTx = tipoTx;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal
	 *            the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

	/**
	 * @return the administracionEstadoSolicituds
	 */
	public List<DocumentoAdministracionEstadoSolicitudDTO> getAdministracionEstadoSolicituds() {
		return administracionEstadoSolicituds;
	}

	/**
	 * @param administracionEstadoSolicituds
	 *            the administracionEstadoSolicituds to set
	 */
	public void setAdministracionEstadoSolicituds(
			List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds) {
		this.administracionEstadoSolicituds = administracionEstadoSolicituds;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the idInstanciaProceso
	 */
	public Long getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

}
