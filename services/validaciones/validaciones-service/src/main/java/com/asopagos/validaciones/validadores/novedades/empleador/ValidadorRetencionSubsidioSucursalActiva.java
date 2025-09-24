package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 52 <br>
 * Valida:
 * Validar en BD que el campo "¿Retención de subsidios activa?" para la sucursal, tenga el valor "Si"
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorRetencionSubsidioSucursalActiva extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio ValidadorRetencionSubsidioSucursalActiva.execute");
        try {

            if (datosValidacion == null || datosValidacion.isEmpty()
                    || datosValidacion.get(ConstantesValidaciones.ID_SUCURSAL_PARAM) == null) {
                logger.debug("VALIDACION NO EVALUADA - No hay parametros");
                return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                        ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_SUCURSAL_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }

            Long idSucursal = Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_SUCURSAL_PARAM));

            // Se consulta la sucursal empresa por identificador
            SucursalEmpresa sucursalEmpresa = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_SUCURSAL_EMPRESA_BY_ID, SucursalEmpresa.class)
                    .setParameter(ConstantesValidaciones.ID_SUCURSAL_PARAM, idSucursal).getSingleResult();

            if (sucursalEmpresa != null && sucursalEmpresa.getRetencionSubsidioActiva() != null && sucursalEmpresa.getRetencionSubsidioActiva()) {
                logger.debug("VALIDACION EXITOSA - Fin ValidadorRetencionSubsidioSucursalActiva.execute");
                // Validación exitosa - Validador 52
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_SUCURSAL_ACTIVO);
            }

            logger.debug("VALIDACION FALLIDA- Fin Fin ValidadorRetencionSubsidioSucursalActiva.execute");
            // Validación no aprobada, Validador 52
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NOVEDADES_EMPLEADOR_VALIDADOR_52),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_SUCURSAL_ACTIVO,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_MENSAJE_NO_EV,
                    ValidacionCoreEnum.VALIDACION_RETENCION_SUBSIDIO_SUCURSAL_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
