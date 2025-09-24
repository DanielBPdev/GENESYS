package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.cartera.GestionCarteraDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de una persona *
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaDTO implements Serializable {

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private String nombreCompleto;

    private Long idPersona;

    private Long fechaNacimiento;

    private Long idPersonaDetalle;

    private Boolean fallecido;

    private EstadoAfiliadoEnum estadoAfiliadoCaja;

    private ClasificacionEnum clasificacion;

    private TipoTransaccionEnum tipoTransaccion;

    private Short digitoVerificacion;

    private String razonSocial;

    private Long idAfiliado;

    private Boolean afiliable;

    private EstadoCivilEnum estadoCivil;

    private Date fechaExpedicionDocumento;

    private GeneroEnum genero;

    private Integer idOcupacionProfesion;

    private NivelEducativoEnum nivelEducativo;

    private Boolean cabezaHogar;

    private Boolean habitaCasaPropia;

    private Boolean autorizacionEnvioEmail;

    private MedioDePagoModeloDTO medioDePagoPersona;

    private UbicacionDTO ubicacionDTO;

    private Boolean autorizaUsoDatosPersonales;

    private Boolean resideSectorRural;

    private Date fechaIngreso;

    private Date fechaRetiro;

    private EstadoAfiliadoEnum estadoAfiliadoRol;

    private Short gradoAcademico;

    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    private Boolean creadoPorPila;

    private Date fechaFallecido;

    private Boolean beneficiarioSubsidio;

    private Date fechaDefuncion;

    private Boolean estudianteTrabajoDesarrolloHumano;

    private Long idPersonaPadre;

    private Long idPersonaMadre;

    private Boolean precargar;

    private OrientacionSexualEnum orientacionSexual;

    private FactorVulnerabilidadEnum factorVulnerabilidad;

    private PertenenciaEtnicaEnum pertenenciaEtnica;

    private Long idPaisResidencia;

    private String centroTrabajo;

    /**
     * Estado de cartera que tiene la persona
     */
    private EstadoCarteraEnum estadoCartera;
    /**
     * GestionCarteraDTO tiene la información de cartera como los números de
     * operación o líneas de cobro de la persona
     */
    private GestionCarteraDTO gestionCartera;

    private Long idBeneficiario;

    private Long idGrupoFamiliar;

    private Long idResguardo;

    private Long idPuebloIndigena;

    private Boolean encVista360;

    private String tipoAfiliacion;

    public PersonaDTO() {
    }

    public PersonaDTO(Persona persona, PersonaDetalle personaDetalle, Ubicacion ubicacion, Municipio municipio) {
        convertToDTO(persona, personaDetalle, ubicacion, municipio);
    }
    
    public PersonaDTO(Persona persona, PersonaDetalle personaDetalle) {
        convertToDTO(persona, personaDetalle);
    }

    public PersonaDTO(Persona persona, RolAfiliado rolAfiliado) {
        this.setIdPersona(persona.getIdPersona());
        convertToDTO(persona, null);
        this.setEstadoAfiliadoCaja(rolAfiliado.getEstadoAfiliado());
    }

    public PersonaDTO(Persona persona, PersonaDetalle personaDetalle, RolAfiliado rolAfiliado) {
        convertToDTO(persona, personaDetalle, rolAfiliado);
    }

    /**
     * Constructor con persona
     * 
     * @param persona
     *                Persona base
     */
    public PersonaDTO(Persona persona) {
        this.setIdPersona(persona.getIdPersona());
        convertToDTO(persona, null);
    }

    private void convertToDTO(Persona persona, PersonaDetalle personaDetalle) {
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setPrimerNombre(persona.getPrimerNombre());
        this.setSegundoNombre(persona.getSegundoNombre());
        this.setPrimerApellido(persona.getPrimerApellido());
        this.setSegundoApellido(persona.getSegundoApellido());
        this.setRazonSocial(persona.getRazonSocial());
        this.setDigitoVerificacion(persona.getDigitoVerificacion());
        this.setCreadoPorPila(persona.getCreadoPorPila());
        this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
        this.setIdPersona(persona.getIdPersona());
        if (personaDetalle != null) {
            this.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
            this.setIdPersona(personaDetalle.getIdPersona());
            this.setFechaNacimiento(
                    personaDetalle.getFechaNacimiento() != null ? personaDetalle.getFechaNacimiento().getTime() : null);
            this.setGenero(personaDetalle.getGenero());
            this.setFallecido(personaDetalle.getFallecido());
            this.setIdOcupacionProfesion(personaDetalle.getIdOcupacionProfesion());
            this.setNivelEducativo(personaDetalle.getNivelEducativo());
            this.setCabezaHogar(personaDetalle.getCabezaHogar());
            this.setHabitaCasaPropia(personaDetalle.getHabitaCasaPropia());
            this.setFechaExpedicionDocumento(personaDetalle.getFechaExpedicionDocumento());
            this.setEstadoCivil(personaDetalle.getEstadoCivil());
            this.setGradoAcademico(personaDetalle.getGradoAcademico());
            this.setResideSectorRural(personaDetalle.getResideSectorRural());
            this.setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
            this.setFechaFallecido(personaDetalle.getFechaFallecido());
            this.setBeneficiarioSubsidio(personaDetalle.getBeneficiarioSubsidio());
            this.setFechaDefuncion(personaDetalle.getFechaDefuncion());
            this.setEstudianteTrabajoDesarrolloHumano(personaDetalle.getEstudianteTrabajoDesarrolloHumano());
            this.setIdPersonaPadre(personaDetalle.getIdPersonaPadre());
            this.setIdPersonaMadre(personaDetalle.getIdPersonaMadre());
            this.setOrientacionSexual(personaDetalle.getOrientacionSexual());
            this.setPertenenciaEtnica(personaDetalle.getPertenenciaEtnica());
            this.setFactorVulnerabilidad(personaDetalle.getFactorVulnerabilidad());
            this.setIdPaisResidencia(personaDetalle.getIdPaisResidencia());
            this.setIdResguardo(personaDetalle.getIdResguardo());
            this.setIdPuebloIndigena(personaDetalle.getIdPuebloIndigena());
        }
        if (persona.getUbicacionPrincipal() != null) {
            if (persona.getUbicacionPrincipal().getAutorizacionEnvioEmail() != null) {
                this.setAutorizacionEnvioEmail(persona.getUbicacionPrincipal().getAutorizacionEnvioEmail());
            }
            this.setUbicacionDTO(UbicacionDTO.obtenerUbicacionDTO(persona.getUbicacionPrincipal()));
        }
    }
    
    private void convertToDTO(Persona persona, PersonaDetalle personaDetalle, Ubicacion ubicacion, Municipio municipio) {
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setPrimerNombre(persona.getPrimerNombre());
        this.setSegundoNombre(persona.getSegundoNombre());
        this.setPrimerApellido(persona.getPrimerApellido());
        this.setSegundoApellido(persona.getSegundoApellido());
        this.setRazonSocial(persona.getRazonSocial());
        this.setDigitoVerificacion(persona.getDigitoVerificacion());
        this.setCreadoPorPila(persona.getCreadoPorPila());
        this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
        this.setIdPersona(persona.getIdPersona());
        if (personaDetalle != null) {
            this.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
            this.setIdPersona(personaDetalle.getIdPersona());
            this.setFechaNacimiento(
                    personaDetalle.getFechaNacimiento() != null ? personaDetalle.getFechaNacimiento().getTime() : null);
            this.setGenero(personaDetalle.getGenero());
            this.setFallecido(personaDetalle.getFallecido());
            this.setIdOcupacionProfesion(personaDetalle.getIdOcupacionProfesion());
            this.setNivelEducativo(personaDetalle.getNivelEducativo());
            this.setCabezaHogar(personaDetalle.getCabezaHogar());
            this.setHabitaCasaPropia(personaDetalle.getHabitaCasaPropia());
            this.setFechaExpedicionDocumento(personaDetalle.getFechaExpedicionDocumento());
            this.setEstadoCivil(personaDetalle.getEstadoCivil());
            this.setGradoAcademico(personaDetalle.getGradoAcademico());
            this.setResideSectorRural(personaDetalle.getResideSectorRural());
            this.setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
            this.setFechaFallecido(personaDetalle.getFechaFallecido());
            this.setBeneficiarioSubsidio(personaDetalle.getBeneficiarioSubsidio());
            this.setFechaDefuncion(personaDetalle.getFechaDefuncion());
            this.setEstudianteTrabajoDesarrolloHumano(personaDetalle.getEstudianteTrabajoDesarrolloHumano());
            this.setIdPersonaPadre(personaDetalle.getIdPersonaPadre());
            this.setIdPersonaMadre(personaDetalle.getIdPersonaMadre());
            this.setOrientacionSexual(personaDetalle.getOrientacionSexual());
            this.setPertenenciaEtnica(personaDetalle.getPertenenciaEtnica());
            this.setFactorVulnerabilidad(personaDetalle.getFactorVulnerabilidad());
            this.setIdPaisResidencia(personaDetalle.getIdPaisResidencia());
            this.setIdResguardo(personaDetalle.getIdResguardo());
            this.setIdPuebloIndigena(personaDetalle.getIdPuebloIndigena());
        }
        if (ubicacion != null) {
            if (ubicacion.getAutorizacionEnvioEmail() != null) {
                this.setAutorizacionEnvioEmail(ubicacion.getAutorizacionEnvioEmail());
            }
            this.setUbicacionDTO(UbicacionDTO.obtenerUbicacionDTO(ubicacion, municipio));
        }
    }

    private void convertToDTO(Persona persona, PersonaDetalle personaDetalle, RolAfiliado rolAfiliado) {
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setPrimerNombre(persona.getPrimerNombre());
        this.setSegundoNombre(persona.getSegundoNombre());
        this.setPrimerApellido(persona.getPrimerApellido());
        this.setSegundoApellido(persona.getSegundoApellido());
        this.setRazonSocial(persona.getRazonSocial());
        this.setDigitoVerificacion(persona.getDigitoVerificacion());
        this.setCreadoPorPila(persona.getCreadoPorPila());
        this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(persona));
        this.setIdPersona(persona.getIdPersona());
        if (personaDetalle != null) {
            this.setFechaNacimiento(
                    personaDetalle.getFechaNacimiento() != null ? personaDetalle.getFechaNacimiento().getTime() : null);
            this.setGenero(personaDetalle.getGenero());
            this.setFallecido(personaDetalle.getFallecido());
            this.setIdOcupacionProfesion(personaDetalle.getIdOcupacionProfesion());
            this.setNivelEducativo(personaDetalle.getNivelEducativo());
            this.setCabezaHogar(personaDetalle.getCabezaHogar());
            this.setHabitaCasaPropia(personaDetalle.getHabitaCasaPropia());
            this.setFechaExpedicionDocumento(personaDetalle.getFechaExpedicionDocumento());
            this.setEstadoCivil(personaDetalle.getEstadoCivil());
            this.setGradoAcademico(personaDetalle.getGradoAcademico());
            this.setResideSectorRural(personaDetalle.getResideSectorRural());
            this.setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
            this.setFechaFallecido(personaDetalle.getFechaFallecido());
            this.setBeneficiarioSubsidio(personaDetalle.getBeneficiarioSubsidio());
            this.setFechaDefuncion(personaDetalle.getFechaDefuncion());
            this.setEstudianteTrabajoDesarrolloHumano(personaDetalle.getEstudianteTrabajoDesarrolloHumano());
            this.setIdPersonaPadre(personaDetalle.getIdPersonaPadre());
            this.setIdPersonaMadre(personaDetalle.getIdPersonaMadre());
            this.setOrientacionSexual(personaDetalle.getOrientacionSexual());
            this.setPertenenciaEtnica(personaDetalle.getPertenenciaEtnica());
            this.setFactorVulnerabilidad(personaDetalle.getFactorVulnerabilidad());
            this.setIdPaisResidencia(personaDetalle.getIdPaisResidencia());
            this.setIdResguardo(personaDetalle.getIdResguardo());
            this.setIdPuebloIndigena(personaDetalle.getIdPuebloIndigena());
        }
        if (persona.getUbicacionPrincipal() != null) {
            if (persona.getUbicacionPrincipal().getAutorizacionEnvioEmail() != null) {
                this.setAutorizacionEnvioEmail(persona.getUbicacionPrincipal().getAutorizacionEnvioEmail());
            }
            this.setUbicacionDTO(UbicacionDTO.obtenerUbicacionDTO(persona.getUbicacionPrincipal()));
        }
        if (rolAfiliado != null) {
            this.setEstadoAfiliadoRol(rolAfiliado.getEstadoAfiliado());
            this.setFechaIngreso(rolAfiliado.getFechaIngreso());
            this.setFechaRetiro(rolAfiliado.getFechaRetiro());
        }
    }

    public PersonaDTO(PersonaModeloDTO personaModeloDto) {
        this.setTipoIdentificacion(personaModeloDto.getTipoIdentificacion());
        this.setNumeroIdentificacion(personaModeloDto.getNumeroIdentificacion());
        this.setPrimerNombre(personaModeloDto.getPrimerNombre());
        this.setSegundoNombre(personaModeloDto.getSegundoNombre());
        this.setPrimerApellido(personaModeloDto.getPrimerApellido());
        this.setSegundoApellido(personaModeloDto.getSegundoApellido());
        this.setIdPersona(personaModeloDto.getIdPersona());
        this.setFechaNacimiento(personaModeloDto.getFechaNacimiento());
        this.setGenero(personaModeloDto.getGenero());
        this.setFallecido(personaModeloDto.getFallecido());
        this.setIdOcupacionProfesion(personaModeloDto.getIdOcupacionProfesion());
        this.setNivelEducativo(personaModeloDto.getNivelEducativo());
        this.setCabezaHogar(personaModeloDto.getCabezaHogar());
        this.setHabitaCasaPropia(personaModeloDto.getHabitaCasaPropia());
        this.setEstadoCivil(personaModeloDto.getEstadoCivil());
        this.setGradoAcademico(personaModeloDto.getGradoAcademico());
        this.setResideSectorRural(personaModeloDto.getResideSectorRural());
        this.setAutorizaUsoDatosPersonales(personaModeloDto.getAutorizaUsoDatosPersonales());
        this.setFechaFallecido(
                personaModeloDto.getFechaFallecido() != null ? new Date(personaModeloDto.getFechaFallecido()) : null);
        this.setDigitoVerificacion(personaModeloDto.getDigitoVerificacion());
        this.setFechaExpedicionDocumento(
                personaModeloDto.getFechaExpedicionDocumento() != null
                        ? new Date(personaModeloDto.getFechaExpedicionDocumento())
                        : null);
        this.setRazonSocial(personaModeloDto.getRazonSocial());
        this.setCreadoPorPila(personaModeloDto.getCreadoPorPila());
        this.setBeneficiarioSubsidio(personaModeloDto.getBeneficiarioSubsidio());
        this.setFechaDefuncion(
                personaModeloDto.getFechaDefuncion() != null ? new Date(personaModeloDto.getFechaDefuncion()) : null);
        if (personaModeloDto.getUbicacionModeloDTO() != null) {
            if (personaModeloDto.getUbicacionModeloDTO().getAutorizacionEnvioEmail() != null) {
                this.setAutorizacionEnvioEmail(personaModeloDto.getUbicacionModeloDTO().getAutorizacionEnvioEmail());
            }
            this.setUbicacionDTO(
                    UbicacionDTO.obtenerUbicacionDTO(personaModeloDto.getUbicacionModeloDTO().convertToEntity()));
        }
        this.setEstudianteTrabajoDesarrolloHumano(personaModeloDto.getEstudianteTrabajoDesarrolloHumano());
        this.setIdPersonaPadre(
                personaModeloDto.getIdPersonaPadre() != null ? personaModeloDto.getIdPersonaPadre() : null);
        this.setIdPersonaMadre(
                personaModeloDto.getIdPersonaMadre() != null ? personaModeloDto.getIdPersonaMadre() : null);
        this.setOrientacionSexual(personaModeloDto.getOrientacionSexual());
        this.setPertenenciaEtnica(personaModeloDto.getPertenenciaEtnica());
        this.setFactorVulnerabilidad(personaModeloDto.getFactorVulnerabilidad());
        this.setIdPaisResidencia(personaModeloDto.getIdPaisResidencia());
        this.setIdResguardo(personaModeloDto.getIdResguardo());
        this.setIdPuebloIndigena(personaModeloDto.getIdPuebloIndigena());
    }

    /**
     * Metodo encargado de convertir una entidad persona a un DTO
     * 
     * @param persona,
     *                 Persona a capturar los datos
     * @return dto de persona
     */
    public static PersonaDTO convertPersonaToDTO(Persona persona, PersonaDetalle personaDetalle) {
        return new PersonaDTO(persona, personaDetalle);
    }
    
    public static PersonaDTO convertPersonaToDTO(Persona persona, PersonaDetalle personaDetalle, Ubicacion ubicacion, Municipio municipio) {
        return new PersonaDTO(persona, personaDetalle, ubicacion, municipio);
    }

    /**
     * Metodo encargado de setear los datos de una persona DTO a un objeto de
     * tipo entidad persona
     * 
     * @param persona,
     *                 persona a seterar los datos
     * @return persona con los datos cargados
     */
    public Persona obtenerDatosPersona(Persona persona) {
        if (getTipoIdentificacion() != null) {
            persona.setTipoIdentificacion(getTipoIdentificacion());
        }
        if (getNumeroIdentificacion() != null && !getNumeroIdentificacion().isEmpty()) {
            persona.setNumeroIdentificacion(getNumeroIdentificacion());
        }
        if (getDigitoVerificacion() != null) {
            persona.setDigitoVerificacion(getDigitoVerificacion());
        }
        if (getPrimerNombre() != null && !getPrimerNombre().isEmpty()) {
            persona.setPrimerNombre(getPrimerNombre());
        }
        if (getSegundoNombre() != null && !getSegundoNombre().isEmpty()) {
            persona.setSegundoNombre(getSegundoNombre());
        }
        if (getPrimerApellido() != null && !getPrimerApellido().isEmpty()) {
            persona.setPrimerApellido(getPrimerApellido());
        }
        if (getSegundoApellido() != null && !getSegundoApellido().isEmpty()) {
            persona.setSegundoApellido(getSegundoApellido());
        }
        if (getRazonSocial() != null) {
            persona.setRazonSocial(getRazonSocial());
        }
        if (this.getCreadoPorPila() != null) {
            persona.setCreadoPorPila(this.getCreadoPorPila());
        }
        return persona;
    }

    /**
     * Metodo encargado de setear los datos de una persona DTO a un objeto de
     * tipo entidad persona detalle
     * 
     * @param personaDetalle,
     *                        persona a seterar los datos
     * @return persona detalle con los datos cargados
     */
    public PersonaDetalle obtenerDatosPersonaDetalle(PersonaDetalle personaDetalle) {
        if (getIdPersona() != null) {
            personaDetalle.setIdPersona(getIdPersona());
        }
        if (getFechaNacimiento() != null) {
            personaDetalle.setFechaNacimiento(new Date(getFechaNacimiento()));
        }
        if (getEstadoCivil() != null) {
            personaDetalle.setEstadoCivil(getEstadoCivil());
        }
        if (getFechaExpedicionDocumento() != null) {
            personaDetalle.setFechaExpedicionDocumento(getFechaExpedicionDocumento());
        }
        if (getGenero() != null) {
            personaDetalle.setGenero(getGenero());
        }
        if (getIdOcupacionProfesion() != null) {
            personaDetalle.setIdOcupacionProfesion(getIdOcupacionProfesion());
        }
        if (getNivelEducativo() != null) {
            personaDetalle.setNivelEducativo(getNivelEducativo());
        }
        if (getCabezaHogar() != null) {
            personaDetalle.setCabezaHogar(getCabezaHogar());
        }
        if (getHabitaCasaPropia() != null) {
            personaDetalle.setHabitaCasaPropia(getHabitaCasaPropia());
        }
        if (getAutorizaUsoDatosPersonales() != null) {
            personaDetalle.setAutorizaUsoDatosPersonales(getAutorizaUsoDatosPersonales());
        }
        if (getFallecido() != null) {
            personaDetalle.setFallecido(getFallecido());
        }
        if (getGradoAcademico() != null) {
            personaDetalle.setGradoAcademico(getGradoAcademico());
        }
        if (getResideSectorRural() != null) {
            personaDetalle.setResideSectorRural(getResideSectorRural());
        }
        if (this.getFechaFallecido() != null) {
            personaDetalle.setFechaFallecido(this.getFechaFallecido());
        }
        if (this.getBeneficiarioSubsidio() != null) {
            personaDetalle.setBeneficiarioSubsidio(this.getBeneficiarioSubsidio());
        }
        if (this.getFechaDefuncion() != null) {
            personaDetalle.setFechaDefuncion(this.getFechaDefuncion());
        }
        if (this.getEstudianteTrabajoDesarrolloHumano() != null) {
            personaDetalle.setEstudianteTrabajoDesarrolloHumano(this.getEstudianteTrabajoDesarrolloHumano());
        }
        if (this.getIdPersonaPadre() != null) {
            personaDetalle.setIdPersonaPadre(this.getIdPersonaPadre());
        }
        if (this.getIdPersonaMadre() != null) {
            personaDetalle.setIdPersonaMadre(this.getIdPersonaMadre());
        }
        if (getOrientacionSexual() != null) {
            personaDetalle.setOrientacionSexual(getOrientacionSexual());
        }
        if (getPertenenciaEtnica() != null) {
            personaDetalle.setPertenenciaEtnica(getPertenenciaEtnica());
        }
        if (getFactorVulnerabilidad() != null) {
            personaDetalle.setFactorVulnerabilidad(getFactorVulnerabilidad());
        }
        if (getIdPaisResidencia() != null) {
            personaDetalle.setIdPaisResidencia(getIdPaisResidencia());
        }
        if (getIdResguardo() != null) {
            personaDetalle.setIdResguardo(getIdResguardo());
        }
        if (getIdPuebloIndigena() != null) {
            personaDetalle.setIdPuebloIndigena(getIdPuebloIndigena());
        }

        return personaDetalle;
    }

    /**
     * Método encargado de obtener un DTO de persona mediante un afiliado
     * 
     * @param afiliado,
     *                  dato de ingreso
     * @return persona dto
     */
    public static PersonaDTO obtenerPersonaDTODeAfiliado(Afiliado afiliado, PersonaDetalle personaDetalle) {
        if (afiliado != null) {
            PersonaDTO personaDTO = new PersonaDTO();
            personaDTO = convertPersonaToDTO(afiliado.getPersona(), personaDetalle);
            personaDTO.setIdAfiliado(afiliado.getIdAfiliado());
            return personaDTO;
        }
        return null;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *                           the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *                             the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the fechaNacimiento
     */
    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Long getIdPersonaDetalle() {
        return this.idPersonaDetalle;
    }

    public void setIdPersonaDetalle(Long idPersonaDetalle) {
        this.idPersonaDetalle = idPersonaDetalle;
    }

    /**
     * @param fechaNacimiento
     *                        the fechaNacimiento to set
     */
    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the estadoAfiliadoCaja
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoCaja() {
        return estadoAfiliadoCaja;
    }

    /**
     * @param estadoAfiliadoCaja
     *                           the estadoAfiliadoCaja to set
     */
    public void setEstadoAfiliadoCaja(EstadoAfiliadoEnum estadoAfiliadoCaja) {
        this.estadoAfiliadoCaja = estadoAfiliadoCaja;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *                        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre
     *                     the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre
     *                      the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido
     *                       the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido
     *                        the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto
     *                       the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado
     *                   the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * @return the afiliable
     */
    public Boolean getAfiliable() {
        return afiliable;
    }

    /**
     * @param afiliable
     *                  the afiliable to set
     */
    public void setAfiliable(Boolean afiliable) {
        this.afiliable = afiliable;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *                      the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the digitoVerificacion
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion
     *                           the digitoVerificacion to set
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *                    the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the estadoCivil
     */
    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil
     *                    the estadoCivil to set
     */
    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the fechaExpedicionDocumento
     */
    public Date getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    /**
     * @param fechaExpedicionDocumento
     *                                 the fechaExpedicionDocumento to set
     */
    public void setFechaExpedicionDocumento(Date fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    /**
     * @return the genero
     */
    public GeneroEnum getGenero() {
        return genero;
    }

    /**
     * @param genero
     *               the genero to set
     */
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    /**
     * @return the idOcupacionProfesion
     */
    public Integer getIdOcupacionProfesion() {
        return idOcupacionProfesion;
    }

    /**
     * @param idOcupacionProfesion
     *                             the idOcupacionProfesion to set
     */
    public void setIdOcupacionProfesion(Integer idOcupacionProfesion) {
        this.idOcupacionProfesion = idOcupacionProfesion;
    }

    /**
     * @return the nivelEducativo
     */
    public NivelEducativoEnum getNivelEducativo() {
        return nivelEducativo;
    }

    /**
     * @param nivelEducativo
     *                       the nivelEducativo to set
     */
    public void setNivelEducativo(NivelEducativoEnum nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    /**
     * @return the cabezaHogar
     */
    public Boolean getCabezaHogar() {
        return cabezaHogar;
    }

    /**
     * @param cabezaHogar
     *                    the cabezaHogar to set
     */
    public void setCabezaHogar(Boolean cabezaHogar) {
        this.cabezaHogar = cabezaHogar;
    }

    /**
     * @return the habitaCasaPropia
     */
    public Boolean getHabitaCasaPropia() {
        return habitaCasaPropia;
    }

    /**
     * @param habitaCasaPropia
     *                         the habitaCasaPropia to set
     */
    public void setHabitaCasaPropia(Boolean habitaCasaPropia) {
        this.habitaCasaPropia = habitaCasaPropia;
    }

    /**
     * @return the autorizacionEnvioEmail
     */
    public Boolean getAutorizacionEnvioEmail() {
        return autorizacionEnvioEmail;
    }

    /**
     * @param autorizacionEnvioEmail
     *                               the autorizacionEnvioEmail to set
     */
    public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
    }

    /**
     * @return the medioDePagoPersona
     */
    public MedioDePagoModeloDTO getMedioDePagoPersona() {
        return medioDePagoPersona;
    }

    /**
     * @param medioDePagoPersona
     *                           the medioDePagoPersona to set
     */
    public void setMedioDePagoPersona(MedioDePagoModeloDTO medioDePagoPersona) {
        this.medioDePagoPersona = medioDePagoPersona;
    }

    /**
     * @return the ubicacionDTO
     */
    public UbicacionDTO getUbicacionDTO() {
        return ubicacionDTO;
    }

    /**
     * @param ubicacionDTO
     *                     the ubicacionDTO to set
     */
    public void setUbicacionDTO(UbicacionDTO ubicacionDTO) {
        this.ubicacionDTO = ubicacionDTO;
    }

    /**
     * @return the autorizaUsoDatosPersonales
     */
    public Boolean getAutorizaUsoDatosPersonales() {
        return autorizaUsoDatosPersonales;
    }

    /**
     * @param autorizaUsoDatosPersonales
     *                                   the autorizaUsoDatosPersonales to set
     */
    public void setAutorizaUsoDatosPersonales(Boolean autorizaUsoDatosPersonales) {
        this.autorizaUsoDatosPersonales = autorizaUsoDatosPersonales;
    }

    /**
     * @return the resideSectorRural
     */
    public Boolean getResideSectorRural() {
        return resideSectorRural;
    }

    /**
     * @param resideSectorRural
     *                          the resideSectorRural to set
     */
    public void setResideSectorRural(Boolean resideSectorRural) {
        this.resideSectorRural = resideSectorRural;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @return the estadoAfiliadoRol
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoRol() {
        return estadoAfiliadoRol;
    }

    /**
     * @param fechaIngreso
     *                     the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @param fechaRetiro
     *                    the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @param estadoAfiliadoRol
     *                          the estadoAfiliadoRol to set
     */
    public void setEstadoAfiliadoRol(EstadoAfiliadoEnum estadoAfiliadoRol) {
        this.estadoAfiliadoRol = estadoAfiliadoRol;
    }

    /**
     * @return the fallecido
     */
    public Boolean getFallecido() {
        return fallecido;
    }

    /**
     * @param fallecido
     *                  the fallecido to set
     */
    public void setFallecido(Boolean fallecido) {
        this.fallecido = fallecido;
    }

    /**
     * @return the gradoAcademico
     */
    public Short getGradoAcademico() {
        return gradoAcademico;
    }

    /**
     * @param gradoAcademico
     *                       the gradoAcademico to set
     */
    public void setGradoAcademico(Short gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona
     *                  the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * 
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * 
     * @param valor
     *              para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the creadoPorPila
     */
    public Boolean getCreadoPorPila() {
        return creadoPorPila;
    }

    /**
     * @param creadoPorPila
     *                      the creadoPorPila to set
     */
    public void setCreadoPorPila(Boolean creadoPorPila) {
        this.creadoPorPila = creadoPorPila;
    }

    /**
     * @return the fechaFallecido
     */
    public Date getFechaFallecido() {
        return fechaFallecido;
    }

    /**
     * @param fechaFallecido
     *                       the fechaFallecido to set
     */
    public void setFechaFallecido(Date fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    /**
     * @return the beneficiarioSubsidio
     */
    public Boolean getBeneficiarioSubsidio() {
        return beneficiarioSubsidio;
    }

    /**
     * @param beneficiarioSubsidio
     *                             the beneficiarioSubsidio to set
     */
    public void setBeneficiarioSubsidio(Boolean beneficiarioSubsidio) {
        this.beneficiarioSubsidio = beneficiarioSubsidio;
    }

    /**
     * @return the fechaDefuncion
     */
    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    /**
     * @param fechaDefuncion the fechaDefuncion to set
     */
    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public Boolean getEstudianteTrabajoDesarrolloHumano() {
        return estudianteTrabajoDesarrolloHumano;
    }

    public void setEstudianteTrabajoDesarrolloHumano(Boolean estudianteTrabajoDesarrolloHumano) {
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
    }

    /**
     * @return the idPersonaPadre
     */
    public Long getIdPersonaPadre() {
        return idPersonaPadre;
    }

    /**
     * @param idPersonaPadre the idPersonaPadre to set
     */
    public void setIdPersonaPadre(Long idPersonaPadre) {
        this.idPersonaPadre = idPersonaPadre;
    }

    /**
     * @return the idPersonaMadre
     */
    public Long getIdPersonaMadre() {
        return idPersonaMadre;
    }

    /**
     * @param idPersonaMadre the idPersonaMadre to set
     */
    public void setIdPersonaMadre(Long idPersonaMadre) {
        this.idPersonaMadre = idPersonaMadre;
    }

    /**
     * @return the precargar
     */
    public Boolean getPrecargar() {
        return precargar;
    }

    /**
     * @param precargar the precargar to set
     */
    public void setPrecargar(Boolean precargar) {
        this.precargar = precargar;
    }

    /**
     * @return
     */
    public OrientacionSexualEnum getOrientacionSexual() {
        return orientacionSexual;
    }

    /**
     * @param orientacionSexual the orientacionSexual to set
     */
    public void setOrientacionSexual(OrientacionSexualEnum orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    /**
     * @return
     */
    public FactorVulnerabilidadEnum getFactorVulnerabilidad() {
        return factorVulnerabilidad;
    }

    /**
     * @param factorVulnerabilidad the factorVulnerabilidad to set
     */
    public void setFactorVulnerabilidad(FactorVulnerabilidadEnum factorVulnerabilidad) {
        this.factorVulnerabilidad = factorVulnerabilidad;
    }

    /**
     * @return
     */
    public PertenenciaEtnicaEnum getPertenenciaEtnica() {
        return pertenenciaEtnica;
    }

    /**
     * @param pertenenciaEtnica the pertenenciaEtnica to set
     */
    public void setPertenenciaEtnica(PertenenciaEtnicaEnum pertenenciaEtnica) {
        this.pertenenciaEtnica = pertenenciaEtnica;
    }

    public Long getIdPaisResidencia() {
        return idPaisResidencia;
    }

    public void setIdPaisResidencia(Long idPaisResidencia) {
        this.idPaisResidencia = idPaisResidencia;
    }

    /**
     * @return the estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * @param estadoCartera the estadoCartera to set
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * @return the gestionCartera
     */
    public GestionCarteraDTO getGestionCartera() {
        return gestionCartera;
    }

    /**
     * @param gestionCartera the gestionCartera to set
     */
    public void setGestionCartera(GestionCarteraDTO gestionCartera) {
        this.gestionCartera = gestionCartera;
    }

    public String getCentroTrabajo() {
        return this.centroTrabajo;
    }

    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public Long getIdGrupoFamiliar() {
        return this.idGrupoFamiliar;
    }

    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }
    public Long getIdResguardo() {
        return this.idResguardo;
    }

    public void setIdResguardo(Long idResguardo) {
        this.idResguardo = idResguardo;
    }

    public Long getIdPuebloIndigena() {
        return this.idPuebloIndigena;
    }

    public void setIdPuebloIndigena(Long idPuebloIndigena) {
        this.idPuebloIndigena = idPuebloIndigena;
    }

    public Boolean getEncVista360() {
        return this.encVista360;
    }

    public void setEncVista360(Boolean encVista360) {
        this.encVista360 = encVista360;
    }


    public String getTipoAfiliacion() {
        return this.tipoAfiliacion;
    }

    public void setTipoAfiliacion(String tipoAfiliacion) {
        this.tipoAfiliacion = tipoAfiliacion;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonaDTO [tipoIdentificacion=");
        builder.append(tipoIdentificacion);
        builder.append(", numeroIdentificacion=");
        builder.append(numeroIdentificacion);
        builder.append(", primerNombre=");
        builder.append(primerNombre);
        builder.append(", segundoNombre=");
        builder.append(segundoNombre);
        builder.append(", primerApellido=");
        builder.append(primerApellido);
        builder.append(", segundoApellido=");
        builder.append(segundoApellido);
        builder.append(", nombreCompleto=");
        builder.append(nombreCompleto);
        builder.append(", idPersona=");
        builder.append(idPersona);
        builder.append(", fechaNacimiento=");
        builder.append(fechaNacimiento);
        builder.append(", fallecido=");
        builder.append(fallecido);
        builder.append(", estadoAfiliadoCaja=");
        builder.append(estadoAfiliadoCaja);
        builder.append(", clasificacion=");
        builder.append(clasificacion);
        builder.append(", tipoTransaccion=");
        builder.append(tipoTransaccion);
        builder.append(", digitoVerificacion=");
        builder.append(digitoVerificacion);
        builder.append(", razonSocial=");
        builder.append(razonSocial);
        builder.append(", idAfiliado=");
        builder.append(idAfiliado);
        builder.append(", afiliable=");
        builder.append(afiliable);
        builder.append(", estadoCivil=");
        builder.append(estadoCivil);
        builder.append(", fechaExpedicionDocumento=");
        builder.append(fechaExpedicionDocumento);
        builder.append(", genero=");
        builder.append(genero);
        builder.append(", idOcupacionProfesion=");
        builder.append(idOcupacionProfesion);
        builder.append(", nivelEducativo=");
        builder.append(nivelEducativo);
        builder.append(", cabezaHogar=");
        builder.append(cabezaHogar);
        builder.append(", habitaCasaPropia=");
        builder.append(habitaCasaPropia);
        builder.append(", autorizacionEnvioEmail=");
        builder.append(autorizacionEnvioEmail);
        builder.append(", medioDePagoPersona=");
        builder.append(medioDePagoPersona);
        builder.append(", ubicacionDTO=");
        builder.append(ubicacionDTO);
        builder.append(", autorizaUsoDatosPersonales=");
        builder.append(autorizaUsoDatosPersonales);
        builder.append(", resideSectorRural=");
        builder.append(resideSectorRural);
        builder.append(", fechaIngreso=");
        builder.append(fechaIngreso);
        builder.append(", fechaRetiro=");
        builder.append(fechaRetiro);
        builder.append(", estadoAfiliadoRol=");
        builder.append(estadoAfiliadoRol);
        builder.append(", gradoAcademico=");
        builder.append(gradoAcademico);
        builder.append(", tipoSolicitante=");
        builder.append(tipoSolicitante);
        builder.append(", creadoPorPila=");
        builder.append(creadoPorPila);
        builder.append(", fechaFallecido=");
        builder.append(fechaFallecido);
        builder.append(", beneficiarioSubsidio=");
        builder.append(beneficiarioSubsidio);
        builder.append(", fechaDefuncion=");
        builder.append(fechaDefuncion);
        builder.append(", estudianteTrabajoDesarrolloHumano=");
        builder.append(estudianteTrabajoDesarrolloHumano);
        builder.append(", idPersonaPadre=");
        builder.append(idPersonaPadre);
        builder.append(", idPersonaMadre=");
        builder.append(idPersonaMadre);
        builder.append(", precargar=");
        builder.append(precargar);
        builder.append(", orientacionSexual=");
        builder.append(orientacionSexual);
        builder.append(", pertenenciaEtnica=");
        builder.append(pertenenciaEtnica);
        builder.append(", factorVulnerabilidad=");
        builder.append(factorVulnerabilidad);
        builder.append(", idPaisResidencia=");
        builder.append(idPaisResidencia);
        builder.append("]");
        return builder.toString();
    }

}
