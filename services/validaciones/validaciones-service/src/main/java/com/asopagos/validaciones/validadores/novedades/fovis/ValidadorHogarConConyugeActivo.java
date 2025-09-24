package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si un hogar presenta un integrante tipo conyuge en estado activo
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorHogarConConyugeActivo extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (!hogarTieneConyugeActivo(getIdPostulacion(datosValidacion))) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HOGAR_NO_TIENE_CONYUGE_ACTIVO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_HOGAR_CON_CONYUGE_ACTIVO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HOGAR_CON_CONYUGE_ACTIVO);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_HOGAR_CON_CONYUGE_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si hay un conyuge activo en el hogar
     * @param idPostulacion
     * @return <code>true</code> si existe un conyuge asociado a la postulación
     */
    private boolean hogarTieneConyugeActivo(Long idPostulacion) {
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONYUGE_ACTIVO_HOGAR)
                    .setParameter(ConstantesValidaciones.TIPO_INTEGRANTE_HOGAR, ClasificacionEnum.CONYUGE_HOGAR)
                    .setParameter(ConstantesValidaciones.ESTADO_INTEGRANTE_HOGAR, EstadoFOVISHogarEnum.ACTIVO)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
