package com.asopagos.archivos.convert;

import static com.asopagos.util.Interpolator.interpolate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.asopagos.archivos.ejb.ArchivosBusiness;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ConvertHtmlToPdf {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ConvertHtmlToPdf.class);

    /**
     * CONSTATNTES
     */
    Pattern objPattern = Pattern.compile("\"\\{(.*?)\\}\"", Pattern.DOTALL | Pattern.MULTILINE);

    /**
     * Referencia al EJB ArchivosBusiness
     */
    private final ArchivosBusiness archivosBusiness;

    /**
     * Constructor de la clase
     * @param archivosBusiness
     */
    public ConvertHtmlToPdf(ArchivosBusiness archivosBusiness) {
        this.archivosBusiness = archivosBusiness;
    }

    /**
     * FUNCIONES
     * @param objInformacionConvertDTO
     * @return
     */
    public byte[] createPdf(InformacionConvertDTO objInformacionConvertDTO) {
        logger.debug("Inicia createPdf(InformacionConvertDTO)");
        try {
            Document document = new Document(PageSize.LETTER);
            // margenes izq,dere,top,footer
            document.setMargins(objInformacionConvertDTO.getMargenesx().get(0), objInformacionConvertDTO.getMargenesx().get(1),
                    objInformacionConvertDTO.getAltura() + objInformacionConvertDTO.getMargenesy().get(0),
                    objInformacionConvertDTO.getAltura() + objInformacionConvertDTO.getMargenesy().get(1));
            document.setMarginMirroring(true);
            ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, bOutput);
            document.open();

            // CSS
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
            // HTML
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new Base64ImageProvider());

            HeaderFooter event = new HeaderFooter(objInformacionConvertDTO);
            writer.setPageEvent(event);

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);

            String htmlString =  objInformacionConvertDTO.getHtmlContenido();
            byte[] flujo = htmlString.getBytes();
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(flujo);
            p.parse(byteArrayIS);
            try {
                document.close();
            } catch (Exception e) {
                logger.error(interpolate("Error cerrando el documento {0}", e));
            }
            logger.debug("Finaliza createPdf(InformacionConvertDTO)");
            String base64Pdf = Base64.getEncoder().encodeToString(bOutput.toByteArray());
            return bOutput.toByteArray();
        } catch (Exception e) {
            logger.error(interpolate("Error documento {0}", e));
            return null;
        }

    }

    public InformacionConvertDTO convertHtml(InformacionConvertDTO objInformacionConvertDTO) {

        InformacionArchivoDTO objInformacionArchivoDTO = new InformacionArchivoDTO();
        if (objInformacionConvertDTO.getHtmlHeader() != null)
            objInformacionConvertDTO
                    .setHtmlHeader(convertIdImageToBase64(objInformacionConvertDTO.getHtmlHeader(), objInformacionArchivoDTO));
        if (objInformacionConvertDTO.getHtmlFooter() != null)
            objInformacionConvertDTO
                    .setHtmlFooter(convertIdImageToBase64(objInformacionConvertDTO.getHtmlFooter(), objInformacionArchivoDTO));
        if (objInformacionConvertDTO.getHtmlContenido() != null)
            objInformacionConvertDTO
                    .setHtmlContenido(convertIdImageToBase64(objInformacionConvertDTO.getHtmlContenido(), objInformacionArchivoDTO));
        if (objInformacionConvertDTO.isRequiereSello())
            objInformacionConvertDTO
                    .setHtmlSello(convertIdImageToBase64(objInformacionConvertDTO.getHtmlSello(), objInformacionArchivoDTO));

        return objInformacionConvertDTO;
    }

    public String convertIdImageToBase64(String html, InformacionArchivoDTO objInformacionArchivoDTO) {
        Matcher matchs = objPattern.matcher(html);
        while (matchs.find()) {
            String idImage = matchs.group(1);
            try {
                String encoded;
                try {
                    objInformacionArchivoDTO = archivosBusiness.obtenerArchivo(idImage, null);
                    encoded = Base64.getEncoder().encodeToString(objInformacionArchivoDTO.getDataFile());
                } catch (Exception e) {
                    URL url = ConvertHtmlToPdf.class.getClassLoader().getResource("image/default.png");
                    Path path = Paths.get(url.toURI());
                    byte[] data = Files.readAllBytes(path);
                    encoded = Base64.getEncoder().encodeToString(data);
                }
                html = html.replaceAll(idImage, "data:image/jpeg;base64," + encoded);
            } catch (Exception e) {
                logger.debug("No se pudo convertir el objeto" + idImage);
            }
        }
        html = replaceConvencion(html);
        return html;
    }

    public String replaceConvencion(String html) {
        html = html.replaceAll("\"\\{", "\"");
        html = html.replaceAll("\\}\"", "\"");
        return html;
    }

}
