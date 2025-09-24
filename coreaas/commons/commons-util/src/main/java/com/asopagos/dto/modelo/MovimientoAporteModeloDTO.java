package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;

/**
 * DTO que representa el modelo del movimiento de aporte
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@XmlRootElement
public class MovimientoAporteModeloDTO implements Serializable{

	/**
     * serial version
     */
    private static final long serialVersionUID = 1033295606762064722L;
    /**
	 * Identificador del movimiento de aporte
	 */
	private Long idMovimientoAporte;
	/**
	 * Tipo de ajuste del movimiento.
	 */
	private TipoAjusteMovimientoAporteEnum tipoAjuste;
	/**
	 * Tipo de movimiento, puede ser recaudos, devoluciones o correcciones.
	 */
	private TipoMovimientoRecaudoAporteEnum tipoMovimiento;
	/**
	 * Estado del movimiento con respecto al aporte, (VIGENTE, CORREGIDO,
	 * ANULADO)
	 */
	private EstadoAporteEnum estado;
	/**
	 * Movimiento del aporte.
	 */
	private BigDecimal aporte;
	/**
	 * Movimiento del inter�s.
	 */
	private BigDecimal interes;
	/**
	 * Fecha en la que se actualiz� el estado.
	 */
	private Date fechaActualizacionEstado;
	/**
	 * Fecha de creaci�n del movimiento.
	 */
	private Date fechaCreacion;
	/**
	 * Id del aporte detallado
	 */
	private Long idAporteDetallado;
	/**
	 * Id del aporte general
	 */
	private Long idAporteGeneral;

	/**
	 * Constructor por defecto
	 */
	public MovimientoAporteModeloDTO() {
		super();
	}

	/**
	 * Constructor utilizado para crear un nuevo movimiento de aportes
	 * 
	 * @param tipoAjuste
	 *            Tipo de ajuste realizado
	 * @param tipoMovimiento
	 *            Proceso de negocio / Tipo de movimiento realizado al aporte
	 * @param estado
	 *            Estado del aporte
	 * @param aporte
	 *            Monto del aporte
	 * @param interes
	 *            Monto del interés
	 * @param fechaActualizacionEstado
	 *            Fecha de actualización del estado del aporte
	 * @param fechaCreacion
	 *            Fecha de creación del registro
	 * @param idAporteDetallado
	 *            Identificador único del aporte detallado
	 * @param idAporteGeneral
	 *            Identificador único del aporte general
	 */
	public MovimientoAporteModeloDTO(TipoAjusteMovimientoAporteEnum tipoAjuste, TipoMovimientoRecaudoAporteEnum tipoMovimiento, EstadoAporteEnum estado, BigDecimal aporte, BigDecimal interes, Date fechaActualizacionEstado, Date fechaCreacion, Long idAporteDetallado, Long idAporteGeneral) {
		super();
		this.tipoAjuste = tipoAjuste;
		this.tipoMovimiento = tipoMovimiento;
		this.estado = estado;
		this.aporte = aporte;
		this.interes = interes;
		this.fechaActualizacionEstado = fechaActualizacionEstado;
		this.fechaCreacion = fechaCreacion;
		this.idAporteDetallado = idAporteDetallado;
		this.idAporteGeneral = idAporteGeneral;
	}

	/**
	 * Método que convierte la entidad a MovimientoAportante.
	 * 
	 * @param informacionFaltanteAportante
	 *            representada en forma de entidad.
	 */
	public void convertToDTO(MovimientoAporte movimientoAporte) {
		this.setIdMovimientoAporte(movimientoAporte.getIdMovimientoAporte());
		this.setAporte(movimientoAporte.getValorAporte());
		this.setEstado(movimientoAporte.getEstadoAporte());
		if (movimientoAporte.getFechaActualizacionEstado() != null) {
			this.setFechaActualizacionEstado(movimientoAporte.getFechaActualizacionEstado());
		}
		if (movimientoAporte.getFechaCreacion() != null) {
			this.setFechaCreacion(movimientoAporte.getFechaCreacion());
		}
		this.setIdAporteDetallado(movimientoAporte.getIdAporteDetallado());
		this.setIdAporteGeneral(movimientoAporte.getIdAporteGeneral());
		this.setInteres(movimientoAporte.getValorInteres());
		this.setTipoAjuste(movimientoAporte.getTipoAjuste());
		this.setTipoMovimiento(movimientoAporte.getTipoMovimiento());
	}

	/**
	 * Método que convierte de DTO a una Entidad.
	 * 
	 * @return MovimientoAporte convertida.
	 */
	public MovimientoAporte convertToEntity() {

		MovimientoAporte movimientoAporte = new MovimientoAporte();
		movimientoAporte.setIdMovimientoAporte(this.getIdMovimientoAporte());
		movimientoAporte.setValorAporte(this.getAporte());
		movimientoAporte.setEstadoAporte(this.getEstado());
		if (this.getFechaActualizacionEstado() != null) {
			movimientoAporte.setFechaActualizacionEstado(new Date(this.getFechaActualizacionEstado().getTime()));
		}
		if (this.getFechaCreacion() != null) {
			movimientoAporte.setFechaCreacion(new Date(this.getFechaCreacion().getTime()));
		}
		movimientoAporte.setIdAporteDetallado(this.getIdAporteDetallado());
		movimientoAporte.setIdAporteGeneral(this.getIdAporteGeneral());
		movimientoAporte.setValorInteres(this.getInteres());
		movimientoAporte.setTipoAjuste(this.getTipoAjuste());
		movimientoAporte.setTipoMovimiento(this.getTipoMovimiento());
		return movimientoAporte;
	}

	/**
	 * Copia los datos del DTO a la Entidad.
	 * 
	 * @param informacionFaltanteAportante
	 *            previamente consultada.
	 */
	public MovimientoAporte copyDTOToEntity(MovimientoAporte movimientoAporte) {
		if (this.getIdMovimientoAporte() != null) {
			movimientoAporte.setIdMovimientoAporte(this.getIdMovimientoAporte());
		}
		if (this.getAporte() != null) {
			movimientoAporte.setValorAporte(this.getAporte());
		}
		if (this.getEstado() != null) {
			movimientoAporte.setEstadoAporte(this.getEstado());
		}
		if (this.getFechaActualizacionEstado() != null) {
			movimientoAporte.setFechaActualizacionEstado(new Date(this.getFechaActualizacionEstado().getTime()));
		}
		if (this.getFechaCreacion() != null) {
			movimientoAporte.setFechaCreacion(new Date(this.getFechaCreacion().getTime()));
		}
		if (this.getIdAporteDetallado() != null) {
			movimientoAporte.setIdAporteDetallado(this.getIdAporteDetallado());
		}
		if (this.getInteres() != null) {
			movimientoAporte.setValorInteres(this.getInteres());
		}
		if (this.getTipoAjuste() != null) {
			movimientoAporte.setTipoAjuste(this.getTipoAjuste());
		}
		if (this.getTipoMovimiento() != null) {
			movimientoAporte.setTipoMovimiento(this.getTipoMovimiento());
		}
		return movimientoAporte;
	}

	/**
	 * @return the tipoAjuste
	 */
	public TipoAjusteMovimientoAporteEnum getTipoAjuste() {
		return tipoAjuste;
	}

	/**
	 * @param tipoAjuste
	 *            the tipoAjuste to set
	 */
	public void setTipoAjuste(TipoAjusteMovimientoAporteEnum tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

	/**
	 * @return the tipoMovimiento
	 */
	public TipoMovimientoRecaudoAporteEnum getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento
	 *            the tipoMovimiento to set
	 */
	public void setTipoMovimiento(TipoMovimientoRecaudoAporteEnum tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return the estado
	 */
	public EstadoAporteEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(EstadoAporteEnum estado) {
		this.estado = estado;
	}

	/**
	 * @return the aporte
	 */
	public BigDecimal getAporte() {
		return aporte;
	}

	/**
	 * @param aporte
	 *            the aporte to set
	 */
	public void setAporte(BigDecimal aporte) {
		this.aporte = aporte;
	}

	/**
	 * @return the interes
	 */
	public BigDecimal getInteres() {
		return interes;
	}

	/**
	 * @param interes
	 *            the interes to set
	 */
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	/**
	 * @return the fechaActualizacionEstado
	 */
	public Date getFechaActualizacionEstado() {
		return fechaActualizacionEstado;
	}

	/**
	 * @param fechaActualizacionEstado
	 *            the fechaActualizacionEstado to set
	 */
	public void setFechaActualizacionEstado(Date fechaActualizacionEstado) {
		this.fechaActualizacionEstado = fechaActualizacionEstado;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the idAporteDetallado
	 */
	public Long getIdAporteDetallado() {
		return idAporteDetallado;
	}

	/**
	 * @param idAporteDetallado
	 *            the idAporteDetallado to set
	 */
	public void setIdAporteDetallado(Long idAporteDetallado) {
		this.idAporteDetallado = idAporteDetallado;
	}

	/**
	 * @return the idMovimientoAporte
	 */
	public Long getIdMovimientoAporte() {
		return idMovimientoAporte;
	}

	/**
	 * @param idMovimientoAporte
	 *            the idMovimientoAporte to set
	 */
	public void setIdMovimientoAporte(Long idMovimientoAporte) {
		this.idMovimientoAporte = idMovimientoAporte;
	}

	/**Obtiene el valor de idAporteGeneral
	 * @return El valor de idAporteGeneral
	 */
	public Long getIdAporteGeneral() {
		return idAporteGeneral;
	}

	/** Establece el valor de idAporteGeneral
	 * @param idAporteGeneral El valor de idAporteGeneral por asignar
	 */
	public void setIdAporteGeneral(Long idAporteGeneral) {
		this.idAporteGeneral = idAporteGeneral;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovimientoAporteModeloDTO [idMovimientoAporte=");
		builder.append(idMovimientoAporte);
		builder.append(", aporte=");
		builder.append(aporte);
		builder.append(", interes=");
		builder.append(interes);
		builder.append(", fechaActualizacionEstado=");
		builder.append(fechaActualizacionEstado);
		builder.append(", fechaCreacion=");
		builder.append(fechaCreacion);
		builder.append(", idAporteDetallado=");
		builder.append(idAporteDetallado);
		builder.append(", idAporteGeneral=");
		builder.append(idAporteGeneral);
		builder.append("]");
		return builder.toString();
	}
}
