package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.afiliaciones.clients.ConsultarUltimaClasificacionCategoria;
import com.asopagos.entidades.pagadoras.clients.ConsultarEntidadesByTipoAfiliacion;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.enumeraciones.afiliaciones.TipoAfiliacionEntidadPagadoraEnum;
import java.util.Objects;
import com.asopagos.entidades.pagadoras.dto.ConsultarEntidadPagadoraOutDTO;
import java.util.regex.*;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import java.math.BigDecimal;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.util.HashMap;


public class Ley2225LineValidator extends LineValidator{
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
	private static final ILogger logger = LogManager.getLogger(Ley2225LineValidator.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
			

        System.out.println("Nico estuvo aqui c: LINE");
		// String docEmpleador= (String) arguments.getContext().get("docEmpleador");
		// String tipoDocumneto= (String) arguments.getContext().get("tipoDocumneto");
		
		// logger.info("2G datos empleador idEmpleaador"+docEmpleador);
		// logger.info("2G datos empleador tipodocumento"+tipoDocumneto);

		lstHallazgos = new ArrayList<>();
                TipoIdentificacionEnum tipoIdentEnum = null;
		Map<String, Object> line = arguments.getLineValues();
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgosReal = (List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoPensionados25AniosConstants.LISTA_HALLAZGOS);
		try {
			System.out.println("Inicio de validador 25 años");
			System.out.println("tamaño lista hallazgos"+listaHallazgosReal.size());
			if (line.get(ArchivoPensionados25AniosConstants.TIPO_DOCUMENTO_PENSIONADO) != null) {
				TipoIdentificacionEnum tipoIdentificacion = null;	
				String tipoDocumentoEmpleador = ((String) line.get(ArchivoPensionados25AniosConstants.TIPO_DOCUMENTO_PENSIONADO))
						.toUpperCase();
						// TipoIdentificacionEmpleadorEnum tipoIdentEnum = null;
                logger.info("antes de  if" + tipoDocumentoEmpleador);
						if(tipoDocumentoEmpleador.length()>2){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"tipo de documento persona",
									"Longitud del tipo de documento superior"));
						}else if(!verificarEntero(tipoDocumentoEmpleador)){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"tipo de documento persona",
									"formato incorrecto"));
						}else { 
							tipoIdentificacion = verificarTipoDocumento(tipoDocumentoEmpleador);
							if(tipoIdentificacion == null){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"tipo de documento persona",
									"es invalido"));
							}/*else{
								String numeroDocumento = ((String) line.get(ArchivoPensionados25AniosConstants.NUMERO_DOCUMENTO_PENSIONADO))
									.toUpperCase();
                                List<String> validaciones = ejecutarMallaValidacionAfiliado(tipoIdentificacion,numeroDocumento);

								if(!validaciones.isEmpty()){
									for(String validacion : validaciones) {
                                     lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"tipo de documento persona",
										validacion));
									}
								}
							}*/
						}
						

					if (line.get(ArchivoPensionados25AniosConstants.NUMERO_DOCUMENTO_PENSIONADO) != null && tipoIdentificacion!= null) {
                        

                    

						String numeroDocumento = ((String) line.get(ArchivoPensionados25AniosConstants.NUMERO_DOCUMENTO_PENSIONADO))
								.toUpperCase();
		
								
									if(numeroDocumento.length() > 17){
										lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"numeroDocumento",
										"Longitud del número  documento excede la longitud permitida"));
									}else if(verificarNumeroDocumento(numeroDocumento,tipoIdentificacion)){
										lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
												"numeroDocumento",
												"Longitud del numeroDocumento erronea con respecto al tipo de documento"));
									
									}
									
						// logger.info(numeroDocumento);
						// logger.info(tipoIdentificacion);
						
					}else{
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"numeroDocumento",
							"numeroDocumento no presente en el archivo."));
					}
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"tipo de documento persona",
					"tipo de documento no presente en el archivo"));
			}

			if (line.get(ArchivoPensionados25AniosConstants.PRIMER_NOMBRE_PENSIONADO) != null) {
				String primerNombrePensionado = ((String) line.get(ArchivoPensionados25AniosConstants.PRIMER_NOMBRE_PENSIONADO))
						.toUpperCase();

						if(primerNombrePensionado.contains(" ")){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerNombrePensionado",
										"primer nombrePensionado contiene espacios"));
						}else if(primerNombrePensionado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerNombrePensionado",
										"Longitud del primer NombrePensionado superior"));
						}else if(!validarRegex(primerNombrePensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerNombrePensionado",
										"primer nombre pensionado contiene caracteres inválido"));
						}

			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"primerNombrePensionado",
					"primerNombrePensionado no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.SEGUNDO_NOMBRE_PENSIONADO) != null) {
				String segundoNombrePensionado = ((String) line.get(ArchivoPensionados25AniosConstants.SEGUNDO_NOMBRE_PENSIONADO))
						.toUpperCase();
						if(segundoNombrePensionado != null){
							if(segundoNombrePensionado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoNombrePensionado",
										"Longitud del segundoNombrePensionado superior"));
							}else if(!validarRegex(segundoNombrePensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoNombrePensionado",
										"segundoNombrepensionado contiene cartacteres inválidos"));
							}
						} 

			}
			if (line.get(ArchivoPensionados25AniosConstants.PRIMER_APELLIDO_PENSIONADO) != null) {
				String primerApellidoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.PRIMER_APELLIDO_PENSIONADO))
						.toUpperCase();
				
						if(primerApellidoPensionado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerApellidoPensionado",
										"Longitud del primer NombrePensionado superior"));
						}else if(!validarRegex(primerApellidoPensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerApellidoPensionado",
										"primerApellido Pensionado contiene cartacteres inválidos"));
						}

			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"primerApellidoPensionado",
					"primerApellidoPensionado no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.SEGUNDO_APELLIDO_PENSIONADO) != null) {
				String segundoApellidoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.SEGUNDO_APELLIDO_PENSIONADO))
						.toUpperCase();
						if(segundoApellidoPensionado != null){
							if(segundoApellidoPensionado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoApellidoPensionado",
										"Longitud del segundo apellidoPensionado superior"));
							}else if(!validarRegex(segundoApellidoPensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoApellidoPensionado",
										"segundoApellidoPensionado contiene cartacteres inválidos"));
							}
						} 

			}
			if (line.get(ArchivoPensionados25AniosConstants.FECHA_NACIMIENTO_PENSIONADO) != null) {
				String fechaNacimiento = ((String) line.get(ArchivoPensionados25AniosConstants.FECHA_NACIMIENTO_PENSIONADO))
						.toUpperCase();

				if(verificarEntero(fechaNacimiento)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"fechaNacimiento",
							"fechaNacimiento contiene caracteres no permitidos"));
				}else if(fechaNacimiento.length()>8){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"fechaNacimiento",
							"Longitud del fechaNacimiento superior"));
				}

				
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"fechaNacimiento",
					"fechaNacimiento no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO) != null) {
				String fechaRecepcion = ((String) line.get(ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO))
						.toUpperCase();

				 if(verificarEntero(fechaRecepcion)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"fechaRecepcion",
							"fechaRecepcion contiene caracteres no permitidos"));
				}else if(fechaRecepcion.length()>8){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"fechaRecepcion",
							"Longitud del fechaRecepcion superior"));
				}

				
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"fechaRecepcion",
					"fechaRecepcion no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.DEPARTAMENTO_PENSIONADO) != null) {
				String departamentoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.DEPARTAMENTO_PENSIONADO))
						.toUpperCase();
				if(departamentoPensionado.length()>2){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"departamentoPensionado",
							"Longitud del departamentoPensionado superior"));
				}else{
					if(verificarDepartamento(departamentoPensionado, arguments)){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"departamentoPensionado",
							"Departamento no existente en parametrización"));
					}
				}
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"departamentoPensionado",
							"departamentoPensionado inválido o no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.MUNICIPIO_PENSIONADO) != null) {
				String municipioPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.MUNICIPIO_PENSIONADO))
						.toUpperCase();
						
				if(municipioPensionado.length()>5){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"municipioPensionado",
							"Longitud del municipioPensionado superior"));
				}else{
					if(verificarMunicipio(municipioPensionado, arguments)){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"municipioPensionado",
							"Municipio no existente en parametrización"));
					}
				}
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"municipioPensionado",
							"municipioPensionado inválido o no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.DIRECCION_PENSIONADO) != null) {
				String direccionPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.DIRECCION_PENSIONADO))
						.toUpperCase();
				if(direccionPensionado.length()<5 || direccionPensionado.length()>100){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionPensionado",
							"Longitud del direccionPensionado erronea"));
				}
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionPensionado",
							"direccionPensionado inválido o no presente en el archivo"));
			}
			if (line.get(ArchivoPensionados25AniosConstants.GENERO_PENSIONADO) != null) {
				String generoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.GENERO_PENSIONADO))
						.toUpperCase();
				
					if(generoPensionado.length()>1){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"generoPensionado",
								"Longitud del generoPensionado superior"));
								
					}else if(!verificarEntero(generoPensionado)){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"generoPensionado",
								"formato generoPensionado"));
					}else{
						boolean contieneGenero = false;
						for (GeneroEnum genero : GeneroEnum.values()) {
							logger.info("generoPensionado"+ generoPensionado);	
							logger.info("genero.getCodigo()"+ genero.getCodigo());	
							logger.info("genero.getCodigo().equals(generoPensionado)"+ genero.getCodigo().equals(generoPensionado));	

							if (genero.getCodigo().equals(generoPensionado)) {
								logger.info("genero.getCodigo().equals(generoPensionado) 8)"+ genero.getCodigo().equals(generoPensionado));	

								contieneGenero = true;
								break; 
							}
						}
						
							if (!contieneGenero){//obtener tipo GeneroEnum
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"generoPensionado",
										""));
							}
						
							
						
					} 
					 
				
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"generoPensionado",
					"generoPensionado no presente en el archivo"));
			}

			if (line.get(ArchivoPensionados25AniosConstants.ESTADO_CIVIL_PENSIONADO) != null) {
				String estadoCivilPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.ESTADO_CIVIL_PENSIONADO));
					logger.info("estadoCivilPensionado"+ estadoCivilPensionado);	
				

							if(estadoCivilPensionado.length()>1){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"estadoCivilPensionado",
										"Longitud del estado civilPensionado superior"));
							}else if(verificarEntero(estadoCivilPensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"estadoCivilPensionado",
										"formato incorrecto"));
							}else {
								int EnteroestadoCivil = Integer.parseInt(estadoCivilPensionado);
								logger.info("EnteroestadoCivil"+ EnteroestadoCivil);	

								if (EnteroestadoCivil<=0 || EnteroestadoCivil>6) { 
									
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"estadoCivilPensionado",
											""));
								}
							}
							
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
					"estadoCivilPensionado",
					"estado civilPensionado no presente en el archivo"));
			}

            if (line.get(ArchivoPensionados25AniosConstants.TELEFONO_PENSIONADO) != null) {
				String telefonoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.TELEFONO_PENSIONADO))
						.toUpperCase();
						
							if(telefonoPensionado.length()!=7){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"telefonoPensionado",
										"Longitud del telefonoPensionado superior o menos a 7"));
							}else if(verificarEntero(telefonoPensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"telefonoPensionado",
										"formato incorrecto, se esperaba entrada numerica"));
							}else{
								if (todosCaracteresIguales(telefonoPensionado)) {
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"telefonoPensionado",
										"formato incorrecto, numeros totalmente iguales o seguidos"));
								}                
							} 
							
						
			}else{
				if (line.get(ArchivoPensionados25AniosConstants.TELEFONO_PENSIONADO) == null){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						"telefonoPensionado",
						"telefonoPensionado no presente en el archivo"));
					
				}
			}
            if (line.get(ArchivoPensionados25AniosConstants.CELULAR_PENSIONADO) != null) {
				String celularPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.CELULAR_PENSIONADO))
						.toUpperCase();
						
							if(celularPensionado.length()>10){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"celularPensionado",
										"Longitud del celularPensionado superior"));
							}else if(verificarEntero(celularPensionado)){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"celularPensionado",
										"formato incorrecto, se esperaba entrada numerica"));
							}else if(celularPensionado.charAt(0) != '3'){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"celularPensionado",
										"formato incorrecto, no inicia con formato válido"));

							}else{	
								if (todosCaracteresIguales(celularPensionado)) {
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"celularPensionado",
										"formato incorrecto, números totalmente iguales o seguidos"));
								}                
							} 
							
						
			}else{
				if (line.get(ArchivoPensionados25AniosConstants.CELULAR_PENSIONADO) == null){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						"celularPensionado",
						"celularPensionado no presente en el archivo"));
					
				}
			}
            if (line.get(ArchivoPensionados25AniosConstants.CORREO_PENSIONADO) != null) {
				String correoPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.CORREO_PENSIONADO));
							if(correoPensionado.length()>50){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"correoPensionado",
										"Longitud del correoPensionado superior"));
							}else{	
								if (validarCorreo(correoPensionado.toLowerCase())) {
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"correoPensionado",
										"formato incorrecto de correo"));
								}                
							} 
			}
            if (line.get(ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO) != null) {
				String pagadorPensionPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO));
				logger.info("valor pagadorPensionPensionado 2g "+pagadorPensionPensionado);
						
							if(pagadorPensionPensionado.length()>2){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"pagadorPensionPensionado",
										"Longitud del pagadorPensionPensionado superior"));
							} else{
								// Validador de pagador de pension
								logger.info(pagadorPensionPensionado);
								
								ConsultarListaValores consultarEntidades = new ConsultarListaValores(46, null, null);
								consultarEntidades.execute();
								List<ElementoListaDTO> entidades = consultarEntidades.getResult();
								logger.info("entidades.getIdLista() "+entidades.size());
								boolean entidadEncontrada = false;

								for (ElementoListaDTO entidad : entidades) {
								logger.info("entidad.getAtributos() "+entidad.getValor());
									String identificador =  entidad.getIdentificador().toString();
									if(identificador.equals(pagadorPensionPensionado)){
									logger.info("entidad.getAtributos() "+entidad.getAtributos());

										entidadEncontrada = true;
										break;
			
									}
								}
								if (!entidadEncontrada) {
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
																"pagadorPensionPensionado",
																"Razón social no encontrada en las entidades pensionales"));
								}

							}							
							 
						
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
				"pagadorPensionPensionado",
				"pagadorPensionPensionado no presente en el archivo"));
			}

				
		
					BigDecimal porcentaje = new BigDecimal(0.5);
					BigDecimal valorMinimo = new  BigDecimal(100000);
					BigDecimal valorMaximo = new BigDecimal(500000000);
				if (line.get(ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO) != null) {
					String valorMesadaPensionalPensionado = ((String) line.get(ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO))
							.toUpperCase();
							
								if(valorMesadaPensionalPensionado.length()>20){
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"valorMesadaPensionalPensionado",
											"Longitud del valorMesadaPensionalPensionado superior"));
								}else {
									if(verificarEntero(valorMesadaPensionalPensionado)){
										lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
												"valorMesadaPensionalPensionado",
												"formato incorrecto"));
									}else {
										BigDecimal valorEntero = new BigDecimal(valorMesadaPensionalPensionado);
										System.out.println("valorEntero.compareTo(valorMaximo) "+valorEntero.compareTo(valorMaximo));
										System.out.print("valorEntero.compareTo(valorMinimo"+valorEntero.compareTo(valorMinimo));

										if(valorEntero.compareTo(valorMinimo)<0||valorEntero.compareTo(valorMaximo)>0){
											lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
												"valorMesadaPensionalPensionado",
												""));
										}
									}
	
								}
								
				}
				System.out.println("tamaño lista hallazgos"+listaHallazgosReal.size());
			 
		}finally {
			System.out.println("tamaño lista hallazgos final"+listaHallazgosReal.size());
			System.out.println("tamaño lista hallazgos final"+lstHallazgos.size());

			((List<Long>) arguments.getContext().get(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoPensionados25AniosConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
				((List<Long>) arguments.getContext().get(ArchivoPensionados25AniosConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}

    }
	

	private Boolean verificarEntero(String cadena) {
		
			try{
				Long valorEntero = Long.parseLong(cadena);
				return false;
			}catch (NumberFormatException e) {
				return true;
			}

	}
	private Boolean verificarDepartamento(String depar, LineArgumentDTO arguments) {
		logger.info("dep "+depar);
		List<Departamento> listaDepartamentos= ((List<Departamento>) arguments.getContext()
					.get(ArchivoPensionados25AniosConstants.LISTA_DEPARTAMENTO));
					for (Departamento departamento : listaDepartamentos) {
						logger.info("dep "+departamento.getCodigo());

						if(departamento.getCodigo().equals(depar)){
						return false;

						}
					}
			return true;		

	}
	private Boolean verificarMunicipio(String muni, LineArgumentDTO arguments) {
		logger.info("dep "+muni);

		List<Municipio> listaMunicipios= ((List<Municipio>) arguments.getContext()
					.get(ArchivoPensionados25AniosConstants.LISTA_MUNICIPIO));
					for (Municipio municipio : listaMunicipios) {
						logger.info("dep "+municipio.getCodigo());

						if(municipio.getCodigo().equals(muni)){
							return false;
						}
						
					}
			return true;		

	}



	private boolean todosCaracteresIguales(String campo) {
        for (int i = 1; i < campo.length(); i++) {
            if (campo.charAt(i) != campo.charAt(0)) {
                // return false;
					int valorActual = Character.getNumericValue(campo.charAt(i));
					int valorAnterior = Character.getNumericValue(campo.charAt(i - 1));
		
					if (valorActual != valorAnterior + 1) {
						return false;
					}
            }
        }
        return true;
    }

	private Boolean verificarLong(String cadena) {
		try {
			Long valorEntero = Long.parseLong(cadena);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}

    private TipoIdentificacionEnum verificarTipoDocumento(String tipoDocumento) {

			try {
				TipoIdentificacionEnum resultTipoIdentificacionEnum = null;
				for (TipoIdentificacionEnum tipoIdentificacionEnum : TipoIdentificacionEnum.values()) {
					if (tipoIdentificacionEnum.getValorEnPILA().equals(tipoDocumento) ) {
						resultTipoIdentificacionEnum = tipoIdentificacionEnum;
						break;
					}
				}
				if(resultTipoIdentificacionEnum != null && !TipoIdentificacionEnum.NIT.equals(resultTipoIdentificacionEnum)){
					return resultTipoIdentificacionEnum;
				}else{
					return null;
				}
			} catch (Exception e) {
				System.out.println("Return true tipoAportante");
				return null;
			}

	}

	private Boolean validarCorreo(String correo){

		if(correo.contains("@")){
			logger.info("contiene @");
			int aux = correo.indexOf("@");
			String dominio = correo.substring(aux);
			logger.info("contiene dominio "+dominio);
			if(dominio.contains(".com")){
			logger.info("contiene .com");

				return false;
			}
		}

		return true;
	}
	private Boolean validarRegex(String valor){
        String regex = "^[A-Z0-9ÁÉÊÍÓÚÄÜÀÈÌÒÙ\"%&()+-.<=>?_`{}~¢¤¦μ¯´¾¨ ']*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(valor);
        return matcher.matches();
	}

    private Boolean verificarNumeroDocumento(String numeroDocumento, TipoIdentificacionEnum tipoDocumento) {
		logger.info("Num doc: " + numeroDocumento);
		logger.info("tipoDocumento doc: " + tipoDocumento);
			if(tipoDocumento.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)){
				logger.info("entra cc");
				logger.info("entra cc : "+numeroDocumento.length());
				if(numeroDocumento.length()>10){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
				
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)){
				if(numeroDocumento.length()>7){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)){
				if(numeroDocumento.length()<10 || numeroDocumento.length()>11){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.PASAPORTE)){
				if(numeroDocumento.length()>16){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.CARNE_DIPLOMATICO)){
				if(numeroDocumento.length()>15){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.SALVOCONDUCTO)){
				if(numeroDocumento.length()>15){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA)){
				if(numeroDocumento.length()>16){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}else if(tipoDocumento.equals(TipoIdentificacionEnum.PERM_PROT_TEMPORAL)){
				if(numeroDocumento.length()>8){
					return Boolean.TRUE;
				}else{
					return Boolean.FALSE;
				}
			}
			return Boolean.TRUE;
	}


    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
            String error = Interpolator.interpolate(ArchivoPensionados25AniosConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
            ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
            hallazgo.setNumeroLinea(lineNumber);
            hallazgo.setNombreCampo(campo);
            hallazgo.setError(error);
             return hallazgo;
    }
	// TODO 001: Mover metodo ejecutarMallaValidacionAfiliado al archivo AfiliacionPersonasCompositeBusiness
 private List<String> ejecutarMallaValidacionAfiliado(
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado) {
        String firmaMetodo = "NovedadesBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;
        List<String> validaciones = new ArrayList();
		logger.info(firmaMetodo + ": " + tipoIdAfiliado+ " : "+ numIdAfiliado);
       
        

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
		datosValidacion.put("tipoAfiliado", "PENSIONADO");

        ValidarReglasNegocio validador = new ValidarReglasNegocio("25LEY", bloque.getProceso(),
                "PENSIONADO", datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
				logger.info("Validaciones no existosas");
				validaciones.add(validacion.getDetalle());
                
            }
        }
        return validaciones;
    }
	
}