package com.asopagos.subsidiomonetario.composite.constants;

/**
 * <b>Descripción: Clase que contiene las constantes utilizadas en el Composite Business</b>
 * <b>Historia de Usuario: HU-436 y 438-</b>
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co> Roy López Cardona</a>
 */
public class SubsidioMonetarioCompositeConstants {

    /** Constructor privado de la clase */
    private SubsidioMonetarioCompositeConstants() {
    }

    /** Variable para indicar la acción sobre la aprobación en primer nivel */
    public static final String APROBADO_PRIMER_NIVEL = "aprobadoPrimerNivel";

    /** Variable para indicar la acción sobre la aprobación en segundo nivel */
    public static final String APROBRADO_SEGUNDO_NIVEL = "aprobadoSegundoNivel";

    /** Varibale que contiene el valor para la aprobación */
    public static final Boolean APROBACION = true;

    /** Variable que contiene el valor para el rechazo */
    public static final Boolean RECHAZO = false;

    /** Constante que representa el identificador de los supervisores en el grupo de usuarios */
    public static final String GRUPO_SUPERVISORES = "SupSubMon";

    /** Constante que indica la variable de supervisor de subsidio */
    public static final String SUPERVISOR_SUBSIDIO = "supervisorSubsidio";

    /** Constante que indica la variable del analista de subsidio */
    public static final String ANALISTA_SUBSIDIO = "analistaSubsidio";

    /** Constante que indica la acción sobre la aprobación en primer nivel para fallecimiento */
    public static final String FALLECIMIENTO_APROBADA_PRIMER_NIVEL = "aprobadaPrimerNivel";

    /** Constante que indica la acción sobre la aprobación en segundo nivel para fallecimiento */
    public static final String FALLECIMIENTO_APROBADA_SEGUNDO_NIVEL = "aprobadaSegundoNivel";

    /** Constante que indica la acción de escalar solicitud de fallecimiento */
    public static final String FALLECIMIENTO_ESCALAR_SOLICITUD = "escalarSolicitud";
   
    /** Constante que indica el estado del aportante */
    public static final String ESPERA_APORTE = "esperaAporte";
    
    /** Constante que indica el timer */
    public static final String TIMER_APO_SUB_FALLECIMIENTO= "timerApoSubFallecimiento";

}
