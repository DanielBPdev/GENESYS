package com.asopagos.reportes.normativos.impl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción: </b> Clase encargada de la generación del reporte Desagregado
 * de Cartera por Aportante solicitado por la Unidad de Gestión Pensional y
 * Parafiscales con los parámetros establecidos por dicha entidad en la
 * Resolución 2082 de 2016. <br/>
 * <b>Historia de Usuario: </b> Reportes Normativos
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class ReporteNormativoDesagregadoCarteraAportante extends ReporteNormativoAbstract {

	/**
	 * Entity manager
	 */
	private final EntityManager entityManager;
	
	/**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReporteNormativoDesagregadoCarteraAportante.class);

	/**
	 * Constructor
	 * 
	 * @param entityManager
	 *            Entity manager
	 */
	public ReporteNormativoDesagregadoCarteraAportante(EntityManager entityManager) {
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
			logger.debug("Pregenerando ReporteNormativoDesagregadoCarteraAportante...");
			Integer resultado = (int)entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESAGREGADO_CARTERA_APORTANTE_COUNT)
					.setParameter("fecha", fechaFin)
					.getSingleResult();					
					
			logger.debug("ReporteNormativoDesagregadoCarteraAportante pregenerado");
			return resultado;
		} catch (Exception e) {
			logger.error("Error pregenerando ReporteNormativoDesagregadoCarteraAportante", e);
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
			logger.debug("Generando datos ReporteNormativoDesagregadoCarteraAportante...");
			List<String[]> data = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESAGREGADO_CARTERA_APORTANTE)
					.setParameter("fecha", fechaFin)
					.getResultList();
			logger.debug("ReporteNormativoDesagregadoCarteraAportante datos generados");
			return data;
		} catch (Exception e) {
			logger.error("Error generando datos ReporteNormativoDesagregadoCarteraAportante", e);
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

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date, java.util.Map)
     */
	@Override
	public String generarNombreFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		String resultado = "";
		String separador = "_";		
		String nombreFicha= "FICHA_DE_CONTROL";	        
	    String codigoPILAadmin = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);	 
	    
	    LocalDate fechaGeneracionReporte = generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    fechaGeneracionReporte = fechaGeneracionReporte.plusMonths(1);
	    
	    String anioGeneracionReporte = String.valueOf(fechaGeneracionReporte.getYear());
	    String mesGeneracionReporte = String.valueOf((fechaGeneracionReporte.getMonthValue()<10)?"0"+fechaGeneracionReporte.getMonthValue():fechaGeneracionReporte.getMonthValue());
	    
	    String extencion = "desagregado.txt";
	    resultado = codigoPILAadmin + separador 
	    		+ nombreFicha + separador 
	    		+ anioGeneracionReporte + separador 
	    		+ mesGeneracionReporte + separador 
	    		+ extencion;
	   
	    return resultado;
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
				formatter.format(fechaCortePublicacion) + separador +
				ficha.getMetodologiaCalculoDeudaPresunta();
		
		return resultado.getBytes(StandardCharsets.UTF_8);
	}
	
    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#pregenerarReporte(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */    
    public String obtenerValorTotalDeuda(Date fechaFin) {        
    	BigInteger suma = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DESAGREGADO_CARTERA_APORTANTE_V_TOTAL)
                    .setParameter("fecha", fechaFin, TemporalType.DATE)                    
                    .getSingleResult();       
        return (suma == null) ? "" : suma.toString();
    }
}
