package com.asopagos.archivos.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.fovis.CampoHojaArchivoCruceEnum;
import com.asopagos.enumeraciones.fovis.HojaArchivoCruceEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.Interpolator;

/**
 * Class to read the excel file cruce FOVIS
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ComprimidoUtil {

   

	/**
     * Método encargado de comprimir una serie de archivos
     * 
     * @param compression default poner 1
     * @parama data lista de map con la combinación nombre de archivo y array de byte
     *         
     * @return retorna File comprimido
     * @throws Exception
     */
    
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

    public static File comprimirZip(int compression, Map<String, File> files, String zipFileName) throws Exception {
        File tempZipFile = new File(zipFileName);

        try (FileOutputStream fos = new FileOutputStream(tempZipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.setLevel(compression);

            for (Map.Entry<String, File> keySet: files.entrySet()) {
                String fileName = keySet.getKey();
                ZipEntry entry = new ZipEntry(fileName);
                zos.putNextEntry(entry);
                try (FileInputStream fis = new FileInputStream(keySet.getValue())) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    fis.close();
                } finally {
                    zos.closeEntry();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return tempZipFile;
    }
    
	/**
     * Método encargado de comprimir una serie de archivos
     * 
     * @param compression default poner 1
     * @parama data lista de map con la combinación nombre de archivo y array de byte
     *         
     * @return retorna File comprimido
	 * @throws IOException 
     * @throws Exception
     */
    
    public static Response comprimirZipResponse(int compression,HashMap<String, byte[]> data) throws IOException{
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response.ResponseBuilder response = null;
        BufferedInputStream zipFileInputStream;
		zipFileInputStream = new BufferedInputStream(new FileInputStream(tempZipFile));
		response = Response.ok(zipFileInputStream);
        response.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        response.header("Content-Disposition", "attachment; filename=zipFile.zip");
        return response.build();
    }
    public static Response comprimirZipResponse(int compression,HashMap<String, byte[]> data, String fileName) throws IOException{
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response.ResponseBuilder response = null;
        BufferedInputStream zipFileInputStream;
		zipFileInputStream = new BufferedInputStream(new FileInputStream(tempZipFile));
		response = Response.ok(zipFileInputStream);
        response.header("Content-Type",  MediaType.APPLICATION_OCTET_STREAM_TYPE);
        response.header("Content-Disposition", "attachment; filename=" + fileName);	
        return response.build();
    }
}
