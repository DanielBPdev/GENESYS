package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.CargueBloqueoCuotaMonetaria;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;
import com.asopagos.enumeraciones.subsidiomonetario.bloqueos.CausalBloqueoCuotaMonetariaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoNivelBloqueoEnum;


/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class BloqueoBeneficiarioCuotaMonetariaDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * id BloqueoBeneficiarioCuotaMonetaria en core
     */
    private Long idBloqueoBeneficiarioCuotaMonetaria;

    /**
     * id del beneficiario en la tabla Persona
     */
	private Long personaBeneficiario;
		
	/**
	* tipo identificacion beneficiario
	*/	 
	private TipoIdentificacionEnum tipoIdentificacionBeneciario;
	    
    /**
     * numero identificacion beneficiario 
     */
   
    private String numeroIdentificacionBeneficiario;
    
    /**
     * Indicador S/N si el bloquedo quedo radicado ó false si quedo cancelado
     */
   
    private Boolean asignacionCuotaPorOtraCCF;
    
    /**
     * Indicador S/N si el bloquedo quedo radicado ó false si quedo cancelado
     */
  
    private Boolean beneficiarioComoAfiliadoOtraCCF;

    /**
     *  Causal de bloqueo del beneficiario
     */
   
    private CausalBloqueoCuotaMonetariaEnum causalBloqueoCuotaMonetaria;

    
    /**
     *  Relacion beneficiario frente a trabajador
     */

    private long relacionBeneficiario;
    
    /**
     * id cargue de archivo bloqueo beneficiario cuota monetaria
     */
	private Long cargueBloqueoCuotaMonetaria;
	
	/**
	 * nombre beneficiario
	 */
	private String razonSocialBeneficiario;
	
	/**
	 * Estado del beneficiario
	 */
	private String estadoBeneficiario;
	
	/**
	 * Periodo inicio bloqueo
	 */
	private Date periodoInicio;
	
	/**
	 * Periodo fin bloqueo
	 */
	private Date periodoFin;
	
	/**
	 * id bloqueo para afiliado beneficiario en subsidio
	 */
	private Long idBloqueoAfiliadoBeneficiarioCM;
	
	/**
	 * Motivo de bloqueo no acreditado cargado manualmente por BD
	 */
	private Boolean bloqueoParMotivoNoAcreditado;
	
	/**
     * Motivo de bloqueo fraude cargado manualmente por BD
     */
	private Boolean bloqueoParMotivoFraude;
	
	/**
     * Motivo de bloqueo otro cargado manualmente por BD
     */
	private Boolean bloqueoParMotivoOtro;
	
	/**
	 * Tipo identificacion afiliado cuando es bloqueo par afiliado - beneficiario
	 */
	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    
	/**
     * Numero identificacion afiliado cuando es bloqueo par afiliado - beneficiario
     */
    private String numeroIdentificacionAfiliado;
    
    /**
     * Id persona afiliado en la tabla Persona
     */
    private Long PersonaAfiliado;
    
    /**
     * Relacion afiliado principal - beneficiario cuando es bloqueo par afiliado principal - beneficiario
     */
    private String relacionAfiBen;

    /**
     * Relacion Afiliado - Beneficiario cuando el bloqueo es por beneficiario - trabajador
     */
    private String idBen;

    /**
     * Nivel de bloqueo (Beneficiario o Beneficiario-trabajador)
     */
    private TipoNivelBloqueoEnum nivelBloqueo;

    public TipoNivelBloqueoEnum getNivelBloqueo() {
        return this.nivelBloqueo;
    }

    public void setNivelBloqueo(TipoNivelBloqueoEnum nivelBloqueo) {
        this.nivelBloqueo = nivelBloqueo;
    }

    public String getIdBen() {
        return this.idBen;
    }

    public void setIdBen(String idBen) {
        this.idBen = idBen;
    }

	/**
     * @return the relacionAfiBen
     */
    public String getRelacionAfiBen() {
        return relacionAfiBen;
    }


    /**
     * @param relacionAfiBen the relacionAfiBen to set
     */
    public void setRelacionAfiBen(String relacionAfiBen) {
        this.relacionAfiBen = relacionAfiBen;
    }


    /**
     * @return the personaBeneficiario
     */
    public Long getPersonaBeneficiario() {
        return personaBeneficiario;
    }


    /**
     * @param personaBeneficiario the personaBeneficiario to set
     */
    public void setPersonaBeneficiario(Long personaBeneficiario) {
        this.personaBeneficiario = personaBeneficiario;
    }


    /**
     * @return the razonSocialBeneficiario
     */
    public String getRazonSocialBeneficiario() {
        return razonSocialBeneficiario;
    }


    /**
     * @param razonSocialBeneficiario the razonSocialBeneficiario to set
     */
    public void setRazonSocialBeneficiario(String razonSocialBeneficiario) {
        this.razonSocialBeneficiario = razonSocialBeneficiario;
    }


    public long getRelacionBeneficiario() {
        return this.relacionBeneficiario;
    }

    public void setRelacionBeneficiario(long relacionBeneficiario) {
        this.relacionBeneficiario = relacionBeneficiario;
    }

    /**
     * @return the estadoBeneficiario
     */
    public String getEstadoBeneficiario() {
        return estadoBeneficiario;
    }


    /**
     * @param estadoBeneficiario the estadoBeneficiario to set
     */
    public void setEstadoBeneficiario(String estadoBeneficiario) {
        this.estadoBeneficiario = estadoBeneficiario;
    }


    /**
     * @return the idBloqueoAfiliadoBeneficiarioCM
     */
    public Long getIdBloqueoAfiliadoBeneficiarioCM() {
        return idBloqueoAfiliadoBeneficiarioCM;
    }


    /**
     * @param idBloqueoAfiliadoBeneficiarioCM the idBloqueoAfiliadoBeneficiarioCM to set
     */
    public void setIdBloqueoAfiliadoBeneficiarioCM(Long idBloqueoAfiliadoBeneficiarioCM) {
        this.idBloqueoAfiliadoBeneficiarioCM = idBloqueoAfiliadoBeneficiarioCM;
    }


    /**
     * @return the bloqueoParMotivoNoAcreditado
     */
    public Boolean getBloqueoParMotivoNoAcreditado() {
        return bloqueoParMotivoNoAcreditado;
    }


    /**
     * @param bloqueoParMotivoNoAcreditado the bloqueoParMotivoNoAcreditado to set
     */
    public void setBloqueoParMotivoNoAcreditado(Boolean bloqueoParMotivoNoAcreditado) {
        this.bloqueoParMotivoNoAcreditado = bloqueoParMotivoNoAcreditado;
    }


    /**
     * @return the bloqueoParMotivoFraude
     */
    public Boolean getBloqueoParMotivoFraude() {
        return bloqueoParMotivoFraude;
    }


    /**
     * @param bloqueoParMotivoFraude the bloqueoParMotivoFraude to set
     */
    public void setBloqueoParMotivoFraude(Boolean bloqueoParMotivoFraude) {
        this.bloqueoParMotivoFraude = bloqueoParMotivoFraude;
    }


    /**
     * @return the bloqueoParMotivoOtro
     */
    public Boolean getBloqueoParMotivoOtro() {
        return bloqueoParMotivoOtro;
    }


    /**
     * @param bloqueoParMotivoOtro the bloqueoParMotivoOtro to set
     */
    public void setBloqueoParMotivoOtro(Boolean bloqueoParMotivoOtro) {
        this.bloqueoParMotivoOtro = bloqueoParMotivoOtro;
    }


    /**
     * @return the tipoIdentificacionAfiliado
     */
    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return tipoIdentificacionAfiliado;
    }


    /**
     * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
     */
    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }


    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }


    /**
     * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }


    /**
     * @return the personaAfiliado
     */
    public Long getPersonaAfiliado() {
        return PersonaAfiliado;
    }


    /**
     * @param personaAfiliado the personaAfiliado to set
     */
    public void setPersonaAfiliado(Long personaAfiliado) {
        PersonaAfiliado = personaAfiliado;
    }


    public Long getIdBloqueoBeneficiarioCuotaMonetaria() {
		return idBloqueoBeneficiarioCuotaMonetaria;
	}


	public void setIdBloqueoBeneficiarioCuotaMonetaria(Long idBloqueoBeneficiarioCuotaMonetaria) {
		this.idBloqueoBeneficiarioCuotaMonetaria = idBloqueoBeneficiarioCuotaMonetaria;
	}


	public TipoIdentificacionEnum getTipoIdentificacionBeneciario() {
		return tipoIdentificacionBeneciario;
	}


	public void setTipoIdentificacionBeneciario(TipoIdentificacionEnum tipoIdentificacionBeneciario) {
		this.tipoIdentificacionBeneciario = tipoIdentificacionBeneciario;
	}


	public String getNumeroIdentificacionBeneficiario() {
		return numeroIdentificacionBeneficiario;
	}


	public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
		this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
	}


	public Boolean getAsignacionCuotaPorOtraCCF() {
		return asignacionCuotaPorOtraCCF;
	}


	public void setAsignacionCuotaPorOtraCCF(Boolean asignacionCuotaPorOtraCCF) {
		this.asignacionCuotaPorOtraCCF = asignacionCuotaPorOtraCCF;
	}


	public Boolean getBeneficiarioComoAfiliadoOtraCCF() {
		return beneficiarioComoAfiliadoOtraCCF;
	}


	public void setBeneficiarioComoAfiliadoOtraCCF(Boolean beneficiarioComoAfiliadoOtraCCF) {
		this.beneficiarioComoAfiliadoOtraCCF = beneficiarioComoAfiliadoOtraCCF;
	}


    public CausalBloqueoCuotaMonetariaEnum getCausalBloqueoCuotaMonetaria() {
        return causalBloqueoCuotaMonetaria;
    }

    public void setCausalBloqueoCuotaMonetaria(CausalBloqueoCuotaMonetariaEnum causalBloqueoCuotaMonetaria) {
        this.causalBloqueoCuotaMonetaria = causalBloqueoCuotaMonetaria;
    }

	public Long getCargueBloqueoCuotaMonetaria() {
		return cargueBloqueoCuotaMonetaria;
	}


	public void setCargueBloqueoCuotaMonetaria(Long cargueBloqueoCuotaMonetaria) {
		this.cargueBloqueoCuotaMonetaria = cargueBloqueoCuotaMonetaria;
	}


	public Date getPeriodoInicio() {
		return periodoInicio;
	}


	public void setPeriodoInicio(Date periodoInicio) {
		this.periodoInicio = periodoInicio;
	}


	public Date getPeriodoFin() {
		return periodoFin;
	}


	public void setPeriodoFin(Date periodoFin) {
		this.periodoFin = periodoFin;
	}
    
	
	
}
