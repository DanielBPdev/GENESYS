package com.asopagos.fovis.cruce.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.fovis.constants.FileFieldConstants;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida la estructura de la hoja de cedula del archivo decruce<br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">jocorrea</a>
 */

public class CruceHojaReunidosLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CruceHojaReunidosLineValidator() {
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
            if (lineNumber > NumerosEnterosConstants.UNO) {
                // Se valida el campo No 1 - DOCUMENTO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.DOCUMENTO, FileFieldConstants.MSG_DOCUMENTO,
                        null, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, false, false);
                // Se valida el campo No 2 - TIPO DOCUMENTO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.TIPO_DOCUMENTO,
                        FileFieldConstants.MSG_TIPO_DOCUMENTO, null, null, false, false);
                // Se valida el campo No 3 - APELLIDOS Y NOMBRES
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.APELLIDOS_NOMBRES,
                        FileFieldConstants.MSG_APELLIDOS_NOMBRES, null, null, false, false);
                // Se valida el campo No 4 - MUNICIPIO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.MUNICIPIO, FileFieldConstants.MSG_MUNICIPIO,
                        null, null, false, false);
                // Se valida el campo No 5 - DEPARTAMENTO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.DEPARTAMENTO,
                        FileFieldConstants.MSG_DEPARTAMENTO, null, null, false, false);
            }
            else if (lineNumber == NumerosEnterosConstants.UNO) {
                // Se valida el campo No 1 - DOCUMENTO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.DOCUMENTO,
                        FileFieldConstants.MSG_DOCUMENTO, FileFieldConstants.MSG_DOCUMENTO);
                // Se valida el campo No 2 - TIPO DOCUMENTO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.TIPO_DOCUMENTO,
                        FileFieldConstants.MSG_TIPO_DOCUMENTO, FileFieldConstants.MSG_TIPO_DOCUMENTO);
                // Se valida el campo No 3 - APELLIDOS Y NOMBRES
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.APELLIDOS_NOMBRES,
                        FileFieldConstants.MSG_APELLIDOS_NOMBRES, FileFieldConstants.MSG_APELLIDOS_NOMBRES);
                // Se valida el campo No 4 - MUNICIPIO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.MUNICIPIO,
                        FileFieldConstants.MSG_MUNICIPIO, FileFieldConstants.MSG_MUNICIPIO);
                // Se valida el campo No 5 - DEPARTAMENTO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.DEPARTAMENTO,
                        FileFieldConstants.MSG_DEPARTAMENTO, FileFieldConstants.MSG_DEPARTAMENTO);
            }

        } finally {
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }
        }
    }

}