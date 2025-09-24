package com.asopagos.pila.validadores.bloque2;

import java.util.List;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoAPEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de digito verificacion<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFuncionDigitoVerificacion extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFuncionDigitoVerificacion.class);

    /**
     * Metodo se encarga de validar la cantidad final
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorFuncionDigitoVerificacion.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String id = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NUMERO_ID));

        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DIGITO_VERIFICACION));
        Integer digVer = null;
        if (valor != null && !valor.toString().isEmpty()) {
            digVer = (Integer) valor;
        }

        // se obtiene el tipo de persona
        valor = args.getContext().get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum((String) valor);
        TipoPersonaEnum tipoPersona = obtenerTipoPersona(valoresDeLinea, tipoArchivoEnum, args);

        String tipoIdentificacion = null;

        if (getParams().get(ConstantesParametroValidador.CAMPO_TIPO_DOCUMENTO) == null) {
            // cuando no se recibe parámetro, se considera que se está enviando el ID de la administradora (NIT)
            tipoIdentificacion = "NI";
        }
        else {
            tipoIdentificacion = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_DOCUMENTO));
        }

        TipoIdentificacionEnum tipoIdentificacionEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (tipoPersona == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_PERSONA);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FileProcessingException(mensaje);
        }

        if (id == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.ID);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new FileProcessingException(mensaje);
        }

        if (digVer != null && TipoIdentificacionEnum.NIT.equals(tipoIdentificacionEnum)) {

            Short digVerCalculado = FuncionesValidador.calcularDigitoVarificacion(id);

            if (digVerCalculado == null) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NUMERO_IDENTIFICACION_ALFANUMERICO.getReadableMessage(idCampo, id, tipoError,
                        nombreCampo, id);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new FileProcessingException(mensaje);
            }
            else if (digVerCalculado.compareTo(digVer.shortValue()) != 0 && TipoPersonaEnum.JURIDICA.equals(tipoPersona)) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_COINCIDE_CON_CALCULO_DIGITO.getReadableMessage(idCampo, digVer.toString(),
                        tipoError, nombreCampo, digVer.toString(), digVerCalculado.toString());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                throw new FileProcessingException(mensaje);
            }
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    @SuppressWarnings("unchecked")
    private TipoPersonaEnum obtenerTipoPersona(Map<String, Object> valoresDeLinea, TipoArchivoPilaEnum tipoArchivoEnum,
            LineArgumentDTO args) {
        String firmaMetodo = "ValidadorFuncionDigitoVerificacion.obtenerTipoPersona(Map<String, Object>, TipoArchivoPilaEnum, "
                + "LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Object valor = null;
        TipoPersonaEnum tipoPersona = null;

        // en archivos A-AP-I, el campo se encuentra en la línea
        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())) {

            valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I137.getNombreCampo());
        }
        else if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivoEnum.getSubtipo())) {

            valor = valoresDeLinea.get(EtiquetaArchivoAEnum.A19.getNombreCampo());
        }
        else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())
                && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(tipoArchivoEnum.getSubtipo())) {

            valor = valoresDeLinea.get(EtiquetaArchivoAPEnum.AP19.getNombreCampo());
            args.getContext().put(ConstantesContexto.TIPO_PERSONA, (String) valor);
        }
        // en archivos IP, el valor se debe buscar en el contexto
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
        return tipoPersona;
    }

}
