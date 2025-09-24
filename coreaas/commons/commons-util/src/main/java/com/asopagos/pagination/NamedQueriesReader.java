package com.asopagos.pagination;


/**
 * <b>Descripcion:</b> Clase que lee el archivo de named queries del servico actual y lo almacena en memoria<br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXB;

public class NamedQueriesReader {

	/**
	 * Nombre de la propiedad a configurar para identificar el archivo de namedqueries a cargar
	 */
	public static final String NAMED_QUERIES_XML_PATH_PROPERTIE_NAME = "asopagos.namedquery.file";
	
	/**
	 * Ruta por defecto del archivo de namedqueries a cargar
	 */
	private static final String NAMED_QUERIES_DEFAULT_XML_PATH = "/META-INF/jpql/NamedQueries.xml";

    private static NamedQueriesReader instance;
    
    /**
     * Mapa que almacenara las namedqueries existentes en cada archivo de namedqueries relacionado
     */
    private Map<String, Map> namedQueryFiles = new HashMap<>();
    
    public static NamedQueriesReader getInstance() {
        if (instance == null) {
            instance = new NamedQueriesReader();
        }
        return instance;
    }

    private NamedQueriesReader() {
        init();
    }

    /**
     * @return the readedNamedQueries para el path por defecto
     */
    public Map<String, NamedQueryRead> getReadedNamedQueries() {
        return namedQueryFiles.get(NAMED_QUERIES_DEFAULT_XML_PATH);
    }

    /**
     * @return the readedNamedQueries para el pat indicado explicitamente
     */
    public Map<String, NamedQueryRead> getReadedNamedQueries(String namedQueriesPath) {
    	if(!namedQueryFiles.containsKey(namedQueriesPath)){
    		loadNamedQueries(namedQueriesPath); 
    	}
        return namedQueryFiles.get(namedQueriesPath);
    }
    
    /**
     * @param readedNamedQueries the readedNamedQueries to set
     */
    public void setReadedNamedQueries(Map<String, NamedQueryRead> readedNamedQueries) {
        this.namedQueryFiles.put(NAMED_QUERIES_DEFAULT_XML_PATH, readedNamedQueries);
    }

    /**
     * Realiza carga inicial de los namedqueries definidos en la ruta por defecto
     */
    private void init() {
        
        loadNamedQueries(NAMED_QUERIES_DEFAULT_XML_PATH);
    }

    /**
     * Realiza la lectura de los namedqueries de la ruta recibida por parametro
     * @param namedQueriesPath Ruta especifica del archivo namedqueries a cargar
     */
    private void loadNamedQueries(String namedQueriesPath) {

        //NamedQueries Xml file
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(namedQueriesPath);
        
        EntityMappingsRead map = JAXB.unmarshal(in, EntityMappingsRead.class);

        List<NamedQueryRead> listNamedQueries = map.getNamedQueries();
        
        List<NamedNativeQueryRead> listNamedNativeQueries = map.getNamedNativeQueries();
        
        Map<String,Object> readedNamedQueries = new HashMap<>();
        
        if (listNamedQueries != null) {
            for (NamedQueryRead namedQueryRead : listNamedQueries) {

                readedNamedQueries.put(namedQueryRead.getName(), namedQueryRead);
            }
        }
        if (listNamedNativeQueries != null) {
            for (NamedNativeQueryRead namedNativeQueryRead : listNamedNativeQueries) {

                readedNamedQueries.put(namedNativeQueryRead.getName(), namedNativeQueryRead);
            }
        }
        namedQueryFiles.put(namedQueriesPath, readedNamedQueries);
        
    }

    
}
