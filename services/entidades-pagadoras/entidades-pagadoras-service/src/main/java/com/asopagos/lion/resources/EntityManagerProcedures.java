package com.asopagos.lion.resources;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.com.heinsohn.lion.common.dto.EmInformerDTO;

@Startup
@ApplicationScoped
public class EntityManagerProcedures {
	
	@PersistenceContext(unitName = "entidades_pagadoras_PU")
	private EntityManager entityManager;

	public EntityManagerProcedures() {
	}

	@Produces
	public EmInformerDTO produceEmInformerDTO() {
		EmInformerDTO emInformerDTO = new EmInformerDTO();
		emInformerDTO.setEmInformer(entityManager);
		return emInformerDTO;
	}

}
