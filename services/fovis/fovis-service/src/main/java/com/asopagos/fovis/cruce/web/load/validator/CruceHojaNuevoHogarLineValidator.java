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

public class CruceHojaNuevoHogarLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CruceHojaNuevoHogarLineValidator() {
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
                // Se valida el campo No 3 - FECHA DE SOLICITUD 
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_SOLICITUD,
                        FileFieldConstants.MSG_FECHA_SOLICITUD, null, null, false, false);
                // Se valida el campo No 4 - ENTIDAD OTORGANTE
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.ENTIDAD_OTORGANTE,
                        FileFieldConstants.MSG_ENTIDAD_OTORGANTE, null, null, false, false);
                // Se valida el campo No 5 - CAJA DE COMPESACION
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.CAJA_COMPESACION,
                        FileFieldConstants.MSG_CAJA_COMPESACION, null, null, false, false);
                // Se valida el campo No 6 - ASIGNADO POSTERIOR A REPORTE
                FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, FileFieldConstants.ASIGNADO_POSTERIOR_REPORTE,
                        FileFieldConstants.MSG_ASIGNADO_POSTERIOR_REPORTE, null, null, false, false);

            }
            else if (lineNumber == NumerosEnterosConstants.UNO) {
                // Se valida el campo No 1 - IDENTIFICACIÓN
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.IDENTIFICACION,
                        FileFieldConstants.MSG_IDENTIFICACION, FileFieldConstants.MSG_IDENTIFICACION);
                // Se valida el campo No 2 - APELLIDOS Y NOMBRES
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.APELLIDOS_NOMBRES,
                        FileFieldConstants.MSG_APELLIDOS_NOMBRES, FileFieldConstants.MSG_APELLIDOS_NOMBRES);
                // Se valida el campo No 3 - FECHA DE SOLICITUD 
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.FECHA_SOLICITUD,
                        FileFieldConstants.MSG_FECHA_SOLICITUD, FileFieldConstants.MSG_FECHA_SOLICITUD);
                // Se valida el campo No 4 - ENTIDAD OTORGANTE
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.ENTIDAD_OTORGANTE,
                        FileFieldConstants.MSG_ENTIDAD_OTORGANTE, FileFieldConstants.MSG_ENTIDAD_OTORGANTE);
                // Se valida el campo No 5 - CAJA DE COMPESACION
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.CAJA_COMPESACION,
                        FileFieldConstants.MSG_CAJA_COMPESACION, FileFieldConstants.MSG_CAJA_COMPESACION);
                // Se valida el campo No 6 - ASIGNADO POSTERIOR A REPORTE
                FieldValidatorUtil.validateHeader(lstHallazgos, line, lineNumber, FileFieldConstants.ASIGNADO_POSTERIOR_REPORTE,
                        FileFieldConstants.MSG_ASIGNADO_POSTERIOR_REPORTE, FileFieldConstants.MSG_ASIGNADO_POSTERIOR_REPORTE);

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