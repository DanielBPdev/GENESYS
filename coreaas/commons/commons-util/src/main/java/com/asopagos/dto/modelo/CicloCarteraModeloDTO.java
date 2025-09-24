package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.ccf.cartera.CicloCartera;
import com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoCicloEnum;

/**
 * DTO que representa el modelo del Ciclo de Fiscalización
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class CicloCarteraModeloDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 3009900457808566326L;

    /**
     * Número identificador del Ciclo de Fiscalización
     */
    private Long idCicloFiscalizacion;

    /**
     * Representa el estado de fiscalización para el ciclo que puede ser Activo,
     * Finalizado, Cancelado
     */
    private EstadoCicloCarteraEnum estadoCicloFiscalizacion;

    /**
     * La fecha de inicio para el ciclo de fiscalización
     */
    private Long fechaInicio;

    /**
     * Fecha fin para procesar el ciclo de fiscalización
     */
    private Long fechaFin;
    /**
     * Representa la cantidad de entidades relacionadas al ciclo de fiscalización
     * Es un valor calculado, no hace parte de la entidad.
     */
    private Long cantidadEntidades;

    /**
     * Indica la fecha en la que se crea el ciclo de fiscalización
     */
    private Long fechaCreacion;

    /**
     * Representa la cantidad de entidades que ya han sido fiscalizadas.
     * Es un valor calculado, no hace parte de la entidad.
     */
    private Long cantidadFiscalizadas;

    /**
     * Aportantes del ciclo de fiscalización.
     */
    private List<CicloAportanteModeloDTO> aportantes;
     
    /**
     * Tipo ciclo cartera 
     */
    private TipoCicloEnum tipoCiclo;
    
    /**
     * Método constructor vacío.
     */
    public CicloCarteraModeloDTO(){
        
    }
    /**
     * Método constructor del ciclo cartera.
     * @param cicloCartera ciclo cartera.
     * @param cantidadEntidades cantidad de entidades.
     */
    public CicloCarteraModeloDTO(CicloCartera cicloCartera,Long cantidadEntidades){
        this.setIdCicloFiscalizacion(cicloCartera.getIdCicloCartera());
        this.setCantidadEntidades(cantidadEntidades);
        this.setFechaFin(cicloCartera.getFechaFin().getTime());
        this.setFechaInicio(cicloCartera.getFechaInicio().getTime());
    }
    
    /**
     * Método constructor del ciclo cartera.
     * @param cicloCartera ciclo cartera.
     */
    public CicloCarteraModeloDTO(CicloCartera cicloCartera){
        this.setIdCicloFiscalizacion(cicloCartera.getIdCicloCartera());
        this.setFechaFin(cicloCartera.getFechaFin() != null ? cicloCartera.getFechaFin().getTime() : null);
        this.setFechaInicio(cicloCartera.getFechaInicio() != null ? cicloCartera.getFechaInicio().getTime() : null);
        this.setEstadoCicloFiscalizacion(cicloCartera.getEstadoCicloCartera());
        this.setFechaCreacion(cicloCartera.getFechaCreacion() != null ? cicloCartera.getFechaCreacion().getTime() : null);
        this.setTipoCiclo(cicloCartera.getTipoCiclo());
        
    }

    /**
     * Método encargado de convertir de DTO a entidad
     * @return CicloFiscalizacion
     */
    public CicloCartera convertToEntity() {
        CicloCartera cicloFiscalizacion = new CicloCartera();
        cicloFiscalizacion.setIdCicloCartera(this.getIdCicloFiscalizacion());
        cicloFiscalizacion.setEstadoCicloCartera(this.getEstadoCicloFiscalizacion());
        cicloFiscalizacion.setFechaInicio(this.getFechaInicio() != null ? new Date(this.getFechaInicio()) : null);
        cicloFiscalizacion.setFechaFin(this.getFechaFin() != null ? new Date(this.getFechaFin()) : null);
        cicloFiscalizacion.setFechaCreacion(this.getFechaCreacion() != null ? new Date(this.getFechaCreacion()) : null);
        cicloFiscalizacion.setTipoCiclo(this.tipoCiclo);
        return cicloFiscalizacion;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param CicloCartera
     *        entidad a convertir.
     */
    public void convertToDTO(CicloCartera cicloFiscalizacion) {
        this.setIdCicloFiscalizacion(cicloFiscalizacion.getIdCicloCartera());
        this.setEstadoCicloFiscalizacion(cicloFiscalizacion.getEstadoCicloCartera());
        this.setFechaInicio(cicloFiscalizacion.getFechaInicio() != null ? cicloFiscalizacion.getFechaInicio().getTime() : null);
        this.setFechaFin(cicloFiscalizacion.getFechaFin() != null ? cicloFiscalizacion.getFechaFin().getTime() : null);
        this.setFechaCreacion(cicloFiscalizacion.getFechaCreacion() != null ? cicloFiscalizacion.getFechaCreacion().getTime() : null);
        this.setTipoCiclo(cicloFiscalizacion.getTipoCiclo()); 
    }

    /**
     * @return the idCicloFiscalizacion
     */
    public Long getIdCicloFiscalizacion() {
        return idCicloFiscalizacion;
    }

    /**
     * @param idCicloFiscalizacion
     *        the idCicloFiscalizacion to set
     */
    public void setIdCicloFiscalizacion(Long idCicloFiscalizacion) {
        this.idCicloFiscalizacion = idCicloFiscalizacion;
    }

    /**
     * @return the estadoCicloFiscalizacion
     */
    public EstadoCicloCarteraEnum getEstadoCicloFiscalizacion() {
        return estadoCicloFiscalizacion;
    }

    /**
     * @param estadoCicloFiscalizacion
     *        the estadoCicloFiscalizacion to set
     */
    public void setEstadoCicloFiscalizacion(EstadoCicloCarteraEnum estadoCicloFiscalizacion) {
        this.estadoCicloFiscalizacion = estadoCicloFiscalizacion;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the cantidadEntidades
     */
    public Long getCantidadEntidades() {
        return cantidadEntidades;
    }

    /**
     * @param cantidadEntidades
     *        the cantidadEntidades to set
     */
    public void setCantidadEntidades(Long cantidadEntidades) {
        this.cantidadEntidades = cantidadEntidades;
    }

    /**
     * @return the fechaCreacion
     */
    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion
     *        the fechaCreacion to set
     */
    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Método que retorna el valor de cantidadFiscalizadas.
     * @return valor de cantidadFiscalizadas.
     */
    public Long getCantidadFiscalizadas() {
        return cantidadFiscalizadas;
    }

    /**
     * Método encargado de modificar el valor de cantidadFiscalizadas.
     * @param valor
     *        para modificar cantidadFiscalizadas.
     */
    public void setCantidadFiscalizadas(Long cantidadFiscalizadas) {
        this.cantidadFiscalizadas = cantidadFiscalizadas;
    }

    /**
     * Método que retorna el valor de aportantes.
     * @return valor de aportantes.
     */
    public List<CicloAportanteModeloDTO> getAportantes() {
        return aportantes;
    }

    /**
     * Método encargado de modificar el valor de aportantes.
     * @param valor
     *        para modificar aportantes.
     */
    public void setAportantes(List<CicloAportanteModeloDTO> aportantes) {
        this.aportantes = aportantes;
    }

    /**
     * Método que retorna el valor de tipoCiclo.
     * @return valor de tipoCiclo.
     */
    public TipoCicloEnum getTipoCiclo() {
        return tipoCiclo;
    }

    /**
     * Método encargado de modificar el valor de tipoCiclo.
     * @param valor
     *        para modificar tipoCiclo.
     */
    public void setTipoCiclo(TipoCicloEnum tipoCiclo) {
        this.tipoCiclo = tipoCiclo;
    }

}
