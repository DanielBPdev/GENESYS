package com.asopagos.reportes.normativos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TemporalType;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que implementa el Reporte de novedades de afiliación y cartera al día
 * Resolución 1056 <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ReporteNormativoNovedadesEstadoAfiliacion extends ReporteNormativoAbstract {

    public static final String CANTIDAD_REGISTROS = "cantidadRegistros";
    public static final String NOMBRE_ARCHIVO = "nombreArchivo";
    private final EntityManager entityManager;

    public ReporteNormativoNovedadesEstadoAfiliacion(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarNombreArchivo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoResolucion1056(generacionReporteDTO);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#pregenerarReporte(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum,
     *      java.util.Date, java.util.Date)
     */
    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
    	 Integer resultado = null;
         try {
             resultado= Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.Count.NovedadesEstadoAfiliacion")
                     .setParameter("fechaFin", fechaFin, TemporalType.DATE)                    
                     .getSingleResult().toString());
             
         
         } catch (Exception e) {
             throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
         }
         return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarEncabezado()
     */
    @Override
    protected List<String[]> generarEncabezado() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum,
     *      java.util.Date, java.util.Date)
     */
    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  
    	
    	return null;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum,
     *      java.util.Date, java.util.Date, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<String[]> generarData(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin,
    		 Map<String, Object> datosAdicionales) {   	
    		 
    		  List<String[]> dataR = new ArrayList<>();
    	        Integer cantidadRegistros = (Integer) datosAdicionales.get(CANTIDAD_REGISTROS);
    	        String nombreArchivo = (String) datosAdicionales.get(NOMBRE_ARCHIVO);
    		 List<String[]> data = new ArrayList<>();
             try {
            	 String[] registro1 = { "1", (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO),
                         CalendarUtils.darFormatoYYYYMMDD(fechaInicio), CalendarUtils.darFormatoYYYYMMDD(fechaFin), cantidadRegistros.toString(),
                         nombreArchivo };
            	 data.add(registro1);
                 dataR = entityManager.createNamedQuery("Consultar.reporteNormativo.NovedadesEstadoAfiliacion")
                         .setParameter("fechaFin", fechaFin, TemporalType.DATE)                    
                         .getResultList();
                 
                 data.addAll(dataR);
             
             } catch (Exception e) {
                 throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
             }
             return data;  

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
