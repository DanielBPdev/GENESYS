package com.asopagos.zenith.dto;

import java.io.Serializable;

public class RegistroDetalleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * identificar el tipo de contenido en el registro que se va a tratar
	 */
	private Short tipoRegistro;

	/**
	 * Tipo de identificación del COTIZANTE
	 */
	private String tipoIdentificacionCotizante;

	/**
	 * Número de identificación del COTIZANTE
	 */
	private String numeroIdentificacionCotizante;

	/**
	 * Tipo de identificación de los miembros a cargo o de población cubierta
	 */
	private String tipoIdentificacionMiembroPoblacionCubierta;

	/**
	 * Número de identificación de los miembros a cargo o de población cubierta
	 */
	private String numeroIdentificacionMiembroPoblacionCubierta;

	/**
	 * Género o identidad sexual, sólo puede tener una letra (M - F)
	 */
	private String codigoGenero;

	/**
	 * Fecha de nacimiento del COTIZANTE y la población cubierta
	 */
	private String fechaNacimiento;

	/**
	 * Campos en los cuales se deben retornar el primer apellido del cotizante y
	 * su respectiva población cubierta o miembros a cargo
	 */
	private String primerApellido;

	/**
	 * Campos en los cuales se deben retornar el segundo apellido del cotizante
	 * y su respectiva población cubierta o miembros a cargo
	 */
	private String segundoApellido;

	/**
	 * Campos en los cuales se deben retornar el primer nombre del cotizante y
	 * su respectiva población cubierta o miembros a cargo
	 */
	private String primerNombre;

	/**
	 * Campos en los cuales se deben retornar el segundo nombre del cotizante y
	 * su respectiva población cubierta o miembros a cargo
	 */
	private String segundoNombre;

	/**
	 * código departamento
	 */
	private String departamentoResidencia;

	/**
	 * código municipio
	 */
	private String municipioResidencia;

	/**
	 * Si el campo tipo de trayectoria es 1 (aporte), este campo debe retornar
	 * el primer día del último periodo aportado.
	 *
	 * Si el campo tipo de trayectoria es 2 (afiliación), este campo debe
	 * retornar la fecha de afiliación del trabajador o del beneficiario según
	 * el caso.
	 */
	private String fechaIngreso;

	/**
	* 
	*/
	private String fechaRetiro;

	/**
	* Código asignado según superintendencia de subsidio familiar en cada 
	* departamento para cada CCF
	*/
	private String codigoCajaCompensacionFamiliar;

	/**
	* Identificación de tipo de cotizante a la CCF, 
	* campo numérico de un digito 1 o 4
	*/
	private Short codigoTipoCotizante;

	/**
	* Campo para identificar los miembros de la población cubierta 
	* en el archivo plano
	*/
	private Short codigoTipoMiembroPoblacionCubierta;

	/**
	* Campo que identifica al beneficiario la condición de 
	* discapacidad o de estudiante
	*/
	private String codigoCondicionBeneficiario;

	/**
	* Campo para identificar las personas a cargo que apliquen 
	* para Cuota Monetaria en el archivo plano.
	*/
	private Short codigoTipoRelacionCotizante;

	/**
	* Campo que identifica el estado de afiliación del cotizante a la CCF
	*/
	private Short estadoAfiliacion;

	/**
	* Dirección de residencia del cotizante
	*/
	private String direccionCotizante;

	/**
	* Código postal de residencia del cotizante
	*/
	private Integer codigoPostalCotizante;

	/**
	* Teléfono de residencia o ubicación fijo del cotizante
	*/
	private String telefonoCotizante;

	/**
	* Número celular del cotizante
	*/
	private String celularCotizante;

	/**
	* Correo electrónico del cotizante
	*/
	private String correoElectronicoCotizante;

	/**
	* Indica el valor salarial del cotizante
	*/
	private Double salarioCotizante;

	/**
	* Fecha de vigencia de escolaridad de las personas a cargo 
	* que requieran de la misma, para el beneficio 
	* de cuota monetaria
	*/
	private String fechaVigenciaCertificadoEscolaridad;

	/**
	* Campo utilizado para identificar si la persona cargo aplica para subsidio 
	* de cuota monetaria agrícola
	*/
	private Short beneficiarioAgricola;

	/**
	* Se debe enviar vacío por defecto
	*/
	private String fechaActualizacionHojaVida;

	/**
	* Nivel educativo que tiene la personas a cargo del cotizante. 
	* Campo alfabético de dos dígitos
	*/
	private String nivelEducativoPersonaCargo;

	/**
	* Este campo diferencia los registros en dos tipos, aporte y afiliación.
	*/
	private Short tipoTrayectoria;

	/**
	* Nombre de la empresa que realiza el aporte del cotizante a la CCF.
	*/
	private String empresaQueRealizaAporte;

	/**
	* NIT de la empresa que realiza el aporte del cotizante a la CCF.
	*/
	private String nitEmpresaQueRealizaAporte;

	
	/**
	 * 
	 */
	public RegistroDetalleDTO() {
		
	}

	/**
	 * @param tipoIdentificacionCotizante
	 * @param numeroIdentificacionCotizante
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
	 * @param fechaIngreso
	 * @param fechaRetiro
	 * @param codigoCajaCompensacionFamiliar
	 * @param codigoTipoCotizante
	 * @param codigoTipoMiembroPoblacionCubierta
	 * @param codigoCondicionBeneficiario
	 * @param codigoTipoRelacionCotizante
	 * @param estadoAfiliacion
	 * @param direccionCotizante
	 * @param codigoPostalCotizante
	 * @param telefonoCotizante
	 * @param celularCotizante
	 * @param correoElectronicoCotizante
	 * @param salarioCotizante
	 * @param fechaVigenciaCertificadoEscolaridad
	 * @param beneficiarioAgricola
	 * @param fechaActualizacionHojaVida
	 * @param nivelEducativoPersonaCargo
	 * @param tipoTrayectoria
	 * @param empresaQueRealizaAporte
	 * @param nitEmpresaQueRealizaAporte
	 */
	public RegistroDetalleDTO(String tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String tipoIdentificacionMiembroPoblacionCubierta, String numeroIdentificacionMiembroPoblacionCubierta,
			String codigoGenero, String fechaNacimiento, String primerApellido, String segundoApellido,
			String primerNombre, String segundoNombre, String departamentoResidencia, String municipioResidencia,
			String fechaIngreso, String fechaRetiro, String codigoCajaCompensacionFamiliar, Short codigoTipoCotizante,
			Short codigoTipoMiembroPoblacionCubierta, String codigoCondicionBeneficiario,
			Short codigoTipoRelacionCotizante, Short estadoAfiliacion, String direccionCotizante,
			Integer codigoPostalCotizante, String telefonoCotizante, String celularCotizante,
			String correoElectronicoCotizante, Double salarioCotizante, String fechaVigenciaCertificadoEscolaridad,
			Short beneficiarioAgricola, String fechaActualizacionHojaVida, String nivelEducativoPersonaCargo,
			Short tipoTrayectoria, String empresaQueRealizaAporte, String nitEmpresaQueRealizaAporte) {
		
		this.tipoRegistro = 2;
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
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
		this.fechaIngreso = fechaIngreso;
		this.fechaRetiro = fechaRetiro;
		this.codigoCajaCompensacionFamiliar = codigoCajaCompensacionFamiliar;
		this.codigoTipoCotizante = codigoTipoCotizante;
		this.codigoTipoMiembroPoblacionCubierta = codigoTipoMiembroPoblacionCubierta;
		this.codigoCondicionBeneficiario = codigoCondicionBeneficiario;
		this.codigoTipoRelacionCotizante = codigoTipoRelacionCotizante;
		this.estadoAfiliacion = estadoAfiliacion;
		this.direccionCotizante = direccionCotizante;
		this.codigoPostalCotizante = codigoPostalCotizante;
		this.telefonoCotizante = telefonoCotizante;
		this.celularCotizante = celularCotizante;
		this.correoElectronicoCotizante = correoElectronicoCotizante;
		this.salarioCotizante = salarioCotizante;
		this.fechaVigenciaCertificadoEscolaridad = fechaVigenciaCertificadoEscolaridad;
		this.beneficiarioAgricola = beneficiarioAgricola;
		this.fechaActualizacionHojaVida = fechaActualizacionHojaVida;
		this.nivelEducativoPersonaCargo = nivelEducativoPersonaCargo;
		this.tipoTrayectoria = tipoTrayectoria;
		this.empresaQueRealizaAporte = empresaQueRealizaAporte;
		this.nitEmpresaQueRealizaAporte = nitEmpresaQueRealizaAporte;
	}



	/**
	 * @param tipoRegistro
	 * @param tipoIdentificacionCotizante
	 * @param numeroIdentificacionCotizante
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
	 * @param fechaIngreso
	 * @param fechaRetiro
	 * @param codigoCajaCompensacionFamiliar
	 * @param codigoTipoCotizante
	 * @param codigoTipoMiembroPoblacionCubierta
	 * @param codigoCondicionBeneficiario
	 * @param codigoTipoRelacionCotizante
	 * @param estadoAfiliacion
	 * @param direccionCotizante
	 * @param codigoPostalCotizante
	 * @param telefonoCotizante
	 * @param celularCotizante
	 * @param correoElectronicoCotizante
	 * @param salarioCotizante
	 * @param fechaVigenciaCertificadoEscolaridad
	 * @param beneficiarioAgricola
	 * @param fechaActualizacionHojaVida
	 * @param nivelEducativoPersonaCargo
	 * @param tipoTrayectoria
	 * @param empresaQueRealizaAporte
	 * @param nitEmpresaQueRealizaAporte
	 */
	public RegistroDetalleDTO(Short tipoRegistro, String tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String tipoIdentificacionMiembroPoblacionCubierta,
			String numeroIdentificacionMiembroPoblacionCubierta, String codigoGenero, String fechaNacimiento,
			String primerApellido, String segundoApellido, String primerNombre, String segundoNombre,
			String departamentoResidencia, String municipioResidencia, String fechaIngreso, String fechaRetiro,
			String codigoCajaCompensacionFamiliar, Short codigoTipoCotizante, Short codigoTipoMiembroPoblacionCubierta,
			String codigoCondicionBeneficiario, Short codigoTipoRelacionCotizante, Short estadoAfiliacion,
			String direccionCotizante, Integer codigoPostalCotizante, String telefonoCotizante, String celularCotizante,
			String correoElectronicoCotizante, Double salarioCotizante, String fechaVigenciaCertificadoEscolaridad,
			Short beneficiarioAgricola, String fechaActualizacionHojaVida, String nivelEducativoPersonaCargo,
			Short tipoTrayectoria, String empresaQueRealizaAporte, String nitEmpresaQueRealizaAporte) {
		this.tipoRegistro = tipoRegistro;
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante != null ? tipoIdentificacionCotizante : "";
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante != null ? numeroIdentificacionCotizante : "";
		this.tipoIdentificacionMiembroPoblacionCubierta = tipoIdentificacionMiembroPoblacionCubierta != null ? tipoIdentificacionMiembroPoblacionCubierta : "";
		this.numeroIdentificacionMiembroPoblacionCubierta = numeroIdentificacionMiembroPoblacionCubierta != null ? numeroIdentificacionMiembroPoblacionCubierta : "";
		this.codigoGenero = codigoGenero != null ? codigoGenero : "";
		this.fechaNacimiento = fechaNacimiento != null ? fechaNacimiento : "";
		this.primerApellido = primerApellido != null ? primerApellido : "";
		this.segundoApellido = segundoApellido != null ? segundoApellido : "";
		this.primerNombre = primerNombre != null ? primerNombre : "";
		this.segundoNombre = segundoNombre != null ? segundoNombre : "";
		this.departamentoResidencia = departamentoResidencia != null ? departamentoResidencia : "";
		this.municipioResidencia = municipioResidencia != null ? municipioResidencia : "";
		this.fechaIngreso = fechaIngreso != null ? fechaIngreso : "";
		this.fechaRetiro = fechaRetiro != null ? fechaRetiro : "";
		this.codigoCajaCompensacionFamiliar = codigoCajaCompensacionFamiliar != null ? codigoCajaCompensacionFamiliar : "";
		this.codigoTipoCotizante = codigoTipoCotizante;
		this.codigoTipoMiembroPoblacionCubierta = codigoTipoMiembroPoblacionCubierta;
		this.codigoCondicionBeneficiario = codigoCondicionBeneficiario != null ? codigoCondicionBeneficiario : "";
		this.codigoTipoRelacionCotizante = codigoTipoRelacionCotizante;
		this.estadoAfiliacion = estadoAfiliacion;
		this.direccionCotizante = direccionCotizante != null ? direccionCotizante : "";
		this.codigoPostalCotizante = codigoPostalCotizante;
		this.telefonoCotizante = telefonoCotizante != null ? telefonoCotizante : "";
		this.celularCotizante = celularCotizante != null ? celularCotizante : "";
		this.correoElectronicoCotizante = correoElectronicoCotizante != null ? correoElectronicoCotizante : "";
		this.salarioCotizante = salarioCotizante;
		this.fechaVigenciaCertificadoEscolaridad = fechaVigenciaCertificadoEscolaridad != null ? fechaVigenciaCertificadoEscolaridad : "";
		this.beneficiarioAgricola = beneficiarioAgricola;
		this.fechaActualizacionHojaVida = fechaActualizacionHojaVida != null ? fechaActualizacionHojaVida : "";
		this.nivelEducativoPersonaCargo = nivelEducativoPersonaCargo != null ? nivelEducativoPersonaCargo : "";
		this.tipoTrayectoria = tipoTrayectoria;
		this.empresaQueRealizaAporte = empresaQueRealizaAporte != null ? empresaQueRealizaAporte : "";
		this.nitEmpresaQueRealizaAporte = nitEmpresaQueRealizaAporte != null ? nitEmpresaQueRealizaAporte : "";
	}


	/**
	 * @return the tipoRegistro
	 */
	public Short getTipoRegistro() {
		return tipoRegistro;
	}


	/**
	 * @param tipoRegistro the tipoRegistro to set
	 */
	public void setTipoRegistro(Short tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}


	/**
	 * @return the tipoIdentificacionCotizante
	 */
	public String getTipoIdentificacionCotizante() {
		return tipoIdentificacionCotizante;
	}


	/**
	 * @param tipoIdentificacionCotizante the tipoIdentificacionCotizante to set
	 */
	public void setTipoIdentificacionCotizante(String tipoIdentificacionCotizante) {
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
	}


	/**
	 * @return the numeroIdentificacionCotizante
	 */
	public String getNumeroIdentificacionCotizante() {
		return numeroIdentificacionCotizante;
	}


	/**
	 * @param numeroIdentificacionCotizante the numeroIdentificacionCotizante to set
	 */
	public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
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
	public String getDepartamentoResidencia() {
		return departamentoResidencia;
	}


	/**
	 * @param departamentoResidencia the departamentoResidencia to set
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
	 * @param municipioResidencia the municipioResidencia to set
	 */
	public void setMunicipioResidencia(String municipioResidencia) {
		this.municipioResidencia = municipioResidencia;
	}


	/**
	 * @return the fechaIngreso
	 */
	public String getFechaIngreso() {
		return fechaIngreso;
	}


	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	/**
	 * @return the fechaRetiro
	 */
	public String getFechaRetiro() {
		return fechaRetiro;
	}


	/**
	 * @param fechaRetiro the fechaRetiro to set
	 */
	public void setFechaRetiro(String fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}


	/**
	 * @return the codigoCajaCompensacionFamiliar
	 */
	public String getCodigoCajaCompensacionFamiliar() {
		return codigoCajaCompensacionFamiliar;
	}


	/**
	 * @param codigoCajaCompensacionFamiliar the codigoCajaCompensacionFamiliar to set
	 */
	public void setCodigoCajaCompensacionFamiliar(String codigoCajaCompensacionFamiliar) {
		this.codigoCajaCompensacionFamiliar = codigoCajaCompensacionFamiliar;
	}


	/**
	 * @return the codigoTipoCotizante
	 */
	public Short getCodigoTipoCotizante() {
		return codigoTipoCotizante;
	}


	/**
	 * @param codigoTipoCotizante the codigoTipoCotizante to set
	 */
	public void setCodigoTipoCotizante(Short codigoTipoCotizante) {
		this.codigoTipoCotizante = codigoTipoCotizante;
	}


	/**
	 * @return the codigoTipoMiembroPoblacionCubierta
	 */
	public Short getCodigoTipoMiembroPoblacionCubierta() {
		return codigoTipoMiembroPoblacionCubierta;
	}


	/**
	 * @param codigoTipoMiembroPoblacionCubierta the codigoTipoMiembroPoblacionCubierta to set
	 */
	public void setCodigoTipoMiembroPoblacionCubierta(Short codigoTipoMiembroPoblacionCubierta) {
		this.codigoTipoMiembroPoblacionCubierta = codigoTipoMiembroPoblacionCubierta;
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
	 * @return the codigoTipoRelacionCotizante
	 */
	public Short getCodigoTipoRelacionCotizante() {
		return codigoTipoRelacionCotizante;
	}


	/**
	 * @param codigoTipoRelacionCotizante the codigoTipoRelacionCotizante to set
	 */
	public void setCodigoTipoRelacionCotizante(Short codigoTipoRelacionCotizante) {
		this.codigoTipoRelacionCotizante = codigoTipoRelacionCotizante;
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
	 * @return the direccionCotizante
	 */
	public String getDireccionCotizante() {
		return direccionCotizante;
	}


	/**
	 * @param direccionCotizante the direccionCotizante to set
	 */
	public void setDireccionCotizante(String direccionCotizante) {
		this.direccionCotizante = direccionCotizante;
	}


	/**
	 * @return the codigoPostalCotizante
	 */
	public Integer getCodigoPostalCotizante() {
		return codigoPostalCotizante;
	}


	/**
	 * @param codigoPostalCotizante the codigoPostalCotizante to set
	 */
	public void setCodigoPostalCotizante(Integer codigoPostalCotizante) {
		this.codigoPostalCotizante = codigoPostalCotizante;
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
	 * @return the salarioCotizante
	 */
	public Double getSalarioCotizante() {
		return salarioCotizante;
	}


	/**
	 * @param salarioCotizante the salarioCotizante to set
	 */
	public void setSalarioCotizante(Double salarioCotizante) {
		this.salarioCotizante = salarioCotizante;
	}


	/**
	 * @return the fechaVigenciaCertificadoEscolaridad
	 */
	public String getFechaVigenciaCertificadoEscolaridad() {
		return fechaVigenciaCertificadoEscolaridad;
	}


	/**
	 * @param fechaVigenciaCertificadoEscolaridad the fechaVigenciaCertificadoEscolaridad to set
	 */
	public void setFechaVigenciaCertificadoEscolaridad(String fechaVigenciaCertificadoEscolaridad) {
		this.fechaVigenciaCertificadoEscolaridad = fechaVigenciaCertificadoEscolaridad;
	}


	/**
	 * @return the beneficiarioAgricola
	 */
	public Short getBeneficiarioAgricola() {
		return beneficiarioAgricola;
	}


	/**
	 * @param beneficiarioAgricola the beneficiarioAgricola to set
	 */
	public void setBeneficiarioAgricola(Short beneficiarioAgricola) {
		this.beneficiarioAgricola = beneficiarioAgricola;
	}


	/**
	 * @return the fechaActualizacionHojaVida
	 */
	public String getFechaActualizacionHojaVida() {
		return fechaActualizacionHojaVida;
	}


	/**
	 * @param fechaActualizacionHojaVida the fechaActualizacionHojaVida to set
	 */
	public void setFechaActualizacionHojaVida(String fechaActualizacionHojaVida) {
		this.fechaActualizacionHojaVida = fechaActualizacionHojaVida;
	}


	/**
	 * @return the nivelEducativoPersonaCargo
	 */
	public String getNivelEducativoPersonaCargo() {
		return nivelEducativoPersonaCargo;
	}


	/**
	 * @param nivelEducativoPersonaCargo the nivelEducativoPersonaCargo to set
	 */
	public void setNivelEducativoPersonaCargo(String nivelEducativoPersonaCargo) {
		this.nivelEducativoPersonaCargo = nivelEducativoPersonaCargo;
	}


	/**
	 * @return the tipoTrayectoria
	 */
	public Short getTipoTrayectoria() {
		return tipoTrayectoria;
	}


	/**
	 * @param tipoTrayectoria the tipoTrayectoria to set
	 */
	public void setTipoTrayectoria(Short tipoTrayectoria) {
		this.tipoTrayectoria = tipoTrayectoria;
	}


	/**
	 * @return the empresaQueRealizaAporte
	 */
	public String getEmpresaQueRealizaAporte() {
		return empresaQueRealizaAporte;
	}


	/**
	 * @param empresaQueRealizaAporte the empresaQueRealizaAporte to set
	 */
	public void setEmpresaQueRealizaAporte(String empresaQueRealizaAporte) {
		this.empresaQueRealizaAporte = empresaQueRealizaAporte;
	}


	/**
	 * @return the nitEmpresaQueRealizaAporte
	 */
	public String getNitEmpresaQueRealizaAporte() {
		return nitEmpresaQueRealizaAporte;
	}


	/**
	 * @param nitEmpresaQueRealizaAporte the nitEmpresaQueRealizaAporte to set
	 */
	public void setNitEmpresaQueRealizaAporte(String nitEmpresaQueRealizaAporte) {
		this.nitEmpresaQueRealizaAporte = nitEmpresaQueRealizaAporte;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return tipoRegistro + "," + tipoIdentificacionCotizante + "," + numeroIdentificacionCotizante + ","
				+ tipoIdentificacionMiembroPoblacionCubierta + "," + numeroIdentificacionMiembroPoblacionCubierta + ","
				+ codigoGenero + "," + fechaNacimiento + "," + primerApellido + "," + segundoApellido + ","
				+ primerNombre + "," + segundoNombre + "," + departamentoResidencia + "," 
				+ municipioResidencia + "," + fechaIngreso + "," + fechaRetiro + "," + codigoCajaCompensacionFamiliar + "," + 
				(codigoTipoCotizante != null ? codigoTipoCotizante : "") + "," + (codigoTipoMiembroPoblacionCubierta != null ? codigoTipoMiembroPoblacionCubierta : "") 
				+ "," + codigoCondicionBeneficiario + "," + (codigoTipoRelacionCotizante != null ? codigoTipoRelacionCotizante : "") 
				+ "," + (estadoAfiliacion != null ? estadoAfiliacion : "") + "," + direccionCotizante + ","
				+ (codigoPostalCotizante != null ? codigoPostalCotizante : "") + "," + telefonoCotizante + "," + celularCotizante + ","
				+ correoElectronicoCotizante + "," + (salarioCotizante != null ? salarioCotizante.longValue() : "") + "," + fechaVigenciaCertificadoEscolaridad + ","
				+ (beneficiarioAgricola != null ? beneficiarioAgricola : "") + "," + fechaActualizacionHojaVida + "," + nivelEducativoPersonaCargo + ","
				+ tipoTrayectoria + "," + empresaQueRealizaAporte + "," + nitEmpresaQueRealizaAporte+"\r\n";
	}
	
	
	
	
	
	
}
