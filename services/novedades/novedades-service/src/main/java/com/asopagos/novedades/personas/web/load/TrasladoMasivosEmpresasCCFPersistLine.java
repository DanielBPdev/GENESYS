package com.asopagos.novedades.personas.web.load;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.afiliaciones.AfiliadosMasivosDTO;
import com.asopagos.novedades.constants.ArchivoTrasladoEmpresasCCFConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.GeneroEnumA;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.AreaGeograficaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.BenefeciarioCuotaMonetariaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.CategoriaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.CondicionDiscapacidadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.EstadoCivilEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.FactorVulnerabilidadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.NivelEscolaridadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.OrientacionSexualEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.ParentezcoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.PerteneciaEtnicaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoAfiliadoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoCuotaModeradoraEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoIdentificacionAfiliadoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoIdentificacionEmpleadorEnumA;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;

import com.asopagos.novedades.constants.ArchivoTrasladoEmpresasCCFConstants;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.afiliaciones.clients.BuscarMunicipio;
import com.asopagos.validaciones.dto.ConsultaRegistroCivilDTO;
import com.asopagos.validaciones.clients.ConsultarDatosRegistraduriaNacional;
import com.asopagos.validaciones.dto.ParametrosRegistroCivilDTO;
import  com.asopagos.validaciones.clients.ExisteRegistraduriaNacional;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import com.asopagos.util.Interpolator;


public class TrasladoMasivosEmpresasCCFPersistLine implements IPersistLine{
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
 /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        System.out.println("Nico estuvo aqui >:) PERSIST");
        Map<String, Object> line;
        // InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null; //NUEVO DTO
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS));
            //Cambio esta validacion para que retorne los registros validos sin importar si hay hallazgos.
            boolean persist = true;
            if (!hallazgos.isEmpty() && hallazgos.stream().anyMatch(iteHallazgo -> 
                    iteHallazgo.getNumeroLinea().equals(lineArgumentDTO.getLineNumber()))) {
                persist = false;
            }
            
            if (persist) {
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    // informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO(); //NUEVO DTO
                    AfiliadosMasivosDTO afiliadoDTO = new AfiliadosMasivosDTO();
                    List<AfiliadosMasivosDTO> afliadosCandidatos = new ArrayList<>();
                    

        

                    // // Informacion basica afiliado
                    // AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                    // BeneficiarioModeloDTO AfiliadosMasivosDTO = new BeneficiarioModeloDTO();
                    
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_EMPRESA)) {
                        int tipoIdentificacionEmpleador = Integer.parseInt(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_EMPRESA));
                                TipoIdentificacionEmpleadorEnumA tipoIdentEnum = TipoIdentificacionEmpleadorEnumA
                                .obtenerTipoIdentificacionEmpleadorEnum(tipoIdentificacionEmpleador);
                                TipoIdentificacionEnum tipoIdentificacion = null;
                                for (TipoIdentificacionEnum tipo : TipoIdentificacionEnum.values()) {
                                    if (tipo.getDescripcion().toUpperCase().equals(tipoIdentEnum.getTipoIdentificacion().toUpperCase())) {
                                        tipoIdentificacion = tipo;
                                        break;
                                    }
                                }
                        
                                afiliadoDTO.setTipoIdentificacionEmpresa(tipoIdentificacion);

                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA)) {
                        String numeroIdentificacionEmpleador = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA);
                        afiliadoDTO.setNumeroIdentificacionEmpresa(numeroIdentificacionEmpleador);
                    }
                    if(afiliadoDTO.getNumeroIdentificacionEmpresa()!=null && afiliadoDTO.getTipoIdentificacionEmpresa()!=null){
                        System.out.println("2G empleador entra" );
                        ConsultarEmpleadorTipoNumero empleador = new ConsultarEmpleadorTipoNumero(afiliadoDTO.getNumeroIdentificacionEmpresa(), afiliadoDTO.getTipoIdentificacionEmpresa());
                        empleador.execute();
                        if (empleador.getResult() != null  ) {
                            EmpleadorModeloDTO empleadorDto = empleador.getResult();
                            System.out.println("hay empleador idEmpleador" + empleadorDto.getIdEmpleador());
                            afiliadoDTO.setIdEmpleador(empleadorDto.getIdEmpleador());
                            
                        }else{
                            System.out.println("No hay empleador" );
                            
                        }	
                    }
                
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO)) {
                        int tipoIdentificacion = Integer.parseInt(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO));
                                TipoIdentificacionAfiliadoEnumA tipoIdentEnum = TipoIdentificacionAfiliadoEnumA
                                .obtenerTipoIdentificacionEnum(tipoIdentificacion);
                                TipoIdentificacionEnum tipoIdent = null;
                                for (TipoIdentificacionEnum tipo : TipoIdentificacionEnum.values()) {
                                    if (tipo.getDescripcion().toUpperCase().equals(tipoIdentEnum.getTipoIdentificacion().toUpperCase())) {
                                        tipoIdent = tipo;
                                        break;
                                    }
                                }
                                afiliadoDTO.setTipoIdentificacionAfiliado(tipoIdent);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO);
                        afiliadoDTO.setNumeroIdentificacionAfiliado(numeroIdentificacion);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO)) {
                        String primerNombre = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO)
                                .toUpperCase();
                                afiliadoDTO.setPrimerNombre(primerNombre.toUpperCase());
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO)) {
                        String segundoNombre = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO);
                        afiliadoDTO.setSegundoNombre(segundoNombre.toUpperCase());
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO)) {
                        String primerApellido = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO);
                        afiliadoDTO.setPrimerApellido(primerApellido.toUpperCase());
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO)) {
                        String segundoApellido = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO)
                                .toUpperCase();
                                              
                                afiliadoDTO.setSegundoApellido(segundoApellido.toUpperCase());                        
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO)) {
                        String fechaNacimiento = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO)
                                .toUpperCase();
                                afiliadoDTO.setFechaDeNacimiento(fechaNacimiento);
                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO)) {
                        String sexoAfiliado = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO);
                        GeneroEnumA genero = GeneroEnumA.obtenerGeneroEnum(Integer.parseInt(sexoAfiliado));
                        GeneroEnum generoAfiliado = null;
                        for (GeneroEnum gen : GeneroEnum.values()) {
                            if (gen.getDescripcion().toUpperCase().equals(genero.getDescripcion().toUpperCase())) {
                                generoAfiliado = gen;
                                break;
                            }
                        }
                        afiliadoDTO.setGenero(generoAfiliado);
                    }
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.ORIENTACION_SEXUAL_AFILIADO)) {
                        String orientacionSexual = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.ORIENTACION_SEXUAL_AFILIADO)
                                .toUpperCase();
                                OrientacionSexualEnumA orientacion = OrientacionSexualEnumA.obtenerOrientacionSexualEnum(Integer.parseInt(orientacionSexual));
                                OrientacionSexualEnum orientacionSexualA = null;
                                for (OrientacionSexualEnum o : OrientacionSexualEnum.values()) {
                                    if (o.getDescripcion().toUpperCase().equals(orientacion.getDescripcion().toUpperCase())) {
                                        orientacionSexualA = o;
                                        break;
                                    }
                                }
                  
                                afiliadoDTO.setOrientacionSexual(orientacionSexualA);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.NIVEL_ESCOLARIDAD_AFIIADO)) {
                        String nivelEscolaridad = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.NIVEL_ESCOLARIDAD_AFIIADO);
								NivelEscolaridadEnumA nivel = NivelEscolaridadEnumA.obtenerNivelEscolaridadEnum(Integer.parseInt(nivelEscolaridad));
                                NivelEducativoEnum nivelEdu = null;
                                for (NivelEducativoEnum nivelE : NivelEducativoEnum.values()) {
                                    if (nivelE.getDescripcion().toUpperCase().equals(nivel.getDescripcion().toUpperCase())) {
                                        nivelEdu = nivelE;
                                        break;
                                    }
                                }
                                afiliadoDTO.setNivelEscolaridad(nivelEdu);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_OCUPACION_AFILIADO)) {
                        String codigoOcupacionAfiliado = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_OCUPACION_AFILIADO)
                                .toUpperCase();
                                afiliadoDTO.setCodigoOcupacion(codigoOcupacionAfiliado);
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.FACTOR_VULNERABILIDAD_AFILIADO)) {
                        String factorVulnerabilidad = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.FACTOR_VULNERABILIDAD_AFILIADO);
                        FactorVulnerabilidadEnumA factor = FactorVulnerabilidadEnumA.obtenerFactorVulnerabilidadEnum(Integer.parseInt(factorVulnerabilidad));
                        FactorVulnerabilidadEnum factorV = null;
                                for (FactorVulnerabilidadEnum fact : FactorVulnerabilidadEnum.values()) {
                                    if (fact.getDescripcion().toUpperCase().equals(factor.getDescripcion().toUpperCase())) {
                                        factorV = fact;
                                        break;
                                    }
                                }
                                afiliadoDTO.setFactorVulnerabilidad(factorV);
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.ESTADO_CIVIL_AFILIADO)) {
                        String estadoCivil = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.ESTADO_CIVIL_AFILIADO);
                        EstadoCivilEnumA estadoCivile = EstadoCivilEnumA.obtenerEstadoCivilEnum(Integer.parseInt(estadoCivil));
                        EstadoCivilEnum estadoC = null;
                                for (EstadoCivilEnum estado : EstadoCivilEnum.values()) {
                                    if (estado.getDescripcion().toUpperCase().equals(estadoCivile.getDescripcion().toUpperCase())) {
                                        estadoC = estado;
                                        break;
                                    }
                                }

                                afiliadoDTO.setEstadoCivil(estadoC);
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PERTENENCIA_ETNICA_AFILIADO)) {
                        String pertenenciaEtnica = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PERTENENCIA_ETNICA_AFILIADO)
                                .toUpperCase();
                                PerteneciaEtnicaEnumA pertenencia = PerteneciaEtnicaEnumA.obtenerPerteneciaEtnicaEnum(Integer.parseInt(pertenenciaEtnica));
                                PertenenciaEtnicaEnum pertenenciaEt = null;
                                for (PertenenciaEtnicaEnum perte : PertenenciaEtnicaEnum.values()) {
                                    if (perte.getDescripcion().toUpperCase().equals(pertenencia.getDescripcion().toUpperCase())) {
                                        pertenenciaEt = perte;
                                        break;
                                    }
                                }
                                afiliadoDTO.setPertenenciaEtnica(pertenenciaEt);                        
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PAIS_RESIDENCIA_AFILIADO)) {
                        String paisResidencia = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PAIS_RESIDENCIA_AFILIADO)
                                .toUpperCase();
                                afiliadoDTO.setNombrePaisResidencia(paisResidencia);
                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO)) {
                        String codigoMunicipio = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO);
                        BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipio);
				        buscarMunicipio.execute();

				        Short idCodigo = buscarMunicipio.getResult();
                        afiliadoDTO.setCodigoMucipio(Integer.valueOf(idCodigo));
                    }
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.RESIDE_RURAL)) {
                        String resideRural = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.RESIDE_RURAL)
                                .toUpperCase();
                        afiliadoDTO.setResideRural(resideRural.equals("2") ? Boolean.TRUE: Boolean.FALSE);
         
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_LABOR_AFILIADO)) {
                        String codigoMunicipioLabor = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_LABOR_AFILIADO);
                        BuscarMunicipio buscarMunicipioLabor = new BuscarMunicipio(codigoMunicipioLabor);
				        buscarMunicipioLabor.execute();

                        Short idCodigoLabor = buscarMunicipioLabor.getResult();
                        afiliadoDTO.setCodigoMunicipioLabor(Integer.valueOf(idCodigoLabor));
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SALARIO_MENSUAL)) {
                        BigDecimal salarioMensual = new BigDecimal(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SALARIO_MENSUAL));
                        afiliadoDTO.setSalarioMensual(salarioMensual);
     
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_SOLICITANTE)) {
                        String claseTrabajador = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_SOLICITANTE);
                        afiliadoDTO.setClaseTrabajador(ClaseTrabajadorEnum.valueOf(TipoAfiliadoEnumA.obtenerTipoAfiliadoEnum(Integer.parseInt(claseTrabajador)).toString()));
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO)){
                         String direccion = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO);
                         afiliadoDTO.setDireccion(direccion);
                    }
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR_AFILIADO)) {
                        String telefonoCelular = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR_AFILIADO);
                        afiliadoDTO.setTelefonoCelular(telefonoCelular);
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO_AFILIADO)) {
                        String telefonoFijo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO_AFILIADO);               
                        afiliadoDTO.setTelefonoFijo(telefonoFijo);                        
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CORREO_ELECTRONICO)) {
                        String correoElectronico = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CORREO_ELECTRONICO)
                                .toUpperCase();
                        
                                afiliadoDTO.setCorreoElectronico(correoElectronico);
                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CATEGORIA)) {
                        String categoria = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CATEGORIA);
                        afiliadoDTO.setCategoria(categoria);
                    }
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.BENEFICIARIO_CUOTA)) {
                        String beneficiarioCuotaMonetario = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.BENEFICIARIO_CUOTA);
                        afiliadoDTO.setBeneficiarioCuotaMonetario(beneficiarioCuotaMonetario.equals("1")?Boolean.TRUE: Boolean.FALSE);
                    }
                    
                      afiliadoDTO.setNoLinea(lineArgumentDTO.getLineNumber());

                    // afliadosCandidatos.add(afiliadoDTO);
                    ((List<AfiliadosMasivosDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA)).add(validarRegistraduria(afiliadoDTO,hallazgos,lineArgumentDTO));
                } catch (Exception e) {
                    System.out.println("Error ActualizacionRetiroBeneficiarioPersistLine validate("+ e.getMessage() +")");
                    e.printStackTrace();
                    // ((List<AfiliadosMasivosDTO>) lineArgumentDTO.getContext()
                    //         .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                }
            }
        }
    
    }

    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoPensionados25AniosConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
         return hallazgo;
   }

    private AfiliadosMasivosDTO validarRegistraduria(AfiliadosMasivosDTO afiliado,List<ResultadoHallazgosValidacionArchivoDTO> hallazgos,LineArgumentDTO arguments) {
       ExisteRegistraduriaNacional existeRegistraduriaNacional = new ExisteRegistraduriaNacional(afiliado.getTipoIdentificacionAfiliado(),afiliado.getNumeroIdentificacionAfiliado());
       existeRegistraduriaNacional.execute();
       System.out.println("Melissa estuvo");
       Boolean ras = existeRegistraduriaNacional.getResult();
       if (ras == null || ras == false) {
        return afiliado;
       }
       System.out.println("Melissa PERSIST");
       System.out.println("Melissa  2"+afiliado.getNumeroIdentificacionAfiliado());
       if(afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.CEDULA_CIUDADANIA ||
       afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.REGISTRO_CIVIL ||
       afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.TARJETA_IDENTIDAD) {
        ParametrosRegistroCivilDTO parametro = new ParametrosRegistroCivilDTO();
        if (afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.CEDULA_CIUDADANIA){
            parametro.setTipoBusqueda("CC");
            parametro.setCedula(afiliado.getNumeroIdentificacionAfiliado());
        }
        if (afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.REGISTRO_CIVIL){
            parametro.setTipoBusqueda("REGS");
            parametro.setSerial(afiliado.getNumeroIdentificacionAfiliado());
        }
        if (afiliado.getTipoIdentificacionAfiliado() == TipoIdentificacionEnum.TARJETA_IDENTIDAD){
            parametro.setTipoBusqueda("REGN");
            parametro.setNuip(afiliado.getNumeroIdentificacionAfiliado());
        }


        ConsultarDatosRegistraduriaNacional consultarDatosRegistraduriaNacional = new ConsultarDatosRegistraduriaNacional(parametro);
        consultarDatosRegistraduriaNacional.execute();
        ConsultaRegistroCivilDTO res = consultarDatosRegistraduriaNacional.getResult();
        System.out.println(res.getEstadoDescCedula()+"Melissa estuvo aqui >:) PERSIST");
        if(res.getEstadoDescCedula() != null && (res.getEstadoDescCedula().equals("Cancelada por Muerte")|| res.getEstadoDescCedula().equals("Cancelada por Muerte Facultad Ley 1365 2009"))){    
            System.out.println(res.getEstadoDescCedula()+"Melissa estuvo aqui 0 PERSIST");
            hallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento empleador",
										"la persona se encuentra fallecida, no se puede afiliar"));
          }
          else if (res.getEstadoDescCedula() == "ANOMALO INVALIDO" || res.getEstadoDescCedula() == "ANOMALO REEMPLAZADO INVALIDO" ||
            res.getEstadoDescCedula() == "ANOMALO REEMPLAZADO VALIDO" || res.getEstadoDescCedula() == "ANOMALO VALIDO" ||
            res.getEstadoDescCedula() == "INVALIDO" || res.getEstadoDescCedula() == "REEMPLAZADO INVALIDO" ||
            res.getEstadoDescCedula() == "BORRADO" || res.getEstadoDescCedula() == "POR AUTORIZAR") {
                System.out.println(res.getEstadoDescCedula()+"Melissa estuvo aqui 1 PERSIST");
           
                if(res.getNombre1Inscrito() != null) {
                afiliado.setPrimerNombre(res.getNombre1Inscrito());
                }
                if(res.getNombre2Inscrito() != null) {
                    afiliado.setSegundoNombre(res.getNombre2Inscrito());
                }
                if(res.getApellido1Inscrito() != null) {
                    afiliado.setPrimerApellido(res.getApellido1Inscrito());
                }
                if(res.getApellido2Inscrito() != null) {
                    afiliado.setSegundoApellido(res.getApellido2Inscrito());
                }
                if(res.getGenero() != null) {
                    afiliado.setGenero(res.getGenero());
                }
        }
       }
       return afiliado;
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