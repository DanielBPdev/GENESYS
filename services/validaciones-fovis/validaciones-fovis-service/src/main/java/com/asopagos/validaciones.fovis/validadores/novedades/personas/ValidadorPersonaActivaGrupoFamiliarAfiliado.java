package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CLASE QUE VALIDA:
 * Persona ya está activa en algún grupo familiar del mismo afiliado principal
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorPersonaActivaGrupoFamiliarAfiliado extends ValidadorFovisAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPersonaActivaGrupoFamiliarAfiliado.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
            String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

            List<ClasificacionEnum> tipoBeneficiario = new ArrayList<ClasificacionEnum>();
            tipoBeneficiario.add(ClasificacionEnum.CONYUGE);
            tipoBeneficiario.add(ClasificacionEnum.PADRE);
            tipoBeneficiario.add(ClasificacionEnum.MADRE);
            tipoBeneficiario.add(ClasificacionEnum.HIJO_BIOLOGICO);
            tipoBeneficiario.add(ClasificacionEnum.HIJO_ADOPTIVO);
            tipoBeneficiario.add(ClasificacionEnum.HIJASTRO);
            tipoBeneficiario.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
            tipoBeneficiario.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);

            // Se consulta(n) el(los) afiliado(s) principal(es) del beneficario
            List<Afiliado> afiliados = (List<Afiliado>) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_PPAL_GRUPO_FAMILIAR_BENEFICIARIO_ACTIVO, Afiliado.class)
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionFovisPersonaUtils.obtenerListaEstadoActivo())
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, tipoBeneficiario)
                    .setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
                    .setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario).getResultList();

            // Se busca el afiliado principal que esta realizando el proceso
            Afiliado afiliado = (Afiliado) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion).getSingleResult();

            // Si el beneficiario no esta asociado con algun afiliado 
            // Validación exitosa
            if ((afiliados == null || afiliados.isEmpty()) && afiliado != null) {
                logger.debug("HABILITADA- Fin de método ValidadorPersonaActivaGrupoFamiliarAfiliado.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO);
            }
            // Si afiliado no existe 
            // Validación fallida
            if (afiliado == null) {
                logger.debug("NO HABILITADA- Fin de método ValidadorPersonaActivaGrupoFamiliarAfiliado.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PER_ACTIVA_GRUPO_AFILIADO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            // Se verifica si las consultas arrojaron resultados
            if (afiliados != null && !afiliados.isEmpty() && afiliado != null) {
                // Se verifica si el afiliado que esta ejecutando la validación se encuentra en la lista
                if (existeAfiliadoAsociadoBeneficiario(afiliados, afiliado)) {
                    //ValidacionFallida
                    logger.debug("NO HABILITADA- Fin de método ValidadorPersonaActivaGrupoFamiliarAfiliado.execute");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PER_ACTIVA_GRUPO_AFILIADO),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
                }
            }
            // Si no se encuentran coincidencias la validación es exitosa
            logger.debug("HABILITADA- Fin de método ValidadorPersonaActivaGrupoFamiliarAfiliado.execute");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO);
        } catch (NoResultException nre) {
            logger.error("No evaluado - No se ha encontrado el afiliado principal.", nre);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_PER_ACTIVA_GRUPO_AFILIADO,
                    ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_PER_ACTIVA_GRUPO_AFILIADO,
                    ValidacionCoreEnum.VALIDACION_PER_ACTIVA_GRUPO_AFILIADO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
    
    /**
     * Se verifica si el afiliado principal del beneficiario es el mismo afiliado que ejecuta la validacion
     * @param listAfiliado
     *        Lista de afiliados en donde esta asociado el beneficiario
     * @param afiliado
     *        Afiliado que realiza la validacion
     * @return TRUE Si el afiliado es el mismo (Beneficiario asociado a quien ejecuta la validacion), FALSE en caso contrario
     */
    private Boolean existeAfiliadoAsociadoBeneficiario(List<Afiliado> listAfiliado, Afiliado afiliado) {
        boolean result = false;
        // Se itera la lista de afiliados en donde esta asociado el beneficiario
        for (Afiliado afil : listAfiliado) {
            // Se verifica si el afiliado que esta ejecutando la novedad se encuentra en la lista
            if (afil.getIdAfiliado().equals(afiliado.getIdAfiliado())) {
                //ValidacionFallida
                result = true;
            }
        }
        return result;
    }
}
