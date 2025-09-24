package com.asopagos.subsidiomonetario.pagos.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de las NamedQueries del servicio<br>
 * <b>Módulo:</b> Subsidios HU-31-XXX <br/>
 * 
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
public class NamedQueriesConstants {

    /**
     * Constructor privado por ser clase utilitaria
     */
    private NamedQueriesConstants() {
    }

    /**
     * HU-31
     * Consulta que se encarga de buscar una cuenta de administrador del subsidio
     * por medio del id de la base de datos y convertir la respuesta en DTO.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_POR_ID = "PagosSubsidioMonetario.buscarCuentaAdminSubsidioDTO.porID";

    /**
     * HU-31
     * Consulta busca una cuenta de administrador del subsidio a partir del id.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_ID = "PagosSubsidioMonetario.buscarCuentaAdminSubsido.id";

    /**
     * HU-31-208 Y 221
     * Consulta que busca los detalles de subsidios asignados que no van a ser anulados y los retorna en forma de DTO.
     */
    public static final String CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_DTO_NO_ANULADOS = "PagosSubsidioMonetario.DetalleSubsidioAsignado.buscarDetallesSubsidioAsignadoDTONoAnulados";

    /**
     * HU-31-205
     * Consulta que se encarga de ejecutar el procesamiento de almacenamiento
     * para comparar los datos que hay en la tabla de la cuenta administrador de subsidio
     * con los registros que hay en la tabla temporal del archivo de retiro del tercero pagador.
     */
    public static final String PROCEDURE_CONCILIAR_CAMPOS_ARCHIVO_TEMPORAL_RETIRO_CON_CUENTA_ADMIN_SUBSIDIO = "CuentaAdministradoSubsidio.StoredProcedures.conciliar.campos.archivo.temporal.retiro";

    /**
     * HU-31-200
     * Consulta que busca una transaccón fallida registrada por el id.
     */
    public static final String CONSULTAR_TRANSACCION_FALLIDA_SUBSIDIO_POR_ID = "PagosSubsidioMonetario.TransaccionesFallidasSubsidio.buscarPorID";

    /**
     * HU-31-200
     * Consulta que busca las transaccones fallidas por un rango de fecha de inicio y fin.
     * Esa transacciones fallidas las convierte en DTO.
     */
    public static final String CONSULTAR_TRANSACCIONES_FALLIDAS_DTO_POR_RANGO_DE_FECHAS = "PagosSubsidioMonetario.TransaccionesFallidasSubsidio.buscarTransacionesFallidasDTO.porRangoDeFechas";

    /**
     * Consulta que busca los detalles de subsidios asignados asociados a una cuenta de administrador de subsidio.
     */
    public static final String CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_DTO_ASOCIADOS_CUENTA_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.DetalleSubsidioAsignadoDTO.asociado.cuentaAdministradorSubsidio";

    /**
     * HU-31-199
     * Consulta que se encarga de traer todos los registro de la cuenta de administrador del subsidio que sean de tipo abono
     * con estado enviado y hayan sido pagados por el medio de pago Bancos (En el caso del modelo de datos seria por medio de pago
     * transferencia)
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscarPor.tipoTransaccionAbono.estadoEnviado.MedioDePagoBancos";

    /**
    * HU-31-199
    * Consulta que se encarga de traer todos los registro de la cuenta de administrador del subsidio que sean de tipo abono
    * con estado enviado y hayan sido pagados por el medio de pago Bancos (En el caso del modelo de datos seria por medio de pago
    * transferencia)
    */
   public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_BANCO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscarCuentas.MedioDePagoBancos";
   
   /**
    * HU-31-199
    * Actualiza el estado de las cuentas asociadas a abonos tipo Transeferencia con medio de pago Bancos, 
    * filtrando las cuentas que no fueron exitosas.
    */
   public static final String USP_PG_CONFIRMAR_ABONOS_MEDIO_PAGO_BANCOS = "USP_PG_ConfirmarAbonosMedioPagoBancos";

    
    /**
     * HU-31-201
     * Consulta que se encarga de buscar los registros de administradores de subsidios que cumplen con todos los filtros
     * señalados en la HU.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_TRANSACCIONES_POR_FILTROS = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.verTransaccionesConFiltros";

    /**
     * HU-31-201
     * Consulta que se encarga de buscar los registros de administradores de subsidios que cumplen con todos los filtros
     * señalados en la HU.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_TRANSACCIONES_POR_FILTROS_PAGINADA = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.verTransaccionesConFiltrosPaginada";
    
    
    /**
     * HU-31-203
     * Consulta que se encarga de buscar los registros en la cuenta del administrador del subsidio con el estado de transacción aplicado,
     * por cualquier medio de pago y que correspondan a un administrador del subsidio especifico.
     */
    public static final String CONSULTAR_SALDO_CUENTA_ADMIN_SUBSIDIO_ESTADO_APLICADO_MEDIOS_DE_PAGOS_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscarPorEstadoAplicado.cualquierMedioDePago.adminSubsidio";

    /**
     * Consulta que trae todos los sitios de pagos según el municipio.
     */
    public static final String CONSULTAR_SITIO_DE_PAGO_POR_MUNICIPIO = "PagosSubsidioMonetario.buscarSitioDePago.municipio";

    /**
     * Consulta que trae el departamento según el municipio.
     */
    public static final String CONSULTAR_DEPARTAMENTO_POR_MUNICIPIO = "PagosSubsidioMonetario.buscarDapartamento.municipio";

    /**
     * Consulta que trae los convenios relacionados asociados a un usuario de genesys
     */
    public static final String CONSULTAR_CONVENIO_NOMBRE_USUARIO_GENESYS = "PagosSubsidioMonetario.buscarConvenio.usuarioGenesys";

    /**
     * Consulta que trae los convenios con idConvenio
     */
    public static final String CONSULTAR_CONVENIO_NOMBRE_ID_CONVENIO = "PagosSubsidioMonetario.buscarConvenio.idConvenio";
        
    /**
     * HU-31-210
     * Consulta que se trae todos los convenios del tercero pagador registrados.
     */
    public static final String CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO_CON_DOCUMENTOS_SOPORTE = "PagosSubsidioMonetario.buscar.conveniosTerceroPagadorDTO.documentosSoporte";

    /**
     * HU-31-210
     * Consulta que se trae todos los convenios del tercero pagador registrados.
     */
    public static final String CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO = "PagosSubsidioMonetario.buscar.conveniosTerceroPagadorDTO";

    /**
     * Consulta que obtiene todos los registros de la cuenta del administrador del subsidio y la respuesta la retorna en forma de DTO.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_TODOS_DTO = "PagosSubsidioMonetario.CuentaAdminSubsidio.buscar.todos.DTO";

    /**
     * Consulta que obtiene todos los registros de los detalles de subsidios asignados en forma de DTO que contengan dichos IDs.
     */
    public static final String CONSULTAR_DETALLES_SUBSIDIOS_ASIGNADOS_DTO_POR_IDS = "PagosSubsidioMonetario.DetalleSubsidioAsignado.DTO.buscar.por.IDS";

    /**
     * Consulta que obtiene el identificador de la persona autorizada que esta relacionada con ek registro de la cuenta del adminsitrador de
     * subsidio.
     */
    public static final String CONSULTAR_IDENTIFICADOR_PERSONA_AUTORIZADA_CUENTA_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.buscar.personaAutorizada.cobro.cuentaAdminSubsidio";

    /**
     * HU-31-210
     * Consulta que devuelve el convenio del tercero pagador
     * que este relacionado con el id.
     */
    public static final String CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_ID = "PagosSubsidioMonetario.buscar.convenioTerceroPagador.por.id";

    /**
     * HU-31-225
     * Consulta que obtiene el listado de registros candidatos relacionados con la cuenta del administrador del subsidio y el detalle de
     * subsidio asignado
     * para ser anulados por perdida de derecho.
     */
    public static final String CONSULTAR_LISTADO_PARA_ANULACION_POR_PERDIDA_DE_DERECHO = "PagosSubsidioMonetario.generar.listado.anulacion.por.perdidad.de.derecho";

    /**
     * HU-31-205
     * Constante que ejecuta el procedimiento almacenado (SP) para la validación de los campos de los archivos
     * de retiros del tercero pagador con la cuenta.
     */
    public static final String PROCEDURE_USP_VALIDAR_CAMPOS_ARCHIVO_RETIRO_PAGOS = "USP_PG_ValidarCamposArchivoRetiroPagos";
    
    
    /**
     * 
     * 
     * 
     */
    public static final String PROCEDURE_USP_PG_VALIDAR_CONTENIDO_ARCHIVO_TERCERO_PAGADOR_EFECTIVO = "USP_PG_ValidarContenidoArchivoTerceroPagadorEfectivo";
    public static final String PROCEDURE_USP_PG_INSERT_RESULTADOS_VALIDACION_CARGA_MANUAL_TER_PAG = "USP_PG_InsertRestuladosValidacionCargaManualRetiroTerceroPag";

    /**
     * HU-31-205
     * Constante que busca por medio del id del documento cargado, si fue conciliado satisfactoriamente o no
     */
    public static final String CONSULTAR_ESTADO_CONCILIACION_ARCHIVO_RETIRO_TERCERO_PAGADOR = "PagosSubsidioMonetario.buscar.estado.archivo.retiro.tercero.pagador";

    /**
     * HU-31-205
     * Constante que busca las inconsistencias del archivo cargado que contiene los retiros realizados por el tercero pagador.
     */
    public static final String CONSULTAR_INCOSISTENCIAS_ARCHIVO_RETIRO_TERCERO_PAGADOR = "PagosSubsidioMonetario.buscar.inconsistenciasDTO.archivo.retiro.tercero.pagador";

    /**
     * HU-31-205
     * Sentencia que permite actualizar todos los registros de retiros de la cuenta del administrador del subsidio que fueron
     * conciliados en el archivo
     */
    public static final String ACTUALIZAR_ESTADO_RETIRO_CUENTA_CONCILIADO_ARCHIVO_RETIRO_TERCERO_PAGAGOR = "PagosSubsidioMonetario.actualizar.estado.cuenta.conciliacion.archivo.retiro.tercero.pagador";

    /**
     * HU-31-219
     * Constante para la consulta de busqueda de subsidios monetarios candidatos para el cambio de medio de pagos con los filtros necesarios
     * y el tipo de medio
     */
    public static final String CONSULTAR_CANDIDATOS_SUBSIDIOS_CAMBIO_MEDIO_DE_PAGO = "PagosSubsidioMonetario.CuentaAdminSubsidioDTO.consultar.abonos.cambio.medioDePago";

    /**
     * HU-31-206
     * Consulta que se encarga de filtrar los registros de la cuenta del administrador del subsidio para generar los informes de retiro
     * cuando el estado es SIN CONCILIAR y las dos listas tanto de afiliados y beneficiarios no son null
     */
    public static final String CONSULTAR_INFORMES_CONSUMOS_RETIRO_CUENTA_ADMIN_SUBSIDIO_UNO = "PagosSubsidioMonetario.CuentaAdminSubsidioDTO.generar.informes.retiro.uno";

    /**
     * HU-31-206
     * Consulta que se encarga de filtrar los registros de la cuenta del administrador del subsidio para generar los informes de retiro
     * cuando el estado es CONCILIADO y las dos listas tanto de afiliados y beneficiarios no son null
     */
    public static final String CONSULTAR_INFORMES_CONSUMOS_RETIRO_CUENTA_ADMIN_SUBSIDIO_DOS = "PagosSubsidioMonetario.CuentaAdminSubsidioDTO.generar.informes.retiro.dos";

    /**
     * HU-31-222
     * Consulta que se encarga de filtrar los registros de la cuenta del administrador del subsidio y detalle de subsidio asignado para
     * obtener la transacciones de tipo abono en estado cobrado.
     */
    public static final String CONSULTAR_TRANSACCIONES_ABONO_COBRADOS = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.consultar.abonos.cobrados";

    /**
     * HU-31-210
     * Consulta que se encarga de buscar si existe un convenio relacionado con el identificador de empresa
     */
    public static final String CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_ID_EMPRESA = "PagosSubsidioMonetario.buscar.ConvenioTerceroPagador.por.Id.Empresa";
    
    /**
     * 
     * 
     */
    public static final String CONSULTAR_ARCHIVO_TERCERO_PAGADOR_EFECTIVO_POR_ID = "PagosSubsidioMonetario.buscar.ArchivoRetiroTerceroPagadorEfectivo.por.Id";

    /**
     * HU-31-205
     * Constante que representa la consulta para cargar todos los nombres de los convenios de los terceros pagadores registrados.
     */
    public static final String CONSULTAR_NOMBRES_CONVENIOS_TERCEROS_PAGADORES = "PagosSubsidioMonetario.mostar.NombreConvenios";

    /**
     * Constante que representa la consulta encargada de buscar el registro de operación realizado en una transacción en linea.
     */
    public static final String CONSULTAR_REGISTRO_OPERACION_SUBSIDIO_POR_ID = "PagosSubsidioMonetario.buscar.registro.operacion.subsidio.por.id";

    /**
     * Constante que representa la consulta encargada de buscar una cuenta de administrador de subsidio a partir del identificador de
     * transacción del tercero pagador.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO_PAGADOR = "PagosSubsidioMonetario.buscar.cuenta.admin.subsidio.por.id.transaccion.tercero.pagador";

    /**
     * Constante que representa la consulta encargada de buscar una cuenta de administrador de subsidio a partir del identificador de
     * transacción del tercero pagador, sin Punto Cobro.
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO= "PagosSubsidioMonetario.buscar.cuenta.admin.subsidio.por.id.transaccion.tercero";
    
    /**
     * Constante que representa la consulta encargada de buscar los medios de pagos relacionados a un administrador de subsidio
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ASOCIADOS_ADMINISTRADOR_SUBSIDIO = "PagosSubsidioMonetario.buscar.mediosDePagos.asociados.adminSubsidio";
    
    /**
     * Constante que representa la consulta encargada de buscar los medios de pagos por id
     */
    public static final String CONSULTAR_MEDIO_DE_PAGO_POR_ID = "PagosSubsidioMonetario.buscar.medioDePagos.Id";

    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TARJETA de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TARJETA = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.tarjeta.admin.subsidio";
    
    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TARJETA de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TARJETA_ACTIVO_CAMBIO = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.tarjeta.admin.subsidio.activo.cambio";


    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TRANSFERENCIA de medio de pago asociado a un
     * administrador de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TRANSEFERENCIA = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.transferencia.admin.subsidio";

    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TRANSFERENCIA de medio de pago asociado a un
     * administrador de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_TRANSEFERENCIA_ACTIVO_CAMBIO = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.transferencia.admin.subsidio.activo.cambio";

    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo EFECTIVO de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_EFECTIVO = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.efectivo.admin.subsidio";
    
    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo EFECTIVO de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_ADMIN_SUBSIDIO_POR_TIPO_EFECTIVO_CAMBIO = "PagosSubsidioMonetario.buscar.medios.de.pagos.por.tipo.efectivo.admin.subsidio.cambio";
    
    
    /**
     * HU-31-202
     * Consulta encargada de buscar los detalles de subsidios asignado que estan relacionados para el calculo del saldo del administrador
     * del subsidio
     */
    public static final String CONSULTAR_DETALLES_SUBSIDIOS_ASIGNADOS_RELACIONADOS_CON_CONSULTA_SALDO_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.DetalleSubsidioAsignadoDTO.consultar.detalles.asociados.saldo";

    /**
     * HU-31-203
     * Constante que representa la Consulta que trae los abonos que estan en estado 'SOLICITADO' por transacción de retiro de un
     * administrador de subsidio con un identificador de transacción de tercero pagador.
     */
    public static final String CONSULTAR_ABONOS_ESTADO_SOLICITADO_POR_RETIRO_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscar.abonos.retiros.estado.solicitado.por.adminSubsidio";
    
    /**
     * HU-31-203
     * Constante que representa la Consulta que trae los abonos que estan en estado 'SOLICITADO' por transacción de retiro de un
     * administrador de subsidio con un identificador de transacción de tercero pagador y sitio cobro.
     */
    public static final String CONSULTAR_ABONOS_ESTADO_SOLICITADO_POR_RETIRO_ADMIN_SUBSIDIO_PUNTO_COBRO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscar.abonos.retiros.estado.solicitado.por.adminSubsidio.puntoCobro";

    /**
     * Constante que representa la consulta que obtiene el valor del retiro realizado por un administrador de subsidio en especifico,
     * asociado a un identificador de tercero pagador único.
     */
    public static final String CONSULTAR_VALOR_RETIRO_POR_ADMIN_SUBSIDIO_E_ID_TERCERO_PAGADOR = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.obtener.valor.retiro.por.adminSubsidio.idTerceroPagador";
    /**
     * Constante que representa la consulta que obtiene el valor del retiro realizado por un administrador de subsidio en especifico,
     * asociado a un identificador de tercero pagador único.
     */
    public static final String CONSULTAR_VALOR_RETIRO_POR_ID_CUENTA = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.obtener.valor.retiro.por.id.cuenta";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información que representa los totales pendientes por dispersar para una
     * liquidación
     */
    public static final String CONSULTAR_DISPERSION_TOTALES_PENDIENTES = "PagosSubsidioMonetario.consultar.dispersion.totalesPendientes";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información de los totales para los pagos pendientes a dispersar al medio tarjeta
     */
    public static final String CONSULTAR_RESUMEN_PAGOS_TARJETA = "PagosSubsidioMonetario.consultar.resumen.pagosTarjeta";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información de los totales para los pagos pendientes a dispersar al medio efectivo
     */
    public static final String CONSULTAR_RESUMEN_PAGOS_EFECTIVO = "PagosSubsidioMonetario.consultar.resumen.pagosEfectivo";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información de los totales para los pagos pendientes a dispersar al medio banco
     */
    public static final String CONSULTAR_RESUMEN_PAGOS_BANCO = "PagosSubsidioMonetario.consultar.resumen.pagosBanco";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información de los totales para pagos pendientes a dispersar por concepto de
     * pignoración a entidades internas
     */
    public static final String CONSULTAR_RESUMEN_PAGOS_ENTIDAD_DESCUENTO_INTERNA = "PagosSubsidioMonetario.consultar.resumen.pagosEntidadesDescuento.interna";

    /**
     * HU-311-411
     * Constante para la consulta que permite obtener la información de los totales para pagos pendientes a dispersar por concepto de
     * pignoración a entidades externas
     */
    public static final String CONSULTAR_RESUMEN_PAGOS_ENTIDAD_DESCUENTO_EXTERNA = "PagosSubsidioMonetario.consultar.resumen.pagosEntidadesDescuento.externa";

    /**
     * HU-311-441
     * Constante para la cosnulta que permite obtener la información de encabezado para los detalles de los pagos pendientes a dispersar
     */
    public static final String CONSULTAR_DETALLE_PAGOS_ENCABEZADO = "PagosSubsidioMonetario.consultar.detallePagos.encabezado";

    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos para el medio tarjeta
     */
    public static final String CONSULTAR_DETALLE_PAGOS_MEDIO_TARJETA = "PagosSubsidioMonetario.consultar.detallePagos.medioTarjeta";
    
    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos para el medio tarjeta
     */
    public static final String USP_SM_GET_CONSULTARDISPERSIONMONTOLIQUIDADO = "USP_SM_GET_ConsultarDispersionMontoLiquidado";

    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos para el medio efectivo
     */
    public static final String CONSULTAR_DETALLE_PAGOS_MEDIO_EFECTIVO = "PagosSubsidioMonetario.consultar.detallePagos.medioEfectivo";

    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos para el medio banco
     */
    public static final String CONSULTAR_DETALLE_PAGOS_MEDIO_BANCO = "PagosSubsidioMonetario.consultar.detallePagos.medioBanco";

    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos a las entidades de descuento
     */
    public static final String CONSULTAR_DETALLE_PAGOS_ENTIDADES_DESCUENTO = "PagosSubsidioMonetario.consultar.detallePagos.entidadesDescuento";

    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos a entidades bancarias para la generación del
     * archivo de transacciones
     */
    public static final String CONSULTAR_PAGOS_CONSIGNACIONES_BANCARIAS_GENERACION_ARCHIVO = "PagosSubsidioMonetario.consultar.pagosBanco.archivoTransacciones";
    
    /**
     * HU-311-441
     * Constante para la consulta que permite obtener la información detallada de los pagos a entidades bancarias para la generación del
     * archivo de transacciones
     */
    public static final String CONSULTAR_PAGOS_CONSIGNACIONES_BANCARIAS_GENERACION_ARCHIVO_MES_POR_MES = "PagosSubsidioMonetario.consultar.pagosBanco.archivoTransacciones.mesPorMes";
    
    /**
     * HU-311-441
     * Constante para la consulta que permite obtener el tipo de desembolso de la solicitud
     */
    public static final String CONSULTAR_SOLICITUD_LIQUIDACION_TIPO_DESEMBOLSO = "PagosSubsidioMonetario.consultar.solicitud.tipoDesembolso";

    /**
     * HU 311-441
     * Constante para la consulta que permite obtener las cuentas de administrador de subsidio asociadas a una solicitud de liquidación
     */
    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_RADICACION_MEDIOS_PAGO = "PagosSubsidioMonetario.consultar.cuentasAdministradorSubsidio.numeroRadicacion";
    
    /**
     * 
     * 
     */
    public static final String ACTUALIZAR_DISPERSION_ESTADO = "PagosSubsidioMonetario.acualizar.dispesion.estado";


    /**
     * HU 311-441
     * Constante para la consulta que permite obtener la lista de cuentas de los administradores de subsidio con el medio de pago tarjeta
     */
    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_MEDIO_TARJETA = "PagosSubsidioMonetario.consultar.cuentasAdministrador.medioTarjeta";

    
    public static final String CONSULTAR_CUENTA_ADMIN_MEDIO_TARJETA = "PagosSubsidioMonetario.consultar.cuentaAdmini.medioTarjeta";
    
    /**
     * HU 311-441
     * Constante para la consulta que permite obtener las cuentas de administrador de subsidio asociadas a una solicitud de liquidación
     */
    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_RADICACION_MEDIOS_PAGO_IDS = "PagosSubsidioMonetario.consultar.cuentasAdministradorSubsidio.numeroRadicacion.ids";
    
    /**
     * HU 311-441
     * Constante para ejecutar el SP que dipersa las cuentas en estado enviado
     */
    public static final String USP_PG_DISPERSAR_PAGOS_ESTADO_ENVIADO = "USP_PG_DispersarPagosEstadoEnviado";
    
    /**
     * HU 311-441
     * Constante para ejecutar el SP que dipersa las cuentas en estado aplicado
     */
    public static final String USP_PG_DISPERSAR_PAGOS_ESTADO_APLICADO = "USP_PG_DispersarPagosEstadoAplicado";
    
    /**
     * HU-31-203
     * Consulta que se encarga de mostrar el saldo a favor de que tiene registrado el administrador de subsidio en los abonos
     */
    public static final String CONSULTAR_SALDO_A_FAVOR_CUENTA_ADMIN_SUBSIDIO_POR_MEDIO_DE_PAGO_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.consultar.saldo.cualquierMedioDePago.adminSubsidio";

    
    public static final String CONSULTAR_SALDO_A_FAVOR_ABONO_CUENTA_ADMIN_SUBSIDIO_POR_MEDIO_DE_PAGO_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.consultar.saldo.abono.cualquierMedioDePago.adminSubsidio";
    /**
     * HU-31-210
     * Consulta que busca los convenios del tercero pagador sin relación a un documento de soporte
     */
    public static final String CONSULTAR_CONVENIOS_TERCERO_PAGADOR_DTO_SIN_DOCUMENTOS_SOPORTE = "PagosSubsidioMonetario.buscar.conveniosTerceroPagadorDTO.SinDocumentosSoporte";

    /**
     * HU-31-208,207
     * Constante para la consulta que permite obtener la lista de cuentas de los administradores que se encuentran dentro de los
     * identificadores parametrizados
     */
    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_IDS = "PagosSubsidioMonetario.consultar.cuentasAdministradorSubsidio.ids";

    /**
     * HU-31-208,207
     * Constante para la consulta que permite obtener la lista de cuentas de los administradores que se encuentran dentro de los
     * identificadores parametrizados para el medio de pago tarjeta
     */
    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_MEDIO_TARJETA_IDS = "PagosSubsidioMonetario.consultar.cuentasAdministradorSubsidio.medioTarjeta.ids";
    
    

    /**
     * HU-31-ANEXO REVERSIÓN
     * Constante para la consulta que permite obtener el retiro asociado a un identificador de transacción del tercero pagador,
     * de tipo TARJETA.
     */
    public static final String CONSULTAR_RETIRO_TARJETA_PARA_REALIZAR_REVERSION = "PagosSubsidioMonetario.consultar.retiro.tarjeta.para.reversion";

    /**
     * HU-31-ANEXO REVERSIÓN
     * Constante para la consulta que trae los abonos que estan en estado 'COBRADO' asociados al retiro al cual se le efectuara el reverso
     */
    public static final String CONSULTAR_ABONOS_COBRADOS_ASOCIADOS_RETIRO_PARA_REALIZAR_REVERSION = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscar.abonos.asociados.estado.cobrado.retiros.para.reversion";

    /**
     * HU-31-227
     * Constante para la consulta de la solicitud de anulacion de subsidio cobrado por identificador
     */
    public static final String CONSULTAR_SOLICITUD_ANULACION_SUBSIDIO_COBRADO_ID = "PagosSubsidioMonetario.SolicitudAnulacionSubsidioCobrado.idSolicitudAnulacionSubsidioCobrado";

    /**
     * HU-31-227
     * Consulta que se encarga de filtrar los registros de la cuenta del administrador del subsidio y detalle de subsidio asignado para
     * obtener la transacciones de tipo abono en estado cobrado asociadas a una solciitud de anulacion de subsidio cobrado.
     */
    public static final String CONSULTAR_TRANSACCIONES_ABONO_COBRADOS_POR_NUMERO_RADICADO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.consultar.abonos.cobrados.numeroRadicado";

    /**
     * HU-31-ANEXO
     * Consulta que se encarga de buscar un archivo de consumo de tarjeta referente de ANIBOL por el nombre.
     */
    public static final String CONSULTAR_ARCHIVO_CONSUMO_TARJETA_ANIBOL_POR_NOMBRE = "PagosSubsidioMonetario.consultar.archivo.consumo.ANIBOL.por.nombre";
    
    /**
     * HU-31-ANEXO
     * Consulta que se encarga de buscar un archivo de consumo de tarjeta referente de ANIBOL por el nombre.
     */
    public static final String CONSULTAR_ARCHIVO_CONSUMO_TERCERO_PAGAGOR_EFECTIVO_POR_NOMBRE = "PagosSubsidioMonetario.consultar.archivo.consumo.tercero.pagador.efectivo.por.nombre";
      

    /**
     * HU-31-ANEXO
     * Consulta que se encarga de buscar las cuentas de administradores de subsidio relacionadas a un registro de tarjeta en el archivo de
     * consumo de ANIBOL.
     */
    public static final String CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_RELACIONADAS_REGISTRO_TARJETA_ANIBOL = "PagosSubsidioMonetario.consultar.CuentaAdministradorSubsidioDTO.relacionadas.numero.tarjeta.registro.ANIBOL";

    /**
     * HU-31-202
     * Consulta que se encarga de buscar el codigo de un municipio de la CCF
     */
    public static final String BUSCAR_CODIGO_MUNICIPIO_POR_ID = "PagosSubsidioMonetario.consultar.Municipio.codigo.por.id";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener los totales relacionados a la dispersión de una liquidación por fallecimiento
     */
    public static final String CONSULTAR_DISPERSION_FALLECIMIENTO_TOTALES_PENDIENTES = "PagosSubsidioMonetario.consultar.dispersionFallecimiento.totalesPendientes";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener los totales de la cabecera para la información del medio de pago
     */
    public static final String CONSULTAR_CABECERA_DISPERSION_MEDIO_PAGO = "PagosSubsidioMonetario.consultar.cabeceraDispersion.medioPago";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener los resultados por administrador en el medio de pago definido, para un proceso de
     * liquidación por fallecimiento
     */
    public static final String CONSULTAR_RESULTADOS_DISPERSION_FALLECIMIENTO_POR_ADMINISTRADOR_MEDIO_PAGO = "PagosSubsidioMonetario.consultar.resultadosDispersion.administradorMedioPago";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener la información detallada por administrador de subsidio para el medio de pago tarjeta,
     * en una liquidación de fallecimiento
     */
    public static final String CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_TARJETA = "PagosSubsidioMonetario.consultar.detallePorAdministrador.liquidacionFallecimiento.pagosTarjeta";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener la información detallada por administrador de subsidio para el medio de pago efectivo,
     * en una liquidación de fallecimiento
     */
    public static final String CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_EFECTIVO = "PagosSubsidioMonetario.consultar.detallePorAdministrador.liquidacionFallecimiento.pagosEfectivo";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener la información detallada por administrador de subsidio para el medio de pago banco -
     * consignaciones, en una liquidación de fallecimiento
     */
    public static final String CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_BANCO_CONSIGNACIONES = "PagosSubsidioMonetario.consultar.detallePorAdministrador.liquidacionFallecimiento.pagosBancoConsignaciones";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener la información detallada por administrador de subsidio para el medio de pago banco -
     * pagos judiciales, en una liquidación de fallecimiento
     */
    public static final String CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_PAGOS_BANCO_JUDICIALES = "PagosSubsidioMonetario.consultar.detallePorAdministrador.liquidacionFallecimiento.pagosBancoJudiciales";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener la información detallada por administrador de subsidio de los descuentos aplicados a
     * los beneficiarios en sus grupos familiares
     */
    public static final String CONSULTAR_DETALLE_POR_ADMINISTRADOR_LIQUIDACION_FALLECIMIENTO_DESCUENTOS = "PagosSubsidioMonetario.consultar.detallePorAdministrador.liquidacionFallecimiento.descuentos";

    /**
     * HU-317-508
     * Constante para la consulta que permite obtener el periodo relacionado a la primer cuota para una liquidación de fallecimiento
     */
    public static final String CONSULTAR_PRIMER_PERIODO_CUOTAS_LIQUIDACION_FALLECIMIENTO = "PagosSubsidioMonetario.consultar.primerPeriodo.cuotas.liquidacionFallecimiento";

    /**
     * Consulta que se encarga de buscar un registro de solicitud de ANIBOL por medio del identificador
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_ANIBOL_POR_ID = "PagosSubsidioMonetario.consultar.registroSolicitudAnibol.por.id";
    
    /**
     * Consulta que se encarga de buscar un registro de solicitud de ANIBOL por medio del id proceso de Anibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_ANIBOL_POR_ID_PROCESO = "PagosSubsidioMonetario.consultar.registroSolicitudAnibol.por.idProceso";

    /**
     * HU-31-210
     * Consulta que permite obtener un convenio del tercero pagador por el nombre
     */
    public static final String CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_NOMBRE = "PagosSubsidioMonetario.consultar.convenioTerceroPagador.por.nombre";


 /**
  * HU-31-210
  * Consulta que permite obtener un convenio del tercero pagador por el nombre
  */
 public static final String CONSULTAR_CONVENIO_TERCERO_PAGADOR_POR_NOMBRE_GENESYS = "PagosSubsidioMonetario.consultar.convenioTerceroPagador.por.nombre.genesys";

    /**
     * Consulta que permite obtener los detalles con ls informacion faltante por el id de cada detalle
     */
    public static final String CONSULTAR_DATOS_EMPLEADOR_ADMIN_BENEFICIARIO_AFILIADO_DETALLES_SUBSIDIO = "PagosSubsidioMonetario.consultar.admin.empleador.afiliado.beneficiario.por.IdsDetalles";

    public static final String CONSULTAR_DATOS_EMPLEADOR_ADMIN_BENEFICIARIO_AFILIADO_DETALLES_SUBSIDIO_PAGINADO = "PagosSubsidioMonetario.consultar.admin.empleador.afiliado.beneficiario.por.IdsDetalles.paginado";
    
    /**
     * HU-317-508
     * Consulta que permite obtener los detalles de subsidios programados a partir de los identificadores de condiciones de los
     * beneficiarios
     */
    public static final String CONSULTAR_DETALLES_PROGRAMADOS_POR_IDS_CONDICIONES_BENEFICIARIOS = "PagosSubsidioMonetario.consultar.detallesProgramados.por.idsCondicionesBeneficiarios";

    /**
     * HU-317-508
     * Consulta que permite obtener los detalles de subsidios programados a partir de los identificadores de condiciones de los
     * beneficiarios
     */
    public static final String CONSULTAR_DETALLES_PROGRAMADOS_POR_IDS_CONDICIONES_BENEFICIARIOS_Y_RADICADO = "PagosSubsidioMonetario.consultar.detallesProgramados.por.idsCondicionesBeneficiariosYnumeroRadicado";
    
    /**
     * HU-317-508
     * Consulta que permite actualizar el estado de los detalles programados para que no se tengan en cuenta para posteriores liquidaciones
     */
    public static final String ACTUALIZAR_ESTADOS_DETALLES_PROGRAMADOS = "PagosSubsidioMonetario.actualizar.estado.detalles.programados";

    /**
     * HU-TRA-001
     * Consulta que permite el identificador de una cuenta segun el resultado de liquidacion de su detalle
     */
    public static final String CONSULTAR_DETALLE_CUENTA_SUBSIDIO_DTO_TRANSACCIONES_POR_RVL = "PagosSubsidioMonetario.DetalleSubsidioAsignadoDTO.porResultadoValidacionLiquidacion";
    
    /**
     * HU-TRA-001
     * Consulta que permite el identificador de una cuenta segun el resultado de liquidacion de su detalle
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_DTO_TRANSACCIONES_POR_RVL = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.porResultadoValidacionLiquidacion";
    
    /**
     * HU-31-201
     * Consulta que permite obtener los detalles de subsidios asignados por medio de los filtros 
     */
    public static final String CONSULTAR_DETALLES_SUBSIDIO_FILTROS_201_CASO_ABONOS_DETALLES = "PagosSubsidioMonetario.consultar.detalles.por.filtros.hu31201.caso.AbonosPlusDetalles";

    /**
     * HU-317-508
     * Consulta que permite obtener la información de los pagos de subsidios monetarios (detalles programados) con la información relevante del pago
     * de un administrador del subsidio 
     */
    public static final String OBTENER_PAGOS_SUBSIDIOS_PENDIENTES_POR_PROGRAMADOS = "PagosSubsidioMonetario.obtener.pagos.subsidio.pendientes.por.programar";

    /**
     * HU-31-219
     * Consulta que permite obtener los registros que se cambiaron al medio de pago BANCOS para posterior generar el archivo.
     */
    public static final String CONSULTAR_PAGOS_TRANSACCIONES_219_GENERACION_ARCHIVO = "PagosSubsidioMonetario.consultar.pagosBancos.transacciones.archivo";

    /**
     * HU-31-201
     * Consulta que obtiene los detalles asociados a las cuentas cobradas a partir del id del retiro
     */
    public static final String OBTENER_DETALLES_RETIROS_CUENTAS = "PagosSubsidioMonetario.consultar.detalles.retiros.por.id";
    
    /**
     * HU-31-201
     * Consulta que obtiene los detalles asociados a las cuentas cobradas a partir del id del retiro
     */
    public static final String OBTENER_DETALLES_RETIROS_CUENTAS_SEGUNDO_NIVEL = "PagosSubsidioMonetario.consultar.detalles.retiros.por.id.segundo.nivel";
    
    
    /**
     * HU-31-201
     * Consulta que obtiene los detalles asociados a las cuentas por id de cuenta
     */
    public static final String OBTENER_DETALLES_CUENTASADMIN_POR_ID = "PagosSubsidioMonetario.consultar.detalles.cuentasAdmin.por.id";
    
    
    
    /**
     * 
     * Consulta que se encarga de buscar las cuentas de administradores de subsidio relacionadas a una liasta de empleadores
     * consumo de ANIBOL.
     */
    public static final String CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_LISTAS = "PagosSubsidioMonetario.buscarCuentaAdminSubsidioDTO.porListas";
    
    /**
     * 
     * Consulta que se encarga de buscar las cuentas de administradores anuladas de subsidio relacionadas a una liasta de empleadores
     * consumo de ANIBOL.
     */
    public static final String CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_LISTAS_ANUL = "PagosSubsidioMonetario.buscarCuentaAdminSubsidioDTO.porListas.anul";
    
    
    /**
     * 
     * Consulta que se encarga de buscar las cuentas de administradores de subsidio relacionadas a una liasta de empleadores
     * consumo de ANIBOL.
     */
    public static final String CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_AFILIADOS = "PagosSubsidioMonetario.buscarCuentaAdminSubsidioDTO.porIdAfiliados";
 
    
    /**
     * 
     * Consulta que se encarga de buscar las cuentas de administradores de subsidio relacionadas a una liasta de empleadores
     * consumo de ANIBOL.
     */
    public static final String CONSULTAR_CUENTAS_ADMIN_SUBSIDIO_BENEFICIARIOS = "PagosSubsidioMonetario.buscarCuentaAdminSubsidioDTO.porIdDetalleBeneficiarios";
 
    
    /**
     * Constante que representa la consulta encargada de buscar los medios de pagos relacionados a un administrador de subsidio
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_INACTIVOS_ASOCIADOS_ADMINISTRADOR_SUBSIDIO = "PagosSubsidioMonetario.buscar.mediosDePagosInactivos.asociados.adminSubsidio";

    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TARJETA de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_TARJETA = "PagosSubsidioMonetario.buscar.medios.de.pagos.inactivo.por.tipo.tarjeta.admin.subsidio";
    
    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo TRANSFERENCIA de medio de pago asociado a un
     * administrador de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_TRANSEFERENCIA = "PagosSubsidioMonetario.buscar.medios.de.pagos.inactivo.por.tipo.transferencia.admin.subsidio";

    /**
     * HU-31-219
     * Constante que representa la consulta encargada de buscar los registros de tipo EFECTIVO de medio de pago asociado a un administrador
     * de subsidio.
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_INACTIVO_ADMIN_SUBSIDIO_POR_TIPO_EFECTIVO = "PagosSubsidioMonetario.buscar.medios.de.pagos.inactivo.por.tipo.efectivo.admin.subsidio";
    
    /**
     * Consulta las solicitudes con medio de pago tarjeta en estado enviado
     */
    public static final String CONSULTAR_SOLICITUDES_TARJETA_ESTADO_ENVIADO = "PagosSubsidioMonetario.buscar.solicitud.tarjeta.enviado";
    
    /**
     * Consulta los números de radicado con medio de pago tarjeta en estado enviado
     */
    public static final String CONSULTAR_RADICADOS_TARJETA_ESTADO_ENVIADO = "PagosSubsidioMonetario.buscar.radicado.tarjeta.enviado";
    
    /**
     * Consulta los registros en RegistroSolicitudAnibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_ANIBOL = "PagosSubsidioMonetario.buscar.registro.solicitud.anibol";
    
    /**
     * Consulta que devuelve las fechas de pagos de los subsidios de acuerdo al periodo pagado y la fecha parametrizada
     */
    public static final String CONSULTAR_FECHAS_PROGRAMADAS_SUB_FALLECIMIENTO = "PagosSubsidioMonetario.consultar.fechas.programadas.sub.fallecimiento";
    
    /**
     * Consulta los registros de solicitud de descuento en RegistroSolicitudAnibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_DESCUENTO_ANIBOL = "PagosSubsidioMonetario.buscar.registro.solicitud.descuento.anibol";
    
    /**
     * Consulta los registros de solicitud de prescripcion en RegistroSolicitudAnibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_DESCUENTO_PRESCRIPCION_ANIBOL = "PagosSubsidioMonetario.buscar.registro.solicitud.descuento.prescripcion.anibol";
    
    public static final String CONSULTAR_REG_SOL_DISP_ORIG_CAMBIO_MEDIO_PAGO_ANIBOL = "PagosSubsidioMonetario.buscar.registro.solicitud.dispersion.origen.cambio.medio.pago.anibol";

    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_SOLICITUD = "PagosSubsidioMonetario.buscarCuentaAdminSubsido.solicitudLiquidacionSubsidio";

    public static final String CONSULTAR_LISTA_DETALLES_SUBSIDIO_ASIGNADO = "PagosSubsidioMonetario.buscarDetalles.subsidio.asignado";

	public static final String CONSULTAR_LISTA_CUENTAS_ADMIN_SUBSIDIO_POR_ID = "PagosSubsidioMonetario.consultarCuentas.admin.subisidio.por.id";
	
	public static final String CONSULTAR_REG_SOL_LIQ_FALLECIMIENTO_ANIBOL = "PagosSubsidioMonetario.buscar.registroSolicitudLiquidacionFallecimientoAnibol";
	
	public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_SUBSIDIO_POR_ID = "PagosSubsidioMonetario.buscar.cuentasAdministradrSubsidioPorId";
    
	public static final String CONSULTAR_PERSONA_EXISTE_Y_ASOCIADA_TARJETA = "PagosSubsidioMonetario.consultar.PersonaExisteYTieneAsociadaLaTarjeta";

    public static final String CONSULTAR_PERSONA_EXISTE_Y_ASOCIADA_TARJETA_MULTISERVICIOS = "PagosSubsidioMonetario.consultar.personaConTarjetaMultiservicios";
	
    public static final String CONSULTAR_PERSONA_EXISTE_Y_ESTADO_AFILIACION = "PagosSubsidioMonetario.consultar.PersonaExisteYEstadoAfiliacion";

    public static final String CONSULTAR_PERSONA_ADMINSUBSIDIO_GRUPOFAMILIAR = "PagosSubsidioMonetario.consultar.PersonaExpedicionValidacion";

	public static final String CONSULTAR_SALDO_TARJETA_GENESYS = "PagosSubsidioMonetario.consultar.saldoTarjetaGenesys";
	
	public static final String BLOQUEAR_TARJETA_ACTIVA = "PagosSubsidioMonetario.actualizar.bloquearTarjetaActiva";
	
	public static final String CONSULTAR_REGISTRO_ABONO_CUENTA_ADMIN_SUBSIDIO = "PagosSubsidioMonetario.consultar.RegistroAbonoCuentaAdministradorSubsidio";
	
	public static final String CONSULTAR_GRUPOS_TRABAJADOR_MEDIO_TARJETA = "PagosSubsidioMonetario.consultar.gruposFamiliaresTrabajadorConMedioDePagoTarjeta";
	
	public static final String CONSULTAR_REGISTRO_INCONSISTENCIAS_TARJETA = "PagosSubsidioMonetario.consultar.registroInconsistenciasTarjetas";
	
	public static final String BUSCAR_REGISTRO_INCONSISTENCIA_TARJETA_POR_ID = "PagosSubsidioMonetario.consultar.registroInconsistenciaTarjetaPorId";
	
	public static final String CONSULTAR_HISTORICO_REGISTRO_INCONSISTENCIAS_TARJETA = "PagosSubsidioMonetario.consultar.historicoInconsistenciasTarjeta";
	
	public static final String OBTENER_INFO_DETALLES_AGRUPADOS = "PagosSubsidioMonetario.consultar.InformacionDetallesSubsidioAgrupados";
	
    public static final String CONSULTAR_DETALLES_SUBSIDIO_ASIGNADO_ASOCIADOS_CUENTA_ADMIN_SUBSIDIO_ORDENADOS = "PagosSubsidioMonetario.DetalleSubsidioAsignadoDTO.asociado.cuentaAdministradorSubsidio.ordenadosPorFecha";
    
    public static final String CONSULTAR_TEMP_ARCHIVO_RETIRO_TERCERO_PAGADOR_EFECTIVO = "PagosSubsidioMonetario.buscarTempArchivoRetiroTerceroPagadorEfectivoDTO.porIDConvenio";
    
    /**
     * HU-31-199
     * Consulta que se encarga de traer todos los registro de la cuenta de administrador del subsidio que sean de tipo abono
     * con estado enviado y hayan sido pagados por el medio de pago Bancos (En el caso del modelo de datos seria por medio de pago
     * transferencia), para generar reporte de bonos
     */
    public static final String CONSULTAR_CUENTA_ADM_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO = "PagosSubsidioMonetario.buscarPor.tipoTransaccionAbono.estadoEnviado.MedioDePagoBancos";
    
    public static final String CONSULTAR_CANTIDAD_CUENTAS_POR_TRANSACCION_TERCERO_PAGADOR = "PagosSubsidioMonetario.consultar.cantidad.cuentas.por.transaccion.t.p";
    
    /**
     * HU-31-199
     * Consulta que se encarga de traer todos los registro de la cuenta de administrador del subsidio que sean de tipo abono
     * con estado enviado y hayan sido pagados por el medio de pago Bancos (En el caso del modelo de datos seria por medio de pago
     * transferencia) utilizando un parametro adicional de consulta
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_POR_TIPO_ABONO_ESTADO_ENVIADO_MEDIO_DE_PAGO_BANCO_CON_FILTRO = "PagosSubsidioMonetario.CuentaAdministradorSubsidioDTO.buscarPor.tipoTransaccionAbono.estadoEnviado.MedioDePagoBancosConFiltro";

    public static final String PROCEDURE_USP_PG_RESULTADOSDISPERSIONADMINISTRADORMEDIOPAGO = "USP_PG_ResultadosDispersionAdministradorMedioPago";
    
    public static final String CONSULTAR_SUMATORIA_ABONOS_BANCARIOS = "PagosSubsidioMonetario.sumatoria.total.abonos.bancarios";
    
    /**
     * Consulta que se encarga de traer todo los retiros con estado Solicitado
     * punto 3.1 Realizar la consulta de retiros asociados a abonos con estado "Solicitado" (mediante el botón "Consultar")
     * HU-31-415-Confirmación de valor entregado para transacciones de retiro
     */
    public static final String CONSULTAR_RETIROS_CON_ESTADO_SOLICITADO = "PagosSubsidioMonetario.consultar.retiros.estado.solicitado";

    public static final String PROCEDURE_USP_PG_MODIFICARCUENTAYDETALLEPORREVERSO = "USP_PG_ModificarCuentaYDetallePorReverso";
    
    /**
     * Consulta para formar el archivo que muestra los abonos dispersados de acuerdo a un tercero pagador
     * HU-31-227
     */
    public static final String CONSULTAR_CUOTAS_DIPERSADAS_POR_CONVENIO_TERCERO_PAGADOR = "PagosSubsidioMonetario.consultar.cuotas.dispersadas.por.convenioTerceroPagador";
    
    /**
     * Consulta para formar el archivo que muestra los abonos dispersados de acuerdo a un tercero pagador
     * HU-31-227
     */
    public static final String CONSULTAR_ENCABEZADO_CUOTAS_DISPERSADAS_POR_TERCERO_PAGADOR = "PagosSubsidioMonetario.consultar.encabezado.cuotas.dispersadas.por.terceroPagador";
    
    public static final String USP_PG_GET_CONSULTARTRANSACCIONESSUBSIDIO = "USP_PG_GET_ConsultaTransaccionesSubsidio";
    public static final String USP_PG_GET_CONSULTARDETALLESSUBSIDIO = "USP_PG_GET_ConsultaDetallesSubsidio";
    
    public static final String USP_PG_GET_CONSULTARTRANSDETALLESSUBSIDIO = "USP_PG_GET_ConsultaTransDetallesSubsidio";
    
    public static final String USP_PG_GET_CONSULTARLISTADOARCHIVOTRANSDETASUBSIDIO = "USP_SM_GET_ListadoArchivoTransDetaSubsidio";
    public static final String CONSULTAR_ARCHIVO_TRANS_DETALLE_SUBSIDIO_TODOS = "PagosSubsidioMonetario.consultar.archivoTransDetSubTodos";
    
    public static final String CONSULTAR_ARCHIVO_TRANS_DETALLE_SUBSIDIO_ESTADO = "PagosSubsidioMonetario.consultar.archivoTransDetSubEstado";
    
    public static final String USP_PG_GET_CONSULTATRANSACCIONESTODOSFILTROSSUBSIDIO = "USP_PG_GET_ConsultaTransaccionesTodosFiltrosSubsidio";
        
    /**
     * HU-31-201
     * Consulta que se encarga de buscar los registros de administradores de subsidios que cumplen con todos los filtros
     * señalados en la HU
     */
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA = "PagosSubsidioMonetario.consultarCuentaAdminSubsidioPaginada";
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA_COUNT = "PagosSubsidioMonetario.consultarCuentaAdminSubsidioPaginadaCount";
    public static final String CONSULTAR_CUENTA_ADMIN_SUBSIDIO_PAGINADA_COUNT2 = "PagosSubsidioMonetario.consultarCuentaAdminSubsidioPaginadaCount2";
    public static final String CONSULTAR_DETALLES_A_ANULAR_MANTIS_265820 = "PagosSubsidioMonetario.consultar.mantis265820";

    /**
     * HU-31-223 & HU-31-224
     * Consulta que trae todos los registros asociados a la cuenta, que serán anulados por fecha de vencimiento o por prescripción.
     */
    public static final String CONSULTAR_LISTADO_ABONOS_POR_VENCIMIENTO_PRESCRIPCION = "USP_SM_GET_ResumenListadoAbonosPorVencimientoYPrescripcion";
    
    /**
     * HU-31-223 & HU-31-224
     * Consulta que trae el resumen de todos los registros asociados a la cuenta, que serán anulados por fecha de vencimiento o por prescripción.
     */
    public static final String CONSULTAR_LISTADO_ABONOS_POR_VENCIMIENTO_PRESCRIPCION_RESUMEN = "USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcionResumen";
    
    /**
    * Consulta los Descuentos asociados a un detalle de subsidios
    */
    
    public static final String CONSULTAR_DESCUENTOS_SUBSIDIO_DETALLE = "PagosSubsidioMonetario.consultar.descuentosSubsidio";

    /**
     * Consulta los registros de solicitud de dispersion de subsidio monetario en RegistroSolicitudAnibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_DISPERSION_SUBSIDIO_MONETARIO = "PagosSubsidioMonetario.buscar.registro.solicitud.dispersion.subsidio.monetario";

    /**
     * Consulta los registros de solicitud de anulacion de subsidio monetario en RegistroSolicitudAnibol
     */
    public static final String CONSULTAR_REGISTRO_SOLICITUD_ANULACION_SUBSIDIO_MONETARIO = "PagosSubsidioMonetario.buscar.registro.solicitud.anulacion.subsidio.monetario";

    /**
     * Actualiza el estado de las transacciones de la dispersion de subsidio monetario que se procesaron correctamente
     */
    public static final String ACTUALIZAR_ESTADO_TRANSACCIONES_PROCESADAS_DISPERSION_SUBSIDIO_MONETARIO = "PagosSubsidioMonetario.actualizar.estado.transacciones.procesadas.dispersion.subsidio.monetario";

    /**
     * Consulta que obtiene el identificador de la cuenta del administrador subsidio para la dispersion del subsidio monetario
     */
    public static final String BUSCAR_CUENTA_ADMIN_SUBSIDIO_DISPERSION_SUBSIDIO_MONETARIO = "PagosSubsidioMonetario.buscar.cuentaAdminSubsidio.dispersion.subsidio.monetario";

     /**
     * Consulta que obtiene el email de un administrador de subsidio que es o será objeto de una prescripción de abonos de subsidio monetario
     */
    public static final String BUSCAR_EMAIL_ADMIN_SUBSIDIO_MONETARIO_PRESCRIPCION = "PagosSubsidioMonetario.buscar.correo.administrador.prescripcion";

    /**
     * Consutla que obtiene los Ids de las cuentas (abonos) del administrador de subsidio que hacen parte del primer aviso para la prescripción del subsidio monetario
     */
    public static final String BUSCAR_CUENTA_ADMIN_AVISO_PRESCRIPCION_PAGOS = "PagosSubsidioMonetario.buscar.cuentas.administrador.avisos.prescripcion";
    
    public static final String USP_PG_CONFIRMAR_ABONOS_MEDIO_PAGO_BANCOS_ARCHIVO = "USP_PG_ConfirmarAbonosMedioPagoBancosArchivo";

    public static final String CONSULTAR_EXISTENCIA_CUENTA_ADMIN_SUBDIO_POR_ID_TRANSACCION_TERCERO_PAGADOR = "PagosSubsidioMonetario.buscar.existenicia.cuenta.admin.subsidio.por.id.transaccion.tercero.pagador";

    public static final String CONSULTAR_USUARIO_TERCERO = "PagosSubsidioMonetario.buscar.usuario";

    public static final String CONSULTAR_LISTADO_TRANSACCIONES_RETIRO = "PagosSubsidioMonetario.buscar.listado.valor.real.transaccion";

    public static final String CONSULTAR_LISTADO_ABONOS_RETIRO = "PagosSubsidioMonetario.buscar.listado.abonos.retiro";

    public static final String CONSULTAR_DETALLES_SUBSIDIO_ADMIN = "PagosSubsidioMonetario.buscar.detalles.subsidio";

    public static final String CONSULTAR_CUENTA_SUBSIDIO_ADMIN = "PagosSubsidioMonetario.buscar.cuenta.administrador.subsidio";

    public static final String VALIDAR_EXISTENCIA_TARJETA_POR_NUMERO = "PagosSubsidioMonetario.validar.existencia.tarjeta";

    public static final String CONSULTAR_GRUPOS_FAMILIARES_CON_MARCA_Y_ADMIN = "PagosSubsidioMonetario.consultar.grupos.familiares.con.marca.y.admin";

    public static final String CONSULTAR_ESTADO_DISPERCION = "PagosSubsidioMonetario.consultar.estado.dispercion";
    
    public static final String CONSULTAR_MEDIO_Y_GRUPOS_PARA_TRASLADO = "USP_GET_ConsultarTrasladoDeSaldos";

    public static final String CONSULTAR_INFO_MEDIO_TARJETA_TRASLADO = "PagosSubsidioMonetario.consultar.medio.tarjeta.traslado";

    public static final String CONSULTAR_INFO_MEDIO_TRANSFERENCIA_TRASLADO = "PagosSubsidioMonetario.consultar.medio.transferencia.traslado";

    public static final String CONSULTAR_INFO_MEDIO_EFECTIVO_TRASLADO = "PagosSubsidioMonetario.consultar.medio.efectivo.traslado";

    public static final String CONSULTAR_CUENTAS_ADMINISTRADOR_TRASLADO = "PagosSubsidioMonetario.consultar.cuentas.administrador.traslado";

    public static final String CONSULTAR_DETALLES_CUENTA_TRASLADO = "PagosSubsidioMonetario.consultar.detalles.cuentas.administrador.traslado";

    public static final String CONSULTAR_MEDIOPAGO_TRASLADO = "PagosSubsidioMonetario.consultar.medioPago.traslado";
    
    public static final String GUARDAR_JSON_RESPUESTA_CONSULTA_PROCESO_ANIBOL = "PagosSubsidioMonetario.guardarJsonRespuestaAnibolTraslado";

    public static final String CONSULTAR_PERSONA_ADMIN_TRASLADO = "PagosSubsidioMonetario.consultar.persona.admin.traslado";

    public static final String CONSULTAR_REGISTRO_ANIBOL_TRASLADO = "PagosSubsidioMonetario.consultar.solicitudes.en.proceso.anibol.traslado.saldos";

    public static final String CONSULTAR_CUENTA_ADMINISTRADOR_TRASLADO = "PagosSubsidioMonetario.consultar.cuenta.administrador.traslado";

    public static final String CONSULTAR_DETALLES_RETOMA_TRASLADO = "PagosSubsidioMonetario.consultar.detalles.cuenta.retoma.traslado";

    public static final String CONSULTAR_BANDEJA_ACTUALIZAR = "PagosSubsidioMonetario.consultar.bandeja.Actualizar";

    public static final String CONSULTAR_DATOS_ADMIN_REGISTRO_BANDEJA = "PagosSubsidioMonetario.consultar.datos.admin.registro.bandeja";

    public static final String CONSULTAR_BANDEJA_TRANSACCIONES = "PagosSubsidioMonetario.consultar.bandeja.transacciones";

    public static final String CONSULTAR_ULTIMA_SOLICITUD = "PagosSubsidioMonetario.consultar.ultima.solicitud";

    public static final String CONSULTAR_ULTIMA_SOLICITUD_DOCUMENTO = "PagosSubsidioMonetario.consultar.ultima.solicitud.documento";
    
    public static final String CONSULTAR_BANDEJA_TRANSACCIONES_POR_PERSONA = "PagosSubsidioMonetario.consultar.bandeja.por.persona";

    public static final String CONSULTAR_CUENTA_REESTABLECER = "PagosSubsidioMonetario.consultar.cuenta.reestablecer";

    public static final String CONSULTAR_PERSONAS_BANDEJA_TRANSACCIONES = "PagosSubsidioMonetario.consultar.personas.bandeja";

    public static final String CONSULTAR_DETALLE_BANDEJA_TRANSACCIONES = "PagosSubsidioMonetario.consultar.detalle.bandeja";

    public static final String CONSULTAR_MEDIO_DE_PAGO_DETALLE_BANDEJA = "PagosSubsidioMonetario.consultar.medio.pagp.detalle.bandeja";

    public static final String CONSULTAR_ID_MEDIO_TARJETA = "PagosSubsidioMonetario.consultar.id.medio.pago.tarjeta";

    public static final String CONSULTAR_GESTION_TRANSACCIONES = "PagosSubsidioMonetario.consultar.gestion.transacciones";

    public static final String CONSULTAR_BANDEJA_TRANSACCIONES_ANIBOL = "PagosSubsidioMonetario.consultar.bandeja.transacciones.anibol";

    public static final String CONSULTAR_DATOS_BANCO = "PagosSubsidioMonetario.consultar.datos.banco.traslado";

    public static final String CONSULTAR_DATOS_TARJETA = "PagosSubsidioMonetario.consultar.datos.tarjeta.traslado";

    public static final String CONSULTAR_MEDIOS_DE_PAGO_TRASLADO_ADMINISTRADOR_SUBSIDIO = "PagosSubsidioMonetario.buscar.mediosDePagos.traslados.adminSubsidio";
    // finaliza 80008

    public static final String CONSULTAR_PARAMETROS_OUT_REGISTRO_OPERACION = "PagosSubsidioMonetario.consultar.parametros.out.registro.operacion";

    public static final String CONSULTAR_LISTADO_TRANSACCIONES_RETIRO_INTERMEDIO = "PagosSubsidioMonetario.buscar.listado.valor.real.transaccion.intermedio";

    public static final String ACTUALIZAR_LISTA_CUENTASADMIN = "PagosSubsidioMonetario.actualizar.lista.cuentaAdministradorSubsidio";

    public static final String ACTUALIZAR_LISTA_CUENTASADMIN_ESTADOOPERACION = "PagosSubsidioMonetario.actualizar.lista.cuentaAdministradorSubsidio.estadoOperacion";
    public static final String CONSULTAR_TRANSACCION_EN_PROCESO = "PagosSubsidioMonetario.transacciones.en.proceso";
    public static final String CONSULTAR_ABONOS_RELACIONADOS_RETIRO = "PagosSubsidioMonetario.buscar.abonos.relacionados.retiro";

    public static final String REGISTRAR_RETIRO = "PagosSubsidioMonetario.registrar.retiro.SP";

    public static final String CONSULTAR_LISTADO_TRANSACCIONES_RETIRO_INTERMEDIOS = "PagosSubsidioMonetario.buscar.Abonos.retiro.intermedio";
}
