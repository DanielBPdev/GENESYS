package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.Date;
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
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar la fecha de pago<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFechaPagoPlanillaAsociada extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaPagoPlanillaAsociada.class);

    /**
     * Metodo se encarga de validar la fecha de pago
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorFechaPagoPlanillaAsociada.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = " - ";

        //Se obtienen los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        Object fechaObjeto = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.FECHA_PAGO));
        String fechaPago = "";

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        
        if(fechaObjeto == null){
            fechaPago = null;
        }else if(fechaObjeto instanceof Date){
            fechaPago = FuncionesValidador.formatoFecha(fechaObjeto);
        }else if(fechaObjeto instanceof String){
            fechaPago = (String) fechaObjeto;
        }

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

        String tipoPlanilla = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.TIPO_PLANILLA));
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoPlanillaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_PLANILLA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        //Para planillas de tipo J el contenido del campo  planilla asociada es opcional.
        if(!TipoPlanillaEnum.SENTENCIA_JUDICIAL.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)){
            logger.info("ENTRA IF VALIDADOR FECHA");
            // se evalua que se cuente con el valor para los tipos que lo exigen
            if (listaTiposNoVacios.contains(tipoPlanillaEnum) && (fechaPago == null || fechaPago.equals(""))) {
                mensaje += MensajesValidacionEnum.ERROR_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo, "", tipoError, nombreCampo);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
                throw new FileProcessingException(mensaje);

            }

            // se evalua que el valor este vacío o cero para los casos que así lo exigen
            if (listaTiposVacios.contains(tipoPlanillaEnum) && fechaPago != null && !fechaPago.equals("")) {
                mensaje += MensajesValidacionEnum.ERROR_NO_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo, fechaPago, tipoError, nombreCampo);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        
        //Para planillas de tipo O el contenido del campo  planilla asociada es obligatorio.
        //GLPI 68360
        // if(TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)){
        //     // se evalua que se cuente con el valor para los tipos que lo exigen
        //     if (fechaPago == null || fechaPago.equals("")) {
        //         mensaje += MensajesValidacionEnum.ERROR_REQUIERE_FECHA_PAGO.getReadableMessage(idCampo, "", tipoError, nombreCampo);

        //         logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
        //         throw new FileProcessingException(mensaje);

        //     }
        // }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
