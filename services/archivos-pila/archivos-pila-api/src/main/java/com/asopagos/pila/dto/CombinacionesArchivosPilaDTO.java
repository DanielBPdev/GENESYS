package com.asopagos.pila.dto;

import java.util.ArrayList;
import java.util.List;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;

/**
 * DTO que contiene la información necesaria para realizar la validación de
 * carga de archivos según su posible convinatoria
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class CombinacionesArchivosPilaDTO {
    /** archivo que se intentar cargar */
    private IndicePlanilla archivoCargado;

    /** archivo que se encuentra cargado previamente */
    private List<ElementoCombinatoriaArchivosDTO> archivoPrevio;

    /** archivo que se encuentra cargado tipo A o AP */
    private List<ElementoCombinatoriaArchivosDTO> archivo_A_AP;

    /** archivo que se encuentra cargado tipo AR o APR */
    private List<ElementoCombinatoriaArchivosDTO> archivo_AR_APR;

    /** archivo que se encuentra cargado tipo I o IP */
    private List<ElementoCombinatoriaArchivosDTO> archivo_I_IP;

    /** archivo que se encuentra cargado tipo IR o IPR */
    private List<ElementoCombinatoriaArchivosDTO> archivo_IR_IPR;

    /** indica que el archivo cargado se trata de un reproceso */
    private Boolean reproceso = false;

    /** indica que el archivo cargado se puede procesar */
    private Boolean procesar = false;

    /** indica que se debe anular el archivo cargado al final del proceso */
    private Boolean anularCargado = false;

    /** indica que el archivo previo se debe eliminar */
    private Boolean anularAnterior = false;

    /** indica que se deben anular archivos I_IP y/o A_AP previos */
    private boolean anularOriginales = false;

    /**
     * Método para agregar archivos a listados
     * @param indicePlanilla
     *        Entrada de índice de planilla a agregar
     * @param lista
     *        Indicador de la lista a la cual se agrega el índice
     *        <br>
     *        1 - listado de archivos anteriores
     *        <br>
     *        2 - listado de archivos A - AP
     *        <br>
     *        3 - listado de archivos I - IP
     *        <br>
     *        4 - listado de archivos AR - APR
     *        <br>
     *        5 - listado de archivos IR - IPR
     * @param esAnulable
     *        Indicador para determinar que el índice de planilla es anulable
     */
    public void addIndiceListado(ElementoCombinatoriaArchivosDTO entrada, Integer lista) {

        switch (lista) {
            case 1: // listado de archivos anteriores
                if (this.archivoPrevio == null) { // se crea el listado en caso de no existir
                    this.archivoPrevio = new ArrayList<>();
                }
                this.archivoPrevio.add(entrada);
                break;
            case 2: // listado de archivos A - AP
                if (this.archivo_A_AP == null) { // se crea el listado en caso de no existir
                    this.archivo_A_AP = new ArrayList<>();
                }
                this.archivo_A_AP.add(entrada);
                break;
            case 3: // listado de archivos I - IP
                if (this.archivo_I_IP == null) { // se crea el listado en caso de no existir
                    this.archivo_I_IP = new ArrayList<>();
                }
                this.archivo_I_IP.add(entrada);
                break;
            case 4: // listado de archivos AR - APR
                if (this.archivo_AR_APR == null) { // se crea el listado en caso de no existir
                    this.archivo_AR_APR = new ArrayList<>();
                }
                this.archivo_AR_APR.add(entrada);
                break;
            case 5: // listado de archivos IR - IPR
                if (this.archivo_IR_IPR == null) { // se crea el listado en caso de no existir
                    this.archivo_IR_IPR = new ArrayList<>();
                }
                this.archivo_IR_IPR.add(entrada);
                break;
            default:
                break;
        }
    }
    
    /**
     * Metodo encargado de determinar que algún listado de elementos contiene un índice que no es anulable
     * */
    public Boolean listadoAnulable(){
        
        if(this.archivoPrevio != null){
            for (ElementoCombinatoriaArchivosDTO elemento : this.archivoPrevio) {
                if(!elemento.getEsAnulable()){
                    return false;
                }
            }
        }
        
        if(this.archivo_A_AP != null){
            for (ElementoCombinatoriaArchivosDTO elemento : this.archivo_A_AP) {
                if(!elemento.getEsAnulable()){
                    return false;
                }
            }
        }
        
        if(this.archivo_I_IP != null){
            for (ElementoCombinatoriaArchivosDTO elemento : this.archivo_I_IP) {
                if(!elemento.getEsAnulable()){
                    return false;
                }
            }
        }
        
        if(this.archivo_AR_APR != null){
            for (ElementoCombinatoriaArchivosDTO elemento : this.archivo_AR_APR) {
                if(!elemento.getEsAnulable()){
                    return false;
                }
            }
        }
        
        if(this.archivo_IR_IPR != null){
            for (ElementoCombinatoriaArchivosDTO elemento : this.archivo_IR_IPR) {
                if(!elemento.getEsAnulable()){
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * @return the archivoCargado
     */
    public IndicePlanilla getArchivoCargado() {
        return archivoCargado;
    }

    /**
     * @param archivoCargado the archivoCargado to set
     */
    public void setArchivoCargado(IndicePlanilla archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    /**
     * @return the archivoPrevio
     */
    public List<ElementoCombinatoriaArchivosDTO> getArchivoPrevio() {
        return archivoPrevio;
    }

    /**
     * @param archivoPrevio the archivoPrevio to set
     */
    public void setArchivoPrevio(List<ElementoCombinatoriaArchivosDTO> archivoPrevio) {
        this.archivoPrevio = archivoPrevio;
    }

    /**
     * @return the archivo_A_AP
     */
    public List<ElementoCombinatoriaArchivosDTO> getArchivo_A_AP() {
        return archivo_A_AP;
    }

    /**
     * @param archivo_A_AP the archivo_A_AP to set
     */
    public void setArchivo_A_AP(List<ElementoCombinatoriaArchivosDTO> archivo_A_AP) {
        this.archivo_A_AP = archivo_A_AP;
    }

    /**
     * @return the archivo_AR_APR
     */
    public List<ElementoCombinatoriaArchivosDTO> getArchivo_AR_APR() {
        return archivo_AR_APR;
    }

    /**
     * @param archivo_AR_APR the archivo_AR_APR to set
     */
    public void setArchivo_AR_APR(List<ElementoCombinatoriaArchivosDTO> archivo_AR_APR) {
        this.archivo_AR_APR = archivo_AR_APR;
    }

    /**
     * @return the archivo_I_IP
     */
    public List<ElementoCombinatoriaArchivosDTO> getArchivo_I_IP() {
        return archivo_I_IP;
    }

    /**
     * @param archivo_I_IP the archivo_I_IP to set
     */
    public void setArchivo_I_IP(List<ElementoCombinatoriaArchivosDTO> archivo_I_IP) {
        this.archivo_I_IP = archivo_I_IP;
    }

    /**
     * @return the archivo_IR_IPR
     */
    public List<ElementoCombinatoriaArchivosDTO> getArchivo_IR_IPR() {
        return archivo_IR_IPR;
    }

    /**
     * @param archivo_IR_IPR the archivo_IR_IPR to set
     */
    public void setArchivo_IR_IPR(List<ElementoCombinatoriaArchivosDTO> archivo_IR_IPR) {
        this.archivo_IR_IPR = archivo_IR_IPR;
    }

    /**
     * @return the reproceso
     */
    public Boolean getReproceso() {
        return reproceso;
    }

    /**
     * @param reproceso the reproceso to set
     */
    public void setReproceso(Boolean reproceso) {
        this.reproceso = reproceso;
    }

    /**
     * @return the procesar
     */
    public Boolean getProcesar() {
        return procesar;
    }

    /**
     * @param procesar the procesar to set
     */
    public void setProcesar(Boolean procesar) {
        this.procesar = procesar;
    }

    /**
     * @return the anularCargado
     */
    public Boolean getAnularCargado() {
        return anularCargado;
    }

    /**
     * @param anularCargado the anularCargado to set
     */
    public void setAnularCargado(Boolean anularCargado) {
        this.anularCargado = anularCargado;
    }

    /**
     * @return the anularAnterior
     */
    public Boolean getAnularAnterior() {
        return anularAnterior;
    }

    /**
     * @param anularAnterior the anularAnterior to set
     */
    public void setAnularAnterior(Boolean anularAnterior) {
        this.anularAnterior = anularAnterior;
    }

    /**
     * @return the anularOriginales
     */
    public boolean isAnularOriginales() {
        return anularOriginales;
    }

    /**
     * @param anularOriginales the anularOriginales to set
     */
    public void setAnularOriginales(boolean anularOriginales) {
        this.anularOriginales = anularOriginales;
    }
}
