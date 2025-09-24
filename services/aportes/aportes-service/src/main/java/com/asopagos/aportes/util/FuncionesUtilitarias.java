package com.asopagos.aportes.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.DatosConsultaAportesCierreDTO;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesDatosAportesPila;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.Interpolator;

import co.com.heinsohn.lion.common.util.FileUtilities;
import co.com.heinsohn.lion.fileprocessing.util.ConstantsProperties;

/**
 * <b>Descripcion:</b> Clase que contiene funciones utilitarias<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class FuncionesUtilitarias {
	/**
	 * Instancia del logger
	 */
	private static final ILogger logger = LogManager.getLogger(FuncionesUtilitarias.class);

	private FuncionesUtilitarias() {
	}

	/**
	 * Método que crea un hallazgo según la información ingresada
	 * 
	 * @param lineNumber
	 * @param campo
	 * @param errorMessage
	 * @return
	 */
	public static ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo,
			String errorMessage) {
		String firmaMetodo = "FuncionesUtilitarias.crearHallazgo(Long, String, String)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		String error = null;

		if (errorMessage != null) {
			error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
		} else {
			error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_ESTRUCTURA);
			campo = "Error Estructura";
		}

		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(error);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return hallazgo;
	}

	/**
	 * Método que toma lista los id de registros generales, registros
	 * detallados, códigos de municipios en el listado de DTOs de aportes para
	 * registrar en Core
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, List> obtenerListadosRegistros(List<AporteDTO> aportes) {
		String firmaMetodo = "FuncionesUtilitarias.obtenerListadosRegistros(List<AporteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Map<String, List> result = new HashMap<>();
		result.put(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT, new ArrayList<Long>());
		result.put(ConstantesDatosAportesPila.LISTA_ID_REGDET_INPUT, new ArrayList<Long>());
		result.put(ConstantesDatosAportesPila.LISTA_COD_MUNICIPIOS_INPUT, new ArrayList<String>());
		result.put(ConstantesDatosAportesPila.LISTA_COD_OI_INPUT, new ArrayList<Integer>());

		Set<Long> listaIdRegGen = new HashSet<>();
		Set<Long> listaIdRegDet = new HashSet<>();
		Set<String> listaCodMunicipios = new HashSet<>();
		Set<Integer> listaCodOI = new HashSet<>();

		for (AporteDTO aporte : aportes) {
			listaIdRegGen.add(aporte.getAporteGeneral().getIdRegistroGeneral());
			listaIdRegDet.add(aporte.getAporteDetallado().getIdRegistroDetallado());
			if (aporte.getCodigoMunicioAportante() != null) {
				listaCodMunicipios.add(aporte.getCodigoMunicioAportante());
			}
			if (aporte.getCodigoMunicioCotizante() != null) {
				listaCodMunicipios.add(aporte.getCodigoMunicioCotizante());
			}
			if (aporte.getAporteGeneral().getIdOperadorInformacion() != null) {
				listaCodOI.add(aporte.getAporteGeneral().getIdOperadorInformacion().intValue());
			}
		}

		result.get(ConstantesDatosAportesPila.LISTA_ID_REGGEN_INPUT).addAll(listaIdRegGen);
		result.get(ConstantesDatosAportesPila.LISTA_ID_REGDET_INPUT).addAll(listaIdRegDet);
		result.get(ConstantesDatosAportesPila.LISTA_COD_MUNICIPIOS_INPUT).addAll(listaCodMunicipios);
		result.get(ConstantesDatosAportesPila.LISTA_COD_OI_INPUT).addAll(listaCodOI);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * Metodo que prepara en un mapa los tipos de identificación de persona y
	 * los números de ID relacionados en el listado de DTOs de aportes para
	 * registrar en Core
	 */
	public static List<String> construirListadoIdsPersonas(List<AporteDTO> aportes, Integer tipo) {
		String firmaMetodo = "FuncionesUtilitarias.construirMapaIdsPersonas(List<AporteDTO>, Integer)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Set<String> resultBase = new HashSet<>();
		List<String> result = new ArrayList<>();
		TipoIdentificacionEnum tipoId = null;
		String numId = null;

		for (AporteDTO aporte : aportes) {
			// tipo 1, se consulta identificación de aportantes
			if (tipo == 1 && aporte.getPersonaAportante() != null) {
				tipoId = aporte.getPersonaAportante().getTipoIdentificacion();
				numId = aporte.getPersonaAportante().getNumeroIdentificacion();
			} else if (tipo == 1 && aporte.getEmpresaAportante() != null) {
				tipoId = aporte.getEmpresaAportante().getTipoIdentificacion();
				numId = aporte.getEmpresaAportante().getNumeroIdentificacion();
			}
			// tipo 2, se consulta identificación de cotizantes
			else if (tipo == 2 && aporte.getPersonaCotizante() != null) {
				tipoId = aporte.getPersonaCotizante().getTipoIdentificacion();
				numId = aporte.getPersonaCotizante().getNumeroIdentificacion();
			}
			// tipo 3, se consulta identificación de tramitadores
			else if (tipo == 3) {
				tipoId = aporte.getTipoDocTramitador();
				numId = aporte.getIdTramitador();
			}

			if (tipoId != null && numId != null) {
				String concatenado = tipoId.name() + numId;
				resultBase.add(concatenado);
			}
		}

		result.addAll(resultBase);

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * Método encargado de consultar las sucursales incluidas en el listado de
	 * aportes
	 * 
	 * @param aportes
	 * @return
	 */
	public static List<String> construirListadoLlavesSucursales(List<AporteDTO> aportes) {
		String firmaMetodo = "FuncionesUtilitarias.construirListadoLlavesSucursales(List<AporteDTO>)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		Set<String> setLlavesSucursales = new HashSet<>();
		List<String> result = new ArrayList<>();

		for (AporteDTO aporte : aportes) {
			if (aporte.getEmpresaAportante() != null && aporte.getSucursalEmpresa() != null
					&& aporte.getSucursalEmpresa().getCodigo() != null
					&& aporte.getSucursalEmpresa().getNombre() != null) {

				String llave = aporte.getEmpresaAportante().getTipoIdentificacion().name()
						+ aporte.getEmpresaAportante().getNumeroIdentificacion()
						+ aporte.getSucursalEmpresa().getCodigo() + aporte.getSucursalEmpresa().getNombre();

				setLlavesSucursales.add(llave);
			}
		}

		result.addAll(setLlavesSucursales);
		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return result;
	}

	/**
	 * Método encargado de la eliminación de las líneas vacías de un archivo
	 * 
	 * @param dataFile
	 *            Flujo de bytes con el contenido del archivo
	 * @return <b>byte[]</b> Flujo de bytes con el contenido del archivo, sin
	 *         líneas vacías
	 */
	public static byte[] limpiarLineasVacias(byte[] dataFile) {
		String firmaMetodo = "FuncionesUtilitarias.limpiarLineasVacias(byte[])";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		byte[] cleanDataFile = dataFile;
		StringBuilder nuevoArchivo = new StringBuilder();

		try {
			String charsetName = FileUtilities.getFileEncoding(cleanDataFile);
			if (charsetName == null) {
				charsetName = ConstantsProperties.DEFAULT_ISO;
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(cleanDataFile), charsetName));
			String linea = null;

			while ((linea = reader.readLine()) != null) {
				if (!linea.isEmpty() && !linea.trim().equalsIgnoreCase("")) {
					nuevoArchivo.append(linea).append(System.getProperty("line.separator"));
				}
			}
		} catch (IOException e) {
			logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}

		cleanDataFile = nuevoArchivo.toString().getBytes();

		logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
		return cleanDataFile;
	}

	/**
	 * Método encargado de construir el DTO de criterios para la consulta de
	 * registros de aportes
	 * 
	 * @param modo
	 * @return
	 */
	public static DatosConsultaAportesCierreDTO construirCriteriosConsultaCierre(Integer modo, Date fechaInicio,
			Date fechaFin) {
		String firmaMetodo = "FuncionesUtilitarias.construirCriteriosConsultaCierre(Integer)";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

		DatosConsultaAportesCierreDTO result = null;

		List<String> tiposMovimiento = new ArrayList<>();
		List<String> formasReconocimiento = new ArrayList<>();
		List<String> estadosRegistro = new ArrayList<>();

		switch (modo) {
			case 1: // Renglón aportes (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.REGISTRADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.FALSE, Boolean.FALSE);
				break;
			case 2: // Renglón devoluciones (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
				formasReconocimiento.add("");
	
				estadosRegistro.add(EstadoRegistroAporteEnum.REGISTRADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;
			case 3: // Renglón legalizados (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.REGISTRADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.TRUE);
				break;
			case 4: // Renglón otros ingresos (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.OTROS_INGRESOS.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.TRUE);
				break;			
			case 5: // Renglón correcciones (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.REGISTRADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.TRUE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;
			case 6: // Renglón aportes (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
	
				formasReconocimiento.add("");
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());
	
				estadosRegistro.add("");
				estadosRegistro.add(EstadoRegistroAporteEnum.RELACIONADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.FALSE, Boolean.FALSE);
				break;
			case 7: // Renglón devoluciones (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.DEVOLUCION_APORTES.name());
	
				formasReconocimiento.add("");
	
				estadosRegistro.add(EstadoRegistroAporteEnum.RELACIONADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;
			case 8: // Renglón legalizados (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.RELACIONADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.TRUE, new BigDecimal(-1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.TRUE);
				break;
			case 9: // Renglón otros ingresos (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_PILA_AUTOMATICO.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL_APORTES.name());
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_MANUAL.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.OTROS_INGRESOS.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.FALSE, Boolean.TRUE, new BigDecimal(-1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.TRUE);
				break;
			case 10: // Renglón correcciones (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.RECAUDO_MANUAL.name());
	
				formasReconocimiento.add("");
	
				estadosRegistro.add(EstadoRegistroAporteEnum.RELACIONADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.TRUE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;
			case 11: // Renglón correcciones a la alta (Registrados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
				
	
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_AUTOMATICO_OPORTUNO.name());
				formasReconocimiento.add(FormaReconocimientoAporteEnum.RECONOCIMIENTO_RETROACTIVO_AUTOMATICO.name());
	
				estadosRegistro.add(EstadoRegistroAporteEnum.REGISTRADO.name());
				
				result = new DatosConsultaAportesCierreDTO(Boolean.TRUE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;

				case 12: // Renglón correcciones a la alta (Relacionados)
				tiposMovimiento.add(TipoMovimientoRecaudoAporteEnum.CORRECCION_APORTES.name());
	
				formasReconocimiento.add("");
	
				estadosRegistro.add(EstadoRegistroAporteEnum.RELACIONADO.name());
	
				result = new DatosConsultaAportesCierreDTO(Boolean.TRUE, Boolean.FALSE, new BigDecimal(1), fechaInicio,
						fechaFin, tiposMovimiento, formasReconocimiento, estadosRegistro, Boolean.TRUE, Boolean.FALSE);
				break;
			}
	
			logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
			return result;
	}
	
	/**
     * Metodo que toma una fecha en milisegundos y la convierte a String
     * 
     * @param fecha
     *            Fecha a convertir
     * @param completa
     *            Indica que se desea fecha completa
     * @return <b>String</b> Fecha convertida
     */
    public static String formatoFechaMilis(Long fecha, Boolean completa) {
        logger.debug("Inicia formatoFechaMilis(Long)");

        String result = null;
        DateTimeFormatter formatter = null;
        
        if(!completa){
            LocalDate fechaLD = Instant.ofEpochMilli(fecha).atZone(ZoneId.systemDefault()).toLocalDate();

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            result = formatter.format(fechaLD);
        }else{
            LocalDateTime fechaLD = Instant.ofEpochMilli(fecha).atZone(ZoneId.systemDefault()).toLocalDateTime();

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

            result = formatter.format(fechaLD);
        }

        logger.debug("Finaliza formatoFechaMilis(Long)");
        return result;
    }
    
    /**
     * Método para obtener una fecha en millisegundos a partir de un String
     * @param fechaCadena
     * @return <b>Long</b>
     */
    public static Long obtenerFechaMillis(String fechaCadena) {
        String firmaMetodo = "FuncionesUtilitarias.obtenerFechaMillis(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            int anio = Integer.parseInt(fechaCadena.split("-")[0]);
            int mes = Integer.parseInt(fechaCadena.split("-")[1]);
            int dia = Integer.parseInt(fechaCadena.split("-")[2]);

            LocalDate fecha = LocalDate.of(anio, mes, dia);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return fecha.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
}
