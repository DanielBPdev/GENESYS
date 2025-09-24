package com.asopagos.validaciones.validadores.fovis.common;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

public class ValidadorModalidadMejoraDeViviendaJefeHogar extends ValidadorAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorModalidadMejoraDeViviendaJefeHogar.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                String tipoIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                String numeroIdentificacionJefeHogar = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                Boolean esBeneficiario = Boolean.valueOf(datosValidacion.get(ConstantesValidaciones.HA_SIDO_BENEIFICARIO_SUBSIDIO_VIVIENDA));
                String objetoValidacion = datosValidacion.get(ConstantesValidaciones.OBJETO_VALIDACION_PARAM);
                String modalidad = datosValidacion.get(ConstantesValidaciones.MODALIDAD_POSTULACION);
                String primerNombre = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM);
                String segundoNombre = datosValidacion.get(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM);
                String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
                String segundoApellido = datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM);

                /*
                 * Evalua si los datos de tipoIdentificacion y
                 * numeroIdentificacion no estan nulos o vacios
                 */
                if ((numeroIdentificacionJefeHogar != null && !numeroIdentificacionJefeHogar.equals(""))
                        && (tipoIdentificacionJefeHogar != null && !tipoIdentificacionJefeHogar.equals(""))
                        && (esBeneficiario != null)) {
                    
                    
                    if (esBeneficiario && !((modalidad.equals(ModalidadEnum.CONSTRUCCION_SITIO_PROPIO_URBANO.name())) 
                            || (modalidad.equals(ModalidadEnum.MEJORAMIENTO_VIVIENDA_URBANA.name())))) {
                        
                        List<Object[]> datosPersona = this.nombrePersona(tipoIdentificacionJefeHogar, numeroIdentificacionJefeHogar);
                        String nombreCompleto;
                
                        if (!datosPersona.isEmpty()) {
                            Object[] datosPersonaExiste = datosPersona.get(0);
                            nombreCompleto = datosPersonaExiste[0].toString();
                        } else
                            nombreCompleto = primerNombre+' '+segundoNombre+' '+primerApellido+' '+segundoApellido;
                        
                        if (objetoValidacion != null) {
                            if (objetoValidacion.equals("")) {
                                objetoValidacion = "JEFE_HOGAR";
                            }
                        }
                        else {
                            objetoValidacion = "JEFE_HOGAR";
                        }
                        
                        return crearValidacion(
                                myResources.getString(ConstantesValidaciones.KEY_MODALIDAD_MEJORA_VIVIENDA_POSTULACION_INVALIDA)
                                .replace(ConstantesValidaciones.MENSAJE_PARAM_0, objetoValidacion)
                                .replace(ConstantesValidaciones.MENSAJE_PARAM_1, nombreCompleto),
                                ResultadoValidacionEnum.NO_APROBADA,
                                ValidacionCoreEnum.VALIDACION_MODALIDAD_MEJORA_VIVIENDA_SALUDABLE_JEFE_HOGAR,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                }  else {
                    logger.debug("No evaluado - No llegaron todos los parámetros");
                    return crearMensajeNoEvaluado();
                }
            } else {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MODALIDAD_MEJORA_VIVIENDA_SALUDABLE_JEFE_HOGAR);

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
                        + myResources.getString(ConstantesValidaciones.KEY_MODALIDAD_MEJORA_VIVIENDA_POSTULACION_INVALIDA),
                ResultadoValidacionEnum.NO_EVALUADA,
                ValidacionCoreEnum.VALIDACION_MODALIDAD_MEJORA_VIVIENDA_SALUDABLE_JEFE_HOGAR,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }
    
    /**
     * Metodo que retorna el nombre completo asociado a un jefe de hogar
     * @param tipoIdentificacionJefeHogar Tipo de identificación del jefe de hogar
     * @param numeroIdentificacionJefeHogar Número de identificación del jefe de hogar
     * @return Lista de objetos encontrados
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> nombrePersona(String tipoIdentificacionJefeHogar,String numeroIdentificacionJefeHogar) {
        
        List<Object[]> datosPersona = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_NOMBRES_PERSONA_POR_IDENTIFICACION)
                .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionJefeHogar)
                .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionJefeHogar)
                .getResultList();
        return datosPersona;
    }

}
