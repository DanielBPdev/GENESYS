package com.asopagos.novedades.convertidores.persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarNovedadPila;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
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
public class ActualizarBeneficiarioNovedadPersona implements NovedadCore {

    private List<TipoTransaccionEnum> conyugeLabora;
    private List<TipoTransaccionEnum> ingresoMensual;
    private List<TipoTransaccionEnum> activarBeneficiarioConyuge;
    private List<TipoTransaccionEnum> activarBeneficiarioHijo;
    private List<TipoTransaccionEnum> activarBeneficiarioPadre;
    private List<TipoTransaccionEnum> inactivarBeneficiarioConyuge;
    private List<TipoTransaccionEnum> inactivarBeneficiarioHijosPadres;
    private List<TipoTransaccionEnum> actualizacionCertificadosEstudiosEscolaridad;
    private List<TipoTransaccionEnum> retiroBeneficiario;



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
            if (datosPersona.getOcupacionProfesionPadre() != null
                    && datosPersona.getOcupacionProfesionPadre().getIdOcupacionProfesion() != null) {
                beneficiarioDTO.setIdOcupacionProfesion(datosPersona.getOcupacionProfesionPadre().getIdOcupacionProfesion());
            }
        }
    }

    /**
     * Asigna los datos de Inactivación.
     * @param beneficiarioDTO
     * @param datosPersona
     */
    private void asignarDatosInactivacionBeneficiario(BeneficiarioModeloDTO beneficiarioDTO, DatosPersonaNovedadDTO datosPersona) {
        /* Datos Beneficiario. */
        beneficiarioDTO.setMotivoDesafiliacion(datosPersona.getMotivoDesafiliacionBeneficiario());
        if (datosPersona.getMotivoDesafiliacionBeneficiario().equals(MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO)) {
            beneficiarioDTO.setFechaDefuncion(datosPersona.getFechaDefuncion());
            if (datosPersona.getFechaReporteFallecimientoTrabajador() == null) {
                beneficiarioDTO.setFechaFallecido(new Date().getTime());
            } else {
                beneficiarioDTO.setFechaFallecido(datosPersona.getFechaReporteFallecimientoTrabajador());
            }
            beneficiarioDTO.setFallecido(Boolean.TRUE);
        }
//        if (datosPersona.getFechaInactivacionBeneficiario() != null) {
//            beneficiarioDTO.setFechaRetiro(datosPersona.getFechaInactivacionBeneficiario());
//        } else {
        beneficiarioDTO.setFechaRetiro(datosPersona.getFechaRetiro());
//        }
        beneficiarioDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
        beneficiarioDTO.setEstadoBeneficiarioCaja(EstadoAfiliadoEnum.INACTIVO);
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
        /* Novedad 1 Back */
        activarBeneficiarioConyuge = new ArrayList<>();
        activarBeneficiarioConyuge.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB);
        activarBeneficiarioConyuge.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL);
        activarBeneficiarioConyuge.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB);
        /* Novedad 2 - 6 Back */
        activarBeneficiarioHijo = new ArrayList<>();
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_WEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
        activarBeneficiarioHijo.add(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB);
        /* Novedad 7 - 8 Back */
        activarBeneficiarioPadre = new ArrayList<>();
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB);
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL);
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_WEB);
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB);
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL);
        activarBeneficiarioPadre.add(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_WEB);

        /* Novedad 9 Back */
        inactivarBeneficiarioConyuge = new ArrayList<>();
        inactivarBeneficiarioConyuge.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB);
        inactivarBeneficiarioConyuge.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL);
        inactivarBeneficiarioConyuge.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_WEB);

        /* Novedad 10 - 14 Back */
        inactivarBeneficiarioHijosPadres = new ArrayList<>();
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_WEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_DEPWEB);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL);
        inactivarBeneficiarioHijosPadres.add(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_WEB);

        /* Novedad 17 - 26 Back */
        actualizacionCertificadosEstudiosEscolaridad = new ArrayList<>();
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_WEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_WEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_WEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad.add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_DEPWEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_WEB);
        actualizacionCertificadosEstudiosEscolaridad
                .add(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_DEPWEB);

        /* Novedad 249 - 256 back */
        retiroBeneficiario = new ArrayList<>();
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_CONYUGE);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_HJO_BIOLOGICO);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_HIJASTRO);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_HERMANO_HUERFANO);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_HIJO_ADOPTIVO);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_BENEFICIARIO_CUSTODIA);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_PADRE);
        retiroBeneficiario.add(TipoTransaccionEnum.RETIRO_MADRE);
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
        
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
      //  Date fechaRetiro = new Date(TimeUnit.SECONDS.toMillis(datosPersona.getFechaInicioNovedad()));
        System.out.println("datosPersona.getFechaInicioNovedad: "+datosPersona.getFechaInicioNovedad());
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        /* Asocia los tipos de Novedad de Persona. */
        this.agregarListaNovedades();
        List<BeneficiarioModeloDTO> beneficiarioModeloDTOConsulta = new ArrayList<>();
        /* Se crea el Objeto BeneficiarioDTO */
        BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        if ((activarBeneficiarioPadre.contains(novedad) || activarBeneficiarioHijo.contains(novedad) || activarBeneficiarioConyuge.contains(novedad)) && datosPersona.getIdBeneficiario() == null) {
            System.out.println("entre idBeneficiario");
            ConsultarBeneficiarioTipoNroIdentificacion consultaBeneficiarioTipoNroIdentificacion = new ConsultarBeneficiarioTipoNroIdentificacion(datosPersona.getNumeroIdentificacionBeneficiario(), datosPersona.getTipoIdentificacionBeneficiario());
            consultaBeneficiarioTipoNroIdentificacion.execute();
            beneficiarioModeloDTOConsulta = consultaBeneficiarioTipoNroIdentificacion.getResult();
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

        // Contiene la información de vigencia del certificado
        NovedadDetalleModeloDTO novedadDetalleModeloDTO = new NovedadDetalleModeloDTO();

        /* se asocia el afiliado principal al beneficiario */
        beneficiarioModeloDTO.setIdAfiliado(afiliadoModeloDTO.getIdAfiliado());
        
        /* Por defecto no se actualiza el certificado escolar, solo en las novedades que asi lo requieran*/
        beneficiarioModeloDTO.setActualizarCertificado(Boolean.FALSE);
        
        
        if (conyugeLabora.contains(novedad)) {
            beneficiarioModeloDTO.setLabora(datosPersona.getConyugeLabora());
            /* Novedad 132 Front. */
        }
        else if (ingresoMensual.contains(novedad)) {
            beneficiarioModeloDTO.setSalarioMensualBeneficiario(datosPersona.getValorIngresoMensualConyuge());
            validarPostuladoFovis = true;
            /* Novedad 1 Back */
        }
        else if (activarBeneficiarioConyuge.contains(novedad)) { 
            //mantis264260

            EstadoCivilEnum estadoAnteriorAfiliado = EstadoCivilEnum.valueOf(afiliadoModeloDTO.getEstadoCivil().name());
            beneficiarioModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilBeneficiario());
            afiliadoModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilBeneficiario());

            // glpi 71392 - se valida el estado civil novedad web
            // Si llegma sin marcacion entonces
            if (solicitudNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB)
                && estadoAnteriorAfiliado.equals(datosPersona.getEstadoCivilTrabajador())
                ) {
                beneficiarioModeloDTO.setEstadoCivil(EstadoCivilEnum.CASADO);
                afiliadoModeloDTO.setEstadoCivil(EstadoCivilEnum.CASADO);
            }

            if (solicitudNovedadDTO.getTipoTransaccion().equals(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB)
                && !estadoAnteriorAfiliado.equals(datosPersona.getEstadoCivilTrabajador())
                ) {
                beneficiarioModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilTrabajador());
                afiliadoModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilTrabajador());
            }

            this.asignarDatosComunesActivacion(beneficiarioModeloDTO, datosPersona);
            this.asignarDatosActivacionConyuge(beneficiarioModeloDTO, datosPersona);
            //Mantis 265205
            this.asignarFechaExpedicionDocumento(beneficiarioModeloDTO, datosPersona, TipoBeneficiarioEnum.CONYUGE);
            /*CCREPNORMATIVOS*/
//            afiliadoModeloDTO.setEstadoCivil(EstadoCivilEnum.CASADO_UNION_LIBRE);
            addGrupoFamiliar = true;
            actulizaSolicitudNovedad = true;
            validarPostuladoFovis = true;
            /* Novedad 2 - 6 Back */
            // Se realiza la consulta por medio del utilitario
            List<ParametroConsultaDTO> listaParametros = new ArrayList<>();
            ParametroConsultaDTO dto = new ParametroConsultaDTO();
            dto.setEntityManager(entityManager);
            dto.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
            dto.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
            listaParametros.add(dto);
            List<PersonaPostulacionDTO> listPostulaciones = PersonasFOVISUtils.consultarPostulacionVigente(listaParametros);

            if (listPostulaciones != null && !listPostulaciones.isEmpty()) {
                //System.out.println("Lo validó!!");
                List<PersonaDTO> listaPersonas = new ArrayList<>();
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
                personaDTO.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
                listaPersonas.add(personaDTO);

                VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                        solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
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
        else if (activarBeneficiarioHijo.contains(novedad)) {
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
        else if (activarBeneficiarioPadre.contains(novedad)) {
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
        else if (inactivarBeneficiarioConyuge.contains(novedad)) {
            /* Datos de Persona */
            if (datosPersona.getEstadoCivilConyuge() != null) {
                beneficiarioModeloDTO.setEstadoCivil(datosPersona.getEstadoCivilConyuge());
            }
            if (datosPersona.getFechaFinsociedadConyugal() != null) {
                beneficiarioModeloDTO.setFechaFinSociedadConyugal(datosPersona.getFechaFinsociedadConyugal());
            }
            afiliadoModeloDTO.setCabezaHogar(Boolean.TRUE);
            if (datosPersona.getMotivoDesafiliacionBeneficiario().equals(MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO)) {
                afiliadoModeloDTO.setEstadoCivil(EstadoCivilEnum.VIUDO);
            } else if(Boolean.TRUE.equals(afiliadoModeloDTO.getFallecido())) {
                beneficiarioModeloDTO.setEstadoCivil(EstadoCivilEnum.VIUDO);
            }else {
                afiliadoModeloDTO.setEstadoCivil(EstadoCivilEnum.SEPARADO);
            }
            this.asignarDatosInactivacionBeneficiario(beneficiarioModeloDTO, datosPersona);
            validarPostuladoFovis = true;
            ConsultarCondicionInvalidezConyugeCuidador consultarCondicionInvalidezConyugeCuidador = new ConsultarCondicionInvalidezConyugeCuidador(beneficiarioModeloDTO.getTipoIdentificacion(), beneficiarioModeloDTO.getNumeroIdentificacion());
            consultarCondicionInvalidezConyugeCuidador.execute();

            List<CondicionInvalidez> listaCondicionInvalidez = consultarCondicionInvalidezConyugeCuidador.getResult();
            for(CondicionInvalidez condicionInvalidez : listaCondicionInvalidez) {
                CondicionInvalidezModeloDTO condicionInvalidezModeloDTO = new CondicionInvalidezModeloDTO();
                condicionInvalidezModeloDTO.convertToDTO(condicionInvalidez);
                condicionInvalidezModeloDTO.setFechaFinConyugeCuidador(new Date().getTime());
                condicionInvalidezModeloDTO.setConyugeCuidador(false);
                ActualizarCondicionInvalidez actualizarCondicionInvalidez = new ActualizarCondicionInvalidez(condicionInvalidezModeloDTO);
                actualizarCondicionInvalidez.execute();
            }
            /* Novedad 10 - 16 Back */
        }
        else if (inactivarBeneficiarioHijosPadres.contains(novedad)) {
            this.asignarDatosInactivacionBeneficiario(beneficiarioModeloDTO, datosPersona);
            validarPostuladoFovis = true;
            if((datosPersona.getConyugeCuidadorTrabajador() != null && datosPersona.getConyugeCuidadorTrabajador() == true) || 
            (datosPersona.getConyugeCuidadorHijo() != null && datosPersona.getConyugeCuidadorHijo() == true) ||
            (datosPersona.getConyugeCuidadorPadre() != null && datosPersona.getConyugeCuidadorPadre() == true)){
                conyugeCuidador= false;
                fechaFinConyugeCuidador = new Date().getTime(); 
            }
            
            /* Novedad 17 - 26 Back */
        }
        else if (actualizacionCertificadosEstudiosEscolaridad.contains(novedad)) {
            this.asignarActualizacionCertificadoEstudios(beneficiarioModeloDTO, datosPersona, novedadDetalleModeloDTO);
            novedadDetalleModeloDTO.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
            /* Novedad 201 - 204 back */
        }
        else if (retiroBeneficiario.contains(novedad)) {
            /*
             * Mantis 230815 se setea a estado inactivo el estado del afiliado
             * y de la caja
             */
            beneficiarioModeloDTO.setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum.INACTIVO);
            beneficiarioModeloDTO.setEstadoBeneficiarioCaja(EstadoAfiliadoEnum.INACTIVO);
            beneficiarioModeloDTO.setMotivoDesafiliacion(datosPersona.getMotivoDesafiliacionBeneficiario());
        }
        // Se verifica si se debe agregar un nuevo grupo familiar
        if (addGrupoFamiliar && beneficiarioModeloDTO.getIdGrupoFamiliar() == null) {
            grupoFamiliar = datosPersona.getGrupoFamiliarBeneficiario();
        }
        if(beneficiarioModeloDTO.getEstadoBeneficiarioAfiliado() == EstadoAfiliadoEnum.INACTIVO){
              System.out.println("**__** INACTIVANDO AL BENEFICIARIO getFechaInactivacionBeneficiario: "+solicitudNovedadDTO.getDatosPersona().getFechaInactivacionBeneficiario());
            if(solicitudNovedadDTO.getDatosPersona().getFechaInactivacionBeneficiario() != null){
            beneficiarioModeloDTO.setFechaRetiro(solicitudNovedadDTO.getDatosPersona().getFechaInactivacionBeneficiario());
            }else{
                  System.out.println("**__** INACTIVANDO AL BENEFICIARIO: datosPersona.getFechaRetiro() "+datosPersona.getFechaRetiro());
                beneficiarioModeloDTO.setFechaRetiro(datosPersona.getFechaRetiro());
            }
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

        //EjecutarActualizacionBeneficiario actualizarBeneficiarioService = new EjecutarActualizacionBeneficiario(beneficiarioGrupoAfiliado);        
        n.ejecutarActualizacionBeneficiario(beneficiarioGrupoAfiliado);
    }

}
