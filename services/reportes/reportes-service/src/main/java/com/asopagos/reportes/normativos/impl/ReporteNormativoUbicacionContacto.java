package com.asopagos.reportes.normativos.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.asopagos.reportes.constants.NamedQueriesConstants;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.GeneradorNombreArchivoUtil;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase encargada del procesamiento del reporte de ubicación y contacto <br>
 * <b>Código en la norma:</b> 1.2 <br>
 * <b>Descripción del reporte:</b> Datos de ubicación y contacto <br>
 * <b>Norma asociada al reporte:</b> Resolución 2082 de 2016<br>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ReporteNormativoUbicacionContacto extends ReporteNormativoAbstract {

    private final EntityManager entityManager;

    public ReporteNormativoUbicacionContacto(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO) {
        return GeneradorNombreArchivoUtil.generarNombreArchivoUbicacionContacto(generacionReporteDTO);
    }

    @Override
    public Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin) {
    	  Integer resultado = null;
          try {
              resultado = Integer.parseInt(entityManager.createNamedQuery("Consultar.reporteNormativo.Count.UbicacionContacto")
                      .setParameter("fechaFin", fechaFin, TemporalType.DATE)
                      .getSingleResult().toString());

          } catch (Exception e) {
              throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
          }

          return resultado;
    }

    @Override
    protected List<String[]> generarEncabezado() {
        return null;
    }

    @Override
    protected List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO) {  	
    	Date fechaFin = generacionReporteDTO.getFechaFin();
    	 List<String[]> data = null;
         try {
             data = entityManager.createNamedQuery("Consultar.reporteNormativo.UbicacionContacto")                    
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

    /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#generarData(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date, java.util.Map)
     */
	@Override
	public String generarNombreFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO) {
		String resultado = "";
		String separador = "_";		
		String nombreFicha= "FICHA_DE_CONTROL";	        
	    String codigoPILAadmin = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);	   	   
	    LocalDate fechaGeneracionReporte = LocalDate.now();
	    String anioGeneracionReporte = String.valueOf(fechaGeneracionReporte.getYear());
	    String mesGeneracionReporte = String.valueOf(fechaGeneracionReporte.getMonthValue());	    
	    String extencion = "consolidado.txt";
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
				formatter.format(fechaCortePublicacion);
				//ficha.getMetodologiaCalculoDeudaPresunta();
		
		return resultado.getBytes(StandardCharsets.UTF_8);
	}
	
	 /** (non-Javadoc)
     * @see com.asopagos.reportes.normativos.ReporteNormativoAbstract#pregenerarReporte(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum, java.util.Date, java.util.Date)
     */    
    public String obtenerValorTotalDeuda(Date fechaFin) {        
        String resultadoS = "";// vacio - correo 11/06/2019       
        return resultadoS;
    }

}
