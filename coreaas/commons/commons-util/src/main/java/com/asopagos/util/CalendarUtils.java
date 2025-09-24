package com.asopagos.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.asopagos.dto.ElementoListaDTO;
import co.com.heinsohn.lion.common.util.CalendarUtil;

/**
 * <b>Descripción:</b> Clase que contiene métodos utilitarios para trabajar con
 * fechas <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class CalendarUtils {

    /**
     * Constante que contiene el formato a trabajar de la fecha
     */
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    /**
     * Constante que contiene el split / para la lectura de la fechas
     */
    private static final String SPLIT_FORMATO_FECHA = "/";

    private static final List<String> monthsES = Arrays.asList("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");

    private static final List<String> monthsEN = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    private static final String DATE_FORMAT = "MMM d yyyy hh:mma";

    public enum TipoDia {
        HABIL, CALENDARIO
    }

    /**
     * Método que permite truncar la hora de un objeto Date
     * 
     * @param original
     * @return
     */
    public static Date truncarHora(Date original) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(original);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Método que permite truncar la hora maxima de un objeto Date
     * 
     * @param original
     * @return
     */
    public static Date truncarHoraMaxima(Date original) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(original);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Método encargado de sumar dias a una fecha
     * 
     * @param fecha,
     *        fecha enviada
     * @param dias,
     *        dias a sumar
     * @return fecha con dias sumados
     */
    public static Date sumarDias(Date fecha, Integer dias) {
        if (fecha != null && dias != null) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(fecha);
            dia.add(Calendar.DAY_OF_YEAR, dias);
            return dia.getTime();
        }
        return fecha;
    }

    /**
     * Método encargado de restar dias a una fecha
     * 
     * @param fecha,
     *        fecha enviada
     * @param dias,
     *        dias a restar
     * @return fecha con dias restados
     */
    public static Date restarDias(Date fecha, Integer dias) {
        if (fecha != null && dias != null) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(fecha);
            dia.add(Calendar.DAY_OF_YEAR, (dias * -1));
            return dia.getTime();
        }
        return fecha;
    }

    /**
     * Método encargado de obtener el dia siguiente a una fecha
     * 
     * @param fecha,
     *        fecha enviada
     * @return dia siguiente
     */
    public static Date obtenerDiaSiguiente(Date fecha) {
        if (fecha != null) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(fecha);
            dia.add(Calendar.DAY_OF_YEAR, 1);
            return dia.getTime();
        }
        return fecha;
    }

    /**
     * Método encargado de obtener el dia siguiente a una fecha
     * 
     * @param fecha,
     *        fecha enviada
     * @return dia siguiente
     */
    public static Date obtenerDiaAnterior(Date fecha) {
        if (fecha != null) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(fecha);
            dia.add(Calendar.DAY_OF_YEAR, -1);
            return dia.getTime();
        }
        return fecha;
    }

    /**
     * Metodo que determina si la fechaA es menor a la fechaB
     * 
     * @param fechaA,
     *        fecha A
     * @param fechaB,
     *        fecha B
     * @return true en caso que la fechaA sea menor que la fechaB, false caso
     *         contrario
     */
    public static boolean esFechaMenor(Date fechaA, Date fechaB) {
        return fechaA.before(fechaB);
    }

    /**
     * Metodo que determina si la fechaA es menor o igual a la fechaB
     * 
     * @param fechaA,
     *        fecha A
     * @param fechaB,
     *        fecha B
     * @return true en caso que la fechaA sea menor o igual que la fechaB, false
     *         caso contrario
     */
    public static boolean esFechaMenorIgual(Date fechaA, Date fechaB) {
        return fechaA.before(fechaB) || fechaA.compareTo(fechaB) == 0;
    }

    /**
     * Metodo que determina si la fechaA es mayor a la fechaB
     * 
     * @param fechaA,
     *        fecha A
     * @param fechaB,
     *        fecha B
     * @return true en caso que la fechaA sea mayor que la fechaB, false caso
     *         contrario
     */
    public static boolean esFechaMayor(Date fechaA, Date fechaB) {
        return fechaA.after(fechaB);
    }

    /**
     * Metodo que determina si la fechaA es mayor o igual a la fechaB
     * 
     * @param fechaA,
     *        fecha A
     * @param fechaB,
     *        fecha B
     * @return true en caso que la fechaA sea mayor o igual que la fechaB, false
     *         caso contrario
     */
    public static boolean esFechaMayorIgual(Date fechaA, Date fechaB) {
        return fechaA.after(fechaB) || fechaA.compareTo(fechaB) == 0;
    }

    /**
     * Metodo encargado de calcular una fecha en años
     * 
     * @param fecha,fecha
     *        a la cual se va realizar el calculo de los años
     * @return retorna la fecha en años
     */
    public static int calcularEdadAnos(Date fecha) {
        // Se obtiene la fecha actual
        Date fechaActual = new Date();
        // Formato a utilizar en la fecha dd/MM/yyyy
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        // Se obtiene el dia de hoy
        String hoy = formato.format(fechaActual);
        // Se obtiene la fecha a calcular
        String fechaCalculo = formato.format(fecha);
        // Se agrega a una arreglo la lectura del formato de la fecha de
        // nacimiento
        String[] arregloFechaCalculo = fechaCalculo.split(SPLIT_FORMATO_FECHA);
        // Se agrega a una arreglo la lectura del formato de la fecha de actual
        String[] arregloFechaActual = hoy.split(SPLIT_FORMATO_FECHA);
        int anos = Integer.parseInt(arregloFechaActual[2]) - Integer.parseInt(arregloFechaCalculo[2]);
        int mes = Integer.parseInt(arregloFechaActual[1]) - Integer.parseInt(arregloFechaCalculo[1]);
        // Se valida si el mes es menor a cero
        if (mes < 0) {
            anos = anos - 1;
        }
        else // Veficacion que el mes sea igual a cero
        if (mes == 0) {
            int dia = Integer.parseInt(arregloFechaActual[0]) - Integer.parseInt(arregloFechaCalculo[0]);
            // Verificacion que el dia sea menor a cero
            if (dia < 0) {
                anos = anos - 1;
            }
        }
        return anos;
    }

    /**
     * Método encargado de convertir una fecha de String a Date utilizando el
     * formato recibido
     * 
     * @param fecha,
     *        fecha a realizar la conversión
     * @param formato,
     *        formato de la fecha
     * @return retorna la fecha en Date
     * @throws ParseException
     */
    public static Long convertirFechaDate(String fecha) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.US);
        Date dateIn = (Date) formatter.parse(fecha);
        return dateIn.getTime();
    }

    /**
     * Método que obtiene la diferencia existente entre una fecha A y una fecha
     * B
     * 
     * @param Calendar
     *        fechaA
     * @param Calendar
     *        fechaB
     * @param int
     *        dataType (Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
     *        Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND)
     * @return long, diferencia en dataType recibido
     * 
     * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
     */
    public static long obtenerDiferenciaEntreFechas(Calendar fechaA, Calendar fechaB, int dataType) {
        long diference = 0;
        if (fechaB != null && fechaA != null) {
            diference = fechaB.getTimeInMillis() - fechaA.getTimeInMillis();
            switch (dataType) {
                case Calendar.YEAR:
                    diference = diference / 31536000000L;
                    break;
                case Calendar.MONTH:
                    diference = diference / 2592000000L;
                    break;
                case Calendar.DATE:
                    diference = diference / 86400000;
                    break;
                case Calendar.HOUR:
                    diference = diference / 3600000;
                    break;
                case Calendar.MINUTE:
                    diference = diference / 60000;
                    break;
                case Calendar.SECOND:
                    diference = diference / 1000;
                    break;
            }
        }
        return diference;
    }

    /**
     * Método que obtiene la diferencia de años, meses y días entre dos fechas
     * @param fechaInicial
     *        Fecha inicial
     * @param fechaFinal
     *        Fecha final
     * @return Cadena de la forma "x años, y meses, z días"
     */
    public static String obtenerDiferenciaEntreFechasComoString(Date fechaInicial, Date fechaFinal) {
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.setTime(fechaInicial);
        LocalDate dateInicial = LocalDate.of(calendarInicial.get(Calendar.YEAR), calendarInicial.get(Calendar.MONTH) + 1,
                calendarInicial.get(Calendar.DAY_OF_MONTH));

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(fechaFinal);
        LocalDate dateFinal = LocalDate.of(calendarFinal.get(Calendar.YEAR), calendarFinal.get(Calendar.MONTH) + 1,
                calendarFinal.get(Calendar.DAY_OF_MONTH));

        Period diferencia = Period.between(dateInicial, dateFinal);
        StringBuilder resultado = new StringBuilder();

        if (diferencia.getYears() > 0) {
            resultado.append(diferencia.getYears());
            resultado.append(diferencia.getYears() > 1 ? " años, " : " año, ");
        }

        if (diferencia.getMonths() > 0) {
            resultado.append(diferencia.getMonths());
            resultado.append(diferencia.getMonths() > 1 ? " meses, " : " mes, ");
        }

        resultado.append(diferencia.getDays());
        resultado.append(diferencia.getDays() > 1 ? " días" : " día");

        return resultado.toString();
    }

    /**
     * Realiza la conversión de una cadena a calendar.
     * 
     * @param fechaString
     *        cadena a convertir a fecha.
     * @return Calendar, fecha resultante. null si ocurre un error de
     *         conversión.
     * 
     * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
     */
    public static Calendar convertirCadenaAFecha(String fechaString) {
        Calendar fechaConvertida = Calendar.getInstance();
        try {
            DateFormat df = new SimpleDateFormat(FORMATO_FECHA);
            Date date = (Date) df.parse(fechaString);

            fechaConvertida.setTime(date);
        } catch (Exception e) {
            return null;
        }
        return fechaConvertida;
    }

    /**
     * Método que configura en cero la hora, minutos, segundos y milisegundos de
     * una fecha recibida.
     * 
     * @return Calendar, con la fecha formateada sin hora
     * 
     * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
     */
    public static Calendar formatearFechaSinHora(Calendar fechaOriginal) {
        Calendar fecha = (Calendar) fechaOriginal.clone();
        fecha.set(Calendar.HOUR_OF_DAY, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        return fecha;
    }

    /**
     * Método encargado de validar si la fecha A es mayor a la fecha B
     * 
     * @param fechaA
     *        Fecha A
     * @param fechaB
     *        Fecha B
     * @return boolean, retorna true si la Fecha A es mayor a la Fecha B
     * 
     * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
     */
    public static boolean esFechaMayor(Calendar fechaA, Calendar fechaB) {
        long diaDiferencia;
        diaDiferencia = obtenerDiferenciaEntreFechas(fechaA, fechaB, Calendar.SECOND);
        if (diaDiferencia < 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de convertir una fecha de tipo date a String en un
     * formato especifico
     * 
     * @param fecha,
     *        fecha a dar el formato
     * @return retorna la fecha en formato yyyy-MM-dd
     */
    public static String darFormatoYYYYMMDD(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format.format(fecha.getTime());
        return formatted;
    }
    
    /**
     * Metodo encargado de convertir una fecha de tipo date a String en un
     * formato especifico
     * 
     * @param fecha,
     *        fecha a dar el formato
     * @return retorna la fecha en formato yyyy-MM
     */
    public static String darFormatoYYYYMM(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String formatted = format.format(fecha.getTime());
        return formatted;
    }

    /**
     * Metodo encargado de convertir una fecha de tipo date a String en un
     * formato especifico
     * 
     * @param fecha,
     *        fecha a dar el formato
     * @return retorna la fecha en formato yyyyMMdd
     */
    public static String darFormatoYYYYMMDDSinGuion(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String formatted = format.format(fecha.getTime());
        return formatted;
    }

    /**
     * Método encargado de dar formato a la fecha yyyy-MM-dd y convertirla a un
     * Date
     * 
     * @param fecha,
     *        fecha a realizar la conversion
     * @return retorna la fecha de tipo Date
     */
    public static Date darFormatoYYYYMMDDGuionDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate;
        try {
            fechaDate = sdf.parse(fecha);
            if (!fecha.equals(sdf.format(fechaDate))) {
                fechaDate = null;
            }
        } catch (ParseException e) {
            return null;
        }
        return fechaDate;
    }

    /**
     * Método encargado de dar formato a la fecha dd/MM/yyyy y convertirla a un
     * Date
     * 
     * @param fecha,
     *        fecha a realizar la conversion
     * @return retorna la fecha de tipo Date
     */
    public static Date darFormatoDDMMYYYYSlashDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaDate;
        try {
            fechaDate = sdf.parse(fecha);
            if (!fecha.equals(sdf.format(fechaDate))) {
                fechaDate = null;
            }
        } catch (ParseException e) {
            return null;
        }
        return fechaDate;
    }

    /**
     * Método encargado de dar formato a la fecha dd/MM/yyyy y convertirla a un
     * Date
     *
     * @param fecha,
     *        fecha a realizar la conversion
     * @return retorna la fecha de tipo Date
     */
    public static Date darFormatoDDMMYYYYSlashDateBySheet(String fecha) {
        Date fechaDate;
        try {
            fecha = fecha.replaceAll("\\s+", " ").trim();
            String monthES = fecha.substring(0, 3);
            String monthEN = monthsEN.get(monthsES.indexOf(monthES));
            fecha = fecha.replaceAll(monthES, monthEN);

            ZoneId defaultZoneId = ZoneId.systemDefault();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate localDate = LocalDate.parse(fecha, dtf);

            fechaDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        } catch (IllegalArgumentException | DateTimeParseException e) {
            e.printStackTrace();
            fechaDate = null;
        }
        return fechaDate;
    }

    /**
     * Función para calcular una fecha sumando o restando días a otra
     * @param fecha
     *        Fecha base a la cuál se le sumarán o restarán los días
     * @param dias
     *        Cantidad de días a modificar, si son positivos se suman, negativos se restan
     * @param festivos
     *        Listado de los días festivos registrados en la BD (se obtienen con el servicio ConsultarListaValores -> id=239)
     * @param tipoDia
     *        indica si los días a calcular son hábiles o calendario
     * @return Fecha modificada
     */
    public static Date calcularFecha(Date fecha, Integer dias, TipoDia tipoDia, List<ElementoListaDTO> festivos) {

        int incremento = 1;
        int diasTemporal = dias;
        GregorianCalendar fechaRespuesta = (GregorianCalendar) CalendarUtil.toCalendar(fecha);

        if (dias < 0)
            incremento = -1;

        if (TipoDia.CALENDARIO.equals(tipoDia)) {

            if (incremento > 0) {
                fechaRespuesta.setTime(sumarDias(fecha, dias));
            }
            else {
                restarDias(fecha, dias * incremento);
            }
        }
        else { // se calcula con días hábiles

            // se modifica la fecha día a día
            while (diasTemporal != 0) {
                // se modifica la fecha
                fechaRespuesta.add(Calendar.DAY_OF_MONTH, incremento);

                // se verifica que no sea sábado, domingo ni festivo
                if (fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                        && fechaRespuesta.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && !esFestivo(fechaRespuesta, festivos)) {
                    diasTemporal -= incremento;
                }
            }
        }
        return fechaRespuesta.getTime();
    }

    /**
     * Función para establecer sí una fecha está incluida en el listado de festivos
     * @param fecha
     *        Fecha a buscar
     * @param festivos
     *        Listado de días festivos
     * @returns Fecha festivo true o false
     */
    private static boolean esFestivo(Calendar fecha, List<ElementoListaDTO> festivos) {
        for (ElementoListaDTO diaFestivo : festivos) {
            Calendar diaFest = CalendarUtil.toCalendar(new Date((Long) diaFestivo.getAtributos().get("fecha")));
            diaFest = CalendarUtil.fomatearFechaSinHora(diaFest);
            Calendar fechaTemporal = CalendarUtil.fomatearFechaSinHora(fecha);
            if (diaFest.compareTo(fechaTemporal) == 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Función encargada de asignar la hora a una fecha especifica
     * @param fecha,
     *        fecha a asignarle la hora
     * @param hora,
     *        hora a asignar
     * @return fecha con hora
     */
    public static Date concatenarFechaHora(Long fecha, Long hora) {
        Calendar fechaCalendario = Calendar.getInstance();
        fechaCalendario.setTime(new Date(fecha));
        SimpleDateFormat formatoHora = new SimpleDateFormat("hh");
        SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm");
        SimpleDateFormat formatoSegundo = new SimpleDateFormat("ss");
        Date horaDate=new Date(hora);
        String horaString = formatoHora.format(horaDate);
        String minutoString = formatoMinuto.format(horaDate);
        String segundoString = formatoSegundo.format(horaDate);
        fechaCalendario.set(Calendar.HOUR, Integer.valueOf(horaString));
        fechaCalendario.set(Calendar.MINUTE, Integer.valueOf(minutoString));
        fechaCalendario.set(Calendar.SECOND, Integer.valueOf(segundoString));
        return fechaCalendario.getTime();
    }
  
    /**
     * Método encargado de obtener el último día del mes Habil 
     * @param fecha, fecha a verificar el último día del mes 
     * @param diasFestivos, días festivos que existen
     * @return retorna el último día del mes  
     */
    public static Date obtenerUltimoDiaMesHabil(Date fecha, List<ElementoListaDTO> diasFestivos) {
        Calendar fechaCalendar = Calendar.getInstance();
        fechaCalendar.setTime(fecha);
        fechaCalendar.set(Calendar.DAY_OF_MONTH, fechaCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Calendar fechaSinHora = CalendarUtil.fomatearFechaSinHora(fechaCalendar);
        boolean esFestivo = true;
        do {
            esFestivo = esFestivo(fechaSinHora, diasFestivos);
            if (esFestivo) {
                fechaSinHora.setTime(restarDias(fechaSinHora.getTime(), 1));
            }
            else {
                break; 
            }
        } while (esFestivo);
        return fechaSinHora.getTime();
    }
     
    /**
     * Método encargado de conventir una fecha string en formato yyyymmdd a date
     * @param fecha,
     *        fecha a convertir
     * @return retorna la fecha en formato date
     * @throws ParseException 
     */
    public static Date convertirFechaAnoMesDia(String fecha) {
        Date fechaSinFormato= null;
        try {
            fechaSinFormato = new Date (convertirFechaDate(fecha));
        } catch (ParseException e) {
            fechaSinFormato= null;
        }
        Calendar fechaFormato = Calendar.getInstance();
        fechaFormato.setTime(fechaSinFormato);
        fechaFormato = formatearFechaSinHora(fechaFormato);
            
        int year = fechaFormato.get(Calendar.YEAR);
        int month = fechaFormato.get(Calendar.MONTH) + 1;
        int day = fechaFormato.get(Calendar.DAY_OF_MONTH);
        
        String month2Digit = ""+ month;
        String day2Digit = ""+ day;
        
        if (month < 10) {
            month2Digit = "0" + month;
        }
        
        if (day < 10) {
            day2Digit = "0" + day;
        }
              
        String fechaConvertida = year + "-"
                + month2Digit + "-"
                + day2Digit ;
        
        return darFormatoYYYYMMDDGuionDate(fechaConvertida);
    }
    
    /**
     * Método que permite inicailizar la fecha en el primer dia del mes y truncar la hora de un objeto Date
     * 
     * @param original
     * @return
     */
    public static Date obtenerPrimerDiaMesTruncarHora(Date original) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(original);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Calcula el valor en milisegundos de un rango de tiempo en formato [numero]['d','m','h','s']
     * @param parametro String  correspondiente al rango
     * @return Valor en milisegundos de tipo Long
     */
    public static Long toMilis(String parametro){
    	Long milisegundos = 0L;
    	char unidadTiempo = parametro.charAt(parametro.length()-1);
    	
    	if (!Character.isDigit(unidadTiempo)) {
    		Long valorUnidadTiempo = Long.parseLong(parametro.substring(0,parametro.length()-1));
        	
        	switch (unidadTiempo) {
    		case 'd':
    			milisegundos = valorUnidadTiempo*24*3600*1000;
    			break;
    		case 'h':
    			milisegundos = valorUnidadTiempo*3600*1000;
    			break;
    		case 'm':
    			milisegundos = valorUnidadTiempo*60*1000;
    			break;
    		case 's':
    			milisegundos = valorUnidadTiempo*1000;
    			break;
    		default:
    			milisegundos = valorUnidadTiempo;
    			break;
    		}
		} else {
			milisegundos = Long.parseLong(parametro);
		}
    	
    	return milisegundos;
    }
    
    public static String calcularEdadAniosMesesDias(Date fechaNacimiento) {
        String edadAniosMesesDias;
        LocalDate fechaNac = Instant.ofEpochMilli(fechaNacimiento.getTime())
                  .atZone(ZoneId.systemDefault())
                  .toLocalDate();
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        edadAniosMesesDias = periodo.getYears() + " años, " + periodo.getMonths() + " meses y " + periodo.getDays() + " días";
        
        return edadAniosMesesDias;
    }
    
    public static long calcularTiempoEjecucion(long timeStart, long timeEnd){
    	return TimeUnit.SECONDS.convert((timeEnd - timeStart), TimeUnit.NANOSECONDS);
    }

    public static int calcularEdadAnios(Date fechaNacimiento){
        int edadAnios;
        LocalDate fechaNac = Instant.ofEpochMilli(fechaNacimiento.getTime())
                  .atZone(ZoneId.systemDefault())
                  .toLocalDate();
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        edadAnios = periodo.getYears();
        
        return edadAnios;
    }
}
