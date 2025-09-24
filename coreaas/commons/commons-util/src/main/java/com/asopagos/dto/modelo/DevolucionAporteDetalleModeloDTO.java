package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.aportes.DevolucionAporteDetalle;

/**
 * <b>Descripción: </b> DTO que representa los datos relacionados a la
 * información resultante de la gestión de un aporte incluido en una solicitud
 * de devolución <br/>
 * <b>Historia de Usuario: </b> HU-005
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
@XmlRootElement
public class DevolucionAporteDetalleModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7268010296132436888L;

	/**
	 * Identificador único, llave primaria
	 */
	private Long idAporteDevolucionDetalle;

	/**
	 * Indica si el valor del aporte obligatorio fue incluido en el monto final
	 * de la devolución
	 */
	private Boolean incluyeAporteObligatorio;

	/**
	 * Indica si el valor en mora del cotizante fue incluido en el monto final
	 * de la devolución
	 */
	private Boolean incluyeMoraCotizante;

	/**
	 * Comentarios agregados por el analista de aportes, relacionados a la
	 * condición de cumplimiento del aporte respecto a su registro histórico
	 */
	private String comentarioHistorico;

	/**
	 * Comentarios agregados por el analista de aportes, relacionados a la
	 * condición de cumplimiento del cotizante respecto a las novedades
	 * registradas para el periodo de pago del aporte mismo
	 */
	private String comentarioNovedades;

	/**
	 * Comentarios agregados por el analista de aportes, relacionados a la
	 * condición de cumplimiento del aporte respecto a su estado y evaluación
	 * vigente
	 */
	private String comentarioAportes;

	/**
	 * El usuario que realizó la gestión de devolución para este aporte
	 */
	private String usuario;

	/**
	 * Fecha y hora de gestión del aporte
	 */
	private Long fechaGestion;

	/**
	 * Devolución de aportes -> Referencia a la tabla
	 * <code>DevolucionAporte</code>
	 */
	private Long idDevolucionAporte;

	/**
	 * Movimiento monetario -> Referencia a la tabla
	 * <code>MovimientoAporte</code>
	 */
	private Long idMovimientoAporte;

	/**
	 * Método que convierte el DTO en su entidad equivalente
	 * 
	 * @return La entidad de tipo <code>DevolucionAporteDetalle</code>
	 */
	public DevolucionAporteDetalle convertToEntity() {
		DevolucionAporteDetalle devolucionAporteDetalle = new DevolucionAporteDetalle();
		devolucionAporteDetalle.setIdAporteDevolucionDetalle(this.idAporteDevolucionDetalle);
		devolucionAporteDetalle.setComentarioAportes(this.comentarioAportes);
		devolucionAporteDetalle.setComentarioNovedades(this.comentarioNovedades);
		devolucionAporteDetalle.setComentarioHistorico(this.comentarioHistorico);
		devolucionAporteDetalle.setDevolucionAporte(this.idDevolucionAporte);

		if (this.fechaGestion != null) {
			devolucionAporteDetalle.setFechaGestion(new Date(this.fechaGestion));
		}

		devolucionAporteDetalle.setIncluyeAporteObligatorio(this.incluyeAporteObligatorio);
		devolucionAporteDetalle.setIncluyeMoraCotizante(this.incluyeMoraCotizante);
		devolucionAporteDetalle.setMovimientoAporte(this.idMovimientoAporte);
		devolucionAporteDetalle.setUsuario(this.usuario);
		return devolucionAporteDetalle;
	}

	/**
	 * Método que convierte una entidad <code>DevolucionAporteDetalle</code>, en
	 * DTO
	 * 
	 * @param devolucionAporteDetalle
	 *            La entidad <code>DevolucionAporteDetalle</code>
	 */
	public void convertToDTO(DevolucionAporteDetalle devolucionAporteDetalle) {
		this.idAporteDevolucionDetalle = devolucionAporteDetalle.getIdAporteDevolucionDetalle();
		this.incluyeAporteObligatorio = devolucionAporteDetalle.getIncluyeAporteObligatorio();
		this.incluyeMoraCotizante = devolucionAporteDetalle.getIncluyeMoraCotizante();
		this.comentarioHistorico = devolucionAporteDetalle.getComentarioHistorico();
		this.comentarioNovedades = devolucionAporteDetalle.getComentarioNovedades();
		this.comentarioAportes = devolucionAporteDetalle.getComentarioAportes();
		this.usuario = devolucionAporteDetalle.getUsuario();

		if (devolucionAporteDetalle.getFechaGestion() != null) {
			this.fechaGestion = devolucionAporteDetalle.getFechaGestion().getTime();
		}

		this.idDevolucionAporte = devolucionAporteDetalle.getDevolucionAporte();
		this.idMovimientoAporte = devolucionAporteDetalle.getMovimientoAporte();
	}

	/**
	 * Obtiene el valor de idAporteDevolucionDetalle
	 * 
	 * @return El valor de idAporteDevolucionDetalle
	 */
	public Long getIdAporteDevolucionDetalle() {
		return idAporteDevolucionDetalle;
	}

	/**
	 * Establece el valor de idAporteDevolucionDetalle
	 * 
	 * @param idAporteDevolucionDetalle
	 *            El valor de idAporteDevolucionDetalle por asignar
	 */
	public void setIdAporteDevolucionDetalle(Long idAporteDevolucionDetalle) {
		this.idAporteDevolucionDetalle = idAporteDevolucionDetalle;
	}

	/**
	 * Obtiene el valor de incluyeAporteObligatorio
	 * 
	 * @return El valor de incluyeAporteObligatorio
	 */
	public Boolean getIncluyeAporteObligatorio() {
		return incluyeAporteObligatorio;
	}

	/**
	 * Establece el valor de incluyeAporteObligatorio
	 * 
	 * @param incluyeAporteObligatorio
	 *            El valor de incluyeAporteObligatorio por asignar
	 */
	public void setIncluyeAporteObligatorio(Boolean incluyeAporteObligatorio) {
		this.incluyeAporteObligatorio = incluyeAporteObligatorio;
	}

	/**
	 * Obtiene el valor de incluyeMoraCotizante
	 * 
	 * @return El valor de incluyeMoraCotizante
	 */
	public Boolean getIncluyeMoraCotizante() {
		return incluyeMoraCotizante;
	}

	/**
	 * Establece el valor de incluyeMoraCotizante
	 * 
	 * @param incluyeMoraCotizante
	 *            El valor de incluyeMoraCotizante por asignar
	 */
	public void setIncluyeMoraCotizante(Boolean incluyeMoraCotizante) {
		this.incluyeMoraCotizante = incluyeMoraCotizante;
	}

	/**
	 * Obtiene el valor de comentarioHistorico
	 * 
	 * @return El valor de comentarioHistorico
	 */
	public String getComentarioHistorico() {
		return comentarioHistorico;
	}

	/**
	 * Establece el valor de comentarioHistorico
	 * 
	 * @param comentarioHistorico
	 *            El valor de comentarioHistorico por asignar
	 */
	public void setComentarioHistorico(String comentarioHistorico) {
		this.comentarioHistorico = comentarioHistorico;
	}

	/**
	 * Obtiene el valor de comentarioNovedades
	 * 
	 * @return El valor de comentarioNovedades
	 */
	public String getComentarioNovedades() {
		return comentarioNovedades;
	}

	/**
	 * Establece el valor de comentarioNovedades
	 * 
	 * @param comentarioNovedades
	 *            El valor de comentarioNovedades por asignar
	 */
	public void setComentarioNovedades(String comentarioNovedades) {
		this.comentarioNovedades = comentarioNovedades;
	}

	/**
	 * Obtiene el valor de comentarioAportes
	 * 
	 * @return El valor de comentarioAportes
	 */
	public String getComentarioAportes() {
		return comentarioAportes;
	}

	/**
	 * Establece el valor de comentarioAportes
	 * 
	 * @param comentarioAportes
	 *            El valor de comentarioAportes por asignar
	 */
	public void setComentarioAportes(String comentarioAportes) {
		this.comentarioAportes = comentarioAportes;
	}

	/**
	 * Obtiene el valor de usuario
	 * 
	 * @return El valor de usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el valor de usuario
	 * 
	 * @param usuario
	 *            El valor de usuario por asignar
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el valor de idDevolucionAporte
	 * 
	 * @return El valor de idDevolucionAporte
	 */
	public Long getIdDevolucionAporte() {
		return idDevolucionAporte;
	}

	/**
	 * Establece el valor de idDevolucionAporte
	 * 
	 * @param idDevolucionAporte
	 *            El valor de idDevolucionAporte por asignar
	 */
	public void setIdDevolucionAporte(Long idDevolucionAporte) {
		this.idDevolucionAporte = idDevolucionAporte;
	}

	/**
	 * Obtiene el valor de idMovimientoAporte
	 * 
	 * @return El valor de idMovimientoAporte
	 */
	public Long getIdMovimientoAporte() {
		return idMovimientoAporte;
	}

	/**
	 * Establece el valor de idMovimientoAporte
	 * 
	 * @param idMovimientoAporte
	 *            El valor de idMovimientoAporte por asignar
	 */
	public void setIdMovimientoAporte(Long idMovimientoAporte) {
		this.idMovimientoAporte = idMovimientoAporte;
	}

	/**
	 * Obtiene el valor de fechaGestion
	 * 
	 * @return El valor de fechaGestion
	 */
	public Long getFechaGestion() {
		return fechaGestion;
	}

	/**
	 * Establece el valor de fechaGestion
	 * 
	 * @param fechaGestion
	 *            El valor de fechaGestion por asignar
	 */
	public void setFechaGestion(Long fechaGestion) {
		this.fechaGestion = fechaGestion;
	}
}
