package com.asopagos.zenith.ejb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.ConfirmacionProcesoArchivoZenithDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.ConexionServidorFTPUtil;
import com.asopagos.util.DesEncrypter;
import com.asopagos.zenith.clients.ConfirmarProcesamientoArchivo;
import com.asopagos.zenith.constants.NamedQueriesConstants;
import com.asopagos.zenith.dto.DatosAfiliadoProcesoDTO;
import com.asopagos.zenith.dto.DatosPostulanteDTO;
import com.asopagos.zenith.dto.DatosSubsidioDTO;
import com.asopagos.zenith.dto.MiembroGrupoPostulanteDTO;
import com.asopagos.zenith.dto.RegistroControlDTO;
import com.asopagos.zenith.dto.RegistroDetalleAporteDTO;
import com.asopagos.zenith.dto.RegistroDetalleDTO;
import com.asopagos.zenith.enums.ErrorProcesamientoArchivoEnum;
import com.asopagos.zenith.enums.RespuestaConfirmacionEnum;
import com.asopagos.zenith.service.ZenithService;

import co.com.heinsohn.lion.common.enums.Protocolo;
import co.com.heinsohn.lion.common.util.FileManagerUtil;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject; 

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados con 
 * la integración Zenith-Genesys <br/>
 *
 * @author Steven Quintero <squintero@heinsohn.com.co>
 */
@Stateless
public class ZenithBusiness implements ZenithService {
	
	/**
	 * Referencia a la unidad de persistencia
	 */
	@PersistenceContext(unitName = "zenith_PU")
	private EntityManager entityManager;

	private static final ILogger logger = LogManager.getLogger(ZenithBusiness.class);

	
	private static final String REGEX_EXTENCION = "\\..*";
	
	private static final String EXTENCION_TXT = ".txt";
	
	private static final String FORMATO_FECHA = "yyyy-MM-dd";
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.zenith.service.ZenithService#generarArchivoAfiliadosZenith(
	 * java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void generarArchivoAfiliadosZenith(String nombreArchivo, String rutaUbicacion, Date fechaDisponible) {

		String firma = "generarArchivoAfiliadosZenith(String, String, Date)";
		logger.info("Inicia Servicio: " + firma);
		
		String rutaSalidaSinErrores = CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_URL_ARCHIVOS_SALIDA).toString();
		String rutaSalidaConErrores = CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_URL_ARCHIVOS_ERRORES).toString();
		
		// se crea la lista que contendrá, de encontrarse, el archivo que se busca
		List<InformacionArchivoDTO> archivoAProcesar = new ArrayList<>();

		// arreglo de bytes que contendrá con la información del archivo
		// consultado en el FTP
		byte[] byteArrayArchivoEntrada = null;

		// valida si el formato del archivo de entrada es el correcto. Por
		// defecto se asume que es el indicado
		boolean formatoArchivoEntradaTxt = true;

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		String codigoCaja = CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString();

		// se mapean los datos de conexión al FTP y se realiza la conexión
		ConexionServidorFTPUtil<InformacionArchivoDTO> conexionFTP = configurarConexionFTP();
		conexionFTP.conectarRecorrerNoDescargar();

		// se recorre el arreglo con los archivos obtenidos del FTP buscando el
		// archivo solicitado
		for (InformacionArchivoDTO informacionArchivoDTO : conexionFTP.getArchivosDescargados()) {

			String nombreArchivoRemotoSinSufijo = informacionArchivoDTO.getFileName();
			if (nombreArchivoRemotoSinSufijo.contains(".")) {
				nombreArchivoRemotoSinSufijo = nombreArchivoRemotoSinSufijo.replaceAll(REGEX_EXTENCION, "");
			}
			
			// si el archivo es encontrado se valida que el formato sea txt
			String nombreArchivoSinSufijo = nombreArchivo.replaceAll(REGEX_EXTENCION, "");
			if (nombreArchivoRemotoSinSufijo.equals(nombreArchivoSinSufijo)) {
				if (!informacionArchivoDTO.getFileType().equals("text/plain")) {
					formatoArchivoEntradaTxt = false;
				}
				// si el formato es el correcto se añade a la lista de archivos
				// a descargar (para este caso solo será este archivo)
				else {
					archivoAProcesar.add(informacionArchivoDTO);
					break;
				}
			}
		}

		// se actualiza la lista de archivos descargados en el utilitario y se
		// procede a descargar el archivo de FTP
		conexionFTP.setArchivosDescargados(archivoAProcesar);
		conexionFTP.conectarYDescargar();

		// se valida si el archivo fue descargado
		for (InformacionArchivoDTO informacionArchivoDTO : conexionFTP.getArchivosDescargados()) {
			byteArrayArchivoEntrada = informacionArchivoDTO.getDataFile();
		}

		// se cierra la conexión
		conexionFTP.cerrarConexionSFTP();

		try {
			// se valida que el formato y el nombre del archivo sean correctos.
			if (validarFormatoYNombreArchivoEntrada(rutaSalidaConErrores, nombreArchivo, byteArrayArchivoEntrada, formatoArchivoEntradaTxt,
					codigoCaja, conexionFTP)) {
				// si no cumple las validaciones se finaliza el servicio.
				logger.info("Finaliza con error Servicio: " + firma);
				
				confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
				return;
			}
			archivo = File.createTempFile(nombreArchivo, EXTENCION_TXT);

			// se mapea la información obtenida del archivo de entrada,
			// contenida en el byte array
			// dentro del archivo en el repositorio local
			OutputStream os = new FileOutputStream(archivo);
			if (byteArrayArchivoEntrada != null) {
				os.write(byteArrayArchivoEntrada);
				os.close();
			}

			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// se lee/recorre el archivo obtenido
			boolean flag = false;
			String linea;
			Integer cantidadRegistrosDetalleReportados = 0;

			List<DatosAfiliadoProcesoDTO> afiliadosAProcesar = new ArrayList<>();

			while ((linea = br.readLine()) != null) {
                //logger.info("LOG LINEA 186: ENTRA AL WHILE :: se imprime linea" + linea);

				if (!flag) {
					String[] datos = linea.split(",");
					// se valida la estructura y contenido del registro control
                    String nombreArchivoSinSufijo = nombreArchivo.replaceAll(REGEX_EXTENCION, "");
					if (datos.length != 6 || Integer.valueOf(datos[0]) != 1 || !codigoCaja.equals(datos[1])
							|| !nombreArchivoSinSufijo.equals(datos[5])) {
						finalizarConError(rutaSalidaConErrores, nombreArchivo+ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_CONTROL.getSufijo()+EXTENCION_TXT,
								byteArrayArchivoEntrada, conexionFTP);
						logger.info("Finaliza con error Servicio: " + firma + ": " + ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_CONTROL.name());
						
						confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
						return;

					} else {
						cantidadRegistrosDetalleReportados = Integer.valueOf(datos[4]);
					}
					flag = true;
				} else {
					String[] datos = linea.split(",");
					// validamos que cada registro detalle tenga 3 elementos,
					// que el primero sea el valor 2 (identificador de detalle),
					// que el segundo sea un dato válido para tipo de identificación
					// y que el último no sea nulo o vacío

					if (datos.length != 3 || Integer.valueOf(datos[0]) != 2
							|| TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(datos[1]) == null
							|| datos[2] == null || datos[2].equals("")) {
						System.out.println("error en contenido");
						//si el registro no tiene 3 datos se toma como error de estructura
						if (datos.length != 3) {
							finalizarConError(rutaSalidaConErrores, nombreArchivo+ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_DETALLE.getSufijo()+EXTENCION_TXT,
									byteArrayArchivoEntrada, conexionFTP);
							logger.info("Finaliza con error Servicio: " + firma + ": " + ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_DETALLE.name());
							confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
							return;
							
						}
						//si el registro tiene campos incorrectos se toma como error de contenido
						else {
							finalizarConError(rutaSalidaConErrores, nombreArchivo+ErrorProcesamientoArchivoEnum.ERROR_CONTENIDO_REGISTRO_DETALLE.getSufijo()+EXTENCION_TXT,
									byteArrayArchivoEntrada, conexionFTP);
							logger.info("Finaliza con error Servicio: " + firma + ": " + ErrorProcesamientoArchivoEnum.ERROR_CONTENIDO_REGISTRO_DETALLE.name());
							confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
							return;
						}
						
					} else {
						DatosAfiliadoProcesoDTO datosAfiliado = new DatosAfiliadoProcesoDTO(
								TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(datos[1]), datos[2]);
						afiliadosAProcesar.add(datosAfiliado);
					}
				}
			}
			// se compara la cantidad reportada de registros detalle en el
			// registro control
			// con la cantidad real obtenida del archivo
            logger.info("Cantidad de registros:  filiadosAProcesar.size()= " + afiliadosAProcesar.size() +
                    " cantidadRegistrosDetalleReportados= " + cantidadRegistrosDetalleReportados);
			if (afiliadosAProcesar.size() != cantidadRegistrosDetalleReportados) {
				finalizarConError(rutaSalidaConErrores, nombreArchivo+ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_CONTROL.getSufijo()+EXTENCION_TXT,
						byteArrayArchivoEntrada, conexionFTP);
				logger.info("Finaliza con error Servicio: " + firma + ": " + ErrorProcesamientoArchivoEnum.ERROR_ESTRUCTURA_REGISTRO_CONTROL.name());
				confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
				return;
			}

			String nombreArchivoSalida = obtenerNombreArchivoSalida(codigoCaja);
			File archivoSalida = File.createTempFile(nombreArchivoSalida, EXTENCION_TXT);
			// Si el archivo no existe es creado
			if (!archivoSalida.exists()) {
				archivoSalida.createNewFile();
			}
			FileWriter fw = new FileWriter(archivoSalida);
			BufferedWriter bw = new BufferedWriter(fw);
			List<RegistroDetalleDTO> listaRegistrosSalida = new ArrayList<>();
			for (DatosAfiliadoProcesoDTO da : afiliadosAProcesar) {
				List<RegistroDetalleDTO> registrosSalida = entityManager
						.createNamedQuery(NamedQueriesConstants.CONSULTAR_INFO_AFILIADOS_ACTIVOS)
						.setParameter("tipoId", da.getTipoId().name())
						.setParameter("numeroId", da.getNumeroId())
						.getResultList();
				if (registrosSalida != null && !registrosSalida.isEmpty()) {
					listaRegistrosSalida.addAll(registrosSalida);
				}
			}

			RegistroControlDTO registroControl = generarRegistroControl(nombreArchivoSalida,
					listaRegistrosSalida.size(), codigoCaja);

			bw.write(registroControl.toString());
			bw.write(listaToString(listaRegistrosSalida).toUpperCase());

			bw.close();

			conexionFTP.setUrlArchivos(rutaSalidaSinErrores);
			FileManagerUtil fos = new FileManagerUtil();
			byte[] bytesArchivoSalida = fos.getBytes(archivoSalida);
			conexionFTP.subirArchivoFTP(nombreArchivoSalida + EXTENCION_TXT, bytesArchivoSalida);
			logger.info("Finaliza Servicio: " + firma);
			confirmarProcesamientoArchivo(nombreArchivoSalida + EXTENCION_TXT, RespuestaConfirmacionEnum.EXITOSO);

		} catch (Exception e) {
			logger.info("Finaliza con error Servicio: " + firma, e);
			confirmarProcesamientoArchivo(nombreArchivo+EXTENCION_TXT, RespuestaConfirmacionEnum.NO_PROCESADO);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		} finally {
			logger.info("finalizando procesos abiertos en: "+firma);
			// cerramos el archivo, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.zenith.service.ZenithService#solicitarDatosPostulanteZenith(
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DatosPostulanteDTO solicitarDatosPostulanteZenith(TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante) {
		String firma = "solicitarDatosPostulanteZenith(TipoIdentificacionEnum, String)";
		logger.debug("inicia el método " + firma);
		try {
			DatosPostulanteDTO datosPostulante = new DatosPostulanteDTO();

			List<MiembroGrupoPostulanteDTO> miembrosGrupo = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_POSTULANTE_Y_GRUPO_FAMILIAR)
					.setParameter("numeroId", numeroIdentificacionCotizante)
					.setParameter("tipoId", tipoIdentificacionCotizante.name()).getResultList();

			// se calcula el periodo base como 36 periodos antes del actual
			String periodoBase = obtenerPeriodoBase();

			List<RegistroDetalleAporteDTO> detallesAportes = entityManager
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_DETALLES_APORTES_MAYORES_A_PERIODO)
					.setParameter("numeroId", numeroIdentificacionCotizante)
					.setParameter("tipoId", tipoIdentificacionCotizante.name()).setParameter("periodoBase", periodoBase)
					.getResultList();

			if (miembrosGrupo != null && !miembrosGrupo.isEmpty()) {
				for (MiembroGrupoPostulanteDTO miembro : miembrosGrupo) {
					if (miembro.getFechaRetiroUltimaEmpresa() == null) {
						miembro.setFechaRetiroUltimaEmpresa(""); 
					}
				}
				datosPostulante.setMiembrosGrupo(miembrosGrupo);
			}

			if (detallesAportes != null && !detallesAportes.isEmpty()) {
				Map<String, Integer> aportesPorEmpresa = new HashMap<>();
				boolean tieneDependientes = false;
				boolean tieneIndependientes = false;

				for (RegistroDetalleAporteDTO aporte : detallesAportes) {
					if (aporte.getTipoCotizante() == 1) {
						tieneDependientes = true;
					}else{
						tieneIndependientes = true;
					}
     	       	}
			
				List<RegistroDetalleAporteDTO> aportesValidos = new ArrayList<>();

				Map<String, RegistroDetalleAporteDTO> aportesAgrupados = new HashMap<String, RegistroDetalleAporteDTO>();

				for (RegistroDetalleAporteDTO aporte : detallesAportes) {

					// REGLAS
					// 1 - Se omite el aporte si el valor y la tarifa son cero
					if (aporte.getTarifaAporte() != null && aporte.getValorAporteObligatorio() != null && 
						aporte.getTarifaAporte().compareTo(BigDecimal.ZERO) == 0 && aporte.getValorAporteObligatorio().compareTo(BigDecimal.ZERO) == 0) {
						continue;
					}			
					
					// 2 - Si tipo de cotizante es independiente, se divide en dos
					if (aporte.getTipoCotizante() == 2) {
						Short diasTotales = aporte.getDiasCotizadosPeriodo();
						if (diasTotales != null) {
							int mitad = diasTotales / 2;
							aporte.setDiasCotizadosPeriodo((short) mitad);			
						}
					}
					
					aporte.setTarifaAporte(null);

					// 3 - Agrupamos por periodo y NIT
					String key = aporte.getPeriodoAporte() + "-" + aporte.getnITEmpresaRealizoAporte();

					if (aportesAgrupados.containsKey(key)) {
						RegistroDetalleAporteDTO existenteAporte = aportesAgrupados.get(key);
						// 4 - Si uno de los periodos del aporte llega a ser > 30, dejarlo en maximo en 30
						existenteAporte.setDiasCotizadosPeriodo((short) Math.min((existenteAporte.getDiasCotizadosPeriodo() + aporte.getDiasCotizadosPeriodo()), 30));
						existenteAporte.setValorAporteObligatorio(existenteAporte.getValorAporteObligatorio().add(aporte.getValorAporteObligatorio()));
					} else {
						RegistroDetalleAporteDTO nuevoAporte = new RegistroDetalleAporteDTO();
						nuevoAporte.setTipoIdentificacionCotizante(aporte.getTipoIdentificacionCotizante());
						nuevoAporte.setNumeroIdentificacionCotizante(aporte.getNumeroIdentificacionCotizante());
						nuevoAporte.setPeriodoAporte(aporte.getPeriodoAporte());
						// 4 - Si uno de los periodos del aporte llega a ser > 30, dejarlo en maximo en 30
						nuevoAporte.setDiasCotizadosPeriodo((short) Math.min(aporte.getDiasCotizadosPeriodo(), 30));
						nuevoAporte.setTipoCotizante(aporte.getTipoCotizante());
						nuevoAporte.setCodigoCCFDondeRealizoAporte(aporte.getCodigoCCFDondeRealizoAporte());
						nuevoAporte.setEmpresaDondeRealizoAporte(aporte.getEmpresaDondeRealizoAporte());
						nuevoAporte.setnITEmpresaRealizoAporte(aporte.getnITEmpresaRealizoAporte());
						nuevoAporte.setValorAporteObligatorio(aporte.getValorAporteObligatorio());
						aportesAgrupados.put(key, nuevoAporte);
					}
				}


				// Dejamos unicamente por periodo la empresa con mayor aportes
				/*
				Map<String, RegistroDetalleAporteDTO> aportesPorPeriodo = new HashMap<String, RegistroDetalleAporteDTO>();
				for (RegistroDetalleAporteDTO aporte : aportesAgrupados.values()) {
					String periodoKey = aporte.getPeriodoAporte();
					if (!aportesPorPeriodo.containsKey(periodoKey) || 
						aporte.getValorAporteObligatorio().compareTo(aportesPorPeriodo.get(periodoKey).getValorAporteObligatorio()) > 0) {
						aportesPorPeriodo.put(periodoKey, aporte);
					}
				}*/

				// Ordenamos de mas reciente a antiguo los aportes
				aportesValidos = new ArrayList<RegistroDetalleAporteDTO>(aportesAgrupados.values());
				Collections.sort(aportesValidos, new Comparator<RegistroDetalleAporteDTO>() {
					@Override
					public int compare(RegistroDetalleAporteDTO o1, RegistroDetalleAporteDTO o2) {
						// formato "YYYY-MM"
						return o2.getPeriodoAporte().compareTo(o1.getPeriodoAporte());
					}
				});

				for (RegistroDetalleAporteDTO aporte : aportesValidos) {
					aporte.setValorAporteObligatorio(null);
				}
				
				datosPostulante.setDetallesAportes(aportesValidos);
			}

			logger.debug("finaliza el método " + firma);
			return datosPostulante;

		} catch (Exception e) {
			logger.debug("finaliza con error el método " + firma, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.zenith.service.ZenithService#solicitarDatosSubsidioZenith(
	 * com.asopagos.enumeraciones.personas.TipoIdentificacionEnum,
	 * java.lang.String)
	 */
	@Override
	public List<DatosSubsidioDTO> solicitarDatosSubsidioZenith(TipoIdentificacionEnum tipoIdCotizante,
			String numeroIdCotizante) {
		String firma = "solicitarDatosSubsidioZenith(TipoIdentificacionEnum, String)";
		logger.debug("inicia el método " + firma);
		try {
			List<DatosSubsidioDTO> listaDatosSubsidio = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_SUBSIDIO_POSTULANTE)
					.setParameter("tipoIdentificacion",tipoIdCotizante.name())
					.setParameter("numeroIdentificacion",numeroIdCotizante)
					.getResultList();

			logger.debug("finaliza el método " + firma);
			return listaDatosSubsidio;
		} catch (Exception e) {
			logger.debug("finaliza con error el método " + firma, e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * <b>Descripción:</b> Método encargado de generar el registro de control
	 * para el archivo de salida con los trabajadores activos.
	 * 
	 * @param nombreArchivoSalida,
	 *            nombre generado para el archivo.
	 * 
	 * @param cantidadRegistrosDetalle,
	 *            cantidad de registro detalle generados para el archivo.
	 * 
	 * @param codigoCaja,
	 *            valor asignado para identificación de la caja.
	 * 
	 * @return RegistroControlDTO con el registro control para el archivo.
	 * 
	 * @author squintero
	 */
	private RegistroControlDTO generarRegistroControl(String nombreArchivoSalida, int cantidadRegistrosDetalle,
			String codigoCaja) {

		Date fechaActual = new Date();

		SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
		// se obtiene el periodo el cual es el año-mes actual menos 1 mes
		// y se le da el formato definido arriba
		String formatoFecha = formato.format(fechaActual);

		RegistroControlDTO registroControl = new RegistroControlDTO();
		registroControl.setTipoRegistro(1);
		registroControl.setCodigoCajaCompensacion(codigoCaja);
		registroControl.setFechaInicioCorte(formatoFecha);
		registroControl.setFechaFinCorte(formatoFecha);
		registroControl.setCantidadRegistrosArchivo(cantidadRegistrosDetalle);
		registroControl.setNombreArchivo(nombreArchivoSalida);
		return registroControl;
	}

	public static String listaToString(List<?> lista) {
		String salida = "";
		for (int i = 0; i < lista.size(); i++) {
			salida += lista.get(i);
		}
		return salida;
	}

	/**
	 * <b>Descripción:</b> Genera el nombre para el archivo de salida con el
	 * formato indicado.
	 * 
	 * @param codigoCaja,
	 *            valor asignado para identificación de la caja.
	 * 
	 * @return String con el nombre para el archivo de salida.
	 * 
	 * @author squintero
	 */
	private String obtenerNombreArchivoSalida(String codigoCaja) {
		Date fechaActual = new Date();
		String formatoFecha = new SimpleDateFormat(FORMATO_FECHA).format(fechaActual);
		formatoFecha = formatoFecha.replace("-", "");
		return "CMC" + codigoCaja + formatoFecha;
	}

	/**
	 * <b>Descripción:</b> Obtiene el periodo base para la consulta de los
	 * aportes (periodo base es el mes anterior al actual menos 30 meses más).
	 * 
	 * @return String con el periodo base.
	 * 
	 * @author squintero
	 */
	private String obtenerPeriodoBase() {
		try {
			Date fechaActual = new Date();
			// Formato a utilizar en la fecha dd/MM/yyyy

			Date fechaBase = restarMeses(fechaActual, 37);
			SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
			// se obtiene el periodo el cual es el año-mes actual menos 1 mes
			// y se le da el formato definido arriba
			String formatoFechaBase = formato.format(fechaBase);

			StringBuilder periodoBase = new StringBuilder(formatoFechaBase);

			String base = periodoBase.substring(0, 7);

			return base;
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
	}

	/**
	 * <b>Descripción:</b> Método encargado de restar meses a una fecha.
	 * 
	 * @param fecha,
	 *            fecha enviada.
	 * 
	 * @param meses,
	 *            meses a restar.
	 * 
	 * @return fecha con meses restados.
	 * 
	 * @author squintero
	 */
	private Date restarMeses(Date fecha, Integer meses) {
		if (fecha != null && meses != null) {
			Calendar mes = Calendar.getInstance();
			mes.setTime(fecha);
			mes.add(Calendar.MONTH, (meses * -1));
			return mes.getTime();
		}
		return fecha;
	}

	/**
	 * <b>Descripción:</b> Método encargado de validar el formato del archivo de
	 * entrada y el nombre del mismo.
	 * 
	 * @param nombreArchivo
	 *            nombre del archivo de entrada.
	 * 
	 * @param byteArrayArchivoEntrada
	 *            array de byte con los datos del archivo de entrada.
	 * 
	 * @param formatoArchivoEntradaTxt
	 *            booleano que identifica si el archivo es o no del formato
	 *            indicado.
	 * 
	 * @param codigoCaja
	 *            código asignado a la caja de compensación (constante de
	 *            negocio).
	 * 
	 * @param conexionFTP
	 *            instancia del utilitario que facilita la conexión al FTP.
	 * 
	 * @return boolean true si no cumple con alguna de las validaciones, false
	 *         en caso contrario.
	 * 
	 * @author squintero
	 */
	private boolean validarFormatoYNombreArchivoEntrada(String rutaErrores, String nombreArchivo, byte[] byteArrayArchivoEntrada,
			boolean formatoArchivoEntradaTxt, String codigoCaja,
			ConexionServidorFTPUtil<InformacionArchivoDTO> conexionFTP) {
		if (!formatoArchivoEntradaTxt) {
			conexionFTP.setUrlArchivos(rutaErrores);
			conexionFTP.subirArchivoFTP(
					nombreArchivo + ErrorProcesamientoArchivoEnum.FORMATO_ARCHIVO_NO_PERMITIDO.getSufijo()+EXTENCION_TXT,
					byteArrayArchivoEntrada);

			return true;
		}
		if (nombreArchivo.contains(EXTENCION_TXT)) {
			nombreArchivo = nombreArchivo.replace(EXTENCION_TXT, "");
		}

		if (!nombreArchivo.matches("[CMCCCF]+\\d{10}") || !nombreArchivo.contains(codigoCaja)) {
			conexionFTP.setUrlArchivos(rutaErrores);
			conexionFTP.subirArchivoFTP(nombreArchivo + ErrorProcesamientoArchivoEnum.ERROR_MASCARA_ARCHIVO.getSufijo()+EXTENCION_TXT,
					byteArrayArchivoEntrada);

			return true;
		}

		return false;
	}

	/**
	 * <b>Descripción:</b> Método encargado de mapear los datos y credenciales
	 * de conexión al servidor de FTP.
	 * 
	 * @return ConexionServidorFTPUtil una instancia del utilitario que facilita
	 *         la conexión al FTP.
	 * 
	 * @author squintero
	 */
	private ConexionServidorFTPUtil<InformacionArchivoDTO> configurarConexionFTP() {
		ConexionServidorFTPUtil<InformacionArchivoDTO> conexionFTP = new ConexionServidorFTPUtil<>("ZENITH",
				InformacionArchivoDTO.class);
		
		conexionFTP.setNombreHost(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_NOMBRE_HOST).toString());
		conexionFTP.setPuerto(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_PUERTO).toString());
		conexionFTP.setNombreUsuario(DesEncrypter.getInstance().decrypt(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_NOMBRE_USUARIO).toString()));
		conexionFTP.setContrasena(DesEncrypter.getInstance().decrypt(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_CONTRASENA).toString()));
		conexionFTP.setUrlArchivos(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_URL_ARCHIVOS_ENTRADA).toString());
		conexionFTP.setProtocolo(Protocolo.valueOf(CacheManager.getParametro(ParametrosSistemaConstants.FTP_ZENITH_PROTOCOLO).toString()));
		
		return conexionFTP;
	}
	
	/**
	 * <b>Descripción:</b> Método que sube el archivo de entrada a la carpeta de error renombrado según 
	 * los datos de entrada.
	 * 
	 * @param urlArchivo
	 * 			url de la carpeta de errores en el FTP.
	 * 
	 * @param nombreArchivo
	 * 			nombre del archivo concatenado al sufijo indicado para el error.
	 * 
	 * @param byteArrayArchivo
	 * 			byte array con los datos del archivo erroneo.
	 * 
	 * @param conexionFTP
	 * 			instancia del utilitario que facilita la conexión al FTP
	 * 
	 * @author squintero
	 */
	private void finalizarConError(String urlArchivo, String nombreArchivo, byte[] byteArrayArchivo,
			ConexionServidorFTPUtil<InformacionArchivoDTO> conexionFTP) {
		conexionFTP.setUrlArchivos(urlArchivo);
		conexionFTP.subirArchivoFTP(nombreArchivo, byteArrayArchivo);
	}
	
	/**
	 * Método encargado de invocar el servicio del ESB que informa a zenith sobre el resultado del procesamiento
	 * del archivo
	 * 
	 * @param nombreArchivo
	 * 			es el nombre del archivo procesado.
	 * 
	 * @param respuesta
	 * 			indica el resultado del procesamiento (RespuestaConfirmacionEnum).
	 * 
	 * @author squintero
	 */
	private void confirmarProcesamientoArchivo(String nombreArchivo, RespuestaConfirmacionEnum respuesta) {
		Date fechaActual = new Date();
		String formatoFecha = new SimpleDateFormat(FORMATO_FECHA).format(fechaActual);

		logger.info("Zenith: confirmación de archivo: " + nombreArchivo + " - Respuesta: " + respuesta + " - Fecha: " + formatoFecha);

		if(respuesta != RespuestaConfirmacionEnum.EXITOSO) {
			logger.warn("Respuesta no válida para confirmación de archivo Zenith: " + respuesta);
			return;
		}

		ConfirmacionProcesoArchivoZenithDTO respuestaConfirmacion = new ConfirmacionProcesoArchivoZenithDTO();
		respuestaConfirmacion.setNombreArchivo(nombreArchivo);
		respuestaConfirmacion.setResultado(respuesta == RespuestaConfirmacionEnum.EXITOSO ? "1" : "2");
		respuestaConfirmacion.setObservacion("OK");
		respuestaConfirmacion.setFechaTransacciones(formatoFecha);

		try {
			
			String urlDestino = (String) entityManager
				.createNamedQuery(NamedQueriesConstants.CONSULTAR_PARAMETRO_ZENITH_URL)
				.getSingleResult();
			logger.info("URL destino para confirmación Zenith: " + urlDestino);
			// 1. Obtener el token
			String respuestaTokenJson = obtenerToken(); // ya lo tienes definido
			JSONObject json = new JSONObject(respuestaTokenJson);
			String token = json.getString("token");
			String tokenGenerado = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJUb2tlbkdlbmVyYWRvckFzbyIsInN1YiI6IjEiLCJhdWQiOiIyIiwiZXhwIjoxODEyMDY3MjAwLCJpbmkiOjE3MzgwNzc4NTc0Mjh9.-zXRKW62Z9O8KUCTjzR-XQut-c4EoCRIuvhc7bBGFXI";
			logger.info("Token recibido para Zenith: " + token);

			// 2. Serializar el DTO a JSON
			String requestJson = new ObjectMapper().writeValueAsString(respuestaConfirmacion);

			// 3. Enviar POST manualmente con token en el header
			URL url = new URL(urlDestino);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setDoOutput(true);

			// Escribir datos en el body de la petición
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = requestJson.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			logger.info("Enviando confirmación a Zenith: " + requestJson);

			int status = conn.getResponseCode();
			logger.info("Código de respuesta HTTP Zenith: " + status);

			// Solo leer la respuesta si el código es 2xx (OK)
			if (status >= 200 && status < 300) {
				try (InputStream inputStream = conn.getInputStream(); 
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line.trim());
					}
					logger.info("Respuesta Zenith confirmación: " + response.toString());
				} catch (IOException e) {
					logger.error("Error al leer el flujo de respuesta de Zenith.", e);
				}
			} else {
				logger.warn("Error al confirmar procesamiento Zenith. Código HTTP: " + status);
			}
		} catch (Exception e) {
			logger.error("Error al confirmar procesamiento Zenith: " + e.getMessage(), e);
		}
	}

	public String obtenerToken() throws IOException {
		String jsonInputString = "{"
			+ "\"idCliente\":\"1\","
			+ "\"idAplicativo\":\"2\","
			+ "\"fechaVencimiento\":\"03/05/2027\""
			+ "}";

		String url = "https://zenith.asopagos.com:8444/GeneradorToken/api/token/generar";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		int code = con.getResponseCode();
		logger.info("Código HTTP respuesta token: " + code);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			return response.toString(); // contiene el JSON con el token
		}
	}

}
