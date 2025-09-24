package com.asopagos.reportes.normativos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase encargada del procesamiento del reporte Postulaciones y asignaciones FOVIS <br>
 * <b>Código en la norma:</b> 5-444A <br>
 * <b>Descripción del reporte:</b> Reportar la información de las fechas programadas anualmente para las postulaciones y asignaciones de los
 * subsidios de vivienda FOVIS en el período informado.<br>
 * <b>Norma asociada al reporte:</b> Circular Externa 00020 de Diciembre de 2016 (Inciso 5-444)
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ReporteNormativoPostulacionesAsignacionesFOVIS extends ReporteNormativoAbstract{

    private final EntityManager entityManager;
    
    public ReporteNormativoPostulacionesAsignacionesFOVIS(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoCircular020(generacionReporteDTO);
    }

    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
        Integer resultado = null;
        try {
            resultado= entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_ASIGNACIONES)
                    .setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                    .getResultList().size();
            
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return resultado;
    }

    @Override
    protected List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList<>();
        String[] encabezado = {"Año","Fecha de apertura de la postulación","Fecha de Cierre de postulación","Fecha de Asignación"};
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
    	Date fechaInicio = generacionReporteDTO.getFechaInicio();
        List<String[]> data = null;
        try {
            data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_POSTULACIONES_ASIGNACIONES)
                    .setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                    .getResultList();
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
