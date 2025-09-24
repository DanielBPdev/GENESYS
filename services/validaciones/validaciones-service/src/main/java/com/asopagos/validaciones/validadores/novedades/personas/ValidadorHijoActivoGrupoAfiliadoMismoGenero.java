package com.asopagos.validaciones.validadores.novedades.personas;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

public class ValidadorHijoActivoGrupoAfiliadoMismoGenero extends ValidadorAbstract{

	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		try{
                    logger.info("DIFERENTE GENERO- Fin de método ValidadorHijoActivoGrupoAfiliadoMismoGenero.execute");

			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM));
	        String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
	        TipoIdentificacionEnum tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_BENEF_PARAM));
	        String numeroIdentificacionBeneficiario = datosValidacion.get(ConstantesValidaciones.NUM_ID_BENEF_PARAM);

			logger.info("ValidadorHijoActivoGrupoAfiliadoMismoGenero tipoIdentificacion " +tipoIdentificacion);
			logger.info("ValidadorHijoActivoGrupoAfiliadoMismoGenero numeroIdentificacion " +numeroIdentificacion);
			logger.info("ValidadorHijoActivoGrupoAfiliadoMismoGenero tipoIdentificacionBeneficiario " +tipoIdentificacionBeneficiario);
			logger.info("ValidadorHijoActivoGrupoAfiliadoMismoGenero numeroIdentificacionBeneficiario " +numeroIdentificacionBeneficiario);
	        
	        List<PersonaDTO> afiliadosPrincipales = (List<PersonaDTO>) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_AFILIADOS_PRINCIPALES_DE_UN_BENEFICIARIO_DADO_AFILIADO)
	        .setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, ValidacionPersonaUtils.obtenerListaEstadoActivo())
	        .setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionHijo())
	        .setParameter(ConstantesValidaciones.NUM_ID_BENEF_PARAM, numeroIdentificacionBeneficiario)
    		.setParameter(ConstantesValidaciones.TIPO_ID_BENEF_PARAM, tipoIdentificacionBeneficiario)
    		.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
    		.getResultList();
	        
	        PersonaDTO afiliado= (PersonaDTO) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO)
	        .setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
	        .setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion).getSingleResult();

			logger.info("afiliado " + afiliado.getNumeroIdentificacion());
			logger.info("afiliado nombre" + afiliado.getNombreCompleto());
			logger.info("afiliado genero" + afiliado.getGenero());
	        
	        if(afiliadosPrincipales != null && !afiliadosPrincipales.isEmpty())
	        {
	        	for (PersonaDTO afiliadoPrincipal : afiliadosPrincipales) {
					logger.info("afiliadoPrincipal " + afiliadoPrincipal.getNumeroIdentificacion());
					logger.info("afiliadoPrincipal nombre" + afiliadoPrincipal.getNombreCompleto());
					logger.info("afiliadoPrincipal genero" + afiliadoPrincipal.getGenero());
				
	        		if(afiliadoPrincipal.getGenero().equals(afiliado.getGenero())){
	        			logger.info("DIFERENTE GENERO- Fin de método ValidadorHijoActivoGrupoAfiliadoMismoGenero.execute 1");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HIJO_2_GRUPO_AFILIADO_GENERO),ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPO_AFILIADO_GENERO,
								TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	        		}
				}
	        }
	        logger.debug("DIFERENTE GENERO- Fin de método ValidadorHijoActivoGrupoAfiliadoMismoGenero.execute");
    		return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPO_AFILIADO_GENERO);  

		} catch(NoResultException nre){
			logger.info("NO EXISTE AFILIADO- Fin de método ValidadorHijoActivoGrupoAfiliadoMismoGenero.execute 2");
			return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_HIJO_2_GRUPO_AFILIADO_GENERO),ResultadoValidacionEnum.NO_APROBADA,
					ValidacionCoreEnum.VALIDACION_HIJO_2_GRUPO_AFILIADO_GENERO,
					TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
		}
		
		catch (Exception e) {
			logger.error("No evaluado - Ocurrió alguna excepción", e);
			return crearMensajeNoEvaluado(ConstantesValidaciones.KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION,
					ValidacionCoreEnum.VALIDACION_AFILIADO_ACTIVO, TipoExcepcionFuncionalEnum.EXCEPCION_TECNICA);
		}
	}
	
}
