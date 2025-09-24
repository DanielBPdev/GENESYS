package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo subtipo cotizante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorTipoAportanteSubtipoCotizante extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoAportanteSubtipoCotizante.class);

    /** Constantes para mensajes */
    private static final String TIPO_APORTANTE = "tipo de cotizante válido";
    private static final String SUBTIPO_COTIZANTE = "subtipo de cotizante válido";

    /**
     * Metodo se encarga de validar del campo subtipo cotizante
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        Integer subTipoCotizante = 0;
        Integer tipoAportante = null;

        Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SUBTIPO_COTIZANTE));
        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            subTipoCotizante = (Integer) valorCampo;
        }

        valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_APORTANTE));

        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
                tipoAportante = Integer.parseInt((String) valorCampo);
            }
            else if(valorCampo instanceof Integer){
                tipoAportante = (Integer) valorCampo;
            }
        }
        TipoAportanteEnum tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(tipoAportante);

        /** CONTROL DE CAMBIOS 224118: Se puede presentar el subtipo de cotizante cero (0) para identificar la no marcación del mismo */
        
        SubTipoCotizanteEnum subTipoCotizanteEnum = SubTipoCotizanteEnum.obtenerSubTipoCotizante(subTipoCotizante);

        if (tipoAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (subTipoCotizanteEnum == null) {
            // combinación común
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_COTIZANTE.getReadableMessage(idCampo,
                    subTipoCotizante.toString(), tipoError, nombreCampo, subTipoCotizante.toString(),
                    tipoAportanteEnum.getDescripcion());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        List<TipoAportanteEnum> tiposCotizanteValidos = definirTiposAportanteValidos(subTipoCotizanteEnum);

        if (tiposCotizanteValidos == null) {
            // no se obtiene un listado de tipos de cotizante, no se definió un subtipo válido
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUBTIPO_COTIZANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        else if (!tiposCotizanteValidos.contains(tipoAportanteEnum)) {
            // combinación común
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_COTIZANTE.getReadableMessage(idCampo,
                    subTipoCotizante.toString(), tipoError, nombreCampo,
                    (subTipoCotizanteEnum != null ? subTipoCotizanteEnum.getDescripcion() : subTipoCotizante.toString()),
                    tipoAportanteEnum.getDescripcion());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * Método para establecer el listado de los tipos de cotizantes que son válidos para aplicar un subtipo de cotizante dado
     * 
     * @param subTipoCotizanteEnum
     *        Subtipo de cotizante evaluado
     * @return <b>List<TipoCotizanteEnum></b>
     *         Listado de los tipos de cotizante que pueden presentar el subtipo de cotizante presentado
     */
    private List<TipoAportanteEnum> definirTiposAportanteValidos(SubTipoCotizanteEnum subTipoCotizanteEnum) {
        logger.debug("Inicia definirTiposCotizanteValidos(SubTipoCotizanteEnum)");

        List<TipoAportanteEnum> result = new ArrayList<>();

        /** CONTROL DE CAMBIOS 224118: cambian las combinaciones posibles por independientes y tipos cotizantes nuevos */
        switch (subTipoCotizanteEnum) {
            case SUBTIPO_01:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case SUBTIPO_02:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case SUBTIPO_03:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case SUBTIPO_04:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case SUBTIPO_05:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case SUBTIPO_06:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case SUBTIPO_09:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case SUBTIPO_10:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);           
                break;
            case SUBTIPO_11:
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                break;
            case SUBTIPO_12:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                break;
            default:
                break;
        }

        logger.debug("Finaliza definirTiposCotizanteValidos(SubTipoCotizanteEnum)");
        return result;
    }
}
