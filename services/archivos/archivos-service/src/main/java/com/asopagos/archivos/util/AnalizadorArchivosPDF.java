package com.asopagos.archivos.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.asopagos.archivos.constants.DocumentType.Documents;
import com.asopagos.archivos.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoClasificableDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.novedades.SolicitudNovedad;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.archivos.TipoPropietarioArchivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.google.cloud.storage.StorageOptions;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;

/**
 * @author sbrinez
 */
public class AnalizadorArchivosPDF {
    

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(AnalizadorArchivosPDF.class);    
    
    /**
     * Referencia al EntityManager
     */
    private final EntityManager entityManager;
    
    /**
     * Constructor de la clase
     *
     * @param entityManager
     */
    public AnalizadorArchivosPDF(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<InformacionArchivoDTO> analizarArchivoPDF(byte[] dataFile) {
    	String firmaServicio = "AnalizadorArchivosPDF.analizarArchivoPDF( byte[] )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    	
        PDDocument documento = null;
        List<InformacionArchivoDTO> archivosDTO = new ArrayList<>();
        InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null;
        InformacionArchivoDTO archivoDTO = null;
        
        PDDocument subArchivo = new PDDocument();
        PDPage pagina;
        Code128Reader reader = null;
        PDFRenderer renderer = null;
        long start = 0;
        
        BufferedImage image = null; 
        LuminanceSource source = null;
        BinaryBitmap bitmap = null;
        Map<DecodeHintType,Object> map = null;
        Result result = null;
        ByteArrayOutputStream baos = null;
        
        String etiquetaAnterior = null;
        String idSolicitud = null;
        String idRequisito = null;
        String idPersona = null;

        Map<String,String> hmBarCodes = new HashMap<String, String>();
        
        try {
        	start = System.currentTimeMillis();
            documento = PDDocument.load(dataFile);
            reader = new Code128Reader();
            renderer = new PDFRenderer(documento);
            logger.debug("Archivo cargado en: "+(System.currentTimeMillis()-start) + " milisegundos");
		} catch (IOException e) {
			logger.error(firmaServicio + " :: " + MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
    				+ " :: No se logro realizar la carga del archivo :: " + e.getMessage()  );
		}
        if( documento != null ){
        	for (int numeroPagina = 0; numeroPagina < documento.getNumberOfPages(); ++numeroPagina) {
                pagina = documento.getPage(numeroPagina);
                try {
                    //image = renderer.renderImage(numeroPagina);

                	//Se realiza el renderizado de la pagina, teniendo encuenta el factor de escala para pdfbox, en dpi
                	//donde 1 = 72 dpi, de esta manera la imagen se renderiza con mayor definicion: 360 dpi.
                	//Esto con el fin de evitar ruidos en la imagen, y dimensiones del codigo de barras 
                	//al momento de escanear el documento 
                	image = renderer.renderImage(numeroPagina, 5);
                    source = new BufferedImageLuminanceSource(image);
                    bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    map = new HashMap<DecodeHintType,Object>();
                    map.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                    result = reader.decode(bitmap, map);
                    logger.debug(firmaServicio + " :: " + "Codigo de barras << " + result.getText() + " >> "
                		+ "encontrado en la página: #" + numeroPagina+1);
                    hmBarCodes.put(result.getText(), String.valueOf(numeroPagina+1));
                    if (etiquetaAnterior != null) {
        				baos = new ByteArrayOutputStream();
                        subArchivo.save(baos);
                        String[] arrBarcodeValues = etiquetaAnterior.split("-");
                        if( arrBarcodeValues != null && arrBarcodeValues.length == 3){
                        	archivoDTO = crearArchivoParaClasificacion(arrBarcodeValues, baos.toByteArray());
                        	if( archivoDTO != null ){
                        		archivosDTO.add(archivoDTO);
                        	}
                        	baos.close();
                        	cerrarDocumento(subArchivo);
	                        subArchivo = new PDDocument();
                        }else{
                        	String mensajeError = "Codigo de barras generado por el sistema es invalido \n"+
                          			"Codigo de barras << " + etiquetaAnterior + " >> encontrado en la pagina: #" + 
                						 hmBarCodes.get(etiquetaAnterior);
            				logger.debug(firmaServicio + " :: " + MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
                		    				+ " :: " + mensajeError  );
            				throw new WriterException(mensajeError);
                        }
                    }
                    etiquetaAnterior = result.getText();
                } catch (NotFoundException | WriterException excep) {
                	//Si la primera pagina del archivo no contiene codigo de barras, se genera error en la separacion de todos los 
                	//documentos contenidos en el archivo, ya que es obligatorio
                	if(numeroPagina == 0 || excep instanceof WriterException){
                		cerrarDocumento(subArchivo);
                		cerrarDocumento(documento);
                		throw new ParametroInvalidoExcepcion(excep);
                	}
                } catch(FormatException | IOException ex) {
                	cerrarDocumento(subArchivo);
                	cerrarDocumento(documento);
                    throw new TechnicalException(ex);
                }
                subArchivo.addPage(pagina);
                if(numeroPagina == documento.getNumberOfPages()-1 ){
                	if (etiquetaAnterior != null) {
            			try {
            				baos = new ByteArrayOutputStream();
                            subArchivo.save(baos);
                            String[] arrBarcodeValues = etiquetaAnterior.split("-");
                            if( arrBarcodeValues != null && arrBarcodeValues.length == 3){
                            	archivoDTO = crearArchivoParaClasificacion(arrBarcodeValues, baos.toByteArray());
                            	if( archivoDTO != null ){
                            		archivosDTO.add(archivoDTO);
                            	}
                            	baos.close();
                            	cerrarDocumento(subArchivo);
    	                        subArchivo = new PDDocument();
            			 }else{
            				 String mensajeError = "Codigo de barras generado por el sistema es invalido \n"+
                      			"Codigo de barras << " + etiquetaAnterior + " >> encontrado en la pagina: #" + 
            						 hmBarCodes.get(etiquetaAnterior);
            				 logger.debug(firmaServicio + " :: " + MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
            		    				+ " :: " + mensajeError  );
            				 throw new WriterException(mensajeError);
                         }
            			} catch(WriterException ex ) {
            				cerrarDocumento(subArchivo);
            				cerrarDocumento(documento);
                            throw new ParametroInvalidoExcepcion(ex);
                        } catch(IOException ex) {
                        	cerrarDocumento(subArchivo);
                        	cerrarDocumento(documento);
                            throw new TechnicalException(ex);
                        }
                    }
                }
            }
        	cerrarDocumento(subArchivo);
        	cerrarDocumento(documento);
        }
        logger.debug("Tiempo total: "+(System.currentTimeMillis()-start) + " milisegundos");
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return archivosDTO;
    }
    
    /**
     * Metodo que permite cerrar el nuevo documento PDF
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param subArchivo
     * 		  <code>PDDocument</code>
     * 		  El archivo representacion del nuevo documento PDF
     * 
     */
    private void cerrarDocumento(PDDocument subArchivo){
    	String firmaServicio = "AnalizadorArchivosPDF.cerrarDocumento( PDDocument )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        try{
    		if(subArchivo != null){
            	//Se cierra el objeto de uso para crear documentos
            	subArchivo.close();        			
    		}
    	} catch(IOException ex) { 
    		throw new TechnicalException(ex);    	
		}
        logger.debug(ConstantesComunes.FIN_LOGGER+ firmaServicio);
    }
    
    /**
     * Metodo que permite obtener el numero y tipo de identificacion del propietario 
     * del documento escaneado en base al identificador de la solicitud global
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param arrBarcodeValues
     * 		  <code>String[]</code>
     * 		  El arreglo con los valores encontrados en el codigo de barras identificado
     * 
     * @param arrBytesPagina
     * 		  <code>byte[]</code>
     * 	      El arreglo de bytes del documento identificado
     * 
     * @return <code>InformacionArchivoDTO</code>
     * 		   Retorna el documento identificado y procedente para clasificacion por escaneo masivo
     */
    private InformacionArchivoDTO crearArchivoParaClasificacion(String[] arrBarcodeValues, 
    		byte[] arrBytesPagina){
    	String firmaServicio = "AnalizadorArchivosPDF.crearArchivoParaClasificacion( String[], byte[] )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        String strTipoTransaccionProcesoNombre = null;
        InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null;
        InformacionArchivoDTO archivoDTO = new InformacionArchivoDTO();
        String idSolicitud = arrBarcodeValues[0];
        String idRequisito = arrBarcodeValues[1];
        String idPersona = arrBarcodeValues[2];
        archivoDTO = new InformacionArchivoDTO();
        archivoDTO.setFileName( UUID.randomUUID().toString().
    		concat(Documents.PDF.getExtension()));
        archivoDTO.setFileType(Documents.PDF.getMimeType());
        archivoDTO.setIdSolicitud(Long.valueOf(idSolicitud));
        archivoDTO.setIdRequisito(Long.valueOf(idRequisito));
        archivoDTO.setIdPersona(Long.valueOf(idPersona));
        archivoDTO.setDataFile(arrBytesPagina);
        archivoDTO.setSize(new Long (arrBytesPagina.length));
        archivoDTO.setClasificable(Boolean.TRUE);
        //Se busca el numero y tipo de identificacion del dueño con base en el Id de la solicitud
        infoArchivoClasificableDTO = obtenerPropietarioDocumento(idSolicitud, idPersona);
        if( infoArchivoClasificableDTO != null ){
        	archivoDTO.setTipoIdentificacionPropietario(
    			infoArchivoClasificableDTO.getTipoIdentificacionPropietario());
            archivoDTO.setNumeroIdentificacionPropietario(
        		infoArchivoClasificableDTO.getNumeroIdentificacionPropietario());
            archivoDTO.setTipoPropietario(
        		infoArchivoClasificableDTO.getTipoPropietario());
            archivoDTO.setIdInstanciaProceso(
        		infoArchivoClasificableDTO.getIdInstanciaProceso());
            strTipoTransaccionProcesoNombre = infoArchivoClasificableDTO.
        		getTipoTransaccion().getProceso().getDescripcion().concat(" - ").
        			concat(infoArchivoClasificableDTO.getTipoTransaccion().getDescripcion()
			); 
            archivoDTO.setProcessName(strTipoTransaccionProcesoNombre);
        }else{
        	archivoDTO.setClasificable(Boolean.FALSE);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return archivoDTO;
	}
    
    /**
     * Metodo que permite obtener el numero y tipo de identificacion del propietario 
     * del documento escaneado en base al identificador de la solicitud global
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idSolicitud
     * 	 	  	<code>String</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
     */
    private InformacionArchivoClasificableDTO obtenerPropietarioDocumento(String idSolicitud){
    	String firmaServicio = "AnalizadorArchivosPDF.obtenerPropietarioDocumento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null;
        Solicitud solicitud = null;
        
        //1- buscar solicitud global
        solicitud = buscarSolicitud(idSolicitud);
        if(solicitud != null && solicitud.getTipoTransaccion() != null){
        	infoArchivoClasificableDTO = catalogarTransaccion(solicitud);
        }else{
        	logger.error(firmaServicio + " :: " + "No existe la solicitud global indicada "
    			+ "en el codigo de barras del documento para su clasificacion");
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO;
    }
    
    /**
     * Metodo que permite obtener el numero y tipo de identificacion del propietario 
     * del documento escaneado en base al identificador de la solicitud global
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idSolicitud
     * 	 	  	<code>String</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
     */
    private InformacionArchivoClasificableDTO obtenerPropietarioDocumento(
    		String idSolicitud, String idPersona){
    	String firmaServicio = "AnalizadorArchivosPDF.obtenerPropietarioDocumento(String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Solicitud solicitud = null;
        Persona persona = null;
        InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null;
        TipoTransaccionEnum tipoTransaccion = null;
        ProcesoEnum procesoTransaccion = null;
        solicitud = buscarSolicitud(idSolicitud);
        if(solicitud != null && solicitud.getTipoTransaccion() != null){
        	persona = buscarPersona(idPersona);
        	if( persona  != null ){
        		infoArchivoClasificableDTO =  new InformacionArchivoClasificableDTO();
        		infoArchivoClasificableDTO.setIdSolicitud(Long.valueOf(idSolicitud));
        		infoArchivoClasificableDTO.setIdInstanciaProceso(solicitud.getIdInstanciaProceso());
        		infoArchivoClasificableDTO.setTipoIdentificacionPropietario(persona.getTipoIdentificacion());
        		infoArchivoClasificableDTO.setNumeroIdentificacionPropietario(persona.getNumeroIdentificacion());
        		tipoTransaccion = solicitud.getTipoTransaccion();
        		infoArchivoClasificableDTO.setTipoTransaccion(tipoTransaccion);
        		procesoTransaccion = tipoTransaccion.getProceso();		
        		switch (procesoTransaccion) {
				case POSTULACION_FOVIS_PRESENCIAL:
				case NOVEDADES_PERSONAS_PRESENCIAL:
				case AFILIACION_PERSONAS_PRESENCIAL:
				case LEGALIZACION_DESEMBOLSO_FOVIS:
					infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
					break;
				case NOVEDADES_EMPRESAS_PRESENCIAL: 
				case AFILIACION_EMPRESAS_PRESENCIAL:
				case NOVEDADES_FOVIS_REGULAR:
				case NOVEDADES_FOVIS_ESPECIAL:
					infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.EMPLEADOR);
					break;
				default: 
					logger.error(firmaServicio + " :: " + "El documento no corresponde con "
    	    			+ "un proceso presencial valido para su clasificacion");
					break;
				}
        		
        	}
        }else{
        	logger.error(firmaServicio + " :: " + "No existe la solicitud global indicada "
    			+ "en el codigo de barras del documento para su clasificacion");
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO;
    }
    
    /**
     * Metodo que permite realizar la consulta de una solicitud por su identificador
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idSolicitud
     * 	 	  	<code>String</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>Solicitud</code>
     * 		 	Retorna la solicitud encontrada 			
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Solicitud buscarSolicitud(String idSolicitud){
    	String firmaServicio = "AnalizadorArchivosPDF.obtenerPropietarioDocumento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Solicitud solicitudEntity = null;
        try {
        	solicitudEntity = entityManager.createNamedQuery(
                NamedQueriesConstants.BUSCAR_SOLICITUD_POR_ID_SOLICITUD, Solicitud.class)
                .setParameter("idSolicitud", Long.valueOf(idSolicitud)).getSingleResult();
        } catch (NoResultException noe) {
        	
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return solicitudEntity;
    }
    
    /**
     * Metodo que permite realizar la consulta de una persona por su identificador
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	idSolicitud
     * 	 	  	<code>String</code>
     * 		  	El identificador de la persona
     * 
     * @return  <code>Persona</code>
     * 		 	Retorna la persona encontrada 			
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Persona buscarPersona(String idPersona){
    	String firmaServicio = "AnalizadorArchivosPDF.buscarPersona(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        Persona personaEntity = null;
        try {
        	personaEntity = entityManager.createNamedQuery(
                    NamedQueriesConstants.BUSCAR_PERSONA_POR_ID, Persona.class)
                    .setParameter("idPersona", Long.valueOf(idPersona)).getSingleResult();
        } catch (NoResultException noe) {
        	
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return personaEntity;
    }
    
    /**
     * Metodo que permite catalogar el tipo de transaccion para identificar 
     * a que solicitud especifica se debe consultar
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param 	tipoTransaccion
     * 	 	  	<code>TipoTransaccionEnum</code>
     * 		  	El tipo de transaccion asociado a la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo 			
     */
    private InformacionArchivoClasificableDTO catalogarTransaccion(Solicitud solicitudEntity){
    	String firmaServicio = "AnalizadorArchivosPDF.catalogarTransaccion(TipoTransaccionEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null;
        TipoTransaccionEnum tipoTransaccion = null;
        ProcesoEnum procesoTransaccion = null;
        Long idSolicitudGlobal = null;
        
        tipoTransaccion = solicitudEntity.getTipoTransaccion();
        procesoTransaccion = tipoTransaccion.getProceso();
        idSolicitudGlobal = solicitudEntity.getIdSolicitud();
        
        //Dependiendo del proceso que agrupe el tipo de transacion especifica se 
        //consulta la solicitud especifica correspondiente para obtener la informacion del propietario del documento
        if(ProcesoEnum.POSTULACION_FOVIS_PRESENCIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudPostulacionFovisPresencial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
        	}	
        }else if(ProcesoEnum.NOVEDADES_EMPRESAS_PRESENCIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudNovedadesEmpresasPresencial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.EMPLEADOR);
        	}
        }else if(ProcesoEnum.NOVEDADES_PERSONAS_PRESENCIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudNovedadesPersonasPresencial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
        	}
        }else if(ProcesoEnum.AFILIACION_EMPRESAS_PRESENCIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudAfiliacionEmpresasPresencial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.EMPLEADOR);
        	}
        }else if(ProcesoEnum.AFILIACION_PERSONAS_PRESENCIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudAfiliacionPersonasPresencial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
        	}
        }else if(ProcesoEnum.LEGALIZACION_DESEMBOLSO_FOVIS.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudLegalizacionDesembolsoFOVIS(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
        	}
        }else if(ProcesoEnum.NOVEDADES_FOVIS_REGULAR.equals(procesoTransaccion) || 
    		ProcesoEnum.NOVEDADES_FOVIS_ESPECIAL.equals(procesoTransaccion)){
        	infoArchivoClasificableDTO = buscarSolicitudNovedadFOVISRegularEspecial(idSolicitudGlobal);
        	if(infoArchivoClasificableDTO != null){
        		infoArchivoClasificableDTO.setTipoPropietario(TipoPropietarioArchivoEnum.PERSONA);
        	}
        }else{
        	logger.error(firmaServicio + " :: " + "El documento no corresponde con "
    			+ "un proceso presencial valido para su clasificacion");
        }	
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return infoArchivoClasificableDTO;
    }
    
    /**
	 * Metodo encargado de consultar la solicitud Postulacion Fovis Presencial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudPostulacionFovisPresencial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudPostulacionFovisPresencial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_POSTULACION_FOVIS_PRESENCIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Novedades Empresas Presencial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudNovedadesEmpresasPresencial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudNovedadesEmpresasPresencial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDADES_EMPRESAS_PRESENCIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Novedades Personas Presencial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudNovedadesPersonasPresencial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudNovedadesPersonasPresencial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDADES_PERSONAS_PRESENCIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Afiliacion Empresas Presencial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudAfiliacionEmpresasPresencial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudAfiliacionEmpresasPresencial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_EMPRESAS_PRESENCIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Afiliacion Personas Presencial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudAfiliacionPersonasPresencial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudAfiliacionPersonasPresencial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_AFILIACION_PERSONAS_PRESENCIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Legalizacion Desembolso FOVIS
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudLegalizacionDesembolsoFOVIS(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudLegalizacionDesembolsoFOVIS(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_LEGALIZACION_DESEMBOLSO_FOVIS_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
	/**
	 * Metodo encargado de consultar la solicitud de Solicitud de Novedad FOVIS Regular o Especial
	 * y obtener la informacion requerida por el archivo para su posterior clasificacion
	 * 
	 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
	 * 
	 * @param 	idSolicitudGlobal
	 * 	 	  	<code>Long</code>
     * 		  	El identificador de la solicitud global
     * 
     * @return  <code>InformacionArchivoClasificableDTO</code>
     * 		 	Retorna la informacion de un archivo procedente de clasificacion por escaneo masivo
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private InformacionArchivoClasificableDTO buscarSolicitudNovedadFOVISRegularEspecial(Long idSolicitudGlobal) {
		String firmaServicio = "AnalizadorArchivosPDF.buscarSolicitudNovedadFOVISRegularEspecial(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		InformacionArchivoClasificableDTO infoArchivoClasificableDTO = null; 
		try {
			infoArchivoClasificableDTO = (InformacionArchivoClasificableDTO) entityManager.createNamedQuery(
				NamedQueriesConstants.BUSCAR_SOLICITUD_NOVEDAD_FOVIS_REGULAR_ESPECIAL_POR_ID_SOLICITUD)
				.setParameter("idSolicitud", idSolicitudGlobal).getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
    	logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
		return infoArchivoClasificableDTO; 
	}
	
}
