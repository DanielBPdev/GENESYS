package com.asopagos.entidaddescuento.load.validator;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidaddescuento.constants.CamposArchivoConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.dto.FileArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.imp.FileNameValidator;

public class EntidadDescuentoFileValidator extends FileNameValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(EntidadDescuentoLineValidator.class);

    /**
     * Inicializacion de la lista de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.imp.FileNameValidator#validate(co.com.heinsohn.lion.fileprocessing.fileloader.dto.FileArgumentDTO)
     */
    @Override
    public void validate(FileArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia la validación del nombre del archivo de descuentos");

        try {
            lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();

            String pattern = getParams().get(CamposArchivoConstants.FILE_NAME_PATTERN);
            if (!arg0.getFileLoadedName().matches(pattern)) {
                lstHallazgos.add(crearHallazgo(arg0.getFileLoadedName(), "Nombre del archivo invalido"));
            }
        } finally {
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arg0.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
        }
        logger.debug("Finaliza la validación del nombre del archivo de descuentos");
    }
    
    /**
     * Método quecrea un hallazgo según la información ingresada
     * 
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(new Long(0));
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }

}
