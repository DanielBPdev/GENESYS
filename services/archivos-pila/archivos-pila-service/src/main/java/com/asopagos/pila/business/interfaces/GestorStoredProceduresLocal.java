package com.asopagos.pila.business.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.FasePila2Enum;

import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;

/**
 * <b>Descripcion:</b> Interfaz que define los métodos para el trabajo con procedimientos almacenados en BD
 * para el arranque de la segunda etapa de la gestión de aportes<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-395, HU-211-396, HU-211-480, HU-211-397, HU-211-398<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface GestorStoredProceduresLocal {

    /**
     * Método para ejecutar el procedimiento almacenado que se encarga de dar inicio a las ETL con las
     * validaciones de la Fase 1 (HU-211-395, HU-211-396, HU-211-480) y continua con la Fase 2 (HU-211-397)
     * 
     * @param IdIndicePlanilla
     *        Identificador de índice de planilla a procesar
     * @param fase
     *        Identificador de la fase en la cual se inicia el procesamiento
     * @param esSimulado
     *        Marca para saber que la ejecución es una simulación de proceso asistido
     * @param usuario
     *        El usuario que realiza la ejecución de un proceso asistido
	 * @param timeStamp
	 * 		  Marca de tiempo que identifica al paquete de planillas a procesar
     */
    public void ejecutarProcesamientoPila2(Long IdIndicePlanilla, FasePila2Enum fase, Boolean esSimulado, String usuario, Long timeStamp);
    
    /**
     * Método encargado de la consulta del siguiente valor de la secuencia Sec_PilaArchivoIRegistro2
     * para establecer el ID del registro a agregar en BD
     * @param cantidad
     *        Cantidad de valores de secuencia solicitados
     * @param nombreSecuencia 
     * @return <b>Long</b>
     *         Valor de la secuencia para el ID
     */
    public List<Long> obtenerValoresSecuencia(Integer cantidad, String nombreSecuencia);

	/**
	 * Método para el almacenamiento de los datos de paquete de planillas para PILA 2
	 * @param idsParaPila2
	 * 		  Listado de los IDs de índice de planilla del paquete
	 * @param timeStamp
	 * 		  Marca de tiempo que identifica al paquete
	 */
	public void almacenarPaquetePlanillas(List<Long> idsParaPila2, Long timeStamp);

	/**
	 * Método que persiste un archivo en el tabla PreliminarArchivoPila
	 * @param p
	 */
	public void almacenarPreliminarArchivoPila(byte[] file, IndicePlanilla indicePlanilla, String locale) throws FileProcessingException;
	
	/**
	 * Método que ejecuta la distribución del contenido del archivo en las tablas correspodientes 
	 * @param idPlanilla
	 */
	public void executePILA1Persistencia(Long idPlanilla);
}
