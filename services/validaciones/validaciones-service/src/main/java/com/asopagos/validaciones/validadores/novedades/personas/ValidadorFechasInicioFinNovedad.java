package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.NovedadDetalle;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Valida si se enviaron las fechas de la novedad tanto la fecha inicio como la fecha fin
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
public class ValidadorFechasInicioFinNovedad extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorFechasInicioFinNovedad.execute");
        try {
            String fechaInicio = datosValidacion.get("fechaInicio");
            String fechaFin = datosValidacion.get("fechaFin");
            
            if (fechaInicio == null || fechaFin == null) {
                logger.debug("No Aprobada - Fin de método ValidadorFechasInicioFinNovedad.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_NOVEDAD_SIN_FECHAS),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_FECHAS_INICIO_FIN_NOVEDAD,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción ValidadorSolicitanteTieneNovedadVigente.execute", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_NOVEDAD_SIN_FECHAS,
                    ValidacionCoreEnum.VALIDACION_FECHAS_INICIO_FIN_NOVEDAD, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
        // Retorna validación exitosa
        return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_FECHAS_INICIO_FIN_NOVEDAD);
    }
}

