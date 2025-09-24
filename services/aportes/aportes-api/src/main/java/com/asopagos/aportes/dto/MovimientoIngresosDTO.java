package com.asopagos.aportes.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.util.CalendarUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * DTO que sirve para representar los datos en una consulta de movimientos de
 * ingresos históricos.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co">Cristian David Parra
 *         Zuluaga</a>
 */
public class MovimientoIngresosDTO {
	/**
	 * Número de operación del recaudo.
	 */
	private Long idAporte;

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
	 * Tipo de entidad, si es empresa, pensionado o independiente.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoEntidad;

	/**
	 * Tipo de identificación de la entidad: empleador, pensionado o
	 * independiente.
	 */
	private TipoIdentificacionEnum tipoIdentificacionEntidad;

	/**
	 * Número de identificación de la entidad.
	 */
	private String numeroIdentificacionEntidad;

	/**
	 * Nombres o razón social, según aplique uno u otro.
	 */
	private String nombres;

	/**
	 * Monto de todos los aportes Relacionados
	 */
	private BigDecimal montoAporte;

	/**
	 * Monto de todos los intereses Relacionados
	 */
	private BigDecimal montoIntereses;

	/**
	 * Sumatoria de todos los aportes e intereses Relacionados.
	 */
	private BigDecimal montoTotal;

	/**
	 * Indica "si" en caso de que haya pagado por medio de un tercero pagador,
	 * si es "no" el pago fue realizado por un tercero.
	 */
	private Boolean pagoPorSiMismo;

	/**
	 * Tipo de identificación del aportante.
	 */
	private TipoIdentificacionEnum tipoIdentificacionAportante;

	/**
	 * Número de identificación del aportante.
	 */
	private String numeroIdentificacionAportante;

	/**
	 * Nombre o razón social del aportante
	 */
	private String razonSocialAportante;

	/**
	 * Indica si el aporte tiene, al menos, un cotizante asociado
	 */
	private Boolean conDetalle;

	/**
	 * Tipo de reconocimiento
	 */
	private TipoReconocimientoAporteEnum tipoReconocimiento;

	/**
	 * Fecha en que fue hecho el reconocimiento
	 */
	private Long fechaReconocimiento;

	/**
	 * Casilla de selección.
	 */
	private Boolean casillaSeleccion;

	/**
	 * Estado del aporte
	 */
	private EstadoAporteEnum estadoAporte;

	/**
	 * Indica la forma del reconocimiento para el aporte
	 */
	private FormaReconocimientoAporteEnum formaReconocimientoAporte;

	/**
	 * Referencia a la empresa que tramita el aporte de un pensionado o
	 * independiente (pagador por terceros y tercero pagador)
	 */
	private Long idEmpresaTramitadora;
    
    /**
     * Indicador que marca al aporte en proceso de reconocimiento
     * */
     private Boolean enProcesoReconocimiento;

	/** Nuevos campos para el glpi 72581  */

	private ModalidadRecaudoAporteEnum modalidadRecaudoAporte;

	private String numeroPlanilla;

	private String periodoPagoAporte;

	private BigDecimal porcentajeAporte;

	@JsonIgnore
	private Long idRegistroGeneral;


	
	public MovimientoIngresosDTO(
		Long idAporteGeneral,
		Date fechaProcesamiento,
		Date fechaRecaudo,
		String tipoSolicitante,
		String tipoIdentificacionEntidad,
		String numeroIdentificacionEntidad,
		String razonSocial,
		String primerNombre,
		String segundoNombre,
		String primerApellido,
		String segundoApellido,
		String tipoIdentificacionAportante,
		String numeroIdentificacionAportante,
		String razonSocialAportante,
		BigDecimal valorTotalAporte,
		BigDecimal valorInteresesMora,
		Boolean conDetalle,
		String estadoRegistroAporteAportante,
		Date fechaReconocimiento,
		String formaReconocimientoAporte,
		String estadoAporteAportante,
		Long empresaTramitadora,
		Boolean enProcesoReconocimiento,
		String modalidadRecaudoAporte,
		Long idRegistroGeneral,
		BigDecimal porcentajeAporte
	) {
		iniciarDatos(idAporteGeneral,
		fechaProcesamiento,
		fechaRecaudo,
		tipoSolicitante,
		tipoIdentificacionEntidad,
		numeroIdentificacionEntidad,
		razonSocial,
		primerNombre,
		segundoNombre,
		primerApellido,
		segundoApellido,
		valorTotalAporte,
		valorInteresesMora,
		conDetalle,
		estadoRegistroAporteAportante,
		fechaReconocimiento,
		formaReconocimientoAporte,
		estadoAporteAportante,
		empresaTramitadora,
		enProcesoReconocimiento,
		modalidadRecaudoAporte,
		idRegistroGeneral);
		this.setTipoIdentificacionAportante(tipoIdentificacionAportante != null ? TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante) : this.getTipoIdentificacionEntidad());
		this.setNumeroIdentificacionAportante(numeroIdentificacionAportante != null ? numeroIdentificacionAportante : this.getNumeroIdentificacionEntidad());
		this.setRazonSocialAportante(razonSocialAportante != null ? razonSocialAportante : this.getNombres());
		this.setPorcentajeAporte(porcentajeAporte != null ? porcentajeAporte : new BigDecimal("0"));

	}

	/**
	 * <b>Método constructor del DTO</b> para estructurar información a mostrar
	 * cuando se hace <b>por persona</b>.
	 */
	public MovimientoIngresosDTO(Persona aportantePersona, AporteGeneral aporteGeneral) {
		this.setIdAporte(aporteGeneral.getId());

		if (aporteGeneral.getFechaProcesamiento() != null) {
			this.setFechaRegistroAporte(aporteGeneral.getFechaProcesamiento().getTime());
		}
		if (aporteGeneral.getFechaRecaudo() != null) {
		    this.setFechaPago(aporteGeneral.getFechaRecaudo().getTime());
			this.setAntiguedadRecaudo(CalendarUtils.obtenerDiferenciaEntreFechasComoString(aporteGeneral.getFechaRecaudo(), new Date()));
		}

		this.setTipoEntidad(aporteGeneral.getTipoSolicitante());
		this.setTipoIdentificacionEntidad(aportantePersona.getTipoIdentificacion());
		this.setNumeroIdentificacionEntidad(aportantePersona.getNumeroIdentificacion());
		
		if (aportantePersona.getRazonSocial() != null) {
			this.setNombres(aportantePersona.getRazonSocial());
		} else {
			if (aportantePersona.getPrimerNombre() != null) {
				String nombre = aportantePersona.getPrimerNombre() + " ";
				nombre += aportantePersona.getSegundoNombre() != null ? aportantePersona.getSegundoNombre() + " " : "";
				nombre += aportantePersona.getPrimerApellido() + " ";
				nombre += aportantePersona.getSegundoApellido() != null ? aportantePersona.getSegundoApellido() : "";
				this.setNombres(nombre);
			}
		}

		this.setMontoAporte(aporteGeneral.getValorTotalAporteObligatorio());
		this.setMontoIntereses(aporteGeneral.getValorInteresesMora());
		BigDecimal monto = aporteGeneral.getValorTotalAporteObligatorio() != null ? aporteGeneral.getValorTotalAporteObligatorio() : BigDecimal.ZERO;
		BigDecimal interes = aporteGeneral.getValorInteresesMora() != null ? aporteGeneral.getValorInteresesMora() : BigDecimal.ZERO;
		this.setMontoTotal(monto.add(interes));

		/**
		 * Se debe validar por tipo solicitante
		 * Si es independiente y pensionados 
		 */
		if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aporteGeneral.getTipoSolicitante())) {
			this.setPagoPorSiMismo(Boolean.FALSE);
		} else if (aporteGeneral.getEmpresaTramitadoraAporte() != null) {
			this.setPagoPorSiMismo(Boolean.FALSE);
		} else {
			this.setPagoPorSiMismo(Boolean.TRUE);
		}

		// NOTA: La información del aportante se asigna en el método
		// consultarMovimientoHistoricos de la clase
		// com.asopagos.aportes.business.ejb.ConsultasModeloCore
		this.setConDetalle(aporteGeneral.getAporteConDetalle());

		if (aporteGeneral.getEstadoRegistroAporteAportante() != null) {
			if (aporteGeneral.getEstadoRegistroAporteAportante().equals(EstadoRegistroAporteEnum.REGISTRADO)) {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.RECONOCIMIENTO_INGRESOS);
			} else {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.OTROS_INGRESOS);
			}
		}

		if (aporteGeneral.getFechaReconocimiento() != null) {
			this.setFechaReconocimiento(aporteGeneral.getFechaReconocimiento().getTime());
		}

		this.setFormaReconocimientoAporte(aporteGeneral.getFormaReconocimientoAporte());
		this.setEstadoAporte(aporteGeneral.getEstadoAporteAportante());
		this.setIdEmpresaTramitadora(aporteGeneral.getEmpresaTramitadoraAporte());
		
        this.setEnProcesoReconocimiento(
                aporteGeneral.getEnProcesoReconocimiento() != null ? aporteGeneral.getEnProcesoReconocimiento() : Boolean.FALSE);

		this.setModalidadRecaudoAporte(aporteGeneral.getModalidadRecaudoAporte());

		this.setIdRegistroGeneral(aporteGeneral.getIdRegistroGeneral());
		this.setPorcentajeAporte(new BigDecimal("0"));

	}

	/**
	 * Constructor para query nativa para empresas
	 */

	 private void iniciarDatos(
		Long idAporteGeneral,
		Date fechaProcesamiento,
		Date fechaRecaudo,
		String tipoSolicitante,
		String tipoIdentificacionEntidad,
		String numeroIdentificacionEntidad,
		String razonSocial,
		String primerNombre,
		String segundoNombre,
		String primerApellido,
		String segundoApellido,
		BigDecimal valorTotalAporte,
		BigDecimal valorInteresesMora,
		Boolean conDetalle,
		String estadoRegistroAporteAportante,
		Date fechaReconocimiento,
		String formaReconocimientoAporte,
		String estadoAporteAportante,
		Long empresaTramitadora,
		Boolean enProcesoReconocimiento,
		String modalidadRecaudoAporte,
		Long idRegistroGeneral
		) {
			this.setIdAporte(idAporteGeneral);

			if (fechaProcesamiento != null) {
				this.setFechaRegistroAporte(fechaProcesamiento.getTime());
			}
			if (fechaRecaudo != null) {
				this.setFechaPago(fechaRecaudo.getTime());
				this.setAntiguedadRecaudo(CalendarUtils.obtenerDiferenciaEntreFechasComoString(fechaRecaudo, new Date()));
			}

			this.setTipoEntidad(TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante));

			this.setTipoIdentificacionEntidad(TipoIdentificacionEnum.valueOf(tipoIdentificacionEntidad));
			this.setNumeroIdentificacionEntidad(numeroIdentificacionEntidad);

			if (razonSocial != null) {
				this.setNombres(razonSocial);
			} else {
				if (primerNombre != null) {
					String nombre = primerNombre + " ";
					nombre += segundoNombre != null ? segundoNombre + " " : "";
					nombre += primerApellido + " ";
					nombre += segundoApellido != null ? segundoApellido : "";
					this.setNombres(nombre);
				}
			}
			

			this.setMontoAporte(valorTotalAporte);
			this.setMontoIntereses(valorInteresesMora);
			BigDecimal monto = valorTotalAporte != null ? valorTotalAporte : BigDecimal.ZERO;
			BigDecimal interes = valorInteresesMora != null ? valorInteresesMora : BigDecimal.ZERO;
			this.setMontoTotal(monto.add(interes));

			/**
			 * Se debe validar por tipo solicitante
			 * Si es independiente y pensionados 
			 */
			if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante))) {
				this.setPagoPorSiMismo(Boolean.FALSE);
			} else if (empresaTramitadora != null) {
				this.setPagoPorSiMismo(Boolean.FALSE);
			} else {
				this.setPagoPorSiMismo(Boolean.TRUE);
			}

			this.setTipoIdentificacionAportante(this.getTipoIdentificacionEntidad());
			this.setNumeroIdentificacionAportante(this.getNumeroIdentificacionEntidad());
			this.setRazonSocialAportante(this.getNombres());
			this.setConDetalle(conDetalle);

			if (estadoRegistroAporteAportante != null) {
				if (EstadoRegistroAporteEnum.valueOf(estadoRegistroAporteAportante).equals(EstadoRegistroAporteEnum.REGISTRADO)) {
					this.setTipoReconocimiento(TipoReconocimientoAporteEnum.RECONOCIMIENTO_INGRESOS);
				} else {
					this.setTipoReconocimiento(TipoReconocimientoAporteEnum.OTROS_INGRESOS);
				}
			}

			if (fechaReconocimiento != null) {
				this.setFechaReconocimiento(fechaReconocimiento.getTime());
			}

			this.setFormaReconocimientoAporte(FormaReconocimientoAporteEnum.valueOf(formaReconocimientoAporte));
			this.setEstadoAporte(EstadoAporteEnum.valueOf(estadoAporteAportante));
			this.setIdEmpresaTramitadora(empresaTramitadora);
			
			this.setEnProcesoReconocimiento(
				enProcesoReconocimiento != null ? enProcesoReconocimiento : Boolean.FALSE);

			this.setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudoAporte));
			this.setIdRegistroGeneral(idRegistroGeneral);
			this.setPorcentajeAporte(new BigDecimal("0"));
	 }

	 public MovimientoIngresosDTO(
		Long idAporteGeneral,
		Date fechaProcesamiento,
		Date fechaRecaudo,
		String tipoSolicitante,
		String tipoIdentificacionEntidad,
		String numeroIdentificacionEntidad,
		String razonSocial,
		String primerNombre,
		String segundoNombre,
		String primerApellido,
		String segundoApellido,
		BigDecimal valorTotalAporte,
		BigDecimal valorInteresesMora,
		Boolean conDetalle,
		String estadoRegistroAporteAportante,
		Date fechaReconocimiento,
		String formaReconocimientoAporte,
		String estadoAporteAportante,
		Long empresaTramitadora,
		Boolean enProcesoReconocimiento,
		String modalidadRecaudoAporte,
		Long idRegistroGeneral
		) {
			iniciarDatos(
				idAporteGeneral,
				fechaProcesamiento,
				fechaRecaudo,
				tipoSolicitante,
				tipoIdentificacionEntidad,
				numeroIdentificacionEntidad,
				razonSocial,
				primerNombre,
				segundoNombre,
				primerApellido,
				segundoApellido,
				valorTotalAporte,
				valorInteresesMora,
				conDetalle,
				estadoRegistroAporteAportante,
				fechaReconocimiento,
				formaReconocimientoAporte,
				estadoAporteAportante,
				empresaTramitadora,
				enProcesoReconocimiento,
				modalidadRecaudoAporte,
				idRegistroGeneral
			);
	 }

	/**
	 * <b>Método constructor del DTO</b> para estructurar información a mostrar
	 * cuando se hace <b>por empresa</b>.
	 */
	public MovimientoIngresosDTO(Empresa aportanteEmpresa, AporteGeneral aporteGeneral ) {
		this.setIdAporte(aporteGeneral.getId());

		if (aporteGeneral.getFechaProcesamiento() != null) {
			this.setFechaRegistroAporte(aporteGeneral.getFechaProcesamiento().getTime());
		}
	    if (aporteGeneral.getFechaRecaudo() != null) {
            this.setFechaPago(aporteGeneral.getFechaRecaudo().getTime());
			this.setAntiguedadRecaudo(CalendarUtils.obtenerDiferenciaEntreFechasComoString(aporteGeneral.getFechaRecaudo(), new Date()));
        }

		this.setTipoEntidad(aporteGeneral.getTipoSolicitante());

		if (aportanteEmpresa.getPersona() != null) {
			this.setTipoIdentificacionEntidad(aportanteEmpresa.getPersona().getTipoIdentificacion());
			this.setNumeroIdentificacionEntidad(aportanteEmpresa.getPersona().getNumeroIdentificacion());

			if (aportanteEmpresa.getPersona().getRazonSocial() != null) {
				this.setNombres(aportanteEmpresa.getPersona().getRazonSocial());
			} else {
				if (aportanteEmpresa.getPersona().getPrimerNombre() != null) {
					String nombre = aportanteEmpresa.getPersona().getPrimerNombre() + " ";
					nombre += aportanteEmpresa.getPersona().getSegundoNombre() != null ? aportanteEmpresa.getPersona().getSegundoNombre() + " " : "";
					nombre += aportanteEmpresa.getPersona().getPrimerApellido() + " ";
					nombre += aportanteEmpresa.getPersona().getSegundoApellido() != null ? aportanteEmpresa.getPersona().getSegundoApellido() : "";
					this.setNombres(nombre);
				}
			}
		}

		this.setMontoAporte(aporteGeneral.getValorTotalAporteObligatorio());
		this.setMontoIntereses(aporteGeneral.getValorInteresesMora());
		BigDecimal monto = aporteGeneral.getValorTotalAporteObligatorio() != null ? aporteGeneral.getValorTotalAporteObligatorio() : BigDecimal.ZERO;
		BigDecimal interes = aporteGeneral.getValorInteresesMora() != null ? aporteGeneral.getValorInteresesMora() : BigDecimal.ZERO;
		this.setMontoTotal(monto.add(interes));

		/**
		 * Se debe validar por tipo solicitante
		 * Si es independiente y pensionados 
		 */
		if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(aporteGeneral.getTipoSolicitante())) {
			this.setPagoPorSiMismo(Boolean.FALSE);
		} else if (aporteGeneral.getEmpresaTramitadoraAporte() != null) {
			this.setPagoPorSiMismo(Boolean.FALSE);
		} else {
			this.setPagoPorSiMismo(Boolean.TRUE);
		}

		this.setTipoIdentificacionAportante(this.getTipoIdentificacionEntidad());
		this.setNumeroIdentificacionAportante(this.getNumeroIdentificacionEntidad());
		this.setRazonSocialAportante(this.getNombres());
		this.setConDetalle(aporteGeneral.getAporteConDetalle());

		if (aporteGeneral.getEstadoRegistroAporteAportante() != null) {
			if (aporteGeneral.getEstadoRegistroAporteAportante().equals(EstadoRegistroAporteEnum.REGISTRADO)) {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.RECONOCIMIENTO_INGRESOS);
			} else {
				this.setTipoReconocimiento(TipoReconocimientoAporteEnum.OTROS_INGRESOS);
			}
		}

		if (aporteGeneral.getFechaReconocimiento() != null) {
			this.setFechaReconocimiento(aporteGeneral.getFechaReconocimiento().getTime());
		}

		this.setFormaReconocimientoAporte(aporteGeneral.getFormaReconocimientoAporte());
		this.setEstadoAporte(aporteGeneral.getEstadoAporteAportante());
		this.setIdEmpresaTramitadora(aporteGeneral.getEmpresaTramitadoraAporte());
        
        this.setEnProcesoReconocimiento(
                aporteGeneral.getEnProcesoReconocimiento() != null ? aporteGeneral.getEnProcesoReconocimiento() : Boolean.FALSE);

		this.setModalidadRecaudoAporte(aporteGeneral.getModalidadRecaudoAporte());
		this.setIdRegistroGeneral(aporteGeneral.getIdRegistroGeneral());
		this.setPorcentajeAporte(new BigDecimal("0"));
	}

	/**
	 * Constructor por defecto
	 */
	public MovimientoIngresosDTO() {
	}

	/**
	 * Obtiene el valor de idAporte
	 * 
	 * @return El valor de idAporte
	 */
	public Long getIdAporte() {
		return idAporte;
	}

	/**
	 * Establece el valor de idAporte
	 * 
	 * @param idAporte
	 *            El valor de idAporte por asignar
	 */
	public void setIdAporte(Long idAporte) {
		this.idAporte = idAporte;
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
	 * Obtiene el valor de tipoEntidad
	 * 
	 * @return El valor de tipoEntidad
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * Establece el valor de tipoEntidad
	 * 
	 * @param tipoEntidad
	 *            El valor de tipoEntidad por asignar
	 */
	public void setTipoEntidad(TipoSolicitanteMovimientoAporteEnum tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * Obtiene el valor de tipoIdentificacionEntidad
	 * 
	 * @return El valor de tipoIdentificacionEntidad
	 */
	public TipoIdentificacionEnum getTipoIdentificacionEntidad() {
		return tipoIdentificacionEntidad;
	}

	/**
	 * Establece el valor de tipoIdentificacionEntidad
	 * 
	 * @param tipoIdentificacionEntidad
	 *            El valor de tipoIdentificacionEntidad por asignar
	 */
	public void setTipoIdentificacionEntidad(TipoIdentificacionEnum tipoIdentificacionEntidad) {
		this.tipoIdentificacionEntidad = tipoIdentificacionEntidad;
	}

	/**
	 * Obtiene el valor de numeroIdentificacionEntidad
	 * 
	 * @return El valor de numeroIdentificacionEntidad
	 */
	public String getNumeroIdentificacionEntidad() {
		return numeroIdentificacionEntidad;
	}

	/**
	 * Establece el valor de numeroIdentificacionEntidad
	 * 
	 * @param numeroIdentificacionEntidad
	 *            El valor de numeroIdentificacionEntidad por asignar
	 */
	public void setNumeroIdentificacionEntidad(String numeroIdentificacionEntidad) {
		this.numeroIdentificacionEntidad = numeroIdentificacionEntidad;
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
	 * Obtiene el valor de montoIntereses
	 * 
	 * @return El valor de montoIntereses
	 */
	public BigDecimal getMontoIntereses() {
		return montoIntereses;
	}

	/**
	 * Establece el valor de montoIntereses
	 * 
	 * @param montoIntereses
	 *            El valor de montoIntereses por asignar
	 */
	public void setMontoIntereses(BigDecimal montoIntereses) {
		this.montoIntereses = montoIntereses;
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
	 * Obtiene el valor de pagoPorSiMismo
	 * 
	 * @return El valor de pagoPorSiMismo
	 */
	public Boolean getPagoPorSiMismo() {
		return pagoPorSiMismo;
	}

	/**
	 * Establece el valor de pagoPorSiMismo
	 * 
	 * @param pagoPorSiMismo
	 *            El valor de pagoPorSiMismo por asignar
	 */
	public void setPagoPorSiMismo(Boolean pagoPorSiMismo) {
		this.pagoPorSiMismo = pagoPorSiMismo;
	}

	/**
	 * Obtiene el valor de tipoIdentificacionAportante
	 * 
	 * @return El valor de tipoIdentificacionAportante
	 */
	public TipoIdentificacionEnum getTipoIdentificacionAportante() {
		return tipoIdentificacionAportante;
	}

	/**
	 * Establece el valor de tipoIdentificacionAportante
	 * 
	 * @param tipoIdentificacionAportante
	 *            El valor de tipoIdentificacionAportante por asignar
	 */
	public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
		this.tipoIdentificacionAportante = tipoIdentificacionAportante;
	}

	/**
	 * Obtiene el valor de numeroIdentificacionAportante
	 * 
	 * @return El valor de numeroIdentificacionAportante
	 */
	public String getNumeroIdentificacionAportante() {
		return numeroIdentificacionAportante;
	}

	/**
	 * Establece el valor de numeroIdentificacionAportante
	 * 
	 * @param numeroIdentificacionAportante
	 *            El valor de numeroIdentificacionAportante por asignar
	 */
	public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
	}

	/**
	 * Obtiene el valor de conDetalle
	 * 
	 * @return El valor de conDetalle
	 */
	public Boolean getConDetalle() {
		return conDetalle;
	}

	/**
	 * Establece el valor de conDetalle
	 * 
	 * @param conDetalle
	 *            El valor de conDetalle por asignar
	 */
	public void setConDetalle(Boolean conDetalle) {
		this.conDetalle = conDetalle;
	}

	/**
	 * Obtiene el valor de casillaSeleccion
	 * 
	 * @return El valor de casillaSeleccion
	 */
	public Boolean getCasillaSeleccion() {
		return casillaSeleccion;
	}

	/**
	 * Establece el valor de casillaSeleccion
	 * 
	 * @param casillaSeleccion
	 *            El valor de casillaSeleccion por asignar
	 */
	public void setCasillaSeleccion(Boolean casillaSeleccion) {
		this.casillaSeleccion = casillaSeleccion;
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
	 * Obtiene el valor de formaReconocimientoAporte
	 * 
	 * @return El valor de formaReconocimientoAporte
	 */
	public FormaReconocimientoAporteEnum getFormaReconocimientoAporte() {
		return formaReconocimientoAporte;
	}

	/**
	 * Establece el valor de formaReconocimientoAporte
	 * 
	 * @param formaReconocimientoAporte
	 *            El valor de formaReconocimientoAporte por asignar
	 */
	public void setFormaReconocimientoAporte(FormaReconocimientoAporteEnum formaReconocimientoAporte) {
		this.formaReconocimientoAporte = formaReconocimientoAporte;
	}

	/**
	 * Obtiene el valor de razonSocialAportante
	 * 
	 * @return El valor de razonSocialAportante
	 */
	public String getRazonSocialAportante() {
		return razonSocialAportante;
	}

	/**
	 * Establece el valor de razonSocialAportante
	 * 
	 * @param razonSocialAportante
	 *            El valor de razonSocialAportante por asignar
	 */
	public void setRazonSocialAportante(String razonSocialAportante) {
		this.razonSocialAportante = razonSocialAportante;
	}

	/**
	 * Obtiene el valor de idEmpresaTramitadora
	 * 
	 * @return El valor de idEmpresaTramitadora
	 */
	public Long getIdEmpresaTramitadora() {
		return idEmpresaTramitadora;
	}

	/**
	 * Establece el valor de idEmpresaTramitadora
	 * 
	 * @param idEmpresaTramitadora
	 *            El valor de idEmpresaTramitadora por asignar
	 */
	public void setIdEmpresaTramitadora(Long idEmpresaTramitadora) {
		this.idEmpresaTramitadora = idEmpresaTramitadora;
	}

	/**
	 * Obtiene el valor de tipoReconocimiento
	 * 
	 * @return El valor de tipoReconocimiento
	 */
	public TipoReconocimientoAporteEnum getTipoReconocimiento() {
		return tipoReconocimiento;
	}

	/**
	 * Establece el valor de tipoReconocimiento
	 * 
	 * @param tipoReconocimiento
	 *            El valor de tipoReconocimiento por asignar
	 */
	public void setTipoReconocimiento(TipoReconocimientoAporteEnum tipoReconocimiento) {
		this.tipoReconocimiento = tipoReconocimiento;
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

    /**
     * @return the enProcesoReconocimiento
     */
    public Boolean getEnProcesoReconocimiento() {
        return enProcesoReconocimiento;
    }

    /**
     * @param enProcesoReconocimiento the enProcesoReconocimiento to set
     */
    public void setEnProcesoReconocimiento(Boolean enProcesoReconocimiento) {
        this.enProcesoReconocimiento = enProcesoReconocimiento;
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


	public ModalidadRecaudoAporteEnum getModalidadRecaudoAporte() {
		return this.modalidadRecaudoAporte;
	}

	public void setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum modalidadRecaudoAporte) {
		this.modalidadRecaudoAporte = modalidadRecaudoAporte;
	}

	public String getNumeroPlanilla() {
		return this.numeroPlanilla;
	}

	public void setNumeroPlanilla(String numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
	}

	public String getPeriodoPagoAporte() {
		return this.periodoPagoAporte;
	}

	public void setPeriodoPagoAporte(String periodoPagoAporte) {
		this.periodoPagoAporte = periodoPagoAporte;
	}

	public BigDecimal getPorcentajeAporte() {
		return this.porcentajeAporte;
	}

	public void setPorcentajeAporte(BigDecimal porcentajeAporte) {
		this.porcentajeAporte = porcentajeAporte;
	}


	public Long getIdRegistroGeneral() {
		return this.idRegistroGeneral;
	}

	public void setIdRegistroGeneral(Long idRegistroGeneral) {
		this.idRegistroGeneral = idRegistroGeneral;
	}



}