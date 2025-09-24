package com.asopagos.aportes.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoReconocimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que representa un movimiento detallado de aportes
 * <br/>
 * <b>Módulo:</b> Asopagos - HU-261 <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class MovimientoIngresosDetalladoDTO {

	/**
	 * Número de operación del recaudo para Nivel 2.
	 */
	private Long idAporteDetallado;

	/**
	 * Fecha en que se registró el aporte.
	 */
	private Long fechaRegistroAporte;
	
    /**
     * Fecha de pago del recaudo de aporte
     */
    private Long fechaPago;

	/**
	 * Antiguedad del recaudo en años, meses y días desde la fecha de registro
	 * del aporte a la fecha actual.
	 */
	private String antiguedadRecaudo;

	/**
	 * Tipo de afiliado, si es dependiente.
	 */
	private TipoAfiliadoEnum tipoPersona;

	/**
	 * Tipo de identificación de la persona(Cotizante).
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación de la persona(Cotizante).
	 */
	private String numeroIdentificacion;

	/**
	 * Nombres
	 */
	private String nombres;

	/**
	 * Monto de todos los aportes Relacionados
	 */
	private BigDecimal montoAporte;

	/**
	 * Monto de todos los intereses Relacionados
	 */
	private BigDecimal montoInteres;

	/**
	 * Sumatoria de todos los aportes e intereses Relacionados.
	 */
	private BigDecimal montoTotal;

	/**
	 * Descripción del estado del aporte a nivel de cotizante
	 */
	private EstadoAporteEnum estadoAporte;

	/**
	 * Tipo de reconocimiento de ingresos. Toma los valores “Reconocimiento de
	 * ingresos” y “Otros Ingresos”.
	 */
	private TipoReconocimientoAporteEnum tipoReconocimiento;

	/**
	 * Fecha en que fue hecho el reconocimiento
	 */
	private Long fechaReconocimiento;

	/**
	 * Forma de reconocimiento del aporte.
	 */
	private FormaReconocimientoAporteEnum formaReconocimiento;

	/**
	 * Método constructor
	 */
	public MovimientoIngresosDetalladoDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param idAporteDetallado
	 * @param fechaRegistroAporte
	 * @param tipoPersona
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param primerNombre
	 * @param segundoNombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param montoAporte
	 * @param montoInteres
	 * @param estadoAporte
	 * @param tipoMovimiento
	 * @param fechaReconocimiento
	 * @param formaReconocimiento
	 */
    public MovimientoIngresosDetalladoDTO(Long idAporteDetallado, Date fechaRegistroAporte, TipoAfiliadoEnum tipoPersona,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, BigDecimal montoAporte, BigDecimal montoInteres, EstadoAporteEnum estadoAporte,
            EstadoRegistroAporteEnum estadoRegistroAporteAportante, Date fechaReconocimiento,
            FormaReconocimientoAporteEnum formaReconocimiento, Date fechaRecaudo) {
		this.setIdAporteDetallado(idAporteDetallado);

		if (fechaRegistroAporte != null) {
			this.setFechaRegistroAporte(fechaRegistroAporte.getTime());
		}

		if (fechaRecaudo != null){
			this.setFechaPago(fechaRecaudo.getTime());
			this.setAntiguedadRecaudo(CalendarUtils.obtenerDiferenciaEntreFechasComoString(fechaRecaudo, new Date()));
		}else{
			this.setFechaPago(null);
		}

		this.setTipoPersona(tipoPersona);
		this.setTipoIdentificacion(tipoIdentificacion);
		this.setNumeroIdentificacion(numeroIdentificacion);

		if (primerNombre != null) {
			String nombre = primerNombre + " ";
			nombre += segundoNombre != null ? segundoNombre + " " : "";
			nombre += primerApellido + " ";
			nombre += segundoApellido != null ? segundoApellido : "";
			this.setNombres(nombre);
		}

		this.setMontoAporte(montoAporte);
		this.setMontoInteres(montoInteres);
		BigDecimal monto = (montoAporte != null ? montoAporte : BigDecimal.ZERO);
		BigDecimal interes = (montoInteres != null ? montoInteres : BigDecimal.ZERO);
		this.setMontoTotal(monto.add(interes));

		if (estadoRegistroAporteAportante != null) {
			if (estadoRegistroAporteAportante.equals(EstadoRegistroAporteEnum.REGISTRADO)) {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.RECONOCIMIENTO_INGRESOS);
			} else {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.OTROS_INGRESOS);
			}
		}

		this.setFechaReconocimiento(fechaReconocimiento != null ? fechaReconocimiento.getTime() : null);
		this.setFormaReconocimiento(formaReconocimiento);
		this.setEstadoAporte(estadoAporte);
	}

	/**
	 * Obtiene el valor de idAporteDetallado
	 * 
	 * @return El valor de idAporteDetallado
	 */
	public Long getIdAporteDetallado() {
		return idAporteDetallado;
	}

	/**
	 * Establece el valor de idAporteDetallado
	 * 
	 * @param idAporteDetallado
	 *            El valor de idAporteDetallado por asignar
	 */
	public void setIdAporteDetallado(Long idAporteDetallado) {
		this.idAporteDetallado = idAporteDetallado;
	}

	/**
	 * Obtiene el valor de fechaRegistroAporte
	 * 
	 * @return El valor de fechaRegistroAporte
	 */
	public Long getFechaRegistroAporte() {
		return fechaRegistroAporte;
	}

	/**
	 * Establece el valor de fechaRegistroAporte
	 * 
	 * @param fechaRegistroAporte
	 *            El valor de fechaRegistroAporte por asignar
	 */
	public void setFechaRegistroAporte(Long fechaRegistroAporte) {
		this.fechaRegistroAporte = fechaRegistroAporte;
	}

	/**
	 * Obtiene el valor de antiguedadRecaudo
	 * 
	 * @return El valor de antiguedadRecaudo
	 */
	public String getAntiguedadRecaudo() {
		return antiguedadRecaudo;
	}

	/**
	 * Establece el valor de antiguedadRecaudo
	 * 
	 * @param antiguedadRecaudo
	 *            El valor de antiguedadRecaudo por asignar
	 */
	public void setAntiguedadRecaudo(String antiguedadRecaudo) {
		this.antiguedadRecaudo = antiguedadRecaudo;
	}

	/**
	 * Obtiene el valor de tipoPersona
	 * 
	 * @return El valor de tipoPersona
	 */
	public TipoAfiliadoEnum getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Establece el valor de tipoPersona
	 * 
	 * @param tipoPersona
	 *            El valor de tipoPersona por asignar
	 */
	public void setTipoPersona(TipoAfiliadoEnum tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de nombres
	 * 
	 * @return El valor de nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Establece el valor de nombres
	 * 
	 * @param nombres
	 *            El valor de nombres por asignar
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * Obtiene el valor de montoAporte
	 * 
	 * @return El valor de montoAporte
	 */
	public BigDecimal getMontoAporte() {
		return montoAporte;
	}

	/**
	 * Establece el valor de montoAporte
	 * 
	 * @param montoAporte
	 *            El valor de montoAporte por asignar
	 */
	public void setMontoAporte(BigDecimal montoAporte) {
		this.montoAporte = montoAporte;
	}

	/**
	 * Obtiene el valor de montoInteres
	 * 
	 * @return El valor de montoInteres
	 */
	public BigDecimal getMontoInteres() {
		return montoInteres;
	}

	/**
	 * Establece el valor de montoInteres
	 * 
	 * @param montoInteres
	 *            El valor de montoInteres por asignar
	 */
	public void setMontoInteres(BigDecimal montoInteres) {
		this.montoInteres = montoInteres;
	}

	/**
	 * Obtiene el valor de montoTotal
	 * 
	 * @return El valor de montoTotal
	 */
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	/**
	 * Establece el valor de montoTotal
	 * 
	 * @param montoTotal
	 *            El valor de montoTotal por asignar
	 */
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	/**
	 * Obtiene el valor de estadoAporte
	 * 
	 * @return El valor de estadoAporte
	 */
	public EstadoAporteEnum getEstadoAporte() {
		return estadoAporte;
	}

	/**
	 * Establece el valor de estadoAporte
	 * 
	 * @param estadoAporte
	 *            El valor de estadoAporte por asignar
	 */
	public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
		this.estadoAporte = estadoAporte;
	}

	/**
	 * Obtiene el valor de formaReconocimiento
	 * 
	 * @return El valor de formaReconocimiento
	 */
	public FormaReconocimientoAporteEnum getFormaReconocimiento() {
		return formaReconocimiento;
	}

	/**
	 * Establece el valor de formaReconocimiento
	 * 
	 * @param formaReconocimiento
	 *            El valor de formaReconocimiento por asignar
	 */
	public void setFormaReconocimiento(FormaReconocimientoAporteEnum formaReconocimiento) {
		this.formaReconocimiento = formaReconocimiento;
	}

	/**
	 * Obtiene el valor de fechaReconocimiento
	 * 
	 * @return El valor de fechaReconocimiento
	 */
	public Long getFechaReconocimiento() {
		return fechaReconocimiento;
	}

	/**
	 * Establece el valor de fechaReconocimiento
	 * 
	 * @param fechaReconocimiento
	 *            El valor de fechaReconocimiento por asignar
	 */
	public void setFechaReconocimiento(Long fechaReconocimiento) {
		this.fechaReconocimiento = fechaReconocimiento;
	}

	/**Obtiene el valor de tipoReconocimiento
	 * @return El valor de tipoReconocimiento
	 */
	public TipoReconocimientoAporteEnum getTipoReconocimiento() {
		return tipoReconocimiento;
	}

	/** Establece el valor de tipoReconocimiento
	 * @param tipoReconocimiento El valor de tipoReconocimiento por asignar
	 */
	public void setTipoReconocimiento(TipoReconocimientoAporteEnum tipoReconocimiento) {
		this.tipoReconocimiento = tipoReconocimiento;
	}

    /**
     * @return the fechaPago
     */
    public Long getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Long fechaPago) {
        this.fechaPago = fechaPago;
    }
}
