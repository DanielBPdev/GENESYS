package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;

/**
 * <b>Descripción:</b> DTO que transporta la informacion laboral del trabajador
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacionLaboralTrabajadorDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5425344245755792993L;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private TipoIdentificacionEnum tipoIdentificacion;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private String numeroIdentificacion;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idEmpleador;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idSucursalEmpleador;

	private ClaseTrabajadorEnum claseTrabajador;

	private Date fechaInicioContrato;

	private Date fechaFinContrato;

	private TipoSalarioEnum tipoSalario;

	private BigDecimal valorSalario;

	private Short horasLaboradasMes;

	private String cargo;

	private TipoContratoEnum tipoContrato;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private TipoAfiliadoEnum tipoAfiliado;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private EstadoAfiliadoEnum estadoAfiliado;
	
	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idRolAfiliado;
	
	private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;

	private Date fechaInicioCondicionVet;

	private Date fechaFinCondicionVet;

	private Integer departamentoDesempenioLabores;
	
	/**
	 * Identificador del municipio asociado a donde el trabjador realiza el desempeño de sus labores.
	 */
	private Short municipioDesempenioLabores;

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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
	 * @return the idSucursalEmpleador
	 */
	public Long getIdSucursalEmpleador() {
		return idSucursalEmpleador;
	}

	/**
	 * @param idSucursalEmpleador the idSucursalEmpleador to set
	 */
	public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
		this.idSucursalEmpleador = idSucursalEmpleador;
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
	 * @return the fechaInicioContrato
	 */
	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	/**
	 * @param fechaInicioContrato the fechaInicioContrato to set
	 */
	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	/**
	 * @return the fechaFinContrato
	 */
	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	/**
	 * @param fechaFinContrato the fechaFinContrato to set
	 */
	public void setFechaFinContrato(Date fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
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
	 * @return the valorSalario
	 */
	public BigDecimal getValorSalario() {
		return valorSalario;
	}

	/**
	 * @param valorSalario the valorSalario to set
	 */
	public void setValorSalario(BigDecimal valorSalario) {
		this.valorSalario = valorSalario;
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
     * @return the municipioDesempenioLabores
     */
    public Short getMunicipioDesempenioLabores() {
        return municipioDesempenioLabores;
    }

    /**
     * @param municipioDesempenioLabores the municipioDesempenioLabores to set
     */
    public void setMunicipioDesempenioLabores(Short municipioDesempenioLabores) {
        this.municipioDesempenioLabores = municipioDesempenioLabores;
    }

	public Date getFechaInicioCondicionVet() {
		return this.fechaInicioCondicionVet;
	}

	public void setFechaInicioCondicionVet(Date fechaInicioCondicionVet) {
		this.fechaInicioCondicionVet = fechaInicioCondicionVet;
	}

	public Date getFechaFinCondicionVet() {
		return this.fechaFinCondicionVet;
	}

	public void setFechaFinCondicionVet(Date fechaFinCondicionVet) {
		this.fechaFinCondicionVet = fechaFinCondicionVet;
	}


	public Integer getDepartamentoDesempenioLabores() {
		return this.departamentoDesempenioLabores;
	}

	public void setDepartamentoDesempenioLabores(Integer departamentoDesempenioLabores) {
		this.departamentoDesempenioLabores = departamentoDesempenioLabores;
	}
}
