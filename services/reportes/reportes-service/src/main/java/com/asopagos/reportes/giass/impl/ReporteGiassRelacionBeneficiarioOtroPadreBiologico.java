package com.asopagos.reportes.giass.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.giass.ReporteGiassAbstract;

public class ReporteGiassRelacionBeneficiarioOtroPadreBiologico  extends ReporteGiassAbstract {

	private final EntityManager entityManager;

	public ReporteGiassRelacionBeneficiarioOtroPadreBiologico(EntityManager entityManager){
		//Se debe modificar el encabezado 
		String[] headerAux = {"Tipo Identificacion","Numero identificacion","Primer apellido", "Segundo apellido", "Primer nombre", "Segundo nombre", "Fecha nacimiento" , "Genero", "NIvel educativo"};
		this.listHeader = new ArrayList<>();
		this.listHeader.add(headerAux);
		this.entityManager = entityManager;
		this.fileName = "RELACIONBENEFICIARIOOTROPADREBIOLOGICO";
    }

	@Override
	public List<String[]> obtenerDatos() {
		
		List<Object[]> resultadoConsultaNativa = entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_RELACION_BENEFICIARIO_OTRO_PADRE_BIOLOGICO)
				.getResultList();
		
		/*
		 * Se opera sobre los valores repetidos y se toma el segundo valor que sería
		 * el otro padre biologico
		 */
		
		Iterator<Object[]> iteradorConsultaNativa = resultadoConsultaNativa
				.iterator();
		String valorAuxiliar = null;
		while (iteradorConsultaNativa.hasNext()) {
			Object[] resultadoNativo = iteradorConsultaNativa.next();
			if (valorAuxiliar == null || !(valorAuxiliar.equals((String) resultadoNativo[1]))){
				valorAuxiliar = (String) resultadoNativo[1];
			}else{
				iteradorConsultaNativa.remove();
			}
		}
		
		return this.convertResponseNativeToArrayString(resultadoConsultaNativa);
	}


}
