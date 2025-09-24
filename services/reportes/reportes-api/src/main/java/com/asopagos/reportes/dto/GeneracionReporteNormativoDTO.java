package com.asopagos.reportes.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.GeneracionReporteNormativo;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;

/**
 * <b>Descripción</b> DTO que contiene la información necesaria para la
 * generación de un reporte normativo
 * 
 * @author sbrinez
 */
@XmlRootElement
public class GeneracionReporteNormativoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idGeneracionReporte;

    private ReporteNormativoEnum reporteNormativo;

    private Date fechaInicio;

    private Date fechaFin;

    private Boolean reporteOficial;

    private String identificadorArchivo;

    private String nombreReporte;

    private String usuarioGeneracion;

    private Date fechaGeneracion;

    private String periodo;
    
    private String nombreArchivo;
    
    private FichaControlDTO fichaControl;
    
    private Byte periodosDesagregado;

    public GeneracionReporteNormativoDTO() {
    }

    public GeneracionReporteNormativoDTO(GeneracionReporteNormativo generacionReporteNormativo) {
        this.idGeneracionReporte = generacionReporteNormativo.getIdGeneracionReporteNormativo();
        this.fechaGeneracion = generacionReporteNormativo.getFechaGeneracion();
        this.setFechaInicio(new Date(generacionReporteNormativo.getFechaInicio().getTime()));
        this.setFechaFin(new Date(generacionReporteNormativo.getFechaFin().getTime()));
        this.identificadorArchivo = generacionReporteNormativo.getIdentificadorArchivo();
        this.nombreReporte = generacionReporteNormativo.getNombreReporte();
        this.usuarioGeneracion = generacionReporteNormativo.getUsuarioGeneracion();
        
        StringBuilder fechaPeriodo = new StringBuilder();
        String separador = "/";

        LocalDate inicioPeriodo = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String mesIniPeriodo = String
                .valueOf((inicioPeriodo.getMonthValue() < 9) ? "0" + inicioPeriodo.getMonthValue() : inicioPeriodo.getMonthValue());
        String diaIniPeriodo = String
                .valueOf((inicioPeriodo.getDayOfMonth() < 9) ? "0" + inicioPeriodo.getDayOfMonth() : inicioPeriodo.getDayOfMonth());

        LocalDate finPeriodo = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String mesFinPeriodo = String
                .valueOf((finPeriodo.getMonthValue() < 9) ? "0" + finPeriodo.getMonthValue() : finPeriodo.getMonthValue());
        String diaFinPeriodo = String
                .valueOf((finPeriodo.getDayOfMonth() < 9) ? "0" + finPeriodo.getDayOfMonth() : finPeriodo.getDayOfMonth());

        fechaPeriodo.append(inicioPeriodo.getYear() + separador + mesIniPeriodo + separador + diaIniPeriodo + " - "
                + finPeriodo.getYear() + separador + mesFinPeriodo + separador + diaFinPeriodo);

        this.setPeriodo(fechaPeriodo.toString());

    }

    /**
     * Metodo encargado de convertir el dto en entidad.
     * @return entidad
     */
    public GeneracionReporteNormativo convertToEntity() {
        GeneracionReporteNormativo reporte = new GeneracionReporteNormativo();
        reporte.setNombreReporte(this.getNombreReporte());
        reporte.setIdentificadorArchivo(this.getIdentificadorArchivo());
        reporte.setFechaGeneracion(new Date());
        reporte.setUsuarioGeneracion(this.getUsuarioGeneracion());
        reporte.setFechaInicio(this.getFechaInicio());
        reporte.setFechaFin(this.getFechaFin());
        reporte.setReporteOficial(this.getReporteOficial());
        reporte.setReporteNormativo(this.getReporteNormativo());
        return reporte;
    }

    /**
     * @return the idGeneracionReporte
     */
    public Long getIdGeneracionReporte() {
        return idGeneracionReporte;
    }

    /**
     * @param idGeneracionReporte
     *        the idGeneracionReporte to set
     */
    public void setIdGeneracionReporte(Long idGeneracionReporte) {
        this.idGeneracionReporte = idGeneracionReporte;
    }

    /**
     * @return the reporteNormativo
     */
    public ReporteNormativoEnum getReporteNormativo() {
        return reporteNormativo;
    }

    /**
     * @param reporteNormativo
     *        the reporteNormativo to set
     */
    public void setReporteNormativo(ReporteNormativoEnum reporteNormativo) {
        this.reporteNormativo = reporteNormativo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the reporteOficial
     */
    public Boolean getReporteOficial() {
        return reporteOficial;
    }

    /**
     * @param reporteOficial
     *        the reporteOficial to set
     */
    public void setReporteOficial(Boolean reporteOficial) {
        this.reporteOficial = reporteOficial;
    }

    /**
     * @return the identificadorArchivo
     */
    public String getIdentificadorArchivo() {
        return identificadorArchivo;
    }

    /**
     * @param identificadorArchivo
     *        the identificadorArchivo to set
     */
    public void setIdentificadorArchivo(String identificadorArchivo) {
        this.identificadorArchivo = identificadorArchivo;
    }

    /**
     * @return the nombreReporte
     */
    public String getNombreReporte() {
        return nombreReporte;
    }

    /**
     * @param nombreReporte
     *        the nombreReporte to set
     */
    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    /**
     * @return the usuarioGeneracion
     */
    public String getUsuarioGeneracion() {
        return usuarioGeneracion;
    }

    /**
     * @param usuarioGeneracion
     *        the usuarioGeneracion to set
     */
    public void setUsuarioGeneracion(String usuarioGeneracion) {
        this.usuarioGeneracion = usuarioGeneracion;
    }

    /**
     * @return the fechaGeneracion
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * @param fechaGeneracion
     *        the fechaGeneracion to set
     */
    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

	public FichaControlDTO getFichaControl() {
		return fichaControl;
	}

	public void setFichaControl(FichaControlDTO fichaControl) {
		this.fichaControl = fichaControl;
	}

	public Byte getPeriodosDesagregado() {
		return periodosDesagregado;
	}

	public void setPeriodosDesagregado(Byte periodosDesagregado) {
		this.periodosDesagregado = periodosDesagregado;
	}
    
    

}
