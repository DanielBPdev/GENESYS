package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.BeneficiarioDetalle;
import com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario;
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
public class CertificadoEscolarBeneficiarioDTO implements Serializable {

    private Long idCertificadoEscolarBeneficiario;

    private Long idBeneficiarioDetalle;

    private Date fechaRecepcionCertificadoEscolar;

    private Date fechaVencimientoCertificadoEscolar;
    
    private Date fechaCreacionCertificadoEscolar;


    public CertificadoEscolarBeneficiarioDTO() {
        super();
    }
    
    public CertificadoEscolarBeneficiarioDTO(CertificadoEscolarBeneficiario certificadoEscolarBeneficiario) {
        this.setIdCertificadoEscolarBeneficiario(certificadoEscolarBeneficiario.getIdCertificadoEscolarBeneficiario());
        this.setIdBeneficiarioDetalle(certificadoEscolarBeneficiario.getIdBeneficiarioDetalle());
        this.setFechaRecepcionCertificadoEscolar(certificadoEscolarBeneficiario.getFechaRecepcionCertificadoEscolar());
        this.setFechaVencimientoCertificadoEscolar(certificadoEscolarBeneficiario.getFechaVencimientoCertificadoEscolar());
        this.setFechaCreacionCertificadoEscolar(certificadoEscolarBeneficiario.getFechaCreacionCertificadoEscolar());
    }

    public static CertificadoEscolarBeneficiarioDTO convertToDTO(CertificadoEscolarBeneficiario certificadoEscolarBeneficiario) {
        CertificadoEscolarBeneficiarioDTO certificadoEscolarBeneficiarioDTO = new CertificadoEscolarBeneficiarioDTO();
        certificadoEscolarBeneficiarioDTO.setIdCertificadoEscolarBeneficiario(certificadoEscolarBeneficiario.getIdCertificadoEscolarBeneficiario());
        certificadoEscolarBeneficiarioDTO.setIdBeneficiarioDetalle(certificadoEscolarBeneficiario.getIdBeneficiarioDetalle());
        certificadoEscolarBeneficiarioDTO.setFechaRecepcionCertificadoEscolar(certificadoEscolarBeneficiario.getFechaRecepcionCertificadoEscolar());
        certificadoEscolarBeneficiarioDTO.setFechaVencimientoCertificadoEscolar(certificadoEscolarBeneficiario.getFechaVencimientoCertificadoEscolar());
        certificadoEscolarBeneficiarioDTO.setFechaCreacionCertificadoEscolar(certificadoEscolarBeneficiario.getFechaCreacionCertificadoEscolar());
        return certificadoEscolarBeneficiarioDTO;
    }

    
    /**
     * @return the idCertificadoEscolarBeneficiario
     */
    public Long getIdCertificadoEscolarBeneficiario() {
        return idCertificadoEscolarBeneficiario;
    }

    /**
     * @param idCertificadoEscolarBeneficiario the idCertificadoEscolarBeneficiario to set
     */
    public void setIdCertificadoEscolarBeneficiario(Long idCertificadoEscolarBeneficiario) {
        this.idCertificadoEscolarBeneficiario = idCertificadoEscolarBeneficiario;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * @return the fechaRecepcionCertificadoEscolar
     */
    public Date getFechaRecepcionCertificadoEscolar() {
        return fechaRecepcionCertificadoEscolar;
    }

    /**
     * @param fechaRecepcionCertificadoEscolar the fechaRecepcionCertificadoEscolar to set
     */
    public void setFechaRecepcionCertificadoEscolar(Date fechaRecepcionCertificadoEscolar) {
        this.fechaRecepcionCertificadoEscolar = fechaRecepcionCertificadoEscolar;
    }

    /**
     * @return the fechaVencimientoCertificadoEscolar
     */
    public Date getFechaVencimientoCertificadoEscolar() {
        return fechaVencimientoCertificadoEscolar;
    }

    /**
     * @param fechaVencimientoCertificadoEscolar the fechaVencimientoCertificadoEscolar to set
     */
    public void setFechaVencimientoCertificadoEscolar(Date fechaVencimientoCertificadoEscolar) {
        this.fechaVencimientoCertificadoEscolar = fechaVencimientoCertificadoEscolar;
    }

    /**
     * @return the fechaCreacionCertificadoEscolar
     */
    public Date getFechaCreacionCertificadoEscolar() {
        return fechaCreacionCertificadoEscolar;
    }

    /**
     * @param fechaCreacionCertificadoEscolar the fechaCreacionCertificadoEscolar to set
     */
    public void setFechaCreacionCertificadoEscolar(Date fechaCreacionCertificadoEscolar) {
        this.fechaCreacionCertificadoEscolar = fechaCreacionCertificadoEscolar;
    }

}
