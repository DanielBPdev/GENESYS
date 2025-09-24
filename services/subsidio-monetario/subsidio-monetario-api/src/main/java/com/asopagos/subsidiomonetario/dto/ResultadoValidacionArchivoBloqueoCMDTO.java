package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;

/**
 * <b>Descripcion:</b> DTO que contiene elementos de respuesta enviados a pantallas
 * y que se usan en varios servicios <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class ResultadoValidacionArchivoBloqueoCMDTO implements Serializable {

    private static final long serialVersionUID = -8849142300233645170L;

          
    /**
     * fecha carga
     */
    private Date fechaCargueArchivo;
    
    /**
     * Numero de registros cargados
     */
    private int numeroRegistros;
    
    /**
     * id de CargueBloqueoCuotaMonetaria
     */
    private Long idCargueBloqueoCuotaMonetaria;
    
    /**
     * Lista de resultados de la validacion
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> resultadoAllazgos;
    
    /**
     * Numero de registros exitosos
     */
    private int numeroRegistrosExitosos;
    
    /**
     * numero de registros con error o inconsistentes
     */
    private int numeroRegistrosError;
    
    private List<String[]> lineasError;
    
    
    /**
     * @return the lineasError
     */
    public List<String[]> getLineasError() {
        return lineasError;
    }



    /**
     * @param lineasError the lineasError to set
     */
    public void setLineasError(List<String[]> lineasError) {
        this.lineasError = lineasError;
    }



    /**
     * @return the numeroRegistrosExitosos
     */
    public int getNumeroRegistrosExitosos() {
        return numeroRegistrosExitosos;
    }



    /**
     * @param numeroRegistrosExitosos the numeroRegistrosExitosos to set
     */
    public void setNumeroRegistrosExitosos(int numeroRegistrosExitosos) {
        this.numeroRegistrosExitosos = numeroRegistrosExitosos;
    }



    /**
     * @return the numeroRegistrosError
     */
    public int getNumeroRegistrosError() {
        return numeroRegistrosError;
    }



    /**
     * @param numeroRegistrosError the numeroRegistrosError to set
     */
    public void setNumeroRegistrosError(int numeroRegistrosError) {
        this.numeroRegistrosError = numeroRegistrosError;
    }



    /**
     * @return the numeroRadicado
     */
    public Date getFechaCargueArchivo() {
		return fechaCargueArchivo;
	}



    /**
     * @param numeroRadicado the numeroRadicado to set
     */
	public void setFechaCargueArchivo(Date fechaCargueArchivo) {
		this.fechaCargueArchivo = fechaCargueArchivo;
	}



	   /**
     * @return the numeroRadicado
     */
	public int getNumeroRegistros() {
		return numeroRegistros;
	}



	   /**
     * @param numeroRadicado the numeroRadicado to set
     */
	public void setNumeroRegistros(int numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}



    /**
     * @return the numeroRadicado
     */
	public List<ResultadoHallazgosValidacionArchivoDTO> getResultadoAllazgos() {
		return resultadoAllazgos;
	}



    /**
     * @param numeroRadicado the numeroRadicado to set
     */
	public void setResultadoAllazgos(List<ResultadoHallazgosValidacionArchivoDTO> resultadoAllazgos) {
		this.resultadoAllazgos = resultadoAllazgos;
	}

	
    
    public Long getIdCargueBloqueoCuotaMonetaria() {
		return idCargueBloqueoCuotaMonetaria;
	}



	public void setIdCargueBloqueoCuotaMonetaria(Long idCargueBloqueoCuotaMonetaria) {
		this.idCargueBloqueoCuotaMonetaria = idCargueBloqueoCuotaMonetaria;
	}



	public ResultadoValidacionArchivoBloqueoCMDTO(){}   
  
}
