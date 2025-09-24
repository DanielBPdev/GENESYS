/**
 * 
 */
package com.asopagos.novedades.fovis.convertidores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.dto.modelo.ParametrizacionFOVISModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.enumeraciones.fovis.PlazoVencimientoEnum;
import com.asopagos.fovis.clients.ConsultarDatosGeneralesFovis;
import com.asopagos.listas.clients.ConsultarListaValores;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ConsultarPostulacionesNovedadVencimiendoSubsidios;
import com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;

/**
 * Clase que contiene la lógica para validar la novedad Suspensión automática por cambio de año calendario
 * 
 * <b>Historia de Usuario:</b> HU 095 Registrar novedades automáticas FOVIS
 * proceso 3.2.5
 * @author Edward Castano <ecastano@heinsohn.com.co>
 *
 */
public class ValidarNovedadAutomaticaVencimientoSubsidiosAsignados implements ValidacionAutomaticaFovisCore {

    private final ILogger logger = LogManager.getLogger(ValidarNovedadAutomaticaVencimientoSubsidiosAsignados.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar(com.asopagos.enumeraciones.fovis.ParametroFOVISEnum)
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar(ParametroFOVISEnum parametro) {

        DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaFovisDTO();
        try {
            logger.debug("Inicia método ValidarNovedadAutomaticaSuspencionCambioAnoCalendario.validar()");
            List<PostulacionFOVISModeloDTO> listaPostulacionesVencimiendo = new ArrayList<PostulacionFOVISModeloDTO>();
            //Se identifican las postulaciones que cumplen con la condicion para generar la novedad automatica de suspencion por cambio de año
            if (parametro != null && ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA.equals(parametro)) {
                listaPostulacionesVencimiendo = consultarPostulacionesNovedadVencimiendoSubsidios(EstadoHogarEnum.ASIGNADO_SIN_PRORROGA);
            }
            else if (parametro != null && ParametroFOVISEnum.PLAZO_VENCIMIENTO_PRIMERA_PRORROGA.equals(parametro)) {
                listaPostulacionesVencimiendo = consultarPostulacionesNovedadVencimiendoSubsidios(
                        EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA);
            }
            else if (parametro != null && ParametroFOVISEnum.PLAZO_VENCIMIENTO_SEGUNDA_PRORROGA.equals(parametro)) {
                listaPostulacionesVencimiendo = consultarPostulacionesNovedadVencimiendoSubsidios(
                        EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA);
            }

            List<PostulacionFOVISModeloDTO> listaVencimiendoProcesar = new ArrayList<PostulacionFOVISModeloDTO>();
            //Se obtiene la paramtrizacion general FOVIS
            List<ParametrizacionFOVISModeloDTO> parametrizacion = consultarDatosGeneralesFovis();
            //Se obtienen los valores parametrizados para los plazos de vencimiento para los hogares con estado 
            // Asignado sin prórroga, Asignado con primera prórroga, Asignado con segunda prórroga
            ParametrizacionFOVISModeloDTO vencimientoSinProrroga = new ParametrizacionFOVISModeloDTO();
            ParametrizacionFOVISModeloDTO vencimientoPrimeraProrroga = new ParametrizacionFOVISModeloDTO();
            ParametrizacionFOVISModeloDTO vencimientoSegundaProrroga = new ParametrizacionFOVISModeloDTO();
            if (parametrizacion != null && !parametrizacion.isEmpty()) {
                for (ParametrizacionFOVISModeloDTO param : parametrizacion) {
                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SIN_PRORROGA.equals(param.getParametro())) {
                        vencimientoSinProrroga = param;
                    }

                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_PRIMERA_PRORROGA.equals(param.getParametro())) {
                        vencimientoPrimeraProrroga = param;
                    }

                    if (ParametroFOVISEnum.PLAZO_VENCIMIENTO_SEGUNDA_PRORROGA.equals(param.getParametro())) {
                        vencimientoSegundaProrroga = param;
                    }
                }
            }

            if (listaPostulacionesVencimiendo != null && !listaPostulacionesVencimiendo.isEmpty()) {
                String mesesVencimientoSinProrroga = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.MESES_VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA);
                String mesesVencimientoSinSegunda = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.MESES_VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA);
                String mesesVencimientoConSegunda = (String) CacheManager
                        .getParametro(ParametrosSistemaConstants.MESES_VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA);
                //Se procesan las postulaciones consultadas
                for (PostulacionFOVISModeloDTO postulacionDTO : listaPostulacionesVencimiendo) {

                    //Se validan lo hogares con estado: "Asignado sin prórroga", "Asignado con primera prórroga", "Asignado con segunda prórroga"
                    if (EstadoHogarEnum.ASIGNADO_SIN_PRORROGA.equals(postulacionDTO.getEstadoHogar())) {
                        //Se suma el valor parametrizado para el vencimiento sin prorroga a la fecha de publicación de los resultados de cada asignación
                        //para identificar si se va a procedar esta postulacion en la novedad
                        if (postulacionDTO.getFechaPublicacion() != null) {
                            Calendar fechaVencimiento = calcularFechaVencimiento(new Date(postulacionDTO.getFechaPublicacion()),
                                    vencimientoSinProrroga);
                            // Después que se cumplan 12 meses mas el valor parametrizado, de la fecha de publicación de los resultados de cada asignación
                            if (mesesVencimientoSinProrroga != null && !mesesVencimientoSinProrroga.isEmpty()) {
                                fechaVencimiento.add(Calendar.MONTH, Integer.valueOf(mesesVencimientoSinProrroga));
                            }

                            Date fecVen = CalendarUtils.truncarHoraMaxima(fechaVencimiento.getTime());
                            Calendar cal = Calendar.getInstance();
                            Date fecAct = CalendarUtils.truncarHoraMaxima(cal.getTime());
                            //logger.info("fecAct " + fecAct);
                            //logger.info("fecVen " + fecVen);

                            //if (CalendarUtils.obtenerDiferenciaEntreFechas(fechaVencimiento, Calendar.getInstance(), Calendar.DATE) == 0) {
                            if (CalendarUtils.esFechaMenor(fecVen, fecAct)) {
                                //Si la fecha de publicacion es igual a la fecha actual se cambia el estado del hogar
                                postulacionDTO.setEstadoHogar(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA);
                                listaVencimiendoProcesar.add(postulacionDTO);
                            }
                        } else {
                            continue;
                        }

                    }
                    //Cambia el estado de los hogares con estado "Habil segundo año" a "Hogar rechazado"
                    if (EstadoHogarEnum.ASIGNADO_CON_PRIMERA_PRORROGA.equals(postulacionDTO.getEstadoHogar())) {
                        //Se suma el valor parametrizado para el vencimiento sin prorroga a la fecha de publicación de los resultados de cada asignación
                        //para identificar si se va a procedar esta postulacion en la novedad
                        if (postulacionDTO.getFechaPublicacion() != null) {
                            Calendar fechaVencimiento = calcularFechaVencimiento(new Date(postulacionDTO.getFechaPublicacion()),
                                    vencimientoPrimeraProrroga);
                            // Después que se cumplan 24 meses mas el valor parametrizado, de la fecha de publicación de los resultados de cada asignación
                            if (mesesVencimientoSinSegunda != null && !mesesVencimientoSinSegunda.isEmpty()) {
                                fechaVencimiento.add(Calendar.MONTH, Integer.valueOf(mesesVencimientoSinSegunda));
                            }

                            Date fecVen = CalendarUtils.truncarHoraMaxima(fechaVencimiento.getTime());
                            Calendar cal = Calendar.getInstance();
                            Date fecAct = CalendarUtils.truncarHoraMaxima(cal.getTime());
                            //logger.info("fecAct " + fecAct);
                            //logger.info("fecVen " + fecVen);

                            //if (CalendarUtils.obtenerDiferenciaEntreFechas(fechaVencimiento, Calendar.getInstance(), Calendar.DATE) == 0) {
                            if (CalendarUtils.esFechaMenor(fecVen, fecAct)) {
                                postulacionDTO.setEstadoHogar(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA);
                                listaVencimiendoProcesar.add(postulacionDTO);
                            }
                        } else {
                            continue;
                        }

                    }

                    if (EstadoHogarEnum.ASIGNADO_CON_SEGUNDA_PRORROGA.equals(postulacionDTO.getEstadoHogar())) {
                        //Se suma el valor parametrizado para el vencimiento sin prorroga a la fecha de publicación de los resultados de cada asignación
                        //para identificar si se va a procedar esta postulacion en la novedad
                        if (postulacionDTO.getFechaPublicacion() != null) {
                            Calendar fechaVencimiento = calcularFechaVencimiento(new Date(postulacionDTO.getFechaPublicacion()),
                                    vencimientoSegundaProrroga);

                            logger.info("fechaVencimiento ----> " +fechaVencimiento.getTime());

                            //Se valida si existe algun plazo adicional parametrizado para la novedad si es asi se recalcula la fecha de vencimiento
                            if (vencimientoSegundaProrroga.getPlazoAdicional() != null
                                    && vencimientoSegundaProrroga.getValorAdicional() != null) {
                                fechaVencimiento = agregarPlazoAdicional(fechaVencimiento.getTime(), vencimientoSegundaProrroga);
                            }

                            logger.info("fechaVencimiento 3.0----> " +fechaVencimiento.getTime());

                            // Después que se cumplan 36 meses mas el valor parametrizado, de la fecha de publicación de los resultados de cada asignación
                            logger.info("mesesVencimientoConSegunda ----> " +mesesVencimientoConSegunda);
                            if (mesesVencimientoConSegunda != null && !mesesVencimientoConSegunda.isEmpty()) {
                                fechaVencimiento.add(Calendar.MONTH, Integer.valueOf(mesesVencimientoConSegunda));
                                logger.info("fechaVencimiento 176 ----> " +fechaVencimiento.getTime());
                            }
                            Date fecVen = CalendarUtils.truncarHoraMaxima(fechaVencimiento.getTime());
                            Calendar cal = Calendar.getInstance();
                            Date fecAct = CalendarUtils.truncarHoraMaxima(cal.getTime());
                            //logger.info("fecAct " + fecAct);
                            //logger.info("fecVen " + fecVen);
                            logger.info("fecVen " +fecVen.getTime());
                            logger.info("fecAct " +fecAct.getTime());
                            //if (CalendarUtils.obtenerDiferenciaEntreFechas(fechaVencimiento, Calendar.getInstance(), Calendar.DATE) == 0) {
                            if (CalendarUtils.esFechaMenor(fecVen, fecAct)) {
                                postulacionDTO.setEstadoHogar(EstadoHogarEnum.VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA);
                                listaVencimiendoProcesar.add(postulacionDTO);
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }

            datosNovedadAutomaticaDTO.setListaPostulaciones(listaVencimiendoProcesar);
            logger.debug("Finaliza método ValidarNovedadAutomaticaSuspencionCambioAnoCalendario.validar()");
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en ValidarNovedadAutomaticaSuspencionCambioAnoCalendario.validar()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return datosNovedadAutomaticaDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar()
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Método que invoca el servicio que consulta las postulaciones en que cumplen con las condiciones de la novedad de sancion automatica
     * por cambio de calendario
     * 
     * @return Objeto <code>List<PostulacionFOVISModeloDTO> </code> con la información de los postulaciones a procesar por la novedad
     */
    private List<PostulacionFOVISModeloDTO> consultarPostulacionesNovedadVencimiendoSubsidios(EstadoHogarEnum estadoHogar) {
        logger.debug("Inicia el método consultarPostulacionesNovedadSuspencionAutomatica");
        ConsultarPostulacionesNovedadVencimiendoSubsidios service = new ConsultarPostulacionesNovedadVencimiendoSubsidios(estadoHogar);
        service.execute();
        logger.debug("Finaliza el método consultarPostulacionesNovedadSuspencionAutomatica");
        return service.getResult();
    }

    /**
     * Método que invoca el servicio que consulta los parámetros generales de FOVIS
     * 
     * @return La lista de los parámetros generales de FOVIS
     */
    private List<ParametrizacionFOVISModeloDTO> consultarDatosGeneralesFovis() {
        logger.debug("Inicia el método consultarDatosGeneralesFovis");
        ConsultarDatosGeneralesFovis service = new ConsultarDatosGeneralesFovis();
        service.execute();
        logger.debug("Finaliza el método consultarDatosGeneralesFovis");
        return service.getResult();
    }

    /**
     * 
     * Metodo encargado de validar y calcular la fecha de vencimiento de la postulacion
     * 
     * @param postulacionDTO
     *        postulacion a validar
     * @param parametrizacionFovis
     *        parametrizacion definida para el proceso
     * @return fecha de vencimiendo dadas las condiciones de la postulacion y la parametrizacion realizada
     */
    private Calendar calcularFechaVencimiento(Date fechaPublicacion, ParametrizacionFOVISModeloDTO parametrizacionFovis) {
        logger.debug("Inicia el método calcularFechaVencimiendo");
        //logger.info("fechaPublicacion - llegando a la funcion: " + fechaPublicacion);

        ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
        consultarListafestivos.execute();

        //Se suma el valor parametrizado para el vencimiento sin prorroga a la fecha de publicación de los resultados de cada asignación
        //para identificar si se va a procedar esta postulacion en la novedad
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaPublicacion);
        calendar.add(Calendar.MONTH, 1);
        Date fechaInicio = CalendarUtils.obtenerPrimerDiaMesTruncarHora(calendar.getTime());

        Calendar fechaVencimiento = Calendar.getInstance();
        fechaVencimiento.setTime(fechaInicio);
        //logger.info("fechaInicio: " + fechaInicio);
        //logger.info("fechaVencimiento: " + fechaVencimiento);

        if (PlazoVencimientoEnum.DIAS_HABILES.equals(parametrizacionFovis.getPlazoVencimiento())) {
            Date fechaCalculada = CalendarUtils.calcularFecha(fechaInicio, parametrizacionFovis.getValorNumerico().intValue(),
                    CalendarUtils.TipoDia.HABIL, consultarListafestivos.getResult());
            fechaVencimiento.setTime(fechaCalculada);
        }
        else if (PlazoVencimientoEnum.DIAS_CALENDARIO.equals(parametrizacionFovis.getPlazoVencimiento())) {
            Date fechaCalculada = CalendarUtils.calcularFecha(fechaInicio, parametrizacionFovis.getValorNumerico().intValue(),
                    CalendarUtils.TipoDia.CALENDARIO, consultarListafestivos.getResult());
            fechaVencimiento.setTime(fechaCalculada);
        }
        else if (PlazoVencimientoEnum.MESES.equals(parametrizacionFovis.getPlazoVencimiento())) {
            fechaVencimiento.setTime(fechaInicio);
            fechaVencimiento.add(Calendar.MONTH, parametrizacionFovis.getValorNumerico().intValue());
        }
        else if (PlazoVencimientoEnum.ANIOS.equals(parametrizacionFovis.getPlazoVencimiento())) {
            fechaVencimiento.setTime(fechaInicio);
            fechaVencimiento.add(Calendar.YEAR, parametrizacionFovis.getValorNumerico().intValue());
        }
        //logger.info("fechaVencimiento saliendo: " + fechaVencimiento);

        logger.debug("Finaliza el método calcularFechaVencimiendo");
        return fechaVencimiento;

    }

    /**
     * 
     * Metodo encargado de validar y calcular la fecha de vencimiento de la postulacion
     * 
     * @param postulacionDTO
     *        postulacion a validar
     * @param parametrizacionFovis
     *        parametrizacion definida para el proceso
     * @return fecha de vencimiendo dadas las condiciones de la postulacion y la parametrizacion realizada
     */
    private Calendar agregarPlazoAdicional(Date fechaPublicacion, ParametrizacionFOVISModeloDTO parametrizacionFovis) {
        logger.debug("Inicia el método calcularFechaVencimiendo");
        ConsultarListaValores consultarListafestivos = new ConsultarListaValores(239, null, null);
        consultarListafestivos.execute();

        //Se suma el valor parametrizado para el vencimiento sin prorroga a la fecha de publicación de los resultados de cada asignación
        //para identificar si se va a procedar esta postulacion en la novedad
        Calendar fechaVencimiento = Calendar.getInstance();
        if (parametrizacionFovis.getPlazoAdicional() != null && parametrizacionFovis.getValorAdicional() != null) {
            logger.info("ingresa a plazo adicional");
            if (PlazoVencimientoEnum.DIAS_HABILES.equals(parametrizacionFovis.getPlazoAdicional())) {
                Date fechaCalculada = CalendarUtils.calcularFecha(fechaPublicacion, parametrizacionFovis.getValorAdicional().intValue(),
                        CalendarUtils.TipoDia.HABIL, consultarListafestivos.getResult());
                fechaVencimiento.setTime(fechaCalculada);
            }
            else if (PlazoVencimientoEnum.DIAS_CALENDARIO.equals(parametrizacionFovis.getPlazoAdicional())) {
                Date fechaCalculada = CalendarUtils.calcularFecha(fechaPublicacion, parametrizacionFovis.getValorAdicional().intValue(),
                        CalendarUtils.TipoDia.CALENDARIO, consultarListafestivos.getResult());
                fechaVencimiento.setTime(fechaCalculada);
                logger.info("DIAS_CALENDARIO " +fechaCalculada);
            }
            else if (PlazoVencimientoEnum.MESES.equals(parametrizacionFovis.getPlazoAdicional())) {
                fechaVencimiento.setTime(fechaPublicacion);
                fechaVencimiento.add(Calendar.MONTH, parametrizacionFovis.getValorAdicional().intValue());
            }
            else if (PlazoVencimientoEnum.ANIOS.equals(parametrizacionFovis.getPlazoAdicional())) {
                fechaVencimiento.setTime(fechaPublicacion);
                fechaVencimiento.add(Calendar.YEAR, parametrizacionFovis.getValorAdicional().intValue());
            }

            logger.info("fechaVencimiento " +fechaVencimiento);

        }
        logger.info("fechaVencimiento 2.0" +fechaVencimiento);
        logger.debug("Finaliza el método calcularFechaVencimiendo");
        return fechaVencimiento;

    }

}
