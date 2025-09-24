package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo cotizante aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorTipoCotizanteAportante extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoCotizanteAportante.class);

    /** Constantes para mensajes */
    private static final String TIPO_APORTANTE = "Tipo de aportante";
    private static final String TIPO_COTIZANTE = "Tipo de cotizante";

    /**
     * Metodo se encarga de validar el tipo cotizante
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicio validate(FieldArgumentDTO)");
        String mensaje = null;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Integer tipoCotizante = (Integer) arg0.getFieldValue();
            Integer tipoAportante = null;

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            try {
                tipoAportante = Integer
                        .parseInt(arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VARIABLE)).toString());
            } catch (NullPointerException e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_APORTANTE);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            TipoAportanteEnum tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(tipoAportante);
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            boolean error = false;
            
            if (tipoAportanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_APORTANTE);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
            if (tipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_COTIZANTE);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (tipoCotizanteEnum != null) {
                List<TipoAportanteEnum> tiposAportantesNoValidos = definirAportantesValidos(tipoCotizanteEnum);

                // sí los tipos de aportantes no válidos es nulo es porque no se reconoció el tipo de cotizante 
                if (tiposAportantesNoValidos == null) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ESTA_EN_TIPOS_COTIZANTE_EN_RESOLUCION.getReadableMessage(idCampo,
                            tipoCotizante.toString(), tipoError, nombreCampo, tipoCotizanteEnum.getDescripcion());

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
                else if (!tiposAportantesNoValidos.contains(tipoAportanteEnum)) {
                    error = true;
                }
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_PERMITIDO_PARA_TIPO_APORTANTE.getReadableMessage(idCampo,
                        tipoCotizante.toString(), tipoError, nombreCampo, tipoCotizanteEnum.getDescripcion(),
                        tipoAportanteEnum.getDescripcion());

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

    /**
     * Método para establecer los tipos de aportante válidos para un tipo de cotizante dado
     * 
     * @param tipoCotizanteEnum
     *        Tipo de cotizante evaluado
     * @return <b>List<TipoAportanteEnum></b>
     *         Listado de los tipos de aportante válidos para el caso evaluado
     */
    private List<TipoAportanteEnum> definirAportantesValidos(TipoCotizanteEnum tipoCotizanteEnum) {
        logger.debug("Inicia definirAportantesValidos(TipoCotizanteEnum)");

        List<TipoAportanteEnum> result = null;

        result = new ArrayList<>();

        // se compara cada tipo de cotizante con los tipos de aportante que no le pueden reportar de acuerdo a la resolución
        switch (tipoCotizanteEnum) {
            case TIPO_COTIZANTE_DEPENDIENTE:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_SERVICIO_DOMESTICO:
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_INDEPENDIENTE:
            case TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN:
            case TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD:
            case TIPO_COTIZANTE_PAGO_POR_TERCERO:
            case TIPO_COTIZANTE_PREPENSIONADO_AVS:
            case TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO:
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                break; 
            case TIPO_COTIZANTE_MADRE_SUSTITUTA:
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.ADMINISTRADORAS_HOGARES_BIENESTAR);
                break;
            case TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA:
            case TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                break;
            case TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO:
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC:
            case TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_ESTUDIANTES_REG_ESP:
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                break;
            case TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                break;
            case TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_DESEMPLEADO_SCCF:
            case TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES:
            case TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES:
            case TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI:
            case TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE:
            case TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ:
                result.add(TipoAportanteEnum.EMPLEADOR);
                break;
            case TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                break;
            case TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI:
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO:
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL:
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD:
            case TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD:
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP:
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                break;
            case TIPO_COTIZANTE_BENEFICIARIO_UPC:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.ADMINISTRADORAS_HOGARES_BIENESTAR);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_AFILIADO_PARTICIPE:
            case TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE:
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_IND_VOLUNTARIO_ARL:
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                result.add(TipoAportanteEnum.CONTRATANTE);
                break;
            case TIPO_COTIZANTE_EDIL_JAL:
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                break;
            case TIPO_COTIZANTE_PROGRAMA_REINCORPORACION:
                result.add(TipoAportanteEnum.PAGADOR_PROGRAMA_REINCORPORACION);
                break;
            case TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO:
                result.add(TipoAportanteEnum.PAGADOR_APORTES_PARAFISCALES_MAGISTERIO);
                break;
            case TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA:
                result.add(TipoAportanteEnum.PAGADOR_PRESTACION_HUMANITARIA);
                break;
            case TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL:
                result.add(TipoAportanteEnum.PAGADOR_SUBSISTEMA_NACIONAL_VOLUNTARIOS);
                break;
            case TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO:
                result.add(TipoAportanteEnum.EMPLEADOR);
                break;
            case TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                break;
            case TIPO_COTIZANTE_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ:
                result.add(TipoAportanteEnum.PAGADOR_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ);
                break;
            case TIPO_COTIZANTE_LEY_DE_SEGUNDAS_OPORTUNIDADES:
                result.add(TipoAportanteEnum.EMPLEADOR);
                result.add(TipoAportanteEnum.INDEPENDIENTE);
                result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                result.add(TipoAportanteEnum.AGREMIACIONES);
                result.add(TipoAportanteEnum.CTA);
                result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                result.add(TipoAportanteEnum.PAGADOR_SINDICAL);  
            break;
            case TIPO_COTIZANTE_MUJER_CON_APORTE:
            result.add(TipoAportanteEnum.INDEPENDIENTE);
            break;
            default:
                break;
        }

        logger.debug("Finaliza definirAportantesValidos(TipoCotizanteEnum)");
        return result;
    }

}
