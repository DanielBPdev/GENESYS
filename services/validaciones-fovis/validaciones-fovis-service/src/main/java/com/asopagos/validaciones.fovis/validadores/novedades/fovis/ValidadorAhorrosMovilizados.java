package com.asopagos.validaciones.fovis.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.TipoAhorroPrevioEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Validador que verifica si los ahorros asociados a una postulacion se encuentran movilizados
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorAhorrosMovilizados extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (ahorrosMovilizados(getIdPostulacion(datosValidacion))) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AHORROS_MOVILIZADOS),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_AHORROS_MOVILIZADOS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AHORROS_MOVILIZADOS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_AHORROS_MOVILIZADOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si los ahorros asociados a una postulacion se encuentran movilizados
     * @param idPostulacion
     *        Identificador postulación evaluada
     * @return <code>true</code> si existen ahorros movilizados
     */
    @SuppressWarnings("unchecked")
    private boolean ahorrosMovilizados(Long idPostulacion) {
        List<String> tipoAhorro = new ArrayList<>();
        tipoAhorro.add(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO.name());
        tipoAhorro.add(TipoAhorroPrevioEnum.AHORRO_PROGRAMADO_CONTRACTUAL_EVALUACION_CREDITICIA_FAVORABLE_FNA.name());

        List<Integer> ahorrosAsociados = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MOVILIZACION_AHORROS)
                .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion)
                .setParameter(ConstantesValidaciones.TIPO_AHORRO, tipoAhorro)
                .setParameter(ConstantesValidaciones.AHORROS_MOVILIZADOS, NumerosEnterosConstants.UNO).getResultList();

        return !ahorrosAsociados.isEmpty();
    }
}
