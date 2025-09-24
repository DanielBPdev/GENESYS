package com.asopagos.aportes.masivos.dto;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;

/**
 * DTO para la transferencia de datos de los archivos de entidad de descuento
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy López Cardona.</a>
 */
public class ArchivoCrucesAportesDTO extends InformacionArchivoDTO {

    /** modo como se carga el archivo automático/manual */
    private TipoCargaArchivoEnum modo;

    /** El Usuario que realiza la carga */
    private String usuario;


    /**
     * Método que retorna el DTO correspondiente al padre
     * @return DTO con la información del padre
     */
    public InformacionArchivoDTO convertToParent(){
        InformacionArchivoDTO informacionArchivoDTO = new InformacionArchivoDTO();
        informacionArchivoDTO.setProcessName(getProcessName());
        informacionArchivoDTO.setIdInstanciaProceso(getIdInstanciaProceso());
        informacionArchivoDTO.setDocName(getDocName());
        informacionArchivoDTO.setDescription(getDescription());
        informacionArchivoDTO.setFileName(getFileName());
        informacionArchivoDTO.setDataFile(getDataFile());
        informacionArchivoDTO.setIdentificadorDocumento(getIdentificadorDocumento());
        informacionArchivoDTO.setVersionDocumento(getVersionDocumento());
        informacionArchivoDTO.setFileType(getFileType());
        return informacionArchivoDTO;
    }

    /**
     * @return the modo
     */
    public TipoCargaArchivoEnum getModo() {
        return modo;
    }

    /**
     * @param modo
     *        the modo to set
     */
    public void setModo(TipoCargaArchivoEnum modo) {
        this.modo = modo;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /*public void setfileName(String fileName) {
        this.fileName = ArchivoCrucesAportesDTO.getFileName();//
    }

    public String getfileName() {
        return fileName;
    }*/

    @java.lang.Override
    public java.lang.String toString() {
        return "ArchivoCrucesAportesDTO{" +
                "modo=" + modo +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
