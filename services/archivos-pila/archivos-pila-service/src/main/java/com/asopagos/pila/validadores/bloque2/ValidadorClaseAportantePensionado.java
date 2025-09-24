package com.asopagos.pila.validadores.bloque2;

import java.util.List;
import java.util.Map;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * Esta clase realiza la validacion del aportante y pensionado
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 * @version 1.0.0
 *
 */
public class ValidadorClaseAportantePensionado extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorClaseAportantePensionado.class);

    /**
     * Metodo encargado de realizar la validacion
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando falla la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
        
        if(arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()){
            String mensaje = null;

            Map<String, Object> contexto = arg0.getContext();
            String claseAportante = (String) arg0.getFieldValue();

            // sí se tiene un paso de valores desde el archivo IP - IPR
            if (contexto.get(ConstantesContexto.PASO_VARIABLES) != null
                    && !((List<PasoValores>) contexto.get(ConstantesContexto.PASO_VARIABLES)).isEmpty()) {

                List<PasoValores> valoresRecibidos = (List<PasoValores>) contexto.get(ConstantesContexto.PASO_VARIABLES);
                String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
                String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
                String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

                for (PasoValores pasoValores : valoresRecibidos) {

                    // se compara el 
                    if (pasoValores.getNombreVariable().equals(ConstantesContexto.CLASE_APORTANTE)
                            && !pasoValores.getValorVariable().equals("0") && !pasoValores.getValorVariable().equals(claseAportante)) {

                        // sí el campo se encuentra, se le agrega al contexto, con lo cual se notifica que fue usado y se puede eliminar
                        contexto.put(ConstantesContexto.CLASE_APORTANTE, claseAportante);

                        mensaje = MensajesValidacionEnum.ERROR_CLASIFICACION_ARCHIVO_DETALLE.getReadableMessage(
                                idCampo, claseAportante, tipoError, nombreCampo, claseAportante,
                                pasoValores.getValorVariable());

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }

                // sí el campo se encuentra, se le agrega al contexto, con lo cual se notifica que fue usado y se puede eliminar
                contexto.put(ConstantesContexto.CLASE_APORTANTE, claseAportante);
            }
            else {
                if (claseAportante != null) {
                    // sí no se recibe el valor, se pasa al contexto para que el orquestador lo prepare para su uso durante la lectura IP -IPR
                    contexto.put(ConstantesContexto.CLASE_APORTANTE, claseAportante);
                }
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
