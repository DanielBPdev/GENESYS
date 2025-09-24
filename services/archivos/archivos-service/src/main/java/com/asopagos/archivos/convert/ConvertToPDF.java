package com.asopagos.archivos.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.asopagos.archivos.constants.DocumentType;
import com.asopagos.archivos.constants.DocumentType.Documents;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;

/**
 * @author Jonatan Velandia
 */
public class ConvertToPDF {

    private static final ILogger logger = LogManager.getLogger(ConvertToPDF.class);

    /**
     * Ip Del Servidor OpenOffice
     */
    private static final String IP_SERVER;
    /**
     * Puerto Del Servidor OpenOffice
     */
    private static final int PORT_SERVER;

    static {
        IP_SERVER = (String) CacheManager.getConstante(ConstantesSistemaConstants.OPEN_OFFICE_END_POINT);
        PORT_SERVER = Integer.valueOf((String) CacheManager.getConstante(ConstantesSistemaConstants.OPEN_OFFICE_PORT));
    }

    /**
     * Metodo Encargado De Convertir Un Archivo En Formato Pdf Con La Ayuda De
     * OpenOffice Como Servidor
     *
     * @param binaryFile
     * @param enumDoc
     * @return
     */
    public static byte[] convertToPDF(byte[] binaryFile, Documents enumDoc) {
        byte[] returnByte = null;
        try {
            returnByte = convertFileToPdf(binaryFile, enumDoc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("Error en la transformacion del archivo: " + enumDoc.getName() + e.getMessage());
        }
        return returnByte;
    }

    public static byte[] convertFileToPdf(byte[] binaryFile, Documents enumDoc) throws IOException {

        logger.debug("Iniciando conversión de archivo: " + enumDoc.getName() + " a PDF");
        InputStream targetStream = new ByteArrayInputStream(binaryFile);
        OpenOfficeConnection connection = null;
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
                // Conexion con OpenOffice, corriendo como servidor 
                logger.debug("Conexión con el Servidor: " + IP_SERVER + " en el puerto: " + PORT_SERVER);
                try{
                    connection = new SocketOpenOfficeConnection(IP_SERVER, PORT_SERVER);
                    connection.connect();
                } catch (Exception e) {
                    connection = null;
                    logger.error("Genera ERROR Conexión con el Servidor: SocketOpenOfficeConnection " + IP_SERVER + " en el puerto: " + PORT_SERVER);
                    connection = new SocketOpenOfficeConnection(IP_SERVER, PORT_SERVER);
                    connection.connect();
                }
          

                //Se Especifica El Tipo De Archivo De Conversion Origen
                docReq = new DocumentFormat(enumDoc.getName(), enumDoc.getDocumentFamily(), enumDoc.getMimeType(), enumDoc.getNameTmp());
                //Se Especifica EL Tipo De Archivo De Conversion De Destino
                docRes = new DocumentFormat(Documents.PDF.getName(), Documents.PDF.getDocumentFamily(), DocumentType.WRITER_PDF_EXPORT,
                        Documents.PDF.getNameTmp());
                docRes.setExportFilter(outputDocumentFamily, DocumentType.WRITER_PDF_EXPORT);

                DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                converter.convert(targetStream, docReq, fos, docRes);
            }

        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            //Cerramos Las Conexiones
            if (connection != null && connection.isConnected()) {
                connection.disconnect();
            }
            fos.close();
        }
        return fos.toByteArray();
    }
}
