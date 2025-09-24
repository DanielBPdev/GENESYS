package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.cartera.CarteraAgrupadora;
import com.asopagos.entidades.ccf.cartera.CicloAportante;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.SubTipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;

/**
 * <b>Descripción: </b> Clase que los datos de un aportante que se encuentra en
 * una gestión manual<br/>
 * <b>Historia de Usuario: HU-TRA-236 Panel de Control Gestión de Cartera -
 * Supervisor</b>
 * 
 * @author <a href="mailto:atoro@heinsohn.com.co">Angélica Toro Murillo</a>
 */
@XmlRootElement
public class AportanteGestionManualDTO implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3261733278365368438L;
	/**
	 * Tipo de identificaci[on del aportante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	/**
	 * Número de identificación del aportante.
	 */
	private String numeroIdentificacion;
	/**
	 * Nombre completo del aportante.
	 */
	private String nombreCompleto;
	/**
	 * Tipo de solicitante.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	/**
	 * Número de operación (número de radicación de la solicitud.)
	 */
	private String numeroOperacion;
	/**
	 * Analista que tiene la tarea o la gestionó.
	 */
	private String analista;
	/**
	 * Línea de cobro asociada.
	 */
	private TipoLineaCobroEnum lineaCobro;
	/**
	 * Estado de afiliación del aportante..
	 */
	private EstadoAfiliadoEnum estadoAfiliacion;
	/**
	 * Estado en cartera.
	 */
	private EstadoCarteraEnum estadoCartera;
	/**
	 * Estado de la solicitud.
	 */
	private EstadoFiscalizacionEnum estadoSolicitud;
	/**
	 * Tipo de acción de cobro.
	 */
	private TipoAccionCobroEnum accionCobro;
	/**
	 * Tipo de deuda.
	 */
	private TipoDeudaEnum tipoDeuda;
	/**
	 * Monto de la deuda.
	 */
	private BigDecimal montoDeuda;

	/**
	 * Fecha de ingreso a la deuda antigua.
	 */
	private Long fechaIngreso;
	
	/**
	 * Identificador de cartera
	 */
	private Long idCartera;

	/**
	 * Tipo de deuda.
	 */
	private SubTipoDeudaEnum deuda;

	/**
	 * Número de radicado
	 */
	private String numeroRadicado;

	/**
	 * Estado de gestión de cobro manual
	 */
	private EstadoFiscalizacionEnum estadoGestion;

	/**
	 * Método constructor.
	 */
	public AportanteGestionManualDTO() {
	}

	/**
	 * Constructor para la cartera y persona.
	 * 
	 * @param cartera
	 *            entidad de la cartera
	 * @param persona
	 *            aportante asociada a la cartera.
	 */
	public AportanteGestionManualDTO(Cartera cartera, Persona persona) {
		this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
		this.setTipoIdentificacion(persona.getTipoIdentificacion());
		this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
		this.setTipoSolicitante(cartera.getTipoSolicitante());
		this.setEstadoCartera(cartera.getEstadoCartera());
		this.setMontoDeuda(cartera.getDeudaPresunta());
		this.setTipoDeuda(cartera.getTipoDeuda());
		this.setLineaCobro(cartera.getTipoLineaCobro());
		this.setAccionCobro(cartera.getTipoAccionCobro());
		/*
		 * se asume que siempre es presunta, las historias no hablan de como
		 * calcular deuda real.
		 */
		this.setDeuda(SubTipoDeudaEnum.DEUDA_PRESUNTA);
	}

	/**
	 * Constructor de cartera, persona y solicitud.
	 * 
	 * @param cartera
	 * @param persona
	 * @param solicitudManual
	 */
	public AportanteGestionManualDTO(CicloAportante cicloAportante, Persona persona, SolicitudGestionCobroManual solicitudManual) {
		this.setAnalista(solicitudManual.getSolicitudGlobal().getDestinatario());
		this.setNumeroOperacion(solicitudManual.getSolicitudGlobal().getNumeroRadicacion());
		this.setTipoSolicitante(cicloAportante.getTipoSolicitanteMovimientoAporteEnum());
		this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
		this.setTipoIdentificacion(persona.getTipoIdentificacion());
		this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
		this.setLineaCobro(solicitudManual.getLineaCobro());
		if (TipoLineaCobroEnum.LC2.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC3.equals(solicitudManual.getLineaCobro())) {
			this.setEstadoCartera(EstadoCarteraEnum.AL_DIA);
		} else if (TipoLineaCobroEnum.LC1.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC4.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC5.equals(solicitudManual.getLineaCobro())) {
			this.setEstadoCartera(EstadoCarteraEnum.MOROSO);
		}
		this.setEstadoSolicitud(solicitudManual.getEstadoSolicitud());
	}

	/**
	 * Constructor
	 */
	public AportanteGestionManualDTO(CicloAportante cicloAportante, Persona persona, SolicitudGestionCobroManual solicitudManual, CarteraAgrupadora carteraAgrupadora, Cartera cartera) {
		this.setAnalista(solicitudManual.getSolicitudGlobal().getDestinatario());
		this.setNumeroOperacion(solicitudManual.getSolicitudGlobal().getNumeroRadicacion());
		this.setTipoSolicitante(cicloAportante.getTipoSolicitanteMovimientoAporteEnum());
		this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
		this.setTipoIdentificacion(persona.getTipoIdentificacion());
		this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
		this.setLineaCobro(solicitudManual.getLineaCobro());
		this.setIdCartera(cartera.getIdCartera());
		this.setNumeroOperacion(carteraAgrupadora.getNumeroOperacion()!=null?carteraAgrupadora.getNumeroOperacion().toString():null);
		if (TipoLineaCobroEnum.LC2.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC3.equals(solicitudManual.getLineaCobro())) {
			this.setEstadoCartera(EstadoCarteraEnum.AL_DIA);
		} else if (TipoLineaCobroEnum.LC1.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC4.equals(solicitudManual.getLineaCobro()) || TipoLineaCobroEnum.LC5.equals(solicitudManual.getLineaCobro())) {
			this.setEstadoCartera(EstadoCarteraEnum.MOROSO);
		}
		this.setEstadoSolicitud(solicitudManual.getEstadoSolicitud());
	}
	
	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * 
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * 
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de nombreCompleto.
	 * 
	 * @return valor de nombreCompleto.
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Método que retorna el valor de tipoSolicitante.
	 * 
	 * @return valor de tipoSolicitante.
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Método que retorna el valor de numeroOperacion.
	 * 
	 * @return valor de numeroOperacion.
	 */
	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * Método que retorna el valor de analista.
	 * 
	 * @return valor de analista.
	 */
	public String getAnalista() {
		return analista;
	}

	/**
	 * Método que retorna el valor de lineaCobro.
	 * 
	 * @return valor de lineaCobro.
	 */
	public TipoLineaCobroEnum getLineaCobro() {
		return lineaCobro;
	}

	/**
	 * Método que retorna el valor de estadoAfiliacion.
	 * 
	 * @return valor de estadoAfiliacion.
	 */
	public EstadoAfiliadoEnum getEstadoAfiliacion() {
		return estadoAfiliacion;
	}

	/**
	 * Método que retorna el valor de estadoCartera.
	 * 
	 * @return valor de estadoCartera.
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * Método que retorna el valor de estadoSolicitud.
	 * 
	 * @return valor de estadoSolicitud.
	 */
	public EstadoFiscalizacionEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de nombreCompleto.
	 * 
	 * @param valor
	 *            para modificar nombreCompleto.
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitante.
	 * 
	 * @param valor
	 *            para modificar tipoSolicitante.
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de numeroOperacion.
	 * 
	 * @param valor
	 *            para modificar numeroOperacion.
	 */
	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	/**
	 * Método encargado de modificar el valor de analista.
	 * 
	 * @param valor
	 *            para modificar analista.
	 */
	public void setAnalista(String analista) {
		this.analista = analista;
	}

	/**
	 * Método encargado de modificar el valor de lineaCobro.
	 * 
	 * @param valor
	 *            para modificar lineaCobro.
	 */
	public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
		this.lineaCobro = lineaCobro;
	}

	/**
	 * Método encargado de modificar el valor de estadoAfiliacion.
	 * 
	 * @param valor
	 *            para modificar estadoAfiliacion.
	 */
	public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de estadoCartera.
	 * 
	 * @param valor
	 *            para modificar estadoCartera.
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * Método encargado de modificar el valor de estadoSolicitud.
	 * 
	 * @param valor
	 *            para modificar estadoSolicitud.
	 */
	public void setEstadoSolicitud(EstadoFiscalizacionEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * Método que retorna el valor de accionCobro.
	 * 
	 * @return valor de accionCobro.
	 */
	public TipoAccionCobroEnum getAccionCobro() {
		return accionCobro;
	}

	/**
	 * Método que retorna el valor de tipoDeuda.
	 * 
	 * @return valor de tipoDeuda.
	 */
	public TipoDeudaEnum getTipoDeuda() {
		return tipoDeuda;
	}

	/**
	 * Método que retorna el valor de montoDeuda.
	 * 
	 * @return valor de montoDeuda.
	 */
	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}

	/**
	 * Método encargado de modificar el valor de accionCobro.
	 * 
	 * @param valor
	 *            para modificar accionCobro.
	 */
	public void setAccionCobro(TipoAccionCobroEnum accionCobro) {
		this.accionCobro = accionCobro;
	}

	/**
	 * Método encargado de modificar el valor de tipoDeuda.
	 * 
	 * @param valor
	 *            para modificar tipoDeuda.
	 */
	public void setTipoDeuda(TipoDeudaEnum tipoDeuda) {
		this.tipoDeuda = tipoDeuda;
	}

	/**
	 * Método encargado de modificar el valor de montoDeuda.
	 * 
	 * @param valor
	 *            para modificar montoDeuda.
	 */
	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}

	/**
	 * Método que retorna el valor de fechaIngreso.
	 * 
	 * @return valor de fechaIngreso.
	 */
	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * Método encargado de modificar el valor de fechaIngreso.
	 * 
	 * @param valor
	 *            para modificar fechaIngreso.
	 */
	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * Método que retorna el valor de deuda.
	 * 
	 * @return valor de deuda.
	 */
	public SubTipoDeudaEnum getDeuda() {
		return deuda;
	}

	/**
	 * Método encargado de modificar el valor de deuda.
	 * 
	 * @param valor
	 *            para modificar deuda.
	 */
	public void setDeuda(SubTipoDeudaEnum deuda) {
		this.deuda = deuda;
	}

	/**
	 * Obtiene el valor de numeroRadicado
	 * 
	 * @return El valor de numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * Establece el valor de numeroRadicado
	 * 
	 * @param numeroRadicado
	 *            El valor de numeroRadicado por asignar
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	/**
	 * Obtiene el valor de estadoGestion
	 * 
	 * @return El valor de estadoGestion
	 */
	public EstadoFiscalizacionEnum getEstadoGestion() {
		return estadoGestion;
	}

	/**
	 * Establece el valor de estadoGestion
	 * 
	 * @param estadoGestion
	 *            El valor de estadoGestion por asignar
	 */
	public void setEstadoGestion(EstadoFiscalizacionEnum estadoGestion) {
		this.estadoGestion = estadoGestion;
	}

	/**Obtiene el valor de idCartera
	 * @return El valor de idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/** Establece el valor de idCartera
	 * @param idCartera El valor de idCartera por asignar
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

}
