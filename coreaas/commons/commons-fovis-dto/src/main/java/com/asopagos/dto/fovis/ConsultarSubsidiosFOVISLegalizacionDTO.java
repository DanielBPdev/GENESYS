package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion para consultar las
 * solicitudes FOVIS<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-053<br/>
 *
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
public class ConsultarSubsidiosFOVISLegalizacionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2613344062191518713L;

	/**
	 * Identificador del ciclo de asignacion
	 */
	private Long idCicloAsignacion;

	/**
	 * Estado de hogar
	 */
	private EstadoHogarEnum estadoHogar;

	/**
	 * Fecha inicio
	 */
	private Long fechaInicio;

	/**
	 * Fecha inicio
	 */
	private Long fechaFin;

	/**
	 * Estado de la solicitud legalizacion.
	 */
	private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;

	/**
	 * Identificador de la solicitud de legalizacion
	 */
	private String numeroSolicitudLegalizacion;

	/**
	 * Identificador de la solicitud de postulacion
	 */
	private String numeroSolicitudPostulacion;

	/**
	 * Tipo identificacion jefe de hogar
	 */
	private TipoIdentificacionEnum tipoIdentificacionJefe;

	/**
	 * Numero identificacion jefe de hogar
	 */
	private String numeroIdentificacionJefe;

	/**
	 * Nombre del jefe de hogar
	 */
	private String nombreJefeHogar;

	/**
	 * Apellido del jefe de hogar
	 */
	private String apellidoJefeHogar;

	/**
	 * Tipo identificacion oferente
	 */
	private TipoIdentificacionEnum tipoIdentificacionOferente;

	/**
	 * Numero identificacion oferente
	 */
	private String numeroIdentificacionOferente;

	/**
	 * Nombre del oferente
	 */
	private String razonSocialNombreOferente;

	/**
	 * Identificador del proyecto de vivienda
	 */
	private Long proyectoVivienda;

	/**
	 * Identificador del departamento donde se encuentra el proyecto solucion
	 * vivienda
	 */
	private Long departamento;

	/**
	 * Identificador del municio donde se encuentra el proyecto solucion
	 * vivienda
	 */
	private Long municipio;

	/**
	 * Identificador del departamento donde se encuentra el proyecto solucion
	 * vivienda
	 */
	private Long departamentoSolucion;

	/**
	 * Identificador del municio donde se encuentra el proyecto solucion
	 * vivienda
	 */
	private Long municipioSolucion;

	// TODO campos a mostrar
	/**
	 * Nombre del ciclo asociado a la solicitud de legalizacion
	 */
	private String nombreCiclo;
	/**
	 * Nombre Jefe Hogar
	 */
	private String primerNombre;

	/**
	 * Nombre Jefe Hogar
	 */
	private String segundoNombre;

	/**
	 * Apellido Jefe Hogar
	 */
	private String primerApellido;

	/**
	 * Apellido Jefe Hogar
	 */
	private String segundoApellido;

	/**
	 * Nombre completo Jefe Hogar
	 */
	private String nombreCompleto;

	/**
	 * Asociación a la modalidad
	 */
	private ModalidadEnum idModalidad;

	/**
	 * Departamento
	 */
	private String departamentoResidencia;

	/**
	 * Municipio residencia
	 */
	private String municipioResidencia;

	/**
	 * Nombre proyecto de vivienda
	 */
	private String nombreProyecto;

	/**
	 * Departamento proyecto vivienda
	 */
	private String departamentoProyecto;

	/**
	 * Municipio proyecto vivienda
	 */
	private String municipioProyecto;

	/**
	 * Valor asignado de SFV
	 */
	private BigDecimal valorAsignadoSFV;

	/**
	 * @param estadoHogar
	 * @param estadoSolicitud
	 * @param numeroSolicitudLegalizacion
	 * @param numeroSolicitudPostulacion
	 * @param tipoIdentificacionJefe
	 * @param numeroIdentificacionJefe
	 * @param tipoIdentificacionOferente
	 * @param numeroIdentificacionOferente
	 * @param razonSocialNombreOferente
	 * @param nombreCiclo
	 * @param primerNombre
	 * @param segundoNombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param idModalidad
	 * @param departamentoResidencia
	 * @param municipioResidencia
	 * @param nombreProyecto
	 * @param departamentoProyecto
	 * @param municipioProyecto
	 * @param valorAsignadoSFV
	 * @param fechaRadicacion
	 * @param valorADesembolsar
	 */
	public ConsultarSubsidiosFOVISLegalizacionDTO(String nombreCiclo, String numeroSolicitudPostulacion,
			ModalidadEnum idModalidad, TipoIdentificacionEnum tipoIdentificacionJefe, String numeroIdentificacionJefe,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String departamentoResidencia, String municipioResidencia, EstadoHogarEnum estadoHogar,
			TipoIdentificacionEnum tipoIdentificacionOferente, String numeroIdentificacionOferente,
			String razonSocialNombreOferente, String nombreProyecto, String departamentoProyecto,
			String municipioProyecto, BigDecimal valorAsignadoSFV, String numeroSolicitudLegalizacion,
			Date fechaRadicacion, EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud,
			BigDecimal valorADesembolsar) {
		super();
		this.estadoHogar = estadoHogar;
		this.estadoSolicitud = estadoSolicitud;
		this.numeroSolicitudLegalizacion = numeroSolicitudLegalizacion;
		this.numeroSolicitudPostulacion = numeroSolicitudPostulacion;
		this.tipoIdentificacionJefe = tipoIdentificacionJefe;
		this.numeroIdentificacionJefe = numeroIdentificacionJefe;
		this.tipoIdentificacionOferente = tipoIdentificacionOferente;
		this.numeroIdentificacionOferente = numeroIdentificacionOferente;
		this.razonSocialNombreOferente = razonSocialNombreOferente;
		this.nombreCiclo = nombreCiclo;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.idModalidad = idModalidad;
		this.departamentoResidencia = departamentoResidencia;
		this.municipioResidencia = municipioResidencia;
		this.nombreProyecto = nombreProyecto;
		this.departamentoProyecto = departamentoProyecto;
		this.municipioProyecto = municipioProyecto;
		this.valorAsignadoSFV = valorAsignadoSFV;
		this.fechaRadicacion = fechaRadicacion != null ? fechaRadicacion.getTime() : null;
		this.valorADesembolsar = valorADesembolsar;
	}

	/**
	 * Fecha de radicación de la solicitud
	 */
	private Long fechaRadicacion;

	/**
	 * Valor a desembolsar de la solicitud
	 */
	private BigDecimal valorADesembolsar;

	/**
	 * Constructor por defecto
	 */
	public ConsultarSubsidiosFOVISLegalizacionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fechaInicio
	 */
	public Long getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(Long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Long getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Long fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the idCicloAsignacion
	 */
	public Long getIdCicloAsignacion() {
		return idCicloAsignacion;
	}

	/**
	 * @param idCicloAsignacion
	 *            the idCicloAsignacion to set
	 */
	public void setIdCicloAsignacion(Long idCicloAsignacion) {
		this.idCicloAsignacion = idCicloAsignacion;
	}

	/**
	 * @return the estadoHogar
	 */
	public EstadoHogarEnum getEstadoHogar() {
		return estadoHogar;
	}

	/**
	 * @param estadoHogar
	 *            the estadoHogar to set
	 */
	public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
		this.estadoHogar = estadoHogar;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud
	 *            the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * @return the numeroSolicitudLegalizacion
	 */
	public String getNumeroSolicitudLegalizacion() {
		return numeroSolicitudLegalizacion;
	}

	/**
	 * @param numeroSolicitudLegalizacion
	 *            the numeroSolicitudLegalizacion to set
	 */
	public void setNumeroSolicitudLegalizacion(String numeroSolicitudLegalizacion) {
		this.numeroSolicitudLegalizacion = numeroSolicitudLegalizacion;
	}

	/**
	 * @return the numeroSolicitudPostulacion
	 */
	public String getNumeroSolicitudPostulacion() {
		return numeroSolicitudPostulacion;
	}

	/**
	 * @param numeroSolicitudPostulacion
	 *            the numeroSolicitudPostulacion to set
	 */
	public void setNumeroSolicitudPostulacion(String numeroSolicitudPostulacion) {
		this.numeroSolicitudPostulacion = numeroSolicitudPostulacion;
	}

	/**
	 * @return the tipoIdentificacionJefe
	 */
	public TipoIdentificacionEnum getTipoIdentificacionJefe() {
		return tipoIdentificacionJefe;
	}

	/**
	 * @param tipoIdentificacionJefe
	 *            the tipoIdentificacionJefe to set
	 */
	public void setTipoIdentificacionJefe(TipoIdentificacionEnum tipoIdentificacionJefe) {
		this.tipoIdentificacionJefe = tipoIdentificacionJefe;
	}

	/**
	 * @return the numeroIdentificacionJefe
	 */
	public String getNumeroIdentificacionJefe() {
		return numeroIdentificacionJefe;
	}

	/**
	 * @param numeroIdentificacionJefe
	 *            the numeroIdentificacionJefe to set
	 */
	public void setNumeroIdentificacionJefe(String numeroIdentificacionJefe) {
		this.numeroIdentificacionJefe = numeroIdentificacionJefe;
	}

	/**
	 * @return the nombreJefeHogar
	 */
	public String getNombreJefeHogar() {
		return nombreJefeHogar;
	}

	/**
	 * @param nombreJefeHogar
	 *            the nombreJefeHogar to set
	 */
	public void setNombreJefeHogar(String nombreJefeHogar) {
		this.nombreJefeHogar = nombreJefeHogar;
	}

	/**
	 * @return the apellidoJefeHogar
	 */
	public String getApellidoJefeHogar() {
		return apellidoJefeHogar;
	}

	/**
	 * @param apellidoJefeHogar
	 *            the apellidoJefeHogar to set
	 */
	public void setApellidoJefeHogar(String apellidoJefeHogar) {
		this.apellidoJefeHogar = apellidoJefeHogar;
	}

	/**
	 * @return the tipoIdentificacionOferente
	 */
	public TipoIdentificacionEnum getTipoIdentificacionOferente() {
		return tipoIdentificacionOferente;
	}

	/**
	 * @param tipoIdentificacionOferente
	 *            the tipoIdentificacionOferente to set
	 */
	public void setTipoIdentificacionOferente(TipoIdentificacionEnum tipoIdentificacionOferente) {
		this.tipoIdentificacionOferente = tipoIdentificacionOferente;
	}

	/**
	 * @return the numeroIdentificacionOferente
	 */
	public String getNumeroIdentificacionOferente() {
		return numeroIdentificacionOferente;
	}

	/**
	 * @param numeroIdentificacionOferente
	 *            the numeroIdentificacionOferente to set
	 */
	public void setNumeroIdentificacionOferente(String numeroIdentificacionOferente) {
		this.numeroIdentificacionOferente = numeroIdentificacionOferente;
	}

	/**
	 * @return the razonSocialNombreOferente
	 */
	public String getRazonSocialNombreOferente() {
		return razonSocialNombreOferente;
	}

	/**
	 * @param razonSocialNombreOferente
	 *            the razonSocialNombreOferente to set
	 */
	public void setRazonSocialNombreOferente(String razonSocialNombreOferente) {
		this.razonSocialNombreOferente = razonSocialNombreOferente;
	}

	/**
	 * @return the proyectoVivienda
	 */
	public Long getProyectoVivienda() {
		return proyectoVivienda;
	}

	/**
	 * @param proyectoVivienda
	 *            the proyectoVivienda to set
	 */
	public void setProyectoVivienda(Long proyectoVivienda) {
		this.proyectoVivienda = proyectoVivienda;
	}

	/**
	 * @return the municipioSolucion
	 */
	public Long getMunicipioSolucion() {
		return municipioSolucion;
	}

	/**
	 * @param municipioSolucion
	 *            the municipioSolucion to set
	 */
	public void setMunicipioSolucion(Long municipioSolucion) {
		this.municipioSolucion = municipioSolucion;
	}

	/**
	 * @return the departamento
	 */
	public Long getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the municipio
	 */
	public Long getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 *            the municipio to set
	 */
	public void setMunicipio(Long municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the departamentoSolucion
	 */
	public Long getDepartamentoSolucion() {
		return departamentoSolucion;
	}

	/**
	 * @param departamentoSolucion
	 *            the departamentoSolucion to set
	 */
	public void setDepartamentoSolucion(Long departamentoSolucion) {
		this.departamentoSolucion = departamentoSolucion;
	}

	/**
	 * @return the nombreCiclo
	 */
	public String getNombreCiclo() {
		return nombreCiclo;
	}

	/**
	 * @param nombreCiclo
	 *            the nombreCiclo to set
	 */
	public void setNombreCiclo(String nombreCiclo) {
		this.nombreCiclo = nombreCiclo;
	}

	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * @param primerNombre
	 *            the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * @param segundoNombre
	 *            the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido
	 *            the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido
	 *            the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto
	 *            the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the idModalidad
	 */
	public ModalidadEnum getIdModalidad() {
		return idModalidad;
	}

	/**
	 * @param idModalidad
	 *            the idModalidad to set
	 */
	public void setIdModalidad(ModalidadEnum idModalidad) {
		this.idModalidad = idModalidad;
	}

	/**
	 * @return the departamentoResidencia
	 */
	public String getDepartamentoResidencia() {
		return departamentoResidencia;
	}

	/**
	 * @param departamentoResidencia
	 *            the departamentoResidencia to set
	 */
	public void setDepartamentoResidencia(String departamentoResidencia) {
		this.departamentoResidencia = departamentoResidencia;
	}

	/**
	 * @return the municipioResidencia
	 */
	public String getMunicipioResidencia() {
		return municipioResidencia;
	}

	/**
	 * @param municipioResidencia
	 *            the municipioResidencia to set
	 */
	public void setMunicipioResidencia(String municipioResidencia) {
		this.municipioResidencia = municipioResidencia;
	}

	/**
	 * @return the nombreProyecto
	 */
	public String getNombreProyecto() {
		return nombreProyecto;
	}

	/**
	 * @param nombreProyecto
	 *            the nombreProyecto to set
	 */
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	/**
	 * @return the departamentoProyecto
	 */
	public String getDepartamentoProyecto() {
		return departamentoProyecto;
	}

	/**
	 * @param departamentoProyecto
	 *            the departamentoProyecto to set
	 */
	public void setDepartamentoProyecto(String departamentoProyecto) {
		this.departamentoProyecto = departamentoProyecto;
	}

	/**
	 * @return the municipioProyecto
	 */
	public String getMunicipioProyecto() {
		return municipioProyecto;
	}

	/**
	 * @param municipioProyecto
	 *            the municipioProyecto to set
	 */
	public void setMunicipioProyecto(String municipioProyecto) {
		this.municipioProyecto = municipioProyecto;
	}

	/**
	 * @return the valorAsignadoSFV
	 */
	public BigDecimal getValorAsignadoSFV() {
		return valorAsignadoSFV;
	}

	/**
	 * @param valorAsignadoSFV
	 *            the valorAsignadoSFV to set
	 */
	public void setValorAsignadoSFV(BigDecimal valorAsignadoSFV) {
		this.valorAsignadoSFV = valorAsignadoSFV;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Long getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion
	 *            the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Long fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the valorADesembolsar
	 */
	public BigDecimal getValorADesembolsar() {
		return valorADesembolsar;
	}

	/**
	 * @param valorADesembolsar
	 *            the valorADesembolsar to set
	 */
	public void setValorADesembolsar(BigDecimal valorADesembolsar) {
		this.valorADesembolsar = valorADesembolsar;
	}

}
