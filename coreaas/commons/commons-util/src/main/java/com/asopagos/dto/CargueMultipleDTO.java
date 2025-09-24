package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class CargueMultipleDTO implements Serializable{

	/**
	 * Código identificador de la solicitud de afiliación de la persona
	 */
	private Long idSolicitudAfiliacionMultiple;

	/**
	 * Id que identifica la carga múltiple para el empleador
	 */
	private Long codigoCargueMultiple;

	/**
	 * Id que identifica a la sucursal empleador asociada al rol afiliado
	 */
	private Long idSucursalEmpleador;

	/**
	 * Id que identifica al empleador asociada al rol afiliado
	 */
	private Long idEmpleador;

	/**
	 * Descripción del sujeto trámite de tipo afiliado
	 */
	private TipoAfiliadoEnum tipoSolicitante;

	/**
	 * Descripción del tipo de transacción dentro del proceso de la solicitud
	 */
	private ClasificacionEnum clasificacion;

	/**
	 * Descripción del tipo de transacción dentro del proceso de la solicitud
	 */
	private TipoTransaccionEnum tipoTransaccion;

	/**
	 * Proceso al que pertenece la carga
	 */
	private ProcesoEnum proceso;

	/**
	 * Estado del proceso de carga múltiple de la solicitud de afiliación
	 */
	private EstadoCargaMultipleEnum estado;

	/**
	 * Fecha en la que se realiza la carga múltiple
	 */
	private Date fechaCarga;

	/**
	 * Codigo del identificador del archivo en el ECM 
	 */
	private String codigoIdentificacionECM;

	/**
	 * Numero de días en que se genera el temporizador
	 */
	private Long numeroDiaTemporizador;

	/**
	 * @return the idSolicitudAfiliacionMultiple
	 */
	public Long getIdSolicitudAfiliacionMultiple() {
		return idSolicitudAfiliacionMultiple;
	}

	/**
	 * @param idSolicitudAfiliacionMultiple
	 *            the idSolicitudAfiliacionMultiple to set
	 */
	public void setIdSolicitudAfiliacionMultiple(Long idSolicitudAfiliacionMultiple) {
		this.idSolicitudAfiliacionMultiple = idSolicitudAfiliacionMultiple;
	}

	/**
	 * @return the codigoCargueMultiple
	 */
	public Long getCodigoCargueMultiple() {
		return codigoCargueMultiple;
	}

	/**
	 * @param codigoCargueMultiple
	 *            the codigoCargueMultiple to set
	 */
	public void setCodigoCargueMultiple(Long codigoCargueMultiple) {
		this.codigoCargueMultiple = codigoCargueMultiple;
	}

	/**
	 * @return the tipoSolicitante
	 */
	public TipoAfiliadoEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * @param tipoSolicitante
	 *            the tipoSolicitante to set
	 */
	public void setTipoSolicitante(TipoAfiliadoEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * @return the clasificacion
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion
	 *            the clasificacion to set
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/**
	 * @param proceso
	 *            the proceso to set
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return the estado
	 */
	public EstadoCargaMultipleEnum getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(EstadoCargaMultipleEnum estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaCarga
	 */
	public Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param fechaCarga
	 *            the fechaCarga to set
	 */
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	/**
	 * @return the codigoIdentificacionECM
	 */
	public String getCodigoIdentificacionECM() {
		return codigoIdentificacionECM;
	}

	/**
	 * @param codigoIdentificacionECM
	 *            the codigoIdentificacionECM to set
	 */
	public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
		this.codigoIdentificacionECM = codigoIdentificacionECM;
	}

	/**
	 * @return the idSucursalEmpleador
	 */
	public Long getIdSucursalEmpleador() {
		return idSucursalEmpleador;
	}

	/**
	 * @param idSucursalEmpleador
	 *            the idSucursalEmpleador to set
	 */
	public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
		this.idSucursalEmpleador = idSucursalEmpleador;
	}

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador
	 *            the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * @return the numeroDiaTemporizador
	 */
	public Long getNumeroDiaTemporizador() {
		return numeroDiaTemporizador;
	}

	/**
	 * @param numeroDiaTemporizador
	 *            the numeroDiaTemporizador to set
	 */
	public void setNumeroDiaTemporizador(Long numeroDiaTemporizador) {
		this.numeroDiaTemporizador = numeroDiaTemporizador;
	}

}
