package com.asopagos.reportes.normativos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
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
 * <b>Código en la norma:</b> 5-433B <br>
 * <b>Descripción del reporte:</b> Reportar la información de Asignación, entrega y reintegro de subsidios de viviendas Microdato FOVIS.<br>
 * <b>Norma asociada al reporte:</b> Circular Externa 00020 de Diciembre  de 2016 (Inciso 5-433B)
 * @author Fabian Hernando López Higuera <flopez@heinsohn.com.co>
 */
public class ReporteNormativoAsignacionEntregaReintegroMiDatoSubsidioFOVIS extends ReporteNormativoAbstract {

    private final EntityManager entityManager;

    public ReporteNormativoAsignacionEntregaReintegroMiDatoSubsidioFOVIS(EntityManager entityManager) {
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
              resultado= Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.count.AsignacionPagoReintegroMicroDatoFOVIS")
                      .setParameter("fechaFin", fechaFin, TemporalType.DATE)                    
                      .getSingleResult().toString());              
         
          } catch (Exception e) {
              throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
          }          
          return resultado;
    }

    @Override
    protected List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList<>();
        String[] encabezado = { "Tipo de Identificación","Número de Identificación", "Componente del hogar objeto", "Tipo de Identificación de los integrantes del hogar",
                "Número de Identificación de los integrantes del hogar", "Afiliado a Caja de Compensación", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido",
        		"Parentesco de la persona con el titular", "Ingreso del integrante del hogar","Nivel de ingreso", "Componente", "Año de asignación", "Estado del subsidio", "Valor del subsidio" };
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {    	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
    	List<String[]> data = null;
          try {
              data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HISTORICO_CONSOLIDADO_ASIGNACIONES_PAGOS_MICRODATO_FOVIS)
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
