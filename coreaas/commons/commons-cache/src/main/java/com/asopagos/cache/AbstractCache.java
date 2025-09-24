package com.asopagos.cache;

import java.util.Map;

/**
 * Interface que reprensenta la implementación de una de las clases que gestionan la cache
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public abstract class AbstractCache {
    
    /**
     * Establece la lista de parametros en el caché
     * @param parametros
     */
    public abstract void setParametros(Map<String, String> parametros);
    
    /**
     * Establece la lista de constantes en el caché
     * @return Retorna mapa con todos los parámetros
     */    
    public abstract Map<String, String> getParametros();   
    
        /**
     * Establece la lista de parametros en el caché
     * @param parametros
     */
    public abstract void setParametrosGap(Map<String, String> parametros);
    
    /**
     * Establece la lista de constantes en el caché
     * @return Retorna mapa con todos los parámetros
     */    
	public abstract Object getParametroGap(String key);
    
    /**
     * Establece la lista de constantes en el caché
     * @param constantes 
     */    
    public abstract void setConstantes(Map<String, String> constantes);

	/**
	 * Retorna la constante identificada por key definida en la tabla de 
     * constantes.
     * 
	 * @param key Llave que referencia en cache el valor buscado
	 * @return Object con el valor buscado.
	 */
	public abstract Object getConstante(String key);
	
	/**
	 * Retorna el parámetro identificado por key definido en la tabla de 
     * parametros.
     * 
	 * @param key Llave que referencia en cache el valor buscado
	 * @return Object con el valor buscado.
	 */
	public abstract Object getParametro(String key);
	
	/**
	 * Actualiza o agrega un valor dado en cache.
	 * 
	 * @param llave Nombre del parámetro a agregar o actualizar.
	 * @param valor Valor del parámetro a agregar o actualizar.
	 */
	public abstract void updateCacheParametro(String llave, String valor);
    
    /**
     * Actualiza los parámetros y constantes cargados en cache mediante la 
     * recarga de los datos desde la base de datos
     */
    public void sincronizarParametrosYConstantes() {
        setConstantes(DatosCacheDAO.obtenerDatosConstantes());
        setParametros(DatosCacheDAO.obtenerDatosParametros());
        setParametrosGap(DatosCacheDAO.obtenerDatosParametrosGap());

    }
    
}
