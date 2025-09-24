package com.asopagos.afiliaciones.personas.composite.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.BeneficiarioDTO;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 *
 * @author sbrinez
 */
public class RadicarSolicitudAbreviadaDTO implements Serializable {

    private Long idSolicitudGlobal;

    private CanalRecepcionEnum canal;

    private FormatoEntregaDocumentoEnum metodoEnvio;

    private TipoAfiliadoEnum tipoSolicitante;

    private List<BeneficiarioDTO> beneficiarios;

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
     * @param canal the canal to set
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

}
