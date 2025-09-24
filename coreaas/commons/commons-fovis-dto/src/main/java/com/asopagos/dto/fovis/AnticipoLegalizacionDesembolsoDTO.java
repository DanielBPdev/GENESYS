package com.asopagos.dto.fovis;

import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.FormaPagoEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene los datos de los anticipos
 * desembolsados de legalización y desembolso<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-053<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class AnticipoLegalizacionDesembolsoDTO {

	/**
	 * Identificador de la LegalizacionDesembolso
	 */
	private Long idLegalizacionDesembolso;
	/**
	 * Identificador de la solicitud de legalizacion y desembolso
	 */
	private Long idSolicitudLegalizacionDesembolso;
	/**
	 * Valor desembolsado de la solicitud.
	 */
	private BigDecimal valorDesembolsado;
	/**
	 * Numero de radicación de la solicitud de legalización y desembolso.
	 */
	private String numeroRadicacion;
	/**
	 * Fecha de radicación de la solicitud de legalización y desembolso
	 */
	private Long fechaRadicacion;

	/**
	 * Asociación de la Forma de pago
	 */
	private FormaPagoEnum formaPago;

	/**
	 * Estado de la solicitud de desembolso.
	 */
	private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;

	/**
	 * Identificador de la LegalizacionDesembolso
	 */
	private Long idSolicitudGlobal;

	/**
	 * Postulacion
	 */
	private PostulacionFOVISModeloDTO postulacionDTO;

	/**
	 * Constructor por defecto
	 */
	public AnticipoLegalizacionDesembolsoDTO() {
		super();
	}

	/**
	 * Constructor que recibe los datos a mostrar.
	 * 
	 * @param idLegalizacionDesembolso
	 * @param idSolicitudLegalizacionDesembolso
	 * @param valorDesembolsado
	 * @param numeroRadicacion
	 * @param fechaRadicacion
	 */
	public AnticipoLegalizacionDesembolsoDTO(Long idLegalizacionDesembolso, Long idSolicitudLegalizacionDesembolso,
			BigDecimal valorDesembolsado, String numeroRadicacion, Date fechaRadicacion, FormaPagoEnum formaPago) {
		this.setIdLegalizacionDesembolso(idLegalizacionDesembolso);
		this.setIdSolicitudLegalizacionDesembolso(idSolicitudLegalizacionDesembolso);
		this.setValorDesembolsado(valorDesembolsado);
		this.setFechaRadicacion(fechaRadicacion.getTime());
		this.setNumeroRadicacion(numeroRadicacion);
		this.setFormaPago(formaPago);
	}

	/**
	 * Constructor que recibe los datos a mostrar.
	 * 
	 * @param idLegalizacionDesembolso
	 *            identificador de la legalizacion y desembolso
	 * @param idSolicitudLegalizacionDesembolso,
	 *            identificador de la solicitud de legalizacion
	 * @param valorDesembolsado,
	 *            valor desembolsado en la solicitud de legalizacion
	 * @param numeroRadicacion,
	 *            numero de radicado de la solicitud de legalizacion
	 * @param fechaRadicacion,
	 *            fecha de radicacion de la solicitud de legalizacion
	 * @param formaPago,
	 *            forma de pago usada
	 * @param estado,
	 *            estado de la solicitud de legalizacin
	 * @param postulacionFOVIS,
	 *            postulacion asociada al proceso
	 * @param idSolicitudGlobal,
	 *            solicitud global
	 */
	public AnticipoLegalizacionDesembolsoDTO(Long idLegalizacionDesembolso, Long idSolicitudLegalizacionDesembolso,
			BigDecimal valorDesembolsado, String numeroRadicacion, Date fechaRadicacion, FormaPagoEnum formaPago,
			EstadoSolicitudLegalizacionDesembolsoEnum estado, PostulacionFOVIS postulacionFOVIS,
			Long idSolicitudGlobal) {
		this.setIdLegalizacionDesembolso(idLegalizacionDesembolso);
		this.setIdSolicitudLegalizacionDesembolso(idSolicitudLegalizacionDesembolso);
		this.setValorDesembolsado(valorDesembolsado);
		this.setFechaRadicacion(fechaRadicacion.getTime());
		this.setNumeroRadicacion(numeroRadicacion);
		this.setFormaPago(formaPago);
		this.setEstadoSolicitud(estado);
		this.setIdSolicitudGlobal(idSolicitudGlobal);
		if (postulacionFOVIS != null) {
			PostulacionFOVISModeloDTO pos = new PostulacionFOVISModeloDTO();
			pos.convertToDTO(postulacionFOVIS);
			this.setPostulacionDTO(pos);
		}
	}

	/**
	 * @return the idLegalizacionDesembolso
	 */
	public Long getIdLegalizacionDesembolso() {
		return idLegalizacionDesembolso;
	}

	/**
	 * @param idLegalizacionDesembolso
	 *            the idLegalizacionDesembolso to set
	 */
	public void setIdLegalizacionDesembolso(Long idLegalizacionDesembolso) {
		this.idLegalizacionDesembolso = idLegalizacionDesembolso;
	}

	/**
	 * @return the idSolicitudLegalizacionDesembolso
	 */
	public Long getIdSolicitudLegalizacionDesembolso() {
		return idSolicitudLegalizacionDesembolso;
	}

	/**
	 * @param idSolicitudLegalizacionDesembolso
	 *            the idSolicitudLegalizacionDesembolso to set
	 */
	public void setIdSolicitudLegalizacionDesembolso(Long idSolicitudLegalizacionDesembolso) {
		this.idSolicitudLegalizacionDesembolso = idSolicitudLegalizacionDesembolso;
	}

	/**
	 * @return the valorDesembolsado
	 */
	public BigDecimal getValorDesembolsado() {
		return valorDesembolsado;
	}

	/**
	 * @param valorDesembolsado
	 *            the valorDesembolsado to set
	 */
	public void setValorDesembolsado(BigDecimal valorDesembolsado) {
		this.valorDesembolsado = valorDesembolsado;
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
	 * @return the formaPago
	 */
	public FormaPagoEnum getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago
	 *            the formaPago to set
	 */
	public void setFormaPago(FormaPagoEnum formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud
	 *            the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * @return the postulacionDTO
	 */
	public PostulacionFOVISModeloDTO getPostulacionDTO() {
		return postulacionDTO;
	}

	/**
	 * @param postulacionDTO
	 *            the postulacionDTO to set
	 */
	public void setPostulacionDTO(PostulacionFOVISModeloDTO postulacionDTO) {
		this.postulacionDTO = postulacionDTO;
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

}
