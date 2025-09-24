package com.asopagos.pila.constants;

/**
 * <b>Descripcion:</b> Clase que contiene los valores contantes empleados para la presentación de mensajes de error<br/>
 * <b>Módulo:</b> Asopagos - HU-211-401 y HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ConstantesParaMensajes {

    /* Constante para el mensaje relacionado con la tabla de dbo.PilaIndicePlanilla */
    public static final String INDICE_PLANILLA = "Índices de Planilla Operador Información";
    
    /* Constante para el mensaje relacionado con la tabla staging.RegistroGeneral */
    public static final String RESULTADO_GENERAL = "Resultado general PILA Mundo 2";
    
    /* Constante para el mensaje relacionado con la tabla staging.RegistroDetallado */
    public static final String RESULTADO_DETALLADO = "Resultado detallado PILA Mundo 2";

    /* Constante para el mensaje relacionado con la tabla dbo.PilaArchivoIRegistro1 */
    public static final String REGISTRO_1_I_IR_PILA = "Archivo tipo I-IR, Registro 1";

    /* Constante para el mensaje relacionado con la tabla dbo.PilaArchivoIRegistro2 */
    public static final String REGISTRO_2_I_IR_PILA = "Archivo tipo I-IR, Registro 1";

    /* Constante para el mensaje relacionado con la tabla dbo.DiasFestivos */
    public static final String DIAS_FESTIVOS = "Días Festivos parametrizados en el sistema";

    /* Constante para el mensaje relacionado con la tabla dbo.RolAfiliado */
    public static final String ROL_AFILIADO = "Estado de afiliación del afiliado principal";
    
    /* Constante para mensaje relacionado a consultas de archivos de operador financiero*/
    public static final String ARCHIVOS_OPERADOR_FINANCIERO = "Archivo de operador financiero";
    
    /* Constante para mensaje relacionado con consulta de aportes en BD Core*/
    public static final String APORTES = "Aportes en Base de Datos Transaccional";
    
    /* Constante para descripcion de tipo de validacion bloque 0 */
    public static final String DESC_VALIDACION_B0 = "Duplicidad del archivo";
    
    /* Constante para descripcion de tipo de validacion bloque 1 */
    public static final String DESC_VALIDACION_B1 = "Estructura del archivo";

    /* Constante para descripcion de tipo de validacion bloque 2 */
    public static final String DESC_VALIDACION_B2 = "Tipo del archivo";

    /* Constante para descripcion de tipo de validacion bloque 3 */
    public static final String DESC_VALIDACION_B3 = "Consistencia entre pareja de archivos";

    /* Constante para descripcion de tipo de validacion bloque 4 */
    public static final String DESC_VALIDACION_B4 = "Consistencia individual";

    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B5 = "Tipo y número de documento VS BD";

    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B6 = "Conciliacion O.I vs O.F";

    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B7 = "Procesado vs BD";
    
    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B8 = "Relacion o registro de aportes";
    
    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B9 = "Relacion o registro de novedades";

    /* Constante para descripcion de tipo de validacion bloque 5 */
    public static final String DESC_VALIDACION_B10   = "Notificado";

    /* Constante para mensaje de continuación a siguiente fase */
    public static final String CONTINUAR = "CONTINUAR";

    /* Constante para mensaje de no continuación a siguiente fase por registro sin acción */
    public static final String NO_CONTINUAR_REGISTROS_SIN_ACCION = "NO CONTINUAR - Registros sin acción";

    /* Constante para mensaje de no continuación a siguiente fase por registro sin acción */
    public static final String NO_CONTINUAR_REGISTROS_SIN_ACCION_MIXTA = "NO CONTINUAR - Registros sin acción mixta";

    /* Constante para mensaje de no continuación a siguiente fase por inconsistencias sin gestionar */
    public static final String NO_CONTINUAR_INCONSISTENCIAS_SIN_GESTIONAR = "NO CONTINUAR - Inconsistencias sin gestionar";
    
    /* valor de variable a responder */
    public static final String RESULTADO_VALIDACION = "resultadoValidacion";
    
    /* valor de variable a responder */
    public static final String NUEVO_ESTADO_EVALUACION = "estadoEvaluacion";

    /* Constante para el mensaje relacionado con la tabla dbo.TemAporte en modelo PILA */
    public static final String APORTE_TEMPORAL = "Tabla de aportes simulados";

    /* Constante para el mensaje relacionado con la tabla dbo.AporteDetallado en modelo Core */
    public static final String APORTE_DETALLADO = "Tabla de aportes detallados";
    
    /* Constantes para indicar la actualización de un registro de aporte simulado de corrección*/
    public static final String APORTES_APROBADOS = "Todos los aportes de corrección han sido aprobados";
    public static final String APORTES_PENDIENTES_GESTION = "Se encuentran aportes de corrección pendientes de gestión";
    public static final String APORTES_POR_APROBAR = "Se encuentran aportes de corrección pendientes por aprobar";

    /* Constante para el mensaje relacionado con la tabla NovedadDetalle */
    public static final String NOVEDADES_VIGENTES = "Novedades vigentes del cotizante";

    /* Constante para el mensaje relacionado con la tabla RegistroDetalladoNovedad */
    public static final String RESULTADO_DETALLADO_NOVEDAD = "Novedades de resultado detallado de PILA Mundo 2";

    /* Constante para el mensaje relacionado con la tabla TemNovedad */
    public static final String RESULTADO_NOVEDAD_TEMPORAL = "Registros temporales de Novedad";

    /* Constantes para los mensajes de salida del proceso de finalización de planilla asistida */
    public static final String FINALIZADO_EXITOSO = "El proceso de planilla asistida ha finalizado exitosamente";
    public static final String NO_FINALIZADO_APORTES_NOVEDADES_SIN_PROCESAR = "No finalizado, aportes y/o novedades pedientes por ser registradas por el sistema";
    public static final String NO_FINALIZADO_ERROR_NOTIFICACION = "No finalizado, fallo en el proceso de notificación";

    /* Constante para el mensaje de salida de SP de cotizante con subsidio */
    public static final String COTIZANTE_CON_SUBSIDIO = "Cotizante con Subsidio";

    /* Constante para el mensaje relacionado con la tabla dbo.PilaProceso en modelo PILA */
    public static final String PROCESOS_PILA = "Tabla de procesos PILA";

    /* Constante para el mensaje relacionado con la tabla dbo.HistorialEstadoBloque */
    public static final String HISTORIAL_ESTADOS_VALIDACION = "Historial de Estados de Validación";
}
