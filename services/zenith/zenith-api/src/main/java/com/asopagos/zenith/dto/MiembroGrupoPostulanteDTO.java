package com.asopagos.zenith.dto;

import java.io.Serializable;

public class MiembroGrupoPostulanteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tipoIdentificacionMiembroPoblacionCubierta;
	private String numeroIdentificacionMiembroPoblacionCubierta;
	private String codigoGenero;
	private String fechaNacimiento;
	private String primerApellido;
	private String segundoApellido;
	private String primerNombre;
	private String segundoNombre;
	private Short departamentoResidencia;
	private Short municipioResidencia;
	private String codigoCaja;
	private Short TipoCotizante;
	private Short codigoTipoMiembroPoblacion;
	private String codigoCondicionBeneficiario;
	private Short codigoTipoRelacionAportante;
	private Short estadoAfiliacion;
	private String direccionResidenciaCotizante;
	private String codigoPostalResidenciaCotizante;
	private String telefonoCotizante;
	private String celularCotizante;
	private String correoElectronicoCotizante;
	private String salarioTrabajador;
	private String fechaVencimientoCertificadoEscolaridad;
	private Short beneficiarioRural;
	private String nivelEducativoPersonaACargo;
	private String nombreUltimaEmpresa;
	private String nITUltimaEmpresa;
	private String fechaRetiroUltimaEmpresa;
	private String condicionBeneficiario;
	private String categoriaTrabajador;
	
	
	/**
	 * 
	 */
	public MiembroGrupoPostulanteDTO() {
	}
	
	/**
	 * @param tipoIdentificacionMiembroPoblacionCubierta
	 * @param numeroIdentificacionMiembroPoblacionCubierta
	 * @param codigoGenero
	 * @param fechaNacimiento
	 * @param primerApellido
	 * @param segundoApellido
	 * @param primerNombre
	 * @param segundoNombre
	 * @param departamentoResidencia
	 * @param municipioResidencia
	 * @param codigoCaja
	 * @param tipoCotizante
	 * @param codigoTipoMiembroPoblacion
	 * @param codigoCondicionBeneficiario
	 * @param codigoTipoRelacionAportante
	 * @param estadoAfiliacion
	 * @param direccionResidenciaCotizante
	 * @param codigoPostalResidenciaCotizante
	 * @param telefonoCotizante
	 * @param celularCotizante
	 * @param correoElectronicoCotizante
	 * @param salarioTrabajador
	 * @param fechaVencimientoCertificadoEscolaridad
	 * @param beneficiarioRural
	 * @param nivelEducativoPersonaACargo
	 * @param nombreUltimaEmpresa
	 * @param nITUltimaEmpresa
	 * @param fechaRetiroUltimaEmpresa
	 * @param condicionBeneficiario
	 */
	public MiembroGrupoPostulanteDTO(String tipoIdentificacionMiembroPoblacionCubierta,
			String numeroIdentificacionMiembroPoblacionCubierta, String codigoGenero, String fechaNacimiento,
			String primerApellido, String segundoApellido, String primerNombre, String segundoNombre,
			Short departamentoResidencia, Short municipioResidencia, String codigoCaja, Short tipoCotizante,
			Short codigoTipoMiembroPoblacion, String codigoCondicionBeneficiario, Short codigoTipoRelacionAportante,
			Short estadoAfiliacion, String direccionResidenciaCotizante, String codigoPostalResidenciaCotizante,
			String telefonoCotizante, String celularCotizante, String correoElectronicoCotizante,
			String salarioTrabajador, String fechaVencimientoCertificadoEscolaridad, Short beneficiarioRural,
			String nivelEducativoPersonaACargo, String nombreUltimaEmpresa, String nITUltimaEmpresa,
			String fechaRetiroUltimaEmpresa, String condicionBeneficiario) {
		
		this.tipoIdentificacionMiembroPoblacionCubierta = tipoIdentificacionMiembroPoblacionCubierta;
		this.numeroIdentificacionMiembroPoblacionCubierta = numeroIdentificacionMiembroPoblacionCubierta;
		this.codigoGenero = codigoGenero;
		this.fechaNacimiento = fechaNacimiento;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.departamentoResidencia = departamentoResidencia;
		this.municipioResidencia = municipioResidencia;
		this.codigoCaja = codigoCaja;
		TipoCotizante = tipoCotizante;
		this.codigoTipoMiembroPoblacion = codigoTipoMiembroPoblacion;
		this.codigoCondicionBeneficiario = codigoCondicionBeneficiario;
		this.codigoTipoRelacionAportante = codigoTipoRelacionAportante;
		this.estadoAfiliacion = estadoAfiliacion;
		this.direccionResidenciaCotizante = direccionResidenciaCotizante;
		this.codigoPostalResidenciaCotizante = codigoPostalResidenciaCotizante;
		this.telefonoCotizante = telefonoCotizante;
		this.celularCotizante = celularCotizante;
		this.correoElectronicoCotizante = correoElectronicoCotizante;
		this.salarioTrabajador = salarioTrabajador;
		this.fechaVencimientoCertificadoEscolaridad = fechaVencimientoCertificadoEscolaridad;
		this.beneficiarioRural = beneficiarioRural;
		this.nivelEducativoPersonaACargo = nivelEducativoPersonaACargo;
		this.nombreUltimaEmpresa = nombreUltimaEmpresa;
		this.nITUltimaEmpresa = nITUltimaEmpresa;
		this.fechaRetiroUltimaEmpresa = fechaRetiroUltimaEmpresa;
		this.condicionBeneficiario = condicionBeneficiario;
	}

	/**
	 * @param tipoIdentificacionMiembroPoblacionCubierta
	 * @param numeroIdentificacionMiembroPoblacionCubierta
	 * @param codigoGenero
	 * @param fechaNacimiento
	 * @param primerApellido
	 * @param segundoApellido
	 * @param primerNombre
	 * @param segundoNombre
	 * @param departamentoResidencia
	 * @param municipioResidencia
	 * @param codigoCaja
	 * @param tipoCotizante
	 * @param codigoTipoMiembroPoblacion
	 * @param codigoCondicionBeneficiario
	 * @param codigoTipoRelacionAportante
	 * @param estadoAfiliacion
	 * @param direccionResidenciaCotizante
	 * @param codigoPostalResidenciaCotizante
	 * @param telefonoCotizante
	 * @param celularCotizante
	 * @param correoElectronicoCotizante
	 * @param salarioTrabajador
	 * @param fechaVencimientoCertificadoEscolaridad
	 * @param beneficiarioRural
	 * @param nivelEducativoPersonaACargo
	 * @param nombreUltimaEmpresa
	 * @param nITUltimaEmpresa
	 * @param fechaRetiroUltimaEmpresa
	 * @param condicionBeneficiario
	 */
	public MiembroGrupoPostulanteDTO(String tipoIdentificacionMiembroPoblacionCubierta,
			String numeroIdentificacionMiembroPoblacionCubierta, String codigoGenero, String fechaNacimiento,
			String primerApellido, String segundoApellido, String primerNombre, String segundoNombre,
			Short departamentoResidencia, Short municipioResidencia, String codigoCaja, Short tipoCotizante,
			Short codigoTipoMiembroPoblacion, String codigoCondicionBeneficiario, Short codigoTipoRelacionAportante,
			Short estadoAfiliacion, String direccionResidenciaCotizante, String codigoPostalResidenciaCotizante,
			String telefonoCotizante, String celularCotizante, String correoElectronicoCotizante,
			String salarioTrabajador, String fechaVencimientoCertificadoEscolaridad, Short beneficiarioRural,
			String nivelEducativoPersonaACargo, String nombreUltimaEmpresa, String nITUltimaEmpresa,
			String fechaRetiroUltimaEmpresa, String condicionBeneficiario, String categoriaTrabajador) {
		
		this.tipoIdentificacionMiembroPoblacionCubierta = tipoIdentificacionMiembroPoblacionCubierta;
		this.numeroIdentificacionMiembroPoblacionCubierta = numeroIdentificacionMiembroPoblacionCubierta;
		this.codigoGenero = codigoGenero;
		this.fechaNacimiento = fechaNacimiento;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.departamentoResidencia = departamentoResidencia;
		this.municipioResidencia = municipioResidencia;
		this.codigoCaja = codigoCaja;
		TipoCotizante = tipoCotizante;
		this.codigoTipoMiembroPoblacion = codigoTipoMiembroPoblacion;
		this.codigoCondicionBeneficiario = codigoCondicionBeneficiario;
		this.codigoTipoRelacionAportante = codigoTipoRelacionAportante;
		this.estadoAfiliacion = estadoAfiliacion;
		this.direccionResidenciaCotizante = direccionResidenciaCotizante;
		this.codigoPostalResidenciaCotizante = codigoPostalResidenciaCotizante;
		this.telefonoCotizante = telefonoCotizante;
		this.celularCotizante = celularCotizante;
		this.correoElectronicoCotizante = correoElectronicoCotizante;
		this.salarioTrabajador = salarioTrabajador;
		this.fechaVencimientoCertificadoEscolaridad = fechaVencimientoCertificadoEscolaridad;
		this.beneficiarioRural = beneficiarioRural;
		this.nivelEducativoPersonaACargo = nivelEducativoPersonaACargo;
		this.nombreUltimaEmpresa = nombreUltimaEmpresa;
		this.nITUltimaEmpresa = nITUltimaEmpresa;
		this.fechaRetiroUltimaEmpresa = fechaRetiroUltimaEmpresa;
		this.condicionBeneficiario = condicionBeneficiario;
		this.categoriaTrabajador = categoriaTrabajador;
	}
	
	
	/**
	 * @return the tipoIdentificacionMiembroPoblacionCubierta
	 */
	public String getTipoIdentificacionMiembroPoblacionCubierta() {
		return tipoIdentificacionMiembroPoblacionCubierta;
	}
	/**
	 * @param tipoIdentificacionMiembroPoblacionCubierta the tipoIdentificacionMiembroPoblacionCubierta to set
	 */
	public void setTipoIdentificacionMiembroPoblacionCubierta(String tipoIdentificacionMiembroPoblacionCubierta) {
		this.tipoIdentificacionMiembroPoblacionCubierta = tipoIdentificacionMiembroPoblacionCubierta;
	}
	/**
	 * @return the numeroIdentificacionMiembroPoblacionCubierta
	 */
	public String getNumeroIdentificacionMiembroPoblacionCubierta() {
		return numeroIdentificacionMiembroPoblacionCubierta;
	}
	/**
	 * @param numeroIdentificacionMiembroPoblacionCubierta the numeroIdentificacionMiembroPoblacionCubierta to set
	 */
	public void setNumeroIdentificacionMiembroPoblacionCubierta(String numeroIdentificacionMiembroPoblacionCubierta) {
		this.numeroIdentificacionMiembroPoblacionCubierta = numeroIdentificacionMiembroPoblacionCubierta;
	}
	/**
	 * @return the codigoGenero
	 */
	public String getCodigoGenero() {
		return codigoGenero;
	}
	/**
	 * @param codigoGenero the codigoGenero to set
	 */
	public void setCodigoGenero(String codigoGenero) {
		this.codigoGenero = codigoGenero;
	}
	/**
	 * @return the fechaNacimiento
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}
	/**
	 * @param primerApellido the primerApellido to set
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
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre the primerNombre to set
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
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	/**
	 * @return the departamentoResidencia
	 */
	public Short getDepartamentoResidencia() {
		return departamentoResidencia;
	}
	/**
	 * @param departamentoResidencia the departamentoResidencia to set
	 */
	public void setDepartamentoResidencia(Short departamentoResidencia) {
		this.departamentoResidencia = departamentoResidencia;
	}
	/**
	 * @return the municipioResidencia
	 */
	public Short getMunicipioResidencia() {
		return municipioResidencia;
	}
	/**
	 * @param municipioResidencia the municipioResidencia to set
	 */
	public void setMunicipioResidencia(Short municipioResidencia) {
		this.municipioResidencia = municipioResidencia;
	}
	/**
	 * @return the codigoCaja
	 */
	public String getCodigoCaja() {
		return codigoCaja;
	}
	/**
	 * @param codigoCaja the codigoCaja to set
	 */
	public void setCodigoCaja(String codigoCaja) {
		this.codigoCaja = codigoCaja;
	}
	/**
	 * @return the tipoCotizante
	 */
	public Short getTipoCotizante() {
		return TipoCotizante;
	}
	/**
	 * @param tipoCotizante the tipoCotizante to set
	 */
	public void setTipoCotizante(Short tipoCotizante) {
		TipoCotizante = tipoCotizante;
	}
	/**
	 * @return the codigoTipoMiembroPoblacion
	 */
	public Short getCodigoTipoMiembroPoblacion() {
		return codigoTipoMiembroPoblacion;
	}
	/**
	 * @param codigoTipoMiembroPoblacion the codigoTipoMiembroPoblacion to set
	 */
	public void setCodigoTipoMiembroPoblacion(Short codigoTipoMiembroPoblacion) {
		this.codigoTipoMiembroPoblacion = codigoTipoMiembroPoblacion;
	}
	/**
	 * @return the codigoCondicionBeneficiario
	 */
	public String getCodigoCondicionBeneficiario() {
		return codigoCondicionBeneficiario;
	}
	/**
	 * @param codigoCondicionBeneficiario the codigoCondicionBeneficiario to set
	 */
	public void setCodigoCondicionBeneficiario(String codigoCondicionBeneficiario) {
		this.codigoCondicionBeneficiario = codigoCondicionBeneficiario;
	}
	/**
	 * @return the codigoTipoRelacionAportante
	 */
	public Short getCodigoTipoRelacionAportante() {
		return codigoTipoRelacionAportante;
	}
	/**
	 * @param codigoTipoRelacionAportante the codigoTipoRelacionAportante to set
	 */
	public void setCodigoTipoRelacionAportante(Short codigoTipoRelacionAportante) {
		this.codigoTipoRelacionAportante = codigoTipoRelacionAportante;
	}
	/**
	 * @return the estadoAfiliacion
	 */
	public Short getEstadoAfiliacion() {
		return estadoAfiliacion;
	}
	/**
	 * @param estadoAfiliacion the estadoAfiliacion to set
	 */
	public void setEstadoAfiliacion(Short estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}
	/**
	 * @return the direccionResidenciaCotizante
	 */
	public String getDireccionResidenciaCotizante() {
		return direccionResidenciaCotizante;
	}
	/**
	 * @param direccionResidenciaCotizante the direccionResidenciaCotizante to set
	 */
	public void setDireccionResidenciaCotizante(String direccionResidenciaCotizante) {
		this.direccionResidenciaCotizante = direccionResidenciaCotizante;
	}
	/**
	 * @return the codigoPostalResidenciaCotizante
	 */
	public String getCodigoPostalResidenciaCotizante() {
		return codigoPostalResidenciaCotizante;
	}
	/**
	 * @param codigoPostalResidenciaCotizante the codigoPostalResidenciaCotizante to set
	 */
	public void setCodigoPostalResidenciaCotizante(String codigoPostalResidenciaCotizante) {
		this.codigoPostalResidenciaCotizante = codigoPostalResidenciaCotizante;
	}
	/**
	 * @return the telefonoCotizante
	 */
	public String getTelefonoCotizante() {
		return telefonoCotizante;
	}
	/**
	 * @param telefonoCotizante the telefonoCotizante to set
	 */
	public void setTelefonoCotizante(String telefonoCotizante) {
		this.telefonoCotizante = telefonoCotizante;
	}
	/**
	 * @return the celularCotizante
	 */
	public String getCelularCotizante() {
		return celularCotizante;
	}
	/**
	 * @param celularCotizante the celularCotizante to set
	 */
	public void setCelularCotizante(String celularCotizante) {
		this.celularCotizante = celularCotizante;
	}
	/**
	 * @return the correoElectronicoCotizante
	 */
	public String getCorreoElectronicoCotizante() {
		return correoElectronicoCotizante;
	}
	/**
	 * @param correoElectronicoCotizante the correoElectronicoCotizante to set
	 */
	public void setCorreoElectronicoCotizante(String correoElectronicoCotizante) {
		this.correoElectronicoCotizante = correoElectronicoCotizante;
	}
	/**
	 * @return the salarioTrabajador
	 */
	public String getSalarioTrabajador() {
		return salarioTrabajador;
	}
	/**
	 * @param salarioTrabajador the salarioTrabajador to set
	 */
	public void setSalarioTrabajador(String salarioTrabajador) {
		this.salarioTrabajador = salarioTrabajador;
	}
	/**
	 * @return the fechaVencimientoCertificadoEscolaridad
	 */
	public String getFechaVencimientoCertificadoEscolaridad() {
		return fechaVencimientoCertificadoEscolaridad;
	}
	/**
	 * @param fechaVencimientoCertificadoEscolaridad the fechaVencimientoCertificadoEscolaridad to set
	 */
	public void setFechaVencimientoCertificadoEscolaridad(String fechaVencimientoCertificadoEscolaridad) {
		this.fechaVencimientoCertificadoEscolaridad = fechaVencimientoCertificadoEscolaridad;
	}
	/**
	 * @return the beneficiarioRural
	 */
	public Short getBeneficiarioRural() {
		return beneficiarioRural;
	}
	/**
	 * @param beneficiarioRural the beneficiarioRural to set
	 */
	public void setBeneficiarioRural(Short beneficiarioRural) {
		this.beneficiarioRural = beneficiarioRural;
	}
	/**
	 * @return the nivelEducativoPersonaACargo
	 */
	public String getNivelEducativoPersonaACargo() {
		return nivelEducativoPersonaACargo;
	}
	/**
	 * @param nivelEducativoPersonaACargo the nivelEducativoPersonaACargo to set
	 */
	public void setNivelEducativoPersonaACargo(String nivelEducativoPersonaACargo) {
		this.nivelEducativoPersonaACargo = nivelEducativoPersonaACargo;
	}
	/**
	 * @return the nombreUltimaEmpresa
	 */
	public String getNombreUltimaEmpresa() {
		return nombreUltimaEmpresa;
	}
	/**
	 * @param nombreUltimaEmpresa the nombreUltimaEmpresa to set
	 */
	public void setNombreUltimaEmpresa(String nombreUltimaEmpresa) {
		this.nombreUltimaEmpresa = nombreUltimaEmpresa;
	}
	/**
	 * @return the nITUltimaEmpresa
	 */
	public String getnITUltimaEmpresa() {
		return nITUltimaEmpresa;
	}
	/**
	 * @param nITUltimaEmpresa the nITUltimaEmpresa to set
	 */
	public void setnITUltimaEmpresa(String nITUltimaEmpresa) {
		this.nITUltimaEmpresa = nITUltimaEmpresa;
	}
	/**
	 * @return the fechaRetiroUltimaEmpresa
	 */
	public String getFechaRetiroUltimaEmpresa() {
		return fechaRetiroUltimaEmpresa;
	}
	/**
	 * @param fechaRetiroUltimaEmpresa the fechaRetiroUltimaEmpresa to set
	 */
	public void setFechaRetiroUltimaEmpresa(String fechaRetiroUltimaEmpresa) {
		this.fechaRetiroUltimaEmpresa = fechaRetiroUltimaEmpresa;
	}
	/**
	 * @return the condicionBeneficiario
	 */
	public String getCondicionBeneficiario() {
		return condicionBeneficiario;
	}
	/**
	 * @param condicionBeneficiario the condicionBeneficiario to set
	 */
	public void setCondicionBeneficiario(String condicionBeneficiario) {
		this.condicionBeneficiario = condicionBeneficiario;
	}

	
	/**
	 * @return the categoriaTrabajador
	 */
	public String getCategoriaTrabajador() {
		return categoriaTrabajador;
	}
	/**
	 * @param categoriaTrabajador the nITEmpresaRealizoAporte to set
	 */
	public void setCategoriaTrabajador(String categoriaTrabajador) {
		this.categoriaTrabajador = categoriaTrabajador;
	}

	
}
