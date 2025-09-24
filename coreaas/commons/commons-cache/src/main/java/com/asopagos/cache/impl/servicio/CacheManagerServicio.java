package com.asopagos.cache.impl.servicio;

import java.util.HashMap;
import java.util.Map;
import com.asopagos.cache.DatosCacheDAO;
import com.asopagos.cache.AbstractCache;
import com.asopagos.enumeraciones.cache.GestorCacheEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class CacheManagerServicio extends AbstractCache {
    

	private Map<String, String> mapaParametros;

	private Map<String, String> mapaParametrosGap;
    
	private Map<String, String> mapaConstantes;
    
	private static final ILogger log = LogManager.getLogger(CacheManagerServicio.class); 
	
    
	public CacheManagerServicio() {
		log.info("El gestor que se está cargando es: " + GestorCacheEnum.SERVICIO.getNombre());
		mapaParametros = DatosCacheDAO.obtenerDatosParametros();
		mapaConstantes = DatosCacheDAO.obtenerDatosConstantes();
	}

	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#setParametros(java.util.Map) 
	 */	
    @Override
    public void setParametros(Map<String, String> parametros) {
        mapaParametros = parametros;
    }

		/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#setParametros(java.util.Map) 
	 */	
    @Override
    public void setParametrosGap(Map<String, String> parametros) {
        mapaParametrosGap = parametros;
    }

	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#getParametros() 
	 */
    @Override
    public Map<String, String> getParametros() {
        return new HashMap(mapaParametros);
    }

			/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#getParametro(java.lang.String)
	 */
	@Override
	public Object getParametroGap(String key) {
        //String valor = mapaParametros.get(key);
        String valor = DatosCacheDAO.obtenerParametroGap(key);
        log.debug("Parámetro " + key + " recuperado desde caché con valor " + valor);
        return valor;
	}

	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#setConstantes(java.util.Map) 
	 */    
    @Override
    public void setConstantes(Map<String, String> constantes) {
        mapaConstantes = constantes;
    }
    
	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#getConstante(java.lang.String)
	 */
	@Override
	public Object getConstante(String key) {
        //String valor = mapaConstantes.get(key);
        String valor = DatosCacheDAO.obtenerConstante(key);
        log.debug("Constante " + key + " recuperada desde caché con valor " + valor);
        return valor;
	}

	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#getParametro(java.lang.String)
	 */
	@Override
	public Object getParametro(String key) {
        //String valor = mapaParametros.get(key);
        String valor = DatosCacheDAO.obtenerParametro(key);
        log.debug("Parámetro " + key + " recuperado desde caché con valor " + valor);
        return valor;
	}

	/** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#updateCache(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateCacheParametro(String llave, String valor) {
		mapaParametros.put(llave, valor);
	}
    
}
