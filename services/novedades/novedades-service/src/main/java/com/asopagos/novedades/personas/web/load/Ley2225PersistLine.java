package com.asopagos.novedades.personas.web.load;
import java.lang.Long;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.dto.afiliaciones.AfiliadosMasivosDTO;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.afiliaciones.clients.ConsultarPersonaPensionado25Anios;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

public class Ley2225PersistLine implements IPersistLine{
    private static final ILogger logger = LogManager.getLogger(Ley2225PersistLine.class);

 /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    

    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {

        System.out.println("Nico estuvo aqui c: PERSIST 2225");
        Map<String, Object> line;
        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null; //NUEVO DTO
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoPensionados25AniosConstants.LISTA_HALLAZGOS));
            // Cambio esta validacion para que retorne los registros validos sin importar si hay hallazgos.
            boolean persist = true;
            if (!hallazgos.isEmpty() && hallazgos.stream().anyMatch(iteHallazgo -> 
                    iteHallazgo.getNumeroLinea().equals(lineArgumentDTO.getLineNumber()))) {
                persist = false;
            }

            if (persist) {
                try {
                    logger.info("Inicia prueba toma cambios");

                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    // informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO(); //NUEVO DTO
                    Afiliado25AniosDTO pensionadoDTO = new Afiliado25AniosDTO();
                    List<Afiliado25AniosDTO> pensionados = new ArrayList<>();
                    pensionadoDTO.setNoLinea(lineArgumentDTO.getLineNumber());
                    // consulta si la persona existe en bd
                    
                       
                    // if (esCampoValido(line, ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO)) {
                    //     String fechaRecepcion = getVal // String numeroIdentificacion = getValorCampo(line, ArchivoPensionados25AniosConstants.NUMERO_DOCUMENTO_PENSIONADO); 
                    // ConsultarPersonaPensionado25Anios consultarPersona = new ConsultarPersonaPensionado25Anios(resultTipoIdentificacionEnum, numeroIdentificacion);
                    // consultarPersona.execute(); 
                    
                    // Afiliado25AniosExistenteDTO persona = consultarPersona.getResult();orCampo(line, ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO);
                    //     persona.setFechaRecepcionDocumentos(fechaRecepcion);
                    // }  

                    // if (esCampoValido(line, ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO)) {
                    //     String pagador = getValorCampo(line, ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO);
                    //     ConsultarListaValores consultarEntidades = new ConsultarListaValores(46, null, null);
                    // 	consultarEntidades.execute();
                    // 	List<ElementoListaDTO> entidades = consultarEntidades.getResult();
                    // 	logger.info("entidades.getIdLista() "+entidades.size());
                    // 	boolean entidadEncontrada = false;

                    // 	for (ElementoListaDTO entidad : entidades) {
                    // 	logger.info("entidad.getAtributos() "+entidad.getValor());
                    // 		String identificador =  entidad.getIdentificador().toString();
                    // 		if(identificador.equals(pagador)){
                    // 		logger.info("entidad.getAtributos() "+entidad.getAtributos());

                    //             persona.setPagadorPension(entidad.getValor());

                    // 			break;
    
                    // 		}
                    // 	}
                    // }
                    // // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    // if (esCampoValido(line, ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO)) {
                    //     String mesada = getValorCampo(line, ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO);
                    //     persona.setValorMesadaPensional(Long.parseLong(mesada));                        
                    // }
                        
                    // persona.setNoLinea(lineArgumentDTO.getLineNumber());
                    //     ((List<Afiliado25AniosExistenteDTO>) lineArgumentDTO.getContext()
                    //     .get(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_EXISTENTES)).add(persona);
                    
                    TipoIdentificacionEnum resultTipoIdentificacionEnum = null;
                    String tipodocumento = getValorCampo(line, ArchivoPensionados25AniosConstants.TIPO_DOCUMENTO_PENSIONADO);
                    String numeroIdentificacion = getValorCampo(line, ArchivoPensionados25AniosConstants.NUMERO_DOCUMENTO_PENSIONADO);
                    for (TipoIdentificacionEnum tipoIdentificacionEnum : TipoIdentificacionEnum.values()) {
                        if (tipoIdentificacionEnum.getValorEnPILA().equals(tipodocumento) ) {
                            resultTipoIdentificacionEnum = tipoIdentificacionEnum;
                            break;
                        }
                    }
                    pensionadoDTO.setTipoIdentificacion(resultTipoIdentificacionEnum);
                    pensionadoDTO.setNumeroIdentificacion(numeroIdentificacion);

                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.PRIMER_NOMBRE_PENSIONADO)) {
                        String primerNombre = getValorCampo(line, ArchivoPensionados25AniosConstants.PRIMER_NOMBRE_PENSIONADO);
                        pensionadoDTO.setPrimerNombre(primerNombre.toUpperCase());

                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.SEGUNDO_NOMBRE_PENSIONADO)) {
                        String segundoNombre = getValorCampo(line, ArchivoPensionados25AniosConstants.SEGUNDO_NOMBRE_PENSIONADO);
                        pensionadoDTO.setSegundoNombre(segundoNombre.toUpperCase());
                        
                    }
                
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.PRIMER_APELLIDO_PENSIONADO)) {
                        String primerApellido = getValorCampo(line, ArchivoPensionados25AniosConstants.PRIMER_APELLIDO_PENSIONADO);
                        pensionadoDTO.setPrimerApellido(primerApellido.toUpperCase());
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.SEGUNDO_APELLIDO_PENSIONADO)) {
                        String segundoApellido = getValorCampo(line, ArchivoPensionados25AniosConstants.SEGUNDO_APELLIDO_PENSIONADO);
                        pensionadoDTO.setSegundoApellido(segundoApellido.toUpperCase());
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.FECHA_NACIMIENTO_PENSIONADO)) {
                        String fechaNacimiento = getValorCampo(line, ArchivoPensionados25AniosConstants.FECHA_NACIMIENTO_PENSIONADO);
                        pensionadoDTO.setFechaNacimiento(fechaNacimiento);
                            }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO)) {
                        String fechaRecepcion = getValorCampo(line, ArchivoPensionados25AniosConstants.FECHA_RECEPCION_PENSIONADO);
                        pensionadoDTO.setFechaRecepcionDocumentos(fechaRecepcion);
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.DEPARTAMENTO_PENSIONADO)) {
                        String departamento = getValorCampo(line, ArchivoPensionados25AniosConstants.DEPARTAMENTO_PENSIONADO);

                        List<Departamento> departamentos = ((List<Departamento>) lineArgumentDTO
                        .getContext().get(ArchivoPensionados25AniosConstants.LISTA_DEPARTAMENTO));
                        pensionadoDTO.setDepartamento(Departamento(departamento,departamentos));
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.MUNICIPIO_PENSIONADO)) {
                        String municipio = getValorCampo(line, ArchivoPensionados25AniosConstants.MUNICIPIO_PENSIONADO);
                        List<Municipio> municipios = ((List<Municipio>) lineArgumentDTO
                        .getContext().get(ArchivoPensionados25AniosConstants.LISTA_MUNICIPIO));         
                        pensionadoDTO.setMunicipio(Municipio(municipio,municipios));
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.DIRECCION_PENSIONADO)) {
                        String direccion = getValorCampo(line, ArchivoPensionados25AniosConstants.DIRECCION_PENSIONADO);                                
                        pensionadoDTO.setDireccion(direccion);

                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.GENERO_PENSIONADO)) {
                        String genero = getValorCampo(line, ArchivoPensionados25AniosConstants.GENERO_PENSIONADO);
                        
                        pensionadoDTO.setGenero(genero);
                    }
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.ESTADO_CIVIL_PENSIONADO)) {
                        String estado = getValorCampo(line, ArchivoPensionados25AniosConstants.ESTADO_CIVIL_PENSIONADO);
                        String estadoCivil = "";
                        int EnteroestadoCivil = Integer.parseInt(estado);
                        
                        switch (EnteroestadoCivil) { 
                            case 1:
                            estadoCivil = EstadoCivilEnum.UNION_LIBRE.getDescripcion();
                                break;
                            case 2:
                            estadoCivil = EstadoCivilEnum.CASADO.getDescripcion();
                                break;
                            case 3 :
                            estadoCivil = EstadoCivilEnum.DIVORCIADO.getDescripcion();
                                break;
                            case 4 :
                            estadoCivil = EstadoCivilEnum.SEPARADO.getDescripcion();
                                break;
                            case 5 :
                            estadoCivil = EstadoCivilEnum.VIUDO.getDescripcion();
                                break;
                            case 6 :
                            estadoCivil = EstadoCivilEnum.SOLTERO.getDescripcion();
                                break;
                            }
                        pensionadoDTO.setEstadoCivil(estadoCivil);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.TELEFONO_PENSIONADO)) {
                        String telefono = getValorCampo(line, ArchivoPensionados25AniosConstants.TELEFONO_PENSIONADO);
                        pensionadoDTO.setTelefono(Long.parseLong(telefono));
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.CELULAR_PENSIONADO)) {
                        String celular = getValorCampo(line, ArchivoPensionados25AniosConstants.CELULAR_PENSIONADO);
                        pensionadoDTO.setCelular(Long.parseLong(celular));
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.CORREO_PENSIONADO)) {
                        String correo = getValorCampo(line, ArchivoPensionados25AniosConstants.CORREO_PENSIONADO);
                        
                        pensionadoDTO.setCorreoElectronico(correo);
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO)) {
                        String pagador = getValorCampo(line, ArchivoPensionados25AniosConstants.PAGADOR_PENSION_PENSIONADO);
                        ConsultarListaValores consultarEntidades = new ConsultarListaValores(46, null, null);
                        consultarEntidades.execute();
                        List<ElementoListaDTO> entidades = consultarEntidades.getResult();
                        logger.info("entidades.getIdLista() "+entidades.size());
                        boolean entidadEncontrada = false;

                        for (ElementoListaDTO entidad : entidades) {
                        logger.info("entidad.getAtributos() "+entidad.getValor());
                            String identificador =  entidad.getIdentificador().toString();
                            if(identificador.equals(pagador)){
                            logger.info("entidad.getAtributos() "+entidad.getAtributos());

                                pensionadoDTO.setPagadorPension(entidad.getValor());

                                break;
    
                            }
                        }
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO)) {
                        String mesada = getValorCampo(line, ArchivoPensionados25AniosConstants.VALOR_MESADA_PENSIONAL_PENSIONADO);
                        pensionadoDTO.setValorMesadaPensional(Long.parseLong(mesada));                        
                    }
                    pensionadoDTO.setNoLinea(lineArgumentDTO.getLineNumber());
                    
                    // pensionadosNuevos.add(Afiliado25AniosDTO);
                    ((List<Afiliado25AniosDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_NUEVOS)).add(pensionadoDTO);


                    

                } catch (Exception e) {
                    System.out.println("Error ActualizacionRetiroBeneficiarioPersistLine validate("+ e.getMessage() +")");
                    e.printStackTrace();
                    // ((List<AfiliadosMasivosDTO>) lineArgumentDTO.getContext()
                    //         .get(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                }
            }

            List<Afiliado25AniosDTO> listaCandidatos =((List<Afiliado25AniosDTO>) lineArgumentDTO.getContext()
                                    .get(ArchivoPensionados25AniosConstants.LISTA_CANDIDATOS_PENSIONADOS_NUEVOS));


        }

        


    
    }

    private String Municipio(String muni, List<Municipio> municipios) {
					for (Municipio municipio : municipios) {
						if(municipio.getCodigo().equals(muni)){
							return municipio.getNombre()+'/'+municipio.getIdMunicipio();
						}
						
					}
			return null;		

	}
    private String Departamento(String depa, List<Departamento> departamentos) {
					for (Departamento departamento : departamentos) {
						if(departamento.getCodigo().equals(depa)){
							return departamento.getNombre()+'/'+departamento.getIdDepartamento();
						}
						
					}
			return null;		

	}

     

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private Boolean esCampoValido(Map<String, Object> line, String nombreCampo) {
        if (line.get(nombreCampo) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private String getValorCampo(Map<String, Object> line, String nombreCampo) {
        return ((String) line.get(nombreCampo));
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }
}