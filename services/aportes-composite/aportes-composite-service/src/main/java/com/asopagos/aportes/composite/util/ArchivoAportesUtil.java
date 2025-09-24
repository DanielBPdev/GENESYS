package com.asopagos.aportes.composite.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asopagos.aportes.dto.ArchivoCierreDTO;
import com.asopagos.aportes.dto.DetalleRegistroCotizanteDTO;
import com.asopagos.aportes.dto.DetalleRegistroDTO;
import com.asopagos.aportes.dto.RegistroAporteDTO;
import com.asopagos.aportes.dto.RegistrosArchivoAporteDTO;
import com.asopagos.aportes.dto.ResultadoDetalleRegistroDTO;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import com.asopagos.enumeraciones.aportes.TipoRegistroEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class ArchivoAportesUtil {

	/**
	 * Instancia del gestor de registro de eventos.
	 */
	private static final ILogger logger = LogManager.getLogger(ArchivoAportesUtil.class);


	/**
	 * @param registros
	 * @return
	 */
	public static ArchivoCierreDTO generarReporteCierreRecaudo(RegistrosArchivoAporteDTO registros) {
		// ArchivoCierreDTO
		try {
			logger.debug(
					"Inicio del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			logger.info(
					"Inicio del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");

			XSSFWorkbook libro = new XSSFWorkbook();

			ByteArrayOutputStream archivo = new ByteArrayOutputStream();
			ArchivoCierreDTO archivoIds = new ArchivoCierreDTO();
			StringBuilder idsAportes = new StringBuilder();

			construirHojaCuadroResumen(libro, registros.getResumenCierreRecaudo());
			String ids;

			if (registros.getDetallePorRegistro() != null) {
				for (DetalleRegistroDTO detalleRegistroDTO : registros.getDetallePorRegistro()) {
					if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.APORTES)) {
						construirHojaAportes(libro, detalleRegistroDTO);
						ids = obtenerIdsAportes(detalleRegistroDTO);
						if (ids != null) {
							idsAportes.append(ids);
						}
					} else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.DEVOLUCIONES)) {
						construirHojaDevoluciones(libro, detalleRegistroDTO);
						ids = obtenerIdsAportes(detalleRegistroDTO);
						if (ids != null) {
							idsAportes.append(ids);
						}
					} else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.REGISTRADOS)) {
						construirHojaLegalizados(libro, detalleRegistroDTO);
						ids = obtenerIdsAportes(detalleRegistroDTO);
						if (ids != null) {
							idsAportes.append(ids);
						}
					} else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.CORRECCIONES)) {
						construirHojaCorreccionesAnulados(libro, detalleRegistroDTO);
						ids = obtenerIdsAportes(detalleRegistroDTO);
						if (ids != null) {
							idsAportes.append(ids);
						}
					} else if (detalleRegistroDTO.getTipoRegistro().equals(TipoRegistroEnum.CORRECCIONES_ORIGEN)) {
						construirHojaCorreccionesOrigen(libro, detalleRegistroDTO);
						ids = obtenerIdsAportes(detalleRegistroDTO);
						if (ids != null) {
							idsAportes.append(ids);
						}
					}
				}
			}
			
			// Almacenamos el libro de Excel via ese flujo de datos
			// Com.libro.write(salida);
			// Desc.
			libro.write(archivo);
			// Creamos el flujo de salida de datos, apuntando al archivo donde
			// queremos almacenar el libro de Excel
			// Com.FileOutputStream salida = new FileOutputStream(archivo);
			libro.close();
						
			// Cerramos el libro para concluir operaciones
			//LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",archivo.getAbsolutePath());
			logger.debug(
					"Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			logger.info(
					"Finaliza del método generarReporteCierreRecaudo(List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
					
			archivoIds.setIdsAporteGeneral(idsAportes.toString());
			// Desc.
			archivoIds.setArchivo(archivo.toByteArray());
			// Desc.
			return archivoIds;
			// Com.return new ArchivoCierreDTO();

		} catch (FileNotFoundException ex) {
			logger.error("Finaliza del método generarReporteCierreRecaudo: No se encuentra el archivo", ex);
			logger.info("Finaliza del método generarReporteCierreRecaudo: No se encuentra el archivo", ex);
		} catch (IOException ex) {
			logger.info("Finaliza del método generarReporteCierreRecaudo: Ocurrio un error al generar el archivo excel",ex);
			logger.error("Finaliza del método generarReporteCierreRecaudo: Ocurrio un error al generar el archivo excel",ex);
		}
		return null;
	}

	/**
	 * Hoja donde se puede ver la informcación del cuadro de resumen del cierre
	 * de recaudo de aportes
	 * 
	 * @param libro
	 * @param resumenCierreRecaudo
	 * @return Hoja con la información
	 */
	private static Sheet construirHojaCuadroResumen(XSSFWorkbook libro,
			List<ResumenCierreRecaudoDTO> resumenCierreRecaudo) {
		try {

			logger.debug(
					"Inicia el método construirHojaCuadroResumen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			logger.info(
					"Inicia el método construirHojaCuadroResumen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");

			Sheet pagina = libro.createSheet("Cuadro resumen");
			//HSSFPalette palette = libro.getCustomPalette();

			String[] periodos = { "Periodo Regular", "Periodo Anterior", "Periodo Futuro", "Resumen" };
			String[] tiposSolicitantes = { "Dependientes", "Independientes", "Pensionados", "Subtotal" };
			String[] estadoRegistrado = { "Registrado", "Relacionado" };
			String[] valorAportes = { "Aporte", "Interes", "Total" };

			String[] tiposDeRegistro = { 
				"Aportes", 
				"Devoluciones",
				"Aportes - Devoluciones",
				"Registrados (Legalizados)", 
				"Registrados (Otros Ingresos)",
				"Correcciones", 
				"Subtotal" 
				};

			int indiceFila = 0;
			for (String periodo : periodos) {
				Row fila = pagina.createRow(indiceFila);

				// 0,first row (0-based); 2,last row (0-based) ; 0, first column
				// (0-based); 5 last column (0-based)
				pagina.addMergedRegion(new CellRangeAddress(indiceFila, indiceFila + 2, 0, 2));

				// get the color which most closely matches the color you want
				// to use
				XSSFColor azulPeriodos = new XSSFColor(new Color(0, 98, 242));
				CellStyle style = libro.createCellStyle();
				Cell celda = fila.createCell(0);

				style.setFillForegroundColor(azulPeriodos.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setAlignment(HorizontalAlignment.CENTER);
				celda.setCellStyle(style);
				celda.setCellValue(periodo);

				int indiceColumnaTipoSolicitante = 3;
				int indiceColumnaEstadoRegistro = 0;
				int indiceColumnaValorAportes = 3;
				Row filaDos = pagina.createRow(indiceFila + 1);
				Row filaTres = pagina.createRow(indiceFila + 2);

				for (String tipoSolicitante : tiposSolicitantes) {

					pagina.addMergedRegion(new CellRangeAddress(indiceFila, indiceFila, indiceColumnaTipoSolicitante,
							indiceColumnaTipoSolicitante + 5));
					CellStyle style2 = libro.createCellStyle();
					Cell celda2 = fila.createCell(indiceColumnaTipoSolicitante);
					XSSFColor amarilloSolicitante = new XSSFColor(new Color(255, 242, 204));
					XSSFColor grisSolicitante = new XSSFColor(new Color(180, 190, 230));
					if (tipoSolicitante.equals("Dependientes") || tipoSolicitante.equals("Pensionados")) {
						style2.setFillForegroundColor(amarilloSolicitante.getIndex());
					} else if (tipoSolicitante.equals("Independientes") || tipoSolicitante.equals("Subtotal")) {
						style2.setFillForegroundColor(grisSolicitante.getIndex());
					}
					style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					style2.setAlignment(HorizontalAlignment.CENTER);
					celda2.setCellStyle(style2);
					celda2.setCellValue(tipoSolicitante);

					for (String estado : estadoRegistrado) {
						indiceColumnaEstadoRegistro = indiceColumnaEstadoRegistro + 3;
						pagina.addMergedRegion(new CellRangeAddress(indiceFila + 1, indiceFila + 1,
								indiceColumnaEstadoRegistro, indiceColumnaEstadoRegistro + 2));

						Font fuenteColorRojo = libro.createFont();
						fuenteColorRojo.setColor(HSSFColorPredefined.RED.getIndex());
						CellStyle style3 = libro.createCellStyle();
						style3.setFillForegroundColor(style2.getFillForegroundColor());
						style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						style3.setAlignment(HorizontalAlignment.CENTER);
						style3.setFont(fuenteColorRojo);
						Cell celda3 = filaDos.createCell(indiceColumnaEstadoRegistro);
						celda3.setCellStyle(style3);
						celda3.setCellValue(estado);

						for (String valorAporte : valorAportes) {
							CellStyle style4 = libro.createCellStyle();
							Cell celda4 = filaTres.createCell(indiceColumnaValorAportes);
							style4.setFillForegroundColor(style2.getFillForegroundColor());
							style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							style4.setAlignment(HorizontalAlignment.CENTER);
							celda4.setCellStyle(style4);
							celda4.setCellValue(valorAporte);
							indiceColumnaValorAportes++;
						}
					}
					indiceColumnaTipoSolicitante = indiceColumnaTipoSolicitante + 6;

				}

				int indiceColumnaTipoRegistro = 0;
				int indiceFilaIter = 3;
				Row filaCuatro = pagina.createRow(indiceFila + 3);
				Row filaCinco = pagina.createRow(indiceFila + 4);
				Row filaSeis = pagina.createRow(indiceFila + 5);
				Row filaSiete = pagina.createRow(indiceFila + 6);
				Row filaOcho = pagina.createRow(indiceFila + 7);
				Row filaNueve = pagina.createRow(indiceFila + 8);
				Row filaDiez = pagina.createRow(indiceFila + 9);

				Cell celda1 = null;
				Cell celda2 = null;
				Cell celda3 = null;
				Cell celda4 = null;
				Cell celda5 = null;
				Cell celda6 = null;
				Cell celda7 = null;
				Cell celda8 = null;
				Cell celda9 = null;
				Cell celda10 = null;
				Cell celda11 = null;
				Cell celda12 = null;
				Cell celda13 = null;
				Cell celda14 = null;
				Cell celda15 = null;
				Cell celda16 = null;
				Cell celda17 = null;
				Cell celda18 = null;
				Cell celda19 = null;
				Cell celda20 = null;
				Cell celda21 = null;
				Cell celda22 = null;
				Cell celda23 = null;
				Cell celda24 = null;

				Cell celdaSub1 = null;
				Cell celdaSub2 = null;
				Cell celdaSub3 = null;
				Cell celdaSub4 = null;
				Cell celdaSub5 = null;
				Cell celdaSub6 = null;
				Cell celdaSub7 = null;
				Cell celdaSub8 = null;
				Cell celdaSub9 = null;
				Cell celdaSub10 = null;
				Cell celdaSub11 = null;
				Cell celdaSub12 = null;
				Cell celdaSub13 = null;
				Cell celdaSub14 = null;
				Cell celdaSub15 = null;
				Cell celdaSub16 = null;
				Cell celdaSub17 = null;
				Cell celdaSub18 = null;
				Cell celdaSub19 = null;
				Cell celdaSub20 = null;
				Cell celdaSub21 = null;
				Cell celdaSub22 = null;
				Cell celdaSub23 = null;
				Cell celdaSub24 = null;

				for (String tipoRegistro : tiposDeRegistro) {
					pagina.addMergedRegion(new CellRangeAddress(indiceFila + indiceFilaIter,
							indiceFila + indiceFilaIter, indiceColumnaTipoRegistro, indiceColumnaTipoRegistro + 2));
					CellStyle style5 = libro.createCellStyle();

					if (tipoRegistro.equals("Aportes")) {
						celda5 = filaCuatro.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					} else if (tipoRegistro.equals("Devoluciones")) {
						celda5 = filaCinco.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					} else if (tipoRegistro.equals("Aportes - Devoluciones")) {
						celda5 = filaSeis.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					} else if (tipoRegistro.equals("Registrados (Legalizados)")) {
						celda5 = filaSiete.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					} else if (tipoRegistro.equals("Registrados (Otros ingresos)")) {
                        celda5 = filaOcho.createCell(0);
                        celda5.setCellStyle(style5);
                        celda5.setCellValue(tipoRegistro);
                    } else if (tipoRegistro.equals("Correcciones")) {
						celda5 = filaNueve.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					} else if (tipoRegistro.equals("Subtotal")) {
						celda5 = filaDiez.createCell(0);
						celda5.setCellStyle(style5);
						celda5.setCellValue(tipoRegistro);
					}
					indiceFilaIter++;
				}

				int indiceColumnaAporte = 3;

				for (ResumenCierreRecaudoDTO resumenCierreRecaudoDTO : resumenCierreRecaudo) {

					if (resumenCierreRecaudoDTO.getPeriodo().getDescripcion().equals(periodo)) {

						BigDecimal montoRegistradoDependienteAD = BigDecimal.ZERO;
						BigDecimal interesRegistradoDependienteAD = BigDecimal.ZERO;
						BigDecimal totalRegistradoDependienteAD = BigDecimal.ZERO;
						BigDecimal montoRegistradoInependienteAD = BigDecimal.ZERO;
						BigDecimal interesRegistradoInependienteAD = BigDecimal.ZERO;
						BigDecimal totalRegistradoIndependienteAD = BigDecimal.ZERO;
						BigDecimal montoRegistradoPensionadoAD = BigDecimal.ZERO;
						BigDecimal interesRegistradoPensionadoAD = BigDecimal.ZERO;
						BigDecimal totalRegistradoPensionadoAD = BigDecimal.ZERO;
						BigDecimal montoRelacionadoDependienteAD = BigDecimal.ZERO;
						BigDecimal interesRelacionadoDependienteAD = BigDecimal.ZERO;
						BigDecimal totalRelacionadoDependienteAD = BigDecimal.ZERO;
						BigDecimal montoRelacionadoIndependienteAD = BigDecimal.ZERO;
						BigDecimal interesRelacionadoIndependienteAD = BigDecimal.ZERO;
						BigDecimal totalRelacionadoIndependienteAD = BigDecimal.ZERO;
						BigDecimal montoRelacionadoPensionadoAD = BigDecimal.ZERO;
						BigDecimal interesRelacionadoPensionadoAD = BigDecimal.ZERO;
						BigDecimal totalRelacionadoPensionadoAD = BigDecimal.ZERO;

						BigDecimal montoRegistradoDependienteSub = BigDecimal.ZERO;
						BigDecimal interesRegistradoDependienteSub = BigDecimal.ZERO;
						BigDecimal totalRegistradoDependienteSub = BigDecimal.ZERO;
						BigDecimal montoRegistradoIndependienteSub = BigDecimal.ZERO;
						BigDecimal interesRegistradoIndependienteSub = BigDecimal.ZERO;
						BigDecimal totalRegistradoIndependienteSub = BigDecimal.ZERO;
						BigDecimal montoRegistradoPensionadoSub = BigDecimal.ZERO;
						BigDecimal interesRegistradoPensionadoSub = BigDecimal.ZERO;
						BigDecimal totalRegistradoPensionadoSub = BigDecimal.ZERO;
						BigDecimal montoRelacionadoDependienteSub = BigDecimal.ZERO;
						BigDecimal interesRelacionadoDependienteSub = BigDecimal.ZERO;
						BigDecimal totalRelacionadoDependienteSub = BigDecimal.ZERO;
						BigDecimal montoRelacionadoIndependienteSub = BigDecimal.ZERO;
						BigDecimal interesRelacionadoIndependienteSub = BigDecimal.ZERO;
						BigDecimal totalRelacionadoIndependienteSub = BigDecimal.ZERO;
						BigDecimal montoRelacionadoPensionadoSub = BigDecimal.ZERO;
						BigDecimal interesRelacionadoPensionadoSub = BigDecimal.ZERO;
						BigDecimal totalRelacionadoPensionadoSub = BigDecimal.ZERO;

						BigDecimal montoRegistradoSub = BigDecimal.ZERO;
						BigDecimal interesRegistradoSub = BigDecimal.ZERO;
						BigDecimal totalRegistradoSub = BigDecimal.ZERO;
						BigDecimal montoRelacionadoSub = BigDecimal.ZERO;
						BigDecimal interesRelacionadoSub = BigDecimal.ZERO;
						BigDecimal totalRelacionadoSub = BigDecimal.ZERO;

						int contador = 0;
						for (RegistroAporteDTO registro : resumenCierreRecaudoDTO.getRegistros()) {

							if (registro.getTipoRegistro().getDescripcion().equals("Aportes")) {
								contador++;
								CellStyle style2 = libro.createCellStyle();
								XSSFColor amarilloAporte = new XSSFColor(new Color(255, 255, 0));
								style2.setFillForegroundColor(amarilloAporte.getIndex());
								filaCuatro.setRowNum(indiceFila + 3);
								celda1 = filaCuatro.createCell(indiceColumnaAporte);
								style2.setAlignment(HorizontalAlignment.RIGHT);
								celda1.setCellStyle(style2);
								celda1.setCellValue(registro.getMontoRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoDependienteAD = montoRegistradoDependienteAD
										.add(registro.getMontoRegistradoDependiente());
								montoRegistradoDependienteSub = montoRegistradoDependienteSub
										.add(registro.getMontoRegistradoDependiente());

								celda2 = filaCuatro.createCell(indiceColumnaAporte);
								celda2.setCellStyle(style2);
								celda2.setCellValue(registro.getInteresRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoDependienteAD = interesRegistradoDependienteAD
										.add(registro.getInteresRegistradoDependiente());
								interesRegistradoDependienteSub = interesRegistradoDependienteSub
										.add(registro.getInteresRegistradoDependiente());

								celda3 = filaCuatro.createCell(indiceColumnaAporte);
								celda3.setCellStyle(style2);
								celda3.setCellValue(registro.getTotalRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoDependienteAD = totalRegistradoDependienteAD
										.add(registro.getTotalRegistradoDependiente());
								totalRegistradoDependienteSub = totalRegistradoDependienteSub
										.add(registro.getTotalRegistradoDependiente());

								celda4 = filaCuatro.createCell(indiceColumnaAporte);
								celda4.setCellStyle(style2);
								celda4.setCellValue(registro.getMontoRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
										.add(registro.getMontoRelacionadoDependiente());
								montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
										.add(registro.getMontoRelacionadoDependiente());

								celda5 = filaCuatro.createCell(indiceColumnaAporte);
								celda5.setCellStyle(style2);
								celda5.setCellValue(registro.getInteresRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
										.add(registro.getInteresRelacionadoDependiente());

								celda6 = filaCuatro.createCell(indiceColumnaAporte);
								celda6.setCellStyle(style2);
								celda6.setCellValue(registro.getTotalRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
										.add(registro.getTotalRelacionadoDependiente());
								totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
										.add(registro.getTotalRelacionadoDependiente());

								celda7 = filaCuatro.createCell(indiceColumnaAporte);
								celda7.setCellStyle(style2);
								celda7.setCellValue(registro.getMontoRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoInependienteAD = montoRegistradoInependienteAD
										.add(registro.getMontoRegistradoIndependiente());
								montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
										.add(registro.getMontoRegistradoIndependiente());

								celda8 = filaCuatro.createCell(indiceColumnaAporte);
								celda8.setCellStyle(style2);
								celda8.setCellValue(registro.getInteresRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoInependienteAD = interesRegistradoInependienteAD
										.add(registro.getInteresRegistradoIndependiente());
								interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
										.add(registro.getInteresRegistradoIndependiente());

								celda9 = filaCuatro.createCell(indiceColumnaAporte);
								celda9.setCellStyle(style2);
								celda9.setCellValue(registro.getTotalRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
										.add(registro.getTotalRegistradoIndependiente());
								totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
										.add(registro.getTotalRegistradoIndependiente());

								celda10 = filaCuatro.createCell(indiceColumnaAporte);
								celda10.setCellStyle(style2);
								celda10.setCellValue(registro.getMontoRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
										.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
										.add(registro.getMontoRelacionadoIndependiente());

								celda11 = filaCuatro.createCell(indiceColumnaAporte);
								celda11.setCellStyle(style2);
								celda11.setCellValue(registro.getInteresRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
										.add(registro.getInteresRelacionadoIndependiente());

								celda12 = filaCuatro.createCell(indiceColumnaAporte);
								celda12.setCellStyle(style2);
								celda12.setCellValue(registro.getTotalRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
										.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
										.add(registro.getTotalRelacionadoIndependiente());

								celda13 = filaCuatro.createCell(indiceColumnaAporte);
								celda13.setCellStyle(style2);
								celda13.setCellValue(registro.getMontoRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoPensionadoAD = montoRegistradoPensionadoAD
										.add(registro.getMontoRegistradoPensionado());
								montoRegistradoPensionadoSub = montoRegistradoPensionadoSub
										.add(registro.getMontoRegistradoPensionado());

								celda14 = filaCuatro.createCell(indiceColumnaAporte);
								celda14.setCellStyle(style2);
								celda14.setCellValue(registro.getInteresRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
										.add(registro.getInteresRegistradoPensionado());
								interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
										.add(registro.getInteresRegistradoPensionado());

								celda15 = filaCuatro.createCell(indiceColumnaAporte);
								celda15.setCellStyle(style2);
								celda15.setCellValue(registro.getTotalRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoPensionadoAD = totalRegistradoPensionadoAD
										.add(registro.getTotalRegistradoPensionado());
								totalRegistradoPensionadoSub = totalRegistradoPensionadoSub
										.add(registro.getTotalRegistradoPensionado());

								celda16 = filaCuatro.createCell(indiceColumnaAporte);
								celda16.setCellStyle(style2);
								celda16.setCellValue(registro.getMontoRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD
										.add(registro.getMontoRelacionadoPensionado());
								montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub
										.add(registro.getMontoRelacionadoPensionado());

								celda17 = filaCuatro.createCell(indiceColumnaAporte);
								celda17.setCellStyle(style2);
								celda17.setCellValue(registro.getInteresRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
										.add(registro.getInteresRelacionadoPensionado());
								interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
										.add(registro.getInteresRelacionadoPensionado());

								celda18 = filaCuatro.createCell(indiceColumnaAporte);
								celda18.setCellStyle(style2);
								celda18.setCellValue(registro.getTotalRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD
										.add(registro.getTotalRelacionadoPensionado());
								totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub
										.add(registro.getTotalRelacionadoPensionado());

								BigDecimal montoRegistrado = BigDecimal.ZERO;
								BigDecimal interesRegistrado = BigDecimal.ZERO;
								BigDecimal totalRegistrado = BigDecimal.ZERO;
								BigDecimal montoRelacionado = BigDecimal.ZERO;
								BigDecimal interesRelacionado = BigDecimal.ZERO;
								BigDecimal totalRelacionado = BigDecimal.ZERO;

								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

								celda19 = filaCuatro.createCell(indiceColumnaAporte);
								celda19.setCellStyle(style2);
								celda19.setCellValue(montoRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda20 = filaCuatro.createCell(indiceColumnaAporte);
								celda20.setCellStyle(style2);
								celda20.setCellValue(interesRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda21 = filaCuatro.createCell(indiceColumnaAporte);
								celda21.setCellStyle(style2);
								celda21.setCellValue(totalRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda22 = filaCuatro.createCell(indiceColumnaAporte);
								celda22.setCellStyle(style2);
								celda22.setCellValue(montoRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda23 = filaCuatro.createCell(indiceColumnaAporte);
								celda23.setCellStyle(style2);
								celda23.setCellValue(interesRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda24 = filaCuatro.createCell(indiceColumnaAporte);
								celda24.setCellStyle(style2);
								celda24.setCellValue(totalRelacionado.toString());
								indiceColumnaAporte = 3;

							} else if (registro.getTipoRegistro().getDescripcion().equals("Devoluciones")) {
								contador++;
								CellStyle style2 = libro.createCellStyle();
								filaCinco.setRowNum(indiceFila + 4);
								celda1 = filaCinco.createCell(indiceColumnaAporte);
								style2.setAlignment(HorizontalAlignment.RIGHT);
								celda1.setCellStyle(style2);
								celda1.setCellValue(registro.getMontoRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoDependienteAD = montoRegistradoDependienteAD
										.add(registro.getMontoRegistradoDependiente());
								montoRegistradoDependienteSub = montoRegistradoDependienteSub
										.add(registro.getMontoRegistradoDependiente());

								celda2 = filaCinco.createCell(indiceColumnaAporte);
								celda2.setCellStyle(style2);
								celda2.setCellValue(registro.getInteresRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoDependienteAD = interesRegistradoDependienteAD
										.add(registro.getInteresRegistradoDependiente());
								interesRegistradoDependienteSub = interesRegistradoDependienteSub
										.add(registro.getInteresRegistradoDependiente());

								celda3 = filaCinco.createCell(indiceColumnaAporte);
								celda3.setCellStyle(style2);
								celda3.setCellValue(registro.getTotalRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoDependienteAD = totalRegistradoDependienteAD
										.add(registro.getTotalRegistradoDependiente());
								totalRegistradoDependienteSub = totalRegistradoDependienteSub
										.add(registro.getTotalRegistradoDependiente());

								celda4 = filaCinco.createCell(indiceColumnaAporte);
								celda4.setCellStyle(style2);
								celda4.setCellValue(registro.getMontoRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoDependienteAD = montoRelacionadoDependienteAD
										.add(registro.getMontoRelacionadoDependiente());
								montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
										.add(registro.getMontoRelacionadoDependiente());

								celda5 = filaCinco.createCell(indiceColumnaAporte);
								celda5.setCellStyle(style2);
								celda5.setCellValue(registro.getInteresRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoDependienteAD = interesRelacionadoDependienteAD
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
										.add(registro.getInteresRelacionadoDependiente());

								celda6 = filaCinco.createCell(indiceColumnaAporte);
								celda6.setCellStyle(style2);
								celda6.setCellValue(registro.getTotalRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoDependienteAD = totalRelacionadoDependienteAD
										.add(registro.getTotalRelacionadoDependiente());
								totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
										.add(registro.getTotalRelacionadoDependiente());

								celda7 = filaCinco.createCell(indiceColumnaAporte);
								celda7.setCellStyle(style2);
								celda7.setCellValue(registro.getMontoRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoInependienteAD = montoRegistradoInependienteAD
										.add(registro.getMontoRegistradoIndependiente());
								montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
										.add(registro.getMontoRegistradoIndependiente());

								celda8 = filaCinco.createCell(indiceColumnaAporte);
								celda8.setCellStyle(style2);
								celda8.setCellValue(registro.getInteresRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoInependienteAD = interesRegistradoInependienteAD
										.add(registro.getInteresRegistradoIndependiente());
								interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
										.add(registro.getInteresRegistradoIndependiente());

								celda9 = filaCinco.createCell(indiceColumnaAporte);
								celda9.setCellStyle(style2);
								celda9.setCellValue(registro.getTotalRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoIndependienteAD = totalRegistradoIndependienteAD
										.add(registro.getTotalRegistradoIndependiente());
								totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
										.add(registro.getTotalRegistradoIndependiente());

								celda10 = filaCinco.createCell(indiceColumnaAporte);
								celda10.setCellStyle(style2);
								celda10.setCellValue(registro.getMontoRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoIndependienteAD = montoRelacionadoIndependienteAD
										.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
										.add(registro.getMontoRelacionadoIndependiente());

								celda11 = filaCinco.createCell(indiceColumnaAporte);
								celda11.setCellStyle(style2);
								celda11.setCellValue(registro.getInteresRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoIndependienteAD = interesRelacionadoIndependienteAD
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
										.add(registro.getInteresRelacionadoIndependiente());

								celda12 = filaCinco.createCell(indiceColumnaAporte);
								celda12.setCellStyle(style2);
								celda12.setCellValue(registro.getTotalRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoIndependienteAD = totalRelacionadoIndependienteAD
										.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
										.add(registro.getTotalRelacionadoIndependiente());

								celda13 = filaCinco.createCell(indiceColumnaAporte);
								celda13.setCellStyle(style2);
								celda13.setCellValue(registro.getMontoRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoPensionadoAD = montoRegistradoPensionadoAD
										.add(registro.getMontoRegistradoPensionado());
								montoRegistradoPensionadoSub = montoRegistradoPensionadoSub
										.add(registro.getMontoRegistradoPensionado());

								celda14 = filaCinco.createCell(indiceColumnaAporte);
								celda14.setCellStyle(style2);
								celda14.setCellValue(registro.getInteresRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoPensionadoAD = interesRegistradoPensionadoAD
										.add(registro.getInteresRegistradoPensionado());
								interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
										.add(registro.getInteresRegistradoPensionado());

								celda15 = filaCinco.createCell(indiceColumnaAporte);
								celda15.setCellStyle(style2);
								celda15.setCellValue(registro.getTotalRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoPensionadoAD = totalRegistradoPensionadoAD
										.add(registro.getTotalRegistradoPensionado());
								totalRegistradoPensionadoSub = totalRegistradoPensionadoSub
										.add(registro.getTotalRegistradoPensionado());

								celda16 = filaCinco.createCell(indiceColumnaAporte);
								celda16.setCellStyle(style2);
								celda16.setCellValue(registro.getMontoRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoPensionadoAD = montoRelacionadoPensionadoAD
										.add(registro.getMontoRelacionadoPensionado());
								montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub
										.add(registro.getMontoRelacionadoPensionado());

								celda17 = filaCinco.createCell(indiceColumnaAporte);
								celda17.setCellStyle(style2);
								celda17.setCellValue(registro.getInteresRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoPensionadoAD = interesRelacionadoPensionadoAD
										.add(registro.getInteresRelacionadoPensionado());
								interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
										.add(registro.getInteresRelacionadoPensionado());

								celda18 = filaCinco.createCell(indiceColumnaAporte);
								celda18.setCellStyle(style2);
								celda18.setCellValue(registro.getTotalRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoPensionadoAD = totalRelacionadoPensionadoAD
										.add(registro.getTotalRelacionadoPensionado());
								totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub
										.add(registro.getTotalRelacionadoPensionado());

								BigDecimal montoRegistrado = BigDecimal.ZERO;
								BigDecimal interesRegistrado = BigDecimal.ZERO;
								BigDecimal totalRegistrado = BigDecimal.ZERO;
								BigDecimal montoRelacionado = BigDecimal.ZERO;
								BigDecimal interesRelacionado = BigDecimal.ZERO;
								BigDecimal totalRelacionado = BigDecimal.ZERO;

								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

								celda19 = filaCinco.createCell(indiceColumnaAporte);
								celda19.setCellStyle(style2);
								celda19.setCellValue(montoRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda20 = filaCinco.createCell(indiceColumnaAporte);
								celda20.setCellStyle(style2);
								celda20.setCellValue(interesRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda21 = filaCinco.createCell(indiceColumnaAporte);
								celda21.setCellStyle(style2);
								celda21.setCellValue(totalRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda22 = filaCinco.createCell(indiceColumnaAporte);
								celda22.setCellStyle(style2);
								celda22.setCellValue(montoRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda23 = filaCinco.createCell(indiceColumnaAporte);
								celda23.setCellStyle(style2);
								celda23.setCellValue(interesRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda24 = filaCinco.createCell(indiceColumnaAporte);
								celda24.setCellStyle(style2);
								celda24.setCellValue(totalRelacionado.toString());

								indiceColumnaAporte = 3;
							} else if (registro.getTipoRegistro().getDescripcion()
									.equals("Registrados (Legalizados)")) {
								contador++;
								CellStyle style2 = libro.createCellStyle();
								filaSiete.setRowNum(indiceFila + 6);

								celda1 = filaSiete.createCell(indiceColumnaAporte);
								style2.setAlignment(HorizontalAlignment.RIGHT);
								celda1.setCellStyle(style2);
								celda1.setCellValue(registro.getMontoRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoDependienteSub = montoRegistradoDependienteSub
										.add(registro.getMontoRegistradoDependiente());

								celda2 = filaSiete.createCell(indiceColumnaAporte);
								celda2.setCellStyle(style2);
								celda2.setCellValue(registro.getInteresRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoDependienteSub = interesRegistradoDependienteSub
										.add(registro.getInteresRegistradoDependiente());

								celda3 = filaSiete.createCell(indiceColumnaAporte);
								celda3.setCellStyle(style2);
								celda3.setCellValue(registro.getTotalRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoDependienteSub = totalRegistradoDependienteSub
										.add(registro.getTotalRegistradoDependiente());

								celda4 = filaSiete.createCell(indiceColumnaAporte);
								celda4.setCellStyle(style2);
								celda4.setCellValue(registro.getMontoRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
										.add(registro.getMontoRelacionadoDependiente());

								celda5 = filaSiete.createCell(indiceColumnaAporte);
								celda5.setCellStyle(style2);
								celda5.setCellValue(registro.getInteresRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
										.add(registro.getInteresRelacionadoDependiente());

								celda6 = filaSiete.createCell(indiceColumnaAporte);
								celda6.setCellStyle(style2);
								celda6.setCellValue(registro.getTotalRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
										.add(registro.getTotalRelacionadoDependiente());

								celda7 = filaSiete.createCell(indiceColumnaAporte);
								celda7.setCellStyle(style2);
								celda7.setCellValue(registro.getMontoRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
										.add(registro.getMontoRegistradoIndependiente());

								celda8 = filaSiete.createCell(indiceColumnaAporte);
								celda8.setCellStyle(style2);
								celda8.setCellValue(registro.getInteresRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
										.add(registro.getInteresRegistradoIndependiente());

								celda9 = filaSiete.createCell(indiceColumnaAporte);
								celda9.setCellStyle(style2);
								celda9.setCellValue(registro.getTotalRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
										.add(registro.getTotalRegistradoIndependiente());

								celda10 = filaSiete.createCell(indiceColumnaAporte);
								celda10.setCellStyle(style2);
								celda10.setCellValue(registro.getMontoRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
										.add(registro.getMontoRelacionadoIndependiente());

								celda11 = filaSiete.createCell(indiceColumnaAporte);
								celda11.setCellStyle(style2);
								celda11.setCellValue(registro.getInteresRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
										.add(registro.getInteresRelacionadoIndependiente());

								celda12 = filaSiete.createCell(indiceColumnaAporte);
								celda12.setCellStyle(style2);
								celda12.setCellValue(registro.getTotalRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
										.add(registro.getTotalRelacionadoIndependiente());

								celda13 = filaSiete.createCell(indiceColumnaAporte);
								celda13.setCellStyle(style2);
								celda13.setCellValue(registro.getMontoRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoPensionadoSub = montoRegistradoPensionadoSub
										.add(registro.getMontoRegistradoPensionado());

								celda14 = filaSiete.createCell(indiceColumnaAporte);
								celda14.setCellStyle(style2);
								celda14.setCellValue(registro.getInteresRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
										.add(registro.getInteresRegistradoPensionado());

								celda15 = filaSiete.createCell(indiceColumnaAporte);
								celda15.setCellStyle(style2);
								celda15.setCellValue(registro.getTotalRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoPensionadoSub = totalRegistradoPensionadoSub
										.add(registro.getTotalRegistradoPensionado());

								celda16 = filaSiete.createCell(indiceColumnaAporte);
								celda16.setCellStyle(style2);
								celda16.setCellValue(registro.getMontoRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub
										.add(registro.getMontoRelacionadoPensionado());

								celda17 = filaSiete.createCell(indiceColumnaAporte);
								celda17.setCellStyle(style2);
								celda17.setCellValue(registro.getInteresRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
										.add(registro.getInteresRelacionadoPensionado());

								celda18 = filaSiete.createCell(indiceColumnaAporte);
								celda18.setCellStyle(style2);
								celda18.setCellValue(registro.getTotalRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub
										.add(registro.getTotalRelacionadoPensionado());

								BigDecimal montoRegistrado = BigDecimal.ZERO;
								BigDecimal interesRegistrado = BigDecimal.ZERO;
								BigDecimal totalRegistrado = BigDecimal.ZERO;
								BigDecimal montoRelacionado = BigDecimal.ZERO;
								BigDecimal interesRelacionado = BigDecimal.ZERO;
								BigDecimal totalRelacionado = BigDecimal.ZERO;

								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

								celda19 = filaSiete.createCell(indiceColumnaAporte);
								celda19.setCellStyle(style2);
								celda19.setCellValue(montoRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda20 = filaSiete.createCell(indiceColumnaAporte);
								celda20.setCellStyle(style2);
								celda20.setCellValue(interesRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda21 = filaSiete.createCell(indiceColumnaAporte);
								celda21.setCellStyle(style2);
								celda21.setCellValue(totalRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda22 = filaSiete.createCell(indiceColumnaAporte);
								celda22.setCellStyle(style2);
								celda22.setCellValue(montoRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda23 = filaSiete.createCell(indiceColumnaAporte);
								celda23.setCellStyle(style2);
								celda23.setCellValue(interesRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda24 = filaSiete.createCell(indiceColumnaAporte);
								celda24.setCellStyle(style2);
								celda24.setCellValue(totalRelacionado.toString());

								indiceColumnaAporte = 3;

							} else if (registro.getTipoRegistro().getDescripcion()
                                    .equals("Registrados (Otros ingresos)")) {
                                contador++;
                                CellStyle style2 = libro.createCellStyle();
                                filaOcho.setRowNum(indiceFila + 7);

                                celda1 = filaOcho.createCell(indiceColumnaAporte);
                                style2.setAlignment(HorizontalAlignment.RIGHT);
                                celda1.setCellStyle(style2);
                                celda1.setCellValue(registro.getMontoRegistradoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRegistradoDependienteSub = montoRegistradoDependienteSub
                                        .add(registro.getMontoRegistradoDependiente());

                                celda2 = filaOcho.createCell(indiceColumnaAporte);
                                celda2.setCellStyle(style2);
                                celda2.setCellValue(registro.getInteresRegistradoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRegistradoDependienteSub = interesRegistradoDependienteSub
                                        .add(registro.getInteresRegistradoDependiente());

                                celda3 = filaOcho.createCell(indiceColumnaAporte);
                                celda3.setCellStyle(style2);
                                celda3.setCellValue(registro.getTotalRegistradoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRegistradoDependienteSub = totalRegistradoDependienteSub
                                        .add(registro.getTotalRegistradoDependiente());

                                celda4 = filaOcho.createCell(indiceColumnaAporte);
                                celda4.setCellStyle(style2);
                                celda4.setCellValue(registro.getMontoRelacionadoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
                                        .add(registro.getMontoRelacionadoDependiente());

                                celda5 = filaOcho.createCell(indiceColumnaAporte);
                                celda5.setCellStyle(style2);
                                celda5.setCellValue(registro.getInteresRelacionadoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
                                        .add(registro.getInteresRelacionadoDependiente());

                                celda6 = filaOcho.createCell(indiceColumnaAporte);
                                celda6.setCellStyle(style2);
                                celda6.setCellValue(registro.getTotalRelacionadoDependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
                                        .add(registro.getTotalRelacionadoDependiente());

                                celda7 = filaOcho.createCell(indiceColumnaAporte);
                                celda7.setCellStyle(style2);
                                celda7.setCellValue(registro.getMontoRegistradoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
                                        .add(registro.getMontoRegistradoIndependiente());

                                celda8 = filaOcho.createCell(indiceColumnaAporte);
                                celda8.setCellStyle(style2);
                                celda8.setCellValue(registro.getInteresRegistradoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
                                        .add(registro.getInteresRegistradoIndependiente());

                                celda9 = filaOcho.createCell(indiceColumnaAporte);
                                celda9.setCellStyle(style2);
                                celda9.setCellValue(registro.getTotalRegistradoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
                                        .add(registro.getTotalRegistradoIndependiente());

                                celda10 = filaOcho.createCell(indiceColumnaAporte);
                                celda10.setCellStyle(style2);
                                celda10.setCellValue(registro.getMontoRelacionadoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
                                        .add(registro.getMontoRelacionadoIndependiente());

                                celda11 = filaOcho.createCell(indiceColumnaAporte);
                                celda11.setCellStyle(style2);
                                celda11.setCellValue(registro.getInteresRelacionadoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
                                        .add(registro.getInteresRelacionadoIndependiente());

                                celda12 = filaOcho.createCell(indiceColumnaAporte);
                                celda12.setCellStyle(style2);
                                celda12.setCellValue(registro.getTotalRelacionadoIndependiente().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
                                        .add(registro.getTotalRelacionadoIndependiente());

                                celda13 = filaOcho.createCell(indiceColumnaAporte);
                                celda13.setCellStyle(style2);
                                celda13.setCellValue(registro.getMontoRegistradoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRegistradoPensionadoSub = montoRegistradoPensionadoSub
                                        .add(registro.getMontoRegistradoPensionado());

                                celda14 = filaOcho.createCell(indiceColumnaAporte);
                                celda14.setCellStyle(style2);
                                celda14.setCellValue(registro.getInteresRegistradoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
                                        .add(registro.getInteresRegistradoPensionado());

                                celda15 = filaOcho.createCell(indiceColumnaAporte);
                                celda15.setCellStyle(style2);
                                celda15.setCellValue(registro.getTotalRegistradoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRegistradoPensionadoSub = totalRegistradoPensionadoSub
                                        .add(registro.getTotalRegistradoPensionado());

                                celda16 = filaOcho.createCell(indiceColumnaAporte);
                                celda16.setCellStyle(style2);
                                celda16.setCellValue(registro.getMontoRelacionadoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub
                                        .add(registro.getMontoRelacionadoPensionado());

                                celda17 = filaOcho.createCell(indiceColumnaAporte);
                                celda17.setCellStyle(style2);
                                celda17.setCellValue(registro.getInteresRelacionadoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
                                        .add(registro.getInteresRelacionadoPensionado());

                                celda18 = filaOcho.createCell(indiceColumnaAporte);
                                celda18.setCellStyle(style2);
                                celda18.setCellValue(registro.getTotalRelacionadoPensionado().toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;
                                totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub
                                        .add(registro.getTotalRelacionadoPensionado());

                                BigDecimal montoRegistrado = BigDecimal.ZERO;
                                BigDecimal interesRegistrado = BigDecimal.ZERO;
                                BigDecimal totalRegistrado = BigDecimal.ZERO;
                                BigDecimal montoRelacionado = BigDecimal.ZERO;
                                BigDecimal interesRelacionado = BigDecimal.ZERO;
                                BigDecimal totalRelacionado = BigDecimal.ZERO;

                                montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
                                montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
                                montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
                                montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
                                montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
                                montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
                                interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
                                interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
                                interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
                                interesRelacionado = interesRelacionado
                                        .add(registro.getInteresRelacionadoDependiente());
                                interesRelacionado = interesRelacionado
                                        .add(registro.getInteresRelacionadoIndependiente());
                                interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
                                totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
                                totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
                                totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
                                totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
                                totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
                                totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

                                celda19 = filaOcho.createCell(indiceColumnaAporte);
                                celda19.setCellStyle(style2);
                                celda19.setCellValue(montoRegistrado.toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;

                                celda20 = filaOcho.createCell(indiceColumnaAporte);
                                celda20.setCellStyle(style2);
                                celda20.setCellValue(interesRegistrado.toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;

                                celda21 = filaOcho.createCell(indiceColumnaAporte);
                                celda21.setCellStyle(style2);
                                celda21.setCellValue(totalRegistrado.toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;

                                celda22 = filaOcho.createCell(indiceColumnaAporte);
                                celda22.setCellStyle(style2);
                                celda22.setCellValue(montoRelacionado.toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;

                                celda23 = filaOcho.createCell(indiceColumnaAporte);
                                celda23.setCellStyle(style2);
                                celda23.setCellValue(interesRelacionado.toString());
                                indiceColumnaAporte = indiceColumnaAporte + 1;

                                celda24 = filaOcho.createCell(indiceColumnaAporte);
                                celda24.setCellStyle(style2);
                                celda24.setCellValue(totalRelacionado.toString());

                                indiceColumnaAporte = 3;

                            } else if (registro.getTipoRegistro().getDescripcion().equals("Correcciones")) {
								contador++;
								// Correcciones
								CellStyle style2 = libro.createCellStyle();
								filaNueve.setRowNum(indiceFila + 8);
								celda1 = filaNueve.createCell(indiceColumnaAporte);
								style2.setAlignment(HorizontalAlignment.RIGHT);
								celda1.setCellStyle(style2);
								celda1.setCellValue(registro.getMontoRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoDependienteSub = montoRegistradoDependienteSub
										.add(registro.getMontoRegistradoDependiente());

								celda2 = filaNueve.createCell(indiceColumnaAporte);
								celda2.setCellStyle(style2);
								celda2.setCellValue(registro.getInteresRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoDependienteSub = interesRegistradoDependienteSub
										.add(registro.getInteresRegistradoDependiente());

								celda3 = filaNueve.createCell(indiceColumnaAporte);
								celda3.setCellStyle(style2);
								celda3.setCellValue(registro.getTotalRegistradoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoDependienteSub = totalRegistradoDependienteSub
										.add(registro.getTotalRegistradoDependiente());

								celda4 = filaNueve.createCell(indiceColumnaAporte);
								celda4.setCellStyle(style2);
								celda4.setCellValue(registro.getMontoRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoDependienteSub = montoRelacionadoDependienteSub
										.add(registro.getMontoRelacionadoDependiente());

								celda5 = filaNueve.createCell(indiceColumnaAporte);
								celda5.setCellStyle(style2);
								celda5.setCellValue(registro.getInteresRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoDependienteSub = interesRelacionadoDependienteSub
										.add(registro.getInteresRelacionadoDependiente());

								celda6 = filaNueve.createCell(indiceColumnaAporte);
								celda6.setCellStyle(style2);
								celda6.setCellValue(registro.getTotalRelacionadoDependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoDependienteSub = totalRelacionadoDependienteSub
										.add(registro.getTotalRelacionadoDependiente());

								celda7 = filaNueve.createCell(indiceColumnaAporte);
								celda7.setCellStyle(style2);
								celda7.setCellValue(registro.getMontoRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoIndependienteSub = montoRegistradoIndependienteSub
										.add(registro.getMontoRegistradoIndependiente());

								celda8 = filaNueve.createCell(indiceColumnaAporte);
								celda8.setCellStyle(style2);
								celda8.setCellValue(registro.getInteresRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoIndependienteSub = interesRegistradoIndependienteSub
										.add(registro.getInteresRegistradoIndependiente());

								celda9 = filaNueve.createCell(indiceColumnaAporte);
								celda9.setCellStyle(style2);
								celda9.setCellValue(registro.getTotalRegistradoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoIndependienteSub = totalRegistradoIndependienteSub
										.add(registro.getTotalRegistradoIndependiente());

								celda10 = filaNueve.createCell(indiceColumnaAporte);
								celda10.setCellStyle(style2);
								celda10.setCellValue(registro.getMontoRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoIndependienteSub = montoRelacionadoIndependienteSub
										.add(registro.getMontoRelacionadoIndependiente());

								celda11 = filaNueve.createCell(indiceColumnaAporte);
								celda11.setCellStyle(style2);
								celda11.setCellValue(registro.getInteresRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoIndependienteSub = interesRelacionadoIndependienteSub
										.add(registro.getInteresRelacionadoIndependiente());

								celda12 = filaNueve.createCell(indiceColumnaAporte);
								celda12.setCellStyle(style2);
								celda12.setCellValue(registro.getTotalRelacionadoIndependiente().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoIndependienteSub = totalRelacionadoIndependienteSub
										.add(registro.getTotalRelacionadoIndependiente());

								celda13 = filaNueve.createCell(indiceColumnaAporte);
								celda13.setCellStyle(style2);
								celda13.setCellValue(registro.getMontoRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRegistradoPensionadoSub = montoRegistradoPensionadoSub
										.add(registro.getMontoRegistradoPensionado());

								celda14 = filaNueve.createCell(indiceColumnaAporte);
								celda14.setCellStyle(style2);
								celda14.setCellValue(registro.getInteresRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRegistradoPensionadoSub = interesRegistradoPensionadoSub
										.add(registro.getInteresRegistradoPensionado());

								celda15 = filaNueve.createCell(indiceColumnaAporte);
								celda15.setCellStyle(style2);
								celda15.setCellValue(registro.getTotalRegistradoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRegistradoPensionadoSub = totalRegistradoPensionadoSub
										.add(registro.getTotalRegistradoPensionado());

								celda16 = filaNueve.createCell(indiceColumnaAporte);
								celda16.setCellStyle(style2);
								celda16.setCellValue(registro.getMontoRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								montoRelacionadoPensionadoSub = montoRelacionadoPensionadoSub
										.add(registro.getMontoRelacionadoPensionado());

								celda17 = filaNueve.createCell(indiceColumnaAporte);
								celda17.setCellStyle(style2);
								celda17.setCellValue(registro.getInteresRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								interesRelacionadoPensionadoSub = interesRelacionadoPensionadoSub
										.add(registro.getInteresRelacionadoPensionado());

								celda18 = filaNueve.createCell(indiceColumnaAporte);
								celda18.setCellStyle(style2);
								celda18.setCellValue(registro.getTotalRelacionadoPensionado().toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;
								totalRelacionadoPensionadoSub = totalRelacionadoPensionadoSub
										.add(registro.getTotalRelacionadoPensionado());

								BigDecimal montoRegistrado = BigDecimal.ZERO;
								BigDecimal interesRegistrado = BigDecimal.ZERO;
								BigDecimal totalRegistrado = BigDecimal.ZERO;
								BigDecimal montoRelacionado = BigDecimal.ZERO;
								BigDecimal interesRelacionado = BigDecimal.ZERO;
								BigDecimal totalRelacionado = BigDecimal.ZERO;

								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoDependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoIndependiente());
								montoRegistrado = montoRegistrado.add(registro.getMontoRegistradoPensionado());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoDependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoIndependiente());
								montoRelacionado = montoRelacionado.add(registro.getMontoRelacionadoPensionado());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoDependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoIndependiente());
								interesRegistrado = interesRegistrado.add(registro.getInteresRegistradoPensionado());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoDependiente());
								interesRelacionado = interesRelacionado
										.add(registro.getInteresRelacionadoIndependiente());
								interesRelacionado = interesRelacionado.add(registro.getInteresRelacionadoPensionado());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoDependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoIndependiente());
								totalRegistrado = totalRegistrado.add(registro.getTotalRegistradoPensionado());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoDependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoIndependiente());
								totalRelacionado = totalRelacionado.add(registro.getTotalRelacionadoPensionado());

								celda19 = filaNueve.createCell(indiceColumnaAporte);
								celda19.setCellStyle(style2);
								celda19.setCellValue(montoRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda20 = filaNueve.createCell(indiceColumnaAporte);
								celda20.setCellStyle(style2);
								celda20.setCellValue(interesRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda21 = filaNueve.createCell(indiceColumnaAporte);
								celda21.setCellStyle(style2);
								celda21.setCellValue(totalRegistrado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda22 = filaNueve.createCell(indiceColumnaAporte);
								celda22.setCellStyle(style2);
								celda22.setCellValue(montoRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda23 = filaNueve.createCell(indiceColumnaAporte);
								celda23.setCellStyle(style2);
								celda23.setCellValue(interesRelacionado.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celda24 = filaNueve.createCell(indiceColumnaAporte);
								celda24.setCellStyle(style2);
								celda24.setCellValue(totalRelacionado.toString());

								indiceColumnaAporte = 3;

							}

							if (contador == 5) {
								// "Aportes - Devoluciones" fila seis

								CellStyle style3 = libro.createCellStyle();
								filaSeis.setRowNum(indiceFila + 5);
								Cell celdaAD1 = filaSeis.createCell(indiceColumnaAporte);
								style3.setAlignment(HorizontalAlignment.RIGHT);
								celdaAD1.setCellStyle(style3);
								celdaAD1.setCellValue(montoRegistradoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD2 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD2.setCellStyle(style3);
								celdaAD2.setCellValue(interesRegistradoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD3 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD3.setCellStyle(style3);
								celdaAD3.setCellValue(totalRegistradoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD4 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD4.setCellStyle(style3);
								celdaAD4.setCellValue(montoRelacionadoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD5 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD5.setCellStyle(style3);
								celdaAD5.setCellValue(interesRelacionadoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD6 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD6.setCellStyle(style3);
								celdaAD6.setCellValue(totalRelacionadoDependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD7 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD7.setCellStyle(style3);
								celdaAD7.setCellValue(montoRegistradoInependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD8 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD8.setCellStyle(style3);
								celdaAD8.setCellValue(interesRegistradoInependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD9 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD9.setCellStyle(style3);
								celdaAD9.setCellValue(totalRegistradoIndependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD10 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD10.setCellStyle(style3);
								celdaAD10.setCellValue(montoRelacionadoIndependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD11 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD11.setCellStyle(style3);
								celdaAD11.setCellValue(interesRelacionadoIndependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD12 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD12.setCellStyle(style3);
								celdaAD12.setCellValue(totalRelacionadoIndependienteAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD13 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD13.setCellStyle(style3);
								celdaAD13.setCellValue(montoRegistradoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD14 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD14.setCellStyle(style3);
								celdaAD14.setCellValue(interesRegistradoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD15 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD15.setCellStyle(style3);
								celdaAD15.setCellValue(totalRegistradoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD16 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD16.setCellStyle(style3);
								celdaAD16.setCellValue(montoRelacionadoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD17 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD17.setCellStyle(style3);
								celdaAD17.setCellValue(interesRelacionadoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD18 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD18.setCellStyle(style3);
								celdaAD18.setCellValue(totalRelacionadoPensionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								BigDecimal montoRegistradoAD = BigDecimal.ZERO;
								BigDecimal interesRegistradoAD = BigDecimal.ZERO;
								BigDecimal totalRegistradoAD = BigDecimal.ZERO;
								BigDecimal montoRelacionadoAD = BigDecimal.ZERO;
								BigDecimal interesRelacionadoAD = BigDecimal.ZERO;
								BigDecimal totalRelacionadoAD = BigDecimal.ZERO;

								montoRegistradoAD = montoRegistradoAD.add(montoRegistradoDependienteAD);
								montoRegistradoAD = montoRegistradoAD.add(montoRegistradoInependienteAD);
								montoRegistradoAD = montoRegistradoAD.add(montoRegistradoPensionadoAD);
								montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoDependienteAD);
								montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoIndependienteAD);
								montoRelacionadoAD = montoRelacionadoAD.add(montoRelacionadoPensionadoAD);
								interesRegistradoAD = interesRegistradoAD.add(interesRegistradoDependienteAD);
								interesRegistradoAD = interesRegistradoAD.add(interesRegistradoInependienteAD);
								interesRegistradoAD = interesRegistradoAD.add(interesRegistradoPensionadoAD);
								interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoDependienteAD);
								interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoIndependienteAD);
								interesRelacionadoAD = interesRelacionadoAD.add(interesRelacionadoPensionadoAD);
								totalRegistradoAD = totalRegistradoAD.add(totalRegistradoDependienteAD);
								totalRegistradoAD = totalRegistradoAD.add(totalRegistradoIndependienteAD);
								totalRegistradoAD = totalRegistradoAD.add(totalRegistradoPensionadoAD);
								totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoDependienteAD);
								totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoIndependienteAD);
								totalRelacionadoAD = totalRelacionadoAD.add(totalRelacionadoPensionadoAD);

								Cell celdaAD19 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD19.setCellStyle(style3);
								celdaAD19.setCellValue(montoRegistradoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD20 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD20.setCellStyle(style3);
								celdaAD20.setCellValue(interesRegistradoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD21 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD21.setCellStyle(style3);
								celdaAD21.setCellValue(totalRegistradoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD22 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD22.setCellStyle(style3);
								celdaAD22.setCellValue(montoRelacionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD23 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD23.setCellStyle(style3);
								celdaAD23.setCellValue(interesRelacionadoAD.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								Cell celdaAD24 = filaSeis.createCell(indiceColumnaAporte);
								celdaAD24.setCellStyle(style3);
								celdaAD24.setCellValue(totalRelacionadoAD.toString());

								indiceColumnaAporte = 3;

								// "Subtotal" fila diez
								CellStyle style4 = libro.createCellStyle();
								filaDiez.setRowNum(indiceFila + 9);
								celdaSub1 = filaDiez.createCell(indiceColumnaAporte);
								style4.setAlignment(HorizontalAlignment.RIGHT);
								celdaSub1.setCellStyle(style4);
								celdaSub1.setCellValue(montoRegistradoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub2 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub2.setCellStyle(style4);
								celdaSub2.setCellValue(interesRegistradoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub3 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub3.setCellStyle(style4);
								celdaSub3.setCellValue(totalRegistradoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub4 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub4.setCellStyle(style4);
								celdaSub4.setCellValue(montoRelacionadoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub5 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub5.setCellStyle(style4);
								celdaSub5.setCellValue(interesRelacionadoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub6 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub6.setCellStyle(style4);
								celdaSub6.setCellValue(totalRelacionadoDependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub7 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub7.setCellStyle(style4);
								celdaSub7.setCellValue(montoRegistradoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub8 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub8.setCellStyle(style4);
								celdaSub8.setCellValue(interesRegistradoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub9 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub9.setCellStyle(style4);
								celdaSub9.setCellValue(totalRegistradoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub10 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub10.setCellStyle(style4);
								celdaSub10.setCellValue(montoRelacionadoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub11 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub11.setCellStyle(style4);
								celdaSub11.setCellValue(interesRelacionadoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub12 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub12.setCellStyle(style4);
								celdaSub12.setCellValue(totalRelacionadoIndependienteSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub13 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub13.setCellStyle(style4);
								celdaSub13.setCellValue(montoRegistradoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub14 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub14.setCellStyle(style4);
								celdaSub14.setCellValue(interesRegistradoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub15 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub15.setCellStyle(style4);
								celdaSub15.setCellValue(totalRegistradoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub16 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub16.setCellStyle(style4);
								celdaSub16.setCellValue(montoRelacionadoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub17 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub17.setCellStyle(style4);
								celdaSub17.setCellValue(interesRelacionadoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub18 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub18.setCellStyle(style4);
								celdaSub18.setCellValue(totalRelacionadoPensionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								montoRegistradoSub = montoRegistradoSub.add(montoRegistradoDependienteSub);
								montoRegistradoSub = montoRegistradoSub.add(montoRegistradoIndependienteSub);
								montoRegistradoSub = montoRegistradoSub.add(montoRegistradoPensionadoSub);
								montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoDependienteSub);
								montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoIndependienteSub);
								montoRelacionadoSub = montoRelacionadoSub.add(montoRelacionadoPensionadoSub);
								interesRegistradoSub = interesRegistradoSub.add(interesRegistradoDependienteSub);
								interesRegistradoSub = interesRegistradoSub.add(interesRegistradoIndependienteSub);
								interesRegistradoSub = interesRegistradoSub.add(interesRegistradoPensionadoSub);
								interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoDependienteSub);
								interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoIndependienteSub);
								interesRelacionadoSub = interesRelacionadoSub.add(interesRelacionadoPensionadoSub);
								totalRegistradoSub = totalRegistradoSub.add(totalRegistradoDependienteSub);
								totalRegistradoSub = totalRegistradoSub.add(totalRegistradoIndependienteSub);
								totalRegistradoSub = totalRegistradoSub.add(totalRegistradoPensionadoSub);
								totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoDependienteSub);
								totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoIndependienteSub);
								totalRelacionadoSub = totalRelacionadoSub.add(totalRelacionadoPensionadoSub);

								celdaSub19 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub19.setCellStyle(style4);
								celdaSub19.setCellValue(montoRegistradoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub20 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub20.setCellStyle(style4);
								celdaSub20.setCellValue(interesRegistradoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub21 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub21.setCellStyle(style4);
								celdaSub21.setCellValue(totalRegistradoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub22 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub22.setCellStyle(style4);
								celdaSub22.setCellValue(montoRelacionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub23 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub23.setCellStyle(style4);
								celdaSub23.setCellValue(interesRelacionadoSub.toString());
								indiceColumnaAporte = indiceColumnaAporte + 1;

								celdaSub24 = filaDiez.createCell(indiceColumnaAporte);
								celdaSub24.setCellStyle(style4);
								celdaSub24.setCellValue(totalRelacionadoSub.toString());

								indiceColumnaAporte = 3;
							}
						}
					}
				}
				indiceFila = indiceFila + 11;
			}
			logger.debug(
					"Finaliza el método construirHojaCuadroResumen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			return pagina;
		} catch (Exception ex) {
			logger.error("Finaliza del método construirHojaCuadroResumen: Ocurrio un error al generar el archivo excel"
					+ ex);
		}
		return null;
	}

	/**
	 * @param libro
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static Sheet construirHojaAportes(XSSFWorkbook libro, DetalleRegistroDTO detalleRegistroDTO) {
		try {
			logger.debug(
					"Inicia el método construirHojaAportes(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			logger.info(
					"Inicia el método construirHojaAportes(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");

			Sheet pagina = libro.createSheet("Aportes.");


			String[] encabezadoAportante = { 
				    "Número de Operación del Recaudo", 
					"Tipo Aportante",
					"Tipo registro aporte", 
					"Tipo identificación del aportante",
					"Número de identificación del Aportante", 
					"Razón Social – Nombre Aportante", 
					"Periodo aportado",
					"Fecha de Recaudo", 
					"Número de planilla o número asignado si es manual", 
					"Valor recaudo Caja",
					"Valor intereses de mora", 
					"Total Recaudo", 
					"Usuario que ejecutó la operación" 
				};

			String[] encabezadoCotizante = { 
				    "Número de Operación del Recaudo", 
					"Tipo registro aporte",
					"Tipo identificación del cotizante", 
					"Número de identificación del cotizante",
					"Primer Nombre del cotizante", 
					"Segundo Nombre del cotizante", 
					"Primer apellido del cotizante",
					"Segundo Apellido del cotizante", 
					"Periodo aportado", 
					"Fecha de Recaudo", 
					"Tarifa", "Valor aporte nivel 2",
					"Valor intereses de mora nivel 2", 
					"Total recaudo", 
					"Usuario que ejecutó la operación",
					"Fecha del registro de la novedad" 
				};

			int indiceRow = 0;
			int indiceColumn = 0;

			//Estilo celda aportantes
			CellStyle style = libro.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			
			//Estilo celda cotizante
			CellStyle styleCot = libro.createCellStyle();
			styleCot.setAlignment(HorizontalAlignment.CENTER);
			
			for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

				Row fila = pagina.createRow(indiceRow);

				// 0,first row (0-based); 0,last row (0-based) ; 0, first column
				// (0-based); 12 last column (0-based)

				CellRangeAddress mergedCell = new CellRangeAddress(
					    indiceRow, indiceRow, 
						indiceColumn,
						indiceColumn + 12);

				pagina.addMergedRegion(mergedCell);
				Cell celda = fila.createCell(indiceColumn);
				celda.setCellStyle(style);
				celda.setCellValue("Aportante");

				indiceRow++;
				Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);

				for (String encAportante : encabezadoAportante) {
					Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
					celdaIterEncabezadoAportante.setCellValue(encAportante);
					indiceColumn++;
				}

				indiceColumn = 0;
				indiceRow++;
				Row filaIterAportante = pagina.createRow(indiceRow);
				Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacion());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoAportante().name());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoRegistro().name());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoIdentificacionAportante().name());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroIdentificacionAportante());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getRazonSocial());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getPeriodoAporte());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaRecaudo().getTime(), true));

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroPlanilla());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getMonto().toString());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getInteres().toString());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTotalAporte().toString());

				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getUsuario());

				indiceColumn = 0;
				indiceRow++;
				Row filaVaciaAportante = pagina.createRow(indiceRow);
				filaVaciaAportante.createCell(indiceColumn);
				indiceRow++;

				if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
					Row filaCot = pagina.createRow(indiceRow);

					// 0,first row (0-based); 0,last row (0-based) ; 0, first
					// column (0-based); 12 last column (0-based)
					CellRangeAddress mergedCellCot = new CellRangeAddress(
						    indiceRow, 
							indiceRow, 
							indiceColumn,
							indiceColumn + 15);

					pagina.addMergedRegion(mergedCellCot);
					Cell celdaCot = filaCot.createCell(indiceColumn);
					celdaCot.setCellStyle(styleCot);
					celdaCot.setCellValue("Cotizantes");
					indiceRow++;

					Row filaIterEncabezadoCotizante = pagina.createRow(indiceRow);
					for (String encCotizante : encabezadoCotizante) {
						Cell celdaIterEncabezadoCotizante = filaIterEncabezadoCotizante.createCell(indiceColumn);
						celdaIterEncabezadoCotizante.setCellValue(encCotizante);
						indiceColumn++;
					}

					indiceColumn = 0;
					indiceRow++;

					for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
						Row filaIterCotizante = pagina.createRow(indiceRow);
						Cell celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroOperacion());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoRegistro().name());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoIdentificacionCotizante().name());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroIdentificacionCotizante());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerNombre());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoNombre());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerApellido());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoApellido());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPeriodoAporte());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaRecaudo().getTime(), true));

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
                        celdaIterCotizante.setCellValue(cotizante.getTarifa().toString());

                        indiceColumn++;
                        celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getMonto().toString());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getInteres().toString());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTotalAporte().toString());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getUsuario());

						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true));

						indiceColumn = 0;
						indiceRow++;
					}

					Row filaVaciaCotizante = pagina.createRow(indiceRow);
					filaVaciaCotizante.createCell(indiceColumn);
					indiceRow++;
				}
			}
			logger.debug(
					"Finaliza el método construirHojaAportes(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			logger.info(
					"Finaliza el método construirHojaAportes(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");

			return pagina;
		} catch (Exception ex) {
			logger.error("Finaliza del método construirHojaAportes: Ocurrio un error al generar el archivo excel" + ex);
			logger.info("Finaliza del método construirHojaAportes: Ocurrio un error al generar el archivo excel" + ex);
		}
		return null;
	}

	/**
	 * @param libro
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static Sheet construirHojaDevoluciones(XSSFWorkbook libro, DetalleRegistroDTO detalleRegistroDTO) {
		try {
			logger.debug(
					"Inicia el método construirHojaDevoluciones(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			Sheet pagina = libro.createSheet("Devoluciones");

			String[] encabezadoAportante = { "Número de Operación de la devolución", "Tipo Aportante",
					"Tipo registro aporte", "Tipo identificación del aportante",
					"Número de identificación del Aportante", "Razón Social – Nombre Aportante", "Periodo devolución",
					"Fecha de Devolución", "Número de planilla o número asignado si es manual", "Valor devolución Caja",
					"Valor devolución intereses de mora", "Total devolución", "Usuario que ejecutó la operación",
					"Destino", "Valor Gastos Bancarios" };

			String[] encabezadoCotizante = { "Número de Operación de la devolución", "Tipo registro aporte",
					"Tipo identificación del cotizante", "Número de identificación del cotizante",
					"Primer Nombre del cotizante", "Segundo Nombre del cotizante", "Primer apellido del cotizante",
					"Segundo Apellido del cotizante", "Periodo devolución", "Fecha de devolución", "Tarifa",
					"Valor devolución Caja", "Valor devolución intereses de mora", "Total devolución",
					"Usuario que ejecutó la operación para cotizante", "Fecha del registro de la novedad" };

			int indiceRow = 0;
			int indiceColumn = 0;

			//Estilo celda aportantes
			CellStyle style = libro.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			
			//Estilo celda cotizante
			CellStyle styleCot = libro.createCellStyle();
			styleCot.setAlignment(HorizontalAlignment.CENTER);
			
			for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

				Row fila = pagina.createRow(indiceRow);

				// 0,first row (0-based); 0,last row (0-based) ; 0, first column
				// (0-based); 12 last column (0-based)
				CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
						indiceColumn + 14);
				pagina.addMergedRegion(mergedCell);
				Cell celda = fila.createCell(indiceColumn);
				celda.setCellStyle(style);
				celda.setCellValue("Aportante");

				indiceRow++;
				Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
				for (String encAportante : encabezadoAportante) {
					Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
					celdaIterEncabezadoAportante.setCellValue(encAportante);
					indiceColumn++;
				}
				indiceColumn = 0;
				indiceRow++;
				Row filaIterAportante = pagina.createRow(indiceRow);
				Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacion());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoRegistro().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoIdentificacionAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroIdentificacionAportante());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getRazonSocial());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getPeriodoAporte());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaRecaudo().getTime(), true));
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroPlanilla());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getMonto().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getInteres().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTotalAporte().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getUsuario());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getDestinatario().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getValorGastosBancarios().toString());
				indiceColumn = 0;
				indiceRow++;
				Row filaVaciaAportante = pagina.createRow(indiceRow);
				filaVaciaAportante.createCell(indiceColumn);
				indiceRow++;

				if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
					Row filaCot = pagina.createRow(indiceRow);

					// 0,first row (0-based); 0,last row (0-based) ; 0, first
					// column (0-based); 12 last column (0-based)
					CellRangeAddress mergedCellCot = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
							indiceColumn + 15);
					pagina.addMergedRegion(mergedCellCot);
					Cell celdaCot = filaCot.createCell(indiceColumn);
					celdaCot.setCellStyle(styleCot);
					celdaCot.setCellValue("Cotizantes");
					indiceRow++;
					Row filaIterEncabezadoCotizante = pagina.createRow(indiceRow);
					for (String encCotizante : encabezadoCotizante) {
						Cell celdaIterEncabezadoCotizante = filaIterEncabezadoCotizante.createCell(indiceColumn);
						celdaIterEncabezadoCotizante.setCellValue(encCotizante);
						indiceColumn++;
					}
					indiceColumn = 0;
					indiceRow++;

					for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
						Row filaIterCotizante = pagina.createRow(indiceRow);
						Cell celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroOperacion());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoRegistro().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoIdentificacionCotizante().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroIdentificacionCotizante());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPeriodoAporte());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaRecaudo().getTime(), true));
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
                        celdaIterCotizante.setCellValue(cotizante.getTarifa().toString());
                        indiceColumn++;
                        celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getMonto().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getInteres().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTotalAporte().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getUsuario());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true));
						indiceColumn = 0;
						indiceRow++;
					}

					Row filaVaciaCotizante = pagina.createRow(indiceRow);
					filaVaciaCotizante.createCell(indiceColumn);
					indiceRow++;
				}
			}
			logger.debug(
					"Finaliza el método construirHojaDevoluciones(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			return pagina;
		} catch (Exception ex) {
			logger.error(
					"Finaliza del método construirHojaDevoluciones: Ocurrio un error al generar el archivo excel" + ex);
		}
		return null;
	}

	/**
	 * @param libro
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static Sheet construirHojaLegalizados(XSSFWorkbook libro, DetalleRegistroDTO detalleRegistroDTO) {
		try {
			logger.debug(
					"Inicia el método construirHojaLegalizados(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			Sheet pagina = libro.createSheet("Registrados(Legalizados)");

			String[] encabezadoAportante = { "Número de Operación del Recaudo", "Tipo Aportante",
					"Tipo registro aporte", "Tipo identificación del aportante",
					"Número de identificación del Aportante", "Razón Social – Nombre Aportante", "Periodo aportado",
					"Fecha de Recaudo", "Número de planilla o número asignado si es manual", "Valor recaudo Caja",
					"Valor intereses de mora", "Total recaudo", "Usuario que ejecutó la operación",
					"Fecha del reconocimiento del aporte" };

			String[] encabezadoCotizante = { "Número de Operación del recaudo", "Tipo registro aporte",
					"Tipo identificación del cotizante", "Número de identificación del cotizante",
					"Primer Nombre del cotizante", "Segundo Nombre del cotizante", "Primer apellido del cotizante",
					"Segundo Apellido del cotizante", "Periodo aportado", "Fecha de Recaudo", "Tarifa", "Valor Recaudo Caja",
					"Valor intereses de mora", "Total Recaudo", "Usuario que ejecutó la operación para cotizante",
					"Fecha del registro de la novedad" };

			int indiceRow = 0;
			int indiceColumn = 0;

			//Estilo celda aportantes
			CellStyle style = libro.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			
			//Estilo celda cotizante
			CellStyle styleCot = libro.createCellStyle();
			styleCot.setAlignment(HorizontalAlignment.CENTER);
			
			for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

				Row fila = pagina.createRow(indiceRow);

				// 0,first row (0-based); 0,last row (0-based) ; 0, first column
				// (0-based); 12 last column (0-based)
				CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
						indiceColumn + 13);
				pagina.addMergedRegion(mergedCell);
				Cell celda = fila.createCell(indiceColumn);
				celda.setCellStyle(style);
				celda.setCellValue("Aportante");

				indiceRow++;
				Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
				for (String encAportante : encabezadoAportante) {
					Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
					celdaIterEncabezadoAportante.setCellValue(encAportante);
					indiceColumn++;
				}
				indiceColumn = 0;
				indiceRow++;
				Row filaIterAportante = pagina.createRow(indiceRow);
				Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacion());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoRegistro().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoIdentificacionAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroIdentificacionAportante());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getRazonSocial());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getPeriodoAporte());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaRecaudo().getTime(), true));
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroPlanilla());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getMonto().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getInteres().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTotalAporte().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getUsuario());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaReconocimiento().getTime(), true));
				indiceColumn = 0;
				indiceRow++;
				Row filaVaciaAportante = pagina.createRow(indiceRow);
				filaVaciaAportante.createCell(indiceColumn);
				indiceRow++;

				if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
					Row filaCot = pagina.createRow(indiceRow);

					// 0,first row (0-based); 0,last row (0-based) ; 0, first
					// column (0-based); 12 last column (0-based)
					CellRangeAddress mergedCellCot = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
							indiceColumn + 15);
					pagina.addMergedRegion(mergedCellCot);
					Cell celdaCot = filaCot.createCell(indiceColumn);
					celdaCot.setCellStyle(styleCot);
					celdaCot.setCellValue("Cotizantes");
					indiceRow++;
					Row filaIterEncabezadoCotizante = pagina.createRow(indiceRow);
					for (String encCotizante : encabezadoCotizante) {
						Cell celdaIterEncabezadoCotizante = filaIterEncabezadoCotizante.createCell(indiceColumn);
						celdaIterEncabezadoCotizante.setCellValue(encCotizante);
						indiceColumn++;
					}
					indiceColumn = 0;
					indiceRow++;

					for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
						Row filaIterCotizante = pagina.createRow(indiceRow);
						Cell celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroOperacion());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoRegistro().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoIdentificacionCotizante().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroIdentificacionCotizante());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPeriodoAporte());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaRecaudo().getTime(), true));
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
                        celdaIterCotizante.setCellValue(cotizante.getTarifa().toString());
                        indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getMonto().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getInteres().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTotalAporte().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getUsuario());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true));
						indiceColumn = 0;
						indiceRow++;
					}

					Row filaVaciaCotizante = pagina.createRow(indiceRow);
					filaVaciaCotizante.createCell(indiceColumn);
					indiceRow++;
				}
			}
			logger.debug(
					"Finaliza el método construirHojaLegalizados(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			return pagina;
		} catch (Exception ex) {
			logger.error(
					"Finaliza del método construirHojaLegalizados: Ocurrio un error al generar el archivo excel" + ex);
		}
		return null;
	}

	/**
	 * @param libro
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static Sheet construirHojaCorreccionesAnulados(XSSFWorkbook libro, DetalleRegistroDTO detalleRegistroDTO) {

		logger.debug(
				"Inicia el método construirHojaCorreccionesAnulados(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
		Sheet pagina = libro.createSheet("Correcciones recaudo original");

		String[] encabezadoAportante = { "Número de Operación del Recaudo afectado", "Tipo Aportante",
				"Tipo registro aporte", "Tipo identificación del aportante",
				"Número de identificación del Aportante", "Razón Social – Nombre Aportante", "Periodo corregido",
				"Fecha de Recaudo", "Fecha de corrección", "Número de planilla o número asignado si es manual",
				"Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección",
				"Usuario que ejecutó la operación", "Número de Operación de la corrección" };

		String[] encabezadoCotizante = { "Número de Operación del recaudo", "Tipo registro aporte",
				"Tipo identificación del cotizante", "Número de identificación del cotizante",
				"Primer Nombre del cotizante", "Segundo Nombre del cotizante", "Primer apellido del cotizante",
				"Segundo Apellido del cotizante", "Periodo corregido", "Fecha de Recaudo", "Tipo de transacción", "Tarifa",
				"Valor corrección Caja", "Valor corrección intereses de mora", "Total corrección",
				"Usuario que ejecutó la operación para cotizante", "Fecha del registro de la novedad",
				"Número de Operación de la corrección" };

		int indiceRow = 0;
		int indiceColumn = 0;
		
		//Estilo celda aportantes
		CellStyle style = libro.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		//Estilo celda cotizante
		CellStyle styleCot = libro.createCellStyle();
		styleCot.setAlignment(HorizontalAlignment.CENTER);

		for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

			Row fila = pagina.createRow(indiceRow);

			// 0,first row (0-based); 0,last row (0-based) ; 0, first column
			// (0-based); 12 last column (0-based)
			CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
					indiceColumn + 14);
			pagina.addMergedRegion(mergedCell);
			Cell celda = fila.createCell(indiceColumn);
			celda.setCellStyle(style);
			celda.setCellValue("Aportante");

			indiceRow++;
			Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
			for (String encAportante : encabezadoAportante) {
				Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
				celdaIterEncabezadoAportante.setCellValue(encAportante);
				indiceColumn++;
			}
			indiceColumn = 0;
			indiceRow++;
			Row filaIterAportante = pagina.createRow(indiceRow);
			Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacion());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getTipoAportante().name());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getTipoRegistro().name());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getTipoIdentificacionAportante().name());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getNumeroIdentificacionAportante());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getRazonSocial());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getPeriodoAporte());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(FuncionesUtilitarias
					.formatoFechaMilis(resultado.getAportante().getFechaRecaudo().getTime(), true));
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(FuncionesUtilitarias
					.formatoFechaMilis(resultado.getAportante().getFechaRecaudoCorreccion().getTime(), true));
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getNumeroPlanilla());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getMonto().toString());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getInteres().toString());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getTotalAporte().toString());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getUsuario());
			indiceColumn++;
			celdaIterAportante = filaIterAportante.createCell(indiceColumn);
			celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacionCorreccion());
			indiceColumn = 0;
			indiceRow++;
			Row filaVaciaAportante = pagina.createRow(indiceRow);
			filaVaciaAportante.createCell(indiceColumn);
			indiceRow++;

			if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
				Row filaCot = pagina.createRow(indiceRow);

				// 0,first row (0-based); 0,last row (0-based) ; 0, first
				// column (0-based); 12 last column (0-based)
				CellRangeAddress mergedCellCot = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
						indiceColumn + 17);
				pagina.addMergedRegion(mergedCellCot);
				Cell celdaCot = filaCot.createCell(indiceColumn);
				celdaCot.setCellStyle(styleCot);
				celdaCot.setCellValue("Cotizantes");
				indiceRow++;
				Row filaIterEncabezadoCotizante = pagina.createRow(indiceRow);
				for (String encCotizante : encabezadoCotizante) {
					Cell celdaIterEncabezadoCotizante = filaIterEncabezadoCotizante.createCell(indiceColumn);
					celdaIterEncabezadoCotizante.setCellValue(encCotizante);
					indiceColumn++;
				}
				indiceColumn = 0;
				indiceRow++;

				for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
					Row filaIterCotizante = pagina.createRow(indiceRow);
					Cell celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getNumeroOperacion());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getTipoRegistro().name());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getTipoIdentificacionCotizante().name());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getNumeroIdentificacionCotizante());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getPrimerNombre());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getSegundoNombre());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getPrimerApellido());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getSegundoApellido());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getPeriodoAporte());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(
							FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaRecaudo().getTime(), true));
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getTipoTransaccion());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
                    celdaIterCotizante.setCellValue(cotizante.getTarifa().toString());
                    indiceColumn++;
                    celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getMonto().toString());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getInteres().toString());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getTotalAporte().toString());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getUsuario());
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(
							FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true));
					indiceColumn++;
					celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
					celdaIterCotizante.setCellValue(cotizante.getNumeroOperacionCorreccion());
					indiceColumn = 0;
					indiceRow++;
				}

				Row filaVaciaCotizante = pagina.createRow(indiceRow);
				filaVaciaCotizante.createCell(indiceColumn);
				indiceRow++;
			}
		}
		logger.debug(
				"Finaliza el método construirHojaCorreccionesAnulados(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
		return pagina;
	}

	/**
	 * @param libro
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static Sheet construirHojaCorreccionesOrigen(XSSFWorkbook libro, DetalleRegistroDTO detalleRegistroDTO) {
		try {
			logger.debug(
					"Inicia el método construirHojaCorreccionesOrigen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			Sheet pagina = libro.createSheet("Correcciones nuevo aporte");

			String[] encabezadoAportante = { "Número de Operación de la corrección", "Tipo Aportante",
					"Tipo registro aporte", "Tipo identificación del aportante",
					"Número de identificación del Aportante", "Razón Social – Nombre Aportante",
					"Periodo del nuevo aporte", "Fecha de Recaudo", "Fecha de corrección",
					"Número de planilla o número asignado si es manual", "Valor corrección Caja",
					"Valor corrección intereses de mora", "Total corrección", "Usuario que ejecutó la operación" };

			String[] encabezadoCotizante = { "Número de Operación del recaudo", "Tipo registro aporte",
					"Tipo identificación del cotizante", "Número de identificación del cotizante",
					"Primer Nombre del cotizante", "Segundo Nombre del cotizante", "Primer apellido del cotizante",
					"Segundo Apellido del cotizante", "Periodo del nuevo aporte", "Fecha de Recaudo",
					"Tipo de transacción", "Tarifa", "Valor corrección Caja", "Valor corrección intereses de mora",
					"Total corrección", "Usuario que ejecutó la operación para cotizante",
					"Fecha del registro de la novedad" };

			int indiceRow = 0;
			int indiceColumn = 0;
			
			//Estilo celda aportantes
			CellStyle style = libro.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			
			//Estilo celda cotizante
			CellStyle styleCot = libro.createCellStyle();
			styleCot.setAlignment(HorizontalAlignment.CENTER);

			for (ResultadoDetalleRegistroDTO resultado : detalleRegistroDTO.getResultadoporRegistros()) {

				Row fila = pagina.createRow(indiceRow);

				// 0,first row (0-based); 0,last row (0-based) ; 0, first column
				// (0-based); 12 last column (0-based)
				CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
						indiceColumn + 13);
				pagina.addMergedRegion(mergedCell);
				Cell celda = fila.createCell(indiceColumn);
				celda.setCellStyle(style);
				celda.setCellValue("Aportante");

				indiceRow++;
				Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
				for (String encAportante : encabezadoAportante) {
					Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
					celdaIterEncabezadoAportante.setCellValue(encAportante);
					indiceColumn++;
				}
				indiceColumn = 0;
				indiceRow++;
				Row filaIterAportante = pagina.createRow(indiceRow);
				Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroOperacion());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoRegistro().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTipoIdentificacionAportante().name());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroIdentificacionAportante());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getRazonSocial());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getPeriodoAporte());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaRecaudo().getTime(), true));
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(FuncionesUtilitarias
						.formatoFechaMilis(resultado.getAportante().getFechaRecaudoCorreccion().getTime(), true));
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getNumeroPlanilla());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getMonto().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getInteres().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getTotalAporte().toString());
				indiceColumn++;
				celdaIterAportante = filaIterAportante.createCell(indiceColumn);
				celdaIterAportante.setCellValue(resultado.getAportante().getUsuario());
				indiceColumn = 0;
				indiceRow++;
				Row filaVaciaAportante = pagina.createRow(indiceRow);
				filaVaciaAportante.createCell(indiceColumn);
				indiceRow++;

				if (resultado.getCotizantes() != null && !resultado.getCotizantes().isEmpty()) {
					Row filaCot = pagina.createRow(indiceRow);

					// 0,first row (0-based); 0,last row (0-based) ; 0, first
					// column (0-based); 12 last column (0-based)
					CellRangeAddress mergedCellCot = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
							indiceColumn + 16);
					pagina.addMergedRegion(mergedCellCot);
					Cell celdaCot = filaCot.createCell(indiceColumn);
					celdaCot.setCellStyle(styleCot);
					celdaCot.setCellValue("Cotizantes");
					indiceRow++;
					Row filaIterEncabezadoCotizante = pagina.createRow(indiceRow);
					for (String encCotizante : encabezadoCotizante) {
						Cell celdaIterEncabezadoCotizante = filaIterEncabezadoCotizante.createCell(indiceColumn);
						celdaIterEncabezadoCotizante.setCellValue(encCotizante);
						indiceColumn++;
					}
					indiceColumn = 0;
					indiceRow++;

					for (DetalleRegistroCotizanteDTO cotizante : resultado.getCotizantes()) {
						Row filaIterCotizante = pagina.createRow(indiceRow);
						Cell celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroOperacion());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoRegistro().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoIdentificacionCotizante().name());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getNumeroIdentificacionCotizante());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoNombre());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPrimerApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getSegundoApellido());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getPeriodoAporte());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaRecaudo().getTime(), true));
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTipoTransaccion());
						indiceColumn++;
                        celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
                        celdaIterCotizante.setCellValue(cotizante.getTarifa().toString());
                        indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getMonto().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getInteres().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getTotalAporte().toString());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(cotizante.getUsuario());
						indiceColumn++;
						celdaIterCotizante = filaIterCotizante.createCell(indiceColumn);
						celdaIterCotizante.setCellValue(
								FuncionesUtilitarias.formatoFechaMilis(cotizante.getFechaNovedad().getTime(), true));
						indiceColumn = 0;
						indiceRow++;
					}

					Row filaVaciaCotizante = pagina.createRow(indiceRow);
					filaVaciaCotizante.createCell(indiceColumn);
					indiceRow++;
				}
			}
			logger.debug(
					"Finaliza el método construirHojaCorreccionesOrigen(HSSFWorkbook libro, List<ResumenCierreRecaudoDTO> resumenCierreRecaudo)");
			return pagina;
		} catch (Exception ex) {
			logger.error(
					"Finaliza del método construirHojaCorreccionesOrigen: Ocurrio un error al generar el archivo excel"
							+ ex);
		}
		return null;
	}

	/**
	 * @param detalleRegistroDTO
	 * @return
	 */
	private static String obtenerIdsAportes(DetalleRegistroDTO detalleRegistroDTO) {
		StringBuilder ids = new StringBuilder();
		int indice = 0;
		for (ResultadoDetalleRegistroDTO detalle : detalleRegistroDTO.getResultadoporRegistros()) {
			if (indice == 0) {
				indice++;
			} else {
				ids.append(",");
			}
			ids.append(detalle.getAportante().getNumeroOperacion());
		}
		return ids.toString();
	}
}
