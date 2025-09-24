package com.asopagos.validaciones.validadores.novedades.empleador;

import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.ejb.ValidadorAbstract;

/**
 * Validador 37. Validar que no hay otro empleador registrado 
 * en la Caja de Compensación con la misma Razón social 
 * o nombre (coincidencia exacta).

 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ValidadorEmpleadorMismaRazonSocialNombre extends ValidadorAbstract {
	/*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
     */
    @Override
    public ValidacionDTO execute(Map<String, String> datosValidacion) {
        logger.debug("Inicio de método ValidadorEmpleadorRegistradoCajaRazonSocialNombre.execute");
        try {
            if (datosValidacion != null && !datosValidacion.isEmpty()) {
                
            	TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
            					.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
            	//razón social
                String razonSocial = datosValidacion.get(ConstantesValidaciones.RAZON_SOCIAL);
                
                // Primer nombre
                String primerNombre = datosValidacion.get(ConstantesValidaciones.PRIMER_NOMBRE_PARAM);
                
                // Segundo nombre
                String segundoNombre = datosValidacion.get(ConstantesValidaciones.SEGUNDO_NOMBRE_PARAM);
                
                // Primer apellido
                String primerApellido = datosValidacion.get(ConstantesValidaciones.PRIMER_APELLIDO_PARAM);
                
                // Segundo apellido
                String segundoApellido = datosValidacion.get(ConstantesValidaciones.SEGUNDO_APELLIDO_PARAM);
                
                ClasificacionEnum clasificacion = ClasificacionEnum
                        .valueOf(datosValidacion.get(ConstantesValidaciones.CLASIFICACION_PARAM));
                
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Empresa> c = cb.createQuery(Empresa.class);
                Root<Empresa> empresa = c.from(Empresa.class);
                c.select(empresa);
                List<Empresa> emp = null;
                if (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT) && !ClasificacionEnum.PERSONA_NATURAL.equals(clasificacion)) {
                	ParameterExpression<String> parametroRazonSocial = cb.parameter(String.class);
                	c.where(cb.equal(empresa.get("persona").get("razonSocial"), parametroRazonSocial));
                	emp = entityManager.createQuery(c)
                			.setParameter(parametroRazonSocial, razonSocial)
                			.getResultList();
				}else{
					ParameterExpression<String> parametroPrimerNombre = cb.parameter(String.class);
					ParameterExpression<String> parametroSegundoNombre = cb.parameter(String.class);
					ParameterExpression<String> parametroPrimerApellido= cb.parameter(String.class);
					ParameterExpression<String> parametroSegundoApellido = cb.parameter(String.class);
					c.where(cb.equal(empresa.get("persona").get("primerNombre"), parametroPrimerNombre),
							cb.equal(empresa.get("persona").get("segundoNombre"), parametroSegundoNombre),
							cb.equal(empresa.get("persona").get("primerApellido"), parametroPrimerApellido),
							cb.equal(empresa.get("persona").get("segundoApellido"), parametroSegundoApellido));
					
					emp = entityManager.createQuery(c)
							.setParameter(parametroPrimerNombre, primerNombre)
							.setParameter(parametroSegundoNombre, segundoNombre)
							.setParameter(parametroPrimerApellido, primerApellido)
							.setParameter(parametroSegundoApellido, segundoApellido)
							.getResultList();
				}
                if(emp == null || emp.isEmpty()){
                    logger.debug("Aprobado");
                    return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_EMPLEADOR_MISMA_RAZON_SOCIAL_NOMBRE);
                }else{
                 
                    logger.debug("No aprobada - El empleador no está cubierto por beneficios de Ley 1429 de 2010 y/o el beneficio ha caducado");
                    return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_OTRO_EMPLEADOR_YA_REGISTRADO_EN_LA_CCF),
                            ResultadoValidacionEnum.NO_APROBADA, ValidacionCoreEnum.VALIDACION_EMPLEADOR_MISMA_RAZON_SOCIAL_NOMBRE,
                            TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2);
                }
            }
            else {
                logger.debug("No evaluado- No llegó el mapa con valores");
                return crearMensajeNoEvaluado(ConstantesValidaciones
                		.KEY_OTRO_EMPLEADOR_YA_REGISTRADO_EN_LA_CCF
                		, ValidacionCoreEnum.VALIDACION_EMPLEADOR_MISMA_RAZON_SOCIAL_NOMBRE,
                        TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
            }
        } catch (Exception e) {
            logger.error("No evaluado - Ocurrió alguna excepción", e);
            return crearMensajeNoEvaluado(ConstantesValidaciones
            		.KEY_OTRO_EMPLEADOR_YA_REGISTRADO_EN_LA_CCF
            		, ValidacionCoreEnum.VALIDACION_EMPLEADOR_MISMA_RAZON_SOCIAL_NOMBRE,
                    TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
        }
    }
}
