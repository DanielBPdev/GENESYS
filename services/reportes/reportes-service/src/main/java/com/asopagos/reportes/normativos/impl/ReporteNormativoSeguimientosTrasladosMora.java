package com.asopagos.reportes.normativos.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * @author  
 */

public class ReporteNormativoSeguimientosTrasladosMora extends ReporteNormativoAbstract{

    private final EntityManager entityManager;
    
    public ReporteNormativoSeguimientosTrasladosMora(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarNombreArchivo(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoSeguimientoTrasladosPorCompetencia(generacionReporteDTO);
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#pregenerarReporte(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */
    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
        Integer resultado = null;      
            resultado= Integer.parseInt(entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_COUNT)
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)                    
                    .getSingleResult().toString());     
        return resultado;
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarEncabezado()
     */
    @Override
    protected List<String[]> generarEncabezado() {
        List<String[]> lstEncabezado = new ArrayList();
        String[] encabezado = null;
        lstEncabezado.add(encabezado);
        return lstEncabezado;
    }

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
    	List<String[]> data = null;  
    	
    	if(generacionReporteDTO.getPeriodosDesagregado() == null){
    		data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA)
                      .setParameter("fechaFin", fechaFin, TemporalType.DATE)                      
                      .getResultList();      
    		
    	}else{
    		int actualizaciones = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_UPDATE)
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                    .setParameter("perDesargegados", generacionReporteDTO.getPeriodosDesagregado())
                    .executeUpdate();
    		
    		data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_OFICIAL)
                    .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                    .setParameter("perDesargegados", generacionReporteDTO.getPeriodosDesagregado())
                    .getResultList();      
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
    
    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date, java.util.Map)
     */
	@Override
	public String generarNombreFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		LocalDate fechaReporte =  generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		String separador = "";	
		
		String nombreFicha= "FICHA_DE_CONTROL";
	    String subSistema= "CCF";	    
	    String codigoPILAadmin = separador.concat((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));	    
	    String anioReporte = separador.concat(String.valueOf(fechaReporte.getYear()));
	    String mesReporte = separador.concat(String.valueOf((fechaReporte.getMonthValue()<10)?"0"+fechaReporte.getMonthValue():fechaReporte.getMonthValue()));	  
	    String ultimosCaracteres = "ACUERDO_1035";
	    LocalDate fechaGeneracionReporte = LocalDate.now();
	    String anioGeneracionReporte = String.valueOf(fechaGeneracionReporte.getYear());
	    String mesGeneracionReporte = String.valueOf(fechaGeneracionReporte.getMonthValue());	    
	    String extencion = ".txt";
        return nombreFicha + subSistema + codigoPILAadmin + anioReporte + mesReporte + ultimosCaracteres + anioGeneracionReporte + mesGeneracionReporte + extencion;	
	}
	
	 /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date, java.util.Map)
     */
	@Override
	public byte[] generarFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		// TODO Auto-generated method stub
		String separador = "|";
		FichaControlDTO ficha = generacionReporteDTO.getFichaControl();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		LocalDate fechaCorteReporte =  new Date(ficha.getFechaCorteReporte()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaCortePublicacion =  new Date(ficha.getFechaPublicacionReporte()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		String resultado = ficha.getCodigoAdministradora() + separador +
				ficha.getNombreAdministrador() + separador +
				ficha.getNombreResponsableEnvio() + separador +
				ficha.getCargoResponsableEnvio() + separador +
				ficha.getEmailResponsable_1() + (ficha.getEmailResponsable_2() != null?(";" + ficha.getEmailResponsable_2() + separador):separador)+ 
				ficha.getTelefono() + separador +
				ficha.getIndicativo() + separador +
				ficha.getNombreArchivo() + separador +
				ficha.getCantidadRegistros() + separador +
				ficha.getValorCarteraReportada() + separador +
				formatter.format(fechaCorteReporte) + separador +
				formatter.format(fechaCortePublicacion);
		
		return resultado.getBytes(StandardCharsets.UTF_8);
	}

}
