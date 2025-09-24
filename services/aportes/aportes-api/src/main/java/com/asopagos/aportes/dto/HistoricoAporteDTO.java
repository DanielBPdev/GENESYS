/**
 * 
 */
package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.enumeraciones.auditoria.TipoOperacionEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;

/**
 * Clase que se encarga encarga de tener la informacion de cuenta aporte DTO
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class HistoricoAporteDTO implements Serializable {

	/**
	 * Tipo de operación
	 */
	private TipoOperacionEnum tipoOperacion;
	/**
	 * Número de la operación
	 */
	private String numeroOperacion;
	/**
	 * Fecha de Operación
	 */
	private Long fechaOperacion;
	/**
	 * Valor Mora
	 */
	private BigDecimal mora;
	/**
	 * Valor total
	 */
	private BigDecimal total;
	/**
	 * Saldo Final
	 */
	private BigDecimal SaldoFinal;
	/**
	 * Estado del aporte
	 */
	private EstadoAporteEnum estadoAporte;
	/**
	 * Estado del registro
	 */
	private EstadoRegistroAporteEnum estadoRegistro;
	/**
	 * Cuenta aporte que tiene el historial
	 */
	private CuentaAporteDTO cuentaAporte;

	/**
	 * 
	 */
	public HistoricoAporteDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Método que retorna el valor de tipoOperacion.
	 * 
	 * @return valor de tipoOperacion.
	 */
	public TipoOperacionEnum getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Método que retorna el valor de numeroOperacion.
	 * 
	 * @return valor de numeroOperacion.
	 */
	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * Método que retorna el valor de fechaOperacion.
	 * 
	 * @return valor de fechaOperacion.
	 */
	public Long getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * Método que retorna el valor de mora.
	 * 
	 * @return valor de mora.
	 */
	public BigDecimal getMora() {
		return mora;
	}

	/**
	 * Método que retorna el valor de total.
	 * 
	 * @return valor de total.
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Método que retorna el valor de saldoFinal.
	 * 
	 * @return valor de saldoFinal.
	 */
	public BigDecimal getSaldoFinal() {
		return SaldoFinal;
	}

	/**
	 * Método que retorna el valor de estadoAporte.
	 * 
	 * @return valor de estadoAporte.
	 */
	public EstadoAporteEnum getEstadoAporte() {
		return estadoAporte;
	}

	/**
	 * Método que retorna el valor de estadoRegistro.
	 * 
	 * @return valor de estadoRegistro.
	 */
	public EstadoRegistroAporteEnum getEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * Método que retorna el valor de cuentaAporte.
	 * 
	 * @return valor de cuentaAporte.
	 */
	public CuentaAporteDTO getCuentaAporte() {
		return cuentaAporte;
	}

	/**
	 * Método encargado de modificar el valor de tipoOperacion.
	 * 
	 * @param valor
	 *            para modificar tipoOperacion.
	 */
	public void setTipoOperacion(TipoOperacionEnum tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroOperacion.
	 * 
	 * @param valor
	 *            para modificar numeroOperacion.
	 */
	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	/**
	 * Método encargado de modificar el valor de fechaOperacion.
	 * 
	 * @param valor
	 *            para modificar fechaOperacion.
	 */
	public void setFechaOperacion(Long fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * Método encargado de modificar el valor de mora.
	 * 
	 * @param valor
	 *            para modificar mora.
	 */
	public void setMora(BigDecimal mora) {
		this.mora = mora;
	}

	/**
	 * Método encargado de modificar el valor de total.
	 * 
	 * @param valor
	 *            para modificar total.
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Método encargado de modificar el valor de saldoFinal.
	 * 
	 * @param valor
	 *            para modificar saldoFinal.
	 */
	public void setSaldoFinal(BigDecimal saldoFinal) {
		SaldoFinal = saldoFinal;
	}

	/**
	 * Método encargado de modificar el valor de estadoAporte.
	 * 
	 * @param valor
	 *            para modificar estadoAporte.
	 */
	public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
		this.estadoAporte = estadoAporte;
	}

	/**
	 * Método encargado de modificar el valor de estadoRegistro.
	 * 
	 * @param valor
	 *            para modificar estadoRegistro.
	 */
	public void setEstadoRegistro(EstadoRegistroAporteEnum estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * Método encargado de modificar el valor de cuentaAporte.
	 * 
	 * @param valor
	 *            para modificar cuentaAporte.
	 */
	public void setCuentaAporte(CuentaAporteDTO cuentaAporte) {
		this.cuentaAporte = cuentaAporte;
	}

}
