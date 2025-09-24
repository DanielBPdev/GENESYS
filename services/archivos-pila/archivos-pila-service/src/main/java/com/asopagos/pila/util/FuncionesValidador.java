package com.asopagos.pila.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.QueriesConstants;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.dto.modelo.LogErrorPilaM1ModeloDTO;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro2;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro2;
import com.asopagos.entidades.pila.parametrizacion.NormatividadFechaVencimiento;
import com.asopagos.entidades.pila.procesamiento.ErrorValidacionLog;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAPEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoFEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoLineaTipoRegistroEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.EntityManagerProceduresPeristanceLocal;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.dto.ComposicionMensajeEstructuraDTO;
import com.asopagos.pila.dto.ControlLoteOFDTO;
import com.asopagos.pila.dto.ElementoEtiquetaControlMensajesDTO;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.ErrorValidacionValorMoraDTO;
import com.asopagos.pila.dto.PilaArchivoFRegistro6ModeloDTO;
import com.asopagos.pila.dto.PilaArchivoIPRegistro2ModeloDTO;
import com.asopagos.pila.dto.PilaArchivoIRegistro2ModeloDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.dto.UbicacionCampoArchivoPilaDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.rest.exception.TechnicalException;

import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;

/**
 * <b>Descripcion:</b> Clase en la que se implementan funcionalidades empleadas
 * por los validadores <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class FuncionesValidador {
    /** Referencia al logger */
    private static final ILogger logger = LogManager.getLogger(FuncionesValidador.class);

    /**
     * Mapa para los valores tomados de constantes y/o parámetros y de tablas de parametrización
     */
    public static Map<String, Object> valoresGeneralesValidacion;

    /**
     * Constructor privado
     */
    private FuncionesValidador() {
    }

    /**
     * Método encargado de obtener la fecha del último día hábil del mes de otra
     * fecha de referencia
     * 
     * @param festivos
     *        Listado de días festivos parametrizados
     * @param fechaReferencia
     *        Fecha de referencia para el cálculo
     * @return <b>Date</b> Fecha del último día hábil del mes
     */
    public static Date obtenerUltimoDiaHabilMes(List<DiasFestivos> festivos, Date fechaReferencia) {

        Date result = null;

        // se crea una fecha Calendar para el resultado previo a partir de la
        // fecha de referencia
        Calendar resultCal = Calendar.getInstance();
        resultCal.setTimeInMillis(fechaReferencia.getTime());

        // se toma el mes de la fecha de referencia
        int mesRef = resultCal.get(Calendar.MONTH);

        // se incrementa la fecha de referencia de a un día hábil hasta el
        // último día hábil del mes
        RECORRIDO_HABILES: while (true) {
            if (mesRef == resultCal.get(Calendar.MONTH)) {
                resultCal = modificarFecha(resultCal, 1, festivos);
            }
            else {
                resultCal = modificarFecha(resultCal, -1, festivos);
                break RECORRIDO_HABILES;
            }
        }

        result = resultCal.getTime();

        return result;
    }

    /**
     * Función para sustraer o adicionar días hábiles a una fecha
     * 
     * @param fecha
     *        Fecha a modificar
     * @param dias
     *        Cantidad de días hábiles a modificar
     * @param festivos
     *        Listado de los días festivos registrados en la BD
     * @return Fecha modificada
     */
    public static Calendar modificarFecha(Calendar fecha, Integer dias, List<DiasFestivos> festivos) {

        int incremento = 1;
        Integer diasTemporal = dias;
        GregorianCalendar fechaRespuesta = (GregorianCalendar) fecha.clone();

        if (dias < 0)
            incremento = -1;

        try {
            // se modifica la fecha día a día
            while (diasTemporal != 0) {
                // se modifica la fecha
                fechaRespuesta.add(Calendar.DAY_OF_MONTH, incremento);

                // se evalua la fecha modificada
                if (fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                        && fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                    // sí la fecha modificada no es un domingo, se le busca en
                    // la lista de festivos
                    if (!isFestivo(fechaRespuesta, festivos)) {
                        diasTemporal -= incremento;
                    }
                }
            }
        } catch (Exception e) {
        }
        return fechaRespuesta;
    }

    /**
     * Función para establecer sí una fecha está incluida en el listado de
     * festivos
     * 
     * @param fecha
     *        Fecha a buscar
     * @param festivos
     *        Listado de días festivos
     * @returns Fecha festivo true o false
     */
    public static boolean isFestivo(Calendar fecha, List<DiasFestivos> festivos) {
        for (DiasFestivos diaFestivo : festivos) {
            Calendar diaFest = CalendarUtil.toCalendar(diaFestivo.getFecha());
            Calendar fechaTemporal = fecha;
            diaFest = CalendarUtil.fomatearFechaSinHora(diaFest);
            fechaTemporal = CalendarUtil.fomatearFechaSinHora(fechaTemporal);

            if (diaFest.compareTo(fechaTemporal) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Función para realizar el redondeo
     * 
     * @param valor
     *        Valor a redondear
     * @param redondeo
     *        Divisor para el redondeo
     * @return Valor redondeado
     */
    public static BigDecimal redondearValor(BigDecimal valor, BigDecimal redondeo) {
        BigDecimal result = null;
        BigDecimal ajusteRedondeo = null;

        if (valor != null && redondeo != null) {

            if (valor.compareTo(new BigDecimal(0)) == 0) {
                result = new BigDecimal(0);
            }
            else {
                // se aplica el redondeo
                // primero se toma el residuo del aporte obligatorio calculado y
                // el redondeo
                ajusteRedondeo = valor.remainder(redondeo);

                // sí el residuo es cero, no se hace nada
                if (ajusteRedondeo.compareTo(new BigDecimal(0)) != 0) {
                    // se aproxima al valor de redondeo más cercano (Ejemplo:
                    // redondeo a múltiplo de 100
                    ajusteRedondeo = ajusteRedondeo.subtract(redondeo).negate();
                }
                // por último, se aplica el ajuste
                result = valor.add(ajusteRedondeo);
            }
        }

        return result;
    }

    /**
     * Función para obtener el valor de un campo del nombre de un archivo PILA
     * 
     * @param tipoArchivo
     *        Enumeración del tipo de archivo
     * @param campoSolicitado
     *        Enumeración del campo solicitado
     * @param nombreArchivo
     *        Nombre del archivo
     * @return Valor del campo solicitado o null
     */
    public static Object obtenerCampoNombreArchivo(TipoArchivoPilaEnum tipoArchivo, CamposNombreArchivoEnum campoSolicitado,
            String nombreArchivo) {
        Object result = null;
        String[] nombreSeparado = nombreArchivo.split("_");
        String[] fechaSeparada = null;
        try {
            if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(tipoArchivo)) {
                switch (campoSolicitado) {
                    case FECHA_RECAUDO_OF:
                        fechaSeparada = nombreSeparado[0].split("-");

                        int anio = Integer.parseInt(fechaSeparada[0]);
                        int mes = Integer.parseInt(fechaSeparada[1]) - 1;
                        int dia = Integer.parseInt(fechaSeparada[2]);

                        result = new GregorianCalendar(anio, mes, dia);
                        break;
                    case CODIGO_ADMINISTRADORA_OF:
                        result = nombreSeparado[1];
                        break;
                    case CODIGO_BANCO_OF:
                        result = nombreSeparado[2];
                        break;
                    case TIPO_ARCHIVO_OF:
                        result = nombreSeparado[3];
                        break;
                    default:
                        break;
                }
            }
            else {
                // casos de Operador de información
                switch (campoSolicitado) {
                    case FECHA_PAGO_OI:
                        fechaSeparada = nombreSeparado[0].split("-");

                        int anio = Integer.parseInt(fechaSeparada[0]);
                        int mes = Integer.parseInt(fechaSeparada[1]) - 1;
                        int dia = Integer.parseInt(fechaSeparada[2]);

                        result = new GregorianCalendar(anio, mes, dia);
                        break;
                    case MODALIDAD_PLANILLA_OI:
                        result = nombreSeparado[1];
                        break;
                    case NUMERO_PLANILLA_OI:
                        result = Long.parseLong(nombreSeparado[2]);
                        break;
                    case TIPO_DOCUMENTO_APORTANTE_OI:
                        result = nombreSeparado[3];
                        break;
                    case IDENTIFICACION_APORTANTE_OI:
                        result = nombreSeparado[4];
                        break;
                    case CODIGO_ADMINISTRADORA_OI:
                        result = nombreSeparado[5];
                        break;
                    case CODIGO_OPERADOR_OI:
                        result = nombreSeparado[6];
                        break;
                    case TIPO_ARCHIVO_OI:
                        result = nombreSeparado[7];
                        break;
                    case PERIODO_PAGO_OI:
                        result = nombreSeparado[8].split("\\.")[0];
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    /**
     * Función para validar el formato de un número de identificación
     * 
     * @param id
     *        Número de identificación a validar
     * @param tipoId
     *        Tipo de ID
     * @return Mensaje de error o null
     */
    public static String validarNumeroId(String id, TipoIdentificacionEnum tipoId) {

        String result = null;
        logger.info("Jamv - Valida tipo documento");
        if (tipoId != null) {
            switch (tipoId) {
                case NIT:
                    // La longitud debe ser menor a 11 caracteres (Ajustes4)
                    if (id.length() > 11) {
                        result = "La longitud del campo no corresponde a la de un " + tipoId.getDescripcion();
                    }
                    break;
                case CEDULA_CIUDADANIA:
                    // La longitud no puede ser mayor de 10 dígitos
                    if (id.length() > 10) {
                        result = "La longitud del campo no corresponde a la de una " + tipoId.getDescripcion();
                    }
                    break;
                case PASAPORTE:
                    // La longitud no puede ser mayor de 16 caracteres
                    if (id.length() > 16) {
                        result = "La longitud del campo no corresponde a la de un " + tipoId.getDescripcion();
                    }
                    break;
                case REGISTRO_CIVIL:
                    // La longitud es de 8, 10 o 11 caracteres
                    if (id.length() != 8 && id.length() != 10 && id.length() != 11) {
                        result = "La longitud del campo no corresponde a la de un " + tipoId.getDescripcion();
                    }
                    break;
                case TARJETA_IDENTIDAD:
                    // Longitud tarjeta de identidad solo puede ser de 10 u 11
                    // dígitos.
                    if (id.length() != 10 && id.length() != 11) {
                        result = "La longitud del campo no corresponde a la de una " + tipoId.getDescripcion();
                    }
                    break;
                case CEDULA_EXTRANJERIA:
                    // Máximo de 7 caracteres
                    if (id.length() > 16) {
                        result = "La longitud del campo no corresponde a la de una " + tipoId.getDescripcion();
                    }
                    break;
                case CARNE_DIPLOMATICO:
                    // La longitud es de 1 u 11 caracteres
                    if (id.length() > 15) {//if (id.length() != 1 && id.length() != 11) {
                        result = "La longitud del campo no corresponde al de un " + tipoId.getDescripcion();
                    }/*
                    else {
                        /*
                         * La letra que antecede el número dependerá de la
                         * asignación que el Sistema de Protocolo le haga al
                         * funcionario: O: funcionario Org. Internacional D:
                         * Diplomático C: Consular A: Administrativos S: Servicio
                         * Doméstico
                         

                        String primeraLetra = id.substring(0, 1);
                        switch (primeraLetra) {
                            case "O":
                            case "D":
                            case "C":
                            case "A":
                            case "S":
                                break;
                            default:
                                result = "El primer caracter del documento no es válido para el tipo " + tipoId.getDescripcion();
                                break;b
                        }
                    }*/
                    break;
                case PERM_PROT_TEMPORAL:
                    // Máximo de 8 caracteres
                    logger.info("Jamv - INGRESA A PERMISO PROTECCION TEMPORAL");
                    if (id.length() > 8) {
                        result = "La longitud del campo no corresponde a la de un " + tipoId.getDescripcion();
                    }
                    break;
                case PERM_ESP_PERMANENCIA:
                    // La longitud no puede ser mayor de 15 caracteres
                    logger.info("Jamv - INGRESA A PERMISO ESPECIAL DE PERMANENCIA");
                    if (id.length() > 15 || id.length() < 15) {
                        result = "La longitud del campo no corresponde a la de un " + tipoId.getDescripcion();
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            result = "No se cuenta con un tipo de identificación para validar";
        }
        return result;
    }

    /**
     * Función para establecer sí un caso de normatividad para establecer la
     * fecha de vencimiento de una planilla se ajusta a los datos de una lectura
     * en curso
     * 
     * @param entrada
     *        Entrada de normatividad comparada
     * @param tipoArchivo
     *        Tipo del archivo validado
     * @param cantidadTrabajadoresOPensionados
     *        Cantidad de los trabajadores o pensionados reportados en la
     *        planilla
     * @param claseAportante
     *        Clase del aportante de la planilla
     * @return <b>NormatividadFechaVencimiento</b> Devuelve la misma entrada en
     *         el caso de que sea válida para su uso en el cálculo de la fecha
     *         de vencimiento, null en caso contrario
     */
    public static NormatividadFechaVencimiento verificarClasificacion(NormatividadFechaVencimiento entrada, TipoArchivoPilaEnum tipoArchivo,
            Integer cantidadTrabajadoresOPensionados, String claseAportante) {
        // de la enumeración de tipo de archivo, se infiere el tipo general para
        // consultar la clasificación para el día de vencimiento
        String tipoArchivoClasificacion = "";
        if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivo.getSubtipo())
                && GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivo.getGrupo())) {
            tipoArchivoClasificacion = "I";

        }
        else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivo.getSubtipo())
                && GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivo.getGrupo())) {

            tipoArchivoClasificacion = "IP";
        }

        if (entrada.getClasificacion().getTipoArchivo().equals(tipoArchivoClasificacion)) {
            String valorComparacion = null;

            if (entrada.getClasificacion().getCampo() != null && entrada.getClasificacion().getCampo().compareTo((short) 26) == 0) {
                valorComparacion = Integer.toString(cantidadTrabajadoresOPensionados);
            }
            else if (entrada.getClasificacion().getCampo() != null && entrada.getClasificacion().getCampo().compareTo((short) 35) == 0) {
                valorComparacion = claseAportante;
            }

            // indicador para establecer que el aportante cumple con la
            // clasificación
            boolean cumple = false;

            try {
                switch (entrada.getClasificacion().getComparacion()) {
                    case IGUAL:
                        if (entrada.getClasificacion().getValor().equals(valorComparacion))
                            cumple = true;
                        break;
                    case MAYOR:
                        if (Integer.parseInt(valorComparacion) > Integer.parseInt(entrada.getClasificacion().getValor())) {
                            cumple = true;
                        }
                        break;
                    case MAYOR_IGUAL:
                        if (Integer.parseInt(valorComparacion) >= Integer.parseInt(entrada.getClasificacion().getValor())) {
                            cumple = true;
                        }
                        break;
                    case MENOR:
                        if (Integer.parseInt(valorComparacion) < Integer.parseInt(entrada.getClasificacion().getValor())) {
                            cumple = true;
                        }
                        break;
                    case MENOR_IGUAL:
                        if (Integer.parseInt(valorComparacion) <= Integer.parseInt(entrada.getClasificacion().getValor())) {
                            cumple = true;
                        }
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException | NullPointerException e) {
            }

            // en el caso de que la entrada sea la indicada para el registro que
            // se está revisando,
            // se cierra el ciclo para ir al paso siguiente
            if (cumple) {
                return entrada;
            }
        }

        return null;
    }

    /**
     * Función para convertir una fecha en cadena de texto con formato
     * YYYY-MM-DD a Date
     * 
     * @param fechaCadena
     *        Cadena de texto con la fecha a convertir
     * @return Fecha en formato Date o null
     */
    public static Date convertirDate(String fechaCadena) {
        Date result = null;
        try {
            int anio = Integer.parseInt(fechaCadena.split("-")[0]);
            int mes = Integer.parseInt(fechaCadena.split("-")[1]) - 1;
            int dia = Integer.parseInt(fechaCadena.split("-")[2]);

            result = new GregorianCalendar(anio, mes, dia).getTime();
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * Método para obtener el tipo de archivo leido a partir del nombre del
     * archivo
     * 
     * @param nombreArchivo
     *        <code>String</code> El nombre de archivo a evaluar
     * @return <code>TipoArchivoPilaEnum</code> enumeracion que indica los tipos
     *         de archivo esperados en el proceso de carga PILA.
     */
    public static TipoArchivoPilaEnum getTipoArchivo(String nombreArchivo) {
        TipoArchivoPilaEnum tipoArchivoPilaEnum = null;
        try {
            String[] nombreSeparado = nombreArchivo.split("\\.")[0].split("_");
            String tipoArchivo = null;

            // de acuerdo a la cantidad de campos se define el tipo del archivo
            // (OI 9 campos - OF 4 campos)
            if (nombreSeparado.length == 9) {
                tipoArchivo = nombreSeparado[7];
            }
            else if (nombreSeparado.length == 4) {
                tipoArchivo = nombreSeparado[3];
            }

            // una vez se lee el campo, se retorna la enumeración que le
            // corresponde
            if (tipoArchivo != null && !tipoArchivo.isEmpty()) {
                tipoArchivoPilaEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);
            }
        } catch (Exception e) {
        }
        
        return tipoArchivoPilaEnum;
    }

    /**
     * Función para calcular el valor del dígito de verificación de un ID
     * 
     * @param numeroIdentificacion
     *        ID para el cálculo del dígito de verificación
     * @return <b>Short</b> Dígito de verificación calculado
     */
    public static Short calcularDigitoVarificacion(String numeroIdentificacion) {
        Short result = null;

        if (numeroIdentificacion != null) {
            // lo primero que se debe hacer es lograr que la cadena del ID tenga
            // una longitud de 15 caracteres
            String idTemp = numeroIdentificacion;
            while (idTemp.length() < 15) {
                idTemp = "0" + idTemp;
            }

            // se establece el arreglo con los números primos incluidos en la
            // fórmula de la DIAN
            Integer[] primos = { 71, 67, 59, 53, 47, 43, 41, 37, 29, 23, 19, 17, 13, 7, 3 };

            // se hace una sumaroria del valor de cada caracter multiplicado por
            // uno de los números primos en el arreglo
            Integer sumatoria = 0;
            for (int i = 0; i < primos.length; i++) {
                try {
                    sumatoria += Integer.parseInt("" + idTemp.charAt(i)) * primos[i];
                } catch (NumberFormatException e) {
                    sumatoria = -1;
                    break;
                }
            }

            // una vez se tiene la sumatoria, se obtiene el residuo de la
            // sumatoria dividida entre 11
            Integer residuo = sumatoria % 11;

            // finalmente, se evalua el valor del residuo para definir el valor
            // del DV
            if (residuo == 0) {
                result = (short) 0;
            }
            else if (residuo == 1) {
                result = (short) 1;
            }
            else {
                result = (short) (11 - residuo);
            }
        }

        return result;
    }

    /**
     * Método para convertir un mensaje de error funcional de validación en los
     * campos designados para el DTO de errores de validación
     * 
     * @param errorDTO
     *        DTO de error funcional de validación en el que se desea llenar
     *        la información
     * @param mensajeError
     *        Mensaje que será procesado
     * @param ErrorDetalladoValidadorDTO
     *        DTO actualizado
     */
    public static ErrorDetalladoValidadorDTO descomponerMensajeErrorValidacion(ErrorDetalladoValidadorDTO errorDTO, String mensaje) {
        // se toman el ID del campo, tipo de error, código del error y mensaje
        // final del mensaje recibido de la excepción
        String[] mensajeSeparado = mensaje.split(ConstantesComunesProcesamientoPILA.SEPARADOR_MENSAJE_ERROR);

        // se hace tratamiento del mensaje fragmentado de acuerdo a la cantidad
        // de fragmentos
        switch (mensajeSeparado.length) {
            case 3:
                // el mensaje contiene un código de error, tipo de error y mensaje
                errorDTO.setCodigoError(limpiarCodigoError(mensajeSeparado[0]));

                // si el DTO ya cuenta con un tipo de error, se conserva
                if (errorDTO.getTipoError() == null)
                    errorDTO.setTipoError(TipoErrorValidacionEnum.obtenerTipoError(mensajeSeparado[1]));

                errorDTO.setMessage(mensajeSeparado[2]);
                break;
            case 5:
                // el mensaje contiene un código de error, código de campo, valor de
                // campo, tipo de error y mensaje
                errorDTO.setCodigoError(limpiarCodigoError(mensajeSeparado[0]));

                errorDTO.setIdCampoError(mensajeSeparado[1]);

                errorDTO.setValorCampo(mensajeSeparado[2]);

                // si el DTO ya cuenta con un tipo de error, se conserva
                if (errorDTO.getTipoError() == null)
                    errorDTO.setTipoError(TipoErrorValidacionEnum.obtenerTipoError(mensajeSeparado[3]));

                errorDTO.setMessage(mensajeSeparado[4]);
                break;
            case 6:
                // el mensaje contiene un código de error, código de campo, valor de
                // campo, número de línea, tipo de error y mensaje
                errorDTO.setCodigoError(limpiarCodigoError(mensajeSeparado[0]));

                errorDTO.setIdCampoError(mensajeSeparado[1]);

                errorDTO.setValorCampo(mensajeSeparado[2]);

                // si el DTO ya cuenta con un tipo de error, se conserva
                if (errorDTO.getTipoError() == null)
                    errorDTO.setTipoError(TipoErrorValidacionEnum.obtenerTipoError(mensajeSeparado[3]));

                errorDTO.setLineNumber(StringUtils.isNumeric(mensajeSeparado[4]) ? Long.parseLong(mensajeSeparado[4]) : null);
                errorDTO.setMessage(mensajeSeparado[5]);
                break;
            default:
                errorDTO.setMessage(mensaje);
                break;

        }

        return errorDTO;
    }

    /**
     * Método para eliminar texto adicional en el código de un error
     * 
     * @param codigoError
     *        Código de error como se recibe de la excepción
     * @return <String> Código de error formateado
     */
    private static String limpiarCodigoError(String codigoError) {
        String result = codigoError;

        // se quita el texto innecesario del código del error
        String[] codigoErrorDetallado = codigoError.split(":");
        if (codigoErrorDetallado.length == 1) {
            // el mensaje tiene un código de error limpio
            result = codigoError;
        }
        else if (codigoErrorDetallado.length == 3) {
            // el mensaje tiene un código de error interpolado del tipo
            // {codigoError}: {mensajeError}
            result = codigoErrorDetallado[1];
        }
        else if (codigoErrorDetallado.length == 2) {
            // el mensaje tiene un código de error interpolado del tipo
            // {codigoError}: {mensajeError}
            result = codigoErrorDetallado[0].trim();
        }

        return result;
    }

    /**
     * Método para determina la acción que se debe seguir con base en un estado
     * recibido
     * 
     * @param estado
     *        Estado de bloque para determinar acción
     * @param tipoArchivo
     *        Tipo de archivo que se está procesando
     * @return AccionProcesoArchivoEnum Acción a ejecutar con base en el estado
     */
    public static AccionProcesoArchivoEnum determinarAccion(EstadoProcesoArchivoEnum estado, TipoArchivoPilaEnum tipoArchivo) {
        AccionProcesoArchivoEnum accion = null;

        // los estados marcados con reportarBandejaInconsistencias = true
        // retornan la acción correspondiente
        if (estado != null && estado.getReportarBandejaInconsistencias()) {
            accion = AccionProcesoArchivoEnum.PASAR_A_BANDEJA;
        }
        else if (estado != null) {
            // para los demás estados, la acción está asociada a cada caso
            switch (estado) {
                case DESCARGADO:
                    accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_0;
                    break;
                case CARGADO:
                case CARGADO_REPROCESO:
                case CARGADO_REPROCESO_ACTUAL:
                case CARGADO_EXITOSAMENTE:
                    accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_1;
                    break;
                case EN_PROCESO:
                    accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_2;
                    break;
                case ESTRUCTURA_VALIDADA:
                    if (GrupoArchivoPilaEnum.OPERADOR_FINANCIERO.equals(tipoArchivo.getGrupo())) {
                        accion = AccionProcesoArchivoEnum.EN_ESPERA;
                    }
                    else {
                        accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_3;
                    }
                    break;
                case PAREJA_DE_ARCHIVOS_CONSISTENTES:
                    accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_4;
                    break;
                case PERSISTENCIA_ARCHIVO_COMPLETADA:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivo.getSubtipo())) {
                        accion = AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS;
                    }
                    else if (tipoArchivo != null) {
                        accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_5;
                    }
                    break;
                case ARCHIVO_CONSISTENTE:
                case APROBADO:
                    accion = AccionProcesoArchivoEnum.EJECUTAR_BLOQUE_6;
                    break;
                case RECAUDO_CONCILIADO:
                case RECAUDO_VALOR_CERO_CONCILIADO:
                    accion = AccionProcesoArchivoEnum.PASAR_A_CRUCE_CON_BD;
                    break;
                case PERSISTENCIA_ARCHIVO_FALLIDA:
                case VARIABLE_CRITICA_FUERA_DE_CONTEXTO:
                    accion = AccionProcesoArchivoEnum.REINTENTAR_BLOQUE;
                    break;
                case PAREJA_DE_ARCHIVOS_EN_ESPERA:
                case PENDIENTE_CONCILIACION:
                case ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION:
                case GESTIONAR_DIFERENCIA_EN_CONCILIACION:
                    accion = AccionProcesoArchivoEnum.EN_ESPERA;
                    break;
                case ARCHIVO_FINANCIERO_CONCILIADO:
                    accion = AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS;
                    break;
                default:
                    break;
            }
        }

        return accion;
    }

    /**
     * Método encargado de agregar un error a un DTO de respuesta de validación
     * 
     * @param respuestaTemp
     *        DTO de respuesta de validación que será actualizada o creada
     * @param indicePlanilla
     *        Entrada de índice de planilla OI u OF al cual se asocia la
     *        respuesta, en el caso de que la respuesta ya tenga designado
     *        un índice, este parámetro no será utilizado. A través de este
     *        parámetro, se puede informar el tipo de archivo en forma
     *        directa
     * @param bloque
     *        Bloque de validación en el cual se presenta el error
     * @param mensaje
     *        Mensaje de error
     * @param tipoError
     *        Tipo de error que será agregado
     * @param numeroLinea
     *        Número de la línea del error
     * @return <b>RespuestaValidacionDTO</b> DTO de respuesta de validación
     *         actualizado
     */
    public static RespuestaValidacionDTO agregarError(RespuestaValidacionDTO respuesta, Object indicePlanilla, BloqueValidacionEnum bloque,
            String mensaje, TipoErrorValidacionEnum tipoError, Long numeroLinea) {
        RespuestaValidacionDTO respuestaTemp = respuesta;

        // sí no se tiene un DTO de respuesta, se instancia
        if (respuestaTemp == null) {
            respuestaTemp = new RespuestaValidacionDTO();
        }

        ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();

        // sí no se tiene un índice de planilla correspondiente al tipo
        // recibido, se le agrega a la respuesta
		if (indicePlanilla != null && indicePlanilla instanceof IndicePlanilla) {
			if (respuestaTemp.getIndicePlanilla() == null) {
				respuestaTemp.setIndicePlanilla((IndicePlanilla) indicePlanilla);
			}
			error.setTipoArchivo(((IndicePlanilla) indicePlanilla).getTipoArchivo());
		}
        else if (indicePlanilla != null && indicePlanilla instanceof IndicePlanillaOF) {
            if(respuestaTemp.getIndicePlanillaOF() == null) {
            	respuestaTemp.setIndicePlanillaOF((IndicePlanillaOF) indicePlanilla);
            }
            error.setTipoArchivo(((IndicePlanillaOF) indicePlanilla).getTipoArchivo());
        }
        else if (indicePlanilla instanceof TipoArchivoPilaEnum) {
            error.setTipoArchivo((TipoArchivoPilaEnum) indicePlanilla);
        }
        error.setBloque(bloque);
        error.setTipoError(tipoError);

        error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

        if (numeroLinea != null && error.getLineNumber() == null) {
            error.setLineNumber(numeroLinea);
        }

        // se agrega el error a la respuesta
        respuestaTemp.addErrorDetalladoValidadorDTO(error);
        return respuestaTemp;
    }

    /**
     * Método encargado de preparar el DTO de error para agregar al listado a
     * partir de un mensaje de error
     * 
     * @param message
     *        Mensaje de error
     * @param bloque
     *        Bloque de validación en el que se da el error
     * @param lineNumber
     *        Número de la línea en la que se presenta el error
     * @return <b>ErrorDetalladoValidadorDTO</b> DTO con la información de la
     *         inconsistencia presentada
     */
    public static ErrorDetalladoValidadorDTO prepararError(String message, BloqueValidacionEnum bloque, Long lineNumber) {
        ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();

        // se toman el tipo de error, código del error y mensaje final del
        // mensaje recibido de la excepción
        FuncionesValidador.descomponerMensajeErrorValidacion(error, message);

        // sí el tipo de error no está definido, se debe a un error en la
        // estructura del archivo, en este caso
        // se debe agregar el tipo de error de acuerdo al bloque de validación
        // en el que se generó
        if (error.getTipoError() == null && bloque != null) {
            switch (bloque) {
                case BLOQUE_1_OF:
                case BLOQUE_2_OI:
                    error.setTipoError(TipoErrorValidacionEnum.TIPO_2);
                    break;
                case BLOQUE_4_OI:
                    error.setTipoError(TipoErrorValidacionEnum.TIPO_1);
                    break;
                default:
                    break;
            }

        }

        error.setBloque(bloque);

        if (lineNumber != null && error.getLineNumber() == null) {
            error.setLineNumber(lineNumber);
        }
        return error;
    }

    /**
     * Método para obtener un nombre de campo en archivo PILA a partir de un
     * tipo de archivo, un tipo de registro y un número de campo
     * 
     * @param tipoArchivo
     *        Tipo de archivo consultado
     * @param registro
     *        Tipo de registro consultado
     * @param campo
     *        Número de campo solicitado
     * @return <b>String[]</b> Nombre y código del campo en el mapa de valores
     *         del componente FileProcessor
     */
    public static String[] getNombreCampo(TipoArchivoPilaEnum tipoArchivo, String registro, String campo) {
        String[] result = new String[2];

        try {
            if (tipoArchivo != null) {
                switch (tipoArchivo) {
                    case ARCHIVO_OF:
                        EtiquetaArchivoFEnum campoF = EtiquetaArchivoFEnum.obtenerEtiqueta(Integer.parseInt(registro),
                                Integer.parseInt(campo));
                        result[0] = campoF.toString();
                        result[1] = campoF.getNombreCampo();
                        break;
                    case ARCHIVO_OI_A:
                    case ARCHIVO_OI_AR:
                        EtiquetaArchivoAEnum campoA = EtiquetaArchivoAEnum.obtenerEtiqueta(Integer.parseInt(campo));
                        result[0] = campoA.toString();
                        result[1] = campoA.getNombreCampo();
                        break;
                    case ARCHIVO_OI_AP:
                    case ARCHIVO_OI_APR:
                        EtiquetaArchivoAPEnum campoAP = EtiquetaArchivoAPEnum.obtenerEtiqueta(Integer.parseInt(campo));
                        result[0] = campoAP.toString();
                        result[1] = campoAP.getNombreCampo();
                        break;
                    case ARCHIVO_OI_I:
                    case ARCHIVO_OI_IR:
                        EtiquetaArchivoIEnum campoI = EtiquetaArchivoIEnum.obtenerEtiqueta(registro, Integer.parseInt(campo));
                        result[0] = campoI.toString();
                        result[1] = campoI.getNombreCampo();
                        break;
                    case ARCHIVO_OI_IP:
                    case ARCHIVO_OI_IPR:
                        EtiquetaArchivoIPEnum campoIP = EtiquetaArchivoIPEnum.obtenerEtiqueta(Integer.parseInt(registro),
                                Integer.parseInt(campo));
                        result[0] = campoIP.toString();
                        result[1] = campoIP.getNombreCampo();
                        break;
                    default:
                        result = null;
                        break;
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Método para obtener el ID de lectura de archivo desde los parámetros de
     * la aplicación
     * 
     * @param perfilArchivo
     *        Enumeraciónd el perfil de lectura solicitado
     * @return <b>Long</b> ID de lectura del tipo de archivo
     */
    public static Long getIdPerfilLectura(PerfilLecturaPilaEnum perfilArchivo) {
        Long idLectura = null;
        String llave = null;

        switch (perfilArchivo) {
            case ARCHIVO_FINANCIERO:
                llave = ConstantesSistemaConstants.PILA_ARCHIVO_FINANCIERO;
                break;
            case DETALLE_INDEPENDIENTE_DEPENDIENTE:
                llave = ConstantesSistemaConstants.PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE;
                break;
            case DETALLE_PENSIONADO:
                llave = ConstantesSistemaConstants.PILA_DETALLE_PENSIONADO;
                break;
            case INFORMACION_INDEPENDIENTE_DEPENDIENTE:
                llave = ConstantesSistemaConstants.PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE;
                break;
            case INFORMACION_PENSIONADO:
                llave = ConstantesSistemaConstants.PILA_INFORMACION_PENSIONADO;
                break;
        }

        if (llave != null) {
            idLectura = Long.parseLong((String) valoresGeneralesValidacion.get(llave));
        }
        return idLectura;
    }

    /**
     * Metodo que toma una fecha tipo Date o Calendar y la convierte a String
     * 
     * @param fecha
     *        Fecha a convertir
     * @return <b>String</b> Fecha convertida
     */
    public static String formatoFecha(Object fecha) {
        String result = null;

        Calendar fechaCal = null;

        if (fecha instanceof Date) {
            fechaCal = Calendar.getInstance();
            fechaCal.setTimeInMillis(((Date) fecha).getTime());
        }
        else if (fecha instanceof Calendar) {
            fechaCal = (Calendar) fecha;
        }

        if (fechaCal != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaLD = LocalDate.of(fechaCal.get(Calendar.YEAR), fechaCal.get(Calendar.MONTH) + 1,
                    fechaCal.get(Calendar.DAY_OF_MONTH));

            result = formatter.format(fechaLD);
        }
        return result;
    }

    /**
     * Metodo que toma una fecha en milisegundos y la convierte a String
     * 
     * @param fecha
     *        Fecha a convertir
     * @return <b>String</b> Fecha convertida
     */
    public static String formatoFechaMilis(Long fecha) {
        String result = null;
        
        LocalDate fechaLD = Instant.ofEpochMilli(fecha).atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        result = formatter.format(fechaLD);
        return result;
    }

    /**
     * Método para comparar fechas al segundo
     * 
     * @param fechaA
     * @param fechaB
     * @return <b>Integer</b> Indica que la fecha A es mayor (1), igual (0) o
     *         menor (-1) que la fecha B
     */
    public static Integer compararFechas(Calendar fechaA, Calendar fechaB) {
        Long segundosA = fechaA.getTimeInMillis() / 1000;
        Long segundosB = fechaB.getTimeInMillis() / 1000;
        Long resultado = segundosA - segundosB;

        if (resultado == 0) {
            return 0;
        }
        else if (resultado < 0) {
            return -1;
        }
        else {
            return 1;
        }
    }

    /**
     * Método para comparar fechas en String al milisegundo
     * 
     * @param fechaA
     * @param fechaB
     * @return <b>Integer</b> Indica que la fecha A es mayor (1), igual (0) o
     *         menor (-1) que la fecha B
     */
    public static Integer compararFechasString(String fechaA, String fechaB) {
    	try{
    		String[] fecha = fechaA.split("-");
        	Integer anio = Integer.parseInt(fecha[0]);
        	Integer mes = Integer.parseInt(fecha[1]);
        	Integer dia = Integer.parseInt(fecha[2]);
        	
        	LocalDate localDateA = LocalDate.of(anio, mes, dia);

    		fecha = fechaB.split("-");
        	anio = Integer.parseInt(fecha[0]);
        	mes = Integer.parseInt(fecha[1]);
        	dia = Integer.parseInt(fecha[2]);
        	
        	LocalDate localDateB = LocalDate.of(anio, mes, dia);
        	
        	return localDateA.compareTo(localDateB);
    	}catch(Exception e){
    		return null;
    	}
    }

    /**
     * Función para escoger un caso específico de normatividad para establecer
     * la fecha de vencimiento de una planilla
     * 
     * @param entradasNormatividad
     * @param idAportante
     * @param periodoAporte
     * @param tipoArchivo
     * @param cantidadPersonas
     * @param claseAportante
     * @param naturaleza
     * @return NormatividadFechaVencimiento
     */
    public static NormatividadFechaVencimiento elegirNormatividad(List<NormatividadFechaVencimiento> entradasNormatividad,
            String idAportante, String periodoAporte, TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, String claseAportante,
            NaturalezaJuridicaEnum naturaleza, Date fechaAporte) {
        String firmaMetodo = "FuncionesValidador.elegirNormatividad(List<NormatividadFechaVencimiento>, String, String, TipoArchivoPilaEnum, "
                + "Integer, String)";
        String mensaje = null;

        NormatividadFechaVencimiento result = null;

        // se recorren las entradas de normatividad para establecer el día de
        // vencimiento correspondiente
        for (NormatividadFechaVencimiento entrada : entradasNormatividad) {
            // en primer lugar se debe establecer que el período de aporte
            // corresponde al caso
            if (esPeriodoValido(periodoAporte, entrada.getPeriodoInicial(), entrada.getPeriodoFinal())) {
                String[] valoresDigitoFinal = null;
                String fragmentoId = null;

                if (entrada.getUltimoDigitoId() != null) {
                    valoresDigitoFinal = entrada.getUltimoDigitoId().split(",");
                    int cantidadDigitos = 0;

                    for (String digito : valoresDigitoFinal) {
                        if (!digito.contains("-")) {
                            // en este caso, la comparación se hace frente a un
                            // valor específico

                            cantidadDigitos = digito.length();

                            fragmentoId = idAportante.substring(idAportante.length() - cantidadDigitos, idAportante.length());
                            if (fragmentoId.equals(digito) && entrada.getClasificacion() != null) {
                                // sí el dígito concuerda, se debe validar la
                                // clasificación
                                result = FuncionesValidador.verificarClasificacion(entrada, tipoArchivo, cantidadPersonas, claseAportante);
                            }
                            // sí dígito concuerda, pero no se cuenta con
                            // clasificación, significa que es un caso general
                            else if (fragmentoId.equals(digito) && entrada.getClasificacion() == null) {
                                result = entrada;
                            }
                        }
                        else {
                            // en este caso, el valor que se comprueba es un
                            // rango en lugar de un valor específico
                            String valorMinimoRango = null;
                            String valorMaximoRango = null;

                            try {
                                valorMinimoRango = digito.split("-")[0];
                                valorMaximoRango = digito.split("-")[1];
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }

                            if (valorMinimoRango != null && valorMaximoRango != null) {
                                valorMinimoRango = valorMinimoRango.trim();
                                valorMaximoRango = valorMaximoRango.trim();
                                idAportante = idAportante.trim();

                                cantidadDigitos = valorMinimoRango.length();
                                if (idAportante.length() < cantidadDigitos) {
                                    cantidadDigitos = idAportante.length();
                                }
                                fragmentoId = idAportante.substring(idAportante.length() - cantidadDigitos, idAportante.length());

                                // se compara el fragmento del ID entre los
                                // valores mínimo y máximo
                                if (fragmentoId.compareTo(valorMinimoRango) >= 0 && fragmentoId.compareTo(valorMaximoRango) <= 0) {
                                    // sí se encuentra entre el rango de valor,
                                    // se valida la clasificación

                                    // cuando la entrada de normatividad
                                    // seleccionada no presenta clasificación,
                                    // se toma la entrada directamente

                                    if (entrada.getClasificacion() == null) {
                                    	
                                    	if(fechaAporte != null && entrada.getFechaPagoInicio()!=null && entrada.getFechaPagoFin()!= null) {
                                    		if(fechaAporte.compareTo(entrada.getFechaPagoInicio())>=0 && fechaAporte.compareTo(entrada.getFechaPagoFin())<=0) {
                                    			result = entrada;
                                    		}
                                    	}else {
                                    		result = entrada;
                                    	}
                                    	
                                    }
                                    else {
                                        result = FuncionesValidador.verificarClasificacion(entrada, tipoArchivo, cantidadPersonas,
                                                claseAportante);
                                    }
                                }
                            }
                            else {
                                // error por falta de datos de rango
                                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS_SIMPLE
                                        .getReadableMessage(ConstantesComunesProcesamientoPILA.RANGO_DIGITO_FINAL_ID);

                                logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                                throw new TechnicalException(mensaje);
                            }
                        }
                    }
                }
                else if (entrada.getClasificacion() == null) {
                    // corresponde al caso 1, Tabla 1, Hoja 9, Anexo 2.1.1
                    result = entrada;
                }
                else if (NaturalezaJuridicaEnum.PUBLICA.equals(naturaleza)) {
                    // corresponde al caso 106, Tabla 1, Hoja 9, Anexo 2.1.1
                    // (versión febrero 2018)
                    result = FuncionesValidador.verificarClasificacion(entrada, tipoArchivo, cantidadPersonas, claseAportante);
                }
            }

            if (result != null)
                break;
        }
        return result;
    }

    /**
     * Función para determinar sí el período de aporte coincide con el rango de
     * una entrada de normatividad
     * 
     * @param periodoAporte
     *        Período de aporte
     * @param periodoInicio
     *        Período inicial de la entrada de normatividad
     * @param periodoFinal
     *        Período final de la entrada de normatividad
     * @return <b>Boolean</b> Verdadero en caso de que el período de aporte sea
     *         válido para el caso de normatividad
     */
    public static Boolean esPeriodoValido(String periodoAporte, String periodoInicio, String periodoFinal) {
        Boolean result = false;

        // se debe contar con período de aporte y período inicial de
        // normatividad
        if (periodoAporte != null && periodoInicio != null) {
            // primero se evalua el caso cuando se cuenta con período final
            if (periodoFinal != null) {
                if (esPeriodoMayor(periodoAporte, periodoInicio) && esPeriodoMayor(periodoFinal, periodoAporte)) {
                    result = true;
                }
            }
            else {
                if (esPeriodoMayor(periodoAporte, periodoInicio)) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Función para establecer sí un período A es mayor a un periodo B
     * 
     * @param periodoA
     *        Primer período de comparación
     * @param periodoB
     *        Segundo período de comparación
     * @return <b>Boolean</b> Verdadero en caso de ser mayor, falso en caso
     *         contrario. Retorna nulo en caso de recibir parámetros nulos o de
     *         formato incorrecto
     * @throws FileProcessingException
     */
    public static Boolean esPeriodoMayor(String periodoA, String periodoB) {
        Boolean result = null;
        if (periodoA != null && periodoB != null) {
            try {
                // se fragmentan los períodos en sus componentes
                Integer anioA = Integer.parseInt(periodoA.split("-")[0]);
                Integer mesA = Integer.parseInt(periodoA.split("-")[1]);

                Integer anioB = Integer.parseInt(periodoB.split("-")[0]);
                Integer mesB = Integer.parseInt(periodoB.split("-")[1]);

                // se comparan los períodos fragmentados
                result = false;

                // se empieza con el año o en el caso que el año es igual, se
                // comparan los meses
                if ((anioA.compareTo(anioB) > 0) || (anioA.compareTo(anioB) == 0 && mesA.compareTo(mesB) >= 0)) {
                    result = true;
                }
            } catch (Exception e) {
                String mensaje = MensajesValidacionEnum.ERROR_CALCULO_SIMPLE
                        .getReadableMessage(ConstantesComunesProcesamientoPILA.PERIODO_MAYOR);
                throw new TechnicalException(mensaje);
            }
        }
        return result;
    }

    /**
     * Función para calcular la fecha de vencimiento
     * 
     * @param casoEspecificoNorma
     * @param oportunidad
     * @param periodoAporte
     * @param festivos
     * @return fecha calculada
     * @throws FileProcessingException
     */
    public static Calendar calcularFechaVencimiento(String periodoAporte, PeriodoPagoPlanillaEnum oportunidad,
            NormatividadFechaVencimiento casoEspecificoNorma, List<DiasFestivos> festivos) {
        String firmaMetodo = "FuncionesValidador.calcularFechaVencimiento(String, PeriodoPagoPlanillaEnum, NormatividadFechaVencimiento, List<>)";
        String mensaje = null;

        int anioVen = Integer.parseInt(periodoAporte.split("-")[0]);
        int mesVen = Integer.parseInt(periodoAporte.split("-")[1]) - 1;
        int diaVen = 1;

        Calendar fechaVencimiento = null;

        try {
            // se crea la forma base de la fecha de vencimiento a partir del
            // período del aporte
            fechaVencimiento = new GregorianCalendar(anioVen, mesVen, diaVen);

            fechaVencimiento = CalendarUtil.fomatearFechaSinHora(fechaVencimiento);

            // de acuerdo a la oportunidad de pago de planilla, se modifica la
            // fecha para marcar el mes vencido
            if (PeriodoPagoPlanillaEnum.MES_VENCIDO.equals(oportunidad)) {
                fechaVencimiento.add(Calendar.MONTH, 1);
            }

			if (casoEspecificoNorma.getTipoFecha() != null) {
	            // se evalua el tipo de fecha definido en el caso de normatividad
	            // correspondiente
	            // según sea el caso, se especifica el día de vencimiento
	            switch (casoEspecificoNorma.getTipoFecha()) {
	                case CALENDARIO:
	                    fechaVencimiento.set(Calendar.DAY_OF_MONTH, casoEspecificoNorma.getDiaVencimiento().intValue());
	                    break;
	                case HABIL:
	                    // se toma la cantisdad de días hábiles del caso de normatividad
	                    int dias = casoEspecificoNorma.getDiaVencimiento().intValue();
	
	                    // sí la fecha del día 1 del mes de vencimiento no es un sábado,
	                    // domingo o festivo, se debe reducir la cantidad de
	                    // días en 1
	                    if (fechaVencimiento.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
	                            && fechaVencimiento.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
	                            && !FuncionesValidador.isFestivo(fechaVencimiento, festivos)) {
	                        dias -= 1;
	                    }
	
	                    fechaVencimiento = FuncionesValidador.modificarFecha(fechaVencimiento, dias, festivos);
	                    break;
	                case PRIMER_HABIL:
	                    // en este caso, primero se establece una fecha calendario
	                    fechaVencimiento.set(Calendar.DAY_OF_MONTH, casoEspecificoNorma.getDiaVencimiento().intValue());
	
	                    /*
	                     * sí la fecha de vencimiento es un sábado, domingo o festivo,
	                     * se debe llevar hasta el siguiente día hábil
	                     */
	                    while (fechaVencimiento.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
	                            || fechaVencimiento.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
	                            || FuncionesValidador.isFestivo(fechaVencimiento, festivos)) {
	                        fechaVencimiento = FuncionesValidador.modificarFecha(fechaVencimiento, 1, festivos);
	                    }
	                    break;
	            }
            } else {
            	fechaVencimiento = null;
            }
        } catch (Exception e) {
            mensaje = MensajesValidacionEnum.ERROR_CALCULO_SIMPLE.getReadableMessage(ConstantesComunesProcesamientoPILA.FECHA_VENCIMIENTO);

            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);

            throw new TechnicalException(mensaje);
        }
        return fechaVencimiento;
    }

    /**
     * Función encargada de llevar a cabo el cálculo del valor total de mora de
     * un aporte
     * 
     * @param periodosInteres
     *        Listado de los períodos de tasa de interés abarcados por el
     *        tiempo de morosidad
     * @param fechaVencimiento
     *        Fecha de vencimiento del aporte
     * @param fechaPago
     *        Fecha de pago del aporte
     * @param valorAporte
     *        Valor total del aporte
     * @return <b>ErrorValidacionValorMora</b> DTO que contiene el resultado del
     *         cálculo como un valor numérico y un indicio de mensaje de error
     *         en caso de presentarse alguno
     */
    public static ErrorValidacionValorMoraDTO calcularValorMora(List<TasasInteresMora> periodosInteres, Date fechaVencimiento,
            Date fechaPago, BigDecimal valorAporte) {
        logger.info("INICIA calcularValorMora");
        logger.info(periodosInteres);
        logger.info(fechaVencimiento);
        logger.info(fechaPago);
        logger.info(valorAporte);
        TasasInteresMora primerPeriodo = consultarPeriodoInteres(periodosInteres, fechaVencimiento);
        TasasInteresMora ultimoPeriodo = consultarPeriodoInteres(periodosInteres, fechaPago);

        ErrorValidacionValorMoraDTO result = new ErrorValidacionValorMoraDTO();
        BigDecimal valorTotalMoraCalculado = new BigDecimal(0);

        logger.info(primerPeriodo);
        logger.info(ultimoPeriodo);
        if (primerPeriodo != null && ultimoPeriodo != null) {
            // se consultan todos los periodos de interés en el rango

            List<TasasInteresMora> periodosInteresMora = consultarRangoPeriodoInteres(periodosInteres, primerPeriodo.getNumeroPeriodoTasa(),
                    ultimoPeriodo.getNumeroPeriodoTasa());

            if (periodosInteresMora != null) {
                // se declaran las variables de cálculo
                Long diasPeriodo = null;
                BigDecimal valorMoraPeriodo = null;

                // se recorren los períodos
                for (TasasInteresMora tasasInteresMora : periodosInteresMora) {

                    // se calculan los días en el período de tasa
                    try {
                        // sí sólo se trata de un período, los días se calculan
                        // entre la fecha de vencimiento y pago
                        if (periodosInteresMora.size() == 1) {
                        	logger.info("a. fechaPago" + fechaPago);
                        	logger.info("a. fechaVencimiento" + fechaVencimiento);
                            diasPeriodo = (CalendarUtil.getDiferenceBetweenDates(CalendarUtil.toCalendar(fechaVencimiento),
                                    CalendarUtil.toCalendar(fechaPago), Calendar.SECOND) / 60 / 60 / 24);
                        }
                        else {
                            // para más de un período
                            // los días del primer período se calculan con la
                            // fecha final del mismo y la fecha de vencimiento
                            if (tasasInteresMora.getNumeroPeriodoTasa().compareTo(primerPeriodo.getNumeroPeriodoTasa()) == 0) {
                            	logger.info("b. tasasInteresMora.getFechaFinTasa()" + tasasInteresMora.getFechaFinTasa());
                            	logger.info("b. fechaVencimiento" + fechaVencimiento);
                                diasPeriodo = (CalendarUtil.getDiferenceBetweenDates(CalendarUtil.toCalendar(fechaVencimiento),
                                        CalendarUtil.toCalendar(tasasInteresMora.getFechaFinTasa()), Calendar.SECOND) / 60 / 60 / 24) ;
                            }
                            else if (tasasInteresMora.getNumeroPeriodoTasa().compareTo(ultimoPeriodo.getNumeroPeriodoTasa()) == 0) {
                                // los días del último período se calculan con
                                // la fecha inicial del mismo y la fecha de pago

                                // se le agrega un día al resultado de la
                                // función, toda vez que la operación ignora el
                                // primer día (+1)
                            	logger.info("c. tasasInteresMora.getFechaFinTasa()" + tasasInteresMora.getFechaInicioTasa());
                            	logger.info("c. fechaPago" + fechaPago);
                                diasPeriodo = (CalendarUtil.getDiferenceBetweenDates(
                                        CalendarUtil.toCalendar(tasasInteresMora.getFechaInicioTasa()), CalendarUtil.toCalendar(fechaPago),
                                        Calendar.SECOND) / 60 / 60 / 24)+1;
                            }
                            else {
                                // los días de los demás períodos, se calculan
                                // con las fechas de inicio y fin del mismo

                                // se le agrega un día al resultado de la
                                // función, toda vez que la operación ignora el
                                // primer día (+1)
                            	logger.info("d. tasasInteresMora.getFechaInicioTasa()" + tasasInteresMora.getFechaInicioTasa());
                            	logger.info("d. tasasInteresMora.getFechaFinTasa()" + tasasInteresMora.getFechaFinTasa());
                                diasPeriodo = (CalendarUtil.getDiferenceBetweenDates(
                                        CalendarUtil.toCalendar(tasasInteresMora.getFechaInicioTasa()),
                                        CalendarUtil.toCalendar(tasasInteresMora.getFechaFinTasa()), Calendar.SECOND) / 60 / 60 / 24)+1;
                            }
                        }
                    } catch (Exception e) {
                        // Error en cálculo de los días en el período de interés
                        result.setIndicioMensaje(MessagesConstants.CALCULO_DIAS_PERIODO);
                        result.setMensajeErrorDetallado(e.getMessage());
                        result.setValorMoraCalculado(null);
                        return result;
                    }

                    logger.info("diasPeriodo" + diasPeriodo);
                    if (diasPeriodo != null) {
                        // primero divido el % de tasa entre 366 días

                        double valorTemporal = tasasInteresMora.getPorcentajeTasa().doubleValue() / 366;

                        // luego multiplico por el valor del aporte y por la
                        // cantidad de días en el período
                        valorTemporal *= valorAporte.doubleValue() * diasPeriodo;

                        valorMoraPeriodo = BigDecimal.valueOf(valorTemporal).setScale(16, BigDecimal.ROUND_HALF_UP);

                        valorTotalMoraCalculado = valorTotalMoraCalculado.add(valorMoraPeriodo);
                    }
                }

                // se aplican las reglas de redondeo
                valorTotalMoraCalculado = FuncionesValidador.redondearValor(valorTotalMoraCalculado, BigDecimal.valueOf(100));

                result.setValorMoraCalculado(valorTotalMoraCalculado);
            }
            else {
                // Error en la consulta del rango de períodos de tasa de
                // interés, se debe registrar como error técnico de validador
                result.setIndicioMensaje(MessagesConstants.ERROR_TECNICO);
                result.setValorMoraCalculado(null);
            }
        }
        else {
            // Error por falta de primer y/o último período de tasa de interés
            result.setIndicioMensaje(MessagesConstants.PERIODOS_INTERES);
            result.setValorMoraCalculado(null);
        }
        return result;
    }

    /**
     * Función para consultar un período de tasa de interés específico a partir
     * de una fecha
     * 
     * @param periodosInteres
     *        Listado de los períodos de tasa de interés
     * @param fechaReferencia
     *        Fecha de referencia para buscar la tasa correspondiente
     * @return
     */
    private static TasasInteresMora consultarPeriodoInteres(List<TasasInteresMora> periodosInteres, Date fechaReferencia) {
        TasasInteresMora result = null;

        if (periodosInteres != null && fechaReferencia != null) {
            for (TasasInteresMora tasaInteres : periodosInteres) {
                // sí la fecha de referencia encaja entre al fecha de inicio y
                // fin del período de tasa de interés, se retorna
                if (CalendarUtil.compararFechas(fechaReferencia, tasaInteres.getFechaInicioTasa()) >= 0
                        && CalendarUtil.compararFechas(fechaReferencia, tasaInteres.getFechaFinTasa()) <= 0) {

                    result = tasaInteres;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Función para solcitar un listado con los
     * 
     * @param periodosInteres
     * @param consecutivoInicial
     * @param consecutivoFinal
     * @return Listado de períodos de mora comprendidos entre dos consecutivos
     *         de período
     */
    private static List<TasasInteresMora> consultarRangoPeriodoInteres(List<TasasInteresMora> periodosInteres, Short consecutivoInicial,
            Short consecutivoFinal) {
        List<TasasInteresMora> result = null;

        if (consecutivoInicial != null && consecutivoFinal != null) {
            result = new ArrayList<>();

            for (TasasInteresMora tasaInteres : periodosInteres) {
                // sí la entrada tiene un número de consecutivo dentro del
                // rango, se le agrega al resultado
                if (tasaInteres.getNumeroPeriodoTasa().compareTo(consecutivoInicial) >= 0
                        && tasaInteres.getNumeroPeriodoTasa().compareTo(consecutivoFinal) <= 0) {
                    result.add(tasaInteres);
                }
            }
        }
        return result;
    }

    /**
     * Función encargada de determinar si se cumplen las condiciones para hacer
     * descuento al valor de mora de un aporte y aplicarlo de darse el caso
     * 
     * @param mora
     *        Valor de mora a modificar
     * @param casosDescuento
     *        Listado de DTO con las condiciones que atribuyen descuento
     * @param perfilArchivo
     *        Perfil de lectura del archivo PILA
     * @param indicadorUGPP
     *        Código indicador de la UGPP
     * @param fechaPago
     *        Fecha de pago del aporte (milisegundos)
     * @param periodoPago
     *        Período del aporte
     * @param tiposCotizantes
     *        Listado de los tipos de cotizante encontrados en la planilla
     * @return <b>BigDecimal</b> Valor recalculado de la mora
     */
    public static BigDecimal aplicarDescuentoMora(BigDecimal mora, List<DescuentoInteresMoraModeloDTO> casosDescuento,
            PerfilLecturaPilaEnum perfilArchivo, Short indicadorUGPP, Long fechaPago, String periodoPago, Set<String> tiposCotizantes) {
        BigDecimal moraConDescuento = mora;

        DescuentoInteresMoraModeloDTO casoAplicable = null;

        // se busca un caso de descuento que le aplique al aporte
        for (DescuentoInteresMoraModeloDTO descuento : casosDescuento) {
            Boolean aplica = true;

            Date periodoPagoLong = convertirDate(periodoPago + "-01");
            Date periodoIniLong = convertirDate(descuento.getPeriodoPagoInicial() + "-01");
            Date periodoFinLong = convertirDate(descuento.getPeriodoPagoFinal() + "-01");

            // la planilla corresponde al perfíl e indicador de la UGPP
            if ((!descuento.getPerfilLecturaPila().equals(perfilArchivo) || (descuento.getIndicadorUGPP() != null
                    && (indicadorUGPP == null || !descuento.getIndicadorUGPP().equals(indicadorUGPP))))
                    // el caso tiene fecha de pago mínima y esta es posterior al
                    // pago
                    || (descuento.getFechaPagoInicial() != null && fechaPago.compareTo(descuento.getFechaPagoInicial()) < 0)
                    // el caso tiene fecha de pago máxima y esta es anterior al
                    // pago
                    || (descuento.getFechaPagoFinal() != null && fechaPago.compareTo(descuento.getFechaPagoFinal()) > 0)
                    // el caso tiene período mínimo y este es posterior al
                    // período del aporte
                    || (periodoIniLong != null && (periodoPagoLong == null || periodoPagoLong.compareTo(periodoIniLong) < 0))
                    // el caso tiene período máximo y este es anterior al
                    // período del aporte
                    || (periodoFinLong != null && (periodoPagoLong == null || periodoPagoLong.compareTo(periodoFinLong) > 0))
                    // el caso tiene exclusión de tipos de cotizante y la
                    // planilla contiene alguno de esos tipos de cotizante
                    || (descuento.getTiposCotizanteExcluidos() != null
                            && !Collections.disjoint(descuento.getTiposCotizanteExcluidos(), tiposCotizantes))) {
                aplica = false;
            }

            if (aplica) {
                casoAplicable = descuento;
                break;
            }
        }

        // sí se encuentra un caso aplicable, se recalcula el valor de mora con
        // el descuento
        if (casoAplicable != null && mora != null) {
            BigDecimal descuento = mora.multiply(casoAplicable.getPorcentajeDescuento());
            moraConDescuento = moraConDescuento.subtract(descuento);
        }
        return moraConDescuento != null ? moraConDescuento : BigDecimal.ZERO;
    }

    /**
     * Función para la comparación de dos valores de mora
     * 
     * @param valorMora
     *        Valor de mora estipulado en aporte
     * @param valorTotalMoraCalculado
     *        Valor de mora calculado a partir de las tasas de interes y
     *        tiempo de morosidad
     * @param toleranciaValorMora
     *        Valor de la diferencia tolerada en la comparación
     * @return <b>Boolean</b> Indica sí el valor cumple o no con la validación
     */
    public static Boolean validarValorMora(BigDecimal valorMora, BigDecimal valorTotalMoraCalculado, BigDecimal toleranciaValorMora) {
        Boolean result = true;

        // se compara el valor de mora calculado con el valor de mora leído

        // se establece el rango de valor aceptable de acuerdo al margen de
        // tolerancia parametrizado
        BigDecimal inferior = valorTotalMoraCalculado.subtract(valorTotalMoraCalculado.multiply(toleranciaValorMora));
        BigDecimal superior = valorTotalMoraCalculado.add(valorTotalMoraCalculado.multiply(toleranciaValorMora));

        if (valorMora.compareTo(inferior) < 0 || valorMora.compareTo(superior) > 0) {
            result = false;
        }
        return result;
    }

    /**
     * Función encargada de preparar un DTO para la persistencia de un log de
     * error de excepción no controlada
     * 
     * @param indice
     *        Entrada de índice de planilla OI u OF en que el cual se
     *        presenta una excepción no controlada
     * @param e
     *        Objeto de la excepción lanzada
     * @return <b>LogErrorPilaM1ModeloDTO</b> DTO para la persistencia del log
     *         de error no controlado
     */
    public static LogErrorPilaM1ModeloDTO prepararLogError(Object indice, Exception e) {
        LogErrorPilaM1ModeloDTO result = new LogErrorPilaM1ModeloDTO();

        String nombreArchivo = null;
        Long idIndiceOI = null;
        Long idIndiceOF = null;

        if (indice instanceof IndicePlanilla) {
            nombreArchivo = ((IndicePlanilla) indice).getNombreArchivo();
            idIndiceOI = ((IndicePlanilla) indice).getId();
        }
        else if (indice instanceof IndicePlanillaOF) {
            nombreArchivo = ((IndicePlanillaOF) indice).getNombreArchivo();
            idIndiceOF = ((IndicePlanillaOF) indice).getId();
        }

        String mensaje = "";
        if (e.getCause() != null) {
            mensaje += "|" + e.getCause().toString();
        }
        if (e.getMessage() != null) {
            mensaje += "|" + e.getMessage();
        }
        if (e.getStackTrace() != null) {
            mensaje += "|" + Arrays.toString(e.getStackTrace());
        }

        result.setFechaHoraError(Calendar.getInstance().getTimeInMillis());
        result.setNombreArchivo(nombreArchivo);
        result.setIndicePlanillaOI(idIndiceOI);
        result.setIndicePlanillaOF(idIndiceOF);
        result.setMensaje(mensaje);
        return result;
    }

    /**
     * Método encargado de componer errores de estructura de componente en
     * lenguaje natural
     * 
     * @param mensajeOriginal
     *        Mensaje emitido por el componente fileProcessing
     * @return <b>List<ComposicionMensajeEstructuraDTO></b> Mensaje compuesto
     *         para la presentaión en bandeja de inconsistencias
     */
    public static List<ComposicionMensajeEstructuraDTO> componerErrorEstructura(String mensajeOriginal) {
        List<ComposicionMensajeEstructuraDTO> mensajes = new ArrayList<>();
        ComposicionMensajeEstructuraDTO mensajeParaAdicionar = null;
      System.out.println("**__** componerErrorEstructura+**  mensajeOriginal"+mensajeOriginal);
        // se crea un mapa único que contiene la totalidad de las etiquetas de
        // campos de los diferentes tipos de archivo
        List<ElementoEtiquetaControlMensajesDTO> etiquetas = new ArrayList<>();
 
        for (EtiquetaArchivoAEnum etiquetaA : EtiquetaArchivoAEnum.values()) {
            ElementoEtiquetaControlMensajesDTO valor = new ElementoEtiquetaControlMensajesDTO(etiquetaA.getNombreCampo(), etiquetaA.name(),
                    etiquetaA.getDescripcion(), etiquetaA.getTipoRegistro());
            etiquetas.add(valor);
        }

        for (EtiquetaArchivoIEnum etiquetaI : EtiquetaArchivoIEnum.values()) {
            ElementoEtiquetaControlMensajesDTO valor = new ElementoEtiquetaControlMensajesDTO(etiquetaI.getNombreCampo(), etiquetaI.name(),
                    etiquetaI.getDescripcion(), etiquetaI.getTipoRegistro());
            etiquetas.add(valor);
        }

        for (EtiquetaArchivoAPEnum etiquetaAP : EtiquetaArchivoAPEnum.values()) {
            ElementoEtiquetaControlMensajesDTO valor = new ElementoEtiquetaControlMensajesDTO(etiquetaAP.getNombreCampo(),
                    etiquetaAP.name(), etiquetaAP.getDescripcion(), etiquetaAP.getTipoRegistro());
            etiquetas.add(valor);
        }

        for (EtiquetaArchivoIPEnum etiquetaIP : EtiquetaArchivoIPEnum.values()) {
            ElementoEtiquetaControlMensajesDTO valor = new ElementoEtiquetaControlMensajesDTO(etiquetaIP.getNombreCampo(),
                    etiquetaIP.name(), etiquetaIP.getDescripcion(), etiquetaIP.getTipoRegistro());
            etiquetas.add(valor);
        }

        for (EtiquetaArchivoFEnum etiquetaF : EtiquetaArchivoFEnum.values()) {
            ElementoEtiquetaControlMensajesDTO valor = new ElementoEtiquetaControlMensajesDTO(etiquetaF.getNombreCampo(), etiquetaF.name(),
                    etiquetaF.getDescripcion(), etiquetaF.getTipoRegistro());
            etiquetas.add(valor);
        }

        // los mensajes de estructura del componente, se fraccionan por ";"
        // (errores de obligatoriedad y formato)
        String[] mensajesIndividuales = mensajeOriginal.split(";");
        ElementoEtiquetaControlMensajesDTO etiqueta = null;
        Boolean mensajeProcesado;

        for (String mensajeIndividual : mensajesIndividuales) {
            mensajeParaAdicionar = new ComposicionMensajeEstructuraDTO();

            mensajeProcesado = Boolean.FALSE;

            try {
                Pattern pat;
                Matcher mat;

                // se comprueba mensaje con patrón de error de línea vacía
                if (!mensajeProcesado) {
                    Boolean coincideError = Boolean.FALSE;

                    pat = Pattern.compile(ConstantesComunesProcesamientoPILA.PATRON_LINEA_VACIA);
                    mat = pat.matcher(mensajeIndividual);

                    if (mat.find()) {
                        coincideError = Boolean.TRUE;
                    }

                    pat = Pattern.compile(ConstantesComunesProcesamientoPILA.PATRON_LINEA_VACIA_SIN_IDENTIFICADOR);
                    mat = pat.matcher(mensajeIndividual);

                    if (mat.find()) {
                        coincideError = Boolean.TRUE;
                    }

                    /*
                     * este patrón de mensaje, lo arroja el componente junto
                     * otros reportes de línea vacía, no se debe agregar el
                     * mensaje para evitar duplicidad de reporte
                     */
                    pat = Pattern.compile(ConstantesComunesProcesamientoPILA.PATRON_LINEA_VACIA_YA_REPORTADA);
                    mat = pat.matcher(mensajeIndividual);

                    if (mat.find()) {
                        coincideError = Boolean.FALSE;
                    }

                    if (coincideError) {
                        mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_LINEA_VACIA
                                .getReadableMessage(TipoErrorValidacionEnum.TIPO_2.name()));

                        mensajeProcesado = Boolean.TRUE;
                    }
                }

                // se comprueba mensaje con patrón de error de estructura por
                // longitud de la línea
                if (!mensajeProcesado
                        && mensajeIndividual.contains(ConstantesComunesProcesamientoPILA.PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LONGITUD)) {
                    etiqueta = ubicarEtiqueta(mensajeIndividual, etiquetas);

                    if (etiqueta != null) {
                        mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_LINEA_INCOMPLETA
                                .getReadableMessage(TipoErrorValidacionEnum.TIPO_2.name(), etiqueta.getTipoRegistro().getDescripcion()));

                        mensajeParaAdicionar.setTipoRegistro(etiqueta.getTipoRegistro());
                        mensajeParaAdicionar.setErrorExcluyente(Boolean.TRUE);
                    }
                    else {
                        mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_LINEA_NO_RECONOCIDA
                                .getReadableMessage(TipoErrorValidacionEnum.TIPO_2.name()));
                    }

                    mensajeParaAdicionar.setErrorGeneralEstructura(Boolean.TRUE);
                    mensajeProcesado = Boolean.TRUE;
                }

                // se comprueba mensaje con patrón de error de estructura por
                // falta de líneas requeridas
                if (!mensajeProcesado) {
                    pat = Pattern.compile(ConstantesComunesProcesamientoPILA.PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LINEA_FALTANTE);
                    mat = pat.matcher(mensajeIndividual);

                    String nombreRegistro = null;

                    if (mat.find()) {
                        nombreRegistro = mat.group(4);
                    }

                    if (nombreRegistro != null) {
                        TipoLineaTipoRegistroEnum tipoLineaTipoRegistroEnum = TipoLineaTipoRegistroEnum
                                .obtenerPorDescripcionLinea(nombreRegistro);

                        mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_LINEA_REQUERIDA.getReadableMessage(
                                TipoErrorValidacionEnum.TIPO_2.name(), tipoLineaTipoRegistroEnum.getDescripcionLinea()));

                        mensajeParaAdicionar.setTipoRegistro(tipoLineaTipoRegistroEnum.getTipoRegistro());
                        mensajeParaAdicionar.setErrorExcluyente(Boolean.TRUE);

                        mensajeProcesado = Boolean.TRUE;
                    }
                }
                
                // se comprueba mensaje con patrón de error de estructura de línea incompleta
                if (!mensajeProcesado && mensajeIndividual
                        .contains(ConstantesComunesProcesamientoPILA.PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_LINEA_CAMPOS_FALTANTE)) {
                    mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_LINEA_NO_RECONOCIDA
                            .getReadableMessage(TipoErrorValidacionEnum.TIPO_2.name()));

                    mensajeProcesado = Boolean.TRUE;
                }

                // se comprueba mensaje con patrón de error de estructura de
                // campo
                if (!mensajeProcesado) {
                    etiqueta = ubicarEtiqueta(mensajeIndividual, etiquetas);

                    String valor = "";
                    if (etiqueta != null && mensajeIndividual.contains("\"")) {
                        // se cuenta con un valor para extraer (error de tipo de
                        // dato)
                        pat = Pattern.compile(ConstantesComunesProcesamientoPILA.PATRON_VALOR_CAMPO_ERROR_ESTRUCTURA_TIPO);
                        mat = pat.matcher(mensajeIndividual);

                        if (mat.find()) {
                            valor = mat.group(3);
                        }
                    }
   System.out.println("**__** COMENTADO IF ETI  QQUETA comentado para que pase el campo nulo de correo electronico 03/03/2022+** :"+etiqueta);
                   // if (etiqueta != null) {
                   //     mensajeParaAdicionar.setMensaje(MensajesValidacionEnum.ERROR_ESTRUCTURA_ARCHIVO_CAMPO.getReadableMessage(
                   //             etiqueta.getCodigoCampo(), valor, TipoErrorValidacionEnum.TIPO_2.name(), etiqueta.getDescripcionCampo()));
                   //        System.out.println("**__** mensajeParaAdicionar.setMensaje+** :"+mensajeParaAdicionar.getMensaje());
                   //     mensajeParaAdicionar.setTipoRegistro(etiqueta.getTipoRegistro());
                   // }
                }

            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                mensajeParaAdicionar.setMensaje(mensajeIndividual);
            }

            mensajes.add(mensajeParaAdicionar);
        }
        return mensajes;
    }

    /**
     * Método encargado de identificar una etiqueta de campo dentro de un
     * mensaje de estructura FileProcessing
     * 
     * @param mensajeIndividual
     *        Mensaje separado de componente
     * @param etiquetas
     *        Listado de DTOs con la información resumida de las etiquetas
     *        de campos de PILA
     * @return <b>ElementoEtiquetaControlMensajesDTO</b> Etiqueta identificada
     *         en el mensaje, null sí no se ubica ninguna etiqueta
     */
    private static ElementoEtiquetaControlMensajesDTO ubicarEtiqueta(String mensajeIndividual,
            List<ElementoEtiquetaControlMensajesDTO> etiquetas) {
        String nombreEtiqueta = null;
        String[] mensajeFraccionado = mensajeIndividual.split(" ");

        // se busca inicialmente, sí alguna de las palabras del mensaje
        // original, contiene una etiqueta de campo (expresión regular)
        for (String fragmento : mensajeFraccionado) {
            fragmento = fragmento.replace("\"", "");
            if (Pattern.matches(ConstantesComunesProcesamientoPILA.PATRON_ETIQUETA_CAMPO, fragmento)) {
                nombreEtiqueta = fragmento;
                break;
            }
        }

        if (nombreEtiqueta != null) {
            /*
             * se recorren las etiquetas de campo con el fin de establecer sí el
             * mensaje contiene el label de algún campo (error de campo
             * obligatorio faltante)
             */
            for (ElementoEtiquetaControlMensajesDTO etiqueta : etiquetas) {
                if (nombreEtiqueta.equals(etiqueta.getNombreCampo())) {
                          System.out.println("**__** ElementoEtiquetaControlMensajesDTO+**  nombreEtiqueta"+nombreEtiqueta);
                          System.out.println("**__** ElementoEtiquetaControlMensajesDTO+**  etiqueta.getNombreCampo()"+etiqueta.getNombreCampo());
                    return etiqueta;
                }
            }
        }
        return null;
    }

    /**
     * Método encargado de ubicar el número de lote correspondiente a una línea
     * del archivo OF
     * 
     * @param lineNumber
     *        Número de línea
     * @param mapaControlSumatorias
     *        Mapa con los DTO con la información de los lotes leídos
     * @return <b>Integer</b> Número de lote al que corresponde la línea
     */
    public static Integer buscarLoteLinea(Long lineNumber, Map<Integer, ControlLoteOFDTO> mapaControlSumatorias) {
        Integer numeroLote = null;

        Iterator<Entry<Integer, ControlLoteOFDTO>> it = mapaControlSumatorias.entrySet().iterator();

        while (it.hasNext()) {
            Entry<Integer, ControlLoteOFDTO> par = it.next();
            ControlLoteOFDTO lote = par.getValue();

            if ((lote.getLineasEnLote().contains(lineNumber)) || (lineNumber >= lote.getLineaInicioLote()
                    && (lote.getLineaFinLote() == null || lineNumber <= lote.getLineaFinLote()))) {
                numeroLote = lote.getNumeroLote();
                break;
            }
        }
        return numeroLote;
    }

    /**
     * Función que inicializa el arreglo que lleva el control de la cantidad de
     * tipos de registro leídos
     * 
     * @return
     */
    public static Map<TipoLineaTipoRegistroEnum, Integer> inicializarListaControlRegistros() {
        Map<TipoLineaTipoRegistroEnum, Integer> listaRegistros = new EnumMap<>(TipoLineaTipoRegistroEnum.class);

        for (TipoLineaTipoRegistroEnum tipoLinea : TipoLineaTipoRegistroEnum.values()) {
            listaRegistros.put(tipoLinea, 0);
        }
        return listaRegistros;
    }

    /**
     * Método encargado de construir la sentencia INSERT para la persistencia de
     * registros masivos de PILA
     * 
     * @param registros
     *        Listado de registros a incluir en la sentencia
     * @param indicador
     *        Indicador para la tabla que se va a persistir:<br>
     *        1 -> PilaArchivoIRegistro2<br>
     *        2 -> PilaArchivoIPRegistro2<br>
     *        3 -> PilaArchivoFRegistro6
     * @return
     */
    public static String prepararQueryInsercion(List<?> registros, Integer indicador) {
        StringBuilder sentencia = new StringBuilder(QueriesConstants.INSERT_INTO_CLAUSE);
        String tabla = null;
        List<String> campos = null;
        List<String> values = new ArrayList<>();

        switch (indicador) {
            case 1:
                PilaArchivoIRegistro2ModeloDTO pi2DTO = new PilaArchivoIRegistro2ModeloDTO();
                tabla = "PilaArchivoIRegistro2";

                campos = pi2DTO.getNombresCampos();

                for (Object registro : registros) {
                    PilaArchivoIRegistro2 registroPi2 = (PilaArchivoIRegistro2) registro;
                    values.add(pi2DTO.crearLineaValues(registroPi2));
                }
                break;
            case 2:
                PilaArchivoIPRegistro2ModeloDTO ip2DTO = new PilaArchivoIPRegistro2ModeloDTO();
                tabla = "PilaArchivoIPRegistro2";

                campos = ip2DTO.getNombresCampos();

                for (Object registro : registros) {
                    PilaArchivoIPRegistro2 registroIp2 = (PilaArchivoIPRegistro2) registro;
                    values.add(ip2DTO.crearLineaValues(registroIp2));
                }
                break;
            case 3:
                PilaArchivoFRegistro6ModeloDTO pf6DTO = new PilaArchivoFRegistro6ModeloDTO();
                tabla = "PilaArchivoFRegistro6";

                campos = pf6DTO.getNombresCampos();

                for (Object registro : registros) {
                    PilaArchivoFRegistro6 registroPf6 = (PilaArchivoFRegistro6) registro;
                    values.add(pf6DTO.crearLineaValues(registroPf6));
                }
                break;
            default:
                break;
        }

        if (tabla != null && campos != null && !campos.isEmpty() && !values.isEmpty()) {
            sentencia.append(tabla);
            sentencia.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);

            sentencia.append(campos.get(0));

            for (int i = 1; i < campos.size(); i++) {
                sentencia.append(QueriesConstants.COMMA_SYMBOL);
                sentencia.append(campos.get(i));
            }
            sentencia.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);
            sentencia.append(QueriesConstants.VALUES_CLAUSE);

            sentencia.append(values.get(0));

            for (int i = 1; i < values.size(); i++) {
                sentencia.append(QueriesConstants.COMMA_SYMBOL);
                sentencia.append(values.get(i));
            }
        }
        return sentencia.toString();
    }

    /**
     * Método para preparar una entrada de log de errores de validación a partir
     * de un DTO de error
     * 
     * @param error
     *        DTO con la información del error que se requiere agregar a la
     *        base de datos
     * @param idError
     *        ID que se asignará a la entrada de error
     * @return <b>ErrorValidacionLog</b> Entity de log de errores para ser
     *         persistido
     * @throws ErrorFuncionalValidacionException
     */
    public static ErrorValidacionLog generarEntityError(ErrorDetalladoValidadorDTO error, Long idError)
            throws ErrorFuncionalValidacionException {
        ErrorValidacionLog result = null;

        if (error != null) {
            result = new ErrorValidacionLog();

            result.setId(idError);
            result.setIdRegistroTipo2(error.getIdRegTipo2());
            
            // se asigna el código de campo como nombre del campo de manera
            // temporal
            result.setNombreCampo(error.getIdCampoError());

            result.setBloqueValidacion(error.getBloque());
            result.setCodigoError(error.getCodigoError());

            if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(error.getTipoArchivo())) {
                result.setIdIndicePlanillaOF(error.getIdIndicePlanilla());
            }
            else {
                result.setIdIndicePlanilla(error.getIdIndicePlanilla());
            }

            // se debe asegurar que el mensaje de error no supere los 4000
            // caracteres
            String mensaje = error.getMessage();
            if (mensaje != null && mensaje.length() > ConstantesComunesProcesamientoPILA.LONGITUD_MAX_MENSAJE_ERROR_INCONSISTENCIA) {
                mensaje = mensaje.substring(0, ConstantesComunesProcesamientoPILA.LONGITUD_MAX_MENSAJE_ERROR_INCONSISTENCIA - 3) + "...";
            }

            result.setMensajeError(mensaje);

            // se convierte el número de línea a Short
            if (error.getLineNumber() != null) {
                result.setNumeroLinea(error.getLineNumber().shortValue());
            }

            result.setTipoArchivo(error.getTipoArchivo());
            result.setTipoError(error.getTipoError());
            result.setValorCampo(error.getValorCampo());
            result.setEstadoInconsistencia(EstadoGestionInconsistenciaEnum.PENDIENTE_GESTION);
        }
        return result;
    }

    /**
     * Método encargado de agregar los datos de posición y nombre de campo en
     * errores de validación
     * 
     * @param errores
     * @param ubicaciones
     */
    public static void asignarUbicacionCampo(List<ErrorValidacionLog> errores, List<UbicacionCampoArchivoPilaDTO> ubicaciones) {
        
        if (ubicaciones == null || ubicaciones.isEmpty()) {
            return;
        }

        // se crea un mapa para contener los DTO de ubicación de campo y
        // relacionarlo con el código de campo en el error
        Map<String, List<ErrorValidacionLog>> mapaErrores = new HashMap<>();

        for (ErrorValidacionLog error : errores) {
            String codigo = error.getNombreCampo();

            // sí la inconsistencia cuenta con un dato de código de campo
            if (codigo != null) {
                // se debe establecer sí el código del campo corresponde con
                // algún valor de etiqueta

                Object etiqueta = null;
                String nombreInterno = null;

                // se compara con etiqueta de archivo A-AR
                etiqueta = EtiquetaArchivoAEnum.obtenerEtiqueta(codigo);
                nombreInterno = etiqueta != null ? ((EtiquetaArchivoAEnum) etiqueta).getNombreCampo() : null;

                // se compara con etiqueta de archivo AP-APR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoAPEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoAPEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo I-IR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoIEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoIEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo IP-IPR
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoIPEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoIPEnum) etiqueta).getNombreCampo() : null;
                }

                // se compara con etiqueta de archivo F
                if (etiqueta == null) {
                    etiqueta = EtiquetaArchivoFEnum.obtenerEtiqueta(codigo);
                    nombreInterno = etiqueta != null ? ((EtiquetaArchivoFEnum) etiqueta).getNombreCampo() : null;
                }

                if (nombreInterno != null && mapaErrores.containsKey(nombreInterno)) {
                    mapaErrores.get(nombreInterno).add(error);
                }
                else if (nombreInterno != null) {
                    List<ErrorValidacionLog> listaNueva = new ArrayList<>();
                    listaNueva.add(error);
                    mapaErrores.put(nombreInterno, listaNueva);
                }
            }
        }

        // se agregan las ubicaciones en los errores
        for (UbicacionCampoArchivoPilaDTO ubicacion : ubicaciones) {
            List<ErrorValidacionLog> subConjuntoError = mapaErrores.get(ubicacion.getNombreInterno());

            if (subConjuntoError != null) {
                for (ErrorValidacionLog error : subConjuntoError) {
                    error.setNombreCampo(ubicacion.getNombreCampo());
                    error.setPosicionInicial(ubicacion.getPosicionInicial());
                    error.setPosicionFinal(ubicacion.getPosicionFinal());
                }
            }
        }
    }

    /**
     * Método encargado de obtener una instancia del EntityManager direccionado al modelo de datos de PILA
     * @return <b>EntityManager</b>
     *         Instancia del EntityManager direccionado al modelo de datos de PILA
     */
    public static EntityManager obtenerEntityPila() {
        EntityManagerProceduresPeristanceLocal emPila = ResourceLocator.lookupEJBReference(EntityManagerProceduresPeristanceLocal.class);

        return emPila.getEntityManager();
    }

    /**
     * Método encargado de obtener una referencia al gestor de SPs para la persistencia de archivos
     * @return
     */
    public static GestorStoredProceduresLocal obtenerGestorSP() {
        GestorStoredProceduresLocal resutl = ResourceLocator.lookupEJBReference(GestorStoredProceduresLocal.class);

        return resutl;
    }

    /**
     * Método encargado de asociar errores de Tipo 0 en el B4 a un registro de tipo 2 de PILA
     * @param idRegistro,
     * @param errores
     */
    public static void asociarErroresBloque4(Long idRegistro, Long numeroLinea, List<ErrorDetalladoValidadorDTO> errores) {
        for (ErrorDetalladoValidadorDTO error : errores) {
            if (error.getLineNumber().compareTo(numeroLinea) == 0) {
                error.setIdRegTipo2(idRegistro);
            }
        }
    }

    /**
     * Método encargado de organizar el listado de DTOs de archivos PILA cargados
     * @param listaArchivoPila
     *        Lista de DTOs cargados
     * @param agrupar
     *        Indicador para establecer si el ordenamiento se hace por grupos de archivos A / I (<code>true</code>)
     *        o se trata de un ordenamiento general
     * @return Listado ordenado de DTOs de archivos
     */
    public static List<ArchivoPilaDTO> ordenarListadoDatosArchivos(List<ArchivoPilaDTO> listaArchivoPila, Boolean agrupar) {
        List<ArchivoPilaDTO> listaOrdenada = null;

        System.out.println(".'.'.'.'.'.'.'.'.'.'.'.' inicia ordenarListadoDatosArchivos(List<ArchivoPilaDTO> listaArchivoPila, Boolean agrupar) ");
        
        if(agrupar){
            listaOrdenada = new ArrayList<>();
            
            // se separan los archivos por tipo
            List<ArchivoPilaDTO> listaArchivosA = new ArrayList<>();
            List<ArchivoPilaDTO> listaArchivosI = new ArrayList<>();
            List<ArchivoPilaDTO> listaArchivosF = new ArrayList<>();
            
            for(ArchivoPilaDTO archivo : listaArchivoPila){
                System.out.println(".'.'.'.'.'.'.'.'.'.'.'.' for archivoPilaDTO ");
                System.out.println(".'.'.'.'.'.'.'.'.'.'.'.' ");
                System.out.println(archivo.toString());
                TipoArchivoPilaEnum tipoArchivo = FuncionesValidador.getTipoArchivo(archivo.getFileName());
                if(tipoArchivo != null && PerfilLecturaPilaEnum.ARCHIVO_FINANCIERO.equals(tipoArchivo.getPerfilArchivo())){
                    listaArchivosF.add(archivo);
                }else if (tipoArchivo != null && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivo.getSubtipo())){
                    listaArchivosA.add(archivo);
                }else if (tipoArchivo != null && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivo.getSubtipo())){
                    listaArchivosI.add(archivo);
                }
            }
            
            // se organizan los listados
            listaArchivosA.sort((ArchivoPilaDTO archivo1, ArchivoPilaDTO archivo2) -> archivo1.getFechaModificacion()
                    .compareTo(archivo2.getFechaModificacion()));
            listaArchivosI.sort((ArchivoPilaDTO archivo1, ArchivoPilaDTO archivo2) -> archivo1.getFechaModificacion()
                    .compareTo(archivo2.getFechaModificacion()));
            listaArchivosF.sort((ArchivoPilaDTO archivo1, ArchivoPilaDTO archivo2) -> archivo1.getFechaModificacion()
                    .compareTo(archivo2.getFechaModificacion()));
            
            // se compone de nuevo el listado
            listaOrdenada.addAll(listaArchivosA);
            listaOrdenada.addAll(listaArchivosI);
            listaOrdenada.addAll(listaArchivosF);
        }else{
            listaOrdenada = listaArchivoPila;
          //Calendar c = Calendar.getInstance();
          //c.add(Calendar.DATE, -1);
          //Date fechaA = c.getTime();
          //logger.info("FECHA ACTUAL"+fechaA);
          //DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
          //dateMod = formatter.parse(atributos.getMtimeString());
          //                informacionArchivoDTO.setFechaModificacion(dateMod.getTime());
          //listaOrdenada.removeIf(a -> {
          //    Date fechaModificacion = a.getFechaModificacion();
          //    return (fechaModificacion != null && fechaModificacion.before(fechaA));
          //});
            // se organiza los listados
            //listaOrdenada.sort((ArchivoPilaDTO archivo1, ArchivoPilaDTO archivo2) -> archivo2.getFechaModificacion()
            //        .compareTo(archivo1.getFechaModificacion()));
            listaOrdenada.sort((ArchivoPilaDTO archivo1, ArchivoPilaDTO archivo2) -> archivo1.getFileName().compareTo(archivo2.getFileName()));

        }
        return listaOrdenada;
    }

    /**
     * Método encargado de definir el mensaje de respuesta de acuerdo al estado recibido
     * 
     * @param respuesta
     *        DTO con la respuesta del servicio
     * @return RespuestaServicioDTO
     *         DTO actualizado con el mensaje correspondiente
     */
    public static RespuestaServicioDTO prepararMensajeRespuesta(RespuestaServicioDTO respuesta) {
        String mensaje = "";

        switch (respuesta.getEstado()) {
            case DESCARGADO_CON_INCONSISTENCIAS:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(),
                        MessagesConstants.ERROR_ARCHIVO_DESCARGADO_CON_INCONSISTENCIA);
                break;
            case DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(), MessagesConstants.ERROR_ARCHIVO_DUPLICADO);
                break;
            case DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO_ANTERIOR:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(),
                        MessagesConstants.ERROR_ARCHIVO_DUPLICADO_ANTERIOR);
                break;
            case DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(),
                        MessagesConstants.ERROR_ARCHIVO_GRUPO_NO_VALIDO);
                break;
            case DESCARGADO_CON_INCONSISTENCIAS_REPROCESO_PREVIO:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(),
                        MessagesConstants.ERROR_ARCHIVO_REPROCESO_PREVIO);
                break;
            case DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA:
            case DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(), MessagesConstants.ERROR_EXTENSION_ARCHIVO);
                break;
            case CARGADO:
            case CARGADO_EXITOSAMENTE:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(), MessagesConstants.CARGA_EXITOSA);
                break;
            case CARGADO_REPROCESO:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(), MessagesConstants.CARGA_EXITOSA_REPROCESO);
                break;
            case CARGADO_REPROCESO_ACTUAL:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(),
                        MessagesConstants.CARGA_EXITOSA_REPROCESO_ACTUAL);
                break;
            case INCONSISTENCIA_NOMBRE_ARCHIVO:
                mensaje = MessagesConstants.getMensajeCargaArchivo(respuesta.getFileName(), MessagesConstants.ERROR_NOMBRE_ARCHIVO);
                break;
            default:
                break;
        }

        respuesta.setMensajeRespuesta(mensaje);
        return respuesta;
    }

    /**
    * Método encargado de definir si el municipioLaboral esta en el departamento de la CCF
    * 
    */
	public static Boolean validarExistenciaMunicipioEnDepto(Set<String[]> departamentosMunicipiosSet, String dptoCCF, String muniLaboral) {
		
        Set<String> municipiosSet = new HashSet<String>();
		
		if(dptoCCF != null && !dptoCCF.trim().equals("")) {
           	
            if(departamentosMunicipiosSet.size()>0) {            	
            	Iterator departamentoIter = departamentosMunicipiosSet.iterator();
            	while(departamentoIter.hasNext()) {
            		String[] departamentoArr = (String[]) departamentoIter.next();
            		if(dptoCCF.equals(departamentoArr[0])) {
            			municipiosSet.add(departamentoArr[1]);
            		}
            	}
            }
            
        }
		
		if(municipiosSet.size()>0 && municipiosSet.contains(muniLaboral)) {
			return true;
		}
		
		
		return false;
	}
}
