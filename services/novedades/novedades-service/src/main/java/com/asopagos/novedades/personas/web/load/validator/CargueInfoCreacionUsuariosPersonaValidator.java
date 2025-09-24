package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.novedades.constants.CamposGestionArchivosUsuariosConstants;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de creacion de usuarios persona masivo<br/>
 * 
 *
 */

public class CargueInfoCreacionUsuariosPersonaValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public CargueInfoCreacionUsuariosPersonaValidator() {
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
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber, CamposGestionArchivosUsuariosConstants.TIPO_IDENTIFICACION_PERSONA,
                    CamposGestionArchivosUsuariosConstants.TIPO_IDENTIFICACION_PERSONA, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, true,
                    false, true);

            // Se valida el campo No 2 - Número de identificación del beneficiario
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, CamposGestionArchivosUsuariosConstants.NUMERO_IDENTIFICACION_PERSONA,
                    CamposGestionArchivosUsuariosConstants.NUMERO_IDENTIFICACION_PERSONA, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                    null, null, true, false);

        } finally {
            ((List<Long>) arguments.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(CamposGestionArchivosUsuariosConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }
        }
    }

}