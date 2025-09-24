package com.asopagos.pila.business.interfaces;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import com.asopagos.entidades.ccf.aportes.TasasInteresMora;
import com.asopagos.entidades.pila.parametrizacion.NormatividadFechaVencimiento;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;

/**
 * <b>Descripción:</b> Interfaz para la persistencia de datos relacionados a la preparación del contexto para validación <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Local
public interface IPersistenciaPreparacionContexto {

    /**
     * Método para la consulta de las variables de paso de un bloque específico de la BD
     * @param numeroPlanilla
     *        Número de la planilla consultada
     * @param codOperador
     *        Código del operador de información que emite la planilla
     * @param bloque
     *        Bloque solicitado
     * @return List<PasoValores>
     *         Listado de variables de paso de valores
     */
    public List<PasoValores> consultarVariablesBloquePlanilla(Long numeroPlanilla, String codOperador, BloqueValidacionEnum bloque);

    /**
     * Método para consultar los días fetivos parametrizados
     * @return List<DiasFestivos>
     *         Listado de días festivos
     */
    public List<DiasFestivos> consultarFestivos();

    /**
     * Función para consultar la normatividad para determinar las fechas de vencimiento de una planilla
     * @return List<NormatividadFechaVencimiento>
     *         Listado con las entradas de normatividad parametrizadas
     */
    public List<NormatividadFechaVencimiento> consultarNormatividadVencimiento();

    /**
     * Función para consultar los escenarios de oportunidad en la presentación de las planillas PILA
     * @return List<NormatividadFechaVencimiento>
     *         Listado de entradas de escenarios de oportunidad en la presentación de las planillas PILA
     */
    public List<NormatividadFechaVencimiento> consultarOportunidadVencimiento();

    /**
     * Función para consultar los períodos de tasa de interés parametrizados
     * @return List<TasasInteresMora>
     *         Listado de entradas de períodos de tasa de interés
     */
    public List<TasasInteresMora> consultarTasasInteres();

    /**
     * Función para consultar los períodos de tasa de interés parametrizados válidos para un rango de fechas
     * @param fechaInicial
     *        Fecha inicial para el rango de consulta
     * @param fechaFinal
     *        Fecha final para el rango de consulta
     * @return <b>List<TasasInteresMora></b>
     *         Listado de entradas de períodos de tasa de interés
     */
    public List<TasasInteresMora> consultarTasasInteresPorRango(Date fechaInicial, Date fechaFinal);
}
