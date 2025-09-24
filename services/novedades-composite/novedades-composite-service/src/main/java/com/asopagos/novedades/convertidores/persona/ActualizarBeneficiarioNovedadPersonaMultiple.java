package com.asopagos.novedades.convertidores.persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarNovedadPila;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.ParametroConsultaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.NovedadDetalleModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.novedades.composite.clients.EjecutarActualizacionBeneficiario;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.composite.service.util.NovedadesCompositeUtils;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliadosrutines.actualizarnovedadpila.ActualizarNovedadPilaRutine;
import com.asopagos.rutine.listaschequeorutines.guardarlistachequeo.GuardarListaChequeoRutine;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.PersonasFOVISUtils;
import com.asopagos.afiliados.clients.ConsultarCondicionInvalidezConyugeCuidador;
import com.asopagos.personas.clients.ActualizarCondicionInvalidez; 
import com.asopagos.entidades.ccf.personas.CondicionInvalidez; 
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.afiliados.clients.ConsultarBeneficiarioTipoNroIdentificacion;
import java.util.concurrent.TimeUnit;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;

/**
 * Clase que contiene la lógica para actualizar los datos de un Beneficiario.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarBeneficiarioNovedadPersonaMultiple implements NovedadCore {

    private List<TipoTransaccionEnum> conyugeLabora;
    private List<TipoTransaccionEnum> ingresoMensual;
    private List<TipoTransaccionEnum> activarBeneficiarioConyuge;
    private List<TipoTransaccionEnum> activarBeneficiarioHijo;
    private List<TipoTransaccionEnum> activarBeneficiarioPadre;
    private List<TipoTransaccionEnum> inactivarBeneficiarioConyuge;
    private List<TipoTransaccionEnum> inactivarBeneficiarioHijosPadres;
    private List<TipoTransaccionEnum> actualizacionCertificadosEstudiosEscolaridad;
    private List<TipoTransaccionEnum> retiroBeneficiario;
    private List<ClasificacionEnum> activarBeneficiarioMultiple;

    private static final ILogger logger = LogManager.getLogger(ActualizarBeneficiarioNovedadPersona.class);


    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {

        return null;
    }

    /**
     * Asigna los datos comunes a la Activación de Beneficiario.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarDatosComunesActivacion(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona) {
        logger.info("asignarDatosComunesActivacion ");
        if (beneficiarioDTO.getIdBeneficiario() == null) {
            /* Datos Afiliado */
            AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
            afiliadoModeloDTO.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
            afiliadoModeloDTO.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
            /* Datos Persona */
            beneficiarioDTO.setTipoIdentificacion(datosPersona.getTipoIdentificacionBeneficiario());
            beneficiarioDTO.setNumeroIdentificacion(datosPersona.getNumeroIdentificacionBeneficiario());
            /* Se consulta si existe la persona. */
            ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(beneficiarioDTO.getNumeroIdentificacion(),
                    beneficiarioDTO.getTipoIdentificacion());
            consultarDatosPersona.execute();
            PersonaModeloDTO personaModeloDTO = consultarDatosPersona.getResult();
            /* Si existe se asignan los datos actuales de la persona */
            if (personaModeloDTO != null && personaModeloDTO.getIdPersona() != null) {
                Persona persona = personaModeloDTO.convertToPersonaEntity();
                PersonaDetalle personaDetalle = null;
                if (personaModeloDTO.getIdPersonaDetalle() != null) {
                    personaDetalle = personaModeloDTO.convertToPersonaDetalleEntity();
                }
                /* Se asignan los datos de la persona encontrada */
                beneficiarioDTO.convertToDTO(persona, personaDetalle);
                /* Se asigna la Ubicación Principal de la Persona */
                beneficiarioDTO.setUbicacionModeloDTO(personaModeloDTO.getUbicacionModeloDTO());
            }
            beneficiarioDTO.setPrimerApellido(datosPersona.getPrimerApellidoBeneficiario());
            beneficiarioDTO.setSegundoApellido(datosPersona.getSegundoApellidoBeneficiario());
            beneficiarioDTO.setPrimerNombre(datosPersona.getPrimerNombreBeneficiario());
            beneficiarioDTO.setSegundoNombre(datosPersona.getSegundoNombreBeneficiario());
            /* Datos Beneficiario */
            beneficiarioDTO.setTipoBeneficiario(datosPersona.getParentescoBeneficiarios());
        }
        else {
            beneficiarioDTO.setIdBeneficiario(datosPersona.getIdBeneficiario());
        }
        /*
         * Se modifica a activo el estado del beneficiario.
         * La afiliacion es TRUE cuando se agrega beneficiario,
         * FALSE cuando no pasa validaciones de afiliacion y
         * null cuando se activa uno existente
         */
        if (datosPersona.getAfiliacion() == null || datosPersona.getAfiliacion()) {
            beneficiarioDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.ACTIVO);
            beneficiarioDTO.setEstadoBeneficiarioCaja(EstadoAfiliadoEnum.ACTIVO);
        }
        else {
            beneficiarioDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
        }
        beneficiarioDTO.setFechaAfiliacion(new Date().getTime());
        beneficiarioDTO.setIdRolAfiliado(datosPersona.getIdRolAfiliado());
        beneficiarioDTO.setFechaRetiro(null);
        beneficiarioDTO.setMotivoDesafiliacion(null);
    }

    /**
     * Asigna los datos de Activación de Conyuge.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarDatosActivacionConyuge(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona) {
        if (beneficiarioDTO.getIdBeneficiario() == null) {
            /* Datos de Persona */
            /*CCREPNORMATIVOS*/
            //beneficiarioDTO.setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
            beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getFechaExpedicionDocConyuge());
            beneficiarioDTO.setGenero(datosPersona.getGeneroConyuge());

            beneficiarioDTO.setFechaNacimiento(datosPersona.getFechaNacimientoConyuge());
            beneficiarioDTO.setNivelEducativo(datosPersona.getNivelEducativoConyuge());
            beneficiarioDTO.setIdResguardo(datosPersona.getIdResguardo());
            beneficiarioDTO.setIdPuebloIndigena(datosPersona.getIdPuebloIndigena());
            beneficiarioDTO.setPertenenciaEtnica(datosPersona.getPertenenciaEtnica());
            if (datosPersona.getProfesionConyuge() != null && datosPersona.getProfesionConyuge().getIdOcupacionProfesion() != null) {
                beneficiarioDTO.setIdOcupacionProfesion(datosPersona.getProfesionConyuge().getIdOcupacionProfesion());
            }
            /* Datos de Beneficiario */
            beneficiarioDTO.setLabora(datosPersona.getConyugeLabora());
            beneficiarioDTO.setSalarioMensualBeneficiario(datosPersona.getValorIngresoMensualConyuge());
            beneficiarioDTO.setFechaInicioSociedadConyugal(datosPersona.getFechaInicioUnionConAfiliadoPrincipal());
        }
    }

    /**
     * Asigna los datos de Activación de Hijos.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarDatosActivacionHijos(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona) {
        if (beneficiarioDTO.getIdBeneficiario() == null) {
            /* Datos de Persona */
            beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getFechaExpedicionDocHijo());
            beneficiarioDTO.setGenero(datosPersona.getGeneroHijo());
            beneficiarioDTO.setFechaNacimiento(datosPersona.getFechaNacimientoHijo());
            beneficiarioDTO.setNivelEducativo(datosPersona.getNivelEducativoHijo());
            beneficiarioDTO.setIdResguardo(datosPersona.getIdResguardo());
            beneficiarioDTO.setIdPuebloIndigena(datosPersona.getIdPuebloIndigena());
            beneficiarioDTO.setPertenenciaEtnica(datosPersona.getPertenenciaEtnica());
            if (datosPersona.getProfesionHijo() != null && datosPersona.getProfesionHijo().getIdOcupacionProfesion() != null) {
                beneficiarioDTO.setIdOcupacionProfesion(datosPersona.getProfesionHijo().getIdOcupacionProfesion());
            }
            /* Datos beneficiario */
            if (datosPersona.getGradoCursadoHijo() != null && datosPersona.getGradoCursadoHijo().getIdgradoAcademico() != null) {
                beneficiarioDTO.setGradoAcademicoBeneficiario(datosPersona.getGradoCursadoHijo().getIdgradoAcademico());
                beneficiarioDTO.setGradoAcademico(datosPersona.getGradoCursadoHijo().getIdgradoAcademico());
            }
        }
    }

    /**
     * Asigna los datos de Activación de Padres.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarDatosActivacionPadres(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona) {
        if (beneficiarioDTO.getIdBeneficiario() == null) {
            /* Datos de Persona */
            beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getExpediciondocPadre());
            beneficiarioDTO.setGenero(datosPersona.getGeneroPadre());
            beneficiarioDTO.setFechaNacimiento(datosPersona.getFechaNacimientoPadre());
            beneficiarioDTO.setNivelEducativo(datosPersona.getNivelEducativoPadre());
            beneficiarioDTO.setIdResguardo(datosPersona.getIdResguardo());
            beneficiarioDTO.setIdPuebloIndigena(datosPersona.getIdPuebloIndigena());
            beneficiarioDTO.setPertenenciaEtnica(datosPersona.getPertenenciaEtnica());
            if (datosPersona.getOcupacionProfesionPadre() != null
                    && datosPersona.getOcupacionProfesionPadre().getIdOcupacionProfesion() != null) {
                beneficiarioDTO.setIdOcupacionProfesion(datosPersona.getOcupacionProfesionPadre().getIdOcupacionProfesion());
            }
        }
    }

    /**
     * Asigna los datos de Actualización de Certificado de Estudios.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarActualizacionCertificadoEstudios(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona, NovedadDetalleModeloDTO novedadDetalleModeloDTO) {
        beneficiarioDTO.setNivelEducativo(datosPersona.getNivelEducativoHijo());
        if (datosPersona.getGradoCursadoHijo() != null && datosPersona.getGradoCursadoHijo().getIdgradoAcademico() != null) {
            beneficiarioDTO.setGradoAcademicoBeneficiario(datosPersona.getGradoCursadoHijo().getIdgradoAcademico());
            beneficiarioDTO.setGradoAcademico(datosPersona.getGradoCursadoHijo().getIdgradoAcademico());
        }
        beneficiarioDTO.setCertificadoEscolaridad(datosPersona.getCertificadoEscolarHijo());
        beneficiarioDTO.setFechaVencimientoCertificadoEscolar(datosPersona.getFechaVencimientoCertEscolar());
        novedadDetalleModeloDTO.setFechaFin(datosPersona.getFechaVencimientoCertEscolar());
        if (datosPersona.getFechaReporteCertEscolarHijo() != null) {
            beneficiarioDTO.setFechaRecepcionCertificadoEscolar(datosPersona.getFechaReporteCertEscolarHijo());
            novedadDetalleModeloDTO.setFechaInicio(datosPersona.getFechaReporteCertEscolarHijo());
        }
        beneficiarioDTO.setEstudianteTrabajoDesarrolloHumano(datosPersona.getBeneficioProgramaTrabajoDesarrollo());
        beneficiarioDTO.setActualizarCertificado(Boolean.TRUE);
    }


    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void agregarListaNovedades() {
        /* Novedad 131 Front. */
        conyugeLabora = new ArrayList<>();
        conyugeLabora.add(TipoTransaccionEnum.CONYUGE_LABORA_DEPWEB);
        conyugeLabora.add(TipoTransaccionEnum.CONYUGE_LABORA_PRESENCIAL);
        conyugeLabora.add(TipoTransaccionEnum.CONYUGE_LABORA_WEB);
        /* Novedad 132 Front. */
        ingresoMensual = new ArrayList<>();
        ingresoMensual.add(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_DEPWEB);
        ingresoMensual.add(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_PRESENCIAL);
        ingresoMensual.add(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_WEB);
        /* Novedad Activar Beneficiario Multiple 89526  */
        activarBeneficiarioMultiple = new ArrayList<>();
        activarBeneficiarioMultiple.add(ClasificacionEnum.CONYUGE);
        activarBeneficiarioMultiple.add(ClasificacionEnum.PADRE);
        activarBeneficiarioMultiple.add(ClasificacionEnum.MADRE);
        activarBeneficiarioMultiple.add(ClasificacionEnum.HIJO_BIOLOGICO);
        activarBeneficiarioMultiple.add(ClasificacionEnum.HIJO_ADOPTIVO);
        activarBeneficiarioMultiple.add(ClasificacionEnum.HIJASTRO);
        activarBeneficiarioMultiple.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        activarBeneficiarioMultiple.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        
    }
    
    /**
     * Método encargado de asignar la fecha de expedición de documento a un beneficiario
     * 
     * @author Francisco Alejandro Hoyos Rojas
     * @param beneficiarioDTO beneficiario
     * @param datosPersona datos de la persona que vienen desde la novedad
     * @param tipoBeneficiario tipo de beneficiario PADRE,HIJO,CONYUGE
     */
    private void  asignarFechaExpedicionDocumento(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona, TipoBeneficiarioEnum tipoBeneficiario) {
    	switch(tipoBeneficiario) {
    		case HIJO:{
    			if(beneficiarioDTO.getIdBeneficiario() != null) {
    				beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getFechaExpedicionDocHijo());
    			}
    			break;
    		}
    		case PADRES:{
    			if(beneficiarioDTO.getIdBeneficiario() != null) {
    				beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getExpediciondocPadre());
    			}
    			break;
    		}
    		case CONYUGE:{
    			if(beneficiarioDTO.getIdBeneficiario() != null) {
    				beneficiarioDTO.setFechaExpedicionDocumento(datosPersona.getFechaExpedicionDocConyuge());
    			}
    			break;
    		}
    	}
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO) {
        /* se transforma a un objeto de datos del empleador */
        String firmaServicio = "transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO)";
        System.out.println("Inicia "+firmaServicio);
        NovedadesCompositeUtils n = new NovedadesCompositeUtils(entityManager);
        
        List<DatosPersonaNovedadDTO> datosPersonaMultiple = solicitudNovedadDTO.getDatosPersonaMultiple();
        for(DatosPersonaNovedadDTO datosPersona : datosPersonaMultiple){

        
            System.out.println("datosPersona.getFechaInicioNovedad: "+datosPersona.getFechaInicioNovedad());
            ClasificacionEnum novedad = datosPersona.getClasificacion();
            /* Asocia los tipos de Novedad de Persona. */
            this.agregarListaNovedades();

            List<BeneficiarioModeloDTO> beneficiarioModeloDTOConsulta = new ArrayList<>();
            /* Se crea el Objeto BeneficiarioDTO */
            BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
            if ((activarBeneficiarioMultiple.contains(novedad)) && datosPersona.getIdBeneficiario() == null) {
                System.out.println("entre idBeneficiario");
                ConsultarBeneficiarioTipoNroIdentificacion consultaBeneficiarioTipoNroIdentificacion = new ConsultarBeneficiarioTipoNroIdentificacion(datosPersona.getNumeroIdentificacionBeneficiario(), datosPersona.getTipoIdentificacionBeneficiario());
                consultaBeneficiarioTipoNroIdentificacion.execute();
                // List<BeneficiarioModeloDTO> beneficiarioModeloDTOConsulta = consultaBeneficiarioTipoNroIdentificacion.getResult();
                beneficiarioModeloDTOConsulta = consultaBeneficiarioTipoNroIdentificacion.getResult();
                logger.info("beneficiarioModeloDTOConsulta " +beneficiarioModeloDTOConsulta.toString());
                if (beneficiarioModeloDTOConsulta != null) {
                    for (BeneficiarioModeloDTO beneficiarioModeloDTOConsultai : beneficiarioModeloDTOConsulta) {
                        if (beneficiarioModeloDTOConsultai.getAfiliado().getNumeroIdentificacion().equals(datosPersona.getNumeroIdentificacionTrabajador())
                        && beneficiarioModeloDTOConsultai.getAfiliado().getTipoIdentificacion().equals(datosPersona.getTipoIdentificacionTrabajador())) {
                            System.out.println("coinciden idBeneficiario");
                            datosPersona.setIdBeneficiario(beneficiarioModeloDTOConsultai.getIdBeneficiario());
                            break;
                        }
                    }
                }
            }
            if (datosPersona.getIdBeneficiario() != null) {
                logger.info("datosPersona.getIdBeneficiario() != null");
                ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(datosPersona.getIdBeneficiario());
                consultarBeneficiario.execute();
                beneficiarioModeloDTO = consultarBeneficiario.getResult();
            }

            /* Se consulta el afiliado y se asocia al beneficiario que se va a crear */
            ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(datosPersona.getNumeroIdentificacion(),
                    datosPersona.getTipoIdentificacion());
            consultarDatosAfiliado.execute();
            AfiliadoModeloDTO afiliadoModeloDTO = consultarDatosAfiliado.getResult();

            GrupoFamiliarModeloDTO grupoFamiliar = null;
            // Indica si se debe agregar informacion del grupo familiar
            Boolean addGrupoFamiliar = false;

            // Indica si se actualizara la informacion de solicitud
            Boolean actulizaSolicitudNovedad = false;

            // Indica si se debe verificar la validacion de postulado FOVIS
            Boolean validarPostuladoFovis = false;
            
            //Mantis-257940: Datos para condición de Invalidez
            Boolean invalidez = null;
            Long fechaReporteInvalidez = null;
            Long fechaInicioInvalidez = null;
            Boolean conyugeCuidador = null;
            Long fechaInicioConyugeCuidador = null;
            Long fechaFinConyugeCuidador = null;
            Long idConyugeCuidador = null;

            //Informacion datos de identificacion beneficiario
            PertenenciaEtnicaEnum pertenenciaEtnica = null;
            Long idResguardo = null;
            Long idPuebloIndigena = null;

            // Contiene la información de vigencia del certificado
            NovedadDetalleModeloDTO novedadDetalleModeloDTO = new NovedadDetalleModeloDTO();

            /* se asocia el afiliado principal al beneficiario */
            beneficiarioModeloDTO.setIdAfiliado(afiliadoModeloDTO.getIdAfiliado());
            
            /* Por defecto no se actualiza el certificado escolar, solo en las novedades que asi lo requieran*/
            beneficiarioModeloDTO.setActualizarCertificado(Boolean.FALSE);
            
            logger.info("Tipo de Novedad que Entra: " + novedad);
            // if (ClasificacionEnum.CONYUGE == novedad) {
            //     beneficiarioModeloDTO.setLabora(datosPersona.getConyugeLabora());
            //     /* Novedad 132 Front. */
            // }
            // else if (ClasificacionEnum.CONYUGE == novedad) {
            //     beneficiarioModeloDTO.setSalarioMensualBeneficiario(datosPersona.getValorIngresoMensualConyuge());
            //     validarPostuladoFovis = true;
            //     /* Novedad 1 Back */
            // }
            if (ClasificacionEnum.CONYUGE == novedad) { 
                logger.info("Entra al else if de ClasificacionEnum.CONYUGE: ");
                // Lógica común
                beneficiarioModeloDTO.setLabora(datosPersona.getConyugeLabora());
                beneficiarioModeloDTO.setSalarioMensualBeneficiario(datosPersona.getValorIngresoMensualConyuge());
                logger.info("afiliadoModeloDTO " + afiliadoModeloDTO.toString());
                // Lógica de estado civil
                EstadoCivilEnum estadoAnteriorAfiliado = EstadoCivilEnum.valueOf(afiliadoModeloDTO.getEstadoCivil().name());
                beneficiarioModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilBeneficiario());
                afiliadoModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilBeneficiario());

                if (solicitudNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB)) {
                    if (estadoAnteriorAfiliado.equals(datosPersona.getEstadoCivilTrabajador())) {
                        beneficiarioModeloDTO.setEstadoCivil(EstadoCivilEnum.CASADO);
                        afiliadoModeloDTO.setEstadoCivil(EstadoCivilEnum.CASADO);
                    } else {
                        beneficiarioModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilTrabajador());
                        afiliadoModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilTrabajador());
                    }
                }

                this.asignarDatosComunesActivacion(beneficiarioModeloDTO, datosPersona);
                this.asignarDatosActivacionConyuge(beneficiarioModeloDTO, datosPersona);
                this.asignarFechaExpedicionDocumento(beneficiarioModeloDTO, datosPersona, TipoBeneficiarioEnum.CONYUGE);
                
                addGrupoFamiliar = true;
                actulizaSolicitudNovedad = true;
                // validarPostuladoFovis = true;

                // Lógica FOVIS
                List<ParametroConsultaDTO> listaParametros = new ArrayList<>();
                ParametroConsultaDTO dto = new ParametroConsultaDTO();
                dto.setEntityManager(entityManager);
                dto.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
                dto.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
                listaParametros.add(dto);
                List<PersonaPostulacionDTO> listPostulaciones = PersonasFOVISUtils.consultarPostulacionVigente(listaParametros);

                if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
                    List<PersonaDTO> listaPersonas = new ArrayList<>();
                    PersonaDTO personaDTO = new PersonaDTO();
                    personaDTO.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
                    personaDTO.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
                    listaPersonas.add(personaDTO);

                    VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = 
                        new VerificarPersonaNovedadRegistrarAnalisisFovis(solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
                    verificarPersonaNovedadRegistrarAnalisisFovis.execute();
                }else{
                    //System.out.println("No validó!!");
                }
                if (beneficiarioModeloDTOConsulta != null) {
                List<BeneficiarioModeloDTO> listaBeneficiarios = new ArrayList<>();
                for (BeneficiarioModeloDTO beneficiarioModeloDTOConsultai : beneficiarioModeloDTOConsulta) {
                    if(!beneficiarioModeloDTOConsultai.getTipoBeneficiario().equals(ClasificacionEnum.CONYUGE)) {
                        listaBeneficiarios.add(beneficiarioModeloDTOConsultai);
                    }
                }
                if(!listaBeneficiarios.isEmpty()) {
                    DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
                    datosNovedadConsecutivaDTO.setFechaRetiro(new Date().getTime());
                    datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
                    datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
                    datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedadDTO.getNovedadDTO().getNovedad());
                    RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
                    novedadCascada.execute();
                }
                
            }
            }
            else if (ClasificacionEnum.HIJO_BIOLOGICO == novedad || ClasificacionEnum.HIJO_ADOPTIVO == novedad || ClasificacionEnum.HIJASTRO == novedad ||
                ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES == novedad || ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA == novedad) {
                logger.info("Entra al else if de ClasificacionEnum.HIJO: ");
                this.asignarDatosComunesActivacion(beneficiarioModeloDTO, datosPersona);
                this.asignarDatosActivacionHijos(beneficiarioModeloDTO, datosPersona);
                this.asignarActualizacionCertificadoEstudios(beneficiarioModeloDTO, datosPersona, novedadDetalleModeloDTO);
                //Mantis 265205
                this.asignarFechaExpedicionDocumento(beneficiarioModeloDTO, datosPersona, TipoBeneficiarioEnum.HIJO);
                addGrupoFamiliar = true;
                actulizaSolicitudNovedad = true;
                validarPostuladoFovis = true;
                //Mantis 257940
                invalidez = datosPersona.getCondicionInvalidezHijo();
                fechaReporteInvalidez = datosPersona.getFechaRepoteinvalidezHijo();
                fechaInicioInvalidez = datosPersona.getFechaInicioInvalidezHijo();
                conyugeCuidador = datosPersona.getConyugeCuidadorHijo();
                fechaInicioConyugeCuidador = datosPersona.getFechaInicioConyugeCuidadorHijo();
                fechaFinConyugeCuidador = datosPersona.getFechaFinConyugeCuidadorHijo();
                idConyugeCuidador = datosPersona.getIdConyugeCuidador();
                /* Novedad 7 - 8 Back */
            }
            else if (ClasificacionEnum.PADRE == novedad || ClasificacionEnum.MADRE == novedad) {
                logger.info("Entra al else if de ClasificacionEnum.PADRES: ");
                this.asignarDatosComunesActivacion(beneficiarioModeloDTO, datosPersona);
                this.asignarDatosActivacionPadres(beneficiarioModeloDTO, datosPersona);
                //Mantis 265205
                this.asignarFechaExpedicionDocumento(beneficiarioModeloDTO, datosPersona, TipoBeneficiarioEnum.PADRES);
                addGrupoFamiliar = true;
                actulizaSolicitudNovedad = true;
                validarPostuladoFovis = true;
                //Mantis 257940
                invalidez = datosPersona.getCondicionInvalidezPadre();
                fechaReporteInvalidez = datosPersona.getFechaReporteInvalidezPadre();
                fechaInicioInvalidez = datosPersona.getFechaInicioInvalidezPadre();
                conyugeCuidador = datosPersona.getConyugeCuidadorPadre();
                fechaInicioConyugeCuidador = datosPersona.getFechaInicioConyugeCuidadorPadre();
                fechaFinConyugeCuidador = datosPersona.getFechaFinConyugeCuidadorPadre();
                /* Novedad 9 Back */
            }

            else if (actualizacionCertificadosEstudiosEscolaridad.contains(novedad)) {
                this.asignarActualizacionCertificadoEstudios(beneficiarioModeloDTO, datosPersona, novedadDetalleModeloDTO);
                novedadDetalleModeloDTO.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
                /* Novedad 201 - 204 back */
            }

            // Se verifica si se debe agregar un nuevo grupo familiar
            if (addGrupoFamiliar && beneficiarioModeloDTO.getIdGrupoFamiliar() == null) {
                grupoFamiliar = datosPersona.getGrupoFamiliarBeneficiario();
            }
            
            //Se asocia la condición especial (de aplicar)
            beneficiarioModeloDTO.setInvalidez(invalidez);
            beneficiarioModeloDTO.setFechaReporteInvalidez(fechaReporteInvalidez);
            beneficiarioModeloDTO.setFechaInicioInvalidez(fechaInicioInvalidez);
            beneficiarioModeloDTO.setConyugeCuidador(conyugeCuidador);
            beneficiarioModeloDTO.setFechaInicioConyugeCuidador(fechaInicioConyugeCuidador);
            beneficiarioModeloDTO.setFechaFinConyugeCuidador(fechaFinConyugeCuidador);
            beneficiarioModeloDTO.setIdConyugeCuidador(idConyugeCuidador);
            
            /* Instancia el servicio para actualizar Beneficiario */
            BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliado = new BeneficiarioGrupoAfiliadoDTO();
            beneficiarioGrupoAfiliado.setAfiliado(afiliadoModeloDTO);
            beneficiarioGrupoAfiliado.setBeneficiario(beneficiarioModeloDTO);
            beneficiarioGrupoAfiliado.setGrupoFamiliar(grupoFamiliar);
            // Se agrega la informacion de la solicitud de novedad
            // para actulizarla de acuerdo al beneficiario que es creado
            if (actulizaSolicitudNovedad) {
                beneficiarioGrupoAfiliado.setSolicitudNovedadDTO(solicitudNovedadDTO);
            }
            else {
                beneficiarioGrupoAfiliado.setSolicitudNovedadDTO(null);
            }

            // Se verifica si se indico desde validaciones que la persona esta asociada a una postulacion FOVIS
            // Se crea la tarea para revisarla en la HU325-77
            if (validarPostuladoFovis) {
                // Se verifica si la persona esta asociada a una postulacion FOVIS
                List<PersonaDTO> listaPersonas = new ArrayList<>();
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNumeroIdentificacion(beneficiarioModeloDTO.getNumeroIdentificacion());
                personaDTO.setTipoIdentificacion(beneficiarioModeloDTO.getTipoIdentificacion());
                listaPersonas.add(personaDTO);
                // Se crea la tarea para revisarla en la HU325-77
                /*
                VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                        solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
                verificarPersonaNovedadRegistrarAnalisisFovis.execute();
                */
                
                n.verificarPersonaNovedadRegistrarAnalisisFovis(solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas, userDTO);
            }

            // Actualizar las fechas de vigencia de la novedad
            if (novedadDetalleModeloDTO.getFechaFin() != null) {
                /*
                ActualizarNovedadPila actualizarNovedadPila = new ActualizarNovedadPila(novedadDetalleModeloDTO);
                actualizarNovedadPila.execute();
                */
                
                ActualizarNovedadPilaRutine a = new ActualizarNovedadPilaRutine();
                a.actualizarNovedadPila(novedadDetalleModeloDTO, entityManager);
                
            }

            logger.info("INGRESA A actualizarBeneficiarioService " + new Date());

//            EjecutarActualizacionBeneficiario actualizarBeneficiarioService = new EjecutarActualizacionBeneficiario(beneficiarioGrupoAfiliado);
//            actualizarBeneficiarioService.execute();
            n.ejecutarActualizacionBeneficiario(beneficiarioGrupoAfiliado);

            logger.info("TERMINA A actualizarBeneficiarioService " + new Date());

            logger.info("datosPersonaList --> n " + datosPersona.getNumeroIdentificacionBeneficiario());
            logger.info("datosPersonaList --> n2 " + beneficiarioModeloDTO.getNumeroIdentificacion());
            // Se guarda la lista de chequeo
            ListaChequeoDTO listaChequeo = new ListaChequeoDTO();
            listaChequeo.setIdSolicitudGlobal(solicitudNovedadDTO.getIdSolicitud());
            if (beneficiarioModeloDTO != null) {
                logger.info("persona.getListaChequeoNovedad() " + datosPersona.getListaChequeoNovedad().toString());
                listaChequeo.setListaChequeo(datosPersona.getListaChequeoNovedad());
                listaChequeo.setNumeroIdentificacion(beneficiarioModeloDTO.getNumeroIdentificacion());
                listaChequeo.setTipoIdentificacion(beneficiarioModeloDTO.getTipoIdentificacion());
            }
            if (listaChequeo.getListaChequeo() != null && !listaChequeo.getListaChequeo().isEmpty()) {
                logger.info("**__**guardarListaChequeo: " + listaChequeo.toString());
                GuardarListaChequeoRutine g = new GuardarListaChequeoRutine();
                g.guardarListaChequeo(listaChequeo, entityManager);
            }

        }

    }

}
