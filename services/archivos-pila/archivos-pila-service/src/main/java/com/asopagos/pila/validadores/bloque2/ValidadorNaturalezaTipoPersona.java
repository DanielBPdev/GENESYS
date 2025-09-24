package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que se encarga de validar la Naturaleza del tipo de persona<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorNaturalezaTipoPersona extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNaturalezaTipoPersona.class);
    private static final String TIPO_APORTANTE = "tipo aportante";
    private static final String NATURALEZA_JURIDICA = "naturaleza jurídica";

    /**
     * Metodo se encarga de validar la naturaliza juridica de la persona
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_APORTANTE));

        Integer tipoAportante = null;

        if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
            tipoAportante = Integer.parseInt((String) valorCampo);
        }
        else if(valorCampo instanceof Integer){
            tipoAportante = (Integer) valorCampo;
        }

        TipoAportanteEnum tipoAportanteEnum = null;

        try {
            tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(tipoAportante);
        } catch (Exception e) {
        }

        valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_NATURALEZA_APORTANTE));

        Integer naturalezaAportante = null;
        NaturalezaJuridicaEnum naturalezaJuridicaEnum = null;

        if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
            naturalezaAportante = Integer.parseInt((String) valorCampo);
        }
        else if(valorCampo instanceof Integer){
            naturalezaAportante = (Integer) valorCampo;
        }

        try {
            naturalezaJuridicaEnum = NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(naturalezaAportante);
        } catch (Exception e) {
        }

        if (tipoAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (naturalezaJuridicaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, NATURALEZA_JURIDICA);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        List<NaturalezaJuridicaEnum> naturalezasValidas = definirNaturalezasValidas(tipoAportanteEnum);

        if (!naturalezasValidas.contains(naturalezaJuridicaEnum)) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_APORTANTE.getReadableMessage(idCampo,
                    naturalezaJuridicaEnum.getDescripcion(), tipoError, nombreCampo, naturalezaJuridicaEnum.getDescripcion(),
                    tipoAportanteEnum.getDescripcion());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * @param tipoAportanteEnum
     * @return
     */
    private List<NaturalezaJuridicaEnum> definirNaturalezasValidas(TipoAportanteEnum tipoAportante) {
        logger.debug("Inicia definirNaturalezasValidas(TipoAportanteEnum)");

        List<NaturalezaJuridicaEnum> result = null;

        if (tipoAportante != null) {
            result = new ArrayList<NaturalezaJuridicaEnum>();

            switch (tipoAportante) {
                case EMPLEADOR:
                case PAGADOR_SINDICAL:
                    result.add(NaturalezaJuridicaEnum.PUBLICA);
                    result.add(NaturalezaJuridicaEnum.PRIVADA);
                    result.add(NaturalezaJuridicaEnum.MIXTA);
                    break;
                case INDEPENDIENTE:
                case AGREMIACIONES:
                case CTA:
                case ADMINISTRADORAS_HOGARES_BIENESTAR:
                case TRABAJADOR_PAGO_APORTE_FALTANTE:
                    result.add(NaturalezaJuridicaEnum.PRIVADA);
                    break;
                case UNIVERSIDADES_PUBLICAS:
                case PAGADOR_CONSEJALES:
                case PAGADOR_PROGRAMA_REINCORPORACION:
                case PAGADOR_APORTES_PARAFISCALES_MAGISTERIO: 
                case PAGADOR_PRESTACION_HUMANITARIA:
                    result.add(NaturalezaJuridicaEnum.PUBLICA);
                    break;
                case MISION_DIPLOMATICA:
                    result.add(NaturalezaJuridicaEnum.ORGANISMOS_MULTILATERALES);
                    result.add(NaturalezaJuridicaEnum.ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS);
                    break;
                case PAGADOR_SUBSISTEMA_NACIONAL_VOLUNTARIOS:
                    result.add(NaturalezaJuridicaEnum.PUBLICA);
                    result.add(NaturalezaJuridicaEnum.PRIVADA);
                    break;
                case CONTRATANTE:
                    result.add(NaturalezaJuridicaEnum.PUBLICA);
                    result.add(NaturalezaJuridicaEnum.PRIVADA);
                    result.add(NaturalezaJuridicaEnum.MIXTA);
                    result.add(NaturalezaJuridicaEnum.ORGANISMOS_MULTILATERALES);
                    result.add(NaturalezaJuridicaEnum.ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS);
                break;
                case PAGADOR_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ:
                result.add(NaturalezaJuridicaEnum.PUBLICA);
            break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza definirNaturalezasValidas(TipoAportanteEnum)");
        return result;
    }

}
