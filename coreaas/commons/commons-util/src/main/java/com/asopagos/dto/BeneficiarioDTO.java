package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de un beneficiario
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class BeneficiarioDTO implements Serializable {

    private Long idGrupoFamiliar;

    private PersonaDTO persona;

    private ClasificacionEnum tipoBeneficiario;

    private ResultadoGeneralValidacionEnum resultadoValidacion;

    private List<ItemChequeoDTO> listaChequeo;

    private Boolean certificadoEscolaridad;

    private String comentariosInvalidez;

    private EstadoAfiliadoEnum estadoBeneficiarioAfiliado;

    private EstadoAfiliadoEnum estadoBeneficiarioCaja;

    private Boolean estudianteTrabajoDesarrolloHumano;

    private Date fechaAfiliacion;

    private Date fechaRecepcionCertificadoEscolar;

    private Date fechaReporteInvalidez;
    
    private Date fechaInicioInvalidez;

    private Date fechaVencimientoCertificadoEscolar;

    private Boolean invalidez;

    private Boolean labora;

    private Long idBeneficiario;

    private Byte numero;
    
    private BigDecimal salarioMensualBeneficiario;
	
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
    
    private Date fechaRetiro; 
    
    private Date fechaInicioSociedadConyugal;
    
    private Date fechaFinSociedadConyugal; 
    
    private PersonaDTO personaAfiliada;
    
    private Long idEmpleador;
    
    private Long idSolicitud;

    private Boolean conyugeCuidador;

    private Date fechaInicioConyugeCuidador;

    private Long idConyugeCuidador;

    private Date fechaFinConyugeCuidador;
    
    /**
     * Afiliación a la que pertenece el beneficiario 
     */
    private Long idRolAfiliado;
    
    /**
     * Identifica el grado academico del beneficiario
     */
    private Short idGradoAcademico;
    
    /**
     * Fecha recepción de los documentos adjuntados al beneficiario
     */
    private Long fechaRecepcionDocumento;
    
    /**
     * Representa si al beneficiario que ya se encuentra activo respecto a un afiliado principal se le van a omitir sus validaciones o no
     */
    private Boolean omitirValidaciones;

    public BeneficiarioDTO() {
        super();
    }
    
    public BeneficiarioDTO(Beneficiario beneficiario, PersonaDetalle personaDetalle, CondicionInvalidez conInvalidez,
            BeneficiarioDetalle benDetalle) {
        this.setIdBeneficiario(beneficiario.getIdBeneficiario());
        this.setPersona(PersonaDTO.convertPersonaToDTO(beneficiario.getPersona(), personaDetalle));
        if (beneficiario.getGrupoFamiliar() != null) {
            this.setIdGrupoFamiliar(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar());
            this.setNumero(beneficiario.getGrupoFamiliar().getNumero());
        }
        this.setTipoBeneficiario(beneficiario.getTipoBeneficiario());
        this.setEstadoBeneficiarioAfiliado(beneficiario.getEstadoBeneficiarioAfiliado());
        this.setEstudianteTrabajoDesarrolloHumano(beneficiario.getEstudianteTrabajoDesarrolloHumano());
        if (conInvalidez != null) {
            this.setInvalidez(conInvalidez.getCondicionInvalidez());
            this.setFechaReporteInvalidez(conInvalidez.getFechaReporteInvalidez());
            this.setComentariosInvalidez(conInvalidez.getComentarioInvalidez());
            this.setConyugeCuidador(conInvalidez.getConyugeCuidador());
            this.setFechaInicioConyugeCuidador(conInvalidez.getFechaInicioConyugeCuidador());
            this.setFechaFinConyugeCuidador(conInvalidez.getFechaFinConyugeCuidador());
            this.setIdConyugeCuidador(conInvalidez.getIdConyugeCuidador());
        }
        if (benDetalle != null) {
            this.setCertificadoEscolaridad(benDetalle.getCertificadoEscolaridad());
            this.setLabora(benDetalle.getLabora());
            this.setSalarioMensualBeneficiario(benDetalle.getSalarioMensualBeneficiario());
        }
        this.setFechaAfiliacion(beneficiario.getFechaAfiliacion());
        this.setMotivoDesafiliacion(beneficiario.getMotivoDesafiliacion());
        this.setFechaRetiro(beneficiario.getFechaRetiro());
        this.setFechaInicioSociedadConyugal(beneficiario.getFechaInicioSociedadConyugal());
        this.setFechaFinSociedadConyugal(beneficiario.getFechaFinSociedadConyugal());
        this.setIdRolAfiliado(beneficiario.getIdRolAfiliado());
        if (beneficiario.getOmitirValidaciones() != null){
            this.setOmitirValidaciones(beneficiario.getOmitirValidaciones());
        }
    }

    public static BeneficiarioDTO convertBeneficiarioToDTO(Beneficiario beneficiario, PersonaDetalle personaDetalle,
            CondicionInvalidez condicionInvalidez, BeneficiarioDetalle benDetalle) {
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO();
        beneficiarioDTO.setIdBeneficiario(beneficiario.getIdBeneficiario());
        beneficiarioDTO.setPersona(PersonaDTO.convertPersonaToDTO(beneficiario.getPersona(), personaDetalle));
        if (beneficiario.getGrupoFamiliar() != null) {
            beneficiarioDTO.setIdGrupoFamiliar(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar());
            beneficiarioDTO.setNumero(beneficiario.getGrupoFamiliar().getNumero());
        }
        beneficiarioDTO.setTipoBeneficiario(beneficiario.getTipoBeneficiario());
        beneficiarioDTO.setEstadoBeneficiarioAfiliado(beneficiario.getEstadoBeneficiarioAfiliado());
        
        beneficiarioDTO.setEstudianteTrabajoDesarrolloHumano(beneficiario.getEstudianteTrabajoDesarrolloHumano());
        beneficiarioDTO.setFechaAfiliacion(beneficiario.getFechaAfiliacion());
        beneficiarioDTO.setMotivoDesafiliacion(beneficiario.getMotivoDesafiliacion());
        beneficiarioDTO.setFechaRetiro(beneficiario.getFechaRetiro());
        beneficiarioDTO.setFechaInicioSociedadConyugal(beneficiario.getFechaInicioSociedadConyugal());
        beneficiarioDTO.setFechaFinSociedadConyugal(beneficiario.getFechaFinSociedadConyugal());
        beneficiarioDTO.setIdRolAfiliado(beneficiario.getIdRolAfiliado());
        if(condicionInvalidez != null){
            beneficiarioDTO.setInvalidez(condicionInvalidez.getCondicionInvalidez());
            beneficiarioDTO.setFechaReporteInvalidez(condicionInvalidez.getFechaReporteInvalidez());
            beneficiarioDTO.setFechaInicioInvalidez(condicionInvalidez.getFechaInicioInvalidez());
            beneficiarioDTO.setComentariosInvalidez(condicionInvalidez.getComentarioInvalidez());
            beneficiarioDTO.setConyugeCuidador(condicionInvalidez.getConyugeCuidador());
            beneficiarioDTO.setFechaInicioConyugeCuidador(condicionInvalidez.getFechaInicioConyugeCuidador());
            beneficiarioDTO.setFechaFinConyugeCuidador(condicionInvalidez.getFechaFinConyugeCuidador());
            beneficiarioDTO.setIdConyugeCuidador(condicionInvalidez.getIdConyugeCuidador());
        }
        
        if(benDetalle != null){
            beneficiarioDTO.setLabora(benDetalle.getLabora());
            beneficiarioDTO.setCertificadoEscolaridad(benDetalle.getCertificadoEscolaridad());
            beneficiarioDTO.setSalarioMensualBeneficiario(benDetalle.getSalarioMensualBeneficiario());
        }
        if (beneficiario.getOmitirValidaciones() != null){
            beneficiarioDTO.setOmitirValidaciones(beneficiario.getOmitirValidaciones());
        }
        return beneficiarioDTO;
    }

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona
     *        the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    /**
     * @return the tipoBeneficiario
     */
    public ClasificacionEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario
     *        the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(ClasificacionEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the resultadoValidacion
     */
    public ResultadoGeneralValidacionEnum getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion
     *        the resultadoValidacion to set
     */
    public void setResultadoValidacion(ResultadoGeneralValidacionEnum resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the listaChequeo
     */
    public List<ItemChequeoDTO> getListaChequeo() {
        return listaChequeo;
    }

    /**
     * @param listaChequeo
     *        the listaChequeo to set
     */
    public void setListaChequeo(List<ItemChequeoDTO> listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar
     *        the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the certificadoEscolaridad
     */
    public Boolean getCertificadoEscolaridad() {
        return certificadoEscolaridad;
    }

    /**
     * @param certificadoEscolaridad
     *        the certificadoEscolaridad to set
     */
    public void setCertificadoEscolaridad(Boolean certificadoEscolaridad) {
        this.certificadoEscolaridad = certificadoEscolaridad;
    }

    /**
     * @return the comentariosInvalidez
     */
    public String getComentariosInvalidez() {
        return comentariosInvalidez;
    }

    /**
     * @param comentariosInvalidez
     *        the comentariosInvalidez to set
     */
    public void setComentariosInvalidez(String comentariosInvalidez) {
        this.comentariosInvalidez = comentariosInvalidez;
    }

    /**
     * @return the estadoBeneficiarioAfiliado
     */
    public EstadoAfiliadoEnum getEstadoBeneficiarioAfiliado() {
        return estadoBeneficiarioAfiliado;
    }

    /**
     * @param estadoBeneficiarioAfiliado
     *        the estadoBeneficiarioAfiliado to set
     */
    public void setEstadoBeneficiarioAfiliado(EstadoAfiliadoEnum estadoBeneficiarioAfiliado) {
        this.estadoBeneficiarioAfiliado = estadoBeneficiarioAfiliado;
    }

    /**
     * @return the estadoBeneficiarioCaja
     */
    public EstadoAfiliadoEnum getEstadoBeneficiarioCaja() {
        return estadoBeneficiarioCaja;
    }

    /**
     * @param estadoBeneficiarioCaja
     *        the estadoBeneficiarioCaja to set
     */
    public void setEstadoBeneficiarioCaja(EstadoAfiliadoEnum estadoBeneficiarioCaja) {
        this.estadoBeneficiarioCaja = estadoBeneficiarioCaja;
    }

    /**
     * @return the estudianteTrabajoDesarrolloHumano
     */
    public Boolean getEstudianteTrabajoDesarrolloHumano() {
        return estudianteTrabajoDesarrolloHumano;
    }

    public BigDecimal getSalarioMensualBeneficiario() {
		return salarioMensualBeneficiario;
	}

	public void setSalarioMensualBeneficiario(BigDecimal salarioMensualBeneficiario) {
		this.salarioMensualBeneficiario = salarioMensualBeneficiario;
	}

	/**
     * @param estudianteTrabajoDesarrolloHumano
     *        the estudianteTrabajoDesarrolloHumano to set
     */
    public void setEstudianteTrabajoDesarrolloHumano(Boolean estudianteTrabajoDesarrolloHumano) {
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
    }

    /**
     * @return the fechaAfiliacion
     */
    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    /**
     * @param fechaAfiliacion
     *        the fechaAfiliacion to set
     */
    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    /**
     * @return the fechaRecepcionCertificadoEscolar
     */
    public Date getFechaRecepcionCertificadoEscolar() {
        return fechaRecepcionCertificadoEscolar;
    }

    /**
     * @param fechaRecepcionCertificadoEscolar
     *        the fechaRecepcionCertificadoEscolar to set
     */
    public void setFechaRecepcionCertificadoEscolar(Date fechaRecepcionCertificadoEscolar) {
        this.fechaRecepcionCertificadoEscolar = fechaRecepcionCertificadoEscolar;
    }

    /**
     * @return the fechaReporteInvalidez
     */
    public Date getFechaReporteInvalidez() {
        return fechaReporteInvalidez;
    }

    /**
     * @param fechaReporteInvalidez
     *        the fechaReporteInvalidez to set
     */
    public void setFechaReporteInvalidez(Date fechaReporteInvalidez) {
        this.fechaReporteInvalidez = fechaReporteInvalidez;
    }

    /**
     * @return the fechaVencimientoCertificadoEscolar
     */
    public Date getFechaVencimientoCertificadoEscolar() {
        return fechaVencimientoCertificadoEscolar;
    }

    /**
     * @param fechaVencimientoCertificadoEscolar
     *        the fechaVencimientoCertificadoEscolar to set
     */
    public void setFechaVencimientoCertificadoEscolar(Date fechaVencimientoCertificadoEscolar) {
        this.fechaVencimientoCertificadoEscolar = fechaVencimientoCertificadoEscolar;
    }

    /**
     * @return the invalidez
     */
    public Boolean getInvalidez() {
        return invalidez;
    }

    /**
     * @param invalidez
     *        the invalidez to set
     */
    public void setInvalidez(Boolean invalidez) {
        this.invalidez = invalidez;
    }

    /**
     * @return the labora
     */
    public Boolean getLabora() {
        return labora;
    }

    /**
     * @param labora
     *        the labora to set
     */
    public void setLabora(Boolean labora) {
        this.labora = labora;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * @return the numero
     */
    public Byte getNumero() {
        return numero;
    }

    /**
     * @param numero
     *        the numero to set
     */
    public void setNumero(Byte numero) {
        this.numero = numero;
    }

	/**
	 * Método que retorna el valor de motivoDesafiliacion.
	 * @return valor de motivoDesafiliacion.
	 */
	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * Método encargado de modificar el valor de motivoDesafiliacion.
	 * @param valor para modificar motivoDesafiliacion.
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}

	/**
	 * Método que retorna el valor de fechaRetiro.
	 * @return valor de fechaRetiro.
	 */
	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	/**
	 * Método encargado de modificar el valor de fechaRetiro.
	 * @param valor para modificar fechaRetiro.
	 */
	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	/**
	 * Método que retorna el valor de fechaInicioSociedadConyugal.
	 * @return valor de fechaInicioSociedadConyugal.
	 */
	public Date getFechaInicioSociedadConyugal() {
		return fechaInicioSociedadConyugal;
	}

	/**
	 * Método encargado de modificar el valor de fechaInicioSociedadConyugal.
	 * @param valor para modificar fechaInicioSociedadConyugal.
	 */
	public void setFechaInicioSociedadConyugal(Date fechaInicioSociedadConyugal) {
		this.fechaInicioSociedadConyugal = fechaInicioSociedadConyugal;
	}

	/**
	 * Método que retorna el valor de fechaFinSociedadConyugal.
	 * @return valor de fechaFinSociedadConyugal.
	 */
	public Date getFechaFinSociedadConyugal() {
		return fechaFinSociedadConyugal;
	}

	/**
	 * Método encargado de modificar el valor de fechaFinSociedadConyugal.
	 * @param valor para modificar fechaFinSociedadConyugal.
	 */
	public void setFechaFinSociedadConyugal(Date fechaFinSociedadConyugal) {
		this.fechaFinSociedadConyugal = fechaFinSociedadConyugal;
	}

    /**
     * Método que retorna el valor de idRolAfiliado.
     * @return valor de idRolAfiliado.
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * Método encargado de modificar el valor de idRolAfiliado.
     * @param valor para modificar idRolAfiliado.
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the personaAfiliada
     */
    public PersonaDTO getPersonaAfiliada() {
        return personaAfiliada;
    }

    /**
     * @param personaAfiliada the personaAfiliada to set
     */
    public void setPersonaAfiliada(PersonaDTO personaAfiliada) {
        this.personaAfiliada = personaAfiliada;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the idGradoAcademico
     */
    public Short getIdGradoAcademico() {
        return idGradoAcademico;
    }

    /**
     * @param idGradoAcademico
     *        the idGradoAcademico to set
     */
    public void setIdGradoAcademico(Short idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    /**
     * @return the fechaRecepcionDocumento
     */
    public Long getFechaRecepcionDocumento() {
        return fechaRecepcionDocumento;
    }

    /**
     * @param fechaRecepcionDocumento the fechaRecepcionDocumento to set
     */
    public void setFechaRecepcionDocumento(Long fechaRecepcionDocumento) {
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }

    /**
     * @return the omitirValidaciones
     */
    public Boolean getOmitirValidaciones() {
        return omitirValidaciones;
    }

    /**
     * @param omitirValidaciones the omitirValidaciones to set
     */
    public void setOmitirValidaciones(Boolean omitirValidaciones) {
        this.omitirValidaciones = omitirValidaciones;
    }

    /**
     * @return the fechaInicioInvalidez
     */
    public Date getFechaInicioInvalidez() {
        return fechaInicioInvalidez;
    }

    /**
     * @param fechaInicioInvalidez the fechaInicioInvalidez to set
     */
    public void setFechaInicioInvalidez(Date fechaInicioInvalidez) {
        this.fechaInicioInvalidez = fechaInicioInvalidez;
    }
    
    public Boolean getConyugeCuidador() {
        return this.conyugeCuidador;
    }

    public void setConyugeCuidador(Boolean conyugeCuidador) {
        this.conyugeCuidador = conyugeCuidador;
    }

    public Date getFechaInicioConyugeCuidador() {
        return this.fechaInicioConyugeCuidador;
    }

    public void setFechaInicioConyugeCuidador(Date fechaInicioConyugeCuidador) {
        this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador;
    }

    public Date getFechaFinConyugeCuidador() {
        return this.fechaFinConyugeCuidador;
    }

    public void setFechaFinConyugeCuidador(Date fechaFinConyugeCuidador) {
        this.fechaFinConyugeCuidador = fechaFinConyugeCuidador;
    }

    public Long getIdConyugeCuidador() {
        return this.idConyugeCuidador;
    }

    public void setIdConyugeCuidador(Long idConyugeCuidador) {
        this.idConyugeCuidador = idConyugeCuidador;
    }

}
