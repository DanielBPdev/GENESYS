package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * En el momento de la activación del afiliado principal se
 * debe verificar que el trabajador es sea socio del empleador
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoConyugeSocioEmpleador extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorAfiliadoConyugeSocioEmpleador.execute");
        try {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            TipoIdentificacionEnum tipoIdentificacionEmpleador = TipoIdentificacionEnum
                    .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM));
            String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);

            //se busca(n) el(los) socio(s) del empleador 
            //dado cuyo conyuge sea el afiliado principal (trabajador)
            List<SocioEmpleador> socios = (List<SocioEmpleador>) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_CONYUGE_SOCIO_EMPLEADOR)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
                    .setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador)
                    .setParameter("existenCapitulaciones", false).getResultList();

            //se realiza la validación
            if (socios.isEmpty()) {
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorAfiliadoConyugeSocioEmpleador.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_CONYUGE_SOCIO_EMPLEADOR);
            }
            else {
                //validación fallida
                logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoConyugeSocioEmpleador.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_CONYUGE_SOCIO_EMPLEADOR),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_AFILIADO_CONYUGE_SOCIO_EMPLEADOR,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_AFILIADO_CONYUGE_SOCIO_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}