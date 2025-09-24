package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovis;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CargueArchivoCruceFovisDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5027908672381412399L;

    /**
     * Identificador cargue
     */
    private Long idCargueArchivoFovis;

    /**
     * Nombre archivo
     */
    private String nombreArchivo;

    /**
     * Fecha de procesamiento del archivo
     */
    private Date fechaCargue;

    /**
     * Identificador ECM del archivo
     */
    private String codigoIdentificacionECM;

    /**
     * Informacion archivo payload
     */
    private InformacionCruceFovisDTO informacionContenidoArchivo;

    /**
     * Constructor
     */
    public CargueArchivoCruceFovisDTO() {
        super();
    }

    /**
     * Convierte la entidad en DTO
     * @param cargueArchivoCruceFovis
     *        Entidad
     * @return DTO
     */
    public static CargueArchivoCruceFovisDTO convertEntityToDTO(CargueArchivoCruceFovis cargueArchivoCruceFovis) {
        CargueArchivoCruceFovisDTO cargueArchivoCruceFovisDTO = new CargueArchivoCruceFovisDTO();
        cargueArchivoCruceFovisDTO.setIdCargueArchivoFovis(cargueArchivoCruceFovis.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovisDTO.setNombreArchivo(cargueArchivoCruceFovis.getNombreArchivo());
        cargueArchivoCruceFovisDTO.setFechaCargue(cargueArchivoCruceFovis.getFechaCargue());
        cargueArchivoCruceFovisDTO.setCodigoIdentificacionECM(cargueArchivoCruceFovis.getCodigoIdentificacionECM());
        return cargueArchivoCruceFovisDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public CargueArchivoCruceFovis convertToEntity() {
        CargueArchivoCruceFovis cargueArchivoCruceFovis = new CargueArchivoCruceFovis();
        cargueArchivoCruceFovis.setCodigoIdentificacionECM(this.getCodigoIdentificacionECM());
        cargueArchivoCruceFovis.setFechaCargue(this.getFechaCargue());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoFovis());
        cargueArchivoCruceFovis.setNombreArchivo(this.getNombreArchivo());
        return cargueArchivoCruceFovis;
    }

    /**
     * @return the idCargueArchivoFovis
     */
    public Long getIdCargueArchivoFovis() {
        return idCargueArchivoFovis;
    }

    /**
     * @param idCargueArchivoFovis
     *        the idCargueArchivoFovis to set
     */
    public void setIdCargueArchivoFovis(Long idCargueArchivoFovis) {
        this.idCargueArchivoFovis = idCargueArchivoFovis;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the fechaCargue
     */
    public Date getFechaCargue() {
        return fechaCargue;
    }

    /**
     * @param fechaCargue
     *        the fechaCargue to set
     */
    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    /**
     * @return the codigoIdentificacionECM
     */
    public String getCodigoIdentificacionECM() {
        return codigoIdentificacionECM;
    }

    /**
     * @param codigoIdentificacionECM
     *        the codigoIdentificacionECM to set
     */
    public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
        this.codigoIdentificacionECM = codigoIdentificacionECM;
    }

    /**
     * @return the informacionContenidoArchivo
     */
    public InformacionCruceFovisDTO getInformacionContenidoArchivo() {
        return informacionContenidoArchivo;
    }

    /**
     * @param informacionContenidoArchivo
     *        the informacionContenidoArchivo to set
     */
    public void setInformacionContenidoArchivo(InformacionCruceFovisDTO informacionContenidoArchivo) {
        this.informacionContenidoArchivo = informacionContenidoArchivo;
    }

}
