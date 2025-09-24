package com.asopagos.subsidiomonetario.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las
 * NamedQueries del servicio<br>
 * <b>Módulo:</b> Subsidios HU-311-434 <br/>
 * 
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson A. Arboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy Lopez Cardona</a>
 */
public class NamedQueriesConstants {

    /**
     * Constructor privado por ser clase utilitaria
     */
    private NamedQueriesConstants() {
    }

    /**
     * Consulta la ejecucion programada actual
     */
    public static final String CONSULTA_EJECUCION_LIQUIDACION_PROGRAMADA = "SubsidioMonetarioService.ParametrizacionEjecucionProgramada.ConsultarEjecucionProgramada";

    /**
     * HU-311-434 Consulta todas las parametrizaciones de liquidación
     */
    public static final String CONSULTAR_PARAMETRIZACIONES_LIQUIDACION = "SubsidioMonetario.buscarTodas.parametrizacion";

    /**
     * HU-311-434 Consulta una parametrización por su identificador
     */
    public static final String CONSULTAR_PARAMETRIZACION_LIQUIDACION_ID = "SubsidioMonetario.buscarPorId.parametrizacion";

    /**
     * Consulta de la liquidacion masiva sin derecho
     */
    public static final String CONSULTA_LIQUIDACION_MASIVA_SIN_DERECHO = "SubsidioMonetario.consultar.liquidacion.masiva.sin.derecho";

    /**
     * HU-311-434 Consulta una condición por su identificador
     */
    public static final String CONSULTAR_CONDICION_LIQUIDACION_ID = "SubsidioMonetario.buscarPorId.condicion";

    /**
     * HU-311-436 Y 438 Consulta una solicitud de liquidación por número de radicado
     */
    public static final String CONSULTAR_SOLICITUD_LIQUIDACION_NUMERO_RADICADO = "SubsidioMonetario.buscarPorNumeroRadicado.solicitud";

    /**
     * HU-311-436 Y 438 Consulta una solicitud de liquidación
     */
    public static final String CONSULTAR_SOLICITUD_LIQUIDACION = "SubsidioMonetario.buscarPorNumeroRadicado.solicitudLiquidacion";

    /**
     * HU-311-434 Consultar solicitudes de liquidacion sin cerrar
     */
    public static final String CONSULTAR_SOLICITUDES_LIQUIDACION_SIN_CERRAR = "SubsidioMonetario.buscarSolicitudesLiquidacionSinCerrar.solicitudLiquidacion";
    
    public static final String CONSULTAR_SOLICITUDES_LIQUIDACION_MASIVA_CERRADA = "SubsidioMonetario.buscarSolicitudesLiquidacionMasivaCerrada.solicitudLiquidacion";

    /**
     * HU-311-434 Cambiar el estado de solicitudes de liquidación
     */
    public static final String CERRAR_SOLICITUDES = "SubsidioMonetario.cerrarSolicitudes.solicitudLiquidacion";

    /**
     * HU-311-434 Buscar una solicitud de liquidación dado su identificador
     */
    public static final String BUSCAR_SOLICITUD = "SubsidioMonetario.buscarPorId.solicitud";

    /**
     * HU-311-434 Consultar Periodos
     */
    public static final String CONSULTAR_PERIODOS = "SubsidioMonetario.consultarPeriodos.periodo";

    /**
     * HU 311-434 Consultar periodo regular para una solicitud de liquidación
     */
    public static final String CONSULTAR_PERIODO_SOLICITUD_LIQUIDACION = "SubsidioMonetario.consultarPeriodo.solicitudLiquidacion";

    /**
     * HU 311-434 Consultar el número de solicitudes en la tabla
     */
    public static final String CONTEO_SOLICITUDES_LIQUIDACION = "SubsidioMonetario.consultarTotalSolicitudes.solicitudLiquidacion";

    /**
     * HU 311-434 Consultar la ultima solicitud realizada
     */
    public static final String CONSULTAR_ULTIMA_SOLICITUD = "SubsidioMonetario.consultarUltimaSolicitud.solicitudLiquidacion";

    /**
     * HU 317-144 Consultar liquidaciones diferentes a cerrada
     */
    public static final String CONSULTAR_LIQUIDACION_MASIVA_NO_CERRADA = "SubsidioMonetario.consultarLiquidacionMasivaSinCerrar.solicitudLiquidacion";

    /**
     * HU 317-144 Consultar los beneficiario que posee un afiliado (Trabajador independiente)
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO = "SubsidioMonetario.consultarBeneficiariosAfiliado.beneficiario";

    /**
     * HU 317-144 Consultar los beneficiario que posee un afiliado, sin empresa (Trabajador independiente)
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_DISTINCT = "SubsidioMonetario.consultarBeneficiariosAfiliadoDistinct.beneficiario";

    /**
     * HU 317-144 Consultar los beneficiarios asociados a un empleador
     */
    public static final String CONSULTAR_BENEFICIARIOS_POR_EMPLEADOR = "SubsidioMonetario.consultarBeneficiariosXEmpleador.beneficiario";

    /**
     * HU-311-435 Ejecutar el orquestador de liquidacion masiva
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_MASIVA = "USP_SM_UTIL_Orquestador_LiquidacionMasiva";

    /**
     * Ejecutar el orquestador de staging
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_STAGING = "USP_SM_UTIL_Orquestacion_Staging";

    /**
     * Ejecutar el orquestador de staging Fallecimiento
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_STAGING_FALLECIMIENTO = "USP_SM_UTIL_Orquestacion_Staging_Fallecimiento";

    /**
     * Ejecutar procedimiento eliminacion de liquidacion puntual
     */
    public static final String EJECUTAR_SP_ELIMINAR_LIQUIDACION = "USP_SM_UTIL_EliminarLiquidacion";
    
    

    /**
     * Consultar liquidaciones programadas abiertas
     */
    public static final String CONSULTAR_LIQUIDACIONES_PROGRAMADAS_ABIERTAS = "SubsidioMonetario.consultarSolicitudesProgramadas.solicitudLiquidacion";

    /**
     * Consultar solicitudes de liquidacion a partir del numero de radicado
     */
    public static final String ACTUALIZAR_SOLICITUDES_X_NUMERO_RADICADO = "SubsidioMonetario.actualizarEstadoSolicitudNumRadicado.solicitudLiquidacion";

    /**
     * Constante para la consulta de descuentos sobre la cuota asignada de los beneficiarios
     */
    public static final String CONSULTAR_DESCUENTOS_BENEFICIARIOS_POR_LIQUIDACION = "SubsidioMonetario.consultarDescuentos.liquidacion";
    
    /**
     * Constante para la consulta de descuentos sobre la cuota asignada de los beneficiarios
     */
    public static final String CONSULTAR_DESCUENTOS_BENEFICIARIOS_POR_LIQUIDACION_EMPLEADOR = "SubsidioMonetario.consultarDescuentos.liquidacion.empleador";

    /**
     * Constante para la consulta de resultado de liquidación
     */
    public static final String CONSULTAR_RESULTADO_LIQUIDACION = "USP_SM_GET_ConsultaResultadoLiquidacion";
    /**
     * Constante para la consulta de resultado de liquidación
     */
    public static final String CONSULTAR_VALIDACIONES_LIQUIDACION = "USP_SM_GET_ConsultaValidacionesLiquidacion";
    
    /**
     * Constante para la consulta de resultado de liquidación
     */
    public static final String CONSULTAR_RESULTADO_LIQUIDACION_SP = "SubsidioMonetario.consultarResultado.liquidacion.sp";
    
    /**
     * Constante para la consulta de resultado de liquidación
     */
    public static final String ELIMINAR_MARCA_PROCESO_APROBACION_SEG_NIVEL = "SubsidioMonetario.eliminar.marcaProcesoAprobacionSegNivel";
    
    /**
     * Constante para la consulta de resultado de liquidación
     */
    public static final String CONSULTAR_RESULTADO_LIQUIDACION_EMPLEADOR = "SubsidioMonetario.consultarResultado.liquidacion.empleador";

    /**
     * Constante para la consulta de los derechos de los beneficiarios
     */
    public static final String CONSULTAR_DERECHOS_BENEFICIARIOS_TEMPORAL = "SubsidioMonetario.consultarDerechosBeneficiarios.temporal";

    /**
     * Constante para la consulta de los archivos de liquidación (identificadores en ECM) HU 311-441, HU 311-442, HU 317-266
     */
    public static final String CONSULTAR_ARCHIVOS_LIQUIDACION = "SubsidioMonetario.consultarArchivos.liquidacion";

    /**
     * Constante para la consulta de los archivos de liquidación (identificadores en ECM) HU 311-441, HU 311-442, HU 317-266 por id
     */
    public static final String CONSULTAR_ARCHIVOS_LIQUIDACION_ID = "SubsidioMonetario.consultarArchivos.liquidacion.id";

    /**
     * Permite consultar el valor de una cuota en un periodo dado
     */
    public static final String CONSULTAR_VALOR_CUOTA_PERIODO = "SubsidioMonetario.consultarValorCuotaPeriodo.parametrizacionCondicionesSubsidio";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de trabajadores con mas de X subsidios
     */
    public static final String CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_SUBSIDIOS = "SubsidioMonetario.consultarCantidadTrabajadoresMasXSubsidios";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de trabajadores con más de X monto
     */
    public static final String CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_MONTO = "SubsidioMonetario.consultarCantidadTrabajadoresMasXMonto";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de trabajadores con más de X subsidios de invalidez
     */
    public static final String CONSULTAR_CANTIDAD_TRABAJADORES_MAS_X_SUBSIDIOS_INVALIDEZ = "SubsidioMonetario.consultarCantidadTrabajadoresMasXSubsidiosInvalidez";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de empresas con más de X peridos retroactivos
     */
    public static final String CONSULTAR_CANTIDAD_EMPRESAS_MAS_X_PERIODOS_RRETROACTIVOS = "SubsidioMonetario.consultarCantidadEmpresasMasXPeriodosRetroactivos";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de empresas en el beneficio 1429 con beneficio mayor a cero
     */
    public static final String CONSULTAR_CANTIDAD_EMPRESAS_1429 = "SubsidioMonetario.consutlarCantidadEmpresas1429";

    /**
     * HU-311-436,438,523 Constante para la consulta del monto descontado sobre el valor causado de los beneficiarios
     */
    public static final String CONSULTAR_MONTO_DESCONTADO_PERSONAS = "SubsidioMonetario.consultarMontoDescontadoPersonas";

    /**
     * HU-311-436,438,523 Constante para la consulta de la cantidad de personas con descuento
     */
    public static final String CONSULTAR_CANTIDAD_PERSONAS_DESCUENTO = "SubsidioMonetario.consultarCantidadPersonasConDescuento";

    /**
     * HU-311-436,438,523 Constante para la consulta de los trabajadores con una cantidad de subsidios superior a la parametrizada
     */
    public static final String CONSULTAR_TRABAJADORES_MAS_X_SUBSIDIOS = "SubsidioMonetario.consultarTrabajadoresMasXSubsidios";

    /**
     * HU-311-436,438,523 Constante para la consulta de los trabajadores con un monto liquidado superior al parametrizado
     */
    public static final String CONSULTAR_TRABAJADORES_MAS_X_MONTO = "SubsidioMonetario.consultarTrabajadoresMasXMonto";

    /**
     * HU-311-436,438,523 Constante para la consulta de los trabajadores con una cantidad de subsidios de invalidez superior a la
     * parametrizada
     */
    public static final String CONSULTAR_TRABAJADORES_MAS_X_SUBSIDIOS_INVALIDEZ = "SubsidioMonetario.consultarTrabajadoresMasXSubsidiosInvalidez";

    /**
     * Constante para la consulta de empresas con una cantidad de periodos retroactivos superior a la parametrizada
     */
    public static final String CONSULTAR_EMPRESAS_MAS_X_PERIODOS_RERTROACTIVOS = "SubsidioMonetario.consultarEmpresasMasXPeriodosRetroactivos";

    /**
     * HU-311-436,438,523 Constante para la consulta de empresas en el año 1 y 2 del beneficio 1429 con un subsidio mayor a cero
     */
    public static final String CONSULTAR_EMPRESAS_1429 = "SubsidioMonetario.consultarEmpresas1429";

    /**
     * HU-311-436,438,523 Constante para la consulta de las personas con descuentos
     */
    public static final String CONSULTAR_DESCUENTOS_PERSONAS = "SubsidioMonetario.consultarDescuentosPersonas";

    /**
     * HU-311-436,438 HU317-266 Constante para la consulta de las personas sin derecho en una liquidación y posterior generación del archivo
     * de personas sin derecho
     */
    public static final String CONSULTAR_NUMERO_PERSONAS_SIN_DERECHO = "SubsidioMonetario.consultarPersonasSinDerecho.liquidacion.count";
    
    /**
     * HU-311-436,438 HU317-266 Constante para la consulta de las personas sin derecho en una liquidación y posterior generación del archivo
     * de personas sin derecho
     */
    public static final String CONSULTAR_PERSONAS_SIN_DERECHO = "SubsidioMonetario.consultarPersonasSinDerecho.liquidacion";
    
    /**
     * VISTA 360 Constante para la consulta de las personas sin derecho en una liquidación y posterior generación del archivo
     * de personas sin derecho
     */
    public static final String CONSULTAR_PERSONAS_SIN_DERECHO_EMPLEADOR = "SubsidioMonetario.consultarPersonasSinDerecho.liquidacion.empleador";

    /**
     * HU 436,438 Constante para la consulta del periodo regular asociado a una liquidacion
     */
    public static final String CONSULTAR_PERIODO_LIQUIDACION_POR_RADICACION = "SubsidioMonetario.consultarPeriodoRegular.radicacion";

    /**
     * HU 436,438 Constante para el procedimiento almacenado que permite obtener el resultado de la liquidación
     */
    public static final String PROCEDURE_USP_GET_RESULTADO_LIQUIDACION = "SubsidioMonetario.StoredProcedures.USP_SM_GET_ResultadoLiquidacion";
    
    /**
     * HU 436,438 Constante para el procedimiento almacenado que permite obtener el resultado de la liquidación
     */
    public static final String PROCEDURE_USP_GET_RESULTADO_LIQUIDACION_SIN_DERECHO = "SSubsidioMonetario.StoredProcedures.USP_SM_GET_ResultadoLiquidacionSinDerecho";
    
    public static final String PROCEDURE_USP_LIMPIAR_RESULTADO_LIQUIDACION_SIN_DERECHO = "SSubsidioMonetario.StoredProcedures.USP_SM_LIMPIAR_ResultadoLiquidacionSinDerecho";

    public static final String CONSULTAR_REGISTROS_LIQUIDACION_SIN_DERECHO = "SSubsidioMonetario.Consultar.Registros.Liquidacion.Sin.Derecho";

    public static final String ACTUALIZAR_ECM_ARCHIVO_LIQUIDACION_SUBSIDIO = "SSubsidioMonetario.Actualizar.ECM.Archivo.Liquidacion.Sin.Derecho";
    /**
     * HU 431 Constante para la consulta de la parametrización anual de condiciones para las liquidaciones
     */
    public static final String CONSULTAR_PARAMETRIZACION_POR_RADICACION = "SubsidioMonetario.consultarParametrizacionCondiciones.radicacion";

    /**
     * Constante para consultar liquidacion en proceso
     */
    public static final String CONSULTAR_CONSULTAR_LIQUIDACIONES_EN_PROCESO = "SubsidioMonetario.consultarLiquidacionesEnProceso.solicitudLiquidacionSubsidio";

    /**
     * Constante para consulta de los valores de la cuota anual y el periodo HU-317-143
     */
    public static final String CONSULTAR_VALOR_CUOTA_ANUAL_Y_PERIODO = "SubsidioMonetario.consultarValorCuotaAnualYAgraria.paramatrizacionCondicionesSubsidio";

    /**
     * HU 438 Constante para el procedimiento almacenado que envía la información de la liquidación a pagos
     */
    public static final String PROCEDURE_USP_SM_UTIL_ENVIAR_CONSOLIDADO_A_PAGOS = "SubsidioMonetario.StoredProcedures.USP_SM_UTIL_EnviarConsolidadoAPagos";

    /**
     * Constante para consultar si un periodo existe en BD HU-143
     */
    public static final String CONSULTAR_PERIODO_EXISTE = "SubsidioMonetario.consultarPeriodoExiste.periodo";

    /**
     * Constante para consultar el id de un periodo a partir de la fecha
     */
    public static final String CONSULTAR_ID_PERIODO_FECHA = "SubsidioMonetario.consultarIdPeriodoXFecha.periodo";

    /**
     * Constante para consultar el valor del factor de discapacidad para un periodo dado
     */
    public static final String CONSULTAR_FACTOR_DISCAPACIDAD_PERIODO = "SubsidioMonetario.consultarValorCuotaPeriodo.parametrizacionLiquidacionSubsidio";

    /**
     * Constante para consultar el valor de la cuota agraria en un periodo
     */
    public static final String CONSULTAR_VALOR_CUOTA_AGRARIA_PERIODO = "SubsidioMonetario.consultarValorCuotaAgrariaPeriodo.parametrizacionCondicionesSubsidio";

    /**
     * Constante para llamado al SP de liquidacion especifica
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_ESPECIFICA = "USP_SM_UTIL_Orquestador_LiquidacionEspecificaAjuste";

    /**
     * HU 311-523 Constante para la consulta que permite obtener las liquidaciones masivas que se han dispersado
     */
    public static final String CONSULTAR_HISTORICO_LIQUIDACION_MASIVA = "SubsidioMonetario.consultarHistorico.liquidacionMasiva";

    /**
     * HU 311-441 Constante para la consulta que permite actualizar el estado de derecho para los subsidios asignados en una liquidación
     */
    public static final String ACTUALIZAR_ESTADO_DERECHO_LIQUIDACION = "SubsidioMonetario.actualizar.estadoDerechoLiquidacion";

    /**
     * HU 311-441 Constante para la consulta de paramatrización de condiciones para un periodo definido
     */
    public static final String CONSULTAR_PARAMETRIZACION_CONDICION_INTERVALO = "SubsidioMonetario.cosultar.condicion.intervalo";
    
    /**
     * HU 311-441 Constante para la consulta de paramatrización de la liquidacion para un periodo definido
     */
    public static final String CONSULTAR_PARAMETRIZACION_LIQUIDACION_INTERVALO = "SubsidioMonetario.cosultar.liquidacion.intervalo";

    /**
     * HU 311-523 Constante para la consulta de las liquidaciones específicas que se han dispersado o rechazado
     */
    public static final String CONSULTAR_HISTORICO_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultarHistorico.liquidacionEspecifica";

    /**
     * HU 317-226 Constante para la consulta de las llaves primarias de los registros del conjunto de validaciones
     */
    public static final String CONSULTAR_IDS_CONJUNTO_VALIDACIONES = "SubsidioMonetario.consultarIdsConjuntoValidaciones.conjuntoValidacionSubsidio";

    /**
     * HU 317-226 para llamado al SP de Liquidacion por reconocimiento
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_RECONOCIMIENTO = "USP_SM_UTIL_Orquestador_LiquidacionEspecificaReconocimiento";
    
    /**
     * CC Liquidacion paralela por reconocimiento
     */
    public static final String EJECUTAR_SP_GESTIONAR_COLA_EJECUTION_LIQUIDACION = "USP_SM_UTIL_GestionarColaEjecucionLiquidacion";
    

    /**
     * HU 317-138 Constante para la consulta que permite obtener el total de derechos a asignar en un proceso de liquidacion específica
     */
    public static final String CONSULTAR_TOTAL_DERECHOS_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultar.totalDerechos.liquidacionEspecifica";

    /**
     * HU 311-436,438 Constante para el procedimiento almacenado que se encarga de activar la bandera en cola proceso para las condiciones
     * en una liquidación rechazada
     */
    public static final String PROCEDURE_USP_SM_UTIL_ACTIVAR_EN_COLA_PROCESO = "SubsidioMonetario.StoredProcedures.USP_SM_UTIL_ActivarEnColaProceso";

    /**
     * HU 317-507 Constante para la consulta que permite obtener los registros de subsidiso asignados para una liquidación de fallecimiento
     */
    public static final String CONSULTAR_RESULTADO_LIQUIDACION_FALLECIMIENTO = "SubsidioMonetario.consultar.resultadoLiquidacion.fallecimiento";
    
    /**
     * HU 317-507 Constante para la consulta que permite obtener los registros de subsidiso asignados para una liquidación de fallecimiento
     */
    public static final String CONSULTAR_RESULTADO_LIQUIDACION_FALLECIMIENTO_CONFIRMADOS = "SubsidioMonetario.consultar.resultadoLiquidacion.fallecimiento.confirmados";

    /**
     * HU 317-507 Constante para la consulta que permite actualizar estado de confirmación para un beneficiario dentro de un proceso de
     * liquidación de fallecimiento
     */
    public static final String ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_BENEFICIARIO_CONFIRMADO = "SubsidioMonetario.actualizar.gestionFallecimiento.beneficiarioConfirmado";
    
    /**
     * HU 317-507 Constante para la consulta que permite actualizar estado de confirmación para los beneficiarios dentro de un proceso de
     * liquidación de fallecimiento
     */
    public static final String ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_CONFIRMADO_APORTE_MINIMO = "SubsidioMonetario.actualizar.gestionFallecimiento.confirmar.aporteMinimo";

    /**
     * HU 317-507 Constante para la consulta que permite actualilzar estado de confirmación para los beneficiarios fallecidos asociados a un
     * afiliado en una liquidación de fallecimiento
     */
    public static final String ACTUALIZAR_GESTION_SUBSIDIO_FALLECIMIENTO_AFILIADO_CONFIRMADO = "SubsidioMonetario.actualizar.gestionFallecimiento.afiliadoConfirmado";

    /**
     * HU 317-517 Constante par ala consulta que permite obtener la lista de cuotas proyectadas para un beneficiario en una liquidación por
     * fallecimiento
     */
    public static final String CONSULTAR_DETALLE_BENEFICIARIO_SUBSIDIO_FALLECIMIENTO = "SubsidioMonetario.consultar.detalleBeneficiario.subsidioFallecimiento";

    /**
     * HU 317-503 Constante para llamado SP condiciones fallecimiento trabajador
     */
    public static final String EJECUTAR_SP_CONDICIONES_TRABAJADOR_FALLECIDO = "USP_SM_GET_CondicionesTrabajadorFallecido_trabajador";

    /**
     * HU 317-503 Constante para obtener el detalle del trabajador fallecido
     */
    public static final String CONSULTAR_DETALLE_TRABAJADOR_FALLECIDO = "SubsidioMonetario.consultarDetalleTrabajadorFallecido.persona";
    
    /**
     * HU 317-503 Constante para obtener el detalle del trabajador
     */
    public static final String CONSULTAR_DETALLE_TRABAJADOR = "SubsidioMonetario.consultarDetalleTrabajador.persona";
    
    /**
     * HU 317-503 Constante para obtener el detalle del trabajador fallecido
     */
    public static final String CONSULTAR_PERSONA_TIPO_NUMERO_DOCUMENTO = "SubsidioMonetario.consultarTipoNumeroDoc.persona";

    /**
     * HU-143-144-146-148 Buscar los ids de los afiliados, personas, empresas y beneficiarios para persistirlos en persona liquidacion
     * especifica
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_IDS = "SubsidioMonetario.consultarBeneficiariosAfiliadoIds.beneficiario";

    /**
     * HU-143-144-146-148 Buscar los ids de los afiliados, personas, empresas y beneficiarios para persistirlos en persona liquidacion
     * especifica
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_IDS_EMPLEADOR = "SubsidioMonetario.consultarBeneficiariosAfiliadoIdsXEmpleador.beneficiario";

    /**
     * HU-143-144-146-148 Buscar los id's de todos los beneficiarios del sistema
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_IDS_TODOS = "SubsidioMonetario.consultarBeneficiariosAfiliadoIdsTodos.beneficiario";
    
    /**
     * HU-143-144-146-148 Buscar los id's de todos los beneficiarios del sistema
     */
    public static final String CONSULTAR_EMPLEADORES_POR_PERSONA_TRABAJADOR = "SubsidioMonetario.consultar.empleadores.por.persona.trabajador";

    /**
     * HU-317-503 Consultar si el beneficiario tuvo estado de derecho en la ultima liquidacion
     */
    public static final String CONSULTAR_ESTADO_DERECHO_BENEFICIARIOS = "SubsidioMonetario.consultar.estadoDerechoLiquidacion.beneficiario";

    /**
     * HU-317-503, HU-317-506 Orquestador SP para subsidio por fallecimiento.
     */
    public static final String EJECUTAR_SP_ORQUESTADOR_LIQUIDACION_FALLECIMIENTO = "USP_SM_UTIL_Orquestador_LiquidacionFallecido";

    /**
     * Transversal proceso de dispersión. Constante para la consulta que permite obtener la información de condición persona en un proceso
     * de liquidación
     */
    public static final String CONSULTAR_CONDICIONES_PERSONAS_LIQUIDACION = "SubsidioMonetario.consultar.condicionesPersonas.liquidacion";

    /**
     * Transversal proceso de dispersión. Constante para la consulta que permite obtener la información de la condición de la entidad de
     * descuento en un proceso de liquidación
     */
    public static final String CONSULTAR_CONDICIONES_ENTIDADES_DESCUENTO_LIQUIDACION = "SubsidioMonetario.consultar.condicionesEntidadesDescuento.liquidacion";

    /**
     * HU 317-506 Constante para llamado SP condiciones fallecimiento trabajador
     */
    public static final String EJECUTAR_SP_CONDICIONES_BENEFICIARIO_FALLECIDO = "USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario";

    /**
     * HU-317-507,510 Constante para la consulta que permite obtener el periodo de fallecimiento del afiliado
     */
    public static final String CONSULTAR_PERIODO_FALLECIMIENTO_AFILIADO = "SubsidioMonetario.consultar.periodoFallecimiento.afiliado";

    /**
     * HU-317-507,510 Constante para la consulta que permite obtener el periodo de fallecimiento del beneficiario
     */
    public static final String CONSULTAR_PERIODO_FALLECIMIENTO_BENEFICIARIO = "SubsidioMonetario.consultar.periodoFallecimiento.beneficiario";
    
    /**
     * HU-317-507,510 Constante para la consulta que permite obtener el estado del aporte del afiliado en una liquidación de fallecimeinto
     */
    public static final String CONSULTAR_ESTADO_APORTE_AFILIADO = "SubsidioMonetario.consultar.estadoAporte.afiliado";

    /**
     * HU-317-506 Constante para validar que el beneficiario fallecido, tenga “Estado de afiliación con respecto al afiliado principal”
     * distinto a “Activo”
     */
    public static final String CONSULTAR_ESTADO_AFILIACION_AFI_PPAL_BENE = "SubsidioMonetario.consultar.estadoAfiliacionAfiPpal.beneficiario";

    /**
     * HU-317-506 Constante para validar que el beneficiario al menos al día anterior a la “Fecha de defunción” tenga “Estado de afiliación
     * con respecto al afiliado principal” igual a “Activo”
     */
    public static final String CONSULTAR_ESTADO_AFILIACION_AFILIADO_FECHA_DEFUN_BENE = "SubsidioMonetario.consultar.estadoAfiliacionFechaDefuncion.beneficiario";

    /**
     * Verifica si una persona beneficiaria ha recibido subsidio en el periodo de fallefcimiento
     */
    public static final String CONSULTAR_SUBSIDIO_FALLECIMIENTO_BENEFICIARIO = "SubsidioMonetario.consultar.subsidio.fallecimiento.beneficiario";

    /**
     * Constante para la consulta del caso 5 HU-317-503
     */
    public static final String CONSULTAR_BENE_ACTIVO_AL_MORIR_AFILIADO = "SubsidioMonetario.consultar.beneficiarioActvoAlMorirAfiliado.trabajador";

    /**
     * Consulta un conjunto de validacion dado el valor de la enumeracion
     */
    public static final String CONSULTAR_CONJUNTO_VALIDACION_X_NOMBRE = "SubsidioMonetario.consultarIdConjuntoValidacionXNombre.conjuntoValidacionSubsidio";

    /**
     * HU 311-431 Constante para la consulta que permite obtener la información de las cajas de compensación
     */
    public static final String CONSULTAR_CAJAS_DE_COMPENSACION = "SubsidioMonetario.cosultar.cajasCompensacion";

    /**
     * Consulta del id del afiliado a partir del id de la persona
     */
    public static final String CONSULTAR_ID_AFILIADO_PERSONA = "SubsidioMonetario.consultarIdAfiliado.afiliado";

    /**
     * Consultar las empresas que posee un afiliado
     */
    public static final String CONSULTAR_CONSULTAR_EMPRESAS_AFILIADO = "SubsidioMonetario.consultarEmpresasAfiliado.rolAfiliado";
    
    /**
     * Consultar las empresas que posee un RolAfiliado
     */
    public static final String CONSULTAR_CONSULTAR_EMPRESAS_ROA = "SubsidioMonetario.consultarEmpresasAfiliado.roa";
    
    /**
     * Consultar las empresas que posee un afiliado
     */
    public static final String CONSULTAR_CONSULTAR_EMPRESAS_BEN = "SubsidioMonetario.consultarEmpresasAfiliado.ben";
    
    

    /**
     * Consultar el nivel de una liquidacion especifica
     */
    public static final String CONSULTAR_NIVEL_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultarNivelLiquidacionEspecifica.personaLiquidacionEspecifica";

    /**
     * Consultar las personas que hacen parte de una liquidacion especifica
     */
    public static final String CONSULTAR_PERSONAS_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultarPersonasLiquidacionEspecifica.personas";

    /**
     * Consultar empresas que hacen parte de una liqudiacion especifica
     */
    public static final String CONSULTAR_EMPRESAS_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultarEmpleadoresLiquidacionEspecifica.empleadores";

    /**
     * Consultar periodos que hacen parte de una liqudiacion especifica
     */
    public static final String CONSULTAR_PERIODOS_LIQUIDACION_ESPECIFICA = "SubsidioMonetario.consultarPeriodosLiquidacionEspecifica.periodoLiquidacion";

    /**
     * Consultar las condiciones asociadas
     */
    public static final String CONSULTAR_CONDICIONES_LIQUIDACION_RECONOCIMIENTO = "SubsidioMonetario.consultarCondicionesLiquidacionReconocimiento.aplicacionValidacionSubsidio";

    /**
     * HU 317-509 Constante para la consulta que permite obtener la información histórica de las liquidaciones de fallecimiento que cumplan
     * con los filtros establecidos
     */
    public static final String CONSULTAR_HISTORICO_LIQUIDACION_ESPECIFICA_FALLECIMIENTO = "SubsidioMonetario.consultar.historico.liquidacionesFallecimiento";

    /**
     * HU 317-509 Constante para la consulta que permite obtener la información de los beneficiarios un proceso de liquidación específica
     * por defunción de beneficiario
     */
    public static final String CONSULTAR_BENEFICIARIOS_LIQUIDACION_FALLECIMIENTOS_POR_RADICADOS = "SubsidioMonetario.consultar.beneficiarios.liquidacionFallecimiento";

    /**
     * Condiciones de subsidio por ajuste 
     */
    public static final String CONSULTAR_CONDICIONES_ESPECIALES_LIQ_AJUSTE = "SubsidioMonetario.consultarCondicionesEspecialesLiqAjuste.parametrizacionSubsidioAjuste";

    public static final String CONSULTAR_ID_PARAMETRIZACION_CONDICION = "SubsidioMonetario.consultarIdParametrizacionCondicion";

    /**
     * Consultar trabajador dependiente fallecido o beneficiarios de un trabajador fallecidos
     */
    public static final String CONSULTAR_TRABAJADOR_BENEFICIARIOS_FALLECIDOS_POR_SOLICITUD_LIQUIDACION = "SubsidioMonetario.consultarTrabajadorBeneficiariosFallecidos.por.solicitudLiquidacionSubsidio";
    
    /**
     * Consultar los estados del empleador para una liquidacion por reconocimiento
     */
    public static final String CONSULTAR_ESTADOS_EMPLEADOR_LIQ_RECONOCIMIENTO = "SubsidioMonetario.consultarEstadosEmpleador.estadoCondicionValidacionSubsidio";

    /**
     * HU-317-513,514,515,516
     * Constante para la consulta que permite obtener la validación fallida referente a una persona en caso de que exista
     */
    public static final String CONSULTAR_VALIDACION_FALLIDA_PERSONA_FALLECIMIENTO = "SubsidioMonetario.consultar.validacionFallida.personaFallecimiento";
    
    /**
     * Constante para la consulta que permite obtener la validación fallida 
     */
    public static final String CONSULTAR_VALIDACION_FALLIDA_PERSONA = "SubsidioMonetario.consultar.validacionFallida.persona";
    
    /**
     * Constante para la consulta que permite obtener la Condición persona asociada al trabajador por Numero de radicación.
     */
    public static final String CONSULTAR_IDCONDICIONPERSONA_RADICACION = "SubsidioMonetario.consultar.condicionPersonaPorRadicacion";

    /**
     * HU-317-513,514,515,516
     * Constante para la consulta que permite obtener el identificador de core para la persona relacionada en la liquidación de
     * fallecimiento
     */
    public static final String CONSULTAR_IDENTIFICADOR_PERSONA_CORE = "SubsidioMonetario.consultar.identificadorPersona.core";

    /**
     * HU-317-513,515
     * Constante para la consulta que permite obtener el identificador de PersonaLiquidacionEspecifica relacionado a un trabajador en una
     * liquidacion de fallecimeinto
     */
    public static final String SELECCIONAR_PERSONA_LIQUIDACION_ESPECIFICA_TRABAJADOR_LIQUIDACION_FALLECIMIENTO = "SubsidioMonetario.seleccionar.personaLiquidacionEspecifica.trabajador.liquidacionFallecimiento";

    /**
     * HU-317-513,515
     * Constante para la consulta que permite obtener el identificador de PersonaLiquidacionEspecifica relacionado a un trabajador en una
     * liquidacion de fallecimeinto
     */
    public static final String SELECCIONAR_PERSONA_LIQUIDACION_ESPECIFICA_BENEFICIARIO_LIQUIDACION_FALLECIMIENTO = "SubsidioMonetario.seleccionar.personaLiquidacionEspecifica.beneficiario.liquidacionFallecimiento";

    /**
     * HU 317-513,514,515,516
     * Constante para la consulta que permite obtener el conjunto de validaciones para el tipo de proceso parametrizado
     */
    public static final String CONSULTAR_VALIDACIONES_TIPO_PROCESO = "SubsidioMonetario.consultar.validaciones.tipoProceso";

    /**
     * HU 317-513,514,515,516
     * Constante para la consulta que permite obtener el identificador del conjunto validación parametrizado
     */
    public static final String CONSULTAR_IDENTIFICADOR_CONJUNTO_VALIDACION = "SubsidioMonetario.consultar.identificador.conjuntoValidacion";

    /**
     * HU 311-434
     * Constante para la consulta que permite obtener la fecha de corte de aportes para la última liquidación masiva ejecutada
     */
    public static final String CONSULTAR_FECHA_ULTIMO_CORTE_APORTES_LIQUIDACION_MASIVA = "SubsidioMonetario.consultarFecha.ultimoCorteAportes.liquidacionMasiva";

    /**
     * HU 311-434
     * Constante para la consulta que permite obtener la fecha de corte de aportes para la última liquidación masiva ejecutada native query
     */
    public static final String CONSULTAR_FECHA_ULTIMO_CORTE_APORTES_LIQUIDACION_MASIVA2 = "SubsidioMonetario.consultarFecha.ultimoCorteAportes.liquidacionMasiva2";

    
    /**
     * HU 311-434
     * Constante para la consulta que permite obtener la última liquidación masiva ejecutada
     */
    public static final String CONSULTAR_ULTIMA_LIQUIDACION_MASIVA = "SubsidioMonetario.consultarSolicitudLiquidacion.ultima.liquidacionMasiva";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la información del comunicado 63
     */
    public static final String CONSULTAR_VALORES_COMUNICADO_63 = "SubsidioMonetario.consultar.comunicado63.masivo.dispersion.pagos.empleador";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener los destinatarios del comunicado 63
     */
    public static final String CONSULTAR_DESTINATARIOS_COMUNICADO_63 = "SubsidioMonetario.consultarEmails.comunicado63.masivo.dispersion.pagos.empleador";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la información del comunicado 64
     */
    public static final String CONSULTAR_VALORES_COMUNICADO_64 = "SubsidioMonetario.consultar.comunicado64.masivo.dispersion.pagos.trabajador";
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener los destinatarios del comunicado 64
     */
    public static final String CONSULTAR_DESTINATARIOS_COMUNICADO_64 = "SubsidioMonetario.consultarEmails.comunicado64.masivo.dispersion.pagos.trabajador";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la información del comunicado 65
     */
    public static final String CONSULTAR_VALORES_COMUNICADO_65 = "SubsidioMonetario.consultar.comunicado65.masivo.dispersion.pagos.admin";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener los destinatarios del comunicado 65
     */
    public static final String CONSULTAR_DESTINATARIOS_COMUNICADO_65 = "SubsidioMonetario.consultarEmails.comunicado65.masivo.dispersion.pagos.admin";

    /**
     * HU 311-434
     * Constante para la consulta que permite obtener el porcentaje de avance sobre el proceso de liquidación
     */
    public static final String CONSULTAR_PORCENTAJE_AVANCE_LIQUIDACION = "SubsidioMonetario.consultar.porcentajeAvance.liquidacion";
    
    /**
     * HU 311-434
     * Constante para la consulta que permite obtener el porcentaje de avance sobre el proceso de liquidación
     */
    public static final String CONSULTAR_AVANCE_PROCESO_LIQUIDACION = "SubsidioMonetario.consultar.avance.proceso.liquidacion";

    /**
     * HU 31-434
     * Constante para la consulta que permite registrar la cancelación sobre un proceso de liquidación
     */
    public static final String REGISTRAR_CANCELACION_PROCESO_LIQUIDACION = "SubsidioMonetario.registrar.cancelacion.procesoLiquidacion";
    
    /**
     * HU 31-434
     * Constante para la consulta que permite determinar la cancelación sobre un proceso de liquidación
     */
    public static final String CONSULTAR_CANCELACION_PROCESO_LIQUIDACION = "SubsidioMonetario.consultar.cancelacion.procesoLiquidacion";

    /**
     * HU 317-506: CASO 6
     * Constante para la consulta que permite obtener el resultado de la validación del rango de dias del caso 6
     */
    public static final String CONSULTAR_RANGO_DIAS_BENEFICIARIO_HU_506_CASO6 = "SubsidioMonetario.consultar.rangoDias.beneficiario.hu506.caso6";

    /**
     * HU 317-506: CASO 3
     * Constante para la consulta que permite saber si el trabajador estuvo al menos un día activo en el periodo en el cual fallece el o los beneficiarios
     */
    public static final String CONSULTAR_TRABAJADOR_ACTIVO_AL_FALLECER_BENEFICIARIO_HU_317506_CASO3 = "SubsidioMonetario.consultar.trabadorActivo.alFallecerBeneficiario.hu506.caso3";
    
    /**
     * HU 317-506: CASO 9
     * Constante para la consulta que permite saber si el beneficiario por el grupo familiar al que se le evalúa tiene registrado un 
     * administrador de subsidio y un medio de pago.
     */
    public static final String CONSULTAR_ADMON_SUBSIDIO_POR_BENEFICIARIO_HU_317506_CASO9 = "SubsidioMonetario.consultar.admonSubsidio.porBeneficiario.hu506.caso9";

    /**
     * HU 31-434
     * Constante para la consulta que permite registrar inicio del avance sobre un proceso de liquidación
     */
    public static final String REGISTRAR_AVANCE_PROCESO_LIQUIDACION = "SubsidioMonetario.registrar.avance.procesoLiquidacion";

    /**
     * HU 317-508
     * Constante para la consulta que permite obtener la información del comunicado 74 y 77
     */
    public static final String CONSULTAR_VALORES_COMUNICADO_74_77 = "SubsidioMonetario.consultar.comunicado_74_77.fallecimiento.dispersion.pagos.trabajador";
    /**
     * Consulta que permite insertar el bloqueo para un beneficiario con relacion al afiliado
     */
    public static final String INSERTAR_BLOQUEO_AFILIADO_RELACION_BENEFICIARIO = "SubsidioMonetario.insertar.bloqueo.afiliado.relacion.beneficiario";
    /**
     * HU 317-508
     * Constante para la consulta que permite obtener la información del comunicado 74 y 77
     */
    public static final String CONSULTAR_DESTINATARIOS_COMUNICADO_74_77 = "SubsidioMonetario.consultarEmails.comunicado_74_77.masivo.dispersion.pagos.trabajadorConyuge";
    
    /**
     * HU 317-508
     * Constante para la consulta que permite obtener la información del comunicado 75 y 78
     */
    public static final String CONSULTAR_VALORES_COMUNICADO_74_75_77_78 = "SubsidioMonetario.consultar.comunicado_74_75_77_78.fallecimiento.dispersion.pagos";
    
    /**
     * 508
     * Consulta que permite saber si la fecha actual concuerda con alguna de las fechas programados de los detalles programados para permitir el pago a los detalles asignados
     */
    public static final String CONSULTAR_DIA_PERIODO_PARA_DISPERSION_DETALLES_PROGRAMADOS = "SubsidioMonetario.consultar.diaParametrizado.detallesProgramados.porPeriodo";
    
    /**
     * Constante que permite ejecutar el SP que registra los detalles de subsidios programados a la tabla detalles de subsidios asignados
     */
    public static final String PROCEDURE_USP_REGISTRAR_DETALLESPROGRAMADOS_A_DETALLESASIGNADOS = "USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados";

    /**
     * HU-311-434 Consultar solicitudes de liquidacion sin cerrar
     */
    public static final String CONSULTAR_SOLICITUDES_LIQUIDACION_FALLECIMIENTO_SIN_CERRAR = "SubsidioMonetario.buscarSolicitudesLiquidacionFallecimientoSinCerrar.solicitudLiquidacion";
    
    /**
     * Vista 360 empleador Consulta las liquidaciones en las cuales aparece la empresa relacionada 
     */
    public static final String CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_EMPLEADOR = "SubsidioMonetario.vista360.empleador.liquidacion";

    /**
     * Consulta los beneficiarios de un afiliado, sin empleador, esta consulta arroja los resultados
     * que se muestran en pantalla para seleccion. Los beneficiarios son los asociados a las condiciones realizadas en la liquidacion
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_ASOCIADOS_CONDICION_LIQUIDACION_SUBSIDIO = "SubsidioMonetario.consultarBeneficiarios.asociadoAfiliado.personaLiquidacionEspecifica";
    
    /**
     * Consulta que obtiene el ultimo registro de la tabla aplicacion validacion subsidio relacionado a una solicitud de liquidacion de subsidio.
     */
    public static final String CONSULTAR_ULTIMA_APLICACION_VALIDACION_SUBSIDIO_POR_ID_SUBSIDIO = "SubsidioMonetario.consultar.ultimo.registroAplicacionValidacionSubsidio.por.idSolicitudLiqSubsidio";

    /**
     * Consulta que se encarga de eliminar el registro de la tabla aplicacion validacion subsidio persona asociado al id aplicacion subsidio
     */
    public static final String ELIMINAR_REGISTRO_APLICACION_VALIDACION_SUBSIDIO_PERSONA_POR_ID_APLICACION_VALIDACION = "SubsidioMonetario.eliminar.registroAplicacionValidacionSubsidioPersona.por.idAplicacionValidacionAsociado";

    /**
     * Consulta que se encarga de eliminar el registro de la tabla AplicacionValidacionSubsidio por medio del id
     */
    public static final String ELIMINAR_REGISTRO_APLICACION_VALIDACION_SUBSIDIO_POR_ID = "SubsidioMonetario.eliminar.registro.aplicacionValidacionSubsidio.por.id";

    /**
     * Vista 360 trabajador Consulta las liquidaciones en las cuales aparece el trabajador relacionado
     */
    public static final String CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_TRABAJADOR = "SubsidioMonetario.vista360.trabajador.liquidacion";

    /**
     * Vista 360 trabajador Consulta las liquidaciones en las cuales aparece el trabajador relacionado
     */
    public static final String CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_TRABAJADOR_2 = "SubsidioMonetario.vista360.trabajador.liquidacion.2";
    
    /**
     * Vista 360 trabajador Consulta las validaciones de una liquidación específica para el empleador según el trabajador relacionado 
     */
    public static final String CONSULTAR_VISTA_360_VALIDACIONES_LIQUIDACION_EMPLEADOR = "SubsidioMonetario.vista360.trabajador.detalleValidacionesEmpleador";
    
    /**
     * Vista 360 trabajador Consulta las validaciones de la empresa relacionada de una liquidación específica para el trabajador relacionado
     */
    //public static final String CONSULTAR_VISTA_360_VALIDACIONES_EMPLEADOR_LIQUIDACION_TRABAJADOR = "SubsidioMonetario.vista360.trabajador.detalleValidacionesEmpleador";
    
    /**
     * Vista 360 trabajador Consulta grupos familiares relacionada de una liquidación específica para el trabajador relacionado
     */
    public static final String CONSULTAR_VISTA_360_GRUPO_FAMILIAR_LIQUIDACION_TRABAJADOR = "SubsidioMonetario.vista360.trabajador.detalleGruposFamiliaresTrabajador";

    /**
     * Consulta encargada de obtener la información de la cuota monetaria por afiliado o beneficiario
     */
    public static final String CONSULTAR_CUOTA_MONETARIA_POR_AFILIADO_BENEFICIARIO = "SubsidioMonetario.obtener.cuota.monetaria";
    
    /**
     * Consulta el nombre y el identificador de los sitios de pago 
     */
    public static final String CONSULTAR_SITIOS_PAGO = "SubsidioMonetario.consultar.nombre.sitios.pago";

    /**
     * Consulta encargada de obtener la información del subsidio por afiliado o beneficiario
     */
    public static final String CONSULTAR_INFO_SUBSIDIO_POR_AFILIADO_BENEFICIARIO = "SubsidioMonetario.obtener.info.subsidio.por.afiliado.beneficiario";
    
    /**
     * Vista 360 empleador Consulta las liquidaciones en las cuales aparece la empresa relacionada 
     */
    public static final String CONSULTAR_VISTA_360_SOLICITUDES_LIQUIDACION_EMPLEADOR_EXPORTAR = "SubsidioMonetario.vista360.empleador.liquidacion.exportar";

    /**
     * Consulta que obtiene el periodo de fallecimiento del beneficiario en el caso de que él afiliado no haya cumplido las validaciones
     */
    public static final String CONSULTAR_PERIODO_FALLECIMIENTO_BENEFICIARIO_AFILIADO_NO_CUMPLE_VALIDACIONES = "SubsidioMonetario.consultar.periodoFallecimiento.beneficiario.afiliado.no.cumple";

    /**
     * Consulta que obtiene el periodo de fallecimiento del beneficiario en el caso de que él afiliado no haya cumplido las validaciones
     */
    public static final String CONSULTAR_VISTA_360_PAGOS_POR_CUOTAS_NOMETARIA = "SubsidioMonetario.vista360.trabajador.detallePagosCuotaMonetaria";
    
    /**
     * Consulta que obtiene la información de la vista 360 para la liquidación de fallecimiento  
     */
    public static final String CONSULTAR_VISTA_360_LIQUIDACION_FALLECIMIENTO_SUBSIDIO = "SubsidioMonetario.vista360.trabajador.liquidacionFallecimiento.subsidio";
    
    /**
     * Consulta que obtiene la información de la vista 360 para la liquidación de fallecimiento  
     */
    public static final String CONSULTAR_VISTA_360_LIQUIDACION_FALLECIMIENTO_CORE = "SubsidioMonetario.vista360.trabajador.liquidacionFallecimiento.core";

    /**
     * Consulta que obtiene la información del administrador del subsidio y sus grupos familiares (servicio de integración)
     */
    public static final String CONSULTAR_INFO_ADMINSUBSIDIO = "SubsidioMonetario.consultar.info.adminSubsidio";
    /**
     * Consulta que obtiene el periodo de fallecimiento del beneficiario en el caso de que él afiliado no haya cumplido las validaciones
     */
    public static final String CONSULTAR_FECHA_ULTIMA_ACTUALIZACION_STAGIN = "SubsidioMonetario.consultar.rRevisionAuditoriaSubsidio.fechaRevision";
    
    /**
     * Consulta que obtiene el valor de los pagos de una liquidacion para un trabajador especifico
     */
    public static final String CONSULTAR_VISTA_360_PAGOS_POR_TRABAJADOR_LIQUIDACION = "SubsidioMonetario.vista360.trabajador.liquidacionPagosCuotaMonetaria";
    
    /**
     * Consulta que obtiene el valor de los pagos de una liquidacion para un trabajador especifico
     */
    public static final String CONSULTAR_VISTA_360_PAGOS_POR_TRABAJADOR_LIQUIDACION_PERIODO = "SubsidioMonetario.vista360.trabajador.liquidacionPagosCuotaMonetariaYperiodo";
    
    /**
     * Consulta que obtiene el valor de los pagos de una liquidacion para un trabajador especifico
     */
    public static final String CONSULTAR_MAXIMO_PERIODO_REGULAR_LIQUIDACION = "SubsidioMonetario.maximoPeriodoRegular.liquidacion";

    /**
     * Consulta que devuelve un beneficiario si no pertenece a la categoria PADRES
     */
    public static final String CONSULTAR_CATEGORIA_PADRES_BENEFICIARIO = "SubsidioMonetario.consultar.beneficiario.categoria.PADRES";

    public static final String CONSULTA_RETIRO_LIQUIDACION_TRABAJADOR = "SubsidioMonetario.vista360.trabajador.retiroLiquidacionTrabajador";


    /**
     * Consulta que devuelve la información relevante para crear el comunicado 137 (138) por fallecimiento
     */
    public static final String CONSULTAR_INFORMACION_COMUNICADO_137 = "SubsidioMonetario.consultar.informacion.comunicado.fallecimiento.137";

    /**
     * Consulta que devuelve la información de los destinatarios de los comunicados de la 137 y 138
     */
    public static final String CONSULTAR_EMAILS_PERSONAS_FALLECIMIENTO_COM_137_138 = "SubsidioMonetario.consultar.correos.personas.comunicados.137.138";

    /**
     * Consulta que devuelve la información relevante para crear el comunicado 138 (139) por fallecimiento
     */
    public static final String CONSULTAR_INFORMACION_COMUNICADO_138 = "SubsidioMonetario.consultar.informacion.comunicado.fallecimiento.138";

    /**
     * Consulta que devuelve la información de las liquidaciones de un empleador en la base de datos de subsidio
     */
    public static final String CONSULTAR_INFORMACION_EMPLEADOR_VISTA360_EXPORTAR_SUBSIDIO = "SubsidioMonetario.vista360.empleador.liquidacion.exportar.subsidio";

    /**
     * Constante para el procedimiento almacenado que genera los datos de dispersión de subsidios y los
     * almacena en cada tabla de dispersión
     */
    public static final String PROCEDURE_USP_SM_UTIL_CONSOLIDARSUBSIDIOS = "SubsidioMonetario.StoredProcedures.USP_SM_UTIL_ConsolidarSubsidiosFallecimiento";

    /**
     * Constante que obtiene las validaciones asociadas a una id de condicion persona y un número de radicado
     */
    public static final String CONSULTAR_VALIDACIONES_SUBSIDIO_POR_RADICADO_IDCONDICIONPERSONA = "SubsidioMonetario.consultar.validaciones.por.numeroRadicado.idCondicionPersona";

    /**
     * Constante que se encarga de eliminar las validaciones que se han gestionado
     */
    public static final String ELIMINAR_REGISTROS_VALIDACIONES_SUBSIDIO= "SubsidioMonetario.eliminar.validaciones.idCondicionPersona.numeroRadicados";

    /**
     * Constante que se encarga de actualizar el resultaado de la validacion del resultado validacion liquidacion con la primera validación
     */
    public static final String UPDATE_REGISTRO_RESULTADOVALIDACIONLIQ_PRIMERA_VALIDACION = "SubsidioMonetario.actualizar.registro.primeraValidacion";

    /**
     * Constante que se encarga de obtener el id de las validaciones de un numero de radicacion (con excepción de la primera)
     */
    public static final String CONSULTAR_VALIDACIONES_SUBSIDIO_TRABAJADOR_CUMPLE = "SubsidioMonetario.consultar.validacionesRegistro.trabajador.cumple";

    /**
     * Constante que se encarga de eliminar los registros (con excepción del primero)
     */
    public static final String ELIMINAR_REGISTRO_VALIDACION_TRABAJADOR_CUMPLE = "SubsidioMonetario.eliminar.registros.trabajador.cumple";
    
    /**
     * Constante que consulta los registros por numero radicado del trabajador que cumple y se desea devolver el proceso de gestion
     */
    public static final String CONSULTAR_REGISTROS_TRABAJADOR_CUMPLE = "SubsidioMonetario.consultar.registros.trabajador.cumple";

    /**
     * Constante que permite eliminar los registros relacionados a la tabla ResultadoValidacionLiquidacion por sus ids
     */
    public static final String ELIMINAR_REGISTRO_VALIDACION_GESTION_SUBSIDIO_FALLECIMIENTO = "SubsidioMonetario.eliminar.registroValidacion.gestionSubsidioFallecimiento";
    
    /**
     * Constante consulta que obtiene el registro del resultado validacion liquidacion del beneficiario
     */
    public static final String CONSULTAR_ID_RESULTADO_VALIDACION_LIQUIDACION_ID_CONDICION_BEN = "SubsidioMonetario.consultar.idResultadoValidacionLiquidacion.por.condicionPer.beneficiario";

    public static final String UPDATE_REGISTRO_RESULTADO_VALIDACION_LIQ_BENEFICIARIO_CUMPLE= "SubsidioMonetario.editar.beneficiarioCumple.resultadoValidacionLiq";
    
    /**
     * Se eliminan los registros relacionados a la proyeccion de cuotas ya que se elimina todo desde el trabajador
     */
    public static final String ELIMINAR_REGISTROS_VALIDACIONES_RELACIONADOS_PROYECCION_CUOTA_FALLECIMIENTO = "SubsidioMonetario.eliminar.registrosValidaciones.proyeccionCuotaFallecimiento";

    public static final String BUSCAR_CONDICIONVALIDACION_BENEFICIARIO_CUMPLE = "SubsidioMonetario.buscar.condicionValidacion.beneficiarioCumple";

    /**
     * Consulta que obtiene los periodos y el monto de lo liquidado para las cuotas programadas/dispersados
     */
    public static final String OBTENER_PERIODOS_PROGRAMADOS_DISPERSADOS = "SubsidioMonetario.obtener.periodosMontos.programadosDispersados";
    
    /**
     * Consulta que obtiene los periodos y el monto de lo liquidado para las cuotas programadas
     */
    //public static final String OBTENER_PERIODOS_PROGRAMADOS = "SubsidioMonetario.obtener.periodosMontos.programados";

    /**
     * Consulta que obtiene los periodos y el monto de lo liquidado para las cuotas dispersadas 
     */
    //public static final String OBTENER_PERIODOS_DISPERSADOS = "SubsidioMonetario.obtener.periodosMontos.dispersados";

    /**
     * Consulta que obtiene la información relevante de la ubicación sea del trabajdor o del administrador de la liq de fallecimiento dispersada
     */
    public static final String CONSULTAR_INFO_UBICACION_TRABAJADOR_ADMIN_DISPERSION_FALLECIMIENTO = "SubsidioMonetario.consultar.infoUbicacion.trabajador.admin";

    /**
     * Consulta que obtiene los estados de los nyumeros de radicados sea masiva o especifica para la vista 360 trabajador
     */
    public static final String CONSULTAR_ESTADO_LIQUIDACIONES_TRABAJADOR_VISTA360 = "SubsidioMonetario.consultar.registros.trabajador.vista360";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la cantidad de registros del comunicado 63
     */
    public static final String CONSULTAR_NUMERO_VALORES_COMUNICADO_63 = "SubsidioMonetario.consultar.numero.valores.comunicado63.masivo.dispersion.pagos.empleador";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la cantidad de registros del comunicado 64
     */
    public static final String CONSULTAR_NUMERO_VALORES_COMUNICADO_64 = "SubsidioMonetario.consultar.numero.valores.comunicado64.masivo.dispersion.pagos.trabajador";
    
    /**
     * HU 311-438
     * Constante para la consulta que permite obtener la cantidad de registris del comunicado 65
     */
    public static final String CONSULTAR_NUMERO_VALORES_COMUNICADO_65 = "SubsidioMonetario.consultar.numero.valores.comunicado65.masivo.dispersion.pagos.admin";
    
    /**
     * HU 317-508
     * Constante para la consulta que permite obtener la información del comunicado 74 y 77
     */
    public static final String CONSULTAR_NUMERO_VALORES_COMUNICADO_74_77 = "SubsidioMonetario.consultar.numero.valores.comunicado_74_77.fallecimiento.dispersion.pagos.trabajador";
    
    
    /**
     * HU 317-508
     * Constante para la consulta que permite obtener la información del comunicado 74 y 77
     */
    public static final String HISTORICOS_CONSULTAR_ESTADOS_SOLICITUD_LIQUIDACION_SUBSIDIO = "SubsidioMonetario.consultar.historico.estados.solicitud.liquidacion";
    
    /**
     * HU-311-434 Consulta todas las condiciones de liquidación
     */
    public static final String CONSULTAR_CONDICIONES_LIQUIDACION = "SubsidioMonetario.buscarTodas.condicion";
    
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
    
    /**
     * HU INGE-035-311-431
     * Constante para la consulta que permite obtener cuenta de confa
     */
    public static final String CONSULTAR_CUENTA_CCF = "SubsidioMonetario.consultar.cuenta.ccf";
    
    /**
     * HU INGE-035-311-431
     * Constante para la consulta que permite obtener cuenta de confa
     */
    public static final String ELIMINAR_CUENTAS_CCF = "SubsidioMonetario.eliminar.cuenta.ccf";

    /**
     * HU 317-503 Constante para llamado SP gestionar secuencias
     */
    public static final String EJECUTAR_SP_GESTOR_VALOR_SECUENCIA = "USP_GET_GestorValorSecuencia";
    
    /**
     * HU 317-503 Constante para llamado SP gestionar secuencias
     */
    public static final String SECUENCIA_BLOQUEO_BENEFICIARIO_CUOTAMONETARIA = "SEC_BloqueoBeneficiarioCuotaMonetaria";

    /**
     * 
     */
    public static final String VALIDAR_EXISTENCIA_BENEFICIARIOS_BLOQUEO = "SubsidioMonetario.validar.existencia.beneficiarios.bloqueo";
    
    /**
     * 
     */
    public static final String VALIDAR_EXISTENCIA_BENEFICIARIOS_BLOQUEO_AUD = "SubsidioMonetario.validar.existencia.beneficiarios.bloqueo.aud";
    
    /**
     *
     */
    public static final String ELIMINAR_BENEFICIARIO_NO_EXISTENTES_BLOQUEO = "SubsidioMonetario.eliminar.beneficiarios.bloqueo.no.existentes.bloqueo";
    
    /**
    *
    */
   public static final String USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria = "USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria";
   
   /**
   *
   */
   public static final String USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria = "USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria";
    
    /**
     * 
     */
    public static final String ELIMINAR_BENEFICIARIO_NO_EXISTENTES_BLOQUEO_AUD = "SubsidioMonetario.eliminar.beneficiarios.bloqueo.no.existentes.bloqueo.aud";
    
    /**
     * 
     */
    public static final String RADICAR_BLOQUEO_CM = "SubsidioMonetario.radicar.bloqueoCM.beneficiarios";
    
    /**
     * 
     */
    public static final String RADICAR_BLOQUEO_CM_AUD = "SubsidioMonetario.radicar.bloqueoCM.beneficiarios.aud";
    
    /**
     * HU 317-503 Constante para llamado SP gestionar secuencias
     */
    public static final String ELIMINAR_BLOQUEO_BENEFICIARIO_CUOTACM_POR_ID_CARGUE = "SubsidioMonetario.eliminar.bloqueoBeneficiarioCuotaMonetaria.por.id.cargue";
    
    /**
     * HU 317-503 Constante para llamado SP gestionar secuencias
     */
    public static final String ELIMINAR_BLOQUEO_BENEFICIARIO_CUOTACM_POR_ID_CARGUE_AUD = "SubsidioMonetario.eliminar.bloqueoBeneficiarioCuotaMonetaria.por.id.cargue.aud";
    
    /**
     * 
     */
    public static final String ELIMINAR_CARGUE_BLOQUEO_DM_POR_ID = "SubsidioMonetario.eliminar.cargueBloqueoCuotaMonetaria.por.id";  
    
    
    /**
     * 
     */
    public static final String ELIMINAR_CARGUE_BLOQUEO_DM_POR_ID_AUD = "SubsidioMonetario.eliminar.cargueBloqueoCuotaMonetaria.por.id.aud"; 
    
    /**
     * HU 317-503 Constante para llamado SP gestionar secuencias
     */
    public static final String CONSULTAR_BENEFICIARIOS_POR_FILTROS = "SubsidioMonetario.consultar.beneficiarios.por.filtros";    
    
    /**
     * Constante con el nombre de la consulta que obtiene la informacion del afiliado con relacion al beneficiario
     */
    public static final String CONSULTAR_AFILIADO_RELACION_POR_BENEFICIARIO = "SubsidioMonetario.buscar.informacion.afiliado.relacion.beneficiario";

    public static final String CONSULTAR_AFILIADO_POR_BENEFICIARIO = "SubsidioMonetario.consultar.afiliado.por.beneficiario";

    public static final String CONSULTAR_BENEFICIARIOS_BLOQUEADOS = "SubsidioMonetario.consultar.beneficiarios.bloqueados";
    
    public static final String CONSULTAR_EXISTENCIA_BENEFICIARIOS_BLOQUEADOS_CORE = "SubsidioMonetario.consultar.existencia.beneficiarios.bloqueados.core";
    
    public static final String CONSULTAR_EXISTENCIA_BENEFICIARIOS_BLOQUEADOS_SUBSIDIO = "SubsidioMonetario.consultar.existencia.beneficiarios.bloqueados.subsidio";
    
    public static final String CONSULTAR_BENEFICIARIOS_BLOQUEADOS_POR_FILTROS = "SubsidioMonetario.consultar.beneficiarios.bloqueados.por.filtros";
    
    public static final String CONSULTAR_PAR_AFILIADOS_BENEFICIARIOS_BLOQUEADOS_POR_FILTROS = "SubsidioMonetario.consultar.par.afiliados.beneficiarios.bloqueados.por.filtros";
    
    public static final String DESBLOQUEAR_PAR_AFILIADO_BENEFICIARIO = "SubsidioMonetario.desbloquear.par.afiliado.beneficiario";
    
    public static final String CONSULTAR_TRABAJADOR_BLOQUEADO_SUBSIDIO = "SubsidioMonetario.consultar.trabajador.bloqueado.subsidio";

    public static final String DESBLOQUEAR_CUOTA_MONETARIA_BENEFICIARIOS = "SubsidioMonetario.desbloquear.cuota.monetaria.beneficiarios";
    
    public static final String CONSULTAR_BENEFICIARIOS_NOEXISTEN = "SubsidioMonetario.consultar.beneficiarios.noExisten";
    
    /**
     * cc liquidacion paralela
     */
    public static final String CONSULTAR_BENEFICIARIO_DERECHO_ASIGNADO = "subsidioMonetario.consultar.beneficiarios.derechoAsignado.MismoPeriodo";
    /**
     * CONSTANTE PARA VERIFICAR SI EL PROCESO STAGING SE ESTÁ EJECUTANDO
     */
    public static final String CONSULTAR_PROCESO_ACTUALIZACION_STAGING = "subsidioMonetario.consultar.proceso.actualizacion.staging";
    
    /**
     * cc liquidacion paralela
     */
    public static final String CONSULTAR_TIPO_LIQUIDACION_A_APROBAR = "subsidioMonetario.consultar.tipoLiquidacion.aprobar";
    
    public static final String ACTUALIZAR_ESTADO_DERECHO_RADICADO = "SubsidioMonetario.actualizar.estadoDerecho.LiquidacionParalela";
    
    /**
    * CC liquidacion paralela tercera parte. Verifica personas afiliadas sin condiciones en staging
    */
    public static final String VERIFICAR_CONDICION_PERSONA_AFILIADO = "SubsidioMonetario.consultar.condicionPersonaAfiliado.LiquidacionParalela";
    
    /**
     * CC liquidacion paralela tercera parte. Verifica personas afiliadas sin condiciones en staging
     */
    public static final String VERIFICAR_CONDICION_PERSONA_EMPLEADOR = "SubsidioMonetario.consultar.condicionPersonaEmpleador.LiquidacionParalela";
    
    /**
     * CC liquidacion paralela tercera parte. Verifica personas afiliadas sin condiciones en staging
     */
    public static final String CONSULTAR_PERSONAS_SIN_CONDICIONES = "SubsidioMonetario.consultar.personasLiquidacionEspecifica.sinCondiciones";
    
    /**
     * CC liquidacion paralela tercera parte. Verifica personas afiliadas sin condiciones en staging
     */
    public static final String EJECUTAR_SP_USP_SM_GET_MARCAAPROBACIONSEGNIVEL = "USP_SM_GET_MarcaAprobacionSegNivel";
    
    public static final String CONSULTAR_BENEFICIARIOS_IDENTIFICACION = "SubsidioMonetario.consultar.beneficiarios.por.tipoYNumeroIdentificacion";
    
    public static final String CONSULTAR_BENEFICIARIO_BLOQUEO = "SubsidioMonetario.validar.existencia.beneficiarios.archivo.bloqueo";
    
    public static final String CONSULTAR_PAR_AFILIADO_BENEFICIARIO_BLOQUEADOS = "SubsidioMonetario.consultar.par.afiliadoBeneficiario.bloqueados";
    
    public static final String CONSULTAR_PROCESO_SUBSIDIO = "SubsidioMonetario.consultar.proceso.subsidio";
    
    public static final String CONSULTAR_PROCESO_SUBSIDIO_ELIMINACION = "SubsidioMonetario.consultar.procesoEliminacion.subsidio";
    
    /**
     * Consultar trabajador dependiente fallecido o beneficiarios de un trabajador fallecidos
     */
    public static final String CONSULTAR_BENEFICIARIOS_FALLECIDOS_POR_NUMERO_LIQUIDACION = "SubsidioMonetario.consultarBeneficiariosFallecidos.por.numeroLiquidacion";
    
    public static final String CONSULTAR_DETALLE_POR_NUMERO_RADICADO_PROYECCION_SUBSIDIO = "SubsidioMonetario.consultarProyeccionSubsidio.por.numeroRadicacion";
    
    
    public static final String SUBSIDIO_INICIO_PROCESO_ELIMINACION = "SubsidioMonetario.registrar.inicio.procesoEliminacion";
    
    public static final String CONSULTAR_DESCUENTOS_SUBSIDIO_TRABAJADOR = "SubsidioMonetario.consultar.descuentosSubsidioTrabajador";
    
    public static final String CONSULTAR_ENTIDADES_DESCUENTO = "SubsidioMonetario.consultar.entidadesDescuento";
    
    public static final String STORED_PROCEDURE_WEB_SUBSIDIO_ESPECIE_LIQUIDACIÓN_MANUAL = "stored.procedure.consultar.especie.liquidacion.manual";
    
    public static final String CONSULTAR_IDENTIFICACION_AFILIACION_CUOTA_MONETARIA_IVR = "SubsidioMonetario.consultar.afiliacion.couta.monetaria.ivr";
    
    public static final String CONSULTAR_LISTADO_PERIODOS_CUOTA_MONETARIA_IVR = "SubsidioMonetario.consultar.listado.periodos.couta.monetaria.ivr";

    public static final String CONSULTAR_LIQUIDACION_FALLECIMIENTO = "SubsidioMonetario.consultar.liquidacion.fallecimiento";

    public static final String  CONSULTA_LIQUIDACION_F_PROGRAMADO = "SubsidioMonetario.StoredProcedures.LiquidacionesFallecimientoProgramado";

    public static final String USP_SM_UTIL_INSERT_CONDICIONES_DBO = "SubsidioMonetario.StoredProcedure.InsercionCondiciones.Dbo";

    public static final String USP_SM_GET_SOLICITUDES_LIQUIDACION_TRABAJADOR = "Solicitudes_liquidacion_trabajador_2";

    public static final String USP_SM_GET_CONSULTA_LIQUIDACIONES_POR_EMPLEADOR = "SubsidioMonetario.sp.vista360.empleador.liquidacion";
}
