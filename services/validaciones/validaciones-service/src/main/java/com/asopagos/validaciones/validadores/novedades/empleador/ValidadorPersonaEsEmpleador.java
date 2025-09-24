package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 36 Validar que no hay una persona o empleador registrado (a) con el mismo tipo y número de documento de identificación
 * 
 * Valida:
 * Validar que no hay una persona o empleador registrado (a) con el mismo tipo y número de documento de identificación
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorPersonaEsEmpleador extends ValidadorAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio ValidadorPersonaEsEmpleador.execute");
        try {
            if (datosValidacion == null || datosValidacion.isEmpty() || datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) == null) {
                logger.debug("VALIDACION NO EJECUTADA - No hay parametros");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                        ValidacionCoreEnum.VALIDACION_PERSONA_ES_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            // Se consulta persona con el tipo y nro documento
            List<Persona> listPersona = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_EXISTENCIA_PERSONA_POR_TIPO_NUMERO, Persona.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
            // Se valida la condición
            if (listPersona == null || listPersona.isEmpty()) {
                logger.debug("VALIDACION EXITOSA - Fin ValidadorPersonaEsEmpleador.execute");
                // Validación exitosa, Validador 36
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_ES_EMPLEADOR);
            }
            else {
                logger.debug("VALIDACION FALLIDA- Fin ValidadorPersonaEsEmpleador.execute");
                // Validación no aprobada, Validador 36
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HAY_PERSONA_REGISTRADA),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_ES_EMPLEADOR,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV, ValidacionCoreEnum.VALIDACION_PERSONA_ES_EMPLEADOR,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
