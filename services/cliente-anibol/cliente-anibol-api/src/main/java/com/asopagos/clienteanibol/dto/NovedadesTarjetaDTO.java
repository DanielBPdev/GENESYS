package com.asopagos.clienteanibol.dto;

import com.asopagos.clienteanibol.enums.TipoEstadoCivilEnum;
import com.asopagos.clienteanibol.enums.TipoIdentificacionEnum;
import com.asopagos.clienteanibol.enums.TipoNovedadTarjetaEnum;
import com.asopagos.clienteanibol.enums.TipoPersonaEnum;
import com.asopagos.clienteanibol.enums.TipoSexoEnum;

public class NovedadesTarjetaDTO {

	private String idProceso;
	private String idEntidad;
	private TipoNovedadTarjetaEnum tipoNovedad;
	private TipoPersonaEnum tipoPersona;
	private TipoIdentificacionEnum tipoIdentificacion;
	private String numeroIdentificacion;
	private String primerApellido;
	private String segundoApellido;
	private String primerNombre;
	private String segundoNombre;
	private String fechaNacimiento;
	private TipoSexoEnum sexo;
	private TipoEstadoCivilEnum estadoCivil;
	private String direccionResidencial;
	private String codigoDepartamento;
	private String codigoCiudad;
	private String telefonoResidencia;
	private String codigoDepartamentoCorrespondencia;
	private String codigoCiudadCorrespondencia;
	
	public String getIdProceso() {
		return idProceso;
	}
	
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public TipoNovedadTarjetaEnum getTipoNovedad() {
		return tipoNovedad;
	}

	public void setTipoNovedad(TipoNovedadTarjetaEnum tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}

	public TipoPersonaEnum getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersonaEnum tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public TipoSexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}

	public TipoEstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(TipoEstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getDireccionResidencial() {
		return direccionResidencial;
	}

	public void setDireccionResidencial(String direccionResidencial) {
		this.direccionResidencial = direccionResidencial;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public String getCodigoCiudad() {
		return codigoCiudad;
	}

	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}

	public String getTelefonoResidencia() {
		return telefonoResidencia;
	}

	public void setTelefonoResidencia(String telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}

	public String getCodigoDepartamentoCorrespondencia() {
		return codigoDepartamentoCorrespondencia;
	}

	public void setCodigoDepartamentoCorrespondencia(String codigoDepartamentoCorrespondencia) {
		this.codigoDepartamentoCorrespondencia = codigoDepartamentoCorrespondencia;
	}

	public String getCodigoCiudadCorrespondencia() {
		return codigoCiudadCorrespondencia;
	}

	public void setCodigoCiudadCorrespondencia(String codigoCiudadCorrespondencia) {
		this.codigoCiudadCorrespondencia = codigoCiudadCorrespondencia;
	}
	
	
}
