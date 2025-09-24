package com.asopagos.dto.modelo;

import java.io.Serializable;

import com.asopagos.entidades.transversal.core.Departamento;

/**
 * <b>Descripción: </b> Entidad que mapea los datos de la tabla
 * <code>Departamento</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class DepartamentoModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1547678678786876876L;

	/**
	 * Código identificador de llave primaria del departamento
	 */
	private Short idDepartamento;

	/**
	 * Código identificador del departamento
	 */
	private String codigo;

	/**
	 * Nombre del departamento
	 */
	private String nombre;

	/**
	 * Código indicativo del telefono fijo asoaciado al departamento
	 */
	private String indicativoTelefoniaFija;

	/**
	 * Marca que indica el departamento que aplica para Subsidio Familiar de
	 * Vivienda urbano. Por defecto: Archipiélago de San Andrés, Providencia y
	 * Santa Catalina
	 */
	private Boolean excepcionAplicaFOVIS;

	/**
	 * Método que convierte el DTO en la entidad equivalente
	 * 
	 * @return Entidad <code>Departamento</code> equivalente
	 */
	public Departamento convertToEntity() {
		Departamento departamento = new Departamento();
		departamento.setCodigo(this.codigo);
		departamento.setExcepcionAplicaFOVIS(this.excepcionAplicaFOVIS);
		departamento.setIdDepartamento(this.idDepartamento);
		departamento.setIndicativoTelefoniaFija(this.indicativoTelefoniaFija);
		departamento.setNombre(this.nombre);
		return departamento;
	}

	/**
	 * Método que convierte una entidad <code>Departamento</code> en DTO
	 * 
	 * @param departamento
	 *            La entidad a convertir
	 */
	public void convertToDTO(Departamento departamento) {
		this.codigo = departamento.getCodigo();
		this.excepcionAplicaFOVIS = departamento.getExcepcionAplicaFOVIS();
		this.idDepartamento = departamento.getIdDepartamento();
		this.indicativoTelefoniaFija = departamento.getIndicativoTelefoniaFija();
		this.nombre = departamento.getNombre();
	}

	/**
	 * Obtiene el valor de idDepartamento
	 * 
	 * @return El valor de idDepartamento
	 */
	public Short getIdDepartamento() {
		return idDepartamento;
	}

	/**
	 * Establece el valor de idDepartamento
	 * 
	 * @param idDepartamento
	 *            El valor de idDepartamento por asignar
	 */
	public void setIdDepartamento(Short idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	/**
	 * Obtiene el valor de codigo
	 * 
	 * @return El valor de codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo
	 * 
	 * @param codigo
	 *            El valor de codigo por asignar
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de nombre
	 * 
	 * @return El valor de nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el valor de nombre
	 * 
	 * @param nombre
	 *            El valor de nombre por asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el valor de indicativoTelefoniaFija
	 * 
	 * @return El valor de indicativoTelefoniaFija
	 */
	public String getIndicativoTelefoniaFija() {
		return indicativoTelefoniaFija;
	}

	/**
	 * Establece el valor de indicativoTelefoniaFija
	 * 
	 * @param indicativoTelefoniaFija
	 *            El valor de indicativoTelefoniaFija por asignar
	 */
	public void setIndicativoTelefoniaFija(String indicativoTelefoniaFija) {
		this.indicativoTelefoniaFija = indicativoTelefoniaFija;
	}

	/**
	 * Obtiene el valor de excepcionAplicaFOVIS
	 * 
	 * @return El valor de excepcionAplicaFOVIS
	 */
	public Boolean getExcepcionAplicaFOVIS() {
		return excepcionAplicaFOVIS;
	}

	/**
	 * Establece el valor de excepcionAplicaFOVIS
	 * 
	 * @param excepcionAplicaFOVIS
	 *            El valor de excepcionAplicaFOVIS por asignar
	 */
	public void setExcepcionAplicaFOVIS(Boolean excepcionAplicaFOVIS) {
		this.excepcionAplicaFOVIS = excepcionAplicaFOVIS;
	}
}
