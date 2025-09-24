/**
 *
 */
package com.asopagos.novedades.convertidores.empleador;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

import com.asopagos.empleadores.clients.ConsultarRepresentantesLegalesEmpleador;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.EjecutarCambiosRepresentantes;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.novedades.dto.DatosNovedadRepresentanteDTO;
import com.asopagos.novedades.dto.SocioEmpleadorDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar Representante legal, suplente o socios
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarRepresentantesNovedad implements NovedadCore{

	private final ILogger logger = LogManager.getLogger(ActualizarRepresentantesNovedad.class);
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.DatosNovedadDTO, com.asopagos.enumeraciones.core.TipoTransaccionEnum)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		logger.debug("Inicio de método ActualizarEmpleadorNovedad.transformarServicio");

		/*se transforma a un objeto de datos del empleador*/
		DatosEmpleadorNovedadDTO datosEmpleador = (DatosEmpleadorNovedadDTO) solicitudNovedadDTO.getDatosEmpleador();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();

		DatosNovedadRepresentanteDTO datosNovedadRepresentanteDTO = new DatosNovedadRepresentanteDTO();
		if(novedad.equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL) ||
				novedad.equals(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB)){
			//Se asignan los datos.
			asignarDatosRepresentanteLegal(datosNovedadRepresentanteDTO, datosEmpleador);
			asignarDatosRepresentanteSuplente(datosNovedadRepresentanteDTO, datosEmpleador);
			asignarDatosSocio(datosNovedadRepresentanteDTO, datosEmpleador);
		}
		datosNovedadRepresentanteDTO.setIdEmpleador(datosEmpleador.getIdEmpleador());
		/*se instancia el Composite para Atualizar Representante Legal*/
		EjecutarCambiosRepresentantes ejecutarCambiosRepresentantes = new EjecutarCambiosRepresentantes(datosNovedadRepresentanteDTO);
		return ejecutarCambiosRepresentantes;
	}

	/**
	 * Método que se encarga de asignar los datos de Representante Legal
	 * @param datosNovedadRepresentante
	 * @param datosEmpleador
	 */
	private void asignarDatosRepresentanteLegal(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador){
		logger.debug("Inicio de método asignarDatosRepresentanteLegal(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador)");
		Persona representanteLegal = new Persona();
		Ubicacion ubicacionRepLegal = new Ubicacion();
		representanteLegal.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionRepLegal());
		representanteLegal.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionRepLegal());
		representanteLegal.setPrimerNombre(datosEmpleador.getPrimerNombreRepLegal());
		representanteLegal.setSegundoNombre(datosEmpleador.getSegundoNombreRepLegal());
		representanteLegal.setPrimerApellido(datosEmpleador.getPrimerApellidoRepLegal());
		representanteLegal.setSegundoApellido(datosEmpleador.getSegundoApellidoRepLegal());
		//Se asignan datos de la Ubicación del Representante Legal
		ubicacionRepLegal.setEmail(datosEmpleador.getEmailRepLegal());
		ubicacionRepLegal.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoRepLegal() != null ? datosEmpleador.getIndicativoTelFijoRepLegal().toString() : null);
		ubicacionRepLegal.setTelefonoFijo(datosEmpleador.getTelefonoFijoRepLegal());
		ubicacionRepLegal.setTelefonoCelular(datosEmpleador.getTelefonoCelularRepLegal());
		ubicacionRepLegal.setMunicipio(datosEmpleador.getMunicipio() != null && datosEmpleador.getMunicipio().getIdMunicipio() != null ? datosEmpleador.getMunicipio() : null);
		logger.info("***Weizman => NovedadesComposite.asignarDatosRepresentanteLegal => ubicacionRepLegal -> " + ubicacionRepLegal.toString());
		representanteLegal.setUbicacionPrincipal(ubicacionRepLegal);
		//Se asocian los datos del Representante Legal al DTO
		datosNovedadRepresentante.setRepresentanteLegal(representanteLegal);
		logger.info("***Weizman => NovedadesComposite.asignarDatosRepresentanteLegal => representanteLegal -> " + representanteLegal.toString());
		logger.debug("Fin de método asignarDatosRepresentanteLegal(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador)");
	}

	/**
	 * Método que se encarga de actualizar los datos de Representante Legal Suplente
	 * @param datosNovedadRepresentante
	 * @param datosEmpleador
	 */
	private void asignarDatosRepresentanteSuplente(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador){
		logger.debug("Inicio de método asignarDatosRepresentanteSuplente(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador)");
		//Si existe informacion asociada al Representante Legal Suplente.
		if(datosEmpleador.getTipoIdentificacionRepLegalSupl() != null && datosEmpleador.getNumeroIdentificacionRepLegalSupl() != null){
			Persona representanteSuplente = new Persona();
			Ubicacion ubicacionRepSuplente = new Ubicacion();

			representanteSuplente.setTipoIdentificacion(datosEmpleador.getTipoIdentificacionRepLegalSupl());
			representanteSuplente.setNumeroIdentificacion(datosEmpleador.getNumeroIdentificacionRepLegalSupl());
			representanteSuplente.setPrimerNombre(datosEmpleador.getPrimerNombreRepLegalSupl());
			representanteSuplente.setSegundoNombre(datosEmpleador.getSegundoNombreRepLegalSupl());
			representanteSuplente.setPrimerApellido(datosEmpleador.getPrimerApellidoRepLegalSupl());
			representanteSuplente.setSegundoApellido(datosEmpleador.getSegundoApellidoRepLegalSupl());
			//Se asignan datos de la Ubicación del Representante Legal Suplente
			ubicacionRepSuplente.setEmail(datosEmpleador.getEmailRepLegalSupl());
			if(datosEmpleador.getIndicativoTelFijoRepLegalSupl() != null){
				ubicacionRepSuplente.setIndicativoTelFijo(datosEmpleador.getIndicativoTelFijoRepLegalSupl().toString());
			}
			ubicacionRepSuplente.setTelefonoFijo(datosEmpleador.getTelefonoFijoRepLegalSupl());
			ubicacionRepSuplente.setTelefonoCelular(datosEmpleador.getTelefonoCelularRepLegalSupl());
			representanteSuplente.setUbicacionPrincipal(ubicacionRepSuplente);
			//Se asocian los datos del Representante Legal Suplente al DTO
			datosNovedadRepresentante.setRepresentanteLegalSuplente(representanteSuplente);
			logger.debug("Fin de método asignarDatosRepresentanteSuplente(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador)");
		}
	}


	/**
	 * Método que se encarga de actualizar los datos de Socios
	 * @param datosNovedadRepresentante
	 * @param datosEmpleador
	 */
	private void asignarDatosSocio(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador){
		logger.debug("Inicio de método asignarDatosSocio(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador)");
		List<SocioEmpleador> socios = new ArrayList<>();
		//Si existe información asociada a Socios
		if(datosEmpleador.getSociosEmpleador() != null && !datosEmpleador.getSociosEmpleador().isEmpty()){
			for (SocioEmpleadorDTO socioEmpleadorDTO : datosEmpleador.getSociosEmpleador()) {
				SocioEmpleador socioEmpleador = new SocioEmpleador();
				//Se asignan datos del Socio
				Persona socio = new Persona();
				socio.setTipoIdentificacion(socioEmpleadorDTO.getTipoIdentificacionSocio());
				socio.setNumeroIdentificacion(socioEmpleadorDTO.getNumeroIdentificacionSocio());
				socio.setPrimerNombre(socioEmpleadorDTO.getPrimerNombreSocio());
				socio.setSegundoNombre(socioEmpleadorDTO.getSegundoNombreSocio());
				socio.setPrimerApellido(socioEmpleadorDTO.getPrimerApellidoSocio());
				socio.setSegundoApellido(socioEmpleadorDTO.getSegundoApellidoSocio());
				socioEmpleador.setPersona(socio);
				//Si existe información de Cónyuge Socio
				if(socioEmpleadorDTO.getTipoIdentificacionConyugeSocio() != null
						&& socioEmpleadorDTO.getNumeroIdentificacionConyugeSocio() != null){
					Persona conyugeSocio = new Persona();
					conyugeSocio.setTipoIdentificacion(socioEmpleadorDTO.getTipoIdentificacionConyugeSocio());
					conyugeSocio.setNumeroIdentificacion(socioEmpleadorDTO.getNumeroIdentificacionConyugeSocio());
					conyugeSocio.setPrimerNombre(socioEmpleadorDTO.getPrimerNombreConyugeSocio());
					conyugeSocio.setSegundoNombre(socioEmpleadorDTO.getSegundoNombreConyugeSocio());
					conyugeSocio.setPrimerApellido(socioEmpleadorDTO.getPrimerApellidoConyugeSocio());
					conyugeSocio.setSegundoApellido(socioEmpleadorDTO.getSegundoApellidoConyugeSocio());
					socioEmpleador.setConyugue(conyugeSocio);
				}

				socioEmpleador.setExistenCapitulaciones(socioEmpleadorDTO.getExistenCapitulaciones());
				socioEmpleador.setIdentificadorDocumentoCapitulaciones(socioEmpleadorDTO.getIdentificadorDocumentoCapitulaciones());
				socioEmpleador.setIdSocioEmpleador(socioEmpleadorDTO.getIdSocioEmpleador());
				socios.add(socioEmpleador);
			}
		}
		datosNovedadRepresentante.setSociosEmpleador(socios);
		logger.debug("Fin de método asignarDatosRepresentanteSuplente(DatosNovedadRepresentanteDTO datosNovedadRepresentante, DatosEmpleadorNovedadDTO datosEmpleador))");

	}

	@Override
	public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
		// TODO Auto-generated method stub

	}

	/**
	 * Realiza Merge entre 2 objetos del mismo tipo. Si le envías
	 * @param target objeto actual
	 * @param source objeto nuevo con la nueva infomación a actualizar
	 * @return tarjet retorna el objeto que le enviemos como primer parámetro (ojeto actual)
	 */
	public static <T> T mergeObjects(T target, T source) {
		if (target == null || source == null) {
			throw new IllegalArgumentException("Both target and source objects must be non-null.");
		}

		Class<?> targetClass = target.getClass();
		Class<?> sourceClass = source.getClass();

		if (!targetClass.equals(sourceClass)) {
			throw new IllegalArgumentException("Both target and source objects must be of the same class.");
		}

		try {
			Field[] fields = targetClass.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				Object sourceValue = field.get(source);
				if (sourceValue != null) {
					field.set(target, sourceValue);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return target;
	}
}
