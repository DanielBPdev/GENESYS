package com.asopagos.validaciones.validadores.novedades.fovis;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validadador que verifica si una persona se encuentra inhabilitada para subsidio FOVIS
 * @author <a href="mailto:mamonroy@heinsohn.com.co">Mario Andres Monroy Monroy</a>
 */
public class ValidadorPersonaInhabilitadaSubsidioFovis extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {

        logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.execute");
        try {
            
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);
            String identPostulacion = datosValidacion.get(ConstantesValidaciones.ID_POSTULACION);
            
            if (tipoIdentificacion == null || tipoIdentificacion.isEmpty() || numeroIdentificacion == null
                    || numeroIdentificacion.isEmpty() || identPostulacion == null || identPostulacion.isEmpty()) {
                logger.debug("No evaluado - No llegaron todos los parámetros");
                return crearMensajeNoEvaluado();
            }
            Long idPostulacion = Long.valueOf(identPostulacion);
            
            Boolean hogarSancionado = postulacionSancionada(idPostulacion);
            
            if (ClasificacionEnum.JEFE_HOGAR.name().equals(objetoValidacion)) {
                if (jefeHogarInhabilitadoSubsidio(tipoIdentificacion, numeroIdentificacion) || hogarSancionado) {
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INHABILITADA_SUBSIDIO_FOVIS),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
            else if (ClasificacionEnum.HOGAR.name().equals(objetoValidacion)){
                if(hogarInhabilitadoSubsidio(idPostulacion) || hogarSancionado){
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HOGAR_INHABILITADO_SUBSIDIO_FOVIS),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
            else if (beneficiarioInhabilitadoSubsidio(tipoIdentificacion, numeroIdentificacion) || hogarSancionado) {
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_INHABILITADA_SUBSIDIO_FOVIS),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
            
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razón no se puede evaluar.
     * 
     * @return validacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_INHABILITADA_SUBSIDIO_FOVIS,
                TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
    }

    /**
     * Valida si el jefe de hogar se encuentra habilitado para subsidio de vivienda FOVIS
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private boolean jefeHogarInhabilitadoSubsidio(String tipoIdentificacion, String numeroIdentificacion) {

        logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.jefeHogarInhabilitadoSubsidio");
        try {

            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_JEFE_HOGAR_INHABILITADO_SUBSIDIO_FOVIS)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO).getSingleResult();

            logger.debug("Fin de método ValidadorPersonaHabilitadaSubsidioFovis.jefeHogarInhabilitadoSubsidio");
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }

    /**
     * Valida si el integrante del hogar se encuentra habilitado para subsidio de vivienda FOVIS
     * 
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private boolean beneficiarioInhabilitadoSubsidio(String tipoIdentificacion, String numeroIdentificacion) {

        logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
        try {

            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIARIO_INHABILITADO_SUBSIDIO_FOVIS)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO).getSingleResult();

            logger.debug("Fin de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }

    /**
     * Valida si el hogar se encuentra inhabilitado para subsidio de vivienda FOVIS
     * verificando cada miembro del hogar
     * 
     * @param idPostulacion Identificador de la postulacion
     * @return TRUE si todos los miembros del hogar estan inhabilitados, FALSE en caso contrario
     */
    private boolean hogarInhabilitadoSubsidio(Long idPostulacion) {

        logger.debug("Inicio de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
        try {
            // Se consulta la cantidad de integrantes de la postulación 
            Integer cantidadIntegrantes = (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_CANTIDAD_MIEMBROS_HOGAR)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();

            // Se consulta las personas  con inhabilidad
            List<Integer> inhabilidadesAsocidadas = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGAR_INHABILITADO_SUBSIDIO_FOVIS)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion)
                    .setParameter(ConstantesValidaciones.INHABILITADO_SUBSIDIO_PARAM, NumerosEnterosConstants.UNO).getResultList();

            // Se verifica si la cantidad de integrantes con inhabilidad es la misma a la cantidad de integrantes del hogar 
            if (inhabilidadesAsocidadas == null || inhabilidadesAsocidadas.isEmpty()) {
                return false;
            }
            if (inhabilidadesAsocidadas.size() != cantidadIntegrantes) {
                return false;
            }

            logger.debug("Fin de método ValidadorPersonaHabilitadaSubsidioFovis.beneficiarioInhabilitadoSubsidio");
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }

    /**
     * Verifica si la postulación enviada tiene registrada sancion
     * @param idPostulacion
     *        Identificador Postulación
     * @return TRUE si se encuentra sancionado, FALSE en caso contrario
     */
    private boolean postulacionSancionada(Long idPostulacion) {
        try {
            Object pof = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTA_POSTULACION_SANCIONADA_POR_ID)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion)
                    .setParameter("restituido", NumerosEnterosConstants.UNO).getSingleResult();
            if (pof != null) {
                return true;
            }
            return false;
        } catch (NoResultException | NonUniqueResultException e) {
            return false;
        }
    }
}
