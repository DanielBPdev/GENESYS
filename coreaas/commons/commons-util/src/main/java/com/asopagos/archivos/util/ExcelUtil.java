package com.asopagos.archivos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

public class ExcelUtil {

    public static byte[] generarArchivoExcel(String nombreHoja, List<String[]> filas, List<String> cabeceras) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            try (Workbook wb = new Workbook(os, "MyApplication", "1.0")) {
                Worksheet ws = wb.newWorksheet(nombreHoja);
                
                // Escribir las cabeceras con estilo de negrita
                for (int i = 0; i < cabeceras.size(); i++) {
                    ws.style(0, i).bold().set();
                    ws.value(0, i, cabeceras.get(i));
                }
                
                // Escribir los datos
                for (int i = 0; i < filas.size(); i++) {
                    String[] fila = filas.get(i);
                    for (int j = 0; j < fila.length; j++) {
                        ws.value(i + 1, j, fila[j]);
                    }
                }
                
                wb.finish();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
