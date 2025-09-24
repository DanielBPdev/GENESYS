package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.cartera.GestionCarteraDTO;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.MotivoInactivacionRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.MotivoRetencionSubsidioEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoAportesEmpleadorEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

@XmlRootElement
public class EmpleadorDTO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long idEmpleador;

	private PersonaDTO persona;
	
	private EmpresaDTO empresa;

	private EstadoAportesEmpleadorEnum estadoAportesEmpleador;

	private MotivoDesafiliacionEnum motivoDesafiliacion;

	private Boolean expulsionSubsanada;

	private Date fechaCambioEstadoAfiliacion;

	private String nombreComercial;

	private Date fechaConstitucion;

	private NaturalezaJuridicaEnum naturalezaJuridica;

	private CodigoCIIUDTO codigoCIIU;

	private ARLDTO arl;

	private Integer numeroTotalTrabajadores;

	private BigDecimal valorTotalUltimaNomina;

	private Date periodoUltimaNomina;

	private Integer idUltimaCajaCompensacion;

	private String paginaWeb;

	private Long idPersonaRepresentanteLegal;

	private Long idPersonaRepresentanteLegalSuplente;

	private TipoMedioDePagoEnum medioDePagoSubsidioMonetario;

	private EstadoEmpleadorEnum estadoEmpleador;

	private Boolean especialRevision;
	
	private EstadoAfiliadoEnum estadoAfiliado;
	
	private Date fechaRetiro;
	
	private Date fechaSubsancionExpulsion;
	
	private String motivoSubsanacionExpulsion;
	
	private Short diaHabilVencimientoAporte;
	
	private Date fechaRetiroTotalTrabajadores;
	
	private UbicacionDTO ubicacionPrincipal;
	
	/**
     * Hace referencia a los beneficios que tiene asociado el empleador
     */
	private BeneficioEmpleadorModeloDTO beneficio; 

    private Boolean retencionSubsidioActiva;
    
    private MotivoRetencionSubsidioEnum motivoRetencionSubsidio;

    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidio;
    
    /**
     * Canal de reintegro del empleador
     * */
    private CanalRecepcionEnum canalReingreso;
    
    /**
     * Referencia al aporte general con el que se general el reintegro
     * */
    private Long referenciaAporteReingreso;

	/**
	 * Estado de cartera que tiene la persona
	 */
	private EstadoCarteraEnum estadoCartera;	
	/**
	 * GestionCarteraDTO tiene la información de cartera como los números de operación o líneas de cobro de la persona 
	 */
	private GestionCarteraDTO gestionCartera;

	/**
	 * trasladoCajasCompensacion marca para los empleadores que son transferibles entre CFF 
	 */
	private Boolean trasladoCajasCompensacion;

	/**
	 * Contructor vacio
	 */
	public EmpleadorDTO() {
		
	}
	
	//Constructor usado para la generación del reporte de empleadores afiliados
	//que hasta el momento no tienen registrada aprobación del consejo directivo
	public EmpleadorDTO(Empleador empleador,Short idMunicipio){
	    this.setIdEmpleador(empleador.getIdEmpleador());
	    this.setNumeroTotalTrabajadores(empleador.getNumeroTotalTrabajadores());
	    this.setValorTotalUltimaNomina(empleador.getValorTotalUltimaNomina());
	    this.setFechaCambioEstadoAfiliacion(empleador.getFechaCambioEstadoAfiliacion());
	    if(empleador.getEmpresa()!=null){
	        this.setEmpresa(EmpresaDTO.convertEmpresaToDTO(empleador.getEmpresa()));
        }
	    if (idMunicipio != null) {
	        UbicacionDTO ubicacion = new UbicacionDTO();
	        ubicacion.setIdMunicipio(idMunicipio);
	        this.setUbicacionPrincipal(ubicacion);
        }
	    
	}
	
	//Constructor usado para la generación del reporte de empleadores afiliados
		//que hasta el momento no tienen registrada aprobación del consejo directivo
		public EmpleadorDTO(Empleador empleador){
		    this.setIdEmpleador(empleador.getIdEmpleador());
		    if(empleador.getEmpresa().getPersona()!=null){
	            this.setPersona(PersonaDTO.convertPersonaToDTO(empleador.getEmpresa().getPersona(), null));
	        }
		    
		}
	
	public static EmpleadorDTO convertEmpleadorToDTO(Empleador empleador) {
        EmpleadorDTO empleadorDTO = new EmpleadorDTO();
        empleadorDTO.setIdEmpleador(empleador.getIdEmpleador());
        //empleadorDTO.setEstadoAportesEmpleador(empleador.getEstadoAportesEmpleador());
        empleadorDTO.setMotivoDesafiliacion(empleador.getMotivoDesafiliacion());
        empleadorDTO.setExpulsionSubsanada(empleador.getExpulsionSubsanada());
        empleadorDTO.setFechaCambioEstadoAfiliacion(empleador.getFechaCambioEstadoAfiliacion());
        empleadorDTO.setFechaRetiro(empleador.getFechaRetiro());
        empleadorDTO.setNumeroTotalTrabajadores(empleador.getNumeroTotalTrabajadores());
        empleadorDTO.setValorTotalUltimaNomina(empleador.getValorTotalUltimaNomina());
        empleadorDTO.setPeriodoUltimaNomina(empleador.getPeriodoUltimaNomina());
        empleadorDTO.setEstadoEmpleador(empleador.getEstadoEmpleador());
        empleadorDTO.setNombreComercial(empleador.getEmpresa().getNombreComercial());
        empleadorDTO.setFechaConstitucion(empleador.getEmpresa().getFechaConstitucion());
        empleadorDTO.setNaturalezaJuridica(empleador.getEmpresa().getNaturalezaJuridica());
        empleadorDTO.setIdUltimaCajaCompensacion(empleador.getEmpresa().getIdUltimaCajaCompensacion());
        empleadorDTO.setPaginaWeb(empleador.getEmpresa().getPaginaWeb());
        empleadorDTO.setIdPersonaRepresentanteLegal(empleador.getEmpresa().getIdPersonaRepresentanteLegal());
        empleadorDTO.setIdPersonaRepresentanteLegalSuplente(empleador.getEmpresa().getIdPersonaRepresentanteLegalSuplente());
        empleadorDTO.setEspecialRevision(empleador.getEmpresa().getEspecialRevision());
        PersonaDTO persona = PersonaDTO.convertPersonaToDTO(empleador.getEmpresa().getPersona(), null);
        empleadorDTO.setEstadoAfiliado(persona.getEstadoAfiliadoCaja());   
        if(empleador.getEmpresa().getArl()!=null){
            empleadorDTO.setArl(ARLDTO.convertArlToDTO(empleador.getEmpresa().getArl()));
        }
        if(empleador.getEmpresa().getCodigoCIIU()!=null){
            empleadorDTO.setCodigoCIIU(CodigoCIIUDTO.convertCodigoCIIUToDto(empleador.getEmpresa().getCodigoCIIU()));
        }
        if(empleador.getEmpresa()!=null){
            empleadorDTO.setEmpresa(EmpresaDTO.convertEmpresaToDTO(empleador.getEmpresa()));
        }
        if(empleador.getEmpresa().getPersona()!=null){
            empleadorDTO.setPersona(PersonaDTO.convertPersonaToDTO(empleador.getEmpresa().getPersona(), null));
        }
        /*Refactor Medios De Pago.*/
        empleadorDTO.setMedioDePagoSubsidioMonetario(empleador.getMedioDePagoSubsidioMonetario());
        
        empleadorDTO.setDiaHabilVencimientoAporte(empleador.getDiaHabilVencimientoAporte());
        empleadorDTO.setRetencionSubsidioActiva(empleador.getRetencionSubsidioActiva());
        empleadorDTO.setMotivoRetencionSubsidio(empleador.getMotivoRetencionSubsidio());
        empleadorDTO.setMotivoInactivaRetencionSubsidio(empleador.getMotivoInactivaRetencionSubsidio());
        empleadorDTO.setCanalReingreso(empleador.getCanalReingreso());
        empleadorDTO.setReferenciaAporteReingreso(empleador.getReferenciaAporteReingreso());
		empleadorDTO.setTrasladoCajasCompensacion(empleador.getTrasladoCajasCompensacion());
        return empleadorDTO;
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
	 * @return the persona
	 */
	public PersonaDTO getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	/**
	 * @return the estadoAportesEmpleador
	 */
	public EstadoAportesEmpleadorEnum getEstadoAportesEmpleador() {
		return estadoAportesEmpleador;
	}

	/**
	 * @param estadoAportesEmpleador
	 *            the estadoAportesEmpleador to set
	 */
	public void setEstadoAportesEmpleador(EstadoAportesEmpleadorEnum estadoAportesEmpleador) {
		this.estadoAportesEmpleador = estadoAportesEmpleador;
	}

	/**
	 * @return the motivoDesafiliacion
	 */
	public MotivoDesafiliacionEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * @param motivoDesafiliacion
	 *            the motivoDesafiliacion to set
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}

	/**
	 * @return the expulsionSubsanada
	 */
	public Boolean getExpulsionSubsanada() {
		return expulsionSubsanada;
	}

	/**
	 * @param expulsionSubsanada
	 *            the expulsionSubsanada to set
	 */
	public void setExpulsionSubsanada(Boolean expulsionSubsanada) {
		this.expulsionSubsanada = expulsionSubsanada;
	}

	/**
	 * @return the fechaCambioEstadoAfiliacion
	 */
	public Date getFechaCambioEstadoAfiliacion() {
		return fechaCambioEstadoAfiliacion;
	}

	/**
	 * @param fechaCambioEstadoAfiliacion
	 *            the fechaCambioEstadoAfiliacion to set
	 */
	public void setFechaCambioEstadoAfiliacion(Date fechaCambioEstadoAfiliacion) {
		this.fechaCambioEstadoAfiliacion = fechaCambioEstadoAfiliacion;
	}

	/**
	 * @return the nombreComercial
	 */
	public String getNombreComercial() {
		return nombreComercial;
	}

	/**
	 * @param nombreComercial
	 *            the nombreComercial to set
	 */
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	/**
	 * @return the fechaConstitucion
	 */
	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	/**
	 * @param fechaConstitucion
	 *            the fechaConstitucion to set
	 */
	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	/**
	 * @return the naturalezaJuridica
	 */
	public NaturalezaJuridicaEnum getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	/**
	 * @param naturalezaJuridica
	 *            the naturalezaJuridica to set
	 */
	public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
		this.naturalezaJuridica = naturalezaJuridica;
	}

	/**
	 * @return the codigoCIIU
	 */
	public CodigoCIIUDTO getCodigoCIIU() {
		return codigoCIIU;
	}

	/**
	 * @param codigoCIIU
	 *            the codigoCIIU to set
	 */
	public void setCodigoCIIU(CodigoCIIUDTO codigoCIIU) {
		this.codigoCIIU = codigoCIIU;
	}

	/**
	 * @return the arl
	 */
	public ARLDTO getArl() {
		return arl;
	}

	/**
	 * @param arl
	 *            the arl to set
	 */
	public void setArl(ARLDTO arl) {
		this.arl = arl;
	}

	/**
	 * @return the numeroTotalTrabajadores
	 */
	public Integer getNumeroTotalTrabajadores() {
		return numeroTotalTrabajadores;
	}

	/**
	 * @param numeroTotalTrabajadores
	 *            the numeroTotalTrabajadores to set
	 */
	public void setNumeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
		this.numeroTotalTrabajadores = numeroTotalTrabajadores;
	}

	/**
	 * @return the valorTotalUltimaNomina
	 */
	public BigDecimal getValorTotalUltimaNomina() {
		return valorTotalUltimaNomina;
	}

	/**
	 * @param valorTotalUltimaNomina
	 *            the valorTotalUltimaNomina to set
	 */
	public void setValorTotalUltimaNomina(BigDecimal valorTotalUltimaNomina) {
		this.valorTotalUltimaNomina = valorTotalUltimaNomina;
	}

	/**
	 * @return the periodoUltimaNomina
	 */
	public Date getPeriodoUltimaNomina() {
		return periodoUltimaNomina;
	}

	/**
	 * @param periodoUltimaNomina
	 *            the periodoUltimaNomina to set
	 */
	public void setPeriodoUltimaNomina(Date periodoUltimaNomina) {
		this.periodoUltimaNomina = periodoUltimaNomina;
	}

	/**
	 * @return the idUltimaCajaCompensacion
	 */
	public Integer getIdUltimaCajaCompensacion() {
		return idUltimaCajaCompensacion;
	}

	/**
	 * @param idUltimaCajaCompensacion
	 *            the idUltimaCajaCompensacion to set
	 */
	public void setIdUltimaCajaCompensacion(Integer idUltimaCajaCompensacion) {
		this.idUltimaCajaCompensacion = idUltimaCajaCompensacion;
	}

	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * @param paginaWeb
	 *            the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * @return the idPersonaRepresentanteLegal
	 */
	public Long getIdPersonaRepresentanteLegal() {
		return idPersonaRepresentanteLegal;
	}

	/**
	 * @param idPersonaRepresentanteLegal
	 *            the idPersonaRepresentanteLegal to set
	 */
	public void setIdPersonaRepresentanteLegal(Long idPersonaRepresentanteLegal) {
		this.idPersonaRepresentanteLegal = idPersonaRepresentanteLegal;
	}

	/**
	 * @return the idPersonaRepresentanteLegalSuplente
	 */
	public Long getIdPersonaRepresentanteLegalSuplente() {
		return idPersonaRepresentanteLegalSuplente;
	}

	/**
	 * @param idPersonaRepresentanteLegalSuplente
	 *            the idPersonaRepresentanteLegalSuplente to set
	 */
	public void setIdPersonaRepresentanteLegalSuplente(Long idPersonaRepresentanteLegalSuplente) {
		this.idPersonaRepresentanteLegalSuplente = idPersonaRepresentanteLegalSuplente;
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
	 * @return the estadoEmpleador
	 */
	public EstadoEmpleadorEnum getEstadoEmpleador() {
		return estadoEmpleador;
	}

	/**
	 * @param estadoEmpleador
	 *            the estadoEmpleador to set
	 */
	public void setEstadoEmpleador(EstadoEmpleadorEnum estadoEmpleador) {
		this.estadoEmpleador = estadoEmpleador;
	}

	/**
	 * @return the especialRevision
	 */
	public Boolean getEspecialRevision() {
		return especialRevision;
	}

	/**
	 * @param especialRevision
	 *            the especialRevision to set
	 */
	public void setEspecialRevision(Boolean especialRevision) {
		this.especialRevision = especialRevision;
	}

	/**
	 * Método que retorna el valor de estadoAfiliado.
	 * @return valor de estadoAfiliado.
	 */
	public EstadoAfiliadoEnum getEstadoAfiliado() {
		return estadoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de estadoAfiliado.
	 * @param valor para modificar estadoAfiliado.
	 */
	public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
		this.estadoAfiliado = estadoAfiliado;
	}

    /**
     * @return the empresa
     */
    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

	/**
	 * @return fechaSubsancionExpulsion
	 */
	public Date getFechaSubsancionExpulsion() {
		return fechaSubsancionExpulsion;
	}

	/**
	 * @param fechaSubsancionExpulsion the fechaSubsancionExpulsion to set
	 */
	public void setFechaSubsancionExpulsion(Date fechaSubsancionExpulsion) {
		this.fechaSubsancionExpulsion = fechaSubsancionExpulsion;
	}

	/**
	 * @return motivoSubsanacionExpulsion
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
     * @return the fechaRetiroTotalTrabajadores
     */
    public Date getFechaRetiroTotalTrabajadores() {
        return fechaRetiroTotalTrabajadores;
    }

    /**
     * @param fechaRetiroTotalTrabajadores the fechaRetiroTotalTrabajadores to set
     */
    public void setFechaRetiroTotalTrabajadores(Date fechaRetiroTotalTrabajadores) {
        this.fechaRetiroTotalTrabajadores = fechaRetiroTotalTrabajadores;
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
     * @return the ubicacionPrincipal
     */
    public UbicacionDTO getUbicacionPrincipal() {
        return ubicacionPrincipal;
    }

    /**
     * @param ubicacionPrincipal the ubicacionPrincipal to set
     */
    public void setUbicacionPrincipal(UbicacionDTO ubicacionPrincipal) {
        this.ubicacionPrincipal = ubicacionPrincipal;
    }

	/**
	 * @return the estadoCartera
	 */
	public EstadoCarteraEnum getEstadoCartera() {
		return estadoCartera;
	}

	/**
	 * @param estadoCartera the estadoCartera to set
	 */
	public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
		this.estadoCartera = estadoCartera;
	}

	/**
	 * @return the gestionCartera
	 */
	public GestionCarteraDTO getGestionCartera() {
		return gestionCartera;
	}

	/**
	 * @param gestionCartera the gestionCartera to set
	 */
	public void setGestionCartera(GestionCarteraDTO gestionCartera) {
		this.gestionCartera = gestionCartera;
	}

	/**
	 * @return the trasladoCajasCompensacion
	 */
	public Boolean getTrasladoCajasCompensacion() {
		return trasladoCajasCompensacion;
	}

	/**
	 * @param gestionCartera the gestionCartera to set
	 */
	public void setTrasladoCajasCompensacion(Boolean trasladoCajasCompensacion) {
		this.trasladoCajasCompensacion = trasladoCajasCompensacion;
	}
    
}
