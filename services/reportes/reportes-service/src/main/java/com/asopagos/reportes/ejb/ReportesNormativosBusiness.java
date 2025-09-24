package com.asopagos.reportes.ejb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

//import org.jboss.marshalling.ByteOutputStream;
import java.nio.file.Files;
import com.asopagos.archivos.clients.AlmacenarArchivo;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.core.DatosFichaControl;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.reportes.business.interfaces.IConsultasModeloCore;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.dto.ResultadoReporteDTO;
import com.asopagos.reportes.normativos.ReporteNormativoAbstract;
import com.asopagos.reportes.normativos.ReporteNormativoFactory;
import com.asopagos.reportes.normativos.impl.ReporteNormativoAportantesProcesoEnUnidad;
import com.asopagos.reportes.normativos.impl.ReporteNormativoConsolidadoCartera;
import com.asopagos.reportes.normativos.impl.ReporteNormativoDesagregadoCarteraAportante;
import com.asopagos.reportes.normativos.impl.ReporteNormativoUbicacionContacto;
import com.asopagos.reportes.service.ReportesNormativosService;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la generación de
 * reportes normativos
 * <b>Módulo: Asopagos - HU-Reportes Normativos</b>
 *
 * @author
 */
@Stateless
public class ReportesNormativosBusiness implements ReportesNormativosService {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ReportesNormativosBusiness.class);

    /** Inject del EJB para consultas en modelo Core */
    @Inject
    private IConsultasModeloCore consultasCore;

    /** Entity Manager */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    /** Entity Manager */
    @PersistenceContext(unitName = "reportes_PU")
    private EntityManager entityManagerReportes;

    @Override
    public Response exportarReporteNormativo(GeneracionReporteNormativoDTO generacionReporteDTO, FormatoReporteEnum formatoReporte) {

        EntityManager entityManager;
        
        // ajuste fechas comfasucre
        Date fechaFinGeneracionReporteNormativoDTO = generacionReporteDTO.getFechaFin();
        generacionReporteDTO.setFechaFin(obtenerFechaPorParametroAjuste(generacionReporteDTO.getFechaFin()));

        switch (generacionReporteDTO.getReporteNormativo()) {
            case POSTULACIONES_ASIGNACIONES_FOVIS:
            case TRABAJADORES_SECTOR_AGROPECUARIO:
                entityManager = entityManagerCore;
                break;
            default:
                entityManager = entityManagerReportes;
                break;
        }

        ReporteNormativoAbstract reporteNormativo = ReporteNormativoFactory.getReporteNormativo(generacionReporteDTO.getReporteNormativo(),
                entityManager);        
        

        String nombreArchivo = reporteNormativo.generarNombreArchivo(generacionReporteDTO);
        generacionReporteDTO.setNombreArchivo(nombreArchivo);       
        
        
        String nombreFichaControl = null;
        File comprimido = null;

        if (generacionReporteDTO.getReporteOficial()) {        	
        	byte[] dataReporte = reporteNormativo.generarReporte(generacionReporteDTO, formatoReporte);
        	
        	nombreFichaControl = reporteNormativo.generarNombreFichaControl(generacionReporteDTO);
        	byte[] fichaDeControl = reporteNormativo.generarFichaControl(generacionReporteDTO);        
        	
            InformacionArchivoDTO info = new InformacionArchivoDTO();
            info.setProcessName(ProcesoEnum.REPORTES_NORMATIVOS.name());//no tiene relación con un proceso por BPM
            info.setDescription(generacionReporteDTO.getReporteNormativo().getDescripcion());
            info.setDocName(nombreArchivo.concat(formatoReporte.getFormato()));
            info.setFileName(nombreArchivo.concat(formatoReporte.getFormato()));
            
            // sin el reporte no tiene ficha de control en archivos separados
            if(nombreFichaControl == null){ 
	            info.setDataFile(dataReporte);
	            info.setFileType(formatoReporte.getMediaType());
	            
            }else{
            	//Creacion de archivo comprimido con archivo del reporte y ficha de control
            	HashMap<String, byte[]> zip = new HashMap<>(); 
            	zip.put(nombreArchivo+formatoReporte.getFormato(), dataReporte);
            	zip.put(nombreFichaControl,fichaDeControl);   
            	byte[] fileContent = null;
    			try {    				
					comprimido = comprimirZip(1,zip);
					fileContent = Files.readAllBytes(comprimido.toPath());
				} catch (Exception e) {
					logger.error(ConstantesComunes.FIN_LOGGER + "",e);
					e.printStackTrace();
				}
    			
            	info.setDataFile(fileContent);
 	            info.setFileType("application/zip"); 	           
            }
            String idReporteECM = null;

            //try {
                idReporteECM = almacenarArchivo(info).getIdentificadorDocumento();
            /*} catch (Exception e) {
                logger.debug(ConstantesComunes.FIN_LOGGER + " Error al almacenar el archivo del reporte normativo en el ECM");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
                        e + " almacenando el reporte normativo en el ECM");
            }
*/
            GeneracionReporteNormativoDTO reporte = new GeneracionReporteNormativoDTO();
            reporte.setReporteNormativo(generacionReporteDTO.getReporteNormativo());
            reporte.setFechaInicio(generacionReporteDTO.getFechaInicio());
            reporte.setFechaFin(fechaFinGeneracionReporteNormativoDTO);
            reporte.setIdentificadorArchivo(idReporteECM);
            reporte.setNombreReporte(nombreArchivo);
            reporte.setReporteOficial(Boolean.TRUE);
            reporte.setUsuarioGeneracion(generacionReporteDTO.getUsuarioGeneracion());
            //se guarda el reporte normativo
            consultasCore.guardarReporteNormativo(reporte);
            
            // se guardan los datos del formulario ingresados por pantalla para precargarlos la próxima vez que se 
            // exporte un reporte oficial FICHACONTROL
            if (generacionReporteDTO.getReporteOficial() && generacionReporteDTO.getFichaControl() != null){
            	DatosFichaControl datosFichaControl = consultasCore.consultarValoresPrecargadosFichaControlEntidad(generacionReporteDTO.getReporteNormativo());
            	datosFichaControl.setNombreResponsableEnvio(generacionReporteDTO.getFichaControl().getNombreResponsableEnvio());
            	datosFichaControl.setCargoResponsableEnvio(generacionReporteDTO.getFichaControl().getCargoResponsableEnvio());
            	datosFichaControl.setCorreoElectronicoUno(generacionReporteDTO.getFichaControl().getEmailResponsable_1());
            	datosFichaControl.setCorreoElectronicoDos(generacionReporteDTO.getFichaControl().getEmailResponsable_2());           	
            	datosFichaControl.setTelefonoResponsable(generacionReporteDTO.getFichaControl().getTelefono());
            	datosFichaControl.setIndicativoResponsable(generacionReporteDTO.getFichaControl().getIndicativo());
            	datosFichaControl.setNombreReporte(generacionReporteDTO.getReporteNormativo().name());
            	consultasCore.guardarDatosFichaControl(datosFichaControl);
            }
            
        }
      
        Response.ResponseBuilder response = null;
        
        if(nombreFichaControl != null && generacionReporteDTO.getReporteOficial()){    // tiene ficha de control en archivo separado    	
			BufferedInputStream zipFileInputStream;
			try {
				zipFileInputStream = new BufferedInputStream(new FileInputStream(comprimido));
				response = Response.ok(zipFileInputStream);
		        response.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
		        response.header("Content-Disposition", "attachment; filename=zipFile.zip");
			} catch (FileNotFoundException e) {
				 logger.error(ConstantesComunes.FIN_LOGGER + "",e);
			}			
        }else{
        	byte[] dataReporte = reporteNormativo.generarReporte(generacionReporteDTO, formatoReporte);
	        response = Response.ok(new ByteArrayInputStream(dataReporte));
	        response.header("Content-Type", formatoReporte.getMimeType() + ";charset=utf-8");
	        response.header("Content-Disposition", "attachment; filename=" + nombreArchivo);
        }

        return response.build();
    }  


    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesNormativosService#generarResultadosReporte(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO)
     */
    @Override
    public ResultadoReporteDTO generarResultadosReporte(GeneracionReporteNormativoDTO generacionReporteDTO) {
        String firmaServicio = "ReportesNormativosBusiness.generarResultadosReporte(GeneracionReporteNormativoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ResultadoReporteDTO resultado = new ResultadoReporteDTO();

        EntityManager entityManager;

        switch (generacionReporteDTO.getReporteNormativo()) {
	        case POSTULACIONES_ASIGNACIONES_FOVIS:
	        case TRABAJADORES_SECTOR_AGROPECUARIO:
	            entityManager = entityManagerCore;
	            break;
	        default:
	            entityManager = entityManagerReportes;
	            break;
        }

        ReporteNormativoAbstract reporteNormativo = ReporteNormativoFactory.getReporteNormativo(generacionReporteDTO.getReporteNormativo(),
                entityManager);
        
        String nombreReporte = reporteNormativo.generarNombreArchivo(generacionReporteDTO);
        resultado.setNombreReporteGenerado(nombreReporte);
        resultado.setFormatoEntregaReporteNorma(generacionReporteDTO.getReporteNormativo().getFormatoPredeterminado());
        resultado.setPeriodicidadReporte(generacionReporteDTO.getReporteNormativo().getPeriodicidadReporte());

        int numeroReporte;
        try {
        	generacionReporteDTO.setFechaFin(obtenerFechaPorParametroAjuste(generacionReporteDTO.getFechaFin()));     
            numeroReporte = reporteNormativo.pregenerarReporte(generacionReporteDTO.getReporteNormativo(),
                    generacionReporteDTO.getFechaInicio(), generacionReporteDTO.getFechaFin());
        } catch (Exception e) {
            logger.error(
                    ConstantesComunes.FIN_LOGGER + firmaServicio + " Ocurrió un error  en la busqueda del numero de registros del reporte",
                    e);
            numeroReporte = 0;
        }
        resultado.setNumeroRegistros(numeroReporte);
        
        resultado.setFichaControl(obtenerFichaControl(reporteNormativo,generacionReporteDTO.getReporteNormativo(), nombreReporte, numeroReporte,generacionReporteDTO.getFechaFin()));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return resultado;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesNormativosService#verificarReporteParaPeriodo(java.lang.Long,
     *      com.asopagos.enumeraciones.reportes.ReporteNormativoEnum)
     */
    @Override
    public Boolean verificarReportePeriodo(Long fechaInicio, Long fechaFin, ReporteNormativoEnum reporteNormativo) {
        String firmaServicio = "ReportesNormativosBusiness.generarResultadosReporte(GeneracionReporteNormativoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return consultasCore.verificarReporteNormativo(fechaInicio, fechaFin, reporteNormativo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.reportes.service.ReportesNormativosService#consultarHistoricosReportesOficiales(com.asopagos.reportes.dto.GeneracionReporteNormativoDTO,
     *      javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public List<GeneracionReporteNormativoDTO> consultarHistoricosReportesOficiales(GeneracionReporteNormativoDTO generacionReporteDTO,
            UriInfo uri, HttpServletResponse response) {
        Boolean sinFechas = Boolean.FALSE;
        //si alguna o el rango de fechas viene vacia, se consulta por con la fecha del decimo periodo con la fecha de inicio
        //y con la fecha actual.
        if (generacionReporteDTO.getFechaFin() == null || generacionReporteDTO.getFechaInicio() == null) {
            generacionReporteDTO.setFechaInicio(obtenerFechaInicial10Periodos(generacionReporteDTO.getReporteNormativo()));
            LocalDate fechafinal = null;
            switch (generacionReporteDTO.getReporteNormativo()) {
                case REPORTE_MAESTRO_AFILIADO: //SEMANAL
                case REPORTE_ARCHIVO_MAESTRO_SUBSIDIOS:
                case REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS:
                case REPORTE_NOVEDADES_ESTADO_AFILIACION_APORTANTE:               
                    //se obtiene el viernes de fecha de corte
                    fechafinal = LocalDate.now().with(DayOfWeek.FRIDAY);
                    break;
                default:
                    //se obtiene el ultimo día del mes
                    fechafinal = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
                    break;
            }

            generacionReporteDTO.setFechaFin(Date.from(fechafinal.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            sinFechas = Boolean.TRUE;
        }

        return consultasCore.consultarHistoricosReportesOficiales(generacionReporteDTO, uri, response, sinFechas);
    }

    /**
     * Metodo encargado de llamar el cliente AlmacenarArchivo para guardar la información del archivo en el ECM
     * @param infoFile
     *        <code>InformacionArchivoDTO</code>
     *        DTO con la información inicial del archivo
     * @return información del archivo con su numero de identificación y versión
     */
    private InformacionArchivoDTO almacenarArchivo(InformacionArchivoDTO infoFile) {
        logger.debug("Inicia almacenarArchivo(InformacionArchivoDTO infoFile)");
        AlmacenarArchivo archivo = new AlmacenarArchivo(infoFile);
        archivo.execute();
        return archivo.getResult();
    }

    private Date obtenerFechaInicial10Periodos(ReporteNormativoEnum reporteNormativoEnum) {
        LocalDate fechaInicio = LocalDate.now();

        switch (reporteNormativoEnum) {
            case EMPRESAS_Y_APORTANTES: //mensual
            case AFILIADOS:
            case AFILIADOS_A_CARGO:
            case EMPRESAS_EN_MORA:
            case ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS:
            case REPORTE_CONSOLIDADO_CARTERA:
            case REPORTE_DESAGREGADO_CARTERA:
            case AVISO_INCUMPLIMIENTO:
            case REPORTE_MENSUAL_AFILIADOS:
            case REPORTE_MENSUAL_ASIGNADOS:
            case REPORTE_DEVOLUCIONES:
            case REPORTE_PAGO_POR_FUERA_DE_PILA:
            case REPORTE_INCONSISTENCIAS:
                fechaInicio = fechaInicio.minus(10, ChronoUnit.MONTHS);
                break;

            case REPORTE_MAESTRO_AFILIADO: //SEMANAL
            case REPORTE_ARCHIVO_MAESTRO_SUBSIDIOS:
            case REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS:
            case REPORTE_NOVEDADES_ESTADO_AFILIACION_APORTANTE:
                fechaInicio = fechaInicio.minus(11, ChronoUnit.WEEKS);
                break;
            case POSTULACIONES_ASIGNACIONES_FOVIS: //ANUAL
            case REPORTE_UBICACION_CONTACTO:
                fechaInicio = fechaInicio.minus(10, ChronoUnit.YEARS);
                break;
            case TRABAJADORES_SECTOR_AGROPECUARIO: //semestral
            case REPORTE_REGISTRO_UNICO_EMPLEADORES:
                fechaInicio = fechaInicio.minus(5, ChronoUnit.YEARS);
                break;
            default: //TRIMESTRAL
                //serían 8 trimestres (2 años)
                fechaInicio = fechaInicio.minus(2, ChronoUnit.YEARS);
                fechaInicio = fechaInicio.minus(2, ChronoUnit.MONTHS);
                break;
        }

        switch (reporteNormativoEnum) {
            case REPORTE_MAESTRO_AFILIADO: //SEMANAL
            case REPORTE_ARCHIVO_MAESTRO_SUBSIDIOS:
            case REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS:
            case REPORTE_NOVEDADES_ESTADO_AFILIACION_APORTANTE:
                //Si es semanal el periodo inicia el sabado
                fechaInicio = fechaInicio.with(DayOfWeek.SATURDAY);
                break;
            default:
                //se pone el primer dia del mes de ese periodo
                fechaInicio = fechaInicio.withDayOfMonth(1);
                break;
        }

        return Date.from(fechaInicio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    
    private static File comprimirZip(int compression,HashMap<String, byte[]> data) throws Exception {
        File tempZipFile =
            File.createTempFile("test-data" + compression, ".zip");
        tempZipFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempZipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.setLevel(compression);            
            Iterator it = data.entrySet().iterator();
            
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String text = (String) pair.getKey();
                ZipEntry entry = new ZipEntry(text);
                zos.putNextEntry(entry);
                try {
                    zos.write((byte[])pair.getValue());
                } finally {
                    zos.closeEntry();
                }                
                it.remove(); // avoids a ConcurrentModificationException
            }          
        }
        return tempZipFile;
    }
    
    private FichaControlDTO obtenerFichaControl(ReporteNormativoAbstract reporte,ReporteNormativoEnum reporteNormativoEnum, String nombreReporte, 
    											Integer numeroReporte, Date fechaFin) {      	 
    	FichaControlDTO fichaControl = new FichaControlDTO();
    
    	 if(//reporteNormativoEnum == ReporteNormativoEnum.APORTANTES_EN_PROCESO_UNIDAD || 
    		reporteNormativoEnum == ReporteNormativoEnum.SEGUIMINETOS_TRASLADOS_MORA ||
    		reporteNormativoEnum == ReporteNormativoEnum.REPORTE_DESAGREGADO_CARTERA ||
    		reporteNormativoEnum == ReporteNormativoEnum.REPORTE_UBICACION_CONTACTO ||
    		reporteNormativoEnum == ReporteNormativoEnum.REPORTE_CONSOLIDADO_CARTERA ||
    		reporteNormativoEnum == ReporteNormativoEnum.REPORTE_INCONSISTENCIAS){
    		 
    		fichaControl.setCodigoAdministradora((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
    		//fichaControl.setNombreAdministrador((String) CacheManager.getConstante(ParametrosSistemaConstants.NOMBRE_CCF)); 
    		fichaControl.setNombreAdministrador((String) CacheManager.getParametro(ParametrosSistemaConstants.NOMBRE_CCF));
    		 
    		
 	    	fichaControl.setNombreArchivo(nombreReporte);
 	    	fichaControl.setCantidadRegistros(numeroReporte);
 	    	fichaControl.setFechaCorteReporte(fechaFin.getTime()); 	    	
 	    	
 	    	// DATOS PRECARGADOS 	    	
 	    	FichaControlDTO datosPrecargados = consultasCore.consultarValoresPrecargadosFichaControl(reporteNormativoEnum);
 	    	fichaControl.setNombreResponsableEnvio(datosPrecargados.getNombreResponsableEnvio());
 	    	fichaControl.setCargoResponsableEnvio(datosPrecargados.getCargoResponsableEnvio());
 	    	fichaControl.setEmailResponsable_1(datosPrecargados.getEmailResponsable_1());
 	    	fichaControl.setEmailResponsable_2(datosPrecargados.getEmailResponsable_2());
 	    	fichaControl.setTelefono(datosPrecargados.getTelefono());
 	    	fichaControl.setIndicativo(datosPrecargados.getIndicativo());
 	    	
 	    	/*if(reporteNormativoEnum == ReporteNormativoEnum.APORTANTES_EN_PROCESO_UNIDAD ){
 	    		fichaControl.setValorCarteraReportada(((ReporteNormativoAportantesProcesoEnUnidad)reporte).obtenerValorTotalDeuda(fechaFin));
 	    	}	  
 	    	*/
 	    	if(reporteNormativoEnum == ReporteNormativoEnum.REPORTE_DESAGREGADO_CARTERA ){
 	    		fichaControl.setValorCarteraReportada(((ReporteNormativoDesagregadoCarteraAportante)reporte).obtenerValorTotalDeuda(fechaFin));
 	    	}
 	    	
 	    	if(reporteNormativoEnum == ReporteNormativoEnum.REPORTE_CONSOLIDADO_CARTERA ){
 	    		fichaControl.setValorCarteraReportada(((ReporteNormativoConsolidadoCartera)reporte).obtenerValorTotalDeuda(fechaFin));
 	    	} 	  
 	    	
 	    	if(reporteNormativoEnum == ReporteNormativoEnum.REPORTE_UBICACION_CONTACTO ){
 	    		fichaControl.setValorCarteraReportada(((ReporteNormativoUbicacionContacto)reporte).obtenerValorTotalDeuda(fechaFin));
 	    	} 	
    	 }    	
	    return fichaControl;
    }
    
    private Date obtenerFechaPorParametroAjuste(Date fecha){
    	String mesAjuste = consultasCore.consultarParametro(ParametrosSistemaConstants.AJUSTE_MES_REPORTE_NORMATIVO)==null?"0"
				:consultasCore.consultarParametro(ParametrosSistemaConstants.AJUSTE_MES_REPORTE_NORMATIVO);
		Calendar c = Calendar.getInstance(); 
		c.setTime(fecha);
		c.add(Calendar.MONTH, Integer.parseInt(mesAjuste));
		return c.getTime();
    }
}
