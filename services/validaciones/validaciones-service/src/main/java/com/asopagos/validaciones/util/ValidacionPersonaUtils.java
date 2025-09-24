/**
 * 
 */
package com.asopagos.validaciones.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * Clase que contiene la lógica en comun utilizada por los validadores
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
public class ValidacionPersonaUtils {

	/**
	 * Constante que contiene el formato a trabajar de la fecha
	 */
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	/**
	 * Constante que contiene el split / para la lectura de la fechas
	 */
	private static final String SPLIT_FORMATO_FECHA = "/";

	/**
	 * Metodo encargado de convertir una fecha en tipo Long a String
	 * 
	 * @param fechaLong,fecha
	 *            a realizar la conversion
	 * @return retorna la fecha en tipo String y en formado dd/MM/yyyy
	 * @throws Exception,
	 *             se propaga la excepcion en caso de existir una
	 */
	public static String convertirFecha(String fechaLong) throws Exception {
		Date fechaDate = new Date(new Long(fechaLong));
		SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
		String fechaString = formato.format(fechaDate);
		return fechaString;
	}

	/**
	 * Metodo encargado de calcular la de edad en años de acuerdo a la fecha de
	 * nacimiento
	 * 
	 * @param fecha_nac,fecha
	 *            de nacimiento de la persona
	 * @return retorna la edad de la persona en años
	 */
	public static int calcularEdadAnos(String fechaNacimiento) {
		// Se obtiene la fecha actual
		Date fechaActual = new Date();
		// Formato a utilizar en la fecha dd/MM/yyyy
		SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
		// Se obtiene el dia de hoy
		String hoy = formato.format(fechaActual);
		// Se agrega a una arreglo la lectura del formato de la fecha de
		// nacimiento
		String[] arregloFechaNacimiento = fechaNacimiento.split(SPLIT_FORMATO_FECHA);
		// Se agrega a una arreglo la lectura del formato de la fecha de actual
		String[] arregloFechaActual = hoy.split(SPLIT_FORMATO_FECHA);
		int anos = Integer.parseInt(arregloFechaActual[2]) - Integer.parseInt(arregloFechaNacimiento[2]);
		int mes = Integer.parseInt(arregloFechaActual[1]) - Integer.parseInt(arregloFechaNacimiento[1]);
		// Se valida si el mes es menor a cero
		if (mes < 0) {
			anos = anos - 1;
		} else // Veficacion que el mes sea igual a cero
		if (mes == 0) {
			int dia = Integer.parseInt(arregloFechaActual[0]) - Integer.parseInt(arregloFechaNacimiento[0]);
			// Verificacion que el dia sea mayor a cero
			if (dia < 0) {
				anos = anos - 1;
			}
		}
		return anos;
	}

	/**
	 * Método que obtiene las clasisficaciones por sujeto tramite.
	 * 
	 * @param tipo
	 *            de sujeto tramite.
	 * @return listado de las clasificaciones.
	 */
	public static List<ClasificacionEnum> obtenerClasificacionNaturalYDomestico() {
		List<ClasificacionEnum> clasificacionList = new ArrayList<>();
		clasificacionList.add(ClasificacionEnum.PERSONA_NATURAL);
		clasificacionList.add(ClasificacionEnum.EMPLEADOR_DE_SERVICIO_DOMESTICO);
		return clasificacionList;
	}

	/**
	 * Método que obtiene las clasisficaciones para hijo.
	 * 
	 * @return listado de las clasificaciones.
	 */
	public static List<ClasificacionEnum> obtenerClasificacionHijo() {
		List<ClasificacionEnum> clasificacionList = new ArrayList<>();
		clasificacionList.add(ClasificacionEnum.HIJO_BIOLOGICO);
		clasificacionList.add(ClasificacionEnum.HIJO_ADOPTIVO);
		clasificacionList.add(ClasificacionEnum.HIJASTRO);
		clasificacionList.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
		clasificacionList.add(ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
		return clasificacionList;
	}

	/**
	 * Método que obtiene lista hijo huerfano.
	 * 
	 * @return listado de las clasificaciones.
	 */
	public static List<ClasificacionEnum> obtenerListHijoHuerfano() {
		List<ClasificacionEnum> clasificacionList = new ArrayList<ClasificacionEnum>();
		clasificacionList.add(ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
		return clasificacionList;
	}

	/**
	 * Método que obtiene las clasificaciones por sujeto tramite.
	 * 
	 * @return listado de las clasificaciones padres.
	 */
	public static List<ClasificacionEnum> obtenerClasificacionPadre() {
		List<ClasificacionEnum> clasificacionList = new ArrayList<>();
		clasificacionList.add(ClasificacionEnum.PADRE);
		clasificacionList.add(ClasificacionEnum.MADRE);
		return clasificacionList;
	}

	/**
	 * Método que obtiene las clasificaciones por sujeto tramite.
	 * 
	 * @return listado de los estados.
	 */
	public static List<EstadoAfiliadoEnum> obtenerEstadosAfiliado() {
		List<EstadoAfiliadoEnum> estadoList = new ArrayList<>();
		estadoList.add(EstadoAfiliadoEnum.ACTIVO);
		estadoList.add(EstadoAfiliadoEnum.INACTIVO);
		return estadoList;
	}

	/**
	 * Método que obtiene las clasificaciones por sujeto tramite.
	 * 
	 * @return listado de los estados.
	 */
	public static List<EstadoAfiliadoEnum> obtenerListaEstadoActivo() {
		List<EstadoAfiliadoEnum> estadoList = new ArrayList<>();
		estadoList.add(EstadoAfiliadoEnum.ACTIVO);
		return estadoList;
	}
	
	/**
	 * Método que obtiene el estado activo de un beneficiario
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @return estado activo de un beneficiario
	 */
	public static EstadoAfiliadoEnum obtenerEstadoActivoBeneficiario() {
		return EstadoAfiliadoEnum.ACTIVO;
	}
	
	/**
	 * Método que obtiene el estado inactivo de un beneficiario
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @return estado inactivo de un beneficiario
	 */
	public static EstadoAfiliadoEnum obtenerEstadoInactivoBeneficiario() {
		return EstadoAfiliadoEnum.INACTIVO;
	}	
	
	/**
	 * Método que obtiene el motivo de desafiliación de fallecimiento de un beneficiario
	 * 
	 * @author Francisco Alejandro Hoyos Rojas
	 * @return motivo de desafiliación de fallecimiento de un beneficiario
	 */
	public static MotivoDesafiliacionBeneficiarioEnum obtenerMotivoDesafiliacionFallecimientoBeneficiario() {
		return MotivoDesafiliacionBeneficiarioEnum.FALLECIMIENTO;
	}	
}
