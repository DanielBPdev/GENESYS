package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Método que contiene la validación cruzada de un dígito de verificación respecto a un tipo de persona <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorValorTipoPersona extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorTipoPersona.class);

    /**
     * Metodo se encarga de validar a existencia de un valor respecto al tipo de persona
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
        String tipoPersona = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_PERSONA));

        // indicador para saber sí el valor debe ser vacío o cero para tipo persona Natural
        Boolean debeSerCero = false;
        if (getParams().get(ConstantesParametroValidador.MARCA_VALOR_CERO) != null) {
            debeSerCero = true;
        }

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        Boolean error = false;

        if (tipoPersona != null) {
            TipoPersonaEnum tipoPersonaEnum = TipoPersonaEnum.obtenerTipoPersona(tipoPersona);

            Object valorCampoReferencia = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_VALOR_COMPARACION));

            // la persona jurídica debe presentar el dato evaluado
            if (TipoPersonaEnum.JURIDICA.equals(tipoPersonaEnum)) {
                if (valorCampoReferencia == null) {
                    valorCampoReferencia = "";
                    error = true;
                }
                else if (valorCampoReferencia instanceof String && ((String) valorCampoReferencia).isEmpty()) {
                    error = true;
                }

                // se presenta el error de dato requerido no presente
                if (error) {
                    mensaje = MensajesValidacionEnum.ERROR_VALOR_OBLIGATORIO_TIPO_PERSONA.getReadableMessage(idCampo,
                            valorCampoReferencia.toString(), tipoError, nombreCampo, tipoPersonaEnum.getDescripcion());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else if (debeSerCero) {
                error = true;
                if (valorCampoReferencia != null) {
                    if (valorCampoReferencia instanceof String) {
                        if (((String) valorCampoReferencia).isEmpty()) {
                            error = false;
                        }
                        else {
                            try {
                                if (Integer.parseInt((String) valorCampoReferencia) == 0) {
                                    error = false;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                    else if (valorCampoReferencia instanceof Integer && ((Integer) valorCampoReferencia).compareTo(0) == 0) {
                        error = false;
                    }
                }
                else {
                    error = false;
                }

                // se presenta el error de dato no requerido presente
                if (error && tipoPersonaEnum != null) {
                    mensaje = MensajesValidacionEnum.ERROR_VALOR_NO_OBLIGATORIO_TIPO_PERSONA.getReadableMessage(idCampo,
                            valorCampoReferencia.toString(), tipoError, nombreCampo, tipoPersonaEnum.getDescripcion());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
