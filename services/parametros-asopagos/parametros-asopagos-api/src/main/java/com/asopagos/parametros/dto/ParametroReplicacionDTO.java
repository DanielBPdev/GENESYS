package com.asopagos.parametros.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.parametros.enums.EnumAccion;

/**
 * <b>Descripción:</b> DTO que contiene la información correspondiente a un
 * evento de replicación. 
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Carlos Eduardo García Amaya <cargarcia@heinsohn.com.co>
 */
@XmlRootElement
public class ParametroReplicacionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Entity en formato JSON con los datos que se van a modificar en
	 * base de datos
	 */
	private String objetoModificar;
	/**
	 * Nombre completo de la clase java tipo entity que se va a modificar en 
	 * base de datos. Ejemplo: com.asopagos.entidades.transversal.core.Departamento
	 */
	private String claseObjetoModificar;
	/**
	 * Nombre de la accion CRUD a ejecutar en la base de datos: Crear,
	 * Actualizar, Eliminar.
	 * 
	 */
	private EnumAccion accion;
	
	public ParametroReplicacionDTO(String objetoModificar, String claseObjetoModificar, EnumAccion accion) {
		this.objetoModificar = objetoModificar;
		this.claseObjetoModificar = claseObjetoModificar;
		this.accion = accion;
	}

	/**
	 * Obtiene el valor de la propiedad objetoModificar
	 * @return
	 */
	public String getObjetoModificar() {
		return objetoModificar;
	}
	
	/**
	 * Asigna el valor de la propiedad objetoModificar
	 * @param objetoModificar
	 */
	public void setObjetoModificar(String objetoModificar) {
		this.objetoModificar = objetoModificar;
	}

	/**
	 * Obtiene el valor de la propiedad claseObjetoModificar
	 * @return
	 */
	public String getClaseObjetoModificar() {
		return claseObjetoModificar;
	}

	/**
	 * Asigna el valor de la propiedad claseObjetoModificar
	 * @param claseObjetoModificar
	 */
	public void setClaseObjetoModificar(String claseObjetoModificar) {
		this.claseObjetoModificar = claseObjetoModificar;
	}

	/**
	 * Obtiene el valor de la propiedad accion
	 * @return
	 */
	public Enum getAccion() {
		return accion;
	}

	/**
	 * Asigna el valor de la propiedad accion
	 * @param accion
	 */
	public void setAccion(EnumAccion accion) {
		this.accion = accion;
	}

	public ParametroReplicacionDTO() {
		
	}
}
