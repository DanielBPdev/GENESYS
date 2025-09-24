package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.ModalidadPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;

/**
 * Entidad que representa la información general de un aporte <br/>
 * <b>Historia de Usuario: </b>211-397
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez
 *         Cediel.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@XmlRootElement
public class AporteGeneralModeloDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo general
	 */
	private Long id;

	/**
	 * Referencia al registro general del aporte (staging)
	 */
	private Long idRegistroGeneral;

	/**
	 * Referencia a la empresa en calidad de aportante
	 */
	private Long idEmpresa;

	/**
	 * Referencia a la persona independiente en calidad de aportante
	 */
	private Long idPersona;

	/**
	 * Descripción del estado del aportante
	 */
	private EstadoEmpleadorEnum estadoAportante;

	/**
	 * Descripción del estado del aporte a nivel de Aportante
	 */
	private EstadoAporteEnum estadoAporteAportante;

	/**
	 * Descripción del estado del registro a nivel de Aportante
	 */
	private EstadoRegistroAporteEnum estadoRegistroAporteAportante;

	/**
	 * Referencia a la sucursal de la empresa del aportante
	 */
	private Long idSucursalEmpresa;

	/**
	 * Marca de Referencia que indica que el aportante es "pagador por terceros"
	 * para el aporte asociado
	 */
	private Boolean pagadorPorTerceros;

	/**
	 * Período de pago
	 */
	private String periodoAporte;

	/**
	 * Aporte obligatorio del aporte
	 */
	private BigDecimal valorTotalAporteObligatorio;

	/**
	 * Valor interés de mora para un pensionado
	 */
	private BigDecimal valorInteresesMora;

	/**
	 * Fecha de recaudo del aporte
	 */
	private Long fechaRecaudo;

	/**
	 * Fecha de procesamiento del aporte (Sistema al momento de relacionar o
	 * registrar)
	 */
	private Long fechaProcesamiento;

	/**
	 * Codigo de la entidad financiera recaudadora o receptora
	 */
	private Short codigoEntidadFinanciera;

	/**
	 * Referencia al operador de informacion que se relaciona en el registro de
	 * la planilla integrada de liquidación de aportes
	 */
	private Long idOperadorInformacion;

	/**
	 * Modalidad de la planilla integrada de liquidación de aportes
	 */
	private ModalidadPlanillaEnum modalidadPlanilla;

	/**
	 * Modalidad del recaudo del aporte
	 */
	private ModalidadRecaudoAporteEnum modalidadRecaudoAporte;

	/**
	 * Indica si un aporte tiene detalle 1=[Si] o 0=[No]
	 */
	private Boolean aporteConDetalle;

	/**
	 * Indica el número de cuenta por la cual se recauda el aporte
	 */
	private String numeroCuenta;

	/**
	 * Indica el tipo de solicitante que realizo el movimiento en aporte
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Indica si el aporte general tiene modificaciones en movimiento aporte
	 */
	private Boolean tieneModificaciones;

	/**
	 * Indica el origen del aporte
	 */
	private OrigenAporteEnum origenAporte;

	/**
	 * Se relaciona con el id de la caja de compensacion
	 */
	private Integer idCajaCompensacion;

	/**
	 * Indica el correo electrónico asociada al aportante
	 */
	private String emailAportante;

	/**
	 * Referencia a la empresa que tramita el aporte de un pensionado o
	 * independiente (pagador por terceros y tercero pagador)
	 */
	private Long empresaTramitadoraAporte;

	/**
	 * Indica la forma del reconocimiento para el aporte
	 */
	private FormaReconocimientoAporteEnum formaReconocimientoAporte;

	/**
	 * Fecha de reconocimiento del aporte, en la cual se almacena la forma de
	 * reconocimiento
	 */
	private Long fechaReconocimiento;

	/**
     * Representa una marca en aporte general donde indica el periodo que se cancelo 
     * teniendo en cuenta la fecha actual (PERIODO_REGULAR, PERIODO_RETROACTIVO, PERIODO_FUTURO) 
     */
    private MarcaPeriodoEnum marcaPeriodo;
	
    /**
     * marca usada para identificar cuáles fueron los aportes que llegaron en cierta planilla
     */
    private Boolean marcaActualizacionCartera;
    
    /**
     * Conciliado aportes representa cuando ya se realizo un cierre de recaudo de aportes desde el proceso 215
     */
    private Boolean conciliado;
    
    /**
     * Número de planilla referenciada en el aporte manual para efectos de reporte normativo 
     * de pagos fuera de PILA para la UGPP
     * */
    private String numeroPlanillaManual;
    
    /**
     * Indicador que marca al aporte en proceso de reconocimiento
     * */
     private Boolean enProcesoReconocimiento;
     
     /**
     * Variable que indica el id de la cuentaBancariaRecaudo
     */
    private Integer cuentaBancariaRecaudo;
    
    private String cuentaBancariaRecaudoTexto;
    
	/**
	 * Método encargado de convertir de DTO a Entidad.
	 * 
	 * @return entidad convertida.
	 */
	public AporteGeneral convertToEntity() {
		AporteGeneral aporteGeneral = new AporteGeneral();
		
		aporteGeneral.setCuentaBancariaRecaudo(this.getCuentaBancariaRecaudo());
		aporteGeneral.setId(this.getId());
		aporteGeneral.setIdRegistroGeneral(this.getIdRegistroGeneral());
		aporteGeneral.setIdEmpresa(this.getIdEmpresa());
		aporteGeneral.setIdPersona(this.getIdPersona());
		aporteGeneral.setEstadoAportante(this.getEstadoAportante());
		aporteGeneral.setEstadoAporteAportante(this.getEstadoAporteAportante());
		aporteGeneral.setEstadoRegistroAporteAportante(this.getEstadoRegistroAporteAportante());
		aporteGeneral.setIdSucursalEmpresa(this.getIdSucursalEmpresa());
		aporteGeneral.setPagadorPorTerceros(this.getPagadorPorTerceros());
		aporteGeneral.setPeriodoAporte(this.getPeriodoAporte());
		aporteGeneral.setValorTotalAporteObligatorio(this.getValorTotalAporteObligatorio());
		aporteGeneral.setValorInteresesMora(this.getValorInteresesMora()!=null?this.getValorInteresesMora():BigDecimal.ZERO);
		
		if (this.getFechaRecaudo() != null) {
			aporteGeneral.setFechaRecaudo(new Date(this.getFechaRecaudo()));
		}

		if (this.getFechaProcesamiento() != null) {
			aporteGeneral.setFechaProcesamiento(new Date(this.getFechaProcesamiento()));
		}

		if (this.getFechaReconocimiento() != null) {
			aporteGeneral.setFechaReconocimiento(new Date(this.getFechaReconocimiento()));
		}

		aporteGeneral.setCodigoEntidadFinanciera(this.getCodigoEntidadFinanciera());
		aporteGeneral.setIdOperadorInformacion(this.getIdOperadorInformacion());
		aporteGeneral.setModalidadPlanilla(this.getModalidadPlanilla());
		aporteGeneral.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte());
		aporteGeneral.setAporteConDetalle(this.getAporteConDetalle());
		aporteGeneral.setNumeroCuenta(this.getNumeroCuenta());
		aporteGeneral.setTipoSolicitante(this.getTipoSolicitante());
		aporteGeneral.setOrigenAporte(this.getOrigenAporte());
		aporteGeneral.setIdCajaCompensacion(this.getIdCajaCompensacion());
		aporteGeneral.setEmailAportante(this.getEmailAportante());
		aporteGeneral.setEmpresaTramitadoraAporte(this.getEmpresaTramitadoraAporte());
		aporteGeneral.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte());
		aporteGeneral.setMarcaPeriodo(this.getMarcaPeriodo());
		aporteGeneral.setMarcaActualizacionCartera(this.getMarcaActualizacionCartera());
		aporteGeneral.setConciliado(this.getConciliado());
		aporteGeneral.setNumeroPlanillaManual(this.getNumeroPlanillaManual());
        aporteGeneral
                .setEnProcesoReconocimiento(this.getEnProcesoReconocimiento() != null ? this.getEnProcesoReconocimiento() : Boolean.FALSE);
		return aporteGeneral;
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param aporteGeneral
	 *            entidad a convertir.
	 */
	public void convertToDTO(AporteGeneral aporteGeneral) {
		this.setId(aporteGeneral.getId());
		this.setIdRegistroGeneral(aporteGeneral.getIdRegistroGeneral());
		this.setIdEmpresa(aporteGeneral.getIdEmpresa());
		this.setIdPersona(aporteGeneral.getIdPersona());
		this.setEstadoAportante(aporteGeneral.getEstadoAportante());
		this.setEstadoAporteAportante(aporteGeneral.getEstadoAporteAportante());
		this.setEstadoRegistroAporteAportante(aporteGeneral.getEstadoRegistroAporteAportante());
		this.setIdSucursalEmpresa(aporteGeneral.getIdSucursalEmpresa());
		this.setPagadorPorTerceros(aporteGeneral.getPagadorPorTerceros());
		this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
		this.setValorTotalAporteObligatorio(aporteGeneral.getValorTotalAporteObligatorio());
		this.setValorInteresesMora(aporteGeneral.getValorInteresesMora());
		this.setFechaRecaudo(aporteGeneral.getFechaRecaudo() != null ? aporteGeneral.getFechaRecaudo().getTime() : null);
		this.setFechaProcesamiento(aporteGeneral.getFechaProcesamiento() != null ? aporteGeneral.getFechaProcesamiento().getTime() : null);
		this.setFechaReconocimiento(aporteGeneral.getFechaReconocimiento() != null ? aporteGeneral.getFechaReconocimiento().getTime() : null);
		this.setCodigoEntidadFinanciera(aporteGeneral.getCodigoEntidadFinanciera());
		this.setIdOperadorInformacion(aporteGeneral.getIdOperadorInformacion());
		this.setModalidadPlanilla(aporteGeneral.getModalidadPlanilla());
		this.setModalidadRecaudoAporte(aporteGeneral.getModalidadRecaudoAporte());
		this.setAporteConDetalle(aporteGeneral.getAporteConDetalle());
		this.setNumeroCuenta(aporteGeneral.getNumeroCuenta());
		this.setTipoSolicitante(aporteGeneral.getTipoSolicitante());
		this.setOrigenAporte(aporteGeneral.getOrigenAporte());
		this.setIdCajaCompensacion(aporteGeneral.getIdCajaCompensacion());
		this.setEmailAportante(aporteGeneral.getEmailAportante());
		this.setEmpresaTramitadoraAporte(aporteGeneral.getEmpresaTramitadoraAporte());
		this.setFormaReconocimientoAporte(aporteGeneral.getFormaReconocimientoAporte());
		this.setMarcaPeriodo(aporteGeneral.getMarcaPeriodo());
		this.setMarcaActualizacionCartera(aporteGeneral.getMarcaActualizacionCartera());
		this.setConciliado(aporteGeneral.getConciliado());
		this.setNumeroPlanillaManual(aporteGeneral.getNumeroPlanillaManual());
                this.setCuentaBancariaRecaudo(aporteGeneral.getCuentaBancariaRecaudo());
        this.setEnProcesoReconocimiento(
                aporteGeneral.getEnProcesoReconocimiento() != null ? aporteGeneral.getEnProcesoReconocimiento() : Boolean.FALSE);
	}


	public void convertDTONative(
		Long id,
		Long idRegistroGeneral,
		Long idEmpresa,
		Long idPersona,
		String estadoAportante,
		String estadoAporteAportante,
		String estadoRegistroAporteAportante,
		Long idSucursalEmpresa,
		Boolean pagadorPorTerceros,
		String periodoAporte,
		BigDecimal valorTotalAporteObligatorio,
		BigDecimal valorInteresesMora,
		Date fechaRecaudo,
		Date fechaProcesamiento,
		Date fechaReconocimiento,
		Short codigoEntidadFinanciera,
		Long idOperadorInformacion,
		String modalidadPlanilla,
		String modalidadRecaudoAporte,
		Boolean aporteConDetalle,
		String numeroCuenta,
		String tipoSolicitante,
		String origenAporte,
		Integer idCajaCompensacion,
		String emailAportante,
		Long empresaTramitadoraAporte,
		String formaReconocimientoAporte,
		String marcaPeriodo,
		Boolean marcaActualizacionCartera,
		Boolean conciliado,
		String numeroPlanillaManual,
		Integer cuentaBancariaRecaudo,
		Boolean enProcesoReconocimiento
	) {

		this.setId(id);
		this.setIdRegistroGeneral(idRegistroGeneral);
		this.setIdEmpresa(idEmpresa);
		this.setIdPersona(idPersona);
		this.setEstadoAportante(estadoAportante != null ? EstadoEmpleadorEnum.valueOf(estadoAportante) : null);
		this.setEstadoAporteAportante(estadoAporteAportante != null ? EstadoAporteEnum.valueOf(estadoAporteAportante) : null);
		this.setEstadoRegistroAporteAportante(estadoRegistroAporteAportante != null ? EstadoRegistroAporteEnum.valueOf(estadoRegistroAporteAportante) : null);
		this.setIdSucursalEmpresa(idSucursalEmpresa);
		this.setPagadorPorTerceros(pagadorPorTerceros);
		this.setPeriodoAporte(periodoAporte);
		this.setValorTotalAporteObligatorio(valorTotalAporteObligatorio);
		this.setValorInteresesMora(valorInteresesMora);
		this.setFechaRecaudo(fechaRecaudo != null ? fechaRecaudo.getTime() : null);
		this.setFechaProcesamiento(fechaProcesamiento != null ? fechaProcesamiento.getTime() : null);
		this.setFechaReconocimiento(fechaReconocimiento != null ? fechaReconocimiento.getTime() : null);
		this.setCodigoEntidadFinanciera(codigoEntidadFinanciera);
		this.setIdOperadorInformacion(idOperadorInformacion);
		this.setModalidadPlanilla(modalidadPlanilla != null ? ModalidadPlanillaEnum.valueOf(modalidadPlanilla): null);
		this.setModalidadRecaudoAporte(modalidadRecaudoAporte != null ? ModalidadRecaudoAporteEnum.valueOf(modalidadRecaudoAporte): null);
		this.setAporteConDetalle(aporteConDetalle);
		this.setNumeroCuenta(numeroCuenta);
		this.setTipoSolicitante(tipoSolicitante != null ? TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante) : null);
		this.setOrigenAporte(origenAporte != null ? OrigenAporteEnum.valueOf(origenAporte) : null);
		this.setIdCajaCompensacion(idCajaCompensacion);
		this.setEmailAportante(emailAportante);
		this.setEmpresaTramitadoraAporte(empresaTramitadoraAporte);
		this.setFormaReconocimientoAporte(formaReconocimientoAporte != null ? FormaReconocimientoAporteEnum.valueOf(formaReconocimientoAporte) : null);
		this.setMarcaPeriodo(marcaPeriodo != null ? MarcaPeriodoEnum.valueOf(marcaPeriodo) : null);
		this.setMarcaActualizacionCartera(marcaActualizacionCartera);
		this.setConciliado(conciliado);
		this.setNumeroPlanillaManual(numeroPlanillaManual);
		this.setCuentaBancariaRecaudo(cuentaBancariaRecaudo);
        this.setEnProcesoReconocimiento(enProcesoReconocimiento != null ? enProcesoReconocimiento : Boolean.FALSE);
		
	}

    /**
     * Método constructor que recibe un aporte general
     * 
     * @param aporteGeneral
     */
    public AporteGeneralModeloDTO(AporteGeneral aporteGeneral) {
        super();
        convertToDTO(aporteGeneral);
    }

	/**
	 * Método constructor que recibe un aporte general y la informacion para
	 * determinar si tiene modificaciones
	 * 
	 * @param aporteGeneral
	 * @param cantidadModificaciones
	 */
	public AporteGeneralModeloDTO(AporteGeneral aporteGeneral, Long cantidadModificaciones) {
		convertToDTO(aporteGeneral);

		if (cantidadModificaciones > 0) {
			this.tieneModificaciones = true;
		} else {
			this.tieneModificaciones = false;
		}

	}

	
	public AporteGeneralModeloDTO(
		Long id,
		Long idRegistroGeneral,
		Long idEmpresa,
		Long idPersona,
		String estadoAportante,
		String estadoAporteAportante,
		String estadoRegistroAporteAportante,
		Long idSucursalEmpresa,
		Boolean pagadorPorTerceros,
		String periodoAporte,
		BigDecimal valorTotalAporteObligatorio,
		BigDecimal valorInteresesMora,
		Date fechaRecaudo,
		Date fechaProcesamiento,
		Date fechaReconocimiento,
		Short codigoEntidadFinanciera,
		Long idOperadorInformacion,
		String modalidadPlanilla,
		String modalidadRecaudoAporte,
		Boolean aporteConDetalle,
		String numeroCuenta,
		String tipoSolicitante,
		String origenAporte,
		Integer idCajaCompensacion,
		String emailAportante,
		Long empresaTramitadoraAporte,
		String formaReconocimientoAporte,
		String marcaPeriodo,
		Boolean marcaActualizacionCartera,
		Boolean conciliado,
		String numeroPlanillaManual,
		Integer cuentaBancariaRecaudo,
		Boolean enProcesoReconocimiento,
		Integer cantidadModificaciones,
		String cuenta
		) {

		convertDTONative(
			id,
			idRegistroGeneral,
			idEmpresa,
			idPersona,
			estadoAportante,
			estadoAporteAportante,
			estadoRegistroAporteAportante,
			idSucursalEmpresa,
			pagadorPorTerceros,
			periodoAporte,
			valorTotalAporteObligatorio,
			valorInteresesMora,
			fechaRecaudo,
			fechaProcesamiento,
			fechaReconocimiento,
			codigoEntidadFinanciera,
			idOperadorInformacion,
			modalidadPlanilla,
			modalidadRecaudoAporte,
			aporteConDetalle,
			numeroCuenta,
			tipoSolicitante,
			origenAporte,
			idCajaCompensacion,
			emailAportante,
			empresaTramitadoraAporte,
			formaReconocimientoAporte,
			marcaPeriodo,
			marcaActualizacionCartera,
			conciliado,
			numeroPlanillaManual,
			cuentaBancariaRecaudo,
			enProcesoReconocimiento
		);
		this.setCuentaBancariaRecaudoTexto(cuenta);

		if (cantidadModificaciones > 0) {
			this.tieneModificaciones = true;
		} else {
			this.tieneModificaciones = false;
		}

	}
	

	/**
	 * Método constructor
	 */
	public AporteGeneralModeloDTO() {
	}

        /**
     * Método encargado de copiar un DTO a una Entidad.
     *
     * @param aporteGeneral previamente consultado.
     * @return aporteGeneral aporte modificado con los datos del DTO.
     */
    public AporteGeneral copyDTOToEntiy(AporteGeneral aporteGeneral) {
        if (this.getId() != null) {
            aporteGeneral.setId(this.getId());
        }
        if (this.getIdRegistroGeneral() != null) {
            aporteGeneral.setIdRegistroGeneral(this.getIdRegistroGeneral());
        }
        if (this.getIdEmpresa() != null) {
            aporteGeneral.setIdEmpresa(this.getIdEmpresa());
        }
        if (this.getIdPersona() != null) {
            aporteGeneral.setIdPersona(this.getIdPersona());
        }
        if (this.getEstadoAportante() != null) {
            aporteGeneral.setEstadoAportante(this.getEstadoAportante());
        }
        if (this.getEstadoAporteAportante() != null) {
            aporteGeneral.setEstadoAporteAportante(this.getEstadoAporteAportante());
        }
        if (this.getEstadoRegistroAporteAportante() != null) {
            aporteGeneral.setEstadoRegistroAporteAportante(this.getEstadoRegistroAporteAportante());
        }
        if (this.getIdSucursalEmpresa() != null) {
            aporteGeneral.setIdSucursalEmpresa(this.getIdSucursalEmpresa());
        }
        if (this.getPagadorPorTerceros() != null) {
            aporteGeneral.setPagadorPorTerceros(this.getPagadorPorTerceros());
        }
        if (this.getPeriodoAporte() != null) {
            aporteGeneral.setPeriodoAporte(this.getPeriodoAporte());
        }
        if (this.getValorTotalAporteObligatorio() != null) {
            aporteGeneral.setValorTotalAporteObligatorio(this.getValorTotalAporteObligatorio());
        }
        if (this.getValorInteresesMora() != null) {
            aporteGeneral.setValorInteresesMora(this.getValorInteresesMora());
        }
        if (this.getFechaRecaudo() != null) {
            aporteGeneral.setFechaRecaudo(new Date(this.getFechaRecaudo()));
        }
        if (this.getFechaProcesamiento() != null) {
            aporteGeneral.setFechaProcesamiento(new Date(this.getFechaProcesamiento()));
        }
        if (this.getFechaReconocimiento() != null) {
            aporteGeneral.setFechaReconocimiento(new Date(this.getFechaReconocimiento()));
        }
        if (this.getCodigoEntidadFinanciera() != null) {
            aporteGeneral.setCodigoEntidadFinanciera(this.getCodigoEntidadFinanciera());
        }
        if (this.getIdOperadorInformacion() != null) {
            aporteGeneral.setIdOperadorInformacion(this.getIdOperadorInformacion());
        }
        if (this.getModalidadPlanilla() != null) {
            aporteGeneral.setModalidadPlanilla(this.getModalidadPlanilla());
        }
        if (this.getModalidadRecaudoAporte() != null) {
            aporteGeneral.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte());
        }
        if (this.getAporteConDetalle() != null) {
            aporteGeneral.setAporteConDetalle(this.getAporteConDetalle());
        }
        if (this.getNumeroCuenta() != null) {
            aporteGeneral.setNumeroCuenta(this.getNumeroCuenta());
        }
        if (this.getTipoSolicitante() != null) {
            aporteGeneral.setTipoSolicitante(this.getTipoSolicitante());
        }
        if (this.getEmailAportante() != null) {
            aporteGeneral.setEmailAportante(this.getEmailAportante());
        }
        if (this.getEmpresaTramitadoraAporte() != null) {
            aporteGeneral.setEmpresaTramitadoraAporte(this.getEmpresaTramitadoraAporte());
        }
        if (this.getFormaReconocimientoAporte() != null) {
            aporteGeneral.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte());
        }
        if (this.getMarcaPeriodo() != null) {
            aporteGeneral.setMarcaPeriodo(this.getMarcaPeriodo());
        }
        if (this.getMarcaActualizacionCartera() != null) {
            aporteGeneral.setMarcaActualizacionCartera(this.getMarcaActualizacionCartera());
        }
        if (this.getConciliado() != null) {
            aporteGeneral.setConciliado(this.getConciliado());
        }
        if (this.getNumeroPlanillaManual() != null) {
            aporteGeneral.setNumeroPlanillaManual(this.getNumeroPlanillaManual());
        }
        if (this.getCuentaBancariaRecaudo() != null) {
            aporteGeneral.setCuentaBancariaRecaudo(this.getCuentaBancariaRecaudo());
        }
        return aporteGeneral;
    }

	/**
	 * Obtiene el valor de id
	 * 
	 * @return El valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id
	 * 
	 * @param id
	 *            El valor de id por asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de idRegistroGeneral
	 * 
	 * @return El valor de idRegistroGeneral
	 */
	public Long getIdRegistroGeneral() {
		return idRegistroGeneral;
	}

	/**
	 * Establece el valor de idRegistroGeneral
	 * 
	 * @param idRegistroGeneral
	 *            El valor de idRegistroGeneral por asignar
	 */
	public void setIdRegistroGeneral(Long idRegistroGeneral) {
		this.idRegistroGeneral = idRegistroGeneral;
	}

	/**
	 * Obtiene el valor de idEmpresa
	 * 
	 * @return El valor de idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Establece el valor de idEmpresa
	 * 
	 * @param idEmpresa
	 *            El valor de idEmpresa por asignar
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * Obtiene el valor de idPersona
	 * 
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Establece el valor de idPersona
	 * 
	 * @param idPersona
	 *            El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Obtiene el valor de estadoAportante
	 * 
	 * @return El valor de estadoAportante
	 */
	public EstadoEmpleadorEnum getEstadoAportante() {
		return estadoAportante;
	}

	/**
	 * Establece el valor de estadoAportante
	 * 
	 * @param estadoAportante
	 *            El valor de estadoAportante por asignar
	 */
	public void setEstadoAportante(EstadoEmpleadorEnum estadoAportante) {
		this.estadoAportante = estadoAportante;
	}

	/**
	 * Obtiene el valor de estadoAporteAportante
	 * 
	 * @return El valor de estadoAporteAportante
	 */
	public EstadoAporteEnum getEstadoAporteAportante() {
		return estadoAporteAportante;
	}

	/**
	 * Establece el valor de estadoAporteAportante
	 * 
	 * @param estadoAporteAportante
	 *            El valor de estadoAporteAportante por asignar
	 */
	public void setEstadoAporteAportante(EstadoAporteEnum estadoAporteAportante) {
		this.estadoAporteAportante = estadoAporteAportante;
	}

	/**
	 * Obtiene el valor de estadoRegistroAporteAportante
	 * 
	 * @return El valor de estadoRegistroAporteAportante
	 */
	public EstadoRegistroAporteEnum getEstadoRegistroAporteAportante() {
		return estadoRegistroAporteAportante;
	}

	/**
	 * Establece el valor de estadoRegistroAporteAportante
	 * 
	 * @param estadoRegistroAporteAportante
	 *            El valor de estadoRegistroAporteAportante por asignar
	 */
	public void setEstadoRegistroAporteAportante(EstadoRegistroAporteEnum estadoRegistroAporteAportante) {
		this.estadoRegistroAporteAportante = estadoRegistroAporteAportante;
	}

	/**
	 * Obtiene el valor de idSucursalEmpresa
	 * 
	 * @return El valor de idSucursalEmpresa
	 */
	public Long getIdSucursalEmpresa() {
		return idSucursalEmpresa;
	}

	/**
	 * Establece el valor de idSucursalEmpresa
	 * 
	 * @param idSucursalEmpresa
	 *            El valor de idSucursalEmpresa por asignar
	 */
	public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
		this.idSucursalEmpresa = idSucursalEmpresa;
	}

	/**
	 * Obtiene el valor de pagadorPorTerceros
	 * 
	 * @return El valor de pagadorPorTerceros
	 */
	public Boolean getPagadorPorTerceros() {
		return pagadorPorTerceros;
	}

	/**
	 * Establece el valor de pagadorPorTerceros
	 * 
	 * @param pagadorPorTerceros
	 *            El valor de pagadorPorTerceros por asignar
	 */
	public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
		this.pagadorPorTerceros = pagadorPorTerceros;
	}

	/**
	 * Obtiene el valor de periodoAporte
	 * 
	 * @return El valor de periodoAporte
	 */
	public String getPeriodoAporte() {
		return periodoAporte;
	}

	/**
	 * Establece el valor de periodoAporte
	 * 
	 * @param periodoAporte
	 *            El valor de periodoAporte por asignar
	 */
	public void setPeriodoAporte(String periodoAporte) {
		this.periodoAporte = periodoAporte;
	}

	/**
	 * Obtiene el valor de valorTotalAporteObligatorio
	 * 
	 * @return El valor de valorTotalAporteObligatorio
	 */
	public BigDecimal getValorTotalAporteObligatorio() {
		return valorTotalAporteObligatorio;
	}

	/**
	 * Establece el valor de valorTotalAporteObligatorio
	 * 
	 * @param valorTotalAporteObligatorio
	 *            El valor de valorTotalAporteObligatorio por asignar
	 */
	public void setValorTotalAporteObligatorio(BigDecimal valorTotalAporteObligatorio) {
		this.valorTotalAporteObligatorio = valorTotalAporteObligatorio;
	}

	/**
	 * Obtiene el valor de valorInteresesMora
	 * 
	 * @return El valor de valorInteresesMora
	 */
	public BigDecimal getValorInteresesMora() {
		return valorInteresesMora;
	}

	/**
	 * Establece el valor de valorInteresesMora
	 * 
	 * @param valorInteresesMora
	 *            El valor de valorInteresesMora por asignar
	 */
	public void setValorInteresesMora(BigDecimal valorInteresesMora) {
		this.valorInteresesMora = valorInteresesMora;
	}

	/**
	 * Obtiene el valor de fechaRecaudo
	 * 
	 * @return El valor de fechaRecaudo
	 */
	public Long getFechaRecaudo() {
		return fechaRecaudo;
	}

	/**
	 * Establece el valor de fechaRecaudo
	 * 
	 * @param fechaRecaudo
	 *            El valor de fechaRecaudo por asignar
	 */
	public void setFechaRecaudo(Long fechaRecaudo) {
		this.fechaRecaudo = fechaRecaudo;
	}

	/**
	 * Obtiene el valor de fechaProcesamiento
	 * 
	 * @return El valor de fechaProcesamiento
	 */
	public Long getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	/**
	 * Establece el valor de fechaProcesamiento
	 * 
	 * @param fechaProcesamiento
	 *            El valor de fechaProcesamiento por asignar
	 */
	public void setFechaProcesamiento(Long fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	/**
	 * Obtiene el valor de codigoEntidadFinanciera
	 * 
	 * @return El valor de codigoEntidadFinanciera
	 */
	public Short getCodigoEntidadFinanciera() {
		return codigoEntidadFinanciera;
	}

	/**
	 * Establece el valor de codigoEntidadFinanciera
	 * 
	 * @param codigoEntidadFinanciera
	 *            El valor de codigoEntidadFinanciera por asignar
	 */
	public void setCodigoEntidadFinanciera(Short codigoEntidadFinanciera) {
		this.codigoEntidadFinanciera = codigoEntidadFinanciera;
	}

	/**
	 * Obtiene el valor de idOperadorInformacion
	 * 
	 * @return El valor de idOperadorInformacion
	 */
	public Long getIdOperadorInformacion() {
		return idOperadorInformacion;
	}

	/**
	 * Establece el valor de idOperadorInformacion
	 * 
	 * @param idOperadorInformacion
	 *            El valor de idOperadorInformacion por asignar
	 */
	public void setIdOperadorInformacion(Long idOperadorInformacion) {
		this.idOperadorInformacion = idOperadorInformacion;
	}

	/**
	 * Obtiene el valor de modalidadPlanilla
	 * 
	 * @return El valor de modalidadPlanilla
	 */
	public ModalidadPlanillaEnum getModalidadPlanilla() {
		return modalidadPlanilla;
	}

	/**
	 * Establece el valor de modalidadPlanilla
	 * 
	 * @param modalidadPlanilla
	 *            El valor de modalidadPlanilla por asignar
	 */
	public void setModalidadPlanilla(ModalidadPlanillaEnum modalidadPlanilla) {
		this.modalidadPlanilla = modalidadPlanilla;
	}

	/**
	 * Obtiene el valor de modalidadRecaudoAporte
	 * 
	 * @return El valor de modalidadRecaudoAporte
	 */
	public ModalidadRecaudoAporteEnum getModalidadRecaudoAporte() {
		return modalidadRecaudoAporte;
	}

	/**
	 * Establece el valor de modalidadRecaudoAporte
	 * 
	 * @param modalidadRecaudoAporte
	 *            El valor de modalidadRecaudoAporte por asignar
	 */
	public void setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum modalidadRecaudoAporte) {
		this.modalidadRecaudoAporte = modalidadRecaudoAporte;
	}

	/**
	 * Obtiene el valor de aporteConDetalle
	 * 
	 * @return El valor de aporteConDetalle
	 */
	public Boolean getAporteConDetalle() {
		return aporteConDetalle;
	}

	/**
	 * Establece el valor de aporteConDetalle
	 * 
	 * @param aporteConDetalle
	 *            El valor de aporteConDetalle por asignar
	 */
	public void setAporteConDetalle(Boolean aporteConDetalle) {
		this.aporteConDetalle = aporteConDetalle;
	}

	/**
	 * Obtiene el valor de numeroCuenta
	 * 
	 * @return El valor de numeroCuenta
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Establece el valor de numeroCuenta
	 * 
	 * @param numeroCuenta
	 *            El valor de numeroCuenta por asignar
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * Obtiene el valor de tipoSolicitante
	 * 
	 * @return El valor de tipoSolicitante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Establece el valor de tipoSolicitante
	 * 
	 * @param tipoSolicitante
	 *            El valor de tipoSolicitante por asignar
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Obtiene el valor de tieneModificaciones
	 * 
	 * @return El valor de tieneModificaciones
	 */
	public Boolean getTieneModificaciones() {
		return tieneModificaciones;
	}

	/**
	 * Establece el valor de tieneModificaciones
	 * 
	 * @param tieneModificaciones
	 *            El valor de tieneModificaciones por asignar
	 */
	public void setTieneModificaciones(Boolean tieneModificaciones) {
		this.tieneModificaciones = tieneModificaciones;
	}

	/**
	 * Obtiene el valor de origenAporte
	 * 
	 * @return El valor de origenAporte
	 */
	public OrigenAporteEnum getOrigenAporte() {
		return origenAporte;
	}

	/**
	 * Establece el valor de origenAporte
	 * 
	 * @param origenAporte
	 *            El valor de origenAporte por asignar
	 */
	public void setOrigenAporte(OrigenAporteEnum origenAporte) {
		this.origenAporte = origenAporte;
	}

	/**
	 * Obtiene el valor de idCajaCompensacion
	 * 
	 * @return El valor de idCajaCompensacion
	 */
	public Integer getIdCajaCompensacion() {
		return idCajaCompensacion;
	}

	/**
	 * Establece el valor de idCajaCompensacion
	 * 
	 * @param idCajaCompensacion
	 *            El valor de idCajaCompensacion por asignar
	 */
	public void setIdCajaCompensacion(Integer idCajaCompensacion) {
		this.idCajaCompensacion = idCajaCompensacion;
	}

	/**
	 * Obtiene el valor de emailAportante
	 * 
	 * @return El valor de emailAportante
	 */
	public String getEmailAportante() {
		return emailAportante;
	}

	/**
	 * Establece el valor de emailAportante
	 * 
	 * @param emailAportante
	 *            El valor de emailAportante por asignar
	 */
	public void setEmailAportante(String emailAportante) {
		this.emailAportante = emailAportante;
	}

	/**
	 * Obtiene el valor de empresaTramitadoraAporte
	 * 
	 * @return El valor de empresaTramitadoraAporte
	 */
	public Long getEmpresaTramitadoraAporte() {
		return empresaTramitadoraAporte;
	}

	/**
	 * Establece el valor de empresaTramitadoraAporte
	 * 
	 * @param empresaTramitadoraAporte
	 *            El valor de empresaTramitadoraAporte por asignar
	 */
	public void setEmpresaTramitadoraAporte(Long empresaTramitadoraAporte) {
		this.empresaTramitadoraAporte = empresaTramitadoraAporte;
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
     * @return the marcaPeriodo
     */
    public MarcaPeriodoEnum getMarcaPeriodo() {
        return marcaPeriodo;
    }

    /**
     * @param marcaPeriodo the marcaPeriodo to set
     */
    public void setMarcaPeriodo(MarcaPeriodoEnum marcaPeriodo) {
        this.marcaPeriodo = marcaPeriodo;
    }

    /**
     * @return the marcaActualizacionCartera
     */
    public Boolean getMarcaActualizacionCartera() {
        return marcaActualizacionCartera;
    }

    /**
     * @param marcaActualizacionCartera the marcaActualizacionCartera to set
     */
    public void setMarcaActualizacionCartera(Boolean marcaActualizacionCartera) {
        this.marcaActualizacionCartera = marcaActualizacionCartera;
    }

    /**
     * @return the conciliado
     */
    public Boolean getConciliado() {
        return conciliado;
    }

    /**
     * @param conciliado the conciliado to set
     */
    public void setConciliado(Boolean conciliado) {
        this.conciliado = conciliado;
    }

    /**
     * @return the numeroPlanillaManual
     */
    public String getNumeroPlanillaManual() {
        return numeroPlanillaManual;
    }

    /**
     * @param numeroPlanillaManual the numeroPlanillaManual to set
     */
    public void setNumeroPlanillaManual(String numeroPlanillaManual) {
        this.numeroPlanillaManual = numeroPlanillaManual;
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

    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }

    public String getCuentaBancariaRecaudoTexto() {
        return cuentaBancariaRecaudoTexto;
    }

    public void setCuentaBancariaRecaudoTexto(String cuentaBancariaRecaudoTexto) {
        this.cuentaBancariaRecaudoTexto = cuentaBancariaRecaudoTexto;
    }


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", idRegistroGeneral='" + getIdRegistroGeneral() + "'" +
			", idEmpresa='" + getIdEmpresa() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", estadoAportante='" + getEstadoAportante() + "'" +
			", estadoAporteAportante='" + getEstadoAporteAportante() + "'" +
			", estadoRegistroAporteAportante='" + getEstadoRegistroAporteAportante() + "'" +
			", idSucursalEmpresa='" + getIdSucursalEmpresa() + "'" +
			", pagadorPorTerceros='" + getPagadorPorTerceros() + "'" +
			", periodoAporte='" + getPeriodoAporte() + "'" +
			", valorTotalAporteObligatorio='" + getValorTotalAporteObligatorio() + "'" +
			", valorInteresesMora='" + getValorInteresesMora() + "'" +
			", fechaRecaudo='" + getFechaRecaudo() + "'" +
			", fechaProcesamiento='" + getFechaProcesamiento() + "'" +
			", codigoEntidadFinanciera='" + getCodigoEntidadFinanciera() + "'" +
			", idOperadorInformacion='" + getIdOperadorInformacion() + "'" +
			", modalidadPlanilla='" + getModalidadPlanilla() + "'" +
			", modalidadRecaudoAporte='" + getModalidadRecaudoAporte() + "'" +
			", aporteConDetalle='" + getAporteConDetalle() + "'" +
			", numeroCuenta='" + getNumeroCuenta() + "'" +
			", tipoSolicitante='" + getTipoSolicitante() + "'" +
			", tieneModificaciones='" + getTieneModificaciones() + "'" +
			", origenAporte='" + getOrigenAporte() + "'" +
			", idCajaCompensacion='" + getIdCajaCompensacion() + "'" +
			", emailAportante='" + getEmailAportante() + "'" +
			", empresaTramitadoraAporte='" + getEmpresaTramitadoraAporte() + "'" +
			", formaReconocimientoAporte='" + getFormaReconocimientoAporte() + "'" +
			", fechaReconocimiento='" + getFechaReconocimiento() + "'" +
			", marcaPeriodo='" + getMarcaPeriodo() + "'" +
			", marcaActualizacionCartera='" + getMarcaActualizacionCartera() + "'" +
			", conciliado='" + getConciliado() + "'" +
			", numeroPlanillaManual='" + getNumeroPlanillaManual() + "'" +
			", enProcesoReconocimiento='" + getEnProcesoReconocimiento() + "'" +
			", cuentaBancariaRecaudo='" + getCuentaBancariaRecaudo() + "'" +
			", cuentaBancariaRecaudoTexto='" + getCuentaBancariaRecaudoTexto() + "'" +
			"}";
	}

    
    
}