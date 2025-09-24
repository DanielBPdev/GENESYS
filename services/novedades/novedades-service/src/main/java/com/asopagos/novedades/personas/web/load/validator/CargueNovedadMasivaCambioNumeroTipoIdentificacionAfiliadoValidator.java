package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.novedades.constants.ArchivoCampoNovedadCambioTipoNumeroMasivoConstante;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

public class CargueNovedadMasivaCambioNumeroTipoIdentificacionAfiliadoValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CargueNovedadMasivaCambioNumeroTipoIdentificacionAfiliadoValidator() {
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
            // Se valida el campo No 1 - Tipo de identificación actual del afiliado
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_ACTUAL_AFILIADO,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, true,
                    false, true);

            // Se valida el campo No 2 - Número de identificación actual del afiliado
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_AFILIADO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                    null, null, true, false);

            // Se valida el campo No 3 - Tipo de identificación nuevo del afiliado 
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_AFILIADO,
                    ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, true,
                    false, true);

            // Se valida el campo No 3 - Número de identificación nuevo del afiliado
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_AFILIADO,
                    ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                    null, null, true, false);
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