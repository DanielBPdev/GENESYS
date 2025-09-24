package com.asopagos.reportes.giass.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.giass.ReporteGiassAbstract;

public class ReporteGiassAfiliacionesEmpleadores extends ReporteGiassAbstract {

	private final EntityManager entityManager;

	public ReporteGiassAfiliacionesEmpleadores(EntityManager entityManager) {

		String[] headerAux = { "Tipo Identificacion", "Numero identificacion", "Primer apellido", "Segundo apellido",
				"Primer nombre", "Segundo nombre", "Fecha nacimiento", "Genero", "Departamento nacimiento",
				"Municipio nacimiento", "Fecha expedicion documento" };
		this.listHeader = new ArrayList<>();
		this.listHeader.add(headerAux);
		this.entityManager = entityManager;
		this.fileName = "AFILIACIONES_EMPLEADORES";
	}

	@Override
	public List<String[]> obtenerDatos() {

		List<Object[]> resultadoConsultaNativa = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACIONES_EMPLEADORES_GIASS).getResultList();

		List<Object[]> listaAfiliacionesEmpleadores = new ArrayList<>();
		
		Iterator<Object[]> iteradorConsultaNativa = resultadoConsultaNativa
				.iterator();
		BigInteger valorAuxiliar = null;
		while (iteradorConsultaNativa.hasNext()) {
			Object[] resultadoNativo = iteradorConsultaNativa.next();
			if (valorAuxiliar == null || !(valorAuxiliar.equals((BigInteger) resultadoNativo[0]))){
				Object[] afiliacionEmpleador = new Object[4];
				afiliacionEmpleador[0] = resultadoNativo[1] ;
				afiliacionEmpleador[1] = resultadoNativo[2];
				afiliacionEmpleador[2] = resultadoNativo[3];
				afiliacionEmpleador[3] = null;
				listaAfiliacionesEmpleadores.add(afiliacionEmpleador);
				valorAuxiliar = (BigInteger) resultadoNativo[0];
			}else{
				Object[] ultimaAfiliacion = listaAfiliacionesEmpleadores.get(listaAfiliacionesEmpleadores.size()-1);
				ultimaAfiliacion[3] = resultadoNativo[3];
				listaAfiliacionesEmpleadores.set(listaAfiliacionesEmpleadores.size()-1, ultimaAfiliacion);
				valorAuxiliar = null;
			}
		}
		return this.convertResponseNativeToArrayString(listaAfiliacionesEmpleadores);
	}

}
