package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralValidacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;

/**
 *
 * @author sbrinez
 */
public class AfiliarBeneficiarioDTO implements Serializable {

    @NotNull
    private Long idSolicitudGlobal;

    private String numeroIdentificacionAfiliado;

    private TipoAfiliadoEnum tipoAfiliado;

    private BeneficiarioHijoPadreDTO beneficiarioHijoPadre;

    private IdentificacionUbicacionPersonaDTO beneficiarioConyuge;

    @NotNull
    private TipoBeneficiarioEnum tipoBeneficiario;

    @NotNull
    private String bloqueValidacion;

    private Object datosValidacion;

    private ResultadoGeneralValidacionEnum resultadoGeneralValidacion;
    
    private Long IdBeneficiario;

    private Long fechaRecepcionDocumento;
    
    private List<Long> idsBeneficiariosInactivar;
    
    /**
     * @return the beneficiarioHijoPadre
     */
    public BeneficiarioHijoPadreDTO getBeneficiarioHijoPadre() {
        return beneficiarioHijoPadre;
    }

    /**
     * @param beneficiarioHijoPadre
     *        the beneficiarioHijoPadre to set
     */
    public void setBeneficiarioHijoPadre(BeneficiarioHijoPadreDTO beneficiarioHijoPadre) {
        this.beneficiarioHijoPadre = beneficiarioHijoPadre;
    }

    /**
     * @return the beneficiarioConyuge
     */
    public IdentificacionUbicacionPersonaDTO getBeneficiarioConyuge() {
        return beneficiarioConyuge;
    }

    /**
     * @param beneficiarioConyuge
     *        the beneficiarioConyuge to set
     */
    public void setBeneficiarioConyuge(IdentificacionUbicacionPersonaDTO beneficiarioConyuge) {
        this.beneficiarioConyuge = beneficiarioConyuge;
    }

    /**
     * @return the tipoBeneficiario
     */
    public TipoBeneficiarioEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario
     *        the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(TipoBeneficiarioEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the datosValidacion
     */
    public Object getDatosValidacion() {
        return datosValidacion;
    }

    /**
     * @param datosValidacion
     *        the datosValidacion to set
     */
    public void setDatosValidacion(Object datosValidacion) {
        this.datosValidacion = datosValidacion;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }

    /**
     * @param numeroIdentificacionAfiliado
     *        the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    /**
     * @return the bloqueValidacion
     */
    public String getBloqueValidacion() {
        return bloqueValidacion;
    }

    /**
     * @param bloqueValidacion
     *        the bloqueValidacion to set
     */
    public void setBloqueValidacion(String bloqueValidacion) {
        this.bloqueValidacion = bloqueValidacion;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado
     *        the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the resultadoGeneralValidacion
     */
    public ResultadoGeneralValidacionEnum getResultadoGeneralValidacion() {
        return resultadoGeneralValidacion;
    }

    /**
     * @param resultadoGeneralValidacion the resultadoGeneralValidacion to set
     */
    public void setResultadoGeneralValidacion(ResultadoGeneralValidacionEnum resultadoGeneralValidacion) {
        this.resultadoGeneralValidacion = resultadoGeneralValidacion;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return IdBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        IdBeneficiario = idBeneficiario;
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
     * @return the idsBeneficiariosInactivar
     */
    public List<Long> getIdsBeneficiariosInactivar() {
        return idsBeneficiariosInactivar;
    }

    /**
     * @param idsBeneficiariosInactivar the idsBeneficiariosInactivar to set
     */
    public void setIdsBeneficiariosInactivar(List<Long> idsBeneficiariosInactivar) {
        this.idsBeneficiariosInactivar = idsBeneficiariosInactivar;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AfiliarBeneficiarioDTO [idSolicitudGlobal=");
		builder.append(idSolicitudGlobal);
		builder.append(", numeroIdentificacionAfiliado=");
		builder.append(numeroIdentificacionAfiliado);
		builder.append(", tipoAfiliado=");
		builder.append(tipoAfiliado);
		builder.append(", beneficiarioHijoPadre=");
		builder.append(beneficiarioHijoPadre);
		builder.append(", beneficiarioConyuge=");
		builder.append(beneficiarioConyuge);
		builder.append(", tipoBeneficiario=");
		builder.append(tipoBeneficiario);
		builder.append(", bloqueValidacion=");
		builder.append(bloqueValidacion);
		builder.append(", datosValidacion=");
		builder.append(datosValidacion);
		builder.append(", resultadoGeneralValidacion=");
		builder.append(resultadoGeneralValidacion);
		builder.append(", IdBeneficiario=");
		builder.append(IdBeneficiario);
		builder.append(", fechaRecepcionDocumento=");
		builder.append(fechaRecepcionDocumento);
		builder.append("]");
		return builder.toString();
	}
}
