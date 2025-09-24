package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.pila.temporal.TemAporteActualizado;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que representa a un entity TemAporteActualizado <br/>
 * <b>Módulo:</b> Asopagos - HU-211-480 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class TemAporteActualizadoModeloDTO implements Serializable {
    private static final long serialVersionUID = 2574653759380922350L;

    /**
     * Identificador de registro
     * */
    private Long id;
    
    /**
     * Estado de aporte para la actualización
     * */
    private EstadoRegistroAportesArchivoEnum estadoRegistroAporte;
    
    /**
     * ID de RegistroDetallado actualizado
     * */
    private Long idRegistroDetallado;
    
    /**
     * Marca de actualización desde aporte manual
     * */
    private Boolean marcaAporteManual;
    
    /**
     * ID de RegistroGeneral que genera la actualización
     * */
    private Long registroModificador;
    
    /**
     * @param id
     * @param estadoRegistroAporte
     * @param idRegistroDetallado
     * @param marcaAporteManual
     * @param registroModificador
     */
    public TemAporteActualizadoModeloDTO(Long id, EstadoRegistroAportesArchivoEnum estadoRegistroAporte, Long idRegistroDetallado,
            Boolean marcaAporteManual, Long registroModificador) {
        super();
        this.id = id;
        this.estadoRegistroAporte = estadoRegistroAporte;
        this.idRegistroDetallado = idRegistroDetallado;
        this.marcaAporteManual = marcaAporteManual;
        this.registroModificador = registroModificador;
    }

    /**
     * Constructor por defecto
     * */
    public TemAporteActualizadoModeloDTO(){
        super();
    }

    /**
     * Constructor con base en entity
     * */
    public TemAporteActualizadoModeloDTO(TemAporteActualizado temAporteActualizado){
        super();
        this.convertToDTO(temAporteActualizado);
    }
    
    /**
     * Conversión a DTO
     * */
    public void convertToDTO(TemAporteActualizado temAporteActualizado){
        this.id = temAporteActualizado.getId();
        this.estadoRegistroAporte = temAporteActualizado.getEstadoRegistroAporte();
        this.idRegistroDetallado = temAporteActualizado.getIdRegistroDetallado();
        this.marcaAporteManual = temAporteActualizado.getMarcaAporteManual();
        this.registroModificador = temAporteActualizado.getRegistroModificador();
    }
    
    /**
     * Conversión a Entity
     * */
    public TemAporteActualizado convertToEntity(){
        TemAporteActualizado temAporteActualizado = new TemAporteActualizado();
        
        temAporteActualizado.setId(this.getId());
        temAporteActualizado.setEstadoRegistroAporte(this.getEstadoRegistroAporte());
        temAporteActualizado.setIdRegistroDetallado(this.getIdRegistroDetallado());
        temAporteActualizado.setMarcaAporteManual(this.getMarcaAporteManual());
        temAporteActualizado.setRegistroModificador(this.getRegistroModificador());
        
        return temAporteActualizado;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the estadoRegistroAporte
     */
    public EstadoRegistroAportesArchivoEnum getEstadoRegistroAporte() {
        return estadoRegistroAporte;
    }

    /**
     * @param estadoRegistroAporte the estadoRegistroAporte to set
     */
    public void setEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum estadoRegistroAporte) {
        this.estadoRegistroAporte = estadoRegistroAporte;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the marcaAporteManual
     */
    public Boolean getMarcaAporteManual() {
        return marcaAporteManual;
    }

    /**
     * @param marcaAporteManual the marcaAporteManual to set
     */
    public void setMarcaAporteManual(Boolean marcaAporteManual) {
        this.marcaAporteManual = marcaAporteManual;
    }

    /**
     * @return the registroModificador
     */
    public Long getRegistroModificador() {
        return registroModificador;
    }

    /**
     * @param registroModificador the registroModificador to set
     */
    public void setRegistroModificador(Long registroModificador) {
        this.registroModificador = registroModificador;
    }

}
