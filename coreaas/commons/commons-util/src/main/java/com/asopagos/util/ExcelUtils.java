/**
 *
 */
package com.asopagos.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que se encarga encarga de leer el número de lineas de un archivo de
 * formato xlsx o xls
 *
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
public class ExcelUtils {

    /**
     * Método encargado de contar el número de lineas de un excel
     *
     * @param archivoExcel,
     *        archivo a contar las lineas
     * @parama finLineaLectura, linea en la que se detiene la lectura del
     *         archivo
     * @return retorna el número de lineas del archivo
     * @throws IOException
     */
    public static Long leerNumeroFilas(byte[] archivoExcel, Long finLineaLectura) throws IOException {
        // Finds the workbook instance for XLSX file
        // Return first sheet from the XLSX workbook
        try (InputStream fis = new ByteArrayInputStream(archivoExcel);
             XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);) {
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
            // Traversing over each row of XLSX file
            Long contadorLinea = 0L;
            while (rowIterator.hasNext()) {
                rowIterator.next();
                if (contadorLinea <= finLineaLectura) {
                    contadorLinea++;
                } else {
                    break;
                }
            }
            return contadorLinea;
        }
    }


    public static byte[] limpiarFilasExcel(byte[] archivoExcel) throws IOException {
        try (InputStream fis = new ByteArrayInputStream(archivoExcel);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
           ) {
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            
            // Iteracion sobre las filas del documento
            Boolean isRowEmpty = Boolean.FALSE;
            for(int i = 0; i < mySheet.getLastRowNum(); i++){
                if(mySheet.getRow(i)==null){
                    // Eliminacion de la fila
                    mySheet.shiftRows(i + 1, mySheet.getLastRowNum(), -1);
                    i--;
                continue;
                }
                isRowEmpty = isEmpty(mySheet.getRow(i));
                if(isRowEmpty==true){
                    mySheet.shiftRows(i + 1, mySheet.getLastRowNum(), -1);
                    i--;
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            myWorkBook.write(bos);
            archivoExcel = bos.toByteArray();
            bos.close();
            
        }
        return archivoExcel;

    }


    public static byte[] ajustarOrdenHojaExcelPorNombre(byte[] archivoExcel, String nombreHoja) throws IOException {
        try (InputStream fis = new ByteArrayInputStream(archivoExcel);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
           ) {
            XSSFSheet mySheet = myWorkBook.getSheet(nombreHoja);
            myWorkBook.setSheetOrder(nombreHoja, 0);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            myWorkBook.write(bos);
            archivoExcel = bos.toByteArray();
            bos.close();
            
        }
        return archivoExcel;

    }

    private static boolean isEmpty(Row fila) {
        for (int i = 0; i< fila.getLastCellNum(); i++) {
            Cell c = fila.getCell(i);
            if (c != null || c.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene el workbook a partir del archivo enviado
     * @param excelFile
     *        Flujo de bytes archivo enviado
     * @return Libro excel 
     * @throws Exception
     */
    public static Workbook getWorkbook(byte[] excelFile) throws Exception {
        // Gets the excel file instance
        // Finds the workbook instance for excel file
        try (InputStream fis = new ByteArrayInputStream(excelFile);
             Workbook workbook = WorkbookFactory.create(fis);) {
            return workbook;
        }
    }

    /**
     * Get number of sheets excel file
     * @param workbook
     *        Excel file to analyze
     * @return Number of sheets from file
     * @throws IOException
     */
    public static Long getNumberOfSheets(Workbook workbook) throws Exception {
        // Returns number of sheets from the excel workbook
        Integer sheetsQuantity = workbook.getNumberOfSheets();
        return sheetsQuantity.longValue();
    }

    /**
     * Validates the names of sheets workbook
     * @param workbook
     *        Excel file to analyze
     * @param sheetNames
     *        List of sheet names
     * @return True if excel file contains the list names. <br>
     *         False if excel file does not contains some name
     * @throws IOException
     */
    public static Boolean validateSheetNames(Workbook workbook, List<String> sheetNames) throws Exception {
        for (String sheetN : sheetNames) {
            if (!verifySheetName(workbook, sheetN)) {
                System.out.println("VALIDACION FALLIDA DE HOJA ================================: " + sheetN);
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Verifies the sheet name between workBook and the sheet name parameter
     * @param myWorkBook
     *        workbook that represent the excel file
     * @param sheetName
     *        sheet name to validate
     * @return True if excel file contains the sheet name,otherwise false
     */
    private static Boolean verifySheetName(Workbook myWorkBook, String sheetName) {
        Sheet sheet = myWorkBook.getSheet(sheetName);
        if (sheet != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static byte[] generarArchivoCSV(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter baoss = null;
        try {
            baoss = new OutputStreamWriter(baos, "UTF-8");
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
                writer.print(valor != null ? valor.toString() : "");
                if (i < valores.length) {
                    writer.print(caracterSeparador);
                }
            }
            writer.print('\r');
            writer.print('\n');
        }
    }

}
