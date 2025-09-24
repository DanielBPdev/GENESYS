package com.asopagos.subsidiomonetario.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio<br>
 * <b>Módulo:</b> Subsidios HU-311-434 <br/>
 */
public class NamedQueriesConstants {

    /**
     * Constructor privado por ser clase utilitaria
     */
    private NamedQueriesConstants() {
    }
    
    public static final String CONSULTAR_CAJAS_DE_COMPENSACION = "SubsidioMonetario.cosultar.cajasCompensacion";

    /**
     * HU-311-434 Consulta todas las parametrizaciones de liquidación
     */
    public static final String CONSULTAR_PARAMETRIZACIONES_LIQUIDACION = "SubsidioMonetario.buscarTodas.parametrizacion";

    /**
     * HU-311-434 Consulta una parametrización por su identificador
     */
    public static final String CONSULTAR_PARAMETRIZACION_LIQUIDACION_ID = "SubsidioMonetario.buscarPorId.parametrizacion";

    /**
     * HU-311-434 Consulta todas las condiciones de liquidación
     */
    public static final String CONSULTAR_CONDICIONES_LIQUIDACION = "SubsidioMonetario.buscarTodas.condicion";

    /**
     * HU-311-434 Consulta una condición por su identificador
     */
    public static final String CONSULTAR_CONDICION_LIQUIDACION_ID = "SubsidioMonetario.buscarPorId.condicion";

    /**
     * HU BLOQUEO POR BENEFICIARIOS SUBSIDIO ENTIDADES EXTERNAS
     * Constante para la consulta que permite obtener anios parametrizados en condiciones de subsidio
     */
    public static final String CONSULTAR_ANIOS_PARAMETRIZADOS_COND_SUBSIDIOS = "SubsidioMonetario.consultar.anios.condicion.parametrizados.subsidio";
    
    /**
     * HU BLOQUEO POR BENEFICIARIOS SUBSIDIO ENTIDADES EXTERNAS
     * Constante para la consulta que permite obtener anios parametrizados en condiciones de subsidio
     */
    public static final String CONSULTAR_ANIOS_PARAMETRIZADOS_CONC_SUBSIDIOS = "SubsidioMonetario.consultar.anios.concepto.parametrizados.subsidio";

}
