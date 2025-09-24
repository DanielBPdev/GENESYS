/**
 * 
 */
package com.asopagos.dto.cargaMultiple;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos de un trabajador canditato
 * de novedad. (Carga múltiple)
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class TrabajadorCandidatoNovedadDTO implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Persona.
	 */
	private PersonaModeloDTO personaDTO;

	/**
	 * Clase trabajador.
	 */
	private ClaseTrabajadorEnum claseTrabajador;

	/**
	 * Fecha inicio del contrato.
	 */
	private Date fechaInicioContrato;

	/**
	 * Valor del salario mensual.
	 */
	private BigDecimal valorSalarioMensual;

	/**
	 * Tipo de salario.
	 */
	private TipoSalarioEnum tipoSalario;

	/**
	 * Ocupación.
	 */
	private String cargoOficina;

	/**
	 * Tipo de contrato.
	 */
	private TipoContratoEnum tipoContratoEnum;

	/**
	 * Codigo del cargue.
	 */
	private Long codigoCargueMultiple;

	/**
	 * Departamento.
	 */
	private Departamento departamento;

	/**
	 * Muncipio.
	 */
	private Municipio municipio;

	/**
	 * Id del empleador.
	 */
	private Long idEmpleador;

	/**
	 * Id de la sucursal del empleador.
	 */
	private Long idSucursalEmpleador;
	/**
	 * Listado de los tipos de transaccion de una solicitud multiple
	 */
	private List<TipoTransaccionEnum> lstTipoTransaccion;
	/**
	 * Lista de estados de estados de una solicitud multiple
	 */
	private List<EstadoSolicitudNovedadEnum> lstEstadoSolicitudNovedadEnum;
	/**
	 * booleano que se encargara de tener si la validacion es aprobada
	 */
	private boolean validacionNovedad;
	/**
	 * Estado del archivo
	 */
	private EstadoCargueMasivoEnum estadoArhivo;
	/**
	 * Nombre del archivo al que pertenece la novedad
	 */
	private String nombreArchivo;
	/**
	 * Fecha en que se realiza el procesamiento del archivo
	 */
	private Long fechaProcesamiento;
	
	/**
	 * Método que retorna el valor de personaDTO.
	 * @return valor de personaDTO.
	 */
	public PersonaModeloDTO getPersonaDTO() {
		return personaDTO;
	}
	/**
	 * Método encargado de modificar el valor de personaDTO.
	 * @param valor para modificar personaDTO.
	 */
	public void setPersonaDTO(PersonaModeloDTO personaDTO) {
		this.personaDTO = personaDTO;
	}
	/**
	 * Método que retorna el valor de claseTrabajador.
	 * @return valor de claseTrabajador.
	 */
	public ClaseTrabajadorEnum getClaseTrabajador() {
		return claseTrabajador;
	}
	/**
	 * Método encargado de modificar el valor de claseTrabajador.
	 * @param valor para modificar claseTrabajador.
	 */
	public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
		this.claseTrabajador = claseTrabajador;
	}
	/**
	 * Método que retorna el valor de fechaInicioContrato.
	 * @return valor de fechaInicioContrato.
	 */
	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}
	/**
	 * Método encargado de modificar el valor de fechaInicioContrato.
	 * @param valor para modificar fechaInicioContrato.
	 */
	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}
	/**
	 * Método que retorna el valor de valorSalarioMensual.
	 * @return valor de valorSalarioMensual.
	 */
	public BigDecimal getValorSalarioMensual() {
		return valorSalarioMensual;
	}
	/**
	 * Método encargado de modificar el valor de valorSalarioMensual.
	 * @param valor para modificar valorSalarioMensual.
	 */
	public void setValorSalarioMensual(BigDecimal valorSalarioMensual) {
		this.valorSalarioMensual = valorSalarioMensual;
	}
	/**
	 * Método que retorna el valor de tipoSalario.
	 * @return valor de tipoSalario.
	 */
	public TipoSalarioEnum getTipoSalario() {
		return tipoSalario;
	}
	/**
	 * Método encargado de modificar el valor de tipoSalario.
	 * @param valor para modificar tipoSalario.
	 */
	public void setTipoSalario(TipoSalarioEnum tipoSalario) {
		this.tipoSalario = tipoSalario;
	}
	/**
	 * Método que retorna el valor de cargoOficina.
	 * @return valor de cargoOficina.
	 */
	public String getCargoOficina() {
		return cargoOficina;
	}
	/**
	 * Método encargado de modificar el valor de cargoOficina.
	 * @param valor para modificar cargoOficina.
	 */
	public void setCargoOficina(String cargoOficina) {
		this.cargoOficina = cargoOficina;
	}
	/**
	 * Método que retorna el valor de tipoContratoEnum.
	 * @return valor de tipoContratoEnum.
	 */
	public TipoContratoEnum getTipoContratoEnum() {
		return tipoContratoEnum;
	}
	/**
	 * Método encargado de modificar el valor de tipoContratoEnum.
	 * @param valor para modificar tipoContratoEnum.
	 */
	public void setTipoContratoEnum(TipoContratoEnum tipoContratoEnum) {
		this.tipoContratoEnum = tipoContratoEnum;
	}
	/**
	 * Método que retorna el valor de codigoCargueMultiple.
	 * @return valor de codigoCargueMultiple.
	 */
	public Long getCodigoCargueMultiple() {
		return codigoCargueMultiple;
	}
	/**
	 * Método encargado de modificar el valor de codigoCargueMultiple.
	 * @param valor para modificar codigoCargueMultiple.
	 */
	public void setCodigoCargueMultiple(Long codigoCargueMultiple) {
		this.codigoCargueMultiple = codigoCargueMultiple;
	}
	/**
	 * Método que retorna el valor de departamento.
	 * @return valor de departamento.
	 */
	public Departamento getDepartamento() {
		return departamento;
	}
	/**
	 * Método encargado de modificar el valor de departamento.
	 * @param valor para modificar departamento.
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	/**
	 * Método que retorna el valor de municipio.
	 * @return valor de municipio.
	 */
	public Municipio getMunicipio() {
		return municipio;
	}
	/**
	 * Método encargado de modificar el valor de municipio.
	 * @param valor para modificar municipio.
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
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
	 * Método que retorna el valor de idSucursalEmpleador.
	 * @return valor de idSucursalEmpleador.
	 */
	public Long getIdSucursalEmpleador() {
		return idSucursalEmpleador;
	}
	/**
	 * Método encargado de modificar el valor de idSucursalEmpleador.
	 * @param valor para modificar idSucursalEmpleador.
	 */
	public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
		this.idSucursalEmpleador = idSucursalEmpleador;
	}
	/**
	 * Método que retorna el valor de lstTipoTransaccion.
	 * @return valor de lstTipoTransaccion.
	 */
	public List<TipoTransaccionEnum> getLstTipoTransaccion() {
		return lstTipoTransaccion;
	}
	/**
	 * Método encargado de modificar el valor de lstTipoTransaccion.
	 * @param valor para modificar lstTipoTransaccion.
	 */
	public void setLstTipoTransaccion(List<TipoTransaccionEnum> lstTipoTransaccion) {
		this.lstTipoTransaccion = lstTipoTransaccion;
	}
	/**
	 * Método que retorna el valor de lstEstadoSolicitudNovedadEnum.
	 * @return valor de lstEstadoSolicitudNovedadEnum.
	 */
	public List<EstadoSolicitudNovedadEnum> getLstEstadoSolicitudNovedadEnum() {
		return lstEstadoSolicitudNovedadEnum;
	}
	/**
	 * Método encargado de modificar el valor de lstEstadoSolicitudNovedadEnum.
	 * @param valor para modificar lstEstadoSolicitudNovedadEnum.
	 */
	public void setLstEstadoSolicitudNovedadEnum(List<EstadoSolicitudNovedadEnum> lstEstadoSolicitudNovedadEnum) {
		this.lstEstadoSolicitudNovedadEnum = lstEstadoSolicitudNovedadEnum;
	}
	/**
	 * Método que retorna el valor de validacionNovedad.
	 * @return valor de validacionNovedad.
	 */
	public boolean isValidacionNovedad() {
		return validacionNovedad;
	}
	/**
	 * Método encargado de modificar el valor de validacionNovedad.
	 * @param valor para modificar validacionNovedad.
	 */
	public void setValidacionNovedad(boolean validacionNovedad) {
		this.validacionNovedad = validacionNovedad;
	}
	/**
	 * Método que retorna el valor de estadoArhivo.
	 * @return valor de estadoArhivo.
	 */
	public EstadoCargueMasivoEnum getEstadoArhivo() {
		return estadoArhivo;
	}
	/**
	 * Método encargado de modificar el valor de estadoArhivo.
	 * @param valor para modificar estadoArhivo.
	 */
	public void setEstadoArhivo(EstadoCargueMasivoEnum estadoArhivo) {
		this.estadoArhivo = estadoArhivo;
	}
	/**
	 * Método que retorna el valor de nombreArchivo.
	 * @return valor de nombreArchivo.
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	/**
	 * Método encargado de modificar el valor de nombreArchivo.
	 * @param valor para modificar nombreArchivo.
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	/**
	 * Método que retorna el valor de fechaProcesamiento.
	 * @return valor de fechaProcesamiento.
	 */
	public Long getFechaProcesamiento() {
		return fechaProcesamiento;
	}
	/**
	 * Método encargado de modificar el valor de fechaProcesamiento.
	 * @param valor para modificar fechaProcesamiento.
	 */
	public void setFechaProcesamiento(Long fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}
	/**
	 * Método que retorna el valor de serialversionuid.
	 * @return valor de serialversionuid.
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
