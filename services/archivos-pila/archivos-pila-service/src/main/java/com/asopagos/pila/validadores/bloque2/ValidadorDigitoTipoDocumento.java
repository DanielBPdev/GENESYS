package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAPEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.pila.constants.ConstantesContexto;
import java.util.List;

/**
 * <b>Descripción:</b> Clase que se encarga de validar el tipo de documento<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorDigitoTipoDocumento extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDigitoTipoDocumento.class);

    /**
     * Metodo se encarga de validar el tipo de documento
     *
     * @param LineArgumentDTO objeto con la informacion a validar
     * @exception FileProcessingException lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        String tipoDocumento = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_DOCUMENTO));

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
        
        // se obtiene el tipo de persona
        Object valor = args.getContext().get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum((String) valor);
        TipoPersonaEnum tipoPersona = obtenerTipoPersona(valoresDeLinea, tipoArchivoEnum, args);
        
        

        if (tipoDocumento != null && !tipoDocumento.isEmpty()) {
            Integer digitoVerificacion = null;
            TipoIdentificacionEnum tipoDocumentoEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoDocumento);

            Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DIGITO_VERIFICACION));

            if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
                digitoVerificacion = Integer.parseInt((String) valorCampo);
            } else if (valorCampo instanceof Integer) {
                digitoVerificacion = (Integer) valorCampo;
            }

            if (TipoPersonaEnum.JURIDICA.equals(tipoPersona) && digitoVerificacion == null) {
                // los NIT requieren un dígito de verificación (incluyendo el cero)

                mensaje = MensajesValidacionEnum.ERROR_REQUIERE_DIGITO_VERIFICACION.getReadableMessage(idCampo, tipoDocumento, tipoError,
                        nombreCampo, tipoDocumento);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            } 
            
            /*else if (!TipoIdentificacionEnum.NIT.equals(tipoDocumentoEnum) && (digitoVerificacion == null || digitoVerificacion != 0)) {
                // los documentos diferentes a NIT, deben tener dígito de verificación cero
                mensaje = MensajesValidacionEnum.ERROR_NO_REQUIERE_DIGITO_VERIFICACION.getReadableMessage(idCampo, tipoDocumento, tipoError,
                        nombreCampo, tipoDocumento);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }*/
            logger.debug("Finaliza validate(LineArgumentDTO)");
        }
    }

    @SuppressWarnings("unchecked")
    private TipoPersonaEnum obtenerTipoPersona(Map<String, Object> valoresDeLinea, TipoArchivoPilaEnum tipoArchivoEnum,
            LineArgumentDTO args) {
        String firmaMetodo = "ValidadorDigitoTipoDocumento.obtenerTipoPersona(Map<String, Object>, TipoArchivoPilaEnum, "
                + "LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object valor = null;
        TipoPersonaEnum tipoPersona = null;

        try{
            // en archivos A-AP-I, el campo se encuentra en la línea
            if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                    && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())) {

                valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I137.getNombreCampo());
            } else if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                    && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivoEnum.getSubtipo())) {

                valor = valoresDeLinea.get(EtiquetaArchivoAEnum.A19.getNombreCampo());
            } else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())
                    && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivoEnum.getSubtipo())) {

                valor = valoresDeLinea.get(EtiquetaArchivoAPEnum.AP19.getNombreCampo());
                args.getContext().put(ConstantesContexto.TIPO_PERSONA, (String) valor);
            } // en archivos IP, el valor se debe buscar en el contexto
            else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())
                    && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())) {

                List<PasoValores> valoresRecibidos = (List<PasoValores>) args.getContext().get(ConstantesContexto.PASO_VARIABLES);
                for (PasoValores pasoValores : valoresRecibidos) {
                    if (pasoValores.getNombreVariable().equals(ConstantesContexto.TIPO_PERSONA)) {
                        valor = pasoValores.getValorVariable();
                        break;
                    }
                }

            }

            if (valor != null) {
                tipoPersona = TipoPersonaEnum.obtenerTipoPersona((String) valor);
            }

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        } catch(Exception e ){
            
            logger.error("********************* Error calculando tipo de persona " + firmaMetodo);
        } 
        
        return tipoPersona;
    }

}
