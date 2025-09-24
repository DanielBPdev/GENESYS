package com.asopagos.reportes.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes para el nombramiento de
 * NamedStoredProcedures <br/>
 * <b>Módulo:</b> Asopagos - TRA-488 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero D.</a>
 */

public class NamedStoredProcedureConstants {

    /**
     * Constante para el llamado al procedimiento USP_ExecutePILA2
     */
    public static final String CONSULTAR_PARAMETRIZACION_META = "USP_REP_GET_ParametrizacionMeta";

    /**
     * Constante para el llamado al procedimiento USP_ExecutePILA2
     */
    public static final String ACTUALIZAR_PARAMETRIZACION_META = "USP_REP_INSERT_ParametrizacionMeta";
    
    /**
     * Constante para el llamado al procedimiento USP_ExecuteConsultarHistoricoEstadoAportante
     */
    public static final String CONSULTAR_HISTORICO_ESTADO_APORTANTE = "USP_ExecuteConsultarHistoricoEstadoAportante";
    
    /**
     * Constante para el llamado al procedimiento USP_ExecuteConsultarHistoricoAfiliacionPersona
     */
    public static final String CONSULTAR_HISTORICO_AFILIACION_PERSONA = "USP_ExecuteConsultarHistoricoAfiliacionPersona";
}
