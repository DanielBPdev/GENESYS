package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si la modalidad de la postulacion es ADQUISICION VIVIENDA NUEVA URBANA
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorModalidadAdquisicionViviendaNuevaUrbana extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            /*if (!modalidadValida(getIdPostulacion(datosValidacion))) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MODALIDAD_INVALIDA_FOVIS_64),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_NUEVA_URBANA_64_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }*/
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_NUEVA_URBANA_64_FOVIS);

        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_NUEVA_URBANA_64_FOVIS,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si la postulacion tiene la modalidad ADQUISICION VIVIENDA NUEVA URBANA
     * @param idPostulacion
     * @return
     */
    /*private boolean modalidadValida(Long idPostulacion) {
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_MODALIDAD_POSTULACION)
                    .setParameter(ConstantesValidaciones.MODALIDAD_POSTULACION, ModalidadEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA.name())
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }*/
}
