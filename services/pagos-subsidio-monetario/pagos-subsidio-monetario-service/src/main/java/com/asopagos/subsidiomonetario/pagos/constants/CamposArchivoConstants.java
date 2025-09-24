package com.asopagos.subsidiomonetario.pagos.constants;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes para la validación del archivo de la solicitud del convenio del tercer pagador en el proceso de pagos<br/>
 * <b>Módulo:</b> Asopagos - HU -31 - 205 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
public class CamposArchivoConstants {
	
	/**
	 * Campos validadores - Archivo de concialiación de retiros por tercero pagador HU-31-205
	 */
	public static final String IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR = "identificacionTransaccionTerceroPagador";
	public static final String TIPO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO = "tipoIdentificacionAdministradorSubsidio";
	public static final String NUMERO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO = "numeroIdentificacionAdministradorSubsidio";
	public static final String VALOR_REAL_TRANSACCION = "valorRealTransaccion";
	public static final String FECHA_TRANSACCION = "fechaTransaccion";
	public static final String HORA_TRANSACCION = "horaTransaccion";
	public static final String DEPARTAMENTO = "departamento";
	public static final String MUNICIPIO = "municipio";
	public static final String TIPO_SUBSIDIO = "tipoSubsidio";
	
	// nombre de la variable que se espera tener en el contexto
    public static final String LISTA_DEPARTAMENTO = "listaDepartamentos";
    public static final String LISTA_MUNICIPIO = "listaMunicipios";
    public static final String LISTA_HALLAZGOS = "listaHallazgos";
    public static final String LISTA_CANDIDATOS = "listaCandidatos";
    public static final String LISTA_REVERSOS = "listaReversos";
    public static final String LISTA_OTRAS_TRANSACCIONES = "listaOtrasTransacciones";
    public static final String LISTA_ERRORES_HALLAZGOS = "listaErroresHallazgos";
    public static final String LISTA_CAMPOS_ERRORES_POR_LINEA = "listaCamposErroresPorLinea";
    public static final String TOTAL_REGISTRO = "totalRegistro";
    public static final String TOTAL_REGISTRO_ERRORES = "totalRegistroErrores";
    public static final String TOTAL_REGISTRO_VALIDO="totalRegistroValido";
    public static final String TOTAL_REGISTRO_OTRAS_TRANSACCIONES = "totalRegistroOtrasTransacciones";
	
	/**
	 * Campos validadores  - Archivo de consumo retiros tarjeta entregados por ANIBOL
	 */
    public static final String DESCRIPCION_TRANSACCION = "descripcionTransaccion";
    public static final String DESCRIPCION_RESPUESTA = "descripcionRespuesta";

    public static final String IDENTIFICADOR_CAJA_COMPENSACION = "identificadorCajaCompensacion";
    public static final String NUMERO_TARJETA = "numeroTarjeta";
    public static final String NIT_EMPRESA = "nitEmpresa";
    public static final String CUENTA_RELACIONADA = "cuentaRelacionada";
    public static final String DISPOSITIVO_ORIGEN = "dispositivoOrigen";
    public static final String DESCRIPCION_ESTADO_COBRO = "descripcionEstadoCobro";
    public static final String VALOR_TRANSACCION = "valorTransaccion";
    public static final String VALOR_DISPENSADO = "valorDispensado";
    public static final String VALOR_A_COBRAR = "valorCobro";
    public static final String VALOR_IMPUESTOS = "valorImpuestos";
    public static final String TOTAL_A_COBRAR = "totalCobro";
    public static final String IMPUESTO_EMERGENCIA_ECONOMICA = "impuestoEmergenciaEconomica";
    public static final String INDICADOR_REVERSO = "indicadorReverso";
    public static final String RESPUESTA_AUTORIZADOR = "respuestaAutorizador";
    public static final String CODIGO_AUTORIZADOR = "codigoAutorizacion";
    public static final String SUBTIPO = "subtipo";
    public static final String FECHA_AUTORIZADOR = "fechaAutorizador";
    public static final String HORA_AUTORIZADOR = "horaAutorizador";
    public static final String HORA_DISPOSITIVO = "horaDispositivo";
    public static final String NUMERO_REFERENCIA = "numeroReferencia";
    public static final String RED = "red";
    public static final String NUMERO_DISPOSITIVO ="numeroDispositivo";
    public static final String CODIGO_ESTABLECIMIENTO = "codigoEstablecimiento";
    public static final String CODIGO_CUENTA = "codigoCuenta";
    
}
