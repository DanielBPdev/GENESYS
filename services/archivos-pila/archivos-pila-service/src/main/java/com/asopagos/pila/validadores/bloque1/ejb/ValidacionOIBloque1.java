package com.asopagos.pila.validadores.bloque1.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque1.interfaces.IValidacionOIBloque1;

/**
 * <b>Descripción:</b> Clase que contiene la función para la validación del nombre de los archivos del operador de Información en el
 * Bloque 1 <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class ValidacionOIBloque1 implements IValidacionOIBloque1, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al Logger
     */
    private static ILogger logger = LogManager.getLogger(ValidacionOIBloque1.class);

    @SuppressWarnings("unchecked")
    @Override
    public RespuestaValidacionDTO validacionNombreArchivo(String nombreArchivo, Map<String, Object> contexto,
            PerfilLecturaPilaEnum perfilArchivo, RespuestaValidacionDTO result) throws ErrorFuncionalValidacionException {

        logger.debug("Inicia validacionNombreArchivo(String nombreArchivo, Map<String, Object>, PerfilLecturaPilaEnum)");

        RespuestaValidacionDTO resultTemp = result;

        /*
         * el nombre del archivo, de acuerdo a resolución, consta de 9 fragmentos separados por guión bajo (_)
         * y deben estar escritos completamente en mayúsculas
         */

        // en primer lugar, fragmento el nombre del archivo para establecer la cantidad de campos encontrados
        String[] nombreSeparado = nombreArchivo.split("_");

        String mensajeSalida = null;

        if (nombreSeparado.length != 9) {

            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_NO_CUMPLE_FORMATO.getReadableMessage(
                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO, nombreArchivo, TipoErrorValidacionEnum.TIPO_2.toString());

            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
        }

        // se comprueba cada fragmento del nombre
        for (int i = 0; i < nombreSeparado.length; i++) {
            switch (i) {
                case 0:
                    // Fecha de Pago
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // formato yyyy-MM-dd
                        if (!Pattern.matches(ConstantesComunesProcesamientoPILA.PATRON_FORMATO_FECHA_GUION, nombreSeparado[i])) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FORMATO_FECHA_NO_VALIDO.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }

                        // coherencia de la fecha
                        Integer anio = null;
                        Integer mes = null;
                        Integer dia = null;

                        try {
                            anio = Integer.parseInt(nombreSeparado[0].split("-")[0]);
                            mes = Integer.parseInt(nombreSeparado[0].split("-")[1]);
                            dia = Integer.parseInt(nombreSeparado[0].split("-")[2]);

                            try {
                                LocalDate.of(anio, mes, dia);
                            } catch (DateTimeException e) {

                                mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FECHA_PAGO_NO_VALIDA.getReadableMessage(
                                        ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                        TipoErrorValidacionEnum.TIPO_2.toString());

                                resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida,
                                        null, null);
                            }
                        } catch (Exception e) {
                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FECHA_PAGO_NO_VALIDA.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_FECHA_PAGO, nombreSeparado[i]);
                    break;
                case 1:
                    // Modalidad de Planilla Integrada de Liquidación de Aportes.
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_MODALIDAD_PLANILLA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // valores únicos 1 y 2
                        if (!nombreSeparado[i].equals("1") && !nombreSeparado[i].equals("2")) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_MODALIDAD_PLANILLA.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_MODALIDAD_PLANILLA, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_MODALIDAD, nombreSeparado[i]);
                    break;
                case 2:
                    // Número de formulario único o planilla pagada
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_NUMERO_PLANILLA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }

                    contexto.put(ConstantesContexto.NOMBRE_NUMERO_PLANILLA, nombreSeparado[i]);
                    break;
                case 3:
                    // Tipo de documento de aportante
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_DOCUMENTO_APORTANTE, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // se toma la lista de referencia desde el contexto
                        List<TipoIdentificacionEnum> tiposIdValidos = (List<TipoIdentificacionEnum>) contexto
                                .get(ConstantesContexto.TIPOS_ID_VALIDOS);

                        TipoIdentificacionEnum tipoEnNombre = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(nombreSeparado[i]);

                        if (tipoEnNombre == null || !tiposIdValidos.contains(tipoEnNombre)) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_TIPO_DOCUMENTO_INVALIDO.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_DOCUMENTO_APORTANTE, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_TIPO_DOCUMENTO, nombreSeparado[i]);
                    break;
                case 4:
                    // Número de identificación del aportante sin dígito de verificación.
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_NUMERO_IDENTIFICACION_APORTANTE, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }

                    contexto.put(ConstantesContexto.NOMBRE_ID_APORTANTE, nombreSeparado[i]);
                    break;
                case 5:
                    // Código de la Entidad Administradora
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // validación de caracteres alfanumericos en mayúscula
                        if (!Pattern.matches(ConstantesComunesProcesamientoPILA.PATRON_FORMATO_CODIGO_CCF_MAYUSCULA, nombreSeparado[i])) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_INVALIDO.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }

                        // validación del código de la CCF de la planilla respecto a la entidad que tramita la misma
                        String codigoCCF = (String) contexto.get(ConstantesContexto.CODIGO_CCF);
                        if (!nombreSeparado[i].equals(codigoCCF)) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_NO_CONCUERDA
                                    .getReadableMessage(ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA,
                                            nombreSeparado[i], TipoErrorValidacionEnum.TIPO_2.toString(), codigoCCF);

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_CODIGO_ENTIDAD, nombreSeparado[i]);
                    break;
                case 6:
                    // Código del operador de información a través del cual pagó el aportante
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_CODIGO_OI, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // se toma el listado de referencia desde el contexto
                        Set<String> codigosOI = (Set<String>) contexto.get(ConstantesContexto.OPERADORES_INFORMACION);

                        if (!codigosOI.contains(nombreSeparado[i])) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_OPERADOR_INFORMACION_INVALIDO
                                    .getReadableMessage(ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_CODIGO_OI, nombreSeparado[i],
                                            TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_CODIGO_OPERADOR, nombreSeparado[i]);
                    break;
                case 7:
                    // Tipo de Archivo
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_ARCHIVO, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // primero se toma el ID del FileDefinitionLoad, con el fin de establecer los valores válidos

                        TipoArchivoPilaEnum tipoArchivoPilaEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(nombreSeparado[i]);

                        if (tipoArchivoPilaEnum == null) {

                            String tiposValidos = TipoArchivoPilaEnum.ARCHIVO_OI_A.getCodigo() + ", "
                                    + TipoArchivoPilaEnum.ARCHIVO_OI_AP.getCodigo() + ", " + TipoArchivoPilaEnum.ARCHIVO_OI_AR.getCodigo()
                                    + ", " + TipoArchivoPilaEnum.ARCHIVO_OI_APR.getCodigo() + ", "
                                    + TipoArchivoPilaEnum.ARCHIVO_OI_I.getCodigo() + ", " + TipoArchivoPilaEnum.ARCHIVO_OI_IP.getCodigo()
                                    + ", " + TipoArchivoPilaEnum.ARCHIVO_OI_IR.getCodigo() + ", "
                                    + TipoArchivoPilaEnum.ARCHIVO_OI_IPR.getCodigo() + ", ";

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_TIPO_ARCHIVO.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_ARCHIVO, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_3.toString(), tiposValidos);

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);

                            logger.debug(
                                    "Finaliza validacionNombreArchivo(String nombreArchivo, Map<String, Object>, PerfilLecturaPilaEnum) - "
                                            + mensajeSalida);

                            return resultTemp;
                        }
                        else if (tipoArchivoPilaEnum.getPerfilArchivo().compareTo(perfilArchivo) != 0) {
                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_TIPO_ARCHIVO.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_ARCHIVO, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_3.toString(), perfilArchivo.getDescripcion());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);

                            logger.debug(
                                    "Finaliza validacionNombreArchivo(String nombreArchivo, Map<String, Object>, PerfilLecturaPilaEnum) - "
                                            + mensajeSalida);

                            return resultTemp;
                        }
                        else {
                            // sí se ha cumplido con el requisito del tipo de archivo, preparo las variables de control por tipo
                            // en el caso que una variable de control sea empleada por más de un tipo de archivo, el nombre se mantiene por uniformidad
                            switch (tipoArchivoPilaEnum) {
                                case ARCHIVO_OI_I:
                                case ARCHIVO_OI_IR:
                                    // lista de tipos de cotizante encontrados
                                    contexto.put(ConstantesContexto.TIPOS_COTIZANTES_ENCONTRADOS, new HashSet<>());

                                    // contador de registros tipo 2
                                    contexto.put(ConstantesContexto.CONTADOR_REGISTROS_2, Integer.valueOf(0));

                                    // todas las tarifas en cero presentan una novedad SNL, IGE, LMA o IRL
                                    contexto.put(ConstantesContexto.TARIFA_CERO_NOVEDAD, true);

                                    // sumatorias IBC (una general y 2 para registros A y C en planilla tipo N)
                                    contexto.put(ConstantesContexto.SUMATORIA_IBC_GENERAL, new BigDecimal(0));
                                    contexto.put(ConstantesContexto.SUMATORIA_IBC_A, new BigDecimal(0));
                                    contexto.put(ConstantesContexto.SUMATORIA_IBC_C, new BigDecimal(0));

                                    // sumatorias Aporte Obligatorio (una general y 2 para registros A y C en planilla tipo N)
                                    contexto.put(ConstantesContexto.SUMATORIA_AO_GENERAL, new BigDecimal(0));
                                    contexto.put(ConstantesContexto.SUMATORIA_AO_A, new BigDecimal(0));
                                    contexto.put(ConstantesContexto.SUMATORIA_AO_C, new BigDecimal(0));

                                    // control de secuencia de registro tipo 2
                                    contexto.put(ConstantesContexto.CONTROL_SECUENCIA_REGISTRO_2, Integer.valueOf(1));

                                    // último número de secuencia registro tipo 2
                                    contexto.put(ConstantesContexto.ULTIMA_SECUENCIA_REGISTRO_2, Integer.valueOf(0));

                                    break;
                                case ARCHIVO_OI_IP:
                                case ARCHIVO_OI_IPR:
                                    // control de secuencia de registro tipo 2
                                    contexto.put(ConstantesContexto.CONTROL_SECUENCIA_REGISTRO_2, Integer.valueOf(1));

                                    // último número de secuencia registro tipo 2
                                    contexto.put(ConstantesContexto.ULTIMA_SECUENCIA_REGISTRO_2, Integer.valueOf(0));

                                    // contador de registros tipo 2
                                    contexto.put(ConstantesContexto.CONTADOR_REGISTROS_2, Integer.valueOf(0));

                                    // sumatoria de las mesadas pensionales
                                    contexto.put(ConstantesContexto.SUMATORIA_MESADAS, new BigDecimal(0));

                                    // sumatoria Aporte Obligatorio
                                    contexto.put(ConstantesContexto.SUMATORIA_AO_GENERAL, new BigDecimal(0));

                                    // listado de pensionados leídos para control de cantidad de pensionados
                                    Set<String> pensionados = new HashSet<String>();
                                    contexto.put(ConstantesContexto.LISTA_PENSIONADOS, pensionados);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_TIPO_ARCHIVO, nombreSeparado[i]);
                    break;
                case 8:
                    // Período de pago al cual pertenece la Planilla
                    // campo diligenciado
                    if (nombreSeparado[i].isEmpty()) {
                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CAMPO_VACIO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_PERIODO_APORTE, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    else {

                        // formato yyyy-MM
                        if (!Pattern.matches(ConstantesContexto.PATRON_PERIODO_NOMBRE, nombreSeparado[i].toLowerCase())) {

                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FORMATO_PERIODO_APORTE.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_PERIODO_APORTE, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }

                        // coherencia del mes
                        Integer mes = Integer.parseInt(nombreSeparado[0].split("-")[1]);

                        if (mes > 12) {
                            mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_VALOR_PERIODO_APORTE.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_PERIODO_APORTE, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString());

                            resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null,
                                    null);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_PERIODO_PAGO, nombreSeparado[i].replace(".txt", "").replace(".TXT", ""));
                    break;
                case 9:
                    // Los nombres de los archivos deben ser grabados en mayúsculas
                    if (!nombreArchivo.equals(nombreArchivo.toUpperCase())) {

                        mensajeSalida = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CARACTERES_MINUSCULA
                                .getReadableMessage(TipoErrorValidacionEnum.TIPO_2.toString());

                        resultTemp = FuncionesValidador.agregarError(resultTemp, null, BloqueValidacionEnum.BLOQUE_1_OI, mensajeSalida, null, null);
                    }
                    break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza validacionNombreArchivo(String nombreArchivo, Map<String, Object>, PerfilLecturaPilaEnum)");
        return resultTemp;
    }
}
