/**
 * 
 */
package com.asopagos.validaciones.fovis.validadores.fovis.common;

import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.core.BeneficioEmpleador;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.fovis.constants.ConstantesValidaciones;
import com.asopagos.validaciones.fovis.constants.NamedQueriesConstants;
import com.asopagos.validaciones.fovis.ejb.ValidadorFovisAbstract;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validadador que verifica que el jefe de hogar es dependiente y el empleador es beneficiario
 * de la ley 1429.
 * @author <a href="mailto:anvalbuena@heinsohn.com.co">Andrés Felipe Valbuena</a>
 */
public class ValidadorJefeHogarDependienteBeneficio1429 extends ValidadorFovisAbstract {

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorJefeHogarAlDiaAportes.execute");
        try {
            String tipoIdentificacion = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
            String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
            if (datosValidacion == null || datosValidacion.isEmpty() || tipoIdentificacion == null || tipoIdentificacion.equals("")
                    || numeroIdentificacion == null || numeroIdentificacion.equals("")) {
                // NO EVALUADA mapa validacion
                logger.debug("No evaluado - No llegó el mapa con valores");
                return crearMensajeNoEvaluado();
            }
            // Se consultan los roles afiliado asociados al jefe
            List<RolAfiliado> listRolAfiliado = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_ROL_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION, RolAfiliado.class)
                    .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, TipoIdentificacionEnum.valueOf(tipoIdentificacion))
                    .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

            if (listRolAfiliado == null || listRolAfiliado.isEmpty()) {
                // NO EVALUADA falta informacion
                logger.debug("No evaludao - Falta informacion");
                return crearMensajeNoEvaluado();
            }

            // CASO 3 Jefe de hogar es trabajador dependiente activo y a la vez Independiente o Pensionado activo
            // APROBADA
            List<TipoAfiliadoEnum> listPermitido =  new ArrayList<>();
            listPermitido.add(TipoAfiliadoEnum.PENSIONADO);
            listPermitido.add(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE);
            if (tieneAfiliacion(listRolAfiliado, EstadoAfiliadoEnum.ACTIVO, listPermitido)) {
                logger.debug("Aprobado");
                return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429);
            }

            // CASO 1 Jefe de hogar es trabajador dependiente y está activo con un solo empleador
            // NO APROBADA si el empleador tiene activo beneficio 1429
            // CASO 2 Jefe de hogar es trabajador dependiente y está activo con más de un empleador
            // NO APROBADA si TODOS los empleadores tiene activo beneficio 1429
            listPermitido =  new ArrayList<>();
            listPermitido.add(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
            if (tieneAfiliacion(listRolAfiliado, EstadoAfiliadoEnum.ACTIVO, listPermitido)
                    && esDependienteBeneficio(listRolAfiliado)) {
                logger.debug("No aprobada- No existe persona con número y tipo documento");
                return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429),
                        ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429,
                        TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
            }

            logger.debug("Aprobado");
            return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429);
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado();
        }
    }

    /**
     * Indica si el jefe de hogar tiene una afiliación acorde a los parametros
     * @param listRoles
     *        Lista de roles del jefe de hogar
     * @param estadoAfiliado
     *        Estado esperado del rol
     * @param listTipoAfiliado
     *        Tipo de afiliación esperado
     * @return TRUE si cumple las condiciones FALSE en caso contrario
     */
    private Boolean tieneAfiliacion(List<RolAfiliado> listRoles, EstadoAfiliadoEnum estadoAfiliado,
            List<TipoAfiliadoEnum> listTipoAfiliado) {
        Boolean resultado = false;
        for (RolAfiliado rolAfiliado : listRoles) {
            if (estadoAfiliado.equals(rolAfiliado.getEstadoAfiliado()) && listTipoAfiliado.contains(rolAfiliado.getTipoAfiliado())) {
                resultado = true;
                break;
            }
        }
        return resultado;
    }
    
    /**
     * Método encargado de validar, <b>por tipo y número de identificación</b>, si el jefe de hogar es trabajador dependiente y
     * el empleador esta registrado como beneficiario de ley 1429.
     * @param tipoIdentificacion
     *        tipo de identificación del jefe de hogar.
     * @param numeroIdentificacion
     *        número de identificación del jefe de hogar.
     * @return true si cumple con las condiciones.
     */
    private Boolean esDependienteBeneficio(List<RolAfiliado> listRoles) {
        try {
            logger.debug("Inicio de método ValidadorJefeHogarDependienteBeneficio1429.esDependienteBeneficio1429");
            // Se obtienen los id de empleadores
            List<Long> listIdRoles = new ArrayList<>();
            for (RolAfiliado rolAfiliado : listRoles) {
                if (EstadoAfiliadoEnum.ACTIVO.equals(rolAfiliado.getEstadoAfiliado()) && rolAfiliado.getEmpleador() != null) {
                    listIdRoles.add(rolAfiliado.getIdRolAfiliado());
                }
            }
            // Se consultan los empleadores con beneficio 1429 activo
            List<BeneficioEmpleador> listBeneficios = entityManager
                    .createNamedQuery(NamedQueriesConstants.VALIDAR_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429, BeneficioEmpleador.class)
                    .setParameter(ConstantesValidaciones.KEY_ID_ROL_AFILIADO, listIdRoles)
                    .setParameter(ConstantesValidaciones.TIPO_BENEFICIO_PARAM, TipoBeneficioEnum.LEY_1429)
                    .setParameter("estadoBeneficio", Boolean.TRUE).getResultList();

            // Se verifica si todos los empleadores tiene activo el beneficio
            if (listBeneficios == null || listBeneficios.isEmpty()) {
                return false;
            }
            
            logger.debug("Fin de método ValidadorJefeHogarAlDiaAportes.estaAlDiaPorTipoNumeroDocumento");
            if (listIdRoles.size() == listBeneficios.size()) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            logger.error("Exception - Ocurrió alguna excepción", e);
            return false;
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
                        + myResources.getString(ConstantesValidaciones.KEY_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429),
                ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }
}
