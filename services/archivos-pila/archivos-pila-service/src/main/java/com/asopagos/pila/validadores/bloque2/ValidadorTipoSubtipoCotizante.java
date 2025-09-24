package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
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
public class ValidadorTipoSubtipoCotizante extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoSubtipoCotizante.class);

    /** Constantes para mensajes */
    private static final String TIPO_COTIZANTE = "tipo de cotizante válido";
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
        Integer tipoCotizante = null;

        Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SUBTIPO_COTIZANTE));
        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            subTipoCotizante = (Integer) valorCampo;
        }

        valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));

        if (valorCampo != null && !valorCampo.toString().isEmpty()) {
            if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
                tipoCotizante = Integer.parseInt((String) valorCampo);
            }
            else if(valorCampo instanceof Integer){
                tipoCotizante = (Integer) valorCampo;
            }
        }
        TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

        /** CONTROL DE CAMBIOS 224118: Se puede presentar el subtipo de cotizante cero (0) para identificar la no marcación del mismo */
        if (subTipoCotizante != null && subTipoCotizante.compareTo(0) != 0) {
            SubTipoCotizanteEnum subTipoCotizanteEnum = SubTipoCotizanteEnum.obtenerSubTipoCotizante(subTipoCotizante);

            if (tipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COTIZANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (subTipoCotizanteEnum == null) {
                // combinación común
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_COTIZANTE.getReadableMessage(idCampo,
                        subTipoCotizante.toString(), tipoError, nombreCampo, subTipoCotizante.toString(),
                        tipoCotizanteEnum.getDescripcion());

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            List<TipoCotizanteEnum> tiposCotizanteValidos = definirTiposCotizanteValidos(subTipoCotizanteEnum);

            if (tiposCotizanteValidos == null) {
                // no se obtiene un listado de tipos de cotizante, no se definió un subtipo válido
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, SUBTIPO_COTIZANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
            else if (!tiposCotizanteValidos.contains(tipoCotizanteEnum)) {
                // combinación común
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_PARA_TIPO_COTIZANTE.getReadableMessage(idCampo,
                        subTipoCotizante.toString(), tipoError, nombreCampo,
                        (subTipoCotizanteEnum != null ? subTipoCotizanteEnum.getDescripcion() : subTipoCotizante.toString()),
                        tipoCotizanteEnum.getDescripcion());

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
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
    private List<TipoCotizanteEnum> definirTiposCotizanteValidos(SubTipoCotizanteEnum subTipoCotizanteEnum) {
        logger.debug("Inicia definirTiposCotizanteValidos(SubTipoCotizanteEnum)");

        List<TipoCotizanteEnum> result = new ArrayList<>();

        /** CONTROL DE CAMBIOS 224118: cambian las combinaciones posibles por independientes y tipos cotizantes nuevos */
        switch (subTipoCotizanteEnum) {
            case SUBTIPO_01:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_02:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                break;
            case SUBTIPO_03:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_04:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_05:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_06:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_09:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_10:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                break;
            case SUBTIPO_11:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            case SUBTIPO_12:
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                break;
            default:
                break;
        }

        logger.debug("Finaliza definirTiposCotizanteValidos(SubTipoCotizanteEnum)");
        return result;
    }
}
