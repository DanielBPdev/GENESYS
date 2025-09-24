package com.asopagos.log;

/**
 * <b>Descripción:</b> Clase que actúa como fábrica de Logging de acuerdo a la
 * configuración establecida
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class LogManager {
    
    /**
     * Constante para el API de logging Log4J
     */
    private static final String LOG4J = "Log4J";
    
    /**
     * Variable que determina el API de logging configurado
     */
    private static final String logType;
    
    /**
     * Bloque de inicialización estático para determinar el API de log a usar
     */
    static {
        //TODO Leer la configuración del log desde un archivo de configuración
        logType = LOG4J;
    }
    
    /**
     * Método utilitario que instancia el objeto de logging a usar de acuerdo
     * a la configuración
     * @param clazz
     * @return 
     */
    public static ILogger getLogger(Class clazz) {
        if (logType.equals(LOG4J)) {
            return new Log4JLogger(clazz);
        } else {
            return null;
        }
    }
    
}
