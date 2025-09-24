package com.asopagos.archivos.business.ejb;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeManager;
import com.asopagos.archivos.constants.DocumentType.Documents;
import org.jodconverter.local.office.LocalOfficeManager;
import com.artofsolving.jodconverter.DocumentFamily;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.core.DocumentConverter;


import java.io.ByteArrayInputStream; 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;


/**
 * @author Juan David Quintero
 * Bean responsable de la conversion de documentos 
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ConvertToPDF {


    private static final ILogger logger = LogManager.getLogger(ConvertToPDF.class);

    /**
     * Ip Del Servidor OpenOffice
     */
    private static String IP_SERVER;
    /**
     * Puertos Del Servidor OpenOffice
     */
    private static final List<Integer> PORT_SERVERS = new ArrayList();

    private static OfficeManager OFFICE_MANAGER;

    @PostConstruct
    public void cargarVariablesConstantes() {
        logger.info("Inicio de carga de variables");
        IP_SERVER = (String) CacheManager.getConstante(ConstantesSistemaConstants.OPEN_OFFICE_END_POINT);
        String stringPuertos = (String) CacheManager.getConstante(ConstantesSistemaConstants.OPEN_OFFICE_PORT);
        int[] puertos = Arrays.stream(stringPuertos.split(",")).mapToInt(Integer::parseInt).toArray();

        try {
            //Instancia unica de officeManager
            OFFICE_MANAGER =
            LocalOfficeManager.builder()
                .portNumbers(puertos)
                .build();
                
            OFFICE_MANAGER.start();
        } catch (Exception e) {
            logger.error("Error al iniciar office manager", e);
        }
        
    }

    @PreDestroy
    public void finalizar() {
        logger.info("Inicio de finalizar");
        OfficeUtils.stopQuietly(OFFICE_MANAGER);
        
    }

    @Lock(LockType.READ)
    public byte[] convertToPDF(byte[] binaryFile, Documents enumDoc) {
        byte[] returnByte = null;
        try {
            returnByte = convertFileToPdf(binaryFile, enumDoc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("Error en la transformacion del archivo: " + enumDoc.getName() + e.getMessage());
        }
        return returnByte;
    }


    private byte[] convertFileToPdf(byte[] binaryFile, Documents enumDoc) throws IOException {
        logger.debug("Iniciando conversión de archivo: " + enumDoc.getName() + " a PDF");
        InputStream targetStream = new ByteArrayInputStream(binaryFile);
        DocumentFormat docReq;
        DocumentFormat docRes;
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        DocumentFamily outputDocumentFamily = enumDoc.getDocumentFamily();

        try {
            if (enumDoc.equals(Documents.IMAGE)) {
                logger.debug("convirtiendo imagen a PDF");
                Image img;
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, fos);
                writer.open();
                document.open();
                img = Image.getInstance(binaryFile);

                //Tamaño de documento con márgenes
                float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();

                //Diferencia de tamaños entre la imagen y el documento
                float diffWithImag = documentWidth - img.getWidth();
                float diffHeigthImag = documentHeight - img.getHeight();

                //Si la imagen es más grande en alguna de sus dimensiones, deberá escalarse al porcentaje de la menor dimesión.
                if (diffWithImag < 0 || diffHeigthImag < 0) {
                    float withPercentToscale = documentWidth * Float.valueOf("100") / img.getWidth();
                    float heigthPercentToscale = documentHeight * Float.valueOf("100") / img.getHeight();

                    if (withPercentToscale > heigthPercentToscale) {
                        logger.debug("La imagen se escalará al: " + withPercentToscale);
                        img.scalePercent(heigthPercentToscale);
                    }
                    else {
                        logger.debug("La imagen se escalará al: " + heigthPercentToscale);
                        img.scalePercent(withPercentToscale);
                    }
                }
                document.add(img);
                document.close();
                writer.close();
            }
            else {

                DocumentFormat formatoInicial = DefaultDocumentFormatRegistry.getFormatByExtension(enumDoc.getExtension().substring(1));
                DocumentFormat formatoFinal = DefaultDocumentFormatRegistry.PDF;
                convertFileToPdfOffice(
                    targetStream,
                    formatoInicial,
                    fos,
                    formatoFinal
                );

            } 
        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            fos.close();
        }
        return fos.toByteArray();
    }

    private void convertFileToPdfOffice(
        InputStream inputStream,
        DocumentFormat formatoInicial,
        OutputStream outputStream,
        DocumentFormat formatoFinal) throws IOException {

        try {

            final DocumentConverter documentConverter = LocalConverter.make(OFFICE_MANAGER);
            // Convert
            documentConverter
            .convert(inputStream)
            .as(formatoInicial)
            .to(outputStream)
            .as(formatoFinal)
            .execute();

        } catch (Exception e) {
            logger.error("Error al convertir" , e);
        }

    }
}
