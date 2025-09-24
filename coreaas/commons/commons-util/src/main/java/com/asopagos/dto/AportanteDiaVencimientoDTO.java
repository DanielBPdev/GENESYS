package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

/**
 * <b>Descripcion:</b> DTO empelado para la definición y consulta de aportantes con su tipo de afiliación número de identificación,
 * su ID de registro en BD y día hábil de vencimiento de aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-223-169 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class AportanteDiaVencimientoDTO implements Serializable {
    private static final long serialVersionUID = -3413493276135059523L;

    /** Tipo de solicitante del aporte */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitanteAporte;

    /** Número de identificación del aportante */
    private String numeroIdentificacion;

    /** ID de registro en base de datos */
    private Long idRegistro;

    /** Día hábil de vencimiento de aportes */
    private Short diaHabilVencimiento;
    
    /** Id de Beneficio de ley para empleadores */
    private Long idBeneficioLey;
    
    /** Constructor por defecto */
    public AportanteDiaVencimientoDTO(){}

    /** Constructor para NamedQuery Aportes.Consultar.empleador.sin.dia.vencimiento.aportes */
    public AportanteDiaVencimientoDTO(String tipoSolicitanteAporte, String numeroIdentificacion,
            Long idRegistro, Long idBeneficioLey) {
        this.tipoSolicitanteAporte = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitanteAporte);
        this.numeroIdentificacion = numeroIdentificacion;
        this.idRegistro = idRegistro;
        this.idBeneficioLey = idBeneficioLey;
    }

    /** Constructor para NamedQuery Aportes.Consultar.personas.sin.dia.vencimiento.aportes */
    public AportanteDiaVencimientoDTO(String tipoSolicitanteAporte, String numeroIdentificacion,
            Long idRegistro) {
        this.tipoSolicitanteAporte = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitanteAporte);
        this.numeroIdentificacion = numeroIdentificacion;
        this.idRegistro = idRegistro;
        this.idBeneficioLey = null;
    }

    /**
     * @return the tipoSolicitanteAporte
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitanteAporte() {
        return tipoSolicitanteAporte;
    }

    /**
     * @param tipoSolicitanteAporte
     *        the tipoSolicitanteAporte to set
     */
    public void setTipoSolicitanteAporte(TipoSolicitanteMovimientoAporteEnum tipoSolicitanteAporte) {
        this.tipoSolicitanteAporte = tipoSolicitanteAporte;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the idRegistro
     */
    public Long getIdRegistro() {
        return idRegistro;
    }

    /**
     * @param idRegistro
     *        the idRegistro to set
     */
    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * @return the diaHabilVencimiento
     */
    public Short getDiaHabilVencimiento() {
        return diaHabilVencimiento;
    }

    /**
     * @param diaHabilVencimiento
     *        the diaHabilVencimiento to set
     */
    public void setDiaHabilVencimiento(Short diaHabilVencimiento) {
        this.diaHabilVencimiento = diaHabilVencimiento;
    }

    /**
     * @return the idBeneficioLey
     */
    public Long getIdBeneficioLey() {
        return idBeneficioLey;
    }

    /**
     * @param idBeneficioLey the idBeneficioLey to set
     */
    public void setIdBeneficioLey(Long idBeneficioLey) {
        this.idBeneficioLey = idBeneficioLey;
    }
}
