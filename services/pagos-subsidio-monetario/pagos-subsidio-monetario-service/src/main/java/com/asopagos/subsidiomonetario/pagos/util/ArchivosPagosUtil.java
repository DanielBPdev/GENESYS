package com.asopagos.subsidiomonetario.pagos.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Contiene funcionalidades relacionadas con la generación de archivos<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:amarin@heinsohn.com.co"></a>
 */
public class ArchivosPagosUtil {
	
	 
    
    /**
     * Genera array de bytes que permite la creación de un archivo .xlsx
     * @param encabezado : Contiene el nombre de cada columna del archivo
     * @param data : Información contenida en cada fila
     * @return array de bytes con la data del archivo a generar
     */
    public static byte[] generarNuevoArchivoExcel(List<String[]> encabezado, List<String[]> data) {
        //TO-DO Usar Apache POI paara crar este archivo en formato XSLX
    	XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet pagina = libro.createSheet("reporte");

        int indiceRow = 0;
        int indiceColumn = 0;

        if (encabezado != null && encabezado.size() > 1) {
            //0,first row (0-based); 0,last row  (0-based) ; 0, first column (0-based);  last column  (0-based)
            CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn, encabezado.get(1).length);
            pagina.addMergedRegion(mergedCell);
            indiceRow++;
        }

        if (encabezado != null) {
            //Generación del encabezado del reporte
            XSSFRow filaIterEncabezadoAportante = pagina.createRow(indiceRow);
            for (int i = 0; i < encabezado.size(); i++) {
                for (String encAportante : encabezado.get(i)) {
                    XSSFCell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
                    celdaIterEncabezadoAportante.setCellValue(encAportante);
                    indiceColumn++;
                }
                indiceRow++;
            }
        }
        //Generación de cada registro por fila
        for (Object[] items : data) {
            indiceColumn = 0;
            XSSFRow filaIterAportante = pagina.createRow(indiceRow);
            for (Object dato : items) {
            	XSSFCell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
                
                Integer numero = null;
                if(dato != null && dato instanceof BigDecimal){
                    numero =  BigDecimal.valueOf(Double.valueOf(dato.toString())).toBigInteger().intValue();
                }else if(dato != null && dato instanceof Integer){
                    numero = Integer.valueOf(dato.toString());
                }
                
                if(numero!=null){
                    celdaIterAportante.setCellType(CellType.NUMERIC);
                    celdaIterAportante.setCellValue(numero.longValue());
                    
                }else{
                    celdaIterAportante.setCellValue(dato != null ?  dato.toString() : "");
                }
                    
                indiceColumn++;
            }
            //se aumenta el número de la fila para almacenar el otro registro
            indiceRow++;
        }

        ByteArrayOutputStream archivo = new ByteArrayOutputStream();
        try {
            // Almacenamos el libro de Excel via ese flujo de datos
            libro.write(archivo);
            libro.close();
        } catch (IOException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
                    e + " En el cierre del archivo para la generación del Excel");
        }
        return archivo.toByteArray();
    }
    
    public static byte[] generarArchivoPlano(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(baos)) {
            if (encabezado != null && !encabezado.isEmpty()) {
                construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
            }
            if (data != null && !data.isEmpty()) {
                construirSeccionArchivoPlano(writer, data, caracterSeparador);
            }
            writer.flush();
        }
        return baos.toByteArray();
    }
    
    public static byte[] generarArchivoCSV(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter baoss = null;
        try {
            baoss = new OutputStreamWriter(baos,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        baos.write(239);
        baos.write(187);
        baos.write(191);
        try (PrintWriter writer = new PrintWriter(baoss)) {
            if (encabezado != null && !encabezado.isEmpty()) {
                construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
            }
            if (data != null && !data.isEmpty()) {
                construirSeccionArchivoPlano(writer, data, caracterSeparador);
            }
            writer.flush();
        }
        return baos.toByteArray();
    }
    
    private static void construirSeccionArchivoPlano(PrintWriter writer, List<String[]> datosSeccion, String caracterSeparador) {
        int i;
        for (Object[] valores : datosSeccion) {
            i = 0;
            for (Object valor : valores) {
                i++;
                writer.print(valor != null?valor.toString():"");
                if (i < valores.length) {
                    writer.print(caracterSeparador);
                }
            }
            writer.print('\r');
            writer.print('\n');
        }        
    }
    
    public static File comprimirZip(int compression,HashMap<String, byte[]> data) throws Exception {
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
}
