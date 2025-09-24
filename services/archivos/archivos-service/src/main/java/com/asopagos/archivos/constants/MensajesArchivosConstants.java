package com.asopagos.archivos.constants;

/**
 * <b>Descripción:</b> Clase que define constantes para los mensajes de negocio para el 
 * servicio de archivos
 * 
 * <b>HU:</b> Asopagos - HU Transversal - 444<br/>
 * 
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co">hhernandez@heinsohn.com.co</a>
 * 
 */
public class MensajesArchivosConstants {
    
	/**
     * Mensaje general de procesamiento de archivo exitoso
     */
    public static final String PROCESAMIENTO_ARCHIVO_EXITOSO = "Se ha realizado el proceso de clasificación satisfactoriamente";
    
    /**
     * Mensaje general de procesamiento de archivo no exitoso
     */
    public static final String PROCESAMIENTO_NO_EXITOSO = "No se logro realizar el proceso de clasificación, "
    		+ "apesar de haber identificado documentos en el archivo. Verifique que el archivo sea válido "
    		+ "y las etiquetas de documentos correspondan con requisitos documentales validos";
    
    /**
     * Mensaje que indica que procesamiento de archivo es no exitoso por falta de etiquetas de documento presentes
     */
    public static final String ERROR_ARCHIVO_CARGUE_ETIQUETAS_PROCESAMIENTO_NO_EXITOSO = "No se logro realizar el proceso de clasificación, "
    		+ "no se encontraron etiquetas de documento presentes en el archivo";
	
    /**
     * Mensaje que indica error por etiquetas en el analisis del archivo sujeto de clasificación 
     */
    public static final String ERROR_ARCHIVO_CARGUE_ETIQUETAS_INVALIDAS = "El archivo no cumple con las condiciones requeridas. "
		+ "Por favor verificar que todas las páginas tengan la etiqueta del "
		+ "documento correspondiente y que sea una etiqueta válida";
    	
}