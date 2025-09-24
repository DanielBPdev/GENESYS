package com.asopagos.afiliaciones.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class DatoHistoricoAfiliadoOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* Estado Actual de afiliación de la persona (Activo - Inactivo)
	*/
	private String estadoAfiliacion;

	/**
	* Tipo de afiliado (Dependiente, Independiente, Pensionado)
	*/
	private String tipoAfiliado;

	/**
	* define que tipo a que tipo de independiente pertenece el afiliado ('TAXISTA', 'VOLUNTARIO', 'MIGRANTE_RETORNADO', 'MIGRANTE', 'INDEPENDIENTE_REGULAR')
	*/
	private String claseIndependiente;

	/**
	* define la modalidad de trabdjor a la que pertenece el dependiente ('TRABAJADOR_POR_DIAS', 'REGULAR', 'SERVICIO_DOMESTICO', 'MADRE_COMUNITARIA')
	*/
	private String claseTrabajador;

	/**
	* Fecha en la que la persona ingresó a la empresa
	*/
	private String fechaIngresoEmpresa;

	/**
	* Fecha en la que se realizó la afiliación a la CCF
	*/
	private String fechaAfiliacionCCF;

	/**
	* Registro	Fecha de creación del registro en la solicitud de afiliación
	*/
	private String fechaCreacion;

	/**
	* Valor de la mesada salarial de la persona pagado por una empresa
	*/
	private String salario;

	/**
	* Horas laboradas al mes por afiliado
	*/
	private String horasLaboradasMes;

	/**
	* Numero de identificacion del empleador
	*/
	private String numeroIdentificacionEmpleador;

	/**
	* Dígito de verificación del NIT para empleadores
	*/
	private String digitoVerificacion;

	/**
	* Nombre o Razón social del empleador
	*/
	private String nombreEmpleador;

	/**
	* Sucursal del empleador asociada al afiliado
	*/
	private String sucursalEmpleador;

	/**
	* Nombre de la sucursal del empleador asociada al afiliado
	*/
	private String nombreSucursalEmpleador;

	/**
	* Tipo de transacción según modificación de la persona
	*/
	private String tipoTransaccion;

	
	/**
	 * 
	 */
	public DatoHistoricoAfiliadoOutDTO(){}
	
	/**
	 * @param estadoAfiliacion
	 * @param tipoAfiliado
	 * @param claseIndependiente
	 * @param claseTrabajador
	 * @param fechaIngresoEmpresa
	 * @param fechaAfiliacionCCF
	 * @param fechaCreacion
	 * @param salario
	 * @param horasLaboradasMes
	 * @param numeroIdentificacionEmpleador
	 * @param digitoVerificacion
	 * @param nombreEmpleador
	 * @param sucursalEmpleador
	 * @param nombreSucursalEmpleador
	 * @param tipoTransaccion
	 */
	public DatoHistoricoAfiliadoOutDTO(String estadoAfiliacion, String tipoAfiliado, String claseIndependiente,
			String claseTrabajador, String fechaIngresoEmpresa, String fechaAfiliacionCCF, String fechaCreacion,
			String salario, String horasLaboradasMes, String numeroIdentificacionEmpleador, String digitoVerificacion,
			String nombreEmpleador, String sucursalEmpleador, String nombreSucursalEmpleador, String tipoTransaccion) {
		this.estadoAfiliacion = estadoAfiliacion;
		this.tipoAfiliado = tipoAfiliado;
		this.claseIndependiente = claseIndependiente;
		this.claseTrabajador = claseTrabajador;
		this.fechaIngresoEmpresa = fechaIngresoEmpresa;
		this.fechaAfiliacionCCF = fechaAfiliacionCCF;
		this.fechaCreacion = fechaCreacion;
		this.salario = salario;
		this.horasLaboradasMes = horasLaboradasMes;
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
		this.digitoVerificacion = digitoVerificacion;
		this.nombreEmpleador = nombreEmpleador;
		this.sucursalEmpleador = sucursalEmpleador;
		this.nombreSucursalEmpleador = nombreSucursalEmpleador;
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the estadoAfiliacion
	 */
	public String getEstadoAfiliacion() {
		return estadoAfiliacion;
	}

	/**
	 * @param estadoAfiliacion the estadoAfiliacion to set
	 */
	public void setEstadoAfiliacion(String estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}

	/**
	 * @return the tipoAfiliado
	 */
	public String getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * @param tipoAfiliado the tipoAfiliado to set
	 */
	public void setTipoAfiliado(String tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * @return the claseIndependiente
	 */
	public String getClaseIndependiente() {
		return claseIndependiente;
	}

	/**
	 * @param claseIndependiente the claseIndependiente to set
	 */
	public void setClaseIndependiente(String claseIndependiente) {
		this.claseIndependiente = claseIndependiente;
	}

	/**
	 * @return the claseTrabajador
	 */
	public String getClaseTrabajador() {
		return claseTrabajador;
	}

	/**
	 * @param claseTrabajador the claseTrabajador to set
	 */
	public void setClaseTrabajador(String claseTrabajador) {
		this.claseTrabajador = claseTrabajador;
	}

	/**
	 * @return the fechaIngresoEmpresa
	 */
	public String getFechaIngresoEmpresa() {
		return fechaIngresoEmpresa;
	}

	/**
	 * @param fechaIngresoEmpresa the fechaIngresoEmpresa to set
	 */
	public void setFechaIngresoEmpresa(String fechaIngresoEmpresa) {
		this.fechaIngresoEmpresa = fechaIngresoEmpresa;
	}

	/**
	 * @return the fechaAfiliacionCCF
	 */
	public String getFechaAfiliacionCCF() {
		return fechaAfiliacionCCF;
	}

	/**
	 * @param fechaAfiliacionCCF the fechaAfiliacionCCF to set
	 */
	public void setFechaAfiliacionCCF(String fechaAfiliacionCCF) {
		this.fechaAfiliacionCCF = fechaAfiliacionCCF;
	}

	/**
	 * @return the fechaCreacion
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the salario
	 */
	public String getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(String salario) {
		this.salario = salario;
	}

	/**
	 * @return the horasLaboradasMes
	 */
	public String getHorasLaboradasMes() {
		return horasLaboradasMes;
	}

	/**
	 * @param horasLaboradasMes the horasLaboradasMes to set
	 */
	public void setHorasLaboradasMes(String horasLaboradasMes) {
		this.horasLaboradasMes = horasLaboradasMes;
	}

	/**
	 * @return the numeroIdentificacionEmpleador
	 */
	public String getNumeroIdentificacionEmpleador() {
		return numeroIdentificacionEmpleador;
	}

	/**
	 * @param numeroIdentificacionEmpleador the numeroIdentificacionEmpleador to set
	 */
	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	/**
	 * @return the digitoVerificacion
	 */
	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * @param digitoVerificacion the digitoVerificacion to set
	 */
	public void setDigitoVerificacion(String digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * @return the nombreEmpleador
	 */
	public String getNombreEmpleador() {
		return nombreEmpleador;
	}

	/**
	 * @param nombreEmpleador the nombreEmpleador to set
	 */
	public void setNombreEmpleador(String nombreEmpleador) {
		this.nombreEmpleador = nombreEmpleador;
	}

	/**
	 * @return the sucursalEmpleador
	 */
	public String getSucursalEmpleador() {
		return sucursalEmpleador;
	}

	/**
	 * @param sucursalEmpleador the sucursalEmpleador to set
	 */
	public void setSucursalEmpleador(String sucursalEmpleador) {
		this.sucursalEmpleador = sucursalEmpleador;
	}

	/**
	 * @return the nombreSucursalEmpleador
	 */
	public String getNombreSucursalEmpleador() {
		return nombreSucursalEmpleador;
	}

	/**
	 * @param nombreSucursalEmpleador the nombreSucursalEmpleador to set
	 */
	public void setNombreSucursalEmpleador(String nombreSucursalEmpleador) {
		this.nombreSucursalEmpleador = nombreSucursalEmpleador;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
}
