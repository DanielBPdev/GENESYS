package com.asopagos.fovis.cruce.web.load.validator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.constants.FileFieldConstants;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;

/**
 * Clase con implementaciones comunes de validacion de archivos
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class FieldValidatorUtil {

    private FieldValidatorUtil() {

    }

    /**
     * Valida el campo teniendo en cuenta los parametros de validacion
     * 
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param line
     *        Informacion de la linea del archivo
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param nombreCampo
     *        Nombre del campo a validar
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @param longitudCampo
     *        Indica la longitud maxima permitida del campo
     * @param tipoEnumValida
     *        Indica el enumerado contra el que se valida el campo
     * @param expresionRegular
     *        Indica la expresion regular que valida el campo
     * @param isRequired
     *        Indica si el campo es requerido
     * @param isDate
     *        Indica si el campo es una fecha
     */
    public static void validate(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, Map<String, Object> line, Long lineNumber,
            String nombreCampo, String constanteMensaje, Integer longitudCampo, String expresionRegular, Boolean isRequired,
            Boolean isDate) {
        // Se verifica que llegue el campo con valor
        if (validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            if (longitudCampo != null && !validateLength(valorCampo, longitudCampo)) {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje,
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO + longitudCampo));
                return;
            }
            // Se verifica si es campo fecha
            if (isDate && validateDate(valorCampo)) {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_FORMATO_FECHA));
                return;
            }
            // Se verifica si se valida contra una RegExp
            if (expresionRegular != null && validateRegex(valorCampo, expresionRegular)) {
                // Se verifica si se encontro el valor por defecto para los NIT ENTIDAD
                if (valorCampo.equals(FileFieldConstants.VALUE_DEFAULT_NIT_ENTIDAD)) {
                    return;
                }
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                return;
            }
        }
        else {
            createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }
    }

    /**
     * Verifica que el campo enviado por parametro exista y su valor NO sea nulo o vacio
     * @param line
     *        Informacion linea leida del archivo
     * @param nombreCampo
     *        Nombre del campo
     * @return TRUE si el campo tiene informacion, FALSE si el valor del campo es nulo o vacio
     */
    public static Boolean validateNotNull(Map<String, Object> line, String nombreCampo) {
        Boolean result = Boolean.FALSE;
        if (line != null && line.get(nombreCampo) != null && !line.get(nombreCampo).equals("")) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Verifica si se debe generar el hallazgo de campo requerido y lo crea si es necesario
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @param isRequired
     *        Indica si el campo es requerido
     */
    public static void createMessageRequired(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, Long lineNumber,
            String constanteMensaje, Boolean isRequired) {
        // Se verifica si el campo es obligatorio
        if (isRequired) {
            lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_REQUERIDO));
        }
    }

    /**
     * Verifica que el valor del campo enviado este dentro de longitud maxima permitida
     * @param valorCampo
     *        Valor del campo del archivo
     * @param longitudCampo
     *        Longitud maxima permitida para el campo
     * @return TRUE si el campo tiene una longitud permitida y FALSE si se excede en la longitud
     */
    public static Boolean validateLength(String valorCampo, Integer longitudCampo) {
        Boolean result = Boolean.TRUE;
        if (!(valorCampo.length() <= longitudCampo)) {
            result = Boolean.FALSE;
        }
        return result;
    }

    /**
     * Valida que el valor del campo sea una fecha
     * @param valorCampo
     *        Valor campo a validar
     */
    public static Boolean validateDate(String valorCampo) {
        Boolean result = Boolean.FALSE;
        Date fecha = CalendarUtils.darFormatoYYYYMMDDGuionDate(valorCampo);
        if (fecha == null) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Valida que el valor del campo haga parte de la expresion regular
     * @param valorCampo
     *        Valor campo a validar
     * @param expresionRegular
     *        Indica la expresion regular que valida el campo
     */
    public static Boolean validateRegex(String valorCampo, String expresionRegular) {
        Boolean result = Boolean.FALSE;
        if (!(valorCampo.matches(expresionRegular))) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Valida el tipo de identificacion
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param line
     *        Informacion de la linea del archivo
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param nombreCampo
     *        Nombre del campo a validar
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @param longitudCampo
     *        Indica la longitud maxima permitida del campo
     * @param isRequired
     *        Indica si el campo es requerido
     * @param isTipIdenEmpleador
     *        Indica si el tipo identificacion corresponde a un empleador
     * @param isTipIdenTrabajador
     *        Indica si el tipo de identificacion corresponde a trabajador
     */
    public static void validateTipoIdentificacion(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, Map<String, Object> line,
            Long lineNumber, String nombreCampo, String constanteMensaje, Integer longitudCampo, Boolean isRequired,
            Boolean isTipIdenEmpleador, Boolean isTipIdenTrabajador) {
        // Se verifica que llegue el campo con valor
        if (validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            // Se verifica que este dentro de los parametros de longitud
            if (validateLength(valorCampo, longitudCampo)) {
                Boolean result = Boolean.FALSE;
                TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(valorCampo.toUpperCase());
                if (tipoIdentEnum == null) {
                    result = Boolean.TRUE;
                }
                else if (isTipIdenEmpleador && !tipoIdentEnum.getIdentificionEmpleador()) {
                    result = Boolean.TRUE;
                }
                else if (isTipIdenTrabajador && !tipoIdentEnum.getIdentificionTrabajador()) {
                    result = Boolean.TRUE;
                }
                if (result) {
                    lstHallazgos
                            .add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                }
            }
            else {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje,
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO + longitudCampo));
            }
        }
        else {
            createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }
    }

    /**
     * Valida que el departamento enviado conincida con una existente
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param listDepartamento
     *        Lista de departamentos del sistema
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param valorCampo
     *        Valor del campo departamento
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @return Objeto departamento
     */
    public static Departamento validateDepartamento(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos,
            List<Departamento> listDepartamento, Long lineNumber, String valorCampo, String constanteMensaje) {
        Departamento departamento = null;
        try {
            departamento = GetValueUtil.getDepartamento(listDepartamento, Long.parseLong(valorCampo));
            if (departamento == null) {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
        }
        return departamento;
    }

    /**
     * Valida que el municipio enviado corresponda con uno existente
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param listMunicipio
     *        Lista de municipios existentes en el sistema
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param valorCampo
     *        Valor del campo departamento
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @return Objeto municipio
     */
    public static Municipio validateMunicipio(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, List<Municipio> listMunicipio,
            Long lineNumber, String valorCampo, String constanteMensaje) {
        Municipio municipio = null;
        try {
            municipio = GetValueUtil.getMunicipio(listMunicipio, Long.parseLong(valorCampo));
            if (municipio == null) {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
        }
        return municipio;
    }

    /**
     * Valida la estructura y contenido de los campos departamento y municipio segun parametros
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param arguments
     *        Informacion del archivo
     * @param line
     *        Informacion de la linea del archivo
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param nombreCampo
     *        Nombre del campo a validar
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @param longitudCampo
     *        Indica la longitud maxima permitida del campo
     * @param isRequired
     *        Indica si el campo es requerido
     * @param isDepartamento
     *        Indica si se valida un departamento
     * @param isMunicipio
     *        Indica si se valida un municipio
     * @return Objeto con la informacion de un Departamento o Municipio segun corresponda
     */
    @SuppressWarnings("unchecked")
    public static Object validateDepartamentoMunicipio(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, LineArgumentDTO arguments,
            Map<String, Object> line, Long lineNumber, String nombreCampo, String constanteMensaje, Integer longitudCampo,
            Boolean isRequired, Boolean isDepartamento, Boolean isMunicipio) {
        if (validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            // Se verifica que este dentro de los parametros de longitud
            if (validateLength(valorCampo, longitudCampo)) {
                // Se verifica si se valida contra una RegExp
                if (validateRegex(valorCampo, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS)) {
                    lstHallazgos
                            .add(crearHallazgo(lineNumber, constanteMensaje, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                    return null;
                }
                if (isDepartamento) {
                    List<Departamento> listDepartamento = (List<Departamento>) arguments.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO);
                    return validateDepartamento(lstHallazgos, listDepartamento, lineNumber, valorCampo, constanteMensaje);
                }
                else if (isMunicipio) {
                    List<Municipio> listMunicipio = (List<Municipio>) arguments.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                    return validateMunicipio(lstHallazgos, listMunicipio, lineNumber, valorCampo, constanteMensaje);
                }
            }
            else {
                lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje,
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO + longitudCampo));
            }
        }
        else {
            createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }

        return null;
    }

    /**
     * Valida el encabezado de un archivo
     * @param lstHallazgos Lista que contiene los errores encontrados para la linea evaluada
     * @param line Informacion de la linea del archivo
     * @param lineNumber Numero de la linea del archivo
     * @param nombreCampo Nombre del campo a validar
     * @param constanteMensaje Nombre del campo para mensajes de error
     * @param nombreEncabezado Nombre del encabezado a validar
     */
    public static void validateHeader(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, Map<String, Object> line, Long lineNumber,
            String nombreCampo, String constanteMensaje, String nombreEncabezado) {
        // Se verifica que llegue el campo con valor
        if (validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            if (valorCampo.trim().equals(nombreEncabezado)) {
                return;
            }
        }
        // Se agrega el error cuando no se encuentre valor o no sea el valor esperado
        lstHallazgos.add(crearHallazgo(lineNumber, constanteMensaje, FileFieldConstants.MENSAJE_ERROR_ENCABEZADO));
    }

    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber,
     *        numero de linea
     * @param campo,
     *        campo al que pertenece el hallazgo
     * @param errorMessage,
     *        mensaje de error
     * @return retorna el resultado de hallazgo DTO
     */
    public static ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }
}
