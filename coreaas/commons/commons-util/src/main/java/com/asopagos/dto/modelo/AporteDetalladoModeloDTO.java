package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;

/**
 * DTO que contiene los campos de un AportanteDetallado.
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class AporteDetalladoModeloDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Código identificador de llave primaria llamada No. de operación de
	 * recaudo
	 */
	private Long id;

	/**
	 * Referencia al aporte general
	 */
	@NotNull
	private Long idAporteGeneral;

	/**
	 * Referencia al registro general del aporte (staging)
	 */
	private Long idRegistroDetallado;

	/**
	 * Referencia a la persona asociada como cotizante
	 */
	private Long idPersona;

	/**
	 * Descripción del tipo de cotizante
	 */
	private TipoAfiliadoEnum tipoCotizante;

	/**
	 * Descripción del estado del cotizante inicial
	 */
	private EstadoAfiliadoEnum estadoCotizante;

	/**
	 * Descripción del estado del aporte a nivel de cotizante
	 */
	private EstadoAporteEnum estadoAporteCotizante;

	/**
	 * Descripción del estado del registro a nivel de cotizante
	 */
	private EstadoRegistroAporteEnum estadoRegistroAporteCotizante;

	/**
	 * Referencia al cotizante
	 */
	private Long idCotizante;

	/**
	 * Número de días cotizados
	 */
	private Short diasCotizados;

	/**
	 * Número de horas laboradas
	 */
	private Short horasLaboradas;

	/**
	 * Salario básico
	 */
	private BigDecimal salarioBasico;

	/**
	 * Municipio Laboral
	 */
	private String municipioLaboral;

	/**
	 * Departamento Laboral
	 */
	private Short departamentoLaboral;

	/**
	 * Ingreso Base Cotización (IBC)
	 */
	private BigDecimal valorIBC;

	/**
	 * Tarifa
	 */
	private BigDecimal tarifa;

	/**
	 * Presenta Salario integral
	 */
	private Boolean salarioIntegral;

	/**
	 * Aporte obligatorio (sumatoria de Aporte obligatorio)
	 */
	private BigDecimal aporteObligatorio;

	/**
	 * Valor Saldo Aporte (se afecta cuando se presenta un movimiento en el
	 * aporte sea un ajuste o devolución)
	 */
	private BigDecimal valorSaldoAporte;

	/**
	 * Valor de mora individual
	 */
	private BigDecimal valorMora;

	/**
	 * Contenido del Registro tipo 2 campo 29: Correcciones
	 */
	private String correcciones;

	/**
	 * Descripción del estado del aporte del recaudo
	 */
	private EstadoAporteEnum estadoAporteRecaudo;

	/**
	 * Descripción del estado del aporte para el ajuste
	 */
	private EstadoAporteEnum estadoAporteAjuste;

	/**
	 * Estado del registro del aporte en el procesamiento del archivo
	 */
	private EstadoRegistroAportesArchivoEnum estadoRegistroAporteArchivo;

	/**
	 * Indica el usuario que aprueba el registro del aporte.
	 */
	private String usuarioAprobadorAporte;

	/**
	 * Indica el código de la sucursal del cotizante
	 */
	private String codSucursal;

	/**
	 * Indica el nombre de la sucursal del cotizante
	 */
	private String nomSucursal;

	/**
	 * Fecha de registro del aporte
	 */
	private Date fechaCreacion;
    
    /**
     * Forma de reconocimiento del aporte detallado
     * */
    private FormaReconocimientoAporteEnum formaReconocimientoAporte;

	/**
	 * Fecha de movimiento/actualización del aporte
	 */
	private Long fechaMovimiento;

	/**
	 * Descripción del estado del registro a nivel de Cotizante o pensionado
	 */
	private EstadoRegistroAporteEnum estadoRegistroAporte;
	
	/**
     * Representa una marca en aporte detallado donde indica el periodo que se cancelo 
     * teniendo en cuenta la fecha actual (PERIODO_REGULAR, PERIODO_RETROACTIVO, PERIODO_FUTURO) 
     */
    private MarcaPeriodoEnum marcaPeriodo;
    
    /**
     *  
     *  
     */
    private ModalidadRecaudoAporteEnum modalidadRecaudoAporte;
    
    /**
     *  
     *  
     */
    private Boolean marcaCalculoCategoria;
    
	/**
     *  
     *  
     */
    private Long idRegistroDetalladoUltimo;

    /**
     * Constructor por defecto
     * */
    public AporteDetalladoModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public AporteDetalladoModeloDTO(AporteDetallado aporteDetallado){
        super();
        convertToDTO(aporteDetallado);
    }
    
	/**
	 * Método encargado de convertir de DTO a Entidad.
	 * 
	 * @param this
	 *            DTO a convertir.
	 * @return entidad convertida.
	 */
	public AporteDetallado convertToEntity() {
		AporteDetallado aporteDetallado = new AporteDetallado();
		aporteDetallado.setId(this.getId());
		aporteDetallado.setIdAporteGeneral(this.getIdAporteGeneral());
		aporteDetallado.setIdRegistroDetallado(this.getIdRegistroDetallado());
		aporteDetallado.setIdPersona(this.getIdPersona());
		aporteDetallado.setTipoCotizante(this.getTipoCotizante());
		aporteDetallado.setEstadoCotizante(this.getEstadoCotizante());
		aporteDetallado.setEstadoAporteCotizante(this.getEstadoAporteCotizante());
		aporteDetallado.setEstadoRegistroAporteCotizante(this.getEstadoRegistroAporteCotizante());
		aporteDetallado.setDiasCotizados(this.getDiasCotizados());
		aporteDetallado.setHorasLaboradas(this.getHorasLaboradas());
		aporteDetallado.setSalarioBasico(this.getSalarioBasico());
		aporteDetallado.setMunicipioLaboral(this.getMunicipioLaboral());
		aporteDetallado.setDepartamentoLaboral(this.getDepartamentoLaboral());
		aporteDetallado.setValorIBC(this.getValorIBC());
		aporteDetallado.setTarifa(this.getTarifa());
		aporteDetallado.setSalarioIntegral(this.getSalarioIntegral());
		aporteDetallado.setAporteObligatorio(this.getAporteObligatorio());
		aporteDetallado.setValorSaldoAporte(this.getValorSaldoAporte());
		aporteDetallado.setValorMora(this.getValorMora());
		aporteDetallado.setCorrecciones(this.getCorrecciones());
		aporteDetallado.setEstadoAporteRecaudo(this.getEstadoAporteRecaudo());
		aporteDetallado.setEstadoAporteAjuste(this.getEstadoAporteAjuste());
		aporteDetallado.setEstadoRegistroAporteArchivo(this.getEstadoRegistroAporteArchivo());
		aporteDetallado.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte());
		aporteDetallado.setCodSucursal(this.getCodSucursal());
		aporteDetallado.setNomSucursal(this.getNomSucursal());
		aporteDetallado.setFechaCreacion(this.getFechaCreacion());
		aporteDetallado.setMarcaPeriodo(this.getMarcaPeriodo());
		if (this.getFechaMovimiento() != null) {
			aporteDetallado.setFechaMovimiento(new Date(this.getFechaMovimiento()));
		}
		aporteDetallado.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte());
		aporteDetallado.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte());
		
		return aporteDetallado;
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param AportanteDetallado
	 *            entidad a convertir.
	 */
	public void convertToDTO(AporteDetallado aporteDetallado) {
		this.setId(aporteDetallado.getId());
		this.setIdAporteGeneral(aporteDetallado.getIdAporteGeneral());
		this.setIdRegistroDetallado(aporteDetallado.getIdRegistroDetallado());
		this.setIdPersona(aporteDetallado.getIdPersona());
		this.setTipoCotizante(aporteDetallado.getTipoCotizante());
		this.setEstadoCotizante(aporteDetallado.getEstadoCotizante());
		this.setEstadoAporteCotizante(aporteDetallado.getEstadoAporteCotizante());
		this.setEstadoRegistroAporteCotizante(aporteDetallado.getEstadoRegistroAporteCotizante());
		this.setDiasCotizados(aporteDetallado.getDiasCotizados());
		this.setHorasLaboradas(aporteDetallado.getHorasLaboradas());
		this.setSalarioBasico(aporteDetallado.getSalarioBasico());
		this.setMunicipioLaboral(aporteDetallado.getMunicipioLaboral());
		this.setDepartamentoLaboral(this.getDepartamentoLaboral());
		this.setValorIBC(aporteDetallado.getValorIBC());
		this.setTarifa(aporteDetallado.getTarifa());
		this.setSalarioIntegral(aporteDetallado.getSalarioIntegral());
		this.setAporteObligatorio(aporteDetallado.getAporteObligatorio());
		this.setValorSaldoAporte(aporteDetallado.getValorSaldoAporte());
		this.setValorMora(aporteDetallado.getValorMora());
		this.setCorrecciones(aporteDetallado.getCorrecciones());
		this.setEstadoAporteRecaudo(aporteDetallado.getEstadoAporteRecaudo());
		this.setEstadoAporteAjuste(aporteDetallado.getEstadoAporteAjuste());
		this.setEstadoRegistroAporteArchivo(aporteDetallado.getEstadoRegistroAporteArchivo());
		this.setUsuarioAprobadorAporte(aporteDetallado.getUsuarioAprobadorAporte());
		this.setCodSucursal(aporteDetallado.getCodSucursal());
		this.setNomSucursal(aporteDetallado.getNomSucursal());
		this.setFechaCreacion(aporteDetallado.getFechaCreacion());
		this.setMarcaPeriodo(aporteDetallado.getMarcaPeriodo());
		if (aporteDetallado.getFechaMovimiento() != null) {
			this.setFechaMovimiento(aporteDetallado.getFechaMovimiento().getTime());
		}
		this.setFormaReconocimientoAporte(aporteDetallado.getFormaReconocimientoAporte());
		this.setModalidadRecaudoAporte(aporteDetallado.getModalidadRecaudoAporte());
		this.setIdRegistroDetalladoUltimo(aporteDetallado.getIdRegistroDetalladoUltimo());
	}

	/**
	 * Método encargado de copiar un DTO a una Entidad.
	 * 
	 * @param AporteDetallado
	 *            previamente consultado.
	 */
	public AporteDetallado copyDTOToEntiy(AporteDetallado aporteDetallado) {
		if (this.getId() != null) {
			aporteDetallado.setId(this.getId());
		}
		if (this.getIdAporteGeneral() != null) {
			aporteDetallado.setIdAporteGeneral(this.getIdAporteGeneral());
		}
		if (this.getIdRegistroDetallado() != null) {
			aporteDetallado.setIdRegistroDetallado(this.getIdRegistroDetallado());
		}
		if (this.getIdPersona() != null) {
			aporteDetallado.setIdPersona(this.getIdPersona());
		}
		if (this.getTipoCotizante() != null) {
			aporteDetallado.setTipoCotizante(this.getTipoCotizante());
		}
		if (this.getEstadoCotizante() != null) {
			aporteDetallado.setEstadoCotizante(this.getEstadoCotizante());
		}
		if (this.getEstadoAporteCotizante() != null) {
			aporteDetallado.setEstadoAporteCotizante(this.getEstadoAporteCotizante());
		}
		if (this.getEstadoRegistroAporteCotizante() != null) {
			aporteDetallado.setEstadoRegistroAporteCotizante(this.getEstadoRegistroAporteCotizante());
		}
		if (this.getDiasCotizados() != null) {
			aporteDetallado.setDiasCotizados(this.getDiasCotizados());
		}
		if (this.getHorasLaboradas() != null) {
			aporteDetallado.setHorasLaboradas(this.getHorasLaboradas());
		}
		if (this.getSalarioBasico() != null) {
			aporteDetallado.setSalarioBasico(this.getSalarioBasico());
		}
		if (this.getMunicipioLaboral() != null) {
			aporteDetallado.setMunicipioLaboral(this.getMunicipioLaboral());
		}
		if (this.getDepartamentoLaboral() != null) {
			aporteDetallado.setDepartamentoLaboral(this.getDepartamentoLaboral());
		}
		if (this.getValorIBC() != null) {
			aporteDetallado.setValorIBC(this.getValorIBC());
		}
		if (this.getTarifa() != null) {
			aporteDetallado.setTarifa(this.getTarifa());
		}
		if (this.getSalarioIntegral() != null) {
			aporteDetallado.setSalarioIntegral(this.getSalarioIntegral());
		}
		if (this.getAporteObligatorio() != null) {
			aporteDetallado.setAporteObligatorio(this.getAporteObligatorio());
		}
		if (this.getValorSaldoAporte() != null) {
			aporteDetallado.setValorSaldoAporte(this.getValorSaldoAporte());
		}
		if (this.getValorMora() != null) {
			aporteDetallado.setValorMora(this.getValorMora());
		}
		if (this.getCorrecciones() != null) {
			aporteDetallado.setCorrecciones(this.getCorrecciones());
		}
		if (this.getEstadoAporteRecaudo() != null) {
			aporteDetallado.setEstadoAporteRecaudo(this.getEstadoAporteRecaudo());
		}
		if (this.getEstadoAporteAjuste() != null) {
			aporteDetallado.setEstadoAporteAjuste(this.getEstadoAporteAjuste());
		}
		if (this.getEstadoRegistroAporteArchivo() != null) {
			aporteDetallado.setEstadoRegistroAporteArchivo(this.getEstadoRegistroAporteArchivo());
		}
		if (this.getUsuarioAprobadorAporte() != null) {
			aporteDetallado.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte());
		}
		if (this.getUsuarioAprobadorAporte() != null) {
			aporteDetallado.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte());
		}
		if (this.getCodSucursal() != null) {
			aporteDetallado.setCodSucursal(this.getCodSucursal());
		}
		if (this.getNomSucursal() != null) {
			aporteDetallado.setNomSucursal(this.getNomSucursal());
		}
		if (this.getFechaCreacion() != null) {
			aporteDetallado.setFechaCreacion(this.getFechaCreacion());
		}
		if (this.getFechaMovimiento() != null) {
			aporteDetallado.setFechaMovimiento(new Date(this.getFechaMovimiento()));
		}
		if (this.getMarcaPeriodo() != null) {
            aporteDetallado.setMarcaPeriodo(this.getMarcaPeriodo());
        }
		if (this.getFormaReconocimientoAporte() != null) {
		    aporteDetallado.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte());
		}
		return aporteDetallado;
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
	 * Obtiene el valor de idAporteGeneral
	 * 
	 * @return El valor de idAporteGeneral
	 */
	public Long getIdAporteGeneral() {
		return idAporteGeneral;
	}

	/**
	 * Establece el valor de idAporteGeneral
	 * 
	 * @param idAporteGeneral
	 *            El valor de idAporteGeneral por asignar
	 */
	public void setIdAporteGeneral(Long idAporteGeneral) {
		this.idAporteGeneral = idAporteGeneral;
	}

	/**
	 * Obtiene el valor de idRegistroDetallado
	 * 
	 * @return El valor de idRegistroDetallado
	 */
	public Long getIdRegistroDetallado() {
		return idRegistroDetallado;
	}

	/**
	 * Establece el valor de idRegistroDetallado
	 * 
	 * @param idRegistroDetallado
	 *            El valor de idRegistroDetallado por asignar
	 */
	public void setIdRegistroDetallado(Long idRegistroDetallado) {
		this.idRegistroDetallado = idRegistroDetallado;
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
	 * Obtiene el valor de tipoCotizante
	 * 
	 * @return El valor de tipoCotizante
	 */
	public TipoAfiliadoEnum getTipoCotizante() {
		return tipoCotizante;
	}

	/**
	 * Establece el valor de tipoCotizante
	 * 
	 * @param tipoCotizante
	 *            El valor de tipoCotizante por asignar
	 */
	public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
		this.tipoCotizante = tipoCotizante;
	}

	/**
	 * Obtiene el valor de estadoCotizante
	 * 
	 * @return El valor de estadoCotizante
	 */
	public EstadoAfiliadoEnum getEstadoCotizante() {
		return estadoCotizante;
	}

	/**
	 * Establece el valor de estadoCotizante
	 * 
	 * @param estadoCotizante
	 *            El valor de estadoCotizante por asignar
	 */
	public void setEstadoCotizante(EstadoAfiliadoEnum estadoCotizante) {
		this.estadoCotizante = estadoCotizante;
	}

	/**
	 * Obtiene el valor de estadoAporteCotizante
	 * 
	 * @return El valor de estadoAporteCotizante
	 */
	public EstadoAporteEnum getEstadoAporteCotizante() {
		return estadoAporteCotizante;
	}

	/**
	 * Establece el valor de estadoAporteCotizante
	 * 
	 * @param estadoAporteCotizante
	 *            El valor de estadoAporteCotizante por asignar
	 */
	public void setEstadoAporteCotizante(EstadoAporteEnum estadoAporteCotizante) {
		this.estadoAporteCotizante = estadoAporteCotizante;
	}

	/**
	 * Obtiene el valor de estadoRegistroAporteCotizante
	 * 
	 * @return El valor de estadoRegistroAporteCotizante
	 */
	public EstadoRegistroAporteEnum getEstadoRegistroAporteCotizante() {
		return estadoRegistroAporteCotizante;
	}

	/**
	 * Establece el valor de estadoRegistroAporteCotizante
	 * 
	 * @param estadoRegistroAporteCotizante
	 *            El valor de estadoRegistroAporteCotizante por asignar
	 */
	public void setEstadoRegistroAporteCotizante(EstadoRegistroAporteEnum estadoRegistroAporteCotizante) {
		this.estadoRegistroAporteCotizante = estadoRegistroAporteCotizante;
	}

	/**
	 * Obtiene el valor de idCotizante
	 * 
	 * @return El valor de idCotizante
	 */
	public Long getIdCotizante() {
		return idCotizante;
	}

	/**
	 * Establece el valor de idCotizante
	 * 
	 * @param idCotizante
	 *            El valor de idCotizante por asignar
	 */
	public void setIdCotizante(Long idCotizante) {
		this.idCotizante = idCotizante;
	}

	/**
	 * Obtiene el valor de diasCotizados
	 * 
	 * @return El valor de diasCotizados
	 */
	public Short getDiasCotizados() {
		return diasCotizados;
	}

	/**
	 * Establece el valor de diasCotizados
	 * 
	 * @param diasCotizados
	 *            El valor de diasCotizados por asignar
	 */
	public void setDiasCotizados(Short diasCotizados) {
		this.diasCotizados = diasCotizados;
	}

	/**
	 * Obtiene el valor de horasLaboradas
	 * 
	 * @return El valor de horasLaboradas
	 */
	public Short getHorasLaboradas() {
		return horasLaboradas;
	}

	/**
	 * Establece el valor de horasLaboradas
	 * 
	 * @param horasLaboradas
	 *            El valor de horasLaboradas por asignar
	 */
	public void setHorasLaboradas(Short horasLaboradas) {
		this.horasLaboradas = horasLaboradas;
	}

	/**
	 * Obtiene el valor de salarioBasico
	 * 
	 * @return El valor de salarioBasico
	 */
	public BigDecimal getSalarioBasico() {
		return salarioBasico;
	}

	/**
	 * Establece el valor de salarioBasico
	 * 
	 * @param salarioBasico
	 *            El valor de salarioBasico por asignar
	 */
	public void setSalarioBasico(BigDecimal salarioBasico) {
		this.salarioBasico = salarioBasico;
	}

	/**
	 * Obtiene el valor de municipioLaboral
	 * 
	 * @return El valor de municipioLaboral
	 */
	public String getMunicipioLaboral() {
		return municipioLaboral;
	}

	/**
	 * Establece el valor de municipioLaboral
	 * 
	 * @param municipioLaboral
	 *            El valor de municipioLaboral por asignar
	 */
	public void setMunicipioLaboral(String municipioLaboral) {
		this.municipioLaboral = municipioLaboral;
	}

	/**
	 * Obtiene el valor de departamentoLaboral
	 * 
	 * @return El valor de departamentoLaboral
	 */
	public Short getDepartamentoLaboral() {
		return departamentoLaboral;
	}

	/**
	 * Establece el valor de departamentoLaboral
	 * 
	 * @param departamentoLaboral
	 *            El valor de departamentoLaboral por asignar
	 */
	public void setDepartamentoLaboral(Short departamentoLaboral) {
		this.departamentoLaboral = departamentoLaboral;
	}

	/**
	 * Obtiene el valor de valorIBC
	 * 
	 * @return El valor de valorIBC
	 */
	public BigDecimal getValorIBC() {
		return valorIBC;
	}

	/**
	 * Establece el valor de valorIBC
	 * 
	 * @param valorIBC
	 *            El valor de valorIBC por asignar
	 */
	public void setValorIBC(BigDecimal valorIBC) {
		this.valorIBC = valorIBC;
	}

	/**
	 * Obtiene el valor de tarifa
	 * 
	 * @return El valor de tarifa
	 */
	public BigDecimal getTarifa() {
		return tarifa;
	}

	/**
	 * Establece el valor de tarifa
	 * 
	 * @param tarifa
	 *            El valor de tarifa por asignar
	 */
	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Obtiene el valor de salarioIntegral
	 * 
	 * @return El valor de salarioIntegral
	 */
	public Boolean getSalarioIntegral() {
		return salarioIntegral;
	}

	/**
	 * Establece el valor de salarioIntegral
	 * 
	 * @param salarioIntegral
	 *            El valor de salarioIntegral por asignar
	 */
	public void setSalarioIntegral(Boolean salarioIntegral) {
		this.salarioIntegral = salarioIntegral;
	}

	/**
	 * Obtiene el valor de aporteObligatorio
	 * 
	 * @return El valor de aporteObligatorio
	 */
	public BigDecimal getAporteObligatorio() {
		return aporteObligatorio;
	}

	/**
	 * Establece el valor de aporteObligatorio
	 * 
	 * @param aporteObligatorio
	 *            El valor de aporteObligatorio por asignar
	 */
	public void setAporteObligatorio(BigDecimal aporteObligatorio) {
		this.aporteObligatorio = aporteObligatorio;
	}

	/**
	 * Obtiene el valor de valorSaldoAporte
	 * 
	 * @return El valor de valorSaldoAporte
	 */
	public BigDecimal getValorSaldoAporte() {
		return valorSaldoAporte;
	}

	/**
	 * Establece el valor de valorSaldoAporte
	 * 
	 * @param valorSaldoAporte
	 *            El valor de valorSaldoAporte por asignar
	 */
	public void setValorSaldoAporte(BigDecimal valorSaldoAporte) {
		this.valorSaldoAporte = valorSaldoAporte;
	}

	/**
	 * Obtiene el valor de valorMora
	 * 
	 * @return El valor de valorMora
	 */
	public BigDecimal getValorMora() {
		return valorMora;
	}

	/**
	 * Establece el valor de valorMora
	 * 
	 * @param valorMora
	 *            El valor de valorMora por asignar
	 */
	public void setValorMora(BigDecimal valorMora) {
		this.valorMora = valorMora;
	}

	/**
	 * Obtiene el valor de correcciones
	 * 
	 * @return El valor de correcciones
	 */
	public String getCorrecciones() {
		return correcciones;
	}

	/**
	 * Establece el valor de correcciones
	 * 
	 * @param correcciones
	 *            El valor de correcciones por asignar
	 */
	public void setCorrecciones(String correcciones) {
		this.correcciones = correcciones;
	}

	/**
	 * Obtiene el valor de estadoAporteRecaudo
	 * 
	 * @return El valor de estadoAporteRecaudo
	 */
	public EstadoAporteEnum getEstadoAporteRecaudo() {
		return estadoAporteRecaudo;
	}

	/**
	 * Establece el valor de estadoAporteRecaudo
	 * 
	 * @param estadoAporteRecaudo
	 *            El valor de estadoAporteRecaudo por asignar
	 */
	public void setEstadoAporteRecaudo(EstadoAporteEnum estadoAporteRecaudo) {
		this.estadoAporteRecaudo = estadoAporteRecaudo;
	}

	/**
	 * Obtiene el valor de estadoAporteAjuste
	 * 
	 * @return El valor de estadoAporteAjuste
	 */
	public EstadoAporteEnum getEstadoAporteAjuste() {
		return estadoAporteAjuste;
	}

	/**
	 * Establece el valor de estadoAporteAjuste
	 * 
	 * @param estadoAporteAjuste
	 *            El valor de estadoAporteAjuste por asignar
	 */
	public void setEstadoAporteAjuste(EstadoAporteEnum estadoAporteAjuste) {
		this.estadoAporteAjuste = estadoAporteAjuste;
	}

	/**
	 * Obtiene el valor de estadoRegistroAporteArchivo
	 * 
	 * @return El valor de estadoRegistroAporteArchivo
	 */
	public EstadoRegistroAportesArchivoEnum getEstadoRegistroAporteArchivo() {
		return estadoRegistroAporteArchivo;
	}

	/**
	 * Establece el valor de estadoRegistroAporteArchivo
	 * 
	 * @param estadoRegistroAporteArchivo
	 *            El valor de estadoRegistroAporteArchivo por asignar
	 */
	public void setEstadoRegistroAporteArchivo(EstadoRegistroAportesArchivoEnum estadoRegistroAporteArchivo) {
		this.estadoRegistroAporteArchivo = estadoRegistroAporteArchivo;
	}

	/**
	 * Obtiene el valor de usuarioAprobadorAporte
	 * 
	 * @return El valor de usuarioAprobadorAporte
	 */
	public String getUsuarioAprobadorAporte() {
		return usuarioAprobadorAporte;
	}

	/**
	 * Establece el valor de usuarioAprobadorAporte
	 * 
	 * @param usuarioAprobadorAporte
	 *            El valor de usuarioAprobadorAporte por asignar
	 */
	public void setUsuarioAprobadorAporte(String usuarioAprobadorAporte) {
		this.usuarioAprobadorAporte = usuarioAprobadorAporte;
	}

	/**
	 * Obtiene el valor de codSucursal
	 * 
	 * @return El valor de codSucursal
	 */
	public String getCodSucursal() {
		return codSucursal;
	}

	/**
	 * Establece el valor de codSucursal
	 * 
	 * @param codSucursal
	 *            El valor de codSucursal por asignar
	 */
	public void setCodSucursal(String codSucursal) {
		this.codSucursal = codSucursal;
	}

	/**
	 * Obtiene el valor de nomSucursal
	 * 
	 * @return El valor de nomSucursal
	 */
	public String getNomSucursal() {
		return nomSucursal;
	}

	/**
	 * Establece el valor de nomSucursal
	 * 
	 * @param nomSucursal
	 *            El valor de nomSucursal por asignar
	 */
	public void setNomSucursal(String nomSucursal) {
		this.nomSucursal = nomSucursal;
	}

	/**
	 * Obtiene el valor de fechaCreacion
	 * 
	 * @return El valor de fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece el valor de fechaCreacion
	 * 
	 * @param fechaCreacion
	 *            El valor de fechaCreacion por asignar
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene el valor de fechaMovimiento
	 * 
	 * @return El valor de fechaMovimiento
	 */
	public Long getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * Establece el valor de fechaMovimiento
	 * 
	 * @param fechaMovimiento
	 *            El valor de fechaMovimiento por asignar
	 */
	public void setFechaMovimiento(Long fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	/**
	 * Obtiene el valor de estadoRegistroAporte
	 * 
	 * @return El valor de estadoRegistroAporte
	 */
	public EstadoRegistroAporteEnum getEstadoRegistroAporte() {
		return estadoRegistroAporte;
	}

	/**
	 * Establece el valor de estadoRegistroAporte
	 * 
	 * @param estadoRegistroAporte
	 *            El valor de estadoRegistroAporte por asignar
	 */
	public void setEstadoRegistroAporte(EstadoRegistroAporteEnum estadoRegistroAporte) {
		this.estadoRegistroAporte = estadoRegistroAporte;
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
     * @return the formaReconocimientoAporte
     */
    public FormaReconocimientoAporteEnum getFormaReconocimientoAporte() {
        return formaReconocimientoAporte;
    }

    /**
     * @param formaReconocimientoAporte the formaReconocimientoAporte to set
     */
    public void setFormaReconocimientoAporte(FormaReconocimientoAporteEnum formaReconocimientoAporte) {
        this.formaReconocimientoAporte = formaReconocimientoAporte;
    }

	public ModalidadRecaudoAporteEnum getModalidadRecaudoAporte() {
		return modalidadRecaudoAporte;
	}

	public void setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum modalidadRecaudoAporte) {
		this.modalidadRecaudoAporte = modalidadRecaudoAporte;
	}

	public Boolean getMarcaCalculoCategoria() {
		return marcaCalculoCategoria;
	}

	public void setMarcaCalculoCategoria(Boolean marcaCalculoCategoria) {
		this.marcaCalculoCategoria = marcaCalculoCategoria;
	}

	public Long getIdRegistroDetalladoUltimo() {
		return this.idRegistroDetalladoUltimo;
	}

	public void setIdRegistroDetalladoUltimo(Long idRegistroDetalladoUltimo) {
		this.idRegistroDetalladoUltimo = idRegistroDetalladoUltimo;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", idAporteGeneral='" + getIdAporteGeneral() + "'" +
			", idRegistroDetallado='" + getIdRegistroDetallado() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", tipoCotizante='" + getTipoCotizante() + "'" +
			", estadoCotizante='" + getEstadoCotizante() + "'" +
			", estadoAporteCotizante='" + getEstadoAporteCotizante() + "'" +
			", estadoRegistroAporteCotizante='" + getEstadoRegistroAporteCotizante() + "'" +
			", idCotizante='" + getIdCotizante() + "'" +
			", diasCotizados='" + getDiasCotizados() + "'" +
			", horasLaboradas='" + getHorasLaboradas() + "'" +
			", salarioBasico='" + getSalarioBasico() + "'" +
			", municipioLaboral='" + getMunicipioLaboral() + "'" +
			", departamentoLaboral='" + getDepartamentoLaboral() + "'" +
			", valorIBC='" + getValorIBC() + "'" +
			", tarifa='" + getTarifa() + "'" +
			", salarioIntegral='" + getSalarioIntegral() + "'" +
			", aporteObligatorio='" + getAporteObligatorio() + "'" +
			", valorSaldoAporte='" + getValorSaldoAporte() + "'" +
			", valorMora='" + getValorMora() + "'" +
			", correcciones='" + getCorrecciones() + "'" +
			", estadoAporteRecaudo='" + getEstadoAporteRecaudo() + "'" +
			", estadoAporteAjuste='" + getEstadoAporteAjuste() + "'" +
			", estadoRegistroAporteArchivo='" + getEstadoRegistroAporteArchivo() + "'" +
			", usuarioAprobadorAporte='" + getUsuarioAprobadorAporte() + "'" +
			", codSucursal='" + getCodSucursal() + "'" +
			", nomSucursal='" + getNomSucursal() + "'" +
			", fechaCreacion='" + getFechaCreacion() + "'" +
			", formaReconocimientoAporte='" + getFormaReconocimientoAporte() + "'" +
			", fechaMovimiento='" + getFechaMovimiento() + "'" +
			", estadoRegistroAporte='" + getEstadoRegistroAporte() + "'" +
			", marcaPeriodo='" + getMarcaPeriodo() + "'" +
			", modalidadRecaudoAporte='" + getModalidadRecaudoAporte() + "'" +
			", marcaCalculoCategoria='" + getMarcaCalculoCategoria() + "'" +
			"}";
	}
    
}
