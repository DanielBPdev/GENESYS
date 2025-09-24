package com.asopagos.validaciones.validadores.novedades.personas;

import java.math.BigInteger;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * CLASE QUE VALIDA:
 * Asociado al afiliado principal, el valor del campo "Medio de
 * pago asignado" debe tener el valor "Transferencia"
 * 
 * 
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 */
public class ValidadorTransferenciaMedioDePago extends ValidadorAbstract {
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorTransferenciaMedioDePago.execute");
        try {
            BigInteger idMedioPago = null;
            try {
                if (datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR) != null) {
                    Long idGrupoFamiliar = Long.parseLong(datosValidacion.get(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR));
                    idMedioPago = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_MEDIO_DE_PAGO_GRUPO_FAMILIAR)
                            .setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, idGrupoFamiliar)
                            .setParameter(ConstantesValidaciones.ESTADO_MEDIO_PAGO_PARAM, NumerosEnterosConstants.UNO)
                            .setParameter(ConstantesValidaciones.TIPO_MEDIO_PAGO_PARAM, TipoMedioDePagoEnum.TRANSFERENCIA.name())
                            .getSingleResult();
                }
                else {
                    TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
                            .valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
                    String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                    idMedioPago = (BigInteger) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_MEDIO_DE_PAGO_TRABAJADOR)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion.name())
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
                            .setParameter(ConstantesValidaciones.ESTADO_MEDIO_PAGO_PARAM, NumerosEnterosConstants.UNO)
                            .setParameter(ConstantesValidaciones.TIPO_MEDIO_PAGO_PARAM, TipoMedioDePagoEnum.TRANSFERENCIA.name())
                            .getSingleResult();
                }
            } catch (NoResultException e) {
                /* No tiene asociado medio de Pago transferencia. */
                idMedioPago = null;
            }

            //se realiza la validación
            if (idMedioPago != null) {
                //validación exitosa
                logger.debug("HABILITADA- Fin de método ValidadorTransferenciaMedioDePago.execute");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_TRANSFERENCIA_MEDIO_DE_PAGO);
            }
            else {
                //validación fallida
                logger.debug("NO HABILITADA- Fin de método ValidadorTransferenciaMedioDePago.execute");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_TRANSFERENCIA_MEDIO_DE_PAGO),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_TRANSFERENCIA_MEDIO_DE_PAGO,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
                    ValidacionCoreEnum.VALIDACION_TRANSFERENCIA_MEDIO_DE_PAGO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}