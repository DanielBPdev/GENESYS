package com.asopagos.validaciones.fovis.validadores.fovis.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

public class ValidadorModalidadAdquisicionViviendaUsada extends ValidadorFovisAbstract {

    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorModalidadAdquisicionViviendaUsada.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {

                //obtiene los parametros de la postulacion
                String modalidad = datosValidacion.get(ConstantesValidaciones.MODALIDAD_POSTULACION);
                String condicionHogar = datosValidacion.get(ConstantesValidaciones.CONDICION_HOGAR);
                String condicionEspecialJefeHogarConcat = datosValidacion.get(ConstantesValidaciones.CONDICION_ESPECIAL_JEFE_HOGAR);
                String condicionEspecialMiembroHogarConcat = datosValidacion.get(ConstantesValidaciones.CONDICION_ESPECIAL_MIEMBRO_HOGAR);
                String clasificacion = datosValidacion.get(ConstantesValidaciones.CLASIFICACION_PARAM);

                /*
                 * Evalua si los parametros no estan nulos o vacios
                 */
                if ((modalidad != null && !modalidad.equals("")) &&
                    ((condicionHogar != null && !condicionHogar.equals("")) ||
                        (condicionEspecialJefeHogarConcat != null && !condicionEspecialJefeHogarConcat.equals("")) ||
                        (condicionEspecialMiembroHogarConcat != null && !condicionEspecialMiembroHogarConcat.equals("")))) {

                    //Se separan los valores separados por pipe line
                    List < String > condicionEspecialJefeHogar = Arrays.asList(condicionEspecialJefeHogarConcat.split("\\|"));
                    List < String > condicionEspecialMiembroHogar = Arrays.asList(condicionEspecialMiembroHogarConcat.split("\\|"));

                    //Si la modalidad de vivienda es adquisicion de vivienda usada rural o urbana
                    //se valida si la Condición del hogar/Tipo postulante es Desplazado inscrito en acción social o Víctima de atentado terrorista
                    //se valida si la Condición especial- Jefe de hogar es Madre comunitaria ICBF o Damnificado desastre natural
                    //se valida si la Condición especial- Miembro del hogar es Madre comunitaria ICBF o Damnificado desastre natural

                    
                    if ((modalidad.equals(ModalidadEnum.ADQUISICION_VIVIENDA_USADA_RURAL.name()) || modalidad.equals(ModalidadEnum.ADQUISICION_VIVIENDA_USADA_URBANA.name())) &&
                            !((condicionHogar.equals(CondicionHogarEnum.DESPLAZADO_INSCRITO_ACCION_SOCIAL.name()) || condicionHogar.equals(CondicionHogarEnum.VICTIMA_ATENTADO_TERRORISTA.name()) || condicionHogar.equals(CondicionHogarEnum.RECUPERADORES_RECICLABLES.name()) || condicionHogar.equals(CondicionHogarEnum.CONCEJALES.name()) || condicionHogar.equals(CondicionHogarEnum.HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO.name()) ) ||
                                (condicionEspecialJefeHogar.contains(NombreCondicionEspecialEnum.DAMNIFICADO_DESASTRE_NATURAL.name()) || condicionEspecialJefeHogar.contains(NombreCondicionEspecialEnum.MADRE_COMUNITARIA_ICBF.name())) ||
                                (condicionEspecialMiembroHogar.contains(NombreCondicionEspecialEnum.DAMNIFICADO_DESASTRE_NATURAL.name()) || condicionEspecialMiembroHogar.contains(NombreCondicionEspecialEnum.MADRE_COMUNITARIA_ICBF.name())))) {

                        return crearValidacion(
                            myResources.getString(ConstantesValidaciones.KEY_MODALIDAD_ADQUISICION_VIVIENDA_USADA_INVALIDA)
                            .replace(ConstantesValidaciones.MENSAJE_PARAM_0, modalidad),
                            ResultadoValidacionEnum.NO_APROBADA,
                            ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                    }
                } else {
                    logger.debug("No evaluado - No llegaron todos los parámetros");
                    return crearMensajeNoEvaluado();
                }
            } else {
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA);

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
                        + myResources.getString(ConstantesValidaciones.KEY_MODALIDAD_ADQUISICION_VIVIENDA_USADA_INVALIDA),
                ResultadoValidacionEnum.NO_EVALUADA,
                ValidacionCoreEnum.VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
