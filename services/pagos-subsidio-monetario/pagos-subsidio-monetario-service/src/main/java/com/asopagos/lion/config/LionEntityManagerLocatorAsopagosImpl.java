package com.asopagos.lion.config;

import javax.persistence.EntityManager;
import co.com.heinsohn.lion.common.dto.EmInformerDTO;
import co.com.heinsohn.lion.common.interfaces.EntityManagerInterface;

/**
 * Clase que crea el EntityManager con el nombre de la unidad de persistencia
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 * 
 */
public class LionEntityManagerLocatorAsopagosImpl implements EntityManagerInterface {

	@Override
	public EntityManager getEntityManager(EmInformerDTO emInformerDTO) {
		return (EntityManager) emInformerDTO.getEmInformer();
	}

}