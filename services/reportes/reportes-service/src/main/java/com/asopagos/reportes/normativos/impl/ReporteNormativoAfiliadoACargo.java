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
 *
 * @author
 */

public class ReporteNormativoAfiliadoACargo extends ReporteNormativoAbstract {

    private final EntityManager entityManager;

    public ReporteNormativoAfiliadoACargo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarNombreArchivo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoCircular020(generacionReporteDTO);
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
            resultado= Integer.parseInt(entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_A_CARGO_COUNT)
                    //.setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
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
        List<String[]> lstEncabezado = new ArrayList();
        String[] encabezado = { "Tipo de identificación de la empresa", "Número de identificación Empresa",
                "Tipo de identificación Afiliado", "Número de identificación afiliado", "Tipo de identificación de la persona a cargo",
                "Numero de identificación de la persona a cargo","Primer Nombre de la persona a cargo", 
                "Segundo Nombre de la persona a cargo", "Primer Apellido de la persona a cargo",
                "Segundo Apellido de la persona a cargo", "Fecha de Nacimiento de la persona a cargo", "Género",
                "Parentesco de la persona a cargo","Código municipio de residencia de la persona a cargo", "Área Geográfica de Residencia de la persona a cargo", 
                "Condición de discapacidad de la persona a cargo",
                "Tipo de cuota monetaria pagado a la persona a cargo","Valor de la cuota monetaria pagada a la persona a cargo",
                "Número de cuotas pagadas", "Número de periodos pagados" };
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum,
     *      java.util.Date, java.util.Date)
     */
    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {    	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
        List<String[]> data = null;
        try {
            data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADOS_A_CARGO)
                    //.setParameter("fechaInicio", fechaInicio, TemporalType.DATE)
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
