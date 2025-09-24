package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemNovedadModeloDTO;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.enumeraciones.aportes.PilaEstadoTransitorioEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información de la bandeja de gestión
 * transitoria
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 */

public class DatosBandejaTransitoriaDTO implements Serializable {
	private static final long serialVersionUID = -1226895260691525855L;

	/**
	 * Código identificador de llave primaria
	 */
	private Long id;

	/**
	 * Referencia al id de la planilla (id del archivo I)
	 */
	private Long pilaIndicePlanilla;

	/**
	 * Descripción de la acción del proceso de formalización de datos
	 */
	private PilaAccionTransitorioEnum accion;

	/**
	 * Descripción del estado del proceso de formalización de datos
	 */
	private PilaEstadoTransitorioEnum estado;

	/**
	 * Fecha de procesamiento del aporte (Sistema al momento de relacionar o
	 * registrar)
	 */
	private Date fecha;

	/**
	 * datos de la planilla asociada
	 */
	private IndicePlanillaModeloDTO indicePlanillaModeloDTO;
	
	/**
	 * lista de datos de novedades fallidas
	 */
	private List<TemNovedadModeloDTO> novedades;
	
	public DatosBandejaTransitoriaDTO() {
		super();
	}

	/**Constructor
	 * @param pilaEstadoTransitorio
	 * @param indicePlanillaModeloDTO
	 */
	public DatosBandejaTransitoriaDTO(PilaEstadoTransitorio pilaEstadoTransitorio,
			IndicePlanillaModeloDTO indicePlanillaModeloDTO) {
		this.id = pilaEstadoTransitorio.getId();
		this.pilaIndicePlanilla = pilaEstadoTransitorio.getPilaIndicePlanilla();
		this.accion = pilaEstadoTransitorio.getAccion();
		this.estado = pilaEstadoTransitorio.getEstado();
		this.fecha = pilaEstadoTransitorio.getFecha();
		this.indicePlanillaModeloDTO = indicePlanillaModeloDTO;
	}

	/**
	 * @return the indicePlanillaModeloDTO
	 */
	public IndicePlanillaModeloDTO getIndicePlanillaModeloDTO() {
		return indicePlanillaModeloDTO;
	}

	/**
	 * @param indicePlanillaModeloDTO the indicePlanillaModeloDTO to set
	 */
	public void setIndicePlanillaModeloDTO(IndicePlanillaModeloDTO indicePlanillaModeloDTO) {
		this.indicePlanillaModeloDTO = indicePlanillaModeloDTO;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the pilaIndicePlanilla
	 */
	public Long getPilaIndicePlanilla() {
		return pilaIndicePlanilla;
	}

	/**
	 * @param pilaIndicePlanilla the pilaIndicePlanilla to set
	 */
	public void setPilaIndicePlanilla(Long pilaIndicePlanilla) {
		this.pilaIndicePlanilla = pilaIndicePlanilla;
	}

	/**
	 * @return the accion
	 */
	public PilaAccionTransitorioEnum getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(PilaAccionTransitorioEnum accion) {
		this.accion = accion;
	}

	/**
	 * @return the estado
	 */
	public PilaEstadoTransitorioEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(PilaEstadoTransitorioEnum estado) {
		this.estado = estado;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the novedades
	 */
	public List<TemNovedadModeloDTO> getNovedades() {
		return novedades;
	}

	/**
	 * @param novedades the novedades to set
	 */
	public void setNovedades(List<TemNovedadModeloDTO> novedades) {
		this.novedades = novedades;
	}

}