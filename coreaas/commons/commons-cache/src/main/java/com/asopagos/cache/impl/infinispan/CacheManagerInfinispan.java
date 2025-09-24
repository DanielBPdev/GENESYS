package com.asopagos.cache.impl.infinispan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import com.asopagos.cache.AbstractCache;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.util.HashMap;

/**
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class CacheManagerInfinispan extends AbstractCache {
    

    private static final ILogger logger = LogManager.getLogger(CacheManagerInfinispan.class);
    
    private static final String HOTROD_CLIENT_PROPERTIES = "hotrod-client.properties";

    private static final String NOMBRE_CACHE_PARAMETROS = "parametros";

    private static final String NOMBRE_CACHE_PARAMETROS_GAPS = "parametrosGap";
    
    private static final String NOMBRE_CACHE_CONSTANTES = "constantes";

    private Cache<String, String> cacheParametros;
    
    private Cache<String, String> cacheParametrosGap;

    private Cache<String, String> cacheConstantes;
    

    public CacheManagerInfinispan() {
        logger.info("El cache que se está implementando es INFINISPAN");
        try {
            Properties properties = leerPropiedadesHotRod();
            CachingProvider jcacheProvider = Caching.getCachingProvider();
            CacheManager cacheManager = jcacheProvider.getCacheManager(
                    jcacheProvider.getDefaultURI(), jcacheProvider.getDefaultClassLoader(), properties);

            cacheParametros = cacheManager.getCache(NOMBRE_CACHE_PARAMETROS);
            if (cacheParametros == null) {
                MutableConfiguration<String, String> configuration = new ServicioCacheConfiguration();
                configuration.setTypes(String.class, String.class);
                configuration.setStatisticsEnabled(true);
                cacheParametros = cacheManager.createCache(NOMBRE_CACHE_PARAMETROS, configuration);
            }
            cacheConstantes = cacheManager.getCache(NOMBRE_CACHE_CONSTANTES);
            if (cacheConstantes == null) {
                MutableConfiguration<String, String> configuration = new ServicioCacheConfiguration();
                configuration.setTypes(String.class, String.class);
                configuration.setStatisticsEnabled(true);
                cacheConstantes = cacheManager.createCache(NOMBRE_CACHE_CONSTANTES, configuration);
            }
            cacheParametrosGap = cacheManager.getCache(NOMBRE_CACHE_PARAMETROS_GAPS);
            if (cacheParametrosGap == null) {
                MutableConfiguration<String, String> configuration = new ServicioCacheConfiguration();
                configuration.setTypes(String.class, String.class);
                configuration.setStatisticsEnabled(true);
                cacheParametrosGap = cacheManager.createCache(NOMBRE_CACHE_PARAMETROS_GAPS, configuration);
            }
        } catch (IllegalArgumentException | IOException e) {
            logger.error("No se pudo crear alguno de los cachés " + NOMBRE_CACHE_PARAMETROS + " o " + NOMBRE_CACHE_CONSTANTES);
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga el archivo hotrod-client.properties del contexto
     * @return 
     * @throws java.io.IOException 
     */
    public static Properties leerPropiedadesHotRod() throws IOException {
        try {
            InputStream input = CacheManagerInfinispan.class.getClassLoader().getResourceAsStream(HOTROD_CLIENT_PROPERTIES);
            Properties properties = new Properties();
            properties.load(input);
            logger.info("Cargado archivo " + HOTROD_CLIENT_PROPERTIES);
            return properties;
        } catch (IOException e) {
            logger.error("No se pudo cargar el archivo " + HOTROD_CLIENT_PROPERTIES, e);
            throw e;
        }
    }

    @Override
    public void setParametros(Map<String, String> parametros) {
        cacheParametros.putAll(parametros);
    }

    @Override
    public void setParametrosGap(Map<String, String> parametros) {
        cacheParametrosGap.putAll(parametros);
    }
    
    @Override
    public Map<String, String> getParametros() {
        Map<String, String> map = new HashMap();
        cacheParametros.forEach((param) -> {
            map.put(param.getKey(), param.getValue());
        });
        return map;
    }    

    @Override
    public void setConstantes(Map<String, String> constantes) {
        cacheConstantes.putAll(constantes);
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.cache.AbstractCache#getConstante(java.lang.String)
     */
    @Override
    public Object getConstante(String key) {
        return cacheConstantes.get(key);
    }

    /** (non-Javadoc)
     * @see com.asopagos.cache.AbstractCache#getParametro(java.lang.String)
     */
    @Override
    public Object getParametro(String key) {
        return cacheParametros.get(key);
    }

        /** (non-Javadoc)
     * @see com.asopagos.cache.AbstractCache#getParametroGap(java.lang.String)
     */
    @Override
    public Object getParametroGap(String key) {
        return cacheParametrosGap.get(key);
    }

    /** (non-Javadoc)
	 * @see com.asopagos.cache.AbstractCache#updateCache(java.lang.String, java.lang.String)
     */
    @Override
    public void updateCacheParametro(String llave, String valor) {
        cacheParametros.put(llave, valor);
    }

}
