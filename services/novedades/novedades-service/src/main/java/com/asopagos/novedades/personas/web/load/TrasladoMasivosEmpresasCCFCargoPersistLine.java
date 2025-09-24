package com.asopagos.novedades.personas.web.load;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.asopagos.dto.afiliaciones.AfiliadosACargoMasivosDTO;
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
import com.asopagos.novedades.constants.ArchivoTrasladoEmpresasCCFConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.AreaGeograficaEnum;
import com.asopagos.afiliaciones.clients.BuscarMunicipio;
import com.asopagos.validaciones.dto.ConsultaRegistroCivilDTO;
import com.asopagos.validaciones.clients.ConsultarDatosRegistraduriaNacional;
import com.asopagos.validaciones.dto.ParametrosRegistroCivilDTO;
import  com.asopagos.validaciones.clients.ExisteRegistraduriaNacional;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.novedades.constants.ArchivoPensionados25AniosConstants;
import com.asopagos.util.Interpolator;





public class TrasladoMasivosEmpresasCCFCargoPersistLine implements IPersistLine{
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)0
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null; //NUEVO DTO
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

                    // // Informacion basica afiliado
                    
                    AfiliadosACargoMasivosDTO afiliadoCargoDTO = new AfiliadosACargoMasivosDTO();
                    // BeneficiarioModeloDTO beneficiarioDTO = new BeneficiarioModeloDTO();

                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_CARGO)) {
                        int tipoIdentificacionAfiliado = Integer.parseInt(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_CARGO));
                                TipoIdentificacionAfiliadoEnumA tipoIdentEnum = TipoIdentificacionAfiliadoEnumA
                                .obtenerTipoIdentificacionEnum(tipoIdentificacionAfiliado);
                                TipoIdentificacionEnum tipoIdentificacion = null;
                                for (TipoIdentificacionEnum tipo : TipoIdentificacionEnum.values()) {
                                    if (tipo.getDescripcion().toUpperCase().equals(tipoIdentEnum.getTipoIdentificacion().toUpperCase())) {
                                        tipoIdentificacion = tipo;
                                        break;
                                    }
                                }
                        afiliadoCargoDTO.setTipoIdentificacionAfiliado(tipoIdentificacion);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO)) {
                        String numeroIdentificacionAfiliado = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO);
                        afiliadoCargoDTO.setNumeroIdentificacionAfiliado(numeroIdentificacionAfiliado);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO)) {
                        int tipoIdentificacionAfiliadoCargo = Integer.parseInt(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO));
                                TipoIdentificacionAfiliadoEnumA tipoIdentEnum = TipoIdentificacionAfiliadoEnumA
                                .obtenerTipoIdentificacionEnum(tipoIdentificacionAfiliadoCargo);
                                TipoIdentificacionEnum tipoIdentificacion = null;
                                for (TipoIdentificacionEnum tipo : TipoIdentificacionEnum.values()) {
                                    if (tipo.getDescripcion().toUpperCase().equals(tipoIdentEnum.getTipoIdentificacion().toUpperCase())) {
                                        tipoIdentificacion = tipo;
                                        break;
                                    }
                                }
                        afiliadoCargoDTO.setTipoIdentificacionPersonaCargo(tipoIdentificacion);
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO)) {
                        String numeroIdentificacionAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO);
                        afiliadoCargoDTO.setNumeroIdentificacionPersonaCargo(numeroIdentificacionAfiliadoCargo);
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO_A_CARGO)) {
                        String primerNombreAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO_A_CARGO);
                        afiliadoCargoDTO.setPrimerNombre(primerNombreAfiliadoCargo.toUpperCase());
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO_A_CARGO)) {
                        String segundoNombreAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO_A_CARGO)
                                .toUpperCase();
                                              
                        afiliadoCargoDTO.setSegundoNombre(segundoNombreAfiliadoCargo.toUpperCase());                        
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO_A_CARGO)) {
                        String primerApellidoAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO_A_CARGO)
                                .toUpperCase();
                        
                        afiliadoCargoDTO.setPrimerApellido(primerApellidoAfiliadoCargo.toUpperCase());
                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO_A_CARGO)) {
                        String segundoApellidoAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO_A_CARGO);
                        afiliadoCargoDTO.setSegundoApellido(segundoApellidoAfiliadoCargo.toUpperCase());
                    }
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO_A_CARGO)) {
                        String fechaNacimientoAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO_A_CARGO)
                                .toUpperCase();
                        afiliadoCargoDTO.setFechaDeNacimiento(fechaNacimientoAfiliadoCargo);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO_A_CARGO)) {
                        String sexoAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO_A_CARGO);
                        GeneroEnumA genero = GeneroEnumA.obtenerGeneroEnum(Integer.parseInt(sexoAfiliadoCargo));
                        GeneroEnum generoAfiliado = null;
                        for (GeneroEnum gen : GeneroEnum.values()) {
                            if (gen.getDescripcion().toUpperCase().equals(genero.getDescripcion().toUpperCase())) {
                                generoAfiliado = gen;
                                break;
                            }
                        }
                        afiliadoCargoDTO.setGenero(generoAfiliado);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO)) {
                        int parentescoPersonaAfiliadoCargo = Integer.parseInt(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO));
                        ParentezcoEnumA parentezco = ParentezcoEnumA.obtenerParentezcoEnum(parentescoPersonaAfiliadoCargo);
                                
                        afiliadoCargoDTO.setParentezco(parentezco);
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO_A_CARGO)) {
                        String codigoMunicipioAfiliadoCargo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO_A_CARGO);
                        BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipioAfiliadoCargo);
				        buscarMunicipio.execute();

				        Short idCodigo = buscarMunicipio.getResult();
                        afiliadoCargoDTO.setCodigoMunicipio(String.valueOf(idCodigo));
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO_A_CARGO)) {
                        String direccion = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO_A_CARGO);
                        afiliadoCargoDTO.setDireccion(direccion);
                                  
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR)) {
                        String telefonoCelular = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR)
                                .toUpperCase();                  
                        afiliadoCargoDTO.setTelefonoCelular(telefonoCelular);                        
                    }
                    // Se asigna el campo No 1 - Tipo de identificación de la empresa 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO)) {
                        String telefonoFijo = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO)
                                .toUpperCase();
                        
                        afiliadoCargoDTO.setTelefonoFijo(telefonoFijo);
                    }
                    // Se asigna el campo No 2 - Número de identificación de la empresa
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA)) {
                        String conyugeLabora = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA);
                        afiliadoCargoDTO.setConyugeLabora(conyugeLabora.equals("1")? Boolean.TRUE:Boolean.FALSE);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.INGRESO_MENSUAL)) {
                        BigDecimal salarioMensual = new BigDecimal(getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.INGRESO_MENSUAL));
                        afiliadoCargoDTO.setIngresoMensual(salarioMensual);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_INICIO_UNION_AFILIADO)) {
                        String fechaInicioUnionAfiliado = getValorCampo(line, ArchivoTrasladoEmpresasCCFConstants.FECHA_INICIO_UNION_AFILIADO)
                                .toUpperCase();
                        afiliadoCargoDTO.setFechaInicioUnionAfiliado(fechaInicioUnionAfiliado);
                    }
              
                      afiliadoCargoDTO.setNoLinea(lineArgumentDTO.getLineNumber());
                    
                    // informacionActualizacionNovedadDTO.setBeneficiario(beneficiarioDTO);
                    // informacionActualizacionNovedadDTO.setAfiliado(AfiliadosMasivosDTO);
                    ((List<AfiliadosACargoMasivosDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA_CARGO)).add(validarRegistraduria(afiliadoCargoDTO,hallazgos,lineArgumentDTO));
                } catch (Exception e) {
                    System.out.println("Error ActualizacionRetiroBeneficiarioPersistLine validate("+ e.getMessage() +")");
                    e.printStackTrace();
                    // ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                    //         .get(ArchivoTrasladoEmpresasCCFConstants.LISTA_CANDIDATOS_AFILIACION_MASIVA)).add(informacionActualizacionNovedadDTO);
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
    private AfiliadosACargoMasivosDTO validarRegistraduria(AfiliadosACargoMasivosDTO afiliado,List<ResultadoHallazgosValidacionArchivoDTO> hallazgos,LineArgumentDTO arguments) {
        ExisteRegistraduriaNacional existeRegistraduriaNacional = new ExisteRegistraduriaNacional(afiliado.getTipoIdentificacionPersonaCargo(),afiliado.getNumeroIdentificacionPersonaCargo());
       existeRegistraduriaNacional.execute();
       Boolean ras = existeRegistraduriaNacional.getResult();
       if (ras == null || ras == false) {
        return afiliado;
       }
        if(afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.CEDULA_CIUDADANIA ||
        afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.REGISTRO_CIVIL ||
        afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.TARJETA_IDENTIDAD) {
         ParametrosRegistroCivilDTO parametro = new ParametrosRegistroCivilDTO();
         if (afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.CEDULA_CIUDADANIA){
             parametro.setTipoBusqueda("CC");
             parametro.setCedula(afiliado.getNumeroIdentificacionPersonaCargo());
         }
         if (afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.REGISTRO_CIVIL){
             parametro.setTipoBusqueda("REGS");
             parametro.setSerial(afiliado.getNumeroIdentificacionPersonaCargo());
         }
         if (afiliado.getTipoIdentificacionPersonaCargo() == TipoIdentificacionEnum.TARJETA_IDENTIDAD){
             parametro.setTipoBusqueda("REGN");
             parametro.setNuip(afiliado.getNumeroIdentificacionPersonaCargo());
         }
 
         ConsultarDatosRegistraduriaNacional consultarDatosRegistraduriaNacional = new ConsultarDatosRegistraduriaNacional(parametro);
         consultarDatosRegistraduriaNacional.execute();
         ConsultaRegistroCivilDTO res = consultarDatosRegistraduriaNacional.getResult();
         System.out.println(res.getEstadoDescCedula()+"afiliados cargo estuvo aqui >:) PERSIST");
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