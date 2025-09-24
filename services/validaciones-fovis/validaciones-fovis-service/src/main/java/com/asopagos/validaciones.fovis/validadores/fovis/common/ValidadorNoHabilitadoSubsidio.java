package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validadador que verifica si una persona esta habilitada para adquirir un subsidio de vivienda (v28)
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorNoHabilitadoSubsidio extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorNoHabilitadoSubsidio.execute");
        try {

            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

                if ((tipoIdentificacion != null && !tipoIdentificacion.equals(""))
                        && (numeroIdentificacion != null && !numeroIdentificacion.equals(""))) {

                    if (inhabilitadoParaSubsidio(tipoIdentificacion, numeroIdentificacion)) {
                        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_HABILITADO_SUBSIDIO),
                                ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_NO_HABILITADO_SUBSIDIO,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                }
                else {
                    logger.debug("No evaluado - No llegaron todos los parámetros");
                    return crearMensajeNoEvaluado();
                }
            }
            else {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }

            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_NO_HABILITADO_SUBSIDIO);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     * 
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_NO_HABILITADO_SUBSIDIO),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_NO_HABILITADO_SUBSIDIO,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

    /**
     * Metodo que evalua si una persona esta inhabilitada para recibir subsidio de
     * vivienda
     * 
     * @param tipoIdentificacion
     *        Tipo de identificacion de la persona
     * @param numeroIdentificacion
     *        Numero de identificacion de la persona
     * @return true si la persona esta inhabilitada para recibir el subsidio
     */
    @SuppressWarnings("unchecked")
    private boolean inhabilitadoParaSubsidio(String tipoIdentificacion, String numeroIdentificacion) {

        logger.debug("Inicio de método ValidadorNoHabilitadoSubsidio.habilitadoParaSubsidio");
        /* se verifica si está inhabilitado con el tipo y número de documento */
        List<Object> inhabilidades = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_INHABILITADA_SUBSIDIO_FOVIS)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacion).name())
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                .setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO).getResultList();
        logger.debug("Fin de método ValidadorNoHabilitadoSubsidio.inhabilitadoParaSubsidio");
        if (inhabilidades != null && !inhabilidades.isEmpty()) {
            return true;
        }
        return false;
    }

}
