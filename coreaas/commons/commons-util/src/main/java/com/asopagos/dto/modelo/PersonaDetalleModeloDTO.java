package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.ccf.personas.Pais;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.SectorUbicacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que almacena la información de la entidad
 * PersonaDetalle <br/>
 * <b>Módulo:</b> Asopagos - HU-3.2.1<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
@XmlRootElement
public class PersonaDetalleModeloDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 18889865640005421L;
	
	/**
	 * Código identificador de llave primaria de la persona
	 */
	private Long idPersonaDetalle;
	/**
	 * Referencia a la persona
	 */
	private Long idPersona;
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
	 * Referencia a la ocupación o profesión asociada a la persona
	 */
	private Integer idOcupacionProfesion;
	/**
	 * Descripción del grado academico
	 */
	private Short gradoAcademico;
	/**
	 * Descripción del nivel de educación
	 */
	private NivelEducativoEnum nivelEducativo;
	/**
	 * Indicador S/N si la persona es cabeza de hogar [S=Si N=No]
	 */
	private Boolean cabezaHogar;
	/**
	 * Indicador S/N si la persona autoriza el uso de los datos personales [S=Si
	 * N=No]
	 */
	private Boolean autorizaUsoDatosPersonales;
	/**
	 * Indicador S/N si la persona reside en un sector rural [S=Si N=No]
	 */
	private Boolean resideSectorRural;
	/**
	 * Descripción del estado civil de la persona
	 */
	private EstadoCivilEnum estadoCivil;
	/**
	 * Indicador S/N si la persona posee casa propia [S=Si N=No]
	 */
	private Boolean habitaCasaPropia;
	/**
	 * Indicador S/N si la persona ha fallecido [S=Si N=No]
	 */
	private Boolean fallecido;
	/**
	 * Fecha de fallecido de la persona
	 */
	private Long fechaFallecido;
	/**
	 * Identifica si la persona ha sido beneficiario
	 */
	private Boolean beneficiarioSubsidio;

	/**
	 * Fecha de defuncion de la persona
	 */
	private Long fechaDefuncion;
	
	/**
	 * Indicador S/N de si el beneficiario tipo hijo es estudiante de
     * programa en institución para el trabajo y desarrollo humano [S=Si N=No]
	 */
	private Boolean estudianteTrabajoDesarrolloHumano;	
	
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
	
	/**
	 * CC Vistas 360
	 */
	private String edad;
	
	private String nombrePaisResidencia;

	private String nombreResguardo;

	private String nombrePuebloIndigena;

	private Long idResguardo;

	private Long idPuebloIndigena;

    /**
	 * Constructor vacío
	 */
	public PersonaDetalleModeloDTO() {
	}

	/**
	 * Constructor que recibe el entity que maneja el DTO.
	 * 
	 * @param personaDetalle
	 */
	public PersonaDetalleModeloDTO(PersonaDetalle personaDetalle) {
		copyEntityToDTO(personaDetalle);
	}

	/**
	 * Convierte el actual DTO en el entity equivalente.
	 * 
	 * @return Entity PersonaDetalle
	 */
	public PersonaDetalle convertToEntity() {
		PersonaDetalle personaDetalle = new PersonaDetalle();
		personaDetalle.setIdPersonaDetalle(this.getIdPersonaDetalle());
		personaDetalle.setIdPersona(this.getIdPersona());
		personaDetalle.setFechaNacimiento(this.getFechaNacimiento() != null ? new Date(this.getFechaNacimiento()) : null);
		personaDetalle.setFechaExpedicionDocumento(this.getFechaExpedicionDocumento() != null ? new Date(this.getFechaExpedicionDocumento()) : null);
		personaDetalle.setGenero(this.getGenero());
		personaDetalle.setIdOcupacionProfesion(this.getIdOcupacionProfesion());
		personaDetalle.setGradoAcademico(this.getGradoAcademico());
		personaDetalle.setNivelEducativo(this.getNivelEducativo());
		personaDetalle.setCabezaHogar(this.getCabezaHogar());
		personaDetalle.setAutorizaUsoDatosPersonales(this.getAutorizaUsoDatosPersonales());
		personaDetalle.setResideSectorRural(this.getResideSectorRural());
		personaDetalle.setEstadoCivil(this.getEstadoCivil());
		personaDetalle.setHabitaCasaPropia(this.getHabitaCasaPropia());
		personaDetalle.setFallecido(this.getFallecido());
		personaDetalle.setFechaFallecido(this.getFechaFallecido() != null ? new Date(this.getFechaFallecido()) : null);
		personaDetalle.setBeneficiarioSubsidio(this.getBeneficiarioSubsidio());
		personaDetalle.setFechaDefuncion(this.getFechaDefuncion() != null ? new Date(this.getFechaDefuncion()) : null);
		personaDetalle.setEstudianteTrabajoDesarrolloHumano(this.getEstudianteTrabajoDesarrolloHumano());
		personaDetalle.setOrientacionSexual(this.getOrientacionSexual());
		personaDetalle.setFactorVulnerabilidad(this.getFactorVulnerabilidad());
		personaDetalle.setPertenenciaEtnica(this.getPertenenciaEtnica());
		personaDetalle.setIdPaisResidencia(this.getIdPaisResidencia());
		personaDetalle.setIdResguardo(this.getIdResguardo());
		personaDetalle.setIdPuebloIndigena(this.getIdPuebloIndigena());
		return personaDetalle;
	}

	/**
	 * Método que convierte una entidad <code>PersonaDetalle</code> en el DTO
	 * 
	 * @param personaDetalle
	 *            La entidad a convertir
	 */
	public void convertToDTO(PersonaDetalle personaDetalle) {
		this.idPersonaDetalle = personaDetalle.getIdPersonaDetalle();
		this.idPersona = personaDetalle.getIdPersona();
		this.fechaNacimiento = personaDetalle.getFechaNacimiento() != null ? personaDetalle.getFechaNacimiento().getTime() : null;
		this.fechaExpedicionDocumento = personaDetalle.getFechaExpedicionDocumento() != null ? personaDetalle.getFechaExpedicionDocumento().getTime() : null;
		this.genero = personaDetalle.getGenero();
		this.idOcupacionProfesion = personaDetalle.getIdOcupacionProfesion();
		this.gradoAcademico = personaDetalle.getGradoAcademico();
		this.nivelEducativo = personaDetalle.getNivelEducativo();
		this.cabezaHogar = personaDetalle.getCabezaHogar();
		this.autorizaUsoDatosPersonales = personaDetalle.getAutorizaUsoDatosPersonales();
		this.resideSectorRural = personaDetalle.getResideSectorRural();
		this.estadoCivil = personaDetalle.getEstadoCivil();
		this.habitaCasaPropia = personaDetalle.getHabitaCasaPropia();
		this.fallecido = personaDetalle.getFallecido();
		this.fechaFallecido = personaDetalle.getFechaFallecido() != null ? personaDetalle.getFechaFallecido().getTime() : null;
		this.beneficiarioSubsidio = personaDetalle.getBeneficiarioSubsidio();
		this.fechaDefuncion = personaDetalle.getFechaDefuncion() != null ? personaDetalle.getFechaDefuncion().getTime() : null;
		this.estudianteTrabajoDesarrolloHumano = personaDetalle.getEstudianteTrabajoDesarrolloHumano();
		this.orientacionSexual = personaDetalle.getOrientacionSexual();
		this.factorVulnerabilidad = personaDetalle.getFactorVulnerabilidad();
		this.pertenenciaEtnica = personaDetalle.getPertenenciaEtnica();
		this.idPaisResidencia = personaDetalle.getIdPaisResidencia();
		this.idResguardo = personaDetalle.getIdResguardo();
		this.idPuebloIndigena = personaDetalle.getIdPuebloIndigena();
	}

	/**
	 * Copia las propiedades del DTO actual al entity que llega por parámetro
	 * 
	 * @param personaDetalle
	 * @return La PersonaDetalle con las propiedades modificadas
	 */
	public PersonaDetalle copyDTOToEntity(PersonaDetalle personaDetalle) {
		if (this.getIdPersonaDetalle() != null) {
			personaDetalle.setIdPersonaDetalle(this.getIdPersonaDetalle());
		}
		if (this.getIdPersona() != null) {
			personaDetalle.setIdPersona(this.getIdPersona());
		}
		if (this.getFechaNacimiento() != null) {
			personaDetalle.setFechaNacimiento(new Date(this.getFechaNacimiento()));
		}
		if (this.getFechaExpedicionDocumento() != null) {
			personaDetalle.setFechaExpedicionDocumento(new Date(this.getFechaExpedicionDocumento()));
		}
		if (this.getGenero() != null) {
			personaDetalle.setGenero(this.getGenero());
		}
		if (this.getIdOcupacionProfesion() != null) {
			personaDetalle.setIdOcupacionProfesion(this.getIdOcupacionProfesion());
		}
		if (this.getGradoAcademico() != null) {
			personaDetalle.setGradoAcademico(this.getGradoAcademico());
		}
		if (this.getNivelEducativo() != null) {
			personaDetalle.setNivelEducativo(this.getNivelEducativo());
		}
		if (this.getCabezaHogar() != null) {
			personaDetalle.setCabezaHogar(this.getCabezaHogar());
		}
		if (this.getAutorizaUsoDatosPersonales() != null) {
			personaDetalle.setAutorizaUsoDatosPersonales(this.getAutorizaUsoDatosPersonales());
		}
		if (this.getResideSectorRural() != null) {
			personaDetalle.setResideSectorRural(this.getResideSectorRural());
		}
		if (this.getEstadoCivil() != null) {
			personaDetalle.setEstadoCivil(this.getEstadoCivil());
		}
		if (this.getHabitaCasaPropia() != null) {
			personaDetalle.setHabitaCasaPropia(this.getHabitaCasaPropia());
		}
		if (this.getFallecido() != null) {
			personaDetalle.setFallecido(this.getFallecido());
		}
		if (this.getFechaFallecido() != null) {
			personaDetalle.setFechaFallecido(new Date(this.getFechaFallecido()));
		}
		if (this.getBeneficiarioSubsidio() != null) {
			personaDetalle.setBeneficiarioSubsidio(this.getBeneficiarioSubsidio());
		}
		if (this.getFechaDefuncion() != null) {
		    personaDetalle.setFechaDefuncion(new Date(this.getFechaDefuncion()));
		}
		if(this.getEstudianteTrabajoDesarrolloHumano() != null ){
			personaDetalle.setEstudianteTrabajoDesarrolloHumano(this.getEstudianteTrabajoDesarrolloHumano());
		}
		if (this.getOrientacionSexual() != null) {
			personaDetalle.setOrientacionSexual(this.getOrientacionSexual());
		}
		if (this.getFactorVulnerabilidad() != null) {
			personaDetalle.setFactorVulnerabilidad(this.getFactorVulnerabilidad());
		}
		if (this.getPertenenciaEtnica() != null) {
			personaDetalle.setPertenenciaEtnica(this.getPertenenciaEtnica());
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

	/**
	 * Copia las propiedades del entity que llega por parámetro al actual DTO.
	 * 
	 * @param personaDetalle
	 */
	public void copyEntityToDTO(PersonaDetalle personaDetalle) {

		if (this.getIdPersonaDetalle() != null) {
			this.setIdPersonaDetalle(personaDetalle.getIdPersonaDetalle());
		}
		if (personaDetalle.getIdPersona() != null) {
			this.setIdPersona(personaDetalle.getIdPersona());
		}
		if (personaDetalle.getFechaNacimiento() != null) {
			this.setFechaNacimiento(personaDetalle.getFechaNacimiento().getTime());
		}
		if (personaDetalle.getFechaExpedicionDocumento() != null) {
			this.setFechaExpedicionDocumento(personaDetalle.getFechaExpedicionDocumento().getTime());
		}
		if (personaDetalle.getGenero() != null) {
			this.setGenero(personaDetalle.getGenero());
		}
		if (personaDetalle.getIdOcupacionProfesion() != null) {
			this.setIdOcupacionProfesion(personaDetalle.getIdOcupacionProfesion());
		}
		if (personaDetalle.getGradoAcademico() != null) {
			this.setGradoAcademico(personaDetalle.getGradoAcademico());
		}
		if (personaDetalle.getNivelEducativo() != null) {
			this.setNivelEducativo(personaDetalle.getNivelEducativo());
		}
		if (personaDetalle.getCabezaHogar() != null) {
			this.setCabezaHogar(personaDetalle.getCabezaHogar());
		}
		if (personaDetalle.getAutorizaUsoDatosPersonales() != null) {
			this.setAutorizaUsoDatosPersonales(personaDetalle.getAutorizaUsoDatosPersonales());
		}
		if (personaDetalle.getResideSectorRural() != null) {
			this.setResideSectorRural(personaDetalle.getResideSectorRural());
		}
		if (personaDetalle.getEstadoCivil() != null) {
			this.setEstadoCivil(personaDetalle.getEstadoCivil());
		}
		if (personaDetalle.getHabitaCasaPropia() != null) {
			this.setHabitaCasaPropia(personaDetalle.getHabitaCasaPropia());
		}
		if (personaDetalle.getFallecido() != null) {
			this.setFallecido(personaDetalle.getFallecido());
		}
		if (personaDetalle.getFechaFallecido() != null) {
			this.setFechaFallecido(personaDetalle.getFechaFallecido().getTime());
		}
		if (personaDetalle.getBeneficiarioSubsidio() != null) {
			this.setBeneficiarioSubsidio(personaDetalle.getBeneficiarioSubsidio());
		}
		if (personaDetalle.getFechaDefuncion() != null){
		    this.setFechaDefuncion(personaDetalle.getFechaDefuncion().getTime());
		}
		if (personaDetalle.getEstudianteTrabajoDesarrolloHumano() != null ){
			this.setEstudianteTrabajoDesarrolloHumano(personaDetalle.getEstudianteTrabajoDesarrolloHumano());
		}
		if (personaDetalle.getOrientacionSexual() != null) {
			this.setOrientacionSexual(personaDetalle.getOrientacionSexual());
		}
		if (personaDetalle.getFactorVulnerabilidad() != null) {
			this.setFactorVulnerabilidad(personaDetalle.getFactorVulnerabilidad());
		}
		if (personaDetalle.getPertenenciaEtnica() != null) {
			this.setPertenenciaEtnica(personaDetalle.getPertenenciaEtnica());
		}
		if (personaDetalle.getIdPaisResidencia() != null) {
			this.setIdPaisResidencia(personaDetalle.getIdPaisResidencia());
		}
		if (personaDetalle.getIdResguardo() != null) {
			this.setIdResguardo(personaDetalle.getIdResguardo());
		}
		if (personaDetalle.getIdPuebloIndigena() != null) {
			this.setIdPuebloIndigena(personaDetalle.getIdPuebloIndigena());
		}
	}

	/**Obtiene el valor de idPersonaDetalle
	 * @return El valor de idPersonaDetalle
	 */
	public Long getIdPersonaDetalle() {
		return idPersonaDetalle;
	}

	/** Establece el valor de idPersonaDetalle
	 * @param idPersonaDetalle El valor de idPersonaDetalle por asignar
	 */
	public void setIdPersonaDetalle(Long idPersonaDetalle) {
		this.idPersonaDetalle = idPersonaDetalle;
	}

	/**Obtiene el valor de idPersona
	 * @return El valor de idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/** Establece el valor de idPersona
	 * @param idPersona El valor de idPersona por asignar
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**Obtiene el valor de fechaNacimiento
	 * @return El valor de fechaNacimiento
	 */
	public Long getFechaNacimiento() {
		return fechaNacimiento;
	}

	/** Establece el valor de fechaNacimiento
	 * @param fechaNacimiento El valor de fechaNacimiento por asignar
	 */
	public void setFechaNacimiento(Long fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**Obtiene el valor de fechaExpedicionDocumento
	 * @return El valor de fechaExpedicionDocumento
	 */
	public Long getFechaExpedicionDocumento() {
		return fechaExpedicionDocumento;
	}

	/** Establece el valor de fechaExpedicionDocumento
	 * @param fechaExpedicionDocumento El valor de fechaExpedicionDocumento por asignar
	 */
	public void setFechaExpedicionDocumento(Long fechaExpedicionDocumento) {
		this.fechaExpedicionDocumento = fechaExpedicionDocumento;
	}

	/**Obtiene el valor de genero
	 * @return El valor de genero
	 */
	public GeneroEnum getGenero() {
		return genero;
	}

	/** Establece el valor de genero
	 * @param genero El valor de genero por asignar
	 */
	public void setGenero(GeneroEnum genero) {
		this.genero = genero;
	}

	/**Obtiene el valor de idOcupacionProfesion
	 * @return El valor de idOcupacionProfesion
	 */
	public Integer getIdOcupacionProfesion() {
		return idOcupacionProfesion;
	}

	/** Establece el valor de idOcupacionProfesion
	 * @param idOcupacionProfesion El valor de idOcupacionProfesion por asignar
	 */
	public void setIdOcupacionProfesion(Integer idOcupacionProfesion) {
		this.idOcupacionProfesion = idOcupacionProfesion;
	}

	/**Obtiene el valor de gradoAcademico
	 * @return El valor de gradoAcademico
	 */
	public Short getGradoAcademico() {
		return gradoAcademico;
	}

	/** Establece el valor de gradoAcademico
	 * @param gradoAcademico El valor de gradoAcademico por asignar
	 */
	public void setGradoAcademico(Short gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	/**Obtiene el valor de nivelEducativo
	 * @return El valor de nivelEducativo
	 */
	public NivelEducativoEnum getNivelEducativo() {
		return nivelEducativo;
	}

	/** Establece el valor de nivelEducativo
	 * @param nivelEducativo El valor de nivelEducativo por asignar
	 */
	public void setNivelEducativo(NivelEducativoEnum nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	/**Obtiene el valor de cabezaHogar
	 * @return El valor de cabezaHogar
	 */
	public Boolean getCabezaHogar() {
		return cabezaHogar;
	}

	/** Establece el valor de cabezaHogar
	 * @param cabezaHogar El valor de cabezaHogar por asignar
	 */
	public void setCabezaHogar(Boolean cabezaHogar) {
		this.cabezaHogar = cabezaHogar;
	}

	/**Obtiene el valor de autorizaUsoDatosPersonales
	 * @return El valor de autorizaUsoDatosPersonales
	 */
	public Boolean getAutorizaUsoDatosPersonales() {
		return autorizaUsoDatosPersonales;
	}

	/** Establece el valor de autorizaUsoDatosPersonales
	 * @param autorizaUsoDatosPersonales El valor de autorizaUsoDatosPersonales por asignar
	 */
	public void setAutorizaUsoDatosPersonales(Boolean autorizaUsoDatosPersonales) {
		this.autorizaUsoDatosPersonales = autorizaUsoDatosPersonales;
	}

	/**Obtiene el valor de resideSectorRural
	 * @return El valor de resideSectorRural
	 */
	public Boolean getResideSectorRural() {
		return resideSectorRural;
	}

	/** Establece el valor de resideSectorRural
	 * @param resideSectorRural El valor de resideSectorRural por asignar
	 */
	public void setResideSectorRural(Boolean resideSectorRural) {
		this.resideSectorRural = resideSectorRural;
	}

	/**Obtiene el valor de estadoCivil
	 * @return El valor de estadoCivil
	 */
	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	/** Establece el valor de estadoCivil
	 * @param estadoCivil El valor de estadoCivil por asignar
	 */
	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**Obtiene el valor de habitaCasaPropia
	 * @return El valor de habitaCasaPropia
	 */
	public Boolean getHabitaCasaPropia() {
		return habitaCasaPropia;
	}

	/** Establece el valor de habitaCasaPropia
	 * @param habitaCasaPropia El valor de habitaCasaPropia por asignar
	 */
	public void setHabitaCasaPropia(Boolean habitaCasaPropia) {
		this.habitaCasaPropia = habitaCasaPropia;
	}

	/**Obtiene el valor de fallecido
	 * @return El valor de fallecido
	 */
	public Boolean getFallecido() {
		return fallecido;
	}

	/** Establece el valor de fallecido
	 * @param fallecido El valor de fallecido por asignar
	 */
	public void setFallecido(Boolean fallecido) {
		this.fallecido = fallecido;
	}

	/**Obtiene el valor de fechaFallecido
	 * @return El valor de fechaFallecido
	 */
	public Long getFechaFallecido() {
		return fechaFallecido;
	}

	/** Establece el valor de fechaFallecido
	 * @param fechaFallecido El valor de fechaFallecido por asignar
	 */
	public void setFechaFallecido(Long fechaFallecido) {
		this.fechaFallecido = fechaFallecido;
	}

	/**Obtiene el valor de beneficiarioSubsidio
	 * @return El valor de beneficiarioSubsidio
	 */
	public Boolean getBeneficiarioSubsidio() {
		return beneficiarioSubsidio;
	}

	/** Establece el valor de beneficiarioSubsidio
	 * @param beneficiarioSubsidio El valor de beneficiarioSubsidio por asignar
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

	/**
	 * @return
	 */
	public Boolean getEstudianteTrabajoDesarrolloHumano() {
		return estudianteTrabajoDesarrolloHumano;
	}

	/**
	 * @param estudianteTrabajoDesarrolloHumano
	 */
	public void setEstudianteTrabajoDesarrolloHumano(Boolean estudianteTrabajoDesarrolloHumano) {
		this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
	}

	/**
	 * @return
	 */
	public OrientacionSexualEnum getOrientacionSexual() {
		return orientacionSexual;
	}

	/**Establece el valor de orientacionSexual
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

	/**Establece el valor de factorVulnerabilidad
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
	
	/**Establece el valor de pertenenciaEtnica
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

    /**
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the nombrePaisResidencia
     */
    public String getNombrePaisResidencia() {
        return nombrePaisResidencia;
    }

    /**
     * @param nombrePaisResidencia the nombrePaisResidencia to set
     */
    public void setNombrePaisResidencia(String nombrePaisResidencia) {
        this.nombrePaisResidencia = nombrePaisResidencia;
    }

	public Boolean isCabezaHogar() {
		return this.cabezaHogar;
	}

	public Boolean isAutorizaUsoDatosPersonales() {
		return this.autorizaUsoDatosPersonales;
	}

	public Boolean isResideSectorRural() {
		return this.resideSectorRural;
	}

	public Boolean isHabitaCasaPropia() {
		return this.habitaCasaPropia;
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

	public String getNombreResguardo() {
		return this.nombreResguardo;
	}

	public void setNombreResguardo(String nombreResguardo) {
		this.nombreResguardo = nombreResguardo;
	}

	public String getNombrePuebloIndigena() {
		return this.nombrePuebloIndigena;
	}

	public void setNombrePuebloIndigena(String nombrePuebloIndigena) {
		this.nombrePuebloIndigena = nombrePuebloIndigena;
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

	
}