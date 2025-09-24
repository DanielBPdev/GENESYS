package com.asopagos.notificaciones.enums;

/**
 * Enumeración que contiene el nombre del proceso y las clases para procesar después del envío de un comunicado.
 * @author Steven Quintero <squintero@heinsohn.com.co>
 *
 */
public enum EnvioComunicadoProcesoEnum {
    
    /**
     * Proceso de PILA.
     */
    PILA("PILA", null, "com.asopagos.notificaciones.envio.EnvioComunicadoFallidoPila", Boolean.FALSE),
    /**
     * Proceso de gestión preventiva de cartera.
     */
    GESTION_PREVENTIVA_CARTERA("GESTION_PREVENTIVA_CARTERA","com.asopagos.notificaciones.envio.EnvioComunicadoExitosoGestionPreventiva","com.asopagos.notificaciones.envio.EnvioComunicadoFallidoGestionPreventiva", Boolean.TRUE),
    
    /**
     * Proceso de afiliación (Intentos de afiliación)
     */
    AFILIACION_EMPRESAS_PRESENCIAL("AFILIACION_EMPRESAS_PRESENCIAL","com.asopagos.notificaciones.envio.EnvioComunicadoIntentoAfiliacion","com.asopagos.notificaciones.envio.EnvioComunicadoIntentoAfiliacion",Boolean.FALSE),
    
    /**
     * Proceso aportes manuales
     */
    DEVOLUCION_APORTES("DEVOLUCION_APORTES","com.asopagos.notificaciones.envio.EnvioComunicadoCierreAporteManual","com.asopagos.notificaciones.envio.EnvioComunicadoCierreAporteManual",Boolean.FALSE);
    
    /**
     * Nombre del proceso.
     */
    private String nombre;

    /**
     * Ruta cualificada para procesar cuando el comunicado fue enviado.
     */
    private String claseExito;

    /**
     *Ruta cualificada para procesar cuando el comunicado NO fue enviado. 
     */
    private String claseFallido;
    
    /**
     *Ruta cualificada para procesar cuando el comunicado NO fue enviado. 
     */
    private Boolean validarProceso;

    private EnvioComunicadoProcesoEnum(String nombre, String claseExito, String claseFallido, Boolean validarProceso) {
        this.nombre = nombre;
        this.claseExito = claseExito;
        this.claseFallido = claseFallido;
        this.validarProceso = validarProceso;
    }

    /**
     * Método que permite obtener el nombre de la clase cuando el proceso BPM es exitoso
     * a partir del nombre del proceso
     * @param procesoEvento
     *        es el nombre del proceso a buscar.
     * 
     * @return String con el nombre completo de la clase
     */
    public static String obtenerClaseProcesoExitoso(String procesoEvento) {

        for (EnvioComunicadoProcesoEnum envioComunicadoProcesoEnum : EnvioComunicadoProcesoEnum.values()) {
            if (envioComunicadoProcesoEnum.getNombre().equals(procesoEvento)) {
                return envioComunicadoProcesoEnum.getClaseExito();
            }
        }
        return null;
    }

    /**
     * Método que permite obtener la enumeración teniendo como parametro un proceso
     * @param procesoEvento
     *        es el nombre del proceso a buscar.
     * 
     * @return EnvioComunicadoProcesoEnum enumeración para el proceso 
     */
    public static EnvioComunicadoProcesoEnum obtenerEnumeracionPorProceso(String procesoEvento) {

        for (EnvioComunicadoProcesoEnum envioComunicadoProcesoEnum : EnvioComunicadoProcesoEnum.values()) {
            if (envioComunicadoProcesoEnum.getNombre().equals(procesoEvento)) {
                return envioComunicadoProcesoEnum;
            }
        }
        return null;
    }

    /**
     * Método que permite obtener el nombre de la clase cuando el proceso BPM es fallido
     * a partir del nombre del proceso
     * @param procesoEvento
     *        es el nombre del proceso a buscar.
     * 
     * @return String con el nombre completo de la clase
     */
    public static String obtenerClaseProcesoFallido(String procesoEvento) {

        for (EnvioComunicadoProcesoEnum envioComunicadoProcesoEnum : EnvioComunicadoProcesoEnum.values()) {
            if (envioComunicadoProcesoEnum.getNombre().equals(procesoEvento)) {
                return envioComunicadoProcesoEnum.getClaseFallido();
            }
        }
        return null;
    }
    
    /**
     * Método que retorna el valor de nombre.
     * @return valor de nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que retorna el valor de claseExito.
     * @return valor de claseExito.
     */
    public String getClaseExito() {
        return claseExito;
    }

    /**
     * Método que retorna el valor de claseFallido.
     * @return valor de claseFallido.
     */
    public String getClaseFallido() {
        return claseFallido;
    }

    /**
     * @return the validarProceso
     */
    public Boolean getValidarProceso() {
        return validarProceso;
    }

}
