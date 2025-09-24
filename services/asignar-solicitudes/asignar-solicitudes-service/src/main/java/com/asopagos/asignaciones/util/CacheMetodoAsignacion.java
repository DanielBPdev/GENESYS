package com.asopagos.asignaciones.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.enumeraciones.core.MetodoAsignacionEnum;

/**
 * <b>Descripción:</b> Singleton de metodos de asignacion de solicitudes
 * <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class CacheMetodoAsignacion {

	/**
	 * Instancia del Singleton
	 */
	private static CacheMetodoAsignacion metodoAsignacion;

	/**
	 * Instancias de los distintos metodos de asignacion
	 */
	private Map<MetodoAsignacionEnum, MetodoAsignacion> metodosAsignacion;

	/**
	 * Metodo constructor
	 */
	public CacheMetodoAsignacion() {
		init();
	}

	/**
	 * Metodo encargado de inicializar las instancias
	 */
	private void init() {
		metodosAsignacion = new HashMap<>();
		metodosAsignacion.put(MetodoAsignacionEnum.CONSECUTIVO_TURNOS, new AsignacionConsecutivoTurnos());
		metodosAsignacion.put(MetodoAsignacionEnum.NUMERO_SOLICITUDES, new AsignacionNumeroSolicitudes());
		metodosAsignacion.put(MetodoAsignacionEnum.PREDEFINIDO, new AsignacionPredefinida());
	}

	/**
	 * Método encargado de ejecutar la lógica correspondiente a cada metodo de
	 * asignacion
	 * 
	 * @return el usuario al cual se le realiza la asignación
	 */
	public String ejecutar(ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion, Long sede, EntityManager entityManager)
			throws Exception {
		return metodosAsignacion.get(parametrizacionMetodoAsignacion.getMetodo())
				.ejecutar(parametrizacionMetodoAsignacion, sede ,entityManager);
	}

	/**
	 * Metodo encargado de obtener la instancia del Singleton
	 * 
	 * @return instancia del singleton
	 */
	public static CacheMetodoAsignacion getInstance() {
		if (metodoAsignacion == null) {
			metodoAsignacion = new CacheMetodoAsignacion();
		}
		return metodoAsignacion;
	}

	/**
	 * @return the metodoAsignacion
	 */
	public static CacheMetodoAsignacion getMetodoAsignacion() {
		return metodoAsignacion;
	}

	/**
	 * @param metodoAsignacion
	 *            the metodoAsignacion to set
	 */
	public static void setMetodoAsignacion(CacheMetodoAsignacion metodoAsignacion) {
		CacheMetodoAsignacion.metodoAsignacion = metodoAsignacion;
	}

	/**
	 * @return the metodosAsignacion
	 */
	public Map<MetodoAsignacionEnum, MetodoAsignacion> getMetodosAsignacion() {
		return metodosAsignacion;
	}

	/**
	 * @param metodosAsignacion
	 *            the metodosAsignacion to set
	 */
	public void setMetodosAsignacion(Map<MetodoAsignacionEnum, MetodoAsignacion> metodosAsignacion) {
		this.metodosAsignacion = metodosAsignacion;
	}

}
