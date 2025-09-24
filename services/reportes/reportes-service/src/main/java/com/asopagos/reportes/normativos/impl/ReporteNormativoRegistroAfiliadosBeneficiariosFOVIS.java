package com.asopagos.reportes.normativos.impl;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class ReporteNormativoRegistroAfiliadosBeneficiariosFOVIS extends ReporteNormativoAbstract {

	private final EntityManager entityManager;

	public ReporteNormativoRegistroAfiliadosBeneficiariosFOVIS(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
		return GeneradorNombreArchivoUtil.generarNombreArchivoOtrosReportes(generacionReporteDTO);
	}

	@Override
	public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
		Integer resultado = null;
		try {
			resultado = (Integer)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_BENEFICIADOS_COUNT_FOVIS)					
					.setParameter("fechaFin", fechaFin, TemporalType.DATE).getSingleResult();

		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return resultado;
	}

	@Override
	protected List<String[]> generarEncabezado() {
		List<String[]> lstEncabezado = new ArrayList<>();
		String[] encabezado = { "Año", "Fecha de apertura de la postulación", "Fecha de Cierre de postulación",
				"Fecha de Asignación" };
		lstEncabezado.add(encabezado);
		return lstEncabezado;
	}

	@Override
	protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
		List<String[]> data = null;
		try {
			data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_BENEFICIADOS_FOVIS)
					.setParameter("fechaFin", fechaFin, TemporalType.DATE).getResultList();
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return data;
	}

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date, java.util.Map)
     */
    @Override
    protected List<String[]> generarData(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin,
            Map<String, Object> datosAdicionales) {
        return null;
    }

	@Override
	public String generarNombreFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] generarFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
