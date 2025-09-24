package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

/**
 * Clase que contiene la lógica para validar cuando la persona es mayor al jefe de hogar
 * 
 * @author Jose Arley Correa Salama <jocorrea@heinsohn.com.co>
 */
public class ValidadorPersonaEdadMayorJefeHogar extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        try {
            logger.debug("Inicio ValidadorPersonaEdadMayorJefeHogar");
            if (datosValidacion == null || datosValidacion.isEmpty()) {
                /* mensaje no evaluado porque no llegaron datos */
                return crearMensajeNoEvaluado();
            }
            String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
            String fechaNacimiento = datosValidacion.get(ConstantesValidaciones.FECHA_NACIMIENTO_PARAM);

            PersonaDetalle personaDetalle = null;
            /* Se verifica si la fecha de nacimiento es null */
            if (fechaNacimiento == null || fechaNacimiento.equals("")) {
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_JEFE);
            }
            if (tipoIdentificacion == null || numeroIdentificacion == null || numeroIdentificacion.isEmpty()) {
                /* mensaje no evaluado porque no hay parametros */
                return crearMensajeNoEvaluado();
            }
            Persona personaEncontrado = null;
            List<Persona> personaTipoIdentificacion = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_TIPO_NUMERO, Persona.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
            try {
                personaDetalle = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION, PersonaDetalle.class)
                        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
            } catch (NoResultException e) {
                personaDetalle = null;
            }
            if (!personaTipoIdentificacion.isEmpty()) {
                personaEncontrado = personaTipoIdentificacion.get(0);
            }
            else {
                List<Persona> personaIdentificacion = entityManager
                        .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_POR_NUMERO, Persona.class)
                        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();
                try {
                    personaDetalle = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONADETALLE_ID_NUMERO_IDENTIFICACION, PersonaDetalle.class)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();
                } catch (NoResultException e) {
                    personaDetalle = null;
                }
                if (!personaIdentificacion.isEmpty()) {
                    personaEncontrado = personaTipoIdentificacion.get(0);
                }
            }
            if (personaEncontrado != null && personaDetalle != null) {
                Date fNacimiento = new Date(new Long(fechaNacimiento));
                /*
                 * Verificacion de la fecha nacimiento del afiliado
                 * es mayor a la del afiliado principal
                 */
                if (fNacimiento.getTime() < personaDetalle.getFechaNacimiento().getTime()) {
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_MAYOR_JEFE),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_JEFE,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
            else {
                /* Afiliado principal no encontrado */
                return crearMensajeNoEvaluado();
            }

            /* exitoso */
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_JEFE);
        } catch (Exception e) {
            /* No evaluado ocurrió alguna excepción */
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * 
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_MAYOR_JEFE),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_MAYOR_JEFE,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
