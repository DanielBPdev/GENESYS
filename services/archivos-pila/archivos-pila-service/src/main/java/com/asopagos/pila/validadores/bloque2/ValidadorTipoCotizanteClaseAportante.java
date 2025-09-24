package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo cotizante clase aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorTipoCotizanteClaseAportante extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoCotizanteClaseAportante.class);

    /**
     * Metodo se encarga de validar el tipo cotizante clase aportante
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "validate(FieldArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Integer tipoCotizante = (Integer) arg0.getFieldValue();
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            String claseAportante = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE));
            ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            if (tipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_COTIZANTE);

                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (claseAportanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.CLASE_APORTANTE);

                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            List<ClaseAportanteEnum> clasesAportanteNoValidas = definirClasesAportenteNoValidas(tipoCotizanteEnum);

            if (clasesAportanteNoValidas.contains(claseAportanteEnum)) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_PERMITIDO_PARA_CLASE_DE_APORTANTE.getReadableMessage(idCampo,
                        tipoCotizante.toString(), tipoError, nombreCampo, tipoCotizanteEnum.getDescripcion(),
                        claseAportanteEnum.getDescripcion());

                logger.debug(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método para listar las clases de aportante que NO son válidas para reportar a un tipo de cotizante determinado
     * 
     * @param tipoCotizante
     *        Tipo de cotizante consultado
     * @return <b>List<ClaseAportanteEnum></b>
     *         Listado de las clases de aportante que NO son válidas para reportar al tipo de cotizante consultado
     */
    private List<ClaseAportanteEnum> definirClasesAportenteNoValidas(TipoCotizanteEnum tipoCotizante) {
        logger.debug("Inicia definirClasesAportenteNoValidas(TipoCotizanteEnum)");

        List<ClaseAportanteEnum> result = null;

        if (tipoCotizante != null) {
            result = new ArrayList<>();

            // se compara cada tipo de cotizante con las clases de aportante que le pueden reportar de acuerdo a la resolución
            switch (tipoCotizante) {
                case TIPO_COTIZANTE_DEPENDIENTE:
                case TIPO_COTIZANTE_BENEFICIARIO_UPC:
                case TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL:
                case TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA:
                case TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES: /** CONTROL CAMBIOS 224118: Tipo nuevo */
                    // pueden ser reportados por cualquier clase de aportante
                    break;
                case TIPO_COTIZANTE_SERVICIO_DOMESTICO:
                case TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN:
                case TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD:
                case TIPO_COTIZANTE_PAGO_POR_TERCERO:
                case TIPO_COTIZANTE_PREPENSIONADO_AVS:
                case TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO:
                case TIPO_COTIZANTE_INDEPENDIENTE: /** CONTROL CAMBIOS 224118: Cambio de condiciones */
                    result.add(ClaseAportanteEnum.CLASE_A);
                    result.add(ClaseAportanteEnum.CLASE_B);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    break;
                case TIPO_COTIZANTE_MADRE_SUSTITUTA:
                case TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL:
                case TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL:
                case TIPO_COTIZANTE_IND_VOLUNTARIO_ARL: /** CONTROL CAMBIOS 224118: Tipo nuevo */
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    break; 
                case TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA:
                case TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA:
                case TIPO_COTIZANTE_ESTUDIANTES_REG_ESP:
                case TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD:
                case TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR:
                case TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL:
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case TIPO_COTIZANTE_DESEMPLEADO_SCCF:
                case TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO:
                case TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC:
                case TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI:
                case TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO:
                case TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL:
                case TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES:
                case TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES:
                case TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI:
                case TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE:
                case TIPO_COTIZANTE_AFILIADO_PARTICIPE:
                case TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ:
                case TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE:
                case TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO: /** CONTROL CAMBIOS 224118: Tipo nuevo */
                case TIPO_COTIZANTE_PROGRAMA_REINCORPORACION:
                case TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA:
                case TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL:
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD:
                case TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD:
                case TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP:
                case TIPO_COTIZANTE_EDIL_JAL:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO:
                    result.add(ClaseAportanteEnum.CLASE_B);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO:
                    result.add(ClaseAportanteEnum.CLASE_D);
                    break;
                case TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ:
                    result.add(ClaseAportanteEnum.CLASE_B);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES:
                    result.add(ClaseAportanteEnum.CLASE_D);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza definirClasesAportenteNoValidas(TipoCotizanteEnum)");
        return result;
    }
}
