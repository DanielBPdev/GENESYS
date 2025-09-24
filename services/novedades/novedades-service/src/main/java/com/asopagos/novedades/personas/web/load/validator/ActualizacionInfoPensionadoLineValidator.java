package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa Salamanca</a>
 */

public class ActualizacionInfoPensionadoLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public ActualizacionInfoPensionadoLineValidator() {
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
            // Se valida el campo No 1 - Tipo de identificación del beneficiario
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFI_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, true,
                    false, true);

            // Se valida el campo No 2 - Número de identificación del beneficiario
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFI_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                    null, null, true, false);

            // Se valida el campo No 3 - Nombres  
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NOMBRES_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.NOMBRES_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                    null, true, false);
            // Se valida el campo No 4 - Apellidos 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.APELLIDOS_BENEFICIARIO,
                    ArchivoCampoNovedadConstante.APELLIDOS_BENEFICIARIO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                    null, true, false);
            // Se valida el campo No 5 - Administradora de pensiones 
            validateAFP(lstHallazgos, arguments, line, lineNumber, ArchivoCampoNovedadConstante.ADMINISTRADOR_PENSION,
                    ArchivoCampoNovedadConstante.ADMINISTRADOR_PENSION_MSG, true);

            // Se valida el campo No 6 - ¿Persona tiene condición de pensionado? 
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.ES_PENSIONADO,
                    ArchivoCampoNovedadConstante.ES_PENSIONADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                    ExpresionesRegularesConstants.SI_NO_IGNORE_CASE, true, false);

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
     * Verifica que el codigo de la AFP exista en el sistema
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param arguments
     * @param line
     *        Informacion de la linea del archivo
     * @param lineNumber
     *        Numero de la linea del archivo
     * @param nombreCampo
     *        Nombre del campo a validar
     * @param constanteMensaje
     *        Nombre del campo para mensajes de error
     * @param isRequired
     *        Indica si el campo es requerido
     */
    @SuppressWarnings("unchecked")
    private void validateAFP(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, LineArgumentDTO arguments, Map<String, Object> line,
            Long lineNumber, String nombreCampo, String constanteMensaje, Boolean isRequired) {
        // Se verifica que llegue el campo con valor
        if (FieldValidatorUtil.validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            List<AFP> listAFP = (List<AFP>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_AFP);
            AFP afp = GetValueUtil.getAFPByCodigoPila(listAFP, valorCampo);
            if (afp == null) {
                lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
            }
        }
        else {
            FieldValidatorUtil.createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }
    }
}