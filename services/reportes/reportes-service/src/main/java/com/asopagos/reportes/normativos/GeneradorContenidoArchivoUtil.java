package com.asopagos.reportes.normativos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.xsd.AFILIADOS2017C01;
import com.asopagos.reportes.xsd.AFILIADOSACARGO2017C01;
import com.asopagos.reportes.xsd.ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01;
import com.asopagos.reportes.xsd.ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01;
import com.asopagos.reportes.xsd.CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01;
import com.asopagos.reportes.xsd.CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01;
import com.asopagos.reportes.xsd.EMPRESASENMORA2017C01;
import com.asopagos.reportes.xsd.EMPRESASYAPORTANTES2017C01;
import com.asopagos.reportes.xsd.NUMEROCUOTAS2017C1;
import com.asopagos.reportes.xsd.POSTULACIONESASIGNACIONESFOVIS2017C01;
import com.asopagos.rest.exception.TechnicalException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase encargada de generar el flujo de bytes para todos los formatos de
 * archivos soportados
 * 
 * @author
 */
public class GeneradorContenidoArchivoUtil {

	public static byte[] generarArchivoPlano(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintWriter writer = new PrintWriter(baos)) {
			if (encabezado != null && !encabezado.isEmpty()) {
				construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
			}
			if (data != null && !data.isEmpty()) {
				construirSeccionArchivoPlano(writer, data, caracterSeparador);
			}
			writer.flush();
		}
		return baos.toByteArray();
	}

	public static byte[] generarArchivoCSV(List<String[]> encabezado, List<String[]> data, String caracterSeparador) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter baoss = null;
		try {
			baoss = new OutputStreamWriter(baos, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		baos.write(239);
		baos.write(187);
		baos.write(191);
		try (PrintWriter writer = new PrintWriter(baoss)) {
			if (encabezado != null && !encabezado.isEmpty()) {
				construirSeccionArchivoPlano(writer, encabezado, caracterSeparador);
			}
			if (data != null && !data.isEmpty()) {
				construirSeccionArchivoPlano(writer, data, caracterSeparador);
			}
			writer.flush();
		}
		return baos.toByteArray();
	}

	private static void construirSeccionArchivoPlano(PrintWriter writer, List<String[]> datosSeccion,
			String caracterSeparador) {
		int i;
		for (Object[] valores : datosSeccion) {
			i = 0;
			for (Object valor : valores) {
				i++;
				writer.print(valor != null ? valor.toString() : "");
				if (i < valores.length) {
					writer.print(caracterSeparador);
				}
			}
			writer.print('\r');
			writer.print('\n');
		}
	}

	public static byte[] generarNuevoArchivoExcel(List<String[]> encabezado, List<String[]> data,
            ReporteNormativoEnum reporteNormativo) {
        // TO-DO Usar Apache POI paara crar este archivo en formato XSLX
        XSSFWorkbook libro = new XSSFWorkbook();
        Sheet pagina = libro.createSheet("reporte");

        int indiceRow = 0;
        int indiceColumn = 0;

        if (encabezado != null && encabezado.size() > 1) {
            // 0,first row (0-based); 0,last row (0-based) ; 0, first column (0-based); last
            // column (0-based)
            CellRangeAddress mergedCell = new CellRangeAddress(indiceRow, indiceRow, indiceColumn,
                    encabezado.get(1).length);
            pagina.addMergedRegion(mergedCell);
            indiceRow++;
        }

        if (encabezado != null) {
            // Generación del encabezado del reporte
            Row filaIterEncabezadoAportante = pagina.createRow(indiceRow);
            for (int i = 0; i < encabezado.size(); i++) {
                for (String encAportante : encabezado.get(i)) {
                    Cell celdaIterEncabezadoAportante = filaIterEncabezadoAportante.createCell(indiceColumn);
                    celdaIterEncabezadoAportante.setCellValue(encAportante);
                    indiceColumn++;
                }
                indiceRow++;
            }
        }

        if (reporteNormativo.name()
                .equals(com.asopagos.enumeraciones.reportes.ReporteNormativoEnum.REPORTE_NOVEDADES_AFILIADOS_SUBSIDIOS
                        .toString())) {
            // Generación de cada registro por fila
            for (Object[] items : data) {
                indiceColumn = 0;
                Row filaIterAportante = pagina.createRow(indiceRow);

                for (int i = 0; i < items.length; i++) {
                    Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);

                    Integer numero = null;
                    if (items[i] != null && items[i] instanceof BigDecimal) {
                        numero = BigDecimal.valueOf(Double.valueOf(items[i].toString()))
                                .setScale(2, RoundingMode.HALF_UP).toBigInteger().intValue();
                    } else if (items[i] != null && items[i] instanceof Integer) {
                        numero = Integer.valueOf(items[i].toString());
                    }

                    if (numero != null) {
                        celdaIterAportante.setCellType(CellType.NUMERIC);
                        celdaIterAportante.setCellValue(numero.longValue());
                        indiceColumn++;

                    } else {
                        if (items[i] != null) {
                            // si es fecha la muestra sin hora, si no, agrega la info
                            if (items[i] instanceof Date) {
                                String fechaConvertida = items[i].toString().substring(0, 10);

                                celdaIterAportante.setCellValue(fechaConvertida);
                                indiceColumn++;
                            } else {
                                celdaIterAportante.setCellValue(items[i].toString());
                                indiceColumn++;
                            }
                        } else {
                            // si es vacio puede dejar campos vacios antes del codigo
                            if (i < 9) {
                                celdaIterAportante.setCellValue("");
                                indiceColumn++;
                            }
                        }

                    }

                }

                // se aumenta el número de la fila para almacenar el otro registro
                indiceRow++;
            }
        } else {

            for (Object[] items : data) {
                indiceColumn = 0;
                Row filaIterAportante = pagina.createRow(indiceRow);
                for (Object dato : items) {
                    Cell celdaIterAportante = filaIterAportante.createCell(indiceColumn);

                    Integer numero = null;
                    if (dato != null && dato instanceof BigDecimal) {
                        numero = BigDecimal.valueOf(Double.valueOf(dato.toString())).setScale(2, RoundingMode.HALF_UP)
                                .toBigInteger().intValue();
                    } else if (dato != null && dato instanceof Integer) {
                        numero = Integer.valueOf(dato.toString());
                    }

                    if (numero != null) {
                        celdaIterAportante.setCellType(CellType.NUMERIC);
                        celdaIterAportante.setCellValue(numero.longValue());

                    } else {
                        celdaIterAportante.setCellValue(dato != null ? dato.toString() : "");
                    }

                    indiceColumn++;
                }
                // se aumenta el número de la fila para almacenar el otro registro
                indiceRow++;
            }
        }

        ByteArrayOutputStream archivo = new ByteArrayOutputStream();
        try {
            // Almacenamos el libro de Excel via ese flujo de datos
            libro.write(archivo);
            libro.close();
        } catch (IOException e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
                    e + " En el cierre del archivo para la generación del Excel");
        }
        return archivo.toByteArray();
    }

	public static byte[] generarArchivoXML(List<String[]> encabezado, List<String[]> data,
			ReporteNormativoEnum reporteNormativo) {
		byte[] respuesta = null;

		switch (reporteNormativo) {
		case NUMERO_CUOTAS:
			respuesta = generarXMLNumeroCuotas(data);
			break;
		case EMPRESAS_Y_APORTANTES:
			respuesta = generarXMLempresasAportantes(data);
			break;
		case AFILIADOS:
			respuesta = generarXMLafiliados(data);
			break;
		case AFILIADOS_A_CARGO:
			respuesta = generarXMLafiliadoAcargo(data);
			break;
		case EMPRESAS_EN_MORA:
			respuesta = generarXMLempresasMora(data);
			break;
		case ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS:
			respuesta = generarXMLasignacionPagoReintegroSubsidiosViviendaFovis(data);
			break;
		case CONSOLIDADO_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS:
			respuesta = generarXMLConsolidadPagoReintegroSubsidiosViviendaFovis(data);
			break;
		case CONSOLIDADO_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_MICRO_FOVIS:
			respuesta = generarXMLConsolidadoPagoReintegroSubsidiosViviendaMicroDatoFovis(data);
			break;
		case ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_MICRO_FOVIS:
			respuesta = generarXMLAsignacionEntregaReintegroSubsidiosViviendaMicroDatoFovis(data);
			break;
		default: // POSTULACIONES_ASIGNACIONES_FOVIS
			respuesta = generarXMLpostulacionesAsignacionesFovis(data);
			break;
		}

		return respuesta;
	}

	private static byte[] generarXMLempresasAportantes(List<String[]> data) {
		EMPRESASYAPORTANTES2017C01 empresaAportante = new EMPRESASYAPORTANTES2017C01();
		List<EMPRESASYAPORTANTES2017C01.TEMPRESASYAPORTANTES2017C01> lstEmpresasAportantes = new ArrayList<>();
		for (Object[] items : data) {
			EMPRESASYAPORTANTES2017C01.TEMPRESASYAPORTANTES2017C01 item = new EMPRESASYAPORTANTES2017C01.TEMPRESASYAPORTANTES2017C01();
			item.setTIPIDENTIFICACION(isNull(items[0], ""));
			item.setNUMIDENTIFICACION(isNull(items[1], ""));
			item.setNOMEMPRESA(isNull(items[2], ""));
			item.setCODMUNICIPIODANE(isNull(items[3], ""));
			item.setDIRCORRESPONDECIA(isNull(items[4], ""));
			item.setESTVINCULACION(isNull(items[5], ""));
			item.setTIPAPORTANTE(isNull(items[6], ""));
			item.setTIPSECTOR(isNull(items[7], ""));
			item.setACTECONOMICA(items[8].toString());
			item.setSITEMPRESALEY1429(isNull(items[9], ""));
			item.setPROPAGOLEY1429(isNull(items[10], ""));
			item.setSITEMPRESALEY590(isNull(items[11], ""));
			item.setPROPAGOLEY590(isNull(items[12], ""));
			item.setAPOTOTALMENSUAL(BigInteger.valueOf(Long.valueOf(isNull(items[13], "0"))));
			item.setINTPAGADOSMORA(BigInteger.valueOf(Long.valueOf(isNull(items[14], "0"))));
			item.setVALREINTEGROS(BigInteger.valueOf(Long.valueOf(isNull(items[15], "0"))));
			lstEmpresasAportantes.add(item);
		}
		empresaAportante.withTEMPRESASYAPORTANTES2017C01(lstEmpresasAportantes);
		return obtenerBytesArchivoXML(empresaAportante);
	}

	private static byte[] generarXMLafiliados(List<String[]> data) {
		AFILIADOS2017C01 reporteAfiliado = new AFILIADOS2017C01();
		List<AFILIADOS2017C01.TAFILIADOS2017C01> lstAfiliados = new ArrayList<>();
		for (Object[] items : data) {
			AFILIADOS2017C01.TAFILIADOS2017C01 item = new AFILIADOS2017C01.TAFILIADOS2017C01();
			item.setTIPIDENTIFICACIONEMPRESA(isNull(items[0], ""));
			item.setNUMIDENTIFICACIONEMPRESA(isNull(items[1], "")); //
			item.setTIPIDENTIFICACIONAFILIADO(isNull(items[2], ""));
			item.setNUMIDENTIFICACIONAFILIADO(isNull(items[3], ""));
			item.setPRINOMBRE(isNull(items[4], ""));
			item.setSEGNOMBRE(isNull(items[5], ""));
			item.setPRIAPELLIDO(isNull(items[6], ""));
			item.setSEGAPELLIDO(isNull(items[7], ""));
			item.setFECNACIMIENTO(isNull(items[8], ""));
			item.setGENEROCCF(isNull(items[9], ""));
			item.setORISEXUAL(isNull(items[10], ""));
			item.setNIVELESCOLARIDAD(isNull(items[11], ""));
			item.setCODOCUPACIONDANE(isNull(items[12], ""));
			item.setFACTORVULNERABILIDAD(isNull(items[13], ""));
			item.setESTADOCIVIL(isNull(items[14], ""));
			item.setPERTENENCIAETNICA(isNull(items[15], ""));
			item.setPAISRESIDENCIABENEFICIARIO(isNull(items[16], ""));
			item.setCODMUNICIPIODANE(isNull(items[17], ""));
			item.setAREGEOGRAFICARESIDENCIA(isNull(items[18], ""));
			item.setSALBASICO(isNull(items[19], ""));
			item.setTIPAFILIADO(isNull(items[20], ""));
			item.setCATEGORIACCF(isNull(items[21], ""));
			item.setBENCUOTAMONETARIA(isNull(items[22], ""));
			lstAfiliados.add(item);
		}
		reporteAfiliado.withTAFILIADOS2017C01(lstAfiliados);
		return obtenerBytesArchivoXML(reporteAfiliado);
	}

	private static byte[] generarXMLafiliadoAcargo(List<String[]> data) {
		AFILIADOSACARGO2017C01 afiliadoAcargo = new AFILIADOSACARGO2017C01();
		List<AFILIADOSACARGO2017C01.TAFILIADOSACARGO2017C01> lstAfiliados = new ArrayList<>();
		for (Object[] items : data) {
			AFILIADOSACARGO2017C01.TAFILIADOSACARGO2017C01 item = new AFILIADOSACARGO2017C01.TAFILIADOSACARGO2017C01();

			item.setTIPIDENTIFICACION(isNull(items[0], ""));
			item.setNUMIDENTIFICACIONEMPRESA(isNull(items[1], ""));
			item.setTIPIDENTIFICACIONAFILIADO(isNull(items[2], ""));
			item.setNUMIDENTIFICACIONAFILIADO(isNull(items[3], ""));
			item.setTIPIDENTIFICACIONPERSONAACARGO(isNull(items[4], ""));
			item.setNUMIDENTIFICACIONPERSONAACARGO(isNull(items[5], ""));
			item.setPRINOMBREPERSONAACARGO(isNull(items[6], ""));
			item.setSEGNOMBREPERSONAACARGO(isNull(items[7], ""));
			item.setPRIAPELLIDOPERSONAACARGO(isNull(items[8], ""));
			item.setSEGAPELLIDOPERSONAACARGO(isNull(items[9], ""));
			item.setFECNACIMIENTOPERSONAACARGO(isNull(items[10], ""));
			item.setGENPERSONAACARGO(isNull(items[11], ""));
			item.setPARPERSONAACARGO(isNull(items[12], ""));
			item.setCODMUNICIPIORESIDENCIADANE(isNull(items[13], ""));
			item.setAREGEOGRAFICARESIDENCIA(isNull(items[14], ""));
			item.setDISCPERSONAACARGO(isNull(items[15], ""));
			item.setTIPCUOTAMONETARIAPERSONAACARGO(isNull(items[16], ""));
			item.setVALCUOTAMONETARIAPERSONAACARGO(isNull(items[17], ""));
			item.setNUMCUOTASPAGADAS(isNull(items[18], ""));
			item.setNUMPERIODOSPAGADAS(isNull(items[19], ""));
			lstAfiliados.add(item);
		}
		afiliadoAcargo.withTAFILIADOSACARGO2017C01(lstAfiliados);
		return obtenerBytesArchivoXML(afiliadoAcargo);
	}

	private static byte[] generarXMLempresasMora(List<String[]> data) {
		EMPRESASENMORA2017C01 empresaMora = new EMPRESASENMORA2017C01();
		List<EMPRESASENMORA2017C01.TEMPRESASENMORA2017C01> listaEmpresasMora = new ArrayList<>();
		for (Object[] items : data) {
			EMPRESASENMORA2017C01.TEMPRESASENMORA2017C01 empresa = new EMPRESASENMORA2017C01.TEMPRESASENMORA2017C01();

			if (items[0] != null)
				empresa.setTIPIDENTIFICACION((items[0].toString()));
			if (items[1] != null)
				empresa.setNUMIDENTIFICACIONEMPRESA(items[1].toString());
			if (items[2] != null)
				empresa.setNOMEMPRESA(items[2].toString());

			empresa.setCODMUNICIPIODANE(isNull(items[3], ""));
			empresa.setDIREMPRESA(isNull(items[4], ""));
			empresa.setREPLEGAL(isNull(items[5], ""));
			empresa.setFECINICIOMORA(isNull(items[6], ""));
			empresa.setSALMORA(isNull(items[7], ""));
			empresa.setPERMORA(isNull(items[8], ""));
			empresa.setGESCOBRO(isNull(items[9], ""));
			empresa.setACUPAGO(isNull(items[10], ""));
			empresa.setCARRECUPERADA(isNull(items[11], ""));
			empresa.setCORELECTRONICO(isNull(items[12], ""));

			listaEmpresasMora.add(empresa);
		}
		empresaMora.withTEMPRESASENMORA2017C01(listaEmpresasMora);
		return obtenerBytesArchivoXML(empresaMora);
	}

	private static byte[] generarXMLasignacionPagoReintegroSubsidiosViviendaFovis(List<String[]> data) {

		ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 asignacionPago = new ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01();
		List<ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01.TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01> lstAsignaciones = new ArrayList<>();

		for (Object[] items : data) {
			ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01.TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 item = new ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01.TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01();
			item.setANIOVIGENCIA(items[0] != null ? items[0].toString() : "");
			item.setFUEFINANCIAMIENTO(items[1] != null ? items[1].toString() : "");
			item.setCODTIPOVIVIENDA(items[2] != null ? items[2].toString() : "");
			item.setCODMUNICIPIODANE(items[3] != null ? items[3].toString() : "");
			item.setGENEROCCF(items[4] != null ? items[4].toString() : "");
			item.setRANEDAD(items[5] != null ? items[5].toString() : "");
			item.setNIVINGRESO(items[6] != null ? items[6].toString() : "");
			item.setCOMVIVIENDA(items[7] != null ? items[7].toString() : "");
			item.setESTSUBSIDIOVIVIENDA(items[8] != null ? items[8].toString() : "");
			item.setCANSUBSIDIOSASIGNADOS(items[9] != null ? items[9].toString() : "");
			item.setVALSUBSIDIOSASIGNADOS(items[10] != null ? items[10].toString() : "");

			lstAsignaciones.add(item);
		}
		asignacionPago.withTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01(lstAsignaciones);
		return obtenerBytesArchivoXML(asignacionPago);
	}

	private static byte[] generarXMLpostulacionesAsignacionesFovis(List<String[]> data) {
		POSTULACIONESASIGNACIONESFOVIS2017C01 reporte = new POSTULACIONESASIGNACIONESFOVIS2017C01();
		List<POSTULACIONESASIGNACIONESFOVIS2017C01.TPOSTULACIONESASIGNACIONESFOVIS2017C01> lstPostulaciones = new ArrayList<>();

		for (Object[] items : data) {
			POSTULACIONESASIGNACIONESFOVIS2017C01.TPOSTULACIONESASIGNACIONESFOVIS2017C01 item = new POSTULACIONESASIGNACIONESFOVIS2017C01.TPOSTULACIONESASIGNACIONESFOVIS2017C01();
			if (items[0] != null)
				item.setANIOVIGENCIA(BigInteger.valueOf(Long.valueOf(items[0].toString())));
			if (items[1] != null)
				item.setFECAPERTURAFOVIS((String) items[1]);
			if (items[2] != null)
				item.setFECCIERREFOVIS((String) items[2]);
			if (items[3] != null)
				item.setFECASIGNACIONFOVIS((String) items[3]);

			lstPostulaciones.add(item);
		}
		reporte.withTPOSTULACIONESASIGNACIONESFOVIS2017C01(lstPostulaciones);
		return obtenerBytesArchivoXML(reporte);
	}

	private static byte[] generarXMLConsolidadPagoReintegroSubsidiosViviendaFovis(List<String[]> data) {

		CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 consolidadoPago = new CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01();
		List<CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01.TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01> lstConsolidado = new ArrayList<>();

		for (Object[] items : data) {
			CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01.TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 item = new CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01.TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01();
			item.setANIOVIGENCIAASIGNACIONSUBSIDIO(items[0] != null ? items[0].toString() : "");
			item.setCODTIPOPLANVIVIENDA(items[1] != null ? items[1].toString() : "");
			item.setESTSUBSIDIO(items[2] != null ? items[2].toString() : "");
			item.setCANSUBSIDIOS(items[3] != null ? items[3].toString() : "");
			item.setVALTOTALSUBSIDIOS(items[4] != null ? items[4].toString() : "");

			lstConsolidado.add(item);
		}
		consolidadoPago.withTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01(lstConsolidado);
		return obtenerBytesArchivoXML(consolidadoPago);
	}

	private static byte[] generarXMLConsolidadoPagoReintegroSubsidiosViviendaMicroDatoFovis(List<String[]> data) {

		CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 consolidadoPago = new CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01();
		List<CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01.TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01> lstConsolidado = new ArrayList<>();

		for (Object[] items : data) {
			CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01.TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 item = new CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01.TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01();
			item.setTIPIDENTIFICACIONAFILIADO(items[0] != null ? items[0].toString() : "");
			item.setNUMIDENTIFICAAFILIADO(items[1] != null ? items[1].toString() : "");
			item.setANIOVIGENCIAASIGNACIONSUBSIDIO(items[2] != null ? items[2].toString() : "");
			item.setCODTIPOPLANVIVIENDA(items[3] != null ? items[3].toString() : "");
			item.setESTSUBSIDIO(items[4] != null ? items[4].toString() : "");
			item.setVALSUBSIDIO(items[5] != null ? items[5].toString() : "");

			lstConsolidado.add(item);
		}
		consolidadoPago.withTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01(lstConsolidado);
		return obtenerBytesArchivoXML(consolidadoPago);
	}

	private static byte[] generarXMLAsignacionEntregaReintegroSubsidiosViviendaMicroDatoFovis(List<String[]> data) {

		ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 consolidadoPago = new ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01();
		List<ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01.TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01> lstConsolidado = new ArrayList<>();

		for (Object[] items : data) {
			ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01.TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01 item = new ASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01.TASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01();
			item.setTIPIDENTIFICACION(items[0] != null ? items[0].toString() : "");
			item.setNUMIDENTIFICAAFILIADO(items[1] != null ? items[1].toString() : "");
			item.setCOMPONENTEHOGAR(items[2] != null ? items[2].toString() : "");
			item.setTIPIDENTIFICACIONINTEGRANTEHOGAR(items[3] != null ? items[3].toString() : "");
			item.setNUMIDENTIFICACIONINTEGRANTEHOGAR(items[4] != null ? items[4].toString() : "");
			item.setTITULARSUBSIDIO(items[5] != null ? items[5].toString() : "");
			item.setPRINOMBRE(items[6] != null ? items[6].toString() : "");
			item.setSEGNOMBRE(items[7] != null ? items[7].toString() : "");
			item.setPRIAPELLIDO(items[8] != null ? items[8].toString() : "");
			item.setSEGAPELLIDO(items[9] != null ? items[9].toString() : "");
			item.setPARENTESCO(items[10] != null ? items[10].toString() : "");
			item.setINGRESOINTEGRANTEHOGAR(items[11] != null ? items[11].toString() : "");
			item.setNIVELINGRESOHOGAR(items[12] != null ? items[12].toString() : "");
			item.setCOMPONENTE(items[13] != null ? items[13].toString() : "");
			item.setANIOASIGNACION(items[14] != null ? items[14].toString() : "");
			item.setESTSUBSIDIO(items[15] != null ? items[15].toString() : "");
			item.setVALSUBSIDIO(items[16] != null ? items[16].toString() : "");

			lstConsolidado.add(item);
		}
		consolidadoPago.withTASIGNAPAGOREINTEGROSIBSIDIOVIVIENDAMICRODATO2017C01(lstConsolidado);
		return obtenerBytesArchivoXML(consolidadoPago);
	}

	private static byte[] generarXMLNumeroCuotas(List<String[]> data) {
		NUMEROCUOTAS2017C1 reporte = new NUMEROCUOTAS2017C1();
		List<NUMEROCUOTAS2017C1.TNUMEROCUOTAS2017C1> lstNumeroCuotas = new ArrayList<>();

		for (Object[] items : data) {
			NUMEROCUOTAS2017C1.TNUMEROCUOTAS2017C1 item = new NUMEROCUOTAS2017C1.TNUMEROCUOTAS2017C1();
			if (items[0] != null)
				item.setCODIGODEPARTAMENTO((String) items[0]);
			if (items[1] != null)
				item.setANIO(BigInteger.valueOf(Long.valueOf(items[1].toString())));
			if (items[2] != null)
				item.setVALOR(BigInteger.valueOf(Long.valueOf(items[2].toString())));

			lstNumeroCuotas.add(item);
		}
		reporte.withTUNMEROCUOTAS2017C01(lstNumeroCuotas);
		return obtenerBytesArchivoXML(reporte);
	}

	private static byte[] obtenerBytesArchivoXML(Object clase) {
		byte[] respuesta = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clase.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ByteArrayOutputStream xmlStream = new ByteArrayOutputStream();
			m.marshal(clase, xmlStream);
			respuesta = xmlStream.toByteArray();
		} catch (Exception e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
		}
		return respuesta;
	}

	public static byte[] generarArchivoExcel(List<String[]> data, String sheetName, int indiceRow,
			String identificadorPlantilla) throws IOException, InvalidFormatException {
		// se carga la plantilla existente
		OPCPackage planilla = OPCPackage.open(new ByteArrayInputStream(obtenerPlanillaExcel(identificadorPlantilla)));
		XSSFWorkbook libro = new XSSFWorkbook(planilla);
		Sheet pagina = libro.getSheet(sheetName);
		// se obtiene la fila existente
		for (int i = 0; i <= indiceRow; i++) {
			if (pagina.getRow(i) == null) {
				pagina.createRow(i);
			}
		}
		Row existingRow = pagina.getRow(indiceRow);

		int indiceColumn = 0;
		int countRow = indiceRow;
		// Generación de cada registro por fila
		for (Object[] items : data) {
			Cell celdaReporte = null;
			Row filaReporte = null;
			indiceColumn = 0;
			filaReporte = pagina.createRow(countRow);
			for (Object dato : items) {

				Integer numero = null;
				if (dato != null && dato instanceof BigDecimal) {
					numero = BigDecimal.valueOf(Double.valueOf(dato.toString())).toBigInteger().intValue();
				} else if (dato != null && dato instanceof Integer) {
					numero = Integer.valueOf(dato.toString());
				}

				if (countRow == indiceRow) { // se conserva el mismo estilo de la primera fila
					if (existingRow.getCell(indiceColumn) != null) {
						celdaReporte = existingRow.getCell(indiceColumn);
						if (numero != null) {
							celdaReporte.setCellType(CellType.NUMERIC);
							celdaReporte.setCellValue(numero.longValue());

						} else {
							celdaReporte.setCellValue(dato != null ? dato.toString() : "");
						}

					} else {
						celdaReporte = filaReporte.createCell(indiceColumn);
						if (numero != null) {
							celdaReporte.setCellType(CellType.NUMERIC);
							celdaReporte.setCellValue(numero.longValue());

						} else {
							celdaReporte.setCellValue(dato != null ? dato.toString() : "");
						}
					}
				} else {
					celdaReporte = filaReporte.createCell(indiceColumn);
					if (numero != null) {
						celdaReporte.setCellType(CellType.NUMERIC);
						celdaReporte.setCellValue(numero.longValue());

					} else {
						celdaReporte.setCellValue(dato != null ? dato.toString() : "");
					}
					// se obtiene el estilo de cada columna de la fila existente en el reporte
					/*
					 * if (existingRow.getCell(indiceColumn) != null &&
					 * existingRow.getCell(indiceColumn).getCellStyle() != null) { CellStyle
					 * currentStyle = existingRow.getCell(indiceColumn).getCellStyle(); //se aplica
					 * el estilo anterior celdaReporte.setCellStyle(currentStyle); }
					 */
				}
				indiceColumn++;
			}
			// se aumenta el número de la fila para almacenar el otro registro
			countRow++;
		}

		ByteArrayOutputStream archivo = new ByteArrayOutputStream();
		try {
			// Almacenamos el libro de Excel via ese flujo de datos
			libro.write(archivo);
			libro.close();
		} catch (IOException e) {
			throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO,
					e + " En el cierre del archivo para la generación del Excel");
		}
		return archivo.toByteArray();
	}

	/**
	 * Metodo que permite obtener un archivo a partir de su identificador en el ECM
	 * 
	 * @param identificadorArchivo Identificador del archivo dentro del ECM
	 * @return DTO con información del archivo
	 */
	private static byte[] obtenerPlanillaExcel(String identificadorArchivo) {
		ObtenerArchivo archivo = new ObtenerArchivo(identificadorArchivo);
		archivo.execute();
		return archivo.getResult().getDataFile();
	}

	private static String isNull(Object cadena, String valorDefecto) {
		return (cadena == null) ? valorDefecto : cadena.toString();
	}

}
