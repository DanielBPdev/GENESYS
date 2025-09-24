package com.asopagos.constantes.parametros.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.general.Parametro;
import com.asopagos.enumeraciones.core.CategoriaParametroEnum;
import com.asopagos.enumeraciones.core.SubCategoriaParametroEnum;
import com.asopagos.enumeraciones.core.DataTypeEnum;

/**
 * <b>Descripción:</b> <b>Historia de Usuario:</b>
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@XmlRootElement
public class ConstantesParametroDTO implements Serializable {

	/**
	 * Nombre del parametro
	 */
	@NotNull
	private String nombre;

	/**
	 * Valor el cual tiene el parametro
	 */
	private String valor;

	/**
	 * Sub Categoria a la que pertenece el parametro
	 */
	private SubCategoriaParametroEnum subCategoriaEnum;

	/**
	 * Categoria a la que pertenece el parametro
	 */
	private CategoriaParametroEnum categoriaParametroEnum;

	/**
	 * Identificador que se encarga de decir si las constantesParametros se
	 * deben de cargar al inicio
	 */
	private Boolean cargaInicio;

	/**
	 * Descripcion perteneciente a un parametro
	 */
	private String descripcion;
	
	/**
	 * Tipo de dato del parametro
	 */
	private DataTypeEnum tipoDato;

	/**
	 * Tipo de dato del visualizarPantalla
	 */
	private Boolean visualizarPantalla;
	/**
	 * Método encargado de convertir un Parametro a ConstanteParametroDTO
	 * 
	 * @param parametro
	 * @return retorna el ConstantesParametroDTO
	 */
	public ConstantesParametroDTO convertirParametroDTO(Parametro parametro) {
		ConstantesParametroDTO constanteParametroDTO = new ConstantesParametroDTO();
		constanteParametroDTO.setNombre(parametro.getNombre());
		constanteParametroDTO.setValor(parametro.getValor());
		if (parametro.getSubCategoriaParametroEnum() != null) {
			constanteParametroDTO.setSubCategoriaEnum(parametro.getSubCategoriaParametroEnum());
			constanteParametroDTO
					.setCategoriaParametroEnum(parametro.getSubCategoriaParametroEnum().getCategoriaEnum());
		}
		constanteParametroDTO.setDescripcion(parametro.getDescripcion());
		constanteParametroDTO.setTipoDato(parametro.getTipoDato() != null ? parametro.getTipoDato():null);
		constanteParametroDTO.setVisualizarPantalla(parametro.getVisualizarPantalla());
		return constanteParametroDTO;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the subCategoriaEnum
	 */
	public SubCategoriaParametroEnum getSubCategoriaEnum() {
		return subCategoriaEnum;
	}

	/**
	 * @param subCategoriaEnum
	 *            the subCategoriaEnum to set
	 */
	public void setSubCategoriaEnum(SubCategoriaParametroEnum subCategoriaEnum) {
		this.subCategoriaEnum = subCategoriaEnum;
	}

	/**
	 * @return the categoriaParametroEnum
	 */
	public CategoriaParametroEnum getCategoriaParametroEnum() {
		return categoriaParametroEnum;
	}

	/**
	 * @param categoriaParametroEnum
	 *            the categoriaParametroEnum to set
	 */
	public void setCategoriaParametroEnum(CategoriaParametroEnum categoriaParametroEnum) {
		this.categoriaParametroEnum = categoriaParametroEnum;
	}

	/**
	 * @return the cargaInicio
	 */
	public Boolean getCargaInicio() {
		return cargaInicio;
	}

	/**
	 * @param cargaInicio
	 *            the cargaInicio to set
	 */
	public void setCargaInicio(Boolean cargaInicio) {
		this.cargaInicio = cargaInicio;
	}

	/**
	 * Método que retorna el valor de descripcion.
	 * 
	 * @return valor de descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método encargado de modificar el valor de descripcion.
	 * 
	 * @param valor
	 *            para modificar descripcion.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoDato
	 */
	public DataTypeEnum getTipoDato() {
		return tipoDato;
	}

	/**
	 * @param tipoDato the tipoDato to set
	 */
	public void setTipoDato(DataTypeEnum tipoDato) {
		this.tipoDato = tipoDato;
	}

	/**
	 * @return the getVisualizarPantalla
	 */

	 public Boolean getVisualizarPantalla() {
		return visualizarPantalla;
	}

	/**
	 * @param visualizarPantalla the visualizarPantalla to set
	 */

	public void setVisualizarPantalla(Boolean visualizarPantalla) {
		this.visualizarPantalla = visualizarPantalla;
	}
	
	
}