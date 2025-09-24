package com.asopagos.asignaciones.util;

import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;

/**
 * <b>Descripción:</b> Interfaz de metodos de asignacion de solicitudes
 * <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public interface MetodoAsignacion {

	/**
	 * Metodo encargado de asignar solicitudes dependiendo el metodo de
	 * asignacion parametrizado para la sede y proceso
	 * 
	 * @return usuario del sistema
	 */
	public String ejecutar(ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion, Long sede, EntityManager entityManager) throws Exception;
}
