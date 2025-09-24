package com.asopagos.afiliaciones.personas.web.load.validator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.asopagos.afiliacion.personas.web.constants.CamposArchivoConstantes;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.afiliaciones.personas.web.clients.ValidacionesArchivoAfiliaciones;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class TrabajadorDependienteLineValidator extends LineValidator {

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(TrabajadorDependienteLineValidator.class);
	/**
	 * Constante que contiene el valor de la fecha en el año 1952
	 */
	private static final int MIL_NOVECIENTOS_CINCUENTA_DOS = 1952;
	/**
	 * Constante que contiene el valor de la fecha en el año 1900
	 */
	private static final int MIL_NOVECIENTOS = 1900;

	/**
	 * indica si el correo electronico es invalido
	 */
	private Boolean errorCorreoElectronico = false;
	/**
	 * Calendar utilizado para la fecha de nacimiento
	 */
	private Date fechaNacimiento = null;
	/**
	 * 
	 */
	private Calendar cNacimiento = Calendar.getInstance();
	/**
	 * 
	 */
	private Calendar cNacimientoMilNovecientos = Calendar.getInstance();
	/**
	 * Calendar
	 */
	private Calendar cInicioLabores = Calendar.getInstance();
	/**
	 * Inicializacion de la lista de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	/**
	 * Método constructor de la clase
	 */
	public TrabajadorDependienteLineValidator() {
		// Se asigna la fecha del año establecido
		cNacimiento.set(Calendar.YEAR, MIL_NOVECIENTOS_CINCUENTA_DOS);
		cNacimiento.set(Calendar.MONTH, 1);
		cNacimiento.set(Calendar.DAY_OF_YEAR, 1);
		// Se asigna la fecha del año de nacimiento minima
		cNacimientoMilNovecientos.set(Calendar.YEAR, MIL_NOVECIENTOS);
		cNacimientoMilNovecientos.set(Calendar.MONTH, 1);
		cNacimientoMilNovecientos.set(Calendar.DAY_OF_YEAR, 1);

		cInicioLabores.set(Calendar.YEAR, 1965);
		cInicioLabores.set(Calendar.MONTH, 1);
		cInicioLabores.set(Calendar.DAY_OF_YEAR, 1);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		/*
		 * Se valida a partir de la segunda linea del cargue
		 */
		logger.info("Inicia validate en TrabajadorDependienteLineValidator");
		if (arguments.getLineNumber() > ArchivoMultipleCampoConstants.LINEA_ENCABEZADO) {
			((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
			logger.info("TrabajadorDependienteLineValidator");
			try {
				lstHallazgos = new ArrayList<>();
				Map<String, Object> line = arguments.getLineValues();
				// Validación de datos de identificación basicos
				validarDatosBasicosIdentificacion(arguments, line);
				Calendar sistema = Calendar.getInstance();
				// se valida fecha de expedición de documento de identidad
				Date fechaExpedicionDoc = validarFechaExpedicionDocumento(arguments, line, sistema);

				// se valida el género
				GeneroEnum genero = null;
				try {
					genero = GetValueUtil.getGeneroDescripcion((String) line.get(CamposArchivoConstantes.GENERO));
					if (genero == null) {
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.GENERO_MSG,
								"Debe ser un genero valido"));
					}
				} catch (Exception e) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.GENERO_MSG,
							"Debe ser un genero valido"));
				}
				// Validacion para la Fecha Nacimiento
				logger.info("");
				Date fNacimiento = verificarFechaNacimiento(arguments, line, fechaExpedicionDoc, sistema);
				if (fNacimiento == null) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_NACIMIENTO_MSG, CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
				}
				// Validacion de los datos de la ubicacion de la persona

				validarUbicacion(arguments, line);
				// Clase de trabajador
				ClaseTrabajadorEnum claseTrabajadorEnum = null;
				try {
					claseTrabajadorEnum = GetValueUtil.getClaseTrabajadorDescripcion(
							((String) line.get(CamposArchivoConstantes.CLASE_TRABAJADOR)));
					if (claseTrabajadorEnum == null) {
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								CamposArchivoConstantes.CLASE_TRABAJADOR_MSG, "Clase trabajador debe de ser valida"));
					}
				} catch (Exception e) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.CLASE_TRABAJADOR_MSG, "Clase trabajador debe de ser valida"));
				}
				// Fecha de inicio labores con el empleador

				verificacionFechaInicioLabEmpleador(arguments, line, fechaNacimiento, sistema);
				// Validación valor salario mensual
				verificarSalarioMensual(arguments);
				// Validación del Estado civil
				verificarEstadoCivil(arguments, line, fechaNacimiento);
				//Validacion orientacion sexual

				varificarCampoOrientacionSexual(arguments,line);
				//Validacion vulnerabilidad
				varificarCampoFactorVulnerabilidad(arguments,line);
				//Validacion Pertenencia Etnica
				varificarCampoPertenenciaEtnica(arguments,line);
				//Validacion pais de residencia

				varificarCamposWS(arguments,line,"1");
				//Validacion OCUPACION 
				varificarCamposWS(arguments,line,"2");
				//Validacion NIVEL ESCOLAR
				varificarCampoNivelEscolaridad(arguments,line);
				//Validacion MUNICIPIO LABOR
				verificarMunicipioLabor(arguments, line);
                                  
			} finally {
				((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
				if (!lstHallazgos.isEmpty()) {
					((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES))
							.add(1L);
				} else {
					((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_VALIDO))
							.add(1L);
				}
				fechaNacimiento = null;
			}
		}
		logger.info("Termina validate en TrabajadorDependienteLineValidator");
	}

	/**
	 * Método encargado de verificar la ubicacion de la persona
	 * 
	 * @param arguments
	 * @param line
	 */
	private void validarUbicacion(LineArgumentDTO arguments, Map<String, Object> line) {
		// Validacion de Cabeza hogar
		validarRegex(arguments, CamposArchivoConstantes.CABEZA_HOGAR,
				ExpresionesRegularesConstants.SI_NO, "Valor perteneciente a cabeza de hogar invalido, debe ser "
						+ ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
				CamposArchivoConstantes.CABEZA_HOGAR_MSG);
		// Se valida el departamento y el municipio
		verificarDepartamentoMunicipio(arguments, line);
		if (line.get(CamposArchivoConstantes.DIRECCION_RESIDENCIA) != null
				&& !line.get(CamposArchivoConstantes.DIRECCION_RESIDENCIA).equals("")) {
			// Validación de la Dirección de residencia
			String direccionResidencia = ((String) line.get(CamposArchivoConstantes.DIRECCION_RESIDENCIA))
					.toUpperCase();
			if (!(direccionResidencia.length() > 6
					&& direccionResidencia.length() <= ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES)) {
				lstHallazgos
						.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.DIRECCION_RESIDENCIA_MSG,
								"La longitud de la dirección de residencia no está entre "
										+ "7" + " y "
										+ ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES + " caracteres."));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.DIRECCION_RESIDENCIA_MSG,
					CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
		}

		// Teléfono fijo
		String telefonoFijo = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.TELEFONO_FIJO);
		validarRegex(arguments, CamposArchivoConstantes.TELEFONO_FIJO, ExpresionesRegularesConstants.TELEFONO_FIJO,
				"Número de teléfono fijo invalido", CamposArchivoConstantes.TELEFONO_FIJO_MSG);
		// Teléfono celular
		String telefonoCelular = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.TELEFONO_CELULAR);
		validarRegex(arguments, CamposArchivoConstantes.TELEFONO_CELULAR,
				ExpresionesRegularesConstants.TELEFONO_CELULAR, "Número de teléfono celular invalido",
				CamposArchivoConstantes.TELEFONO_CELULAR_MSG);
		if ((telefonoFijo == null || telefonoFijo.equals(""))
				&& (telefonoCelular == null || telefonoCelular.equals(""))) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.TELEFONO_CELULAR_MSG,
					"Se debe de llenar mínimo un numero de teléfono fijo o celular"));
		}
		// Correo electronico
		verificarCorreoElectronico(arguments);

		if (arguments.getLineValues().get(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO) != null
				&& !arguments.getLineValues().get(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO).equals("")) {
			String autorizacionEnvioCorreo = (arguments.getLineValues())
					.get(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO).toString().trim();
			validarRegex(arguments, CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO,
					ExpresionesRegularesConstants.SI_NO, "Autorización envio correo electronico, debe ser "
							+ ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
					CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO_MSG);
			if (ArchivoMultipleCampoConstants.SI.equals(autorizacionEnvioCorreo) && errorCorreoElectronico) {
				lstHallazgos.add(
						crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO_MSG,
								"El correo electrónico debe de tener un valor valido"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO_MSG, CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
		}

		// Autoriza utilización de datos personales
		validarRegex(arguments, CamposArchivoConstantes.AUTORIZA_USO_DATOS_PERSONALES,
				ExpresionesRegularesConstants.SI_NO,
				"Valor perteneciente a autorización de utilización datos personales es invalido, debe ser "
						+ ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
				CamposArchivoConstantes.AUTORIZA_USO_DATOS_PERSONALES_MSG);
		// Validacion de Reside en sector rural
		validarRegex(arguments, CamposArchivoConstantes.RESIDE_SECTOR_RURAL,
				ExpresionesRegularesConstants.SI_NO, "Valor perteneciente a reside en sector rural invalido, debe ser "
						+ ArchivoMultipleCampoConstants.SI + " o " + ArchivoMultipleCampoConstants.NO,
				CamposArchivoConstantes.RESIDE_SECTOR_RURAL_MSG);
	}

	/**
	 * Método encargado de validar la fecha de expdición del documento de
	 * identidad
	 * 
	 * @param arguments,
	 *            listado de argumentos a verificar
	 * @param line,
	 *            linea a verificar
	 * @param sistema,
	 *            fecha del sistema
	 * @return retorna la fecha de expdicion de documento
	 */
	private Date validarFechaExpedicionDocumento(LineArgumentDTO arguments, Map<String, Object> line,
			Calendar sistema) {
		Date fechaExpedicionDoc = null;
		if (line.get(CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD) != null
				&& !line.get(CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD).equals("")) {
			String strFechaExpedicionDoc = line.get(CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD).toString();
			if (!strFechaExpedicionDoc.equals("")) {
				try {
					fechaExpedicionDoc = new Date(CalendarUtils.convertirFechaDate(strFechaExpedicionDoc));
					if (!(CalendarUtils.esFechaMayorIgual(fechaExpedicionDoc, cNacimiento.getTime())
							&& CalendarUtils.esFechaMenor(fechaExpedicionDoc, sistema.getTime()))) {
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD_MSG,
								"Rango de fechas no permitido. Digite la fecha en formato día - mes - año. Ejemplo: 02-10-1990"));
					}
				} catch (Exception e) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD_MSG,
							" Digite la en formato día - mes - año. Ejemplo: 02-10-1990"));
				}
			}
		}
		// Mantis 0242798. 1
		/*
		 * else { lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
		 * CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD_MSG,
		 * CamposArchivoConstantes.CAMPO_REQUERIDO_MSG)); }
		 */
		return fechaExpedicionDoc;
	}

	/**
	 * Método encargado de validar los datos de identificación basicos de la
	 * persona
	 * 
	 * @param arguments,
	 *            listado de argumentos
	 * @param line,
	 *            linea a realizar la verificación
	 */
	private void validarDatosBasicosIdentificacion(LineArgumentDTO arguments, Map<String, Object> line) {
		/*
		 * se simula la captura de los datos desde la pantalla para poder
		 * utilizar el mismo servicios en el momento de persistir la información
		 * completa.
		 */
		TipoIdentificacionEnum tipoIdentificacion = null;
		// se valida el tipo de identificación
		try {
			tipoIdentificacion = GetValueUtil
					.getTipoIdentificacionDescripcion((String) line.get(CamposArchivoConstantes.TIPO_IDENTIFICACION));
			if (tipoIdentificacion == null) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						CamposArchivoConstantes.TIPO_IDENTIFICACION_MSG, "Tipo de identificación invalido"));
			}
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.TIPO_IDENTIFICACION_MSG,
					"Tipo de identificación invalido"));
			tipoIdentificacion = null;
		}
		// Se valida el numero de documento
		verificarNumeroDocumento(tipoIdentificacion, arguments);
		// se valida el primer nombre
		validarRegex(arguments, CamposArchivoConstantes.PRIMER_NOMBRE, ExpresionesRegularesConstants.PRIMER_NOMBRE,
				"Solo se permiten 30 caracteres del alfabeto sin espacios", CamposArchivoConstantes.PRIMER_NOMBRE_MSG);
		// se valida el segundo nombre
		validarRegex(arguments, CamposArchivoConstantes.SEGUNDO_NOMBRE, ExpresionesRegularesConstants.SEGUNDO_NOMBRE,
				"Digite el segundo nombre. Si requiere ingresar más nombres, sepárelos mediante espacio. Ejemplo: Andrea Carolina.Hasta 30 carateres del alfabeto",
				CamposArchivoConstantes.SEGUNDO_NOMBRE_MSG);
		// se valida el primer apellido
		validarRegex(arguments, CamposArchivoConstantes.PRIMER_APELLIDO, ExpresionesRegularesConstants.PRIMER_APELLIDO,
				"Solo se permiten 30 caracteres del alfabeto", CamposArchivoConstantes.PRIMER_APELLIDO_MSG);
		// se valida el segundo apellido
		validarRegex(arguments, CamposArchivoConstantes.SEGUNDO_APELLIDO,
				ExpresionesRegularesConstants.SEGUNDO_APELLIDO, "Solo se permiten 30 caracteres del alfabeto",
				CamposArchivoConstantes.SEGUNDO_APELLIDO_MSG);
	}

	/**
	 * Método encargado de verificar el departamento y el municipio
	 * 
	 * @param arguments,
	 *            argumentos a verificar
	 * @param line,
	 *            linea a verificar
	 */
	private void verificarDepartamentoMunicipio(LineArgumentDTO arguments, Map<String, Object> line) {
		Departamento departamento = null;
		List<Departamento> lstDepartamento = null;
		try {
			lstDepartamento = ((List<Departamento>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
			if (line.get(CamposArchivoConstantes.DEPARTAMENTO) != null
					&& !line.get(CamposArchivoConstantes.DEPARTAMENTO).equals("")) {
				String nombreDepartamento = ((String) line.get(CamposArchivoConstantes.DEPARTAMENTO)).toUpperCase();
				departamento = GetValueUtil.getDepartamentoNombre(lstDepartamento, nombreDepartamento);
			} else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.DEPARTAMENTO_MSG,
						CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
			}
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.DEPARTAMENTO_MSG,
					"Debe ser un departamento valido"));
			departamento = null;
		}
		Municipio municipio = null;
		List<Municipio> lstMunicipio = null;
		try {
			lstMunicipio = ((List<Municipio>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO));
			if (line.get(CamposArchivoConstantes.MUNICIPIO) != null
					&& !line.get(CamposArchivoConstantes.MUNICIPIO).equals("")) {
				String nombreMunicipio = ((String) line.get(CamposArchivoConstantes.MUNICIPIO)).toUpperCase();
				if (departamento != null) {
					municipio = GetValueUtil.getMunicipioNombre(lstMunicipio, nombreMunicipio,
							departamento.getIdDepartamento() != null ? departamento.getIdDepartamento().shortValue()
									: null);
				}
			} else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.MUNICIPIO_MSG,
						CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
			}
		} catch (Exception e) {
			municipio = null;
		}
		if (municipio == null) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.MUNICIPIO_MSG,
					" El municipio debe de pertenecer al departamento ingresado"));
		}
	}

	/**
	 * Método quecrea un hallazgo según la información ingresada
	 * 
	 * @param lineNumber
	 * @param campo
	 * @param errorMessage
	 * @return
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(error);
		return hallazgo;
	}

	/**
	 * Validador de campo aplicando una expresión regular.
	 * 
	 * @param arguments
	 * @param campoVal
	 * @param regex
	 * @param mensaje
	 */
	private void validarRegex(LineArgumentDTO arguments, String campoVal, String regex, String mensaje,
			String campoMSG) {
		try {
			ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
			String valorCampo = "";
			boolean campoObligatorio = true;
			boolean campoNombre = false;
			String campoValidar = "";

			if ((arguments.getLineValues()).get(campoVal) == null
					&& (campoVal.equals(CamposArchivoConstantes.TELEFONO_CELULAR)
							|| (campoVal.equals(CamposArchivoConstantes.TELEFONO_FIJO)
									|| (campoVal.equals(CamposArchivoConstantes.CORREO_ELECTRONICO))))) {
				campoObligatorio = false;
			}

			if (campoVal.equals(CamposArchivoConstantes.SEGUNDO_NOMBRE)
					|| campoVal.equals(CamposArchivoConstantes.SEGUNDO_APELLIDO)) {
				if (arguments.getLineValues().get(campoVal) == null) {
					campoObligatorio = false;
				} else {
					campoValidar = ((String) (arguments.getLineValues()).get(campoVal)).trim();
					valorCampo = campoValidar.toLowerCase();
					campoNombre = true;
				}
			}
			if (campoObligatorio) {
				campoValidar = ((String) (arguments.getLineValues()).get(campoVal)).trim();
				if (campoVal.equals(CamposArchivoConstantes.PRIMER_NOMBRE)
						|| campoVal.equals(CamposArchivoConstantes.PRIMER_APELLIDO)) {
					if (campoValidar == null || campoValidar.equals("")) {
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
						return;
					}
					valorCampo = campoValidar.toLowerCase();
					campoNombre = true;
				}
				if (!campoNombre) {
					if (campoVal.equals(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO)
							|| campoVal.equals(CamposArchivoConstantes.AUTORIZA_USO_DATOS_PERSONALES)
							|| campoVal.equals(CamposArchivoConstantes.RESIDE_SECTOR_RURAL)
							|| campoVal.equals(CamposArchivoConstantes.CLASE_TRABAJADOR)
							|| campoVal.equals(CamposArchivoConstantes.ESTADO_CIVIL)
							|| campoVal.equals(CamposArchivoConstantes.CABEZA_HOGAR)) {
						if (campoValidar == null || campoValidar.equals("")) {
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
							return;
						} else {
							valorCampo = campoValidar.toUpperCase();
						}
					} else {
						valorCampo = campoValidar;
					}
				}
				// Se verifica la expresion regular para el correo electronico
				if (campoVal.equals(CamposArchivoConstantes.CORREO_ELECTRONICO)) {
					Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
					Matcher match = patron.matcher(valorCampo);
					if (!match.find()) {
						hallazgo = crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);
					}
				} else {
					if (valorCampo != null && !(valorCampo.matches(regex))) {
						hallazgo = crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);
					}
				}

				if (hallazgo != null) {
					lstHallazgos.add(hallazgo);
				}
			}
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG,
					" Valor perteneciente " + campoMSG + " invalido"));
		}
	}

	/**
	 * Metodo encargado de verificar la fecha de nacimiento respecto al año 1900
	 * 
	 * @param arguments
	 * @param line
	 * @param fechaExpedicionDoc
	 * @return fecha de nacimiento verificada
	 * @throws ParseException
	 */
	private Date verificarFechaNacimiento(LineArgumentDTO arguments, Map<String, Object> line, Date fechaExpedicionDoc,
			Calendar sistema) {
		// Fecha de nacimiento
		try {
			String strFechaNacimiento = line.get(CamposArchivoConstantes.FECHA_NACIMIENTO).toString();
			fechaNacimiento = new Date(CalendarUtils.convertirFechaDate(strFechaNacimiento));
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FECHA_NACIMIENTO_MSG,
					" Digite la " + CamposArchivoConstantes.FECHA_NACIMIENTO_MSG
							+ " en formato día - mes - año. Ejemplo: 02-10-1990"));
		}
		
		/*
		 * Se valida la fecha de nacimiento este entre 1900 y la fecha de
		 * nacimiento
		 */
		if (fechaNacimiento != null) {
		    if (fechaExpedicionDoc != null && (!CalendarUtils.esFechaMenor(fechaNacimiento, fechaExpedicionDoc))) {
	            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FECHA_NACIMIENTO_MSG,
	                    "La fecha de nacimiento no es menor a la fecha de expedición del documento"));
	        }
		    if (!(CalendarUtils.esFechaMayorIgual(fechaNacimiento, cNacimientoMilNovecientos.getTime())
					&& CalendarUtils.esFechaMenorIgual(fechaNacimiento, sistema.getTime()))) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FECHA_NACIMIENTO_MSG,
						"La fecha de nacimiento no está en el rango permitido"));
			} else {
				/*
				 * Se valida que la fecha de nacimiento sea menor a la
				 * diferencia de edad
				 */
				if (CalendarUtils
						.calcularEdadAnos(fechaNacimiento) < ArchivoMultipleCampoConstants.DIFERENCIA_EDAD_15) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_NACIMIENTO_MSG, "La persona no tiene al menos "
									+ ArchivoMultipleCampoConstants.DIFERENCIA_EDAD_15 + " años"));
				}
			}
		}
		return fechaNacimiento;
	}

	/**
	 * Metodo encargado de verificar el correo electronico
	 * 
	 * @param arguments
	 * @param line
	 */
	private void verificarCorreoElectronico(LineArgumentDTO arguments) {
		errorCorreoElectronico = false;
		if ((arguments.getLineValues()).get(CamposArchivoConstantes.CORREO_ELECTRONICO) != null) {
			String correoElectronico = (String) (arguments.getLineValues())
					.get(CamposArchivoConstantes.CORREO_ELECTRONICO);
			if ((correoElectronico.length() < 8)
					|| (correoElectronico.length() > ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES)) {
				lstHallazgos
						.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.CORREO_ELECTRONICO_MSG,
								"La longitud del correo electrónico no está entre "
										+ ArchivoMultipleCampoConstants.LONGITUD_8_CARACTERES + " y "
										+ ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES + " caracteres"));
				errorCorreoElectronico = true;
			} else {
				validarRegex(arguments, CamposArchivoConstantes.CORREO_ELECTRONICO,
						ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL, "Correo electrónico invalido",
						CamposArchivoConstantes.CORREO_ELECTRONICO_MSG);
			}
		} else {
			errorCorreoElectronico = true;
		}
	}

	/**
	 * Metodo encargado de verificar el estado civil
	 * 
	 * @param arguments
	 * @param line,
	 *            linea a realizar la validacion
	 * @param fechaNacimiento,
	 *            fecha de nacimiento
	 */
	private void verificarEstadoCivil(LineArgumentDTO arguments, Map<String, Object> line, Date fechaNacimiento) {
		boolean agregarHallazgo = false;
		EstadoCivilEnum estadoCivil = null;
		String msgError = " Valor perteneciente a estado civil invalido";
		if (line.get(CamposArchivoConstantes.ESTADO_CIVIL) != null
				&& !line.get(CamposArchivoConstantes.ESTADO_CIVIL).equals("")) {
			try {
				estadoCivil = GetValueUtil
						.getEstadoCivilDescripcion((String) line.get(CamposArchivoConstantes.ESTADO_CIVIL));
			} catch (Exception e) {
				agregarHallazgo = true;
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.ESTADO_CIVIL_MSG,
					CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
		}
		try{
			if (estadoCivil != null) {
				if ((estadoCivil.equals(EstadoCivilEnum.CASADO) || estadoCivil.equals(EstadoCivilEnum.UNION_LIBRE)) && CalendarUtils
								.calcularEdadAnos(fechaNacimiento) < ArchivoMultipleCampoConstants.DIFERENCIA_EDAD_14) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.ESTADO_CIVIL_MSG,
							"Persona menor de " + ArchivoMultipleCampoConstants.DIFERENCIA_EDAD_14 + " años"));
				}
			} else {
				agregarHallazgo = true;
			}
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FECHA_NACIMIENTO_MSG,
					" Digite la " + CamposArchivoConstantes.FECHA_NACIMIENTO_MSG
							+ " en formato día - mes - año. Ejemplo: 02-10-1990"));
		}
		if (agregarHallazgo) {
			lstHallazgos
					.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.ESTADO_CIVIL_MSG, msgError));
		}
	}

	/**
	 * Metodo encargado de verifiar el salario mensual
	 * 
	 * @param arguments
	 * @param line
	 */
	private void verificarSalarioMensual(LineArgumentDTO arguments) {
		if (arguments.getLineValues().get(CamposArchivoConstantes.VALOR_SALARIO_MENSUAL) != null
				&& !arguments.getLineValues().get(CamposArchivoConstantes.VALOR_SALARIO_MENSUAL).equals("")) {
			try {
				BigDecimal salarioMensual = new BigDecimal(
						(arguments.getLineValues()).get(CamposArchivoConstantes.VALOR_SALARIO_MENSUAL).toString());
				if (salarioMensual.toString().length() > ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES) {
					lstHallazgos.add(
							crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.VALOR_SALARIO_MENSUAL_MSG,
									"Valor salario mensual debe de tener una longitud menor igual a "
											+ ArchivoMultipleCampoConstants.LONGITUD_9_CARACTERES + " caracteres"));
				} else {
					if (!(salarioMensual.longValue() >= ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO
							&& salarioMensual.longValue() <= ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO)) {
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								CamposArchivoConstantes.VALOR_SALARIO_MENSUAL_MSG,
								"El valor del salario no esta entre "
										+ ArchivoMultipleCampoConstants.VALOR_MINIMO_SALARIO + " y "
										+ ArchivoMultipleCampoConstants.VALOR_MAXIMO_SALARIO));
					}
				}
			} catch (Exception e) {
				lstHallazgos
						.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.VALOR_SALARIO_MENSUAL_MSG,
								" Valor perteneciente " + CamposArchivoConstantes.VALOR_SALARIO_MENSUAL_MSG
										+ " invalido. Únicamente debe contener caracteres numéricos"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.VALOR_SALARIO_MENSUAL_MSG,
					CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
		}
	}

	/**
	 * Metodo encargado de verificar la Fecha inicio de labores con el empleador
	 * 
	 * @param arguments
	 * @param line,
	 *            linea a la cual se le realiza la validacion
	 * @param fechaNacimiento,
	 *            fecha de nacimiento
	 * @throws ParseException
	 */
	private void verificacionFechaInicioLabEmpleador(LineArgumentDTO arguments, Map<String, Object> line,
			Date fechaNacimiento, Calendar sistema) {
		try {
			if (line.get(CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR) != null
					&& !line.get(CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR).equals("")) {
				String strFInicioLabores = line.get(CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR)
						.toString();
				Date fInicioLabores = new Date(CalendarUtils.convertirFechaDate(strFInicioLabores));
				if (!CalendarUtils.esFechaMayor(fInicioLabores, cInicioLabores.getTime())) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
							"La fecha de inicio de labores con empleador no es mayor a 1964."));
				}
				if (!(CalendarUtils.esFechaMayor(fInicioLabores, fechaNacimiento))) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
							"La fecha de inicio de labores con empleador no es mayor a la fecha de nacimiento"));

				}
				if (CalendarUtils.esFechaMayor(fInicioLabores, sistema.getTime())) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
							"La fecha de inicio de labores con empleador es mayor a la fecha actual"));
				}
			} else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
						CamposArchivoConstantes.CAMPO_REQUERIDO_MSG));
			}
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG,
					" Digite la " + CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR_MSG
							+ " en formato día - mes - año. Ejemplo: 02-10-1990"));
		}
	}

	/**
	 * Método encargado de verificar el número de documento por tipo de
	 * identificacion
	 * 
	 * @param tipoIdentificacion
	 * @param arguments
	 */
	public void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments) {
		if (tipoIdentificacion != null) {
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
						"La cédula de ciudadanía no debe tener más de 10 dígitos.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.CEDULA_EXTRANJERIA,
						"La cédula de extranjería debe tener máximo 16 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.PASAPORTE)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.PASAPORTE,
						"El pasaporte debe tener hasta 16 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.CARNE_DIPLOMATICO)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
						"El formato del carné diplomatico es inválido.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.REGISTRO_CIVIL)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.REGISTRO_CIVIL,
						"El formato del registro civil es inválido.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.NIT,
						"El pasaporte debe tener hasta 10 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.SALVOCONDUCTO)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.SALVO_CONDUCTO,
						"El salvoconducto de permanencia debe tener 16 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.PERM_ESP_PERMANENCIA,
						"El permiso especial de permanencia debe tener 15 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_PROT_TEMPORAL)) {
				// se valida el número de identificación
				validarRegex(arguments, CamposArchivoConstantes.NUMERO_IDENTIFICACION,
						ExpresionesRegularesConstants.PERM_PROT_TEMPORAL,
						"El permiso por protección temporal debe tener hasta 8 caracteres.",
						CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG);
				return;
			}

			/*
			 * Por mantis en el cliente número: 0226289 se dejan unicamente
			 * Cédula de ciudadanía, cédula de extrangería o tarjeta de
			 * identidad. if
			 * (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT)) { // se
			 * valida el número de identificación validarRegex(arguments,
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION,
			 * ExpresionesRegularesConstants.NIT,
			 * "El Nit debe terner 9 o 10 dígitos.",
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG); return; }
			 * 
			 * if (tipoIdentificacion.equals(TipoIdentificacionEnum.PASAPORTE))
			 * { // se valida el número de identificación
			 * validarRegex(arguments,
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION,
			 * ExpresionesRegularesConstants.PASAPORTE,
			 * "El pasaporte no puede tener más de 16 caracteres.",
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG); return; } if
			 * (tipoIdentificacion.equals(TipoIdentificacionEnum.REGISTRO_CIVIL)
			 * ) { // se valida el número de identificación
			 * validarRegex(arguments,
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION,
			 * ExpresionesRegularesConstants.REGISTRO_CIVIL,
			 * "El registro civil debe tener 8, 10 u 11 caracteres.",
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG); return; } if
			 * (tipoIdentificacion.equals(TipoIdentificacionEnum.
			 * CARNE_DIPLOMATICO)) { // se valida el número de identificación
			 * validarRegex(arguments,
			 * CamposArchivoConstantes.NUMERO_IDENTIFICACION,
			 * ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
			 * "El carné diplomático debe tener 9 u 11 caracteres."
			 * , CamposArchivoConstantes.NUMERO_IDENTIFICACION_MSG); return; }
			 */
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.NUMERO_IDENTIFICACION,
					"Tipo de identificación invalido para verificar respecto al número de identificación"));

		}
	}

    private void varificarCampoOrientacionSexual(LineArgumentDTO arguments, Map<String, Object> line) {
        Boolean validacionSuperada = false;
        try {
            if (line.get(CamposArchivoConstantes.ORIENTACION_SEXUAL) != null && !line.get(CamposArchivoConstantes.ORIENTACION_SEXUAL).equals("")) {
                String orientacionSexualArch = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.ORIENTACION_SEXUAL);

                //VALIDACION ORIENTACION SEXUAL
                for (OrientacionSexualEnum orientacion : OrientacionSexualEnum.values()) {
                    if (orientacion.getDescripcion().equals(orientacionSexualArch)) {
                        validacionSuperada = true;
                        break;
                    }
                }
                
                if(!validacionSuperada){
                   lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.ORIENTACION_SEXUAL, CamposArchivoConstantes.ORIENTACION_SEXUAL_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.ORIENTACION_SEXUAL, CamposArchivoConstantes.ORIENTACION_SEXUAL_MSG));
        }
    }
    
    private void varificarCampoFactorVulnerabilidad(LineArgumentDTO arguments, Map<String, Object> line) {
        Boolean validacionSuperada = false;
        try {
            if (line.get(CamposArchivoConstantes.FACTOR_VULNERABILIDAD) != null && !line.get(CamposArchivoConstantes.FACTOR_VULNERABILIDAD).equals("")) {
                String factorVulnerabilidadArch = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.FACTOR_VULNERABILIDAD);
                //VALIDACION FACTOR VULNERABILIDAD 
                for (FactorVulnerabilidadEnum factorVulnerabilidad : FactorVulnerabilidadEnum.values()) {
                    if (factorVulnerabilidad.getDescripcion().equals(factorVulnerabilidadArch)) {
                        validacionSuperada = true;
                        break;
                    }
                }
                
                if(!validacionSuperada){
                   lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FACTOR_VULNERABILIDAD, CamposArchivoConstantes.FACTOR_VULNERABILIDAD_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.FACTOR_VULNERABILIDAD, CamposArchivoConstantes.FACTOR_VULNERABILIDAD_MSG));
        }
    }
    
    private void varificarCampoPertenenciaEtnica(LineArgumentDTO arguments, Map<String, Object> line) {
        Boolean validacionSuperada = false;
        try {
            if (line.get(CamposArchivoConstantes.PERTENENCIA_ETNICA) != null && !line.get(CamposArchivoConstantes.PERTENENCIA_ETNICA).equals("")) {
                String pertenenciaEtnicaArch = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.PERTENENCIA_ETNICA);

                //VALIDACION ETNIA 
                for (PertenenciaEtnicaEnum etnia : PertenenciaEtnicaEnum.values()) {
                    if (etnia.getDescripcion().equals(pertenenciaEtnicaArch)) {
                         validacionSuperada = true;
                         break;
                    }
                }
                
                if(!validacionSuperada){
                   lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.PERTENENCIA_ETNICA, CamposArchivoConstantes.PERTENENCIA_ETNICA_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.PERTENENCIA_ETNICA, CamposArchivoConstantes.PERTENENCIA_ETNICA_MSG));
        }
    }
    
    private void varificarCampoNivelEscolaridad(LineArgumentDTO arguments, Map<String, Object> line) {
        Boolean validacionSuperada = false;
        try {
            if (line.get(CamposArchivoConstantes.NIVEL_ESCOLARIDAD) != null && !line.get(CamposArchivoConstantes.NIVEL_ESCOLARIDAD).equals("")) {
                String nivelEscolaridad = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.NIVEL_ESCOLARIDAD);

                //VALIDACION NIVEL EDUCATIVO 
                for (NivelEducativoEnum educacion : NivelEducativoEnum.values()) {
                    if (educacion.getDescripcion().equals(nivelEscolaridad)) {
                        validacionSuperada = true;
                        break;
                    }
                }
        
                if(!validacionSuperada){
                   lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.NIVEL_ESCOLARIDAD, CamposArchivoConstantes.NIVEL_ESCOLARIDAD_MSG));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.NIVEL_ESCOLARIDAD, CamposArchivoConstantes.NIVEL_ESCOLARIDAD_MSG));
        }
    }
    
    private void verificarMunicipioLabor(LineArgumentDTO arguments, Map<String, Object> line) {
		List<Municipio> lstMunicipio = null;
                Boolean validacionSuperada = false;
		try {
			lstMunicipio = ((List<Municipio>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO));
                        
			if (line.get(CamposArchivoConstantes.MUNICIPIO_LABOR) != null && !line.get(CamposArchivoConstantes.MUNICIPIO_LABOR).equals("")) {
				String nombreMunicipio = ((String) line.get(CamposArchivoConstantes.MUNICIPIO_LABOR)).toUpperCase();
				
                                for (Municipio municipio : lstMunicipio) {
                                    if (municipio.getNombre().equals(nombreMunicipio)) {
                                           validacionSuperada = true; 
                                           break;
                                    }
                                
                                }
                                
                                if(!validacionSuperada){
                                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.MUNICIPIO_LABOR, CamposArchivoConstantes.MUNICIPIO_LABOR_MSG));
                                }
			} 
		} catch (Exception e) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.MUNICIPIO_LABOR, CamposArchivoConstantes.MUNICIPIO_LABOR_MSG));
		}
	}
    
    //se valida estos campos mediante un Servicio Web 
    private void varificarCamposWS(LineArgumentDTO arguments, Map<String, Object> line, String idValidacion) {
        RespuestaValidacionArchivoDTO aplicarValidacion = new RespuestaValidacionArchivoDTO();
        String valorCampo = "";
        
        
        if (idValidacion.equals("1")) {
            valorCampo = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.PAIS_RESIDENCIA) == null ? "" : (String) (arguments.getLineValues()).get(CamposArchivoConstantes.PAIS_RESIDENCIA);
        } else if (idValidacion.equals("2")) {
            valorCampo = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.OCUPACION) == null ? "" : (String) (arguments.getLineValues()).get(CamposArchivoConstantes.OCUPACION);
        } else if (idValidacion.equals("3")) {
            valorCampo = (String) (arguments.getLineValues()).get(CamposArchivoConstantes.MUNICIPIO_LABOR) == null ? "" : (String) (arguments.getLineValues()).get(CamposArchivoConstantes.MUNICIPIO_LABOR);
        }
        
        if(!valorCampo.equals("")){
            ValidacionesArchivoAfiliaciones validacionesArchAfiliacion = new ValidacionesArchivoAfiliaciones(idValidacion, valorCampo);
            validacionesArchAfiliacion.execute();
            aplicarValidacion = (RespuestaValidacionArchivoDTO) validacionesArchAfiliacion.getResult();

                if (aplicarValidacion.getStatus().equals("KO")) {
                    if (idValidacion.equals("1")) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.PAIS_RESIDENCIA, CamposArchivoConstantes.PAIS_RESIDENCIA_MSG));
                    } else if (idValidacion.equals("2")) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.OCUPACION, CamposArchivoConstantes.OCUPACION_MSG));
                    } else if (idValidacion.equals("3")) {
                        lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstantes.MUNICIPIO_LABOR, CamposArchivoConstantes.MUNICIPIO_LABOR_MSG));
                    }
                }
            }   
        }
    
    

}