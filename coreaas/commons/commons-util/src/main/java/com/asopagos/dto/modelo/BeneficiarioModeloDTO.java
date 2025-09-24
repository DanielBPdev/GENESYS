package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;

/**
 * DTO con los datos del Modelo de Beneficiario.
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class BeneficiarioModeloDTO extends PersonaModeloDTO implements Serializable {

    /**
     * Código identificador de llave primaria del beneficiario
     */
    private Long idBeneficiario;

    /**
     * Id que identifica al afiliado asociada al beneficiario
     */
    private Long idAfiliado;

    /**
     * Id que identifica al grupo familiar asoaciado beneficiario
     */
    private Long idGrupoFamiliar;

    /**
     * Sujeto Tramite tipo solicitante de tipo beneficiario
     */
    private ClasificacionEnum tipoBeneficiario;

    /**
     * Descripción del estado de afiliación del beneficiario
     */
    private EstadoAfiliadoEnum estadoBeneficiarioCaja;

    /**
     * Descripción del estado de afiliación del beneficiario
     */
    private EstadoAfiliadoEnum estadoBeneficiarioAfiliado;

    /**
     * Código identificador de llave primaria del beneficiario Detalle
     */
    private Long idBeneficiarioDetalle;

    /**
     * Indicador S/N si el beneficiario labora[S=Si N=No]
     */
    private Boolean labora;

    /**
     * Indicador S/N si el beneficiario cuenta con certificado de escolaridad [S=Si N=No]
     */
    private Boolean certificadoEscolaridad;

    /**
     * Fecha de vencimiento del certificado de escolaridad
     */
    private Long fechaVencimientoCertificadoEscolar;

    /**
     * Fecha recepción del certificado de escolaridad
     */
    private Long fechaRecepcionCertificadoEscolar;

    private Boolean estudianteTrabajoDesarrolloHumano;

    /**
     * Indicador S/N de si el beneficiario posee alguna invalidez
     */
    private Boolean invalidez;

    /**
     * Fecha del reporte de invalidez
     */
    private Long fechaReporteInvalidez;
    
    /**
     * Fecha de inicio de invalidez
     */
    private Long fechaInicioInvalidez;

    /**
     * Comentarios sobre la invalidez del benificiario
     */
    private String comentariosInvalidez;

    /**
     * Fecha de afiliación del beneficiario
     */
    private Long fechaAfiliacion;

    /**
     * Salario mensual del beneficiaro
     */
    private BigDecimal salarioMensualBeneficiario;

    /**
     * Digito de verificación de la persona
     */
    private Short gradoAcademicoBeneficiario;

    /**
     * Descripción de la desafiliacion del beneficiario
     */
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;

    /**
     * Fecha retiro del Beneficiario
     */
    private Long fechaRetiro;

    /**
     * Fecha inicio sociedad conyugal del beneficiario
     */
    private Long fechaInicioSociedadConyugal;

    /**
     * Fecha fin sociedad conyugal del beneficiario
     */
    private Long fechaFinSociedadConyugal;
    /**
     * Identificador del rol afiliado al que pertenece el beneficiario
     */
    private Long idRolAfiliado;

    /**
     * Representa el afiliado al que pertenece el beneficiario
     */
    private AfiliadoModeloDTO afiliado;
    
    /**
     * Indica si la trasacción actualiza los datos de certificado escolar
     */
    private Boolean actualizarCertificado;

    private Boolean conyugeCuidador;

    private Long fechaInicioConyugeCuidador;

    private Long fechaFinConyugeCuidador;

    private Long idConyugeCuidador;

    /**
     * Asocia los datos del DTO a la Entidad
     * @return Persona
     */
    public Beneficiario convertToBeneficiarioEntity() {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setIdBeneficiario(this.getIdBeneficiario());
        if (this.getIdPersona() != null) {
            Persona persona = new Persona();
            persona.setIdPersona(this.getIdPersona());
            beneficiario.setPersona(persona);
        }
        if (this.getIdGrupoFamiliar() != null) {
            GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
            grupoFamiliar.setIdGrupoFamiliar(this.getIdGrupoFamiliar());
            beneficiario.setGrupoFamiliar(grupoFamiliar);
        }
        beneficiario.setTipoBeneficiario(this.getTipoBeneficiario());
        //beneficiario.setEstadoBeneficiarioCaja(this.getEstadoBeneficiarioCaja());
        beneficiario.setEstadoBeneficiarioAfiliado(this.getEstadoBeneficiarioAfiliado());
        //beneficiario.setLabora(this.getLabora());
        //beneficiario.setCertificadoEscolaridad(this.getCertificadoEscolaridad());
        //if (this.getFechaVencimientoCertificadoEscolar() != null) {
        //  beneficiario.setFechaVencimientoCertificadoEscolar(new Date(this.getFechaVencimientoCertificadoEscolar()));
        //}
        //if (this.getFechaRecepcionCertificadoEscolar() != null) {
        //beneficiario.setFechaRecepcionCertificadoEscolar(new Date(this.getFechaRecepcionCertificadoEscolar()));
        // }
        beneficiario.setEstudianteTrabajoDesarrolloHumano(this.getEstudianteTrabajoDesarrolloHumano());
        /*
         * beneficiario.setInvalidez(this.getInvalidez());
         * if (this.getFechaReporteInvalidez() != null) {
         * beneficiario.setFechaReporteInvalidez(new Date(this.getFechaReporteInvalidez()));
         * }
         * beneficiario.setComentariosInvalidez(this.getComentariosInvalidez());
         */
        if (this.getFechaAfiliacion() != null) {
            beneficiario.setFechaAfiliacion(new Date(this.getFechaAfiliacion()));
        }
        //beneficiario.setSalarioMensualBeneficiario(this.getSalarioMensualBeneficiario());
        beneficiario.setGradoAcademico(this.getGradoAcademicoBeneficiario());
        beneficiario.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
        beneficiario.setFechaRetiro(this.getFechaRetiro() != null ? new Date(this.getFechaRetiro()): null);
        if (this.getIdAfiliado() != null) {
            Afiliado afiliado = new Afiliado();
            afiliado.setIdAfiliado(this.getIdAfiliado());
            beneficiario.setAfiliado(afiliado);
        }
        beneficiario.setFechaInicioSociedadConyugal(
                this.getFechaInicioSociedadConyugal() != null ? new Date(this.getFechaInicioSociedadConyugal()) : null);
        beneficiario.setFechaFinSociedadConyugal(
                this.getFechaFinSociedadConyugal() != null ? new Date(this.getFechaFinSociedadConyugal()) : null);
        beneficiario.setIdRolAfiliado(this.getIdRolAfiliado());
        return beneficiario;
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * @return Persona
     */
    public CondicionInvalidez convertToCondicionInvalidezEntity() {
        CondicionInvalidez condicionInvalidez = new CondicionInvalidez();
        condicionInvalidez.setCondicionInvalidez(this.invalidez!= null ? this.invalidez:null);
        if (this.invalidez != null) {
            condicionInvalidez.setFechaReporteInvalidez(this.fechaReporteInvalidez != null ? new Date(this.fechaReporteInvalidez):null);
            condicionInvalidez.setFechaInicioInvalidez(this.fechaInicioInvalidez != null ? new Date(this.fechaInicioInvalidez):null);
        }
        condicionInvalidez.setConyugeCuidador(this.conyugeCuidador);
        if (this.conyugeCuidador != null) {
            condicionInvalidez.setFechaInicioConyugeCuidador(this.fechaInicioConyugeCuidador != null ? new Date(this.fechaInicioConyugeCuidador):null);
            condicionInvalidez.setFechaFinConyugeCuidador(this.fechaFinConyugeCuidador != null ? new Date(this.fechaFinConyugeCuidador):null);
            condicionInvalidez.setIdConyugeCuidador(this.idConyugeCuidador != null ?this.idConyugeCuidador:null);
        }
        condicionInvalidez.setComentarioInvalidez(this.comentariosInvalidez!= null ? this.comentariosInvalidez:null);

        return condicionInvalidez;
    }

    /**
     * @param Asocia
     *        los datos de la Entidad al DTO
     * @return PersonaModeloDTO
     */
    public void convertToDTO(Beneficiario beneficiario, PersonaDetalle personaDetalle, CondicionInvalidez condicionInvalidez,
            BeneficiarioDetalle beneDetalle) {

        this.setIdBeneficiario(beneficiario.getIdBeneficiario());
        if (beneficiario.getGrupoFamiliar() != null && beneficiario.getGrupoFamiliar().getIdGrupoFamiliar() != null) {
            this.setIdGrupoFamiliar(beneficiario.getGrupoFamiliar().getIdGrupoFamiliar());
        }
        if (beneficiario.getPersona() != null) {
            super.convertToDTO(beneficiario.getPersona(), personaDetalle);
        }
        this.setTipoBeneficiario(beneficiario.getTipoBeneficiario());
        this.setEstadoBeneficiarioAfiliado(beneficiario.getEstadoBeneficiarioAfiliado());
        if (beneDetalle != null) {
            this.setIdBeneficiarioDetalle(beneDetalle.getIdBeneficiarioDetalle());
            this.setCertificadoEscolaridad(beneDetalle.getCertificadoEscolaridad());
            this.setLabora(beneDetalle.getLabora());
            this.setSalarioMensualBeneficiario(beneDetalle.getSalarioMensualBeneficiario());
        }
        this.setEstudianteTrabajoDesarrolloHumano(beneficiario.getEstudianteTrabajoDesarrolloHumano());
        if (beneficiario.getFechaAfiliacion() != null) {
            this.setFechaAfiliacion(beneficiario.getFechaAfiliacion().getTime());
        }
        this.setGradoAcademicoBeneficiario(beneficiario.getGradoAcademico());
        this.setMotivoDesafiliacion(beneficiario.getMotivoDesafiliacion());
        this.setFechaRetiro(beneficiario.getFechaRetiro() != null ? beneficiario.getFechaRetiro().getTime() : null);
        if (beneficiario.getAfiliado() != null && beneficiario.getAfiliado().getIdAfiliado() != null) {
            this.setIdAfiliado(beneficiario.getAfiliado().getIdAfiliado());
            AfiliadoModeloDTO afiliado = new AfiliadoModeloDTO();
            afiliado.convertToDTO(beneficiario.getAfiliado(), null);
            this.setAfiliado(afiliado);
        }
        this.setFechaInicioSociedadConyugal(
                beneficiario.getFechaInicioSociedadConyugal() != null ? beneficiario.getFechaInicioSociedadConyugal().getTime() : null);
        this.setFechaFinSociedadConyugal(
                beneficiario.getFechaFinSociedadConyugal() != null ? beneficiario.getFechaFinSociedadConyugal().getTime() : null);
        this.setIdRolAfiliado(beneficiario.getIdRolAfiliado());

        if (condicionInvalidez != null) {
            this.setComentariosInvalidez(condicionInvalidez.getComentarioInvalidez());
            this.setInvalidez(condicionInvalidez.getCondicionInvalidez());
            this.setConyugeCuidador(condicionInvalidez.getConyugeCuidador());
            if (condicionInvalidez.getFechaReporteInvalidez() != null) {
                this.setFechaReporteInvalidez(condicionInvalidez.getFechaReporteInvalidez().getTime());
            }
            if(condicionInvalidez.getFechaInicioConyugeCuidador() != null){
                this.setFechaInicioConyugeCuidador(condicionInvalidez.getFechaInicioConyugeCuidador().getTime());
            }
            if(condicionInvalidez.getFechaFinConyugeCuidador() != null){
                this.setFechaFinConyugeCuidador(condicionInvalidez.getFechaFinConyugeCuidador().getTime());
            }
            if(condicionInvalidez.getIdConyugeCuidador() != null){
                this.setIdConyugeCuidador(condicionInvalidez.getIdConyugeCuidador());
            }

        }
    }

    public void convertToDTO(Beneficiario beneficiario, PersonaDetalle personaDetalle, CondicionInvalidez condicionInvalidez,
            BeneficiarioDetalle beneDetalle, CertificadoEscolarBeneficiario certificado) {
        this.convertToDTO(beneficiario, personaDetalle, condicionInvalidez, beneDetalle);
        if (certificado != null) {
            this.setFechaRecepcionCertificadoEscolar(certificado.getFechaRecepcionCertificadoEscolar() != null
                    ? certificado.getFechaRecepcionCertificadoEscolar().getTime() : null);
            this.setFechaVencimientoCertificadoEscolar(certificado.getFechaVencimientoCertificadoEscolar() != null
                    ? certificado.getFechaVencimientoCertificadoEscolar().getTime() : null);
        }
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * @param beneficiario
     *        previamente consultada.
     * @return Beneficiario
     */
    public Beneficiario copyDTOToEntity(Beneficiario beneficiario) {
        beneficiario.setIdBeneficiario(this.getIdBeneficiario());
        //beneficiario.setCertificadoEscolaridad(this.getCertificadoEscolaridad());
        beneficiario.setEstudianteTrabajoDesarrolloHumano(this.getEstudianteTrabajoDesarrolloHumano());
        //beneficiario.setLabora(this.getLabora());
        beneficiario.setEstadoBeneficiarioAfiliado(this.getEstadoBeneficiarioAfiliado());
        //beneficiario.setEstadoBeneficiarioCaja(this.getEstadoBeneficiarioCaja());
        if (this.getFechaAfiliacion() != null) {
            beneficiario.setFechaAfiliacion(new Date(this.getFechaAfiliacion()));
        }
        /*
         * if (this.getFechaRecepcionCertificadoEscolar() != null) {
         * beneficiario.setFechaRecepcionCertificadoEscolar(new Date(this.getFechaRecepcionCertificadoEscolar()));
         * }
         */
        /**
         * beneficiario.setComentariosInvalidez(this.getComentariosInvalidez());
         * beneficiario.setInvalidez(this.getInvalidez());
         * if (this.getFechaReporteInvalidez() != null) {
         * beneficiario.setFechaReporteInvalidez(new Date(this.getFechaReporteInvalidez()));
         * }
         * if (this.getFechaVencimientoCertificadoEscolar() != null) {
         * beneficiario.setFechaVencimientoCertificadoEscolar(new Date(this.getFechaVencimientoCertificadoEscolar()));
         * }
         */
        if (this.getFechaInicioSociedadConyugal() != null) {
            beneficiario.setFechaInicioSociedadConyugal(new Date(this.getFechaInicioSociedadConyugal()));
        }
        if (this.getFechaFinSociedadConyugal() != null) {
            beneficiario.setFechaFinSociedadConyugal(new Date(this.getFechaFinSociedadConyugal()));
        }
        beneficiario.setGradoAcademico(this.getGradoAcademicoBeneficiario());
        if (this.getIdGrupoFamiliar() != null) {
            GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
            grupoFamiliar.setIdGrupoFamiliar(this.getIdGrupoFamiliar());
            beneficiario.setGrupoFamiliar(grupoFamiliar);
        }
        //beneficiario.setSalarioMensualBeneficiario(this.getSalarioMensualBeneficiario());
        beneficiario.setTipoBeneficiario(this.getTipoBeneficiario());
        beneficiario.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
        if (this.getFechaRetiro() != null) {
            beneficiario.setFechaRetiro(new Date(this.getFechaRetiro()));
        }
        beneficiario.setIdRolAfiliado(this.getIdRolAfiliado());
        if (this.getIdAfiliado() != null) {
            Afiliado afiliado = new Afiliado();
            afiliado.setIdAfiliado(this.getIdAfiliado());
            beneficiario.setAfiliado(afiliado);
        }
        if (this.getIdPersona() != null) {
            Persona persona = new Persona();
            persona.setIdPersona(this.getIdPersona());
            beneficiario.setPersona(persona);
        }
        return beneficiario;
    }

    /**
     * Convierte del DTO a la Persona Detalle.
     * @param personaDetalle
     * @return
     */
    public BeneficiarioDetalle copyDTOToEntity(BeneficiarioDetalle beneDetalle) {
        if (this.getIdBeneficiarioDetalle() != null) {
            beneDetalle.setIdBeneficiarioDetalle(this.getIdBeneficiarioDetalle());
        }
        if (this.getLabora() != null) {
            beneDetalle.setLabora(this.getLabora());
        }
        if (this.getSalarioMensualBeneficiario() != null) {
            beneDetalle.setSalarioMensualBeneficiario(this.getSalarioMensualBeneficiario());
        }
        if (this.getCertificadoEscolaridad() != null) {
            beneDetalle.setCertificadoEscolaridad(this.getCertificadoEscolaridad());
        }
        return beneDetalle;
    }
    
    /**
     * Constructor que inicializa la clase DTO por medio de los datos del beneficiario y beneficiarioDetalle
     * @param beneficiario
     *        <code>Beneficiario</code>
     *        Entidad con la información del beneficiario
     * @param beneficiarioDetalle
     *        <code>BeneficiarioDetalle</code>
     *        Entidad con la información del beneficiario detalle.
     */
    public BeneficiarioModeloDTO(Beneficiario beneficiario, BeneficiarioDetalle beneficiarioDetalle) {
        this.convertToDTO(beneficiario, null, null, beneficiarioDetalle);
    }
    
    /**
     * Constructor vacio de la clase
     */
    public BeneficiarioModeloDTO(){
        
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
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado
     *        the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
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
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle
     *        the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
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
    public Long getFechaVencimientoCertificadoEscolar() {
        return fechaVencimientoCertificadoEscolar;
    }

    /**
     * @param fechaVencimientoCertificadoEscolar
     *        the fechaVencimientoCertificadoEscolar to set
     */
    public void setFechaVencimientoCertificadoEscolar(Long fechaVencimientoCertificadoEscolar) {
        this.fechaVencimientoCertificadoEscolar = fechaVencimientoCertificadoEscolar;
    }

    /**
     * @return the fechaRecepcionCertificadoEscolar
     */
    public Long getFechaRecepcionCertificadoEscolar() {
        return fechaRecepcionCertificadoEscolar;
    }

    /**
     * @param fechaRecepcionCertificadoEscolar
     *        the fechaRecepcionCertificadoEscolar to set
     */
    public void setFechaRecepcionCertificadoEscolar(Long fechaRecepcionCertificadoEscolar) {
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
    public Long getFechaReporteInvalidez() {
        return fechaReporteInvalidez;
    }

    /**
     * @param fechaReporteInvalidez
     *        the fechaReporteInvalidez to set
     */
    public void setFechaReporteInvalidez(Long fechaReporteInvalidez) {
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
     * @return the fechaAfiliacion
     */
    public Long getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    /**
     * @param fechaAfiliacion
     *        the fechaAfiliacion to set
     */
    public void setFechaAfiliacion(Long fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    /**
     * @return the salarioMensualBeneficiario
     */
    public BigDecimal getSalarioMensualBeneficiario() {
        return salarioMensualBeneficiario;
    }

    /**
     * @param salarioMensualBeneficiario
     *        the salarioMensualBeneficiario to set
     */
    public void setSalarioMensualBeneficiario(BigDecimal salarioMensualBeneficiario) {
        this.salarioMensualBeneficiario = salarioMensualBeneficiario;
    }

    /**
     * @return the gradoAcademicoBeneficiario
     */
    public Short getGradoAcademicoBeneficiario() {
        return gradoAcademicoBeneficiario;
    }

    /**
     * @param gradoAcademicoBeneficiario
     *        the gradoAcademicoBeneficiario to set
     */
    public void setGradoAcademicoBeneficiario(Short gradoAcademicoBeneficiario) {
        this.gradoAcademicoBeneficiario = gradoAcademicoBeneficiario;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion
     *        the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the fechaRetiro
     */
    public Long getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(Long fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the fechaInicioSociedadConyugal
     */
    public Long getFechaInicioSociedadConyugal() {
        return fechaInicioSociedadConyugal;
    }

    /**
     * @param fechaInicioSociedadConyugal
     *        the fechaInicioSociedadConyugal to set
     */
    public void setFechaInicioSociedadConyugal(Long fechaInicioSociedadConyugal) {
        this.fechaInicioSociedadConyugal = fechaInicioSociedadConyugal;
    }

    /**
     * @return the fechaFinSociedadConyugal
     */
    public Long getFechaFinSociedadConyugal() {
        return fechaFinSociedadConyugal;
    }

    /**
     * @param fechaFinSociedadConyugal
     *        the fechaFinSociedadConyugal to set
     */
    public void setFechaFinSociedadConyugal(Long fechaFinSociedadConyugal) {
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
     * @param valor
     *        para modificar idRolAfiliado.
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the afiliado
     */
    public AfiliadoModeloDTO getAfiliado() {
        return afiliado;
    }

    /**
     * @param afiliado the afiliado to set
     */
    public void setAfiliado(AfiliadoModeloDTO afiliado) {
        this.afiliado = afiliado;
    }

    /**
     * @return the actualizarCertificado
     */
    public Boolean getActualizarCertificado() {
        return actualizarCertificado;
    }

    /**
     * @param actualizarCertificado the actualizarCertificado to set
     */
    public void setActualizarCertificado(Boolean actualizarCertificado) {
        this.actualizarCertificado = actualizarCertificado;
    }

	/**
	 * @return the fechaInicioInvalidez
	 */
	public Long getFechaInicioInvalidez() {
		return fechaInicioInvalidez;
	}

	/**
	 * @param fechaInicioInvalidez the fechaInicioInvalidez to set
	 */
	public void setFechaInicioInvalidez(Long fechaInicioInvalidez) {
		this.fechaInicioInvalidez = fechaInicioInvalidez;
	}
    public Boolean getConyugeCuidador() {
        return this.conyugeCuidador;
    }

    public void setConyugeCuidador(Boolean conyugeCuidador) {
        this.conyugeCuidador = conyugeCuidador;
    }

    public Long getFechaInicioConyugeCuidador() {
        return this.fechaInicioConyugeCuidador;
    }

    public void setFechaInicioConyugeCuidador(Long fechaInicioConyugeCuidador) {
        this.fechaInicioConyugeCuidador = fechaInicioConyugeCuidador;
    }

    public Long getFechaFinConyugeCuidador() {
        return this.fechaFinConyugeCuidador;
    }

    public void setFechaFinConyugeCuidador(Long fechaFinConyugeCuidador) {
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
        return "{" +
            " idBeneficiario='" + getIdBeneficiario() + "'" +
            ", idAfiliado='" + getIdAfiliado() + "'" +
            ", idGrupoFamiliar='" + getIdGrupoFamiliar() + "'" +
            ", tipoBeneficiario='" + getTipoBeneficiario() + "'" +
            ", estadoBeneficiarioCaja='" + getEstadoBeneficiarioCaja() + "'" +
            ", estadoBeneficiarioAfiliado='" + getEstadoBeneficiarioAfiliado() + "'" +
            ", idBeneficiarioDetalle='" + getIdBeneficiarioDetalle() + "'" +
            ", fechaVencimientoCertificadoEscolar='" + getFechaVencimientoCertificadoEscolar() + "'" +
            ", fechaRecepcionCertificadoEscolar='" + getFechaRecepcionCertificadoEscolar() + "'" +
            ", fechaReporteInvalidez='" + getFechaReporteInvalidez() + "'" +
            ", fechaInicioInvalidez='" + getFechaInicioInvalidez() + "'" +
            ", comentariosInvalidez='" + getComentariosInvalidez() + "'" +
            ", fechaAfiliacion='" + getFechaAfiliacion() + "'" +
            ", salarioMensualBeneficiario='" + getSalarioMensualBeneficiario() + "'" +
            ", gradoAcademicoBeneficiario='" + getGradoAcademicoBeneficiario() + "'" +
            ", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", fechaInicioSociedadConyugal='" + getFechaInicioSociedadConyugal() + "'" +
            ", fechaFinSociedadConyugal='" + getFechaFinSociedadConyugal() + "'" +
            ", idRolAfiliado='" + getIdRolAfiliado() + "'" +
            ", afiliado='" + getAfiliado() + "'" +
            "}";
    }
    
    
}
