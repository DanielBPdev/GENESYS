package com.asopagos.validaciones.ejb;

import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.dto.ValidacionDTO;



/**
 * <b>Descripción:</b> Interfaz que representa un validador <b>Historia de
 * Usuario: </b>TRA
 * 
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
public interface ValidadorCore {
	/**
	 * Método que ejecuta la lógica definida para el validador
	 * 
	 * @param datosValidacion
	 *            Son los datos necesarios para la validación
	 * @return Objeto con el resultado de la validación
	 */
	public ValidacionDTO execute(Map<String, String> datosValidacion);

	/**
	 * Método que asigna un EntityManager al validador
	 * 
	 * @param entityManager
	 *            Es el EntityManager a Asignar
	 */
	public void setEntityManager(EntityManager entityManager);
}
