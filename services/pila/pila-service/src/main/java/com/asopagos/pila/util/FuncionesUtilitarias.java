package com.asopagos.pila.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.asopagos.dto.DiasFestivosModeloDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.common.util.CalendarUtil;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class FuncionesUtilitarias {
    /** Referencia al logger */
    private static final ILogger logger = LogManager.getLogger(FuncionesUtilitarias.class);

    /**
     * Función para sustraer o adicionar días hábiles a una fecha
     * @param fecha
     *        Fecha a modificar
     * @param dias
     *        Cantidad de días hábiles a modificar
     * @param festivos
     *        Listado de los días festivos registrados en la BD
     * @return Fecha modificada
     */
    public static Calendar modificarFechaHabil(Calendar fecha, Integer dias, List<DiasFestivosModeloDTO> festivos) {
        String firmaMetodo = "FuncionesUtilitarias.modificarFecha(Calendar, Integer, List<DiasFestivosModeloDTO>)";
        logger.debug("Inicia "+firmaMetodo );

        int incremento = 1;
        Integer diasTemporal = dias;
        GregorianCalendar fechaRespuesta = (GregorianCalendar) fecha.clone();

        if (dias < 0)
            incremento = -1;

        try {
            // se modifica la fecha día a día
            while (diasTemporal != 0) {
                // se modifica la fecha
                fechaRespuesta.add(Calendar.DAY_OF_MONTH, incremento);

                // se evalua la fecha modificada
                if (fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                        && fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                    // sí la fecha modificada no es un domingo, se le busca en la lista de festivos
                    if (!isFestivo(fechaRespuesta, festivos)) {
                        diasTemporal -= incremento;
                    }
                }
            }
        } catch (Exception e) {
        }
        
        logger.debug("Finaliza "+firmaMetodo );
        return fechaRespuesta;
    }

    /**
     * Función para establecer sí una fecha está incluida en el listado de festivos
     * @param fecha
     *        Fecha a buscar
     * @param festivos
     *        Listado de días festivos
     * @returns Fecha festivo true o false
     */
    public static boolean isFestivo(Calendar fecha, List<DiasFestivosModeloDTO> festivos) {
        String firmaMetodo = "FuncionesUtilitarias.isFestivo(Calendar, List<DiasFestivosModeloDTO>)";
        logger.debug("Inicia "+firmaMetodo );
        for (DiasFestivosModeloDTO diaFestivo : festivos) {
            Calendar diaFest = CalendarUtil.toCalendar(diaFestivo.getFecha());
            Calendar fechaTemporal = fecha;
            diaFest = CalendarUtil.fomatearFechaSinHora(diaFest);
            fechaTemporal = CalendarUtil.fomatearFechaSinHora(fechaTemporal);

            if (diaFest.compareTo(fechaTemporal) == 0) {
                logger.debug("Finaliza isFestivo(Calendar, List<DiasFestivos>)");
                return true;
            }
        }
        
        logger.debug("Finaliza "+firmaMetodo );
        return false;
    }
    
//    /**
//     * Función para modificar una fecha calendario
//     * */
//    public static Calendar modificarFecha(Calendar fecha){
//        String firmaMetodo = "FuncionesUtilitarias.modificarFecha(Calendar, List<DiasFestivosModeloDTO>)";
//        logger.debug("Inicia "+firmaMetodo );
//        
//        logger.debug("Finaliza "+firmaMetodo );
//    }
}