package com.asopagos.validaciones.validadores.afiliacion.persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.transversal.personas.ISujetoTramite;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;
import com.asopagos.validaciones.ejb.ValidadorAbstract;
import com.asopagos.validaciones.util.ValidacionPersonaUtils;

/**
 * Clase que contiene la lógica para validar si se es beneficiario de un afiliado con el mismo genero. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class ValidadorPersonaHijoAfiliadoPrincipal extends ValidadorAbstract {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.validaciones.api.ValidadorCore#execute(java.util.Map)
	 */
	@Override
	public ValidacionDTO execute(Map<String, String> datosValidacion) {
		logger.debug("Inicio de método ValidadorPersonaHijoAfiliadoPrincipal.execute");
		try {
			if (datosValidacion != null && !datosValidacion.isEmpty()) {
				String tipoId = datosValidacion.get(ConstantesValidaciones.TIPO_ID_PARAM);
				TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoId);
				String numeroIdentificacion = datosValidacion.get(ConstantesValidaciones.NUM_ID_PARAM);
				String genero = datosValidacion.get(ConstantesValidaciones.GENERO_PARAM);
				GeneroEnum generoAfiliado= genero != null ? GeneroEnum.valueOf(genero) : null;
				String numeroIdentificacionAfiliado=datosValidacion.get(ConstantesValidaciones.NUM_ID_AFILIADO_PARAM);
				TipoIdentificacionEnum tipoIdentificacionAfiliado= TipoIdentificacionEnum.valueOf(datosValidacion.get(ConstantesValidaciones.TIPO_ID_AFILIADO_PARAM));
				
				PersonaDetalle personaDetalle=null;
				if (tipoIdentificacion != null && !tipoIdentificacion.equals("")){
					boolean existe = false;
					List<Beneficiario> personasConTipoYNumero = entityManager
							.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO_GENERO)
							.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
							.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
							.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionHijo())
							.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
							.setParameter(ConstantesValidaciones.GENERO_PARAM, generoAfiliado).getResultList();
					// se soluciona glpi 54173
					if(personasConTipoYNumero == null || personasConTipoYNumero.isEmpty()){
						List<Beneficiario> personasConNumero = entityManager
								.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO_GENERO)
								.setParameter(ConstantesValidaciones.NUM_ID_PARAM, numeroIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_ID_PARAM, tipoIdentificacion)
								.setParameter(ConstantesValidaciones.TIPO_BENEFICIARIO_PARAM, ValidacionPersonaUtils.obtenerClasificacionHijo())
								.setParameter(ConstantesValidaciones.ESTADO_BENEFICIARIO, EstadoAfiliadoEnum.ACTIVO)
								.setParameter(ConstantesValidaciones.GENERO_PARAM, generoAfiliado).getResultList();						
						if(personasConNumero != null && !personasConNumero.isEmpty()){
							existe = true;
							if(personasConNumero.iterator().next().getAfiliado().getPersona().getTipoIdentificacion().equals(tipoIdentificacionAfiliado) &&
									personasConNumero.iterator().next().getAfiliado().getPersona().getNumeroIdentificacion().equals(numeroIdentificacion)){
								existe = false;
							}
						}
					}else{
						existe = true;
						if(personasConTipoYNumero.iterator().next().getAfiliado().getPersona().getTipoIdentificacion().equals(tipoIdentificacionAfiliado) &&
								personasConTipoYNumero.iterator().next().getAfiliado().getPersona().getNumeroIdentificacion().equals(numeroIdentificacionAfiliado)){
							existe = false;
						}
					}
					if(existe){
						logger.debug("No aprobada- Existe beneficiario tipo hijo con el mismo genero");
						return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_MISMO_GENERO),
								ResultadoValidacionEnum.NO_APROBADA,
								ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL,TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);					
					}
				}else{
					logger.debug("No evaluado- No llegaron todos los parametros necesarios");
					return crearMensajeNoEvaluado();
				}
			}else{
				logger.debug("No evaluado- El mapa no está lleno");
				return crearMensajeNoEvaluado();
			}
			/*exitoso*/
			logger.debug("Aprobado");
			return crearMensajeExitoso(ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL);
		} catch (Exception e) {
			logger.error("No evaluado- Ocurrió una excepción en el método ValidadorPersonaHijoAfiliadoPrincipal.execute",e);
			return crearMensajeNoEvaluado();
		}
	}


	/**
	 * Mensaje utilizado cuando por alguna razon no se puede evaluar.
	 * @return validacion afiliaacion instanciada.
	 */
	private  ValidacionDTO crearMensajeNoEvaluado(){
		return crearValidacion(
				myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR
						+ myResources.getString(ConstantesValidaciones.KEY_PERSONA_HIJO_MISMO_GENERO),
				ResultadoValidacionEnum.NO_EVALUADA, ValidacionCoreEnum.VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL,
				TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_1);
	}
    /**
     * Método que obtiene las clasisficaciones por sujeto tramite.
     * @param tipo de sujeto tramite.
     * @return listado de las clasificaciones.
     */
	public List<ClasificacionEnum> obtenerClasificacionPorTipo(ISujetoTramite tipo){
		List<ClasificacionEnum> clasificacionList = new ArrayList<ClasificacionEnum>();
		for (ClasificacionEnum clasificacion : ClasificacionEnum.values()) {
			if(clasificacion.getSujetoTramite().equals(tipo)){
				clasificacionList.add(clasificacion);
			}
		}
		return clasificacionList;
	}
}
