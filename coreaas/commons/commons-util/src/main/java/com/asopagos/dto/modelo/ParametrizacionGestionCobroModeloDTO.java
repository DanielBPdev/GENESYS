package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.ParametrizacionGestionCobro;
import com.asopagos.enumeraciones.cartera.MetodoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;

/**
 * Representa la parametrización para el proceso 2.2.3 Gestión de cobro
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:07 p. m.
 */
public class ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Identificador de la entidad parametrización de gestión de cobro
     */
    private Long idParametrizacionGestionCobro;
    /**
     * Dirección de envío del comunicado si el método de envío es fisico
     */
    private Boolean oficinaPrincipalFisico;
    /**
     * Dirección de envío del comunicado si el método de envío es fisico
     */
    private Boolean correspondenciaFisico;
    /**
     * Dirección de envío del comunicado si el método de envío es fisico
     */
    private Boolean notificacionJudicialFisico;
    /**
     * Dirección de envío del comunicado si el método de envío es electronico
     */
    private Boolean oficinaPrincipalElectronico;
    /**
     * Dirección de envío del comunicado si el método de envío es electronico
     */
    private Boolean representanteLegalElectronico;
    /**
     * Dirección de envío del comunicado si el método de envío es Electronico
     */
    private Boolean responsableAportesElectronico;
    /**
     * Método del envio del comunicado este puede ser Fisico o Electronico
     */
    private MetodoEnvioComunicadoEnum metodoEnvioComunicado;
    /**
     * Tipo de parametrización para la gestión de cobro
     */
    private TipoParametrizacionGestionCobroEnum tipoParametrizacion;

    /**
     * @return the idParametrizacionGestionCobro
     */
    public Long getIdParametrizacionGestionCobro() {
        return idParametrizacionGestionCobro;
    }

    /**
     * @param idParametrizacionGestionCobro
     *        the idParametrizacionGestionCobro to set
     */
    public void setIdParametrizacionGestionCobro(Long idParametrizacionGestionCobro) {
        this.idParametrizacionGestionCobro = idParametrizacionGestionCobro;
    }

    /**
     * @return the oficinaPrincipalFisico
     */
    public Boolean getOficinaPrincipalFisico() {
        return oficinaPrincipalFisico;
    }

    /**
     * @param oficinaPrincipalFisico
     *        the oficinaPrincipalFisico to set
     */
    public void setOficinaPrincipalFisico(Boolean oficinaPrincipalFisico) {
        this.oficinaPrincipalFisico = oficinaPrincipalFisico;
    }

    /**
     * @return the correspondenciaFisico
     */
    public Boolean getCorrespondenciaFisico() {
        return correspondenciaFisico;
    }

    /**
     * @param correspondenciaFisico
     *        the correspondenciaFisico to set
     */
    public void setCorrespondenciaFisico(Boolean correspondenciaFisico) {
        this.correspondenciaFisico = correspondenciaFisico;
    }

    /**
     * @return the notificacionJudicialFisico
     */
    public Boolean getNotificacionJudicialFisico() {
        return notificacionJudicialFisico;
    }

    /**
     * @param notificacionJudicialFisico
     *        the notificacionJudicialFisico to set
     */
    public void setNotificacionJudicialFisico(Boolean notificacionJudicialFisico) {
        this.notificacionJudicialFisico = notificacionJudicialFisico;
    }

    /**
     * @return the oficinaPrincipalElectronico
     */
    public Boolean getOficinaPrincipalElectronico() {
        return oficinaPrincipalElectronico;
    }

    /**
     * @param oficinaPrincipalElectronico
     *        the oficinaPrincipalElectronico to set
     */
    public void setOficinaPrincipalElectronico(Boolean oficinaPrincipalElectronico) {
        this.oficinaPrincipalElectronico = oficinaPrincipalElectronico;
    }

    /**
     * @return the representanteLegalElectronico
     */
    public Boolean getRepresentanteLegalElectronico() {
        return representanteLegalElectronico;
    }

    /**
     * @param representanteLegalElectronico
     *        the representanteLegalElectronico to set
     */
    public void setRepresentanteLegalElectronico(Boolean representanteLegalElectronico) {
        this.representanteLegalElectronico = representanteLegalElectronico;
    }

    /**
     * @return the responsableAportesElectronico
     */
    public Boolean getResponsableAportesElectronico() {
        return responsableAportesElectronico;
    }

    /**
     * @param responsableAportesElectronico
     *        the responsableAportesElectronico to set
     */
    public void setResponsableAportesElectronico(Boolean responsableAportesElectronico) {
        this.responsableAportesElectronico = responsableAportesElectronico;
    }

    /**
     * @return the metodoEnvioComunicado
     */
    public MetodoEnvioComunicadoEnum getMetodoEnvioComunicado() {
        return metodoEnvioComunicado;
    }

    /**
     * @param metodoEnvioComunicado
     *        the metodoEnvioComunicado to set
     */
    public void setMetodoEnvioComunicado(MetodoEnvioComunicadoEnum metodoEnvioComunicado) {
        this.metodoEnvioComunicado = metodoEnvioComunicado;
    }

    /**
     * @return the tipoParametrizacion
     */
    public TipoParametrizacionGestionCobroEnum getTipoParametrizacion() {
        return tipoParametrizacion;
    }

    /**
     * @param tipoParametrizacion
     *        the tipoParametrizacion to set
     */
    public void setTipoParametrizacion(TipoParametrizacionGestionCobroEnum tipoParametrizacion) {
        this.tipoParametrizacion = tipoParametrizacion;
    }

    /**
     * Método encargado de convertir de Entidad a DTO
     * @param parametrizacionGestionCobro
     *        entidad a convertir.
     * @return DTO convertido
     */
    public ParametrizacionGestionCobroModeloDTO convertToDTO(ParametrizacionGestionCobro parametrizacionGestionCobro) {
        this.setIdParametrizacionGestionCobro(parametrizacionGestionCobro.getIdParametrizacionGestionCobro());
        this.setCorrespondenciaFisico(parametrizacionGestionCobro.getCorrespondenciaFisico());
        this.setOficinaPrincipalFisico(parametrizacionGestionCobro.getOficinaPrincipalFisico());
        this.setNotificacionJudicialFisico(parametrizacionGestionCobro.getNotificacionJudicialFisico());
        this.setOficinaPrincipalElectronico(parametrizacionGestionCobro.getOficinaPrincipalElectronico());
        this.setRepresentanteLegalElectronico(parametrizacionGestionCobro.getRepresentanteLegalElectronico());
        this.setResponsableAportesElectronico(parametrizacionGestionCobro.getResponsableAportesElectronico());
        this.setMetodoEnvioComunicado(parametrizacionGestionCobro.getMetodoEnvioComunicado());
        this.setTipoParametrizacion(parametrizacionGestionCobro.getTipoParametrizacion());
        return this;
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public ParametrizacionGestionCobro convertToEntity(ParametrizacionGestionCobro parametrizacionGestionCobro) {
        parametrizacionGestionCobro.setIdParametrizacionGestionCobro(this.getIdParametrizacionGestionCobro());
        parametrizacionGestionCobro.setCorrespondenciaFisico(this.getCorrespondenciaFisico());
        parametrizacionGestionCobro.setOficinaPrincipalFisico(this.getOficinaPrincipalFisico());
        parametrizacionGestionCobro.setNotificacionJudicialFisico(this.getNotificacionJudicialFisico());
        parametrizacionGestionCobro.setOficinaPrincipalElectronico(this.getOficinaPrincipalElectronico());
        parametrizacionGestionCobro.setRepresentanteLegalElectronico(this.getRepresentanteLegalElectronico());
        parametrizacionGestionCobro.setResponsableAportesElectronico(this.getResponsableAportesElectronico());
        parametrizacionGestionCobro.setMetodoEnvioComunicado(this.getMetodoEnvioComunicado());
        parametrizacionGestionCobro.setTipoParametrizacion(this.getTipoParametrizacion());
        return parametrizacionGestionCobro;
    }
}