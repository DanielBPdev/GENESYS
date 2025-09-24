package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validador que verifica si existe al menos un ciclo de asignacion vigente en el sistema
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCicloAsignacionVigente extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (!hayCiclosDeAsignacionVigentes()) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_EXISTEN_CICLOS_ASIGNACION_VIGENTES),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE_52_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE_52_FOVIS);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_CICLO_ASIGNACION_VIGENTE_52_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si hay al menos un ciclo de asignacion vigente en el sistema
     * @param idPostulacion
     * @return <code>true</code> Si existen ciclos vigentes
     */
    @SuppressWarnings("unchecked")
    private boolean hayCiclosDeAsignacionVigentes() {
        List<Integer> ciclosActivos = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EXISTE_CICLO_ASIGNACION_VIGENTE)
                .setParameter(ConstantesValidaciones.ESTADO_CICLO_ASIGNACION, EstadoCicloAsignacionEnum.VIGENTE.name()).getResultList();

        return !ciclosActivos.isEmpty();
    }
}
