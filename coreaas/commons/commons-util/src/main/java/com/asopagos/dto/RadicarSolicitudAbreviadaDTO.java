package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 *
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
public class RadicarSolicitudAbreviadaDTO implements Serializable {

    private Long idSolicitudGlobal;

    private CanalRecepcionEnum canal;

    private FormatoEntregaDocumentoEnum metodoEnvio;

    private TipoRadicacionEnum tipoRadicacion;

    private BigDecimal valorSalarioMesada;

    private TipoAfiliadoEnum tipoSolicitante;

    private List<BeneficiarioDTO> beneficiarios;

    private Long idEntidadPagadora;

    private Long idRolAfiliado;

    private boolean registrarIntentoAfiliacion;

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
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal
     *        the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the metodoEnvio
     */
    public FormatoEntregaDocumentoEnum getMetodoEnvio() {
        return metodoEnvio;
    }

    /**
     * @param metodoEnvio
     *        the metodoEnvio to set
     */
    public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios
     *        the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the tipoRadicacion
     */
    public TipoRadicacionEnum getTipoRadicacion() {
        return tipoRadicacion;
    }

    /**
     * @param tipoRadicacion
     *        the tipoRadicacion to set
     */
    public void setTipoRadicacion(TipoRadicacionEnum tipoRadicacion) {
        this.tipoRadicacion = tipoRadicacion;
    }

    /**
     * @return the valorSalarioMesada
     */
    public BigDecimal getValorSalarioMesada() {
        return valorSalarioMesada;
    }

    /**
     * @param valorSalarioMesada
     *        the valorSalarioMesada to set
     */
    public void setValorSalarioMesada(BigDecimal valorSalarioMesada) {
        this.valorSalarioMesada = valorSalarioMesada;
    }

    /**
     * @return the tipoSolicitante
     */
    public TipoAfiliadoEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoAfiliadoEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the idEntidadPagadora
     */
    public Long getIdEntidadPagadora() {
        return idEntidadPagadora;
    }

    /**
     * @param idEntidadPagadora
     *        the idEntidadPagadora to set
     */
    public void setIdEntidadPagadora(Long idEntidadPagadora) {
        this.idEntidadPagadora = idEntidadPagadora;
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
     * @return the registrarIntentoAfiliacion
     */
    public boolean getRegistrarIntentoAfiliacion() {
        return registrarIntentoAfiliacion;
    }

    /**
     * @param registrarIntentoAfiliacion
     *        the registrarIntentoAfiliacion to set
     */
    public void setRegistrarIntentoAfiliacion(boolean registrarIntentoAfiliacion) {
        this.registrarIntentoAfiliacion = registrarIntentoAfiliacion;
    }

}
