package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador que verifica si el tipo y numero de identificacion referenciados ya estan registrados
 * en sistema
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorCambioTipoNumeroIdentificacion extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            List<String> camposValidacion = new ArrayList<>();
            camposValidacion.add(ConstantesValidaciones.TIPO_ID_PARAM);
            camposValidacion.add(ConstantesValidaciones.NUM_ID_PARAM);
            verificarDatosValidacion(datosValidacion, camposValidacion);
            
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            
            if (tipoNumeroIdentificacionExisten(tipoIdentificacion, numeroIdentificacion)) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TIPO_NUMERO_IDENTIFICACION_EXISTEN),
                        ResultadoValidacionEnum.NO_APROBADA,
                        ValidacionCoreEnum.VALIDACION_CAMBIO_TIPO_NUMERO_IDENTIFICACION_34_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_CAMBIO_TIPO_NUMERO_IDENTIFICACION_34_FOVIS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_CAMBIO_TIPO_NUMERO_IDENTIFICACION_34_FOVIS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }

    /**
     * Valida si el tipo y numero de identificacion evaluados ya se encuentran registrados en sistema
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return <code>true</code> si existe la información en BD
     */
    private boolean tipoNumeroIdentificacionExisten(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getSingleResult();

            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
