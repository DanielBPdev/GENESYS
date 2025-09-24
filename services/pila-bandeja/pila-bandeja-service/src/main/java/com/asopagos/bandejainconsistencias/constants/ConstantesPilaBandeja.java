package com.asopagos.bandejainconsistencias.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes comunes usadas en Pila bandeja <br/>
 * <b>Módulo:</b> Asopagos - HU 430 - 404 - 392 -389 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda</a>
 */

public class ConstantesPilaBandeja {

    /** Constructor sin parametros */
    private ConstantesPilaBandeja() {
    }

    /** Constante que denota el empleador sin pago de aportes */
    public static final String SIN_PAGO_APORTES = "Sin pago de aportes";
    
    /** Constantes para denotar nombres de parametros comunes enviados a los named query */
    public static final String NUMERO_PLANILLA = "numeroPlanilla";
    public static final String ID_PLANILLA = "idPlanilla"; 
    public static final String TIPO_ARCHIVO = "tipoArchivo";
    public static final String BLOQUE = "bloque";
    public static final String ID_INDICE_PLANILLA = "idIndicePlanilla";
    public static final String BLOQUE_VALIDACION = "bloquesValidacion";
    public static final String FECHA_FIN = "fechaFin";
    public static final String FECHA_INICIO = "fechaInicio";
    public static final String NUMERO_IDENTIFICACION = "numeroIdentificacion";
    public static final String TIPO_IDENTIFICACION = "tipoIdentificacion";
    public static final String ESTADO_INCONSISTENCIA = "estadoInconsistencia";
    public static final String TIPO_ARCHIVO_A = "tipoArchivoA";
    public static final String CONSULTA_OPERADOR_OI = "consultaOI";
    public static final String CONSULTA_OPERADOR_OF = "consultaOF";
    public static final String CODIGO_OI = "codOperadorInformacion";
    public static final String PERIODO_APORTE = "periodoPago";
    
    /** Constantes para denotar varios mensajes de error */
    public static final String ERROR_EN_CONSULTA = "Error al realizar la consulta, verifique los datos";
    public static final String NO_HAY_INDICES_ASOCIADOS= "No se encontraron indices asociados";
    
    /** Constantes para definir mensajes de error especificos de un metodo */
    public static final String FIN_APROBAR_SOLICITUD_CAMBIO_IDEN = "Finaliza aprobarSolicitudCambioIden( List<SolicitudCambioNumIdentAportante>, UserDTO )";
    public static final String OPERADOR_INFORMACION = "operadorInformacion";

    public static final String NOMBRE_PLANILLA = "nombreArchivo";
    public static final String NOMBRE_PLANILLA_PAR = "nombreArchivoPar";
}
