package com.asopagos.validaciones.fovis.validadores.novedades.personas;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

import java.util.List;
import java.util.Map;

/**
 * CLASE QUE VALIDA:
 * que la persona ya está activa como beneficiario tipo hijo
 * en dos grupos familiares de distintos afiliados principales
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorHijoActivo2GruposDeDistintosAfiliados extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorHijoActivo2GruposDeDistintosAfiliados.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
            String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

            //se consulta(n) el(los) afiliado(s) principal(es) del beneficario
            List<Persona> personas = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPO_FAMILIAR_BENEFICIARIO_ACTIVO, Persona.class)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionFovisPersonaUtils.obtenerClasificacionHijo())
                    .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionFovisPersonaUtils.obtenerListaEstadoActivo())
                    .setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
                    .setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario).getResultList();

            //si se obtuvieron dos o más afiliados se verifica que no sean los mismos
            if (personas != null && !personas.isEmpty() && personas.size() >= 2) {
                for (int i = 0; i < personas.size(); i++) {
                    Persona p = personas.get(i);
                    for (int j = 0; j < personas.size(); j++) {
                        //comparamos cada persona con todas las demas. de encontrar una coincidencia
                        //falla la validación
                        if (i != j && !p.getIdPersona().equals(personas.get(j).getIdPersona())) {
                            //validación fallida
                            logger.debug("NO HABILITADA- Fin de método ValidadorHijoActivo2GruposDeDistintosAfiliados.execute");
                            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS),
                                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS,
                                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                        }
                    }
                }
                //si no se encuentran coincidencias la validación es exitosa
                logger.debug("HABILITADA- Fin de método ValidadorHijoActivo2GruposDeDistintosAfiliados.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS);
            }
            //si la validación arrojó solo un afiliado principal
            else if (personas != null && !personas.isEmpty()) {
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorHijoActivo2GruposDeDistintosAfiliados.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS);
            }
            // En caso de que la consulta no haya arrojado resultados
            // Es exitosa la validacion, puesto que no existe asociado el beneficiario
            // a ningun afiliado
            else {
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorHijoActivo2GruposDeDistintosAfiliados.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
