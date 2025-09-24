/**
 * 
 */
package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 74 - Verifica si se ha registrado y aprobado al hogar la novedad No 27 "Ajuste y actualización valor SFV" con anterioridad
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ValidadorRegistroNovedadActualizaSFV extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            if (verificarNovedadSFV(getIdPostulacion(datosValidacion))) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_HOGAR_REGISTRA_NOVEDAD_AJUSTE_SFV),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_REGISTRO_NOVEDAD_ACTUALIZA_SFV,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_REGISTRO_NOVEDAD_ACTUALIZA_SFV);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_REGISTRO_NOVEDAD_ACTUALIZA_SFV, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Consulta si existe asociada la novedad 27 a la postulción indicada
     * @param idPostulacion
     *        Identificador de postulación validada
     * @return TRUE si existe registro de la novedad FALSE en caso contrario
     */
    @SuppressWarnings("unchecked")
    private Boolean verificarNovedadSFV(Long idPostulacion) {
        // Transacción esperada
        List<String> listTipoTransaccion = new ArrayList<>();
        listTipoTransaccion.add(TipoTransaccionEnum.AJUSTE_ACTUALIZACION_VALOR_SFV.name());
        // Consulta si existe novedad aprobada asociada a la postulación
        List<Date> listaRegistro = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDAD_FOVIS_BY_TIPO_TRANSACCION_POSTULACION)
                .setParameter(ConstantesValidaciones.TIPO_NOVEDAD_PARAM, listTipoTransaccion)
                .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getResultList();
        return (listaRegistro != null && !listaRegistro.isEmpty());
    }

}
