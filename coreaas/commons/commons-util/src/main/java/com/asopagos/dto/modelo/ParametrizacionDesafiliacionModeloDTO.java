package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.asopagos.entidades.ccf.cartera.ParametrizacionDesafiliacion;
import com.asopagos.enumeraciones.cartera.AccionCarteraEnum;
import com.asopagos.enumeraciones.cartera.MetodoEnvioComunicadoEnum;
import com.asopagos.enumeraciones.cartera.ProgramacionEjecucionEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;

/**
 * Modelo DTO para la parametrización del proceso de desafiliación de cartera 
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class ParametrizacionDesafiliacionModeloDTO implements Serializable {

    /**
     * Identificador para la entidad Parametrización de Desafiliación
     */
    private Long idParametrizacionDesafiliacion;
    /**
     * Representa el tipo de linea de cobro LC4C o LC5C: Notificación de
     * desafiliación 
     */
    private TipoLineaCobroEnum lineaCobro;
    /**
     * Programación de la ejecución de desafiliación para el aportante
     */
    private ProgramacionEjecucionEnum programacionEjecucion;
    /**
     * Monto de mora por inexactitud al momento de la desafiliación
     */
    private BigDecimal montoMoraInexactitud;
    /**
     * Cantidad de periodos en mora al momento de la desafiliación del aportante
     */
    private Long periodosMora;
    /**
     * Método de envio comunicado fisico o electronico
     */
    private MetodoEnvioComunicadoEnum metodoEnvioComunicado;
    /**
     * Dirección de envió del comunicado para el caso donde el método de envio del
     * comunicado sea fisico
     */
    private Boolean oficinaPrincipalFisico;
    /**
     * Dirección de envió del comunicado para el caso donde el método de envio del
     * comunicado sea fisico
     */
    private Boolean correspondenciaFisico;
    /**
     * Dirección de envió del comunicado para el caso donde el método de envio del
     * comunicado sea fisico
     */
    private Boolean notificacionJudicialFisico;
    /**
     * Destinatarios del comunicado para el caso donde el método de envio del
     * comunicado sea electronico
     */
    private Boolean oficinaPrincipalElectronico;
    /**
     * Destinatarios del comunicado para el caso donde el método de envio del
     * comunicado sea electronico
     */
    private Boolean representanteLegalElectronico;
    /**
     * Destinatarios del comunicado para el caso donde el método de envio del
     * comunicado sea electronico
     */
    private Boolean responsableAportesElectronico;
    /**
     * Le siguiente accion a realizar luego se determina por: Resultado de envío de
     * comunicado o el Registro de la recepción de comunicado
     */
    private AccionCarteraEnum siguienteAccion;

    /**
     * Habilitar la notificacion de desafiliacion para la linea de cobro 
     */
    private Boolean habilitado;

    private Boolean desafiAutomatica;

    private Integer numDiasDesafiliacion;

    private ArrayList<String>  datosUbicacion;
    
    /**
     * 
     */
    public ParametrizacionDesafiliacionModeloDTO() {
    }

    /**
     * @return the idParametrizacionDesafiliacion
     */
    public Long getIdParametrizacionDesafiliacion() {
        return idParametrizacionDesafiliacion;
    }

    /**
     * @param idParametrizacionDesafiliacion
     *        the idParametrizacionDesafiliacion to set
     */
    public void setIdParametrizacionDesafiliacion(Long idParametrizacionDesafiliacion) {
        this.idParametrizacionDesafiliacion = idParametrizacionDesafiliacion;
    }

    /**
     * @return the lineaCobro
     */
    public TipoLineaCobroEnum getLineaCobro() {
        return lineaCobro;
    }

    /**
     * @param lineaCobro the lineaCobro to set
     */
    public void setLineaCobro(TipoLineaCobroEnum lineaCobro) {
        this.lineaCobro = lineaCobro;
    }

    /**
     * @return the programacionEjecucion
     */
    public ProgramacionEjecucionEnum getProgramacionEjecucion() {
        return programacionEjecucion;
    }

    /**
     * @param programacionEjecucion
     *        the programacionEjecucion to set
     */
    public void setProgramacionEjecucion(ProgramacionEjecucionEnum programacionEjecucion) {
        this.programacionEjecucion = programacionEjecucion;
    }

    /**
     * @return the montoMoraInexactitud
     */
    public BigDecimal getMontoMoraInexactitud() {
        return montoMoraInexactitud;
    }

    /**
     * @param montoMoraInexactitud
     *        the montoMoraInexactitud to set
     */
    public void setMontoMoraInexactitud(BigDecimal montoMoraInexactitud) {
        this.montoMoraInexactitud = montoMoraInexactitud;
    }

    /**
     * @return the periodosMora
     */
    public Long getPeriodosMora() {
        return periodosMora;
    }

    /**
     * @param periodosMora
     *        the periodosMora to set
     */
    public void setPeriodosMora(Long periodosMora) {
        this.periodosMora = periodosMora;
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
     * @return the siguienteAccion
     */
    public AccionCarteraEnum getSiguienteAccion() {
        return siguienteAccion;
    }

    /**
     * @param siguienteAccion
     *        the siguienteAccion to set
     */
    public void setSiguienteAccion(AccionCarteraEnum siguienteAccion) {
        this.siguienteAccion = siguienteAccion;
    }

    /**
     * @return the habilitado
     */
    public Boolean getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    public Boolean getDesafiAutomatica(){
        return desafiAutomatica;
    }

    public void setDesafiAutomatica(Boolean desafiAutomatica){
        this.desafiAutomatica = desafiAutomatica;
    }

    public Integer getNumDiasDesafiliacion(){
        return numDiasDesafiliacion;
    }

    public void setNumDiasDesafiliacion(Integer numDiasDesafiliacion){
        this.numDiasDesafiliacion = numDiasDesafiliacion;
    }
    //  devuelve la lista completa
    public ArrayList<String> getDatosUbicacion() {
        return datosUbicacion;
    }
    
    // reemplaza toda la lista con una nueva
    public void setDatosUbicacion(ArrayList<String>  datosUbicacion) {
        this.datosUbicacion = datosUbicacion;
    }
    /**
     * Método encargado de convertir una entidad a DTO
     * 
     * @param parametrizacionExclusiones
     *        parametrización de exclusiones
     */
    public void convertToDTO(ParametrizacionDesafiliacion parametrizacionDesafiliacion) {
        this.setIdParametrizacionDesafiliacion(parametrizacionDesafiliacion.getIdParametrizacionDesafiliacion());
        this.setLineaCobro(parametrizacionDesafiliacion.getLineaCobro());
        this.setProgramacionEjecucion(parametrizacionDesafiliacion.getProgramacionEjecucion());
        this.setMontoMoraInexactitud(parametrizacionDesafiliacion.getMontoMoraInexactitud());
        this.setPeriodosMora(parametrizacionDesafiliacion.getPeriodosMora());
        this.setMetodoEnvioComunicado(parametrizacionDesafiliacion.getMetodoEnvioComunicado());
        this.setOficinaPrincipalFisico(parametrizacionDesafiliacion.getOficinaPrincipalFisico());
        this.setCorrespondenciaFisico(parametrizacionDesafiliacion.getCorrespondenciaFisico());
        this.setNotificacionJudicialFisico(parametrizacionDesafiliacion.getNotificacionJudicialFisico());
        this.setOficinaPrincipalElectronico(parametrizacionDesafiliacion.getOficinaPrincipalElectronico());
        this.setRepresentanteLegalElectronico(parametrizacionDesafiliacion.getRepresentanteLegalElectronico());
        this.setResponsableAportesElectronico(parametrizacionDesafiliacion.getResponsableAportesElectronico());
        this.setSiguienteAccion(parametrizacionDesafiliacion.getSiguienteAccion());
        this.setHabilitado(parametrizacionDesafiliacion.getHabilitado());
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     * @return parametrizacion de exclusiones.
     */
    public ParametrizacionDesafiliacion convertToEntity() {
        ParametrizacionDesafiliacion parametrizacionDesafiliacion = new ParametrizacionDesafiliacion();
        parametrizacionDesafiliacion.setIdParametrizacionDesafiliacion(this.getIdParametrizacionDesafiliacion());
        parametrizacionDesafiliacion.setLineaCobro(this.getLineaCobro());
        parametrizacionDesafiliacion.setProgramacionEjecucion(this.getProgramacionEjecucion());
        parametrizacionDesafiliacion.setMontoMoraInexactitud(this.getMontoMoraInexactitud());
        parametrizacionDesafiliacion.setPeriodosMora(this.getPeriodosMora());
        parametrizacionDesafiliacion.setMetodoEnvioComunicado(this.getMetodoEnvioComunicado());
        parametrizacionDesafiliacion.setOficinaPrincipalFisico(this.getOficinaPrincipalFisico());
        parametrizacionDesafiliacion.setCorrespondenciaFisico(this.getCorrespondenciaFisico());
        parametrizacionDesafiliacion.setNotificacionJudicialFisico(this.getNotificacionJudicialFisico());
        parametrizacionDesafiliacion.setOficinaPrincipalElectronico(this.getOficinaPrincipalElectronico());
        parametrizacionDesafiliacion.setRepresentanteLegalElectronico(this.getRepresentanteLegalElectronico());
        parametrizacionDesafiliacion.setResponsableAportesElectronico(this.getResponsableAportesElectronico());
        parametrizacionDesafiliacion.setSiguienteAccion(this.getSiguienteAccion());
        parametrizacionDesafiliacion.setHabilitado(this.getHabilitado());
        parametrizacionDesafiliacion.setDesafiAutomatica(this.getDesafiAutomatica());
        parametrizacionDesafiliacion.setNumDiasDesafiliacion(this.getNumDiasDesafiliacion());
        return parametrizacionDesafiliacion;
    }
}
