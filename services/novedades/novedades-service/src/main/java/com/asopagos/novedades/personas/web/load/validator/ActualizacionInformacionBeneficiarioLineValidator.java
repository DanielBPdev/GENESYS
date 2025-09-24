package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa Salamanca</a>
 */

public class ActualizacionInformacionBeneficiarioLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public ActualizacionInformacionBeneficiarioLineValidator() {
        super();
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();
        try {
            Long lineNumber = arguments.getLineNumber();
            // Se valida el campo No 1 - Tipo de identificación del beneficiario o miembro del grupo familiar  
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES,
                    true, false, true);

            // Se valida el campo No 2 - Número de identificación del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO_MSG,
                    ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES, null, null, true, false);

            // Se valida el campo No 3 - Primer apellido del beneficiario o miembro del grupo familiar  
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.PRIMER_APELLIDO_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES,
                    null, null, true, false);

            // Se valida el campo No 4 - Segundo apellido del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES,
                    null, null, false, false);

            // Se valida el campo No 5 - Primer nombre del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.PRIMER_NOMBRE_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES, null,
                    null, true, false);

            // Se valida el campo No 6 - Segundo nombre del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES,
                    null, null, false, false);

            // Se valida el campo No 7 - Condición especial de pago cuota monetaria 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.CONDICION_ESPECIAL_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.CONDICION_ESPECIAL_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER,
                    null, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, false, false);

            // Se valida el campo No 8 - Fecha de nacimiento del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                    null, null, true, true);

            // Se valida el campo No 9 - Genero del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.GENERO_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.GENERO_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER,
                    FieldValidatorUtil.ENUM_GENERO, null, true, false);

            // Se valida el campo No 10 - Nacionalidad del beneficiario o miembro del grupo familiar 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NACIONALIDAD_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.NACIONALIDAD_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_4_CARACTERES, null,
                    null, false, false);

            // Se valida el campo No 11 - Nivel educativo 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NIVEL_EDUCATIVO_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.NIVEL_EDUCATIVO_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER,
                    FieldValidatorUtil.ENUM_NIVEL_EDUCATIVO, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, true, false);

        } finally {
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }
        }
    }

    /**
     * Verifica que el nivel educativo se encuentre dentro de los rangos permitidos
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
     */
    private void validateNivelEducativo(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, Map<String, Object> line,
            Long lineNumber, String nombreCampo, String constanteMensaje, Integer longitudCampo, Boolean isRequired) {
        if (FieldValidatorUtil.validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            // Se verifica que este dentro de los parametros de longitud
            if (FieldValidatorUtil.validateLength(valorCampo, longitudCampo)) {
                // Se verifica que sea un digito
                if (FieldValidatorUtil.validateRegex(valorCampo, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS)) {
                    lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                    return;
                }
                Integer nivelEducativo = Integer.parseInt(valorCampo);
                if (!(nivelEducativo >= 0 && nivelEducativo <= 6)) {
                    lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                }
            }
            else {
                lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO + longitudCampo));
            }
        }
        else {
            FieldValidatorUtil.createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }
    }

}