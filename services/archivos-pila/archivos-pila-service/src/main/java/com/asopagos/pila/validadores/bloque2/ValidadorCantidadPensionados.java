package com.asopagos.pila.validadores.bloque2;

import java.util.HashSet;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Esta clase se encarga de realizar la validacion de cantidad de pensionado<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorCantidadPensionados extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCantidadPensionados.class);

    /** Constantes mensajes */
    private static final String LISTADO_PENSIONADOS = "listado de pensionados";
    private static final String CANTIDAD_PENSIONADOS = "cantidad de pensionados reportados";

    /**
     * Metodo encargado de realizar la validacion
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando falla la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(LineValidator)");
        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        
        Integer cantidadPensionados = (Integer) arg0.getContext().get(ConstantesContexto.CANTIDAD_PENSIONADOS);
        
        if(cantidadPensionados == null){
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, CANTIDAD_PENSIONADOS);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        HashSet<String> listaPensionados = (HashSet<String>) arg0.getContext().get(ConstantesContexto.LISTA_PENSIONADOS);
        
        if(listaPensionados == null){
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, LISTADO_PENSIONADOS);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        
        Integer cuentaPensionados = listaPensionados.size();

        if (!cantidadPensionados.equals(cuentaPensionados)) {
            mensaje = MensajesValidacionEnum.ERROR_CUENTA_PENSIONADOS_TIPO.getReadableMessage(idCampo,
                    cantidadPensionados.toString(), tipoError, "1", nombreCampo, cantidadPensionados.toString(),
                    cuentaPensionados.toString());

            logger.debug("Finaliza validate(LineValidator) - " + mensaje);
            throw new FileProcessingException(mensaje);

        }

        logger.debug("Finaliza validate(LineValidator)");
    }

}
