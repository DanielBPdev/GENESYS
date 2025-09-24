package com.asopagos.afiliaciones.personas.web.composite.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.web.composite.ejb.comun.AfiliacionPersonasWebCompositeBusinessComun;
import com.asopagos.afiliaciones.personas.web.composite.service.ValidacionPersonasWebCompositeService;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import com.asopagos.dto.ListaDatoValidacionDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import java.util.Calendar;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.asopagos.afiliaciones.personas.clients.CrearSolicitudCargueMultiple;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;

/**
 * <b>Descripcion:</b> EJB que implementa los servicios de composición del
 * proceso de afiliación de personas WEB
 *
 * @author Julian Andres Sanchez
 *         <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez@heinsohn.com.co
 *         </a>
 */
@Stateless
public class ValidacionPersonasWebCompositeBusiness extends AfiliacionPersonasWebCompositeBusinessComun
		implements ValidacionPersonasWebCompositeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ILogger logger = LogManager.getLogger(ValidacionPersonasWebCompositeBusiness.class);

	@PostConstruct
	private void init() {

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asopagos.afiliaciones.personas.web.composite.service.ValidacionPersonasWebCompositeService#validarDatosAfiliacionTrabajadorCandidato(java.lang.Long,
	 *      java.util.List, java.lang.Long)
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public void validarDatosAfiliacionTrabajadorCandidato(Long idCargueMultiple,
			List<AfiliarTrabajadorCandidatoDTO> lstTrabajadorCandidatoDTO, Long numeroDiaTemporizador,
			String nombreArchivo, UserDTO userDTO) {
		logger.info("**__**Inicia metodo validarDatosAfiliacionTrabajadorCandidato");
		List<Long> lstIdSolicitud = new ArrayList<Long>();
		String ciudadSolicitud = "";
		String razonSocial = "";
		String correoEmpleador = "";
		TipoIdentificacionEnum tipoIdentificacionEmpleador = null;
		String numeroIdentificacionEmpleador = null;
		// Actualizo el estado del cargue multiple en la base de datos a
		// EN_PROCESO
		actualizarEstadoCargueMultiple(idCargueMultiple, EstadoCargaMultipleEnum.EN_PROCESO);
		ProcesoEnum proceso = ProcesoEnum.AFILIACION_DEPENDIENTE_WEB;
		TipoAfiliadoEnum objetoValidacion = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
		List<AfiliarTrabajadorCandidatoDTO> lstGeneralCandidato = new ArrayList<AfiliarTrabajadorCandidatoDTO>();
		// Ejecucion del Bloque PRINCIPAL
		//String bloquePrincipal = "PRINCIPAL";
		String bloquePrincipal = "122-363-1";
		ListaDatoValidacionDTO lstValidacionBloquePrincipal = AfiliacionPersonasWebCompositeBusinessComun
				.validarPersonasCargaMultipleStoredProcedure(bloquePrincipal, proceso, objetoValidacion.toString(),
						lstTrabajadorCandidatoDTO);
		// Ejecucion del Bloque 122-363-1
		String bloqueUno = "122-363-1";
		//cambios alexander
		lstGeneralCandidato.addAll(lstValidacionBloquePrincipal.getCandidatoAfiliacionDTOAprobado());
		lstGeneralCandidato.addAll(lstValidacionBloquePrincipal.getCandidatoAfiliacionDTONoAprobado());
			CrearSolicitudCargueMultiple crearSolicitudCargueMultiple = new CrearSolicitudCargueMultiple(idCargueMultiple);
			crearSolicitudCargueMultiple.execute();
			//solicitud asociada al cargue y al dato temporal
			Long res = crearSolicitudCargueMultiple.getRes();
			for (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO : lstGeneralCandidato){
			afiliarTrabajadorCandidatoDTO.setIdSolicitudGlobal(res);
			afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setIdSolicitudGlobal(res);
			afiliarTrabajadorCandidatoDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
			afiliarTrabajadorCandidatoDTO.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
			afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
			afiliarTrabajadorCandidatoDTO.setNombreArchivo(nombreArchivo);
		}
		AfiliacionPersonasWebCompositeBusinessComun.guardarInformacionTemporalCargueMultipleTotal(lstGeneralCandidato,res);
		//fin cambios alexander 
	/*	if (lstAprobado != null) { 
			ListaDatoValidacionDTO lstValidacionBloqueUno = AfiliacionPersonasWebCompositeBusinessComun
					.validarPersonasCargaMultiple(bloqueUno, proceso, objetoValidacion.toString(), lstAprobado);
			if (lstValidacionBloqueUno.getCandidatoAfiliacionDTOAprobado() != null) {
				lstGeneralCandidato.addAll(lstValidacionBloqueUno.getCandidatoAfiliacionDTOAprobado());
			}
			if (lstValidacionBloqueUno.getCandidatoAfiliacionDTONoAprobado() != null) {
				lstGeneralCandidato.addAll(lstValidacionBloqueUno.getCandidatoAfiliacionDTONoAprobado());
			}
		}
		// Ejecucion del Bloque 122-363-1N
		String bloqueUnoN = "122-363-1N";
		List<AfiliarTrabajadorCandidatoDTO> lstNoAprobado = lstValidacionBloquePrincipal.getCandidatoAfiliacionDTONoAprobado();
		if (lstNoAprobado != null) {
			ListaDatoValidacionDTO lstValidacionBloqueUnoN = AfiliacionPersonasWebCompositeBusinessComun
					.validarPersonasCargaMultiple(bloqueUnoN, proceso, objetoValidacion.toString(), lstNoAprobado);
			if (lstValidacionBloqueUnoN.getCandidatoAfiliacionDTOAprobado() != null) {
				lstGeneralCandidato.addAll(lstValidacionBloqueUnoN.getCandidatoAfiliacionDTOAprobado());
			}
			if (lstValidacionBloqueUnoN.getCandidatoAfiliacionDTONoAprobado() != null) {
				lstGeneralCandidato.addAll(lstValidacionBloqueUnoN.getCandidatoAfiliacionDTONoAprobado());
			}
		} */
	/*	for (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO : lstGeneralCandidato){
			CrearSolicitudCargueMultiple crearSolicitudCargueMultiple = new CrearSolicitudCargueMultiple(idCargueMultiple);
			crearSolicitudCargueMultiple.execute();
			//solicitud asociada al cargue y al dato temporal
			Long res = crearSolicitudCargueMultiple.getRes();
			afiliarTrabajadorCandidatoDTO.setIdSolicitudGlobal(res);
			afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setIdSolicitudGlobal(res);
			afiliarTrabajadorCandidatoDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
			afiliarTrabajadorCandidatoDTO.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
			afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
			afiliarTrabajadorCandidatoDTO.setNombreArchivo(nombreArchivo);
			AfiliacionPersonasWebCompositeBusinessComun.guardarInformacionTemporalCargueMultiple(afiliarTrabajadorCandidatoDTO);
			logger.info("aqui andamos"+ res);
		}*/
		// // Se almacenan los datos temporalmente
		// for (AfiliarTrabajadorCandidatoDTO afiliarTrabajadorCandidatoDTO : lstGeneralCandidato) {
		// 	afiliarTrabajadorCandidatoDTO
		// 			.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
		// 	afiliarTrabajadorCandidatoDTO.setClasificacion(ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
		// 	afiliarTrabajadorCandidatoDTO.getAfiliadoInDTO().setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
		// 	afiliarTrabajadorCandidatoDTO.setNombreArchivo(nombreArchivo);
		// 	Long idSolicitud = AfiliacionPersonasWebCompositeBusinessComun
		// 			.crearRegistroTemporalSolicitudTrabajadorCandidato(afiliarTrabajadorCandidatoDTO);
		// 	lstIdSolicitud.add(idSolicitud);

		// 	if (afiliarTrabajadorCandidatoDTO.getRazonSocialNombre() != null) {
		// 		razonSocial = afiliarTrabajadorCandidatoDTO.getRazonSocialNombre();
		// 	}
		// 	if (afiliarTrabajadorCandidatoDTO.getTipoIdentificacionEmpleador() != null) {
		// 		tipoIdentificacionEmpleador = afiliarTrabajadorCandidatoDTO.getTipoIdentificacionEmpleador();
		// 	}
		// 	if (afiliarTrabajadorCandidatoDTO.getNumeroIdentificacionEmpleador() != null) {
		// 		numeroIdentificacionEmpleador = afiliarTrabajadorCandidatoDTO.getNumeroIdentificacionEmpleador();
		// 	}
		// 	if (afiliarTrabajadorCandidatoDTO.getCorreoEmpleador() != null) {
		// 		correoEmpleador = afiliarTrabajadorCandidatoDTO.getCorreoEmpleador();
		// 	}
			
		// 	//se cierran las solicitudes que no pasaron validaciones
		// 	if(!candidato.getAfiliable()){
		// 		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(candidato.getIdSolicitudGlobal(),
		// 				EstadoSolicitudAfiliacionPersonaEnum.CANCELADA);
				
		// 		AfiliacionPersonasWebCompositeBusinessComun.actualizarEstadoSolicitudPersona(candidato.getIdSolicitudGlobal(),
		// 				EstadoSolicitudAfiliacionPersonaEnum.CERRADA);
				
		// 		IntentoAfiliacionInDTO afiliacionInDTO = new IntentoAfiliacionInDTO();
		// 		afiliacionInDTO.setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum.INCUMPLIMIENTO_VALIDACIONES);
		// 		afiliacionInDTO.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION);
		// 		afiliacionInDTO.setIdSolicitud(candidato.getIdSolicitudGlobal());
		// 		AfiliacionPersonasWebCompositeBusinessComun.registrarIntentoAfiliacion(afiliacionInDTO);
		// 	}
		// }
		// /* Se inicia el temporizador por número de días */
		// generarTemporizador(numeroDiaTemporizador, idCargueMultiple, lstIdSolicitud, ciudadSolicitud, razonSocial,
		// 		tipoIdentificacionEmpleador, numeroIdentificacionEmpleador, correoEmpleador, userDTO);
		actualizarEstadoCargueMultiple(idCargueMultiple, EstadoCargaMultipleEnum.EVALUADO);
		//Se procede a actualizar la consola de estados 
		ConsolaEstadoCargueProcesoDTO consolaEstadoCargueProcesoDTO=new ConsolaEstadoCargueProcesoDTO();
		consolaEstadoCargueProcesoDTO.setCargue_id(idCargueMultiple);
		consolaEstadoCargueProcesoDTO.setEstado(EstadoCargueMasivoEnum.FINALIZADO);
		consolaEstadoCargueProcesoDTO.setProceso(TipoProcesoMasivoEnum.CARGUE_DE_AFILIACION_MULTIPLE_122);
		AfiliacionPersonasWebCompositeBusinessComun.actualizarCargueConsolaEstado(idCargueMultiple, consolaEstadoCargueProcesoDTO);
			logger.info("**__**Finaliza  metodo validarDatosAfiliacionTrabajadorCandidato");
	}
	
}