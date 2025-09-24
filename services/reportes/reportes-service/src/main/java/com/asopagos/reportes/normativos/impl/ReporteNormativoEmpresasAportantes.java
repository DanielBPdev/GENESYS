package com.asopagos.reportes.normativos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * @author 
 */
public class ReporteNormativoEmpresasAportantes extends ReporteNormativoAbstract {
    
    
	private EntityManager entityManager;
	
	private static final ILogger logger = LogManager.getLogger(ReporteNormativoDesagregadoCarteraAportante.class);
    
   
    public ReporteNormativoEmpresasAportantes(EntityManager entityManager) {
        this.entityManager = entityManager;    
    }
    
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoCircular020(generacionReporteDTO);
    }    

    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {    	         
    	try {
			logger.debug("Pregenerando ReporteNormativoEmpresasAportantes...");
			Integer resultado = (int)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_APORTANTES_COUNT)
					.setParameter("fecha", fechaFin)
					.getSingleResult();					
					
			logger.debug("ReporteNormativoEmpresasAportantes pregenerado");
			return resultado;
		} catch (Exception e) {
			logger.error("Error pregenerando ReporteNormativoEmpresasAportantes", e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
    }
    
    @Override
    protected List<String[]> generarEncabezado() {
    	ArrayList<String[]> encabezado = new ArrayList<>();
    	String[] linea = {"Tipo de identificación","Número de identificación","Nombre","Código municipio","Dirección","Estado de vinculación","Tipo de Aportante","Tipo de sector","Actividad económica principal","Situación de la Empresa frente a la Ley 1429 de 2010","Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010","Situación de la Empresa frente a la Ley 590 de 2000","Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000","Aporte total mensual","Intereses pagados por mora","Valor reintegros"};
    	encabezado.add(linea);
    	return encabezado;
    }

    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {      	
    	Date fechaFin = generacionReporteDTO.getFechaFin();    	
			logger.debug("Generando datos ReporteNormativoEmpresasAportantes...");
			List<String[]> data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_APORTANTES)
					.setParameter("fecha", fechaFin)
					.getResultList();
			logger.debug("ReporteNormativoEmpresasAportantes datos generados");
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
