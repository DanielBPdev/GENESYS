/**
 * 
 */
package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de número de la planilla<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorNumeroPlanillaAsociada extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNumeroPlanillaAsociada.class);

    /**
     * Metodo se encarga de validar numero de la planilla
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorNumeroPlanillaAsociada.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // Se obtiene los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        String tiposNoVacios = getParams().get(ConstantesParametroValidador.TIPOS_PLANILLA_ASOCIADA_NO_VACIA);
        List<TipoPlanillaEnum> listaTiposNoVacios = new ArrayList<>();

        for (String tipo : tiposNoVacios.split(",")) {
            TipoPlanillaEnum tipoTemporal = TipoPlanillaEnum.obtenerTipoPlanilla(tipo);

            if (tipoTemporal != null) {
                listaTiposNoVacios.add(tipoTemporal);
            }
        }

        String tiposVacios = getParams().get(ConstantesParametroValidador.TIPOS_PLANILLA_ASOCIADA_VACIA);
        List<TipoPlanillaEnum> listaTiposVacios = new ArrayList<>();

        for (String tipo : tiposVacios.split(",")) {
            TipoPlanillaEnum tipoTemporal = TipoPlanillaEnum.obtenerTipoPlanilla(tipo);

            if (tipoTemporal != null) {
                listaTiposVacios.add(tipoTemporal);
            }
        }

        String campoPlanillaAsociada = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_PLANILLA_ASOCIADA));

        String tipoPlanilla = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_PLANILLA));
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoPlanillaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_PLANILLA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        //Para planillas de tipo J el contenido del campo  planilla asociada es opcional.
        if(!TipoPlanillaEnum.SENTENCIA_JUDICIAL.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)){
            logger.info("ENTRA IF VALIDADOR PLANILLA");
            // se evalua que se cuente con el valor para los tipos que lo exigen
            if (listaTiposNoVacios.contains(tipoPlanillaEnum)
                    && (campoPlanillaAsociada == null || campoPlanillaAsociada.equals("") || campoPlanillaAsociada.equals("0"))) {

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo, "", tipoError,
                        nombreCampo, tipoPlanillaEnum.getDescripcion());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);

            }
        
            // se evalua que el valor este vacío o cero para los casos que así lo exigen
            if (listaTiposVacios.contains(tipoPlanillaEnum)
                    && (campoPlanillaAsociada != null && !campoPlanillaAsociada.equals("") && !campoPlanillaAsociada.equals("0"))) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo,
                        campoPlanillaAsociada, tipoError, nombreCampo, tipoPlanillaEnum.getDescripcion());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        
      //Para planillas de tipo O el contenido del campo  planilla asociada es obligatorio.
        //GLPI 68360
        // if(TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)){
        //     // se evalua que se cuente con el valor para los tipos que lo exigen
        //     if (campoPlanillaAsociada == null || campoPlanillaAsociada.equals("") || campoPlanillaAsociada.equals("0")) {

        //         mensaje = MensajesValidacionEnum.ERROR_CAMPO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampo, "", tipoError,
        //                 nombreCampo, tipoPlanillaEnum.getDescripcion());

        //         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
        //         throw new FileProcessingException(mensaje);

        //     }
        // }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
