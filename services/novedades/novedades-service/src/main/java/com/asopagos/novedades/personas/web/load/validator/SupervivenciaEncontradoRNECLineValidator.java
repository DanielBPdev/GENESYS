package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.novedades.clients.ConsultarTipoNumeroIdentificacionYTipo;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.util.GetValueUtil;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class SupervivenciaEncontradoRNECLineValidator extends LineValidator {

	/**
	 * Listado de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		lstHallazgos = new ArrayList<>();
		Map<String, Object> line = arguments.getLineValues();
		System.out.println("**__** Inicia  SupervivenciaEncontradoRNECLineValidator ");
		try {
			if (line.get(ArchivoCampoNovedadConstante.TIPO_REGISTRO_UNO) != null) {
				// validacion codigo de entidad CCF
				varificarRegistro1(arguments, line);

			}
			if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TRES) != null) {
				// fin Validaciones registros tipo TRES
				// Inicio Validaciones registros tipo TRES
				// Tipo de identificacion - Registro Tipo 3
				verificarExistenciaGenesysTipo3(arguments, line);
				verificarTipoNumeroIdentificacion(arguments, line);
				// Verificacion existencia persona GENESYS
				// Estado ANI - Registro Tipo 3
				verificarEstadoANI(arguments, line);
				// validacion de defuncion si ya se encuentra fallecido
				validarFallecimiento3(arguments, line);
				verificarFechasTipo3(arguments, line);
			}
			if (line.get(ArchivoCampoNovedadConstante.TIPO_REGISTRO_DOS) != null) {
				// Remitir a bandeja de inconsistencia por cargue de archivos
				verificarExistenciaGenesysTipo2(arguments, line);
				// validacion de defuncion
				verificarFallecimiento2(arguments, line);
				verificarFechasTipo2(arguments, line);
			}
		} finally {
			((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			System.out.println("**__** finally  SupervivenciaEncontradoRNECLineValidator ");
			if (!lstHallazgos.isEmpty()) {
				((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}
	}

	/**
	 * Método encargado de verificar el tipo y número de idenfificación
	 * 
	 * @param arguments,
	 * @param line,      linea que contiene el valor de los argumentos a verificar
	 */
	private void verificarTipoNumeroIdentificacion(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("**__** inicia  verificarTipoNumeroIdentificacion ");

		if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES) != null) {
			String tipoIdentificacionTres = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES)).toUpperCase();
			if (tipoIdentificacionTres.length() != ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG,
						ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
								+ ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG,
					ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG
							+ ArchivoMultipleCampoConstants.REQUERIDO
							+ ArchivoCampoNovedadConstante.REGISTRO_TIPO_3));
		}
		// Numero de identificacion - Registro Tipo 3
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES) != null) {
			String numIdentificacionTres = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES);
			if (numIdentificacionTres.length() > ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG,
						ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
								+ ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG,
					ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG
							+ ArchivoMultipleCampoConstants.REQUERIDO
							+ ArchivoCampoNovedadConstante.REGISTRO_TIPO_3));
		}
	}

	/**
	 * Método encargado de verificar el estado ANI
	 * 
	 * @param arguments,
	 *                   arguments que contiene la los datos a validar del estado
	 *                   ANI
	 * @param line,
	 *                   linea que sera verificada
	 */
	private void verificarEstadoANI(LineArgumentDTO arguments, Map<String, Object> line) {
		if (line.get(ArchivoCampoNovedadConstante.ESTADO_ANI) != null) {
			String estadoANI = ((String) line.get(ArchivoCampoNovedadConstante.ESTADO_ANI)).toUpperCase();
			if (estadoANI.length() > ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES) {
				lstHallazgos.add(
						crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.ESTADO_ANI_MSG,
								ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
										+ ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					ArchivoCampoNovedadConstante.ESTADO_ANI_MSG, ArchivoCampoNovedadConstante.ESTADO_ANI_MSG
							+ ArchivoMultipleCampoConstants.REQUERIDO + ArchivoCampoNovedadConstante.REGISTRO_TIPO_3));
		}
	}

	/**
	 * Método encargado de validar la fecha de defunción de la persona
	 * 
	 * @param arguments,
	 *                   arguments que contiene la los datos a validar de la fecha
	 *                   de defunción
	 * @param line,
	 *                   linea que sera verificada
	 */
	private void validarFechaDefuncion(LineArgumentDTO arguments, Map<String, Object> line) {
		if (line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION) != null) {
			String fechaDefuncion = (String) line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION);
			if (fechaDefuncion.length() > ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.FECHA_DEFUNCION_MSG,
						ArchivoCampoNovedadConstante.FECHA_DEFUNCION_MSG + ArchivoMultipleCampoConstants.REQUERIDO
								+ ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES));
			} else {
				Date fFechaDefuncion = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaDefuncion);
				if (fFechaDefuncion == null) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							ArchivoCampoNovedadConstante.FECHA_DEFUNCION_MSG,
							"La fecha se encuentra en un formato no permitido. Formato: yyyy-MM-dd"));
				}
			}
		}
		/*
		 * Eliminacion validacion fecha por GLPI
		 * else {
		 * lstHallazgos.add(
		 * crearHallazgo(arguments.getLineNumber(),
		 * ArchivoCampoNovedadConstante.FECHA_DEFUNCION_MSG,
		 * ArchivoCampoNovedadConstante.FECHA_DEFUNCION_MSG +
		 * ArchivoMultipleCampoConstants.REQUERIDO
		 * + ArchivoCampoNovedadConstante.REGISTRO_TIPO_3));
		 * }
		 */
	}

	/**
	 * Método encargado de validar si la susodicha persona existe dentro de la base
	 * de datos de genesys
	 * de tipo numero 3
	 */

	private void verificarExistenciaGenesysTipo3(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia verificarExistenciaGenesysTipo3");
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES) != null) {

			String numIdentificacionTres = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES);

			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES)).toUpperCase();
			TipoIdentificacionEnum tipoId = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);

			System.out.println("numIdentificacionTres: " + numIdentificacionTres);
			if (consultarTipoNumeroIdentificacion(numIdentificacionTres, tipoId) == Boolean.FALSE) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
						"El número de identificación no fue encontrado"));
			}
		}
		System.out.println("Finaliza verificarExistenciaGenesysTipo3");

	}

	/**
	 * Método encargado de validar si la persona existe dentro de la base de datos
	 * de genesys
	 * de tipo número 2
	 */

	private void verificarExistenciaGenesysTipo2(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia verificarExistenciaGenesysTipo2");

		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS) != null) {

			String numIdentificacionDos = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS);

			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS)).toUpperCase();
			TipoIdentificacionEnum tipoId = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);

			System.out.println("numIdentificacionDos: " + numIdentificacionDos);
			if (consultarTipoNumeroIdentificacion(numIdentificacionDos, tipoId) == Boolean.FALSE) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
						"El número de identificación no fue encontrado"));
			}
		}
		System.out.println("Finaliza verificarExistenciaGenesysTipo2");

	}

	private void verificarFechasTipo2(LineArgumentDTO arguments, Map<String, Object> line) {
		// fechaExpRegistroCivil
		String fDefuncionStr = null;
		Date fFechaDefuncionDate = null;
		if (line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION) != null
				&& line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION).toString() != "") {
			fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION);
			System.out.println("Fecha tipo 2: " + fDefuncionStr);
			fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
			if (fFechaDefuncionDate == null) {

				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.FECHA_DEFUNCION,
						"El campo de la fecha de defunción es invalido"));
			}
		}
		if (line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL) != null
				&& line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL).toString() != "") {
			fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL);
			System.out.println("Fecha tipo 2: " + fDefuncionStr);
			fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
			if (fFechaDefuncionDate == null) {

				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG,
						"El campo de la fecha de expedición es invalido"));
			}
		}

	}

	private void verificarFechasTipo3(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia verificarFechaTipo3");
		String fDefuncionStr = null;
		Date fFechaDefuncionDate = null;
		if (line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION) != null
				&& line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION).toString() != "") {
			fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION);
			System.out.println("Fecha tipo 3: " + fDefuncionStr);
			fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
			if (fFechaDefuncionDate == null) {

				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.FECHA_DEFUNCION,
						"El campo de la fecha de defunción es invalido"));
			}
		}
		if (line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG) != null
				&& line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG).toString() != "") {
			fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG);
			System.out.println("Fecha tipo 3: " + fDefuncionStr);
			fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
			if (fFechaDefuncionDate == null) {

				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG,
						"El campo de la fecha de expedicin es invalido"));
			}
		}

	}

	private void validarFallecimiento3(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia validarFallecimiento3");
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES) != null
				&& line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES) != null) {
			String numeroIdentificacion = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES);
			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES)).toUpperCase();
			TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
			Boolean isFallecido = validarEstadoDeFallecimientoPersona(numeroIdentificacion, tipoIdentEnum);
			if (isFallecido != null && isFallecido) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
						"La persona no se encuentra viva"));
			}
		}
		System.out.println("Finaliza validarFallecimiento3");

	}

	private void verificarFallecimiento2(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia verificarFallecimiento2");
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS) != null
				&& line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS) != null) {
			String numeroIdentificacion = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS);
			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS)).toUpperCase();
			TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
			Boolean isFallecido = validarEstadoDeFallecimientoPersona(numeroIdentificacion, tipoIdentEnum);
			if (isFallecido != null && isFallecido) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
						"La persona no se encuentra viva"));
			}
		}
		System.out.println("Finaliza verificarFallecimiento2");
	}

	private void varificarRegistro1(LineArgumentDTO arguments, Map<String, Object> line) {
		System.out.println("Inicia varificarCodigoEntidad");
		String valorCodigo = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString()
				.substring(3, 5);

		if (line.get(ArchivoCampoNovedadConstante.CODIGO_ENTIDAD) != null) {
			String codigoCaja = line.get(ArchivoCampoNovedadConstante.CODIGO_ENTIDAD).toString();
			if (codigoCaja.length() != 6) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.CODIGO_ENTIDAD,
						"El codigo de la entidad es inválido"));
				return;
			}
			String valorCodigoCaja = codigoCaja.substring(4, 6);
			if (!("CCF0" + valorCodigoCaja).equals(("CCF0" + valorCodigo))) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.CODIGO_ENTIDAD,
						"El código de la entidad no corresponde al código de la CCF"));
				return;
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					ArchivoCampoNovedadConstante.CODIGO_ENTIDAD,
					"El codigo de la entidad es inválido"));
		}
		if (line.get(ArchivoCampoNovedadConstante.NOMBRE_ARCHIVO_RESPUESTA) != null) {
			String nombreArchivo = line.get(ArchivoCampoNovedadConstante.NOMBRE_ARCHIVO_RESPUESTA).toString();
			// SMCCCF03920221110
			String pat = "SMCCCF0" + valorCodigo + "\\d{8}RNEC";
			boolean match = nombreArchivo.matches(pat);
			if (!match) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						ArchivoCampoNovedadConstante.NOMBRE_ARCHIVO_RESPUESTA,
						"El nombre del archivo de respuesta es inválido"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					ArchivoCampoNovedadConstante.NOMBRE_ARCHIVO_RESPUESTA,
					"El nombre del archivo de respuesta es inválido"));
		}

	}

	/**
	 * Método que crea un hallazgo según la información ingresada
	 * 
	 * @param lineNumber,
	 *                      numero de linea
	 * @param campo,
	 *                      campo al que pertenece el hallazgo
	 * @param errorMessage,
	 *                      mensaje de error
	 * @return retorna el resultado de hallazgo DTO
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		System.out.println("**__** inicia  crearHallazgo ");
		String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(error);
		return hallazgo;
	}

	public Boolean consultarTipoNumeroIdentificacion(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion) {
		ConsultarTipoNumeroIdentificacionYTipo service = new ConsultarTipoNumeroIdentificacionYTipo(
				numeroIdentificacion, tipoIdentificacion);
		service.execute();
		return service.getResult();
	}

	/**
	 * Valida si la persona se encuentra fallecida
	 * return : True si se encuentra fallecido
	 */
	public Boolean validarEstadoDeFallecimientoPersona(String numeroIdentificacion,
			TipoIdentificacionEnum tipoIdentificacion) {
		ConsultarDatosPersona c = new ConsultarDatosPersona(numeroIdentificacion, tipoIdentificacion);
		c.execute();
		PersonaModeloDTO persona = c.getResult();
		if (persona != null) {
			return persona.getFallecido();
		}
		return Boolean.FALSE;

	}

}
