package com.asopagos.reportes.normativos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.normativos.impl.ReporteNormativoNovedadesEstadoAfiliacion;

/**
 * Clase abstracta que define el comportamiento general para la generación de
 * reportes normativos
 * 
 * @author sbrinez
 */
public abstract class ReporteNormativoAbstract {

    public abstract String generarNombreArchivo(GeneracionReporteNormativoDTO generacionReporteDTO);
    
    public abstract String generarNombreFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO);
    
    public abstract byte[] generarFichaControl(GeneracionReporteNormativoDTO generacionReporteDTO);

    public abstract Integer pregenerarReporte(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin);

    public final byte[] generarReporte(GeneracionReporteNormativoDTO generacionReporteDTO, FormatoReporteEnum formatoReporte) {

        ReporteNormativoEnum reporteNormativo = generacionReporteDTO.getReporteNormativo();
        List<String[]> encabezado = null;
        List<String[]> data;

        String mesFinal, diaFinal;

        if (reporteNormativo.getEncabezado()) {
            encabezado = generarEncabezado();
        }
        
       
        //data = generarData(reporteNormativo, generacionReporteDTO.getFechaInicio(), generacionReporteDTO.getFechaFin());
        
        data = generarData(generacionReporteDTO);
        

        LocalDate fechaInicial = generacionReporteDTO.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinal = generacionReporteDTO.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String separador = "-";
        switch (generacionReporteDTO.getReporteNormativo()) {
            case REPORTE_ARCHIVO_MAESTRO_SUBSIDIOS:
            case REPORTE_MAESTRO_AFILIADO:
            case REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS:
                String mesInicial = String
                        .valueOf((fechaInicial.getMonthValue() < 9) ? "0" + fechaInicial.getMonthValue() : fechaInicial.getMonthValue());
                String diaInicial = String
                        .valueOf((fechaInicial.getDayOfMonth() < 9) ? "0" + fechaInicial.getDayOfMonth() : fechaInicial.getDayOfMonth());
                mesFinal = String.valueOf((fechaFinal.getMonthValue() < 9) ? "0" + fechaFinal.getMonthValue() : fechaFinal.getMonthValue());
                diaFinal = String.valueOf((fechaFinal.getDayOfMonth() < 9) ? "0" + fechaFinal.getDayOfMonth() : fechaFinal.getDayOfMonth());
                String[] datosControl1 = { "1", (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO),
                        fechaInicial.getYear() + separador + mesInicial + separador + diaInicial,
                        fechaFinal.getYear() + separador + mesFinal + separador + diaFinal,
                        pregenerarReporte(generacionReporteDTO.getReporteNormativo(), generacionReporteDTO.getFechaInicio(),
                                generacionReporteDTO.getFechaFin()).toString(),
                        generarNombreArchivo(generacionReporteDTO) };
                data.add(0, datosControl1);
                break;
            case REPORTE_MENSUAL_AFILIADOS:
                separador = "/";
                mesFinal = String.valueOf((fechaFinal.getMonthValue() < 9) ? "0" + fechaFinal.getMonthValue() : fechaFinal.getMonthValue());
                diaFinal = String.valueOf((fechaFinal.getDayOfMonth() < 9) ? "0" + fechaFinal.getDayOfMonth() : fechaFinal.getDayOfMonth());
                String[] datosControl = { fechaFinal.getYear() + separador + mesFinal + separador + diaFinal,
                        pregenerarReporte(generacionReporteDTO.getReporteNormativo(), generacionReporteDTO.getFechaInicio(),
                                generacionReporteDTO.getFechaFin()).toString() };
                data.add(0, datosControl);

                break;
            case REPORTE_MENSUAL_ASIGNADOS:
                Date hoy = new Date();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
                String fechaHoy = formatoFecha.format(hoy);
                String[] datosControlASignadosFovis = { fechaHoy, pregenerarReporte(generacionReporteDTO.getReporteNormativo(),
                        generacionReporteDTO.getFechaInicio(), generacionReporteDTO.getFechaFin()).toString() };
                data.add(0, datosControlASignadosFovis);
                break;
            case REPORTE_NOVEDADES_ESTADO_AFILIACION_APORTANTE:
                Map<String, Object> datosAdicionales = new HashMap<>();
                datosAdicionales.put(ReporteNormativoNovedadesEstadoAfiliacion.NOMBRE_ARCHIVO, generacionReporteDTO.getNombreArchivo());
                datosAdicionales.put(ReporteNormativoNovedadesEstadoAfiliacion.CANTIDAD_REGISTROS,
                        pregenerarReporte(generacionReporteDTO.getReporteNormativo(), generacionReporteDTO.getFechaInicio(),
                                generacionReporteDTO.getFechaFin()));

                data = generarData(reporteNormativo, generacionReporteDTO.getFechaInicio(), generacionReporteDTO.getFechaFin(),
                        datosAdicionales);
                break;
            default:
                break;

        }

        return generarArchivoReporteNormativo(formatoReporte, encabezado, data, reporteNormativo.getCaracterSeparador(), reporteNormativo);
    }

    private byte[] generarArchivoReporteNormativo(FormatoReporteEnum formatoReporte, List<String[]> encabezado, List<String[]> data,
            String caracterSeparador, ReporteNormativoEnum reporteNormativo) {

        switch (formatoReporte) {
            case EXCEL: {
                if (reporteNormativo.getIdentificadorPlantilla() != null) {
                    String nombreHoja = reporteNormativo.equals(ReporteNormativoEnum.REPORTE_DEVOLUCIONES) ? "Devoluciones"
                            : reporteNormativo.equals(ReporteNormativoEnum.REPORTE_PAGO_POR_FUERA_DE_PILA) ? "Hoja 1" : "INFORME SUPER";
                    try {
                        return GeneradorContenidoArchivoUtil.generarArchivoExcel(data, nombreHoja, reporteNormativo.getFila(),
                                reporteNormativo.getIdentificadorPlantilla());
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    return GeneradorContenidoArchivoUtil.generarNuevoArchivoExcel(encabezado, data, reporteNormativo);
                }
            }
            case XML: {
                return GeneradorContenidoArchivoUtil.generarArchivoXML(encabezado, data, reporteNormativo);
            }
            case CSV: {
                return GeneradorContenidoArchivoUtil.generarArchivoCSV(encabezado, data, caracterSeparador);
            }
            case TXT: {
                return GeneradorContenidoArchivoUtil.generarArchivoPlano(encabezado, data, caracterSeparador);
            }
            default: {
                return null;
            }
        }
    }

    protected abstract List<String[]> generarEncabezado();

    protected abstract List<String[]> generarData(GeneracionReporteNormativoDTO generacionReporteDTO);

    protected abstract List<String[]> generarData(ReporteNormativoEnum reporteNormativo, Date fechaInicio, Date fechaFin,
            Map<String, Object> datosAdicionales);

}
