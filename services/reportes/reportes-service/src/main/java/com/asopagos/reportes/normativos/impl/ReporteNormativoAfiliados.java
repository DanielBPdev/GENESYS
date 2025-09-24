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

public class ReporteNormativoAfiliados extends ReporteNormativoAbstract{

    private final EntityManager entityManager;
    
    public ReporteNormativoAfiliados(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarNombreArchivo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoCircular020(generacionReporteDTO);
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#pregenerarReporte(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */
    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
        Integer resultado = null;
       // try {
            resultado= Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.Count.Afiliados")
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)                    
                    .getSingleResult().toString());
            
        /*
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
        return resultado;
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarEncabezado()
     */
    @Override
    protected List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList();
        String[] encabezado = {"Tipo de identificación de la empresa","Número de identificación","Tipo de identificación afiliado","Número de identificación afiliado", "Primer Nombre","Segundo Nombre","Primer Apellido","Segundo Apellido","Fecha de nacimiento","Sexo","Orientación Sexual","Nivel de escolaridad","Código Ocupación","Factor de Vulnerabilidad","Estado Civil","Pertenencia Étnica","Nombre del país residencia del beneficiario","Código de municipio","Área geográfica de residencia","Salario Básico","Tipo de afiliado","Categoría","Beneficiario de cuota monetaria"};
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */
    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {    	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
        List<String[]> data = null;
       // try {
            data = entityManager.createNamedQuery("Consultar.reporteNormativo.Afiliados")
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)                   
                    .getResultList();
        /*
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        */
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
