package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * Debe tener por lo menos un grupo familiar con mas de un beneficiario
 * que en el campo "Estado con respecto al afiliado principal"
 * tengan el valor "Activo"
 * 
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorGrupoFamiliarMasUnBenefActivo extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorGrupoFamiliarActivo.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

            // Lista de estados validos del beneficiario
            List<String> estadoList = new ArrayList<>();
            estadoList.add(EstadoAfiliadoEnum.ACTIVO.name());
            
            /*
             * se consulta el grupo familiar del afiliado con el tipo y número de documento, que tenga mas de un beneficiario ACTIVO
             */
            List<Object[]> infoGruposFamiliares = (List<Object[]>) entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_GRUPOS_FAMILIAR_CON_BENEFICIARIOS_ACTIVOS)
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, estadoList)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

            /*
             * se verifica que la consulta haya retornado al menos un registro con un grupo familiar con mas de un beneficiario activo
             */
            if (infoGruposFamiliares != null && !infoGruposFamiliares.isEmpty()) {
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorGrupoFamiliarActivo.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_MAS_UN_BENEF_ACTIVO);
            }
            else {
                //validación fallida
                logger.debug("NO HABILITADA- Fin de método ValidadorGrupoFamiliarActivo.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MSG_GRUPO_FAMILIAR_SIN_BENEF_ACTIVO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_MAS_UN_BENEF_ACTIVO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_GRUPO_FAMILIAR_MAS_UN_BENEF_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}