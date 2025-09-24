package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import java.util.logging.Logger;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 * Clase que contiene la lógica para validar cuando la persona esta sin
 * coincidencias en los apellidos respecto al afiliado
 *
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidadorPersonaSinCoincidenciaApellidosAfiliado extends ValidadorAbstract {

    /**
     * Metodo encargado de validar si la persona esta sin coincidencias con los
     * apellidos del afiliado principal
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        
        try {
            logger.debug("Inicio ValidadorPersonaSinCoincidenciaApellidosAfiliado");
            logger.info("Inicio ValidadorPersonaSinCoincidenciaApellidosAfiliado");
            String tipoBeneficiario = datosValidacion.get(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM);
            
            // se agrego esta linea  con tipo beneficiario en custodia
            if (datosValidacion != null && !datosValidacion.isEmpty() && tipoBeneficiario!="BENEFICIARIO_EN_CUSTODIA") {
            //if (datosValidacion != null && !datosValidacion.isEmpty()) { //linea original
                //Se obtienen los nombres y apellidos para la respuesta si no pasa la validacion
                String primerNombreBeneficiario = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM).toUpperCase() : "";
                String SegundoNombreBeneficiario = datosValidacion.get(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM).toUpperCase() : "";
                String primerApellidoBeneficiario = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM).toUpperCase() : "";
                String segundoApellidoBeneficiario = datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM) != null ? datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM).toUpperCase() : "";

                String nombreCompletoBeneficiario = primerNombreBeneficiario + " " + SegundoNombreBeneficiario + " " + primerApellidoBeneficiario + " " + segundoApellidoBeneficiario;
                //logger.info(" ValidadorPersonaSinCoincidenciaApellidosAfiliado nombreCompletoBeneficiario beneficiario VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO"+nombreCompletoBeneficiario);
                //Inicialmente se toman los parametros que vendrian del payload del botón validar
                String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
                TipoIdentificacionEnum tipoIdentificacion = tipoId == null ? null : TipoIdentificacionEnum.valueOf(tipoId);//ajuste para controlar null pointer exception
                String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);

                String tipoIdAfiliado = datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM);
                TipoIdentificacionEnum tipoIdentificacionAfiliado = tipoIdAfiliado == null ? null : TipoIdentificacionEnum.valueOf(tipoIdAfiliado);
                String numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);

                //Si TIPO_ID_AFILIADO_PARAM es null es porque viene del botón radicar y en este caso las key se nombran diferente
                if (datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM) == null) {
                    tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM);
                    tipoIdentificacion = tipoId == null ? null : TipoIdentificacionEnum.valueOf(tipoId);
                    numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

                    tipoIdAfiliado = datosValidacion.get(ConstantesValidaciones.TIPO_ID_CAMBIO_PARAM);
                    tipoIdentificacionAfiliado = tipoIdAfiliado == null ? null : TipoIdentificacionEnum.valueOf(tipoIdAfiliado);
                    numeroIdentificacionAfiliado = datosValidacion.get(ConstantesValidaciones.NUM_ID_CAMBIO_PARAM);
                }

                Afiliado afiliado = new Afiliado();

                if (tipoIdentificacionAfiliado != null && !tipoIdentificacionAfiliado.equals("") && !numeroIdentificacionAfiliado.equals("")) {     
                    afiliado = (Afiliado) entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacionAfiliado)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacionAfiliado)
                            .getSingleResult();
                }
                if (tipoIdentificacion != null && !tipoIdentificacion.equals("") && !numeroIdentificacion.equals("")) {
                    Beneficiario beneficiario = null;
                    List<Beneficiario> beneficiarioTipoIdentificacion = entityManager
                            .createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO)
                            .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
                            .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getResultList();

                    String primerApellidoAfiliado = afiliado != null ? afiliado.getPersona().getPrimerApellido().toUpperCase() : null;
                    String segundoApellidoAfiliado = afiliado != null ? afiliado.getPersona().getSegundoApellido().toUpperCase() : null;
                    if (beneficiarioTipoIdentificacion != null && !beneficiarioTipoIdentificacion.isEmpty()) {
                        beneficiario = beneficiarioTipoIdentificacion.get(0);
                    }
                    // Se verifica si el beneficiario no existe
                    if (beneficiario == null || beneficiario != null) {
                        /**
                         * Se verifica el primer y segundo apellido del
                         * beneficiario respecto al afiliado Principal
                         */
                        //boolean concidenApellido = false;
                        if (primerApellidoBeneficiario.equals(primerApellidoAfiliado)
                                || segundoApellidoBeneficiario.equals(primerApellidoAfiliado)
                                || segundoApellidoAfiliado.equals(segundoApellidoBeneficiario)
                                || segundoApellidoAfiliado.equals(primerApellidoBeneficiario)
                                ) {
                           
                            return crearMensajeExitoso(
                                    ValidacionCoreEnum.VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO);
                        } else {
                              //Se inicia el procedimiento para validar los apellidos del conyuge
                              //GLPI 54299
                              GrupoFamiliar grupoFamiliar = (GrupoFamiliar) entityManager
                                            .createNamedQuery(NamedQueriesConstants.BUSCAR_GRUPOFAMILIAR_POR_AFILIADO_ID)
                                            .setParameter(ConstantesValidaciones.NUM_ID_AFILIADO, afiliado.getIdAfiliado())
                                            .getSingleResult();
                              
                              if (grupoFamiliar != null){
                                    logger.info("VAL04" + grupoFamiliar.getIdGrupoFamiliar());
                                    Beneficiario _beneficiario;
                                    try{
                                        _beneficiario = (Beneficiario) entityManager
                                            .createNamedQuery(NamedQueriesConstants.BUSCAR_CONYUGE_GRUPO_AFILIADO)
                                            .setParameter(ConstantesValidaciones.NUM_ID_GRUPOFAMILIAR, grupoFamiliar.getIdGrupoFamiliar())
                                            .getSingleResult();
                                    }catch (NoResultException e) {
                                        _beneficiario = null;
                                    }
                                   
                                    if(_beneficiario != null){
                                       
                                        String primerApellidoConyuge = _beneficiario.getPersona().getPrimerApellido().toUpperCase();
                                        String segundoApellidoConyuge = _beneficiario.getPersona().getSegundoApellido().toUpperCase();
                                        if (primerApellidoBeneficiario.equals(primerApellidoConyuge)
                                          || segundoApellidoBeneficiario.equals(primerApellidoConyuge)     
                                          || segundoApellidoConyuge.equals(segundoApellidoBeneficiario)
                                          || segundoApellidoConyuge.equals(primerApellidoBeneficiario)) {
                                           
                                          return crearMensajeExitoso(
                                              ValidacionCoreEnum.VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO);
                                        }
                                    }
                              }
                             
                              //si todas las validaciones son fallidas, retorna la excepción
                              return crearValidacion(
                                    myResources.getString(
                                            ConstantesValidaciones.KEY_PERSONA_NO_COINCIDE_APELLIDOS_AFILIADO)
                                            .replace(ConstantesValidaciones.MENSAJE_PARAM_0, nombreCompletoBeneficiario),
                                    ResultadoValidacionEnum.NO_APROBADA,
                                    ValidacionCoreEnum.VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO,
                                    TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
                              
                        }
                    } else {
                        
                        return crearValidacion(
                                myResources.getString(
                                        ConstantesValidaciones.KEY_PERSONA_NO_COINCIDE_APELLIDOS_AFILIADO)
                                        .replace(ConstantesValidaciones.MENSAJE_PARAM_0, nombreCompletoBeneficiario),
                                ResultadoValidacionEnum.NO_APROBADA,
                                ValidacionCoreEnum.VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO,
                                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);

                    }
                } else {
                    /**
                     * mensaje no evaluado porque faltan datos
                     */
                    logger.debug("No evaluado- Faltan datos");
                    return crearMensajeNoEvaluado();
                }
            } else {
                /**
                 * mensaje no evaluado porque falta informacion
                 */
                logger.debug("No evaludao- Falta informacion");
                return crearMensajeNoEvaluado();
            }
        } catch (Exception e) {
            /**
             * No evaluado ocurrió alguna excepción
             */
            logger.debug("No evaludao- Ocurrio alguna excepcion" + e);
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
                + myResources.getString(ConstantesValidaciones.KEY_PERSONA_NO_COINCIDE_APELLIDOS_AFILIADO),
                ResultadoValidacionEnum.NO_EVALUADA,
                ValidacionCoreEnum.VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO,
                TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
    }

}
