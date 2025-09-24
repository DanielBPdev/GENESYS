package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * CLASE QUE VALIDA:
 * En el momento de la activación del afiliado principal se debe 
 * verificar que el trabajador es/sea socio del empleador
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorAfiliadoSocioDelEmpleador extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorAfiliadoSocioDelEmpleador.execute");
        try {
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            String tipoIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
            String numeroIdentificacionEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);

            BigInteger personasConTipoYNumero = (BigInteger) entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_SOCIO_EMPLEADOR_POR_SOCIO_EMPLEADOR_TIPO_SOLICITANTE)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                    .setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdentificacionEmpleador)
                    .setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdentificacionEmpleador).getSingleResult();

            logger.debug("NO HABILITADA- Fin de método ValidadorAfiliadoSocioDelEmpleador.execute");
            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_AFILIADO_SOCIO_EMPLEADOR),
                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_AFILIADO_SOCIO_EMPLEADOR,
                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
        } catch (NoResultException nre) {
            logger.debug("HABILITADA- Fin de método ValidadorAfiliadoSocioDelEmpleador.execute");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_AFILIADO_SOCIO_EMPLEADOR);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_AFILIADO_SOCIO_EMPLEADOR, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}