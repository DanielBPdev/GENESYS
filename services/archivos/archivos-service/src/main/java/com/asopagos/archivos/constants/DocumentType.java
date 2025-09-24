package com.asopagos.archivos.constants;

import com.artofsolving.jodconverter.DocumentFamily;

/**
 * @author Jonatan Velandia
 */
public class DocumentType {
    
    
    public static final String CONTENT_TYPE = "Content-Type";
    
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
        
    public static final String WRITER_PDF_EXPORT = "writer_pdf_Export";
    
	
	public enum Documents {
		/*
		 * Constantes Utilizadas Para Identificar El Tipo De Documento A Convertir
		 */
		MS_DOCX(".docx","Microsoft Word 2007 XML", DocumentFamily.TEXT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
		MS_DOC(".doc","Microsoft Word", DocumentFamily.TEXT, "application/msword"),
		MS_XSLX(".xlsx","Microsoft Excel 2007 XML", DocumentFamily.SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
		MS_CSV(".csv", "Comma Separated Values", DocumentFamily.SPREADSHEET, "text/csv"),
		MS_XLS(".xls", "Microsoft Excel", DocumentFamily.SPREADSHEET, "application/vnd.ms-excel"),
		TXT(".txt", "Plain Text", DocumentFamily.TEXT, "text/plain"),
		HTML(".html", "HTML", DocumentFamily.TEXT, "text/html"),
		PDF(".pdf", "TEXT", DocumentFamily.TEXT, "application/pdf"),
		IMAGE(null, "IMAGE", null, null),
		ZIP(".zip", "ZIP", null, "application/octet-stream");
		
		private String extension;
		private String name;
		private DocumentFamily documentFamily;
		private String mimeType;
		private String nameTmp;
		
		private Documents(String extension, String name, DocumentFamily documentFamily, String mimeType) {
			this.extension = extension;
			this.name = name;
			this.documentFamily = documentFamily;
			this.mimeType = mimeType;
			this.nameTmp = extension == null ? null : extension.substring(1);
		}
        
        /**
         * Obtiene la instancia de Documents correspondiente a partir del MIME 
         * type
         * @param mimeType
         * @return 
         */
        public static Documents getEnumType(String mimeType) {
            for (Documents doc : Documents.values()) {
                if (doc.getMimeType() != null && doc.getMimeType().equals(mimeType)) {
                    return doc;
                } 
            }
            if (mimeType != null && mimeType.toLowerCase().startsWith("image")) {
                return IMAGE;
            }
            return null;
        }
        
		public String getExtension() {
			return extension;
		}
        
		public void setExtension(String extension) {
			this.extension = extension;
		}
        
		public String getName() {
			return name;
		}
        
		public void setName(String name) {
			this.name = name;
		}
        
		public DocumentFamily getDocumentFamily() {
			return documentFamily;
		}
        
		public void setDocumentFamily(DocumentFamily documentFamily) {
			this.documentFamily = documentFamily;
		}
        
		public String getMimeType() {
			return mimeType;
		}
        
		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}
        
		public String getNameTmp() {
			return nameTmp;
		}
        
		public void setNameTmp(String nameTmp) {
			this.nameTmp = nameTmp;
		}
	}
    
}
