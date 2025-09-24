package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

/**
 * <b>Descripción:</b> DTO que transporta los datos básicos de un beneficiario
 * hijo o padre
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class BeneficiarioHijoPadreDTO extends BeneficiarioDTO implements Serializable{

    private PersonaDTO persona;

    private ClasificacionEnum tipoBeneficiario;

    private Boolean certificadoEscolaridad;

    private Date fechaVencimientoCertificadoEscolar;

    private Date fechaRecepcionCertificadoEscolar;

    private Boolean estudianteTrabajoDesarrolloHumano;

    private Boolean invalidez;

    private Date fechaReporteInvalidez;
    
    private Date fechaInicioInvalidez;
        
    private Boolean conyugeCuidador;

    private Date fechaInicioConyugeCuidador;

    private Date fechaFinConyugeCuidador;

    private Long idConyugeCuidador;

    private String comentariosInvalidez;

    private ResultadoGeneralValidacionEnum resultadoValidacion;

    private Long idGrupoFamiliar;

    private List<ItemChequeoDTO> listaChequeo;

    private Date fechaAfiliacion;

    private Long idBeneficiario;

    private Short idGradoAcademico;
    
    private Date fechaRetiro;

    // Dato no corresponde a una afiliacion
    // Sin embargo de adjunta 
    private UbicacionDTO ubicacion;
    /**
     * Afiliacion a la que pertenece el beneficiario tipo hijo
     */
    private Long idRolAfiliado;
    
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;

    /**
     * Representa si al beneficiario que ya se encuentra activo respecto a un afiliado principal se le van a omitir sus validaciones o no
     */
    private Boolean omitirValidaciones;
    
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
     * @return the estudianteTrabajoDesarrolloHumano
     */
    public Boolean getEstudianteTrabajoDesarrolloHumano() {
        return estudianteTrabajoDesarrolloHumano;
    }

    /**
     * @param estudianteTrabajoDesarrolloHumano
     *        the estudianteTrabajoDesarrolloHumano to set
     */
    public void setEstudianteTrabajoDesarrolloHumano(Boolean estudianteTrabajoDesarrolloHumano) {
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
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
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado
     *        the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
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

    public UbicacionDTO getUbicacion() {
        return this.ubicacion;

    }
    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeneficiarioHijoPadreDTO [persona=");
		builder.append(persona);
		builder.append(", tipoBeneficiario=");
		builder.append(tipoBeneficiario);
		builder.append(", certificadoEscolaridad=");
		builder.append(certificadoEscolaridad);
		builder.append(", fechaVencimientoCertificadoEscolar=");
		builder.append(fechaVencimientoCertificadoEscolar);
		builder.append(", fechaRecepcionCertificadoEscolar=");
		builder.append(fechaRecepcionCertificadoEscolar);
		builder.append(", estudianteTrabajoDesarrolloHumano=");
		builder.append(estudianteTrabajoDesarrolloHumano);
		builder.append(", invalidez=");
		builder.append(invalidez);
		builder.append(", fechaReporteInvalidez=");
		builder.append(fechaReporteInvalidez);
		builder.append(", fechaInicioInvalidez=");
		builder.append(fechaInicioInvalidez);
		builder.append(", comentariosInvalidez=");
		builder.append(comentariosInvalidez);
		builder.append(", resultadoValidacion=");
		builder.append(resultadoValidacion);
		builder.append(", idGrupoFamiliar=");
		builder.append(idGrupoFamiliar);
		builder.append(", listaChequeo=");
		builder.append(listaChequeo);
		builder.append(", fechaAfiliacion=");
		builder.append(fechaAfiliacion);
		builder.append(", idBeneficiario=");
		builder.append(idBeneficiario);
		builder.append(", idGradoAcademico=");
		builder.append(idGradoAcademico);
		builder.append(", fechaRetiro=");
		builder.append(fechaRetiro);
		builder.append(", idRolAfiliado=");
		builder.append(idRolAfiliado);
		builder.append(", motivoDesafiliacion=");
		builder.append(motivoDesafiliacion);
		builder.append(", omitirValidaciones=");
		builder.append(omitirValidaciones);
		builder.append("]");
		return builder.toString();
	}
    
    

}
