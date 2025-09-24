package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
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
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo planilla aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorTiposPlanillaAportante extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTiposPlanillaAportante.class);

    /** Constantes para mensajes */
    private static final String TIPO_APORTANTE = "tipo aportante";
    private static final String TIPO_PLANILLA = "tipo planilla";

    /**
     * Metodo se encarga de validar del campo tipo planilla aportante
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtiene los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String tipoAportante = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.TIPO_APORTANTE));
        TipoAportanteEnum tipoAportanteEnum = null;
        try {
            tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(Integer.parseInt(tipoAportante));
        } catch (Exception e) {
        }

        String tipoPlanilla = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.TIPO_PLANILLA));
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (tipoAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoPlanillaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_PLANILLA);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        List<TipoAportanteEnum> tiposAportanteValidos = determinarTiposPlanillaValidos(tipoPlanillaEnum);

        // se valida que el tipo de planilla sea concordande con el tipo de aportante de acuerdo a la resolución

        if (!tiposAportanteValidos.contains(tipoAportanteEnum)) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_TIPO_PLANILLA_NO_VALIDA_PARA_TIPO_APORTANTE.getReadableMessage(idCampo,
                    tipoPlanilla, tipoError, nombreCampo, tipoPlanillaEnum.getDescripcion(), tipoAportanteEnum.getDescripcion());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * Método para listar los tipos de aportante que son válidos para presentar un determinado tipo de planilla
     * 
     * @param tipoPlanilla
     *        Tipo de planilla evaluada
     * @return <b>List<TipoAportanteEnum></b>
     *         Listado de los tipos de aportante que son válidos para presentar el tipo de planilla especificado
     */
    private List<TipoAportanteEnum> determinarTiposPlanillaValidos(TipoPlanillaEnum tipoPlanilla) {
        logger.debug("Inicia determinarTiposPlanillaValidos(TipoPlanillaEnum)");

        List<TipoAportanteEnum> result = null;

        if (tipoPlanilla != null) {
            result = new ArrayList<>();

            switch (tipoPlanilla) {
                case EMPLEADOS:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.PAGADOR_APORTES_PARAFISCALES_MAGISTERIO);
                    break;
                case INDEPENDIENTES_EMPRESAS:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.PAGADOR_PROGRAMA_REINCORPORACION);
                    result.add(TipoAportanteEnum.PAGADOR_PRESTACION_HUMANITARIA);
                    result.add(TipoAportanteEnum.PAGADOR_SUBSISTEMA_NACIONAL_VOLUNTARIOS);
                    result.add(TipoAportanteEnum.CONTRATANTE);
                    break;
                case COTIZANTES_NOVEDAD_INGRESO:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.PAGADOR_PROGRAMA_REINCORPORACION);
                    result.add(TipoAportanteEnum.PAGADOR_APORTES_PARAFISCALES_MAGISTERIO);
                    break;
                case INDEPENDIENTES:
                case SERVICIO_DOMESTICO:
                case MADRES_SUSTITUTAS:
                case CONTRIBUCION_SOLIDARIA:
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    break;
                case APORTE_FALTANTE_SGP:
                case EMPLEADOS_BENEFICIARIOS_SGP:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    break;
                case EMPRESA_EN_PROCESO_LIQUIDACION:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    break;
                case ESTUDIANTES:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    break;
                    case MORA:
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
                case SENTENCIA_JUDICIAL:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.ADMINISTRADORAS_HOGARES_BIENESTAR);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.PAGADOR_APORTES_PARAFISCALES_MAGISTERIO);
                 break;
                case PAGO_TERCEROS_UGPP:
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
                case CORRECIONES:
                    // se puede presentar para todos los tipos de aportante
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.ADMINISTRADORAS_HOGARES_BIENESTAR);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.PAGADOR_PROGRAMA_REINCORPORACION);
                    result.add(TipoAportanteEnum.PAGADOR_APORTES_PARAFISCALES_MAGISTERIO);
                    result.add(TipoAportanteEnum.PAGADOR_PRESTACION_HUMANITARIA);
                    result.add(TipoAportanteEnum.PAGADOR_SUBSISTEMA_NACIONAL_VOLUNTARIOS);
                    result.add(TipoAportanteEnum.TRABAJADOR_PAGO_APORTE_FALTANTE);
                    result.add(TipoAportanteEnum.CONTRATANTE);
                    break;
                case OBLIGACIONES:
                case ACUERDOS:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.UNIVERSIDADES_PUBLICAS);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_CONSEJALES);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                case  PISO_PROTECCION_SOCIAL:
                    result.add(TipoAportanteEnum.EMPLEADOR);
                    result.add(TipoAportanteEnum.INDEPENDIENTE);
                    result.add(TipoAportanteEnum.AGREMIACIONES);
                    result.add(TipoAportanteEnum.CTA);
                    result.add(TipoAportanteEnum.MISION_DIPLOMATICA);
                    result.add(TipoAportanteEnum.PAGADOR_SINDICAL);
                    result.add(TipoAportanteEnum.CONTRATANTE);
                    break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza determinarTiposPlanillaValidos(TipoPlanillaEnum)");
        return result;
    }

}
