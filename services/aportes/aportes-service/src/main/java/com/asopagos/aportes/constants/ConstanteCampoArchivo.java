package com.asopagos.aportes.constants;

import java.math.BigDecimal;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los campos
 * <b>Módulo:</b> Asopagos-212 - HU-481 <br/>
 * 
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ConstanteCampoArchivo {

	/**
	 * Secuencia
	 */
	public static final String SECUENCIA = "archivoIregistro2campo1";
	/**
	 * Tipo de registro.
	 */
	public static final String TIPO_REGISTRO = "archivoIregistro2campo2";
	/**
	 * Tipo identificación del cotizante.
	 */
	public static final String TIPO_IDENTIFICACION_COTIZANTE = "archivoIregistro2campo3";
	/**
	 * No. de identificación del cotizante.
	 */
	public static final String NUMERO_IDENTIFICACION_COTIZANTE = "archivoIregistro2campo4";
	/**
	 * Tipo de cotizante
	 */
	public static final String TIPO_COTIZANTE = "archivoIregistro2campo5";
	/**
	 * Subtipo de cotizante
	 */
	public static final String SUBTIPO_COTIZANTE = "archivoIregistro2campo6";
	/**
	 * Extranjero no obligado a cotizar a pensiones.
	 */
	public static final String EXTRANJERO_NO_ABLIGADO_A_COTIZAR = "archivoIregistro2campo7";
	/**
	 * Colombiano en el exterior.
	 */
	public static final String COLOMBIANO_EXTERIOR = "archivoIregistro2campo8";
	/**
	 * Código del Departamento de la ubicación laboral.
	 */
	public static final String CODIGO_DEPARTAMENTO = "archivoIregistro2campo9";
	/**
	 * Código del Municipio de la ubicación laboral.
	 */
	public static final String CODIGO_MUNICIPIO = "archivoIregistro2campo10";
	/**
	 * Primer apellido.
	 */
	public static final String PRIMER_APELLIDO = "archivoIregistro2campo11";
	/**
	 * Segundo apellido.
	 */
	public static final String SEGUNDO_APELLIDO = "archivoIregistro2campo12";
	/**
	 * Primer nombre.
	 */
	public static final String PRIMER_NOMBRE = "archivoIregistro2campo13";
	/**
	 * Segundo nombre.
	 */
	public static final String SEGUNDO_NOMBRE = "archivoIregistro2campo14";
	/**
	 * ING: Ingreso.
	 */
	public static final String ING = "archivoIregistro2campo15";
	/**
	 * RET: Retiro.
	 */
	public static final String RET = "archivoIregistro2campo16";
	/**
	 * VSP: Variación permanente de salario.
	 */
	public static final String VSP = "archivoIregistro2campo17";
	/**
	 * VST: Variación transitoria del salario.
	 */
	public static final String VST = "archivoIregistro2campo18";
	/**
	 * SLN: Suspensión temporal del contrato de trabajo, licencia no remunerada
	 * o comisión de servicios.
	 */
	public static final String SLN = "archivoIregistro2campo19";
	/**
	 * IGE: Incapacidad Temporal por Enfermedad General.
	 */
	public static final String IGE = "archivoIregistro2campo20";
	/**
	 * LMA: Licencia de Maternidad o paternidad.
	 */
	public static final String LMA = "archivoIregistro2campo21";
	/**
	 * VAC - LR: Vacaciones, Licencia remunerada
	 */
	public static final String VAC = "archivoIregistro2campo22";
	/**
	 * "IRL: días de incapacidad por accidente de trabajo o enfermedad laboral "
	 */
	public static final String IRL = "archivoIregistro2campo23";
	/**
	 * Días cotizados.
	 */
	public static final String DIAS_COTIZADOS = "archivoIregistro2campo24";
	/**
	 * Salario básico.
	 */
	public static final String SALARIO_BASICO = "archivoIregistro2campo25";
	/**
	 * Ingreso Base Cotización (IBC)
	 */
	public static final String IBC = "archivoIregistro2campo26";
	/**
	 * Tarifa.
	 */
	public static final String TARIFA = "archivoIregistro2campo27";
	/**
	 * Aporte obligatorio.
	 */
	public static final String APORTE_OBLIGATORIO = "archivoIregistro2campo28";
	/**
	 * Días cotizados.
	 */
	public static final String CORRECCIONES = "archivoIregistro2campo29";
	/**
	 * Salario básico.
	 */
	public static final String SALARIO_INTEGRAL = "archivoIregistro2campo30";
	/**
	 * Fecha de ingreso formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INGRESO = "archivoIregistro2campo31";
	/**
	 * Fecha de retiro formato (AAAA-MM-DD)
	 */
	public static final String FECHA_RETIRO = "archivoIregistro2campo32";
	/**
	 * Fecha inicio VSP formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VSP = "archivoIregistro2campo33";
	/**
	 * Fecha inicio SLN formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_SLN = "archivoIregistro2campo34";
	/**
	 * Fecha fin SLN formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_SLN = "archivoIregistro2campo35";
	/**
	 * Fecha inicio IGE formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_IGE = "archivoIregistro2campo36";
	/**
	 * Fecha fin IGE formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_IGE = "archivoIregistro2campo37";
	/**
	 * Fecha inicio LMA formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_LMA = "archivoIregistro2campo38";
	/**
	 * Fecha fin LMA formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_LMA = "archivoIregistro2campo39";
	/**
	 * Fecha inicio VAC - LR formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VAC = "archivoIregistro2campo40";
	/**
	 * Fecha fin VAC - LR formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_VAC = "archivoIregistro2campo41";
	/**
	 * Fecha inicio VCT formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VST = "archivoIregistro2campo42";
	/**
	 * Fecha fin VCT formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_VST = "archivoIregistro2campo43";
	/**
	 * Fecha inicio IRL formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_IRL = "archivoIregistro2campo44";
	/**
	 * Fecha fin IRL formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_IRL = "archivoIregistro2campo45";
	/**
	 * Número de horas laboradas
	 */
	public static final String NUMERO_HORA_LABORAL = "archivoIregistro2campo46";

	/**
	 * Secuencia
	 */
	public static final String SECUENCIA_MSG = "Secuencia";
	/**
	 * Tipo de registro.
	 */
	public static final String TIPO_REGISTRO_MSG = "Tipo de registro";
	/**
	 * Tipo identificación del cotizante.
	 */
	public static final String TIPO_IDENTIFICACION_COTIZANTE_MSG = "Tipo identificación del cotizante";
	/**
	 * No. de identificación del cotizante.
	 */
	public static final String NUMERO_IDENTIFICACION_COTIZANTE_MSG = "No. de identificación del cotizante";
	/**
	 * Tipo de cotizante
	 */
	public static final String TIPO_COTIZANTE_MSG = "Tipo de cotizante";
	/**
	 * Subtipo de cotizante
	 */
	public static final String SUBTIPO_COTIZANTE_MSG = "Subtipo de cotizante";
	/**
	 * Extranjero no obligado a cotizar a pensiones.
	 */
	public static final String EXTRANJERO_NO_ABLIGADO_A_COTIZAR_MSG = "Extranjero no obligado a cotizar a pensiones";
	/**
	 * Colombiano en el exterior.
	 */
	public static final String COLOMBIANO_EXTERIOR_MSG = "Colombiano en el exterior";
	/**
	 * Código del Departamento de la ubicación laboral.
	 */
	public static final String CODIGO_DEPARTAMENTO_MSG = "Código del Departamento de la ubicación laboral";
	/**
	 * Código del Municipio de la ubicación laboral.
	 */
	public static final String CODIGO_MUNICIPIO_MSG = "Código del Municipio de la ubicación laboral";
	/**
	 * Primer apellido.
	 */
	public static final String PRIMER_APELLIDO_MSG = "Primer apellido";
	/**
	 * Segundo apellido.
	 */
	public static final String SEGUNDO_APELLIDO_MSG = "Segundo apellido";
	/**
	 * Primer nombre.
	 */
	public static final String PRIMER_NOMBRE_MSG = "Primer nombre";
	/**
	 * Segundo nombre.
	 */
	public static final String SEGUNDO_NOMBRE_MSG = "Segundo nombre";
	/**
	 * ING: Ingreso.
	 */
	public static final String ING_MSG = "ING: Ingreso";
	/**
	 * RET: Retiro.
	 */
	public static final String RET_MSG = "RET: Retiro";
	/**
	 * VSP: Variación permanente de salario.
	 */
	public static final String VSP_MSG = "VSP: Variación permanente de salario";
	/**
	 * VST: Variación transitoria del salario.
	 */
	public static final String VST_MSG = "VST: Variación transitoria del salario";
	/**
	 * SLN: Suspensión temporal del contrato de trabajo, licencia no remunerada
	 * o comisión de servicios.
	 */
	public static final String SLN_MSG = "SLN: Suspensión temporal del contrato de trabajo, licencia no remuneradao comisión de servicios";
	/**
	 * IGE: Incapacidad Temporal por Enfermedad General.
	 */
	public static final String IGE_MSG = "IGE: Incapacidad Temporal por Enfermedad General";
	/**
	 * LMA: Licencia de Maternidad o paternidad.
	 */
	public static final String LMA_MSG = "LMA: Licencia de Maternidad o paternidad";
	/**
	 * VAC - LR: Vacaciones, Licencia remunerada
	 */
	public static final String VAC_MSG = "VAC - LR: Vacaciones, Licencia remunerada";
	/**
	 * "IRL: días de incapacidad por accidente de trabajo o enfermedad laboral "
	 */
	public static final String IRL_MSG = "IRL: días de incapacidad por accidente de trabajo o enfermedad laboral";
	/**
	 * Días cotizados.
	 */
	public static final String DIAS_COTIZADOS_MSG = "Días cotizados";
	/**
	 * Salario básico.
	 */
	public static final String SALARIO_BASICO_MSG = "Salario básico";
	/**
	 * Ingreso Base Cotización (IBC)
	 */
	public static final String IBC_MSG = "Ingreso Base Cotización (IBC)";
	/**
	 * Tarifa.
	 */
	public static final String TARIFA_MSG = "Tarifa";
	/**
	 * Aporte obligatorio.
	 */
	public static final String APORTE_OBLIGATORIO_MSG = "Aporte obligatorio";
	/**
	 * Días cotizados.
	 */
	public static final String CORRECCIONES_MSG = "Días cotizados";
	/**
	 * Salario básico.
	 */
	public static final String SALARIO_INTEGRAL_MSG = "Salario básico";
	/**
	 * Fecha de ingreso formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INGRESO_MSG = "Fecha de ingreso";
	/**
	 * Fecha de retiro formato (AAAA-MM-DD)
	 */
	public static final String FECHA_RETIRO_MSG = "Fecha de retiro";
	/**
	 * Fecha inicio VSP formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VSP_MSG = "Fecha inicio VSP";
	/**
	 * Fecha inicio SLN formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_SLN_MSG = "Fecha inicio SLN";
	/**
	 * Fecha fin SLN formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_SLN_MSG = "Fecha fin SLN";
	/**
	 * Fecha inicio IGE formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_IGE_MSG = "Fecha inicio IGE";
	/**
	 * Fecha fin IGE formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_IGE_MSG = "Fecha fin IGE";
	/**
	 * Fecha inicio LMA formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_LMA_MSG = "Fecha inicio LMA";
	/**
	 * Fecha fin LMA formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_LMA_MSG = "Fecha fin LMA";
	/**
	 * Fecha inicio VAC - LR formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VAC_MSG = "Fecha inicio VAC - LR";
	/**
	 * Fecha fin VAC - LR formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_VAC_MSG = "Fecha fin VAC - LR";
	/**
	 * Fecha inicio VCT formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_VST_MSG = "Fecha inicio VCT";
	/**
	 * Fecha fin VCT formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_VST_MSG = "Fecha fin VCT";
	/**
	 * Fecha inicio IRL formato (AAAA-MM-DD)
	 */
	public static final String FECHA_INICIO_IRL_MSG = "Fecha inicio IRL";
	/**
	 * Fecha fin IRL formato (AAAA-MM-DD)
	 */
	public static final String FECHA_FIN_IRL_MSG = "Fecha fin IRL";
	/**
	 * Número de horas laboradas
	 */
	public static final String NUMERO_HORA_LABORAL_MSG = "Número de horas laboradas";

	public static final String INVALIDO_MSG = ".Invalido";
	/**
	 * Tipo de registro establecido en el archivo
	 */
	public static final int TIPO_REGISTRO_ESTABLECIDO = 2;
	/**
	 * Valor de inicio de la secuencia
	 */
	public static final Long INICIO_SECUENCIA = 00001L;
	/**
	 * Valor de fin de la secuencia
	 */
	public static final Long FIN_SECUENCIA = 99999L;
	/**
	 * Días inicial cotizados
	 */
	public static final Long DIA_COTIZADO_INICIO = 0L;
	/**
	 * Días final cotizados
	 */
	public static final Long DIA_COTIZADO_FINAL = 30L;
	/**
	 * Valor minimo del salario
	 */
	public static final BigDecimal VALOR_MINIMO_SALARIO = new BigDecimal(0);
	/**
	 * Valor de correcion tipo A
	 */
	public static final String VALOR_CORRECION_A = "A";
	/**
	 * Valor de correcion tipo C
	 */
	public static final String VALOR_CORRECION_C = "C";
	/**
	 * Valor del mensaje debe ser un formato AAAA-MM-DD
	 */
	public static final String DEBE_SER_UN_FORMATO_AAAA_MM_DD = "Debe ser en formato (AAAA-MM-DD)";
}
