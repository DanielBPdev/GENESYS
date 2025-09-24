package com.asopagos.archivos.convert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.asopagos.archivos.dto.InformacionConvertDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class HeaderFooter extends PdfPageEventHelper {
	
	public InformacionConvertDTO objInformacionConvertDTOH;

	PdfTemplate total;
	int count = 0;

	protected ElementList header;
	protected ElementList footer;
	protected ElementList sello;

	public HeaderFooter(InformacionConvertDTO objInformacionConvertDTO) throws IOException {
		if (objInformacionConvertDTO.getHtmlFooter()!=null) {
			footer = parseToElementList(objInformacionConvertDTO.getHtmlFooter(), null);
		} else
			footer = parseToElementList("", null);

		if (objInformacionConvertDTO.isRequiereSello()) {
			sello = parseToElementList(objInformacionConvertDTO.getHtmlSello(), null);
		} else
			sello = parseToElementList("", null);

		if (objInformacionConvertDTO.getHtmlHeader()!=null) {
			header = parseToElementList(objInformacionConvertDTO.getHtmlHeader(), null);
		} else
			header = parseToElementList("", null);

		objInformacionConvertDTOH = objInformacionConvertDTO;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {
			ColumnText ct = new ColumnText(writer.getDirectContent());
			if(header.size()!=0){			
			ct.setSimpleColumn(objInformacionConvertDTOH.getMargenesx().get(0),
					document.getPageSize().getHeight() - objInformacionConvertDTOH.getMargenesy().get(1),
					document.getPageSize().getWidth() - objInformacionConvertDTOH.getMargenesx().get(1),
					document.getPageSize().getHeight() - objInformacionConvertDTOH.getMargenesy().get(1)
							- objInformacionConvertDTOH.getAltura());
			for (Element e : header) {
				ct.addElement(e);
			}
			ct.go();
			}
			if(footer.size()!=0){
			ct = new ColumnText(writer.getDirectContent());
			ct.setSimpleColumn(objInformacionConvertDTOH.getMargenesx().get(0),
					objInformacionConvertDTOH.getMargenesy().get(0),
					document.getPageSize().getWidth() - objInformacionConvertDTOH.getMargenesx().get(1),
					objInformacionConvertDTOH.getMargenesy().get(0) + objInformacionConvertDTOH.getAltura());
			for (Element e : footer) {
				ct.addElement(e);
			}
			ct.go();
			}
			if(sello.size()!=0){
			ct = new ColumnText(writer.getDirectContent());
			ct.setSimpleColumn(
					objInformacionConvertDTOH.getMargenesx().get(0)
							+ objInformacionConvertDTOH.getMargenesSelloxy().get(0),
					objInformacionConvertDTOH.getMargenesy().get(0)
							+ objInformacionConvertDTOH.getMargenesSelloxy().get(1),
					document.getPageSize().getWidth() - objInformacionConvertDTOH.getMargenesx().get(1),
					objInformacionConvertDTOH.getMargenesy().get(0) + objInformacionConvertDTOH.getAltura());
			for (Element e : sello) {
				ct.addElement(e);
			}
			ct.go();
			}
			if (count == 0) {
				total = writer.getDirectContent().createTemplate(17, 17);
				count++;
			}
			Font ffont = new Font(Font.FontFamily.UNDEFINED, 11, Font.NORMAL);
			PdfPTable table = new PdfPTable(2);
			try {
				table.setWidths(new int[] { 1, 1 });
				table.setTotalWidth(510);
				table.getDefaultCell().setBorder(0);
				table.getDefaultCell().setFixedHeight(0);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				//Se comenta, ya que no es requerido agregar la enumeracion de pagina
				//Phrase footer = new Phrase(String.format("Pagina %d de", writer.getPageNumber()), ffont);
				//table.addCell(footer);
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				//cell.setBorder(PdfPCell.TOP);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, 275, 40, writer.getDirectContent());
			} catch (Exception de) {
				throw new ExceptionConverter(de);
			}

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		ColumnText.showTextAligned(total, Element.ALIGN_RIGHT, new Phrase(String.valueOf(writer.getPageNumber() - 1)),
				10, 4, 0);
	}

	public ElementList parseToElementList(String html, String css) throws IOException {

		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		if (css != null) {
			CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(css.getBytes()));
			cssResolver.addCss(cssFile);
		}

		// HTML
		CssAppliers cssAppliers = new CssAppliersImpl(FontFactory.getFontImp());
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new Base64ImageProvider());
		htmlContext.autoBookmark(false);

		// Pipelines
		ElementList elements = new ElementList();
		ElementHandlerPipeline end = new ElementHandlerPipeline(elements, null);
		HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, end);
		CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

		// XML Worker
		XMLWorker worker = new XMLWorker(cssPipeline, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(html.getBytes()));

		return elements;
	}

}
