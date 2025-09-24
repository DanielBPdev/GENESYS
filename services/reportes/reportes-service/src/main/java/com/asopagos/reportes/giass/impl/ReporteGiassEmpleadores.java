package com.asopagos.reportes.giass.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.giass.ReporteGiassAbstract;

public class ReporteGiassEmpleadores extends ReporteGiassAbstract {

	private final EntityManager entityManager;

	public ReporteGiassEmpleadores(EntityManager entityManager){
		String[] headerAux = {"Tipo Identificacion","Numero identificacion","Digito verificacion", "Razon social", "Naturaleza juridica", "Tipo identificacion RL" , "Numero identificacion RL", "Correo electronico RL", "Telefono RL" , "Codigo departamento" , "Codigo municipio", "Direccion", "Fecha renovacion", "Numero empleados", "Actividad economica principal", "Actividad economica secundaria", "Fecha constitucion"};
		this.listHeader = new ArrayList<>();
		this.listHeader.add(headerAux);
		this.entityManager = entityManager;
		this.fileName = "BENEFICIARIOS";
    }

	@Override
	public List<String[]> obtenerDatos() {
		
		List<Object[]> resultadoConsultaNativa = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPLEADORES_GIASS)
				.getResultList();		
		
		return this.convertResponseNativeToArrayString(resultadoConsultaNativa);
	}
	
}
