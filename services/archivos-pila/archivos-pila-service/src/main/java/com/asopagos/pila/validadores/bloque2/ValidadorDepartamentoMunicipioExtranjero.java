package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar el departamento municipio extranjero<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorDepartamentoMunicipioExtranjero extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDepartamentoMunicipioExtranjero.class);

    /**
     * Metodo se encarga de validar el municipio departamento
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String extranjero = null;
        String localidad = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        try {
            localidad = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_LOCALIDAD)).toString();
            extranjero = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_EXTRANJERO)).toString();
        } catch (NullPointerException e) {
        }

        
        int localidadLectura = 0;
       
        if (null != localidad  ){
            try{
                localidadLectura = Integer.parseInt(localidad);
            } catch(Exception e){
                logger.error("Error al transformar valores a enteros - " + localidad);
            }                 
        }
                
        // Ajuste por mantis 259725        
        /*if (localidad != null && localidadLectura != 0) {
            if (extranjero != null) {
                mensaje = MensajesValidacionEnum.ERROR_DEPARTAMENTO_MUNICIPIO_EXTRANJERO.getReadableMessage(idCampo, localidad, tipoError,
                        nombreCampo, localidad);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }*/
        
        logger.debug("Finaliza validate(LineArgumentDTO)");

    }

}
