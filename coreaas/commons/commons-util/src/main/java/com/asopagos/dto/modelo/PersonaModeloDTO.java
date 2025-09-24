package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.entidades.ccf.personas.Pais;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * DTO con los datos del Modelo de Persona.
 *
 * @author Fabian López <flopez@heinsohn.com.co>
 */
public class PersonaModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 22574235798347593L;

    // Propiedades de la Persona //

    /**
     * Código identificador de llave primaria de la persona
     */
    private Long idPersona;
    /**
     * Descripción del tipo de identificación de la persona
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación de la persona
     */
    private String numeroIdentificacion;
    /**
     * Id que identifica a la ubicación asociada a la persona
     */
    private UbicacionModeloDTO ubicacionModeloDTO;
    /**
     * Digito de verificación de la persona
     */
    private Short digitoVerificacion;
    /**
     * Descripción del primer nombre
     */
    private String primerNombre;
    /**
     * Descripción del segundo nombre
     */
    private String segundoNombre;
    /**
     * Descripción del primer apellido
     */
    private String primerApellido;
    /**
     * Descripción del segundo apellido
     */
    private String segundoApellido;
    /**
     * Descripción de la razón social
     */
    private String razonSocial;
    /**
     * indica si fue creado por pila
     */
    private Boolean creadoPorPila;

    // Propiedades de la PersonaDetalle //
    /**
     * Código identificador de la Persona Detalle.
     */
    private Long idPersonaDetalle;
    /**
     * Fecha de nacimiento de la persona
     */
    private Long fechaNacimiento;
    /**
     * Fecha de expedición del documento de la persona
     */
    private Long fechaExpedicionDocumento;
    /**
     * Descripción del género
     */
    private GeneroEnum genero;
    /**
     * Id que identifica la ocupación o profesión asociada a la persona
     */
    private Integer idOcupacionProfesion;
    /**
     * Descripción del nivel de educación
     */
    private NivelEducativoEnum nivelEducativo;
    /**
     * Grado académico de la persona
     */
    private Short gradoAcademico;
    /**
     * Indicador S/N si la persona es cabeza de hogar
     */
    private Boolean cabezaHogar;
    /**
     * Indicador S/N si la persona posee casa propia
     */
    private Boolean habitaCasaPropia;
    /**
     * Indicador S/N si la persona autoriza el uso de los datos personales
     */
    private Boolean autorizaUsoDatosPersonales;
    /**
     * Indicador S/N si la persona reside en un sector rural
     */
    private Boolean resideSectorRural;
    /**
     * Descripción del estado civil de la persona
     */
    private EstadoCivilEnum estadoCivil;
    /**
     * Indicador S/N si la persona ha fallecido
     */
    private Boolean fallecido;
    /**
     * Fecha Fallecido
     */
    private Long fechaFallecido;
    /**
     * Identifica si la persona ha sido beneficiario de subsidio de mejoramiento
     * de vivienda.
     */
    private Boolean beneficiarioSubsidio;

    /**
     * Fecha de defuncion de la persona
     */
    private Long fechaDefuncion;

    /**
     * Estudiante trabajao desarrollo humano
     */
    private Boolean estudianteTrabajoDesarrolloHumano;

    /** 
     * Información de padre biologico
     */
    private Long idPersonaPadre;

    /**
     * Información de madre biologico
     */
    private Long idPersonaMadre;

    // otras propiedades, útiles para manejo de datos //

    /**
     * Para cambio de Tipo de Identificación Persona.
     */
    private TipoIdentificacionEnum tipoIdentificacionNuevo;
    /**
     * Para cambio de Número de Identificación Persona.
     */
    private String numeroIdentificacionNuevo;
    /**
     * Id que identifica el medio de pago asoaciado a la persona
     */
    private Long idMedioPago;
    /**
     * Indica si la persona asociada puede postularse al subsidio FOVIS.
     */
    private Boolean postulableFOVIS;
    /**
     * Orientacion sexual de la persona
     */
    private OrientacionSexualEnum orientacionSexual;

    /**
     * Factor de vulnerabilidad de la persona
     */
    private FactorVulnerabilidadEnum factorVulnerabilidad;

    /**
     * Pertenencia Etnica de la persona
     */
    private PertenenciaEtnicaEnum pertenenciaEtnica;

    /**
     * Pais de residencia de la persona
     */
    private Long idPaisResidencia;

    private Long idResguardo;

    private Long idPuebloIndigena;

    /**
     * tipoMedioDePago de la persona
     */
    private TipoMedioDePagoEnum tipoMedioDePago;



    /**
     * Constructor de la clase.
     */
    public PersonaModeloDTO() {
        super();
    }


    public PersonaModeloDTO(Persona persona) {
        convertToDTO(persona, null);
    }

    /**
     * Constructor que asigna los datos al DTO de Persona y PersonaDetalle
     *
     * @param persona
     * @param personaDetalle
     */
    public PersonaModeloDTO(Persona persona, PersonaDetalle personaDetalle) {
        convertToDTO(persona, personaDetalle);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     *
     * @return Persona
     */
    public Persona convertToPersonaEntity() {
        Persona persona = copyDTOToEntity(new Persona());
        persona.setIdPersona(this.getIdPersona());
        if (this.getUbicacionModeloDTO() != null && this.getUbicacionModeloDTO().getIdUbicacion() != null) {
            persona.setUbicacionPrincipal(this.getUbicacionModeloDTO().convertToEntity());
        }
        return persona;
    }

    /**
     * @return PersonaDetalle
     */
    public PersonaDetalle convertToPersonaDetalleEntity() {
        PersonaDetalle personaDetalle = copyDTOToEntity(new PersonaDetalle());
        personaDetalle.setIdPersonaDetalle(this.getIdPersonaDetalle());
        personaDetalle.setIdPersona(this.getIdPersona());
        return personaDetalle;
    }

    /**
     * @param Asocia los datos de la Entidad al DTO
     */
    public void convertToDTO(Persona persona, PersonaDetalle personaDetalle) {
        this.setIdPersona(persona.getIdPersona());
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setPrimerNombre(persona.getPrimerNombre());
        this.setSegundoNombre(persona.getSegundoNombre());
        this.setPrimerApellido(persona.getPrimerApellido());
        this.setSegundoApellido(persona.getSegundoApellido());
        this.setRazonSocial(persona.getRazonSocial());
        this.setDigitoVerificacion(persona.getDigitoVerificacion());
        this.setCreadoPorPila(persona.getCreadoPorPila());
        if (personaDetalle != null) {
            this.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
            this.setFechaNacimiento(personaDetalle.getFechaNacimiento() != null ? personaDetalle.getFechaNacimiento().getTime() : null);
            this.setGenero(personaDetalle.getGenero());
            this.setFallecido(personaDetalle.getFallecido());
            this.setIdOcupacionProfesion(personaDetalle.getIdOcupacionProfesion());
            this.setNivelEducativo(personaDetalle.getNivelEducativo());
            this.setCabezaHogar(personaDetalle.getCabezaHogar());
            this.setHabitaCasaPropia(personaDetalle.getHabitaCasaPropia());
            this.setFechaExpedicionDocumento(
                    personaDetalle.getFechaExpedicionDocumento() != null ? personaDetalle.getFechaExpedicionDocumento().getTime() : null);
            this.setEstadoCivil(personaDetalle.getEstadoCivil());
            this.setGradoAcademico(personaDetalle.getGradoAcademico());
            this.setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
            this.setFechaFallecido(personaDetalle.getFechaFallecido() != null ? personaDetalle.getFechaFallecido().getTime() : null);
            this.setResideSectorRural(personaDetalle.getResideSectorRural());
            this.setBeneficiarioSubsidio(personaDetalle.getBeneficiarioSubsidio());
            this.setFechaDefuncion(personaDetalle.getFechaDefuncion() != null ? personaDetalle.getFechaDefuncion().getTime() : null);
            this.setEstudianteTrabajoDesarrolloHumano(personaDetalle.getEstudianteTrabajoDesarrolloHumano());
            this.setIdPersonaPadre(personaDetalle.getIdPersonaPadre() != null ? personaDetalle.getIdPersonaPadre() : null);
            this.setIdPersonaMadre(personaDetalle.getIdPersonaMadre() != null ? personaDetalle.getIdPersonaMadre() : null);
            this.setOrientacionSexual(personaDetalle.getOrientacionSexual());
            this.setFactorVulnerabilidad(personaDetalle.getFactorVulnerabilidad());
            this.setPertenenciaEtnica(personaDetalle.getPertenenciaEtnica());
            this.setIdPaisResidencia(personaDetalle.getIdPaisResidencia());
            this.setIdResguardo(personaDetalle.getIdResguardo());
            this.setIdPuebloIndigena(personaDetalle.getIdPuebloIndigena());
        }
        if (persona.getUbicacionPrincipal() != null && persona.getUbicacionPrincipal().getIdUbicacion() != null) {
            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
            ubicacionModeloDTO.convertToDTO(persona.getUbicacionPrincipal());
            this.setUbicacionModeloDTO(ubicacionModeloDTO);
        }
    }

    /**
     * Método que conviernte un <code>PersonaDTO</code> en el DTO
     *
     * @param personaDTO El <code>PersonaDTO</code> a convertir
     */
    public void convertFromPersonaDTO(PersonaDTO personaDTO) {
        this.setAutorizaUsoDatosPersonales(personaDTO.getAutorizaUsoDatosPersonales());
        this.setCabezaHogar(personaDTO.getCabezaHogar());
        this.setDigitoVerificacion(personaDTO.getDigitoVerificacion());
        this.setEstadoCivil(personaDTO.getEstadoCivil());
        this.setFallecido(personaDTO.getFallecido());
        this.setFechaExpedicionDocumento(
                personaDTO.getFechaExpedicionDocumento() != null ? personaDTO.getFechaExpedicionDocumento().getTime() : null);
        this.setFechaNacimiento(personaDTO.getFechaNacimiento());
        this.setGenero(personaDTO.getGenero());
        this.setGradoAcademico(personaDTO.getGradoAcademico());
        this.setHabitaCasaPropia(personaDTO.getHabitaCasaPropia());
        this.setIdMedioPago(personaDTO.getMedioDePagoPersona() != null ? personaDTO.getMedioDePagoPersona().getIdMedioDePago() : null);
        this.setIdOcupacionProfesion(personaDTO.getIdOcupacionProfesion());
        this.setIdPersona(personaDTO.getIdPersona());
        this.setNivelEducativo(personaDTO.getNivelEducativo());
        this.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
        this.setPrimerApellido(personaDTO.getPrimerApellido());
        this.setPrimerNombre(personaDTO.getPrimerNombre());
        this.setRazonSocial(personaDTO.getRazonSocial());
        this.setResideSectorRural(personaDTO.getResideSectorRural());
        this.setSegundoApellido(personaDTO.getSegundoApellido());
        this.setSegundoNombre(personaDTO.getSegundoNombre());
        this.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
        this.setCreadoPorPila(personaDTO.getCreadoPorPila());
        this.setFechaFallecido(personaDTO.getFechaFallecido() != null ? personaDTO.getFechaFallecido().getTime() : null);
        this.setBeneficiarioSubsidio(personaDTO.getBeneficiarioSubsidio());
        this.setFechaDefuncion(personaDTO.getFechaDefuncion() != null ? personaDTO.getFechaDefuncion().getTime() : null);
        this.setEstudianteTrabajoDesarrolloHumano(personaDTO.getEstudianteTrabajoDesarrolloHumano());
        if (personaDTO.getUbicacionDTO() != null) {
            UbicacionDTO ubicacionDTO = personaDTO.getUbicacionDTO();
            UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
            ubicacionModeloDTO.setAutorizacionEnvioEmail(ubicacionDTO.getAutorizacionEnvioEmail());
            ubicacionModeloDTO.setCodigoPostal(ubicacionDTO.getCodigoPostal());
            ubicacionModeloDTO.setDireccionFisica(ubicacionDTO.getDireccion());
            ubicacionModeloDTO.setDescripcionIndicacion(ubicacionDTO.getDescripcionIndicacion());
            ubicacionModeloDTO.setEmail(ubicacionDTO.getCorreoElectronico());
            ubicacionModeloDTO.setEmailSecundario(ubicacionDTO.getCorreoElectronicoSecundario());
            ubicacionModeloDTO.setIdMunicipio(ubicacionDTO.getIdMunicipio());
            ubicacionModeloDTO.setIdUbicacion(ubicacionDTO.getIdUbicacion());
            ubicacionModeloDTO.setIndicativoTelFijo(ubicacionDTO.getIndicativoTelefonoFijo());
            ubicacionModeloDTO.setTelefonoCelular(ubicacionDTO.getTelefonoCelular());
            ubicacionModeloDTO.setTelefonoFijo(ubicacionDTO.getTelefonoFijo());
            this.setUbicacionModeloDTO(ubicacionModeloDTO);
        }
        this.setOrientacionSexual(personaDTO.getOrientacionSexual());
        this.setPertenenciaEtnica(personaDTO.getPertenenciaEtnica());
        this.setFactorVulnerabilidad(personaDTO.getFactorVulnerabilidad());
        this.setIdPaisResidencia(personaDTO.getIdPaisResidencia());
        this.setIdResguardo(personaDTO.getIdResguardo());
        this.setIdPuebloIndigena(personaDTO.getIdPuebloIndigena());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     *
     * @param persona previamente consultada.
     */
    public Persona copyDTOToEntity(Persona persona) {
        if (this.getTipoIdentificacion() != null) {
            persona.setTipoIdentificacion(this.getTipoIdentificacion());
        }
        if (this.getNumeroIdentificacion() != null && !this.getNumeroIdentificacion().isEmpty()) {
            persona.setNumeroIdentificacion(this.getNumeroIdentificacion());
        }
        /* Si llega un Tipo de Identificación nuevo - Novedades */
        if (this.getNumeroIdentificacionNuevo() != null && !this.getNumeroIdentificacionNuevo().isEmpty()) {
            persona.setNumeroIdentificacion(this.getNumeroIdentificacionNuevo());
        }
        /* Si llega un Numero de Identificación nuevo - Novedades */
        if (this.getTipoIdentificacionNuevo() != null) {
            persona.setTipoIdentificacion(this.getTipoIdentificacionNuevo());
        }
        if (this.getPrimerNombre() != null && !this.getPrimerNombre().isEmpty()) {
            persona.setPrimerNombre(this.getPrimerNombre());
        }
        if (this.getSegundoNombre() != null) {
            persona.setSegundoNombre(this.getSegundoNombre());
        }
        if (this.getPrimerApellido() != null && !this.getPrimerApellido().isEmpty()) {
            persona.setPrimerApellido(this.getPrimerApellido());
        }
        if (this.getSegundoApellido() != null) {
            persona.setSegundoApellido(this.getSegundoApellido());
        }
        if (this.getDigitoVerificacion() != null) {
            persona.setDigitoVerificacion(this.getDigitoVerificacion());
        }
        if (this.getRazonSocial() != null && !this.getRazonSocial().isEmpty()) {
            persona.setRazonSocial(this.getRazonSocial());
        }
        if (this.getCreadoPorPila() != null) {
            persona.setCreadoPorPila(this.getCreadoPorPila());
        }
        return persona;
    }

    /**
     * Convierte del DTO a la Persona Detalle.
     *
     * @param personaDetalle
     * @return
     */
    public PersonaDetalle copyDTOToEntity(PersonaDetalle personaDetalle) {
        if (this.getAutorizaUsoDatosPersonales() != null) {
            personaDetalle.setAutorizaUsoDatosPersonales(this.getAutorizaUsoDatosPersonales());
        }
        if (this.getEstadoCivil() != null) {
            personaDetalle.setEstadoCivil(this.getEstadoCivil());
        }
        if (this.getFechaExpedicionDocumento() != null) {
            personaDetalle.setFechaExpedicionDocumento(
                    this.getFechaExpedicionDocumento() != null ? new Date(this.getFechaExpedicionDocumento()) : null);
        }
        if (this.getFechaNacimiento() != null) {
            personaDetalle.setFechaNacimiento(this.getFechaNacimiento() != null ? new Date(this.getFechaNacimiento()) : null);
        }
        if (this.getGenero() != null) {
            personaDetalle.setGenero(this.getGenero());
        }
        if (this.getIdOcupacionProfesion() != null) {
            personaDetalle.setIdOcupacionProfesion(this.getIdOcupacionProfesion());
        }
        if (this.getNivelEducativo() != null) {
            personaDetalle.setNivelEducativo(this.getNivelEducativo());
        }
        if (this.getCabezaHogar() != null) {
            personaDetalle.setCabezaHogar(this.getCabezaHogar());
        }
        if (this.getHabitaCasaPropia() != null) {
            personaDetalle.setHabitaCasaPropia(this.getHabitaCasaPropia());
        }
        if (this.getResideSectorRural() != null) {
            personaDetalle.setResideSectorRural(this.getResideSectorRural());
        }
        if (this.getFallecido() != null) {
            personaDetalle.setFallecido(this.getFallecido());
        }
        if (this.getGradoAcademico() != null) {
            personaDetalle.setGradoAcademico(this.getGradoAcademico());
        }
        if (this.getAutorizaUsoDatosPersonales() != null) {
            personaDetalle.setAutorizaUsoDatosPersonales(this.getAutorizaUsoDatosPersonales());
        }
        if (this.getFechaFallecido() != null) {
            personaDetalle.setFechaFallecido(this.getFechaFallecido() != null ? new Date(this.getFechaFallecido()) : null);
        }
        if (this.getBeneficiarioSubsidio() != null) {
            personaDetalle.setBeneficiarioSubsidio(this.getBeneficiarioSubsidio());
        }
        if (this.getFechaDefuncion() != null) {
            personaDetalle.setFechaDefuncion(this.getFechaDefuncion() != null ? new Date(this.getFechaDefuncion()) : null);
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
        if (this.getOrientacionSexual() != null) {
            personaDetalle.setOrientacionSexual(this.getOrientacionSexual());
        }
        if (this.getPertenenciaEtnica() != null) {
            personaDetalle.setPertenenciaEtnica(this.getPertenenciaEtnica());
        }
        if (this.getFactorVulnerabilidad() != null) {
            personaDetalle.setFactorVulnerabilidad(this.getFactorVulnerabilidad());
        }
        if (this.getIdPaisResidencia() != null) {
            personaDetalle.setIdPaisResidencia(this.getIdPaisResidencia());
        }
        if (this.getIdResguardo() != null) {
            personaDetalle.setIdResguardo(this.getIdResguardo());
        }
        if (this.getIdPuebloIndigena() != null) {
            personaDetalle.setIdPuebloIndigena(this.getIdPuebloIndigena());
        }
        return personaDetalle;
    }

    public PersonaModeloDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de idPersona.
     *
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     *
     * @param valor para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the idPersonaDetalle
     */
    public Long getIdPersonaDetalle() {
        return idPersonaDetalle;
    }

    /**
     * @param idPersonaDetalle the idPersonaDetalle to set
     */
    public void setIdPersonaDetalle(Long idPersonaDetalle) {
        this.idPersonaDetalle = idPersonaDetalle;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     *
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     *
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     *
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     *
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionNuevo.
     *
     * @return valor de tipoIdentificacionNuevo.
     */
    public TipoIdentificacionEnum getTipoIdentificacionNuevo() {
        return tipoIdentificacionNuevo;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionNuevo.
     *
     * @param valor para modificar tipoIdentificacionNuevo.
     */
    public void setTipoIdentificacionNuevo(TipoIdentificacionEnum tipoIdentificacionNuevo) {
        this.tipoIdentificacionNuevo = tipoIdentificacionNuevo;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionNuevo.
     *
     * @return valor de numeroIdentificacionNuevo.
     */
    public String getNumeroIdentificacionNuevo() {
        return numeroIdentificacionNuevo;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionNuevo.
     *
     * @param valor para modificar numeroIdentificacionNuevo.
     */
    public void setNumeroIdentificacionNuevo(String numeroIdentificacionNuevo) {
        this.numeroIdentificacionNuevo = numeroIdentificacionNuevo;
    }

    /**
     * Método que retorna el valor de digitoVerificacion.
     *
     * @return valor de digitoVerificacion.
     */
    public Short getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * Método encargado de modificar el valor de digitoVerificacion.
     *
     * @param valor para modificar digitoVerificacion.
     */
    public void setDigitoVerificacion(Short digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * Método que retorna el valor de primerNombre.
     *
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     *
     * @param valor para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     *
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     *
     * @param valor para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     *
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     *
     * @param valor para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     *
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     *
     * @param valor para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de razonSocial.
     * @return valor de razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Método encargado de modificar el valor de razonSocial.
     * @param valor
     *        para modificar razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Método que retorna el valor de ubicacionModeloDTO.
     * @return valor de ubicacionModeloDTO.
     */
    public UbicacionModeloDTO getUbicacionModeloDTO() {
        return ubicacionModeloDTO;
    }

    /**
     * Método encargado de modificar el valor de ubicacionModeloDTO.
     * @param valor
     *        para modificar ubicacionModeloDTO.
     */
    public void setUbicacionModeloDTO(UbicacionModeloDTO ubicacionModeloDTO) {
        this.ubicacionModeloDTO = ubicacionModeloDTO;
    }

    /**
     * Método que retorna el valor de fechaNacimiento.
     * @return valor de fechaNacimiento.
     */
    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Método encargado de modificar el valor de fechaNacimiento.
     * @param valor
     *        para modificar fechaNacimiento.
     */
    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método que retorna el valor de fechaExpedicionDocumento.
     * @return valor de fechaExpedicionDocumento.
     */
    public Long getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    /**
     * Método encargado de modificar el valor de fechaExpedicionDocumento.
     * @param valor
     *        para modificar fechaExpedicionDocumento.
     */
    public void setFechaExpedicionDocumento(Long fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    /**
     * @return the gradoAcademico
     */
    public Short getGradoAcademico() {
        return gradoAcademico;
    }

    /**
     * @param gradoAcademico
     *        the gradoAcademico to set
     */
    public void setGradoAcademico(Short gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    /**
     * Método que retorna el valor de genero.
     * @return valor de genero.
     */
    public GeneroEnum getGenero() {
        return genero;
    }

    /**
     * Método encargado de modificar el valor de genero.
     * @param valor
     *        para modificar genero.
     */
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    /**
     * Método que retorna el valor de idOcupacionProfesion.
     * @return valor de idOcupacionProfesion.
     */
    public Integer getIdOcupacionProfesion() {
        return idOcupacionProfesion;
    }

    /**
     * Método encargado de modificar el valor de idOcupacionProfesion.
     * @param valor
     *        para modificar idOcupacionProfesion.
     */
    public void setIdOcupacionProfesion(Integer idOcupacionProfesion) {
        this.idOcupacionProfesion = idOcupacionProfesion;
    }

    /**
     * Método que retorna el valor de nivelEducativo.
     * @return valor de nivelEducativo.
     */
    public NivelEducativoEnum getNivelEducativo() {
        return nivelEducativo;
    }

    /**
     * Método encargado de modificar el valor de nivelEducativo.
     * @param valor
     *        para modificar nivelEducativo.
     */
    public void setNivelEducativo(NivelEducativoEnum nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    /**
     * Método que retorna el valor de cabezaHogar.
     * @return valor de cabezaHogar.
     */
    public Boolean getCabezaHogar() {
        return cabezaHogar;
    }

    /**
     * Método encargado de modificar el valor de cabezaHogar.
     * @param valor
     *        para modificar cabezaHogar.
     */
    public void setCabezaHogar(Boolean cabezaHogar) {
        this.cabezaHogar = cabezaHogar;
    }

    /**
     * Método que retorna el valor de habitaCasaPropia.
     * @return valor de habitaCasaPropia.
     */
    public Boolean getHabitaCasaPropia() {
        return habitaCasaPropia;
    }

    /**
     * Método encargado de modificar el valor de habitaCasaPropia.
     * @param valor
     *        para modificar habitaCasaPropia.
     */
    public void setHabitaCasaPropia(Boolean habitaCasaPropia) {
        this.habitaCasaPropia = habitaCasaPropia;
    }

    /**
     * Método que retorna el valor de autorizaUsoDatosPersonales.
     * @return valor de autorizaUsoDatosPersonales.
     */
    public Boolean getAutorizaUsoDatosPersonales() {
        return autorizaUsoDatosPersonales;
    }

    /**
     * Método encargado de modificar el valor de autorizaUsoDatosPersonales.
     * @param valor
     *        para modificar autorizaUsoDatosPersonales.
     */
    public void setAutorizaUsoDatosPersonales(Boolean autorizaUsoDatosPersonales) {
        this.autorizaUsoDatosPersonales = autorizaUsoDatosPersonales;
    }

    /**
     * Método que retorna el valor de resideSectorRural.
     * @return valor de resideSectorRural.
     */
    public Boolean getResideSectorRural() {
        return resideSectorRural;
    }

    /**
     * Método encargado de modificar el valor de resideSectorRural.
     * @param valor
     *        para modificar resideSectorRural.
     */
    public void setResideSectorRural(Boolean resideSectorRural) {
        this.resideSectorRural = resideSectorRural;
    }

    /**
     * Método que retorna el valor de idMedioPago.
     * @return valor de idMedioPago.
     */
    public Long getIdMedioPago() {
        return idMedioPago;
    }

    /**
     * Método encargado de modificar el valor de idMedioPago.
     * @param valor
     *        para modificar idMedioPago.
     */
    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    /**
     * Método que retorna el valor de estadoCivil.
     * @return valor de estadoCivil.
     */
    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Método encargado de modificar el valor de estadoCivil.
     * @param valor
     *        para modificar estadoCivil.
     */
    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Método que retorna el valor de fallecido.
     * @return valor de fallecido.
     */
    public Boolean getFallecido() {
        return fallecido;
    }

    /**
     * Método encargado de modificar el valor de fallecido.
     * @param valor
     *        para modificar fallecido.
     */
    public void setFallecido(Boolean fallecido) {
        this.fallecido = fallecido;
    }

    /**
     * @return the fechaFallecido
     */
    public Long getFechaFallecido() {
        return fechaFallecido;
    }

    /**
     * @param fechaFallecido
     *        the fechaFallecido to set
     */
    public void setFechaFallecido(Long fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    /**
     * Método que retorna el valor de creadoPorPila.
     * @return valor de creadoPorPila.
     */
    public Boolean getCreadoPorPila() {
        return creadoPorPila;
    }

    /**
     * Método encargado de modificar el valor de creadoPorPila.
     * @param valor
     *        para modificar creadoPorPila.
     */
    public void setCreadoPorPila(Boolean creadoPorPila) {
        this.creadoPorPila = creadoPorPila;
    }

    /**
     * @return the postulableFOVIS
     */
    public Boolean getPostulableFOVIS() {
        return postulableFOVIS;
    }

    /**
     * @param postulableFOVIS
     *        the postulableFOVIS to set
     */
    public void setPostulableFOVIS(Boolean postulableFOVIS) {
        this.postulableFOVIS = postulableFOVIS;
    }

    /**
     * @return the beneficiarioSubsidio
     */
    public Boolean getBeneficiarioSubsidio() {
        return beneficiarioSubsidio;
    }

    /**
     * @param beneficiarioSubsidio
     *        the beneficiarioSubsidio to set
     */
    public void setBeneficiarioSubsidio(Boolean beneficiarioSubsidio) {
        this.beneficiarioSubsidio = beneficiarioSubsidio;
    }

    /**
     * @return the fechaDefuncion
     */
    public Long getFechaDefuncion() {
        return fechaDefuncion;
    }

    /**
     * @param fechaDefuncion the fechaDefuncion to set
     */
    public void setFechaDefuncion(Long fechaDefuncion) {
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
     * @return
     */
    public OrientacionSexualEnum getOrientacionSexual() {
        return orientacionSexual;
    }

    /**
     * Establece el valor de orientacionSexual
     *
     * @param orientacionSexual
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
     * Establece el valor de factorVulnerabilidad
     *
     * @param factorVulnerabilidad
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
     * Establece el valor de pertenenciaEtnica
     *
     * @param pertenenciaEtnica
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

    public TipoMedioDePagoEnum getTipoMedioDePago() {
        return this.tipoMedioDePago;
    }

    public void setTipoMedioDePago(TipoMedioDePagoEnum tipoMedioDePago) {
        this.tipoMedioDePago = tipoMedioDePago;
    }


    public Boolean isCreadoPorPila() {
        return this.creadoPorPila;
    }

    public Boolean isCabezaHogar() {
        return this.cabezaHogar;
    }

    public Boolean isHabitaCasaPropia() {
        return this.habitaCasaPropia;
    }

    public Boolean isAutorizaUsoDatosPersonales() {
        return this.autorizaUsoDatosPersonales;
    }

    public Boolean isResideSectorRural() {
        return this.resideSectorRural;
    }

    public Boolean isFallecido() {
        return this.fallecido;
    }

    public Boolean isBeneficiarioSubsidio() {
        return this.beneficiarioSubsidio;
    }

    public Boolean isEstudianteTrabajoDesarrolloHumano() {
        return this.estudianteTrabajoDesarrolloHumano;
    }

    public Boolean isPostulableFOVIS() {
        return this.postulableFOVIS;
    }

    @Override
    public String toString() {
        return "{" +
            " idPersona='" + getIdPersona() + "'" +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", ubicacionModeloDTO='" + getUbicacionModeloDTO() + "'" +
            ", digitoVerificacion='" + getDigitoVerificacion() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", creadoPorPila='" + isCreadoPorPila() + "'" +
            ", idPersonaDetalle='" + getIdPersonaDetalle() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", fechaExpedicionDocumento='" + getFechaExpedicionDocumento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", idOcupacionProfesion='" + getIdOcupacionProfesion() + "'" +
            ", nivelEducativo='" + getNivelEducativo() + "'" +
            ", gradoAcademico='" + getGradoAcademico() + "'" +
            ", cabezaHogar='" + isCabezaHogar() + "'" +
            ", habitaCasaPropia='" + isHabitaCasaPropia() + "'" +
            ", autorizaUsoDatosPersonales='" + isAutorizaUsoDatosPersonales() + "'" +
            ", resideSectorRural='" + isResideSectorRural() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", fallecido='" + isFallecido() + "'" +
            ", fechaFallecido='" + getFechaFallecido() + "'" +
            ", beneficiarioSubsidio='" + isBeneficiarioSubsidio() + "'" +
            ", fechaDefuncion='" + getFechaDefuncion() + "'" +
            ", estudianteTrabajoDesarrolloHumano='" + isEstudianteTrabajoDesarrolloHumano() + "'" +
            ", idPersonaPadre='" + getIdPersonaPadre() + "'" +
            ", idPersonaMadre='" + getIdPersonaMadre() + "'" +
            ", tipoIdentificacionNuevo='" + getTipoIdentificacionNuevo() + "'" +
            ", numeroIdentificacionNuevo='" + getNumeroIdentificacionNuevo() + "'" +
            ", idMedioPago='" + getIdMedioPago() + "'" +
            ", postulableFOVIS='" + isPostulableFOVIS() + "'" +
            ", orientacionSexual='" + getOrientacionSexual() + "'" +
            ", factorVulnerabilidad='" + getFactorVulnerabilidad() + "'" +
            ", pertenenciaEtnica='" + getPertenenciaEtnica() + "'" +
            ", idPaisResidencia='" + getIdPaisResidencia() + "'" +
            ", idResguardo='" + getIdResguardo() + "'" +
            ", idPuebloIndigena='" + getIdPuebloIndigena() + "'" +
            ", tipoMedioDePago='" + getTipoMedioDePago() + "'" +
            "}";
    }

}
