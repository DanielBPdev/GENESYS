package com.asopagos.reportes.giass.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.giass.ReporteGiassAbstract;

public class ReporteGiassTrabajadores extends ReporteGiassAbstract {

	private final EntityManager entityManager;

	public ReporteGiassTrabajadores(EntityManager entityManager){
		
		String[] headerAux = {"Tipo Identificacion","Numero identificacion","Primer apellido", "Segundo apellido", "Primer nombre", "Segundo nombre", "Fecha nacimiento" , "Genero", "NIvel educativo"};
		this.listHeader = new ArrayList<>();
		this.listHeader.add(headerAux);
		this.entityManager = entityManager;
		this.fileName = "TRABAJADORES";
    }

	@Override
	public List<String[]> obtenerDatos() {
		
		List<Object[]> resultadoConsultaNativa = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_TRABAJADORES_GIASS)
				.getResultList();		
		
		return this.convertResponseNativeToArrayString(resultadoConsultaNativa);
	}
}
