package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.Map;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de numero de registro<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorNumeroRegistrosTipo2 extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNumeroRegistrosTipo2.class);

    /** Constantes para mensajes */
    private static final String CANTIDAD_REGISTROS_TIPO_2 = "cantidad registros tipo 2";
    private static final String TIPO_PLANILLA = "tipo planilla";

    /**
     * Metodo se encarga de validar numero de registro
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = arg0.getLineValues();
        Integer cantidadRegistrosTipo2 = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        
        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_CANTIDAD_REGISTROS_TIPO_2));

        if(valor != null && !valor.toString().isEmpty()){
            cantidadRegistrosTipo2 = ((BigDecimal) valor).intValue();
        }

        if(cantidadRegistrosTipo2 == null){
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, CANTIDAD_REGISTROS_TIPO_2);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (cantidadRegistrosTipo2 != null && !cantidadRegistrosTipo2.toString().isEmpty()) {
            String tipoArchivo = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE));

            TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

            // sí la cantidad de registros tipo 2 indicada por el campo es igual a 0
            // y el tipo de archivo no es IR, se presenta el error
            if (cantidadRegistrosTipo2.equals(0) && !TipoArchivoPilaEnum.ARCHIVO_OI_IR.equals(tipoArchivoEnum)
                    && !TipoArchivoPilaEnum.ARCHIVO_OI_IPR.equals(tipoArchivoEnum)) {

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_VALOR_CERO_PARA_TIPO_ARCHIVO_DISTINTO_A_IR.getReadableMessage(idCampo,
                        cantidadRegistrosTipo2.toString(), tipoError, nombreCampo);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // sólo para los archivos tipo I o IR (los archivos de pensionados no tienen un tipo de planilla)
            if (TipoArchivoPilaEnum.ARCHIVO_OI_I.equals(tipoArchivoEnum) || TipoArchivoPilaEnum.ARCHIVO_OI_IR.equals(tipoArchivoEnum)) {

                String tipoPlanilla = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_PLANILLA));
                TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

                if (tipoPlanillaEnum != null) {
//                    // sí la cantidad de registros es 1 y el tipo de planilla es N, también se presenta error
//                    if (cantidadRegistrosTipo2.equals(1) && TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) {
//                        mensaje = MensajesValidacionEnum.ERROR_CAMPO_SOLO_CUENTA_CON_UN_REGISTRO_TIPO_DOS_PARA_TIPO_PLANILLA_N
//                                .getReadableMessage(idCampo, cantidadRegistrosTipo2.toString(), tipoError, nombreCampo);
//
//                        logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
//                        throw new FileProcessingException(mensaje);
//                    }
                }
                else {
                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_PLANILLA);

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
