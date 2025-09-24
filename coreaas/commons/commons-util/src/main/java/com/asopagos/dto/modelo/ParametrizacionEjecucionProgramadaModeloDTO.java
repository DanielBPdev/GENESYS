package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.entidades.ccf.general.ParametrizacionEjecucionProgramada;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.FrecuenciaEjecucionProcesoEnum;
import com.asopagos.enumeraciones.core.ProcesoAutomaticoEnum;

public class ParametrizacionEjecucionProgramadaModeloDTO implements Serializable {
	
	
	/**
	 * Código identificador de llave primaria de la parametrización de la ejecución
	 * programada
	 */  
	private Long idParametrizacion;
    
    /**
     * Descripción que representa el proceso programado
     */
    private ProcesoAutomaticoEnum proceso;
    
    /**
     * Hora de la ejecución programada
     */
    private String horas;

    /**
     * Minutos de la ejecución programada
     */
    private String minutos;
    
    /**
     * Segundos de la ejecución programada
     */
    private String segundos;
    
    /**
     * Uno o más días de la semana (0 a 7) (0 y 7 refieren a domingo) de la ejecución programada
     */
    private String diaSemana;
    
    /**
     * Uno o más días del mes (1 a 31) de la ejecución programada
     */
    private String diaMes;
    
    /**
     * Uno o más meses (1 a 12) de la ejecución programada
     */
    private String mes;
    
    /**
     * Año en el que se desea ejecutar
     */
    private String anio;
    
    /**
     * Fecha Inicial de ejecución
     */
    private Date fechaInicio;
    
    /**
     * Fecha fin de ejecución
     */
    private Date fechaFin;
    
    /**
     * Frecuencia de la ejecución
     */
    private FrecuenciaEjecucionProcesoEnum frecuenciaEjecucionProceso;
    
    /**
     * Estado de la ejecución
     */
    private EstadoActivoInactivoEnum estadoEjecucionProceso;


    /**
	 * Método que convierte la entidad a ParametrizacionEjecucionProgramada.
	 * @param ParametrizacionEjecucionProgramada representada en forma de entidad.
	 */
	public void convertToDTO(ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada){
		this.setAnio(parametrizacionEjecucionProgramada.getAnio());
		this.setDiaMes(parametrizacionEjecucionProgramada.getDiaMes());
		this.setDiaSemana(parametrizacionEjecucionProgramada.getDiaSemana());
		this.setFechaFin(parametrizacionEjecucionProgramada.getFechaFin());
		this.setFechaInicio(parametrizacionEjecucionProgramada.getFechaInicio());
		this.setFrecuenciaEjecucionProceso(parametrizacionEjecucionProgramada.getFrecuenciaEjecucionProceso());
		this.setHoras(parametrizacionEjecucionProgramada.getHoras());
		this.setIdParametrizacion(parametrizacionEjecucionProgramada.getIdParametrizacion());
		this.setMes(parametrizacionEjecucionProgramada.getMes());
		this.setMinutos(parametrizacionEjecucionProgramada.getMinutos());
		this.setProceso(parametrizacionEjecucionProgramada.getProceso());
		this.setSegundos(parametrizacionEjecucionProgramada.getSegundos());
		this.setEstadoEjecucionProceso(parametrizacionEjecucionProgramada.getEstadoEjecucionProceso());
	}
	
	/**
	 * Método que convierte de DTO a una Entidad.
	 * @return parametrizacionEjecucionProgramada convertida.
	 */
	public ParametrizacionEjecucionProgramada convertToEntity(){
		ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada = new ParametrizacionEjecucionProgramada();
		parametrizacionEjecucionProgramada.setAnio(this.getAnio());
		parametrizacionEjecucionProgramada.setDiaMes(this.getDiaMes());
		parametrizacionEjecucionProgramada.setDiaSemana(this.getDiaSemana());
		parametrizacionEjecucionProgramada.setFechaFin(this.getFechaFin());
		parametrizacionEjecucionProgramada.setFechaInicio(this.getFechaInicio());
		parametrizacionEjecucionProgramada.setFrecuenciaEjecucionProceso(this.getFrecuenciaEjecucionProceso());
		parametrizacionEjecucionProgramada.setHoras(this.getHoras());
		parametrizacionEjecucionProgramada.setIdParametrizacion(this.getIdParametrizacion());
		parametrizacionEjecucionProgramada.setMes(this.getMes());
		parametrizacionEjecucionProgramada.setMinutos(this.getMinutos());
		parametrizacionEjecucionProgramada.setProceso(this.getProceso());
		parametrizacionEjecucionProgramada.setSegundos(this.getSegundos());
		parametrizacionEjecucionProgramada.setEstadoEjecucionProceso(this.getEstadoEjecucionProceso());
		return parametrizacionEjecucionProgramada;
	}
	
	/**
     * Copia los datos del DTO a la Entidad.
     * @param parametrizacionEjecucionProgramada previamente consultada.
     */
	public ParametrizacionEjecucionProgramada copyDTOToEntity(ParametrizacionEjecucionProgramada parametrizacionEjecucionProgramada) {
		if (this.getAnio() != null) {
			parametrizacionEjecucionProgramada.setAnio(this.getAnio());
		}
		if (this.getDiaMes() != null) {
			parametrizacionEjecucionProgramada.setDiaMes(this.getDiaMes());
		}
		if (this.getDiaSemana() != null) {
			parametrizacionEjecucionProgramada.setDiaSemana(this.getDiaSemana());
		}
		if (this.getFechaFin() != null) {
			parametrizacionEjecucionProgramada.setFechaFin(this.getFechaFin());
		}
		if (this.getFechaInicio() != null) {
			parametrizacionEjecucionProgramada.setFechaInicio(this.getFechaInicio());
		}
		if (this.getFrecuenciaEjecucionProceso() != null) {
			parametrizacionEjecucionProgramada.setFrecuenciaEjecucionProceso(this.getFrecuenciaEjecucionProceso());
		}
		if (this.getHoras() != null) {
			parametrizacionEjecucionProgramada.setHoras(this.getHoras());
		}
		if (this.getIdParametrizacion() != null) {
			parametrizacionEjecucionProgramada.setIdParametrizacion(this.getIdParametrizacion());
		}
		if (this.getMes() != null) {
			parametrizacionEjecucionProgramada.setMes(this.getMes());
		}
		if (this.getMinutos() != null) {
			parametrizacionEjecucionProgramada.setMinutos(this.getMinutos());
		}
		if (this.getProceso() != null) {
			parametrizacionEjecucionProgramada.setProceso(this.getProceso());
		}
		if (this.getSegundos() != null) {
			parametrizacionEjecucionProgramada.setSegundos(this.getSegundos());
		}
		if (this.getEstadoEjecucionProceso() != null) {
			parametrizacionEjecucionProgramada.setEstadoEjecucionProceso(this.getEstadoEjecucionProceso());
		}
		return parametrizacionEjecucionProgramada;
	}
	
	/**
	 * @return the idParametrizacion
	 */
	public Long getIdParametrizacion() {
		return idParametrizacion;
	}

	/**
	 * @param idParametrizacion the idParametrizacion to set
	 */
	public void setIdParametrizacion(Long idParametrizacion) {
		this.idParametrizacion = idParametrizacion;
	}

	/**
	 * @return the proceso
	 */
	public ProcesoAutomaticoEnum getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(ProcesoAutomaticoEnum proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return the horas
	 */
	public String getHoras() {
		return horas;
	}

	/**
	 * @param horas the horas to set
	 */
	public void setHoras(String horas) {
		this.horas = horas;
	}

	/**
	 * @return the minutos
	 */
	public String getMinutos() {
		return minutos;
	}

	/**
	 * @param minutos the minutos to set
	 */
	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	/**
	 * @return the segundos
	 */
	public String getSegundos() {
		return segundos;
	}

	/**
	 * @param segundos the segundos to set
	 */
	public void setSegundos(String segundos) {
		this.segundos = segundos;
	}

	/**
	 * @return the diaSemana
	 */
	public String getDiaSemana() {
		return diaSemana;
	}

	/**
	 * @param diaSemana the diaSemana to set
	 */
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	/**
	 * @return the diaMes
	 */
	public String getDiaMes() {
		return diaMes;
	}

	/**
	 * @param diaMes the diaMes to set
	 */
	public void setDiaMes(String diaMes) {
		this.diaMes = diaMes;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
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
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the frecuenciaEjecucionProceso
	 */
	public FrecuenciaEjecucionProcesoEnum getFrecuenciaEjecucionProceso() {
		return frecuenciaEjecucionProceso;
	}

	/**
	 * @param frecuenciaEjecucionProceso the frecuenciaEjecucionProceso to set
	 */
	public void setFrecuenciaEjecucionProceso(FrecuenciaEjecucionProcesoEnum frecuenciaEjecucionProceso) {
		this.frecuenciaEjecucionProceso = frecuenciaEjecucionProceso;
	}
	
	/**
	 * @return the estadoEjecucionProceso
	 */
	public EstadoActivoInactivoEnum getEstadoEjecucionProceso() {
		return estadoEjecucionProceso;
	}

	/**
	 * @param estadoEjecucionProceso the estadoEjecucionProceso to set
	 */
	public void setEstadoEjecucionProceso(EstadoActivoInactivoEnum estadoEjecucionProceso) {
		this.estadoEjecucionProceso = estadoEjecucionProceso;
	}
}