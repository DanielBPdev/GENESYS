package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 50 <br>
 * Valida:
 * Validar en BD que el campo "¿Retención de subsidios activa?" para el empleador, tenga el valor "Si"
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorRetencionSubsidioEmpleadorActiva extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio ValidadorRetencionSubsidioEmpleadorActiva.execute");
        try {

            if (datosValidacion == null || datosValidacion.isEmpty() || datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM) == null
                    || datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM) == null) {
                logger.debug("VALIDACION NO EVALUADA - No hay parametros");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                        ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_EMPLEADOR_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }

            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            // Se consulta el empleador con el tipo y nro documento
            Empleador empleador = (Empleador) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_EMPLEADOR)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();

            if (empleador != null && empleador.getRetencionSubsidioActiva() != null && empleador.getRetencionSubsidioActiva()) {
                logger.debug("VALIDACION EXITOSA - Fin ValidadorRetencionSubsidioEmpleadorActiva.execute");
                // Validación exitosa - Validador 50
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_EMPLEADOR_ACTIVO);
            }

            logger.debug("VALIDACION FALLIDA- Fin Fin ValidadorRetencionSubsidioEmpleadorActiva.execute");
            // Validación no aprobada, Validador 50
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NOVEDADES_EMPLEADOR_VALIDADOR_50),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_EMPLEADOR_ACTIVO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                    ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_EMPLEADOR_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
