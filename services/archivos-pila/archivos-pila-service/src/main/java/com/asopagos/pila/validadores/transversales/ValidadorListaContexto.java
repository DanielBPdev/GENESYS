package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import java.util.Set;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase maneja la validacion de la lista en el contexto<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorListaContexto extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorListaContexto.class);

    /** Constante para mensaje */
    private static final String LISTA_CONTEXTO = "valores de comprobación en el contexto";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            String llaveListaContexto = getParams().get(ConstantesParametroValidador.LLAVE_LISTA_CONTEXTO);
            boolean error = true;

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            Object valorCampo = arg0.getFieldValue();

            Set<String> listaContexto = (Set<String>) arg0.getContext().get(llaveListaContexto);

            if (listaContexto != null) {
                String listaReferencia = pasarListaAString(listaContexto);

                CICLO_CONTROL: for (String elementoListaContexto : listaContexto) {
                    // los elementos de la lista se comparan de acuerdo al tipo de dato del campo

                    if (valorCampo instanceof String) {
                        if (((String) valorCampo).equals(elementoListaContexto)) {
                            error = false;
                            break CICLO_CONTROL;
                        }
                    }

                    if (valorCampo instanceof Integer) {
                        try {
                            if (((Integer) valorCampo).compareTo(Integer.parseInt(elementoListaContexto)) == 0) {
                                error = false;
                                break CICLO_CONTROL;
                            }
                        } catch (NumberFormatException nfe) {
                        }
                    }

                    if (valorCampo instanceof BigDecimal) {
                        try {
                            if (((BigDecimal) valorCampo).compareTo(new BigDecimal(elementoListaContexto)) == 0) {
                                error = false;
                                break CICLO_CONTROL;
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                if (error) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ESTA_ENTRE_CODIGOS_ADMITIDOS.getReadableMessage(idCampo,
                            valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString(), listaReferencia);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, LISTA_CONTEXTO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
            logger.debug("Finaliza validate(FieldArgumentDTO)");
        }
    }

    /**
     * Metodo para crear un String con el contenido de la lista del contexto
     * 
     * @param listaContexto
     *        Lista de valores para comparar el campo
     * @return <b>String</b>
     *         Cadena con el contenido del listado del contexto
     */
    private String pasarListaAString(Set<String> listaContexto) {
        StringBuilder result = new StringBuilder();

        int count = 0;
        for (String elemento : listaContexto) {
            if (count++ > 0) {
                result.append(", ");
            }
            result.append(elemento);
        }

        return result.toString();
    }

}
