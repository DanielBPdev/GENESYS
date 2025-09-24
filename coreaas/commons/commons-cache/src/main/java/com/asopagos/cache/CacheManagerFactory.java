package com.asopagos.cache;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.enumeraciones.cache.GestorCacheEnum;

/**
 * Clase factory encargada de instanciar una implementación de com.asopagos.cache.Cache
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class CacheManagerFactory {

	/**
     * Referencia al logger
     */
	private static final ILogger logger = LogManager.getLogger(CacheManagerFactory.class);
	
	protected static AbstractCache instance;
	
    
	private CacheManagerFactory() {
		// constructor vacío para instanciar la clase CacheManager
	}
	
	/**
     * Dependiendo de la propiedad nombreGestor se instancia la implementación de
     * com.asopagos.cache.Cache que se requiera.
     * 
     * @param nombreGestor
     * 			nombre del gestor de cache que se busca instanciar
     * 
     * @return Instancia de com.asopagos.cache.Cache
     */
	public static AbstractCache getInstance(String nombreGestor) {
        
		String clase = null;	
		if (GestorCacheEnum.INFINISPAN.getNombre().equals(nombreGestor)) {
			clase = GestorCacheEnum.INFINISPAN.getClase();
		} else if (nombreGestor == null || GestorCacheEnum.SERVICIO.getNombre().equals(nombreGestor)) {
			clase = GestorCacheEnum.SERVICIO.getClase();
		}
		if (clase != null) {
			try {
				instanciarImplementacion(clase);
			} catch (Exception e) {
                try {
                    instanciarImplementacion(GestorCacheEnum.SERVICIO.getClase());
                } catch (Exception ex) {                    
                }
			}
		} else {
		    instance = null;
		}
		return instance;
	}
	
	/**
	 * crea la instancia de la clase requerida
	 * 
	 * @param clase
	 */
	private static void instanciarImplementacion(String clase) throws Exception {
		try {
			instance = (AbstractCache) Class.forName(clase).newInstance();
		} catch (Exception e) {
			logger.error("No se pudo instanciar la clase: " + clase, e);
			throw e;
		}
	}

}
