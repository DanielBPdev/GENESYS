package com.asopagos.afiliaciones.personas.web.load;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.afiliacion.personas.web.constants.CamposArchivoConstantes;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import com.asopagos.dto.InformacionLaboralTrabajadorDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.cargaMultiple.AfiliarTrabajadorCandidatoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.afiliaciones.personas.web.clients.BuscarCodigoPais;
import com.asopagos.afiliaciones.personas.web.clients.BuscarIdOcupacion;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;

/**
 * <b>Descripcion:</b> Clase que contiene las constantes con los nombre de los
 * campos que se encuentran en el archivo de solicitud de aficliación multiple
 * <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class TrabajadorDependientePersistLine implements IPersistLine {

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(TrabajadorDependientePersistLine.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	@Override
	public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
		logger.info("Inicia persistLine en TrabajadorDependientePersistLine");
		AfiliarTrabajadorCandidatoDTO inDTO = null;
                
		if (lines.size() <= ArchivoMultipleCampoConstants.LONGITUD_REGISTROS) {
			logger.info("TrabajadorDependientePersistLine: Registros menores o igual a total registros");
			for (int i = 0; i < lines.size(); i++) {
				if (i > 0) {
					LineArgumentDTO lineArgumentDTO = lines.get(i);
					try {

						List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
								.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
						if (hallazgos.isEmpty()) {
							// se simula la captura de los datos desde la
							// pantalla
							// para
							// poder utilizar el mismo servicios en el momento
							// de
							// persistir
							// la información completa.
							inDTO = simularCapturaDatosPantalla(lineArgumentDTO);
                                                        logger.info("LINA:TrabajadorDependientePersistLine;  inDTO " + inDTO.getInformacionLaboralTrabajador().getMunicipioDesempenioLabores());

							((List<AfiliarTrabajadorCandidatoDTO>) lineArgumentDTO.getContext()
									.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(inDTO);
						}
					} catch (Exception e) {
						((List<AfiliarTrabajadorCandidatoDTO>) lineArgumentDTO.getContext()
								.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(inDTO);
					}
				}
			}
		} else {
			logger.info("Finaliza persistLine en TrabajadorDependientePersistLine");
			return;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
	 */
	@Override
	public void setRollback(EntityManager em) throws FileProcessingException {
		// TODO Auto-generated method stub
	}

	/**
	 * Método que simula la captura de la pantalla para mantener uniformidad en
	 * el llamado de los datos temporales que deben ser mostrados en la HU-369
	 * 
	 * @param lineArgumentDTO
	 * @return
	 */
	private AfiliarTrabajadorCandidatoDTO simularCapturaDatosPantalla(LineArgumentDTO lineArgumentDTO) {
		AfiliarTrabajadorCandidatoDTO atcDTO = new AfiliarTrabajadorCandidatoDTO();
		AfiliadoInDTO afiliadoDTO = simularDatosTab1(lineArgumentDTO);
		IdentificacionUbicacionPersonaDTO identificacionUbicacion = simularDatosTab2(lineArgumentDTO,
				afiliadoDTO.getPersona());
		InformacionLaboralTrabajadorDTO informacionLaboral = simularDatosTab3(lineArgumentDTO);
		afiliadoDTO.getPersona().setUbicacionDTO(identificacionUbicacion.getUbicacion());
		afiliadoDTO.setClaseTrabajador(informacionLaboral.getClaseTrabajador());
		afiliadoDTO.setFechaInicioContrato(informacionLaboral.getFechaInicioContrato());
		atcDTO.setAfiliadoInDTO(afiliadoDTO);
		atcDTO.setIdentificadorUbicacionPersona(identificacionUbicacion);
		atcDTO.setInformacionLaboralTrabajador(informacionLaboral);
		atcDTO.setCodigoCargueMultiple(
				(Long) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE));
		return atcDTO;
	}

	/**
	 * Método que simula la captura de la pantalla para mantener uniformidad en
	 * el llamado de los datos temporales que deben ser mostrados en la HU-369
	 * pestaña 1 datos básicos
	 * 
	 * @param lineArgumentDTO
	 * @param IdEmpleador
	 * @return AfiliadoInDTO
	 * @throws ParseException
	 */
	private AfiliadoInDTO simularDatosTab1(LineArgumentDTO lineArgumentDTO) {
		Map<String, Object> line = lineArgumentDTO.getLineValues();
		Long idEmpleador = (Long) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_EMPLEADOR);
		PersonaDTO persona = new PersonaDTO();
		persona.setTipoIdentificacion(GetValueUtil
				.getTipoIdentificacionDescripcion((String) line.get(CamposArchivoConstantes.TIPO_IDENTIFICACION)));
		persona.setNumeroIdentificacion((String) line.get(CamposArchivoConstantes.NUMERO_IDENTIFICACION));
		persona.setPrimerNombre((String) line.get(CamposArchivoConstantes.PRIMER_NOMBRE).toString().toUpperCase());
		String segundoNombre = null;
		if (line.get(CamposArchivoConstantes.SEGUNDO_NOMBRE) != null) {
			segundoNombre = (String) line.get(CamposArchivoConstantes.SEGUNDO_NOMBRE).toString().toUpperCase();
		}
		persona.setSegundoNombre(segundoNombre);
		persona.setPrimerApellido((String) line.get(CamposArchivoConstantes.PRIMER_APELLIDO).toString().toUpperCase());
		String segundoApellido = null;
		if (line.get(CamposArchivoConstantes.SEGUNDO_APELLIDO) != null) {
			segundoApellido = (String) line.get(CamposArchivoConstantes.SEGUNDO_APELLIDO).toString().toUpperCase();
		}
		persona.setSegundoApellido(segundoApellido);
		Date fechaExpDoc = null;
		if (line.get(CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD) != null) {
			String strFechaExpDoc = line.get(CamposArchivoConstantes.FECHA_EXP_DOC_IDENTIDAD).toString();
			try {
				fechaExpDoc = new Date(CalendarUtils.convertirFechaDate(strFechaExpDoc));
				persona.setFechaExpedicionDocumento(fechaExpDoc);
			} catch (ParseException e) {
				persona.setFechaExpedicionDocumento(null);
			}
		}
		persona.setGenero(GetValueUtil.getGeneroDescripcion((String) line.get(CamposArchivoConstantes.GENERO)));
		String strFechaNacimiento = line.get(CamposArchivoConstantes.FECHA_NACIMIENTO).toString();
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = new Date(CalendarUtils.convertirFechaDate(strFechaNacimiento));
			persona.setFechaNacimiento(fechaNacimiento.getTime());
		} catch (ParseException e) {
			persona.setFechaNacimiento(null);
		}
		persona.setCabezaHogar(ArchivoMultipleCampoConstants.SI
				.equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.CABEZA_HOGAR))));
		persona.setAutorizaUsoDatosPersonales(ArchivoMultipleCampoConstants.SI
				.equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.AUTORIZA_USO_DATOS_PERSONALES))));
		persona.setEstadoCivil(
				GetValueUtil.getEstadoCivilDescripcion((String) line.get(CamposArchivoConstantes.ESTADO_CIVIL)));
		persona.setResideSectorRural(ArchivoMultipleCampoConstants.SI
				.equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.RESIDE_SECTOR_RURAL))));
		persona.setAutorizacionEnvioEmail(ArchivoMultipleCampoConstants.SI
                .equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO))));
		String strSalarioMensual = line.get(CamposArchivoConstantes.VALOR_SALARIO_MENSUAL).toString();
		BigDecimal salarioMensual = new BigDecimal(strSalarioMensual);
                
                
                if (line.get(CamposArchivoConstantes.ORIENTACION_SEXUAL) != null) {
                    String orientacionSexual = (String) line.get(CamposArchivoConstantes.ORIENTACION_SEXUAL).toString();
                    OrientacionSexualEnum resultOrientacionEnum = null;
                    for (OrientacionSexualEnum orientacionEnum : OrientacionSexualEnum.values()) {
                        if (orientacionEnum.getDescripcion().equals(orientacionSexual)) {
                            resultOrientacionEnum = orientacionEnum;
                            break;
                        }
                    }                    
                    persona.setOrientacionSexual(resultOrientacionEnum);

                }

                if (line.get(CamposArchivoConstantes.FACTOR_VULNERABILIDAD) != null) {
                    String vulnerabilidad = (String) line.get(CamposArchivoConstantes.FACTOR_VULNERABILIDAD).toString();
                    FactorVulnerabilidadEnum resultFactorVul = null;
                    for (FactorVulnerabilidadEnum factorVulnerabilidad : FactorVulnerabilidadEnum.values()) {
                        if (factorVulnerabilidad.getDescripcion().equals(vulnerabilidad)) {
                            resultFactorVul = factorVulnerabilidad;
                            break;
                        }
                    }
                    
                    persona.setFactorVulnerabilidad(resultFactorVul);
                }
                
                if (line.get(CamposArchivoConstantes.PERTENENCIA_ETNICA) != null) {
			String etnia = (String) line.get(CamposArchivoConstantes.PERTENENCIA_ETNICA).toString();
                        PertenenciaEtnicaEnum pertenenciaEtnicaEnumResul = null;
                        for (PertenenciaEtnicaEnum etniaEnum : PertenenciaEtnicaEnum.values()) {
                            if (etniaEnum.getDescripcion().equals(etnia)) {
                                 pertenenciaEtnicaEnumResul = etniaEnum;
                                 break;
                            }
                        }
                        
                    persona.setPertenenciaEtnica(pertenenciaEtnicaEnumResul);
                }
                
                if (line.get(CamposArchivoConstantes.PAIS_RESIDENCIA) != null) {
                    String pais = (String) line.get(CamposArchivoConstantes.PAIS_RESIDENCIA).toString();
                    RespuestaValidacionArchivoDTO respuesta = new RespuestaValidacionArchivoDTO();

                    
                    BuscarCodigoPais buscarCodigoPais = new BuscarCodigoPais(pais);
                    buscarCodigoPais.execute();
                    respuesta = (RespuestaValidacionArchivoDTO) buscarCodigoPais.getResult();
                    
                    long codigoPais = Long.parseLong(respuesta.getMensaje());
                    persona.setIdPaisResidencia(codigoPais);
		}
                
                if (line.get(CamposArchivoConstantes.OCUPACION) != null) {
			String ocupacion = (String) line.get(CamposArchivoConstantes.OCUPACION).toString();

                        RespuestaValidacionArchivoDTO respuestaO = new RespuestaValidacionArchivoDTO();
                        BuscarIdOcupacion buscarIdOcupacion = new BuscarIdOcupacion(ocupacion);
                        buscarIdOcupacion.execute();
                        respuestaO = (RespuestaValidacionArchivoDTO) buscarIdOcupacion.getResult();
                        
                        int codigoOcupacion = Integer.parseInt(respuestaO.getMensaje());
                        
                        persona.setIdOcupacionProfesion(codigoOcupacion);
                
                }
                
                if (line.get(CamposArchivoConstantes.NIVEL_ESCOLARIDAD) != null) {
			String nivelEscolar = (String) line.get(CamposArchivoConstantes.NIVEL_ESCOLARIDAD).toString();
                        NivelEducativoEnum nivelEducativoEnumResult = null;
                        for (NivelEducativoEnum educacion : NivelEducativoEnum.values()) {
                            if (educacion.getDescripcion().equals(nivelEscolar)) {
                                nivelEducativoEnumResult = educacion;
                                break;
                            }
                        }
                        
                        persona.setNivelEducativo(nivelEducativoEnumResult);
		}
                
		AfiliadoInDTO afiliado = new AfiliadoInDTO();
		afiliado.setValorSalarioMesada(salarioMensual);
		afiliado.setPersona(persona);
		afiliado.setIdEmpleador(idEmpleador);
		afiliado.setTipoAfiliado(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE);
		return afiliado;
	}

	/**
	 * Método que simula la captura de la pantalla para mantener uniformidad en
	 * el llamado de los datos temporales que deben ser mostrados en la HU-369
	 * pestaña 2 datos de ubicación
	 * 
	 * @param lineArgumentDTO
	 * @param IdEmpleador
	 * @return
	 * @throws ParseException
	 */
	private IdentificacionUbicacionPersonaDTO simularDatosTab2(LineArgumentDTO lineArgumentDTO, PersonaDTO persona) {
		Map<String, Object> line = lineArgumentDTO.getLineValues();
		List<Departamento> lstDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
				.get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
		String nombreDepartamento = ((String) line.get(CamposArchivoConstantes.DEPARTAMENTO)).toUpperCase();
		Departamento departamento = GetValueUtil.getDepartamentoNombre(lstDepartamento, nombreDepartamento);
		List<Municipio> listaMunicipios = (List<Municipio>) lineArgumentDTO.getContext()
				.get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
		Municipio municipio = GetValueUtil.getMunicipioNombre(listaMunicipios,
				((String) line.get(CamposArchivoConstantes.MUNICIPIO)).toUpperCase(), departamento.getIdDepartamento().shortValue());
		
      
                // Datos de ubicación
		UbicacionDTO ubicacion = new UbicacionDTO();
		ubicacion.setIdDepartamento(municipio.getIdDepartamento());
		ubicacion.setIdMunicipio(municipio.getIdMunicipio());
		String telefonoCelular = null;
		if (line.get(CamposArchivoConstantes.TELEFONO_CELULAR) != null) {
			telefonoCelular = (String) line.get(CamposArchivoConstantes.TELEFONO_CELULAR);
		}
		ubicacion.setTelefonoCelular(telefonoCelular);
		String correoElectronico = null;
		if (line.get(CamposArchivoConstantes.CORREO_ELECTRONICO) != null) {
			correoElectronico = (String) line.get(CamposArchivoConstantes.CORREO_ELECTRONICO);
		}
		ubicacion.setCorreoElectronico(correoElectronico);
		String telefonoFijo = null;
		if (line.get(CamposArchivoConstantes.TELEFONO_FIJO) != null) {
			telefonoFijo = (String) line.get(CamposArchivoConstantes.TELEFONO_FIJO);
		}
		ubicacion.setTelefonoFijo(telefonoFijo);
		ubicacion.setAutorizacionEnvioEmail(ArchivoMultipleCampoConstants.SI
				.equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.AUTORIZACION_ENVIOCORREO))));
		ubicacion.setDireccion((String) line.get(CamposArchivoConstantes.DIRECCION_RESIDENCIA));
                
		// Datos de la persona y la ubicación
		IdentificacionUbicacionPersonaDTO inDTO = new IdentificacionUbicacionPersonaDTO();
		inDTO.setAutorizacionUsoDatosPersonales(ArchivoMultipleCampoConstants.SI
                .equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.AUTORIZA_USO_DATOS_PERSONALES))));
		inDTO.setResideSectorRural(ArchivoMultipleCampoConstants.SI
                .equalsIgnoreCase(((String) line.get(CamposArchivoConstantes.RESIDE_SECTOR_RURAL))));
		inDTO.setPersona(persona);
		inDTO.setUbicacion(ubicacion);
                
                if (line.get(CamposArchivoConstantes.MUNICIPIO_LABOR) != null) {                        
                    String nombreMunicipio = ((String) line.get(CamposArchivoConstantes.MUNICIPIO_LABOR)).toUpperCase();
		    short municipioId = 0;		
                    for (Municipio municipioValor : listaMunicipios) {
                        if (municipioValor.getNombre().equals(nombreMunicipio)) {
                            municipioId = municipioValor.getIdMunicipio();
                            break;
                        }
                    }
                                
                    inDTO.setMunicipioDesempenioLabores(municipioId);
                }
                
		return inDTO;
	}

	/**
	 * Método que simula la captura de la pantalla para mantener uniformidad en
	 * el llamado de los datos temporales que deben ser mostrados en la HU-369
	 * pestaña 2 datos de información laboral del trabajador
	 * 
	 * @param lineArgumentDTO
	 * @param IdEmpleador
	 * @return
	 * @throws ParseException
	 */
	private InformacionLaboralTrabajadorDTO simularDatosTab3(LineArgumentDTO lineArgumentDTO) {
		Map<String, Object> line = lineArgumentDTO.getLineValues();
		InformacionLaboralTrabajadorDTO inDTO = new InformacionLaboralTrabajadorDTO();
		inDTO.setClaseTrabajador(GetValueUtil
				.getClaseTrabajadorDescripcion((String) line.get(CamposArchivoConstantes.CLASE_TRABAJADOR)));
		String strSalarioMensual = line.get(CamposArchivoConstantes.VALOR_SALARIO_MENSUAL).toString();
		BigDecimal salarioMensual = new BigDecimal(strSalarioMensual);
		inDTO.setValorSalario(salarioMensual);
		String strFechaInicioLaboresEmpleador = line.get(CamposArchivoConstantes.FECHA_INICIO_LABORES_CON_EMPLEADOR)
				.toString();
		Long fInicioLaboresEmpleador = null;
                
                 if (line.get(CamposArchivoConstantes.MUNICIPIO_LABOR) != null) {  
                     List<Municipio> listaMunicipios = (List<Municipio>) lineArgumentDTO.getContext()
				.get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                     
                    String nombreMunicipio = ((String) line.get(CamposArchivoConstantes.MUNICIPIO_LABOR)).toUpperCase();
		    short municipioId = 0;		
                    for (Municipio municipioValor : listaMunicipios) {
                        if (municipioValor.getNombre().equals(nombreMunicipio)) {
                            municipioId = municipioValor.getIdMunicipio();
                            break;
                        }
                    }
                                
                    inDTO.setMunicipioDesempenioLabores(municipioId);
                }
                 
		try {
			fInicioLaboresEmpleador = CalendarUtils.convertirFechaDate(strFechaInicioLaboresEmpleador);
			inDTO.setFechaInicioContrato(new Date(fInicioLaboresEmpleador));
		} catch (ParseException e) {
			inDTO.setFechaInicioContrato(null);
		}
		return inDTO;
	}

}