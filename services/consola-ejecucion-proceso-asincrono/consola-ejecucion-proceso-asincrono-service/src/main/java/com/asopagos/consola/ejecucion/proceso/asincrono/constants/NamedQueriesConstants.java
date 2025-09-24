package com.asopagos.consola.ejecucion.proceso.asincrono.constants;

/**
 * <b>Descripción:</b> Contiene los nombres de las consultas creadas
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Consulta la ultima ejecucion sin revisar por tipo proceso
     */
    public static final String CONSULTAR_ULTIMA_EJECUCION_TIPO_PROCESO = "consultar.ultima.ejecucion.tipo.proceso";

    /**
     * Consulta la ultima ejecucion sin revisar por tipo proceso e id de proceso
     */
    public static final String CONSULTAR_EJECUCION_BY_TIPO_AND_ID_PROCESO = "consultar.ejecucion.by.tipo.and.id.proceso";
    
    /**
     * Consulta la ejecucion por el id
     */
    public static final String CONSULTAR_EJECUCION_BY_ID = "consultar.ejecucion.by.id";
}
