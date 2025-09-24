package com.asopagos.pila.enumeraciones;

import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> Clase que maneja los mensajes de validacion del los
 * campos del archivo <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jpiraban@heinsohn.com.co"> Jhon Angel Piraban
 *         Castellanos</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson Andrés Arboleda
 *         Valencia</a>
 */

public enum MensajesValidacionEnum {
    ERROR_ESTRUCTURA_ARCHIVO(0, "¬¬{0}¬¬Error en la estructura del archivo: {1}"),
    ERROR_IDENTIFICADOR_INDICE_PLANILLA(1,"¬¬{0}¬¬{1}¬¬{2}¬¬No se cuenta con el identificador del archivo en el índice de planillas"), 
    ERROR_PERSISTENCIA_REGISTRO(2, "¬¬{0}¬¬Se ha presentado un error al momento de persistir el registro - {1}"), 
    ERROR_CAMPO(3, "¬¬{0}¬¬Error en el campo {1} - no contiene valor"), 
    ERROR_BANCO_AUTORIZADOR(4,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no cumple con la restricción de valor para sus {5} caracteres "), 
    ERROR_SUMATORIA_CONTEO_CAMPOS(5,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no concuerda con la sumatoria o conteo del campo correspondiente ({5})."), 
    ERROR_FORMATO_INCORRECTO(6,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4},no corresponde a un formato válido."), 
    ERROR_FALTA_PARAMETROS(7,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser validado por dato no leído a consecuencia de error en estructura de la línea actual o líneas anteriores ({4})"), 
    ERROR_SUMATORIA_APORTES(8,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no coincide con la sumatoria de aportes obligatorios realizada en los registros tipo 2 ({5})."), 
    ERROR_CUENTA_PENSIONADOS_TIPO(9,"¬¬{0}¬¬{1}¬¬{2}¬¬{3}¬¬El campo {4} con valor {5}, no coincide con la cuenta de pensionados en el registro tipo 2 ({6})"), 
    ERROR_CLASIFICACION_ARCHIVO_DETALLE(10,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no concuerda con la clasificación empleada en el archivo de detalle para el cálculo y validación de los días de mora ({5})."), 
    ERROR_CLASE_TIPO_APORTANTE(11,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, Un aportante tipo {5}, no puede ser clase {4}"), 
    ERROR_TIPO_PLANILLA(12,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no es válido para una planilla de tipo {5}"), 
    ERROR_VALOR_SECUENCIA(13,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {1}, no es válido para el valor de secuencia 1."), 
    ERROR_VALOR_CORRECCION(14,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no es válido para una planilla de corrección."), 
    ERROR_DEPARTAMENTO_MUNICIPIO_EXTRANJERO(15,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no es válido al haber marcado al cotizante como \"Extranjero no obligado a cotizar a pensiones."),
    ERROR_DEPARTAMENTO_MUNICIPIO_EXTRANJERO_1(16,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no se encuentra diligenciado para un cotizante no marcado como \"Extranjero no obligado a cotizar a pensiones\"."), 
    ERROR_DEPARTAMENTO_MUNICIPIO(17,"¬¬{0}¬¬{1}@@{2}¬¬{3}¬¬Se encuentran los ID de municipio {1} y de departamento {2}, "+ "los cuales no presentan relación entre sí. "), 
    ERROR_CLASE_PAGADOR_PENSIONES(18,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no fue posible establecer una clase de pagador de pensiones que concuerde con la cantidad de días de mora indicados por la planilla."), 
    ERROR_CANTIDAD_DIAS_MORA(19,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} , con valor {4}, no concuerda con la cantidad de días de morosidad calculados a partir de la normatividad correspondiente ({5})"), 
    ERROR_REQUIERE_DIGITO_VERIFICACION(20,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} , con valor {4}, requiere de un dígito de verificación."), 
    ERROR_NO_REQUIERE_DIGITO_VERIFICACION(21,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} , con valor {4}, debe tener dígito de verificación con valor cero(0)."), 
    ERROR_REQUIERE_FECHA_PAGO(22,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} sin valor.  Este tipo de planilla requiere que se especifique una fecha de pago de planilla asociada."), 
    ERROR_NO_REQUIERE_FECHA_PAGO(23,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} , con valor {1}.  Este tipo de planilla no acepta una fecha de pago de planilla asociada."),
    ERROR_NOMBRE_ARCHIVO_GENERAL(24,"¬¬{0}¬¬{1}¬¬{2}¬¬Se han presentado el/los siguientes errores en el nombre de archivo \"{3}\": "), 
    ERROR_NOMBRE_ARCHIVO_NO_CUMPLE_FORMATO(25,"¬¬{0}¬¬{1}¬¬{2}¬¬El archivo {1}, no cumple con el formato de nombre establecido."), 
    ERROR_NOMBRE_ARCHIVO_FORMATO_FECHA_NO_VALIDO(26,"¬¬{0}¬¬{1}¬¬{2}¬¬La fecha del pago no presenta el formato de fecha correcto."), 
    ERROR_NOMBRE_ARCHIVO_FECHA_PAGO_NO_VALIDA(27,"¬¬{0}¬¬{1}¬¬{2}¬¬La fecha del pago no es válida."), 
    ERROR_NOMBRE_ARCHIVO_CODIGO_ENTIDAD_SUPERA_CARACTERES_DEFINIDOS(28,"¬¬{0}¬¬{1}¬¬{2}¬¬El código de la entidad supera a los 6 caracteres definidos. {3}"), 
    ERROR_NOMBRE_ARCHIVO_TIPO_ARCHIVO(29,"¬¬{0}¬¬{1}¬¬{2}¬¬El tipo de archivo no se encuentra entre los valores admitidos ({3})."), 
    ERROR_NOMBRE_ARCHIVO_TIPO_DOCUMENTO_INVALIDO(30,"¬¬{0}¬¬{1}¬¬{2}¬¬El tipo de documento de identidad del aportante no se encuentra entre los valores admitidos."), 
    ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_INVALIDO(31,"¬¬{0}¬¬{1}¬¬{2}¬¬El código de la Entidad Administradora no cumple con el requisito de caracteres en mayúscula sostenida."), 
    ERROR_NOMBRE_ARCHIVO_CODIGO_OPERADOR_INFORMACION_INVALIDO(32,"¬¬{0}¬¬{1}¬¬{2}¬¬El código de operador de información no se encuentra entre los valores admitidos."), 
    ERROR_NOMBRE_ARCHIVO_FORMATO_PERIODO_APORTE(33,"¬¬{0}¬¬{1}¬¬{2}¬¬El período de aporte no presenta el formato correcto."), 
    ERROR_NOMBRE_ARCHIVO_VALOR_PERIODO_APORTE(34,"¬¬{0}¬¬{1}¬¬{2}¬¬El período de aporte no es válido."), 
    ERROR_NOMBRE_ARCHIVO_CARACTERES_MINUSCULA(35,"¬¬{0}¬¬{1}¬¬{2}¬¬El nombre de archivo presenta caracteres alfanuméricos que no están en mayúscula."),
    ERROR_NOMBRE_ARCHIVO_REGISTRADO_EN_SISTEMA_SIN_ANULAR(36,"¬¬{0}¬¬{1}¬¬{2}¬¬El archivo {1}, ya se encuentra el archivo registrado en el sistema sin anular."), 
    ERROR_TIPO_PLANILLA_PILA(37,"¬¬{0}¬¬No se encuentra {1} con el tipo y número de identificación de aportante definidos en la planilla PILA."), 
    ERROR_CAMPO_NO_COINCIDE_CON_VALOR_OF(38,"¬¬{0}¬¬{1}:-:{5}¬¬{2}¬¬El campo {3} con valor {4}, no coincide con el valor reportado por el Operador financiero ({5})."), 
    ERROR_CAMPO_NO_SE_ENCUENTRA_INFORMACION_RECAUDO_EN_OF(39,"¬¬{0}¬¬No se encuentra información de recaudo de aporte de la planilla {1} en la información del Operador Financiero"), 
    ERROR_SIN_DATOS_BUSQUEDA_PLANILLA_OF(40,"¬¬{0}¬¬No se encuentran los datos requeridos para la búsqueda de la planilla en la información del Operador Financiero"), 
    ERROR_SIN_INFORMACION_BASE_ARCHIVO_DETALLE_APORTE(41,"¬¬{0}¬¬No se cuenta con la información base de la lectura del archivo de detalle de aporte."), 
    ERROR_SIN_ACCESO_A_INFO_BD(42,"¬¬{0}¬¬No se cuenta con acceso a la información en la base de datos."),
    ERROR_NOMBRE_ARCHIVO_MODALIDAD_PLANILLA(43,"¬¬{0}¬¬{1}¬¬{2}¬¬El valor de la modalidad de la planilla no es válido"),
    ERROR_COMPARACION_CONTEXTO(44,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no coincide con su contraparte en {5} ({6})"), 
    ERROR_COMPARACION_CONTEXTO_REFERENCIA(45,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}. No se cuenta con valor de referencia para comparar al campo"), 
    ERROR_CAMPO_NO_EVALUADO_NO_ES_TIPO_FECHA(46,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no puede ser evaluado debido a que se ha recibido una referencia que no es del tipo fecha."), 
    ERROR_CAMPO_POSTERIOR_A_FECHA_PAGO_PLANILLA(47,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, es posterior a la fecha de pago de la planilla ({5})."), 
    ERROR_CAMPO_ES_ANTERIOR_A_FECHA_MINIMA_DEFINIDA(48,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, es anterior a la fecha mínima definida ({5})."), 
    ERROR_CAMPO_NO_ES_TIPO_FECHA(49,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no puede ser evaluado debido a que no es del tipo fecha."),
    ERROR_FECHA_PAGO_APORTE_ANTERIOR_A_FECHA_ACTUAL(51,"¬¬{0}¬¬{1}¬¬{2}¬¬La fecha de pago del aporte {3} es anterior a la fecha actual menos {4} días hábiles ({5})."), 
    ERROR_FECHA_PAGO_APORTE_POSTERIOR_A_FECHA_ACTUAL(52,"¬¬{0}¬¬{1}¬¬{2}¬¬La fecha de pago del aporte {3} es posterior a la fecha actual más un día hábil ({4})."), 
    ERROR_AL_COMPARAR_FECHA_PAGO_PLANILLA(53,"¬¬{0}¬¬{1}¬¬{2}¬¬Se ha presentado un problema al comparar la fecha de pago de la planilla. {3}"), 
    ERROR_CAMPO_FECHA_NOVEDAD_SIN_HABERSE_MARCADO(54,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta una fecha de inicio o fin de novedad sin haber marcado la misma."), 
    ERROR_CAMPO_NO_PRESENTA_FECHA_NOVEDAD_HABIENDOSE_MARCADO(55,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no presenta una fecha de inicio o fin de novedad habiendo marcado la misma."), 
    ERROR_CAMPO_PRESENTA_FECHA_FIN_NOVEDAD_SIN_FECHA_INICIO(56,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta una fecha de fin de novedad sin contar con una fecha de inicio."), 
    ERROR_CAMPO_SIN_VERIFICAR_DEBIDO_A_CONVERSION_FECHAS(57,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no pudo ser verificado debido a un error en la conversión de fechas.\n {4}"), 
    ERROR_FECHA_INICIO_NOVEDAD_POSTERIOR_A_FECHA_FINALIZACION_NOVEDAD(58,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta una fecha de inicio de novedad ({4}) posterior a la fecha de finalización de la misma ({5})."), 
    ERROR_PERIODO_PAGO_APORTE_ANTERIOR_A_PERIODO_MINIMO(59,"¬¬{0}¬¬{1}¬¬{2}¬¬El período de pago del aporte {3} es anterior al período mínimo posible ({4})."),
    ERROR_CAMPO_NO_ES_VALIDO(61,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no es válido."), 
    ERROR_CAMPO_SALARIO_INFERIOR_A_SMLMV_PARA_PERIODO(62,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, es inferior al valor de 1 SMLMV para el período aportado ({5})."),
    ERROR_CAMPO_TIPO_DOCUMENTO_NO_VALIDO_PARA_TIPO_PERSONA(64,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no es válido para una persona tipo {5}"), 
    ERROR_CAMPO_NO_VALIDO_SIN_HABERSE_MARCADO_NOVEDAD(65,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no es válido sin haberse marcado una novedad del tipo ING, RET, SLN, LMA o IRL."), 
    ERROR_CAMPO_VALOR_IBC_REPORTADO_30_DIAS_PARA_COTIZANTE(66,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, se encuentra reportado por 30 días cotizados para un cotizante tipo {5}."), 
    ERROR_CAMPO_VALOR_IBC_NO_CORRESPONDE_AL_VALOR_DE_DIAS_COTIZADOS(67,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no corresponde al valor esperado respecto a los días cotizados ({5})."), 
    ERROR_VALOR_IBC_SUPERA_AL_70PTO_EN_SALARIO_INTEGRAL_SIN_NOVEDADES(68,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, supera al 70% en un salario integral sin novedades."), 
    ERROR_VALOR_IBC_DIFERENTE_A_SALARIO_BASICO_SIN_SALARIO_INTEGRAL_NI_NOVEDADES(69,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, es diferente al salario básico sin salario integral y sin novedades marcadas."),
    ERROR_CAMPO_MARCA_SALARIO_INTEGRAL_NO_CUMPLE_VALOR_MIN_ESTABLECIDO(71,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} marca al salario como integral ({4}) el cual no cumple con el valor mínimo establecido ({5})."),
    ERROR_CAMPO_ULTIMO_REGISTRO_TIPO_2_DEBE_SER_C(73,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4} en el último registro tipo 2, debe tener valor C."), 
    ERROR_CUENTA_REGISTROS_TIPO_2(74,"¬¬{0}¬¬{1}¬¬{2}¬¬{3}¬¬Campo {4} con valor {1}. No coincide con la cuenta de registros de tipo 2 realizada ({5})."), 
    ERROR_ULTIMA_SECUENCIA_2(75,"¬¬{0}¬¬{1}¬¬{2}¬¬Campo {3} con valor {1}.  No coincide con el último valor de Campo 1: Secuencia en Registro tipo 2 ({4})."),
    ERROR_CAMPO_NUMERO_IDENTIFICACION_ALFANUMERICO(77,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se puede verificar a causa de número de identificación alfanumérico."), 
    ERROR_CAMPO_NO_COINCIDE_CON_CALCULO_DIGITO(78,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no coincide con el cálculo de dígito realizado ({5})."),
    ERROR_CAMPO_TIPO_COTIZANTE_EXIGE_CANTIDAD_HORAS_LABORADAS(80,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, se encuentra vacío para un tipo de cotizante que exige una cantidad de horas laboradas ({4})."),
    ERROR_CAMPO_HORAS_COTIZADAS_NEGATIVAS(81, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta un valor de horas negativo ({1})"), 
    ERROR_CAMPO_NO_CUMPLE_REGLA_REDONDEO(82,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, presenta un valor que no cumple con la regla de redondeo definida ({5})."), 
    ERROR_CAMPO_TIPO_COTIZANTE_NO_ADMITE_VALOR_CERO(83,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, presenta valor 0 con un tipo de cotizante que no permite tal valor ({5})."), 
    ERROR_CAMPO_NO_COINCIDE_CON_SUMATORIA_IBC_REGISTROS_TIPO_2(84,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no coincide con la sumatoria de IBC realizada en los registros tipo 2 ({5})."), 
    ERROR_CAMPO_NO_COINCIDE_CON_SUMATORIA_MESADAS_PENSIONALES_REGISTROS_TIPO_2(85,"¬¬{0}¬¬{1}¬¬{2}¬¬{3}¬¬El campo {4} con valor : {5}, no coincide con la sumatoria de mesadas pensionales realizada en los registros tipo 2 ({6})."), 
    ERROR_CAMPO_NO_VALIDO_PARA_TIPO_APORTANTE(86,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no es válido para un tipo de aportante {5}"),
    ERROR_CAMPO_VALOR_DEBE_SER_NUMERICO(88,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}. El valor del campo debe ser numérico."), 
    ERROR_CAMPO_DEBE_SER_NUMERICO(89,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}. El valor del campo debe ser numérico."), 
    ERROR_CAMPO_NO_PUEDE_SER_COMPARADO_PARA_COTIZANTE(90,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no puede ser comparado para el tipo de cotizante {5}."), 
    ERROR_CAMPO_FECHA_NOVEDAD_NO_CORRESPONDE_CON_PERIODO_PAGO_PLANILLA(91,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {1}. La fecha de la novedad no corresponde con el período de pago de la planilla ({4})."), 
    ERROR_CAMPO_NOVEDAD_NO_VALIDA_PARA_TIPO_COTIZANTE(92,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no es una novedad tipo {5} válida para un tipo de cotizante {6}."), 
    ERROR_CAMPO_FECHA_ES_ANTERIOR_AL_INICIO_LEY_1429(93,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, es anterior al inicio de la vigencia de la Ley 1429 de 2010 (2010-12-29)"), 
    ERROR_CAMPO_FECHA_ES_POSTERIOR_AL_FIN_LEY_1429(94,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, es posterior al fin de la vigencia de la Ley 1429 de 2010 (2014-12-31)"), 
    ERROR_CAMPO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA(95,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no se encuentra diligenciado con un tipo de planilla que exige que se defina una planilla asociada ({4})."), 
    ERROR_CAMPO_NO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA(96,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} se encuentra diligenciado con un tipo de planilla que exige que no se defina una planilla asociada ({4})."), 
    ERROR_CAMPO_LECTURA_INFORMACION_PLANILLA_ASOCIADA(97,"¬¬{0}¬¬{1}¬¬{2}¬¬Error en el campo {3}. Fallo en la lectura de la información de planilla asociada y tipo de planilla."), 
    ERROR_CAMPO_PRESENTA_VALOR_CERO_PARA_TIPO_ARCHIVO_DISTINTO_A_IR(98,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta un valor de 0 para un tipo de archivo diferente a IR"), 
    ERROR_CAMPO_SOLO_CUENTA_CON_UN_REGISTRO_TIPO_DOS_PARA_TIPO_PLANILLA_N(99,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, indica que sólo cuenta con un registro tipo 2 en un tipo de planilla N"), 
    ERROR_NO_SE_PUEDE_PREPARAR_INFO_PARA_BLOQUE_3_CANTIDAD_CAMPOS_NO_VALIDA(100,"¬¬{0}¬¬No es posible preparar la información para el bloque 3. la cantidad de campos solicitada no es válida"), 
    ERROR_CAMPO_AL_TRATAR_DE_DEFINIR_TIPO_REGISTRO_Y_NUMERO_CAMPO(101,"¬¬{0}¬¬No es posible preparar la información para el bloque 3. Se encontró un error al tratar de definir el tipo de registro y número de campo ({3})."), 
    ERROR_CAMPO_FECHA_MATRICULA_POSTERIOR_PERIODO_PAGO_PLANILLA(102,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {4}, presenta una fecha de matrícula ({5}) que es posterior al período de pago de la planilla ({6})."),
    ERROR_CAMPO_NO_VALIDO_EN_PLANILLA_COTIZANTES_TARIFA_4PCTO_O_0PCTO(104,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {4}, no es valido en planilla con cotizantes tarifa 0% sin novedad soporte."),

    // Validador Tarifa
    ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE_EN_SU_ANIO_BENEFICIO(105,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {1}, no es válido para un aportante clase {4} en su año de beneficio {5}."), 
    ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE(106,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {1}, no es válido para un aportante clase {4} y cotizante tipo {5}."), 
    ERROR_CAMPO_NO_VALIDO_PARA_ARCHIVO_SUPERA_MESADA_1_5_SMLMV(107,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no es válido en un archivo tipo {5} con una mesada pensional mayor a 1.5 SMLMV ($ {6})."),

    // Validador tipo cotizante
    ERROR_CAMPO_NO_ESTA_EN_TIPOS_COTIZANTE_EN_RESOLUCION(109,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra entre los tipos de cotizante contemplados en la resolución"), 
    ERROR_CAMPO_NO_PERMITIDO_PARA_TIPO_APORTANTE(110,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra permitido para el tipo de aportante {5}"),

    // Validador tipo cotizante, clase aportante
    ERROR_CAMPO_NO_PERMITIDO_PARA_CLASE_DE_APORTANTE(112,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra permitido para la clase de aportante {5}"),

    // Validador tipo cotizante planilla
    ERROR_CAMPO_NO_PERMITIDO_PARA_TIPO_PLANILLA(113,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra permitido para el tipo de planilla {5}"),

    // Validador tipo cotizante subsistema
    ERROR_CAMPO_NO_PERMITIDO_PARA_REPORTE_SUBSISTEMA_CCF(114,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra permitido para reporte al subsistema de CCF"),

    // Validador tipo documento extranjero
    ERROR_CAMPO_UNICO_VALOR_VALIDO_X_O_VACIO(115,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4}. El único valor válido es X o vacío."), 
    ERROR_CAMPO_TIPO_DOCUMENTO_NO_COMPATIBLE_CON_MARCACION(116,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4}. El tipo de documento del cotizante {5}, no es compatible con la marcación realizada."),

    // Validador tipos planilla aportante
    ERROR_CAMPO_TIPO_PLANILLA_NO_VALIDA_PARA_TIPO_APORTANTE(118,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4} con un tipo de aportante {5}. Este tipo de planilla no es válida para un aportante de este tipo."),

    // Validador tipo subtipo cotizante
    ERROR_SE_REQUIERE_SUBTIPO_COTIZANTE(121,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor tipo de cotizante {4}, requiere de un subtipo de cotizante."), 
    ERROR_CAMPO_NO_VALIDO_PARA_TIPO_COTIZANTE(122,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4}, no es válido para el tipo de cotizante {5}"),

    // Validador aporte obligatorio
    ERROR_CAMPO_VALOR_DISTINTO_AL_ESPERADO_DE_ACUERDO_AL_IBC(123,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, presenta un valor diferente al esperado de acuerdo al IBC ({5}) y la tarifa ({6} %) definida ({7})."),

    // Validador valor mora
    ERROR_CAMPO_PRESENTA_DIFERENCIA_RESPECTO_A_VALOR_CALCULADO_PLANILLA(126,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {1}, presenta una diferencia respecto al valor calculado para la planilla ({4})."), 
    ERROR_CAMPO_SIN_VALIDAR_FALLO_EN_RANGO_PERIODOS(127,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser validado debido error en la consulta del rango de períodos de tasa de interés."),

    // Validador total aporte
    ERROR_CAMPO_NO_COINCIDE_CON_VALOR_APORTE_MAS_MORA(128,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, no coincide con la suma del valor del aporte más el valor de mora ({5})."),

    // Validador fecha maxima b4
    ERROR_CAMPO_DIFERENTE_FECHA_ACTUAL(129,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, es diferente a la fecha actual {5} días hábiles ({6})."),

    // Validador Fecha Pago Max Min Campo
    ERROR_CAMPO_ES_ANTERIOR_A_FECHA_ACTUAL_MENOS_DIAS_HABILES(129,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4} es anterior a la fecha actual menos {5} días hábiles ({6})."), 
    ERROR_CAMPO_ES_POSTERIOR_A_FECHA_ACTUAL_MAS_DIAS_HABILES(130,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4} es posterior a la fecha actual más {5} días hábiles ({6})."),

    // Mensajes de validación para comparación de variables en bloque 3 OI
    ERROR_DIFERENCIA_CAMPOS(131, "¬¬{0}¬¬{1}¬¬{2}¬¬Diferencia en pareja de archivos - valor en {3}: {1}"),

    // Validador dias cotizados (trv.)
    ERROR_CAMPO_INFERIOR_A_30_DIAS_SIN_HABER_MARCADO_NOVEDAD_VALIDA(132,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, es inferior a 30 días sin haberse marcado una novedad válida."), 
    ERROR_CAMPO_SUPERA_VALOR_MAXIMO_DEFINIDO(133,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, supera el valor máximo definido para el campo."),
    ERROR_CAMPO_VALOR_DEBE_SER_MAYOR_CERO(133,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, debe ser mayor a cero."),

    // Validador formato id (trv.)
    ERROR_CAMPO_PRESENTA_INCONSISTENCIAS(135,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, presenta inconsistencias. {5}"),

    // Validador alfanumericos (trv.)
    ERROR_CAMPO_SOLO_ADMITE_ALFANUMERICOS(136,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4} con un tipo de documento {5}. Este tipo de documento sólo admite valores alfanuméricos"), 
    ERROR_CAMPO_NO_ADMITE_ALFANUMERICOS(137,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4} con un tipo de documento {5}. Este tipo de documento no admite valores alfanuméricos"),

    // Validador lista contexto (trv.)
    ERROR_CAMPO_NO_ESTA_ENTRE_CODIGOS_ADMITIDOS(138,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4} no se encuentra entre los códigos admitidos ({5})"),
    ERROR_VALOR_NO_ESTA_EN_RANGO_DE_VALORES_ADMITIDOS(140,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4} no se encuentra entre los valores admitidos {5} y {6}"),

    // Validador rango valor (trv.)
    ERROR_VALOR_NO_ESTA_EN_RANGO_VALIDO(141,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no se encuentra entre los valores admitidos ({5})"),

    // Validador de secuencia (trv.)
    ERROR_VALOR_NO_CONCUERDA_CON_ESPERADO_EN_SECUENCIA(142,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4}, no concuerda con el valor esperado para la secuencia ({5})"), 
    ERROR_CAMPO_NO_TIENE_VALOR(143, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} no tiene valor"),

    // Validador valor unico (trv.)
    ERROR_CAMPO_NO_CORRESPONDE_AL_VALOR_UNICO_ADMITIDO(145,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {4} no corresponde al valor único admitido ({5})"),

    // Validador de cruce de campos (trv.)
    ERROR_CRUCE_VALOR_VS_VACIO(146,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {4} , no es válido cuando el {5} es vacío."), 
    ERROR_CRUCE_SIN_VALOR_VS_NO_VACIO(147,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta valor vacío no válido cuando {4} es no vacío ({5})."), 
    ERROR_CRUCE_SIN_VALOR_VS_VACIO(148,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, presenta valor vacío no válido cuando {4} es vacío."), 
    ERROR_CRUCE_VALOR_VS_NO_VACIO(149,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {4} , no es válido cuando el {5} no es vacío ({6})."), 
    ERROR_CRUCE_VALOR_VS_REFERENCIA(150,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {4} , no es válido cuando el {5} es {6}"), 
    ERROR_CRUCE_SIN_VALOR_VS_VACIO_NO_REFERENCIA(151,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser vacío cuando el {4} es vacío o es diferente al valor de referencia ({5})."), 
    ERROR_CRUCE_SIN_VALOR_VS_NO_REFERENCIA(152,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser vacío cuando el {4} es diferente a {5} ({6})."), 
    ERROR_CRUCE_SIN_VALOR_VS_REFERENCIA(153,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser vacío cuando el {4} es {5}."), 
    ERROR_CRUCE_VALOR_VS_NO_REFERENCIA(154,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {1} , no es válido cuando el {4} es diferente a {5} ({6})."),
    
    ERROR_CRUCE_VALOR_REFERENCIA_DISTINTA(180,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta un valor de {4} distinto al esperado ({5})"),

    // bloque 0 OI
    ERROR_TAMANO_ARCHIVO_CERO(155,"¬¬{0}¬¬{1}¬¬{2}¬¬El archivo {3} presenta un tamaño de cero."), 
    ERROR_TAMANO_ARCHIVO_INDIVIDUAL(156,"¬¬{0}¬¬{1}¬¬{2}¬¬El archivo {3} presenta un tamaño superior a {4}Mb."), 
    ERROR_ARCHIVO_NULO(157,"¬¬{0}¬¬{1}¬¬{2}¬¬No se cuenta con la información mínima requerida para el registro del archivo."), 
    ERROR_ARCHIVO_INCOMPLETO(158,"¬¬{0}¬¬No se ha recibido información del archivo a cargar."), 
    ERROR_EXTENSION_ARCHIVO(159, "¬¬{0}¬¬{1}¬¬{2}¬¬La extensión del archivo {1} no es válida."),

    // estado por falta de fecha de fecha de modificación del archivo
    ERROR_ARCHIVO_SIN_FECHA_MODIFICACION(160,"¬¬{0}¬¬Archivo {3} descargado con inconsistencias. No se cuenta con la fecha de modificación del archivo"),

    // estados por duplicidad
    ERROR_ARCHIVO_DUPLICADO(161,"¬¬{0}¬¬{1}¬¬{2}¬¬Archivo {1} descargado con inconsistencias. Archivo duplicado"), 
    ERROR_ARCHIVO_DUPLICADO_ANTERIOR(162,"¬¬{0}¬¬{1}¬¬{2}¬¬Archivo {3} descargado con inconsistencias. Archivo de reproceso duplicado menos reciente"),

    // estados por combinatoria
    ERROR_ARCHIVO_GRUPO_NO_VALIDO(163,"¬¬{0}¬¬{1}¬¬{2}¬¬Archivo {1} descargado con inconsistencias. El archivo corresponde a un grupo de archivo diferente al esperado"), 
    ERROR_ARCHIVO_REPROCESO_PREVIO(164,"¬¬{0}¬¬{1}¬¬{2}¬¬Archivo {3} descargado con inconsistencias. Archivo normal con un reproceso previo"), 
    ERROR_ARCHIVO_ANULACION_FALLIDA(165,"¬¬{0}¬¬Archivo {1} descargado con inconsistencias. No se anularon las entradas anteriores correctamente"),

    // bloque 0 OF
    ERROR_INSTANCIA_PERSISTENCIA(166,"¬¬{0}¬¬Error al solicitar acceso a la Base de Datos."),

    // excepción no controlada componente
    ERROR_EXCEPCION_COMPONENTE(167,"¬¬{0}¬¬{1}¬¬{2}¬¬Se presentó una excepción no controlada durante la ejecución del componente de lectura de archivos: {3}"),
    
    ERROR_CALCULO(168,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}, no puede ser validado por un error durante el cálculo de {4} - {5}"),

    // mensajes previos al cruce con BD
    ERROR_TIPO_ARCHIVO_CRUCE_BD(169, "¬¬{0}¬¬El archivo {1}, no es válido para cruce con base de datos de afiliados"),
    
    // mensajes de la verificación de correcciones de novedades ING y RET
    ERROR_CORRECCION_NOVEDAD_CASO1(170, "¬¬{0}¬¬{1}¬¬{2}¬¬La línea de corrección del cotizante con {3} número {4}, incluye novedad {5} presentada el {6}, después del último día hábil del mes de vencimiento ({7})"), 
    ERROR_CORRECCION_NOVEDAD_CASO2(171, "¬¬{0}¬¬{1}¬¬{2}¬¬La línea de corrección del cotizante {3} con {4} número {5}, incluye novedad {6} presentada el {7}, después del {8}° día hábil del mes siguiente a la fecha de novedad ({9})"),
    
    ERROR_UBICACION_LABORAL_EXTRANJERO(172, "¬¬{0}¬¬{1}@@{2}¬¬{3}¬¬Los campos {4} con valores {1} y {2} respectivamente, deben ser iguales a los de la Caja de Compensación ({5}-{6}) para un cotizante Independiente Extranjero o Colombiano en el Exterior"),
    
    // mensajes para los errores de novedades en múltiples registros
    ERROR_NOVEDAD_MULTIPLE_IGUAL_IBC(173, "¬¬{0}¬¬El cotizante presenta novedades en registro separado con el mismo Ingreso Base de Cotización ({1})"), 
    ERROR_NOVEDAD_MULTIPLE_ING_RET_SEPARADOS(174, "¬¬{0}¬¬El cotizante presenta novedades ING y RET en líneas separadas (líneas {1} y {2} respectivamente)"), 
    ERROR_NOVEDAD_MULTIPLE_NOVEDAD_REPETIDA(175, "¬¬{0}¬¬El cotizante presenta la misma novedad {1} con diferente Ingreso Base de Cotización en la línea {2}"), 
    ERROR_NOVEDAD_MULTIPLE_DIAS_COTIZADOS_ING_RET(176, "¬¬{0}¬¬Las novedades presentadas por el cotizante con {1} número {2}, no abarcan la cantidad de días cotizados con novedades ING o RET ({3})"), 
    ERROR_NOVEDAD_MULTIPLE_DIAS_COTIZADOS(177, "¬¬{0}¬¬Las novedades presentadas por el cotizante con {1} número {2}, no cumplen el criterio de dias cotizados para un período ({3})"), 
    ERROR_NOVEDAD_MULTIPLE_ING_RET_MULTIPLE (178, "¬¬{0}¬¬El cotizante presenta múltiples novedades ING y RET en registros separados"), 
    ERROR_NOVEDAD_MULTIPLE_CALCULO_IBC (179, "¬¬{0}¬¬El valor del IBC {1}, es diferente al calculado para {2} días cotizados reportados en el registro Tipo 2 ({3})"), 
    ERROR_NOVEDAD_MULTIPLE_CALCULO_IBC_ING (180, "¬¬{0}¬¬El valor del IBC {1}, es diferente al calculado para {2} días cotizados reportados en el registro Tipo 2 ({3} valor mínimo con novedad ING)"),
    ERROR_NOVEDAD_MULTIPLE_TIPO_COTIZANTE (181, "¬¬{0}¬¬El tipo de cotizante, para el cotizante con {1} número {2}, no es congruente en todos sus registros Tipo 2"),
    
    
    ERROR_VALOR_OBLIGATORIO_TIPO_PERSONA(182, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {1}, es obligatorio o no es valor numérico para un tipo de persona {4}."), 
    ERROR_VALOR_NO_OBLIGATORIO_TIPO_PERSONA(183, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {1}, debe ser vacío o cero para un tipo de persona {4}."), 
    ERROR_PERIODO_ANIO_MENOR_MATRICULA (184, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {1}.  El año del período del aporte ({4}) es anterior a la fecha de matrícula mercantíl del aportante ({5})"), 
    
    ERROR_FALTA_PARAMETROS_SIMPLE (185, "Error en cálculo, no se cuenta con el dato: {0}"),
    ERROR_CALCULO_SIMPLE(186, "Error en cálculo, no se puede establecer el valor: {0}"), 
    
    ERROR_PERIODO_LIMITE(187, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor: {1}, no es válido para un período posterior al {4} ({5})"),

    // mensajes para la comparación de sumatorias de IBC y días cotizados en planillas de corrección
    ERROR_DIFERENCIA_SUMATORIA_DIAS_CORRECCION (188, "¬¬{0}¬¬{1}¬¬{2}¬¬La sumatoria de días cotizados para el cotizante con {3} número {4} en los registros tipo C ({1}) es menor a la calculada para los registros tipo A ({5})"),
    ERROR_DIFERENCIA_SUMATORIA_IBC_CORRECCION (189, "¬¬{0}¬¬{1}¬¬{2}¬¬La sumatoria de IBC para el cotizante con {3} número {4} en los registros tipo C ({1}) es menor a la calculada para los registros tipo A ({5})"),
    
    ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_NO_CONCUERDA(190,"¬¬{0}¬¬{1}¬¬{2}¬¬El código de la Entidad Administradora en el nombre del archivo ({1}), no coincide con el de la CCF actual ({3})"),
    ERROR_CAMPO_NO_VALIDO_PARA_CLASE_APORTANTE_NOV_SLN(191,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor : {1}, no es válido cuando presenta novedad de suspención (SLN)."),
    ERROR_CUENTA_CORRECCIONES(192,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3}. El conjunto de registros de correción para el cotizante con ID {4}, presenta más de dos (2) correcciones ({5})"),
    ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO(193,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {0} no fue diligenciado."),
    ERROR_ESTRUCTURA_ARCHIVO_CAMPO(194, "¬¬{0}¬¬{1}¬¬{2}¬¬Error en la lectura del campo {3} (Validación de Obligatoriedad, formato y/o posición. El dato inválido se considera como vacío para efectos de validaciones posteriores)"),
    ERROR_ESTRUCTURA_ARCHIVO_LINEA_NO_RECONOCIDA(195, "¬¬{0}¬¬La longitud de la línea no concuerda con ningún tipo de línea parametrizado para el tipo de archivo."),
    ERROR_ESTRUCTURA_ARCHIVO_LINEA_INCOMPLETA(196, "¬¬{0}¬¬La línea correspondiente al tipo {1}, presenta una longitud diferente a la esperada según la normatividad vigente. La línea no puede ser leída junto los valores requeridos para validaciones posteriores que esta contenga."),
    ERROR_ESTRUCTURA_ARCHIVO_LINEA_REQUERIDA(197, "¬¬{0}¬¬La línea tipo {1} no se encuentra en el archivo y es de caracter obligatorio."),
    ERROR_ESTRUCTURA_ARCHIVO_LINEA_VACIA(198, "¬¬{0}¬¬Línea vacía o la línea no corresponde a ningún formato establecido por normatividad vigente para el tipo de archivo leído."),
    ERROR_ESTRUCTURA_ARCHIVO_NO_RECONOCIDO(199, "Error no reconocido en la estructura del archivo: {0}"),
    ERROR_ESTRUCTURA_ARCHIVO_MULTIPLES_RESGITROS_UNICOS(200, "¬¬{0}¬¬Se han detectado múltiples ocurrencias del {1} en el archivo ({2})."),
    ERROR_CONCILIACION_IDENTIFICACION_APORTANTE(201, "¬¬{0}¬¬El número de identificación del aportante en el archivo de Operador de Información ({1}), no coincide con el encontrado en el registro de log financiero correspondiente a la planilla ({2})"),
    
    ERROR_CONTENIDO_DE_ARCHIVO_PERDIDO_EN_ECM (202, "¬¬{0}¬¬Archivo imposible de persistir. No se encuentra el contenido del archivo."),
    ERROR_PERSISTENCIA_REGISTRO_LECTURA_SECUENCIA(203, "¬¬{0}¬¬Se ha presentado un error al momento de persistir el registro (Lectura de secuencia)"),
    ERROR_NO_SE_PUEDE_PREPARAR_INFO_PARA_BLOQUE_3(204,"¬¬{0}¬¬Se presentaron errores de estructura que impidieron preparar la información para el bloque de validación 3"), 
    
    // Mensajes de error para validación de subgrupos de datos de planilla de corrección 
    ERROR_CORRECCION_SUBGRUPO_SEPARADO (205, "¬¬{0}¬¬Se encuentran datos del cotizante identificado con {1} {2} validado anteriormente en el archivo."),
    ERROR_CORRECCION_SUBGRUPO_PRIMERA_ULTIMA_CORRECCION (206, "¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {1}, no es válido para el {4} registro del grupo de datos del cotizante identificado con {5} {6}"),
    ERROR_CORRECCION_SUBGRUPO_CANTIDAD_REGISTROS (207, "¬¬{0}¬¬Se encuentra que la cantidad total de registros de corrección A ({1}) es mayor que la de registros de corrección C ({2}) para el cotizante identificado con {3} {4}."),

	//Mensajes de error para CC planillas O y Q
    ERROR_CANTIDAD_DIAS_MORA_EN_CERO(19,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} , con valor {4}, para este tipo planilla debe ser mayor a 0"),
    ERROR_CAMPO_SALARIO_MAYOR_A_IBC(62,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} con valor {4}, es inferior al valor del IBC  ({5})."),
    ERROR_FECHA_APERTURA_LIQUIDACION_FORZOSA (208, "¬¬{0}¬¬La línea tipo {1} no se encuentra en el archivo y es de caracter obligatorio."),

    //longitud documento

    ERROR_LONGITUD_PERM_ESP_PERMANENCIA(209,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4} con un tipo de documento {5}. No permite un numero de digito que no sea 15"),
    ERROR_LONGITUD_PERM_PROT_TEMPORAL(210,"¬¬{0}¬¬{1}¬¬{2}¬¬El campo {3} presenta valor {4} con un tipo de documento {5}. No permite mas de 8 dígitos");
    
    
    private final Integer codigo;
    private final String descripcion;

    /**
     * Obtiene el mensaje asosicado al valor de la enumeración, reemplezando las
     * variables específicas.
     * 
     * @param params
     *            Array de strings para interpolar en el mensaje
     * @return String Con la cadena interpolada a partir de los varargs
     */
    public String getReadableMessage(String... params) {
        return codigo + ": " + Interpolator.interpolate(getDescripcion(), params);
    }

    /**
     * Método para obtener un mensaje mixto enlazado en un StringBuilder que
     * incluye al valor interpolado de la enumeración
     * 
     * @param mensaje
     *            String builder en el que se están uniendo los mensajes
     * @param params
     *            Array de strings para interpolar en el mensaje
     * @return StringBuilder Con el mensaje añadido
     */
    public StringBuilder getAppendedMessage(StringBuilder mensaje, String... params) {
        return mensaje.append(Interpolator.interpolate(getDescripcion().replace("¬¬{0}¬¬{1}¬¬{2}¬¬", ""), params));
    }

    /**
     * Función para obtener una enumeración de clase aportante a partir de un
     * valor de referencia
     */
    public static MensajesValidacionEnum obtenerMensajeValidacionEnum(Integer codigo) {
        for (MensajesValidacionEnum mensajeValidacion : MensajesValidacionEnum.values()) {if (mensajeValidacion.codigo == codigo) {    return mensajeValidacion;}
        }
        return null;
    }

    /**
     * @param codigo
     * @param descripcion
     */
    private MensajesValidacionEnum(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

}
