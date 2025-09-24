package com.asopagos.listas.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase utilitaria para la lectura y cargue del archivo de
 * configuración de listas y enumeraciones
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class ConfiguracionListaReader {
	
	/**
     * Map que almacena la configuración de listas establecida en el archivo xml
     */
	private static final Map<Integer, ConfiguracionLista> configuracionListas;
    
    /**
     * Referencia al Pattern para la expresión regular de números
     */
    public static final Pattern pattern = Pattern.compile(ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS); 
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConfiguracionListaReader.class);
    
    
	/**
	 * Contexto estático para inicialización de la lectura del archivo de 
     * configuración de listas
	 */
	static {
		
		configuracionListas = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(
                    ConfiguracionListaReader.class.getClassLoader().getResourceAsStream("listas-config.xml"));

            NodeList listas = doc.getElementsByTagName("lista");
            for (int i = 0; i < listas.getLength(); i++) {
            	Node lista = listas.item(i);
                Element element = (Element) lista;
                ConfiguracionLista configuracionLista = new ConfiguracionLista();
                configuracionLista.setIdLista(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
                configuracionLista.setNombreClase(element.getElementsByTagName("clase").item(0).getTextContent());
                configuracionLista.setCampoNombre(element.getElementsByTagName("campo-nombre").item(0).getTextContent());
                
                if (!Class.forName(element.getElementsByTagName("clase").item(0).getTextContent()).isEnum()) {
                    configuracionLista.setCampoCodigo(element.getElementsByTagName("campo-codigo").item(0).getTextContent());
                }
                NodeList nodeAtributos = element.getElementsByTagName("atributo");
                if (nodeAtributos.getLength() > 0) {
                    configuracionLista.setAtributos(procesarAtributos(nodeAtributos, configuracionLista));
                }
                configuracionListas.put(configuracionLista.getIdLista(), configuracionLista);
            }
		} catch (Exception e) {
            String mensajeError = "Error procesando el archivo de onfiguración de listas";
            logger.error(mensajeError, e);
            throw new TechnicalException(mensajeError, e);
		} 
	}
    
    /**
     * Método encargado de procesar los atributos definidos para cada lista
     * @throws DOMException 
     * @throws ClassNotFoundException 
     */
    private static Map<String, Object> procesarAtributos (
            NodeList nodeAtributos, ConfiguracionLista configLista) throws ClassNotFoundException, DOMException {
        
        Map<String, Object> atributos = new HashMap<String, Object>();
        for (int i = 0; i < nodeAtributos.getLength(); i++) {
            Node atributo = nodeAtributos.item(i);
            if (Class.forName(configLista.getNombreClase()).isEnum()) {
                atributos.put(atributo.getTextContent(), null);
            } else {
                atributos.put(atributo.getTextContent(), null);
            }
        }
        logger.debug(atributos);
        return atributos;
    }
	
	/**
     * Retorna la configuración para la lista identificada por 
     * <source>idLista</source>
     * @param idLista
     * @return 
     */
	public static ConfiguracionLista obtenerConfiguracionLista(Integer idLista) {
		return configuracionListas.get(idLista);
	}
	
}
