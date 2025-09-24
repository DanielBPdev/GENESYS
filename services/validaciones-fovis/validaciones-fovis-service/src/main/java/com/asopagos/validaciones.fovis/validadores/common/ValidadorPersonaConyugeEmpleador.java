package com.asopagos.validaciones.fovis.validadores.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;
import com.asopagos.validaciones.fovis.util.ValidacionFovisPersonaUtils;

/**
 * Clase que contiene la lógica para validar cuando la persona es conyuge del empleador.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaConyugeEmpleador extends ValidadorFovisAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorPersonaConyugeEmpleador.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                //Se capturan los datos de la persona
                String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                TipoIdentificacionEnum tipoIdenPersona = TipoIdentificacionEnum.valueOf(tipoId);
                String numeroIdenPersona = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
                //Se capturan los datos del empleador 
                String tipoIdEmpleador = datosValidacion.get(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM);
                TipoIdentificacionEnum tipoIdenEmpleadorEnum = TipoIdentificacionEnum.valueOf(tipoIdEmpleador);
                String numeroIdEmpleador = datosValidacion.get(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM);
                if (tipoIdenPersona != null && !numeroIdenPersona.equals("")) {
                    ArrayList<SolicitudAfiliacionEmpleador> solAfiliacionEmpl = new ArrayList<>();
                     solAfiliacionEmpl = (ArrayList<SolicitudAfiliacionEmpleador>) entityManager
                            .createNamedQuery(NamedQueriesConstants.VALIDAR_EMPLEADOR_RELACIONADO_EN_LA_SOLICITUD_POR_TIPO_SOLICITANTE)
                            .setParameter(ConstantesValidaciones.TIPO_ID_EMPLEADOR_PARAM, tipoIdenEmpleadorEnum)
                            .setParameter(ConstantesValidaciones.NUM_ID_EMPLEADOR_PARAM, numeroIdEmpleador)
                            .setParameter(ConstantesValidaciones.CLASIFICACION_PARAM,
                                    ValidacionFovisPersonaUtils.obtenerClasificacionNaturalYDomestico())
                            .getResultList();
                    if (solAfiliacionEmpl != null && !solAfiliacionEmpl.isEmpty()) {
                        
                        List<Beneficiario> lstBeneficiarios = entityManager
                                .createNamedQuery(NamedQueriesConstants.VALIDAR_BENEFICIARIO_REALCIONADO_CON_AFILIADO)
                                .setParameter("tipoBeneficiario",ClasificacionEnum.CONYUGE)
                                .setParameter("estadoBeneficiario",EstadoAfiliadoEnum.ACTIVO)
                                .setParameter("tipoIdAfiliado",tipoIdenPersona)
                                .setParameter("numeroIdAfiliado",numeroIdenPersona)
                                .setParameter("tipoIdEmpleador",tipoIdenEmpleadorEnum)
                                .setParameter("numeroIdEmpleador",numeroIdEmpleador)
                                .getResultList();

                        if (!lstBeneficiarios.isEmpty()) {
                            logger.debug("No aprobada- La persona es conyuge del empleador");
                            return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_CONYUGE_EMPLEADOR),
                                    ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_PERSONA_CONYUGE_EMPLEADOR,
                                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
                        }
                    }
                }
                else {
                    logger.debug("NO EVALUADO - No hay parametros");
                    return crearMensajeNoEvaluado();
                }
            }
            else {
                logger.debug("NO EVALUADO- no hay valores en el map");
                return crearMensajeNoEvaluado();
            }
            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_CONYUGE_EMPLEADOR);
        } catch (NoResultException e) {
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_CONYUGE_EMPLEADOR);
        } catch (Exception e) {
            logger.error("NO EVALUADO  ocurrió un tipo de excepción no esperada", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Mensaje utilizado cuando por alguna razon no se puede evaluar.
     * @return validacion afiliaacion instanciada.
     */
    private ValidacionDTO crearMensajeNoEvaluado() {
        return crearValidacion(
                myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
                        + myResources.getString(ConstantesValidaciones.KEY_PERSONA_CONYUGE_EMPLEADOR),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_CONYUGE_EMPLEADOR,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
