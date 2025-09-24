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

public class CruceHojaFechaCorteLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CruceHojaFechaCorteLineValidator() {
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
                // Se valida el campo No 1 - NIT ENTIDAD
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.NIT_ENTIDAD,
                        FileFieldConstants.MSG_NIT_ENTIDAD, null, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, false, false);
                // Se valida el campo No 2 - ENTIDAD
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.ENTIDAD,
                        FileFieldConstants.MSG_ENTIDAD, null, null, false, false);
                // Se valida el campo No 3 - TIPO INFORMACION
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.TIPO_INFORMACION,
                        FileFieldConstants.MSG_TIPO_INFORMACION, null, null, false, false);
                // Se valida el campo No 4 - FECHA DE CORTE 
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_CORTE,
                        FileFieldConstants.MSG_FECHA_CORTE, null, null, false, false);
                // Se valida el campo No 5 - FECHA DE ACTUALIZACION
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_ACTUALIZACION,
                        FileFieldConstants.MSG_FECHA_ACTUALIZACION, null, null, false, false);

            }
            else if (lineNumber == NumerosEnterosConstants.UNO) {
                // Se valida el campo No 1 - NIT ENTIDAD
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.NIT_ENTIDAD,
                        FileFieldConstants.MSG_NIT_ENTIDAD, FileFieldConstants.MSG_NIT_ENTIDAD);
                // Se valida el campo No 2 - ENTIDAD
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.ENTIDAD,
                        FileFieldConstants.MSG_ENTIDAD, FileFieldConstants.MSG_ENTIDAD);
                // Se valida el campo No 3 - TIPO INFORMACION
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.TIPO_INFORMACION,
                        FileFieldConstants.MSG_TIPO_INFORMACION, FileFieldConstants.MSG_TIPO_INFORMACION);
                // Se valida el campo No 4 - FECHA DE CORTE 
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_CORTE,
                        FileFieldConstants.MSG_FECHA_CORTE, FileFieldConstants.MSG_FECHA_CORTE);
                // Se valida el campo No 5 - FECHA DE ACTUALIZACION
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_ACTUALIZACION,
                        FileFieldConstants.MSG_FECHA_ACTUALIZACION, FileFieldConstants.MSG_FECHA_ACTUALIZACION);

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