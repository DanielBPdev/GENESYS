package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona tiene registrada con condicion de invalidez.
 * 
 * Validador 16 - Novedades personas
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorPersonaCondicionInvalidez extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.info("Inicio de método ValidadorPersonaCondicionInvalidez.execute");
        try {

            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("NO EVALUADO - No hay datos de validacion");
                return crearMensajeNoEvaluado();
            }

            TipoIdentificacionEnum tipoIdentificacion;
            String numeroIdentificacion;

            if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM) != null
                    && datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM) != null) {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);
            }
            else {
                tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            }

            // Se consulta si existe condicion de invalidez
            CondicionInvalidez conInvalidez = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_CONDICIONINVALIDEZ_ID_NUMERO_TIPO_IDENTIFICACION,
                            CondicionInvalidez.class)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getSingleResult();

            if (conInvalidez != null && conInvalidez.getCondicionInvalidez() != null && conInvalidez.getCondicionInvalidez()) {
                logger.info("NO APROBADA - Existe persona con invalidez");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INVALIDEZ),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_CONDICION_INVALIDEZ,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }

            logger.debug("APROBADO");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_CONDICION_INVALIDEZ);

        } catch (NoResultException e) {
            // Si no existe condicion de invalidez se considera la validacion exitosa
            logger.debug("APROBADO");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_CONDICION_INVALIDEZ);
        } catch (Exception e) {
            logger.error("NO EVALUADO - Ocurrió una excepción no esperada", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_INVALIDEZ),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_CONDICION_INVALIDEZ,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
