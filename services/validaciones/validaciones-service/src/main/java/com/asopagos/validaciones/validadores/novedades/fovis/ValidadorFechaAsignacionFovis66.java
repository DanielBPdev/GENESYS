package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.Date;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica la fecha de asignacion de la solicitud es anterior a 7
 * de marzo de 2016
 * 
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy
 *         Monroy</a>
 */
public class ValidadorFechaAsignacionFovis66 extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            Long idPostulacion = getIdPostulacion(datosValidacion);

            if (!fechaValida(idPostulacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_FECHA_ASIGNACION_PREVIA_7_MARZO_2016),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_FECHA_ASIGNACION_FOVIS_66,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHA_ASIGNACION_FOVIS_66);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_FECHA_ASIGNACION_FOVIS_66, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si la fecha de aceptacion de la solicitud de asignación es previa
     * al 7 de marzo de 2016
     * 
     * @param idPostulacion
     * @return <code>true</code> Si la fecha de asignación es mayor a la fecha parametrizada
     */
    private boolean fechaValida(Long idPostulacion) {
        try {
            // Se consulta la fecha tope parametrizada
            CacheManager.sincronizarParametrosYConstantes();
            String fechaParametrizadaString = (String) CacheManager
                    .getParametro(ParametrosSistemaConstants.FECHA_ASIGNACION_SOLICITUD_TOPE);

            // Se consulta la fecha de asigancion de la solicitud de postulacion
            Date fechaAsignacion = (Date) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHA_ASIGNACION_SOLICITUD_POSTULACION)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();

            Date fechaParametrizada = new Date(Long.parseLong(fechaParametrizadaString));

            return !fechaAsignacion.before(fechaParametrizada);
        } catch (NoResultException nre) {
            return false;
        }
    }
}
