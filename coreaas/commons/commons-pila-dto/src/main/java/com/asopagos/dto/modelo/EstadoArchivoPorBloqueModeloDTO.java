package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * <b>Descripcion:</b> DTO que representa un entity de tipo EstadoArchivoPorBloque<br/>
 * <b>Módulo:</b> Asopagos - HU-211 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class EstadoArchivoPorBloqueModeloDTO implements Serializable {
    private static final long serialVersionUID = 2168134467644093583L;

    /**
     * Código identificador de llave primaria de la entrada de estados por bloque para 
     * archivo de Operador de Información 
     */
    private Long id;
    
    /**
     * Referencia a la entrada de índice de planillas de Operador de Información
     */
    private Long indicePlanilla;

    /**
     * Descripción del tipo de archivo referenciado
     */
    private TipoArchivoPilaEnum tipoArchivo;

    /**
     * Estado del proceso del archivo para el bloque 0 de validación - 
     * Validación de Carpeta, tamaño y combinatoria de archivos
     */
    private EstadoProcesoArchivoEnum estadoBloque0;

    /**
     * Acción a ejecutar al terminar el bloque 0 de validación
     */
    private AccionProcesoArchivoEnum accionBloque0;

    /**
     * Estado del proceso del archivo para el bloque 1 de validación - 
     * Validación de estructura del nombre del archivo
     */
    private EstadoProcesoArchivoEnum estadoBloque1;

    /**
     * Acción a ejecutar al terminar el bloque 1 de validación
     */
    private AccionProcesoArchivoEnum accionBloque1;

    /**
     * Estado del proceso del archivo para el bloque 2 de validación - 
     * Validación de estructura del contenido del archivo y reglas de normatividad
     */
    private EstadoProcesoArchivoEnum estadoBloque2;

    /**
     * Acción a ejecutar al terminar el bloque 2 de validación
     */
    private AccionProcesoArchivoEnum accionBloque2;

    /**
     * Estado del proceso del archivo para el bloque 3 de validación - 
     * Validación combinada del contenido de pareja de archivos
     */
    private EstadoProcesoArchivoEnum estadoBloque3;

    /**
     * Acción a ejecutar al terminar el bloque 3 de validación
     */
    private AccionProcesoArchivoEnum accionBloque3;

    /**
     * Estado del proceso del archivo para el bloque 4 de validación - 
     * Validación individual de consistencia de archivo
     */
    private EstadoProcesoArchivoEnum estadoBloque4;

    /**
     * Acción a ejecutar al terminar el bloque 4 de validación
     */
    private AccionProcesoArchivoEnum accionBloque4;

    /**
     * Estado del proceso del archivo para el bloque 5 de validación - 
     * Validación de existencia del aportante en BD
     */
    private EstadoProcesoArchivoEnum estadoBloque5;

    /**
     * Acción a ejecutar al terminar el bloque 5 de validación
     */
    private AccionProcesoArchivoEnum accionBloque5;

    /**
     * Estado del proceso del archivo para el bloque 6 de validación - 
     * Validación de conciliación de archivo de OI con archivo de OF
     */
    private EstadoProcesoArchivoEnum estadoBloque6;

    /**
     * Acción a ejecutar al terminar el bloque 6 de validación
     */
    private AccionProcesoArchivoEnum accionBloque6;
    
    /**
     * Estado del proceso del archivo para el bloque 7 de validación - 
     * Fase 1 de PILA 2: Validación de información vs BD
     */
    private EstadoProcesoArchivoEnum estadoBloque7;
    
    /**
     * Acción a ejecutar al terminar el bloque 7 de validación
     */
    private AccionProcesoArchivoEnum accionBloque7;
    
    /**
     * Estado del proceso del archivo para el bloque 8 de validación - 
     * Fase 2 de PILA 2: Registro o Relación del aporte
     */
    private EstadoProcesoArchivoEnum estadoBloque8;
    
    /**
     * Acción a ejecutar al terminar el bloque 8 de validación
     */
    private AccionProcesoArchivoEnum accionBloque8;
    
    /**
     * Estado del proceso del archivo para el bloque 9 de validación - 
     * Fase 3 de PILA 2: Registro o Relación de las novedades del archivo
     */
    private EstadoProcesoArchivoEnum estadoBloque9;
    
    /**
     * Acción a ejecutar al terminar el bloque 9 de validación
     */
    private AccionProcesoArchivoEnum accionBloque9;
    
    /**
     * Estado del proceso del archivo para el bloque 10 de validación - 
     * Notificación de los resultados del proceso
     */
    private EstadoProcesoArchivoEnum estadoBloque10;
    
    /**
     * Acción a ejecutar al terminar el bloque 10 de validación
     */
    private AccionProcesoArchivoEnum accionBloque10;
    
    /** Fecha de actualización del estado del bloque 0 */
    private Long fechaBloque0;

    /** Fecha de actualización del estado del bloque 1 */
    private Long fechaBloque1;

    /** Fecha de actualización del estado del bloque 2 */
    private Long fechaBloque2;

    /** Fecha de actualización del estado del bloque 3 */
    private Long fechaBloque3;

    /** Fecha de actualización del estado del bloque 4 */
    private Long fechaBloque4;

    /** Fecha de actualización del estado del bloque 5 */
    private Long fechaBloque5;

    /** Fecha de actualización del estado del bloque 6 */
    private Long fechaBloque6;

    /** Fecha de actualización del estado del bloque 7 */
    private Long fechaBloque7;

    /** Fecha de actualización del estado del bloque 8 */
    private Long fechaBloque8;

    /** Fecha de actualización del estado del bloque 9 */
    private Long fechaBloque9;

    /** Fecha de actualización del estado del bloque 10 */
    private Long fechaBloque10;
    
    /**
     * Método para la conversión de Entity a DTO
     * */
    public void convertToDTO(EstadoArchivoPorBloque estadoBloque){
        this.setId(estadoBloque.getId());
        this.setIndicePlanilla(estadoBloque.getIndicePlanilla().getId());
        this.setTipoArchivo(estadoBloque.getTipoArchivo());
        this.setEstadoBloque0(estadoBloque.getEstadoBloque0());
        this.setAccionBloque0(estadoBloque.getAccionBloque0());
        this.setEstadoBloque1(estadoBloque.getEstadoBloque1());
        this.setAccionBloque1(estadoBloque.getAccionBloque1());
        this.setEstadoBloque2(estadoBloque.getEstadoBloque2());
        this.setAccionBloque2(estadoBloque.getAccionBloque2());
        this.setEstadoBloque3(estadoBloque.getEstadoBloque3());
        this.setAccionBloque3(estadoBloque.getAccionBloque3());
        this.setEstadoBloque4(estadoBloque.getEstadoBloque4());
        this.setAccionBloque4(estadoBloque.getAccionBloque4());
        this.setEstadoBloque5(estadoBloque.getEstadoBloque5());
        this.setAccionBloque5(estadoBloque.getAccionBloque5());
        this.setEstadoBloque6(estadoBloque.getEstadoBloque6());
        this.setAccionBloque6(estadoBloque.getAccionBloque6());
        this.setEstadoBloque7(estadoBloque.getEstadoBloque7());
        this.setAccionBloque7(estadoBloque.getAccionBloque7());
        this.setEstadoBloque8(estadoBloque.getEstadoBloque8());
        this.setAccionBloque8(estadoBloque.getAccionBloque8());
        this.setEstadoBloque9(estadoBloque.getEstadoBloque9());
        this.setAccionBloque9(estadoBloque.getAccionBloque9());
        this.setEstadoBloque10(estadoBloque.getEstadoBloque10());
        this.setAccionBloque10(estadoBloque.getAccionBloque10());
        this.setFechaBloque0(estadoBloque.getFechaBloque0() != null ? estadoBloque.getFechaBloque0().getTime() : null);
        this.setFechaBloque1(estadoBloque.getFechaBloque1() != null ? estadoBloque.getFechaBloque1().getTime() : null);
        this.setFechaBloque2(estadoBloque.getFechaBloque2() != null ? estadoBloque.getFechaBloque2().getTime() : null);
        this.setFechaBloque3(estadoBloque.getFechaBloque3() != null ? estadoBloque.getFechaBloque3().getTime() : null);
        this.setFechaBloque4(estadoBloque.getFechaBloque4() != null ? estadoBloque.getFechaBloque4().getTime() : null);
        this.setFechaBloque5(estadoBloque.getFechaBloque5() != null ? estadoBloque.getFechaBloque5().getTime() : null);
        this.setFechaBloque6(estadoBloque.getFechaBloque6() != null ? estadoBloque.getFechaBloque6().getTime() : null);
        this.setFechaBloque7(estadoBloque.getFechaBloque7() != null ? estadoBloque.getFechaBloque7().getTime() : null);
        this.setFechaBloque8(estadoBloque.getFechaBloque8() != null ? estadoBloque.getFechaBloque8().getTime() : null);
        this.setFechaBloque9(estadoBloque.getFechaBloque9() != null ? estadoBloque.getFechaBloque9().getTime() : null);
        this.setFechaBloque10(estadoBloque.getFechaBloque10() != null ? estadoBloque.getFechaBloque10().getTime() : null);
    }
    
    /**
     * Método para la conversión de DTO a Entity
     * */
    public EstadoArchivoPorBloque convertToEntity(IndicePlanillaModeloDTO indice){
        EstadoArchivoPorBloque estadoBloque = new EstadoArchivoPorBloque();
        
        estadoBloque.setId(this.getId());
        estadoBloque.setIndicePlanilla(indice.convertToEntity());
        estadoBloque.setTipoArchivo(this.getTipoArchivo());
        estadoBloque.setEstadoBloque0(this.getEstadoBloque0());
        estadoBloque.setAccionBloque0(this.getAccionBloque0());
        estadoBloque.setEstadoBloque1(this.getEstadoBloque1());
        estadoBloque.setAccionBloque1(this.getAccionBloque1());
        estadoBloque.setEstadoBloque2(this.getEstadoBloque2());
        estadoBloque.setAccionBloque2(this.getAccionBloque2());
        estadoBloque.setEstadoBloque3(this.getEstadoBloque3());
        estadoBloque.setAccionBloque3(this.getAccionBloque3());
        estadoBloque.setEstadoBloque4(this.getEstadoBloque4());
        estadoBloque.setAccionBloque4(this.getAccionBloque4());
        estadoBloque.setEstadoBloque5(this.getEstadoBloque5());
        estadoBloque.setAccionBloque5(this.getAccionBloque5());
        estadoBloque.setEstadoBloque6(this.getEstadoBloque6());
        estadoBloque.setAccionBloque6(this.getAccionBloque6());
        estadoBloque.setEstadoBloque7(this.getEstadoBloque7());
        estadoBloque.setAccionBloque7(this.getAccionBloque7());
        estadoBloque.setEstadoBloque8(this.getEstadoBloque8());
        estadoBloque.setAccionBloque8(this.getAccionBloque8());
        estadoBloque.setEstadoBloque9(this.getEstadoBloque9());
        estadoBloque.setAccionBloque9(this.getAccionBloque9());
        estadoBloque.setEstadoBloque10(this.getEstadoBloque10());
        estadoBloque.setAccionBloque10(this.getAccionBloque10());
        estadoBloque.setFechaBloque0(this.getFechaBloque0() != null ? new Date(this.getFechaBloque0()) : null);
        estadoBloque.setFechaBloque1(this.getFechaBloque1() != null ? new Date(this.getFechaBloque1()) : null);
        estadoBloque.setFechaBloque2(this.getFechaBloque2() != null ? new Date(this.getFechaBloque2()) : null);
        estadoBloque.setFechaBloque3(this.getFechaBloque3() != null ? new Date(this.getFechaBloque3()) : null);
        estadoBloque.setFechaBloque4(this.getFechaBloque4() != null ? new Date(this.getFechaBloque4()) : null);
        estadoBloque.setFechaBloque5(this.getFechaBloque5() != null ? new Date(this.getFechaBloque5()) : null);
        estadoBloque.setFechaBloque6(this.getFechaBloque6() != null ? new Date(this.getFechaBloque6()) : null);
        estadoBloque.setFechaBloque7(this.getFechaBloque7() != null ? new Date(this.getFechaBloque7()) : null);
        estadoBloque.setFechaBloque8(this.getFechaBloque8() != null ? new Date(this.getFechaBloque8()) : null);
        estadoBloque.setFechaBloque9(this.getFechaBloque9() != null ? new Date(this.getFechaBloque9()) : null);
        estadoBloque.setFechaBloque10(this.getFechaBloque10() != null ? new Date(this.getFechaBloque10()) : null);

        return estadoBloque;
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
     * @return the indicePlanilla
     */
    public Long getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla the indicePlanilla to set
     */
    public void setIndicePlanilla(Long indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the estadoBloque0
     */
    public EstadoProcesoArchivoEnum getEstadoBloque0() {
        return estadoBloque0;
    }

    /**
     * @param estadoBloque0 the estadoBloque0 to set
     */
    public void setEstadoBloque0(EstadoProcesoArchivoEnum estadoBloque0) {
        this.estadoBloque0 = estadoBloque0;
    }

    /**
     * @return the accionBloque0
     */
    public AccionProcesoArchivoEnum getAccionBloque0() {
        return accionBloque0;
    }

    /**
     * @param accionBloque0 the accionBloque0 to set
     */
    public void setAccionBloque0(AccionProcesoArchivoEnum accionBloque0) {
        this.accionBloque0 = accionBloque0;
    }

    /**
     * @return the estadoBloque1
     */
    public EstadoProcesoArchivoEnum getEstadoBloque1() {
        return estadoBloque1;
    }

    /**
     * @param estadoBloque1 the estadoBloque1 to set
     */
    public void setEstadoBloque1(EstadoProcesoArchivoEnum estadoBloque1) {
        this.estadoBloque1 = estadoBloque1;
    }

    /**
     * @return the accionBloque1
     */
    public AccionProcesoArchivoEnum getAccionBloque1() {
        return accionBloque1;
    }

    /**
     * @param accionBloque1 the accionBloque1 to set
     */
    public void setAccionBloque1(AccionProcesoArchivoEnum accionBloque1) {
        this.accionBloque1 = accionBloque1;
    }

    /**
     * @return the estadoBloque2
     */
    public EstadoProcesoArchivoEnum getEstadoBloque2() {
        return estadoBloque2;
    }

    /**
     * @param estadoBloque2 the estadoBloque2 to set
     */
    public void setEstadoBloque2(EstadoProcesoArchivoEnum estadoBloque2) {
        this.estadoBloque2 = estadoBloque2;
    }

    /**
     * @return the accionBloque2
     */
    public AccionProcesoArchivoEnum getAccionBloque2() {
        return accionBloque2;
    }

    /**
     * @param accionBloque2 the accionBloque2 to set
     */
    public void setAccionBloque2(AccionProcesoArchivoEnum accionBloque2) {
        this.accionBloque2 = accionBloque2;
    }

    /**
     * @return the estadoBloque3
     */
    public EstadoProcesoArchivoEnum getEstadoBloque3() {
        return estadoBloque3;
    }

    /**
     * @param estadoBloque3 the estadoBloque3 to set
     */
    public void setEstadoBloque3(EstadoProcesoArchivoEnum estadoBloque3) {
        this.estadoBloque3 = estadoBloque3;
    }

    /**
     * @return the accionBloque3
     */
    public AccionProcesoArchivoEnum getAccionBloque3() {
        return accionBloque3;
    }

    /**
     * @param accionBloque3 the accionBloque3 to set
     */
    public void setAccionBloque3(AccionProcesoArchivoEnum accionBloque3) {
        this.accionBloque3 = accionBloque3;
    }

    /**
     * @return the estadoBloque4
     */
    public EstadoProcesoArchivoEnum getEstadoBloque4() {
        return estadoBloque4;
    }

    /**
     * @param estadoBloque4 the estadoBloque4 to set
     */
    public void setEstadoBloque4(EstadoProcesoArchivoEnum estadoBloque4) {
        this.estadoBloque4 = estadoBloque4;
    }

    /**
     * @return the accionBloque4
     */
    public AccionProcesoArchivoEnum getAccionBloque4() {
        return accionBloque4;
    }

    /**
     * @param accionBloque4 the accionBloque4 to set
     */
    public void setAccionBloque4(AccionProcesoArchivoEnum accionBloque4) {
        this.accionBloque4 = accionBloque4;
    }

    /**
     * @return the estadoBloque5
     */
    public EstadoProcesoArchivoEnum getEstadoBloque5() {
        return estadoBloque5;
    }

    /**
     * @param estadoBloque5 the estadoBloque5 to set
     */
    public void setEstadoBloque5(EstadoProcesoArchivoEnum estadoBloque5) {
        this.estadoBloque5 = estadoBloque5;
    }

    /**
     * @return the accionBloque5
     */
    public AccionProcesoArchivoEnum getAccionBloque5() {
        return accionBloque5;
    }

    /**
     * @param accionBloque5 the accionBloque5 to set
     */
    public void setAccionBloque5(AccionProcesoArchivoEnum accionBloque5) {
        this.accionBloque5 = accionBloque5;
    }

    /**
     * @return the estadoBloque6
     */
    public EstadoProcesoArchivoEnum getEstadoBloque6() {
        return estadoBloque6;
    }

    /**
     * @param estadoBloque6 the estadoBloque6 to set
     */
    public void setEstadoBloque6(EstadoProcesoArchivoEnum estadoBloque6) {
        this.estadoBloque6 = estadoBloque6;
    }

    /**
     * @return the accionBloque6
     */
    public AccionProcesoArchivoEnum getAccionBloque6() {
        return accionBloque6;
    }

    /**
     * @param accionBloque6 the accionBloque6 to set
     */
    public void setAccionBloque6(AccionProcesoArchivoEnum accionBloque6) {
        this.accionBloque6 = accionBloque6;
    }

    /**
     * @return the estadoBloque7
     */
    public EstadoProcesoArchivoEnum getEstadoBloque7() {
        return estadoBloque7;
    }

    /**
     * @param estadoBloque7 the estadoBloque7 to set
     */
    public void setEstadoBloque7(EstadoProcesoArchivoEnum estadoBloque7) {
        this.estadoBloque7 = estadoBloque7;
    }

    /**
     * @return the accionBloque7
     */
    public AccionProcesoArchivoEnum getAccionBloque7() {
        return accionBloque7;
    }

    /**
     * @param accionBloque7 the accionBloque7 to set
     */
    public void setAccionBloque7(AccionProcesoArchivoEnum accionBloque7) {
        this.accionBloque7 = accionBloque7;
    }

    /**
     * @return the estadoBloque8
     */
    public EstadoProcesoArchivoEnum getEstadoBloque8() {
        return estadoBloque8;
    }

    /**
     * @param estadoBloque8 the estadoBloque8 to set
     */
    public void setEstadoBloque8(EstadoProcesoArchivoEnum estadoBloque8) {
        this.estadoBloque8 = estadoBloque8;
    }

    /**
     * @return the accionBloque8
     */
    public AccionProcesoArchivoEnum getAccionBloque8() {
        return accionBloque8;
    }

    /**
     * @param accionBloque8 the accionBloque8 to set
     */
    public void setAccionBloque8(AccionProcesoArchivoEnum accionBloque8) {
        this.accionBloque8 = accionBloque8;
    }

    /**
     * @return the estadoBloque9
     */
    public EstadoProcesoArchivoEnum getEstadoBloque9() {
        return estadoBloque9;
    }

    /**
     * @param estadoBloque9 the estadoBloque9 to set
     */
    public void setEstadoBloque9(EstadoProcesoArchivoEnum estadoBloque9) {
        this.estadoBloque9 = estadoBloque9;
    }

    /**
     * @return the accionBloque9
     */
    public AccionProcesoArchivoEnum getAccionBloque9() {
        return accionBloque9;
    }

    /**
     * @param accionBloque9 the accionBloque9 to set
     */
    public void setAccionBloque9(AccionProcesoArchivoEnum accionBloque9) {
        this.accionBloque9 = accionBloque9;
    }

    /**
     * @return the estadoBloque10
     */
    public EstadoProcesoArchivoEnum getEstadoBloque10() {
        return estadoBloque10;
    }

    /**
     * @param estadoBloque10 the estadoBloque10 to set
     */
    public void setEstadoBloque10(EstadoProcesoArchivoEnum estadoBloque10) {
        this.estadoBloque10 = estadoBloque10;
    }

    /**
     * @return the accionBloque10
     */
    public AccionProcesoArchivoEnum getAccionBloque10() {
        return accionBloque10;
    }

    /**
     * @param accionBloque10 the accionBloque10 to set
     */
    public void setAccionBloque10(AccionProcesoArchivoEnum accionBloque10) {
        this.accionBloque10 = accionBloque10;
    }

    /**
     * @return the fechaBloque0
     */
    public Long getFechaBloque0() {
        return fechaBloque0;
    }

    /**
     * @param fechaBloque0 the fechaBloque0 to set
     */
    public void setFechaBloque0(Long fechaBloque0) {
        this.fechaBloque0 = fechaBloque0;
    }

    /**
     * @return the fechaBloque1
     */
    public Long getFechaBloque1() {
        return fechaBloque1;
    }

    /**
     * @param fechaBloque1 the fechaBloque1 to set
     */
    public void setFechaBloque1(Long fechaBloque1) {
        this.fechaBloque1 = fechaBloque1;
    }

    /**
     * @return the fechaBloque2
     */
    public Long getFechaBloque2() {
        return fechaBloque2;
    }

    /**
     * @param fechaBloque2 the fechaBloque2 to set
     */
    public void setFechaBloque2(Long fechaBloque2) {
        this.fechaBloque2 = fechaBloque2;
    }

    /**
     * @return the fechaBloque3
     */
    public Long getFechaBloque3() {
        return fechaBloque3;
    }

    /**
     * @param fechaBloque3 the fechaBloque3 to set
     */
    public void setFechaBloque3(Long fechaBloque3) {
        this.fechaBloque3 = fechaBloque3;
    }

    /**
     * @return the fechaBloque4
     */
    public Long getFechaBloque4() {
        return fechaBloque4;
    }

    /**
     * @param fechaBloque4 the fechaBloque4 to set
     */
    public void setFechaBloque4(Long fechaBloque4) {
        this.fechaBloque4 = fechaBloque4;
    }

    /**
     * @return the fechaBloque5
     */
    public Long getFechaBloque5() {
        return fechaBloque5;
    }

    /**
     * @param fechaBloque5 the fechaBloque5 to set
     */
    public void setFechaBloque5(Long fechaBloque5) {
        this.fechaBloque5 = fechaBloque5;
    }

    /**
     * @return the fechaBloque6
     */
    public Long getFechaBloque6() {
        return fechaBloque6;
    }

    /**
     * @param fechaBloque6 the fechaBloque6 to set
     */
    public void setFechaBloque6(Long fechaBloque6) {
        this.fechaBloque6 = fechaBloque6;
    }

    /**
     * @return the fechaBloque7
     */
    public Long getFechaBloque7() {
        return fechaBloque7;
    }

    /**
     * @param fechaBloque7 the fechaBloque7 to set
     */
    public void setFechaBloque7(Long fechaBloque7) {
        this.fechaBloque7 = fechaBloque7;
    }

    /**
     * @return the fechaBloque8
     */
    public Long getFechaBloque8() {
        return fechaBloque8;
    }

    /**
     * @param fechaBloque8 the fechaBloque8 to set
     */
    public void setFechaBloque8(Long fechaBloque8) {
        this.fechaBloque8 = fechaBloque8;
    }

    /**
     * @return the fechaBloque9
     */
    public Long getFechaBloque9() {
        return fechaBloque9;
    }

    /**
     * @param fechaBloque9 the fechaBloque9 to set
     */
    public void setFechaBloque9(Long fechaBloque9) {
        this.fechaBloque9 = fechaBloque9;
    }

    /**
     * @return the fechaBloque10
     */
    public Long getFechaBloque10() {
        return fechaBloque10;
    }

    /**
     * @param fechaBloque10 the fechaBloque10 to set
     */
    public void setFechaBloque10(Long fechaBloque10) {
        this.fechaBloque10 = fechaBloque10;
    }
}
