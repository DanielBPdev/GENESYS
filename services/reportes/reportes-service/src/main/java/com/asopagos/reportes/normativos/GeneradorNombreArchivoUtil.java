package com.asopagos.reportes.normativos;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;

/**
 * @author mosorio
 */
public class GeneradorNombreArchivoUtil {
    
	private final static String SEPARADOR = "_";
	
    public static String generarNombreArchivoCircular020(GeneracionReporteNormativoDTO generacionReporteDTO) {
        //Revisar las HU para construir las reglas para la nomenclatura de archivos
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy");    	
        String codigoCaja = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        String codigoArchivo = (generacionReporteDTO.getReporteNormativo().getCodigo()!=null)?generacionReporteDTO.getReporteNormativo().getCodigo():"";
        String codigoPeriodo = obtenerCodigoPeriodo(generacionReporteDTO.getReporteOficial(), generacionReporteDTO.getFechaInicio(),
                generacionReporteDTO.getFechaFin(),generacionReporteDTO.getReporteNormativo());

        return codigoCaja.concat(codigoArchivo).concat(codigoPeriodo).concat(String.valueOf(df.format(generacionReporteDTO.getFechaFin())));
    }
    
    public static String generarNombreArchivoSeguimientoTrasladosPorCompetencia(GeneracionReporteNormativoDTO generacionReporteDTO) {
        //Revisar las HU para construir las reglas para la nomenclatura de archivos
    	String nombreReporte = "SEGUIMIENTO_TRASLADOS_";
    	String extension = "RTA.txt";
    	SimpleDateFormat df = new SimpleDateFormat("yyyy");    	
        String codigoCaja = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        //String codigoArchivo = (generacionReporteDTO.getReporteNormativo().getCodigo()!=null)?generacionReporteDTO.getReporteNormativo().getCodigo():"";
        String codigoPeriodo = obtenerCodigoPeriodo(generacionReporteDTO.getReporteOficial(), generacionReporteDTO.getFechaInicio(),
                generacionReporteDTO.getFechaFin(),generacionReporteDTO.getReporteNormativo());

        return nombreReporte
        		.concat(codigoCaja).concat(SEPARADOR)
        		.concat(String.valueOf(df.format(generacionReporteDTO.getFechaFin())))
        		.concat(codigoPeriodo).concat(SEPARADOR)        		
        		.concat(extension);
    }
    
    public static String generarNombreArchivoResolucion74YUgpp(GeneracionReporteNormativoDTO generacionReporteDTO){
        StringBuilder salida = new StringBuilder(generacionReporteDTO.getReporteNormativo().getDescripcion());
        
        salida.append((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
        salida.append(obtenerCodigoPeriodo(generacionReporteDTO.getReporteOficial(), generacionReporteDTO.getFechaInicio(),
                generacionReporteDTO.getFechaFin(), generacionReporteDTO.getReporteNormativo()));
        
        return salida.toString();
    }
    
    public static String generarNombreArchivoTrabajadoresAgropecuarios(GeneracionReporteNormativoDTO generacionReporteDTO){
        String salida = null;
        LocalDate fechaSolicitudLD = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        salida = formatter.format(fechaSolicitudLD) + SEPARADOR + generacionReporteDTO.getReporteNormativo().name();
        
        return salida;
    }
    
    public static String generarNombreArchivoInconsistenciasDecreto3033(GeneracionReporteNormativoDTO generacionReporteDTO){
        String codigoCCF = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        LocalDate fechaSolicitudLD = generacionReporteDTO.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM");
        String reporte = generacionReporteDTO.getReporteNormativo().getNorma().replaceAll(" ", SEPARADOR).toUpperCase();
        return "CCF_" + codigoCCF + SEPARADOR + formatter.format(fechaSolicitudLD) + SEPARADOR + reporte;
    }
    
    /**
     * Metodo que permite obtener el código del periodo según las fechas ingresadas por pantalla.
     * @param reporteOficial
     *        <code>Boolean</code>
     *        True si es un reporte oficial y False o null si no lo es
     * @param fechaInicial
     *        <code>Date</code>
     *        Fecha inicial del rango
     * @param fechaFinal
     *        <code>Date</code>
     *        Fecha final del rango
     * @param periodo
     *        <code>Date</code>
     *        periodo del reporte oficial
     * @return Código del periodo según las fechas si es oficial o no.
     */
    private static String obtenerCodigoPeriodo(Boolean reporteOficial, Date fechaInicial, Date fechaFinal,ReporteNormativoEnum reporteNormativo) {

        String codigo = "";

        if ( (reporteOficial || (fechaFinal == null)) 
                && !reporteNormativo.equals(ReporteNormativoEnum.POSTULACIONES_ASIGNACIONES_FOVIS)) {
            LocalDate fechaPeriodo = fechaInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            codigo = obtenerCodigoPeriodoDeUnMes(fechaPeriodo);
        }
        else {
            LocalDate periodoIni = fechaInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate periodoFin = fechaFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Period period = Period.between(periodoIni, periodoFin);
            //si es de un mes
            if (period.getMonths() == 0 && period.getDays() <= 31) {

                codigo = obtenerCodigoPeriodoDeUnMes(periodoFin);

            }
            //trimestres
            else if (period.getMonths() == 2) {
                if (periodoFin.getMonth() == Month.MARCH && periodoFin.getDayOfMonth() <= 30) {
                    //trimestre 1
                    codigo = String.valueOf(20);
                }
                else if (periodoFin.getMonth() == Month.JUNE && periodoFin.getDayOfMonth() <= 30) {
                    //trimestre 2
                    codigo = String.valueOf(21);
                }
                else if (periodoFin.getMonth() == Month.SEPTEMBER && periodoFin.getDayOfMonth() <= 30) {
                    //trimestre 3
                    codigo = String.valueOf(22);
                }
                else if (periodoFin.getMonth() == Month.DECEMBER && periodoFin.getDayOfMonth() <= 31) {
                    //trimestre 4
                    codigo = String.valueOf(23);
                }
            }
            //semestres
            else if (period.getMonths() == 6) {

                if (periodoFin.getMonth() == Month.JUNE && periodoFin.getDayOfMonth() <= 30) {
                    //semestre 1
                    codigo = String.valueOf(30);
                }
                else if (periodoFin.getMonth() == Month.DECEMBER && periodoFin.getDayOfMonth() <= 31) {
                    //semestre 2
                    codigo = String.valueOf(31);
                }
            }
            //anual
            else if ((period.getYears() == 1 || (period.getMonths() >= 11 && period.getDays() >= 30))&& periodoFin.getMonth() == Month.DECEMBER && periodoFin.getDayOfMonth() <= 31) {
                codigo = String.valueOf(50);
            }
        }
        return codigo;
    }

    /**
     * Metodo que obtiene el código del reporte segun el mes.
     * @param fecha
     *        <code>LocalDate</code>
     * @return código del reporte dependiendo del mes
     */
    private static String obtenerCodigoPeriodoDeUnMes(LocalDate fecha) {
        String codigo;
        switch (fecha.getMonth()) {
            case JANUARY:
                codigo = "01";
                break;
            case FEBRUARY:
                codigo = "02";
                break;
            case MARCH:
                codigo = "03";
                break;
            case APRIL:
                codigo = "04";
                break;
            case MAY:
                codigo = "05";
                break;
            case JUNE:
                codigo = "06";
                break;
            case JULY:
                codigo = "07";
                break;
            case AUGUST:
                codigo = "08";
                break;
            case SEPTEMBER:
                codigo = "09";
                break;
            case OCTOBER:
                codigo = String.valueOf(10);
                break;
            case NOVEMBER:
                codigo = String.valueOf(11);
                break;
            default: //December
                codigo = String.valueOf(12);
                break;
        }
        return codigo;
    }
    
    public static String generarNombreArchivoResolucion2082(GeneracionReporteNormativoDTO generacionReporteDTO){
        String subSistema= "CCF";
        LocalDate fechaReporte =  generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String codigoPILAadmin = SEPARADOR.concat((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
        String nombre = "";
        switch (generacionReporteDTO.getReporteNormativo()) {
            case AVISO_INCUMPLIMIENTO:
                nombre = "incumplimiento";
                break;
            case REPORTE_CONSOLIDADO_CARTERA:
                nombre = "consolidado";
                break;
            case REPORTE_DESAGREGADO_CARTERA:
            	fechaReporte = fechaReporte.plusMonths(1);
                nombre = "desagregado";
                break;
            case REPORTE_UBICACION_CONTACTO:
                nombre = "contacto";
                break;
            default:
                break;
        }
        String mesReporte = SEPARADOR.concat(String.valueOf((fechaReporte.getMonthValue()<10)?"0"+fechaReporte.getMonthValue():fechaReporte.getMonthValue()));
        String anioReporte = SEPARADOR.concat(String.valueOf(fechaReporte.getYear()));
        String fecha = anioReporte.concat(mesReporte);
        String nombreReporte = SEPARADOR.concat(nombre);
        return subSistema.concat(codigoPILAadmin).concat(fecha).concat(nombreReporte);
    }
    
    public static String generarNombreArchivoUGPP(GeneracionReporteNormativoDTO generacionReporteDTO){
        String subSistema= "EPS";
        LocalDate fechaReporte =  generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String codigoPILAadmin = SEPARADOR.concat((String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO));
        String mesReporte = SEPARADOR.concat(String.valueOf((fechaReporte.getMonthValue()<10)?"0"+fechaReporte.getMonthValue():fechaReporte.getMonthValue()));
        String anioReporte = SEPARADOR.concat(String.valueOf(fechaReporte.getYear()));
        String fecha = anioReporte.concat(mesReporte);
        String nombre;
        
        switch (generacionReporteDTO.getReporteNormativo()) {
            case APORTANTES_EN_PROCESO_UNIDAD:
                nombre = "";
                break;           
            default:
            	nombre = "aportantes";
                break;
        }
        
        String nombreReporte = SEPARADOR.concat(nombre);
        return subSistema.concat(codigoPILAadmin).concat(fecha).concat(nombreReporte);
    }

    public static String generarNombreArchivoUbicacionContacto(GeneracionReporteNormativoDTO generacionReporteDTO){
        String subSistema= "CCF";
        String nombre = "UBICACION_Y_CONTACTO";
        LocalDate fechaReporte = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return subSistema.concat(formatter.format(fechaReporte)).concat(SEPARADOR).concat(nombre);
    }

    public static String generarNombreArchivoResolucion1056(GeneracionReporteNormativoDTO generacionReporteDTO){
        StringBuilder salida = new StringBuilder("RUA250");
        String separadorFecha = "";
        String prefijoCodigo = "CO";
        String extension = ".txt";
        switch (generacionReporteDTO.getReporteNormativo()) {
            case REPORTE_MAESTRO_AFILIADO:
                //salida.append("CMCP");
            	salida.append("PMPP");               
                break;
            case REPORTE_ARCHIVO_MAESTRO_SUBSIDIOS:
                salida.append("CMSP");
                break;

            case REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS:
                salida.append("CNCA");
                break;

            case REPORTE_NOVEDADES_ESTADO_AFILIACION_APORTANTE:
                //salida.append("CNCE");
            	salida.append("PNPE");
                break;

            default:
                break;
        }
        Date fechaFin = generacionReporteDTO.getFechaFin();
        //LocalDate fechaCorte = obtenerViernesSemana(LocalDate.now());
        LocalDate fechaCorte = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String mesCorte = String.valueOf((fechaCorte.getMonthValue()<10)?"0"+fechaCorte.getMonthValue():fechaCorte.getMonthValue());
        String diaCorte = String.valueOf((fechaCorte.getDayOfMonth()<10)?"0"+fechaCorte.getDayOfMonth():fechaCorte.getDayOfMonth());
        salida.append(fechaCorte.getYear()+separadorFecha+mesCorte+separadorFecha+diaCorte);
        salida.append("NI");
        String numeroId = (String) CacheManager.getParametro(ParametrosSistemaConstants.NUMERO_ID_CCF);
        String [] aux = numeroId.split("-");
        String numero = aux.length != 1 ? aux[0].concat(aux[1]):aux[0];
        //int sizeNumeroId = (12-numero.length())< 0 ? 0 : (12-numero.length());
        //salida.append(String.format("%0"+(sizeNumeroId)+"d", Long.valueOf(numero)));
        salida.append(StringUtils.leftPad(numero, 12, "0"));
        salida.append(prefijoCodigo);
        String codigoCaja =(String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO); 
        salida.append(StringUtils.leftPad(codigoCaja, 6, "0"));
        salida.append(extension);
    
        return salida.toString();
    }
    
    private static LocalDate obtenerViernesSemana(LocalDate date) {
        return date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    }
    
    
    public static String generarNombreArchivoOtrosReportes(GeneracionReporteNormativoDTO generacionReporteDTO){
        String nombreArchivo = "";
        switch (generacionReporteDTO.getReporteNormativo()) {
            case REPORTE_MENSUAL_AFILIADOS:
                nombreArchivo = "AFILIADOS";
                break;
            case REPORTE_MENSUAL_ASIGNADOS:
                nombreArchivo = "ASIGNADOS";
                break;
            default:
                break;
        }
        String codigoCaja = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        LocalDate periodoFin = generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Month mes = periodoFin.getMonth();
        Integer anio=periodoFin.getYear();
        String nombreMes = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return nombreArchivo + codigoCaja + nombreMes.toUpperCase() + anio.toString();
    }
    
}
