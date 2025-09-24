package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
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

public class ActualizacionInfoCertificadoEscorlarBeneficiarioLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public ActualizacionInfoCertificadoEscorlarBeneficiarioLineValidator() {
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

            // Se valida el campo No 3 - Fecha inicio certificado escolar
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_INICIO_CERTIFICADO_ESCOLAR,
                    ArchivoCampoNovedadConstante.FECHA_INICIO_CERTIFICADO_ESCOLAR_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                    null, null, true, true);

            // Se valida el campo No 4 - Fecha fin certificado escolar
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_FIN_CERTIFICADO_ESCOLAR,
                    ArchivoCampoNovedadConstante.FECHA_FIN_CERTIFICADO_ESCOLAR_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                    null, null, true, true);

            // Se valida el campo No 5 - Gardo cursado
            validateGradoCursado(lstHallazgos, arguments, line, lineNumber, ArchivoCampoNovedadConstante.GRADO_ACADEMICO_CURSADO,
                    ArchivoCampoNovedadConstante.GRADO_ACADEMICO_CURSADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES, true);

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
     * Valida el campo grado cursado teniendo en cuenta los parametros
     * @param lstHallazgos
     *        Lista que contiene los errores encontrados para la linea evaluada
     * @param arguments
     *        Informacion del archivo leido
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
    @SuppressWarnings("unchecked")
    private void validateGradoCursado(List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos, LineArgumentDTO arguments,
            Map<String, Object> line, Long lineNumber, String nombreCampo, String constanteMensaje, Integer longitudCampo,
            Boolean isRequired) {
        if (FieldValidatorUtil.validateNotNull(line, nombreCampo)) {
            // Se obtiene el valor del campo
            String valorCampo = (String) line.get(nombreCampo);
            // Se verifica que este dentro de los parametros de longitud
            if (FieldValidatorUtil.validateLength(valorCampo, longitudCampo)) {
                // Se valida el grado cursado
                List<GradoAcademico> listGradoCursado = (List<GradoAcademico>) arguments.getContext()
                        .get(ArchivoMultipleCampoConstants.LISTA_GRADOS_ACADEMICOS);
                GradoAcademico gradoAcademico = GetValueUtil.getGradoAcademico(listGradoCursado, valorCampo.toUpperCase());
                if (gradoAcademico == null) {
                    lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                }
            }
        }
        else {
            FieldValidatorUtil.createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
        }
    }

}