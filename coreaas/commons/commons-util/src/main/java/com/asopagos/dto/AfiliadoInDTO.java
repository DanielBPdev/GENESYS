package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.asopagos.dto.webservices.AfiliaPensionadoDTO;
import com.asopagos.dto.webservices.AfiliarTrabajadorIndDTO;

/**
 * <b>Descripción:</b> DTO que transporta los de ingreso de un Afiliado
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class AfiliadoInDTO implements Serializable {

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private PersonaDTO persona;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private TipoAfiliadoEnum tipoAfiliado;

	private CanalRecepcionEnum canalRecepcion;

	private Long idEmpleador;

	private BigDecimal valorSalarioMesada;

	private Long idAfiliado;

	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
	private Long idRolAfiliado;

	private String nombreUsuarioCaja;

	private Long sucursalEmpleadorId;

	private ClasificacionEnum tipoEmpleador;

	private ClaseTrabajadorEnum claseTrabajador;

	private Date fechaInicioContrato;

	private Date fechaAfiliacion;

	private Long idSolicitudGlobal;
	
	private Long idInstanciaProceso;

	private String dominio;

	private Long codigoCargueMultiple;

	private String numeroRadicado;
	
	private List<Long> idBeneficiario;

	private Short idPagadorPension; 

	private Short idMunicipioDesempenioLabores;

	private AfiliarTrabajadorIndDTO trabajadorIndependienteWS;

	private AfiliaPensionadoDTO pensionadoWS;

	@JsonProperty("idPagadorPension")
	public Short getIdPagadorPension() {
		return this.idPagadorPension;
	}

	public void setIdPagadorPension(Short idPagadorPension) {
		this.idPagadorPension = idPagadorPension;
	}

	/**
	 * @return the numeroRadicado
	 */
	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	/**
	 * @param numeroRadicado
	 *            the numeroRadicado to set
	 */
	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	public static AfiliadoInDTO convertAfiliadoToDTO(Afiliado afiliado, PersonaDetalle personaDetalle) {
		AfiliadoInDTO dto = new AfiliadoInDTO();
		dto.setIdAfiliado(afiliado.getIdAfiliado());
		dto.setPersona(PersonaDTO.convertPersonaToDTO(afiliado.getPersona(), personaDetalle));
		return dto;
	}
	
	public static AfiliadoInDTO convertAfiliadoToDTO(
			PersonaDetalle personaDetalle,
			Afiliado afiliado,
			Ubicacion ubicacion,
			Municipio municipio,
			Persona persona) {
		AfiliadoInDTO dto = new AfiliadoInDTO();
		dto.setIdAfiliado(afiliado.getIdAfiliado());
		dto.setPersona(PersonaDTO.convertPersonaToDTO(persona, personaDetalle,ubicacion,municipio));
		return dto;
	}
	
	/**
	 * Método encargado de convertir un afiliado dto en una entidad Afiliado.
	 * @param afiliadoInDTO DTO del afiliado.
	 * @return entidad afiliado.
	 */
	public static Afiliado convertToAfiliado(AfiliadoInDTO afiliadoInDTO) {
		Afiliado afiliado = new Afiliado();
		afiliado.setIdAfiliado(afiliadoInDTO.getIdAfiliado());
		afiliado.setPersona(afiliadoInDTO.getPersona().obtenerDatosPersona(new Persona()));
		return afiliado;
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

	public CanalRecepcionEnum getCanalRecepcion() {
		return canalRecepcion;
	}

	public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
		this.canalRecepcion = canalRecepcion;
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
	 * @return the tipoAfiliado
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * @param tipoAfiliado
	 *            the tipoAfiliado to set
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * @return the valorSalarioMesada
	 */
	public BigDecimal getValorSalarioMesada() {
		return valorSalarioMesada;
	}

	/**
	 * @param valorSalarioMesada
	 *            the valorSalarioMesada to set
	 */
	public void setValorSalarioMesada(BigDecimal valorSalarioMesada) {
		this.valorSalarioMesada = valorSalarioMesada;
	}

	/**
	 * @return the idAfiliado
	 */
	public Long getIdAfiliado() {
		return idAfiliado;
	}

	/**
	 * @param idAfiliado
	 *            the idAfiliado to set
	 */
	public void setIdAfiliado(Long idAfiliado) {
		this.idAfiliado = idAfiliado;
	}

	/**
	 * @return the nombreUsuarioCaja
	 */
	public String getNombreUsuarioCaja() {
		return nombreUsuarioCaja;
	}

	/**
	 * @param nombreUsuarioCaja
	 *            the nombreUsuarioCaja to set
	 */
	public void setNombreUsuarioCaja(String nombreUsuarioCaja) {
		this.nombreUsuarioCaja = nombreUsuarioCaja;
	}

	/**
	 * @return the idRolAfiliado
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * @param idRolAfiliado
	 *            the idRolAfiliado to set
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * @return the sucursalEmpleadorId
	 */
	public Long getSucursalEmpleadorId() {
		return sucursalEmpleadorId;
	}

	/**
	 * @param sucursalEmpleadorId
	 *            the sucursalEmpleadorId to set
	 */
	public void setSucursalEmpleadorId(Long sucursalEmpleadorId) {
		this.sucursalEmpleadorId = sucursalEmpleadorId;
	}

	/**
	 * @return the tipoEmpleador
	 */
	public ClasificacionEnum getTipoEmpleador() {
		return tipoEmpleador;
	}

	/**
	 * @param tipoEmpleador
	 *            the tipoEmpleador to set
	 */
	public void setTipoEmpleador(ClasificacionEnum tipoEmpleador) {
		this.tipoEmpleador = tipoEmpleador;
	}

	/**
	 * @return the fechaAfiliacion
	 */
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * @param fechaAfiliacion
	 *            the fechaAfiliacion to set
	 */
	public void setFechaAfiliacion(Date fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal
	 *            the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

	/**
	 * @return the claseTrabajador
	 */
	public ClaseTrabajadorEnum getClaseTrabajador() {
		return claseTrabajador;
	}

	/**
	 * @param claseTrabajador
	 *            the claseTrabajador to set
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
	 * @param fechaInicioContrato
	 *            the fechaInicioContrato to set
	 */
	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the codigoCargueMultiple
	 */
	public Long getCodigoCargueMultiple() {
		return codigoCargueMultiple;
	}

	/**
	 * @param codigoCargueMultiple
	 *            the codigoCargueMultiple to set
	 */
	public void setCodigoCargueMultiple(Long codigoCargueMultiple) {
		this.codigoCargueMultiple = codigoCargueMultiple;
	}

    /**
     * @return the idBeneficiario
     */
    public List<Long> getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(List<Long> idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

	public Short getIdMunicipioDesempenioLabores() {
		return this.idMunicipioDesempenioLabores;
	}

	public void setIdMunicipioDesempenioLabores(Short idMunicipioDesempenioLabores) {
		this.idMunicipioDesempenioLabores = idMunicipioDesempenioLabores;
	}

	public AfiliarTrabajadorIndDTO getTrabajadorIndependienteWS() {
		return this.trabajadorIndependienteWS;
	}

	public void setTrabajadorIndependienteWS(AfiliarTrabajadorIndDTO trabajadorIndependienteWS) {
		this.trabajadorIndependienteWS = trabajadorIndependienteWS;
	}

	public AfiliaPensionadoDTO getPensionadoWS() {
		return this.pensionadoWS;
	}

	public void setPensionadoWS(AfiliaPensionadoDTO pensionadoWS) {
		this.pensionadoWS = pensionadoWS;
	}

	@Override
	public String toString() {
		return "{" +
			" persona='" + getPersona() + "'" +
			", tipoAfiliado='" + getTipoAfiliado() + "'" +
			", canalRecepcion='" + getCanalRecepcion() + "'" +
			", idEmpleador='" + getIdEmpleador() + "'" +
			", valorSalarioMesada='" + getValorSalarioMesada() + "'" +
			", idAfiliado='" + getIdAfiliado() + "'" +
			", idRolAfiliado='" + getIdRolAfiliado() + "'" +
			", nombreUsuarioCaja='" + getNombreUsuarioCaja() + "'" +
			", sucursalEmpleadorId='" + getSucursalEmpleadorId() + "'" +
			", tipoEmpleador='" + getTipoEmpleador() + "'" +
			", claseTrabajador='" + getClaseTrabajador() + "'" +
			", fechaInicioContrato='" + getFechaInicioContrato() + "'" +
			", fechaAfiliacion='" + getFechaAfiliacion() + "'" +
			", idSolicitudGlobal='" + getIdSolicitudGlobal() + "'" +
			", idInstanciaProceso='" + getIdInstanciaProceso() + "'" +
			", dominio='" + getDominio() + "'" +
			", codigoCargueMultiple='" + getCodigoCargueMultiple() + "'" +
			", numeroRadicado='" + getNumeroRadicado() + "'" +
			", idBeneficiario='" + getIdBeneficiario() + "'" +
			", idPagadorPension='" + getIdPagadorPension() + "'" +
			", idMunicipioDesempenioLabores='" + getIdMunicipioDesempenioLabores() + "'" +
			"}";
	}
}
