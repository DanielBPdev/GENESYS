package com.asopagos.reportes.normativos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * @author 
 */
public class ReporteNormativoEmpresasEnMora extends ReporteNormativoAbstract {
    
    
    private final EntityManager entityManager;
    
    
    public ReporteNormativoEmpresasEnMora(EntityManager entityManager) {
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
            resultado = Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.Count.EmpresaEnMora")
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                    .getSingleResult().toString());

        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        return resultado;
    }    

    @Override
    protected List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList();
        String[] encabezado = {"Tipo de Identificación de la empresa", "Número de Identificación de la empresa", "Nombre Empresa",
            "Código municipio de la empresa", "Dirección de la Empresa", "Representante Legal", "Fecha de inicio de la mora",
            "Saldo en Mora", "Periodos en mora", "Gestión del proceso de cobro", "Acuerdo de pago", "Cartera recuperada",
            "Correo electrónico"};
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
    	List<String[]> data = null;
        try {
            data = entityManager.createNamedQuery("Consultar.reporteNormativo.EmpresaEnMora")                    
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
