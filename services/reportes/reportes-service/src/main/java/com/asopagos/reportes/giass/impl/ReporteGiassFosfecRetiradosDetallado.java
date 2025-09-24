package com.asopagos.reportes.giass.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.giass.ReporteGiassAbstract;

public class ReporteGiassFosfecRetiradosDetallado extends ReporteGiassAbstract {
	
	private static List<Object[]> listaAfiliacionesRetiradosCajaNativo;

	private final EntityManager entityManager;

	public ReporteGiassFosfecRetiradosDetallado(EntityManager entityManager) {
		this.listHeader = new ArrayList<>();
		this.entityManager = entityManager;
		this.fileName = "FOSFEC_RETIRADOS_DETALLADO";
	}

	@Override
	protected List<String[]> obtenerDatos() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -3);
		Date fechaLimite = calendar.getTime();

		List<Object> listaDesafiliadosCajaNativo = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESAFILIADOS_CAJA)
				.setParameter("fechaLimite", fechaLimite).getResultList();

		List<BigInteger> listaAfiliadosRetirados = new ArrayList<>();
		for (int i = 0; i < listaDesafiliadosCajaNativo.size(); i++) {
			listaAfiliadosRetirados.add((BigInteger) (listaDesafiliadosCajaNativo.get(i)));
		}
		
		listaAfiliacionesRetiradosCajaNativo = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIACIONES_CAJA_DESAFILIADOS)
				.setParameter("listaAfiliadosRetirados", listaAfiliadosRetirados)
				.setParameter("fechaLimite", fechaLimite).getResultList();

		for (int i = 0; i < listaDesafiliadosCajaNativo.size(); i++) {
			this.contruirRetiradoFosfecDetallado(listaDesafiliadosCajaNativo.get(i), fechaLimite);
		}

		return null;
		// return
		// this.convertResponseNativeToArrayString(resultadoConsultaNativa);
	}

	/**
	 * Método que se encarga de contruir una lista de retirados de la caja de
	 * compensacion basado en las diferentes afiliaciones que ha tenido un
	 * cotizante a la caja a partir de una fecha especifica de afiliacion
	 * 
	 * @param afiliadoRetiradoFosfec
	 * @param listaAfiliacionesRetiradosCajaNativo
	 * @return
	 */
	private String[] contruirRetiradoFosfecDetallado(Object afiliadoRetiradoFosfec, Date fechaLimite) {
		Iterator<Object[]> iteratorListaAfiliaciones = listaAfiliacionesRetiradosCajaNativo.iterator();
		Object[] afiliacion = iteratorListaAfiliaciones.next();
		int numeroMesRevision = 0;

		while (iteratorListaAfiliaciones.hasNext() && afiliacion[0].toString().equals(afiliadoRetiradoFosfec.toString())
				&& numeroMesRevision <= 36) {

			String[] tipoAfiliadoHistoricoMensual = new String[36];
			String[] salarioHistoricoMensual = new String[36];

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(fechaLimite);

			Date fechaInicio = (Date) afiliacion[1];
			Date fechaFin = (Date) afiliacion[2];

			for (int i = numeroMesRevision; i < 35; i++) {
				calendar.add(Calendar.MONTH, 1);
				Date fechaPeriodo = calendar.getTime();
				if (fechaFin.after(fechaPeriodo)) {
					if (fechaInicio.before(fechaPeriodo)) {
						tipoAfiliadoHistoricoMensual[i] = afiliacion[3].toString();
						salarioHistoricoMensual[i] = afiliacion[4].toString();
					}
				} else {
					iteratorListaAfiliaciones.remove();
					afiliacion = iteratorListaAfiliaciones.next();
					if(afiliacion[0].toString().equals(afiliadoRetiradoFosfec.toString())){
						continue;
					}else{
						break;
					}
				}
			}
		}
		// Hasta este punto ya se tiene la informacion por mes del tipo de afiliado y salario
		//Falta colocar datos basicos del afiliados para retornar e ir formando el arreglo
		return null;
	}

}