package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase valida el valor unico<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorValorUnico extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorUnico.class);
    
    /** Constante mensaje */
    private static final String CAMPO_VACIO_VALOR_UNICO = "campo vacío";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lanzado cuando hay error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicio validate(FieldArgumentDTO)");
        
        String mensaje = null;

        String valorUnico = getParams().get(ConstantesParametroValidador.VALOR_UNICO);
        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
            
            Object valorCampo = arg0.getFieldValue();

            if (valorUnico == null || valorUnico.isEmpty()) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_CORRESPONDE_AL_VALOR_UNICO_ADMITIDO.getReadableMessage(
                        idCampo, valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString(), CAMPO_VACIO_VALOR_UNICO);
                
                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
 
            if (getParams().get(ConstantesParametroValidador.CONVERTIR_NUMERO).equals("1") && !nombreCampo.equals(ConstantesParametroValidador.NOMBRE_CAMPO_TIPO_I_REG_I_CODIGO_FORMATO) ) {
                valorUnico = Integer.toString(Integer.parseInt(valorUnico));
            }

            // se compara de acuerdo al tipo del campo
            boolean error = false;
            
            if(valorCampo instanceof String){
                if(!((String) valorCampo).equals(valorUnico)){
                    error = true;
                }
            }else if(valorCampo instanceof Integer){
                try{
                    if(((Integer) valorCampo).compareTo(Integer.parseInt(valorUnico)) != 0){
                        error= true;
                    }
                }catch(NumberFormatException nfe){
                    error = true;
                }
            }else if(valorCampo instanceof BigDecimal){
                try{
                    if(((BigDecimal) valorCampo).compareTo(new BigDecimal(valorUnico)) != 0){
                        error = true;
                    }
                }catch(Exception e){
                    error = true;
                }
            }
            
            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_CORRESPONDE_AL_VALOR_UNICO_ADMITIDO.getReadableMessage(
                        idCampo, valorCampo.toString(), tipoError, nombreCampo, valorCampo.toString(), valorUnico);
                
                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }
}
