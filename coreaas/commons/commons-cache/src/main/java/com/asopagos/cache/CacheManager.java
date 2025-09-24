package com.asopagos.cache;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripción:</b> Clase manejadora de datos de caché
 * <b>Historia de Usuario:</b> Transversal Caché
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 * @author Steven Quintero <squintero@heinsohn.com.co>
 */
public class CacheManager {
    

    private static final String GESTION_CACHE = "gestor-cache";

    private static final AbstractCache cache;

    private static final ILogger log = LogManager.getLogger(CacheManager.class);


    static {
        String gestorCache;
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("cache");
            gestorCache = resourceBundle.getString(GESTION_CACHE);
        } catch (MissingResourceException mre) {
            gestorCache = System.getProperty(GESTION_CACHE, System.getenv(GESTION_CACHE));
        } 
        cache = CacheManagerFactory.getInstance(gestorCache);
    }

    /**
     * @return the cache
     */
    public static AbstractCache getCache() {
        return cache;
    }

    /**
     * Retorna la constante identificada por key definida en la tabla de
     * constantes.
     *
     * @param key llave que referencia en cache el valor buscado
     *
     * @return Object con el valor buscado.
     */
    public static Object getConstante(String key) {
        return cache.getConstante(key);
    }

    /**
     * Retorna el parámetro identificado por key definido en la tabla de
     * parametros.
     *
     * @param key llave que referencia en cache el valor buscado
     *
     * @return Object con el valor buscado.
     */
    public static Object getParametro(String key) {
        return cache.getParametro(key);
    }

     /**
     * Retorna el parámetro identificado por key definido en la tabla de
     * parametros.
     *
     * @param key llave que referencia en cache el valor buscado
     *
     * @return Object con el valor buscado.
     */
    public static Object getParametroGap(String key) {
        return cache.getParametroGap(key);
    }

    /**
     * Actualiza o agrega un valor dado en cache.
     *
     * @param llave nombre del parámetro a agregar o actualizar.
     *
     * @param valor valor del parámetro a agregar o actualizar.
     */
    public static void sincronizarParametro(String llave, String valor) {
        cache.updateCacheParametro(llave, valor);
    }

    /**
     * Método que recupera los parámetros
     * @return Mapa que contiene todos los paráetros del sistema
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> consultarParametros() {
        return cache.getParametros();
    }

    /**
     * sincroniza la información entre base de datos y cache
     */
    public static void sincronizarParametrosYConstantes() {
        cache.sincronizarParametrosYConstantes();
    }

}
