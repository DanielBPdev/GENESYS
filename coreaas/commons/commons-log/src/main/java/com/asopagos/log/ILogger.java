package com.asopagos.log;

/**
 * <b>Descripción:</b> Interface que determina el comportamiento de un 
 * objeto genérico Logger para la aplicación
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public interface ILogger {
    
    /**
     * Método para el registro de logs con prioridad Info
     * @param object
     */
    public void info(Object object);
    
    /**
     * Método para el registro de logs con prioridad Info y un objeto Throwable
     * @param object
     * @param throwable
     */    
    public void info(Object object, Throwable throwable);
    
    /**
     * Método para el registro de logs con prioridad Info
     * @param object
     */    
    public void trace(Object object);
    
    /**
     * Método para el registro de logs con prioridad Info y un objeto Throwable
     * @param object
     * @param throwable
     */     
    public void trace(Object object, Throwable throwable);

    /**
     * Método para el registro de logs con prioridad Info
     * @param object
     */    
    public void debug(Object object);
    
    /**
     * Método para el registro de logs con prioridad Info y un objeto Throwable
     * @param object
     * @param throwable
     */     
    public void debug(Object object, Throwable throwable);
    
    /**
     * Método para el registro de logs con prioridad Info
     * @param object
     */    
    public void error(Object object);
    
    /**
     * Método para el registro de logs con prioridad Info y un objeto Throwable
     * @param object
     * @param throwable
     */     
    public void error(Object object, Throwable throwable);

    /**
     * Método para el registro de logs con prioridad Info
     * @param object
     */    
    public void fatal(Object object);
    
    /**
     * Método para el registro de logs con prioridad Info y un objeto Throwable
     * @param object
     * @param throwable
     */     
    public void fatal(Object object, Throwable throwable);
    
    /**
     * Método para el registro de logs con prioridad Warnning
     * @param object
     */    
    public void warn(Object object);
    
    /**
     * Método para el registro de logs con prioridad Warnning y un objeto Throwable
     * @param object
     * @param throwable
     */     
    public void warn(Object object, Throwable throwable);
    
}
