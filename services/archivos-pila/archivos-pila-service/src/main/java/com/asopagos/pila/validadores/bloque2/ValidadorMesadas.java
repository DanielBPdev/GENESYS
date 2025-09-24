package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de mesadas<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorMesadas extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorMesadas.class);
    
    /** Constantes para mensajes */
    private static final String TOTAL_MESADAS = "Valor total de mesadas pensionales";
    private static final String SUMATORIA_MESADAS = "Valor calculado de sumatoria de mesadas pensionales";

    /**
     * Metodo se encarga de validar las mesadas
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
        String mensaje = "";

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        
        BigDecimal totalMesadas = (BigDecimal) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TOTAL_MESADAS));
        
        if(totalMesadas == null){
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TOTAL_MESADAS);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        
        BigDecimal sumatoriaMesadas = (BigDecimal) arg0.getContext()
                .get(getParams().get(ConstantesParametroValidador.LLAVE_SUMATORIA_MESADAS));
        
        if(sumatoriaMesadas == null){
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUMATORIA_MESADAS);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (totalMesadas.compareTo(sumatoriaMesadas) != 0) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_COINCIDE_CON_SUMATORIA_MESADAS_PENSIONALES_REGISTROS_TIPO_2
                    .getReadableMessage(idCampo, totalMesadas.toPlainString(), tipoError, "1", nombreCampo, totalMesadas.toPlainString(),
                            sumatoriaMesadas.toPlainString());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
