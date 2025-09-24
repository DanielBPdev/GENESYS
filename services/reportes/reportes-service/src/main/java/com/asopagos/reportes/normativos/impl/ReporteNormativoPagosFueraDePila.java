/**
 * 
 */
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
 * <b>Descripcion:</b> Clase que implementa el Reporte Pagos fuera de PILA UGPP<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class ReporteNormativoPagosFueraDePila extends ReporteNormativoAbstract {

    private final EntityManager entityManager;

    public ReporteNormativoPagosFueraDePila(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarNombreArchivo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoResolucion74YUgpp(generacionReporteDTO);
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
            resultado = Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.Count.PagosFueraDePilaUgpp")
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
        List<String[]> lstEncabezado = new ArrayList<>();

        
        String[] segundaLinea = { "TIPO DOCUMENTO", "NÚMERO DE IDENTIFICACIÓN", "RAZON SOCIAL", "ULTIMA DIRECCIÓN INFORMADA",
                "NOMBRE MUNICIPIO", "NOMBRE DEPARTAMENTO", "AÑO COTIZACION", "MES COTIZACION", "SUBSISTEMA  AL CUAL CORRESPONDE EL PAGO",
                "DIAS COTIZADOS", "IBC", "CÉDULA TRABAJADOR", "NOMBRE TRABAJADOR", "NOVEDAD", "NO. PLANILLA", "FECHA PAGO", "VALOR PAGADO",
                "ADMINISTRADORA DEL SISTEMA DE APORTES PARAFISCALES", "COD ADMINISTRADORA DEL SISTEMA DE APORTES PARAFISCALES" };

       
        lstEncabezado.add(segundaLinea);

        return lstEncabezado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum,
     *      java.util.Date, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
        List<String[]> data = null;
        try {
            data = entityManager.createNamedQuery("Consultar.reporteNormativo.PagosFueraDePilaUgpp")
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
