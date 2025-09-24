package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos de un Rol Afiliado
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 */
@XmlRootElement
public class RolAfiliadoDTO implements Serializable {

	/**
	 * Identificador del Rol Afiliado
	 */
	private Long idRolAfiliado;
	
	/**
	 * Datos del Afiliado
	 */
	private AfiliadoInDTO afiliadoDTO;
	
	/**
	 * Tipo de Afiliado
	 */
	private TipoAfiliadoEnum tipoAfiliado;
	
	/**
	 * Estado del afiliado
	 */
	private EstadoAfiliadoEnum estadoAfiliado;
	
	/**
	 * Estado en Entidad Pagadora
	 */
	private EstadoActivoInactivoEnum estadoEnEntidadPagadora;
	
	/**
	 * Descripción del estado activo/inactivo de la entidad pagadora
	 */
	private ClaseIndependienteEnum claseIndependiente;
	
	/**
	 * Id del Empleador
	 */
	private Long idEmpleador;
	
	/**
	 * Id del Empleador
	 */
	private EmpleadorDTO empleador;
	
	/**
	 * Id de la Sucursal Empresa
	 */
	private Long idSucursalEmpresa;
	
	/**
	 * Id del Pagador Pensión
	 */
	private Short idPagadorPension;    
	    
    /**
     * Id que identifica al entidad pagadora asociada al rol afiliado
     */
	private Long idPagadorAportes;
	    
    /**
     * Código identificador ante entidad pagadora, aplica para cuando la persona es tipo solicitante :PENSIONADO
     */
    private String identificadorAnteEntidadPagadora;    

    /**
     * Fecha de ingreso
     */
    private Date fechaIngreso;

    /**
     * Fecha de retiro
     */
    private Date fechaRetiro;

    /**
     * Valor del salario de la mesada de ingresos
     */
    private BigDecimal valorSalarioMesadaIngresos;

    /**
     * Porcentaje del pago de los aportes
     */
    private BigDecimal porcentajePagoAportes;

    /**
     * Descripción de las posibles clases de personas independientes
     */
    private ClaseTrabajadorEnum claseTrabajador;

    /**
     * Descripción del tipo de salario del rol afiliado
     */
    private TipoSalarioEnum tipoSalario;

    /**
     * Descripción del tipo de contrato del rol afiliado     	
     */
    private TipoContratoEnum tipoContrato;

    /**
     * Descripción del cargo del afiliado
     */
    private String cargo;

    /**
     * Número de horas laboradas por mes del rol afiliado
     */
    private Short horasLaboradasMes;

    /**
     * Fecha de la afiliación
     */
    private Date fechaAfiliacion;
	    
    /**
     * Descripción de la desafiliacion del afiliado
     */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
	    
    /**
     * Indicador S/N el motivo de afiliacion es sustitucionPatronal [S=Si N=No]
     */
	private Boolean sustitucionPatronal;
    
    /**
     * Día hábil de vencimiento de aportes 
     * */
    private Short diaHabilVencimientoAporte;
    
    /**
     * Canal de recepción de novedad o solicitud de reingreso
     * */
    private CanalRecepcionEnum canalReingreso;
    
    /**
     * Referencia al aporte detallado que incluyó la novedad de reingreso
     * */
    private Long referenciaAporteReingreso;
    
    /**
     * Referencia a la solicitud de reingreso en afiliaciones 
     * */
    private Long referenciaSolicitudReingreso;
    
  public RolAfiliadoDTO (){
    	
    }
    
    public RolAfiliadoDTO (RolAfiliado roa){
    	this.setEmpleador(new EmpleadorDTO(roa.getEmpleador()));
    	this.idRolAfiliado=roa.getIdRolAfiliado();
    }
	
	/**
	 * Convierte los datos de RolAfiliado a RolAfiliadoDTO.
	 * @param grupoFamiliar
	 * @return
	 */
	public static RolAfiliadoDTO convertRolAfiliadoToDTO(RolAfiliado rolAfiliado) {
		RolAfiliadoDTO rolAfiliadoDTO = new RolAfiliadoDTO();
		if (rolAfiliado.getAfiliado() != null) {
			rolAfiliadoDTO.setAfiliadoDTO(AfiliadoInDTO.convertAfiliadoToDTO(rolAfiliado.getAfiliado(), null));
		}
		rolAfiliadoDTO.setCargo(rolAfiliado.getCargo());
		rolAfiliadoDTO.setClaseIndependiente(rolAfiliado.getClaseIndependiente());
		rolAfiliadoDTO.setClaseTrabajador(rolAfiliado.getClaseTrabajador());
		if (rolAfiliado.getEmpleador() != null && rolAfiliado.getEmpleador().getIdEmpleador() != null) {
			rolAfiliadoDTO.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
		}
		rolAfiliadoDTO.setEstadoAfiliado(rolAfiliado.getEstadoAfiliado());
		rolAfiliadoDTO.setEstadoEnEntidadPagadora(rolAfiliado.getEstadoEnEntidadPagadora());
		rolAfiliadoDTO.setFechaAfiliacion(rolAfiliado.getFechaAfiliacion());
		rolAfiliadoDTO.setFechaIngreso(rolAfiliado.getFechaIngreso());
		rolAfiliadoDTO.setFechaRetiro(rolAfiliado.getFechaRetiro());
		rolAfiliadoDTO.setHorasLaboradasMes(rolAfiliado.getHorasLaboradasMes());
		rolAfiliadoDTO.setIdentificadorAnteEntidadPagadora(rolAfiliado.getIdentificadorAnteEntidadPagadora());
		if (rolAfiliado.getPagadorAportes() != null) {
			rolAfiliadoDTO.setIdPagadorAportes(rolAfiliado.getPagadorAportes().getIdEntidadPagadora());
		}
		if (rolAfiliado.getPagadorPension() != null) {
			rolAfiliadoDTO.setIdPagadorPension(rolAfiliado.getPagadorPension().getIdAFP());	
		}
		rolAfiliadoDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
		rolAfiliadoDTO.setMotivoDesafiliacion(rolAfiliado.getMotivoDesafiliacion());
		rolAfiliadoDTO.setPorcentajePagoAportes(rolAfiliado.getPorcentajePagoAportes());
		if (rolAfiliado.getSucursalEmpleador() != null) {
			rolAfiliadoDTO.setIdSucursalEmpresa(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
		}
		rolAfiliadoDTO.setSustitucionPatronal(rolAfiliado.getSustitucionPatronal());
		rolAfiliadoDTO.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
		rolAfiliadoDTO.setTipoContrato(rolAfiliado.getTipoContrato());
		rolAfiliadoDTO.setTipoSalario(rolAfiliado.getTipoSalario());
		rolAfiliadoDTO.setValorSalarioMesadaIngresos(rolAfiliado.getValorSalarioMesadaIngresos());
		rolAfiliadoDTO.setDiaHabilVencimientoAporte(rolAfiliado.getDiaHabilVencimientoAporte());
		rolAfiliadoDTO.setCanalReingreso(rolAfiliado.getCanalReingreso());
		rolAfiliadoDTO.setReferenciaAporteReingreso(rolAfiliado.getReferenciaAporteReingreso());
		rolAfiliadoDTO.setReferenciaSolicitudReingreso(rolAfiliado.getReferenciaSolicitudReingreso());
		
		return rolAfiliadoDTO;
    }

	public void asignarDatosRolAfiliado(RolAfiliado rolAfiliado) {
		if (getSustitucionPatronal() != null) {
			rolAfiliado.setSustitucionPatronal(getSustitucionPatronal());
		}
		if (getAfiliadoDTO() != null) {
			Afiliado afiliado = new Afiliado();
			afiliado.setIdAfiliado(getAfiliadoDTO().getIdAfiliado());
			rolAfiliado.setAfiliado(afiliado);
		}
		if (getCargo() != null) {
			rolAfiliado.setCargo(getCargo());
		}
		if (getClaseIndependiente() != null) {
			rolAfiliado.setClaseIndependiente(getClaseIndependiente());
		}
		if (getClaseTrabajador() != null) {
			rolAfiliado.setClaseTrabajador(getClaseTrabajador());
		}
		if (getEstadoAfiliado() != null) {
			rolAfiliado.setEstadoAfiliado(getEstadoAfiliado());
		}
		if (getEstadoEnEntidadPagadora() != null) {
			rolAfiliado.setEstadoEnEntidadPagadora(getEstadoEnEntidadPagadora());
		}
		if (getFechaAfiliacion() != null) {
			rolAfiliado.setFechaAfiliacion(getFechaAfiliacion());
		}
		if (getFechaIngreso() != null) {
			rolAfiliado.setFechaIngreso(getFechaIngreso());
		}
		if (getFechaRetiro() != null) {
			rolAfiliado.setFechaRetiro(getFechaRetiro());
		}
		if (getHorasLaboradasMes() != null) {
			rolAfiliado.setHorasLaboradasMes(getHorasLaboradasMes());
		}
		if (getIdEmpleador() != null) {
			Empleador empleador = new Empleador();
			empleador.setIdEmpleador(getIdEmpleador());
			rolAfiliado.setEmpleador(empleador);
		}
		if (getIdentificadorAnteEntidadPagadora() != null) {
			rolAfiliado.setIdentificadorAnteEntidadPagadora(getIdentificadorAnteEntidadPagadora());
		}
		if (getIdPagadorAportes() != null) {
			EntidadPagadora pagadorAportes = new EntidadPagadora();
			pagadorAportes.setIdEntidadPagadora(getIdPagadorAportes());
			rolAfiliado.setPagadorAportes(pagadorAportes);
		}
		if (getIdPagadorPension() != null) {
			AFP pagadorPension = new AFP();
			pagadorPension.setIdAFP(getIdPagadorPension());
			rolAfiliado.setPagadorPension(pagadorPension);
		}
		if (getIdSucursalEmpresa() != null) {
			SucursalEmpresa sucursalEmpleador = new SucursalEmpresa();
			sucursalEmpleador.setIdSucursalEmpresa(getIdSucursalEmpresa());
			rolAfiliado.setSucursalEmpleador(sucursalEmpleador);
		}
		if (getMotivoDesafiliacion() != null) {
			rolAfiliado.setMotivoDesafiliacion(getMotivoDesafiliacion());
		}
		if (getPorcentajePagoAportes() != null) {
			rolAfiliado.setPorcentajePagoAportes(getPorcentajePagoAportes());
		}
		if (getTipoAfiliado() != null) {
			rolAfiliado.setTipoAfiliado(getTipoAfiliado());
		}
		if (getTipoContrato() != null) {
			rolAfiliado.setTipoContrato(getTipoContrato());
		}
		if (getTipoSalario() != null) {
			rolAfiliado.setTipoSalario(getTipoSalario());
		}
		if (getValorSalarioMesadaIngresos() != null) {
			rolAfiliado.setValorSalarioMesadaIngresos(getValorSalarioMesadaIngresos());
		}
        if (getDiaHabilVencimientoAporte() != null) {
            rolAfiliado.setDiaHabilVencimientoAporte(getDiaHabilVencimientoAporte());
        }
        if (getCanalReingreso() != null){
            rolAfiliado.setCanalReingreso(getCanalReingreso());
        }
        if (getReferenciaAporteReingreso() != null){
            rolAfiliado.setReferenciaAporteReingreso(getReferenciaAporteReingreso());
        }
        if (getReferenciaSolicitudReingreso() != null){
            rolAfiliado.setReferenciaSolicitudReingreso(getReferenciaSolicitudReingreso());
        }
	}
	
	/**
	 * @return the idRolAfiliado
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * @param idRolAfiliado the idRolAfiliado to set
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * @return the afiliadoDTO
	 */
	public AfiliadoInDTO getAfiliadoDTO() {
		return afiliadoDTO;
	}

	/**
	 * @param afiliadoDTO the afiliadoDTO to set
	 */
	public void setAfiliadoDTO(AfiliadoInDTO afiliadoDTO) {
		this.afiliadoDTO = afiliadoDTO;
	}

	/**
	 * @return the tipoAfiliado
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * @param tipoAfiliado the tipoAfiliado to set
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * @return the estadoAfiliado
	 */
	public EstadoAfiliadoEnum getEstadoAfiliado() {
		return estadoAfiliado;
	}

	/**
	 * @param estadoAfiliado the estadoAfiliado to set
	 */
	public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
		this.estadoAfiliado = estadoAfiliado;
	}

	/**
	 * @return the estadoEnEntidadPagadora
	 */
	public EstadoActivoInactivoEnum getEstadoEnEntidadPagadora() {
		return estadoEnEntidadPagadora;
	}

	/**
	 * @param estadoEnEntidadPagadora the estadoEnEntidadPagadora to set
	 */
	public void setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum estadoEnEntidadPagadora) {
		this.estadoEnEntidadPagadora = estadoEnEntidadPagadora;
	}

	/**
	 * @return the claseIndependiente
	 */
	public ClaseIndependienteEnum getClaseIndependiente() {
		return claseIndependiente;
	}

	/**
	 * @param claseIndependiente the claseIndependiente to set
	 */
	public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
		this.claseIndependiente = claseIndependiente;
	}
	
	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	

	/**
	 * @return the idSucursalEmpresa
	 */
	public Long getIdSucursalEmpresa() {
		return idSucursalEmpresa;
	}

	/**
	 * @param idSucursalEmpresa the idSucursalEmpresa to set
	 */
	public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
		this.idSucursalEmpresa = idSucursalEmpresa;
	}

	/**
	 * @return the idPagadorPension
	 */
	public Short getIdPagadorPension() {
		return idPagadorPension;
	}

	/**
	 * @param idPagadorPension the idPagadorPension to set
	 */
	public void setIdPagadorPension(Short idPagadorPension) {
		this.idPagadorPension = idPagadorPension;
	}

	/**
	 * @return the idPagadorAportes
	 */
	public Long getIdPagadorAportes() {
		return idPagadorAportes;
	}

	/**
	 * @param idPagadorAportes the idPagadorAportes to set
	 */
	public void setIdPagadorAportes(Long idPagadorAportes) {
		this.idPagadorAportes = idPagadorAportes;
	}

	/**
	 * @return the identificadorAnteEntidadPagadora
	 */
	public String getIdentificadorAnteEntidadPagadora() {
		return identificadorAnteEntidadPagadora;
	}

	/**
	 * @param identificadorAnteEntidadPagadora the identificadorAnteEntidadPagadora to set
	 */
	public void setIdentificadorAnteEntidadPagadora(String identificadorAnteEntidadPagadora) {
		this.identificadorAnteEntidadPagadora = identificadorAnteEntidadPagadora;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
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
	 * @return the valorSalarioMesadaIngresos
	 */
	public BigDecimal getValorSalarioMesadaIngresos() {
		return valorSalarioMesadaIngresos;
	}

	/**
	 * @param valorSalarioMesadaIngresos the valorSalarioMesadaIngresos to set
	 */
	public void setValorSalarioMesadaIngresos(BigDecimal valorSalarioMesadaIngresos) {
		this.valorSalarioMesadaIngresos = valorSalarioMesadaIngresos;
	}

	/**
	 * @return the porcentajePagoAportes
	 */
	public BigDecimal getPorcentajePagoAportes() {
		return porcentajePagoAportes;
	}

	/**
	 * @param porcentajePagoAportes the porcentajePagoAportes to set
	 */
	public void setPorcentajePagoAportes(BigDecimal porcentajePagoAportes) {
		this.porcentajePagoAportes = porcentajePagoAportes;
	}

	/**
	 * @return the claseTrabajador
	 */
	public ClaseTrabajadorEnum getClaseTrabajador() {
		return claseTrabajador;
	}

	/**
	 * @param claseTrabajador the claseTrabajador to set
	 */
	public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
		this.claseTrabajador = claseTrabajador;
	}

	/**
	 * @return the tipoSalario
	 */
	public TipoSalarioEnum getTipoSalario() {
		return tipoSalario;
	}

	/**
	 * @param tipoSalario the tipoSalario to set
	 */
	public void setTipoSalario(TipoSalarioEnum tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	/**
	 * @return the tipoContrato
	 */
	public TipoContratoEnum getTipoContrato() {
		return tipoContrato;
	}

	/**
	 * @param tipoContrato the tipoContrato to set
	 */
	public void setTipoContrato(TipoContratoEnum tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the horasLaboradasMes
	 */
	public Short getHorasLaboradasMes() {
		return horasLaboradasMes;
	}

	/**
	 * @param horasLaboradasMes the horasLaboradasMes to set
	 */
	public void setHorasLaboradasMes(Short horasLaboradasMes) {
		this.horasLaboradasMes = horasLaboradasMes;
	}

	/**
	 * @return the fechaAfiliacion
	 */
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * @param fechaAfiliacion the fechaAfiliacion to set
	 */
	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * @return the motivoDesafiliacion
	 */
	public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * @param motivoDesafiliacion the motivoDesafiliacion to set
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}

	/**
	 * @return the sustitucionPatronal
	 */
	public Boolean getSustitucionPatronal() {
		return sustitucionPatronal;
	}

	/**
	 * @param sustitucionPatronal the sustitucionPatronal to set
	 */
	public void setSustitucionPatronal(Boolean sustitucionPatronal) {
		this.sustitucionPatronal = sustitucionPatronal;
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
     * @return the referenciaSolicitudReingreso
     */
    public Long getReferenciaSolicitudReingreso() {
        return referenciaSolicitudReingreso;
    }

    /**
     * @param referenciaSolicitudReingreso the referenciaSolicitudReingreso to set
     */
    public void setReferenciaSolicitudReingreso(Long referenciaSolicitudReingreso) {
        this.referenciaSolicitudReingreso = referenciaSolicitudReingreso;
    }

	public EmpleadorDTO getEmpleador() {
		return empleador;
	}

	public void setEmpleador(EmpleadorDTO empleador) {
		this.empleador = empleador;
	}
	 
    
	
}
