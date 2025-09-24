package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
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
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo cotizante planilla<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorTipoCotizantePlanilla extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoCotizantePlanilla.class);

    /**
     * Metodo se encarga de validar el tipo cotizante planilla
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Integer tipoCotizante = (Integer) arg0.getFieldValue();
            String tipoPlanilla = null;

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            try {
                tipoPlanilla = arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE)).toString();
            } catch (NullPointerException e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_PLANILLA);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);
            List<TipoCotizanteEnum> tiposCotizanteValidos = definirCotizantesValidos(tipoPlanillaEnum);

            boolean error = true;

            if (tiposCotizanteValidos != null && tiposCotizanteValidos.contains(tipoCotizanteEnum)) {
                error = false;
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_PERMITIDO_PARA_TIPO_PLANILLA.getReadableMessage(idCampo,
                        tipoCotizante.toString(), tipoError, nombreCampo,
                        (tipoCotizanteEnum != null ? tipoCotizanteEnum.getDescripcion() : tipoCotizante.toString()), tipoPlanilla);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

    /**
     * Método encargado de definir los tipos de cotizante válidos de acuerdo al tipo de planilla recibido
     * 
     * @param tipoPlanillaEnum
     * @return <b>List<TipoCotizanteEnum></b>
     *         Listado de tipos de cotizante válidos para el tipo de planilla
     */
    private List<TipoCotizanteEnum> definirCotizantesValidos(TipoPlanillaEnum tipoPlanillaEnum) {
        logger.debug("Inicia definirCotizantesValidos(TipoPlanillaEnum)");

        List<TipoCotizanteEnum> result = null;

        // se hace la validación de acuerdo a la combinación válida de tipos de cotizante y de planillas de acuerdo a la resolución
        if (tipoPlanillaEnum != null) {
            result = new ArrayList<>();

            switch (tipoPlanillaEnum) {
                case COTIZANTES_NOVEDAD_INGRESO:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    break;
                case EMPLEADOS:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES);
                    break;
                case APORTE_FALTANTE_SGP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                    break;
                case MADRES_SUSTITUTAS:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    break;
                case INDEPENDIENTES:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS);
                    break;
                case SENTENCIA_JUDICIAL:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);

                    break;
                case ESTUDIANTES:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); /** CC 224118 */
                    break;
                case MORA:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    break;
                case CORRECIONES:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    break;
                case SERVICIO_DOMESTICO:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    break;
                case EMPLEADOS_BENEFICIARIOS_SGP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
                    break;
                case PAGO_TERCEROS_UGPP:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    break;
                case EMPRESA_EN_PROCESO_LIQUIDACION:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    break;
                case INDEPENDIENTES_EMPRESAS:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROGRAMA_REINCORPORACION);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL);
                    break;
                case OBLIGACIONES:
                case ACUERDOS:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DESEMPLEADO_SCCF);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_UPC);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PAGO_POR_TERCERO);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PREPENSIONADO_AVS); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES); /** CC 224118 */
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA);
                    break;
                case PISO_PROTECCION_SOCIAL:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL);
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL);
                case CONTRIBUCION_SOLIDARIA:
                    result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO);
                    break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza definirCotizantesValidos(TipoPlanillaEnum)");
        return result;
    }
}
