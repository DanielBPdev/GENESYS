package com.asopagos.aportes.masivos.service.load.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.aportes.masivos.dto.ResultadoAporteMasivoDTO;
import com.asopagos.aportes.masivos.service.constants.ArchivoCampoMasivoConstante;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;  
import java.util.Date;


/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:498 </br>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class AportesMasivosRecaudoManualPersistLine implements IPersistLine {

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	@Override
	public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
		LineArgumentDTO lineArgumentDTO;
		Map<String, Object> line;
		for (int i = 0; i < lines.size(); i++) {
			lineArgumentDTO = lines.get(i);
			try {

				line = lineArgumentDTO.getLineValues();

				for (Map.Entry<String, Object> entry : line.entrySet()) {
					System.out.println(entry.getKey() + ":" + entry.getValue());
				}

				ResultadoAporteMasivoDTO resValidacion = new ResultadoAporteMasivoDTO();

				resValidacion.setEcmIdentificador(lineArgumentDTO.getContext()
							.get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE).toString());
				
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE) != null) {
					// Parsear TipoIdentificacionEnum
					String tipoIdentificacion = line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE).toString();
					TipoIdentificacionEnum tipoId = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
					resValidacion.setTipoIdentificacionAportante(tipoId);
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE) != null) {
					// String
					resValidacion.setNumeroIdentificacionAportante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE).toString());
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_MOTIVO_RAZON_SOCIAL) != null) {
					resValidacion.setRazonSocial(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_MOTIVO_RAZON_SOCIAL).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ID_DEPARTAMENTO) != null) {
					try {	
						Long idDepartamento = Long.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ID_DEPARTAMENTO).toString());
						resValidacion.setIdDepartamento(idDepartamento);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ID_MUNICIPIO) != null) {
					try {
						
						Long idMunicipio = Long.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ID_MUNICIPIO).toString());
						resValidacion.setIdMunicipio(idMunicipio);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO) != null) {
					try {
						Date fecha = new Date();
						String sDate1=line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO).toString();
						fecha=new SimpleDateFormat("yyyy-MM").parse(sDate1);
						resValidacion.setPeriodoPago(fecha);

					} catch (Exception e) {
						System.out.println("Error en el campo periodo pago");
					}
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_COTIZANTE) != null) {
					try {
						Integer tipoCotizanteInt = Integer.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_COTIZANTE).toString());
						TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizanteInt);
						resValidacion.setTipoCotizante(tipoCotizante);	
					} catch (Exception e) {
						// TODO: handle exception
					}
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOCUMENTO_COTIZANTE) != null) {
					TipoIdentificacionEnum tipoIdCot = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOCUMENTO_COTIZANTE).toString());
					resValidacion.setTipoIdentificacionCotizante(tipoIdCot);
					// TipoIdentificacionEnum
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOCUMENTO_COTIZANTE) != null) {
					resValidacion.setNumeroDocumentoCotizante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOCUMENTO_COTIZANTE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PRIMER_NOMBRE_COTIZANTE) != null) {
					resValidacion.setPrimerNombreCotizante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PRIMER_NOMBRE_COTIZANTE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SEGUNDO_NOMBRE_COTIZANTE) != null) {
					resValidacion.setSegundoNombreCotizante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SEGUNDO_NOMBRE_COTIZANTE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PRIMER_APELLIDO_COTIZANTE) != null) {
					resValidacion.setPrimerApellidoCotizante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PRIMER_APELLIDO_COTIZANTE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SEGUNDO_APELLIDO_COTIZANTE) != null) {
					resValidacion.setSegundoApellidoCotizante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SEGUNDO_APELLIDO_COTIZANTE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_FECHA_RECEPCION_APORTE) != null) {
					Long fechaRecepcion = null;
					Date fecha = new Date();
					try {
						String sDate1=line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_FECHA_RECEPCION_APORTE).toString();
						fecha=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
					} catch (Exception e) {
						System.out.println("Error en el campo de la fecha de recepcion del aporte");
					}
					fechaRecepcion = fecha.getTime();
					resValidacion.setFechaRecepcionAporte(fecha);
					// Long
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_FECHA_PAGO) != null) {
					Long fechaRecepcion = null;
					Date fecha = new Date();
					try {
						String sDate1=line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_FECHA_PAGO).toString();
						fecha=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
					} catch (Exception e) {
						System.out.println("Error en el campo de la fecha de pago");
					}
					fechaRecepcion = fecha.getTime();
					resValidacion.setFechaDePago(fecha);
					// Long
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_CONCEPTO_DE_PAGO) != null) {
					try {	
						resValidacion.setConceptoDePago(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_CONCEPTO_DE_PAGO).toString());
						//Short conceptoDePago = Short.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_CONCEPTO_DE_PAGO).toString());
						//resValidacion.setConceptoDePago(conceptoDePago);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IBC) != null) {
					try {		
						BigDecimal ibc = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IBC).toString());
						resValidacion.setIbc(ibc);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ING) != null) {
					resValidacion.setIng(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_ING).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_RET) != null) {
					resValidacion.setRet(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_RET).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IRL) != null) {
					resValidacion.setIrl(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IRL).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VSP) != null) {
					resValidacion.setVsp(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VSP).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VST) != null) {
					resValidacion.setVst(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VST).toString());
					// String
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SLN) != null) {
					resValidacion.setSln(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SLN).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IGE) != null) {
					resValidacion.setIge(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_IGE).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_LMA) != null) {
					resValidacion.setLma(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_LMA).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VAC) != null) {
					resValidacion.setVac(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VAC).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SALARIO_BASICO) != null) {
					BigDecimal salario = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SALARIO_BASICO).toString());
					resValidacion.setSalarioBasico(salario);
					// BigDecimal
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_COTIZADOS) != null) {
					Short diasCotizados = Short.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_COTIZADOS).toString());
					resValidacion.setDiasCotizados(diasCotizados);
					// Integer
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_MORA) != null) {
					Integer diasMora = Integer.parseInt(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_MORA).toString());
					resValidacion.setDiasMora(diasMora);
					// Integer

				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TARIFA) != null) {
					try {		
						BigDecimal tarifa = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TARIFA).toString());
						resValidacion.setTarifa(tarifa);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// BigDecimal
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DE_HORAS_LABORADAS) != null) {
					try {
						Integer numeroDeHorasLaboradas = Integer.parseInt(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DE_HORAS_LABORADAS).toString());
						resValidacion.setNumeroDeHorasLaboradas(numeroDeHorasLaboradas);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					// Integer
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_APORTE_OBLIGATORIO) != null) {
					try {
						
						BigDecimal aporteObligatorio = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_APORTE_OBLIGATORIO).toString());
						resValidacion.setAporteObligatorio(aporteObligatorio);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// BigDecimal
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VALOR_INTERESES) != null) {
					try {
						
						BigDecimal valorIntereses = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_VALOR_INTERESES).toString());
						resValidacion.setValorIntereses(valorIntereses);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// BigDecimal
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TOTAL_APORTE) != null) {
					try {
						
						BigDecimal totalAporte = new BigDecimal(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TOTAL_APORTE).toString());
						resValidacion.setTotalAporte(totalAporte);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// BigDecimal
				}

				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_APORTANTE) != null) {
					try {
						
						TipoSolicitanteMovimientoAporteEnum tipoAportante = TipoSolicitanteMovimientoAporteEnum.valueOf(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_APORTANTE).toString().toUpperCase());
						resValidacion.setTipoAportante(tipoAportante);
					} catch (Exception e) {
						// TODO: handle exception
					}
					// BigDecimal
				}

				resValidacion.setEcmIdentificador(lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE).toString());
				

				((List<ResultadoAporteMasivoDTO>) lineArgumentDTO.getContext()
							.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resValidacion);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error en la carga de aportes masivos" + e.getMessage());
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
	 */
	@Override
	public void setRollback(EntityManager em) throws FileProcessingException {
		System.out.println("Error en la carga de aportes masivos");
	    throw new UnsupportedOperationException();
	}
}