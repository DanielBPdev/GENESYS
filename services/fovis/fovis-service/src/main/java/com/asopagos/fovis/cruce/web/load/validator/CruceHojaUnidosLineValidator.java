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

public class CruceHojaUnidosLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CruceHojaUnidosLineValidator() {
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
                // Se valida el campo No 1 - IDENTIFICACIÓN
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.IDENTIFICACION,
                        FileFieldConstants.MSG_IDENTIFICACION, null, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, false, false);
                // Se valida el campo No 2 - APELLIDOS Y NOMBRES
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.APELLIDOS_NOMBRES,
                        FileFieldConstants.MSG_APELLIDOS_NOMBRES, null, null, false, false);
                // Se valida el campo No 3 - FOLIO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.FOLIO, FileFieldConstants.MSG_FOLIO, null,
                        null, false, false);
                // Se valida el campo No 4 - SEXO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.SEXO, FileFieldConstants.MSG_SEXO, null,
                        null, false, false);
                // Se valida el campo No 5 - PARENTESCO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.PARENTESCO,
                        FileFieldConstants.MSG_PARENTESCO, null, null, false, false);
                // Se valida el campo No 6 - DEPARTAMENTO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.DEPARTAMENTO,
                        FileFieldConstants.MSG_DEPARTAMENTO, null, null, false, false);
                // Se valida el campo No 7 - MUNICIPIO
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.MUNICIPIO, FileFieldConstants.MSG_MUNICIPIO,
                        null, null, false, false);

            }
            else if (lineNumber == NumerosEnterosConstants.UNO) {
                // Se valida el campo No 1 - IDENTIFICACIÓN
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.IDENTIFICACION,
                        FileFieldConstants.MSG_IDENTIFICACION, FileFieldConstants.MSG_IDENTIFICACION);
                // Se valida el campo No 2 - APELLIDOS Y NOMBRES
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.APELLIDOS_NOMBRES,
                        FileFieldConstants.MSG_APELLIDOS_NOMBRES, FileFieldConstants.MSG_APELLIDOS_NOMBRES);
                // Se valida el campo No 3 - FOLIO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.FOLIO, FileFieldConstants.MSG_FOLIO,
                        FileFieldConstants.MSG_FOLIO);
                // Se valida el campo No 4 - SEXO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.SEXO, FileFieldConstants.MSG_SEXO,
                        FileFieldConstants.MSG_SEXO);
                // Se valida el campo No 5 - PARENTESCO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.PARENTESCO,
                        FileFieldConstants.MSG_PARENTESCO, FileFieldConstants.MSG_PARENTESCO);
                // Se valida el campo No 6 - DEPARTAMENTO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.DEPARTAMENTO,
                        FileFieldConstants.MSG_DEPARTAMENTO, FileFieldConstants.MSG_DEPARTAMENTO);
                // Se valida el campo No 7 - MUNICIPIO
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.MUNICIPIO,
                        FileFieldConstants.MSG_MUNICIPIO, FileFieldConstants.MSG_MUNICIPIO);

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