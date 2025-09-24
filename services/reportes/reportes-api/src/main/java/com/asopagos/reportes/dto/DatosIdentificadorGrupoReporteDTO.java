package com.asopagos.reportes.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.ReporteKPI;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;

/**
 * <b>Descripción</b> DTO que representa los identificadores del reporte y grupo.
 * <b></b>
 * @author <a href="mailto:juagonzalez@heinsohn.com.co">Juan Camilo González Vargas</a>
 */
@XmlRootElement
public class DatosIdentificadorGrupoReporteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id del reporte
     */
    private Long idReporte;

    /**
     * identificador del reporte
     */
    private String identificadorReporte;

    /**
     * frecuencia del reporte
     */
    private FrecuenciaMetaEnum frecuenciaReporte;

    /**
     * id del grupo del reporte
     */
    private String idGrupo;

    /**
     * tipo de reporte
     */
    private ReporteKPIEnum nombreReporte;

    public DatosIdentificadorGrupoReporteDTO() {
    }

    public DatosIdentificadorGrupoReporteDTO(ReporteKPI reporte) {
        convertToDTO(reporte);
    }

    public void convertToDTO(ReporteKPI reporte) {
        this.setIdReporte(reporte.getIdReporte());
        this.setIdentificadorReporte(reporte.getIdentificadorReporte());
        this.setFrecuenciaReporte(reporte.getFrecuenciaReporte());
        this.setIdGrupo(reporte.getIdGrupo());
        this.setNombreReporte(reporte.getNombreReporte());
    }

    public ReporteKPI convertToEntity() {

        ReporteKPI reporte = new ReporteKPI();
        reporte.setIdReporte(this.getIdReporte());
        reporte.setIdentificadorReporte(this.getIdentificadorReporte());
        reporte.setFrecuenciaReporte(this.getFrecuenciaReporte());
        reporte.setIdGrupo(this.getIdGrupo());
        reporte.setNombreReporte(this.getNombreReporte());
        return reporte;
    }

    public ReporteKPI copyDTOToEntity(ReporteKPI reporte) {
        if (this.getIdReporte() != null) {
            reporte.setIdReporte(this.getIdReporte());
        }
        if (this.getIdentificadorReporte() != null) {
            reporte.setIdentificadorReporte(this.getIdentificadorReporte());
        }
        if (this.getFrecuenciaReporte() != null) {
            reporte.setFrecuenciaReporte(this.getFrecuenciaReporte());
        }
        if (this.getIdGrupo() != null) {
            reporte.setIdGrupo(this.getIdGrupo());
        }
        if (this.getNombreReporte() != null) {
            reporte.setNombreReporte(this.getNombreReporte());
        }
        return reporte;
    }

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * @return the identificadorReporte
     */
    public String getIdentificadorReporte() {
        return identificadorReporte;
    }

    /**
     * @param identificadorReporte
     *        the identificadorReporte to set
     */
    public void setIdentificadorReporte(String identificadorReporte) {
        this.identificadorReporte = identificadorReporte;
    }

    public FrecuenciaMetaEnum getFrecuenciaReporte() {
        return frecuenciaReporte;
    }

    public void setFrecuenciaReporte(FrecuenciaMetaEnum frecuenciaReporte) {
        this.frecuenciaReporte = frecuenciaReporte;
    }

    /**
     * @return the idGrupo
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo
     *        the idGrupo to set
     */
    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * @return the nombreReporte
     */
    public ReporteKPIEnum getNombreReporte() {
        return nombreReporte;
    }

    /**
     * @param nombreReporte
     *        the nombreReporte to set
     */
    public void setNombreReporte(ReporteKPIEnum nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

}
