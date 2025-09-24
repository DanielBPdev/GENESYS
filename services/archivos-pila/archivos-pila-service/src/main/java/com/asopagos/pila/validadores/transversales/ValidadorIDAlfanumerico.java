package com.asopagos.pila.validadores.transversales;

import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que se encarga de llevar a cabo la persistencia de los registros tipo 2 del archivo I<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorIDAlfanumerico extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorIDAlfanumerico.class);

    /** Constantes mensaje errors */
    private static final String TIPO_IDENTIFICACION = "tipo documento de identificación válido";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtiene los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String id = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_ID));
        String tipoId = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_ID));

        // la validación sólo se lleva a cabo cuando se cuenta con ambos valores
        if (id != null && tipoId != null) {
            TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId);

            if (tipoIdentificacionEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_IDENTIFICACION);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            boolean esAlfanumerico= false;
            boolean esNumerico = false;
            boolean puedeTenerCaracteres= false;

            // Se valida sí el ID contiene caracteres alfanuméricos
            /*
            try {
                Long.parseLong(id);
                tieneCaracteres = false;
            } catch (NumberFormatException e) {
                tieneCaracteres = true;
            }
            */
            
            String expAlfNum ="^[\\da-zA-Z]{1,}$";
            String expNum ="[0-9]+";

            switch(tipoIdentificacionEnum){
                case PERM_ESP_PERMANENCIA:
                    logger.info("Jamv - INGRESA A PERMISO ESPECIAL DE PERMANENCIA");
                    if (id.length() > 15 || id.length() < 15) {
                        mensaje = MensajesValidacionEnum.ERROR_LONGITUD_PERM_ESP_PERMANENCIA.getReadableMessage(idCampo, id, tipoError, nombreCampo,
                        id, tipoId);

                        logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                    break;
                case PERM_PROT_TEMPORAL:
                    logger.info("Jamv - INGRESA A PERMISO ESPECIAL DE PERMANENCIA");
                    if (id.length() > 8) {
                        mensaje = MensajesValidacionEnum.ERROR_LONGITUD_PERM_PROT_TEMPORAL.getReadableMessage(idCampo, id, tipoError, nombreCampo,
                        id, tipoId);

                        logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                    break;
                default:
                    break;
                }
 


            if(id.matches(expAlfNum)){             
            	esAlfanumerico = true;
            }
            
            if(id.matches(expNum)){
            	esNumerico = true;
            }
            

            // se valida que el tipo de documento admita o no caracteres alfanuméricos
            switch (tipoIdentificacionEnum) {
                case PASAPORTE:
                case CARNE_DIPLOMATICO:
                case CEDULA_EXTRANJERIA:
                    puedeTenerCaracteres = true;
                    break;
                default:
                    puedeTenerCaracteres = false;
                    break;
            }
            
            if (!esAlfanumerico && !esNumerico) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_PRESENTA_INCONSISTENCIAS.getReadableMessage(idCampo, id, tipoError, nombreCampo,
                        id, tipoId);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // se evaluan los casos no válidos
            if (puedeTenerCaracteres && id.isEmpty()) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_SOLO_ADMITE_ALFANUMERICOS.getReadableMessage(idCampo, id, tipoError,
                        nombreCampo, id, tipoId);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
            
            if (puedeTenerCaracteres && !esNumerico && !esAlfanumerico) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_SOLO_ADMITE_ALFANUMERICOS.getReadableMessage(idCampo, id, tipoError,
                        nombreCampo, id, tipoId);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (!puedeTenerCaracteres && !esNumerico) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ADMITE_ALFANUMERICOS.getReadableMessage(idCampo, id, tipoError, nombreCampo,
                        id, tipoId);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
