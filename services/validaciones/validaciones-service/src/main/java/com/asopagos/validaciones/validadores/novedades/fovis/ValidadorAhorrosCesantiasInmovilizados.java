package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si una postulación presenta ahorros y cesantias inmovilizados
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorAhorrosCesantiasInmovilizados extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            Long idPostulacion = getIdPostulacion(datosValidacion);

            if (!persentaAhorros(idPostulacion) && !persentaCesantiasInmovilizadas(idPostulacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NO_AHORROS_CESANTIAS_INMOVILIZADAS),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_AHORROS_CESANTIAS_INMOVILIZADOS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AHORROS_CESANTIAS_INMOVILIZADOS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_AHORROS_CESANTIAS_INMOVILIZADOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si la postulación presenta ahorros de tipo: <br>
     * AHORRO_PROGRAMADO <br>
     * AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA
     * @param idPostulacion
     *        Identificador de la postulación evaluada
     * @return <code>true</code> Si existen ahorros
     */
    @SuppressWarnings("unchecked")
    private boolean persentaAhorros(Long idPostulacion) {
        List<String> tipoAhorro = new ArrayList<>();
        tipoAhorro.add(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO.name());
        tipoAhorro.add(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA.name());

        List<Integer> ahorrosAsociados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AHORRO_PRESENTA_VALOR)
                .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion)
                .setParameter(ConstantesValidaciones.TIPO_AHORRO, tipoAhorro).getResultList();

        return !ahorrosAsociados.isEmpty();
    }

    /**
     * Valida si la postulación presenta cesantias inmovilizadas
     * @param idPostulacion
     *        Identificador de la postulación evaluada
     * @return <code>true</code> si existe cesantias inmovilizadas
     */
    private boolean persentaCesantiasInmovilizadas(Long idPostulacion) {
        try {
            List<String> tipoAhorro = new ArrayList<>();
            tipoAhorro.add(TipoAhorroPrevioEnum.CESANTIAS_INMOVILIZADAS.name());

            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_AHORRO_PRESENTA_VALOR)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion)
                    .setParameter(ConstantesValidaciones.TIPO_AHORRO, tipoAhorro).getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
