package com.asopagos.reportes.normativos.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción: </b> Clase encargada de la generación del reporte aviso de
 * incumplimiento solicitado por la Unidad de Gestión Pensional y Parafiscales
 * en la Resolución 2082 de 2016 <br/>
 * <b>Historia de Usuario: </b> Reportes Normativos
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ReporteNormativoAvisoIncumplimiento extends ReporteNormativoAbstract {

	/**
	 * Entity manager
	 */
	private final EntityManager entityManager;

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(ReporteNormativoAvisoIncumplimiento.class);

	/**
	 * Constructor
	 * 
	 * @param entityManager
	 *            Entity manager
	 */
	public ReporteNormativoAvisoIncumplimiento(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#
	 * generarNombreArchivo(GeneracionReporteNormativoDTO)
	 */
	@Override
	public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
		return GeneradorNombreArchivoUtil.generarNombreArchivoResolucion2082(generacionReporteDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#
	 * pregenerarReporte(ReporteNormativoEnum, Date, Date)
	 */
	@Override
	public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
		try {
			logger.debug("Pregenerando ReporteNormativoAvisoIncumplimiento...");
			Integer resultado = (int)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AVISO_INCUMPLIMIENTO_COUNT)
					.setParameter("fecha", fechaFin, TemporalType.DATE)
					.getSingleResult();
			logger.debug("ReporteNormativoAvisoIncumplimiento pregenerado");
			return resultado;
		} catch (Exception e) {
			logger.error("Error pregenerando ReporteNormativoAvisoIncumplimiento", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#
	 * generarEncabezado()
	 */
	@Override
	protected List<String[]> generarEncabezado() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(
	 * ReporteNormativoEnum, Date, Date)
	 */
	@SuppressWarnings("unchecked")
	 @Override
	    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  
	    	Date fechaFin = generacionReporteDTO.getFechaFin();
	    	try {
			logger.debug("Generando datos ReporteNormativoAvisoIncumplimiento...");
			List<String[]> data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AVISO_INCUMPLIMIENTO)
					.setParameter("fecha", fechaFin, TemporalType.DATE)
					.getResultList();
			logger.debug("ReporteNormativoAvisoIncumplimiento datos generados");
			return data;
		} catch (Exception e) {
			logger.error("Error generando datos ReporteNormativoAvisoIncumplimiento", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
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
