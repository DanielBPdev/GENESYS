package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ExpulsionEnum;
import com.asopagos.enumeraciones.core.MotivoInactivacionRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.MotivoRetencionSubsidioEnum;
import com.asopagos.enumeraciones.personas.EstadoAportesEmpleadorEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * DTO que contiene los campos de los roles de un afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class EmpleadorModeloDTO extends EmpresaModeloDTO implements Serializable {

    /**
	 * Código identificador de llave primaria del empleador 
	 */
	private Long idEmpleador;
    /**
     * Descripción del estado de los aportes del empleador
     */
	private EstadoAportesEmpleadorEnum estadoAportesEmpleador;
	/**
	 * Descripción del motivo de desafiliación
	 */
	private MotivoDesafiliacionEnum motivoDesafiliacion;
	 /**
 	 * Indicador S/N si el empleador cuenta con alguna expulsión subsanada[S=Si N=No]
 	 */
	private Boolean expulsionSubsanada;
	/**
	 * Fecha del cambio de estado de afiliación del empleador
	 */
	private Long fechaCambioEstadoAfiliacion;
	/**
	 * Número total de trabajadores asociados al empleador
	 */
	private Integer numeroTotalTrabajadores;
	/**
	 * Valor total de la última nómina
	 */
	private BigDecimal valorTotalUltimaNomina;
	/**
	 * Periodo de la última nómina
	 */
	private Long periodoUltimaNomina;
	/**
	 * Id que identifica al medio de pago asociada al empleador
	 */
	private TipoMedioDePagoEnum medioDePagoSubsidioMonetario;
	/**
	 * Descripción del estado del empleador 
	 */
	private EstadoEmpleadorEnum estadoEmpleador;
	/**
	 * Fecha del cambio de estado de afiliación del empleador
	 */
	private Long fechaRetiro;
	
	/**
	 * Fecha registro subsanacion expulsion 
	 */
	private Long fechaSubsancionExpulsion;
	
	/**
	 * Motivo subsnacion expulsion
	 */
	private String motivoSubsanacionExpulsion;
	
	/**
     * Momento en el cual se retiran todos los trabajadores
     */
	private Long fechaRetiroTotalTrabajadores;
	
	/**
     * Cantidad de ingresos a bandeja de cero trabajadores
     */
	private Short cantIngresoBandejaCeroTrabajadores;
	
	/**
     * Fecha activa para evitar desafiliacion automatica
     */
	private Long fechaGestionDesafiliacion; 
	
	/**
     * Hace referencia a los beneficios que tiene asociado el empleador
     */
	private BeneficioEmpleadorModeloDTO beneficio; 
    
    /**
     * Día hábil de vencimiento de aportes 
     * */
    private Short diaHabilVencimientoAporte;
    
    /**
     * Marca que permite saber si el empleador
     * esta en proceso de desafiliacion
     */
    private ExpulsionEnum marcaExpulsion;

    private Boolean retencionSubsidioActiva;

    private MotivoRetencionSubsidioEnum motivoRetencionSubsidio;

    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio;
    
    /** Variables nuevas VISTA 360 (28-may-2018) */
    /**
     * Tipo de beneficio que posee el empleador 1429 o 590
     */
    private TipoBeneficioEnum tipoBeneficio;
    
    /**
     * Clasificacion en la solicitud con la cual se afilio el empleador
     */
    private ClasificacionEnum clasificacion;
    
    /**
     * Estado de la solicitud de afiliacion del trabajador 
     */
    private EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitud;
    
    /**
     * Id de la empresa asociada
     */
    private Long idEmpresa;
    
    /**
     * Id de la persona asociada al empleador
     */
    private Long idPersona;
    
    /**
     * Indica si es una entidad pagadora
     */
    private Boolean entidadPagadora;
    
    /**
     * Tipo de pagador, "Aportante y Pagador", Aportante y Pagador
     */
    private String tipoPagador;
    
    /**
     * Canal de reintegro del empleador
     * */
    private CanalRecepcionEnum canalReingreso;
    
    /**
     * Referencia al aporte general con el que se general el reintegro
     * */
    private Long referenciaAporteReingreso;


	/**
	 * email celular oficina principal
	 */
	private String celularOficinaPrincipal;
	/**
	 * email 1 oficina principal
	 */
	private String email1OficinaPrincipal;
	/**
	 * email 2 Envío correspondencia
	 */
	private String email2EnvioDeCorrespondencia;
	/**
	 * email 3 notificación judicial
	 */
	private String email3NotificacionJudicial;
	/**
	 * telefono 1 oficina principal
	 */
	private String telefono1OficinaPrincipal;
	/**
	 * telefono 2 envio de correspondencia
	 */
	private String telefono2EnvioDeCorrespondencia;
	/**
	 * telefono 3 notificación judicial
	 */
	private String telefono3NotificacionJudicial;
	/**
	 * responsable1DeLaCajaParaContacto
	 */
	private String responsable1DeLaCajaParaContacto;
	/**
	 * responsable2DeLaCajaParaContacto
	 */
	private String responsable2DeLaCajaParaContacto;
	/**
	 * id ubicación principal
	 */
	private Long idUbicacionPrincipal;
	/**
	 * id ubicación envio de correspondencia
	 */
	private Long idUbicacionEnvioDeCorrespondencia;
	/**
	 * id ubicación notificacion judicial
	 */
	private Long idUbicacionNotificacionJudicial;

    private Boolean trasladoCajasCompensacion;

	private Long idSucursalEmpleador;
    
    
	public EmpleadorModeloDTO() {
	}

    /**
     * Constructor usado en vista 360 para la cabecera (para mantis 245470)
     *
     * @param tipoBeneficio
     * @param entidadPagadora
     * @param estadoEmpleador
     */
    public EmpleadorModeloDTO(TipoBeneficioEnum tipoBeneficio, Boolean entidadPagadora, EstadoEmpleadorEnum estadoEmpleador) {
        this.tipoBeneficio = tipoBeneficio;
        this.entidadPagadora = entidadPagadora;
        this.estadoEmpleador = estadoEmpleador;
    }


	/**
     * Constructor usado en vista 360 para la cabecera (para mantis 245470)
     *
     * @param tipoBeneficio
     * @param entidadPagadora
     * @param estadoEmpleador
     * @param trasladoCajasCompensacion
     */
    public EmpleadorModeloDTO(TipoBeneficioEnum tipoBeneficio, Boolean entidadPagadora, EstadoEmpleadorEnum estadoEmpleador, 
        Boolean trasladoCajasCompensacion) {
        this.tipoBeneficio = tipoBeneficio;
        this.entidadPagadora = entidadPagadora;
        this.estadoEmpleador = estadoEmpleador;
        this.trasladoCajasCompensacion = trasladoCajasCompensacion;
    }
	
    /**
     * Constructor usado en vista 360 para la cabecera
     * @param tipoBeneficio
     * @param clasificacion
     * @param estadoSolicitud
     * @param idEmpresa
     * @param idPersona
     * @param entidadPagadora
     * @param estadoEmpleador 
     */
    public EmpleadorModeloDTO(TipoBeneficioEnum tipoBeneficio, ClasificacionEnum clasificacion,
            EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitud, Boolean entidadPagadora, EstadoEmpleadorEnum estadoEmpleador) {
        this.tipoBeneficio = tipoBeneficio;
        this.clasificacion = clasificacion;
        this.estadoSolicitud = estadoSolicitud;
        this.entidadPagadora = entidadPagadora;
        this.estadoEmpleador = estadoEmpleador;
    }	
	
	public EmpleadorModeloDTO(Empleador empleador) {
	    super();
		this.convertToDTO(empleador);
	}

    public EmpleadorModeloDTO(Long idEmpleador, Long idEmpresa, Long idPersona, String tipoIdentificacion,
            String numeroIdentificacion, String razonSocial, Short digitoVerificacion, String estadoEmpleador) {
        this.setIdEmpleador(idEmpleador);
        this.setIdEmpresa(idEmpresa);
        this.setIdPersona(idPersona);
        this.setTipoIdentificacion(tipoIdentificacion != null ? TipoIdentificacionEnum.valueOf(tipoIdentificacion) : null);
        this.setNumeroIdentificacion(numeroIdentificacion);
        this.setDigitoVerificacion(digitoVerificacion);
        this.setRazonSocial(razonSocial);
        this.setEstadoEmpleador(estadoEmpleador != null ? EstadoEmpleadorEnum.valueOf(estadoEmpleador) : null);
    }

	public EmpleadorModeloDTO(Long idEmpleador,String tipoIdentificacion,String numeroIdentificacion){
		this.setIdEmpleador(idEmpleador);
		this.setTipoIdentificacion(tipoIdentificacion != null ? TipoIdentificacionEnum.valueOf(tipoIdentificacion) : null);
		this.setNumeroIdentificacion(numeroIdentificacion);
	}

	/**
	 * Método encargado de convertir de Entidad a DTO
	 * @param empleador entidad a convertir.
	 */
	public void convertToDTO(Empleador empleador) {
		super.convertToDTO(empleador.getEmpresa());
		this.setEstadoEmpleador(empleador.getEstadoEmpleador());
		//this.setEstadoAportesEmpleador(empleador.getEstadoAportesEmpleador());
		this.setExpulsionSubsanada(empleador.getExpulsionSubsanada());
		if(empleador.getFechaCambioEstadoAfiliacion()!=null){
			this.setFechaCambioEstadoAfiliacion(empleador.getFechaCambioEstadoAfiliacion().getTime());
		}
		this.setIdEmpleador(empleador.getIdEmpleador());
		/*Refactor Medio De Pago*/
		this.setMedioDePagoSubsidioMonetario(empleador.getMedioDePagoSubsidioMonetario());
		this.setMotivoDesafiliacion(empleador.getMotivoDesafiliacion());
		this.setNumeroTotalTrabajadores(empleador.getNumeroTotalTrabajadores());
		if(empleador.getPeriodoUltimaNomina()!=null){
			this.setPeriodoUltimaNomina(empleador.getPeriodoUltimaNomina().getTime());
		}
		if (empleador.getFechaRetiro() != null) {
			this.setFechaRetiro(empleador.getFechaRetiro().getTime());
		}
		this.setValorTotalUltimaNomina(empleador.getValorTotalUltimaNomina());
		if (empleador.getFechaSubsancionExpulsion() != null) {
			this.setFechaSubsancionExpulsion(empleador.getFechaSubsancionExpulsion().getTime());
		}
		empleador.setMotivoSubsanacionExpulsion(empleador.getMotivoSubsanacionExpulsion());
		if (empleador.getFechaRetiroTotalTrabajadores() != null) {
			this.setFechaRetiroTotalTrabajadores(empleador.getFechaRetiroTotalTrabajadores().getTime());
		}
		this.setCantIngresoBandejaCeroTrabajadores(empleador.getCantIngresoBandejaCeroTrabajadores());
		if (empleador.getFechaGestionDesafiliacion() != null) {
			this.setFechaGestionDesafiliacion(empleador.getFechaGestionDesafiliacion().getTime());
		}
		
		this.setDiaHabilVencimientoAporte(empleador.getDiaHabilVencimientoAporte());
		this.setMarcaExpulsion(empleador.getMarcaExpulsion() != null ? empleador.getMarcaExpulsion() :null);
		this.setRetencionSubsidioActiva(empleador.getRetencionSubsidioActiva());
		this.setMotivoRetencionSubsidio(empleador.getMotivoRetencionSubsidio());
		this.setMotivoInactivaRetencionSubsidio(empleador.getMotivoInactivaRetencionSubsidio());
		this.setCanalReingreso(empleador.getCanalReingreso());
		this.setReferenciaAporteReingreso(empleador.getReferenciaAporteReingreso());
        this.setTrasladoCajasCompensacion(empleador.getTrasladoCajasCompensacion());
	}
	
	/**
	 * Método encargado de convertir un DTO a Entidad
	 * @return entidad convertida.
	 */
	public Empleador convertToEntity() {
		Empleador empleador = new Empleador();
		empleador.setEstadoEmpleador(this.getEstadoEmpleador());
		empleador.setExpulsionSubsanada(this.getExpulsionSubsanada());
		if(this.getFechaCambioEstadoAfiliacion()!=null){
			empleador.setFechaCambioEstadoAfiliacion(new Date(this.getFechaCambioEstadoAfiliacion()));
		}
		empleador.setIdEmpleador(this.getIdEmpleador());
		empleador.setMedioDePagoSubsidioMonetario(this.getMedioDePagoSubsidioMonetario());
		empleador.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
		empleador.setNumeroTotalTrabajadores(this.getNumeroTotalTrabajadores());
		if(this.getPeriodoUltimaNomina()!=null){
			empleador.setPeriodoUltimaNomina(new Date(this.getPeriodoUltimaNomina()));
		}
		if(this.getFechaRetiro() != null){
			empleador.setFechaRetiro(new Date(this.getFechaRetiro()));
		}
		empleador.setValorTotalUltimaNomina(this.getValorTotalUltimaNomina());
		empleador.setEmpresa(super.convertToEmpresaEntity());
		if (this.getFechaSubsancionExpulsion() != null) {
			empleador.setFechaSubsancionExpulsion(new Date(this.getFechaSubsancionExpulsion()));
		}
		empleador.setMotivoSubsanacionExpulsion(this.getMotivoSubsanacionExpulsion());
		if (this.getFechaRetiroTotalTrabajadores() != null) {
			empleador.setFechaRetiroTotalTrabajadores(new Date(this.getFechaRetiroTotalTrabajadores()));
		}
		empleador.setCantIngresoBandejaCeroTrabajadores(this.getCantIngresoBandejaCeroTrabajadores());
		if (this.getFechaGestionDesafiliacion() != null) {
			empleador.setFechaGestionDesafiliacion(new Date(this.getFechaGestionDesafiliacion()));
		}
		
		empleador.setDiaHabilVencimientoAporte(this.getDiaHabilVencimientoAporte());
		empleador.setMarcaExpulsion(this.getMarcaExpulsion() != null ? this.getMarcaExpulsion() : null);
		empleador.setRetencionSubsidioActiva(this.getRetencionSubsidioActiva());
		empleador.setMotivoRetencionSubsidio(this.getMotivoRetencionSubsidio());
		empleador.setMotivoInactivaRetencionSubsidio(this.getMotivoInactivaRetencionSubsidio());
		empleador.setCanalReingreso(this.getCanalReingreso());
		empleador.setReferenciaAporteReingreso(this.getReferenciaAporteReingreso());
        empleador.setTrasladoCajasCompensacion(this.getTrasladoCajasCompensacion());
		return empleador;

	}
	/**
	 * Método encargado de copiar un DTO a una entidad.
	 * @return empleador entidad convertida.
	 */
	public Empleador copyDTOToEntity(Empleador empleador){

		if (this.getEstadoEmpleador() != null) {
			empleador.setEstadoEmpleador(this.getEstadoEmpleador());
		}
		if (this.getExpulsionSubsanada() != null) {
			empleador.setExpulsionSubsanada(this.getExpulsionSubsanada());
		}
		if (this.getFechaCambioEstadoAfiliacion() != null) {
			empleador.setFechaCambioEstadoAfiliacion(new Date(this.getFechaCambioEstadoAfiliacion()));
		}
		if (this.getIdEmpleador() != null) {
			empleador.setIdEmpleador(this.getIdEmpleador());
		}
		if (this.getMedioDePagoSubsidioMonetario() != null) {
			empleador.setMedioDePagoSubsidioMonetario(this.getMedioDePagoSubsidioMonetario());
		}
		if (this.getMotivoDesafiliacion() != null) {
			empleador.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
		}
		if (this.getNumeroTotalTrabajadores() != null) {
			empleador.setNumeroTotalTrabajadores(this.getNumeroTotalTrabajadores());
		}
		if (this.getPeriodoUltimaNomina() != null) {
			empleador.setPeriodoUltimaNomina(new Date(this.getPeriodoUltimaNomina()));
		}
		if (this.getFechaRetiro() != null) {
			empleador.setFechaRetiro(new Date(this.getFechaRetiro()));
		}
		if (this.getValorTotalUltimaNomina() != null) {
			empleador.setValorTotalUltimaNomina(this.getValorTotalUltimaNomina());
		}
		Empresa empresa = super.convertToEmpresaEntity();
		if(empresa.getIdEmpresa()!=null){
			empleador.setEmpresa(empresa);
		}
		if (this.getFechaSubsancionExpulsion() != null) {
			empleador.setFechaSubsancionExpulsion(new Date(this.getFechaSubsancionExpulsion()));
		}
		if (this.getMotivoSubsanacionExpulsion() != null) {
			empleador.setMotivoSubsanacionExpulsion(this.getMotivoSubsanacionExpulsion());
		}
		if (this.getFechaRetiroTotalTrabajadores() != null) {
			empleador.setFechaRetiroTotalTrabajadores(new Date(this.getFechaRetiroTotalTrabajadores()));
		}
		if (this.getCantIngresoBandejaCeroTrabajadores() != null) {
			empleador.setCantIngresoBandejaCeroTrabajadores(this.getCantIngresoBandejaCeroTrabajadores());
		}
		if (this.getFechaGestionDesafiliacion() != null) {
			empleador.setFechaGestionDesafiliacion(new Date(this.getFechaGestionDesafiliacion()));
		}
        if (this.getDiaHabilVencimientoAporte() != null) {
            empleador.setDiaHabilVencimientoAporte(this.getDiaHabilVencimientoAporte());
        }
        
        if (this.getMarcaExpulsion() != null ){
            empleador.setMarcaExpulsion(this.getMarcaExpulsion());
        }
        if (this.getRetencionSubsidioActiva() != null) {
            empleador.setRetencionSubsidioActiva(this.getRetencionSubsidioActiva());
        }
        if (this.getMotivoRetencionSubsidio() != null) {
            empleador.setMotivoRetencionSubsidio(this.getMotivoRetencionSubsidio());
        }
        if (this.getMotivoInactivaRetencionSubsidio() != null) {
            empleador.setMotivoInactivaRetencionSubsidio(this.getMotivoInactivaRetencionSubsidio());
        }
        
        empleador.setCanalReingreso(this.getCanalReingreso());
        empleador.setReferenciaAporteReingreso(this.getReferenciaAporteReingreso());
        
		return empleador;
	}
	/**
	 * Método que retorna el valor de idEmpleador.
	 * @return valor de idEmpleador.
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}
	/**
	 * Método encargado de modificar el valor de idEmpleador.
	 * @param valor para modificar idEmpleador.
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}
	/**
	 * Método que retorna el valor de estadoAportesEmpleador.
	 * @return valor de estadoAportesEmpleador.
	 */
	public EstadoAportesEmpleadorEnum getEstadoAportesEmpleador() {
		return estadoAportesEmpleador;
	}
	/**
	 * Método encargado de modificar el valor de estadoAportesEmpleador.
	 * @param valor para modificar estadoAportesEmpleador.
	 */
	public void setEstadoAportesEmpleador(EstadoAportesEmpleadorEnum estadoAportesEmpleador) {
		this.estadoAportesEmpleador = estadoAportesEmpleador;
	}
	/**
	 * Método que retorna el valor de motivoDesafiliacion.
	 * @return valor de motivoDesafiliacion.
	 */
	public MotivoDesafiliacionEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}
	/**
	 * Método encargado de modificar el valor de motivoDesafiliacion.
	 * @param valor para modificar motivoDesafiliacion.
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}
	/**
	 * Método que retorna el valor de expulsionSubsanada.
	 * @return valor de expulsionSubsanada.
	 */
	public Boolean getExpulsionSubsanada() {
		return expulsionSubsanada;
	}
	/**
	 * Método encargado de modificar el valor de expulsionSubsanada.
	 * @param valor para modificar expulsionSubsanada.
	 */
	public void setExpulsionSubsanada(Boolean expulsionSubsanada) {
		this.expulsionSubsanada = expulsionSubsanada;
	}
	/**
	 * Método que retorna el valor de fechaCambioEstadoAfiliacion.
	 * @return valor de fechaCambioEstadoAfiliacion.
	 */
	public Long getFechaCambioEstadoAfiliacion() {
		return fechaCambioEstadoAfiliacion;
	}
	/**
	 * Método encargado de modificar el valor de fechaCambioEstadoAfiliacion.
	 * @param valor para modificar fechaCambioEstadoAfiliacion.
	 */
	public void setFechaCambioEstadoAfiliacion(Long fechaCambioEstadoAfiliacion) {
		this.fechaCambioEstadoAfiliacion = fechaCambioEstadoAfiliacion;
	}
	/**
	 * Método que retorna el valor de numeroTotalTrabajadores.
	 * @return valor de numeroTotalTrabajadores.
	 */
	public Integer getNumeroTotalTrabajadores() {
		return numeroTotalTrabajadores;
	}
	/**
	 * Método encargado de modificar el valor de numeroTotalTrabajadores.
	 * @param valor para modificar numeroTotalTrabajadores.
	 */
	public void setNumeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
		this.numeroTotalTrabajadores = numeroTotalTrabajadores;
	}
	/**
	 * Método que retorna el valor de valorTotalUltimaNomina.
	 * @return valor de valorTotalUltimaNomina.
	 */
	public BigDecimal getValorTotalUltimaNomina() {
		return valorTotalUltimaNomina;
	}
	/**
	 * Método encargado de modificar el valor de valorTotalUltimaNomina.
	 * @param valor para modificar valorTotalUltimaNomina.
	 */
	public void setValorTotalUltimaNomina(BigDecimal valorTotalUltimaNomina) {
		this.valorTotalUltimaNomina = valorTotalUltimaNomina;
	}
	/**
	 * Método que retorna el valor de periodoUltimaNomina.
	 * @return valor de periodoUltimaNomina.
	 */
	public Long getPeriodoUltimaNomina() {
		return periodoUltimaNomina;
	}
	/**
	 * Método encargado de modificar el valor de periodoUltimaNomina.
	 * @param valor para modificar periodoUltimaNomina.
	 */
	public void setPeriodoUltimaNomina(Long periodoUltimaNomina) {
		this.periodoUltimaNomina = periodoUltimaNomina;
	}
	
	/**
	 * @return the medioDePagoSubsidioMonetario
	 */
	public TipoMedioDePagoEnum getMedioDePagoSubsidioMonetario() {
		return medioDePagoSubsidioMonetario;
	}

	/**
	 * @param medioDePagoSubsidioMonetario the medioDePagoSubsidioMonetario to set
	 */
	public void setMedioDePagoSubsidioMonetario(TipoMedioDePagoEnum medioDePagoSubsidioMonetario) {
		this.medioDePagoSubsidioMonetario = medioDePagoSubsidioMonetario;
	}

	/**
	 * Método que retorna el valor de estadoEmpleador.
	 * @return valor de estadoEmpleador.
	 */
	public EstadoEmpleadorEnum getEstadoEmpleador() {
		return estadoEmpleador;
	}
	/**
	 * Método encargado de modificar el valor de estadoEmpleador.
	 * @param valor para modificar estadoEmpleador.
	 */
	public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
		this.estadoEmpleador = estadoEmpleador;
	}
	/**
	 * Método que retorna el valor de fechaRetiro.
	 * @return valor de fechaRetiro.
	 */
	public Long getFechaRetiro() {
		return fechaRetiro;
	}
	/**
	 * Método encargado de modificar el valor de fechaRetiro.
	 * @param valor para modificar fechaRetiro.
	 */
	public void setFechaRetiro(Long fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}
	
	/**
	 * @return the fechaSubsancionExpulsion
	 */
	public Long getFechaSubsancionExpulsion() {
		return fechaSubsancionExpulsion;
	}

	/**
	 * @param fechaSubsancionExpulsion the fechaSubsancionExpulsion to set
	 */
	public void setFechaSubsancionExpulsion(Long fechaSubsancionExpulsion) {
		this.fechaSubsancionExpulsion = fechaSubsancionExpulsion;
	}

	/**
	 * @return the motivoSubsanacionExpulsion
	 */
	public String getMotivoSubsanacionExpulsion() {
		return motivoSubsanacionExpulsion;
	}

	/**
	 * @param motivoSubsanacionExpulsion the motivoSubsanacionExpulsion to set
	 */
	public void setMotivoSubsanacionExpulsion(String motivoSubsanacionExpulsion) {
		this.motivoSubsanacionExpulsion = motivoSubsanacionExpulsion;
	}

	/**
	 * @return the fechaRetiroTotalTrabajadores
	 */
	public Long getFechaRetiroTotalTrabajadores() {
		return fechaRetiroTotalTrabajadores;
	}

	/**
	 * @param fechaRetiroTotalTrabajadores the fechaRetiroTotalTrabajadores to set
	 */
	public void setFechaRetiroTotalTrabajadores(Long fechaRetiroTotalTrabajadores) {
		this.fechaRetiroTotalTrabajadores = fechaRetiroTotalTrabajadores;
	}

	/**
	 * @return the cantIngresoBandejaCeroTrabajadores
	 */
	public Short getCantIngresoBandejaCeroTrabajadores() {
		return cantIngresoBandejaCeroTrabajadores;
	}

	/**
	 * @param cantIngresoBandejaCeroTrabajadores the cantIngresoBandejaCeroTrabajadores to set
	 */
	public void setCantIngresoBandejaCeroTrabajadores(Short cantIngresoBandejaCeroTrabajadores) {
		this.cantIngresoBandejaCeroTrabajadores = cantIngresoBandejaCeroTrabajadores;
	}

	/**
	 * @return the fechaGestionDesafiliacion
	 */
	public Long getFechaGestionDesafiliacion() {
		return fechaGestionDesafiliacion;
	}

	/**
	 * @param fechaGestionDesafiliacion the fechaGestionDesafiliacion to set
	 */
	public void setFechaGestionDesafiliacion(Long fechaGestionDesafiliacion) {
		this.fechaGestionDesafiliacion = fechaGestionDesafiliacion;
	}

	/**
	 * @return the beneficio
	 */
	public BeneficioEmpleadorModeloDTO getBeneficio() {
		return beneficio;
	}

	/**
	 * @param beneficio the beneficio to set
	 */
	public void setBeneficio(BeneficioEmpleadorModeloDTO beneficio) {
		this.beneficio = beneficio;
	}

    /**
     * @return the diaHabilVencimientoAporte
     */
    public Short getDiaHabilVencimientoAporte() {
        return diaHabilVencimientoAporte;
    }

    /**
     * @param diaHabilVencimientoAporte the diaHabilVencimientoAporte to set
     */
    public void setDiaHabilVencimientoAporte(Short diaHabilVencimientoAporte) {
        this.diaHabilVencimientoAporte = diaHabilVencimientoAporte;
    }

    /**
     * Método que retorna el valor de marcaExpulsion.
     * @return valor de marcaExpulsion.
     */
    public ExpulsionEnum getMarcaExpulsion() {
        return marcaExpulsion;
    }

    /**
     * Método encargado de modificar el valor de marcaExpulsion.
     * @param valor para modificar marcaExpulsion.
     */
    public void setMarcaExpulsion(ExpulsionEnum marcaExpulsion) {
        this.marcaExpulsion = marcaExpulsion;
    }

    /**
     * @return the retencionSubsidioActiva
     */
    public Boolean getRetencionSubsidioActiva() {
        return retencionSubsidioActiva;
    }

    /**
     * @param retencionSubsidioActiva the retencionSubsidioActiva to set
     */
    public void setRetencionSubsidioActiva(Boolean retencionSubsidioActiva) {
        this.retencionSubsidioActiva = retencionSubsidioActiva;
    }

    /**
     * @return the motivoRetencionSubsidio
     */
    public MotivoRetencionSubsidioEnum getMotivoRetencionSubsidio() {
        return motivoRetencionSubsidio;
    }

    /**
     * @param motivoRetencionSubsidio the motivoRetencionSubsidio to set
     */
    public void setMotivoRetencionSubsidio(MotivoRetencionSubsidioEnum motivoRetencionSubsidio) {
        this.motivoRetencionSubsidio = motivoRetencionSubsidio;
    }

    /**
     * @return the motivoInactivaRetencionSubsidio
     */
    public MotivoInactivacionRetencionSubsidioEnum getMotivoInactivaRetencionSubsidio() {
        return motivoInactivaRetencionSubsidio;
    }

    /**
     * @param motivoInactivaRetencionSubsidio the motivoInactivaRetencionSubsidio to set
     */
    public void setMotivoInactivaRetencionSubsidio(MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio) {
        this.motivoInactivaRetencionSubsidio = motivoInactivaRetencionSubsidio;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoSolicitudAfiliacionEmpleadorEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the entidadPagadora
     */
    public Boolean getEntidadPagadora() {
        return entidadPagadora;
    }

    /**
     * @param entidadPagadora the entidadPagadora to set
     */
    public void setEntidadPagadora(Boolean entidadPagadora) {
        this.entidadPagadora = entidadPagadora;
    }

    /**
     * @return the tipoBeneficio
     */
    public TipoBeneficioEnum getTipoBeneficio() {
        return tipoBeneficio;
    }

    /**
     * @param tipoBeneficio the tipoBeneficio to set
     */
    public void setTipoBeneficio(TipoBeneficioEnum tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    /**
     * @return the tipoPagador
     */
    public String getTipoPagador() {
        return tipoPagador;
    }

    /**
     * @param tipoPagador the tipoPagador to set
     */
    public void setTipoPagador(String tipoPagador) {
        this.tipoPagador = tipoPagador;
    }

    /**
     * @return the canalReingreso
     */
    public CanalRecepcionEnum getCanalReingreso() {
        return canalReingreso;
    }

    /**
     * @param canalReingreso the canalReingreso to set
     */
    public void setCanalReingreso(CanalRecepcionEnum canalReingreso) {
        this.canalReingreso = canalReingreso;
    }

    /**
     * @return the referenciaAporteReingreso
     */
    public Long getReferenciaAporteReingreso() {
        return referenciaAporteReingreso;
    }

    /**
     * @param referenciaAporteReingreso the referenciaAporteReingreso to set
     */
    public void setReferenciaAporteReingreso(Long referenciaAporteReingreso) {
        this.referenciaAporteReingreso = referenciaAporteReingreso;
    }	


    /**
     * @return the trasladoCajasCompensacion
     */
    public Boolean getTrasladoCajasCompensacion() {
        return trasladoCajasCompensacion;
    }

    /**
     * @param trasladoCajasCompensacion the referenciaAporteReingreso to set
     */
    public void setTrasladoCajasCompensacion(Boolean trasladoCajasCompensacion) {
        this.trasladoCajasCompensacion = trasladoCajasCompensacion;
    }

	public String getCelularOficinaPrincipal() {
		return celularOficinaPrincipal;
	}

	public void setCelularOficinaPrincipal(String celularOficinaPrincipal) {
		this.celularOficinaPrincipal = celularOficinaPrincipal;
	}

	public String getEmail1OficinaPrincipal() {
		return email1OficinaPrincipal;
	}

	public void setEmail1OficinaPrincipal(String email1OficinaPrincipal) {
		this.email1OficinaPrincipal = email1OficinaPrincipal;
	}

	public String getEmail2EnvioDeCorrespondencia() {
		return email2EnvioDeCorrespondencia;
	}

	public void setEmail2EnvioDeCorrespondencia(String email2EnvioDeCorrespondencia) {
		this.email2EnvioDeCorrespondencia = email2EnvioDeCorrespondencia;
	}

	public String getEmail3NotificacionJudicial() {
		return email3NotificacionJudicial;
	}

	public void setEmail3NotificacionJudicial(String email3NotificacionJudicial) {
		this.email3NotificacionJudicial = email3NotificacionJudicial;
	}

	public String getTelefono1OficinaPrincipal() {
		return telefono1OficinaPrincipal;
	}

	public void setTelefono1OficinaPrincipal(String telefono1OficinaPrincipal) {
		this.telefono1OficinaPrincipal = telefono1OficinaPrincipal;
	}

	public String getTelefono2EnvioDeCorrespondencia() {
		return telefono2EnvioDeCorrespondencia;
	}

	public void setTelefono2EnvioDeCorrespondencia(String telefono2EnvioDeCorrespondencia) {
		this.telefono2EnvioDeCorrespondencia = telefono2EnvioDeCorrespondencia;
	}

	public String getTelefono3NotificacionJudicial() {
		return telefono3NotificacionJudicial;
	}

	public void setTelefono3NotificacionJudicial(String telefono3NotificacionJudicial) {
		this.telefono3NotificacionJudicial = telefono3NotificacionJudicial;
	}

	public String getResponsable1DeLaCajaParaContacto() {
		return responsable1DeLaCajaParaContacto;
	}

	public void setResponsable1DeLaCajaParaContacto(String responsable1DeLaCajaParaContacto) {
		this.responsable1DeLaCajaParaContacto = responsable1DeLaCajaParaContacto;
	}

	public String getResponsable2DeLaCajaParaContacto() {
		return responsable2DeLaCajaParaContacto;
	}

	public void setResponsable2DeLaCajaParaContacto(String responsable2DeLaCajaParaContacto) {
		this.responsable2DeLaCajaParaContacto = responsable2DeLaCajaParaContacto;
	}

	public Long getIdUbicacionPrincipal() {
		return idUbicacionPrincipal;
	}

	public void setIdUbicacionPrincipal(Long idUbicacionPrincipal) {
		this.idUbicacionPrincipal = idUbicacionPrincipal;
	}

	public Long getIdUbicacionEnvioDeCorrespondencia() {
		return idUbicacionEnvioDeCorrespondencia;
	}

	public void setIdUbicacionEnvioDeCorrespondencia(Long idUbicacionEnvioDeCorrespondencia) {
		this.idUbicacionEnvioDeCorrespondencia = idUbicacionEnvioDeCorrespondencia;
	}

	public Long getIdUbicacionNotificacionJudicial() {
		return idUbicacionNotificacionJudicial;
	}

	public void setIdUbicacionNotificacionJudicial(Long idUbicacionNotificacionJudicial) {
		this.idUbicacionNotificacionJudicial = idUbicacionNotificacionJudicial;
	}

	public Boolean isExpulsionSubsanada() {
		return this.expulsionSubsanada;
	}

	public Boolean isRetencionSubsidioActiva() {
		return this.retencionSubsidioActiva;
	}

	public Boolean isEntidadPagadora() {
		return this.entidadPagadora;
	}

	public Boolean isTrasladoCajasCompensacion() {
		return this.trasladoCajasCompensacion;
	}

	public Long getIdSucursalEmpleador() {
		return this.idSucursalEmpleador;
	}

	public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
		this.idSucursalEmpleador = idSucursalEmpleador;
	}

	@Override
	public String toString() {
		return "{" +
			" idEmpleador='" + getIdEmpleador() + "'" +
			", estadoAportesEmpleador='" + getEstadoAportesEmpleador() + "'" +
			", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
			", expulsionSubsanada='" + getExpulsionSubsanada() + "'" +
			", fechaCambioEstadoAfiliacion='" + getFechaCambioEstadoAfiliacion() + "'" +
			", numeroTotalTrabajadores='" + getNumeroTotalTrabajadores() + "'" +
			", valorTotalUltimaNomina='" + getValorTotalUltimaNomina() + "'" +
			", periodoUltimaNomina='" + getPeriodoUltimaNomina() + "'" +
			", medioDePagoSubsidioMonetario='" + getMedioDePagoSubsidioMonetario() + "'" +
			", estadoEmpleador='" + getEstadoEmpleador() + "'" +
			", fechaRetiro='" + getFechaRetiro() + "'" +
			", fechaSubsancionExpulsion='" + getFechaSubsancionExpulsion() + "'" +
			", motivoSubsanacionExpulsion='" + getMotivoSubsanacionExpulsion() + "'" +
			", fechaRetiroTotalTrabajadores='" + getFechaRetiroTotalTrabajadores() + "'" +
			", cantIngresoBandejaCeroTrabajadores='" + getCantIngresoBandejaCeroTrabajadores() + "'" +
			", fechaGestionDesafiliacion='" + getFechaGestionDesafiliacion() + "'" +
			", beneficio='" + getBeneficio() + "'" +
			", diaHabilVencimientoAporte='" + getDiaHabilVencimientoAporte() + "'" +
			", marcaExpulsion='" + getMarcaExpulsion() + "'" +
			", retencionSubsidioActiva='" + getRetencionSubsidioActiva() + "'" +
			", motivoRetencionSubsidio='" + getMotivoRetencionSubsidio() + "'" +
			", motivoInactivaRetencionSubsidio='" + getMotivoInactivaRetencionSubsidio() + "'" +
			", tipoBeneficio='" + getTipoBeneficio() + "'" +
			", clasificacion='" + getClasificacion() + "'" +
			", estadoSolicitud='" + getEstadoSolicitud() + "'" +
			", idEmpresa='" + getIdEmpresa() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", entidadPagadora='" + getEntidadPagadora() + "'" +
			", tipoPagador='" + getTipoPagador() + "'" +
			", canalReingreso='" + getCanalReingreso() + "'" +
			", referenciaAporteReingreso='" + getReferenciaAporteReingreso() + "'" +
			", celularOficinaPrincipal='" + getCelularOficinaPrincipal() + "'" +
			", email1OficinaPrincipal='" + getEmail1OficinaPrincipal() + "'" +
			", email2EnvioDeCorrespondencia='" + getEmail2EnvioDeCorrespondencia() + "'" +
			", email3NotificacionJudicial='" + getEmail3NotificacionJudicial() + "'" +
			", telefono1OficinaPrincipal='" + getTelefono1OficinaPrincipal() + "'" +
			", telefono2EnvioDeCorrespondencia='" + getTelefono2EnvioDeCorrespondencia() + "'" +
			", telefono3NotificacionJudicial='" + getTelefono3NotificacionJudicial() + "'" +
			", responsable1DeLaCajaParaContacto='" + getResponsable1DeLaCajaParaContacto() + "'" +
			", responsable2DeLaCajaParaContacto='" + getResponsable2DeLaCajaParaContacto() + "'" +
			", idUbicacionPrincipal='" + getIdUbicacionPrincipal() + "'" +
			", idUbicacionEnvioDeCorrespondencia='" + getIdUbicacionEnvioDeCorrespondencia() + "'" +
			", idUbicacionNotificacionJudicial='" + getIdUbicacionNotificacionJudicial() + "'" +
			", trasladoCajasCompensacion='" + getTrasladoCajasCompensacion() + "'" +
			"}";
	}

}
