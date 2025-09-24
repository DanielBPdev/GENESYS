package com.asopagos.aportes.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asopagos.aportes.dto.ArchivoCierreDTO;
import com.asopagos.aportes.dto.DetalleRegistroCotizanteDTO;
import com.asopagos.aportes.dto.DetalleRegistroDTO;
import com.asopagos.aportes.dto.RegistroAporteDTO;
import com.asopagos.aportes.dto.RegistrosArchivoAporteDTO;
import com.asopagos.aportes.dto.ResultadoDetalleRegistroDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class ArchivoAportesUtil {

    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(ArchivoAportesUtil.class);
    
    private static final String MSG_DEFAULT = "No se encontraron registros para el periodo";

    private static final SimpleDateFormat formatoFechaCierre = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * @param registros
     * @return
     */
    public static ArchivoCierreDTO generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros) {

        logger.debug("Inicio del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
        logger.info("Inicio del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
        XSSFWorkbook libro = new XSSFWorkbook();

        ByteArrayOutputStream archivo = new ByteArrayOutputStream();
        ArchivoCierreDTO archivoIds = new ArchivoCierreDTO();
        StringBuilder idsAportes = new StringBuilder();
        List<String[]> data = new ArrayList<String[]>();
        
        byte[] archivoAportes = MSG_DEFAULT.getBytes();
        byte[] archivoAportesCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoOtrosIngresos = MSG_DEFAULT.getBytes();
        byte[] archivoOtrosIngresosCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoDevoluciones = MSG_DEFAULT.getBytes();
        byte[] archivoDevolucionesCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoRegistrados = MSG_DEFAULT.getBytes();
        byte[] archivoRegistradosCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoCorrecciones = MSG_DEFAULT.getBytes();
        byte[] archivoCorreccionesCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoCorreccionesOrigen = MSG_DEFAULT.getBytes();
        byte[] archivoCorreccionesOrigenCotizantes = MSG_DEFAULT.getBytes();
        byte[] archivoPlanillasN = MSG_DEFAULT.getBytes();
        byte[] archivoPlanillasNCotizante = MSG_DEFAULT.getBytes();
        byte[] archivoExtratemporaneos = MSG_DEFAULT.getBytes();
        byte[] archivoExtratemporaneosCotizantes = MSG_DEFAULT.getBytes();


        construirHojaCuadroResumen(libro, registros.getResumenCierreRecaudo());

        String ids;
        if (registros.getDetallePorRegistro() != null) {
            for (DetalleRegistroDTO detalleRegistroDTO : registros.getDetallePorRegistro()) {
                
                if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.APORTES)) {
                    logger.info( "[ Cierre de Recaudo] - ConstruirHojaAportes");
                    data = generarDataAportes(detalleRegistroDTO);
                    archivoAportes = generarArchivoPlano(null, data, "|");
                    data = generarDataAportes2(detalleRegistroDTO);
                    archivoAportesCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        logger.info( "[ Cierre de Recaudo] - idsAportes");
                        idsAportes.append(ids);
                    }
                }
                if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.OTROS_INGRESOS)) {
                        logger.info( "[ Cierre de Recaudo] - ConstruirHojaAportes Otros Ingresos");
                        data = generarDataOtrosIngresosAportante(detalleRegistroDTO);
                        archivoOtrosIngresos = generarArchivoPlano(null, data, "|");
                        data = generarDataOtrosIngresosCotizante(detalleRegistroDTO);
                        archivoOtrosIngresosCotizantes = generarArchivoPlano(null, data, "|");
                        ids = obtenerIdsAportes(detalleRegistroDTO);
                        if (ids != null) {
                            logger.info( "[ Cierre de Recaudo] - idsAportes");
                            idsAportes.append(ids);
                        }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.DEVOLUCIONES)) {
                    data = generarDataDevolucionesAportante(detalleRegistroDTO);
                    archivoDevoluciones = generarArchivoPlano(null, data, "|");
                    data = generarDataDevolucionesCotizante(detalleRegistroDTO);
                    archivoDevolucionesCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        idsAportes.append(ids);
                    }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.REGISTRADOS)) {
                    data = generarDataLegalizadosAportante(detalleRegistroDTO);
                    archivoRegistrados = generarArchivoPlano(null, data, "|");
                    data = generarDataLegalizadosCotizante(detalleRegistroDTO);
                    archivoRegistradosCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        idsAportes.append(ids);
                    }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.CORRECCIONES)) {
                    data = generarDataCorrecionesAnuladasAportante(detalleRegistroDTO);
                    archivoCorrecciones = generarArchivoPlano(null, data, "|");
                    data = generarDataCorrecionesAnuladasCotizante(detalleRegistroDTO);
                    archivoCorreccionesCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        idsAportes.append(ids);
                    }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.CORRECCIONES_ORIGEN)) {
                    data = generarDataCorrecionesOrigenAportante(detalleRegistroDTO);
                    archivoCorreccionesOrigen = generarArchivoPlano(null, data, "|");
                    data = generarDataCorrecionesOrigenCotizante(detalleRegistroDTO);
                    archivoCorreccionesOrigenCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        idsAportes.append(ids);
                    }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.PLANILLAS_N)) {
                        data = generarDataCorrecionesALaAltaAportante(detalleRegistroDTO);
                        archivoPlanillasN = generarArchivoPlano(null, data, "|");
                        data = generarDataCorrecionesALaAltaCotizante(detalleRegistroDTO);
                        archivoPlanillasNCotizante = generarArchivoPlano(null, data, "|");
                        ids = obtenerIdsAportes(detalleRegistroDTO);
                        if (ids != null) {
                            idsAportes.append(ids);
                        }
                }
                else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.APORTES_EXTEMPORANEOS)) {
                    logger.info( "[ Cierre de Recaudo] - ConstruirHojaExtratemporaneos");
                    data = generarDataExtratemporaneo(detalleRegistroDTO);
                    archivoExtratemporaneos = generarArchivoPlano(null, data, "|");
                    data = generarDataExtratemporaneos2(detalleRegistroDTO);
                    archivoExtratemporaneosCotizantes = generarArchivoPlano(null, data, "|");
                    ids = obtenerIdsAportes(detalleRegistroDTO);
                    if (ids != null) {
                        logger.info( "[ Cierre de Recaudo] - idsAportes");
                        idsAportes.append(ids);
                    }
                }
            }
        }

        // Almacenamos el libro de Excel via ese flujo de datos
        // Com.libro.write(salida);
        // Desc.
        try {
            libro.write(archivo);
            libro.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // Creamos el flujo de salida de datos, apuntando al archivo donde
        // queremos almacenar el libro de Excel
        // Com.FileOutputStream salida = new FileOutputStream(archivo);

        // Cerramos el libro para concluir operaciones
       

        File comprimido = null;
        byte[] contenidoArchivoComp = null;

        HashMap<String, byte[]> zip = new HashMap<>();
        zip.put("Resumen.xls", archivo.toByteArray());
        zip.put("Aportes.csv", archivoAportes);
        zip.put("Aportes2.csv", archivoAportesCotizantes);
        zip.put("Prescripcion.csv", archivoOtrosIngresos);
        zip.put("Prescripcion2.csv", archivoOtrosIngresosCotizantes);
        zip.put("Devoluciones.csv", archivoDevoluciones);
        zip.put("Devoluciones2.csv", archivoDevolucionesCotizantes);
        zip.put("Legalizados.csv", archivoRegistrados);
        zip.put("Legalizados2.csv", archivoRegistradosCotizantes);
        zip.put("CorrecionesRecaudoOriginal.csv", archivoCorrecciones);
        zip.put("CorrecionesRecaudoOriginal2.csv", archivoCorreccionesCotizantes);
        zip.put("CorrecionesNuevoAporte.csv", archivoCorreccionesOrigen);
        zip.put("CorrecionesNuevoAporte2.csv", archivoCorreccionesOrigenCotizantes);
        zip.put("PlanillasN.csv", archivoPlanillasN);
        zip.put("PlanillasN2.csv", archivoPlanillasNCotizante);
        zip.put("AportesExtratemporaneos.csv", archivoExtratemporaneos);
        zip.put("AportesExtratemporaneos2.csv", archivoExtratemporaneosCotizantes);
        Iterator<String> it = zip.keySet().iterator();
 
        while(it.hasNext()){
             String pointer = it.next();
             System.out.println(" Logs Generacion Archivos Archivo: " + pointer + ", Datos: " +  zip.get(pointer)+"\n\n");
        }

        try {
            comprimido = comprimirZip(1, zip);
            contenidoArchivoComp = Files.readAllBytes(comprimido.toPath());
            logger.info(" [ Generacion del .zip] Genera archivo .zip Aportes.csv ");
        } catch (Exception e) {
            logger.error("Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)", e);
            logger.info(" error al Generar archivo .zip  ");
        }

        logger.debug("Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
        archivoIds.setIdsAporteGeneral(idsAportes.toString());
        // Desc.
        archivoIds.setArchivo(contenidoArchivoComp);
        // Desc.
        return archivoIds;

    }

    /**
     * Hoja donde se puede ver la informcación del cuadro de resumen del cierre
     * de recaudo de aportes
     * 
     * @param libro
     * @param resumenCierreRecaudo
     * @return Hoja con la información
     */
    private static Sheet construirHojaCuadroResumen(XSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo) {
          try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print
            String jsonResumen = mapper.writeValueAsString(resumenCierreRecaudo);
            System.out.println("Contenido de resumenCierreRecaudo en JSON:\n" + jsonResumen);
        } catch (Exception e) {
            System.err.println("Error al convertir resumenCierreRecaudo a JSON:");
            e.printStackTrace();
        }
        try {
            logger.info(
                    "Inicia el método construirHojaCuadroResumen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
            Sheet pagina = libro.createSheet("Cuadro resumen");
            //HSSFPalette palette = libro.getCustomPalette();

            String[] periodos = { "Periodo Regular", "Periodo Anterior", "Periodo Futuro", "Resumen","Resumen Independientes","Resumen pensionados" };
            String[] tiposSolicitantes = { "Dependientes", "Independientes", "Pensionados", "Subtotal" };
            String[] tiposSolicitantesIndependiente = {"Independientes 2%","Independientes 0,6%", "Subtotal"};
            String[] estadoRegistrado = { "Registrado", "Relacionado" };
            String[] valorAportes = { "Aporte", "Interes", "Total" };
            String[] tiposDeRegistro = { "Aportes","Planillas N","Aportes extratemporaneos", "Devoluciones", "Aportes - Devoluciones", "Registrados (Legalizados)",
                    "Aportes Registrados (Otros Ingresos)", "Correcciones", "Subtotal" };
           String[] tiposSolicitantesPensionados = {"Pensionados 2%","Pensionados 0,6%", "Subtotal"};


            List<Cell> celdas = new ArrayList<Cell>();

            int indiceFila = 0;
            for (String periodo : periodos) {
               Row fila = pagina.createRow(indiceFila);

                // 0,first row (0-based); 2,last row (0-based) ; 0, first column
                // (0-based); 5 last column (0-based)
                pagina.addMergedRegion(new CellRangeAddress(indiceFila, indiceFila + 2, 0, 2));

                // get the color which most closely matches the color you want
                // to use
                XSSFColor azulPeriodos = new XSSFColor(new Color(0, 98, 242));
                CellStyle style = libro.createCellStyle();
                Cell celda = fila.createCell(0);

                style.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                style.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setAlignment(HorizontalAlignment.CENTER);
                celda.setCellStyle(style);
                celda.setCellValue(periodo);

                int indiceColumnaTipoSolicitante = 3;
                int indiceColumnaEstadoRegistro = 0;
                int indiceColumnaValorAportes = 3;
                Row filaDos = pagina.createRow(indiceFila + 1);
                Row filaTres = pagina.createRow(indiceFila + 2);
                if (periodo.equals("Resumen Independientes")) {

                        

                    for (String tipoSolicitante : tiposSolicitantesIndependiente) {
                        pagina.addMergedRegion(
                                new CellRangeAddress(indiceFila, indiceFila, indiceColumnaTipoSolicitante, indiceColumnaTipoSolicitante + 5));
                        CellStyle style2 = libro.createCellStyle();
                        Cell celda2 = fila.createCell(indiceColumnaTipoSolicitante);
                        XSSFColor amarilloSolicitante = new XSSFColor(new Color(255, 242, 204));
                        XSSFColor grisSolicitante = new XSSFColor(new Color(180, 190, 230));
                        style2.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                        style2.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        style2.setAlignment(HorizontalAlignment.CENTER);
                        celda2.setCellStyle(style2);
                        celda2.setCellValue(tipoSolicitante);

                        for (String estado : estadoRegistrado) {
                            indiceColumnaEstadoRegistro = indiceColumnaEstadoRegistro + 3;
                            pagina.addMergedRegion(new CellRangeAddress(indiceFila + 1, indiceFila + 1, indiceColumnaEstadoRegistro,
                                    indiceColumnaEstadoRegistro + 2));

                            Font fuenteColorRojo = libro.createFont();
                            fuenteColorRojo.setColor(HSSFColorPredefined.RED.getIndex());
                            CellStyle style3 = libro.createCellStyle();
                            style3.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                            style3.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            style3.setAlignment(HorizontalAlignment.CENTER);
                            style3.setFont(fuenteColorRojo);
                            Cell celda3 = filaDos.createCell(indiceColumnaEstadoRegistro);
                            celda3.setCellStyle(style3);
                            celda3.setCellValue(estado);

                            for (String valorAporte : valorAportes) {
                                CellStyle style4 = libro.createCellStyle();
                                Cell celda4 = filaTres.createCell(indiceColumnaValorAportes);
                                style4.setFillForegroundColor(style2.getFillForegroundColor());
                                style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                style4.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                                style4.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                                style4.setAlignment(HorizontalAlignment.CENTER);
                                celda4.setCellStyle(style4);
                                celda4.setCellValue(valorAporte);
                                indiceColumnaValorAportes++;
                            }
                        }
                        indiceColumnaTipoSolicitante = indiceColumnaTipoSolicitante + 6;
                    } 
                }else if (periodo.equals("Resumen pensionados")) {


                        for (String tiposolicitudpensionados : tiposSolicitantesPensionados) {
                                pagina.addMergedRegion(
                                        new CellRangeAddress(indiceFila, indiceFila, indiceColumnaTipoSolicitante, indiceColumnaTipoSolicitante + 5));
                                CellStyle style2 = libro.createCellStyle();
                                Cell celda2 = fila.createCell(indiceColumnaTipoSolicitante);
                                XSSFColor amarilloSolicitante = new XSSFColor(new Color(255, 242, 204));
                                XSSFColor grisSolicitante = new XSSFColor(new Color(180, 190, 230));
                                style2.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                                style2.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                                style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                style2.setAlignment(HorizontalAlignment.CENTER);
                                celda2.setCellStyle(style2);
                                celda2.setCellValue(tiposolicitudpensionados);

                                for (String estado : estadoRegistrado) {
                                        indiceColumnaEstadoRegistro = indiceColumnaEstadoRegistro + 3;
                                        pagina.addMergedRegion(new CellRangeAddress(indiceFila + 1, indiceFila + 1, indiceColumnaEstadoRegistro,
                                                indiceColumnaEstadoRegistro + 2));

                                        Font fuenteColorRojo = libro.createFont();
                                        fuenteColorRojo.setColor(HSSFColorPredefined.RED.getIndex());
                                        CellStyle style3 = libro.createCellStyle();
                                        style3.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                                        style3.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                                        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        style3.setAlignment(HorizontalAlignment.CENTER);
                                        style3.setFont(fuenteColorRojo);
                                        Cell celda3 = filaDos.createCell(indiceColumnaEstadoRegistro);
                                        celda3.setCellStyle(style3);
                                        celda3.setCellValue(estado);

                                        for (String valorAporte : valorAportes) {
                                                CellStyle style4 = libro.createCellStyle();
                                                Cell celda4 = filaTres.createCell(indiceColumnaValorAportes);
                                                style4.setFillForegroundColor(style2.getFillForegroundColor());
                                                style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                style4.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                                                style4.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                                                style4.setAlignment(HorizontalAlignment.CENTER);
                                                celda4.setCellStyle(style4);
                                                celda4.setCellValue(valorAporte);
                                                indiceColumnaValorAportes++;
                                        }
                                }
                                indiceColumnaTipoSolicitante = indiceColumnaTipoSolicitante + 6;
                        }
                } else {
                    for (String tipoSolicitante : tiposSolicitantes) {
                        pagina.addMergedRegion(
                                new CellRangeAddress(indiceFila, indiceFila, indiceColumnaTipoSolicitante, indiceColumnaTipoSolicitante + 5));
                        CellStyle style2 = libro.createCellStyle();
                        Cell celda2 = fila.createCell(indiceColumnaTipoSolicitante);
                        XSSFColor amarilloSolicitante = new XSSFColor(new Color(255, 242, 204));
                        XSSFColor grisSolicitante = new XSSFColor(new Color(180, 190, 230));
                        style2.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                        style2.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        style2.setAlignment(HorizontalAlignment.CENTER);
                        celda2.setCellStyle(style2);
                        celda2.setCellValue(tipoSolicitante);

                        for (String estado : estadoRegistrado) {
                            indiceColumnaEstadoRegistro = indiceColumnaEstadoRegistro + 3;
                            pagina.addMergedRegion(new CellRangeAddress(indiceFila + 1, indiceFila + 1, indiceColumnaEstadoRegistro,
                                    indiceColumnaEstadoRegistro + 2));

                            Font fuenteColorRojo = libro.createFont();
                            fuenteColorRojo.setColor(HSSFColorPredefined.RED.getIndex());
                            CellStyle style3 = libro.createCellStyle();
                            style3.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                            style3.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            style3.setAlignment(HorizontalAlignment.CENTER);
                            style3.setFont(fuenteColorRojo);
                            Cell celda3 = filaDos.createCell(indiceColumnaEstadoRegistro);
                            celda3.setCellStyle(style3);
                            celda3.setCellValue(estado);

                            for (String valorAporte : valorAportes) {
                                CellStyle style4 = libro.createCellStyle();
                                Cell celda4 = filaTres.createCell(indiceColumnaValorAportes);
                                style4.setFillForegroundColor(style2.getFillForegroundColor());
                                style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                style4.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
                                style4.setFillBackgroundColor(HSSFColorPredefined.BLACK.getIndex());
                                style4.setAlignment(HorizontalAlignment.CENTER);
                                celda4.setCellStyle(style4);
                                celda4.setCellValue(valorAporte);
                                indiceColumnaValorAportes++;
                            }
                        }
                        indiceColumnaTipoSolicitante = indiceColumnaTipoSolicitante + 6;
                    }
                }
                int indiceColumnaTipoRegistro = 0;
                int indiceFilaIter = 3;
                Row filaCuatro = pagina.createRow(indiceFila + 3);
                Row filaCinco = pagina.createRow(indiceFila + 4);
                Row filaSeis = pagina.createRow(indiceFila + 5);
                Row filaSiete = pagina.createRow(indiceFila + 6);
                Row filaOcho = pagina.createRow(indiceFila + 7);
                Row filaNueve = pagina.createRow(indiceFila + 8);
                Row filaDiez = pagina.createRow(indiceFila + 9);
                Row filaOnce = pagina.createRow(indiceFila + 10);
                Row filaDoce = pagina.createRow(indiceFila + 11);


                Cell celda1 = null;
                Cell celda2 = null;
                Cell celda3 = null;
                Cell celda4 = null;
                Cell celda5 = null;
                Cell celda6 = null;
                Cell celda7 = null;
                Cell celda8 = null;
                Cell celda9 = null;
                Cell celda10 = null;
                Cell celda11 = null;
                Cell celda12 = null;
                Cell celda13 = null;
                Cell celda14 = null;
                Cell celda15 = null;
                Cell celda16 = null;
                Cell celda17 = null;
                Cell celda18 = null;
                Cell celda19 = null;
                Cell celda20 = null;
                Cell celda21 = null;
                Cell celda22 = null;
                Cell celda23 = null;
                Cell celda24 = null;

                Cell celdaSub1 = null;
                Cell celdaSub2 = null;
                Cell celdaSub3 = null;
                Cell celdaSub4 = null;
                Cell celdaSub5 = null;
                Cell celdaSub6 = null;
                Cell celdaSub7 = null;
                Cell celdaSub8 = null;
                Cell celdaSub9 = null;
                Cell celdaSub10 = null;
                Cell celdaSub11 = null;
                Cell celdaSub12 = null;
                Cell celdaSub13 = null;
                Cell celdaSub14 = null;
                Cell celdaSub15 = null;
                Cell celdaSub16 = null;
                Cell celdaSub17 = null;
                Cell celdaSub18 = null;
                Cell celdaSub19 = null;
                Cell celdaSub20 = null;
                Cell celdaSub21 = null;
                Cell celdaSub22 = null;
                Cell celdaSub23 = null;
                Cell celdaSub24 = null;

                for (String tipoRegistro : tiposDeRegistro) {
                    pagina.addMergedRegion(new CellRangeAddress(indiceFila + indiceFilaIter, indiceFila + indiceFilaIter,
                            indiceColumnaTipoRegistro, indiceColumnaTipoRegistro + 2));
                    CellStyle style5 = libro.createCellStyle();
                    if (tipoRegistro.equals("Aportes")) {
                        celda5 = filaCuatro.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Planillas N")) {
                        celda5 = filaCinco.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Aportes extratemporaneos")) {
                        celda5 = filaSeis.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Devoluciones")) {
                        celda5 = filaSiete.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Aportes - Devoluciones")) {
                        celda5 = filaOcho.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Registrados (Legalizados)")) {
                        celda5 = filaNueve.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Aportes Registrados (Otros Ingresos)")) {
                        celda5 = filaDiez.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Correcciones")) {
                        celda5 = filaOnce.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Subtotal")) {
                        celda5 = filaDoce.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    }

                    indiceFilaIter++;
                }

                int indiceColumnaAporte = 3;

                for (ResumenCierreRecaudoDTO resumenCierreRecaudoDTO : resumenCierreRecaudo) {

                    if (resumenCierreRecaudoDTO.getPeriodo().getDescripcion().equals(periodo) || ((periodo.equals("Resumen Independientes") || periodo.equals("Resumen pensionados")) &&
                            resumenCierreRecaudoDTO.getPeriodo().getDescripcion().equals("Resumen"))) {
                        BigDecimal montoRegistradoDependienteAD = BigDecimal.ZERO;
                        BigDecimal interesRegistradoDependienteAD = BigDecimal.ZERO;
                        BigDecimal totalRegistradoDependienteAD = BigDecimal.ZERO;
                        BigDecimal montoRegistradoInependienteAD = BigDecimal.ZERO;
                        BigDecimal interesRegistradoInependienteAD = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteAD = BigDecimal.ZERO;
                        BigDecimal montoRegistradoPensionadoAD = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadoAD = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadoAD = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoDependienteAD = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoDependienteAD = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoDependienteAD = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoIndependienteAD = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteAD = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteAD = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadoAD = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadoAD = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoPensionadoAD = BigDecimal.ZERO;

                        BigDecimal montoRegistradoDependienteSub = BigDecimal.ZERO;
                        BigDecimal interesRegistradoDependienteSub = BigDecimal.ZERO;
                        BigDecimal totalRegistradoDependienteSub = BigDecimal.ZERO;
                        BigDecimal montoRegistradoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal interesRegistradoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal montoRegistradoPensionadoSub = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadoSub = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadoSub = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoDependienteSub = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoDependienteSub = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoDependienteSub = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteSub = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadoSub = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadoSub = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoPensionadoSub = BigDecimal.ZERO;

                        BigDecimal montoRegistradoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal montoRegistradoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal montoRegistradoIndependienteAD006 = BigDecimal.ZERO;  
                        BigDecimal montoRegistradoPensionadosAD006 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoIndependienteAD006 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadosAD006 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteAD006 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadosAD006 = BigDecimal.ZERO;


                        BigDecimal montoRelacionadoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteAD02 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoPensionadosAD02 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoIndependienteAD006 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadosAD006 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteAD006 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadosAD006 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteAD006 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoPensionadosAD006 = BigDecimal.ZERO;


                        BigDecimal montoRegistradoIndependienteSub02 = BigDecimal.ZERO;
                        BigDecimal montoRegistradoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoIndependienteSub02 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteSub02 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal montoRegistradoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal montoRegistradoPensionadosSub006 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal interesRegistradoPensionadosSub006 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal totalRegistradoPensionadosSub006 = BigDecimal.ZERO;

                        BigDecimal montoRelacionadoIndependienteSub02 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteSub02 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteSub02 = BigDecimal.ZERO;                         
                        BigDecimal totalRelacionadoPensionadosSub02 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoPensionadosSub006 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoPensionadosSub006 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoIndependienteSub006 = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoPensionadosSub006 = BigDecimal.ZERO;


                        BigDecimal montoRegistradoSub = BigDecimal.ZERO;
                        BigDecimal interesRegistradoSub = BigDecimal.ZERO;
                        BigDecimal totalRegistradoSub = BigDecimal.ZERO;
                        BigDecimal montoRelacionadoSub = BigDecimal.ZERO;
                        BigDecimal interesRelacionadoSub = BigDecimal.ZERO;
                        BigDecimal totalRelacionadoSub = BigDecimal.ZERO;


                        int contador = 0;
                        for (RegistroAporteDTO registro : resumenCierreRecaudoDTO.getRegistros()) {
                            logger.info("registro getTipoRegistro" +registro.getTipoRegistro());
                            logger.info("registro " +registro.toString());

                            // setea los datos para que tengan formaro de dos decimales
                            registro.setMontoRegistradoDependiente(registro.getMontoRegistradoDependiente() != null ? registro.getMontoRegistradoDependiente() : registro.getMontoRegistradoDependiente());
                            registro.setInteresRegistradoDependiente(registro.getInteresRegistradoDependiente() != null ? registro.getInteresRegistradoDependiente() : registro.getInteresRegistradoDependiente());
                            registro.setTotalRegistradoDependiente(registro.getTotalRegistradoDependiente() != null ? registro.getTotalRegistradoDependiente() : registro.getTotalRegistradoDependiente());
                            registro.setMontoRelacionadoDependiente(registro.getMontoRelacionadoDependiente() != null ? registro.getMontoRelacionadoDependiente() : registro.getMontoRelacionadoDependiente());
                            registro.setInteresRelacionadoDependiente(registro.getInteresRelacionadoDependiente() != null ? registro.getInteresRelacionadoDependiente() : registro.getInteresRelacionadoDependiente());
                            registro.setTotalRelacionadoDependiente(registro.getTotalRelacionadoDependiente() != null ? registro.getTotalRelacionadoDependiente() : registro.getTotalRelacionadoDependiente());
                            registro.setMontoRegistradoIndependiente(registro.getMontoRegistradoIndependiente() != null ? registro.getMontoRegistradoIndependiente() : registro.getMontoRegistradoIndependiente());
                            registro.setInteresRegistradoIndependiente(registro.getInteresRegistradoIndependiente() != null ? registro.getInteresRegistradoIndependiente() : registro.getInteresRegistradoIndependiente());
                            registro.setTotalRegistradoIndependiente(registro.getTotalRegistradoIndependiente() != null ? registro.getTotalRegistradoIndependiente() : registro.getTotalRegistradoIndependiente());
                            registro.setMontoRelacionadoIndependiente(registro.getMontoRelacionadoIndependiente() != null ? registro.getMontoRelacionadoIndependiente() : registro.getMontoRelacionadoIndependiente());
                            registro.setInteresRelacionadoIndependiente(registro.getInteresRelacionadoIndependiente() != null ? registro.getInteresRelacionadoIndependiente() : registro.getInteresRelacionadoIndependiente());
                            registro.setTotalRelacionadoIndependiente(registro.getTotalRelacionadoIndependiente() != null ? registro.getTotalRelacionadoIndependiente() : registro.getTotalRelacionadoIndependiente());
                            registro.setMontoRegistradoPensionado(registro.getMontoRegistradoPensionado() != null ? registro.getMontoRegistradoPensionado() : registro.getMontoRegistradoPensionado());
                            registro.setInteresRegistradoPensionado(registro.getInteresRegistradoPensionado() != null ? registro.getInteresRegistradoPensionado() : registro.getInteresRegistradoPensionado());
                            registro.setTotalRegistradoPensionado(registro.getTotalRegistradoPensionado() != null ? registro.getTotalRegistradoPensionado() : registro.getTotalRegistradoPensionado());
                            registro.setMontoRelacionadoPensionado(registro.getMontoRelacionadoPensionado() != null ? registro.getMontoRelacionadoPensionado() : registro.getMontoRelacionadoPensionado());
                            registro.setInteresRelacionadoPensionado(registro.getInteresRelacionadoPensionado() != null ? registro.getInteresRelacionadoPensionado() : registro.getInteresRelacionadoPensionado());
                            registro.setTotalRelacionadoPensionado(registro.getTotalRelacionadoPensionado() != null ? registro.getTotalRelacionadoPensionado() : registro.getTotalRelacionadoPensionado());
                            registro.setMontoRegistradoIndependiente_02(registro.getMontoRegistradoIndependiente_02() != null ? registro.getMontoRegistradoIndependiente_02() : registro.getMontoRegistradoIndependiente_02());
                            registro.setInteresRegistradoIndependiente_02(registro.getInteresRegistradoIndependiente_02() != null ? registro.getInteresRegistradoIndependiente_02() : registro.getInteresRegistradoIndependiente_02());
                            registro.setTotalRegistradoIndependiente_02(registro.getTotalRegistradoIndependiente_02() != null ? registro.getTotalRegistradoIndependiente_02() : registro.getTotalRegistradoIndependiente_02());
                            registro.setMontoRegistradoIndependiente_06(registro.getMontoRegistradoIndependiente_06() != null ? registro.getMontoRegistradoIndependiente_06() : registro.getMontoRegistradoIndependiente_06());
                            registro.setInteresRegistradoIndependiente_06(registro.getInteresRegistradoIndependiente_06() != null ? registro.getInteresRegistradoIndependiente_06() : registro.getInteresRegistradoIndependiente_06());
                            registro.setTotalRegistradoIndependiente_06(registro.getTotalRegistradoIndependiente_06() != null ? registro.getTotalRegistradoIndependiente_06() : registro.getTotalRegistradoIndependiente_06());
                            registro.setMontoRelacionadoIndependiente_02(registro.getMontoRelacionadoIndependiente_02() != null ? registro.getMontoRelacionadoIndependiente_02() : registro.getMontoRelacionadoIndependiente_02());
                            registro.setInteresRelacionadoIndependiente_02(registro.getInteresRelacionadoIndependiente_02() != null ? registro.getInteresRelacionadoIndependiente_02() : registro.getInteresRelacionadoIndependiente_02());
                            registro.setTotalRelacionadoIndependiente_02(registro.getTotalRelacionadoIndependiente_02() != null ? registro.getTotalRelacionadoIndependiente_02() : registro.getTotalRelacionadoIndependiente_02());
                            registro.setMontoRelacionadoIndependiente_06(registro.getMontoRelacionadoIndependiente_06() != null ? registro.getMontoRelacionadoIndependiente_06() : registro.getMontoRelacionadoIndependiente_06());
                            registro.setInteresRelacionadoIndependiente_06(registro.getInteresRelacionadoIndependiente_06() != null ? registro.getInteresRelacionadoIndependiente_06() : registro.getInteresRelacionadoIndependiente_06());
                            registro.setTotalRelacionadoIndependiente_06(registro.getTotalRelacionadoIndependiente_06() != null ? registro.getTotalRelacionadoIndependiente_06() : registro.getTotalRelacionadoIndependiente_06());

                            registro.setMontoRegistradoPensionado_02(registro.getMontoRegistradoPensionado_02() != null ? registro.getMontoRegistradoPensionado_02() : registro.getMontoRegistradoPensionado_02());
                            registro.setInteresRegistradoPensionado_02(registro.getInteresRegistradoPensionado_02() != null ? registro.getInteresRegistradoPensionado_02() : registro.getInteresRegistradoPensionado_02());
                            registro.setTotalRegistradoPensionado_02(registro.getTotalRegistradoPensionado_02() != null ? registro.getTotalRegistradoPensionado_02() : registro.getTotalRegistradoPensionado_02());
                            registro.setMontoRegistradoPensionado_06(registro.getMontoRegistradoPensionado_06() != null ? registro.getMontoRegistradoPensionado_06() : registro.getMontoRegistradoPensionado_06());
                            registro.setInteresRegistradoPensionado_06(registro.getInteresRegistradoPensionado_06() != null ? registro.getInteresRegistradoPensionado_06() : registro.getInteresRegistradoPensionado_06());
                            registro.setTotalRegistradoPensionado_06(registro.getTotalRegistradoPensionado_06() != null ? registro.getTotalRegistradoPensionado_06() : registro.getTotalRegistradoPensionado_06());
                            registro.setMontoRelacionadoPensionado_02(registro.getMontoRelacionadoPensionado_02() != null ? registro.getMontoRelacionadoPensionado_02() : registro.getMontoRelacionadoPensionado_02());
                            registro.setInteresRelacionadoPensionado_02(registro.getInteresRelacionadoPensionado_02() != null ? registro.getInteresRelacionadoPensionado_02() : registro.getInteresRelacionadoPensionado_02());
                            registro.setTotalRelacionadoPensionado_02(registro.getTotalRelacionadoPensionado_02() != null ? registro.getTotalRelacionadoPensionado_02() : registro.getTotalRelacionadoPensionado_02());
                            registro.setMontoRelacionadoPensionado_06(registro.getMontoRelacionadoPensionado_06() != null ? registro.getMontoRelacionadoPensionado_06() : registro.getMontoRelacionadoPensionado_06());
                            registro.setInteresRelacionadoPensionado_06(registro.getInteresRelacionadoPensionado_06() != null ? registro.getInteresRelacionadoPensionado_06() : registro.getInteresRelacionadoPensionado_06());
                            registro.setTotalRelacionadoPensionado_06(registro.getTotalRelacionadoPensionado_06() != null ? registro.getTotalRelacionadoPensionado_06() : registro.getTotalRelacionadoPensionado_06());
                            
                            logger.info(registro.getTipoRegistro().getDescripcion());
                            if (registro.getTipoRegistro().getDescripcion().equals("Aportes")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    logger.info("generación archivo excel resumen independientes aportes");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaCuatro.setRowNum(indiceFila + 3);
                                    celda1 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD02 = montoRegistradoIndependienteAD02.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD02 = interesRegistradoIndependienteAD02
                                            .add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD02 = totalRegistradoIndependienteAD02.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD02 = montoRelacionadoIndependienteAD02
                                            .add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD02 = interesRelacionadoIndependienteAD02
                                            .add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD02 = totalRelacionadoIndependienteAD02
                                            .add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD006 = montoRegistradoIndependienteAD006
                                            .add(registro.getMontoRegistradoIndependiente_06());
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD006 = interesRegistradoIndependienteAD006
                                            .add(registro.getInteresRegistradoIndependiente_06());
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD006 = totalRegistradoIndependienteAD006
                                            .add(registro.getTotalRegistradoIndependiente_06());
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD006 = montoRelacionadoIndependienteAD006
                                            .add(registro.getMontoRelacionadoIndependiente_06());
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD006 = interesRelacionadoIndependienteAD006
                                            .add(registro.getInteresRelacionadoIndependiente_06());
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD006 = totalRelacionadoIndependienteAD006
                                            .add(registro.getTotalRelacionadoIndependiente_06());
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;

                                }else if (periodo.equals("Resumen pensionados")) {
                                    logger.info("generación archivo excel resumen pensionados aportes");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaCuatro.setRowNum(indiceFila + 3);
                                    celda1 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD02 = montoRegistradoPensionadosAD02.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD02 = interesRegistradoPensionadosAD02
                                            .add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD02 = totalRegistradoPensionadosAD02.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD02 = montoRelacionadoPensionadosAD02
                                            .add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD02 = interesRelacionadoPensionadosAD02
                                            .add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD02 = totalRelacionadoPensionadosAD02
                                            .add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD006 = montoRegistradoPensionadosAD006
                                            .add(registro.getMontoRegistradoPensionado_06());
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD006 = interesRegistradoPensionadosAD006
                                            .add(registro.getInteresRegistradoPensionado_06());
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD006 = totalRegistradoPensionadosAD006
                                            .add(registro.getTotalRegistradoPensionado_06());
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD006 = montoRelacionadoPensionadosAD006
                                            .add(registro.getMontoRelacionadoPensionado_06());
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD006 = interesRelacionadoPensionadosAD006
                                            .add(registro.getInteresRelacionadoPensionado_06());
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD006 = totalRelacionadoPensionadosAD006
                                            .add(registro.getTotalRelacionadoPensionado_06());
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;

                                }else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaCuatro.setRowNum(indiceFila + 3);
                                    celda1 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteAD = montoRegistradoDependienteAD.add(registro.getMontoRegistradoDependiente());
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteAD = interesRegistradoDependienteAD
                                            .add(registro.getInteresRegistradoDependiente());
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteAD = totalRegistradoDependienteAD.add(registro.getTotalRegistradoDependiente());
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
                                            .add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
                                            .add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
                                            .add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoInependienteAD = montoRegistradoInependienteAD
                                            .add(registro.getMontoRegistradoIndependiente());
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoInependienteAD = interesRegistradoInependienteAD
                                            .add(registro.getInteresRegistradoIndependiente());
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
                                            .add(registro.getTotalRegistradoIndependiente());
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
                                            .add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
                                            .add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
                                            .add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoAD = montoRegistradoPensionadoAD.add(registro.getMontoRegistradoPensionado());
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
                                            .add(registro.getInteresRegistradoPensionado());
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoAD = totalRegistradoPensionadoAD.add(registro.getTotalRegistradoPensionado());
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD.add(registro.getMontoRelacionadoPensionado());
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
                                            .add(registro.getInteresRelacionadoPensionado());
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD.add(registro.getTotalRelacionadoPensionado());
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda19);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda20);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda21);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda22);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda23);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaCuatro.createCell(indiceColumnaAporte);
                                    celdas.add(celda24);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;
                                }
///////////////////////////////////////////////////////aportes

                            } else if (registro.getTipoRegistro().getDescripcion().equals("Planillas N")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    logger.info("generación archivo excel resumen independientes aportes");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaCinco.setRowNum(indiceFila + 4);
                                    celda1 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD02 = montoRegistradoIndependienteAD02.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD02 = interesRegistradoIndependienteAD02
                                            .add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD02 = totalRegistradoIndependienteAD02.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD02 = montoRelacionadoIndependienteAD02
                                            .add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD02 = interesRelacionadoIndependienteAD02
                                            .add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD02 = totalRelacionadoIndependienteAD02
                                            .add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD006 = montoRegistradoIndependienteAD006
                                            .add(registro.getMontoRegistradoIndependiente_06());
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD006 = interesRegistradoIndependienteAD006
                                            .add(registro.getInteresRegistradoIndependiente_06());
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD006 = totalRegistradoIndependienteAD006
                                            .add(registro.getTotalRegistradoIndependiente_06());
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD006 = montoRelacionadoIndependienteAD006
                                            .add(registro.getMontoRelacionadoIndependiente_06());
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD006 = interesRelacionadoIndependienteAD006
                                            .add(registro.getInteresRelacionadoIndependiente_06());
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD006 = totalRelacionadoIndependienteAD006
                                            .add(registro.getTotalRelacionadoIndependiente_06());
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;

                                } else if(periodo.equals("Resumen pensionados")){
                                    logger.info("generación archivo excel resumen pensionados plnillas N");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaCinco.setRowNum(indiceFila + 4);
                                    celda1 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD02 = montoRegistradoPensionadosAD02.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD02 = interesRegistradoPensionadosAD02
                                            .add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD02 = totalRegistradoPensionadosAD02.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD02 = montoRelacionadoPensionadosAD02
                                            .add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD02 = interesRelacionadoPensionadosAD02
                                            .add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD02 = totalRelacionadoPensionadosAD02
                                            .add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD006 = montoRegistradoPensionadosAD006
                                            .add(registro.getMontoRegistradoPensionado_06());
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD006 = interesRegistradoPensionadosAD006
                                            .add(registro.getInteresRegistradoPensionado_06());
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD006 = totalRegistradoPensionadosAD006
                                            .add(registro.getTotalRegistradoPensionado_06());
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD006 = montoRelacionadoPensionadosAD006
                                            .add(registro.getMontoRelacionadoPensionado_06());
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD006 = interesRelacionadoPensionadosAD006
                                            .add(registro.getInteresRelacionadoPensionado_06());
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD006 = totalRelacionadoPensionadosAD006
                                            .add(registro.getTotalRelacionadoPensionado_06());
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaCinco.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;
                                }else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    filaCinco.setRowNum(indiceFila + 4);
                                    celda1 = filaCinco.createCell(indiceColumnaAporte);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteAD = montoRegistradoDependienteAD.add(registro.getMontoRegistradoDependiente());
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaCinco.createCell(indiceColumnaAporte);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteAD = interesRegistradoDependienteAD
                                            .add(registro.getInteresRegistradoDependiente());
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaCinco.createCell(indiceColumnaAporte);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteAD = totalRegistradoDependienteAD.add(registro.getTotalRegistradoDependiente());
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaCinco.createCell(indiceColumnaAporte);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
                                            .add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaCinco.createCell(indiceColumnaAporte);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
                                            .add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaCinco.createCell(indiceColumnaAporte);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
                                            .add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaCinco.createCell(indiceColumnaAporte);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoInependienteAD = montoRegistradoInependienteAD
                                            .add(registro.getMontoRegistradoIndependiente());
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaCinco.createCell(indiceColumnaAporte);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoInependienteAD = interesRegistradoInependienteAD
                                            .add(registro.getInteresRegistradoIndependiente());
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaCinco.createCell(indiceColumnaAporte);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
                                            .add(registro.getTotalRegistradoIndependiente());
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaCinco.createCell(indiceColumnaAporte);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
                                            .add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaCinco.createCell(indiceColumnaAporte);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
                                            .add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaCinco.createCell(indiceColumnaAporte);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
                                            .add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaCinco.createCell(indiceColumnaAporte);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoAD = montoRegistradoPensionadoAD.add(registro.getMontoRegistradoPensionado());
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaCinco.createCell(indiceColumnaAporte);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
                                            .add(registro.getInteresRegistradoPensionado());
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaCinco.createCell(indiceColumnaAporte);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoAD = totalRegistradoPensionadoAD.add(registro.getTotalRegistradoPensionado());
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaCinco.createCell(indiceColumnaAporte);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD.add(registro.getMontoRelacionadoPensionado());
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaCinco.createCell(indiceColumnaAporte);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
                                            .add(registro.getInteresRelacionadoPensionado());
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaCinco.createCell(indiceColumnaAporte);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD.add(registro.getTotalRelacionadoPensionado());
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaCinco.createCell(indiceColumnaAporte);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaCinco.createCell(indiceColumnaAporte);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaCinco.createCell(indiceColumnaAporte);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaCinco.createCell(indiceColumnaAporte);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaCinco.createCell(indiceColumnaAporte);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaCinco.createCell(indiceColumnaAporte);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                                //////////////////////////////////////////////////////////////////////////
                            } else if (registro.getTipoRegistro().getDescripcion().equals("Aportes extemporaneos")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    logger.info("generación archivo excel resumen independientes aportes extratemporaneos");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaSeis.setRowNum(indiceFila + 5);
                                    celda1 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD02 = montoRegistradoIndependienteAD02.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD02 = interesRegistradoIndependienteAD02
                                            .add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD02 = totalRegistradoIndependienteAD02.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD02 = montoRelacionadoIndependienteAD02
                                            .add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD02 = interesRelacionadoIndependienteAD02
                                            .add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD02 = totalRelacionadoIndependienteAD02
                                            .add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD006 = montoRegistradoIndependienteAD006
                                            .add(registro.getMontoRegistradoIndependiente_06());
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD006 = interesRegistradoIndependienteAD006
                                            .add(registro.getInteresRegistradoIndependiente_06());
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD006 = totalRegistradoIndependienteAD006
                                            .add(registro.getTotalRegistradoIndependiente_06());
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD006 = montoRelacionadoIndependienteAD006
                                            .add(registro.getMontoRelacionadoIndependiente_06());
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD006 = interesRelacionadoIndependienteAD006
                                            .add(registro.getInteresRelacionadoIndependiente_06());
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD006 = totalRelacionadoIndependienteAD006
                                            .add(registro.getTotalRelacionadoIndependiente_06());
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;

                                } else  if (periodo.equals("Resumen pensionados")) {
                                    logger.info("generación archivo excel resumen pensionados aportes extratemporaneos");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaSeis.setRowNum(indiceFila + 5);
                                    celda1 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD02 = montoRegistradoPensionadosAD02.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD02 = interesRegistradoPensionadosAD02
                                            .add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD02 = totalRegistradoPensionadosAD02.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD02 = montoRelacionadoPensionadosAD02
                                            .add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD02 = interesRelacionadoPensionadosAD02
                                            .add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD02 = totalRelacionadoPensionadosAD02
                                            .add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD006 = montoRegistradoPensionadosAD006
                                            .add(registro.getMontoRegistradoPensionado_06());
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD006 = interesRegistradoPensionadosAD006
                                            .add(registro.getInteresRegistradoPensionado_06());
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD006 = totalRegistradoPensionadosAD006
                                            .add(registro.getTotalRegistradoPensionado_06());
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD006 = montoRelacionadoPensionadosAD006
                                            .add(registro.getMontoRelacionadoPensionado_06());
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD006 = interesRelacionadoPensionadosAD006
                                            .add(registro.getInteresRelacionadoPensionado_06());
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD006 = totalRelacionadoPensionadosAD006
                                            .add(registro.getTotalRelacionadoPensionado_06());
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;
                                } else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaSeis.setRowNum(indiceFila + 5);
                                    celda1 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteAD = montoRegistradoDependienteAD.add(registro.getMontoRegistradoDependiente());
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteAD = interesRegistradoDependienteAD
                                            .add(registro.getInteresRegistradoDependiente());
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteAD = totalRegistradoDependienteAD.add(registro.getTotalRegistradoDependiente());
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
                                            .add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
                                            .add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
                                            .add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoInependienteAD = montoRegistradoInependienteAD
                                            .add(registro.getMontoRegistradoIndependiente());
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoInependienteAD = interesRegistradoInependienteAD
                                            .add(registro.getInteresRegistradoIndependiente());
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
                                            .add(registro.getTotalRegistradoIndependiente());
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
                                            .add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
                                            .add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
                                            .add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoAD = montoRegistradoPensionadoAD.add(registro.getMontoRegistradoPensionado());
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
                                            .add(registro.getInteresRegistradoPensionado());
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoAD = totalRegistradoPensionadoAD.add(registro.getTotalRegistradoPensionado());
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD.add(registro.getMontoRelacionadoPensionado());
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
                                            .add(registro.getInteresRelacionadoPensionado());
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD.add(registro.getTotalRelacionadoPensionado());
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda19);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda20);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda21);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda22);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda23);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaSeis.createCell(indiceColumnaAporte);
                                    celdas.add(celda24);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());
                                    indiceColumnaAporte = 3;
                                }

////////////////////////////////////////////////////////////////////////////////
                            } else if (registro.getTipoRegistro().getDescripcion().equals("Devoluciones")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaSiete.setRowNum(indiceFila + 6);
                                    celda1 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD02 = montoRegistradoIndependienteAD02.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD02 = interesRegistradoIndependienteAD02
                                            .add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD02 = totalRegistradoIndependienteAD02.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD02 = montoRelacionadoIndependienteAD02
                                            .add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD02 = interesRelacionadoIndependienteAD02
                                            .add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD02 = totalRelacionadoIndependienteAD02
                                            .add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteAD006 = montoRegistradoIndependienteAD006
                                            .add(registro.getMontoRegistradoIndependiente_06());
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteAD006 = interesRegistradoIndependienteAD006
                                            .add(registro.getInteresRegistradoIndependiente_06());
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD006 = totalRegistradoIndependienteAD006
                                            .add(registro.getTotalRegistradoIndependiente_06());
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD006 = montoRelacionadoIndependienteAD006
                                            .add(registro.getMontoRelacionadoIndependiente_06());
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD006 = interesRelacionadoIndependienteAD006
                                            .add(registro.getInteresRelacionadoIndependiente_06());
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD006 = totalRelacionadoIndependienteAD006
                                            .add(registro.getTotalRelacionadoIndependiente_06());
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;

                                } else if (periodo.equals("Resumen pensionados")) {
                                logger.info("generación archivo excel resumen pensionados devoluciones");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaSiete.setRowNum(indiceFila + 6);
                                    celda1 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD02 = montoRegistradoPensionadosAD02.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD02 = interesRegistradoPensionadosAD02
                                            .add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD02 = totalRegistradoPensionadosAD02.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD02 = montoRelacionadoPensionadosAD02
                                            .add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD02 = interesRelacionadoPensionadosAD02
                                            .add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD02 = totalRelacionadoPensionadosAD02
                                            .add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosAD006 = montoRegistradoPensionadosAD006
                                            .add(registro.getMontoRegistradoPensionado_06());
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                               .add(registro.getMontoRegistradoPensionado_06());
                                    celda8 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosAD006 = interesRegistradoPensionadosAD006
                                            .add(registro.getInteresRegistradoPensionado_06());
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosAD006 = totalRegistradoPensionadosAD006
                                            .add(registro.getTotalRegistradoPensionado_06());
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosAD006 = montoRelacionadoPensionadosAD006
                                            .add(registro.getMontoRelacionadoPensionado_06());
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosAD006 = interesRelacionadoPensionadosAD006
                                            .add(registro.getInteresRelacionadoPensionado_06());
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosAD006 = totalRelacionadoPensionadosAD006
                                            .add(registro.getTotalRelacionadoPensionado_06());
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaSiete.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                   }
                                    else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    filaSiete.setRowNum(indiceFila + 6);
                                    celda1 = filaSiete.createCell(indiceColumnaAporte);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteAD = montoRegistradoDependienteAD.add(registro.getMontoRegistradoDependiente());
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaSiete.createCell(indiceColumnaAporte);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteAD = interesRegistradoDependienteAD
                                            .add(registro.getInteresRegistradoDependiente());
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaSiete.createCell(indiceColumnaAporte);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteAD = totalRegistradoDependienteAD.add(registro.getTotalRegistradoDependiente());
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaSiete.createCell(indiceColumnaAporte);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
                                            .add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaSiete.createCell(indiceColumnaAporte);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
                                            .add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaSiete.createCell(indiceColumnaAporte);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
                                            .add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaSiete.createCell(indiceColumnaAporte);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoInependienteAD = montoRegistradoInependienteAD
                                            .add(registro.getMontoRegistradoIndependiente());
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaSiete.createCell(indiceColumnaAporte);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoInependienteAD = interesRegistradoInependienteAD
                                            .add(registro.getInteresRegistradoIndependiente());
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaSiete.createCell(indiceColumnaAporte);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
                                            .add(registro.getTotalRegistradoIndependiente());
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaSiete.createCell(indiceColumnaAporte);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
                                            .add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaSiete.createCell(indiceColumnaAporte);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
                                            .add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaSiete.createCell(indiceColumnaAporte);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
                                            .add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaSiete.createCell(indiceColumnaAporte);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoAD = montoRegistradoPensionadoAD.add(registro.getMontoRegistradoPensionado());
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaSiete.createCell(indiceColumnaAporte);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
                                            .add(registro.getInteresRegistradoPensionado());
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaSiete.createCell(indiceColumnaAporte);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoAD = totalRegistradoPensionadoAD.add(registro.getTotalRegistradoPensionado());
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaSiete.createCell(indiceColumnaAporte);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD.add(registro.getMontoRelacionadoPensionado());
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaSiete.createCell(indiceColumnaAporte);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
                                            .add(registro.getInteresRelacionadoPensionado());
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaSiete.createCell(indiceColumnaAporte);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD.add(registro.getTotalRelacionadoPensionado());
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaSiete.createCell(indiceColumnaAporte);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaSiete.createCell(indiceColumnaAporte);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaSiete.createCell(indiceColumnaAporte);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaSiete.createCell(indiceColumnaAporte);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaSiete.createCell(indiceColumnaAporte);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaSiete.createCell(indiceColumnaAporte);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                                ///////////////////////////////////////////////////////////////////////////
                            } else if (registro.getTipoRegistro().getDescripcion().equals("Registrados (Legalizados)")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaNueve.setRowNum(indiceFila + 8);
                                    celda1 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;


                                } else  if (periodo.equals("Resumen pensionados")) {
                                logger.info("Registrados (Legalizados)");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaNueve.setRowNum(indiceFila + 8);
                                    celda1 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaNueve.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                                    else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    filaNueve.setRowNum(indiceFila + 8);

                                    celda1 = filaNueve.createCell(indiceColumnaAporte);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaNueve.createCell(indiceColumnaAporte);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaNueve.createCell(indiceColumnaAporte);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaNueve.createCell(indiceColumnaAporte);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaNueve.createCell(indiceColumnaAporte);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaNueve.createCell(indiceColumnaAporte);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaNueve.createCell(indiceColumnaAporte);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaNueve.createCell(indiceColumnaAporte);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaNueve.createCell(indiceColumnaAporte);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaNueve.createCell(indiceColumnaAporte);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaNueve.createCell(indiceColumnaAporte);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaNueve.createCell(indiceColumnaAporte);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaNueve.createCell(indiceColumnaAporte);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaNueve.createCell(indiceColumnaAporte);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaNueve.createCell(indiceColumnaAporte);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaNueve.createCell(indiceColumnaAporte);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaNueve.createCell(indiceColumnaAporte);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaNueve.createCell(indiceColumnaAporte);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaNueve.createCell(indiceColumnaAporte);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaNueve.createCell(indiceColumnaAporte);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaNueve.createCell(indiceColumnaAporte);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaNueve.createCell(indiceColumnaAporte);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaNueve.createCell(indiceColumnaAporte);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaNueve.createCell(indiceColumnaAporte);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
///////////////////////////////////////////////
                            } else if (registro.getTipoRegistro().getDescripcion().equals("Registrados (Otros ingresos)")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaDiez.setRowNum(indiceFila + 9);
                                    celda1 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;


                                } else if (periodo.equals("Resumen pensionados")) {
                                    logger.info("Registrados (Otros ingresos)");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaDiez.setRowNum(indiceFila + 9);
                                    celda1 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaDiez.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }else {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    filaDiez.setRowNum(indiceFila + 9);

                                    celda1 = filaDiez.createCell(indiceColumnaAporte);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaDiez.createCell(indiceColumnaAporte);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaDiez.createCell(indiceColumnaAporte);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaDiez.createCell(indiceColumnaAporte);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaDiez.createCell(indiceColumnaAporte);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaDiez.createCell(indiceColumnaAporte);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaDiez.createCell(indiceColumnaAporte);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaDiez.createCell(indiceColumnaAporte);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaDiez.createCell(indiceColumnaAporte);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaDiez.createCell(indiceColumnaAporte);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaDiez.createCell(indiceColumnaAporte);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaDiez.createCell(indiceColumnaAporte);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaDiez.createCell(indiceColumnaAporte);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaDiez.createCell(indiceColumnaAporte);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaDiez.createCell(indiceColumnaAporte);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaDiez.createCell(indiceColumnaAporte);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaDiez.createCell(indiceColumnaAporte);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaDiez.createCell(indiceColumnaAporte);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaDiez.createCell(indiceColumnaAporte);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaDiez.createCell(indiceColumnaAporte);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaDiez.createCell(indiceColumnaAporte);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaDiez.createCell(indiceColumnaAporte);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaDiez.createCell(indiceColumnaAporte);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaDiez.createCell(indiceColumnaAporte);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
/////////////////////////////////////////////////////////////////////////////
                            } else if (registro.getTipoRegistro().getDescripcion().equals("Correcciones")) {
                                if (periodo.equals("Resumen Independientes")) {
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaOnce.setRowNum(indiceFila + 10);
                                    celda1 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub02 = montoRegistradoIndependienteSub02.add(registro.getMontoRegistradoIndependiente_02());

                                    celda2 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub02 = interesRegistradoIndependienteSub02
                                            .add(registro.getInteresRegistradoIndependiente_02());

                                    celda3 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub02 = totalRegistradoIndependienteSub02.add(registro.getTotalRegistradoIndependiente_02());

                                    celda4 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub02 = montoRelacionadoIndependienteSub02
                                            .add(registro.getMontoRelacionadoIndependiente_02());

                                    celda5 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub02 = interesRelacionadoIndependienteSub02
                                            .add(registro.getInteresRelacionadoIndependiente_02());

                                    celda6 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoIndependiente_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub02 = totalRelacionadoIndependienteSub02
                                            .add(registro.getTotalRelacionadoIndependiente_02());

                                    celda7 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub006 = montoRegistradoIndependienteSub006
                                            .add(registro.getMontoRegistradoIndependiente_06());

                                    celda8 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub006 = interesRegistradoIndependienteSub006
                                            .add(registro.getInteresRegistradoIndependiente_06());

                                    celda9 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub006 = totalRegistradoIndependienteSub006
                                            .add(registro.getTotalRegistradoIndependiente_06());

                                    celda10 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub006 = montoRelacionadoIndependienteSub006
                                            .add(registro.getMontoRelacionadoIndependiente_06());

                                    celda11 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub006 = interesRelacionadoIndependienteSub006
                                            .add(registro.getInteresRelacionadoIndependiente_06());

                                    celda12 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub006 = totalRelacionadoIndependienteSub006
                                            .add(registro.getTotalRelacionadoIndependiente_06());
                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente_06());

                                    celda13 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;

                                } else if (periodo.equals("Resumen pensionados")) {
                                logger.info("correciones");
                                    contador++;
                                    CellStyle style2 = libro.createCellStyle();
                                    XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
                                    style2.setFillForegroundColor(amarilloAporte.getIndex());
                                    filaOnce.setRowNum(indiceFila + 10);
                                    celda1 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda1);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub02 = montoRegistradoPensionadosSub02.add(registro.getMontoRegistradoPensionado_02());

                                    celda2 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda2);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub02 = interesRegistradoPensionadosSub02
                                            .add(registro.getInteresRegistradoPensionado_02());

                                    celda3 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda3);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub02 = totalRegistradoPensionadosSub02.add(registro.getTotalRegistradoPensionado_02());

                                    celda4 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda4);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub02 = montoRelacionadoPensionadosSub02
                                            .add(registro.getMontoRelacionadoPensionado_02());

                                    celda5 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda5);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub02 = interesRelacionadoPensionadosSub02
                                            .add(registro.getInteresRelacionadoPensionado_02());

                                    celda6 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoPensionado_02().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub02 = totalRelacionadoPensionadosSub02
                                            .add(registro.getTotalRelacionadoPensionado_02());

                                    celda7 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda6);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadosSub006 = montoRegistradoPensionadosSub006
                                            .add(registro.getMontoRegistradoPensionado_06());

                                    celda8 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda8);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadosSub006 = interesRegistradoPensionadosSub006
                                            .add(registro.getInteresRegistradoPensionado_06());

                                    celda9 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda9);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadosSub006 = totalRegistradoPensionadosSub006
                                            .add(registro.getTotalRegistradoPensionado_06());

                                    celda10 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda10);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadosSub006 = montoRelacionadoPensionadosSub006
                                            .add(registro.getMontoRelacionadoPensionado_06());

                                    celda11 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda11);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadosSub006 = interesRelacionadoPensionadosSub006
                                            .add(registro.getInteresRelacionadoPensionado_06());

                                    celda12 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda12);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoPensionado_06().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadosSub006 = totalRelacionadoPensionadosSub006
                                            .add(registro.getTotalRelacionadoPensionado_06());
                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_02());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado_06());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_02());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado_06());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_02());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado_06());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_02());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado_06());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_02());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado_06());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_02());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado_06());

                                    celda13 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda13);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda14 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda14);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda15 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda15);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda16 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda16);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda17 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda17);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda18 = filaOnce.createCell(indiceColumnaAporte);
                                    celdas.add(celda18);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                                    else {
                                    contador++;
                                    // Correcciones
                                    CellStyle style2 = libro.createCellStyle();
                                    filaOnce.setRowNum(indiceFila + 10);
                                    celda1 = filaOnce.createCell(indiceColumnaAporte);
                                    style2.setAlignment(HorizontalAlignment.RIGHT);
                                    celda1.setCellStyle(style2);
                                    celda1.setCellValue(registro.getMontoRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoDependienteSub = montoRegistradoDependienteSub.add(registro.getMontoRegistradoDependiente());

                                    celda2 = filaOnce.createCell(indiceColumnaAporte);
                                    celda2.setCellStyle(style2);
                                    celda2.setCellValue(registro.getInteresRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                            .add(registro.getInteresRegistradoDependiente());

                                    celda3 = filaOnce.createCell(indiceColumnaAporte);
                                    celda3.setCellStyle(style2);
                                    celda3.setCellValue(registro.getTotalRegistradoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoDependienteSub = totalRegistradoDependienteSub.add(registro.getTotalRegistradoDependiente());

                                    celda4 = filaOnce.createCell(indiceColumnaAporte);
                                    celda4.setCellStyle(style2);
                                    celda4.setCellValue(registro.getMontoRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                            .add(registro.getMontoRelacionadoDependiente());

                                    celda5 = filaOnce.createCell(indiceColumnaAporte);
                                    celda5.setCellStyle(style2);
                                    celda5.setCellValue(registro.getInteresRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                            .add(registro.getInteresRelacionadoDependiente());

                                    celda6 = filaOnce.createCell(indiceColumnaAporte);
                                    celda6.setCellStyle(style2);
                                    celda6.setCellValue(registro.getTotalRelacionadoDependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                            .add(registro.getTotalRelacionadoDependiente());

                                    celda7 = filaOnce.createCell(indiceColumnaAporte);
                                    celda7.setCellStyle(style2);
                                    celda7.setCellValue(registro.getMontoRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                            .add(registro.getMontoRegistradoIndependiente());

                                    celda8 = filaOnce.createCell(indiceColumnaAporte);
                                    celda8.setCellStyle(style2);
                                    celda8.setCellValue(registro.getInteresRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                            .add(registro.getInteresRegistradoIndependiente());

                                    celda9 = filaOnce.createCell(indiceColumnaAporte);
                                    celda9.setCellStyle(style2);
                                    celda9.setCellValue(registro.getTotalRegistradoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                            .add(registro.getTotalRegistradoIndependiente());

                                    celda10 = filaOnce.createCell(indiceColumnaAporte);
                                    celda10.setCellStyle(style2);
                                    celda10.setCellValue(registro.getMontoRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                            .add(registro.getMontoRelacionadoIndependiente());

                                    celda11 = filaOnce.createCell(indiceColumnaAporte);
                                    celda11.setCellStyle(style2);
                                    celda11.setCellValue(registro.getInteresRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                            .add(registro.getInteresRelacionadoIndependiente());

                                    celda12 = filaOnce.createCell(indiceColumnaAporte);
                                    celda12.setCellStyle(style2);
                                    celda12.setCellValue(registro.getTotalRelacionadoIndependiente().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                            .add(registro.getTotalRelacionadoIndependiente());

                                    celda13 = filaOnce.createCell(indiceColumnaAporte);
                                    celda13.setCellStyle(style2);
                                    celda13.setCellValue(registro.getMontoRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRegistradoPensionadoSub = montoRegistradoPensionadoSub.add(registro.getMontoRegistradoPensionado());

                                    celda14 = filaOnce.createCell(indiceColumnaAporte);
                                    celda14.setCellStyle(style2);
                                    celda14.setCellValue(registro.getInteresRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                            .add(registro.getInteresRegistradoPensionado());

                                    celda15 = filaOnce.createCell(indiceColumnaAporte);
                                    celda15.setCellStyle(style2);
                                    celda15.setCellValue(registro.getTotalRegistradoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRegistradoPensionadoSub = totalRegistradoPensionadoSub.add(registro.getTotalRegistradoPensionado());

                                    celda16 = filaOnce.createCell(indiceColumnaAporte);
                                    celda16.setCellStyle(style2);
                                    celda16.setCellValue(registro.getMontoRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub.add(registro.getMontoRelacionadoPensionado());

                                    celda17 = filaOnce.createCell(indiceColumnaAporte);
                                    celda17.setCellStyle(style2);
                                    celda17.setCellValue(registro.getInteresRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                            .add(registro.getInteresRelacionadoPensionado());

                                    celda18 = filaOnce.createCell(indiceColumnaAporte);
                                    celda18.setCellStyle(style2);
                                    celda18.setCellValue(registro.getTotalRelacionadoPensionado().doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;
                                    totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub.add(registro.getTotalRelacionadoPensionado());

                                    BigDecimal montoRegistrado = BigDecimal.ZERO;
                                    BigDecimal interesRegistrado = BigDecimal.ZERO;
                                    BigDecimal totalRegistrado = BigDecimal.ZERO;
                                    BigDecimal montoRelacionado = BigDecimal.ZERO;
                                    BigDecimal interesRelacionado = BigDecimal.ZERO;
                                    BigDecimal totalRelacionado = BigDecimal.ZERO;

                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                    montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                    montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                    interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoDependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoIndependiente());
                                    interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                    totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                    totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                    celda19 = filaOnce.createCell(indiceColumnaAporte);
                                    celda19.setCellStyle(style2);
                                    celda19.setCellValue(montoRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda20 = filaOnce.createCell(indiceColumnaAporte);
                                    celda20.setCellStyle(style2);
                                    celda20.setCellValue(interesRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda21 = filaOnce.createCell(indiceColumnaAporte);
                                    celda21.setCellStyle(style2);
                                    celda21.setCellValue(totalRegistrado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda22 = filaOnce.createCell(indiceColumnaAporte);
                                    celda22.setCellStyle(style2);
                                    celda22.setCellValue(montoRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda23 = filaOnce.createCell(indiceColumnaAporte);
                                    celda23.setCellStyle(style2);
                                    celda23.setCellValue(interesRelacionado.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celda24 = filaOnce.createCell(indiceColumnaAporte);
                                    celda24.setCellStyle(style2);
                                    celda24.setCellValue(totalRelacionado.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                            }


                            logger.info("contador ??? " + contador);

                            if (contador == 7) {
                                // "Aportes - Devoluciones" fila seis
                                if (periodo.equals("Resumen Independientes")) {
                                    CellStyle style3 = libro.createCellStyle();
                                    filaOcho.setRowNum(indiceFila + 7);
                                    Cell celdaAD1 = filaOcho.createCell(indiceColumnaAporte);
                                    style3.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaAD1.setCellStyle(style3);
                                    celdaAD1.setCellValue(montoRegistradoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD2 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD2.setCellStyle(style3);
                                    celdaAD2.setCellValue(interesRegistradoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD3 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD3.setCellStyle(style3);
                                    celdaAD3.setCellValue(totalRegistradoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD4 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD4.setCellStyle(style3);
                                    celdaAD4.setCellValue(montoRelacionadoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD5 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD5.setCellStyle(style3);
                                    celdaAD5.setCellValue(interesRelacionadoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD6 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD6.setCellStyle(style3);
                                    celdaAD6.setCellValue(totalRelacionadoIndependienteAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD7 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD7.setCellStyle(style3);
                                    celdaAD7.setCellValue(montoRegistradoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD8 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD8.setCellStyle(style3);
                                    celdaAD8.setCellValue(interesRegistradoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD9 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD9.setCellStyle(style3);
                                    celdaAD9.setCellValue(totalRegistradoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD10 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD10.setCellStyle(style3);
                                    celdaAD10.setCellValue(montoRelacionadoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD11 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD11.setCellStyle(style3);
                                    celdaAD11.setCellValue(interesRelacionadoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD12 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD12.setCellStyle(style3);
                                    celdaAD12.setCellValue(totalRelacionadoIndependienteAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    BigDecimal montoRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal interesRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal totalRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal montoRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal interesRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal totalRelacionadoAD = BigDecimal.ZERO;

                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoIndependienteAD02);
                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoIndependienteAD006);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoIndependienteAD02);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoIndependienteAD006);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoIndependienteAD02);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoIndependienteAD006);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoIndependienteAD02);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoIndependienteAD006);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoIndependienteAD02);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoIndependienteAD006);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoIndependienteAD02);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoIndependienteAD006);

                                    Cell celdaAD19 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD19.setCellStyle(style3);
                                    celdaAD19.setCellValue(montoRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD20 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD20.setCellStyle(style3);
                                    celdaAD20.setCellValue(interesRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD21 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD21.setCellStyle(style3);
                                    celdaAD21.setCellValue(totalRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD22 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD22.setCellStyle(style3);
                                    celdaAD22.setCellValue(montoRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD23 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD23.setCellStyle(style3);
                                    celdaAD23.setCellValue(interesRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD24 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD24.setCellStyle(style3);
                                    celdaAD24.setCellValue(totalRelacionadoAD.doubleValue());

                                    indiceColumnaAporte = 3;

                                    // "Subtotal" fila diez
                                    CellStyle style4 = libro.createCellStyle();
                                    filaDoce.setRowNum(indiceFila + 11);
                                    celdaSub1 = filaDoce.createCell(indiceColumnaAporte);
                                    style4.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaSub1.setCellStyle(style4);
                                    celdaSub1.setCellValue(montoRegistradoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub2 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub2.setCellStyle(style4);
                                    celdaSub2.setCellValue(interesRegistradoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub3 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub3.setCellStyle(style4);
                                    celdaSub3.setCellValue(totalRegistradoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub4 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub4.setCellStyle(style4);
                                    celdaSub4.setCellValue(montoRelacionadoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub5 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub5.setCellStyle(style4);
                                    celdaSub5.setCellValue(interesRelacionadoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub6 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub6.setCellStyle(style4);
                                    celdaSub6.setCellValue(totalRelacionadoIndependienteSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub7 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub7.setCellStyle(style4);
                                    celdaSub7.setCellValue(montoRegistradoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub8 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub8.setCellStyle(style4);
                                    celdaSub8.setCellValue(interesRegistradoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub9 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub9.setCellStyle(style4);
                                    celdaSub9.setCellValue(totalRegistradoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub10 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub10.setCellStyle(style4);
                                    celdaSub10.setCellValue(montoRelacionadoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub11 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub11.setCellStyle(style4);
                                    celdaSub11.setCellValue(interesRelacionadoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub12 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub12.setCellStyle(style4);
                                    celdaSub12.setCellValue(totalRelacionadoIndependienteSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoIndependienteSub02);
                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoIndependienteSub006);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoIndependienteSub02);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoIndependienteSub006);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoIndependienteSub02);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoIndependienteSub006);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoIndependienteSub02);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoIndependienteSub006);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoIndependienteSub02);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoIndependienteSub006);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoIndependienteSub02);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoIndependienteSub006);

                                    celdaSub19 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub19.setCellStyle(style4);
                                    celdaSub19.setCellValue(montoRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub20 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub20.setCellStyle(style4);
                                    celdaSub20.setCellValue(interesRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub21 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub21.setCellStyle(style4);
                                    celdaSub21.setCellValue(totalRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub22 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub22.setCellStyle(style4);
                                    celdaSub22.setCellValue(montoRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub23 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub23.setCellStyle(style4);
                                    celdaSub23.setCellValue(interesRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub24 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub24.setCellStyle(style4);
                                    celdaSub24.setCellValue(totalRelacionadoSub.doubleValue());

                                    indiceColumnaAporte = 3;
                                } else  if (periodo.equals("Resumen pensionados")) {
                                    CellStyle style3 = libro.createCellStyle();
                                    filaOcho.setRowNum(indiceFila + 7);
                                    Cell celdaAD1 = filaOcho.createCell(indiceColumnaAporte);
                                    style3.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaAD1.setCellStyle(style3);
                                    celdaAD1.setCellValue(montoRegistradoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD2 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD2.setCellStyle(style3);
                                    celdaAD2.setCellValue(interesRegistradoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD3 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD3.setCellStyle(style3);
                                    celdaAD3.setCellValue(totalRegistradoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD4 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD4.setCellStyle(style3);
                                    celdaAD4.setCellValue(montoRelacionadoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD5 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD5.setCellStyle(style3);
                                    celdaAD5.setCellValue(interesRelacionadoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD6 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD6.setCellStyle(style3);
                                    celdaAD6.setCellValue(totalRelacionadoPensionadosAD02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD7 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD7.setCellStyle(style3);
                                    celdaAD7.setCellValue(montoRegistradoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD8 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD8.setCellStyle(style3);
                                    celdaAD8.setCellValue(interesRegistradoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD9 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD9.setCellStyle(style3);
                                    celdaAD9.setCellValue(totalRegistradoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD10 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD10.setCellStyle(style3);
                                    celdaAD10.setCellValue(montoRelacionadoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD11 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD11.setCellStyle(style3);
                                    celdaAD11.setCellValue(interesRelacionadoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD12 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD12.setCellStyle(style3);
                                    celdaAD12.setCellValue(totalRelacionadoPensionadosAD006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    BigDecimal montoRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal interesRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal totalRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal montoRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal interesRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal totalRelacionadoAD = BigDecimal.ZERO;

                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoPensionadosAD02);
                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoPensionadosAD006);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoPensionadosAD02);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoPensionadosAD006);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoPensionadosAD02);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoPensionadosAD006);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoPensionadosAD02);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoPensionadosAD006);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoPensionadosAD02);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoPensionadosAD006);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoPensionadosAD02);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoPensionadosAD006);

                                    Cell celdaAD19 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD19.setCellStyle(style3);
                                    celdaAD19.setCellValue(montoRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD20 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD20.setCellStyle(style3);
                                    celdaAD20.setCellValue(interesRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD21 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD21.setCellStyle(style3);
                                    celdaAD21.setCellValue(totalRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD22 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD22.setCellStyle(style3);
                                    celdaAD22.setCellValue(montoRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD23 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD23.setCellStyle(style3);
                                    celdaAD23.setCellValue(interesRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD24 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD24.setCellStyle(style3);
                                    celdaAD24.setCellValue(totalRelacionadoAD.doubleValue());

                                    indiceColumnaAporte = 3;

                                    // "Subtotal" fila diez
                                    CellStyle style4 = libro.createCellStyle();
                                    filaDoce.setRowNum(indiceFila + 11);
                                    celdaSub1 = filaDoce.createCell(indiceColumnaAporte);
                                    style4.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaSub1.setCellStyle(style4);
                                    celdaSub1.setCellValue(montoRegistradoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub2 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub2.setCellStyle(style4);
                                    celdaSub2.setCellValue(interesRegistradoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub3 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub3.setCellStyle(style4);
                                    celdaSub3.setCellValue(totalRegistradoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub4 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub4.setCellStyle(style4);
                                    celdaSub4.setCellValue(montoRelacionadoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub5 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub5.setCellStyle(style4);
                                    celdaSub5.setCellValue(interesRelacionadoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub6 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub6.setCellStyle(style4);
                                    celdaSub6.setCellValue(totalRelacionadoPensionadosSub02.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub7 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub7.setCellStyle(style4);
                                    celdaSub7.setCellValue(montoRegistradoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub8 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub8.setCellStyle(style4);
                                    celdaSub8.setCellValue(interesRegistradoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub9 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub9.setCellStyle(style4);
                                    celdaSub9.setCellValue(totalRegistradoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub10 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub10.setCellStyle(style4);
                                    celdaSub10.setCellValue(montoRelacionadoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub11 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub11.setCellStyle(style4);
                                    celdaSub11.setCellValue(interesRelacionadoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub12 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub12.setCellStyle(style4);
                                    celdaSub12.setCellValue(totalRelacionadoPensionadosSub006.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoPensionadosSub02);
                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoPensionadosSub006);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoPensionadosSub02);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoPensionadosSub006);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoPensionadosSub02);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoPensionadosSub006);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoPensionadosSub02);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoPensionadosSub006);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoPensionadosSub02);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoPensionadosSub006);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoPensionadosSub02);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoPensionadosSub006);

                                    celdaSub19 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub19.setCellStyle(style4);
                                    celdaSub19.setCellValue(montoRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub20 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub20.setCellStyle(style4);
                                    celdaSub20.setCellValue(interesRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub21 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub21.setCellStyle(style4);
                                    celdaSub21.setCellValue(totalRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub22 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub22.setCellStyle(style4);
                                    celdaSub22.setCellValue(montoRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub23 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub23.setCellStyle(style4);
                                    celdaSub23.setCellValue(interesRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub24 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub24.setCellStyle(style4);
                                    celdaSub24.setCellValue(totalRelacionadoSub.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                                else {

                                    CellStyle style3 = libro.createCellStyle();
                                    filaOcho.setRowNum(indiceFila + 7);
                                    Cell celdaAD1 = filaOcho.createCell(indiceColumnaAporte);
                                    style3.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaAD1.setCellStyle(style3);
                                    celdaAD1.setCellValue(montoRegistradoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD2 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD2.setCellStyle(style3);
                                    celdaAD2.setCellValue(interesRegistradoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD3 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD3.setCellStyle(style3);
                                    celdaAD3.setCellValue(totalRegistradoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD4 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD4.setCellStyle(style3);
                                    celdaAD4.setCellValue(montoRelacionadoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD5 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD5.setCellStyle(style3);
                                    celdaAD5.setCellValue(interesRelacionadoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD6 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD6.setCellStyle(style3);
                                    celdaAD6.setCellValue(totalRelacionadoDependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD7 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD7.setCellStyle(style3);
                                    celdaAD7.setCellValue(montoRegistradoInependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD8 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD8.setCellStyle(style3);
                                    celdaAD8.setCellValue(interesRegistradoInependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD9 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD9.setCellStyle(style3);
                                    celdaAD9.setCellValue(totalRegistradoIndependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD10 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD10.setCellStyle(style3);
                                    celdaAD10.setCellValue(montoRelacionadoIndependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD11 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD11.setCellStyle(style3);
                                    celdaAD11.setCellValue(interesRelacionadoIndependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD12 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD12.setCellStyle(style3);
                                    celdaAD12.setCellValue(totalRelacionadoIndependienteAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD13 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD13.setCellStyle(style3);
                                    celdaAD13.setCellValue(montoRegistradoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD14 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD14.setCellStyle(style3);
                                    celdaAD14.setCellValue(interesRegistradoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD15 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD15.setCellStyle(style3);
                                    celdaAD15.setCellValue(totalRegistradoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD16 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD16.setCellStyle(style3);
                                    celdaAD16.setCellValue(montoRelacionadoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD17 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD17.setCellStyle(style3);
                                    celdaAD17.setCellValue(interesRelacionadoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD18 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD18.setCellStyle(style3);
                                    celdaAD18.setCellValue(totalRelacionadoPensionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    BigDecimal montoRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal interesRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal totalRegistradoAD = BigDecimal.ZERO;
                                    BigDecimal montoRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal interesRelacionadoAD = BigDecimal.ZERO;
                                    BigDecimal totalRelacionadoAD = BigDecimal.ZERO;

                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoDependienteAD);
                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoInependienteAD);
                                    montoRegistradoAD = montoRegistradoAD.add(montoRegistradoPensionadoAD);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoDependienteAD);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoIndependienteAD);
                                    montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoPensionadoAD);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoDependienteAD);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoInependienteAD);
                                    interesRegistradoAD = interesRegistradoAD.add(interesRegistradoPensionadoAD);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoDependienteAD);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoIndependienteAD);
                                    interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoPensionadoAD);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoDependienteAD);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoIndependienteAD);
                                    totalRegistradoAD = totalRegistradoAD.add(totalRegistradoPensionadoAD);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoDependienteAD);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoIndependienteAD);
                                    totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoPensionadoAD);

                                    Cell celdaAD19 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD19.setCellStyle(style3);
                                    celdaAD19.setCellValue(montoRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD20 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD20.setCellStyle(style3);
                                    celdaAD20.setCellValue(interesRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD21 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD21.setCellStyle(style3);
                                    celdaAD21.setCellValue(totalRegistradoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD22 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD22.setCellStyle(style3);
                                    celdaAD22.setCellValue(montoRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD23 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD23.setCellStyle(style3);
                                    celdaAD23.setCellValue(interesRelacionadoAD.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    Cell celdaAD24 = filaOcho.createCell(indiceColumnaAporte);
                                    celdaAD24.setCellStyle(style3);
                                    celdaAD24.setCellValue(totalRelacionadoAD.doubleValue());

                                    indiceColumnaAporte = 3;

                                    // "Subtotal" fila diez
                                    CellStyle style4 = libro.createCellStyle();
                                    filaDoce.setRowNum(indiceFila + 11);
                                    celdaSub1 = filaDoce.createCell(indiceColumnaAporte);
                                    style4.setAlignment(HorizontalAlignment.RIGHT);
                                    celdaSub1.setCellStyle(style4);
                                    celdaSub1.setCellValue(montoRegistradoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub2 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub2.setCellStyle(style4);
                                    celdaSub2.setCellValue(interesRegistradoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub3 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub3.setCellStyle(style4);
                                    celdaSub3.setCellValue(totalRegistradoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub4 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub4.setCellStyle(style4);
                                    celdaSub4.setCellValue(montoRelacionadoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub5 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub5.setCellStyle(style4);
                                    celdaSub5.setCellValue(interesRelacionadoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub6 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub6.setCellStyle(style4);
                                    celdaSub6.setCellValue(totalRelacionadoDependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub7 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub7.setCellStyle(style4);
                                    celdaSub7.setCellValue(montoRegistradoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub8 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub8.setCellStyle(style4);
                                    celdaSub8.setCellValue(interesRegistradoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub9 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub9.setCellStyle(style4);
                                    celdaSub9.setCellValue(totalRegistradoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub10 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub10.setCellStyle(style4);
                                    celdaSub10.setCellValue(montoRelacionadoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub11 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub11.setCellStyle(style4);
                                    celdaSub11.setCellValue(interesRelacionadoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub12 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub12.setCellStyle(style4);
                                    celdaSub12.setCellValue(totalRelacionadoIndependienteSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub13 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub13.setCellStyle(style4);
                                    celdaSub13.setCellValue(montoRegistradoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub14 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub14.setCellStyle(style4);
                                    celdaSub14.setCellValue(interesRegistradoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub15 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub15.setCellStyle(style4);
                                    celdaSub15.setCellValue(totalRegistradoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub16 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub16.setCellStyle(style4);
                                    celdaSub16.setCellValue(montoRelacionadoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub17 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub17.setCellStyle(style4);
                                    celdaSub17.setCellValue(interesRelacionadoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub18 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub18.setCellStyle(style4);
                                    celdaSub18.setCellValue(totalRelacionadoPensionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoDependienteSub);
                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoIndependienteSub);
                                    montoRegistradoSub = montoRegistradoSub.add(montoRegistradoPensionadoSub);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoDependienteSub);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoIndependienteSub);
                                    montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoPensionadoSub);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoDependienteSub);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoIndependienteSub);
                                    interesRegistradoSub = interesRegistradoSub.add(interesRegistradoPensionadoSub);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoDependienteSub);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoIndependienteSub);
                                    interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoPensionadoSub);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoDependienteSub);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoIndependienteSub);
                                    totalRegistradoSub = totalRegistradoSub.add(totalRegistradoPensionadoSub);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoDependienteSub);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoIndependienteSub);
                                    totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoPensionadoSub);

                                    celdaSub19 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub19.setCellStyle(style4);
                                    celdaSub19.setCellValue(montoRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub20 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub20.setCellStyle(style4);
                                    celdaSub20.setCellValue(interesRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub21 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub21.setCellStyle(style4);
                                    celdaSub21.setCellValue(totalRegistradoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub22 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub22.setCellStyle(style4);
                                    celdaSub22.setCellValue(montoRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub23 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub23.setCellStyle(style4);
                                    celdaSub23.setCellValue(interesRelacionadoSub.doubleValue());
                                    indiceColumnaAporte = indiceColumnaAporte + 1;

                                    celdaSub24 = filaDoce.createCell(indiceColumnaAporte);
                                    celdaSub24.setCellStyle(style4);
                                    celdaSub24.setCellValue(totalRelacionadoSub.doubleValue());

                                    indiceColumnaAporte = 3;
                                }
                            }
                        }
                    }
                }
                indiceFila = indiceFila + 13;
            }
            logger.debug(
                    "Finaliza el método construirHojaCuadroResumen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
            return pagina;
        } catch (Exception ex) {
            logger.error("Finaliza del método construirHojaCuadroResumen: Ocurrio un error al generar el archivo excel" + ex);
        }
                return null;
    }

    /**
     * @param detalleRegistroDTO
     * @return
     */
    private static String obtenerIdsAportes(DetalleRegistroDTO detalleRegistroDTO) {
        StringBuilder ids = new StringBuilder();
        int indice = 0;
        for (ResultadoDetalleRegistroDTO detalle : detalleRegistroDTO.getResultadoporRegistros()) {
            if (indice == 0) {
                indice++;
            }
            else {
                ids.append("|");
            }
            ids.append(detalle.getAportante().getNumeroOperacion());
        }
        return ids.toString();
    }
    
       /**
     * Genera la data correspondiente a los aportes
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataAportes(DetalleRegistroDTO detalleRegistroDTO) {

        logger.debug("Inicia el método generarDataAportes(DetalleRegistroDTO)");
        logger.info("Inicia el método generarDataAportes(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();


        String[] encabezadoAportante = { 
                
                "Numero de operacion del Recaudo",
                "Tipo Aportante", 
                "Tipo registro aporte",
                "Tipo identificacion del aportante", 
                "Numero de identificacion del Aportante",
                "Razon Social – Nombre Aportante",
                "Digito de verificacion del Aportante",
                "Periodo aportado", 
                "Fecha de Recaudo", 
                "Numero de planilla o Numero asignado si es manual", 
                "Valor recaudo Caja",
                "Valor intereses de mora", 
                "Total Recaudo", 
                "Usuario que ejecutó la operacion",
                "Oportunidad de pago",
                "Estado del aportante",
                "Codigo banco recaudador",
                "Cuenta bancaria recaudador",
                "Sucursal de la empresa",
                "Tarifa del aportante",
                "Fecha de procesamiento",
                "Método de Recaudo",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero",
                "Motivo de retiro"
         };

        String[] labelAportante = { "Aportante" };

        Date inicio = new Date();
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            String[] filaAportante = { 
                    resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(), resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(), resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaProcesamiento() != null ? new Date(resultado.getAportante().getFechaProcesamiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getModalidadRecaudo().name(),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),
                    resultado.getAportante().getMotivoDeRetiro() == null ? "": resultado.getAportante().getMotivoDeRetiro()
             
                };

            data.add(filaAportante);

            }
        logger.debug("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        return data;
    }

    /**
     * Genera la data correspondiente a los aportes
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataAportes2(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método generarDataAportes2(DetalleRegistroDTO)");
        logger.info("Inicia el método generarDataAportes2(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = {
                "Numero de operacion del Recaudo",
                "Tipo registro aporte",
                "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante",
                "Primer Nombre del cotizante",
                "Segundo Nombre del cotizante",
                "Primer apellido del cotizante",
                "Segundo Apellido del cotizante",
                "Periodo aportado",
                "Fecha de Recaudo",
                "Tarifa",
                "Valor aporte nivel 2",
                "Valor intereses de mora nivel 2",
                "Total recaudo",
                "Usuario que ejecutó la operacion",
                "Numero de planilla o Numero asignado si es manual",
                "Fecha del registro de la novedad",
                "Tipo identificacion del aportante",
                "Numero de identificacion del Aportante",
                "Oportunidad de pago",
                "Estado del aportante",
                "Codigo banco recaudador",
                "Cuenta bancaria recaudador",
                "Sucursal de la empresa",
                "Tarifa del aportante",
                "Fecha de procesamiento",
                "Método de Recaudo",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero"
        };

        String[] labelCotizantes = { "Cotizantes" };

        Date inicio = new Date();
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {


                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                        String[] filaCotizante = {

                            cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(), cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(), cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(), cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(resultado.getAportante().getFechaPago() != null ? new Date(resultado.getAportante().getFechaPago().getTime()): new Date().getTime()),
                            cotizante.getTarifa().toString(),
                            cotizante.getMonto().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            resultado.getAportante().getNumeroPlanilla(),
                            FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true),
                            resultado.getAportante().getTipoIdentificacionAportante().name(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getCodigoBancoRecaudador(),
                            resultado.getAportante().getCuentaBancariaRecaudo(),
                            resultado.getAportante().getSucursalEmpresa(),
                            resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                            formatoFechaCierre.format(resultado.getAportante().getFechaProcesamiento() != null ? new Date(resultado.getAportante().getFechaProcesamiento().getTime()) : new Date().getTime()),
                            resultado.getAportante().getModalidadRecaudo().name(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()
                    };

                    data.add(filaCotizante);
                }
            }
        }
        logger.debug("Finaliza el método construirHojaAportes2(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaAportes2(DetalleRegistroDTO)");
        return data;

    }

    public static List<String[]> generarDataExtratemporaneo(DetalleRegistroDTO detalleRegistroDTO) {

        logger.debug("Inicia el método generarDataExtratemporaneo(DetalleRegistroDTO)");
        logger.info("Inicia el método generarDataExtratemporaneo(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();


        String[] encabezadoAportante = {

                "Numero de operacion del Recaudo",
                "Tipo Aportante",
                "Tipo registro aporte",
                "Tipo identificacion del aportante",
                "Numero de identificacion del Aportante",
                "Razon Social – Nombre Aportante",
                "Digito de verificacion del Aportante",
                "Periodo aportado",
                "Fecha de Recaudo",
                "Numero de planilla o Numero asignado si es manual",
                "Valor recaudo Caja",
                "Valor intereses de mora",
                "Total Recaudo",
                "Usuario que ejecutó la operacion",
                "Oportunidad de pago",
                "Estado del aportante",
                "Codigo banco recaudador",
                "Cuenta bancaria recaudador",
                "Sucursal de la empresa",
                "Tarifa del aportante",
                "Fecha de procesamiento",
                "Método de Recaudo",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero",
                "Motivo de retiro"
        };

        String[] labelAportante = {"Aportante"};

        Date inicio = new Date();
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            logger.info("Inicia el método generarDataExtratemporaneo(DetalleRegistroDTO)");
            logger.debug("Inicia el método generarDataExtratemporaneo(DetalleRegistroDTO)");

            String[] filaAportante = {
                    resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(), resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(), resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()) : new Date().getTime()),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getInteres().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTotalAporte().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()) : new Date().getTime()),
                    resultado.getAportante().getModalidadRecaudo().name(),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),
                    resultado.getAportante().getMotivoDeRetiro() == null ? "": resultado.getAportante().getMotivoDeRetiro()
            };

            data.add(filaAportante);

        }
        logger.debug("Finaliza el método generarDataExtratemporaneo(DetalleRegistroDTO)");
        logger.info("Finaliza el método generarDataExtratemporaneo(DetalleRegistroDTO)");
        return data;
    }

    public static List<String[]> generarDataExtratemporaneos2(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método generarDataExtratemporaneos2(DetalleRegistroDTO)");
        logger.info("Inicia el método generarDataExtratemporaneos2(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = {
                "Numero de operacion del Recaudo",
                "Tipo registro aporte",
                "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante",
                "Primer Nombre del cotizante",
                "Segundo Nombre del cotizante",
                "Primer apellido del cotizante",
                "Segundo Apellido del cotizante",
                "Periodo aportado",
                "Fecha de Recaudo",
                "Tarifa",
                "Valor aporte nivel 2",
                "Valor intereses de mora nivel 2",
                "Total recaudo",
                "Usuario que ejecutó la operacion",
                "Fecha del registro de la novedad",
                "Tipo identificacion del aportante",
                "Numero de identificacion del Aportante",
                "Oportunidad de pago",
                "Estado del aportante",
                "Codigo banco recaudador",
                "Cuenta bancaria recaudador",
                "Sucursal de la empresa",
                "Tarifa del aportante",
                "Fecha de procesamiento",
                "Método de Recaudo",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero",
                "Motivo de retiro"
        };

        String[] labelCotizantes = {"Cotizantes"};

        Date inicio = new Date();
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {


                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                    String[] filaCotizante = {

                            cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(), cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(), cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(), cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(resultado.getAportante().getFechaPago() != null ? new Date(resultado.getAportante().getFechaPago().getTime()) : new Date().getTime()),
                            cotizante.getTarifa().toString(),
                            cotizante.getMonto().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true),
                            resultado.getAportante().getTipoIdentificacionAportante().name(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getCodigoBancoRecaudador(),
                            resultado.getAportante().getCuentaBancariaRecaudo(),
                            resultado.getAportante().getSucursalEmpresa(),
                            resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                            formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()) : new Date().getTime()),
                            resultado.getAportante().getModalidadRecaudo().name(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()

                    };


                    data.add(filaCotizante);
                }
            }
        }
        logger.debug("Finaliza el método generarDataExtratemporaneos2(DetalleRegistroDTO)");
        logger.info("Finaliza el método generarDataExtratemporaneos2(DetalleRegistroDTO)");
        return data;

    }

    public static List<String[]> generarDataOtrosIngresosAportante(DetalleRegistroDTO detalleRegistroDTO) {

        List<String[]> data = new ArrayList<String[]>();


        String[] encabezadoAportante = { 
                
                "Numero de operacion del Recaudo",
                "Tipo Aportante", 
                "Tipo registro aporte",
                "Tipo identificacion del aportante", 
                "Numero de identificacion del Aportante",
                "Razon Social – Nombre Aportante",
                "Digito de Verificacion",
                "Periodo aportado", 
                "Fecha de Recaudo", 
                "Numero de planilla o Numero asignado si es manual", 
                "Valor recaudo Caja",
                "Valor intereses de mora", 
                "Total Recaudo", 
                "Usuario que ejecutó la operacion",
                "Fecha de Reconocimiento",
                "Oportunidad de pago",
                "Estado del aportante",
                "Codigo banco recaudador",
                "Cuenta bancaria recaudador",
                "Sucursal de la empresa",
                "Tarifa del aportante",
                "Fecha de procesamiento",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero"
         };

        String[] labelAportante = { "Aportante" };

        Date inicio = new Date();
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
                 String[] filaAportante = {
                    resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(), resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(), resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaProcesamiento() != null ? new Date(resultado.getAportante().getFechaProcesamiento().getTime()) : new Date().getTime()),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),

            };


            data.add(filaAportante);
        }
        logger.debug("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        return data;
    }
    public static List<String[]> generarDataOtrosIngresosCotizante(DetalleRegistroDTO detalleRegistroDTO) {

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = { 
                "Numero de operacion del Recaudo", 
                "Tipo registro aporte", 
                "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante",
                "Primer Nombre del cotizante", 
                "Segundo Nombre del cotizante",
                "Primer apellido del cotizante",
                "Segundo Apellido del cotizante", 
                "Periodo aportado", 
                "Fecha de Recaudo", 
                "Tarifa",
                "Valor aporte nivel 2",
                "Valor intereses de mora nivel 2", 
                "Total recaudo", 
                "Usuario que ejecutó la operacion",
                "Número de planilla o número asignado si es manual",
                "Fecha del registro de la novedad",
                "Numero de operacion del Recaudo",
                "Numero de identificacion del Aportante",
                "Oportunidad de pago",
                "Estado del aportante",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero"
        };

        String[] labelCotizantes = { "Cotizantes" };

        Date inicio = new Date();
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                        String[] filaCotizante = {

                            cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(), cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(),
                            cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(),
                            cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo() != null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            cotizante.getTarifa().setScale(3, RoundingMode.CEILING).toString(),
                            cotizante.getMonto().stripTrailingZeros().toString(),
                            cotizante.getInteres().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            resultado.getAportante().getNumeroPlanilla(),
                            FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()
                    };

                    data.add(filaCotizante);
                }
            }
        }
        logger.debug("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaAportes(DetalleRegistroDTO)");
        return data;
    }
    
    /**
     * Genera la data correspondiente a las devoluciones
     * @param detalleRegistroDTO
     * @return
     */


    public static List<String[]> generarDataDevolucionesCotizante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método construirHojaDevoluciones(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = { "Numero de operacion de la devolución", "Tipo registro aporte",
                "Tipo identificacion del cotizante", "Numero de identificacion del cotizante", "Primer Nombre del cotizante",
                "Segundo Nombre del cotizante", "Primer apellido del cotizante", "Segundo Apellido del cotizante", "Periodo devolución",
                "Fecha de devolución", "Numero de Solicitud de Devolución", "Tarifa", "Valor devolución Caja", "Valor devolución intereses de mora", "Total devolución",
                "Usuario que ejecutó la operacion para cotizante", "número de planilla o número asignado si es manual", "Fecha del registro de la novedad", "Numero de operacion del Recaudo", "Numero de identificacion del Aportante","Oportunidad de pago","Estado del aportante",
                "Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero"};

        String[] labelCotizantes = { "Cotizantes" };
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
                    String[] filaCotizante = { cotizante.getNumeroOperacion().toString(),
                            cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(),
                            cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(), cotizante.getSegundoNombre(),
                            cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(), cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo() != null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            resultado.getAportante().getNumeroSolicitudDevolucion(),
                            cotizante.getTarifa().setScale(3, RoundingMode.CEILING).toString(),
                            cotizante.getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            resultado.getAportante().getNumeroPlanilla(),
                            FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()
                         };

                    data.add(filaCotizante);
                }
            }
        }
        logger.debug(
                "Finaliza el método construirHojaDevoluciones(DetalleRegistroDTO)");
        return data;
    }
    /**
     * Genera la data correspondiente a las devoluciones
     * @param detalleRegistroDTO
     * @return
     */


     public static List<String[]> generarDataDevolucionesAportante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método construirHojaDevoluciones(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoAportante = { "Numero de operacion de la devolución", "Tipo Aportante", "Tipo registro aporte",
                "Tipo identificacion del aportante", "Numero de identificacion del Aportante", "Razon Social – Nombre Aportante", "Digito de verificacion Aportante",
                "Periodo devolución", "Fecha de Ajuste", "Numero de planilla o Numero asignado si es manual", "Numero Solicitud Devolucion","Valor devolución Caja",
                "Valor devolución intereses de mora", "Total devolución", "Usuario que ejecutó la operacion", "Destino",
                "Valor Gastos Bancarios", "Usuario que ejecutó la operacion", "Oportunidad de pago", "Estado del aportante",
                "Codigo banco recaudador", "Cuenta bancaria recaudador", "Sucursal de la empresa", "Tarifa del aportante",
                "Fecha de procesamiento","Fecha de Recaudo","Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero" };


        String[] labelAportante = { "Aportante" };
        String[] labelCotizantes = { "Cotizantes" };
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            String[] filaAportante = { resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(),
                    resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(),
                    resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaDevolucion() != null ? new Date(resultado.getAportante().getFechaDevolucion().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getNumeroSolicitudDevolucion(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getDestinatario().name(),
                    resultado.getAportante().getValorGastosBancarios().setScale(0, RoundingMode.CEILING).toString(),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero() ,
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero()

            };


            data.add(filaAportante);

        }
        logger.debug(
                "Finaliza el método construirHojaDevoluciones(DetalleRegistroDTO)");
        return data;
    }
    
    /**
     * Genera la data correspondiente a los aportes legalizados
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataLegalizadosAportante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método construirHojaLegalizados(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoAportante = { "Numero de operacion del Recaudo", "Tipo Aportante", "Tipo registro aporte",
                "Tipo identificacion del aportante", "Numero de identificacion del Aportante", "Razon Social – Nombre Aportante", "Digito de verificacion Aportante",
                "Periodo aportado", "Fecha de Recaudo", "Numero de planilla o Numero asignado si es manual", "Valor recaudo Caja",
                "Valor intereses de mora", "Total recaudo", "Usuario que ejecutó la operacion",
                "Oportunidad de pago", "Fecha de Reconocimiento","Estado del aportante",
                "Codigo banco recaudador", "Cuenta bancaria recaudador", "Sucursal de la empresa", "Tarifa del aportante",
                "Fecha de procesamiento", "Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero"};


        String[] labelAportante = { "Aportante" };
        String[] labelCotizantes = { "Cotizantes" };
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            String[] filaAportante = { resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(),
                    resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(),
                    resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getOportunidadDePago(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaProcesamiento() != null ? new Date(resultado.getAportante().getFechaProcesamiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),
                    resultado.getAportante().getMotivoDeRetiro() == null ? "": resultado.getAportante().getMotivoDeRetiro()
                };
            data.add(filaAportante);
        }
        logger.debug("Finaliza el método construirHojaLegalizados(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaLegalizados(DetalleRegistroDTO)");
        return data;
    }
     /**
     * Genera la data correspondiente a los aportes legalizados
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataLegalizadosCotizante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug("Inicia el método construirHojaLegalizados(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();


        String[] encabezadoCotizante = { "Numero de operacion del recaudo", "Tipo registro aporte", "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante", "Primer Nombre del cotizante", "Segundo Nombre del cotizante",
                "Primer apellido del cotizante", "Segundo Apellido del cotizante", "Periodo aportado", "Fecha de Recaudo", "Tarifa",
                "Valor Recaudo Caja", "Valor intereses de mora", "Total Recaudo", "Usuario que ejecutó la operacion para cotizante", "Número de planilla o número asignado si es manual",
                "Fecha del registro de la novedad", "Numero de operacion del Recaudo", "Numero de identificacion del Aportante","Oportunidad de pago","Estado del aportante",
                "Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero" };

        String[] labelAportante = { "Aportante" };
        String[] labelCotizantes = { "Cotizantes" };
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                    String[] filaCotizante = { cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(),
                            cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(), cotizante.getSegundoNombre(),
                            cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(),
                            cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo() != null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            cotizante.getTarifa().setScale(3, RoundingMode.CEILING).toString(),
                            cotizante.getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            resultado.getAportante().getNumeroPlanilla(),
                            FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()
                        };      
                        data.add(filaCotizante);
                
                }
            }
        }
        logger.debug("Finaliza el método construirHojaLegalizados(DetalleRegistroDTO)");
        logger.info("Finaliza el método construirHojaLegalizados(DetalleRegistroDTO)");
        return data;
    }
 
    
    /**
     * Genera la data correspondiente a las correciones anuladas
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesAnuladasAportante(DetalleRegistroDTO detalleRegistroDTO) {

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoAportante = { "Numero de operacion del Recaudo afectado", "Tipo Aportante", "Tipo registro aporte",
                "Tipo identificacion del aportante", "Numero de identificacion del Aportante", "Digito de verificacion aportante", "Razon Social – Nombre Aportante",
                 "Periodo corregido","Fecha de Recaudo", "Fecha de corrección", "Número asignado de la solicitud", "Numero de planilla o Numero asignado si es manual",
                "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección", "Usuario que ejecutó la operacion", "Oportunidad de pago", "Estado del aportante",
                "Codigo banco recaudador", "Cuenta bancaria recaudador", "Sucursal de la empresa", "Tarifa del aportante",
                "Fecha de procesamiento","Numero de operacion de la corrección", "Tipo de planilla", "Pagador por si mismo", "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero"};

        String[] labelAportante = { "Aportante" };

        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            String[] filaAportante = { resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(),
                    resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudoCorreccion()!= null ? new Date(resultado.getAportante().getFechaRecaudoCorreccion().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroSolicitudCorreccion(),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getNumeroOperacionCorreccion() != null ? resultado.getAportante().getNumeroOperacionCorreccion().toString() : "",
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),
                    resultado.getAportante().getMotivoDeRetiro() == null ? "": resultado.getAportante().getMotivoDeRetiro()
                        

                };

            data.add(filaAportante);
        }
        logger.debug(
                "Finaliza el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");
        return data;
    }
    /**
     * Genera la data correspondiente a las correciones anuladas
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesAnuladasCotizante(DetalleRegistroDTO detalleRegistroDTO) {


        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = { "Numero de operacion del recaudo", "Tipo registro aporte", "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante", "Primer Nombre del cotizante", "Segundo Nombre del cotizante",
                "Primer apellido del cotizante", "Segundo Apellido del cotizante", "Periodo corregido", "Fecha de Recaudo","Número asignado de la solicitud",
                "Tipo de transacción","Tarifa", "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección",
                "Usuario que ejecutó la operacion para cotizante", "Fecha del registro de la novedad",
                "Numero de operacion de la corrección", "Numero de operacion del Recaudo", "Numero de identificacion del Aportante","Oportunidad de pago","Estado del aportante",
                "Tipo de planilla",
                "Pagador por si mismo",
                "Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero",
                "Razón Social – Nombre Pagador por tercero"};

        String[] labelCotizantes = { "Cotizantes" };
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {

           

                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                    String[] filaCotizante = { cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(),
                            cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(),
                            cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(),
                            cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo()!= null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            resultado.getAportante().getNumeroSolicitudCorreccion(),
                            cotizante.getTipoTransaccion(),
                            cotizante.getTarifa().setScale(0, RoundingMode.CEILING).toString(),
                            cotizante.getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            formatoFechaCierre.format(cotizante.getFechaNovedad() != null ? new Date(cotizante.getFechaNovedad().getTime()): new Date().getTime()),
                            cotizante.getNumeroOperacionCorreccion().toString(),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()
                        };

                    data.add(filaCotizante);

                }
            }
        }
        logger.debug(
                "Finaliza el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");
        return data;
    }

    /**
     * Genera la data correspondiente a las correciones anuladas
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesALaAltaAportante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug(
                "Inicia el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoAportante = { "Numero de operacion del Recaudo afectado", "Tipo Aportante", "Tipo registro aporte",
                "Tipo identificacion del aportante", "Numero de identificacion del Aportante", "Razon Social – Nombre Aportante", "Digito de verificacion aportante",
                "Periodo corregido", "Fecha de Recaudo", "Fecha de corrección", "Numero de planilla ","Recaudo Original",
                "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección", "Usuario que ejecutó la operacion",
                "Numero de operacion de la corrección", "Oportunidad de pago", "Estado del aportante",
                "Codigo banco recaudador", "Cuenta bancaria recaudador", "Sucursal de la empresa", "Tarifa del aportante",
                "Fecha de procesamiento","Método de Recaudo","Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero", "Motivo de retiro"
        };

        String[] labelAportante = { "Aportante" };
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            String[] filaAportante = { resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(),
                    resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(),
                    resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudoCorreccion() != null ? new Date(resultado.getAportante().getFechaRecaudoCorreccion().getTime()): new Date().getTime()),
                    resultado.getAportante().getRecaudoOriginal(),
                    resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                    resultado.getAportante().getNumeroOperacionCorreccion() != null ? resultado.getAportante().getNumeroOperacionCorreccion().toString() : "",
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                    resultado.getAportante().getModalidadRecaudo().name(),
                    resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),
                    resultado.getAportante().getMotivoDeRetiro() == null ? "": resultado.getAportante().getMotivoDeRetiro()

                };

            data.add(filaAportante);

        }
        logger.debug(
                "Finaliza el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");
        return data;
    }
    /**
     * Genera la data correspondiente a las correciones anuladas
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesALaAltaCotizante(DetalleRegistroDTO detalleRegistroDTO) {
        logger.debug(
                "Inicia el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = { "Numero de operacion del recaudo", "Tipo registro aporte", "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante", "Primer Nombre del cotizante", "Segundo Nombre del cotizante",
                "Primer apellido del cotizante", "Segundo Apellido del cotizante", "Periodo corregido", "Fecha de Recaudo",
                "Tipo de transacción", "Tarifa", "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección",
                "Usuario que ejecutó la operacion para cotizante", "Número de planilla o número asignado si es manual", "Fecha del registro de la novedad",
                "Numero de operacion de la corrección", "Método de Recaudo", "Numero de operacion del Recaudo afectado", "Numero de identificacion del Aportante", "Oportunidad de pago", "Estado del aportante",
                "Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero", "Motivo de retiro"
        };

        String[] labelCotizantes = { "Cotizantes" };
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {


                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
                    String[] filaCotizante = { cotizante.getNumeroOperacion().toString(), cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(),
                            cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(),
                            cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(),
                            cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo() != null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            cotizante.getTipoTransaccion(),
                            cotizante.getTarifa().toString(),
                            cotizante.getMonto().setScale(2, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(2, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(2, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            resultado.getAportante().getNumeroPlanilla(),
                            formatoFechaCierre.format(new Date(cotizante.getFechaNovedad().getTime())),
                            cotizante.getNumeroOperacionCorreccion().toString(),
                            resultado.getAportante().getModalidadRecaudo().name(),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero(),                   
                        };

                    data.add(filaCotizante);

                }
            }
        }
        logger.debug(
                "Finaliza el método construirHojaCorreccionesAnulados(DetalleRegistroDTO)");
        return data;
    }
    
    /**
     * Genera la data correspondiente a las correciones de origen
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesOrigenAportante(DetalleRegistroDTO detalleRegistroDTO) {

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoAportante = { "Numero de operacion de la corrección", "Tipo Aportante", "Tipo registro aporte",
                "Tipo identificacion del aportante", "Numero de identificacion del Aportante", "Razon Social – Nombre Aportante",
                "Digito de verificacion Aportante",
                "Periodo del nuevo aporte","Numero asignado a la solicitud", "Fecha de Recaudo", "Fecha de corrección", "Numero de planilla o Numero asignado si es manual",
                "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección", "Usuario que ejecutó la operacion",
                "Oportunidad de pago", "Estado del aportante",
                "Codigo banco recaudador", "Cuenta bancaria recaudador", "Sucursal de la empresa", "Tarifa del aportante",
                "Fecha de procesamiento", "Tipo de planilla", "Pagador por si mismo","Tipo identificación pagador por tercero",
                "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero"
                };

        String[] labelAportante = { "Aportante" };
        data.add(labelAportante);
        data.add(encabezadoAportante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

            String[] filaAportante = { resultado.getAportante().getNumeroOperacion().toString(),
                    resultado.getAportante().getTipoAportante().name(),
                    resultado.getAportante().getTipoRegistro().name(),
                    resultado.getAportante().getTipoIdentificacionAportante().name(),
                    resultado.getAportante().getNumeroIdentificacionAportante(),
                    resultado.getAportante().getRazonSocial(),
                    resultado.getAportante().getDigitoVerificacion(),
                    resultado.getAportante().getPeriodoAporte(),
                    resultado.getAportante().getNumeroSolicitudCorreccion(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudo() != null ? new Date(resultado.getAportante().getFechaRecaudo().getTime()): new Date().getTime()),
                    formatoFechaCierre.format(resultado.getAportante().getFechaRecaudoCorreccion() != null ? new Date(resultado.getAportante().getFechaRecaudoCorreccion().getTime()): new Date().getTime()),
                    //resultado.getAportante().getNumeroPlanilla(),
                    resultado.getAportante().getNumeroPlanilla() != null && !resultado.getAportante().getNumeroPlanilla().isEmpty()
                        ? resultado.getAportante().getNumeroPlanilla()
                        : "NO APLICA", // Se deja no aplica porque para estos archivos no se tiene en cuenta el numero de planilla
                    resultado.getAportante().getMonto().setScale(2, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getInteres().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getInteres().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getTipoAportante().name().equals(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.name())
                        ? resultado.getAportante().getTotalAporte().setScale(0, RoundingMode.HALF_UP).toString().replaceAll("\\.", ",")
                        : resultado.getAportante().getTotalAporte().setScale(2, RoundingMode.HALF_UP).toString().replaceAll("\\.", ","),
                    resultado.getAportante().getUsuario(),
                //     resultado.getAportante().getNumeroOperacionCorreccion() != null ? resultado.getAportante().getNumeroOperacionCorreccion().toString() : "",
                    resultado.getAportante().getOportunidadDePago(),
                    resultado.getAportante().getEstadoAportante(),
                    resultado.getAportante().getCodigoBancoRecaudador(),
                    resultado.getAportante().getCuentaBancariaRecaudo(),
                    resultado.getAportante().getSucursalEmpresa(),
                    resultado.getAportante().getTarifaAportante() != null ? resultado.getAportante().getTarifaAportante().toString() : new BigDecimal("0").setScale(0, RoundingMode.CEILING).toString(),
                    formatoFechaCierre.format(resultado.getAportante().getFechaReconocimiento() != null ? new Date(resultado.getAportante().getFechaReconocimiento().getTime()): new Date().getTime()),
                   resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                    Boolean.TRUE.equals(resultado.getAportante().getPagadorPorSiMismo()) ? "Si": "No",
                    resultado.getAportante().getTipoIdentificacionTercero() == null ? "" : resultado.getAportante().getTipoIdentificacionTercero().name(),
                    resultado.getAportante().getNumeroIdentificacionTercero() == null ? "": resultado.getAportante().getNumeroIdentificacionTercero(),
                    resultado.getAportante().getRazonSocialtercero() == null ? "": resultado.getAportante().getRazonSocialtercero(),

                };

            data.add(filaAportante);
        }

        logger.debug(
                "Finaliza el método construirHojaCorreccionesOrigen(DetalleRegistroDTO)");
        return data;
    }
    /**
     * Genera la data correspondiente a las correciones de origen
     * @param detalleRegistroDTO
     * @return
     */
    public static List<String[]> generarDataCorrecionesOrigenCotizante(DetalleRegistroDTO detalleRegistroDTO) {

        List<String[]> data = new ArrayList<String[]>();

        String[] encabezadoCotizante = { "Numero de operacion del recaudo", "Tipo registro aporte", "Tipo identificacion del cotizante",
                "Numero de identificacion del cotizante", "Primer Nombre del cotizante", "Segundo Nombre del cotizante",
                "Primer apellido del cotizante", "Segundo Apellido del cotizante", "Periodo del nuevo aporte", "Fecha de Recaudo", "Número asignado de la solicitud",
                "Tipo de transacción", "Tarifa", "Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección",
                "Usuario que ejecutó la operacion para cotizante", "Fecha del registro de la novedad","Numero de operacion del recaudo",
                "Numero de identificacion del Aportante","Oportunidad de pago","Estado del aportante", "Tipo de planilla",
                "Pagador por si mismo","Tipo identificación pagador por tercero", "Número de identificación del pagador por tercero", "Razón Social – Nombre Pagador por tercero"};

        String[] labelCotizantes = { "Cotizantes" };
        data.add(labelCotizantes);
        data.add(encabezadoCotizante);
        for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {
            if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {

                

                for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {

                    String[] filaCotizante = { cotizante.getNumeroOperacion().toString(),
                        cotizante.getTipoRegistro().name(),
                            cotizante.getTipoIdentificacionCotizante().name(),
                            cotizante.getNumeroIdentificacionCotizante(),
                            cotizante.getPrimerNombre(),
                            cotizante.getSegundoNombre(), cotizante.getPrimerApellido(),
                            cotizante.getSegundoApellido(), cotizante.getPeriodoAporte(),
                            formatoFechaCierre.format(cotizante.getFechaRecaudo() != null ? new Date(cotizante.getFechaRecaudo().getTime()): new Date().getTime()),
                            resultado.getAportante().getNumeroSolicitudCorreccion(),
                            cotizante.getTipoTransaccion(),
                            cotizante.getTarifa().setScale(0, RoundingMode.CEILING).toString(),
                            cotizante.getMonto().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getInteres().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getTotalAporte().setScale(5, RoundingMode.CEILING).toString().replaceAll("\\.", ","),
                            cotizante.getUsuario(),
                            formatoFechaCierre.format(cotizante.getFechaNovedad() != null ? new Date(cotizante.getFechaNovedad().getTime()): new Date().getTime()),
                            resultado.getAportante().getNumeroOperacion().toString(),
                            resultado.getAportante().getNumeroIdentificacionAportante(),
                            resultado.getAportante().getOportunidadDePago(),
                            resultado.getAportante().getEstadoAportante(),
                            resultado.getAportante().getTipoPlanilla() == null ? "" : resultado.getAportante().getTipoPlanilla(),
                            Boolean.TRUE.equals(cotizante.getPagadorPorSiMismo()) ? "Si": "No",
                            cotizante.getTipoIdentificacionTercero() == null ? "" : cotizante.getTipoIdentificacionTercero().name(),
                            cotizante.getNumeroIdentificacionTercero() == null ? "": cotizante.getNumeroIdentificacionTercero(),
                            cotizante.getRazonSocialtercero() == null ? "": cotizante.getRazonSocialtercero()    
                   };
                    data.add(filaCotizante);

                }
            }
        }
        logger.debug(
                "Finaliza el método construirHojaCorreccionesOrigen(DetalleRegistroDTO)");
        return data;
    }
    
    /**
     * Genera el bite[] correspondiente al archivoa construir
     * @param encabezado
     * @param data
     * @param caracterSeparador
     * @return
     */
    public static byte[] generarArchivoPlano(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
        logger.debug("Inicia generarArchivoPlano(List<String[]>, List<String[]>, String)");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
        byte[] separador = "sep=|\n".getBytes(); 
        baos.write(separador);
        }catch(IOException e1){
             e1.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(baos)) {
            if (encabezado != null && !encabezado.isEmpty()) {
                construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
            }
            if (data != null && !data.isEmpty()) {
                construirSeccionArchivoPlano(writer, data, caracterSeparador);
            }
            writer.flush();
        }
        logger.debug("Finaliza generarArchivoPlano(List<String[]>, List<String[]>, String)");
        return baos.toByteArray();
    }
    
    /**
     * Construye una linea del archivo plano
     * @param writer
     * @param datosSeccion
     * @param caracterSeparador
     */
    private static void construirSeccionArchivoPlano(PrintWriter writer, List<String[]> datosSeccion, String caracterSeparador) {
        int i;
        for (String[] valores : datosSeccion) {
            i = 0;
            for (String valor : valores) {
                i++;
                writer.print(valor != null ? valor : "");
                if (i < valores.length) {
                    writer.print(caracterSeparador);
                }
            }
            writer.print('\r');
            writer.print('\n');
        }
    }
    
    /**
     * Construye el archivo zip a partir de los pares string-bite[] contenidos en el mapa
     * @param compression
     * @param data
     * @return
     * @throws Exception
     */
    private static File comprimirZip(int compression, HashMap<String, byte[]> data) throws Exception {
        logger.debug("Inicia comprimirZip(int, HashMap<String, byte[]>)");
        File tempZipFile = File.createTempFile("test-data" + compression, ".zip");
        tempZipFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempZipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.setLevel(compression);
            Iterator it = data.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String text = (String) pair.getKey();
                ZipEntry entry = new ZipEntry(text);
                zos.putNextEntry(entry);
                try {
                    zos.write((byte[]) pair.getValue());
                } finally {
                    zos.closeEntry();
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        logger.debug("Fin comprimirZip(int, HashMap<String, byte[]>)");
        return tempZipFile;
    }
}