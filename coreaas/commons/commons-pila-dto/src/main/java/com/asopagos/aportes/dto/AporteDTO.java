package com.asopagos.aportes.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.pila.temporal.TemAportante;
import com.asopagos.entidades.pila.temporal.TemAporte;
import com.asopagos.entidades.pila.temporal.TemCotizante;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO con los datos del aporte.
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 *
 */
public class AporteDTO {

    private Long idTransaccion;
    private Boolean isSimulado;
    private Boolean esCotizanteFallecido;
    private Boolean esTrabajadorReintegrable;
    private Boolean esEmpleadorReintegrable;
    private EmpresaModeloDTO empresaAportante;
    private PersonaModeloDTO personaAportante;
    private SucursalEmpresaModeloDTO sucursalEmpresa;
    private PersonaModeloDTO personaCotizante;
    private AporteDetalladoModeloDTO aporteDetallado;
    private AporteGeneralModeloDTO aporteGeneral;
    private List<NovedadPilaDTO> novedades;
    private CanalRecepcionEnum canal;
    private String codigoMunicioAportante;
    private String codigoMunicioCotizante;
    private TipoIdentificacionEnum tipoDocTramitador;
    private String idTramitador;
    private Short digVerTramitador;
    private String nombreTramitador;
    private Boolean aporteGeneralProcesado = Boolean.FALSE;
    private Boolean aporteDetalladoProcesado = Boolean.FALSE;
    private Boolean esManual = Boolean.FALSE;
    
    //REFACTOR PILA MAYO 2020
    private Long idPersona;
    private Long idEmpresa;
    private Long idEmpleador;
    private Boolean tieneCotizanteDependienteReintegrable;
    private String estadoEmpleador;
    private String periodoAporte;
    
    /**
     * Marca de empresa para proceso de fiscalización de aportes
     */
    private Boolean enviadoAFiscalizacion;
    
    /**
     * Motivo de la marca de envío a fiscalización
     */
    private MotivoFiscalizacionAportanteEnum motivoFiscalizacion;

    /**
     * Fecha de fiscalización de la empresa
     */
    private Long fechaFiscalizacion;
    /**
     * Movimiento del aporte a registrar.
     */
    private MovimientoAporteModeloDTO movimiento;

    /**
     * Método que retorna el valor de movimiento.
     * @return valor de movimiento.
     */
    public MovimientoAporteModeloDTO getMovimiento() {
        return movimiento;
    }

    /**
     * Método encargado de modificar el valor de movimiento.
     * @param valor para modificar movimiento.
     */
    public void setMovimiento(MovimientoAporteModeloDTO movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * constructor de clase por defecto
     */
    public AporteDTO() {
    }
    
    /**
     * 
     */
    public AporteDTO(TemAportante aportante, TemCotizante cotizante, TemAporte aporte) {
    	aporteDTO(aportante, cotizante, aporte);
    }
    
    /**
     * Constructor usado en el copiar planillas (refactor mayo 2020)
     * 
     * @param aportante
     * @param idEmpresa
     * @param idEmpleador
     */
	public AporteDTO(TemAportante aportante, Long idPersona, Long idEmpresa, Long idEmpleador, String estadoEmpleador,
			Boolean tieneCotizanteDependienteReintegrable, String periodoAporte, String modalidadRecaudoAporte) {
		
    	inicializarAportante(aportante);
    	this.idPersona = idPersona;
    	this.idEmpresa = idEmpresa;
    	this.idEmpleador = idEmpleador;
    	this.estadoEmpleador = estadoEmpleador;
    	this.tieneCotizanteDependienteReintegrable = tieneCotizanteDependienteReintegrable;
    	this.periodoAporte = periodoAporte;
    	this.setCanal(ModalidadRecaudoAporteEnum.MANUAL.toString().equals(modalidadRecaudoAporte)
				? CanalRecepcionEnum.APORTE_MANUAL : CanalRecepcionEnum.PILA);
    	
    	this.setDigVerTramitador(aportante.getDigVerTramitador());
        this.setIdTramitador(aportante.getIdTramitador());
        this.setNombreTramitador(aportante.getNombreTramitador());
        this.setTipoDocTramitador(aportante.getTipoDocTramitador());
    }
    
    /**
     * Constructor usado en el copiar planillas (refactor mayo 2020)
     * 
     * @param aportante
     * @param idEmpresa
     * @param idEmpleador
     */
    public AporteDTO(TemCotizante cotizante) {
    	inicializarCotizante(cotizante);
    }
    		
     
    /** Método con la logica de inicialización de los atributos
     * @param aportante (tap)
     * @param cotizante (tct)
     * @param aporte    (tem)
     */
    private void aporteDTO(TemAportante aportante, TemCotizante cotizante, TemAporte aporte) {
        
    	inicializarAportante(aportante);
    	inicializarCotizante(cotizante);
    	inicializarSucursal(aportante, cotizante);
    	inicializarAporteGeneral(aportante, aporte);
        
        this.aporteDetallado = new AporteDetalladoModeloDTO();
        this.aporteDetallado.setAporteObligatorio(aporte.getAporteObligatorio());
        this.aporteDetallado.setCodSucursal(cotizante.getCodSucursal());
        this.aporteDetallado.setCorrecciones(aporte.getCorrecciones());
        this.aporteDetallado.setDiasCotizados(aporte.getDiasCotizados());
        this.aporteDetallado.setEstadoAporteAjuste(aporte.getEstadoAporteAjuste());
        this.aporteDetallado.setEstadoAporteCotizante(aporte.getEstadoAporteRecaudo());
        this.aporteDetallado.setEstadoAporteRecaudo(aporte.getEstadoAporteRecaudo());
        this.aporteDetallado.setEstadoRegistroAporte(aporte.getEstadoRegistroAporte());
        this.aporteDetallado.setHorasLaboradas(aporte.getHorasLaboradas());
        this.aporteDetallado.setMunicipioLaboral(aporte.getMunicipioLaboral());
        this.aporteDetallado.setNomSucursal(cotizante.getNomSucursal());
        this.aporteDetallado.setSalarioBasico(aporte.getSalarioBasico());
        this.aporteDetallado.setSalarioIntegral(aporte.getSalarioIntegral());
        this.aporteDetallado.setTarifa(aporte.getTarifa());
        if(cotizante.getTipoCotizante()!=null){
            this.aporteDetallado.setTipoCotizante(TipoAfiliadoEnum.valueOf(cotizante.getTipoCotizante()));    
        }
        this.aporteDetallado.setUsuarioAprobadorAporte(aporte.getUsuarioAprobadorAporte());
        this.aporteDetallado.setValorIBC(aporte.getValorIBC());
        BigDecimal mora = aporte.getValorIntMoraDetalle();
        if(mora == null){
            mora = BigDecimal.ZERO;
        }
        this.aporteDetallado.setValorMora(mora);
        this.aporteDetallado.setValorSaldoAporte(aporte.getValorSaldoAporte());        
        this.aporteDetallado.setEstadoRegistroAporteArchivo(aporte.getEstadoValRegistroAporte());
        this.aporteDetallado.setIdRegistroDetallado(aporte.getRegistroDetallado());
        this.aporteDetallado.setEstadoRegistroAporteCotizante(aporte.getEstadoRegistroAporte());
        
        TipoAfiliadoEnum tipoAfiliado = TipoAfiliadoEnum.valueOf(cotizante.getTipoCotizante());
        switch(tipoAfiliado){
		case PENSIONADO:
			this.aporteGeneral.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.PENSIONADO);
			break;
		case TRABAJADOR_DEPENDIENTE:
			this.aporteGeneral.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR);
			break;
		case TRABAJADOR_INDEPENDIENTE:
			this.aporteGeneral.setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE);
			break;
		default:
			break;
        }
        
        // se determina el aporte por terceros
        this.aporteGeneral.setPagadorPorTerceros(Boolean.FALSE);
		if (aportante != null && cotizante != null
				&& (cotizante.getTipoIdCotizante().equals(aportante.getTipoDocAportante())
						|| cotizante.getIdCotizante().equals(aportante.getIdAportante()))) {

	        this.aporteGeneral.setPagadorPorTerceros(Boolean.TRUE);
        }
        
        String codigoMunicipioAportante = aportante.getCodDepartamento() + aportante.getCodCiudad();
        this.setCodigoMunicioAportante(codigoMunicipioAportante);
        this.setCodigoMunicioCotizante(cotizante.getCodigoMunicipio());
        
        this.setDigVerTramitador(aportante.getDigVerTramitador());
        this.setIdTramitador(aportante.getIdTramitador());
        this.setNombreTramitador(aportante.getNombreTramitador());
        this.setTipoDocTramitador(aportante.getTipoDocTramitador());
        /*datos propios del proceso*/
        this.setEsCotizanteFallecido(cotizante.getEsFallecido());
		this.setCanal(ModalidadRecaudoAporteEnum.MANUAL.equals(aporte.getModalidadRecaudoAporte())
				? CanalRecepcionEnum.APORTE_MANUAL : CanalRecepcionEnum.PILA);
        
        
        /*datos del movimiento*/
        this.movimiento = new MovimientoAporteModeloDTO();
        this.movimiento.setAporte(this.getAporteDetallado().getAporteObligatorio());
        this.movimiento.setEstado(EstadoAporteEnum.VIGENTE);
        this.movimiento.setFechaActualizacionEstado(new Date());
        this.movimiento.setFechaCreacion(new Date());
        this.movimiento.setInteres(this.getAporteDetallado().getValorMora());
        this.movimiento.setTipoAjuste(null);
        this.movimiento.setTipoMovimiento(aporte.getModalidadRecaudoAporte().getTipoMovimiento());
        
    }
    

    /**
     * @param idTransaccion
     * @param isSimulado
     * @param esCotizanteFallecido
     * @param empresaAportante
     * @param personaAportante
     * @param sucursalEmpresa
     * @param personaCotizante
     * @param aporteDetallado
     * @param aporteGeneral
     * @param novedades
     */
    public AporteDTO(Long idTransaccion, Boolean isSimulado, Boolean esCotizanteFallecido, EmpresaModeloDTO empresaAportante,
            PersonaModeloDTO personaAportante, SucursalEmpresaModeloDTO sucursalEmpresa, PersonaModeloDTO personaCotizante,
            AporteDetalladoModeloDTO aporteDetallado, AporteGeneralModeloDTO aporteGeneral, List<NovedadPilaDTO> novedades) {
        this.idTransaccion = idTransaccion;
        this.isSimulado = isSimulado;
        this.esCotizanteFallecido = esCotizanteFallecido;
        this.empresaAportante = empresaAportante;
        this.personaAportante = personaAportante;
        this.sucursalEmpresa = sucursalEmpresa;
        this.personaCotizante = personaCotizante;
        this.aporteDetallado = aporteDetallado;
        this.aporteGeneral = aporteGeneral;
        this.novedades = novedades;
    }

    /**
     * @param idTransaccion
     * @param isSimulado
     * @param esCotizanteFallecido
     * @param empresaAportante
     * @param personaAportante
     * @param sucursalEmpresa
     * @param personaCotizante
     * @param aporteDetallado
     * @param aporteGeneral
     * @param novedades
     */
    public AporteDTO(Long idTransaccion, Boolean isSimulado, Boolean esCotizanteFallecido, EmpresaModeloDTO empresaAportante,
            PersonaModeloDTO personaAportante, SucursalEmpresaModeloDTO sucursalEmpresa, PersonaModeloDTO personaCotizante,
            AporteDetalladoModeloDTO aporteDetallado, AporteGeneralModeloDTO aporteGeneral, List<NovedadPilaDTO> novedades,
            CanalRecepcionEnum canal) {
        this.idTransaccion = idTransaccion;
        this.isSimulado = isSimulado;
        this.esCotizanteFallecido = esCotizanteFallecido;
        this.empresaAportante = empresaAportante;
        this.personaAportante = personaAportante;
        this.sucursalEmpresa = sucursalEmpresa;
        this.personaCotizante = personaCotizante;
        this.aporteDetallado = aporteDetallado;
        this.aporteGeneral = aporteGeneral;
        this.novedades = novedades;
        this.canal = canal;
    }

    public boolean verificarDatosTramitador(){
    	return this.tipoDocTramitador != null && this.idTramitador != null; 
    }
    
    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion
     *        the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the isSimulado
     */
    public Boolean getIsSimulado() {
        return isSimulado;
    }

    /**
     * @param isSimulado
     *        the isSimulado to set
     */
    public void setIsSimulado(Boolean isSimulado) {
        this.isSimulado = isSimulado;
    }

    /**
     * @return the esCotizanteFallecido
     */
    public Boolean getEsCotizanteFallecido() {
        return esCotizanteFallecido;
    }

    /**
     * @param esCotizanteFallecido
     *        the esCotizanteFallecido to set
     */
    public void setEsCotizanteFallecido(Boolean esCotizanteFallecido) {
        this.esCotizanteFallecido = esCotizanteFallecido;
    }

    public Boolean getEsTrabajadorReintegrable() {
        return esTrabajadorReintegrable;
    }

    public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
        this.esTrabajadorReintegrable = esTrabajadorReintegrable;
    }

    /**
     * @return the empresaAportante
     */
    public EmpresaModeloDTO getEmpresaAportante() {
        return empresaAportante;
    }

    /**
     * @param empresaAportante
     *        the empresaAportante to set
     */
    public void setEmpresaAportante(EmpresaModeloDTO empresaAportante) {
        this.empresaAportante = empresaAportante;
    }

    /**
     * @return the personaAportante
     */
    public PersonaModeloDTO getPersonaAportante() {
        return personaAportante;
    }

    /**
     * @param personaAportante
     *        the personaAportante to set
     */
    public void setPersonaAportante(PersonaModeloDTO personaAportante) {
        this.personaAportante = personaAportante;
    }

    /**
     * @return the sucursalEmpresa
     */
    public SucursalEmpresaModeloDTO getSucursalEmpresa() {
        return sucursalEmpresa;
    }

    /**
     * @param sucursalEmpresa
     *        the sucursalEmpresa to set
     */
    public void setSucursalEmpresa(SucursalEmpresaModeloDTO sucursalEmpresa) {
        this.sucursalEmpresa = sucursalEmpresa;
    }

    /**
     * @return the personaCotizante
     */
    public PersonaModeloDTO getPersonaCotizante() {
        return personaCotizante;
    }

    /**
     * @param personaCotizante
     *        the personaCotizante to set
     */
    public void setPersonaCotizante(PersonaModeloDTO personaCotizante) {
        this.personaCotizante = personaCotizante;
    }

    /**
     * @return the aporteDetallado
     */
    public AporteDetalladoModeloDTO getAporteDetallado() {
        return aporteDetallado;
    }

    /**
     * @param aporteDetallado
     *        the aporteDetallado to set
     */
    public void setAporteDetallado(AporteDetalladoModeloDTO aporteDetallado) {
        this.aporteDetallado = aporteDetallado;
    }

    /**
     * @return the aporteGeneral
     */
    public AporteGeneralModeloDTO getAporteGeneral() {
        return aporteGeneral;
    }

    /**
     * @param aporteGeneral
     *        the aporteGeneral to set
     */
    public void setAporteGeneral(AporteGeneralModeloDTO aporteGeneral) {
        this.aporteGeneral = aporteGeneral;
    }

    /**
     * @return the novedades
     */
    public List<NovedadPilaDTO> getNovedades() {
        return novedades;
    }

    /**
     * @param novedades
     *        the novedades to set
     */
    public void setNovedades(List<NovedadPilaDTO> novedades) {
        this.novedades = novedades;
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal
     *        the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

	/**
	 * @return the codigoMunicioAportante
	 */
	public String getCodigoMunicioAportante() {
		return codigoMunicioAportante;
	}

	/**
	 * @param codigoMunicioAportante the codigoMunicioAportante to set
	 */
	public void setCodigoMunicioAportante(String codigoMunicioAportante) {
		this.codigoMunicioAportante = codigoMunicioAportante;
	}

	/**
	 * @return the codigoMunicioCotizante
	 */
	public String getCodigoMunicioCotizante() {
		return codigoMunicioCotizante;
	}

	/**
	 * @param codigoMunicioCotizante the codigoMunicioCotizante to set
	 */
	public void setCodigoMunicioCotizante(String codigoMunicioCotizante) {
		this.codigoMunicioCotizante = codigoMunicioCotizante;
	}

	/**
	 * @return the tipoDocTramitador
	 */
	public TipoIdentificacionEnum getTipoDocTramitador() {
		return tipoDocTramitador;
	}

	/**
	 * @param tipoDocTramitador the tipoDocTramitador to set
	 */
	public void setTipoDocTramitador(TipoIdentificacionEnum tipoDocTramitador) {
		this.tipoDocTramitador = tipoDocTramitador;
	}

	/**
	 * @return the idTramitador
	 */
	public String getIdTramitador() {
		return idTramitador;
	}

	/**
	 * @param idTramitador the idTramitador to set
	 */
	public void setIdTramitador(String idTramitador) {
		this.idTramitador = idTramitador;
	}

	/**
	 * @return the digVerTramitador
	 */
	public Short getDigVerTramitador() {
		return digVerTramitador;
	}

	/**
	 * @param digVerTramitador the digVerTramitador to set
	 */
	public void setDigVerTramitador(Short digVerTramitador) {
		this.digVerTramitador = digVerTramitador;
	}

	/**
	 * @return the nombreTramitador
	 */
	public String getNombreTramitador() {
		return nombreTramitador;
	}

	/**
	 * @param nombreTramitador the nombreTramitador to set
	 */
	public void setNombreTramitador(String nombreTramitador) {
		this.nombreTramitador = nombreTramitador;
	}
	
    /**
     * @return the esEmpleadorReintegrable
     */
    public Boolean getEsEmpleadorReintegrable() {
        return esEmpleadorReintegrable;
    }

    /**
     * @param esEmpleadorReintegrable the esEmpleadorReintegrable to set
     */
    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }

    /**
     * Método que retorna el valor de enviadoAFiscalizacion.
     * @return valor de enviadoAFiscalizacion.
     */
    public Boolean getEnviadoAFiscalizacion() {
        return enviadoAFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de enviadoAFiscalizacion.
     * @param valor para modificar enviadoAFiscalizacion.
     */
    public void setEnviadoAFiscalizacion(Boolean enviadoAFiscalizacion) {
        this.enviadoAFiscalizacion = enviadoAFiscalizacion;
    }

    /**
     * Método que retorna el valor de motivoFiscalizacion.
     * @return valor de motivoFiscalizacion.
     */
    public MotivoFiscalizacionAportanteEnum getMotivoFiscalizacion() {
        return motivoFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de motivoFiscalizacion.
     * @param valor para modificar motivoFiscalizacion.
     */
    public void setMotivoFiscalizacion(MotivoFiscalizacionAportanteEnum motivoFiscalizacion) {
        this.motivoFiscalizacion = motivoFiscalizacion;
    }

    /**
     * Método que retorna el valor de fechaFiscalizacion.
     * @return valor de fechaFiscalizacion.
     */
    public Long getFechaFiscalizacion() {
        return fechaFiscalizacion;
    }

    /**
     * Método encargado de modificar el valor de fechaFiscalizacion.
     * @param valor para modificar fechaFiscalizacion.
     */
    public void setFechaFiscalizacion(Long fechaFiscalizacion) {
        this.fechaFiscalizacion = fechaFiscalizacion;
    }

    /**
     * @return the aporteGeneralProcesado
     */
    public Boolean getAporteGeneralProcesado() {
        return aporteGeneralProcesado;
    }

    /**
     * @param aporteGeneralProcesado the aporteGeneralProcesado to set
     */
    public void setAporteGeneralProcesado(Boolean aporteGeneralProcesado) {
        this.aporteGeneralProcesado = aporteGeneralProcesado;
    }

    /**
     * @return the aporteDetalladoProcesado
     */
    public Boolean getAporteDetalladoProcesado() {
        return aporteDetalladoProcesado;
    }

    /**
     * @param aporteDetalladoProcesado the aporteDetalladoProcesado to set
     */
    public void setAporteDetalladoProcesado(Boolean aporteDetalladoProcesado) {
        this.aporteDetalladoProcesado = aporteDetalladoProcesado;
    }

    /**
     * @return the esManual
     */
    public Boolean getEsManual() {
        return esManual;
    }

    /**
     * @param esManual the esManual to set
     */
    public void setEsManual(Boolean esManual) {
        this.esManual = esManual;
    }
    
    /**
     * Método que inicializa los datos relacionados del aportante
     * @param aportante
     */
    private void inicializarAportante(TemAportante aportante) {
    	/*si se trata de un empleador se crea como empresa*/
        EmpresaModeloDTO empresa = new EmpresaModeloDTO();
        this.setEsEmpleadorReintegrable(aportante.getEsEmpleadorReintegrable()); //--
        empresa.setTipoIdentificacion(aportante.getTipoDocAportante());
        empresa.setNumeroIdentificacion(aportante.getIdAportante());
        empresa.setRazonSocial(aportante.getRazonSocial());
        empresa.setPrimerNombre(aportante.getPrimerNombreAportante());
        empresa.setSegundoNombre(aportante.getSegundoNombreAportante());
        empresa.setPrimerApellido(aportante.getPrimerApellidoAportante());
        empresa.setSegundoApellido(aportante.getSegundoApellidoAportante());
        empresa.setDigitoVerificacion(aportante.getDigVerAportante());
        
        UbicacionModeloDTO ubicacion = new UbicacionModeloDTO();
        ubicacion.setDireccionFisica(aportante.getDireccion());
        
        ubicacion.setIdMunicipio(null);
        if(aportante.getTelefono()!=null){
        	ubicacion.setTelefonoCelular(aportante.getTelefono().toString());
        }
        ubicacion.setEmail(aportante.getEmail());
        empresa.setUbicacionModeloDTO(ubicacion);
        
        if(aportante.getTipoSolicitud()!=null && aportante.getTipoSolicitud().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())){
            if(aportante.getNaturalezaJuridica()!=null){
                empresa.setNaturalezaJuridica(NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(new Integer(aportante.getNaturalezaJuridica())));
				empresa.setFechaConstitucion(
						aportante.getFechaMatricula() != null ? aportante.getFechaMatricula().getTime() : null);
            }
            /*se setean en este punto los campos de enviado a fiscalización si el aportante es un empleador*/
            empresa.setEnviadoAFiscalizacion(aportante.getEnviadoAFiscalizacion());
            empresa.setFechaFiscalizacion(new Date().getTime());
            empresa.setMotivoFiscalizacion(aportante.getMotivoFiscalizacion());
            this.empresaAportante = empresa;
        }else{
            /*si el aportante es una persona entonces se setean al DTO Ccomo tal para luego ser tramitado*/
            this.setEnviadoAFiscalizacion(aportante.getEnviadoAFiscalizacion()); //--?
            this.setFechaFiscalizacion(new Date().getTime());//--?
            this.setMotivoFiscalizacion(aportante.getMotivoFiscalizacion());//--?
            this.personaAportante = empresa;
        }
    }
    
    /**
     * Método que inicializa los datos relacionados del cotizante
     * @param cotizante
     */
    private void inicializarCotizante(TemCotizante cotizante) {
    	this.personaCotizante = new PersonaModeloDTO();
        this.personaCotizante.setTipoIdentificacion(cotizante.getTipoIdCotizante());
        this.personaCotizante.setNumeroIdentificacion(cotizante.getIdCotizante());
        this.personaCotizante.setPrimerNombre(cotizante.getPrimerNombre());
        this.personaCotizante.setSegundoNombre(cotizante.getSegundoNombre());
        this.personaCotizante.setPrimerApellido(cotizante.getPimerApellido());
        this.personaCotizante.setSegundoApellido(cotizante.getSegundoApellido());
        
        this.personaCotizante.setFallecido(cotizante.getEsFallecido());//--?
        this.esTrabajadorReintegrable = cotizante.getEsTrabajadorReintegrable();
        UbicacionModeloDTO ubicacionCotizante = new UbicacionModeloDTO();
        ubicacionCotizante.setIdMunicipio(null);
        this.personaCotizante.setUbicacionModeloDTO(ubicacionCotizante);
    }
    
    /**
     * Método que inicializa los datos relacionados de la sucursal 
     * 
     * @param aportante
     * @param cotizante
     */
    private void inicializarSucursal(TemAportante aportante, TemCotizante cotizante) {
    	// sólo se crea una sucursal sí se cuenta con los datos
        if (aportante.getMarcaSucursal() != null && aportante.getMarcaSucursal() && cotizante.getCodSucursalPILA() != null
                && cotizante.getNomSucursalPILA() != null) {
            this.sucursalEmpresa = new SucursalEmpresaModeloDTO();
            this.sucursalEmpresa.setCodigo(cotizante.getCodSucursalPILA());
            this.sucursalEmpresa.setNombre(cotizante.getNomSucursalPILA());
        }
        else if (aportante.getMarcaSucursal() != null && !aportante.getMarcaSucursal() && cotizante.getCodSucursal() != null
                && cotizante.getNomSucursal() != null) {
            this.sucursalEmpresa = new SucursalEmpresaModeloDTO();
            this.sucursalEmpresa.setCodigo(cotizante.getCodSucursal());
            this.sucursalEmpresa.setNombre(cotizante.getNomSucursal());
        }
    }
    
    /**
     * Método que inicializa los datos relacionados de la sucursal
     * 
     * @param aportante
     * @param aporte
     */
    private void inicializarAporteGeneral(TemAportante aportante, TemAporte aporte) {
        this.aporteGeneral = new AporteGeneralModeloDTO();
        this.aporteGeneral.setIdRegistroGeneral(aporte.getRegistroGeneral());
        this.aporteGeneral.setPeriodoAporte(aporte.getPeriodoAporte());
        this.aporteGeneral.setValorTotalAporteObligatorio(aporte.getValTotalApoObligatorio());
        this.aporteGeneral.setValorInteresesMora(aporte.getValorIntMoraGeneral());
        if(aporte.getFechaRecaudo()!=null){
        	this.aporteGeneral.setFechaRecaudo(aporte.getFechaRecaudo().getTime());
        }
        this.aporteGeneral.setCodigoEntidadFinanciera(aporte.getCodEntidadFinanciera());
        this.aporteGeneral.setIdOperadorInformacion(aporte.getOperadorInformacion());
        this.aporteGeneral.setModalidadPlanilla(aporte.getModalidadPlanilla());
        this.aporteGeneral.setAporteConDetalle(aporte.getApoConDetalle());
        this.aporteGeneral.setNumeroCuenta(aporte.getNumeroCuenta());
        this.aporteGeneral.setEstadoRegistroAporteAportante(aporte.getEstadoRegistroAporte());
        this.aporteGeneral.setEstadoAporteAportante(aporte.getEstadoAporteRecaudo());
        this.aporteGeneral.setEmailAportante(aportante.getEmail());
        if(aporte.getFechaProcesamiento()!=null){
        	this.aporteGeneral.setFechaProcesamiento(aporte.getFechaProcesamiento().getTime());
        }
        this.aporteGeneral.setNumeroPlanillaManual(aporte.getNumeroPlanillaManual());
    	this.aporteGeneral.setModalidadRecaudoAporte(aporte.getModalidadRecaudoAporte());
    }

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getIdEmpleador() {
		return idEmpleador;
	}

	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	public Boolean getTieneCotizanteDependienteReintegrable() {
		return tieneCotizanteDependienteReintegrable;
	}

	public void setTieneCotizanteDependienteReintegrable(Boolean tieneCotizanteDependienteReintegrable) {
		this.tieneCotizanteDependienteReintegrable = tieneCotizanteDependienteReintegrable;
	}

	public String getEstadoEmpleador() {
		return estadoEmpleador;
	}

	public void setEstadoEmpleador(String estadoEmpleador) {
		this.estadoEmpleador = estadoEmpleador;
	}

	public String getPeriodoAporte() {
		return periodoAporte;
	}

	public void setPeriodoAporte(String periodoAporte) {
		this.periodoAporte = periodoAporte;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

    @Override
    public String toString() {
        return "{" +
            " idTransaccion='" + getIdTransaccion() + "'" +
            ", isSimulado='" + getIsSimulado() + "'" +
            ", esCotizanteFallecido='" + getEsCotizanteFallecido() + "'" +
            ", esTrabajadorReintegrable='" + getEsTrabajadorReintegrable() + "'" +
            ", esEmpleadorReintegrable='" + getEsEmpleadorReintegrable() + "'" +
            ", empresaAportante='" + getEmpresaAportante() + "'" +
            ", personaAportante='" + getPersonaAportante() + "'" +
            ", sucursalEmpresa='" + getSucursalEmpresa() + "'" +
            ", personaCotizante='" + getPersonaCotizante() + "'" +
            ", aporteDetallado='" + getAporteDetallado() + "'" +
            ", aporteGeneral='" + getAporteGeneral() + "'" +
            ", novedades='" + getNovedades() + "'" +
            ", canal='" + getCanal() + "'" +
            ", codigoMunicioAportante='" + getCodigoMunicioAportante() + "'" +
            ", codigoMunicioCotizante='" + getCodigoMunicioCotizante() + "'" +
            ", tipoDocTramitador='" + getTipoDocTramitador() + "'" +
            ", idTramitador='" + getIdTramitador() + "'" +
            ", digVerTramitador='" + getDigVerTramitador() + "'" +
            ", nombreTramitador='" + getNombreTramitador() + "'" +
            ", aporteGeneralProcesado='" + getAporteGeneralProcesado() + "'" +
            ", aporteDetalladoProcesado='" + getAporteDetalladoProcesado() + "'" +
            ", esManual='" + getEsManual() + "'" +
            ", idPersona='" + getIdPersona() + "'" +
            ", idEmpresa='" + getIdEmpresa() + "'" +
            ", idEmpleador='" + getIdEmpleador() + "'" +
            ", tieneCotizanteDependienteReintegrable='" + getTieneCotizanteDependienteReintegrable() + "'" +
            ", estadoEmpleador='" + getEstadoEmpleador() + "'" +
            ", periodoAporte='" + getPeriodoAporte() + "'" +
            ", enviadoAFiscalizacion='" + getEnviadoAFiscalizacion() + "'" +
            ", motivoFiscalizacion='" + getMotivoFiscalizacion() + "'" +
            ", fechaFiscalizacion='" + getFechaFiscalizacion() + "'" +
            ", movimiento='" + getMovimiento() + "'" +
            "}";
    }
}
